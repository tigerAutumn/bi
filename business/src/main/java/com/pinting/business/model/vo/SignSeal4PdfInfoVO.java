package com.pinting.business.model.vo;

import com.pinting.business.enums.SealPDFType;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SignSeal4PdfReq.java, v 0.1 2015-9-17 上午10:10:42 BabyShark Exp $
 */
public class SignSeal4PdfInfoVO {
    private SealPDFType type;
    private String      sealCode;
    private String      sealPassword;
    private String      certDN;
    private String      certSN;
    private String      sealPerson;
    private String      sealLocation;
    private String      sealResson;
    private String      keyword;
    private String      locationStyle; // 上:U；下:D；左:L；右:R；中:C；默认：C
    private String      pdfPath;
    private String      sealedFileName;
    private String      isNew; //添加标记 是新改版签章：Y   非新改版签章：N

    public SealPDFType getType() {
        return type;
    }

    public void setType(SealPDFType type) {
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

    public String getPdfPath() {
        return pdfPath;
    }

    public void setPdfPath(String pdfPath) {
        this.pdfPath = pdfPath;
    }

    public String getSealedFileName() {
        return sealedFileName;
    }

    public void setSealedFileName(String sealedFileName) {
        this.sealedFileName = sealedFileName;
    }

	public String getIsNew() {
		return isNew;
	}

	public void setIsNew(String isNew) {
		this.isNew = isNew;
	}
    
}
