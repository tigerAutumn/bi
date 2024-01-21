package com.pinting.gateway.mobile.in.controller;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.jsoup.helper.StringUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.site.message.ReqMsg_Bank_AlreadyUseMoney;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_ChannelBank;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_Pay19BankList;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryBankBin;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryBankInfoByUser;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_QueryDefaultBank;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_bindBankList;
import com.pinting.business.hessian.site.message.ReqMsg_Product_BalanceBuyInfo;
import com.pinting.business.hessian.site.message.ReqMsg_Product_CheckBeforeBuy;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListIndexInfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListIndexInfoQuery4User;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListIndexQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_NewBuyer;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ProductListReturnType;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ProductListStatusFinish30;
import com.pinting.business.hessian.site.message.ReqMsg_Product_SaveProductInform;
import com.pinting.business.hessian.site.message.ReqMsg_Product_SelectProductInform;
import com.pinting.business.hessian.site.message.ReqMsg_Product_SingleProduct;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_ReapalQuickCMB;
import com.pinting.business.hessian.site.message.ReqMsg_User_AppAddUserAddress;
import com.pinting.business.hessian.site.message.ReqMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_ValidUser;
import com.pinting.business.hessian.site.message.ResMsg_Bank_AlreadyUseMoney;
import com.pinting.business.hessian.site.message.ResMsg_Bank_ChannelBank;
import com.pinting.business.hessian.site.message.ResMsg_Bank_Pay19BankList;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryBankBin;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryBankInfoByUser;
import com.pinting.business.hessian.site.message.ResMsg_Bank_QueryDefaultBank;
import com.pinting.business.hessian.site.message.ResMsg_Bank_bindBankList;
import com.pinting.business.hessian.site.message.ResMsg_Product_BalanceBuyInfo;
import com.pinting.business.hessian.site.message.ResMsg_Product_CheckBeforeBuy;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListIndexInfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListIndexInfoQuery4User;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListIndexQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_NewBuyer;
import com.pinting.business.hessian.site.message.ResMsg_Product_ProductListReturnType;
import com.pinting.business.hessian.site.message.ResMsg_Product_ProductListStatusFinish30;
import com.pinting.business.hessian.site.message.ResMsg_Product_SaveProductInform;
import com.pinting.business.hessian.site.message.ResMsg_Product_SelectProductInform;
import com.pinting.business.hessian.site.message.ResMsg_Product_SingleProduct;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_BalanceBuy;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_ReapalQuickCMB;
import com.pinting.business.hessian.site.message.ResMsg_User_AppAddUserAddress;
import com.pinting.business.hessian.site.message.ResMsg_User_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_ValidUser;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.json.JsonValueProcessorImpl;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.business.out.service.AppIndexBusinessService;
import com.pinting.gateway.business.out.service.AppProductBusinessService;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.mobile.in.enums.OpenMessageEnum;
import com.pinting.gateway.mobile.in.util.CheckUtil;
import com.pinting.gateway.mobile.in.util.Constants;
import com.pinting.gateway.reapal.out.config.ReapalConfig;
import com.pinting.gateway.reapal.out.enums.ReapalQuickPayRespCode;
import com.pinting.gateway.reapal.out.model.req.ResendCodeReq;
import com.pinting.gateway.reapal.out.model.resp.ResendCodeResp;
import com.pinting.gateway.reapal.out.service.QuickPayServiceClient;
import com.pinting.gateway.util.AddressUtil;
import com.pinting.gateway.util.IDCardUtil;
import com.pinting.open.base.controller.ControllerCallBack;
import com.pinting.open.base.controller.OpenController;
import com.pinting.open.base.exception.OpenException;
import com.pinting.open.base.request.Request;
import com.pinting.open.base.response.Response;
import com.pinting.open.pojo.model.product.Bank;
import com.pinting.open.pojo.model.product.Product;
import com.pinting.open.pojo.request.more.FeedbackRequest;
import com.pinting.open.pojo.request.product.BalanceBuyInfoRequest;
import com.pinting.open.pojo.request.product.BalanceBuyRequest;
import com.pinting.open.pojo.request.product.BaofooBankListRequest;
import com.pinting.open.pojo.request.product.BindBankRequest;
import com.pinting.open.pojo.request.product.BindOrderRequest;
import com.pinting.open.pojo.request.product.BindPreOrderRequest;
import com.pinting.open.pojo.request.product.CardBinRequest;
import com.pinting.open.pojo.request.product.CheckBeforeBuyRequest;
import com.pinting.open.pojo.request.product.CheckIDCardRequest;
import com.pinting.open.pojo.request.product.CheckIDCardSecondEditionRequest;
import com.pinting.open.pojo.request.product.NewBuyerRequest;
import com.pinting.open.pojo.request.product.NoBindOrderRequest;
import com.pinting.open.pojo.request.product.NoBindPreOrderRequest;
import com.pinting.open.pojo.request.product.ProductIndexRequest;
import com.pinting.open.pojo.request.product.ProductListIndexRequest;
import com.pinting.open.pojo.request.product.ProductListRequest;
import com.pinting.open.pojo.request.product.ProductListReturnTypeRequest;
import com.pinting.open.pojo.request.product.ProductListStatusFinish30Request;
import com.pinting.open.pojo.request.product.ProductSingleRequest;
import com.pinting.open.pojo.request.product.ReapalQuickCMBRequest;
import com.pinting.open.pojo.request.product.ReapalResendCodeRequest;
import com.pinting.open.pojo.request.product.SaveProductInformRequest;
import com.pinting.open.pojo.request.product.SelectProductInformRequest;
import com.pinting.open.pojo.request.product.UseMoneyTodayRequest;
import com.pinting.open.pojo.request.product.UserLastBankRequest;
import com.pinting.open.pojo.response.product.BalanceBuyInfoResponse;
import com.pinting.open.pojo.response.product.BalanceBuyResponse;
import com.pinting.open.pojo.response.product.BaofooBankListResponse;
import com.pinting.open.pojo.response.product.BindBankResponse;
import com.pinting.open.pojo.response.product.BindPreOrderResponse;
import com.pinting.open.pojo.response.product.CardBinResponse;
import com.pinting.open.pojo.response.product.CheckBeforeBuyResponse;
import com.pinting.open.pojo.response.product.CheckIDCardResponse;
import com.pinting.open.pojo.response.product.CheckIDCardSecondEditionResponse;
import com.pinting.open.pojo.response.product.NewBuyerResponse;
import com.pinting.open.pojo.response.product.NoBindPreOrderResponse;
import com.pinting.open.pojo.response.product.ProductIndexResponse;
import com.pinting.open.pojo.response.product.ProductListReturnTypeResponse;
import com.pinting.open.pojo.response.product.ProductListIndexResponse;
import com.pinting.open.pojo.response.product.ProductListResponse;
import com.pinting.open.pojo.response.product.ProductListStatusFinish30Response;
import com.pinting.open.pojo.response.product.ProductSingleResponse;
import com.pinting.open.pojo.response.product.ReapalQuickCMBResponse;
import com.pinting.open.pojo.response.product.SelectProductInformResponse;
import com.pinting.open.pojo.response.product.UseMoneyTodayResponse;
import com.pinting.open.pojo.response.product.UserLastBankResponse;

@Controller
@Scope("prototype")
@RequestMapping("mobile/product")
public class ProductController extends OpenController {

	@Autowired
	private AppProductBusinessService appProductBusinessService;

	@Autowired
	private AppIndexBusinessService appIndexBusinessService;

	@Autowired
	private QuickPayServiceClient reapalClient;

	private Logger logger = LoggerFactory.getLogger(ProductController.class);

