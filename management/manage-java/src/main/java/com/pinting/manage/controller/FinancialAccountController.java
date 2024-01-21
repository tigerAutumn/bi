package com.pinting.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.*;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.MoneyUtil;

import net.sf.json.JSONObject;

import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.velocity.tools.generic.NumberTool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.accounting.finance.service.DepUserBalanceWithdrawService;
import com.pinting.business.accounting.finance.service.DepUserTopUpService;
import com.pinting.business.dao.BsAuthMapper;
import com.pinting.business.dao.BsPaymentChannelMapper;
import com.pinting.business.dao.BsSmsRecordJnlMapper;
import com.pinting.business.dao.BsSysBalanceDailyFileMapper;
import com.pinting.business.dao.LnBindCardMapper;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_AccountDataList;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery1;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery2;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_ListQuery3;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleAgentData;
import com.pinting.business.hessian.manage.message.ReqMsg_FinancialAccount_SaleReceivable;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryAdvanceTransferOrder;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryHFBankSysAccountTransfer;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryHFTopUpOrder;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryHFWithdrawOrder;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryHsDepServiceFeeList;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryLoanList;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryPtDepServiceFeeList;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryQbDepServiceFeeList;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryRebateOrder;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QuerySysBalanceDailyList;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryYunHeadFeeTransferList;
import com.pinting.business.hessian.manage.message.ReqMsg_MAccount_QueryZanCompensateList;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_AccountDataList;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery1;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery2;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_ListQuery3;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleAgentData;
import com.pinting.business.hessian.manage.message.ResMsg_FinancialAccount_SaleReceivable;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryAdvanceTransferOrder;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryHFBankSysAccountTransfer;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryHFTopUpOrder;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryHFWithdrawOrder;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryHsDepServiceFeeList;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryLoanList;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryPtDepServiceFeeList;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryQbDepServiceFeeList;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryRebateOrder;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QuerySysBalanceDailyList;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryYunHeadFeeTransferList;
import com.pinting.business.hessian.manage.message.ResMsg_MAccount_QueryZanCompensateList;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ReqMsg_UserBalance_Withdraw;
import com.pinting.business.hessian.site.message.ResMsg_RegularBuy_Order;
import com.pinting.business.hessian.site.message.ResMsg_UserBalance_Withdraw;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.model.vo.BsHfBankSysAccountTransferVo;
import com.pinting.business.model.vo.BsHfBankSysLoanerWithdrawVo;
import com.pinting.business.model.vo.BsHfBankSysTopUpVo;
import com.pinting.business.model.vo.BsHfBankSysWithdrawVo;
import com.pinting.business.model.vo.DepBalanceFinanceStatisticsVO;
import com.pinting.business.model.vo.FinanceWithdrawRecordVO;
import com.pinting.business.model.vo.PreTopUpResVO;
import com.pinting.business.model.vo.SysNotReceivableVO;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.service.manage.BsHfBankService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.FinancialAccountService;
import com.pinting.business.service.manage.MFinanceStatisticsService;
import com.pinting.business.service.manage.MSysTopUp2BaoFooService;
import com.pinting.business.service.manage.MUserService;
import com.pinting.business.service.manage.MarginSysSubAccountService;
import com.pinting.business.service.manage.SignRepeatSendService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Util;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooCutpayment_Cutpayment;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_BalanceInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_FundDataCheck;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_GetFundList;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_QueryAccountInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_QueryAccountLeftAmountInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_QueryOrder;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_QueryProductBalance;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_BalanceInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_FundDataCheck;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_GetFundList;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_QueryAccountInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_QueryAccountLeftAmountInfo;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_QueryOrder;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_QueryProductBalance;
import com.pinting.gateway.hessian.message.hfbank.model.GetFundListInfoData;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;

@Controller
public class FinancialAccountController {

    @Autowired
    @Qualifier("dispatchService")
    private HessianService hessianService;
    @Autowired
    private FinancialAccountService financialAccountService;
    @Autowired
    private MUserService mUserService;
    @Autowired
    private MSysTopUp2BaoFooService mSysTopUp2BaoFooService;
	@Autowired
	private MarginSysSubAccountService marginSysSubAccountService;
	@Autowired
	private BsHfBankService bsHfBankService;
	@Autowired
	private BsBankCardService bsBankCardService;
	@Autowired
	private DepUserTopUpService depUserTopUpService;
	@Autowired
	private DepUserBalanceWithdrawService depUserBalanceWithdrawService;
	@Autowired
	private SMSService smsService;
	@Autowired
	private UserService userService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private HfbankTransportService hfbankTransportService;
	@Autowired
	private BaoFooTransportService baoFooTransportService;
	@Autowired
	private BsAuthMapper bsAuthMapper;
	@Autowired
	private SignRepeatSendService signRepeatSendService;
	@Autowired
	private LoanUserService loanUserService;
	@Autowired
	private LnBindCardMapper lnBindCardMapper;
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private BsSysSubAccountService bsSysSubAccountService;
	@Autowired
	private BsSmsRecordJnlMapper bsSmsRecordJnlMapper;
	@Autowired
	private MAccountService mAccountService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private MFinanceStatisticsService financeService;
	@Autowired
	private BsSysBalanceDailyFileMapper bsSysBalanceDailyFileMapepr;
	@Autowired
	private BsPaymentChannelMapper bsPaymentChannelMapper;

	private Logger log = LoggerFactory.getLogger(FinancialAccountController.class);

	// 宝付充值账号bindId
	private String baofooBindId = "20170724103813304107263";

    @RequestMapping("/financialAccount/index")
    public String financialAccountIndex(ReqMsg_FinancialAccount_ListQuery2 req,  String timeYear,String timeMonth,String timeDay,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                              Map<String, Object> model) {

        if ("0".equals(timeYear)) {
        	timeYear = null;
		}
        if ("0".equals(timeMonth)) {
        	timeMonth = null;
		}
        if ("0".equals(timeDay)) {
        	timeDay = null;
		}
		if (("".equals(timeYear)|| timeYear==null ) && ("".equals(timeMonth)|| timeMonth==null ) && ("".equals(timeDay)|| timeDay==null )) {
        	req.setTime(null);
		}else if (!"".equals(timeYear)&& timeYear!=null  && ("".equals(timeMonth)|| timeMonth==null ) && ("".equals(timeDay)|| timeDay==null )) {
			req.setTime(timeYear);
			req.setType("year");
		}else if (!"".equals(timeYear)&& timeYear!=null  && !"".equals(timeMonth)&& timeMonth!=null && ("".equals(timeDay)|| timeDay==null )) {
			req.setTime(timeYear+"-"+timeMonth);
			req.setType("month");
		}else if (!"".equals(timeYear)&& timeYear!=null  && !"".equals(timeMonth)&& timeMonth!=null && !"".equals(timeDay)&& timeDay!=null ) {
			req.setTime(timeYear+"-"+timeMonth+"-"+timeDay);
			req.setType("day");
		}else {
			return null;
		}

    	if (req.getTime() == null || "".equals(req.getTime())) {
            Date date = DateUtil.addDays(new Date(), -1);
            req.setTime(DateUtil.formatDateTime(date, "yyyy-MM-dd"));
			timeYear = DateUtil.formatDateTime(date, "yyyy");
			timeMonth = DateUtil.formatDateTime(date, "MM");
			timeDay = DateUtil.formatDateTime(date, "dd");
            req.setType("day");
        }
		model.put("timeYear", timeYear);
		model.put("timeMonth", timeMonth);
		model.put("timeDay", timeDay);
        model.put("req", req);
        ResMsg_FinancialAccount_ListQuery2 res = (ResMsg_FinancialAccount_ListQuery2) hessianService.handleMsg(req);
        model.put("totalInvestAmount", res.getTotalInvestAmount());
        model.put("totalReturnInterestAmount", res.getTotalReturnInterestAmount());
        model.put("dataGrid", res.getFinancialAccountList());
        model.put("count", res.getCount());
        return "financial/index";
    }


	/**
	 * 财务对账数据--财务结算1.0（12月1日前）
	 * @return
	 */
	@RequestMapping("/financialAccount/index_account1")
	public String indexAccount1(ReqMsg_FinancialAccount_ListQuery1 req,String timeYear,String timeMonth,HttpServletRequest request,HttpServletResponse response,Map<String, Object> model){
        if ("0".equals(timeYear)) {
        	timeYear = null;
		}
        if ("0".equals(timeMonth)) {
        	timeMonth = null;
		}
		if (("".equals(timeYear)|| timeYear==null ) && ("".equals(timeMonth)|| timeMonth==null )) {
        	req.setTime(null);
		}else if (!"".equals(timeYear)&& timeYear!=null  && !"".equals(timeMonth)&& timeMonth!=null ) {
			req.setTime(timeYear+"-"+timeMonth);
		}else {
			return null;
		}
		if (req.getTime() == null || "".equals(req.getTime())) {
			Date date = new Date();
			req.setTime(DateUtil.formatDateTime(date, "yyyy-MM"));
			timeYear = DateUtil.formatDateTime(date, "yyyy");
			timeMonth = DateUtil.formatDateTime(date, "MM");
		}
		model.put("req", req);
		model.put("timeYear", timeYear);
		model.put("timeMonth", timeMonth);
        ResMsg_FinancialAccount_ListQuery1 res = (ResMsg_FinancialAccount_ListQuery1) hessianService.handleMsg(req);
        model.put("dataGrid", res.getFinancialAccountList());
        model.put("count", res.getCount());
        model.put("sumTotalInvestAmount", res.getSumTotalInvestAmount());
        return "financial/index_account1";
	}


	@RequestMapping("/financialAccount/index_account3")
    public String indexAccount3(ReqMsg_FinancialAccount_ListQuery3 req, String timeYear,String timeMonth,String timeDay,
                                              HttpServletRequest request,
                                              HttpServletResponse response,
                                              Map<String, Object> model) {
        if ("0".equals(timeYear)) {
        	timeYear = null;
		}
        if ("0".equals(timeMonth)) {
        	timeMonth = null;
		}
        if ("0".equals(timeDay)) {
        	timeDay = null;
		}
		if (("".equals(timeYear)|| timeYear==null ) && ("".equals(timeMonth)|| timeMonth==null ) && ("".equals(timeDay)|| timeDay==null )) {
        	req.setTime(null);
		}else if (!"".equals(timeYear)&& timeYear!=null  && !"".equals(timeMonth)&& timeMonth!=null && ("".equals(timeDay)|| timeDay==null )) {
			req.setTime(timeYear+"-"+timeMonth);
			req.setType("month");
		}else if (!"".equals(timeYear)&& timeYear!=null  && !"".equals(timeMonth)&& timeMonth!=null && !"".equals(timeDay)&& timeDay!=null ) {
			req.setTime(timeYear+"-"+timeMonth+"-"+timeDay);
			req.setType("day");
		}else {
			return null;
		}

		if (req.getTime() == null || "".equals(req.getTime())) {
			Date date = DateUtil.addDays(new Date(), -1);
			req.setTime(DateUtil.formatDateTime(date, "yyyy-MM-dd"));
			timeYear = DateUtil.formatDateTime(date, "yyyy");
			timeMonth = DateUtil.formatDateTime(date, "MM");
			timeDay = DateUtil.formatDateTime(date, "dd");
			req.setType("day");
		}
		model.put("timeYear", timeYear);
		model.put("timeMonth", timeMonth);
		model.put("timeDay", timeDay);
        model.put("req", req);
        ResMsg_FinancialAccount_ListQuery3 res = (ResMsg_FinancialAccount_ListQuery3) hessianService.handleMsg(req);
        model.put("totalInvestAmount", res.getTotalInvestAmount());
        model.put("totalReturnInterestAmount", res.getTotalReturnInterestAmount());
        model.put("dataGrid", res.getFinancialAccountList());
        model.put("count", res.getCount());
        return "financial/index_account3";
    }

