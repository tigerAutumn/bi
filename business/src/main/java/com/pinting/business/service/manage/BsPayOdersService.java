package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.vo.BsPayOrdersVO;
import com.pinting.business.model.vo.DailyCheckGachaVO;

public interface BsPayOdersService {
	
	/**订单查询（客服使用）**/
	List<BsPayOrdersVO> payOrdersPage(BsPayOrdersVO record);
	int countPayOrders(BsPayOrdersVO record);
	
	/**
	 * 查询用户首次购买设备信息
	 * return Map<String,Object>
	 */
	public Map<String,Object> findFirstInvestDevice(Map<String,Object> map);
	
	/**
	 * 根据用户查询成功或处理中的订单。
	 * @param userId
	 * @return
	 */
	public List<BsPayOrders> selectBuySuccessPayOrders(Integer userId);
	
	/**
	 * 财务确认处理查看详情中，根据userId查询用户订单信息
	 * @param userId
	 * @return
	 */
	public List<BsPayOrdersVO> queryPayOrdersByUserId(Integer userId);
	
	/*************************管理台日常对账轧差信息*********************************/
	/**
	 * 管理台 还款日常管理计数
	 * @param record
	 * @return
	 */
	Integer queryGachaCheckDailyCount(DailyCheckGachaVO record);
	
	/**
	 * 管理台 还款日常管理列表
	 * @param record
	 * @return
	 */
	List<DailyCheckGachaVO> queryGachaCheckDailyInfo(DailyCheckGachaVO record);

	/**
	 * 管理台 还款日常管理还款金额统计
	 * @param record
	 * @return
     */
	Double queryGachaCheckDailySum(DailyCheckGachaVO record);
	
}
