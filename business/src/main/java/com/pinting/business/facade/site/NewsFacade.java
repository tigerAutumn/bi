/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.facade.site;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsAppActive;
import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.BsUserMessageRead;
import com.pinting.business.model.vo.BsNewsListVO;
import com.pinting.business.service.manage.MAppActiveService;
import com.pinting.business.service.site.UserMessageReadService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.service.site.NewsService;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 
 * @author HuanXiong
 * @version $Id: NewsFacade.java, v 0.1 2016-2-23 上午11:03:03 HuanXiong Exp $
 */
@Component("News")
public class NewsFacade {
    
    @Autowired
    private NewsService newsService;
    @Autowired
    private MAppActiveService activeService;
    @Autowired
    private UserMessageReadService userMessageReadService;
    
    /**
     * 分页查询所有新闻
     * @param req
     * @param res
     */
    public void queryNewsPage(ReqMsg_News_QueryNewsPage req, ResMsg_News_QueryNewsPage res) {
        newsService.queryNewsPage(req, res);
    }
    
    /**
     * 查询某一个新闻的内容
     * @param req
     * @param res
     */
    public void details(ReqMsg_News_Details req, ResMsg_News_Details res) {
        newsService.details(req, res);
    }
    
    /**
     * 查询最新的新闻|公告|动态
     * @param req
     * @param res
     */
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
    }
    
    /**
     * 查询相关来源的最新报道
     * @param req
     * @param res
     */
    public void queryBySource(ReqMsg_News_QueryBySource req, ResMsg_News_QueryBySource res) {
        newsService.queryBySource(req, res);
    }

    /**
     * 分页查询港湾资讯（公司动态、媒体报道、湾粉活动）-存管后改版
     * type
     * @param req
     * @param res
     */
    public void queryNewsPageInfo(ReqMsg_News_QueryNewsPageInfo req, ResMsg_News_QueryNewsPageInfo res) {
        newsService.queryNewsPageInfo(req, res);
    }

    /**
     * 分页查询平台公告
     * @param req
     * @param res
     */
    public void queryNoticeInfo(ReqMsg_News_QueryNoticeInfo req, ResMsg_News_QueryNoticeInfo res) {
        newsService.queryNoticeInfo(req, res);
    }

    /**
     * 查询某一个平台公告
     * @param req
     * @param res
     */
    public void noticeDetails(ReqMsg_News_NoticeDetails req, ResMsg_News_NoticeDetails res) {
        newsService.queryNoticeDetails(req, res);
    }

    public void isRead(ReqMsg_News_IsRead req, ResMsg_News_IsRead res) {
        if (req.getUserId() == null) {
            res.setIsRead(Constants.NO);
        } else {
            BsUserMessageRead read = userMessageReadService.queryByPosition(req.getUserId(), Constants.POSITION_ACTIVITY);
            BsAppActive active = activeService.selectLatestActive();
            if(active == null) {
                res.setIsRead(Constants.YES);
                return;
            }
            if (null == read) {
                res.setIsRead(Constants.NO);
            } else {
                if(null != active) {
                    Date publishTime = active.getPublishTime();
                    if (publishTime.after(read.getReadTime())) {
                        res.setIsRead(Constants.NO);
                    } else {
                        res.setIsRead(Constants.YES);
                    }
                } else {
                    res.setIsRead(Constants.YES);
                }
            }
        }
    }

    public void readMessage(ReqMsg_News_ReadMessage req, ResMsg_News_ReadMessage res) {
        newsService.readMessage(req, res);
    }

}
