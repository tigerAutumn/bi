package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ResMsg;

public class B2GResMsg_CFCATransfer_UserCert extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1192730959399072540L;

	private Integer           userId;

    private String            userName;

    private String            idCard;

    private String            p10;

    private String            keyIdentifier;

    private String            dn;

    private String            sequenceNo;

    private String            serialNo;

    private String            startTime;

    private String            endTime;

    private String            signatureCert;

    private String            encryptionCert;

    private String            encryptionPrivateKey;

    private String            pfxData;

    private String            pfxPath;

    private String            pfxPassword;

    public String getEncryptionPrivateKey() {
        return encryptionPrivateKey;
    }

    public void setEncryptionPrivateKey(String encryptionPrivateKey) {
        this.encryptionPrivateKey = encryptionPrivateKey;
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

    public String getP10() {
        return p10;
    }

    public void setP10(String p10) {
        this.p10 = p10;
    }

    public String getKeyIdentifier() {
        return keyIdentifier;
    }

    public void setKeyIdentifier(String keyIdentifier) {
        this.keyIdentifier = keyIdentifier;
    }

    public String getDn() {
        return dn;
    }

    public void setDn(String dn) {
        this.dn = dn;
    }

    public String getSequenceNo() {
        return sequenceNo;
    }

    public void setSequenceNo(String sequenceNo) {
        this.sequenceNo = sequenceNo;
    }

    public String getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(String serialNo) {
        this.serialNo = serialNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getSignatureCert() {
        return signatureCert;
    }

    public void setSignatureCert(String signatureCert) {
        this.signatureCert = signatureCert;
    }

    public String getEncryptionCert() {
        return encryptionCert;
    }

    public void setEncryptionCert(String encryptionCert) {
        this.encryptionCert = encryptionCert;
    }

    public String getPfxData() {
        return pfxData;
    }

    public void setPfxData(String pfxData) {
        this.pfxData = pfxData;
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

}
