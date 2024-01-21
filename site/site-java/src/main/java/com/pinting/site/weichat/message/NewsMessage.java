package com.pinting.site.weichat.message;


import java.util.List;

import com.pinting.site.weichat.message.BaseMessage;


/**
 * 文本消息 (图文信息)
 * @Project: site-java
 * @Title: NewsMessage.java
 * @author Huang MengJian
 * @date 2015-3-24 下午2:19:30
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class NewsMessage extends BaseMessage {  
    // 图文消息个数，限制为10条以内  
    private int ArticleCount;  
    // 多条图文消息信息，默认第一个item为大图  
    private List<Article> Articles;  
  
    public int getArticleCount() {  
        return ArticleCount;  
    }  
  
    public void setArticleCount(int articleCount) {  
        ArticleCount = articleCount;  
    }  
  
    public List<Article> getArticles() {  
        return Articles;  
    }  
  
    public void setArticles(List<Article> articles) {  
        Articles = articles;  
    }  
}  