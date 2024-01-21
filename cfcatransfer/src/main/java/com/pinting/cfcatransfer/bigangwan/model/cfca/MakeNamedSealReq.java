/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.model.cfca;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: MakeNamedSealReq.java, v 0.1 2015-9-17 下午1:24:01 BabyShark Exp $
 */
public class MakeNamedSealReq {
    private String pfxPath;
    private String pfxPassword;
    private String sealPerson;
    private String sealOrg;
    private String sealName;
    private String sealCode;
    private String sealPassword;
    private String imageShape  = "3";
    private String imageWidth  = "100";
    private String imageHeight = "25";
    private String color       = "FF0000";
    private String fontSize    = "100";
    private String orgCode     = "F001";

    public String getOrgCode() {
        return orgCode;
    }

    public void setOrgCode(String orgCode) {
        this.orgCode = orgCode;
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

    public String getImageShape() {
        return imageShape;
    }

    public void setImageShape(String imageShape) {
        this.imageShape = imageShape;
    }

    public String getImageWidth() {
        return imageWidth;
    }

    public void setImageWidth(String imageWidth) {
        this.imageWidth = imageWidth;
    }

    public String getImageHeight() {
        return imageHeight;
    }

    public void setImageHeight(String imageHeight) {
        this.imageHeight = imageHeight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getFontSize() {
        return fontSize;
    }

    public void setFontSize(String fontSize) {
        this.fontSize = fontSize;
    }

}
