package com.pinting.manage.controller;

import com.pinting.business.model.vo.BsCompanyDeptVO;
import com.pinting.business.model.vo.RepayDSDFVO;
import com.pinting.business.service.manage.FinancialDSDFService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
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
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;

@Controller
@RequestMapping("/financial/dsdf")
public class FinancialDSDFController {
	
	@Autowired
	private FinancialDSDFService financialDSDFService;

	/**
     * 归集户代收代付
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/repayAct/index")
    public String repayActIndex(Map<String, Object> model, HttpServletRequest request, 
    		HttpServletResponse response) {
    	String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String timeStart = request.getParameter("startTime");
		String timeEnd = request.getParameter("endTime");
		String type = request.getParameter("type");
		String status = request.getParameter("status");
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		String queryFlag = request.getParameter("queryFlag");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
		}
		if(StringUtil.isEmpty(timeStart) && StringUtil.isEmpty(timeEnd)){
			timeStart = DateUtil.formatYYYYMMDD(new Date());
			timeEnd = DateUtil.formatYYYYMMDD(new Date());
		}
		Double ds_amount = 0d;
		Double df_amount = 0d;
		Integer count = 0;
		if (StringUtil.isNotEmpty(queryFlag) && queryFlag.equals("QUERYFLAG")) {
			count = financialDSDFService.countRepayDSDF(userName, mobile, type, timeStart, timeEnd, status);
			if (count != null && count > 0) {
				List<RepayDSDFVO> list = financialDSDFService.getRepayDSDFList(userName, mobile, type,
						timeStart, timeEnd, status, Integer.valueOf(pageNum), Integer.valueOf(numPerPage));
				ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(list);
				model.put("dataGrid", dataGrid);

				RepayDSDFVO vo = financialDSDFService.sumRepayDSDF(userName, mobile, type, timeStart, timeEnd, status);
				ds_amount = vo.getDSAmount();
				df_amount = vo.getDFAmount();
			}
		}

        model.put("totalRows", count);
		model.put("pageNum", pageNum);
		model.put("numPerPage", numPerPage);
		model.put("userName", userName);
		model.put("mobile", mobile);
		model.put("startTime", timeStart);
		model.put("endTime", timeEnd);
		model.put("type", type);
		model.put("status", status);
		model.put("ds_amount", ds_amount);
		model.put("df_amount", df_amount);
        return "/financialStatistics/dsdf/repayIndex";
    }
    
    
    /**
	   * 归集户代收代付 导出
	   * @param request
	   * @param response
	   */
	  @RequestMapping("/repayAct/export")
	  public void exportRepayAct(BsCompanyDeptVO req,HttpServletRequest request, HttpServletResponse response) {
	      
		  String userName = request.getParameter("userName");
		  String mobile = request.getParameter("mobile");
		  String timeStart = request.getParameter("startTime");
		  String timeEnd = request.getParameter("endTime");
		  String type = request.getParameter("type");
		  String status = request.getParameter("status");
		  if(StringUtil.isEmpty(timeStart) && StringUtil.isEmpty(timeEnd)){
			  timeStart = DateUtil.formatYYYYMMDD(new Date());
			  timeEnd = DateUtil.formatYYYYMMDD(new Date());
		  }
		  Integer count = financialDSDFService.countRepayDSDF(userName, mobile, type, timeStart, timeEnd, status);
	      //需要导出的数据
		  List<RepayDSDFVO> list = financialDSDFService.getRepayDSDFList(userName, mobile, type,
     			 timeStart, timeEnd, status, Constants.MANAGE_DEFAULT_PAGENUM, count);
	      
	      
		  ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(list);
		  List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
		  Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		  List<Object> titles = new ArrayList<Object>();
		  titles.add("融资客户名称");
		  titles.add("手机号");
		  titles.add("类型");
		  titles.add("代收");
		  titles.add("代付");
		  titles.add("创建日期");
		  titles.add("更新日期");
		  titles.add("状态");
		  titleMap.put("title", titles);
		  excelList.add(titleMap);
		  
          int i = 0;
          if (!CollectionUtils.isEmpty(dataGrid)) {
              for (HashMap<String, Object> data : dataGrid) {
                  Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
                  List<Object> objs = new ArrayList<Object>();
                  objs.add(data.get("userName") == null ? "" : data.get("userName"));
                  objs.add(data.get("userMobile") == null ? "" : data.get("userMobile"));
                  if(data.get("DSDFType") != null && "DS".equals(data.get("DSDFType"))){
                	  objs.add("代收");
                	  objs.add(data.get("amount"));
                	  objs.add(0d);
                  }else if(data.get("DSDFType") != null && "DF".equals(data.get("DSDFType"))){
                	  objs.add("代付");
                	  objs.add(0d);
                	  objs.add((Double)data.get("amount"));
                  }

				  objs.add(data.get("createTime") == null ? "" : DateUtil.formatYYYYMMDD((Date)data.get("createTime")));
                  objs.add(data.get("doneTime") == null ? "" : DateUtil.formatYYYYMMDD((Date)data.get("doneTime")));
				  if (Constants.ORDER_STATUS_SUCCESS.equals(data.get("status").toString())) {
                	  objs.add("成功");
                  } else if (Constants.ORDER_STATUS_PAYING.equals(data.get("status").toString())) {
                	  objs.add("处理中");
                  } else {
                	  objs.add(data.get(""));
                  }
                  datasMap.put("order"+i, objs);
                  i++;
                  excelList.add(datasMap);
              }
              
          }
          
          RepayDSDFVO vo = financialDSDFService.sumRepayDSDF(userName, mobile, type, timeStart, timeEnd, status);
          Double ds_amount = vo.getDSAmount();
          Double df_amount = vo.getDFAmount();
          Map<String,List<Object>> endMap = new HashMap<String, List<Object>>();
          List<Object> endTitles = new ArrayList<Object>();
          endTitles.add("代收总额(元)：");
          endTitles.add(ds_amount == null?"":MoneyUtil.format(ds_amount));
          endTitles.add("代付总额(元)：");
          endTitles.add(df_amount == null?"":MoneyUtil.format(df_amount));
          endMap.put("endTitles1", endTitles);
          excelList.add(endMap);
          try {
              ExportUtil.exportExcel(response, request, "归集户代收代付数据", excelList);
          } catch (Exception e) {
              e.printStackTrace();
          }
	  }

