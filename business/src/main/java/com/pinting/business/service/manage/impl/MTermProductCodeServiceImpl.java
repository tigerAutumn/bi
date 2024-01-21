package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsTermProductCodeMapper;
import com.pinting.business.model.BsTermProductCode;
import com.pinting.business.model.BsTermProductCodeExample;
import com.pinting.business.service.manage.MTermProductCodeService;

@Service
public class MTermProductCodeServiceImpl implements MTermProductCodeService{
	@Autowired
	private BsTermProductCodeMapper termProductCodeMapper;

	@Override
	public List<BsTermProductCode> findBsTermProductCodes(
			BsTermProductCodeExample example) {
	    return termProductCodeMapper.selectDistinctTerm(example);
//		return termProductCodeMapper.selectByExample(example);
	}
}
