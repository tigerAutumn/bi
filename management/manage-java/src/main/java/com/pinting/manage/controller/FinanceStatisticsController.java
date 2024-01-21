package com.pinting.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.LnCompensateDetailMapper;
import com.pinting.business.dao.LnCompensateMapper;
import com.pinting.business.dao.LnPayOrdersMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.enums.BaoFooCheckBalanceTypeEnum;
import com.pinting.business.enums.HFBankCheckBalanceTypeEnum;
import com.pinting.business.enums.PayPlatformEnum;
import com.pinting.business.model.LnCompensate;
import com.pinting.business.model.LnCompensateDetail;
import com.pinting.business.model.LnCompensateExample;
import com.pinting.business.model.LnPayOrders;
import com.pinting.business.model.LnPayOrdersExample;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.vo.*;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.model.vo.BalanceFinanceStatisticsVO;
import com.pinting.business.model.vo.CalculateRewardsStatisticsVO;
import com.pinting.business.model.vo.Cash30YunVO;
import com.pinting.business.model.vo.CorpusBuyStatisticsVO;
import com.pinting.business.model.vo.FinanceInterestOffline7DaiVO;
import com.pinting.business.model.vo.GrantRewardsStatisticsVO;
import com.pinting.business.model.vo.InfluxCollectionRepayVO;
import com.pinting.business.model.vo.MDepositDsDfQueryVO;
import com.pinting.business.model.vo.MDepositDsDfResRecordVO;
import com.pinting.business.model.vo.MDepositDsDfResVO;
import com.pinting.business.model.vo.PayFinanceStatisticsVO;
import com.pinting.business.model.vo.RevenueCollectionRepayVO;
import com.pinting.business.model.vo.UserDrawBonusStatisticsVO;
import com.pinting.business.model.vo.UserGrantInterestStatisticsVO;
import com.pinting.business.model.vo.UserRechanageStatisticsVO;
import com.pinting.business.model.vo.UserRedPublishStatisticsVO;
import com.pinting.business.service.manage.FinancialStatisticsService;
import com.pinting.business.service.manage.MFinanceStatisticsService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;

@Controller
@RequestMapping("/financeStatistics")
public class FinanceStatisticsController {

	@Autowired
	private MFinanceStatisticsService financeService;
	@Autowired
	private FinancialStatisticsService financialStatisticsService;
	@Autowired
	private LnPayOrdersMapper lnPayOrdersMapper;
	@Autowired
	private LnCompensateDetailMapper lnCompensateDetailMapper;
	@Autowired
	private LnCompensateMapper lnCompensateMapper;
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	
	private Logger log = LoggerFactory.getLogger(FinanceStatisticsController.class);

