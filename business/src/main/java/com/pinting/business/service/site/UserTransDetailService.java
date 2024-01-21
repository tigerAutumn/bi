package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.BsUserTransDetail;
import com.pinting.gateway.in.util.MethodRole;

public interface UserTransDetailService {

	/**
	 * 根据用户id查询某天或某月的提现成功的次数
	 * @param userId
	 * @return
	 */
	public int countByUserIdWithdrawSuc(Integer userId,Date time);
	
	/**
	 * 根据用户编号查询交易信息
	 * @param userId 用户编号
	 * @param pageIndex 页码-从0开始
	 * @param pageSize 页大小
	 * @return 如果查询成功返回明细，否则返回null
	 */
	List<BsUserTransDetail> findByUserId(Integer userId, Integer pageIndex, Integer pageSize);
	
	/**
	 * 根据userId查询交易明细总数
	 * @param userId 用户编号
	 * @return 返回明细总数
	 */
	public Integer findByUserIdCount(Integer userId);
	
	/**
	 * 根据userId查询用户正在处理的订单数
	 * @param userId 用户编号
	 * @return 用户处理中订单数量
	 */
	public Integer processingNumAll(Integer userId);
	
	/**
	 * 根据userId查询用户正在处理的购买的订单数
	 * @param userId 用户编号
	 * @return 用户处理中订单数量
	 */
	public Integer processingNum(Integer userId);
	
	/**
	 * 根据userId和时间，查询某用户在某时间段内的非失败的提现交易总额
	 * @param userId
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	public Double sumUnFallWithdraw(Integer userId, String startDay, String endDay);

	/**
	 * 根据时间查询某一天的某个人的所有赞分期回款
	 * @param userId	用户Id
	 * @param time		时间（yyyy-MM-dd HH:mm:ss）
     * @return
     */
	List<BsUserTransDetail> queryReturnZanDetail(Integer userId, String time);

	public List<BsUserTransDetail> findByUserIdNew(Integer userId,
												   Integer pageIndex, Integer pageSize);

	public Integer findByUserIdCountNew(Integer userId);
}
