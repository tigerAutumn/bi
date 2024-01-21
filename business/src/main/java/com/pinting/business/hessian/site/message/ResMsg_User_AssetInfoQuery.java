package com.pinting.business.hessian.site.message;

import java.util.ArrayList;
import java.util.HashMap;

import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_AssetInfoQuery extends ResMsg {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7397934264789451280L;
	
	private Integer userId;
	private String nick;
	private String userName;
	private String mobile;
	private String email;
	private String idCard;
	private Integer status;
	private Integer isBindName;
	private Integer isBindBank;
	private Integer accountId;
	private Double investEarnings;
	private Double totalInvestEarnings;
	private Double bonus;
	private Double totalBonus;
	private Double assetAmount;
	private Double regularAmount;
	private Integer totalTransNum;
	private Double canWithdraw; //结算户账户可提现金额
	private Double availableBalance; //结算户账户可用余额
	private Double freezeBalance; //结算户账户冻结金额
	private Integer dafyStatus;
	private Integer investNum;
	private Double dayInvestEarnings;
	private Double balance; //结算户余额（账户余额）
	private Integer bankCardCount; //银行卡张数
	private Double jljBalance; //奖励金账户余额
	private Double jljCanWithdraw; ///奖励金账户可提现金额
	private Integer processingNum;//当前用户处理中订单的数量
	private Integer processingBuyNum;//当前用户处理中购买订单的数量
	private Integer redPacketNum;  // 红包数量
	private ArrayList<HashMap<String,Object>> investList;//投资列表
	private int less7Days; //七天内产品到期的数目
	
	
	private String  isShowReturnPath; //是否显示回款路径按钮
	private String  h5ReturnPathHide; //H5隐藏回款路径

	
	//存管改造添加字段
	private Double  depBalance; //存管户余额
	private String  noticeType; //页面加载提示类型（OPEN 开通、ACTIVATE 激活、NO 无提示）
	private String  depAccountStatus; //存管户状态(OLD 只有存管前账户   、DEP  只有存管户 、 DOUBLE  双账户并存)
	private Double  depAvailableBalance; //存管户可用余额
	private Double  depCanWithdraw; //存管户可提现余额
	private Double depFreezeBalance; // 存管冻结余额
	private String havePayPsd;// 有交易密码

	private String riskStatus; // 风险测评状态 测评过-yes；未测评-no；已过期-expire

	public String getRiskStatus() {
		return riskStatus;
	}

	public void setRiskStatus(String riskStatus) {
		this.riskStatus = riskStatus;
	}

	public String getHavePayPsd() {
		return havePayPsd;
	}

	public void setHavePayPsd(String havePayPsd) {
		this.havePayPsd = havePayPsd;
	}

	public Double getDepFreezeBalance() {
		return depFreezeBalance;
	}

	public void setDepFreezeBalance(Double depFreezeBalance) {
		this.depFreezeBalance = depFreezeBalance;
	}
	public Double getDepAvailableBalance() {
		return depAvailableBalance;
	}

	public void setDepAvailableBalance(Double depAvailableBalance) {
		this.depAvailableBalance = depAvailableBalance;
	}

	public Double getDepCanWithdraw() {
		return depCanWithdraw;
	}

	public void setDepCanWithdraw(Double depCanWithdraw) {
		this.depCanWithdraw = depCanWithdraw;
	}

	public Integer getRedPacketNum() {
        return redPacketNum;
    }
    public void setRedPacketNum(Integer redPacketNum) {
        this.redPacketNum = redPacketNum;
    }
    public Double getBalance() {
		return balance;
	}
	public void setBalance(Double balance) {
		this.balance = balance;
	}
	public Double getDayInvestEarnings() {
		return dayInvestEarnings;
	}
	public void setDayInvestEarnings(Double dayInvestEarnings) {
		this.dayInvestEarnings = dayInvestEarnings;
	}
	public Integer getInvestNum() {
		return investNum;
	}
	public void setInvestNum(Integer investNum) {
		this.investNum = investNum;
	}
	public Integer getDafyStatus() {
		return dafyStatus;
	}
	public void setDafyStatus(Integer dafyStatus) {
		this.dafyStatus = dafyStatus;
	}
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getNick() {
		return nick;
	}
	public void setNick(String nick) {
		this.nick = nick;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIdCard() {
		return idCard;
	}
	public void setIdCard(String idCard) {
		this.idCard = idCard;
	}
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public Integer getIsBindName() {
		return isBindName;
	}
	public void setIsBindName(Integer isBindName) {
		this.isBindName = isBindName;
	}
	public Integer getIsBindBank() {
		return isBindBank;
	}
	public void setIsBindBank(Integer isBindBank) {
		this.isBindBank = isBindBank;
	}
	public Integer getAccountId() {
		return accountId;
	}
	public void setAccountId(Integer accountId) {
		this.accountId = accountId;
	}
	public Double getInvestEarnings() {
		return investEarnings;
	}
	public void setInvestEarnings(Double investEarnings) {
		this.investEarnings = investEarnings;
	}
	public Double getTotalInvestEarnings() {
		return totalInvestEarnings;
	}
	public void setTotalInvestEarnings(Double totalInvestEarnings) {
		this.totalInvestEarnings = totalInvestEarnings;
	}
	public Double getBonus() {
		return bonus;
	}
	public void setBonus(Double bonus) {
		this.bonus = bonus;
	}
	public Double getTotalBonus() {
		return totalBonus;
	}
	public void setTotalBonus(Double totalBonus) {
		this.totalBonus = totalBonus;
	}
	public Double getAssetAmount() {
		return assetAmount;
	}
	public void setAssetAmount(Double assetAmount) {
		this.assetAmount = assetAmount;
	}
	public Double getRegularAmount() {
		return regularAmount;
	}
	public void setRegularAmount(Double regularAmount) {
		this.regularAmount = regularAmount;
	}
	public Integer getTotalTransNum() {
		return totalTransNum;
	}
	public void setTotalTransNum(Integer totalTransNum) {
		this.totalTransNum = totalTransNum;
	}
	public Double getCanWithdraw() {
		return canWithdraw;
	}
	public void setCanWithdraw(Double canWithdraw) {
		this.canWithdraw = canWithdraw;
	}
	public Integer getBankCardCount() {
		return bankCardCount;
	}
	public void setBankCardCount(Integer bankCardCount) {
		this.bankCardCount = bankCardCount;
	}
	public ArrayList<HashMap<String, Object>> getInvestList() {
		return investList;
	}
	public void setInvestList(ArrayList<HashMap<String, Object>> investList) {
		this.investList = investList;
	}
	public int getLess7Days() {
		return less7Days;
	}
	public void setLess7Days(int less7Days) {
		this.less7Days = less7Days;
	}
	public Double getAvailableBalance() {
		return availableBalance;
	}
	public void setAvailableBalance(Double availableBalance) {
		this.availableBalance = availableBalance;
	}
	public Double getJljBalance() {
		return jljBalance;
	}
	public void setJljBalance(Double jljBalance) {
		this.jljBalance = jljBalance;
	}
	public Double getFreezeBalance() {
		return freezeBalance;
	}
	public void setFreezeBalance(Double freezeBalance) {
		this.freezeBalance = freezeBalance;
	}
	public Double getJljCanWithdraw() {
		return jljCanWithdraw;
	}
	public void setJljCanWithdraw(Double jljCanWithdraw) {
		this.jljCanWithdraw = jljCanWithdraw;
	}
	public Integer getProcessingNum() {
		return processingNum;
	}
	public void setProcessingNum(Integer processingNum) {
		this.processingNum = processingNum;
	}
	public Integer getProcessingBuyNum() {
		return processingBuyNum;
	}
	public void setProcessingBuyNum(Integer processingBuyNum) {
		this.processingBuyNum = processingBuyNum;
	}
	public String getIsShowReturnPath() {
		return isShowReturnPath;
	}
	public void setIsShowReturnPath(String isShowReturnPath) {
		this.isShowReturnPath = isShowReturnPath;
	}
	public String getH5ReturnPathHide() {
		return h5ReturnPathHide;
	}
	public void setH5ReturnPathHide(String h5ReturnPathHide) {
		this.h5ReturnPathHide = h5ReturnPathHide;
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
