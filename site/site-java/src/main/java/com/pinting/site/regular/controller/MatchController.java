package com.pinting.site.regular.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.pinting.business.hessian.site.message.*;
import com.pinting.core.util.StringUtil;
import com.pinting.site.common.utils.GlobEnv;
import com.pinting.site.weichat.util.WeChatUtil;
import com.pinting.util.URLConstant;
import com.pinting.util.WeChatShareUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.site.common.enums.CookieEnums;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.util.Constants;

/**
 * 债权匹配相关
 * @author bianyatian
 * @2016-4-22 上午10:27:57
 */
@Controller
public class MatchController {
	@Autowired
	private CommunicateBusiness siteService;
	@Autowired
	private WeChatUtil weChatUtil;
	
	/**
	 * 债权匹配，需要登录拦截
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{channel}/match/myMatch")
	public String matchList(@PathVariable String channel, HttpServletRequest request,Integer pageIndex,
                                   HttpServletResponse response, Map<String, Object> model,
                                   String productId, Integer subAccountId,String entrustStatus){
		ResMsg_Match_GetUserMatchList res =new ResMsg_Match_GetUserMatchList();
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		//校验SubAccountId是否属于该用户
		boolean flag = checkUserIdSubAccountId(Integer.parseInt(userId), subAccountId);
		if (!flag) {
			if("micro2.0".equals(channel)) {
				String link = GlobEnv.getWebURL("/micro2.0/index");
				WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
				String url = channel + "/regular/detailsOfClaims_error_page";
				return url;
			}else {
				String url = channel + "/regular/detailsOfClaims_error_page";
				return url;
			}
		}else {
			try {
				share(channel, model, request, GlobEnv.getWebURL("/micro2.0/index"));
				String qianbao = request.getParameter("qianbao");
				// 组织请求报文
				ReqMsg_Match_GetUserMatchList req = new ReqMsg_Match_GetUserMatchList();
				if(StringUtils.isNotBlank(productId)){
					if(pageIndex == null || pageIndex<0){
						pageIndex = 0;
					}
					req.setStartPage(pageIndex * Constants.EXCHANGE_PAGE_SIZE);
					req.setUserId(Integer.parseInt(userId));
					req.setProductId(Integer.parseInt(productId));
					req.setSubAccountId(subAccountId);
					req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
					res = (ResMsg_Match_GetUserMatchList)siteService.handleMsg(req);

					model.put("total", res.getTotal());
					int pageNum = res.getTotal()%Constants.EXCHANGE_PAGE_SIZE == 0? res.getTotal()/Constants.EXCHANGE_PAGE_SIZE : res.getTotal()/Constants.EXCHANGE_PAGE_SIZE+1;
					model.put("pageNum",pageNum);
					model.put("pageIndex", pageIndex);

					ArrayList<HashMap<String, Object>> list = res.getDataGrid();
					if(!CollectionUtils.isEmpty(list)){
						for (HashMap<String, Object> hashMap : list) {
							String name = (String)hashMap.get("borrowerName");
							String borrowerName = name.substring(0,1);
							if(name.length() > 3) {
								borrowerName = borrowerName+"**";
							}else {
								for(int i= 0;i<name.length()-1;i++){
									borrowerName = borrowerName+"*";
								}
							}
							hashMap.put("borrowerName", borrowerName);
						}
					}
					model.put("dataList", res.getDataGrid());
					model.put("productId", productId);
					model.put("subAccountId", subAccountId);
				}
				model.put("qianbao", qianbao);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//资金接收方标记
			model.put("propertySymbol", res.getPropertySymbol());

			//3月10号后，云贷、7贷老产品重新匹配后 页面债权明细文案显示标志
			model.put("debtDetailsFlag", res.getDebtDetailsFlag() == null ? null : res.getDebtDetailsFlag());

			if (Constants.PROPERTY_SYMBOL_ZAN.endsWith(res.getPropertySymbol())) {
				//String entrustStatus = request.getParameter("entrustStatus");
				if (entrustStatus == null || StringUtils.isBlank(entrustStatus)) {
					model.put("entrustStatus", res.getEntrustStatus());
				}else{
					model.put("entrustStatus", entrustStatus);
				}
				model.put("entrustAmount", res.getEntrustAmount());
				return channel + "/match/my_match_zan";
			}else {
				return channel + "/match/my_match";
			}
		}
	}
	/**
	 * 债权匹配-加载更多
	 * @param channel
	 * @param pageIndex
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{channel}/match/myMatch_content")
	public String matchListContentInit(@PathVariable String channel,Integer pageIndex, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   String productId, Integer subAccountId){
		try {
			String qianbao = request.getParameter("qianbao");
			// 组织请求报文
			CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Match_GetUserMatchList req = new ReqMsg_Match_GetUserMatchList();
            if(StringUtils.isNotBlank(productId)){
            	req.setStartPage(pageIndex * Constants.EXCHANGE_PAGE_SIZE);
            	req.setUserId(Integer.parseInt(userId));
            	req.setProductId(Integer.parseInt(productId));
            	req.setSubAccountId(subAccountId);
            	req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
            	ResMsg_Match_GetUserMatchList res = (ResMsg_Match_GetUserMatchList)siteService.handleMsg(req);
            	
            	model.put("total", res.getTotal());
            	int pageNum = res.getTotal()%Constants.EXCHANGE_PAGE_SIZE == 0? res.getTotal()/Constants.EXCHANGE_PAGE_SIZE : res.getTotal()/Constants.EXCHANGE_PAGE_SIZE+1;
            	model.put("pageNum",pageNum);
            	model.put("pageIndex", pageIndex);
				//资金接收方标记
				model.put("propertySymbol", res.getPropertySymbol());
				//3月10号后，云贷、7贷老产品重新匹配后 页面债权明细文案显示标志
				model.put("debtDetailsFlag", res.getDebtDetailsFlag() == null ? null : res.getDebtDetailsFlag());

            	ArrayList<HashMap<String, Object>> list = res.getDataGrid();
            	if (list != null ) {
                	for (HashMap<String, Object> hashMap : list) {
                		String name = (String)hashMap.get("borrowerName");
                		String borrowerName = name.substring(0,1);
						if(name.length() > 3) {
							borrowerName = borrowerName+"**";
						}else {
							for(int i= 0;i<name.length()-1;i++){
								borrowerName = borrowerName+"*";
							}
						}

                		hashMap.put("borrowerName", borrowerName);
                	}
				}
            	model.put("dataList", res.getDataGrid());
            	
            }
            model.put("qianbao", qianbao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/match/my_match_content";
		
	}
	
	
	
	/**
	 * 委托计划债权匹配-加载更多
	 * @param channel
	 * @param pageIndex
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{channel}/match/myMatch_content_zan")
	public String matchZanListContentInit(@PathVariable String channel,Integer pageIndex, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model,
                                   String productId, Integer subAccountId){
		try {
			String qianbao = request.getParameter("qianbao");
			// 组织请求报文
			CookieManager manager = new CookieManager(request);
            String userId = manager.getValue(CookieEnums._SITE.getName(),
                CookieEnums._SITE_USER_ID.getName(), true);
            ReqMsg_Match_GetUserMatchList req = new ReqMsg_Match_GetUserMatchList();
            if(StringUtils.isNotBlank(productId)){
            	req.setStartPage(pageIndex * Constants.EXCHANGE_PAGE_SIZE);
            	req.setUserId(Integer.parseInt(userId));
            	req.setProductId(Integer.parseInt(productId));
            	req.setSubAccountId(subAccountId);
            	req.setPageSize(Constants.EXCHANGE_PAGE_SIZE);
            	ResMsg_Match_GetUserMatchList res = (ResMsg_Match_GetUserMatchList)siteService.handleMsg(req);
            	
            	model.put("total", res.getTotal());
            	int pageNum = res.getTotal()%Constants.EXCHANGE_PAGE_SIZE == 0? res.getTotal()/Constants.EXCHANGE_PAGE_SIZE : res.getTotal()/Constants.EXCHANGE_PAGE_SIZE+1;
            	model.put("pageNum",pageNum);
            	model.put("pageIndex", pageIndex);
            	ArrayList<HashMap<String, Object>> list = res.getDataGrid();
            	if (list != null ) {
                	for (HashMap<String, Object> hashMap : list) {
                		String name = (String)hashMap.get("borrowerName");
                		String borrowerName = name.substring(0,1);
						if(name.length() > 3) {
							borrowerName = borrowerName+"**";
						}else {
							for(int i= 0;i<name.length()-1;i++){
								borrowerName = borrowerName+"*";
							}
						}
                		hashMap.put("borrowerName", borrowerName);
                	}
				}
            	model.put("dataList", res.getDataGrid());
            	
            }
            model.put("qianbao", qianbao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return channel + "/match/my_match_content_zan";
		
	}
	
	
	/**
	 * 债权匹配，还款详情
	 * @param channel
	 * @param request
	 * @param response
	 * @param matchId
	 * @return
	 */
	@RequestMapping("/{channel}/match/myMatchDetail")
	@ResponseBody
	public Map<String, Object> myMatchDetail(@PathVariable String channel,HttpServletRequest request,
                                   HttpServletResponse response,
                                   String matchId, String propertySymbol, String subAccountId){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ReqMsg_Match_GetMatchRepayDetailList req = new ReqMsg_Match_GetMatchRepayDetailList();
			matchId = request.getParameter("matchId");
			propertySymbol = request.getParameter("propertySymbol");
			subAccountId = request.getParameter("subAccountId");
			String repayFlag = request.getParameter("repayFlag");
			if (Constants.REPAY_STATUS_FAIL_FLAG.equals(repayFlag) || Constants.REPAY_STATUS_SUCC_FLAG.equals(repayFlag)) {
				ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
				HashMap<String, Object> hashMap = new HashMap<>();
				hashMap.put("repayAmount", Double.parseDouble(request.getParameter("amount")));
				hashMap.put("repayTime", request.getParameter("lastRepayDate"));
				hashMap.put("note", "");
    			resultList.add(hashMap);
				result.put("dataList", resultList);
				return result;
			}
			if(StringUtil.isNotBlank(repayFlag)) {
				req.setRepayFlag(repayFlag);
			}
			if(StringUtil.isNotBlank(propertySymbol)) {
				req.setPropertySymbol(propertySymbol);
			}
			if(StringUtil.isNotBlank(subAccountId)) {
				req.setSubAccountId(subAccountId);
			}
			if(StringUtils.isNotBlank(matchId)){
            	
            	req.setMatchId(Integer.parseInt(matchId));
            	ResMsg_Match_GetMatchRepayDetailList res = (ResMsg_Match_GetMatchRepayDetailList)siteService.handleMsg(req);
            	ArrayList<HashMap<String, Object>> list = res.getList();
            	ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
            	if(!CollectionUtils.isEmpty(list)){
            		for (HashMap<String, Object> hashMap : list) {
                		Date repayTime = (Date) hashMap.get("repayTime");
            			if(repayTime != null){
            				hashMap.put("repayTime", DateUtil.formatYYYYMMDD(repayTime));
            			}
            			if(StringUtils.isEmpty((String)hashMap.get("note"))){
            				hashMap.put("note", "");
            			}
            			resultList.add(hashMap);
    				}
            	}
            	result.put("dataList", resultList);
            	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
		
	}
	
	
	/**
	 * 债权匹配--还款下详情（查询还款明细）
	 * @param channel
	 * @param request
	 * @param response
	 * @param matchId
	 * @return
	 */
	@RequestMapping("/{channel}/match/myMatchDetailZan")
	@ResponseBody
	public Map<String, Object> myMatchDetailZan(@PathVariable String channel,HttpServletRequest request,
                                   HttpServletResponse response,
                                   String matchId){
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			ReqMsg_Match_GetMatchRepayDetailZanList req = new ReqMsg_Match_GetMatchRepayDetailZanList();
			matchId = request.getParameter("matchId");
			if(StringUtils.isNotBlank(matchId)){
            	
            	req.setMatchId(Integer.parseInt(matchId));
            	ResMsg_Match_GetMatchRepayDetailZanList res = (ResMsg_Match_GetMatchRepayDetailZanList)siteService.handleMsg(req);
            	ArrayList<HashMap<String, Object>> list = res.getList();
            	ArrayList<HashMap<String, Object>> resultList = new ArrayList<HashMap<String, Object>>();
            	if(!CollectionUtils.isEmpty(list)){
            		for (HashMap<String, Object> hashMap : list) {
                		Date planDate = (Date) hashMap.get("planDate");
            			if(planDate != null){
            				hashMap.put("planDate", DateUtil.formatYYYYMMDD(planDate));
            			}else {
            				hashMap.put("planDate", "");
						}
            			
                		Date doneTime = (Date) hashMap.get("doneTime");
            			if(doneTime != null){
            				hashMap.put("doneTime", DateUtil.formatYYYYMMDD(doneTime));
            			}else {
            				hashMap.put("doneTime", "");
						}
            			
            			if(!StringUtils.isEmpty((String)hashMap.get("status"))){
            				String status = (String)hashMap.get("status");
            				if (Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_INIT.equals(status)) {
            					hashMap.put("note", "待还款");
							}else if (Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAYING.equals(status)) {
								hashMap.put("note", "还款中");
							}else if (Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAIED.equals(status)) {
								hashMap.put("note", "已还款");
							}else if (Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_ADVANCE.equals(status)) {
								hashMap.put("note", "已垫付");
							}
            				
            			}
            			resultList.add(hashMap);
    				}
            	}
            	result.put("dataList", resultList);
            	
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	
	/**
	 * 债权匹配，不需要登录拦截
	 * @param channel
	 * @param request
	 * @param response
	 * @param model
	 * @param productId
	 * @return
	 */
	@RequestMapping("/{channel}/match/myMatchApp")
	public String myMatchApp(@PathVariable String channel, HttpServletRequest request,Integer pageIndex,
                                   HttpServletResponse response, Map<String, Object> model,
                                   String productId, Integer subAccountId,String userId,String entrustStatus){
		ResMsg_Match_GetUserMatchList res =new ResMsg_Match_GetUserMatchList();
		
		try {
			String qianbao = request.getParameter("qianbao");
			// 组织请求报文
			ReqMsg_Match_GetUserMatchList req = new ReqMsg_Match_GetUserMatchList();
            if(StringUtils.isNotBlank(productId)){
            	if(pageIndex == null || pageIndex<0){
            		pageIndex = 0;
            	}
            	req.setStartPage(pageIndex * Constants.EXCHANGE_PAGE_SIZE);
            	req.setUserId(Integer.parseInt(userId));
            	req.setProductId(Integer.parseInt(productId));
            	req.setSubAccountId(subAccountId);
            	req.setPageSize(Constants.EXCHANGE_PAGE_SIZE_LONG);
            	res = (ResMsg_Match_GetUserMatchList)siteService.handleMsg(req);
            	
            	model.put("total", res.getTotal());
            	int pageNum = res.getTotal()%Constants.EXCHANGE_PAGE_SIZE == 0? res.getTotal()/Constants.EXCHANGE_PAGE_SIZE : res.getTotal()/Constants.EXCHANGE_PAGE_SIZE+1;
            	model.put("pageNum",pageNum);
            	model.put("pageIndex", pageIndex);
            	
            	ArrayList<HashMap<String, Object>> list = res.getDataGrid();
            	if(!CollectionUtils.isEmpty(list)){
            		for (HashMap<String, Object> hashMap : list) {
                		String name = (String)hashMap.get("borrowerName");
                		String borrowerName = name.substring(0,1);
						if(name.length() > 3) {
							borrowerName = borrowerName+"**";
						}else {
							for(int i= 0;i<name.length()-1;i++){
								borrowerName = borrowerName+"*";
							}
						}
                		hashMap.put("borrowerName", borrowerName);
                	}
            	}
            	model.put("dataList", res.getDataGrid());
            	model.put("productId", productId);
            	model.put("subAccountId", subAccountId);
            }
            model.put("qianbao", qianbao);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		//String entrustStatus = request.getParameter("entrustStatus");
		if (entrustStatus == null || StringUtils.isBlank(entrustStatus)) {
			model.put("entrustStatus", res.getEntrustStatus());
		}else{
			model.put("entrustStatus", entrustStatus);
		}
		
		return channel + "/match/my_match_zan_app";

	}


	/**
	 * 借款人详情
	 * @param channel
	 * @return
     */
	@RequestMapping(URLConstant.BORROWER_INFO)
	public String matchList(@PathVariable String channel, ReqMsg_Match_QueryBorrowerInfo req, Map<String, Object> model, HttpServletRequest request) {
		CookieManager manager = new CookieManager(request);
		String userId = manager.getValue(CookieEnums._SITE.getName(),
				CookieEnums._SITE_USER_ID.getName(), true);
		req.setUserId(Integer.parseInt(userId));
		ResMsg_Match_QueryBorrowerInfo res = (ResMsg_Match_QueryBorrowerInfo) siteService.handleMsg(req);
		if("920051".equals(res.getResCode())) {
			if (Constants.CHANNEL_MICRO.equals(channel)) {
				String link = GlobEnv.getWebURL("/micro2.0/index");
				WeChatShareUtil.share(channel, link, null, null, null, false, request, model, weChatUtil);
				model.put("borrower_msg", res.getResMsg());
				String url = channel + "/regular/detailsOfClaims_error_page";
				return url;
			} else {
				model.put("borrower_msg", res.getResMsg());
				String url = channel + "/regular/detailsOfClaims_error_page";
				return url;
			}
		}
		model.put("borrowerInfo", res.getBorrowerInfo());
		model.put("req", req);
		return channel + "/regular/agreement_borrower";
	}


	/**
	 * 分享
	 * @param channel   访问端口。micro2.0-H5；gen2.0-PC；gen178-PC_178
	 * @param model
	 * @param request
	 */
	private void share(String  channel, Map<String, Object> model, HttpServletRequest request, String link) {
		if(Constants.CHANNEL_MICRO.equals(channel)){
			String qianbao = request.getParameter("qianbao");
			if (StringUtil.isNotBlank(qianbao)
					&& Constants.USER_AGENT_QIANBAO.equals(qianbao)) {
				model.put("qianbao", Constants.USER_AGENT_QIANBAO);
				link += "?qianbao=qianbao";
				CookieManager manager = new CookieManager(request);
	            String viewAgentFlagCookie = manager.getValue(CookieEnums._VIEW.getName(), CookieEnums._VIEW_AGENT_FLAG.getName(), true);
		        if(StringUtil.isNotBlank(viewAgentFlagCookie)){
		        	link += "&agentViewFlag="+viewAgentFlagCookie;
		        }
				model.put("qianbao", qianbao);
			}
			// 分享
			Map<String, String> sign = weChatUtil.createAuth(request);
			sign.put("link", link);
			model.put("weichat", sign);
		}
	}

	/**
	 * 校验SubAccountId是否属于该用户
	 * @param userId
	 * @param subAccountId
	 * @return
	 */
	private boolean checkUserIdSubAccountId(int userId, int subAccountId) {
		ReqMsg_Account_CheckUserIdSubAccountId reqAccount = new ReqMsg_Account_CheckUserIdSubAccountId();
		reqAccount.setUserId(userId);
		reqAccount.setSubAccountId(subAccountId);

		ResMsg_Account_CheckUserIdSubAccountId resAccount = (ResMsg_Account_CheckUserIdSubAccountId) siteService.handleMsg(reqAccount);
		if (resAccount.getSubAccountId() == subAccountId) {
			return true;
		} else {
			return false;
		}
	}
	
}
