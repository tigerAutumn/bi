package com.pinting.business.service.site;

import java.util.List;

public interface AppNoticeService {
	
	/**
	 * 发送app消息
	 * @author bianyatian
	 * @param title
	 * @param content
	 * @param userIds
	 */
	void sendTicketMessage(String title, String content, List<Integer> userIds);

}
