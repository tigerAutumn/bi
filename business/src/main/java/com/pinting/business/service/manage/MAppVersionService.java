package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.BsAppVersion;


public interface MAppVersionService {
	
	/**
	 * 查询所有的app版本列表
	 * @param pageNum 页码
	 * @param numPerPage 用户页面大小
	 * @return
	 */
	public List<BsAppVersion> findAppVersion(Integer pageNum,Integer numPerPage);
	
	/**
	 * 查询所有的app版本列表总数
	 * @return
	 */
	public int findAllAppVersionCount();
	
	/**
	 * 存储app版本信息
	 * @param bsAppVersion
	 */
	public int saveAppVersion(BsAppVersion bsAppVersion);
	
	/**
	 * 根据参数查询呢app版本列表
	 * @return
	 */
	public List<BsAppVersion> findVersionByMap(Map<String,Object> map);
	
	/**
	 * 根据主键查询
	 * @param id 主键
	 * @return
	 */
	public BsAppVersion findAppVersionById(Integer id);
	
	/**
	 * 更新app版本信息
	 * @param bsAppVersion
	 */
	public int updateAppVersion(BsAppVersion bsAppVersion);
	
	/**
	 * 根据主键删除版本
	 * @param id 主键
	 * @return
	 */
	public int deleteAppVersionById(Integer id);
	
	/**
	 * 根据版本和app类型查询是否是最新版本
	 * @param map 查询条件
	 * @return 0 是最新版本   其他否
	 */
	public int findIsLastVersion(Map<String,Object> map);
	
	/**
	 * 查找ios/android APP版本号的最大值
	 * @return
	 */
	public List<BsAppVersion>  findAppVersionMaxValue();
	
}
