package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.loan.model.RevenueSettleResultInfo;
import com.pinting.business.accounting.loan.service.DepFixedNotifyPartnerService;
import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleAccountService;
import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleService;
import com.pinting.business.accounting.loan.service.impl.process.RevenueProcess;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.coreflow.core.enums.BusinessTypeEnum;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.LnAccountFillDetailVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.model.vo.RevenueSettleDetailVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.*;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.*;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GReqMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.hessian.message.loan7.B2GResMsg_DepLoan7Notice_RevenueSettle;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.loan.DafyNoticeService;

import com.pinting.gateway.out.service.loan7.DepLoan7NoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.math.BigDecimal;
import java.util.*;

/**
 * Author:      cyb
 * Date:        2017/4/7
 * Description: 营收结算及通知（还款营收、砍头息）
 */
@Service
public class DepFixedRevenueSettleServiceImpl implements DepFixedRevenueSettleService {

    private final Logger logger = LoggerFactory.getLogger(DepFixedRevenueSettleServiceImpl.class);

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private LnRepeatRepayRecordMapper lnRepeatRepayRecordMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private BsRevenueTransDetailMapper revenueTransDetailMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private DafyNoticeService dafyNoticeService;
    @Autowired
    private BsDailySettlementFileMapper bsDailySettlementFileMapper;
    @Autowired
    private LnAccountFillDetailMapper lnAccountFillDetailMapper;
    @Autowired
    private LnAccountFillMapper lnAccountFillMapper;
    @Autowired
    private BsRevenueTransRecordMapper revenueTransRecordMapper;
    @Autowired
    private DepFixedNotifyPartnerService depFixedNotifyPartnerService;
    @Autowired
    private DepFixedRevenueSettleAccountService depFixedRevenueSettleAccountService;
    @Autowired
    private DepLoan7NoticeService depLoan7NoticeService;
    @Autowired
    private BsSpecialJnlMapper bsSpecialJnlMapper;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    
    @Override
    public void revenueSettle() {
        //砍头息昨日总计
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);
        /*try {
            Double zsdHeadFeeAmount = revenueTransDetailMapper.sumFeeAmountDaily(yestday, Constants.PROPERTY_SYMBOL_ZSD, Constants.REVENUE_TYPE_HEAD_FEE_INCOME);
            if(zsdHeadFeeAmount > 0){
                //向赞时贷转账
                transToZsd(zsdHeadFeeAmount, Constants.TRANS_SYS_ZSD_LOAN_FEE, Constants.TRANS_TYPE_LOAN_FEE_INSTR, null);
            }
            logger.info("==============【赞时贷】砍头息"+zsdHeadFeeAmount+"===================");
        }catch (Exception e){
            logger.error("砍头息结算异常", e);
        }*/

        //还款营收昨日总计
        Double revenueAmount = revenueTransDetailMapper.sumFeeAmountDaily(yestday,Constants.PROPERTY_SYMBOL_YUN_DAI_SELF,Constants.REVENUE_TYPE_REPAY_INCOME);
        //统计ln_account_fill_detail昨日已补账总和
        Double fillAmount = lnAccountFillDetailMapper.sumAmount(yestday, Constants.FILL_DETAIL_FILL_TYPE_INTEREST,
                Constants.FILL_DETAIL_FILL_STATUS_SUCCESS, Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
  
		/*云贷差额*/
        Double diffAmount = MoneyUtil.subtract(revenueAmount, fillAmount).doubleValue();
        logger.info("==================营收-补账的差额"+diffAmount+"================");
        
        if(diffAmount<0){
			/*前一日已补账详细列表*/
            List<LnAccountFillDetailVO> detailList = lnAccountFillDetailMapper.getList(yestday, Constants.FILL_DETAIL_FILL_TYPE_INTEREST,
                    Constants.FILL_DETAIL_FILL_STATUS_SUCCESS, Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
			/*校验是否已有非失败的前一日合作方补账记录*/
            LnAccountFillExample example = new LnAccountFillExample();
            example.createCriteria().andPartnerCodeEqualTo(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF)
                    .andStatusNotEqualTo(Constants.FILL_FILL_STATUS_FAIL).andFillDateEqualTo(yestday);
            List<LnAccountFill> list = lnAccountFillMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(list) && CollectionUtils.isNotEmpty(detailList)){
                logger.info("==================营收-补账的差额小于0，记录补账记录，发起云贷通知================");
                String orderNo= Util.generateSysOrderNo("YWF");//待补账通知订单号
                LnAccountFill lnAccountFill = new LnAccountFill();
                lnAccountFill.setAmount(Math.abs(diffAmount));
                lnAccountFill.setApplyTime(new Date());
                lnAccountFill.setCreateTime(new Date());
                lnAccountFill.setFillDate(yestday);
                lnAccountFill.setFillType(Constants.FILL_FILL_TYPE_INTEREST);
                lnAccountFill.setOrderNo(orderNo);
                lnAccountFill.setPartnerCode(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
                lnAccountFill.setStatus(Constants.FILL_FILL_STATUS_INIT);
                lnAccountFill.setUpdateTime(new Date());
				/*合作方补账记录表新增数据*/
                lnAccountFillMapper.insertSelective(lnAccountFill);
                logger.info("lnAccountFill.getId() ===> {}", lnAccountFill.getId());
				/*4、5、6、7*/
                depFixedNotifyPartnerService.notifyWaitFill2YunDai(lnAccountFill, detailList);
            }else{
                logger.info("==================营收-补账的差额小于0，且有非失败的前一日合作方补账记录或补账 明细记录列表为空，不操作================");
            }

            //发送告警短信,通知开发和产品人员
            specialJnlService.warnAppoint4Fail( diffAmount, "云贷【营收发生日】营收结算为负数，请及时联系合作方处理", null, "云贷营收结算", false, Constants.EMERGENCY_MOBILE, Constants.PRODUCT_OPERATOR_MOBILE);
            
        }else if(diffAmount > 0){
            //向云贷转账
        	BsRevenueTransRecordExample example = new BsRevenueTransRecordExample();
            example.createCriteria().andSettleDateEqualTo(nowDate).andPayeeCodeEqualTo(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF)
            .andPayerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_BGW).andSettleTypeEqualTo(Constants.REVENUE_TYPE_REPAY_REVENUE)
            .andStatusEqualTo(Constants.REVENUE_TRANS_SUCCESS);
            List<BsRevenueTransRecord> revenueTransRecordList = revenueTransRecordMapper.selectByExample(example);
        	if (CollectionUtils.isEmpty(revenueTransRecordList)) {               		
        		transToYunDai(diffAmount,Constants.TRANS_SYS_YUN_REPAY_REVENUE,Constants.TRANS_TYPE_REPAY_REVENUE_INSTR,null);
        	} else {
        		logger.info("==============【云贷自主】还款营收已结算===================");
        	}
        }
        logger.info("==============【云贷自主】还款营收昨日"+revenueAmount+"===================");
    }

    @Override
    public void repayRepeatSettle() {
        //结算退还金额
        Double repayAmount = 0d;
        List<Integer> repeatRepayIds = new ArrayList<Integer>();
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);

