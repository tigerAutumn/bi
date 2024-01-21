package com.pinting.mall.facade;

import com.pinting.mall.hessian.site.message.ReqMsg_MallCommodity_GetDetail;
import com.pinting.mall.hessian.site.message.ReqMsg_MallCommodity_IndexList;
import com.pinting.mall.hessian.site.message.ResMsg_MallCommodity_GetDetail;
import com.pinting.mall.hessian.site.message.ResMsg_MallCommodity_IndexList;
import com.pinting.mall.model.MallCommodityInfoWithBLOBs;
import com.pinting.mall.model.vo.MallCommodityVO;
import com.pinting.mall.service.site.MallCommodityService;
import com.pinting.mall.util.BeanUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 商城商品相关
 *
 * @author bianyatian
 * @project mall
 * @2018-5-15 下午2:04:33
 */
@Component("MallCommodity")
public class MallCommodityFacade {
    private static Logger logger = LoggerFactory.getLogger(MallCommodityFacade.class);

    @Autowired
    private MallCommodityService mallCommodityService;

    /**
     * 积分商城首页商品列表
     *
     * @param req
     * @param res
     * @author bianyatian
     */
    public void indexList(ReqMsg_MallCommodity_IndexList req, ResMsg_MallCommodity_IndexList res) {

        List<MallCommodityVO> list = mallCommodityService.getIndexList();
        if (CollectionUtils.isNotEmpty(list)) {
            res.setCommodityList(BeanUtils.classToArrayList(list));
        }
    }

    /**
     * 查询商品详情信息
     *
     * @param req
     * @param res
     */
    public void getDetail(ReqMsg_MallCommodity_GetDetail req, ResMsg_MallCommodity_GetDetail res) {

        MallCommodityInfoWithBLOBs mallDetail = mallCommodityService.getCommodityDetail(req.getId());
        if (mallDetail != null) {
            res.setId(mallDetail.getId());
            res.setCommName(mallDetail.getCommName());
            res.setCommPictureUrl(mallDetail.getCommPictureUrl());
            res.setCommProperty(mallDetail.getCommProperty());
            res.setLeftCount(mallDetail.getLeftCount());
            res.setPoints(mallDetail.getPoints());
            res.setSoldCount(mallDetail.getSoldCount());
            res.setStatus(mallDetail.getStatus());
            res.setIsRecommend(mallDetail.getIsRecommend());
            res.setExchangeNote(mallDetail.getExchangeNote());
            res.setCommDetails(mallDetail.getCommDetails());
        }
    }
}
