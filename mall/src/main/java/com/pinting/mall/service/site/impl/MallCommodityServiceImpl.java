package com.pinting.mall.service.site.impl;

import java.util.ArrayList;
import java.util.List;

import com.pinting.mall.model.MallCommodityInfoWithBLOBs;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.mall.dao.MallCommodityInfoMapper;
import com.pinting.mall.dao.MallCommodityTypeMapper;
import com.pinting.mall.model.MallCommodityInfo;
import com.pinting.mall.model.MallCommodityType;
import com.pinting.mall.model.vo.MallCommodityVO;
import com.pinting.mall.service.site.MallCommodityService;
import com.pinting.mall.util.BeanUtils;

@Service
public class MallCommodityServiceImpl implements MallCommodityService {

	@Autowired
	private MallCommodityTypeMapper commTypeMapper;
	@Autowired
	private MallCommodityInfoMapper commInfoMapper;
	
	@Override
	public List<MallCommodityVO> getIndexList() {
		List<MallCommodityVO> list = new ArrayList<>();
		//查询类别
		List<MallCommodityType> typeList = commTypeMapper.selectCommTypeList4Index();
		if(CollectionUtils.isNotEmpty(typeList)){
			//循环类别查询商品列表
			for (MallCommodityType mallCommodityType : typeList) {
				List<MallCommodityInfo> infoList = commInfoMapper.selectCommInfoByTypeId(mallCommodityType.getId());
				if(CollectionUtils.isNotEmpty(infoList)){
					MallCommodityVO commVO = new MallCommodityVO();
					commVO.setCommInfoList(BeanUtils.classToArrayList(infoList));
					commVO.setCommTypeName(mallCommodityType.getCommTypeName());
					list.add(commVO);
				}
			}
		}
		
		return list;
	}

	@Override
	public MallCommodityInfoWithBLOBs getCommodityDetail(Integer id) {
		return commInfoMapper.selectByPrimaryKey(id);
	}
}
