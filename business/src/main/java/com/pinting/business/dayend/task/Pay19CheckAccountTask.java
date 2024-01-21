package com.pinting.business.dayend.task;

import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.dao.Bs19payCheckRecordMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.*;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.B2GReqMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.B2GResMsg_Pay4Another_QueryCheckAccountFile;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.pay19.model.AccountDetail;
import com.pinting.gateway.out.service.Pay19TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * 19付对账
 * @Project: business
 * @Title: CheckAccountJob.java
 * @author dingpf
 * @date 2015-11-24 上午9:37:33
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class Pay19CheckAccountTask {
    private Logger                   log = LoggerFactory.getLogger(Pay19CheckAccountTask.class);
    @Autowired
    private Bs19payCheckRecordMapper bs19payCheckRecordMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private Pay19TransportService pay19TransportService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private CheckAccountService checkAccountService;
    
    

	/**
     * 任务执行
     */
    protected void execute() {
    	//余额购买对账直接置为成功
    	balanceBuyCheckAccount();
    	//19付对账
        checkAccountJob();
        //系统购买前置校验，若有未对账成功的产品，需告警
        checkProductAmount();
    }

    private void checkProductAmount() {
    	try {
    		log.info("==================定时任务{系统购买前置校验}开始====================");
    		Date checkDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));//对应的产品起息日
        	
        	BsSubAccountExample accountExample4Succ = new BsSubAccountExample();
        	accountExample4Succ.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG)
        		.andStatusEqualTo(Constants.SUBACCOUNT_STATUS_FINANCING)
        		.andInterestBeginDateEqualTo(checkDate)
        		.andCheckStatusEqualTo("SUCCESS");
        	int succCount = bsSubAccountMapper.countByExample(accountExample4Succ);
        	
        	BsSubAccountExample accountExample4All = new BsSubAccountExample();
        	accountExample4All.createCriteria().andProductTypeEqualTo(Constants.PRODUCT_TYPE_REG)
        		.andStatusEqualTo(Constants.SUBACCOUNT_STATUS_FINANCING)
        		.andInterestBeginDateEqualTo(checkDate);
        	int allCount = bsSubAccountMapper.countByExample(accountExample4All);
        	int unSuccCount = allCount - succCount;
        	if(unSuccCount != 0){
        		log.info("==================定时任务{系统购买前置校验}有"+ unSuccCount +"笔对账状态未成功的理财，将影响系统购买====================");
        		specialJnlService.warn4Fail(null, "定时任务{系统购买前置校验}：有"+ unSuccCount +"笔对账状态未成功的理财，将影响系统购买，请检查", null, unSuccCount +"笔理财购买将缺失",true);
        	}
        	log.info("==================定时任务{系统购买前置校验}结束====================");
		} catch (Exception e) {
			log.error("==================定时任务{系统购买前置校验}执行异常====================");
			e.printStackTrace();
		}
		
	}

	/**
     * 余额购买对账直接置为成功
     */
    private void balanceBuyCheckAccount() {
		try {
			log.info("==================定时任务{19付对账}余额购买对账直接置为成功====================");
			Date checkDate = DateUtil.addDays(new Date(), -1);//对账日期
			Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
            Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
            BsPayOrdersExample orderExample = new BsPayOrdersExample();
            orderExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate)
            .andTransTypeEqualTo(Constants.TRANS_BALANCE_BUY_PRODUCT).andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS);
            //获得昨天所有成功的余额购买
            List<BsPayOrders> orders = bsPayOrdersMapper.selectByExample(orderExample);
			if(orders!=null && orders.size()>0){
				for (BsPayOrders order : orders) {
					Integer subAccountId = order.getSubAccountId();
					//更新子账户表投资产品对账标志
			        if(subAccountId != null){
			        	BsSubAccount bsSubAccount = bsSubAccountMapper.selectByPrimaryKey(subAccountId);
			        	String checkStatus = bsSubAccount.getCheckStatus();
			        	BsSubAccount subAcTemp = new BsSubAccount();
			        	subAcTemp.setId(subAccountId);
			        	if(!StringUtil.isEmpty(checkStatus) 
			        			&& Constants.CHECK_ACCOUNT_STATUS_FAIL.equals(checkStatus)){
			        		subAcTemp.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_FAIL_2_SUCCESS);
			        	}else{
			        		subAcTemp.setCheckStatus(Constants.CHECK_ACCOUNT_STATUS_SUCCESS);
			        	}
			            bsSubAccountMapper.updateByPrimaryKeySelective(subAcTemp);
			        }
				}
			}
		} catch (Exception e) {
			log.error("==================定时任务{19付对账}执行19付余额购买对账失败====================", e);
            //告警
            specialJnlService.warn4Fail(null, "定时任务{19付对账}余额购买对账失败：" + StringUtil.left(e.getMessage(), 20), null, "19付对账余额购买",true);
		}
	}

	/**
     * 19付对账
     */
    private void checkAccountJob() {
        try {
            log.info("==================定时任务{19付对账}获取19付对账文件====================");
            Date checkDate = DateUtil.addDays(new Date(), -1);//对账日期
            B2GReqMsg_Pay4Another_QueryCheckAccountFile request = new B2GReqMsg_Pay4Another_QueryCheckAccountFile();
            request.setCheckDate(checkDate);
            B2GResMsg_Pay4Another_QueryCheckAccountFile result = pay19TransportService.queryCheckAccountFile(request);
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(result.getResCode())) {
                log.info("==================定时任务{19付对账}开始进行19付明细对账====================");
                //总账
                //TotalAccount totalAccount = result.getTotalAccount();
                //明细账
                List<AccountDetail> details = result.getAccountDetails();
                //插入对账记录表
                if (!CollectionUtils.isEmpty(details)) {
                    for (AccountDetail detail : details) {
                        Bs19payCheckRecord record = new Bs19payCheckRecord();
                        record.setCommission(detail.getFee());
                        record.setCurrencyType(detail.getCurrency());
                        record.setOrderDesc(detail.getOrderRemark());
                        record.setOrderFinishTime(detail.getFinishTime());
                        record.setOrderNo(detail.getOrderNo());
                        record.setOrderSettleTime(detail.getSettleTime());
                        record.setOrderSrc(detail.getOrderSource());
                        record.setOrderSubmitTime(detail.getSubmitTime());
                        record.setPay19OrderJnl(detail.getMpOrderNo());
                        record.setSettleAmount(detail.getSettleAmount());
                        record.setTradeCompanyOrderTime(detail.getReqTime());
                        record.setTranAmount(detail.getAmount());
                        record.setTranType(detail.getTradeType());
                        bs19payCheckRecordMapper.insertSelective(record);
                    }
                }else{
                	//对账文件无明细记录（此种情况可能有问题，需要告警确认）
                	log.info("==================定时任务{19付对账}对账失败：文件无明细记录，可能下载文件异常，需要您的确认，请检查====================");
                	//告警
                    //specialJnlService.warn4Fail(null, "定时任务{19付对账}对账失败：文件无明细记录，可能下载文件异常，需要您的确认，请检查", null, "19付对账文件无明细",true);
                    return;
                	
                }
                //查询对账日期范围内本地订单
                Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
                Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
                BsPayOrdersExample orderExample = new BsPayOrdersExample();
                orderExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate)
                .andChannelEqualTo(Constants.CHANNEL_PAY19);
                List<BsPayOrders> orders = bsPayOrdersMapper.selectByExample(orderExample);

                //根据本地订单进行明细对账
                Map<String, Object> isCheckedMap = new HashMap<String, Object>();
                for (BsPayOrders order : orders) {
                	//余额购买产品的订单无需对账，此处过滤
                	if(Constants.TRANS_BALANCE_BUY_PRODUCT.equals(order.getTransType())){
                		continue;
                	}
                	
                    String localOrderNo = order.getOrderNo();//订单号
                    int localStatus = order.getStatus();//状态
                    Double localAmount = order.getAmount();//订单金额
                    
                    //本地前一天订单尚在处理中的情况，需要告警
                    /*if(Constants.ORDER_STATUS_PAYING == localStatus){
                    	specialJnlService.warn4Fail(localAmount, "定时任务{19付对账}订单号["+localOrderNo+"]状态处理中，需监控", localOrderNo, "定时任务{19付对账}");
                    }*/
                    
                    boolean isExist = false;
                    if (!CollectionUtils.isEmpty(details)) {
                        for (AccountDetail detail : details) {
                            if (!localOrderNo.equals(detail.getOrderNo())) {
                                continue;
                            } else {
                                isExist = true;
                                //对账文件有（表示三方成功），本地订单成功，且金额一致，则对账一致
                                if (Constants.ORDER_STATUS_SUCCESS == localStatus
                                    && localAmount.compareTo(detail.getAmount()) == 0) {
                                    //交易类型暂不校验
                                	/*String payType = detail.getTradeType();
                                    if ("代付".equals(payType)) {
                                        payType = Constants.CHANNEL_TRS_DF;
                                    } else if ("快捷支付".equals(payType)) {
                                        payType = Constants.CHANNEL_TRS_QUICKPAY;
                                    } else if ("转账".equals(payType)) {
                                        payType = Constants.CHANNEL_TRS_TRANSFER;
                                    }
                                    //对账不一致（交易类型不一致）
                                    if (!order.getChannelTransType().equals(payType)) {
                                        String info = "{" + detail.getOrderNo() + "|"
                                                      + detail.getTradeType()
                                                      + "|交易成功}对账不一致：交易类型不匹配";
                                        checkAccountIsUnMatch(detail.getOrderNo(), localAmount,
                                            detail.getAmount(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT,
                                            info, "交易成功", "交易成功");
                                        break;
                                    }*/
                                    //对账一致
                                    String info = "{" + detail.getOrderNo() + "|"
                                                  + detail.getTradeType() + "|交易成功}对账一致";
                                    checkAccountService.checkAccountIsMatch(detail.getOrderNo(), localAmount,
                                        detail.getAmount(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                        info, order.getSubAccountId());
                                }
                                //对账文件有（表示三方成功），本地订单失败或金额不匹配，则对账不一致
                                else {
                                	//本地订单处理中，但三方已成功，如果订单是卡购买，则需调用 购买成功 服务
                                	if(Constants.ORDER_STATUS_PAYING == localStatus
                                            && localAmount.compareTo(detail.getAmount()) == 0){
                                		
                                		checkAccountService.checkAccount4Paying(order, detail, OrderStatus.SUCCESS.getCode(), null);
                                		
                                	}else{
                                		//对账不一致
                                        String info = "{" + detail.getOrderNo() + "|"
                                                      + detail.getTradeType()
                                                      + "|交易成功}对账不一致：本地订单失败或金额不匹配";
                                        checkAccountService.checkAccountIsUnMatch(detail.getOrderNo(), localAmount,
                                            detail.getAmount(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态："+String.valueOf(order.getStatus()),
                                            "交易成功", order.getSubAccountId(), "19付对账不一致");
                                	}
                                }
                            }
                            break;
                        }
                        //不存在于对账文件，但本地已成功！(第三方不成功或无此账户)
                        if (!isExist && Constants.ORDER_STATUS_SUCCESS == localStatus) {
                            //对账不一致
                            String info = "{" + localOrderNo + "|交易结果未知}对账不一致：本地订单成功，但三方未成功或无成功记录";
                            checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, 0d,
                            		Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, 
                            		"订单状态："+String.valueOf(order.getStatus()), "无", order.getSubAccountId(), "19付对账不一致");
                        }
                        //不存在于对账文件，但本地处理中！(认为三方已失败)
                        if(!isExist && Constants.ORDER_STATUS_PAYING == localStatus){
                        	String info = "{" + localOrderNo + "|交易结果未知}对账不一致：本地订单处理中，但三方未成功或无成功记录";
                            checkAccountService.checkAccountIsUnMatch(localOrderNo, localAmount, 0d,
                            		Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, 
                            		"订单状态："+String.valueOf(order.getStatus()), "无", order.getSubAccountId(), "19付对账本地处理中");
                        	
                        }
                        isCheckedMap.put(localOrderNo, localStatus);
                    }
                }

                //三方成功，但本地无此订单
                if (!CollectionUtils.isEmpty(details)) {
                    for (AccountDetail detail : details) {
                        //实名认证、转账的情况跳过
                        if ("实名认证记录".equals(detail.getTradeType()) || "转账".equals(detail.getTradeType())) {
                            continue;
                        }
                        String orderNo = detail.getOrderNo();
                        //订单为空的请求跳过
                        if(StringUtil.isEmpty(orderNo)){
                        	continue;
                        }
                        //若对账文件中有记录尚未校验，则针对这笔订单进行本地查询并对账
                        if (isCheckedMap.get(orderNo) == null) {
                            BsPayOrdersExample example = new BsPayOrdersExample();
                            example.createCriteria().andOrderNoEqualTo(detail.getOrderNo());
                            List<BsPayOrders> bsOrders = bsPayOrdersMapper.selectByExample(example);
                            //第三方有(成功)，本系统未记录此账务
                            if (CollectionUtils.isEmpty(bsOrders)) {
                                //对账不一致
                                String info = "{" + detail.getOrderNo() + "|"
                                              + detail.getTradeType()
                                              + "|交易成功}对账不一致：第三方成功，但本地未记录此账务";
                                checkAccountService.checkAccountIsUnMatch(detail.getOrderNo(), 0d, detail.getAmount(),
                                		Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态：无", "交易成功", null, "19付对账不一致");
                            }
                            //第三方有(成功)，本系统也有记录此账务（发生此种情况可能是对账文件中包含 其他日期的 账务）
                            else {
                                if (Constants.ORDER_STATUS_SUCCESS == bsOrders.get(0).getStatus()
                                    && detail.getAmount().compareTo(bsOrders.get(0).getAmount()) == 0) {
                                	//交易类型暂不校验
                                    /*String payType = detail.getTradeType();
                                    if ("代付".equals(payType)) {
                                        payType = Constants.CHANNEL_TRS_DF;
                                    } else if ("快捷支付".equals(payType)) {
                                        payType = Constants.CHANNEL_TRS_QUICKPAY;
                                    } else if ("转账".equals(payType)) {
                                        payType = Constants.CHANNEL_TRS_TRANSFER;
                                    }
                                    //对账不一致（交易类型不一致）
                                    if (!bsOrders.get(0).getChannelTransType().equals(payType)) {
                                        String info = "{" + detail.getOrderNo() + "|"
                                                      + detail.getTradeType()
                                                      + "|交易成功}对账不一致：交易类型不匹配";
                                        checkAccountIsUnMatch(detail.getOrderNo(), bsOrders.get(0)
                                            .getAmount(), detail.getAmount(),
                                            Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "交易成功", "交易成功");
                                    } else {*/
                                        //对账一致
                                        String info = "{" + orderNo + "|" + detail.getTradeType()
                                                      + "|交易成功}对账一致";
                                        checkAccountService.checkAccountIsMatch(orderNo, bsOrders.get(0).getAmount(),
                                            detail.getAmount(), Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT,
                                            info, bsOrders.get(0).getSubAccountId());
                                    /*}*/
                                } else {
                                	
                                	if(Constants.ORDER_STATUS_PAYING ==  bsOrders.get(0).getStatus()
                                            && detail.getAmount().compareTo(bsOrders.get(0).getAmount()) == 0){
                                		
                                		checkAccountService.checkAccount4Paying(bsOrders.get(0), detail, OrderStatus.SUCCESS.getCode(), null);
                                		
                                	}else{
                                		//对账不一致
                                        String info = "{" + detail.getOrderNo() + "|"
                                                      + detail.getTradeType()
                                                      + "|交易成功}对账不一致：第三方成功，但本地订单失败或金额不匹配";
                                        checkAccountService.checkAccountIsUnMatch(detail.getOrderNo(), bsOrders.get(0)
                                            .getAmount(), detail.getAmount(), Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info,
                                            "订单状态："+String.valueOf(bsOrders.get(0).getStatus()), "交易成功", 
                                            bsOrders.get(0).getSubAccountId(), "19付对账不一致");
                                		
                                	}
                                }
                            }
                        }
                    }
                }
            }
            log.info("==================定时任务{19付对账}执行19付明细对账完成====================");
        } catch (Exception e) {
            log.error("==================定时任务{19付对账}执行19付对账失败====================", e);
            //告警
            specialJnlService.warn4Fail(null, "定时任务{19付对账}对账失败：" + StringUtil.left(e.getMessage(), 20), null, "19付对账异常失败",true);
        }

    }


    public static void main(String[] args) {
    }
}
