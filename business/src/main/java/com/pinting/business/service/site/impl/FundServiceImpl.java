package com.pinting.business.service.site.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.FdAppointMapper;
import com.pinting.business.dao.FdInfoMapper;
import com.pinting.business.dao.FdNetMapper;
import com.pinting.business.model.FdAppoint;
import com.pinting.business.model.FdAppointExample;
import com.pinting.business.model.FdInfo;
import com.pinting.business.model.FdInfoExample;
import com.pinting.business.model.FdNet;
import com.pinting.business.model.FdNetExample;
import com.pinting.business.service.site.FundService;

@Service
public class FundServiceImpl implements FundService{

	@Autowired
	private FdAppointMapper appointMapper;
	
	@Autowired
	private FdInfoMapper infoMapper;
	
	@Autowired
	private FdNetMapper netMapper;
	
	@Override
	public boolean addFund(FdAppoint appoint) {
		return appointMapper.insertSelective(appoint) == 1? true:false;
	}

	public List<FdInfo> findFdInfoListByPage(int start,int pageSize){
		
		HashMap<String, Object> paramDate=new HashMap<String, Object>();
		paramDate.put("start", start * pageSize);
		paramDate.put("pageSize", pageSize);
		
		List<FdInfo> dataList = infoMapper.selectByExamplePage(paramDate);
		return dataList.size() > 0? dataList : null;
		
	}

	@Override
	public int countNetValueList() {
		FdInfoExample example = new FdInfoExample();
		return infoMapper.countByExample(example);
	}

	@Override
	public List<FdNet> findFdNetByFundId(int fundId) {
		FdNetExample example = new FdNetExample();
		example.createCriteria().andFundIdEqualTo(fundId);
		example.setOrderByClause("date desc");
		List<FdNet> dataList = netMapper.selectByExample(example);
		return dataList.size() > 0? dataList : null;
	}

}
