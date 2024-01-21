/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询最新的新闻|公告|动态 出参
 * @author HuanXiong
 * @version $Id: ResMsg_News_CurrentNews.java, v 0.1 2016-4-5 下午3:52:30 HuanXiong Exp $
 */
public class ResMsg_News_CurrentNews extends ResMsg {

    /**  */
    private static final long serialVersionUID = 1636644045964405703L;
    
    /*是否有，新闻|公告|动态*/
    private boolean hasNews;
    /*对应新闻|公告|动态列表*/
    private ArrayList<HashMap<String, Object>> news;

    public boolean isHasNews() {
        return hasNews;
    }

    public void setHasNews(boolean hasNews) {
        this.hasNews = hasNews;
    }

    public ArrayList<HashMap<String, Object>> getNews() {
        return news;
    }

    public void setNews(ArrayList<HashMap<String, Object>> news) {
        this.news = news;
    }
    
}
