package com.pinting.business.accounting.loan.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.loan.enums.*;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.service.SysDailyAccountService;
import com.pinting.business.accounting.loan.service.SysDailyBizService;
import com.pinting.business.accounting.loan.util.UncompressUtil;
import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BAOFOOTransTypeEnum;
import com.pinting.business.enums.BaoFooCheckBalanceTypeEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.TransPartnerRevenueInfo;
import com.pinting.business.model.vo.BaoFooAccountingDetailVO;
import com.pinting.business.model.vo.BsHfBankSysAccountTransferVo;
import com.pinting.business.model.vo.HFBalanceDetailVO;
import com.pinting.business.model.vo.LnRepayCheckAccountVO;
import com.pinting.business.model.vo.ZANRevenueModelVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.manage.BsHfBankService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.CalculatorUtil;
import com.pinting.business.util.Constants;
import com.pinting.business.util.TransferEnv;
import com.pinting.business.util.TransferEnvUtil;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_DownLoadFile;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_QueryAccountLeftAmountInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_QueryAccountLeftAmountInfo;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.pay19.model.AccountDetail;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.util.*;

/**
 * Created by 剑钊
 *
 * @2016/9/7 11:02.
 */
@Service
public class SysDailyBizServiceImpl implements SysDailyBizService {


    private Logger log = LoggerFactory.getLogger(SysDailyBizServiceImpl.class);
    @Autowired
    private LnRepayMapper repayMapper;
    @Autowired
    private SysDailyAccountService dailyAccountService;
    @Autowired
    private AlgorithmService algorithmService;
    @Autowired
    private LnRepayScheduleMapper repayScheduleMapper;
    @Autowired
    private Bs19payCheckRecordMapper bs19payCheckRecordMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private CheckAccountService checkAccountService;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private BsRevenueTransRecordMapper revenueTransRecordMapper;
    @Autowired
    private BsSysReceiveMoneyMapper receiveMoneyMapper;
    @Autowired
    private BsRevenueTransDetailMapper revenueTransDetailMapper;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private BsSysBalanceDailySnapMapper bsSysBalanceDailySnapMapper;
    @Autowired
    private BsHfBankService bsHfBankService;
    @Autowired
    private BsSysSubAccountMapper bsSysSubAccountMapper;
    @Autowired
    private BsPaymentChannelMapper  paymentChannelMapper;
    @Autowired
    private LnCompensateMapper lnCompensateMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;
    @Autowired
    private LnRepayMapper lnRepayMapper;
    @Autowired
    private BsSysDailyCheckGachaMapper bsSysDailyCheckGachaMapper;
    @Autowired
    private LnRepeatRepayRecordMapper lnRepeatRepayRecordMapper;
    @Autowired 
    private LnRepayScheduleMapper lnRepayScheduleMapper;
    
    @Override
    public void checkAndGenerateRevenuePlan() throws ParseException {

        //获得上次结算日期
        Date lastSettleDate = revenueTransRecordMapper.selectLastSettleDate();
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -1);
        //获取上次结算日期开始到昨日结束所有的 还款信息
        List<LnRepay> repayList = repayMapper.selectYesRepayList(PartnerEnum.ZAN.getCode(),
                DateUtil.addDays(lastSettleDate, 1),DateUtil.getDateEnd(calendar.getTime()),LoanStatus.REPAY_STATUS_REPAIED.getCode());

