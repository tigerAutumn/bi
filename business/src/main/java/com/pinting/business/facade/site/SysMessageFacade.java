package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_SysMessage_User178Message;
import com.pinting.business.hessian.site.message.ReqMsg_SysMessage_UserNormalMessage;
import com.pinting.business.hessian.site.message.ResMsg_SysMessage_User178Message;
import com.pinting.business.hessian.site.message.ResMsg_SysMessage_UserNormalMessage;
import com.pinting.business.model.BsSysMessage;
import com.pinting.business.service.site.SysMessageService;
import com.pinting.business.util.BeanUtils;

@Component("SysMessage")
public class SysMessageFacade {
	@Autowired
	private SysMessageService sysMessageService;
	
	public void userNormalMessage(ReqMsg_SysMessage_UserNormalMessage req, ResMsg_SysMessage_UserNormalMessage resp){
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

		List<BsSysMessage>  sysMessages = sysMessageService.userNormalMessage();
		for (BsSysMessage bsSysMessage : sysMessages) {
			result.add(BeanUtils.transBeanMap(bsSysMessage));
		}
		resp.setSysMessages(result); 
	}
	
	public void user178Message(ReqMsg_SysMessage_User178Message req, ResMsg_SysMessage_User178Message resp){
		
		
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();

		List<BsSysMessage>  sysMessages = sysMessageService.user178Message(req.getType());
		for (BsSysMessage bsSysMessage : sysMessages) {
			result.add(BeanUtils.transBeanMap(bsSysMessage));
		}
		resp.setSysMessages(result); 
	}
	
}
