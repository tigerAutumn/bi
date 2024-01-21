package com.pinting.gateway.baofoo.in.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 剑钊 on 2016/7/22.
 * 宝付代付异步通知（暂未开通）
 */
@Controller
@RequestMapping("baofoo/acctTrans")
public class TransNoticeController {

    private static Logger log = LoggerFactory
            .getLogger(TransNoticeController.class);


    @ResponseBody
    @RequestMapping("/notice")
    public String notice(HttpServletRequest request, HttpServletResponse response){

        return null;
    }
}
