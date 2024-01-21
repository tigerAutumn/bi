package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.service.HFBankCheckAccountService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PayPathEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.HFAccountDetailVO;
import com.pinting.business.model.vo.HFAccountTotalVO;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.*;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_FundDataCheck;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_FundDataCheck;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.pay19.model.AccountDetail;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Author:      cyb
 * Date:        2017/5/10
 * Description: 恒丰出入金对账服务
 */
@Service
public class HFBankCheckAccountServiceImpl implements HFBankCheckAccountService {

    private Logger logger = LoggerFactory.getLogger(HFBankCheckAccountServiceImpl.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    @Autowired
    private SpecialJnlService specialJnlService;

    @Autowired
    private CheckAccountService checkAccountService;

    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;

    @Autowired
    private Bs19payCheckRecordMapper bs19payCheckRecordMapper;

    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;

    @Autowired
    private BsServiceFeeMapper bsServiceFeeMapper;

    @Override
    public void checkAccount(Date time) {
    	// 下载文件
        String path = download(time);
        // 解析文件
        Map<String, Object> result = parseFile(time, path);
        // 对账
        HFAccountTotalVO total = (HFAccountTotalVO) result.get("total");
        List<HFAccountDetailVO> details = (List<HFAccountDetailVO>) result.get("details");
        check(total, details, time);
    }

    /**
     * 进行出入金对账
     * @param total     对账文件汇总信息
     * @param details   对账文件明细信息
     * @param time      对账日期
     */
    private void check(HFAccountTotalVO total, List<HFAccountDetailVO> details, Date time) {
        // 1. 对账文件无数据，告警
        // 2. 对账文件存在数据，做记录，bs_19pay_check_record +
        // 3. 本地订单无数据，告警
        // 4. 本地订单有数据
        // 4.1 本地订单存在记录，且对账文件也存在记录
        // 4.1.1 本地订单状态是SUCCESS，且金额一致
        // 4.1.2 本地订单状态是PAYING，且金额一致
        // 4.1.2 本地订单状态是FAIL或者金额不一致
        // 4.2 本地订单存在记录，但对账文件不存在记录
        // 4.2.1 本地已成功
        // 4.2.2 本地处理中
        // 4.3 本地订单不存在记录，但对账文件存在记录（存在这种现象，可能是对账文件中存在了非对账日的订单）
        // 4.3.1 对账文件订单号为空
        // 4.3.2 再次查询对账订单号在本地是否存在
        // 4.3.2.1 不存在，告警
        // 4.3.2.2 存在，重复4.1操作

        logger.info("恒丰出入金对账开始：{}，汇总信息：{}", time, JSONObject.fromObject(total));
        List<HFAccountDetailVO> repeatDetailList = new ArrayList<>();
        Map<String, Integer> countMap = new HashMap<>();
        if(CollectionUtils.isEmpty(details)) {
            // 1. 对账文件无数据，告警
            logger.info("恒丰出入金对账失败：文件无明细记录，可能下载文件异常，需要您的确认，请检查");
            specialJnlService.warn4Fail(null, "恒丰出入金对账失败：无文件或文件无明细记录，可能下载文件异常，需要您的确认，请检查", null, "恒丰出入金对账文件无文件或文件无明细记录", true);
            return;
        } else {
            logger.info("对账文件存在数据，插入 bs_19pay_check_record 表");
            // 2. 对账文件存在数据，做记录，bs_19pay_check_record +
            for (HFAccountDetailVO detail: details) {
                BsPayOrders order = new BsPayOrders();
                BsPayOrdersExample bsPayOrdersExample = new BsPayOrdersExample();
                bsPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getOrderNo());
                List<BsPayOrders> bsOrders = bsPayOrdersMapper.selectByExample(bsPayOrdersExample);
                if(CollectionUtils.isEmpty(bsOrders)) {
                    LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                    lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getOrderNo());
                    List<LnPayOrders> lnPayOrderses = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                    if(!CollectionUtils.isEmpty(lnPayOrderses)) {
                        order.setCreateTime(lnPayOrderses.get(0).getCreateTime());
                        order.setUpdateTime(lnPayOrderses.get(0).getUpdateTime());
                    }
                } else {
                    order = bsOrders.get(0);
                }
                Bs19payCheckRecord record = new Bs19payCheckRecord();
                record.setCurrencyType("RMB");
                record.setOrderDesc(detail.getType() + "【行内支付通道号：" + detail.getPayPathNo() + "】");
                record.setOrderNo(detail.getOrderNo());
                record.setCommission(0d);
                record.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                record.setTranAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                record.setOrderSubmitTime(order.getCreateTime());
                record.setTradeCompanyOrderTime(order.getCreateTime());
                record.setOrderFinishTime(order.getUpdateTime());
                record.setOrderSettleTime(order.getUpdateTime());
                record.setOrderSrc(Constants.HF_CHECK_ACCOUNT_ORDER_TYPE_TOP_UP.equals(detail.getType()) ? "C-充值" : "T-提现");
                record.setPay19OrderJnl(detail.getChannelJnlNo());
//                record.setTranType(detail.getType());
                bs19payCheckRecordMapper.insertSelective(record);

                for (HFAccountDetailVO accountDetail: details) {
                    if(accountDetail.getOrderNo().equals(detail.getOrderNo())) {
                        Integer count = countMap.get(accountDetail.getOrderNo()) == null ? 0 : countMap.get(accountDetail.getOrderNo());
                        countMap.put(accountDetail.getOrderNo(), count + 1);
                        break;
                    }
                }
            }
        }
        logger.info("重复订单个数：{}", JSONArray.fromObject(countMap));
        for(HFAccountDetailVO detail: details) {
            if(countMap.get(detail.getOrderNo()) > 1) {
                repeatDetailList.add(detail);
            }
        }
        logger.info("重复订单：{}", JSONArray.fromObject(repeatDetailList));

