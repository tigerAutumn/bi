package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsUserRegistView;
import com.pinting.business.model.BsUserRegistViewExample;
import com.pinting.business.model.vo.BsUserRegistViewVO;


public interface BsUserRegisterService {
	
	/**
	 * 根据时间查询得到用户的注册记录-后续要存库
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public BsUserRegistView selectByTime(String startDate, String endDate);
	
	/**
	 * 新增
	 * @param record
	 */
	public void addRegisterView(BsUserRegistView record);
	
	/**
	 * 根据条件查询
	 * @param example
	 * @return
	 */
	public BsUserRegistView selectByExample(BsUserRegistViewExample example);
	
	/**
	 * 修改
	 * @param record
	 */
	public void updateView(BsUserRegistView record);
	
	/**
	 * 列表调速
	 * @param viewVo
	 * @return
	 */
	public int findRegistViewAllCount(BsUserRegistViewVO viewVo);
	
	/**
	 * 获取列表
	 * @param viewVo
	 * @return
	 */
	public List<BsUserRegistView> findRegistViewAllList(BsUserRegistViewVO viewVo);
}
