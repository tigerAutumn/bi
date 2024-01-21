package com.pinting.business.util;

import java.util.Map;

public class WxTemplate {

	private String template_id; //消息模板id
	
	private String touser; //用户open_id

	private String url; //点击消息跳转的url地址
	
	private String topcolor = "#FF0000"; //消息标题的颜色
	
	private Map<String,WxTemplateData> data; //模板数据 key:模板中的变量名称;value:模板中变量的值和颜色

	public WxTemplate(String template_id, String touser, String url, String topcolor, Map<String, WxTemplateData> data) {
		this.template_id = template_id;
		this.touser = touser;
		this.url = url;
		this.topcolor = topcolor;
		this.data = data;
	}
	
	public WxTemplate() {}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, WxTemplateData> getData() {
		return data;
	}

	public void setData(Map<String, WxTemplateData> data) {
		this.data = data;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTopcolor() {
		return topcolor;
	}

	public void setTopcolor(String topcolor) {
		this.topcolor = topcolor;
	}
	
}
