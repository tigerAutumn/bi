package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 踏春活动
 * @author bianyatian
 * @2017-3-24 下午7:19:15
 */
public class ResMsg_Activity_SpringIndex extends ResMsg {

	private static final long serialVersionUID = 5301260321088227678L;
	
	
	/* 推荐产品列表 */
    private List<HashMap<String, Object>> productList;
    
    /* 投资排行榜列表 */
    private List<HashMap<String, Object>> investRankingList;


	public List<HashMap<String, Object>> getProductList() {
		return productList;
	}

	public void setProductList(List<HashMap<String, Object>> productList) {
		this.productList = productList;
	}

	public List<HashMap<String, Object>> getInvestRankingList() {
		return investRankingList;
	}

	public void setInvestRankingList(List<HashMap<String, Object>> investRankingList) {
		this.investRankingList = investRankingList;
	}
    
}
