package com.pinting.business.facade.manage;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_OperationalWxAutoReply_GetSubscribeReply;
import com.pinting.business.hessian.manage.message.ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.hessian.manage.message.ResMsg_OperationalWxAutoReply_GetSubscribeReply;
import com.pinting.business.hessian.manage.message.ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.service.manage.OperationalWxAutoReplyService;
import com.pinting.business.util.BeanUtils;
/**
 * 微信自动消息回复
 * @Project: business
 * @Title: WxAutoReplyFacade.java
 * @author dargon & cat
 * @date 2016-06-30 上午09:57:39
 */
@Component("OperationalWxAutoReply")
public class OperationalWxAutoReplyFacade {
	@Autowired
	private OperationalWxAutoReplyService wxAutoReplyService;
	
    /**
     * 查询自动回复消息
     * @param req
     * @param res
     */
    public void selectAutoReplyMessage(ReqMsg_OperationalWxAutoReply_SelectAutoReplyMessage req, ResMsg_OperationalWxAutoReply_SelectAutoReplyMessage res) {
    	wxAutoReplyService.selectAutoReplyMessage(req, res);
    }
    
    
    public void getSubscribeReply(ReqMsg_OperationalWxAutoReply_GetSubscribeReply req, ResMsg_OperationalWxAutoReply_GetSubscribeReply res){
    	List<BsWxAutoReply> list = wxAutoReplyService.getSubscribeReplyList();
    	res.setDataGrid(BeanUtils.classToArrayList(list));
    }

}
