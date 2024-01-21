package com.pinting.site.common.interceptor;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.pinting.business.hessian.site.message.ReqMsg_Product_InfoQuery;
import com.pinting.business.hessian.site.message.ReqMsg_SysConfig_QuerySysConfig;
import com.pinting.business.hessian.site.message.ReqMsg_UserProductLimit_UserProductLimitQuery;
import com.pinting.business.hessian.site.message.ReqMsg_User_CheckNewUser;
import com.pinting.business.hessian.site.message.ResMsg_Product_InfoQuery;
import com.pinting.business.hessian.site.message.ResMsg_SysConfig_QuerySysConfig;
import com.pinting.business.hessian.site.message.ResMsg_UserProductLimit_UserProductLimitQuery;
import com.pinting.business.hessian.site.message.ResMsg_User_CheckNewUser;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

/**
 * 预下单检验用户购买金额是否大于剩余额度、是否大于可买额度和该产品是否下架
 *
 * @Project: site-java
 * @author yanwl
 * @Title: CheckUserBuyInterceptor.java
 * @date 2016-3-16 下午7:36:34
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
public class CheckUserBuyInterceptor extends HandlerInterceptorAdapter{

	private Logger logger = LoggerFactory.getLogger(CheckUserBuyInterceptor.class);

	@Autowired
	private CommunicateBusiness interceptorService;
	
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String transType = request.getParameter("transType");
		logger.info("充值、购买拦截器请求信息。transType：{}，amount：{}，buyAmount：{}，money：{}，buyMoney：{}，productId：{}",
				transType, request.getParameter("amount"), request.getParameter("buyAmount"), request.getParameter("money"), request.getParameter("buyMoney")
				, request.getParameter("productId"));
		if(!"2".equals(transType) || ("2".equals(transType) && Constants.PC_BALANCE_USER_BUY.equals(request.getServletPath()))){
			try {
				String servletPath = request.getServletPath();
				String buyAmount = request.getParameter("amount");
				if(Constants.PC_BIND_CARD_USER_BUY.equals(servletPath) || Constants.PC_BIND_CARD_ORDER_USER_BUY.equals(servletPath)) {
					buyAmount = request.getParameter("buyAmount");
				}else if(Constants.PC_BANK_USER_BUY.equals(servletPath)) {
					buyAmount = request.getParameter("money");
				}else if(Constants.PC_BALANCE_USER_BUY.equals(servletPath)) {
					buyAmount = request.getParameter("buyMoney");
				}
				logger.info("购买拦截器请求信息。transType：{}，amount：{}，buyAmount：{}，money：{}，buyMoney：{}，productId：{}",
						transType, request.getParameter("amount"), request.getParameter("buyAmount"), request.getParameter("money"), request.getParameter("buyMoney")
						, request.getParameter("productId"));

				Map<String, Object> resultMap = new HashMap<String, Object>();
				String resCode = ConstantUtil.RESCODE_SUCCESS;
				String resMsg = "";
				String pId = request.getParameter("productId");
				CookieManager manager = new CookieManager(request);
				String userId = manager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
				if(StringUtil.isNotEmpty(userId)) {
					if(StringUtil.isNotEmpty(pId)) {
						int proId = Integer.valueOf(pId);
						if(StringUtil.isNotEmpty(buyAmount)) {
							if(proId == Constants.PRODUCT_ID_NEWER_1_MONTH) {
								ReqMsg_User_CheckNewUser req = new ReqMsg_User_CheckNewUser();
								req.setUserId(Integer.valueOf(userId));
								ResMsg_User_CheckNewUser res = (ResMsg_User_CheckNewUser)interceptorService.handleMsg(req);
								if(!res.getIsNewUser()) {
									//校验该用户是否是新用户
									resCode = "9100043";
									resMsg = "该用户不是新用户，不能购买此产品";
								}else {
									ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
									reqMsg.setId(proId);
									ResMsg_Product_InfoQuery proResMsg = (ResMsg_Product_InfoQuery) interceptorService.handleMsg(reqMsg);
									//校验剩余额度是否满足购买金额
									double amount = Double.valueOf(buyAmount);
									double proLimit = MoneyUtil.subtract(proResMsg.getMaxTotalAmount() == null ? 0 : proResMsg.getMaxTotalAmount(), proResMsg.getCurrTotalAmount()== null ? 0 : proResMsg.getCurrTotalAmount()).doubleValue();
									if(amount > proLimit) {
										resCode = "9100044";
										resMsg = "购买金额超出剩余额度";
									}else {
										//校验可买额度是否满足购买金额
										ReqMsg_UserProductLimit_UserProductLimitQuery reqUserProLimit = new ReqMsg_UserProductLimit_UserProductLimitQuery();
										reqUserProLimit.setUserId(Integer.valueOf(userId));
										reqUserProLimit.setProductId(proId);
										ResMsg_UserProductLimit_UserProductLimitQuery resUserProLimit = (ResMsg_UserProductLimit_UserProductLimitQuery)interceptorService.handleMsg(reqUserProLimit);
										if(resUserProLimit.getLeftAmount() > 0) {
											if(amount > resUserProLimit.getLeftAmount()) {
												resCode = "9100045";
												resMsg = "购买金额超出可买额度";
											}else {
												//判断活动是否下架
												ReqMsg_SysConfig_QuerySysConfig confReq = new ReqMsg_SysConfig_QuerySysConfig();
												confReq.setKey("CHECK_NEWUSER_END_TIME");
												ResMsg_SysConfig_QuerySysConfig confRes = (ResMsg_SysConfig_QuerySysConfig) interceptorService.handleMsg(confReq);
												String endTime = confRes.getConfValue();
												Date now = new Date();
												if(now.compareTo(DateUtil.parseDateTime(endTime)) > 0){
													resCode = "9100050";
													resMsg = "该产品已下架";
												}
											}
										}else {
											resCode = "9100046";
											resMsg = "新用户没有可买额度不能购买";
										}
									}
								}
							}else if(proId == Constants.PRODUCT_ID_ADD_RATE_1_YEAR) {
								ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
								reqMsg.setId(proId);
								ResMsg_Product_InfoQuery proResMsg = (ResMsg_Product_InfoQuery) interceptorService.handleMsg(reqMsg);
								//校验剩余额度是否满足购买金额
								double amount = Double.valueOf(buyAmount);
								double proLimit = MoneyUtil.subtract(proResMsg.getMaxTotalAmount() == null ? 0 : proResMsg.getMaxTotalAmount(), proResMsg.getCurrTotalAmount() == null ? 0 : proResMsg.getCurrTotalAmount()).doubleValue();
								if(amount > proLimit) {
									resCode = "9100044";
									resMsg = "购买金额超出剩余额度";
								}else {
									//判断活动是否下架
									ReqMsg_SysConfig_QuerySysConfig confReq = new ReqMsg_SysConfig_QuerySysConfig();
									confReq.setKey("CHECK_NEWUSER_END_TIME");
									ResMsg_SysConfig_QuerySysConfig confRes = (ResMsg_SysConfig_QuerySysConfig) interceptorService.handleMsg(confReq);
									String endTime = confRes.getConfValue();
									Date now = new Date();
									if(now.compareTo(DateUtil.parseDateTime(endTime)) > 0){
										resCode = "9100050";
										resMsg = "该产品已下架";
									}
								}
							}
						}else {
							resCode = "9100047";
							resMsg = "购买金额不能为空";
						}
					}else {
						resCode = "9100048";
						resMsg = "产品编号不能为空";
					}
				}else {
					resCode = "9100049";
					resMsg = "该用户没有登录";
				}
				
				resultMap.put("resCode", resCode);
				resultMap.put("resMsg", resMsg);
				request.setAttribute("resultMap", resultMap);
			}catch (Exception e) {
				e.printStackTrace();
			}
		}
		return true;
	}
}
