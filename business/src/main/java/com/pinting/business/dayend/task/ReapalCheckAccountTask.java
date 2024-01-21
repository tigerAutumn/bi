package com.pinting.business.dayend.task;

import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.exception.PTException;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.pay19.enums.OrderStatus;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalQuickPay_QueryOrderResult;
import com.pinting.gateway.out.service.ReapalTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * 融宝支付对账
 * @Project: business
 * @Title: ReapalCheckAccountTask.java
 * @author dingpf
 * @date 2016-1-8 下午1:49:00
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class ReapalCheckAccountTask {
    private Logger log = LoggerFactory.getLogger(ReapalCheckAccountTask.class);
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private ReapalTransportService reapalTransportService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private CheckAccountService checkAccountService;
    
    

	/**
     * 任务执行
     */
    protected void execute() {
    	
    	//融宝对账
        checkAccountJob();
    }

    /**
     * 融宝对账
     */
    private void checkAccountJob() {
		try {
			log.info("==================定时任务{融宝对账}快捷支付对账开始====================");
			Date checkDate = DateUtil.addDays(new Date(), -1);//对账日期
			//查询对账日期范围内本地融宝订单
            Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
            Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
            BsPayOrdersExample orderExample = new BsPayOrdersExample();
            orderExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate)
            .andChannelEqualTo(Constants.CHANNEL_REAPAL).andChannelTransTypeEqualTo(Constants.CHANNEL_TRS_QUICKPAY);
            List<BsPayOrders> orders = bsPayOrdersMapper.selectByExample(orderExample);
			//有订单需对账查询
            if(orders!=null && orders.size()>0){
				for (BsPayOrders order : orders) {
					try {
						Integer subAccountId = order.getSubAccountId();
						//调用快捷支付订单查询接口，判断金额、状态是否一致
						B2GReqMsg_ReapalQuickPay_QueryOrderResult req = new B2GReqMsg_ReapalQuickPay_QueryOrderResult();
						req.setOrderNo(order.getOrderNo());
						B2GResMsg_ReapalQuickPay_QueryOrderResult resp = reapalTransportService.queryOrderResult(req);
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
							String reapalStatus = resp.getStatus();
							//融宝快捷查询到的金额是分，所以此处除以100
							Double reapalAmount = MoneyUtil.divide(resp.getAmount(), 100).doubleValue();
							Integer localStatus = order.getStatus();
							Double localAmount = order.getAmount();
							//本地订单 成功
							if(localStatus == Constants.ORDER_STATUS_SUCCESS){
								//对账一致：本地成功，三方成功，且金额一致
								if("completed".equals(reapalStatus) && reapalAmount.compareTo(localAmount) == 0){
									//对账一致
									String info = "{" + order.getOrderNo() + "|融宝快捷支付|交易成功}对账一致";
									checkAccountService.checkAccountIsMatch(order.getOrderNo(), localAmount, reapalAmount, 
											Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT, info, subAccountId);
								}
								//对账不一致：状态或金额不一致
								else{
									String infoMsg = "completed".equals(reapalStatus)?"交易成功}对账不一致：金额不匹配":"交易失败}对账不一致：本地成功，三方状态失败";
									String info = "{" + order.getOrderNo() + "|融宝快捷支付|"+ infoMsg;
									String smsMsg = "completed".equals(reapalStatus)?"金额不匹配":"状态不匹配";
									checkAccountService.checkAccountIsUnMatch(order.getOrderNo(), localAmount, reapalAmount, 
											Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态："+String.valueOf(order.getStatus()), 
											reapalStatus, subAccountId, "融宝对账"+smsMsg);
								}
							}
							//本地订单 处理中
							else if(localStatus == Constants.ORDER_STATUS_PAYING){
								//对账不一致：本地处理中，三方已成功，本地需置为成功
								if("completed".equals(reapalStatus) && reapalAmount.compareTo(localAmount) == 0){
									checkAccountService.reapalQuickPayCheckAccount4Paying(order, resp, OrderStatus.SUCCESS.getCode(), null);
								}
								//对账不一致：本地处理中，三方失败或已关闭，本地需置为失败
								else if("failed".equals(reapalStatus) || "closed".equals(reapalStatus)){
									checkAccountService.reapalQuickPayCheckAccount4Paying(order, resp, OrderStatus.FAIL.getCode(), null);
								}
								//对账一致：本地处理中，三方处理中，但需告警
								else{
									//对账一致
									String info = "{" + order.getOrderNo() + "|融宝快捷支付|交易处理中}对账一致";
									checkAccountService.checkAccountIsMatch(order.getOrderNo(), localAmount, reapalAmount, 
											Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT, info, subAccountId);
									//告警
						            specialJnlService.warn4Fail(null, "定时任务{融宝对账}单笔订单"+order.getOrderNo()+"交易处理中", order.getOrderNo(), "融宝对账订单处理中",true);
								}
							}
							//本地订单 非成功、非处理中
							else{
								//对账不一致：本地失败，三方成功
								if("completed".equals(reapalStatus)){
									String info = "{" + order.getOrderNo() + "|融宝快捷支付|本地失败三方成功";
									checkAccountService.checkAccountIsUnMatch(order.getOrderNo(), localAmount, reapalAmount, 
											Constants.CHECK_ACCOUNT_DETAIL_DIFFERENT, info, "订单状态："+String.valueOf(order.getStatus()), 
											reapalStatus, subAccountId, "融宝对账本地失败三方成功");
								}
								//对账一致：本地失败，三方非成功
								else{
									//对账一致
									String info = "{" + order.getOrderNo() + "|融宝快捷支付|交易失败}对账一致";
									checkAccountService.checkAccountIsMatch(order.getOrderNo(), localAmount, reapalAmount, 
											Constants.CHECK_ACCOUNT_DETAIL_NO_DIFFERENT, info, subAccountId);
								}
							}
						}
						//查询异常
						else{
							throw new PTException(resp.getResCode(), 
									StringUtil.isEmpty(resp.getResMsg())?"订单结果查询失败":resp.getResMsg());
						}
					} catch (Exception e) {
						log.error("==================定时任务{融宝对账}单笔订单"+order.getOrderNo()+"对账失败====================", e);
			            //告警
			            specialJnlService.warn4Fail(null, "定时任务{融宝对账}单笔"+order.getOrderNo()+"订单对账失败：" + StringUtil.left(e.getMessage(), 20), order.getOrderNo(), "融宝单笔对账异常失败",true);
					}
					//睡眠1s再进行下次接口查询并对账
					Thread.sleep(1000);
				}
			}
			//无订单记录，不需对账
			else{
				log.info("==================定时任务{融宝对账}无融宝订单记录，不需对账====================");
			}
		} catch (Exception e) {
			log.error("==================定时任务{融宝对账}执行融宝对账失败====================", e);
            //告警
            specialJnlService.warn4Fail(null, "定时任务{融宝对账}融宝对账失败：" + StringUtil.left(e.getMessage(), 20), null, "融宝对账异常失败",true);
		}
	}

    public static void main(String[] args) {
    }
}
