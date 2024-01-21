package com.pinting.business.util;

import com.pinting.core.util.*;
import org.apache.commons.codec.binary.Base64;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.HSSFColor;
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
 * Author:      cyb
 * Date:        2016/9/24
 * Description: 导出Excel文件
 */
public class ExportUtil {
	private static final Logger log = LoggerFactory.getLogger(ExportUtil.class);

    /**
     * 导出CSV文件到本地
     *
     * @param path csv文件(路径+文件名)，csv文件不存在会自动创建
     * @param dataList 数据
     * @return
     */
    public static boolean exportLocalCSV(String path, List<String> dataList, String fileName) {
    	log.info("生成文件，保存路径path="+path+";文件名fileName ="+fileName);
        boolean isSucess=false;
        File dic = new File(path);
        if(!dic.exists()) {
            dic.mkdirs();
        }
        File file = new File(path + File.separator + fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        FileOutputStream out=null;
        OutputStreamWriter osw=null;
        BufferedWriter bw=null;
        try {
            out = new FileOutputStream(file);
            osw = new OutputStreamWriter(out,"UTF-8");
            bw =new BufferedWriter(osw);
            if(dataList!=null && !dataList.isEmpty()){
                for(String data : dataList){
                    if(null == data || "" == data) {
                        bw.append("").append("\r");
                    } else {
                        bw.append(data).append("\r");
                    }
                }
            }
            isSucess=true;
        } catch (Exception e) {
            isSucess=false;
        }finally{
            if(bw!=null){
                try {
                    bw.close();
                    bw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(osw!=null){
                try {
                    osw.close();
                    osw=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(out!=null){
                try {
                    out.close();
                    out=null;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return isSucess;
    }

    /**
     * 导出到本地
     *
     * @param title   标题
     * @param prePath 路径前缀
     * @param list    列表
     * @throws Exception
     */
    public static void exportLocalExcel(String title, String prePath, List<Map<String, List<Object>>> list)
            throws Exception {
        File preDirect = new File(prePath);
        if(!preDirect.exists() && !preDirect.isDirectory()) {
            preDirect.mkdirs();
        }

        //输出流定义
        String excel_name = title + "_" + com.pinting.core.util.DateUtil.formatYYYYMMDD(new Date()) + ".xls";
        OutputStream os = new FileOutputStream(prePath + File.separator + excel_name);

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
                str = com.pinting.core.util.DateUtil.format((Date) obj);
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
            String excel_name = fileName + "_" + com.pinting.core.util.DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmss") + ".xls";
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
}
