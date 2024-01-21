package com.pinting.mall.model.vo;

import java.util.Date;

/**
 * 积分记录VO
 * @author shh
 * @date 2018/5/17 19:50
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
public class MallAccountJnlVO {

    /* 积分商城用户余额流水表id */
    private Integer id;

    /* 手机号 */
    private String mobile;

    /* 交易时间 */
    private Date transTime;

    /* 积分交易场景 */
    private String transType;

    /* 类型:收入 支出 */
    private String pointsType;

    /* 交易积分 */
    private Long points;

    /* 查询手机号 */
    private String strMobile;

    /* 交易开始时间 */
    private String startTransTime;

    /* 交易结束时间 */
    private String endTransTime;

    /* 当前页码 */
    private int pageNum;

    /* 每页记录数 */
    private int numPerPage;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public Date getTransTime() {
        return transTime;
    }

    public void setTransTime(Date transTime) {
        this.transTime = transTime;
    }

    public String getTransType() {
        return transType;
    }

    public void setTransType(String transType) {
        this.transType = transType;
    }

    public String getPointsType() {
        return pointsType;
    }

    public void setPointsType(String pointsType) {
        this.pointsType = pointsType;
    }

    public Long getPoints() {
        return points;
    }

    public void setPoints(Long points) {
        this.points = points;
    }

    public String getStrMobile() {
        return strMobile;
    }

    public void setStrMobile(String strMobile) {
        this.strMobile = strMobile;
    }

    public String getStartTransTime() {
        return startTransTime;
    }

    public void setStartTransTime(String startTransTime) {
        this.startTransTime = startTransTime;
    }

    public String getEndTransTime() {
        return endTransTime;
    }

    public void setEndTransTime(String endTransTime) {
        this.endTransTime = endTransTime;
    }

    public int getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }

    public int getNumPerPage() {
        return numPerPage;
    }

    public void setNumPerPage(int numPerPage) {
        this.numPerPage = numPerPage;
    }
}
