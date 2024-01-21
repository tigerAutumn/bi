/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.cfcatransfer.bigangwan.model;

/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: UserCertRequest.java, v 0.1 2015-9-17 下午5:13:57 BabyShark Exp $
 */
public class MakeSealReqModel extends BaseReqModel {
    private String            clientKey;
    //证书类型：1.普通，2.高级
    private String            certType         = "1";
    // 客户类型：个人：1 企业：2
    private String            customerType     = "1";
    // 用户名
    private String            userName;
    // 证件类型
    private String            identType        = "Z";
    // 证件号码
    private String            idCard;
    // 证书所属机构编码
    private String            branchCode       = "678";
    //证书保存路径
    private String            pfxPath;

    public String getPfxPath() {
        return pfxPath;
    }

    public void setPfxPath(String pfxPath) {
        this.pfxPath = pfxPath;
    }

    public String getCertType() {
        return certType;
    }

    public void setCertType(String certType) {
        this.certType = certType;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdentType() {
        return identType;
    }

    public void setIdentType(String identType) {
        this.identType = identType;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }

    public String getClientKey() {
        return clientKey;
    }

    public void setClientKey(String clientKey) {
        this.clientKey = clientKey;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }
}
