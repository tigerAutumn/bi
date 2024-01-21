/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.model.cfca;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: MakeSeal.java, v 0.1 2015-9-15 下午7:55:56 BabyShark Exp $
 */
public class MakeSealReq {

    private String pfxPath;
    private String pfxPassword;
    private String imagePath;
    private String sealPerson;
    private String sealOrg;
    private String sealName;
    private String sealCode;
    private String sealPassword;
    private String sealType = "0";
    private String imageCode;
    private String orgCode  = "F001";

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
    }

    public String getSealPerson() {
        return sealPerson;
    }

    public void setSealPerson(String sealPerson) {
        this.sealPerson = sealPerson;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public String getSealType() {
        return sealType;
    }

    public void setSealType(String sealType) {
        this.sealType = sealType;
    }

    public String getImageCode() {
        return imageCode;
    }

    public void setImageCode(String imageCode) {
        this.imageCode = imageCode;
    }

}
