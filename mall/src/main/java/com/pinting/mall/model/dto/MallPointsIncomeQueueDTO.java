package com.pinting.mall.model.dto;

/**
 * @title 积分发放队列
 * @author Gemma
 * @date 2018-5-9 19:38:46
 */
public class MallPointsIncomeQueueDTO {
	private Integer id;			//积分发放任务主键 
	private Integer userId;		//用户userId 关联 bs_user.id
	private String transType;	//交易类型  交易类型：注册：REGISTER 开通存管：OPEN_DEPOSIT 完成风险测评：FINISH_RISK_ASSESSMENT 投资：INVEST 签到：SIGN
	private String channel; // 渠道 REQUEST_TERMINAL_H5/ REQUEST_TERMINAL_ANDROID/ REQUEST_TERMINAL_IOS/ REQUEST_TERMINAL_PC
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
}
