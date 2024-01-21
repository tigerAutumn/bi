package com.pinting.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.pinting.mall.dao.MallAccountMapper;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.vo.MallUserPointsVO;
import com.pinting.mall.service.MallUserPointsService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户积分服务
 *
 * @author shh
 * @date 2018/5/17 9:39
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class MallUserPointsServiceImpl implements MallUserPointsService {

    @Autowired
    private MallAccountMapper mallAccountMapper;

    @Override
    public List<MallUserPointsVO> queryMallUserPointsList(MallUserPointsVO record) {
        List<MallUserPointsVO> resultList =  mallAccountMapper.selectMallUserPointsList(record);
        return (CollectionUtils.isEmpty(resultList)) ? null : resultList;
    }

    @Override
    public int queryMallUserPointsCount(MallUserPointsVO record) {
        // 只计数，不查询列表
        PageHelper.startPage(1, -1);
        List<MallUserPointsVO> list = mallAccountMapper.selectMallUserPointsList(record);
        return new PagerModelRspDTO(new PagerReqDTO(), list).getTotalRow();
    }

    @Override
    public PagerModelRspDTO<MallUserPointsVO> queryMallUserPointsListNew(MallUserPointsVO record, PagerReqDTO pagerReq) {
        PageHelper.startPage(pagerReq.getPageNum(), pagerReq.getNumPerPage());
        List<MallUserPointsVO> resultList =  mallAccountMapper.selectMallUserPointsList(record);
        return new PagerModelRspDTO(pagerReq, resultList);
    }
}
