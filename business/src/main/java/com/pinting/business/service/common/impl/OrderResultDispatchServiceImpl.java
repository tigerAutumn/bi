package com.pinting.business.service.common.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.service.common.OrderBusinessService;
import com.pinting.business.service.common.OrderResultDispatchService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by babyshark on 2016/9/11.
 */
@Service
public class OrderResultDispatchServiceImpl implements OrderResultDispatchService {
	
	private Logger logger = LoggerFactory.getLogger(OrderResultDispatchServiceImpl.class);
	
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private OrderBusinessService orderBusinessService;
    @Autowired
    private DepFixedRevenueSettleService depFixedRevenueSettleService;

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    /**
     * 业务分发，外部业务确定订单结果后，调用此分发服务
     * 此服务判断订单所属交易，再调用  OrderBusinessService 中对应的方法
     *
     * @param orderResultInfo 结果对象
     */
    @Override
    public void dispatch(OrderResultInfo orderResultInfo) {

        OrderBusinessService.BusinessType bizType = null;
        //根据订单号获得业务类型
        String orderNo = orderResultInfo.getOrderNo();
        BsPayOrders order = payOrdersService.findOrderByOrderNo(orderNo);
        String channel = "";
        if (order == null) {
            LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
            lnPayOrdersExample.createCriteria().andOrderNoEqualTo(orderNo);
            List<LnPayOrders> lnOrders = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
            if (CollectionUtils.isEmpty(lnOrders)) {
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            LnPayOrders lnOrder = lnOrders.get(0);
            bizType = getBizType(lnOrder.getTransType(), lnOrder.getChannelTransType(), PartnerEnum.getEnumByCode(lnOrder.getPartnerCode()));
            channel = lnOrder.getChannel();
        } else {
            channel = order.getChannel();
            bizType = getBizType(order.getTransType(), order.getChannelTransType(), null);
        }

        //根据业务类型进行通知分发
        dispathBiz(bizType, orderResultInfo, channel);
    }

    /**
     * 根据业务类型进行结果通知的分发
     *
     * @param bizType
     * @param orderResultInfo
     */
    private void dispathBiz(OrderBusinessService.BusinessType bizType, OrderResultInfo orderResultInfo, String channel) {
        // 其他渠道分发（宝付等）
        switch (bizType) {
            case QUICKPAY_TOPUP:
                    if(Constants.CHANNEL_HFBANK.equals(channel)) {
                        orderBusinessService.financeHFTopUp(orderResultInfo);
                    } else {
                        orderBusinessService.financerQuickPayTopUp(orderResultInfo);
                    }
                break;
            case EBANK_TOPUP:
                if(Constants.CHANNEL_HFBANK.equals(channel)) {
                    orderBusinessService.financeHFTopUp(orderResultInfo);
                } else {
                    orderBusinessService.financerEBankTopUp(orderResultInfo);
                }
                break;
            case WITHDRAW:
                if(Constants.CHANNEL_HFBANK.equals(channel)) {
                    orderBusinessService.financeHFWithdraw(orderResultInfo);
                } else {
                    orderBusinessService.financerWithdraw(orderResultInfo);
                }
                break;
            case LOAN:
                if(Constants.CHANNEL_HFBANK.equals(channel)) {
                    orderBusinessService.outOfAccountResultAcceptZan(orderResultInfo);
                } else {
                    orderBusinessService.loanerLoan(orderResultInfo);
                }
                break;
            case YUN_SELF_LOAN:
                orderBusinessService.outOfAccountResultAccept(orderResultInfo);
                break;
            case ZSD_LOAN:
                orderBusinessService.outOfAccountResultAccept(orderResultInfo);
                break;
            case SEVEN_SELF_LOAN:
                orderBusinessService.outOfAccountResultAccept(orderResultInfo);
                break;
            case REPAY:
            	//查询相关订单表
            	LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                lnPayOrdersExample.createCriteria().andOrderNoEqualTo(orderResultInfo.getOrderNo());
                List<LnPayOrders> orderList = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                String idCardNo_zan = CollectionUtils.isEmpty(orderList) ? "" :orderList.get(0).getIdCard();
                try {
                    jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY.getKey()+idCardNo_zan);
                    logger.info("=========赞分期结果通知的分发，加锁："+RedisLockEnum.LOCK_REPAY.getKey()+idCardNo_zan+"=======");
                    orderBusinessService.loanerRepay(orderResultInfo);
                } finally {
                    jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY.getKey()+idCardNo_zan);
                    logger.info("=========赞分期结果通知的分发，解锁："+RedisLockEnum.LOCK_REPAY.getKey()+idCardNo_zan+"=======");
                }
                break;
            case REG_TRANS:
                orderBusinessService.sysRegTrans(orderResultInfo);
                break;
            case REG_RETURN:
                orderBusinessService.financerRegProductReturn(orderResultInfo);
                break;
            case REG_D_RETURN:
                orderBusinessService.financerRegDProductReturn(orderResultInfo);
                break;
            case REVENUE_TRANS:
                orderBusinessService.sysRevenueTrans(orderResultInfo);
                break;
            case MARKET_PAY:
                orderBusinessService.partnerMarketingTrans(orderResultInfo);
                break;
            case WITHDRAW_2_DEP_REPAY_CARD:
                orderBusinessService.withdraw2DepRepayCard(orderResultInfo);
                break;
            case YUN_REVENUE_TRANS:
                orderBusinessService.revenueSettle(orderResultInfo);
                break;
            case SEVEN_DAI_REVENUE_TRANS:
                orderBusinessService.sevenDaiRevenueSettle(orderResultInfo);
                break;
            case YUN_SELF_REPAY:
            	//根据我方还款订单号获取订单信息
                LnPayOrdersExample exampleOrder = new LnPayOrdersExample();
                exampleOrder.createCriteria().andOrderNoEqualTo(orderResultInfo.getOrderNo());
                List<LnPayOrders> lnPayOrders = lnPayOrdersMapper.selectByExample(exampleOrder);
                String idCardNo = CollectionUtils.isEmpty(lnPayOrders) ? "" :lnPayOrders.get(0).getIdCard();
                try {
                    jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo);
                    logger.info("=========云贷结果通知的分发，加锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo +"=======");
                    orderBusinessService.repayResult(orderResultInfo);
                } finally {
                    jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo);
                    logger.info("=========云贷结果通知的分发，解锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.YUN_DAI_SELF.getCode() + idCardNo +"=======");
                }
                break;
            case ZSD_REPAY:
            	//根据我方还款订单号获取订单信息
                LnPayOrdersExample exampleOrder_zsd = new LnPayOrdersExample();
                exampleOrder_zsd.createCriteria().andOrderNoEqualTo(orderResultInfo.getOrderNo());
                List<LnPayOrders> lnPayOrders_zsd  = lnPayOrdersMapper.selectByExample(exampleOrder_zsd );
                String idCardNo_zsd  = CollectionUtils.isEmpty(lnPayOrders_zsd) ? "" :lnPayOrders_zsd.get(0).getIdCard();
                try {
                    jsClientDaoSupport.lock(RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo_zsd );
                    logger.info("=========赞时贷结果通知的分发，加锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo_zsd+"=======");
                    orderBusinessService.repayResult(orderResultInfo);
                } finally {
                    jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo_zsd );
                    logger.info("=========赞时贷结果通知的分发，解锁："+RedisLockEnum.LOCK_ZSD_REPAY.getKey()+idCardNo_zsd+"=======");
                }
                break;
            case SEVEN_DAI_SELF_REPAY:
                // 7贷借款人还款，根据我方还款订单号获取订单信息
                LnPayOrdersExample sevenOrderExample = new LnPayOrdersExample();
                sevenOrderExample.createCriteria().andOrderNoEqualTo(orderResultInfo.getOrderNo());
                List<LnPayOrders> sevenOrder = lnPayOrdersMapper.selectByExample(sevenOrderExample);
                String sevenIdCard = CollectionUtils.isEmpty(sevenOrder) ? "" :sevenOrder.get(0).getIdCard();
                try {
                    jsClientDaoSupport.lock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + sevenIdCard);
                    logger.info("=========云贷结果通知的分发，加锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + sevenIdCard +"=======");
                    orderBusinessService.repayResult(orderResultInfo);
                } finally {
                    jsClientDaoSupport.unlock(RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + sevenIdCard);
                    logger.info("=========云贷结果通知的分发，解锁："+ RedisLockEnum.LOCK_REPAY_SELF.getKey() + PartnerEnum.SEVEN_DAI_SELF.getCode() + sevenIdCard +"=======");
                }
                break;
            case TRANS_TYPE_DF_2_BORROWER:
                orderBusinessService.borrowerDFWithdraw(orderResultInfo);
                break;
            case CUT_REPAY_2_BORROWER:
                orderBusinessService.cutRepayToBorrower(orderResultInfo);
                break;
            default:
                break;
        }

    }

    /**
     * 获得订单业务类型
     *
     * @param transType        交易类型
     * @param channelTransType 渠道交易类型
     * @param partnerEnum 合作方，可以为null
     * @return 返回业务订单类型枚举对象，获取不到则throws异常
     */
    private OrderBusinessService.BusinessType getBizType(String transType, String channelTransType, PartnerEnum partnerEnum) {
        if (Constants.TRANS_TOP_UP.equals(transType) &&
                Constants.CHANNEL_TRS_QUICKPAY.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.QUICKPAY_TOPUP;
        }
        if (Constants.TRANS_TOP_UP.equals(transType) &&
                Constants.CHANNEL_TRS_NETBANK.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.EBANK_TOPUP;
        }
        if (Constants.TRANS_BALANCE_WITHDRAW.equals(transType) &&
                Constants.CHANNEL_TRS_DF.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.WITHDRAW;
        }
        if (Constants.TRANS_BONUS_WITHDRAW.equals(transType) &&
                Constants.CHANNEL_TRS_DF.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.WITHDRAW;
        }
        if (Constants.TRANS_SYS_BUY_DAFY.equals(transType) &&
                Constants.CHANNEL_TRS_TRANSFER.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.REG_TRANS;
        }
        if (Constants.TRANS_SYS_PARTNER_REVENUE.equals(transType) &&
                Constants.CHANNEL_TRS_TRANSFER.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.REVENUE_TRANS;
        }
        if (Constants.TRANS_RETURN_2_USER_BANK_CARD.equals(transType) &&
                Constants.CHANNEL_TRS_DF.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.REG_RETURN;
        }
        if (Constants.TRANS_RETURN_REG_D_2_USER_BANK_CARD.equals(transType) &&
                Constants.CHANNEL_TRS_DF.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.REG_D_RETURN;
        }
        if (LoanStatus.TRANS_TYPE_LOAN.getCode().equals(transType) &&
                LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode().equals(channelTransType)) {
            if(PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerEnum.getCode())){
                return OrderBusinessService.BusinessType.YUN_SELF_LOAN;
            } else if(PartnerEnum.ZAN.getCode().equals(partnerEnum.getCode())){
                return OrderBusinessService.BusinessType.LOAN;
            } else if(PartnerEnum.ZSD.getCode().equals(partnerEnum.getCode())) {
                return OrderBusinessService.BusinessType.ZSD_LOAN;
            } else if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partnerEnum.getCode())) {
                return OrderBusinessService.BusinessType.SEVEN_SELF_LOAN;
            }
        }
        if (LoanStatus.TRANS_TYPE_REPAY.getCode().equals(transType) &&
                LoanStatus.CHANNEL_TRANS_TYPE_AUTH.getCode().equals(channelTransType)) {
            if(PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerEnum.getCode())){
                return OrderBusinessService.BusinessType.YUN_SELF_REPAY;
            } else if(PartnerEnum.ZAN.getCode().equals(partnerEnum.getCode())){
                return OrderBusinessService.BusinessType.REPAY;
            } else if(PartnerEnum.ZSD.getCode().equals(partnerEnum.getCode())) {
                return OrderBusinessService.BusinessType.ZSD_REPAY;
            }
        }
        if (LoanStatus.TRANS_TYPE_REPAY.getCode().equals(transType) &&
                LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode().equals(channelTransType)) {
            if(PartnerEnum.YUN_DAI_SELF.getCode().equals(partnerEnum.getCode())){
                return OrderBusinessService.BusinessType.YUN_SELF_REPAY;
            } else if(PartnerEnum.ZAN.getCode().equals(partnerEnum.getCode())){
                return OrderBusinessService.BusinessType.REPAY;
            } else if(PartnerEnum.ZSD.getCode().equals(partnerEnum.getCode())) {
                return OrderBusinessService.BusinessType.ZSD_REPAY;
            } else if(PartnerEnum.SEVEN_DAI_SELF.getCode().equals(partnerEnum.getCode())) {
                return OrderBusinessService.BusinessType.SEVEN_DAI_SELF_REPAY;
            }
        }
        if (LoanStatus.TRANS_TYPE_MARKET.getCode().equals(transType) &&
                LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode().equals(channelTransType)) {
            return OrderBusinessService.BusinessType.MARKET_PAY;
        }
        if (LoanStatus.TRANS_TYPE_WITHDRAW_2_DEP_REPAY_CARD.getCode().equals(transType) &&
                LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode().equals(channelTransType)) {
            return OrderBusinessService.BusinessType.WITHDRAW_2_DEP_REPAY_CARD;
        }
        if(LoanStatus.TRANS_TYPE_DF_2_BORROWER.getCode().equals(transType) &&
                LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode().equals(channelTransType)) {
            return OrderBusinessService.BusinessType.TRANS_TYPE_DF_2_BORROWER;
        }
        if ((Constants.TRANS_SYS_YUN_REPEAT.equals(transType) ||
                Constants.TRANS_SYS_YUN_LOAN_FEE.equals(transType) ||
                Constants.TRANS_SYS_YUN_REPAY_REVENUE.equals(transType))&&
                Constants.CHANNEL_TRS_TRANSFER.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.YUN_REVENUE_TRANS;
        }
        if (LoanStatus.TRANS_TYPE_CUT_REPAY_2_BORROWER.getCode().equals(transType) &&
                LoanStatus.CHANNEL_TRANS_TYPE_DK.getCode().equals(channelTransType)) {
            return OrderBusinessService.BusinessType.CUT_REPAY_2_BORROWER;
        }
        if ((Constants.TRANS_SYS_SEVEN_REPEAT.equals(transType) ||
                Constants.TRANS_SYS_SEVEN_LOAN_FEE.equals(transType) ||
                Constants.TRANS_SYS_SEVEN_REPAY_REVENUE.equals(transType))&&
                Constants.CHANNEL_TRS_TRANSFER.equals(channelTransType)) {
            return OrderBusinessService.BusinessType.SEVEN_DAI_REVENUE_TRANS;
        }
        throw new PTMessageException(PTMessageEnum.ORDER_BIZTYPE_UNKNOWN);
    }
}
