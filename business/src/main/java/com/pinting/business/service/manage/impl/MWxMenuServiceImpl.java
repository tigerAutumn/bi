package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.MWxMenuMapper;
import com.pinting.business.model.MWxMenu;
import com.pinting.business.model.MWxMenuExample;
import com.pinting.business.model.wxmenu.Button;
import com.pinting.business.model.wxmenu.ClickButton;
import com.pinting.business.model.wxmenu.ComplexButton;
import com.pinting.business.model.wxmenu.Menu;
import com.pinting.business.model.wxmenu.ViewButton;
import com.pinting.business.service.manage.MWxMenuService;

/**
 * 微信菜单管理Service
 * @author yanwl
 * @date 2015-12-08
 */
@Service
public class MWxMenuServiceImpl implements MWxMenuService {

	@Autowired
	private MWxMenuMapper menuMapper;
	
	/**
	 * 查询所有menu
	 */
	@Override
	public List<MWxMenu> findWxMenu(MWxMenuExample example) {
		List<MWxMenu> menus = menuMapper.selectByExample(example);
		return menus;
	}

	/**
	 * menu转json字符串
	 */
	@Override
	public String menu2Json() {
		MWxMenuExample example = new MWxMenuExample();
		example.setDistinct(false);
		example.setOrderByClause("id asc");
		List<MWxMenu> wxMenus = menuMapper.selectByExample(example);
		Menu menu = WxMenu2Menu(wxMenus);
		String json = JSONObject.fromObject(menu).toString();
		return json;
	}
	
	/**
	 * dao层菜单转Menu
	 * @param wxMenus
	 * @return
	 */
	private Menu WxMenu2Menu(List<MWxMenu> wxMenus) {
		Menu menu = new Menu();
		List<Button> buttons = new ArrayList<Button>();
		for (MWxMenu wxMenu : wxMenus) {
			// 所有父菜单
			if(null != wxMenu.getParentId() && 0 == wxMenu.getParentId()) {
				MWxMenuExample example2 = new MWxMenuExample();
				example2.setDistinct(false);
				example2.createCriteria().andParentIdEqualTo(wxMenu.getId());
				List<MWxMenu> list = menuMapper.selectByExample(example2);
				
				// 查询所有无子菜单的父菜单，即子菜单的parentID不等于当前菜单的id
				if(null == list || 0 == list.size()) {
					if("view".equals(wxMenu.getType())) {
						ViewButton viewButton = new ViewButton();
						viewButton.setName(wxMenu.getName());
						viewButton.setType(wxMenu.getType());
						viewButton.setUrl(wxMenu.getUrl());
						buttons.add(viewButton);
					} else if("click".equals(wxMenu.getType())) {
						ClickButton clickButton = new ClickButton();
						clickButton.setName(wxMenu.getName());
						clickButton.setType(wxMenu.getType());
						clickButton.setKey(wxMenu.getUrl());
						buttons.add(clickButton);
					}
				} else {	// 含有子菜单
					ComplexButton complexButton = new ComplexButton();
					complexButton.setName(wxMenu.getName());
					Button[] btns = new Button[list.size()];
					for (int i = 0; i < btns.length; i++) {
						if("view".equals(list.get(i).getType())) {
							ViewButton viewButton = new ViewButton();
							viewButton.setName(list.get(i).getName());
							viewButton.setType(list.get(i).getType());
							viewButton.setUrl(list.get(i).getUrl());
							btns[i] = viewButton;
						} else if("click".equals(list.get(i).getType())) {
							ClickButton clickButton = new ClickButton();
							clickButton.setName(list.get(i).getName());
							clickButton.setType(list.get(i).getType());
							clickButton.setKey(list.get(i).getUrl());
							btns[i] = clickButton;
						} 
					}
					complexButton.setSub_button(btns);
					buttons.add(complexButton);
				}
			}
		}
		Button[] btns = new Button[buttons.size()];
		for (int i = 0; i < btns.length; i++) {
			btns[i] = buttons.get(i);
		}
		menu.setButton(btns);
		return menu;
	}
	
	/**
	 * 保存微信菜单数据
	 */
	public int saveWxMenu(MWxMenu wxMenu){
		return menuMapper.insertSelective(wxMenu);
	}

	/**
	 * 删除菜单项
	 */
	@Override
	public boolean deleteMenuById(Integer id) {
		int num = menuMapper.deleteByPrimaryKey(id);
		if(num > 0) {
			return true;
		}
		return false;
	}

	/**
	 * 查询父菜单
	 */
	@Override
	public List<MWxMenu> findParentMenus() {
		MWxMenuExample example = new MWxMenuExample();
		example.setDistinct(false);
		example.createCriteria().andParentIdEqualTo(0);
		return menuMapper.selectByExample(example);
	}

	/**
	 * 根据id查询
	 */
	@Override
	public MWxMenu getMWxMenuById(Integer id) {
		return menuMapper.selectByPrimaryKey(id);
	}

	/**
	 * 修改菜单
	 */
	@Override
	public int updateMWxMenu(MWxMenu wxMenu) {
		
		return menuMapper.updateByPrimaryKeySelective(wxMenu);
	}
}
