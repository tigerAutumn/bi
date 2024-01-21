package com.pinting.open.pojo.model.asset;

public class Message {

	private Integer id;
	
	private String title; //标题
	
	private String pushTime; //推送时间
	
	private Integer pushType; //推送类型 1、url;2、文本;3、app内页
	
	private Integer appPage; //app内页编码
	
	private String url; //url地址
	
	private String pushAbstract; //摘要

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getPushTime() {
		return pushTime;
	}

	public void setPushTime(String pushTime) {
		this.pushTime = pushTime;
	}

	public Integer getPushType() {
		return pushType;
	}

	public void setPushType(Integer pushType) {
		this.pushType = pushType;
	}

	public Integer getAppPage() {
		return appPage;
	}

	public void setAppPage(Integer appPage) {
		this.appPage = appPage;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPushAbstract() {
		return pushAbstract;
	}

	public void setPushAbstract(String pushAbstract) {
		this.pushAbstract = pushAbstract;
	}
}
