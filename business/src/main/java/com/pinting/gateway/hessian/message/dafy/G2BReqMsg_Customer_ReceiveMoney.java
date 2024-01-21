package com.pinting.gateway.hessian.message.dafy;

import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.gateway.hessian.message.dafy.model.ReceiveMoneyDetail;

/**
 * @Project: gateway
 * @Title: G2BReqMsg_Customer_ReceiveMoney.java
 * @author Zhou Changzai
 * @date 2015-4-2 下午8:40:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class G2BReqMsg_Customer_ReceiveMoney extends ReqMsg{
	private static final long serialVersionUID = 4965944000931091777L;
	
	/**
	 * 	ReceiveMoneyDetail：
	 * 	private String cardNo;//	身份证号
		private String name;//	客户姓名
		private String bankNo;//	银行卡号
		private double amountBj;//	理财本金
		private double amountLx;//	理财收益
		private Date successTime;//	成功时间
		private int status;//	状态 status (0成功 1失败)
		private String orderNo;//	订单号
		private String productId;//	订单号
	 */
	private List<ReceiveMoneyDetail> dataList;

	public List<ReceiveMoneyDetail> getDataList() {
		return dataList;
	}
	public void setDataList(List<ReceiveMoneyDetail> dataList) {
		this.dataList = dataList;
	}
	
}
