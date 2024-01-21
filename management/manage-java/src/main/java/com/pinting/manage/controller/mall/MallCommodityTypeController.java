package com.pinting.manage.controller.mall;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.model.MallCommodityType;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.service.MallCommodityTypeService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Map;

/**
 * 商品类别Controller
 *
 * @author shh
 * @date 2018/5/11 15:18
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallCommodityTypeController {

    private Logger log = LoggerFactory.getLogger(MallCommodityTypeController.class);

    @Autowired
    private MallCommodityTypeService mallCommodityTypeService;

    @RequestMapping("/mallCommodityType/getList")
    public String getList(PagerReqDTO pagerReq, Map<String, Object> model) {

        PagerModelRspDTO resultList = mallCommodityTypeService.queryMallCommodityTypelList(pagerReq);
        model.put("mallCommodityTypeList", resultList.getList());
        model.put("count", resultList.getTotalRow());
        model.put("pageNum", resultList.getPageNum());
        model.put("numPerPage", resultList.getNumPerPage());
        // 偏移量
        model.put("offset", resultList.getOffset());
        return "/mall/mallCommodityType/mallCommodityType_list";
    }

    /**
     * 进入添加/编辑页面
     * @param commodityTypeId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/mallCommodityType/detail")
    public String detail(Integer commodityTypeId, HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> model) {
        if (null != commodityTypeId) {
            MallCommodityType record = mallCommodityTypeService.queryMallCommodityTypeById(commodityTypeId);
            model.put("mallCommodityType", record);
        }
        return "/mall/mallCommodityType/mallCommodityType_detail";
    }

    /**
     * 添加/修改
     * @param commodityTypeId
     * @param commTypeName
     * @param isRecommendValue
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("mallCommodityType/save")
    public @ResponseBody
    Map<Object, Object> save(Integer commodityTypeId, String commTypeName, String isRecommendValue, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) {
        MallCommodityType mallCommodityType = new MallCommodityType();
        // 如果商品类别名字是红包或加息券的时候,code写入红包：RED_PACKET或加息券：INTEREST_TACKET
        if(StringUtil.isNotBlank(commTypeName)) {
            commTypeName = commTypeName.trim();
            mallCommodityType.setCommTypeName(commTypeName);
            if(commTypeName.equals("红包")) {
                mallCommodityType.setCommTypeCode(Constants.MALL_COMMODITY_TYPE_CODE_RED_PACKET);
            }else if(commTypeName.equals("加息券")) {
                mallCommodityType.setCommTypeCode(Constants.MALL_COMMODITY_TYPE_CODE_INTEREST_TACKET);
            }else {
                mallCommodityType.setCommTypeCode("null");
            }
        }

        mallCommodityType.setIsRecommend(isRecommendValue);
        if (null != commodityTypeId) {

            // 查询商品类别名称是否已存在
            MallCommodityType result = mallCommodityTypeService.queryCommTypeName(mallCommodityType);
            if(null != result && !result.getId().equals(commodityTypeId)) {
                return ReturnDWZAjax.toAjaxString("301", "商品类别名称已存在");
            }

            CookieManager cookie = new CookieManager(request);
            String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                    CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
            mallCommodityType.setLastOperator(Integer.parseInt(mUserId));
            // 1、修改
            mallCommodityType.setId(commodityTypeId);
            mallCommodityType.setUpdateTime(new Date());
            try {
                mallCommodityTypeService.updatMallCommodityTypeById(mallCommodityType);
                return ReturnDWZAjax.success("修改成功！");
            }catch(Exception e) {
                log.error("商品类别信息更新失败", e);
                return ReturnDWZAjax.fail("操作失败！");
            }
        }else {
            // 查询商品类别名称是否已存在
            MallCommodityType result = mallCommodityTypeService.queryCommTypeName(mallCommodityType);
            if(null != result) {
                return ReturnDWZAjax.toAjaxString("301", "商品类别名称已存在");
            }

            // 2、新增
            CookieManager cookie = new CookieManager(request);
            String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                    CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
            mallCommodityType.setCreator(Integer.parseInt(mUserId));
            mallCommodityType.setLastOperator(Integer.parseInt(mUserId));
            mallCommodityType.setCreateTime(new Date());
            mallCommodityType.setUpdateTime(new Date());
            try {
                mallCommodityTypeService.addMallCommodityType(mallCommodityType);
                return ReturnDWZAjax.success("新增成功！");
            }catch(Exception e) {
                log.error("商品类别信息新增失败", e);
                return ReturnDWZAjax.fail("操作失败！");
            }
        }
    }

}
