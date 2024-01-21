package com.pinting.business.hessian.manage.message;


import java.util.List;

import com.pinting.core.hessian.msg.ReqMsg;

public class ReqMsg_PushUtil_SendCustomizedcast extends ReqMsg {
	/**
	 * 序列化编号
	 */
	private static final long serialVersionUID = 2349153500472492640L;
	
	private String ticker; // 必填 通知栏提示文字
	private String title; // 必填 通知标题
	private String text; // 必填 通知文字描述
	private String displayType = "notification"; // 必填 消息类型，值可以为:notification-通知，message-消息
	/**
	 * 自定义通知图标:
	 * 可选 状态栏图标ID, R.drawable.[smallIcon],
	 * 如果没有, 默认使用应用图标。 图片要求为24*24dp的图标,或24*24px放在drawable-mdpi下。注意四周各留1个dp的空白像素
	 */
	private String icon;
    
	/**
	 * 可选 通知栏拉开后左侧图标ID, R.drawable.[largeIcon].
	 * 图片要求为64*64dp的图标,可设计一张64*64px放在drawable-mdpi下,注意图片四周留空，不至于显示太拥挤
	 */
    private String largeIcon;
    
    /**
     * 可选 通知栏大图标的URL链接。该字段的优先级大于largeIcon。
     * 该字段要求以http或者https开头。
     */
    private String img;
    
    /**
     * 自定义通知声音:
     * 可选 通知声音，R.raw.[sound]. 
     * 如果该字段为空，采用SDK默认的声音, 即res/raw/下的umeng_push_notification_default_sound声音文件
     * 如果SDK默认声音文件不存在，则使用系统默认的Notification提示音。
     */
    private String sound;
    
    /**
     * 自定义通知样式:
     * 可选 默认为0，用于标识该通知采用的样式。使用该参数时, 开发者必须在SDK里面实现自定义通知栏样式。
     */
    private Integer builderId;
    
    /**
     * 通知到达设备后的提醒方式
     * 可选 收到通知是否震动,默认为true
     */
    private boolean playVibrate;
    
    /**
     * 通知到达设备后的提醒方式
     * 可选 收到通知是否闪灯,,默认为true
     */
    private boolean playLights;
    
    /**
     * 通知到达设备后的提醒方式
     * 可选 收到通知是否发出声音,默认为true
     */
    private boolean playSound;
    
    /**
     * 可选 当"after_open"为"go_url"时，必填。通知栏点击后跳转的URL，要求以http或者https开头
     */
    private String url;
    
    /**
     * 可选 当"after_open"为"go_activity"时，必填。通知栏点击后打开的Activity
     */
    private String activity;
    
    /**
     * 可选 display_type=message, 或者display_type=notification且"after_open"为"go_custom"时，
     * 该字段必填。用户自定义内容, 可以为字符串或者JSON格式。
     */
    private String custom;
    
    /**
     * 可选 定时发送时间，若不填写表示立即发送。定时发送时间不能小于当前时间
     * 格式: "YYYY-MM-DD HH:mm:ss"。 注意, start_time只对任务生效。
     */
    private String startTime;
    
    /**
     * 可选 消息过期时间,其值不可小于发送时间或者start_time(如果填写了的话),
     * 如果不填写此参数，默认为3天后过期。格式同start_time("YYYY-MM-DD HH:mm:ss")
     */
    private String expireTime;
    
    /**
     * 可选 发送限速，每秒发送的最大条数。
     * 开发者发送的消息如果有请求自己服务器的资源，可以考虑此参数。
     */
    private Integer maxSendNum;
    
    /**
     * 可选 正式/测试模式。测试模式下，只会将消息发给测试设备。
     * 测试设备需要到web上添加。Android: 测试设备属于正式设备的一个子集
     */
    private boolean productionMode;
    
    /**
     * 可选 发送消息描述，建议填写。
     */
    private String description;
    
    /**
     * 可选 开发者自定义消息标识ID, 开发者可以为同一批发送的多条消息
     * 提供同一个thirdparty_id, 便于友盟后台后期合并统计数据。
     */
    private String thirdpartyId;
    
    /**
     * 所有推送的用户Id
     */
    private List<Integer> userIdList;
    
    /**
     * 可选 当type=customizedcast时，必填，alias的类型, 
     * alias_type可由开发者自定义,开发者在SDK中调用setAlias(alias, alias_type)时所设置的alias_type
     */
    private String androidAliasType = "CoinHarbour";
    
