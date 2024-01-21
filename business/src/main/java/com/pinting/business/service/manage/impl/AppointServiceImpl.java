package com.pinting.business.service.manage.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.FdAppointMapper;
import com.pinting.business.model.FdAppoint;
import com.pinting.business.model.FdAppointExample;
import com.pinting.business.model.vo.FdAppointVO;
import com.pinting.business.service.manage.AppointService;

@Service
public class AppointServiceImpl implements AppointService {

	@Autowired
	public FdAppointMapper fdAppointMapper;
	
	@Override
	public ArrayList<FdAppointVO> findMFdAppointInfoList(FdAppoint fdAppoint) {
		return fdAppointMapper.selectMFdAppointInfoList(fdAppoint);
	}

	@Override
	public int countAppointList() {
		FdAppointExample fdAppointExample  = new FdAppointExample();
		return fdAppointMapper.countByExample(fdAppointExample);
	}

	@Override
	public FdAppoint findFdAppointById(int id) {
		return fdAppointMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean updateAppointById(FdAppoint appoint) {
		return fdAppointMapper.updateByPrimaryKeySelective(appoint)>0;
	}
	
	
	
	
}
