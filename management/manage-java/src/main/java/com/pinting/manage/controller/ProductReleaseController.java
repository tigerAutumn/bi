/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.manage.controller;

import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanStatutsChange_ScheduleRegistReset4Publish;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductSerial;
import com.pinting.business.model.BsProductTag;
import com.pinting.business.model.vo.ProductReleaseInfoVO;
import com.pinting.business.service.manage.BsProductTagService;
import com.pinting.business.service.manage.MProductSerialService;
import com.pinting.business.service.manage.MProductService;
import com.pinting.business.service.manage.ProductReleaseService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.EditorUtil;
import com.pinting.util.ReturnDWZAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 理财计划发布管理Controller
 * @author HuanXiong
 * @version $Id: ProductReleaseController.java, v 0.1 2016-4-21 下午2:56:23 HuanXiong Exp $
 */
@Controller
@RequestMapping("/product/release")
public class ProductReleaseController {
    
    @Autowired
    private ProductReleaseService productReleaseService;
    @Autowired
    private MProductService mProductService;
    @Autowired
    private MProductSerialService productSerialService;
    @Autowired
    private ProductService productService;
    @Autowired
    @Qualifier("dispatchService")
    public HessianService planCheckService;
    @Autowired
    @Qualifier("manageService")
    private HessianService planStatusChangeService;
    @Autowired
    private BsProductTagService productTagService;
    
