package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

/**
 * 1、商品兑换前校验，响应校验结果
 * 2、如果实体商品，则响应用户收货地址
 */
public class ResMsg_MallExchangeFlow_ExchangeCheck extends ResMsg {

    private static final long serialVersionUID = 1L;

    private Boolean checkResult; // 兑换前校验结果：true 校验通过，false 校验失败

    private Boolean isAddrShow; // 是否使用收货地址 true: 实体商品  false 虚拟商品

    private Integer id; // 收货人地址信息表ID

    private Integer userId; // 用户编号ID

    private String recName; // 收货人姓名

    private String recAdress; // 收货人省市区地址

    private String recAdressDetail; // 收货人详细地址

    private String recMobile; // 收货人手机号

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

    public String getRecName() {
        return recName;
    }

    public void setRecName(String recName) {
        this.recName = recName;
    }

    public String getRecAdress() {
        return recAdress;
    }

    public void setRecAdress(String recAdress) {
        this.recAdress = recAdress;
    }

    public String getRecAdressDetail() {
        return recAdressDetail;
    }

    public void setRecAdressDetail(String recAdressDetail) {
        this.recAdressDetail = recAdressDetail;
    }

    public String getRecMobile() {
        return recMobile;
    }

    public void setRecMobile(String recMobile) {
        this.recMobile = recMobile;
    }

    public Boolean getCheckResult() {
        return checkResult;
    }

    public void setCheckResult(Boolean checkResult) {
        this.checkResult = checkResult;
    }

    public Boolean getAddrShow() {
        return isAddrShow;
    }

    public void setAddrShow(Boolean addrShow) {
        isAddrShow = addrShow;
    }
}
