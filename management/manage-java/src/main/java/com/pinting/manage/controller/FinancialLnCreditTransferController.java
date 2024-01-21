package com.pinting.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.model.vo.BalanceFinanceStatisticsVO;
import com.pinting.business.model.vo.LnCreditTransfer2VIPVO;
import com.pinting.business.model.vo.LnCreditTransferMQueryVO;
import com.pinting.business.model.vo.LnCreditTransferMVO;
import com.pinting.business.service.manage.MLnCreditTransferService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;

/**
 * 财务-债权转让
 * @author bianyatian
 * @2016-12-2 下午3:04:48
 */
@Controller
public class FinancialLnCreditTransferController {

    @Autowired
    private MLnCreditTransferService mLnCreditTransferService;

    /**
     * 债权转让统计
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/financial/lnCreditTransferIndex")
    public String lnCreditTransferIndex(LnCreditTransferMQueryVO req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        req.setInUserName(req.getInUserName() == null ?req.getInUserName() :req.getInUserName().trim());
        req.setInUserMobile(req.getInUserMobile() == null ?req.getInUserMobile() :req.getInUserMobile().trim());

        try {
            int count = mLnCreditTransferService.countTransferList(req);
            List<LnCreditTransferMVO> list = new ArrayList<LnCreditTransferMVO>();
            if(count >0){
                list = mLnCreditTransferService.getTransferList(req);
            }
            Double transSumPrincipal = mLnCreditTransferService.transSumPrincipal(req);//债转本金总额
            Double transSumInterest = mLnCreditTransferService.transSumInterest(req);//投资客户总利息

            model.put("count", count);
            model.put("list", list);
            model.put("transSumPrincipal", transSumPrincipal);
            model.put("transSumInterest", transSumInterest);
        } catch (Exception e) {
            e.printStackTrace();
        }

        model.put("req", req);

        return "financial/finance_lnCreditTransfer_index";
    }

    /**
     * 债权转让统计-导出
     * @param req
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("/financial/lnCreditTransferExport")
    public void lnCreditTransferExport(LnCreditTransferMQueryVO req, HttpServletRequest request, HttpServletResponse response){

        //需要导出的数据
        req.setInUserName(req.getInUserName() == null ?req.getInUserName() :req.getInUserName().trim());
        req.setInUserMobile(req.getInUserMobile() == null ?req.getInUserMobile() :req.getInUserMobile().trim());

        int count = mLnCreditTransferService.countTransferList(req);
        List<LnCreditTransferMVO> dataGrid = new ArrayList<LnCreditTransferMVO>();
        if(count >0){
            req.setStart(1);
            req.setNumPerPage(count);
            dataGrid = mLnCreditTransferService.getTransferList(req);
        }
        FileInputStream fis = null;
        HSSFWorkbook wb = null;
        try {
            File file = new File(GlobEnvUtil.get("transfer.index.excel"));
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
                LnCreditTransferMVO vo = dataGrid.get(i);
                //单据头
                HSSFRow headRow = sheetHead.createRow(base+i);
                Cell head_cell_0 = headRow.createCell(0); //序号
                head_cell_0.setCellStyle(basicStyle);
                head_cell_0.setCellValue(vo.getRowno());
                Cell head_cell_1 = headRow.createCell(1); //单据编号(orderNo)
                head_cell_1.setCellStyle(textCellStyle);
                head_cell_1.setCellValue(vo.getOutLoanRelationId()+"0000"+vo.getInLoanRelationId());
                Cell head_cell_2 = headRow.createCell(2); //日期
                head_cell_2.setCellStyle(dateCellStyle);
                head_cell_2.setCellValue(vo.getCreateTime());

                //单据体
                HSSFRow bodyRow = sheetBody.createRow(base+i);
                Cell body_cell_0 = bodyRow.createCell(0); //序号
                body_cell_0.setCellStyle(basicStyle);
                body_cell_0.setCellValue(vo.getRowno());
                Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
                body_cell_1.setCellStyle(textCellStyle);
                body_cell_1.setCellValue(vo.getOutLoanRelationId()+"0000"+vo.getInLoanRelationId());

                Cell body_cell_2 = bodyRow.createCell(2); //转出客户代码
                body_cell_2.setCellStyle(textCellStyle);
                body_cell_2.setCellValue("3."+vo.getOutUserId());

                Cell body_cell_3 = bodyRow.createCell(3); //转入客户代码
                body_cell_3.setCellStyle(textCellStyle);
                body_cell_3.setCellValue("3."+vo.getInUserId());

                Cell body_cell_4 = bodyRow.createCell(4); //债转本金
                body_cell_4.setCellStyle(smallCellStyle);
                body_cell_4.setCellValue(vo.getAmount());

                Cell body_cell_5 = bodyRow.createCell(5); //应付投资客户利息
                body_cell_5.setCellStyle(smallCellStyle);
                body_cell_5.setCellValue(MoneyUtil.subtract(vo.getTotalAmount(),vo.getAmount()).doubleValue());

                Cell body_cell_6 = bodyRow.createCell(6); //本利合计
                body_cell_6.setCellStyle(smallCellStyle);
                body_cell_6.setCellValue(vo.getTotalAmount());


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
     * 债转支付vip
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/financial/lnCreditTransfer2VIPIndex")
    public String lnCreditTransfer2VIPIndex(LnCreditTransferMQueryVO req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        req.setInUserName(req.getInUserName() == null ?req.getInUserName() :req.getInUserName().trim());
        req.setInUserMobile(req.getInUserMobile() == null ?req.getInUserMobile() :req.getInUserMobile().trim());

        try {
            LnCreditTransfer2VIPVO vipVO= mLnCreditTransferService.getTransfer2VIP(req);

            model.put("count", vipVO.getCount());
            model.put("list", vipVO.getList());
            model.put("amount", vipVO.getTransSumAmount());
        } catch (Exception e) {
            e.printStackTrace();
        }
        MoneyUtil.format(MoneyUtil.subtract(1.0d, 2.0d));
        model.put("req", req);

        return "financial/finance_lnCreditTransfer_2vip";
    }

    /**
     * 债转支付vip-导出
     * @param req
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("/financial/lnCreditTransfer2VIPExport")
    public void lnCreditTransfer2VIPExport(LnCreditTransferMQueryVO req, HttpServletRequest request, HttpServletResponse response){
        try {

            req.setInUserName(req.getInUserName() == null ?req.getInUserName() :req.getInUserName().trim());
            req.setInUserMobile(req.getInUserMobile() == null ?req.getInUserMobile() :req.getInUserMobile().trim());
            LnCreditTransfer2VIPVO vipVO= mLnCreditTransferService.getTransfer2VIP4Export(req);

            List<LnCreditTransferMVO> dataGrid = vipVO.getList();

            FileInputStream fis = null;
            HSSFWorkbook wb = null;
            try {
                File file = new File(GlobEnvUtil.get("transfer.vip.excel"));
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
                    LnCreditTransferMVO vo = dataGrid.get(i);
                    //单据头
                    HSSFRow headRow = sheetHead.createRow(base+i);
                    Cell head_cell_0 = headRow.createCell(0); //序号
                    head_cell_0.setCellStyle(basicStyle);
                    head_cell_0.setCellValue(vo.getRowno());
                    Cell head_cell_1 = headRow.createCell(1); //单据编号(orderNo)
                    head_cell_1.setCellStyle(textCellStyle);
                    head_cell_1.setCellValue(vo.getOutLoanRelationId()+"0000"+vo.getInLoanRelationId());
                    Cell head_cell_2 = headRow.createCell(2); //日期
                    head_cell_2.setCellStyle(dateCellStyle);
                    head_cell_2.setCellValue(DateUtil.formatYYYYMMDD(vo.getCreateTime()));

                    //单据体
                    HSSFRow bodyRow = sheetBody.createRow(base+i);
                    Cell body_cell_0 = bodyRow.createCell(0); //序号
                    body_cell_0.setCellStyle(basicStyle);
                    body_cell_0.setCellValue(vo.getRowno());

                    Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
                    body_cell_1.setCellStyle(textCellStyle);
                    body_cell_1.setCellValue(vo.getOutLoanRelationId()+"0000"+vo.getInLoanRelationId());

                    Cell body_cell_2 = bodyRow.createCell(2);
                    body_cell_2.setCellStyle(textCellStyle);
                    body_cell_2.setCellValue("3."+vo.getInUserId());

                    Cell body_cell_3 = bodyRow.createCell(3);
                    body_cell_3.setCellStyle(textCellStyle);
                    body_cell_3.setCellValue("3."+vo.getOutUserId());

                    Cell body_cell_4 = bodyRow.createCell(4); //应付客户本金
                    body_cell_4.setCellStyle(smallCellStyle);
                    body_cell_4.setCellValue(vo.getAmount());

                    Cell body_cell_5 = bodyRow.createCell(5); //应付客户利息
                    body_cell_5.setCellStyle(smallCellStyle);
                    body_cell_5.setCellValue(MoneyUtil.subtract(vo.getInAmount(), vo.getAmount()).doubleValue());

                    Cell body_cell_6 = bodyRow.createCell(6); //应付客户本金和利息
                    body_cell_6.setCellStyle(smallCellStyle);
                    body_cell_6.setCellValue(vo.getInAmount());

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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 债转支付- 存管后，云贷/赞时贷
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/financial/lnCreditTransferYunZSD")
    public String lnCreditTransferYunZSDIndex(LnCreditTransferMQueryVO req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model){
        req.setOutUserName(req.getOutUserName() == null ?req.getOutUserName() :req.getOutUserName().trim());
        if(StringUtil.isBlank(req.getPartnerCode())){
            req.setPartnerCode(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
        }
        if(StringUtil.isBlank(req.getQueryPartnerBusinessFlag())){
            req.setQueryPartnerBusinessFlag("777");
        }

        try {
            if(req.getBeginTime() == null){
                req.setBeginTime(DateUtil.getDateBegin(new Date()));
            }
            if(req.getOverTime() == null){
                req.setOverTime(DateUtil.getDateBegin(new Date()));
            }

            Date endDate = DateUtil.parseDate("2017-11-08");
            if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPartnerCode())
                    && req.getOverTime().compareTo(endDate) >=0
                    && req.getBeginTime().compareTo(endDate)<0){
                //云贷，且查询结束日期在2017-11-8之后，且开始日期在2017-11-8之前的，将开始日期置为2017-11-8
                req.setBeginTime(endDate);
            }

            LnCreditTransfer2VIPVO vipVO= mLnCreditTransferService.getTransferPay(req);

            model.put("count", vipVO.getCount());
            model.put("list", vipVO.getList());
            model.put("amountRecord", vipVO.getLnCreditTransferMVO());
        } catch (Exception e) {
            e.printStackTrace();
        }
        model.put("req", req);

        return "financial/finance_transfer_pay_yunzsd";
    }

    /**
     * 债转支付-债转支付  存管后，云贷/赞时贷
     * @param req
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("/financial/lnCreditTransferYunZSDExport")
    public void lnCreditTransferYunZSDExport(LnCreditTransferMQueryVO req, HttpServletRequest request, HttpServletResponse response){
        try {

            req.setOutUserName(req.getOutUserName() == null ?req.getOutUserName() :req.getOutUserName().trim());
            if(StringUtil.isBlank(req.getQueryPartnerBusinessFlag())){
                req.setQueryPartnerBusinessFlag("777");
            }
            LnCreditTransfer2VIPVO vipVO= mLnCreditTransferService.getTransferPay4Export(req);

            List<LnCreditTransferMVO> dataGrid = vipVO.getList();

            FileInputStream fis = null;
            HSSFWorkbook wb = null;
            try {
                File file = new File(GlobEnvUtil.get("dep.debt.transfer.pay.excel"));
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

                if (dataGrid != null && !dataGrid.isEmpty()) {
                    int base = 3;
                    for(int i=0;i<dataGrid.size();i++) {
                        LnCreditTransferMVO vo = dataGrid.get(i);
                        //单据头
                        HSSFRow headRow = sheetHead.createRow(base+i);
                        Cell head_cell_0 = headRow.createCell(0); //序号
                        head_cell_0.setCellStyle(basicStyle);
                        head_cell_0.setCellValue(vo.getRowno());
                        Cell head_cell_1 = headRow.createCell(1); //单据编号(orderNo)
                        head_cell_1.setCellStyle(textCellStyle);
                        head_cell_1.setCellValue(vo.getOutLoanRelationId()+"0000"+vo.getInLoanRelationId());
                        Cell head_cell_2 = headRow.createCell(2); //日期
                        head_cell_2.setCellStyle(dateCellStyle);
                        head_cell_2.setCellValue(DateUtil.formatYYYYMMDD(vo.getCreateTime()));

                        //单据体
                        HSSFRow bodyRow = sheetBody.createRow(base+i);
                        Cell body_cell_0 = bodyRow.createCell(0); //序号
                        body_cell_0.setCellStyle(basicStyle);
                        body_cell_0.setCellValue(vo.getRowno());

                        Cell body_cell_1 = bodyRow.createCell(1); //单据编号(orderNo)
                        body_cell_1.setCellStyle(textCellStyle);
                        body_cell_1.setCellValue(vo.getOutLoanRelationId()+"0000"+vo.getInLoanRelationId());

                        Cell body_cell_2 = bodyRow.createCell(2);
                        body_cell_2.setCellStyle(textCellStyle);
                        body_cell_2.setCellValue("3."+vo.getInUserId());//新投资客户代码

                        Cell body_cell_3 = bodyRow.createCell(3);
                        body_cell_3.setCellStyle(textCellStyle);
                        body_cell_3.setCellValue(vo.getInUserName());//新投资客户名称


                        Cell body_cell_4 = bodyRow.createCell(4); //原投资客户代码
                        body_cell_4.setCellStyle(textCellStyle);
                        body_cell_4.setCellValue("3."+vo.getOutUserId());

                        Cell body_cell_5 = bodyRow.createCell(5); //原投资客户名称
                        body_cell_5.setCellStyle(textCellStyle);
                        body_cell_5.setCellValue(vo.getOutUserName());

                        Cell body_cell_6 = bodyRow.createCell(6); //部门
                        body_cell_6.setCellStyle(textCellStyle);
                        body_cell_6.setCellValue("101");

                        Cell body_cell_7 = bodyRow.createCell(7); //新客户利息
                        body_cell_7.setCellStyle(smallCellStyle);
                        body_cell_7.setCellValue(MoneyUtil.subtract(vo.getInAmount(), vo.getAmount()).doubleValue());

                        Cell body_cell_8 = bodyRow.createCell(8); //老客户利息
                        body_cell_8.setCellStyle(smallCellStyle);
                        body_cell_8.setCellValue(vo.getOutAmount());

                        Cell body_cell_9 = bodyRow.createCell(9); //公司手续费
                        body_cell_9.setCellStyle(smallCellStyle);
                        body_cell_9.setCellValue(vo.getServiceAmount());

                    }
                    if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF.equals(req.getPartnerCode())){
                        fileName = fileName+"云贷";
                    }else if(Constants.PRODUCT_PROPERTY_SYMBOL_ZSD.equals(req.getPartnerCode())){
                        fileName = fileName+"赞时贷";
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
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

}