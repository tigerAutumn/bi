package com.pinting.business.hessian.site.message;

import java.util.HashMap;
import java.util.List;

import com.pinting.core.hessian.msg.ResMsg;
/**
 * 根据上级id号查询城市 出参
 * @author Huang MengJian
 * @2015-2-12 上午10:56:43
 */
public class ResMsg_PCA_InfoQuery extends ResMsg{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2537789830190373324L;
	/*城市列表*/
	private List<HashMap<String,Object>> provinceList;

	public List<HashMap<String, Object>> getProvinceList() {
		return provinceList;
	}

	public void setProvinceList(List<HashMap<String, Object>> provinceList) {
		this.provinceList = provinceList;
	}
	
	
}
