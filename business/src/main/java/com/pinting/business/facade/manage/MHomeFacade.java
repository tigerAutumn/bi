package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MHome_MenuQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MHome_MenuQuery;
import com.pinting.business.model.vo.MMenuVO;
import com.pinting.business.service.manage.MRoleMenuService;
import com.pinting.business.util.BeanUtils;

/**
 * 角色权限菜单查询
 * @Project: business
 * @Title: MHomeFacade.java
 * @author dingpf
 * @date 2015-1-28 下午5:30:52
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MHome")
public class MHomeFacade{
	@Autowired
	private MRoleMenuService mRoleMenuService;
	
	public void menuQuery(ReqMsg_MHome_MenuQuery req, ResMsg_MHome_MenuQuery res){
		//根据userId验证用户是否存在，角色权限是否一致，否则不进行角色菜单查询（暂不实现）
		String userId = req.getUserId();
		String roleId = req.getRoleId();
		
		//通过验证后，进行角色菜单查询
		List<MMenuVO> menuVOs = mRoleMenuService.findMRoleMenuByRoleId(Integer.valueOf(roleId));
		ArrayList<HashMap<String,Object>> menuList = BeanUtils.classToArrayList(menuVOs);
		res.setMenus(menuList);
		
		res.setUserId(Integer.valueOf(userId));
		res.setRoleId(Integer.valueOf(roleId));
		//继续设置userName和roleName（暂不实现）
		//res.setUserName(...);
		//res.setRoleName(...);
	}
}
