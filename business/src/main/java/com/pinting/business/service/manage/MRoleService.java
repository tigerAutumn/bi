package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.MRole;

public interface MRoleService {
	/**
	 * 查询角色列表
	 * @param expId 需要排除的id
	 * @return 如果成功，则返回用户list，否则返回null
	 */
	public List<MRole> findMRoleList(Integer expId);
	/**
	 * 根据id删除某一个角色
	 * @param id 角色id
	 * @return 如果成功，则返回true，否则返回false
	 */
	public boolean removeRole(int id);
	/**
	 * 查询某一个角色
	 * @param id 角色id
	 * @return 如果成功，则返回用户角色信息，否则返回null
	 */
	public MRole findRoleById(int id);
	/**
	 * 根据角色名查询某一个角色是否存在
	 * @param name 角色名
	 * @return 如果成功，则返回true，否则返回false
	 */
	public boolean findRoleByName(String name);
	/**
	 * 修改角色
	 * @param MRole 角色信息
	 * @return 如果成功，则返回true，否则返回false
	 */
	public boolean modifyRole(MRole mrole);
	/**
	 * 修改角色
	 * @param MRole 角色信息
	 * @return 如果成功，则返回true，否则返回false
	 */
	public boolean createRole(MRole mrole);
	
	/**
	 * 根据达飞roleId查询角色
	 * @param dafyRoleId
	 * @return
	 */
	public MRole selectMRoleByDafyRoleId(Integer dafyRoleId);
}
