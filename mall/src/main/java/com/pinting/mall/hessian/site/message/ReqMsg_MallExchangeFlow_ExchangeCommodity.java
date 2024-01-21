package com.pinting.mall.hessian.site.message;

import com.pinting.core.hessian.msg.ReqMsg;

import javax.validation.constraints.NotNull;

/**
 * 商品兑换
 */
public class ReqMsg_MallExchangeFlow_ExchangeCommodity extends ReqMsg {

    private static final long serialVersionUID = 1L;

    @NotNull(message="商品ID不能为空")
    private Integer id; // 商品信息表ID

    @NotNull(message="用户编码ID不能为空")
    private Integer userId; // 用户编号ID

    private String recName; // 收货人姓名

    private String recAdress; // 收货人省市区地址

    private String recAdressDetail; // 收货人详细地址

    private String recMobile; // 收货人手机号

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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
}
