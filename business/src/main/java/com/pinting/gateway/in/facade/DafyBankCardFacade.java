package com.pinting.gateway.in.facade;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.gateway.in.service.DafyUserService;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyBankCard_CardBindResult;
import com.pinting.gateway.hessian.message.dafy.G2BResMsg_DafyBankCard_CardBindResult;

/**
 * 银行卡相关处理类
 * 
 * @Project: business
 * @Title: DafyBankCardFacade.java
 * @author dingpf
 * @date 2015-2-10 下午7:32:47
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("DafyBankCard")
public class DafyBankCardFacade {
	@Autowired
	private DafyUserService dafyUserService;
	@Autowired
	private BsSpecialJnlService bsSpecialJnlService;

	private Logger log = LoggerFactory.getLogger(DafyBankCardFacade.class);

	// 达飞绑卡结果通知
	public void cardBindResult(G2BReqMsg_DafyBankCard_CardBindResult req,
			G2BResMsg_DafyBankCard_CardBindResult res) {
		List<String> customerIdList = req.getCustomerIdList();
		List<String> resultList = req.getResultList();
		List<String> msgList = req.getResultMsgList();

		log.info("====================>Business平台已收到银行卡绑定结果");
		
		if(customerIdList!=null && customerIdList.size()>0){
			boolean allSuccFlag = true;
			String failCustomerIds = "";
			for (int i = 0; i < customerIdList.size(); i++) {
				try {
					//结果处理
					dafyUserService.bindCardResultInform(customerIdList.get(i),
							resultList.get(i), msgList.get(i));
				} catch (Exception e) {
					allSuccFlag = false;
					failCustomerIds += customerIdList.get(i) + ",";
					log.error("=============达飞绑卡通知调用失败,customerId = " + customerIdList.get(i), e);
					bsSpecialJnlService.addSpecialJnl("【达飞绑卡通知】", "达飞客户号为:"
							+ customerIdList.get(i) + "的用户绑卡通知失败");
				}
			}
			if(!allSuccFlag){
				throw new PTMessageException(PTMessageEnum.DAFY_RESULT_PROCESSING_ERROR, "达飞客户号为:"
						+ failCustomerIds);
			}
		}
	}
}
