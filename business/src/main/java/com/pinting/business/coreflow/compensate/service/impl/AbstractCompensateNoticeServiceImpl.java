package com.pinting.business.coreflow.compensate.service.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.coreflow.compensate.util.ConstantsForFields;
import com.pinting.business.coreflow.core.enums.TransCodeEnum;
import com.pinting.business.coreflow.core.model.FlowContext;
import com.pinting.business.coreflow.core.service.DepFixedService;
import com.pinting.business.coreflow.core.service.DispatcherService;
import com.pinting.business.dao.LnCompensateDetailMapper;
import com.pinting.business.dao.LnCompensateMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.service.loan.LateRepayAgreementService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_LateRepayNotice;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 代偿通知抽象实现类
 * Created by zousheng on 2018/6/19.
 */
public abstract class AbstractCompensateNoticeServiceImpl implements DepFixedService, ConstantsForFields {

    private final Logger logger = LoggerFactory.getLogger(AbstractCompensateNoticeServiceImpl.class);

    protected JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private LnCompensateMapper lnCompensateMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    protected LateRepayAgreementService lateRepayAgreementService;
    @Autowired
    private DispatcherService dispatcherService;

    @Override
    public ResMsg execute(FlowContext flowContext) {

        G2BReqMsg_DafyLoan_LateRepayNotice req = (G2BReqMsg_DafyLoan_LateRepayNotice) flowContext.getReq();
        PartnerEnum partner = flowContext.getPartnerEnum();

        // 1.转换代偿信息
        LnCompensate lnCompensate = getLnCompensate(req, partner);
        // 2.转换代偿详情列表
        List<LnCompensateDetail> detailList = getLnCompensateDetails(req);
        // 3.业务前校验
        verifyBusinessBefore(lnCompensate, detailList, flowContext.getPartnerEnum());

        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE.getKey() + lnCompensate.getPartnerCode());

            // 4.代偿通知及明细记录ln_compensate、ln_compensate_detail
            addCompensateInfo(lnCompensate, detailList);

            // 5.代偿通知批量处理
            lateRepayNotice(lnCompensate, detailList, partner);

