package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import com.pinting.business.dao.MRoleMenuMapper;
import com.pinting.business.model.MMenu;
import com.pinting.business.model.MRoleMenuExample;
import com.pinting.business.model.MRoleMenuKey;
import com.pinting.business.model.vo.MMenuVO;
import com.pinting.business.service.manage.MMenuService;
import com.pinting.business.service.manage.MRoleMenuService;
import com.pinting.business.util.BeanUtils;

@Service
public class MRoleMenuServiceImpl implements MRoleMenuService {
	@Autowired
	private MMenuService mMenuService;
	@Autowired
	private MRoleMenuMapper mRoleMenuMapper;
	
	@Override
	public List<MMenuVO> findMRoleMenuByRoleId(Integer roleId) {
		
		List<MMenuVO> parentMMenus = mMenuService.findParentMMenuByRoleId(roleId);
		List<MMenu> subMMenus = mMenuService.findSubMMenuByRoleId(roleId);
				
		if(parentMMenus != null && parentMMenus.size() > 0){
			for (MMenuVO menu : parentMMenus) {
				List<MMenu> childMenus = new ArrayList<MMenu>();
				for (MMenu subMenu : subMMenus) {
					if(subMenu.getParentId().equals(menu.getId())){
						childMenus.add(subMenu);
					}
				}
				List<HashMap<String, Object>> childMapMenus = BeanUtils.classToArrayList(childMenus);
				menu.setChildMenus(childMapMenus);
			}
		}
		
		return parentMMenus;
	}
	@Override
	public List<MMenuVO> findAllMRoleMenuForAssignByRoleId(Integer roleId) {
		
		List<MMenuVO> parentMMenus = mMenuService.findAllParentMMenus();
		List<MMenu> subMMenus = mMenuService.findSubMMenuByRoleId(roleId);
		List<MMenu> allSubMMenus = mMenuService.findAllSubMMenus();
		
		if(parentMMenus != null && parentMMenus.size() > 0){
			for (MMenuVO menu : parentMMenus) {
				List<MMenu> childMenus = new ArrayList<MMenu>();
				if(allSubMMenus != null && allSubMMenus.size() > 0){
					for (MMenu subMenu : allSubMMenus) {
						if(subMenu.getParentId().equals(menu.getId())){
							childMenus.add(subMenu);
						}
					}
					
					List<HashMap<String, Object>> childMapMenus = BeanUtils.classToArrayList(childMenus);
					
					for (HashMap<String, Object> tmpMap : childMapMenus) {
						if(subMMenus != null && subMMenus.size() > 0){
							for (MMenu tmpMenu : subMMenus) {
								if(tmpMap.get("id").equals(tmpMenu.getId())){
									tmpMap.put("assignedFlag", true);
									break;
								}
							}
						}else{
							break;
						}
					}
					
					menu.setChildMenus(childMapMenus);
				}else{
					break;
				}
			}
		}
		
		return parentMMenus;
	}
	
	@Transactional(rollbackFor=Exception.class)
	@Override
	public boolean modifyMRoleMenuByRoleIdAndMenuId(Integer roleId,
			String menuIds) {
		if(StringUtils.isEmpty(menuIds)){
			MRoleMenuExample example = new MRoleMenuExample();
			example.createCriteria().andRoleIdEqualTo(roleId);
			mRoleMenuMapper.deleteByExample(example);
			
			return true;
		}else{
			String[] ids = menuIds.split(",");
			if(ids != null && ids.length > 0){
				MRoleMenuExample example = new MRoleMenuExample();
				example.createCriteria().andRoleIdEqualTo(roleId);
				mRoleMenuMapper.deleteByExample(example);
				for (String menuId: ids) {
					MRoleMenuKey mRoleMenuKey = new MRoleMenuKey();
					mRoleMenuKey.setRoleId(roleId);
					mRoleMenuKey.setMenuId(Integer.valueOf(menuId));
					mRoleMenuMapper.insert(mRoleMenuKey);
				}
				return true;
			}
			return false;
		}
	}

}
