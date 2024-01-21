package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_DeleteFund;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_InfoQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_NetInfoQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_NetListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_NetSave;
import com.pinting.business.hessian.manage.message.ReqMsg_MFund_SaveFund;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_DeleteFund;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_InfoQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_NetInfoQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_NetListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_NetSave;
import com.pinting.business.hessian.manage.message.ResMsg_MFund_SaveFund;
import com.pinting.business.model.FdInfo;
import com.pinting.business.model.FdNet;
import com.pinting.business.model.vo.FdNetVO;
import com.pinting.business.service.manage.MFundService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;

@Component("MFund")
public class MFundFacade {
	
	@Autowired
	private MFundService fundService;
	
	public void listQuery(ReqMsg_MFund_ListQuery reqMsg,ResMsg_MFund_ListQuery resMsg){
		
		FdInfo fdInfo = new FdInfo();
		fdInfo.setPageNum(reqMsg.getPageNum());
		
		fdInfo.setNumPerPage(reqMsg.getNumPerPage());
		
		ArrayList<FdInfo> valueList = (ArrayList<FdInfo>) fundService.findMFdInfoList(fdInfo);
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		resMsg.setValueList(hashList);
		
		int total = fundService.countNetValueList();
		
		fdInfo.setTotalRows(total);
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
		
	}
	//基金净值列表查询
	public void netListQuery(ReqMsg_MFund_NetListQuery reqMsg,ResMsg_MFund_NetListQuery resMsg){
		
		FdNetVO fdNet = new FdNetVO();
		fdNet.setPageNum(reqMsg.getPageNum());
		
		fdNet.setNumPerPage(reqMsg.getNumPerPage());
		
		ArrayList<FdNetVO> valueList = (ArrayList<FdNetVO>)  fundService.findMFdNetInfoList(fdNet);
		ArrayList<HashMap<String,Object>> hashList = BeanUtils.classToArrayList(valueList); 
		resMsg.setNetList(hashList);

		int total = fundService.countNetList();
		
		fdNet.setTotalRows(total);
		
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
		resMsg.setTotalRows(total);
		
	}
	
	
	public void infoQuery(ReqMsg_MFund_InfoQuery reqMsg,ResMsg_MFund_InfoQuery resMsg){
		
		
		FdInfo fdInfo = fundService.findFdInfoById(reqMsg.getId());
		
		resMsg.setId(fdInfo.getId());
		resMsg.setName(fdInfo.getName());
		resMsg.setNote(fdInfo.getNote());
		resMsg.setScale(fdInfo.getScale());
		resMsg.setStatus(fdInfo.getStatus());
	}
	
	
	public void saveFund(ReqMsg_MFund_SaveFund reqMsg,ResMsg_MFund_SaveFund resMsg) throws PTMessageException{
		
		boolean isSeccuss = false;
		
		if(reqMsg.getOperateType().equals("create")){
			FdInfo fdInfo = new FdInfo();
			fdInfo.setCreateDate(new Date());
			fdInfo.setName(reqMsg.getName());
			fdInfo.setNote(reqMsg.getNote());
			fdInfo.setScale(reqMsg.getScale());
			fdInfo.setStatus(reqMsg.getStatus());
			isSeccuss = fundService.insertFdInfo(fdInfo);
			
		}else{
			
			FdInfo fdInfo = new FdInfo();
			fdInfo.setUpdateTime(new Date());
			fdInfo.setStatus(reqMsg.getStatus());
			fdInfo.setName(reqMsg.getName());
			fdInfo.setNote(reqMsg.getNote());
			fdInfo.setScale(reqMsg.getScale());
			fdInfo.setId(reqMsg.getId());
			isSeccuss = fundService.modifyFdInfoById(fdInfo);
		}
		
		if(!isSeccuss){
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
	}

	public void netSave(ReqMsg_MFund_NetSave req,ResMsg_MFund_NetSave resp) throws PTMessageException{
		
		boolean isSeccuss = false;
		FdNet fdNet2 = new FdNet();
		if(req.getId()==null){
			FdNet fdNet = new FdNet();
			fdNet.setFundId(req.getFundId());
			fdNet.setDate(req.getDate());
			fdNet.setNet(req.getNet());
			fdNet.setNote(req.getNote());
			fdNet2 = fundService.findFdNetInfoByfundIdAndDate(req.getFundId(), req.getDate());
			if(fdNet2!=null)
			{
				throw new PTMessageException(PTMessageEnum.FUND_AND_DATE_IS_EXIT);
			}
			isSeccuss = fundService.insertNetInfo(fdNet);
			
		}else{
			if(req.getOperateType()!=null)
			{
				
				isSeccuss =fundService.DeleteNetInfoById(req.getId());
			}else
			{
			FdNet fdNet = new FdNet();
			fdNet.setId(req.getId());
			fdNet.setFundId(req.getFundId());
			fdNet.setDate(req.getDate());
			fdNet.setNet(req.getNet());
			fdNet.setNote(req.getNote());
			fdNet2 = fundService.findFdNetInfoByfundIdAndDate(req.getFundId(), req.getDate());
			if(fdNet2!=null&&(!fdNet2.getId().equals(fdNet.getId())))
			{
				throw new PTMessageException(PTMessageEnum.FUND_AND_DATE_IS_EXIT);
			}
			isSeccuss = fundService.modifyNetInfoById(fdNet);
			}
		}
		
		if(!isSeccuss){
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
	}
	public void netInfoQuery(ReqMsg_MFund_NetInfoQuery reqMsg,ResMsg_MFund_NetInfoQuery resMsg) throws PTMessageException{
		if(reqMsg.getId()!=null)
		{
		FdNet fdNet = fundService.findFdNetInfoById(reqMsg.getId());
		
		resMsg.setId(fdNet.getId());
		resMsg.setFundId(fdNet.getFundId());
		resMsg.setNote(fdNet.getNote());
		resMsg.setDate(fdNet.getDate());
		resMsg.setNet(fdNet.getNet());
		}
		FdInfo fdInfo = new FdInfo();
		fdInfo.setStatus(1);
		ArrayList<FdInfo> fundList = (ArrayList<FdInfo>) fundService.findMFdInfoList(fdInfo);
		ArrayList<HashMap<String,Object>> hashLists = BeanUtils.classToArrayList(fundList);
		resMsg.setFundList(hashLists);
	}
	
	public void deleteFund(ReqMsg_MFund_DeleteFund reqMsg,ResMsg_MFund_DeleteFund resMsg) throws PTMessageException{
		
		boolean key = false;
		String[] ids = null;
		List<Integer> idList = new ArrayList<Integer>();
		if(reqMsg.getIds() != null){
			ids = reqMsg.getIds().split(",");
			for (int i = 0; i < ids.length; i++) {
				idList.add(Integer.parseInt(ids[i]));
			}
		}else{
			idList.add(reqMsg.getId());
		}
		
		if(reqMsg.getOperateType().equals("disable")){ //删除产品
			key = fundService.DeleteFdInfoByIds(idList,Constants.FUND_STATUS_DISABLE);
		}else{ //重新上架这些产品
			key = fundService.DeleteFdInfoByIds(idList, Constants.FUND_STATUS_ABLE);
		}
		if(!key){
			throw new PTMessageException(PTMessageEnum.TRANS_ERROR);
		}
		
	}
	
}
