package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.MMenu;
import com.pinting.business.model.vo.MMenuVO;

/**
 * 角色权限菜单接口
 * @Project: business
 * @Title: MRoleMenuService.java
 * @author dingpf
 * @date 2015-1-28 下午4:57:54
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface MRoleMenuService {
	/**
	 * 根据角色编号查询角色菜单
	 * @param roleId 角色编号
	 * @return 成功返回角色菜单列表，否则返回null
	 */
	public List<MMenuVO> findMRoleMenuByRoleId(Integer roleId);
	
	/**
	 * 查询所有菜单，并根据角色编号 标注 已分配给该角色的菜单
	 * @param roleId
	 * @return
	 */
	public List<MMenuVO> findAllMRoleMenuForAssignByRoleId(Integer roleId);
	
	/**
	 * 根据角色编号和菜单编号 进行 角色权限菜单分配
	 * @param roleId
	 * @param menuIds 多个id，以逗号隔开的 字符串
	 * @return 成功返回true，否则返回false
	 */
	public boolean modifyMRoleMenuByRoleIdAndMenuId(Integer roleId, String menuIds);
}
