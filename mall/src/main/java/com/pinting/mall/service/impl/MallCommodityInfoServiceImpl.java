package com.pinting.mall.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.pinting.mall.dao.MallCommodityInfoMapper;
import com.pinting.mall.enums.MallInfoStatusEnum;
import com.pinting.mall.enums.PTMessageEnum;
import com.pinting.mall.exception.PTMessageException;
import com.pinting.mall.model.MallCommodityInfo;
import com.pinting.mall.model.MallCommodityInfoExample;
import com.pinting.mall.model.MallCommodityInfoWithBLOBs;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.manange.MallCommodityInfoVO;
import com.pinting.mall.service.MallCommodityInfoService;

/**
 * 商品管理服务
 *
 * @author zousheng
 * @date 2018-5-15
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class MallCommodityInfoServiceImpl implements MallCommodityInfoService {

    @Autowired
    private MallCommodityInfoMapper mallCommodityInfoMapper;

    @Override
    public PagerModelRspDTO<MallCommodityInfoVO> queryMallCommodityInfoList(MallCommodityInfoVO mallCommodityInfoVO, PagerReqDTO pagerReq) {

        PageHelper.startPage(pagerReq.getPageNum(), pagerReq.getNumPerPage());
        List<MallCommodityInfoVO> list = mallCommodityInfoMapper.selectMallCommodityInfoList(mallCommodityInfoVO);
        return new PagerModelRspDTO(pagerReq, list);
    }

    @Override
    public MallCommodityInfoWithBLOBs queryMallCommodityDetail(Integer id) {
        return mallCommodityInfoMapper.selectByPrimaryKey(id);
    }

    @Override
    public void addMallCommodityInfo(MallCommodityInfoWithBLOBs record) {
        mallCommodityInfoMapper.insertSelective(record);
    }

    @Override
    public void updateMallCommodityInfo(MallCommodityInfoWithBLOBs record) {
        mallCommodityInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void forSaleCommodity(Integer id, Integer mUserId) {
        MallCommodityInfoWithBLOBs record = new MallCommodityInfoWithBLOBs();
        record.setId(id);
        record.setStatus(MallInfoStatusEnum.FOR_SALE.getCode());
        record.setForSaleTime(new Date());
        record.setLastOperator(mUserId);
        record.setUpdateTime(new Date());

        mallCommodityInfoMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public void soldOutCommodity(Integer id, Integer mUserId) {
        MallCommodityInfoWithBLOBs record = new MallCommodityInfoWithBLOBs();
        record.setId(id);
        record.setStatus(MallInfoStatusEnum.SOLD_OUT.getCode());
        record.setLastOperator(mUserId);
        record.setUpdateTime(new Date());
        mallCommodityInfoMapper.updateByPrimaryKeySelective(record);
    }

	@Override
	public void deleteCommodity(Integer id, Integer mUserId) {
		MallCommodityInfoExample example = new MallCommodityInfoExample();
		example.createCriteria().andIdEqualTo(id);
		List<MallCommodityInfo> list = mallCommodityInfoMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			MallCommodityInfo info = list.get(0);
			if (info.getSoldCount() > 0) {
				throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "商品已兑换，不能删除");
			}
			MallCommodityInfoWithBLOBs record = new MallCommodityInfoWithBLOBs();
			record.setId(id);
			record.setStatus(MallInfoStatusEnum.DELETED.getCode());
			record.setLastOperator(mUserId);
			record.setUpdateTime(new Date());
			mallCommodityInfoMapper.updateByPrimaryKeySelective(record);
		} else {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, "商品不存在");
		}
	}
	
}
