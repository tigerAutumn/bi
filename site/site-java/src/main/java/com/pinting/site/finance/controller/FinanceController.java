/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.site.finance.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.core.base.BaseController;

/**
 * 理财产品
 * @author HuanXiong
 * @version $Id: FinanceController.java, v 0.1 2015-11-12 上午10:19:08 HuanXiong Exp $
 */
@Controller
public class FinanceController extends BaseController {

    /**
     * 理财产品列表 1
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/finance/finance_product_list")
    public String financeProductList(@PathVariable String channel, HttpServletRequest request,
                                     HttpServletResponse response, Map<String, Object> model) {
        return channel + "/finance/finance_product_list";
    }

    /**
     * 理财产品细节 2
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/finance/finance_product_detail")
    public String financeProductDetail(@PathVariable String channel, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {
        return channel + "/finance/finance_product_detail";
    }

    /**
     * 【购买产品】未绑卡
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/finance/buy_product_null")
    public String buyProductNull(@PathVariable String channel, HttpServletRequest request,
                                 HttpServletResponse response, Map<String, Object> model) {
        return channel + "/finance/buy_product_null";
    }

    /**
     * 【购买产品】添加银行卡
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/finance/buy_product_add_card")
    public String buyProductAddCard(@PathVariable String channel, HttpServletRequest request,
                                    HttpServletResponse response, Map<String, Object> model) {
        return channel + "/finance/buy_product_add_card";
    }

    /**
     * 【购买产品】已绑卡
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/finance/buy_product")
    public String buyProduct(@PathVariable String channel, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) {
        return channel + "/finance/buy_product";
    }

    /**
     * 【购买产品】已绑卡，更改银行卡
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/finance/buy_product_change_card")
    public String buyProductChangeCard(@PathVariable String channel, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {
        return channel + "/finance/buy_product_change_card";
    }

    /**
     * 【购买产品】购买结果页
     * 
     * @param channel
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("{channel}/finance/buy_product_result")
    public String buyProductResult(@PathVariable String channel, HttpServletRequest request,
                                   HttpServletResponse response, Map<String, Object> model) {
        return channel + "/finance/buy_product_result";
    }

}
