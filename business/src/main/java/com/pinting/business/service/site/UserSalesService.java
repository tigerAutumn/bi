package com.pinting.business.service.site;

import com.pinting.business.model.BsUserSales;

public interface UserSalesService {
	
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	public int updateBsSales(BsUserSales record);
	
	/**
	 * 查询
	 * @param record
	 * @return
	 */
	public BsUserSales selectByPrimaryKey(Integer id);
	
	/**
	 * 添加
	 * @return
	 */
	public int addUserSales(BsUserSales record);

}
