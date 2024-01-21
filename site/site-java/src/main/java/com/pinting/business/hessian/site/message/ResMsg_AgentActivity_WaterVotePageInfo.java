package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

import java.util.HashMap;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/5
 * Description:
 */
public class ResMsg_AgentActivity_WaterVotePageInfo extends ResMsg {
    private static final long serialVersionUID = -4744262360207505614L;

    /* 登录用户姓名 */
    private String userName;

    /* 编号 */
    private String serialNo;

    /* 节水活动列表 */
    private List<HashMap<String, Object>> list;

    /**
     * 总记录数
     */
    private int totalRows;

    /**
     * 总页数
     */
    private int totalPages;

    /* 活动是否开始 */
    private String isStart;

    private String startTime;

    private String endTime;

    /* 是否登录 */
    private String isLogin;

    /* 是否参与过 */
    private String isJoined;

    public String getIsStart() {
        return isStart;
    }

    public void setIsStart(String isStart) {
        this.isStart = isStart;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getIsLogin() {
        return isLogin;
    }

    public void setIsLogin(String isLogin) {
        this.isLogin = isLogin;
    }

    public String getIsJoined() {
        return isJoined;
    }

    public void setIsJoined(String isJoined) {
        this.isJoined = isJoined;
    }

    public List<HashMap<String, Object>> getList() {
        return list;
    }

    public void setList(List<HashMap<String, Object>> list) {
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
