package com.pinting.util;

import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * POI方式导出excel工具类
 *
 * @author yanwl
 * @version $Id: ExportExcelUtil.java, v 0.1 2015-11-16 13:57:00
 * @date 2015-11-16
 */
public class ExportUtil {

    private static final Logger log = LoggerFactory.getLogger(ExportUtil.class);

    public static final String EXPORT_EXCEL_DOWNLOAD = "EXPORT_EXCEL_DOWNLOAD_";

    /**
     * 导出excel（多表头）
     *
     * @param request
     * @param response
     * @param title
     * @param list
     * @throws Exception
     */
    public static void exportExcelMultipleHead(HttpServletRequest request, HttpServletResponse response, String title, List<Map<String, List<Object>>>... list) throws Exception {
        //输出流定义
        OutputStream os = response.getOutputStream();
        String excel_name = title + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".xls";
        String enableFileName = "";
        String agent = (String) request.getHeader("USER-" + "AGENT");
        if (agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("rv:11.0) like Gecko") == -1 && agent.indexOf("Edge") == -1) {// FF
            enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(excel_name.getBytes("UTF-8")))) + "?=";
            response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
        } else { // IE
            enableFileName = new String(excel_name.getBytes("GBK"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
        }

        //创建excel文件
        HSSFWorkbook hssf_w_book = new HSSFWorkbook();
        HSSFSheet hssf_w_sheet = hssf_w_book.createSheet(title);
        hssf_w_sheet.setDefaultColumnWidth(21); //固定列宽度
        HSSFRow hssf_w_row = null;//创建一行
        HSSFCell hssf_w_cell = null;//创建每个单元格

        //定义表头单元格样式
        HSSFCellStyle head_cellStyle = hssf_w_book.createCellStyle();
        //定义表头字体样式
        HSSFFont head_font = hssf_w_book.createFont();
        head_font.setFontName("宋体");//设置头部字体为宋体
        head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
        head_font.setFontHeightInPoints((short) 10); //字体大小
        head_font.setColor(new HSSFColor.WHITE().getIndex());
        //表头单元格样式设置
        head_cellStyle.setFont(head_font);//单元格样式使用字体
        head_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
        head_cellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        head_cellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());

        //定义数据单元格样式
        HSSFCellStyle cellStyle_CN = hssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
        cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);

        //在多表头导出时，定义第一个表头出现位置
        int index = 0;
        //遍历写入表数据的list
        for (List<Map<String, List<Object>>> mapList : list) {
            int head_index = 0;
            for (Map<String, List<Object>> map : mapList) {
                //遍历map获取表头字段，并将表头字段放进String型的数组
                Set<String> keys = map.keySet();
                hssf_w_row = hssf_w_sheet.createRow(index);
                for (String key : keys) {
                    List<Object> objects = map.get(key);
                    for (int k = 0; k < objects.size(); k++) {
                        hssf_w_cell = hssf_w_row.createCell(k);
                        hssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                        hssf_w_cell.setCellValue(formatObject(objects.get(k)));
                        if (head_index == 0) {
                            hssf_w_cell.setCellStyle(head_cellStyle);
                        } else {
                            hssf_w_cell.setCellStyle(cellStyle_CN);
                        }
                    }
                }
                index++;
                head_index++;
            }
        }

        //excel文件导出
        hssf_w_book.write(os);
        os.flush();
        os.close();
    }

    public static void exportExcel(HttpServletResponse response, HttpServletRequest request,
                                   String title, List<Map<String, List<Object>>> list)
            throws Exception {
        //输出流定义
        OutputStream os = response.getOutputStream();
        String excel_name = title + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".xls";
        String enableFileName = "";
        String agent = (String) request.getHeader("USER-" + "AGENT");
        if (agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("rv:11.0) like Gecko") == -1 && agent.indexOf("Edge") == -1) {// FF
            enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(excel_name.getBytes("UTF-8")))) + "?=";
            response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
        } else { // IE      
            enableFileName = new String(excel_name.getBytes("GBK"), "ISO-8859-1");
            response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
        }

        //创建excel文件
        HSSFWorkbook hssf_w_book = new HSSFWorkbook();
        HSSFSheet hssf_w_sheet = hssf_w_book.createSheet(title);
        hssf_w_sheet.setDefaultColumnWidth(21); //固定列宽度
        HSSFRow hssf_w_row = null;//创建一行
        HSSFCell hssf_w_cell = null;//创建每个单元格

        //定义表头单元格样式
        HSSFCellStyle head_cellStyle = hssf_w_book.createCellStyle();
        //定义表头字体样式
        HSSFFont head_font = hssf_w_book.createFont();
        head_font.setFontName("宋体");//设置头部字体为宋体
        head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
        head_font.setFontHeightInPoints((short) 10); //字体大小
        head_font.setColor(new HSSFColor.WHITE().getIndex());
        //表头单元格样式设置
        head_cellStyle.setFont(head_font);//单元格样式使用字体
        head_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
        head_cellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        head_cellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());

        //定义数据单元格样式
        HSSFCellStyle cellStyle_CN = hssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
        cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);

        //在多表头导出时，定义第一个表头出现位置
        int index = 0;
        //遍历写入表数据的list
        for (Map<String, List<Object>> map : list) {
            //遍历map获取表头字段，并将表头字段放进String型的数组
            Set<String> keys = map.keySet();
            hssf_w_row = hssf_w_sheet.createRow(index);
            for (String key : keys) {
                List<Object> objects = map.get(key);
                for (int k = 0; k < objects.size(); k++) {
                    hssf_w_cell = hssf_w_row.createCell(k);
                    hssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    hssf_w_cell.setCellValue(formatObject(objects.get(k)));
                    if (index == 0) {
                        hssf_w_cell.setCellStyle(head_cellStyle);
                    } else {
                        hssf_w_cell.setCellStyle(cellStyle_CN);
                    }
                }
            }
            index++;
        }
        //excel文件导出
        hssf_w_book.write(os);
        os.flush();
        os.close();
    }

    /**
     * 导出多个sheet, 生成excel文件追加sheet页写入
     *
     * @param title    导出文件sheet页标题
     * @param workbook sheet的模板数据
     */
    public static ExcelDownloadHelperVO exportExcelMoreSheet(String title, HSSFWorkbook workbook) {
        log.info("=============生成导出Excel文件开始=============");
        ExcelDownloadHelperVO excelDownloadHelper = createExcelFile(title, workbook);
        log.info("=============生成导出Excel文件结束=============");
        return excelDownloadHelper;
    }

    /**
     * 追加多个sheet, 生成excel文件追加sheet页写入
     *
     * @param excelDownloadHelper //  大文件excel下载帮助对象
     * @param workbook            // sheet的模板数据
     * @return
     */
    public static ExcelDownloadHelperVO exportExcelMoreSheet(ExcelDownloadHelperVO excelDownloadHelper, HSSFWorkbook workbook) {
        log.info("=============生成导出Excel文件开始=============");
        writeExcelFile(excelDownloadHelper, workbook);
        log.info("=============生成导出Excel文件结束=============");
        return excelDownloadHelper;
    }

    /**
     * 导出多个sheet, 生成excel文件追加sheet页写入
     *
     * @param title      导出文件sheet页标题
     * @param exportList sheet的数据集合
     */
    public static ExcelDownloadHelperVO exportExcelMoreSheet(String title, List<Map<String, List<Object>>> exportList) {
        log.info("=============生成导出Excel文件开始=============");

        ExcelDownloadHelperVO excelDownloadHelper = null;

        List exportHeadListTemp = new ArrayList(); // 表头提出来
        exportHeadListTemp.add(exportList.get(0));
        exportList.remove(0);

        log.info("=============导出Excel文件大小{}=============", exportList.size());
        // 加载导出数据
        int i = 1;
        int start = 0; // 第一行未表头
        int end = 0;
        int loadSize = 0;
        int size = exportList.size();
        List exportListTemp;
        do {
            loadSize = Constants.EXCEL_SHEET_EXPORT_MAX_SIZE <= (size - start) ? Constants.EXCEL_SHEET_EXPORT_MAX_SIZE : (size - start);
            end = start + loadSize;
            exportListTemp = exportList.subList(start, end);
            if (i == 1) {
                HSSFWorkbook hssf_w_book = exportExcelCreateWorkbook(title, exportHeadListTemp, exportListTemp);
                excelDownloadHelper = createExcelFile(title, hssf_w_book);
            } else {
                HSSFWorkbook hssf_w_book = writeExportExcel(excelDownloadHelper, title + i, exportHeadListTemp, exportListTemp);
                writeExcelFile(excelDownloadHelper, hssf_w_book);
            }
            start = end;
            i++;
        } while (start < size);
        exportListTemp.clear();
        exportList.clear(); // 清除导出列表数据

        log.info("=============生成导出Excel文件结束=============");
        return excelDownloadHelper;
    }

    /**
     * 追加多个sheet, 生成excel文件追加sheet页写入
     *
     * @param excelDownloadHelper //  大文件excel下载帮助对象
     * @param title               // 导出文件sheet页标题
     * @param exportList          // 导出文件数据列表
     * @param index               创建生成sheet页下标起始值
     * @return
     */
    public static ExcelDownloadHelperVO exportExcelMoreSheet(ExcelDownloadHelperVO excelDownloadHelper, String title, List<Map<String, List<Object>>> exportList, Integer index) {
        log.info("=============生成导出Excel文件开始=============");

        List exportHeadListTemp = new ArrayList(); // 表头提出来
        exportHeadListTemp.add(exportList.get(0));
        exportList.remove(0);

        log.info("=============导出Excel文件大小{}=============", exportList.size());
        // 加载导出数据
        int i = index;
        int start = 0; // 第一行未表头
        int end = 0;
        int loadSize = 0;
        int size = exportList.size();
        List exportListTemp;
        do {
            loadSize = Constants.EXCEL_SHEET_EXPORT_MAX_SIZE <= (size - start) ? Constants.EXCEL_SHEET_EXPORT_MAX_SIZE : (size - start);
            end = start + loadSize;
            exportListTemp = exportList.subList(start, end);
            HSSFWorkbook hssf_w_book = writeExportExcel(excelDownloadHelper, title + i, exportHeadListTemp, exportListTemp);
            writeExcelFile(excelDownloadHelper, hssf_w_book);
            start = end;
            i++;
        } while (start < size);
        exportListTemp.clear();
        exportList.clear(); // 清除导出列表数据

        log.info("=============生成导出Excel文件结束=============");
        return excelDownloadHelper;
    }

    private static HSSFWorkbook exportExcelCreateWorkbook(String title, List<Map<String, List<Object>>> headExportList, List<Map<String, List<Object>>> badyExportList) {
        //创建excel文件
        HSSFWorkbook hssf_w_book = new HSSFWorkbook();
        //定义表头单元格样式
        HSSFCellStyle head_cellStyle = hssf_w_book.createCellStyle();
        //定义表头字体样式
        HSSFFont head_font = hssf_w_book.createFont();
        head_font.setFontName("宋体");//设置头部字体为宋体
        head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
        head_font.setFontHeightInPoints((short) 10); //字体大小
        head_font.setColor(new HSSFColor.WHITE().getIndex());
        //表头单元格样式设置
        head_cellStyle.setFont(head_font);//单元格样式使用字体
        head_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
        head_cellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        head_cellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());

        //定义数据单元格样式
        HSSFCellStyle cellStyle_CN = hssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
        cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);

        HSSFSheet hssf_w_sheet = hssf_w_book.createSheet(title + 1);
        hssf_w_sheet.setDefaultColumnWidth(21); //固定列宽度

        loadExportHeadData(hssf_w_sheet, head_cellStyle, headExportList);
        loadExportBodyData(hssf_w_sheet, cellStyle_CN, badyExportList);
        return hssf_w_book;
    }

    private static ExcelDownloadHelperVO createExcelFile(String title, HSSFWorkbook hssf_w_book) {
        //输出流定义
        FileOutputStream output = null;
        String fileFullPath = null;
        ExcelDownloadHelperVO excelDownloadHelper = new ExcelDownloadHelperVO();
        try {
            String excelName = title + "_" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss") + ".xls";
            String fileName = new String(excelName.getBytes("UTF-8"), "UTF-8");
            String filePath = GlobEnv.get("export.excel.download.directory");
            fileFullPath = filePath + "/" + fileName;
            //输出Excel文件
            output = new FileOutputStream(fileFullPath);
            hssf_w_book.write(output);
            output.flush();

            String serviceWeb = GlobEnv.get("server.web");
            excelDownloadHelper.setFileFullPath(fileFullPath);
            excelDownloadHelper.setFileName(fileName);
            excelDownloadHelper.setFileUrl(serviceWeb + "/resources/upload/excel/" + fileName);
        } catch (Exception e) {
            log.error("=============生成导出Excel文件失败=============", e);
            excelDownloadHelper.setDesc("生成导出Excel文件失败, 请重新导出！");
        } finally {
            if (hssf_w_book != null) {
                try {
                    hssf_w_book.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (output != null) {
                try {
                    output.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return excelDownloadHelper;
    }

    /**
     * 在Excel文件中追加sheet页
     *
     * @param excelDownloadHelper
     * @param title
     * @param headExportList
     * @param badyExportList
     * @return
     */
    private static HSSFWorkbook writeExportExcel(ExcelDownloadHelperVO excelDownloadHelper, String title, List<Map<String, List<Object>>> headExportList, List<Map<String, List<Object>>> badyExportList) {

        FileInputStream fs = null;
        HSSFWorkbook hssf_w_book = null;
        try {
            fs = new FileInputStream(excelDownloadHelper.getFileFullPath());  //获取head.xls
            POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
            hssf_w_book = new HSSFWorkbook(ps);
            //定义表头单元格样式
            HSSFCellStyle head_cellStyle = hssf_w_book.createCellStyle();
            //定义表头字体样式
            HSSFFont head_font = hssf_w_book.createFont();
            head_font.setFontName("宋体");//设置头部字体为宋体
            head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
            head_font.setFontHeightInPoints((short) 10); //字体大小
            head_font.setColor(new HSSFColor.WHITE().getIndex());
            //表头单元格样式设置
            head_cellStyle.setFont(head_font);//单元格样式使用字体
            head_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
            head_cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
            head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
            head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
            head_cellStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
            head_cellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
            head_cellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());

            //定义数据单元格样式
            HSSFCellStyle cellStyle_CN = hssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
            cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
            cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
            cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
            cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);

            HSSFSheet hssf_w_sheet = hssf_w_book.createSheet(title);
            hssf_w_sheet.setDefaultColumnWidth(21); //固定列宽度

            loadExportHeadData(hssf_w_sheet, head_cellStyle, headExportList);
            loadExportBodyData(hssf_w_sheet, cellStyle_CN, badyExportList);
        } catch (Exception e) {
            log.error("=============生成导出Excel文件失败=============", e);
            excelDownloadHelper.setDesc("生成导出Excel文件失败, 请重新导出！");
        } finally {
            if (fs != null) {
                try {
                    fs.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return hssf_w_book;
    }

    /**
     * 在Excel文件中追加sheet页
     *
     * @param excelDownloadHelper
     * @param hssf_w_book
     */
    private static void writeExcelFile(ExcelDownloadHelperVO excelDownloadHelper, HSSFWorkbook hssf_w_book) {
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(excelDownloadHelper.getFileFullPath());  // 向.xls中写数据
            out.flush();
            hssf_w_book.write(out);
            out.close();
        } catch (IOException e1) {
            e1.printStackTrace();
        } finally {
            if (hssf_w_book != null) {
                try {
                    hssf_w_book.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 加载导出表头
     *
     * @param hssf_w_sheet
     * @param head_cellStyle
     * @param headList
     */
    private static void loadExportHeadData(HSSFSheet hssf_w_sheet, HSSFCellStyle head_cellStyle, List<Map<String, List<Object>>> headList) {
        HSSFRow hssf_w_row = null;//创建一行
        HSSFCell hssf_w_cell = null;//创建每个单元格
        //在多表头导出时，定义第一个表头出现位置
        int index = 0;
        //遍历写入表数据的list
        for (Map<String, List<Object>> map : headList) {
            //遍历map获取表头字段，并将表头字段放进String型的数组
            Set<String> keys = map.keySet();
            hssf_w_row = hssf_w_sheet.createRow(index);
            for (String key : keys) {
                List<Object> objects = map.get(key);
                for (int k = 0; k < objects.size(); k++) {
                    hssf_w_cell = hssf_w_row.createCell(k);
                    hssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    hssf_w_cell.setCellValue(formatObject(objects.get(k)));
                    hssf_w_cell.setCellStyle(head_cellStyle);
                }
            }
        }
    }

    /**
     * 加载导出记录
     *
     * @param hssf_w_sheet
     * @param cellStyle_CN
     * @param badyList
     */
    private static void loadExportBodyData(HSSFSheet hssf_w_sheet, HSSFCellStyle cellStyle_CN, List<Map<String, List<Object>>> badyList) {
        HSSFRow hssf_w_row = null;//创建一行
        HSSFCell hssf_w_cell = null;//创建每个单元格
        int index = 1;
        //遍历写入表数据的list
        for (Map<String, List<Object>> map : badyList) {
            Set<String> keys = map.keySet();
            hssf_w_row = hssf_w_sheet.createRow(index);
            for (String key : keys) {
                List<Object> objects = map.get(key);
                for (int k = 0; k < objects.size(); k++) {
                    hssf_w_cell = hssf_w_row.createCell(k);
                    hssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    hssf_w_cell.setCellValue(formatObject(objects.get(k)));
                    hssf_w_cell.setCellStyle(cellStyle_CN);
                }
            }
            index++;
        }
    }

    public static void exportLocalExcel(HttpServletResponse response, HttpServletRequest request,
                                        String title, List<Map<String, List<Object>>> list)
            throws Exception {
        //输出流定义
        String excel_name = title + "_" + DateUtil.formatYYYYMMDD(new Date()) + ".xls";
        OutputStream os = new FileOutputStream("F:" + File.separator + excel_name);

        //创建excel文件
        HSSFWorkbook hssf_w_book = new HSSFWorkbook();
        HSSFSheet hssf_w_sheet = hssf_w_book.createSheet(title);
        hssf_w_sheet.setDefaultColumnWidth(21); //固定列宽度
        HSSFRow hssf_w_row = null;//创建一行
        HSSFCell hssf_w_cell = null;//创建每个单元格

        //定义表头单元格样式
        HSSFCellStyle head_cellStyle = hssf_w_book.createCellStyle();
        //定义表头字体样式
        HSSFFont head_font = hssf_w_book.createFont();
        head_font.setFontName("宋体");//设置头部字体为宋体
        head_font.setBoldweight(Font.BOLDWEIGHT_BOLD); //粗体
        head_font.setFontHeightInPoints((short) 10); //字体大小
        head_font.setColor(new HSSFColor.WHITE().getIndex());
        //表头单元格样式设置
        head_cellStyle.setFont(head_font);//单元格样式使用字体
        head_cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setVerticalAlignment(HSSFCellStyle.ALIGN_CENTER);
        head_cellStyle.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderRight(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setBorderTop(XSSFCellStyle.BORDER_THIN);
        head_cellStyle.setFillPattern(HSSFCellStyle.FINE_DOTS);
        head_cellStyle.setFillForegroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());
        head_cellStyle.setFillBackgroundColor(new HSSFColor.GREY_25_PERCENT().getIndex());

        //定义数据单元格样式
        HSSFCellStyle cellStyle_CN = hssf_w_book.createCellStyle();//创建数据单元格样式(数据库数据样式)
        cellStyle_CN.setBorderBottom(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderLeft(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderRight(XSSFCellStyle.BORDER_THIN);
        cellStyle_CN.setBorderTop(XSSFCellStyle.BORDER_THIN);

        //在多表头导出时，定义第一个表头出现位置
        int index = 0;
        //遍历写入表数据的list
        for (Map<String, List<Object>> map : list) {
            //遍历map获取表头字段，并将表头字段放进String型的数组
            Set<String> keys = map.keySet();
            hssf_w_row = hssf_w_sheet.createRow(index);
            for (String key : keys) {
                List<Object> objects = map.get(key);
                for (int k = 0; k < objects.size(); k++) {
                    hssf_w_cell = hssf_w_row.createCell(k);
                    hssf_w_cell.setCellType(XSSFCell.CELL_TYPE_STRING);
                    hssf_w_cell.setCellValue(formatObject(objects.get(k)));
                    if (index == 0) {
                        hssf_w_cell.setCellStyle(head_cellStyle);
                    } else {
                        hssf_w_cell.setCellStyle(cellStyle_CN);
                    }
                }
            }
            index++;
        }
        //excel文件导出
        hssf_w_book.write(os);
        os.flush();
        os.close();
    }

    //导出的内容格式化
    private static String formatObject(Object obj) {
        String str = "";
        if (obj != null) {
            if (obj instanceof Date) {
                str = DateUtil.format((Date) obj);
            } else if (obj instanceof Double) {
                str = MoneyUtil.format((Double) obj);
            } else if (obj instanceof BigDecimal) {
                str = MoneyUtil.format((BigDecimal) obj);
            } else {
                str = obj.toString();
            }
        }
        return str;
    }

    /**
     * 使用Excel模板，导出Excel文件
     *
     * @param workbook
     * @param response
     * @param request
     */
    public static void exportExcel(HSSFWorkbook workbook, String fileName, HttpServletResponse response, HttpServletRequest request) {
        OutputStream os = null;
        try {
            //输出流定义
            os = response.getOutputStream();
            String excel_name = fileName + "_" + DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss") + ".xls";
            String enableFileName = "";
            String agent = (String) request.getHeader("USER-" + "AGENT");
            if (agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("rv:11.0) like Gecko") == -1 && agent.indexOf("Edge") == -1) {// FF
                enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(excel_name.getBytes("UTF-8")))) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            } else { // IE      
                enableFileName = new String(excel_name.getBytes("GBK"), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            }

            //excel文件导出
            workbook.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 导出下载Excel文件
     *
     * @param fileFullPath
     * @param response
     * @param request
     */
    public static void exportExcel(String fileName, String fileFullPath, HttpServletRequest request, HttpServletResponse response) {
        OutputStream os = null;
        FileInputStream fs = null;
        HSSFWorkbook hssf_w_book = null;
        try {
            fs = new FileInputStream(fileFullPath);  //获取head.xls
            POIFSFileSystem ps = new POIFSFileSystem(fs);  //使用POI提供的方法得到excel的信息
            hssf_w_book = new HSSFWorkbook(ps);

            String excel_name = fileName;
            String enableFileName = "";
            String agent = (String) request.getHeader("USER-" + "AGENT");
            if (agent != null && agent.indexOf("MSIE") == -1 && agent.indexOf("rv:11.0) like Gecko") == -1 && agent.indexOf("Edge") == -1) {// FF
                enableFileName = "=?UTF-8?B?" + (new String(Base64.encodeBase64(excel_name.getBytes("UTF-8")))) + "?=";
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            } else { // IE
                enableFileName = new String(excel_name.getBytes("GBK"), "ISO-8859-1");
                response.setHeader("Content-Disposition", "attachment; filename=" + enableFileName);
            }

            //输出流定义
            os = response.getOutputStream();
            //excel文件导出
            hssf_w_book.write(os);
            os.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (fs != null) {
                    fs.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (os != null) {
                    os.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if (hssf_w_book != null) {
                    hssf_w_book.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 复制工作表
     * 此方法主要用于复制2个不同HSSFWorkbook间的工作表
     */
    public static void copySheet(HSSFWorkbook fromWorkbook, HSSFWorkbook toWorkbook, int fromSheetIndex, int toSheetIndex) {
        HSSFSheet fromSheet = fromWorkbook.getSheetAt(fromSheetIndex);
        for (int i = fromSheet.getFirstRowNum(); i <= fromSheet.getLastRowNum(); i++) {
            copyRows(fromWorkbook, toWorkbook, fromSheetIndex, toSheetIndex, i, i, i);
        }
    }

    /**
     * 复制行
     * 此方法主要用于复制2个不同HSSFWorkbook间的行
     */
    public static void copyRows(HSSFWorkbook fromWorkbook, HSSFWorkbook toWorkbook, int fromSheetIndex, int toSheetIndex, int startRow, int endRow, int position) {
        HSSFSheet fromSheet = fromWorkbook.getSheetAt(fromSheetIndex);
        HSSFSheet toSheet = toWorkbook.getSheetAt(toSheetIndex);
        int i;
        int j;

        if ((startRow == -1) || (endRow == -1)) {
            return;
        }

        List<CellRangeAddress> oldRanges = new ArrayList<CellRangeAddress>();
        for (i = 0; i < fromSheet.getNumMergedRegions(); i++) {
            oldRanges.add(fromSheet.getMergedRegion(i));
        }

        // 拷贝合并的单元格。原理：复制当前合并单元格后，原位置的格式会移动到新位置，需在原位置生成旧格式
        for (CellRangeAddress oldRange : oldRanges) {
            CellRangeAddress newRange = new CellRangeAddress(oldRange.getFirstRow(), oldRange.getLastRow(),
                    oldRange.getFirstColumn(), oldRange.getLastColumn());

            if (oldRange.getFirstRow() >= startRow && oldRange.getLastRow() <= endRow) {
                int targetRowFrom = oldRange.getFirstRow() - startRow + position;
                int targetRowTo = oldRange.getLastRow() - startRow + position;
                oldRange.setFirstRow(targetRowFrom);
                oldRange.setLastRow(targetRowTo);
                toSheet.addMergedRegion(oldRange);
                fromSheet.addMergedRegion(newRange);
            }
        }

        // 设置列宽
        for (i = startRow; i <= endRow; i++) {
            HSSFRow fromRow = fromSheet.getRow(i);
            if (fromRow != null) {
                for (j = fromRow.getLastCellNum(); j >= fromRow.getFirstCellNum(); j--) {
                    toSheet.setColumnWidth(j, fromSheet.getColumnWidth(j));
                    toSheet.setColumnHidden(j, false);
                }
                break;
            }
        }

        // 拷贝行并填充数据
        for (; i <= endRow; i++) {
            HSSFRow fromRow = fromSheet.getRow(i);
            if (fromRow == null) {
                continue;
            }
            HSSFRow toRow = toSheet.createRow(i - startRow + position);
            toRow.setHeight(fromRow.getHeight());
            for (j = fromRow.getFirstCellNum(); j <= fromRow.getPhysicalNumberOfCells(); j++) {
                HSSFCell fromCell = fromRow.getCell(j);
                if (fromCell == null) {
                    continue;
                }
                HSSFCell toCell = toRow.createCell(j);
                HSSFCellStyle toStyle = toWorkbook.createCellStyle();
                copyCellStyle(fromWorkbook, toWorkbook, fromCell.getCellStyle(), toStyle);
                toCell.setCellStyle(toStyle);
                int cType = fromCell.getCellType();
                toCell.setCellType(cType);
                switch (cType) {
                    case HSSFCell.CELL_TYPE_BOOLEAN:
                        toCell.setCellValue(fromCell.getBooleanCellValue());
                        // System.out.println("--------TYPE_BOOLEAN:" +
                        // targetCell.getBooleanCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_ERROR:
                        toCell.setCellErrorValue(fromCell.getErrorCellValue());
                        // System.out.println("--------TYPE_ERROR:" +
                        // targetCell.getErrorCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_FORMULA:
                        toCell.setCellFormula(parseFormula(fromCell.getCellFormula()));
                        // System.out.println("--------TYPE_FORMULA:" +
                        // targetCell.getCellFormula());
                        break;
                    case HSSFCell.CELL_TYPE_NUMERIC:
                        toCell.setCellValue(fromCell.getNumericCellValue());
                        // System.out.println("--------TYPE_NUMERIC:" +
                        // targetCell.getNumericCellValue());
                        break;
                    case HSSFCell.CELL_TYPE_STRING:
                        toCell.setCellValue(fromCell.getRichStringCellValue());
                        // System.out.println("--------TYPE_STRING:" + i +
                        // targetCell.getRichStringCellValue());
                        break;
                }
            }
        }
    }

    /**
     * 复制单元格样式
     * 此方法主要用于复制2个不同HSSFWorkbook间的单元格样式
     */
    public static void copyCellStyle(HSSFWorkbook fromWorkbook, HSSFWorkbook toWorkbook, HSSFCellStyle fromStyle, HSSFCellStyle toStyle) {
        toStyle.setAlignment(fromStyle.getAlignment());

        // 边框和边框颜色
        toStyle.setBorderBottom(fromStyle.getBorderBottom());
        toStyle.setBorderLeft(fromStyle.getBorderLeft());
        toStyle.setBorderRight(fromStyle.getBorderRight());
        toStyle.setBorderTop(fromStyle.getBorderTop());
        toStyle.setTopBorderColor(fromStyle.getTopBorderColor());
        toStyle.setBottomBorderColor(fromStyle.getBottomBorderColor());
        toStyle.setRightBorderColor(fromStyle.getRightBorderColor());
        toStyle.setLeftBorderColor(fromStyle.getLeftBorderColor());

        // 字体
        HSSFFont tofont = toWorkbook.createFont();
        copyFont(fromStyle.getFont(fromWorkbook), tofont);
        toStyle.setFont(tofont);

        // 背景和前景
        toStyle.setFillBackgroundColor(fromStyle.getFillBackgroundColor());
        toStyle.setFillForegroundColor(fromStyle.getFillForegroundColor());

        toStyle.setDataFormat(fromStyle.getDataFormat());
        toStyle.setFillPattern(fromStyle.getFillPattern());
        toStyle.setHidden(fromStyle.getHidden());
        toStyle.setIndention(fromStyle.getIndention());
        toStyle.setLocked(fromStyle.getLocked());
        toStyle.setRotation(fromStyle.getRotation());
        toStyle.setVerticalAlignment(fromStyle.getVerticalAlignment());
        toStyle.setWrapText(fromStyle.getWrapText());
    }

    /**
     * 复制字体
     * 此方法主要用于复制2个不同HSSFWorkbook间的字体
     */
    public static void copyFont(HSSFFont fromFont, HSSFFont toFont) {
        toFont.setBoldweight(fromFont.getBoldweight());
        toFont.setCharSet(fromFont.getCharSet());
        toFont.setColor(fromFont.getColor());
        toFont.setFontHeight(fromFont.getFontHeight());
        toFont.setFontHeightInPoints(fromFont.getFontHeightInPoints());
        toFont.setFontName(fromFont.getFontName());
        toFont.setItalic(fromFont.getItalic());
        toFont.setStrikeout(fromFont.getStrikeout());
        toFont.setTypeOffset(fromFont.getTypeOffset());
        toFont.setUnderline(fromFont.getUnderline());
    }

    private static String parseFormula(String pPOIFormula) {
        final String cstReplaceString = "ATTR(semiVolatile)"; //$NON-NLS-1$
        StringBuffer result;
        int index;

        result = new StringBuffer();
        index = pPOIFormula.indexOf(cstReplaceString);
        if (index >= 0) {
            result.append(pPOIFormula.substring(0, index));
            result.append(pPOIFormula.substring(index + cstReplaceString.length()));
        } else {
            result.append(pPOIFormula);
        }

        return result.toString();
    }

    /**
     * 大数量文件下载支持对象
     */
    public static class ExcelDownloadHelperVO {

        private String fileFullPath; // 文件全路径

        private String fileUrl; // 文件Url路径

        private String fileName; // 文件名

        private String desc; // 描述

        public String getFileFullPath() {
            return fileFullPath;
        }

        public void setFileFullPath(String fileFullPath) {
            this.fileFullPath = fileFullPath;
        }

        public String getFileUrl() {
            return fileUrl;
        }

        public void setFileUrl(String fileUrl) {
            this.fileUrl = fileUrl;
        }

        public String getFileName() {
            return fileName;
        }

        public void setFileName(String fileName) {
            this.fileName = fileName;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }
    }
}
