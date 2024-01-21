package com.pinting.core.base;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BaseService {
	protected Logger log = null;
	
	public BaseService(){
		this.log = LoggerFactory.getLogger(this.getClass());
	}

}
