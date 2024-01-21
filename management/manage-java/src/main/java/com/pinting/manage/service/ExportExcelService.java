package com.pinting.manage.service;

import com.pinting.util.ExportUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.util.List;
import java.util.Map;

public interface ExportExcelService {

    /**
     * 根据excel模板生成导出excel文件
     * @param redisKey
     * @param workbook
     * @return
     */
    ExportUtil.ExcelDownloadHelperVO createExportExcel(final String redisKey, final String title, HSSFWorkbook workbook);

    /**
     * 创建生成导出excel文件
     * @param redisKey
     * @param excelListData
     * @return
     */
    ExportUtil.ExcelDownloadHelperVO createExportExcel(final String redisKey, String title, final List<Map<String, List<Object>>> excelListData);

    /**
     * 追加写入excel文件
     * @param excelDownloadHelper //  大文件excel下载帮助对象
     * @param workbook // excel模板文件
     * @return
     */
    ExportUtil.ExcelDownloadHelperVO writeExportExcel(ExportUtil.ExcelDownloadHelperVO excelDownloadHelper, HSSFWorkbook workbook);

    /**
     * 追加写入excel文件
     * @param excelDownloadHelper //  大文件excel下载帮助对象
     * @param title // 导出文件sheet页标题
     * @param exportList // 导出文件数据列表
     * @param index 创建生成sheet页下标起始值
     * @return
     */
    ExportUtil.ExcelDownloadHelperVO writeExportExcel(ExportUtil.ExcelDownloadHelperVO excelDownloadHelper, String title, List<Map<String, List<Object>>> exportList, Integer index);

    /**
     * 获取下载导出文件对象
     * @param redisKey
     * @return
     */
    ExportUtil.ExcelDownloadHelperVO getExpoertExcelVOInfo(String redisKey);
}
