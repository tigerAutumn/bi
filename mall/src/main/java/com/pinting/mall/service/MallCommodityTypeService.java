package com.pinting.mall.service;

import com.pinting.mall.model.MallCommodityType;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;

import java.util.List;

/**
 * 商品类别service
 *
 * @author shh
 * @date 2018/5/11 14:23
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface MallCommodityTypeService {

    /**
     * 添加商品类别
     * @param record
     * @return
     */
    int addMallCommodityType(MallCommodityType record);

    /**
     * 根据id修改商品类别信息
     * @param record
     * @return
     */
    int updatMallCommodityTypeById(MallCommodityType record);

    /**
     * 根据id查询
     * @param id
     * @return
     */
    MallCommodityType queryMallCommodityTypeById(Integer id);

    /**
     * 查询商品类别名称是否已存在
     * @param record
     * @return
     */
    MallCommodityType queryCommTypeName(MallCommodityType record);

    /**
     * 商品类别列表
     *
     * @return
     */
    PagerModelRspDTO<MallCommodityType> queryMallCommodityTypelList(PagerReqDTO pagerReq);

    /**
     * 查询所有商品类别
     * @return
     */
    List<MallCommodityType> queryMallCommodityTypeAll();
}
