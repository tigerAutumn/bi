package com.pinting.manage.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.pinting.business.util.FileUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.manage.service.ExportExcelService;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ExportExcelServiceImpl implements ExportExcelService {

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(com.pinting.business.util.Constants.REDIS_SUBSYS_BUSINESS);

    @Override
    public ExportUtil.ExcelDownloadHelperVO createExportExcel(final String redisKey, final String title, HSSFWorkbook workbook) {

        // 删除旧excel文件
        String excelDownloadHelperVOStr = jsClientDaoSupport.getObj(String.class, redisKey);
        if (StringUtils.isNotBlank(excelDownloadHelperVOStr)) {
            ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = JSONObject.parseObject(excelDownloadHelperVOStr, ExportUtil.ExcelDownloadHelperVO.class);
            if (excelDownloadHelperVO != null && StringUtils.isNotBlank(excelDownloadHelperVO.getFileFullPath())) {
                FileUtil.deleteFile(excelDownloadHelperVO.getFileFullPath());
            }
        }

        ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = ExportUtil.exportExcelMoreSheet(title, workbook);
        jsClientDaoSupport.addOrUpdateObj(JSONObject.toJSONString(excelDownloadHelperVO), redisKey);
        return excelDownloadHelperVO;
    }

    @Override
    public ExportUtil.ExcelDownloadHelperVO createExportExcel(final String redisKey, final String title, final List<Map<String, List<Object>>> excelListData) {

        // 删除旧excel文件
        String excelDownloadHelperVOStr = jsClientDaoSupport.getObj(String.class, redisKey);
        if (StringUtils.isNotBlank(excelDownloadHelperVOStr)) {
            ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = JSONObject.parseObject(excelDownloadHelperVOStr, ExportUtil.ExcelDownloadHelperVO.class);
            if (excelDownloadHelperVO != null && StringUtils.isNotBlank(excelDownloadHelperVO.getFileFullPath())) {
                FileUtil.deleteFile(excelDownloadHelperVO.getFileFullPath());
            }
        }

        if (excelListData.size() <= Constants.EXCEL_SHEET_EXPORT_MAX_SIZE) {
            ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = ExportUtil.exportExcelMoreSheet(title, excelListData);
            jsClientDaoSupport.addOrUpdateObj(JSONObject.toJSONString(excelDownloadHelperVO), redisKey);
            return excelDownloadHelperVO;
        } else {
            // 异步生成导出下载文件
            new Thread(new Runnable() {
                @Override
                public void run() {
                    ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = ExportUtil.exportExcelMoreSheet(title, excelListData);
                    jsClientDaoSupport.addOrUpdateObj(JSONObject.toJSONString(excelDownloadHelperVO), redisKey);
                }
            }).start();

            ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = new ExportUtil.ExcelDownloadHelperVO();
            excelDownloadHelperVO.setDesc("正在生成下载文件，请10分钟后下载查看");
            jsClientDaoSupport.addOrUpdateObj(JSONObject.toJSONString(excelDownloadHelperVO), redisKey);
            return excelDownloadHelperVO;
        }
    }

    @Override
    public ExportUtil.ExcelDownloadHelperVO writeExportExcel(ExportUtil.ExcelDownloadHelperVO excelDownloadHelper, HSSFWorkbook workbook) {
        ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = ExportUtil.exportExcelMoreSheet(excelDownloadHelper, workbook);
        return excelDownloadHelperVO;
    }

    @Override
    public ExportUtil.ExcelDownloadHelperVO writeExportExcel(ExportUtil.ExcelDownloadHelperVO excelDownloadHelper, String title, List<Map<String, List<Object>>> exportList, Integer index) {
        ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = ExportUtil.exportExcelMoreSheet(excelDownloadHelper, title, exportList, index);
        return excelDownloadHelperVO;
    }

    @Override
    public ExportUtil.ExcelDownloadHelperVO getExpoertExcelVOInfo(String redisKey) {
        String excelDownloadHelperVOStr = jsClientDaoSupport.getObj(String.class, redisKey);
        ExportUtil.ExcelDownloadHelperVO excelDownloadHelperVO = JSONObject.parseObject(excelDownloadHelperVOStr, ExportUtil.ExcelDownloadHelperVO.class);
        return excelDownloadHelperVO;
    }
}
