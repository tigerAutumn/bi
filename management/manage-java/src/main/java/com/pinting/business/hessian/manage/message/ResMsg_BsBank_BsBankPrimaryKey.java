package com.pinting.business.hessian.manage.message;

import java.util.Date;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsBank_BsBankPrimaryKey extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -550368090453582843L;
	
	private Integer id;

    private String name;

    private Integer status;

    private String note;

    private String smallLogo;

    private String largeLogo;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getSmallLogo() {
        return smallLogo;
    }

    public void setSmallLogo(String smallLogo) {
        this.smallLogo = smallLogo;
    }

    public String getLargeLogo() {
        return largeLogo;
    }

    public void setLargeLogo(String largeLogo) {
        this.largeLogo = largeLogo;
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
