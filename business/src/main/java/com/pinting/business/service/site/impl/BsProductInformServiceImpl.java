package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsProductInformMapper;
import com.pinting.business.model.BsProductInform;
import com.pinting.business.model.BsProductInformExample;
import com.pinting.business.model.vo.ProductInformVO;
import com.pinting.business.service.site.BsProductInformService;
import com.pinting.business.util.Constants;

@Service
public class BsProductInformServiceImpl implements BsProductInformService {

	@Autowired
    private BsProductInformMapper bsProductInformMapper;
    
	@Override
	public int countByUserIdProductId(BsProductInform record) {
		BsProductInformExample example = new BsProductInformExample();
		example.createCriteria().andUserIdEqualTo(record.getUserId())
			.andProductIdEqualTo(record.getProductId());
		List<BsProductInform> list = bsProductInformMapper.selectByExample(example);
		return list.size();
	}

	@Override
	public void addProductInform(BsProductInform record) {
		record.setCreateTime(new Date());
		record.setUpdateTime(new Date());
		bsProductInformMapper.insertSelective(record);
	}

	@Override
	public void delete(Integer id) {
		bsProductInformMapper.deleteByPrimaryKey(id);
	}

	@Override
	public List<ProductInformVO> getListByTime(Date startTime, Date endTime, String remindType) {
		
		
		return bsProductInformMapper.getListByTime(startTime, endTime, remindType);
	}

	@Override
	public Integer countByUserTime(BsProductInform record) {
		BsProductInformExample example = new BsProductInformExample();
		example.createCriteria().andUserIdEqualTo(record.getUserId())
			.andRemindTypeEqualTo(record.getRemindType())
			.andCreateTimeGreaterThanOrEqualTo(record.getCreateTime());
		List<BsProductInform> list = bsProductInformMapper.selectByExample(example);
		return list.size();
	}

}
