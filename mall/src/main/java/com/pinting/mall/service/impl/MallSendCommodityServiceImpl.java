package com.pinting.mall.service.impl;

import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.mall.dao.MallSendCommodityMapper;
import com.pinting.mall.model.MallSendCommodity;
import com.pinting.mall.model.MallSendCommodityExample;
import com.pinting.mall.service.MallSendCommodityService;


/**
 *
 * @author SHENGUOPING
 * @date  2018年5月16日 下午4:32:31
 */
@Service
public class MallSendCommodityServiceImpl implements MallSendCommodityService {

	@Autowired
	private MallSendCommodityMapper mallSendCommodityMapper;
	
	@Override
	public MallSendCommodity findByOrderId(Integer orderId) {
		MallSendCommodityExample example = new MallSendCommodityExample();
		example.createCriteria().andOrderIdEqualTo(orderId);
		List<MallSendCommodity> list = mallSendCommodityMapper.selectByExample(example);
		return CollectionUtils.isEmpty(list) ? null : list.get(0);
	}
	
}
