package com.pinting.gateway.business.in.facade;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.dafy.out.model.DafyIncrementDataReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDataResModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDeptReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDeptResModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementUserReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementUserResModel;
import com.pinting.gateway.dafy.out.model.GetLoanRelationUrlReqModel;
import com.pinting.gateway.dafy.out.model.GetLoanRelationUrlResModel;
import com.pinting.gateway.dafy.out.model.QueryCurrDeptInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryCurrDeptInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryDeptInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryDeptInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryLoanRelationshipReqModel;
import com.pinting.gateway.dafy.out.model.QueryLoanRelationshipResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByCurrDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByCurrDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByPhoneReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByPhoneResModel;
import com.pinting.gateway.dafy.out.model.RegisterCustomerReqModel;
import com.pinting.gateway.dafy.out.model.RegisterCustomerResModel;
import com.pinting.gateway.dafy.out.service.SendDafyService;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.dafy.out.util.DafyOutMsgUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementDataMission;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_IncrementUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryCurrDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryCurrDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserById;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_Register;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationNew;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_GetLoanRelationUrl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementDataMission;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_IncrementUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryCurrDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryCurrDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryDeptInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryDeptUserInfo;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserById;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_Register;
import com.pinting.gateway.util.BeanUtils;

