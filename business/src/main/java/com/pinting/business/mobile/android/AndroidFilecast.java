package com.pinting.business.mobile.android;

import com.pinting.business.mobile.AndroidNotification;

public class AndroidFilecast extends AndroidNotification {
	public AndroidFilecast() throws Exception {
			/*setAppMasterSecret(appMasterSecret);String appkey,String appMasterSecret
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}