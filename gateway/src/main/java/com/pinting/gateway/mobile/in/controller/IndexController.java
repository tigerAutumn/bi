package com.pinting.gateway.mobile.in.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.hessian.site.message.*;
import com.pinting.open.pojo.model.index.*;
import com.pinting.open.pojo.request.index.*;
import com.pinting.open.pojo.response.index.*;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.jsoup.helper.StringUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.business.out.service.AppIndexBusinessService;
import com.pinting.gateway.business.out.service.AppProductBusinessService;
import com.pinting.gateway.mobile.in.enums.OpenMessageEnum;
import com.pinting.gateway.mobile.in.util.CheckUtil;
import com.pinting.gateway.mobile.in.util.Constants;
import com.pinting.gateway.mobile.in.util.Util;
import com.pinting.gateway.util.AddressUtil;
import com.pinting.open.base.controller.ControllerCallBack;
import com.pinting.open.base.controller.OpenController;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Product;

@Controller
@Scope("prototype")
@RequestMapping("mobile/index")
public class IndexController extends OpenController {

	@Autowired
	private AppIndexBusinessService appIndexBusinessService;

	@Autowired
	private AppProductBusinessService appProductBusinessService;






	@ResponseBody
	@RequestMapping("login/v_1.0.1")
	public String login_1_0_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, LoginRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				LoginRequest lgRequest = (LoginRequest) request;
				String password = lgRequest.getPassword();
				if(!CheckUtil.ParamCheck(lgRequest, "mobile","password")) {
					throw new OpenException(OpenMessageEnum.MOBILE_OR_PASSWORD_EMPTY.getCode(),OpenMessageEnum.MOBILE_OR_PASSWORD_EMPTY.getMessage());
				}
				if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(password, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				LoginResponse lgResponse = (LoginResponse) response;
				LoginRequest lgRequest = (LoginRequest) request;
				String mobile = lgRequest.getMobile();
				String password = lgRequest.getPassword();
				ReqMsg_User_Login reqLogin = new ReqMsg_User_Login();
				reqLogin.setVersion("1.0.1");
				reqLogin.setNick(mobile);
				reqLogin.setPassword(password);
				ResMsg_User_Login resLogin = appIndexBusinessService.appIndexLogin(reqLogin);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resLogin.getResCode())) {
					BeanUtils.copyProperties(resLogin, lgResponse);
					if("qianbao".equals(resLogin.getAgentId())) {
						lgResponse.setAgentName("qianbao");
					}
					lgResponse.setAgentId(resLogin.getRealAgentId());
				}
				else {
					throw new OpenException(resLogin.getResCode(),resLogin.getResMsg());
				}
			}
		});
	}




	@ResponseBody
	@RequestMapping("register/v_1.0.1")
	public String register_1_0_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, RegisterRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				RegisterRequest reRequest = (RegisterRequest) request;
				String mobile = reRequest.getMobile();
				String mobileCode = reRequest.getMobileCode();
				String password = reRequest.getPassword();
				String recommendId = reRequest.getRecommendId();
				if(!CheckUtil.ParamCheck(reRequest, "mobile","mobileCode","password")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(mobile.length() != 11 || !CheckUtil.RegCheck(mobile, "^[1][34587]\\d{9}$")) {
					throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
				}
				if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(mobile, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}
				if(!CheckUtil.RegCheck(mobileCode, "^[0-9]*$")) {
					throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
				}

				if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(mobile, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}

				if(recommendId != null || !"".equals(recommendId)) {
					if(!CheckUtil.RegCheck(String.valueOf(recommendId), "^[0-9a-zA-Z_]+$")) {
						throw new OpenException(OpenMessageEnum.RECOMMENT_NOT_NORM.getCode(),OpenMessageEnum.RECOMMENT_NOT_NORM.getMessage());
					}
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				RegisterResponse reResponse = (RegisterResponse) response;
				RegisterRequest reRequest = (RegisterRequest) request;
				ReqMsg_User_Regist reqRegister = new ReqMsg_User_Regist();
				BeanUtils.copyProperties(reRequest, reqRegister);
				reqRegister.setRegTerminal(reRequest.getAppClient());
				reqRegister.setVersion("1.0.1");

				if (reqRegister.getRecommendId() == null || "".equals(reqRegister.getRecommendId())) {
					reqRegister.setRecommendId(null);
				} else {
					//判断是否是销售人员推荐（根据邀请码判断）
					if(!reqRegister.getRecommendId().matches("[0-9]+")){//不是数字就设置null
						reqRegister.setInviteCode(reqRegister.getRecommendId());//把邀请码存下来
						reqRegister.setRecommendId(null);
						reqRegister.setAgentId(31); // 销售人员推荐渠道ID
					}
					//计算推荐人ID
					if(null != reqRegister.getRecommendId() && reqRegister.getRecommendId().length()!= 11){
						reqRegister.setRecommendId(Util.getUserIdByInvitationCode(reqRegister.getRecommendId()).toString());
						reqRegister.setAgentId(null);
					}else if (null != reqRegister.getRecommendId() && reqRegister.getRecommendId().length()== 11) {
						reqRegister.setManagerInviteCode(reqRegister.getRecommendId());
						reqRegister.setRecommendId(null);
						reqRegister.setAgentId(Constants.MANAGER_AGENT_ID); // 客户经理人推荐渠道ID
					}

				}

				ResMsg_User_Regist resRegister = appIndexBusinessService.appIndexRegister(reqRegister);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resRegister.getResCode())) {
					BeanUtils.copyProperties(resRegister, reResponse);
					reResponse.setAgentId(resRegister.getAgentId());
				}
				else {
					throw new OpenException(resRegister.getResCode(),resRegister.getResMsg());
				}
			}
		});
	}


























	@ResponseBody
	@RequestMapping("login/v_1.0.0")
	public String login(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, LoginRequest.class, new ControllerCallBack(){

			@Override
			public void doCheck(Request request, Response response) {
				LoginRequest lgRequest = (LoginRequest) request;
				String password = lgRequest.getPassword();
				if(!CheckUtil.ParamCheck(lgRequest, "mobile","password")) {
					throw new OpenException(OpenMessageEnum.MOBILE_OR_PASSWORD_EMPTY.getCode(),OpenMessageEnum.MOBILE_OR_PASSWORD_EMPTY.getMessage());
				}
				if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(password, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				LoginResponse lgResponse = (LoginResponse) response;
				LoginRequest lgRequest = (LoginRequest) request;
				String mobile = lgRequest.getMobile();
				String password = lgRequest.getPassword();
				ReqMsg_User_Login reqLogin = new ReqMsg_User_Login();
				reqLogin.setVersion("1.0.0");
				reqLogin.setNick(mobile);
				reqLogin.setPassword(password);
				ResMsg_User_Login resLogin = appIndexBusinessService.appIndexLogin(reqLogin);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resLogin.getResCode())) {
					BeanUtils.copyProperties(resLogin, lgResponse);
					if("qianbao".equals(resLogin.getAgentId())) {
						lgResponse.setAgentName("qianbao");
					}
				}
				else {
					throw new OpenException(resLogin.getResCode(),resLogin.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("register/v_1.0.0")
	public String register(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, RegisterRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				RegisterRequest reRequest = (RegisterRequest) request;
				String mobile = reRequest.getMobile();
				String mobileCode = reRequest.getMobileCode();
				String password = reRequest.getPassword();
				String recommendId = reRequest.getRecommendId();
				if(!CheckUtil.ParamCheck(reRequest, "mobile","mobileCode","password")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(mobile.length() != 11 || !CheckUtil.RegCheck(mobile, "^[1][34587]\\d{9}$")) {
					throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
				}
				if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(mobile, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}
				if(!CheckUtil.RegCheck(mobileCode, "^[0-9]*$")) {
					throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
				}

				if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(mobile, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}

				if(recommendId != null || !"".equals(recommendId)) {
					if(!CheckUtil.RegCheck(String.valueOf(recommendId), "^[0-9a-zA-Z_]+$")) {
						throw new OpenException(OpenMessageEnum.RECOMMENT_NOT_NORM.getCode(),OpenMessageEnum.RECOMMENT_NOT_NORM.getMessage());
					}
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				RegisterResponse reResponse = (RegisterResponse) response;
				RegisterRequest reRequest = (RegisterRequest) request;
				ReqMsg_User_Regist reqRegister = new ReqMsg_User_Regist();
				BeanUtils.copyProperties(reRequest, reqRegister);
				reqRegister.setRegTerminal(reRequest.getAppClient());
				reqRegister.setVersion("1.0.0");

	            if (reqRegister.getRecommendId() == null || "".equals(reqRegister.getRecommendId())) {
	            	reqRegister.setRecommendId(null);
	            } else {
	            	//判断是否是销售人员推荐（根据邀请码判断）
	                if(!reqRegister.getRecommendId().matches("[0-9]+")){//不是数字就设置null
	                	reqRegister.setInviteCode(reqRegister.getRecommendId());//把邀请码存下来
	                	reqRegister.setRecommendId(null);
	                	reqRegister.setAgentId(31); // 销售人员推荐渠道ID
	                }
	    			//计算推荐人ID
	                if(null != reqRegister.getRecommendId() && reqRegister.getRecommendId().length()!= 11){
	                	reqRegister.setRecommendId(Util.getUserIdByInvitationCode(reqRegister.getRecommendId()).toString());
	                	reqRegister.setAgentId(null);
	                }else if (null != reqRegister.getRecommendId() && reqRegister.getRecommendId().length()== 11) {
	                	reqRegister.setManagerInviteCode(reqRegister.getRecommendId());
	                	reqRegister.setRecommendId(null);
	                	reqRegister.setAgentId(Constants.MANAGER_AGENT_ID); // 客户经理人推荐渠道ID
					}

	            }

				ResMsg_User_Regist resRegister = appIndexBusinessService.appIndexRegister(reqRegister);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resRegister.getResCode())) {
					BeanUtils.copyProperties(resRegister, reResponse);
				}
				else {
					throw new OpenException(resRegister.getResCode(),resRegister.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("sendCode/v_1.0.0")
	public String sendCode(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, SendCodeRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				SendCodeRequest smsRequest = (SendCodeRequest) request;
				String mobile = smsRequest.getMobile();
				if(StringUtil.isBlank(mobile)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(mobile.length() != 11 || !CheckUtil.RegCheck(mobile, "^[1][34587]\\d{9}$")) {
					throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				SendCodeRequest smsRequest = (SendCodeRequest) request;
				ReqMsg_SMS_Generate reqSms = new ReqMsg_SMS_Generate();
				reqSms.setVersion("1.0.0");
				reqSms.setMobile(smsRequest.getMobile());
				ResMsg_SMS_Generate respSms = appIndexBusinessService.appIndexSendCode(reqSms);
				if(!Constants.SEND_CODE_ERROR.equals(respSms.getMobileCode())) {
					if(!ConstantUtil.BSRESCODE_SUCCESS.equals(respSms.getResCode())) {
						throw new OpenException(respSms.getResCode(),respSms.getResMsg());
					}
				}
				else {
					throw new OpenException(OpenMessageEnum.VERIFYCODE_ERROR.getCode(),OpenMessageEnum.VERIFYCODE_ERROR.getMessage());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("index/v_1.0.0")
	public String indexPage(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, IndexRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				String userType = indexRequest.getUserType();
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				IndexResponse indexResponse = (IndexResponse) response;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				indexResponse.setCurrDateTime(format.format(new Date()));

				ReqMsg_Home_InfoQuery req = new ReqMsg_Home_InfoQuery();
				req.setVersion("1.0.0");
				ResMsg_Home_InfoQuery res = appIndexBusinessService.appIndexIndexPage(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					indexResponse.setInvestNum(res.getInvestNum());
					indexResponse.setCurrTotalAmount(res.getCurrTotalAmount().intValue());
					DecimalFormat df = new DecimalFormat("0.00");
					indexResponse.setTotalIncome(df.format(res.getTotalIncome()));
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}

				ReqMsg_Product_ListQuery listReq = new ReqMsg_Product_ListQuery();
				listReq.setVersion("1.0.0");
				listReq.setUserType(indexRequest.getUserType());
				ResMsg_Product_ListQuery listRes = appProductBusinessService.appProductProductList(listReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(listRes.getResCode())) {
					Map<String,Object> minMap = listRes.getProductLst().get(0);
					Map<String,Object> maxMap = listRes.getProductLst().get(listRes.getProductLst().size()-1);
					indexResponse.setMinRete((String)minMap.get("rate"));
					indexResponse.setMaxRate((String)maxMap.get("rate"));
				}
				else {
					throw new OpenException(listRes.getResCode(),listRes.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("index/v_1.0.1")
	public String indexPage_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, IndexRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				String userType = indexRequest.getUserType();
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				IndexResponse indexResponse = (IndexResponse) response;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				indexResponse.setCurrDateTime(format.format(new Date()));

				ReqMsg_Home_InfoQuery req = new ReqMsg_Home_InfoQuery();
				req.setVersion("1.0.0");
				ResMsg_Home_InfoQuery res = appIndexBusinessService.appIndexIndexPage(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					indexResponse.setInvestNum(res.getInvestNum());
					indexResponse.setCurrTotalAmount(res.getCurrTotalAmount().intValue());
					DecimalFormat df = new DecimalFormat("0.00");
					indexResponse.setTotalIncome(df.format(res.getTotalIncome()));
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}

				ReqMsg_Product_ListQuery listReq = new ReqMsg_Product_ListQuery();
				listReq.setVersion("1.0.0");
				listReq.setUserType(indexRequest.getUserType());
				ResMsg_Product_ListQuery listRes = appProductBusinessService.appProductProductList(listReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(listRes.getResCode())) {
					Map<String,Object> minMap = listRes.getProductLst().get(0);
					Map<String,Object> maxMap = listRes.getProductLst().get(listRes.getProductLst().size()-1);
					indexResponse.setMinRete((String)minMap.get("rate"));
					indexResponse.setMaxRate((String)maxMap.get("rate"));
				}
				else {
					throw new OpenException(listRes.getResCode(),listRes.getResMsg());
				}

				ReqMsg_AppActive_FindPublishTime publishReq = new ReqMsg_AppActive_FindPublishTime();
				publishReq.setVersion("1.0.0");
				ResMsg_AppActive_FindPublishTime publishRes = appIndexBusinessService.findPublishTime(publishReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(publishRes.getResCode())) {
					if(publishRes.getPublishTime() != null) {
						indexResponse.setPublishTime(format.format(publishRes.getPublishTime()));
					}
					else {
						indexResponse.setPublishTime("");
					}
				}
				else {
					throw new OpenException(publishRes.getResCode(),publishRes.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("index/v_1.0.2")
	public String indexPage_2(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, IndexRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				String userType = indexRequest.getUserType();
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				IndexResponse indexResponse = (IndexResponse) response;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				//最大和最小的年化收益率
				ReqMsg_Product_FindRate rateReq = new ReqMsg_Product_FindRate();
				rateReq.setVersion("1.0.0");
				rateReq.setUserType(indexRequest.getUserType());
				rateReq.setProductShowTerminal("APP");
				ResMsg_Product_FindRate rateRes = appProductBusinessService.appProductFindRate(rateReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(rateRes.getResCode())) {
					indexResponse.setMinRete(rateRes.getMinRate());
					indexResponse.setMaxRate(rateRes.getMaxRate());
				}
				else {
					throw new OpenException(rateRes.getResCode(),rateRes.getResMsg());
				}

				//推荐产品列表
				ReqMsg_Product_FindSuggest suggestReq = new ReqMsg_Product_FindSuggest();
				suggestReq.setVersion("1.0.0");
				suggestReq.setUserType(indexRequest.getUserType());
				suggestReq.setProductShowTerminal("APP");
				ResMsg_Product_FindSuggest suggestRes = appProductBusinessService.appProductFindSuggest(suggestReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(suggestRes.getResCode())) {
					List<Product> dataList = new ArrayList<Product>();
					for(Map<String,Object> map : suggestRes.getProductLst()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setLeftAmount((Double)map.get("leftAmount"));
						p.setPropertyType((String)map.get("propertyType"));
						dataList.add(p);
					}
					indexResponse.setDataList(dataList);
				}
				else {
					throw new OpenException(suggestRes.getResCode(),suggestRes.getResMsg());
				}

				//活动最新发布时间
				ReqMsg_AppActive_FindPublishTime publishReq = new ReqMsg_AppActive_FindPublishTime();
				publishReq.setVersion("1.0.0");
				ResMsg_AppActive_FindPublishTime publishRes = appIndexBusinessService.findPublishTime(publishReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(publishRes.getResCode())) {
					if(publishRes.getPublishTime() != null) {
						indexResponse.setPublishTime(format.format(publishRes.getPublishTime()));
					}
					else {
						indexResponse.setPublishTime("");
					}
				}
				else {
					throw new OpenException(publishRes.getResCode(),publishRes.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("index/v_1.0.3")
	public String indexPage_3(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, IndexRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				String userType = indexRequest.getUserType();
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				IndexResponse indexResponse = (IndexResponse) response;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				//最大和最小的年化收益率
				ReqMsg_Product_FindRate rateReq = new ReqMsg_Product_FindRate();
				rateReq.setVersion("1.0.0");
				rateReq.setUserType(indexRequest.getUserType());
				rateReq.setProductShowTerminal("APP");
				ResMsg_Product_FindRate rateRes = appProductBusinessService.appProductFindRate(rateReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(rateRes.getResCode())) {
					indexResponse.setMinRete(rateRes.getMinRate());
					indexResponse.setMaxRate(rateRes.getMaxRate());
				}
				else {
					throw new OpenException(rateRes.getResCode(),rateRes.getResMsg());
				}

				//推荐产品列表
				ReqMsg_Product_FindSuggest suggestReq = new ReqMsg_Product_FindSuggest();
				suggestReq.setVersion("1.0.1");
				suggestReq.setUserType(indexRequest.getUserType());
				suggestReq.setProductShowTerminal("APP");
				ResMsg_Product_FindSuggest suggestRes = appProductBusinessService.appProductFindSuggest(suggestReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(suggestRes.getResCode())) {
					List<Product> dataList = new ArrayList<Product>();
					for(Map<String,Object> map : suggestRes.getProductLst()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setLeftAmount((Double)map.get("leftAmount"));
						p.setPropertyType((String)map.get("propertyType"));
						p.setActivityType((String)map.get("activityType"));
						p.setMaxSingleInvestAmount((Double)map.get("maxSingleInvestAmount"));
						p.setPropertySymbol((String)map.get("propertySymbol"));
						p.setIsSupportRedPacket((String)map.get("isSupportRedPacket"));
						dataList.add(p);
					}
					indexResponse.setDataList(dataList);
				}
				else {
					throw new OpenException(suggestRes.getResCode(),suggestRes.getResMsg());
				}

				//活动最新发布时间
				ReqMsg_AppActive_FindPublishTime publishReq = new ReqMsg_AppActive_FindPublishTime();
				publishReq.setVersion("1.0.0");
				ResMsg_AppActive_FindPublishTime publishRes = appIndexBusinessService.findPublishTime(publishReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(publishRes.getResCode())) {
					if(publishRes.getPublishTime() != null) {
						indexResponse.setPublishTime(format.format(publishRes.getPublishTime()));
					}
					else {
						indexResponse.setPublishTime("");
					}
				}
				else {
					throw new OpenException(publishRes.getResCode(),publishRes.getResMsg());
				}
			}
		});
	}

	/**
	 * 存管后改版-首页接口信息
	 * @param req
	 * @param resp
     * @return
     */
	@ResponseBody
	@RequestMapping("index/v_1.0.4")
	public String indexPage_4(HttpServletRequest req, HttpServletResponse resp) {
		
		//记录用户登录的IP地址对应的位置信息
		String request_json_str_ = req.getParameter("request_json_str_");
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,
				new JsonValueProcessorImpl());
		JSONObject jsonObject = JSONObject.fromObject(request_json_str_, config);
		Object appClientObject = jsonObject.get("appClient");
		if ("4".equals(appClientObject)) {
			//4对应ios，ios端通过首页来控制更新ip位置频率1天1次，安卓端获取应用列表时获取ip
			String userIdStr = "";
			Object userIdTemp = jsonObject.get("userId");
			if(userIdTemp instanceof String){
				userIdStr = (String) userIdTemp;
			}else if(userIdTemp instanceof Integer){
				userIdStr = String.valueOf(userIdTemp);
			}
			CookieManager manager = new CookieManager(req);
			String ip = manager.getValue("_mobile",
					"_userIP", true);
			if (StringUtil.isBlank(ip) && !StringUtil.isBlank(userIdStr)) {
				ReqMsg_User_AppAddUserAddress addressReq = new ReqMsg_User_AppAddUserAddress();
				addressReq.setVersion("1.0.0");
				addressReq.setUserId(Integer.parseInt(userIdStr));
				ip = AddressUtil.getIp(req);
				String address = "";
				try {
					address = AddressUtil.getAddresses("ip=" + ip, "utf-8");
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				addressReq.setAddress(address);
				
				ResMsg_User_AppAddUserAddress res = (ResMsg_User_AppAddUserAddress)appIndexBusinessService.appAddUserAddress(addressReq);
				if ("000000".equals(res.getResCode())) {
					//将用户IP存入cookie，设置有效时间为1天
					CookieManager ipManager = new CookieManager(req);
					ipManager.setValue("_mobile","_userIP", ip, true);
					ipManager.save(resp, "_mobile", null, "/",
							24 * 3600, true);
				}
			}
		}
		
		return this.execute(req, IndexRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				String userType = indexRequest.getUserType();
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				IndexRequest indexRequest = (IndexRequest) request;
				IndexResponse indexResponse = (IndexResponse) response;
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

				Integer userId = com.pinting.core.util.StringUtil.isNotBlank(indexRequest.getUserId()) ? Integer.parseInt(indexRequest.getUserId()) : null;
				//推荐产品列表
				ReqMsg_Product_FindSuggest suggestReq = new ReqMsg_Product_FindSuggest();
				suggestReq.setVersion("1.0.2");
				suggestReq.setUserType(indexRequest.getUserType());
				suggestReq.setProductShowTerminal("APP");
				ResMsg_Product_FindSuggest suggestRes = appProductBusinessService.appProductFindSuggest(suggestReq);

				//判断是否有限时活动标，是则取该活动标
				//否则判断是否有新手标，并且判断是否是新手，若是则取该新手标
				//否则取普通标
				List<Map<String, Object>> prods = suggestRes.getProductLst();
				Map<String, Object> activityProduct = prods.get(0);
				Map<String, Object> newProduct = prods.get(1);
				Map<String, Object> normalProduct = prods.get(2);
				Map<String, Object> result;
				List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
				if(activityProduct == null) {
					if(newProduct == null) {
						result = normalProduct;
					} else {
						if(null != userId) {
							ReqMsg_Product_NewBuyer req = new ReqMsg_Product_NewBuyer();
							req.setUserId(userId);
							req.setVersion("1.0.0");
							ResMsg_Product_NewBuyer res = appProductBusinessService.appNewBuyer(req);
							if(res.getInvestCount() > 0) {
								result = normalProduct;
							} else {
								result = newProduct;
							}
						} else {
							result = normalProduct;
						}
					}
				} else {
					result = activityProduct;
				}
				resultList.add(result);
				suggestRes.setProductLst(resultList);

				// 是否存在红包
				if ("TRUE".equals(result.get("isSupportRedPacket"))) {
					Integer term = 1;
					if ("30".equals(result.get("term"))) {
						term = 1;
					} else if ("90".equals(result.get("term"))) {
						term = 3;
					} else if ("180".equals(result.get("term"))) {
						term = 6;
					} else if ("365".equals(result.get("term"))) {
						term = 12;
					} else {
						term = Integer.valueOf((String) result.get("term"));
					}
					ResMsg_Product_IsExistRedPacket isExistRedPacketRes = null;
					if(null != userId) {
						ReqMsg_Product_IsExistRedPacket isExistRedPacketReq = new ReqMsg_Product_IsExistRedPacket();
						isExistRedPacketReq.setUserId(userId);
						isExistRedPacketReq.setTerm(term);
						isExistRedPacketReq.setVersion("1.0.0");
						isExistRedPacketRes = appProductBusinessService.appProductIsExistRedPacket(isExistRedPacketReq);
						suggestRes.setIsExistRedPacket(isExistRedPacketRes.getIsExistRedPacket());
					}
					if (null != isExistRedPacketRes && null != isExistRedPacketRes.getIsExistRedPacket()) {
						suggestRes.setIsExistRedPacket(isExistRedPacketRes.getIsExistRedPacket());
					} else {
						suggestRes.setIsExistRedPacket("no");
					}
				}

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(suggestRes.getResCode())) {
					indexResponse.setIsExistRedPacket(suggestRes.getIsExistRedPacket());
					List<Product> dataList = new ArrayList<Product>();
					for(Map<String,Object> map : suggestRes.getProductLst()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setLeftAmount((Double)map.get("leftAmount"));
						p.setPropertyType((String)map.get("propertyType"));
						p.setActivityType((String)map.get("activityType"));
						p.setMaxSingleInvestAmount((Double)map.get("maxSingleInvestAmount"));
						p.setPropertySymbol((String)map.get("propertySymbol"));
						p.setIsSupportRedPacket((String)map.get("isSupportRedPacket"));
						Integer status = (Integer)map.get("status");
						String flag = "";
						if (Constants.PRODUCT_STATUS_PUBLISH_YES == status) {
							flag = "countdown";
						} else if (Constants.PRODUCT_STATUS_FINISH == status) {
							flag = "finish";
						} else if (Constants.PRODUCT_STATUS_OPENING == status) {
							flag = "buy";
						}
						p.setFlag(flag);
						p.setStartTime((String)map.get("startTime"));
						p.setEndTime((String)map.get("endTime"));
						p.setRemindTagContent(null != map.get("remindTagContent") ? (String)map.get("remindTagContent") : "");
						p.setInterestRatesTagContent(null != map.get("interestRatesTagContent") ? (String)map.get("interestRatesTagContent") : "");
						dataList.add(p);
					}
					indexResponse.setDataList(dataList);
				}
				else {
					throw new OpenException(suggestRes.getResCode(),suggestRes.getResMsg());
				}

				//活动最新发布时间
				ReqMsg_AppActive_FindPublishTime publishReq = new ReqMsg_AppActive_FindPublishTime();
				publishReq.setVersion("1.0.0");
				ResMsg_AppActive_FindPublishTime publishRes = appIndexBusinessService.findPublishTime(publishReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(publishRes.getResCode())) {
					if(publishRes.getPublishTime() != null) {
						indexResponse.setPublishTime(format.format(publishRes.getPublishTime()));
					}
					else {
						indexResponse.setPublishTime("");
					}
				}
				else {
					throw new OpenException(publishRes.getResCode(),publishRes.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("checkMobile/v_1.0.0")
	public String checkMobile(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, CheckMobileRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				CheckMobileRequest cmRequest = (CheckMobileRequest) request;
				String mobile = cmRequest.getMobile();
				if(StringUtil.isBlank(mobile)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(mobile.length() != 11 || !CheckUtil.RegCheck(mobile, "^[1][34587]\\d{9}$")) {
					throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				CheckMobileRequest cmRequest = (CheckMobileRequest) request;
				CheckMobileResponse cmResponse = (CheckMobileResponse) response;
				ReqMsg_User_InfoValidation req = new ReqMsg_User_InfoValidation();
				req.setVersion("1.0.0");
				req.setMobile(cmRequest.getMobile().trim());
				ResMsg_User_InfoValidation res = appIndexBusinessService.appIndexCheckMobile(req);
				//手机号未注册
				if("910012".equals(res.getResCode())) {
					cmResponse.setExist(false);
				}
				//手机号已注册
				else if("910005".equals(res.getResCode())) {
					cmResponse.setExist(true);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("checkMobile/v_1.0.1")
	public String checkMobileOrNick(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, CheckMobileRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				CheckMobileRequest cmRequest = (CheckMobileRequest) request;
				String mobile = cmRequest.getMobile();
				if(StringUtil.isBlank(mobile)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				CheckMobileRequest cmRequest = (CheckMobileRequest) request;
				CheckMobileResponse cmResponse = (CheckMobileResponse) response;
				String mobile = cmRequest.getMobile().trim();
				ReqMsg_User_InfoValidation req = new ReqMsg_User_InfoValidation();
				req.setVersion("1.0.0");
				if(CheckUtil.RegCheck(mobile, "^[1][34587]\\d{9}$")) {
					req.setMobile(mobile);
				}
				else {
					req.setNick(mobile);
				}
				ResMsg_User_InfoValidation res = appIndexBusinessService.appIndexCheckMobile(req);
				//手机号未注册
				if("910012".equals(res.getResCode())) {
					cmResponse.setExist(false);
				}
				//手机号已注册
				else if("910005".equals(res.getResCode())) {
					cmResponse.setExist(true);
				}
				//昵称已注册
				else if("910006".equals(res.getResCode())) {
					cmResponse.setExist(true);
				}
				//用户状态冻结
				else if("910101".equals(res.getResCode())) {
					cmResponse.setExist(true);
				}
				//昵称未注册
				else {
					cmResponse.setExist(false);
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("sendCodeByID/v_1.0.0")
	public String sendCodeByID(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, SendCodeByIDRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				SendCodeByIDRequest scRequest = (SendCodeByIDRequest) request;
				Integer userId = scRequest.getUserId();
				if(userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				SendCodeByIDRequest scRequest = (SendCodeByIDRequest) request;
				Integer userId = scRequest.getUserId();
				ReqMsg_User_InfoQuery reqUserInfo = new ReqMsg_User_InfoQuery();
				reqUserInfo.setVersion("1.0.0");
				reqUserInfo.setUserId(userId);
				ResMsg_User_InfoQuery resUserInfo = appIndexBusinessService.appIndexUserInfo(reqUserInfo);
				if(!ConstantUtil.BSRESCODE_SUCCESS.equals(resUserInfo.getResCode())) {
					throw new OpenException(resUserInfo.getResCode(),resUserInfo.getResMsg());
				}
				ReqMsg_SMS_Generate reqSms = new ReqMsg_SMS_Generate();
				reqSms.setVersion("1.0.0");
				reqSms.setMobile(resUserInfo.getMobile());
				ResMsg_SMS_Generate respSms = appIndexBusinessService.appIndexSendCode(reqSms);
				if(!Constants.SEND_CODE_ERROR.equals(respSms.getMobileCode())) {
					if(!ConstantUtil.BSRESCODE_SUCCESS.equals(respSms.getResCode())) {
						throw new OpenException(respSms.getResCode(),respSms.getResMsg());
					}
				}
				else {
					throw new OpenException(OpenMessageEnum.VERIFYCODE_ERROR.getCode(),OpenMessageEnum.VERIFYCODE_ERROR.getMessage());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("forgetPassword/v_1.0.0")
	public String forgetPassword(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ForgetPasswordRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ForgetPasswordRequest fpRequest = (ForgetPasswordRequest) request;
				String mobile = fpRequest.getMobile();
				String mobileCode = fpRequest.getMobileCode();
				String password = fpRequest.getPassword();
				if(!CheckUtil.ParamCheck(fpRequest, "mobile","mobileCode","password")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(mobile.length() != 11 || !CheckUtil.RegCheck(mobile, "^[1][34587]\\d{9}$")) {
					throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
				}
				if(password.length() < 6 || password.length() > 16 || !CheckUtil.RegCheck(mobile, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}
				if(!CheckUtil.RegCheck(mobileCode, "^[0-9]*$")) {
					throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ForgetPasswordRequest fpRequest = (ForgetPasswordRequest) request;
				ReqMsg_User_FindPassword req = new ReqMsg_User_FindPassword();
				req.setVersion("1.0.0");
				req.setMobile(fpRequest.getMobile());
				req.setMobileCode(fpRequest.getMobileCode());
				req.setPassword(fpRequest.getPassword());
				ResMsg_User_FindPassword resp = appIndexBusinessService.appIndexForgetPassword(req);
				if(!ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
					throw new OpenException(resp.getResCode(),resp.getResMsg());
				}
			}
		});
	}

	//账户安全中心_密码修改
	@ResponseBody
	@RequestMapping("password_change/v_1.0.0")
	public String passwordChange(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, PasswordChangeRequest.class, new ControllerCallBack(){
			@Override
			public void doCheck(Request request, Response response) {
				/**
				 * 	private Integer userId;  //用户ID

				 */
				PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest) request;
				Integer userId = passwordChangeRequest.getUserId();
				String oldPassWord = passwordChangeRequest.getOldPassWord();
				String newPassWord = passwordChangeRequest.getNewPassWord();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}

				if(!CheckUtil.ParamCheck(passwordChangeRequest, "userId","oldPassWord","newPassWord")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}

				if(oldPassWord.length() < 6 || oldPassWord.length() > 16 || !CheckUtil.RegCheck(oldPassWord, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}

				if(newPassWord.length() < 6 || newPassWord.length() > 16 || !CheckUtil.RegCheck(newPassWord, "^[0-9a-zA-Z_]+$")) {
					throw new OpenException(OpenMessageEnum.PASSWORD_NOT_NORM.getCode(),OpenMessageEnum.PASSWORD_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				PasswordChangeResponse passwordChangeResponse = (PasswordChangeResponse) response;
				PasswordChangeRequest passwordChangeRequest = (PasswordChangeRequest) request;
				//参数设置
				ReqMsg_Profile_PasswordModify reqPasswordModify = new ReqMsg_Profile_PasswordModify();
				reqPasswordModify.setVersion("1.0.0");
				reqPasswordModify.setUserId(passwordChangeRequest.getUserId());
				reqPasswordModify.setOldPassWord(passwordChangeRequest.getOldPassWord());
				reqPasswordModify.setNewPassWord(passwordChangeRequest.getNewPassWord());
				//查询操作
				ResMsg_Profile_PasswordModify resPasswordModify = (ResMsg_Profile_PasswordModify) appIndexBusinessService.appIndexPasswordChange(reqPasswordModify);
				//返回数据处理
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resPasswordModify.getResCode())) {
					BeanUtils.copyProperties(resPasswordModify, passwordChangeResponse);
				}
				else {
					passwordChangeResponse.setSuccess(false);
					throw new OpenException(resPasswordModify.getResCode(),resPasswordModify.getResMsg());
				}

			}
		});
	}

	//注册红包查询
	@ResponseBody
	@RequestMapping("registerRedPacket/v_1.0.0")
	public String registerRedPacket(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, RegisterRedPacketRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				RegisterRedPacketRequest rpRequest = (RegisterRedPacketRequest) request;
				Integer userId = rpRequest.getUserId();
				if (userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				RegisterRedPacketRequest rpRequest = (RegisterRedPacketRequest) request;
				RegisterRedPacketResponse rpResponse = (RegisterRedPacketResponse) response;

				//发送注册自动红包
				ReqMsg_AutoRedPacket_RedPacketSend redSendRequest = new ReqMsg_AutoRedPacket_RedPacketSend();
				redSendRequest.setUserId(rpRequest.getUserId());
				redSendRequest.setVersion("1.0.0");
				redSendRequest.setTriggerType("NEW_USER");
				appIndexBusinessService.sendRegisterRedPacket(redSendRequest);

				//发送推荐自动红包
				redSendRequest.setTriggerType("INVITE_FULL");
				appIndexBusinessService.sendRegisterRedPacket(redSendRequest);

				//查询注册红包
				ReqMsg_RedPacketInfo_GetUserRegistRedPakt redQueryRequest = new ReqMsg_RedPacketInfo_GetUserRegistRedPakt();
				redQueryRequest.setUserId(rpRequest.getUserId());
				redQueryRequest.setVersion("1.0.0");
				ResMsg_RedPacketInfo_GetUserRegistRedPakt redResponse = appIndexBusinessService.appIndexRedPacket(redQueryRequest);
				List<Map<String, Object>> temp = redResponse.getUserRegistRedPakts();
				Double redPacketAmount = 0d;
				for(Map<String, Object> map : temp) {
					redPacketAmount = MoneyUtil.add(redPacketAmount, (Double)map.get("subtract")).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
				}
				rpResponse.setRedPacketAmount(redPacketAmount);
			}
		});
	}

	//首页动态获得banner
	@ResponseBody
	@RequestMapping("bannerList/v_1.0.0")
	public String bannerList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BannerListRequest.class, new ControllerCallBack() {

			@Override
			public void doOperation(Request request, Response response) {
				BannerListResponse bannerResponse = (BannerListResponse) response;
				List<BannerVO> temp = new ArrayList<BannerVO>();
				ReqMsg_BannerConfig_getList req = new ReqMsg_BannerConfig_getList();
				req.setbChannel("APP");
				req.setVersion("1.0.0");
				ResMsg_BannerConfig_getList res = appIndexBusinessService.bannerList(req);
				List<HashMap<String, Object>> list = res.getBannerList();
				if(list != null) {
					for(HashMap<String, Object> map : list) {
						BannerVO banner = new BannerVO();
						banner.setUrl((String)map.get("url"));
						banner.setImage(GlobEnvUtil.get("app.banner.base.url")+(String)map.get("imgPath"));
						temp.add(banner);
					}
				}
				bannerResponse.setDataList(temp);
			}

		});
	}

	//app活动列表
	@ResponseBody
	@RequestMapping("appActiveList/v_1.0.0")
	public String activeList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, AppActiveListRequest.class, new ControllerCallBack() {

			@Override
			public void doOperation(Request request, Response response) {
				AppActiveListResponse activeResponse = (AppActiveListResponse) response;
				ReqMsg_AppActive_AppActiveList req = new ReqMsg_AppActive_AppActiveList();
				req.setVersion("1.0.0");
				ResMsg_AppActive_AppActiveList res = appIndexBusinessService.activeList(req);
				List<Map<String, Object>> list = res.getActiveList();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				List<ActiveVO> dataList = new ArrayList<ActiveVO>();
				for(Map<String, Object> map : list) {
					Date startTime = (Date)map.get("startTime");
					Date endTime = (Date)map.get("endTime");
					Date publishTime = (Date)map.get("publishTime");
					ActiveVO vo = new ActiveVO();
					vo.setEndTime(format.format(endTime));
					vo.setImgUrl(GlobEnvUtil.get("app.banner.base.url")+(String)map.get("imgUrl"));
					vo.setPublishTime(format.format(publishTime));
					vo.setStartTime(format.format(startTime));
					vo.setStatus((Integer)map.get("status"));
					vo.setSummary((String)map.get("summary"));
					vo.setTitle((String)map.get("title"));
					vo.setUrl((String)map.get("url"));
					vo.setId((Integer)map.get("id"));
					dataList.add(vo);
				}
				activeResponse.setDataList(dataList);
			}

		});
	}

	//平台数据
	@ResponseBody
	@RequestMapping("platformData/v_1.0.0")
	public String platformData(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, PlatformDataRequest.class, new ControllerCallBack() {

			@Override
			public void doOperation(Request request, Response response) {
				PlatformDataResponse dataResponse = (PlatformDataResponse) response;
				ReqMsg_Invest_PlatformStatistics req = new ReqMsg_Invest_PlatformStatistics();
				req.setVersion("1.0.0");
				ResMsg_Invest_PlatformStatistics res = appIndexBusinessService.platformData(req);
				dataResponse.setInvestInterestWill(res.getInvestInterestWill());
				dataResponse.setTotalInterestAmount(res.getTotalInterestAmount());
				dataResponse.setTotalInvestAmount(res.getTotalInvestAmount());
				dataResponse.setTotalRegUser(res.getTotalRegUser());
				List<InvestTotalGroupByProductVO> productList = new ArrayList<InvestTotalGroupByProductVO>();
				for(Map<String, Object> map : res.getInvestTotalGroupByProduct()) {
					InvestTotalGroupByProductVO vo = new InvestTotalGroupByProductVO();
					vo.setProductName((String)map.get("productName"));
					vo.setInvestTotalGroupByProductAmountString((String)map.get("investTotalGroupByProductAmountString"));
					productList.add(vo);
				}
				dataResponse.setInvestTotalGroupByProduct(productList);
				List<TotalInvestVO> monthList = new ArrayList<TotalInvestVO>();
				for(Map<String, Object> map : res.getTotalInvestGroupByMonth()) {
					TotalInvestVO vo = new TotalInvestVO();
					vo.setInvestMonth((String)map.get("investMonth"));
					vo.setTotalInvestString((String)map.get("totalInvestString"));
					monthList.add(vo);
				}
				dataResponse.setTotalInvestGroupByMonth(monthList);
			}

		});
	}

	//平台数据
	@ResponseBody
	@RequestMapping("platformData/v_1.0.1")
	public String platformData_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, PlatformDataRequest.class, new ControllerCallBack() {

			@Override
			public void doOperation(Request request, Response response) {
				PlatformDataResponse dataResponse = (PlatformDataResponse) response;
				ReqMsg_Invest_PlatformStatistics req = new ReqMsg_Invest_PlatformStatistics();
				req.setVersion("1.0.1");
				ResMsg_Invest_PlatformStatistics res = appIndexBusinessService.platformData(req);
				dataResponse.setInvestInterestWill(res.getInvestInterestWill());
				dataResponse.setTotalInterestAmount(res.getTotalInterestAmount());
				dataResponse.setTotalInvestAmount(res.getTotalInvestAmount());
				dataResponse.setTotalRegUser(res.getTotalRegUser());
				//当年每月的历史累计投资总金额
				List<InvestMentOverDateMonthVO> totalInvestList = new ArrayList<InvestMentOverDateMonthVO>();
				for(Map<String, Object> map : res.getInvestMentOverDateMonth()) {
					InvestMentOverDateMonthVO vo = new InvestMentOverDateMonthVO();
					vo.setInvestMonth((String)map.get("investMonth"));
					vo.setTotalInvestOverMonthString((String)map.get("totalInvestOverMonthString"));
					totalInvestList.add(vo);
				}
				dataResponse.setTotalInvestList(totalInvestList);
				//根据普通用户查询产品平均收益率
				dataResponse.setAverageInvestRateNormal(res.getAverageInvestRateNormal());
				//按照产品投资期限长短查询
				List<InvestTotalGroupByProductVO> productList = new ArrayList<InvestTotalGroupByProductVO>();
				for(Map<String, Object> map : res.getInvestTotalGroupByProductTerm()) {
					InvestTotalGroupByProductVO vo = new InvestTotalGroupByProductVO();
					vo.setInvestTotalGroupByProductAmountString((String)map.get("investTotalGroupByProductAmountString"));
					vo.setProductName((String)map.get("productName"));
					productList.add(vo);
				}
				dataResponse.setInvestTotalGroupByProduct(productList);
				//投资人性别
				List<InvestorTypeVO> sexList = new ArrayList<InvestorTypeVO>();
				for(Map<String, Object> map : res.getInvestorTypeSex()) {
					InvestorTypeVO vo = new InvestorTypeVO();
					vo.setInvestorTypeName((String)map.get("investorTypeName"));
					vo.setInvestorTypeNumberString((String)map.get("investorTypeNumberString"));
					sexList.add(vo);
				}
				dataResponse.setSexList(sexList);
				//投资人年龄
				List<InvestorTypeVO> ageList = new ArrayList<InvestorTypeVO>();
				for(Map<String, Object> map : res.getInvestorTypeAge()) {
					InvestorTypeVO vo = new InvestorTypeVO();
					vo.setInvestorTypeName((String)map.get("investorTypeName"));
					vo.setInvestorTypeRateString((String)map.get("investorTypeRateString"));
					ageList.add(vo);
				}
				dataResponse.setAgeList(ageList);
			}

		});
	}




	//更新用户APP版本号
	@ResponseBody
	@RequestMapping("appVersion/v_1.0.0")
	public String appVersion(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, AppVersionRequest.class,
				new ControllerCallBack() {

					@Override
					public void doCheck(Request request, Response response) {
						AppVersionRequest appVersionRequest = (AppVersionRequest) request;
						Integer userId = appVersionRequest.getUserId();
						if (userId == null) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
						if (StringUtil.isBlank(appVersionRequest.getAppVersion())) {
							throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
						}
					}

					@Override
					public void doOperation(Request request, Response response) {
						AppVersionResponse appVersionResponse = (AppVersionResponse) response;
						AppVersionRequest appVersionRequest = (AppVersionRequest) request;

						// 查询
						ReqMsg_User_AppVersion  reqAppVersion = new ReqMsg_User_AppVersion();
						reqAppVersion.setUserId(appVersionRequest.getUserId());

						if (appVersionRequest.getAppClient() == 4) {
							reqAppVersion.setAppVersion("ios_"+appVersionRequest.getAppVersion());
						}else if (appVersionRequest.getAppClient() == 3) {
							reqAppVersion.setAppVersion("android_"+appVersionRequest.getAppVersion());
						}else {
							reqAppVersion.setAppVersion(appVersionRequest.getAppVersion());
						}


						reqAppVersion.setVersion("1.0.0");
						ResMsg_User_AppVersion resAppVersion = (ResMsg_User_AppVersion) appIndexBusinessService.appVersion(reqAppVersion);
						//返回数据处理
						if(ConstantUtil.BSRESCODE_SUCCESS.equals(resAppVersion.getResCode())) {

						}else {
							throw new OpenException(resAppVersion.getResCode(),resAppVersion.getResMsg());
						}

					}
				});
		}



	//APP启动页面
	@ResponseBody
	@RequestMapping("appStart/v_1.0.0")
	public String appStart(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, AppStartRequest.class, new ControllerCallBack() {
			@Override
			public void doOperation(Request request, Response response) {
				AppStartResponse appStartResponse = (AppStartResponse) response;
				AppStartRequest appStartRequest = (AppStartRequest) request;


				ReqMsg_BannerConfig_AppStart  req = new ReqMsg_BannerConfig_AppStart();
				req.setVersion("1.0.0");
				ResMsg_BannerConfig_AppStart res = appIndexBusinessService.appStart(req);

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					if (res.getImage() != null) {
						appStartResponse.setImage(res.getImage());
						appStartResponse.setUrl(res.getUrl());
					}else {
						appStartResponse.setImage(null);
						appStartResponse.setUrl(null);
					}
				}else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}


			}
		});
	}

	/**
	 * 存管后改版新增接口-首页消息相关（公告、活动提示红点）
	 * @param req
	 * @param res
     * @return
     */
	@ResponseBody
	@RequestMapping("indexMessage/v_1.0.0")
	public String indexMessage(HttpServletRequest req, HttpServletResponse res) {
		return this.execute(req, IndexMessageRequest.class, new ControllerCallBack() {
			@Override
			public void doOperation(Request request, Response response) {
				IndexMessageRequest noticeRequest = (IndexMessageRequest) request;
				IndexMessageResponse noticeResponse = (IndexMessageResponse) response;

				ReqMsg_News_CurrentNews req = new ReqMsg_News_CurrentNews();
				req.setVersion("1.0.0");
				req.setUserId(noticeRequest.getUserId());
				req.setType(Constants.NEWS_TYPE_NOTICE);
				req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
				req.setShowPage(5);
				ResMsg_News_CurrentNews res = appIndexBusinessService.currentNews(req);

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Notice> noticeList = new ArrayList<Notice>();
					if(!CollectionUtils.isEmpty(res.getNews())) {
						for (HashMap<String, Object> map : res.getNews()) {
							Notice notice = new Notice();
							String id = String.valueOf(map.get("id"));
							notice.setId(Integer.parseInt(id));
							notice.setIsRead((String) map.get("isRead"));
							notice.setTitle((String) map.get("subject"));
							noticeList.add(notice);
						}
					}
					noticeResponse.setNoticeList(noticeList);
				} else {
					throw new OpenException(res.getResCode(), res.getResMsg());
				}

				ReqMsg_AppActive_IsRead isReadReq = new ReqMsg_AppActive_IsRead();
				isReadReq.setUserId(req.getUserId());
				isReadReq.setVersion("1.0.0");
				ResMsg_AppActive_IsRead isReadRes = appIndexBusinessService.activeIsRead(isReadReq);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(isReadRes.getResCode())) {
					noticeResponse.setActivityIsRead(isReadRes.getIsRead());
				} else {
					throw new OpenException(isReadRes.getResCode(), isReadRes.getResMsg());
				}
			}
		});
	}

	/**
	 * 存管后改版新增接口-活动中心红点
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("readMessage/v_1.0.0")
	public String readMessage(HttpServletRequest req, HttpServletResponse res) {
		return this.execute(req, ReadMessageRequest.class, new ControllerCallBack() {
			@Override
			public void doOperation(Request request, Response response) {
				ReadMessageRequest readRequest = (ReadMessageRequest) request;

				ReqMsg_News_ReadMessage req = new ReqMsg_News_ReadMessage();
				req.setVersion("1.0.0");
				req.setUserId(readRequest.getUserId());
				req.setType(readRequest.getPosition());
				req.setId(readRequest.getId());
				req.setReceiverType(Constants.NEWS_RECEIVER_TYPE_BGW);
				ResMsg_News_ReadMessage res = appIndexBusinessService.readMessage(req);

				if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					throw new OpenException(res.getResCode(), res.getResMsg());
				}
			}
		});
	}
	
	/**
	 * 协议名称统一接口
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("agreement/v_1.0.0")
	public String agreement(HttpServletRequest req, HttpServletResponse res) {
		return this.execute(req, AgreementRequest.class, new ControllerCallBack() {
			@Override
			public void doOperation(Request request, Response response) {
				AgreementResponse agreementResponse = (AgreementResponse) response;
				ReqMsg_Agreement_GetList req = new ReqMsg_Agreement_GetList();
				req.setVersion("1.0.0");
				ResMsg_Agreement_GetList res = appIndexBusinessService.getList(req);
				
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					agreementResponse.setRegisterAgreement(res.getRegisterAgreement());
					agreementResponse.setBuyAgreementGW(res.getBuyAgreementGW());
					agreementResponse.setRiskWarningGW(res.getRiskWarningGW());
					agreementResponse.setBuyAgreementZAN(res.getBuyAgreementZAN());
					agreementResponse.setRiskWarningZAN(res.getRiskWarningZAN());
					agreementResponse.setBuyAgreementZSD(res.getBuyAgreementZSD());
					agreementResponse.setRiskWarningZSD(res.getRiskWarningZSD());
					agreementResponse.setRegularAgreementYes(res.getRegularAgreementYes());
					agreementResponse.setRegularDetailsYes(res.getRegularDetailsYes());
					agreementResponse.setRegularAgreementNo(res.getRegularAgreementNo());
					agreementResponse.setRegularDetailsNo(res.getRegularDetailsNo());
					agreementResponse.setInstalmentAgreement(res.getInstalmentAgreement());
					agreementResponse.setInstalmentDetail(res.getInstalmentDetail());
					agreementResponse.setPaymentAgreement(res.getPaymentAgreement());
					agreementResponse.setActivateAgreement(res.getActivateAgreement());
					agreementResponse.setOpenAgreement(res.getOpenAgreement());
					//标题
					agreementResponse.setRegisterAgreementTitle(res.getRegisterAgreementTitle());
					agreementResponse.setBuyAgreementTitleGW(res.getBuyAgreementTitleGW());
					agreementResponse.setRiskWarningTitleGW(res.getRiskWarningTitleGW());
					agreementResponse.setBuyAgreementTitleZAN(res.getBuyAgreementTitleZAN());
					agreementResponse.setRiskWarningTitleZAN(res.getRiskWarningTitleZAN());
					agreementResponse.setBuyAgreementTitleZSD(res.getBuyAgreementTitleZSD());
					agreementResponse.setRiskWarningTitleZSD(res.getRiskWarningTitleZSD());
					agreementResponse.setRegularAgreementTitleYes(res.getRegularAgreementTitleYes());
					agreementResponse.setRegularDetailsTitleYes(res.getRegularDetailsTitleYes());
					agreementResponse.setRegularAgreementTitleNo(res.getRegularAgreementTitleNo());
					agreementResponse.setRegularDetailsTitleNo(res.getRegularDetailsTitleNo());
					agreementResponse.setInstalmentAgreementTitle(res.getInstalmentAgreementTitle());
					agreementResponse.setInstalmentDetailTitle(res.getInstalmentDetailTitle());
					agreementResponse.setPaymentAgreementTitle(res.getPaymentAgreementTitle());
					agreementResponse.setActivateAgreementTitle(res.getActivateAgreementTitle());
					agreementResponse.setOpenAgreementTitle(res.getOpenAgreementTitle());
				}else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}
	
	/**
	 * 记录用户已安装应用列表
	 * @param req
	 * @param res
	 * @return
	 */
	@ResponseBody
	@RequestMapping("application/v_1.0.0")
	public String application(HttpServletRequest req, HttpServletResponse res) {
		
		//记录用户登录的IP地址对应的位置信息
		//安卓端因为cookie无法传递，所以通过新接口获取ip位置，实现控制更新频率为1月1次
		String request_json_str_ = req.getParameter("request_json_str_");
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,
				new JsonValueProcessorImpl());
		JSONObject jsonObject = JSONObject.fromObject(request_json_str_, config);
		Integer userId = (Integer)jsonObject.get("userId");
		if (userId != null) {
			ReqMsg_User_AppAddUserAddress addressReq = new ReqMsg_User_AppAddUserAddress();
			addressReq.setVersion("1.0.0");
			addressReq.setUserId(userId);
			String ip = AddressUtil.getIp(req);
			String address = "";
			try {
				address = AddressUtil.getAddresses("ip=" + ip, "utf-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			addressReq.setAddress(address);
			
			appIndexBusinessService.appAddUserAddress(addressReq);
		}
				
		return this.execute(req, ApplicationRequest.class, new ControllerCallBack() {
			@Override
			public void doOperation(Request request, Response response) {
				ApplicationRequest applicationRequest = (ApplicationRequest) request;
				ReqMsg_User_AddApplication req = new ReqMsg_User_AddApplication();
				req.setVersion("1.0.0");
				req.setUserId(applicationRequest.getUserId());
				req.setApplications(applicationRequest.getApplications());
				ResMsg_User_AddApplication res = appIndexBusinessService.addApplication(req);
				
				if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					throw new OpenException(res.getResCode(), res.getResMsg());
				}
			}
		});
	}
}
