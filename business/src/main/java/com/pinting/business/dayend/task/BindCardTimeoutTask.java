package com.pinting.business.dayend.task;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUser;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;

/**
 * 银行卡绑定超时，默认修改状态为失败
 * 
 * @Project: business
 * @Title: BindCardTimeoutTask.java
 * @author dingpf
 * @date 2015-3-13 下午1:08:11
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Deprecated
public class BindCardTimeoutTask {
	private Logger log = LoggerFactory.getLogger(BindCardTimeoutTask.class);
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private SMSServiceClient smsServiceClient;

	/**
	 * 任务执行
	 */
	public void execute() {
		// 定时任务【银行卡绑定超时处理】
		log.info("==================定时任务【银行卡绑定超时处理】开始====================");

		BsSysConfig config = sysConfigService.findConfigByKey(Constants.BIND_CARD_TIMEOUT_INTERVAL);
		Integer timeout = Integer.valueOf(config.getConfValue());
		try {
			Date time = DateUtil.addSeconds(new Date(), -timeout);
			// 查询已超时用户
			List<BsUser> userList = userService.findUserForBindCardTimeout(time);
			if(userList != null && userList.size() > 0){
				// 已超时用户 状态 处理
				bankCardService.batchModifyBindCardInfoForTimeout(timeout);
				// 批量发送短信给已超时用户
				for (BsUser bsUser : userList) {
					//修改bs_bank_card表
					BsBankCard card = new BsBankCard();
					card.setStatus(Constants.BANK_CARD_BINDFAIL);
					card.setUserId(bsUser.getId());
					bankCardService.modifyBankCardByUserId(card);
					/*smsService.sendMessage(bsUser.getMobile(),new StringBuffer("尊敬的币港湾用户，抱歉，您的银行卡绑定失败，原因【" + PTMessageEnum.DAFY_REALNAME_AUTH_ERROR.getMessage()
									+ "】。").toString());*/
					smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.BIND_BANK_CARD_FALL,PTMessageEnum.DAFY_REALNAME_AUTH_ERROR.getMessage()); 
				}
			}
			log.info("==================定时任务【银行卡绑定超时处理】结束====================");
		} catch (Exception e) {
			log.error("==================定时任务【银行卡绑定超时处理】失败====================", e);
		}

	}

}
