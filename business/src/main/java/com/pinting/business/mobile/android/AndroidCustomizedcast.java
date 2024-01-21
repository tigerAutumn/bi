package com.pinting.business.mobile.android;

import com.pinting.business.mobile.AndroidNotification;


public class AndroidCustomizedcast extends AndroidNotification {
	public AndroidCustomizedcast() throws Exception {
			/*setAppMasterSecret(appMasterSecret);
			setPredefinedKeyValue("appkey", appkey);*/
			this.setPredefinedKeyValue("type", "customizedcast");	
	}
	
	public void setAlias(String alias,String aliasType) throws Exception {
    	setPredefinedKeyValue("alias", alias);
    	setPredefinedKeyValue("alias_type", aliasType);
    }
			
	public void setFileId(String fileId,String aliasType) throws Exception {
    	setPredefinedKeyValue("file_id", fileId);
    	setPredefinedKeyValue("alias_type", aliasType);
    }
}
