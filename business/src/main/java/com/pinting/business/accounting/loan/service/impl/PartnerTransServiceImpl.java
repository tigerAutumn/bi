package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.DFResultInfo;
import com.pinting.business.accounting.loan.service.PartnerTransService;
import com.pinting.business.accounting.loan.service.impl.process.MarketingProcess;
import com.pinting.business.accounting.loan.util.redisUtil;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.enums.TransTypeEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.CommissionVO;
import com.pinting.business.model.vo.LnLoanVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.service.common.CommissionService;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_BalanceQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_BalanceQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_PartnerPay4Trans;
import com.pinting.gateway.hessian.message.loan.*;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.loan.NoticeService;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.List;

/**
 * Created by 剑钊
 *
 * @2016/10/18 10:55.
 */
@Service
public class PartnerTransServiceImpl implements PartnerTransService {

    private Logger log = LoggerFactory.getLogger(PartnerTransServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private LnMarketGrantRecordMapper lnMarketGrantRecordMapper;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private BsPayResultQueueMapper queueMapper;
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private CommissionService commissionService;
    @Autowired
    private BsServiceFeeMapper bsServiceFeeMapper;

    @Override
    @Transactional
    public void marketingTrans(G2BReqMsg_Partner_MarketingTrans req) {

        //判断营销订单是否重复提交
        LnMarketGrantRecordExample example=new LnMarketGrantRecordExample();
        example.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());
        List<LnMarketGrantRecord> recordList=lnMarketGrantRecordMapper.selectByExample(example);

        if(CollectionUtils.isNotEmpty(recordList) && recordList.size()>0){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号重复");
        }

        //检查绑卡表，如果客户对应的卡未绑定，则返回相应错误
        LnBindCard lnBindCard = loanUserService.queryLoanBindCardExist(req.getUserId(), req.getBindId(), req.getChannel());

        if(lnBindCard==null){
            throw new PTMessageException(PTMessageEnum.ZAN_LOAN_USER_CARD_UNBIND);
        }

        //支付订单号
        String orderNo= Util.generateOrderNo4BaoFoo(8);

        //营销记录
        LnMarketGrantRecord record=new LnMarketGrantRecord();
        record.setPartnerOrderNo(req.getOrderNo());
        record.setOrderNo(orderNo);
        record.setAmount(MoneyUtil.divide(req.getAmount(),"100").doubleValue());
        record.setBgwBindId(req.getBindId());
        record.setCreateTime(new Date());
        record.setGrantItem(req.getPurpose());
        record.setPartnerCode(req.getChannel());
        record.setPartnerUserId(req.getUserId());
        record.setStatus(Constants.ORDER_TRANS_CODE_INIT);
        record.setUpdateTime(new Date());
        record.setInformStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
        lnMarketGrantRecordMapper.insertSelective(record);

        //订单记录
        LnPayOrders lnPayOrders=new LnPayOrders();
        lnPayOrders.setOrderNo(orderNo);
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setPartnerCode(req.getChannel());
        lnPayOrders.setStatus(Constants.ORDER_STATUS_CREATE);
        lnPayOrders.setAccountType(2);
        lnPayOrders.setAmount(record.getAmount());
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
        lnPayOrders.setBankName(lnBindCard.getBankName());
        lnPayOrders.setChannel(Constants.CHANNEL_BAOFOO);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrders.setIdCard(lnBindCard.getIdCard());
        lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
        lnPayOrders.setMobile(lnBindCard.getMobile());
        BsCardBin bsCardBin = bankCardService.queryBankBin(lnBindCard.getBankCard());
        lnPayOrders.setBankId(bsCardBin.getBankId());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setUserName(lnBindCard.getUserName());
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_MARKET.getCode());

        lnPayOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());

        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        //发起代付请求，给营销客户支付款项
        B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans pay4Trans = new B2GReqMsg_BaoFooQuickPay_PartnerPay4Trans();
        pay4Trans.setTo_acc_no(lnBindCard.getBankCard());
        //金额为营销记录中以元为单位的数值
        pay4Trans.setTrans_money(String.valueOf(record.getAmount()));
        pay4Trans.setTo_acc_name(lnBindCard.getUserName());
        pay4Trans.setTo_bank_name(lnBindCard.getBankName());
        pay4Trans.setTrans_no(lnPayOrders.getOrderNo());
        pay4Trans.setPartner(req.getChannel());
        pay4Trans.setTrans_card_id(lnBindCard.getIdCard());
        pay4Trans.setTrans_mobile(lnBindCard.getMobile());
        pay4Trans.setTrans_summary((PartnerEnum.getEnumByCode(req.getChannel())!=null?PartnerEnum.getEnumByCode(req.getChannel()).getName():"")+"营销代付");
        B2GResMsg_BaoFooQuickPay_PartnerPay4Trans res = null;
        try {
            res = baoFooTransportService.partnerPay4Trans(pay4Trans);

        } catch (Exception e) {
            e.printStackTrace();
            //修改Ln_pay_orders,状态为支付中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, null, "通讯失败，置为处理中");
            //正在处理中
            LnMarketGrantRecord record1=new LnMarketGrantRecord();
            record1.setId(record.getId());
            record1.setUpdateTime(new Date());
            record1.setStatus(Constants.ORDER_TRANS_CODE_PROCCESS);
            lnMarketGrantRecordMapper.updateByPrimaryKeySelective(record1);

            //放到redis中
            LoanNoticeVO vo = new LoanNoticeVO();
            vo.setPayOrderNo(lnPayOrders.getOrderNo());
            vo.setChannel(lnPayOrders.getChannel());
            vo.setChannelTransType(lnPayOrders.getChannelTransType());
            vo.setTransType(lnPayOrders.getTransType());
            vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
            vo.setClientKey(req.getChannel());
            vo.setAmount(MoneyUtil.defaultRound(lnPayOrders.getAmount()).toString());
            redisUtil.rpushRedis(vo);

            //并插入到消息队列表中
            BsPayResultQueue queue = new BsPayResultQueue();
            queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
            queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
            queue.setCreateTime(new Date());
            queue.setDealNum(0);
            queue.setOrderNo(lnPayOrders.getOrderNo());
            queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            queue.setTransType(lnPayOrders.getTransType());
            queue.setUpdateTime(new Date());
            queueMapper.insertSelective(queue);

            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PAYING.getCode());
            //记录ln_pay_orders_jnl表
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnl.setReturnCode(res.getResCode());
            lnPayOrdersJnl.setReturnMsg(res.getResMsg());

            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
        }

        if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {

            //代付成功
            DFResultInfo resultInfo = new DFResultInfo();
            resultInfo.setAmount(lnPayOrders.getAmount());
            resultInfo.setFinishTime(new Date());
            resultInfo.setMxOrderId(lnPayOrders.getOrderNo());
            resultInfo.setOrderStatus(OrderStatus.SUCCESS.getCode());
            resultInfo.setSysOrderId(null);
            resultInfo.setRetCode(res.getResCode());
            resultInfo.setErrorMsg(res.getResMsg());
            //修改Ln_pay_orders,状态为支付中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, null, null);
            LnMarketGrantRecord record1=new LnMarketGrantRecord();
            record1.setId(record.getId());
            record1.setUpdateTime(new Date());
            record1.setStatus(Constants.ORDER_TRANS_CODE_PROCESS);
            lnMarketGrantRecordMapper.updateByPrimaryKeySelective(record1);

            notifyMarketing(resultInfo);

        } else if (res.getResCode().equals(LoanStatus.BAOFOO_PAY_STATUS_PROCESS.getCode())) {
            //修改Ln_pay_orders,状态为支付中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, null, null);
            LnMarketGrantRecord record1=new LnMarketGrantRecord();
            record1.setId(record.getId());
            record1.setUpdateTime(new Date());
            record1.setStatus(Constants.ORDER_TRANS_CODE_PROCESS);
            lnMarketGrantRecordMapper.updateByPrimaryKeySelective(record1);
            //放到redis中
            LoanNoticeVO vo = new LoanNoticeVO();
            vo.setPayOrderNo(lnPayOrders.getOrderNo());
            vo.setChannel(lnPayOrders.getChannel());
            vo.setChannelTransType(lnPayOrders.getChannelTransType());
            vo.setTransType(lnPayOrders.getTransType());
            vo.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()));
            vo.setAmount(MoneyUtil.defaultRound(lnPayOrders.getAmount()).toString());
            vo.setClientKey(req.getChannel());
            redisUtil.rpushRedis(vo);

            //并插入到消息队列表中
            BsPayResultQueue queue = new BsPayResultQueue();
            queue.setChannel(Constants.ORDER_CHANNEL_BAOFOO);
            queue.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
            queue.setCreateTime(new Date());
            queue.setDealNum(0);
            queue.setOrderNo(lnPayOrders.getOrderNo());
            queue.setStatus(LoanStatus.NOTICE_STATUS_INIT.getCode());
            queue.setTransType(lnPayOrders.getTransType());
            queue.setUpdateTime(new Date());
            queueMapper.insertSelective(queue);

            //记录ln_pay_orders_jnl表
            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PAYING.getCode());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setTransAmount(lnPayOrders.getAmount());
            lnPayOrdersJnl.setUserId(lnBindCard.getLnUserId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnl.setReturnCode(res.getResCode());
            lnPayOrdersJnl.setReturnMsg(res.getResMsg());

            lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        } else {
            //代付失败
            //修改Ln_pay_orders,状态为支付中
            payOrdersService.modifyLnOrderStatus4Safe(lnPayOrders.getId(), Integer.parseInt(LoanStatus.ORDERS_STATUS_PAYING.getCode()),
                    null, res.getResCode(), res.getResMsg());
            DFResultInfo resultInfo = new DFResultInfo();
            resultInfo.setAmount(lnPayOrders.getAmount());
            resultInfo.setFinishTime(new Date());
            resultInfo.setMxOrderId(lnPayOrders.getOrderNo());
            resultInfo.setOrderStatus(OrderStatus.FAIL.getCode());
            resultInfo.setSysOrderId(null);
            resultInfo.setErrorMsg(res.getResMsg());
            resultInfo.setRetCode(res.getResCode());

            LnMarketGrantRecord record1=new LnMarketGrantRecord();
            record1.setId(record.getId());
            record1.setUpdateTime(new Date());
            record1.setStatus(Constants.ORDER_TRANS_CODE_PROCESS);
            lnMarketGrantRecordMapper.updateByPrimaryKeySelective(record1);
            notifyMarketing(resultInfo);
        }
    }


    @Override
    public void notifyMarketing(final DFResultInfo req) {

        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_MARKETING.getKey());

            //根据支付订单号查询营销信息表
            LnMarketGrantRecordExample example=new LnMarketGrantRecordExample();
            example.createCriteria().andOrderNoEqualTo(req.getMxOrderId());
            List<LnMarketGrantRecord> recordList=lnMarketGrantRecordMapper.selectByExample(example);

            if (CollectionUtils.isEmpty(recordList)) {
                throw new PTMessageException(PTMessageEnum.ZAN_MARKET_ORDER_NO_NOT_EXIST);
            }

            final LnMarketGrantRecord oldRecord=recordList.get(0);
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    //查询相关订单表
                    LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                    lnPayOrdersExample.createCriteria().andOrderNoEqualTo(req.getMxOrderId());
                    List<LnPayOrders> orderList = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                    if (CollectionUtils.isEmpty(orderList)) {
                        throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
                    }
                    LnPayOrders order = lnPayOrdersMapper.selectByPKForLock(orderList.get(0).getId());
                    if (order.getStatus() == Constants.ORDER_STATUS_PAYING) {

                        //修改营销订单状态
                        LnMarketGrantRecord record=new LnMarketGrantRecord();
                        record.setId(oldRecord.getId());
                        record.setUpdateTime(new Date());

                        //修改ln_pay_orders状态
                        LnPayOrders payOrdersTemp = new LnPayOrders();
                        payOrdersTemp.setId(order.getId());
                        payOrdersTemp.setUpdateTime(new Date());
                        payOrdersTemp.setReturnCode(req.getRetCode());
                        payOrdersTemp.setReturnMsg(req.getErrorMsg());
                        //记录ln_pay_orders_jnl表
                        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();

                        if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                            payOrdersTemp.setStatus(Constants.ORDER_STATUS_SUCCESS);
                            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                            record.setStatus(Constants.ORDER_TRANS_CODE_SUCCESS);
                            record.setFinishTime(new Date());

                            // 记录手续费表
                            CommissionVO commissionVO = commissionService.calServiceFee(req.getAmount(), TransTypeEnum.PARTNER_MARKET_FEE, PayPlatformEnum.BAOFOO);
                            //记录手续费
                            BsServiceFee bsServiceFee = new BsServiceFee();
                            bsServiceFee.setPlanFee(commissionVO.getNeedPayAmount());
                            bsServiceFee.setDoneFee(commissionVO.getActPayAmount());
                            bsServiceFee.setTransAmount(req.getAmount());
                            bsServiceFee.setFeeType(Constants.FEE_TYPE_PARTNER_MARKET_FEE);
                            bsServiceFee.setStatus(Constants.FEE_STATUS_COLLECT);
                            bsServiceFee.setCreateTime(new Date());
                            bsServiceFee.setOrderNo(order.getOrderNo());
                            bsServiceFee.setSubAccountId(order.getSubAccountId());
                            bsServiceFee.setUpdateTime(new Date());
                            bsServiceFee.setNote("应扣" + commissionVO.getNeedPayAmount() + "，实扣" + commissionVO.getActPayAmount());
                            bsServiceFee.setPaymentPlatformFee(commissionVO.getThreePartyPaymentServiceFee());
                            bsServiceFeeMapper.insertSelective(bsServiceFee);

                        } else {
                            payOrdersTemp.setStatus(Constants.ORDER_STATUS_FAIL);
                            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                            record.setStatus(Constants.ORDER_TRANS_CODE_FAIL);
                        }

                        lnMarketGrantRecordMapper.updateByPrimaryKeySelective(record);
                        lnPayOrdersMapper.updateByPrimaryKeySelective(payOrdersTemp);

                        oldRecord.setStatus(record.getStatus());
                        oldRecord.setUpdateTime(record.getUpdateTime());

                        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_DF.getCode());
                        lnPayOrdersJnl.setCreateTime(new Date());
                        lnPayOrdersJnl.setOrderId(order.getId());
                        lnPayOrdersJnl.setOrderNo(order.getOrderNo());
                        lnPayOrdersJnl.setTransAmount(order.getAmount());
                        lnPayOrdersJnl.setUserId(order.getLnUserId());
                        lnPayOrdersJnl.setSysTime(new Date());
                        lnPayOrdersJnl.setReturnCode(req.getRetCode());
                        lnPayOrdersJnl.setReturnMsg(req.getErrorMsg());

                        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                        //修改支付结果表状态
                        BsPayResultQueueExample queueExample = new BsPayResultQueueExample();
                        queueExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                        List<BsPayResultQueue> queueList = queueMapper.selectByExample(queueExample);

                        if (CollectionUtils.isNotEmpty(queueList) && queueList.size() > 0) {
                            BsPayResultQueue queueTemp = new BsPayResultQueue();
                            queueTemp.setId(queueList.get(0).getId());
                            queueTemp.setUpdateTime(new Date());
                            if (OrderStatus.SUCCESS.getCode().equals(req.getOrderStatus())) {
                                queueTemp.setStatus("SUCC");
                            } else {
                                queueTemp.setStatus("FAIL");
                            }
                            queueMapper.updateByPrimaryKeySelective(queueTemp);
                        }

                    }
                }
            });
            //起线程通知
            MarketingProcess marketingProcess=new MarketingProcess();
            marketingProcess.setErrMsg(req.getErrorMsg());
            marketingProcess.setPartnerTransService(this);
            marketingProcess.setRecord(oldRecord);
            new Thread(marketingProcess).start();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_MARKETING.getKey());
        }
    }

    @Override
    public LnLoanVO queryMarketingTransStatus(G2BReqMsg_Partner_QueryMarketingTrans req) {

        if(StringUtils.isBlank(req.getOrderNo())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "订单号为空");
        }


        LnMarketGrantRecordExample example=new LnMarketGrantRecordExample();
        example.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo());

        List<LnMarketGrantRecord> recordList=lnMarketGrantRecordMapper.selectByExample(example);
        if(CollectionUtils.isNotEmpty(recordList) && recordList.size()>0){

            LnPayOrdersExample ordersExample = new LnPayOrdersExample();
            ordersExample.createCriteria().andOrderNoEqualTo(recordList.get(0).getOrderNo());
            List<LnPayOrders> ordersList = lnPayOrdersMapper.selectByExample(ordersExample);

            LnLoanVO lnLoanVO=new LnLoanVO();
            lnLoanVO.setPartnerOrderNo(req.getOrderNo());
            lnLoanVO.setLoanTime(DateUtil.format(recordList.get(0).getFinishTime()));
            if(recordList.get(0).getStatus().equals(Constants.ORDER_TRANS_CODE_SUCCESS)){
                lnLoanVO.setStatus("SUCCESS");
            }else if(recordList.get(0).getStatus().equals(Constants.ORDER_TRANS_CODE_FAIL)){
                lnLoanVO.setStatus("FAIL");
            }else {
                lnLoanVO.setStatus("PROCESS");
            }
            lnLoanVO.setChannel(ordersList.get(0).getChannel());
            lnLoanVO.setReturnMsg(ordersList.get(0).getReturnMsg());

            return lnLoanVO;
        }

        return null;
    }

    @Override
    public String queryBalance(G2BReqMsg_Partner_QueryBalance req) {
        B2GReqMsg_BaoFooQuickPay_BalanceQuery balanceQuery=new B2GReqMsg_BaoFooQuickPay_BalanceQuery();
        balanceQuery.setPartner(req.getChannel());
        B2GResMsg_BaoFooQuickPay_BalanceQuery res;

        try{
            res=baoFooTransportService.queryBalance(balanceQuery);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
        }

        if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)){
            return res.getBalance();
        }else {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
        }


    }

    /**
     * 通知合作方
     *
     * @param record
     * @param errorMsg
     */
    public void notify2Partner(LnMarketGrantRecord record, String errorMsg) {

        B2GReqMsg_MarketNotice_NoticeMarketTrans noticeMarketTrans = new B2GReqMsg_MarketNotice_NoticeMarketTrans();
        noticeMarketTrans.setOrderNo(record.getPartnerOrderNo());
        LnPayOrdersExample ordersExample = new LnPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(record.getOrderNo());
        List<LnPayOrders> ordersList = lnPayOrdersMapper.selectByExample(ordersExample);
        if (CollectionUtils.isNotEmpty(ordersList)) {
            noticeMarketTrans.setChannel(ordersList.get(0).getChannel());
        }
        noticeMarketTrans.setResultCode(record.getStatus().equals(Constants.ORDER_TRANS_CODE_SUCCESS) ? "SUCCESS" : "FAIL");
        noticeMarketTrans.setResultMsg(errorMsg);
        noticeMarketTrans.setPayTime(record.getFinishTime() != null ? DateUtil.format(record.getFinishTime()) : null);
        B2GResMsg_MarketNotice_NoticeMarketTrans res = null;



        try {
            res = noticeService.noticeMarketTrans(noticeMarketTrans);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                //更新通知状态
                record.setInformStatus(LoanStatus.NOTICE_STATUS_SUCCESS.getCode());
            } else {
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }
        } catch (Exception e) {
            //更新通知状态
            record.setInformStatus(LoanStatus.NOTICE_STATUS_FAIL.getCode());

            //入redis
            LoanNoticeVO loanNoticeVO = new LoanNoticeVO();
            loanNoticeVO.setPayOrderNo(record.getOrderNo());
            loanNoticeVO.setChannel(ordersList.get(0).getChannel());
            loanNoticeVO.setChannelTransType(ordersList.get(0).getChannelTransType());
            loanNoticeVO.setTransType(ordersList.get(0).getTransType());
            loanNoticeVO.setStatus(ordersList.get(0).getStatus());
            loanNoticeVO.setAmount(ordersList.get(0).getAmount().toString());
            loanNoticeVO.setClientKey(ordersList.get(0).getPartnerCode());
            loanNoticeVO.setReturnMsg(errorMsg);
            loanNoticeVO.setFinishTime(record.getUpdateTime());
            redisUtil.rpushRedis(loanNoticeVO);
            e.printStackTrace();
        }
        record.setUpdateTime(new Date());
        lnMarketGrantRecordMapper.updateByPrimaryKeySelective(record);

    }
   
}
