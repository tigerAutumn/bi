package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.ProductType;
import com.pinting.business.accounting.finance.model.SubAccountCode;
import com.pinting.business.accounting.finance.service.SysReturnMoneyService;
import com.pinting.business.accounting.finance.service.UserReturnMoneyService;
import com.pinting.business.accounting.finance.service.impl.process.UserReturnMoneyProcess;
import com.pinting.business.accounting.loan.enums.LoanSubjects;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.model.PartnerSysActCode;
import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.enums.ThirdSysChannelEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.model.vo.SysReturnManageInfoVO;
import com.pinting.business.model.vo.SysReturnManageResVO;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.service.site.SubAccountService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysReturnMoneyNotice;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_AcctTrans_QueryRecvAcctTrans;
import com.pinting.gateway.hessian.message.pay19.enums.TradeResult;
import com.pinting.gateway.out.service.Pay19TransportService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by babyshark on 2016/9/9.
 */
@Service
public class SysReturnMoneyServiceImpl implements SysReturnMoneyService {
    private Logger log = LoggerFactory.getLogger(SysReturnMoneyServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private Pay19TransportService pay19TransportService;
    @Autowired
    private SubAccountService subAccountService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsBatchBuyDetailMapper bsBatchBuyDetailMapper;
    @Autowired
    private BsBatchBuyMapper bsBatchBuyMapper;
    @Autowired
    private BsProductMapper bsProductMapper;
    @Autowired
    private BsSysSubAccountMapper sysAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper sysAccountJnlMapper;
    @Autowired
    private BsSysReceiveMoneyMapper bsSysReceiveMoneyMapper;
    @Autowired
    private UserReturnMoneyService userReturnMoneyService;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsSysConfigService bsSysConfigService;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private BsBatchReturnDetailMapper bsBatchReturnDetailMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;
    @Autowired
    private LnFinanceRepayScheduleMapper lnFinanceRepayScheduleMapper;
    @Autowired
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    @Autowired
    private LnDepositionRepayScheduleMapper lnDepositionRepayScheduleMapper;
    @Autowired
    private LnDepositionRepayScheduleDetailMapper lnDepositionRepayScheduleDetailMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private AccountHandleService accountHandleService;
    @Autowired
    private BsSubAccountMapper subAccountMapper;
    @Autowired
    private BsSubAccountPairMapper bsSubAccountPairMapper;
    @Autowired
    private BsTermProductCodeMapper bsTermProductCodeMapper;
    
    /**
     * 系统回款通知处理
     *
     * @param req
     */
    @Override
    public void notifySysReturnMoney(final G2BReqMsg_DafyPayment_SysReturnMoneyNotice req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
            String channel = req.getChannel();
            final String channelName = ThirdSysChannelEnum.getEnumByCode(channel).getName();
            final String actCode = Util.getSysReturnSubActCode(ThirdSysChannelEnum.getEnumByCode(channel));

            log.info("========={" + channelName + "系统回款通知处理}开始=========");
            String oriOutOrderId = req.getPayOrderNo();
            String oriOutMxId = req.getMerchantId();
            Date oriOutOrderDate = req.getPayReqTime();

            //根据理财购买表，判断本次通知是否有效
            BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
            batchBuyExample.createCriteria().andSendBatchIdEqualTo(req.getProductOrderNo());
            List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
            if(batchBuys==null || batchBuys.size()<=0){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            if(Constants.BATCHBUY_STATUS_RETURN_SUCCESS.equals(batchBuys.get(0).getStatus())){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
            }
            if(!channel.equals(batchBuys.get(0).getPropertySymbol())){
                throw new PTMessageException(PTMessageEnum.RETURN_CHANNEL_ERROR);
            }

            //判断最后一期回款时前面利息回款是都已经全部处理结束
            if (Integer.valueOf(req.getProductReturnTerm()) >1) {
                BsSysReceiveMoneyExample receiveExample = new BsSysReceiveMoneyExample();
                receiveExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo()).andTypeEqualTo(Constants.RETURN_NOTICE_TYPE_INTEREST).andStatusEqualTo(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
                List<BsSysReceiveMoney> sysReceiveMoneyList = bsSysReceiveMoneyMapper.selectByExample(receiveExample);
                if (sysReceiveMoneyList.size() + 1 != Integer.parseInt(req.getProductReturnTerm())) {
                    log.info("========={" + channelName + "系统回款通知处理}异常回款通知，原因：最后期回款必须完成利息回款=========");
                    //告警
                    specialJnlService.warn4Fail(req.getAmount(),  "{" + channelName + "理财回款通知}产品批次号["+req.getProductOrderNo()+"]异常回款通知，原因：最后期回款必须完成利息回款", req.getProductOrderNo(),channelName + "未完成利息回款",false);

                    throw new PTMessageException(PTMessageEnum.RETURN_LAST_ERROR);
                }
            }

            //根据通知记录表，判断本次通知是否已处理
            BsSysReceiveMoneyExample receiveMoneyExample = new BsSysReceiveMoneyExample();
            receiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                    .andProductReturnTermEqualTo(req.getProductReturnTerm());
            List<BsSysReceiveMoney> sysReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(receiveMoneyExample);
            if (sysReceiveMoneys != null && sysReceiveMoneys.size() > 0) {
                // 已处理成功
                if (Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS.equals(sysReceiveMoneys.get(0).getStatus())) {
                    throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
                }
                // 未处理成功
                else{
                    //修改为最新数据
                    BsSysReceiveMoney receiveMoney = new BsSysReceiveMoney();
                    receiveMoney.setId(sysReceiveMoneys.get(0).getId());
                    receiveMoney.setType(Constants.RETURN_NOTICE_TYPE_LAST);
                    receiveMoney.setAmount(req.getAmount());
                    receiveMoney.setPayFinshTime(req.getPayFinshTime());
                    receiveMoney.setPayOrderNo(req.getPayOrderNo());
                    receiveMoney.setPayReqTime(req.getPayReqTime());
                    receiveMoney.setProductAmount(req.getProductAmount());
                    receiveMoney.setProductCode(req.getProductCode());
                    receiveMoney.setProductInterest(req.getProductInterest());
                    receiveMoney.setUpdateTime(new Date());
                    bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(receiveMoney);
                }
            }
            // 无通知记录，插入一条
            else {
                BsSysReceiveMoney receiveMoney = new BsSysReceiveMoney();
                receiveMoney.setAmount(req.getAmount());
                receiveMoney.setCreateTime(new Date());
                receiveMoney.setPayFinshTime(req.getPayFinshTime());
                receiveMoney.setPayOrderNo(req.getPayOrderNo());
                receiveMoney.setPayReqTime(req.getPayReqTime());
                receiveMoney.setProductAmount(req.getProductAmount());
                receiveMoney.setProductCode(req.getProductCode());
                receiveMoney.setProductInterest(req.getProductInterest());
                receiveMoney.setProductOrderNo(req.getProductOrderNo());
                receiveMoney.setProductReturnTerm(req.getProductReturnTerm());
                receiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_INIT);
                receiveMoney.setType(Constants.RETURN_NOTICE_TYPE_LAST);
                receiveMoney.setUpdateTime(new Date());
                bsSysReceiveMoneyMapper.insertSelective(receiveMoney);
            }

            //判断是否是最后一期，不是最后一期则进行告警抛错
            BsProductExample productExample = new BsProductExample();
            productExample.createCriteria().andCodeEqualTo(req.getProductCode());
            List<BsProduct> bsProducts = bsProductMapper.selectByExample(productExample);
            Integer term = 0;
            if(bsProducts!=null && bsProducts.size()>0){
                term = bsProducts.get(0).getTerm();
                if(term < 0){
                    term = 1;
                }
                //非最后一期
                Date sendTime = batchBuys.get(0).getSendTime();//系统购买时间
                Date sendTimeFmt = DateUtil.parseDate(DateUtil.formatYYYYMMDD(sendTime));
                Date separateDate = DateUtil.parseDate("2016-02-02");//分期回款开始时间
                if(!String.valueOf(term).equals(req.getProductReturnTerm()) && DateUtil.getDiffeDay(sendTimeFmt, separateDate) > -1){
                    log.info("========={" + channelName + "系统回款通知处理}异常回款通知，原因：期数不是最后一期=========");
                    //回款通知表处理状态改为失败
                    returnReceiveFail(req,  channelName + "理财回款期数非最后一期", "{" + channelName + "理财回款通知}产品批次号["+req.getProductOrderNo()+"]异常回款通知，原因：期数不是最后一期");
                    throw new PTMessageException(PTMessageEnum.RETURN_NOTICE_TERM_NOT_LAST);
                }
            }

            //接口设定 每次只会有一个批次回款通知,故此处直接取列表第一个数据
            Integer batchId = batchBuys.get(0).getId();

            //记录通知到系统购买产品表
            BsBatchBuy batchBuy = new BsBatchBuy();
            batchBuy.setId(batchId);
            batchBuy.setDafyPay19MpOrderNo(oriOutOrderId);
            batchBuy.setReceiveAmount(MoneyUtil.add(req.getProductAmount(), req.getProductInterest()).doubleValue());
            batchBuy.setReturnTime(req.getPayFinshTime());
            batchBuy.setUpdateTime(new Date());
            bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuy);
            //根据19付订单号查询款项是否已到账
            /*B2GReqMsg_AcctTrans_QueryRecvAcctTrans transReq = new B2GReqMsg_AcctTrans_QueryRecvAcctTrans();
            transReq.setOriOutMxId(oriOutMxId);
            transReq.setOriOutOrderDate(oriOutOrderDate);
            transReq.setOriOutOrderId(oriOutOrderId);
            transReq.setTs(new Date());
            B2GResMsg_AcctTrans_QueryRecvAcctTrans transResp = pay19TransportService.queryRecvAcctTrans(transReq);*/
            //去掉以上是否到账的校验，直接置为成功
            B2GResMsg_AcctTrans_QueryRecvAcctTrans transResp = new B2GResMsg_AcctTrans_QueryRecvAcctTrans();
            transResp.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
            transResp.setTradeResult(TradeResult.SUCCESS.getCode());
            transResp.setOrderAmount(String.valueOf(req.getAmount()));

            //查询请求成功
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(transResp.getResCode())){
                //查询到订单交易成功
                if(TradeResult.SUCCESS.getCode().equals(transResp.getTradeResult())){
                    log.info("========={" + channelName + "系统回款通知处理}查询到订单交易成功=========");
                    BsBatchBuy batchBuyTemp = new BsBatchBuy();
                    batchBuyTemp.setId(batchId);
                    batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_RETURN_SUCCESS);
                    batchBuyTemp.setUpdateTime(new Date());
                    bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuyTemp);

                    BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
                    List<String> statuss = new ArrayList<String>();
                    statuss.add(Constants.RETURN_STATUS_NOT);
                    statuss.add(Constants.RETURN_STATUS_FAIL);
                    detailExample.createCriteria().andBatchIdEqualTo(batchId).andReturnStatusIn(statuss);
                    List<BsBatchBuyDetail> details = bsBatchBuyDetailMapper.selectByExample(detailExample);
                    //根据批次查询对应的待回款用户产品明细
                    if(details!=null && details.size()>0){
                        //系统回款入账
                        //计算用户月利息和系统月利息，入账
                        final double productTotalAmount = batchBuys.get(0).getAmount();//总本金
                        final double sysMonthProductInterest =  new BigDecimal(transResp.getOrderAmount()).subtract(new BigDecimal(productTotalAmount)).doubleValue();//req.getProductInterest();//该批次总利息
                        final BigDecimal userTotalInterest = calcUserTotalInterest(details);//该批次用户产品总用户收益
                        //先查询系统回款记录表，是否有结息回款，若有：则需进行前面结息回款的用户利息累加统计，得到最后精确的用户利息；若无：则直接计算本次用户利息
                        BsSysReceiveMoneyExample moneyExample = new BsSysReceiveMoneyExample();
                        moneyExample.createCriteria().andTypeEqualTo(Constants.RETURN_NOTICE_TYPE_INTEREST).andProductOrderNoEqualTo(req.getProductOrderNo());
                        final List<BsSysReceiveMoney> receiveMoneys = bsSysReceiveMoneyMapper.selectByExample(moneyExample);
                        final Integer tempTerm = term;
                        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                            @Override
                            protected void doInTransactionWithoutResult(TransactionStatus status) {
                                BigDecimal userInterest = new BigDecimal(0);
                                //若有：则需进行前面结息回款的用户利息累加统计,最终得到本次用户利息:最后次利息=用户总利息-(期数-1)*(用户总利息/期数)
                                if(receiveMoneys!=null && receiveMoneys.size()>0){
                                    BigDecimal avgUserInterest = MoneyUtil.divide(userTotalInterest.doubleValue(), tempTerm);
                                    userInterest = MoneyUtil.subtract(userTotalInterest.doubleValue(), MoneyUtil.multiply(avgUserInterest.doubleValue(), Double.valueOf(tempTerm-1)).doubleValue());
                                }
                                //若无：则本次用户利息=该批次用户产品总用户收益
                                else{
                                    userInterest = userTotalInterest;
                                }
                                //系统子账户表 JSH 增加金额，并冻结
                                BsSysSubAccount jshSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_JSH);
                                BsSysSubAccount readyUpdateJsh = new BsSysSubAccount();
                                readyUpdateJsh.setId(jshSubAccountLock.getId());
                                readyUpdateJsh.setBalance(MoneyUtil.add(jshSubAccountLock.getBalance(), sysMonthProductInterest).doubleValue());
                                readyUpdateJsh.setAvailableBalance(MoneyUtil.subtract(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysMonthProductInterest).doubleValue(), userInterest.doubleValue()).doubleValue());
                                readyUpdateJsh.setCanWithdraw(MoneyUtil.subtract(MoneyUtil.add(jshSubAccountLock.getCanWithdraw(), sysMonthProductInterest).doubleValue(), userInterest.doubleValue()).doubleValue());
                                readyUpdateJsh.setFreezeBalance(MoneyUtil.add(jshSubAccountLock.getFreezeBalance(), userInterest.doubleValue()).doubleValue());

