package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSensitiveOperateJnlMapper;
import com.pinting.business.dao.BsSpecialJnlMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.model.BsSensitiveOperateJnl;
import com.pinting.business.model.BsSensitiveOperateJnlExample;
import com.pinting.business.model.BsSensitiveOperateJnlExample.Criteria;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.BsSpecialJnlExample;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsSysConfigExample;

import com.pinting.business.service.manage.MSysConfigService;
import com.pinting.business.util.Constants;

@Service
public class MSysConfigServiceImpl implements MSysConfigService {

	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	 
	@Autowired
	private BsSpecialJnlMapper bsSpecialJnlMapper;
	@Autowired
	private BsSensitiveOperateJnlMapper bsSensitiveOperateJnlMapper;
	@Override
	public List<BsSysConfig> findBsSysConfigsByPage(String pageNum,
			String numPerPage) {

		BsSysConfig bsSysConfig = new BsSysConfig();
		bsSysConfig.setPageNum(Integer.valueOf(pageNum));
		bsSysConfig.setNumPerPage(Integer.valueOf(numPerPage));
		List<BsSysConfig> list = bsSysConfigMapper.selectByPage(bsSysConfig);
		
		return list.size() > 0 ? list : null;
	}
	@Override
	public int countTotalBsSysConfigs() {
		BsSysConfigExample example = new BsSysConfigExample();
		return bsSysConfigMapper.countByExample(example);
	}
	@Override
	public BsSysConfig findSysConfigByKey(String confKey) {
		
		return bsSysConfigMapper.selectByPrimaryKey(confKey);
	}
	@Override
	public boolean saveOrUpdateSysConfig(BsSysConfig bsSysConfig,
			String updateFlag) {
		boolean returnFlag = false;
		switch (updateFlag) {
		case Constants.SYSCONFIG_UPDATEFLAG_CREATE:
			bsSysConfigMapper.insertSelective(bsSysConfig);
			returnFlag = true;
			break;
		case Constants.SYSCONFIG_UPDATEFLAG_UPDATE:
			bsSysConfigMapper.updateByPrimaryKeySelective(bsSysConfig);
			returnFlag = true;
			break;
		case Constants.SYSCONFIG_UPDATEFLAG_DELETE:
			bsSysConfigMapper.deleteByPrimaryKey(bsSysConfig.getConfKey());
			returnFlag = true;
			break;
		default:
			break;
		}
		return returnFlag;
	}
	@Override
	public int countTotalMSpecialJnl() {
		BsSpecialJnlExample example = new BsSpecialJnlExample();
		return bsSpecialJnlMapper.countByExample(example);
	}

	@Override
	public List<BsSpecialJnl> findBsSpecialJnlByPage(int pageNum, int numPerPage) {
		BsSpecialJnl bsSpecialJnl = new BsSpecialJnl();
		bsSpecialJnl.setPageNum(Integer.valueOf(pageNum));
		bsSpecialJnl.setNumPerPage(Integer.valueOf(numPerPage));
		List<BsSpecialJnl> list =bsSpecialJnlMapper.selectByPage(bsSpecialJnl);
		
		return list.size() > 0 ? list : null;
	}
	@Override
	public int countTotalBsSensitiveJnl(String name,String ip) {
		BsSensitiveOperateJnlExample example = new BsSensitiveOperateJnlExample();
		Criteria criteria=example.createCriteria();
		if(name!=null&& (!"".equals(name.trim())))
		{
			criteria.andUserNameLike("%"+name.trim()+"%");
		}
		if(ip!=null&& (!"".equals(ip.trim())))
		{
			criteria.andIpEqualTo("%"+ip.trim()+"%");
		}
		return bsSensitiveOperateJnlMapper.countByExample(example);
	}
	@Override
	public List<BsSensitiveOperateJnl> findBsSensitiveOperateJnlByPage(String name, String ip,
			int pageNum, int numPerPage) {
		BsSensitiveOperateJnl bsSensitiveOperateJnl = new BsSensitiveOperateJnl();
		bsSensitiveOperateJnl.setPageNum(Integer.valueOf(pageNum));
		bsSensitiveOperateJnl.setNumPerPage(Integer.valueOf(numPerPage));
		bsSensitiveOperateJnl.setUserName(name);
		bsSensitiveOperateJnl.setIp(ip);
		List<BsSensitiveOperateJnl> list =bsSensitiveOperateJnlMapper.selectByPage(bsSensitiveOperateJnl);
		
		return list.size() > 0 ? list : null;
	}

}
