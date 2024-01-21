package com.pinting.open.base.request;

public abstract class Request {

	private Integer appClient;   //客户端来源(3-Android端;4-iOS端)
	private String  version;     //接口版本号
	private String  signOpenApi;
	private String  timeOpenApi;
	private String  appVersion;  //App版本号

	public abstract String restApiUrl();

	public abstract String testApiUrl();

	public Integer getAppClient() {
		return appClient;
	}

	public void setAppClient(Integer appClient) {
		this.appClient = appClient;
	}

	public String getSignOpenApi() {
		return this.signOpenApi;
	}

	public void setSignOpenApi(String signOpenApi) {
		this.signOpenApi = signOpenApi;
	}

	public String getTimeOpenApi() {
		return this.timeOpenApi;
	}

	public void setTimeOpenApi(String timeOpenApi) {
		this.timeOpenApi = timeOpenApi;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public String getAppVersion() {
		return appVersion;
	}

	public void setAppVersion(String appVersion) {
		this.appVersion = appVersion;
	}

}
