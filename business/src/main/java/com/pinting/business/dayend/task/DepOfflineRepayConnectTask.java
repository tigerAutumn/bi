package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.*;
import com.pinting.business.accounting.loan.service.DepOfflineRepayService;
import com.pinting.business.accounting.loan.service.RepayPaymentService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.TimeTypeEnum;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.pinting.business.util.Constants.DEP_REQUEST_ITEM_ACCOUNT;

/**
 * Created by zhangbao on 2017/4/11.
 * 恒丰线下还款流程串联（管道）
 */
@Service
public class DepOfflineRepayConnectTask {

    private Logger log = LoggerFactory.getLogger(DepOfflineRepayConnectTask.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private LnDepositionTargetMapper lnDepositionTargetMapper;
    @Autowired
    private DepOfflineRepayService depOfflineRepayService;
    @Autowired
    private LnCompensateRelationMapper lnCompensateRelationMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnRepayScheduleMapper repayScheduleMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private RepayPaymentService repayPaymentService;
    @Autowired
    private BsSysConfigMapper sysMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private LnSubAccountMapper lnSubAccountMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
    @Autowired
    private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;
    @Autowired
    private BsHolidayMapper bsHolidayMapper;
    @Autowired
    private BsPayLimitMapper bsPayLimitMapper;
    /**
     * 代付（归集户代发）定时独立触发
     */
    public void executeDF() throws Exception {
        log.info("===恒丰线下还款归集户代发开始===");

        //提现到恒丰还款实体户（取500条DF_PENDING）
        String df_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_DF);
        if(StringUtil.isNotEmpty(df_flag)){
            log.info("恒丰线下还款归集户代发上一批数据处理中");
            return;
        }
        try {
            jsClientDaoSupport.setString("ING",Constants.OFF_REPAY_DF);
            log.info("恒丰线下还款DF开始");
            //根据配置获取代付数据
            Withdraw2DepRepayCardReq withdraw2DepRepayCardReq = getDf2DepRepayCardReq(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
            if(withdraw2DepRepayCardReq != null && CollectionUtils.isNotEmpty(withdraw2DepRepayCardReq.getDepRepaySchedules()) ){
            	depOfflineRepayService.withdraw2DepRepayCard(withdraw2DepRepayCardReq);
            }else{
            	log.info("不存在DF_PENDING的账单");
            }
            log.info("恒丰线下还款DF结束");

        }catch (Exception e){
            log.error("恒丰线下还款DF异常", e);
        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_DF);
        }

        //提现到恒丰还款实体户失败重发
        String df_repeat_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_REPEAT_DF);
        if(StringUtil.isNotEmpty(df_repeat_flag)){
            log.info("恒丰线下还款归集户重发上一批数据处理中");
            return;
        }
        try {
            jsClientDaoSupport.setString("ING",Constants.OFF_REPAY_REPEAT_DF);
            log.info("恒丰线下还款DF重发开始");
            //根据配置获取需要代付重复的数据
            Withdraw2DepRepayCardReq withdraw2DepRepayCardReq = getDf2DepRepayCardReq(Constants.DEP_REPAY_RETURN_FLAG_DF_FAIL);
            if(withdraw2DepRepayCardReq != null && CollectionUtils.isNotEmpty(withdraw2DepRepayCardReq.getDepRepaySchedules()) ){
            	depOfflineRepayService.withdraw2DepRepayCard(withdraw2DepRepayCardReq);
            }else{
            	log.info("不存在DF_FAIL的账单");
            }
           
            log.info("恒丰线下还款DF重发结束");
        }catch (Exception e){
            log.info("恒丰线下还款DF重发异常", e);
        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_REPEAT_DF);
        }

        log.info("===恒丰线下还款归集户代发结束===");
    }

    /**
     * 根据配置和当期时间，获取代付条数
     * @author bianyatian
     * @return
     */
    private Withdraw2DepRepayCardReq getDf2DepRepayCardReq(String returnFlag) {
    	Withdraw2DepRepayCardReq withdraw2DepRepayCardReq = new Withdraw2DepRepayCardReq();
    	String timeType = "DEFAULT";
    	BsPayLimit payLimit = null;
    	//查询当天是否是工作日
        BsHoliday holiday = bsHolidayMapper.todayIsHolidayOrNot();
        if(holiday == null){
        	//工作日获取周几,确定时间类型
        	Calendar cal = Calendar.getInstance();
    		cal.setTime(new Date());
    		Integer week = cal.get(Calendar.DAY_OF_WEEK)-1;
    		timeType = TimeTypeEnum.getEnumByCode(week.toString()) != null ? TimeTypeEnum.getEnumByCode(week.toString()).getTimeType() : timeType;
    		log.info("=======工作日，查询代付限制=============");
    		payLimit = bsPayLimitMapper.selectBfDfByTimeType(timeType);
    		
        }else{
        	//法定节假日
        	timeType = TimeTypeEnum.HOLIDAY.getTimeType();
        	log.info("=======法定节假日，查询代付限制=============");
        	payLimit = bsPayLimitMapper.selectBfDfByTimeType(timeType);
        }
        if(payLimit == null){
        	//未查询到对应时间的限制数据，则查询默认的数据
        	timeType = TimeTypeEnum.DEFAULT.getTimeType();
        	log.info("=======未查询到对应时间的限制数据，则查询默认的数据=============");
        	payLimit = bsPayLimitMapper.selectBfDfByTimeType(timeType);
        }
        if(payLimit == null){
        	//若对应时间和默认的限制数据都查不到，则返回默认条数
        	BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.HF_BATCH_DF_MAX_SIZE);
            Integer itemNum = sys != null ? Integer.valueOf(sys.getConfValue()) : Constants.DEP_REQUEST_ITEM_ACCOUNT;
            withdraw2DepRepayCardReq = getWithdraw2DepRepayCardReqByNum(returnFlag, itemNum);
            log.info("=======未查询到代付限制=============");
    		return withdraw2DepRepayCardReq;
        }else{
        	//校验查的数据如果无对应限制类型和数据，则使用默认条数
        	if(payLimit.getLimitType() == null || payLimit.getLimitVaule() == null){
        		BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.HF_BATCH_DF_MAX_SIZE);
                Integer itemNum = sys != null ? Integer.valueOf(sys.getConfValue()) : Constants.DEP_REQUEST_ITEM_ACCOUNT;
                withdraw2DepRepayCardReq = getWithdraw2DepRepayCardReqByNum(returnFlag, itemNum);
                
        		return withdraw2DepRepayCardReq;
        	}
        	if(payLimit.getLimitType().equals(Constants.PAY_LIMIT_TYPE_NUMBER)){
        		//限制为条数
        		withdraw2DepRepayCardReq = getWithdraw2DepRepayCardReqByNum(returnFlag, payLimit.getLimitVaule());
        	}else{
        		//限制为金额，plan_total相加>limit_value值时，排除该条，跳出循环
        		BsSysConfig sys = sysMapper.selectByPrimaryKey(Constants.HF_BATCH_DF_MAX_SIZE);
                Integer itemNum = sys != null ? Integer.valueOf(sys.getConfValue()) : Constants.DEP_REQUEST_ITEM_ACCOUNT;
        		List<LnDepositionRepaySchedule> repaySchedulesDFPending = lnDepositionRepayScheduleMapper.getLimitDesList(returnFlag, itemNum);
                if (CollectionUtils.isNotEmpty(repaySchedulesDFPending)) {
                	List<DepRepaySchedule> depRepaySchedulesDFPending = new ArrayList<DepRepaySchedule>();
                    Double amountDF = 0d;
                    for (LnDepositionRepaySchedule repaySchedule : repaySchedulesDFPending) {
                        DepRepaySchedule repayScheduleDFPendingItem = new DepRepaySchedule();
                        amountDF = MoneyUtil.add(amountDF, repaySchedule.getPlanTotal()).doubleValue();
                        if(amountDF.compareTo(payLimit.getLimitVaule().doubleValue()) > 0){
                        	if(repaySchedule.getId() == repaySchedulesDFPending.get(0).getId()){
                        		//列表中首笔，单笔超过金额限制，单笔进入
                        		repayScheduleDFPendingItem.setId(repaySchedule.getId());
                                depRepaySchedulesDFPending.add(repayScheduleDFPendingItem);
                                break;
                        	}else{
                        		amountDF = MoneyUtil.subtract(amountDF, repaySchedule.getPlanTotal()).doubleValue();
                        	}
                        	
                        	break;
                        }
                        repayScheduleDFPendingItem.setId(repaySchedule.getId());
                        depRepaySchedulesDFPending.add(repayScheduleDFPendingItem);
                    }
                    withdraw2DepRepayCardReq.setDepRepaySchedules(depRepaySchedulesDFPending);
                    withdraw2DepRepayCardReq.setAmount(MoneyUtil.defaultRound(amountDF).doubleValue());
                }
                return withdraw2DepRepayCardReq;
        	}
        }
        
        
		return withdraw2DepRepayCardReq;
	}

    /**
     * 按照条数和代付还款标识，获取应代付处理的数据
     * @author bianyatian
     * @param returnFlag
     * @param itemNum
     * @return
     */
	private Withdraw2DepRepayCardReq getWithdraw2DepRepayCardReqByNum(String returnFlag,
			Integer itemNum) {
		Withdraw2DepRepayCardReq withdraw2DepRepayCardReq = new Withdraw2DepRepayCardReq();
		List<LnDepositionRepaySchedule> repaySchedulesDFPending = lnDepositionRepayScheduleMapper.getLimitDesList(returnFlag, itemNum);
        if (CollectionUtils.isNotEmpty(repaySchedulesDFPending)) {
        	List<DepRepaySchedule> depRepaySchedulesDFPending = new ArrayList<DepRepaySchedule>();
            Double amountDF = 0d;
            for (LnDepositionRepaySchedule repaySchedule : repaySchedulesDFPending) {
                DepRepaySchedule repayScheduleDFPendingItem = new DepRepaySchedule();
                repayScheduleDFPendingItem.setId(repaySchedule.getId());
                depRepaySchedulesDFPending.add(repayScheduleDFPendingItem);
                amountDF = MoneyUtil.add(amountDF, repaySchedule.getPlanTotal()).doubleValue();
            }
            withdraw2DepRepayCardReq.setDepRepaySchedules(depRepaySchedulesDFPending);
            withdraw2DepRepayCardReq.setAmount(MoneyUtil.defaultRound(amountDF).doubleValue());
        }
		return withdraw2DepRepayCardReq;
	}

	public void executeSuc() throws Exception {
        log.info("恒丰线下还款成功流程串联（管道）开始");

        //代扣还款到借款人/代偿人账户
        String dk_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_DK);
        if(StringUtil.isNotEmpty(dk_flag)) {
            log.info("恒丰线下还款代扣还款上一批数据处理中");
            return;
        }
        try {
            jsClientDaoSupport.setString("ING", Constants.OFF_REPAY_DK);
            log.info("恒丰线下还款DK开始");
            List<LnDepositionRepaySchedule> repaySchedulesDF = lnDepositionRepayScheduleMapper.getLimitDesList(Constants.DEP_REPAY_RETURN_FLAG_DF_SUCC, DEP_REQUEST_ITEM_ACCOUNT);
            if(CollectionUtils.isEmpty(repaySchedulesDF)){
                log.info("不存在DF_SUCC的账单");
            }else{
                CutRepay2BorrowerReq cutRepay2BorrowerReq = new CutRepay2BorrowerReq();
                List<DepRepaySchedule> depRepaySchedulesDF = new ArrayList<DepRepaySchedule>();
                Double amountDF = 0d;
                for(LnDepositionRepaySchedule repaySchedule :repaySchedulesDF){
                    LnCompensateRelationExample exampleRel = new LnCompensateRelationExample();
                    exampleRel.createCriteria().andDepPlanIdEqualTo(repaySchedule.getId());
                    List<LnCompensateRelation> compensateRelations = lnCompensateRelationMapper.selectByExample(exampleRel);
                    DepRepaySchedule repayScheduleDFItem = new DepRepaySchedule();
                    if(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT.equals(repaySchedule.getRepayType())
                    		|| CollectionUtils.isNotEmpty(compensateRelations)){
                    	log.info("=====线下还款计划id："+repaySchedule.getId()+"，该笔为代偿还款，则代扣还款到代偿人账户====");
                        LnCompensateRelation compensateRelation = compensateRelations.get(0);
                        //代扣还款到代偿人账户
                        repayScheduleDFItem.setHfUserId(compensateRelation.getCompHfUserId());
                        repayScheduleDFItem.setRepayAmount(repaySchedule.getPlanTotal());
                        amountDF = MoneyUtil.add(amountDF, repaySchedule.getPlanTotal()).doubleValue();
                    }else{
                    	log.info("=====线下还款计划id："+repaySchedule.getId()+"，还款类型为："+repaySchedule.getRepayType()+"该笔为正常还款或逾期还款，则代扣还款到借款人账户====");
                        //代扣还款到借款人账户
                        LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                        if(lnUser != null){
                            repayScheduleDFItem.setHfUserId(lnUser.getHfUserId());
                            repayScheduleDFItem.setRepayAmount(repaySchedule.getPlanTotal());
                            amountDF = MoneyUtil.add(amountDF, repaySchedule.getPlanTotal()).doubleValue();
                        }
                    }
                    repayScheduleDFItem.setId(repaySchedule.getId());
                    depRepaySchedulesDF.add(repayScheduleDFItem);
                }
                cutRepay2BorrowerReq.setDepRepaySchedules(depRepaySchedulesDF);
                cutRepay2BorrowerReq.setAmount(MoneyUtil.defaultRound(amountDF).doubleValue());
                depOfflineRepayService.cutRepay2Borrower(cutRepay2BorrowerReq);
            }
            log.info("恒丰线下还款DK结束");
        }catch (Exception e){
            log.error("恒丰线下还款DK异常", e);
        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_DK);
        }

        //借款人/代偿人还款至标的,赞分期逾期还款 借款人->代偿人
        String repay_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_REPAY);
        if(StringUtil.isNotEmpty(repay_flag)) {
            log.info("恒丰线下还款还款到标的上一批数据处理中");
            return;
        }
        try {
            jsClientDaoSupport.setString("ING", Constants.OFF_REPAY_REPAY);
            log.info("恒丰线下还款REPAY开始");
            List<LnDepositionRepaySchedule> repaySchedulesDKs = lnDepositionRepayScheduleMapper.getLimitDesList(Constants.DEP_REPAY_RETURN_FLAG_DK_SUCC, DEP_REQUEST_ITEM_ACCOUNT);
            if(CollectionUtils.isEmpty(repaySchedulesDKs)){
                log.info("不存在DK_SUCC的账单");
            }else{
                Repay2DepTargetReq repay2DepTargetReq = new Repay2DepTargetReq();
                List<DepRepaySchedule> depRepaySchedulesDK = new ArrayList<DepRepaySchedule>();
                for(LnDepositionRepaySchedule repaySchedule: repaySchedulesDKs){
                    try{
                        //判断是否赞分期逾期还款
                        LnRepayScheduleExample exampleSche = new LnRepayScheduleExample();
                        exampleSche.createCriteria().andPartnerRepayIdEqualTo(repaySchedule.getPartnerRepayId()).andLoanIdEqualTo(repaySchedule.getLoanId());
                        List<LnRepaySchedule> repaySchedules = repayScheduleMapper.selectByExample(exampleSche);
                        if(CollectionUtils.isEmpty(repaySchedules)){
                            //告警
                            specialJnlService.warn4FailNoSMS(null, "未找到合作方账单编号" + repaySchedule.getPartnerRepayId() + "对应的账单", null, "还款账单不存在");
                            continue;
                        }
                        if(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_REPAY.equals(repaySchedule.getRepayType()) &&
                        		(repaySchedules.get(0).getStatus().equals(Constants.REPAY_SCHEDULE_STATUS_LATE_REPAIED)
                                && StringUtil.isNotEmpty(repaySchedule.getDkOrderNo()))){
                        	log.info("===========线下还款计划id："+repaySchedule.getId()+"：逾期还款到代偿人=========");
                            //赞分期逾期还款
                            LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                            if(lnUser != null){
                                BsSysConfig sys = null;
                                if( Constants.PROPERTY_SYMBOL_ZAN.equals(lnUser.getPartnerCode()) ) {
                                    sys = sysMapper.selectByPrimaryKey(Constants.ZAN_COMPENSATE_USER_ID);
                                } else if ( Constants.PROPERTY_SYMBOL_ZSD.equals(lnUser.getPartnerCode()) ) {
                                    sys = sysMapper.selectByPrimaryKey(Constants.ZSD_COMPENSATE_USER_ID);
                                }
                                if(sys != null){
                                    if(StringUtil.isNotEmpty(sys.getConfValue())){
                                        BsUser compUser = bsUserMapper.selectByPrimaryKey(Integer.valueOf(sys.getConfValue()));
                                        BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
                                        extExample.createCriteria().andUserIdEqualTo(compUser.getId());
                                        List<BsHfbankUserExt> bsHfbankUserExts= bsHfbankUserExtMapper.selectByExample(extExample);
                                        if(CollectionUtils.isEmpty(bsHfbankUserExts)){
                                            specialJnlService.warn4FailNoSMS(null, "未找到bsUserId为" + compUser.getId() + "的用户扩展表信息", null, "代偿人扩展表信息不存在");
                                            continue;
                                        }
                                        BsHfbankUserExt bsHfbankUserExt = bsHfbankUserExts.get(0);
                                        BsSubAccount bsSubAccount = bsSubAccountMapper.selectDepJSHSubAccountByUserId(Integer.valueOf(sys.getConfValue()));
                                        LnSubAccountExample example = new LnSubAccountExample();
                                        example.createCriteria().andLnUserIdEqualTo(repaySchedule.getLnUserId()).andAccountTypeEqualTo(Constants.PRODUCT_TYPE_DEP_JSH);
                                        List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(example);
                                        if(CollectionUtils.isNotEmpty(lnSubAccounts)){
                                            repayPaymentService.overdueRepay2Compensate(repaySchedule,lnUser.getHfUserId(),bsHfbankUserExt.getHfUserId(),lnSubAccounts.get(0).getId(),bsSubAccount.getId());
                                        }else {
                                            specialJnlService.warn4FailNoSMS(null, "未找到lnUserId为" + repaySchedule.getLnUserId() + "的DEP_JSH子账户信息", null, "借款子账户信息不存在");
                                        }
                                    }else{
                                        specialJnlService.warn4FailNoSMS(null, Constants.ZAN_COMPENSATE_USER_ID + "value值不存在", null, "配置表数据不存在");
                                    }
                                }else{
                                    specialJnlService.warn4FailNoSMS(null, "未找到" + Constants.ZAN_COMPENSATE_USER_ID, null, "用户信息不存在");
                                }
                            }else {
                                specialJnlService.warn4FailNoSMS(null, "未找到lnUserId为" + repaySchedule.getLnUserId() + "的对应记录", null, "用户信息不存在");
                            }
                        }else {
                            //判断是否存在代偿记录
                            LnCompensateRelationExample exampleRel = new LnCompensateRelationExample();
                            exampleRel.createCriteria().andDepPlanIdEqualTo(repaySchedule.getId());
                            List<LnCompensateRelation> compensateRelations = lnCompensateRelationMapper.selectByExample(exampleRel);
                            //借款服务费获取
                            Double repayFee = 0d;
                            LnDepositionRepayScheduleDetailExample detailExample = new LnDepositionRepayScheduleDetailExample();
                            detailExample.createCriteria().andPlanIdEqualTo(repaySchedule.getId())
                            	.andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
                            List<LnDepositionRepayScheduleDetail> detailList = lnDepositionRepayScheduleDetailMapper.selectByExample(detailExample);
                            if(CollectionUtils.isNotEmpty(detailList)){
                            	repayFee = detailList.get(0).getPlanAmount();
                            }
                            
                            if(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT.equals(repaySchedule.getRepayType())
                            		&& CollectionUtils.isNotEmpty(compensateRelations)){
                            	log.info("===========线下还款计划id："+repaySchedule.getId()+"：代偿还款到标的=========");
                                //代偿人还款到标的
                                LnCompensateRelation compensateRelation = compensateRelations.get(0);
                                DepRepaySchedule repayScheduleComp = new DepRepaySchedule();
                                LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                                repayScheduleComp.setLnHfUserId(lnUser.getHfUserId());
                                repayScheduleComp.setHfUserId(compensateRelation.getCompHfUserId());
                                repayScheduleComp.setRepayAmount(MoneyUtil.subtract(repaySchedule.getPlanTotal(), repayFee).doubleValue());
                                repayScheduleComp.setId(repaySchedule.getId());
                                LnDepositionTargetExample exampleTar = new LnDepositionTargetExample();
                                exampleTar.createCriteria().andLoanIdEqualTo(repaySchedule.getLoanId());
                                List<LnDepositionTarget> targets = lnDepositionTargetMapper.selectByExample(exampleTar);
                                if(CollectionUtils.isEmpty(targets)){
                                    specialJnlService.warn4FailNoSMS(null, "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的" ,null, "标的不存在");
                                }
                                repayScheduleComp.setTargetId(targets.get(0).getId());
                                repayScheduleComp.setPlanDate(repaySchedule.getPlanDate());
                                repayScheduleComp.setPlanTotal(repaySchedule.getPlanTotal());
                                repayScheduleComp.setBsUserId(compensateRelation.getCompUserId());
                                repayScheduleComp.setRepayFee(repayFee);
                                repayScheduleComp.setPartnerEnum(PartnerEnum.getEnumByCode(lnUser.getPartnerCode()));
                                depOfflineRepayService.compRepay2DepTarget(repayScheduleComp);

                            }else{
                                //借款人还款到标的
                                DepRepaySchedule repayScheduleDKItem = new DepRepaySchedule();
                                LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                                if(lnUser != null){
                                    repayScheduleDKItem.setId(repaySchedule.getId());
                                    repayScheduleDKItem.setPlanTotal(repaySchedule.getPlanTotal());
                                    repayScheduleDKItem.setPlanDate(repaySchedule.getPlanDate());
                                    repayScheduleDKItem.setHfUserId(lnUser.getHfUserId());
                                    repayScheduleDKItem.setRepayAmount(MoneyUtil.subtract(repaySchedule.getPlanTotal(),repayFee).doubleValue());
                                    repayScheduleDKItem.setRepayFee(repayFee);
                                    LnDepositionTargetExample exampleTar = new LnDepositionTargetExample();
                                    exampleTar.createCriteria().andLoanIdEqualTo(repaySchedule.getLoanId());
                                    List<LnDepositionTarget> targets = lnDepositionTargetMapper.selectByExample(exampleTar);
                                    if(CollectionUtils.isEmpty(targets)){
                                        specialJnlService.warn4FailNoSMS(null, "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的" ,null, "标的不存在");
                                        continue;
                                    }
                                    repayScheduleDKItem.setTargetId(targets.get(0).getId());
                                    depRepaySchedulesDK.add(repayScheduleDKItem);
                                }
                            }
                        }
                    }catch (Exception e){
                        specialJnlService.warn4FailNoSMS(null, "恒丰线下还款REPAY,depRepayScheduleId:"+repaySchedule.getId()+"异常" ,null, "恒丰线下还款REAYP异常");
                        log.error("恒丰线下还款REAYP单条异常",e);
                    }
                }
                if(CollectionUtils.isNotEmpty(depRepaySchedulesDK)){
                    repay2DepTargetReq.setDepRepaySchedules(depRepaySchedulesDK);
                    depOfflineRepayService.repay2DepTarget(repay2DepTargetReq);
                }
            }

            log.info("恒丰线下还款REPAY结束");
        }catch (Exception e){
            log.error("恒丰线下还款REPAY异常", e);

        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_REPAY);
        }

        //标的还款至投资人账户
        String return_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_RETURN);
        if(StringUtil.isNotEmpty(return_flag)) {
            log.info("恒丰线下还款标的还款上一批数据处理中");
            return;
        }
        try {
            jsClientDaoSupport.setString("ING", Constants.OFF_REPAY_RETURN);
            log.info("恒丰线下还款RETURN开始");
            List<DepLimitRepaySchedule> repaySchedulesRPs = lnDepositionRepayScheduleMapper.getRetLimitDesList(Constants.DEP_REPAY_RETURN_FLAG_REPAY_SUCC, DEP_REQUEST_ITEM_ACCOUNT);
            if(CollectionUtils.isEmpty(repaySchedulesRPs)){
                log.info("不存在REPAY_SUCC的账单");
            }else{
                for(DepLimitRepaySchedule repaySchedule :repaySchedulesRPs){
                    //赞分期回款需要根据计划日期回款
                    Repay2InvestorReq repay2InvestorReq = new Repay2InvestorReq();
                    //借款服务费获取
                    Double repayFee = 0d;
                    LnDepositionRepayScheduleDetailExample detailExample = new LnDepositionRepayScheduleDetailExample();
                    detailExample.createCriteria().andPlanIdEqualTo(repaySchedule.getId())
                            .andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
                    List<LnDepositionRepayScheduleDetail> detailList = lnDepositionRepayScheduleDetailMapper.selectByExample(detailExample);
                    if(CollectionUtils.isNotEmpty(detailList)){
                        repayFee = detailList.get(0).getPlanAmount();
                    }

                    DepRepaySchedule repayScheduleRPItem = new DepRepaySchedule();
                    repayScheduleRPItem.setRepayAmount(MoneyUtil.subtract(repaySchedule.getPlanTotal(),repayFee).doubleValue());
                    repayScheduleRPItem.setRepayFee(0d);
                    LnDepositionTargetExample exampleRP = new LnDepositionTargetExample();
                    exampleRP.createCriteria().andLoanIdEqualTo(repaySchedule.getLoanId());
                    List<LnDepositionTarget> depositionTargetsRP = lnDepositionTargetMapper.selectByExample(exampleRP);
                    if(CollectionUtils.isEmpty(depositionTargetsRP)){
                        specialJnlService.warn4FailNoSMS(null, "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的" ,null, "标的不存在");
                        continue;
                    }
                    repayScheduleRPItem.setTargetId(depositionTargetsRP.get(0).getId());
                    repayScheduleRPItem.setLoanId(repaySchedule.getLoanId());
                    repayScheduleRPItem.setId(repaySchedule.getId());
                    repayScheduleRPItem.setSerialId(repaySchedule.getSerialId());
                    repayScheduleRPItem.setPartnerEnum(PartnerEnum.getEnumByCode(repaySchedule.getPartnerCode()));
                    repay2InvestorReq.setDepRepaySchedule(repayScheduleRPItem);
                    depOfflineRepayService.repay2Investor(repay2InvestorReq);
                }
            }

            log.info("恒丰线下还款RETURN结束");
        }catch (Exception e){
            log.info("恒丰线下还款RETURN异常", e);
        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_RETURN);
        }
        log.info("恒丰线下还款成功流程串联（管道）结束");
    }

    public void executeErr() throws Exception {
        log.info("恒丰线下还款失败流程串联（管道）开始");

        //代扣还款到借款人/代偿人失败重发
        String dk_repeat_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_REPEAT_DK);
        if(StringUtil.isNotEmpty(dk_repeat_flag)) {
            log.info("恒丰线下还款代扣还款上一批数据处理中");
            return;
        }
        try{
            jsClientDaoSupport.setString("ING", Constants.OFF_REPAY_REPEAT_DK);
            log.info("恒丰线下还款DK重发开始");
            List<LnDepositionRepaySchedule> repaySchedulesFailDKs = lnDepositionRepayScheduleMapper.getLimitDesList(Constants.DEP_REPAY_RETURN_FLAG_DK_FAIL, DEP_REQUEST_ITEM_ACCOUNT);
            if(CollectionUtils.isEmpty(repaySchedulesFailDKs)){
                log.info("不存在DK_FAIL的账单");
            }else{
                CutRepay2BorrowerReq cutRepay2BorrowerReq = new CutRepay2BorrowerReq();
                List<DepRepaySchedule> depRepaySchedulesDKs = new ArrayList<DepRepaySchedule>();
                Double amountDKFail = 0d;
                for(LnDepositionRepaySchedule repaySchedule :repaySchedulesFailDKs){
                    LnCompensateRelationExample exampleRel = new LnCompensateRelationExample();
                    exampleRel.createCriteria().andDepPlanIdEqualTo(repaySchedule.getId());
                    List<LnCompensateRelation> compensateRelations = lnCompensateRelationMapper.selectByExample(exampleRel);
                    DepRepaySchedule repayScheduleItem = new DepRepaySchedule();
                    if(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT.equals(repaySchedule.getRepayType())
                            || CollectionUtils.isNotEmpty(compensateRelations)){
                        log.info("=====线下还款计划id："+repaySchedule.getId()+"，该笔为代偿还款，则代扣还款到代偿人账户====");
                        LnCompensateRelation compensateRelation = compensateRelations.get(0);
                        //代扣还款到代偿人账户
                        repayScheduleItem.setHfUserId(compensateRelation.getCompHfUserId());
                        repayScheduleItem.setRepayAmount(compensateRelation.getAmount());
                        amountDKFail = MoneyUtil.add(amountDKFail, compensateRelation.getAmount()).doubleValue();
                    }else{
                        //代扣还款到借款人账户
                        LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                        if(lnUser != null){
                            repayScheduleItem.setHfUserId(lnUser.getHfUserId());
                            repayScheduleItem.setRepayAmount(repaySchedule.getPlanTotal());
                            amountDKFail = MoneyUtil.add(amountDKFail,repaySchedule.getPlanTotal()).doubleValue();
                        }
                    }
                    repayScheduleItem.setId(repaySchedule.getId());
                    depRepaySchedulesDKs.add(repayScheduleItem);
                }
                cutRepay2BorrowerReq.setDepRepaySchedules(depRepaySchedulesDKs);
                cutRepay2BorrowerReq.setAmount(amountDKFail);
                depOfflineRepayService.cutRepay2Borrower(cutRepay2BorrowerReq);
            }
            log.info("恒丰线下还款DK重发结束");
        }catch (Exception e){
            log.info("恒丰线下还款DK重发异常", e);
        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_REPEAT_DK);
        }

        //借款人/代偿人还款至标的,赞分期逾期还款 借款人->代偿人（失败重发）
        String repay_repeat_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_REPEAT_REPAY);
        if(StringUtil.isNotEmpty(repay_repeat_flag)) {
            log.info("恒丰线下还款还款到标的上一批数据处理中");
            return;
        }
        try {
            jsClientDaoSupport.setString("ING", Constants.OFF_REPAY_REPEAT_REPAY);
            log.info("恒丰线下还款REPAY重发开始");
            List<LnDepositionRepaySchedule> repaySchedulesRP = lnDepositionRepayScheduleMapper.getLimitDesList(Constants.DEP_REPAY_RETURN_FLAG_REPAY_FAIL, DEP_REQUEST_ITEM_ACCOUNT);
            if(CollectionUtils.isEmpty(repaySchedulesRP)){
                log.info("不存在REPAY_FAIL的账单");
            }else{
                Repay2DepTargetReq repay2DepTargetReq = new Repay2DepTargetReq();
                List<DepRepaySchedule> depRepaySchedulesRP = new ArrayList<>();
                for(LnDepositionRepaySchedule repaySchedule: repaySchedulesRP){
                    try{
                        //判断是否赞分期逾期还款
                        LnRepayScheduleExample exampleSche = new LnRepayScheduleExample();
                        exampleSche.createCriteria().andPartnerRepayIdEqualTo(repaySchedule.getPartnerRepayId()).andLoanIdEqualTo(repaySchedule.getLoanId());
                        List<LnRepaySchedule> repaySchedules = repayScheduleMapper.selectByExample(exampleSche);
                        if(CollectionUtils.isEmpty(repaySchedules)){
                            //告警
                            specialJnlService.warn4FailNoSMS(null, "未找到合作方账单编号" + repaySchedule.getPartnerRepayId() + "对应的账单", null, "还款账单不存在");
                            continue;
                        }
                        if(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_REPAY.equals(repaySchedule.getRepayType()) && 
                        		(repaySchedules.get(0).getStatus().equals(Constants.REPAY_SCHEDULE_STATUS_LATE_REPAIED) &&
                                StringUtil.isNotEmpty(repaySchedule.getDkOrderNo()))){
                        	log.info("===========线下还款计划id："+repaySchedule.getId()+"：逾期还款=========");
                            //赞分期逾期还款
                            LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                            if(lnUser != null){
                                BsSysConfig sys = null;
                                if( Constants.PROPERTY_SYMBOL_ZAN.equals(lnUser.getPartnerCode()) ) {
                                    sys = sysMapper.selectByPrimaryKey(Constants.ZAN_COMPENSATE_USER_ID);
                                } else if( Constants.PROPERTY_SYMBOL_ZSD.equals(lnUser.getPartnerCode()) ) {
                                    sys = sysMapper.selectByPrimaryKey(Constants.ZSD_COMPENSATE_USER_ID);
                                }
                                if(sys != null){
                                    if(StringUtil.isNotEmpty(sys.getConfValue())){
                                        BsUser compUser = bsUserMapper.selectByPrimaryKey(Integer.valueOf(sys.getConfValue()));
                                        BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
                                        extExample.createCriteria().andUserIdEqualTo(compUser.getId());
                                        List<BsHfbankUserExt> bsHfbankUserExts= bsHfbankUserExtMapper.selectByExample(extExample);
                                        if(CollectionUtils.isEmpty(bsHfbankUserExts)){
                                            specialJnlService.warn4FailNoSMS(null, "未找到bsUserId为" + compUser.getId() + "的用户扩展表信息", null, "代偿人扩展表信息不存在");
                                            continue;
                                        }
                                        BsHfbankUserExt bsHfbankUserExt = bsHfbankUserExts.get(0);
                                        BsSubAccount bsSubAccount = bsSubAccountMapper.selectDepJSHSubAccountByUserId(Integer.valueOf(sys.getConfValue()));
                                        LnSubAccountExample example = new LnSubAccountExample();
                                        example.createCriteria().andLnUserIdEqualTo(repaySchedule.getLnUserId()).andAccountTypeEqualTo(Constants.PRODUCT_TYPE_DEP_JSH);
                                        List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(example);
                                        if(CollectionUtils.isNotEmpty(lnSubAccounts)){
                                            repayPaymentService.overdueRepay2Compensate(repaySchedule,lnUser.getHfUserId(),bsHfbankUserExt.getHfUserId(),lnSubAccounts.get(0).getId(),bsSubAccount.getId());
                                        }else {
                                            specialJnlService.warn4FailNoSMS(null, "未找到lnUserId为" + repaySchedule.getLnUserId() + "的DEP_JSH子账户信息", null, "借款子账户信息不存在");
                                        }
                                    }else{
                                        specialJnlService.warn4FailNoSMS(null, Constants.ZAN_COMPENSATE_USER_ID + "value值不存在", null, "配置表数据不存在");
                                    }
                                }else{
                                    specialJnlService.warn4FailNoSMS(null, "未找到" + Constants.ZAN_COMPENSATE_USER_ID, null, "配置表数据不存在");
                                }
                            }else {
                                specialJnlService.warn4FailNoSMS(null, "未找到lnUserId为" + repaySchedule.getLnUserId() + "的对应记录", null, "用户信息不存在");
                            }
                        }else {
                        	//借款服务费获取
                            Double repayFee = 0d;
                            LnDepositionRepayScheduleDetailExample detailExample = new LnDepositionRepayScheduleDetailExample();
                            detailExample.createCriteria().andPlanIdEqualTo(repaySchedule.getId())
                            	.andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
                            List<LnDepositionRepayScheduleDetail> detailList = lnDepositionRepayScheduleDetailMapper.selectByExample(detailExample);
                            if(CollectionUtils.isNotEmpty(detailList)){
                            	repayFee = detailList.get(0).getPlanAmount();
                            }
                            //判断是否存在代偿记录
                            LnCompensateRelationExample exampleRel = new LnCompensateRelationExample();
                            exampleRel.createCriteria().andDepPlanIdEqualTo(repaySchedule.getId());
                            List<LnCompensateRelation> compensateRelations = lnCompensateRelationMapper.selectByExample(exampleRel);
                            if(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_LATE_NOT.equals(repaySchedule.getRepayType())
                            		&& CollectionUtils.isNotEmpty(compensateRelations)){
                            	log.info("===========线下还款计划id："+repaySchedule.getId()+"：代偿还款=========");
                                //代偿人还款到标的
                                LnCompensateRelation compensateRelation = compensateRelations.get(0);
                                DepRepaySchedule repayScheduleComp = new DepRepaySchedule();
                                LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                                repayScheduleComp.setLnHfUserId(lnUser.getHfUserId());
                                repayScheduleComp.setHfUserId(compensateRelation.getCompHfUserId());
                                repayScheduleComp.setRepayAmount(MoneyUtil.subtract(repaySchedule.getPlanTotal(), repayFee).doubleValue());
                                repayScheduleComp.setId(repaySchedule.getId());
                                LnDepositionTargetExample exampleTar = new LnDepositionTargetExample();
                                exampleTar.createCriteria().andLoanIdEqualTo(repaySchedule.getLoanId());
                                List<LnDepositionTarget> targets = lnDepositionTargetMapper.selectByExample(exampleTar);
                                if(CollectionUtils.isEmpty(targets)){
                                    specialJnlService.warn4FailNoSMS(null, "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的" ,null, "标的不存在");
                                    continue;
                                }
                                repayScheduleComp.setTargetId(targets.get(0).getId());
                                repayScheduleComp.setPlanDate(repaySchedule.getPlanDate());
                                repayScheduleComp.setPlanTotal(repaySchedule.getPlanTotal());
                                repayScheduleComp.setBsUserId(compensateRelation.getCompUserId());
                                repayScheduleComp.setRepayFee(repayFee);
                                repayScheduleComp.setPartnerEnum(PartnerEnum.getEnumByCode(lnUser.getPartnerCode()));
                                depOfflineRepayService.compRepay2DepTarget(repayScheduleComp);
                            }else{
                                //借款人还款到标的
                                //DepRepaySchedule repayScheduleDKItem = new DepRepaySchedule();
                                LnUser lnUser = lnUserMapper.selectByPrimaryKey(repaySchedule.getLnUserId());
                                if(lnUser != null){
                                    DepRepaySchedule repayScheduleRPItem = new DepRepaySchedule();
                                    repayScheduleRPItem.setId(repaySchedule.getId());
                                    repayScheduleRPItem.setPlanTotal(repaySchedule.getPlanTotal());
                                    repayScheduleRPItem.setPlanDate(repaySchedule.getPlanDate());
                                    repayScheduleRPItem.setHfUserId(lnUser.getHfUserId());
                                    repayScheduleRPItem.setRepayAmount(MoneyUtil.subtract(repaySchedule.getPlanTotal(), repayFee).doubleValue());
                                    repayScheduleRPItem.setRepayFee(repayFee);
                                    LnDepositionTargetExample exampleTar = new LnDepositionTargetExample();
                                    exampleTar.createCriteria().andLoanIdEqualTo(repaySchedule.getLoanId());
                                    List<LnDepositionTarget> targets = lnDepositionTargetMapper.selectByExample(exampleTar);
                                    if(CollectionUtils.isEmpty(targets)){
                                        specialJnlService.warn4FailNoSMS(null, "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的" ,null, "标的不存在");
                                        continue;
                                    }
                                    repayScheduleRPItem.setTargetId(targets.get(0).getId());
                                    depRepaySchedulesRP.add(repayScheduleRPItem);
                                }
                            }
                        }
                    }catch (Exception e){
                        specialJnlService.warn4FailNoSMS(null, "恒丰线下还款REPAY,depRepayScheduleId:"+repaySchedule.getId()+"异常" ,null, "恒丰线下还款REAYP异常");
                        log.error("恒丰线下还款REAYP单条异常",e);
                    }
                }
                if(CollectionUtils.isNotEmpty(depRepaySchedulesRP)){
                    repay2DepTargetReq.setDepRepaySchedules(depRepaySchedulesRP);
                    depOfflineRepayService.repay2DepTarget(repay2DepTargetReq);
                }
            }
            log.info("恒丰线下还款REPAY重发结束");

        }catch (Exception e){
            log.info("恒丰线下还款REPAY重发异常", e);
        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_REPEAT_REPAY);
        }

        //标的还款至投资人账户失败重发
        String return_repeat_flag = jsClientDaoSupport.getString(Constants.OFF_REPAY_REPEAT_RETURN);
        if(StringUtil.isNotEmpty(return_repeat_flag)) {
            log.info("恒丰线下还款标的还款上一批数据处理中");
            return;
        }
        try {
            jsClientDaoSupport.setString("ING", Constants.OFF_REPAY_REPEAT_RETURN);
            log.info("恒丰线下还款RETURN重发开始");
            List<DepLimitRepaySchedule> repaySchedulesRT = lnDepositionRepayScheduleMapper.getRetLimitDesList(Constants.DEP_REPAY_RETURN_FLAG_RETURN_FAIL, DEP_REQUEST_ITEM_ACCOUNT);
            if(CollectionUtils.isEmpty(repaySchedulesRT)){
                log.info("不存在RETURN_FAIL的账单");
            }else{
                for(DepLimitRepaySchedule repaySchedule :repaySchedulesRT){
                    Repay2InvestorReq repay2InvestorReq = new Repay2InvestorReq();
                    //借款服务费获取
                    Double repayFee = 0d;
                    LnDepositionRepayScheduleDetailExample detailExample = new LnDepositionRepayScheduleDetailExample();
                    detailExample.createCriteria().andPlanIdEqualTo(repaySchedule.getId())
                    	.andSubjectCodeEqualTo(LoanSubjects.SUBJECT_CODE_LOAN_SERVICE_FEE.getCode());
                    List<LnDepositionRepayScheduleDetail> detailList = lnDepositionRepayScheduleDetailMapper.selectByExample(detailExample);
                    if(CollectionUtils.isNotEmpty(detailList)){
                    	repayFee = detailList.get(0).getPlanAmount();
                    }
                    
                    DepRepaySchedule repayScheduleRPItem = new DepRepaySchedule();
                    repayScheduleRPItem.setRepayAmount(MoneyUtil.subtract(repaySchedule.getPlanTotal(),repayFee).doubleValue());
                    repayScheduleRPItem.setRepayFee(0d);
                    LnDepositionTargetExample exampleRP = new LnDepositionTargetExample();
                    exampleRP.createCriteria().andLoanIdEqualTo(repaySchedule.getLoanId());
                    List<LnDepositionTarget> depositionTargetsRP = lnDepositionTargetMapper.selectByExample(exampleRP);
                    if(CollectionUtils.isEmpty(depositionTargetsRP)){
                        specialJnlService.warn4FailNoSMS(null, "未找到借款编号"+repaySchedule.getLoanId()+"对应的标的" ,null, "标的不存在");
                        continue;
                    }
                    repayScheduleRPItem.setTargetId(depositionTargetsRP.get(0).getId());
                    repayScheduleRPItem.setLoanId(repaySchedule.getLoanId());
                    repayScheduleRPItem.setId(repaySchedule.getId());
                    repayScheduleRPItem.setSerialId(repaySchedule.getSerialId());
                    repayScheduleRPItem.setPartnerEnum(PartnerEnum.getEnumByCode(repaySchedule.getPartnerCode()));
                    repay2InvestorReq.setDepRepaySchedule(repayScheduleRPItem);
                    depOfflineRepayService.repay2Investor(repay2InvestorReq);
                }
            }
            log.info("恒丰线下还款RETURN重发结束");
        }catch (Exception e){
            log.error("恒丰线下还款RETURN重发异常", e);
        }finally {
            jsClientDaoSupport.setString("",Constants.OFF_REPAY_REPEAT_RETURN);
        }
        log.info("恒丰线下还款失败流程串联（管道）结束");
    }
}
