/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site.impl;

import java.util.*;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsNewsListVO;
import com.pinting.business.model.vo.BsSysNewsVO;
import com.pinting.business.service.manage.MAppActiveService;
import com.pinting.business.service.site.UserMessageReadService;
import com.pinting.core.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.dao.BsSysNewsMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.service.site.NewsService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.MethodRole;

/**
 * 新闻
 * @author HuanXiong
 * @version $Id: NewsServiceImpl.java, v 0.1 2016-2-23 上午10:34:35 HuanXiong Exp $
 */
@Service
public class NewsServiceImpl implements NewsService {

    @Autowired
    private BsSysNewsMapper bsSysNewsMapper;
    @Autowired
    private UserMessageReadService userMessageReadService;
    @Autowired
    private MAppActiveService mAppActiveService;

    /**
     * @see com.pinting.business.service.site.NewsService#currentNews(com.pinting.business.hessian.site.message.ReqMsg_News_CurrentNews, com.pinting.business.hessian.site.message.ResMsg_News_CurrentNews)
     */
    @Override
    @MethodRole("APP")
    @Deprecated
    public void currentNews(ReqMsg_News_CurrentNews req, ResMsg_News_CurrentNews res) {
    	List<BsSysNews> bsSysNews = bsSysNewsMapper.selectCurrentNews(req.getReceiverType(), req.getType(), req.getShowPage());
        if(null == bsSysNews || CollectionUtils.isEmpty(bsSysNews)) {
            res.setHasNews(false);
        } else {
            ArrayList<HashMap<String,Object>> maps = BeanUtils.classToArrayList(bsSysNews);
            res.setHasNews(true);
            if (req.getUserId() == null) {
                if(Constants.POSITION_NOTICE.equals(req.getType())) {
                    List<BsSysNews> latestNoticeList = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.POSITION_NOTICE);
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
                        // 取最新发布的公告
                        List<BsSysNews> latestNoticeList = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.POSITION_NOTICE);
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
                        // 取最新发布的公告
                        List<BsSysNews> latestNoticeList = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.POSITION_NOTICE);
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

    @Override
    @MethodRole("APP")
    @RedisCache(serviceName = "newsCurrentNewsCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.NEWSSERVICE_CURRENTNEWS)
	public BsNewsListVO selectCurrentNews(ReqMsg_News_CurrentNews req, BsNewsListVO vo) {
    	List<BsSysNews> currentList = bsSysNewsMapper.selectCurrentNews(req.getReceiverType(), req.getType(), req.getShowPage());
        if(Constants.POSITION_NOTICE.equals(req.getType())) {
            List<BsSysNews> latestNoticeList = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.POSITION_NOTICE);
            vo.setLatestNews(latestNoticeList);
        }
    	vo.setCurrentNews(currentList);
    	return vo;
	}
    
    /**
     * @see com.pinting.business.service.site.NewsService#queryNewsPage(com.pinting.business.hessian.site.message.ReqMsg_News_QueryNewsPage, com.pinting.business.hessian.site.message.ResMsg_News_QueryNewsPage)
     */
    @Override
    @MethodRole("APP")
    public void queryNewsPage(ReqMsg_News_QueryNewsPage req, ResMsg_News_QueryNewsPage res) {
        List<BsSysNews> list = bsSysNewsMapper.selectNewsPage(req.getType(), req.getReceiverType(), req.getStart(), req.getNumPerPage());
        int count = 0;
        count = bsSysNewsMapper.selectNewsPage(req.getType(), req.getReceiverType(), 0, Integer.MAX_VALUE).size();
        if(!CollectionUtils.isEmpty(list)) {
            ArrayList<HashMap<String,Object>> maps = BeanUtils.classToArrayList(list);
            if(req.getPageNum() <= 1) {
                if (req.getUserId() == null) {
                    if(Constants.POSITION_NOTICE.equals(req.getType())) {
                        List<BsSysNews> latestNoticeList = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.POSITION_NOTICE);
                        BsSysNews notice = new BsSysNews();
                        if(!CollectionUtils.isEmpty(latestNoticeList)) {
                            notice = latestNoticeList.get(0);
                        }
                        for(int i = 0; i < maps.size(); i++) {
                            if(maps.get(i).get("id").equals(notice.getId())) {
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
                            // 取最新发布的公告
                            List<BsSysNews> latestNoticeList = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.POSITION_NOTICE);
                            BsSysNews notice = new BsSysNews();
                            if(!CollectionUtils.isEmpty(latestNoticeList)) {
                                notice = latestNoticeList.get(0);
                            }
                            for(int i = 0; i < list.size(); i++) {
                                if(list.get(i).getId().equals(notice.getId())) {
                                    maps.get(i).put("isRead", Constants.NO);
                                } else {
                                    maps.get(i).put("isRead", Constants.YES);
                                }
                            }
                        } else {
                            // 取最新发布的公告
                            List<BsSysNews> latestNoticeList = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.POSITION_NOTICE);
                            BsSysNews notice = new BsSysNews();
                            if(!CollectionUtils.isEmpty(latestNoticeList)) {
                                notice = latestNoticeList.get(0);
                            }
                            for(int i = 0; i < list.size(); i++) {
                                if(list.get(i).getId().equals(notice.getId())) {
                                    Date publishTime = list.get(i).getPublishTime();
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
            }

            res.setDataGrid(maps);
        } else {
            res.setDataGrid(new ArrayList<HashMap<String, Object>>());
        }
        res.setCount(count);
    }

    /**
     * @see com.pinting.business.service.site.NewsService#details(com.pinting.business.hessian.site.message.ReqMsg_News_Details, com.pinting.business.hessian.site.message.ResMsg_News_Details)
     */
    @Override
    @MethodRole("APP")
    public void details(ReqMsg_News_Details req, ResMsg_News_Details res) {
        BsSysNewsExample bsSysNewsExample = new BsSysNewsExample();
        bsSysNewsExample.createCriteria().andIdEqualTo(req.getId()).andTypeEqualTo(req.getType());
        List<BsSysNews> list = bsSysNewsMapper.selectByExample(bsSysNewsExample);
        if(CollectionUtils.isEmpty(list)) {
            throw new PTMessageException(PTMessageEnum.NEWS_NOT_EXIST);
        } else {
            BsSysNews news = list.get(0);
            if(Constants.NEWS_STATUS_PUBLISHED.equals(news.getStatus())){
                res.setDetails(BeanUtils.transBeanMap(news));
            } else {
                throw new PTMessageException(PTMessageEnum.NEWS_NOT_EXIST);
            }
        }
    }

    @Override
    public void queryBySource(ReqMsg_News_QueryBySource req, ResMsg_News_QueryBySource res) {
        BsSysNews bsSysNews = bsSysNewsMapper.selectNewsBySource(req.getSource(), req.getReceiverType());
        if(null != bsSysNews) {
            res.setNews(BeanUtils.classToHashMap(bsSysNews));
        }
    }

    @Override
    public void readMessage(ReqMsg_News_ReadMessage req, ResMsg_News_ReadMessage res) {
        if(Constants.POSITION_NOTICE.equals(req.getType())) {
            BsSysNews notice = bsSysNewsMapper.selectLatestNews(req.getReceiverType(), Constants.NEWS_TYPE_NOTICE).get(0);
            if(notice.getId().equals(req.getId())) {
                BsUserMessageRead read = userMessageReadService.queryByPosition(req.getUserId(), Constants.POSITION_NOTICE);
                if(read != null) {
                    userMessageReadService.updateMessageRead(read.getId());
                } else {
                    userMessageReadService.addMessageRead(req.getUserId(), Constants.POSITION_NOTICE);
                }
            }
        } else if(Constants.POSITION_ACTIVITY.equals(req.getType())) {
            BsAppActive active = mAppActiveService.selectLatestActive();
            if(null != active) {
                if(active.getId().equals(req.getId())) {
                    BsUserMessageRead read = userMessageReadService.queryByPosition(req.getUserId(), Constants.POSITION_ACTIVITY);
                    if(read != null) {
                        userMessageReadService.updateMessageRead(read.getId());
                    } else {
                        userMessageReadService.addMessageRead(req.getUserId(), Constants.POSITION_ACTIVITY);
                    }
                }
            }
        }
    }


    @Override
    @MethodRole("APP")
    public void queryNewsPageInfo(ReqMsg_News_QueryNewsPageInfo req, ResMsg_News_QueryNewsPageInfo res) {
        List<BsSysNews> list = bsSysNewsMapper.selectNewsPageInfo(req.getType(), req.getReceiverType(), req.getStart(), req.getNumPerPage());
        int count = 0;
        count = bsSysNewsMapper.selectNewsPageInfo(req.getType(), req.getReceiverType(), 0, Integer.MAX_VALUE).size();
        if(!CollectionUtils.isEmpty(list)) {
            res.setDataGrid(BeanUtils.classToArrayList(list));
        } else {
            res.setDataGrid(new ArrayList<HashMap<String, Object>>());
        }
        res.setCount(count);
    }

    @Override
    public void queryNoticeInfo(ReqMsg_News_QueryNoticeInfo req, ResMsg_News_QueryNoticeInfo res) {
        List<BsSysNewsVO> list = bsSysNewsMapper.selectNoticeInfo(req.getReceiverType(), req.getStart(), req.getNumPerPage());
        int count = 0;
        count = bsSysNewsMapper.selectNoticeInfo(req.getReceiverType(), 0, Integer.MAX_VALUE).size();
        if(!CollectionUtils.isEmpty(list)) {
            List<BsSysNewsVO> resultList = new ArrayList<BsSysNewsVO>();
            for(BsSysNews obj : list) {
                BsSysNewsVO bsSysNewsVO = new BsSysNewsVO();
                bsSysNewsVO.setId(obj.getId());
                bsSysNewsVO.setPublishTimeYM(DateUtil.formatDateTime(obj.getPublishTime(), "yyyy-MM"));
                bsSysNewsVO.setPublishTimeD(DateUtil.formatDateTime(obj.getPublishTime(), "dd"));
                bsSysNewsVO.setSubject(obj.getSubject());
                String content = obj.getContent();
                String dest = content.replaceAll("&lt;", "");
                dest = dest.replaceAll("&gt;", "");
                dest = dest.replaceAll("br/", "");
                dest=dest.replace(" ", "");
                bsSysNewsVO.setContent(dest);
                bsSysNewsVO.setSummary(obj.getSummary());
                resultList.add(bsSysNewsVO);
            }
            res.setDataGrid(BeanUtils.classToArrayList(resultList));
        } else {
            res.setDataGrid(new ArrayList<HashMap<String, Object>>());
        }
        res.setCount(count);
    }

    @Override
    public void queryNoticeDetails(ReqMsg_News_NoticeDetails req, ResMsg_News_NoticeDetails res) {
        BsSysNewsExample bsSysNewsExample = new BsSysNewsExample();
        bsSysNewsExample.createCriteria().andIdEqualTo(req.getId());
        List<BsSysNews> list = bsSysNewsMapper.selectByExample(bsSysNewsExample);
        if(CollectionUtils.isEmpty(list)) {
            throw new PTMessageException(PTMessageEnum.NEWS_NOT_EXIST);
        } else {
            BsSysNews news = list.get(0);
            if(Constants.NEWS_STATUS_PUBLISHED.equals(news.getStatus())){
                res.setDetails(BeanUtils.classToHashMap(news));
            } else {
                throw new PTMessageException(PTMessageEnum.NEWS_NOT_EXIST);
            }
        }
    }

	
}
