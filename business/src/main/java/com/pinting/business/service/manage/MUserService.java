package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.MUser;
/**
 * 系统配置接口
 * @Project: business
 * @Title: MUserService.java
 * @author linkin
 * @date 2015-1-29 下午1:46:27
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface MUserService {
	/**
	 * 查询后台用户
	 * @param mUser 后台用户
	 * @return 如果成功，则返回用户list，否则返回null
	 */
	public List<MUser> findMUsers(int status,String name,String mobile,int pageNum,int numPerPage,Integer roleId);
	/**
	 * 查询一个后台用户
	 * @param MUser 后台用户信息
	 * @return 如果成功，则返回用户信息，否则返回null
	 */
	public MUser findMUser(MUser muser);
	/**
	 * 查询一个后台用户
	 * @param id 后台用户id
	 * @return 如果成功，则返回用户信息，否则返回null
	 */
	public MUser findMUser(int id);
	/**
	 * 根据id删除用户
	 * @param id 后台用户id
	 * @return 如果成功，则返回用户true，否则返回false
	 */
	public boolean removeMuser(int id);
	/**
	 * 重置用户密码
	 * @param id 后台用户id
	 * @return 如果成功，则返回用户true，否则返回false
	 */
	public boolean modifyMuserPassword(int id);
	/**
	 * 重置用户密码
	 * @param id 后台用户id
	 * @param password 密码
	 * @return 如果成功，则返回用户true，否则返回false
	 */
	public boolean modifyMuserPassword(int id,String password);
	/**
	 * 修改后台用户信息
	 * @param muser 后台用户信息
	 * @return 如果成功，则返回用户true，否则返回false
	 */
	public boolean modifyMuser(MUser muser);
	/**
	 * 注册后台用户
	 * @param muser 后台用户
	 * @return 如果成功，则返回用户true，否则返回false
	 */
	public boolean createMuser(MUser muser);
	
	/**
	 * 检查是否邮箱已存在
	 * @param email 邮箱
	 * @return 如果成功，则返回用户true，否则返回false
	 */
	public boolean isValidEmail(String email);
	
	/**
	 * 根据状态和用户名为条件查询结果条数
	 * @param status 状态
	 * @param name 用户名
	 * @return 返回结果条数
	 */
	public Integer findAllMUserCount(int status,String name, String mobile,Integer roleId);
}
