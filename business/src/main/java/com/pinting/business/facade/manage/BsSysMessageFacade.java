package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_BsErrorCodeDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_BsErrorCodeModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_ErrorCodeList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsErrorCode_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_BsSysMessageDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_BsSysMessageModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_MessageList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysMessage_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_BsErrorCodeModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_ErrorCodeList;
import com.pinting.business.hessian.manage.message.ResMsg_BsErrorCode_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_BsSysMessageDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_BsSysMessageModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_MessageList;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysMessage_SelectByPrimaryKey;
import com.pinting.business.model.BsErrorCode;
import com.pinting.business.model.BsSysMessage;
import com.pinting.business.model.vo.BsErrorCodeVO;
import com.pinting.business.service.manage.BsErrorCodeService;
import com.pinting.business.service.manage.BsSysMessageService;
import com.pinting.business.util.BeanUtils;


@Component("BsSysMessage")
@Deprecated
public class BsSysMessageFacade {
	@Autowired
	private BsSysMessageService sysMessageService;
	
	/**
	 * 错误码信息列表
	 * @param reqMsg
	 * @param resMsg
	 */
	public void messageList(ReqMsg_BsSysMessage_MessageList reqMsg,ResMsg_BsSysMessage_MessageList resMsg) {
		int totalRows = sysMessageService.findCountSysMessage(reqMsg.getId(), reqMsg.getTitle(), reqMsg.getContent(), reqMsg.getReceiverType(), reqMsg.getStatus(), reqMsg.getUpdateTime(), reqMsg.getCreateTime());
		if (totalRows > 0) {
			ArrayList<BsSysMessage> bsSysMessageList = sysMessageService.findSysMessageList(reqMsg.getId(), reqMsg.getTitle(), reqMsg.getContent(), reqMsg.getReceiverType(), reqMsg.getStatus(), reqMsg.getUpdateTime(), reqMsg.getCreateTime(), reqMsg.getPageNum(),
					reqMsg.getNumPerPage(), reqMsg.getOrderDirection(), reqMsg.getOrderField());
			ArrayList<HashMap<String, Object>> sysMessageList = BeanUtils.classToArrayList(bsSysMessageList);
			resMsg.setValueList(sysMessageList);
		}
		resMsg.setTotalRows(totalRows);
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
	}
	
	
	/**
	 * 进入添加/编辑页面
	 * @param req
	 * @param res
	 */
	public void selectByPrimaryKey(ReqMsg_BsSysMessage_SelectByPrimaryKey req, ResMsg_BsSysMessage_SelectByPrimaryKey res) {
		if(req.getId() != null && req.getId() != 0) {
			BsSysMessage bsSysMessage = sysMessageService.sysMessagePrimaryKey(req.getId());
			res.setId(bsSysMessage.getId());
			res.setTitle(bsSysMessage.getTitle());
			res.setContent(bsSysMessage.getContent());
			res.setReceiverType(bsSysMessage.getReceiverType());
			res.setStatus(bsSysMessage.getStatus());
			res.setCreateTime(bsSysMessage.getCreateTime());
			res.setUpdateTime(bsSysMessage.getUpdateTime());
		}
	}
	
	/**
	 * 添加/编辑
	 * @param req
	 * @param res
	 */
	public void bsSysMessageModify(ReqMsg_BsSysMessage_BsSysMessageModify req, ResMsg_BsSysMessage_BsSysMessageModify res) {
		BsSysMessage bsSysMessage  = new BsSysMessage();
		bsSysMessage.setId(req.getId());
		bsSysMessage.setTitle(req.getTitle());
		bsSysMessage.setContent(req.getContent());
		bsSysMessage.setReceiverType(req.getReceiverType());
		bsSysMessage.setStatus(req.getStatus());
		if (req.getId() != null ) {
			bsSysMessage.setUpdateTime(new Date());
			sysMessageService.updateSysMessage(bsSysMessage);
		}else {
			bsSysMessage.setCreateTime(new Date());
			bsSysMessage.setUpdateTime(new Date());
			sysMessageService.addSysMessage(bsSysMessage);
		}
	}
	
	/**
	 * 删除
	 * @param req
	 * @param res
	 */
	public void bsSysMessageDelete(ReqMsg_BsSysMessage_BsSysMessageDelete req, ResMsg_BsSysMessage_BsSysMessageDelete res) {
		if(req.getId() != null && req.getId() != 0) {
			sysMessageService.deleteSysMessageById(req.getId());
		}
	}
}