        List<String> statusList = new ArrayList<String>();
        statusList.add(Constants.REPEAT_REPAY_STATUS_INIT);
        statusList.add(Constants.REPEAT_REPAY_STATUS_FAIL);
        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
        example.createCriteria().andSettleDateEqualTo(yestday).andPartnerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF).andStatusIn(statusList);
        List<LnRepeatRepayRecord> repeatRepayList = lnRepeatRepayRecordMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(repeatRepayList)){
        	for(LnRepeatRepayRecord lnRepeatRepayRecord: repeatRepayList){
                if(lnRepeatRepayRecord.getReturnAmount() != null){
                    repayAmount = MoneyUtil.add(repayAmount,lnRepeatRepayRecord.getReturnAmount()).doubleValue();
                    repeatRepayIds.add(lnRepeatRepayRecord.getId());
                }
            }
            logger.info("==============【云贷自主】重复还款"+repayAmount+"===================");
        }
        
        if(repayAmount > 0){
            transToYunDai(repayAmount,Constants.TRANS_SYS_YUN_REPEAT,Constants.TRANS_TYPE_REPEAT_INSTR,repeatRepayIds);
        }
    }

    @Override
    public void revenueSettleNotify(BsPayOrders order) {
        B2GReqMsg_DafyLoanNotice_RevenueSettle noticeRevenueSettle = new B2GReqMsg_DafyLoanNotice_RevenueSettle();

        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);

        //日结明细文件表生成
        BsDailySettlementFile file = new BsDailySettlementFile();

        file.setCreateTime(new Date());
        file.setPartnerCode(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF);
        file.setTargetDate(yestday);
        file.setUpdateTime(new Date());

        //生成营收结算明细文件
        String fileUrl = "";
        if(order.getTransType().equals(Constants.TRANS_SYS_YUN_REPEAT)){
            //重复还款
            fileUrl = revenueDetailGen(Constants.REVENUE_FILE_PREFIX_REPEAT, PartnerEnum.YUN_DAI_SELF);
            noticeRevenueSettle.setSettleType(Constants.REVENUE_TYPE_REPEAT);

            file.setBusinessType(Constants.BUSINESS_TYPE_REPEAT_REPAY_DETAIL);
            file.setNote("重复还款结算明细文件");
        }else if(order.getTransType().equals(Constants.TRANS_SYS_YUN_LOAN_FEE)){
            //砍头息
            fileUrl = revenueDetailGen(Constants.REVENUE_FILE_PREFIX_LOAN_FEE, PartnerEnum.YUN_DAI_SELF);
            noticeRevenueSettle.setSettleType(Constants.REVENUE_TYPE_LOAN_FEE);

            file.setBusinessType(Constants.BUSINESS_TYPE_HEAD_FEE_DETAIL);
            file.setNote("砍头息结算明细文件");
        }else if(order.getTransType().equals(Constants.TRANS_SYS_YUN_REPAY_REVENUE)){
            //还款营收
            fileUrl = revenueDetailGen(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE, PartnerEnum.YUN_DAI_SELF);
            noticeRevenueSettle.setSettleType(Constants.REVENUE_TYPE_REPAY_REVENUE);

            file.setBusinessType(Constants.BUSINESS_TYPE_REPAY_REVENUE_DETAIL);
            file.setNote("还款营收结算明细文件");
        }

        file.setFileAddress(fileUrl);
        bsDailySettlementFileMapper.insertSelective(file);

        noticeRevenueSettle.setOrderNo(order.getOrderNo());
        noticeRevenueSettle.setApplyTime(order.getCreateTime());
        noticeRevenueSettle.setFinishTime(order.getUpdateTime());
        noticeRevenueSettle.setAmount(order.getAmount());
        noticeRevenueSettle.setFileUrl(fileUrl.substring(fileUrl.indexOf("/ftp", 0)+4));

        B2GResMsg_DafyLoanNotice_RevenueSettle res = null;
        try {
            logger.info("============发起云贷通知===================");
            res = dafyNoticeService.noticeRevenueSettle(noticeRevenueSettle);

            if (res == null || !ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                logger.error("营收结算转账成功," + Constants.TRANS_TYPE_REPEAT_INSTR + "通知云贷失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //删除redis数据
        jsClientDaoSupport.deleteObjOfHashMap(order.getOrderNo(), "process");
    }

    @Override
    public void sevenRevenueSettleNotify(BsPayOrders order) {
        B2GReqMsg_DepLoan7Notice_RevenueSettle noticeRevenueSettle = new B2GReqMsg_DepLoan7Notice_RevenueSettle();

        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);

        //日结明细文件表生成
        BsDailySettlementFile file = new BsDailySettlementFile();

        file.setCreateTime(new Date());
        file.setPartnerCode(Constants.PROPERTY_SYMBOL_7_DAI_SELF);
        file.setTargetDate(yestday);
        file.setUpdateTime(new Date());

        //生成营收结算明细文件
        String fileUrl = "";
        if(order.getTransType().equals(Constants.TRANS_SYS_SEVEN_REPEAT)){
            //重复还款
            fileUrl = revenueDetailGen(Constants.REVENUE_FILE_PREFIX_REPEAT, PartnerEnum.SEVEN_DAI_SELF);
            noticeRevenueSettle.setSettleType(Constants.REVENUE_TYPE_REPEAT);

            file.setBusinessType(Constants.BUSINESS_TYPE_REPEAT_REPAY_DETAIL);
            file.setNote("重复还款结算明细文件");
        }else if(order.getTransType().equals(Constants.TRANS_SYS_SEVEN_LOAN_FEE)){
            //砍头息
            fileUrl = revenueDetailGen(Constants.REVENUE_FILE_PREFIX_LOAN_FEE, PartnerEnum.SEVEN_DAI_SELF);
            noticeRevenueSettle.setSettleType(Constants.REVENUE_TYPE_LOAN_FEE);

            file.setBusinessType(Constants.BUSINESS_TYPE_HEAD_FEE_DETAIL);
            file.setNote("砍头息结算明细文件");
        }else if(order.getTransType().equals(Constants.TRANS_SYS_SEVEN_REPAY_REVENUE)){
            //还款营收
            fileUrl = revenueDetailGen(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE, PartnerEnum.SEVEN_DAI_SELF);
            noticeRevenueSettle.setSettleType(Constants.REVENUE_TYPE_REPAY_REVENUE);

            file.setBusinessType(Constants.BUSINESS_TYPE_REPAY_REVENUE_DETAIL);
            file.setNote("还款营收结算明细文件");
        }

        file.setFileAddress(fileUrl);
        bsDailySettlementFileMapper.insertSelective(file);

        noticeRevenueSettle.setOrderNo(order.getOrderNo());
        noticeRevenueSettle.setApplyTime(order.getCreateTime());
        noticeRevenueSettle.setFinishTime(order.getUpdateTime());
        noticeRevenueSettle.setAmount(order.getAmount());
        noticeRevenueSettle.setFileUrl(fileUrl.substring(fileUrl.indexOf("/ftp", 0)+4));

        B2GResMsg_DepLoan7Notice_RevenueSettle res = null;
        try {
            logger.info("============发起7贷通知===================");
            res = depLoan7NoticeService.noticeRevenueSettle(noticeRevenueSettle);

            if (res == null || !ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                logger.error("营收结算转账成功," + Constants.TRANS_TYPE_REPEAT_INSTR + "通知7贷失败");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        //删除redis数据
        jsClientDaoSupport.deleteObjOfHashMap(order.getOrderNo(), "process");
    }

    /**
     * 营收转账公共方法
     * @param amount 转账金额
     * @param transType 订单交易类型
     * @param instr 订单交易类型说明
     */
    public void transToYunDai(Double amount,String transType,String instr,List<Integer> repeatRepayIds){
        String payOrderNo= Util.generateSysOrderNo("YTS");//转账云贷订单号
        //砍头息，还款营收记录一条bs_revenue_trans_record初始数据
        BsRevenueTransRecord revenueTransRecord = new BsRevenueTransRecord();
        if(!Constants.TRANS_SYS_YUN_REPEAT.equals(transType)){
            revenueTransRecord.setAmount(amount);
            revenueTransRecord.setCreateTime(new Date());
            revenueTransRecord.setUpdateTime(new Date());
            revenueTransRecord.setSettleDate(new Date());
            revenueTransRecord.setOrderNo(payOrderNo);
            revenueTransRecord.setPayeeCode(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
            revenueTransRecord.setPayerCode(Constants.PRODUCT_PROPERTY_SYMBOL_BGW);
            if(Constants.TRANS_SYS_YUN_LOAN_FEE.equals(transType)){
                revenueTransRecord.setSettleType(Constants.REVENUE_TYPE_LOAN_FEE);
            }else{
                revenueTransRecord.setSettleType(Constants.REVENUE_TYPE_REPAY_REVENUE);
            }
            revenueTransRecord.setStatus(Constants.REVENUE_TRANS_INIT);
            revenueTransRecordMapper.insertSelective(revenueTransRecord);
        }

        //订单表插入，准备转账
        BsPayOrders order = new BsPayOrders();
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        order.setAmount(amount);
        order.setChannel(Constants.CHANNEL_BAOFOO);
        order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
        order.setCreateTime(new Date());
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setOrderNo(payOrderNo);
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setTransType(transType);
        order.setUpdateTime(new Date());
        TransferEnv transferEnv= TransferEnvUtil.transferEnvMap.get(Constants.PROPERTY_SYMBOL_YUN_DAI);
        //订单用userName来记录转账给哪方借贷公司
        order.setUserName(transferEnv.getTransTarget());
        bsPayOrdersMapper.insertSelective(order);

        //订单明细表插入
        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
        orderJnl.setCreateTime(new Date());
        orderJnl.setOrderId(order.getId());
        orderJnl.setOrderNo(order.getOrderNo());
        orderJnl.setSubAccountCode(transferEnv.getTransTarget());
        orderJnl.setSysTime(new Date());
        orderJnl.setTransAmount(order.getAmount());
        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        bsPayOrdersJnlMapper.insertSelective(orderJnl);

        //发起宝付钱包转账
        B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans acctTransReq = new B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans();
        acctTransReq.setTrans_money(order.getAmount().toString());
        acctTransReq.setTrans_no(payOrderNo);
        acctTransReq.setTransSummary(instr);
        acctTransReq.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
        B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = null;
        try {
            res = baoFooTransportService.pay4OnlineTrans(acctTransReq);
        } catch (Exception e) {
            if(transType.equals(Constants.TRANS_SYS_YUN_REPEAT)){
                //批量改重复还款记录状态为失败
                if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                    changeRepeatRepay(repeatRepayIds,Constants.REPEAT_REPAY_STATUS_FAIL);
                }
            }else{
                //bs_revenue_trans_record更新为FAIL
                BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
                revenueTrans.setId(revenueTransRecord.getId());
                revenueTrans.setStatus(Constants.REVENUE_TRANS_FAIL);
                revenueTrans.setUpdateTime(new Date());
                revenueTransRecordMapper.updateByPrimaryKeySelective(revenueTrans);
            }

            //更新订单表，记录订单流水表
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            updateOrder.setReturnMsg("通讯失败");
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(order.getId());
            insertOrderJnl.setOrderNo(order.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(order.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
            insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            insertOrderJnl.setReturnMsg(res.getResMsg());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-" + instr + "}订单号["+order.getOrderNo()+"]转账申请通讯异常",
                    order.getOrderNo(),"系统钱包转账-" + instr,false);
            return;
        }

        //转账请求成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
            if(Constants.BAOFOO_ONLINE_TRANS_STATUS_PAYING.equals(res.getState())){
                //转账中
                if(transType.equals(Constants.TRANS_SYS_YUN_REPEAT)){
                    //批量改重复还款记录状态为处理中
                    if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                        changeRepeatRepay(repeatRepayIds,Constants.REPEAT_REPAY_STATUS_PROCESS);
                    }
                }

                //更新订单表
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_ING);
                updateOrder.setReturnMsg(res.getResMsg());
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                //入redis
                Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("process");
                if (MapUtils.isEmpty(map) || map.size() == 0) {
                    map = new HashMap<>();
                }
                LoanNoticeVO vo = new LoanNoticeVO();
                vo.setPayOrderNo(order.getOrderNo());
                vo.setChannel(order.getChannel());
                vo.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
                vo.setTransType(order.getTransType());
                vo.setStatus(Constants.ORDER_STATUS_PAYING);
                vo.setAmount(MoneyUtil.defaultRound(order.getAmount()).toString());
                redisUtil.rpushRedis(vo);
                //插入消息队列表
                BsPayResultQueue queue = new BsPayResultQueue();
                queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
                queue.setCreateTime(new Date());
                queue.setDealNum(0);
                queue.setOrderNo(order.getOrderNo());
                queue.setStatus(Constants.PAY_RESULT_QUEUE_PROCESS);
                queue.setTransType(order.getTransType());
                queue.setUpdateTime(new Date());
                queueMapper.insertSelective(queue);
            }else if(Constants.BAOFOO_ONLINE_TRANS_STATUS_SUCCESS.equals(res.getState())){
                //成功，调用营收结算结果处理
                RevenueSettleResultInfo result = new RevenueSettleResultInfo();
                result.setOrderNo(payOrderNo);
                result.setReturnCode(res.getResCode());
                result.setReturnMsg(res.getResMsg());
                result.setRepeatRepayIds(repeatRepayIds);
                result.setSuc(true);
                revenueSettleResult(result, PartnerEnum.YUN_DAI_SELF);
            }else {
                //失败，调用营收结算结果处理
                RevenueSettleResultInfo result = new RevenueSettleResultInfo();
                result.setOrderNo(payOrderNo);
                result.setReturnCode(res.getResCode());
                result.setReturnMsg(res.getResCode());
                result.setRepeatRepayIds(repeatRepayIds);
                result.setSuc(false);
                revenueSettleResult(result, PartnerEnum.YUN_DAI_SELF);
            }
        }else{
            //转账请求失败，和赞分期保持一致，置为处理中
            if(transType.equals(Constants.TRANS_SYS_YUN_REPEAT)){
                //重复还款，批量改重复还款记录状态为失败
                if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                    changeRepeatRepay(repeatRepayIds,Constants.REPEAT_REPAY_STATUS_PROCESS);
                }
            }else{
                //非重复还款，bs_revenue_trans_record更新为处理中
                BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
                revenueTrans.setId(revenueTransRecord.getId());
                revenueTrans.setStatus(Constants.REVENUE_TRANS_PROCESS);
                revenueTrans.setUpdateTime(new Date());
                revenueTransRecordMapper.updateByPrimaryKeySelective(revenueTrans);
            }

            //更新订单表，记录订单流水表
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
            updateOrder.setReturnCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
            updateOrder.setReturnMsg(res.getResMsg());
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(order.getId());
            insertOrderJnl.setOrderNo(order.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(order.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
            insertOrderJnl.setReturnCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
            insertOrderJnl.setReturnMsg(res.getResMsg());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-" + instr + "}订单号["+order.getOrderNo()+"]转账申请通讯异常",
                    order.getOrderNo(),"系统钱包转账-" + instr,false);

        }
    }
   
    
    /**
     * 营收转账公共方法
     * @param amount 转账金额
     * @param transType 订单交易类型
     * @param instr 订单交易类型说明
     */
    public void transToZsd(Double amount, String transType, String instr, List<Integer> repeatRepayIds){
        String payOrderNo= Util.generateSysOrderNo("ZTS");	//转账赞时贷订单号
        //砍头息，还款营收记录一条bs_revenue_trans_record初始数据
        BsRevenueTransRecord revenueTransRecord = new BsRevenueTransRecord();
        if(!Constants.TRANS_SYS_ZSD_REPEAT.equals(transType)){
            revenueTransRecord.setAmount(amount);
            revenueTransRecord.setCreateTime(new Date());
            revenueTransRecord.setUpdateTime(new Date());
            revenueTransRecord.setSettleDate(new Date());
            revenueTransRecord.setOrderNo(payOrderNo);
            revenueTransRecord.setPayeeCode(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD);
            revenueTransRecord.setPayerCode(Constants.PRODUCT_PROPERTY_SYMBOL_BGW);
            if(Constants.TRANS_SYS_ZSD_LOAN_FEE.equals(transType)){
                revenueTransRecord.setSettleType(Constants.REVENUE_TYPE_LOAN_FEE);
            }else{
                revenueTransRecord.setSettleType(Constants.REVENUE_TYPE_REPAY_REVENUE);
            }
            revenueTransRecord.setStatus(Constants.REVENUE_TRANS_INIT);
            revenueTransRecordMapper.insertSelective(revenueTransRecord);
        }

        //订单表插入，准备转账
        BsPayOrders order = new BsPayOrders();
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        order.setAmount(amount);
        order.setChannel(Constants.CHANNEL_BAOFOO);
        order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
        order.setCreateTime(new Date());
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setOrderNo(payOrderNo);
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setTransType(transType);
        order.setUpdateTime(new Date());
        //订单用userName来记录转账给哪方借贷公司
        TransferEnv transferEnv= TransferEnvUtil.transferEnvMap.get(Constants.PROPERTY_SYMBOL_ZSD);
        order.setUserName(transferEnv.getTransTarget());
        bsPayOrdersMapper.insertSelective(order);

        //订单明细表插入
        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
        orderJnl.setCreateTime(new Date());
        orderJnl.setOrderId(order.getId());
        orderJnl.setOrderNo(order.getOrderNo());
        orderJnl.setSubAccountCode(transferEnv.getTransTarget());
        orderJnl.setSysTime(new Date());
        orderJnl.setTransAmount(order.getAmount());
        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        bsPayOrdersJnlMapper.insertSelective(orderJnl);

        //发起宝付钱包转账
        B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans acctTransReq = new B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans();
        acctTransReq.setTrans_money(order.getAmount().toString());
        acctTransReq.setTrans_no(payOrderNo);
        acctTransReq.setTransSummary(instr);
        acctTransReq.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD);
        B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = null;
        try {
            res = baoFooTransportService.pay4OnlineTrans(acctTransReq);
        } catch (Exception e) {
            if(transType.equals(Constants.TRANS_SYS_ZSD_REPEAT)){
                //批量改重复还款记录状态为失败
                if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                    changeRepeatRepay(repeatRepayIds, Constants.REPEAT_REPAY_STATUS_FAIL);
                }
            }else{
                //bs_revenue_trans_record更新为FAIL
                BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
                revenueTrans.setId(revenueTransRecord.getId());
                revenueTrans.setStatus(Constants.REVENUE_TRANS_FAIL);
                revenueTrans.setUpdateTime(new Date());
                revenueTransRecordMapper.updateByPrimaryKeySelective(revenueTrans);
            }

            //更新订单表，记录订单流水表
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            updateOrder.setReturnMsg("通讯失败");
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(order.getId());
            insertOrderJnl.setOrderNo(order.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(order.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
            insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            insertOrderJnl.setReturnMsg(res==null ? "":res.getResMsg());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-" + instr + "}订单号["+order.getOrderNo()+"]转账申请通讯异常",
                    order.getOrderNo(),"系统钱包转账-" + instr,false);
            return;
        }

        //转账请求成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
            if(Constants.BAOFOO_ONLINE_TRANS_STATUS_PAYING.equals(res.getState())){
                //转账中
                if(transType.equals(Constants.TRANS_SYS_ZSD_REPEAT)){
                    //批量改重复还款记录状态为处理中
                    if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                        changeRepeatRepay(repeatRepayIds,Constants.REPEAT_REPAY_STATUS_PROCESS);
                    }
                }

                //更新订单表
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_ING);
                updateOrder.setReturnMsg(res.getResMsg());
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                //入redis
                Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("process");
                if (MapUtils.isEmpty(map) || map.size() == 0) {
                    map = new HashMap<>();
                }
                LoanNoticeVO vo = new LoanNoticeVO();
                vo.setPayOrderNo(order.getOrderNo());
                vo.setChannel(order.getChannel());
                vo.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
                vo.setTransType(order.getTransType());
                vo.setStatus(Constants.ORDER_STATUS_PAYING);
                vo.setAmount(MoneyUtil.defaultRound(order.getAmount()).toString());
                redisUtil.rpushRedis(vo);
                //插入消息队列表
                BsPayResultQueue queue = new BsPayResultQueue();
                queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
                queue.setCreateTime(new Date());
                queue.setDealNum(0);
                queue.setOrderNo(order.getOrderNo());
                queue.setStatus(Constants.PAY_RESULT_QUEUE_PROCESS);
                queue.setTransType(order.getTransType());
                queue.setUpdateTime(new Date());
                queueMapper.insertSelective(queue);
            }else if(Constants.BAOFOO_ONLINE_TRANS_STATUS_SUCCESS.equals(res.getState())){
                //成功，调用营收结算结果处理
                RevenueSettleResultInfo result = new RevenueSettleResultInfo();
                result.setOrderNo(payOrderNo);
                result.setReturnCode(res.getResCode());
                result.setReturnMsg(res.getResMsg());
                result.setRepeatRepayIds(repeatRepayIds);
                result.setSuc(true);
                zsdRevenueSettleResult(result);
            }else {
                //失败，调用营收结算结果处理
                RevenueSettleResultInfo result = new RevenueSettleResultInfo();
                result.setOrderNo(payOrderNo);
                result.setReturnCode(res.getResCode());
                result.setReturnMsg(res.getResCode());
                result.setRepeatRepayIds(repeatRepayIds);
                result.setSuc(false);
                zsdRevenueSettleResult(result);
                //告警
                specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-" + instr + "}订单号["+order.getOrderNo()+"]转账失败",
                        order.getOrderNo(),"系统钱包转账-" + instr,false);
            }
        }else{
            //转账请求失败，和赞分期保持一致，置为处理中
            if(transType.equals(Constants.TRANS_SYS_ZSD_REPEAT)){
                //重复还款，批量改重复还款记录状态为失败
                if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                    changeRepeatRepay(repeatRepayIds,Constants.REPEAT_REPAY_STATUS_PROCESS);
                }
            }else{
                //非重复还款，bs_revenue_trans_record更新为处理中
                BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
                revenueTrans.setId(revenueTransRecord.getId());
                revenueTrans.setStatus(Constants.REVENUE_TRANS_PROCESS);
                revenueTrans.setUpdateTime(new Date());
                revenueTransRecordMapper.updateByPrimaryKeySelective(revenueTrans);
            }

            //更新订单表，记录订单流水表
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
            updateOrder.setReturnCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
            updateOrder.setReturnMsg(res.getResMsg());
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(order.getId());
            insertOrderJnl.setOrderNo(order.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(order.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
            insertOrderJnl.setReturnCode(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode());
            insertOrderJnl.setReturnMsg(res.getResMsg());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-" + instr + "}订单号["+order.getOrderNo()+"]转账申请通讯异常",
                    order.getOrderNo(),"系统钱包转账-" + instr,false);
        }
    }
    
    /**
     * 生成营收明细excel
     */
    private String revenueDetailGen(String prefixName, PartnerEnum partnerEnum) {
        String prePath = "";
        if(partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) {
            prePath = GlobEnvUtil.get("self.loan.7dai.account.path");
        } else {
            prePath = GlobEnvUtil.get("self.loan.yundai.account.path");
        }
        List<RevenueSettleDetailVO> repayRevenueList = new ArrayList<RevenueSettleDetailVO>();
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);
        if(prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPEAT)){
            //重复还款
            repayRevenueList = lnLoanMapper.selectRepeatRepayDetail(yestday, partnerEnum.getCode());
        }else if(prefixName.equals(Constants.REVENUE_FILE_PREFIX_LOAN_FEE)){
            //借款手续费
            repayRevenueList = lnLoanMapper.selectLoanFeeDetail(yestday, partnerEnum.getCode());
        }else if(prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)){
        	if (partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
        		//还款营收文件
        		repayRevenueList = lnLoanMapper.selectRepayRevenueDetail(yestday, partnerEnum.getCode());        		 
        	} else if(partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) {
        		//7贷还款营收文件
        		repayRevenueList = lnLoanMapper.selectSevenRepayRevenueDetail(yestday, partnerEnum.getCode());         
        	}
        }

        List<String> content = new ArrayList<>();
        // 设置标题
        content.add(revenueDetailFileTitles(prefixName, partnerEnum));

        if (!CollectionUtils.isEmpty(repayRevenueList)) {
            for (RevenueSettleDetailVO data : repayRevenueList) {
                StringBuffer contentBuffer = new StringBuffer();
                contentBuffer.append(StringUtil.isEmpty(data.getPartnerLoanId()) ? "" : String.valueOf(data.getPartnerLoanId())).append(",");
                if(prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPEAT) || prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)){
                    contentBuffer.append(StringUtil.isEmpty(data.getPartnerRepayId()) ? "" : String.valueOf(data.getPartnerRepayId())).append(",");
                }
                //重复还款时明细增加合作方用户编号
                if(prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPEAT)){
                    contentBuffer.append(StringUtil.isEmpty(data.getPartnerUserId()) ? "" : String.valueOf(data.getPartnerUserId())).append(",");
                }
                if((partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) && prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)) {
                    contentBuffer.append(data.getApplyAmount() == null ? "" : data.getApplyAmount()).append(",");
                }
                contentBuffer.append(data.getFinishTime() == null ? "" : DateUtil.formatYYYYMMDD(data.getFinishTime()));

                if (StringUtil.isNotBlank(data.getPartnerBusinessFlag())) {
                	// 云贷业务标识改成英文
                	if (partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
                		contentBuffer.append(",").append(data.getPartnerBusinessFlag());
                	} else {
                		if(BusinessTypeEnum.REPAY_ANY_TIME.getCode().equals(data.getPartnerBusinessFlag())) {
                            contentBuffer.append(",").append("随借随还产品");
                        } else if (BusinessTypeEnum.EQUAL_PRINCIPAL_INTEREST.getCode().equals(data.getPartnerBusinessFlag())) {
                        	contentBuffer.append(",").append("等本等息产品");
                        } else if (BusinessTypeEnum.AVERAGE_CAPITAL_PLUS_INTEREST.getCode().equals(data.getPartnerBusinessFlag())) {
                        	contentBuffer.append(",").append("等额本息产品");
                        } else {
                            contentBuffer.append(",").append("先息后本产品");
                        }
                	}
                }
                if((partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) && prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)) {
                    contentBuffer.append(",").append(data.getApplyAmount() == null ? "" : data.getApplyAmount());
                }
                content.add(contentBuffer.toString());
            }
        }
        try {
            ExportUtil.exportLocalCSV(prePath + File.separator + prefixName, content,  prefixName + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".csv");
            logger.info("营收结算明细文件生成结束");
            return prePath + File.separator + prefixName+File.separator+prefixName + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".csv";
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("营收结算明细文件生成失败：", e.getMessage());
        }
        return null;
    }

    /**
     * 返回结算明细文件的标题
     *
     * @return
     */
    private String revenueDetailFileTitles(String prefixName, PartnerEnum partnerEnum) {
        StringBuffer header = new StringBuffer();
        if(prefixName.equals(Constants.REVENUE_FILE_PREFIX_LOAN_FEE)){
            header.append("借款编号").append(",");
            header.append("放款时间");
        }else if(prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPEAT)){ // 重复还款文件
            header.append("借款编号").append(",");
            header.append("还款编号").append(",");
            header.append("借款人编号").append(",");
            header.append("完成时间");
            if(partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) {
                header.append(",").append("业务标识");
            } else if (partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) {
            	header.append(",").append("还款业务标识");
            }
        }else{
            header.append("借款编号").append(",");
            header.append("还款编号").append(",");
            if((partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) && prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)) {
                header.append("结算金额").append(",");
            }
            header.append("完成时间");
            if((partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) && prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)) { // 还款营收文件
                header.append(",").append("业务标识");
            }
            // 云贷营收结算通知接口改造
            if((partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) && prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)) { // 还款营收文件
                header.append(",").append("还款业务标识");
            }
            if((partnerEnum.equals(PartnerEnum.YUN_DAI_SELF)) && prefixName.equals(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE)) {
                header.append(",").append("结算金额");
            }
        }
        return header.toString();
    }

    /**
     * 修改重复还款列表
     * @param repeatRepayIds
     * @param status
     */
    private void changeRepeatRepay(List<Integer> repeatRepayIds, String status) {
        for(Integer repeatRepayId: repeatRepayIds){
            LnRepeatRepayRecord repeat = new LnRepeatRepayRecord();
            repeat.setId(repeatRepayId);
            repeat.setUpdateTime(new Date());
            repeat.setStatus(status);
            if(status.equals(Constants.REPEAT_REPAY_STATUS_SUCC)){
                repeat.setFinishTime(new Date());
            }
            lnRepeatRepayRecordMapper.updateByPrimaryKeySelective(repeat);
        }
    }

    /**
     * 云贷||7贷营收结算结果处理
     */
    public void revenueSettleResult(RevenueSettleResultInfo result, PartnerEnum partnerEnum){
        BsPayOrdersExample example = new BsPayOrdersExample();
        example.createCriteria().andOrderNoEqualTo(result.getOrderNo());
        List<BsPayOrders> bsPayOrders = bsPayOrdersMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(bsPayOrders)){
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST, "bs_pay_orders订单号:" + result.getOrderNo()+ "对应订单不存在");
        }
        BsPayOrders bsPayOrder = bsPayOrders.get(0);
        TransferEnv transferEnv = TransferEnvUtil.transferEnvMap.get(partnerEnum.getCode());
        //置营收转账记录表为成功或失败
        BsRevenueTransRecordExample exampleRev = new BsRevenueTransRecordExample();
        exampleRev.createCriteria().andOrderNoEqualTo(result.getOrderNo());
        BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
        revenueTrans.setFinishTime(new Date());
        revenueTrans.setUpdateTime(new Date());

        if(result.getSuc()){
            //更新订单表为支付成功
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(bsPayOrder.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
            updateOrder.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_SUCCESS);
            updateOrder.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_SUCCESS.getDescription());
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            //新增成功流水表
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(bsPayOrder.getId());
            insertOrderJnl.setOrderNo(result.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(bsPayOrder.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
            insertOrderJnl.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_SUCCESS);
            insertOrderJnl.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_SUCCESS.getDescription());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
            PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(partnerEnum);
            if(Constants.TRANS_SYS_YUN_REPEAT.equals(bsPayOrder.getTransType())
                    || Constants.TRANS_SYS_SEVEN_REPEAT.equals(bsPayOrder.getTransType())){
                //批量改重复还款记录状态为成功
                if(CollectionUtils.isNotEmpty(result.getRepeatRepayIds())){
                    changeRepeatRepay(result.getRepeatRepayIds(),Constants.REPEAT_REPAY_STATUS_SUCC);
                }
                //结算记账
                depFixedRevenueSettleAccountService.revenueSettleRecord(partnerActCode.getRevenueActCode(), bsPayOrder.getAmount(), Constants.REVENUE_TYPE_REPEAT);
            }else{
                //bs_revenue_trans_record更新为SUCCESS
                revenueTrans.setStatus(Constants.REVENUE_TRANS_SUCCESS);
                int row = revenueTransRecordMapper.updateByExampleSelective(revenueTrans, exampleRev);
                if(row <= 0){
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"订单号:" + result.getOrderNo() + "对应营收转账记录不存在");
                }
                //结算记账
                if(Constants.TRANS_SYS_YUN_LOAN_FEE.equals(bsPayOrder.getTransType())
                        || Constants.TRANS_SYS_SEVEN_LOAN_FEE.equals(bsPayOrder.getTransType())){
                    depFixedRevenueSettleAccountService.revenueSettleRecord(partnerActCode.getDepHeadFeeActCode(), bsPayOrder.getAmount(), Constants.REVENUE_TYPE_LOAN_FEE);
                }else{
                    depFixedRevenueSettleAccountService.revenueSettleRecord(partnerActCode.getRevenueActCode(), bsPayOrder.getAmount(), Constants.REVENUE_TYPE_REPAY_REVENUE);
                }
            }
            //启线程通知云贷营收结算成功
            RevenueProcess process = new RevenueProcess();
            process.setBsPayOrders(bsPayOrder);
            process.setPartnerEnum(partnerEnum);
            process.setDepFixedRevenueSettleService(this);
            new Thread(process).start();
        }else{
            //更新订单表为支付失败
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(bsPayOrder.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_FAIL);
            updateOrder.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_FAIL.getDescription());
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            //新增成功流水表
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(bsPayOrder.getId());
            insertOrderJnl.setOrderNo(result.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(bsPayOrder.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            insertOrderJnl.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_FAIL);
            insertOrderJnl.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_FAIL.getDescription());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
            if(Constants.TRANS_SYS_YUN_REPEAT.equals(bsPayOrder.getTransType())
                    || Constants.TRANS_SYS_SEVEN_REPEAT.equals(bsPayOrder.getTransType())){
                //批量改重复还款记录状态为失败
                if(CollectionUtils.isNotEmpty(result.getRepeatRepayIds())){
                    changeRepeatRepay(result.getRepeatRepayIds(),Constants.REPEAT_REPAY_STATUS_FAIL);
                }
                specialJnlService.warn4Fail(bsPayOrder.getAmount(), "{系统钱包转账-" + Constants.TRANS_TYPE_REPEAT_INSTR + "}订单号["+bsPayOrder.getOrderNo()+"]转账失败",
                        bsPayOrder.getOrderNo(),"系统钱包转账-" + Constants.TRANS_TYPE_REPEAT_INSTR,false);

            }else{
                //bs_revenue_trans_record更新为FAIL
                revenueTrans.setStatus(Constants.REVENUE_TRANS_FAIL);
                int row = revenueTransRecordMapper.updateByExampleSelective(revenueTrans, exampleRev);
                if(row <= 0){
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"订单号:" + result.getOrderNo() + "对应营收转账记录不存在");
                }
                specialJnlService.warn4Fail(bsPayOrder.getAmount(), "{系统钱包转账-" + Constants.TRANS_TYPE_REPAY_REVENUE_INSTR + "}订单号["+bsPayOrder.getOrderNo()+"]转账失败",
                        bsPayOrder.getOrderNo(),"系统钱包转账-" + Constants.TRANS_TYPE_REPAY_REVENUE_INSTR,false);

            }

        }
    }

	@Override
	public void zsdRevenueSettleResult(RevenueSettleResultInfo result) {
		BsPayOrdersExample example = new BsPayOrdersExample();
        example.createCriteria().andOrderNoEqualTo(result.getOrderNo());
        List<BsPayOrders> bsPayOrders = bsPayOrdersMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(bsPayOrders)){
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST, "bs_pay_orders订单号:" + result.getOrderNo()+ "对应订单不存在");
        }
        BsPayOrders bsPayOrder = bsPayOrders.get(0);
        TransferEnv transferEnv = TransferEnvUtil.transferEnvMap.get(Constants.PROPERTY_SYMBOL_ZSD);
        //置营收转账记录表为成功或失败
        BsRevenueTransRecordExample exampleRev = new BsRevenueTransRecordExample();
        exampleRev.createCriteria().andOrderNoEqualTo(result.getOrderNo());
        BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
        revenueTrans.setFinishTime(new Date());
        revenueTrans.setUpdateTime(new Date());

        if(result.getSuc()){
            //更新订单表为支付成功
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(bsPayOrder.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
            updateOrder.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_SUCCESS);
            updateOrder.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_SUCCESS.getDescription());
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            //新增成功流水表
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(bsPayOrder.getId());
            insertOrderJnl.setOrderNo(result.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(bsPayOrder.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
            insertOrderJnl.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_SUCCESS);
            insertOrderJnl.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_SUCCESS.getDescription());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            if(Constants.TRANS_SYS_ZSD_REPEAT.equals(bsPayOrder.getTransType())){
                //批量改重复还款记录状态为成功
                if(CollectionUtils.isNotEmpty(result.getRepeatRepayIds())){
                    changeRepeatRepay(result.getRepeatRepayIds(),Constants.REPEAT_REPAY_STATUS_SUCC);
                }
                //结算记账
                depFixedRevenueSettleAccountService.revenueSettleRecord(Constants.SYS_ACCOUNT_THD_REVENUE_ZSD, bsPayOrder.getAmount(), Constants.REVENUE_TYPE_REPEAT);
            }else{
                //bs_revenue_trans_record更新为SUCCESS
                revenueTrans.setStatus(Constants.REVENUE_TRANS_SUCCESS);
                int row = revenueTransRecordMapper.updateByExampleSelective(revenueTrans, exampleRev);
                if(row <= 0){
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"订单号:" + result.getOrderNo() + "对应营收转账记录不存在");
                }
                //结算记账
                if(Constants.TRANS_SYS_ZSD_LOAN_FEE.equals(bsPayOrder.getTransType())){
                    depFixedRevenueSettleAccountService.revenueSettleRecord(Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD, bsPayOrder.getAmount(), Constants.REVENUE_TYPE_LOAN_FEE);
                }else{
                    depFixedRevenueSettleAccountService.revenueSettleRecord(Constants.SYS_ACCOUNT_THD_REVENUE_ZSD, bsPayOrder.getAmount(), Constants.REVENUE_TYPE_REPAY_REVENUE);
                }
            }
            //启线程通知云贷营收结算成功
           /* RevenueProcess process = new RevenueProcess();
            process.setBsPayOrders(bsPayOrder);
            process.setDepFixedRevenueSettleService(this);
            new Thread(process).start();*/
        }else{
            //更新订单表为支付失败
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(bsPayOrder.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_FAIL);
            updateOrder.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_FAIL.getDescription());
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            //新增成功流水表
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(bsPayOrder.getId());
            insertOrderJnl.setOrderNo(result.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(bsPayOrder.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            insertOrderJnl.setReturnCode(StringUtil.isNotEmpty(result.getReturnCode())? result.getReturnCode() : ConstantUtil.BSRESCODE_FAIL);
            insertOrderJnl.setReturnMsg(StringUtil.isNotEmpty(result.getReturnMsg())? result.getReturnMsg() : LoanStatus.BAOFOO_PAY_STATUS_FAIL.getDescription());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
            if(Constants.TRANS_SYS_ZSD_REPEAT.equals(bsPayOrder.getTransType())){
                //批量改重复还款记录状态为失败
                if(CollectionUtils.isNotEmpty(result.getRepeatRepayIds())){
                    changeRepeatRepay(result.getRepeatRepayIds(),Constants.REPEAT_REPAY_STATUS_FAIL);
                }
            }else{
                //bs_revenue_trans_record更新为FAIL
                revenueTrans.setStatus(Constants.REVENUE_TRANS_FAIL);
                int row = revenueTransRecordMapper.updateByExampleSelective(revenueTrans, exampleRev);
                if(row <= 0){
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,"订单号:" + result.getOrderNo() + "对应营收转账记录不存在");
                }
            }
        }
	}

	@Override
	public void zsdRevenueSettle() {
		//砍头息昨日总计(赞时贷)
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yestday = DateUtil.addDays(nowDate, -1);
        /*try {
            Double zsdHeadFeeAmount = revenueTransDetailMapper.sumFeeAmountDaily(yestday, Constants.PROPERTY_SYMBOL_ZSD, Constants.REVENUE_TYPE_HEAD_FEE_INCOME);
            if(zsdHeadFeeAmount > 0){
            	BsRevenueTransRecordExample example = new BsRevenueTransRecordExample();
                example.createCriteria().andSettleDateEqualTo(nowDate).andPayeeCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD)
                .andPayerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_BGW).andSettleTypeEqualTo(Constants.REVENUE_TYPE_LOAN_FEE)
                .andStatusEqualTo(Constants.REVENUE_TRANS_SUCCESS);
                List<BsRevenueTransRecord> revenueTransRecordList = revenueTransRecordMapper.selectByExample(example);
            	if (CollectionUtils.isEmpty(revenueTransRecordList)) {            		
            		//向赞时贷转账
            		transToZsd(zsdHeadFeeAmount, Constants.TRANS_SYS_ZSD_LOAN_FEE, Constants.TRANS_TYPE_LOAN_FEE_INSTR, null);
            	} else {            		
            		logger.info("==============【赞时贷】砍头息已结算===================");
            	}
            }
            logger.info("==============【赞时贷】砍头息"+zsdHeadFeeAmount+"===================");
        }catch (Exception e){
            logger.error("砍头息结算异常", e);
        }*/

        //获得上次结算日期
        Date lastSettleDate = revenueTransRecordMapper.selectLastSettleDateByCode(Constants.PROPERTY_SYMBOL_ZSD);
        Double diffAmount = 0d;
        try {
			diffAmount = revenueTransDetailMapper.sumDeductAmountByTimeAndType(lastSettleDate, yestday, Constants.REVENUE_TYPE_REPAY_INCOME, Constants.PROPERTY_SYMBOL_ZSD);
		} catch (Exception e) {
			logger.error("还款营收结算异常", e);
		}
		logger.info("==============【赞时贷】还款营收"+diffAmount+"===================");
        // 赞时贷还款营收结算
        if (diffAmount > 0) {
        	BsRevenueTransRecordExample example = new BsRevenueTransRecordExample();
            example.createCriteria().andSettleDateEqualTo(nowDate).andPayeeCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD)
            .andPayerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_BGW).andSettleTypeEqualTo(Constants.REVENUE_TYPE_REPAY_REVENUE)
            .andStatusEqualTo(Constants.REVENUE_TRANS_SUCCESS);
            List<BsRevenueTransRecord> revenueTransRecordList = revenueTransRecordMapper.selectByExample(example);
        	if (CollectionUtils.isEmpty(revenueTransRecordList)) {           		
        		//向赞时贷转账
        		transToZsd(diffAmount, Constants.TRANS_SYS_ZSD_REPAY_REVENUE, Constants.TRANS_TYPE_REPAY_REVENUE_INSTR, null);
        	} else {            		
        		logger.info("==============【赞时贷】还款营收已结算===================");
        	}
        } 
	}

	@Override
	public void zsdRepayRepeatSettle() {
		//结算退还金额
        Double zsdRepayAmount = 0d;
        List<Integer> zsdRepeatRepayIds = new ArrayList<Integer>();
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yesterday = DateUtil.addDays(nowDate, -1);

        List<String> statusList = new ArrayList<String>();
        statusList.add(Constants.REPEAT_REPAY_STATUS_INIT);
        statusList.add(Constants.REPEAT_REPAY_STATUS_FAIL);
        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
        example.createCriteria().andSettleDateEqualTo(yesterday).andPartnerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD).andStatusIn(statusList);
        List<LnRepeatRepayRecord> zsdRepeatRepayList = lnRepeatRepayRecordMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(zsdRepeatRepayList)){
        	for(LnRepeatRepayRecord lnRepeatRepayRecord: zsdRepeatRepayList){
                if(lnRepeatRepayRecord.getReturnAmount() != null){
                	zsdRepayAmount = MoneyUtil.add(zsdRepayAmount, lnRepeatRepayRecord.getReturnAmount()).doubleValue();
                	zsdRepeatRepayIds.add(lnRepeatRepayRecord.getId());
                }
            }
            logger.info("==============【赞时贷】重复还款"+zsdRepayAmount+"===================");
        }
        
        if(zsdRepayAmount > 0){
            transToZsd(zsdRepayAmount, Constants.TRANS_SYS_ZSD_REPEAT, Constants.TRANS_TYPE_REPEAT_INSTR, zsdRepeatRepayIds);
        }
	}

    @Override
    public void sevenDaiRevenueSettle() {
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yesterday = DateUtil.addDays(nowDate, -1);
        
        // 获得上次结算日期
        Date lastSettleDate = revenueTransRecordMapper.selectLastSettleDateByCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        // 还款营收未结算总和
        Double diffAmount = revenueTransDetailMapper.sumDeductAmountByTimeAndType(lastSettleDate, yesterday, 
        		Constants.REVENUE_TYPE_REPAY_INCOME, PartnerEnum.SEVEN_DAI_SELF.getCode());
		logger.info("==============【7贷自主】还款营收未结算总和：{}，上次结算日期：{}", diffAmount, lastSettleDate);
		PartnerSysActCode partnerActCode = BaseAccount.partnerActCodeMap.get(PartnerEnum.SEVEN_DAI_SELF);
		BsSysSubAccount sysSubAccount = bsSysSubAccountMapper.selectByCode(partnerActCode.getRevenueActCode());
        if(sysSubAccount == null){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "营收结算记账时未找到系统户记录");
        }
        Boolean sendWarnFlag = true;
		if (diffAmount > 0) {
			
            // 如果7贷营收户余额小于还款营收未结算总和告警，否则进行转账
			if(MoneyUtil.subtract(sysSubAccount.getAvailableBalance(), diffAmount).doubleValue() >= 0) {
				BsRevenueTransRecordExample example = new BsRevenueTransRecordExample();
		        List<String> statusList = new ArrayList<>();
		        statusList.add(Constants.REVENUE_TRANS_SUCCESS);
		        statusList.add(Constants.REVENUE_TRANS_PROCESS);
		        example.createCriteria().andSettleDateEqualTo(nowDate).andPayeeCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode())
		                .andPayerCodeEqualTo(Constants.PRODUCT_PROPERTY_SYMBOL_BGW).andSettleTypeEqualTo(Constants.REVENUE_TYPE_REPAY_REVENUE)
		                .andStatusIn(statusList);
		        List<BsRevenueTransRecord> revenueTransRecordList = revenueTransRecordMapper.selectByExample(example);
	        	if (CollectionUtils.isEmpty(revenueTransRecordList)) {           		
	        		//向7贷转账
	        		transToSevenDai(diffAmount, Constants.TRANS_SYS_SEVEN_REPAY_REVENUE, Constants.TRANS_TYPE_REPAY_REVENUE_INSTR, null);
	        	} else {            		
	        		logger.info("==============【7贷自主】还款营收已结算===================");
	        	}
			} else {
				generateDailySettle(yesterday);
				sendWarnFlag = false;
				String detail = "7贷营收户余额小于还款营收未结算总和，请核实";
				logger.error("告警>>>>>>" + detail);
				specialJnlService.warnAppoint4Fail(sysSubAccount.getAvailableBalance(), "七贷营收户余额："+ sysSubAccount.getAvailableBalance() + "，还款营收结算金额："+ diffAmount + "，请双方财务协调处理", null, "7贷还款营收结算", false, 
	            		Constants.EMERGENCY_MOBILE, Constants.PRODUCT_OPERATOR_MOBILE, Constants.FINANCE_MOBILE);
			}
        } else {
        	generateDailySettle(yesterday);            
        }
		if (sysSubAccount.getAvailableBalance().compareTo(0d) < 0 && sendWarnFlag) {
			  //发送告警短信,通知开发、产品、财务人员
          specialJnlService.warnAppoint4Fail(sysSubAccount.getAvailableBalance(), "七贷营收户余额："+ sysSubAccount.getAvailableBalance() + "，还款营收结算金额："+ diffAmount + "，请双方财务协调处理", null, "7贷还款营收结算", false, 
          		Constants.EMERGENCY_MOBILE, Constants.PRODUCT_OPERATOR_MOBILE, Constants.FINANCE_MOBILE);
		}
    }
    
    /**
     * 生成对账文件明细，资产方FTP下载文件
     * @param yesterday
     */
    private void generateDailySettle(Date yesterday) {
        //日结明细文件表生成
        BsDailySettlementFile file = new BsDailySettlementFile();
        file.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());            
        file.setTargetDate(yesterday);
        file.setCreateTime(new Date());
        file.setUpdateTime(new Date());
		//还款营收结算明细文件下载地址配置
        String fileUrl = revenueDetailGen(Constants.REVENUE_FILE_PREFIX_REPAY_REVENUE, PartnerEnum.SEVEN_DAI_SELF);
		file.setBusinessType(Constants.BUSINESS_TYPE_REPAY_REVENUE_DETAIL);
		file.setNote("还款营收结算明细文件");
  		file.setFileAddress(fileUrl);
        bsDailySettlementFileMapper.insertSelective(file);
    }

    @Override
    public void sevenRepayRepeatSettle() {
        //结算退还金额
        Double repayAmount = 0d;
        List<Integer> repeatRepayIds = new ArrayList<Integer>();
        Date nowDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        Date yesterday = DateUtil.addDays(nowDate, -1);

        List<String> statusList = new ArrayList<String>();
        statusList.add(Constants.REPEAT_REPAY_STATUS_INIT);
        statusList.add(Constants.REPEAT_REPAY_STATUS_FAIL);
        LnRepeatRepayRecordExample example = new LnRepeatRepayRecordExample();
        example.createCriteria().andSettleDateEqualTo(yesterday).andPartnerCodeEqualTo(PartnerEnum.SEVEN_DAI_SELF.getCode()).andStatusIn(statusList);
        List<LnRepeatRepayRecord> repeatRepayList = lnRepeatRepayRecordMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(repeatRepayList)){
            for(LnRepeatRepayRecord lnRepeatRepayRecord: repeatRepayList){
                if(lnRepeatRepayRecord.getReturnAmount() != null){
                    repayAmount = MoneyUtil.add(repayAmount,lnRepeatRepayRecord.getReturnAmount()).doubleValue();
                    repeatRepayIds.add(lnRepeatRepayRecord.getId());
                }
            }
            logger.info("==============【7贷自主】重复还款"+repayAmount+"===================");
        }

        if(repayAmount > 0){
            transToSevenDai(repayAmount,Constants.TRANS_SYS_SEVEN_REPEAT,Constants.TRANS_TYPE_REPEAT_INSTR,repeatRepayIds);
        }
    }

    /**
     * 7贷营收转账公共方法
     * @param amount 转账金额
     * @param transType 订单交易类型
     * @param instr 订单交易类型说明
     */
    public void transToSevenDai(Double amount,String transType,String instr,List<Integer> repeatRepayIds){
        String payOrderNo= Util.generateSysOrderNo("7TS");//转账7贷订单号
        //砍头息，还款营收记录一条bs_revenue_trans_record初始数据
        BsRevenueTransRecord revenueTransRecord = new BsRevenueTransRecord();
        if(!Constants.TRANS_SYS_SEVEN_REPEAT.equals(transType)){
            revenueTransRecord.setAmount(amount);
            revenueTransRecord.setCreateTime(new Date());
            revenueTransRecord.setUpdateTime(new Date());
            revenueTransRecord.setSettleDate(new Date());
            revenueTransRecord.setOrderNo(payOrderNo);
            revenueTransRecord.setPayeeCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
            revenueTransRecord.setPayerCode(Constants.PRODUCT_PROPERTY_SYMBOL_BGW);
            if(Constants.TRANS_SYS_SEVEN_LOAN_FEE.equals(transType)){
                revenueTransRecord.setSettleType(Constants.REVENUE_TYPE_LOAN_FEE);
            }else{
                revenueTransRecord.setSettleType(Constants.REVENUE_TYPE_REPAY_REVENUE);
            }
            revenueTransRecord.setStatus(Constants.REVENUE_TRANS_INIT);
            revenueTransRecordMapper.insertSelective(revenueTransRecord);
        }

        //订单表插入，准备转账
        BsPayOrders order = new BsPayOrders();
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        order.setAmount(amount);
        order.setChannel(Constants.CHANNEL_BAOFOO);
        order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
        order.setCreateTime(new Date());
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setOrderNo(payOrderNo);
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setTransType(transType);
        order.setUpdateTime(new Date());
        TransferEnv transferEnv= TransferEnvUtil.transferEnvMap.get(PartnerEnum.SEVEN_DAI_SELF.getCode());
        //订单用userName来记录转账给哪方借贷公司
        order.setUserName(transferEnv.getTransTarget());
        bsPayOrdersMapper.insertSelective(order);

        //订单明细表插入
        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
        orderJnl.setCreateTime(new Date());
        orderJnl.setOrderId(order.getId());
        orderJnl.setOrderNo(order.getOrderNo());
        orderJnl.setSubAccountCode(transferEnv.getTransTarget());
        orderJnl.setSysTime(new Date());
        orderJnl.setTransAmount(order.getAmount());
        orderJnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        bsPayOrdersJnlMapper.insertSelective(orderJnl);

        //发起宝付钱包转账
        B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans acctTransReq = new B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans();
        acctTransReq.setTrans_money(order.getAmount().toString());
        acctTransReq.setTrans_no(payOrderNo);
        acctTransReq.setTransSummary(instr);
        acctTransReq.setPropertySymbol(PartnerEnum.SEVEN_DAI_SELF.getCode());
        B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = null;
        try {
            res = baoFooTransportService.pay4OnlineTrans(acctTransReq);
        } catch (Exception e) {
            if(transType.equals(Constants.TRANS_SYS_SEVEN_REPEAT)){
                //批量改重复还款记录状态为失败
                if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                    changeRepeatRepay(repeatRepayIds,Constants.REPEAT_REPAY_STATUS_FAIL);
                }
            }else{
                //bs_revenue_trans_record更新为FAIL
                BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
                revenueTrans.setId(revenueTransRecord.getId());
                revenueTrans.setStatus(Constants.REVENUE_TRANS_FAIL);
                revenueTrans.setUpdateTime(new Date());
                revenueTransRecordMapper.updateByPrimaryKeySelective(revenueTrans);
            }

            //更新订单表，记录订单流水表
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            updateOrder.setReturnMsg("通讯失败");
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(order.getId());
            insertOrderJnl.setOrderNo(order.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(order.getAmount());
            insertOrderJnl.setTransCode(Constants.ORDER_TRANS_CODE_COMM_FAIL);
            insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            insertOrderJnl.setReturnMsg(res.getResMsg());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-" + instr + "}订单号["+order.getOrderNo()+"]转账申请通讯异常",
                    order.getOrderNo(),"系统钱包转账-" + instr,false);
            return;
        }

        //转账请求成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
            if(Constants.BAOFOO_ONLINE_TRANS_STATUS_PAYING.equals(res.getState())){
                //转账中
                if(transType.equals(Constants.TRANS_SYS_SEVEN_REPEAT)){
                    //批量改重复还款记录状态为处理中
                    if(CollectionUtils.isNotEmpty(repeatRepayIds)){
                        changeRepeatRepay(repeatRepayIds,Constants.REPEAT_REPAY_STATUS_PROCESS);
                    }
                } else {
                    //非重复还款，bs_revenue_trans_record更新为处理中
                    BsRevenueTransRecord revenueTrans = new BsRevenueTransRecord();
                    revenueTrans.setId(revenueTransRecord.getId());
                    revenueTrans.setStatus(Constants.REVENUE_TRANS_PROCESS);
                    revenueTrans.setUpdateTime(new Date());
                    revenueTransRecordMapper.updateByPrimaryKeySelective(revenueTrans);
                }

                //更新订单表
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
                updateOrder.setReturnCode(ConstantUtil.BSRESCODE_ING);
                updateOrder.setReturnMsg(res.getResMsg());
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);

                BsPayOrdersJnl bsPayOrdersJnl = new BsPayOrdersJnl();
                bsPayOrdersJnl.setCreateTime(new Date());
                bsPayOrdersJnl.setOrderId(order.getId());
                bsPayOrdersJnl.setOrderNo(order.getOrderNo());
                bsPayOrdersJnl.setSubAccountCode(transferEnv.getTransTarget());
                bsPayOrdersJnl.setSysTime(new Date());
                bsPayOrdersJnl.setTransAmount(order.getAmount());
                bsPayOrdersJnl.setTransCode(Constants.ORDER_TRANS_CODE_PROCESS);
                bsPayOrdersJnlMapper.insertSelective(bsPayOrdersJnl);

                //入redis
                Map<String, String> map = jsClientDaoSupport.getHashMapFromObj("process");
                if (MapUtils.isEmpty(map) || map.size() == 0) {
                    map = new HashMap<>();
                }
                LoanNoticeVO vo = new LoanNoticeVO();
                vo.setPayOrderNo(order.getOrderNo());
                vo.setChannel(order.getChannel());
                vo.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
                vo.setTransType(order.getTransType());
                vo.setStatus(Constants.ORDER_STATUS_PAYING);
                vo.setAmount(MoneyUtil.defaultRound(order.getAmount()).toString());
                redisUtil.rpushRedis(vo);
                //插入消息队列表
                BsPayResultQueue queue = new BsPayResultQueue();
                queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
                queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
                queue.setCreateTime(new Date());
                queue.setDealNum(0);
                queue.setOrderNo(order.getOrderNo());
                queue.setStatus(Constants.PAY_RESULT_QUEUE_PROCESS);
                queue.setTransType(order.getTransType());
                queue.setUpdateTime(new Date());
                queueMapper.insertSelective(queue);
            }else if(Constants.BAOFOO_ONLINE_TRANS_STATUS_SUCCESS.equals(res.getState())){
                //成功，调用营收结算结果处理
                RevenueSettleResultInfo result = new RevenueSettleResultInfo();
                result.setOrderNo(payOrderNo);
                result.setReturnCode(res.getResCode());
                result.setReturnMsg(res.getResMsg());
                result.setRepeatRepayIds(repeatRepayIds);
                result.setSuc(true);
                revenueSettleResult(result, PartnerEnum.SEVEN_DAI_SELF);
            }else {
                //失败，调用营收结算结果处理
                RevenueSettleResultInfo result = new RevenueSettleResultInfo();
                result.setOrderNo(payOrderNo);
                result.setReturnCode(res.getResCode());
                result.setReturnMsg(res.getResMsg());
                result.setRepeatRepayIds(repeatRepayIds);
                result.setSuc(false);
                revenueSettleResult(result, PartnerEnum.SEVEN_DAI_SELF);
            }
        }else{
            //失败，调用营收结算结果处理
            RevenueSettleResultInfo result = new RevenueSettleResultInfo();
            result.setOrderNo(payOrderNo);
            result.setReturnCode(res.getResCode());
            result.setReturnMsg(res.getResMsg());
            result.setRepeatRepayIds(repeatRepayIds);
            result.setSuc(false);
            revenueSettleResult(result, PartnerEnum.SEVEN_DAI_SELF);
        }
    }

}
