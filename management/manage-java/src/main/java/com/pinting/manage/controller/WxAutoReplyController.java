package com.pinting.manage.controller;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_AddOrUpdateReply;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_DeleteReply;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_GetReplyById;
import com.pinting.business.hessian.manage.message.ReqMsg_MWxAutoReply_GetReplyList;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_AddOrUpdateReply;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_DeleteReply;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_GetReplyById;
import com.pinting.business.hessian.manage.message.ResMsg_MWxAutoReply_GetReplyList;
import com.pinting.business.model.BsWxAutoReply;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.manage.enums.EditorEum;
import com.pinting.manage.enums.EmojiEnum;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;
/**
 * 
 * @author bianyatian
 * @2016-3-30 上午10:53:11
 */
@Controller
public class WxAutoReplyController {

	@Autowired
	@Qualifier("dispatchService")
	private HessianService replyService;
	
	@RequestMapping("/wx/autoReply/index")
	public String userRegistView(HttpServletRequest request,ReqMsg_MWxAutoReply_GetReplyList req,
			HttpServletResponse response, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MWxAutoReply_GetReplyList res = (ResMsg_MWxAutoReply_GetReplyList) replyService.handleMsg(req);
		
		model.put("replyList", res.getReplyList());
		model.put("totalRows", res.getTotalRows());
		model.put("req", req);
		return  "/wxAutoReply/index";
	}
	
	@RequestMapping("/wx/autoReply/detail")
	public String detail(HttpServletRequest request,HttpServletResponse response,
			Map<String, Object> model, String type, String id){
		if(!"addNew".equals(type)){
			ReqMsg_MWxAutoReply_GetReplyById req = new ReqMsg_MWxAutoReply_GetReplyById();
			req.setId(Integer.parseInt(id));
			ResMsg_MWxAutoReply_GetReplyById res = (ResMsg_MWxAutoReply_GetReplyById)replyService.handleMsg(req);
			BsWxAutoReply wxAutoReply = res.getWxAutoReply();
			String key = wxAutoReply.getKeywords();
			if(StringUtils.isNotBlank(key)){
				for (EmojiEnum emoj : EnumSet.allOf(EmojiEnum.class)) {
					key = key.replace(")", "括号start").replace("(", "括号end")
							.replace(emoj.getDescription().replace(")", "括号start").replace("(", "括号end"),emoj.getCode());
				}
			}
			
			wxAutoReply.setKeywords(key);
			if("add".equals(type)){
				wxAutoReply.setContent("");
				wxAutoReply.setTitle("");
				wxAutoReply.setUrl("");
				wxAutoReply.setPicUrl("");
				
			}else{
				String con = wxAutoReply.getContent();
				if(StringUtils.isNotBlank(con)){
					for (EmojiEnum emoj : EnumSet.allOf(EmojiEnum.class)) {
						con = con.replace(")", "括号start").replace("(", "括号end")
								.replace(emoj.getDescription().replace(")", "括号start").replace("(", "括号end"),emoj.getCode());
					}
				}
				wxAutoReply.setContent(con);
			}
			model.put("autoReply", wxAutoReply);
		}
		model.put("type", type);
		return  "/wxAutoReply/detail";
	}
	
	
	@RequestMapping("/wx/autoReply/addOrUpdate")
	public @ResponseBody Map<Object,Object> addOrUpdate(ReqMsg_MWxAutoReply_AddOrUpdateReply req,
			HttpServletRequest request, HttpServletResponse response,HashMap<String,Object> model,
			String type){
		try {
			if("KEY_REPLY".equals(req.getReplyType())){
				String key = req.getKeywords();
				for (EditorEum editor : EnumSet.allOf(EditorEum.class)) {
					key = key.replace(editor.getCode(), editor.getDescription());
				}
				for (EmojiEnum emoj : EnumSet.allOf(EmojiEnum.class)) {
					key = key.replace(emoj.getCode(), emoj.getDescription());
				}
				
				req.setKeywords(key);
			}
			
			String s = req.getContent();
			for (EditorEum editor : EnumSet.allOf(EditorEum.class)) {
				s = s.replace(editor.getCode(), editor.getDescription());
			}
			if("news".equals(req.getMsgType()) ){
				for (EmojiEnum emoj : EnumSet.allOf(EmojiEnum.class)) {
					s = s.replace(emoj.getCode(), "");
				}	
			}else{
				for (EmojiEnum emoj : EnumSet.allOf(EmojiEnum.class)) {
					s = s.replace(emoj.getCode(), emoj.getDescription());
				}
			}
			req.setContent(s);
			ResMsg_MWxAutoReply_AddOrUpdateReply res = (ResMsg_MWxAutoReply_AddOrUpdateReply)replyService.handleMsg(req);
			
			if(StringUtils.isNotBlank(res.getErrorMsg())){
				return ReturnDWZAjax.fail(res.getErrorMsg());
			}else{
				if("update".equals(req.getType())){
					return ReturnDWZAjax.success("修改，成功！");
				}else{
					return ReturnDWZAjax.success("添加，成功！");
				}
				
			}
			
		} catch (Exception e) {
			if("update".equals(req.getType())){
				return ReturnDWZAjax.fail("修改，原因不详");
			}else{
				return ReturnDWZAjax.fail("添加，原因不详");
			}
			
		}
	}
	
	
	@RequestMapping("/wx/autoReply/delete")
	public @ResponseBody Map<Object,Object> delete(HttpServletRequest request, 
			HttpServletResponse response,HashMap<String,Object> model,String id){
		try {
			ReqMsg_MWxAutoReply_DeleteReply req = new ReqMsg_MWxAutoReply_DeleteReply();
			if(StringUtils.isBlank(id)){
				return ReturnDWZAjax.fail("删除失败");
			}else{
				req.setId(Integer.parseInt(id));
			}
			ResMsg_MWxAutoReply_DeleteReply res = (ResMsg_MWxAutoReply_DeleteReply)replyService.handleMsg(req);
			
			return ReturnDWZAjax.success("删除成功！");
			
		} catch (Exception e) {
			return ReturnDWZAjax.fail("删除失败，原因不详");
		}
	}
}