    /**
     * 可选 当type=customizedcast时，必填，alias的类型, 
     * alias_type可由开发者自定义,开发者在SDK中调用setAlias(alias, alias_type)时所设置的alias_type
     */
    private String iosAliasType = "WEIXIN";
    
    private Integer badge;// 可选
    
    private String alert;// 必填
    
    private Integer contentAvailable;//可选
    
    private  String android_appkey;
	private  String android_appMasterSecret;
	private  String ios_appkey;
	private  String ios_appMasterSecret;

	public String getTicker() {
		return ticker;
	}

	public void setTicker(String ticker) {
		this.ticker = ticker;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getDisplayType() {
		return displayType;
	}

	public void setDisplayType(String displayType) {
		this.displayType = displayType;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public String getLargeIcon() {
		return largeIcon;
	}

	public void setLargeIcon(String largeIcon) {
		this.largeIcon = largeIcon;
	}

	public String getImg() {
		return img;
	}

	public void setImg(String img) {
		this.img = img;
	}

	public String getSound() {
		return sound;
	}

	public void setSound(String sound) {
		this.sound = sound;
	}

	public Integer getBuilderId() {
		return builderId;
	}

	public void setBuilderId(Integer builderId) {
		this.builderId = builderId;
	}

	public boolean isPlayVibrate() {
		return playVibrate;
	}

	public void setPlayVibrate(boolean playVibrate) {
		this.playVibrate = playVibrate;
	}

	public boolean isPlayLights() {
		return playLights;
	}

	public void setPlayLights(boolean playLights) {
		this.playLights = playLights;
	}

	public boolean isPlaySound() {
		return playSound;
	}

	public void setPlaySound(boolean playSound) {
		this.playSound = playSound;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getActivity() {
		return activity;
	}

	public void setActivity(String activity) {
		this.activity = activity;
	}

	public String getCustom() {
		return custom;
	}

	public void setCustom(String custom) {
		this.custom = custom;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getExpireTime() {
		return expireTime;
	}

	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

	public Integer getMaxSendNum() {
		return maxSendNum;
	}

	public void setMaxSendNum(Integer maxSendNum) {
		this.maxSendNum = maxSendNum;
	}

	public boolean isProductionMode() {
		return productionMode;
	}

	public void setProductionMode(boolean productionMode) {
		this.productionMode = productionMode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getThirdpartyId() {
		return thirdpartyId;
	}

	public void setThirdpartyId(String thirdpartyId) {
		this.thirdpartyId = thirdpartyId;
	}

	public List<Integer> getUserIdList() {
		return userIdList;
	}

	public void setUserIdList(List<Integer> userIdList) {
		this.userIdList = userIdList;
	}

	public String getAndroidAliasType() {
		return androidAliasType;
	}

	public void setAndroidAliasType(String androidAliasType) {
		this.androidAliasType = androidAliasType;
	}

	public String getIosAliasType() {
		return iosAliasType;
	}

	public void setIosAliasType(String iosAliasType) {
		this.iosAliasType = iosAliasType;
	}

	public Integer getBadge() {
		return badge;
	}

	public void setBadge(Integer badge) {
		this.badge = badge;
	}

	public String getAlert() {
		return alert;
	}

	public void setAlert(String alert) {
		this.alert = alert;
	}

	public Integer getContentAvailable() {
		return contentAvailable;
	}

	public void setContentAvailable(Integer contentAvailable) {
		this.contentAvailable = contentAvailable;
	}

	public String getAndroid_appkey() {
		return android_appkey;
	}

	public void setAndroid_appkey(String android_appkey) {
		this.android_appkey = android_appkey;
	}

	public String getAndroid_appMasterSecret() {
		return android_appMasterSecret;
	}

	public void setAndroid_appMasterSecret(String android_appMasterSecret) {
		this.android_appMasterSecret = android_appMasterSecret;
	}

	public String getIos_appkey() {
		return ios_appkey;
	}

	public void setIos_appkey(String ios_appkey) {
		this.ios_appkey = ios_appkey;
	}

	public String getIos_appMasterSecret() {
		return ios_appMasterSecret;
	}

	public void setIos_appMasterSecret(String ios_appMasterSecret) {
		this.ios_appMasterSecret = ios_appMasterSecret;
	}
}
