package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsSysNewsMapper;
import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.vo.BsSysNewsVO;
import com.pinting.business.service.manage.BsSysNewsService;
import com.pinting.core.util.DateUtil;

@Service
public class BsSysNewsServiceImpl implements BsSysNewsService {
	@Autowired
	private BsSysNewsMapper sysNewsMapper;
	
	@Override
	public ArrayList<BsSysNewsVO> findSysNewsList(Integer id, String type,
			String subject, String keywords, String summary, String subjectImg,
			String receiverType, Integer priority, String status, Integer mUserId,
			String publishTime, String source, Integer readTimes,
			String createTime, String updateTime, String content, int pageNum,
			int numPerPage, String orderDirection, String orderField) {
		BsSysNews  bsSysNews = new BsSysNews();
		if(id != null ) {
			bsSysNews.setId(id);
		}
		if(type != null && !"".equals(type)) {
			bsSysNews.setType(type);
		}
		if(subject != null && !"".equals(subject)) {
			bsSysNews.setSubject(subject.trim());
		}
		if(keywords != null && !"".equals(keywords)) {
			bsSysNews.setKeywords(keywords);
		}
		if(summary != null && !"".equals(summary)) {
			bsSysNews.setSummary(summary);
		}
		if(subjectImg != null && !"".equals(subjectImg)) {
			bsSysNews.setSubjectImg(subjectImg);
		}
		if(receiverType != null && !"".equals(receiverType)) {
			bsSysNews.setReceiverType(receiverType);
		}
		if(priority != null ) {
			bsSysNews.setPriority(priority);
		}
		if(status != null && !"".equals(status)) {
			bsSysNews.setStatus(status);
		}
		if(source != null ) {
			bsSysNews.setSource(source);
		}
		if(publishTime != null && !"".equals(publishTime)) {
			bsSysNews.setPublishTime(DateUtil.parseDateTime(publishTime));
		}
		if(status != null && !"".equals(status)) {
			bsSysNews.setStatus(status);
		}
		if(readTimes != null ) {
			bsSysNews.setReadTimes(readTimes);
		}
		if(createTime != null && !"".equals(createTime)) {
			bsSysNews.setCreateTime(DateUtil.parseDateTime(createTime));
		}
		if(updateTime != null && !"".equals(updateTime)) {
			bsSysNews.setUpdateTime(DateUtil.parseDateTime(updateTime));
		}
		
		if(content != null && !"".equals(content)) {
			bsSysNews.setContent(content);
		}
		if(orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			bsSysNews.setOrderDirection(orderDirection);
			bsSysNews.setOrderField(orderField);
		}
		bsSysNews.setPageNum(pageNum);
		bsSysNews.setNumPerPage(numPerPage);
		return sysNewsMapper.selectSysNewsListPageInfo(bsSysNews);
	}

	@Override
	public int findCountSysNews(Integer id, String type, String subject,
			String keywords, String summary, String subjectImg,
			String receiverType, Integer priority, String status, Integer mUserId,
			String publishTime, String source, Integer readTimes,
			String createTime, String updateTime, String content) {
		BsSysNews  bsSysNews = new BsSysNews();
		if(id != null ) {
			bsSysNews.setId(id);
		}
		if(type != null && !"".equals(type)) {
			bsSysNews.setType(type);
		}
		if(subject != null && !"".equals(subject)) {
			bsSysNews.setSubject(subject.trim());
		}
		if(keywords != null && !"".equals(keywords)) {
			bsSysNews.setKeywords(keywords);
		}
		if(summary != null && !"".equals(summary)) {
			bsSysNews.setSummary(summary);
		}
		if(subjectImg != null && !"".equals(subjectImg)) {
			bsSysNews.setSubjectImg(subjectImg);
		}
		if(receiverType != null && !"".equals(receiverType)) {
			bsSysNews.setReceiverType(receiverType);
		}
		if(priority != null ) {
			bsSysNews.setPriority(priority);
		}
		if(status != null && !"".equals(status)) {
			bsSysNews.setStatus(status);
		}
		if(source != null ) {
			bsSysNews.setSource(source);
		}
		if(publishTime != null && !"".equals(publishTime)) {
			bsSysNews.setPublishTime(DateUtil.parseDateTime(publishTime));
		}
		if(status != null && !"".equals(status)) {
			bsSysNews.setStatus(status);
		}
		if(readTimes != null ) {
			bsSysNews.setReadTimes(readTimes);
		}
		if(createTime != null && !"".equals(createTime)) {
			bsSysNews.setCreateTime(DateUtil.parseDateTime(createTime));
		}
		if(updateTime != null && !"".equals(updateTime)) {
			bsSysNews.setUpdateTime(DateUtil.parseDateTime(updateTime));
		}
		if(content != null && !"".equals(content)) {
			bsSysNews.setContent(content);
		}
		return sysNewsMapper.selectCountSysNews(bsSysNews);
	}

	@Override
	public int updateSysNews(BsSysNews record) {
	    BsSysNews news = sysNewsMapper.selectByPrimaryKey(record.getId());
	    news.setType(record.getType());
        news.setSubject(record.getSubject());
        news.setKeywords(record.getKeywords());
        news.setSummary(record.getSummary());
        news.setSubjectImg(record.getSubjectImg());
        news.setReceiverType(record.getReceiverType());
        news.setPriority(record.getPriority());
        news.setStatus(record.getStatus());
        news.setMUserId(record.getMUserId());
        news.setPublishTime(record.getPublishTime());
        news.setSource(record.getSource());
        news.setReadTimes(record.getReadTimes());
        news.setUpdateTime(record.getUpdateTime());
        news.setContent(record.getContent());
	    return sysNewsMapper.updateByPrimaryKeyWithBLOBs(news);
	}

	@Override
	public int addSysNews(BsSysNews record) {
		record.setUpdateTime(new Date());
		return sysNewsMapper.insertSelective(record);
	}

	@Override
	public BsSysNews sysNewsPrimaryKey(Integer id) {
		return sysNewsMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteSysNewsById(Integer id) {
		return sysNewsMapper.deleteByPrimaryKey(id);
	}

}
