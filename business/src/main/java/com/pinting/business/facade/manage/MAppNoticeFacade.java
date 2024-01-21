package com.pinting.business.facade.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeAdd;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeDetail;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeAdd;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeDelete;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeDetail;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeUpdate;
import com.pinting.business.model.BsAppMessage;
import com.pinting.business.service.manage.MAppNoticeService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.ConstantUtil;

/**
 * 
 * @Project: business
 * @Title: MAppNoticeFacade.java
 * @author yanwl
 * @date 2016-02-23 下午13:34:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MAppNotice")
public class MAppNoticeFacade{
	@Autowired
	private MAppNoticeService mAppNoticeService;
	
	public void appNoticeListQuery(ReqMsg_MAppNotice_AppNoticeListQuery reqMsg,ResMsg_MAppNotice_AppNoticeListQuery resMsg) {
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		int totalRows =  mAppNoticeService.findAllAppNoticeCount(reqMsg.getName(), reqMsg.getTitle(), reqMsg.getReleasePart(), reqMsg.getIsSend());
		if(totalRows > 0) {
			List<BsAppMessage> notices = mAppNoticeService.findAppMessage(pageNum, numPerPage, reqMsg.getName(), reqMsg.getTitle(), reqMsg.getReleasePart(), reqMsg.getIsSend());
			resMsg.setAppNoticeList(BeanUtils.classToArrayList(notices));
		}
		resMsg.setTotalRows(totalRows > 0?totalRows:0);
		resMsg.setNumPerPage(numPerPage);
		resMsg.setPageNum(pageNum);
	}
	
	
	public void appNoticeAdd(ReqMsg_MAppNotice_AppNoticeAdd reqMsg,ResMsg_MAppNotice_AppNoticeAdd resMsg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", reqMsg.getName());
		
		List<BsAppMessage> list = mAppNoticeService.findNoticeByMap(map);
		if(list != null && !list.isEmpty() && list.size()>0) {
			resMsg.setJson(ConstantUtil.BSRESCODE_FAIL);
		}else {
			BsAppMessage message = new BsAppMessage();
			message.setName(reqMsg.getName());
			message.setTitle(reqMsg.getTitle());
			message.setReleasePart(reqMsg.getReleasePart());
			message.setPushChar(reqMsg.getPushChar());
			message.setPushAbstract(reqMsg.getPushAbstract());
			message.setCreateTime(new Date());
			message.setIsSend(2);
			message.setPushType(reqMsg.getPushType());
			if(reqMsg.getPushType() == 3 && reqMsg.getAppPage() != null) {
				message.setAppPage(reqMsg.getAppPage());
			}else {
				message.setContent(reqMsg.getContent());
			}
			
			int row = mAppNoticeService.saveAppNotice(message);
	        if(row > 0) {
	        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
	        }else {
	        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
	        }
		}
	}
	
	
	public void appNoticeDelete(ReqMsg_MAppNotice_AppNoticeDelete reqMsg,ResMsg_MAppNotice_AppNoticeDelete resMsg) {
		BsAppMessage message = mAppNoticeService.findAppNoticeById(reqMsg.getId());
		if(message == null || message.getIsSend() == 1) {
			resMsg.setJson(ConstantUtil.BSRESCODE_FAIL);
		}else {
			int row = mAppNoticeService.deleteAppNotice(reqMsg.getId());
	        if(row > 0) {
	        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
	        }else {
	        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
	        }
		}
	}
	
	public void appNoticeQuery(ReqMsg_MAppNotice_AppNoticeQuery reqMsg,ResMsg_MAppNotice_AppNoticeQuery resMsg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("name", reqMsg.getName());
		map.put("isSend", 2);
		
		List<BsAppMessage> list = mAppNoticeService.findNoticeByMap(map);
		if(list != null && !list.isEmpty() && list.size()>0) {
			resMsg.setAppMessage(list.get(0));
		}else {
			resMsg.setAppMessage(null);
		}
	}
	
	public void appNoticeUpdate(ReqMsg_MAppNotice_AppNoticeUpdate reqMsg,ResMsg_MAppNotice_AppNoticeUpdate resMsg) {
		mAppNoticeService.updateAppNotice(reqMsg.getAppMessage());
	}
	
	public void appNoticeDetail(ReqMsg_MAppNotice_AppNoticeDetail reqMsg,ResMsg_MAppNotice_AppNoticeDetail resMsg) {
		BsAppMessage message = mAppNoticeService.findAppNoticeById(reqMsg.getId());
		resMsg.setAppMessage(message);
	}
}
