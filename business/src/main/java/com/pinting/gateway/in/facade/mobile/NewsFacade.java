/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.gateway.in.facade.mobile;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.BsUserMessageRead;
import com.pinting.business.model.vo.BsNewsListVO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.service.site.NewsService;
import com.pinting.business.service.site.UserMessageReadService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.InterfaceVersion;

/**
 * 
 * @author HuanXiong
 * @version $Id: NewsFacade.java, v 0.1 2016-2-23 上午11:03:03 HuanXiong Exp $
 */
@Component("appNews")
public class NewsFacade {
    
    @Autowired
    private NewsService newsService;
    @Autowired
    private UserMessageReadService userMessageReadService;

    /**
     * 查询首页的公告
     * @param req
     * @param res
     */
    @InterfaceVersion("CurrentNews/1.0.0")
    public void currentNews(ReqMsg_News_CurrentNews req, ResMsg_News_CurrentNews res) {
    	BsNewsListVO vo = new BsNewsListVO();
    	vo = newsService.selectCurrentNews(req, vo);
    	List<BsSysNews> bsSysNews = vo.getCurrentNews();
    	List<BsSysNews> latestNoticeList = vo.getLatestNews();
    	
    	if(null == bsSysNews || CollectionUtils.isEmpty(bsSysNews)) {
            res.setHasNews(false);
        } else {
        	ArrayList<HashMap<String,Object>> maps = BeanUtils.classToArrayList(bsSysNews);
            res.setHasNews(true);
            if (req.getUserId() == null) {
                if(Constants.POSITION_NOTICE.equals(req.getType())) {
                    BsSysNews notice = new BsSysNews();
                    if(!CollectionUtils.isEmpty(latestNoticeList)) {
                        notice = latestNoticeList.get(0);
                    }
                    for(int i = 0; i < bsSysNews.size(); i++) {
                        if(bsSysNews.get(i).getId().equals(notice.getId())) {
                            maps.get(i).put("isRead", Constants.NO);
                        } else {
                            maps.get(i).put("isRead", Constants.YES);
                        }
                    }
                }
            } else {
                if(Constants.POSITION_NOTICE.equals(req.getType())) {
                    BsUserMessageRead read = userMessageReadService.queryByPosition(req.getUserId(), Constants.POSITION_NOTICE);
                    if (null == read) {
                        BsSysNews notice = new BsSysNews();
                        if(!CollectionUtils.isEmpty(latestNoticeList)) {
                            notice = latestNoticeList.get(0);
                        }
                        for(int i = 0; i < bsSysNews.size(); i++) {
                            if(bsSysNews.get(i).getId().equals(notice.getId())) {
                                maps.get(i).put("isRead", Constants.NO);
                            } else {
                                maps.get(i).put("isRead", Constants.YES);
                            }
                        }
                    } else {
                        BsSysNews notice = new BsSysNews();
                        if(!CollectionUtils.isEmpty(latestNoticeList)) {
                            notice = latestNoticeList.get(0);
                        }
                        for(int i = 0; i < bsSysNews.size(); i++) {
                            if(bsSysNews.get(i).getId().equals(notice.getId())) {
                                Date publishTime = bsSysNews.get(i).getPublishTime();
                                if (publishTime.after(read.getReadTime())) {
                                    maps.get(i).put("isRead", Constants.NO);
                                } else {
                                    maps.get(i).put("isRead", Constants.YES);
                                }
                            } else {
                                maps.get(i).put("isRead", Constants.YES);
                            }
                        }
                    }
                }
            }
            res.setNews(maps);
        }
    	newsService.currentNews(req, res);
    }

    /**
     * 分页查询所有新闻
     * @param req
     * @param res
     */
    @InterfaceVersion("QueryNewsPage/1.0.0")
    public void queryNewsPage(ReqMsg_News_QueryNewsPage req, ResMsg_News_QueryNewsPage res) {
        newsService.queryNewsPage(req, res);
    }
    
    /**
     * 查询某一个新闻的内容
     * @param req
     * @param res
     */
    @InterfaceVersion("Details/1.0.0")
    public void details(ReqMsg_News_Details req, ResMsg_News_Details res) {
        newsService.details(req, res);
    }

    /**
     * 阅读
     * @param req
     * @param res
     */
    @InterfaceVersion("ReadMessage/1.0.0")
    public void readMessage(ReqMsg_News_ReadMessage req, ResMsg_News_ReadMessage res) {
        newsService.readMessage(req, res);
    }

    /**
     * 分页查询港湾资讯、平台公告
     * @param req
     * @param res
     */
    @InterfaceVersion("QueryNewsPageInfo/1.0.1")
    public void queryNewsPageInfo(ReqMsg_News_QueryNewsPageInfo req, ResMsg_News_QueryNewsPageInfo res) {
        newsService.queryNewsPageInfo(req, res);
    }

}
