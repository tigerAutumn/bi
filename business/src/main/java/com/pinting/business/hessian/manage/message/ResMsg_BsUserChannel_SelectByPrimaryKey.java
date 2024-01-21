package com.pinting.business.hessian.manage.message;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_BsUserChannel_SelectByPrimaryKey extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6698471908038249346L;
	
	private Integer id;

    private Integer userId;
    
    private String userName;

	private Integer bankChannelId;

    private Date createTime;
    
    /** 查找19付银行对应的银行名称-渠道类型-通道优先级 */
    private ArrayList<HashMap<String,Object>> payCardList;
    
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
	
	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
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

	public ArrayList<HashMap<String, Object>> getPayCardList() {
		return payCardList;
	}

	public void setPayCardList(ArrayList<HashMap<String, Object>> payCardList) {
		this.payCardList = payCardList;
	}

}