        if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0) {

            //记录营收转账数据
            BsRevenueTransRecord record = new BsRevenueTransRecord();
            record.setAmount(0d);
            record.setOrderNo(Util.generateOrderNo4BaoFoo(8));
            record.setCreateTime(new Date());
            record.setPayeeCode(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
            record.setPayerCode(Constants.PRODUCT_PROPERTY_SYMBOL_BGW);
            record.setStatus(Constants.ORDER_TRANS_CODE_INIT);
            record.setUpdateTime(new Date());
            record.setSettleDate(DateUtil.parseDate(DateUtil.formatYYYYMMDD(calendar.getTime())));
            revenueTransRecordMapper.insertSelective(record);

            Double amount = 0d;
            for (LnRepay lnRepay : repayList) {

                LnRepaySchedule repaySchedule = repayScheduleMapper.selectByPrimaryKey(lnRepay.getRepayPlanId());
                //计算营收
                ZANRevenueModelVO vo = algorithmService.calZANRevenue(lnRepay.getLoanId(), repaySchedule.getSerialId());
                log.info(JSONObject.fromObject(vo).toString());

                Double bgwServiceFeeTemp = 0d;
                Double revenueAmountTemp = 0d;
                //逾期还款时 币港湾服务费为0，归为赞分期营收
                if(LoanStatus.REPAY_SCHEDULE_STATUS_LATE_REPAIED.getCode().equals(repaySchedule.getStatus())){
                    bgwServiceFeeTemp = 0d;
                    revenueAmountTemp = MoneyUtil.add(vo.getZANRevenue(), vo.getBGWServiceFee()).doubleValue();
                }
                //正常还款时 币港湾服务费不为0
                else {
                    bgwServiceFeeTemp = vo.getBGWServiceFee();
                    revenueAmountTemp = vo.getZANRevenue();
                }

                //记录每一期还款的营收明细
                BsRevenueTransDetail detail=new BsRevenueTransDetail();
                detail.setPartnerCode(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
                detail.setTransType(Constants.REVENUE_TYPE_REPAY_INCOME);
                detail.setCommissionFee(vo.getServiceFee());
                detail.setCreateTime(new Date());
                detail.setDeposit(vo.getZANDeposit());
                detail.setRepayAmount(vo.getTotalRepay());
                detail.setRepayId(lnRepay.getId());
                detail.setRevenueTransId(record.getId());
                detail.setUpdateTime(new Date());
                detail.setOtherFee(0d);
                detail.setLoanId(lnRepay.getLoanId());
                detail.setRepaySerial(repaySchedule.getSerialId());
                detail.setBgwServiceFee(bgwServiceFeeTemp);
                detail.setRevenueAmount(revenueAmountTemp);

                revenueTransDetailMapper.insertSelective(detail);

                amount=MoneyUtil.add(amount, revenueAmountTemp).doubleValue();
            }
            
            Double overdueRevenueAmount = revenueTransDetailMapper.sumOverdueRevenueAmount(DateUtil.addDays(lastSettleDate, 1), DateUtil.getDateEnd(calendar.getTime()));
            amount=MoneyUtil.add(amount, overdueRevenueAmount).doubleValue();
            Double deductAmount = revenueTransDetailMapper.sumDeductAmount(DateUtil.addDays(lastSettleDate, 1), DateUtil.getDateEnd(calendar.getTime()));
            Double diffAmount = MoneyUtil.defaultRound(MoneyUtil.add(amount, deductAmount)).doubleValue();
            record.setAmount(MoneyUtil.defaultRound(MoneyUtil.add(amount, deductAmount)).doubleValue());
            revenueTransRecordMapper.updateByPrimaryKeySelective(record);

            BsSysSubAccount tempAct = bsSysSubAccountMapper.selectByCode(Constants.SYS_ACCOUNT_THD_REVENUE_ZAN);
            if(tempAct == null){
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",赞分期系统营收户不存在");
            }
            log.info(">>>赞分期全部营收：" + amount +";赞分期逾期垫付：" + deductAmount + ";赞分期营收应转账金额："+ diffAmount + ";" + tempAct.getAvailableBalance());
            //金额大于0 && 小于系统户金额
            if(diffAmount > 0 && diffAmount <= tempAct.getAvailableBalance()){
                //账户转账
                transDailyPartnerRevenue(record);
            }
        }
    }

    @Override
    public void transDailyPartnerRevenue(BsRevenueTransRecord record) throws ParseException {

        //记录bs_pay_orders表中
        BsPayOrders order = new BsPayOrders();
        Date now = new Date();
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        order.setAmount(record.getAmount());
        order.setChannel(Constants.CHANNEL_BAOFOO);
        order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
        order.setCreateTime(now);
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setOrderNo(record.getOrderNo());
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setTransType(Constants.TRANS_SYS_PARTNER_REVENUE);
        order.setUpdateTime(now);

        //读取转账账户
        TransferEnv transferEnv = TransferEnvUtil.transferEnvMap.get(record.getPayeeCode());
        //订单用userName来记录转账给哪方借贷公司
        order.setUserName(transferEnv.getTransTarget());

        bsPayOrdersMapper.insertSelective(order);

        log.info("=========================订单表插入数据：" + JSONObject.fromObject(order).toString());

        B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans req = new B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans();
        req.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
        req.setTrans_no(record.getOrderNo());
        req.setTrans_money(record.getAmount().toString());
        B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = new B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans();
        try {
            res = baoFooTransportService.pay4OnlineTrans(req);
        } catch (Exception e) {
            e.printStackTrace();
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
        }

        //转账结果处理
        TransPartnerRevenueInfo transPartnerRevenueInfo = new TransPartnerRevenueInfo();
        transPartnerRevenueInfo.setFinishTime(new Date());
        if (res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
            transPartnerRevenueInfo.setStatus(res.getState());
        }
        transPartnerRevenueInfo.setAmount(record.getAmount());
        transPartnerRevenueInfo.setOrderNo(record.getOrderNo());
        transPartnerRevenueInfo.setRecordId(record.getId());
        transPartnerRevenueInfo.setReturnCode(res.getResCode());
        transPartnerRevenueInfo.setReturnMsg(res.getResMsg());

        notifyTransDailyPartnerRevenue(transPartnerRevenueInfo);
    }


    /**
     * 合作方营收日终结算转账结果处理
     */
    @Override
    public void notifyTransDailyPartnerRevenue(TransPartnerRevenueInfo transPartnerRevenueInfo) {

        BsPayOrdersExample ordersExample = new BsPayOrdersExample();
        ordersExample.createCriteria().andOrderNoEqualTo(transPartnerRevenueInfo.getOrderNo());
        List<BsPayOrders> bsPayOrdersList = bsPayOrdersMapper.selectByExample(ordersExample);

        BsPayOrders bsPayOrders = new BsPayOrders();
        bsPayOrders.setId(bsPayOrdersList.get(0).getId());
        bsPayOrders.setUpdateTime(transPartnerRevenueInfo.getFinishTime());
        bsPayOrders.setReturnMsg(transPartnerRevenueInfo.getReturnMsg());
        bsPayOrders.setReturnCode(transPartnerRevenueInfo.getReturnCode());

        if (transPartnerRevenueInfo.getRecordId() == null) {
            BsRevenueTransRecordExample revenueTransRecordExample = new BsRevenueTransRecordExample();
            revenueTransRecordExample.createCriteria().andOrderNoEqualTo(transPartnerRevenueInfo.getOrderNo());
            transPartnerRevenueInfo.setRecordId(revenueTransRecordMapper.selectByExample(revenueTransRecordExample).get(0).getId());
        }

        BsRevenueTransRecord record = revenueTransRecordMapper.selectByPrimaryKey(transPartnerRevenueInfo.getRecordId());
        BsRevenueTransRecord temp = new BsRevenueTransRecord();
        temp.setId(transPartnerRevenueInfo.getRecordId());
        temp.setUpdateTime(transPartnerRevenueInfo.getFinishTime());

        //成功记账并更新订单和转账记录状态
        log.info(">>>合作方营收日终结算转账结果处理入参："+ JSONObject.fromObject(transPartnerRevenueInfo).toString() + "<<<");
        if (transPartnerRevenueInfo.getReturnCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {

            if (transPartnerRevenueInfo.getStatus().equals("1")) {
                //修改订单表和转账记录表为成功
                bsPayOrders.setStatus(Constants.ORDER_STATUS_SUCCESS);
                temp.setStatus(Constants.ORDER_TRANS_CODE_SUCCESS);
                temp.setFinishTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(bsPayOrders);
                revenueTransRecordMapper.updateByPrimaryKeySelective(temp);
                //成功时记账
                BaseAccount baseAccount = new BaseAccount();
                baseAccount.setPartner(PartnerEnum.getEnumByCode(record.getPayeeCode()));
                baseAccount.setAmount(record.getAmount());
                dailyAccountService.chargeDailyRevenueSettle(baseAccount);
                return;
            } else if (transPartnerRevenueInfo.getStatus().equals("-1")) {
                bsPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                temp.setStatus(Constants.ORDER_TRANS_CODE_FAIL);
                temp.setFinishTime(new Date());
            } else if (transPartnerRevenueInfo.getStatus().equals("0")) {
                bsPayOrders.setStatus(Constants.ORDER_STATUS_PAYING);
                temp.setStatus(Constants.ORDER_TRANS_CODE_PROCCESS);
            }
            bsPayOrdersMapper.updateByPrimaryKeySelective(bsPayOrders);
            revenueTransRecordMapper.updateByPrimaryKeySelective(temp);
        }
        //失败更新状态
        else {
            bsPayOrders.setStatus(Constants.ORDER_STATUS_PAYING);
            temp.setStatus(Constants.ORDER_TRANS_CODE_PROCCESS);

            bsPayOrdersMapper.updateByPrimaryKeySelective(bsPayOrders);
            revenueTransRecordMapper.updateByPrimaryKeySelective(temp);
        }

    }

    @Override
    public void checkBaoFooDailyAccounting(BsPaymentChannel bsPaymentChannel) {

        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}执行宝付对账文件解析开始====================");
        //解压文件
        UncompressUtil.uncompress(FilePath.DOWN_FILE_FI_PATH.getCode() + DateUtil.formatYYYYMMDD(new Date()) +"_"+bsPaymentChannel.getMerchantNo()+ ".zip", FilePath.UNCOMPRESS_FILE_PATH.getCode(), Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI,bsPaymentChannel.getMerchantNo());
        UncompressUtil.uncompress(FilePath.DOWN_FILE_FO_PATH.getCode() + DateUtil.formatYYYYMMDD(new Date()) +"_"+bsPaymentChannel.getMerchantNo()+ ".zip", FilePath.UNCOMPRESS_FILE_PATH.getCode(), Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO,bsPaymentChannel.getMerchantNo());
        //解析宝付对账文件
        List<BaoFooAccountingDetailVO> voList = new ArrayList<>();
        List<BaoFooAccountingDetailVO> fiVoList = analysisAccountingFile(FilePath.UNCOMPRESS_FILE_PATH.getCode() + DateUtil.formatYYYYMMDD(new Date()) + "_" + Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI +"_"+bsPaymentChannel.getMerchantNo()+ ".txt", Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI);

        voList.addAll(fiVoList);
        voList.addAll(analysisAccountingFile(FilePath.UNCOMPRESS_FILE_PATH.getCode() +DateUtil.formatYYYYMMDD(new Date()) + "_" + Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO +"_"+bsPaymentChannel.getMerchantNo()+ ".txt", Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO));
        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}执行宝付对账文件解析完成====================");

        if (CollectionUtils.isNotEmpty(voList) && voList.size() > 0) {

            //插入对账记录表
            for (BaoFooAccountingDetailVO detail : voList) {
                Bs19payCheckRecord record = new Bs19payCheckRecord();
                record.setCommission(MoneyUtil.defaultRound(detail.getServiceFee()).doubleValue());
                record.setCurrencyType("RMB");
//                record.setOrderDesc(detail.getTxnType());
//                record.setOrderFinishTime(detail.getFinishTime());
                record.setOrderNo(detail.getPayOrderNo());
                record.setOrderSettleTime(DateUtil.parseDate(detail.getLiquidationDate()));
                record.setOrderSrc(detail.getTxnType());
                record.setOrderSubmitTime(DateUtil.parseDateTime(detail.getCreateTime()));
                record.setPay19OrderJnl(detail.getBaofooOrderNo());
                record.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
//                record.setTradeCompanyOrderTime(detail.getReqTime());
                record.setTranAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                record.setTranType(AmountTransType.getEnumByCode(detail.getTxnType())!=null?AmountTransType.getEnumByCode(detail.getTxnType()).getDescription():null);
                record.setTradeCompanyNote(bsPaymentChannel.getMerchantNo());
                record.setRefundOrderNo(detail.getRefundOrderNo());
                record.setRefundCreateTime(detail.getRefundCreateTime() != null ? DateUtil.parseDateTime(detail.getRefundCreateTime()) : null);
                bs19payCheckRecordMapper.insertSelective(record);
            }
        } else {
            //对账文件无明细记录（此种情况可能有问题，需要告警确认）
            log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}对账失败：文件无明细记录，可能下载文件异常，需要您的确认，请检查====================");
            //告警
            specialJnlService.warn4Fail(null, "定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}对账失败：无文件或文件无明细记录，可能下载文件异常，需要您的确认，请检查", null, "宝付对账文件无文件或文件无明细记录", true);
            return;

        }

        //宝付间转账列表入账列表
        List<BaoFooAccountingDetailVO> transFiList = new ArrayList<>();
        //筛选宝付间转账入账信息
        for (BaoFooAccountingDetailVO vo : fiVoList) {
            if (vo.getTxnType().equals("00104")) {
                transFiList.add(vo);
            }
        }

        voList.removeAll(transFiList);

        //查询对账日期范围内本地订单
        Date checkDate = DateUtil.addDays(new Date(), -1);//对账日期
        Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
        Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        
        //理财端订单
        List<BsPayOrders> bsPayOrdersList = bsPayOrdersMapper.baoFooCheckAccountOrders();
        //借款端订单
        List<LnPayOrders> lnPayOrdersList = lnPayOrdersMapper.baoFooCheckAccountOrders();
        //对账（除宝付间转账入账）
        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}执行宝付对账明细开始====================");
        checkAccount(bsPayOrdersList, lnPayOrdersList, voList,bsPaymentChannel.getMerchantNo());
        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}执行宝付对账明细结束====================");
        //宝付间转账入账对账
        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}执行宝付间对账明细开始====================");
        checkTransAccount(transFiList,bsPaymentChannel.getMerchantNo());
        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}执行宝付间对账明细结束====================");        
     
    }
    
    
    
    @Override
    public void checkBaoFooDailyAccountingAssist(BsPaymentChannel bsPaymentChannel) {

        log.info("==================定时任务{宝付对账(辅助通道"+bsPaymentChannel.getMerchantNo()+")}执行宝付对账文件解析开始====================");
        //解压文件
        UncompressUtil.uncompress(FilePath.DOWN_FILE_FI_PATH.getCode() + DateUtil.formatYYYYMMDD(new Date()) + "_"+bsPaymentChannel.getMerchantNo()+".zip", FilePath.UNCOMPRESS_FILE_PATH.getCode(), Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI,bsPaymentChannel.getMerchantNo());
        UncompressUtil.uncompress(FilePath.DOWN_FILE_FO_PATH.getCode() + DateUtil.formatYYYYMMDD(new Date()) + "_"+bsPaymentChannel.getMerchantNo()+".zip", FilePath.UNCOMPRESS_FILE_PATH.getCode(), Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO,bsPaymentChannel.getMerchantNo());
        //解析宝付对账文件
        List<BaoFooAccountingDetailVO> voList = new ArrayList<>();
        List<BaoFooAccountingDetailVO> fiVoList = analysisAccountingFile(FilePath.UNCOMPRESS_FILE_PATH.getCode() + DateUtil.formatYYYYMMDD(new Date()) + "_" + Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI + "_"+bsPaymentChannel.getMerchantNo()+".txt", Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI);

        voList.addAll(fiVoList);
        voList.addAll(analysisAccountingFile(FilePath.UNCOMPRESS_FILE_PATH.getCode() +DateUtil.formatYYYYMMDD(new Date()) + "_" + Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO + "_"+bsPaymentChannel.getMerchantNo()+ ".txt", Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO));
        log.info("==================定时任务{宝付对账(辅助通道"+bsPaymentChannel.getMerchantNo()+")}执行宝付对账文件解析完成====================");

        if (CollectionUtils.isNotEmpty(voList) && voList.size() > 0) {

            //插入对账记录表
            for (BaoFooAccountingDetailVO detail : voList) {
                Bs19payCheckRecord record = new Bs19payCheckRecord();
                record.setCommission(MoneyUtil.defaultRound(detail.getServiceFee()).doubleValue());
                record.setCurrencyType("RMB");
//                record.setOrderDesc(detail.getTxnType());
//                record.setOrderFinishTime(detail.getFinishTime());
                record.setOrderNo(detail.getPayOrderNo());
                record.setOrderSettleTime(DateUtil.parseDate(detail.getLiquidationDate()));
                record.setOrderSrc(detail.getTxnType());
                record.setOrderSubmitTime(DateUtil.parseDateTime(detail.getCreateTime()));
                record.setPay19OrderJnl(detail.getBaofooOrderNo());
                record.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
//                record.setTradeCompanyOrderTime(detail.getReqTime());
                record.setTranAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                record.setTranType(AmountTransType.getEnumByCode(detail.getTxnType())!=null?AmountTransType.getEnumByCode(detail.getTxnType()).getDescription():null);
                record.setTradeCompanyNote(bsPaymentChannel.getMerchantNo());
                record.setRefundOrderNo(detail.getRefundOrderNo());
                record.setRefundCreateTime(detail.getRefundCreateTime() != null ? DateUtil.parseDateTime(detail.getRefundCreateTime()) : null);
                bs19payCheckRecordMapper.insertSelective(record);
            }
        } else {
            //对账文件无明细记录（此种情况可能有问题，需要告警确认）
            log.info("==================定时任务{宝付对账(辅助通道"+bsPaymentChannel.getMerchantNo()+")}对账失败：文件无明细记录，可能下载文件异常，需要您的确认，请检查====================");
            //告警
            specialJnlService.warn4Fail(null, "定时任务{宝付对账(辅助通道"+bsPaymentChannel.getMerchantNo()+")}对账失败：无文件或文件无明细记录，可能下载文件异常，需要您的确认，请检查", null, "宝付对账文件无文件或文件无明细记录", true);
            return;

        }

        //宝付间转账列表入账列表
        List<BaoFooAccountingDetailVO> transFiList = new ArrayList<>();
        //筛选宝付间转账入账信息
        for (BaoFooAccountingDetailVO vo : fiVoList) {
            if (vo.getTxnType().equals("00104")) {
                transFiList.add(vo);
            }
        }

        voList.removeAll(transFiList);

        //查询对账日期范围内本地订单
        Date checkDate = DateUtil.addDays(new Date(), -1);//对账日期
        Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
        Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        

        
        //理财端订单
        BsPayOrdersExample orderExample = new BsPayOrdersExample();
        orderExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate)
                .andChannelEqualTo(Constants.CHANNEL_BAOFOO).andPaymentChannelIdEqualTo(bsPaymentChannel.getId());
        List<BsPayOrders> bsPayOrdersList = bsPayOrdersMapper.selectByExample(orderExample);
        //借款端订单
        LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
        payOrdersExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate)
                .andChannelEqualTo(Constants.CHANNEL_BAOFOO).andPaymentChannelIdEqualTo(bsPaymentChannel.getId());
        List<LnPayOrders> lnPayOrdersList = lnPayOrdersMapper.selectByExample(payOrdersExample);
        //对账（辅助通道无宝付间转账入账）
        log.info("==================定时任务{宝付对账(辅助通道"+bsPaymentChannel.getMerchantNo()+")}执行宝付对账明细开始====================");
        checkAccountAssist(bsPayOrdersList, lnPayOrdersList, voList,bsPaymentChannel.getMerchantNo());
        log.info("==================定时任务{宝付对账(辅助通道"+bsPaymentChannel.getMerchantNo()+")}执行宝付对账明细结束====================");
       
        
    }

    public void checkZanBaoFooDailyAccounting() {

        log.info("==================定时任务{宝付对账}执行赞分期宝付对账文件解析开始====================");
        //解压文件
        UncompressUtil.uncompress(FilePath.ZAN_DOWN_FILE_FO_PATH.getCode() + DateUtil.formatYYYYMMDD(new Date()) + ".zip", FilePath.ZAN_UNCOMPRESS_FILE_PATH.getCode(), Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO,null);
        //解析宝付对账文件
        List<BaoFooAccountingDetailVO> voList = new ArrayList<>();
        voList.addAll(analysisAccountingFile(FilePath.ZAN_UNCOMPRESS_FILE_PATH.getCode() +DateUtil.formatYYYYMMDD(new Date()) + "_" + Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO + ".txt", Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO));
        log.info("==================定时任务{宝付对账}执行赞分期宝付对账文件解析完成====================");

        if (CollectionUtils.isNotEmpty(voList) && voList.size() > 0) {

            //插入对账记录表
            for (BaoFooAccountingDetailVO detail : voList) {
                Bs19payCheckRecord record = new Bs19payCheckRecord();
                record.setCommission(MoneyUtil.defaultRound(detail.getServiceFee()).doubleValue());
                record.setCurrencyType("RMB");
                record.setOrderNo(detail.getPayOrderNo());
                record.setOrderSettleTime(DateUtil.parseDate(detail.getLiquidationDate()));
                record.setOrderSrc(detail.getTxnType());
                record.setOrderSubmitTime(DateUtil.parseDateTime(detail.getCreateTime()));
                record.setPay19OrderJnl(detail.getBaofooOrderNo());
                record.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                record.setTranAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                record.setTranType(AmountTransType.getEnumByCode(detail.getTxnType())!=null?AmountTransType.getEnumByCode(detail.getTxnType()).getDescription():null);
                record.setRefundOrderNo(detail.getRefundOrderNo());
                record.setRefundCreateTime(detail.getRefundCreateTime() != null ? DateUtil.parseDateTime(detail.getRefundCreateTime()) : null);
                bs19payCheckRecordMapper.insertSelective(record);
            }
        } else {
            //对账文件无明细记录（此种情况可能有问题，需要告警确认）
            log.info("==================定时任务{宝付对账}赞分期营销对账失败：文件无明细记录，可能下载文件异常，需要您的确认，请检查====================");
            //告警
            specialJnlService.warn4FailNoSMS(null, "定时任务{宝付对账}赞分期营销对账失败：无文件或文件无明细记录，可能下载文件异常，需要您的确认，请检查", null, "赞分期营销宝付对账文件无文件或文件无明细记录");
            return;

        }

        //查询对账日期范围内营销代付订单
        Date checkDate = DateUtil.addDays(new Date(), -1);//对账日期
        Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
        Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
        LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
        payOrdersExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate)
                .andChannelEqualTo(Constants.CHANNEL_BAOFOO).andTransTypeEqualTo(LoanStatus.TRANS_TYPE_MARKET.getCode());
        List<LnPayOrders> lnPayOrdersList = lnPayOrdersMapper.selectByExample(payOrdersExample);

        checkZanAccount(voList,lnPayOrdersList);
    }


    @Override
    public void downloadCheckAccountFile(BsPaymentChannel bsPaymentChannel) {

        //下载收单文件
        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}获取宝付对账文件开始====================");
        B2GReqMsg_BaoFooQuickPay_DownLoadFile req = new B2GReqMsg_BaoFooQuickPay_DownLoadFile();
        req.setMerchantNo(bsPaymentChannel.getMerchantNo());
        req.setDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));//对账日期
        req.setType(Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI);
        baoFooTransportService.downLoadFile(req);

        //下载出款文件
        req.setType(Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO);
        baoFooTransportService.downLoadFile(req);
        log.info("==================定时任务{宝付对账"+bsPaymentChannel.getMerchantNo()+"}获取宝付对账文件完成====================");
       
        //对账
        checkBaoFooDailyAccounting(bsPaymentChannel);
        
        //宝付对账完成后，增加每块业务的汇总  
        //generateMainCheckGacha(bsPaymentChannel.getMerchantNo());
    }
    
    
    @Override
    public void downloadCheckAccountFileAssist(BsPaymentChannel bsPaymentChannel) {

        //下载收单文件
        log.info("==================定时任务{宝付(辅助通道)对账"+bsPaymentChannel.getMerchantNo()+"}获取宝付对账文件开始====================");
        B2GReqMsg_BaoFooQuickPay_DownLoadFile req = new B2GReqMsg_BaoFooQuickPay_DownLoadFile();
        req.setPartner(Constants.BGW_BAOFOO_ASSIST);
        req.setMerchantNo(bsPaymentChannel.getMerchantNo());
        req.setDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));//对账日期
        req.setType(Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FI);
        baoFooTransportService.downLoadFile(req);

        //下载出款文件
        req.setType(Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO);
        baoFooTransportService.downLoadFile(req);
        log.info("==================定时任务{宝付(辅助通道)对账"+bsPaymentChannel.getMerchantNo()+"}获取宝付对账文件完成====================");
       
        //对账
        checkBaoFooDailyAccountingAssist(bsPaymentChannel);
        
        //宝付对账完成后，增加每块业务的汇总  
        //generateAssistCheckGacha(bsPaymentChannel.getMerchantNo(), bsPaymentChannel.getId());
    }


    @Override
    public void downLoadZanCheckAccountFile() {

        log.info("==================定时任务{宝付对账}获取赞分期宝付对账文件开始====================");

        //下载出款文件（暂时只做营销代付对账）
        B2GReqMsg_BaoFooQuickPay_DownLoadFile req = new B2GReqMsg_BaoFooQuickPay_DownLoadFile();
        req.setDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));//对账日期
        req.setType(Constants.BAOFOO_CHECK_ACCOUNT_TYPE_FO);
        req.setPartner(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN);
        baoFooTransportService.downLoadFile(req);
        log.info("==================定时任务{宝付对账}获取赞分期宝付对账文件完成====================");

        checkZanBaoFooDailyAccounting();
    }

    @Override
    public void advanceAutoAdjustAcc(BsLiquidationRecord record) {

        //获取上次恒丰自有子账户余额快照A0
        BsSysBalanceDailySnapExample example = new BsSysBalanceDailySnapExample();
        example.createCriteria().andAccountTypeEqualTo(Constants.SYS_SNAP_ACC_TYPE_HFBANK)
                .andCodeEqualTo(HFAccountTypeEnum.HF_ACC_TYPE_JSH.getCode()).andSnapDateEqualTo(new Date());
        List<BsSysBalanceDailySnap> snaps = bsSysBalanceDailySnapMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(snaps)){
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,",恒丰账户余额快照:" + HFAccountTypeEnum.HF_ACC_TYPE_JSH.getDescription()+"不存在");
        }
        BsSysBalanceDailySnap snap = snaps.get(0);
        //当前恒丰自有子账户余额A - A0
        Double jshDiff = MoneyUtil.subtract(record.getHfJshBalance(),snap.getBalance()).doubleValue();

        //A-A0+HB,HB表示日终到清算完成这段时间使用的红包金额
        jshDiff = MoneyUtil.add(jshDiff,record.getUsedRedAmount()).doubleValue();
        //BX表示日终到清算这段时间平台转个人的补息金额
        Date now = new Date();
        String beginTime = DateUtil.formatYYYYMMDD(now)+" 00:00:00";
        String endTime = DateUtil.format(now);
        Double BX = bsPayOrdersMapper.sumBalanceByTransType(Constants.TRANS_DEP_FILL_INTEREST, 
        		beginTime, endTime);
        
        //A-A0+HB+BX
        jshDiff = MoneyUtil.add(jshDiff,BX).doubleValue();
        
        //查询垫付金现金子账户余额 D2
        B2GReqMsg_HFBank_QueryAccountLeftAmountInfo leftAmountReq = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
        leftAmountReq.setAcct_type(HFAccountTypeEnum.HF_ACC_TYPE_HF_ADVANCE.getHfcode());
        leftAmountReq.setAccount(Constants.HF_REQ_PLATFORM);//账户编号-01-平台
        B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(leftAmountReq);
        Double advanceTrans = 0d;
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && StringUtil.isNotBlank(res.getData())) {
        	HFBalanceDetailVO vo = JSON.parseObject(res.getData(),HFBalanceDetailVO.class);
        	advanceTrans = Double.valueOf(vo.getBalance());
		}else {
			log.error("==========【系统余额快照】恒丰账户余额查询：垫付金现金子账户余额,失败："+res.getResMsg()+"============================");
		}
        
        //查询币港湾系统子账户垫付金账户余额
        BsSysSubAccount advanceAct = bsSysSubAccountMapper.selectByCode(Constants.SYS_ACCOUNT_DEP_ADVANCE);
        
        Double k_D2 = MoneyUtil.subtract(advanceAct.getBalance(), advanceTrans).doubleValue();
        log.info("==========【垫付金调账】A-A0+HB+BX=K-D2:"+jshDiff+"=?"+advanceAct.getBalance()+"-"+advanceTrans+"===================");
        //A-A0+HB+BX和K-D2对比
        if(jshDiff.compareTo(k_D2) == 0){
            if(k_D2 > 0){
                //相等则自有子账户到垫付金子账户，金额D2
                BsHfBankSysAccountTransferVo sysAccountTransferVo = new BsHfBankSysAccountTransferVo();
                sysAccountTransferVo.setAmount(k_D2);
                sysAccountTransferVo.setDestAccount(Constants.SYS_ACCOUNT_DEP_ADVANCE);
                sysAccountTransferVo.setSourceAccount(Constants.SYS_ACCOUNT_DEP_JSH);
                sysAccountTransferVo.setNote("存管自有子账户到存管垫付金现金账户");
                try {
                    String resCode = bsHfBankService.sysAccountTransferNoCharge(sysAccountTransferVo);
                    if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
                        log.info("恒丰系统划账成功");
                    }else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
                        log.info("恒丰系统划账失败");
                    }else {
                        log.info("恒丰系统划账账户余额不足");
                    }
                }catch (Exception e) {
                    log.info("恒丰系统划账失败的原因：{}",e.getMessage());
                }
            }
        }else{
            //告警
            specialJnlService.warn4Fail(null, "恒丰自有子账户余额差与恒丰垫付金在途账户不相等", null, "垫付金自动调账失败", true);
        }

    }


    /**
     * 解析宝付对账文件
     *
     * @param filePath 对账文件路径
     * @return
     */
    private List<BaoFooAccountingDetailVO> analysisAccountingFile(String filePath, String type) {

        List<BaoFooAccountingDetailVO> voList = new ArrayList<>();

        //对账文件地址
        File file = new File(filePath);

        if (file.isFile() && file.exists()) { //判断文件是否存在
            InputStreamReader read = null;//考虑到编码格式
            try {
                read = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                boolean content = false;
                //行读取
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    //判断读出的是否是总述和详情的分割文字
                    if (lineTxt.contains("宝付交易号")) {
                        content = true;
                        continue;
                    }

                    //交易详情
                    if (content) {

                        String[] account = lineTxt.split("\\|");
                        BaoFooAccountingDetailVO accountingVO = new BaoFooAccountingDetailVO();
                        accountingVO.setMemberId(account[0]);
                        accountingVO.setTerminalId(account[1]);
                        accountingVO.setTxnType(account[2]);
                        accountingVO.setTxnSubType(account[3]);
                        accountingVO.setBaofooOrderNo(account[4]);
                        accountingVO.setPayOrderNo(account[5]);
                        if ("fi".equals(type)) {
                            accountingVO.setLiquidationDate(account[6]);
                            accountingVO.setOrderStatus(account[7]);
                            accountingVO.setAmount(account[8]);
                            accountingVO.setServiceFee(account[9]);
                            accountingVO.setCreateTime(account[11]);
                            if (account.length > 12) {
                                accountingVO.setRefundOrderNo(account[12]);
                                accountingVO.setRefundCreateTime(account[13]);
                            }
                        } else {
                            accountingVO.setLiquidationDate(account[7]);
                            accountingVO.setOrderStatus(account[8]);
                            accountingVO.setAmount(account[9]);
                            accountingVO.setServiceFee(account[10]);
                            accountingVO.setCreateTime(account[14]);
                            if (account.length > 15)
                                accountingVO.setRefundCreateTime(account[15] != null ? account[15] : null);
                        }

                        voList.add(accountingVO);
                    }

                }
                read.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return voList;
    }

   

    /**
     * 对账
     */
    private void checkAccount(List<BsPayOrders> bsPayOrdersList, List<LnPayOrders> lnPayOrdersList, List<BaoFooAccountingDetailVO> detailVOList,String memberId) {

        //根据本地订单进行明细对账
        Map<String, Object> isCheckedMap = new HashMap<String, Object>();
        // 获取商户号
        String merchantNo = "";
        BsPaymentChannelExample bsPaymentChannelExample = new BsPaymentChannelExample();
    	bsPaymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(1);
        List<BsPaymentChannel> bsPaymentChannels = paymentChannelMapper.selectByExample(bsPaymentChannelExample);
        if (!CollectionUtils.isEmpty(bsPaymentChannels)) {
        	merchantNo = bsPaymentChannels.get(0).getMerchantNo();
		}
        
        log.info("主通道理财端订单对账开始"+memberId+"==========================");
        //理财端订单对账
        for (BsPayOrders bsPayOrders : bsPayOrdersList) {

            //余额购买产品的订单无需对账，此处过滤
            if (Constants.TRANS_BALANCE_BUY_PRODUCT.equals(bsPayOrders.getTransType()) || Constants.USER_USER_BIND_CARD.equals(bsPayOrders.getTransType())) {
                continue;
            }

            String localOrderNo = bsPayOrders.getOrderNo();//订单号
            int localStatus = bsPayOrders.getStatus();//状态
            Double localAmount = bsPayOrders.getAmount();//订单金额
            
            boolean isExist = false;
            if (!CollectionUtils.isEmpty(detailVOList)) {
                for (BaoFooAccountingDetailVO detail : detailVOList) {
                    if (!localOrderNo.equals(detail.getPayOrderNo())) {
                        continue;
                    } else {
                        isExist = true;
                        //对账文件有 订单子类别为00（表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (Constants.ORDER_STATUS_SUCCESS == localStatus && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,

                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, bsPayOrders.getSubAccountId());
                        }
                        //对账文件有（表示三方成功），本地订单失败或金额不匹配，则对账不一致
                        else {
                            //本地订单处理中，但三方已成功，如果订单是卡购买，则需调用 购买成功 服务
                            if (Constants.ORDER_STATUS_PAYING == localStatus
                                    && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {

                                AccountDetail accountDetail = new AccountDetail();
                                accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderNo(detail.getPayOrderNo());
                                accountDetail.setMpOrderNo(detail.getBaofooOrderNo());
                                accountDetail.setReqTime(DateUtil.parseDateTime(detail.getCreateTime()));
                                accountDetail.setSettleTime(DateUtil.parseDate(detail.getLiquidationDate()));
                                accountDetail.setTradeType(detail.getTxnType());
                                accountDetail.setOrderSource(bsPayOrders.getChannel());
                                accountDetail.setCurrency("RMB");
                                accountDetail.setFee(MoneyUtil.defaultRound(detail.getServiceFee()).doubleValue());
                                accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderRemark(AmountTransType.getEnumByCode(detail.getTxnType()).getDescription());
                                
                                checkAccountService.checkAccount4Paying(bsPayOrders, accountDetail, OrderStatus.SUCCESS.getCode(), null, merchantNo, detail.getOrderStatus());

                            } else {
                            	
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                                checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(bsPayOrders.getStatus()),
                                        "交易成功", bsPayOrders.getSubAccountId(), "宝付对账不一致", bsPayOrders.getTransType(), Constants.TRANSTERMINAL_FINANCE,
                                        merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                            }
                        }
                    }
                    break;
                }

                //不存在于对账文件，但本地已成功！(第三方不成功或无此账户)
                if (!isExist && Constants.ORDER_STATUS_SUCCESS == localStatus) {
                    //对账不一致
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单成功，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(bsPayOrders.getStatus()), "无", bsPayOrders.getSubAccountId(), 
                            "宝付对账不一致", bsPayOrders.getTransType(), Constants.TRANSTERMINAL_FINANCE,
                            merchantNo, null, null, null, specialJnlInfo);
                }
                //不存在于对账文件，但本地处理中！(认为三方已失败)
                if (!isExist && Constants.ORDER_STATUS_PAYING == localStatus) {
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单处理中，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(bsPayOrders.getStatus()), "无", bsPayOrders.getSubAccountId(),
                            "宝付对账本地处理中", bsPayOrders.getTransType(), Constants.TRANSTERMINAL_FINANCE,
                            merchantNo, null, null, null, specialJnlInfo);

                }
                isCheckedMap.put(localOrderNo, localStatus);
            }
        }
        log.info("主通道理财端对账结束"+memberId+"==========================");
        
        log.info("主通道借款端对账开始"+memberId+"==========================");
        //借款端对账
        for (LnPayOrders lnPayOrders : lnPayOrdersList) {

            //绑卡、营销代付订单无需对账，此处过滤
            if (Constants.USER_USER_BIND_CARD.equals(lnPayOrders.getTransType()) ||
                    LoanStatus.TRANS_TYPE_MARKET.getCode().equals(lnPayOrders.getTransType())) {
                continue;
            }

            String localOrderNo = lnPayOrders.getOrderNo();//订单号
            int localStatus = lnPayOrders.getStatus();//状态
            Double localAmount = lnPayOrders.getAmount();//订单金额
            String partnerCode = lnPayOrders.getPartnerCode();//资产合作方
            
            boolean isExist = false;
            if (!CollectionUtils.isEmpty(detailVOList)) {
                for (BaoFooAccountingDetailVO detail : detailVOList) {

                    if (!localOrderNo.equals(detail.getPayOrderNo())) {
                        continue;
                    } else {
                        isExist = true;
                        //对账文件有 并且订单状态为1 订单子类别为00（表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (Constants.ORDER_STATUS_SUCCESS == localStatus
                                && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {

                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        }
                        //对账文件有（表示三方成功），本地订单失败或金额不匹配，则对账不一致
                        else {
                            //本地订单处理中，但三方已成功 调用成功业务
                            if (Constants.ORDER_STATUS_PAYING == localStatus
                                    && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {

                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType() + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致,本地订单状态为处理中，三方订单为失败";
                                
                                checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "交易成功", null, "宝付对账不一致", 
                                        StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                                        merchantNo, partnerCode, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                
                            } else {

                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                                
                                checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "交易成功", null, "宝付对账不一致", 
                                        StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                                        merchantNo, partnerCode, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                            }
                        }
                    }
                    break;
                }

                //不存在于对账文件，但本地已成功！
                if (!isExist && Constants.ORDER_STATUS_SUCCESS == localStatus) {
                    //对账不一致
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    //对账不一致
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单成功，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", null, "宝付对账不一致", 
                            StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                            merchantNo, partnerCode, null, null, specialJnlInfo);
                }
                //不存在于对账文件，但本地处理中！(认为三方已失败)
                if (!isExist && Constants.ORDER_STATUS_PAYING == localStatus) {
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单处理中，但三方未成功或无成功记录";

                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", null, "宝付对账本地处理中", 
                            StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                            merchantNo, partnerCode, null, null, specialJnlInfo);

                }
                isCheckedMap.put(localOrderNo, localStatus);
            }
        }
        log.info("主通道借款端对账结束"+memberId+"==========================");
        
        log.info("主通道循环检查三方订单开始"+memberId+"==========================");
        //三方成功，但本地无此订单
        if (!CollectionUtils.isEmpty(detailVOList)) {

            for (BaoFooAccountingDetailVO detail : detailVOList) {

                String orderNo = detail.getPayOrderNo();
                //订单为空的请求跳过
                if (StringUtil.isEmpty(orderNo)) {
                    //不为转账交易时，需要对账不一致告警
                    if(!AmountTransType.DIRECT.getCode().equals(detail.getTxnType())){
                        String info = "{" + detail.getBaofooOrderNo() + "|" + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：三方成功但无单号(币港湾商户号)";
                        checkAccountService.checkAccountIsUnMatch(null, null,
                                MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                "订单状态：无" , "交易成功",
                                null, "宝付对账不一致", Constants.TRANS_TYPE_UN_CLEAR, null);
                    }
                    continue;
                }
                //若对账文件中有记录尚未校验，则针对这笔订单进行本地查询并对账
                if (isCheckedMap.get(orderNo) == null && !detail.getTxnType().equals("00104")) {
                    BsPayOrdersExample example = new BsPayOrdersExample();
                    example.createCriteria().andOrderNoEqualTo(detail.getPayOrderNo());
                    List<BsPayOrders> bsOrders = bsPayOrdersMapper.selectByExample(example);

                    LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                    lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getPayOrderNo());
                    List<LnPayOrders> lnOrders = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                    //第三方有(成功)，本系统未记录此账务
                    if ((CollectionUtils.isEmpty(bsOrders) || bsOrders.size() == 0) && (CollectionUtils.isEmpty(lnOrders) || lnOrders.size() == 0)) {
                        //对账不一致
                        String info = "{" + detail.getPayOrderNo() + "|"
                                + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                        //对账不一致
                        String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：第三方成功，但本地未记录此账务";
                        checkAccountService.checkAccountIsUnMatch(null, null, MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
                                Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：无", "交易成功", null, "宝付对账不一致", Constants.TRANS_TYPE_UN_CLEAR, null,
                                merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                    }
                    //第三方有(成功)，本系统也有记录此账务（发生此种情况可能是对账文件中包含 其他日期的 账务）
                    else {
                        if (CollectionUtils.isNotEmpty(bsOrders) && bsOrders.size() > 0 && Constants.ORDER_STATUS_SUCCESS == bsOrders.get(0).getStatus()
                                && bsOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + orderNo + "|" + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(orderNo, bsOrders.get(0).getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, bsOrders.get(0).getSubAccountId());
                        } else if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0 && Constants.ORDER_STATUS_SUCCESS == lnOrders.get(0).getStatus()
                                && lnOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue())== 0 ) {

                            //对账一致
                            String info = "{" + orderNo + "|" + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(orderNo, lnOrders.get(0).getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        } else {
                            //本地订单处理中，但三方已成功 调用成功业务
                            if (CollectionUtils.isNotEmpty(bsOrders) && bsOrders.size() > 0 && Constants.ORDER_STATUS_PAYING == bsOrders.get(0).getStatus()
                                    && bsOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0 ) {

                                AccountDetail accountDetail = new AccountDetail();
                                accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderNo(detail.getPayOrderNo());
                                accountDetail.setMpOrderNo(detail.getBaofooOrderNo());
                                accountDetail.setReqTime(DateUtil.parseDateTime(detail.getCreateTime()));
                                accountDetail.setSettleTime(DateUtil.parseDate(detail.getLiquidationDate()));
                                accountDetail.setTradeType(detail.getTxnType());
                                accountDetail.setOrderSource(bsOrders.get(0).getChannel());
                                accountDetail.setCurrency("RMB");
                                accountDetail.setFee(MoneyUtil.defaultRound(detail.getServiceFee()).doubleValue());
                                accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderRemark(AmountTransType.getEnumByCode(detail.getTxnType()).getDescription());
                         
                                checkAccountService.checkAccount4Paying(bsOrders.get(0), accountDetail, OrderStatus.SUCCESS.getCode(), null, merchantNo, detail.getOrderStatus());

                            } else if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0 && Constants.ORDER_STATUS_PAYING == lnOrders.get(0).getStatus()
                                    && lnOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue())== 0 ) {

                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType() + "|交易成功|"+memberId+"}对账一不致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType() + "|交易成功|"+memberId+"}对账不一致,本地订单状态为处理中，三方订单为失败";
                                
                                checkAccountService.checkAccountIsUnMatch(orderNo,  lnOrders.get(0).getAmount(),
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnOrders.get(0).getStatus()), "交易成功", null, "宝付对账不一致", 
                                        StringUtil.substringBefore(getTransTerminal(lnOrders.get(0)), "-"), StringUtil.substringAfter(getTransTerminal(lnOrders.get(0)), "-"),
                                        merchantNo, lnOrders.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                
                            } else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：第三方成功，但本地订单失败或金额不匹配";
                                
                                if (CollectionUtils.isNotEmpty(bsOrders) && bsOrders.size() > 0) {
                                    checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), bsOrders.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(bsOrders.get(0).getStatus()), "交易成功", bsOrders.get(0).getSubAccountId(), "宝付对账不一致", 
                                            bsOrders.get(0).getTransType(), Constants.TRANSTERMINAL_FINANCE,
                                            merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                    
                                } else if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0) {
                                	checkAccountService.checkAccountIsUnMatch(orderNo, lnOrders.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(lnOrders.get(0).getStatus()), "交易成功", null, "宝付对账不一致",
                                            StringUtil.substringBefore(getTransTerminal(lnOrders.get(0)), "-"), StringUtil.substringAfter(getTransTerminal(lnOrders.get(0)), "-"),
                                            merchantNo, lnOrders.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                }
                            }
                        }
                    }
                }
            }
        }

    }
    
    
    /**
     * 借款端对账， 获取业务类型
     * @param lnPayOrders
     * @return
     */
    private String getTransTerminal(LnPayOrders lnPayOrders) {
    	String transTerminal = "";
        if (Constants.SYS_WITHDRAW_2_DEP_REPAY_CARD.equals(lnPayOrders.getTransType()) && 
        		Constants.CHANNEL_BAOFOO.equals(lnPayOrders.getChannel())) {
        	transTerminal = BAOFOOTransTypeEnum.WITHDRAW_2_DEP_REPAY_CARD.getCode() + "-" +
        			BAOFOOTransTypeEnum.WITHDRAW_2_DEP_REPAY_CARD.getDescription();
        } else if ("REPAY".equals(lnPayOrders.getTransType()) && 
        		Constants.CHANNEL_BAOFOO.equals(lnPayOrders.getChannel())) {
        	if (Constants.CHANNEL_TRS_TRANSFER.equals(lnPayOrders.getChannelTransType())) {
        		transTerminal = BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getCode() + "-" +
    					BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getDescription();
        	} else {        		
        		if (lnPayOrders.getPaymentChannelId() == null || lnPayOrders.getPaymentChannelId() == 1) {                                		
        			transTerminal = BAOFOOTransTypeEnum.DEPREPAY_MAIN_WITHHOLD_REPAY.getCode() + "-" +
        					BAOFOOTransTypeEnum.DEPREPAY_MAIN_WITHHOLD_REPAY.getDescription();
        		} else if (lnPayOrders.getPaymentChannelId() != null && lnPayOrders.getPaymentChannelId() != 1) {
        			transTerminal = BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode() + "-" +
        					BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getDescription();
        		} 
        	}
        } 
        return transTerminal;
    }
    
    /**
     * 对账
     */
    private void checkAccountAssist(List<BsPayOrders> bsPayOrdersList, List<LnPayOrders> lnPayOrdersList, List<BaoFooAccountingDetailVO> detailVOList,String memberId) {
    	
        //根据本地订单进行明细对账
        Map<String, Object> isCheckedMap = new HashMap<String, Object>();
        // 获取商户号
        String merchantNo = "";
        BsPaymentChannelExample bsPaymentChannelExample = new BsPaymentChannelExample();
    	bsPaymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(2);
        List<BsPaymentChannel> bsPaymentChannels = paymentChannelMapper.selectByExample(bsPaymentChannelExample);
        if (!CollectionUtils.isEmpty(bsPaymentChannels)) {
        	merchantNo = bsPaymentChannels.get(0).getMerchantNo();
		}
        
        log.info("辅通道理财端订单对账开始"+memberId+"==========================");
        //理财端订单对账
        for (BsPayOrders bsPayOrders : bsPayOrdersList) {

            //余额购买产品的订单无需对账，此处过滤
            if (Constants.TRANS_BALANCE_BUY_PRODUCT.equals(bsPayOrders.getTransType()) || Constants.USER_USER_BIND_CARD.equals(bsPayOrders.getTransType())) {
                continue;
            }

            String localOrderNo = bsPayOrders.getOrderNo();//订单号
            int localStatus = bsPayOrders.getStatus();//状态
            Double localAmount = bsPayOrders.getAmount();//订单金额

            boolean isExist = false;
            if (!CollectionUtils.isEmpty(detailVOList)) {
                for (BaoFooAccountingDetailVO detail : detailVOList) {
                    if (!localOrderNo.equals(detail.getPayOrderNo())) {
                        continue;
                    } else {
                        isExist = true;
                        //对账文件有 订单子类别为00（表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (Constants.ORDER_STATUS_SUCCESS == localStatus && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,

                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, bsPayOrders.getSubAccountId());
                        }
                        //对账文件有（表示三方成功），本地订单失败或金额不匹配，则对账不一致
                        else {
                            //本地订单处理中，但三方已成功，如果订单是卡购买，则需调用 购买成功 服务
                            if (Constants.ORDER_STATUS_PAYING == localStatus
                                    && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单处理中但是三方订单成功";
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(bsPayOrders.getStatus()),
                                        "交易成功", bsPayOrders.getSubAccountId(), "宝付对账不一致", bsPayOrders.getTransType(), Constants.TRANSTERMINAL_FINANCE,
                                        merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                            } else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(bsPayOrders.getStatus()),
                                        "交易成功", bsPayOrders.getSubAccountId(), "宝付对账不一致", bsPayOrders.getTransType(), Constants.TRANSTERMINAL_FINANCE,
                                        merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                            }
                        }
                    }
                    break;
                }

                //不存在于对账文件，但本地已成功！(第三方不成功或无此账户)
                if (!isExist && Constants.ORDER_STATUS_SUCCESS == localStatus) {
                    //对账不一致
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    //对账不一致
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单成功，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(bsPayOrders.getStatus()), "无", bsPayOrders.getSubAccountId(), 
                            "宝付对账不一致", bsPayOrders.getTransType(), Constants.TRANSTERMINAL_FINANCE,
                            merchantNo, null, null, null, specialJnlInfo);
                }
                //不存在于对账文件，但本地处理中！(认为三方已失败)
                if (!isExist && Constants.ORDER_STATUS_PAYING == localStatus) {
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单处理中，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(bsPayOrders.getStatus()), "无", bsPayOrders.getSubAccountId(), 
                            "宝付对账本地处理中", bsPayOrders.getTransType(), Constants.TRANSTERMINAL_FINANCE,
                            merchantNo, null, null, null, specialJnlInfo);
                }
                isCheckedMap.put(localOrderNo, localStatus);
            }
        }
        log.info("辅通道理财端订单对账结束"+memberId+"==========================");
        
        log.info("辅通道借款端订单对账开始"+memberId+"==========================");
        //借款端对账
        for (LnPayOrders lnPayOrders : lnPayOrdersList) {
            //绑卡、营销代付订单无需对账，此处过滤
            if (Constants.USER_USER_BIND_CARD.equals(lnPayOrders.getTransType()) ||
                    LoanStatus.TRANS_TYPE_MARKET.getCode().equals(lnPayOrders.getTransType())) {
                continue;
            }

            //代扣还款记录必须与钱包转账信息记录一致，不一致告警
            if (Constants.TRANS_REPAY.equals(lnPayOrders.getTransType()) && lnPayOrders.getPaymentChannelId() == 2
            		&& Constants.CHANNEL_TRS_DK.equals(lnPayOrders.getChannelTransType())) {
            	LnPayOrders dkLnPayOrdersInfo = lnPayOrdersMapper.selectByOrderNoAndChannel(lnPayOrders.getOrderNo(), Constants.CHANNEL_BAOFOO, Constants.ORDER_STATUS_SUCCESS);
            	// 代扣还款成功有记录，才执行对账逻辑
            	if (dkLnPayOrdersInfo != null) {
            		LnPayOrders transferInfo = lnPayOrdersMapper.selectByOrderNoAndChannel("DKTS"+lnPayOrders.getOrderNo(), Constants.CHANNEL_BAOFOO, null);
        			String localOrderNo = lnPayOrders.getOrderNo();
        			String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：代扣还款成功,钱包转账失败";
        			String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：代扣还款记录与钱包转账信息记录不一致";
                	if (transferInfo == null) {
                		checkAccountService.checkAccountIsUnMatch(localOrderNo, lnPayOrders.getAmount(), null,
            					Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
            					"订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", lnPayOrders.getSubAccountId(), 
            					"宝付对账本地处理中", BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode(), Constants.TRANSTERMINAL_LOAN,
            					merchantNo, lnPayOrders.getPartnerCode(), null, null, specialJnlInfo);
                	} else { 
                		String errorInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：代扣还款成功,本地订单状态或金额不一致";
                		if (dkLnPayOrdersInfo.getAmount().compareTo(transferInfo.getAmount()) != 0 ||
                				!dkLnPayOrdersInfo.getStatus().equals(transferInfo.getStatus()) ||
                				!dkLnPayOrdersInfo.getPartnerCode().equals(transferInfo.getPartnerCode())) {
                			checkAccountService.checkAccountIsUnMatch(localOrderNo, dkLnPayOrdersInfo.getAmount(), null,
                					Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, errorInfo,
                					"订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", lnPayOrders.getSubAccountId(), 
                					"宝付对账本地处理中", BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode(), Constants.TRANSTERMINAL_LOAN,
                					merchantNo, lnPayOrders.getPartnerCode(), null, null, specialJnlInfo);
                		}
                	}
            	}
            }
            
            String localOrderNo = lnPayOrders.getOrderNo();//订单号
            int localStatus = lnPayOrders.getStatus();//状态
            Double localAmount = lnPayOrders.getAmount();//订单金额

            boolean isExist = false;
            if (!CollectionUtils.isEmpty(detailVOList)) {
                for (BaoFooAccountingDetailVO detail : detailVOList) {
                    if (!localOrderNo.equals(detail.getPayOrderNo())) {
                        continue;
                    } else {
                        isExist = true;
                        //对账文件有 并且订单状态为1 订单子类别为00（表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (Constants.ORDER_STATUS_SUCCESS == localStatus
                                && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        }
                        //对账文件有（表示三方成功），本地订单失败或金额不匹配，则对账不一致
                        else {
                            //本地订单处理中，但三方已成功 调用成功业务
                            if (Constants.ORDER_STATUS_PAYING == localStatus
                                    && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {

                                //借款方业务处理
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单处理中，但是三方成功";
                                
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "交易成功", null, "宝付对账不一致", 
                                        StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                                        merchantNo, lnPayOrders.getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                            } else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                                
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "交易成功", null, "宝付对账不一致", 
                                        StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                                        merchantNo, lnPayOrders.getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                            }
                        }
                    }
                    break;
                }

                //不存在于对账文件，但本地已成功！
                if (!isExist && Constants.ORDER_STATUS_SUCCESS == localStatus) {
                    //对账不一致
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    //对账不一致
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单成功，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", null, "宝付对账不一致", 
                            StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                            merchantNo, lnPayOrders.getPartnerCode(), null, null, specialJnlInfo);
                }
                //不存在于对账文件，但本地处理中！(认为三方已失败)
                if (!isExist && Constants.ORDER_STATUS_PAYING == localStatus) {
                    String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                    String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地订单处理中，但三方未成功或无成功记录";

                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", null, "宝付对账本地处理中",
                            StringUtil.substringBefore(getTransTerminal(lnPayOrders), "-"), StringUtil.substringAfter(getTransTerminal(lnPayOrders), "-"),
                            merchantNo, lnPayOrders.getPartnerCode(), null, null, specialJnlInfo);
                }
                isCheckedMap.put(localOrderNo, localStatus);
            }
        }
        log.info("辅通道借款端订单对账结束"+memberId+"==========================");

        log.info("辅通道循环检查三方订单开始"+memberId+"==========================");
        //三方成功，但本地无此订单
        if (!CollectionUtils.isEmpty(detailVOList)) {

            for (BaoFooAccountingDetailVO detail : detailVOList) {

                String orderNo = detail.getPayOrderNo();
                //订单为空的请求跳过
                if (StringUtil.isEmpty(orderNo)) {
                    //不为转账交易时，需要对账不一致告警
                    if(!AmountTransType.DIRECT.getCode().equals(detail.getTxnType())){
                        String info = "{" + detail.getBaofooOrderNo() + "|" + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：三方成功但无单号(币港湾商户号)";
                        checkAccountService.checkAccountIsUnMatch(null, null,
                                MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                "订单状态：无" , "交易成功",
                                null, "宝付对账不一致", Constants.TRANS_TYPE_UN_CLEAR, null,
                                merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), info);
                    }
                    continue;
                }
                //若对账文件中有记录尚未校验，则针对这笔订单进行本地查询并对账
                if (isCheckedMap.get(orderNo) == null && !detail.getTxnType().equals("00104")) {
                    BsPayOrdersExample example = new BsPayOrdersExample();
                    example.createCriteria().andOrderNoEqualTo(detail.getPayOrderNo());
                    List<BsPayOrders> bsOrders = bsPayOrdersMapper.selectByExample(example);

                    LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                    lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getPayOrderNo());
                    List<LnPayOrders> lnOrders = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                    //第三方有(成功)，本系统未记录此账务
                    if ((CollectionUtils.isEmpty(bsOrders) || bsOrders.size() == 0) && (CollectionUtils.isEmpty(lnOrders) || lnOrders.size() == 0)) {
                        //对账不一致
                        String info = "{" + detail.getPayOrderNo() + "|"
                                + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                        //对账不一致
                        String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：第三方成功，但本地未记录此账务";
                        
                        checkAccountService.checkAccountIsUnMatch(null, null, MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
                                Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：无", "交易成功", null, 
                                "宝付对账不一致", Constants.TRANS_TYPE_UN_CLEAR, null,
                                merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                    }
                    //第三方有(成功)，本系统也有记录此账务（发生此种情况可能是对账文件中包含 其他日期的 账务）
                    else {
                        if (CollectionUtils.isNotEmpty(bsOrders) && bsOrders.size() > 0 && Constants.ORDER_STATUS_SUCCESS == bsOrders.get(0).getStatus()
                                && bsOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + orderNo + "|" + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(orderNo, bsOrders.get(0).getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, bsOrders.get(0).getSubAccountId());
                        } else if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0 && Constants.ORDER_STATUS_SUCCESS == lnOrders.get(0).getStatus()
                                && lnOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0 ) {

                            //对账一致
                            String info = "{" + orderNo + "|" + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(orderNo, lnOrders.get(0).getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        } else {
                            //本地订单处理中，但三方已成功 调用成功业务
                            if (CollectionUtils.isNotEmpty(bsOrders) && bsOrders.size() > 0 && Constants.ORDER_STATUS_PAYING == bsOrders.get(0).getStatus()
                                    && bsOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {

                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单处理中但是三方订单成功";
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), bsOrders.get(0).getAmount(),
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(bsOrders.get(0).getStatus()), "交易成功", bsOrders.get(0).getSubAccountId(),
                                        "宝付对账不一致", bsOrders.get(0).getTransType(), Constants.TRANSTERMINAL_FINANCE,
                                        merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                            } else if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0 && Constants.ORDER_STATUS_PAYING == lnOrders.get(0).getStatus()
                                    && lnOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {

                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单处理中，但是三方成功";
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), lnOrders.get(0).getAmount(),
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnOrders.get(0).getStatus()), "交易成功", null, "宝付对账不一致", 
                                        StringUtil.substringBefore(getTransTerminal(lnOrders.get(0)), "-"), StringUtil.substringAfter(getTransTerminal(lnOrders.get(0)), "-"),
                                        merchantNo, lnOrders.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);

                            } else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：第三方成功，但本地订单失败或金额不匹配";
                                
                                if (CollectionUtils.isNotEmpty(bsOrders) && bsOrders.size() > 0) {
                                    checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), bsOrders.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(bsOrders.get(0).getStatus()), "交易成功", bsOrders.get(0).getSubAccountId(), 
                                            "宝付对账不一致", bsOrders.get(0).getTransType(), Constants.TRANSTERMINAL_FINANCE,
                                            merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                } else if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0) {
                                    
                                	checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), lnOrders.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(lnOrders.get(0).getStatus()), "交易成功", null, "宝付对账不一致", 
                                            StringUtil.substringBefore(getTransTerminal(lnOrders.get(0)), "-"), StringUtil.substringAfter(getTransTerminal(lnOrders.get(0)), "-"),
                                            merchantNo, lnOrders.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                }
                            }
                        }
                    }
                }
                // 辅助通道循环三方新增00104对账
                if (isCheckedMap.get(orderNo) == null && detail.getTxnType().equals("00104")) {
                	LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                	lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getPayOrderNo());
                	List<LnPayOrders> payOrderlList = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                	
                	if (CollectionUtils.isEmpty(payOrderlList) || payOrderlList.size() == 0) {
                		 //对账不一致
                        String info = "{" + detail.getPayOrderNo() + "|"
                                + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                        //对账不一致
                        String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：第三方成功，但本地未记录此账务";
                        
                        checkAccountService.checkAccountIsUnMatch(null, null, MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
                                Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：无", "交易成功", null, 
                                "宝付对账不一致", Constants.TRANS_TYPE_UN_CLEAR, null,
                                merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                	} else if (CollectionUtils.isNotEmpty(payOrderlList) && payOrderlList.size() > 0 
                            && payOrderlList.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0 ) {
                        //对账一致
                        String info = "{" + orderNo + "|" + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账一致";
                        checkAccountService.checkAccountIsMatch(orderNo, payOrderlList.get(0).getAmount(),
                                MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                info, null);
					} else if (CollectionUtils.isNotEmpty(payOrderlList) && Constants.CHANNEL_TRS_TRANSFER.equals(payOrderlList.get(0).getChannelTransType())){
                        //对账不一致
                        String info = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                        //对账不一致
                        String specialJnlInfo = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                        checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), payOrderlList.get(0)
                                .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        "订单状态：" + String.valueOf(payOrderlList.get(0).getStatus()), "交易成功",
                        null, "宝付对账不一致", BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getCode(), BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getDescription(),
                        merchantNo, payOrderlList.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
					} else if (CollectionUtils.isNotEmpty(payOrderlList)){
						//对账不一致
                        String info = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                        //对账不一致
                        String specialJnlInfo = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
                                + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                        checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), payOrderlList.get(0)
                                .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        "订单状态：" + String.valueOf(payOrderlList.get(0).getStatus()), "交易成功",
                        null, "宝付对账不一致", BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getCode(), BAOFOOTransTypeEnum.DEPREPAY_ASSIST_WITHHOLD_REPAY.getDescription(),
                        merchantNo, payOrderlList.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
					}
                }
            }
        }

    }

    /**
     * 宝付账户间转账入账对账
     *
     * @param detailVOList
     */
    private void checkTransAccount(List<BaoFooAccountingDetailVO> detailVOList,String memberId) {
        //根据本地订单进行明细对账
        Map<String, Object> isCheckedMap = new HashMap<String, Object>();

        //查询对账日期范围内本地订单
        Date checkDate = DateUtil.addDays(new Date(), -1);//对账日期
        Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
        Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));

        // 获取商户号
        String merchantNo = "";
        BsPaymentChannelExample bsPaymentChannelExample = new BsPaymentChannelExample();
    	bsPaymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(1);
        List<BsPaymentChannel> bsPaymentChannels = paymentChannelMapper.selectByExample(bsPaymentChannelExample);
        if (!CollectionUtils.isEmpty(bsPaymentChannels)) {
        	merchantNo = bsPaymentChannels.get(0).getMerchantNo();
		}
        
        log.info("宝付账户间转账入账对账开始"+memberId+"==========================");
        //系统转账订单
        BsSysReceiveMoneyExample orderExample = new BsSysReceiveMoneyExample();
        orderExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate);
        List<BsSysReceiveMoney> receiveMoneyList = receiveMoneyMapper.selectByExample(orderExample);
        // 因实际没有这种业务，老产品回款对账代码删除
        
        //查询代偿订单
        List<LnCompensate> compensates = lnCompensateMapper.selectCompensateCheckAccountInfo();
        for (LnCompensate lnCompensate : compensates) {
            String localOrderNo = lnCompensate.getPayOrderNo();//订单号
            Double localAmount = lnCompensate.getTotalMount();//订单金额
            String compensatePartnerCode = lnCompensate.getPartnerCode();//资产合作方
            boolean checkFlag = false;
            
            if (!CollectionUtils.isEmpty(detailVOList)) {
            	//校验标记
                for (BaoFooAccountingDetailVO detail : detailVOList) {

                    if (!localOrderNo.equals(detail.getPayOrderNo()) || !detail.getTxnType().equals("00104")) {
                        continue;
                    } else {
                    	checkFlag = true;

                        //对账文件有 订单子类别为00（表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        } else {
                            //对账不一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                            //对账不一致
                            String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                            checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                    "订单状态：" + "6",
                                    "交易成功", null, "宝付对账不一致", BAOFOOTransTypeEnum.LN_COMPENSATE.getCode(), BAOFOOTransTypeEnum.LN_COMPENSATE.getDescription(),
                                    merchantNo, compensatePartnerCode, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                        }
                        
                        // 对每一笔代偿订单,检查其对应的ln_compensate_detail的详情信息,
                        // 如果状态=’INIT’,并且本地账单状态已废弃/已代偿，  进行告警
                        LnCompensateDetailExample lnCompensateDetailExample = new LnCompensateDetailExample();
                        lnCompensateDetailExample.createCriteria().andCompensateIdEqualTo(lnCompensate.getId()).andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_INIT);
                        List<LnCompensateDetail> lnCompensateDetailList = lnCompensateDetailMapper.selectByExample(lnCompensateDetailExample);
                        if (!CollectionUtils.isEmpty(lnCompensateDetailList)) {
                        	for (LnCompensateDetail lnCompensateDetail : lnCompensateDetailList) {								
                        		LnRepaySchedule lnRepaySchedule = lnRepayScheduleMapper.selectRepayScheduleWithCompensate(lnCompensateDetail.getPartnerRepayId());
                        		if (lnRepaySchedule != null && (Constants.LN_REPAY_LATE_NOT.equals(lnRepaySchedule.getStatus()) ||
                        				Constants.LN_REPAY_LATE_REPAIED.equals(lnRepaySchedule.getStatus()) ||
                        				Constants.LN_REPAY_CANCELLED.equals(lnRepaySchedule.getStatus()))) {		
                        			
                        			String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致： 本地代偿对应代偿明细的状态或金额不一致";
                        			checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount, 
                        					null, Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        					"订单状态：" + "INIT",
                        					"交易成功", null, "宝付对账不一致", BAOFOOTransTypeEnum.LN_COMPENSATE.getCode(), BAOFOOTransTypeEnum.LN_COMPENSATE.getDescription(),
                        					merchantNo, compensatePartnerCode, null, null, info);
                        		}                        	
							}
                		}
                        
                        isCheckedMap.put(localOrderNo, "无(代偿)");
                        break;
                    }
                }
            }
            
            if (!checkFlag) {
                //对账不一致
                String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                //对账不一致
                String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地存在代偿单号，但三方未记录";
                checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                        Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        "订单状态：6" , "无", null, "宝付对账不一致", BAOFOOTransTypeEnum.LN_COMPENSATE.getCode(), BAOFOOTransTypeEnum.LN_COMPENSATE.getDescription(),
                        merchantNo, compensatePartnerCode, null, null, specialJnlInfo);
			}
		}
        
        //查询线下还款对账信息   
        List<LnRepayCheckAccountVO> lnRepays = lnRepayMapper.selectOfflineRepayCheckInfo();
        for (LnRepayCheckAccountVO lnRepay : lnRepays) {
            String localOrderNo = lnRepay.getPayOrderNo();//订单号
            String localStatus = lnRepay.getStatus();
            Double localAmount = lnRepay.getDoneTotal();//订单金额
            String offlineRepayPartnerCode = lnRepay.getPartnerCode();
            boolean checkFlag = false;
            
            if (!CollectionUtils.isEmpty(detailVOList)) {
                for (BaoFooAccountingDetailVO detail : detailVOList) {

                    if (!localOrderNo.equals(detail.getPayOrderNo()) || !detail.getTxnType().equals("00104")) {
                    	continue;
                    } else {
                    	checkFlag = true;

                        //对账文件有 订单子类别为00（表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        } else {
                            //对账不一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                            //对账不一致
                            String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                            checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                    "订单状态：" + String.valueOf(lnRepay.getStatus()),"交易成功", null,
                                    "宝付对账不一致", BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getCode(), BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getDescription(),
                                    merchantNo, offlineRepayPartnerCode, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                        }
                        isCheckedMap.put(localOrderNo, localStatus);
                        break;
                    }
                }
            } 
            if (!checkFlag) { 
                //对账不一致
                String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                //对账不一致
                String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地存在线下还款单号，但三方未记录";
                checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                        Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        "订单状态："+ String.valueOf(lnRepay.getStatus()) , "无", null, 
                        "宝付对账不一致", BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getCode(), BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getDescription(),
                        merchantNo, offlineRepayPartnerCode, null, null, specialJnlInfo);
			}
        }

        
        //查询辅助通道转账到主通道的信息
        List<LnPayOrders> lnPayOrderList = lnPayOrdersMapper.selectBaofooAssistTransOrders();
        for (LnPayOrders lnPayOrders : lnPayOrderList) {
            String localOrderNo = lnPayOrders.getOrderNo();//订单号
            String localStatus = String.valueOf(lnPayOrders.getStatus());
            Double localAmount = lnPayOrders.getAmount();//订单金额
            String main2AssistPartnerCode = lnPayOrders.getPartnerCode();//资产合作方
            boolean checkFlag = false;

            if (!CollectionUtils.isEmpty(detailVOList)) {
                for (BaoFooAccountingDetailVO detail : detailVOList) {

                    if (!localOrderNo.equals(detail.getPayOrderNo()) || !detail.getTxnType().equals("00104")) {

                    	continue;
                    } else {
                    	checkFlag = true;

                        //对账文件有 订单子类别为00（表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        } else {
                            //对账不一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                            //对账不一致
                            String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账不一致：本地订单失败或金额不匹配";
                            checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                    "订单状态：" + String.valueOf(lnPayOrders.getStatus()),
                                    "交易成功", null, "宝付对账不一致", BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getCode(), BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getDescription(),
                                    merchantNo, main2AssistPartnerCode, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                        }

                        isCheckedMap.put(localOrderNo, localStatus);
                        break;
                    }
                }
            }
            	
            if (!checkFlag){
                //对账不一致
                String info = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                String specialJnlInfo = "{" + localOrderNo + "|交易结果未知|"+memberId+"}对账不一致：本地存在转账交易订单单号，但三方未记录";

                checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, null,
                        Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        "订单状态："+ String.valueOf(lnPayOrders.getStatus()) , "无", null, "宝付对账不一致", BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getCode(), BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getDescription(),
                        merchantNo, main2AssistPartnerCode, null, null, specialJnlInfo);
			}
		}

        if (CollectionUtils.isNotEmpty(detailVOList)) {
        	log.info("================== 循环检查三方订单开始====================", JSON.toJSONString(detailVOList));
            for (BaoFooAccountingDetailVO detail : detailVOList) {

                String orderNo = detail.getPayOrderNo();
                String baoFooOrderNo = detail.getBaofooOrderNo();
                //订单为空的请求跳过
                if (StringUtil.isEmpty(orderNo) && StringUtil.isEmpty(baoFooOrderNo) ) {
                    continue;
                }

                //若对账文件中有记录尚未校验，则针对这笔订单进行本地查询并对账
                if (isCheckedMap.get(orderNo) == null && isCheckedMap.get(baoFooOrderNo) == null && detail.getTxnType().equals("00104")) {
                    orderExample = new BsSysReceiveMoneyExample();
                    orderExample.createCriteria().andPayOrderNoEqualTo(detail.getPayOrderNo());
                    List<BsSysReceiveMoney> list = receiveMoneyMapper.selectByExample(orderExample);
                    //第三方有(成功)，本系统未记录此账务
                    if (CollectionUtils.isEmpty(list) || list.size() == 0) {                        //对账不一致，有可能是代偿
                        
                    	LnCompensateExample lnCompensateExample = new LnCompensateExample();
                        lnCompensateExample.createCriteria().andPayOrderNoEqualTo(detail.getPayOrderNo());
                        List<LnCompensate> compensatesList = lnCompensateMapper.selectByExample(lnCompensateExample);
                    	
                    	if (CollectionUtils.isEmpty(compensatesList) || compensatesList.size() == 0) {
	                          LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
	                          lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getPayOrderNo());
	                          List<LnPayOrders> payOrderlList = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
	                          
	                          if (CollectionUtils.isEmpty(payOrderlList) || payOrderlList.size() == 0) {
		                        	  LnRepayExample   repayExample = new LnRepayExample();
		                        	  repayExample.createCriteria().andPayOrderNoEqualTo(detail.getPayOrderNo());
		                        	  List<LnRepay> repayList = lnRepayMapper.selectByExample(repayExample);
		                        	  if (CollectionUtils.isEmpty(repayList) || repayList.size() == 0) {
		                        		  
		                        		  String info = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
		              	                          + "|交易成功|"+memberId+"}对账不一致：本地或三方订单编号缺失";
		                        		  String specialJnlInfo = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
              	                          + "|交易成功|"+memberId+"}对账不一致：第三方成功，但本地未记录此账务(云贷回款、代偿、辅助通道转账)";
		              	                          checkAccountService.checkAccountIsUnMatch(null, null, MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
		              	                          Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：无", "交易成功", null, "宝付对账不一致", 
		              	                          Constants.TRANS_TYPE_UN_CLEAR, null,
		              	                          merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
		                        	  } else {
				  							if (CollectionUtils.isNotEmpty(repayList) && repayList.size() > 0 
					                                && repayList.get(0).getDoneTotal().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
					                            //对账一致
					                            String info = "{" + orderNo + "|" + detail.getTxnType()
					                                    + "|交易成功|"+memberId+"}对账一致";
					                            checkAccountService.checkAccountIsMatch(orderNo, repayList.get(0).getDoneTotal(),
					                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
					                                    info, null);
											} else if (CollectionUtils.isNotEmpty(repayList) && Constants.IS_OFFLINE_REPAY.equals(repayList.get(0).getRepayType())) {
				                                //对账不一致
				                                String info = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
				                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致(合作方线下还款)";
				                                //对账不一致
				                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
				                                        + "|交易成功|"+memberId+"}对账不一致：合作方线下还款金额不匹配";
				                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), repayList.get(0).getDoneTotal(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
				                                "订单状态：" + String.valueOf(repayList.get(0).getStatus()), "交易成功", null, "宝付对账不一致",
				                                BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getCode(), BAOFOOTransTypeEnum.PARTNER_OFFLINE_REPAY.getDescription(),
				                                merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
											} else {
				                                //对账不一致
				                                String info = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
				                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致(辅助通道转账)";
				                                //对账不一致
				                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
				                                        + "|交易成功|"+memberId+"}对账不一致：本地辅助通道转账金额不匹配";
				                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), repayList.get(0).getDoneTotal(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
				                                "订单状态：" + String.valueOf(repayList.get(0).getStatus()), "交易成功", null, "宝付对账不一致",
				                                BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getCode(), BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getDescription(),
				                                merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
											}
		                        	  }
	                        	  	
	                          }else {
		  							if (CollectionUtils.isNotEmpty(payOrderlList) && payOrderlList.size() > 0 
			                                && payOrderlList.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0 ) {
			                            //对账一致
			                            String info = "{" + orderNo + "|" + detail.getTxnType()
			                                    + "|交易成功|"+memberId+"}对账一致";
			                            checkAccountService.checkAccountIsMatch(orderNo, payOrderlList.get(0).getAmount(),
			                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
			                                    info, null);
									} else if (CollectionUtils.isNotEmpty(payOrderlList) && Constants.CHANNEL_TRS_TRANSFER.equals(payOrderlList.get(0).getChannelTransType())){
		                                //对账不一致
		                                String info = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
		                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致(辅助通道转账)";
		                                //对账不一致
		                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
		                                        + "|交易成功|"+memberId+"}对账不一致：本地辅助通道转账金额不匹配";
		                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), payOrderlList.get(0)
		                                        .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
		                                "订单状态：" + String.valueOf(payOrderlList.get(0).getStatus()), "交易成功",
		                                null, "宝付对账不一致", BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getCode(), BAOFOOTransTypeEnum.TRANSFER_2_MAIN.getDescription(),
		                                merchantNo, payOrderlList.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
									}
	                          }
						}else {
							if (CollectionUtils.isNotEmpty(compensatesList) && compensatesList.size() > 0 
	                                && compensatesList.get(0).getTotalMount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
	                            //对账一致
	                            String info = "{" + orderNo + "|" + detail.getTxnType()
	                                    + "|交易成功|"+memberId+"}对账一致";
	                            checkAccountService.checkAccountIsMatch(orderNo, compensatesList.get(0).getTotalMount(),
	                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
	                                    info, null);
							}else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致(代偿)";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|" + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地代偿金额不匹配";
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), compensatesList.get(0)
                                        .getTotalMount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                "订单状态：无(代偿)" , "交易成功",
                                null, "宝付对账不一致", BAOFOOTransTypeEnum.LN_COMPENSATE.getCode(), BAOFOOTransTypeEnum.LN_COMPENSATE.getDescription(),
                                merchantNo, compensatesList.get(0).getPartnerCode(), detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
							}
						}
                    }
                    //第三方有(成功)，本系统也有记录此账务（发生此种情况可能是对账文件中包含 其他日期的 账务）
                    else {
                        if (CollectionUtils.isNotEmpty(list) && list.size() > 0 && Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS.equals(list.get(0).getStatus())
                                && list.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + orderNo + "|" + detail.getTxnType()
                                    + "|交易成功|"+memberId+"}对账一致";
                            checkAccountService.checkAccountIsMatch(orderNo, list.get(0).getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        } else {
                            //本地订单处理中，但三方已成功
                            if (CollectionUtils.isNotEmpty(list) && list.size() > 0 && !Constants.RETURN_NOTICE_DEAL_STATUS_SUCCESS.equals(list.get(0).getStatus())
                                    && list.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地或三方订单编号缺失";
                                //对账不一致
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单没有成功,三方成功";
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), 0d, MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
                                        Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：未成功", "交易成功", null, 
                                        "宝付对账不一致", BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getCode(), BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getDescription(),
                                        merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);

                            } else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：本地订单状态或金额与三方不一致";
                                String specialJnlInfo = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功|"+memberId+"}对账不一致：第三方成功，但本地订单失败或金额不匹配";
                                
                                if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
                                    checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), list.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(list.get(0).getStatus()), "交易成功",
                                            null, "宝付对账不一致", BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getCode(), BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getDescription(),
                                            merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                } else if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
                                    checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), list.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(list.get(0).getStatus()), "交易成功",
                                            null, "宝付对账不一致", BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getCode(), BAOFOOTransTypeEnum.SYS_RECEIVE_MONEY.getDescription(),
                                            merchantNo, null, detail.getPayOrderNo(), detail.getOrderStatus(), specialJnlInfo);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 填充系统日常对账轧差信息
     * @param channel
     * @return
     */
    private BsSysDailyCheckGacha fillSysDailyCheckGacha(String channel, String merchantNo) {
    	BsSysDailyCheckGacha sysDailyCheckGacha = new BsSysDailyCheckGacha();
    	Date time = DateUtil.addDays(new Date(), -1);
    	sysDailyCheckGacha.setCheckDate(time);
    	sysDailyCheckGacha.setChannel(channel);
    	sysDailyCheckGacha.setMerchantNo(merchantNo);
    	return sysDailyCheckGacha;
    }
    
    
    /**
     * 赞分期账户宝付对账
     * @param detailVOList
     */
    private void checkZanAccount(List<BaoFooAccountingDetailVO> detailVOList,List<LnPayOrders> lnPayOrdersList){

        Map<String, Object> isCheckedMap = new HashMap<String, Object>();

        //营销代付对账
        for (LnPayOrders lnPayOrders : lnPayOrdersList) {

            String localOrderNo = lnPayOrders.getOrderNo();//订单号
            int localStatus = lnPayOrders.getStatus();//状态
            Double localAmount = lnPayOrders.getAmount();//订单金额

            boolean isExist = false;
            if (!CollectionUtils.isEmpty(detailVOList)) {
                for (BaoFooAccountingDetailVO detail : detailVOList) {
                    if (!localOrderNo.equals(detail.getPayOrderNo())) {
                        continue;
                    } else {
                        isExist = true;
                        //对账文件有 （表示三方成功），本地订单成功，且金额一致，则对账一致
                        if (Constants.ORDER_STATUS_SUCCESS == localStatus
                                && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            //对账一致
                            String info = "{" + detail.getPayOrderNo() + "|"
                                    + detail.getTxnType() + "|交易成功}营销代付对账一致";
                            checkAccountService.checkAccountIsMatch(detail.getPayOrderNo(), localAmount,
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        }
                        //对账文件有（表示三方成功），本地订单失败或金额不匹配，则对账不一致
                        else {
                            //本地订单处理中，但三方已成功 调用成功业务
                            if (Constants.ORDER_STATUS_PAYING == localStatus
                                    && localAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {

                                //借款方业务处理
                                BsPayOrders bsPayOrders = new BsPayOrders();
                                bsPayOrders.setOrderNo(detail.getPayOrderNo());
                                bsPayOrders.setAmount(localAmount);
                                bsPayOrders.setStatus(localStatus);
                                bsPayOrders.setSubAccountId(lnPayOrders.getSubAccountId());

                                AccountDetail accountDetail = new AccountDetail();
                                accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderNo(detail.getPayOrderNo());
                                accountDetail.setMpOrderNo(detail.getBaofooOrderNo());
                                accountDetail.setReqTime(DateUtil.parseDateTime(detail.getCreateTime()));
                                accountDetail.setSettleTime(DateUtil.parseDate(detail.getLiquidationDate()));
                                accountDetail.setTradeType(detail.getTxnType());
                                accountDetail.setOrderSource(Constants.CHANNEL_BAOFOO);
                                accountDetail.setCurrency("RMB");
                                accountDetail.setFee(localAmount);
                                accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderRemark(AmountTransType.getEnumByCode(detail.getTxnType()).getDescription());
                                checkAccountService.checkAccount4Paying(bsPayOrders, accountDetail, OrderStatus.SUCCESS.getCode(), null);

                            } else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功}营销代付对账不一致：本地订单失败或金额不匹配";
                                checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), localAmount,
                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnPayOrders.getStatus()),
                                        "交易成功", null, "宝付营销代付对账不一致", lnPayOrders.getTransType(), Constants.TRANSTERMINAL_LOAN);
                            }
                        }
                    }
                    break;
                }

                //不存在于对账文件，但本地已成功！(第三方不成功或无此账户 除营销代付)
                if (!isExist && Constants.ORDER_STATUS_SUCCESS == localStatus ) {
                    //对账不一致
                    String info = "{" + localOrderNo + "|交易结果未知}营销代付对账不一致：本地订单成功，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, 0d,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", null, 
                            "宝付营销代付对账不一致", lnPayOrders.getTransType(), Constants.TRANSTERMINAL_LOAN);
                }
                //不存在于对账文件，但本地处理中！(认为三方已失败)
                if (!isExist && Constants.ORDER_STATUS_PAYING == localStatus) {
                    String info = "{" + localOrderNo + "|交易结果未知}营销代付对账不一致：本地订单处理中，但三方未成功或无成功记录";
                    checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, 0d,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(lnPayOrders.getStatus()), "无", null, 
                            "宝付营销代付对账本地处理中", lnPayOrders.getTransType(), Constants.TRANSTERMINAL_LOAN);

                }
                isCheckedMap.put(localOrderNo, localStatus);
            }
        }

        //三方成功，但本地无此订单
        if (!CollectionUtils.isEmpty(detailVOList)) {

            for (BaoFooAccountingDetailVO detail : detailVOList) {

                String orderNo = detail.getPayOrderNo();
                //订单为空的请求跳过
                if (StringUtil.isEmpty(orderNo)) {
                    continue;
                }
                //若对账文件中有记录尚未校验，则针对这笔订单进行本地查询并对账
                if (isCheckedMap.get(orderNo) == null) {

                    LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                    lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getPayOrderNo());
                    List<LnPayOrders> lnOrders = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                    //第三方有(成功)，本系统未记录此账务
                    if (CollectionUtils.isEmpty(lnOrders) || lnOrders.size() == 0) {
                        //对账不一致
                        String info = "{" + detail.getPayOrderNo() + "|"
                                + detail.getTxnType()
                                + "|交易成功}对账不一致：第三方成功，但本地未记录此账务";
                        checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), null, MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
                                Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：无", "交易成功", null, "宝付对账不一致", null, Constants.TRANSTERMINAL_LOAN);
                    }
                    //第三方有(成功)，本系统也有记录此账务（发生此种情况可能是对账文件中包含 其他日期的 账务）
                    else {
                        if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0 && Constants.ORDER_STATUS_SUCCESS == lnOrders.get(0).getStatus()
                                && lnOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0 ) {

                            //对账一致
                            String info = "{" + orderNo + "|" + detail.getTxnType()
                                    + "|交易成功}对账一致";
                            checkAccountService.checkAccountIsMatch(orderNo, lnOrders.get(0).getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, null);
                        } else {
                            //本地订单处理中，但三方已成功 调用成功业务
                            if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0 && Constants.ORDER_STATUS_PAYING == lnOrders.get(0).getStatus()
                                    && lnOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0 ) {

                                //借款方业务处理
                                BsPayOrders bsPayOrders = new BsPayOrders();
                                bsPayOrders.setOrderNo(detail.getPayOrderNo());
                                bsPayOrders.setAmount(lnOrders.get(0).getAmount());
                                bsPayOrders.setStatus(lnOrders.get(0).getStatus());
                                bsPayOrders.setSubAccountId(lnOrders.get(0).getSubAccountId());

                                AccountDetail accountDetail = new AccountDetail();
                                accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderNo(detail.getPayOrderNo());
                                accountDetail.setMpOrderNo(detail.getBaofooOrderNo());
                                accountDetail.setReqTime(DateUtil.parseDateTime(detail.getCreateTime()));
                                accountDetail.setSettleTime(DateUtil.parseDate(detail.getLiquidationDate()));
                                accountDetail.setTradeType(detail.getTxnType());
                                accountDetail.setOrderSource(Constants.CHANNEL_BAOFOO);
                                accountDetail.setCurrency("RMB");
                                accountDetail.setFee(lnOrders.get(0).getAmount());
                                accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                accountDetail.setOrderRemark(AmountTransType.getEnumByCode(detail.getTxnType()).getDescription());
                                checkAccountService.checkAccount4Paying(bsPayOrders, accountDetail, OrderStatus.SUCCESS.getCode(), null);

                            } else {
                                //对账不一致
                                String info = "{" + detail.getPayOrderNo() + "|"
                                        + detail.getTxnType()
                                        + "|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配";

                                if (CollectionUtils.isNotEmpty(lnOrders) && lnOrders.size() > 0) {
                                    checkAccountService.checkAccountIsUnMatch(detail.getPayOrderNo(), lnOrders.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(lnOrders.get(0).getStatus()), "交易成功",
                                            null, "宝付对账不一致", null, Constants.TRANSTERMINAL_LOAN);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    
    /**
     * 合作方对账文件生成
     */
    @Override
    public void generatePartnerDailyActFile() {

    }
   
    @Override
	public void generateMainCheckGacha(String merchantNo) {
		// 当前商户号 已生成过指定日期的对账结果 ，不再生成
    	String checkDate = com.pinting.business.util.DateUtil.getDate(DateUtil.addDays(new Date(), -1));
    	List<BsSysDailyCheckGacha> list = bsSysDailyCheckGachaMapper.selectSysDailyCheckGacha(merchantNo, checkDate);
    	
    	if (CollectionUtils.isEmpty(list)) {
    		BsSysDailyCheckGacha sysDailyCheckGacha = fillSysDailyCheckGacha(Constants.CHANNEL_BAOFOO, merchantNo);
        	// 余额提现
        	Double balanceWithdrawSuccAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_BALANCE_WITHDRAW, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int balanceWithdrawSuccCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_BALANCE_WITHDRAW, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.PAID_BALANCE_WITHDRAW.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(balanceWithdrawSuccAmt);
        	sysDailyCheckGacha.setTransferSuccCount(balanceWithdrawSuccCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 奖励金提现
        	Double bonusWithdrawSuccAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_BONUS_WITHDRAW, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int bonusWithdrawSuccCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_BONUS_WITHDRAW, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.PAID_BONUS_WITHDRAW.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(bonusWithdrawSuccAmt);
        	sysDailyCheckGacha.setTransferSuccCount(bonusWithdrawSuccCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.BGW.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
          	// 宝付代付到归集户
        	Double baofoo2HfbankSuccAmt = lnPayOrdersMapper.sumCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, Constants.SYS_WITHDRAW_2_DEP_REPAY_CARD,
        			Constants.ORDER_STATUS_SUCCESS, null, null, null, checkDate); 
        	int baofoo2HfbankSuccCount = lnPayOrdersMapper.countCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, Constants.SYS_WITHDRAW_2_DEP_REPAY_CARD, 
        			Constants.ORDER_STATUS_SUCCESS, null, null, null, checkDate); 
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.PAID_2_DEP_REPAY_CARD.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(baofoo2HfbankSuccAmt);
        	sysDailyCheckGacha.setTransferSuccCount(baofoo2HfbankSuccCount);
        	sysDailyCheckGacha.setPartnerCode(null);
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 合作方营收划转
        	// 赞分期营收
        	Double sysZanRevenueAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_PARTNER_REVENUE, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int sysZanRevenueCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_PARTNER_REVENUE, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sysZanRevenueAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sysZanRevenueCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);	
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 云贷营收
         	Double sysYunRevenueAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_YUN_REPAY_REVENUE,
         			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int sysYunRevenueCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_YUN_REPAY_REVENUE, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
         	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sysYunRevenueAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sysYunRevenueCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 7贷营收
         	Double sys7RevenueAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_SEVEN_REPAY_REVENUE, 
         			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int sys7RevenueCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_SEVEN_REPAY_REVENUE, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
         	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sys7RevenueAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sys7RevenueCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);	
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 赞时贷营收
         	Double sysZsdRevenueAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_ZSD_REPAY_REVENUE, 
         			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int sysZsdRevenueCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_ZSD_REPAY_REVENUE,
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
         	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sysZsdRevenueAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sysZsdRevenueCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
           	// 合作方重复还款划转
        	// 云贷重复还款
        	Double sysYunRepeatRevenueAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_YUN_REPEAT, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int sysYunRepeatRevenueCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_YUN_REPEAT, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REPEAT_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sysYunRepeatRevenueAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sysYunRepeatRevenueCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
         	// 7贷重复还款
        	Double sys7RepeatRevenueAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_SEVEN_REPEAT, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int sys7RepeatRevenueCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_SEVEN_REPEAT, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REPEAT_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sys7RepeatRevenueAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sys7RepeatRevenueCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
         	// 赞时贷重复还款
        	Double sysZsdRepeatRevenueAmt = bsPayOrdersMapper.sumCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_ZSD_REPEAT, 
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	int sysZsdRepeatRevenueCount = bsPayOrdersMapper.countCheckAccountOrders(Constants.CHANNEL_BAOFOO, Constants.TRANS_SYS_ZSD_REPEAT,
        			Constants.ORDER_STATUS_SUCCESS, checkDate);
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_REPEAT_REVENUE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sysZsdRepeatRevenueAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sysZsdRepeatRevenueCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 存管代偿    	
        	// 云贷合作方代偿
        	Double succYunCompensateAmt = lnCompensateMapper.sumCheckAccountCompensate(Constants.COMPENSATE_REPAYS_STATUS_SUCC, PartnerEnum.YUN_DAI_SELF.getCode());
        	int succYunCompensateCount = lnCompensateMapper.countCheckAccountCompensate(Constants.COMPENSATE_REPAYS_STATUS_SUCC, PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(succYunCompensateAmt);
        	sysDailyCheckGacha.setTransferSuccCount(succYunCompensateCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 7贷合作方代偿
        	Double succ7CompensateAmt = lnCompensateMapper.sumCheckAccountCompensate(Constants.COMPENSATE_REPAYS_STATUS_SUCC, PartnerEnum.SEVEN_DAI_SELF.getCode());
        	int succ7CompensateCount = lnCompensateMapper.countCheckAccountCompensate(Constants.COMPENSATE_REPAYS_STATUS_SUCC, PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(succ7CompensateAmt);
        	sysDailyCheckGacha.setTransferSuccCount(succ7CompensateCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	// 赞时贷合作方代偿
        	Double succZsdCompensateAmt = lnCompensateMapper.sumCheckAccountCompensate(Constants.COMPENSATE_REPAYS_STATUS_SUCC, PartnerEnum.ZSD.getCode());
        	int succZsdCompensateCount = lnCompensateMapper.countCheckAccountCompensate(Constants.COMPENSATE_REPAYS_STATUS_SUCC, PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(succZsdCompensateAmt);
        	sysDailyCheckGacha.setTransferSuccCount(succZsdCompensateCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	
        	//资产合作方线下还款
        	//赞分期线下还款
	    	long zanXStart = System.currentTimeMillis();
        	Double zanOfflineRepayAmt = lnPayOrdersMapper.sumPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.ZAN.getCode());
        	int zanOfflineRepayCount = lnPayOrdersMapper.countPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.ZAN.getCode());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(zanOfflineRepayAmt);
        	sysDailyCheckGacha.setTransferSuccCount(zanOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long zanXEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总赞分期线下还款用时:{}ms", (zanXEnd - zanXStart));
	    	
	    	//云贷线下还款
	    	long yunXStart = System.currentTimeMillis();
        	Double yunOfflineRepayAmt = lnPayOrdersMapper.sumPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.YUN_DAI_SELF.getCode());
        	int yunOfflineRepayCount = lnPayOrdersMapper.countPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(yunOfflineRepayAmt);
        	sysDailyCheckGacha.setTransferSuccCount(yunOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long yunXEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总云贷线下还款用时:{}ms", (yunXEnd - yunXStart));
	    	
        	//7贷线下还款
	    	long sevenXStart = System.currentTimeMillis();
        	Double sevenOfflineRepayAmt = lnPayOrdersMapper.sumPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.SEVEN_DAI_SELF.getCode());
        	int sevenOfflineRepayCount = lnPayOrdersMapper.countPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(sevenOfflineRepayAmt);
        	sysDailyCheckGacha.setTransferSuccCount(sevenOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long sevenXEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总7贷线下还款用时:{}ms", (sevenXEnd - sevenXStart));
	    	
        	//赞时贷线下还款
	    	long zsdXStart = System.currentTimeMillis();
        	Double zsdOfflineRepayAmt = lnPayOrdersMapper.sumPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.ZSD.getCode());
        	int zsdOfflineRepayCount = lnPayOrdersMapper.countPartnerOfflineRepay(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.CHANNEL_TRS_DK, Constants.ORDER_STATUS_SUCCESS, PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(zsdOfflineRepayAmt);
        	sysDailyCheckGacha.setTransferSuccCount(zsdOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long zsdXEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总赞时贷线下还款用时:{}ms", (zsdXEnd - zsdXStart));
	    	
        	// 存管产品还款
        	// 赞分期还款 (赞分期代扣还款-赞分期线下还款，其它合作方取值一样)
        	long zanStart = System.currentTimeMillis();
        	Double depositionRepayZanAmt = lnPayOrdersMapper.sumCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.ZAN.getCode(), null); 
        	int depositionRepayZanCount = lnPayOrdersMapper.countCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.ZAN.getCode(), null); 
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(depositionRepayZanAmt, zanOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(depositionRepayZanCount - zanOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long zanEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总赞分期还款用时:{}ms", (zanEnd - zanStart));
	    	
        	// 云贷还款
	    	long yunStart = System.currentTimeMillis();
        	Double depositionRepayYunAmt = lnPayOrdersMapper.sumCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.YUN_DAI_SELF.getCode(), null); 
        	int depositionRepayYunCount = lnPayOrdersMapper.countCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.YUN_DAI_SELF.getCode(), null); 
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(depositionRepayYunAmt, yunOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(depositionRepayYunCount - yunOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long yunEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总云贷还款用时:{}ms", (yunEnd - yunStart));
	    	
        	// 7贷还款
	       	long sevenStart = System.currentTimeMillis();
        	Double depositionRepay7Amt = lnPayOrdersMapper.sumCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.SEVEN_DAI_SELF.getCode(), null); 
        	int depositionRepay7Count = lnPayOrdersMapper.countCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.SEVEN_DAI_SELF.getCode(), null); 
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(depositionRepay7Amt, sevenOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(depositionRepay7Count - sevenOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long sevenEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总7贷还款用时:{}ms", (sevenEnd - sevenStart));
	    	
        	// 赞时贷还款
	       	long zsdStart = System.currentTimeMillis();
        	Double depositionRepayZsdAmt = lnPayOrdersMapper.sumCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.ZSD.getCode(), null); 
        	int depositionRepayZsdCount = lnPayOrdersMapper.countCheckAccountOrdersForDK(Constants.CHANNEL_BAOFOO, "REPAY", 
        			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_DK, PartnerEnum.ZSD.getCode(), null); 
        	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
        	sysDailyCheckGacha.setTransferSuccAmount(MoneyUtil.subtract(depositionRepayZsdAmt, zsdOfflineRepayAmt).doubleValue());
        	sysDailyCheckGacha.setTransferSuccCount(depositionRepayZsdCount - zsdOfflineRepayCount);
        	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
        	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
        	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
        	long zsdEnd = System.currentTimeMillis();
	    	log.info("=========主通道对账汇总赞时贷还款用时:{}ms", (zsdEnd - zsdStart));
	    	
    	}
	}

	@Override
	public void generateAssistCheckGacha(String merchantNo, Integer paymentChannelId) {
		// 当前商户号 已生成过指定日期的对账结果 ，不再生成
    	String checkDate = com.pinting.business.util.DateUtil.getDate(DateUtil.addDays(new Date(), -1));
    	List<BsSysDailyCheckGacha> list = bsSysDailyCheckGachaMapper.selectSysDailyCheckGacha(merchantNo, checkDate);
    	
    	if (CollectionUtils.isEmpty(list)) {
	    	BsSysDailyCheckGacha sysDailyCheckGacha = fillSysDailyCheckGacha(Constants.CHANNEL_BAOFOO, merchantNo);
	    	// 存管产品还款
	    	// 赞分期还款
	    	long zanStart = System.currentTimeMillis();
	    	Double depositionRepayZanAmt = lnPayOrdersMapper.sumCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS,
	    			 Constants.CHANNEL_TRS_DK, PartnerEnum.ZAN.getCode(), paymentChannelId, checkDate); 
	    	int depositionRepayZanCount = lnPayOrdersMapper.countCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS,
	    			 Constants.CHANNEL_TRS_DK, PartnerEnum.ZAN.getCode(), paymentChannelId, checkDate); 
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(depositionRepayZanAmt);
	    	sysDailyCheckGacha.setTransferSuccCount(depositionRepayZanCount);
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZAN.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	long zanEnd = System.currentTimeMillis();
	    	log.info("=========辅通道:{}对账汇总赞分期还款用时:{}ms", merchantNo, (zanEnd - zanStart));
	    	
	    	// 云贷还款
	    	long yunStart = System.currentTimeMillis();
	    	Double depositionRepayYunAmt = lnPayOrdersMapper.sumCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS, 
	    			 Constants.CHANNEL_TRS_DK, PartnerEnum.YUN_DAI_SELF.getCode(), paymentChannelId, checkDate); 
	    	int depositionRepayYunCount = lnPayOrdersMapper.countCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS,
	    			 Constants.CHANNEL_TRS_DK, PartnerEnum.YUN_DAI_SELF.getCode(), paymentChannelId, checkDate); 
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(depositionRepayYunAmt);
	    	sysDailyCheckGacha.setTransferSuccCount(depositionRepayYunCount);
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.YUN_DAI_SELF.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	long yunEnd = System.currentTimeMillis();
	    	log.info("=========辅通道:{}对账汇总云贷还款用时:{}ms", merchantNo, (yunEnd - yunStart));
	    	
	    	// 7贷还款
	    	long sevenStart = System.currentTimeMillis();
	    	Double depositionRepay7Amt = lnPayOrdersMapper.sumCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS,
	    			Constants.CHANNEL_TRS_DK, PartnerEnum.SEVEN_DAI_SELF.getCode(), paymentChannelId, checkDate); 
	    	int depositionRepay7Count = lnPayOrdersMapper.countCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS,
	    			Constants.CHANNEL_TRS_DK, PartnerEnum.SEVEN_DAI_SELF.getCode(), paymentChannelId, checkDate); 
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(depositionRepay7Amt);
	    	sysDailyCheckGacha.setTransferSuccCount(depositionRepay7Count);
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.SEVEN_DAI_SELF.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	long sevenEnd = System.currentTimeMillis();
	    	log.info("=========辅通道:{}对账汇总7贷还款用时:{}ms", merchantNo, (sevenEnd - sevenStart));
	    	
	    	// 赞时贷还款
	    	long zsdStart = System.currentTimeMillis();
	    	Double depositionRepayZsdAmt = lnPayOrdersMapper.sumCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS,
	    			 Constants.CHANNEL_TRS_DK, PartnerEnum.ZSD.getCode(), paymentChannelId, checkDate); 
	    	int depositionRepayZsdCount = lnPayOrdersMapper.countCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", Constants.ORDER_STATUS_SUCCESS,
	    			 Constants.CHANNEL_TRS_DK, PartnerEnum.ZSD.getCode(), paymentChannelId, checkDate); 
	    	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(depositionRepayZsdAmt);
	    	sysDailyCheckGacha.setTransferSuccCount(depositionRepayZsdCount);
	    	sysDailyCheckGacha.setPartnerCode(PartnerEnum.ZSD.getCode());
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_IN);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
	    	long zsdEnd = System.currentTimeMillis();
	    	log.info("=========辅通道:{}对账汇总赞时贷还款用时:{}ms", merchantNo, (zsdEnd - zsdStart));
	    	
	    	//辅助通道转账到主通道
	    	Double assist2MainTransAmt = lnPayOrdersMapper.sumCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", 
	    			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_TRANSFER, null, paymentChannelId, checkDate);
	    	int assist2MainTransCount = lnPayOrdersMapper.countCheckAccountOrdersForAssistDK(Constants.CHANNEL_BAOFOO, "REPAY", 
	    			Constants.ORDER_STATUS_SUCCESS, Constants.CHANNEL_TRS_TRANSFER, null, paymentChannelId, checkDate);
	      	sysDailyCheckGacha.setBusinessType(BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_ASSIST_2_MAIN.getCode());
	    	sysDailyCheckGacha.setTransferSuccAmount(assist2MainTransAmt);
	    	sysDailyCheckGacha.setTransferSuccCount(assist2MainTransCount);
	    	sysDailyCheckGacha.setPartnerCode(null);
	    	sysDailyCheckGacha.setFinancialFlag(Constants.BUSINESS_FLAG_OUT);
	    	bsSysDailyCheckGachaMapper.insertSelective(sysDailyCheckGacha);
    	}
	}
	
}