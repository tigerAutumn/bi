package com.pinting.business.service.manage;

import java.util.ArrayList;

import com.pinting.business.model.BsProductSerial;

public interface BsProductSerialService {
	
	/**
	 * 产品系列表 列表
	 * 
	 * @return
	 */
	public ArrayList<BsProductSerial> findProductSerialInfo(int pageNum, int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 产品系列表 统计
	 * @return
	 */
	public int selectCountProductSerial();
	
	/**
	 * 查询产品系列表 名称是否已存在
	 * @param record
	 * @return
	 */
	public BsProductSerial selectBsProductSerial(BsProductSerial record); 
	
	/**
	 * 查询该系列产品名称 是否已被产品表引用 
	 * @param record
	 * @return
	 */
	public int selectCountOfSerialId(Integer serialId);
	
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updateProductSerial(BsProductSerial record);
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int addProductSerial(BsProductSerial record);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsProductSerial selectByPrimaryId(Integer id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteProductSerialById(Integer id);
	

}
