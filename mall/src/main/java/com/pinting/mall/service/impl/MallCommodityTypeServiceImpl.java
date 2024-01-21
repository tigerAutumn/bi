package com.pinting.mall.service.impl;

import com.github.pagehelper.PageHelper;
import com.pinting.mall.dao.MallCommodityTypeMapper;
import com.pinting.mall.model.MallCommodityType;
import com.pinting.mall.model.MallCommodityTypeExample;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.service.MallCommodityTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品类别服务
 *
 * @author shh
 * @date 2018/5/11 14:45
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class MallCommodityTypeServiceImpl implements MallCommodityTypeService {

    @Autowired
    private MallCommodityTypeMapper mallCommodityTypeMapper;

    @Override
    public int addMallCommodityType(MallCommodityType record) {
        return mallCommodityTypeMapper.insertSelective(record);
    }

    @Override
    public int updatMallCommodityTypeById(MallCommodityType record) {
        return mallCommodityTypeMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public MallCommodityType queryMallCommodityTypeById(Integer id) {
        return mallCommodityTypeMapper.selectByPrimaryKey(id);
    }

    @Override
    public MallCommodityType queryCommTypeName(MallCommodityType record) {
        return mallCommodityTypeMapper.selectCommTypeName(record);
    }

    @Override
    public PagerModelRspDTO<MallCommodityType> queryMallCommodityTypelList(PagerReqDTO pagerReq) {
        PageHelper.startPage(pagerReq.getPageNum(), pagerReq.getNumPerPage());
        List<MallCommodityType> list = mallCommodityTypeMapper.selectMallCommodityTypelList();
        return new PagerModelRspDTO(pagerReq, list);
    }

    @Override
    public List<MallCommodityType> queryMallCommodityTypeAll() {
        MallCommodityTypeExample example = new MallCommodityTypeExample();
        example.setOrderByClause("update_time desc");
        return mallCommodityTypeMapper.selectByExample(example);
    }
}
