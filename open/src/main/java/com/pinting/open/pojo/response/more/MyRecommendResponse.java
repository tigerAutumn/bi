/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.open.pojo.response.more;

import java.util.List;

import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.more.BsUser;

/**
 * 
 * @author HuanXiong
 * @version $Id: MyRecommendResponse.java, v 0.1 2015-12-22 下午3:33:14 HuanXiong Exp $
 */
public class MyRecommendResponse extends Response {
    private Integer totalPage;
    
    private List<BsUser> dataList;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<BsUser> getDataList() {
        return dataList;
    }

    public void setDataList(List<BsUser> dataList) {
        this.dataList = dataList;
    }
}