/**
 * 与达飞之间 用户相关
 * 
 * @Project: gateway
 * @Title: CustomerFacade.java
 * @author dingpf
 * @date 2015-2-10 下午6:17:43
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Customer")
public class CustomerFacade {
	@Autowired
	private SendDafyService dafySendService;

	public void register(B2GReqMsg_Customer_Register req,
			B2GResMsg_Customer_Register res) {
		// 组装报文
		RegisterCustomerReqModel req2Dafy = new RegisterCustomerReqModel();
		req2Dafy.setName(req.getName());
		req2Dafy.setCardNo(req.getIdCard());
		req2Dafy.setMobile(req.getMobile());
		// 发送给达飞
		RegisterCustomerResModel dafyRes = dafySendService.register(req2Dafy);

		// 返回信息
		res.setCustomerId(dafyRes.getCustomerId());
		if(DafyOutConstant.RETURN_CODE_SUCCESS.equals(dafyRes.getRespCode())){
			res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		}else{
			res.setResCode(ConstantUtil.BSRESCODE_FAIL);
		}
		res.setResMsg(dafyRes.getRespMsg());
//		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(dafyRes.getRespCode())){
//			throw new PTMessageException(PTMessageEnum.DAFY_REGISTER_FAIL, dafyRes.getRespMsg());
//		}

	}
	public void getLoanRelationUrl(B2GReqMsg_Customer_GetLoanRelationUrl req, 
			B2GResMsg_Customer_GetLoanRelationUrl res){
		// 组装报文
		GetLoanRelationUrlReqModel req2Dafy = new GetLoanRelationUrlReqModel();
		// 发送给达飞
		GetLoanRelationUrlResModel dafyRes = dafySendService.getLoanRelationUrl(req2Dafy);
		// 返回信息
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(dafyRes.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_QUERYLOANRELATION_FAIL, dafyRes.getRespMsg());
		}else{
			res.setDownLoadUrl(dafyRes.getDownLoadUrl());
		}
	}
	
	public void getLoanRelationNew(B2GReqMsg_Customer_GetLoanRelationNew req, 
			B2GResMsg_Customer_GetLoanRelationNew res){
		// 组装报文
		QueryLoanRelationshipReqModel req2Dafy = new QueryLoanRelationshipReqModel();
		req2Dafy.setCustomerId(DafyOutMsgUtil.sysCustomerId);
		req2Dafy.setOrderNo(req.getOrderNo());
		req2Dafy.setPageIndex(req.getPageIndex());
		req2Dafy.setPageNum(req.getPageNum());
		
		// 发送给达飞
		QueryLoanRelationshipResModel dafyRes = dafySendService.getLoanRelationNewResModel(req2Dafy);
		// 返回信息
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(dafyRes.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_QUERYLOANRELATION_FAIL, dafyRes.getRespMsg());
		}else{
			res.setData(dafyRes.getData());
			res.setCount(dafyRes.getCount());
			res.setPageIndex(dafyRes.getPageIndex());
			res.setPageNum(dafyRes.getPageNum());
		}
		
	}
	
	/**
	 * 根据电话查询达飞业务员信息
	 * @param req
	 * @param res
	 */
	public void queryUserByPhone(B2GReqMsg_Customer_QueryUserByPhone req, B2GResMsg_Customer_QueryUserByPhone res) {
		QueryUserInfoByPhoneReqModel reqModel = new QueryUserInfoByPhoneReqModel();
		reqModel.setStrMobile(req.getMobile());
		QueryUserInfoByPhoneResModel resModel = dafySendService.queryUserInfoByPhone(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			if(resModel.getStrSalesman() != null) {
				res.setStrSalesman(BeanUtils.transBeanMap(resModel.getStrSalesman()));
			}
		}
	}
	
	/**
	 * 根据用户id查询达飞业务员信息
	 * @param req
	 * @param res
	 */
	public void queryUserById(B2GReqMsg_Customer_QueryUserById req, B2GResMsg_Customer_QueryUserById res) {
		QueryUserInfoByIdReqModel reqModel = new QueryUserInfoByIdReqModel();
		reqModel.setlUserId(req.getlUserId());
		QueryUserInfoByIdResModel resModel = dafySendService.queryUserInfoById(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			if(resModel.getStrSalesman() != null) {
				res.setStrSalesman(BeanUtils.transBeanMap(resModel.getStrSalesman()));
			}
		}
	}
	
	/**
	 * 根据部门编码查询当前部门及子部门下的达飞业务员
	 * @param req
	 * @param res
	 */
	public void queryDeptUserInfo(B2GReqMsg_Customer_QueryDeptUserInfo req, B2GResMsg_Customer_QueryDeptUserInfo res) {
		QueryUserInfoByDeptCodeReqModel reqModel = new QueryUserInfoByDeptCodeReqModel();
		reqModel.setStrDeptCode(req.getDeptCode());
		QueryUserInfoByDeptCodeResModel resModel = dafySendService.queryUserInfoByDeptCode(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			if(!CollectionUtils.isEmpty(resModel.getStrSalesman())) {
				res.setStrSalesman(BeanUtils.classToArrayList(resModel.getStrSalesman()));
			}
		}
	}
	
	/**
	 * 根据部门编码查询当前部门下的达飞业务员
	 * @param req
	 * @param res
	 */
	public void queryCurrDeptUserInfo(B2GReqMsg_Customer_QueryCurrDeptUserInfo req, B2GResMsg_Customer_QueryCurrDeptUserInfo res) {
		QueryUserInfoByCurrDeptCodeReqModel reqModel = new QueryUserInfoByCurrDeptCodeReqModel();
		reqModel.setStrDeptCode(req.getDeptCode());
		QueryUserInfoByCurrDeptCodeResModel resModel = dafySendService.queryUserInfoByCurrDeptCode(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			if(!CollectionUtils.isEmpty(resModel.getStrSalesman())) {
				res.setStrSalesman(BeanUtils.classToArrayList(resModel.getStrSalesman()));
			}
		}
	}
	
	/**
	 * 根据部门编码查询当前部门下面部门
	 * @param req
	 * @param res
	 */
	public void queryDeptInfo(B2GReqMsg_Customer_QueryDeptInfo req, B2GResMsg_Customer_QueryDeptInfo res) {
		QueryDeptInfoByDeptCodeReqModel reqModel = new QueryDeptInfoByDeptCodeReqModel();
		reqModel.setStrDeptCode(req.getDeptCode());
		QueryDeptInfoByDeptCodeResModel resModel = dafySendService.queryDeptInfoByDeptCode(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			if(!CollectionUtils.isEmpty(resModel.getStrDepartment())) {
				res.setStrDepartment(BeanUtils.classToArrayList(resModel.getStrDepartment()));
			}
		}
	}
	
	/**
	 * 根据部门编码查询当前部门
	 * @param req
	 * @param res
	 */
	public void queryCurrDeptInfo(B2GReqMsg_Customer_QueryCurrDeptInfo req, B2GResMsg_Customer_QueryCurrDeptInfo res) {
		QueryCurrDeptInfoByDeptCodeReqModel reqModel = new QueryCurrDeptInfoByDeptCodeReqModel();
		reqModel.setStrDeptCode(req.getStrDeptCode());
		QueryCurrDeptInfoByDeptCodeResModel resModel = dafySendService.queryCurrDeptInfoByDeptCode(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			if(resModel.getStrDepartInfo() != null) {
				res.setStrDepartment(BeanUtils.transBeanMap(resModel.getStrDepartInfo()));
			}
		}
	}
	
	/**
	 * 达飞用户信息增量同步
	 * @param req
	 * @param res
	 */
	public void incrementUserInfo(B2GReqMsg_Customer_IncrementUserInfo req, B2GResMsg_Customer_IncrementUserInfo res) {
		DafyIncrementUserReqModel reqModel = new DafyIncrementUserReqModel();
		reqModel.setDtUpdateTime(req.getDtUpdateTime());
		reqModel.setStrPageIndex(String.valueOf(req.getPageIndex()));
		DafyIncrementUserResModel resModel = dafySendService.incrementUserInfo(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			res.setIsNext(resModel.getIsNext());
			if(!CollectionUtils.isEmpty(resModel.getStrSalesmens())) {
				res.setUserList(BeanUtils.classToArrayList(resModel.getStrSalesmens()));
			}
		}
	}
	
	/**
	 * 达飞部门信息增量同步
	 * @param req
	 * @param res
	 */
	public void incrementDeptInfo(B2GReqMsg_Customer_IncrementDeptInfo req, B2GResMsg_Customer_IncrementDeptInfo res) {
		DafyIncrementDeptReqModel reqModel = new DafyIncrementDeptReqModel();
		reqModel.setDtUpdateTime(req.getDtUpdateTime());
		reqModel.setStrPageIndex(String.valueOf(req.getPageIndex()));
		DafyIncrementDeptResModel resModel = dafySendService.incrementDeptInfo(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			res.setIsNext(resModel.getIsNext());
			if(!CollectionUtils.isEmpty(resModel.getStrDepartments())) {
				res.setDeptList(BeanUtils.classToArrayList(resModel.getStrDepartments()));
			}
		}
	}
	
	/**
	 * 达飞权限信息增量同步
	 * @param req
	 * @param res
	 */
	public void incrementDataMission(B2GReqMsg_Customer_IncrementDataMission req, B2GResMsg_Customer_IncrementDataMission res) {
		DafyIncrementDataReqModel reqModel = new DafyIncrementDataReqModel();
		reqModel.setDtUpdateTime(req.getDtUpdateTime());
		reqModel.setStrPageIndex(String.valueOf(req.getPageIndex()));
		DafyIncrementDataResModel resModel = dafySendService.incrementDataInfo(reqModel);
		if(resModel != null) {
			res.setCode(resModel.getCode());
			res.setMsg(resModel.getMsg());
			res.setIsNext(resModel.getIsNext());
			if(!CollectionUtils.isEmpty(resModel.getStrDataPermissions())) {
				res.setDataList(BeanUtils.classToArrayList(resModel.getStrDataPermissions()));
			}
		}
	}
	
}
