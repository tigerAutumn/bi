/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.hessian.site.message;

import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 
 * @author HuanXiong
 * @version $Id: ResMsg_News_CurrentNews.java, v 0.1 2016-2-23 上午10:19:01 HuanXiong Exp $
 */
public class ResMsg_News_CurrentNotice extends ResMsg {

    /**  */
    private static final long serialVersionUID = 1636644045964405703L;
    
    private boolean hasNews;
    
    private HashMap<String, Object> news;

    public boolean isHasNews() {
        return hasNews;
    }

    public void setHasNews(boolean hasNews) {
        this.hasNews = hasNews;
    }

    public HashMap<String, Object> getNews() {
        return news;
    }

    public void setNews(HashMap<String, Object> news) {
        this.news = news;
    }

}