	/**
	 * 销售应收查询
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financialAccount/saleReceivable")
	public String saleReceivable(ReqMsg_FinancialAccount_SaleReceivable req,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Map<String, Object> model) {
	    if ((req.getStartTime() == null || "".equals(req.getStartTime())) && (req.getEndTime() == null || "".equals(req.getEndTime())) ) {
            Date date = DateUtil.addDays(new Date(), -1);
            req.setTime(DateUtil.formatDateTime(date, "yyyy-MM-dd"));
            req.setStartTime(req.getTime());
            req.setEndTime(req.getTime());
        }
        model.put("req", req);
        ResMsg_FinancialAccount_SaleReceivable res = (ResMsg_FinancialAccount_SaleReceivable) hessianService.handleMsg(req);
        model.put("dataGrid", res.getSaleReceivableList());
        model.put("count", res.getCount());
	    return "financial/sale_receivable";
	}

	/**
	 * 销售渠道结算查询
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financialAccount/saleAgentData")
    public String saleAgentData(ReqMsg_FinancialAccount_SaleAgentData req,
                                 HttpServletRequest request,
                                 HttpServletResponse response,
                                 Map<String, Object> model) {
	    model.put("req", req);
	    ResMsg_FinancialAccount_SaleAgentData res = (ResMsg_FinancialAccount_SaleAgentData) hessianService.handleMsg(req);
	    model.put("agents", res.getAgents());
	    model.put("dataGrid", res.getSaleAgentData());
	    model.put("count", res.getCount());
	    model.put("sumCpAgentData", res.getSumCpAgentData());
	    return "financial/sale_agent_data";
	}

	/**
	 * 财务线下提现登记记录
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/financial/financeWithdrawRecords")
	public String financeWithdrawRecords(FinanceWithdrawRecordVO req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
	    List<FinanceWithdrawRecordVO> grids = financialAccountService.financeWithdrawRecords(req);
	    Integer count = financialAccountService.countFinanceWithdrawRecords(req);
	    CookieManager cookieManager = new CookieManager(request);
	    String opUserIdStr = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
	    MUser mUser = mUserService.findMUser(Integer.parseInt(opUserIdStr));
	    if(1 == mUser.getRoleId()) {
	        model.put("isSupperManager", "yes");
	    } else {
	        model.put("isSupperManager", "no");
	    }
	    model.put("req", req);
	    model.put("count", count);
	    model.put("dataGrid", grids);
	    return "financial/finance_withdraw_records";
	}

	/**
	 * 财务线下提现登记
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/financialRegistry")
	public Map<String, Object> financialRegistry(HttpServletRequest request, HttpServletResponse response) {
	    Map<String, Object> result = new HashMap<String, Object>();
	    try {
	        String amountStr = request.getParameter("amount");
	        NumberTool number = new NumberTool();
	        amountStr = number.format("0.00", amountStr);
	        BigDecimal bigDecimal = new BigDecimal(amountStr);
	        Double amount = bigDecimal.doubleValue();
	        String withdrawTime = request.getParameter("withdrawTime");
	        CookieManager cookieManager = new CookieManager(request);
	        String opUserIdStr = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
	        financialAccountService.financialRegistry(amount, withdrawTime, Integer.parseInt(opUserIdStr));
	        result.put("statusCode", "200");
        } catch (Exception e) {
            result.put("statusCode", "300");
            result.put("message", e.getMessage());
        }
	    return result;
	}

	@ResponseBody
    @RequestMapping("/financial/confirmFinancialRegistry")
    public Map<String, Object> confirmFinancialRegistry(HttpServletRequest request, HttpServletResponse response) {
        Map<String, Object> result = new HashMap<String, Object>();
        try {
            String idStr = request.getParameter("id");
            CookieManager cookieManager = new CookieManager(request);
            String opUserIdStr = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
            financialAccountService.confirmFinancialRegistry(Integer.parseInt(idStr), Integer.parseInt(opUserIdStr));
            result.put("statusCode", "200");
        } catch (Exception e) {
            result.put("statusCode", "300");
            result.put("message", e.getMessage());
        }
        return result;
    }

	@RequestMapping("/financial/addFinanceWithdrawRecord")
	public String addFinanceWithdrawRecord(HttpServletRequest request, HttpServletResponse response) {
	    return "financial/add_finance_withdraw_record";
	}

	/**
	 * 财务充值宝付
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/financial/topUp2BaofooIndex")
	public String topUp2BaofooIndex(HttpServletRequest request, HttpServletResponse response) {
	    return "financialTopUp/topUp2Baofoo";
	}

	/**
	 * 财务充值宝付预下单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/topUp2BaofooPre")
	public Map<String, Object> topUp2BaofooPre(HttpServletRequest request, HttpServletResponse response, String bindId, Double amount ) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			// 财务充值账号
			log.info("baofooBindId = {}", baofooBindId);
			PreTopUpResVO preTopUpResVO = mSysTopUp2BaoFooService.preTopUp(baofooBindId, amount);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(preTopUpResVO.getResCode())){
				result.put("statusCode", "200");
				result.put("transId", preTopUpResVO.getTransId());
			}else{
				result.put("statusCode", "300");
	            result.put("message", preTopUpResVO.getResMsg());
			}
        } catch (Exception e) {
            result.put("statusCode", "300");
            result.put("message", e.getMessage());
        }
        return result;
	}


	@RequestMapping("/financial/topUp2BaofooPre2")
	public String topUp2BaofooPre2(HttpServletRequest request, HttpServletResponse response, String bindId, Double amount, String transId, Map<String, Object> model) {
		model.put("bindId", bindId);
		model.put("amount", amount);
		model.put("transId", transId);
	    return "financialTopUp/topUp2BaofooPre2";
	}

	/**
	 * 财务充值宝付正式下单
	 * @param request
	 * @param response
	 * @param bindId
	 * @param amount
	 * @param transId
	 * @param smsCode
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/topUp2Baofoo")
	public Map<String, Object> topUp2Baofoo(HttpServletRequest request, HttpServletResponse response, String bindId, Double amount, String transId,String smsCode ) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			log.info("baofooBindId = {}", baofooBindId);
			PreTopUpResVO preTopUpResVO = mSysTopUp2BaoFooService.topUp(transId, baofooBindId, amount, smsCode);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(preTopUpResVO.getResCode())){
				result.put("statusCode", "200");
			}else{
				result.put("statusCode", "300");
	            result.put("message", preTopUpResVO.getResMsg());
			}

        } catch (Exception e) {
            result.put("statusCode", "300");
            result.put("message", e.getMessage());
        }
        return result;
	}



	// ============ 2017-1-11 财务需求 ===================================================

	/**
	 * 赞分期风险保证金系统充值页面
	 * @return
     */
	@RequestMapping("/financial/margin_recharge_index")
	public String margin_recharge_index() {
		return "financial/margin/recharge_index";
	}

	/**
	 * 赞分期风险保证金系统充值模态框
	 * @param bindId	宝付绑定ID
	 * @param amount	充值金额
	 * @param transId	交易号
	 * @param model
     * @return
     */
	@RequestMapping("/financial/margin_recharge_modal")
	public String margin_recharge_modal(String bindId, Double amount, String transId, Map<String, Object> model) {
		model.put("bindId", bindId);
		model.put("amount", amount);
		model.put("transId", transId);
		return "financial/margin/recharge_modal";
	}

