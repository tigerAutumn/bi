package com.pinting.business.model.vo;

public class DepBaoFooBalanceStatisticsVO {
	private Integer rowno;		 				//查询结果序号
	private String transDate;					//对账日期
	private Double outUserWithdraw; 			//用户提现
	private Integer outUserWithdrawCount; 		//用户提现成功笔数
	private Double outBonusWithdraw; 			//奖励金提现
	private Integer outBonusWithdrawCount; 		//奖励金提现成功笔数
	private Double outSysPartnerRevenue; 		//赞分期营收划转
	private Double outSysYunRepayRevenue; 		//云贷营收划转
	private Double outSysYunRepeat; 			//云贷重复划转
	private Double outSysZsdRepayRevenue; 		//赞时贷营收划转
	private Double outSysZsdRepeat; 			//赞时贷重复划转
	private Double outSysSevenRepayRevenue; 	//7贷营收划转
	private Double outSysSevenRepeat; 			//7贷重复划转
	private Double outWithdrawToDepRepayCard; 	//宝付代付到归集户
	private Integer outWithdrawToDepRepayCardCount; //宝付代付到归集户成功笔数 
	private Double cutRepay2Borrower; 			//恒丰归集户代扣存管户
	private Integer cutRepay2BorrowerCount; 	//恒丰归集户代扣存管户
	private Double inDFPending_1; 				//存管钱代偿借款用户还款
	private Integer inDFPending_1Count; 		//存管钱代偿借款用户还款成功笔数 
	private Double inYunDaiBack; 				//老产品回款（云贷）
	private Integer inYunDaiBackCount; 			//老产品回款（云贷）成功笔数 
	private Double inSevenDaiBack; 				//老产品回款（七贷）
	private Integer inSevenDaiBackCount; 		//老产品回款（七贷）成功笔数 
	private Double inDepYunBack; 				//存管云贷产品还款 （主）
	private Integer inDepYunBackCount; 			//存管云贷产品还款 成功笔数（主） 
	private Double inDepZanBack; 				//存管赞分期产品还款（主）
	private Integer inDepZanBackCount; 			//存管赞分期产品还款 成功笔数（主） 
	private Double inDepZsdBack; 				//存管赞时贷产品还款（主）
	private Integer inDepZsdBackCount; 			//存管赞时贷产品还款 成功笔数 （主）
	private Double inDepSevenBack; 				//存管7贷产品还款 （主）
	private Integer inDepSevenBackCount; 		//存管7贷产品还款 成功笔数（主）
	private Double inDepYunBack2; 				//存管云贷产品还款 （辅）
	private Integer inDepYunBackCount2; 		//存管云贷产品还款 成功笔数（辅） 
	private Double inDepZanBack2; 				//存管赞分期产品还款（辅）
	private Integer inDepZanBackCount2; 		//存管赞分期产品还款 成功笔数（辅） 
	private Double inDepZsdBack2; 				//存管赞时贷产品还款（辅）
	private Integer inDepZsdBackCount2; 		//存管赞时贷产品还款 成功笔数（辅）
	private Double inDepSevenBack2; 			//存管7贷产品还款 （辅）
	private Integer inDepSevenBackCount2; 		//存管7贷产品还款 成功笔数（辅）
	private Double inDepYunBack2Transfer; 		//存管云贷产品还款 （辅钱报转账）
	private Integer inDepYunBackCount2Transfer; //存管云贷产品还款 成功笔数 （辅钱报转账）
	private Double inDepZanBack2Transfer; 		//存管赞分期产品还款（辅钱报转账）
	private Integer inDepZanBackCount2Transfer; //存管赞分期产品还款 成功笔数 （辅钱报转账）
	private Double inDepZsdBack2Transfer; 		//存管赞时贷产品还款（辅钱报转账）
	private Integer inDepZsdBackCount2Transfer; //存管赞时贷产品还款 成功笔数 （辅钱报转账）
	private Double inDepYunCompensate; 			//云贷代偿
	private Integer inDepYunCompensateCount; 	//云贷代偿 成功笔数 
	private Double inDepZsdCompensate; 			//赞时贷代偿
	private Integer inDepZsdCompensateCount; 	//赞时贷代偿 成功笔数
	private Double inDepSevenCompensate; 			//云贷代偿
	private Integer inDepSevenCompensateCount; 	//云贷代偿 成功笔数
	public Integer getRowno() {
		return rowno;
	}