                                //新增系统记账流水表，JSH余额增加
                                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                                sysAccountJnl.setTransTime(new Date());
                                sysAccountJnl.setTransCode(Constants.SYS_RETURN_INTEREST);
                                sysAccountJnl.setTransName(channelName + "产品最后次结息回款");
                                sysAccountJnl.setTransAmount(sysMonthProductInterest);
                                sysAccountJnl.setSysTime(new Date());
                                sysAccountJnl.setChannelTime(req.getPayFinshTime());
                                sysAccountJnl.setChannelJnlNo(req.getPayOrderNo());
                                sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                                sysAccountJnl.setSubAccountCode2(jshSubAccountLock.getCode());
                                sysAccountJnl.setBeforeBalance2(jshSubAccountLock.getBalance());
                                sysAccountJnl.setAfterBalance2(readyUpdateJsh.getBalance());
                                sysAccountJnl.setBeforeAvialableBalance2(jshSubAccountLock.getAvailableBalance());
                                sysAccountJnl.setAfterAvialableBalance2(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysMonthProductInterest).doubleValue());
                                sysAccountJnl.setBeforeFreezeBalance2(jshSubAccountLock.getFreezeBalance());
                                sysAccountJnl.setAfterFreezeBalance2(jshSubAccountLock.getFreezeBalance());
                                sysAccountJnl.setFee(0.0);
                                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                                sysAccountJnlMapper.insertSelective(sysAccountJnl);

                                //新增系统记账流水表，JSH余额冻结
                                BsSysAccountJnl sysAccountJnlFreeze = new BsSysAccountJnl();
                                sysAccountJnlFreeze.setTransTime(new Date());
                                sysAccountJnlFreeze.setTransCode(Constants.SYS_FREEZE);
                                sysAccountJnl.setTransName(channelName + "产品最后次结息回款-用户月利息冻结");
                                sysAccountJnl.setTransAmount(userInterest.doubleValue());
                                sysAccountJnl.setSysTime(new Date());
                                sysAccountJnl.setChannelTime(req.getPayFinshTime());
                                sysAccountJnl.setChannelJnlNo(req.getPayOrderNo());
                                sysAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                                sysAccountJnl.setSubAccountCode2(jshSubAccountLock.getCode());
                                sysAccountJnl.setBeforeBalance2(readyUpdateJsh.getBalance());
                                sysAccountJnl.setAfterBalance2(readyUpdateJsh.getBalance());
                                sysAccountJnl.setBeforeAvialableBalance2(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysMonthProductInterest).doubleValue());
                                sysAccountJnl.setAfterAvialableBalance2(readyUpdateJsh.getAvailableBalance());
                                sysAccountJnl.setBeforeFreezeBalance2(jshSubAccountLock.getFreezeBalance());
                                sysAccountJnl.setAfterFreezeBalance2(readyUpdateJsh.getFreezeBalance());
                                sysAccountJnl.setFee(0.0);
                                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                                sysAccountJnlMapper.insertSelective(sysAccountJnl);

                                //用户回款动帐
                                //系统子账户表 RETURN 增加用户总本金+用户总利息
                                BsSysSubAccount returnSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(actCode);
                                BsSysSubAccount readyUpdate = new BsSysSubAccount();
                                readyUpdate.setId(returnSubAccountLock.getId());
                                readyUpdate.setBalance(MoneyUtil.add(returnSubAccountLock.getBalance(), userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue()).doubleValue());
                                readyUpdate.setAvailableBalance(MoneyUtil.add(returnSubAccountLock.getAvailableBalance(), userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue()).doubleValue());
                                readyUpdate.setCanWithdraw(MoneyUtil.add(returnSubAccountLock.getCanWithdraw(), userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue()).doubleValue());
                                sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                                //系统子账户表 JSH 减少用户总利息
                                Double beforeBalance = readyUpdateJsh.getBalance();
                                Double beforeAvailableBalance = readyUpdateJsh.getAvailableBalance();
                                Double beforeFreezeBalance = readyUpdateJsh.getFreezeBalance();
                                readyUpdateJsh.setBalance(MoneyUtil.subtract(beforeBalance, userTotalInterest.doubleValue()).doubleValue());
                                readyUpdateJsh.setFreezeBalance(MoneyUtil.subtract(beforeFreezeBalance, userTotalInterest.doubleValue()).doubleValue());
                                sysAccountMapper.updateByPrimaryKeySelective(readyUpdateJsh);

                                //新增系统记账流水表
                                BsSysAccountJnl returnAccountJnl = new BsSysAccountJnl();
                                returnAccountJnl.setTransTime(new Date());
                                returnAccountJnl.setTransCode(Constants.SYS_RETURN);
                                returnAccountJnl.setTransName(channelName + "回款用户获得总额");
                                returnAccountJnl.setTransAmount(userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue());
                                returnAccountJnl.setSysTime(new Date());
                                returnAccountJnl.setChannelTime(req.getPayFinshTime());
                                returnAccountJnl.setChannelJnlNo(req.getPayOrderNo());
                                returnAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                                returnAccountJnl.setSubAccountCode2(returnSubAccountLock.getCode());
                                returnAccountJnl.setBeforeBalance2(returnSubAccountLock.getBalance());
                                returnAccountJnl.setAfterBalance2(readyUpdate.getBalance());
                                returnAccountJnl.setBeforeAvialableBalance2(returnSubAccountLock.getAvailableBalance());
                                returnAccountJnl.setAfterAvialableBalance2(readyUpdate.getAvailableBalance());
                                returnAccountJnl.setBeforeFreezeBalance2(returnSubAccountLock.getFreezeBalance());
                                returnAccountJnl.setAfterFreezeBalance2(returnSubAccountLock.getFreezeBalance());
                                returnAccountJnl.setFee(0.0);
                                returnAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                                sysAccountJnlMapper.insertSelective(returnAccountJnl);

                                BsSysAccountJnl sysAccountJnl4Subtract = new BsSysAccountJnl();
                                sysAccountJnl4Subtract.setTransTime(new Date());
                                sysAccountJnl4Subtract.setTransCode(Constants.SYS_RETURN_INTEREST);
                                sysAccountJnl4Subtract.setTransName(channelName + "回款系统结算户用户总利息扣除");
                                sysAccountJnl4Subtract.setTransAmount(userTotalInterest.doubleValue());
                                sysAccountJnl4Subtract.setSysTime(new Date());
                                sysAccountJnl4Subtract.setChannelTime(req.getPayFinshTime());
                                sysAccountJnl4Subtract.setChannelJnlNo(req.getPayOrderNo());
                                sysAccountJnl4Subtract.setCdFlag2(Constants.CD_FLAG_D);
                                sysAccountJnl4Subtract.setSubAccountCode2(readyUpdateJsh.getCode());
                                sysAccountJnl4Subtract.setBeforeBalance2(beforeBalance);
                                sysAccountJnl4Subtract.setAfterBalance2(readyUpdateJsh.getBalance());
                                sysAccountJnl4Subtract.setBeforeAvialableBalance2(beforeAvailableBalance);
                                sysAccountJnl4Subtract.setAfterAvialableBalance2(readyUpdateJsh.getAvailableBalance());
                                sysAccountJnl4Subtract.setBeforeFreezeBalance2(beforeFreezeBalance);
                                sysAccountJnl4Subtract.setAfterFreezeBalance2(readyUpdateJsh.getFreezeBalance());
                                sysAccountJnl4Subtract.setFee(0.0);
                                sysAccountJnl4Subtract.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                                sysAccountJnlMapper.insertSelective(sysAccountJnl4Subtract);

                            }
                        });
                        //回款通知表处理状态改为成功
                        BsSysReceiveMoneyExample updateReceiveMoneyExample = new BsSysReceiveMoneyExample();
                        updateReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                                .andProductReturnTermEqualTo(req.getProductReturnTerm());
                        List<BsSysReceiveMoney> updateReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(updateReceiveMoneyExample);
                        BsSysReceiveMoney updateReceiveMoney = new BsSysReceiveMoney();
                        updateReceiveMoney.setId(updateReceiveMoneys.get(0).getId());
                        updateReceiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
                        updateReceiveMoney.setUpdateTime(new Date());
                        bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(updateReceiveMoney);

                        log.info("========={"+ channelName + "系统回款通知处理}另起一线程进行客户回款=========");
                        //另起一线程进行客户回款
                        UserReturnMoneyProcess process = new UserReturnMoneyProcess();
                        process.setReceiveBatchs(batchBuys);
                        process.setUserReturnMoneyService(userReturnMoneyService);
                        new Thread(process).start();
                    }
                }
                //查询到订单交易处理中或失败
                else{
                    log.info("========={"+ channelName + "系统回款通知处理}查询到订单交易处理中或失败=========");
                    returnFail(req.getProductOrderNo(), req.getProductAmount(), req.getProductReturnTerm(), channelName);
                    throw new PTMessageException(PTMessageEnum.SYS_BUY_RETURN_ACCTTRANS_FAIL);
                }
            }
            //查询请求失败
            else{
                log.info("========={"+ channelName + "系统回款通知处理}查询请求失败=========");
                returnFail(req.getProductOrderNo(), req.getProductAmount(), req.getProductReturnTerm(), channelName);
                throw new PTMessageException(PTMessageEnum.SYS_BUY_RETURN_ACCTTRANS_QUERY_FAIL);
            }
            log.info("========={"+ channelName + "系统回款通知处理}结束=========");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
        }
    }

    /**
     * 系统理财产品每月结息通知处理
     *
     * @param req
     */
    @Override
    public void notifySysReturnInterest(final G2BReqMsg_DafyPayment_SysReturnMoneyNotice req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
            String channel = req.getChannel();
            final String channelName = ThirdSysChannelEnum.getEnumByCode(channel).getName();
            log.info("========={" + channelName +"系统产品每月结息回款通知处理}开始=========");
            String oriOutOrderId = req.getPayOrderNo();
            String oriOutMxId = req.getMerchantId();
            Date oriOutOrderDate = req.getPayReqTime();
            //根据理财购买表，判断本次通知是否有效
            BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
            batchBuyExample.createCriteria().andSendBatchIdEqualTo(req.getProductOrderNo());
            List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
            if(batchBuys==null || batchBuys.size()<=0){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            if(Constants.BATCHBUY_STATUS_RETURN_SUCCESS.equals(batchBuys.get(0).getStatus())){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
            }
            if(!channel.equals(batchBuys.get(0).getPropertySymbol())){
                throw new PTMessageException(PTMessageEnum.RETURN_CHANNEL_ERROR);
            }
            //校验产品code码是否一致
            if(!req.getProductCode().equals(batchBuys.get(0).getProductCode())){
            	throw new PTMessageException(PTMessageEnum.RETURN_PRODUCT_CODE_ERROR);
            }
            //判断此次还款期数是否大于产品的总期数
            BsProductExample productExample = new BsProductExample();
            productExample.createCriteria().andCodeEqualTo(req.getProductCode());
            List<BsProduct> bsProducts = bsProductMapper.selectByExample(productExample);
            if(CollectionUtils.isEmpty(bsProducts) || bsProducts.get(0).getTerm() < Integer.valueOf(req.getProductReturnTerm())){
            	throw new PTMessageException(PTMessageEnum.RETURN_PRODUCT_TREM_ERROR);
            }

            //根据通知记录表，判断本次通知是否已处理
            BsSysReceiveMoneyExample receiveMoneyExample = new BsSysReceiveMoneyExample();
            receiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                    .andProductReturnTermEqualTo(req.getProductReturnTerm());
            List<BsSysReceiveMoney> sysReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(receiveMoneyExample);
            if (sysReceiveMoneys != null && sysReceiveMoneys.size() > 0) {
                // 已处理成功
                if (Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS.equals(sysReceiveMoneys.get(0).getStatus())) {
                    throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
                }
                // 未处理成功
                else{
                    //修改为最新数据
                    BsSysReceiveMoney receiveMoney = new BsSysReceiveMoney();
                    receiveMoney.setId(sysReceiveMoneys.get(0).getId());
                    receiveMoney.setType(Constants.RETURN_NOTICE_TYPE_INTEREST);
                    receiveMoney.setAmount(req.getAmount());
                    receiveMoney.setPayFinshTime(req.getPayFinshTime());
                    receiveMoney.setPayOrderNo(req.getPayOrderNo());
                    receiveMoney.setPayReqTime(req.getPayReqTime());
                    receiveMoney.setProductAmount(req.getProductAmount());
                    receiveMoney.setProductCode(req.getProductCode());
                    receiveMoney.setProductInterest(req.getProductInterest());
                    receiveMoney.setUpdateTime(new Date());
                    bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(receiveMoney);
                }
            }
            // 无通知记录，插入一条
            else {
                BsSysReceiveMoney receiveMoney = new BsSysReceiveMoney();
                receiveMoney.setAmount(req.getAmount());
                receiveMoney.setCreateTime(new Date());
                receiveMoney.setPayFinshTime(req.getPayFinshTime());
                receiveMoney.setPayOrderNo(req.getPayOrderNo());
                receiveMoney.setPayReqTime(req.getPayReqTime());
                receiveMoney.setProductAmount(req.getProductAmount());
                receiveMoney.setProductCode(req.getProductCode());
                receiveMoney.setProductInterest(req.getProductInterest());
                receiveMoney.setProductOrderNo(req.getProductOrderNo());
                receiveMoney.setProductReturnTerm(req.getProductReturnTerm());
                receiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_INIT);
                receiveMoney.setType(Constants.RETURN_NOTICE_TYPE_INTEREST);
                receiveMoney.setUpdateTime(new Date());
                bsSysReceiveMoneyMapper.insertSelective(receiveMoney);
            }

            //判断是否是非最后一期，如果是最后一期则进行告警抛错
            Integer term = 0;
            if(bsProducts!=null && bsProducts.size()>0){
                term = bsProducts.get(0).getTerm();
                if(term < 0){
                    term = 1;
                }
                //最后一期判断
                if(String.valueOf(term).equals(req.getProductReturnTerm())){
                    log.info("========={" + channelName +"系统产品每月结息回款通知处理}异常结息回款通知，原因：期数不应是最后一期=========");
                    //回款通知表处理状态改为失败
                    returnReceiveFail(req, channelName + "结息回款期数不应是最后一期", "{" + channelName +"理财产品每月结息回款通知}产品批次号["+req.getProductOrderNo()+"]异常结息回款通知，原因：期数不应是最后一期");
                    throw new PTMessageException(PTMessageEnum.RETURN_NOTICE_TERM_IS_LAST);
                }
            }else{
                throw new PTMessageException(PTMessageEnum.DAFY_RECEIVE_MONEY_NOTICE_DATA_ERROR,"无对应理财产品码");
            }

            //接口设定 每次只会有一个批次回款通知,故此处直接取列表第一个数据
            Integer batchId = batchBuys.get(0).getId();

            //根据19付订单号查询款项是否已到账
            /*B2GReqMsg_AcctTrans_QueryRecvAcctTrans transReq = new B2GReqMsg_AcctTrans_QueryRecvAcctTrans();
            transReq.setOriOutMxId(oriOutMxId);
            transReq.setOriOutOrderDate(oriOutOrderDate);
            transReq.setOriOutOrderId(oriOutOrderId);
            transReq.setTs(new Date());
            B2GResMsg_AcctTrans_QueryRecvAcctTrans transResp = pay19TransportService.queryRecvAcctTrans(transReq);*/
            //去掉以上是否到账的校验，直接置为成功
            B2GResMsg_AcctTrans_QueryRecvAcctTrans transResp = new B2GResMsg_AcctTrans_QueryRecvAcctTrans();
            transResp.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
            transResp.setTradeResult(TradeResult.SUCCESS.getCode());

            //查询请求成功
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(transResp.getResCode())){
                //查询到订单交易成功
                if(TradeResult.SUCCESS.getCode().equals(transResp.getTradeResult())){
                    log.info("========={" + channelName +"系统产品每月结息回款通知处理}查询到订单交易成功=========");
                    //入账，所有利息(总月利息)均存入系统子账户的JSH，并计算用户月利息，冻结此部分金额
                    BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
                    List<String> statuss = new ArrayList<String>();
                    statuss.add(Constants.RETURN_STATUS_NOT);
                    statuss.add(Constants.RETURN_STATUS_FAIL);
                    detailExample.createCriteria().andBatchIdEqualTo(batchId).andReturnStatusIn(statuss);
                    final List<BsBatchBuyDetail> details = bsBatchBuyDetailMapper.selectByExample(detailExample);
                    //根据批次查询对应的待回款用户产品明细
                    if(details!=null && details.size()>0){
                        final Integer tempTerm = term;
                        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                            @Override
                            protected void doInTransactionWithoutResult(TransactionStatus status) {
                                double sysProductInterest = req.getProductInterest();//本次回款总利息
                                BigDecimal userTotalInterest = calcUserTotalInterest(details);//该批次用户产品总用户收益
                                //本次用户利息=用户总利息/期数
                                BigDecimal userInterest = MoneyUtil.divide(userTotalInterest.doubleValue(), tempTerm);

                                //系统子账户表 JSH 增加金额，并冻结
                                BsSysSubAccount jshSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_JSH);
                                BsSysSubAccount readyUpdateJsh = new BsSysSubAccount();
                                readyUpdateJsh.setId(jshSubAccountLock.getId());
                                readyUpdateJsh.setBalance(MoneyUtil.add(jshSubAccountLock.getBalance(), sysProductInterest).doubleValue());
                                readyUpdateJsh.setAvailableBalance(MoneyUtil.subtract(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysProductInterest).doubleValue(), userInterest.doubleValue()).doubleValue());
                                readyUpdateJsh.setCanWithdraw(MoneyUtil.subtract(MoneyUtil.add(jshSubAccountLock.getCanWithdraw(), sysProductInterest).doubleValue(), userInterest.doubleValue()).doubleValue());
                                readyUpdateJsh.setFreezeBalance(MoneyUtil.add(jshSubAccountLock.getFreezeBalance(), userInterest.doubleValue()).doubleValue());
                                sysAccountMapper.updateByPrimaryKeySelective(readyUpdateJsh);

                                //新增系统记账流水表，JSH余额增加
                                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                                sysAccountJnl.setTransTime(new Date());
                                sysAccountJnl.setTransCode(Constants.SYS_RETURN_INTEREST);
                                sysAccountJnl.setTransName(channelName +"产品每月结息回款");
                                sysAccountJnl.setTransAmount(sysProductInterest);
                                sysAccountJnl.setSysTime(new Date());
                                sysAccountJnl.setChannelTime(req.getPayFinshTime());
                                sysAccountJnl.setChannelJnlNo(req.getPayOrderNo());
                                sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
                                sysAccountJnl.setSubAccountCode2(jshSubAccountLock.getCode());
                                sysAccountJnl.setBeforeBalance2(jshSubAccountLock.getBalance());
                                sysAccountJnl.setAfterBalance2(readyUpdateJsh.getBalance());
                                sysAccountJnl.setBeforeAvialableBalance2(jshSubAccountLock.getAvailableBalance());
                                sysAccountJnl.setAfterAvialableBalance2(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysProductInterest).doubleValue());
                                sysAccountJnl.setBeforeFreezeBalance2(jshSubAccountLock.getFreezeBalance());
                                sysAccountJnl.setAfterFreezeBalance2(jshSubAccountLock.getFreezeBalance());
                                sysAccountJnl.setFee(0.0);
                                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                                sysAccountJnlMapper.insertSelective(sysAccountJnl);

                                //新增系统记账流水表，JSH余额冻结
                                BsSysAccountJnl sysAccountJnlFreeze = new BsSysAccountJnl();
                                sysAccountJnlFreeze.setTransTime(new Date());
                                sysAccountJnlFreeze.setTransCode(Constants.SYS_FREEZE);
                                sysAccountJnl.setTransName(channelName +"产品每月结息回款-用户月利息冻结");
                                sysAccountJnl.setTransAmount(userInterest.doubleValue());
                                sysAccountJnl.setSysTime(new Date());
                                sysAccountJnl.setChannelTime(req.getPayFinshTime());
                                sysAccountJnl.setChannelJnlNo(req.getPayOrderNo());
                                sysAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                                sysAccountJnl.setSubAccountCode2(jshSubAccountLock.getCode());
                                sysAccountJnl.setBeforeBalance2(readyUpdateJsh.getBalance());
                                sysAccountJnl.setAfterBalance2(readyUpdateJsh.getBalance());
                                sysAccountJnl.setBeforeAvialableBalance2(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysProductInterest).doubleValue());
                                sysAccountJnl.setAfterAvialableBalance2(readyUpdateJsh.getAvailableBalance());
                                sysAccountJnl.setBeforeFreezeBalance2(jshSubAccountLock.getFreezeBalance());
                                sysAccountJnl.setAfterFreezeBalance2(readyUpdateJsh.getFreezeBalance());
                                sysAccountJnl.setFee(0.0);
                                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                                sysAccountJnlMapper.insertSelective(sysAccountJnl);
                            }
                        });
                        //回款通知表处理状态改为成功
                        BsSysReceiveMoneyExample updateReceiveMoneyExample = new BsSysReceiveMoneyExample();
                        updateReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                                .andProductReturnTermEqualTo(req.getProductReturnTerm());
                        List<BsSysReceiveMoney> updateReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(updateReceiveMoneyExample);
                        BsSysReceiveMoney updateReceiveMoney = new BsSysReceiveMoney();
                        updateReceiveMoney.setId(updateReceiveMoneys.get(0).getId());
                        updateReceiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
                        updateReceiveMoney.setUpdateTime(new Date());
                        bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(updateReceiveMoney);
                    }
                }
                //查询到订单交易处理中或失败
                else{
                    log.info("========={" + channelName +"系统产品每月结息回款通知处理}查询到订单交易处理中或失败=========");
                    //回款通知表处理状态改为失败
                    returnReceiveFail(req, channelName +"理财结息回款通知", "{" + channelName +"理财结息回款通知}产品批次号["+req.getProductOrderNo()+"]，第["+req.getProductReturnTerm()+"]期结息回款确认转账划拨失败");
                    throw new PTMessageException(PTMessageEnum.SYS_BUY_RETURN_ACCTTRANS_FAIL);
                }
            }
            //查询请求失败
            else{
                log.info("========={" + channelName +"系统产品每月结息回款通知处理}查询请求失败=========");
                //回款通知表处理状态改为失败
                returnReceiveFail(req, channelName +"理财结息回款通知", "{" + channelName +"理财结息回款通知}产品批次号["+req.getProductOrderNo()+"]，第["+req.getProductReturnTerm()+"]期结息回款确认转账划拨查询请求失败");
                throw new PTMessageException(PTMessageEnum.SYS_BUY_RETURN_ACCTTRANS_QUERY_FAIL);
            }
            log.info("========={" + channelName +"系统产品每月结息回款通知处理}结束=========");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
        }
    }

    private void returnFail(String productOrderNo, Double productAmount, String productReturnTerm, String channelName){
        BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
        batchBuyExample.createCriteria().andSendBatchIdEqualTo(productOrderNo);
        BsBatchBuy batchBuyTemp = new BsBatchBuy();
        batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_RETURN_FAIL);
        batchBuyTemp.setUpdateTime(new Date());
        bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyExample);
        //回款通知表处理状态改为失败
        BsSysReceiveMoneyExample updateReceiveMoneyExample = new BsSysReceiveMoneyExample();
        updateReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(productOrderNo)
                .andProductReturnTermEqualTo(productReturnTerm);
        List<BsSysReceiveMoney> updateReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(updateReceiveMoneyExample);
        BsSysReceiveMoney updateReceiveMoney = new BsSysReceiveMoney();
        updateReceiveMoney.setId(updateReceiveMoneys.get(0).getId());
        updateReceiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_FAIL);
        updateReceiveMoney.setUpdateTime(new Date());
        bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(updateReceiveMoney);
        //告警
        specialJnlService.warn4Fail(productAmount, "{"+channelName+"理财回款通知}产品批次号["+productOrderNo+"]确认转账划拨失败",
                productOrderNo, channelName+"理财回款通知",false);
    }
    private BigDecimal calcUserTotalInterest(List<BsBatchBuyDetail> details){
        BigDecimal userTotalInterest = new BigDecimal(0);
        for (BsBatchBuyDetail bsBatchBuyDetail : details) {
            BsSubAccount subAccount = subAccountService.findSubAccountById(bsBatchBuyDetail.getSubAccountId());
            BsProduct product = bsProductMapper.selectByPrimaryKey(subAccount.getProductId());
            //产品本金
            BigDecimal principal = new BigDecimal(subAccount.getBalance());
            //产品投资天数
            Integer days = product.getTerm4Day();
            //产品收益(四舍五入)
            BigDecimal interest =MoneyUtil.divide(
            		MoneyUtil.multiply(days,MoneyUtil.multiply(principal.doubleValue(), subAccount.getProductRate()).doubleValue()).doubleValue()
            		, 36500d).setScale(2, BigDecimal.ROUND_HALF_UP);
            	/*	new BigDecimal(principal.multiply(
                    new BigDecimal(subAccount.getProductRate()*days/36500d)).doubleValue()).setScale(2, BigDecimal.ROUND_HALF_UP);*/
            userTotalInterest = userTotalInterest.add(interest);
        }
        return userTotalInterest;
    }
    
    /**
     * 根据计息天数计算该批次用户总利息
     * @param details
     * @param days
     * @return
     */
    private BigDecimal calcUserTotalInterestByDays(List<BsBatchBuyDetail> details,Integer days){
        BigDecimal userTotalInterest = new BigDecimal(0);
        for (BsBatchBuyDetail bsBatchBuyDetail : details) {
            BsSubAccount subAccount = subAccountService.findSubAccountById(bsBatchBuyDetail.getSubAccountId());
            BsProduct product = bsProductMapper.selectByPrimaryKey(subAccount.getProductId());
            //产品本金
            BigDecimal principal = new BigDecimal(subAccount.getBalance());
            //产品收益(四舍五入)
            BigDecimal interest =MoneyUtil.divide(
            		MoneyUtil.multiply(days,MoneyUtil.multiply(principal.doubleValue(), subAccount.getProductRate()).doubleValue()).doubleValue()
            		, 36500d).setScale(2, BigDecimal.ROUND_HALF_UP);
            userTotalInterest = userTotalInterest.add(interest);
        }
        return userTotalInterest;
    }

    private void returnReceiveFail(G2BReqMsg_DafyPayment_SysReturnMoneyNotice req, String type, String failInfo){
        BsSysReceiveMoneyExample updateReceiveMoneyExample = new BsSysReceiveMoneyExample();
        updateReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                .andProductReturnTermEqualTo(req.getProductReturnTerm());
        List<BsSysReceiveMoney> updateReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(updateReceiveMoneyExample);
        BsSysReceiveMoney updateReceiveMoney = new BsSysReceiveMoney();
        updateReceiveMoney.setId(updateReceiveMoneys.get(0).getId());
        updateReceiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_FAIL);
        updateReceiveMoney.setUpdateTime(new Date());
        bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(updateReceiveMoney);
        //告警
        specialJnlService.warn4Fail(req.getAmount(), failInfo, req.getProductOrderNo(),type,false);
    }

	@Override
	public void notifySysReturnPrincipalNew(
			final G2BReqMsg_DafyPayment_SysReturnMoneyNotice req) {
		/**
		 * 七贷上线，存量理财本金回款
		 * 1、根据理财购买表，判断本次通知是否有效；根据通知记录表，判断本次通知是否已处理
		 * 2、判断最后一期回款时前面利息回款是都已经全部处理结束
		 * 3、校验，BsBatchBuy表的expectTime是否是3.10（可配）之后，是则调用3.10-9.19的提前回款方案4，否则是二方案5（3.10及之前）
		 * 4、之前利息全部回完，该次本息还款回完所有本金，及该期天数的利息
		 * 5、之前利息全部回完，该次本息还款回完所有利息、本金
		 */
		
		try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
            String channel = req.getChannel();
            final String channelName = ThirdSysChannelEnum.getEnumByCode(channel).getName();

            log.info("========={" + channelName + "系统回款通知处理}开始=========");

            //1、根据理财购买表，判断本次通知是否有效
            BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
            batchBuyExample.createCriteria().andSendBatchIdEqualTo(req.getProductOrderNo());
            List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
            if(batchBuys==null || batchBuys.size()<=0){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            if(Constants.BATCHBUY_STATUS_RETURN_SUCCESS.equals(batchBuys.get(0).getStatus())){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
            }
            if(!channel.equals(batchBuys.get(0).getPropertySymbol())){
                throw new PTMessageException(PTMessageEnum.RETURN_CHANNEL_ERROR);
            }
            
            //校验产品code码是否一致
            if(!req.getProductCode().equals(batchBuys.get(0).getProductCode())){
            	throw new PTMessageException(PTMessageEnum.RETURN_PRODUCT_CODE_ERROR);
            }
            
            //判断此次还款期数是否大于产品的总期数
            BsProductExample productExample = new BsProductExample();
            productExample.createCriteria().andCodeEqualTo(req.getProductCode());
            List<BsProduct> bsProducts = bsProductMapper.selectByExample(productExample);
            if(CollectionUtils.isEmpty(bsProducts) || bsProducts.get(0).getTerm() < Integer.valueOf(req.getProductReturnTerm())){
            	throw new PTMessageException(PTMessageEnum.RETURN_PRODUCT_TREM_ERROR);
            }
            
            //2、根据通知记录表，判断本次通知是否已处理
            BsSysReceiveMoneyExample receiveMoneyExample = new BsSysReceiveMoneyExample();
            receiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                    .andProductReturnTermEqualTo(req.getProductReturnTerm());
            List<BsSysReceiveMoney> sysReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(receiveMoneyExample);
            if (sysReceiveMoneys != null && sysReceiveMoneys.size() > 0) {
                // 已处理成功
                if (Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS.equals(sysReceiveMoneys.get(0).getStatus())) {
                    throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
                }
                // 未处理成功
                else{
                    //修改为最新数据
                    BsSysReceiveMoney receiveMoney = new BsSysReceiveMoney();
                    receiveMoney.setId(sysReceiveMoneys.get(0).getId());
                    receiveMoney.setType(Constants.RETURN_NOTICE_TYPE_LAST);
                    receiveMoney.setAmount(req.getAmount());
                    receiveMoney.setPayFinshTime(req.getPayFinshTime());
                    receiveMoney.setPayOrderNo(req.getPayOrderNo());
                    receiveMoney.setPayReqTime(req.getPayReqTime());
                    receiveMoney.setProductAmount(req.getProductAmount());
                    receiveMoney.setProductCode(req.getProductCode());
                    receiveMoney.setProductInterest(req.getProductInterest());
                    receiveMoney.setUpdateTime(new Date());
                    bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(receiveMoney);
                }
            }
            // 无通知记录，插入一条
            else {
                BsSysReceiveMoney receiveMoney = new BsSysReceiveMoney();
                receiveMoney.setAmount(req.getAmount());
                receiveMoney.setCreateTime(new Date());
                receiveMoney.setPayFinshTime(req.getPayFinshTime());
                receiveMoney.setPayOrderNo(req.getPayOrderNo());
                receiveMoney.setPayReqTime(req.getPayReqTime());
                receiveMoney.setProductAmount(req.getProductAmount());
                receiveMoney.setProductCode(req.getProductCode());
                receiveMoney.setProductInterest(req.getProductInterest());
                receiveMoney.setProductOrderNo(req.getProductOrderNo());
                receiveMoney.setProductReturnTerm(req.getProductReturnTerm());
                receiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_INIT);
                receiveMoney.setType(Constants.RETURN_NOTICE_TYPE_LAST);
                receiveMoney.setUpdateTime(new Date());
                bsSysReceiveMoneyMapper.insertSelective(receiveMoney);
            }
            //判断最后一期回款时前面利息回款是都已经全部处理结束
            if (Integer.valueOf(req.getProductReturnTerm()) >1) {
                BsSysReceiveMoneyExample receiveExample = new BsSysReceiveMoneyExample();
                receiveExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo()).andTypeEqualTo(Constants.RETURN_NOTICE_TYPE_INTEREST).andStatusEqualTo(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
                List<BsSysReceiveMoney> sysReceiveMoneyList = bsSysReceiveMoneyMapper.selectByExample(receiveExample);
                if (sysReceiveMoneyList.size() + 1 != Integer.parseInt(req.getProductReturnTerm())) {
                    log.info("========={" + channelName + "系统回款通知处理}异常回款通知，原因：最后期回款必须完成利息回款=========");
                    //告警
                    specialJnlService.warn4Fail(req.getAmount(),  "{" + channelName + "理财回款通知}产品批次号["+req.getProductOrderNo()+"]异常回款通知，原因：最后期回款必须完成利息回款", req.getProductOrderNo(),channelName + "未完成利息回款",false);

                    throw new PTMessageException(PTMessageEnum.RETURN_LAST_ERROR);
                }
            }
            
            //查询7贷_存量数据回款截止日期
            Date finishDate = DateUtil.parseDate("2018-03-10");
            BsSysConfig config = bsSysConfigService.findKey(ThirdSysChannelEnum.YUN_DAI.getCode().equals(channel) ? Constants.YUN_DAI_OLD_FINISH_DATE : Constants.SEVEN_DAI_OLD_FINISH_DATE);
            if(config != null){
            	finishDate = DateUtil.parseDate(config.getConfValue());
            }
            //BsBatchBuy表的expectTime是否是3.10（可配）之后，是则调用3.10-9.19的提前回款方案4，否则是二方案5（3.10及之前）
            if(batchBuys.get(0).getExpectTime().compareTo(finishDate) <= 0){
            	doFinishBefore(req,batchBuys.get(0),channelName);
            }else{
            	doFinishAfter(req,batchBuys.get(0),channelName);
            }
            log.info("========={"+ channelName + "系统回款通知处理}结束=========");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
        }
		
	}

	/**
	 * 3.10之后到期的存量回款
	 * @param req
	 * @param bsBatchBuy
	 * @param channelName 
	 */
	private void doFinishAfter(final G2BReqMsg_DafyPayment_SysReturnMoneyNotice req,
			BsBatchBuy batchBuy, final String channelName) {
		/**
		 * 1、校验该期回款本金是否和BsBatchBuy中的amount/sendAmount相同，不同告警，抛异常；
		 * 2、校验该期回款利息，本次应回利息= 上期利息/30天*（当天日期-上次回款日期） 和【本次结息】A是否相等，不等告警，继续。
		 * 3、计算各应给对应【所有用户利息】C=用户总利息/期数/30*（当天日期-上次回款日期）
		 * 4、本次应付利息C，若A<C告警，按原方式记账：新增系统记账流水表JSH 增加金额A，并冻结C），新增记账【系统还款户添加本金+C】，回款通知表处理状态改为成功
		 * 5、获取BatchId对应的BsBatchBuyDetail列表，循环列表，
		 * 	修改还款计划lnRepaySchedule表FinishTime，状态为已还；
		 * 	根据每个理财人的计息天数（起息日到昨日）计算应得利息；
		 * 	记录LnFinanceRepaySchedule表新增理财人本金和利息Cn，最后的用户利息取总利息C-（C1-Cn）LnDepositionRepaySchedule 新增；
		 * 	LnDepositionRepayScheduleDetail 新增本金、利息状态同还款分账时一致，为DF_PENDING；
		 * 	废弃原理财户REG，新建站岗户AUTH_YUN/AUTH_7（备注id为原REG户id），开户金额为原理财户开户金额，可用余额为0，新增补差户DIFF；
		 * 	新增子账户对BsSubAccountPair，修改原【债权关系表】 数据中的sub_account_id
		 * 	新增系统记账及流水表JSH ，冻结金额-利息部分
		 */
		//校验本金
        if(batchBuy.getSendAmount().compareTo(req.getProductAmount()) != 0){
        	log.info("========={" + channelName + "系统回款通知处理}异常回款通知，原因：最后一期还款本金："
        			+req.getProductAmount()+"，应还本金为："+batchBuy.getSendAmount()+"=========");
        	Double diffAmount = MoneyUtil.subtract(batchBuy.getSendAmount(), req.getProductAmount()).doubleValue();
        	specialJnlService.warn4Fail(diffAmount,channelName +"系统回款通知,3.10之后回款，本金差额（应回-回款）"+diffAmount,"",channelName + "3.10后回款本金不一致",false);
        	throw new PTMessageException(PTMessageEnum.RETURN_LAST_PRINCIPAL_ERROR);
        }
        //校验回款利息：获得之前的回款利息，获得此次计息天数
        //先查询系统回款记录表，是否有结息回款，若有：利息/30天获得每日利息；若无：直接告警
        BsSysReceiveMoneyExample moneyExample = new BsSysReceiveMoneyExample();
        moneyExample.createCriteria().andTypeEqualTo(Constants.RETURN_NOTICE_TYPE_INTEREST)
        	.andProductOrderNoEqualTo(req.getProductOrderNo()).andStatusEqualTo(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
        moneyExample.setOrderByClause("pay_finsh_time DESC");
        List<BsSysReceiveMoney> receiveMoneys = bsSysReceiveMoneyMapper.selectByExample(moneyExample);
        final Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(req.getPayFinshTime()));
        int payDays = 0;
        Date lastFinshDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(batchBuy.getSendTime())); //默认此次计息的起息日为购买日期
        if(CollectionUtils.isNotEmpty(receiveMoneys)){
        	lastFinshDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(receiveMoneys.get(0).getPayFinshTime())); //上次结息日（此次计息的起息日）
        }
        payDays = DateUtil.getDiffeDay(today, lastFinshDate);
    	Double needPayInterest = MoneyUtil.multiply(payDays,
    			MoneyUtil.divide(receiveMoneys.get(0).getProductInterest(), 30d, 4).doubleValue()).doubleValue();
    	log.info("========={" + channelName + "系统回款通知处理}3.10之后的回款，上次结息日（此次计息的起息日）："
    			+DateUtil.formatYYYYMMDD(lastFinshDate)+"，此次计息的结息日（今日不计息）："+DateUtil.formatYYYYMMDD(new Date())
    			+"，计息天数："+payDays+"，应回款利息："+MoneyUtil.defaultRound(needPayInterest).doubleValue()+"，回款利息："+req.getProductInterest()+"=========");
    	Double diffAmount = MoneyUtil.subtract(MoneyUtil.defaultRound(needPayInterest).doubleValue(), req.getProductInterest()).doubleValue();
    	if(diffAmount != 0){
    		//利息误差金额告警
    		specialJnlService.warn4FailNoSMS(diffAmount,channelName +"系统回款通知,3.10之后回款，利息差额"+diffAmount,"",channelName + "3.10后回款利息不一致");
    	}
    	//接口设定 每次只会有一个批次回款通知,故此处直接取列表第一个数据
        Integer batchId = batchBuy.getId();
        String oriOutOrderId = req.getPayOrderNo();
        //记录通知到系统购买产品表
        BsBatchBuy batchBuyTemp = new BsBatchBuy();
        batchBuyTemp.setId(batchId);
        batchBuyTemp.setDafyPay19MpOrderNo(oriOutOrderId);
        batchBuyTemp.setReceiveAmount(MoneyUtil.add(req.getProductAmount(), req.getProductInterest()).doubleValue());
        batchBuyTemp.setReturnTime(req.getPayFinshTime());
        batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_RETURN_SUCCESS);
        batchBuyTemp.setUpdateTime(new Date());
        bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuyTemp);
        BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
        List<String> statuss = new ArrayList<String>();
        statuss.add(Constants.RETURN_STATUS_NOT);
        statuss.add(Constants.RETURN_STATUS_FAIL);
        detailExample.createCriteria().andBatchIdEqualTo(batchId).andReturnStatusIn(statuss);
        List<BsBatchBuyDetail> details = bsBatchBuyDetailMapper.selectByExample(detailExample);
        log.info("========={批量客户回款}批次号["+batchBuy.getSendBatchId()+"]找到["+details.size()+"]笔理财产品待回款=========");
        //计算用户月利息和系统月利息，入账
        final double productTotalAmount = batchBuy.getAmount();//总本金
    	//该批次用户产品总用户收益
    	final BigDecimal userInterest = calcUserTotalInterestByDays(details, payDays);
    	int allDays = DateUtil.getDiffeDay(today, DateUtil.parseDate(DateUtil.formatYYYYMMDD(batchBuy.getSendTime())));
    			//payDays+30*(Integer.valueOf(req.getProductReturnTerm())-1);//总天数
    	final BigDecimal userTotalInterest = calcUserTotalInterestByDays(details,allDays);
    	//该批次总利息=总还款金额-总购买本金
        final double sysMonthProductInterest =  MoneyUtil.subtract(req.getAmount(), batchBuy.getAmount()).doubleValue();
    	transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //系统记账流水表JSH 增加本次结算利息A，并冻结C），新增记账【系统还款户添加本金+C】
                newReturnSysAccount(channelName,productTotalAmount,sysMonthProductInterest,userInterest,req,userTotalInterest,false);
            }
        });
    	//回款通知表处理状态改为成功
        BsSysReceiveMoneyExample updateReceiveMoneyExample = new BsSysReceiveMoneyExample();
        updateReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                .andProductReturnTermEqualTo(req.getProductReturnTerm());
        List<BsSysReceiveMoney> updateReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(updateReceiveMoneyExample);
        BsSysReceiveMoney updateReceiveMoney = new BsSysReceiveMoney();
        updateReceiveMoney.setId(updateReceiveMoneys.get(0).getId());
        updateReceiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
        updateReceiveMoney.setUpdateTime(new Date());
        bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(updateReceiveMoney);

        log.info("========={"+ channelName + "系统回款通知处理}循环BsBatchBuyDetail列表，处理用户回款=========");
        //根据回款计划表，进行单笔客户回款
        BsBatchBuyDetailExample buyDetailExample = new BsBatchBuyDetailExample();
        buyDetailExample.createCriteria().andBatchIdEqualTo(batchBuy.getId()).andReturnStatusEqualTo(Constants.RETURN_STATUS_NOT);
        List<BsBatchBuyDetail> buyDetails = bsBatchBuyDetailMapper.selectByExample(buyDetailExample);
        if(buyDetails!=null && buyDetails.size()>0){
        	final Date batchBuyDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(batchBuy.getSendTime()));
        	//returnDetails最后一条
        	final Integer lastId = buyDetails.get(buyDetails.size()-1).getId();
        	final List<Double> sumInterest = new ArrayList<Double>();
        	sumInterest.add(0d);
        	for (final BsBatchBuyDetail batchBuyDetail : buyDetails) {
        		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
        			@Override
        			protected void doInTransactionWithoutResult(TransactionStatus status) {
                        Integer userId = batchBuyDetail.getUserId();
                        Integer subAccountId = batchBuyDetail.getSubAccountId();
                        //产品户信息
                        BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
                        //产品本金
                        BigDecimal principal = new BigDecimal(String.valueOf(productAccount.getBalance()));
                        //产品购买日到今日（当日不计息）
                        Integer days = DateUtil.getDiffeDay(today, batchBuyDate);
                        Double interest = 0d;
                        if(batchBuyDetail.getId() == lastId){
                        	//最后一期利息=C-（C1+...+Cn）
                        	interest = MoneyUtil.subtract(userTotalInterest.doubleValue(), sumInterest.get(0)).doubleValue();
                        }else{
                        	//产品收益(四舍五入)=本金*利率*天数/365
                        	interest = CalculatorUtil.calculate("(a*a*a)/a", principal.doubleValue(), productAccount.getProductRate(), days.doubleValue(), 36500d);
                        	Double sumInterestTemp = MoneyUtil.add(sumInterest.get(0),interest).doubleValue();
                        	sumInterest.clear();
                        	sumInterest.add(sumInterestTemp);
                        }
                        
                        //修改BsBatchBuyDetail状态
                		BsBatchBuyDetail buyDetail = new BsBatchBuyDetail();
                        buyDetail.setId(batchBuyDetail.getId());
                        buyDetail.setReturnStatus(Constants.RETURN_STATUS_SUCCESS);
                        buyDetail.setUpdateTime(new Date());
                        bsBatchBuyDetailMapper.updateByPrimaryKeySelective(buyDetail);  
                        //lnRepaySchedule修改，LnFinanceRepaySchedule、LnDepositionRepaySchedule及Detail新增
                        addLnSchedule(subAccountId,principal.doubleValue(),interest.doubleValue());
                        //JSH冻结金额减少用户利息----------------------之前已经统一一笔扣除
                        //subFree4JSH(subAccountId,interest.doubleValue());
                        //废弃原理财户REG，新建站岗户AUTH_YUN/AUTH_7，开户金额为原理财户开户金额，可用余额为0，新增补差户DIFF，修改原【债权关系表】 数据中的sub_account_id
                        openNewSubAccount(req.getChannel(),subAccountId,userId,productAccount.getTerm());
                        
        			}
        		});
        	}
        }
        
	}

	/**
	 * JSH冻结金额减少用户利息
	 * @param subAccountId
	 * @param interest
	 * @param principal
	 */
	protected void subFree4JSH(Integer subAccountId,Double interest) {
		//系统子账户表 JSH 冻结金额，  减少用户利息
        BsSysSubAccount jshSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_JSH);
        BsSysSubAccount readyUpdateJsh = new BsSysSubAccount();
        readyUpdateJsh.setId(jshSubAccountLock.getId());
        readyUpdateJsh.setBalance(MoneyUtil.subtract(jshSubAccountLock.getBalance(), interest).doubleValue());
        readyUpdateJsh.setFreezeBalance(MoneyUtil.subtract(jshSubAccountLock.getFreezeBalance(),interest).doubleValue());

        //新增系统记账流水表，JSH余额冻结减少
        BsSysAccountJnl sysAccountJnl= new BsSysAccountJnl();
        sysAccountJnl.setTransTime(new Date());
        sysAccountJnl.setTransCode(Constants.SYS_FREEZE);
        sysAccountJnl.setTransName("3.10后JSH冻结金额减少用户利息");
        sysAccountJnl.setTransAmount(interest);
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(new Date());
        sysAccountJnl.setChannelJnlNo(null);
        sysAccountJnl.setCdFlag1(Constants.CD_FLAG_C);
        sysAccountJnl.setSubAccountCode1(jshSubAccountLock.getCode());
        sysAccountJnl.setBeforeBalance1(jshSubAccountLock.getBalance());
        sysAccountJnl.setAfterBalance1(readyUpdateJsh.getBalance());
        sysAccountJnl.setBeforeAvialableBalance1(jshSubAccountLock.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance1(readyUpdateJsh.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance1(jshSubAccountLock.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance1(readyUpdateJsh.getFreezeBalance());
        sysAccountJnl.setFee(0.0);
        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
        sysAccountJnlMapper.insertSelective(sysAccountJnl);
		
	}

	/**
	 * 废弃原理财户REG，新建站岗户AUTH_YUN/AUTH_7，开户金额为原理财户开户金额，可用余额为0，
	 * 新增补差户DIFF，插入子账户对BsSubAccountPair，
	 * 修改原【债权关系表】 数据中的sub_account_id
	 * @param channel
	 * @param oldSubAccountId 原理财户REG
	 * @param userId 
	 * @param term 
	 */
	protected void openNewSubAccount(String channel, Integer oldSubAccountId, Integer userId, int term) {
		Date now = new Date();
		BsSubAccount oldSubAccount = subAccountService.findSubAccountById(oldSubAccountId);
		BsSubAccount oldSubAccountTemp = new BsSubAccount();
		oldSubAccountTemp.setId(oldSubAccountId);
		oldSubAccountTemp.setStatus(Constants.SUBACCOUNT_STATUS_CLOSE);//销户
		oldSubAccountTemp.setCloseTime(new Date());
		subAccountService.modifySubAccountById(oldSubAccountTemp);
		PartnerEnum partner = null;
		if(ThirdSysChannelEnum.YUN_DAI.getCode().equals(channel)){
			//云贷，新建AUTH_YUN
			partner = PartnerEnum.YUN_DAI_SELF;
		}else{
			partner = PartnerEnum.SEVEN_DAI_SELF;
		}
		ProductType type = SubAccountCode.productTypeMap.get(partner);
        BsSubAccount authSub = new BsSubAccount();
        authSub.setAccountId(oldSubAccount.getAccountId());
        authSub.setCode(accountHandleService.generateProductAccount(oldSubAccount.getProductId(), userId));
        authSub.setProductId(oldSubAccount.getProductId());
        authSub.setProductType(type.getAuthCode());
        authSub.setProductCode(oldSubAccount.getProductCode());
        authSub.setProductName(oldSubAccount.getProductName());
        authSub.setProductRate(oldSubAccount.getProductRate());
        authSub.setExtraRate(0.0);
        authSub.setOpenBalance(oldSubAccount.getOpenBalance());
        authSub.setBalance(0d);
        authSub.setAvailableBalance(0d);
        authSub.setCanWithdraw(0d);
        authSub.setFreezeBalance(0.0);
        authSub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
        authSub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
        authSub.setInterestBeginDate(oldSubAccount.getInterestBeginDate()); //起息日期
        authSub.setLastFinishInterestDate(oldSubAccount.getLastFinishInterestDate()); //结束日期
        authSub.setAccumulationInerest(oldSubAccount.getAccumulationInerest());
        authSub.setOpenTime(oldSubAccount.getOpenTime());
        authSub.setLastCalInterestDate(oldSubAccount.getLastCalInterestDate());
        authSub.setTransferTime(new Date());
        authSub.setNote(oldSubAccountId.toString());
        
        subAccountMapper.insertSelective(authSub);
		
        //补差户生成
        BsSubAccount diffSub = new BsSubAccount();
        diffSub.setAccountId(oldSubAccount.getAccountId());
        diffSub.setCode(accountHandleService.generateProductAccount(oldSubAccount.getProductId(), userId));
        diffSub.setProductId(oldSubAccount.getProductId());
        diffSub.setProductType(type.getDiffCode());
        diffSub.setProductCode(oldSubAccount.getProductCode());
        diffSub.setProductName(oldSubAccount.getProductName());
        diffSub.setProductRate(oldSubAccount.getProductRate());
        diffSub.setExtraRate(0.0);
        diffSub.setOpenBalance(0.0);
        diffSub.setBalance(0.0);
        diffSub.setAvailableBalance(0.0);
        diffSub.setCanWithdraw(0.0);
        diffSub.setFreezeBalance(0.0);
        diffSub.setTransStatus(Constants.USER_SUB_TRANS_STATUS_0);
        diffSub.setStatus(Constants.SUBACCOUNT_STATUS_FINANCING);
        diffSub.setInterestBeginDate(oldSubAccount.getInterestBeginDate()); //起息日期
        diffSub.setLastFinishInterestDate(oldSubAccount.getLastFinishInterestDate()); //结束日期
        diffSub.setAccumulationInerest(0.0);
        diffSub.setOpenTime(oldSubAccount.getOpenTime());
        diffSub.setTransferTime(new Date());
        subAccountMapper.insertSelective(diffSub);
        //子账户对插入
        BsSubAccountPair subPair = new BsSubAccountPair();
        subPair.setAuthAccountId(authSub.getId());
        subPair.setRegDAccountId(null);
        subPair.setRedAccountId(null);
        subPair.setDiffAccountId(diffSub.getId());
        subPair.setCreateTime(now);
        subPair.setUpdateTime(now);
        subPair.setTerm(term);
        bsSubAccountPairMapper.insertSelective(subPair);
        //根据subAccountId查询债权关系，修改原【债权关系表】 数据中的sub_account_id
        LnLoanRelationExample example = new LnLoanRelationExample();
        example.createCriteria().andBsSubAccountIdEqualTo(oldSubAccountId);
        List<LnLoanRelation> relationList = lnLoanRelationMapper.selectByExample(example);
        LnLoanRelation loanRelationTemp = new LnLoanRelation();
        loanRelationTemp.setId(relationList.get(0).getId());
        loanRelationTemp.setBsSubAccountId(authSub.getId());
        lnLoanRelationMapper.updateByPrimaryKeySelective(loanRelationTemp);
        
	}

	/**
	 * 3.10及之前到期的存量回款
	 * @param req
	 * @param channelName 
	 * @param bsBatchBuy
	 */
	private void doFinishBefore(final G2BReqMsg_DafyPayment_SysReturnMoneyNotice req,
			BsBatchBuy batchBuy, final String channelName) {
		/**
		 * 1、判断是否是最后一期，不是最后一期则进行告警抛错
		 * 2、校验该期回款本金是否和BsBatchBuy中的amount/sendAmount相同，不同告警，抛异常
		 * 3、计算应付用户总利息，最后次利息 = 用户总利息-(期数-1)*(用户总利息/期数)，同原逻辑
		 * 4、按原方式记账：新增系统记账流水表JSH 增加金额A，并冻结C），新增记账【系统还款户添加本金+C】，回款通知表处理状态改为成功
		 * 5、BsBatchReturnDetail新增数据
		 * 6、获取BatchId对应的BsBatchBuyDetail列表,循环列表
		 * 	修改还款计划lnRepaySchedule表FinishTime，状态为已还；
		 * 	记录LnFinanceRepaySchedule表新增理财人本金和利息Cn=计息天数*本金*利率+本金；
		 * 	LnDepositionRepayScheduleDetail 新增本金、利息状态同还款分账时一致，为DF_PENDING；
		 * 	BsBatchReturnDetail回款结算表 新增状态为RETURN_STATUS_PROCCESS
		 */
		String oriOutOrderId = req.getPayOrderNo();
		//判断是否是最后一期，不是最后一期则进行告警抛错
        BsProductExample productExample = new BsProductExample();
        productExample.createCriteria().andCodeEqualTo(req.getProductCode());
        List<BsProduct> bsProducts = bsProductMapper.selectByExample(productExample);
        Integer term = 0;
        if(bsProducts!=null && bsProducts.size()>0){
            term = bsProducts.get(0).getTerm();
            if(term < 0){
                term = 1;
            }
            //非最后一期
            Date sendTime = batchBuy.getSendTime();//系统购买时间
            Date sendTimeFmt = DateUtil.parseDate(DateUtil.formatYYYYMMDD(sendTime));
            Date separateDate = DateUtil.parseDate("2016-02-02");//分期回款开始时间
            if(!String.valueOf(term).equals(req.getProductReturnTerm()) && DateUtil.getDiffeDay(sendTimeFmt, separateDate) > -1){
                log.info("========={" + channelName + "系统回款通知处理}异常回款通知，原因：期数不是最后一期=========");
                //回款通知表处理状态改为失败
                returnReceiveFail(req,  channelName + "理财回款期数非最后一期", "{" + channelName + "理财回款通知}产品批次号["+req.getProductOrderNo()+"]异常回款通知，原因：期数不是最后一期");
                throw new PTMessageException(PTMessageEnum.RETURN_NOTICE_TERM_NOT_LAST);
            }
        }
        //校验本金
        if(batchBuy.getSendAmount().compareTo(req.getProductAmount()) != 0){
        	log.info("========={" + channelName + "系统回款通知处理}异常回款通知，原因：最后一期还款本金："
        			+req.getProductAmount()+"，应还本金为："+batchBuy.getSendAmount()+"=========");
        	Double diffAmount = MoneyUtil.subtract(batchBuy.getSendAmount(), req.getProductAmount()).doubleValue();
        	specialJnlService.warn4Fail(diffAmount,channelName +"系统回款通知,3.10之前回款（最后次回款），本金差额（应回-回款）"+diffAmount,"",channelName + "3.10前回款本金不一致",false);
        	throw new PTMessageException(PTMessageEnum.RETURN_LAST_PRINCIPAL_ERROR);
        }
        
        //接口设定 每次只会有一个批次回款通知,故此处直接取列表第一个数据
        Integer batchId = batchBuy.getId();

        //记录通知到系统购买产品表
        BsBatchBuy batchBuyTemp = new BsBatchBuy();
        batchBuyTemp.setId(batchId);
        batchBuyTemp.setDafyPay19MpOrderNo(oriOutOrderId);
        batchBuyTemp.setReceiveAmount(MoneyUtil.add(req.getProductAmount(), req.getProductInterest()).doubleValue());
        batchBuyTemp.setReturnTime(req.getPayFinshTime());
        batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_RETURN_SUCCESS);
        batchBuyTemp.setUpdateTime(new Date());
        bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuyTemp);
        BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
        List<String> statuss = new ArrayList<String>();
        statuss.add(Constants.RETURN_STATUS_NOT);

        detailExample.createCriteria().andBatchIdEqualTo(batchId).andReturnStatusIn(statuss);
        List<BsBatchBuyDetail> details = bsBatchBuyDetailMapper.selectByExample(detailExample);
        log.info("========={批量客户回款}批次号["+batchBuy.getSendBatchId()+"]找到["+details.size()+"]笔理财产品待回款=========");
        //根据批次查询对应的待回款用户产品明细
        if(details!=null && details.size()>0){
            //系统回款入账
            //计算用户月利息和系统月利息，入账
            final double productTotalAmount = batchBuy.getAmount();//总本金
            //该批次总利息=总还款金额-总购买本金
            final double sysMonthProductInterest =  MoneyUtil.subtract(req.getAmount(), batchBuy.getAmount()).doubleValue();
            final BigDecimal userTotalInterest = calcUserTotalInterest(details);//该批次用户产品总用户收益
            //先查询系统回款记录表，是否有结息回款，若有：则需进行前面结息回款的用户利息累加统计，得到最后精确的用户利息；若无：则直接计算本次用户利息
            BsSysReceiveMoneyExample moneyExample = new BsSysReceiveMoneyExample();
            moneyExample.createCriteria().andTypeEqualTo(Constants.RETURN_NOTICE_TYPE_INTEREST).andProductOrderNoEqualTo(req.getProductOrderNo());
            final List<BsSysReceiveMoney> receiveMoneys = bsSysReceiveMoneyMapper.selectByExample(moneyExample);
            final Integer tempTerm = term;
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {
                    BigDecimal userInterest = new BigDecimal(0);
                    //若有：则需进行前面结息回款的用户利息累加统计,最终得到本次用户利息:最后次利息=用户总利息-(期数-1)*(用户总利息/期数)
                    if(receiveMoneys!=null && receiveMoneys.size()>0){
                        BigDecimal avgUserInterest = MoneyUtil.divide(userTotalInterest.doubleValue(), tempTerm);
                        userInterest = MoneyUtil.subtract(userTotalInterest.doubleValue(), MoneyUtil.multiply(avgUserInterest.doubleValue(), Double.valueOf(tempTerm-1)).doubleValue());
                    }
                    //若无：C则本次用户利息=该批次用户产品总用户收益
                    else{
                        userInterest = userTotalInterest;
                    }
                    //系统记账流水表JSH 增加本次结算利息A，并冻结C），新增记账【系统还款户添加本金+C】，系统回款户
                    newReturnSysAccount(channelName,productTotalAmount,sysMonthProductInterest,userInterest,
                    		req,userTotalInterest,true);
                }
            });
            //回款通知表处理状态改为成功
            BsSysReceiveMoneyExample updateReceiveMoneyExample = new BsSysReceiveMoneyExample();
            updateReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                    .andProductReturnTermEqualTo(req.getProductReturnTerm());
            List<BsSysReceiveMoney> updateReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(updateReceiveMoneyExample);
            BsSysReceiveMoney updateReceiveMoney = new BsSysReceiveMoney();
            updateReceiveMoney.setId(updateReceiveMoneys.get(0).getId());
            updateReceiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
            updateReceiveMoney.setUpdateTime(new Date());
            bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(updateReceiveMoney);

            log.info("========={"+ channelName + "系统回款通知处理}循环BsBatchBuyDetail列表，处理用户回款=========");

            //根据回款计划表，进行单笔客户回款
            BsBatchBuyDetailExample buyDetailExample = new BsBatchBuyDetailExample();
            buyDetailExample.createCriteria().andBatchIdEqualTo(batchBuy.getId()).andReturnStatusEqualTo(Constants.RETURN_STATUS_NOT);
            List<BsBatchBuyDetail> buyDetails = bsBatchBuyDetailMapper.selectByExample(buyDetailExample);
            if(buyDetails!=null && buyDetails.size()>0){
            	for (final BsBatchBuyDetail batchBuyDetail : buyDetails) {
            		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
            			@Override
            			protected void doInTransactionWithoutResult(TransactionStatus status) {
	                        Integer userId = batchBuyDetail.getUserId();
	                        Integer subAccountId = batchBuyDetail.getSubAccountId();
	                        //产品户信息
	                        BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
	                        //产品本金
	                        BigDecimal principal = new BigDecimal(String.valueOf(productAccount.getBalance()));
	                        Integer days = productAccount.getTerm4Day();
	                        //产品收益(四舍五入)
	                        Double interest = CalculatorUtil.calculate("(a*a*a)/a", principal.doubleValue(), productAccount.getProductRate(), days.doubleValue(), 36500d);
	                                
	                        BsBatchReturnDetail returnDetail = new BsBatchReturnDetail();
	                        returnDetail.setBatchId(batchBuyDetail.getBatchId());
	                        returnDetail.setRate(batchBuyDetail.getRate());
	                        returnDetail.setReturnStatus(Constants.RETURN_STATUS_NOT);
	                        returnDetail.setReturnAmount(MoneyUtil.add(principal.doubleValue(), interest.doubleValue()).doubleValue());
	                        returnDetail.setUserId(userId);
	                        returnDetail.setSubAccountId(subAccountId);
	                        returnDetail.setPrincipal(principal.doubleValue());
	                        returnDetail.setInterest(interest.doubleValue());
	                        returnDetail.setCreateTime(new Date());
	                        returnDetail.setUpdateTime(new Date());
	                        bsBatchReturnDetailMapper.insertSelective(returnDetail);
	                        //lnRepaySchedule修改，LnFinanceRepaySchedule、LnDepositionRepaySchedule及Detail新增
	                        addLnSchedule(subAccountId,principal.doubleValue(),interest.doubleValue());
	                        //修改BsBatchBuyDetail状态
	                		BsBatchBuyDetail buyDetail = new BsBatchBuyDetail();
	                        buyDetail.setId(batchBuyDetail.getId());
	                        buyDetail.setReturnStatus(Constants.RETURN_STATUS_SUCCESS);
	                        buyDetail.setUpdateTime(new Date());
	                        bsBatchBuyDetailMapper.updateByPrimaryKeySelective(buyDetail);
            			}
            		});
            	}
            }
        }
	}

	/**
	 * 修改还款计划lnRepaySchedule表FinishTime，状态为已还
	 * 新增LnFinanceRepaySchedule表LnDepositionRepaySchedule及Detail
	 * @param subAccountId
	 * @param principal
	 * @param interest
	 */
	private void addLnSchedule(Integer subAccountId, double principal, double interest) {
		//根据subAccountId查询债权关系
		LnLoanRelationExample example = new LnLoanRelationExample();
		example.createCriteria().andBsSubAccountIdEqualTo(subAccountId);
		List<LnLoanRelation> relationList = lnLoanRelationMapper.selectByExample(example);
		
		//查询ln_repay_schedule
        LnRepayScheduleExample repayScheduleExample = new LnRepayScheduleExample();
        repayScheduleExample.createCriteria().andLoanIdEqualTo(relationList.get(0).getLoanId()).andStatusNotEqualTo(Constants.LN_REPAY_CANCELLED);
        List<LnRepaySchedule> repaySchedulList = lnRepayScheduleMapper.selectByExample(repayScheduleExample);
        LnRepaySchedule lnRepayScheduleTemp = new LnRepaySchedule();
        lnRepayScheduleTemp.setId(repaySchedulList.get(0).getId());
        lnRepayScheduleTemp.setFinishTime(new Date());
        lnRepayScheduleTemp.setUpdateTime(new Date());
        lnRepayScheduleTemp.setStatus(Constants.LN_REPAY_REPAIED);
        lnRepayScheduleMapper.updateByPrimaryKeySelective(lnRepayScheduleTemp);
        
		//生成理财人还款计划数据
		LnFinanceRepaySchedule financeRepaySchedule = new LnFinanceRepaySchedule();
		financeRepaySchedule.setRepaySerial(1);
		//当天时间
		Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		financeRepaySchedule.setCreateTime(new Date());
		financeRepaySchedule.setPlanDate(today);
		financeRepaySchedule.setRelationId(relationList.get(0).getId());
		financeRepaySchedule.setStatus(Constants.FINANCE_REPAY_SATAE_REPAYING);
		financeRepaySchedule.setUpdateTime(new Date());
		financeRepaySchedule.setPlanTotal(MoneyUtil.add(principal, interest).doubleValue());
		financeRepaySchedule.setPlanPrincipal(principal);
		financeRepaySchedule.setPlanInterest(interest);
		financeRepaySchedule.setPlanFee(0d);
		financeRepaySchedule.setPlanTransInterest(0d);
		lnFinanceRepayScheduleMapper.insertSelective(financeRepaySchedule);
		
		//查询借款数据
		LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(relationList.get(0).getLoanId());
		//存管还款计划表
        LnDepositionRepaySchedule schedul = new LnDepositionRepaySchedule();
        schedul.setLnUserId(lnLoan.getLnUserId());
        schedul.setLoanId(repaySchedulList.get(0).getLoanId());
        schedul.setPartnerRepayId(repaySchedulList.get(0).getPartnerRepayId());
        schedul.setSerialId(1);
        schedul.setPlanDate(repaySchedulList.get(0).getPlanDate());
        schedul.setPlanTotal(MoneyUtil.add(principal, interest).doubleValue());
        schedul.setStatus(Constants.LN_REPAY_STATUS_INIT);
        schedul.setReturnFlag(Constants.DEP_REPAY_RETURN_FLAG_DF_PENDING);
        schedul.setRepayType(Constants.LN_DEP_REPAY_SCHEDULE_REPAY_TYPE_NORMAL_REPAY);
        	
        schedul.setCreateTime(new Date());
        schedul.setUpdateTime(new Date());
        lnDepositionRepayScheduleMapper.insertSelective(schedul);
		//存管还款计划表明细
        LnDepositionRepayScheduleDetail detailInterest = new LnDepositionRepayScheduleDetail();
        detailInterest.setPlanId(schedul.getId());
        detailInterest.setPlanAmount(interest);
        detailInterest.setSubjectCode(LoanSubjects.SUBJECT_CODE_INTEREST.getCode());
        detailInterest.setCreateTime(new Date());
        detailInterest.setUpdateTime(new Date());
        lnDepositionRepayScheduleDetailMapper.insertSelective(detailInterest);

        LnDepositionRepayScheduleDetail detailPrincipal = new LnDepositionRepayScheduleDetail();
        detailPrincipal.setPlanId(schedul.getId());
        detailPrincipal.setPlanAmount(principal);
        detailPrincipal.setSubjectCode(LoanSubjects.SUBJECT_CODE_PRINCIPAL.getCode());
        detailPrincipal.setCreateTime(new Date());
        detailPrincipal.setUpdateTime(new Date());
        lnDepositionRepayScheduleDetailMapper.insertSelective(detailPrincipal);
        
        
		
	}

	/**
	 * 存量数据回款系统记账：系统记账流水表JSH 增加本次结算利息A，并冻结C），新增记账【系统还款户添加本金+C】
	 * @param channelName
	 * @param productTotalAmount
	 * @param sysMonthProductInterest
	 * @param userInterest
	 * @param req 
	 * @param userTotalInterest 
	 * @param isBefore 是否是3.10之前的回款
	 */
	protected void newReturnSysAccount(String channelName, double productTotalAmount,
			double sysMonthProductInterest, BigDecimal userInterest, G2BReqMsg_DafyPayment_SysReturnMoneyNotice req, 
			BigDecimal userTotalInterest, boolean isBefore) {
		//系统子账户表 JSH本次结算利息A，并冻结此次回至用户利息
        BsSysSubAccount jshSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_JSH);
        BsSysSubAccount readyUpdateJsh = new BsSysSubAccount();
        readyUpdateJsh.setId(jshSubAccountLock.getId());
        readyUpdateJsh.setBalance(MoneyUtil.add(jshSubAccountLock.getBalance(), sysMonthProductInterest).doubleValue());
        readyUpdateJsh.setAvailableBalance(MoneyUtil.subtract(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysMonthProductInterest).doubleValue(), userInterest.doubleValue()).doubleValue());
        readyUpdateJsh.setCanWithdraw(MoneyUtil.subtract(MoneyUtil.add(jshSubAccountLock.getCanWithdraw(), sysMonthProductInterest).doubleValue(), userInterest.doubleValue()).doubleValue());
        readyUpdateJsh.setFreezeBalance(MoneyUtil.add(jshSubAccountLock.getFreezeBalance(), userInterest.doubleValue()).doubleValue());

        //新增系统记账流水表，JSH余额增加
        BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
        sysAccountJnl.setTransTime(new Date());
        sysAccountJnl.setTransCode(Constants.SYS_RETURN_INTEREST);
        sysAccountJnl.setTransName(channelName + "产品最后次结息回款");
        sysAccountJnl.setTransAmount(sysMonthProductInterest);
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(req.getPayFinshTime());
        sysAccountJnl.setChannelJnlNo(req.getPayOrderNo());
        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
        sysAccountJnl.setSubAccountCode2(jshSubAccountLock.getCode());
        sysAccountJnl.setBeforeBalance2(jshSubAccountLock.getBalance());
        sysAccountJnl.setAfterBalance2(readyUpdateJsh.getBalance());
        sysAccountJnl.setBeforeAvialableBalance2(jshSubAccountLock.getAvailableBalance());
        sysAccountJnl.setAfterAvialableBalance2(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysMonthProductInterest).doubleValue());
        sysAccountJnl.setBeforeFreezeBalance2(jshSubAccountLock.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance2(jshSubAccountLock.getFreezeBalance());
        sysAccountJnl.setFee(0.0);
        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
        sysAccountJnlMapper.insertSelective(sysAccountJnl);

        //新增系统记账流水表，JSH余额冻结
        BsSysAccountJnl sysAccountJnlFreeze = new BsSysAccountJnl();
        sysAccountJnlFreeze.setTransTime(new Date());
        sysAccountJnlFreeze.setTransCode(Constants.SYS_FREEZE);
        sysAccountJnl.setTransName(channelName + "产品最后次结息回款-用户月利息冻结");
        sysAccountJnl.setTransAmount(userInterest.doubleValue());
        sysAccountJnl.setSysTime(new Date());
        sysAccountJnl.setChannelTime(req.getPayFinshTime());
        sysAccountJnl.setChannelJnlNo(req.getPayOrderNo());
        sysAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
        sysAccountJnl.setSubAccountCode2(jshSubAccountLock.getCode());
        sysAccountJnl.setBeforeBalance2(readyUpdateJsh.getBalance());
        sysAccountJnl.setAfterBalance2(readyUpdateJsh.getBalance());
        sysAccountJnl.setBeforeAvialableBalance2(MoneyUtil.add(jshSubAccountLock.getAvailableBalance(), sysMonthProductInterest).doubleValue());
        sysAccountJnl.setAfterAvialableBalance2(readyUpdateJsh.getAvailableBalance());
        sysAccountJnl.setBeforeFreezeBalance2(jshSubAccountLock.getFreezeBalance());
        sysAccountJnl.setAfterFreezeBalance2(readyUpdateJsh.getFreezeBalance());
        sysAccountJnl.setFee(0.0);
        sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
        sysAccountJnlMapper.insertSelective(sysAccountJnl);
        
        String actCode = Util.getSysReturnSubActCode(ThirdSysChannelEnum.getEnumByCode(req.getChannel()));
        
        if(isBefore){
        	//用户回款动帐
            //系统子账户表 RETURN 增加用户总本金+用户总利息
            BsSysSubAccount returnSubAccountLock = bsSysSubAccountService.findSysSubAccount4Lock(actCode);
            BsSysSubAccount readyUpdate = new BsSysSubAccount();
            readyUpdate.setId(returnSubAccountLock.getId());
            readyUpdate.setBalance(MoneyUtil.add(returnSubAccountLock.getBalance(), userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue()).doubleValue());
            readyUpdate.setAvailableBalance(MoneyUtil.add(returnSubAccountLock.getAvailableBalance(), userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue()).doubleValue());
            readyUpdate.setCanWithdraw(MoneyUtil.add(returnSubAccountLock.getCanWithdraw(), userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue()).doubleValue());
            sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
            
            //新增系统记账流水表
            BsSysAccountJnl returnAccountJnl = new BsSysAccountJnl();
            returnAccountJnl.setTransTime(new Date());
            returnAccountJnl.setTransCode(Constants.SYS_RETURN);
            returnAccountJnl.setTransName(channelName + "回款用户获得总额");
            returnAccountJnl.setTransAmount(userTotalInterest.add(new BigDecimal(productTotalAmount)).doubleValue());
            returnAccountJnl.setSysTime(new Date());
            returnAccountJnl.setChannelTime(req.getPayFinshTime());
            returnAccountJnl.setChannelJnlNo(req.getPayOrderNo());
            returnAccountJnl.setCdFlag2(Constants.CD_FLAG_D);
            returnAccountJnl.setSubAccountCode2(returnSubAccountLock.getCode());
            returnAccountJnl.setBeforeBalance2(returnSubAccountLock.getBalance());
            returnAccountJnl.setAfterBalance2(readyUpdate.getBalance());
            returnAccountJnl.setBeforeAvialableBalance2(returnSubAccountLock.getAvailableBalance());
            returnAccountJnl.setAfterAvialableBalance2(readyUpdate.getAvailableBalance());
            returnAccountJnl.setBeforeFreezeBalance2(returnSubAccountLock.getFreezeBalance());
            returnAccountJnl.setAfterFreezeBalance2(returnSubAccountLock.getFreezeBalance());
            returnAccountJnl.setFee(0.0);
            returnAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
            sysAccountJnlMapper.insertSelective(returnAccountJnl);
        }
        
        //系统子账户表 JSH 减少用户总利息
        Double beforeBalance = readyUpdateJsh.getBalance();
        Double beforeAvailableBalance = readyUpdateJsh.getAvailableBalance();
        Double beforeFreezeBalance = readyUpdateJsh.getFreezeBalance();
        readyUpdateJsh.setBalance(MoneyUtil.subtract(beforeBalance, userTotalInterest.doubleValue()).doubleValue());
        readyUpdateJsh.setFreezeBalance(MoneyUtil.subtract(beforeFreezeBalance, userTotalInterest.doubleValue()).doubleValue());
        sysAccountMapper.updateByPrimaryKeySelective(readyUpdateJsh);

        

        BsSysAccountJnl sysAccountJnl4Subtract = new BsSysAccountJnl();
        sysAccountJnl4Subtract.setTransTime(new Date());
        sysAccountJnl4Subtract.setTransCode(Constants.SYS_RETURN_INTEREST);
        sysAccountJnl4Subtract.setTransName(channelName + "回款系统结算户用户总利息扣除");
        sysAccountJnl4Subtract.setTransAmount(userTotalInterest.doubleValue());
        sysAccountJnl4Subtract.setSysTime(new Date());
        sysAccountJnl4Subtract.setChannelTime(req.getPayFinshTime());
        sysAccountJnl4Subtract.setChannelJnlNo(req.getPayOrderNo());
        sysAccountJnl4Subtract.setCdFlag2(Constants.CD_FLAG_C);
        sysAccountJnl4Subtract.setSubAccountCode2(jshSubAccountLock.getCode());
        sysAccountJnl4Subtract.setBeforeBalance2(beforeBalance);
        sysAccountJnl4Subtract.setAfterBalance2(readyUpdateJsh.getBalance());
        sysAccountJnl4Subtract.setBeforeAvialableBalance2(beforeAvailableBalance);
        sysAccountJnl4Subtract.setAfterAvialableBalance2(readyUpdateJsh.getAvailableBalance());
        sysAccountJnl4Subtract.setBeforeFreezeBalance2(beforeFreezeBalance);
        sysAccountJnl4Subtract.setAfterFreezeBalance2(readyUpdateJsh.getFreezeBalance());
        sysAccountJnl4Subtract.setFee(0.0);
        sysAccountJnl4Subtract.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
        sysAccountJnlMapper.insertSelective(sysAccountJnl4Subtract);

        //用户回款动帐:新增记账【系统还款户添加本金+C】
        Double thdRepayAmount = MoneyUtil.add(productTotalAmount, userTotalInterest.doubleValue()).doubleValue();
        BsSysSubAccount thdRepayAct = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_THD_REPAY);
		BsSysSubAccount tempThdRepayAct = new BsSysSubAccount();
		tempThdRepayAct.setId(thdRepayAct.getId());
		tempThdRepayAct.setBalance(MoneyUtil.add(thdRepayAct.getBalance(), thdRepayAmount).doubleValue());
		tempThdRepayAct.setAvailableBalance(MoneyUtil.add(thdRepayAct.getAvailableBalance(), thdRepayAmount).doubleValue());
		tempThdRepayAct.setCanWithdraw(MoneyUtil.add(thdRepayAct.getCanWithdraw(), thdRepayAmount).doubleValue());
		bsSysSubAccountMapper.updateByPrimaryKeySelective(tempThdRepayAct);
		
		//还款资金子账户入账 记账
		BsSysAccountJnl sysActJnl4ThdRepay = new BsSysAccountJnl();
		sysActJnl4ThdRepay.setTransTime(new Date());
		sysActJnl4ThdRepay.setTransCode(Constants.SYS_THD_REPAY);
		sysActJnl4ThdRepay.setTransName("还款资金子账户入账");
		sysActJnl4ThdRepay.setTransAmount(thdRepayAmount);
		sysActJnl4ThdRepay.setSysTime(new Date());
		sysActJnl4ThdRepay.setChannelTime(null);
		sysActJnl4ThdRepay.setChannelJnlNo(null);
		sysActJnl4ThdRepay.setCdFlag2(Constants.CD_FLAG_D);
		sysActJnl4ThdRepay.setSubAccountCode2(thdRepayAct.getCode());
		sysActJnl4ThdRepay.setBeforeBalance2(thdRepayAct.getBalance());
		sysActJnl4ThdRepay.setAfterBalance2(tempThdRepayAct.getBalance());
		sysActJnl4ThdRepay.setBeforeAvialableBalance2(thdRepayAct.getAvailableBalance());
		sysActJnl4ThdRepay.setAfterAvialableBalance2(tempThdRepayAct.getAvailableBalance());
		sysActJnl4ThdRepay.setBeforeFreezeBalance2(thdRepayAct.getFreezeBalance());
		sysActJnl4ThdRepay.setAfterFreezeBalance2(thdRepayAct.getFreezeBalance());
		sysActJnl4ThdRepay.setFee(0.0);
		bsSysAccountJnlMapper.insertSelective(sysActJnl4ThdRepay);
		
	}

	@Override
	public String return4ManageCheck(List<String> sendBatchIdList) {
		SysReturnManageResVO res = new SysReturnManageResVO();
		if(CollectionUtils.isEmpty(sendBatchIdList)){
			return "批次号不能为空！";
		}
		for (String sendBatchId : sendBatchIdList) {
			//根据理财购买表，判断批次号是否正确，且未回款成功
	        BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
	        batchBuyExample.createCriteria().andSendBatchIdEqualTo(sendBatchId);
	        List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
	        if(batchBuys==null || batchBuys.size()<=0){
				return sendBatchId+"批次号有误，请重新输入！";
	        }
	        
	        if(Constants.BATCHBUY_STATUS_RETURN_SUCCESS.equals(batchBuys.get(0).getStatus())){
				return sendBatchId+"批次号已成功，请重新输入！";
	        }
	        
		}
		return null;
	}

	@Override
	public SysReturnManageResVO return4ManageGetList(
			List<String> sendBatchIdList) {
		SysReturnManageResVO res = new SysReturnManageResVO();
		List<SysReturnManageInfoVO> list = new ArrayList<SysReturnManageInfoVO>();
		int num = 1;
		Double sumTotal = 0d;
		Double sumPrincipal = 0d;
		for (String sendBatchId : sendBatchIdList) {
			//查询购买表信息
	        BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
	        batchBuyExample.createCriteria().andSendBatchIdEqualTo(sendBatchId);
	        List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
	        BsBatchBuy batchBuy = batchBuys.get(0);
	        SysReturnManageInfoVO detail = new SysReturnManageInfoVO();
	        detail.setRowno(num);
	        num+=1;
	        
	        //获取该回款批次的期数，计算成天
	        BsProductExample productExample = new BsProductExample();
	        productExample.createCriteria().andCodeEqualTo(batchBuys.get(0).getProductCode());
	        List<BsProduct> bsProducts = bsProductMapper.selectByExample(productExample);
	        detail.setTerm(bsProducts.get(0).getTerm() == 12?365 : bsProducts.get(0).getTerm()*30);
	        //本金
	        detail.setPrincipal(batchBuy.getSendAmount());
	        
	        //计算利息
	        BsSysReceiveMoneyExample moneyExample = new BsSysReceiveMoneyExample();
	        moneyExample.createCriteria().andTypeEqualTo(Constants.RETURN_NOTICE_TYPE_INTEREST)
	        	.andProductOrderNoEqualTo(batchBuy.getSendBatchId()).andStatusEqualTo(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
	        moneyExample.setOrderByClause("pay_finsh_time DESC");
	        List<BsSysReceiveMoney> receiveMoneys = bsSysReceiveMoneyMapper.selectByExample(moneyExample);
	        final Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
	        int payDays = 0;
	        Date lastFinshDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(batchBuy.getSendTime())); //默认此次计息的起息日为购买日期
	        if(CollectionUtils.isNotEmpty(receiveMoneys)){
	        	lastFinshDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(receiveMoneys.get(0).getPayFinshTime())); //上次结息日（此次计息的起息日）
	        }
	        payDays = DateUtil.getDiffeDay(today, lastFinshDate);
	    	Double needPayInterest = MoneyUtil.multiply(payDays,
	    			MoneyUtil.divide(receiveMoneys.get(0).getProductInterest(), 30d, 4).doubleValue()).doubleValue();
	    	detail.setInterest(MoneyUtil.defaultRound(needPayInterest).doubleValue());
	    	//总金额
	    	detail.setTotal(MoneyUtil.add(detail.getPrincipal(), detail.getInterest()).doubleValue());
	    	detail.setSendBatchId(sendBatchId);
	    	list.add(detail);
	    	//总金额累计
	    	sumTotal = MoneyUtil.add(sumTotal, detail.getTotal()).doubleValue();
	    	sumPrincipal = MoneyUtil.add(sumPrincipal, detail.getPrincipal()).doubleValue();
		}
		res.setSumTotal(sumTotal);
		res.setSumPrincipal(sumPrincipal);
		res.setDetailList(list);
		return res;
	}

	@Override
	public String doSysReturn(List<String> sendBatchIdList) {
		for (final String sendBatchId : sendBatchIdList) {
			//根据理财购买表，判断批次号是否正确，且未回款成功
	        BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
	        batchBuyExample.createCriteria().andSendBatchIdEqualTo(sendBatchId);
	        List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
	        if(batchBuys==null || batchBuys.size()<=0){
				return sendBatchId+"批次号有误，请重新输入！";
	        }
	        final BsBatchBuy batchBuy = batchBuys.get(0);
	        if(Constants.BATCHBUY_STATUS_RETURN_SUCCESS.equals(batchBuys.get(0).getStatus())){
				return sendBatchId+"批次号已成功，请重新输入！";
	        }
	        //计算利息，新增回款信息表
	        BsSysReceiveMoneyExample moneyExample = new BsSysReceiveMoneyExample();
	        moneyExample.createCriteria().andTypeEqualTo(Constants.RETURN_NOTICE_TYPE_INTEREST)
	        	.andProductOrderNoEqualTo(batchBuy.getSendBatchId()).andStatusEqualTo(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
	        moneyExample.setOrderByClause("pay_finsh_time DESC");
	        List<BsSysReceiveMoney> receiveMoneys = bsSysReceiveMoneyMapper.selectByExample(moneyExample);
	        final Date today = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
	        int days = 0;
	        Date lastFinshDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(batchBuy.getSendTime())); //默认此次计息的起息日为购买日期
	        if(CollectionUtils.isNotEmpty(receiveMoneys)){
	        	lastFinshDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(receiveMoneys.get(0).getPayFinshTime())); //上次结息日（此次计息的起息日）
	        }
	        days = DateUtil.getDiffeDay(today, lastFinshDate);
	    	Double needPayInterest = MoneyUtil.multiply(days,
	    			MoneyUtil.divide(receiveMoneys.get(0).getProductInterest(), 30d, 4).doubleValue()).doubleValue();
	    	final Double interest = MoneyUtil.defaultRound(needPayInterest).doubleValue();
	        //新增回款通知记录
	        BsSysReceiveMoneyExample receiveMoneyExample = new BsSysReceiveMoneyExample();
            receiveMoneyExample.createCriteria().andProductOrderNoEqualTo(sendBatchId);
            final List<BsSysReceiveMoney> sysReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(receiveMoneyExample);
            if(CollectionUtils.isEmpty(sysReceiveMoneys)){
				return sendBatchId+"原回款通知记录不正确！";
	        }
            final Double principal = batchBuy.getSendAmount();
            final int payDays = days;
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
            try {
            	transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	String channel = batchBuy.getPropertySymbol();
                        String channelName = ThirdSysChannelEnum.getEnumByCode(channel).getName();
                        log.info("========={【" + channelName+"】batchBuy.id = "+batchBuy.getId() + "提前赎回处理}开始=========");
                        
                    	//新增回款通知记录
                    	int returnTerm = sysReceiveMoneys.size()+1;
                        BsSysReceiveMoney receiveMoney = new BsSysReceiveMoney();
                        receiveMoney.setAmount(MoneyUtil.add(principal, interest).doubleValue());
                        receiveMoney.setCreateTime(new Date());
                        receiveMoney.setPayFinshTime(new Date());
                        receiveMoney.setPayOrderNo("");
                        receiveMoney.setPayReqTime(new Date());
                        receiveMoney.setProductAmount(principal);
                        receiveMoney.setProductCode(batchBuy.getProductCode());
                        receiveMoney.setProductInterest(interest);
                        receiveMoney.setProductOrderNo(sendBatchId);
                        receiveMoney.setProductReturnTerm(String.valueOf(returnTerm));
                        receiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_INIT);
                        receiveMoney.setType(Constants.RETURN_NOTICE_TYPE_LAST);
                        receiveMoney.setUpdateTime(new Date());
                        bsSysReceiveMoneyMapper.insertSelective(receiveMoney);
                        Integer batchId = batchBuy.getId();
                        //修改batchBuy表
                        BsBatchBuy batchBuyTemp = new BsBatchBuy();
                        batchBuyTemp.setId(batchId);
                        batchBuyTemp.setDafyPay19MpOrderNo("");
                        batchBuyTemp.setReceiveAmount(MoneyUtil.add(principal, interest).doubleValue());
                        batchBuyTemp.setReturnTime(new Date());
                        batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_RETURN_SUCCESS);
                        batchBuyTemp.setUpdateTime(new Date());
                        bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuyTemp);
                        BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
                        List<String> statuss = new ArrayList<String>();
                        statuss.add(Constants.RETURN_STATUS_NOT);
                        statuss.add(Constants.RETURN_STATUS_FAIL);
                        detailExample.createCriteria().andBatchIdEqualTo(batchId).andReturnStatusIn(statuss);
                        List<BsBatchBuyDetail> details = bsBatchBuyDetailMapper.selectByExample(detailExample);
                        log.info("========={批量客户回款}批次号["+batchBuy.getSendBatchId()+"]找到["+details.size()+"]笔理财产品待回款=========");
                        //计算用户月利息和系统月利息，入账
                        final double productTotalAmount = batchBuy.getAmount();//总本金
                    	//该批次用户产品总用户收益
                    	final BigDecimal userInterest = calcUserTotalInterestByDays(details, payDays);
                    	int allDays = DateUtil.getDiffeDay(today, DateUtil.parseDate(DateUtil.formatYYYYMMDD(batchBuy.getSendTime())));
                    			//payDays+30*(Integer.valueOf(req.getProductReturnTerm())-1);//总天数
                    	final BigDecimal userTotalInterest = calcUserTotalInterestByDays(details,allDays);
                    	//该批次总利息=总还款金额-总购买本金
                        final double sysMonthProductInterest =  interest;
                        G2BReqMsg_DafyPayment_SysReturnMoneyNotice req = new G2BReqMsg_DafyPayment_SysReturnMoneyNotice();
                        req.setMerchantId(batchBuy.getSendBatchId());
                        req.setPayOrderNo("");
                        req.setPayReqTime(new Date());
                        req.setPayFinshTime(new Date());
                        req.setAmount(MoneyUtil.add(principal, interest).doubleValue());
                        req.setProductOrderNo(sendBatchId);
                        req.setProductCode(batchBuy.getProductCode());
                        req.setProductAmount(principal);
                        req.setProductInterest(interest);
                        req.setChannel(channel);
                        req.setProductReturnTerm(String.valueOf(returnTerm));
                        
                        //系统记账流水表JSH 增加本次结算利息A，并冻结C），新增记账【系统还款户添加本金+C】
                        newReturnSysAccount(channelName,productTotalAmount,sysMonthProductInterest,userInterest,req,userTotalInterest,false);
                    	//回款通知表处理状态改为成功
                        BsSysReceiveMoneyExample updateReceiveMoneyExample = new BsSysReceiveMoneyExample();
                        updateReceiveMoneyExample.createCriteria().andProductOrderNoEqualTo(req.getProductOrderNo())
                                .andProductReturnTermEqualTo(req.getProductReturnTerm());
                        List<BsSysReceiveMoney> updateReceiveMoneys = bsSysReceiveMoneyMapper.selectByExample(updateReceiveMoneyExample);
                        BsSysReceiveMoney updateReceiveMoney = new BsSysReceiveMoney();
                        updateReceiveMoney.setId(updateReceiveMoneys.get(0).getId());
                        updateReceiveMoney.setStatus(Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS);
                        updateReceiveMoney.setUpdateTime(new Date());
                        bsSysReceiveMoneyMapper.updateByPrimaryKeySelective(updateReceiveMoney);

                        log.info("========={"+ channelName + "系统回款通知处理}循环BsBatchBuyDetail列表，处理用户回款=========");
                        //根据回款计划表，进行单笔客户回款
                        BsBatchBuyDetailExample buyDetailExample = new BsBatchBuyDetailExample();
                        buyDetailExample.createCriteria().andBatchIdEqualTo(batchBuy.getId()).andReturnStatusEqualTo(Constants.RETURN_STATUS_NOT);
                        List<BsBatchBuyDetail> buyDetails = bsBatchBuyDetailMapper.selectByExample(buyDetailExample);
                        if(buyDetails!=null && buyDetails.size()>0){
                        	final Date batchBuyDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(batchBuy.getSendTime()));
                        	//returnDetails最后一条
                        	final Integer lastId = buyDetails.get(buyDetails.size()-1).getId();
                        	final List<Double> sumInterest = new ArrayList<Double>();
                        	sumInterest.add(0d);
                        	for (final BsBatchBuyDetail batchBuyDetail : buyDetails) {
                        		Integer userId = batchBuyDetail.getUserId();
                                Integer subAccountId = batchBuyDetail.getSubAccountId();
                                //产品户信息
                                BsSubAccountVO productAccount = subAccountService.findMyInvestByUserIdAndId(userId, subAccountId);
                                //产品本金
                                BigDecimal principal = new BigDecimal(String.valueOf(productAccount.getBalance()));
                                //产品购买日到今日（当日不计息）
                                Integer days = DateUtil.getDiffeDay(today, batchBuyDate);
                                Double interest = 0d;
                                if(batchBuyDetail.getId() == lastId){
                                	//最后一期利息=C-（C1+...+Cn）
                                	interest = MoneyUtil.subtract(userTotalInterest.doubleValue(), sumInterest.get(0)).doubleValue();
                                }else{
                                	//产品收益(四舍五入)=本金*利率*天数/365
                                	interest = CalculatorUtil.calculate("(a*a*a)/a", principal.doubleValue(), productAccount.getProductRate(), days.doubleValue(), 36500d);
                                	Double sumInterestTemp = MoneyUtil.add(sumInterest.get(0),interest).doubleValue();
                                	sumInterest.clear();
                                	sumInterest.add(sumInterestTemp);
                                }
                                
                                //修改BsBatchBuyDetail状态
                        		BsBatchBuyDetail buyDetail = new BsBatchBuyDetail();
                                buyDetail.setId(batchBuyDetail.getId());
                                buyDetail.setReturnStatus(Constants.RETURN_STATUS_SUCCESS);
                                buyDetail.setUpdateTime(new Date());
                                bsBatchBuyDetailMapper.updateByPrimaryKeySelective(buyDetail);  
                                //lnRepaySchedule修改，LnFinanceRepaySchedule、LnDepositionRepaySchedule及Detail新增
                                addLnSchedule(subAccountId,principal.doubleValue(),interest.doubleValue());
                                //废弃原理财户REG，新建站岗户AUTH_YUN/AUTH_7，开户金额为原理财户开户金额，可用余额为0，新增补差户DIFF，修改原【债权关系表】 数据中的sub_account_id
                                openNewSubAccount(req.getChannel(),subAccountId,userId,productAccount.getTerm());
                        	}
                        }
                        
                    }
            	});
            	
                
			} catch (Exception e) {
				e.printStackTrace();
			} finally{
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_SYS_RECEIVEMONEY.getKey());
			}
            
		}
		return null;
	}

}
