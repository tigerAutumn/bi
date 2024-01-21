package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_BsSysNewsDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_BsSysNewsModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_NewsList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_BsSysNewsDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_BsSysNewsModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_NewsList;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_SelectByPrimaryKey;
import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.vo.BsSysNewsVO;
import com.pinting.business.service.manage.BsSysNewsService;
import com.pinting.business.util.BeanUtils;


@Component("BsSysNews")
public class BsSysNewsFacade {
	@Autowired
	private BsSysNewsService sysNewsService;
	/**
	 * 网站新闻公告列表
	 * @param reqMsg
	 * @param resMsg
	 */
	public void newsList(ReqMsg_BsSysNews_NewsList reqMsg,ResMsg_BsSysNews_NewsList resMsg) {
		int totalRows = sysNewsService.findCountSysNews(reqMsg.getId(), reqMsg.getType(), reqMsg.getSubject(), reqMsg.getKeywords(), reqMsg.getSummary(), reqMsg.getSubjectImg(), reqMsg.getReceiverType(), reqMsg.getPriority(), reqMsg.getStatus(), reqMsg.getmUserId(), reqMsg.getPublishTime(), reqMsg.getSource(), reqMsg.getReadTimes(), reqMsg.getCreateTime(), reqMsg.getUpdateTime(), reqMsg.getContent());
		if (totalRows > 0) {
			ArrayList<BsSysNewsVO> bsSysNewsList = sysNewsService.findSysNewsList(reqMsg.getId(), reqMsg.getType(), reqMsg.getSubject(), reqMsg.getKeywords(), reqMsg.getSummary(), reqMsg.getSubjectImg(), reqMsg.getReceiverType(), reqMsg.getPriority(), reqMsg.getStatus(), reqMsg.getmUserId(), reqMsg.getPublishTime(), reqMsg.getSource(), reqMsg.getReadTimes(), reqMsg.getCreateTime(), reqMsg.getUpdateTime(), reqMsg.getContent(), reqMsg.getPageNum(),
					reqMsg.getNumPerPage(), reqMsg.getOrderDirection(), reqMsg.getOrderField());
			ArrayList<HashMap<String, Object>> sysNewsList = BeanUtils.classToArrayList(bsSysNewsList);
			resMsg.setValueList(sysNewsList);
		}
		resMsg.setTotalRows(totalRows);
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
	}
	
	
	/**
	 * 进入添加/编辑页面
	 * @param req
	 * @param res
	 */
	public void selectByPrimaryKey(ReqMsg_BsSysNews_SelectByPrimaryKey req, ResMsg_BsSysNews_SelectByPrimaryKey res) {
		if(req.getId() != null && req.getId() != 0) {
			//reqMsg.getId(), reqMsg.getType(), reqMsg.getSubject(), reqMsg.getKeywords(),
			//reqMsg.getSummary(), reqMsg.getSubjectImg(), reqMsg.getReceiverType(), reqMsg.getPriority(),
			//reqMsg.getStatus(), reqMsg.getmUserId(), reqMsg.getPublishTime(), reqMsg.getSource(), 
			//reqMsg.getReadTimes(), reqMsg.getCreateTime(), reqMsg.getUpdateTime(), reqMsg.getContent()
			BsSysNews bsSysNews = sysNewsService.sysNewsPrimaryKey(req.getId());
			res.setId(bsSysNews.getId());
			res.setType(bsSysNews.getType());
			res.setSubject(bsSysNews.getSubject());
			res.setKeywords(bsSysNews.getKeywords());
			res.setSummary(bsSysNews.getSummary());
			res.setSubjectImg(bsSysNews.getSubjectImg());
			res.setReceiverType(bsSysNews.getReceiverType());
			res.setPriority(bsSysNews.getPriority());
			res.setStatus(bsSysNews.getStatus());
			res.setmUserId(bsSysNews.getMUserId());
			res.setPublishTime(bsSysNews.getPublishTime());
			res.setSource(bsSysNews.getSource());
			res.setReadTimes(bsSysNews.getReadTimes());
			res.setCreateTime(bsSysNews.getCreateTime());
			res.setUpdateTime(bsSysNews.getUpdateTime());
			res.setContent(bsSysNews.getContent());
		}
	}
	
	/**
	 * 添加/编辑
	 * @param req
	 * @param res
	 */
	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.NEWSSERVICE_CURRENTNEWS})
	public void bsSysNewsModify(ReqMsg_BsSysNews_BsSysNewsModify req, ResMsg_BsSysNews_BsSysNewsModify res) {
	    BsSysNews bsSysNews = new BsSysNews();
		bsSysNews.setId(req.getId());
		bsSysNews.setType(req.getType());
		bsSysNews.setSubject(req.getSubject());
		bsSysNews.setKeywords(req.getKeywords());
		bsSysNews.setSummary(req.getSummary());
		bsSysNews.setSubjectImg(req.getSubjectImg());
		String receiverType = req.getReceiverType();
		if(StringUtils.isNotBlank(receiverType) && receiverType.endsWith(",")){
			receiverType = receiverType.substring(0,receiverType.length()-1);
			bsSysNews.setReceiverType(receiverType);//发布端口
		}else{
			bsSysNews.setReceiverType(receiverType);//发布端口
		}
		bsSysNews.setPriority(req.getPriority());
		bsSysNews.setStatus(req.getStatus());
		bsSysNews.setMUserId(req.getmUserId().longValue());
		bsSysNews.setPublishTime(req.getPublishTime());
		bsSysNews.setSource(req.getSource());
		bsSysNews.setReadTimes(req.getReadTimes());
		bsSysNews.setCreateTime(req.getCreateTime());
		bsSysNews.setUpdateTime(req.getUpdateTime());
		bsSysNews.setContent(req.getContent());
		if (req.getId() != null ) {
			sysNewsService.updateSysNews(bsSysNews);
		}else {
			sysNewsService.addSysNews(bsSysNews);
		}
	}
	
	/**
	 * 删除
	 * @param req
	 * @param res
	 */
	public void bsSysNewsDelete(ReqMsg_BsSysNews_BsSysNewsDelete req, ResMsg_BsSysNews_BsSysNewsDelete res) {
		if(req.getId() != null && req.getId() != 0) {
			sysNewsService.deleteSysNewsById(req.getId());
		}
	}
}
