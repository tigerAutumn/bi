package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.Date;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 批量开户(四要素绑卡)细节请求信息
 */
public class BatchRegistExtDetail implements Serializable {

    private static final long serialVersionUID = -8080422493460037617L;

    /* 明细编号 */
    private String detail_no;
    /* 用户姓名 */
    private String name;
    /* 证件类型（1：身份证） */
    private String id_type;
    /* 证件号码 */
    private String id_code;
    /* 手机号码 */
    private String mobile;
    /* 电子邮箱 */
    private String email;
    /* 性别（0:男性，1:女性） */
    private String sex;
    /* 客户类型（1:个人客户，2:企业客户，不传参数则默认为”个人客户“） */
    private String cus_type;
    /* 出生日期 */
    private Date birthday;
    /* 开户行 */
    private String open_branch;
    /* 卡号 */
    private String card_no;
    /* 卡类型(1:借记卡，2:信用卡) */
    private String card_type;
    /* 预留手机号 */
    private String pre_mobile;
    /* 支付通道 */
    private String pay_code;
    /* 异步通知地址，企业客户必填 */
    private String notify_url;
    /* 备注 */
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

    public String getId_type() {
        return id_type;
    }

    public void setId_type(String id_type) {
        this.id_type = id_type;
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

    public String getCus_type() {
        return cus_type;
    }

    public void setCus_type(String cus_type) {
        this.cus_type = cus_type;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getOpen_branch() {
        return open_branch;
    }

    public void setOpen_branch(String open_branch) {
        this.open_branch = open_branch;
    }

    public String getCard_no() {
        return card_no;
    }

    public void setCard_no(String card_no) {
        this.card_no = card_no;
    }

    public String getCard_type() {
        return card_type;
    }

    public void setCard_type(String card_type) {
        this.card_type = card_type;
    }

    public String getPre_mobile() {
        return pre_mobile;
    }

    public void setPre_mobile(String pre_mobile) {
        this.pre_mobile = pre_mobile;
    }

    public String getPay_code() {
        return pay_code;
    }

    public void setPay_code(String pay_code) {
        this.pay_code = pay_code;
    }

    public String getNotify_url() {
        return notify_url;
    }

    public void setNotify_url(String notify_url) {
        this.notify_url = notify_url;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
