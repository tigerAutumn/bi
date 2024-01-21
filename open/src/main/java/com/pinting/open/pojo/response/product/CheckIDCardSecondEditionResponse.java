package com.pinting.open.pojo.response.product;

import com.pinting.open.base.response.Response;

/**
 * 
 * @ClassName: CheckIDCardSecondEditionResponse 
 * @Description: 身份证验证接口第二版
 * @author lenovo
 * @date 2016年4月11日 下午9:39:47 
 *
 */
public class CheckIDCardSecondEditionResponse extends Response {

	private String respInfo;

	public String getRespInfo() {
		return respInfo;
	}

	public void setRespInfo(String respInfo) {
		this.respInfo = respInfo;
	}
}
