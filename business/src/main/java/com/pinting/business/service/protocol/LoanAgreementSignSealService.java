package com.pinting.business.service.protocol;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.ProtocolSealVO;

public interface LoanAgreementSignSealService {

	
	 public void protocolSeal(PartnerEnum partner, ProtocolSealVO protocolSeal);
	
}
