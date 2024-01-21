package com.pinting.open.pojo.response.asset;

import com.pinting.open.base.response.Response;

public class MyAssetResponse extends Response {
	
	private String mobile; //手机号码
	
	private Double assetAmount; //账户总资产
	
	private Double totalInvestEarnings;  //累计收益
	
	private Double investEarnings; //当前投资收益
	
	private Double totalBonus;//活动奖励
	
	private Double availableBalance; //结算户账户可用余额
	
	private Double dayInvestEarnings; //当日收益
	
	private Double jljCanWithdraw; ///奖励金账户可提现金额，我的奖励
	
	private Integer investNum;  //我的投资（当前购买产品数）
	
	private int less7Days; //七天内产品到期的数目
	
	private Integer processingNum;//当前用户处理中订单的数量(交易明细)
	
	private Integer bankCardCount; //银行卡张数
	
	private Integer redPacketNum; //红包数量
	
	private boolean HaveFirstCard;  //是否有回款卡
	
	private boolean HavePayPassword;  //是否有交易密码
	
	private String  isShowReturnPath; //是否显示回款路径文字
	
	private Double  depBalance; //存管户可用余额
	
	private String  noticeType; //页面加载提示类型（OPEN 开通、ACTIVATE 激活、NO 无提示）
	
	private String  depAccountStatus; //存管户状态(OLD 只有存管前账户   、DEP  只有存管户 、 DOUBLE  双账户并存)

	private String riskStatus; // 风险测评状态 测评过-yes；未测评-no；已过期-expire

	public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}

	public boolean isHaveFirstCard() {
		return HaveFirstCard;
	}

	public void setHaveFirstCard(boolean haveFirstCard) {
		HaveFirstCard = haveFirstCard;
	}

	public boolean isHavePayPassword() {
		return HavePayPassword;
	}

	public void setHavePayPassword(boolean havePayPassword) {
		HavePayPassword = havePayPassword;
	}

	public Integer getRedPacketNum() {
		return redPacketNum;
	}

	public void setRedPacketNum(Integer redPacketNum) {
		this.redPacketNum = redPacketNum;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getAssetAmount() {
		return assetAmount;
	}

	public void setAssetAmount(Double assetAmount) {
		this.assetAmount = assetAmount;
	}

	public Double getTotalInvestEarnings() {
		return totalInvestEarnings;
	}

	public void setTotalInvestEarnings(Double totalInvestEarnings) {
		this.totalInvestEarnings = totalInvestEarnings;
	}
	
	

	public Double getInvestEarnings() {
		return investEarnings;
	}

	public void setInvestEarnings(Double investEarnings) {
		this.investEarnings = investEarnings;
	}

	public Double getTotalBonus() {
		return totalBonus;
	}

	public void setTotalBonus(Double totalBonus) {
		this.totalBonus = totalBonus;
	}

	public Double getAvailableBalance() {
		return availableBalance;
	}

	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}

	public Double getDayInvestEarnings() {
		return dayInvestEarnings;
	}

	public void setDayInvestEarnings(Double dayInvestEarnings) {
		this.dayInvestEarnings = dayInvestEarnings;
	}

	public Double getJljCanWithdraw() {
		return jljCanWithdraw;
	}

	public void setJljCanWithdraw(Double jljCanWithdraw) {
		this.jljCanWithdraw = jljCanWithdraw;
	}

	public Integer getInvestNum() {
		return investNum;
	}

	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}

	public int getLess7Days() {
		return less7Days;
	}

	public void setLess7Days(int less7Days) {
		this.less7Days = less7Days;
	}

	public Integer getProcessingNum() {
		return processingNum;
	}

	public void setProcessingNum(Integer processingNum) {
		this.processingNum = processingNum;
	}

	public Integer getBankCardCount() {
		return bankCardCount;
	}

	public void setBankCardCount(Integer bankCardCount) {
		this.bankCardCount = bankCardCount;
	}

	public String getIsShowReturnPath() {
		return isShowReturnPath;
	}

	public void setIsShowReturnPath(String isShowReturnPath) {
		this.isShowReturnPath = isShowReturnPath;
	}

	public Double getDepBalance() {
		return depBalance;
	}

	public void setDepBalance(Double depBalance) {
		this.depBalance = depBalance;
	}

	public String getNoticeType() {
		return noticeType;
	}

	public void setNoticeType(String noticeType) {
		this.noticeType = noticeType;
	}

	public String getDepAccountStatus() {
		return depAccountStatus;
	}

	public void setDepAccountStatus(String depAccountStatus) {
		this.depAccountStatus = depAccountStatus;
	}
}
