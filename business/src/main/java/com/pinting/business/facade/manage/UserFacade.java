package com.pinting.business.facade.manage;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.pinting.business.dao.MUserMapper;
import com.pinting.business.model.MUserExample;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_MUser_Info;
import com.pinting.business.hessian.manage.message.ReqMsg_MUser_changePwd;
import com.pinting.business.hessian.manage.message.ReqMsg_MUser_login;
import com.pinting.business.hessian.manage.message.ReqMsg_MUser_operate;
import com.pinting.business.hessian.manage.message.ResMsg_MUser_Info;
import com.pinting.business.hessian.manage.message.ResMsg_MUser_changePwd;
import com.pinting.business.hessian.manage.message.ResMsg_MUser_login;
import com.pinting.business.hessian.manage.message.ResMsg_MUser_operate;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.MRole;
import com.pinting.business.model.MUser;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.MRoleService;
import com.pinting.business.service.manage.MUserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.encrypt.MD5Util;
/**
 * 后台用户管理
 * @Project: business
 * @Title: UserFacade.java
 * @author Linkin
 * @date 2015-1-29 下午1:43:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MUser")
public class UserFacade{
	@Autowired
	private MUserService mUserService;
	@Autowired
	private MRoleService mRoleService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private MUserMapper userMapper;
	
	public void info(ReqMsg_MUser_Info req, ResMsg_MUser_Info resp){
		   
		if(req.getFlag()!=null&&req.getFlag().equals("update"))
		{
			List<MRole> mRoles = mRoleService.findMRoleList(null);
			resp.setMRoleList(BeanUtils.classToArrayList(mRoles));
			MUser muser = mUserService.findMUser(req.getId());
			resp.setId(muser.getId());
			resp.setName(muser.getName());
			resp.setNick(muser.getNick());
			resp.setEmail(muser.getEmail());
			resp.setMobile(muser.getMobile());
			resp.setRoleId(muser.getRoleId());
			resp.setNote(muser.getNote());
			resp.setStatus(muser.getStatus());
			resp.setAgentId(muser.getAgentId());
			List<BsAgent> agernList = agentService.agentNameGroupByList(new BsAgent());
			resp.setAgentList(BeanUtils.classToArrayList(agernList));
		}else if(req.getFlag()!=null)
		{
			List<MRole> mRoles = mRoleService.findMRoleList(null);
			resp.setMRoleList(BeanUtils.classToArrayList(mRoles));
			List<BsAgent> agernList = agentService.agentNameGroupByList(new BsAgent());
			resp.setAgentList(BeanUtils.classToArrayList(agernList));
		}
		else
		{
			
			List<MRole> mRoles = mRoleService.findMRoleList(null);
			resp.setMRoleList(BeanUtils.classToArrayList(mRoles));
			int pageNum = req.getPageNum();
			int numPerPage = req.getNumPerPage();
			
			if(req.getStatus()==null)
			{
				req.setStatus(1);
			}
			int totalRows =mUserService.findAllMUserCount(req.getStatus(),req.getName(),req.getMobile(),req.getRoleId());
			MUser mUser = new MUser();
			mUser.setNumPerPage(req.getNumPerPage());
			mUser.setPageNum(req.getPageNum());
			mUser.setStatus(req.getStatus());
			List<MUser> mUsers = mUserService.findMUsers(req.getStatus(),req.getName(),req.getMobile(),pageNum,numPerPage,req.getRoleId());
			resp.setStatus(req.getStatus());
			resp.setTotalRows(totalRows);
			resp.setNumPerPage(numPerPage);
			resp.setPageNum(pageNum);
			resp.setName(req.getName());
			resp.setMobile(req.getMobile());
			resp.setMUserList(BeanUtils.classToArrayList(mUsers));
			resp.setRoleId(req.getRoleId());
			
		}
	}
	public void login(ReqMsg_MUser_login req, ResMsg_MUser_login resp){
		MUser muser = new MUser();
		muser.setEmail(req.getEmail());
		muser.setPassword(req.getPassword());
		MUser mUser = mUserService.findMUser(muser);
		if(mUser!=null) {
			MRole mrole = null;
			//客户经理
			Pattern p = Pattern.compile("^[1][34587]\\d{9}$");
			Matcher m = p.matcher(req.getEmail());
			if(m.matches()) {
				mrole = mRoleService.selectMRoleByDafyRoleId(req.getRoleId());
			}
			else {
				mrole = mRoleService.findRoleById(mUser.getRoleId());
			}
			resp.setEmail(req.getEmail());
			resp.setId(mUser.getId());
			resp.setName(mUser.getName());
			resp.setNick(mUser.getNick());
			resp.setRoleName(mrole!=null?mrole.getName():"无操作权限");
			resp.setRoleId(mrole!=null?mrole.getId():-1);
			MUser muser2 = new MUser();
			muser2.setId(mUser.getId());
			muser2.setLoginTime(new Date());
			muser2.setLoginFailTimes(0);
			mUserService.modifyMuser(muser2);
		}
		else {
			throw new PTMessageException(PTMessageEnum.USER_PASS_ERROR);
		}
		
	}
	public void operate(ReqMsg_MUser_operate req, ResMsg_MUser_operate resp) throws PTMessageException{
		if(req.getFlag().equals("delete"))
		{
			mUserService.removeMuser(req.getId());
			resp.setFlag("销户");
		}else if(req.getFlag().equals("updatePasswd"))
		{
			MUser user = new MUser();
			//1、重置密码为6位随机数字
			int i=(int)((Math.random()*9+1)*100000);
			String newPsd = String.valueOf(i);
			user.setPassword(MD5Util.encryptMD5(newPsd + MD5Util.getEncryptkey()));
			//重置密码不修改用户的状态
//			user.setStatus(1);
			MUserExample example = new MUserExample();
			example.createCriteria().andIdEqualTo(req.getId());
			userMapper.updateByExampleSelective(user, example);
			resp.setFlag("重置密码");
			//2、重置密码的用户有手机号码 则发送短信
			MUser mUser = userMapper.selectByPrimaryKey(req.getId());
			if(mUser == null) {
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}
			if(mUser.getMobile() != null && !"".equals(mUser.getMobile())) {
				smsServiceClient.sendTemplateMessage(mUser.getMobile(), TemplateKey.MANAGER_RESET_PASSWORD, String.valueOf(newPsd));
			}
		}else if(req.getFlag().equals("update"))
		{
			boolean isExistEmail = mUserService.isValidEmail(req.getEmail());
			MUser mUser = mUserService.findMUser(req.getId());
			if(isExistEmail&&!mUser.getEmail().equals(req.getEmail()))
			{
				resp.setFlag("邮箱已存在");
				return;
			}
			MUser muser = new MUser();
			muser.setId(req.getId());
			muser.setName(req.getName());
			muser.setNick(req.getNick());
			muser.setEmail(req.getEmail());
			muser.setMobile(req.getMobile());
			muser.setStatus(req.getStatus());
			muser.setRoleId(req.getRoleId());
			muser.setNote(req.getNote());
			muser.setAgentId(req.getAgentId());
			mUserService.modifyMuser(muser);
			if (req.getAgentId() == null) {
				userMapper.updateAgentIdByPrimaryKey(req.getId(), req.getAgentId());
			}
			resp.setFlag("修改");
		}
		else if(req.getFlag().equals("create"))
		{
			boolean isExistEmail = mUserService.isValidEmail(req.getEmail());
			if(isExistEmail)
			{
				resp.setFlag("邮箱已存在");
				return;
			}
			MUser muser = new MUser();
			muser.setName(req.getName());
			muser.setNick(req.getNick());
			muser.setPassword(MD5Util.encryptMD5("123456" +MD5Util.getEncryptkey()));
			muser.setEmail(req.getEmail());
			muser.setMobile(req.getMobile());
			muser.setStatus(req.getStatus());
			muser.setRoleId(req.getRoleId());
			muser.setNote(req.getNote());
			muser.setAgentId(req.getAgentId());
			mUserService.createMuser(muser);
			resp.setFlag("新增");
		}else if(req.getFlag().equals("create"))
		{
			
			if(req.getPassword().equals(mUserService.findMUser(req.getId()).getPassword()))
			{
				throw new PTMessageException(PTMessageEnum.ROLENAME_IS_EXIT);
			}
			mUserService.modifyMuserPassword(req.getId(),req.getPassword());
		}
	}

	public void changePwd(ReqMsg_MUser_changePwd req, ResMsg_MUser_changePwd resp) throws PTMessageException{
		MUser mUser = mUserService.findMUser(req.getId());
		String OldPassword = MD5Util.encryptMD5(req.getOldPassword() +MD5Util.getEncryptkey());
		String newPassword =  MD5Util.encryptMD5(req.getPassword() +MD5Util.getEncryptkey());
		if(!mUser.getPassword().equals(OldPassword))
		{
			throw new PTMessageException(PTMessageEnum.USER_OLD_PASS_ERROR);
		}
		else if(newPassword.equals(mUser.getPassword()))
		{
			throw new PTMessageException(PTMessageEnum.PASS_LOGIN_PASS_SAME_ERROR);
		}else 
		{
			mUserService.modifyMuserPassword(req.getId(),req.getPassword());
		}
	}
}
