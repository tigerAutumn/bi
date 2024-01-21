package com.pinting.gateway.hessian.message.hfbank.model;

import java.io.Serializable;
import java.util.List;

/**
 * Author:      yed
 * Date:        2017/5/25
 * Description: 资金余额查询响应信息业务数据
 */
public class GetFundListDetailData implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3127890673473673646L;
	private List<GetFundListInfoData> fundList;
	public List<GetFundListInfoData> getFundList() {
		return fundList;
	}
	public void setFundList(List<GetFundListInfoData> fundList) {
		this.fundList = fundList;
	}
}