/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.response.more;

import com.pinting.open.base.response.Response;

/**
 * 
 * @author HuanXiong
 * @version $Id: RecommendResponse.java, v 0.1 2015-12-22 下午3:33:25 HuanXiong Exp $
 */
public class RecommendResponse extends Response {
    
    private String title;
    
    private String content;
    
    private String logo;
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }
    
}
