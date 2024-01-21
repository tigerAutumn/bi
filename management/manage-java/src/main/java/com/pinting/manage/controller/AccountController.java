package com.pinting.manage.controller;

import com.pinting.business.accounting.service.CheckAccountService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.MUserOpRecord;
import com.pinting.business.model.vo.AvailableClaimVO;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.model.vo.LnPayOrdersVO;
import com.pinting.business.model.vo.LoanNoticeVO;
import com.pinting.business.model.vo.ZsdAvailableClaimVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.service.manage.ResidentOrderService;
import com.pinting.business.service.site.AccountService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.manage.model.SysReturnMoneyNoticeDo;
import com.pinting.manage.service.MSysReturnMoneyService;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import com.pinting.util.Messages;
import com.pinting.util.ReturnDWZAjax;

import jxl.Workbook;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.UnderlineStyle;
import jxl.write.*;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

@Controller
public class AccountController {
	 private Logger log = LoggerFactory.getLogger(AccountController.class);
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService accountService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private CheckAccountService checkAccountService;
	@Autowired
    private SpecialJnlService specialJnlService;
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private ResidentOrderService residentOrderService;
	@Autowired
	private PayOrdersService payOrdersService;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private MStatisticsService mStatisticsService;
	@Autowired
	private AlgorithmService algorithmService;
	@Autowired
	private AccountService accService;
	
	@RequestMapping("/account/acctTransRepeatSend/index")
	public String acctTransRepeatSendIndex(HashMap<String,Object> model){
		
		return "/account/checkError/acctTransRepeatSend";
	}
	@RequestMapping("/account/acctTransRepeatSend")
	public @ResponseBody Map<Object,Object> acctTransRepeatSend(String subAccountId, HashMap<String,Object> model){
		ReqMsg_MAccount_ManualWorkAcctTrans4Buy acctTrans = new ReqMsg_MAccount_ManualWorkAcctTrans4Buy();
		acctTrans.setSubAccountId(Integer.valueOf(subAccountId));
		ResMsg_MAccount_ManualWorkAcctTrans4Buy resp = (ResMsg_MAccount_ManualWorkAcctTrans4Buy) accountService.handleMsg(acctTrans);
		
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("转账购买已提交，交易结果需确认！");
		} else {
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
		
	}
	
	@RequestMapping("/account/acctTransAllSend")
	public @ResponseBody Map<Object,Object> acctTransAllSend(String subAccountId, HashMap<String,Object> model){
		ReqMsg_MAccount_ManualWorkAcctTrans4BuyAll acctTrans = new ReqMsg_MAccount_ManualWorkAcctTrans4BuyAll();
		ResMsg_MAccount_ManualWorkAcctTrans4BuyAll resp = (ResMsg_MAccount_ManualWorkAcctTrans4BuyAll) accountService.handleMsg(acctTrans);
		
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("转账购买已提交，交易结果需确认！");
		} else {
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
		
	}
	
