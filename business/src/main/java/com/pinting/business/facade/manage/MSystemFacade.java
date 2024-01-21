package com.pinting.business.facade.manage;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.BsSensitiveOperateJnl;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsSysStatus;
import com.pinting.business.model.vo.DepServiceFeeVO;
import com.pinting.business.service.manage.MSysConfigService;
import com.pinting.business.service.manage.MSysStatusService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 系统管理
 * @Project: business
 * @Title: MSystemFacade.java
 * @author dingpf&Linkin
 * @date 2015-1-29 下午1:43:49
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MSystem")
public class MSystemFacade{
	
	@Autowired
	private MSysConfigService mSysConfigService;
	@Autowired
	private MSysStatusService mSysStatusService;
	
	public void sysConfigsQuery(ReqMsg_MSystem_SysConfigsQuery req, ResMsg_MSystem_SysConfigsQuery res){
		
		String pageNum = req.getPageNum();
		String numPerPage = req.getNumPerPage();
		
		int totalRows = mSysConfigService.countTotalBsSysConfigs();
		if(totalRows > 0){
			List<BsSysConfig> list = mSysConfigService.findBsSysConfigsByPage(pageNum, numPerPage);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setConfigs(mapList);
		}
		res.setPageNum(pageNum);
		res.setNumPerPage(numPerPage);
		res.setTotalRows(String.valueOf(totalRows));
		
	}
	
	public void sysConfigQuery(ReqMsg_MSystem_SysConfigQuery req, ResMsg_MSystem_SysConfigQuery res){
		
		BsSysConfig bsSysConfig = mSysConfigService.findSysConfigByKey(req.getConfKey());
		res.setConfKey(bsSysConfig.getConfKey());
		res.setConfValue(bsSysConfig.getConfValue());
		res.setName(bsSysConfig.getName());
		res.setNote(bsSysConfig.getNote());
		
	}
	public void spercialJnl(ReqMsg_MSystem_spercialJnl req, ResMsg_MSystem_spercialJnl resp){
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		int totalRows =mSysConfigService.countTotalMSpecialJnl();
		List<BsSpecialJnl> spJnl = mSysConfigService.findBsSpecialJnlByPage(pageNum,numPerPage);	
		resp.setTotalRows(totalRows);
		resp.setNumPerPage(numPerPage);
		resp.setPageNum(pageNum);	
		resp.setMSpercialList(BeanUtils.classToArrayList(spJnl));
	}
	
	public void status(ReqMsg_MSystem_status req, ResMsg_MSystem_status res){
		
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		List<BsSysStatus> list = mSysStatusService.findBsStatisticsList();
		if (!CollectionUtils.isEmpty(list)) {			
			int totalRows = list.size();
			if (totalRows > 0) {
				List<BsSysStatus> valueList = mSysStatusService.findBsStatisticsList(pageNum, numPerPage);
				ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(valueList);
				res.setBsValueList(mapList);
				res.setTotalRows(totalRows);
			} else {
				res.setTotalRows(0);
			}
		}
	}

	@Deprecated
	public void statusUpdate(ReqMsg_MSystem_statusUpdate req, ResMsg_MSystem_statusUpdate res){
		BsSysStatus bsSysStatus = new BsSysStatus();
		bsSysStatus.setmUserId(req.getmUserId());
		//bsSysStatus.setStatusKey(req.getStatusKey());
		bsSysStatus.setStatusValue(req.getStatusValue());
		bsSysStatus.setUpdatetime(new Date());
		if(mSysStatusService.updateBsStatus(bsSysStatus))
		{
			if(req.getStatusKey().equals("sys_halt"))
			{
				Constants.sysValue=req.getStatusValue();
				
			}else
			{
				Constants.tranValue=req.getStatusValue();
			}
		}
	}
	public void sensitiveJnl(ReqMsg_MSystem_sensitiveJnl req, ResMsg_MSystem_sensitiveJnl resp){
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		int totalRows =mSysConfigService.countTotalBsSensitiveJnl(req.getUserName(),req.getIp());
		List<BsSensitiveOperateJnl> spJnl = mSysConfigService.findBsSensitiveOperateJnlByPage(req.getUserName(),req.getIp(),pageNum,numPerPage);	
		resp.setTotalRows(totalRows);
		resp.setNumPerPage(numPerPage);
		resp.setPageNum(pageNum);	
		resp.setUserName(req.getUserName());
		resp.setIp(req.getIp());
		resp.setBsSensitiveList(BeanUtils.classToArrayList(spJnl));
	}
	public void sysConfigUpdate(ReqMsg_MSystem_SysConfigUpdate req, ResMsg_MSystem_SysConfigUpdate res){
		if(Constants.SYSCONFIG_UPDATEFLAG_UPDATE.equals(req.getUpdateFlag())){
			BsSysConfig bsSysConfig = new BsSysConfig();
			bsSysConfig.setConfKey(req.getConfKey());
			bsSysConfig.setConfValue(req.getConfValue());
			bsSysConfig.setName(req.getName());
			bsSysConfig.setNote(req.getNote());
			boolean flag = mSysConfigService.saveOrUpdateSysConfig(bsSysConfig , req.getUpdateFlag());
			if(!flag){
				throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
			}
		}else{
			throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
		}
	}
	
}
