package com.pinting.business.facade.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuAdd;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuModify;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_MWxMenuUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_WxMenuListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxMenu_WxParentMenusListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuAdd;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuDelete;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuModify;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_MWxMenuUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_WxMenuListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MWxMenu_WxParentMenusListQuery;
import com.pinting.business.model.MWxMenu;
import com.pinting.business.model.MWxMenuExample;
import com.pinting.business.service.manage.MWxMenuService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.ConstantUtil;

/**
 * 
 * @Project: business
 * @Title: AccountFacade.java
 * @author yanwl
 * @date 2015-12-08 下午3:34:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MWxMenu")
public class MWxMenuFacade{
	@Autowired
	private MWxMenuService mWxMenuService;
	
	public void wxMenuListQuery(ReqMsg_MWxMenu_WxMenuListQuery reqMsg,ResMsg_MWxMenu_WxMenuListQuery resMsg) {
		MWxMenuExample example = new MWxMenuExample();
        example.setDistinct(false);
        example.setOrderByClause("id asc");
        List<MWxMenu> menus = mWxMenuService.findWxMenu(example);
        resMsg.setWxMenuList(BeanUtils.classToArrayList(menus));
	}
	
	public void wxParentMenusListQuery(ReqMsg_MWxMenu_WxParentMenusListQuery reqMsg,ResMsg_MWxMenu_WxParentMenusListQuery resMsg) {
		List<MWxMenu> menus = mWxMenuService.findParentMenus();
		resMsg.setWxParentMenuList(BeanUtils.classToArrayList(menus));
	}
	
	public void mWxMenuAdd(ReqMsg_MWxMenu_MWxMenuAdd reqMsg,ResMsg_MWxMenu_MWxMenuAdd resMsg) {
		String name = reqMsg.getName();
        String type = reqMsg.getType();
        String url = reqMsg.getUrl();
        Integer parentId = reqMsg.getParentId();
        Integer orderNum = reqMsg.getOrderNum();
        MWxMenu wxMenu = new MWxMenu();
        wxMenu.setName(name);
        wxMenu.setType(type);
        wxMenu.setUrl(url);
        wxMenu.setParentId(parentId);
        wxMenu.setOrderNum(orderNum);
        int row = mWxMenuService.saveWxMenu(wxMenu);
        if(row > 0) {
        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
        }else {
        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
        }
	}
	
	public void mWxMenuDelete(ReqMsg_MWxMenu_MWxMenuDelete reqMsg,ResMsg_MWxMenu_MWxMenuDelete resMsg) {
		boolean flag = mWxMenuService.deleteMenuById(reqMsg.getMenuId());
		if(flag) {
			resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
		}else {
			resMsg.setJson(ConstantUtil.RESCODE_FAIL);
		}
	}
	
	public void mWxMenuModify(ReqMsg_MWxMenu_MWxMenuModify reqMsg,ResMsg_MWxMenu_MWxMenuModify resMsg) {
    	String json = mWxMenuService.menu2Json();
        resMsg.setMenuJson(json);
	}
	
	
	public void mWxMenuUpdate(ReqMsg_MWxMenu_MWxMenuUpdate reqMsg,ResMsg_MWxMenu_MWxMenuUpdate resMsg) {
		
		String name = reqMsg.getName();
        String type = reqMsg.getType();
        String url = reqMsg.getUrl();
        Integer parentId = reqMsg.getParentId();
        Integer orderNum = reqMsg.getOrderNum();
        MWxMenu wxMenu = new MWxMenu();
        wxMenu.setId(reqMsg.getId());
        wxMenu.setName(name);
        wxMenu.setType(type);
        wxMenu.setUrl(url);
        wxMenu.setParentId(parentId);
        wxMenu.setOrderNum(orderNum);
        int row = mWxMenuService.updateMWxMenu(wxMenu);
        if(row > 0) {
        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
        }else {
        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
        }
	}
}
