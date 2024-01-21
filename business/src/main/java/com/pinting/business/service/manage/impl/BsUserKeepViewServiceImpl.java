package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsUserKeepViewMapper;
import com.pinting.business.model.BsUserKeepView;
import com.pinting.business.model.vo.BsUserKeepViewVO;
import com.pinting.business.service.manage.BsUserKeepViewService;

@Service
public class BsUserKeepViewServiceImpl implements BsUserKeepViewService {
	
	@Autowired
	private BsUserKeepViewMapper bsUserKeepViewMapper;

	@Override
	public void addKeepView(BsUserKeepView record) {
		bsUserKeepViewMapper.insertSelective(record);
	}

	@Override
	public List<BsUserKeepViewVO> findUserKeepViewList(BsUserKeepViewVO record) {
		List<BsUserKeepViewVO> list =  bsUserKeepViewMapper.selectUserKeepViewList(record);
		return list.size() > 0 ? list : null;
	}

	@Override
	public Integer findUserKeepViewCount(BsUserKeepViewVO record) {
		int count = bsUserKeepViewMapper.selectUserKeepViewCount(record);
		return count;
	}

	@Override
	public void updateKeepView(BsUserKeepView record) {
		record.setUpdateTime(new Date());
		bsUserKeepViewMapper.updateByPrimaryKeySelective(record);
	}

}
