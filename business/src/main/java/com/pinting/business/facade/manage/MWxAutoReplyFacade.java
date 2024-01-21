package com.pinting.business.facade.manage;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_AddOrUpdateReply;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_DeleteReply;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_GetReplyById;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_GetReplyList;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_AddOrUpdateReply;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_DeleteReply;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_GetReplyById;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_GetReplyList;
import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.model.vo.BsWxAutoReplyVO;
import com.pinting.business.service.manage.BsWxAutoReplyService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;

/**
 * 
 * @author bianyatian
 * @2016-3-30 上午10:47:47
 */
@Component("MWxAutoReply")
public class MWxAutoReplyFacade {
	@Autowired
	private BsWxAutoReplyService bsWxAutoReplyService;
	
	public void getReplyList(ReqMsg_MWxAutoReply_GetReplyList req, ResMsg_MWxAutoReply_GetReplyList res){
		BsWxAutoReplyVO bsWxAutoReplyVO = new BsWxAutoReplyVO();
		bsWxAutoReplyVO.setPageNum(req.getPageNum());
		bsWxAutoReplyVO.setNumPerPage(req.getNumPerPage());
		if(req.getStartTime() != null) {
			bsWxAutoReplyVO.setStartTime(req.getStartTime());
		}
		if(req.getEndTime() != null) {
			bsWxAutoReplyVO.setEndTime(req.getEndTime());
		}
		if(req.getsName() != null) {
			bsWxAutoReplyVO.setName(req.getsName());
		}
		if(req.getsKeywords() != null) {
			bsWxAutoReplyVO.setKeywords(req.getsKeywords());
		}
		if(req.getsTitle() != null) {
			bsWxAutoReplyVO.setTitle(req.getsTitle());
		}
		if(req.getsContent() != null) {
			bsWxAutoReplyVO.setContent(req.getsContent());
		}
		if(req.getsReplyType() != null) {
			bsWxAutoReplyVO.setReplyType(req.getsReplyType());
		}
		if(req.getsMsgType() != null) {
			bsWxAutoReplyVO.setMsgType(req.getsMsgType());
		}
		int totalRows = bsWxAutoReplyService.getCountByReplyVO(bsWxAutoReplyVO);
		if(totalRows > 0){
			List<BsWxAutoReply> list = bsWxAutoReplyService.getListByReplyVO(bsWxAutoReplyVO);
			res.setReplyList(BeanUtils.classToArrayList(list));
		}
		res.setTotalRows(totalRows);
		
	}
	
	public void addOrUpdateReply(ReqMsg_MWxAutoReply_AddOrUpdateReply req, ResMsg_MWxAutoReply_AddOrUpdateReply res ){
		BsWxAutoReply reply = new BsWxAutoReply();
		if(StringUtils.isBlank(req.getName())){
			res.setErrorMsg("消息名称不能为空！");
			return;
		}
		if(StringUtils.isBlank(req.getReplyType())){
			res.setErrorMsg("回复类型不能为空！");
			return;
		}
		if(StringUtils.isBlank(req.getMsgType())){
			res.setErrorMsg("消息类型不能为空！");
			return;
		}
		reply.setName(req.getName());
		reply.setReplyType(req.getReplyType());
		if(Constants.WX_AUTO_REPLY_TYPE_KEY.equals(req.getReplyType())){
			if(StringUtils.isBlank(req.getKeywords())){
				res.setErrorMsg("关键词不能为空！");
				return;
			}else{
				String key = req.getKeywords().replaceAll("，", ",").replaceAll(",", "");
				if(key.length() > 30){
					res.setErrorMsg("关键词除去逗号后，字数需在30字以内，表情一般算5个字符长度！");
    				return;
				}
			}
			reply.setKeywords(req.getKeywords());
		}
		reply.setMsgType(req.getMsgType());
		if(Constants.WX_Msg_Type_news.equals(req.getMsgType())){
			if(StringUtils.isBlank(req.getTitle())){
				res.setErrorMsg("图文消息标题不能为空！");
				return;
			}
			if(req.getContent().length() > 300){
				res.setErrorMsg("文本内容字数需在300字以内，表情一般算5个字符长度！");
				return;
			}
			reply.setTitle(req.getTitle());
			reply.setPicUrl(req.getPicUrl());
			reply.setUrl(req.getUrl());
			reply.setContent(req.getContent());
		}else{
			if(StringUtils.isBlank(req.getContent())){
				res.setErrorMsg("文本内容不能为空！");
				return;
			}else{
				if(req.getContent().length() > 600){
					res.setErrorMsg("文本内容字数需在600字以内，表情一般算5个字符长度！");
    				return;
				}
			}
			reply.setContent(req.getContent());
		}
		
		if("update".equals(req.getType())){
			reply.setId(req.getId());
			bsWxAutoReplyService.updateReply(reply);
		}else{
			bsWxAutoReplyService.addWxAutoReply(reply);
		}
		
	}
	
	public void getReplyById(ReqMsg_MWxAutoReply_GetReplyById req, ResMsg_MWxAutoReply_GetReplyById res){
		BsWxAutoReply wxAutoReply =bsWxAutoReplyService.getById(req.getId());
		res.setWxAutoReply(wxAutoReply);
	}
	
	public void deleteReply(ReqMsg_MWxAutoReply_DeleteReply req, ResMsg_MWxAutoReply_DeleteReply res){
		bsWxAutoReplyService.deleteReply(req.getId());
	}
}