	/**
	 * 赞分期风险保证金系统快捷预充值
	 * @param bindId	绑定ID
	 * @param amount	充值金额
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/margin_recharge_pre")
	public Map<String, Object> marginRechargePre(String bindId, String amount) {
		Map<String, Object> result = new HashMap<>();
		try {
			log.info("赞分期风险保证金系统快捷预充值请求信息。bindId：{}，金额：{}", baofooBindId, amount);
			PreTopUpResVO vo = mSysTopUp2BaoFooService.preTopUp(baofooBindId, Double.valueOf(amount));
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(vo.getResCode())){
				log.info("赞分期风险保证金系统快捷预充值成功");
				result.put("statusCode", "200");
				result.put("transId", vo.getTransId());
			}else{
				log.info("赞分期风险保证金系统快捷预充值失败，失败原因：{}", vo.getResMsg());
				result.put("statusCode", "300");
				result.put("message", vo.getResMsg());
			}
		} catch (Exception e) {
			log.info("赞分期风险保证金系统快捷预充值异常：{}", e.getMessage());
			result.put("statusCode", "300");
			result.put("message", e.getMessage());
		}
		return result;
	}

	/**
	 * 赞分期风险保证金系统快捷充值正式
	 * @param bindId	绑定ID
	 * @param amount	金额
	 * @param transId	交易流水号
	 * @param smsCode	验证码
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/margin_recharge")
	public Map<String, Object> marginRecharge(String bindId, Double amount, String transId,String smsCode) {
		Map<String, Object> result = new HashMap<>();
		PreTopUpResVO preTopUpResVO = new PreTopUpResVO();
		log.info("baofooBindId = {}", baofooBindId);
		try {
			preTopUpResVO = mSysTopUp2BaoFooService.topUp(transId, baofooBindId, amount, smsCode);
		} catch (Exception e) {
			log.error("赞分期风险保证金系统快捷正式充值异常：{}", e.getMessage());
			result.put("statusCode", "300");
			result.put("message", e.getMessage());
			return result;
		}

		if(ConstantUtil.BSRESCODE_SUCCESS.equals(preTopUpResVO.getResCode())) {
			log.info("赞分期风险保证金系统快捷正式充值成功");
			result.put("statusCode", "200");
			try {
				// 充值成功后更新赞分期风险保证金户金额
				marginSysSubAccountService.updateMarginSysSubAccount(amount);
				log.info("赞分期风险保证金系统快捷正式充值成功，更新赞分期风险保证金户金额成功。金额：{}", amount);
			} catch (Exception e) {
				log.error("充值成功后更新赞分期风险保证金户金额异常：{}", e.getMessage());
				result.put("statusCode", "300");
				result.put("message", null == e.getMessage() ? "充值成功后更新赞分期风险保证金户金额异常" : e.getMessage());
				return result;
			}
		} else {
			log.info("赞分期风险保证金系统快捷正式充值失败，失败原因：{}", preTopUpResVO.getResMsg());
			result.put("statusCode", "300");
			result.put("message", preTopUpResVO.getResMsg());
		}

		return result;
	}

	/**
	 * 业务概览-北京财务
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/financialAccount/business_overview_index")
	public String businessOverviewFinance(ReqMsg_FinancialAccount_AccountDataList req, HttpServletRequest request, Map<String, Object> model) {

		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if(pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(request.getParameter("orderDirection") != null && request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
			model.put("orderField", request.getParameter("orderField"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("create_time");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		if(StringUtil.isBlank(req.getStartTime()) && StringUtil.isBlank(req.getEndTime())) {
			req.setStartTime(DateUtil.formatYYYYMMDD(DateUtil.addMonths(new Date(), -1)));
			req.setEndTime(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));
		}
		if(StringUtil.isBlank(req.getStartTime()) && !StringUtil.isBlank(req.getEndTime())) {
			req.setStartTime(DateUtil.formatYYYYMMDD(DateUtil.addMonths(DateUtil.parseDate(req.getEndTime()), -6)));
		}
		if(!StringUtil.isBlank(req.getStartTime()) && StringUtil.isBlank(req.getEndTime())) {
			req.setEndTime(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));
		}
		ResMsg_FinancialAccount_AccountDataList res = (ResMsg_FinancialAccount_AccountDataList) hessianService.handleMsg(req);
		//存量总额
		model.put("yundaiTotalAmount", res.getYundaiTotalAmount());
		model.put("totalAmount7Dai", res.getTotalAmount7Dai());
		model.put("zanTotalAmount", res.getZanTotalAmount());
		model.put("totalAmount", res.getTotalAmount());
		//融资总额
		model.put("yundaiFinancingAmount", res.getYundaiFinancingAmount());
		model.put("financingAmount7Dai", res.getFinancingAmount7Dai());
		model.put("zanFinancingAmount", res.getZanFinancingAmount());
		model.put("financingAmount", res.getFinancingAmount());
		model.put("res", res);
		model.put("req", req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", req.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("dataList", res.getValueList());
		return "/financial/business_overview_index";
	}

	/**
	 * 业务概览-北京财务 导出 excel
	 * @param req
	 * @param response
	 * @param request
     */
	@RequestMapping("/financialAccount/exportXls")
	public void exportXls(ReqMsg_FinancialAccount_AccountDataList req, HttpServletResponse response, HttpServletRequest request) {
		List<Map<String, List<Object>>> list1 = new ArrayList<Map<String, List<Object>>>();
		List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();

		// 设置标题1
		Map<String, List<Object>> titleMap1 = new HashMap<String, List<Object>>();
		List<Object> titles1 = new ArrayList<Object>();
		titles1.add("当前云贷存量总额");
		titles1.add("当前七贷存量总额");
		titles1.add("当前赞分期存量总额");
		titles1.add("累计理财总额");
		titles1.add("累计云贷融资总额");
		titles1.add("累计七贷融资总额");
		titles1.add("累计赞分期融资总额");
		titles1.add("累计融资总额");
		titleMap1.put("title", titles1);
		list1.add(titleMap1);

		// 设置标题2
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("日期");
		titles.add("理财总金额");
		titles.add("理财金额-云贷");
		titles.add("理财金额-七贷");
		titles.add("理财金额-赞分期");
		titles.add("赎回总金额");
		titles.add("赎回金额-云贷");
		titles.add("赎回金额-七贷");
		titles.add("赎回金额-赞分期");
		titles.add("融资总金额");
		titles.add("融资金额-云贷");
		titles.add("融资金额-七贷");
		titles.add("融资金额-赞分期");
		titleMap.put("title", titles);
		list.add(titleMap);
		if(StringUtil.isBlank(req.getStartTime()) && StringUtil.isBlank(req.getEndTime())) {
			req.setStartTime(DateUtil.formatYYYYMMDD(DateUtil.addMonths(new Date(), -1)));
			req.setEndTime(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));
		}
		if(StringUtil.isBlank(req.getStartTime()) && !StringUtil.isBlank(req.getEndTime())) {
			req.setStartTime(DateUtil.formatYYYYMMDD(DateUtil.addMonths(DateUtil.parseDate(req.getEndTime()), -6)));
		}
		if(!StringUtil.isBlank(req.getStartTime()) && StringUtil.isBlank(req.getEndTime())) {
			req.setEndTime(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)));
		}
		ResMsg_FinancialAccount_AccountDataList resp = (ResMsg_FinancialAccount_AccountDataList) hessianService.handleMsg(req);
		List<HashMap<String, Object>> datas = resp.getValueList();

		Map<String, List<Object>> objMap1 = new HashMap<>();
		List<Object> vo1 = new ArrayList<>();
		vo1.add(resp.getYundaiTotalAmount());
		vo1.add(resp.getTotalAmount7Dai());
		vo1.add(resp.getZanTotalAmount());
		vo1.add(resp.getTotalAmount());
		vo1.add(resp.getYundaiFinancingAmount());
		vo1.add(resp.getFinancingAmount7Dai());
		vo1.add(resp.getZanFinancingAmount());
		vo1.add(resp.getFinancingAmount());

		objMap1.put("businessOverviewFinance", vo1);
		list1.add(objMap1);

		//设置导出excel内容
		if (datas != null && !datas.isEmpty()) {

			Map<String, List<Object>> objMap = new HashMap<String, List<Object>>();
			List<Object> vo = new ArrayList<Object>();
			vo.add("总计");
			vo.add(resp.getTotalBuyAmount());
			vo.add(resp.getBuyAmountForYunDai());
			vo.add(resp.getBuyAmountFor7Dai());
			vo.add(resp.getBuyAmountForZan());
			vo.add(resp.getTotalReturnAmount());
			vo.add(resp.getReturnAmountForYunDai());
			vo.add(resp.getReturnAmountFor7Dai());
			vo.add(resp.getReturnAmountForZan());
			vo.add(resp.getTotalFinanceAmount());
			vo.add(resp.getFinanceAmountToYunDai());
			vo.add(resp.getFinanceAmountTo7Dai());
			vo.add(resp.getFinanceAmountToZan());
			objMap.put("businessOverviewFinance", vo);
			list.add(objMap);
			
			int i = 0;
			for (HashMap<String, Object> data : datas) {
				Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("time"));
				obj.add(data.get("totalBuyAmount"));
				obj.add(data.get("buyAmountForYunDai"));
				obj.add(data.get("buyAmountFor7Dai"));
				obj.add(data.get("buyAmountForZan"));
				obj.add(data.get("totalReturnAmount"));
				obj.add(data.get("returnAmountForYunDai"));
				obj.add(data.get("returnAmountFor7Dai"));
				obj.add(data.get("returnAmountForZan"));
				obj.add(data.get("totalFinanceAmount"));
				obj.add(data.get("totalFinanceAmount"));
				obj.add(data.get("financeAmountTo7Dai"));
				obj.add(data.get("financeAmountToZan"));
				datasMap.put("businessOverviewFinance" + (++i), obj);
				list.add(datasMap);
			}
		}

		try {
			ExportUtil.exportExcelMultipleHead(request, response, "业务概览-北京财务", list1, list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	// ================================ 2017-4-19 财务需求 start ================================

	/**
	 * 恒丰系统提现-订单列表
	 * @param req
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping("/financial/withdraw2HFBankOrderIndex")
	public String withdraw2HFBankOrderList(ReqMsg_MAccount_QueryHFWithdrawOrder req, HttpServletRequest request, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QueryHFWithdrawOrder res = (ResMsg_MAccount_QueryHFWithdrawOrder) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("withdrawOrderList", res.getValueList());
		return "financialTopUp/withdraw2HFBank";
	}

	/**
	 * 进入恒丰系统提现页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/withdraw2HFBankdetail")
	public String withdrawDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.HFBANK_SYS_WITHDRAW); //存管专户平台提现卡号
		if(configUser == null) {
			log.info("存管专户平台提现卡号在配置表中未初始化");
		}else {
			model.put("withdrawCard", configUser.getConfValue());
		}
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);

		return "/financialTopUp/withdraw2HFBank_detail";
	}

	/**
	 * 恒丰系统提现
	 * @param request
	 * @param response
	 * @param amount 提现金额
	 * @param note 备注
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/withdraw2HFBankIndex")
	public Map<String, Object> withdraw2HFBankIndex(HttpServletRequest request, HttpServletResponse response, Double amount, String note) {

		Map<String, Object> result = new HashMap<>();
		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}

		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));

		BsHfBankSysWithdrawVo hfBankSysWithdrawVo = new BsHfBankSysWithdrawVo();
		hfBankSysWithdrawVo.setAmount(amount);
		if(StringUtil.isNotBlank(note)) {
			note = note;
		}else {
			note = " ";
		}
		//恒丰系统账户划转-存入订单表bs_pay_orders表时，note字段，会同时存入备注/操作人，以“;”拼接的一个字符串
		note = note + ";" + mUser.getName();
		hfBankSysWithdrawVo.setNote(note);

		try {
			String resCode = bsHfBankService.sysWithdraw(hfBankSysWithdrawVo);

			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
				log.info("恒丰系统提现通讯成功");
				result.put("statusCode", "200");
			}else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
				log.info("恒丰系统提现通讯异常，提现失败");
				result.put("statusCode", "300");
			}else {
				log.info("恒丰系统提现通讯异常");
				result.put("statusCode", "301");
			}
		}catch(Exception e) {
			result.put("statusCode", "302");
			log.info("恒丰系统提现失败的原因：{}",e.getMessage());
			result.put("message", null == e.getMessage() ? "余额不足" : e.getMessage());
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);

		return result;
	}

	/**
	 * 恒丰系统充值-订单列表
	 * @param req
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping("/financial/topUp2HFBankOrderIndex")
	public String topUp2HFBankOrderList(ReqMsg_MAccount_QueryHFTopUpOrder req, HttpServletRequest request, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QueryHFTopUpOrder res = (ResMsg_MAccount_QueryHFTopUpOrder) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("topUpOrderList", res.getValueList());
		return "financialTopUp/topUp2HFBank";
	}

	/**
	 * 进入恒丰系统充值页面
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/financial/topUp2HFBankdetail")
	public String topUpDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.HFBANK_SYS_TOP_UP); //存管专户平台充值卡号
		if(configUser == null) {
			log.info("存管专户平台充值卡号在配置表中未初始化");
		}else {
			model.put("topUpCard", configUser.getConfValue());
		}
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);

		return "/financialTopUp/topUp2HFBank_detail";
	}

	/**
	 * 恒丰系统充值
	 * @param request
	 * @param response
	 * @param amount
	 * @param note
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/topUp2HFBankIndex")
	public Map<String, Object> topUp2HFBankIndex(HttpServletRequest request, HttpServletResponse response, Double amount, String note) {

		Map<String, Object> result = new HashMap<>();
		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}

		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));

		BsHfBankSysTopUpVo bsHfBankSysTopUpVo = new BsHfBankSysTopUpVo();
		bsHfBankSysTopUpVo.setAmount(amount);

		if(StringUtil.isNotBlank(note)) {
			note = note;
		}else {
			note = " ";
		}
		//恒丰系统账户划转-存入订单表bs_pay_orders表时，note字段，会同时存入备注/操作人，以“;”拼接的一个字符串
		note = note + ";" + mUser.getName();
		bsHfBankSysTopUpVo.setNote(note);
		try {
			String resCode = bsHfBankService.sysTopUp(bsHfBankSysTopUpVo);

			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
				log.info("恒丰系统充值通讯成功");
				result.put("statusCode", "200");
			}else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
				log.info("恒丰系统充值通讯异常，充值失败");
				result.put("statusCode", "300");
			}else {
				log.info("恒丰系统充值通讯异常");
				result.put("statusCode", "301");
			}
		}catch(Exception e) {
			result.put("statusCode", "302");
			log.info("恒丰系统充值失败的原因：{}",e.getMessage());
			result.put("message", null == e.getMessage() ? "通讯异常" : e.getMessage());
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);

		return result;
	}

	/**
	 * 财务功能-恒丰系统账户划转-订单列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/accountTransfer2HFBankIndex")
	public String accountTransfer2HFBankOrderList(ReqMsg_MAccount_QueryHFBankSysAccountTransfer req, HttpServletRequest request, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QueryHFBankSysAccountTransfer res = (ResMsg_MAccount_QueryHFBankSysAccountTransfer) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("accountTransferList", res.getValueList());
		return "financial/hFBankAccountTransfer_index";
	}

	/**
	 * 进入恒丰系统账户划转页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/accountTransfer2HFBankdetail")
	public String accountTransferDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);
		System.out.println("create token:  " + serverToken);

		return "/financial/hFBankAccountTransfer_detail";
	}

	/**
	 * 进入恒丰系统账户划转
	 * @param request
	 * @param response
	 * @param amount 转账金额
	 * @param destAccount 转出账户
	 * @param sourceAccount 转入账户
     * @param note
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/accountTransfer2HFBank")
	public Map<String, Object> accountTransfer2HFBank(HttpServletRequest request, HttpServletResponse response,
													   Double amount, String destAccount,
													   String sourceAccount, String note) {

		Map<String, Object> result = new HashMap<>();

		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}

		BsHfBankSysAccountTransferVo sysAccountTransferVo = new BsHfBankSysAccountTransferVo();
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		//转出账户
		String transferOutAccount = "";
    	//转入账户
		String transferInAccount = "";

		//转出账户-参数
		switch(sourceAccount) {
			case Constants.SYS_ACCOUNT_DEP_JSH:
				transferOutAccount = "自有子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_REDPACKET:
				transferOutAccount = "红包子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN:
				transferOutAccount = "币港湾营收子账户（赞分期）";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN:
				transferOutAccount = "币港湾营收子账户（云贷）";
				break;
			case Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN:
				transferOutAccount = "砍头息子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_OTHER_FEE:
				transferOutAccount = "其他费用户";
				break;
			case Constants.SYS_ACCOUNT_DEP_ADVANCE:
				transferOutAccount = "垫付金子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD:
				transferOutAccount = "砍头息子账户（赞时贷）";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD:
				transferOutAccount = "币港湾营收子账户（赞时贷）";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7:
				transferOutAccount = "币港湾营收子账户（7贷）";
				break;
		}

		//转入账户-参数
		switch(destAccount) {
			case Constants.SYS_ACCOUNT_DEP_JSH:
				transferInAccount = "自有子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_REDPACKET:
				transferInAccount = "红包子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZAN:
				transferInAccount = "币港湾营收子账户（赞分期）";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_YUN:
				transferInAccount = "币港湾营收子账户（云贷）";
				break;
			case Constants.SYS_ACCOUNT_DEP_HEADFEE_YUN:
				transferInAccount = "砍头息子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_OTHER_FEE:
				transferInAccount = "其他费用户";
				break;
			case Constants.SYS_ACCOUNT_DEP_ADVANCE:
				transferInAccount = "垫付金子账户";
				break;
			case Constants.SYS_ACCOUNT_DEP_HEADFEE_ZSD:
				transferInAccount = "砍头息子账户（赞时贷）";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_ZSD:
				transferInAccount = "币港湾营收子账户（赞时贷）";
				break;
			case Constants.SYS_ACCOUNT_DEP_BGW_REVENUE_7:
				transferInAccount = "币港湾营收子账户（7贷）";
				break;
		}
		sysAccountTransferVo.setAmount(amount);
		sysAccountTransferVo.setDestAccount(destAccount);
		sysAccountTransferVo.setSourceAccount(sourceAccount);

		if(StringUtil.isBlank(note)) {
			note = " ";
		}
		//恒丰系统账户划转-存入订单表bs_pay_orders表时，note字段，会同时存入操作人/转出账户/转入账户，以“;”拼接的一个字符串
		note = note + ";" + mUser.getName() + ";" + transferOutAccount + ";" + transferInAccount;
		sysAccountTransferVo.setNote(note);
		try {
			String resCode = bsHfBankService.sysAccountTransfer(sysAccountTransferVo);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
				log.info("恒丰系统划账成功");
				result.put("statusCode", "200");
			}else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
				log.info("恒丰系统划账失败");
				result.put("statusCode", "300");
			}else {
				log.info("恒丰系统划账账户余额不足");
				result.put("statusCode", "301");
			}

		}catch (Exception e) {
			result.put("statusCode", "302");
			log.info("恒丰系统划账失败的原因：{}",e.getMessage());
			result.put("message", null == e.getMessage() ? "余额不足" : e.getMessage());
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);
		System.out.println("put a token:  " + serverToken);

		return result;
	}
	
	/**
	 * 赞分期代偿人充值/提现订单-列表
	 * @param req
	 * @param request
	 * @param model
     * @return
     */
	@RequestMapping("/financial/zanCompensateListIndex")
	public String queryZanCompensateListInit(ReqMsg_MAccount_QueryZanCompensateList req, HttpServletRequest request,
											 HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QueryZanCompensateList res = (ResMsg_MAccount_QueryZanCompensateList) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("zanCompensateAmount", res.getZanCompensateAmount());
		model.put("zanUserList", res.getZanUserList());
		model.put("zanCompensateList", res.getValueList());
		//生成token-预下单时使用
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);
		return "/financial/zanCompensateList_index";
	}
	

	/**
	 * 赞分期代偿人充值预下单/正式下单 需要优先校验代偿人是否已经成功绑卡
	 * @param request
	 * @param response
	 * @param userId
	 * @param amount
	 * @param smsCode 正式下单时验证码,有验证码的是充值正式下单，无验证码则是充值预下单
	 * @param orderNo 预下单订单号
     * @param model
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/accountZanCompensateTopUp")
	public Map<String, Object> accountZanCompensateTopUp(HttpServletRequest request, HttpServletResponse response,
													String userId, Double amount, String smsCode,
													String orderNo, Map<String, Object> model) {

		Map<String, Object> result = new HashMap<>();
		//防重复提交-预下单时
		if(!StringUtil.isNotEmpty(smsCode)) {
			if(com.pinting.util.Util.isRepeatSubmit(request)){
				log.info("请勿重复提交");
				result.put("statusCode", "305");
				return result;
			}
		}
		//防重复提交-正式下单时
		if(StringUtil.isNotEmpty(smsCode)) {
			if(com.pinting.util.Util.isRepeatSubmit(request)){
				log.info("请勿重复提交");
				result.put("statusCode", "305");
				return result;
			}
		}

		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));

		//校验代偿人是否已经成功绑卡
		BsBindBankVO vo = bsBankCardService.queryZanBankCardInfo(Integer.parseInt(userId));
		if(vo == null) {
			log.info("赞分期代偿人未成功绑卡");
			result.put("statusCode", "300");
			return result;
		}

		ReqMsg_RegularBuy_Order preReq = new ReqMsg_RegularBuy_Order();
		ResMsg_RegularBuy_Order preRes = new ResMsg_RegularBuy_Order();
		preReq.setAmount(amount);
		preReq.setUserId(Integer.parseInt(userId));
		preReq.setCardNo(vo.getCardNo());
		preReq.setUserName(vo.getUserName());
		preReq.setIdCard(vo.getIdCard());
		preReq.setMobile(vo.getMobile());
		preReq.setBankId(vo.getBankId());
		preReq.setBankName(vo.getBankName());
		preReq.setIsBind(1);//用户是否绑定-1已绑定
		preReq.setTransType(2);//交易类型-2充值
		preReq.setTerminalType(5);//终端类型-管理台
		preReq.setPlaceOrder(1);//下单类型-1预下单
		preReq.setAccountType("2");//2-融资账户（代偿人）

		if(StringUtil.isNotEmpty(smsCode)) {
			//(2) 恒丰-充值正式下单（快捷）
			try {
				preReq.setPlaceOrder(2);//下单类型-2正式下单
				preReq.setOrderNo(orderNo);
				preReq.setUserId(Integer.parseInt(userId));
				preReq.setVerifyCode(smsCode);

				depUserTopUpService.hfConfirm(preReq, preRes);
				log.info("赞分期代偿人充值正式下单成功");
				result.put("statusCode", "200");//输入验证码正式下单
			} catch (PTMessageException e) {
				result.put("statusCode", "304");
				result.put("message", null == e.getMessage() ? "提现失败请重试！" : e.getMessage());
				log.info("赞分期代偿人充值正式下单失败的原因：" + e.getMessage());
			} catch (Exception e) {
				result.put("statusCode", "300");
			}

		} else {//(1) 恒丰-充值预下单（快捷）
			try {
				depUserTopUpService.hfPre(preReq, preRes);
				log.info("赞分期代偿人充值预下单成功");
				result.put("statusCode", "200");//输入验证码发起正式下单
				result.put("orderNo", preRes.getOrderNo());
			} catch (PTMessageException e) {//自定异常

				if(StringUtil.isNotBlank(e.getErrCode())) {
					if("931009".equals(e.getErrCode()) || "931010".equals(e.getErrCode()) || "931011".equals(e.getErrCode()) || "931019".equals(e.getErrCode())) {
						result.put("statusCode", "301");
						log.info("赞分期代偿人充值预下单失败的原因：" + e.getMessage());
						result.put("message", e.getMessage());
					}else {
						result.put("statusCode", "302");
						log.info("赞分期代偿人充值预下单失败的原因：" + e.getMessage());
						result.put("message", e.getMessage());
					}
				}else {
					result.put("statusCode", "302");
					log.info("赞分期代偿人充值预下单失败的原因：" + e.getMessage());
					result.put("message", e.getMessage());
				}

			} catch (Exception e) {//系统异常
				result.put("statusCode", "300");
			}
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);

		return result;
	}

	/**
	 * 赞分期代偿人提现 需要优先校验代偿人是否已经成功绑卡
	 * 提现前先做短信校验
	 * @param request
	 * @param response
	 * @param userId
	 * @param amount
	 * @param smsCode
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/accountZanCompensateWithdraw")
	public Map<String, Object> accountZanCompensateWithdraw(HttpServletRequest request, HttpServletResponse response,
													String userId, Double amount, String smsCode,
													Map<String, Object> model) {
		Map<String, Object> result = new HashMap<>();

		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}

		BsHfBankSysAccountTransferVo sysAccountTransferVo = new BsHfBankSysAccountTransferVo();
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		String userName = "";

		sysAccountTransferVo.setUserName(userName);

		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE); //赞分期代偿人提现验证手机号
		if(configUser == null) {
			log.info("赞分期代偿人提现审核验证的手机号，未在配置表中初始化");
			result.put("statusCode", "302");
			return result;
		}

		try {
			//验证输入验证码是否正确
			boolean foo = smsService.validateIdentity(configUser.getConfValue(), smsCode);
			if(!foo) {
				log.info("验证码不正确");
				result.put("statusCode", "303");
				return result;
			} else {
				//验证成功-发起提现流程
				ReqMsg_UserBalance_Withdraw withdrawReq = new ReqMsg_UserBalance_Withdraw();
				ResMsg_UserBalance_Withdraw withdrawRes = new ResMsg_UserBalance_Withdraw();

				withdrawReq.setUserId(Integer.parseInt(userId));
				withdrawReq.setAmount(amount);
				withdrawReq.setTerminalType(5);//终端类型-5 管理台提现
				withdrawReq.setManageId(Integer.parseInt(mUserId));
				withdrawReq.setAccountType("DEP_WITHDRAW");//用户提现（存管）
				withdrawReq.setWithdrawType("1");

				depUserBalanceWithdrawService.preCompensatorApply(withdrawReq, withdrawRes);
				log.info("赞分期代偿人提现成功");
				result.put("statusCode", "200");
			}

		}catch (PTMessageException e) {//自定异常
			result.put("statusCode", "304");
			log.info("赞分期代偿人提现失败的原因：" + e.getMessage());
			result.put("message", null == e.getMessage() ? "提现失败请重试！" : e.getMessage());

		} catch (Exception e) {//系统异常
			result.put("statusCode", "300");
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);

		return result;
	}

	/**
	 * 赞分期代偿人充值-确认下单发送验证码
	 * @param request
	 * @param response
	 * @param orderNo 充值预下单返回的订单号
	 * @param userId
	 * @param amount
     * @param model
     * @return
     */
	@RequestMapping("/financial/zanCompensateTopUpPre")
	public String zanCompensateTopUpPre(HttpServletRequest request, HttpServletResponse response,
										String orderNo, String userId, Double amount, Map<String, Object> model) {
		model.put("orderNo", orderNo);
		model.put("userId", userId);
		model.put("amount", amount);
		//生成token-正式下单时使用
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);

		return "financial/zanCompensateTopUpPre";
	}

	/**
	 * 赞分期代偿人提现-校验手机号验证码发送的次数
	 * @param request
	 * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/checkSendMobileCount")
	public Map<String, Object> checkSendMobileCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();

		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE); //赞分期代偿人提现验证手机号
		if(configUser == null) {
			log.info("赞分期代偿人提现审核验证手机号初始数据不存在");
			result.put("statusCode", "302");
			return result;
		}
		
		result.put("statusCode", "200");
		return result;
	}


	/**
	 * 赞分期代偿人提现发送短信验证码
	 * @param request
	 * @param response
	 * @param userId
	 * @param amount
     * @param model
     * @return
     */
	@RequestMapping("/financial/zanCompensateWithdraw")
	public String zanCompensateWithdraw(HttpServletRequest request, HttpServletResponse response,
										String userId, Double amount,  Map<String, Object> model) {
		model.put("userId", userId);
		model.put("amount", amount);

		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE);//赞分期代偿人提现验证手机号

		if(configUser == null) {
			log.info("赞分期代偿人提现审核验证手机号初始数据不存在，短信发送失败");
		}else {
			model.put("checkMobile", configUser.getConfValue());//发送短信的手机号码
			try {
				String resString = smsService.sendIdentify(configUser.getConfValue());
			}catch(PTMessageException e) {
				log.info("赞分期代偿人提现发送短信验证码失败的原因：" + e.getMessage());
			}catch(Exception e){
				log.info("赞分期代偿人提现发送短信验证码失败的原因：{}",e.getMessage());
			}
		}

		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);

		return "financial/zanCompensateWithdraw";
	}
	
	/**
	 * 财务功能-垫付金人工调账-订单列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/advanceTransferOrderIndex")
	public String advanceTransferOrderList(ReqMsg_MAccount_QueryAdvanceTransferOrder req, HttpServletRequest request, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QueryAdvanceTransferOrder res = (ResMsg_MAccount_QueryAdvanceTransferOrder) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("advanceTransferList", res.getValueList());
		return "financial/advanceTransfer_index";
	}

	/**
	 * 进入垫付金人工调账页面-自有子账户转垫付金账户
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/financial/advanceTransfer2HFBank")
	public String advanceTransferDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);

		return "/financial/hFBankAdvanceTransfer_detail";
	}

	/**
	 * 垫付金人工调账,自有子账户转垫付金账户,不记账只记订单
	 * @param request
	 * @param response
	 * @param amount 转账金额
	 * @param note
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/advanceTransferAccount")
	public Map<String, Object> advanceTransferAccount(HttpServletRequest request, HttpServletResponse response,
													  Double amount, String note) {

		Map<String, Object> result = new HashMap<>();

		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}

		BsHfBankSysAccountTransferVo sysAccountTransferVo = new BsHfBankSysAccountTransferVo();
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));

		//转出账户-自有子账户
		String sourceAccount = Constants.SYS_ACCOUNT_DEP_JSH;
		//转入账户-垫付金子账户
		String destAccount = Constants.SYS_ACCOUNT_DEP_ADVANCE;
		//转出账户
		String transferOutAccount = "自有子账户";
		//转入账户
		String transferInAccount = "垫付金子账户";
		sysAccountTransferVo.setAmount(amount);
		sysAccountTransferVo.setDestAccount(destAccount);
		sysAccountTransferVo.setSourceAccount(sourceAccount);

		if(StringUtil.isNotBlank(note)) {
			note = note;
		}else {
			note = " ";
		}
		//恒丰系统垫付金人工调账-存入订单表bs_pay_orders表时，note字段，会同时存入操作人/转出账户/转入账户，以“;”拼接的一个字符串
		note = note + ";" + mUser.getName() + ";" + transferOutAccount + ";" + transferInAccount;
		sysAccountTransferVo.setNote(note);
		try {
			String resCode = bsHfBankService.sysAccountTransferNoCharge(sysAccountTransferVo);
			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
				log.info("恒丰系统垫付金人工调账成功");
				result.put("statusCode", "200");
			}else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
				log.info("恒丰系统垫付金人工调账失败");
				result.put("statusCode", "300");
			}else {
				log.info("恒丰系统垫付金人工调账账户余额不足");
				result.put("statusCode", "301");
			}

		}catch (Exception e) {
			result.put("statusCode", "302");
			log.info("恒丰系统垫付金人工调账失败的原因：{}",e.getMessage());
			result.put("message", null == e.getMessage() ? "垫付金人工调账余额不足" : e.getMessage());
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);

		return result;
	}

	// ================================ 2017-4-19 财务需求 end ================================


	// ================================ 2017-9-29 财务需求 end ================================

	/**
	 * 借款人提现订单-列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/loanListIndex")
	public String queryLoanListInit(ReqMsg_MAccount_QueryLoanList req, HttpServletRequest request,
									HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QueryLoanList res = (ResMsg_MAccount_QueryLoanList) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("loanList", res.getValueList());
		//生成token-预下单时使用
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);
		return "/financial/loanList_index";
	}

	/**
	 * 借款人提现 需要优先校验代偿人是否已经成功绑卡
	 * 提现前先做短信校验
	 * @param request
	 * @param response
	 * @param hfUserId
	 * @param bankCard
	 * @param amount
	 * @param smsCode
     * @param model
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/accountLoanerWithdraw")
	public Map<String, Object> accountLoanerWithdraw(HttpServletRequest request, HttpServletResponse response, String hfUserId,
													 String bankCard, Double amount, String smsCode, Map<String, Object> model) {
		Map<String, Object> result = new HashMap<>();
		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE); //赞分期代偿人提现验证手机号
		if(configUser == null) {
			log.info("借款人提现审核验证的手机号，未在配置表中初始化");
			result.put("statusCode", "302");
			return result;
		}
		LnUser lnUser = loanUserService.queryLoanUserByHfUserId(hfUserId);
		if (lnUser == null) {
			log.info("借款人不存在，请核对");
			result.put("statusCode", "306");
			return result;
		}
		LnBindCardExample bindCardExample = new LnBindCardExample();
		bindCardExample.createCriteria().andLnUserIdEqualTo(lnUser.getId()).andBankCardEqualTo(bankCard);
		List<LnBindCard> bindCardList = lnBindCardMapper.selectByExample(bindCardExample);
		if (CollectionUtils.isEmpty(bindCardList)) {
			log.info("借款人信息不存在，请核对");
			result.put("statusCode", "307");
			return result;
		}

		BsHfBankSysLoanerWithdrawVo bsHfBankSysLoanerWithdrawVo = new BsHfBankSysLoanerWithdrawVo();
		bsHfBankSysLoanerWithdrawVo.setAmount(amount);
		bsHfBankSysLoanerWithdrawVo.setHfUserId(hfUserId);
		bsHfBankSysLoanerWithdrawVo.setLoanUserId(lnUser.getId());
		bsHfBankSysLoanerWithdrawVo.setNote("");
		bsHfBankSysLoanerWithdrawVo.setBankCard(bankCard);
		try {
			//验证输入验证码是否正确
			boolean foo = smsService.validateIdentity(configUser.getConfValue(), smsCode);
			if(!foo) {
				log.info("验证码不正确");
				result.put("statusCode", "303");
				return result;
			} else {
				// 管理台直接调批量提现接口
				String resCode = bsHfBankService.sysLoanerWithdrawNoCharge(bsHfBankSysLoanerWithdrawVo);
				if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
					log.info("借款人提现申请成功");
					result.put("statusCode", "200");
				}else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
					log.info("借款人提现申请成功");
					result.put("statusCode", "300");
				}
			}
		}catch (PTMessageException e) {//自定异常
			result.put("statusCode", "304");
			log.info("借款人提现失败的原因：{}", e);
			result.put("message", null == e.getMessage() ? "提现失败请重试！" : e.getMessage());
		} catch (Exception e) {//系统异常
			result.put("statusCode", "300");
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);
		return result;
	}

	/**
	 * 借款人提现发送短信验证码
	 * @param request
	 * @param response
	 * @param hfUserId
	 * @param amount
	 * @param model
     * @return
     */
	@RequestMapping("/financial/loanerWithdraw")
	public String loanerWithdraw(HttpServletRequest request, HttpServletResponse response,
								 String hfUserId, Double amount, Map<String, Object> model) {
		model.put("hfUserId", hfUserId);
		model.put("amount", amount);

		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE);//赞分期代偿人提现验证手机号
		if(configUser == null) {
			log.info("借款人提现审核验证手机号初始数据不存在，短信发送失败");
		}else {
			model.put("checkMobile", configUser.getConfValue());//发送短信的手机号码
			try {
				String resString = smsService.sendIdentify(configUser.getConfValue());
			}catch(PTMessageException e) {
				log.info("借款人提现发送短信验证码失败的原因：" + e.getMessage());
			}catch(Exception e){
				log.info("借款人提现发送短信验证码失败的原因：{}",e.getMessage());
			}
		}
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);

		return "financial/loanerWithdraw";
	}

	/**
	 * 砍头息划转-列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/yunHeadFeeTransferIndex")
	public String queryYunHeadFeeTransfer(ReqMsg_MAccount_QueryYunHeadFeeTransferList req, HttpServletRequest request,
										  HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QueryYunHeadFeeTransferList res = (ResMsg_MAccount_QueryYunHeadFeeTransferList) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("transferList", res.getValueList());
		return "/financial/hfBankYunHeadFee_index";
	}

	/**
	 * 进入砍头息划转页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/yunHeadFeeTransferDetail")
	public String yunHeadFeeTransferDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);
		BsSysConfig yunHeadFee2User = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.YUN_HEAD_FEE_2_USER);
		BsUser yunUser = bsUserService.findUserByUserId(Integer.parseInt(yunHeadFee2User.getConfValue()));
		if(yunUser == null) {
			log.info("云贷砍头息划转目标用户配置数据不存在");
		} else {
			model.put("yunHeadFee2User", yunUser.getUserName()+"（云贷）");
		}
		//赞时贷
		BsSysConfig zsdHeadFee2User = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.ZSD_HEAD_FEE_2_USER);
		BsUser zsdUser = bsUserService.findUserByUserId(Integer.parseInt(zsdHeadFee2User.getConfValue()));
		if(zsdUser == null) {
			log.info("赞时贷砍头息划转目标用户配置数据不存在");
		} else {
			model.put("zsdHeadFee2User", zsdUser.getUserName()+"（赞时贷）");
		}
		return "/financial/hfBankYunHeadFee_detail";
	}

	/**
	 * 进入砍头息划转
	 * @param request
	 * @param response
	 * @param amount 转账金额
	 * @param transferOutAccount
	 * @param note
	 * @param smsCode
	 * @param isEnoughAmount 划转页面提交时判断
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/yunHeadFeeTransfer")
	public Map<String, Object> yunHeadFeeTransfer(HttpServletRequest request, HttpServletResponse response,
												  Double amount, String transferOutAccount, String note, String smsCode,
												  String isEnoughAmount, String destAccount) {
		Map<String, Object> result = new HashMap<>();
		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}

		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE);
		if(configUser == null) {
			log.info("划账审核验证的手机号，未在配置表中初始化");
			result.put("statusCode", "302");
			return result;
		}

		BsHfBankSysAccountTransferVo sysAccountTransferVo = new BsHfBankSysAccountTransferVo();
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));

		sysAccountTransferVo.setAmount(amount);
		if(StringUtil.isBlank(note)) {
			note = " ";
		}
		if (StringUtil.isBlank(transferOutAccount)) {
			transferOutAccount = "自有子账户";
		}
		//云贷砍头息划转-存入订单表bs_pay_orders表时，note字段，会同时存入操作人以“;”拼接的一个字符串
		note = note + ";" + mUser.getName();
		sysAccountTransferVo.setNote(note);
		sysAccountTransferVo.setDestAccount(destAccount);
		try {
			// 1、优先校验金额是否充足
			if(StringUtil.isNotEmpty(isEnoughAmount)) {
				BsSysSubAccount sysSourceSubAccount = bsSysSubAccountService.findSysSubAccount4Lock(com.pinting.business.util.Constants.SYS_ACCOUNT_DEP_JSH);
				if (MoneyUtil.subtract(sysSourceSubAccount.getAvailableBalance(), amount).doubleValue() < 0) {
					//余额不足
					result.put("statusCode", "302");
					log.info("砍头息划转失败的原因余额不足");
					return result;
				}
			}else {
				//验证输入验证码是否正确
				boolean foo = smsService.validateIdentity(configUser.getConfValue(), smsCode);
				if(!foo) {
					log.info("验证码不正确");
					result.put("statusCode", "303");
					return result;
				} else {
					String resCode = bsHfBankService.sysAccountTransferToPerson(sysAccountTransferVo);
					if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
						log.info("砍头息划转成功");
						result.put("statusCode", "200");
					}else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
						log.info("砍头息划转失败");
						result.put("statusCode", "300");
					}
				}
			}
		}catch (Exception e) {
			result.put("statusCode", "302");
			log.info("砍头息划转失败的原因：{}",e.getMessage());
			result.put("message", null == e.getMessage() ? "余额不足" : e.getMessage());
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);
		return result;
	}

	/**
	 * 砍头息划转发送短信验证码
	 * @param request
	 * @param response
	 * @param amount
	 * @param model
	 * @param note
     * @return
     */
	@RequestMapping("/financial/yunHeadFeeCode")
	public String yunHeadFeeCode(HttpServletRequest request, HttpServletResponse response,
								 Double amount, String note, String destAccount,
								 Map<String, Object> model) {
		model.put("amount", amount);
		model.put("note", note);
		model.put("destAccount", destAccount);
		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE);//赞分期代偿人提现验证手机号
		BsSysConfig headFeeUser = null;
		String propertySymbol = null;
		if (Constants.YUN_HEAD_FEE_2_USER.equals(destAccount)) {
			headFeeUser = sysConfigService.findConfigByKey(Constants.YUN_HEAD_FEE_2_USER);
			propertySymbol = "云贷";
		} else if (Constants.ZSD_HEAD_FEE_2_USER.equals(destAccount)) {
			headFeeUser = sysConfigService.findConfigByKey(Constants.ZSD_HEAD_FEE_2_USER);
			propertySymbol = "赞时贷";
		} 
		if(configUser == null) {
			log.info("砍头息划转验证手机号初始数据不存在，短信发送失败");
		}else {
			model.put("checkMobile", configUser.getConfValue());//发送短信的手机号码
			try {
				BsUser user = bsUserService.findUserByUserId(Integer.parseInt(headFeeUser.getConfValue()));
				model.put("userName", user.getUserName());
				String resString = smsService.sendYunHeadFeeIdentify(configUser.getConfValue(), propertySymbol, amount, user.getUserName());
			}catch(PTMessageException e) {
				log.info("砍头息划转发送短信验证码失败的原因：" + e.getMessage());
			}catch(Exception e){
				log.info("砍头息划转发送短信验证码失败的原因：{}",e.getMessage());
			}
		}
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);
		model.put("propertySymbol", propertySymbol);
		return "financial/hfBankYunHeadFee_verCode";
	}

	/**
	 * 砍头息划转-校验手机号验证码发送的次数
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/checkYunHeadFeeCodeCount")
	public Map<String, Object> checkYunHeadFeeCodeCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE); //赞分期代偿人提现验证手机号
		if(configUser == null) {
			log.info("砍头息划转验证手机号初始数据不存在");
			result.put("statusCode", "302");
			return result;
		}
	
		result.put("statusCode", "200");
		return result;
	}

	/**
	 * 砍头息划转-重发验证码
	 * @param request
	 * @param response
     * @return
     */
	@ResponseBody
	@RequestMapping("/financial/resendHeadFeeCode")
	public Map<String, Object> resendHeadFeeCode(HttpServletRequest request, HttpServletResponse response,
												 String destAccount, String userName,
												 String propertySymbol, String serverTokenTopUp,
												 Double amount) {
		Map<String, Object> result = new HashMap<>();
		BsSysConfig configUser = sysConfigService.findConfigByKey(com.pinting.business.util.Constants.COMPENSATE_WITHDRAW_CHECK_MOBILE); //赞分期代偿人提现验证手机号
		if(configUser == null) {
			log.info("砍头息划转验证手机号初始数据不存在");
			result.put("statusCode", "302");
			return result;
		}

		if (Constants.YUN_HEAD_FEE_2_USER.equals(destAccount)) {
			propertySymbol = "云贷";
		} else if (Constants.ZSD_HEAD_FEE_2_USER.equals(destAccount)) {
			propertySymbol = "赞时贷";
		}
		if(configUser == null) {
			log.info("砍头息划转验证手机号初始数据不存在，短信发送失败");
		}else {
			result.put("checkMobile", configUser.getConfValue());//发送短信的手机号码
			try {
				String resString = smsService.sendYunHeadFeeIdentify(configUser.getConfValue(), propertySymbol, amount, userName);
				result.put("statusCode", "200");
			}catch(PTMessageException e) {
				result.put("statusCode", "303");
				log.info("砍头息划转发送短信验证码失败的原因：" + e.getMessage());
			}catch(Exception e){
				result.put("statusCode", "304");
				log.info("砍头息划转发送短信验证码失败的原因：{}",e.getMessage());
			}
		}
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);
		return result;
	}


	// ================================ 2017-9-29 财务需求 end ================================
	
	
	
	// ================================ 2017-4-27 宝付绑卡(为了充值存管测试资金) start ================================
	/**
	 * 进入绑卡页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/baofooBindCard")
	public String baofooBindCard(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		return "/financial/bindCard/baofoo_bind_card";
	}
	
	
	/**
	 * 财务充值宝付预下单
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/bindCardPre")
	public Map<String, Object> bindCardPre(HttpServletRequest request, HttpServletResponse response, String bankCardNo,String mobile) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			String tranId = mSysTopUp2BaoFooService.preBindCard(bankCardNo,mobile);
			result.put("statusCode", "200");
			result.put("transId", tranId);
        } catch (Exception e) {
            result.put("statusCode", "300");
            result.put("message", e.getMessage());
        }
		return result;
	}    
	
	
	@RequestMapping("/financial/bindCardConfirm")
	public String bindCardConfirm(HttpServletRequest request, HttpServletResponse response, String bankCardNo, String mobile, String transId, Map<String, Object> model) {
		model.put("bankCardNo", bankCardNo);
		model.put("mobile", mobile);
		model.put("transId", transId);
	    return "/financial/bindCard/baofoo_bind_card_confirm";
	}
	
	
	@ResponseBody
	@RequestMapping("/financial/bindCardConfirmSub")
	public Map<String, Object> bindCardConfirmSub(HttpServletRequest request, HttpServletResponse response,String transId,String smsCode,String bankCardNo,String mobile,String idCard,String userName,String bankCode ) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			mSysTopUp2BaoFooService.bindCardConfirm(transId, smsCode, bankCardNo, mobile, idCard, userName, bankCode);
			result.put("statusCode", "200");

        } catch (Exception e) {
            result.put("statusCode", "300");
            result.put("message", e.getMessage());
        }
        return result;
	}
	
	
	// ================================ 2017-4-27 宝付绑卡(为了充值存管测试资金)  end ================================


	// ================================ 2017-11-02 财务需求 start ================================

	/**
	 * 资产方线下还款对账
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/partnerOfflineRepayment")
	public String partnerOfflineRepayment(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String partnerCode = request.getParameter("partnerCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Integer pageNumInt = Constants.MANAGE_DEFAULT_PAGENUM;
		Integer numPerPageInt = Constants.MANAGE_DEFAULT_NUMPERPAGE;
		if(StringUtil.isNotBlank(pageNum)) {
			pageNumInt = Integer.parseInt(pageNum);
		}
		if(StringUtil.isNotBlank(numPerPage)) {
			numPerPageInt = Integer.parseInt(numPerPage);
		}
		if(StringUtil.isNotBlank(partnerCode)) {
			partnerCode = partnerCode;
		}else {
			partnerCode = "-1";
		}
		//默认查询当天的数据
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime = StringUtil.isBlank(startTime) == true ? yestoday : startTime;
		endTime = StringUtil.isBlank(endTime) == true ? yestoday : endTime;

		model.put("numPerPage", numPerPageInt);
		model.put("pageNum", pageNum);
		model.put("partnerCode", partnerCode);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		try {
			Map<String, Object> result = mAccountService.
					queryPartnerOfflineRepaymentInfo(partnerCode, startTime, endTime, pageNumInt, numPerPageInt);
			List<PartnerOfflineRepaymentVO> list = (List<PartnerOfflineRepaymentVO>) result.get("list");
			Integer count = (Integer) result.get("count");
			model.put("list", list);
			model.put("totalRows", count);
			model.put("resCode", "000000");
		} catch (Exception e) {
			model.put("resCode", "999999");
			model.put("resMsg", e.getMessage());
		}
		return "financial/partnerOfflineRepayment_index";
	}

	/**
	 * 资产方线下还款对账 导出excel
	 */
	@RequestMapping("/financial/exportXls")
	public void exportXls(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String partnerCode = request.getParameter("partnerCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String totalRows = request.getParameter("totalRows");
		Integer pageNumInt = Constants.MANAGE_DEFAULT_PAGENUM;
		Integer numPerPageInt = Integer.MAX_VALUE;
		if(StringUtil.isNotBlank(pageNum)) {
			pageNumInt = Integer.parseInt(pageNum);
		}
		if(StringUtil.isNotBlank(numPerPage)) {
			numPerPageInt = Integer.parseInt(totalRows);
		}
		if(StringUtil.isNotBlank(partnerCode)) {
			partnerCode = partnerCode;
		}else {
			partnerCode = "-1";
		}
		model.put("numPerPage", numPerPageInt);
		model.put("pageNum", pageNum);
		model.put("partnerCode", partnerCode);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
		//设置标题
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("还款订单号");
		titles.add("支付订单号");
		titles.add("资产方");
		titles.add("申请时间");
		titles.add("还款总额");
		titles.add("返回状态");
		titleMap.put("title", titles);
		list.add(titleMap);

		Map<String, Object> result = mAccountService.
				queryPartnerOfflineRepaymentInfo(partnerCode, startTime, endTime, pageNumInt, numPerPageInt);
		List<PartnerOfflineRepaymentVO> resultList = (List<PartnerOfflineRepaymentVO>) result.get("list");

		ArrayList<HashMap<String, Object>> datas = BeanUtils.classToArrayList(resultList);
		//设置导出excel内容
		if (datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String, Object> data : datas) {
				Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("partnerOrderNo"));
				obj.add(data.get("payOrderNo"));
				obj.add((data.get("partnerCode") != null && "ZAN".equals(data.get("partnerCode"))) ? "赞分期" : "赞时贷");
				obj.add(data.get("createTime"));
				obj.add(data.get("doneTotal"));
				obj.add(data.get("status") == null ? "" : getStatusName(data.get("status").toString()));
				datasMap.put("repayment" + (++i), obj);
				list.add(datasMap);
			}
		}

		try {
			ExportUtil.exportExcel(response, request, "资产方线下还款对账", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private String getStatusName(String status) {
		String str = "";
		if(StringUtil.isNotBlank(status)) {
			if(Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_INIT.equals(status)) {
				str = "初始状态";
			}else if(Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAYING.equals(status)) {
				str = "还款中";
			}else if(Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAIED.equals(status)) {
				str = "还款成功";
			}else if(Constants.LN_FINANCE_REPAY_SCHEDULE_STATUS_REPAY_FAIL.equals(status)) {
				str = "还款失败";
			}
		}
		return str;
	}

	// ================================ 2017-11-02 财务需求 end ================================


	// ================================ 恒丰存管系统查询功能验证  S =============
	
	/**
	 * 资金变动明细查询
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/financial/getFundListQuery")
	public String getFundList(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		try {
			String orderNo = request.getParameter("order_no");
			String platcust = request.getParameter("platcust");
			String start_date = request.getParameter("start_date");
			String end_date = request.getParameter("end_date");
			String transName = request.getParameter("trans_name");
			String pagesize = request.getParameter("pagesize");
			String pagenum = request.getParameter("pagenum");
			B2GReqMsg_HFBank_GetFundList req = new B2GReqMsg_HFBank_GetFundList();
			req.setOrder_no(orderNo);
			req.setPlatcust(platcust);
			req.setStart_date(DateUtil.getDateBegin(DateUtil.parseDate(start_date)));
			req.setEnd_date(DateUtil.getDateEnd(DateUtil.parseDate(end_date)));
			req.setTrans_name(transName);
			req.setPagesize(pagesize);
			req.setPagenum(pagenum);			
			B2GResMsg_HFBank_GetFundList res = hfbankTransportService.getFundList(req);
			List<GetFundListInfoData> fundList = new ArrayList<>();
			if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				fundList = res.getData().getFundList();
				
			}else {
				
			}
			model.put("funds", fundList);
			model.put("totalRows", fundList.size());
			model.put("pagesize", pagesize);
			model.put("pagenum", pagenum);
			model.put("transName", transName);
			model.put("orderNo", orderNo);
			model.put("platcust", platcust);
			model.put("pagesize", pagesize);
			model.put("startTime", start_date);
			model.put("endTime", end_date);
        } catch (Exception e) {
           log.error(e.getMessage(), e);
        }
		return "/financial/hfBank/hf_fund_list_query";
	}  
	
	/**
	 * 进入查询页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/hfBankInfoQuery")
	public String hfBankInfoQuery(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		log.info("进入恒丰系统查询功能页面");
		return "/financial/hfBank/hf_bank_info_query";
	}
	/**
     * 进入恒丰系统实时余额查询页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/hfBankBalanceQuery")
	public String hfBankBalanceQuery(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		JSONObject jsonStruct = new JSONObject();
		try {
			
			String account_left_amount_info = "01"; //平台 
			String acct_type = "1";
			B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
			req.setAccount(account_left_amount_info);
			req.setAcct_type(acct_type);
			//req.setFund_type(fund_type);
			B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
			if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				jsonStruct = JSONObject.fromObject(res.getData());
				model.put("statusCode", 		"200");
				model.put("balance_01",			jsonStruct.get("balance") == null ? "0" : jsonStruct.get("balance"));
				model.put("freeze_balance_01",	jsonStruct.get("freeze_balance")==null?"0":jsonStruct.get("freeze_balance"));
			} else {
				model.put("statusCode_01", 	"300");
				model.put("message_01", 	res.getResMsg());
			} 
			log.info("进入恒丰系统实时余额查询页面-->自有子账户可用余额"+model.get("balance_01")+"冻结余额"+model.get("freeze_balance_01")); 
		} catch (Exception e) {
			log.info("自有子账户实时余额查询异常:{}"+e.getMessage());
			model.put("statusCode_01", "300");
			model.put("message_01", e.getMessage());
        }
		try {
			String account_left_amount_info = "01"; //平台 
			String acct_type = "5";
			B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
			req.setAccount(account_left_amount_info);
			req.setAcct_type(acct_type);
			//req.setFund_type(fund_type);
			B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
			if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				jsonStruct = JSONObject.fromObject(res.getData());
				model.put("statusCode", 	"200");
				model.put("balance_05",		jsonStruct.get("balance") == null ? "0" : jsonStruct.get("balance"));
				model.put("freeze_balance_05",	jsonStruct.get("freeze_balance")==null?"0":jsonStruct.get("freeze_balance"));
			} else {
				model.put("statusCode_05", 	"300");
				model.put("message_05", 	res.getResMsg());
			}
			log.info("进入恒丰系统实时余额查询页面-->抵用金账户可用余额"+model.get("balance_05")+"冻结余额"+model.get("freeze_balance_05")); 
		} catch (Exception e) {
			log.info("抵用金账户实时余额查询异常:{}"+e.getMessage());
			model.put("statusCode_05", 	"300");
			model.put("message_05", 	e.getMessage());
        }
		try {
			String account_left_amount_info = "01"; //平台 
			String acct_type = "3";
			B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
			req.setAccount(account_left_amount_info);
			req.setAcct_type(acct_type);
			//req.setFund_type(fund_type);
			B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
			if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				jsonStruct = JSONObject.fromObject(res.getData());
				model.put("statusCode_03", 	"200");
				model.put("balance_03",		jsonStruct.get("balance") == null ? "0" : jsonStruct.get("balance"));
				model.put("freeze_balance_03",	jsonStruct.get("freeze_balance")==null?"0":jsonStruct.get("freeze_balance"));
			} else {
				model.put("statusCode_03", 	"300");
				model.put("message_03", 		res.getResMsg());
			}
			log.info("进入恒丰系统实时余额查询页面-->手续费现金账户可用余额"+model.get("balance_03")+"冻结余额"+model.get("freeze_balance_03"));
		} catch (Exception e) {
			log.info("手续费现金实时余额查询异常:{}"+e.getMessage());
			model.put("statusCode_03", "300");
			model.put("message_03", e.getMessage());
        }
		try {
			String account_left_amount_info = "01"; //平台 
			String acct_type = "34";
			B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
			req.setAccount(account_left_amount_info);
			req.setAcct_type(acct_type);
			//req.setFund_type(fund_type);
			B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
			if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				jsonStruct = JSONObject.fromObject(res.getData());
				model.put("statusCode_34", 		"200");
				model.put("balance_transit_34",	jsonStruct.get("balance") == null ? "0" : jsonStruct.get("balance"));
				model.put("freeze_balance_34",	jsonStruct.get("freeze_balance")==null?"0":jsonStruct.get("freeze_balance"));
			} else {
				model.put("statusCode_34", 	"300");
				model.put("message_34", 		res.getResMsg());
			}
			log.info("进入恒丰系统实时余额查询页面-->手续费在途账户可用余额"+model.get("balance_transit_34")+"冻结余额"+model.get("freeze_balance_34"));
		} catch (Exception e) {
			log.info("手续费在途实时余额查询异常:{}"+e.getMessage());
			model.put("statusCode_34", "300");
			model.put("message_34", e.getMessage());
        }
		try {
			
			String account_left_amount_info = "01"; //平台 
			String acct_type = "10";
			B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
			req.setAccount(account_left_amount_info);
			req.setAcct_type(acct_type);
			//req.setFund_type(fund_type);
			B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
			if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				jsonStruct = JSONObject.fromObject(res.getData());
				model.put("statusCode_10", 		"200");
				model.put("balance_10",	jsonStruct.get("balance") == null ? "0" : jsonStruct.get("balance"));
				model.put("freeze_balance_10",	jsonStruct.get("freeze_balance")==null?"0":jsonStruct.get("freeze_balance"));
			} else {
				model.put("statusCode_10", 	"300");
				model.put("message_10", 	res.getResMsg());
			}
			log.info("进入恒丰系统实时余额查询页面-->垫付金现金账户可用余额"+model.get("balance_10")+"冻结余额"+model.get("freeze_balance_10"));  
		} catch (Exception e) {
			log.info("垫付金现金实时余额查询异常:{}"+e.getMessage());
			model.put("statusCode_10", "300");
			model.put("message_10", e.getMessage());
        }
		try {
			
			String account_left_amount_info = "01"; //平台 
			String acct_type = "11";
			B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
			req.setAccount(account_left_amount_info);
			req.setAcct_type(acct_type);
			//req.setFund_type(fund_type);
			B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
			if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				jsonStruct = JSONObject.fromObject(res.getData());
				model.put("statusCode_11", 		"200");
				model.put("balance_transit_11",	jsonStruct.get("balance") == null ? "0" : jsonStruct.get("balance"));
				model.put("freeze_balance_11",	jsonStruct.get("freeze_balance")==null?"0":jsonStruct.get("freeze_balance"));
			} else {
				model.put("statusCode_11", 	"300");
				model.put("message_11", 	res.getResMsg());
			}
			log.info("进入恒丰系统实时余额查询页面-->垫付金在途账户可用余额"+model.get("balance_transit_11")+"冻结余额"+model.get("freeze_balance_11"));  
		} catch (Exception e) {
			log.info("垫付金在途实时余额查询异常:{}"+e.getMessage());
			model.put("statusCode_11", "300");
			model.put("message_11", e.getMessage());
        }
		return "/financial/hfBank/hf_bank_balance_query";
	}	 
	/**
	 * 查询结果
	 * @param request
	 * @param response
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/financial/hfBankInfo")
	public Map<String, Object> hfBankInfo(HttpServletRequest request, HttpServletResponse response,String type) {
		Map<String, Object> result = new HashMap<String, Object>();
		try {
			log.info("进行恒丰系统查询,类型:"+type);
			if ("ORDER_STATUS".equals(type)) {
				//订单状态查询
				String query_order_no = request.getParameter("order_no");
				String orderNo = Util.generateOrderNo4BaoFoo(8);
				B2GReqMsg_HFBank_QueryOrder req = new B2GReqMsg_HFBank_QueryOrder();
				req.setOrder_no(orderNo);
				req.setPartner_trans_date(new Date());
				req.setPartner_trans_time(new Date());
				req.setQuery_order_no(query_order_no);
				B2GResMsg_HFBank_QueryOrder res = hfbankTransportService.queryOrder(req);
				if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					result.put("statusCode", "200");
					result.put("status", res.getData().getStatus());
				}else {
					result.put("statusCode", "300");
			        result.put("message", res.getResMsg());
				}
			}else if ("ACCOUNT_INFO".equals(type)) {
				//账户资金余额
				String account_info_account = request.getParameter("account_info_account");
				B2GReqMsg_HFBank_QueryAccountInfo req = new B2GReqMsg_HFBank_QueryAccountInfo();
				req.setAccount(account_info_account);
				B2GResMsg_HFBank_QueryAccountInfo res = hfbankTransportService.queryAccountInfo(req);
				if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					result.put("statusCode", "200");
					result.put("account_amount", res.getData().getBalance());
				}else {
					result.put("statusCode", "300");
			        result.put("message", res.getResMsg());
				}
			}else if ("ACCOUNT_LEFT_AMOUNT_INFO".equals(type)) {
				//账户余额明细查询
				String account_left_amount_info = request.getParameter("account_left_amount_info");
				String acct_type = request.getParameter("acct_type");
				String fund_type = request.getParameter("fund_type");
				B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req = new B2GReqMsg_HFBank_QueryAccountLeftAmountInfo();
				req.setAccount(account_left_amount_info);
				req.setAcct_type(acct_type);
				req.setFund_type(fund_type);
				B2GResMsg_HFBank_QueryAccountLeftAmountInfo res = hfbankTransportService.queryAccountLeftAmountInfo(req);
				if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					result.put("statusCode", "200");
					result.put("account_left_amount_data", res.getData());
				}else {
					result.put("statusCode", "300");
			        result.put("message", res.getResMsg());
				}
			}else if ("QUERY_PRODUCT_BALANCE".equals(type)) {
				//标的账户余额查询
				String prod_id = request.getParameter("prod_id");
				String product_balance_type = request.getParameter("product_balance_type");
				B2GReqMsg_HFBank_QueryProductBalance req = new B2GReqMsg_HFBank_QueryProductBalance();
				req.setProd_id(prod_id);
				req.setType(product_balance_type);
				B2GResMsg_HFBank_QueryProductBalance res = hfbankTransportService.queryProductBalance(req);
				if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					result.put("statusCode", "200");
					result.put("product_balance_data", res.getData());
				}else {
					result.put("statusCode", "300");
			        result.put("message", res.getResMsg());
				}
			}else if ("WITHHOLD".equals(type)) {
				//代扣
				String command_pass = request.getParameter("command_pass");
				
				String withhold_id_card = request.getParameter("withhold_id_card");
				String withhold_user_name = request.getParameter("withhold_user_name");
				String withhold_mobile = request.getParameter("withhold_mobile");
				String withhold_bank_card = request.getParameter("withhold_bank_card");
				String withhold_bank_code = request.getParameter("withhold_bank_code");
				String withhold_trans_balance = request.getParameter("withhold_trans_balance");
				
				result.put("withhold_id_card", withhold_id_card);
				result.put("withhold_user_name", withhold_user_name);
				result.put("withhold_mobile", withhold_mobile);
				result.put("withhold_bank_card", withhold_bank_card);
				result.put("withhold_bank_code", withhold_bank_code);
				result.put("withhold_trans_balance", withhold_trans_balance);
				
				BsPaymentChannelExample example = new BsPaymentChannelExample();
				example.createCriteria().andIsMainEqualTo(1); //1表示主商户 
				List<BsPaymentChannel> paymentChannel = bsPaymentChannelMapper.selectByExample(example);
				
				if( CollectionUtils.isEmpty( paymentChannel )) {
					result.put("statusCode", "300");
			        result.put("message", "主商户信息不存在!");
				}
				
				String merchantNo = paymentChannel.get(0).getMerchantNo();
				Integer isMain = paymentChannel.get(0).getIsMain();
				if ("caiwu_dk".equals(command_pass)) {
					String orderNo = Util.generateOrderNo4BaoFoo(8);
					B2GReqMsg_BaoFooCutpayment_Cutpayment req = new B2GReqMsg_BaoFooCutpayment_Cutpayment();
					req.setTrans_serial_no(Util.generateUserOrderNo4Pay19(24992)); //代扣用户的id
					req.setTrans_id(orderNo);
					//宝付代扣交易金额入参需要转成分，赞分期就是以分为单位，赞分期无需改动
					req.setTxnAmt(MoneyUtil.multiply(withhold_trans_balance, "100").setScale(2, BigDecimal.ROUND_HALF_UP).toString());
					req.setAcc_no(withhold_bank_card);
					req.setId_card(withhold_id_card);
					req.setId_holder(withhold_user_name);
					req.setMobile(withhold_mobile);
					req.setPay_code(withhold_bank_code);
					req.setMerchantNo(merchantNo);
					req.setIsMain(isMain);
					req.setAdditional_info("财务人工代扣到宝付商户号("+merchantNo+")");

					B2GResMsg_BaoFooCutpayment_Cutpayment res = baoFooTransportService.withholding(req);
					if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
						result.put("trans_id", res.getTrans_id());
						result.put("trans_no", res.getTrans_no());
						result.put("statusCode", "200");
					}else {
						result.put("statusCode", "300");
				        result.put("message", res.getResMsg());
					}
				}else {
					
				}
			} 
			/*else if ("PLATFORMTRANSFER".equals(type)) {
				//平台转账个人
				String orderNo = request.getParameter("order_no");
				String amount = request.getParameter("amount");
				//1.奖励金发放:用户参与运营活动应获得奖励金XX元---活动奖励 2.奖励金发放:用户推荐好友投资行为应获得奖励金XX元---推荐奖励
				//3.手续费返还:用户投资手续费返还XX元  4.现金红包:用户参与运营活动应获得现金红包XX元
				String causeType = request.getParameter("cause_type");
				String platcust = request.getParameter("platcust");
				B2GReqMsg_HFBank_PlatformTransfer req = new B2GReqMsg_HFBank_PlatformTransfer();
				req.setOrder_no(orderNo);
				req.setPlat_account("01");	*//**平台账户(01 为平台账户，从平台自有资金转账)*//*
				req.setAmount(amount);
				req.setCause_type(causeType);
				req.setPlatcust(platcust);
				req.setRemark("平台转账个人测试");
				req.setPartner_trans_date(new Date());
		    	req.setPartner_trans_time(new Date());
		    	B2GResMsg_HFBank_PlatformTransfer res = hfbankTransportService.platformTransfer(req);
				if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					result.put("statusCode", "200");
				}else {
					result.put("statusCode", "300");
			        result.put("message", res.getResMsg());
				}
			} */else if ("FUNDDATA".equals(type)) {
				//账户余额明细查询
				String orderNo = request.getParameter("order_no");
				String paycheckDate = request.getParameter("paycheck_date");
				String precheckFlag = request.getParameter("precheck_flag");
				String beginTime = request.getParameter("begin_time");
				String endTime = request.getParameter("end_time");
				B2GReqMsg_HFBank_FundDataCheck req = new B2GReqMsg_HFBank_FundDataCheck();
				req.setOrder_no(orderNo);
				req.setPaycheck_date(DateUtil.parseDate(paycheckDate));
				req.setPrecheck_flag(precheckFlag);
				req.setBegin_time(DateUtil.parseDateTime(beginTime));
				req.setEnd_time(DateUtil.parseDateTime(endTime));
				req.setPartner_trans_date(new Date());
		    	req.setPartner_trans_time(new Date());
				B2GResMsg_HFBank_FundDataCheck res = hfbankTransportService.fundDataCheck(req);
				if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					result.put("statusCode", "200");
				}else {
					result.put("statusCode", "300");
			        result.put("message", res.getResMsg());
				}
			} else if("BALANCEINFO".equals(type)) {
				//对账文件账户余额数据
				String orderNo = request.getParameter("order_no");
				String paycheckDate = request.getParameter("paycheck_date");
				String precheckFlag = request.getParameter("precheck_flag");
				String beginTime = request.getParameter("begin_time");
				String endTime = request.getParameter("end_time");
				B2GReqMsg_HFBank_BalanceInfo req = new B2GReqMsg_HFBank_BalanceInfo();
				req.setOrder_no(orderNo);
				req.setPaycheck_date(DateUtil.parseDate(paycheckDate));
				req.setPrecheck_flag(precheckFlag);
				req.setBegin_time(DateUtil.parseDateTime(beginTime));
				req.setEnd_time(DateUtil.parseDateTime(endTime));
				req.setPartner_trans_date(new Date());
		    	req.setPartner_trans_time(new Date());
				B2GResMsg_HFBank_BalanceInfo res = hfbankTransportService.balanceInfo(req);
				if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
					result.put("statusCode", "200");
					result.put("recode", res.getRecode());
				}else {
					result.put("statusCode", "300");
			        result.put("message", res.getResMsg());
				}
			} else if("SIGNREPEAT".equals(type)) {
				//
				String signSealId = request.getParameter("sign_seal_id");
				log.info("签章重发开始");
				try {
					signRepeatSendService.signBorrowServicesZan(Integer.valueOf(signSealId));
					result.put("statusCode", "200");
					log.info("签章重发结束");
				} catch (Exception e) {
					log.info("签章重发异常");
					result.put("statusCode", "300");
			        result.put("message", "重发异常");
				}
				
				
			}
			
			
        } catch (Exception e) {
            result.put("statusCode", "300");
            result.put("message", e.getMessage());
        }
		return result;
	}  
	// ================================ 恒丰存管系统查询功能验证 E =============

	// ============ 2017-07-03 财务需求 start =================================================

	/**
	 * 当日系统理财未回款查询
	 * @param dateTime
	 * @param model
	 * @return
	 */
	@RequestMapping("/financialAccount/sysNotReceivable")
	public String getSysNotReceivableList(String dateTime, Map<String, Object> model) {
		//默认查询当天时间
		if(StringUtil.isBlank(dateTime)) {
			dateTime = DateUtil.formatYYYYMMDD(new Date());
		}
		List<SysNotReceivableVO> resultList = financialAccountService.querySysNotReceivableInfo(dateTime);
		int count = 0;
		if(resultList!= null && resultList.size() > 0) {
			count = resultList.size();
		}else {
			count = 0;
		}
		model.put("list", resultList);
		model.put("count", count);
		model.put("dateTime", dateTime);
		return "/financial/sys_not_receivable";
	}

	// ============ 2017-07-03 财务需求 end ===================================================
	
	// ============ 信息服务费结算查询-财务需求 start =================================================
	
	/**
	 * 钱报信息服务费查询
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/qbDepServiceFeeIndex")
	public String queryQbDepServiceFee(ReqMsg_MAccount_QueryQbDepServiceFeeList req, HttpServletRequest request,
										  HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);
		ResMsg_MAccount_QueryQbDepServiceFeeList res = (ResMsg_MAccount_QueryQbDepServiceFeeList) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("transferList", res.getValueList());
		model.put("sumBuyAmount", res.getSumBuyAmount());
		model.put("sumQbServiceFee", res.getSumQbServiceFee());
		model.put("req", req);
		return "/financial/qbDepServiceFee_index";
	}

	/**
	 * 杭商信息服务费查询
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/hsDepServiceFeeIndex")
	public String queryHsDepServiceFee(ReqMsg_MAccount_QueryHsDepServiceFeeList req, HttpServletRequest request,
										  HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);
		ResMsg_MAccount_QueryHsDepServiceFeeList res = (ResMsg_MAccount_QueryHsDepServiceFeeList) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("transferList", res.getValueList());
		model.put("sumBuyAmount", res.getSumBuyAmount());
		model.put("sumHsServiceFee", res.getSumHsServiceFee());
		model.put("req", req);
		return "/financial/hsDepServiceFee_index";
	}
	
	/**
	 * 品听信息服务费查询
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/ptDepServiceFeeIndex")
	public String queryPtDepServiceFee(ReqMsg_MAccount_QueryPtDepServiceFeeList req, HttpServletRequest request,
										  HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		int agentTotal = agentService.findAgentsTotalRows();
		model.put("agentTotal", agentTotal);
		ResMsg_MAccount_QueryPtDepServiceFeeList res = (ResMsg_MAccount_QueryPtDepServiceFeeList) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("transferList", res.getValueList());
		model.put("sumBuyAmount", res.getSumBuyAmount());
		model.put("sumPtServiceFee", res.getSumPtServiceFee());
		model.put("req", req);
		return "/financial/ptDepServiceFee_index";
	}
	
	/**
	 * 钱报信息服务费导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/qbDepServiceFee/exportXls")
	public void qbDepServiceFeeExport(HttpServletRequest request,ReqMsg_MAccount_QueryQbDepServiceFeeList req,
			HttpServletResponse response, Map<String, Object> model) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
		req.setNumPerPage(Integer.MAX_VALUE);
		ResMsg_MAccount_QueryQbDepServiceFeeList res = (ResMsg_MAccount_QueryQbDepServiceFeeList) hessianService.handleMsg(req);
		
		List<HashMap<String,Object>> datas = res.getValueList();
		//设置标题
		list.add(titles());
		//设置导出excel内容
		if(!CollectionUtils.isEmpty(datas)) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(DateUtil.format((Date)data.get("openTime")));
				obj.add(StringUtil.substring(data.get("mobile").toString(),0,3)+"****"+
						StringUtil.substring(data.get("mobile").toString(),data.get("mobile").toString().length()-4,data.get("mobile").toString().length()));
				obj.add(data.get("userName"));
				obj.add(data.get("orderNo"));
				obj.add(data.get("productName"));
				obj.add(data.get("productTerm"));
				obj.add(data.get("productRate"));
				obj.add(data.get("openBalance"));
				obj.add(data.get("bankName"));
				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("withdrawTime")));
				switch ((Integer)data.get("accountStatus")) {
                case 1:
                    obj.add("开户");
                    break;
                case 2:
                    obj.add("投资中");
                    break;
                case 3:
                    obj.add("转让中");
                    break;
                case 4:
                    obj.add("已转让");
                    break;
                case 5:
                    obj.add("已结算");
                    break;
                case 6:
                    obj.add("销户");
                    break;    
                case 7:
                    obj.add("结算中");
                    break;   
                default:
                    break;
				}
				obj.add(data.get("agentName"));
				obj.add(data.get("revenueRate")+"%");
				obj.add(data.get("depServiceFee"));
				datasMap.put("qbDepServiceFee"+(++i), obj);
				list.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "钱报-云贷存管服务费查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 杭商信息服务费导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/hsDepServiceFee/exportXls")
	public void hsDepServiceFeeExport(HttpServletRequest request,ReqMsg_MAccount_QueryHsDepServiceFeeList req,
			HttpServletResponse response, Map<String, Object> model) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
		req.setNumPerPage(Integer.MAX_VALUE);
		ResMsg_MAccount_QueryHsDepServiceFeeList res = (ResMsg_MAccount_QueryHsDepServiceFeeList) hessianService.handleMsg(req);
		
		List<HashMap<String,Object>> datas = res.getValueList();
		//设置标题
		list.add(titles());
		//设置导出excel内容
		if(!CollectionUtils.isEmpty(datas)) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(DateUtil.format((Date)data.get("openTime")));
				obj.add(StringUtil.substring(data.get("mobile").toString(),0,3)+"****"+
						StringUtil.substring(data.get("mobile").toString(),data.get("mobile").toString().length()-4,data.get("mobile").toString().length()));
				obj.add(data.get("userName"));
				obj.add(data.get("orderNo"));
				obj.add(data.get("productName"));
				obj.add(data.get("productTerm"));
				obj.add(data.get("productRate"));
				obj.add(data.get("openBalance"));
				obj.add(data.get("bankName"));
				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("withdrawTime")));
				switch ((Integer)data.get("accountStatus")) {
				case 1:
                    obj.add("开户");
                    break;
                case 2:
                    obj.add("投资中");
                    break;
                case 3:
                    obj.add("转让中");
                    break;
                case 4:
                    obj.add("已转让");
                    break;
                case 5:
                    obj.add("已结算");
                    break;
                case 6:
                    obj.add("销户");
                    break;    
                case 7:
                    obj.add("结算中");
                    break;   
                default:
                    break;
				}
				obj.add(data.get("agentName")==null?"无":data.get("agentName"));
				obj.add(data.get("revenueRate")+"%");
				obj.add(data.get("depServiceFee"));
				datasMap.put("hsDepServiceFee"+(++i), obj);
				list.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "杭商-云贷存管服务费查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 品听信息服务费导出
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/ptDepServiceFee/exportXls")
	public void ptDepServiceFeeExport(HttpServletRequest request,ReqMsg_MAccount_QueryPtDepServiceFeeList req,
			HttpServletResponse response, Map<String, Object> model) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
		req.setNumPerPage(Integer.MAX_VALUE);
		ResMsg_MAccount_QueryPtDepServiceFeeList res = (ResMsg_MAccount_QueryPtDepServiceFeeList) hessianService.handleMsg(req);
		
		List<HashMap<String,Object>> datas = res.getValueList();
		//设置标题
		list.add(titles());
		//设置导出excel内容
		if(!CollectionUtils.isEmpty(datas)) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(DateUtil.format((Date)data.get("openTime")));
				obj.add(StringUtil.substring(data.get("mobile").toString(),0,3)+"****"+
						StringUtil.substring(data.get("mobile").toString(),data.get("mobile").toString().length()-4,data.get("mobile").toString().length()));
				obj.add(data.get("userName"));
				obj.add(data.get("orderNo"));
				obj.add(data.get("productName"));
				obj.add(data.get("productTerm"));
				obj.add(data.get("productRate"));
				obj.add(data.get("openBalance"));
				obj.add(DateUtil.formatYYYYMMDD((Date)data.get("withdrawTime")));
				switch ((Integer)data.get("accountStatus")) {
				case 1:
                    obj.add("开户");
                    break;
                case 2:
                    obj.add("投资中");
                    break;
                case 3:
                    obj.add("转让中");
                    break;
                case 4:
                    obj.add("已转让");
                    break;
                case 5:
                    obj.add("已结算");
                    break;
                case 6:
                    obj.add("销户");
                    break;    
                case 7:
                    obj.add("结算中");
                    break;   
                default:
                    break;
				}
				obj.add(data.get("agentName")==null?"无":data.get("agentName"));
				obj.add(data.get("revenueRate")+"%");
				obj.add(data.get("depServiceFee"));
				datasMap.put("ptDepServiceFee"+(++i), obj);
				list.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "品听-云贷存管服务费查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Object>> titles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("购买日期");
		titles.add("手机号");
		titles.add("姓名");
		titles.add("订单号");
		titles.add("产品名称");
		titles.add("期限(天)");
		titles.add("利率");
		titles.add("购买金额");
		titles.add("到期提现日");
		titles.add("账户状态");
		titles.add("渠道来源");
		titles.add("结算比例");
		titles.add("信息服务费");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	/**
	 * 每日日终账务查询
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/sysBalanceDailyIndex")
	public String sysBalanceDailyIndex(ReqMsg_MAccount_QuerySysBalanceDailyList req, HttpServletRequest request,
										  HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		ResMsg_MAccount_QuerySysBalanceDailyList res = (ResMsg_MAccount_QuerySysBalanceDailyList) hessianService.handleMsg(req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("balanceDailyList", res.getValueList());
		model.put("req", req);
		return "/financial/sysBalanceDaily_index";
	}
	
	/**
	 * 每日日终账务文件下载
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	@RequestMapping("/financial/downloadSysBalanceDaily")
	public void downloadSysBalanceDaily( HttpServletRequest request, HttpServletResponse response) throws IOException {    
		BsSysBalanceDailyFileExample sysBalanceDailyFileExample = new BsSysBalanceDailyFileExample();
		sysBalanceDailyFileExample.createCriteria().andIdEqualTo(Integer.parseInt(request.getParameter("fid")));
		List<BsSysBalanceDailyFile> list = bsSysBalanceDailyFileMapepr.selectByExample(sysBalanceDailyFileExample);
        if (CollectionUtils.isEmpty(list)) {
        	log.info("downloadSysBalanceDaily method downloadSysBalanceDaily is null");
        } else {     
        	BsSysBalanceDailyFile sysBalanceDailyFile = list.get(0);
        	String fileName = sysBalanceDailyFile.getFileName();
    		fileName = URLEncoder.encode(fileName, "UTF-8");
    		response.setHeader("Content-Disposition", "attachment;filename="+fileName);
    		InputStream is = new FileInputStream(new File(sysBalanceDailyFile.getFileAddress()));
    		int read =0;
    		byte[] bytes = new byte[2048];
    		OutputStream os = response.getOutputStream();
    		while((read = is.read(bytes)) != -1){
    			os.write(bytes, 0, read);
    		}
    		os.flush();
    		os.close();
    		is.close();   
        }
    }    
	// ============ 信息服务费结算查询-财务需求 end ===================================================

	//============================== 宝付出入金对账汇总明细  =====================================================
	@RequestMapping("/financial/expDepBaoFooBalanceFinance")
	public void expDepBaoFooBalanceDetail(HttpServletRequest request, HttpServletResponse response) {
		
		String startTime= request.getParameter("startTime");
		String endTime 	= request.getParameter("endTime");
		String pageNum 	= request.getParameter("pageNum");
		String numPerPage=	request.getParameter("numPerPage");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;
		//获得用户购买记录
		List<DepBaoFooBalanceStatisticsVO> resultList = financeService.depBaoFooBalanceStatistics(startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		
		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("对账日期");
		titles.add("宝付出_提现(余额)");
		titles.add("宝付出_提现(余额)成功笔数");
		titles.add("宝付出_提现(奖励金)");
		titles.add("宝付出_提现(奖励金)成功笔数");
		titles.add("宝付出_代付到归集户");
		titles.add("宝付出_代付到归集户成功笔数");
		titles.add("存管_归集户代扣到存管户");
		titles.add("存管_归集户代扣到存管户笔数");
		titles.add("宝付出_赞分期营收");
		titles.add("宝付出_云贷营收");
		titles.add("宝付出_云贷重复还款");
		titles.add("宝付出_赞时贷营收");
		titles.add("宝付出_赞时贷重复还款");
		titles.add("宝付出_7贷营收");
		titles.add("宝付出_7贷重复还款");
		titles.add("宝付入_回款_云贷");
		titles.add("宝付入_回款_云贷成功笔数");
		titles.add("宝付入_回款_七贷");
		titles.add("宝付入_回款_七贷成功笔数");
		titles.add("宝付(主)入_赞分期还款");
		titles.add("宝付(主)入_赞分期还款成功笔数");
		titles.add("宝付(主)入_云贷还款");
		titles.add("宝付(主)入_云贷还款成功笔数");
		titles.add("宝付(主)入_赞时贷还款");
		titles.add("宝付(主)入_赞时贷还款成功笔数");
		titles.add("宝付(主)入_7贷还款");
		titles.add("宝付(主)入_7贷还款成功笔数");
		titles.add("宝付(辅)入_赞分期还款");
		titles.add("宝付(辅)入_赞分期还款成功笔数");
		titles.add("宝付(辅)入_云贷还款");
		titles.add("宝付(辅)入_云贷还款成功笔数");
		titles.add("宝付(辅)入_赞时贷还款");
		titles.add("宝付(辅)入_赞时贷还款成功笔数");
		titles.add("宝付(辅)入_7贷还款");
		titles.add("宝付(辅)入_7贷还款成功笔数");
		titles.add("宝付(辅)入_赞分期还款_钱包转账");
		titles.add("宝付(辅)入_赞分期还款成功笔数_钱包转账");
		titles.add("宝付(辅)入_云贷还款_钱包转账");
		titles.add("宝付(辅)入_云贷还款成功笔数_钱包转账");
		titles.add("宝付(辅)入_赞时贷还款_钱包转账");
		titles.add("宝付(辅)入_赞时贷还款成功笔数_钱包转账");
		titles.add("宝付入_云贷代偿");
		titles.add("宝付入_云贷代偿成功笔数");
		titles.add("宝付入_赞时贷代偿");
		titles.add("宝付入_赞时贷代偿成功笔数");
		titles.add("宝付入_7贷代偿");
		titles.add("宝付入_7贷代偿成功笔数");
		titleMap.put("title", titles);
		excelList.add(titleMap);

		if(!CollectionUtils.isEmpty(resultList)) {
			int i = 0;
			for (DepBaoFooBalanceStatisticsVO data : resultList) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				
				obj.add(data.getTransDate());
				obj.add(data.getOutUserWithdraw()==null?0d:data.getOutUserWithdraw());
				obj.add(data.getOutUserWithdrawCount());
				obj.add(data.getOutBonusWithdraw()==null?0d:data.getOutBonusWithdraw());
				obj.add(data.getOutBonusWithdrawCount());
				obj.add(data.getOutWithdrawToDepRepayCard()==null?0d:data.getOutWithdrawToDepRepayCard());
				obj.add(data.getOutWithdrawToDepRepayCardCount());
				obj.add(data.getCutRepay2Borrower()==null?0d:data.getCutRepay2Borrower());
				obj.add(data.getCutRepay2BorrowerCount());
				obj.add(data.getOutSysPartnerRevenue()==null?0d:data.getOutSysPartnerRevenue());
				obj.add(data.getOutSysYunRepayRevenue()==null?0d:data.getOutSysYunRepayRevenue());
				obj.add(data.getOutSysYunRepeat()==null?0d:data.getOutSysYunRepeat());
				obj.add(data.getOutSysZsdRepayRevenue()==null?0d:data.getOutSysZsdRepayRevenue());
				obj.add(data.getOutSysZsdRepeat()==null?0d:data.getOutSysZsdRepeat());
				obj.add(data.getOutSysSevenRepayRevenue()==null?0d:data.getOutSysSevenRepayRevenue());
				obj.add(data.getOutSysSevenRepeat()==null?0d:data.getOutSysSevenRepeat());
				obj.add(data.getInYunDaiBack()==null?0d:data.getInYunDaiBack());
				obj.add(data.getInYunDaiBackCount());
				obj.add(data.getInSevenDaiBack()==null?0d:data.getInSevenDaiBack());
				obj.add(data.getInSevenDaiBackCount());
				obj.add(data.getInDepZanBack()==null?0d:data.getInDepZanBack());
				obj.add(data.getInDepZanBackCount());
				obj.add(data.getInDepYunBack()==null?0d:data.getInDepYunBack());
				obj.add(data.getInDepYunBackCount());
				obj.add(data.getInDepZsdBack()==null?0d:data.getInDepZsdBack());
				obj.add(data.getInDepZsdBackCount());
				obj.add(data.getInDepSevenBack()==null?0d:data.getInDepSevenBack());
				obj.add(data.getInDepSevenBackCount());
				obj.add(data.getInDepZanBack2()==null?0d:data.getInDepZanBack2());
				obj.add(data.getInDepZanBackCount2());
				obj.add(data.getInDepYunBack2()==null?0d:data.getInDepYunBack2());
				obj.add(data.getInDepYunBackCount2());
				obj.add(data.getInDepZsdBack2()==null?0d:data.getInDepZsdBack2());
				obj.add(data.getInDepZsdBackCount2());
				obj.add(data.getInDepSevenBack2()==null?0d:data.getInDepSevenBack2());
				obj.add(data.getInDepSevenBackCount2());
				obj.add(data.getInDepZanBack2Transfer()==null?0d:data.getInDepZanBack2Transfer());
				obj.add(data.getInDepZanBackCount2Transfer());
				obj.add(data.getInDepYunBack2Transfer()==null?0d:data.getInDepYunBack2Transfer());
				obj.add(data.getInDepYunBackCount2Transfer());
				obj.add(data.getInDepZsdBack2Transfer()==null?0d:data.getInDepZsdBack2Transfer());
				obj.add(data.getInDepZsdBackCount2Transfer());
				obj.add(data.getInDepYunCompensate()==null?0d:data.getInDepYunCompensate());
				obj.add(data.getInDepYunCompensateCount());
				obj.add(data.getInDepZsdCompensate()==null?0d:data.getInDepZsdCompensate());
				obj.add(data.getInDepZsdCompensateCount());
				obj.add(data.getInDepSevenCompensate()==null?0d:data.getInDepSevenCompensate());
				obj.add(data.getInDepSevenCompensateCount());
				datasMap.put("loanDaily"+(++i), obj);
				excelList.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "宝付出入金对账文件", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	/**
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/queryDepBaoFooBalanceFinance")
	public String DepBaoFooBalanceFinanceStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		
		String startTime= request.getParameter("startTime");
		String endTime 	= request.getParameter("endTime");
		String pageNum 	= request.getParameter("pageNum");
		String numPerPage=	request.getParameter("numPerPage");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;
		//获得用户购买记录
		int count = financeService.depBaoFooBalanceStatisticsCount(startTime, endTime);
		if( count > 0 ) {
			List<DepBaoFooBalanceStatisticsVO> userList = financeService.depBaoFooBalanceStatistics(startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			model.put("userList", userList);
		}
		model.put("totalRows", 	count);
		model.put("pageNum", 	pageNum);
		model.put("numPerPage", numPerPage);
		model.put("startTime", 	startTime);
		model.put("endTime", 	endTime);
		
		return "/financial/dep_baofoo_balance_finance_statistics";
	}
	//============================== 融资客户结算  dep.balance.finance.excel=====================================================
	/**
	 * 融资客户结算
	 * 融资客户结算指云贷结算给币港湾的钱，需要核算到具体的客户，与支付融资客户必须对应
	 * 系统回款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/queryBalanceFinance")
	public String balanceFinanceStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		
		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String partnerCode = request.getParameter("partnerCode") != null ? request.getParameter("partnerCode").trim() : "YUN_DAI_SELF";
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		String custType = request.getParameter("custType");	//vip- VIP客户  all- 全部 others- 普通 
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}

		String partnerBusinessFlag = request.getParameter("partnerBusinessFlag");
		if(StringUtil.isBlank(partnerBusinessFlag)){
			partnerBusinessFlag = "777";
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true ? yestoday+" 00:00:00" : startTime;
		endTime=StringUtil.isBlank(endTime)==true ? yestoday+" 23:59:59" : endTime;
		int count = 0;
		//获得用户购买记录
		List<DepBalanceFinanceStatisticsVO> countList = financeService.depBalanceFinanceStatisticsCount(userName, mobile, note, custType, partnerCode, partnerBusinessFlag, startTime, endTime);
		if( countList.size() > 0 ) {
			if( countList.get(0).getTotalCount() > 0 ) {
				List<DepBalanceFinanceStatisticsVO> userList = financeService.depBalanceFinanceStatistics(userName, mobile, note, custType, partnerCode, partnerBusinessFlag, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
				model.put("userList", userList);
				//将数据返回给页面
				model.put("totalPlanPrincipal", countList.get(0).getTotalPlanPrincipal());
				model.put("totalPlanLoanInterest", countList.get(0).getTotalPlanLoanInterest());
				model.put("totalPlanInterest", countList.get(0).getTotalPlanInterest());
				model.put("totalPlanFee", countList.get(0).getTotalPlanFee());
				count = countList.get(0).getTotalCount();
			}
		}
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("custType", custType);
		model.put("note", note);
		model.put("partnerCode", partnerCode);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("partnerBusinessFlag", partnerBusinessFlag);

		return "/financial/balance_finance_statistics";
	}
	
	@RequestMapping("/financial/exportBalanceFinance")
	public void exportBalanceFinance(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String partnerCode = request.getParameter("partnerCode") != null ? request.getParameter("partnerCode").trim() : "YUN_DAI_SELF";
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		String custType = request.getParameter("custType");
		String partnerBusinessFlag = request.getParameter("partnerBusinessFlag");
		if(StringUtil.isBlank(partnerBusinessFlag)){
			partnerBusinessFlag = "777";
		}

		//需要导出的数据
		List<DepBalanceFinanceStatisticsVO> countList = financeService.depBalanceFinanceStatisticsCount(userName, mobile, note, custType, partnerCode, partnerBusinessFlag, startTime, endTime);
		int count = 0;
		List<DepBalanceFinanceStatisticsVO> userList = new ArrayList<DepBalanceFinanceStatisticsVO>();
		if( countList.size() > 0 ) {
			count = countList.get(0).getTotalCount();
			userList = financeService.depBalanceFinanceStatistics(userName, mobile, note, custType, partnerCode, partnerBusinessFlag, startTime, endTime, 1, count);
		}
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.balance.finance.excel"));
			String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
			//打开excel
			fis = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheetHead = wb.getSheetAt(1); //单据头
			sheetHead.setColumnWidth(1,9000); //设置单据编号的列宽
			HSSFSheet sheetBody = wb.getSheetAt(2); //单据体
			sheetBody.setColumnWidth(1, 9000); //设置单据编号的列宽
			sheetBody.setColumnWidth(2, 9000); //设置客户代码的列宽

			HSSFCellStyle basicStyle = ExcelUtil.getBasicStyle(wb);
			HSSFCellStyle textCellStyle = ExcelUtil.getTextStyle(wb);
			HSSFCellStyle smallCellStyle = ExcelUtil.getSmallStyle(wb);
			HSSFCellStyle dateCellStyle = ExcelUtil.getDateStyle(wb);
			
			int base = 3;
			for(int i=0;i<userList.size();i++) {
				DepBalanceFinanceStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号(billNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue(vo.getBillNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getFinishTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getBillNo());
				
				Cell body_cell_2 = bodyRow.createCell(2); //投资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getFnCustomerCode());
				
				Cell body_cell_3 = bodyRow.createCell(3); //投资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());
				
				Cell body_cell_4 = bodyRow.createCell(4); //融资客户代码
				body_cell_4.setCellStyle(smallCellStyle);
				body_cell_4.setCellValue(vo.getLnCustomerCode());
				
				Cell body_cell_5 = bodyRow.createCell(5); //融资客户名称
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getLnUserName());
				
				Cell body_cell_6 = bodyRow.createCell(6); //部门
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue("");
				
				Cell body_cell_7 = bodyRow.createCell(7); //融资本金
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getPlanPrincipal());
				
				Cell body_cell_8 = bodyRow.createCell(8); //融资客户应付利息
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getLoanInterest());
				
				Cell body_cell_9 = bodyRow.createCell(9); //应付投资客户利息
				body_cell_9.setCellStyle(smallCellStyle);
				body_cell_9.setCellValue(vo.getPlanInterest());
				
				Cell body_cell_10 = bodyRow.createCell(10); //手续费
				body_cell_10.setCellStyle(smallCellStyle);
				body_cell_10.setCellValue(vo.getPlanFee());
				
				
			}
			
			ExportUtil.exportExcel(wb, fileName, response, request);
		}catch(Exception e) {
			e.printStackTrace();
		}finally {
			try {
				if(wb != null) {
					wb.close();
				}
				if(fis != null) {
					fis.close();
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 杭商信息服务费查询
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/rebateOrderQueryIndex")
	public String rebateOrderQueryIndex(ReqMsg_MAccount_QueryRebateOrder req, HttpServletRequest request,
										  HttpServletResponse response, HashMap<String,Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", req.getNumPerPage());
		if (request.getParameter("beginTime") != null || request.getParameter("endTime") != null) {
			ResMsg_MAccount_QueryRebateOrder res = (ResMsg_MAccount_QueryRebateOrder) hessianService.handleMsg(req);
			model.put("totalRows", res.getTotalRows());
			model.put("transferList", res.getValueList());
		}
		model.put("req", req);
		return "/financial/rebate_order_query_index";
	}
	
	/**
	 * 退票数据批量订单号查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/financial/rebateOrderQueryIndex/exportXls")
	public void rebateOrderQueryExport(HttpServletRequest request,ReqMsg_MAccount_QueryRebateOrder req,
			HttpServletResponse response, Map<String, Object> model) {
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
		req.setNumPerPage(Integer.MAX_VALUE);
		ResMsg_MAccount_QueryRebateOrder res = (ResMsg_MAccount_QueryRebateOrder) hessianService.handleMsg(req);
		
		List<HashMap<String,Object>> datas = res.getValueList();
		// 设置标题1
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("资产端借款编号");
		titles.add("借款金额");
		titles.add("批量代付订单号");
		titles.add("代付金额");
		titles.add("代付日期");
		titleMap.put("title", titles);
		list.add(titleMap);
		
		//设置导出excel内容
		if(!CollectionUtils.isEmpty(datas)) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("partnerLoanId"));
				obj.add(data.get("approveAmount"));
				obj.add(data.get("dkOrderNo"));
				obj.add(data.get("planTotal"));
				obj.add(data.get("finishTime")+"%");
				datasMap.put("rebateOrder"+(++i), obj);
				list.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "退票数据批量订单号查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
