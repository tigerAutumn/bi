package com.dafy.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dafy.core.constant.Globals;
import com.dafy.core.util.DESUtil;
import com.dafy.core.util.DateUtil;
import com.dafy.core.util.ExportUtil;
import com.dafy.core.util.StringUtil;
import com.dafy.core.util.encrypt.MD5Util;
import com.dafy.core.util.excel.ExcelUtil;
import com.dafy.core.util.excel.POIExcel;
import com.dafy.model.vo.loan7.QueryBillResModel;
import com.dafy.model.vo.loan7.Repayments;
import com.dafy.model.vo.loan7.RepayPlan;
import com.dafy.service.Loan7Service;
import com.dafy.tools.loan7.Loan7InConstant;
import com.dafy.tools.loan7.Loan7RepayPlanUtil;
import com.dafy.tools.loan7.RepayPlanUtil;


/**
 * 
 * 7贷模拟器
 *
 */
@Controller
public class Loan7Controller {
	private static final Logger log = LoggerFactory.getLogger(Loan7Controller.class);
	
	@Autowired
	private Loan7Service loan7Service;

	/**
	 * 生成7贷账单
	 * 必须参数：
	 * @param request
	 * @param response
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/loan7/exportLoan7Bill", method = RequestMethod.GET)
	public void exportUserRechange(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		
		String successDate = request.getParameter("successDate");
		String repayDay = request.getParameter("repayDay"); //还款日
		String period = request.getParameter("period");
		String loanMoney = request.getParameter("loanMoney");
		String yearInterestRate = request.getParameter("yearInterestRate");
		
		List<RepayPlan> listRepays = new ArrayList<RepayPlan>();
		
		
		repayDay = "14"; //默认还款还款日为14号
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    // 借款成功日期
	    Date successTime = format.parse(successDate);
	    period = "90";//借款期数，单位: 天
	     
	    yearInterestRate = "0.437";
		listRepays = RepayPlanUtil.calculateRepayPlan(Integer.valueOf(repayDay), successTime, Integer.valueOf(period), Long.valueOf(loanMoney), Double.valueOf(yearInterestRate));
		
		List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
		//设置标题
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("期数");
		titles.add("本期计息天数");
		titles.add("还款日");
		titles.add("本金");
		titles.add("利息");
		titleMap.put("title", titles);
		list.add(titleMap);
		
		
		if (listRepays != null && !listRepays.isEmpty()) {
			int i = 0;
			for (RepayPlan repayPlan : listRepays) {
				Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(repayPlan.getPeriod());
				obj.add(repayPlan.getDays());
				obj.add(new SimpleDateFormat("yyyy-MM-dd").format(repayPlan.getDate()));
				obj.add(repayPlan.getPrincipal());
				obj.add(repayPlan.getInterest());
				datasMap.put("user" + (++i), obj);
				list.add(datasMap);
			}
			Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
			List<Object> obj = new ArrayList<Object>();
			obj.add("账单期数");
			obj.add(listRepays.size());
			obj.add("计息日");
			obj.add(new SimpleDateFormat("yyyy-MM-dd").format(successTime));
			obj.add(0);
			datasMap.put("user" + (++i), obj);
			
			list.add(datasMap);
		}
		try {
			ExportUtil.exportExcel(response, request, "7d_bill", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 生成7贷账单-随借随还
	 * 必须参数：
	 * @param request
	 * @param response
	 * @throws ParseException
	 */
	@RequestMapping(value = "/loan7/exportLoan7BillRepayAnyTime", method = RequestMethod.GET)
	public void exportLoan7BillRepayAnyTime(HttpServletRequest request, HttpServletResponse response) throws ParseException {

		String successDate = request.getParameter("successDate");
		String loanMoney = request.getParameter("loanMoney");

		List<RepayPlan> listRepays = new ArrayList<RepayPlan>();

		String repayDay = "28"; //最大还款日为28日
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    // 借款成功日期
	    Date successTime = format.parse(successDate);
	    String period = "90";//借款期数，单位: 天

	    String successDay = DateUtil.formatDateTime(successTime,"dd");
	    if(Integer.valueOf(successDay) <28){
	    	repayDay = successDay;
	    }

	    String yearInterestRate = "0.36";

	    listRepays = Loan7RepayPlanUtil.calculateRepayPlan( successTime, Long.valueOf(loanMoney), Double.valueOf(yearInterestRate));

		List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
		//设置标题
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("期数");
		titles.add("本期计息天数");
		titles.add("还款日");
		titles.add("本金");
		titles.add("利息");
		titleMap.put("title", titles);
		list.add(titleMap);


		if (listRepays != null && !listRepays.isEmpty()) {
			int i = 0;
			for (RepayPlan repayPlan : listRepays) {
				Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(repayPlan.getPeriod());
				obj.add(repayPlan.getDays());
				obj.add(new SimpleDateFormat("yyyy-MM-dd").format(repayPlan.getDate()));
				obj.add(repayPlan.getPrincipal());
				obj.add(repayPlan.getInterest());
				datasMap.put("user" + (++i), obj);
				list.add(datasMap);
			}
			Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
			List<Object> obj = new ArrayList<Object>();
			obj.add("账单期数");
			obj.add(listRepays.size());
			obj.add("计息日");
			obj.add(new SimpleDateFormat("yyyy-MM-dd").format(successTime));
			obj.add(0);
			datasMap.put("user" + (++i), obj);

			list.add(datasMap);
		}
		try {
			ExportUtil.exportExcel(response, request, "7d_bill_repay_time", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	/**
	 * 根据原始账单重新计算7贷账单
	 * 必须参数：
	 * @param request
	 * @param response
	 * @throws Exception 
	 */
	@RequestMapping(value = "/loan7/calculateLoan7Bill", method = RequestMethod.GET)
	public void calculateLoan7Bill(HttpServletRequest request, HttpServletResponse response) throws Exception {
		

		String repayDay = request.getParameter("repayDay"); //还款日 默认还款还款日为14号
		String period = request.getParameter("period"); //借款期数，单位: 天
		String yearInterestRate = request.getParameter("yearInterestRate");//借款 利率
		
		String repayAmount = request.getParameter("repayAmount"); //还款金额
		long repayAmountLong = Long.valueOf(repayAmount);
 		String repayTime = request.getParameter("repayTime"); //还款时间
		
		
		repayDay = "14"; //默认还款还款日为14号
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    // 借款成功日期
	    period = "90";//借款期数，单位: 天
	     
	    yearInterestRate = "0.437"; //借款 利率
	    
	    //读取原始账单
        Map<String, List<List<String>>> map = new POIExcel()
        .read("D:\\self\\calBill\\7d_bill.xls");
		List<List<String>>  billList= map.get("7贷账单");
		List<String> end =  billList.get(billList.size()-1);
		Integer billPeriod = Integer.valueOf(end.get(1));
		Integer oldSize = billList.size();
		
		
		//计算账单计算位置
		List<RepayPlan> needCalRepayPlans = new ArrayList<RepayPlan>();
		Integer start = ((billList.size()/(billPeriod+2))-1 )* (billPeriod+2) +1 ;
		
		//讲读取的数据转成对账（待计算账单）
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		for(int i= 0;i < billPeriod ;i++) {
			Calendar calendar = Calendar.getInstance();
			
			Date date = sdf.parse(billList.get(start+i).get(2));
			calendar.setTime(date);
			RepayPlan plan = new RepayPlan(Integer.valueOf(billList.get(start+i).get(0)), calendar.getTimeInMillis(), Long.valueOf(billList.get(start+i).get(1)));
			plan.setPrincipal(Long.valueOf(billList.get(start+i).get(3)));
			plan.setInterest(Long.valueOf(billList.get(start+i).get(4)));
			needCalRepayPlans.add(plan);
		}
		//上次计息开始时间
		Date interestStartDate = sdf.parse(end.get(3));
		//原账单本金
		long oldPrincipal = needCalRepayPlans.get(billPeriod-1).getPrincipal();
		
		
		SimpleDateFormat nowSdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = nowSdf.parse(DateUtil.formatYYYYMMDD(new Date()));
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		
		//还款日期
		Calendar repayDateCalendar = Calendar.getInstance();
		repayDateCalendar.setTime(DateUtil.parseDate(repayTime));
		
		
		
		
		long leftPrincipal = 0; //剩余本金
		long repayPeriod = 1; //当前还款期数
		long genInterest = 0;
		Calendar calInterestCalendar = Calendar.getInstance();
		calInterestCalendar.setTime(interestStartDate);
		long between_days=0;
		if ("0".equals(end.get(4))) {
			between_days = (repayDateCalendar.getTimeInMillis() - calInterestCalendar.getTimeInMillis())/(1000*3600*24) + 1;  
		}else {
			between_days = (repayDateCalendar.getTimeInMillis() - calInterestCalendar.getTimeInMillis())/(1000*3600*24);  
		}
		
		genInterest = new Double(
                Math.ceil(oldPrincipal * Double.valueOf(yearInterestRate) / 365)).longValue() * between_days;
		leftPrincipal = oldPrincipal - (repayAmountLong -genInterest);
		
		if (repayDateCalendar.getTimeInMillis() <= needCalRepayPlans.get(0).getDate()) {
			//第一期提前还款
			//计算账单   知道已还金额的还款本金和还款利息，知道新计息本金，根据新的计息本金计算账单金额
			repayPeriod = 1;
			if (genInterest > Long.valueOf(repayAmount)) {
				throw new Exception("第一期还款，还款金额必须大于当前计息应还金额"+genInterest);
			}
			
		}else if (calendar.getTimeInMillis() <= needCalRepayPlans.get(1).getDate()) {
			//第二期提前还款
			repayPeriod = 2;
			if (genInterest > Long.valueOf(repayAmount)) {
				throw new Exception("第二期还款，还款金额必须大于当前计息应还金额"+genInterest);
			}
		}else if (calendar.getTimeInMillis() <= needCalRepayPlans.get(2).getDate()) {
			//第二期提前还款
			repayPeriod = 3;
			if (genInterest > Long.valueOf(repayAmount)) {
				throw new Exception("第三期还款，还款金额必须大于当前计息应还金额"+genInterest);
			}
		}else {
			repayPeriod = 4;
			if (genInterest > Long.valueOf(repayAmount)) {
				throw new Exception("第四期还款，还款金额必须大于当前计息应还金额"+genInterest);
			}
		}
		
		List<RepayPlan> listRepays = new ArrayList<RepayPlan>();
		for (RepayPlan needRepayPlan : needCalRepayPlans) {
			long interestResult = 0;
			
			if (needRepayPlan.getPeriod() == repayPeriod) {
				//计算计息剩余天数，并且通过计息剩余天数计算剩余利息
				long left_days=(needRepayPlan.getDate() - repayDateCalendar.getTimeInMillis())/(1000*3600*24);  
				interestResult = new Double(
	                    Math.ceil(leftPrincipal * Double.valueOf(yearInterestRate) / 365)).longValue() * left_days;
			}else if (needRepayPlan.getPeriod() < repayPeriod) {
				interestResult = 0;
			}else {
				interestResult = new Double(
	                    Math.ceil(leftPrincipal * Double.valueOf(yearInterestRate) / 365)).longValue() * needRepayPlan.getDays();
			}
			RepayPlan resultRepayPlan = new RepayPlan(needRepayPlan.getPeriod(), needRepayPlan.getDate(), needRepayPlan.getDays());
			if (needRepayPlan.getPeriod() == billPeriod) {
				resultRepayPlan.setPrincipal(leftPrincipal);
			}else {
				resultRepayPlan.setPrincipal(needRepayPlan.getPrincipal());
			}
			
			resultRepayPlan.setInterest(interestResult);
			listRepays.add(resultRepayPlan);
		}
	    
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File("D:\\self\\calBill\\7d_bill.xls");
			String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
			//打开excel
			fis = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheetHead = wb.getSheetAt(0); //单据头
			
			HSSFCellStyle basicStyle = ExcelUtil.getBasicStyle(wb);
			HSSFCellStyle textCellStyle = ExcelUtil.getTextStyle(wb);
			HSSFCellStyle smallCellStyle = ExcelUtil.getSmallStyle(wb);
			HSSFCellStyle dateCellStyle = ExcelUtil.getDateStyle(wb);
			
			int base = oldSize +1;
			
			HSSFRow headRow = sheetHead.createRow(oldSize);
			Cell head_cell_0 = headRow.createCell(0); 
			head_cell_0.setCellStyle(basicStyle);
			head_cell_0.setCellValue("期数");
			Cell head_cell_1 = headRow.createCell(1);
			head_cell_1.setCellStyle(basicStyle);
			head_cell_1.setCellValue("本息计息天数");
			Cell head_cell_2 = headRow.createCell(2); 
			head_cell_2.setCellStyle(basicStyle);
			head_cell_2.setCellValue("还款日");
			Cell head_cell_3 = headRow.createCell(3); 
			head_cell_3.setCellStyle(basicStyle);
			head_cell_3.setCellValue("本金");
			Cell head_cell_4 = headRow.createCell(4); 
			head_cell_4.setCellStyle(basicStyle);
			head_cell_4.setCellValue("利息");
			
			for(int i=0;i<listRepays.size();i++) {
				RepayPlan vo = listRepays.get(i);
				
				HSSFRow contRow = sheetHead.createRow(base+i);
				Cell cont_cell_0 = contRow.createCell(0); 
				cont_cell_0.setCellStyle(basicStyle);
				cont_cell_0.setCellValue(vo.getPeriod());
				Cell cont_cell_1 = contRow.createCell(1); 
				cont_cell_1.setCellStyle(basicStyle);
				cont_cell_1.setCellValue(vo.getDays());
				Cell cont_cell_2 = contRow.createCell(2); 
				cont_cell_2.setCellStyle(dateCellStyle);
				cont_cell_2.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(vo.getDate()));
				Cell cont_cell_3 = contRow.createCell(3); 
				cont_cell_3.setCellStyle(basicStyle);
				cont_cell_3.setCellValue(vo.getPrincipal());
				Cell cont_cell_4 = contRow.createCell(4); 
				cont_cell_4.setCellStyle(basicStyle);
				cont_cell_4.setCellValue(vo.getInterest());
			}
			
			HSSFRow contRow = sheetHead.createRow(base+listRepays.size());
			Cell cont_cell_0 = contRow.createCell(0); 
			cont_cell_0.setCellStyle(basicStyle);
			cont_cell_0.setCellValue("账单期数");
			Cell cont_cell_1 = contRow.createCell(1); 
			cont_cell_1.setCellStyle(basicStyle);
			cont_cell_1.setCellValue(billPeriod);
			Cell cont_cell_2 = contRow.createCell(2); 
			cont_cell_2.setCellStyle(basicStyle);
			cont_cell_2.setCellValue("计息日");
			Cell cont_cell_3 = contRow.createCell(3); 
			//如果还款日<开始计息日期  证明已经还过，日还是还款日
			if(repayDateCalendar.getTimeInMillis() < calInterestCalendar.getTimeInMillis()){
				cont_cell_3.setCellStyle(dateCellStyle);
				repayDateCalendar.add(Calendar.DATE, 1); //日期加1天  
				cont_cell_3.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(repayDateCalendar.getTime()));
			}else {
				cont_cell_3.setCellStyle(dateCellStyle);
				cont_cell_3.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(repayDateCalendar.getTime()));
			}
			Cell cont_cell_4 = contRow.createCell(4); 
			cont_cell_4.setCellStyle(basicStyle);
			cont_cell_4.setCellValue(Integer.valueOf(end.get(4))+1);
			
			Cell cont_cell_5 = contRow.createCell(5); 
			cont_cell_5.setCellStyle(basicStyle);
			cont_cell_5.setCellValue(repayAmountLong);
			
			Cell cont_cell_6 = contRow.createCell(6); 
			cont_cell_6.setCellStyle(basicStyle);
			cont_cell_6.setCellValue(genInterest);
			
			Cell cont_cell_7 = contRow.createCell(7); 
			cont_cell_7.setCellStyle(basicStyle);
			cont_cell_7.setCellValue(repayAmountLong -genInterest);
			
			Cell cont_cell_8 = contRow.createCell(8); 
			cont_cell_8.setCellStyle(basicStyle);
			cont_cell_8.setCellValue(leftPrincipal);
			
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
	 *
	 * @throws Exception
	 */
	@RequestMapping(value = "/loan7/{code}")
	@ResponseBody
	public String login(HttpServletRequest request, @PathVariable String code) throws Exception {
		// DES解密，获得明文
		System.out.println("aa");
		String decryptData = new DESUtil(Globals.LOAN7_OUT_DES_KEY).decryptStr(request.getParameter("DATA").replace(" ", "+"));
		JSONObject json = JSON.parseObject(decryptData);
		log.debug("============请求报文明文："+code+"【" + decryptData + "】============");
		String transCode = json.get("transCode").toString();
		String clientKey = json.get("clientKey").toString();
		//String clientSecret = json.get("clientSecret").toString();
		String requestTime = json.get("requestTime").toString();
		String hash = json.get("hash").toString();
		
		//重新组装hash，验证hash是否正确
		StringBuffer buffer = new StringBuffer("transCode="+transCode);
		buffer.append("&clientKey="+clientKey);
		//buffer.append("&clientSecret="+clientSecret);
		buffer.append("&requestTime="+requestTime);
		log.debug("============hash字段明文：【" + buffer + "】============");
		String newHash = MD5Util.encryptMD5(buffer.toString());
		log.debug("============hash字段密文：【" + newHash + "】============");
		
		String responseTime = DateUtil.format(new Date());
		Map<String,Object> map = new HashMap<String,Object>();
		String respCode = "";
		String respMsg = "";
		String encryptHash = "";

		if ("login".equals(transCode)) {
			respCode = Loan7InConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
			map.put("token", "dafyLoanLoginToken001");
		}else if ("waitFill".equals(transCode)) {
			respCode = Loan7InConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
		}else if ("queryBill".equals(transCode)) {
			String userId = json.get("userId").toString();
			String loanId = json.get("loanId").toString();
			
			QueryBillResModel queryBillResModel = loan7Service.queryBill(loanId);
			
			respCode = Loan7InConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
			map.put("userId", queryBillResModel.getUserId());
			map.put("loanId", queryBillResModel.getLoanId());
			map.put("agreementNo", queryBillResModel.getAgreementNo());
			map.put("agreementUrl", queryBillResModel.getAgreementUrl());
			ArrayList<HashMap<String, Object>>  listMap = new ArrayList<HashMap<String, Object>>();
			for (Repayments repayment : queryBillResModel.getRepayments()) {
				HashMap<String, Object> maps = new HashMap<>();
				maps.put("repayId", repayment.getRepayId());
				maps.put("status", repayment.getStatus());
				SimpleDateFormat time=new SimpleDateFormat("yyyy-MM-dd"); 
				maps.put("repayDate", time.format(repayment.getRepayDate()));
				maps.put("repaySerial", repayment.getRepaySerial());
				maps.put("total", repayment.getTotal() );
				maps.put("principal", repayment.getPrincipal());
				maps.put("interest", repayment.getInterest());
				maps.put("principalOverdue", repayment.getPrincipalOverdue() );
				maps.put("interestOverdue", repayment.getInterestOverdue() );
				maps.put("reservedField1", repayment.getReservedField1());
				maps.put("bgwOrderNo", repayment.getBgwOrderNo());
				listMap.add(maps);
			}
			map.put("repayments", listMap);
			
		}else {
			respCode = Loan7InConstant.RETURN_CODE_SUCCESS;
			respMsg = "交易成功";
			map.put("respCode", respCode);
			map.put("respMsg", respMsg);
			map.put("responseTime", responseTime);
			map.put("hash", encryptHash);
		}

		
		String message = JSON.toJSONString(map);
		log.debug("============响应报文：【" + message + "】============");
		// 对json进行DES加密
		String ciphertext = new DESUtil(Globals.LOAN7_IN_DES_KEY).encryptStr(message);
		log.debug("============报文转换密文：【" + ciphertext + "】============");
		return ciphertext;
	}
	
	
	
	
	
	/**
	 * 生成7贷账单
	 * 必须参数：
	 * @param request
	 * @param response
	 * @throws ParseException 
	 */
	@RequestMapping(value = "/loan7/exportLoan7BillNew", method = RequestMethod.GET)
	public void exportLoan7BillNew(HttpServletRequest request, HttpServletResponse response) throws ParseException {
		
		String successDate = request.getParameter("successDate");
		String loanMoney = request.getParameter("loanMoney");
		String yearInterestRate = request.getParameter("yearInterestRate");
		
		List<RepayPlan> listRepays = new ArrayList<RepayPlan>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
	    // 借款成功日期
	    Date successTime = format.parse(successDate);
	    yearInterestRate = "0.437";
		listRepays = Loan7RepayPlanUtil.calculateRepayPlan( successTime, Long.valueOf(loanMoney), Double.valueOf(yearInterestRate));
		
		List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
		//设置标题
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("期数");
		titles.add("本期计息天数");
		titles.add("还款日");
		titles.add("本金");
		titles.add("利息");
		titleMap.put("title", titles);
		list.add(titleMap);
		
		
		if (listRepays != null && !listRepays.isEmpty()) {
			int i = 0;
			for (RepayPlan repayPlan : listRepays) {
				Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(repayPlan.getPeriod());
				obj.add(repayPlan.getDays());
				obj.add(new SimpleDateFormat("yyyy-MM-dd").format(repayPlan.getDate()));
				obj.add(repayPlan.getPrincipal());
				obj.add(repayPlan.getInterest());
				datasMap.put("user" + (++i), obj);
				list.add(datasMap);
			}
			Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
			List<Object> obj = new ArrayList<Object>();
			obj.add("账单期数");
			obj.add(listRepays.size());
			obj.add("计息日");
			obj.add(new SimpleDateFormat("yyyy-MM-dd").format(successTime));
			obj.add(0);
			datasMap.put("user" + (++i), obj);
			
			list.add(datasMap);
		}
		try {
			ExportUtil.exportExcel(response, request, "7d_bill_new", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	public static void main(String[] args) {
		String a = "Zmer6Z6id7oOlhvB6bjXxaTr6z+dLsHo4njW/EEd0pz0pKDQMJp8r0bQ0CSAakh1xjrd592egLxb653IfpeIcQh4+I8rkb7ebSUWbR+yVN0WjahVWPwNMdOfLBpHRUc/keFzi0gvYAV0AiHyo0V6fUTNoWk1mox7Y/p5trBiITnH0dUUp4W+FwoNxCQWS9xcwVfJDmsodWtnYKM9FacviTaCeJuc+hJw+AXStfGR/6qUGTXsoGmcc48q5ZWcwGHQ9+KJDzQPv0JNWMBgNs31lZA50Pw8plEpVUUkmqnih0eIwYZ9RBW7wg==";
	
		String decryptData = new DESUtil(Globals.LOAN7_OUT_DES_KEY).decryptStr(a.replace(" ", "+"));
	}
}
