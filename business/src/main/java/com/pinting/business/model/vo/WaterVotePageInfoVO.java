package com.pinting.business.model.vo;

import com.pinting.business.model.BsWaterConservationSignUp;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/5
 * Description:
 */
public class WaterVotePageInfoVO extends ActivityBaseVO {

    private static final long serialVersionUID = -3857029917112198826L;

    /* 登录用户姓名 */
    private String userName;

    /* 编号 */
    private String serialNo;

    /* 节水活动列表 */
    private List<BsWaterConservationSignUp> list;

    /**
     * 总记录数
     */
    private int totalRows;

    /**
     * 总页数
     */
    private int totalPages;

    public List<BsWaterConservationSignUp> getList() {
        return list;
    }

    public void setList(List<BsWaterConservationSignUp> list) {
        this.list = list;
    }

    public int getTotalRows() {
        return totalRows;
    }

    public void setTotalRows(int totalRows) {
        this.totalRows = totalRows;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }
}
