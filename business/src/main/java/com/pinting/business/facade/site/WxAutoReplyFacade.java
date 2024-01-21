package com.pinting.business.facade.site;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_WxAutoReply_GetSubscribeReply;
import com.pinting.business.hessian.site.message.ReqMsg_WxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.hessian.site.message.ResMsg_WxAutoReply_GetSubscribeReply;
import com.pinting.business.hessian.site.message.ResMsg_WxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.service.site.WxAutoReplyService;
import com.pinting.business.util.BeanUtils;
/**
 * 微信自动消息回复
 * @Project: business
 * @Title: WxAutoReplyFacade.java
 * @author dargon & cat
 * @date 2016-06-30 上午09:57:39
 */
@Component("WxAutoReply")
public class WxAutoReplyFacade {
	@Autowired
	private WxAutoReplyService wxAutoReplyService;
	
    /**
     * 查询自动回复消息
     * @param req
     * @param res
     */
    public void selectAutoReplyMessage(ReqMsg_WxAutoReply_SelectAutoReplyMessage req, ResMsg_WxAutoReply_SelectAutoReplyMessage res) {
    	wxAutoReplyService.selectAutoReplyMessage(req, res);
    }
    
    
    public void getSubscribeReply(ReqMsg_WxAutoReply_GetSubscribeReply req, ResMsg_WxAutoReply_GetSubscribeReply res){
    	List<BsWxAutoReply> list = wxAutoReplyService.getSubscribeReplyList();
    	res.setDataGrid(BeanUtils.classToArrayList(list));
    }

}
