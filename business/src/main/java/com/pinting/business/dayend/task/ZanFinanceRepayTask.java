package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsLoanFinanceRepayMapper;
import com.pinting.business.model.BsLoanFinanceRepay;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.SendWechatService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 赞分期产品回款通知理财用户定时任务
 * @author bianyatian
 * @2017-1-9 下午12:36:36
 */
@Service
public class ZanFinanceRepayTask {
	
	private Logger log = LoggerFactory.getLogger(ZanFinanceRepayTask.class);
	@Autowired
	private BsLoanFinanceRepayMapper bsLoanFinanceRepayMapper;
    @Autowired
    private SMSServiceClient smsServiceClient;
    @Autowired
    private SendWechatService sendWechatService;
    @Autowired
    private UserService userService;
    @Autowired
    private SysConfigService sysConfigService;
	
	public void execute() {
		log.info("===============【赞分期产品回款通知理财用户】开始===================");
		BsSysConfig sysConfig = sysConfigService.findConfigByKey(Constants.ZAN_FINANCE_REPAY_NOTICE_TIME);
		String time = "20:00:00";//定时时间
		if(sysConfig != null){
			time = sysConfig.getConfValue();
		}
		
		String today = DateUtil.formatYYYYMMDD(new Date());
		String yesterday = DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1));
		String startTimeStr = yesterday + " " +time;
		Date startTime = DateUtil.parseDateTime(startTimeStr); 
		String endTimeStr = today +" " +time;
		Date endTime = DateUtil.parseDateTime(endTimeStr);
		
		List<BsLoanFinanceRepay> repayList = bsLoanFinanceRepayMapper.selectGroupBySubAcctId(startTime, endTime, Constants.FINANCE_REPAY_SATAE_REPAIED);
		if(CollectionUtils.isEmpty(repayList)){
			log.info("===============【赞分期产品回款通知理财用户】需发通知列表为空===================");
		}
		for (BsLoanFinanceRepay bsLoanFinanceRepay : repayList) {
			BsUser fnUser = userService.findUserById(bsLoanFinanceRepay.getFnUserId());
			Double productPrincipal = bsLoanFinanceRepay.getPrincipal();
			Double productInterest = bsLoanFinanceRepay.getInterest();
			smsServiceClient.sendTemplateMessage(fnUser.getMobile(), TemplateKey.RETURN_SUCCESS2BALANCE,
                    String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());
            //发送微信模板消息
            sendWechatService.paymentMgs2Balance(bsLoanFinanceRepay.getFnUserId(),"",
                    String.valueOf(MoneyUtil.add(productPrincipal, productInterest).doubleValue()), productPrincipal.toString(), productInterest.toString());
		}
		log.info("===============【赞分期产品回款通知理财用户】结束===================");
	}
}
