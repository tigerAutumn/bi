package com.pinting.business.mobile.ios;

import com.pinting.business.mobile.IOSNotification;

public class IOSFilecast extends IOSNotification {
	public IOSFilecast() throws Exception {
			/*setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "filecast");	
	}
	
	public void setFileId(String fileId) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    }
}
