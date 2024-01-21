package com.pinting.business.dayend.task;

import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.ReapalTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;


/**
 * 融宝支付处理中订单查询对账（当天23:50定时）
 * @Project: business
 * @Title: ReapalCheckProcessingTask.java
 * @author dingpf
 * @date 2016-1-8 下午1:49:00
 * @Copyright: 2016 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class ReapalCheckProcessingTask {
    private Logger log = LoggerFactory.getLogger(ReapalCheckProcessingTask.class);
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
    	
    	//融宝支付处理中订单查询对账
        checkAccountJob();
    }

    /**
     * 融宝支付处理中订单查询对账
     */
    private void checkAccountJob() {
		try {
			log.info("==================定时任务{融宝处理中订单对账}快捷支付处理中订单查询对账开始====================");
			Date checkDate = new Date();//对账日期：当天
			//查询对账日期范围内本地融宝订单
            Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(checkDate));
            Date endDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(checkDate, 1)));
            BsPayOrdersExample orderExample = new BsPayOrdersExample();
            orderExample.createCriteria().andUpdateTimeIsNotNull().andUpdateTimeBetween(beginDate, endDate)
            .andChannelEqualTo(Constants.CHANNEL_REAPAL).andChannelTransTypeEqualTo(Constants.CHANNEL_TRS_QUICKPAY)
            .andStatusEqualTo(Constants.ORDER_STATUS_PAYING);
            List<BsPayOrders> orders = bsPayOrdersMapper.selectByExample(orderExample);
			//有订单需对账查询
            if(orders!=null && orders.size()>0){
            	log.info("==================定时任务{融宝处理中订单对账}查询到"+orders.size()+"笔处理中订单====================");
				for (BsPayOrders order : orders) {
					try {
						checkAccountService.checkReapalSingle(order);
					} catch (Exception e) {
						log.error("==================定时任务{融宝处理中订单对账}单笔订单"+order.getOrderNo()+"对账失败====================", e);
			            //告警
			            specialJnlService.warn4Fail(null, "定时任务{融宝处理中订单对账}单笔"+order.getOrderNo()+"订单对账失败：" + StringUtil.left(e.getMessage(), 20), order.getOrderNo(), "融宝单笔对账异常失败",true);
					}
					//睡眠1s再进行下次接口查询并对账
					Thread.sleep(1000);
				}
			}
			//无订单记录，不需对账
			else{
				log.info("==================定时任务{融宝处理中订单对账}无融宝处理中订单记录，不需对账====================");
			}
		} catch (Exception e) {
			log.error("==================定时任务{融宝处理中订单对账}执行融宝处理中订单对账失败====================", e);
            //告警
            specialJnlService.warn4Fail(null, "定时任务{融宝处理中订单对账}融宝处理中订单对账失败：" + StringUtil.left(e.getMessage(), 20), null, "融宝处理中订单对账异常失败",true);
		}
	}

    public static void main(String[] args) {
    }
}
