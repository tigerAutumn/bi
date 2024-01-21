package com.pinting.gateway.hfbank.out.model;

/**
 * Created by cyb on 2017/10/16.
 */
public class UpdatePlatAcctReqModel extends BaseReqModel {

    // 平台客户号
    private String platcust;
    // 客户类型（1:个人客户，2:企业客户，不传参数则默认为”个人客户“）
    private String cus_type;
    // 电子邮箱
    private String email;
    // 手机号码
    private String mobile;
    // 企业名称（企业客户必填）
    private String org_name;
    // 营业执照编号（企业客户，营业执照编号、社会信用代码证要二选一）
    private String business_license;
    // 社会信用代码证（企业客户，营业执照编号、社会信用代码证要二选一）
    private String bank_license;
    // 异步通知地址，企业客户必填
    private String notify_url;

    public String getPlatcust() {
        return platcust;
    }

    public void setPlatcust(String platcust) {
        this.platcust = platcust;
    }

    public String getCus_type() {
        return cus_type;
    }

    public void setCus_type(String cus_type) {
        this.cus_type = cus_type;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrg_name() {
        return org_name;
    }

    public void setOrg_name(String org_name) {
        this.org_name = org_name;
    }

    public String getBusiness_license() {
        return business_license;
    }

    public void setBusiness_license(String business_license) {
        this.business_license = business_license;
    }

    public String getBank_license() {
        return bank_license;
    }

    public void setBank_license(String bank_license) {
        this.bank_license = bank_license;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

}
