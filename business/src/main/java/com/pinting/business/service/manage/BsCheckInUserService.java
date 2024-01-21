package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.BsCheckInUserVO;

/**
 * 2016客户年终答谢会签到
 * Created by shh on 2016/11/25 20:00.
 */
public interface BsCheckInUserService {
	
	/**
	 * 单条记录插入签到表
	 * @param mobile
	 * @return
	 */
	boolean insertCheckInUser(String mobile);
	
	/**
	 * 批量插入签到表
	 * @param checkInUserList
	 */
    void batchInsertCheckInUser(List<String> mobileList);
	
	/**
	 * 2016客户年终答谢会抽奖列表
	 * @param record
	 * @return
	 */
	List<BsCheckInUserVO> queryCheckInUserList(BsCheckInUserVO record);
	
	/**
	 * 2016客户年终答谢会抽奖统计
	 * @param record
	 * @return
	 */
    int queryCheckInUserCount(BsCheckInUserVO record);

}
