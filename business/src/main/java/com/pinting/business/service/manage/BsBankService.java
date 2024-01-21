package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsBank;

public interface BsBankService {
	/**
	 * 分页
	 * @return
	 */
	public List<BsBank> bsBankPage(BsBank record);
	/**
	 * 多少条
	 * @param record
	 * @return
	 */
	public int baBankCount(BsBank record);
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updateBsBank(BsBank record);
	/**
	 * 添加
	 * @return
	 */
	public int addBsbank(BsBank record);
	/**
	 * 根据id查询
	 * @return
	 */
	public BsBank bsBankPrimaryKey(Integer id);
	/**
	 * 查询
	 * @return
	 */
	public BsBank selectBank(BsBank record);
	/**
	 * 分组查询银行名称
	 * @return
	 */
	public List<BsBank> groupByBankName();
	
}
