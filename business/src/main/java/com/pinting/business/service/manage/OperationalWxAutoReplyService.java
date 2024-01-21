package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.hessian.manage.message.ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.hessian.manage.message.ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.model.BsWxAutoReply;

/**
 *
 * @author SHENGUOPING
 * @date  2018年6月22日 下午12:36:47
 */
public interface OperationalWxAutoReplyService {

	/**
	 * 根据消息用户消息内容匹配需要返回的消息内容
	 * @param req
	 * @param res
	 */
	void selectAutoReplyMessage(ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage req,ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage res);
	
	/**
	 * 获取关注是回复消息
	 * @return
	 */
	List<BsWxAutoReply> getSubscribeReplyList ();
	
}
