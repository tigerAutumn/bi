package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.MUserMapper;
import com.pinting.business.model.MUser;
import com.pinting.business.model.MUserExample;
import com.pinting.business.model.MUserExample.Criteria;
import com.pinting.business.service.manage.MUserService;
import com.pinting.core.util.encrypt.MD5Util;
@Service
public class MUserServiceImpl implements MUserService {
	@Autowired
	private MUserMapper userMapper;
	@Override
	public List<MUser> findMUsers(int status,String name,String mobile,int pageNum,int numPerPage,Integer roleId) {
		MUser mUser = new MUser();
		mUser.setStatus(status);
		mUser.setName(name);
		mUser.setMobile(mobile);
		mUser.setPageNum(pageNum);
		mUser.setNumPerPage(numPerPage);
		mUser.setRoleId(roleId);
		List<MUser> mUserList = userMapper.selectMUserListByExample(mUser);
		if(mUserList != null && mUserList.size() > 0)
		{
			return mUserList;
		}
		return null;
	}
	@Override
	public boolean removeMuser(int id) {
		MUser user = new MUser();
		user.setStatus(2);
		MUserExample example = new MUserExample();
		example.createCriteria().andIdEqualTo(id);
		return userMapper.updateByExampleSelective(user, example)==1;
	}
	@Override
	public boolean modifyMuserPassword(int id) {
		MUser user = new MUser();
		user.setPassword(MD5Util.encryptMD5("123456" + MD5Util.getEncryptkey()));
		user.setStatus(1);
		MUserExample example = new MUserExample();
		example.createCriteria().andIdEqualTo(id);
		return userMapper.updateByExampleSelective(user, example)==1;
	}
	@Override
	public boolean modifyMuserPassword(int id ,String password) {
		MUser user = new MUser();
		user.setPassword(MD5Util.encryptMD5(password +MD5Util.getEncryptkey()));
		MUserExample example = new MUserExample();
		example.createCriteria().andIdEqualTo(id);
		return userMapper.updateByExampleSelective(user, example)==1;
	}
	@Override
	public boolean createMuser(MUser muser) {
		muser.setCreateTime(new Date());
		return userMapper.insertSelective(muser) == 1 ? true :false;
	}
	@Override
	public boolean modifyMuser(MUser muser) {
		MUserExample example = new MUserExample();
		example.createCriteria().andIdEqualTo(muser.getId());
		return userMapper.updateByExampleSelective(muser, example)==1;
	}
	@Override
	public MUser findMUser(int id) {
		return userMapper.selectByPrimaryKey(id);
	}
	@Override
	public boolean isValidEmail(String email) {

		MUserExample example = new MUserExample();
		example.createCriteria().andEmailEqualTo(email);
		return userMapper.countByExample(example)>0 ?true:false;
	}
	@Override
	public Integer findAllMUserCount(int status, String name, String mobile,Integer roleId) {
		MUserExample example = new MUserExample();
		Criteria criteria = example.createCriteria();
		if(name!=null && (!"".equals(name))){
			criteria.andNameLike("%"+name+"%");
			
		}
		if(mobile!=null && (!"".equals(mobile))){
			criteria.andMobileLike("%"+mobile+"%");
			
		}
		if(status>0){
			criteria.andStatusEqualTo(status);
		}
		if(roleId!=null){
			criteria.andRoleIdEqualTo(roleId);
			
		}
		return userMapper.countByExample(example);

	}
	@Override
	public MUser findMUser(MUser muser) {
		String param = muser.getEmail();
		Pattern p = Pattern.compile("^[1][34587]\\d{9}$");
		Matcher m = p.matcher(param);
		MUserExample example = new MUserExample();
		if(m.matches()) {
			example.createCriteria().andMobileEqualTo(param).andPasswordEqualTo(muser.getPassword()).andStatusEqualTo(1);
		}
		else {
			example.createCriteria().andEmailEqualTo(param).andPasswordEqualTo(muser.getPassword()).andStatusEqualTo(1);
		}
		List<MUser> mUserList =userMapper.selectByExample(example);
		if(mUserList != null && mUserList.size() > 0)
		{
			return mUserList.get(0);
		}
		return null;
	}
}
