package com.pinting.manage.controller;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.model.vo.LnCustomerSettlementVO;
import com.pinting.business.service.manage.LnCustomerSettlementService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

/**
 * Created by cyb on 2017/11/16.
 */
@Controller
public class LnCustomerSettlementController {

    @Autowired
    private LnCustomerSettlementService lnCustomerSettlementService;

    /**
     * 融资客户结算（总）
     *
     * @return
     */
    @RequestMapping("/loan/customer/settle_index")
    public String dataIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model,
                            String fnUserName, String mobile, String repayType, String startDate, String endDate,
							String partnerCode, String queryPartnerBusinessFlag, Integer numPerPage, Integer pageNum) {
		fnUserName = trimParam(fnUserName);
		mobile = trimParam(mobile);
		repayType = trimParam(repayType);
		startDate = trimParam(startDate);
		endDate = trimParam(endDate);
		partnerCode = trimParam(partnerCode);
		pageNum = pageNum == null ? 1 : pageNum;
		numPerPage = numPerPage == null ? 20 : numPerPage;
		if(StringUtil.isBlank(queryPartnerBusinessFlag)){
			queryPartnerBusinessFlag = "777";
		}

		int count = 0;
		Double sum = 0d;
		if (StringUtil.isBlank(startDate) || StringUtil.isBlank(endDate)) {
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			calendar.add(Calendar.DAY_OF_MONTH, -1);
			String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
			startDate=StringUtil.isBlank(startDate)==true?yestoday:startDate;
			endDate=StringUtil.isBlank(endDate)==true?yestoday:endDate;
		}else {
	        count = lnCustomerSettlementService.countLnCustomerSettlementList(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag);
	        if(count > 0) {
	            List<LnCustomerSettlementVO> list = lnCustomerSettlementService.queryLnCustomerSettlementList(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag, numPerPage, pageNum);
	            model.put("list", list);
	            sum = lnCustomerSettlementService.sumLnCustomerSettlementInterest(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag);
	           
	        }
		}
		model.put("sum", sum);
        model.put("count", count);
        model.put("fnUserName", fnUserName);
        model.put("mobile", mobile);
        model.put("repayType", repayType);
        model.put("startDate", startDate);
        model.put("endDate", endDate);
        model.put("partnerCode", partnerCode);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
//		model.put("partnerBusinessFlag", partnerBusinessFlag);
		model.put("queryPartnerBusinessFlag", queryPartnerBusinessFlag);
        return "loan/settle_index";
    }

    private String trimParam(String param) {
        return StringUtil.isBlank(param) ? null : StringUtil.trimStr(param);
    }


    /**
     *	融资客户结算（总）导出
     * 	export
     */
    @RequestMapping("/loan/customer/exportXls")
    public void exportXls(String fnUserName, String mobile, String repayType, String startDate, String endDate, String partnerCode, String queryPartnerBusinessFlag, Integer numPerPage, Integer pageNum, HttpServletResponse response,
                          HttpServletRequest request) {
		fnUserName = trimParam(fnUserName);
		mobile = trimParam(mobile);
		repayType = trimParam(repayType);
		startDate = trimParam(startDate);
		endDate = trimParam(endDate);
		partnerCode = trimParam(partnerCode);
    	Integer count =  lnCustomerSettlementService.countLnCustomerSettlementList(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag);
        List<LnCustomerSettlementVO> userList = lnCustomerSettlementService.queryLnCustomerSettlementList(fnUserName, mobile, repayType, startDate, endDate, partnerCode, queryPartnerBusinessFlag, count, 1);

    	
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.balance.finance.total.excel"));
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
				LnCustomerSettlementVO vo = userList.get(i);
				//单据头
				HSSFRow headRow = sheetHead.createRow(base+i);
				Cell head_cell_0 = headRow.createCell(0); //序号
				head_cell_0.setCellStyle(basicStyle);
				head_cell_0.setCellValue(vo.getRowNo());
				Cell head_cell_1 = headRow.createCell(1); //单据编号(orderNo)
				head_cell_1.setCellStyle(textCellStyle);
				head_cell_1.setCellValue(vo.getBillNo());
				Cell head_cell_2 = headRow.createCell(2); //日期
				head_cell_2.setCellStyle(dateCellStyle);
				head_cell_2.setCellValue(vo.getTransTime());
				
				//单据体
				HSSFRow bodyRow = sheetBody.createRow(base+i);
				Cell body_cell_0 = bodyRow.createCell(0); //序号
				body_cell_0.setCellStyle(basicStyle);
				body_cell_0.setCellValue(vo.getRowNo());
				Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
				body_cell_1.setCellStyle(textCellStyle);
				body_cell_1.setCellValue(vo.getBillNo());
				
				Cell body_cell_2 = bodyRow.createCell(2); //投资客户代码
				body_cell_2.setCellStyle(textCellStyle);
				body_cell_2.setCellValue(vo.getFnCustomerId());
				
				Cell body_cell_3 = bodyRow.createCell(3); //投资客户
				body_cell_3.setCellStyle(textCellStyle);
				body_cell_3.setCellValue(vo.getFnUserName());
				
				Cell body_cell_4 = bodyRow.createCell(4); //融资客户代码
				body_cell_4.setCellStyle(textCellStyle);
				body_cell_4.setCellValue(vo.getLnCustomerId());
				
				Cell body_cell_5 = bodyRow.createCell(5); //融资客户名称
				body_cell_5.setCellStyle(textCellStyle);
				body_cell_5.setCellValue(vo.getLnUserName());

				
				Cell body_cell_6 = bodyRow.createCell(6); //部门默认101
				body_cell_6.setCellStyle(smallCellStyle);
				body_cell_6.setCellValue("101");
				
				Cell body_cell_7 = bodyRow.createCell(7); //融资金额
				body_cell_7.setCellStyle(smallCellStyle);
				body_cell_7.setCellValue(vo.getPlanPrincipal());
				
				Cell body_cell_8 = bodyRow.createCell(8); //融资客户应付利息
				body_cell_8.setCellStyle(smallCellStyle);
				body_cell_8.setCellValue(vo.getLnPlanInterest());
				
				Cell body_cell_9 = bodyRow.createCell(9); //应付投资客户利息
				body_cell_9.setCellStyle(smallCellStyle);
				body_cell_9.setCellValue(vo.getFnPlanInterest());
				
				Cell body_cell_10 = bodyRow.createCell(10); //手续费
				body_cell_10.setCellStyle(smallCellStyle);
				body_cell_10.setCellValue(vo.getFee());
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

}
