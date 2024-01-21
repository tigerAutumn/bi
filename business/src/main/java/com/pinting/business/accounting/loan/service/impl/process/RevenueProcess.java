package com.pinting.business.accounting.loan.service.impl.process;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedRevenueSettleService;
import com.pinting.business.model.BsPayOrders;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RevenueProcess implements Runnable{

	private Logger logger = LoggerFactory.getLogger(RevenueProcess.class);

	private PartnerEnum partnerEnum;
	private BsPayOrders bsPayOrders;
	private DepFixedRevenueSettleService depFixedRevenueSettleService;

	public PartnerEnum getPartnerEnum() {
		return partnerEnum;
	}

	public void setPartnerEnum(PartnerEnum partnerEnum) {
		this.partnerEnum = partnerEnum;
	}

	public BsPayOrders getBsPayOrders() {
		return bsPayOrders;
	}

	public void setBsPayOrders(BsPayOrders bsPayOrders) {
		this.bsPayOrders = bsPayOrders;
	}

	public DepFixedRevenueSettleService getDepFixedRevenueSettleService() {
		return depFixedRevenueSettleService;
	}

	public void setDepFixedRevenueSettleService(DepFixedRevenueSettleService depFixedRevenueSettleService) {
		this.depFixedRevenueSettleService = depFixedRevenueSettleService;
	}

	@Override
	public void run() {
		logger.info(">>>进入营收结算通知" + partnerEnum.getName() + "线程<<<");
		if(partnerEnum.equals(PartnerEnum.SEVEN_DAI_SELF)) {
			depFixedRevenueSettleService.sevenRevenueSettleNotify(bsPayOrders);
		} else {
			depFixedRevenueSettleService.revenueSettleNotify(bsPayOrders);
		}
	}

}
