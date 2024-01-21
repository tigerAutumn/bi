package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsUserRegistViewMapper;
import com.pinting.business.model.BsUserRegistView;
import com.pinting.business.model.BsUserRegistViewExample;
import com.pinting.business.model.vo.BsUserRegistViewVO;
import com.pinting.business.service.manage.BsUserRegisterService;

@Service
public class BsUserRegisterServiceImpl implements BsUserRegisterService{
	@Autowired
	private BsUserRegistViewMapper bsUserRegistViewMapper;

	@Override
	public BsUserRegistView selectByTime(String startDate, String endDate) {
		return bsUserRegistViewMapper.selectByTime(startDate, endDate);
	}

	@Override
	public void addRegisterView(BsUserRegistView record) {
		record.setCreateTime(new Date());
		bsUserRegistViewMapper.insertSelective(record);
		
	}

	@Override
	public BsUserRegistView selectByExample(BsUserRegistViewExample example) {
		List<BsUserRegistView> list = bsUserRegistViewMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			return list.size() > 0 ? list.get(0) : null;
		}
		return null;
	}

	@Override
	public void updateView(BsUserRegistView record) {
		bsUserRegistViewMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int findRegistViewAllCount(BsUserRegistViewVO viewVo) {
		return bsUserRegistViewMapper.selectCountByViewVO(viewVo);
	}

	@Override
	public List<BsUserRegistView> findRegistViewAllList(
			BsUserRegistViewVO viewVo) {
		return bsUserRegistViewMapper.selectListByViewVO(viewVo);
	}

}
