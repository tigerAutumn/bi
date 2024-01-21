package com.pinting.mall.service;

import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.vo.MallUserPointsVO;

import java.util.List;

/**
 * 用户积分服务
 *
 * @author shh
 * @date 2018/5/17 9:38
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public interface MallUserPointsService {

    /**
     * 积分用户管理列表查询
     * @param record
     * @return
     */
    public List<MallUserPointsVO> queryMallUserPointsList(MallUserPointsVO record);

    /**
     * 积分用户管理记录数查询
     * @param record
     * @return
     */
    public int queryMallUserPointsCount(MallUserPointsVO record);

    /**
     * 积分用户管理列表查询-PageHelper分页
     * @param record
     * @return
     */
    public PagerModelRspDTO<MallUserPointsVO> queryMallUserPointsListNew(MallUserPointsVO record, PagerReqDTO pagerReq);

}
