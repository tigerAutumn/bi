package com.pinting.open.pojo.request.asset;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Author:      cyb
 * Date:        2017/3/16
 * Description:
 */
public class RedPacketListRequest extends Request {

    private Integer userId;

    /* INIT-未使用；USED-已使用；OVER-已过期 */
    private String status;

    /* 页码，1开始 */
    private Integer pageNum;

    /* 每页显示条数 */
    private Integer numPerPage;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public Integer getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(Integer numPerPage) {
        this.numPerPage = numPerPage;
    }

    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/asset/redPacketList";
    }

    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/asset/redPacketList";
    }
}
