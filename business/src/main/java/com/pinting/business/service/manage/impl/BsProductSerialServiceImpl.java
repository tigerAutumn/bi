package com.pinting.business.service.manage.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsProductSerialMapper;
import com.pinting.business.model.BsProductSerial;
import com.pinting.business.service.manage.BsProductSerialService;

@Service
public class BsProductSerialServiceImpl implements BsProductSerialService {
	
	@Autowired
	private BsProductSerialMapper bsProductSerialMapper;
	
	@Override
	public ArrayList<BsProductSerial> findProductSerialInfo(int pageNum, int numPerPage, String orderDirection, String orderField) {
		BsProductSerial bsProductSerial = new BsProductSerial();
		bsProductSerial.setPageNum(pageNum);
		bsProductSerial.setNumPerPage(numPerPage);
		if(orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			bsProductSerial.setOrderDirection(orderDirection);
			bsProductSerial.setOrderField(orderField);
		}
		return bsProductSerialMapper.selectProductSerialInfo(bsProductSerial);
	}

	@Override
	public int selectCountProductSerial() {
		return bsProductSerialMapper.selectCountProductSerial();
	}

	@Override
	public BsProductSerial selectBsProductSerial(BsProductSerial record) {
		return bsProductSerialMapper.selectBsProductSerial(record);
	}

	@Override
	public int selectCountOfSerialId(Integer serialId) {
		return bsProductSerialMapper.selectCountOfProductName(serialId);
	}
	
	@Override
	public int updateProductSerial(BsProductSerial record) {
		return bsProductSerialMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int addProductSerial(BsProductSerial record) {
		return bsProductSerialMapper.insert(record);
	}

	@Override
	public BsProductSerial selectByPrimaryId(Integer id) {
		return bsProductSerialMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteProductSerialById(Integer id) {
		return bsProductSerialMapper.deleteByPrimaryKey(id);
	}

}
