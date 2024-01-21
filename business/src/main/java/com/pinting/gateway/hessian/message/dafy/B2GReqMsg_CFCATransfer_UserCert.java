package com.pinting.gateway.hessian.message.dafy;

import com.pinting.core.hessian.msg.ReqMsg;

public class B2GReqMsg_CFCATransfer_UserCert extends ReqMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3269874386565531497L;
	//证书类型：1.普通，2.高级
    private String            certType         = "1";
    // 客户类型：个人：1 企业：2
    private String            customerType     = "1";
    // 用户名
    private String            userName;
    // 证件类型
    private String            identType        = "Z";
    // 证件号码
    private String            identNo;
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

    public String getIdentNo() {
        return identNo;
    }

    public void setIdentNo(String identNo) {
        this.identNo = identNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode;
    }	

}