        // 查询所有对账日产生的理财端订单 
        Date beginDate = DateUtil.parse(DateUtil.formatYYYYMMDD(time) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
        Date endDate = DateUtil.parse(DateUtil.formatYYYYMMDD(time) + " 23:59:59", "yyyy-MM-dd HH:mm:ss");
        BsPayOrdersExample orderExample = new BsPayOrdersExample();
        List<String> bsTransTypes = new ArrayList<>();
        bsTransTypes.add(Constants.TRANS_TOP_UP);
        bsTransTypes.add(Constants.TRANS_BALANCE_WITHDRAW);
        // 恒丰对账理财端新增交易类型，平台提现、平台充值
        bsTransTypes.add(Constants.TRANS_HFBANK_SYS_TOP_UP);
        bsTransTypes.add(Constants.TRANS_HFBANK_SYS_WITHDRAW);        
        orderExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate).andChannelEqualTo(Constants.CHANNEL_HFBANK).andTransTypeIn(bsTransTypes);
        List<BsPayOrders> orders = bsPayOrdersMapper.selectByExample(orderExample);

        // 查询所有对账日产生的借款端订单
        LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
        List<String> lnTransTypes = new ArrayList<>();
        lnTransTypes.add(LoanStatus.TRANS_TYPE_LOAN.getCode());
        payOrdersExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate).andChannelEqualTo(Constants.CHANNEL_HFBANK).andTransTypeIn(lnTransTypes);
        List<LnPayOrders> lnOrders = lnPayOrdersMapper.selectByExample(payOrdersExample);

        // 4. 本地订单有数据
        Map<String, String> checkedOrder = new HashMap<>();
        if(!CollectionUtils.isEmpty(orders)) {
            logger.info("本地理财端订单有数据，进行比较");
            for (BsPayOrders order: orders) {
                boolean fileExist = false;
                boolean isRepeat = false;
                for (HFAccountDetailVO detail : repeatDetailList) {
                    if(detail.getOrderNo().equals(order.getOrderNo())) {
                        isRepeat = true;
                    }
                }
                if(isRepeat) {
                    // 剔除重复订单
                    continue;
                }
                // 查询对应手续费记录
                BsServiceFeeExample feeExample = new BsServiceFeeExample();
                feeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                List<BsServiceFee> feeList = bsServiceFeeMapper.selectByExample(feeExample);
                Double localTransAmount = order.getAmount();
                if(!CollectionUtils.isEmpty(feeList)) {
                    if(Constants.TRANS_BALANCE_WITHDRAW.equals(order.getTransType())) {
                        // 理财人提现的订单金额未扣除手续费
                        Double fee = feeList.get(0).getDoneFee();
                        localTransAmount = MoneyUtil.subtract(order.getAmount(), fee).doubleValue();
                    }
                }
                for (HFAccountDetailVO detail: details) {
                    // 4.1 本地订单存在记录，且对账文件也存在记录
                    if(order.getOrderNo().equals(detail.getOrderNo())) {
                        logger.info("本地理财端订单：{} 和恒丰订单号：{} 一致，进行比较", order.getOrderNo(), detail.getOrderNo());
                        fileExist = true;
                        if(Constants.ORDER_STATUS_SUCCESS == order.getStatus() && localTransAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            logger.info("本地理财端订单：{} 和恒丰订单号：{} 一致，且本地订单成功，金额一致", order.getOrderNo(), detail.getOrderNo());
                            // 4.1.1 本地订单状态是SUCCESS，且金额一致
                            String info = "{" + order.getOrderNo() + "|" + detail.getType()
                                    + "|交易成功}对账一致";
                            checkAccountService.checkAccountIsMatch(order.getOrderNo(), order.getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, order.getSubAccountId());
                            BsPayOrders updateOrder = new BsPayOrders();
                            String payPath = PayPathEnum.find(detail.getPayPathNo())==null? null:PayPathEnum.find(detail.getPayPathNo()).getPayPathVal();
                            if(StringUtil.isNotBlank(payPath)) {
                                updateOrder.setId(order.getId());
                                updateOrder.setPayPath(payPath);
                                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                            }
                        } else if(Constants.ORDER_STATUS_PAYING == order.getStatus() && localTransAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            // 4.1.2 本地订单状态是PAYING，且金额一致，则调用响应业务通知服务，将处理中状态置为成功，同时对账一致
                            logger.info("本地理财端订单：{} 和恒丰订单号：{} 一致，且本地订单处理中，金额一致，则调用响应业务通知服务", order.getOrderNo(), detail.getOrderNo());
                            AccountDetail accountDetail = new AccountDetail();
                            accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                            accountDetail.setOrderNo(detail.getOrderNo());
                            accountDetail.setMpOrderNo(detail.getChannelJnlNo());
                            accountDetail.setReqTime(order.getCreateTime());
                            accountDetail.setSettleTime(DateUtil.parse(detail.getCheckDate() + " " + detail.getCheckTime(), "yyyyMMdd HHmmss"));
                            accountDetail.setTradeType(detail.getType());
                            accountDetail.setOrderSource(order.getChannel());
                            accountDetail.setCurrency("RMB");
                            accountDetail.setFee(0d);
                            accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
//                        accountDetail.setOrderRemark(AmountTransType.getEnumByCode(detail.getTxnType()).getDescription());
                            checkAccountService.checkAccountHf4Paying(order, accountDetail, OrderStatus.SUCCESS.getCode(), null, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), Constants.TRANSTERMINAL_FINANCE);
                        } else {
                            logger.info("本地理财端订单：{} 和恒丰订单号：{} 一致，本地订单失败或者金额不一致", order.getOrderNo(), detail.getOrderNo());
                            // 4.1.2 本地订单状态是FAIL或者金额不一致
                            String specialJnlInfo = "{" + detail.getOrderNo() + "|"
                                    + detail.getType()
                                    + "|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配";
                            
                            String info = "{" + detail.getOrderNo() + "|" + detail.getType()
                                    + "|交易成功}对账不一致：本地订单状态或金额与三方不一致";
                            		
                            checkAccountService.checkHfbankAccountIsUnMatch(detail.getOrderNo(), order.getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
                            		Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：" + String.valueOf(order.getStatus()), 
                            		"交易成功", order.getSubAccountId(), "恒丰对账不一致", order.getTransType(),
                            		Constants.TRANSTERMINAL_FINANCE, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);
                            
                        }
                        break;
                    }
                }
                // 4.2 本地订单存在记录，但对账文件不存在记录
                if(!fileExist && Constants.ORDER_STATUS_SUCCESS == order.getStatus()) {
                    logger.info("本地理财端订单：{} 存在，对账文件中不存在记录", order.getOrderNo());
                    // 4.2.1 本地已成功
                    String specialJnlInfo = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地订单成功，但三方未成功或无成功记录";
                    String info = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地或三方订单编号缺失";
                    checkAccountService.checkHfbankAccountIsUnMatch(order.getOrderNo(), order.getAmount(), 0d,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(order.getStatus()), "无", order.getSubAccountId(), "恒丰对账不一致", order.getTransType(),
                            Constants.TRANSTERMINAL_FINANCE, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), null, null, specialJnlInfo);
                    
                }
                if(!fileExist && Constants.ORDER_STATUS_PAYING == order.getStatus()) {
                    logger.info("本地理财端订单：{} 存在，对账文件中不存在记录", order.getOrderNo());
                    // 4.2.2 本地处理中
                    String specialJnlInfo = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地订单处理中，但三方未成功或无成功记录";
                    String info = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地或三方订单编号缺失";
                    checkAccountService.checkHfbankAccountIsUnMatch(order.getOrderNo(), order.getAmount(), 0d,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(order.getStatus()), "无", order.getSubAccountId(), "恒丰对账本地处理中", order.getTransType(),
                            Constants.TRANSTERMINAL_FINANCE, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), null, null, specialJnlInfo);
                    
                }
                // 添加已经对过账的订单
                checkedOrder.put(order.getOrderNo(), order.getOrderNo());
            }
        }

        if(!CollectionUtils.isEmpty(lnOrders)) {
            for (LnPayOrders order: lnOrders) {
                //绑卡、营销代付订单无需对账，此处过滤
                if (Constants.USER_USER_BIND_CARD.equals(order.getTransType()) || LoanStatus.TRANS_TYPE_UN_BIND_CARD.getCode().equals(order.getTransType()) ||
                        LoanStatus.TRANS_TYPE_MARKET.getCode().equals(order.getTransType())) {
                    continue;
                }
                boolean isRepeat = false;
                for (HFAccountDetailVO detail : repeatDetailList) {
                    if(detail.getOrderNo().equals(order.getOrderNo())) {
                        isRepeat = true;
                    }
                }
                if(isRepeat) {
                    // 剔除重复订单
                    continue;
                }

                // 查询对应手续费记录
                BsServiceFeeExample feeExample = new BsServiceFeeExample();
                feeExample.createCriteria().andOrderNoEqualTo(order.getOrderNo());
                List<BsServiceFee> feeList = bsServiceFeeMapper.selectByExample(feeExample);
                Double localTransAmount = order.getAmount();
                if(!CollectionUtils.isEmpty(feeList)) {
                    if(Constants.TRANS_BALANCE_WITHDRAW.equals(order.getTransType())) {
                        // 理财人提现的订单金额未扣除手续费
                        Double fee = feeList.get(0).getDoneFee();
                        localTransAmount = MoneyUtil.subtract(order.getAmount(), fee).doubleValue();
                    }
                }
                boolean fileExist = false;
                for (HFAccountDetailVO detail: details) {
                    // 4.1 本地订单存在记录，且对账文件也存在记录
                    if(order.getOrderNo().equals(detail.getOrderNo())) {
                        fileExist = true;
                        if(Constants.ORDER_STATUS_SUCCESS == order.getStatus() && localTransAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            logger.info("本地借款端订单：{} 和恒丰订单号：{} 一致，本地订单成功，金额一致", order.getOrderNo(), detail.getOrderNo());

                            // 4.1.1 本地订单状态是SUCCESS，且金额一致
                            String info = "{" + order.getOrderNo() + "|" + detail.getType()
                                    + "|交易成功}对账一致";
                            checkAccountService.checkAccountIsMatch(order.getOrderNo(), order.getAmount(),
                                    MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                    info, order.getSubAccountId());
                            LnPayOrders updateOrder = new LnPayOrders();
                            String payPath = PayPathEnum.find(detail.getPayPathNo())==null ? null:PayPathEnum.find(detail.getPayPathNo()).getPayPathVal();
                            if(StringUtil.isNotBlank(payPath)) {
                                updateOrder.setId(order.getId());
                                updateOrder.setPayPath(payPath);
                                lnPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                            }
                        } else if(Constants.ORDER_STATUS_PAYING == order.getStatus() && localTransAmount.compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                            // 4.1.2 本地订单状态是PAYING，且金额一致，则调用响应业务通知服务，将处理中状态置为成功，同时对账一致
                            logger.info("本地借款端订单：{} 和恒丰订单号：{} 一致，本地订单处理中，金额一致", order.getOrderNo(), detail.getOrderNo());
                            //借款方业务处理
                            BsPayOrders bsPayOrders = new BsPayOrders();
                            bsPayOrders.setOrderNo(detail.getOrderNo());
                            bsPayOrders.setAmount(order.getAmount());
                            bsPayOrders.setStatus(order.getStatus());
                            bsPayOrders.setSubAccountId(order.getSubAccountId());
                            bsPayOrders.setTransType(order.getTransType());
                            
                            AccountDetail accountDetail = new AccountDetail();
                            accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                            accountDetail.setOrderNo(detail.getOrderNo());
                            accountDetail.setMpOrderNo(detail.getChannelJnlNo());
                            accountDetail.setReqTime(order.getCreateTime());
                            accountDetail.setSettleTime(DateUtil.parse(detail.getCheckDate() + " " + detail.getCheckTime(), "yyyyMMdd HHmmss"));
                            accountDetail.setTradeType(detail.getType());
                            accountDetail.setOrderSource(Constants.CHANNEL_HFBANK);
                            accountDetail.setCurrency("RMB");
                            accountDetail.setFee(order.getAmount());
                            accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
//                        accountDetail.setOrderRemark(Constants.HF_CHECK_ACCOUNT_ORDER_TYPE_TOP_UP.equals(detail.getType()) ? "C-充值" : "T-提现");
                            LnPayOrders hfLnPayOrders = lnPayOrdersMapper.selectByOrderNoAndChannel(order.getOrderNo(), PayPlatformEnum.HFBANK.getCode(), null);
                            checkAccountService.checkAccountHf4Paying(bsPayOrders, accountDetail, OrderStatus.SUCCESS.getCode(), null, 
                            		PayPlatformEnum.HFBANK.getCode(), hfLnPayOrders==null? PartnerEnum.BGW.getCode() : hfLnPayOrders.getPartnerCode(), Constants.TRANSTERMINAL_LOAN);
                        } else {
                            logger.info("本地借款端订单：{} 和恒丰订单号：{} 一致，本地订单失败或者金额不一致", order.getOrderNo(), detail.getOrderNo());
                            // 4.1.2 本地订单状态是FAIL或者金额不一致
                            String specialJnlInfo = "{" + detail.getOrderNo() + "|"
                                    + detail.getType()
                                    + "|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配";
                            
                            String info = "{" + detail.getOrderNo() + "|" + detail.getType()
                                    + "|交易成功}对账不一致：本地订单状态或金额与三方不一致";
                            
                            checkAccountService.checkHfbankAccountIsUnMatch(detail.getOrderNo(), order
                                            .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                    "订单状态：" + String.valueOf(order.getStatus()), "交易成功", order.getSubAccountId(), "恒丰对账不一致", order.getTransType(),
                                    Constants.TRANSTERMINAL_LOAN, PayPlatformEnum.HFBANK.getCode(), order.getPartnerCode(), detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);
                        }
                        break;
                    }
                }
                // 4.2 本地订单存在记录，但对账文件不存在记录
                if(!fileExist && Constants.ORDER_STATUS_SUCCESS == order.getStatus()) {
                    logger.info("本地借款端订单：{} 存在，但对账文件不存在记录", order.getOrderNo());
                    // 4.2.1 本地已成功
                    String specialJnlInfo = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地订单成功，但三方未成功或无成功记录";
                    String info = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地或三方订单编号缺失";
                    checkAccountService.checkHfbankAccountIsUnMatch(order.getOrderNo(), order.getAmount(), 0d,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(order.getStatus()), "无", order.getSubAccountId(), "恒丰对账不一致", order.getTransType(),
                            Constants.TRANSTERMINAL_LOAN, PayPlatformEnum.HFBANK.getCode(), order.getPartnerCode(), null, null, specialJnlInfo);
                }
                if(!fileExist && Constants.ORDER_STATUS_PAYING == order.getStatus()) {
                    logger.info("本地借款端订单：{} 存在，但对账文件不存在记录", order.getOrderNo());
                    // 4.2.2 本地处理中
                    String specialJnlInfo = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地订单处理中，但三方未成功或无成功记录";
                    String info = "{" + order.getOrderNo() + "|交易结果未知}对账不一致：本地或三方订单编号缺失";
                    checkAccountService.checkHfbankAccountIsUnMatch(order.getOrderNo(), order.getAmount(), 0d,
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                            "订单状态：" + String.valueOf(order.getStatus()), "无", order.getSubAccountId(), "恒丰对账本地处理中", order.getTransType(),
                            Constants.TRANSTERMINAL_LOAN, PayPlatformEnum.HFBANK.getCode(), order.getPartnerCode(), null, null, specialJnlInfo);
                }
                // 添加已经对过账的订单
                checkedOrder.put(order.getOrderNo(), order.getOrderNo());
            }
        }


        // 4.3 本地订单不存在记录，但对账文件存在记录（存在这种现象，可能是对账文件中存在了非对账日的订单）
        for (HFAccountDetailVO detail: details) {
            boolean isRepeat = false;
            for (HFAccountDetailVO repeatDetail : repeatDetailList) {
                if(detail.getOrderNo().equals(repeatDetail.getOrderNo())) {
                    isRepeat = true;
                }
            }
            if(isRepeat) {
                // 剔除重复订单
                continue;
            }
            // 4.3.1 对账文件订单号为空
            if(StringUtil.isBlank(detail.getOrderNo())) {
                // 对账不一致告警
                String specialJnlInfo = "{" + detail.getOrderNo() + "|" + detail.getType()
                        + "|交易成功}对账不一致：三方成功但无单号(币港湾商户号)";
                
                String info = "{" + detail.getOrderNo() + "|" + detail.getType()
                + "|交易成功}对账不一致：本地或三方订单编号缺失";
                checkAccountService.checkHfbankAccountIsUnMatch(detail.getOrderNo(), 0d,
                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        "订单状态：无" , "交易成功", null, "恒丰对账不一致", null,
                		null, PayPlatformEnum.HFBANK.getCode(), null, detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);
                continue;
            }
            // 4.3.2 再次查询对账订单号在本地是否存在
            if(checkedOrder.get(detail.getOrderNo()) == null) {
                logger.info("昨日本地订单不存在记录，但对账文件存在记录（可能是对账文件中存在了非对账日的订单）：{}", detail.getOrderNo());
                BsPayOrdersExample example = new BsPayOrdersExample();
                example.createCriteria().andOrderNoEqualTo(detail.getOrderNo());
                List<BsPayOrders> payOrders = bsPayOrdersMapper.selectByExample(example);

                LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getOrderNo());
                List<LnPayOrders> lnPayOrders = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);

                // 4.3.2.1 不存在，告警
                if(CollectionUtils.isEmpty(payOrders) && CollectionUtils.isEmpty(lnPayOrders)) {
                    logger.info("昨日本地订单不存在记录，但对账文件存在记录（可能是对账文件中存在了非对账日的订单）：{}，全量本地不存在此记录", detail.getOrderNo());
                    //对账不一致
                    String specialJnlInfo = "{" + detail.getOrderNo() + "|"
                            + detail.getType()
                            + "|交易成功}对账不一致：第三方成功，但本地未记录此账务";
                    String info = "{" + detail.getOrderNo() + "|" + detail.getType()
                            + "|交易成功}对账不一致：本地或三方订单编号缺失";
                    
                    checkAccountService.checkHfbankAccountIsUnMatch(null, 0d, MoneyUtil.defaultRound(detail.getAmount()).doubleValue(),
                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：无", "交易成功", null, "恒丰对账不一致", null,
                    		null, PayPlatformEnum.HFBANK.getCode(), null, detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);
                } else {
                    // 4.3.2.2 存在，重复4.1操作
                	Date beforeDay = DateUtil.addDays(new Date(), -2);
                    String beforeTime = DateUtil.formatDateTime(beforeDay, "yyyyMMdd");
                    Date beforeCheckTime = DateUtil.parse(beforeTime, "yyyyMMdd");
                    Date beforeBeginDate = DateUtil.parse(DateUtil.formatYYYYMMDD(beforeCheckTime) + " 00:00:00", "yyyy-MM-dd HH:mm:ss");
                    if(!CollectionUtils.isEmpty(payOrders)) {
                        if(payOrders.get(0).getOrderNo().equals(detail.getOrderNo())) {
                        	
                            // 异常情况，交易类型、渠道、时间不一致
                        	if (!(PayPlatformEnum.HFBANK.getCode().equals(payOrders.get(0).getChannel()) &&
                        			Arrays.asList(new String[]{Constants.TRANS_TOP_UP, Constants.TRANS_BALANCE_WITHDRAW, Constants.TRANS_HFBANK_SYS_WITHDRAW, 
                        					Constants.TRANS_HFBANK_SYS_TOP_UP}).contains(payOrders.get(0).getTransType()) &&
                        			(beforeBeginDate.before(payOrders.get(0).getUpdateTime()) && endDate.after(payOrders.get(0).getUpdateTime())) )) {
                        		
                                String specialJnlInfo = "{" + detail.getOrderNo() + "|"
                                        + detail.getType()
                                        + "|交易成功}对账不一致：第三方成功，但本地订单信息不匹配";
                                String info = "{" + detail.getOrderNo() + "|" + detail.getType()
                                        + "|交易成功}对账不一致：本地订单信息与三方不一致";
                                
                                checkAccountService.checkHfbankAccountIsUnMatch(detail.getOrderNo(), payOrders.get(0)
                                                .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(payOrders.get(0).getStatus()), "交易成功",
                                        payOrders.get(0).getSubAccountId(), "恒丰对账不一致", payOrders.get(0).getTransType(),
                                        Constants.TRANSTERMINAL_FINANCE, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);

                        	} else {
                        		
                        		if(Constants.ORDER_STATUS_SUCCESS == payOrders.get(0).getStatus() && payOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                                    logger.info("对账一致：{}", payOrders.get(0).getOrderNo());
                                    // 4.1.1 本地订单状态是SUCCESS，且金额一致
                                    String info = "{" + payOrders.get(0).getOrderNo() + "|" + detail.getType()
                                            + "|交易成功}对账一致";
                                    checkAccountService.checkAccountIsMatch(payOrders.get(0).getOrderNo(), payOrders.get(0).getAmount(),
                                            MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                            info, payOrders.get(0).getSubAccountId());
                                    BsPayOrders updateOrder = new BsPayOrders();
                                    String payPath = PayPathEnum.find(detail.getPayPathNo())==null ? null:PayPathEnum.find(detail.getPayPathNo()).getPayPathVal();
                                    if(StringUtil.isNotBlank(payPath)) {
                                        updateOrder.setId(payOrders.get(0).getId());
                                        updateOrder.setPayPath(payPath);
                                        bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                                    }
                                } else if(Constants.ORDER_STATUS_PAYING == payOrders.get(0).getStatus() && payOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
                                    logger.info("本地订单处理中，需要调用分发器：{}", payOrders.get(0).getOrderNo());
                                    // 4.1.2 本地订单状态是PAYING，且金额一致，则调用相应业务分发器，将处理中状态置为成功，同时对账一致
                                    AccountDetail accountDetail = new AccountDetail();
                                    accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
                                    accountDetail.setOrderNo(detail.getOrderNo());
                                    accountDetail.setMpOrderNo(detail.getChannelJnlNo());
                                    accountDetail.setReqTime(payOrders.get(0).getCreateTime());
                                    accountDetail.setSettleTime(DateUtil.parse(detail.getCheckDate() + " " + detail.getCheckTime(), "yyyyMMdd HHmmss"));
                                    accountDetail.setTradeType(detail.getType());
                                    accountDetail.setOrderSource(payOrders.get(0).getChannel());
                                    accountDetail.setCurrency("RMB");
                                    accountDetail.setFee(0d);
                                    accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
//                            accountDetail.setOrderRemark(AmountTransType.getEnumByCode(detail.getTxnType()).getDescription());
                                    checkAccountService.checkAccountHf4Paying(payOrders.get(0), accountDetail, OrderStatus.SUCCESS.getCode(), null, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), Constants.TRANSTERMINAL_FINANCE);

                                } else {
                                    logger.info("对账不一致：{}，本地订单状态是FAIL或者金额不一致", payOrders.get(0).getOrderNo());
                                    // 4.1.2 本地订单状态是FAIL或者金额不一致
                                    String specialJnlInfo = "{" + detail.getOrderNo() + "|"
                                            + detail.getType()
                                            + "|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配";
                                    String info = "{" + detail.getOrderNo() + "|" + detail.getType()
                                            + "|交易成功}对账不一致：本地订单状态或金额与三方不一致";
                                    
                                    checkAccountService.checkHfbankAccountIsUnMatch(detail.getOrderNo(), payOrders.get(0)
                                                    .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态：" + String.valueOf(payOrders.get(0).getStatus()), "交易成功",
                                            payOrders.get(0).getSubAccountId(), "恒丰对账不一致", payOrders.get(0).getTransType(),
                                            Constants.TRANSTERMINAL_FINANCE, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);
                                }
                        		
                        	}
                        }
                    } else if(!CollectionUtils.isEmpty(lnPayOrders)) {
                        if(lnPayOrders.get(0).getOrderNo().equals(detail.getOrderNo())) {
                        	// 异常情况，交易类型、渠道、时间不一致
                        	if (!(PayPlatformEnum.HFBANK.getCode().equals(lnPayOrders.get(0).getChannel()) &&
                        			Arrays.asList(new String[]{Constants.TRANS_TOP_UP, Constants.TRANS_BALANCE_WITHDRAW, Constants.TRANS_HFBANK_SYS_WITHDRAW, 
                        					Constants.TRANS_HFBANK_SYS_TOP_UP}).contains(lnPayOrders.get(0).getTransType()) &&
                        			(beforeBeginDate.before(lnPayOrders.get(0).getUpdateTime()) && endDate.after(lnPayOrders.get(0).getUpdateTime())) )) {
                        		
                                String specialJnlInfo = "{" + detail.getOrderNo() + "|"
                                        + detail.getType()
                                        + "|交易成功}对账不一致：第三方成功，但本地订单信息不匹配";
                                String info = "{" + detail.getOrderNo() + "|" + detail.getType()
                                        + "|交易成功}对账不一致：本地订单信息与三方不一致";
                                
                                checkAccountService.checkHfbankAccountIsUnMatch(detail.getOrderNo(), lnPayOrders.get(0)
                                                .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                        "订单状态：" + String.valueOf(lnPayOrders.get(0).getStatus()), "交易成功",
                                        lnPayOrders.get(0).getSubAccountId(), "恒丰对账不一致", lnPayOrders.get(0).getTransType(),
                                        Constants.TRANSTERMINAL_FINANCE, PayPlatformEnum.HFBANK.getCode(), PartnerEnum.BGW.getCode(), detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);

                        	} else {
                        	
	                            if(Constants.ORDER_STATUS_SUCCESS == lnPayOrders.get(0).getStatus() && lnPayOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
	                                logger.info("对账一致：{}", lnPayOrders.get(0).getOrderNo());
	                                // 4.1.1 本地订单状态是SUCCESS，且金额一致
	                                String info = "{" + lnPayOrders.get(0).getOrderNo() + "|" + detail.getType()
	                                        + "|交易成功}对账一致";
	                                checkAccountService.checkAccountIsMatch(lnPayOrders.get(0).getOrderNo(), lnPayOrders.get(0).getAmount(),
	                                        MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
	                                        info, lnPayOrders.get(0).getSubAccountId());
	                                LnPayOrders updateOrder = new LnPayOrders();
	                                String payPath = PayPathEnum.find(detail.getPayPathNo())==null ? null:PayPathEnum.find(detail.getPayPathNo()).getPayPathVal();
	                                if(StringUtil.isNotBlank(payPath)) {
	                                    updateOrder.setId(payOrders.get(0).getId());
	                                    updateOrder.setPayPath(payPath);
	                                    lnPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
	                                }
	                            } else if(Constants.ORDER_STATUS_PAYING == lnPayOrders.get(0).getStatus() && lnPayOrders.get(0).getAmount().compareTo(MoneyUtil.defaultRound(detail.getAmount()).doubleValue()) == 0) {
	                                // 4.1.2 本地订单状态是PAYING，且金额一致，则调用相应业务分发器，将处理中状态置为成功，同时对账一致
	                                logger.info("本地订单处理中，需要调用分发器：{}", lnPayOrders.get(0).getOrderNo());
	                                //借款方业务处理
	                                BsPayOrders bsPayOrders = new BsPayOrders();
	                                bsPayOrders.setOrderNo(detail.getOrderNo());
	                                bsPayOrders.setAmount(lnPayOrders.get(0).getAmount());
	                                bsPayOrders.setStatus(lnPayOrders.get(0).getStatus());
	                                bsPayOrders.setSubAccountId(lnPayOrders.get(0).getSubAccountId());
	                                bsPayOrders.setTransType(lnPayOrders.get(0).getTransType());
	                                
	                                AccountDetail accountDetail = new AccountDetail();
	                                accountDetail.setAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
	                                accountDetail.setOrderNo(detail.getOrderNo());
	                                accountDetail.setMpOrderNo(detail.getChannelJnlNo());
	                                accountDetail.setReqTime(lnPayOrders.get(0).getCreateTime());
	                                accountDetail.setSettleTime(DateUtil.parse(detail.getCheckDate() + " " + detail.getCheckTime(), "yyyyMMdd HHmmss"));
	                                accountDetail.setTradeType(detail.getType());
	                                accountDetail.setOrderSource(lnPayOrders.get(0).getChannel());
	                                accountDetail.setCurrency("RMB");
	                                accountDetail.setFee(0d);
	                                accountDetail.setSettleAmount(MoneyUtil.defaultRound(detail.getAmount()).doubleValue());
	//                        accountDetail.setOrderRemark(AmountTransType.getEnumByCode(detail.getTxnType()).getDescription());
	                                LnPayOrders hfLnPayOrders = lnPayOrdersMapper.selectByOrderNoAndChannel(lnPayOrders.get(0).getOrderNo(), PayPlatformEnum.HFBANK.getCode(), null);
	                                checkAccountService.checkAccountHf4Paying(bsPayOrders, accountDetail, OrderStatus.SUCCESS.getCode(), null, PayPlatformEnum.HFBANK.getCode(),
	                                		hfLnPayOrders==null? PartnerEnum.BGW.getCode() : hfLnPayOrders.getPartnerCode(), Constants.TRANSTERMINAL_LOAN);
	                            } else {
	                                logger.info("对账不一致：{}，本地订单状态是FAIL或者金额不一致", lnPayOrders.get(0).getOrderNo());
	                                // 4.1.2 本地订单状态是FAIL或者金额不一致
	                                String specialJnlInfo = "{" + detail.getOrderNo() + "|"
	                                        + detail.getType()
	                                        + "|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配";
	                                String info = "{" + detail.getOrderNo() + "|" + detail.getType()
	                                        + "|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配";
	                                
	                                checkAccountService.checkHfbankAccountIsUnMatch(detail.getOrderNo(), lnPayOrders.get(0)
	                                                .getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
	                                        "订单状态：" + String.valueOf(lnPayOrders.get(0).getStatus()), "交易成功",
	                                        lnPayOrders.get(0).getSubAccountId(), "恒丰对账不一致", lnPayOrders.get(0).getTransType(),
	                                        Constants.TRANSTERMINAL_LOAN, PayPlatformEnum.HFBANK.getCode(), lnPayOrders.get(0).getPartnerCode(), detail.getOrderNo(), Constants.ORDER_TRANS_CODE_SUCCESS, specialJnlInfo);
	                            }
                        	}
                        }
                    }

                }
            }

        }

        // 4.4 针对重复订单进行告警
        if(!CollectionUtils.isEmpty(repeatDetailList)) {
            for (HFAccountDetailVO detail : repeatDetailList) {
                logger.info("对账不一致，恒丰对账文件存在重复订单：{}", detail.getOrderNo());
                BsPayOrders order = new BsPayOrders();
                BsPayOrdersExample bsPayOrdersExample = new BsPayOrdersExample();
                bsPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getOrderNo());
                List<BsPayOrders> payOrders = bsPayOrdersMapper.selectByExample(bsPayOrdersExample);
                if(CollectionUtils.isEmpty(payOrders)) {
                    LnPayOrdersExample lnPayOrdersExample = new LnPayOrdersExample();
                    lnPayOrdersExample.createCriteria().andOrderNoEqualTo(detail.getOrderNo());
                    List<LnPayOrders> lnPayOrders = lnPayOrdersMapper.selectByExample(lnPayOrdersExample);
                    if(!CollectionUtils.isEmpty(lnPayOrders)) {
                        order.setStatus(lnPayOrders.get(0).getStatus());
                    }
                } else {
                    order = payOrders.get(0);
                }
                // 对账不一致，恒丰对账文件存在重复订单
                String info = "{" + detail.getOrderNo() + "|"  + detail.getType() + "|交易处理未知}对账不一致：此订单是重复订单";
                checkAccountService.checkAccountIsUnMatch(detail.getOrderNo(), detail.getAmount(), MoneyUtil.defaultRound(detail.getAmount()).doubleValue(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                        "订单状态：" + order.getStatus(), "交易处理未知",
                        order.getSubAccountId(), "恒丰对账不一致");
            }
        }

        logger.info("恒丰出入金对账结束：{}", time);
    }

    /**
     * 解析出入金对账文件
     * @param time  对账日期
     * @param path  路径
     * @return  details-明细-List<HFAccountDetailVO>；total-汇总-HFAccountTotalVO
     */
    private Map<String, Object> parseFile(Date time, String path) {
        logger.info("恒丰出入金对账文件解析开始：{}", time);

        // 具体解析
//        UncompressUtil.uncompress(path, FilePath.HF_CHECK_FILE_PATH.getCode(), "hf_ct", time);

        ArrayList<String> allFileName = new ArrayList<String>();
        try {
            // 先指定压缩档的位置和档名，建立FileInputStream对象
            FileInputStream fins = new FileInputStream(path);
            // 将fins传入ZipInputStream中
            ZipInputStream zins = new ZipInputStream(fins);
            ZipEntry ze = null;
            byte[] ch = new byte[256];
            while ((ze = zins.getNextEntry()) != null) {
                File zfile = new File(GlobEnvUtil.get("baofoo.down.accountFile.fiPath") + DateUtil.formatYYYYMMDD(time)+"_hf_ct.txt");
                File fpath = new File(zfile.getParentFile().getPath());
                if (ze.isDirectory()) {
                    if (!zfile.exists())
                        zfile.mkdirs();
                    zins.closeEntry();
                } else {
                    if (!fpath.exists())
                        fpath.mkdirs();
                    FileOutputStream fouts = new FileOutputStream(zfile);
                    int i;
                    allFileName.add(zfile.getAbsolutePath());
                    while ((i = zins.read(ch)) != -1)
                        fouts.write(ch, 0, i);
                    zins.closeEntry();
                    fouts.close();
                }
            }
            fins.close();
            zins.close();
        } catch (Exception e) {
            logger.error("Extract error:" + e.getMessage());
        }
     
        long start = System.currentTimeMillis();
        Map<String, Object> result = this.analysisAccountingFile(GlobEnvUtil.get("baofoo.down.accountFile.fiPath") + DateUtil.formatYYYYMMDD(time) + "_" + "hf_ct" + ".txt");
        long end = System.currentTimeMillis();
        logger.info("恒丰出入金对账文件解析结束，用时:{}ms", (end - start));
        return result;
    }

    /**
     * 解析出入金对账文件
     * @param filePath 对账文件路径
     * @return
     */
    private Map<String, Object> analysisAccountingFile(String filePath) {
        Map<String, Object> result = new HashMap<>();
        List<HFAccountDetailVO> details = new ArrayList<>();
        HFAccountTotalVO total = new HFAccountTotalVO();
        //对账文件地址
        File file = new File(filePath);
        int row = 0;
        if (file.isFile() && file.exists()) { //判断文件是否存在
            logger.info("恒丰对账文件存在：{}", file);
            InputStreamReader read = null;//考虑到编码格式
            try {
                read = new InputStreamReader(new FileInputStream(file), "utf-8");
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt;
                //行读取
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    row++;
                    String[] account = lineTxt.split("\\|");
                    if (account.length > 1) {
                        logger.info("对账文件每一行数据按照|分隔之后的记录：{}", account);
                        // 第一行是汇总信息
                        if (row == 1) {
                            total.setPlatNo(account[0]);
                            total.setCheckDate(account[1]);
                            total.setSuccNum(Integer.valueOf(account[2]));
                            total.setSuccAmount(MoneyUtil.defaultRound(account[3]).doubleValue());
                            continue;
                        }

                        // 明细信息添加入列表（以|分隔）
                        HFAccountDetailVO detail = new HFAccountDetailVO();
                        detail.setPlatNo(account[0]);
                        detail.setCheckDate(account[1]);
                        detail.setCheckTime(account[2]);
                        detail.setOrderNo(account[3]);
                        detail.setAmount(MoneyUtil.defaultRound(account[4]).doubleValue());
                        detail.setType(account[5]);
                        detail.setChannelNo(account[6]);
                        detail.setChannelJnlNo(account[7]);
                        detail.setPayPathNo(account.length>=9?account[8]:"");
                        details.add(detail);
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
        logger.info("恒丰解析出入金对账文件明细列表：{}", JSONArray.fromObject(details));

        result.put("details", details);
        result.put("total", total);
        return result;
    }

    /**
     * 下载出入金对账文件
     * @param time  对账日期
     * @return  文件下载路径（包含文件名）
     */
    private String download(Date time) {
        logger.info("恒丰出入金对账文件下载开始：{}", time);
        String path = "";
        B2GReqMsg_HFBank_FundDataCheck req = new B2GReqMsg_HFBank_FundDataCheck();
        req.setPartner_trans_date(new Date());
        req.setPartner_trans_time(new Date());
        req.setPrecheck_flag(Constants.HF_PRECHECK_FLAG_NO);  // 0-不是预对账；1-是预对账
        req.setPaycheck_date(time);
        req.setOrder_no(Util.generateUserTransNo4BaoFoo());
        logger.info("恒丰出入金对账文件下载请求信息：{}", JSONObject.fromObject(req));
        B2GResMsg_HFBank_FundDataCheck res;
        try {
            res = hfbankTransportService.fundDataCheck(req);
            logger.info("恒丰出入金对账文件下载返回信息：{}", JSONObject.fromObject(res));
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                logger.info("恒丰出入金对账文件下载成功：{}", JSONObject.fromObject(res));
                path = res.getDestFileName();
            } else {
                logger.error("恒丰出入金对账文件下载失败：{}", StringUtil.isBlank(res.getRemsg()) ? res.getResMsg() : res.getRemsg());
                // 告警 
                //specialJnlService.warn4Fail(null, "【恒丰账户出入金对账】,失败：出入金对账文件下载失败", "", "出入金对账文件查询失败",false);
            }
        } catch (Exception e) {
            logger.error("恒丰出入金对账文件下载失败：{}", e.getMessage());
            //告警 
            //specialJnlService.warn4Fail(null, "【恒丰账户出入金对账】,失败：出入金对账文件下载失败", "", "出入金对账文件查询失败",false);
            e.printStackTrace();
        }
        logger.info("恒丰出入金对账文件下载结束");
        return path;
    }
}
