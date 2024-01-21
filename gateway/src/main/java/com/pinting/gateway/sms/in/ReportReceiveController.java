package com.pinting.gateway.sms.in;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.gateway.hessian.message.hfbank.G2BReqMsg_Sms_CL253ReportNotice;
import com.pinting.gateway.hessian.message.hfbank.G2BResMsg_Sms_CL253ReportNotice;
import com.pinting.gateway.hfbank.in.util.HfbankInConstant;
import com.pinting.gateway.hfbank.in.util.HfbankInMsgUtil;
import com.pinting.gateway.sms.model.CL253ReportModel;

/**
 * 短信报告接收
 * @author bianyatian
 * @2017-6-27 下午4:05:21
 */
@Controller
public class ReportReceiveController {

	private final Logger logger = LoggerFactory.getLogger(ReportReceiveController.class);
	
	@Autowired
    @Qualifier("gatewayHFBankService")
    private HessianService gatewayHFBankService;
	
	/**
	 * 创蓝253平台短信报告通知
	 * @param request
	 * @param response
	 * @param req
     * @return
     */
	@RequestMapping(value = "/sms/cl253ReportNotice", method = RequestMethod.GET)
	public @ResponseBody
	String cl253ReportNotice(HttpServletRequest request,
						   HttpServletResponse response, CL253ReportModel reqModel) {
		
		logger.info("创蓝253平台短信报告通知request参数:["+reqModel.toString()+"]");
		// 待返回报文密文
		G2BReqMsg_Sms_CL253ReportNotice req = new G2BReqMsg_Sms_CL253ReportNotice();
		req.setMobile(reqModel.getMobile());
		req.setMsgid(reqModel.getMsgid());
		req.setStatus(reqModel.getStatus());
		if(StringUtils.isNotBlank(reqModel.getReportTime())){
			req.setReportTime(DateUtil.parse("20"+reqModel.getReportTime(), "yyyyMMddHHmm"));
		}
		G2BResMsg_Sms_CL253ReportNotice res = (G2BResMsg_Sms_CL253ReportNotice)gatewayHFBankService.handleMsg(req);
		
		return "success";
	}
}
