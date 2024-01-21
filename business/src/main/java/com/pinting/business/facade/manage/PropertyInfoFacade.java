package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_PropertyInfo_PropertyInfoDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_PropertyInfo_PropertyInfoList;
import com.pinting.business.hessian.manage.message.ReqMsg_PropertyInfo_PropertyInfoModify;
import com.pinting.business.hessian.manage.message.ResMsg_PropertyInfo_PropertyInfoDelete;
import com.pinting.business.hessian.manage.message.ResMsg_PropertyInfo_PropertyInfoList;
import com.pinting.business.hessian.manage.message.ResMsg_PropertyInfo_PropertyInfoModify;
import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.service.manage.MPropertyInfoService;
import com.pinting.business.util.BeanUtils;

@Component("PropertyInfo")
public class PropertyInfoFacade {
	
	@Autowired
	private MPropertyInfoService mPropertyInfoService;
	
	/**
	 * 列表
	 * @param req
	 * @param res
	 */
	public void propertyInfoList(ReqMsg_PropertyInfo_PropertyInfoList req, ResMsg_PropertyInfo_PropertyInfoList res) {
		int totalRows = mPropertyInfoService.findCountPropertyInfo();
		if(totalRows > 0) {
			ArrayList<BsPropertyInfo> bsPropertyInfoList = mPropertyInfoService.findPropertyInfoInfo(
					req.getPageNum(), req.getNumPerPage(), req.getOrderDirection(), req.getOrderField());
			ArrayList<HashMap<String, Object>> infoList = BeanUtils.classToArrayList(bsPropertyInfoList);
			res.setValueList(infoList);
		}
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}
	
	/**
	 * 添加 修改
	 * @param req
	 * @param res
	 */
	public void propertyInfoModify(ReqMsg_PropertyInfo_PropertyInfoModify req, ResMsg_PropertyInfo_PropertyInfoModify res) {
		BsPropertyInfo bsPropertyInfo = new BsPropertyInfo();
		bsPropertyInfo.setPropertyName(req.getPropertyName().trim());// 去掉名称首尾空格
		BsPropertyInfo result = mPropertyInfoService.findPropertyInfoName(bsPropertyInfo); // 检查合作方名称是否已存在
		
		bsPropertyInfo.setId(req.getId());
		bsPropertyInfo.setPropertySummary(req.getPropertySummary());
		bsPropertyInfo.setReturnSource(req.getReturnSource());
		bsPropertyInfo.setFundSecurity(req.getFundSecurity());
		bsPropertyInfo.setOrgnizeCheck(req.getOrgnizeCheck());
		bsPropertyInfo.setCoopProtocolPics(req.getCoopProtocolPics());
		bsPropertyInfo.setRatingGrade(req.getRatingGrade());
		bsPropertyInfo.setLoanProtocolPics(req.getLoanProtocolPics());
		bsPropertyInfo.setOrgnizeCheckPics(req.getOrgnizeCheckPics());
		bsPropertyInfo.setRatingGradePics(req.getRatingGradePics());
		bsPropertyInfo.setPropertySymbol(req.getPropertySymbol());
		
		if (req.getId() != null && req.getId() != 0) { // 编辑
			if (result != null) {
				if (result.getId().equals(req.getId())) {
					bsPropertyInfo.setUpdateTime(new Date());
					mPropertyInfoService.updatePropertyInfo(bsPropertyInfo);
					res.setFlag(ResMsg_PropertyInfo_PropertyInfoModify.MODIFY_SUCCESS);
				} else {
					res.setFlag(ResMsg_PropertyInfo_PropertyInfoModify.REPEAT_NAME);
				}
			} else {
				bsPropertyInfo.setUpdateTime(new Date());
				mPropertyInfoService.updatePropertyInfo(bsPropertyInfo);
				res.setFlag(ResMsg_PropertyInfo_PropertyInfoModify.MODIFY_SUCCESS);
			}
		} else { 
			if (result == null) {// 新增
				bsPropertyInfo.setCreateTime(new Date());
				bsPropertyInfo.setUpdateTime(new Date());
				mPropertyInfoService.addPropertyInfo(bsPropertyInfo);
				res.setFlag(ResMsg_PropertyInfo_PropertyInfoModify.INSERT_SUCCESS);
			} else {
				res.setFlag(ResMsg_PropertyInfo_PropertyInfoModify.REPEAT_NAME);
			}
		}

	}
	
	/**
	 * 删除
	 * @param req
	 * @param res
	 */
	public void propertyInfoDelete(ReqMsg_PropertyInfo_PropertyInfoDelete req, ResMsg_PropertyInfo_PropertyInfoDelete res) {
		if(req.getId() != null && req.getId() != 0) {
			mPropertyInfoService.deletePropertyInfoById(req.getId());
		}
	}

}
