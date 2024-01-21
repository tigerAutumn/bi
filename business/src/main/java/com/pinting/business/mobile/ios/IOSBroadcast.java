package com.pinting.business.mobile.ios;

import com.pinting.business.mobile.IOSNotification;

public class IOSBroadcast extends IOSNotification {
	public IOSBroadcast() throws Exception {
			/*setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "broadcast");	
		
	}
}
