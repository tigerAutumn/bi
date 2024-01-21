package com.pinting.gateway.hfbank.out.util;

import com.pinting.core.util.GlobEnvUtil;

public class HfbankOutConstant {
	
	public static  String hfbankUrl  = "http://60.212.112.152:6999";
	
	static{
		//恒丰银行存管接口
		hfbankUrl=GlobEnvUtil.get("hfbank.interface.url");
	}

}
