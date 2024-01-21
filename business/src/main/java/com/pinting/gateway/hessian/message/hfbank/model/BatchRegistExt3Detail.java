package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量开户（实名认证）针对每一条借款人请求信息
 */
public class BatchRegistExt3Detail implements Serializable {

    private static final long serialVersionUID = 3595684610155276702L;

    /* 明细编号*/
    private String detail_no;
    /* 用户姓名(个人客户必填)*/
    private String name;
    /* 身份证号码,个人客户必填*/
    private String id_code;
    /* 手机号码(个人客户必填)*/
    private String mobile;
    /* 电子邮箱*/
    private String email;
    /* 性别（0:男性，1:女性）*/
    private String sex;
    /* 出生日期*/
    private Date birthday;
    /* 客户类型（1:个人客户，2:企业客户，不传参数则默认为”个人客户“）*/
    private String cus_type;
    /* 企业名称（企业客户必填）*/
    private String org_name;
    /* 营业执照编号（企业客户，营业执照编号、社会信用代码证要二选一）*/
    private String business_license;
    /* 社会信用代码证（企业客户，营业执照编号、社会信用代码证要二选一）*/
    private String bank_license;
    /* 备注*/
    private String remark;

    public String getDetail_no() {
        return detail_no;
    }

    public void setDetail_no(String detail_no) {
        this.detail_no = detail_no;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId_code() {
        return id_code;
    }

    public void setId_code(String id_code) {
        this.id_code = id_code;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getCus_type() {
        return cus_type;
    }

    public void setCus_type(String cus_type) {
        this.cus_type = cus_type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
