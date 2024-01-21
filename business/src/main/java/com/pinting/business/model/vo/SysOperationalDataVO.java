package com.pinting.business.model.vo;

import java.util.Date;

import com.pinting.core.util.StringUtil;

/**
 * 管理台运营数据微信用户管理列表
 * @author SHENGUOPING
 * @date  2018年6月13日 上午11:22:59
 */
public class SysOperationalDataVO {

	private Integer id;
	// 昵称
	private String nickName;
	// 真实姓名
	private String mName;
	// 更新时间
	private Date updateTime;
	// 操作人ID
	private Integer opUserId;
	
	private Integer rowno;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNickName() {
		return StringUtil.isEmpty(nickName)? "" : nickName.trim();
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}

	public String getmName() {
		return mName;
	}

	public void setmName(String mName) {
		this.mName = mName;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Integer getOpUserId() {
		return opUserId;
	}

	public void setOpUserId(Integer opUserId) {
		this.opUserId = opUserId;
	}

	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}	
	
}
