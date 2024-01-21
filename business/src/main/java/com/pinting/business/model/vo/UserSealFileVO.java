package com.pinting.business.model.vo;

import java.util.Date;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: UserSealFileVO.java, v 0.1 2015-9-18 下午8:33:31 BabyShark Exp $
 */
public class UserSealFileVO {
    private Integer id;

    private Integer userId;
    
    private String srcAddress;

    private String  fileAddress;

    private String  sealType;

    private String  relativeInfo;

    private String  sealStatus;

    private String  note;

    private Date    createTime;

    private Date    updateTime;
    
    private String userSrc;
    
    private String agreementNo;

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

    public String getSrcAddress() {
		return srcAddress;
	}

	public void setSrcAddress(String srcAddress) {
		this.srcAddress = srcAddress;
	}

	public String getFileAddress() {
        return fileAddress;
    }

    public void setFileAddress(String fileAddress) {
        this.fileAddress = fileAddress;
    }

    public String getSealType() {
        return sealType;
    }

    public void setSealType(String sealType) {
        this.sealType = sealType;
    }

    public String getRelativeInfo() {
        return relativeInfo;
    }

    public void setRelativeInfo(String relativeInfo) {
        this.relativeInfo = relativeInfo;
    }

    public String getSealStatus() {
        return sealStatus;
    }

    public void setSealStatus(String sealStatus) {
        this.sealStatus = sealStatus;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
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

	public String getUserSrc() {
		return userSrc;
	}

	public void setUserSrc(String userSrc) {
		this.userSrc = userSrc;
	}

	public String getAgreementNo() {
		return agreementNo;
	}

	public void setAgreementNo(String agreementNo) {
		this.agreementNo = agreementNo;
	}
    
}
