/**
 * SimFinancing 相关逻辑编写
 * @author yanwl
 * @date 2015-11-19
 */
package com.dafy.service;

import java.util.List;
import java.util.Map;

import com.dafy.model.pojo.SimFinancing;
import com.dafy.model.pojo.SimFinancingDetail;
import com.dafy.model.vo.LoanInfoVO;



public interface SimFinancingService {
	/**
	 * 添加SimFinancing
	 * @param simFinancing 理财信息
	 * @return boolean
	 */
	boolean insertSimFinancing(SimFinancing simFinancing,List<SimFinancingDetail> list);
	
	/**
	 * 删除 SimFinancing
	 * @param simFinancingId 理财编号
	 * @return boolean
	 */
	boolean deleteSimFinancing(Integer simFinancingId);
	
	/**
	 * 查询 列表 SimFinancing
	 * @return 返回list列表
	 */
	List<SimFinancing> selectAllSimFinancings(Map<String,Object> map);

	/**
	 * 更新理财信息
	 * @param simFinancing 理财信息
	 * @return 布尔值 成功为true，失败为false
	 */
	boolean updateSimFinancing(SimFinancing simFinancing);

	/**
	 * 根据主键查询 SimFinancing对象
	 * @param simFinancingId 理财编号
	 * @return 返回 SimFinancing
	 */
	SimFinancing selectSimFinancingById(Integer simFinancingId);
	
	/**
	 * 查询SimFinancing总数
	 * @return 总数
	 */
	int countSimFinancings(Map<String,Object> map);
	
	/**
	 * 模拟恒丰标的出账回调币港湾-根据合作方订单号查询借款信息
	 * @param orderNo 合作方订单号
	 * @return
	 */
	LoanInfoVO selectLoanInfoByPartnerOrderNo(String orderNo);
}
