package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsUserKeepView;
import com.pinting.business.model.vo.BsUserKeepViewVO;

public interface BsUserKeepViewService {
	
	/**
	 * 新增
	 * @param record
	 */
	public void addKeepView(BsUserKeepView record);
	
	/**
	 * 列表
	 * @return
	 */
	public List<BsUserKeepViewVO> findUserKeepViewList(BsUserKeepViewVO record);
	
	/**
	 * 统计
	 * @param record
	 * @return
	 */
	public Integer findUserKeepViewCount(BsUserKeepViewVO record);
	
	/**
	 * 修改
	 * @param record
	 */
	public void updateKeepView(BsUserKeepView record);
	
}
