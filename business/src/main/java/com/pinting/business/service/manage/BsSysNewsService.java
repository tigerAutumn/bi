package com.pinting.business.service.manage;

import java.util.ArrayList;
import java.util.Date;

import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.vo.BsSysNewsVO;

public interface BsSysNewsService {
	/**
	 * 系统新闻公告信息列表
	 * @param id 消息ID
	 * @param type 类型
	 * @param subject 标题
	 * @param keywords 关键词
	 * @param summary 摘要
	 * @param subjectImg 标题图片
	 * @param receiverType 接收对象类型
	 * @param priority 优先级
	 * @param status 状态
	 * @param mUserId 发布者
	 * @param publishTime 发布时间
	 * @param source 来源
	 * @param readTimes 阅读次数
	 * @param createTime 创建时间
	 * @param updateTime 修改时间
	 * @param content 内容
	 * @param pageNum 分页
	 * @param numPerPage
	 * @param orderDirection 排序
	 * @param orderField
	 * @return
	 */
	public ArrayList<BsSysNewsVO> findSysNewsList(Integer id, String type, String subject, 
			String keywords, String summary, String subjectImg, String receiverType,Integer priority,
			String status,Integer mUserId, String publishTime,String source,Integer readTimes,String createTime,
			String  updateTime,String content,int pageNum,int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 系统公告信息列表
	 * @param id 消息ID
	 * @param type 类型
	 * @param subject 标题
	 * @param keywords 关键词
	 * @param summary 摘要
	 * @param subjectImg 标题图片
	 * @param receiverType 接收对象类型
	 * @param priority 优先级
	 * @param status 状态
	 * @param mUserId 发布者
	 * @param publishTime 发布时间
	 * @param source 来源
	 * @param readTimes 阅读次数
	 * @param createTime 创建时间
	 * @param updateTime 修改时间
	 * @param content 内容
	 * @return
	 */
	public int findCountSysNews(Integer id, String type, String subject, 
			String keywords, String summary, String subjectImg, String receiverType,Integer priority,
			String status,Integer mUserId, String publishTime,String source,Integer readTimes,String createTime,
			String  updateTime,String content);
	
	
	
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updateSysNews(BsSysNews record);
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int addSysNews(BsSysNews record);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsSysNews sysNewsPrimaryKey(Integer id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteSysNewsById(Integer id);
}
