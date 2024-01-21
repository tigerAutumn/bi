package com.pinting.business.mobile.ios;

import com.pinting.business.mobile.IOSNotification;

public class IOSUnicast extends IOSNotification {
	public IOSUnicast() throws Exception{
			/*setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "unicast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }
}
