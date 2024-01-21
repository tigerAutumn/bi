package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsAppVersion;
import com.pinting.business.model.BsSensitiveOperateJnl;
import com.pinting.business.model.BsSysStatus;

/**
 * 微网站系统级服务接口
 * @Project: business
 * @Title: SystemService.java
 * @author dingpf
 * @date 2015-2-3 上午11:59:16
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface SystemService {
	/**
	 * 新增系统异动告警记录
	 * @param bsSensitiveOperateJnl
	 * @return 新增成功返回true，否则返回false
	 */
	public boolean addSensitiveJnl(BsSensitiveOperateJnl bsSensitiveOperateJnl);
	/**
	 * 批量 新增系统异动告警记录（不需要使用事务）
	 * @param bsSensitiveOperateJnls
	 * @return 新增成功返回true，否则返回false
	 */
	public boolean batchAddSensitiveJnls(List<BsSensitiveOperateJnl> bsSensitiveOperateJnls);
	/**
	 * 查询系统状态
	 * @return
	 */
	public List<BsSysStatus> findSysStatusList();
	
	/**
	 * 
	 * @Title: findNewVersion 
	 * @Description: 查询app最新版本
	 * @param appType
	 * @return
	 * @throws
	 */
	public BsAppVersion findNewVersion(String appType, String appVersion);
	
	/**
	 * 服务可用测试（用户ng调用url测试服务是否可用）
	 */
	public void serverUsableCheck();
}
