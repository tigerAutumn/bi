package com.pinting.gateway.dafy.out.model;

public class Tbdatapermission {
    private String lId;

    private String lUserId;

    private String strDeptCode;

    private Integer nCurrentLevel;
    
    private String lOperateId;
    
    private String dtUpdateTime;

	public String getStrDeptCode() {
		return strDeptCode;
	}

	public void setStrDeptCode(String strDeptCode) {
		this.strDeptCode = strDeptCode;
	}

	public String getDtUpdateTime() {
		return dtUpdateTime;
	}

	public void setDtUpdateTime(String dtUpdateTime) {
		this.dtUpdateTime = dtUpdateTime;
	}

	public String getlId() {
		return lId;
	}

	public void setlId(String lId) {
		this.lId = lId;
	}

	public String getlUserId() {
		return lUserId;
	}

	public void setlUserId(String lUserId) {
		this.lUserId = lUserId;
	}

	public Integer getnCurrentLevel() {
		return nCurrentLevel;
	}

	public void setnCurrentLevel(Integer nCurrentLevel) {
		this.nCurrentLevel = nCurrentLevel;
	}

	public String getlOperateId() {
		return lOperateId;
	}

	public void setlOperateId(String lOperateId) {
		this.lOperateId = lOperateId;
	}

}