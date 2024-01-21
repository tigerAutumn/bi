package com.pinting.business.hessian.site.message;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_Transfer_TransferDetail extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6963216358337436484L;
	
	private Integer id;

    private Integer productId;

    private Double price;

    private Double worth;

    private Double oldRate;

    private Double realRate;

    private Integer surplusTime;
    
    private Integer userId;
    
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getSurplusTime() {
		return surplusTime;
	}

	public void setSurplusTime(Integer surplusTime) {
		this.surplusTime = surplusTime;
	}

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getWorth() {
        return worth;
    }

    public void setWorth(Double worth) {
        this.worth = worth;
    }

    public Double getOldRate() {
        return oldRate;
    }

    public void setOldRate(Double oldRate) {
        this.oldRate = oldRate;
    }

    public Double getRealRate() {
        return realRate;
    }

    public void setRealRate(Double realRate) {
        this.realRate = realRate;
    }

}
