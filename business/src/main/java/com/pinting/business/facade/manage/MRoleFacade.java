package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.dao.MUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_RoleMenuQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_RoleMenuUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_roleDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_roleQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MRole_roleSave;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_RoleMenuQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_RoleMenuUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_roleDelete;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_roleQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MRole_roleSave;
import com.pinting.business.model.MRole;
import com.pinting.business.model.MUser;
import com.pinting.business.model.vo.MMenuRoleVO;
import com.pinting.business.model.vo.MMenuVO;
import com.pinting.business.service.manage.MMenuService;
import com.pinting.business.service.manage.MRoleMenuService;
import com.pinting.business.service.manage.MRoleService;
import com.pinting.business.util.BeanUtils;
/**
 * 后台用户管理
 * @Project: business
 * @Title: MRoleFacade.java
 * @author Linkin
 * @date 2015-1-30 下午1:43:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MRole")
public class MRoleFacade{

	@Autowired
	private MRoleService mRoleService;
	@Autowired
	private MRoleMenuService mRoleMenuService;
	@Autowired
	private MMenuService mMenuService;
	@Autowired
	private MUserMapper mUMapper;
	
	public void roleQuery(ReqMsg_MRole_roleQuery req, ResMsg_MRole_roleQuery resp){
		if(req.getId()==null)
		{
			Integer roleId = null;
			if(req.getmUserId() != null){
				MUser user =mUMapper.selectByPrimaryKey(req.getmUserId());
				if(user != null && user.getRoleId() != null){
					roleId = user.getRoleId();
				}
			}
			List<MRole> mRoles = mRoleService.findMRoleList(roleId);
			resp.setMRoleList(BeanUtils.classToArrayList(mRoles));
		}else{
			MRole mRole = mRoleService.findRoleById(req.getId());
			resp.setId(mRole.getId());
			resp.setName(mRole.getName());
			resp.setNote(mRole.getNote());
		}
	}
	public void roleDelete(ReqMsg_MRole_roleDelete req, ResMsg_MRole_roleDelete resp) throws PTMessageException{
		try{
			mRoleService.removeRole(req.getId());

		}catch(Exception e )
		{
			throw new PTMessageException(PTMessageEnum.ROLE_IS_ENPLOY);
		}
	}
	public void roleSave(ReqMsg_MRole_roleSave req, ResMsg_MRole_roleSave resp) throws PTMessageException{
		if(req.getId()!=null)
		{
			MRole mRole2 = mRoleService.findRoleById(req.getId());
			boolean isNameExict= mRoleService.findRoleByName(req.getName());
			if(!mRole2.getName().equals(req.getName())&&isNameExict)
			{
				throw new PTMessageException(PTMessageEnum.ROLENAME_IS_EXIT);
			}
			MRole mrole = new MRole();
			mrole.setId(req.getId());
			mrole.setName(req.getName());
			mrole.setNote(req.getNote());
			mRoleService.modifyRole(mrole);
		}else
		{
			boolean isNameExict= mRoleService.findRoleByName(req.getName());
			if(isNameExict)
			{
				throw new PTMessageException(PTMessageEnum.ROLENAME_IS_EXIT);
			}
			MRole mrole = new MRole();
			mrole.setName(req.getName());
			mrole.setNote(req.getNote());
			mRoleService.createRole(mrole);
		}
	}
	
	public void roleMenuQuery(ReqMsg_MRole_RoleMenuQuery req, ResMsg_MRole_RoleMenuQuery resp){
		
		String roleId = req.getRoleId();
		List<MMenuVO> menuVOs = mRoleMenuService.findAllMRoleMenuForAssignByRoleId(Integer.valueOf(roleId));
		ArrayList<HashMap<String,Object>> menuList = BeanUtils.classToArrayList(menuVOs);
		resp.setMenus(menuList);
		resp.setRoleId(Integer.valueOf(roleId));
		
	}
	
	
	public static Map<String, LinkedList<Integer>> map = new HashMap<String, LinkedList<Integer>>();
	public void roleMenuUpdate(ReqMsg_MRole_RoleMenuUpdate req, ResMsg_MRole_RoleMenuUpdate resp){
		
		boolean flag = mRoleMenuService.modifyMRoleMenuByRoleIdAndMenuId(Integer.valueOf(req.getRoleId()), req.getMenuIds());
		if(!flag){
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}else{
			map.clear();
			List<MMenuRoleVO> menuList = mMenuService.findAllChildMenu();
			for (MMenuRoleVO mMenuRoleVO : menuList) {
				String url = mMenuRoleVO.getUrl().replace("/", "").replace("：", "").replace(":", "");
				LinkedList<Integer> urlList = MRoleFacade.map.get(url);
				if(MRoleFacade.map.get(url) != null){
					urlList = MRoleFacade.map.get(url);
					urlList.add(mMenuRoleVO.getRoleId()==null?-1:mMenuRoleVO.getRoleId());
				}else{
					urlList = new LinkedList<Integer>();
					urlList.addFirst(mMenuRoleVO.getRoleId()==null?-1:mMenuRoleVO.getRoleId());
				}
				map.put(url, urlList);
			}
		}
	}

}