	//============================== 客户充值S =====================================================
	/**
	 * 客户充值
	 * 用户充值到币港湾的钱，卡购买也算是充值，卡购买是的过程是充值和购买的过程
	 * 用户充值数据，体现为订单表中充值记录和卡购买记录（TOP_UP和CARD_BUY_PRODUCT）
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
		
		return "/financeStatistics/user_rechange_statistics";
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
			File file = new File(GlobEnvUtil.get("user.rechange.excel"));
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
				head_cell_1.setCellValue("10"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("10"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("10"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				Cell body_cell_5 = bodyRow.createCell(5); //充值金额
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getAmount());
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
	//============================== 客户充值E =====================================================
	
	

	
	//============================== 本金购买S =====================================================
	/**
	 * 本金购买
	 * 本金购买统计的数据是用户购买币港湾产品的数据，包含卡购买和余额购买
	 * 数据体现为订单表中卡购买和余额购买的数据（CARD_BUY_PRODUCT和BALANCE_BUY_PRODUCT）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryCorpusBuy")
	public String corpusBuyStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userType = request.getParameter("userType");
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
		
		//获得用户购买记录
		List<CorpusBuyStatisticsVO> userList = financeService.corpusBuyStatistics(userType,userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.corpusBuyStatisticsCount(userType,userName, mobile, startTime, endTime);
		CorpusBuyStatisticsVO totalCorpusBuy = financeService.corpusBuyTotalAmount(userType,userName, mobile, startTime, endTime);
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
		
		return "/financeStatistics/corpus_buy";
	}
	
	@RequestMapping("/exportCorpusBuy")
	public void exportCorpusBuy(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String userType = request.getParameter("userType");
		//需要导出的数据
		Integer count = financeService.corpusBuyStatisticsCount(userType,userName, mobile, startTime, endTime);
		List<CorpusBuyStatisticsVO> userList = financeService.corpusBuyStatistics(userType,userName, mobile, startTime, endTime, 1, count);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("corpus.buy.excel"));
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
				Cell head_cell_1 = headRow.createCell(1); //单据编号("20"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("20"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("20"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("20"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				
				Cell body_cell_4 = bodyRow.createCell(4); //融资客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getPropertyCode());
				
				Cell body_cell_7 = bodyRow.createCell(7); //投资金额
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getBalance());
				
				Cell body_cell_8 = bodyRow.createCell(8); //融资利息
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getFinanceInterest());
				
				Cell body_cell_9 = bodyRow.createCell(9); //应付投资客户利息
				body_cell_9.setCellStyle(smallCellStyle);
				body_cell_9.setCellValue(vo.getUserInterest());
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
	//============================== 本金购买 E =====================================================

	
	
	
	//============================== 支付融资客户 S =====================================================
	/**
	 * 支付融资客户
	 * 指币港湾向达飞产生的购买数据
	 * 体现为订单表中卡购买和余额购买（CARD_BUY_PRODUCT和BALANCE_BUY_PRODUCT）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPayFinance")
	public String payFinanceStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
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
		
		//获得用户购买记录
		List<PayFinanceStatisticsVO> userList = financeService.payFinanceStatistics(userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.payFinanceStatisticsCount(userName, mobile, startTime, endTime);
		PayFinanceStatisticsVO  payFinance = financeService.payFinanceStatisticsTotalAmount(userName, mobile, startTime, endTime);
		//将数据返回给页面
		model.put("payFinance", payFinance);
		model.put("userList", userList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		
		return "/financeStatistics/pay_finance_statistics";
	}
	
	@RequestMapping("/exportPayFinance")
	public void exportPayFinance(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		//需要导出的数据
		Integer count = financeService.payFinanceStatisticsCount(userName, mobile, startTime, endTime);
		List<PayFinanceStatisticsVO> userList = financeService.payFinanceStatistics(userName, mobile, startTime, endTime, 1, count);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("pay.finance.excel"));
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
				Cell head_cell_1 = headRow.createCell(1); //单据编号("30"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("30"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("30"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("30"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				
				Cell body_cell_4 = bodyRow.createCell(4); //融资客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getPropertyCode());
				
				Cell body_cell_7 = bodyRow.createCell(7); //投资金额
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getBalance());
				
				Cell body_cell_8 = bodyRow.createCell(8); //融资客户利息
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getFinanceInterest());
				
				Cell body_cell_9 = bodyRow.createCell(9); //融资客户本息合计
				body_cell_9.setCellStyle(smallCellStyle);
				body_cell_9.setCellValue(vo.getFinanceTotalAmount());
				
				Cell body_cell_10 = bodyRow.createCell(10); //应付投资客户利息
				body_cell_10.setCellStyle(smallCellStyle);
				body_cell_10.setCellValue(vo.getUserInterest());
				
				Cell body_cell_11 = bodyRow.createCell(11); //公司利息收入
				body_cell_11.setCellStyle(smallCellStyle);
				body_cell_11.setCellValue(vo.getInterestIncome());
				
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
	//============================== 支付融资客户 E =====================================================

	
	
	
	//============================== 计提奖励金 S =====================================================
	/**
	 * 计提奖励金
	 * 用户产品购买行为邀请人获得奖励金
	 * 现在系统中邀请人分为三种：普通用户邀请人、销售、客户经理人
	 * 2015年12月30号之前邀请的客户，客户发生购买行为奖励金是全部一次性给到邀请人
	 * 之后邀请的客户，客户发生购买行为奖金是客户和\邀请人对半分，并且按照投资期限分月发放（只限普通用户邀请人，销售和客户经理人不能拿奖励金）
	 * bs_daily_bonus表中数据是奖励金实际发放记录，bs_bonus_grant_plan是产生奖励金时候进行记录发放计划
	 * 2015年12月30号之前的数据直接查bs_daily_bonus表中数据，之后的数据查bs_bonus_grant_plan
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryCalculateRewards")
	public String calculateRewardsStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
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
		
		List<CalculateRewardsStatisticsVO> userList = financeService.calculateRewardsStatistics(userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.calculateRewardsStatisticsCount(userName, mobile, startTime, endTime);
		Double totalAmount = financeService.calculateRewardsStatisticsTotalAmount(userName, mobile, startTime, endTime);
		
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
		
		return "/financeStatistics/calculate_rewards_statistics";
	}
	
	@RequestMapping("/exportCalculateRewards")
	public void exportCalculateRewards(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		//需要导出的数据
		Integer count = financeService.calculateRewardsStatisticsCount(userName, mobile, startTime, endTime);
		List<CalculateRewardsStatisticsVO> userList = financeService.calculateRewardsStatistics(userName, mobile, startTime, endTime, 1, count);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("calculate.rewards.excel"));
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
				CalculateRewardsStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号
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
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				
				Cell body_cell_3 = bodyRow.createCell(3); //部门
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue("101");
				
				Cell body_cell_5 = bodyRow.createCell(5); //计提奖励金金额
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getRewardsAmount()==null? 0:vo.getRewardsAmount());
				
				
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
	//============================== 计提奖励金 E =====================================================

	
	
	
	//============================== 奖励金发放 S  grant.rewards=====================================================
	/**
	 * 奖励金发放
	 * 用户实际已经到手的奖励金，不算已经产生但是未发给用户的部分
	 * 2015年12月30号之前邀请的客户，客户发生购买行为奖励金是全部一次性给到邀请人
	 * 之后邀请的客户，客户发生购买行为奖金是客户和邀请人对半分，并且按照投资期限分月发放（只限普通用户邀请人，销售和客户经理人不能拿奖励金）
	 * bs_daily_bonus表中数据是奖励金实际发放记录，bs_bonus_grant_plan是产生奖励金时候进行记录发放计划
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryGrantRewards")
	public String GrantRewardsStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String type = request.getParameter("type");
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
		
		//获得用户购买记录
		List<GrantRewardsStatisticsVO> userList = financeService.grantRewardsStatistics(userName, mobile, startTime, endTime, type, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.grantRewardsStatisticsCount(userName, mobile, startTime, endTime, type);
		Double totalAmount = financeService.grantRewardsStatisticsTotalAmount(userName, mobile, startTime, endTime, type);
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
		model.put("type", type);
		
		return "/financeStatistics/grant_rewards_statistics";
	}
	
	@RequestMapping("/exportGrantRewards")
	public void exportGrantRewards(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String type = request.getParameter("type");
		
		//需要导出的数据
		Integer count = financeService.grantRewardsStatisticsCount(userName, mobile, startTime, endTime, type);
		List<GrantRewardsStatisticsVO> userList = financeService.grantRewardsStatistics(userName, mobile, startTime, endTime, type, 1, count);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("grant.rewards.excel"));
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
				GrantRewardsStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue(vo.getBillNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getBillNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());
				Cell body_cell_3 = bodyRow.createCell(3); //部门
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue("101");
				Cell body_cell_6 = bodyRow.createCell(6); //奖励金金额
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue(vo.getRewardsAmount()==null? 0:vo.getRewardsAmount());
				
				
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
	//============================== 奖励金发放 E =====================================================

	
	
	
	
	//============================== 融资客户结算 S  balance.finance.excel=====================================================
	/**
	 * 融资客户结算
	 * 融资客户结算指达飞结算给币港湾的钱，需要核算到具体的客户，与支付融资客户必须对应
	 * 系统回款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryBalanceFinance")
	public String BalanceFinanceStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String partnerCode = request.getParameter("partnerCode");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		if (StringUtil.isBlank(partnerCode)) {
			partnerCode = Constants.PROPERTY_SYMBOL_YUN_DAI;
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;
		
		//获得用户购买记录
		List<BalanceFinanceStatisticsVO> userList = financeService.balanceFinanceStatistics(partnerCode,userName, mobile, note, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.balanceFinanceStatisticsCount(partnerCode,userName, mobile, note, startTime, endTime);
		//BalanceFinanceStatisticsVO totalBalanceFinance = financeService.balanceFinanceStatisticsTotalAmount(userName, mobile, note, startTime, endTime,count);
		//将数据返回给页面
		BalanceFinanceStatisticsVO totalBalanceFinance = new BalanceFinanceStatisticsVO();
		totalBalanceFinance.setTotalBalance(null);
		totalBalanceFinance.setTotalFinanceInterest(null);
		model.put("totalBalanceFinance", totalBalanceFinance);
		model.put("userList", userList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("note", note);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("partnerCode", partnerCode);
		return "/financeStatistics/balance_finance_statistics";
	}
	
	@ResponseBody
	@RequestMapping("/queryBalanceFinanceTotal")
	public Map<String, Object> BalanceFinanceTotalStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		
		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String partnerCode = request.getParameter("partnerCode");
		
		if (StringUtil.isBlank(partnerCode)) {
			partnerCode = Constants.PROPERTY_SYMBOL_YUN_DAI;
		}
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;
		
		//获得用户购买记录
		//List<BalanceFinanceStatisticsVO> userList = financeService.balanceFinanceStatistics(userName, mobile, note, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.balanceFinanceStatisticsCount(partnerCode,userName, mobile, note, startTime, endTime);
		BalanceFinanceStatisticsVO totalBalanceFinance = financeService.balanceFinanceStatisticsTotalAmount(partnerCode,userName, mobile, note, startTime, endTime,count);
		//将数据返回给页面
		model.put("totalBalance", totalBalanceFinance.getTotalBalance());
		model.put("totalFinanceInterest", totalBalanceFinance.getTotalFinanceInterest());
		return model;
	}
	
	
	@RequestMapping("/exportBalanceFinance")
	public void exportBalanceFinance(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		String partnerCode = request.getParameter("partnerCode");
		//需要导出的数据
		Integer count = financeService.balanceFinanceStatisticsCount(partnerCode,userName, mobile, note, startTime, endTime);
		List<BalanceFinanceStatisticsVO> userList = financeService.balanceFinanceStatistics(partnerCode,userName, mobile, note, startTime, endTime, 1, count);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("balance.finance.excel"));
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
				BalanceFinanceStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号(orderNo)
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
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getOrderNo());
				
				Cell body_cell_2 = bodyRow.createCell(2); //融资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getPropertyCode());
				
				Cell body_cell_4 = bodyRow.createCell(4); //客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getCustomerCode());
				
				Cell body_cell_6 = bodyRow.createCell(6); //投资金额
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue(vo.getBalance());
				
				Cell body_cell_7 = bodyRow.createCell(7); //融资客户利息
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getFinanceInterest());
				
				Cell body_cell_8 = bodyRow.createCell(8); //融资客户本息合计
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getFinanceTotalAmount());
				
				
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
	//============================== 融资客户结算  E =====================================================

	
	
	
	
	//==============================客户支取S =====================================================

    /**
     * 客户支取
     * 客户支取是指用户真实拿到钱的数据，即统计币港湾账户到用户银行卡的过程
     * 体现为订单表中余额提现和回款到卡（BALANCE_WITHDRAW和RETURN_2_USER_BANK_CARD）
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/queryExtract")
    public String queryExtract(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
        String customerName = request.getParameter("customerName");
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
        
        List<UserDrawBonusStatisticsVO> dataGrid = financeService.userDrawBonusStatistics(customerName, null, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
        Integer count = financeService.userDrawBonusStatisticsCount(customerName, null, startTime, endTime);
        Double totalAmount = financeService.userDrawBonusStatisticsTotalAmount(customerName, null, startTime, endTime);
        //将数据返回给页面
        model.put("totalAmount", totalAmount);
        model.put("dataGrid", dataGrid);
        model.put("totalRows", count);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        model.put("customerName", customerName);
        model.put("mobile", mobile);
        model.put("startTime", startTime);
        model.put("endTime", endTime);
        
        return "/financeStatistics/user_extract_statistics";
    }
    
    /**
     * 客户支取统计导出
     * @param request
     * @param response
     */
    @RequestMapping("/exportUserExtract")
    public void exportUserExtract(HttpServletRequest request, HttpServletResponse response) {
        String customerName = request.getParameter("customerName");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        
        //需要导出的数据
        Integer count = financeService.userDrawBonusStatisticsCount(customerName, null, startTime, endTime);
        List<UserDrawBonusStatisticsVO> dataGrid = financeService.userDrawBonusStatistics(customerName, null, startTime, endTime, 1, count);
        
        FileInputStream fis = null;
        HSSFWorkbook wb = null;
        try {
            File file = new File(GlobEnvUtil.get("user.extract.excel"));
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
            for(int i=0;i<dataGrid.size();i++) {
                UserDrawBonusStatisticsVO vo = dataGrid.get(i);
                //单据头
                HSSFRow headRow = sheetHead.createRow(base+i);
                Cell head_cell_0 = headRow.createCell(0); //序号
                head_cell_0.setCellStyle(basicStyle);
                head_cell_0.setCellValue(vo.getRowNo());
                Cell head_cell_1 = headRow.createCell(1); //单据编号("80"+orderNo)
                head_cell_1.setCellStyle(textCellStyle);
                head_cell_1.setCellValue("80"+vo.getOrderNo());
                Cell head_cell_2 = headRow.createCell(2); //日期
                head_cell_2.setCellStyle(dateCellStyle);
                head_cell_2.setCellValue(vo.getUpdateTime());
                
                //单据体
                HSSFRow bodyRow = sheetBody.createRow(base+i);
                Cell body_cell_0 = bodyRow.createCell(0); //序号
                body_cell_0.setCellStyle(basicStyle);
                body_cell_0.setCellValue(vo.getRowNo());
                Cell body_cell_1 = bodyRow.createCell(1); //单据编号("80"+orderNo)
                body_cell_1.setCellStyle(textCellStyle);
                body_cell_1.setCellValue("80"+vo.getOrderNo());
                Cell body_cell_2 = bodyRow.createCell(2); //客户代码
                body_cell_2.setCellStyle(textCellStyle);
                body_cell_2.setCellValue(vo.getCustomerCode());
                Cell body_cell_7 = bodyRow.createCell(4); //金额
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
    
  //============================== 客户支取 E =====================================================
    
	
  //============================== 红包发放统计 S =====================================================
	 /**
     * 红包发放统计
     * 这个统计功能实际上是统计已使用的红包的数据
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/queryRedPacketPublish")
    public String userRedPacketPublishStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
        String customerName = request.getParameter("customerName");
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
        List<UserRedPublishStatisticsVO> dataGrid = financeService.userRedPacketPublishStatistics(customerName, null, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
        Integer count = financeService.userRedPacketPublishStatisticsCount(customerName, null, startTime, endTime);
        Double  totalAmount = financeService.userRedPacketPublishStatisticsTotalAmount(customerName, null, startTime, endTime);
        
        //将数据返回给页面
        model.put("totalAmount", totalAmount);
        model.put("dataGrid", dataGrid);
        model.put("totalRows", count);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        model.put("customerName", customerName);
        model.put("mobile", mobile);
        model.put("startTime", startTime);
        model.put("endTime", endTime);
        
        return "/financeStatistics/user_red_packet_publish_statistics";
    }
    
    /**
     * 红包发放统计
     * @param request
     * @param response
     */
    @RequestMapping("/exportUserRedPacketPublish")
    public void exportUserRedPacketPublish(HttpServletRequest request, HttpServletResponse response) {
        String customerName = request.getParameter("customerName");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        
        //需要导出的数据
        Integer count = financeService.userRedPacketPublishStatisticsCount(customerName, null, startTime, endTime);
        List<UserRedPublishStatisticsVO> dataGrid = financeService.userRedPacketPublishStatistics(customerName, null, startTime, endTime, 1, count);
        
        FileInputStream fis = null;
        HSSFWorkbook wb = null;
        try {
            File file = new File(GlobEnvUtil.get("grant.redpacket.excel"));
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
            for(int i=0;i<dataGrid.size();i++) {
                UserRedPublishStatisticsVO vo = dataGrid.get(i);
                //单据头
                HSSFRow headRow = sheetHead.createRow(base+i);
                Cell head_cell_0 = headRow.createCell(0); //序号
                head_cell_0.setCellStyle(basicStyle);
                head_cell_0.setCellValue(vo.getRowNo());
                Cell head_cell_1 = headRow.createCell(1); //单据编号("11"+orderNo)
                head_cell_1.setCellStyle(textCellStyle);
                head_cell_1.setCellValue("11"+vo.getOrderNo());
                Cell head_cell_2 = headRow.createCell(2); //日期
                head_cell_2.setCellStyle(dateCellStyle);
                head_cell_2.setCellValue(vo.getUpdateTime());
                
                //单据体
                HSSFRow bodyRow = sheetBody.createRow(base+i);
                Cell body_cell_0 = bodyRow.createCell(0); //序号
                body_cell_0.setCellStyle(basicStyle);
                body_cell_0.setCellValue(vo.getRowNo());
                Cell body_cell_1 = bodyRow.createCell(1); //单据编号("11"+orderNo)
                body_cell_1.setCellStyle(textCellStyle);
                body_cell_1.setCellValue("11"+vo.getOrderNo());
                Cell body_cell_2 = bodyRow.createCell(2); //部门
                body_cell_2.setCellStyle(textCellStyle);
                body_cell_2.setCellValue(vo.getDept());
                Cell body_cell_5 = bodyRow.createCell(3); //客户代码
                body_cell_5.setCellStyle(smallCellStyle);
                body_cell_5.setCellValue(vo.getCustomerCode());
                Cell body_cell_6 = bodyRow.createCell(4); //客户名称
                body_cell_6.setCellStyle(smallCellStyle);
                body_cell_6.setCellValue(vo.getCustomerName());
                Cell body_cell_7 = bodyRow.createCell(5); //金额
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
  //============================== 红包发放 E ===================================================== 
   
    
    
    
    
    
    
  //============================== 发放本息统计 S =====================================================
  /**
   * 发放本息统计
   * 用户回款数据，投资到期以后本息合计发放给客户，
   * 回款到卡和回款到余额的数据
   * 体现为bs_customer_receive_money记录
   * @param request
   * @param response
   * @param model
   * @return
   */
  @RequestMapping("/queryGrantInterest")
  public String queryGrantInterest(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
      String customerName = request.getParameter("customerName");
      String mobile = request.getParameter("mobile");
      String startTime = request.getParameter("startTime");
      String endTime = request.getParameter("endTime");
      String pageNum = request.getParameter("pageNum");
      String numPerPage = request.getParameter("numPerPage");
      String propertySymbol = request.getParameter("propertySymbol");
      
      
      if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
          pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
          numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
      }
      
      if (StringUtil.isEmpty(propertySymbol)) {
    	  propertySymbol = Constants.PROPERTY_SYMBOL_YUN_DAI;
      }
      
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(new Date());
      calendar.add(Calendar.DAY_OF_MONTH, -1);
      String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
      startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
      endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;
      //获得发放本息记录
      List<UserGrantInterestStatisticsVO> dataGrid = financeService.userGrantInterestStatistics(customerName, startTime, endTime,propertySymbol, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
      Integer count = financeService.userGrantInterestStatisticsCount(customerName, startTime, endTime,propertySymbol);
      UserGrantInterestStatisticsVO  totalGrantInterest = financeService.userGrantInterestStatisticsTotalAmount(customerName, startTime, endTime,propertySymbol);
      //将数据返回给页面
      model.put("totalGrantInterest", totalGrantInterest);
      model.put("dataGrid", dataGrid);
      model.put("totalRows", count);
      model.put("pageNum", pageNum);
      model.put("numPerPage", numPerPage);
      model.put("customerName", customerName);
      model.put("mobile", mobile);
      model.put("startTime", startTime);
      model.put("endTime", endTime);
      model.put("propertySymbol", propertySymbol);
      return "/financeStatistics/user_grant_interest_statistics";
  }
  
  /**
   * 导出发放本息统计
   * @param request
   * @param response
   */
  @RequestMapping("/exportGrantInterest")
  public void exportGrantInterest(HttpServletRequest request, HttpServletResponse response) {
      String customerName = request.getParameter("customerName");
      String startTime = request.getParameter("startTime");
      String endTime = request.getParameter("endTime");
      String propertySymbol = request.getParameter("propertySymbol");
      //需要导出的数据
      Integer count = financeService.userGrantInterestStatisticsCount(customerName, startTime, endTime,propertySymbol);
      List<UserGrantInterestStatisticsVO> dataGrid = financeService.userGrantInterestStatistics(customerName, startTime, endTime,propertySymbol, 1, count);
      
      FileInputStream fis = null;
      HSSFWorkbook wb = null;
      try {
          File file = new File(GlobEnvUtil.get("grant.interest.excel"));
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
          for(int i=0;i<dataGrid.size();i++) {
              UserGrantInterestStatisticsVO vo = dataGrid.get(i);
              //单据头
              HSSFRow headRow = sheetHead.createRow(base+i);
              Cell head_cell_0 = headRow.createCell(0); //序号
              head_cell_0.setCellStyle(basicStyle);
              head_cell_0.setCellValue(vo.getRowNo());
              Cell head_cell_1 = headRow.createCell(1); //单据编号("70"/"71"+id)
              head_cell_1.setCellStyle(textCellStyle);
              head_cell_1.setCellValue(vo.getOrderNo());
              Cell head_cell_2 = headRow.createCell(2); //日期
              head_cell_2.setCellStyle(dateCellStyle);
              head_cell_2.setCellValue(vo.getUpdateTime());
              
              //单据体
              HSSFRow bodyRow = sheetBody.createRow(base+i);
              Cell body_cell_0 = bodyRow.createCell(0); //序号
              body_cell_0.setCellStyle(basicStyle);
              body_cell_0.setCellValue(vo.getRowNo());
              Cell body_cell_1 = bodyRow.createCell(1); //单据编号("70"/"71"+id)
              body_cell_1.setCellStyle(textCellStyle);
              body_cell_1.setCellValue(vo.getOrderNo());
              Cell body_cell_2 = bodyRow.createCell(2); //客户代码
              body_cell_2.setCellStyle(textCellStyle);
              body_cell_2.setCellValue(vo.getCustomerCode());
              Cell body_cell_5 = bodyRow.createCell(4); //本金
              body_cell_5.setCellStyle(smallCellStyle);
              body_cell_5.setCellValue(vo.getBalance());
              Cell body_cell_7 = bodyRow.createCell(5); //利息
              body_cell_7.setCellStyle(smallCellStyle);
              body_cell_7.setCellValue(vo.getInterest());
              Cell body_cell_6 = bodyRow.createCell(6); //本息合计
              body_cell_6.setCellStyle(smallCellStyle);
              body_cell_6.setCellValue(vo.getAmount());
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
  //============================== 红包发放 E ===================================================== 
  
  
  
//============================== 7贷投资利息线下结算（4%） S  =====================================================
	/**
	 * 7贷投资利息线下结算（4%）
	 * 7贷投资利息线下结算
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping("/queryFinanceInterestOffline7Dai")
	public String queryFinanceInterestOffline7Dai(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) throws ParseException {
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
		
		
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date startDate = simpleDateFormat.parse(startTime+" 00:00:00");
        Date endDate = simpleDateFormat.parse(endTime+" 23:59:59");
        long ts = startDate.getTime();
        long es = endDate.getTime();
       
        model.put("startTimeS", String.valueOf(ts));
        model.put("startTimeE", String.valueOf(es));
		
		//获得用户购买记录
		Integer count = financeService.financeInterestOffline7DaiCount(userName, mobile, startTime, endTime);
		if(count >0){
			List<FinanceInterestOffline7DaiVO> userList = financeService.financeInterestOffline7Dai(userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			model.put("userList", userList);
		}

		FinanceInterestOffline7DaiVO totalBalanceFinance = financeService.financeInterestOffline7DaiTotalAmount(userName, mobile, startTime, endTime);
		//将数据返回给页面
		model.put("totalBalanceFinance", totalBalanceFinance);
		
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		return "/financeStatistics/finance_interest_offline_7dai";
	}
	
	
	@RequestMapping("/exportFinanceInterestOffline7Dai")
	public void exportFinanceInterestOffline7Dai(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		
		//需要导出的数据
		Integer count = financeService.financeInterestOffline7DaiCount(userName, mobile, startTime, endTime);
		List<FinanceInterestOffline7DaiVO> userList = financeService.financeInterestOffline7Dai(userName, mobile, startTime, endTime, 1, count);
		
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	        Date startDate = simpleDateFormat.parse(startTime+" 00:00:00");
	        Date endDate = simpleDateFormat.parse(endTime+" 23:59:59");
	        long ts = startDate.getTime();
	        long es = endDate.getTime();
			
			File file = new File(GlobEnvUtil.get("balance.finance.excel"));
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
				FinanceInterestOffline7DaiVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号(orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue(vo.getOrderNo()+String.valueOf(ts)+String.valueOf(es));
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getOrderNo()+String.valueOf(ts)+String.valueOf(es));
				
				Cell body_cell_2 = bodyRow.createCell(2); //融资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getPropertyCode());
				
				Cell body_cell_4 = bodyRow.createCell(4); //客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getCustomerCode());
				
				Cell body_cell_6 = bodyRow.createCell(6); //投资金额
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue(0.0);
				
				Cell body_cell_7 = bodyRow.createCell(7); //融资客户利息
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getFinanceInterest());
				
				Cell body_cell_8 = bodyRow.createCell(8); //融资客户本息合计
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getFinanceInterest());
				
				
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
	
	//===============================7贷投资利息线下结算（4%） E =========================================
  
	//===============================保证金代收代付   S =========================================
	/**
	 * 保证金代收代付-查询(赞分期，赞时贷)
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 * @throws ParseException
	 */
	@RequestMapping("/depositDsDf")
	public String depositDsDf(MDepositDsDfQueryVO req, String queryFlag, Map<String, Object> model) throws ParseException {
		req.setUserMobile(req.getUserMobile() == null ? req.getUserMobile() : req.getUserMobile().trim());
		req.setUserName(req.getUserName() == null ? req.getUserName() : req.getUserName().trim());
		try {
			String partnerCode = req.getPartnerCode();
			if(StringUtils.isBlank(partnerCode) || "ZAN".equals(partnerCode)){
				partnerCode = "ZAN";
				req.setPartnerCode(partnerCode);
				req.setDsdfType(req.getDsdfType() == null ? req.getDsdfType() : req.getDsdfType().trim());
				if (StringUtils.isNotBlank(queryFlag) && "QUERY".equals(queryFlag)) {
					MDepositDsDfResRecordVO record = financeService.mDepositDsDfRecord(req);
					model.put("count", record.getCount());
					model.put("list", record.getList());
					model.put("sumAmount", record.getSumAmount());
				}
			}else if("ZSD".equals(partnerCode)){
				req.setDsdfType("计提");
				if (StringUtils.isNotBlank(queryFlag) && "QUERY".equals(queryFlag)) {
					MDepositDsDfResRecordVO record = financeService.depositDsDfRecord4ZSD(req);
					model.put("count", record.getCount());
					model.put("list", record.getList());
					model.put("sumAmount", record.getSumAmount());
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.put("req", req);

		return "/financeStatistics/finance_depositDsDf_index";
	}


	/**
	 * 保证金代收代付-导出
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/depositDsDfExport")
	public void depositDsDfExport(MDepositDsDfQueryVO req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
		try {
			MDepositDsDfResRecordVO recordList = new MDepositDsDfResRecordVO();
			MDepositDsDfResRecordVO record = new MDepositDsDfResRecordVO();
			req.setDsdfType(req.getDsdfType() == null ? req.getDsdfType() : req.getDsdfType().trim());
			req.setUserMobile(req.getUserMobile() == null ? req.getUserMobile() : req.getUserMobile().trim());
			req.setUserName(req.getUserName() == null ? req.getUserName() : req.getUserName().trim());

			String partnerCode = req.getPartnerCode();
			if(StringUtils.isBlank(partnerCode) || "ZAN".equals(partnerCode)){
				partnerCode = "ZAN";
				req.setPartnerCode(partnerCode);
				req.setDsdfType(req.getDsdfType() == null ? req.getDsdfType() : req.getDsdfType().trim());

				req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
				req.setNumPerPage(1);
				record = financeService.mDepositDsDfRecord(req);
				Integer totalRows = record.getCount();
				if(totalRows>0){
					req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
					req.setNumPerPage(totalRows);
					recordList = financeService.mDepositDsDfRecord(req);
				}
			}else if("ZSD".equals(partnerCode)){
				req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
				req.setNumPerPage(1);
				record = financeService.depositDsDfRecord4ZSD(req);
				Integer totalRows = record.getCount();
				if(totalRows>0){
					req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
					req.setNumPerPage(totalRows);
					recordList = financeService.depositDsDfRecord4ZSD(req);
				}
			}

			List<MDepositDsDfResVO> dataGrid = recordList.getList();

			List<HashMap<String,Object>> datas = BeanUtils.classToArrayList(dataGrid);
			List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
			Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
			List<Object> titles = new ArrayList<Object>();
			titles.add("序号");
			titles.add("融资客户名称");
			titles.add("手机号");
			titles.add("融资客户代码");
			titles.add("类型");
			titles.add("代收金额（元）");
			titles.add("代付金额（元）");
			titles.add("产生日期");
			titleMap.put("title", titles);
			list.add(titleMap);
			if(!CollectionUtils.isEmpty(datas)) {
				int i = 0;
				for (HashMap<String,Object> data : datas) {
					Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
					List<Object> obj = new ArrayList<Object>();
					obj.add(data.get("rowno"));
					obj.add(data.get("lnUserName"));
					obj.add(data.get("lnUserMobile"));
					obj.add(data.get("lnUserCode"));
					obj.add(data.get("dsdfType"));
					obj.add(data.get("dsAmount") == null?"":MoneyUtil.format((Double)data.get("dsAmount")));
					obj.add(data.get("dfAmount") == null?"":MoneyUtil.format((Double)data.get("dfAmount")));
					obj.add(DateUtil.formatYYYYMMDD((Date)data.get("doneTime")));
					datasMap.put("transfer"+(++i), obj);
					list.add(datasMap);
				}
			}
			Double dsAmount = record.getSumAmount().getSumDsAmount();
			Double dfAmount = record.getSumAmount().getSumDfAmount();
			Map<String,List<Object>> endMap = new HashMap<String, List<Object>>();
			List<Object> endTitles = new ArrayList<Object>();
			endTitles.add("代收总额(元)：");
			endTitles.add(dsAmount == null?"":MoneyUtil.format(dsAmount));
			endTitles.add("代付总额(元)：");
			endTitles.add(dfAmount == null?"":MoneyUtil.format(dfAmount));
			endMap.put("endTitles1", endTitles);
			list.add(endMap);
			try {
				ExportUtil.exportExcel(response, request, "保证金代转代付"+req.getPartnerCode(), list);
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		model.put("req", req);
	}
			
	//===============================保证金代收代付   E =========================================
  
	
	
	//============================== 赞分期、云贷、赞时贷 营收代收代付S =====================================================
	/**
	 * 赞分期营收代收代付-赞时贷，存管云贷共用
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/revenueCollectionRepay")
	public String revenueCollectionRepay(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("type", type);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("partnerCode", partnerCode);
		
		startTime=StringUtil.isBlank(startTime)==true?"":startTime;
		endTime=StringUtil.isBlank(endTime)==true?"":endTime;
		if(StringUtil.isBlank(partnerCode)){
			partnerCode="YUN_DAI_SELF";
		}
		if("ZAN".equals(partnerCode)){
			
			//获得用户购买记录
			List<RevenueCollectionRepayVO> userList = financeService.revenueCollectionRepayList(userName, mobile, type,startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			Integer count = financeService.revenueCollectionRepayCount(userName, mobile, type,startTime, endTime);
			
			Double  revenueCollectionAmount = financeService.revenueCollectionRepaySum(userName, mobile, type, startTime, endTime, "REVENUE_COLLECTION");
			Double  revenueRepayAmount = financeService.revenueCollectionRepaySum(userName, mobile, type, startTime, endTime, "REVENUE_PAY");
			
			//将数据返回给页面
			model.put("revenueCollectionAmount", revenueCollectionAmount);
			model.put("revenueRepayAmount", revenueRepayAmount);
			model.put("userList", userList);
			model.put("totalRows", count);
			return "/financeStatistics/revenue_collection_repay";
		}else{
			
			//获得用户购买记录
			List<RevenueCollectionRepayVO> userList = financeService.revenueCollectionRepayYunZSDList(userName, mobile, type,startTime, endTime, partnerCode, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			Integer count = financeService.revenueCollectionRepayYunZSDCount(userName, mobile, type,startTime, endTime, partnerCode);
			
			Double  revenueCollectionAmount = financeService.revenueCollectionRepayYunZSDSum(userName, mobile, type, startTime, endTime, "REVENUE_COLLECTION", partnerCode);
			Double  revenueRepayAmount = financeService.revenueCollectionRepayYunZSDSum(userName, mobile, type, startTime, endTime, "REVENUE_PAY", partnerCode);
			
			//将数据返回给页面
			model.put("revenueCollectionAmount", revenueCollectionAmount);
			model.put("revenueRepayAmount", revenueRepayAmount);
			model.put("userList", userList);
			model.put("totalRows", count);
			return "/financeStatistics/revenue_collection_repay_yun";
		}
	}
	
	
	
	
	/**
	 * 导出Excel
	 * @return
	 */
	@RequestMapping("/revenueCollectionRepayReport")
	public void revenueCollectionRepayReport(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		
		startTime=StringUtil.isBlank(startTime)==true?"":startTime;
		endTime=StringUtil.isBlank(endTime)==true?"":endTime;
		
		Integer totalRows = financeService.revenueCollectionRepayCount(userName, mobile, type,startTime, endTime);

		if (totalRows > 0) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(totalRows);
			
			List<RevenueCollectionRepayVO> userList = financeService.revenueCollectionRepayList(userName, mobile, type,startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
			

	        ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(userList);
	        List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
	        Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
	        List<Object> titles = new ArrayList<Object>();
	        titles.add("序号");
	        titles.add("借款用户名称");
	        titles.add("手机号");
	        titles.add("类型");
	        titles.add("代收金额");
	        titles.add("代付金额");
	        titles.add("产生日期");

	        titleMap.put("title", titles);
	        excelList.add(titleMap);
	        
            int i = 0;
            if (!CollectionUtils.isEmpty(dataGrid)) {
                for (HashMap<String, Object> data : dataGrid) {
                    Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
                    List<Object> objs = new ArrayList<Object>();
                    
                    objs.add(data.get("rowno") == null ? "" : data.get("rowno"));
                    objs.add(data.get("userName") == null ? "" : data.get("userName"));
                    objs.add(data.get("mobile") == null ? "" : data.get("mobile"));
                    if ("REVENUE".equals(data.get("type"))) {
                    	objs.add("营收");
					}else if ("ADVANCE".equals(data.get("type"))) {
						objs.add("营收垫付");
					}else if ("LIQUIDATION".equals(data.get("type"))) {
						objs.add("清算");
					}else {
						objs.add(data.get("type"));
					}
                    
                    objs.add(data.get("revenueCollection") == null || (Double)data.get("revenueCollection") == 0 ? "" : MoneyUtil.format((Double)data.get("revenueCollection")));
                    objs.add(data.get("revenuePay") == null || (Double)data.get("revenuePay") == 0 ? "" : MoneyUtil.format((Double)data.get("revenuePay")));
                  
                    
                    objs.add(data.get("time") == null ? "" : DateUtil.format((Date)data.get("time")));
                   
                   
                    datasMap.put("order"+i, objs);
                    i++;
                    excelList.add(datasMap);
                }
            }
            try {
                ExportUtil.exportExcel(response, request, "赞分期营收代收代付导出", excelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
		    
		}
	    
	    
	}
	//============================== 赞分期、云贷、赞时贷 营收代收代付S =====================================================
	
	/**
	 * 归集户代收代付
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/influxCollectionRepay")
	public String influxCollectionRepay(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		// 查询参数
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		startTime = StringUtil.isBlank(startTime)==true?"":startTime;
		endTime = StringUtil.isBlank(endTime)==true?"":endTime;
		
		//获得归集户代收代付记录
		List<InfluxCollectionRepayVO> collectList = financeService.influxCollectionRepayList(userName, mobile, type,startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.influxCollectionRepayCount(userName, mobile, type, startTime, endTime);
		Double influxCollectionAmount = financeService.influxCollectionRepaySum(userName, mobile, type, startTime, endTime, "COLLECTION");
		Double influxRepayAmount = financeService.influxCollectionRepaySum(userName, mobile, type, startTime, endTime, "REPAY");
		
		//将数据返回给页面
		model.put("influxCollectionAmount", influxCollectionAmount);
		model.put("influxRepayAmount", influxRepayAmount);
		model.put("collectList", collectList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("type", type);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		return "/financeStatistics/influx_collection_repay";
	}
	
	/**
	 * 归集户代收代付，数据导出Excel
	 * @return
	 */
	@RequestMapping("/influxCollectionRepayReport")
	public void influxCollectionRepayReport(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		
		startTime=StringUtil.isBlank(startTime)==true?"":startTime;
		endTime=StringUtil.isBlank(endTime)==true?"":endTime;
		
		Integer totalRows = financeService.influxCollectionRepayCount(userName, mobile, type,startTime, endTime);
		if (totalRows > 0) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(totalRows);	
			List<InfluxCollectionRepayVO> userList = financeService.influxCollectionRepayList(userName, mobile, type,startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));

	        ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(userList);
	        List<Map<String, List<Object>>> excelList = new ArrayList<Map<String, List<Object>>>();
	        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
	        List<Object> titles = new ArrayList<Object>();
	        titles.add("序号");
	        titles.add("融资客户名称");
	        titles.add("手机号");
	        titles.add("类型");
	        titles.add("代收金额");
	        titles.add("代付金额");
	        titles.add("产生日期");
	        titleMap.put("title", titles);
	        excelList.add(titleMap);
	        
            int i = 0;
            if (!CollectionUtils.isEmpty(dataGrid)) {
                for (HashMap<String, Object> data : dataGrid) {
                    Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                    List<Object> objs = new ArrayList<Object>();
                    objs.add(data.get("rowno") == null ? "" : data.get("rowno"));
                    objs.add(data.get("userName") == null ? "" : data.get("userName"));
                    objs.add(data.get("mobile") == null ? "" : data.get("mobile"));
                    if ("COLLECTION".equals(data.get("type"))) {
                    	objs.add("代收");
					}else if ("REPAY".equals(data.get("type"))) {
						objs.add("代付");
					}else {
						objs.add(data.get("type"));
					}
                    objs.add(data.get("influxCollection") == null || (Double)data.get("influxCollection") == 0 ? "" : MoneyUtil.format((Double)data.get("influxCollection")));
                    objs.add(data.get("influxRepay") == null || (Double)data.get("influxRepay") == 0 ? "" : MoneyUtil.format((Double)data.get("influxRepay")));
                    objs.add(data.get("time") == null ? "" : DateUtil.format((Date)data.get("time")));
                    datasMap.put("order"+i, objs);
                    i++;
                    excelList.add(datasMap);
                }
            }
            try {
                ExportUtil.exportExcel(response, request, "归集户代收代付导出", excelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}    
	}
	
	//============================== 对账可疑记录查询S =====================================================
	/**
	 * 财务统计-对账可疑记录查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryPayCheckError")
	public String queryPayCheckErrorIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		// 查询参数
		String orderNo = StringUtil.isNotEmpty(request.getParameter("orderNo")) ? request.getParameter("orderNo").trim() : "";
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String partnerCode = request.getParameter("partnerCode");
		String businessType = request.getParameter("businessType");
		String paymentChannelId = request.getParameter("paymentChannelId");
		
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		Date date = new Date();
		// 默认查询当天记录
		startTime = StringUtil.isEmpty(startTime) ? DateUtil.formatYYYYMMDD(date) : startTime;
		endTime = StringUtil.isEmpty(endTime) ? DateUtil.formatYYYYMMDD(date) : endTime;
		
		//获得对账可疑记录
		List<BsPayCheckErrorVO> checkList = financeService.findCheckErrorList(orderNo, partnerCode, businessType, paymentChannelId, PayPlatformEnum.BAOFOO.getCode(),
				startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.countCheckError(orderNo, partnerCode, businessType, paymentChannelId,
				PayPlatformEnum.BAOFOO.getCode(), startTime, endTime);

		//将数据返回给页面
		model.put("checkList", checkList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("orderNo", orderNo);
		model.put("partnerCode", partnerCode);
		model.put("businessType", businessType);
		model.put("paymentChannelId", paymentChannelId);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		return "/financeStatistics/check_error_index";
	}

	/**
	 * 财务统计-对账可疑记录 数据导出Excel
	 * @return
	 */
	@RequestMapping("/payCheckErrorReport")
	public void payCheckErrorInfoReport(HttpServletRequest request, HttpServletResponse response) {
		String orderNo = StringUtil.isNotEmpty(request.getParameter("orderNo")) ? request.getParameter("orderNo").trim() : "";
		String partnerCode = request.getParameter("partnerCode");
		String businessType = request.getParameter("businessType");
		String paymentChannelId = request.getParameter("paymentChannelId");
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		Date date = new Date();
		// 默认查询当天记录
		startTime = StringUtil.isEmpty(startTime) ? DateUtil.formatYYYYMMDD(date) : startTime;
		endTime = StringUtil.isEmpty(endTime) ? DateUtil.formatYYYYMMDD(date) : endTime;

		Integer totalRows = financeService.countCheckError(orderNo, partnerCode, businessType, paymentChannelId, PayPlatformEnum.BAOFOO.getCode(),
				startTime, endTime);
		if (totalRows > 0) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(totalRows);
			List<BsPayCheckErrorVO> checkList = financeService.findCheckErrorList(orderNo, partnerCode, businessType, paymentChannelId, PayPlatformEnum.BAOFOO.getCode(),
					startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));

	        ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(checkList);
	        List<Map<String, List<Object>>> excelList = new ArrayList<Map<String, List<Object>>>();
	        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
	        List<Object> titles = new ArrayList<Object>();
	        titles.add("商户类型");
	        titles.add("资产方");
	        titles.add("业务类型");
	        titles.add("本地订单编号");
	        titles.add("宝付订单编号");
	        titles.add("本地订单状态");
	        titles.add("宝付订单状态");
	        titles.add("宝付金额");
	        titles.add("本地金额");
	        titles.add("对账结果");
	        titles.add("对账日期");
	        titleMap.put("title", titles);
	        excelList.add(titleMap);

            int i = 0;
            if (!CollectionUtils.isEmpty(dataGrid)) {
                for (HashMap<String, Object> data : dataGrid) {
                    Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                    List<Object> objs = new ArrayList<Object>();
                    objs.add(data.get("paymentChannelId") == null ? "" : data.get("paymentChannelId"));
                    objs.add(data.get("partnerCode") == null ? "" : data.get("partnerCode"));
                    String displayBusinessType = data.get("businessType") == null ? "" : data.get("businessType").toString();
                    if (BaoFooCheckBalanceTypeEnum.WITHHOLDING_REPAY.getCode().equals(displayBusinessType)) {
                    	objs.add("代扣(还款)");
					} else if (BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_OFFLINE_REPAY.getCode().equals(displayBusinessType)) {
						objs.add("钱包转账(线下还款)");
					} else if (BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_LN_COMPENSATE.getCode().equals(displayBusinessType)) {
						objs.add("钱包转账(代偿)");
					} else if (BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_ASSIST_2_MAIN.getCode().equals(displayBusinessType)) {
						objs.add("钱包转账(辅转主)");
					} else if (BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_PARTNER_SETTLE.getCode().equals(displayBusinessType)) {
						objs.add("钱包转账(结算合作方)");
					} else if (BaoFooCheckBalanceTypeEnum.WALLET_TRANSFER_SYS_RECEIVE_MONEY.getCode().equals(displayBusinessType)) {
						objs.add("钱包转账(老产品回款)");	
					} else if (BaoFooCheckBalanceTypeEnum.PAID_BALANCE_WITHDRAW.getCode().equals(displayBusinessType)) {
						objs.add("代付(余额提现)");
					} else if (BaoFooCheckBalanceTypeEnum.PAID_BONUS_WITHDRAW.getCode().equals(displayBusinessType)) {
						objs.add("代付(奖励金提现)");
					} else if (BaoFooCheckBalanceTypeEnum.PAID_2_DEP_REPAY_CARD.getCode().equals(displayBusinessType)) {
						objs.add("代付(归集户)");
					} else {
						objs.add("");
					}                    
                    objs.add(data.get("orderNo") == null ? "" : data.get("orderNo"));
                    objs.add(data.get("bfOrderNo") == null ? "" : data.get("bfOrderNo"));
                    String status = data.get("transStatus") == null ? "" : data.get("transStatus").toString();
                    if (Constants.ORDER_STATUS_PAYING.equals(status)) {
                    	objs.add("处理中");
					} else if (Constants.ORDER_STATUS_SUCCESS.equals(status)) {
						objs.add("成功");
					} else if (Constants.ORDER_STATUS_FAIL.equals(status)) {
						objs.add("失败");
					} else if (Constants.ORDER_STATUS_PRE_SUCC.equals(status)) {
						objs.add("预下单成功");
					} else if (Constants.ORDER_STATUS_PRE_FAIL.equals(status)) {
						objs.add("预下单失败");	
					} else if ("无(代偿)".equals(status)) {
						objs.add("无(代偿)");
					} else if ("REPAIED".equals(status)) {
						objs.add("还款成功");	
					} else if ("REPAYING".equals(status)) {
						objs.add("还款中");
					} else if ("REPAY_FAIL".equals(status)) {
						objs.add("还款失败");
					} else if ("SUCC".equals(status)) {
						objs.add("成功");	
					} else if ("FAIL".equals(status)) {
						objs.add("失败");
					} else if ("REPEAT".equals(status)) {
						objs.add("重复还款");	
					} else if ("INIT".equals(status)) {
						objs.add("未处理");		
					} else if (StringUtil.isEmpty(status)) {
						objs.add("数据库为null或者空字符串");
					} else {
						objs.add("");
					}
                    String bfStatus = data.get("hostSysStatus") == null ? "" : data.get("hostSysStatus").toString();
                    if ("1".equals(bfStatus)) {
                    	objs.add("成功");
                    } else {
                    	objs.add("");
                    }
                    objs.add(data.get("doneAmount") == null ? "" : MoneyUtil.format((Double)data.get("doneAmount")));
                    objs.add(data.get("sysAmount") == null ? "" : MoneyUtil.format((Double)data.get("sysAmount")));
                    objs.add(data.get("info") == null ? "" : data.get("info"));
                    objs.add(data.get("settleDate") == null ? "" : DateUtil.format((Date)data.get("settleDate")));
                    datasMap.put("orderNo"+i, objs);
                    i++;
                    excelList.add(datasMap);
                }
            }
            try {
                ExportUtil.exportExcel(response, request, "对账可疑记录导出", excelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	/**
	 * 进入异常订单处理页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/checkGachaErrorHandleIndex")
	public String checkGachaErrorHandleDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//生成token
		String serverToken = com.pinting.util.Util.createToken(request, response);
		model.put("serverToken", serverToken);
		return "/financeStatistics/checkGachaErrorHandle_detail";
	}
	 
	/**
	 * 异常订单处理页面，信息回显
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/checkGachaErrorHandleInfo")
	public Map<String, Object> checkGachaErrorHandleDetailInfo(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<>();
		// 订单号
		String localOrderNo = StringUtil.isEmpty(request.getParameter("localOrderNo")) ? "" : request.getParameter("localOrderNo").trim();
		String handleType = request.getParameter("handleType");
		String partnerCode = "";
		Double orderAmount = 0d;
		int orderCount = 0;
		if (com.pinting.business.util.Constants.HANDLE_TYPE_DK_REFUND.equals(handleType)) {
			List<LnPayOrders> lnPayOrdersList = lnPayOrdersMapper.selectLnPayOrdersWithNoOffline("REPAY",
					"DK", localOrderNo);
			if (!CollectionUtils.isEmpty(lnPayOrdersList)) {
				LnPayOrders record = lnPayOrdersList.get(0);
				partnerCode = record.getPartnerCode();
				orderAmount = record.getAmount();
				orderCount = 1;
				result.put("statusCode", "200");
			} else{
				result.put("statusCode", "300");
			}
		} else if (com.pinting.business.util.Constants.HANDLE_TYPE_MULTI_COMPENSATE.equals(handleType)) {
			List<LnCompensateDetail> lnCompensateDetailList = lnCompensateDetailMapper.selectLnCompensateDetailList(com.pinting.business.util.Constants.ORDER_TRANS_CODE_INIT, localOrderNo);
			if (!CollectionUtils.isEmpty(lnCompensateDetailList)) {
				LnCompensateExample lnCompensateExample = new LnCompensateExample();
				lnCompensateExample.createCriteria().andIdEqualTo(lnCompensateDetailList.get(0).getCompensateId());
				List<LnCompensate> list = lnCompensateMapper.selectByExample(lnCompensateExample);
				partnerCode = list.get(0).getPartnerCode();
				List<String> partnerRepayIds = new ArrayList<>(); 
				for (LnCompensateDetail lnCompensateDetail : lnCompensateDetailList) {
					partnerRepayIds.add(lnCompensateDetail.getPartnerRepayId());
				}
				orderCount = lnRepayScheduleMapper.selectRepayScheduleWithCompensateCount(partnerRepayIds, localOrderNo);
				orderAmount = lnRepayScheduleMapper.selectRepayScheduleWithCompensateSum(partnerRepayIds, localOrderNo);
				result.put("statusCode", "200");
			} else {
				result.put("statusCode", "300");
			}
		}
		result.put("partnerCode", StringUtil.isEmpty(partnerCode)? "" :
				PartnerEnum.getEnumByCode(partnerCode).getName());
		result.put("orderAmount", MoneyUtil.format(orderAmount));
		result.put("orderCount", orderCount);
		return result;
	}
	
	/**
	 * 异常订单处理
	 * @param request
	 * @param response
	 * @param handleType 处理类型
	 * @param orderNo 订单号
     * @return
     */
	@ResponseBody
	@RequestMapping("/checkGachaErrorHandle")
	public Map<String, Object> checkGachaErrorHandle(HttpServletRequest request, HttpServletResponse response, String handleType, String orderNo) {

		Map<String, Object> result = new HashMap<>();
		//防重复提交
		if(com.pinting.util.Util.isRepeatSubmit(request)){
			log.info("请勿重复提交");
			result.put("statusCode", "305");
			return result;
		}

		try {
			String resCode = financeService.checkGachaErrorHandle(handleType, orderNo);

			if(ConstantUtil.BSRESCODE_SUCCESS.equals(resCode)) {
				log.info("异常订单处理成功");
				result.put("statusCode", "200");
			} else if (ConstantUtil.BSRESCODE_FAIL.equals(resCode)) {
				log.info("异常订单处理失败");
				result.put("statusCode", "300");
			} 
		} catch (Exception e) {
			result.put("statusCode", "302");
			log.info("异常订单处理失败的原因：{}", e);
			result.put("message", null == e.getMessage() ? "" : e.getMessage());
		}
		String serverToken = com.pinting.util.Util.createToken(request, response);
		result.put("serverToken", serverToken);
		return result;
	}
	
	/**
	 * 财务统计-对账结果导出页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/exportSysDailyCheckGachaIndex")
	public String exportSysDailyCheckGachaIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "/financeStatistics/exportDailyCheckGacha_index";
	}
	
	/**
	 * 财务统计-导出对账结果表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportSysDailyCheckGacha")
	public void exportSysDailyGacha(HttpServletRequest request, HttpServletResponse response) {
		String checkDate = StringUtil.isEmpty(request.getParameter("checkDate"))?"" : request.getParameter("checkDate");
		//需要导出的数据
		List<BsSysDailyCheckGachaVO> checkList = financeService.findSysDailyCheckGachaList(null, checkDate);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.daily.gacha.excel"));
			String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
			//打开excel
			fis = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheetBody = wb.getSheetAt(0); //单据体
			
			HSSFCellStyle basicStyle = ExcelUtil.getBasicStyle(wb);
			HSSFCellStyle textCellStyle = ExcelUtil.getTextStyle(wb);
			HSSFCellStyle smallCellStyle = ExcelUtil.getSmallStyle(wb);
			HSSFCellStyle dateCellStyle = ExcelUtil.getDateStyle(wb);

			//对账日期数据填充
			HSSFRow bodyRowTemplateRow2 = sheetBody.getRow(1);
			Cell body_cell_1 = bodyRowTemplateRow2.getCell(1); //对账日期
			body_cell_1.getCellStyle();
			body_cell_1.setCellValue(checkDate.replace("-", ""));
			if (!CollectionUtils.isEmpty(checkList)) {
				
				int base = 4;
				for(int i=0;i<checkList.size();i++) {
					BsSysDailyCheckGachaVO vo = checkList.get(i);
					
					//单据体，出账入账数据填充
					HSSFRow bodyRowTemplate = sheetBody.getRow(base+i);

					Cell body_cell_3 = bodyRowTemplate.getCell(3); //入账金额
					body_cell_3.setCellStyle(basicStyle);
					body_cell_3.setCellValue(vo.getSuccInAmount());
				
					Cell body_cell_4 = bodyRowTemplate.createCell(4); //入账笔数
					body_cell_4.setCellStyle(textCellStyle);
					body_cell_4.setCellValue(vo.getSuccInCount());
					
					Cell body_cell_5 = bodyRowTemplate.createCell(5); //出账金额
					body_cell_5.setCellStyle(textCellStyle);
					body_cell_5.setCellValue(vo.getSuccOutAmount());
					
					Cell body_cell_6 = bodyRowTemplate.createCell(6); //出账笔数
					body_cell_6.setCellStyle(textCellStyle);
					body_cell_6.setCellValue(vo.getSuccOutCount());
					
				}
			} else {
				// 数据为空，下载空的模版文件
				for(int i=4;i<30;i++) {					
					HSSFRow bodyRowTemplate = sheetBody.getRow(i);
				}
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
	 * 财务统计- 恒丰对账结果查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryHfCheckError")
	public String queryHfBankCheckErrorIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		// 查询参数
		String orderNo = StringUtil.isNotEmpty(request.getParameter("orderNo")) ? request.getParameter("orderNo").trim() : "";
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String partnerCode = request.getParameter("partnerCode");
		String businessType = request.getParameter("businessType");
		
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		Date date = new Date();
		// 默认查询当天记录
		startTime = StringUtil.isEmpty(startTime) ? DateUtil.formatYYYYMMDD(date) : startTime;
		endTime = StringUtil.isEmpty(endTime) ? DateUtil.formatYYYYMMDD(date) : endTime;
		
		//获得对账可疑记录
		List<BsPayCheckErrorVO> checkList = financeService.findCheckErrorList(orderNo, partnerCode, businessType, null, PayPlatformEnum.HFBANK.getCode(),
				startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.countCheckError(orderNo, partnerCode, businessType, null, PayPlatformEnum.HFBANK.getCode(),
				startTime, endTime);

		//将数据返回给页面
		model.put("checkList", checkList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("orderNo", orderNo);
		model.put("partnerCode", partnerCode);
		model.put("businessType", businessType);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		return "/financeStatistics/hf_check_error_index";
	}

	/**
	 * 财务统计-恒丰对账结果查询记录 数据导出Excel
	 * @return
	 */
	@RequestMapping("/hfCheckErrorReport")
	public void hfCheckErrorInfoReport(HttpServletRequest request, HttpServletResponse response) {
		String orderNo = StringUtil.isNotEmpty(request.getParameter("orderNo")) ? request.getParameter("orderNo").trim() : "";
		String partnerCode = request.getParameter("partnerCode");
		String businessType = request.getParameter("businessType");
		
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		Date date = new Date();
		// 默认查询当天记录
		startTime = StringUtil.isEmpty(startTime) ? DateUtil.formatYYYYMMDD(date) : startTime;
		endTime = StringUtil.isEmpty(endTime) ? DateUtil.formatYYYYMMDD(date) : endTime;

		Integer totalRows = financeService.countCheckError(orderNo, partnerCode, businessType, null, PayPlatformEnum.HFBANK.getCode(),
				startTime, endTime);
		if (totalRows > 0) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(totalRows);
			List<BsPayCheckErrorVO> checkList = financeService.findCheckErrorList(orderNo, partnerCode, businessType, null, PayPlatformEnum.HFBANK.getCode(),
					startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));

	        ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(checkList);
	        List<Map<String, List<Object>>> excelList = new ArrayList<Map<String, List<Object>>>();
	        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
	        List<Object> titles = new ArrayList<Object>();
	        titles.add("账户类型");
	        titles.add("业务对象");
	        titles.add("业务类型");
	        titles.add("本地订单编号");
	        titles.add("恒丰订单编号");
	        titles.add("本地订单状态");
	        titles.add("恒丰订单状态");
	        titles.add("本地金额");
	        titles.add("恒丰金额");
	        titles.add("对账结果");
	        titles.add("对账日期");
	        titleMap.put("title", titles);
	        excelList.add(titleMap);

            int i = 0;
            if (!CollectionUtils.isEmpty(dataGrid)) {
                for (HashMap<String, Object> data : dataGrid) {
                    Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                    List<Object> objs = new ArrayList<Object>();
                    objs.add("存管户");
                    objs.add(data.get("partnerCode") == null ? "" : data.get("partnerCode"));
                    String displayBusinessType = data.get("businessType") == null ? "" : data.get("businessType").toString();
                    if (HFBankCheckBalanceTypeEnum.HFBANK_FINANCIAL_WITHDRAW.getCode().equals(displayBusinessType)) {
                    	objs.add("出金(投资人提现)");
					} else if (HFBankCheckBalanceTypeEnum.HFBANK_PLATFORM_WITHDRAW.getCode().equals(displayBusinessType)) {
						objs.add("出金(平台提现)");
					} else if (HFBankCheckBalanceTypeEnum.HFBANK_YUN_LOAN.getCode().equals(displayBusinessType)) {
						objs.add("出金(云贷放款)");
					} else if (HFBankCheckBalanceTypeEnum.HFBANK_SEVEN_LOAN.getCode().equals(displayBusinessType)) {
						objs.add("出金(7贷放款)");
					} else if (HFBankCheckBalanceTypeEnum.HFBANK_FINANCIAL_TOP_UP.getCode().equals(displayBusinessType)) {
						objs.add("入金(投资人充值)");
					} else if (HFBankCheckBalanceTypeEnum.HFBANK_PLATFORM_TOP_UP.getCode().equals(displayBusinessType)) {
						objs.add("入金(平台充值)");	
					} else {
						objs.add("");
					}                    
                    objs.add(data.get("orderNo") == null ? "" : data.get("orderNo"));
                    objs.add(data.get("bfOrderNo") == null ? "" : data.get("bfOrderNo"));
                    String status = data.get("transStatus") == null ? "" : data.get("transStatus").toString();
                    if (Constants.ORDER_STATUS_PAYING.equals(status)) {
                    	objs.add("处理中");
					} else if (Constants.ORDER_STATUS_SUCCESS.equals(status)) {
						objs.add("成功");
					} else if (Constants.ORDER_STATUS_FAIL.equals(status)) {
						objs.add("失败");
					} else if (Constants.ORDER_STATUS_PRE_SUCC.equals(status)) {
						objs.add("预下单成功");
					} else if (Constants.ORDER_STATUS_PRE_FAIL.equals(status)) {
						objs.add("预下单失败");	
					} else if ("SUCC".equals(status)) {
						objs.add("成功");	
					} else if ("FAIL".equals(status)) {
						objs.add("失败");
					} else if ("INIT".equals(status)) {
						objs.add("未处理");		
					} else if (StringUtil.isEmpty(status)) {
						objs.add("数据库为null或者空字符串");
					} else {
						objs.add("");
					}
                    String bfStatus = data.get("hostSysStatus") == null ? "" : data.get("hostSysStatus").toString();
                    if ("1".equals(bfStatus)) {
                    	objs.add("成功");
                    } else if ("SUCCESS".equals(bfStatus)) {
                    	objs.add("成功");
                    } else {
                    	objs.add("");
                    }
                    objs.add(data.get("sysAmount") == null ? "" : MoneyUtil.format((Double)data.get("sysAmount")));
                    objs.add(data.get("doneAmount") == null ? "" : MoneyUtil.format((Double)data.get("doneAmount")));
                    objs.add(data.get("info") == null ? "" : data.get("info"));
                    objs.add(data.get("settleDate") == null ? "" : DateUtil.format((Date)data.get("settleDate")));
                    datasMap.put("orderNo"+i, objs);
                    i++;
                    excelList.add(datasMap);
                }
            }
            try {
                ExportUtil.exportExcel(response, request, "恒丰异常订单导出", excelList);
            } catch (Exception e) {
                e.printStackTrace();
            }
		}
	}
	
	/**
	 * 财务统计-恒丰对账结果，对账结果导出页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/exportHfDailyCheckGachaIndex")
	public String exportHfSysDailyCheckGachaIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "/financeStatistics/exportHfDailyCheckGacha_index";
	}
	
	/**
	 * 财务统计-恒丰对账结果，导出对账结果表
	 * @param request
	 * @param response
	 */
	@RequestMapping("/exportHfDailyCheckGacha")
	public void exportHfSysDailyGacha(HttpServletRequest request, HttpServletResponse response) {
		String checkDate = StringUtil.isEmpty(request.getParameter("checkDate"))?"" : request.getParameter("checkDate");
		//需要导出的数据
		List<BsSysDailyCheckGachaVO> checkList = financeService.findHfbankDailyCheckGachaList(checkDate);
		
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.hfbank.daily.gacha.excel"));
			String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
			//打开excel
			fis = new FileInputStream(file);
			POIFSFileSystem fs = new POIFSFileSystem(fis);
			wb = new HSSFWorkbook(fs);
			HSSFSheet sheetBody = wb.getSheetAt(0); //单据体

			//对账日期数据填充
			HSSFRow bodyRowTemplateRow2 = sheetBody.getRow(1);
			Cell body_cell_1 = bodyRowTemplateRow2.getCell(1); //对账日期
			body_cell_1.getCellStyle();
			body_cell_1.setCellValue(checkDate.replace("-", ""));
			if (!CollectionUtils.isEmpty(checkList)) {
				
				int base = 4;
				for(int i=0;i<checkList.size();i++) {
					BsSysDailyCheckGachaVO vo = checkList.get(i);
					
					//单据体，出账入账数据填充
					HSSFRow bodyRowTemplate = sheetBody.getRow(base+i);

					Cell body_cell_3 = bodyRowTemplate.getCell(3); //对应业务金额
					body_cell_3.getCellStyle();
					body_cell_3.setCellValue(vo.getSuccOutAmount());
				
					Cell body_cell_4 = bodyRowTemplate.getCell(4); //对应业务笔数
					body_cell_4.getCellStyle();
					body_cell_4.setCellValue(vo.getSuccOutCount());
					
				}
			} else {
				// 数据为空，下载空的模版文件
				for(int i=4;i<12;i++) {					
					HSSFRow bodyRowTemplate = sheetBody.getRow(i);
				}
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
	
	//============================== 对账可疑记录查询E =====================================================
	
	//============================== 云贷自主放款未来30天待兑付查询S =====================================================
	/**
	 * 云贷自主放款未来30天待兑付查询
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/cash30Yun")
	public String cash30Yun(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {

		String pageNum = pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);;
		String numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		//获得用户购买记录
		List<Cash30YunVO> cashList = financeService.cash30Yun(Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		
		Double debtAmount = financeService.debtVipYun();
		Double standAmount = financeService.standVipYun();
		
		//将数据返回给页面
		model.put("debtAmount", debtAmount);
		model.put("standAmount", standAmount);
		model.put("cashList", cashList);
		model.put("totalRows", CollectionUtils.isEmpty(cashList)?0:cashList.size());
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);

		return "/financeStatistics/cash_30_yun";
	}
	
	//============================== 云贷自主放款未来30天待兑付查询E =====================================================

	//============================== 云贷(赞时贷)砍头息代收代付S =====================================================

	/**
	 * 云贷(赞时贷)砍头息代收代付
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/headFeeCollectPay")
	public String headFeeCollectPay(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

		// 查询参数
		String partnerCode = request.getParameter("partnerCode");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		//默认显示昨天记录
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;

		//获得云贷(赞时贷)砍头息代收代付记录
		List<HeadFeeCollectPayVO> collectList = financeService.headFeeCollectPayList(partnerCode, userName, mobile, type,startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.headFeeCollectPayCount(partnerCode, userName, mobile, type, startTime, endTime);
		HeadFeeCollectPayVO totalAmountVO = financeService.headFeeCollectPayTotalAmount(partnerCode, userName, mobile, type, startTime, endTime);

		//将数据返回给页面
		model.put("collectTotal", totalAmountVO.getCollectTotalAmount());
		model.put("payTotal", totalAmountVO.getPayTotalAmount());
		model.put("collectList", collectList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("partnerCode", partnerCode);
		model.put("type", type);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		return "/financeStatistics/headFee_collect_pay";
	}

	/**
	 * 云贷(赞时贷)砍头息代收代付，数据导出Excel
	 * @return
	 */
	@RequestMapping("/exportHeadFeeCollectPay")
	public void exportHeadFeeCollectPay(HttpServletRequest request, HttpServletResponse response) {
		String partnerCode = request.getParameter("partnerCode");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String type = request.getParameter("type");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		startTime=StringUtil.isBlank(startTime)==true?"":startTime;
		endTime=StringUtil.isBlank(endTime)==true?"":endTime;

		pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
		Integer count = financeService.headFeeCollectPayCount(partnerCode, userName, mobile, type, startTime, endTime);
		numPerPage = String.valueOf(count);
		List<HeadFeeCollectPayVO> collectList = financeService.headFeeCollectPayList(partnerCode, userName, mobile, type,startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));

		ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(collectList);
		List<Map<String, List<Object>>> excelList = new ArrayList<>();
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("序号");
		titles.add("融资客户名称");
		titles.add("手机号");
		titles.add("类型");
		titles.add("借款金额");
		titles.add("代收");
		titles.add("代付");
		titles.add("产生日期");
		titleMap.put("title", titles);
		excelList.add(titleMap);

		int i = 0;
		if (!CollectionUtils.isEmpty(dataGrid)) {
			for (HashMap<String, Object> data : dataGrid) {
				Map<String, List<Object>> datasMap = new HashMap<>();
				List<Object> objs = new ArrayList<>();
				objs.add(data.get("rowno") == null ? "" : data.get("rowno"));
				objs.add(data.get("lnUserName") == null ? "" : data.get("lnUserName"));
				objs.add(data.get("mobile") == null ? "" : data.get("mobile"));
				if ("代收".equals(data.get("type"))) {
					objs.add("代收");
				}else if ("代付".equals(data.get("type"))) {
					objs.add("代付");
				}else {
					objs.add(data.get("type"));
				}
				objs.add(data.get("loanAmount") == null || (Double)data.get("loanAmount") == 0 ? "" : MoneyUtil.format((Double)data.get("loanAmount")));
				objs.add(data.get("collectAmount") == null || (Double)data.get("collectAmount") == 0 ? "" : MoneyUtil.format((Double)data.get("collectAmount")));
				objs.add(data.get("payAmount") == null || (Double)data.get("payAmount") == 0 ? "" : MoneyUtil.format((Double)data.get("payAmount")));
				objs.add(data.get("time") == null || StringUtil.isEmpty((String)data.get("time")) ? "" : DateUtil.format(DateUtil.parseDateTime((String)data.get("time"))));
				datasMap.put("order"+i, objs);
				i++;
				excelList.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "云贷(赞时贷)砍头息代收代付导出", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//============================== 云贷(赞时贷)砍头息代收代付E =====================================================


	//============================== 存管后会计分录财务统计 赞分期产品统计(恒丰)S ==============================

	/**
	 * 财务统计-出借匹配统计列表-赞分期出借
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/querylendingMatchForZan")
	public String lendingMatchStatisticsForZan(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userType = request.getParameter("userType");
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

		//获得用户购买记录
		List<CorpusBuyStatisticsVO> userList = financeService.queryLendingMatchForZan(userType,userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.queryLendingMatchForZanCount(userType,userName, mobile, startTime, endTime);
		CorpusBuyStatisticsVO totalCorpusBuy = financeService.queryLendingMatchForZanTotalAmount(userType,userName, mobile, startTime, endTime);

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

		return "/financeStatistics/lending_match_statistics_zan";
	}

	/**
	 * 财务统计-出借匹配统计-赞分期出借-导出excel
	 * @param request
	 * @param response
     */
	@RequestMapping("/exportLendingMatchForZan")
	public void exportCorpusBuy_forZan(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String userType = request.getParameter("userType");
		//需要导出的数据
		Integer count = financeService.queryLendingMatchForZanCount(userType,userName, mobile, startTime, endTime);
		List<CorpusBuyStatisticsVO> userList = financeService.queryLendingMatchForZan(userType,userName, mobile, startTime, endTime, 1, count);

		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.lending.match.excel"));//沿用原本金购买的excel模板
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
				Cell head_cell_1 = headRow.createCell(1); //单据编号("120"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("120"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("120"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("120"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());

				Cell body_cell_3 = bodyRow.createCell(3); //投资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());

				Cell body_cell_4 = bodyRow.createCell(4); //融资客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getPropertyCode());

				Cell body_cell_6 = bodyRow.createCell(6); //部门
				body_cell_6.setCellStyle(textCellStyle);
				body_cell_6.setCellValue("101");

				Cell body_cell_7 = bodyRow.createCell(7); //投资金额
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getBalance());

				Cell body_cell_8 = bodyRow.createCell(8); //融资利息
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getFinanceInterest());

				Cell body_cell_9 = bodyRow.createCell(9); //应付投资客户利息
				body_cell_9.setCellStyle(smallCellStyle);
				body_cell_9.setCellValue(vo.getUserInterest() == null ? 0d : vo.getUserInterest());
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
	 * 财务统计-支付融资客户-赞分期出借
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/queryPayFinanceForZan")
	public String payFinanceStatisticsForZan(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
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
		startTime = StringUtil.isBlank(startTime) == true ? yestoday : startTime;
		endTime = StringUtil.isBlank(endTime) == true ? yestoday : endTime;

		//获得用户购买记录
		List<PayFinanceStatisticsVO> userList = financeService.payFinanceStatisticsForZan(userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.payFinanceStatisticsCountForZan(userName, mobile, startTime, endTime);
		PayFinanceStatisticsVO payFinance = financeService.payFinanceStatisticsTotalAmountForZan(userName, mobile, startTime, endTime);
		//将数据返回给页面
		model.put("payFinance", payFinance);
		model.put("userList", userList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		return "/financeStatistics/pay_finance_statistics_zan";
	}

	/**
	 * 财务统计-支付融资客户-赞分期出借 导出excel
	 * @param request
	 * @param response
     */
	@RequestMapping("/exportPayFinanceForZan")
	public void exportPayFinanceForZan(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		//需要导出的数据
		Integer count = financeService.payFinanceStatisticsCountForZan(userName, mobile, startTime, endTime);
		List<PayFinanceStatisticsVO> userList = financeService.payFinanceStatisticsForZan(userName, mobile, startTime, endTime, 1, count);

		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.pay.financeForZan.excel"));//沿用原支付融资客户的excel模板
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
				Cell head_cell_1 = headRow.createCell(1); //单据编号("130"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("130"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("130"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("130"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());

				Cell body_cell_3 = bodyRow.createCell(3); //投资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());

				Cell body_cell_4 = bodyRow.createCell(4); //融资客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getPropertyCode());

				Cell body_cell_6 = bodyRow.createCell(6); //部门
				body_cell_6.setCellStyle(textCellStyle);
				body_cell_6.setCellValue("101");

				Cell body_cell_7 = bodyRow.createCell(7); //投资金额
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getBalance());

				Cell body_cell_8 = bodyRow.createCell(8); //融资客户利息
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getFinanceInterest());

				Cell body_cell_9 = bodyRow.createCell(9); //融资客户本息合计
				body_cell_9.setCellStyle(smallCellStyle);
				body_cell_9.setCellValue(vo.getFinanceTotalAmount());

				Cell body_cell_10 = bodyRow.createCell(10); //应付投资客户利息
				body_cell_10.setCellStyle(smallCellStyle);
				body_cell_10.setCellValue(vo.getUserInterest() == null ? 0d : vo.getUserInterest());

				Cell body_cell_11 = bodyRow.createCell(11); //公司利息收入
				body_cell_11.setCellStyle(smallCellStyle);
				body_cell_11.setCellValue(vo.getInterestIncome() == null ? 0d : vo.getInterestIncome());
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

	//============================== 存管后会计分录财务统计 赞分期产品统计(恒丰)E ==============================

	//============================== 返还投资人手续费统计 S =====================================================
	/**
	 * 返还投资人手续费统计
	 * 统计订单表中 返还投资人手续费 的转账记录
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/returnFeeToInvestor")
	public String returnFeeToInvestor(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
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

		//默认显示今日
		String yestoday = DateUtil.formatYYYYMMDD(new Date());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;

		//返还投资人手续费明细列表
		List<ReturnFeeToInvestorVO> detailList = financeService.returnFeeToInvestor(partnerCode, userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.returnFeeToInvestorCount(partnerCode, userName, mobile, startTime, endTime);
		//返还投资人手续费总额（不分页）
		Double returnTotal = financeService.returnFeeToInvestorTotalAmount(partnerCode, userName, mobile, startTime, endTime);

		//将数据返回给页面
		model.put("returnTotal", returnTotal);
		model.put("detailList", detailList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("partnerCode", partnerCode);

		return "/financeStatistics/return_fee_to_investor";
	}

	@RequestMapping("/exportReturnFeeToInvestor")
	public void exportReturnFeeToInvestor(HttpServletRequest request, HttpServletResponse response) {
		String partnerCode = request.getParameter("partnerCode");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		//默认显示今日
		String yestoday = DateUtil.formatYYYYMMDD(new Date());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;

		//需要导出的数据
		Integer count = financeService.returnFeeToInvestorCount(partnerCode,userName, mobile, startTime, endTime);
		List<ReturnFeeToInvestorVO> detailList = financeService.returnFeeToInvestor(partnerCode, userName, mobile, startTime, endTime, 1, count);

		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.return.fee.to.investor"));
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
			for(int i=0;i<detailList.size();i++) {
				ReturnFeeToInvestorVO vo = detailList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("071"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("071"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("071"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("071"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //投资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getUserId());

				Cell body_cell_3 = bodyRow.createCell(3); //投资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());

				Cell body_cell_4 = bodyRow.createCell(4); //部门
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue("101");

				Cell body_cell_5 = bodyRow.createCell(5); //补利息金额
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(-vo.getReturnFeeAmount());

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

	//============================== 返还投资人手续费统计 E =====================================================



	//============================== （恒丰）赞分期产品统计-融资客户结算 S  balance.finance.excel=====================================================
	/**
	 * （恒丰）赞分期产品统计-融资客户结算
	 * 融资客户结算指达飞结算给币港湾的钱，需要核算到具体的客户，与支付融资客户必须对应
	 * 系统回款
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/queryBalanceFinanceDepZan")
	public String depZanBalanceFinanceStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;

		//获得用户购买记录
		List<BalanceFinanceStatisticsVO> userList = financeService.depZanBalanceFinanceStatistics(userName, mobile, note, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.depZanBalanceFinanceStatisticsCount(userName, mobile, note, startTime, endTime);
		//BalanceFinanceStatisticsVO totalBalanceFinance = financeService.balanceFinanceStatisticsTotalAmount(userName, mobile, note, startTime, endTime,count);
		//将数据返回给页面
		BalanceFinanceStatisticsVO totalBalanceFinance = new BalanceFinanceStatisticsVO();
		totalBalanceFinance.setTotalBalance(null);
		totalBalanceFinance.setTotalFinanceInterest(null);
		model.put("totalBalanceFinance", totalBalanceFinance);
		model.put("userList", userList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("note", note);
		model.put("startTime", startTime);
		model.put("endTime", endTime);

		return "/financeStatistics/dep_zan_balance_finance_statistics";
	}

	@ResponseBody
	@RequestMapping("/queryBalanceFinanceTotalDepZan")
	public Map<String, Object> depZanBalanceFinanceTotalStatistics(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {

		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;

		//获得用户购买记录
		//List<BalanceFinanceStatisticsVO> userList = financeService.balanceFinanceStatistics(userName, mobile, note, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.depZanBalanceFinanceStatisticsCount(userName, mobile, note, startTime, endTime);
		BalanceFinanceStatisticsVO totalBalanceFinance = financeService.depZanBalanceFinanceStatisticsTotalAmount(userName, mobile, note, startTime, endTime,count);
		//将数据返回给页面
		model.put("totalBalance", totalBalanceFinance.getTotalBalance());
		model.put("totalFinanceInterest", totalBalanceFinance.getTotalFinanceInterest());
		return model;
	}


	@RequestMapping("/exportBalanceFinanceDepZan")
	public void exportDepZanBalanceFinance(HttpServletRequest request, HttpServletResponse response) {
		String userName = request.getParameter("userName") != null ? request.getParameter("userName").trim() : null;
		String mobile = request.getParameter("mobile") != null ? request.getParameter("mobile").trim() : null;
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String note = request.getParameter("note");
		//需要导出的数据
		Integer count = financeService.depZanBalanceFinanceStatisticsCount(userName, mobile, note, startTime, endTime);
		List<BalanceFinanceStatisticsVO> userList = financeService.depZanBalanceFinanceStatistics(userName, mobile, note, startTime, endTime, 1, count);

		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.zan.balance.finance.excel"));
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
				BalanceFinanceStatisticsVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号(orderNo)
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
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getOrderNo());

				Cell body_cell_2 = bodyRow.createCell(2); //融资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getPropertyCode());

				Cell body_cell_4 = bodyRow.createCell(4); //客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getCustomerCode());
				
				Cell body_cell_5 = bodyRow.createCell(5); //客户代码
				body_cell_5.setCellStyle(textCellStyle);
				body_cell_5.setCellValue(vo.getUserName());

				Cell body_cell_6 = bodyRow.createCell(6); //投资金额
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue(vo.getBalance());

				Cell body_cell_7 = bodyRow.createCell(7); //融资客户利息
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getFinanceInterest());

				Cell body_cell_8 = bodyRow.createCell(8); //融资客户本息合计
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getFinanceTotalAmount());

				Cell body_cell_9 = bodyRow.createCell(9); //宝付账户变动金额
				body_cell_9.setCellStyle(smallCellStyle);
				body_cell_9.setCellValue(vo.getTransAmountTotal());

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
	//============================== （恒丰）赞分期产品统计-融资客户结算  E =====================================================
	//============================== 投资到期统计 S =====================================================
	/**
	 * 投资到期统计
	 * 统计bs_deposition_return 的回款金额（包含返还手续费部分（平台转个人），但不包括补息奖励金）
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/investExpire")
	public String investExpire(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		String type = request.getParameter("type");
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

		//返还投资人手续费明细列表
		List<InvestExpireVO> detailList = financeService.investExpire(partnerCode, type, userName, mobile, startTime, endTime, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
		Integer count = financeService.investExpireCount(partnerCode, type, userName, mobile, startTime, endTime);
		Double expireTotal = financeService.investExpireTotalAmount(partnerCode, type, userName, mobile, startTime, endTime);

		//将数据返回给页面
		model.put("partnerCode", partnerCode);
		model.put("expireTotal", expireTotal);
		model.put("detailList", detailList);
		model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("type", type);

		return "/financeStatistics/invest_expire";
	}

	@RequestMapping("/exportInvestExpire")
	public void exportInvestExpire(HttpServletRequest request, HttpServletResponse response) {
		String partnerCode = request.getParameter("partnerCode");
		String type = request.getParameter("type");
		String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		startTime=StringUtil.isBlank(startTime)==true?yestoday:startTime;
		endTime=StringUtil.isBlank(endTime)==true?yestoday:endTime;

		//需要导出的数据
		Integer count = financeService.investExpireCount(partnerCode, type,userName, mobile, startTime, endTime);
		List<InvestExpireVO> detailList = financeService.investExpire(partnerCode, type, userName, mobile, startTime, endTime, 1, count);

		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.invest.expire"));
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
			for(int i=0;i<detailList.size();i++) {
				InvestExpireVO vo = detailList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowno());
				Cell head_cell_1 = headRow.createCell(1); //单据编号("070"+orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue("070"+vo.getOrderNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTime());

				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowno());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号("070"+orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue("070"+vo.getOrderNo());
				Cell body_cell_2 = bodyRow.createCell(2); //投资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getCustomerCode());

				Cell body_cell_3 = bodyRow.createCell(3); //投资客户名称
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getUserName());

				Cell body_cell_4 = bodyRow.createCell(4); //部门
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue("101");

				Cell body_cell_5 = bodyRow.createCell(5); //到期本金及利息
				body_cell_5.setCellStyle(smallCellStyle);
				body_cell_5.setCellValue(vo.getCorpusAndInterest());

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

	//============================== 投资到期统计 E =====================================================

	//============================== 资产方费用结算查询 S =====================================================
	@RequestMapping("/queryPartnerServiceFee/index")
	public String queryPartnerServiceFee(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String queryFlag = request.getParameter("queryFlag");
		
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		
		if (StringUtil.isNotEmpty(queryFlag) && queryFlag.equals("QUERY")) {			
			// 资产方费用结算集合
			BsPartnerServiceFeeVO bsPartnerServiceFeeVO = financialStatisticsService.queryPartnerServiceFee(partnerCode, startTime, endTime);
			model.put("partnerServiceFeeVo", bsPartnerServiceFeeVO);
		}
		
		model.put("totalRows", 1);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("partnerCode", partnerCode);
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		
		return "/financeStatistics/partner_service_fee_index";
	}

	@RequestMapping("/queryPartnerServiceFee/exportXls")
	public void exportPartnerServiceFee(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");

		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		if (StringUtil.isEmpty(partnerCode)) {
			partnerCode = PartnerEnum.YUN_DAI_SELF.getCode();
		}

		// 资产方费用结算集合
		BsPartnerServiceFeeVO bsPartnerServiceFeeVO = financialStatisticsService.queryPartnerServiceFee(partnerCode, startTime, endTime);
		
		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("短信条数");
		titles.add("鉴权笔数");
		titles.add("放款代发笔数");
		titles.add("还款代扣笔数");
		titles.add("协议支付手续费");
		titles.add("放款金额");
		titleMap.put("title", titles);
		excelList.add(titleMap);

		int i = 0;
		Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
		List<Object> obj = new ArrayList<Object>();
		obj.add(bsPartnerServiceFeeVO.getSmsCount());
		obj.add(bsPartnerServiceFeeVO.getAuthCount());
		obj.add(bsPartnerServiceFeeVO.getLoanCount());
		obj.add(bsPartnerServiceFeeVO.getRepayCount());
		obj.add(bsPartnerServiceFeeVO.getAgreementFeeAmount());
		obj.add(bsPartnerServiceFeeVO.getLoanAmount());
		datasMap.put("partnerServiceFee"+(i), obj);
		excelList.add(datasMap);
		try {
			ExportUtil.exportExcel(response, request, "资产方费用结算查询导出", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 协议支付手续费明细
	 * @param request
	 * @param response
	 * @param model
     * @return
     */
	@RequestMapping("/queryAgreementFeeDetail/index")
	public String queryAgreementFeeDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");
		List<AgreementFeeDetailVO> list = financialStatisticsService.queryAgreementFeeDetailList(partnerCode, startTime, endTime);
		model.put("detailList", list);
		return "/financeStatistics/agreementFee_detail";
	}

	/**
	 * 协议支付手续费明细导出
	 * @param request
	 * @param response
	 * @param model
     */
	@RequestMapping("/queryAgreementFeeDetail/exportXls")
	public void exportAgreementFeeDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String partnerCode = request.getParameter("partnerCode");
		String startTime = request.getParameter("startTime");
		String endTime = request.getParameter("endTime");

		if (StringUtil.isEmpty(partnerCode)) {
			partnerCode = PartnerEnum.YUN_DAI_SELF.getCode();
		}

		List<AgreementFeeDetailVO> list = financialStatisticsService.queryAgreementFeeDetailListExportXls(partnerCode, startTime, endTime);
		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("订单编号");
		titles.add("交易金额");
		titles.add("交易手续费");
		titles.add("时间");
		titleMap.put("title", titles);
		excelList.add(titleMap);
		if(!CollectionUtils.isEmpty(list)) {
			int i = 0;
			for (AgreementFeeDetailVO data : list) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.getOrderNo());
				obj.add(data.getAmount());
				obj.add(data.getAgreementFeeAmount());
				obj.add(DateUtil.format(data.getUpdateTime()));
				datasMap.put("agreementFeeDetail"+(++i), obj);
				excelList.add(datasMap);
			}
		}

		try {
			ExportUtil.exportExcel(response, request, "协议支付手续费明细导出", excelList);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	//============================== 资产方费用结算查询 E =====================================================

}
