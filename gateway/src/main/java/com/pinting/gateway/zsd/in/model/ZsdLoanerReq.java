package com.pinting.gateway.zsd.in.model;

import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Pattern;

public class ZsdLoanerReq extends BaseReqModel {

    /**
     * 用户编号
     */
    @NotEmpty(message = "用户编号不能为空")
    private String userId;

    /**
     * 姓名
     */
    @NotEmpty(message = "姓名不能为空")
    private String name;

    /**
     * 注册手机号
     */
    @NotEmpty(message = "手机号不能为空")
    @Pattern(regexp="^[0-9]{11}$",message="手机号格式错误")
    private  String regMobile;

    /**
     * 身份证号
     */
    @NotEmpty(message = "身份证号不能为空")
    @Pattern(regexp="^(\\d{18,18}|\\d{15,15}|\\d{17,17}x|\\d{17,17}X)$",message="身份证号格式错误")
    private String idCard;

    /**
     * 职业
     */
    private String profession;

    /**
     * 年收入
     */
    private String annualIncome;

    /**
     * 省编码
     */
    private String provinceCode;

    /**
     * 市编码
     */
    private String cityCode;

    /**
     * 区编码
     */
    private String areaCode;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRegMobile() {
        return regMobile;
    }

    public void setRegMobile(String regMobile) {
        this.regMobile = regMobile;
    }

    public String getIdCard() {
        return idCard;
    }

    public void setIdCard(String idCard) {
        this.idCard = idCard;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public String getAnnualIncome() {
        return annualIncome;
    }

    public void setAnnualIncome(String annualIncome) {
        this.annualIncome = annualIncome;
    }

    public String getProvinceCode() {
        return provinceCode;
    }

    public void setProvinceCode(String provinceCode) {
        this.provinceCode = provinceCode;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public String getAreaCode() {
        return areaCode;
    }

    public void setAreaCode(String areaCode) {
        this.areaCode = areaCode;
    }
}
