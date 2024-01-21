package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.model.BsWxUserInfo;


public interface WxUserService {
	
	/**
	 * 注册微信用户
	 * @param bsWxUserInfo
	 * @return 如果新增成功，则返回id，否则返回0;
	 */
	public Integer registerWxUser(BsWxUserInfo bsWxUserInfo);
	/**
	 * 根据openid判断微信用户信息
	 * @param openId
	 * @return 如果有，则返回id，否则返回0
	 */
	public Integer isWxUserExist(String openId);
	
	/**
	 * 根据openid并且用户id为null，判断微信用户信息
	 * @param openId
	 * @return 如果有，则返回对象，否则返回null
	 */
	public BsWxUserInfo isWxUserExistWithUserIdNull(String openId);
	
	/**
	 * 查询并删除openId相同，userId不同的数据
	 * @param openId
	 * @param uerId
	 * @return 如果有，则返回对象，否则返回null
	 */
	public void deleteByOpenId(String openId, Integer userId);
	
	/**
	 * 根据openid,用户id,判断微信用户信息
	 * @param openId
	 * @param userId
	 * @return 如果有，则返回对象，否则返回null
	 */
	public BsWxUserInfo isWxUserExistWithUserId(String openId, String userId);
	
	/**
	 * 根据openid判断微信用户信息（列表）
	 * @param openId
	 * @return 如果有，则返回微信用户信息，否则返回null
	 */
	public List<BsWxUserInfo> findWxUser(String openId);
	
	/**
	 * 修改用户是否已关注公众号
	 * @param id
	 * @param isFollow
	 */
	public void modifyWxUser4Follow(Integer id, String isFollow);
	
	/**
	 * 根据用户Id查询微信用户id
	 * @param userId
	 * @return
	 */
	public BsWxUserInfo getWxUserByUserId(Integer userId);
	
	/**
	 * 修改微信用户信息
	 * @param wxUser
	 */
	public void modifyWxUserInfo(BsWxUserInfo wxUser);
	
	/**
	 * 解绑用户openID
	 * @param userId
	 */
	public void unbindWxOpenId(Integer userId);
}
