package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.BsAppMessage;


public interface MAppNoticeService {
	
	/**
	 * 查询所有的app通知列表
	 * @param pageNum 页码
	 * @param numPerPage 用户页面大小
	 * @param name 名称
	 * @param title 标题
	 * @param releasePart 发送端口
	 * @param isSend 是否推送
	 * @return
	 */
	public List<BsAppMessage> findAppMessage(Integer pageNum,Integer numPerPage,String name,String title,Integer releasePart,Integer isSend);
	
	/**
	 * 查询所有的app通知列表总数
	 * @param name 名称
	 * @param title 标题
	 * @param releasePart 发送端口
	 * @param isSend 是否推送
	 * @return
	 */
	public int findAllAppNoticeCount(String name, String title, Integer releasePart,Integer isSend);
	
	/**
	 * 存储app通知信息
	 * @param bsAppNotice
	 */
	public int saveAppNotice(BsAppMessage bsAppMessage);
	
	/**
	 * 根据参数查询app通知列表
	 * @return
	 */
	public List<BsAppMessage> findNoticeByMap(Map<String,Object> map);
	
	
	/**
	 * 更新app通知信息
	 * @param id
	 */
	public int deleteAppNotice(Integer id);
	
	/**
	 * 根据主键查询app通知
	 * @param id
	 */
	public BsAppMessage findAppNoticeById(Integer id);
	
	/**
	 * 更新app通知信息
	 * @param bsAppMessage
	 */
	public int updateAppNotice(BsAppMessage bsAppMessage);
}
