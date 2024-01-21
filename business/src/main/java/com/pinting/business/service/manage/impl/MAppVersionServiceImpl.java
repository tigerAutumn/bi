package com.pinting.business.service.manage.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsAppVersionMapper;
import com.pinting.business.model.BsAppVersion;
import com.pinting.business.model.vo.BsAppVersionVO;
import com.pinting.business.service.manage.MAppVersionService;

/**
 * app版本管理Service
 * @author yanwl
 * @date 2016-02-18
 */
@Service
public class MAppVersionServiceImpl implements MAppVersionService {

	@Autowired
	private BsAppVersionMapper appVersionMapper;

	@Override
	public List<BsAppVersion> findAppVersion(Integer pageNum, Integer numPerPage) {
		BsAppVersionVO bsAppVersion = new BsAppVersionVO();
		bsAppVersion.setNumPerPage(numPerPage);
		bsAppVersion.setPageNum(pageNum);
		List<BsAppVersion> versions = appVersionMapper.selectAppVersion(bsAppVersion);
		return versions.size() > 0 ? versions : null;
	}

	@Override
	public int saveAppVersion(BsAppVersion bsAppVersion) {
		if(bsAppVersion.getIsMandatory() == 1) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("compareVersion", bsAppVersion.getVersion());
			map.put("appType", bsAppVersion.getAppType());
			List<BsAppVersion> versions = appVersionMapper.selectVersionByMap(map);
			if(versions != null && !versions.isEmpty() && versions.size() > 0) {
				for (BsAppVersion appVersion : versions) {
					appVersion.setIsMandatory(1);
					updateAppVersion(appVersion);
				}
			}
		}
		bsAppVersion.setIsMandatory(2);
		return appVersionMapper.insertBsAppVersion(bsAppVersion);
	}

	@Override
	public int findAllAppVersionCount() {
		return appVersionMapper.selectAllAppVersionCount();
	}

	@Override
	public List<BsAppVersion> findVersionByMap(Map<String,Object> map) {
		List<BsAppVersion> versions = appVersionMapper.selectVersionByMap(map);
		return versions.size() > 0 ? versions : null;
	}

	@Override
	public BsAppVersion findAppVersionById(Integer id) {
		return appVersionMapper.selectByPrimaryKey(id);
	}

	@Override
	public int updateAppVersion(BsAppVersion bsAppVersion) {
		return appVersionMapper.updateByPrimaryKeySelective(bsAppVersion);
	}

	@Override
	public int deleteAppVersionById(Integer id) {
		return appVersionMapper.deleteByPrimaryKey(id);
	}

	@Override
	public int findIsLastVersion(Map<String, Object> map) {
		return appVersionMapper.selectIsLastVersion(map);
	}

	@Override
	public List<BsAppVersion> findAppVersionMaxValue() {
		List<BsAppVersion> versionMaxs = appVersionMapper.selectAppVersionMaxValue();
		return versionMaxs.size() > 0 ? versionMaxs : null;
	}
	
}
