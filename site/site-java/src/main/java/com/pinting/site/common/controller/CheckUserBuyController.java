package com.pinting.site.common.controller;

import com.pinting.business.hessian.site.message.*;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * ajax校验用户是否是新用户，用户购买金额是否大于剩余额度或者大于可买额度
 *
 * @Project: site-java
 * @author yanwl
 * @Title: CheckUserBuyController.java
 * @date 2016-3-16 下午4:46:54
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Controller
public class CheckUserBuyController {
	@Autowired
	private CommunicateBusiness siteBusiness;
	
	@RequestMapping("/common/checkUserBuy")
	@ResponseBody
	public Map<String, Object> checkUserBuy(HttpServletRequest request, HttpServletResponse response){
		Map<String, Object> result = new HashMap<String, Object>();
		boolean isPass = true;
		String errMsg = "";
		
		// 组织请求报文
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),CookieEnums._SITE_USER_ID.getName(), true);
		String pId = request.getParameter("productId");
		if(StringUtil.isNotEmpty(pId)) {
			int proId = Integer.valueOf(pId);
			if(proId == Constants.PRODUCT_ID_NEWER_1_MONTH) {
				if(StringUtil.isNotEmpty(userId)) {
					ReqMsg_User_CheckNewUser req = new ReqMsg_User_CheckNewUser();
					req.setUserId(Integer.valueOf(userId));
					ResMsg_User_CheckNewUser res = (ResMsg_User_CheckNewUser)siteBusiness.handleMsg(req);
					if(!res.getIsNewUser()) {
						//校验该用户是否是新用户
						isPass = false;
						errMsg = "该用户不是新用户，不能购买此产品！！";
					}else {
						ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
						reqMsg.setId(proId);
						ResMsg_Product_InfoQuery resMsg = (ResMsg_Product_InfoQuery) siteBusiness.handleMsg(reqMsg);
						if(StringUtil.isNotEmpty(request.getParameter("amount"))) {
							//校验剩余额度是否满足购买金额
							double amount = Double.valueOf(request.getParameter("amount"));
							double proLimit = MoneyUtil.subtract(resMsg.getMaxTotalAmount() == null ? 0 : resMsg.getMaxTotalAmount(), resMsg.getCurrTotalAmount() == null ? 0 :resMsg.getCurrTotalAmount()).doubleValue();
							if(proLimit <= 0) {
								isPass = false;
								errMsg = "亲，没有剩余额度了~";
							}else {
								if(amount > proLimit) {
									isPass = false;
									errMsg = "亲，剩余额度没有这么多哦~";
								}else {
									//校验可买额度是否满足购买金额
									ReqMsg_UserProductLimit_UserProductLimitQuery reqUserProLimit = new ReqMsg_UserProductLimit_UserProductLimitQuery();
									reqUserProLimit.setUserId(Integer.valueOf(userId));
									reqUserProLimit.setProductId(proId);
									ResMsg_UserProductLimit_UserProductLimitQuery resUserProLimit = (ResMsg_UserProductLimit_UserProductLimitQuery)siteBusiness.handleMsg(reqUserProLimit);
									if(resUserProLimit.getLeftAmount() > 0) {
										if(amount > resUserProLimit.getLeftAmount()) {
											isPass = false;
											errMsg = "亲，可买额度没有这么多哦~";
										}
									}else {
										isPass = false;
										errMsg = "新用户没有可买额度不能购买哦~";
									}
								}
							}
						}else {
							isPass = false;
							errMsg = "亲，购买金额不能为空哦~";
						}
					}
				}else {
					isPass = false;
					errMsg = "用户没有登录，请先登录！！";
				}
			}else if(proId == Constants.PRODUCT_ID_ADD_RATE_1_YEAR) {
				ReqMsg_Product_InfoQuery reqMsg = new ReqMsg_Product_InfoQuery();
				reqMsg.setId(proId);
				ResMsg_Product_InfoQuery resMsg = (ResMsg_Product_InfoQuery) siteBusiness.handleMsg(reqMsg);
				if(StringUtil.isNotEmpty(request.getParameter("amount"))) {
					//校验剩余额度是否满足购买金额
					double amount = Double.valueOf(request.getParameter("amount"));
					double proLimit = MoneyUtil.subtract(resMsg.getMaxTotalAmount() == null ? 0 : resMsg.getMaxTotalAmount(), resMsg.getCurrTotalAmount() == null ? 0 :resMsg.getCurrTotalAmount()).doubleValue();
					if(proLimit <= 0) {
						isPass = false;
						errMsg = "亲，没有剩余额度了~";
					}else {
						if(amount > proLimit) {
							isPass = false;
							errMsg = "亲，剩余额度没有这么多哦~";
						}
					}
				}else {
					isPass = false;
					errMsg = "亲，购买金额不能为空哦~";
				}
			} else {
//			    // 是欧洲杯新手标
//			    ReqMsg_User_CheckCanBuyEcupNewUser req = new ReqMsg_User_CheckCanBuyEcupNewUser();
//			    req.setUserId(Integer.parseInt(userId));
//			    req.setProductId(proId);
//			    ResMsg_User_CheckCanBuyEcupNewUser res = (ResMsg_User_CheckCanBuyEcupNewUser) siteBusiness.handleMsg(req);
//			    if(false == res.getNewUser()) {
//			        isPass = false;
//                    errMsg = "该用户不是新用户，不能购买此产品！！";
//			    }
			}
		}else {
			isPass = false;
			errMsg = "产品编号不能为空！！";
		}
		
		result.put("isPass", isPass);
		result.put("errMsg", errMsg);
		return result;
	}
	
	
}
