package com.pinting.manage.controller.mall;

import com.pinting.core.cookie.CookieManager;
import com.pinting.mall.enums.MallInfoStatusEnum;
import com.pinting.mall.model.MallCommodityInfoWithBLOBs;
import com.pinting.mall.model.MallCommodityType;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.manange.MallCommodityInfoVO;
import com.pinting.mall.service.MallCommodityInfoService;
import com.pinting.mall.service.MallCommodityTypeService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.GlobEnv;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * 商品管理Controller
 *
 * @author zousheng
 * @date 2018-05-15
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallCommodityInfoController {

    private Logger log = LoggerFactory.getLogger(MallCommodityInfoController.class);

    @Autowired
    private MallCommodityInfoService mallCommodityInfoService;
    @Autowired
    private MallCommodityTypeService mallCommodityTypeService;

    /**
     * 查询商品信息列表
     *
     * @param pagerReq
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/getList")
    public String getList(@ModelAttribute PagerReqDTO pagerReq, @ModelAttribute MallCommodityInfoVO req, Map<String, Object> model) {

        PagerModelRspDTO pageList = mallCommodityInfoService.queryMallCommodityInfoList(req, pagerReq);
        List<MallCommodityType> typeList = mallCommodityTypeService.queryMallCommodityTypeAll();

        model.put("mallInfoStatusEnum", MallInfoStatusEnum.toMapWithoutDeleted());
        model.put("mallCommodityTypeEnum", toMallCommodityTypeMap(typeList));
        model.put("req", req);
        model.put("pageList", pageList);
        String mUrl = GlobEnv.get("gen.web");
        model.put("mUrl", mUrl);
        return "mall/mallCommodityInfo/mallCommodityInfo_list";
    }

    /**
     * 组装商品类型下拉列表
     *
     * @param list
     * @return
     */
    private Map<String, String> toMallCommodityTypeMap(List<MallCommodityType> list) {
        Map<String, String> enumDataMap = new LinkedHashMap<>();
        for (MallCommodityType info : list) {
            enumDataMap.put(info.getId().toString(), info.getCommTypeName());
        }
        return enumDataMap;
    }

    /**
     * 进入新增/克隆兑换商品信息页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/enterCommodityPage")
    public String enterCommodityPage(Integer id, Map<String, Object> model) {

        if (id != null) {
            model.put("info", mallCommodityInfoService.queryMallCommodityDetail(id));
        }
        model.put("mallInfoStatusEnum", MallInfoStatusEnum.toMapWithoutDeleted());
        List<MallCommodityType> typeList = mallCommodityTypeService.queryMallCommodityTypeAll();
        model.put("mallCommodityTypeEnum", toMallCommodityTypeMap(typeList));
        String mUrl = GlobEnv.get("gen.web");
        model.put("mUrl", mUrl);
        return "mall/mallCommodityInfo/mallCommodityInfo_detail";
    }

    /**
     * 进入编辑兑换商品信息页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/enterCommodityEditPage")
    public String enterCommodityEditPage(Integer id, Map<String, Object> model) {

        if (id != null) {
            model.put("info", mallCommodityInfoService.queryMallCommodityDetail(id));
        }
        model.put("mallInfoStatusEnum", MallInfoStatusEnum.toMapWithoutDeleted());
        List<MallCommodityType> typeList = mallCommodityTypeService.queryMallCommodityTypeAll();
        model.put("mallCommodityTypeEnum", toMallCommodityTypeMap(typeList));
        String mUrl = GlobEnv.get("gen.web");
        model.put("mUrl", mUrl);
        return "mall/mallCommodityInfo/mallCommodityInfo_edit";
    }

    /**
     * 新增/克隆兑换商品信息
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/saveCommodity")
    @ResponseBody
    public Map<Object, Object> saveCommodity(MallCommodityInfoWithBLOBs req, HttpServletRequest request, Map<String, Object> model) {

        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }

        if (StringUtils.isBlank(req.getCommName())) {
            return ReturnDWZAjax.fail("商品名称不能为空！");
        }
        if (req.getCommTypeId() == null) {
            return ReturnDWZAjax.fail("商品类型不能为空！");
        }
        if (StringUtils.isBlank(req.getCommProperty())) {
            return ReturnDWZAjax.fail("商品属性不能为空！");
        }
        if (req.getPoints() == null) {
            return ReturnDWZAjax.fail("所需积分不能为空！");
        }
        if (req.getPoints() <= 0) {
            return ReturnDWZAjax.fail("所需积分必须大于0！");
        }
        if (req.getLeftCount() == null) {
            return ReturnDWZAjax.fail("剩余库存不能为空！");
        }
        if (req.getLeftCount() < 0) {
            return ReturnDWZAjax.fail("剩余库存不能小于0！");
        }
        if (StringUtils.isBlank(req.getIsRecommend())) {
            return ReturnDWZAjax.fail("是否推荐不能为空！");
        }
        if (StringUtils.isBlank(req.getCommPictureUrl())) {
            return ReturnDWZAjax.fail("商品主图必须上传！");
        }
        if (StringUtils.isBlank(req.getExchangeNote())) {
            return ReturnDWZAjax.fail("兑换需知不能为空！");
        }
        if (req.getExchangeNote().length() > Constants.MYSQL_TEXT_MAX_LENGHT) {
            return ReturnDWZAjax.fail("兑换需知内容过多！");
        }
        if (StringUtils.isBlank(req.getCommDetails())) {
            return ReturnDWZAjax.fail("商品详情不能为空！");
        }
        if (req.getCommDetails().length() > Constants.MYSQL_TEXT_MAX_LENGHT) {
            return ReturnDWZAjax.fail("商品详情内容过多！");
        }

        try {
            req.setCreator(Integer.parseInt(mUserId));
            req.setLastOperator(Integer.parseInt(mUserId));
            req.setSoldCount(0);
            req.setStatus(MallInfoStatusEnum.SOLD_OUT.getCode());
            req.setCreateTime(new Date());
            req.setUpdateTime(new Date());
            mallCommodityInfoService.addMallCommodityInfo(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }

        return ReturnDWZAjax.success("添加成功");
    }

    /**
     * 修改兑换商品信息
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/editCommodity")
    @ResponseBody
    public Map<Object, Object> editCommodity(MallCommodityInfoWithBLOBs req, HttpServletRequest request, Map<String, Object> model) {

        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }

        if (req.getId() == null) {
            return ReturnDWZAjax.fail("商品ID不能为空！");
        }
        if (req.getLeftCount() == null) {
            return ReturnDWZAjax.fail("剩余库存不能为空！");
        }
        if (req.getLeftCount() < 0) {
            return ReturnDWZAjax.fail("剩余库存不能小于0！");
        }
        if (StringUtils.isBlank(req.getIsRecommend())) {
            return ReturnDWZAjax.fail("是否推荐不能为空！");
        }
        if (StringUtils.isBlank(req.getExchangeNote())) {
            return ReturnDWZAjax.fail("兑换需知不能为空！");
        }
        if (req.getExchangeNote().length() > Constants.MYSQL_TEXT_MAX_LENGHT) {
            return ReturnDWZAjax.fail("兑换需知内容过多！");
        }
        if (StringUtils.isBlank(req.getCommDetails())) {
            return ReturnDWZAjax.fail("商品详情不能为空！");
        }
        if (req.getCommDetails().length() > Constants.MYSQL_TEXT_MAX_LENGHT) {
            return ReturnDWZAjax.fail("商品详情内容过多！");
        }

        try {
            MallCommodityInfoWithBLOBs editCommodity = new MallCommodityInfoWithBLOBs();
            editCommodity.setId(req.getId());
            editCommodity.setLeftCount(req.getLeftCount());
            editCommodity.setIsRecommend(req.getIsRecommend());
            editCommodity.setExchangeNote(req.getExchangeNote());
            editCommodity.setCommDetails(req.getCommDetails());
            editCommodity.setLastOperator(Integer.parseInt(mUserId));
            editCommodity.setUpdateTime(new Date());
            mallCommodityInfoService.updateMallCommodityInfo(editCommodity);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }

        return ReturnDWZAjax.success("添加成功");
    }

    /**
     * 兑换商品上架
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/forSaleCommodity")
    @ResponseBody
    public Map<Object, Object> forSaleCommodity(@RequestParam Integer id, HttpServletRequest request, Map<String, Object> model) {

        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }
        try {
            mallCommodityInfoService.forSaleCommodity(id, Integer.parseInt(mUserId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }

        return ReturnDWZAjax.success("添加成功");
    }

    /**
     * 兑换商品下架
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/soldOutCommodity")
    @ResponseBody
    public Map<Object, Object> soldOutCommodity(@RequestParam Integer id, HttpServletRequest request, Map<String, Object> model) {

        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }
        try {
            mallCommodityInfoService.soldOutCommodity(id, Integer.parseInt(mUserId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }

        return ReturnDWZAjax.success("添加成功");
    }
    
    /**
     * 删除商品
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityInfo/deleteCommodity")
    @ResponseBody
    public Map<Object, Object> deleteCommodity(@RequestParam Integer commodityId, HttpServletRequest request, Map<String, Object> model) {

        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }
        try {
            mallCommodityInfoService.deleteCommodity(commodityId, Integer.parseInt(mUserId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }

        return ReturnDWZAjax.success("操作成功");
    }
    
}
