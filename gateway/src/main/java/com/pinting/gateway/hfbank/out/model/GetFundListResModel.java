package com.pinting.gateway.hfbank.out.model;

import java.util.List;

import com.pinting.gateway.hessian.message.hfbank.model.GetFundListDetailData;

/**
 * Author:      yed
 * Date:        2017/5/25
 * Description: 资金变动明细查询响应信息
 */
public class GetFundListResModel extends BaseResModel {
	
	private GetFundListDetailData data;

	public GetFundListDetailData getData() {
		return data;
	}

	public void setData(GetFundListDetailData data) {
		this.data = data;
	}
}