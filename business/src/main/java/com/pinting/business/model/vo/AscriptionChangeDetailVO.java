package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/6/24.
 */
public class AscriptionChangeDetailVO {

    private Integer userId;

    private String manageMobile;

    private String userMobile;

    private String deptName;

    private String manageName;

    private Integer preManageId;

    private Integer afterManageId;

    /**
     * 业绩节点
     */
    private Date resultsTime;

    private String userName;

    private String preManageName;

    private String afterManageName;

    private String preAgentName;

    private String afterAgentName="达飞财富端";

    /**
     * 业绩变更投资笔数
     */
    private Integer resultsChangeCount;

    /**
     * 业绩变更总额度
     */
    private Long resultsChangeSum;

    /**
     * 新客户经理部门编码
     */
    private String afterDeptCode;

    /**
     * 新客户经理部门id
     */
    private Integer afterDeptId;

    /**
     * 操作人id
     */
    private Integer opUserId;

    /**
     * 注册时间
     */
    private Date registerTime;


    public String getManageMobile() {
        return manageMobile;
    }

    public void setManageMobile(String manageMobile) {
        this.manageMobile = manageMobile;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public String getDeptName() {
        return deptName;
    }

    public void setDeptName(String deptName) {
        this.deptName = deptName;
    }

    public String getManageName() {
        return manageName;
    }

    public void setManageName(String manageName) {
        this.manageName = manageName;
    }

    public Date getResultsTime() {
        return resultsTime;
    }

    public void setResultsTime(Date resultsTime) {
        this.resultsTime = resultsTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPreManageName() {
        return preManageName;
    }

    public void setPreManageName(String preManageName) {
        this.preManageName = preManageName;
    }

    public String getAfterManageName() {
        return afterManageName;
    }

    public void setAfterManageName(String afterManageName) {
        this.afterManageName = afterManageName;
    }

    public String getPreAgentName() {
        return preAgentName;
    }

    public void setPreAgentName(String preAgentName) {
        this.preAgentName = preAgentName;
    }

    public String getAfterAgentName() {
        return afterAgentName;
    }

    public void setAfterAgentName(String afterAgentName) {
        this.afterAgentName = afterAgentName;
    }

    public Integer getResultsChangeCount() {
        return resultsChangeCount;
    }

    public void setResultsChangeCount(Integer resultsChangeCount) {
        this.resultsChangeCount = resultsChangeCount;
    }

    public Long getResultsChangeSum() {
        return resultsChangeSum;
    }

    public void setResultsChangeSum(Long resultsChangeSum) {
        this.resultsChangeSum = resultsChangeSum;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPreManageId() {
        return preManageId;
    }

    public void setPreManageId(Integer preManageId) {
        this.preManageId = preManageId;
    }

    public Integer getAfterManageId() {
        return afterManageId;
    }

    public void setAfterManageId(Integer afterManageId) {
        this.afterManageId = afterManageId;
    }

    public String getAfterDeptCode() {
        return afterDeptCode;
    }

    public void setAfterDeptCode(String afterDeptCode) {
        this.afterDeptCode = afterDeptCode;
    }

    public Integer getAfterDeptId() {
        return afterDeptId;
    }

    public void setAfterDeptId(Integer afterDeptId) {
        this.afterDeptId = afterDeptId;
    }

    public Integer getOpUserId() {
        return opUserId;
    }

    public void setOpUserId(Integer opUserId) {
        this.opUserId = opUserId;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }
}
