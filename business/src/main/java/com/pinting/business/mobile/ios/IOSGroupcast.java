package com.pinting.business.mobile.ios;


import org.json.JSONObject;

import com.pinting.business.mobile.IOSNotification;

public class IOSGroupcast extends IOSNotification {
	public IOSGroupcast() throws Exception {
			/*setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
