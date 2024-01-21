package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsUserKeepView extends PageInfoObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 733320383389134270L;

	private Integer id;

    private Date registDate;

    private Integer extensiveAgentId;

    private String agentName;

    private Integer registNum;

    private Integer day2LoginNum;

    private Integer day3LoginNum;

    private Integer day7LoginNum;

    private Integer day14LoginNum;

    private Integer day30LoginNum;

    private Integer day60LoginNum;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getRegistDate() {
        return registDate;
    }

    public void setRegistDate(Date registDate) {
        this.registDate = registDate;
    }

    public Integer getExtensiveAgentId() {
        return extensiveAgentId;
    }

    public void setExtensiveAgentId(Integer extensiveAgentId) {
        this.extensiveAgentId = extensiveAgentId;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getRegistNum() {
        return registNum;
    }

    public void setRegistNum(Integer registNum) {
        this.registNum = registNum;
    }

    public Integer getDay2LoginNum() {
        return day2LoginNum;
    }

    public void setDay2LoginNum(Integer day2LoginNum) {
        this.day2LoginNum = day2LoginNum;
    }

    public Integer getDay3LoginNum() {
        return day3LoginNum;
    }

    public void setDay3LoginNum(Integer day3LoginNum) {
        this.day3LoginNum = day3LoginNum;
    }

    public Integer getDay7LoginNum() {
        return day7LoginNum;
    }

    public void setDay7LoginNum(Integer day7LoginNum) {
        this.day7LoginNum = day7LoginNum;
    }

    public Integer getDay14LoginNum() {
        return day14LoginNum;
    }

    public void setDay14LoginNum(Integer day14LoginNum) {
        this.day14LoginNum = day14LoginNum;
    }

    public Integer getDay30LoginNum() {
        return day30LoginNum;
    }

    public void setDay30LoginNum(Integer day30LoginNum) {
        this.day30LoginNum = day30LoginNum;
    }

    public Integer getDay60LoginNum() {
        return day60LoginNum;
    }

    public void setDay60LoginNum(Integer day60LoginNum) {
        this.day60LoginNum = day60LoginNum;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}