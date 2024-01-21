package com.pinting.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.chain.Constants;
import org.aspectj.apache.bcel.classfile.Constant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import antlr.StringUtils;
import com.pinting.business.enums.SmsErrorCodeEnum;
import com.pinting.business.enums.SmsErrorCode4EMayEnum;
import com.pinting.business.enums.SmsErrorCode4EMayMobileEnum;
import com.pinting.business.enums.SmsErrorCode4EMayTelecomEnum;
import com.pinting.business.enums.SmsErrorCode4EMayUnicomEnum;
import com.pinting.business.hessian.manage.message.ReqMsg_SmsRecord_RecordJnlListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_SmsRecord_RecordJnlListQuery;
import com.pinting.business.service.manage.BsSmsRecordJnlService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.controller.process.SMSCheckProcess;
import com.pinting.util.ReturnDWZAjax;
/**
 * 短信记录查询
 * @author bianyatian
 * @2015-12-17 下午1:34:20
 */
@Controller
public class SmsRecordController {

	@Autowired
	@Qualifier("dispatchService")
	public HessianService smsRecordService;
	
	@Autowired
	private BsSmsRecordJnlService bsSmsRecordJnlService;
	
	@RequestMapping("/smsRecord/jnlIndex.htm")
	public String jnlIndex(ReqMsg_SmsRecord_RecordJnlListQuery req,HashMap<String,Object> model,HttpServletRequest request){
		Date beginTime = DateUtil.parse(request.getParameter("beginTime"),
				"yyyy-MM-dd hh:mm:ss");
		Date overTime = DateUtil.parse(request.getParameter("overTime"),
				"yyyy-MM-dd hh:mm:ss");
		req.setBeginTime(beginTime);
		req.setOverTime(overTime);
		ResMsg_SmsRecord_RecordJnlListQuery res = (ResMsg_SmsRecord_RecordJnlListQuery)smsRecordService.handleMsg(req);
		
		List<Map<String, Object>> errorCodeList = SmsErrorCodeEnum.getMapList();
		List<Map<String, Object>> errorCodeListEMay = SmsErrorCode4EMayEnum.getMapList();//亿美
		List<Map<String, Object>> errorCodeListEMayMobile = SmsErrorCode4EMayMobileEnum.getMapList();//亿美-移动
		List<Map<String, Object>> errorCodeListEMayTele = SmsErrorCode4EMayTelecomEnum.getMapList(); //亿美-电信
		List<Map<String, Object>> errorCodeListEMayUni = SmsErrorCode4EMayUnicomEnum.getMapList(); //亿美-联通
		
		model.put("req", req);
		model.put("list", res.getValueList());
		model.put("errorCodeList", errorCodeList);
		model.put("errorCodeListEMay", errorCodeListEMay);
		model.put("errorCodeListEMayMobile", errorCodeListEMayMobile);
		model.put("errorCodeListEMayTele", errorCodeListEMayTele);
		model.put("errorCodeListEMayUni", errorCodeListEMayUni);
		model.put("record", res);
		return "/smsRecord/jnlIndex";
	}
	
	/**
	 * 拉取数据并存储
	 * @param request
	 * @return
	 */
	@RequestMapping("/smsRecord/check.htm")
	@ResponseBody
	public Map<String,Object> check(HttpServletRequest request){
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			SMSCheckProcess process = new SMSCheckProcess();
			process.setBsSmsRecordJnlService(bsSmsRecordJnlService);
			new Thread(process).start();
			map.put("msg", "正在获取短信发送【亿美】状态，稍后请重新查询！");
			return map;
		} catch (Exception e) {
			map.put("msg","操作失败！");
			return map;
		}
		
	}
	
}
