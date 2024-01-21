package com.pinting.business.service.site.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAppVersionMapper;
import com.pinting.business.dao.BsSensitiveOperateJnlMapper;
import com.pinting.business.dao.BsSysStatusMapper;
import com.pinting.business.model.BsAppVersion;
import com.pinting.business.model.BsAppVersionExample;
import com.pinting.business.model.BsSensitiveOperateJnl;
import com.pinting.business.model.BsSysStatus;
import com.pinting.business.model.BsSysStatusExample;
import com.pinting.business.service.site.SystemService;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_ServerUsableCheck;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.DafyTransportService;

@Service
public class SystemServiceImpl implements SystemService {

	@Autowired
	private BsSensitiveOperateJnlMapper bsSensitiveOperateJnlMapper;
	@Autowired
	private BsSysStatusMapper bsSysStatusMapper;
	@Autowired
	private BsAppVersionMapper appMapper;
	@Autowired
	private DafyTransportService dafyTransportService;

	
	@Override
	public boolean addSensitiveJnl(BsSensitiveOperateJnl bsSensitiveOperateJnl) {
		return bsSensitiveOperateJnlMapper.insertSelective(bsSensitiveOperateJnl) > 0;
	}
	@Override
	public boolean batchAddSensitiveJnls(
			List<BsSensitiveOperateJnl> bsSensitiveOperateJnls) {
		for (BsSensitiveOperateJnl bsSensitiveOperateJnl : bsSensitiveOperateJnls) {
			bsSensitiveOperateJnlMapper.insertSelective(bsSensitiveOperateJnl);
		}
		return true;
	}
	@Override
	public List<BsSysStatus> findSysStatusList() {
		BsSysStatusExample example = new BsSysStatusExample();
		return bsSysStatusMapper.selectByExample(example);
	}
	@Override
	@MethodRole("APP")
	public BsAppVersion findNewVersion(String appType, String appVersion) {
		//查询最新版本的app
		BsAppVersionExample example = new BsAppVersionExample();
		example.createCriteria().andAppTypeEqualTo(appType);
		example.setOrderByClause("version desc");
		List<BsAppVersion> list = appMapper.selectByExample(example);
		BsAppVersion app = list.get(0);
		//根据版本号查询更新标志
		BsAppVersionExample currentExample = new BsAppVersionExample();
		currentExample.createCriteria().andAppTypeEqualTo(appType).andVersionEqualTo(appVersion);
		List<BsAppVersion> currentList = appMapper.selectByExample(currentExample);
		if(CollectionUtils.isEmpty(currentList)) {
			app.setIsMandatory(1); //1表示强制更新
		}
		else {
			app.setIsMandatory(currentList.get(0).getIsMandatory());
		}
		return app;
	}
	@Override
	public void serverUsableCheck() {
		dafyTransportService.serverUsableCheck(new B2GReqMsg_Payment_ServerUsableCheck());
	}

}