	@ResponseBody
	@RequestMapping("productList/v_1.0.0")
	public String productList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductListRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ProductListRequest plRequest = (ProductListRequest) request;
				String userType = plRequest.getUserType();
				if(StringUtil.isBlank(userType)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ProductListRequest plRequest = (ProductListRequest) request;
				ProductListResponse plResponse = (ProductListResponse) response;
				String userType = plRequest.getUserType();
				ReqMsg_Product_ListQuery req = new ReqMsg_Product_ListQuery();
				req.setVersion("1.0.0");
				req.setUserType(userType);
				ResMsg_Product_ListQuery res = appProductBusinessService.appProductProductList(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Product> productList = new ArrayList<Product>();
					for(Map<String,Object> map : res.getProductLst()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setName(((String)map.get("name")).split("-")[0]+"(100元起投)");
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						productList.add(p);
					}
					plResponse.setDataList(productList);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("productList/v_1.0.1")
	public String productList_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductListRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ProductListRequest plRequest = (ProductListRequest) request;
				String userType = plRequest.getUserType();
				if(StringUtil.isBlank(userType)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ProductListRequest plRequest = (ProductListRequest) request;
				ProductListResponse plResponse = (ProductListResponse) response;
				String userType = plRequest.getUserType();
				ReqMsg_Product_ListQuery req = new ReqMsg_Product_ListQuery();
				req.setVersion("1.0.1");
				req.setUserType(userType);
				req.setProductShowTerminal("APP");
				req.setPage(plRequest.getPage());
				ResMsg_Product_ListQuery res = appProductBusinessService.appProductProductList(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Product> productList = new ArrayList<Product>();
					for(Map<String,Object> map : res.getProductLst()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setCurrTime((String)map.get("currTime"));
						p.setStartTime((String)map.get("startTime"));
						p.setLeftAmount((Double)map.get("leftAmount"));
						p.setPropertyType((String)map.get("propertyType"));
						if(map.get("endTime") != null) {
							p.setEndTime((String)map.get("endTime"));
						}
						if(map.get("finishTime") != null) {
							p.setFinishTime((String)map.get("finishTime"));
						}
						productList.add(p);
					}
					plResponse.setDataList(productList);
					plResponse.setCount(res.getCount());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("productList/v_1.0.2")
	public String productList_2(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductListRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ProductListRequest plRequest = (ProductListRequest) request;
				String userType = plRequest.getUserType();
				if(StringUtil.isBlank(userType)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(!(Constants.USER_TYPE_NORMAL.equals(userType)||Constants.USER_TYPE_PROMOT.equals(userType))) {
					throw new OpenException(OpenMessageEnum.USER_TYPE_ERROR.getCode(),OpenMessageEnum.USER_TYPE_ERROR.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ProductListRequest plRequest = (ProductListRequest) request;
				ProductListResponse plResponse = (ProductListResponse) response;
				String userType = plRequest.getUserType();
				ReqMsg_Product_ListQuery req = new ReqMsg_Product_ListQuery();
				req.setVersion("1.0.2");
				req.setUserType(userType);
				req.setProductShowTerminal("APP");
				req.setPage(plRequest.getPage());
				ResMsg_Product_ListQuery res = appProductBusinessService.appProductProductList(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Product> productList = new ArrayList<Product>();
					for(Map<String,Object> map : res.getProductLst()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setCurrTime((String)map.get("currTime"));
						p.setStartTime((String)map.get("startTime"));
						p.setLeftAmount((Double)map.get("leftAmount"));
						p.setPropertyType((String)map.get("propertyType"));
						p.setActivityType((String)map.get("activityType"));
						p.setMaxSingleInvestAmount((Double)map.get("maxSingleInvestAmount"));
						p.setIsSupportRedPacket((String)map.get("isSupportRedPacket"));
						if(map.get("endTime") != null) {
							p.setEndTime((String)map.get("endTime"));
						}
						if(map.get("finishTime") != null) {
							p.setFinishTime((String)map.get("finishTime"));
						}
						p.setPropertySymbol((String)map.get("propertySymbol"));
						productList.add(p);
					}
					plResponse.setDataList(productList);
					plResponse.setCount(res.getCount());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("productIndex/v_1.0.0")
	public String productIndex(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductIndexRequest.class, new ControllerCallBack() {

			@Override
			public void doOperation(Request request, Response response) {
				ProductIndexRequest productIndexRequest = (ProductIndexRequest) request;
				ProductIndexResponse piResponse = (ProductIndexResponse) response;
				ReqMsg_Bank_Pay19BankList req = new ReqMsg_Bank_Pay19BankList();
				req.setVersion("1.0.0");
				req.setUserId(productIndexRequest.getUserId());
				ResMsg_Bank_Pay19BankList res = appProductBusinessService.appProductBankList(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Bank> bankList = new ArrayList<Bank>();
					for(Map<String,Object> map : res.getBankList()) {
						Bank bank = new Bank();
						bank.setBankId((Integer)map.get("bankId"));
						bank.setBankName((String)map.get("bankName"));
						bank.setDayTop((Double)map.get("dayTop"));
						bank.setOneTop((Double)map.get("oneTop"));
						bank.setIsAvailable((Integer)map.get("isAvailable"));
						bank.setPay19BankCode((String)map.get("pay19BankCode"));
						bank.setNotice((String)map.get("notice"));
						bank.setDailyNotice((String)map.get("dailyNotice"));
						bankList.add(bank);
					}
					piResponse.setDataList(bankList);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("userLastBank/v_1.0.0")
	public String userLastBank(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, UserLastBankRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				UserLastBankRequest lastRequest = (UserLastBankRequest) request;
				Integer userId = lastRequest.getUserId();
				if(userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				UserLastBankRequest lastRequest = (UserLastBankRequest) request;
				UserLastBankResponse lastResponse = (UserLastBankResponse) response;
				ReqMsg_Bank_QueryDefaultBank req = new ReqMsg_Bank_QueryDefaultBank();
				req.setVersion("1.0.0");
				req.setUserId(lastRequest.getUserId());
				ResMsg_Bank_QueryDefaultBank res = appProductBusinessService.appProductUserLastBank(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					lastResponse.setBindBank(res.isBindBank());
					lastResponse.setAmount(res.getAmount());
					lastResponse.setAvailableBalance(res.getAvailableBalance());
					lastResponse.setBankId(res.getBankId());
					lastResponse.setBankName(res.getBankName());
					lastResponse.setCardNo(res.getCardNo());
					lastResponse.setDayTop(res.getDayTop());
					lastResponse.setIdCard(res.getIdCard());
					lastResponse.setIsFirst(res.getIsFirst());
					lastResponse.setMobile(res.getMobile());
					lastResponse.setOneTop(res.getOneTop());
					lastResponse.setSubAccountId(res.getSubAccountId());
					lastResponse.setUserName(res.getUserName());
					lastResponse.setDailyNotice(res.getDailyNotice());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("bindBank/v_1.0.0")
	public String bindBank(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BindBankRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				BindBankRequest bbRequest = (BindBankRequest) request;
				Integer userId = bbRequest.getUserId();
				if(userId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				BindBankRequest bbRequest = (BindBankRequest) request;
				BindBankResponse bbResponse = (BindBankResponse) response;
				ReqMsg_Bank_bindBankList req = new ReqMsg_Bank_bindBankList();
				req.setVersion("1.0.0");
				req.setUserId(bbRequest.getUserId());
				ResMsg_Bank_bindBankList res = appProductBusinessService.appProductBindBank(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					bbResponse.setAvailableBalance(res.getAvailableBalance());
					bbResponse.setBindBank(res.isBindBank());
					bbResponse.setSubAccountId(res.getSubAccountId());
					List<Bank> list = new ArrayList<Bank>();
					for(Map<String,Object> map : res.getBankList()) {
						Bank bank = new Bank();
						bank.setBankId((Integer)map.get("bankId"));
						bank.setBankName((String)map.get("bankName"));
						bank.setCardNo((String)map.get("cardNo"));
						bank.setIsFirst((Integer)map.get("isFirst"));
						bank.setOneTop((Double)map.get("oneTop"));
						bank.setDayTop((Double)map.get("dayTop"));
						bank.setIsAvailable((Integer)map.get("isAvailable"));
						bank.setDailyNotice((String)map.get("dailyNotice"));
						list.add(bank);
					}
					bbResponse.setDataList(list);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("useMoneyToday/v_1.0.0")
	public String useMoneyToday(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, UseMoneyTodayRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				UseMoneyTodayRequest umRequest = (UseMoneyTodayRequest) request;
				if(!CheckUtil.ParamCheck(umRequest, "userId","bankId")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				UseMoneyTodayRequest umRequest = (UseMoneyTodayRequest) request;
				UseMoneyTodayResponse umResponse = (UseMoneyTodayResponse) response;
				ReqMsg_Bank_AlreadyUseMoney req = new ReqMsg_Bank_AlreadyUseMoney();
				req.setVersion("1.0.0");
				req.setBankId(umRequest.getBankId());
				req.setUserId(umRequest.getUserId());
				ResMsg_Bank_AlreadyUseMoney res = appProductBusinessService.appProductUserMoney(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					umResponse.setAmount(res.getAmount());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("cardBin/v_1.0.0")
	public String cardBin(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, CardBinRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				CardBinRequest cbRequest = (CardBinRequest) request;
				if(!CheckUtil.ParamCheck(cbRequest, "userId","cardNo")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				if(!CheckUtil.RegCheck(cbRequest.getCardNo(), "^[0-9]*$")) {
					throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(),OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				CardBinRequest cbRequest = (CardBinRequest) request;
				CardBinResponse cbResponse = (CardBinResponse) response;
				ReqMsg_Bank_QueryBankBin req = new ReqMsg_Bank_QueryBankBin();
				req.setVersion("1.0.0");
				req.setCardNo(cbRequest.getCardNo());
				req.setUserId(cbRequest.getUserId());
				ResMsg_Bank_QueryBankBin res = appProductBusinessService.appProductBankBin(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					cbResponse.setBankId(res.getBankId());
					cbResponse.setBankCardFuncType(res.getBankCardFuncType());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("noBindPreOrder/v_1.0.0")
	public String noBindPreOrder(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, NoBindPreOrderRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				NoBindPreOrderRequest noRequest = (NoBindPreOrderRequest) request;
				Double amount = noRequest.getAmount();
				String cardNo = noRequest.getCardNo();
				String userName = noRequest.getUserName();
				String idCard = noRequest.getIdCard();
				String mobile = noRequest.getMobile();
				if(!CheckUtil.ParamCheck(noRequest, "userId","amount","cardNo","userName","idCard","mobile","productId","bankId","bankName","terminalType")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				BigDecimal b = new BigDecimal(amount);
				BigDecimal c = new BigDecimal("100");
		        BigDecimal e = new BigDecimal("0");
		        if (amount <= 0 || b.remainder(c).compareTo(e) != 0) {//金额不是100的整数倍
		            throw new OpenException(OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getMessage());
		        }
		        if(cardNo.length() > 20 || !CheckUtil.RegCheck(cardNo, "^[0-9]*$")) {
					throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(),OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
				}
		        if(userName.length() > 10) {
		        	throw new OpenException(OpenMessageEnum.USERNAME_NOT_NORM.getCode(),OpenMessageEnum.USERNAME_NOT_NORM.getMessage());
		        }
		        if(!CheckUtil.RegCheck(idCard, "(\\d{14}[0-9xX])|(\\d{17}[0-9xX])")) {
		        	throw new OpenException(OpenMessageEnum.IDCARD_NOT_NORM.getCode(),OpenMessageEnum.IDCARD_NOT_NORM.getMessage());
		        }
		        if(mobile.length() != 11 || !CheckUtil.RegCheck(mobile, "^[0-9]*$")) {
		        	throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
		        }
			}

			@Override
			public void doOperation(Request request, Response response) {
				NoBindPreOrderRequest noRequest = (NoBindPreOrderRequest) request;
				NoBindPreOrderResponse noResponse = (NoBindPreOrderResponse) response;
				ReqMsg_RegularBuy_Order req = new ReqMsg_RegularBuy_Order();
				BeanUtils.copyProperties(noRequest, req);
				req.setVersion("1.0.0");
				req.setPlaceOrder(Constants.PLACE_ORDER_PREPARE);
				req.setTransType(Constants.TRANS_TYPE_CARD_BUY);
				ResMsg_RegularBuy_Order res = appProductBusinessService.appProductPlaceOrder(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					noResponse.setOrderNo(res.getOrderNo());
					noResponse.setHtml(res.getHtml());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});

	}

	@ResponseBody
	@RequestMapping("noBindOrder/v_1.0.0")
	public String noBindOrder(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, NoBindOrderRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				NoBindOrderRequest noRequest = (NoBindOrderRequest) request;
				Double amount = noRequest.getAmount();
				String cardNo = noRequest.getCardNo();
				String userName = noRequest.getUserName();
				String idCard = noRequest.getIdCard();
				String mobile = noRequest.getMobile();
				String verifyCode = noRequest.getVerifyCode();
				if(!CheckUtil.ParamCheck(noRequest, "userId","amount","cardNo","userName","idCard","mobile","productId","bankId","bankName","orderNo","verifyCode","terminalType")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				BigDecimal b = new BigDecimal(amount);
				BigDecimal c = new BigDecimal("100");
		        BigDecimal e = new BigDecimal("0");
		        if (amount <= 0 || b.remainder(c).compareTo(e) != 0) {//金额不是100的整数倍
		            throw new OpenException(OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getMessage());
		        }
		        if(cardNo.length() > 20 || !CheckUtil.RegCheck(cardNo, "^[0-9]*$")) {
					throw new OpenException(OpenMessageEnum.CARDNO_NOT_NORM.getCode(),OpenMessageEnum.CARDNO_NOT_NORM.getMessage());
				}
		        if(userName.length() > 10) {
		        	throw new OpenException(OpenMessageEnum.USERNAME_NOT_NORM.getCode(),OpenMessageEnum.USERNAME_NOT_NORM.getMessage());
		        }
		        if(!CheckUtil.RegCheck(idCard, "(\\d{14}[0-9xX])|(\\d{17}[0-9xX])")) {
		        	throw new OpenException(OpenMessageEnum.IDCARD_NOT_NORM.getCode(),OpenMessageEnum.IDCARD_NOT_NORM.getMessage());
		        }
		        if(mobile.length() != 11 || !CheckUtil.RegCheck(mobile, "^[0-9]*$")) {
		        	throw new OpenException(OpenMessageEnum.MOBILE_NOT_NORM.getCode(),OpenMessageEnum.MOBILE_NOT_NORM.getMessage());
		        }
		        if(!CheckUtil.RegCheck(verifyCode, "^[0-9]*$")) {
		        	throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
		        }
			}

			@Override
			public void doOperation(Request request, Response response) {
				NoBindOrderRequest noRequest = (NoBindOrderRequest) request;
				ReqMsg_RegularBuy_Order req = new ReqMsg_RegularBuy_Order();
				BeanUtils.copyProperties(noRequest, req);
				req.setVersion("1.0.0");
				req.setPlaceOrder(Constants.PLACE_ORDER_FORMAL);
				req.setTransType(Constants.TRANS_TYPE_CARD_BUY);
				ResMsg_RegularBuy_Order res = appProductBusinessService.appProductPlaceOrder(req);
				if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					throw new OpenException(res.getResCode(),"购买失败："+res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("bindPreOrder/v_1.0.0")
	public String bindPreOrder(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BindPreOrderRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				BindPreOrderRequest yesRequest = (BindPreOrderRequest) request;
				Double amount = yesRequest.getAmount();
				if(!CheckUtil.ParamCheck(yesRequest, "userId","amount","productId","bankId","terminalType")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				BigDecimal b = new BigDecimal(amount);
				BigDecimal c = new BigDecimal("100");
		        BigDecimal e = new BigDecimal("0");
		        if (amount <= 0 || b.remainder(c).compareTo(e) != 0) {//金额不是100的整数倍
		            throw new OpenException(OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getMessage());
		        }
			}

			@Override
			public void doOperation(Request request, Response response) {
				BindPreOrderRequest yesRequest = (BindPreOrderRequest) request;
				BindPreOrderResponse yesResponse = (BindPreOrderResponse) response;
				ReqMsg_Bank_QueryBankInfoByUser reqBank = new ReqMsg_Bank_QueryBankInfoByUser();
				reqBank.setVersion("1.0.0");
				reqBank.setUserId(yesRequest.getUserId());
				reqBank.setBankId(yesRequest.getBankId());
				ResMsg_Bank_QueryBankInfoByUser resBank = appProductBusinessService.appProductBankInfo(reqBank);
				if(!StringUtil.isBlank(resBank.getIdCard())) {
					ReqMsg_RegularBuy_Order req = new ReqMsg_RegularBuy_Order();
					req.setVersion("1.0.0");
					req.setBankName(resBank.getBankName());
					req.setCardNo(resBank.getCardNo());
					req.setIdCard(resBank.getIdCard());
					req.setMobile(resBank.getMobile());
					req.setUserId(yesRequest.getUserId());
					req.setUserName(resBank.getUserName());
					req.setBankId(yesRequest.getBankId());
					req.setProductId(yesRequest.getProductId());
					req.setAmount(yesRequest.getAmount());
					req.setPlaceOrder(Constants.PLACE_ORDER_PREPARE);
					req.setTerminalType(yesRequest.getTerminalType());
					req.setTransType(Constants.TRANS_TYPE_CARD_BUY);
					req.setRedPacketId(yesRequest.getRedPacketId());
					ResMsg_RegularBuy_Order res = appProductBusinessService.appProductPlaceOrder(req);
					if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
						yesResponse.setOrderNo(res.getOrderNo());
						yesResponse.setMobile(req.getMobile());
						yesResponse.setHtml(res.getHtml());
					}
					else {
						throw new OpenException(res.getResCode(),res.getResMsg());
					}
				}
				else {
					throw new OpenException(OpenMessageEnum.PREPARE_ERROR.getCode(),OpenMessageEnum.PREPARE_ERROR.getMessage());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("bindOrder/v_1.0.0")
	public String bindOrder(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BindOrderRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				BindOrderRequest yesRequest = (BindOrderRequest) request;
				Double amount = yesRequest.getAmount();
				String verifyCode = yesRequest.getVerifyCode();
				if(!CheckUtil.ParamCheck(yesRequest, "userId","amount","productId","bankId","orderNo","verifyCode","terminalType")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				BigDecimal b = new BigDecimal(amount);
				BigDecimal c = new BigDecimal("100");
		        BigDecimal e = new BigDecimal("0");
		        if (amount <= 0 || b.remainder(c).compareTo(e) != 0) {//金额不是100的整数倍
		            throw new OpenException(OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getMessage());
		        }
				if(!CheckUtil.RegCheck(verifyCode, "^[0-9]*$")) {
		        	throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
		        }
			}

			@Override
			public void doOperation(Request request, Response response) {
				BindOrderRequest yesRequest = (BindOrderRequest) request;
				ReqMsg_Bank_QueryBankInfoByUser reqBank = new ReqMsg_Bank_QueryBankInfoByUser();
				reqBank.setVersion("1.0.0");
				reqBank.setUserId(yesRequest.getUserId());
				reqBank.setBankId(yesRequest.getBankId());
				ResMsg_Bank_QueryBankInfoByUser resBank = appProductBusinessService.appProductBankInfo(reqBank);
				if(!StringUtil.isBlank(resBank.getIdCard())) {
					ReqMsg_RegularBuy_Order req = new ReqMsg_RegularBuy_Order();
					req.setVersion("1.0.0");
					req.setBankName(resBank.getBankName());
					req.setCardNo(resBank.getCardNo());
					req.setIdCard(resBank.getIdCard());
					req.setMobile(resBank.getMobile());
					req.setUserId(yesRequest.getUserId());
					req.setUserName(resBank.getUserName());
					req.setBankId(yesRequest.getBankId());
					req.setProductId(yesRequest.getProductId());
					req.setAmount(yesRequest.getAmount());
					req.setOrderNo(yesRequest.getOrderNo());
					req.setVerifyCode(yesRequest.getVerifyCode());
					req.setPlaceOrder(Constants.PLACE_ORDER_FORMAL);
					req.setTerminalType(yesRequest.getTerminalType());
					req.setTransType(Constants.TRANS_TYPE_CARD_BUY);
					req.setRedPacketId(yesRequest.getRedPacketId());
					ResMsg_RegularBuy_Order res = appProductBusinessService.appProductPlaceOrder(req);
					if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
						throw new OpenException(res.getResCode(),"购买失败："+res.getResMsg());
					}
				}
				else {
					throw new OpenException(OpenMessageEnum.FORMAL_ERROR.getCode(),OpenMessageEnum.FORMAL_ERROR.getMessage());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("balanceBuy/v_1.0.0")
	public String balanceBuy(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BalanceBuyRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				BalanceBuyRequest bbRequest = (BalanceBuyRequest) request;
				Double amount = bbRequest.getAmount();
				String verifyCode = bbRequest.getVerifyCode();
				if(!CheckUtil.ParamCheck(bbRequest, "userId","amount","productId","verifyCode","terminalType")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				BigDecimal b = new BigDecimal(amount);
				BigDecimal c = new BigDecimal("100");
		        BigDecimal e = new BigDecimal("0");
		        if (amount <= 0 || b.remainder(c).compareTo(e) != 0) {//金额不是100的整数倍
		            throw new OpenException(OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getMessage());
		        }
				if(!CheckUtil.RegCheck(verifyCode, "^[0-9]*$")) {
		        	throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
		        }
			}

			@Override
			public void doOperation(Request request, Response response) {
				BalanceBuyRequest bbRequest = (BalanceBuyRequest) request;
				ReqMsg_User_InfoQuery reqInfo = new ReqMsg_User_InfoQuery();
				reqInfo.setVersion("1.0.0");
				reqInfo.setUserId(bbRequest.getUserId());
			    ResMsg_User_InfoQuery resInfo = appIndexBusinessService.appIndexUserInfo(reqInfo);
			    ReqMsg_User_ValidUser reqValid = new ReqMsg_User_ValidUser();
			    reqValid.setVersion("1.0.0");
			    reqValid.setMobile(resInfo.getMobile());
			    reqValid.setMobileCode(bbRequest.getVerifyCode());
			    ResMsg_User_ValidUser resValid = appProductBusinessService.appProductUserValid(reqValid);
			    if(ConstantUtil.BSRESCODE_SUCCESS.equals(resValid.getResCode())) {
			    	ReqMsg_RegularBuy_BalanceBuy req = new ReqMsg_RegularBuy_BalanceBuy();
			    	req.setVersion("1.0.0");
			    	req.setUserId(bbRequest.getUserId());
		        	req.setTerminalType(bbRequest.getTerminalType());
		        	req.setAmount(bbRequest.getAmount());
		        	req.setProductId(bbRequest.getProductId());
		        	req.setTicketId(bbRequest.getRedPacketId());
		        	ResMsg_RegularBuy_BalanceBuy res = appProductBusinessService.appProductBalanceBuy(req);
		        	if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
		        		throw new OpenException(res.getResCode(),"余额购买失败："+res.getResMsg());
		        	}
			    }
			    else {
			    	throw new OpenException(OpenMessageEnum.VERIFYCODE_INPUT_ERROR.getCode(), resValid.getResMsg());
			    }
			}
		});
	}

	@ResponseBody
	@RequestMapping("balanceBuy/v_1.0.1")
	public String balanceBuy_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BalanceBuyRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				BalanceBuyRequest bbRequest = (BalanceBuyRequest) request;
				Double amount = bbRequest.getAmount();
				String verifyCode = bbRequest.getVerifyCode();
				if(!CheckUtil.ParamCheck(bbRequest, "userId","amount","productId","verifyCode","terminalType")) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
				BigDecimal b = new BigDecimal(amount);
				BigDecimal c = new BigDecimal("100");
		        BigDecimal e = new BigDecimal("0");
		        if (amount <= 0 || b.remainder(c).compareTo(e) != 0) {//金额不是100的整数倍
		            throw new OpenException(OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getCode(),OpenMessageEnum.MONEY_IS_NOT_SPECIFICATION.getMessage());
		        }
				if(!CheckUtil.RegCheck(verifyCode, "^[0-9]*$")) {
		        	throw new OpenException(OpenMessageEnum.VERIFYCODE_NOT_NORM.getCode(),OpenMessageEnum.VERIFYCODE_NOT_NORM.getMessage());
		        }
			}

			@Override
			public void doOperation(Request request, Response response) {
				BalanceBuyRequest bbRequest = (BalanceBuyRequest) request;
				BalanceBuyResponse bbresponse = (BalanceBuyResponse)response;

				ReqMsg_User_InfoQuery reqInfo = new ReqMsg_User_InfoQuery();
				reqInfo.setVersion("1.0.0");
				reqInfo.setUserId(bbRequest.getUserId());
			    ResMsg_User_InfoQuery resInfo = appIndexBusinessService.appIndexUserInfo(reqInfo);
			    ReqMsg_User_ValidUser reqValid = new ReqMsg_User_ValidUser();
			    reqValid.setVersion("1.0.0");
			    reqValid.setMobile(resInfo.getMobile());
			    reqValid.setMobileCode(bbRequest.getVerifyCode());
			    ResMsg_User_ValidUser resValid = appProductBusinessService.appProductUserValid(reqValid);
			    if(ConstantUtil.BSRESCODE_SUCCESS.equals(resValid.getResCode())) {
			    	ReqMsg_RegularBuy_BalanceBuy req = new ReqMsg_RegularBuy_BalanceBuy();
			    	req.setVersion("1.0.0");
			    	req.setUserId(bbRequest.getUserId());
		        	req.setTerminalType(bbRequest.getTerminalType());
		        	req.setAmount(bbRequest.getAmount());
		        	req.setProductId(bbRequest.getProductId());
		        	req.setTicketId(bbRequest.getRedPacketId());
					req.setTicketType(bbRequest.getType());
		        	ResMsg_RegularBuy_BalanceBuy res = appProductBusinessService.appProductBalanceBuy(req);
		        	if(!ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
		        		throw new OpenException(res.getResCode(),"余额购买失败："+res.getResMsg());
		        	}
		        	bbresponse.setIsAutoRedPocket(res.getIsAutoRedPocket());
			    }
			    else {
			    	throw new OpenException(OpenMessageEnum.VERIFYCODE_INPUT_ERROR.getCode(), resValid.getResMsg());
			    }
			}
		});
	}



	@ResponseBody
	@RequestMapping("checkIDCard/v_1.0.0")
	public String checkIDCard(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, CheckIDCardRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				CheckIDCardRequest cardRequest = (CheckIDCardRequest) request;
				String IDCard = cardRequest.getIDCard();
				if(StringUtil.isBlank(IDCard)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				CheckIDCardRequest cardRequest = (CheckIDCardRequest) request;
				CheckIDCardResponse cardResponse = (CheckIDCardResponse) response;
				String res = IDCardUtil.IDCardValidate(cardRequest.getIDCard());
				cardResponse.setRespInfo(res);
			}
		});
	}

	@ResponseBody
	@RequestMapping("checkIDCard/v_1.0.1")
	public String checkIDCard_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, CheckIDCardSecondEditionRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				CheckIDCardSecondEditionRequest cardRequest = (CheckIDCardSecondEditionRequest) request;
				String idCard = cardRequest.getIdCard();
				if(StringUtil.isBlank(idCard)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				CheckIDCardSecondEditionRequest cardRequest = (CheckIDCardSecondEditionRequest) request;
				CheckIDCardSecondEditionResponse cardResponse = (CheckIDCardSecondEditionResponse) response;
				String res = IDCardUtil.IDCardValidate(cardRequest.getIdCard());
				cardResponse.setRespInfo(res);
			}
		});
	}



	@ResponseBody
	@RequestMapping("reapalResendCode/v_1.0.0")
	public String reapalResendCode(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ReapalResendCodeRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ReapalResendCodeRequest resendRequest = (ReapalResendCodeRequest) request;
				String orderNo = resendRequest.getOrderNo();
				if(StringUtil.isBlank(orderNo)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ReapalResendCodeRequest resendRequest = (ReapalResendCodeRequest) request;
				try {
					ResendCodeReq req = new ResendCodeReq();
					req.setMerchant_id(ReapalConfig.merchant_id);
					req.setOrder_no(resendRequest.getOrderNo());
					ResendCodeResp resp = reapalClient.resendCode(req);
					if(!ReapalQuickPayRespCode.SUCCESS_CODE_0000.getCode().equals(resp.getResult_code())) {
						throw new OpenException(OpenMessageEnum.RESEND_CODE_ERROR.getCode(),OpenMessageEnum.RESEND_CODE_ERROR.getMessage());
					}
				}catch(PTMessageException e) {
					throw new OpenException(e.getErrCode(),e.getErrMessage());
				}
			}
		});
	}



	@ResponseBody
	@RequestMapping("reapalQuickCMB/v_1.0.0")
	public String reapalQuickCMB(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ReapalQuickCMBRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ReapalQuickCMBRequest reapalQuickCMBRequset = (ReapalQuickCMBRequest) request;
				String orderNo = reapalQuickCMBRequset.getOrderNo();
				if(StringUtil.isBlank(orderNo)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ReapalQuickCMBRequest reapalQuickCMBRequset = (ReapalQuickCMBRequest) request;
				ReapalQuickCMBResponse reapalQuickCMBResponse = (ReapalQuickCMBResponse) response;

				ReqMsg_RegularBuy_ReapalQuickCMB req = new ReqMsg_RegularBuy_ReapalQuickCMB();
				req.setVersion("1.0.0");
				req.setOrderNo(reapalQuickCMBRequset.getOrderNo());
				req.setProductId(reapalQuickCMBRequset.getProductId());
				ResMsg_RegularBuy_ReapalQuickCMB res = appProductBusinessService.appProductReapalQuickCMB(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					/**
					 * 	private Integer userId;  //用户ID
						private Double amount;   //购买金额
						private String cardNo; //银行卡号
						private String userName;   //用户姓名
						private String idCard; //身份证号
						private String mobile;  //手机号码
						private Integer productId; //理财产品ID
						private String productName; //理财产品名称
						private Integer bankId; //银行ID
						private String bankName; //银行名称
						private Integer isBind; //用户是否绑定@1已绑定,2未绑定
						private Integer transType; //交易类型1卡购买,2充值
						private Integer terminalType; //终端类型@1手机端,2PC端
						private String orderNo; //币港湾订单号
					 */

					reapalQuickCMBResponse.setUserId(res.getUserId());
					reapalQuickCMBResponse.setAmount(res.getAmount());
					reapalQuickCMBResponse.setCardNo(res.getCardNo());
					reapalQuickCMBResponse.setUserName(res.getUserName());
					reapalQuickCMBResponse.setIdCard(res.getIdCard());
					reapalQuickCMBResponse.setMobile(res.getMobile());
					reapalQuickCMBResponse.setProductId(res.getProductId());
					reapalQuickCMBResponse.setProductName(res.getProductName());
					reapalQuickCMBResponse.setBankId(res.getBankId());
					reapalQuickCMBResponse.setBankName(res.getBankName());
					reapalQuickCMBResponse.setIsBind(res.getIsBind());
					reapalQuickCMBResponse.setTransType(res.getTransType());
					reapalQuickCMBResponse.setTerminalType(res.getTerminalType());
					reapalQuickCMBResponse.setOrderNo(res.getOrderNo());

				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("saveProductInform/v_1.0.0")
	public String saveProductInform(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, SaveProductInformRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				SaveProductInformRequest informRequest = (SaveProductInformRequest) request;
				Integer userId = informRequest.getUserId();
				Integer productId = informRequest.getProductId();
				if(userId == null || productId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				SaveProductInformRequest informRequest = (SaveProductInformRequest) request;
				ReqMsg_Product_SaveProductInform reqProductInform = new ReqMsg_Product_SaveProductInform();
				reqProductInform.setUserId(informRequest.getUserId());
				reqProductInform.setProductId(informRequest.getProductId());
				reqProductInform.setVersion("1.0.0");
				ResMsg_Product_SaveProductInform resProductInform = appProductBusinessService.appProductInform(reqProductInform);
				if(!ConstantUtil.BSRESCODE_SUCCESS.equals(resProductInform.getResCode())) {
					throw new OpenException(resProductInform.getResCode(),resProductInform.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("selectProductInform/v_1.0.0")
	public String selectProductInform(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, SelectProductInformRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				SelectProductInformRequest informRequest = (SelectProductInformRequest) request;
				Integer userId = informRequest.getUserId();
				Integer productId = informRequest.getProductId();
				if(userId == null || productId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				SelectProductInformRequest informRequest = (SelectProductInformRequest) request;
				SelectProductInformResponse informResponse = (SelectProductInformResponse) response;
				ReqMsg_Product_SelectProductInform req = new ReqMsg_Product_SelectProductInform();
				req.setUserId(informRequest.getUserId());
				req.setProductId(informRequest.getProductId());
				req.setVersion("1.0.0");
				ResMsg_Product_SelectProductInform res = appProductBusinessService.appSelectProductInform(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					informResponse.setExist(res.isExist());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("newBuyer/v_1.0.0")
	public String newBuyer(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, NewBuyerRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				NewBuyerRequest newBuyerRequest = (NewBuyerRequest) request;
				Integer userId = newBuyerRequest.getUserId();
				Integer productId = newBuyerRequest.getProductId();
				if(userId == null || productId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				NewBuyerRequest newBuyerRequest = (NewBuyerRequest) request;
				NewBuyerResponse newBuyerResponse = (NewBuyerResponse) response;
				ReqMsg_Product_NewBuyer req = new ReqMsg_Product_NewBuyer();
				req.setUserId(newBuyerRequest.getUserId());
				req.setProductId(newBuyerRequest.getProductId());
				req.setVersion("1.0.0");
				ResMsg_Product_NewBuyer res = appProductBusinessService.appNewBuyer(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					newBuyerResponse.setInvestCount(res.getInvestCount());
					newBuyerResponse.setMaxSingleInvestAmount(res.getMaxSingleInvestAmount());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}

	@ResponseBody
	@RequestMapping("selectSingleProduct/v_1.0.0")
	public String selectSingleProduct(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductSingleRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ProductSingleRequest productRequest = (ProductSingleRequest) request;
				Integer id = productRequest.getId();
				if(id == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ProductSingleRequest productRequest = (ProductSingleRequest) request;
				ProductSingleResponse productResponse = (ProductSingleResponse) response;
				ReqMsg_Product_SingleProduct req = new ReqMsg_Product_SingleProduct();
				req.setId(productRequest.getId());
				req.setVersion("1.0.0");
				ResMsg_Product_SingleProduct res = appProductBusinessService.selectSingleProduct(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					Product p = new Product();
					p.setId(res.getId());
					p.setMinInvestAmount(res.getMinInvestAmount());
					p.setName(res.getName());
					p.setRate(res.getRate());
					p.setTerm(String.valueOf(res.getTerm()));
					p.setCurrTime(res.getCurrTime());
					p.setStartTime(res.getStartTime());
					p.setLeftAmount(res.getLeftAmount());
					p.setPropertyType(res.getPropertyType());
					p.setActivityType(res.getActivityType());
					p.setMaxSingleInvestAmount(res.getMaxSingleInvestAmount());
					p.setEndTime(res.getEndTime());
					p.setFinishTime(res.getFinishTime());
					p.setPropertySymbol(res.getPropertySymbol());
					p.setReturnType(res.getReturnType());
					productResponse.setProduct(p);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}

		});
	}



	@ResponseBody
	@RequestMapping("selectSingleProduct/v_1.0.1")
	public String selectSingleProduct_1(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductSingleRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ProductSingleRequest productRequest = (ProductSingleRequest) request;
				Integer id = productRequest.getId();
				if(id == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}

			@Override
			public void doOperation(Request request, Response response) {
				ProductSingleRequest productRequest = (ProductSingleRequest) request;
				ProductSingleResponse productResponse = (ProductSingleResponse) response;
				ReqMsg_Product_SingleProduct req = new ReqMsg_Product_SingleProduct();
				req.setId(productRequest.getId());
				req.setVersion("1.0.0");
				ResMsg_Product_SingleProduct res = appProductBusinessService.selectSingleProduct(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					Product p = new Product();
					p.setId(res.getId());
					p.setMinInvestAmount(res.getMinInvestAmount());
					p.setName(res.getName());
					p.setRate(res.getRate());
					p.setTerm(String.valueOf(res.getTerm()));
					p.setCurrTime(res.getCurrTime());
					p.setStartTime(res.getStartTime());
					p.setLeftAmount(res.getLeftAmount());
					p.setPropertyType(res.getPropertyType());
					p.setActivityType(res.getActivityType());
					p.setMaxSingleInvestAmount(res.getMaxSingleInvestAmount());
					p.setEndTime(res.getEndTime());
					p.setFinishTime(res.getFinishTime());
					p.setPropertySymbol(res.getPropertySymbol());



/*					String start = res.getStartTime();
					Date startDate = DateUtil.parseDateTime(start);


					SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
					String hourString = formatter.format(startDate);

					Calendar calendar = new GregorianCalendar();
					calendar.setTime(new Date());
					calendar.add(calendar.DATE,1);
					Date tomorrowDate=calendar.getTime();

					if (DateUtil.isSameDay(new Date(), startDate)) {
						p.setStartTime("今日"+hourString);
					}else if (DateUtil.isSameDay(tomorrowDate, startDate)) {
						p.setStartTime("明日"+hourString);
					}else {
						SimpleDateFormat formatterMonth = new SimpleDateFormat("M月dd日 HH:mm:ss");
						String dateString = formatterMonth.format(startDate);
						p.setStartTime(dateString);
					}*/

					p.setStatus(res.getStatus());
					p.setCurrTotalAmount(res.getCurrTotalAmount());
					p.setMaxTotalAmount(res.getMaxTotalAmount());
					p.setIsSupportRedPacket(res.getIsSupportRedPacket());

					p.setRemindTagContent(res.getRemindTagContent());
					p.setInterestRatesTagContent(res.getInterestRatesTagContent());
					productResponse.setProduct(p);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}

		});
	}

	@ResponseBody
	@RequestMapping("baofooBankList/v_1.0.0")
	public String baofooBankList(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BaofooBankListRequest.class, new ControllerCallBack() {

			@Override
			public void doOperation(Request request, Response response) {
				BaofooBankListRequest baoFooRequest = (BaofooBankListRequest) request;
				BaofooBankListResponse baoFooResponse = (BaofooBankListResponse) response;

				ReqMsg_Bank_ChannelBank req = new ReqMsg_Bank_ChannelBank();
				req.setVersion("1.0.0");
				req.setPayType(1);
				req.setPayChannel(Constants.CHANNEL_HF);
				ResMsg_Bank_ChannelBank res = appProductBusinessService.baofooBankList(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Bank> bankList = new ArrayList<Bank>();
					for(Map<String,Object> map : res.getBankList()) {
						Bank bank = new Bank();
						bank.setBankId((Integer)map.get("bankId"));
						bank.setBankName((String)map.get("bankName"));
						bank.setDayTop((Double)map.get("dayTop"));
						bank.setOneTop((Double)map.get("oneTop"));
						bank.setIsAvailable((Integer)map.get("isAvailable"));
						bank.setPay19BankCode((String)map.get("pay19BankCode"));
						bank.setNotice((String)map.get("notice"));
						bank.setDailyNotice((String)map.get("dailyNotice"));
						bankList.add(bank);
					}
					baoFooResponse.setDataList(bankList);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}


	@ResponseBody
	@RequestMapping("productListIndex/v_1.0.0")
	public String productListIndex(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductListIndexRequest.class, new ControllerCallBack() {

			@Override
			public void doCheck(Request request, Response response) {
				ProductListIndexRequest plRequest = (ProductListIndexRequest) request;

			}

			@Override
			public void doOperation(Request request, Response response) {
				ProductListIndexRequest plRequest = (ProductListIndexRequest) request;
				ProductListIndexResponse plResponse = (ProductListIndexResponse) response;


				ReqMsg_Product_ListIndexQuery req = new ReqMsg_Product_ListIndexQuery();
				req.setVersion("1.0.0");
				req.setProductShowTerminal("APP");
				ResMsg_Product_ListIndexQuery res = appProductBusinessService.appProductProductListIndex(req);

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					//限时活动
					plResponse.setTimeLimitActivityStatus(res.getTimeLimitActivityStatus());
					Product timeLimitActivity = new Product();
					timeLimitActivity.setName("限时活动");
					if ("show".equals(res.getTimeLimitActivityStatus())) {

						if ("AVERAGE_CAPITAL_PLUS_INTEREST".equals((String)res.getTimeLimitActivity().get("returnType"))) {
							timeLimitActivity.setRate((String)res.getTimeLimitActivity().get("rate"));
							timeLimitActivity.setTerm((String)res.getTimeLimitActivity().get("term"));
							timeLimitActivity.setPropertySymbol((String)res.getTimeLimitActivity().get("propertySymbol"));
						}else if ("FINISH_RETURN_ALL".equals((String)res.getTimeLimitActivity().get("returnType"))) {
							timeLimitActivity.setRate((String)res.getTimeLimitActivity().get("rate"));
							timeLimitActivity.setTerm((String)res.getTimeLimitActivity().get("term"));
						}else {
							timeLimitActivity.setRate((String)res.getTimeLimitActivity().get("rate"));
							timeLimitActivity.setTerm((String)res.getTimeLimitActivity().get("term"));
						}
						timeLimitActivity.setReturnType((String)res.getTimeLimitActivity().get("returnType"));
						timeLimitActivity.setId((Integer)res.getTimeLimitActivity().get("id"));
					}
					plResponse.setTimeLimitActivity(timeLimitActivity);
					//新手专享
					plResponse.setNoviceActivityStatus(res.getNoviceActivityStatus());
					Product noviceActivity = new Product();
					noviceActivity.setName("新手专享");
					if ("show".equals(res.getNoviceActivityStatus())) {
						if ("AVERAGE_CAPITAL_PLUS_INTEREST".equals((String)res.getNoviceActivity().get("returnType"))) {
							noviceActivity.setRate((String)res.getNoviceActivity().get("rate"));
							noviceActivity.setTerm((String)res.getNoviceActivity().get("term"));
						}else if ("FINISH_RETURN_ALL".equals((String)res.getNoviceActivity().get("returnType"))) {
							noviceActivity.setRate((String)res.getNoviceActivity().get("rate"));
							noviceActivity.setTerm((String)res.getNoviceActivity().get("term"));
						}else {
							noviceActivity.setRate((String)res.getNoviceActivity().get("rate"));
							noviceActivity.setTerm((String)res.getNoviceActivity().get("term"));
						}
						noviceActivity.setReturnType((String)res.getNoviceActivity().get("returnType"));
						noviceActivity.setId((Integer)res.getNoviceActivity().get("id"));
					}
					plResponse.setNoviceActivity(noviceActivity);
					//到期还本付息
					plResponse.setFinishReturnAllProductStatus(res.getFinishReturnAllProductStatus());
					Product finishReturnAllProduct = new Product();
					finishReturnAllProduct.setName("到期还本付息");
					if ("show".equals(res.getFinishReturnAllProductStatus())) {
						Double minRate = (Double)res.getFinishReturnAllProduct().get("minRate");
						Double maxRate = (Double)res.getFinishReturnAllProduct().get("maxRate");
						if (minRate.equals(maxRate)) {
							finishReturnAllProduct.setRate(new DecimalFormat("0.00").format(minRate));
						}else {
							finishReturnAllProduct.setRate(new DecimalFormat("0.00").format(minRate)+","+new DecimalFormat("0.00").format(maxRate));
						}

						Integer minTerm = (Integer)res.getFinishReturnAllProduct().get("minTerm");
						Integer maxTerm = (Integer)res.getFinishReturnAllProduct().get("maxTerm");
						if (minTerm.equals(maxTerm)) {
							finishReturnAllProduct.setTerm(String.valueOf(minTerm));
						}else {
							finishReturnAllProduct.setTerm(minTerm+","+maxTerm);
						}


					}

					plResponse.setFinishReturnAllProduct(finishReturnAllProduct);



					//等额本息
					plResponse.setAverageCapitalPlusInterestProductStatus(res.getAverageCapitalPlusInterestProductStatus());
					Product averageCapitalPlusInterestProduct = new Product();
					averageCapitalPlusInterestProduct.setName("等额本息");
					if ("show".equals(res.getAverageCapitalPlusInterestProductStatus())) {
						Double minRate = (Double)res.getAverageCapitalPlusInterestProduct().get("minRate");
						Double maxRate = (Double)res.getAverageCapitalPlusInterestProduct().get("maxRate");
						if (minRate.equals(maxRate)) {
							averageCapitalPlusInterestProduct.setRate(new DecimalFormat("0.00").format(minRate));
						}else {
							averageCapitalPlusInterestProduct.setRate(new DecimalFormat("0.00").format(minRate)+","+new DecimalFormat("0.00").format(maxRate));
						}

						Integer minTerm = (Integer)res.getAverageCapitalPlusInterestProduct().get("minTerm");
						Integer maxTerm = (Integer)res.getAverageCapitalPlusInterestProduct().get("maxTerm");
						if (minTerm.equals(maxTerm)) {
							averageCapitalPlusInterestProduct.setTerm(String.valueOf(minTerm));
						}else {
							averageCapitalPlusInterestProduct.setTerm(minTerm+","+maxTerm);
						}
					}
					plResponse.setAverageCapitalPlusInterestProduct(averageCapitalPlusInterestProduct);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}


	@ResponseBody
	@RequestMapping("productListIndex/v_1.0.1")
	public String productListIndex_1(HttpServletRequest req, HttpServletResponse resp) {
		
		//记录用户登录的IP地址对应的位置信息
		String request_json_str_ = req.getParameter("request_json_str_");
		JsonConfig config = new JsonConfig();
		config.registerJsonValueProcessor(Date.class,
				new JsonValueProcessorImpl());
		JSONObject jsonObject = JSONObject.fromObject(request_json_str_, config);
		Object appClientObject = jsonObject.get("appClient");
		if ("4".equals(appClientObject)) {
			//4对应ios，ios端通过产品列表首页来控制更新ip位置频率1天1次，安卓端获取应用列表时获取ip
			Integer userId = (Integer)jsonObject.get("userId");
			CookieManager manager = new CookieManager(req);
	        String ip = manager.getValue("_mobile",
	                "_userIP", true);
	        if (StringUtil.isBlank(ip) && userId != null) {
	        	ReqMsg_User_AppAddUserAddress addressReq = new ReqMsg_User_AppAddUserAddress();
	        	addressReq.setVersion("1.0.0");
	        	addressReq.setUserId(userId);
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
		
		return this.execute(req, ProductListIndexRequest.class, new ControllerCallBack() {
			
			@Override
			public void doCheck(Request request, Response response) {
				super.doCheck(request, response);
				ProductListIndexRequest plRequest = (ProductListIndexRequest) request;
			}
			
			@Override
			public void doOperation(Request request, Response response) {
				ProductListIndexRequest plRequest = (ProductListIndexRequest) request;
				ProductListIndexResponse plResponse = (ProductListIndexResponse) response;
				
				Integer isNovice = 1;// 1-新手标志,0-不是新手
				if(plRequest.getUserId() != null){
					ReqMsg_Product_ListIndexInfoQuery4User reqMsg = new ReqMsg_Product_ListIndexInfoQuery4User();
					reqMsg.setVersion("1.0.1");
					reqMsg.setUserId(plRequest.getUserId());
					ResMsg_Product_ListIndexInfoQuery4User res = appProductBusinessService.isNewUser(reqMsg);
					isNovice = res.getIsNovice();
				}
				ReqMsg_Product_ListIndexInfoQuery reqMsg = new ReqMsg_Product_ListIndexInfoQuery();
				reqMsg.setVersion("1.0.1");
				reqMsg.setProductShowTerminal("APP");
				ResMsg_Product_ListIndexInfoQuery res = appProductBusinessService.appProductProductListInfoIndex(reqMsg);
				
				

				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					try {
						// 是否新手标识
						plResponse.setIsNovice(isNovice);
						//限时活动
						plResponse.setTimeLimitActivityStatus(res.getTimeLimitActivityStatus());
						Product timeLimitActivity = new Product();
						timeLimitActivity.setName("限时活动");
						if ("show".equals(res.getTimeLimitActivityStatus())) {

							if ("AVERAGE_CAPITAL_PLUS_INTEREST".equals((String)res.getTimeLimitActivity().get("returnType"))) {
								timeLimitActivity.setRate((String)res.getTimeLimitActivity().get("rate"));
								timeLimitActivity.setTerm((String)res.getTimeLimitActivity().get("term"));
								timeLimitActivity.setPropertySymbol((String)res.getTimeLimitActivity().get("propertySymbol"));
							}else if ("FINISH_RETURN_ALL".equals((String)res.getTimeLimitActivity().get("returnType"))) {
								timeLimitActivity.setRate((String)res.getTimeLimitActivity().get("rate"));
								timeLimitActivity.setTerm((String)res.getTimeLimitActivity().get("term"));
							}else {
								timeLimitActivity.setRate((String)res.getTimeLimitActivity().get("rate"));
								timeLimitActivity.setTerm((String)res.getTimeLimitActivity().get("term"));
							}
							timeLimitActivity.setReturnType((String)res.getTimeLimitActivity().get("returnType"));
							timeLimitActivity.setId((Integer)res.getTimeLimitActivity().get("id"));
							timeLimitActivity.setMinInvestAmount((Double)res.getTimeLimitActivity().get("minInvestAmount"));

							// 标的开始时间
							String start = (String)res.getTimeLimitActivity().get("startTime");
							Date startDate = DateUtil.parseDateTime(start);
							SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
							String hourString = formatter.format(startDate);
							Calendar cal = Calendar.getInstance();
							cal.setTime(new Date());
							cal.add(Calendar.DAY_OF_YEAR, 1);
							Date tomorrowDate = cal.getTime();

							if (DateUtil.isSameDay(new Date(), startDate)) {
								timeLimitActivity.setStartTime("今日"+hourString+"开始");
							}else if (DateUtil.isSameDay(tomorrowDate, startDate)) {
								timeLimitActivity.setStartTime("明日"+hourString+"开始");
							}else {
								SimpleDateFormat formatterMonth = new SimpleDateFormat("M月dd日 HH:mm");
								String dateString = formatterMonth.format(startDate);
								timeLimitActivity.setStartTime(dateString+"开始");
							}

							Integer status = (Integer) res.getTimeLimitActivity().get("status");
							Double progress = 0d;
							String flag = "";
							// 未发放已发布
							if(Constants.PRODUCT_STATUS_PUBLISH_YES.equals(status) || Constants.PRODUCT_STATUS_PUBLISH_NO.equals(status)) {
								flag = "countdown";
							}
							// 已经手动结束
							if(Constants.PRODUCT_STATUS_FINISH.equals(status)) {
								flag = "finish";
								progress = 100d;
							}
							// 进行中
							if(Constants.PRODUCT_STATUS_OPENING.equals(status)) {
								flag = "buy";
								Double maxTotalAmount = (Double) res.getTimeLimitActivity().get("maxTotalAmount");
								Double currTotalAmount = (Double) res.getTimeLimitActivity().get("currTotalAmount");
								Double a =  currTotalAmount / maxTotalAmount * 100d;
								if(a > 0 && a < 1) {
									progress = 1d;
								} else if(a > 99 && a < 100) {
									progress = 99d;
								} else {
									progress = a;
								}
							}
							timeLimitActivity.setFlag(flag);
							timeLimitActivity.setProgress(progress);
						}
						plResponse.setTimeLimitActivity(timeLimitActivity);
						//新手专享
						plResponse.setNoviceActivityStatus(res.getNoviceActivityStatus());
						Product noviceActivity = new Product();
						noviceActivity.setName("新手专享");
						if ("show".equals(res.getNoviceActivityStatus())) {
							if ("AVERAGE_CAPITAL_PLUS_INTEREST".equals((String)res.getNoviceActivity().get("returnType"))) {
								noviceActivity.setRate((String)res.getNoviceActivity().get("rate"));
								noviceActivity.setTerm((String)res.getNoviceActivity().get("term"));
							}else if ("FINISH_RETURN_ALL".equals((String)res.getNoviceActivity().get("returnType"))) {
								noviceActivity.setRate((String)res.getNoviceActivity().get("rate"));
								noviceActivity.setTerm((String)res.getNoviceActivity().get("term"));
							}else {
								noviceActivity.setRate((String)res.getNoviceActivity().get("rate"));
								noviceActivity.setTerm((String)res.getNoviceActivity().get("term"));
							}
							noviceActivity.setReturnType((String)res.getNoviceActivity().get("returnType"));
							noviceActivity.setId((Integer)res.getNoviceActivity().get("id"));
							noviceActivity.setMinInvestAmount((Double)res.getNoviceActivity().get("minInvestAmount"));

							// 标的开始时间
							String start = (String)res.getNoviceActivity().get("startTime");
							Date startDate = DateUtil.parseDateTime(start);
							SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
							String hourString = formatter.format(startDate);
							Calendar cal = Calendar.getInstance();
							cal.setTime(new Date());
							cal.add(Calendar.DAY_OF_YEAR, 1);
							Date tomorrowDate = cal.getTime();

							if (DateUtil.isSameDay(new Date(), startDate)) {
								noviceActivity.setStartTime("今日"+hourString+"开始");
							}else if (DateUtil.isSameDay(tomorrowDate, startDate)) {
								noviceActivity.setStartTime("明日"+hourString+"开始");
							}else {
								SimpleDateFormat formatterMonth = new SimpleDateFormat("M月dd日HH:mm");
								String dateString = formatterMonth.format(startDate);
								noviceActivity.setStartTime(dateString+"开始");
							}

							Integer status = null == res.getNoviceActivity() ? null : (Integer) res.getNoviceActivity().get("status");
							Double progress = 0d;
							String flag = "";
							// 未发放已发布
							if(Constants.PRODUCT_STATUS_PUBLISH_YES.equals(status) || Constants.PRODUCT_STATUS_PUBLISH_NO.equals(status)) {
								flag = "countdown";
							}
							// 已经手动结束
							if(Constants.PRODUCT_STATUS_FINISH.equals(status)) {
								flag = "finish";
								progress = 100d;
							}
							// 进行中
							if(Constants.PRODUCT_STATUS_OPENING.equals(status)) {
								flag = "buy";
								Double maxTotalAmount = (Double) res.getNoviceActivity().get("maxTotalAmount");
								Double currTotalAmount = (Double) res.getNoviceActivity().get("currTotalAmount");
								Double a =  currTotalAmount / maxTotalAmount * 100d;
								if(a > 0 && a < 1) {
									progress = 1d;
								} else if(a > 99 && a < 100) {
									progress = 99d;
								} else {
									progress = a;
								}
							}
							noviceActivity.setFlag(flag);
							noviceActivity.setProgress(progress);

						}
						plResponse.setNoviceActivity(noviceActivity);
						//到期还本付息
						plResponse.setFinishReturnAllProductStatus(res.getFinishReturnAllProductStatus());
						Product finishReturnAllProduct = new Product();
						finishReturnAllProduct.setName("港湾计划");
						if ("show".equals(res.getFinishReturnAllProductStatus())) {
							Double minRate = (Double)res.getFinishReturnAllProduct().get("minRate");
							Double maxRate = (Double)res.getFinishReturnAllProduct().get("maxRate");
							if (minRate.equals(maxRate)) {
								finishReturnAllProduct.setRate(new DecimalFormat("0.00").format(minRate));
							}else {
								finishReturnAllProduct.setRate(new DecimalFormat("0.00").format(minRate)+","+new DecimalFormat("0.00").format(maxRate));
							}

							Integer minTerm = (Integer)res.getFinishReturnAllProduct().get("minTerm");
							Integer maxTerm = (Integer)res.getFinishReturnAllProduct().get("maxTerm");
							if (minTerm.equals(maxTerm)) {
								finishReturnAllProduct.setTerm(String.valueOf(minTerm));
							}else {
								finishReturnAllProduct.setTerm(minTerm+","+maxTerm);
							}
						}
						plResponse.setFinishReturnAllProduct(finishReturnAllProduct);

						//等额本息
						plResponse.setAverageCapitalPlusInterestProductStatus(res.getAverageCapitalPlusInterestProductStatus());
						Product averageCapitalPlusInterestProduct = new Product();
						averageCapitalPlusInterestProduct.setName("赞分期计划");
						if ("show".equals(res.getAverageCapitalPlusInterestProductStatus())) {
							Double minRate = (Double)res.getAverageCapitalPlusInterestProduct().get("minRate");
							Double maxRate = (Double)res.getAverageCapitalPlusInterestProduct().get("maxRate");
							if (minRate.equals(maxRate)) {
								averageCapitalPlusInterestProduct.setRate(new DecimalFormat("0.00").format(minRate));
							}else {
								averageCapitalPlusInterestProduct.setRate(new DecimalFormat("0.00").format(minRate)+","+new DecimalFormat("0.00").format(maxRate));
							}

							Integer minTerm = (Integer)res.getAverageCapitalPlusInterestProduct().get("minTerm");
							Integer maxTerm = (Integer)res.getAverageCapitalPlusInterestProduct().get("maxTerm");
							if (minTerm.equals(maxTerm)) {
								averageCapitalPlusInterestProduct.setTerm(String.valueOf(minTerm));
							}else {
								averageCapitalPlusInterestProduct.setTerm(minTerm+","+maxTerm);
							}
						}
						plResponse.setAverageCapitalPlusInterestProduct(averageCapitalPlusInterestProduct);
					} catch (Exception e) {
						logger.info("产品列表错误：{}，堆栈{}", e.getMessage(), e);
						throw new OpenException(res.getResCode(),res.getResMsg());
					}
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}
	
	
	@ResponseBody
	@RequestMapping("productListReturnType/v_1.0.0")
	public String productListReturnType(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductListReturnTypeRequest.class, new ControllerCallBack() {
			
			@Override
			public void doCheck(Request request, Response response) {
				ProductListReturnTypeRequest plRequest = (ProductListReturnTypeRequest) request;
				String  returnType = plRequest.getReturnType();
				if(StringUtil.isBlank(returnType)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}
			
			@Override
			public void doOperation(Request request, Response response) {
				ProductListReturnTypeRequest plRequest = (ProductListReturnTypeRequest) request;
				ProductListReturnTypeResponse plResponse = (ProductListReturnTypeResponse) response;
				
				String  returnType = plRequest.getReturnType();
				ReqMsg_Product_ProductListReturnType req = new ReqMsg_Product_ProductListReturnType();
				req.setVersion("1.0.0");
				req.setReturnType(returnType);
				ResMsg_Product_ProductListReturnType res = appProductBusinessService.appProductProductListReturnType(req);
				
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Product> productList = new ArrayList<Product>();
					for(Map<String,Object> map : res.getProductList()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setMaxTotalAmount((Double)map.get("maxTotalAmount"));
						p.setCurrTotalAmount((Double)map.get("currTotalAmount"));
						
						
						String start = (String)map.get("startTime");
						Date startDate = DateUtil.parseDateTime(start);

						
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
						String hourString = formatter.format(startDate);
						
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(new Date());
						calendar.add(calendar.DATE,1);
						Date tomorrowDate=calendar.getTime();
						
						if (DateUtil.isSameDay(new Date(), startDate)) {
							p.setStartTime("今日"+hourString+"开始");
						}else if (DateUtil.isSameDay(tomorrowDate, startDate)) {
							p.setStartTime("明日"+hourString+"开始");
						}else {
							SimpleDateFormat formatterMonth = new SimpleDateFormat("M月dd日 HH:mm");
							String dateString = formatterMonth.format(startDate);
							p.setStartTime(dateString+"开始");
						}
						p.setStatus((Integer)map.get("status"));
						/*
						p.setCurrTime((String)map.get("currTime"));
						p.setLeftAmount((Double)map.get("leftAmount"));
						p.setPropertyType((String)map.get("propertyType"));
						p.setActivityType((String)map.get("activityType"));
						p.setMaxSingleInvestAmount((Double)map.get("maxSingleInvestAmount"));
						p.setIsSupportRedPacket((String)map.get("isSupportRedPacket"));
						if(map.get("endTime") != null) {
							p.setEndTime((String)map.get("endTime"));
						}
						if(map.get("finishTime") != null) {
							p.setFinishTime((String)map.get("finishTime"));
						}
						p.setPropertySymbol((String)map.get("propertySymbol"));*/
						
						productList.add(p);
					}
					plResponse.setProductList(productList);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}
	
	
	@ResponseBody
	@RequestMapping("productListReturnType/v_1.0.2")
	public String productListReturnType_2(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductListReturnTypeRequest.class, new ControllerCallBack() {
			
			@Override
			public void doCheck(Request request, Response response) {
				ProductListReturnTypeRequest plRequest = (ProductListReturnTypeRequest) request;
				String  returnType = plRequest.getReturnType();
				if(StringUtil.isBlank(returnType)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}
			
			@Override
			public void doOperation(Request request, Response response) {
				ProductListReturnTypeRequest plRequest = (ProductListReturnTypeRequest) request;
				ProductListReturnTypeResponse plResponse = (ProductListReturnTypeResponse) response;
				
				String  returnType = plRequest.getReturnType();
				ReqMsg_Product_ProductListReturnType req = new ReqMsg_Product_ProductListReturnType();
				req.setVersion("1.0.0");
				req.setReturnType(returnType);
				ResMsg_Product_ProductListReturnType res = appProductBusinessService.appProductProductListReturnType(req);
				
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Product> productList = new ArrayList<Product>();
					for(Map<String,Object> map : res.getProductList()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setMaxTotalAmount((Double)map.get("maxTotalAmount"));
						p.setCurrTotalAmount((Double)map.get("currTotalAmount"));
						
						
						String start = (String)map.get("startTime");
						Date startDate = DateUtil.parseDateTime(start);

						
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
						String hourString = formatter.format(startDate);
						
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(new Date());
						calendar.add(calendar.DATE,1);
						Date tomorrowDate=calendar.getTime();
						
						if (DateUtil.isSameDay(new Date(), startDate)) {
							p.setStartTime("今日"+hourString+"开始");
						}else if (DateUtil.isSameDay(tomorrowDate, startDate)) {
							p.setStartTime("明日"+hourString+"开始");
						}else {
							SimpleDateFormat formatterMonth = new SimpleDateFormat("M月dd日 HH:mm");
							String dateString = formatterMonth.format(startDate);
							p.setStartTime(dateString+"开始");
						}
						p.setStatus((Integer)map.get("status"));
						p.setIsSuggest((String)map.get("isSuggest"));
						p.setRemindTagContent((String)map.get("remindTagContent"));
						p.setInterestRatesTagContent((String)map.get("interestRatesTagContent"));
						
						productList.add(p);
					}
					plResponse.setProductList(productList);
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}
	
	
	@ResponseBody
	@RequestMapping("productListStatusFinish30/v_1.0.0")
	public String productListStatusFinish30(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, ProductListStatusFinish30Request.class, new ControllerCallBack() {
			
			@Override
			public void doCheck(Request request, Response response) {
				ProductListStatusFinish30Request plRequest = (ProductListStatusFinish30Request) request;
				String  returnType = plRequest.getReturnType();
				if(StringUtil.isBlank(returnType)) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}
			
			@Override
			public void doOperation(Request request, Response response) {
				ProductListStatusFinish30Request plRequest = (ProductListStatusFinish30Request) request;
				ProductListStatusFinish30Response plResponse = (ProductListStatusFinish30Response) response;
				
				String  returnType = plRequest.getReturnType();
				ReqMsg_Product_ProductListStatusFinish30 req = new ReqMsg_Product_ProductListStatusFinish30();
				req.setVersion("1.0.0");
				req.setReturnType(returnType);
				req.setPage(plRequest.getPage());
				ResMsg_Product_ProductListStatusFinish30 res = appProductBusinessService.appProductProductListStatusFinish30(req);
				
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					List<Product> productList = new ArrayList<Product>();
					for(Map<String,Object> map : res.getProductList()) {
						Product p = new Product();
						p.setId((Integer)map.get("id"));
						p.setName(((String)map.get("name")));
						p.setRate((String)map.get("rate"));
						p.setTerm((String)map.get("term"));
						p.setMinInvestAmount((Double)map.get("minInvestAmount"));
						p.setMaxTotalAmount((Double)map.get("maxTotalAmount"));
						p.setCurrTotalAmount((Double)map.get("currTotalAmount"));
						p.setIsSuggest((String)map.get("isSuggest"));
						
						String start = (String)map.get("startTime");
						Date startDate = DateUtil.parseDateTime(start);

						
						SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
						String hourString = formatter.format(startDate);
						
						Calendar calendar = new GregorianCalendar();
						calendar.setTime(new Date());
						calendar.add(calendar.DATE,1);
						Date tomorrowDate=calendar.getTime();
						
						if (DateUtil.isSameDay(new Date(), startDate)) {
							p.setStartTime("今日"+hourString+"开始");
						}else if (DateUtil.isSameDay(tomorrowDate, startDate)) {
							p.setStartTime("明日"+hourString+"开始");
						}else {
							SimpleDateFormat formatterMonth = new SimpleDateFormat("M月dd日 HH:mm");
							String dateString = formatterMonth.format(startDate);
							p.setStartTime(dateString+"开始");
						}
						p.setStatus((Integer)map.get("status"));
						
						/*
						p.setCurrTime((String)map.get("currTime"));
						p.setLeftAmount((Double)map.get("leftAmount"));
						p.setPropertyType((String)map.get("propertyType"));
						p.setActivityType((String)map.get("activityType"));
						p.setMaxSingleInvestAmount((Double)map.get("maxSingleInvestAmount"));
						p.setIsSupportRedPacket((String)map.get("isSupportRedPacket"));
						if(map.get("endTime") != null) {
							p.setEndTime((String)map.get("endTime"));
						}
						if(map.get("finishTime") != null) {
							p.setFinishTime((String)map.get("finishTime"));
						}
						p.setPropertySymbol((String)map.get("propertySymbol"));*/
						
						productList.add(p);
					}
					plResponse.setProductList(productList);
					plResponse.setCount(res.getCount());
				}
				else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}
	
	
	
	
	@ResponseBody
	@RequestMapping("check_before_buy/v_1.0.0")
	public String checkBeforeBuy(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, CheckBeforeBuyRequest.class, new ControllerCallBack() {
			
			@Override
			public void doCheck(Request request, Response response) {
				CheckBeforeBuyRequest checkBeforeBuyRequest = (CheckBeforeBuyRequest) request;
				Integer userId = checkBeforeBuyRequest.getUserId();
				Integer productId = checkBeforeBuyRequest.getProductId();
				if(userId == null || productId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}
			
			@Override
			public void doOperation(Request request, Response response) {
				CheckBeforeBuyRequest checkBeforeBuyRequest = (CheckBeforeBuyRequest) request;
				CheckBeforeBuyResponse checkBeforeBuyResponse = (CheckBeforeBuyResponse) response;
				
				ReqMsg_Product_CheckBeforeBuy req = new ReqMsg_Product_CheckBeforeBuy();
				req.setUserId(checkBeforeBuyRequest.getUserId());
				req.setProductId(checkBeforeBuyRequest.getProductId());
				req.setVersion("1.0.0");
				ResMsg_Product_CheckBeforeBuy res = appProductBusinessService.checkBeforeBuy(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					
				}else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}
	
	
	//余额购买页面信息_存管改造 v_1.0.0
	@ResponseBody
	@RequestMapping("balanceBuyInfo/v_1.0.0")
	public String balanceBuyInfo(HttpServletRequest req, HttpServletResponse resp) {
		return this.execute(req, BalanceBuyInfoRequest.class, new ControllerCallBack() {
			
			@Override
			public void doCheck(Request request, Response response) {
				BalanceBuyInfoRequest balanceBuyInfoRequest = (BalanceBuyInfoRequest) request;
				
				Integer userId = balanceBuyInfoRequest.getUserId();
				Integer productId = balanceBuyInfoRequest.getProductId();
				if(userId == null || productId == null) {
					throw new OpenException(OpenMessageEnum.REQUEST_PARAM_EMPTY.getCode(),OpenMessageEnum.REQUEST_PARAM_EMPTY.getMessage());
				}
			}
			
			@Override
			public void doOperation(Request request, Response response) {
				BalanceBuyInfoRequest balanceBuyInfoRequest = (BalanceBuyInfoRequest) request;
				BalanceBuyInfoResponse balanceBuyInfoResponse = (BalanceBuyInfoResponse) response;

				ReqMsg_Product_BalanceBuyInfo req = new ReqMsg_Product_BalanceBuyInfo();
				req.setUserId(balanceBuyInfoRequest.getUserId());
				req.setProductId(balanceBuyInfoRequest.getProductId());
				req.setVersion("1.0.0");
				ResMsg_Product_BalanceBuyInfo res = appProductBusinessService.balanceBuyInfo(req);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					balanceBuyInfoResponse.setProductId(res.getProductId());
					balanceBuyInfoResponse.setMinInvestAmount(res.getMinInvestAmount());
					balanceBuyInfoResponse.setName(res.getName());
					balanceBuyInfoResponse.setRate(res.getRate());
					balanceBuyInfoResponse.setTerm(String.valueOf(res.getTerm()));
					balanceBuyInfoResponse.setMaxSingleInvestAmount(res.getMaxSingleInvestAmount());
					balanceBuyInfoResponse.setPropertySymbol(res.getPropertySymbol());
					balanceBuyInfoResponse.setReturnType(res.getReturnType());
					balanceBuyInfoResponse.setIsSupportRedPacket(res.getIsSupportRedPacket());
					balanceBuyInfoResponse.setIsExistRedPacket(res.getIsExistRedPacket());
					balanceBuyInfoResponse.setRedPacketId(res.getRedPacketId());
					balanceBuyInfoResponse.setFull(res.getFull());
					balanceBuyInfoResponse.setSubtract(res.getSubtract());
					balanceBuyInfoResponse.setSerialName(res.getSerialName());
				}else {
					throw new OpenException(res.getResCode(),res.getResMsg());
				}
			}
		});
	}
	
	
	
	
}
