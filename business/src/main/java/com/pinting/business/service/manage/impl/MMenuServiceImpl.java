package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.MMenuMapper;
import com.pinting.business.model.MMenu;
import com.pinting.business.model.MMenuExample;
import com.pinting.business.model.vo.MMenuRoleVO;
import com.pinting.business.model.vo.MMenuVO;
import com.pinting.business.service.manage.MMenuService;

@Service
public class MMenuServiceImpl implements MMenuService {

	@Autowired
	private MMenuMapper mMenuMapper;

	@Override
	public List<MMenuVO> findParentMMenuByRoleId(Integer roleId) {
		List<MMenuVO> menus = mMenuMapper.selectParentMenuByRoleId(roleId);
		return menus.size() > 0 ? menus : null;
	}

	@Override
	public List<MMenu> findSubMMenuByRoleId(Integer roleId) {
		List<MMenu> menus = mMenuMapper.selectSubMenuByRoleId(roleId);
		return menus.size() > 0 ? menus : null;
	}

	@Override
	public List<MMenu> findAllSubMMenus() {
		MMenuExample example = new MMenuExample();
		example.createCriteria().andParentIdNotEqualTo(0);
		List<MMenu> menus = mMenuMapper.selectByExample(example);
		return menus.size() > 0 ? menus : null;
	}

	@Override
	public List<MMenuVO> findAllParentMMenus() {
		List<MMenuVO> menus = mMenuMapper.selectAllParentMenu();
		return menus.size() > 0 ? menus : null;
	}

	@Override
	public List<MMenuRoleVO> findAllChildMenu() {
		return mMenuMapper.selectAllChildMenu();
	}
	

}
