/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.ProductReleaseInfoVO;

/**
 * 理财计划发布服务
 * @author HuanXiong
 * @version $Id: ProductPublishService.java, v 0.1 2016-4-21 上午10:40:11 HuanXiong Exp $
 */
public interface ProductReleaseService {
    
    /**
     * 分页查询理财计划
     * @param req {
     *      name;    // 计划名称
     *      serialId;   // 计划系列(-1表示非系列)
     *      distributeStartTime; // 发布开始时间
     *      distributeEndTime;   // 发布结束时间
     *      productStartTime;   //  开始时间
     *      productEndTime; // 结束时间
     *      term;   // 期限
     *      baseRate;   // 利率
     *      statusForQuery;  // 状态
     *      isSuggest;  // 是否推荐
     * }
     * @return
     */
    public List<ProductReleaseInfoVO> queryReleaseProductGrid(ProductReleaseInfoVO req);
    
    /**
     * 统计理财计划
     * @param req {
     *      name;    // 计划名称
     *      serialId;   // 计划系列(-1表示非系列)
     *      distributeStartTime; // 发布开始时间
     *      distributeEndTime;   // 发布结束时间
     *      productStartTime;   //  开始时间
     *      productEndTime; // 结束时间
     *      term;   // 期限
     *      baseRate;   // 利率
     *      statusForQuery;  // 状态
     *      isSuggest;  // 是否推荐
     * }
     * @return
     */
    public Integer countReleaseProductGrid(ProductReleaseInfoVO req);
    
    /**
     * 发布理财计划
     * @param productId 产品ID
     * @param opId 操作员ID
     * @param startTime 开始时间（为空时，不做修改）
     * @param endTime 结束时间（为空时，不做修改）
     */
    public void releaseProduct(Integer productId, Integer opId, String startTime, String endTime);
    
    /**
     * 结束理财计划
     * @param productId 产品ID
     * @param opId 操作员ID
     */
    public void finishProduct(Integer productId, Integer opId);
    
    /**
     * 推荐理财产品
     * @param productId 产品ID
     * @param opId 操作员ID
     */
    public void recommendProduct(Integer productId, Integer opId);
    
    /**
     * 取消推荐理财产品
     * @param productId 产品ID
     * @param opId 操作员ID
     */
    public void cancelRecommendProduct(Integer productId, Integer opId);
    
}
