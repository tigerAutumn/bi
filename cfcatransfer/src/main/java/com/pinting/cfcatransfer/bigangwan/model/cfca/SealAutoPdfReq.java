/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.model.cfca;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SealAutoPdfReq.java, v 0.1 2015-9-15 下午1:47:56 BabyShark Exp $
 */
public class SealAutoPdfReq {

    private String type               = "3";
    private String sealCode           = "S001";
    private String sealPassword       = "11111111";
    private String certDN             = "";
    private String certSN             = "";
    private String page               = "1";
    private String sealPerson         = "admin";
    private String sealLocation       = "beijing";
    private String sealResson         = "测试签章";
    private String lX                 = "10";
    private String lY                 = "10";
    private String keyword            = "真实姓名";
    private String locationStyle      = "C";                                                                             // 上:U；下:D；左:L；右:R；中:C；默认：C
    private String offsetX            = "10";
    private String offsetY            = "10";
    private String certificationLevel = "0";                                                                             // 0:Approval signature(NOT_CERTIFIED)2:Author signature, form filling allowed

    private String path               = "E:\\dingpf\\dafywallet\\CADemo\\src\\cfca\\testData\\达飞移动支付投资协议（CFCA test）.pdf";
    private String sealedFileName;

    public String getSealedFileName() {
        return sealedFileName;
    }

    public void setSealedFileName(String sealedFileName) {
        this.sealedFileName = sealedFileName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
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

    public String getCertDN() {
        return certDN;
    }

    public void setCertDN(String certDN) {
        this.certDN = certDN;
    }

    public String getCertSN() {
        return certSN;
    }

    public void setCertSN(String certSN) {
        this.certSN = certSN;
    }

    public String getPage() {
        return page;
    }

    public void setPage(String page) {
        this.page = page;
    }

    public String getSealPerson() {
        return sealPerson;
    }

    public void setSealPerson(String sealPerson) {
        this.sealPerson = sealPerson;
    }

    public String getSealLocation() {
        return sealLocation;
    }

    public void setSealLocation(String sealLocation) {
        this.sealLocation = sealLocation;
    }

    public String getSealResson() {
        return sealResson;
    }

    public void setSealResson(String sealResson) {
        this.sealResson = sealResson;
    }

    public String getlX() {
        return lX;
    }

    public void setlX(String lX) {
        this.lX = lX;
    }

    public String getlY() {
        return lY;
    }

    public void setlY(String lY) {
        this.lY = lY;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocationStyle() {
        return locationStyle;
    }

    public void setLocationStyle(String locationStyle) {
        this.locationStyle = locationStyle;
    }

    public String getOffsetX() {
        return offsetX;
    }

    public void setOffsetX(String offsetX) {
        this.offsetX = offsetX;
    }

    public String getOffsetY() {
        return offsetY;
    }

    public void setOffsetY(String offsetY) {
        this.offsetY = offsetY;
    }

    public String getCertificationLevel() {
        return certificationLevel;
    }

    public void setCertificationLevel(String certificationLevel) {
        this.certificationLevel = certificationLevel;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
