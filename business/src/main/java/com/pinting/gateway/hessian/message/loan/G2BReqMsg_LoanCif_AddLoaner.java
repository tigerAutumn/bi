package com.pinting.gateway.hessian.message.loan;

import com.pinting.core.hessian.msg.ReqMsg;

public class G2BReqMsg_LoanCif_AddLoaner extends ReqMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7668991398307455461L;
	
	/**
     * 用户编号
     */
    private String userId;

    /**
     * 姓名
     */
    private String name;

    /**
     * 注册手机号
     */
    private  String regMobile;

    /**
     * 身份证号
     */
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
