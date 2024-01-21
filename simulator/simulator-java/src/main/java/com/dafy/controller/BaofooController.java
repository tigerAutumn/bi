package com.dafy.controller;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.dafy.core.constant.Globals;
import com.dafy.core.util.DateUtil;
import com.dafy.model.pojo.SimFinancingDetail;
import com.dafy.tools.ResultHolder;
import com.dafy.tools.baofoo.RsaCodingUtil;
import com.dafy.tools.baofoo.SecurityUtil;
/**
 * 宝付相关请求模拟器
 * @author longmao
 *
 */
@Controller
public class BaofooController {
	private static final Logger log = LoggerFactory.getLogger(BaofooController.class);
	
	/**
	 * 宝付请求模拟器
	 * @throws Exception
	 */
	@RequestMapping(value = "/baofoo/{code}", method = RequestMethod.POST)
	@ResponseBody
	public String login(HttpServletRequest request, @PathVariable String code) throws Exception {
		
		
		log.info("币港湾请求宝付接口:"+code);
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
	    log.info("币港湾请求宝付接口参数:"+returnMap);
		String data_content = (String)returnMap.get("data_content");
		
	    String cutPaymentcerpath = System.getProperty("user.dir")+"/src/main/webapp/resources/cer/bigangwan20171214.cer";
		//String cutPaymentcerpath = Globals.DAFY_OUT_DES_KEY;
		
	    data_content = RsaCodingUtil.decryptByPubCerFile(data_content, cutPaymentcerpath);
	    //解密返回值
	    data_content = SecurityUtil.Base64Decode(data_content);
	    log.info("币港湾请求宝付接口参数data_content解密:"+data_content);
	    
	    JSONObject jsonDataContent=JSONObject.fromObject(data_content);
	    

	    Map resultMap = new HashMap();
	    
	    /*宝付代扣模拟器
	     * 要使用此模拟器，更改gateway发起宝付代扣请求URL地址
	     * ##代扣模拟器请求地址 baofoo.cut.url = http://localhost:8098/simulator/baofoo/cutPayment
	     * 
	     * 模拟成功或者失败只需要更改type类型（SUCCESS 成功、FAIL 失败）
	     * 失败的情况可以自定义resp_code和resp_msg内容
	     * */
	    
	    if ("cutPayment".equals(code)) {
			String type  = "SUCCESS"; //SUCCESS 成功、FAIL 失败、PROCESS处理中
            String queryType = "SUCCESS"; //SUCCESS 成功、FAIL 失败、PROCESS处理中
	    	//模拟情况,代扣交易和代扣查询为同一接口，用txn_sub_type区分
            if("13".equals(jsonDataContent.get("txn_sub_type"))){
                //代扣交易
                if ("FAIL".equals(type)) {
                    //失败
                    //失败》{"additional_info":"赞分期用户代扣还款","biz_type":"0000",
                    //"data_type":"json","member_id":"912736","req_reserved":"",
                    //"resp_code":"BF00120","resp_msg":"报文交易要素缺失密文域中参数pay_code缺失",
                    //"terminal_id":"31009","trade_date":"20171109090509","trans_id":"9b9f708d3e5613021460",
                    //"trans_serial_no":"9b9f708ec2e0064ea768","txn_sub_type":"13","txn_type":"0431","version":"4.0.0.0"}

                    resultMap.put("additional_info", jsonDataContent.get("additional_info"));
                    resultMap.put("biz_type", jsonDataContent.get("biz_type"));
                    resultMap.put("data_type", returnMap.get("data_type"));
                    resultMap.put("member_id", returnMap.get("member_id"));
                    resultMap.put("req_reserved", jsonDataContent.get("req_reserved"));
                    resultMap.put("resp_code", "BF00120"); //返回码 自定义 BF00120 报文交易要素缺失密文域中参数pay_code缺失
                    resultMap.put("resp_msg", "报文交易要素缺失密文域中参数pay_code缺失");//失败情况描述
                    resultMap.put("terminal_id", returnMap.get("terminal_id"));
                    resultMap.put("trade_date", DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_id", jsonDataContent.get("trans_id"));
                    resultMap.put("trans_serial_no", jsonDataContent.get("trans_serial_no"));
                    resultMap.put("txn_sub_type", jsonDataContent.get("txn_sub_type"));
                    resultMap.put("txn_type", returnMap.get("txn_type"));
                    resultMap.put("version", returnMap.get("version"));
                    resultMap.put("trans_content", "模拟器"); //带上此字段币港湾gateway端不进行解密直接使用返回结果

                }else if ("SUCCESS".equals(type)) {
                    //成功
                    //	    {"additional_info":"云贷自主放款用户代扣还款","biz_type":"0000",
                    //	    	"data_type":"json","member_id":"912736","req_reserved":"",
                    //	    	"resp_code":"0000","resp_msg":"交易成功","succ_amt":"5056",
                    //	    	"terminal_id":"31009","trade_date":"20171109080217","trans_id":"9b9f6feeef0b60048971",
                    //	    	"trans_no":"201711090110001330174927","trans_serial_no":"9b9f6ff1b65c06140d68",
                    //	    	"txn_sub_type":"13","txn_type":"0431","version":"4.0.0.0"}

                    resultMap.put("additional_info", jsonDataContent.get("additional_info"));
                    resultMap.put("biz_type", jsonDataContent.get("biz_type"));
                    resultMap.put("data_type", returnMap.get("data_type"));
                    resultMap.put("member_id", returnMap.get("member_id"));
                    resultMap.put("req_reserved", jsonDataContent.get("req_reserved"));
                    resultMap.put("resp_code", "0000"); //返回码 0000 表示成功
                    resultMap.put("resp_msg", "交易成功"); //成功
                    resultMap.put("terminal_id", returnMap.get("terminal_id"));
                    resultMap.put("trade_date", DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_id", jsonDataContent.get("trans_id"));
                    resultMap.put("trans_no", "mn"+DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_serial_no", jsonDataContent.get("trans_serial_no"));
                    resultMap.put("txn_sub_type", jsonDataContent.get("txn_sub_type"));
                    resultMap.put("txn_type", returnMap.get("txn_type"));
                    resultMap.put("version", returnMap.get("version"));
                    resultMap.put("trans_content", "模拟器"); //带上此字段币港湾gateway端不进行解密直接使用返回结果

                }else if("PROCESS".equals(type)){
                    //处理中
                    resultMap.put("additional_info", jsonDataContent.get("additional_info"));
                    resultMap.put("biz_type", jsonDataContent.get("biz_type"));
                    resultMap.put("data_type", returnMap.get("data_type"));
                    resultMap.put("member_id", returnMap.get("member_id"));
                    resultMap.put("req_reserved", jsonDataContent.get("req_reserved"));
                    resultMap.put("resp_code", "BF00115"); //返回码
                    resultMap.put("resp_msg", "交易处理中");//交易处理中情况描述
                    resultMap.put("terminal_id", returnMap.get("terminal_id"));
                    resultMap.put("trade_date", DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_id", jsonDataContent.get("trans_id"));
                    resultMap.put("trans_serial_no", jsonDataContent.get("trans_serial_no"));
                    resultMap.put("txn_sub_type", jsonDataContent.get("txn_sub_type"));
                    resultMap.put("txn_type", returnMap.get("txn_type"));
                    resultMap.put("version", returnMap.get("version"));
                    resultMap.put("trans_content", "模拟器"); //带上此字段币港湾gateway端不进行解密直接使用返回结果

                }
            }else if("06".equals(jsonDataContent.get("txn_sub_type"))){
                //代扣查询
                if ("FAIL".equals(queryType)) {
                    //失败
                    //失败》{"additional_info":"赞分期用户代扣还款","biz_type":"0000",
                    //"data_type":"json","member_id":"912736","req_reserved":"",
                    //"resp_code":"BF00120","resp_msg":"报文交易要素缺失密文域中参数pay_code缺失",
                    //"terminal_id":"31009","trade_date":"20171109090509","trans_id":"9b9f708d3e5613021460",
                    //"trans_serial_no":"9b9f708ec2e0064ea768","txn_sub_type":"13","txn_type":"0431","version":"4.0.0.0"}

                    resultMap.put("additional_info", jsonDataContent.get("additional_info"));
                    resultMap.put("biz_type", jsonDataContent.get("biz_type"));
                    resultMap.put("data_type", returnMap.get("data_type"));
                    resultMap.put("member_id", returnMap.get("member_id"));
                    resultMap.put("req_reserved", jsonDataContent.get("req_reserved"));
                    resultMap.put("resp_code", "BF00101"); //返回码 自定义 BF00120 报文交易要素缺失密文域中参数pay_code缺失
                    resultMap.put("resp_msg", "报文交易要素缺失密文域中参数pay_code缺失");//失败情况描述
                    resultMap.put("terminal_id", returnMap.get("terminal_id"));
                    resultMap.put("trade_date", DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_id", jsonDataContent.get("orig_trans_id"));
                    resultMap.put("trans_serial_no", jsonDataContent.get("trans_serial_no"));
                    resultMap.put("txn_sub_type", jsonDataContent.get("txn_sub_type"));
                    resultMap.put("txn_type", returnMap.get("txn_type"));
                    resultMap.put("version", returnMap.get("version"));
                    resultMap.put("trans_content", "查询接口模拟器"); //带上此字段币港湾gateway端不进行解密直接使用返回结果

                }else if ("SUCCESS".equals(queryType)) {
                    //成功
                    //	    {"additional_info":"云贷自主放款用户代扣还款","biz_type":"0000",
                    //	    	"data_type":"json","member_id":"912736","req_reserved":"",
                    //	    	"resp_code":"0000","resp_msg":"交易成功","succ_amt":"5056",
                    //	    	"terminal_id":"31009","trade_date":"20171109080217","trans_id":"9b9f6feeef0b60048971",
                    //	    	"trans_no":"201711090110001330174927","trans_serial_no":"9b9f6ff1b65c06140d68",
                    //	    	"txn_sub_type":"13","txn_type":"0431","version":"4.0.0.0"}

                    resultMap.put("additional_info", jsonDataContent.get("additional_info"));
                    resultMap.put("biz_type", jsonDataContent.get("biz_type"));
                    resultMap.put("data_type", returnMap.get("data_type"));
                    resultMap.put("member_id", returnMap.get("member_id"));
                    resultMap.put("req_reserved", jsonDataContent.get("req_reserved"));
                    resultMap.put("resp_code", "0000"); //返回码 0000 表示成功
                    resultMap.put("resp_msg", "交易成功"); //成功
                    resultMap.put("terminal_id", returnMap.get("terminal_id"));
                    resultMap.put("trade_date", DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_id", jsonDataContent.get("orig_trans_id"));
                    resultMap.put("trans_no", "mn"+DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_serial_no", jsonDataContent.get("trans_serial_no"));
                    resultMap.put("txn_sub_type", jsonDataContent.get("txn_sub_type"));
                    resultMap.put("txn_type", returnMap.get("txn_type"));
                    resultMap.put("version", returnMap.get("version"));
                    resultMap.put("succ_amt", "100");
                    resultMap.put("trans_content", "查询接口模拟器"); //带上此字段币港湾gateway端不进行解密直接使用返回结果

                }else if("PROCESS".equals(queryType)){
                    //处理中
                    resultMap.put("additional_info", jsonDataContent.get("additional_info"));
                    resultMap.put("biz_type", jsonDataContent.get("biz_type"));
                    resultMap.put("data_type", returnMap.get("data_type"));
                    resultMap.put("member_id", returnMap.get("member_id"));
                    resultMap.put("req_reserved", jsonDataContent.get("req_reserved"));
                    resultMap.put("resp_code", "BF00115"); //返回码
                    resultMap.put("resp_msg", "交易处理中");//交易处理中情况描述
                    resultMap.put("terminal_id", returnMap.get("terminal_id"));
                    resultMap.put("trade_date", DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss"));
                    resultMap.put("trans_id", jsonDataContent.get("orig_trans_id"));
                    resultMap.put("trans_serial_no", jsonDataContent.get("trans_serial_no"));
                    resultMap.put("txn_sub_type", jsonDataContent.get("txn_sub_type"));
                    resultMap.put("txn_type", returnMap.get("txn_type"));
                    resultMap.put("version", returnMap.get("version"));
                    resultMap.put("trans_content", "查询接口模拟器"); //带上此字段币港湾gateway端不进行解密直接使用返回结果

                }
            }

		}

	    JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	
	/**
	 * 宝付请求模拟器
	 * @throws Exception
	 */
	@RequestMapping(value = "/baofoo/pay/{code}", method = RequestMethod.POST)
	@ResponseBody
	public String pay(HttpServletRequest request, @PathVariable String code) throws Exception {
		
		
		log.info("币港湾请求宝付接口:"+code);
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
	    log.info("币港湾请求宝付接口参数:"+returnMap);
		String data_content = (String)returnMap.get("data_content");
		
	    String cutPaymentcerpath = System.getProperty("user.dir")+"/src/main/webapp/resources/cer/bigangwan20171214.cer";
		//String cutPaymentcerpath = Globals.DAFY_OUT_DES_KEY;
		
	    data_content = RsaCodingUtil.decryptByPubCerFile(data_content, cutPaymentcerpath);
	    //解密返回值
	    data_content = SecurityUtil.Base64Decode(data_content);
	    log.info("币港湾请求宝付接口参数data_content解密:"+data_content);
	    
	    JSONObject jsonDataContent=JSONObject.fromObject(data_content);

	    Map resultMap = new HashMap();
	    
	    /*宝付代付模拟器
	     * 要使用此模拟器，更改gateway发起宝付代付请求URL地址
	     * ##代付模拟器请求地址 baofoo.cut.url = http://localhost:8098/simulator/baofoo/pay/
	     * */
	    
	    if("BF0040007".equals(code)){
	    	Map trans_content = new HashMap();
	    	Map trans_reqDatas = new HashMap();
	    	Map trans_reqData = new HashMap();
	    	
	    	
	    	Map trans_head = new HashMap();
	    	//trans_head.put("return_code", "0000"); //返回码 0000 表示成功
	    	trans_head.put("return_code", "0000"); //返回码 0000 表示成功
	    	trans_head.put("return_msg", "交易成功"); //成功
	    	
	    	//List<Map<String,Object>> rList = new ArrayList<>();
	    	
	    	trans_reqData.put("trans_orderid", 123);
		    trans_reqData.put("trans_batchid", 1234);
		    /**
		     * 0：转账中；
		     * 1：转账成功；
		     * -1：转账失败；
		     * 2：转账退款
		     */
		    trans_reqData.put("state", 0);
		    trans_reqData.put("trans_remark", "模拟器");
		    
		    trans_reqData.put("trans_no", "2");
		    trans_reqData.put("to_acc_name", "2");
		    trans_reqData.put("to_acc_no", "22");
		    trans_reqData.put("to_member_id", "222");
		    trans_reqData.put("trans_money", "111");
		    trans_reqData.put("trans_summary", "222");
		    
		    //rList.add(trans_reqData);
		    
		    List<Map<String,Object>> trans_reqDatas_list = new ArrayList<>();
		    
		    
		    trans_reqDatas.put("trans_reqData", trans_reqData);
		    trans_reqDatas_list.add(trans_reqDatas);
		    
		    trans_content.put("trans_reqDatas", trans_reqDatas_list);
		    
		    trans_content.put("trans_head", trans_head);
	    	
		    resultMap.put("trans_content", trans_content);
		}else if("BF0040001".equals(code)){
			Map trans_content = new HashMap();
			Map trans_reqDatas = new HashMap();
			Map trans_reqData = new HashMap();


			Map trans_head = new HashMap();
			trans_head.put("return_code", "0000"); //返回码 0000 表示成功
			trans_head.put("return_msg", "交易成功"); //成功

			//List<Map<String,Object>> rList = new ArrayList<>();

			trans_reqData.put("trans_orderid", 123);
			trans_reqData.put("trans_batchid", 1234);
			/**
			 * 0：转账中；
			 * 1：转账成功；
			 * -1：转账失败；
			 * 2：转账退款
			 */
			trans_reqData.put("state", 0);
			trans_reqData.put("trans_remark", "模拟器");

			trans_reqData.put("trans_no", "9bb7e822ac2f78331370");
			trans_reqData.put("to_acc_name", "2");
			trans_reqData.put("to_acc_no", "22");
			trans_reqData.put("to_member_id", "222");
			trans_reqData.put("trans_money", "111");
			trans_reqData.put("trans_summary", "222");

			//rList.add(trans_reqData);

			List<Map<String,Object>> trans_reqDatas_list = new ArrayList<>();


			trans_reqDatas.put("trans_reqData", trans_reqData);
			trans_reqDatas_list.add(trans_reqDatas);

			trans_content.put("trans_reqDatas", trans_reqDatas_list);

			trans_content.put("trans_head", trans_head);

			resultMap.put("trans_content", trans_content);
		}else if("BF0040002".equals(code)){
			Map trans_content = new HashMap();
			Map trans_reqDatas = new HashMap();
			Map trans_reqData = new HashMap();


			Map trans_head = new HashMap();
			trans_head.put("return_code", "0000"); //返回码 0000 表示成功
			trans_head.put("return_msg", "交易成功"); //成功

			//List<Map<String,Object>> rList = new ArrayList<>();

			trans_reqData.put("trans_orderid", 123);
			trans_reqData.put("trans_batchid", 1234);
			/**
			 * 0：转账中；
			 * 1：转账成功；
			 * -1：转账失败；
			 * 2：转账退款
			 */
			trans_reqData.put("state", 1);
			trans_reqData.put("trans_remark", "模拟器");

			JSONObject trans_content_json = JSONObject.fromObject(jsonDataContent.get("trans_content"));
			String trans_no = (String) JSONObject.fromObject(JSONArray.fromObject(JSONObject.fromObject(JSONArray.fromObject(trans_content_json.get("trans_reqDatas")).get(0)).get("trans_reqData")).get(0)).get("trans_no");
			log.info("trans_no=" + trans_no);
			trans_reqData.put("trans_no", trans_no);
			trans_reqData.put("to_acc_name", "2");
			trans_reqData.put("to_acc_no", "22");
			trans_reqData.put("to_member_id", "222");
			trans_reqData.put("trans_money", "111");
			trans_reqData.put("trans_summary", "222");

			//rList.add(trans_reqData);

			List<Map<String,Object>> trans_reqDatas_list = new ArrayList<>();


			trans_reqDatas.put("trans_reqData", trans_reqData);
			trans_reqDatas_list.add(trans_reqDatas);

			trans_content.put("trans_reqDatas", trans_reqDatas_list);

			trans_content.put("trans_head", trans_head);

			resultMap.put("trans_content", trans_content);
		}
	    
	    JSONObject jsonObject = JSONObject.fromObject(resultMap);
		return jsonObject.toString();
	}
	
	@RequestMapping(value = "/baofoo/fileLoadNewRequest", method = RequestMethod.GET)
	@ResponseBody
	public String fileLoadNewRequest(HttpServletRequest request) throws Exception {
		 log.info("==========【币港湾请求下载宝付出入账文件】开始==========================");
		 String file_type = (String)request.getParameter("file_type");
		 String member_id = (String)request.getParameter("member_id");
		 log.info("==========【币港湾请求下载宝付出入账文件】文件类型"+file_type+"==========================");
		 String file_address = "";
		 if ("fi".equals(file_type)) {
			 file_address = "D://self//resourse//fi//"+member_id+"_in.zip";
		 }else {
			 file_address = "D://self//resourse//fo//"+member_id+"_out.zip";
		 }
		 File file=new File(file_address);
		 byte[] buffer = null;  
	        try {  
	            FileInputStream fis = new FileInputStream(file);  
	            ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);  
	            byte[] b = new byte[1000];  
	            int n;  
	            while ((n = fis.read(b)) != -1) {  
	                bos.write(b, 0, n);  
	            }  
	            fis.close();  
	            bos.close();  
	            buffer = bos.toByteArray();  
	        } catch (FileNotFoundException e) {  
	            e.printStackTrace();  
	        } catch (IOException e) {  
	            e.printStackTrace();  
	        }  
	        String a=SecurityUtil.byte2Base64String(buffer);
	        log.info("==========【币港湾请求下载宝付出入账文件】获得文件字符串"+a+"==========================");
	        String b = "resp_code=0000&resp_msg=交易成功&resp_body=";
	        log.info("==========【币港湾请求下载宝付出入账文件】结束==========================");
	        return b+a;  
	        
	}
}