	@RequestMapping("/account/checkError/index")
	public String checkErrorInit(ReqMsg_MAccount_CheckErrorListQuery req,HashMap<String,Object> model){
		
		ResMsg_MAccount_CheckErrorListQuery res = (ResMsg_MAccount_CheckErrorListQuery) accountService.handleMsg(req);
		res.setBeginTime(req.getBeginTime());
		res.setOverTime(req.getOverTime());
		model.put("product", res);
		model.put("accountList", res.getValueList());
		
		return "/account/checkError/index";
	}
	/**
	 * 币港湾投资收益统计查询
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/profitLoss/index")
	public String profitLossInit(ReqMsg_MAccount_ProfitLossListQuery req,HashMap<String,Object> model){
		
		ResMsg_MAccount_ProfitLossListQuery res = (ResMsg_MAccount_ProfitLossListQuery) accountService.handleMsg(req);
		res.setBeginTime(req.getBeginTime());
		res.setOverTime(req.getOverTime());
		model.put("product", res);
		model.put("accountList", res.getValueList());
		
		return "/account/profitLoss/index";
	}
	
	@RequestMapping("/account/checkError/check")
	public @ResponseBody Map<Object,Object> checkErrorSave(ReqMsg_MAccount_CheckErrorSave req,HttpServletRequest request){
		
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setmUserid(Integer.parseInt(mUserId));
		
		try{
			ResMsg_MAccount_CheckErrorSave resMsg = (ResMsg_MAccount_CheckErrorSave) accountService.handleMsg(req);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
				return ReturnDWZAjax.success("处理成功！");
			}else{
				return ReturnDWZAjax.fail("处理失败！");
			}
		} catch(Exception e){
			e.printStackTrace();
			return ReturnDWZAjax.fail("处理失败！");
		}
	}
	
	@RequestMapping("/account/transfer/index")
	public String transferInit(ReqMsg_MAccount_TransferListQuery req,HashMap<String,Object> model){
		
		
		ResMsg_MAccount_TransferListQuery res = (ResMsg_MAccount_TransferListQuery) accountService.handleMsg(req);
		res.setBeginTime(req.getBeginTime());
		res.setOverTime(req.getOverTime());
		res.setStatus(req.getStatus());
		model.put("product", res);
		model.put("accountList", res.getValueList());
		
		return "/account/transfer/index";
	}
	
	/**
	 * 订单查询（超级管理员）
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/order/index")
	public String orderInit(ReqMsg_MAccount_OrderListQuery req, HttpServletRequest request, HashMap<String,Object> model){

		log.error("request time" + new Date().getTime());
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			req.setPageNum(pageNum);
		}
		if (request.getParameter("orderDirection") != null
				&& request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("createTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		
		ResMsg_MAccount_OrderListQuery res = (ResMsg_MAccount_OrderListQuery) accountService.handleMsg(req);
		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);
		model.put("req", req);
		model.put("product", res);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("accountList", res.getValueList());
		model.put("buyBankTypeLists", res.getBuyBankTypeList());
		//model.put("agents", res.getAgentList());
		if(req.getOrderField()!=null){
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		log.error("response time" + new Date().getTime());
        return "/account/order/index";
	}
	
	/**
	 * 订单查询（普通管理员）
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/order/indexNormal")
    public String indexNormal(ReqMsg_MAccount_OrderListQuery4Normal req, HttpServletRequest request, HashMap<String,Object> model){
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            req.setPageNum(pageNum);
        }
        if (request.getParameter("orderDirection") != null
                && request.getParameter("orderField") != null) {
            req.setOrderDirection(request.getParameter("orderDirection"));
            req.setOrderField(request.getParameter("orderField"));
            model.put("orderField", request.getParameter("orderField"));
            model.put("orderDirection", request.getParameter("orderDirection"));
        } else {
            req.setOrderDirection("desc");
            req.setOrderField("createTime");
            model.put("orderField", req.getOrderField());
            model.put("orderDirection", req.getOrderDirection());
        }
        if(StringUtil.isNotBlank(req.getUserMobile()) || StringUtil.isNotBlank(req.getUserName())){
        	/*管理用户操作登记表新增数据*/
    		/*MUserOpRecord record = new MUserOpRecord();
    		CookieManager cookie = new CookieManager(request);
			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
			if(StringUtils.isBlank(mUserId)){
				req.setUserMobile("");
				req.setUserName("");
			}else{
				record.setOpUserId(Integer.parseInt(mUserId));
				record.setFunctionName("订单跟踪");
				record.setNote("按条件查询");
				if(StringUtil.isNotBlank(req.getUserMobile())){
        			record.setOpContent("手机号："+req.getUserMobile());
        		}
    			if(StringUtil.isNotBlank(req.getUserName())){
        			record.setOpContent(record.getOpContent()!=null?record.getOpContent()+"，姓名："+req.getUserName():"姓名："+req.getUserName());
        		}
        		
        		String ip = getIp(request);
        		record.setIp(ip);
        		record.setFunctionUrl("/account/order/indexNormal");
        		mUserOpRecordService.addMUserOpRecord(record);
			}*/
        }
        ResMsg_MAccount_OrderListQuery4Normal res = (ResMsg_MAccount_OrderListQuery4Normal) accountService.handleMsg(req);
        int agentTotal = agentService.findAgentsTotalRows();
        model.put("agentTotal", agentTotal);
        model.put("req", req);
        model.put("product", res);
        model.put("pageNum", req.getPageNum());
        model.put("numPerPage", res.getNumPerPage());
        model.put("totalRows", res.getTotalRows());
        model.put("accountList", res.getValueList());
        model.put("buyBankTypeLists", res.getBuyBankTypeList());
        //model.put("agents", res.getAgentList());
        if(req.getOrderField()!=null){
            model.put(req.getOrderField(), req.getOrderDirection());
            model.put("orderField", req.getOrderField());
            model.put("orderDirection",req.getOrderDirection());
        }
        
        return "/account/order/index_normal";
    }

	/**
	 * 驻留订单管理-列表
	 * @param request
	 * @param model
	 * @param orderNo
     * @return
     */
	@RequestMapping("/account/order/residentOrder")
	public String residentOrderInit(HttpServletRequest request, HashMap<String, Object> model, String orderNo) {
		List<LoanNoticeVO> list = residentOrderService.residentOrderManage(orderNo);
		if(list != null && list.size() > 0) {
			ArrayList<HashMap<String, Object>> dataList = BeanUtils.classToArrayList(list);
			model.put("dataList", dataList);
			model.put("orderNo", orderNo);
		}

		return "/account/order/resident_order";
	}

	/**
	 * 驻留订单管理-删除
	 * @param request
	 * @param response
	 * @param model
	 * @param orderNo
     */
	@RequestMapping("/account/order/residentOrderDelete")
	public @ResponseBody Map<Object, Object> delete(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String orderNo) {
		boolean flag = residentOrderService.deleteResidentOrder(orderNo);
		if (flag) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			return ReturnDWZAjax.fail("删除失败，请重试！");
		}
	}

	/**
	 * 驻留订单管理-进入添加订单页面
	 * @param request
	 * @return
     */
	@RequestMapping("/account/order/residentOrderDetail")
	public String residentOrderDetail(HttpServletRequest request) {
		return "/account/order/resident_detail";
	}

	/**
	 * 驻留订单管理-添加订单
	 * @param request 
	 * @param response
	 * @param model
	 * @param orderNo
	 */
	@RequestMapping("/account/order/residentOrderSave")
	public @ResponseBody Map<Object, Object> save(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, String orderNo, Integer orderStatus) {
		int record = 0;
		record = residentOrderService.addResidentOrder(orderNo,orderStatus);
		if (record == 1) {
			return ReturnDWZAjax.success("订单添加成功，信息已存入redis！");
		} else {
			return ReturnDWZAjax.toAjaxString("301", "订单不存在！");
		}
	}

	private String getIp(HttpServletRequest request) {
		String address = request.getHeader("X-Forwarded-For");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			if(address.contains(",")) {
				log.info("X-Forwarded-For:"+address);
			}
			return address;
		}
		address = request.getHeader("Proxy-Client-IP");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			if(address.contains(",")) {
				log.info("Proxy-Client-IP:"+address);
			}
			return address;
		}
		address = request.getHeader("WL-Proxy-Client-IP");
		if (address != null && address.length() > 0
				&& !"unknown".equalsIgnoreCase(address)) {
			if(address.contains(",")) {
				log.info("WL-Proxy-Client-IP:"+address);
			}
			return address;
		}
		if(request.getRemoteAddr().contains(",")) {
			log.info("RemoteAddr:"+request.getRemoteAddr());
		}
		return request.getRemoteAddr();
	}
	@ResponseBody
    @RequestMapping("/account/order/indexNormal/getUserInfo")
    public Map<String, Object> channelWithdraw(HttpServletRequest request, String userId, String infoFlag){
        Map<String, Object> map = new HashMap<String, Object>();
        if(StringUtil.isNotBlank(userId)){
        	/*获取用户信息*/
        	BsUser user = bsUserService.findUserByUserId(Integer.parseInt(userId));
        	if(user != null){
        		/*管理用户操作登记表新增数据*/
        		MUserOpRecord record = new MUserOpRecord();
        		CookieManager cookie = new CookieManager(request);
    			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
    					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
    			if(StringUtils.isBlank(mUserId)){
    				map.put("errorMsg", "未获取到登录者信息");
    			}else{
    				/*record.setOpUserId(Integer.parseInt(mUserId));
    				record.setFunctionName("订单跟踪");
    				record.setNote("单列单行获取");*/
            		map.put("userName", user.getUserName()== null?"":user.getUserName());
            		map.put("userMobile", user.getMobile());
            		/*record.setOpContent(user.getUserName()== null?"手机号："+user.getMobile()+"，姓名：":"手机号："+user.getMobile()+"，姓名："+user.getUserName());
            		String ip = getIp(request);
            		record.setIp(ip);
            		record.setFunctionUrl("/account/order/indexNormal/getUserInfo");
            		mUserOpRecordService.addMUserOpRecord(record);*/
    			}
        	}
        }
        return map;
    }
	
	/**
	 * 导出Excel
	 * @return
	 */
	@RequestMapping("/account/order/exportExcel")
	public void exportExcel(ReqMsg_MAccount_OrderListQuery req, HttpServletRequest request, HttpServletResponse response) {
	    req.setNumPerPage(0);
	    ResMsg_MAccount_OrderListQuery res = (ResMsg_MAccount_OrderListQuery) accountService.handleMsg(req);
	    if(Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
	        ArrayList<HashMap<String, Object>> dataGrid = res.getValueList();
	        List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
	        Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
	        List<Object> titles = new ArrayList<Object>();
	        titles.add("注册手机号");
	        titles.add("预留手机号");
	        titles.add("姓名");
	        titles.add("身份证");
	        titles.add("订单号");
	        titles.add("银行卡");
	        titles.add("金额");
	        titles.add("状态");
	        titles.add("返回信息");
	        titles.add("银行类型");
	        titles.add("支付渠道");
	        titles.add("渠道来源");
	        titles.add("交易时间");
	        titles.add("终端类型");
	        titles.add("交易类型");
	        titles.add("返回码");
	        titles.add("更新时间");
	        titleMap.put("title", titles);
	        excelList.add(titleMap);
	        
            int i = 0;
            if (!CollectionUtils.isEmpty(dataGrid)) {
                for (HashMap<String, Object> data : dataGrid) {
                    Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
                    List<Object> objs = new ArrayList<Object>();
                    objs.add(data.get("userMobile") == null ? "" : data.get("userMobile"));
                    objs.add(data.get("mobile") == null ? "" : data.get("mobile"));
                    objs.add(data.get("userName") == null ? "" : data.get("userName"));
                    objs.add(data.get("idCard") == null ? "" : data.get("idCard"));
                    objs.add(data.get("orderNo") == null ? "" : data.get("orderNo"));
                    objs.add(data.get("bankCardNo") == null ? "" : data.get("bankCardNo"));
                    objs.add(data.get("amount") == null ? "" : MoneyUtil.format((Double)data.get("amount")));
                    switch ((Integer)data.get("status")) {
                        case 1:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_1);
                            break;
                        case 2:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_2);
                            break;
                        case 3:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_3);
                            break;
                        case 4:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_4);
                            break;
                        case 5:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_5);
                            break;
                        case 6:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_6);
                            break;
                        case 7:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_7);
                            break;
                        default:
                            break;
                    }
                    objs.add(data.get("returnMsg") == null ? "" : data.get("returnMsg"));
                    objs.add(data.get("bankName") == null ? "" : data.get("bankName"));
                    if("REAPAL".equals(data.get("payChannel"))) {
                        objs.add(data.get("payChannel") == null ? "" : "融宝");
                    } else if("PAY19".equals(data.get("payChannel"))) {
                        objs.add(data.get("payChannel") == null ? "" : "19付");
                    } else if("DAFY".equals(data.get("payChannel"))) {
                        objs.add(data.get("payChannel") == null ? "" : "达飞");
                    } else {
                        objs.add("");
                    }
                    objs.add(data.get("agentName") == null ? "" : data.get("agentName"));
                    objs.add(data.get("createTime") == null ? "" : DateUtil.format((Date)data.get("createTime")));
                    if(null != data.get("terminalType")) {
                        switch ((Integer)data.get("terminalType")) {
                            case 1:
                                objs.add(data.get("terminalType") == null ? "" : Messages.ACCOUNT_ORDERS_TERMINAL_TYPE_1);
                                break;
                            case 2:
                                objs.add(data.get("terminalType") == null ? "" : Messages.ACCOUNT_ORDERS_TERMINAL_TYPE_2);
                                break;
                            case 3:
                                objs.add(data.get("terminalType") == null ? "" : Messages.ACCOUNT_ORDERS_TERMINAL_TYPE_3);
                                break;
                            case 4:
                                objs.add(data.get("terminalType") == null ? "" : Messages.ACCOUNT_ORDERS_TERMINAL_TYPE_4);
                                break;
                            default:
                                objs.add("");
                                break;
                        }
                    } else {
                        objs.add("");
                    }
                   
                    if("CARD_BUY_PRODUCT".equals(data.get("transType"))){
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_CARD_BUY_PRODUCT);
                    } else if("BALANCE_BUY_PRODUCT".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BALANCE_BUY_PRODUCT);
                    } else if("TOP_UP".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_TOP_UP);
                    } else if("FREEZE".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_FREEZE);
                    } else if("RETURN_2_BALANCE".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_RETURN_2_BALANCE);
                    } else if("RETURN_2_USER_BANK_CARD".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_RETURN_2_USER_BANK_CARD);
                    } else if("BONUS_2_BALANCE".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BONUS_2_BALANCE);
                    } else if("RECOMMEND_BONUS".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_RECOMMEND_BONUS);
                    } else if("BONUS_WITHDRAW".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BONUS_WITHDRAW);
                    } else if("BALANCE_WITHDRAW".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BALANCE_WITHDRAW);
                    } else if("CHANNEL_WITHDRAW".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_CHANNEL_WITHDRAW);
                    } else {
                        objs.add("");
                    }
                    
                    objs.add(data.get("returnCode") == null ? "" : data.get("returnCode"));
                    objs.add(data.get("updateTime") == null ? "" : DateUtil.format((Date)data.get("updateTime")));
                    datasMap.put("order"+i, objs);
                    i++;
                    excelList.add(datasMap);
                }
            }
            try {
                ExportUtil.exportExcel(response, request, "系统订单数据", excelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
	    }
	}
	
	
	@RequestMapping("/account/customerWithdraw")
	public String customerWithdrawInit(ReqMsg_MAccount_CustomerWithdraw req,HashMap<String,Object> model){
		
		
		ResMsg_MAccount_CustomerWithdraw res = (ResMsg_MAccount_CustomerWithdraw) accountService.handleMsg(req);
		model.put("req", req);
		model.put("product", res);
		model.put("accountList", res.getValueList());
		
		return "/account/customerWithdraw/index";
	}
	@RequestMapping("/pinting/account/assetsList")
	public String assetsListInit(ReqMsg_MAccount_AssetsList req,HashMap<String,Object> model){
		
		
		ResMsg_MAccount_AssetsList res = (ResMsg_MAccount_AssetsList) accountService.handleMsg(req);
		model.put("req", req);
		model.put("product", res);
		model.put("accountList", res.getValueList());
		
		return "/pinting/assetsList";
	}
	
	@RequestMapping("/account/transfer/detail")
	public String transferDetail(ReqMsg_MAccount_TransferDetail req,HashMap<String,Object> model){
		
		ResMsg_MAccount_TransferDetail res = (ResMsg_MAccount_TransferDetail)accountService.handleMsg(req);
		model.put("accountJnl", res);
		return "/account/transfer/detail";
	}
	
	@RequestMapping("/account/checkJnl/detail")
	public String checkJnlDetail(ReqMsg_MAccount_CheckJnlDetail req,HashMap<String,Object> model){
		
		ResMsg_MAccount_CheckJnlDetail res = (ResMsg_MAccount_CheckJnlDetail)accountService.handleMsg(req);
		model.put("checkJnl", res);
		return "/account/checkJnl/detail";
	}
	
	@RequestMapping("/account/investMature/warm")
	public String investMatureWarmInit(ReqMsg_MAccount_InvestMatureWarm req, HashMap<String,Object> model){
		
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0){
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		
		ResMsg_MAccount_InvestMatureWarm res = (ResMsg_MAccount_InvestMatureWarm) accountService.handleMsg(req);
		
		model.put("req", req);
		model.put("warmList",res.getValueList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		return "/account/investMature/warm";
	}
	/**
	 * 品听账户查询
	 * @param req
	 * @param response
	 * @return
	 */
	@RequestMapping("/pinting/account/index")
	public String pintingAccountInit(ReqMsg_Statistics_WXAccountDetailQuery req , HttpServletResponse response,HashMap<String,Object> model){
		String pageNum = req.getPageNum();
		String numPerPage = req.getNumPerPage();
		if(pageNum == null && numPerPage == null){
			pageNum ="1";
			numPerPage = "20";
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(req.getStartDate()==null)
		{
			req.setStartDate(DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addMonths(new Date(), -1))));
		}
		if(req.getEndDate()==null)
		{
			
			req.setEndDate(DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), 1))));
		}
		ResMsg_Statistics_WXAccountDetailQuery res = (ResMsg_Statistics_WXAccountDetailQuery) accountService.handleMsg(req);
		model.put("List",res.getData());
		model.put("numPerPage", numPerPage);
		model.put("pageNum",pageNum);
		model.put("totalRows", res.getCount()==null?0:res.getCount());
		model.put("Balance", res.getBalance());
		model.put("transType", req.getTransType());
		model.put("startDate", req.getStartDate());
		model.put("endDate", req.getEndDate());
		return "pinting/account";
	}
	
	/**
	 * 品听提现初始化
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pinting/account/withdrawCash")
	public String puntingWithdrawCashInit(HttpServletRequest request, HttpServletResponse response){
		return "pinting/withdrawCash";
	}
	
	/**
	 * 品听提现
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/pinting/account/withdrawCashSubmit")
	public @ResponseBody Map<Object,Object> puntingWithdrawCashSubmit(HttpServletRequest request, HttpServletResponse response,ReqMsg_MAccount_SysWithdraw req){
		HashMap<Object,Object> result = new HashMap<Object,Object>();
		
		ResMsg_MAccount_SysWithdraw res = (ResMsg_MAccount_SysWithdraw) accountService.handleMsg(req);
		
		if(res.getResCode().equals(Constants.BSRESCODE_SUCCESS)){
			result.put("error", "no");
		}else{
			result.put("error", "yes");
			result.put("message", res.getResMsg());
		}
		
		return result;
	}
	
	@RequestMapping("/pinting/account/withdrawDetail")
	public String pintinWithdrawDetail(HttpServletRequest request,HttpServletResponse response, ReqMsg_MAccount_SysWithdrawDetailListQuery req,HashMap<String,Object> model){
		
		
		ResMsg_MAccount_SysWithdrawDetailListQuery res = (ResMsg_MAccount_SysWithdrawDetailListQuery) accountService.handleMsg(req);
		
		model.put("req", req);
		model.put("withdrawList", res.getValueList());
		model.put("product", res);
		return "pinting/withdrawDetail";
	}
	
	/**
	 * 用户提现查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/userWithdraw")
	public String userWithdrawInit(ReqMsg_MAccount_UserWithdraw req, HttpServletRequest request, HashMap<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if(orderDirection != null && orderField != null) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("createTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_MAccount_UserWithdraw res = (ResMsg_MAccount_UserWithdraw) accountService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("orderDirection", orderDirection);
		model.put("orderField", orderField);
		model.put("totalRows", res.getTotalRows());
		model.put("sumUserAmount", res.getSumUserAmount());
		model.put("withdrawList", res.getValueList());
		if(req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		return "/account/customerWithdraw/userIndex";
		
	}
	
	/**
	 * 用户奖励金查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/bonus/index")
	public String userBonusInit(ReqMsg_MAccount_BonusListQuery req, HttpServletRequest request, HashMap<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if(orderDirection != null && orderField != null) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("sysTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_MAccount_BonusListQuery res = (ResMsg_MAccount_BonusListQuery) accountService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("totalRows", res.getTotalRows());
		model.put("sumBonusAmount", res.getSumBonusAmount());
		model.put("bonusAmountList", res.getValueList());
		if(req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		return "/account/bonus/index";
	}
	
	/**
	 * 用户充值查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/topUp/index")
	public String userTopUpInit(ReqMsg_MAccount_TopUpListQuery req, HttpServletRequest request, HashMap<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if(orderDirection != null && orderField != null) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
			
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("update_time");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_MAccount_TopUpListQuery res = (ResMsg_MAccount_TopUpListQuery) accountService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("orderDirection", orderDirection);
		model.put("orderField", orderField);
		model.put("totalRows", res.getTotalRows());
		model.put("sumTopUpAmount", res.getSumTopUpAmount());
		model.put("topUpAmountList", res.getValueList());
		if(req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		return "/account/topUp/index";
	}
	
	/**
	 * 用户记账流水查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/chargeAccount/index")
	public String userChargeAccountInit(ReqMsg_MAccount_UserChargeAccountQuery req, HttpServletRequest request, HashMap<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if(orderDirection != null && orderField != null) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("sysTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		if(req.getQueryFlag() != null && req.getQueryFlag().equals("QUERY")){
			ResMsg_MAccount_UserChargeAccountQuery res = (ResMsg_MAccount_UserChargeAccountQuery) accountService.handleMsg(req);
			model.put("totalRows", res.getTotalRows());
			model.put("changeAmountList", res.getValueList());
		}
		
		model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("orderDirection", orderDirection);
		model.put("orderField", orderField);

		if(req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		return "/account/chargeAccount/index";
	}
	
	/**
	 * 订单明细流水查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/order/orderDetail")
	public String orderDetailInit(ReqMsg_MAccount_OrderDetailQuery req, HttpServletRequest request, HashMap<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if(orderDirection != null && orderField != null) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("sysTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		if(req.getQueryFlag() != null && req.getQueryFlag().equals("QUERY")){
			ResMsg_MAccount_OrderDetailQuery res = (ResMsg_MAccount_OrderDetailQuery) accountService.handleMsg(req);
			model.put("totalRows", res.getTotalRows());
			model.put("orderDetailList", res.getValueList());
		}
		model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("orderDirection", orderDirection);
		model.put("orderField", orderField);
		if(req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		return "/account/order/orderDetail";
	}
	
	/**
	 * 系统记账流水查询
	 * @param req
	 * @param request
	 * @param model
	 */
	@RequestMapping("/account/sysAccount/index")
	public String sysAccountInit(ReqMsg_MAccount_SysAccountQuery req, HttpServletRequest request, HashMap<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if(orderDirection != null && orderField != null) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("sysTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		if(req.getQueryFlag() != null && req.getQueryFlag().equals("QUERY")){
			ResMsg_MAccount_SysAccountQuery res = (ResMsg_MAccount_SysAccountQuery) accountService.handleMsg(req);
			model.put("totalRows", res.getTotalRows());
			model.put("sysAccountList", res.getValueList());
		}
		model.put("req", req);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("orderDirection", orderDirection);
		model.put("orderField", orderField);
		if(req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		return "/account/sysAccount/index";
	}
	
	/**
	 * 订单处理中转成功或失败（业务）
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/order/toFail")
	public String orderProcessing(ReqMsg_MAccount_OrderTrocessing req, HashMap<String, Object> model) {
		model.put("orderNo", req.getOrderNo());
		return "/account/order/detail";
	}
	
	/**
	 * 融宝对账
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/account/order/reapalCheck")
	public Map<Object, Object> reapalCheck(HttpServletResponse response,
			HttpServletRequest request){
		String subAccountId = request.getParameter("subAccountId");
		String orderNo = request.getParameter("orderNo");
		String status = request.getParameter("status");
		String amount = request.getParameter("amount");
		BsPayOrders order = new BsPayOrders();
		if(StringUtils.isNotEmpty(subAccountId)){
			order.setSubAccountId(Integer.parseInt(subAccountId));
		}else{
			return ReturnDWZAjax.fail("操作异常，未取得子账户编号");
		}
		if(StringUtils.isNotEmpty(amount)){
			order.setAmount(Double.valueOf(amount));
		}else{
			return ReturnDWZAjax.fail("操作异常，未取得订单金额");
		}
		order.setOrderNo(orderNo);
		if(StringUtils.isNotEmpty(status)){
			order.setStatus(Integer.parseInt(status));
		}else{
			return ReturnDWZAjax.fail("操作异常，未取得状态");
		}
		
		try {
			checkAccountService.checkReapalSingle(order);
			return ReturnDWZAjax.success("操作成功！");
		} catch (Exception e) {
			log.error("==================管理台{融宝处理中订单对账}单笔订单"+order.getOrderNo()+"对账失败====================", e);
            //告警
            specialJnlService.warn4Fail(null, "定时任务{融宝处理中订单对账}单笔"+order.getOrderNo()+"订单对账失败：" + StringUtil.left(e.getMessage(), 20), order.getOrderNo(), "融宝单笔对账异常失败",true);
            return ReturnDWZAjax.fail("操作异常，请检查该笔操作");
		}
	}
	
	@ResponseBody
	@RequestMapping("/account/order/processToFail")
	public Map<Object, Object> processToFail(ReqMsg_MAccount_OrderTrocessing req, HashMap<String, Object> model) {
		ResMsg_MAccount_OrderTrocessing resp = (ResMsg_MAccount_OrderTrocessing) accountService.handleMsg(req);
		
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())){
			return ReturnDWZAjax.success("操作成功！");
		}else{
			return ReturnDWZAjax.fail("操作异常，请检查该笔操作");
		}
	}
	
	/**
	 * 导出购买信息excle
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/buyMessage/exportXls")
	public void exportXls(HttpServletResponse response,
			HttpServletRequest request,ReqMsg_Statistics_BuyMessageListQuery req) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=buyMessage"
				+ DateUtil.formatYYYYMMDD(new Date()) +".xls");
		try {
			WritableWorkbook book = Workbook.createWorkbook(response
					.getOutputStream());
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("到期日期 ", 0);
			sheet.setColumnView(0, 18);
			sheet.setColumnView(1, 18);

			sheet.setColumnView(2, 18);
			sheet.setColumnView(3, 18);
			sheet.setColumnView(4, 18);

			sheet.setColumnView(5, 18);
			sheet.setColumnView(6, 18);
			sheet.setColumnView(7, 18);
			sheet.setColumnView(8, 18);
			sheet.setColumnView(9, 18);
			sheet.setColumnView(10, 18);
			sheet.setColumnView(11, 18);
			sheet.setColumnView(12, 18);
			sheet.setColumnView(13, 18);
			sheet.setColumnView(14, 18);
			sheet.setColumnView(15, 18);
			sheet.setColumnView(16, 18);
			
			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
					wfc);
			// 设置单元格样式
			wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为
			Label label;
			label = new Label(0, 0, "购买日期", wcfFC);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

			label = new Label(1, 0, "用户id", wcfFC);
			sheet.addCell(label);

			label = new Label(2, 0, "姓名", wcfFC);
			sheet.addCell(label);

			label = new Label(3, 0, "手机号", wcfFC);
			sheet.addCell(label);

			label = new Label(4, 0, "订单号", wcfFC);
			sheet.addCell(label);
			
			label = new Label(5, 0, "产品名称", wcfFC);
			sheet.addCell(label);

			label = new Label(6, 0, "购买金额", wcfFC);
			sheet.addCell(label);

			label = new Label(7, 0, "利率", wcfFC);
			sheet.addCell(label);
			
//			label = new Label(7, 0, "奖励金计提", wcfFC);
//			sheet.addCell(label);
//			
//			label = new Label(8, 0, "已发放奖励金", wcfFC);
//			sheet.addCell(label);

			label = new Label(8, 0, "期限", wcfFC);
			sheet.addCell(label);

			label = new Label(9, 0, "到期提现日期", wcfFC);
			sheet.addCell(label);

			label = new Label(10, 0, "到期提现金额", wcfFC);
			sheet.addCell(label);

			label = new Label(11, 0, "投资者收益", wcfFC);
			sheet.addCell(label);

			label = new Label(12, 0, "公司收益", wcfFC);
			sheet.addCell(label);

			label = new Label(13, 0, "提现银行", wcfFC);
			sheet.addCell(label);

			label = new Label(14, 0, "状态", wcfFC);
			sheet.addCell(label);
			
			label = new Label(15, 0, "渠道来源", wcfFC);
			sheet.addCell(label);
			
			label = new Label(16, 0, "资产合作方", wcfFC);
			sheet.addCell(label);
			
			wcfFC = new jxl.write.WritableCellFormat(wfc);
			wcfFC.setBackground(jxl.format.Colour.WHITE);
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			wcfFC.setFont(wfc);
			int totalPage = 2;
			req.setNumPerPage(200);//导出excel200条每页
			for(int curPage=1,i=0; curPage<totalPage ;curPage++,i++){  //不知道当前具有多少条，所以默认为1，当第一次请求回来后，再重新计算当前的总页数，进行循环的分页查询
				req.setPageNum(curPage);
				ResMsg_Statistics_BuyMessageListQuery res = (ResMsg_Statistics_BuyMessageListQuery) accountService.handleMsg(req);
				HashMap<String, String> self = reward(res.getSysConfigs(), 1);//购买人奖励金率
				HashMap<String, String> referrer = reward(res.getSysConfigs(), 2);//推荐人奖励金率
				totalPage = (int)Math.ceil(res.getTotalRows() / 200.0) + 1;
				
				List<HashMap<String, Object>> userBuyMessageList = res.getUserBuyMessageList();
				if(null != userBuyMessageList){
					for(int j=0;j<userBuyMessageList.size();j++,i++){
						label = new Label(0, i+1, DateUtil.formatYYYYMMDD((Date)userBuyMessageList.get(j).get("openTime")),wcfFC);   //购买日期
						sheet.addCell(label);

						label = new Label(1, i+1, userBuyMessageList.get(j).get("id")+"",wcfFC);
						sheet.addCell(label);//用户id

						label = new Label(2, i+1, (String)userBuyMessageList.get(j).get("userName"),wcfFC);
						sheet.addCell(label);//姓名
						
						label = new Label(3, i+1, userBuyMessageList.get(j).get("preMobile")+"****"+userBuyMessageList.get(j).get("afterMobile"),wcfFC);
						sheet.addCell(label);//手机号
						
						label = new Label(4, i+1, (String)userBuyMessageList.get(j).get("orderNo"),wcfFC);
						sheet.addCell(label);//订单号
						
						label = new Label(5, i+1, (String)userBuyMessageList.get(j).get("productName"),wcfFC);
						sheet.addCell(label);//产品名称 
						
						label = new Label(6, i+1, (Double)userBuyMessageList.get(j).get("balance") + "",wcfFC);
						sheet.addCell(label);//购买金额
						
						label = new Label(7, i+1, (Double)userBuyMessageList.get(j).get("rate") + "",wcfFC);
						sheet.addCell(label);//利率
						
						Integer agentId = userBuyMessageList.get(j).get("agentId") == null ? 0 : (Integer)userBuyMessageList.get(j).get("agentId");
						Integer recommendId = userBuyMessageList.get(j).get("recommendId") == null ? null : (Integer)userBuyMessageList.get(j).get("recommendId");
						Integer term = (Integer)userBuyMessageList.get(j).get("term");
//						String reward = outBonus((Double)userBuyMessageList.get(j).get("balance"), self.get(term+""), referrer.get(term+""), recommendId, agentId);
//						label = new Label(7, i+1, reward+"",wcfFC); 
//						sheet.addCell(label);//奖励金计提
						
//						label = new Label(8, i+1, (Double)userBuyMessageList.get(j).get("bonus") == null ? "0" : (Double)userBuyMessageList.get(j).get("bonus")+"",wcfFC);  
//						sheet.addCell(label);//已发放奖励金
						
						label = new Label(8, i+1, (Integer)userBuyMessageList.get(j).get("term")+"",wcfFC);
						sheet.addCell(label);//期限 
						
						label = new Label(9, i+1, DateUtil.formatYYYYMMDD((Date)userBuyMessageList.get(j).get("investEndTime")),wcfFC);
						sheet.addCell(label);//到期提现日期
						
						int investCount = DateUtil.getDiffeDay((Date)userBuyMessageList.get(j).get("investEndTime"), (Date)userBuyMessageList.get(j).get("openTime"));
						double balance = (Double)userBuyMessageList.get(j).get("balance");
						double rate = (Double)userBuyMessageList.get(j).get("rate");
						double userInvestAmount = balance * rate / 100 / 365 * investCount;
						double amount = balance + userInvestAmount;
						label = new Label(10, i+1, MoneyUtil.format(amount)+"",wcfFC);
						sheet.addCell(label);//到期提现金额
						
						label = new Label(11, i+1, MoneyUtil.format(userInvestAmount) + "",wcfFC);
						sheet.addCell(label);//投资者收益
						
						double companyInvestAmount = balance * 0.20 / 365 * investCount;
							label = new Label(12, i+1, MoneyUtil.format(companyInvestAmount) + "",wcfFC);
							sheet.addCell(label);//公司收益
							
							label = new Label(13, i+1, (String)userBuyMessageList.get(j).get("bindBankName"),wcfFC);
							sheet.addCell(label);//提现银行
							
							label = new Label(14, i+1, Messages.get("ACCOUNT_STATUS_"+(Integer)userBuyMessageList.get(j).get("accountStatus")),wcfFC);
							sheet.addCell(label);//状态
							
							label = new Label(15, i+1, (String)userBuyMessageList.get(j).get("agentName") == null ? "无" : (String)userBuyMessageList.get(j).get("agentName"),wcfFC);
							sheet.addCell(label);//渠道来源
							
							label = new Label(16, i+1, "7_DAI".equals((String)userBuyMessageList.get(j).get("propertySymbol"))?"七贷":"云贷",wcfFC);  
							sheet.addCell(label);//资产合作方
							
					}
				}
				i--;
			}
			book.write();
			book.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 导出购买信息excle
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping("/buyMessage/zan/exportXls")
	public void zanExportXls(HttpServletResponse response,
						  HttpServletRequest request,ReqMsg_Statistics_BuyMessageListQuery req) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=buyMessage"
				+ DateUtil.formatYYYYMMDD(new Date()) +".xls");
		try {
			WritableWorkbook book = Workbook.createWorkbook(response
					.getOutputStream());
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("到期日期 ", 0);
			sheet.setColumnView(0, 18);
			sheet.setColumnView(1, 18);

			sheet.setColumnView(2, 18);
			sheet.setColumnView(3, 18);
			sheet.setColumnView(4, 18);

			sheet.setColumnView(5, 18);
			sheet.setColumnView(6, 18);
			sheet.setColumnView(7, 18);
			sheet.setColumnView(8, 18);
			sheet.setColumnView(9, 18);
			sheet.setColumnView(10, 18);
			sheet.setColumnView(11, 18);
			sheet.setColumnView(12, 18);
			sheet.setColumnView(13, 18);
			sheet.setColumnView(14, 18);
			sheet.setColumnView(15, 18);
			sheet.setColumnView(16, 18);

			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
					wfc);
			// 设置单元格样式
			wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为
			Label label;
			label = new Label(0, 0, "购买日期", wcfFC);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

			label = new Label(1, 0, "用户id", wcfFC);
			sheet.addCell(label);

			label = new Label(2, 0, "姓名", wcfFC);
			sheet.addCell(label);

			label = new Label(3, 0, "手机号", wcfFC);
			sheet.addCell(label);

			label = new Label(4, 0, "订单号", wcfFC);
			sheet.addCell(label);

			label = new Label(5, 0, "产品名称", wcfFC);
			sheet.addCell(label);

			label = new Label(6, 0, "购买金额", wcfFC);
			sheet.addCell(label);

			label = new Label(7, 0, "利率", wcfFC);
			sheet.addCell(label);

//			label = new Label(7, 0, "奖励金计提", wcfFC);
//			sheet.addCell(label);
//
//			label = new Label(8, 0, "已发放奖励金", wcfFC);
//			sheet.addCell(label);

			label = new Label(8, 0, "期限", wcfFC);
			sheet.addCell(label);

			label = new Label(9, 0, "到期提现日期", wcfFC);
			sheet.addCell(label);

			label = new Label(10, 0, "到期提现金额", wcfFC);
			sheet.addCell(label);

			label = new Label(11, 0, "投资者收益", wcfFC);
			sheet.addCell(label);

			label = new Label(12, 0, "公司收益", wcfFC);
			sheet.addCell(label);

			label = new Label(13, 0, "提现银行", wcfFC);
			sheet.addCell(label);

			label = new Label(14, 0, "状态", wcfFC);
			sheet.addCell(label);

			label = new Label(15, 0, "渠道来源", wcfFC);
			sheet.addCell(label);

			label = new Label(16, 0, "资产合作方", wcfFC);
			sheet.addCell(label);

			wcfFC = new jxl.write.WritableCellFormat(wfc);
			wcfFC.setBackground(jxl.format.Colour.WHITE);
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			wcfFC.setFont(wfc);
			int totalPage = 2;
			req.setNumPerPage(200);//导出excel200条每页
			for(int curPage=1,i=0; curPage<totalPage ;curPage++,i++){  //不知道当前具有多少条，所以默认为1，当第一次请求回来后，再重新计算当前的总页数，进行循环的分页查询
				req.setPageNum(curPage);
				ResMsg_Statistics_BuyMessageListQuery res = new ResMsg_Statistics_BuyMessageListQuery();
				mStatisticsService.buyMessageListQuery(req, res);
				HashMap<String, String> self = reward(res.getSysConfigs(), 1);//购买人奖励金率
				HashMap<String, String> referrer = reward(res.getSysConfigs(), 2);//推荐人奖励金率
				totalPage = (int)Math.ceil(res.getTotalRows() / 200.0) + 1;

				List<HashMap<String, Object>> userBuyMessageList = res.getUserBuyMessageList();
				if(null != userBuyMessageList){
					for(int j=0;j<userBuyMessageList.size();j++,i++){
						label = new Label(0, i+1, DateUtil.formatYYYYMMDD((Date)userBuyMessageList.get(j).get("openTime")),wcfFC);   //购买日期
						sheet.addCell(label);

						label = new Label(1, i+1, userBuyMessageList.get(j).get("id")+"",wcfFC);
						sheet.addCell(label);//用户id

						label = new Label(2, i+1, (String)userBuyMessageList.get(j).get("userName"),wcfFC);
						sheet.addCell(label);//姓名

						label = new Label(3, i+1, userBuyMessageList.get(j).get("preMobile")+"****"+userBuyMessageList.get(j).get("afterMobile"),wcfFC);
						sheet.addCell(label);//手机号

						label = new Label(4, i+1, (String)userBuyMessageList.get(j).get("orderNo"),wcfFC);
						sheet.addCell(label);//订单号

						label = new Label(5, i+1, (String)userBuyMessageList.get(j).get("productName"),wcfFC);
						sheet.addCell(label);//产品名称

						label = new Label(6, i+1, (Double)userBuyMessageList.get(j).get("balance") + "",wcfFC);
						sheet.addCell(label);//购买金额

						label = new Label(7, i+1, (Double)userBuyMessageList.get(j).get("rate") + "",wcfFC);
						sheet.addCell(label);//利率

						Integer agentId = userBuyMessageList.get(j).get("agentId") == null ? 0 : (Integer)userBuyMessageList.get(j).get("agentId");
						Integer recommendId = userBuyMessageList.get(j).get("recommendId") == null ? null : (Integer)userBuyMessageList.get(j).get("recommendId");
						Integer term = (Integer)userBuyMessageList.get(j).get("term");
//						String reward = outBonus((Double)userBuyMessageList.get(j).get("balance"), self.get(term+""), referrer.get(term+""), recommendId, agentId);
//						label = new Label(7, i+1, reward+"",wcfFC);
//						sheet.addCell(label);//奖励金计提

//						label = new Label(8, i+1, (Double)userBuyMessageList.get(j).get("bonus") == null ? "0" : (Double)userBuyMessageList.get(j).get("bonus")+"",wcfFC);
//						sheet.addCell(label);//已发放奖励金

						label = new Label(8, i+1, (Integer)userBuyMessageList.get(j).get("term")+"",wcfFC);
						sheet.addCell(label);//期限

						label = new Label(9, i+1, DateUtil.formatYYYYMMDD((Date)userBuyMessageList.get(j).get("investEndTime")),wcfFC);
						sheet.addCell(label);//到期提现日期

						int investCount = DateUtil.getDiffeDay((Date)userBuyMessageList.get(j).get("investEndTime"), (Date)userBuyMessageList.get(j).get("openTime"));
						double balance = (Double)userBuyMessageList.get(j).get("balance");
						double rate = (Double)userBuyMessageList.get(j).get("rate");
						double userInvestAmount = 0d;
						if(Constants.PROPERTY_SYMBOL_ZAN.equals((String)userBuyMessageList.get(j).get("propertySymbol"))) {
							// 赞分期投资收益采用等额本息计算方式
							List<AverageCapitalPlusInterestVO> vos = algorithmService.calAverageCapitalPlusInterestPlan(balance, term, rate);
							for (AverageCapitalPlusInterestVO vo: vos) {
								userInvestAmount = MoneyUtil.add(userInvestAmount, vo.getPlanInterest()).doubleValue();
							}
						} else {
							userInvestAmount = balance * rate / 100 / 365 * investCount;
						}

						double amount = balance + userInvestAmount;
						label = new Label(10, i+1, MoneyUtil.format(amount)+"",wcfFC);
						sheet.addCell(label);//到期提现金额

						label = new Label(11, i+1, MoneyUtil.format(userInvestAmount) + "",wcfFC);
						sheet.addCell(label);//投资者收益

						double companyInvestAmount = 0d;
						if(Constants.PROPERTY_SYMBOL_ZAN.equals((String)userBuyMessageList.get(j).get("propertySymbol"))) {
							// 赞分期投资收益采用等额本息计算方式
							// 投资金额*15%*（投资月数+1）/24-投资者收益
							companyInvestAmount = balance * 0.15 * (term + 1) / 24 - userInvestAmount;
						} else {
							companyInvestAmount = balance * 0.20 / 365 * investCount;
						}


						label = new Label(12, i+1, MoneyUtil.format(companyInvestAmount) + "",wcfFC);
						sheet.addCell(label);//公司收益

						label = new Label(13, i+1, (String)userBuyMessageList.get(j).get("bindBankName"),wcfFC);
						sheet.addCell(label);//提现银行

						label = new Label(14, i+1, Messages.get("ACCOUNT_STATUS_"+(Integer)userBuyMessageList.get(j).get("accountStatus")),wcfFC);
						sheet.addCell(label);//状态

						label = new Label(15, i+1, (String)userBuyMessageList.get(j).get("agentName") == null ? "无" : (String)userBuyMessageList.get(j).get("agentName"),wcfFC);
						sheet.addCell(label);//渠道来源

						String propertySymbol = (String) userBuyMessageList.get(j).get("propertySymbol");
						if("7_DAI".equals(propertySymbol)) {
							propertySymbol = "七贷";
						}
						if("ZAN".equals(propertySymbol)) {
							propertySymbol = "赞分期";
						}
						if(StringUtil.isBlank(propertySymbol) || "YUN_DAI".equals(propertySymbol)) {
							propertySymbol = "云贷";
						}
						label = new Label(16, i+1, propertySymbol, wcfFC);
						sheet.addCell(label);//资产合作方

					}
				}
				i--;
			}
			book.write();
			book.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 导出购买信息excle 财务投资购买查询（云贷存管）
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping("/buyMessage/dep/exportXls")
	public void depExportXls(HttpServletResponse response,
							 HttpServletRequest request,ReqMsg_Statistics_BuyMessageDepListQuery req) {
		
		req.setBeginBuyAmount(StringUtil.isBlank(req.getBeginBuyAmount())? null : req.getBeginBuyAmount().trim());
		req.setEndBuyAmount(StringUtil.isBlank(req.getEndBuyAmount())? null : req.getEndBuyAmount().trim());
		
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=buyMessage"
				+ DateUtil.formatYYYYMMDD(new Date()) +".xls");
		try {
			WritableWorkbook book = Workbook.createWorkbook(response
					.getOutputStream());
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("到期日期 ", 0);
			sheet.setColumnView(0, 18);
			sheet.setColumnView(1, 18);

			sheet.setColumnView(2, 18);
			sheet.setColumnView(3, 18);
			sheet.setColumnView(4, 18);

			sheet.setColumnView(5, 18);
			sheet.setColumnView(6, 18);
			sheet.setColumnView(7, 18);
			sheet.setColumnView(8, 18);
			sheet.setColumnView(9, 18);
			sheet.setColumnView(10, 18);
			sheet.setColumnView(11, 18);
			sheet.setColumnView(12, 18);
			sheet.setColumnView(13, 18);

			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
					wfc);
			// 设置单元格样式
			wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为
			Label label;

			label = new Label(0, 0, "用户id", wcfFC);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

			label = new Label(1, 0, "手机号", wcfFC);
			sheet.addCell(label);

			label = new Label(2, 0, "姓名", wcfFC);
			sheet.addCell(label);

			label = new Label(3, 0, "站岗户", wcfFC);
			sheet.addCell(label);

			label = new Label(4, 0, "订单号", wcfFC);
			sheet.addCell(label);

			label = new Label(5, 0, "期限", wcfFC);
			sheet.addCell(label);

			label = new Label(6, 0, "利率", wcfFC);
			sheet.addCell(label);
			
			label = new Label(7, 0, "匹配金额", wcfFC);
			sheet.addCell(label);

			label = new Label(8, 0, "购买金额", wcfFC);
			sheet.addCell(label);
			
			label = new Label(9, 0, "产品状态", wcfFC);
			sheet.addCell(label);

			label = new Label(10, 0, "产品名称", wcfFC);
			sheet.addCell(label);

			label = new Label(11, 0, "购买时间", wcfFC);
			sheet.addCell(label);

			label = new Label(12, 0, "到期提现日期", wcfFC);
			sheet.addCell(label);

			label = new Label(13, 0, "渠道来源", wcfFC);
			sheet.addCell(label);

			wcfFC = new jxl.write.WritableCellFormat(wfc);
			wcfFC.setBackground(jxl.format.Colour.WHITE);
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			wcfFC.setFont(wfc);
			int totalPage = 2;
			req.setNumPerPage(200);//导出excel200条每页
			for(int curPage=1,i=0; curPage<totalPage ;curPage++,i++){  //不知道当前具有多少条，所以默认为1，当第一次请求回来后，再重新计算当前的总页数，进行循环的分页查询
				req.setPageNum(curPage);
				ResMsg_Statistics_BuyMessageDepListQuery res = new ResMsg_Statistics_BuyMessageDepListQuery();
				mStatisticsService.depBuyMessageListQuery(req, res);
				HashMap<String, String> self = reward(res.getSysConfigs(), 1);//购买人奖励金率
				HashMap<String, String> referrer = reward(res.getSysConfigs(), 2);//推荐人奖励金率
				totalPage = (int)Math.ceil(res.getTotalRows() / 200.0) + 1;

				List<HashMap<String, Object>> userBuyMessageList = res.getUserBuyMessageList();
				if(null != userBuyMessageList){
					for(int j=0;j<userBuyMessageList.size();j++,i++){

						label = new Label(0, i+1, userBuyMessageList.get(j).get("id")+"",wcfFC);
						sheet.addCell(label);//用户id

						label = new Label(1, i+1, userBuyMessageList.get(j).get("preMobile")+"****"+userBuyMessageList.get(j).get("afterMobile"),wcfFC);
						sheet.addCell(label);//手机号

						label = new Label(2, i+1, (String)userBuyMessageList.get(j).get("userName"),wcfFC);
						sheet.addCell(label);//姓名

						String propertySymbol = (String) userBuyMessageList.get(j).get("propertySymbol");
						if("7_DAI".equals(propertySymbol)) {
							propertySymbol = "七贷";
						}
						if("ZAN".equals(propertySymbol)) {
							propertySymbol = "赞分期";
						}
						if(StringUtil.isBlank(propertySymbol) || "YUN_DAI".equals(propertySymbol)) {
							propertySymbol = "云贷";
						}
						if(StringUtil.isBlank(propertySymbol) || "YUN_DAI_SELF".equals(propertySymbol)) {
							propertySymbol = "云贷存管";
						}
						if(StringUtil.isBlank(propertySymbol) || "ZSD".equals(propertySymbol)) {
							propertySymbol = "赞时贷";
						}
						if(StringUtil.isBlank(propertySymbol) || "FREE".equals(propertySymbol)) {
							propertySymbol = "自有站岗户";
						}
						label = new Label(3, i+1, propertySymbol, wcfFC);
						sheet.addCell(label);//资产合作方

						label = new Label(4, i+1, (String)userBuyMessageList.get(j).get("orderNo"),wcfFC);
						sheet.addCell(label);//订单号

						label = new Label(5, i+1, (Integer)userBuyMessageList.get(j).get("term")+"",wcfFC);
						sheet.addCell(label);//期限

						label = new Label(6, i+1, (Double)userBuyMessageList.get(j).get("rate") + "",wcfFC);
						sheet.addCell(label);//利率
						
						label = new Label(7, i+1, (Double)userBuyMessageList.get(j).get("balance") + "",wcfFC);
						sheet.addCell(label);//匹配金额

						label = new Label(8, i+1, (Double)userBuyMessageList.get(j).get("buyBalance") + "",wcfFC);
						sheet.addCell(label);//购买金额

						Integer accountStatus = (Integer) userBuyMessageList.get(j).get("accountStatus");
						String status = "";
						if(accountStatus == 2) {
							status = "投资中";
						}
						if(accountStatus == 7) {
							status = "结算中";
						}
						if(accountStatus == 5) {
							status = "已结算";
						}
						if(accountStatus == 8) {
							status = "结算失败";
						}
						label = new Label(9, i+1, status, wcfFC);
						sheet.addCell(label);//资产合作方

						label = new Label(10, i+1, (String)userBuyMessageList.get(j).get("productName"),wcfFC);
						sheet.addCell(label);//产品名称

						label = new Label(11, i+1, DateUtil.format((Date)userBuyMessageList.get(j).get("openTime")),wcfFC);
						sheet.addCell(label);//购买日期

						label = new Label(12, i+1, DateUtil.formatYYYYMMDD((Date)userBuyMessageList.get(j).get("investEndTime")),wcfFC);
						sheet.addCell(label);//到期提现日期

						label = new Label(13, i+1, (String)userBuyMessageList.get(j).get("agentName") == null ? "无" : (String)userBuyMessageList.get(j).get("agentName"),wcfFC);
						sheet.addCell(label);//渠道来源

					}
				}
				i--;
			}
			book.write();
			book.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 导出购买信息excle 投资购买查询-存管后
	 *
	 * @param response
	 * @param request
	 */
	@RequestMapping("/buyMessage/depInvestment/exportXls")
	public void depInvestmentExportXls(HttpServletResponse response,
							 HttpServletRequest request, ReqMsg_Statistics_BuyMessageDepListQuery req) {

		response.setCharacterEncoding("utf-8");  
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=investmentBuyMessage"
				+ DateUtil.formatYYYYMMDD(new Date()) +".xls");
		try {
			WritableWorkbook book = Workbook.createWorkbook(response
					.getOutputStream());
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("到期日期 ", 0);
			sheet.setColumnView(0, 18);
			sheet.setColumnView(1, 18);
			sheet.setColumnView(2, 18);
			sheet.setColumnView(3, 18);
			sheet.setColumnView(4, 18);

			sheet.setColumnView(5, 18);
			sheet.setColumnView(6, 18);
			sheet.setColumnView(7, 18);
			sheet.setColumnView(8, 18);
			sheet.setColumnView(9, 18);
			sheet.setColumnView(10, 18);
			sheet.setColumnView(11, 18);

			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
					wfc);
			// 设置单元格样式
			wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为
			Label label;

			label = new Label(0, 0, "用户id", wcfFC);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

			label = new Label(1, 0, "手机号", wcfFC);
			sheet.addCell(label);

			label = new Label(2, 0, "姓名", wcfFC);
			sheet.addCell(label);

			label = new Label(3, 0, "站岗户", wcfFC);
			sheet.addCell(label);

			label = new Label(4, 0, "订单号", wcfFC);
			sheet.addCell(label);

			label = new Label(5, 0, "期限", wcfFC);
			sheet.addCell(label);

			label = new Label(6, 0, "利率", wcfFC);
			sheet.addCell(label);

			label = new Label(7, 0, "购买金额", wcfFC);
			sheet.addCell(label);
			
			label = new Label(8, 0, "产品状态", wcfFC);
			sheet.addCell(label);

			label = new Label(9, 0, "购买时间", wcfFC);
			sheet.addCell(label);

			label = new Label(10, 0, "到期提现日", wcfFC);
			sheet.addCell(label);

			label = new Label(11, 0, "渠道来源", wcfFC);
			sheet.addCell(label);

			wcfFC = new jxl.write.WritableCellFormat(wfc);
			wcfFC.setBackground(jxl.format.Colour.WHITE);
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			wcfFC.setFont(wfc);
			int totalPage = 2;
			req.setNumPerPage(200);//导出excel200条每页
			for(int curPage=1,i=0; curPage<totalPage ;curPage++,i++){  //不知道当前具有多少条，所以默认为1，当第一次请求回来后，再重新计算当前的总页数，进行循环的分页查询
				req.setPageNum(curPage);
				ResMsg_Statistics_BuyMessageDepListQuery res = new ResMsg_Statistics_BuyMessageDepListQuery();
				mStatisticsService.depInvestmentBuyMessageListQuery(req, res);
				totalPage = (int)Math.ceil(res.getTotalRows() / 200.0) + 1;

				List<HashMap<String, Object>> userBuyMessageList = res.getUserBuyMessageList();
				if(null != userBuyMessageList){
					for(int j=0;j<userBuyMessageList.size();j++,i++){

						label = new Label(0, i+1, userBuyMessageList.get(j).get("id")+"",wcfFC);
						sheet.addCell(label);//用户id

						label = new Label(1, i+1, userBuyMessageList.get(j).get("preMobile")+"****"+userBuyMessageList.get(j).get("afterMobile"),wcfFC);
						sheet.addCell(label);//手机号

						label = new Label(2, i+1, (String)userBuyMessageList.get(j).get("userName"),wcfFC);
						sheet.addCell(label);//姓名

						String propertySymbol = (String) userBuyMessageList.get(j).get("propertySymbol");
						if("7_DAI_SELF".equals(propertySymbol)) {
							propertySymbol = "七贷存管";
						} else if("YUN_DAI_SELF".equals(propertySymbol)) {
							propertySymbol = "云贷存管";
						} else if("ZSD".equals(propertySymbol)) {
							propertySymbol = "赞时贷";
						}
						label = new Label(3, i+1, propertySymbol, wcfFC);
						sheet.addCell(label);//资产合作方

						label = new Label(4, i+1, (String)userBuyMessageList.get(j).get("orderNo"),wcfFC);
						sheet.addCell(label);//订单号

						label = new Label(5, i+1, (Integer)userBuyMessageList.get(j).get("term")+"个月",wcfFC);
						sheet.addCell(label);//期限

						label = new Label(6, i+1, (Double)userBuyMessageList.get(j).get("rate") + "",wcfFC);
						sheet.addCell(label);//利率

						label = new Label(7, i+1, (Double)userBuyMessageList.get(j).get("buyBalance") + "",wcfFC);
						sheet.addCell(label);//购买金额

						Integer accountStatus = (Integer) userBuyMessageList.get(j).get("accountStatus");
						String status = "";
						if(accountStatus == 2) {
							status = "投资中";
						}
						if(accountStatus == 7) {
							status = "结算中";
						}
						if(accountStatus == 5) {
							status = "已结算";
						}
						if(accountStatus == 8) {
							status = "结算失败";
						}
						label = new Label(8, i+1, status, wcfFC);
						sheet.addCell(label);//资产合作方

						label = new Label(9, i+1, DateUtil.format((Date)userBuyMessageList.get(j).get("openTime")),wcfFC);
						sheet.addCell(label);//购买日期

						label = new Label(10, i+1, DateUtil.formatYYYYMMDD((Date)userBuyMessageList.get(j).get("investEndTime")),wcfFC);
						sheet.addCell(label);//到期提现日期

						label = new Label(11, i+1, (String)userBuyMessageList.get(j).get("agentName") == null ? "无" : (String)userBuyMessageList.get(j).get("agentName"),wcfFC);
						sheet.addCell(label);//渠道来源

					}
				}
				i--;
			}
			book.write();
			book.close();
		} catch (FileNotFoundException e) {
			log.error("depInvestmentExportXls FileNotFoundException:{}", e);
		} catch (WriteException e) {
			log.error("depInvestmentExportXls WriteException:{}", e);
		} catch (IOException e) {
			log.error("depInvestmentExportXls IOException:{}", e);
		}
	}
	
	/**
	 * 计算奖励金支出
	 * @param balance
	 * @param selfRate
	 * @param referrerRate
	 * @param recommendId 推荐人编号
	 * @param agentId 渠道编号
     * @return
     */
	private String outBonus(Double balance,String selfRate,String referrerRate,Integer recommendId,Integer agentId){
		double result = 0.0;
		double self = 0.0;
		double referrer = 0.0;
		if(null != recommendId && 0 != recommendId){//存在邀请码
			self = balance * Double.parseDouble(selfRate)/100;//购买人奖励金
			referrer = balance * Double.parseDouble(referrerRate)/100;//推荐人奖励金
			result = self+referrer;
		}else{//不存在邀请码存在可能：1.无邀请码 2.是销售人员邀请
			if(agentId == 31){//是销售人员推荐
				result = balance * Double.parseDouble(selfRate)/100;//购买人奖励金
			}else{//不存在邀请码
				result = 0;
			}
		}
		return MoneyUtil.format(String.valueOf(result));
	}

	/**
	 * 奖励金提成率
	 * @param sysConfigList 利率配置信息
	 * @param type 1.购买人奖励金提成率 2.推荐人奖励金提成率
	 * @return
	 */
	private HashMap<String, String> reward(List<HashMap<String, Object>> sysConfigList,int type){
		HashMap<String, String> map = new HashMap<String,String>();
		String result = null;
		if(type == 1){//购买人奖励金提成率
				result = bonusMonth("BONUS_RATE_4_SELF_1MONTH",sysConfigList);
				map.put("1", result);
				result = bonusMonth("BONUS_RATE_4_SELF_3MONTH",sysConfigList);
				map.put("3", result);
				result = bonusMonth("BONUS_RATE_4_SELF_6MONTH",sysConfigList);
				map.put("6", result);
				result = bonusMonth("BONUS_RATE_4_SELF_1YEAR",sysConfigList);
				map.put("12", result);
			}else{//推荐人奖励金提成率
				result = bonusMonth("BONUS_RATE_4_REFERRER_1MONTH",sysConfigList);
				map.put("1", result);
				result = bonusMonth("BONUS_RATE_4_REFERRER_3MONTH",sysConfigList);
				map.put("3", result);
				result = bonusMonth("BONUS_RATE_4_REFERRER_6MONTH",sysConfigList);
				map.put("6", result);
				result = bonusMonth("BONUS_RATE_4_REFERRER_1YEAR",sysConfigList);
				map.put("12", result);
			}
		return map;
	}
	
	/**
	 * 查询利率
	 * @param key
	 * @param sysConfigList
	 * @return
	 */
	private String bonusMonth(String key,List<HashMap<String, Object>> sysConfigList){
		String result = null;
		if (sysConfigList != null && sysConfigList.size() != 0) {
			for (HashMap<String, Object> hashMap : sysConfigList) {
				String confKey = (String) hashMap.get("confKey");
				if(confKey.equals(key)){
					result  = (String) hashMap.get("confValue");
				}
			}
		}
		return result;
	}
	
	
	@RequestMapping("/investmentBackSection/exportXls")
	public void exportXls(HttpServletResponse response,
			HttpServletRequest request,ReqMsg_Statistics_SelectUserInvestmentPaybackList req) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;"
				+ DateUtil.formatYYYYMMDD(new Date()) +".xls");
		
		//输出流定义
        try {
            String excel_name = "投资回款记录" + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".xls";
            String enableFileName = "";
            String agent = (String)request.getHeader("USER-" + "AGENT");
            if(agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("rv:11.0) like Gecko") == -1 && agent.indexOf("Edge") == -1) {// FF      
                enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(excel_name.getBytes("UTF-8")))) + "?=";    
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);    
            } else { // IE      
                enableFileName = new String(excel_name.getBytes("GBK"), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);    
            }
        } catch (IOException e1) {
            e1.printStackTrace();
        }
		
		try {
			WritableWorkbook book = Workbook.createWorkbook(response
					.getOutputStream());
			// 生成名为“第一页”的工作表，参数0表示这是第一页
			WritableSheet sheet = book.createSheet("到期日期 ", 0);
			sheet.setColumnView(0, 8);
			sheet.setColumnView(1, 18);
			sheet.setColumnView(2, 18);
			sheet.setColumnView(3, 18);
			sheet.setColumnView(4, 18);
			sheet.setColumnView(5, 18);
			sheet.setColumnView(6, 18);
			sheet.setColumnView(7, 18);
			sheet.setColumnView(8, 18);
			sheet.setColumnView(9, 18);
			sheet.setColumnView(10, 18);
			sheet.setColumnView(11, 18);
			sheet.setColumnView(12, 18);
			sheet.setColumnView(13, 18);

			jxl.write.WritableFont wfc = new jxl.write.WritableFont(
					WritableFont.ARIAL, 10, WritableFont.NO_BOLD, false,
					UnderlineStyle.NO_UNDERLINE, jxl.format.Colour.BLUE);
			jxl.write.WritableCellFormat wcfFC = new jxl.write.WritableCellFormat(
					wfc);
			// 设置单元格样式
			wcfFC.setBackground(jxl.format.Colour.GRAY_25);// 单元格颜色
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			// 在Label对象的构造子中指名单元格位置是第一列第一行(0,0)
			// 以及单元格内容为
			Label label;
			label = new Label(0, 0, "姓名", wcfFC);
			// 将定义好的单元格添加到工作表中
			sheet.addCell(label);

			label = new Label(1, 0, "手机号", wcfFC);
			sheet.addCell(label);

			label = new Label(2, 0, "身份证", wcfFC);
			sheet.addCell(label);

			label = new Label(3, 0, "订单号", wcfFC);
			sheet.addCell(label);

			label = new Label(4, 0, "产品购买类别", wcfFC);
			sheet.addCell(label);

			label = new Label(5, 0, "结算银行卡类别", wcfFC);
			sheet.addCell(label);

			label = new Label(6, 0, "结算银行卡号", wcfFC);
			sheet.addCell(label);

			label = new Label(7, 0, "购买金额", wcfFC);
			sheet.addCell(label);

			label = new Label(8, 0, "结算金额", wcfFC);
			sheet.addCell(label);

			label = new Label(9, 0, "产品状态", wcfFC);
			sheet.addCell(label);

			label = new Label(10, 0, "结算返回原因", wcfFC);
			sheet.addCell(label);

			label = new Label(11, 0, "购买日期", wcfFC);
			sheet.addCell(label);

			label = new Label(12, 0, "结算日期", wcfFC);
			sheet.addCell(label);
			
			label = new Label(13, 0, "渠道来源", wcfFC);
			sheet.addCell(label);

			wcfFC = new jxl.write.WritableCellFormat(wfc);
			wcfFC.setBackground(jxl.format.Colour.WHITE);
			wcfFC.setAlignment(jxl.format.Alignment.CENTRE);// 单元格居中
			wcfFC.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条
			wfc = new jxl.write.WritableFont(WritableFont.ARIAL, 10,
					WritableFont.NO_BOLD, false, UnderlineStyle.NO_UNDERLINE,
					jxl.format.Colour.BLACK);
			wcfFC.setFont(wfc);
			int totalPage = 2;
			req.setNumPerPage(200);//导出excel200条每页
			for(int curPage=1,i=0; curPage<totalPage ;curPage++,i++){  //不知道当前具有多少条，所以默认为1，当第一次请求回来后，再重新计算当前的总页数，进行循环的分页查询
				req.setPageNum(curPage);
				ResMsg_Statistics_SelectUserInvestmentPaybackList res = (ResMsg_Statistics_SelectUserInvestmentPaybackList) accountService.handleMsg(req);
				totalPage = (int)Math.ceil(res.getTotalRows() / 200.0) + 1;
				
				List<HashMap<String, Object>> userBackSectionList = res.getUserBackSectionList();
				for(int j=0;j<userBackSectionList.size();j++,i++){
					
					label = new Label(0, i+1, (String)userBackSectionList.get(j).get("userName"),wcfFC);   
					sheet.addCell(label);//姓名
					
					label = new Label(1, i+1, (String)userBackSectionList.get(j).get("mobile"),wcfFC);   
					sheet.addCell(label);//手机号
					
					label = new Label(2, i+1, (String)userBackSectionList.get(j).get("idCard"),wcfFC);   
					sheet.addCell(label);//身份证
					
					label = new Label(3, i+1, (String)userBackSectionList.get(j).get("orderNo"),wcfFC);  
					sheet.addCell(label);//订单号
					
					label = new Label(4, i+1, (String)userBackSectionList.get(j).get("productName") + "",wcfFC);  
					sheet.addCell(label);//产品购买类别
					
					label = new Label(5, i+1, (String)userBackSectionList.get(j).get("bindBankName") == null ? "" : (String)userBackSectionList.get(j).get("bindBankName"),wcfFC);  
					sheet.addCell(label);//结算银行卡类别
					
					label = new Label(6, i+1, (String)userBackSectionList.get(j).get("bindBankCard") == null ? "" : (String)userBackSectionList.get(j).get("bindBankCard"),wcfFC); 
					sheet.addCell(label);//结算银行卡号 
					
					label = new Label(7, i+1, (Double)userBackSectionList.get(j).get("balance")+ "",wcfFC);  
					sheet.addCell(label);//购买金额
					
					label = new Label(8, i+1, (Double)userBackSectionList.get(j).get("settlementAmount")+ "",wcfFC);
					sheet.addCell(label);//结算金额  
					
					label = new Label(9, i+1, Messages.get("ACCOUNT_STATUS_"+(Integer)userBackSectionList.get(j).get("accountStatus")),wcfFC);  
					sheet.addCell(label);//产品状态
					
					//label = new Label(10, i+1, (String)userBackSectionList.get(j).get("结算返回原因")+"",wcfFC); 
					label = new Label(10, i+1, "结算返回原因"+"",wcfFC);  
					sheet.addCell(label);//结算返回原因
					
					label = new Label(11, i+1, DateUtil.formatYYYYMMDD((Date)userBackSectionList.get(j).get("openTime")),wcfFC);  
					sheet.addCell(label);//购买日期
					
					label = new Label(12, i+1, DateUtil.formatYYYYMMDD((Date)userBackSectionList.get(j).get("investEndTime")),wcfFC);  
					sheet.addCell(label);//结算日期
					
					label = new Label(13, i+1, (String)userBackSectionList.get(j).get("agentName") == null ? "空" : (String)userBackSectionList.get(j).get("agentName"),wcfFC);  
					sheet.addCell(label);//渠道来源
					
				}
				i--;
			}
			book.write();
			book.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (WriteException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	
	
	/**
	 * 分期产品
	 * 订单查询
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/order/stageOrderIndex")
	public String stageOrderIndex(LnPayOrdersVO req, HttpServletRequest request, HashMap<String,Object> model){
		req.setUserMobile(StringUtil.trim(req.getUserMobile()));
		req.setMobile(StringUtil.trim(req.getMobile()));
		req.setUserName(StringUtil.trim(req.getUserName()));
		req.setIdCard(StringUtil.trim(req.getIdCard()));
		req.setOrderNo(StringUtil.trim(req.getOrderNo()));
		req.setBankCardNo(StringUtil.trim(req.getBankCardNo()));
		req.setReturnMsg(StringUtil.trim(req.getReturnMsg()));
		
		log.error("request time" + new Date().getTime());
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			req.setPageNum(pageNum);
		}
		if (request.getParameter("orderDirection") != null
				&& request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("create_time");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		
		List<BsPayOrders> buyBankTypeList = payOrdersService.findBuyBankTypeList();
		
		List<LnPayOrdersVO> res = new ArrayList<LnPayOrdersVO>();
		Integer totalRows =  lnPayOrdersMapper.selectStagePayOrdersListCount(req);
		if (totalRows > 0) {
			 res = lnPayOrdersMapper.selectStagePayOrdersListPageInfo(req);
		}
		
		
		
		model.put("req", req);
		model.put("accountList", res);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", req.getNumPerPage());
		model.put("totalRows", totalRows);
		model.put("buyBankTypeLists", buyBankTypeList);
		
		if(req.getOrderField()!=null){
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection",req.getOrderDirection());
		}
		log.error("response time" + new Date().getTime());
        return "/account/order/stage_order_index";
	}
	
	
	
	/**
	 * 导出Excel
	 * @return
	 */
	@RequestMapping("/account/order/stageOrderExportExcel")
	public void stageOrderExportExcel(LnPayOrdersVO req, HttpServletRequest request, HttpServletResponse response) {
	    req.setNumPerPage(0);
	    //ResMsg_MAccount_OrderListQuery res = (ResMsg_MAccount_OrderListQuery) accountService.handleMsg(req);
			req.setUserMobile(StringUtil.trim(req.getUserMobile()));
			req.setMobile(StringUtil.trim(req.getMobile()));
			req.setUserName(StringUtil.trim(req.getUserName()));
			req.setIdCard(StringUtil.trim(req.getIdCard()));
			req.setOrderNo(StringUtil.trim(req.getOrderNo()));
			req.setBankCardNo(StringUtil.trim(req.getBankCardNo()));
			req.setReturnMsg(StringUtil.trim(req.getReturnMsg()));
	    
	    	List<LnPayOrdersVO> res = new ArrayList<LnPayOrdersVO>();
			res = lnPayOrdersMapper.selectStagePayOrdersListPageInfo(req);

	        ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(res);
	        List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
	        Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
	        List<Object> titles = new ArrayList<Object>();
	        titles.add("注册手机号");
	        titles.add("预留手机号");
	        titles.add("姓名");
	        titles.add("身份证");
	        titles.add("订单号");
	        titles.add("银行卡");
	        titles.add("金额");
	        titles.add("状态");
	        titles.add("返回信息");
	        titles.add("银行类型");
	        titles.add("支付渠道");
	        titles.add("交易时间");
	        titles.add("交易类型");
	        titles.add("返回码");
	        titles.add("更新时间");
	        titleMap.put("title", titles);
	        excelList.add(titleMap);
	        
            int i = 0;
            if (!CollectionUtils.isEmpty(dataGrid)) {
                for (HashMap<String, Object> data : dataGrid) {
                    Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
                    List<Object> objs = new ArrayList<Object>();
                    objs.add(data.get("userMobile") == null ? "" : data.get("userMobile"));
                    objs.add(data.get("mobile") == null ? "" : data.get("mobile"));
                    objs.add(data.get("userName") == null ? "" : data.get("userName"));
                    objs.add(data.get("idCard") == null ? "" : data.get("idCard"));
                    objs.add(data.get("orderNo") == null ? "" : data.get("orderNo"));
                    objs.add(data.get("bankCardNo") == null ? "" : data.get("bankCardNo"));
                    objs.add(data.get("amount") == null ? "" : MoneyUtil.format((Double)data.get("amount")));
                    switch ((Integer)data.get("status")) {
                        case 1:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_1);
                            break;
                        case 2:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_2);
                            break;
                        case 3:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_3);
                            break;
                        case 4:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_4);
                            break;
                        case 5:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_5);
                            break;
                        case 6:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_6);
                            break;
                        case 7:
                            objs.add(data.get("status") == null ? "" : Messages.ACCOUNT_ORDER_STATUS_7);
                            break;
                        default:
                            break;
                    }
                    objs.add(data.get("returnMsg") == null ? "" : data.get("returnMsg"));
                    objs.add(data.get("bankName") == null ? "" : data.get("bankName"));
                    
                    if("REAPAL".equals(data.get("channel"))) {
                        objs.add(data.get("channel") == null ? "" : "融宝");
                    } else if("PAY19".equals(data.get("channel"))) {
                        objs.add(data.get("channel") == null ? "" : "19付");
                    } else if("DAFY".equals(data.get("channel"))) {
                        objs.add(data.get("channel") == null ? "" : "达飞");
                    } else if("BAOFOO".equals(data.get("channel"))) {
                        objs.add(data.get("channel") == null ? "" : "宝付");
                    } else {
                        objs.add("");
                    }
                    
                    objs.add(data.get("createTime") == null ? "" : DateUtil.format((Date)data.get("createTime")));
                    
                   
                    if("CARD_BUY_PRODUCT".equals(data.get("transType"))){
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_CARD_BUY_PRODUCT);
                    } else if("BALANCE_BUY_PRODUCT".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BALANCE_BUY_PRODUCT);
                    } else if("TOP_UP".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_TOP_UP);
                    } else if("FREEZE".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_FREEZE);
                    } else if("RETURN_2_BALANCE".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_RETURN_2_BALANCE);
                    } else if("RETURN_2_USER_BANK_CARD".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_RETURN_2_USER_BANK_CARD);
                    } else if("BONUS_2_BALANCE".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BONUS_2_BALANCE);
                    } else if("RECOMMEND_BONUS".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_RECOMMEND_BONUS);
                    } else if("BONUS_WITHDRAW".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BONUS_WITHDRAW);
                    } else if("BALANCE_WITHDRAW".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BALANCE_WITHDRAW);
                    } else if("CHANNEL_WITHDRAW".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_CHANNEL_WITHDRAW);
                    }else if("BIND_CARD".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_BIND_CARD);
                    }else if("LOAN".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_LOAN);
                    }else if("REPAY".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_REPAY);
                    }else if("MARKET_PAY".equals(data.get("transType"))) {
                        objs.add(data.get("transType") == null ? "" : Messages.ACCOUNT_JNL_TRANS_CODE_MARKET_PAY);
                    } else {
                        objs.add("");
                    }
                    
                    objs.add(data.get("returnCode") == null ? "" : data.get("returnCode"));
                    objs.add(data.get("updateTime") == null ? "" : DateUtil.format((Date)data.get("updateTime")));
                    datasMap.put("order"+i, objs);
                    i++;
                    excelList.add(datasMap);
                }
            }
            try {
                ExportUtil.exportExcel(response, request, "系统订单数据", excelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
	    
	}
	
	
	
	/**
	 * 订单处理中转成功或失败（业务）
	 * @param req
	 * @param model
	 * @return
	 */
	@RequestMapping("/account/availableClaim")
	public String availableClaim(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String productType = request.getParameter("productType")==null ? Constants.PROPERTY_SYMBOL_ZAN :
			request.getParameter("productType");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		List<AvailableClaimVO> list = new ArrayList<AvailableClaimVO>();
		if (Constants.PROPERTY_SYMBOL_ZAN.equals(productType)) {
			list = accService.availableClaim();
		} else if(Constants.PROPERTY_SYMBOL_ZSD.equals(productType)) {
			list = accService.zsdAvailableClaim();
		}
		model.put("productType", productType);
		model.put("dataGrid", list);
		model.put("totalRows", 4);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		return "/account/availableClaim/index";
	}

	@RequestMapping("/account/sysReturnMoneyNotice/index")
	public String sysReturnMoneyNoticeIndex(HashMap<String,Object> model){

		return "/account/checkError/sysReturnMoneyNoticeIndex";
	}

	@RequestMapping("/account/sysReturnMoneyNoticeConfirm")
	public String sysReturnMoneyNoticeConfirm(String sendBatchId, HashMap<String,Object> model){
		try {
			SysReturnMoneyNoticeDo returnPlan = mSysReturnMoneyService.generateSysReturnMoneyPlan(sendBatchId);
			model.put("plan", returnPlan);
		}catch (Exception e){
			e.printStackTrace();
			String message = StringUtil.isBlank(e.getMessage())?"校验失败！":e.getMessage();
			if(e instanceof PTMessageException){
				message = ((PTMessageException) e).getErrMessage();
			}
			model.put("errMsg", message);
		}
		return "/account/checkError/sysReturnMoneyNoticeConfirm";

	}

	@RequestMapping("/account/sysReturnMoneyNotice")
	public @ResponseBody Map<Object,Object> sysReturnMoneyNotice(String sendBatchId, HashMap<String,Object> model){
		try {
			mSysReturnMoneyService.sysReturnMoneySucc(sendBatchId);
			return ReturnDWZAjax.success("提交成功！");
		}catch (Exception e){
			e.printStackTrace();
			String message = StringUtil.isBlank(e.getMessage())?"提交失败！":e.getMessage();
			if(e instanceof PTMessageException){
				message = ((PTMessageException) e).getErrMessage();
			}

			return ReturnDWZAjax.fail(message);
		}
	}

	@Autowired
	private MSysReturnMoneyService mSysReturnMoneyService;
	

}
