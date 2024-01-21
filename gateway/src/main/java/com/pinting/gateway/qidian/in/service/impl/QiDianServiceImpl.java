package com.pinting.gateway.qidian.in.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mysql.fabric.xmlrpc.base.Array;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;

import com.pinting.gateway.business.out.service.QiDianBusinessService;
import com.pinting.gateway.dafy.in.service.impl.DafyLnLoanServiceImpl;

import com.pinting.gateway.hessian.message.qidian.G2BReqMsg_QiDian_FranchiseeRegist;
import com.pinting.gateway.hessian.message.qidian.G2BResMsg_QiDian_FranchiseeRegist;
import com.pinting.gateway.loan7.in.model.Repayment;
import com.pinting.gateway.loan7.in.util.DepLoan7InConstant;
import com.pinting.gateway.qidian.in.model.FranchiseeRegistReqModel;
import com.pinting.gateway.qidian.in.model.FranchiseeRegistResModel;
import com.pinting.gateway.qidian.in.model.FranchiseeResult;
import com.pinting.gateway.qidian.in.model.Franchisees;
import com.pinting.gateway.qidian.in.model.LoginReqModel;
import com.pinting.gateway.qidian.in.model.LoginResModel;
import com.pinting.gateway.qidian.in.service.QiDianService;
import com.pinting.gateway.qidian.in.util.QiDianInConstant;
import com.pinting.gateway.qidian.in.util.QiDianInMsgUtil;
import com.pinting.gateway.util.Constants;
@Service
public class QiDianServiceImpl implements QiDianService{
	
	
	private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);


	private  Logger log = LoggerFactory.getLogger(DafyLnLoanServiceImpl.class);
	@Autowired
	private QiDianBusinessService qiDianBusinessService;
	
	
	
	@Override
	public LoginResModel login(LoginReqModel reqModel){

		String clientKey = reqModel.getClientKey();
		String clientSecret = reqModel.getClientSecret();

		// 登录数据校验
		LoginResModel resModel = new LoginResModel();
		resModel.setResponseTime(new Date());
		
		log.info("七店token获取入参:" + clientSecret + "/" + clientKey);
		
		if (StringUtil.isEmpty(clientKey)){
			resModel.setRespCode(QiDianInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(QiDianInConstant.REUTRN_MSG_LOGIN_CLIENTKEY_EMPTY_FAIL);
			resModel.setResponseTime(new Date());
			return resModel;
        }
		
		
        if (StringUtil.isEmpty(clientSecret)){
			resModel.setRespCode(QiDianInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(QiDianInConstant.REUTRN_MSG_LOGIN_CLIENTSECRET_EMPTY_FAIL);
			resModel.setResponseTime(new Date());
			return resModel;
        }

        
        if (QiDianInMsgUtil.qiDianInClientKey.equals(clientKey) && QiDianInMsgUtil.qiDianInClientSecret.equals(clientSecret)) {
          
            resModel.setToken(UUID.randomUUID().toString());
            resModel.setRespCode(QiDianInConstant.RETURN_CODE_SUCCESS);
			resModel.setRespMsg(QiDianInConstant.RETURN_MSG_LOGIN_SUCCESS);
			resModel.setResponseTime(new Date());
            try {
				jsClientDaoSupport.setString(resModel.getToken(), "qidian_access_token", 60 * 30);
			} catch (Exception e) {
				// 登录失败
				resModel.setToken(null);
				resModel.setRespCode(QiDianInConstant.RETURN_CODE_FAIL);
				resModel.setRespMsg(QiDianInConstant.REUTRN_MSG_LOGIN_TOKEN_SET_FAIL);
				resModel.setResponseTime(new Date());
			}
        } else {
			// 登录失败
			resModel.setRespCode(QiDianInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(QiDianInConstant.REUTRN_MSG_LOGIN_FAIL);
			resModel.setResponseTime(new Date());
        }

		return resModel;
		
	}


	@Override
	public FranchiseeRegistResModel franchiseeRegist(
			FranchiseeRegistReqModel reqModel) {
		
		FranchiseeRegistResModel resModel = new FranchiseeRegistResModel();
		//请求参数非空校验
		List<Franchisees> franchiseesList = reqModel.getFranchisees();
		if (CollectionUtils.isNotEmpty(franchiseesList)) {
			if (franchiseesList.size() > 100) {
				resModel.setRespCode(QiDianInConstant.RETURN_CODE_REFUSE);
				resModel.setRespMsg(QiDianInConstant.RETURN_MSG_RESULT_OVERRUN_ERROR + ":请求列表数量超过限制（100）");
				resModel.setResponseTime(new Date());
				return resModel;
			}
			
			for (Franchisees franchisees : franchiseesList) {
				if (StringUtil.isBlank(franchisees.getFranchiseeId())) {
					resModel.setRespCode(QiDianInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(QiDianInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":franchiseeId为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(franchisees.getFranchiseeName())) {
					resModel.setRespCode(QiDianInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(QiDianInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":franchiseeName为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
				if (StringUtil.isBlank(franchisees.getFranchiseeMobile())) {
					resModel.setRespCode(QiDianInConstant.RETURN_CODE_REFUSE);
					resModel.setRespMsg(QiDianInConstant.RETURN_MSG_RESULT_PARAMS_ERROR + ":franchiseeMobile为空");
					resModel.setResponseTime(new Date());
					return resModel;
				}
				
			}
		}
		
		// 组装报文，向business发送
		G2BReqMsg_QiDian_FranchiseeRegist req = new G2BReqMsg_QiDian_FranchiseeRegist();
		
		List<com.pinting.gateway.hessian.message.qidian.model.Franchisees> franchiseeListReq = new ArrayList<>();
		for (Franchisees franchisees : franchiseesList) {
			com.pinting.gateway.hessian.message.qidian.model.Franchisees franchisee = new com.pinting.gateway.hessian.message.qidian.model.Franchisees();
			franchisee.setFranchiseeId(franchisees.getFranchiseeId());
			franchisee.setFranchiseeName(franchisees.getFranchiseeName());
			franchisee.setFranchiseeMobile(franchisees.getFranchiseeMobile());
			franchisee.setTradeName(franchisees.getTradeName());
			franchisee.setBusinessCode(franchisees.getBusinessCode());
			franchisee.setArea(franchisees.getArea());
			franchisee.setBranchCompany(franchisees.getBranchCompany());
			franchisee.setProvince(franchisees.getProvince());
			franchisee.setCity(franchisees.getCity());
			franchisee.setDistrict(franchisees.getDistrict());
			franchisee.setAddress(franchisees.getAddress());
			franchisee.setNote(franchisees.getNote());
			franchiseeListReq.add(franchisee);
		}
		
		req.setFranchisees(franchiseeListReq);
		// 向business发起请求通知，告知结果
		G2BResMsg_QiDian_FranchiseeRegist  res = qiDianBusinessService
				.sendBsFranchiseeRegist(req);
		// 获取business响应吗，并装成返回达飞的响应码
		if (!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			resModel.setRespCode(QiDianInConstant.RETURN_CODE_FAIL);
			resModel.setRespMsg(res.getResMsg());
			resModel.setResponseTime(new Date());
			return resModel;
		}
		//组织返回参数
		List<FranchiseeResult>  franchiseeResultListRes = new ArrayList<>();
		List<com.pinting.gateway.hessian.message.qidian.model.FranchiseeResult> results = res.getFranchiseeResult();
		if (CollectionUtils.isNotEmpty(results)) {
			for (com.pinting.gateway.hessian.message.qidian.model.FranchiseeResult franchiseeResult : results) {
				FranchiseeResult fResult  = new FranchiseeResult();
				fResult.setFranchiseeId(franchiseeResult.getFranchiseeId());
				fResult.setBgwFranchiseeId(franchiseeResult.getBgwFranchiseeId());
				fResult.setInviteLink(franchiseeResult.getInviteLink());
				fResult.setUpdateTime(franchiseeResult.getUpdateTime());
				franchiseeResultListRes.add(fResult);
			}
		}
		
		resModel.setFranchiseeResult(franchiseeResultListRes);
		String respCode = QiDianInConstant.RETURN_CODE_SUCCESS;
		resModel.setRespCode(respCode);
		resModel.setRespMsg(res.getResMsg());
		resModel.setResponseTime(new Date());
		return resModel;
	}


}
