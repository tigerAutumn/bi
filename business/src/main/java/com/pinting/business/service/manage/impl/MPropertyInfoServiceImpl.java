package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsPropertyInfoMapper;
import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.model.BsPropertyInfoExample;
import com.pinting.business.service.manage.MPropertyInfoService;

@Service
public class MPropertyInfoServiceImpl implements MPropertyInfoService {
	@Autowired
	private BsPropertyInfoMapper propertyInfoMapper;

	@Override
	public List<BsPropertyInfo> findBsPropertyInfos(BsPropertyInfoExample example) {
		return propertyInfoMapper.selectByExample(example);
	}

	@Override
	public ArrayList<BsPropertyInfo> findPropertyInfoInfo(int pageNum,
			int numPerPage, String orderDirection, String orderField) {
		BsPropertyInfo bsPropertyInfo = new BsPropertyInfo();
		bsPropertyInfo.setPageNum(pageNum);
		bsPropertyInfo.setNumPerPage(numPerPage);
		if(orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			bsPropertyInfo.setOrderDirection(orderDirection);
			bsPropertyInfo.setOrderField(orderField);
		}
		return propertyInfoMapper.selectPropertyInfoList(bsPropertyInfo);
	}
        
	@Override
	public int findCountPropertyInfo() {
		return propertyInfoMapper.countByExample(null);
	}
	
	@Override
	public BsPropertyInfo findPropertyInfoName(BsPropertyInfo record) {
		return propertyInfoMapper.selectPropertyInfoName(record);
	}

	@Override
	public int findCountByProductId(Integer id) {
		return propertyInfoMapper.selectCountByProductId(id);
	}

	@Override
	public int updatePropertyInfo(BsPropertyInfo record) {
		return propertyInfoMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int addPropertyInfo(BsPropertyInfo record) {
		return propertyInfoMapper.insert(record);
	}

	@Override
	public BsPropertyInfo selectByPrimaryId(Integer id) {
		return propertyInfoMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deletePropertyInfoById(Integer id) {
		return propertyInfoMapper.deleteByPrimaryKey(id);
	}
	
}
