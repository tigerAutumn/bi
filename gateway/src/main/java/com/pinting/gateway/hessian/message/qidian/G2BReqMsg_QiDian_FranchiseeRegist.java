package com.pinting.gateway.hessian.message.qidian;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.qidian.model.Franchisees;


public class G2BReqMsg_QiDian_FranchiseeRegist extends ReqMsg {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -234298622169268900L;
	
	/*店主开通申请列表*/
	private  	List<Franchisees>  	franchisees;

	public List<Franchisees> getFranchisees() {
		return franchisees;
	}

	public void setFranchisees(List<Franchisees> franchisees) {
		this.franchisees = franchisees;
	}
	
	
}
