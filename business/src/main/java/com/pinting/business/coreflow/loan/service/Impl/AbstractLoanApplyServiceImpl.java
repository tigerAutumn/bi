package com.pinting.business.coreflow.loan.service.Impl;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BindCardInfoReq;
import com.pinting.business.accounting.loan.service.DepFixedRepayPaymentService;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.LoanQueueDTO;
import com.pinting.business.model.vo.BankBinVO;
import com.pinting.business.model.vo.BsUserCompensateVO;
import com.pinting.business.model.vo.LnLoanRelationVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.common.DepCommonService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.Util;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_DafyLoanNotice_LoanResultNotice;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_ApplyLoan;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.gateway.out.service.loan.DafyNoticeService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 借款申请抽象实现类
 * Created by zousheng on 2018/6/19.
 */
public abstract class AbstractLoanApplyServiceImpl implements DepFixedService {

    private Logger log = LoggerFactory.getLogger(AbstractLoanApplyServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    private static Map<String, Double> settleRate4YunMap = new HashMap<>();

    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnPayOrdersJnlMapper payOrdersJnlMapper;
    @Autowired
    private LnLoanRelationMapper loanRelationMapper;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private DafyNoticeService dafyNoticeService;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    protected LnDepositionTargetMapper lnDepositionTargetMapper;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnSubAccountMapper lnSubAccountMapper;
    @Autowired
    private LnAccountJnlMapper lnAccountJnlMapper;
    @Autowired
    private DepCommonService depCommonService;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcUserExtMapper ucUserExtMapper;
    @Autowired
    private UcBankCardMapper ucBankCardMapper;
    @Autowired
    private Bs19payBankMapper bs19payBankMapper;
    @Autowired
    private LnAccountMapper lnAccountMapper;
    @Autowired
    private LnLoanBlackMapper lnLoanBlackMapper;
    @Autowired
    private DepFixedRepayPaymentService depFixedRepayPaymentService;
    @Autowired
    private LnLoanApplyRecordMapper lnLoanApplyRecordMapper;
    @Autowired
    protected SysConfigService sysConfigService;
    @Autowired
    protected AlgorithmService algorithmService;

    @Override
    public ResMsg execute(FlowContext flowContext) {

        G2BReqMsg_DafyLoan_ApplyLoan req = (G2BReqMsg_DafyLoan_ApplyLoan) flowContext.getReq();
        PartnerEnum partnerEnum = flowContext.getPartnerEnum();

        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_LOAN_SELF.getKey() + partnerEnum.getCode() + req.getIdCard());

            // 1.插入记录借款申请
            insertLoanApplyRecord(req);

            LnUser lnUser = null;
            try {
                // 2.业务数据校验
                verifyBusinessBefore(req, partnerEnum);

                // 3.开户登记--开始
                lnUser = loanUserService.queryLoanUserExist(req.getUserId(), partnerEnum.getCode());
                lnUser = saveLnUser(lnUser, req, partnerEnum);
                addUcUser(req, lnUser.getId(), partnerEnum);
                LnAccount lnAccount = addLnAccount(lnUser.getId());
                addLnSubAccount(lnAccount.getId(), lnAccount.getLnUserId(), partnerEnum);
                // 3.开户登记--结束

                // 4.校验最大借款金额限制
                verifyLoanMaxSumAmount(lnUser.getId(), req.getLoanAmount());

                // 5.借款用户恒丰开户绑卡
                bindCard(req, partnerEnum, lnUser.getId());

                // 查询用户在恒丰绑卡成功后，读取用户HfUserId
                lnUser = lnUserMapper.selectByPrimaryKey(lnUser.getId());
            } catch (PTMessageException e) {
                if (lnUser == null) {
                    lnUser = loanUserService.queryLoanUserExist(req.getUserId(), partnerEnum.getCode());
                }

                if (!isExists4PartnerLoanId(req.getLoanId())) {
                    // 新增借款记录，状态标识放款失败
                    addLoanRecord(req, lnUser != null ? lnUser.getId() : null, LoanStatus.LOAN_STATUS_FAIL.getCode());
                }
                throw e;
            }

            // 6.新增借款记录，状态为审核通过
            LnLoan lnLoan = addLoanRecord(req, lnUser.getId(), LoanStatus.LOAN_STATUS_CHECKED.getCode());

            // 7.初始化存管标的
            LnDepositionTarget target = addDepositionTarget(lnLoan, req, partnerEnum);

            // 8. 构建标的发布请求参数，银行标的发布，标的发布成功后，放入loan_queue队列处理
            publishBankTarget(target, lnLoan, req, partnerEnum, lnUser.getHfUserId());

            return flowContext.getRes();
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_LOAN_SELF.getKey() + partnerEnum.getCode() + req.getIdCard());
        }
    }

    /**
     * 业务开始前数据校验
     *
     * @param req
     * @param partnerEnum
     */
    private void verifyBusinessBefore(G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum) {
        //黑名单校验
        LnLoanBlackExample example = new LnLoanBlackExample();
        example.createCriteria().andMobileEqualTo(req.getMobile());
        List<LnLoanBlack> blackList = lnLoanBlackMapper.selectByExample(example);
        if (CollectionUtils.isNotEmpty(blackList)) {
            String bankCardNo = lnBindCardMapper.getBankCardByMobile(blackList.get(0).getMobile());
            if (StringUtil.isNotBlank(bankCardNo) && req.getBankCard().equals(bankCardNo)) {
                //根据黑名单手机号查询最近一次绑卡卡号，相等异常
                throw new PTMessageException(PTMessageEnum.YUN_SELF_LOAN_USER_FOUND_IN_BLACK);
            } else {
                for (LnLoanBlack lnLoanBlack : blackList) {
                    //空或者不相等，删除黑名单中的信息，继续其他判断
                    lnLoanBlackMapper.deleteByPrimaryKey(lnLoanBlack.getId());
                }
            }
        }

        //借款订单号和借款编号重复申请校验
        Integer count = lnLoanMapper.selectByOrderNoAndPartnerCode(req.getOrderNo(), partnerEnum.getCode());
        if (count > 0) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, partnerEnum.getName() + "订单号重复");
        }

        if (isExists4PartnerLoanId(req.getLoanId())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, partnerEnum.getName() + "借款编号重复");
        }

        //判断bankCode是否存在
        if (!BaoFooEnum.bankCodeNameMap.containsKey(req.getBankCode())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ",银行编码没有找到");
        }

        //根据银行卡号查询卡bin表，然后根据银行id和通道类型查询开户行，并判断是否和三方传过来的一致，不一致则返回相应错误
        BankBinVO bankBinVO = bankCardService.queryBankBin(req.getBankCard());
        if (bankBinVO == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ",银行编码没有找到");
        }

        Bs19payBankExample payBankExample = new Bs19payBankExample();
        payBankExample.createCriteria().andBankIdEqualTo(bankBinVO.getBankId()).andChannelEqualTo(Constants.ORDER_CHANNEL_BAOFOO)
                .andPayTypeEqualTo(1);
        List<Bs19payBank> bs19payBank = bs19payBankMapper.selectByExample(payBankExample);
        if (CollectionUtils.isEmpty(bs19payBank) || !req.getBankCode().equals(bs19payBank.get(0).getPay19BankCode())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, ",银行编码与实际银行账号信息不一致");
        }
    }

    /**
     * 校验最大借款金额
     * 统计借款人待还本金，加上此次申请本金，和最高借款本金比较，超过返回借款失败
     *
     * @param lnUserId
     * @return
     */
    private void verifyLoanMaxSumAmount(Integer lnUserId, Double loanAmount) {
        Double notRepayAmount = lnRepayScheduleMapper.sumNotRepayByLnUserId(lnUserId);
        Double maxLoanAmount = 20d;
        BsSysConfig config = sysConfigService.findConfigByKey(Constants.LOAN_MAX_SUM_AMOUNT);
        if (config != null) {
            maxLoanAmount = MoneyUtil.multiply(Double.valueOf(config.getConfValue()), 10000).doubleValue();
        }
        if (maxLoanAmount.compareTo(MoneyUtil.add(notRepayAmount, loanAmount).doubleValue()) < 0) {
            throw new PTMessageException(PTMessageEnum.LOAN_MAX_OUT);
        }
    }

    /**
     * 获取币港湾借款结算利率（系统结算_年化利率）
     *
     * @return
     */
    protected Double getSettleRate() {
        //云贷借款---币港湾服务费利率
        String settleRate4YunMapKey = DateUtil.format(new Date(), DateUtil.SIMPLE_DATE);
        if (settleRate4YunMap.get(settleRate4YunMapKey) == null) {
            //map取不到当日的值，则先清空map,然后查询配置表，保存该值到map中
            settleRate4YunMap.clear();
            Double initRate = getSysSettleRate();
            if (initRate != null) {
                settleRate4YunMap.put(settleRate4YunMapKey, initRate);
            }
        }
        return settleRate4YunMap.get(settleRate4YunMapKey);
    }

    /**
     * 记录借款信息表ln_loan
     *
     * @param req
     * @param lnUserId
     * @param status
     */
    private LnLoan addLoanRecord(G2BReqMsg_DafyLoan_ApplyLoan req, Integer lnUserId, String status) {
        LnLoan lnLoan = new LnLoan();
        lnLoan.setApplyAmount(req.getLoanAmount());
        lnLoan.setApplyTime(req.getApplyTime());
        lnLoan.setApproveAmount(req.getLoanAmount());
        lnLoan.setBreakMaxDays(req.getBreakMaxDays());
        lnLoan.setBreakTimes(req.getBreakTimes());
        lnLoan.setHeadFee(req.getLoanFee());
        lnLoan.setPartnerLoanId(req.getLoanId());
        lnLoan.setCreateTime(new Date());
        lnLoan.setUpdateTime(new Date());
        lnLoan.setCreditAmount(req.getCreditAmount());
        lnLoan.setLoanedAmount(req.getLoanAmount());
        lnLoan.setCreditLevel(req.getCreditLevel());
        lnLoan.setCreditScore(req.getCreditScore());
        lnLoan.setLnUserId(lnUserId);
        lnLoan.setLoanTimes(req.getLoanTimes());
        lnLoan.setPartnerBusinessFlag(req.getBusinessType());
        lnLoan.setPartnerOrderNo(req.getOrderNo());
        lnLoan.setAddress(req.getAddress());
        lnLoan.setEmail(req.getEmail());
        lnLoan.setPayOrderNo(Util.generateOrderNo4BaoFoo(8));
        lnLoan.setPeriod(req.getLoanTerm());
        lnLoan.setPeriodUnit(getPeriodUnit());
        lnLoan.setPurpose(req.getPurpose());
        lnLoan.setStatus(status);
        lnLoan.setSubjectName(req.getSubjectName());
        lnLoan.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
        Double agreementRate = MoneyUtil.divide(req.getLoanRate(), 100, 2).doubleValue(); //借款协议利率
        lnLoan.setAgreementRate(agreementRate);
        lnLoan.setLoanServiceRate(getSysLoanServerRate()); //借款服务费利率
        lnLoan.setBgwSettleRate(getSettleRate()); // 借款结算利率
        lnLoanMapper.insertSelective(lnLoan);
        return lnLoan;
    }

    /**
     * 初始化存管标的
     *
     * @param lnLoan
     * @param req
     * @param partnerEnum
     * @return
     */
    private LnDepositionTarget addDepositionTarget(LnLoan lnLoan, G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum) {
        //新增一条ln_deposition_target的INIT记录
        LnDepositionTarget target = new LnDepositionTarget();
        target.setProdName(partnerEnum.getName() + lnLoan.getId().toString());
        target.setProdType(Constants.TARGET_PRODUCT_CYCLE);
        target.setTotalLimit(req.getLoanAmount());
        target.setSetupType(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
        target.setSellDate(new Date());
        target.setInterestType(Constants.TARGET_PRODUCT_INTEREST_CHECK);
        target.setCycle(lnLoan.getPeriod());
        target.setCycleUnit(getPeriodUnit().toString());
        target.setIstYear(lnLoan.getAgreementRate());
        target.setRepayType(Constants.TARGET_REPAY_TYPE_PERIOD);
        target.setLoanId(lnLoan.getId());
        target.setChargeOffAuto(Constants.TARGET_OUT_ACCOUNT_ACTIVE);
        target.setOverLimit(Constants.TARGET_OVER_LIMIT);
        target.setOverTotalLimit(req.getLoanAmount());
        target.setStatus(Constants.DEP_TARGET_INIT);
        target.setCreateTime(new Date());
        target.setUpdateTime(new Date());
        lnDepositionTargetMapper.insertSelective(target);
        return target;
    }

    /**
     * 构建标的发送请求参数，恒丰银行标的发布
     *
     * @param target
     * @param lnLoan
     * @param req
     * @param partnerEnum
     * @param hfUserId
     * @return
     */
    private void publishBankTarget(LnDepositionTarget target, LnLoan lnLoan, G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum, String hfUserId) {
        // 1. 构建标的发布请求参数，银行标的发布
        B2GReqMsg_HFBank_Publish publish = builderRequest(target, lnLoan, req, partnerEnum, hfUserId);

        // 2.标的发布请求
        B2GResMsg_HFBank_Publish res = new B2GResMsg_HFBank_Publish();
        try {
            res = hfbankTransportService.publish(publish);
        } catch (Exception e) {
            log.error(partnerEnum.getName() + "标的发布请求异常：{}", e);
            res.setResCode(ConstantUtil.BSRESCODE_FAIL);
            res.setResMsg("通讯失败，置为失败");
        }

        // 3.标的发布响应处理
        handleResponse(target, lnLoan, req, partnerEnum, res, publish.getOrder_no());
    }

    /**
     * 构建标的发送请求参数
     *
     * @param target
     * @param lnLoan
     * @param req
     * @param partnerEnum
     * @param hfUserId
     * @return
     */
    private B2GReqMsg_HFBank_Publish builderRequest(LnDepositionTarget target, LnLoan lnLoan, G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum, String hfUserId) {
        String publishOrderNo = Util.generateOrderNo4BaoFoo(8);
        B2GReqMsg_HFBank_Publish publish = new B2GReqMsg_HFBank_Publish();
        log.info("标的发布的订单号=[" + publishOrderNo + "]产品Id=[" + target.getId().toString() + "]");
        publish.setProd_id(target.getId().toString());
        publish.setProd_name(target.getProdName());
        publish.setProd_type(target.getProdType());
        publish.setTotal_limit(req.getLoanAmount());
        publish.setValue_type(Constants.TARGET_PRODUCT_INTEREST_CHECK);
        publish.setCreate_type(Constants.TARGET_PRODUCT_ESTABLISH_DAY);
        publish.setSell_date(DateUtil.getDate(new Date()));
        publish.setCycle(target.getCycle());
        publish.setCycle_unit(target.getCycleUnit());
        publish.setIst_year(target.getIstYear());
        publish.setRepay_type(target.getRepayType());
        publish.setChargeOff_auto(target.getChargeOffAuto());
        publish.setOverLimit(target.getOverLimit());
        publish.setOver_total_limit(target.getOverTotalLimit());
        publish.setPartner_trans_date(new Date());
        publish.setPartner_trans_time(new Date());
        publish.setOrder_no(publishOrderNo);
        //融资信息列表赋值
        List<PublishFinancingInfo> infoList = new ArrayList<PublishFinancingInfo>();
        PublishFinancingInfo info = new PublishFinancingInfo();
        info.setCust_no(hfUserId);
        info.setReg_date(new Date());
        info.setReg_time(new Date());
        info.setFinanc_int(getFinancInterestRate(target, lnLoan));
        info.setFee_int(0d);//恒丰增加校验-暂定0.00
        info.setOpen_branch(req.getBankCode());
        info.setWithdraw_account(req.getBankCard());
        info.setAccount_type(Constants.HF_LOAN_CARD_TYPE_PERSONAL);
        info.setPayee_name(req.getName());
        info.setFinanc_amt(target.getTotalLimit());
        infoList.add(info);
        publish.setFinancing_info_list(infoList);

        BsUserCompensateVO vo = depFixedRepayPaymentService.compensaterInfo(lnLoan.getCreateTime(), partnerEnum.getCode());
        if (vo == null) {
            specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(), partnerEnum.getName() + "代偿人信息未找到", lnLoan.getPayOrderNo(), "【" + partnerEnum.getName() + "借款申请异常】");
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, partnerEnum.getName() + "代偿人信息未找到");
        }
        CompensationInfo compInfos = new CompensationInfo();
        compInfos.setPlatcust(vo.getHfUserId());
        compInfos.setType(Constants.HF_COMPENSATE_REPAY_TYPE_COMPENSATE);
        List<CompensationInfo> compensationInfos = new ArrayList<>();
        compensationInfos.add(compInfos);
        publish.setCompensation_info_list(compensationInfos);
        return publish;
    }

    /**
     * 标的发送请求响应结果处理
     *
     * @param target
     * @param lnLoan
     * @param req
     * @param partnerEnum
     * @param res
     * @param publishOrderNo
     */
    private void handleResponse(LnDepositionTarget target, LnLoan lnLoan, G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum, B2GResMsg_HFBank_Publish res, String publishOrderNo) {
        //标的发布请求，成功时入redis
        if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
            //修改ln_deposition_target为PUBLISH，新增一条ln_deposition_target_jnl的成功记录
            depCommonService.updateTargetStatus(true, Constants.DEP_TARGET_PUBLISH, DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH, target, lnLoan, publishOrderNo);
            try {
                LoanQueueDTO loanQueueDTO = new LoanQueueDTO();
                loanQueueDTO.setChannel(partnerEnum.getCode());
                loanQueueDTO.setLnLoan(lnLoan);
                //再查一遍绑卡记录，因为有修改的可能
                LnBindCard newCard = loanUserService.queryIncrBindCardExist(req.getUserId(), partnerEnum.getCode(), req.getBankCard());
                loanQueueDTO.setLnBindCard(newCard);
                jsClientDaoSupport.rpush("loan_queue", JSON.toJSONString(loanQueueDTO));
                log.info(">>>" + partnerEnum.getName() + "入借款队列数据:" + JSON.toJSONString(loanQueueDTO) + "<<<");
            } catch (Exception e) {
                log.error(partnerEnum.getName() + "借款申请放入队列异常:{}", e);
            }
        } else {
            //新增一条ln_deposition_target_jnl的失败记录
            depCommonService.updateTargetStatus(false, Constants.DEP_TARGET_PUBLISH, DepTargetEnum.DEP_TARGET_OPERATE_PUBLISH, target, lnLoan, publishOrderNo);
            LnLoan updateLoan = new LnLoan();
            updateLoan.setId(lnLoan.getId());
            updateLoan.setUpdateTime(new Date());
            updateLoan.setStatus(LoanStatus.LOAN_STATUS_FAIL.getCode());
            lnLoanMapper.updateByPrimaryKeySelective(updateLoan);
            LnLoan newLoan = lnLoanMapper.selectByPrimaryKey(lnLoan.getId());
            notifyLoan2Partner(newLoan, "标的发布请求失败", null);
            specialJnlService.warn4FailNoSMS(lnLoan.getApproveAmount(), "标的发布请求失败", lnLoan.getPayOrderNo(), "【" + partnerEnum.getName() + "借款标的发布】");
            throw new PTMessageException(PTMessageEnum.DEP_TARGET_PUBLISH_ERROR, StringUtil.isEmpty(res.getRemsg()) ? "" : res.getRemsg());
        }
    }

    /**
     * 保存用户信息（更新/新增，ln_User）
     *
     * @param lnUser
     * @param req
     * @param partnerEnum
     * @return
     */
    private LnUser saveLnUser(LnUser lnUser, G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum) {
        LnUser user = new LnUser();
        user.setMobile(req.getMobile());
        user.setWorkUnit(req.getWorkUnit());
        user.setEducation(req.getEducation());
        user.setMarriage(req.getMarriage());
        user.setAnnualIncome(req.getMonthlyIncome() == null ? null : MoneyUtil.multiply(req.getMonthlyIncome(), 12).intValue());

        if (lnUser != null) {
            // 更新ln_user
            user.setId(lnUser.getId());
            user.setUpdateTime(new Date());
            lnUserMapper.updateByPrimaryKeySelective(user);
            return user;
        } else {
            // 新增ln_user
            user.setPartnerCode(partnerEnum.getCode());
            user.setPartnerUserId(req.getUserId());
            user.setUserName(req.getName());
            user.setIdCard(req.getIdCard());
            user.setCreateTime(new Date());
            user.setUpdateTime(new Date());
            lnUserMapper.insertSelective(user);
            return user;
        }
    }

    /**
     * 1.新增用户中心 uc_user
     * 2.新增用户中心 uc_user_ext
     *
     * @param req
     * @param lnUserId
     */
    private void addUcUser(G2BReqMsg_DafyLoan_ApplyLoan req, Integer lnUserId, PartnerEnum partnerEnum) {
        UcUserExample ucUserExist = new UcUserExample();
        ucUserExist.createCriteria().andIdCardEqualTo(req.getIdCard()).andStatusEqualTo(Constants.UC_USER_OPEN);
        List<UcUser> ucUsers = ucUserMapper.selectByExample(ucUserExist);
        UcUser ucUser = new UcUser();
        if (CollectionUtils.isEmpty(ucUsers)) {
            ucUser.setCreateTime(new Date());
            ucUser.setUpdateTime(new Date());
            ucUser.setStatus(Constants.UC_USER_OPEN);
            ucUser.setUserName(req.getName());
            ucUser.setIdCard(req.getIdCard());
            ucUserMapper.insertSelective(ucUser);
        } else {
            ucUser = ucUsers.get(0);
        }

        UcUserExtExample extExample = new UcUserExtExample();
        extExample.createCriteria().andUcUserIdEqualTo(ucUser.getId())
                .andUserIdEqualTo(lnUserId).andUserTypeEqualTo(partnerEnum.getCode());
        List<UcUserExt> ucUserExtList = ucUserExtMapper.selectByExample(extExample);
        if (CollectionUtils.isEmpty(ucUserExtList)) {
            UcUserExt ucUserExt = new UcUserExt();
            ucUserExt.setUcUserId(ucUser.getId());
            ucUserExt.setCreateTime(new Date());
            ucUserExt.setUserType(partnerEnum.getCode());
            ucUserExt.setUserId(lnUserId);
            ucUserExtMapper.insertSelective(ucUserExt);
        }
    }

    /**
     * 新增账户信息（ln_account）
     *
     * @param lnUserId
     * @return
     */
    private LnAccount addLnAccount(Integer lnUserId) {
        LnAccountExample example = new LnAccountExample();
        example.createCriteria().andLnUserIdEqualTo(lnUserId);
        List<LnAccount> lnAccountList = lnAccountMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(lnAccountList)) {
            LnAccount lnAccount = new LnAccount();
            lnAccount.setLnUserId(lnUserId);
            lnAccount.setCreateTime(new Date());
            lnAccount.setUpdateTime(new Date());
            lnAccount.setOpenTime(new Date());
            lnAccount.setStatus(Constants.LN_ACCOUNT_STATUS_NORMAL);
            lnAccountMapper.insertSelective(lnAccount);
            return lnAccount;
        } else {
            return lnAccountList.get(0);
        }
    }

    /**
     * 新增借款人余额子账户开户（ln_Sub_Account），借款人开户记账（ln_Account_Jnl）
     *
     * @param lnAccountId
     * @param lnUserId
     * @param partnerEnum
     */
    private void addLnSubAccount(Integer lnAccountId, Integer lnUserId, PartnerEnum partnerEnum) {
        LnSubAccountExample exampleSub = new LnSubAccountExample();
        exampleSub.createCriteria().andLnUserIdEqualTo(lnUserId).andAccountTypeEqualTo(Constants.LOAN_ACT_TYPE_DEP_JSH);
        List<LnSubAccount> lnSubAccounts = lnSubAccountMapper.selectByExample(exampleSub);
        if (CollectionUtils.isEmpty(lnSubAccounts)) {
            LnSubAccount loanAct = new LnSubAccount();
            loanAct.setLnUserId(lnUserId);
            loanAct.setAccountId(lnAccountId);
            loanAct.setAccountType(Constants.LOAN_ACT_TYPE_DEP_JSH);
            loanAct.setOpenBalance(0d);
            loanAct.setBalance(0d);
            loanAct.setAvailableBalance(0d);
            loanAct.setCanWithdraw(0d);
            loanAct.setFreezeBalance(0d);
            loanAct.setStatus(Constants.LOAN_SUBACCOUNT_STATUS_OPEN);
            loanAct.setAccumulationInerest(0d);
            loanAct.setOpenTime(new Date());
            loanAct.setCreateTime(new Date());
            loanAct.setUpdateTime(new Date());
            lnSubAccountMapper.insertSelective(loanAct);

            LnAccountJnl accountJnl = new LnAccountJnl();
            accountJnl.setTransTime(new Date());
            accountJnl.setTransCode(Constants.LN_DEP_JSH_OPEN);
            accountJnl.setTransName(partnerEnum.getName() + "借款用户余额子账户开户");
            accountJnl.setTransAmount(0.0);
            accountJnl.setSysTime(new Date());
            accountJnl.setChannelTime(null);
            accountJnl.setChannelJnlNo(null);
            accountJnl.setCdFlag2(Constants.CD_FLAG_D);
            accountJnl.setUserId2(lnUserId);
            accountJnl.setSubAccountId1(loanAct.getId());
            accountJnl.setSubAccountId2(loanAct.getId());
            accountJnl.setBeforeBalance2(0d);
            accountJnl.setAfterBalance2(0d);
            accountJnl.setBeforeAvialableBalance2(0d);
            accountJnl.setAfterAvialableBalance2(0d);
            accountJnl.setBeforeFreezeBalance2(0d);
            accountJnl.setAfterFreezeBalance2(0d);
            accountJnl.setFee(0.0);
            lnAccountJnlMapper.insertSelective(accountJnl);
        }
    }

    /**
     * 借款用户绑卡
     *
     * @param req
     * @param partnerEnum
     */
    private void bindCard(G2BReqMsg_DafyLoan_ApplyLoan req, PartnerEnum partnerEnum, Integer lnUserId) {
        log.info(partnerEnum.getName() + "用户 {} 绑卡开始", lnUserId);

        BindCardInfoReq cardInfoReq = new BindCardInfoReq();
        cardInfoReq.setBankCard(req.getBankCard());
        cardInfoReq.setBankCode(req.getBankCode());
        cardInfoReq.setIdCard(req.getIdCard());
        cardInfoReq.setLnUserId(lnUserId);
        cardInfoReq.setMobile(req.getMobile());
        cardInfoReq.setName(req.getName());
        cardInfoReq.setPartnerCode(partnerEnum.getCode());
        cardInfoReq.setUcUserType(partnerEnum.getCode());

        UcUserExtExample ucUserExtExample = new UcUserExtExample();
        ucUserExtExample.createCriteria().andUserIdEqualTo(lnUserId).andUserTypeEqualTo(cardInfoReq.getUcUserType());
        List<UcUserExt> extList = ucUserExtMapper.selectByExample(ucUserExtExample);
        if (CollectionUtils.isEmpty(extList)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND.getCode(), "用户中心信息不存在");
        }
        UcUser ucUser = ucUserMapper.selectByPrimaryKey(extList.get(0).getUcUserId());

        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(cardInfoReq.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        // 1 uc_user.hf_user_id 存在记录，证明已经开户
        if (StringUtil.isNotEmpty(ucUser.getHfUserId())) {
            UcBankCard ucCard = new UcBankCard();
            UcBankCardExample bankExample = new UcBankCardExample();
            bankExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andCardNoEqualTo(cardInfoReq.getBankCard())
                    .andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
            bankExample.setOrderByClause("id desc");
            List<UcBankCard> ucBankCards = ucBankCardMapper.selectByExample(bankExample);

            // uc_bank_card 不存在同卡记录
            if (CollectionUtils.isEmpty(ucBankCards)) {
                // 插入记录 uc_bank_card
                UcBankCard ucBankCard = new UcBankCard();
                ucBankCard.setBankId(bs19payBanks.get(0).getBankId());
                ucBankCard.setBankName(BaoFooEnum.bankCodeNameMap.get(cardInfoReq.getBankCode()));
                ucBankCard.setBindTime(new Date());
                ucBankCard.setCardNo(cardInfoReq.getBankCard());
                ucBankCard.setCardOwner(cardInfoReq.getName());
                ucBankCard.setCreateTime(new Date());
                ucBankCard.setIdCard(cardInfoReq.getIdCard());
                ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
                ucBankCard.setUcUserId(ucUser.getId());
                ucBankCard.setUpdateTime(new Date());
                ucBankCard.setMobile(cardInfoReq.getMobile());
                ucBankCardMapper.insertSelective(ucBankCard);
                ucCard = ucBankCard;
            } else {
                ucCard = ucBankCards.get(0);
            }

            LnBindCardExample yunExample = new LnBindCardExample();
            yunExample.createCriteria().andLnUserIdEqualTo(lnUserId).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andPartnerCodeEqualTo(cardInfoReq.getPartnerCode());
            yunExample.setOrderByClause("update_time desc");
            List<LnBindCard> yunList = lnBindCardMapper.selectByExample(yunExample);

            UcUserExtExample otherExtExample = new UcUserExtExample();
            otherExtExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andUserTypeNotEqualTo(cardInfoReq.getUcUserType());
            List<UcUserExt> otherExtList = ucUserExtMapper.selectByExample(otherExtExample);

            String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
            if (CollectionUtils.isEmpty(otherExtList)) {
                // 其他端（币港湾、赞分期）无注册用户，先对恒丰绑卡记录进行解绑再对申请卡进行绑卡
                UcBankCardExample notApplyBankExample = new UcBankCardExample();
                notApplyBankExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andCardNoNotEqualTo(cardInfoReq.getBankCard())
                        .andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED).andIsBindEqualTo(Constants.UC_BIND_CARD_YES);
                List<UcBankCard> notApplyUcBankCards = ucBankCardMapper.selectByExample(notApplyBankExample);
                if (CollectionUtils.isNotEmpty(notApplyUcBankCards)) {
                    this.unBind(ucUser.getHfUserId(), ucUser.getId(), lnUserId, cardInfoReq);
                }
                if (CollectionUtils.isNotEmpty(yunList)) {
                    if (!yunList.get(0).getBankCard().equals(cardInfoReq.getBankCard())) {
                        payBindOrderNo = this.bind(ucUser.getHfUserId(), ucUser.getId(), lnUserId, cardInfoReq);
                        ucCard.setIsBind(Constants.UC_BIND_CARD_YES);
                    }
                }
            }

            boolean needInsert = false;
            if (CollectionUtils.isEmpty(yunList)) {
                needInsert = true;
            } else if (CollectionUtils.isNotEmpty(yunList) && !yunList.get(0).getBankCard().equals(cardInfoReq.getBankCard())) {
                needInsert = true;
                // ln_bind_card存在非最新记录的当前卡信息，更新旧的ln_bind_card
                LnBindCardExample yunCardOpenExample = new LnBindCardExample();
                yunCardOpenExample.createCriteria().andLnUserIdEqualTo(cardInfoReq.getLnUserId())
                        .andPartnerCodeEqualTo(partnerEnum.getCode()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode())
                        .andBankCardEqualTo(cardInfoReq.getBankCard()).andIdCardEqualTo(cardInfoReq.getIdCard());
                LnBindCard yunCard = new LnBindCard();
                yunCard.setStatus(BaoFooEnum.UNBIND_SUCCESS.getCode());
                yunCard.setUpdateTime(new Date());
                lnBindCardMapper.updateByExampleSelective(yunCard, yunCardOpenExample);
            }

            if (needInsert) {
                // 复制绑卡信息（uc_bank_card - > ln_bind_card）
                LnBindCard newCard = new LnBindCard();
                newCard.setIsFirst(Constants.UC_BIND_CARD_NO.equals(ucCard.getIsBind()) ? Constants.IS_FIRST_BANK_NO : Constants.IS_FIRST_BANK_YES);
                newCard.setStatus(BaoFooEnum.BIND_SUCCESS.getCode());
                newCard.setCreateTime(new Date());
                newCard.setUpdateTime(com.pinting.core.util.DateUtil.addSeconds(new Date(), 1));
                newCard.setPartnerCode(partnerEnum.getCode());
                newCard.setLnUserId(lnUserId);
                newCard.setUserName(ucCard.getCardOwner());
                newCard.setMobile(req.getMobile());
                newCard.setIdCard(ucCard.getIdCard());
                newCard.setBankCard(ucCard.getCardNo());
                newCard.setBankCode(req.getBankCode());
                newCard.setBankName(ucCard.getBankName());
                newCard.setPayBindOrderNo(payBindOrderNo);
                lnBindCardMapper.insertSelective(newCard);
            }

            LnUser lnUser = new LnUser();
            lnUser.setId(lnUserId);
            if (StringUtil.isBlank(ucUser.getIdCard())) {
                // 更新uc_user用户信息（姓名，手机号，身份证）
                ucUser.setIdCard(req.getIdCard());
                ucUser.setMobile(req.getMobile());
                ucUser.setUserName(req.getName());

                // 更新ln_user用户信息（姓名，手机号，身份证）
                lnUser.setIdCard(req.getIdCard());
                lnUser.setMobile(req.getMobile());
                lnUser.setUserName(req.getName());
            }

            // 复制恒丰用户编号（uc_user - > ln_user.hf_user_id）
            lnUser.setHfUserId(ucUser.getHfUserId());
            lnUser.setUpdateTime(new Date());
            lnUserMapper.updateByPrimaryKeySelective(lnUser);
        } else {
            // 调用（批量开户）四要素接口
            openAccountBind(partnerEnum.getCode(), ucUser.getId(), lnUserId, cardInfoReq);
        }
        log.info(partnerEnum.getName() + "用户 {} 开户结束", lnUserId);
    }

    /**
     * （批量开户）四要素接口，新借款用户绑卡开户
     *
     * @param partner
     * @param ucUserId
     * @param lnUserId
     * @param req
     */
    private void openAccountBind(String partner, Integer ucUserId, Integer lnUserId, BindCardInfoReq req) {

        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(req.getBankCard());
        lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
        lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnUserId);
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(payBindOrderNo);
        lnPayOrders.setPartnerCode(partner);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getName());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        log.info(PartnerEnum.getEnumByCode(partner).getName() + "用户 {} 未开户，调用恒丰开户四要素绑卡接口。", lnUserId);

        B2GReqMsg_HFBank_BatchBindCard4Elements reqBankCard = new B2GReqMsg_HFBank_BatchBindCard4Elements();
        List<BatchRegistExtDetail> details = new ArrayList<>();
        BatchRegistExtDetail detail = new BatchRegistExtDetail();
        reqBankCard.setOrder_no(Util.generateOrderNo4BaoFoo(8));
        detail.setDetail_no(payBindOrderNo);
        detail.setName(req.getName());
        detail.setId_type(Constants.HF_ID_TYPE_ID_CARD);
        detail.setId_code(req.getIdCard());
        detail.setMobile(req.getMobile());
        detail.setEmail(null);
        detail.setSex(null);
        detail.setCus_type(null);
        detail.setBirthday(null);
        detail.setOpen_branch(null);
        detail.setCard_no(req.getBankCard());
        detail.setCard_type(Constants.HF_CARD_TYPE_DEBIT);
        detail.setPre_mobile(req.getMobile());
        detail.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
        detail.setNotify_url(null);
        detail.setRemark("批量开户(四要素绑卡)");
        details.add(detail);
        reqBankCard.setData(details);
        reqBankCard.setTotal_num(details.size());
        reqBankCard.setPartner_trans_date(new Date());
        reqBankCard.setPartner_trans_time(new Date());
        log.info("批量绑卡请求：{}", JSONObject.fromObject(req));
        boolean communication = true;

        B2GResMsg_HFBank_BatchBindCard4Elements res = new B2GResMsg_HFBank_BatchBindCard4Elements();
        try {
            res = hfbankTransportService.batchBindCard4Elements(reqBankCard);
        } catch (Exception e) {
            communication = false;
            log.error("批量开户(四要素绑卡)请求异常：{}，卡号：{}", e.getMessage(), lnPayOrders.getBankCardNo(), e);
        }

        if (!communication) {
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("通讯异常");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("通讯异常");
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), PartnerEnum.getEnumByCode(partner).getName() + "借款用户" + lnUserId + "调用恒丰(四要素)绑卡失败通讯异常", null, "【" + PartnerEnum.getEnumByCode(partner).getName() + "恒丰(四要素)绑卡通讯异常】");
            throw new PTMessageException(PTMessageEnum.FOUR_ELEMENTS_AUTH_ERROR.getCode(), PartnerEnum.getEnumByCode(partner).getName() + "存管开户失败");
        }

        //4要素开户成功
        if (res != null && !org.springframework.util.CollectionUtils.isEmpty(res.getSuccess_data())
                && StringUtil.isNotEmpty(res.getSuccess_data().get(0).getPlatcust())) {
            String hfUserId = res.getSuccess_data().get(0).getPlatcust();
            //更新ln_user
            LnUser updateLnUser = new LnUser();
            updateLnUser.setId(lnUserId);
            updateLnUser.setUpdateTime(new Date());
            updateLnUser.setHfUserId(hfUserId);
            updateLnUser.setUserName(req.getName());
            updateLnUser.setIdCard(req.getIdCard());
            lnUserMapper.updateByPrimaryKeySelective(updateLnUser);
            //更新uc_user
            UcUser ucUserUp = new UcUser();
            ucUserUp.setId(ucUserId);
            ucUserUp.setHfUserId(hfUserId);
            ucUserUp.setIdCard(req.getIdCard());
            ucUserUp.setUserName(req.getName());
            ucUserUp.setUpdateTime(new Date());
            ucUserMapper.updateByPrimaryKeySelective(ucUserUp);
            //新增uc_bank_card
            UcBankCard ucBankCard = new UcBankCard();
            ucBankCard.setUcUserId(ucUserId);
            ucBankCard.setCardNo(req.getBankCard());
            ucBankCard.setIsBind(Constants.UC_BIND_CARD_YES);
            ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCard.setUpdateTime(new Date());
            ucBankCard.setBindTime(new Date());
            ucBankCard.setCreateTime(new Date());
            ucBankCard.setBankId(bs19payBanks.get(0).getBankId());
            ucBankCard.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
            ucBankCard.setCardOwner(req.getName());
            ucBankCard.setIdCard(req.getIdCard());
            ucBankCard.setMobile(req.getMobile());
            ucBankCardMapper.insertSelective(ucBankCard);

            // 新增ln_bind_card
            LnBindCard newCard = new LnBindCard();
            newCard.setIsFirst(Constants.IS_FIRST_BANK_YES);
            newCard.setPartnerCode(partner);
            newCard.setLnUserId(lnUserId);
            newCard.setUserName(req.getName());
            newCard.setMobile(req.getMobile());
            newCard.setIdCard(req.getIdCard());
            newCard.setBankCard(req.getBankCard());
            newCard.setBankCode(req.getBankCode());
            newCard.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
            newCard.setStatus(BaoFooEnum.BIND_SUCCESS.getCode());
            newCard.setPayBindOrderNo(payBindOrderNo);
            newCard.setCreateTime(new Date());
            newCard.setUpdateTime(new Date());
            lnBindCardMapper.insertSelective(newCard);

            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("绑卡成功");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("绑卡成功");
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);
        } else {
            // 四要素开户失败
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg(res.getResMsg());
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg(res.getResMsg());
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), PartnerEnum.getEnumByCode(partner).getName() + "借款用户" + lnUserId + "调用恒丰(四要素)绑卡失败：" + res.getResMsg(), null, "【" + PartnerEnum.getEnumByCode(partner).getName() + "恒丰(四要素)绑卡失败】");
            throw new PTMessageException(PTMessageEnum.FOUR_ELEMENTS_AUTH_ERROR, res.getResMsg());
        }
    }

    /**
     * 放款通知合作方分发
     *
     * @param agreementNo
     * @param lnLoan
     * @param errorMsg
     */
    private void notifyLoan2Partner(final LnLoan lnLoan, final String errorMsg, final String agreementNo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                B2GReqMsg_DafyLoanNotice_LoanResultNotice noticeLoan = new B2GReqMsg_DafyLoanNotice_LoanResultNotice();
                noticeLoan.setOrderNo(lnLoan.getPartnerOrderNo());
                noticeLoan.setPayChannel(Constants.CHANNEL_HFBANK);
                noticeLoan.setChannel(Constants.CHANNEL_DAFY);
                noticeLoan.setBgwOrderNo(lnLoan.getPayOrderNo());
                noticeLoan.setAgreementNo(agreementNo);
                noticeLoan.setLoanServiceRate(lnLoan.getLoanServiceRate().intValue()); // 云贷获取借款服务费率（100=1%）
                noticeLoan.setLoanId(lnLoan.getPartnerLoanId());
                noticeLoan.setResultCode(lnLoan.getStatus().equals(LoanStatus.LOAN_STATUS_PAIED.getCode()) ? "SUCCESS" : "FAIL");
                noticeLoan.setResultMsg(errorMsg);
                noticeLoan.setFinishTime(lnLoan.getLoanTime() != null ? lnLoan.getLoanTime() : null);
                List<LnLoanRelationVO> loanRelList = loanRelationMapper.selectLendersByLoanId(lnLoan.getId()); //获取出借人列表
                ArrayList<HashMap<String, Object>> lenders = new ArrayList<HashMap<String, Object>>();

                for (LnLoanRelationVO lnLoanRelation : loanRelList) {
                    HashMap<String, Object> map = new HashMap<String, Object>();
                    map.put("investAmount", lnLoanRelation.getApproveAmount());
                    map.put("lenderName", lnLoanRelation.getUserName());
                    map.put("lenderIdcard", lnLoanRelation.getIdCard());
                    map.put("mobile", lnLoanRelation.getMobile());
                    lenders.add(map);
                }
                noticeLoan.setLenders(lenders);
                B2GResMsg_DafyLoanNotice_LoanResultNotice res = null;
                LnLoan loanTemp = new LnLoan();
                loanTemp.setId(lnLoan.getId());
                try {
                    res = dafyNoticeService.noticeLoan(noticeLoan);
                    if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                        loanTemp.setUpdateTime(new Date());
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
                    } else {
                        loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                        loanTemp.setUpdateTime(new Date());
                        lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                        throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                    }
                } catch (Exception e) {
                    loanTemp.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());
                    loanTemp.setUpdateTime(new Date());
                    e.printStackTrace();
                } finally {
                    log.info("更新通知状态为SUCCESS/FAIL");
                    lnLoanMapper.updateByPrimaryKeySelective(loanTemp);
                }
            }
        }).start();
    }

    /**
     * 单客户端解绑
     *
     * @param hfUserId
     * @param ucUserId
     * @param lnUserId
     * @param req
     */
    private void unBind(String hfUserId, Integer ucUserId, Integer lnUserId, BindCardInfoReq req) {
        LnBindCard card = new LnBindCard();

        LnBindCardExample cardExample = new LnBindCardExample();
        cardExample.createCriteria().andLnUserIdEqualTo(lnUserId).andIdCardEqualTo(req.getIdCard()).andBankCardNotEqualTo(req.getBankCard())
                .andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andIsFirstEqualTo(Constants.IS_FIRST_BANK_YES);
        List<LnBindCard> cardList = lnBindCardMapper.selectByExample(cardExample);
        if (CollectionUtils.isNotEmpty(cardList)) {
            card = cardList.get(0);
            Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
            bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
            List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

            // 调用解绑接口
            String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
            //记录ln_pay_orders
            LnPayOrders lnPayOrders = new LnPayOrders();
            lnPayOrders.setCreateTime(new Date());
            lnPayOrders.setAccountType(1);
            lnPayOrders.setBankCardNo(card.getBankCard());
            lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
            lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
            lnPayOrders.setAmount(0d);
            lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
            lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
            lnPayOrders.setLnUserId(lnUserId);
            lnPayOrders.setMobile(card.getMobile());
            lnPayOrders.setIdCard(card.getIdCard());
            lnPayOrders.setMoneyType(0);
            lnPayOrders.setOrderNo(payBindOrderNo);
            lnPayOrders.setPartnerCode(req.getPartnerCode());
            lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
            lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_UN_BIND_CARD.getCode());
            lnPayOrders.setUpdateTime(new Date());
            lnPayOrders.setUserName(req.getName());
            lnPayOrdersMapper.insertSelective(lnPayOrders);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnl.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            B2GReqMsg_HFBank_BatchChangeCard unbindReq = new B2GReqMsg_HFBank_BatchChangeCard();
            unbindReq.setPartner_trans_time(lnPayOrders.getCreateTime());
            unbindReq.setPartner_trans_date(lnPayOrders.getCreateTime());
            unbindReq.setOrder_no(Util.generateOrderNo4BaoFoo(8));
            unbindReq.setTotal_num(1);
            List<BatchUpdateCardExtDetail> data = new ArrayList<>();
            BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
            detail.setPlatcust(hfUserId);
            detail.setCard_no(card.getBankCard());
            detail.setCard_no_old(card.getBankCard());
            detail.setDetail_no(lnPayOrders.getOrderNo());
            detail.setMobile(card.getMobile());
            detail.setName(card.getUserName());
            detail.setRemark("用户解绑卡");
            data.add(detail);
            unbindReq.setData(data);
            B2GResMsg_HFBank_BatchChangeCard res = hfbankTransportService.batchChangeCard(unbindReq);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                if (!CollectionUtils.isEmpty(res.getSuccess_data())) {
                    log.info("用户 {} 解绑卡成功：{}", lnUserId, JSONObject.fromObject(res));
                    lnPayOrders.setStatus(Constants.ORDER_STATUS_SUCCESS);
                    lnPayOrders.setUpdateTime(new Date());
                    lnPayOrders.setNote(detail.getDetail_no());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);

                    //记录ln_pay_orders_jnl表
                    LnPayOrdersJnl succJnl = new LnPayOrdersJnl();
                    succJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                    succJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
                    succJnl.setCreateTime(new Date());
                    succJnl.setOrderId(lnPayOrders.getId());
                    succJnl.setOrderNo(lnPayOrders.getOrderNo());
                    succJnl.setUserId(lnPayOrders.getLnUserId());
                    succJnl.setSysTime(new Date());
                    succJnl.setTransAmount(0d);
                    payOrdersJnlMapper.insertSelective(succJnl);

                    // ln_bind_card 解绑
                    // card.setStatus(BaoFooEnum.UNBIND_SUCCESS.getCode());
                    card.setIsFirst(Constants.IS_FIRST_BANK_NO);
                    card.setUpdateTime(new Date());
                    lnBindCardMapper.updateByPrimaryKeySelective(card);

                    // uc_bind_card is_bind = N
                    UcBankCard ucBankCard = new UcBankCard();
                    // ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_UNBINDED);
                    ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                    ucBankCard.setUpdateTime(new Date());
                    ucBankCard.setUnbindTime(new Date());
                    UcBankCardExample bankExample = new UcBankCardExample();
                    bankExample.createCriteria().andUcUserIdEqualTo(ucUserId).andCardNoEqualTo(card.getBankCard()).andIdCardEqualTo(card.getIdCard());
                    ucBankCardMapper.updateByExampleSelective(ucBankCard, bankExample);
                }
                if (CollectionUtils.isEmpty(res.getSuccess_data())) {
                    log.info("用户 {} 解绑卡失败：{}", lnUserId, JSONObject.fromObject(res));
                    String errorMsg = "";
                    if (!CollectionUtils.isEmpty(res.getError_data())) {
                        lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                        lnPayOrders.setReturnCode(res.getError_data().get(0).getError_no());
                        lnPayOrders.setReturnMsg(res.getError_data().get(0).getError_info());
                        lnPayOrders.setUpdateTime(new Date());
                        lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                        errorMsg = res.getError_data().get(0).getError_info();
                    } else {
                        lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                        lnPayOrders.setReturnCode(res.getResCode());
                        lnPayOrders.setReturnMsg("恒丰返回数据为空");
                        lnPayOrders.setUpdateTime(new Date());
                        lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                        errorMsg = "恒丰返回数据为空";
                    }
                    LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
                    failjnl.setOrderId(lnPayOrders.getId());
                    failjnl.setOrderNo(lnPayOrders.getOrderNo());
                    failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                    failjnl.setTransAmount(0d);
                    failjnl.setSysTime(new Date());
                    failjnl.setUserId(lnPayOrders.getLnUserId());
                    failjnl.setCreateTime(new Date());
                    payOrdersJnlMapper.insertSelective(failjnl);
                    throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败：" + errorMsg);
                }
            } else {
                log.info("用户 {} 解绑卡失败：{}", lnUserId, JSONObject.fromObject(res));
                lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                lnPayOrders.setReturnCode(res.getResCode());
                lnPayOrders.setReturnMsg(res.getResMsg());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
                failjnl.setOrderId(lnPayOrders.getId());
                failjnl.setOrderNo(lnPayOrders.getOrderNo());
                failjnl.setTransAmount(0d);
                failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                failjnl.setSysTime(new Date());
                failjnl.setUserId(lnPayOrders.getLnUserId());
                failjnl.setCreateTime(new Date());
                payOrdersJnlMapper.insertSelective(failjnl);
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败");
            }
        }
    }

    /**
     * 单客户端绑卡
     *
     * @param hfUserId
     * @param ucUserId
     * @param lnUserId
     * @param req
     * @return
     */
    private String bind(String hfUserId, Integer ucUserId, Integer lnUserId, BindCardInfoReq req) {
        String payBindOrderNo = null;
        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(req.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(req.getBankCard());
        lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
        lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(req.getBankCode()));
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnUserId);
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(Util.generateOrderNo4BaoFoo(8));
        lnPayOrders.setPartnerCode(req.getPartnerCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getName());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        // 3、插入订单流水表，状态为创建-1
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        B2GReqMsg_HFBank_UserAddCard addCardReq = new B2GReqMsg_HFBank_UserAddCard();
        addCardReq.setOrder_no(lnPayOrders.getOrderNo());
        addCardReq.setPartner_trans_date(new Date());
        addCardReq.setPartner_trans_time(new Date());
        addCardReq.setType(Constants.HF_BIND_CARD_TYPE_PERSON);//绑卡类型：1个人客户 2 对公客户
        addCardReq.setPlatcust(hfUserId);
        addCardReq.setId_type(Constants.HF_ID_TYPE_ID_CARD);    //1:身份证
        addCardReq.setId_code(lnPayOrders.getIdCard());
        addCardReq.setName(lnPayOrders.getUserName());
        addCardReq.setCard_no(lnPayOrders.getBankCardNo());
        addCardReq.setCard_type(Constants.HF_CARD_TYPE_DEBIT);    //1:借记卡,2:信用卡
        addCardReq.setPre_mobile(lnPayOrders.getMobile());
        addCardReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
        addCardReq.setRemark("个人绑卡");
        log.info("绑卡请求：{}", JSONObject.fromObject(addCardReq));

        B2GResMsg_HFBank_BatchBindCard4Elements res = new B2GResMsg_HFBank_BatchBindCard4Elements();
        boolean communication = true;
        try {
            B2GResMsg_HFBank_UserAddCard addCardRes = hfbankTransportService.userAddCard(addCardReq);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(addCardRes.getResCode())) {
                List<BatchRegistExtSuccess> success_data = new ArrayList<>();
                BatchRegistExtSuccess success = new BatchRegistExtSuccess();
                success.setDetail_no(lnPayOrders.getOrderNo());
                success.setMobile(lnPayOrders.getMobile());
                success.setPlatcust(hfUserId);
                success_data.add(success);
                res.setSuccess_data(success_data);
                res.setSuccess_num("1");
                res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
                res.setResMsg("已开户用户，绑卡成功");
            } else {
                List<BatchRegistExtError> error_data = new ArrayList<>();
                BatchRegistExtError error = new BatchRegistExtError();
                error.setError_no(addCardRes.getResCode());
                error.setError_info(StringUtil.isBlank(addCardRes.getRemsg()) ? addCardRes.getResMsg() : addCardRes.getRemsg());
                error_data.add(error);
                res.setError_data(error_data);
                res.setResCode(error.getError_no());
                res.setResMsg(error.getError_info());
            }
        } catch (Exception e) {
            communication = false;
            log.info("恒丰正式绑卡操作失败 {}，订单号：{}", e.getMessage(), lnPayOrders.getOrderNo());
        }

        if (!communication) {
            // 二、调用正式绑卡接口失败后续操作
            // 1、更新订单表。状态为正式下单失败-7；
            LnPayOrders failOrder = new LnPayOrders();
            failOrder.setId(lnPayOrders.getId());
            failOrder.setUpdateTime(new Date());
            failOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            failOrder.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
            failOrder.setReturnMsg("正式绑卡失败：网络通信异常。");
            lnPayOrdersMapper.updateByPrimaryKeySelective(failOrder);
            // 2、新增订单流水表。
            LnPayOrdersJnl failJnl = new LnPayOrdersJnl();
            failJnl.setOrderId(lnPayOrders.getId());
            failJnl.setOrderNo(lnPayOrders.getOrderNo());
            failJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            failJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            failJnl.setTransAmount(lnPayOrders.getAmount());
            failJnl.setSysTime(new Date());
            failJnl.setUserId(lnUserId);
            failJnl.setCreateTime(new Date());
            failJnl.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
            failJnl.setReturnMsg("正式绑卡失败：网络通信异常。");
            payOrdersJnlMapper.insertSelective(failJnl);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "绑卡失败：网络通信异常");
        }
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && "1".equals(res.getSuccess_num())
                && !org.springframework.util.CollectionUtils.isEmpty(res.getSuccess_data())) {
            log.info("恒丰绑卡操作成功，订单号：{}，{}", lnPayOrders.getOrderNo(), JSONObject.fromObject(res));

            // 1. 新增uc_bank_card
            UcBankCard ucBankCard = new UcBankCard();
            ucBankCard.setIsBind(Constants.UC_BIND_CARD_YES);
            ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCard.setUpdateTime(new Date());
            ucBankCard.setBindTime(new Date());
            UcBankCardExample ucBankCardExample = new UcBankCardExample();
            ucBankCardExample.createCriteria().andCardNoEqualTo(req.getBankCard()).andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCardMapper.updateByExampleSelective(ucBankCard, ucBankCardExample);

            // 2. 更新订单状态
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("绑卡成功");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            // 3. 记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("绑卡成功");
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);


            payBindOrderNo = lnPayOrders.getOrderNo();
            return payBindOrderNo;
        } else {
            // 1. 更新uc_bank_card为失败
            UcBankCard ucBankCard = new UcBankCard();
            ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
            ucBankCard.setStatus(Constants.UC_BIND_CARD_FAIL);
            ucBankCard.setUpdateTime(new Date());
            UcBankCardExample ucBankCardExample = new UcBankCardExample();
            ucBankCardExample.createCriteria().andCardNoEqualTo(req.getBankCard()).andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
            ucBankCardMapper.updateByExampleSelective(ucBankCard, ucBankCardExample);

            // 1. 更新订单状态
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("绑卡失败：" + res.getResMsg());
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            lnPayOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            // 2. 记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("绑卡失败：" + res.getResMsg());
            //lnPayOrdersJnl2.setNote("");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "绑卡失败：" + res.getResMsg());
        }
    }

    /**
     * 插入借款申请记录
     */
    private void insertLoanApplyRecord(G2BReqMsg_DafyLoan_ApplyLoan req) {
        try {
            LnLoanApplyRecord loanApply = new LnLoanApplyRecord();
            loanApply.setApplyAmount(req.getLoanAmount());
            loanApply.setApplyTime(req.getApplyTime());
            loanApply.setBreakMaxDays(req.getBreakMaxDays());
            loanApply.setBreakTimes(req.getBreakTimes());
            loanApply.setHeadFee(req.getLoanFee());
            loanApply.setPartnerLoanId(req.getLoanId());
            loanApply.setCreateTime(new Date());
            loanApply.setUpdateTime(new Date());
            loanApply.setCreditAmount(req.getCreditAmount());
            loanApply.setLoanedAmount(req.getLoanAmount());
            loanApply.setCreditLevel(req.getCreditLevel());
            loanApply.setCreditScore(req.getCreditScore());
            loanApply.setPartnerUserId(req.getUserId());
            loanApply.setLoanTimes(req.getLoanTimes());
            loanApply.setPartnerBusinessFlag(req.getBusinessType());
            loanApply.setPartnerOrderNo(req.getOrderNo());
            loanApply.setPeriod(req.getLoanTerm());
            loanApply.setPurpose(req.getPurpose());
            loanApply.setSubjectName(req.getSubjectName());
            Double agreementRate = MoneyUtil.divide(req.getLoanRate(), 100, 2).doubleValue();
            loanApply.setAgreementRate(agreementRate);
            loanApply.setLoanServiceRate(getSysLoanServerRate());
            loanApply.setBgwSettleRate(getSettleRate());
            lnLoanApplyRecordMapper.insertSelective(loanApply);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 合作方借款编号是否已存在
     *
     * @param partnerLoanId
     * @return true 已存在， false 不存在
     */
    private boolean isExists4PartnerLoanId(String partnerLoanId) {
        LnLoanExample lnLoanExample = new LnLoanExample();
        lnLoanExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId);
        int lnLoanCount = lnLoanMapper.countByExample(lnLoanExample);
        if (lnLoanCount > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询结算费率
     *
     * @return
     */
    protected abstract Double getSysSettleRate();

    /**
     * 查询借款服务费率
     *
     * @return
     */
    protected abstract Double getSysLoanServerRate();

    /**
     * 期数单位
     *
     * @return
     */
    protected abstract Integer getPeriodUnit();

    /**
     * 恒丰标的标的发布，参数：标的利率
     *
     * @param target
     * @param lnLoan
     * @return
     */
    protected abstract String getFinancInterestRate(LnDepositionTarget target, LnLoan lnLoan);
}
