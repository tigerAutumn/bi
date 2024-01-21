package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsSensitiveOperateJnl;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.BsSysConfig;

/**
 * 系统配置接口
 * @Project: business
 * @Title: MSysConfigService.java
 * @author dingpf
 * @date 2015-1-29 下午1:46:27
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface MSysConfigService {
	/**
	 * 分页 查询所有系统配置列表
	 * @param pageNum 页码
	 * @param numPerPage 每页条数
	 * @return 成功返回系统配置列表，否则返回null
	 */
	public List<BsSysConfig> findBsSysConfigsByPage(String pageNum, String numPerPage);
	
	/**
	 * 统计系统配置列表总条数
	 * @return 成功返回系统配置列表总条数，否则返回0
	 */
	public int countTotalBsSysConfigs();
	
	/**
	 * 根据规则主键查询规则信息
	 * @param confKey
	 * @return 成功返回BsSysConfig对象，否则返回null
	 */
	public BsSysConfig findSysConfigByKey(String confKey);
	
	/**
	 * 根据修改标志，保存或修改（包括删除）规则信息
	 * @param bsSysConfig
	 * @param updateFlag 保存或修改标志
	 * @return 成功返回true，否则返回false
	 */
	public boolean saveOrUpdateSysConfig(BsSysConfig bsSysConfig, String updateFlag);
	/**
	 * 统计特殊交易查询流水总条数
	 * @return 成功返回特殊交易查询流水总条数，否则返回0
	 */
	public int countTotalMSpecialJnl();
	/**
	 * 分页 查询所有特殊交易查询流水
	 * @param pageNum 页码
	 * @param numPerPage 每页条数
	 * @return 成功返回特殊交易查询流水，否则返回null
	 */
	public List<BsSpecialJnl> findBsSpecialJnlByPage(int pageNum, int numPerPage);
	/**
	 * 统计用户敏感操作记录总条数
	 * @return 成功返回用户敏感操作记录总条数，否则返回0
	 */
	public int countTotalBsSensitiveJnl(String name,String ip);
	/**
	 * 分页 查询所有用户敏感操作记录查询流水
	 * @param name 用户名
	 * @param ip ip地址
	 * @param pageNum 页码
	 * @param numPerPage 每页条数
	 * @return 成功返回特殊交易查询流水，否则返回null
	 */
	public List<BsSensitiveOperateJnl> findBsSensitiveOperateJnlByPage(String name,String ip,int pageNum, int numPerPage);
}
