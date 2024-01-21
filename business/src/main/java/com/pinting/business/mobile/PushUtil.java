package com.pinting.business.mobile;

import org.json.JSONArray;
import org.json.JSONObject;

import com.pinting.business.mobile.android.AndroidBroadcast;
import com.pinting.business.mobile.android.AndroidCustomizedcast;
import com.pinting.business.mobile.android.AndroidFilecast;
import com.pinting.business.mobile.android.AndroidGroupcast;
import com.pinting.business.mobile.android.AndroidUnicast;
import com.pinting.business.mobile.ios.IOSBroadcast;
import com.pinting.business.mobile.ios.IOSCustomizedcast;
import com.pinting.business.mobile.ios.IOSFilecast;
import com.pinting.business.mobile.ios.IOSGroupcast;
import com.pinting.business.mobile.ios.IOSUnicast;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;


public class PushUtil {
	private static String android_appkey = "";
	private static String android_appMasterSecret = "";
	private static String ios_appkey = "";
	private static String ios_appMasterSecret = "";
	private static PushClient client = new PushClient();
	private static String ALIAS_TYPE = "CoinHarbour";
	
	/*static {
		try {
			android_appkey = GlobEnvUtil.get("android.app.key");
			android_appMasterSecret = GlobEnvUtil.get("android.app.master.secret");
			ios_appkey = GlobEnvUtil.get("ios.app.key");
			ios_appMasterSecret = GlobEnvUtil.get("ios.app.master.secret");
		} catch (Exception e) {
			e.printStackTrace();
			System.exit(1);
		}
	}
	
	public static void pushMsg(UmengNotification msg,String type) throws Exception {
		if(StringUtil.isNotEmpty(type) && "ios".equals(type)) {
			msg.setAppMasterSecret(ios_appMasterSecret);
			msg.setPredefinedKeyValue("appkey", ios_appkey);
		}else {
			msg.setAppMasterSecret(android_appMasterSecret);
			msg.setPredefinedKeyValue("appkey", android_appkey);
		}
		client.send(msg);
	}
	
	public static String getFileld(String alias,String type) throws Exception {
		String fileId = "";
		if(StringUtil.isNotEmpty(type) && "ios".equals(type)) {
			fileId = client.uploadContents(ios_appkey,ios_appMasterSecret,alias);
		}else {
			fileId = client.uploadContents(android_appkey,android_appMasterSecret,alias);
		}
		return fileId;
	}*/
	
	
	public static String pushMsg(UmengNotification msg,String appMasterSecret,String appkey) throws Exception {
		msg.setAppMasterSecret(appMasterSecret);
		msg.setPredefinedKeyValue("appkey", appkey);
		return client.send(msg);
	}
	
	public static String getFileld(String alias,String appMasterSecret,String appkey) throws Exception {
		String fileId = client.uploadContents(appkey,appMasterSecret,alias);
		return fileId;
	}
	
	public static void sendAndroidBroadcast() throws Exception {
//		AndroidBroadcast broadcast = new AndroidBroadcast(appkey,appMasterSecret);
		AndroidBroadcast broadcast = new AndroidBroadcast();
		broadcast.setDescription("后台广播测试");
		broadcast.setTicker( "Android broadcast ticker");
		broadcast.setTitle(  "测试title");
		broadcast.setText(   "hello，你好！1111");
		broadcast.goAppAfterOpen();
		broadcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		broadcast.setProductionMode();
//		broadcast.setTestMode();
		// Set customized fields
		broadcast.setExtraField("test", "helloworld");
		client.send(broadcast);
	}
	
	public static void sendAndroidUnicast() throws Exception {
		//AndroidUnicast unicast = new AndroidUnicast(appkey,appMasterSecret);
		AndroidUnicast unicast = new AndroidUnicast();
		// TODO Set your device token
		unicast.setDeviceToken("Av0XBAgxEJX8vowb3-is_XuEAL2QMhPodviKUMZqvOFh");
		unicast.setTicker("Android unicast ticker");
		unicast.setTitle("这是一个测试");
		unicast.setText("hello，你好！");
		unicast.goAppAfterOpen();
		unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		//unicast.setProductionMode();
		unicast.setTestMode();
		// Set customized fields
		unicast.setExtraField("test", "helloworld");
		client.send(unicast);
	}
	
