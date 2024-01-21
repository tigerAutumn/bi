package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.FdInfoMapper;
import com.pinting.business.dao.FdNetMapper;
import com.pinting.business.model.FdInfo;
import com.pinting.business.model.FdInfoExample;
import com.pinting.business.model.FdNet;
import com.pinting.business.model.FdNetExample;
import com.pinting.business.model.MUser;
import com.pinting.business.model.vo.FdNetVO;
import com.pinting.business.service.manage.MFundService;

@Service
public class MFundServiceImpl implements MFundService{

	
	@Autowired
	private FdInfoMapper infoMapper;
	@Autowired
	private FdNetMapper fdNetMapper;
	
	@Override
	public int countNetValueList() {
		FdInfoExample example = new FdInfoExample();
		return infoMapper.countByExample(example);
	}
	
	@Override
	public List<FdInfo> findMFdInfoList(FdInfo fdInfo) {
		return infoMapper.selectMFdInfoList(fdInfo);
	}

	@Override
	public FdInfo findFdInfoById(int id) {
		return infoMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean insertFdInfo(FdInfo fdInfo) {
		return infoMapper.insertSelective(fdInfo)>0;
	}

	@Override
	public boolean modifyFdInfoById(FdInfo fdInfo) {
		return infoMapper.updateByPrimaryKeySelective(fdInfo)>0;
	}

	@Override
	public boolean DeleteFdInfoByIds(List<Integer> idList,int status) {
		FdInfoExample example = new FdInfoExample();
		example.createCriteria().andIdIn(idList);
		FdInfo fdInfo = new FdInfo();
		fdInfo.setStatus(status);
		return infoMapper.updateByExampleSelective(fdInfo, example)>0;
	}



	@Override
	public int countNetList() {
		FdNetExample example = new FdNetExample();
		return fdNetMapper.countByExample(example);
	}

	@Override
	public FdNet findFdNetInfoById(int id) {
		return fdNetMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean insertNetInfo(FdNet fdNet) {
		return fdNetMapper.insertSelective(fdNet)>0;
	}

	@Override
	public boolean modifyNetInfoById(FdNet fdNet) {
		return fdNetMapper.updateByPrimaryKeySelective(fdNet)>0;
	}

	@Override
	public boolean DeleteNetInfoById(Integer id) {
		return fdNetMapper.deleteByPrimaryKey(id)>0;
	}

	@Override
	public List<FdNetVO> findMFdNetInfoList(FdNetVO fdNet) {
		return fdNetMapper.selectMFdNetInfoList(fdNet);
	}

	@Override
	public FdNet findFdNetInfoByfundIdAndDate(int fundid, Date date) {
		FdNetExample example = new FdNetExample();
		
		example.createCriteria().andFundIdEqualTo(fundid).andDateEqualTo(date);
		List<FdNet> mNetList = fdNetMapper.selectByExample(example);
		if(mNetList != null && mNetList.size() > 0)
		{
			return mNetList.get(0);
		}
		return null;
	}

} 	