	public void setRowno(Integer rowno) {
		this.rowno = rowno;
	}

	public Double getOutUserWithdraw() {
		return outUserWithdraw;
	}

	public void setOutUserWithdraw(Double outUserWithdraw) {
		this.outUserWithdraw = outUserWithdraw;
	}

	public Double getOutBonusWithdraw() {
		return outBonusWithdraw;
	}

	public void setOutBonusWithdraw(Double outBonusWithdraw) {
		this.outBonusWithdraw = outBonusWithdraw;
	}

	public Double getOutSysPartnerRevenue() {
		return outSysPartnerRevenue;
	}

	public void setOutSysPartnerRevenue(Double outSysPartnerRevenue) {
		this.outSysPartnerRevenue = outSysPartnerRevenue;
	}

	public Double getOutSysYunRepayRevenue() {
		return outSysYunRepayRevenue;
	}

	public void setOutSysYunRepayRevenue(Double outSysYunRepayRevenue) {
		this.outSysYunRepayRevenue = outSysYunRepayRevenue;
	}

	public Double getOutSysYunRepeat() {
		return outSysYunRepeat;
	}

	public void setOutSysYunRepeat(Double outSysYunRepeat) {
		this.outSysYunRepeat = outSysYunRepeat;
	}

	public Double getOutWithdrawToDepRepayCard() {
		return outWithdrawToDepRepayCard;
	}

	public void setOutWithdrawToDepRepayCard(Double outWithdrawToDepRepayCard) {
		this.outWithdrawToDepRepayCard = outWithdrawToDepRepayCard;
	}

	public Double getInDFPending_1() {
		return inDFPending_1;
	}

	public void setInDFPending_1(Double inDFPending_1) {
		this.inDFPending_1 = inDFPending_1;
	}

	public Double getInYunDaiBack() {
		return inYunDaiBack;
	}

	public void setInYunDaiBack(Double inYunDaiBack) {
		this.inYunDaiBack = inYunDaiBack;
	}

	public Double getInSevenDaiBack() {
		return inSevenDaiBack;
	}

	public void setInSevenDaiBack(Double inSevenDaiBack) {
		this.inSevenDaiBack = inSevenDaiBack;
	}

	public Double getInDepYunBack() {
		return inDepYunBack;
	}

	public void setInDepYunBack(Double inDepYunBack) {
		this.inDepYunBack = inDepYunBack;
	}

	public Double getInDepZanBack() {
		return inDepZanBack;
	}

	public void setInDepZanBack(Double inDepZanBack) {
		this.inDepZanBack = inDepZanBack;
	}

	public Double getInDepYunCompensate() {
		return inDepYunCompensate;
	}

	public void setInDepYunCompensate(Double inDepYunCompensate) {
		this.inDepYunCompensate = inDepYunCompensate;
	}

	public String getTransDate() {
		return transDate;
	}

	public void setTransDate(String transDate) {
		this.transDate = transDate;
	}

	public Double getOutSysZsdRepeat() {
		return outSysZsdRepeat;
	}

	public void setOutSysZsdRepeat(Double outSysZsdRepeat) {
		this.outSysZsdRepeat = outSysZsdRepeat;
	}

	public Double getInDepZsdBack() {
		return inDepZsdBack;
	}

	public void setInDepZsdBack(Double inDepZsdBack) {
		this.inDepZsdBack = inDepZsdBack;
	}

	public Double getInDepZsdCompensate() {
		return inDepZsdCompensate;
	}

	public void setInDepZsdCompensate(Double inDepZsdCompensate) {
		this.inDepZsdCompensate = inDepZsdCompensate;
	}

	public Double getOutSysZsdRepayRevenue() {
		return outSysZsdRepayRevenue;
	}

	public void setOutSysZsdRepayRevenue(Double outSysZsdRepayRevenue) {
		this.outSysZsdRepayRevenue = outSysZsdRepayRevenue;
	}

	public Integer getOutUserWithdrawCount() {
		return outUserWithdrawCount;
	}

