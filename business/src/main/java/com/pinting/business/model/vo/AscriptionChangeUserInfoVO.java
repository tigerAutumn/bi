package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/6/24.
 * 归属变更-币港湾 用户信息查询
 */
public class AscriptionChangeUserInfoVO {

    private String mobile;

    /**
     * 身份证号
     */
    private String idCard;

    private Integer userId;

    private String userName;    //理财人姓名

    /**
     * 注册时间
     */
    private Date registerTime;

    /**
     * 渠道名称
     */
    private String agentName;

    /**
     * 邀请人
     */
    private String recommendName;

    /**
     * 销售人员
     */
    private String saleName;

    /**
     * 客户经理
     */
    private String managerName;

    /**
     * 客户经理id
     */
    private String managerId;

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getRecommendName() {
        return recommendName;
    }

    public void setRecommendName(String recommendName) {
        this.recommendName = recommendName;
    }

    public String getSaleName() {
        return saleName;
    }

    public void setSaleName(String saleName) {
        this.saleName = saleName;
    }

    public String getManagerName() {
        return managerName;
    }

    public void setManagerName(String managerName) {
        this.managerName = managerName;
    }

    public String getManagerId() {
        return managerId;
    }

    public void setManagerId(String managerId) {
        this.managerId = managerId;
    }
}
