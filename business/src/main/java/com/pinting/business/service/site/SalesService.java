package com.pinting.business.service.site;


import com.pinting.business.model.BsSales;

public interface SalesService {
	
	/**
	 * 修改
	 * @param record
	 * @return
	 */
	public int updateBsSales(BsSales record);
	
	/**
	 * 查询
	 * @param record
	 * @return
	 */
	public BsSales selectBsSales(BsSales record);
	public BsSales selectByPrimaryKey(Integer id);
	
	/**
	 * 添加
	 * @return
	 */
	public int addBsbank(BsSales record);
	

}
