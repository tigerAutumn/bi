package com.pinting.business.model;

import java.util.Date;

public class BsAuth {
    private Integer id;

    private Integer userId;

    private String mobile;

    private String mobileCode;

    private Date mobileTime;

    private Integer mobileTimes;

    private Integer mobileCodeUseTimes;

    private Date mobileLastTime;

    private String email;

    private String emailCode;

    private Date emailTime;

    private Integer emailCodeUseTimes;

    private Date emailLastTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getMobileCode() {
        return mobileCode;
    }

    public void setMobileCode(String mobileCode) {
        this.mobileCode = mobileCode;
    }

    public Date getMobileTime() {
        return mobileTime;
    }

    public void setMobileTime(Date mobileTime) {
        this.mobileTime = mobileTime;
    }

    public Integer getMobileTimes() {
        return mobileTimes;
    }

    public void setMobileTimes(Integer mobileTimes) {
        this.mobileTimes = mobileTimes;
    }

    public Integer getMobileCodeUseTimes() {
        return mobileCodeUseTimes;
    }

    public void setMobileCodeUseTimes(Integer mobileCodeUseTimes) {
        this.mobileCodeUseTimes = mobileCodeUseTimes;
    }

    public Date getMobileLastTime() {
        return mobileLastTime;
    }

    public void setMobileLastTime(Date mobileLastTime) {
        this.mobileLastTime = mobileLastTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmailCode() {
        return emailCode;
    }

    public void setEmailCode(String emailCode) {
        this.emailCode = emailCode;
    }

    public Date getEmailTime() {
        return emailTime;
    }

    public void setEmailTime(Date emailTime) {
        this.emailTime = emailTime;
    }

    public Integer getEmailCodeUseTimes() {
        return emailCodeUseTimes;
    }

    public void setEmailCodeUseTimes(Integer emailCodeUseTimes) {
        this.emailCodeUseTimes = emailCodeUseTimes;
    }

    public Date getEmailLastTime() {
        return emailLastTime;
    }

    public void setEmailLastTime(Date emailLastTime) {
        this.emailLastTime = emailLastTime;
    }
}