	public void setOutUserWithdrawCount(Integer outUserWithdrawCount) {
		this.outUserWithdrawCount = outUserWithdrawCount;
	}

	public Integer getOutBonusWithdrawCount() {
		return outBonusWithdrawCount;
	}

	public void setOutBonusWithdrawCount(Integer outBonusWithdrawCount) {
		this.outBonusWithdrawCount = outBonusWithdrawCount;
	}

	public Integer getOutWithdrawToDepRepayCardCount() {
		return outWithdrawToDepRepayCardCount;
	}

	public void setOutWithdrawToDepRepayCardCount(
			Integer outWithdrawToDepRepayCardCount) {
		this.outWithdrawToDepRepayCardCount = outWithdrawToDepRepayCardCount;
	}

	public Integer getInDFPending_1Count() {
		return inDFPending_1Count;
	}

	public void setInDFPending_1Count(Integer inDFPending_1Count) {
		this.inDFPending_1Count = inDFPending_1Count;
	}

	public Integer getInYunDaiBackCount() {
		return inYunDaiBackCount;
	}

	public void setInYunDaiBackCount(Integer inYunDaiBackCount) {
		this.inYunDaiBackCount = inYunDaiBackCount;
	}

	public Integer getInSevenDaiBackCount() {
		return inSevenDaiBackCount;
	}

	public void setInSevenDaiBackCount(Integer inSevenDaiBackCount) {
		this.inSevenDaiBackCount = inSevenDaiBackCount;
	}

	public Integer getInDepYunBackCount() {
		return inDepYunBackCount;
	}

	public void setInDepYunBackCount(Integer inDepYunBackCount) {
		this.inDepYunBackCount = inDepYunBackCount;
	}

	public Integer getInDepZanBackCount() {
		return inDepZanBackCount;
	}

	public void setInDepZanBackCount(Integer inDepZanBackCount) {
		this.inDepZanBackCount = inDepZanBackCount;
	}

	public Integer getInDepZsdBackCount() {
		return inDepZsdBackCount;
	}

	public void setInDepZsdBackCount(Integer inDepZsdBackCount) {
		this.inDepZsdBackCount = inDepZsdBackCount;
	}

	public Integer getInDepYunCompensateCount() {
		return inDepYunCompensateCount;
	}

	public void setInDepYunCompensateCount(Integer inDepYunCompensateCount) {
		this.inDepYunCompensateCount = inDepYunCompensateCount;
	}

	public Integer getInDepZsdCompensateCount() {
		return inDepZsdCompensateCount;
	}

	public void setInDepZsdCompensateCount(Integer inDepZsdCompensateCount) {
		this.inDepZsdCompensateCount = inDepZsdCompensateCount;
	}

	public Double getInDepYunBack2() {
		return inDepYunBack2;
	}

	public void setInDepYunBack2(Double inDepYunBack2) {
		this.inDepYunBack2 = inDepYunBack2;
	}

	public Integer getInDepYunBackCount2() {
		return inDepYunBackCount2;
	}

	public void setInDepYunBackCount2(Integer inDepYunBackCount2) {
		this.inDepYunBackCount2 = inDepYunBackCount2;
	}

	public Double getInDepZanBack2() {
		return inDepZanBack2;
	}

	public void setInDepZanBack2(Double inDepZanBack2) {
		this.inDepZanBack2 = inDepZanBack2;
	}

	public Integer getInDepZanBackCount2() {
		return inDepZanBackCount2;
	}

	public void setInDepZanBackCount2(Integer inDepZanBackCount2) {
		this.inDepZanBackCount2 = inDepZanBackCount2;
	}

	public Double getInDepZsdBack2() {
		return inDepZsdBack2;
	}

	public void setInDepZsdBack2(Double inDepZsdBack2) {
		this.inDepZsdBack2 = inDepZsdBack2;
	}

	public Integer getInDepZsdBackCount2() {
		return inDepZsdBackCount2;
	}

	public void setInDepZsdBackCount2(Integer inDepZsdBackCount2) {
		this.inDepZsdBackCount2 = inDepZsdBackCount2;
	}

