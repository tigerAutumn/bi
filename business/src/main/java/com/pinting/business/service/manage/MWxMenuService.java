package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.MWxMenu;
import com.pinting.business.model.MWxMenuExample;



public interface MWxMenuService {
	
	/**
	 * 查询所有微信菜单项
	 * @param example
	 * @return
	 */
	public List<MWxMenu> findWxMenu(MWxMenuExample example);
	
	/**
	 * 存储微信菜单数据
	 * @param wxMenu
	 */
	public int saveWxMenu(MWxMenu wxMenu);
	/**
	 * 将菜单项转换成json字符串
	 * @return
	 */
	public String menu2Json();

	/**
	 * 删除菜单项
	 * @param id
	 * @return
	 */
	public boolean deleteMenuById(Integer id);

	/**
	 * 查询所有父菜单
	 * @return
	 */
	public List<MWxMenu> findParentMenus();
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public MWxMenu getMWxMenuById(Integer id);
	
	/**
	 * 修改菜单
	 * @param wxMenu
	 * @return
	 */
	public int updateMWxMenu(MWxMenu wxMenu);
}
