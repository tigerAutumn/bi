package com.pinting.business.mobile.android;

import com.pinting.business.mobile.AndroidNotification;


public class AndroidBroadcast extends AndroidNotification {
	public AndroidBroadcast() throws Exception {
			/*setAppMasterSecret(appMasterSecret);String appkey,String appMasterSecret
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "broadcast");	
	}
}