	public static void sendAndroidGroupcast() throws Exception {
//		AndroidGroupcast groupcast = new AndroidGroupcast(appkey,appMasterSecret);
		AndroidGroupcast groupcast = new AndroidGroupcast();
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"test"},
      	 *			{"tag":"Test"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		JSONObject TestTag = new JSONObject();
		testTag.put("tag", "test");
		TestTag.put("tag", "Test");
		tagArray.put(testTag);
		tagArray.put(TestTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		groupcast.setFilter(filterJson);
		groupcast.setTicker( "Android groupcast ticker");
		groupcast.setTitle(  "中文的title");
		groupcast.setText(   "Android groupcast text");
		groupcast.goAppAfterOpen();
		groupcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		groupcast.setProductionMode();
		client.send(groupcast);
	}
	
	public static void sendAndroidCustomizedcast() throws Exception {
//		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("8065,8063", ALIAS_TYPE);
		customizedcast.setTicker( "Android customizedcast ticker");
		customizedcast.setTitle(  "测试title");
		customizedcast.setText(   "亲，这是一个测试");
		customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		//customizedcast.setProductionMode();
		customizedcast.setTestMode();
		client.send(customizedcast);
	}
	
	public static void sendAndroidCustomizedcastFile() throws Exception {
		//AndroidCustomizedcast customizedcast = new AndroidCustomizedcast(appkey,appMasterSecret);
		AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
		// TODO Set your alias here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		String alias = "8065"+"\n"+"8063";
		//String fileId = client.uploadContents(appkey,appMasterSecret,"aa"+"\n"+"bb"+"\n"+"alias");
		String fileId = client.uploadContents(android_appkey,android_appMasterSecret,alias);
		customizedcast.setFileId(fileId, ALIAS_TYPE);
		customizedcast.setTicker( "Android customizedcastfile ticker");
		customizedcast.setTitle(  "customizedcastfile测试title");
		customizedcast.setText(   "customizedcastfile测试内容");
		customizedcast.setUrl("https://www.baidu.com");
		//customizedcast.goAppAfterOpen();
		customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		// TODO Set 'production_mode' to 'false' if it's a test device. 
		// For how to register a test device, please see the developer doc.
		customizedcast.setProductionMode();
		client.send(customizedcast);
	}
	
	public static void sendAndroidFilecast() throws Exception {
//		AndroidFilecast filecast = new AndroidFilecast(appkey,appMasterSecret);
		AndroidFilecast filecast = new AndroidFilecast();
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(android_appkey,android_appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setTicker( "Android filecast ticker");
		filecast.setTitle(  "中文的title");
		filecast.setText(   "Android filecast text");
		filecast.goAppAfterOpen();
		filecast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
		client.send(filecast);
	}
	
	public static void sendIOSBroadcast() throws Exception {
//		IOSBroadcast broadcast = new IOSBroadcast(appkey,appMasterSecret);
		IOSBroadcast broadcast = new IOSBroadcast();

		broadcast.setAlert("IOS 广播测试");
		broadcast.setBadge( 0);
		broadcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		broadcast.setTestMode();
		// Set customized fields
		broadcast.setCustomizedField("test", "helloworld");
		client.send(broadcast);
	}
	
	public static void sendIOSUnicast() throws Exception {
//		IOSUnicast unicast = new IOSUnicast(appkey,appMasterSecret);
		IOSUnicast unicast = new IOSUnicast();
		// TODO Set your device token
		unicast.setDeviceToken( "xx");
		unicast.setAlert("IOS 单播测试");
		unicast.setBadge( 0);
		unicast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		unicast.setTestMode();
		// Set customized fields
		unicast.setCustomizedField("test", "helloworld");
		client.send(unicast);
	}
	
	public static void sendIOSGroupcast() throws Exception {
//		IOSGroupcast groupcast = new IOSGroupcast(appkey,appMasterSecret);
		IOSGroupcast groupcast = new IOSGroupcast();
		/*  TODO
		 *  Construct the filter condition:
		 *  "where": 
		 *	{
    	 *		"and": 
    	 *		[
      	 *			{"tag":"iostest"}
    	 *		]
		 *	}
		 */
		JSONObject filterJson = new JSONObject();
		JSONObject whereJson = new JSONObject();
		JSONArray tagArray = new JSONArray();
		JSONObject testTag = new JSONObject();
		testTag.put("tag", "iostest");
		tagArray.put(testTag);
		whereJson.put("and", tagArray);
		filterJson.put("where", whereJson);
		System.out.println(filterJson.toString());
		
		// Set filter condition into rootJson
		groupcast.setFilter(filterJson);
		groupcast.setAlert("IOS 组播测试");
		groupcast.setBadge( 0);
		groupcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		groupcast.setTestMode();
		client.send(groupcast);
	}
	
	public static void sendIOSCustomizedcast() throws Exception {
//		IOSCustomizedcast customizedcast = new IOSCustomizedcast(appkey,appMasterSecret);
		IOSCustomizedcast customizedcast = new IOSCustomizedcast();
		// TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
		// And if you have many alias, you can also upload a file containing these alias, then 
		// use file_id to send customized notification.
		customizedcast.setAlias("alias", "alias_type");
		customizedcast.setAlert("IOS 个性化测试");
		customizedcast.setBadge( 0);
		customizedcast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		customizedcast.setTestMode();
		client.send(customizedcast);
	}
	
	public static void sendIOSFilecast() throws Exception {
//		IOSFilecast filecast = new IOSFilecast(appkey,appMasterSecret);
		IOSFilecast filecast = new IOSFilecast();
		// TODO upload your device tokens, and use '\n' to split them if there are multiple tokens 
		String fileId = client.uploadContents(ios_appkey,ios_appMasterSecret,"aa"+"\n"+"bb");
		filecast.setFileId( fileId);
		filecast.setAlert("IOS 文件播测试");
		filecast.setBadge( 0);
		filecast.setSound( "default");
		// TODO set 'production_mode' to 'true' if your app is under production mode
		filecast.setTestMode();
		client.send(filecast);
	}
	
	public static void main(String[] args) {
		try {
			//PushUtil.sendAndroidUnicast();
			//PushUtil.sendAndroidCustomizedcast();
//			PushUtil.sendAndroidCustomizedcastFile();
			//PushUtil.sendAndroidBroadcast();
			
			/*AndroidCustomizedcast customizedcast = new AndroidCustomizedcast();
			// TODO Set your alias here, and use comma to split them if there are multiple alias.
			// And if you have many alias, you can also upload a file containing these alias, then 
			// use file_id to send customized notification.
			customizedcast.setAlias("8063", ALIAS_TYPE);
			customizedcast.setTicker( "Android customizedcast ticker");
			customizedcast.setTitle(  "测试title");
			customizedcast.setText(   "亲，这是一个测试");
			customizedcast.goAppAfterOpen();
			customizedcast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			// TODO Set 'production_mode' to 'false' if it's a test device. 
			// For how to register a test device, please see the developer doc.
			//customizedcast.setProductionMode();
			customizedcast.setTestMode();*/
			
			/*AndroidUnicast unicast = new AndroidUnicast();
			// TODO Set your device token
			unicast.setDeviceToken("Av0XBAgxEJX8vowb3-is_XuEAL2QMhPodviKUMZqvOFh");
			unicast.setTicker("Android unicast ticker");
			unicast.setTitle("这是一个测试");
			unicast.setText("hello，你好！");
			unicast.goAppAfterOpen();
			unicast.setDisplayType(AndroidNotification.DisplayType.NOTIFICATION);
			unicast.setTestMode();
			// Set customized fields
			unicast.setExtraField("test", "helloworld");*/
			
			/*IOSUnicast unicast = new IOSUnicast();
//			IOSBroadcast broadcast = new IOSBroadcast();
			unicast.setDescription("ios广播测试");
			unicast.setDeviceToken("996e20d122618ecdd7ada11f1619f75373722f170e3250c469f63491cd8e6493");
			unicast.setAlert("IOS广播测试");
			unicast.setBadge(1);
			unicast.setSound("default");
			unicast.setTestMode();
			unicast.setCustomizedField("url", "https://www.baidu.com");*/
			
			/*IOSCustomizedcast customizedcast = new IOSCustomizedcast();
			// TODO Set your alias and alias_type here, and use comma to split them if there are multiple alias.
			// And if you have many alias, you can also upload a file containing these alias, then 
			// use file_id to send customized notification.
			customizedcast.setAlias("8245", "WEIXIN");
			customizedcast.setAlert("IOS 个性化测试");
			customizedcast.setBadge(1);
			customizedcast.setSound( "default");
			// TODO set 'production_mode' to 'true' if your app is under production mode
			customizedcast.setTestMode();
			
			PushUtil.pushMsg(customizedcast,"ios");*/
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	

}