    /**
     * 理财计划发布管理首页
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/release_index")
    public String releaseIndex(ProductReleaseInfoVO req, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        req.setNumPerPage(100);
        
        //展示终端
        if(StringUtil.isNotEmpty(req.getsShowTerminal())) {
            String[] terminals = req.getsShowTerminal().split(",");
            if(terminals.length > 0) {
                List<Object> showTerminalList = new ArrayList<Object>();
                for (String str : terminals) {
                    if(StringUtil.isNotEmpty(str.trim())) {
                        showTerminalList.add(str.trim());
                    }
                }
                req.setShowTerminalList(showTerminalList);
            }
        }
        // 理财计划列表
        List<ProductReleaseInfoVO> pros = productReleaseService.queryReleaseProductGrid(req);
        ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(pros);
        if(!CollectionUtils.isEmpty(dataGrid)) {
            for (HashMap<String, Object> data : dataGrid) {
                String showTerminal = (String) data.get("showTerminal");
                StringBuffer show = new StringBuffer();
                if(StringUtil.isNotBlank(showTerminal)) {
                    String[] showTerminalArray = showTerminal.split(",");
                    for (int i = 0; i < showTerminalArray.length; i++) {
                        if ("PC_178".equals(showTerminalArray[i])) {
                            showTerminalArray[i] = "钱报PC";
                        }
                        if ("H5_178".equals(showTerminalArray[i])) {
                            showTerminalArray[i] = "钱报H5";
                        }
                        if ("PC".equals(showTerminalArray[i])) {
                            showTerminalArray[i] = "网站";
                        }
                        if ("H5_KQ".equals(showTerminalArray[i])) {
                            showTerminalArray[i] = "柯桥日报H5";
                        }
                        if ("PC_KQ".equals(showTerminalArray[i])) {
                            showTerminalArray[i] = "柯桥日报PC";
                        }
                        if ("H5_RUIAN".equals(showTerminalArray[i])) {
                            showTerminalArray[i] = "瑞安日报H5";
                        }
                        if ("PC_RUIAN".equals(showTerminalArray[i])) {
                            showTerminalArray[i] = "瑞安日报PC";
                        }
                        show.append(showTerminalArray[i]).append(",");
                    }
                    show.deleteCharAt(show.lastIndexOf(","));
                    data.put("showTerminal", show.toString());
                }
            }
        }
        
        Integer count = productReleaseService.countReleaseProductGrid(req);
        //查询理财计划期数
        List<BsProduct> terms = mProductService.findAllProductTerm();
        model.put("terms", terms);
        //查询理财计划利率
        List<BsProduct> rates = mProductService.findAllProductBaseRate();
        model.put("rates", rates);
        //查询产品系列列表
        List<BsProductSerial> serials = productSerialService.findBsProductSerials(null);
        model.put("serials", serials);
        
        model.put("req", req);
        model.put("dataGrid", dataGrid);
        model.put("count", count);
        return "/product/plan/release/release_index";
    }
    
    /**
     * 计划详情页面（可复用）
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/details_index")
    public String detailsIndex(ReqMsg_ProductPlanCheck_PlanDetail req, 
                               HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String resources = GlobEnvUtil.get("news.resources");
        String manageWeb = GlobEnvUtil.get("server.web");
        String web = GlobEnvUtil.get("news.resources");
        ResMsg_ProductPlanCheck_PlanDetail res = (ResMsg_ProductPlanCheck_PlanDetail) planCheckService.handleMsg(req);
        res.getProductDatas().put("note", EditorUtil.replace((String)res.getProductDatas().get("note"), resources, manageWeb, web));
        res.getProductDatas().put("propertySummary", EditorUtil.replace((String)res.getProductDatas().get("propertySummary"), resources, manageWeb, web));
        res.getProductDatas().put("returnSource", EditorUtil.replace((String)res.getProductDatas().get("returnSource"), resources, manageWeb, web));
        res.getProductDatas().put("fundSecurity", EditorUtil.replace((String)res.getProductDatas().get("fundSecurity"), resources, manageWeb, web));
        res.getProductDatas().put("orgnizeCheck", EditorUtil.replace((String)res.getProductDatas().get("orgnizeCheck"), resources, manageWeb, web));
        res.getProductDatas().put("ratingGrade", EditorUtil.replace((String)res.getProductDatas().get("ratingGrade"), resources, manageWeb, web));
        
        if (res.getProductDatas().get("coopProtocolPics") != null) {
            String[] coopProtocolImgs = ((String)res.getProductDatas().get("coopProtocolPics")).split(";");
            model.put("coopProtocolImgs", coopProtocolImgs);
        }
        if (res.getProductDatas().get("loanProtocolPics") != null) {
            String[] loanProtocolImgs = ((String)res.getProductDatas().get("loanProtocolPics")).split(";");
            model.put("loanProtocolImgs", loanProtocolImgs);
        }
        if (res.getProductDatas().get("orgnizeCheckPics") != null) {
            String[] orgnizeCheckImgs = ((String)res.getProductDatas().get("orgnizeCheckPics")).split(";");
            model.put("orgnizeCheckImgs", orgnizeCheckImgs);
        }
        if (res.getProductDatas().get("ratingGradePics") != null) {
            String[] ratingGradeImgs = ((String)res.getProductDatas().get("ratingGradePics")).split(";");
            model.put("ratingGradeImgs", ratingGradeImgs);
        }
        model.put("product", res.getProductDatas());
        return "/product/plan/release/release_detail";
    }
    
    /**
     * 发布确认页面
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/release_confirm_page")
    public String releaseConfirmPage(ProductReleaseInfoVO req, Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) {
        BsProduct bsProduct = productService.findRegularById(req.getProductId());
        model.put("name", bsProduct.getName());
        model.put("maxTotalAmount", bsProduct.getMaxTotalAmount());
        model.put("term", bsProduct.getTerm());
        model.put("baseRate", bsProduct.getBaseRate());
        model.put("startTime", bsProduct.getStartTime());
        model.put("endTime", bsProduct.getEndTime());
        model.put("productId", req.getProductId());
        return "/product/plan/release/release_confirm_page";
    }
    
    /**
     * 发布操作
     * @param productId 产品ID
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/release")
    public Map<Object, Object> release(String productId, String startTime, String endTime, HttpServletRequest request, HttpServletResponse response) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        String realStartTime = request.getParameter("realStartTime");
        String realEndTime = request.getParameter("realEndTime");
        String minutesAndSecondsEnd = null;
        if(!StringUtil.isBlank(realEndTime)) {
            minutesAndSecondsEnd = DateUtil.formatDateTime(DateUtil.parse(realEndTime, "yyyy-MM-dd HH:mm:ss"), "ss");
        }
        String minutesAndSecondsStart = "00";
        if(!StringUtil.isBlank(realStartTime)){
            minutesAndSecondsStart = DateUtil.formatDateTime(DateUtil.parse(realStartTime, "yyyy-MM-dd HH:mm:ss"), "ss");
            minutesAndSecondsStart = (StringUtil.isBlank(minutesAndSecondsStart)?"00":minutesAndSecondsStart);
        }

        // 数据校验
        if(StringUtil.isBlank(startTime)) {
            return ReturnDWZAjax.fail("开始时间不能为空");
        }
        if(StringUtil.isBlank(productId)) {
            return ReturnDWZAjax.fail("理财计划ID为空");
        }
        if(StringUtil.isNotBlank(endTime)) {
            // 开始时间大于结束时间
            if(com.pinting.business.util.DateUtil.compareTo(DateUtil.parse(startTime+":"+minutesAndSecondsStart, "yyyy-MM-dd HH:mm:ss"), DateUtil.parse(endTime+":"+(StringUtil.isBlank(minutesAndSecondsEnd)?"00":minutesAndSecondsEnd), "yyyy-MM-dd HH:mm:ss")) >= 0) {
                return ReturnDWZAjax.fail("开始时间必须小于结束时间");
            }
        }
        if(com.pinting.business.util.DateUtil.compareTo(DateUtil.parse(startTime+":"+minutesAndSecondsStart, "yyyy-MM-dd HH:mm:ss"), new Date()) <= 0) {
            return ReturnDWZAjax.fail("开始时间必须大于当前时间");
        }
        // 数据校验
        try {
            if(StringUtil.isBlank(endTime)) {
                productReleaseService.releaseProduct(Integer.parseInt(productId), Integer.parseInt(mUserId), startTime + ":" + minutesAndSecondsStart, null);
            } else if(!StringUtil.isBlank(minutesAndSecondsEnd)) {
                productReleaseService.releaseProduct(Integer.parseInt(productId), Integer.parseInt(mUserId), startTime + ":" + minutesAndSecondsStart, endTime + ":" + minutesAndSecondsEnd);
            } else if(StringUtil.isBlank(minutesAndSecondsEnd)) {
                productReleaseService.releaseProduct(Integer.parseInt(productId), Integer.parseInt(mUserId), startTime + ":" + minutesAndSecondsStart, endTime + ":00");
            }
            
            BsProduct bsProduct = productService.findRegularById(Integer.parseInt(productId));
            ReqMsg_ProductPlanStatutsChange_ScheduleRegistReset4Publish reqMsg = new ReqMsg_ProductPlanStatutsChange_ScheduleRegistReset4Publish();
            reqMsg.setBsProduct(bsProduct);
            planStatusChangeService.handleMsg(reqMsg);
            
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("发布操作成功");
    }
    
    /**
     * 结束操作
     * @param productId 产品ID
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/finish")
    public Map<Object, Object> finish(String productId, HttpServletRequest request, HttpServletResponse response) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        try {
            productReleaseService.finishProduct(Integer.parseInt(productId), Integer.parseInt(mUserId));
            
            BsProduct bsProduct = productService.findRegularById(Integer.parseInt(productId));
            ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish reqMsg = new ReqMsg_ProductPlanStatutsChange_ScheduleRegistDelete4Finish();
            reqMsg.setBsProduct(bsProduct);
            planStatusChangeService.handleMsg(reqMsg);
        } catch (Exception e) {
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("结束操作成功");
    }
    
    /**
     * 推荐操作
     * @param productId 产品ID
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/recommend")
    public Map<Object, Object> recommend(String productId, HttpServletRequest request, HttpServletResponse response) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        try {
            productReleaseService.recommendProduct(Integer.parseInt(productId), Integer.parseInt(mUserId));
        } catch (Exception e) {
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("推荐操作成功");
    }
    
    /**
     * 取消推荐操作
     * @param productId 产品ID
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/cancle")
    public Map<Object, Object> cancleRecommend(String productId, HttpServletRequest request, HttpServletResponse response) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        try {
            productReleaseService.cancelRecommendProduct(Integer.parseInt(productId), Integer.parseInt(mUserId));
        } catch (Exception e) {
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("取消推荐操作成功");
    }

    /**
     * 进入加息标签添加页面
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/release_interest_rates_tag")
    public String addInterestRatesTagPage(ProductReleaseInfoVO req, Map<String, Object> model,
                                          HttpServletRequest request, HttpServletResponse response) {
        model.put("productId", req.getProductId());
        BsProductTag productTag = productTagService.qureyProductTagByProductId(req.getProductId(), Constants.BS_PRODUCT_TAG_INTEREST_RATES_TAG);
        if(null != productTag) {
            model.put("productTagType", productTag.getProductTagType());
            model.put("content", productTag.getContent());
        }
        return "/product/plan/release/product_interest_rates_tag";
    }

    /**
     * 进入提醒标签添加页面
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/release_remind_tag")
    public String addRemindTagPage(ProductReleaseInfoVO req, Map<String, Object> model,
                                   HttpServletRequest request, HttpServletResponse response) {
        model.put("productId", req.getProductId());
        BsProductTag productTag = productTagService.qureyProductTagByProductId(req.getProductId(), Constants.BS_PRODUCT_TAG_REMIND_TAG);
        if(null != productTag) {
            model.put("productTagType", productTag.getProductTagType());
            model.put("content", productTag.getContent());
        }
        return "/product/plan/release/product_remind_tag";
    }

    /**
     * 产品标签添加操作
     * @param productId 产品ID
     * @param productTagType 产品标签类型
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/addProductTag")
    public Map<Object, Object> addProductTag(String productId, String productTagType,
                                             HttpServletRequest request, HttpServletResponse response) {
        BsProductTag bsProductTag = new BsProductTag();
        String msg = "";
        if (StringUtil.isNotBlank(productTagType)) {
            if(Constants.BS_PRODUCT_TAG_REMIND_TAG.equals(productTagType)) {
                msg = "提醒标签";
            }else if(Constants.BS_PRODUCT_TAG_INTEREST_RATES_TAG.equals(productTagType)) {
                msg = "加息标签";
            }
        }

        String content = request.getParameter("content");
        if (StringUtil.isNotBlank(productTagType)) {
            content = content;
        }else {
            content = null;
        }
        try {
            BsProductTag productTag = productTagService.qureyProductTagByProductId(Integer.parseInt(productId), productTagType);
            if(null == productTag) {
                bsProductTag.setProductId(Integer.parseInt(productId));
                bsProductTag.setProductTagType(productTagType);
                bsProductTag.setContent(content);
                bsProductTag.setCreateTime(new Date());
                bsProductTag.setUpdateTime(new Date());
                productTagService.addProductTag(bsProductTag);
            }else {
                bsProductTag.setProductId(Integer.parseInt(productId));
                bsProductTag.setProductTagType(productTagType);
                bsProductTag.setContent(content);
                bsProductTag.setUpdateTime(new Date());
                productTagService.updatProductTag(bsProductTag);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success(msg+"添加成功");
    }
    
}
