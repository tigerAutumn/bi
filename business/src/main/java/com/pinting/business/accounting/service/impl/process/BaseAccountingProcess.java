package com.pinting.business.accounting.service.impl.process;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.pinting.business.model.BsAccountJnl;

public abstract class BaseAccountingProcess {
	public Logger log = LoggerFactory.getLogger(BaseAccountingProcess.class);
	
	public abstract Integer preRecordAccountingExecute(BsAccountJnl accountJnl) ;
	
	public abstract void postfRecordAccountingExecute(BsAccountJnl accountJnl) ;

}
