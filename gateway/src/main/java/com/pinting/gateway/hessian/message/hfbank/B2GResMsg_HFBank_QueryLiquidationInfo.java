package com.pinting.gateway.hessian.message.hfbank;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.hfbank.model.QueryLiquidationData;

/**
 * 4.8.6.清算状态查询
 * @author SHENGP
 * @date  2017年5月11日 下午4:38:47
 */
public class B2GResMsg_HFBank_QueryLiquidationInfo extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = -177030734103808836L;

	/* 返回业务数据 */
    private QueryLiquidationData data;

    /* 返回码，10000为成功 */
    private String recode;

    /* 返回结果描述 */
    private String remsg;

	public QueryLiquidationData getData() {
		return data;
	}

	public void setData(QueryLiquidationData data) {
		this.data = data;
	}

	public String getRecode() {
		return recode;
	}

	public void setRecode(String recode) {
		this.recode = recode;
	}

	public String getRemsg() {
		return remsg;
	}

	public void setRemsg(String remsg) {
		this.remsg = remsg;
	}
    
}
