package com.pinting.gateway.hessian.message.qidian;

import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
import com.pinting.gateway.hessian.message.qidian.model.FranchiseeResult;

public class G2BResMsg_QiDian_FranchiseeRegist extends ResMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4362301727073206786L;
	/*店主开通结果列表*/
	private 	List<FranchiseeResult> 		franchiseeResult;
	
	public List<FranchiseeResult> getFranchiseeResult() {
		return franchiseeResult;
	}
	public void setFranchiseeResult(List<FranchiseeResult> franchiseeResult) {
		this.franchiseeResult = franchiseeResult;
	}
	
}
