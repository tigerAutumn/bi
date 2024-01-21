package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsAgent extends PageInfoObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = -3464108046495398673L;

	private Integer id;

    private String agentName;

    private Integer serialId;

    private String agentCode;

    private String agentPic;

    private Integer pageViewTimes;

    private String note;

    private String dept;

    private String agentType;

    private String supportTerminal;

    private Date createTime;

    private Date updateTime;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public Integer getSerialId() {
        return serialId;
    }

    public void setSerialId(Integer serialId) {
        this.serialId = serialId;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentPic() {
        return agentPic;
    }

    public void setAgentPic(String agentPic) {
        this.agentPic = agentPic;
    }

    public Integer getPageViewTimes() {
        return pageViewTimes;
    }

    public void setPageViewTimes(Integer pageViewTimes) {
        this.pageViewTimes = pageViewTimes;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
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

	public String getAgentType() {
		return agentType;
	}

	public void setAgentType(String agentType) {
		this.agentType = agentType;
	}

	public String getSupportTerminal() {
		return supportTerminal;
	}

	public void setSupportTerminal(String supportTerminal) {
		this.supportTerminal = supportTerminal;
	}
}