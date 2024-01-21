package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAppActiveMapper;
import com.pinting.business.model.BsAppActive;
import com.pinting.business.model.BsAppActiveExample;
import com.pinting.business.model.vo.AppActiveVO;
import com.pinting.business.service.manage.MAppActiveService;
import com.pinting.gateway.in.util.MethodRole;

@Service
public class MAppActiveServiceImpl implements MAppActiveService{
	@Autowired
	private BsAppActiveMapper appActiveMapper;

	@Override
	public List<AppActiveVO> findAppActiveListByPage(AppActiveVO record) {
		return appActiveMapper.selectAppActiveListByPage(record);
	}

	@Override
	public int findAppActiveTotalRows(AppActiveVO record) {
		return appActiveMapper.selectAppActiveTotalRows(record);
	}

	@Override
	public int saveAppActive(BsAppActive record) {
		return appActiveMapper.insertSelective(record);
	}

	@Override
	public int updateAppActive(BsAppActive record) {
		return appActiveMapper.updateByPrimaryKey(record);
	}

	@Override
	public int deleteAppActive(Integer id) {
		return appActiveMapper.deleteByPrimaryKey(id);
	}

	@Override
	public BsAppActive findAppActiveById(Integer id) {
		return appActiveMapper.selectByPrimaryKey(id);
	}

	@Override
	@MethodRole("APP")
	public List<BsAppActive> selectAppActive(Date now) {
		return appActiveMapper.selectActivePage("BGW_APP", 0, 20);
	}

	@Override
	public BsAppActive selectLatestActive() {
		return appActiveMapper.selectLatestActive();
	}
}
