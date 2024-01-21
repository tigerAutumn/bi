package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsPCAMapper;
import com.pinting.business.model.BsPCA;
import com.pinting.business.model.BsPCAExample;
import com.pinting.business.service.site.ProvinceService;

@Service
public class ProvinceServiceImpl implements ProvinceService {

	@Autowired
	private BsPCAMapper bsPCAMapper;
	
	@Override
	public List<BsPCA> findPCAByParentId(Integer parentId) {
		BsPCAExample  example = new BsPCAExample();
		example.createCriteria().andParentIdEqualTo(parentId);
		return (ArrayList<BsPCA>) bsPCAMapper.selectByExample(example);
	}

	@Override
	public List<BsPCA> findProvinces() {
		return bsPCAMapper.selectProvinces();
	}

	@Override
	public BsPCA findPCAByItemCode(String itemCode) {
		BsPCAExample  example = new BsPCAExample();
		example.createCriteria().andItemCodeEqualTo(itemCode);
		List<BsPCA> list = bsPCAMapper.selectByExample(example);
		return (list != null && list.size() == 1) ? list.get(0) : null;
	}

}
