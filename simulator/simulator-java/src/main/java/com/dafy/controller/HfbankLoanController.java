package com.dafy.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.dafy.core.util.StringUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.dafy.core.util.DateUtil;
import com.dafy.model.vo.LoanInfoVO;
import com.dafy.model.vo.OutOfAccountReqModel;
import com.dafy.model.vo.OutOfAccountResModel;
import com.dafy.service.SimFinancingService;
import com.dafy.tools.hfBank.CommunicateUtil;
import com.dafy.tools.hfBank.HfbankOutConstant;



/**
 * 
 * @project simulator-java
 * @title HfbankLoanController.java
 * @author Dragon & cat
 * @date 2017-4-11
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Controller
public class HfbankLoanController {
	private static final Logger log = LoggerFactory.getLogger(HfbankLoanController.class);

	@Autowired
	private SimFinancingService simFinancingService;
	
	@ResponseBody
	@RequestMapping(value = "/Gateway_client/fundAction_gatewayRechargeRequest")
	public String fundAction_gatewayRechargeRequest(HttpServletRequest request, HttpServletResponse response) {
		log.info("恒丰银行请求接口:fundAction_gatewayRechargeRequest");
		// 获取参数并解析成可用map
		Map properties = request.getParameterMap();
		// 返回值Map
		Map returnMap = new HashMap();
		Iterator entries = properties.entrySet().iterator();
		Map.Entry entry;
		String name = "";
		String value = "";
		while (entries.hasNext()) {
			entry = (Map.Entry) entries.next();
			name = (String) entry.getKey();
			Object valueObj = entry.getValue();
			if(null == valueObj){
				value = "";
			}else if(valueObj instanceof String[]){
				String[] values = (String[])valueObj;
				for(int i=0;i<values.length;i++){
					value = values[i] + ",";
				}
				value = value.substring(0, value.length()-1);
			}else{
				value = valueObj.toString();
			}
			returnMap.put(name, value);
		}
		log.info("恒丰银行请求接口请求参数:"+returnMap);

		// 网关充值
		String url = "";
		Object return_url = returnMap.get("return_url");
		log.info("网关充值return_url0：{}", return_url);
		if(return_url instanceof String) {
			url = (String) return_url;
			log.info("网关充值return_url1：{}", url);

		} else {
			url = String.valueOf(return_url);
			log.info("网关充值return_url2：{}", url);

		}
		log.info("网关充值return_url：{}", url);

		String result = "<body onload=\"pay.submit()\">正在提交请稍后。。。。。。。。\n" +
				"\t<form method=\"post\" name=\"pay\" id=\"pay\" action=\"https://gw.baofoo.com/payindex\">\n" +
				"\t\t<input name=\"MemberID\" type=\"hidden\" value=\"1160912\"/>\n" +
				"\t\t<input name=\"TerminalID\" type=\"hidden\" value=\"34452\"/>\n" +
				"\t\t<input name=\"InterfaceVersion\" type=\"hidden\" value= \"4.0\"/>\n" +
				"\t\t<input name=\"KeyType\" type=\"hidden\" value= \"1\"/>\n" +
				"\t\t<input name=\"PayID\" type=\"hidden\" value= \"3005\"/>\n" +
				"\t\t<input name=\"TradeDate\" type=\"hidden\" value= \"20171207175304\" />\n" +
				"\t\t<input name=\"TransID\" type=\"hidden\" value= \"201712071753040792765430\" />\n" +
				"\t\t<input name=\"OrderMoney\" type=\"hidden\" value= \"" + returnMap.get("amt") +"\"/>\n" +
				"\t\t<input name=\"PageUrl\" type=\"hidden\" value= \"https://www.bigangwan.com/gen2.0/index\"/>\n" +
				"\t\t<input name=\"ReturnUrl\" type=\"hidden\" value= \"" + url + "\"/>\n" +
				"\t\t<input name=\"Signature\" type=\"hidden\" value=\"ec955a0823a52018e69f093eae1186d1\"/>\n" +
				"\t\t<input name=\"NoticeType\" type=\"hidden\" value= \"1\"/>\n" +
				"\t</form>\n" +
				"</body>\n";
		try {
			response.setContentType("text/html; charset=utf-8");
			PrintWriter out = response.getWriter();
			out.print(result);
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;

	}

	@RequestMapping(value = "/Gateway_client/{code}")
	@ResponseBody
	public String login(HttpServletRequest request, @PathVariable String code, HttpServletResponse response) throws Exception {
		log.info("恒丰银行请求接口:"+code);
		// 获取参数并解析成可用map
	    Map properties = request.getParameterMap();
	    // 返回值Map
	    Map returnMap = new HashMap();
	    Iterator entries = properties.entrySet().iterator();
	    Map.Entry entry;
	    String name = "";
	    String value = "";
	    while (entries.hasNext()) {
	        entry = (Map.Entry) entries.next();
	        name = (String) entry.getKey();
	        Object valueObj = entry.getValue();
	        if(null == valueObj){
	            value = "";
	        }else if(valueObj instanceof String[]){
	            String[] values = (String[])valueObj;
	            for(int i=0;i<values.length;i++){
	                value = values[i] + ",";
	            }
	            value = value.substring(0, value.length()-1);
	        }else{
	            value = valueObj.toString();
	        }
	        returnMap.put(name, value);
	    }
	    log.info("恒丰银行请求接口请求参数:"+returnMap);
	    
		
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("recode", "10000");
		map.put("remsg", "成功");
		map.put("signdata", returnMap.get("signdata"));
		
		if (code.equals("userAction_batchRegistExt")) {
			//4.1.1.批量开户(四要素绑卡)
			//200{"error_data":[{"error_no":"20016","detail_no":"1","error_info":"订单号重复","mobile":"15868157902"}],
			//"finish_datetime":"2017-04-11 13:48:16","order_info":"批量注册处理完成",
			//"order_status":"2","recode":"10000","remsg":"成功",
			//"signdata":"f401e1a9b44461713c65f83cea667e7e","success_num":0,"total_num":1}

			String dataJson = (String)returnMap.get("data");
	        JSONArray jsonArray = JSONArray.fromObject(dataJson);  
	        List<Map<String,Object>> mapListJson = (List)jsonArray; 
	        
	        List<Map<String,Object>> rList = new ArrayList<>();
	        for (Map<String, Object> map2 : mapListJson) {
				Map<String, Object> data = new HashMap<>();
				data.put("detail_no",map2.get("detail_no"));
				data.put("mobile",map2.get("mobile"));
				data.put("platcust","hf-"+map2.get("mobile"));
				rList.add(data);
			}
	        map.put("success_data", rList);
	        map.put("finish_datetime", new Date());
	        map.put("order_info", "批量注册处理完成");
	        map.put("order_status", "2");
			map.put("success_num", returnMap.get("total_num"));
			map.put("total_num", returnMap.get("total_num"));
			
			
		}else if (code.equals("accountAction_getAccountN_balace")) {
			//4.6.6.账户余额明细查询
			//{"data":{"balance":"0.0000","frozen_amount":"0.0000"},"recode":"10000","remsg":"成功","signdata":"98f06cefc1af15c39a990be8fce100c7"}
			Map<String, Object> data = new HashMap<>();
			data.put("balance","0.0000");
			data.put("frozen_amount","0.0000");
			map.put("data", data);
			
		}else if (code.equals("userAction_getBinkCardCode2")) {
			//4.1.8.短验绑卡申请
		}else if (code.equals("userAction_addUser2")) {
			//4.1.9.短验绑卡确认
			map.put("platcust", "HF-"+returnMap.get("pre_mobile"));
		}else if(code.equals("bankCardAction_batchUpdateCardExt")){
			// 批量换卡
			Map<String, Object> success = new HashMap<>();
			Map<String, Object> error = new HashMap<>();
			String data = (String)returnMap.get("data");
			JSONArray jsonArray = JSONArray.fromObject(data);
			
//			Json detail_no = jsonArray.get(0);
			List<Map<String, Object>> success_data = new ArrayList<>();

			List<Map<String,Object>> mapListJson = (List)jsonArray;
			for (Map<String,Object> mapData: mapListJson) {
				Map<String, Object> succ = new HashMap<>();
				succ.put("detail_no", mapData.get("detail_no"));
				succ.put("platcust", mapData.get("platcust"));
//				succ.put("error_no", "asdas");
//				succ.put("error_info", "测试失败");
				success_data.add(succ);
			}
			map.put("success_data", success_data);
//			map.put("error_data", success_data);
	        map.put("total_num",returnMap.get("total_num"));
	       // map.put("success_num",returnMap.get("total_num"));
	        map.put("success_num",returnMap.get("3"));
			
//			map.put("recode", "100201");
//			map.put("remsg", "失败");
		}else if (code.equals("fundAction_directQuickRequest")) {
			//4.4.3.快捷充值请求
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","HFLSH-"+DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			map.put("data", data);
			
//			map.put("recode", "10001");
//			map.put("remsg", "失败");
		}else if (code.equals("fundAction_directQuickConfirm")) {
			//4.4.4.快捷充值确认
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","HFLSH-"+DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			map.put("data", data);	
			
//			map.put("recode", "10001");
//			map.put("remsg", "失败");
		}else if (code.equals("publishAction_publish")) {
			//4.3.1.标的发布
			Map<String, Object> data = new HashMap<>();
			data.put("prod_id",returnMap.get("prod_id"));
			map.put("data", data);
			map.put("recode", "10000");
			map.put("remsg", "成功");
			
/*			map.put("recode", "10001");
			map.put("remsg", "失败");*/

		}else if (code.equals("buyAction_batchExt")) {
			//4.3.3.批量投标
			String dataJson = (String)returnMap.get("data");
	        JSONArray jsonArray = JSONArray.fromObject(dataJson);
	        List<Map<String,Object>> mapListJson = (List)jsonArray;
	        String prod_id = (String)returnMap.get("plat_no");


	        List<Map<String,Object>> rList = new ArrayList<>();
	        for (Map<String, Object> map2 : mapListJson) {
	        	Map<String, Object> data = new HashMap<>();
				data.put("detail_no",map2.get("detail_no"));
				data.put("platcust",map2.get("platcust"));
				data.put("prod_id",prod_id);
				data.put("trans_amt",map2.get("trans_amt"));
				rList.add(data);
			}
	        map.put("success_data", rList);
	        map.put("plat_no",returnMap.get("plat_no"));
	        map.put("order_no",returnMap.get("order_no"));
	        map.put("finish_datetime", new Date());

	        map.put("order_status","3");
	        map.put("order_info","批量投标成功");

	        map.put("total_num",returnMap.get("total_num"));
	       // map.put("success_num",returnMap.get("total_num"));
	        map.put("success_num",returnMap.get("total_num"));
	        
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");

		}else if (code.equals("establishAction_establishOrAbandon")) {
			//4.3.2.标的成废
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","HFLSH-"+returnMap.get("order_no"));
			map.put("data", data);
			
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");

		}else if (code.equals("establishAction_prodAbandon")) {
			//4.3.8.标的废除
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","HFLSH-"+returnMap.get("order_no"));
			map.put("data", data);
			
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");	

		}else if (code.equals("buyAction_batchExt")) {
			
			//4.3.4.成标出账
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","HFLSH-"+returnMap.get("order_no"));
			map.put("data", data);
		}else if(code.equals("userAction_batchRegistExt3")) {
			//4.1.2.批量开户（实名认证）
			map.put("plat_no",returnMap.get("plat_no"));
			map.put("order_no",returnMap.get("order_no"));
			map.put("finish_datetime", new Date());
			map.put("order_status","2");
			map.put("order_info","批量开户实名认证成功");
			map.put("total_num",returnMap.get("total_num"));
			map.put("success_num",returnMap.get("total_num"));

			String dataJson = (String)returnMap.get("data");
			JSONArray jsonArray = JSONArray.fromObject(dataJson);
			List<Map<String,Object>> mapListJson = (List)jsonArray;
			List<Map<String,Object>> rList = new ArrayList<>();
			for (Map<String, Object> map2 : mapListJson) {
				Map<String, Object> data = new HashMap<>();
				data.put("detail_no",map2.get("detail_no"));
				data.put("mobile",map2.get("mobile"));
				data.put("platcust","hf-"+map2.get("mobile"));
				rList.add(data);
			}
			map.put("success_data", rList);
			
			
			
		}else if(code.equals("repayAction_borrowCutRepay")) {
			//4.4.7.借款人扣款还款（代扣）
			
//			map.put("recode", "10001");
//			map.put("remsg", "失败");
			
		}else if(code.equals("fundAction_batchWithdrawExt")) {
			//4.5.1.批量提现
			map.put("plat_no",returnMap.get("plat_no"));
			map.put("order_no",returnMap.get("order_no"));
			map.put("finish_datetime", new Date());
			map.put("order_status","3");
			map.put("order_info","批量提现");

			String dataJson = (String)returnMap.get("data");
			JSONArray jsonArray = JSONArray.fromObject(dataJson);
			List<Map<String,Object>> mapListJson = (List)jsonArray;
			
			List<Map<String,Object>> rList = new ArrayList<>();
			for (Map<String, Object> map2 : mapListJson) {
				Map<String, Object> data = new HashMap<>();
				data.put("detail_no",map2.get("detail_no"));
				data.put("mobile",map2.get("mobile"));
				data.put("platcust","hf-"+map2.get("mobile"));
				data.put("amt",map2.get("amt"));
				rList.add(data);
			}
			map.put("success_data", rList);
			map.put("total_num",mapListJson.size());
			map.put("success_num",returnMap.get("total_num"));
			
//			map.put("recode", "10001");
//			map.put("remsg", "失败");

		}else if(code.equals("accountAction_getAccountInfo")) {
			//4.6.2.资金余额查询
			Map<String, Object> data = new HashMap<>();
			data.put("balance",returnMap.get("balance"));
			map.put("data", data);
		}else if(code.equals("publishAction_getProductN_balance")) {
			//4.6.7.标的账户余额查询
			Map<String, Object> data = new HashMap<>();
			data.put("data",returnMap.get("data"));
			map.put("data", "0.0000");
		}else if(code.equals("orderQueryAction_queryOrder")) {
			//4.6.8.订单状态查询
			Map<String, Object> data = new HashMap<>();
			data.put("plat_no",returnMap.get("plat_no"));
			data.put("query_order_no",returnMap.get("query_order_no"));
			data.put("status","1");
			map.put("data", data);

		}else if(code.equals("orderQueryAction_getFundOrderInfo")) {
			//4.6.9.充值订单状态查询
			Map<String, Object> data = new HashMap<>();
			data.put("plat_no",returnMap.get("plat_no"));
			data.put("query_order_no",returnMap.get("original_serial_no"));
			data.put("status","1");
			map.put("data", data);

		}else if(code.equals("publishAction_publish")) {
			//4.3.1.标的发布
			Map<String, Object> data = new HashMap<>();
			data.put("prod_id",returnMap.get("prod_id"));
			map.put("data", data);

		}else if(code.equals("buyAction_batchExt")) {
			//4.3.3.批量投标
			map.put("plat_no",returnMap.get("plat_no"));
			map.put("order_no",returnMap.get("order_no"));
			map.put("finish_datetime", new Date());
			map.put("order_status","2");
			map.put("order_info","批量投标完成");

			map.put("total_num",returnMap.get("total_num"));
			map.put("success_num",returnMap.get("total_num"));

			String dataJson = (String)returnMap.get("success_data");
			JSONArray jsonArray = JSONArray.fromObject(dataJson);
			List<Map<String,Object>> mapListJson = (List)jsonArray;
			
			List<Map<String,Object>> rList = new ArrayList<>();
			for (Map<String, Object> map2 : mapListJson) {
				Map<String, Object> data = new HashMap<>();
				data.put("detail_no",returnMap.get("detail_no"));
				data.put("platcust",returnMap.get("platcust"));
				data.put("prod_id",returnMap.get("prod_id"));
				data.put("trans_amt",returnMap.get("trans_amt"));
				rList.add(data);
			}
			map.put("data", rList);
		}else if(code.equals("chargeOffAction_chargeOff")) {
			
			
			
		    // 模拟解析失败
//		    Iterator entries1 = properties.entrySet().iterator();
//		    entries1 = null;
//		    Map.Entry entry1;
//		    String name1 = "";
//		    String value1 = "";
//		    
//		    while (entries.hasNext()) {
//		        entry1 = (Map.Entry) entries1.next();
//		        name1 = (String) entry1.getKey();
//		        Object valueObj = entry1.getValue();
//		    }
//		    returnMap = null;
			//4.3.4.标的出账
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","恒丰流水号"+returnMap.get("order_no"));
			map.put("data", data);
			
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");
		}else if(code.equals("repayAction_batchExt")) {
			//4.2.1.借款人批量还款
			
			String dataJson = (String)returnMap.get("data");
	        JSONArray jsonArray = JSONArray.fromObject(dataJson);  
	        List<Map<String,Object>> mapListJson = (List)jsonArray; 
	        
	        List<Map<String,Object>> rList = new ArrayList<>();
	        for (Map<String, Object> map2 : mapListJson) {
				Map<String, Object> data = new HashMap<>();
				data.put("detail_no",map2.get("detail_no"));
				data.put("prod_id",map2.get("prod_id"));
				data.put("platcust",map2.get("platcust"));
				data.put("trans_amt",map2.get("trans_amt"));
				rList.add(data);
			}
	        map.put("success_data", rList);
	        map.put("finish_datetime", new Date());
	        map.put("order_info", "批量还款完成");
	        map.put("order_status", "3");
			map.put("success_num", returnMap.get("total_num"));
			map.put("total_num", returnMap.get("total_num"));
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");
		}else if(code.equals("repayAction_compensate")) {
			//4.2.2.标的代偿（委托）还款
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","恒丰流水号"+returnMap.get("order_no"));
			map.put("data", data);
			
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");
		}else if(code.equals("repayAction_repayCompensate")) {
			//4.2.3.借款人还款代偿（委托）
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","hf_query_id"+returnMap.get("order_no"));
			map.put("data", data);
			
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");
		}else if(code.equals("repayAction_prodRepay")) {
			//4.3.6.标的还款
			Map<String, Object> data = new HashMap<>();
			data.put("order_no",returnMap.get("order_no"));
			data.put("order_status","2");
			data.put("process_date",DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
			data.put("query_id","hf_query_id"+returnMap.get("order_no"));
			map.put("data", data);
			
//	        map.put("recode", "10001");
//			map.put("remsg", "失败");
		}else if(code.equals("platformTransferAction_platCharge")) {
			//平台充值
	        map.put("recode", "10000");
			map.put("remsg", "成功");
		}else if(code.equals("platformTransferAction_platWithdraw")) {
			//平台提现
			map.put("recode", "10000");
			map.put("remsg", "成功");
		} else if(code.equals("transferAction_platformAccountConverse")) {
	        map.put("recode", "10000");
//			map.put("remsg", "模拟平台互转失败");
			map.put("remsg", "模拟平台互转成功");
		} else if(code.equals("platformTransferAction_platTrans")) {
			//平台转个人
//	        map.put("recode", "10001");
//			map.put("remsg", "模拟平台转个人失败");
		} else if("userAction_updatePlatAcct".equals(code)) {
			// 客户信息变更
			map.put("recode", "10000");
			map.put("remsg", "模拟客户信息变更成功");
		} else if ("out_of_ccount".equals(code)) {
			
			String orderNo = (String) returnMap.get("orderNo");
			log.info("模拟恒丰出账回调通知，订单号："+orderNo);
			LoanInfoVO vo = simFinancingService.selectLoanInfoByPartnerOrderNo(orderNo);
			if (vo != null) {
				log.info("查询到订单["+vo.getPartnerOrderNo()+"]相关借款信息："+JSON.toJSONString(vo));
				OutOfAccountReqModel ofAccountReqModel = new OutOfAccountReqModel();
				ofAccountReqModel.setPlat_no("bgw-21005");
				ofAccountReqModel.setOrder_no(vo.getOrderNo());
				ofAccountReqModel.setOut_amt(String.valueOf(vo.getApplyAmount()));
				ofAccountReqModel.setPlatcust(vo.getHfUserId());
				ofAccountReqModel.setOpen_branch(vo.getPay19BankCode());
				ofAccountReqModel.setWithdraw_account(vo.getBankCardNo());
				ofAccountReqModel.setPayee_name(vo.getUserName());
				ofAccountReqModel.setPay_finish_date("2017-10-31");
				ofAccountReqModel.setPay_finish_time("14:55:30");
				ofAccountReqModel.setOrder_status("1");
				ofAccountReqModel.setPay_path("06");
				log.info("恒丰银行出账回调币港湾请求信息：{}", JSONObject.fromObject(ofAccountReqModel));
				String result = CommunicateUtil.doCommunicate2Hf(ofAccountReqModel,HfbankOutConstant.HF_BANK_URL +"/remote/hfbank/business/outOfAccount");
				OutOfAccountResModel resModel = JSON.parseObject(result,OutOfAccountResModel.class);
				log.info("恒丰银行出账回调币港湾响应信息：{}", JSONObject.fromObject(resModel));
				
				if ("fail".equals(resModel.getRecode())) {
			        map.put("recode", "fail");
					map.put("remsg", "恒丰银行出账回调币港湾响应信息失败");
				}
				
			}else {
				map.put("recode", "fail");
				map.put("remsg", "数据库中未查询到相关信息，订单号"+orderNo);
				log.info("未查询到对应信息");
			}
			
		} else if ("out_of_account_fail".equals(code)) {
			
			String orderNo = (String) returnMap.get("orderNo");
			log.info("模拟恒丰出账回调通知，订单号："+orderNo);
			LoanInfoVO vo = simFinancingService.selectLoanInfoByPartnerOrderNo(orderNo);
			if (vo != null) {
				log.info("查询到订单["+vo.getPartnerOrderNo()+"]相关借款信息："+JSON.toJSONString(vo));
				OutOfAccountReqModel ofAccountReqModel = new OutOfAccountReqModel();
				ofAccountReqModel.setPlat_no("bgw-21005");
				ofAccountReqModel.setOrder_no(vo.getOrderNo());
				ofAccountReqModel.setOut_amt(String.valueOf(vo.getApplyAmount()));
				ofAccountReqModel.setPlatcust(vo.getHfUserId());
				ofAccountReqModel.setOpen_branch(vo.getPay19BankCode());
				ofAccountReqModel.setWithdraw_account(vo.getBankCardNo());
				ofAccountReqModel.setPayee_name(vo.getUserName());
				ofAccountReqModel.setPay_finish_date("2017-10-31");
				ofAccountReqModel.setPay_finish_time("14:55:30");
				ofAccountReqModel.setOrder_status("2");
				ofAccountReqModel.setPay_path("06");
				log.info("恒丰银行出账回调币港湾请求信息：{}", JSONObject.fromObject(ofAccountReqModel));
				String result = CommunicateUtil.doCommunicate2Hf(ofAccountReqModel,HfbankOutConstant.HF_BANK_URL +"/remote/hfbank/business/outOfAccount");
				OutOfAccountResModel resModel = JSON.parseObject(result,OutOfAccountResModel.class);
				log.info("恒丰银行出账回调币港湾响应信息：{}", JSONObject.fromObject(resModel));
				
				if ("fail".equals(resModel.getRecode())) {
			        map.put("recode", "fail");
					map.put("remsg", "恒丰银行出账回调币港湾响应信息失败");
				}
				
			}else {
				map.put("recode", "fail");
				map.put("remsg", "数据库中未查询到相关信息，订单号"+orderNo);
				log.info("未查询到对应信息");
			}
			
		}else if(code.equals("transferAction_transferDebt")) {

			//4.3.6.标的转让

			Map<String, Object> data = new HashMap<>();

			map.put("data", data);

			map.put("recode", "10000");

			map.put("remsg", "成功");

			

//	        map.put("recode", "10001");

//			map.put("remsg", "失败");

		}


		String resStr = JSON.toJSONString(map);
		log.info("恒丰银行请求接口返回参数:"+resStr); 
		return resStr;
		
	}


	
}
