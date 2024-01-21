package com.pinting.gateway.in.facade.mobile;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_System_NewVersion;
import com.pinting.business.hessian.site.message.ResMsg_System_NewVersion;
import com.pinting.business.model.BsAppVersion;
import com.pinting.business.service.site.SystemService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.in.util.InterfaceVersion;

@Component("appSystem")
public class SystemFacade{
	@Autowired
	private SystemService systemService;
	
	@InterfaceVersion("NewVersion/1.0.0")
	public void newVersion(ReqMsg_System_NewVersion req, ResMsg_System_NewVersion res) {
		BsAppVersion version = systemService.findNewVersion(req.getAppType(), req.getAppVersion());
		if(version != null) {
			res.setAppType(version.getAppType());
			res.setContent(version.getContent());
			res.setIsMandatory(version.getIsMandatory());
			if("android".equals(req.getAppType().trim())) {
				res.setUrl(GlobEnvUtil.get("android.apk.download.url") + version.getUrl());
			} else {
				res.setUrl(version.getUrl());
			}
			res.setAppVersion(version.getVersion());
		}
	}
}
