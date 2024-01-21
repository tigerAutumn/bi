package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.MMenu;
import com.pinting.business.model.vo.MMenuRoleVO;
import com.pinting.business.model.vo.MMenuVO;

/**
 * 菜单接口
 * @Project: business
 * @Title: MMenuService.java
 * @author dingpf
 * @date 2015-1-28 下午4:58:26
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface MMenuService {
	/**
	 * 根据角色编号查询该角色有权限的一级菜单
	 * @param roleId 角色编号
	 * @return 成功返回角色一级菜单列表，否则返回null
	 */
	public List<MMenuVO> findParentMMenuByRoleId(Integer roleId);
	
	/**
	 * 根据角色编号查询该角色有权限的二级子菜单
	 * @param roleId 角色编号
	 * @return 成功返回角色二级子菜单列表，否则返回null
	 */
	public List<MMenu> findSubMMenuByRoleId(Integer roleId);
	
	/**
	 * 查询所有二级子菜单
	 * @return 成功返回角色二级子菜单列表，否则返回null
	 */
	public List<MMenu> findAllSubMMenus();
	/**
	 * 查询所有一级菜单
	 * @return 成功返回角色一级菜单列表，否则返回null
	 */
	public List<MMenuVO> findAllParentMMenus();
	
	/**
	 * 查询所有菜单子链接
	 * @return
	 */
	public List<MMenuRoleVO> findAllChildMenu();
}
