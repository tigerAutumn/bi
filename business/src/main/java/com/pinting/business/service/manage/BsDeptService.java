package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsDept;

public interface BsDeptService {
	
	/**
	 * 销售团队部门名称列表
	 * @param record
	 * @return
	 */
	public List<BsDept> findDeptName(BsDept record);

}
