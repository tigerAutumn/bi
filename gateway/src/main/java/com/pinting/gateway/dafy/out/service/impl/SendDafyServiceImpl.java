package com.pinting.gateway.dafy.out.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.dafy.out.model.BasicInformationReqModel;
import com.pinting.gateway.dafy.out.model.BasicInformationResModel;
import com.pinting.gateway.dafy.out.model.BindBankcardReqModel;
import com.pinting.gateway.dafy.out.model.BindBankcardResModel;
import com.pinting.gateway.dafy.out.model.BuyProductReqModel;
import com.pinting.gateway.dafy.out.model.BuyProductResModel;
import com.pinting.gateway.dafy.out.model.CheckAccountReqModel;
import com.pinting.gateway.dafy.out.model.CheckAccountResModel;
import com.pinting.gateway.dafy.out.model.CustomerWithdrawReqModel;
import com.pinting.gateway.dafy.out.model.CustomerWithdrawResModel;
import com.pinting.gateway.dafy.out.model.DafyDeptInfoModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDataReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDataResModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDeptReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementDeptResModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementUserReqModel;
import com.pinting.gateway.dafy.out.model.DafyIncrementUserResModel;
import com.pinting.gateway.dafy.out.model.DafyUserInfoModel;
import com.pinting.gateway.dafy.out.model.GetLoanRelationUrlReqModel;
import com.pinting.gateway.dafy.out.model.GetLoanRelationUrlResModel;
import com.pinting.gateway.dafy.out.model.QueryCurrDeptInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryCurrDeptInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryDeptInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryDeptInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryLoanRelationshipReqModel;
import com.pinting.gateway.dafy.out.model.QueryLoanRelationshipResModel;
import com.pinting.gateway.dafy.out.model.QueryRepayJnlReqModel;
import com.pinting.gateway.dafy.out.model.QueryRepayJnlResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByCurrDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByCurrDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByDeptCodeReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByDeptCodeResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByIdResModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByPhoneReqModel;
import com.pinting.gateway.dafy.out.model.QueryUserInfoByPhoneResModel;
import com.pinting.gateway.dafy.out.model.QueryWXAccountDetailReqModel;
import com.pinting.gateway.dafy.out.model.QueryWXAccountDetailResModel;
import com.pinting.gateway.dafy.out.model.RegisterCustomerReqModel;
import com.pinting.gateway.dafy.out.model.RegisterCustomerResModel;
import com.pinting.gateway.dafy.out.model.SysBatchBuyProductReqModel;
import com.pinting.gateway.dafy.out.model.SysBatchBuyProductResModel;
import com.pinting.gateway.dafy.out.model.SysWithdrawReqModel;
import com.pinting.gateway.dafy.out.model.SysWithdrawResModel;
import com.pinting.gateway.dafy.out.model.Tbdatapermission;
import com.pinting.gateway.dafy.out.model.Tbdepartment;
import com.pinting.gateway.dafy.out.model.Tbemployee;
import com.pinting.gateway.dafy.out.service.SendDafyService;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.dafy.out.util.DafyOutMsgUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.CommunicateUtil;
import com.pinting.gateway.util.DESUtil;
import com.pinting.gateway.util.HttpClientUtil;

import net.sf.json.JSONObject;

