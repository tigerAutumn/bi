package com.pinting.business.model;

import java.util.Date;

import com.pinting.business.model.vo.PageInfoObject;

public class BsUserChannel extends PageInfoObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1431231283146477549L;

	private Integer id;

    private Integer userId;

    private Integer bankChannelId;

    private Date createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getBankChannelId() {
        return bankChannelId;
    }

    public void setBankChannelId(Integer bankChannelId) {
        this.bankChannelId = bankChannelId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}