	public Double getInDepYunBack2Transfer() {
		return inDepYunBack2Transfer;
	}

	public void setInDepYunBack2Transfer(Double inDepYunBack2Transfer) {
		this.inDepYunBack2Transfer = inDepYunBack2Transfer;
	}

	public Integer getInDepYunBackCount2Transfer() {
		return inDepYunBackCount2Transfer;
	}

	public void setInDepYunBackCount2Transfer(Integer inDepYunBackCount2Transfer) {
		this.inDepYunBackCount2Transfer = inDepYunBackCount2Transfer;
	}

	public Double getInDepZanBack2Transfer() {
		return inDepZanBack2Transfer;
	}

	public void setInDepZanBack2Transfer(Double inDepZanBack2Transfer) {
		this.inDepZanBack2Transfer = inDepZanBack2Transfer;
	}

	public Integer getInDepZanBackCount2Transfer() {
		return inDepZanBackCount2Transfer;
	}

	public void setInDepZanBackCount2Transfer(Integer inDepZanBackCount2Transfer) {
		this.inDepZanBackCount2Transfer = inDepZanBackCount2Transfer;
	}

	public Double getInDepZsdBack2Transfer() {
		return inDepZsdBack2Transfer;
	}

	public void setInDepZsdBack2Transfer(Double inDepZsdBack2Transfer) {
		this.inDepZsdBack2Transfer = inDepZsdBack2Transfer;
	}

	public Integer getInDepZsdBackCount2Transfer() {
		return inDepZsdBackCount2Transfer;
	}

	public void setInDepZsdBackCount2Transfer(Integer inDepZsdBackCount2Transfer) {
		this.inDepZsdBackCount2Transfer = inDepZsdBackCount2Transfer;
	}

	public Double getCutRepay2Borrower() {
		return cutRepay2Borrower;
	}

	public void setCutRepay2Borrower(Double cutRepay2Borrower) {
		this.cutRepay2Borrower = cutRepay2Borrower;
	}

	public Integer getCutRepay2BorrowerCount() {
		return cutRepay2BorrowerCount;
	}

	public void setCutRepay2BorrowerCount(Integer cutRepay2BorrowerCount) {
		this.cutRepay2BorrowerCount = cutRepay2BorrowerCount;
	}

	public Double getOutSysSevenRepayRevenue() {
		return outSysSevenRepayRevenue;
	}

	public void setOutSysSevenRepayRevenue(Double outSysSevenRepayRevenue) {
		this.outSysSevenRepayRevenue = outSysSevenRepayRevenue;
	}

	public Double getOutSysSevenRepeat() {
		return outSysSevenRepeat;
	}

	public void setOutSysSevenRepeat(Double outSysSevenRepeat) {
		this.outSysSevenRepeat = outSysSevenRepeat;
	}

	public Double getInDepSevenBack() {
		return inDepSevenBack;
	}

	public void setInDepSevenBack(Double inDepSevenBack) {
		this.inDepSevenBack = inDepSevenBack;
	}

	public Integer getInDepSevenBackCount() {
		return inDepSevenBackCount;
	}

	public void setInDepSevenBackCount(Integer inDepSevenBackCount) {
		this.inDepSevenBackCount = inDepSevenBackCount;
	}

	public Double getInDepSevenBack2() {
		return inDepSevenBack2;
	}

	public void setInDepSevenBack2(Double inDepSevenBack2) {
		this.inDepSevenBack2 = inDepSevenBack2;
	}

	public Integer getInDepSevenBackCount2() {
		return inDepSevenBackCount2;
	}

	public void setInDepSevenBackCount2(Integer inDepSevenBackCount2) {
		this.inDepSevenBackCount2 = inDepSevenBackCount2;
	}

	public Double getInDepSevenCompensate() {
		return inDepSevenCompensate;
	}

	public void setInDepSevenCompensate(Double inDepSevenCompensate) {
		this.inDepSevenCompensate = inDepSevenCompensate;
	}

	public Integer getInDepSevenCompensateCount() {
		return inDepSevenCompensateCount;
	}

	public void setInDepSevenCompensateCount(Integer inDepSevenCompensateCount) {
		this.inDepSevenCompensateCount = inDepSevenCompensateCount;
	}
}