package com.pinting.mall.service;

import java.util.List;

import com.pinting.mall.model.MallExchangeOrders;
import com.pinting.mall.model.MallSendCommodity;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.manange.MallCommodityInfoVO;
import com.pinting.mall.model.vo.MallExchangeOrdersCommodityVO;
import com.pinting.mall.model.vo.MallExchangeOrdersVO;

public interface MallExchangeOrdersService {
	
	/**
	 * 根据id查询积分商城订单表数据，关联查询商品信息和商品类别信息
	 * @author bianyatian
	 * @param orderId
	 * @return
	 */
	MallExchangeOrdersCommodityVO selectByOrderId(Integer orderId);
	
	/**
	 * 修改积分商城订单表数据
	 * @author bianyatian
	 * @param exchangeOrders
	 */
	void updateMallExchangeOrders(MallExchangeOrders exchangeOrders);
	
	/**
	 * 管理台订单管理-计数
	 * @param record
	 * @return
	 */
	int countManageExchangeOrders(MallExchangeOrdersVO record);
	
	/**
	 * 管理台订单管理列表，新的分页插件
	 * @param record
	 * @param pagerReq
	 * @return
	 */
    PagerModelRspDTO<MallExchangeOrdersVO> findManageExchangeOrdersList(MallExchangeOrdersVO record, PagerReqDTO pagerReq);

	/**
	 * 订单发货，支持批量
	 * @param userId
	 * @param orderIds
	 * @param note
	 * @param deliveryNote
	 * @return
	 */
	Boolean sendCommodity(String userId, MallSendCommodity mallSendCommodity, 
			String note, String deliveryNote, String operateFlag);
	
	/**
	 * 查询商城订单信息
	 * @param orderId
	 * @return
	 */
	MallExchangeOrders findById(Integer orderId);
	
}
