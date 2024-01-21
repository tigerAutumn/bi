package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_System_AddSensitiveJnl;
import com.pinting.business.hessian.site.message.ReqMsg_System_BatchAddSensitiveJnl;
import com.pinting.business.hessian.site.message.ReqMsg_System_NewVersion;
import com.pinting.business.hessian.site.message.ReqMsg_System_Status;
import com.pinting.business.hessian.site.message.ResMsg_System_AddSensitiveJnl;
import com.pinting.business.hessian.site.message.ResMsg_System_BatchAddSensitiveJnl;
import com.pinting.business.hessian.site.message.ResMsg_System_NewVersion;
import com.pinting.business.hessian.site.message.ResMsg_System_Status;
import com.pinting.business.model.BsAppVersion;
import com.pinting.business.model.BsSensitiveOperateJnl;
import com.pinting.business.model.BsSysStatus;
import com.pinting.business.service.site.SystemService;
import com.pinting.core.util.GlobEnvUtil;

@Component("System")
public class SystemFacade{
	@Autowired
	private SystemService systemService;
	
	public void addSensitiveJnl(ReqMsg_System_AddSensitiveJnl req, ResMsg_System_AddSensitiveJnl res){
		BsSensitiveOperateJnl jnl = new BsSensitiveOperateJnl();
		jnl.setInfo(req.getInfo());
		jnl.setIp(req.getIp());
		jnl.setOpType(req.getOpType());
		jnl.setTerminal(req.getTerminal());
		jnl.setTime(req.getTime());
		jnl.setUserId(req.getUserId());
		jnl.setUserName(req.getUserName());
		systemService.addSensitiveJnl(jnl);
	}
	public void status(ReqMsg_System_Status req, ResMsg_System_Status res){
		List<BsSysStatus> bslist = systemService.findSysStatusList();
		res.setSysValue(bslist.get(0).getStatusValue());
		res.setTranValue(bslist.get(1).getStatusValue());
		
	}
	public void batchAddSensitiveJnl(ReqMsg_System_BatchAddSensitiveJnl req, ResMsg_System_BatchAddSensitiveJnl res){
		
		List<HashMap<String, Object>> reqSensitiveJnls = req.getSensitiveJnls();
		
		if(reqSensitiveJnls != null && reqSensitiveJnls.size() > 0){
			List<BsSensitiveOperateJnl> sensitiveJnls = new ArrayList<BsSensitiveOperateJnl>();
			for (HashMap<String, Object> sensitiveJnlMap : reqSensitiveJnls) {
				BsSensitiveOperateJnl jnl = new BsSensitiveOperateJnl();
				jnl.setInfo((String)sensitiveJnlMap.get("info"));
				jnl.setIp((String)sensitiveJnlMap.get("ip"));
				jnl.setOpType((String)sensitiveJnlMap.get("opType"));
				jnl.setTerminal((String)sensitiveJnlMap.get("terminal"));
				jnl.setTime((Date)sensitiveJnlMap.get("time"));
				jnl.setUserId((Integer)sensitiveJnlMap.get("userId"));
				jnl.setUserName((String)sensitiveJnlMap.get("userName"));
				sensitiveJnls.add(jnl);
			}
			systemService.batchAddSensitiveJnls(sensitiveJnls);
		}
	}
	public void newVersion(ReqMsg_System_NewVersion req, ResMsg_System_NewVersion res) {
		BsAppVersion version = systemService.findNewVersion(req.getAppType(), req.getAppVersion());
		if(version != null) {
			res.setAppType(version.getAppType());
			res.setContent(version.getContent());
			res.setIsMandatory(version.getIsMandatory());
			if("android".equals(req.getAppType().trim())) {
				res.setUrl(GlobEnvUtil.get("wechat.server.web") + version.getUrl());
			} else {
				res.setUrl(version.getUrl());
			}
			res.setAppVersion(version.getVersion());
		}
	}
}
