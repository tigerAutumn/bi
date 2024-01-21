package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsUserInvestView;
import com.pinting.business.model.BsUserInvestViewExample;
import com.pinting.business.model.vo.BsUserInvestViewVO;

/**
 * 用户投资信息接口
 *
 * @Project: business
 * @author yanwl
 * @Title: MUserInvestViewService.java
 * @date 2016-3-22 下午2:58:55
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
public interface MUserInvestViewService {
	/**
	 * 分页查询用户投资信息
	 * @param userInvestView
	 * @return 用户投资列表
	 */
	public List<BsUserInvestView> findUserInvestList(BsUserInvestViewVO userInvestView);
	
	/**
	 * 查询用户投资信息总数
	 * @param userInvestView
	 * @return 用户投资总数
	 */
	public int findUserInvestAllCount(BsUserInvestViewVO userInvestView);
	
	/**
	 * 新增用户投资信息
	 * @param userInvestView
	 * @return 影响行数
	 */
	public int saveUserInvest(BsUserInvestView userInvestView);
	
	/**
	 * 根据日期查询用户投资信息
	 * @param example
	 * @return 用户投资
	 */
	public BsUserInvestView findUserInvestByDate(BsUserInvestViewExample example);
	
	/**
	 * 更新用户投资信息
	 * @param userInvestView
	 * @return 影响行数
	 */
	public int updateUserInvest(BsUserInvestView userInvestView);
}
