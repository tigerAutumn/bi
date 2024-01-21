package com.pinting.business.facade.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_SysConfig_QuerySysConfig;
import com.pinting.business.hessian.site.message.ResMsg_SysConfig_QuerySysConfig;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.site.SysConfigService;


@Component("SysConfig")
public class SysConfigFacade{
	@Autowired
	private SysConfigService sysConfigService;
	
	public void querySysConfig(ReqMsg_SysConfig_QuerySysConfig req, ResMsg_SysConfig_QuerySysConfig res){
		
		BsSysConfig conf = sysConfigService.findConfigByKey(req.getKey());
		res.setConfKey(conf.getConfKey());
		res.setConfValue(conf.getConfValue());
		res.setName(conf.getName());
		res.setNote(conf.getNote());
	}
	
}
