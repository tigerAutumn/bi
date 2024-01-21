package com.dafy.model.pojo;

import com.dafy.core.model.IPOJO;

/**
 * 理财信息明细实体类
 * @author yanwl
 * @date 2015-11-19
 */
public class SimFinancingDetail implements IPOJO{
    /**
	 * 序列化编号
	 */
	private static final long serialVersionUID = -5461137107254438774L;

	/**
     * 编号
     */
    private Integer id;

    /**
     * 总账编号
     */
    private Integer totalId;

    /**
     * 产品唯一订单号
     */
    private String productOrderNo;

    /**
     * 产品编码
     */
    private String productCode;

    /**
     * 产品金额
     */
    private Double productAmount;
    
    /**
     * RECEIVED 已收到 CONFIRMED 已回复确认   CONFIRM_FAIL 回复确认失败   RETURNED 已回款   RETURN_FAIL 回款失败
     */
    private String status;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTotalId() {
		return totalId;
	}

	public void setTotalId(Integer totalId) {
		this.totalId = totalId;
	}

	public String getProductOrderNo() {
		return productOrderNo;
	}

	public void setProductOrderNo(String productOrderNo) {
		this.productOrderNo = productOrderNo;
	}

	public String getProductCode() {
		return productCode;
	}

	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}

	public Double getProductAmount() {
		return productAmount;
	}

	public void setProductAmount(Double productAmount) {
		this.productAmount = productAmount;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
}