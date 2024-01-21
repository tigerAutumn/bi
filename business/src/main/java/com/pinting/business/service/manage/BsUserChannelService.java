package com.pinting.business.service.manage;

import java.util.ArrayList;

import com.pinting.business.model.BsUserChannel;
import com.pinting.business.model.vo.BsUserChannelVO;

public interface BsUserChannelService {
	
	/**
	 * 用户优先支付渠道列表
	 * @param userName 用户名
	 * @param mobile 注册手机号
	 * @param pageNum 分页
	 * @param numPerPage
	 * @param orderDirection 排序
	 * @param orderField
	 * @return
	 */
	public ArrayList<BsUserChannelVO> findUserChannelList(String userName, String mobile, 
			int pageNum, int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 用户优先支付渠道统计
	 * @param userName 用户名
	 * @param mobile 注册手机号
	 * @return
	 */
	public int findCountUserChannel(String userName, String mobile);
	
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updateUserChannel(BsUserChannel record);
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int addUserChannel(BsUserChannel record);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsUserChannelVO bsUserChannelPrimaryKey(Integer id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteUserChannelById(Integer id);
	
	/**
	 * 查询用户优先支付渠道是否已存在
	 * @param record
	 * @return
	 */
	public BsUserChannel selectUserChannel(BsUserChannel record);
	
	/**
	 * 查找19付银行对应的银行名称-渠道类型-通道优先级
	 * @param bsUserChannelVO
	 * @return
	 */
	public ArrayList<BsUserChannelVO> find19payCardNameChannelPriority(BsUserChannelVO bsUserChannelVO);

}
