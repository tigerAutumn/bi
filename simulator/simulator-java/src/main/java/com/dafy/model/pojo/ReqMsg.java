package com.dafy.model.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dafy.core.model.IPOJO;
/**
 * 请求信息实体类
 * @author yanwl
 * @date 2015-11-19
 */
public class ReqMsg{
    /**
     * 身份令牌
     */
    private String token;
    
    /**
     * 客户端标识
     */
    private String clientKey;
    
    /**
     * 消息摘要
     */
    private String hash;
    
    /**
     * 业务代码
     */
    private String transCode;

    /**
     * 请求时间
     */
    private Date requestTime;

    /**
     * 大P客户编号
     */
    private String customerId;

    /**
     * 19付企业平台订单号
     */
    private String payOrderNo;

    /**
     * 转帐完成时间
     */
    private Date payFinishTime;

    /**
     * 转账总金额
     */
    private Double amount;

    /**
     * 产品信息
     */
    private String product;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getClientKey() {
		return clientKey;
	}

	public void setClientKey(String clientKey) {
		this.clientKey = clientKey;
	}

	public String getHash() {
		return hash;
	}

	public void setHash(String hash) {
		this.hash = hash;
	}

	public String getTransCode() {
		return transCode;
	}

	public void setTransCode(String transCode) {
		this.transCode = transCode;
	}

	public Date getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(Date requestTime) {
		this.requestTime = requestTime;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getPayOrderNo() {
		return payOrderNo;
	}

	public void setPayOrderNo(String payOrderNo) {
		this.payOrderNo = payOrderNo;
	}

	public Date getPayFinishTime() {
		return payFinishTime;
	}

	public void setPayFinishTime(Date payFinishTime) {
		this.payFinishTime = payFinishTime;
	}

	public Double getAmount() {
		return amount;
	}

	public void setAmount(Double amount) {
		this.amount = amount;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}