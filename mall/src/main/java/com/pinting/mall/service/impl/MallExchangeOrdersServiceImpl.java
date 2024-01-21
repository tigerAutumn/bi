package com.pinting.mall.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.pinting.mall.dao.MallExchangeOrdersMapper;
import com.pinting.mall.dao.MallSendCommodityMapper;
import com.pinting.mall.model.MallExchangeOrders;
import com.pinting.mall.model.MallExchangeOrdersExample;
import com.pinting.mall.model.MallSendCommodity;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.manange.MallCommodityInfoVO;
import com.pinting.mall.model.vo.MallExchangeOrdersCommodityVO;
import com.pinting.mall.model.vo.MallExchangeOrdersVO;
import com.pinting.mall.service.MallExchangeOrdersService;
import com.pinting.mall.util.Constants;

@Service
public class MallExchangeOrdersServiceImpl implements MallExchangeOrdersService {
	
	private Logger log = LoggerFactory.getLogger(MallExchangeOrdersServiceImpl.class);
 
	@Autowired
	private MallExchangeOrdersMapper exchangeOrdersMapper;
	@Autowired
	private MallSendCommodityMapper mallSendCommodityMapper;
	
	@Override
	public MallExchangeOrdersCommodityVO selectByOrderId(Integer orderId) {
		MallExchangeOrdersCommodityVO exchangeOrdersCommodityVO = exchangeOrdersMapper.selectByOrderId(orderId);
		
		return exchangeOrdersCommodityVO;
	}

	@Override
	public void updateMallExchangeOrders(MallExchangeOrders exchangeOrders) {

		exchangeOrders.setUpdateTime(new Date());
		exchangeOrdersMapper.updateByPrimaryKeySelective(exchangeOrders);
	}

	@Override
	public int countManageExchangeOrders(MallExchangeOrdersVO record) {
		// 只计数，不查询列表
		PageHelper.startPage(1, -1);
        List<MallExchangeOrdersVO> list = exchangeOrdersMapper.findManageExchangeOrdersList(record);
        return new PagerModelRspDTO(new PagerReqDTO(), list).getTotalRow();
	}

	@Override
	public Boolean sendCommodity(String userId, MallSendCommodity mallSendCommodity, 
			String note, String deliveryNote, String operateFlag) {
		Boolean isSuccess = false;
		try {
			if (!"modify".equals(operateFlag)) {				
				mallSendCommodity.setDeliveryNote(deliveryNote);
				mallSendCommodity.setNote(note);
				mallSendCommodity.setStatus(Constants.SEND_COMMODITY_STATUS_FINISHED);
				mallSendCommodity.setmUserId(Integer.parseInt(userId));
				mallSendCommodity.setUpdateTime(new Date());
				mallSendCommodityMapper.updateByPrimaryKeySelective(mallSendCommodity);
				MallExchangeOrders mallExchangeOrders = this.findById(mallSendCommodity.getOrderId());
				if (mallExchangeOrders != null) {
					mallExchangeOrders.setOrderStatus(Constants.ORDER_STATUS_FINISHED);
					mallExchangeOrders.setUpdateTime(new Date());
					exchangeOrdersMapper.updateByPrimaryKeySelective(mallExchangeOrders);
				}
			} else {
				mallSendCommodity.setDeliveryNote(deliveryNote);
				mallSendCommodity.setNote(note);
				mallSendCommodity.setmUserId(Integer.parseInt(userId));
				mallSendCommodity.setUpdateTime(new Date());
				mallSendCommodityMapper.updateByPrimaryKeySelective(mallSendCommodity);
			}
			isSuccess = true;
		} catch (Exception e) {
			log.error("method sendCommodity exception:{}", e);
			isSuccess = false;
		}
		return isSuccess;
	}


	@Override
	public MallExchangeOrders findById(Integer orderId) {
		MallExchangeOrdersExample example = new MallExchangeOrdersExample();
		example.createCriteria().andIdEqualTo(orderId);
		List<MallExchangeOrders> list = exchangeOrdersMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}

	@Override
	public PagerModelRspDTO<MallExchangeOrdersVO> findManageExchangeOrdersList(MallExchangeOrdersVO record,
			PagerReqDTO pagerReq) {
		PageHelper.startPage(pagerReq.getPageNum(), pagerReq.getNumPerPage(), pagerReq.getCount());
        List<MallExchangeOrdersVO> list = exchangeOrdersMapper.findManageExchangeOrdersList(record);
        return new PagerModelRspDTO(pagerReq, list);
	}
	
}
