/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.model;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: UserSealResult.java, v 0.1 2015-9-17 下午4:43:08 BabyShark Exp $
 */
public class UserSealReqModel extends BaseReqModel {


    private Integer           userId;

    private String            userName;

    private String            idCard;

    private String            pfxPath;

    private String            pfxPassword;

    private String            sealPerson;

    private String            sealOrg;

    private String            sealName;

    private String            sealCode;

    private String            sealPassword;

    private String            sealType;

    private String            imagePath;

    private String            imageCode;

    private String            orgCode          = "F001";


    public String getSealType() {
		return sealType;
	}

	public void setSealType(String sealType) {
		this.sealType = sealType;
	}

	public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getPfxPath() {
        return pfxPath;
    }

    public void setPfxPath(String pfxPath) {
        this.pfxPath = pfxPath;
    }

    public String getPfxPassword() {
        return pfxPassword;
    }

    public void setPfxPassword(String pfxPassword) {
        this.pfxPassword = pfxPassword;
    }

    public String getSealPerson() {
        return sealPerson;
    }

    public void setSealPerson(String sealPerson) {
        this.sealPerson = sealPerson;
    }

    public String getSealOrg() {
        return sealOrg;
    }

    public void setSealOrg(String sealOrg) {
        this.sealOrg = sealOrg;
    }

    public String getSealName() {
        return sealName;
    }

    public void setSealName(String sealName) {
        this.sealName = sealName;
    }

    public String getSealCode() {
        return sealCode;
    }

    public void setSealCode(String sealCode) {
        this.sealCode = sealCode;
    }

    public String getSealPassword() {
        return sealPassword;
    }

    public void setSealPassword(String sealPassword) {
        this.sealPassword = sealPassword;
    }

}