/**
 * 向达飞发起请求 接口实现类
 * 
 * @Project: gateway
 * @Title: DafySendServiceImpl.java
 * @author dingpf
 * @date 2015-2-10 下午6:25:26
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class SendDafyServiceImpl implements SendDafyService {
	
	private static Logger logger = LoggerFactory.getLogger(SendDafyServiceImpl.class);

	@Override
	/**
	 * 发起客户注册请求
	 */
	public RegisterCustomerResModel register(RegisterCustomerReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.REGISTER_CUSTOMER);
		reqModel.setRequestTime(new Date());
		RegisterCustomerResModel resModel = (RegisterCustomerResModel) CommunicateUtil
				.doCommunicate2Dafy(reqModel);
		
		if(!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_REGISTER_FAIL, resModel.getRespMsg());
		}
			
		return resModel;
	}

	@Override
	/**
	 * 客户银行卡绑定请求
	 */
	public BindBankcardResModel bindCard(BindBankcardReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.BIND_BANKCARD);
		reqModel.setRequestTime(new Date());
		BindBankcardResModel resModel = (BindBankcardResModel) CommunicateUtil
				.doCommunicate2Dafy(reqModel);
		if(!DafyOutConstant.RETURN_CODE_ACCEPT.equals(resModel.getRespCode())){
			throw new PTMessageException(PTMessageEnum.DAFY_BINDCARD_FAIL, resModel.getRespMsg());
		}
		return resModel;
	}

	@Override
	/**
	 * 产品购买
	 */
	public BuyProductResModel buyProduct(BuyProductReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.BUY_PRODUCT);
		reqModel.setRequestTime(new Date());
		BuyProductResModel resModel = (BuyProductResModel)CommunicateUtil.doCommunicate2Dafy(reqModel);
		return resModel;
	}

	@Override
	/**
	 * 资金对账
	 */
	public CheckAccountResModel checkAccount(CheckAccountReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.CHECK_ACCOUNT);
		reqModel.setRequestTime(new Date());
		CheckAccountResModel resModel = (CheckAccountResModel) CommunicateUtil.doCommunicate2Dafy(reqModel);
		return resModel;
	}

	@Override
	/**
	 * 债权关系查询
	 */
	public GetLoanRelationUrlResModel getLoanRelationUrl(
			GetLoanRelationUrlReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.GET_LOAN_RELATION_URL);
		reqModel.setRequestTime(new Date());
		GetLoanRelationUrlResModel resModel = (GetLoanRelationUrlResModel) CommunicateUtil.doCommunicate2Dafy(reqModel);
		return resModel;
	}

	@Override
	public BasicInformationResModel basicInformation(
			BasicInformationReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.BASIC_INFORMATION);
		reqModel.setRequestTime(new Date());
		BasicInformationResModel resModel = (BasicInformationResModel) CommunicateUtil.doCommunicate2Dafy(reqModel);
		return resModel;
	}
	
	/**
	 * 品听提现
	 */
	@Override
	public SysWithdrawResModel sysWithdraw(SysWithdrawReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.SYS_WITHDRAW);
		reqModel.setRequestTime(new Date());
		SysWithdrawResModel resModel = (SysWithdrawResModel) CommunicateUtil.doCommunicate2Dafy(reqModel);
		return resModel;
	}
	@Override
	public CustomerWithdrawResModel customerWithdraw(
			CustomerWithdrawReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.CUSTOMER_WITHDRAW);
		reqModel.setRequestTime(new Date());
		CustomerWithdrawResModel resModel = (CustomerWithdrawResModel) CommunicateUtil.doCommunicate2Dafy(reqModel);
		return resModel;
	}

	@Override
	public QueryWXAccountDetailResModel wXAccountDetailQuery(
			QueryWXAccountDetailReqModel req) {
		req.setTransCode(DafyOutConstant.QUERY_WXACCOUNT_DETAIL);
		req.setRequestTime(new Date());
		QueryWXAccountDetailResModel resModel = (QueryWXAccountDetailResModel) CommunicateUtil.doCommunicate2Dafy(req);
		return resModel;
	}

	@Override
	public SysBatchBuyProductResModel sysBatchBuyProduct(
			SysBatchBuyProductReqModel req) {
		req.setTransCode(DafyOutConstant.SYS_BATCH_BUY_PRODUCT);
		req.setMerchantId(Pay19HttpUtil.merchant_id);
		req.setRequestTime(new Date());
		req.setClientKey(CommunicateUtil.clientKey);
		SysBatchBuyProductResModel resModel = null;
		try {
			resModel = (SysBatchBuyProductResModel) CommunicateUtil.doCommunicate2Dafy(req);
		} catch (Exception e) {
			logger.error(">>>购买云贷产品失败<<<", e);
			//其他失败，包含通讯检查失败（如登录失败）
			throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_FAIL,e.getMessage());
		}
				
		if(!DafyOutConstant.RETURN_CODE_ACCEPT.equals(resModel.getRespCode())){
			//购买产品失败
			if(DafyOutConstant.RETURN_CODE_FAIL.equals(resModel.getRespCode())){
				throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_FAIL, resModel.getRespMsg());
			//其他失败，包含购买前置检查失败（如打款确认失败）
			}else{
				throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_REFU, resModel.getRespMsg());
			}
		}
		return resModel;
	}

    @Override
    public com.pinting.gateway.loan7.out.model.SysBatchBuyProductResModel sysBatchBuy7Product(
            com.pinting.gateway.loan7.out.model.SysBatchBuyProductReqModel req) {
        req.setTransCode(DafyOutConstant.SYS_BATCH_BUY_PRODUCT);
		req.setMerchantId(Pay19HttpUtil.merchant_id);
        req.setRequestTime(new Date());
		req.setCustomerId("bgw_dafy_customer_id");
        com.pinting.gateway.loan7.out.model.SysBatchBuyProductResModel resModel = null;
        try {
            resModel = (com.pinting.gateway.loan7.out.model.SysBatchBuyProductResModel) CommunicateUtil.doCommunicate2Loan7(req);
        } catch (Exception e) {
            //其他失败，包含通讯检查失败（如登录失败）
			logger.error(">>>购买7贷产品异常<<<", e);
            throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_FAIL, e.getMessage());
        }

        if (!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())) {
            //购买产品失败
            if (DafyOutConstant.RETURN_CODE_FAIL.equals(resModel.getRespCode())) {
                throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_FAIL, resModel.getRespMsg());
                //其他失败，包含购买前置检查失败（如打款确认失败）
            } else {
                throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_REFU, resModel.getRespMsg());
            }
        }
        return resModel;
    }

	@Override
	public QueryLoanRelationshipResModel getLoanRelationNewResModel(
			QueryLoanRelationshipReqModel req) {
		req.setTransCode(DafyOutConstant.GET_LOAN_RELATION_NEW);
		req.setRequestTime(new Date());
		req.setClientKey(CommunicateUtil.clientKey);
		QueryLoanRelationshipResModel resModel = new QueryLoanRelationshipResModel();
		/*List<LoanRelationData> data = new ArrayList<LoanRelationData>();
		for(int i=0;i<=1;i++){
			LoanRelationData lr = new LoanRelationData();
			lr.setAmount(300.00);
			lr.setBorrowerId(String.valueOf(100+i));
			lr.setBorrowerIdCard(String.valueOf(330+i)+"8928387182");
			lr.setBorrowerName("张三"+String.valueOf(i));
			lr.setBorrowNote("买房"+String.valueOf(i));
			lr.setCreateTime(DateUtil.parseDateTime("2016-01-21 13:41:13"));
			lr.setLenderId("123");
			lr.setLenderIdCard("31366666668888");
			lr.setLenderName("李四");
			lr.setProductName("理财"+req.getOrderNo());
			data.add(lr);
		}
		resModel.setData(data);
		resModel.setCount("2");
		resModel.setPageIndex("1");
		resModel.setPageNum("50");*/
		//resModel.setRespCode(DafyOutConstant.RETURN_CODE_SUCCESS);
		QueryLoanRelationshipResModel resModel2 = new QueryLoanRelationshipResModel();
		resModel2 = (QueryLoanRelationshipResModel) CommunicateUtil.doCommunicate2Dafy(req);
		return resModel2;
	}

	@Override
	public QueryRepayJnlResModel queryRepayJnl(QueryRepayJnlReqModel reqModel) {
		reqModel.setTransCode(DafyOutConstant.QUERY_REPAY_JNL);
		reqModel.setRequestTime(new Date());
		reqModel.setClientKey(CommunicateUtil.clientKey);
		QueryRepayJnlResModel resModel = (QueryRepayJnlResModel) CommunicateUtil.doCommunicate2Dafy(reqModel);
		return resModel;
	}

	@Override
	public QueryUserInfoByPhoneResModel queryUserInfoByPhone(QueryUserInfoByPhoneReqModel req) {
		QueryUserInfoByPhoneResModel res = null;
		logger.info("============调用接口：根据手机号查询达飞业务员信息============");
		String message = req.getStrMobile();
		logger.info("============手机号：【"+message+"】============");
		String ciphertext = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(message);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("strMobile", ciphertext);
		String url = GlobEnvUtil.get("dafy.query.userInfo.phone").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			logger.info("============接口返回数据：【"+decryptData+"】============");
			JSONObject obj = JSONObject.fromObject(decryptData);
			res = (QueryUserInfoByPhoneResModel)JSONObject.toBean(obj, QueryUserInfoByPhoneResModel.class);
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		
		return res;
	}

	@Override
	public QueryUserInfoByIdResModel queryUserInfoById(QueryUserInfoByIdReqModel req) {
		QueryUserInfoByIdResModel res = null;
		logger.info("============调用接口：根据用户id查询达飞业务员信息============");
		String message = req.getlUserId();
		logger.info("============用户id：【"+message+"】============");
		String ciphertext = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(message);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("lUserId", ciphertext);
		String url = GlobEnvUtil.get("dafy.query.userInfo.userId").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			logger.info("============接口返回数据：【"+decryptData+"】============");
			JSONObject obj = JSONObject.fromObject(decryptData);
			res = (QueryUserInfoByIdResModel)JSONObject.toBean(obj, QueryUserInfoByIdResModel.class);
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		
		return res;
	}

	@Override
	public QueryUserInfoByDeptCodeResModel queryUserInfoByDeptCode(QueryUserInfoByDeptCodeReqModel req) {
		QueryUserInfoByDeptCodeResModel res = null;
		logger.info("============调用接口：根据部门编码查询当前部门及子部门下的达飞业务员============");
		String message = req.getStrDeptCode();
		logger.info("============部门编号：【"+message+"】============");
		String ciphertext = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(message);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("strDeptCode", ciphertext);
		String url = GlobEnvUtil.get("dafy.query.userInfo.deptCode").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			JSONObject obj = JSONObject.fromObject(decryptData);
			Map<String,Object> classMap = new HashMap<String,Object>();
			classMap.put("strSalesman", DafyUserInfoModel.class);
			res = (QueryUserInfoByDeptCodeResModel)JSONObject.toBean(obj, QueryUserInfoByDeptCodeResModel.class, classMap);
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		
		return res;
	}

	@Override
	public QueryUserInfoByCurrDeptCodeResModel queryUserInfoByCurrDeptCode(QueryUserInfoByCurrDeptCodeReqModel req) {
		QueryUserInfoByCurrDeptCodeResModel res = null;
		logger.info("============调用接口：根据部门编码查询当前部门下的达飞业务员============");
		String message = req.getStrDeptCode();
		logger.info("============部门编号：【"+message+"】============");
		String ciphertext = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(message);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("strDeptCode", ciphertext);
		String url = GlobEnvUtil.get("dafy.query.current.userInfo.deptCode").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			JSONObject obj = JSONObject.fromObject(decryptData);
			Map<String,Object> classMap = new HashMap<String,Object>();
			classMap.put("strSalesman", DafyUserInfoModel.class);
			res = (QueryUserInfoByCurrDeptCodeResModel)JSONObject.toBean(obj, QueryUserInfoByCurrDeptCodeResModel.class, classMap);
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		
		return res;
	}

	@Override
	public QueryDeptInfoByDeptCodeResModel queryDeptInfoByDeptCode(QueryDeptInfoByDeptCodeReqModel req) {
		QueryDeptInfoByDeptCodeResModel res = null;
		logger.info("============调用接口：根据部门编码查询当前部门下面部门============");
		String message = req.getStrDeptCode();
		logger.info("============部门编号：【"+message+"】============");
		String ciphertext = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(message);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("strDeptCode", ciphertext);
		String url = GlobEnvUtil.get("dafy.query.deptInfo.deptCode").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			JSONObject obj = JSONObject.fromObject(decryptData);
			Map<String,Object> classMap = new HashMap<String,Object>();
			classMap.put("strDepartment", DafyDeptInfoModel.class);
			res = (QueryDeptInfoByDeptCodeResModel)JSONObject.toBean(obj, QueryDeptInfoByDeptCodeResModel.class, classMap);
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		
		return res;
	}

	@Override
	public QueryCurrDeptInfoByDeptCodeResModel queryCurrDeptInfoByDeptCode(QueryCurrDeptInfoByDeptCodeReqModel req) {
		QueryCurrDeptInfoByDeptCodeResModel res = null;
		logger.info("============调用接口：根据部门编码查询当前部门============");
		String message = req.getStrDeptCode();
		logger.info("============部门编号：【"+message+"】============");
		String ciphertext = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(message);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("strDeptCode", ciphertext);
		String url = GlobEnvUtil.get("dafy.query.current.deptInfo.deptCode").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			logger.info("============接口返回数据：【"+decryptData+"】============");
			JSONObject obj = JSONObject.fromObject(decryptData);
			res = (QueryCurrDeptInfoByDeptCodeResModel)JSONObject.toBean(obj, QueryCurrDeptInfoByDeptCodeResModel.class);
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		
		return res;
	}

	@Override
	public DafyIncrementUserResModel incrementUserInfo(DafyIncrementUserReqModel req) {
		DafyIncrementUserResModel res = null;
		logger.info("============调用接口：达飞用户信息增量同步============");
		String updateTime = req.getDtUpdateTime();
		String pageIndex = req.getStrPageIndex();
		logger.info("============同步时间(达飞系统时间)：【"+updateTime+"】，当前第"+(Integer.valueOf(pageIndex)+1)+"页============");
		String cipherUpdateTime = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(updateTime);
		String cipherPageIndex = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(pageIndex);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("dtUpdateTime", cipherUpdateTime);
		params.put("strPageIndex", cipherPageIndex);
		String url = GlobEnvUtil.get("dafy.increment.userInfo").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			JSONObject obj = JSONObject.fromObject(decryptData);
			Map<String,Object> classMap = new HashMap<String,Object>();
			classMap.put("strSalesmens", Tbemployee.class);
			res = (DafyIncrementUserResModel)JSONObject.toBean(obj, DafyIncrementUserResModel.class, classMap);
			logger.info("============达飞用户信息增量同步"+res.getStrSalesmens().size()+"条记录============");
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		
		return res;
	}

	@Override
	public DafyIncrementDeptResModel incrementDeptInfo(DafyIncrementDeptReqModel req) {
		DafyIncrementDeptResModel res = null;
		logger.info("============调用接口：达飞部门信息增量同步============");
		String updateTime = req.getDtUpdateTime();
		String pageIndex = req.getStrPageIndex();
		logger.info("============同步时间(达飞系统时间)：【"+updateTime+"】，当前第"+(Integer.valueOf(pageIndex)+1)+"页============");
		String cipherUpdateTime = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(updateTime);
		String cipherPageIndex = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(pageIndex);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("dtUpdateTime", cipherUpdateTime);
		params.put("strPageIndex", cipherPageIndex);
		String url = GlobEnvUtil.get("dafy.increment.deptInfo").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			JSONObject obj = JSONObject.fromObject(decryptData);
			Map<String,Object> classMap = new HashMap<String,Object>();
			classMap.put("strDepartments", Tbdepartment.class);
			res = (DafyIncrementDeptResModel)JSONObject.toBean(obj, DafyIncrementDeptResModel.class, classMap);
			logger.info("============达飞部门信息增量同步"+res.getStrDepartments().size()+"条记录============");
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		return res;
	}

	@Override
	public DafyIncrementDataResModel incrementDataInfo(DafyIncrementDataReqModel req) {
		DafyIncrementDataResModel res = null;
		logger.info("============调用接口：达飞权限信息增量同步============");
		String updateTime = req.getDtUpdateTime();
		String pageIndex = req.getStrPageIndex();
		logger.info("============同步时间(达飞系统时间)：【"+updateTime+"】，当前第"+(Integer.valueOf(pageIndex)+1)+"页============");
		String cipherUpdateTime = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(updateTime);
		String cipherPageIndex = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).encryptStr(pageIndex);
		
		Map<String, String> params = new HashMap<String, String>();
		params.put("dtUpdateTime", cipherUpdateTime);
		params.put("strPageIndex", cipherPageIndex);
		String url = GlobEnvUtil.get("dafy.increment.datapermission").trim();
		String retStr = HttpClientUtil.sendRequest(url, params);
		if(StringUtil.isNotBlank(retStr)) {
			String decryptData = new DESUtil(DafyOutMsgUtil.createDafyQueryKey()).decryptStr(retStr);
			decryptData = decryptData.replaceAll("[\\t\\n\\r]", "");
			JSONObject obj = JSONObject.fromObject(decryptData);
			Map<String,Object> classMap = new HashMap<String,Object>();
			classMap.put("strDataPermissions", Tbdatapermission.class);
			res = (DafyIncrementDataResModel)JSONObject.toBean(obj, DafyIncrementDataResModel.class, classMap);
			logger.info("============达飞权限信息增量同步"+res.getStrDataPermissions().size()+"条记录============");
			logger.info("============调用接口成功============");
		}
		else {
			logger.info("============调用接口失败，通讯失败============");
		}
		return res;
	}

}
