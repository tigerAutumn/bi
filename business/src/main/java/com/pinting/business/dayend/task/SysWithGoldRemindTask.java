package com.pinting.business.dayend.task;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.springframework.stereotype.Service;

/**
 * 恒丰抵用金告警
 * @author SHENGP
 * @date  2017年4月24日 下午7:43:26
 */
@Service
public class SysWithGoldRemindTask {

	private Logger LOG = LoggerFactory.getLogger(SysWithGoldRemindTask.class);
	@Autowired
	private BsSysSubAccountService bsSysSubAccountService;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private BsSubAccountService bsSubAccountService;
	@Autowired
    private SpecialJnlService specialJnlService;
	
	public void execute() {
		LOG.info("=================【恒丰抵用金告警】定时任务开始=====================");
		BsSysSubAccount sysSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(Constants.SYS_ACCOUNT_DEP_REDPACKET);
		Double amount = sysSubAccount.getAvailableBalance();
		BsSysConfig config = bsSysConfigService.findKey(Constants.REDPACKET_GOLD_REMIND);
		Double remindAmount = Double.valueOf(config.getConfValue());
		Double redAccBalance = bsSubAccountService.countRedAccBalance();
		if (MoneyUtil.subtract(amount, remindAmount).doubleValue() < 0
				|| MoneyUtil.subtract(amount, redAccBalance).doubleValue() < 0) {
      	    /*specialJnlService.warn4Fail(amount, "恒丰抵用金告警sysSubAccountId："+sysSubAccount.getId(), 
      	    		sysSubAccount.getId().toString(), "【恒丰抵用金告警】", true);*/
			BsSysConfig bsSysConfigMobile = bsSysConfigService.findKey(Constants.FINANCE_MOBILE);
      	    String mobile = bsSysConfigMobile.getConfValue();
      	    smsServiceClient.sendTemplateMessage(mobile, TemplateKey.REDPACKET_GOLD_REMIND);
		}
	}
	
}
