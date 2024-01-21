package com.pinting.manage.controller;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.model.vo.BsBgwNuccSignRelationVO;
import com.pinting.business.service.manage.NuccSignRelationService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.business.util.FileUtil;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 网联协议号导入
 *
 * @author zousheng
 * @2018-03-31
 */

@Controller
public class NuccSignRelationController {

    @Autowired
    private NuccSignRelationService nuccSignRelationService;

    private List<BsBgwNuccSignRelationVO> failList = new ArrayList<>(); // 导入校验失败数据

    private int dealCount = 0; // 处理数量

    /**
     * 进入网联协议号导入主页
     *
     * @param model
     * @return
     */
    @RequestMapping("/nuccSignRelation/index")
    public String nuccSignRelationIndex(Model model) {
        model.addAttribute("sevenDaiSelf", PartnerEnum.SEVEN_DAI_SELF.getCode());
        model.addAttribute("yunDaiSelf", PartnerEnum.YUN_DAI_SELF.getCode());
        model.addAttribute("bgw", Constants.UC_USER_TYPE_BGW);
        model.addAttribute("bgwBaofooAssist", Constants.BGW_BAOFOO_ASSIST);

        return "/nuccSignRelation/index";
    }

    /**
     * 模板导出
     *
     * @param request
     * @param response
     */
    @RequestMapping("/nuccSignRelation/exportTxt")
    public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
        StringBuilder write = new StringBuilder();
        write.append(Constants.NUCC_SIGN_EXPORT_TITLE).append("\r\n");
        write.append("6212261202027496521,12345678901654").append("\r\n");

        FileUtil.downloadTxtFile("网联协议导入" + DateUtil.getDate(new Date()) + ".txt", write.toString(), response);
    }

    /**
     * 读取文件并预览
     *
     * @param mFile
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/nuccSignRelation/readExcelCheck")
    public @ResponseBody
    HashMap<String, Object> readExcelCheck(@RequestParam String merchantType, @RequestParam String userType,
                                           @RequestParam(value = "fileFieldCheck", required = true) MultipartFile mFile,
                                           HttpServletResponse response, HttpServletRequest request) throws IOException {
        HashMap<String, Object> dataMap = new HashMap<>();
        this.failList.clear();
        // 导入数据预览
        List<BsBgwNuccSignRelationVO> checkList = new ArrayList<>();

        // 导入校验失败数据下载
        List<BsBgwNuccSignRelationVO> failList = new ArrayList<>();

        // 错误信息
        String errorMsg;
        CommonsMultipartFile cFile = (CommonsMultipartFile) mFile;
        DiskFileItem fileItem = (DiskFileItem) cFile.getFileItem();
        List<String> list = FileUtil.readLines(fileItem.getInputStream(), "UTF-8");
        if (CollectionUtils.isNotEmpty(list)) {
            errorMsg = nuccSignRelationService.checkImportDate(merchantType, userType, list, checkList, failList);

            dealCount = checkList.size();
            if (StringUtils.isBlank(errorMsg) && CollectionUtils.isEmpty(checkList)) {
                errorMsg = "没有导入数据";
            }
        } else {
            errorMsg = "没有导入数据";
        }

        if (CollectionUtils.isNotEmpty(failList)) {
            this.failList.clear();
            this.failList.addAll(failList);
        }

        dataMap.put("nuccSignRelationList", checkList);
        dataMap.put("errorMsg", errorMsg);
        dataMap.put("errorSize", failList.size());
        dataMap.put("showSize", checkList.size());
        return dataMap;
    }


    /**
     * 读取文件并上传
     *
     * @param file
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/nuccSignRelation/readExcel")
    public @ResponseBody
    HashMap<String, Object> readExcel(@RequestParam String merchantType, @RequestParam String userType,
                                      @RequestParam(value = "fileField", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        HashMap<String, Object> dataMap = new HashMap<>();
        this.failList.clear();
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtil.isBlank(mUserId)) {
            dataMap.put("errorMsg", "未获取到用户登录信息");
            return dataMap;
        }

        // 导入数据预览
        List<BsBgwNuccSignRelationVO> checkList = new ArrayList<>();
        // 导入校验失败数据下载
        List<BsBgwNuccSignRelationVO> failList = new ArrayList<>();

        // 错误信息
        String errorMsg = "";
        List<String> list = FileUtil.readLines(file.getInputStream(), "UTF-8");
        if (CollectionUtils.isNotEmpty(list)) {
            errorMsg = nuccSignRelationService.checkImportDate(merchantType, userType, list, checkList, failList);

            if (StringUtils.isBlank(errorMsg) && CollectionUtils.isEmpty(checkList)) {
                errorMsg = "没有导入数据";
            }

            // 校验预览文件处理数量与确认导入处理数量一致
            if (StringUtils.isBlank(errorMsg) && dealCount != checkList.size()) {
                errorMsg = "预览导入文件与确认导入文件不一致";
            }

            if(StringUtils.isBlank(errorMsg) && CollectionUtils.isNotEmpty(checkList)) {
                // 校验通过数据入库
                checkList.removeAll(failList);
                nuccSignRelationService.saveImportDate(checkList);
            }
        } else {
            errorMsg = "没有导入数据";
        }

        if (CollectionUtils.isNotEmpty(failList)) {
            this.failList.addAll(failList);
        }

        dataMap.put("errorMsg", errorMsg);
        dataMap.put("errorSize", failList.size());
        return dataMap;
    }

    /**
     * 导出失败文件
     *
     * @param response
     */
    @RequestMapping("/nuccSignRelation/exportFailFile")
    public void exportFailFile(HttpServletResponse response) {
        if (CollectionUtils.isNotEmpty(failList)) {
            StringBuilder write = new StringBuilder();
            write.append("导入文件行号,银行卡号,网联协议号号,错误描述").append("\r\n");
            for (int i = 0; i < failList.size(); i++) {
                write.append(failList.get(i).getLineNo()).append(Constants.SPLIT_SYMBOL)
                        .append(failList.get(i).getCardNo()).append(Constants.SPLIT_SYMBOL)
                        .append(failList.get(i).getProtocolNo()).append(Constants.SPLIT_SYMBOL)
                        .append(failList.get(i).getErrorMsg()).append("\r\n");
            }

            FileUtil.downloadTxtFile("网联协议导入失败文件" + DateUtil.getDate(new Date()) + ".txt", write.toString(), response);
            this.failList.clear();
        }
    }
}
