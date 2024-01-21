package com.pinting.mall.service.site.impl;

import com.pinting.mall.dao.MallExchangeOrdersMapper;
import com.pinting.mall.model.vo.MallExchangeVO;
import com.pinting.mall.service.site.MallExchangeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 积分商城商品兑换服务
 *
 * @author shh
 * @date 2018/5/16 10:49
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class MallExchangeServiceImpl implements MallExchangeService {

    @Autowired
    private MallExchangeOrdersMapper mallExchangeOrdersMapper;

    @Override
    public List<MallExchangeVO> queryMallExchangeByUserId(Integer userId, Integer pageIndex, Integer pageSize) {
        Map<String, Object> data=new HashMap<String, Object>();
        data.put("userId", userId);
        data.put("start", pageIndex * pageSize);
        data.put("pageSize", pageSize);
        List<MallExchangeVO> resultList = mallExchangeOrdersMapper.selectMallExchangeByUserId(data);
        return resultList.size() > 0? resultList : null;
    }

    @Override
    public Integer queryMallExchangeCountByUserId(Integer userId) {
        return mallExchangeOrdersMapper.selectMallExchangeCountByUserId(userId);
    }

    @Override
    public MallExchangeVO queryExchangeRealDetailByUserId(Integer userId, Integer commId, Integer orderId) {
        return mallExchangeOrdersMapper.selectExchangeRealDetailByUserId(userId, commId, orderId);
    }

    @Override
    public MallExchangeVO queryExchangeEmptyDetailByUserId(Integer userId, Integer commId, Integer orderId) {
        return mallExchangeOrdersMapper.selectExchangeEmptyDetailByUserId(userId, commId, orderId);
    }
}
