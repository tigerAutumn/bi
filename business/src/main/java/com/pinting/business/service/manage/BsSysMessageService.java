package com.pinting.business.service.manage;

import java.util.ArrayList;

import com.pinting.business.model.BsSysMessage;

public interface BsSysMessageService {
	/**
	 * 系统公告信息列表
	 * @param id 消息ID
	 * @param title 标题
	 * @param content 内容
	 * @param receiverType 接收者类型
	 * @param status 状态
	 * @param createTime 创建时间
	 * @param updateTime 更新时间
	 * @param pageNum 分页
	 * @param numPerPage
	 * @param orderDirection 排序
	 * @param orderField
	 * @return
	 */
	public ArrayList<BsSysMessage> findSysMessageList(Integer id, String title, String content, 
			String receiverType, String status, String createTime, 
			String  updateTime,int pageNum,int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 系统公告信息列表
	 * @param id 消息ID
	 * @param title 标题
	 * @param content 内容
	 * @param receiverType 接收者类型
	 * @param status 状态
	 * @param createTime 创建时间
	 * @param updateTime 更新时间
	 * @return
	 */
	public int findCountSysMessage(Integer id, String title, String content, 
			String receiverType, String status, String createTime, 
			String  updateTime);
	
	
	
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updateSysMessage(BsSysMessage record);
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int addSysMessage(BsSysMessage record);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsSysMessage sysMessagePrimaryKey(Integer id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteSysMessageById(Integer id);
	
}
