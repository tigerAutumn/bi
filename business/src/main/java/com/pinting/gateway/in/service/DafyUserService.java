package com.pinting.gateway.in.service;

import com.pinting.business.model.DafyUserExt;

public interface DafyUserService {
	/**
	 * 根据达飞客户号，返回该客户本地对应用户编号
	 * @param customerId 达飞客户号
	 * @return 成功返回达飞客户对象，否则返回null
	 */
	public DafyUserExt findDafyUserByCustomerId(String customerId);
	
	/**
	 * 新增本地用户与达飞客户号关系记录
	 * @param dafyUserExt 达飞用户关系表
	 * @return 成功返回true，否则返回false
	 */
	public boolean addDafyUser(DafyUserExt dafyUserExt);
	
	/**
	 * 新增或修改 达飞注册用户和绑卡信息
	 * @param dafyUserExt
	 * @return 成功返回true，否则返回false
	 */
	public boolean addOrModifyDafyUser(DafyUserExt dafyUserExt);
	
	/**
	 * 修改本地用户与达飞客户号关系记录
	 * @param dafyUserExt 达飞用户关系表
	 * @return 成功返回true，否则返回false
	 */
	public boolean modifyDafyUser(DafyUserExt dafyUserExt);
	
	/**
	 * 达飞绑卡通知。
	 * @param customerId 达飞用户编号
	 * @param result 绑卡结果
	 */
	public void bindCardResultInform(String customerId, String result, String resultMsg);
	
	/**
	 * 根据用户编号查询达飞客户号是否存在，银行卡是否绑定
	 * @param userId 用户编号
	 * @return 成功返回达飞用户对象，否则返回null
	 */
	public DafyUserExt findDafyUserByUserId(Integer userId);

	/**
	 * 计算当前总绑卡成功的人数
	 * @return 绑卡成功的人数
	 *  
	 */
	public int countCardNum();

	/**
	 * 计算每日总绑卡成功的人数
	 * @return
	 */
	public int countDayCardNum();

}
