package com.pinting.site.communicate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.Constants;

/**
 * @Project: site-java
 * @Title: CommunicateBusiness.java
 * @author Zhou Changzai
 * @date 2015-3-9 下午7:24:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component
public class CommunicateBusiness {
	@Autowired
	@Qualifier("siteService")
	private HessianService hessianService;
	
	@Autowired
	@Qualifier("siteMallService")
	private HessianService hessianMallService;
	
	
	public ResMsg handleMsg(ReqMsg reqMsg){
		ResMsg resMsg = hessianService.handleMsg(reqMsg);
		
		//获取系统状态,并更新
		try {
			if(resMsg.getExtendMap().get("sys_halt") != null){
				Constants.sysValue = (Integer) resMsg.getExtendMap().get("sys_halt");
			}
			
		} catch (Exception e) {
		}
		
		try {
			if(resMsg.getExtendMap().get("tran_halt") != null){
				Constants.tranValue = (Integer) resMsg.getExtendMap().get("tran_halt");
			}
			
		} catch (Exception e) {
		}
		
		
		return resMsg;
	}
	
	
	public ResMsg handleMallMsg(ReqMsg reqMsg){
		ResMsg resMsg = hessianMallService.handleMsg(reqMsg);
		
		//获取系统状态,并更新
		try {
			if(resMsg.getExtendMap().get("sys_halt") != null){
				Constants.sysValue = (Integer) resMsg.getExtendMap().get("sys_halt");
			}
			
		} catch (Exception e) {
		}
		
		try {
			if(resMsg.getExtendMap().get("tran_halt") != null){
				Constants.tranValue = (Integer) resMsg.getExtendMap().get("tran_halt");
			}
			
		} catch (Exception e) {
		}
		
		
		return resMsg;
	}
}
