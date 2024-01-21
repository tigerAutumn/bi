package com.pinting.business.service.site;

import java.util.List;

import com.pinting.business.hessian.site.message.ReqMsg_WxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.hessian.site.message.ResMsg_WxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.model.BsWxAutoReply;

public interface WxAutoReplyService {
	/**
	 * 根据消息用户消息内容匹配需要返回的消息内容
	 * @param req
	 * @param res
	 */
	void selectAutoReplyMessage(ReqMsg_WxAutoReply_SelectAutoReplyMessage req,ResMsg_WxAutoReply_SelectAutoReplyMessage res);
	
	/**
	 * 获取关注是回复消息
	 * @return
	 */
	List<BsWxAutoReply> getSubscribeReplyList ();
	
}
