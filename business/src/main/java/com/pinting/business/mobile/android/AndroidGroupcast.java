package com.pinting.business.mobile.android;


import org.json.JSONObject;

import com.pinting.business.mobile.AndroidNotification;


public class AndroidGroupcast extends AndroidNotification {
	public AndroidGroupcast() throws Exception {
			/*setAppMasterSecret(appMasterSecret);String appkey,String appMasterSecret
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "groupcast");	
	}
	
	public void setFilter(JSONObject filter) throws Exception {
    	setPredefinedKeyValue("filter", filter);
    }
}
