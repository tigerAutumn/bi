package com.pinting.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.model.vo.CorpusBuyStatisticsVO;
import com.pinting.business.model.vo.HfbankCustomerWithdrawalVO;
import com.pinting.business.model.vo.LnLoanServiceFeeVO;
import com.pinting.business.model.vo.PayFinanceStatisticsVO;
import com.pinting.business.model.vo.UserRechanageStatisticsVO;
import com.pinting.business.service.manage.DepMFinanceStatisticsService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;
/**
 * 存管财务统计（会记分录）相关
 * @project manage-java
 * @title DepFinanceStatisticsController.java
 * @author Dragon & cat
 * @date 2017-10-19
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Controller
@RequestMapping("depFinanceStatistics")
public class DepFinanceStatisticsController {
	
	@Autowired
	private DepMFinanceStatisticsService financeService;
	
	//============================== 恒丰客户充值统计S =====================================================
	/**
	 * 恒丰客户充值统计
	 * 用户充值到币港湾的钱，目前存管系统只能通过充值进入系统，原来的购买在新系统中已不存在
	 * 用户充值数据，体现为订单表中充值记录（TOP_UP）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryUserRechange")
	public String userRechangeStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
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
		//获得用户充值记录
		List<UserRechanageStatisticsVO> userList = financeService.userRechangeStatistics(userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.userRechangeStatisticsCount(userName, mobile, startTime, endTime);
		Double totalAmount = financeService.userTotalRechangeAmountStatistics(userName, mobile, startTime, endTime);
		//将数据返回给页面
		model.put("totalAmount", totalAmount);
		model.put("userList", userList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		
		return "/depFinanceStatistics/user_rechange_statistics";
	}
	
	@RequestMapping("/exportUserRechange")
	public void exportUserRechange(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		//需要导出的数据
		Integer count = financeService.userRechangeStatisticsCount(userName, mobile, startTime, endTime);
		List<UserRechanageStatisticsVO> userList = financeService.userRechangeStatistics(userName, mobile, startTime, endTime, 1, count);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.user.rechange.excel"));
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
				UserRechanageStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("10"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("010"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());
				Cell head_cell_3 = headRow.createCell(3); //账户（账户是文本字段,直接写恒丰或宝付即可）
				head_cell_3.setCellStyle(dateCellStyle);
				head_cell_3.setCellValue("恒丰");
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("10"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("010"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				Cell body_cell_3 = bodyRow.createCell(3); //客户姓名
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());
				Cell body_cell_4 = bodyRow.createCell(4); //部门
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue("101");
				Cell body_cell_5 = bodyRow.createCell(5); //充值金额
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getAmount());
				Cell body_cell_6 = bodyRow.createCell(6); //充值金额
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue(0);
				Cell body_cell_7 = bodyRow.createCell(7); //实收金额
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getAmount());
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
	//============================== 恒丰客户充值统计E =====================================================
	
	
	
	//============================== 存管云贷，赞时贷 本金购买统计S =====================================================
	/**
	 * 存管云贷，赞时贷本金购买
	 * 本金购买统计的数据是用户购买币港湾产品的数据，包含卡购买和余额购买
	 * 数据体现为订单表中卡购买和余额购买的数据（CARD_BUY_PRODUCT和BALANCE_BUY_PRODUCT）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryYunZSDCorpusBuy")
	public String corpusBuyStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		String userType = request.getParameter("userType");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		if(StringUtil.isBlank(partnerCode)){
			partnerCode = "YUN_DAI_SELF";
		}
		
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
		List<CorpusBuyStatisticsVO> userList = financeService.yunZSDCorpusBuyStatistics(userType, userName, mobile, startTime, endTime, partnerCode, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.yunZSDCorpusBuyStatisticsCount(userType, userName, mobile, startTime, endTime, partnerCode);
		Double totalCorpusBuy = financeService.yunZSDCorpusBuyStatisticsSumBalance(userType, userName, mobile, startTime, endTime, partnerCode);
		//将数据返回给页面
		model.put("totalCorpusBuy", totalCorpusBuy);
		model.put("userList", userList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userType", userType);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("partnerCode", partnerCode);
		
		return "/depFinanceStatistics/corpus_buy";
	}
	
	/**
	 * 存管云贷，赞时贷本金购买-导出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/exportYunZSDCorpusBuy")
	public void exportCorpusBuy(HttpServletRequest request, HttpServletResponse response) {
		String partnerCode = request.getParameter("partnerCode");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String userType = request.getParameter("userType");
		if(StringUtil.isBlank(partnerCode)){
			partnerCode = "YUN_DAI_SELF";
		}
		//需要导出的数据
		Integer count = financeService.yunZSDCorpusBuyStatisticsCount(userType, userName, mobile, startTime, endTime, partnerCode);
		List<CorpusBuyStatisticsVO> userList = financeService.yunZSDCorpusBuyStatistics(userType, userName, mobile, startTime, endTime, partnerCode, 1, count);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.corpus.buy.excel"));
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
				CorpusBuyStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("020"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue(vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("020"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //投资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				
				Cell body_cell_3 = bodyRow.createCell(3); //投资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());
				
				Cell body_cell_4 = bodyRow.createCell(4); //部门默认101
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue("101");
				
				Cell body_cell_5 = bodyRow.createCell(5); //投资金额
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getBalance());
				
			}
			
			ExportUtil.exportExcel(wb, fileName+partnerCode, response, request);
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
	

	//============================== 存管云贷，赞时贷 支付融资客户 S =====================================================
	/**
	 * 存管云贷，赞时贷 支付融资客户
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPayFinance")
	public String queryPayFinance(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		if(StringUtil.isBlank(partnerCode)){
			partnerCode = "YUN_DAI_SELF";
		}
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String userType = request.getParameter("userType");

		String partnerBusinessFlag = request.getParameter("partnerBusinessFlag");
		if(StringUtil.isBlank(partnerBusinessFlag)){
			partnerBusinessFlag = "777";
		}

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

		//获得用户融资记录
		List<PayFinanceStatisticsVO> userList = financeService.payFinanceYunZSDStatistics(userType, userName, mobile, startTime, endTime, partnerCode, partnerBusinessFlag, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.payFinanceYunZSDStatisticsCount(userType, userName, mobile, startTime, endTime, partnerCode, partnerBusinessFlag);
		Double totalBalance = financeService.payFinanceYunZSDTotalAmount(userType, userName, mobile, startTime, endTime, partnerCode, partnerBusinessFlag);

		//将数据返回给页面
		model.put("totalBalance", totalBalance);
		model.put("userList", userList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("userType",userType);
		model.put("partnerCode", partnerCode);
		model.put("partnerBusinessFlag", partnerBusinessFlag);

		return "/depFinanceStatistics/pay_finance_statistics";
	}

	/**
	 * 存管云贷，赞时贷 支付融资客户导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportPayFinance")
	public void exportPayFinance(HttpServletRequest request, HttpServletResponse response) {
		String partnerCode = request.getParameter("partnerCode");
		if(StringUtil.isBlank(partnerCode)){
			partnerCode = "YUN_DAI_SELF";
		}
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String userType = request.getParameter("userType");
		String partnerBusinessFlag = request.getParameter("partnerBusinessFlag");
		if(StringUtil.isBlank(partnerBusinessFlag)){
			partnerBusinessFlag = "777";
		}
		//需要导出的数据
		List<PayFinanceStatisticsVO> userList = financeService.payFinanceYunZSDStatistics(userType, userName, mobile, startTime, endTime, partnerCode, partnerBusinessFlag, 1, Integer.MAX_VALUE);

		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.yunself.pay.finance.excel"));
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
				PayFinanceStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("030"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("030"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("030"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("030"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //融资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getPropertyCode());

				Cell body_cell_3 = bodyRow.createCell(3); //融资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getLnUserName());
				
				Cell body_cell_4 = bodyRow.createCell(4); //部门默认101
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue("101");

				Cell body_cell_5 = bodyRow.createCell(5); //融资金额
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getBalance());

			}

			ExportUtil.exportExcel(wb, fileName+partnerCode, response, request);
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

	//============================== 支付融资客户 E =====================================================


	//============================== 恒丰、宝付客户支取统计 S ====================================================

	/**
	 * 恒丰客户支取统计
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/hfbankWithdrawal")
	public String HfbankWithdrawal(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime = StringUtil.isBlank(startTime) == true ? yestoday : startTime;
		endTime=StringUtil.isBlank(endTime) == true ? yestoday : endTime;

		//获得恒丰客户支取数据
		List<HfbankCustomerWithdrawalVO> userWithdrawalList = financeService.queryHfbankWithdrawalList(userName, startTime,
				endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.queryHfbankWithdrawalCount(userName, startTime, endTime);
		//支取总金额
		Double takeTotalAmount = financeService.queryHfbankWithdrawalSumAmount(userName, startTime, endTime);
		//手续费总额
		Double totalFee = financeService.queryHfbankWithdrawalSumFee(userName, startTime, endTime);

		//将数据返回给页面
		model.put("takeTotalAmount", takeTotalAmount);
		model.put("totalFee", totalFee);
		model.put("userWithdrawalList", userWithdrawalList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		return "/depFinanceStatistics/hfbank_withdrawal_index";
	}

	/**
	 * 恒丰客户支取统计 导出excel
	 * @param request
	 * @param response
     */
	@RequestMapping("/exportHfbankWithdrawal")
	public void exportHfbankWithdrawal(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime = StringUtil.isBlank(startTime) == true ? yestoday : startTime;
		endTime=StringUtil.isBlank(endTime) == true ? yestoday : endTime;

		//需要导出的数据
		Integer count = financeService.queryHfbankWithdrawalCount(userName, startTime, endTime);
		//获得恒丰客户支取数据
		List<HfbankCustomerWithdrawalVO> userWithdrawalList = financeService.queryHfbankWithdrawalList(userName, startTime,
				endTime, 1, count);
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.hfbank.withdrawal.excel"));
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
			for(int i=0;i<userWithdrawalList.size();i++) {
				HfbankCustomerWithdrawalVO vo = userWithdrawalList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("808"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("080"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getUpdateTime());

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("808"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("080"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				Cell body_cell_3 = bodyRow.createCell(3); //客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());

				Cell body_cell_4 = bodyRow.createCell(4); //客户提现金额
				body_cell_4.setCellStyle(smallCellStyle);
				body_cell_4.setCellValue(vo.getAmount() == null ? 0d : vo.getAmount());

				Cell body_cell_5 = bodyRow.createCell(5); //提现手续费
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getDoneFee() == null ? 0d : vo.getDoneFee());

				Cell body_cell_6 = bodyRow.createCell(6); //客户实际到账金额
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue(vo.getRevenueAmount() == null ? 0d : vo.getRevenueAmount());
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
	 * 宝付客户支取统计
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/baofooWithdrawal")
	public String baofooWithdrawal(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String transType = request.getParameter("transType");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime = StringUtil.isBlank(startTime) == true ? yestoday : startTime;
		endTime=StringUtil.isBlank(endTime) == true ? yestoday : endTime;

		//获得宝付客户支取数据
		List<HfbankCustomerWithdrawalVO> userWithdrawalList = financeService.queryBaofooWithdrawalList(userName, startTime,
				endTime,transType, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.queryBaofooWithdrawalCount(userName, startTime, endTime,transType);
		//支取总金额
		Double takeTotalAmount = financeService.queryBaofooWithdrawalSumAmount(userName, startTime, endTime,transType);
		//手续费总额
		Double totalFee = financeService.queryBaofooWithdrawalSumFee(userName, startTime, endTime,transType);

		//将数据返回给页面
		model.put("takeTotalAmount", takeTotalAmount);
		model.put("totalFee", totalFee);
		model.put("userWithdrawalList", userWithdrawalList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("transType", transType);
		return "/depFinanceStatistics/baofoo_withdrawal_index";
	}

	/**
	 * 宝付客户支取统计 导出excel
	 * @param request
	 * @param response
     */
	@RequestMapping("/exportBaofooWithdrawal")
	public void exportBaofooWithdrawal(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String transType = request.getParameter("transType");
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime = StringUtil.isBlank(startTime) == true ? yestoday : startTime;
		endTime=StringUtil.isBlank(endTime) == true ? yestoday : endTime;

		//需要导出的数据
		Integer count = financeService.queryBaofooWithdrawalCount(userName, startTime, endTime,transType);
		List<HfbankCustomerWithdrawalVO> userWithdrawalList = financeService.queryBaofooWithdrawalList(userName, startTime,
				endTime,transType, 1, count);

		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.baofoo.withdrawal.excel"));
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
			for(int i=0;i<userWithdrawalList.size();i++) {
				HfbankCustomerWithdrawalVO vo = userWithdrawalList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("180"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("180"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getUpdateTime());

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("180"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("180"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				Cell body_cell_3 = bodyRow.createCell(3); //客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());
				Cell body_cell_4 = bodyRow.createCell(4); //客户提现金额
				body_cell_4.setCellStyle(smallCellStyle);
				body_cell_4.setCellValue(vo.getAmount());
				Cell body_cell_5 = bodyRow.createCell(5); //提现手续费
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getDoneFee());
				Cell body_cell_6 = bodyRow.createCell(6); //客户实际到账金额
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue(vo.getRevenueAmount());

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

	//============================== 恒丰、宝付客户支取统计 E ====================================================
	
	//============================== 存管产品 借款服务费 S =====================================================
	
	/**
	 * 存管产品 借款服务费 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryLoanServiceFee/index")
	public String queryLoanServiceFeeIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

		//将数据返回给页面
		model.put("totalRows", 0);
		model.put("pageNum", Constants.MANAGE_DEFAULT_PAGENUM);
		model.put("numPerPage", Constants.MANAGE_100_NUMPERPAGE);
		String date = DateUtil.formatYYYYMMDD(new Date());
		String startTime = StringUtil.isEmpty(request.getParameter("startTime"))? date : request.getParameter("startTime");
		String endTime = StringUtil.isEmpty(request.getParameter("endTime"))? date : request.getParameter("endTime");
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		return "/depFinanceStatistics/loan_service_fee";
	}
	
	/**
	 * 存管产品 借款服务费 
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryLoanServiceFeeList/index")
	public String queryLoanServiceFeeList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String partnerCode = StringUtil.isEmpty(request.getParameter("partnerCode"))? "" : request.getParameter("partnerCode");
		String userName = StringUtil.isEmpty(request.getParameter("userName"))? "" : request.getParameter("userName").trim();
		String mobile = StringUtil.isEmpty(request.getParameter("mobile"))? "" : request.getParameter("mobile").trim();
		String startTime = StringUtil.isEmpty(request.getParameter("startTime"))? "" : request.getParameter("startTime");
		String endTime = StringUtil.isEmpty(request.getParameter("endTime"))? "" : request.getParameter("endTime");
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
		// 统计
		Integer count = financeService.countLoanServiceFee(userName, mobile, partnerCode, startTime, endTime, partnerBusinessFlag);
		
		// 列表查询
		if(count > 0) {
			List<LnLoanServiceFeeVO> resultList = financeService.selectLoanServiceFeeList(userName, mobile, partnerCode, startTime,
					endTime, partnerBusinessFlag, Integer.parseInt(pageNum), Integer.parseInt(numPerPage));
			model.put("totalRows", count);
			model.put("loanServiceFeeList", resultList);
			LnLoanServiceFeeVO lnLoanServiceFeeVO = financeService.sumLnLoanServiceFee(userName, mobile, partnerCode, startTime, endTime, partnerBusinessFlag);
			model.put("loanInterestTotal", lnLoanServiceFeeVO.getLoanInterestTotal());
			model.put("loanServiceFeeTotal", lnLoanServiceFeeVO.getLoanServiceFeeTotal());
		} else {
			model.put("totalRows", 0);
		}
		
		//将数据返回给页面
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("partnerCode", partnerCode);
		model.put("partnerBusinessFlag", partnerBusinessFlag);

		return "/depFinanceStatistics/loan_service_fee";
	}

	/**
	 * 存管产品 借款服务费 导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportLoanServiceFee")
	public void exportLoanServiceFee(HttpServletRequest request, HttpServletResponse response) {
		String partnerCode = request.getParameter("partnerCode");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String partnerBusinessFlag = request.getParameter("partnerBusinessFlag");
		if(StringUtil.isBlank(partnerBusinessFlag)){
			partnerBusinessFlag = "777";
		}
		// 统计
		Integer count = financeService.countLoanServiceFee(userName, mobile, partnerCode, startTime, endTime, partnerBusinessFlag);
		//需要导出的数据
		List<LnLoanServiceFeeVO> resultList = financeService.selectLoanServiceFeeList(userName, mobile, partnerCode, startTime,
				endTime, partnerBusinessFlag, 1, count);
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.loan.service.fee.excel"));
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
			for(int i=0; i<resultList.size(); i++) {
				LnLoanServiceFeeVO vo = resultList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getSerialNo());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("161"+ln_deposition_repay_schedule的partner_repay_id+ln_user的id)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue(vo.getBillNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				if (vo.getFinishTime() != null) {
					head_cell_2.setCellValue(vo.getFinishTime());
				}

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getSerialNo());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("161"+ln_deposition_repay_schedule的partner_repay_id+ln_user的id)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getBillNo());
				Cell body_cell_2 = bodyRow.createCell(2); //融资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getLnCustomerCode());

				Cell body_cell_3 = bodyRow.createCell(3); //融资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());

				Cell body_cell_5 = bodyRow.createCell(4); //手续费
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getLoanServiceFee());
			}

			ExportUtil.exportExcel(wb, fileName+partnerCode, response, request);
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

	//============================== 存管产品 借款服务费 E =====================================================
			
}
