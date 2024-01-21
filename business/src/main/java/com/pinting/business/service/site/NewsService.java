/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsSysNews;
import com.pinting.business.model.vo.BsNewsListVO;

/**
 *
 * @author HuanXiong
 * @version $Id: NewsService.java, v 0.1 2016-2-23 上午10:20:52 HuanXiong Exp $
 */
public interface NewsService {

    /**
     * 查询新闻+公司动态
     * @param req
     * @param res
     */
    public void currentNews(ReqMsg_News_CurrentNews req, ResMsg_News_CurrentNews res);
    
    
    /**
     * 根据接收类型、公告类型、显示条数查询
     * 当前和最新列表
     * @author bianyatian
     * @param req
     * @return
     */
    public BsNewsListVO selectCurrentNews(ReqMsg_News_CurrentNews req, BsNewsListVO vo);

    /**
     * 分页查询所有新闻
     * @param req
     * @param res
     */
    public void queryNewsPage(ReqMsg_News_QueryNewsPage req, ResMsg_News_QueryNewsPage res);

    /**
     * 查询某一个新闻的内容
     * @param req
     * @param res
     */
    public void details(ReqMsg_News_Details req, ResMsg_News_Details res);

    /**
     * 查询相关来源的最新报道
     * @param req
     * @param res
     */
    public void queryBySource(ReqMsg_News_QueryBySource req, ResMsg_News_QueryBySource res);

    /**
     * 阅读
     * @param req
     * @param res
     */
    void readMessage(ReqMsg_News_ReadMessage req, ResMsg_News_ReadMessage res);

    /**
     * 分页查询港湾资讯、平台公告
     * @param req
     * @param res
     */
    public void queryNewsPageInfo(ReqMsg_News_QueryNewsPageInfo req, ResMsg_News_QueryNewsPageInfo res);

    /**
     * 分页查询平台公告
     * @param req
     * @param res
     */
    public void queryNoticeInfo(ReqMsg_News_QueryNoticeInfo req, ResMsg_News_QueryNoticeInfo res);

    /**
     * 查询某一个公告详情
     * @param req
     * @param res
     */
    public void queryNoticeDetails(ReqMsg_News_NoticeDetails req, ResMsg_News_NoticeDetails res);

}
