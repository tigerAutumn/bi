package com.pinting.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.model.MUserOpRecord;
import com.pinting.business.service.manage.MUserOpRecordService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.manage.enums.CookieEnums;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_BsPayOrders_ListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_BsPayOrders_RealNameAuth;
import com.pinting.business.hessian.manage.message.ResMsg_BsPayOrders_ListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsPayOrders_RealNameAuth;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

@Controller
@RequestMapping("/payOrders")
public class PayOrdersCotroller {

	private Logger log = LoggerFactory.getLogger(PayOrdersCotroller.class);
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService hessianService;
	@Autowired
	private MUserOpRecordService mUserOpRecordService;
	
	/**
	 * 订单查询（客服使用）
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/serve/index")
	public String init(ReqMsg_BsPayOrders_ListQuery reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		if((StringUtil.isEmpty(reqMsg.getMobile()) && StringUtil.isEmpty(reqMsg.getUserMobile()))
		        || StringUtil.isEmpty(reqMsg.getIdCard())){
	        return "/payOrders/serve_index";
		}
		String pageNum = reqMsg.getPageNum();
		String numPerPage = reqMsg.getNumPerPage();
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		ResMsg_BsPayOrders_ListQuery resp = (ResMsg_BsPayOrders_ListQuery) hessianService
				.handleMsg(reqMsg);
		model.put("payOrdersList", resp.getList());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("mobile", reqMsg.getMobile());
		model.put("idCard", reqMsg.getIdCard());
		model.put("userMobile", reqMsg.getUserMobile());
        return "/payOrders/serve_index";
	}
	
	/**
	 * 订单查询2
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/serve/indexCustomer")
    public String indexCustomer(ReqMsg_BsPayOrders_ListQuery reqMsg,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) {
	    if((StringUtil.isEmpty(reqMsg.getMobile()) && StringUtil.isEmpty(reqMsg.getUserMobile()))){
            return "/payOrders/customer_index";
        }
        String pageNum = reqMsg.getPageNum();
        String numPerPage = reqMsg.getNumPerPage();
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
            reqMsg.setPageNum(pageNum);
            reqMsg.setNumPerPage(numPerPage);
        }

		//管理用户操作登记表新增数据
		if(StringUtil.isNotBlank(reqMsg.getMobile()) || StringUtil.isNotBlank(reqMsg.getUserMobile()) || StringUtil.isNotBlank(reqMsg.getIdCard())) {
    		MUserOpRecord record = new MUserOpRecord();
    		CookieManager cookie = new CookieManager(request);
			String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
					CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
			if(StringUtils.isBlank(mUserId)) {
				reqMsg.setMobile("");
				reqMsg.setUserMobile("");
				reqMsg.setIdCard("");
			}else {
				record.setOpUserId(Integer.parseInt(mUserId));
				record.setFunctionName("客户订单查询");
				record.setNote("按条件查询");
				if(StringUtil.isNotBlank(reqMsg.getMobile())) {
        			record.setOpContent("预留手机号码："+reqMsg.getMobile());
        		}
    			if(StringUtil.isNotBlank(reqMsg.getUserMobile())) {
					record.setOpContent(record.getOpContent()!=null ? record.getOpContent()+"，用户手机号码："+reqMsg.getUserMobile() : "用户手机号码："+reqMsg.getUserMobile());
				}
				if(StringUtil.isNotBlank(reqMsg.getIdCard())) {
					record.setOpContent(record.getOpContent()!=null ? record.getOpContent()+"，身份证号码："+reqMsg.getIdCard() : "身份证号码："+reqMsg.getIdCard());
				}
        		String ip = getIp(request);
        		record.setIp(ip);
        		record.setFunctionUrl("/payOrders/serve/indexCustomer");
        		mUserOpRecordService.addMUserOpRecord(record);
			}
		}

        ResMsg_BsPayOrders_ListQuery resp = (ResMsg_BsPayOrders_ListQuery) hessianService
                .handleMsg(reqMsg);
        model.put("payOrdersList", resp.getList());
        model.put("pageNum", resp.getPageNum());
        model.put("numPerPage", resp.getNumPerPage());
        model.put("totalRows", resp.getTotalRows());
        model.put("mobile", reqMsg.getMobile());
        model.put("idCard", reqMsg.getIdCard());
        model.put("userMobile", reqMsg.getUserMobile());
        return "/payOrders/customer_index";
    }

	@RequestMapping("/realNameAuth")
	public @ResponseBody Map<Object,Object> realNameAuth(ReqMsg_BsPayOrders_RealNameAuth req, HttpServletRequest request,
			HttpServletResponse response, Map<String, Object> model) {
		String bankName = req.getBankCode();
		switch (bankName) {
			case "工商银行":
	            req.setBankCode("ICBC");
	            break;
	        case "兴业银行":
	            req.setBankCode("CIB");
	            break;
	        case "农业银行":
	            req.setBankCode("ABC");
	            break;
	        case "中国银行":
	            req.setBankCode("BOC");
	            break;
	        case "建设银行":
	            req.setBankCode("CCB");
	            break;
	        case "招商银行":
	            req.setBankCode("CMB");
	            break;
	        case "光大银行":
	            req.setBankCode("CEB");
	            break;
	        case "民生银行":
	            req.setBankCode("CMBC");
	            break;
	        case "邮储银行":
	            req.setBankCode("POSTGC");
	            break;
	        case "交通银行":
	            req.setBankCode("COMM");
	            break;
	        case "中信银行":
	            req.setBankCode("CITIC");
	            break;
	        case "上海银行":
	            req.setBankCode("BOS");
	            break;
	        case "北京银行":
	            req.setBankCode("BOB");
	            break;
	        case "华夏银行":
	            req.setBankCode("HXB");
	            break;
	        case "平安银行":
	            req.setBankCode("PAB");
	            break;
	        case "广发银行":
	            req.setBankCode("GDB");
	            break;
	        case "浦发银行":
	            req.setBankCode("SPDB");
	            break;
	        default:
	            break;
	    }
		
		ResMsg_BsPayOrders_RealNameAuth resp = (ResMsg_BsPayOrders_RealNameAuth) hessianService.handleMsg(req);
		
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
			return ReturnDWZAjax.success("实名认证成功，结果：匹配");
		} else {
			
			return ReturnDWZAjax.fail(resp.getResMsg());
		}
	
	}

	/**
	 * 获取Ip地址
	 * @param request
	 * @return
     */
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

}
