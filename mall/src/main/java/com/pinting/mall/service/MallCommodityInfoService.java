package com.pinting.mall.service;

import com.pinting.mall.model.MallCommodityInfoWithBLOBs;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.manange.MallCommodityInfoVO;

/**
 * 商品管理service
 *
 * @author zousheng
 * @date 2018-5-15
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface MallCommodityInfoService {

    /**
     * 查询商品列表
     *
     * @param mallCommodityInfoVO 查询条件
     * @param pagerReq            分页参数
     * @return
     */
    PagerModelRspDTO<MallCommodityInfoVO> queryMallCommodityInfoList(MallCommodityInfoVO mallCommodityInfoVO, PagerReqDTO pagerReq);

    /**
     * 查询商品详情信息
     *
     * @param id 商品id
     * @return
     */
    MallCommodityInfoWithBLOBs queryMallCommodityDetail(Integer id);

    /**
     * 新增商品信息
     *
     * @param record 商品信息
     */
    void addMallCommodityInfo(MallCommodityInfoWithBLOBs record);

    /**
     * 更新商品信息
     *
     * @param record
     */
    void updateMallCommodityInfo(MallCommodityInfoWithBLOBs record);

    /**
     * 兑换商品上架
     *
     * @param id      商品ID
     * @param mUserId 操作人
     */
    void forSaleCommodity(Integer id, Integer mUserId);

    /**
     * 兑换商品下架
     *
     * @param id      商品ID
     * @param mUserId 操作人
     */
    void soldOutCommodity(Integer id, Integer mUserId);
    
    /**
     * 商品删除
     * @param id 		商品ID
     * @param mUserId 	操作人
     */
    void deleteCommodity(Integer id, Integer mUserId);
    
}
