package com.pinting.mall.service.site;

import com.pinting.mall.model.MallCommodityInfoWithBLOBs;
import com.pinting.mall.model.vo.MallCommodityVO;

import java.util.List;

/**
 * 积分商城商品相关
 *
 * @author bianyatian
 * @project mall
 * @2018-5-15 下午2:20:37
 */
public interface MallCommodityService {

    /**
     * 获取积分商城首页商品列表
     *
     * @return
     * @author bianyatian
     */
    List<MallCommodityVO> getIndexList();

    /**
     * 查询商品详情信息
     *
     * @param id
     * @return
     */
    MallCommodityInfoWithBLOBs getCommodityDetail(Integer id);
}
