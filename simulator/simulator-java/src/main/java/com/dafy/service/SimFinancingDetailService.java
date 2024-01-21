/**
 * SimFinancingDetail 相关逻辑编写
 * @author yanwl
 * @date 2015-11-19
 */
package com.dafy.service;

import java.util.List;
import java.util.Map;

import com.dafy.model.pojo.SimFinancingDetail;



public interface SimFinancingDetailService {
	/**
	 * 添加SimFinancingDetail
	 * @param simFinancingDetail 理财明细信息
	 * @return boolean
	 */
	boolean insertSimFinancingDetail(SimFinancingDetail simFinancingDetail);
	
	/**
	 * 删除 SimFinancingDetail
	 * @param simFinancingDetailId 理财明细编号
	 * @return boolean
	 */
	boolean deleteSimFinancingDetail(Integer simFinancingDetailId);
	
	/**
	 * 查询 列表 SimFinancingDetail
	 * @return 返回list列表
	 */
	List<SimFinancingDetail> selectAllSimFinancingDetails(Map<String,Object> map);

	/**
	 * 更新理财明细信息
	 * @param simFinancingDetail 理财明细信息
	 * @return 布尔值 成功为true，失败为false
	 */
	boolean updateSimFinancingDetail(SimFinancingDetail simFinancingDetail);

	/**
	 * 根据主键查询 SimFinancingDetail对象
	 * @param simFinancingDetailId 理财明细编号
	 * @return 返回 SimFinancingDetail
	 */
	SimFinancingDetail selectSimFinancingDetailById(Integer simFinancingDetailId);
}
