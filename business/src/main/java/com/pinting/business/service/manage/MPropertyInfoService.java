package com.pinting.business.service.manage;

import java.util.ArrayList;
import java.util.List;

import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.model.BsPropertyInfoExample;

public interface MPropertyInfoService {
	/**
	 * 查询资产合作方信息列表
	 * @param example
	 * @return List<BsPropertyInfo>
	 * @since JDK 1.7
	 */
	public List<BsPropertyInfo> findBsPropertyInfos(BsPropertyInfoExample example);
	
	/**
	 * 资产合作方信息 列表
	 * 
	 * @return
	 */
	public ArrayList<BsPropertyInfo> findPropertyInfoInfo(int pageNum, int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 资产合作方信息 统计 
	 * @return
	 */
	public int findCountPropertyInfo();
	
	/**
	 * 查询资产合作 名称是否已存在
	 * @param record
	 * @return
	 */
	public BsPropertyInfo findPropertyInfoName(BsPropertyInfo record); 
	
	/**
	 * 查询该资产合作名称 是否已被产品表引用
	 * @param record
	 * @return
	 */
	public int findCountByProductId(Integer id);
	
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updatePropertyInfo(BsPropertyInfo record);
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int addPropertyInfo(BsPropertyInfo record);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsPropertyInfo selectByPrimaryId(Integer id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deletePropertyInfoById(Integer id);
	
}
