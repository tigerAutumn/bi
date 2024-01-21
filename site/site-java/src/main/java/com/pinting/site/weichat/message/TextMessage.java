package com.pinting.site.weichat.message;


/**
 * 文本消息
 * @Project: site-java
 * @Title: TextMessage.java
 * @author Huang MengJian
 * @date 2015-3-24 下午2:17:11
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public class TextMessage extends BaseMessage {

	// 消息内容
	private String Content;

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}
}
