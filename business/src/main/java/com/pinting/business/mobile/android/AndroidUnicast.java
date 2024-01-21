package com.pinting.business.mobile.android;

import com.pinting.business.mobile.AndroidNotification;


public class AndroidUnicast extends AndroidNotification {
	public AndroidUnicast() throws Exception {
			//setAppMasterSecret(appMasterSecret);
			//setPredefinedKeyValue("appkey", appkey);
			this.setPredefinedKeyValue("type", "unicast");	
	}
	
	public void setDeviceToken(String token) throws Exception {
    	setPredefinedKeyValue("device_tokens", token);
    }

}