package com.pinting.manage.controller;

import com.pinting.business.model.vo.LoanRepayVO;
import com.pinting.business.service.manage.BgwLaboratoryService;
import com.pinting.manage.service.ExportExcelService;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 币港湾实验室功能Controller
 *
 * @author shh
 * @date 2018/6/1 13:17
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@RequestMapping(value = "/bgwLaboratory")
public class BgwLaboratoryController {

    private static final String EXPORT_EXCEL_DOWNLOAD_REDIS_KEY = ExportUtil.EXPORT_EXCEL_DOWNLOAD + "LoanRepayList";

    @Autowired
    private BgwLaboratoryService bgwLaboratoryService;
    @Autowired
    private ExportExcelService exportExcelService;

    /**
     * 借款用户还款情况查询
     *
     * @param record
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/getLoanRepayList")
    public String getLoanRepayList(LoanRepayVO record, HttpServletRequest request, HashMap<String, Object> model) {
        Integer pageNum = record.getPageNum();
        Integer numPerPage = record.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            record.setPageNum(pageNum);
            record.setNumPerPage(numPerPage);
        }

        String orderDirection = record.getOrderDirection();
        String orderField = record.getOrderField();
        if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
            record.setOrderDirection(orderDirection);
            record.setOrderField(orderField);
        }

        List<LoanRepayVO> loanRepayList = new ArrayList<LoanRepayVO>();
        int count = bgwLaboratoryService.queryLoanRepayCount(record);
        if (count > 0) {
            loanRepayList = bgwLaboratoryService.queryLoanRepayList(record);
        }

        model.put("totalRows", count);
        model.put("record", record);
        model.put("loanRepayList", loanRepayList);
        model.put("excelDownloadHelperVO", exportExcelService.getExpoertExcelVOInfo(EXPORT_EXCEL_DOWNLOAD_REDIS_KEY));
        return "/bgwlaboratory/get_loan_repay_list";
    }

    /**
     * 借款用户还款情况 创建生成导出excel文件
     */
    @RequestMapping("/getLoanRepayList/createExportXls")
    @ResponseBody
    public Map<Object, Object> getLoanRepayListCreateExportExcel(LoanRepayVO record) {
        record.setPageNum(0);
        record.setNumPerPage(Integer.MAX_VALUE);

        String orderDirection = record.getOrderDirection();
        String orderField = record.getOrderField();
        if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
            record.setOrderDirection(orderDirection);
            record.setOrderField(orderField);
        }

        List<LoanRepayVO> loanRepayList = new ArrayList<LoanRepayVO>();
        int count = bgwLaboratoryService.queryLoanRepayCount(record);
        // 1、导出excel数据小于60000的部分
        if (count > 0) {
            int pageNum = 1; // 起始页码1
            int numPerpage = 20000; // 每页2万行记录最优
            do {
                record.setPageNum(pageNum);
                record.setNumPerPage(numPerpage > count ? count : numPerpage);
                pageNum++;
                count = count - numPerpage;
                loanRepayList.addAll(bgwLaboratoryService.queryLoanRepayList(record));

            } while (count > 0); // 每次查千条数据
        }

        List<Map<String, List<Object>>> excelList = new ArrayList<Map<String, List<Object>>>();
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("资产方");
        titles.add("用户编号");
        titles.add("用户姓名");
        titles.add("借款次数");
        titles.add("提前还款次数");
        titles.add("按期还款次数");
        titles.add("本金逾期次数");
        titles.add("利息逾期次数");
        titleMap.put("title", titles);
        excelList.add(titleMap);

        if (!CollectionUtils.isEmpty(loanRepayList)) {
            int i = 0;
            for (LoanRepayVO data : loanRepayList) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                String partnerCode = data.getPartnerCode();
                if (Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(partnerCode)) {
                    partnerCode = "云贷存管";
                }
                if (Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI_SELF.equals(partnerCode)) {
                    partnerCode = "七贷存管";
                }
                obj.add(partnerCode);
                obj.add(data.getLoanUserId());
                obj.add(data.getLoanUserName());
                obj.add(data.getLoanCount());
                obj.add(data.getAdvancedRepayCount());
                obj.add(data.getRepayCount());
                obj.add(data.getOverduePrincipalCompensate());
                obj.add(data.getOverdueInterestCompensate());

                datasMap.put("loanRepayList" + (++i), obj);
                excelList.add(datasMap);
            }
            loanRepayList.clear();
        }

        ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = exportExcelService.createExportExcel(EXPORT_EXCEL_DOWNLOAD_REDIS_KEY, "借款用户还款情况导出", excelList);
        Map dataMap = new HashMap<Object, Object>();
        dataMap.put("excelDownloadHelperVO", excelDownloadHelperVO);
        return ReturnDWZAjax.success("导出操作成功", dataMap);
    }

    /**
     * 借款用户还款情况 导出excel
     */
    @RequestMapping("/getLoanRepayList/exportXls")
    public void getLoanRepayListExportExcel(HttpServletRequest request, HttpServletResponse response) {
        ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = exportExcelService.getExpoertExcelVOInfo(EXPORT_EXCEL_DOWNLOAD_REDIS_KEY);
        ExportUtil.exportExcel(excelDownloadHelperVO.getFileName(), excelDownloadHelperVO.getFileFullPath(), request, response);
    }
}
