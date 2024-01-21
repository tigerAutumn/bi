package com.dafy.model.pojo;

import java.util.Date;

import com.alibaba.fastjson.annotation.JSONField;
import com.dafy.core.model.IPOJO;
/**
 * 理财信息实体类
 * @author yanwl
 * @date 2015-11-19
 */
public class SimFinancing implements IPOJO{
    /**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -5900917581541175806L;

	/**
     * 编号
     */
    private Integer id;

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
     * 状态    RECEIVED 已收到    CONFIRMED 已回复确认   CONFIRM_FAIL 回复确认失败
     */
    private String status;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 支付平台标志
     */
    private String payPlatform;
    /**
     * 网新在19付商户号
     */
    private String merchantId;
    /**
     * 转账请求时间
     */
    private Date payReqTime;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
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

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
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

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@JSONField(format="yyyy-MM-dd HH:mm:ss")
	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getPayPlatform() {
		return payPlatform;
	}

	public void setPayPlatform(String payPlatform) {
		this.payPlatform = payPlatform;
	}

	public String getMerchantId() {
		return merchantId;
	}

	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	public Date getPayReqTime() {
		return payReqTime;
	}

	public void setPayReqTime(Date payReqTime) {
		this.payReqTime = payReqTime;
	}
}