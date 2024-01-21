package com.pinting.mall.service.site;

import com.pinting.mall.model.vo.MallExchangeVO;

import java.util.List;

/**
 * 积分商城商品兑换服务
 *
 * @author shh
 * @date 2018/5/16 10:50
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface MallExchangeService {

    /**
     * 我的兑换-列表
     * @param userId
     * @param pageIndex
     * @param pageSize
     * @return
     */
    public List<MallExchangeVO>  queryMallExchangeByUserId(Integer userId, Integer pageIndex, Integer pageSize);

    /**
     * 根据userId查询我的兑换总数
     * @param userId
     * @return
     */
    public Integer queryMallExchangeCountByUserId(Integer userId);

    /**
     * 根据userId, commId查询我的兑换详情-实物商品
     * @param userId
     * @param commId 商品编号
     * @param orderId 订单表id
     * @return
     */
    public MallExchangeVO queryExchangeRealDetailByUserId(Integer userId, Integer commId, Integer orderId);

    /**
     * 根据userId, commId查询我的兑换详情-虚拟商品
     * @param userId
     * @param commId 商品编号
     * @param orderId 订单表id
     * @return
     */
    public MallExchangeVO queryExchangeEmptyDetailByUserId(Integer userId, Integer commId, Integer orderId);

}