	/**
	 * 归集户代收代付，进入下载批次流水页面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/exportBatchFlowIndex")
	public String exportBatchFlowPage(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		return "/financialStatistics/dsdf/exportBatchFlow_index";
	}

	/**
	 * 归集户代收代付，批次流水数据下载
	 * @param request
	 * @param response
     */
	@RequestMapping("/exportBatchFlowData")
	public void exportBatchFlowData(HttpServletRequest request, HttpServletResponse response) {
		String checkDate = StringUtil.isEmpty(request.getParameter("checkDate"))?"" : request.getParameter("checkDate");

		// 查询下载批次流水数据
		List<RepayDSDFVO> checkList = financialDSDFService.queryRepayDSDFBatchFlowList(checkDate);
		FileInputStream fis = null;
		HSSFWorkbook wb = null;
		try {
			File file = new File(GlobEnvUtil.get("dep.dsdf.batchFlow.excel"));
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
				int base = 3;
				for(int i=0;i<checkList.size();i++) {
					RepayDSDFVO vo = checkList.get(i);
					//单据体，出账入账数据填充
					HSSFRow bodyRowTemplate = sheetBody.createRow(base+i);

					Cell body_cellContent_0 = bodyRowTemplate.createCell(0); //业务类型
					body_cellContent_0.setCellStyle(basicStyle);
					body_cellContent_0.setCellValue("DS".equals(vo.getDSDFType()) ? "代收" : "代付");

					Cell body_cellContent_1 = bodyRowTemplate.createCell(1); //金额
					body_cellContent_1.setCellStyle(textCellStyle);
					body_cellContent_1.setCellValue(vo.getAmount());

					Cell body_cellContent_2 = bodyRowTemplate.createCell(2); //订单状态
					body_cellContent_2.setCellStyle(textCellStyle);
					body_cellContent_2.setCellValue(Constants.ORDER_STATUS_PAYING.equals(vo.getStatus().toString()) ? "处理中" : "成功");

					Cell body_cellContent_3 = bodyRowTemplate.createCell(3); //批次订单号
					body_cellContent_3.setCellStyle(textCellStyle);
					body_cellContent_3.setCellValue(vo.getOrderNo());

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

}
