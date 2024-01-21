package com.pinting.business.service.manage;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsAppActive;
import com.pinting.business.model.vo.AppActiveVO;

public interface MAppActiveService {
	/**
	 * 分页查询所有的AppActiveVO列表
	 * @return List<AppActiveVO>
	 */
	public List<AppActiveVO> findAppActiveListByPage(AppActiveVO record);
	
	/**
	 * 查询AppActiveVO列表的总数
	 * @return int
	 */
	public int findAppActiveTotalRows(AppActiveVO record);
	
	/**
	 * 新增BsAppActive
	 * @return int
	 */
	public int saveAppActive(BsAppActive record);
	
	/**
	 * 更新BsAppActive
	 * @return int
	 */
	public int updateAppActive(BsAppActive record);
	
	/**
	 * 根据主键删除BsAppActive
	 * @return int
	 */
	public int deleteAppActive(Integer id);
	
	/**
	 * 根据主键查询BsAppActive
	 * @return BsAppActive
	 */
	public BsAppActive findAppActiveById(Integer id);
	
	/**
	 * 
	 * @Title: selectAppActive 
	 * @Description: 查询所有已发布的app活动
	 * @return
	 * @throws
	 */
	public List<BsAppActive> selectAppActive(Date now);

	/**
	 * 查询最新发布的活动
	 * @return
     */
	BsAppActive selectLatestActive();
}
