package com.pinting.business.hessian.site.message;

import java.util.Map;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 查询相关来源的最新报道 出参
 * @author HuanXiong
 * @version 2016-6-3 下午2:15:56
 */
public class ResMsg_News_QueryBySource extends ResMsg {

    /**  */
    private static final long serialVersionUID = -961634463346151853L;
    
    /*最新报道列表*/
    private Map<String, Object> news;

    public Map<String, Object> getNews() {
        return news;
    }

    public void setNews(Map<String, Object> news) {
        this.news = news;
    }
    
}
