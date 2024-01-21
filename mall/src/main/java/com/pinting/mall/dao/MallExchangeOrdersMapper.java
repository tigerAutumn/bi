package com.pinting.mall.dao;

import com.pinting.mall.model.MallExchangeOrders;
import com.pinting.mall.model.MallExchangeOrdersExample;
import com.pinting.mall.model.vo.MallExchangeOrdersCommodityVO;
import com.pinting.mall.model.vo.MallExchangeOrdersVO;

import java.util.List;
import java.util.Map;

import com.pinting.mall.model.vo.MallExchangeVO;
import org.apache.ibatis.annotations.Param;

public interface MallExchangeOrdersMapper {
    int countByExample(MallExchangeOrdersExample example);

    int deleteByExample(MallExchangeOrdersExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallExchangeOrders record);

    int insertSelective(MallExchangeOrders record);

    List<MallExchangeOrders> selectByExample(MallExchangeOrdersExample example);

    MallExchangeOrders selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallExchangeOrders record, @Param("example") MallExchangeOrdersExample example);

    int updateByExample(@Param("record") MallExchangeOrders record, @Param("example") MallExchangeOrdersExample example);

    int updateByPrimaryKeySelective(MallExchangeOrders record);

    int updateByPrimaryKey(MallExchangeOrders record);
    
    /**
     * 根据订单表id查询积分商城订单表数据，关联查询商品信息和商品类别信息
     * @author bianyatian
     * @param orderId
     * @return
     */
    MallExchangeOrdersCommodityVO selectByOrderId(@Param("orderId") Integer orderId);

    /**
     * 查询我的兑换列表
     * @param data
     * @return
     */
    List<MallExchangeVO> selectMallExchangeByUserId(Map<String, Object> data);

    /**
     * 根据userId查询我的兑换总数
     * @param userId
     * @return
     */
    int selectMallExchangeCountByUserId(@Param("userId") Integer userId);

    /**
     * 根据userId, commId, orderId查询我的兑换详情-实物商品
     * @param userId
     * @param commId 商品编号
     * @param orderId 订单表id
     * @return
     */
    MallExchangeVO selectExchangeRealDetailByUserId(@Param("userId") Integer userId, @Param("commId") Integer commId,
                                                @Param("orderId") Integer orderId);

    /**
     * 根据userId, commId, orderId查询我的兑换详情-虚拟商品
     * @param userId
     * @param commId 商品编号
     * @param orderId 订单表id
     * @return
     */
    MallExchangeVO selectExchangeEmptyDetailByUserId(@Param("userId") Integer userId, @Param("commId") Integer commId,
                                                @Param("orderId") Integer orderId);
    
    /**
     * 管理台订单管理列表
     * @param record
     * @return
     */
    List<MallExchangeOrdersVO> findManageExchangeOrdersList(MallExchangeOrdersVO record);
    
}