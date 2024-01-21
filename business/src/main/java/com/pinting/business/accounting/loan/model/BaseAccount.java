package com.pinting.business.accounting.loan.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.util.Constants;

import java.util.HashMap;
import java.util.Map;

public class BaseAccount {
	
	public static Map<PartnerEnum, PartnerSysActCode> partnerActCodeMap = new HashMap<PartnerEnum, PartnerSysActCode>(){
		{
			PartnerSysActCode zanActCode = new PartnerSysActCode();
			zanActCode.setAuthActCode(Constants.SYS_ACCOUNT_BGW_AUTH_ZAN);
			zanActCode.setBadLoansActCode(Constants.SYS_ACCOUNT_BADLOANS_ZAN);
			zanActCode.setMarginActCode(Constants.SYS_ACCOUNT_THD_MARGIN_ZAN);
			zanActCode.setRegActCode(Constants.SYS_ACCOUNT_BGW_REG_ZAN);
			zanActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_ZAN);
			zanActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_ZAN);
			zanActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_ZAN);
			zanActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
			zanActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN);
			put(PartnerEnum.ZAN, zanActCode);
			
			PartnerSysActCode yunActCode = new PartnerSysActCode();
			yunActCode.setRegActCode(Constants.SYS_ACCOUNT_REG_YUN);
			yunActCode.setReturnActCode(Constants.SYS_ACCOUNT_RETURN_YUN);
			put(PartnerEnum.YUN_DAI, zanActCode);
			
			PartnerSysActCode sevenActCode = new PartnerSysActCode();
			sevenActCode.setRegActCode(Constants.SYS_ACCOUNT_REG_7);
			sevenActCode.setReturnActCode(Constants.SYS_ACCOUNT_RETURN_7);
			put(PartnerEnum.SEVEN_DAI, zanActCode);

			PartnerSysActCode yunSelfActCode = new PartnerSysActCode();
			yunSelfActCode.setAuthActCode(Constants.SYS_ACCOUNT_BGW_AUTH_YUN);
			yunSelfActCode.setRegActCode(Constants.SYS_ACCOUNT_BGW_REG_YUN);
			yunSelfActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_YUN);
			yunSelfActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_YUN);
			yunSelfActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_YUN);
			yunSelfActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
			yunSelfActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN);
			yunSelfActCode.setDepHeadFeeActCode(Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN);
			put(PartnerEnum.YUN_DAI_SELF, yunSelfActCode);

			PartnerSysActCode zsdActCode = new PartnerSysActCode();
			zsdActCode.setAuthActCode(Constants.SYS_ACCOUNT_BGW_AUTH_ZSD);
			zsdActCode.setMarginActCode(Constants.SYS_ACCOUNT_THD_MARGIN_ZSD);
			zsdActCode.setRegActCode(Constants.SYS_ACCOUNT_BGW_REG_ZSD);
			zsdActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_ZSD);
			zsdActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_ZSD);
			zsdActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_ZSD);
			zsdActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
			zsdActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD);
			zsdActCode.setDepHeadFeeActCode(Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD);
			put(PartnerEnum.ZSD, zsdActCode);

			PartnerSysActCode sevenDaySelfActCode = new PartnerSysActCode();
			sevenDaySelfActCode.setAuthActCode(Constants.SYS_ACCOUNT_BGW_AUTH_7);
			sevenDaySelfActCode.setMarginActCode(Constants.SYS_ACCOUNT_THD_MARGIN_7);
			sevenDaySelfActCode.setRegActCode(Constants.SYS_ACCOUNT_BGW_REG_7);
			sevenDaySelfActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_7);
			sevenDaySelfActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_7);
			sevenDaySelfActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_7);
			sevenDaySelfActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
			sevenDaySelfActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7);
			sevenDaySelfActCode.setDepHeadFeeActCode(Constants.SYS_ACCOUNT_DEP_HEADFEE_7);
			put(PartnerEnum.SEVEN_DAI_SELF, sevenDaySelfActCode);
			
			//自由资金自由
			PartnerSysActCode freeActCode = new PartnerSysActCode();
			freeActCode.setAuthActCode(Constants.SYS_ACCOUNT_BGW_AUTH_FREE);
			freeActCode.setRegActCode(Constants.SYS_ACCOUNT_BGW_REG_FREE);
			put(PartnerEnum.FREE, freeActCode);
			
		}
	};
	
	
	/**
	 * 合作方
	 */
	private PartnerEnum partner;
	/**
	 * 投资人用户编号
	 */
	private Integer investorUserId;
	/**
	 * 借款人用户编号
	 */
	private Integer borrowerUserId;
	
	/**
	 * 发生金额
	 */
	private Double amount;

	/**
	 * 实际金额
	 */
	private Double realAmount;

	/**
	 * 红包金额
	 */
	private Double redPacAmount;
	
	public PartnerEnum getPartner() {
		return partner;
	}
	public void setPartner(PartnerEnum partner) {
		this.partner = partner;
	}
	public Integer getInvestorUserId() {
		return investorUserId;
	}
	public void setInvestorUserId(Integer investorUserId) {
		this.investorUserId = investorUserId;
	}
	public Integer getBorrowerUserId() {
		return borrowerUserId;
	}
	public void setBorrowerUserId(Integer borrowerUserId) {
		this.borrowerUserId = borrowerUserId;
	}
	
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	@Override
	public String toString() {
		return "BaseAccount [partner=" + partner + ", investorUserId="
				+ investorUserId + ", borrowerUserId=" + borrowerUserId
				+ ", amount=" + amount + ", realAmount=" + realAmount
				+ ", redPacAmount=" + redPacAmount + "]";
	}

	public Double getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(Double realAmount) {
		this.realAmount = realAmount;
	}

	public Double getRedPacAmount() {
		return redPacAmount;
	}

	public void setRedPacAmount(Double redPacAmount) {
		this.redPacAmount = redPacAmount;
	}

	/**
	 * 根据借款端合作方编号，获取自由站岗户之外的其他对应系统户
	 * @param loanPartner 借款端合作方编号
	 * @return
     */
	public static PartnerSysActCode getFreeActLoanPartner(PartnerEnum loanPartner){
		PartnerSysActCode freeActCode = partnerActCodeMap.get(PartnerEnum.FREE);
		switch (loanPartner){
			case YUN_DAI_SELF :
				freeActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_YUN);
				freeActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_YUN);
				freeActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_YUN);
				freeActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
				freeActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN);
				freeActCode.setDepHeadFeeActCode(Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN);
				break;
			case SEVEN_DAI_SELF :
				freeActCode.setMarginActCode(Constants.SYS_ACCOUNT_THD_MARGIN_7);
				freeActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_7);
				freeActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_7);
				freeActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_7);
				freeActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
				freeActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7);
				freeActCode.setDepHeadFeeActCode(Constants.SYS_ACCOUNT_DEP_HEADFEE_7);
				break;
			case ZSD :
				freeActCode.setMarginActCode(Constants.SYS_ACCOUNT_THD_MARGIN_ZSD);
				freeActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_ZSD);
				freeActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_ZSD);
				freeActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_ZSD);
				freeActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
				freeActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD);
				freeActCode.setDepHeadFeeActCode(Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD);
				break;
			case ZAN :
				freeActCode.setBadLoansActCode(Constants.SYS_ACCOUNT_BADLOANS_ZAN);
				freeActCode.setMarginActCode(Constants.SYS_ACCOUNT_THD_MARGIN_ZAN);
				freeActCode.setReturnActCode(Constants.SYS_ACCOUNT_BGW_RETURN_ZAN);
				freeActCode.setRevenueActCode(Constants.SYS_ACCOUNT_THD_REVENUE_ZAN);
				freeActCode.setBgwRevenueActCode(Constants.SYS_ACCOUNT_THD_BGW_REVENUE_ZAN);
				freeActCode.setRepayActCode(Constants.SYS_ACCOUNT_THD_REPAY);
				freeActCode.setBgwDepRevenueActCode(Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN);
				break;
			default:
				break;
		}

		return freeActCode;
	}
}