            return flowContext.getRes();
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_LATE_REPAY_NOTICE.getKey() + lnCompensate.getPartnerCode());
        }
    }

    /**
     * 获取代偿信息
     *
     * @param req
     * @return
     */
    protected LnCompensate getLnCompensate(G2BReqMsg_DafyLoan_LateRepayNotice req, PartnerEnum partner) {
        LnCompensate lnCompensate = new LnCompensate();
        lnCompensate.setApplyTime(req.getApplyTime());
        lnCompensate.setCreateTime(new Date());
        lnCompensate.setFinishTime(req.getFinishTime());
        lnCompensate.setOrderNo(req.getOrderNo());
        lnCompensate.setPartnerCode(partner.getCode());
        lnCompensate.setPayOrderNo(req.getPayOrderNo());
        lnCompensate.setTotalMount(req.getTotalAmount());
        lnCompensate.setUpdateTime(new Date());
        return lnCompensate;
    }

    /**
     * 获取代偿列表详情
     *
     * @param req
     * @return
     */
    protected List<LnCompensateDetail> getLnCompensateDetails(G2BReqMsg_DafyLoan_LateRepayNotice req) {
        List<LnCompensateDetail> detailList = new ArrayList<>();
        ArrayList<HashMap<String, Object>> detailMapList = req.getRepayments();
        for (HashMap<String, Object> detailMap : detailMapList) {
            LnCompensateDetail detailRecord = new LnCompensateDetail();
            detailRecord.setInterest((Double) detailMap.get("interest"));
            detailRecord.setInterestOverdue((Double) detailMap.get("interestOverdue"));
            detailRecord.setPartnerLoanId((String) detailMap.get("loanId"));
            detailRecord.setPartnerRepayId((String) detailMap.get("repayId"));
            detailRecord.setPartnerUserId((String) detailMap.get("userId"));
            detailRecord.setPrincipal((Double) detailMap.get("principal"));
            detailRecord.setPrincipalOverdue((Double) detailMap.get("principalOverdue"));
            detailRecord.setRepaySerial((Integer) detailMap.get("repaySerial"));
            detailRecord.setRepayType((String) detailMap.get("repayType"));
            detailRecord.setTotal((Double) detailMap.get("total"));

            detailList.add(detailRecord);
        }
        return detailList;
    }

    /**
     * 业务开始前数据校验
     *
     * @param lnCompensate
     * @param detailList
     */
    private void verifyBusinessBefore(LnCompensate lnCompensate, List<LnCompensateDetail> detailList, PartnerEnum partner) {

        Double totalMount = 0.0d;
        for (LnCompensateDetail lnCompensateDetail : detailList) {
            totalMount = MoneyUtil.add(totalMount, lnCompensateDetail.getTotal()).doubleValue();
        }

        if (lnCompensate.getTotalMount().compareTo(totalMount) != 0) {
            // 校验代偿总金额与详情信息中总金额不一致，做告警处理
            logger.info(">>>" + partner.getName() + "代偿单号[" + lnCompensate.getOrderNo() + "]代偿总金额[" + lnCompensate.getTotalMount() + "]与明细总金额[" + totalMount + "]不一致！<<<");
            specialJnlService.warnDevelop4Fail(0d, partner.getName() + "代偿单号[" + lnCompensate.getOrderNo() + "]代偿总金额[" + lnCompensate.getTotalMount() + "]与明细总金额[" + totalMount + "]不一致",
                    lnCompensate.getOrderNo(), "代偿还款处理", false);
        }
    }

    /**
     * 代偿通知及明细记录ln_compensate、ln_compensate_detail
     *
     * @param lnCompensate
     * @param detailList
     */
    private void addCompensateInfo(final LnCompensate lnCompensate, final List<LnCompensateDetail> detailList) {
        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //校验数据
                LnCompensateExample compensateExample = new LnCompensateExample();
                compensateExample.createCriteria().andOrderNoEqualTo(lnCompensate.getOrderNo())
                        .andPartnerCodeEqualTo(lnCompensate.getPartnerCode());
                //单号重复
                List<LnCompensate> list = lnCompensateMapper.selectByExample(compensateExample);
                if (CollectionUtils.isNotEmpty(list)) {
                    throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_SAME);
                }
                LnCompensateExample compensateExample4Pay = new LnCompensateExample();
                compensateExample4Pay.createCriteria()
                        .andPayOrderNoEqualTo(lnCompensate.getPayOrderNo())
                        .andPartnerCodeEqualTo(lnCompensate.getPartnerCode());
                //支付单号重复
                List<LnCompensate> list4Pay = lnCompensateMapper.selectByExample(compensateExample4Pay);
                if (CollectionUtils.isNotEmpty(list4Pay)) {
                    throw new PTMessageException(PTMessageEnum.COMPENSATE_NOTICE_SAME);
                }
                lnCompensateMapper.insertSelective(lnCompensate);

                for (LnCompensateDetail lnCompensateDetail : detailList) {
                    lnCompensateDetail.setCompensateId(lnCompensate.getId());
                    lnCompensateDetail.setStatus(Constants.COMPENSATE_REPAYS_STATUS_INIT);
                    lnCompensateDetail.setUpdateTime(new Date());
                    lnCompensateDetail.setCreateTime(new Date());
                    lnCompensateDetailMapper.insertSelective(lnCompensateDetail);
                }
            }
        });
    }

    /**
     * 代偿通知批量处理
     *
     * @param lnCompensate
     * @param detailList
     * @param partner
     */
    public void lateRepayNotice(LnCompensate lnCompensate, List<LnCompensateDetail> detailList, PartnerEnum partner) {

        for (LnCompensateDetail lnCompensateDetail : detailList) {
            String redisKeyIdCard = null;
            String redisKeyPartnerCode = null;
            Integer loanId = null;
            try {
                // 1. 查询用户信息
                LnUser lnUser = getLnUser(lnCompensate, lnCompensateDetail, partner);
                redisKeyIdCard = lnUser.getIdCard(); // 身份证号
                redisKeyPartnerCode = lnUser.getPartnerCode(); // 合作方

                jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKeyPartnerCode + redisKeyIdCard);
                logger.info("=========合作方代偿还款-同代扣还款，加锁：" + RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKeyPartnerCode + redisKeyIdCard + "=======");
                // 2. 查询借款信息
                LnLoan lnLoan = getLnLoan(lnUser.getId(), lnCompensateDetail);
                if (lnLoan == null) {
                    logger.info("===========" + partner.getName() + "【代偿还款】lnCompensateDetail.id=" + lnCompensateDetail.getId() + "未查询到借款信息=============");
                    continue;
                }
                // 3.标识这笔借款正在还款中
                updateLnLoanRepaying(lnLoan.getId(), Constants.DEP_REPAY_REPAYING);
                loanId = lnLoan.getId();

                // 4.代偿还款分账处理
                FlowContext flowContext = new FlowContext();
                flowContext.setTransCode(TransCodeEnum.COMPENSATE_REPAY_SPLIT.getCode());
                flowContext.setBusinessType(lnLoan.getPartnerBusinessFlag());
                flowContext.setPartnerEnum(partner);
                flowContext.setExtendData(LnLoan.class.getSimpleName(), lnLoan);
                flowContext.setExtendData(LnCompensateDetail.class.getSimpleName(), lnCompensateDetail);
                flowContext.setExtendData(LnCompensate.class.getSimpleName(), lnCompensate);
                dispatcherService.dispatcherService(flowContext);
            } catch (Exception e) {
                // 6.单笔代偿还款异常告警
                logger.error(partner.getName() + "代偿明细编号[" + lnCompensateDetail.getId() + "]处理异常", e);
                specialJnlService.warn4Fail(lnCompensateDetail.getTotal(), partner.getName() + "代偿明细编号[" + lnCompensateDetail.getId() + "]处理异常",
                        lnCompensate.getOrderNo(), "代偿明细处理", false);

                // 标识代偿失败
                updateLnCompensateDetail(lnCompensateDetail.getId(), Constants.COMPENSATE_REPAYS_STATUS_FAIL);
            } finally {
                jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKeyPartnerCode + redisKeyIdCard);
                logger.info("=========合作方代偿还款-同代扣还款，解锁：" + RedisLockEnum.LOCK_REPAY_SELF.getKey() + redisKeyPartnerCode + redisKeyIdCard + "=======");
                // 7.本次代偿业务结束时，这笔借款标识为可还款
                if (loanId != null) {
                    updateLnLoanRepaying(loanId, Constants.DEP_REPAY_AVAILABLE);
                }
            }
        }
    }

    /**
     * 查询借款用户信息
     *
     * @param lnCompensate
     * @param lnCompensateDetail
     * @param partner
     * @return
     */
    private LnUser getLnUser(LnCompensate lnCompensate, LnCompensateDetail lnCompensateDetail, PartnerEnum partner) {
        LnUserExample lnUserExample = new LnUserExample();
        lnUserExample.createCriteria().andPartnerUserIdEqualTo(lnCompensateDetail.getPartnerUserId())
                .andPartnerCodeEqualTo(lnCompensate.getPartnerCode());
        List<LnUser> lnUserList = lnUserMapper.selectByExample(lnUserExample);
        if (CollectionUtils.isEmpty(lnUserList)) {
            logger.info("===========" + partner.getName() + "【代偿还款】借款用户数据未获得================");
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        return lnUserList.get(0);
    }

    /**
     * 查询借款信息
     *
     * @param lnUserId
     * @param lnCompensateDetail
     * @return
     */
    private LnLoan getLnLoan(Integer lnUserId, LnCompensateDetail lnCompensateDetail) {
        LnLoanExample loanExample = new LnLoanExample();
        loanExample.createCriteria().andPartnerLoanIdEqualTo(lnCompensateDetail.getPartnerLoanId())
                .andLnUserIdEqualTo(lnUserId).andStatusEqualTo(LoanStatus.LOAN_STATUS_PAIED.getCode());
        List<LnLoan> lnLoanList = lnLoanMapper.selectByExample(loanExample);
        if (CollectionUtils.isNotEmpty(lnLoanList)) {
            return lnLoanList.get(0);
        }
        return null;
    }

    /**
     * 借款状态置代偿处理中(还款处理中 / 可还款)
     *
     * @param loanId
     */
    private void updateLnLoanRepaying(Integer loanId, String isRepaying) {
        LnLoan loanUpdate = new LnLoan();
        loanUpdate.setId(loanId);
        loanUpdate.setIsRepaying(isRepaying);
        loanUpdate.setUpdateTime(new Date());
        lnLoanMapper.updateByPrimaryKeySelective(loanUpdate);
    }

    /**
     * 代偿状态更新
     *
     * @param lnCompensateDetailId
     * @param status
     */
    private void updateLnCompensateDetail(Integer lnCompensateDetailId, String status) {
        LnCompensateDetail detail = new LnCompensateDetail();
        detail.setId(lnCompensateDetailId);
        detail.setStatus(status);
        detail.setUpdateTime(new Date());
        lnCompensateDetailMapper.updateByPrimaryKeySelective(detail);
    }
}
