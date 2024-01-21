package com.pinting.open.pojo.request.product;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * 
 * @ClassName: CheckIDCardSecondEditionRequest 
 * @Description: 身份证验证接口第二版
 * @author lenovo
 * @date 2016年4月11日 下午9:39:31 
 *
 */
public class CheckIDCardSecondEditionRequest extends Request {

	private String idCard;
	
	public String getIdCard() {
		return idCard;
	}

	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}

	@Override
	public String restApiUrl() {
		return Constants.BASE_REST_URL + "/mobile/product/checkIDCard";
	}

	@Override
	public String testApiUrl() {
		return Constants.BASE_TEST_URL + "/mobile/product/checkIDCard";
	}

}
