package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.model.BsRepayJnl;
import com.pinting.business.model.vo.PageInfoObject;
import com.pinting.business.service.manage.BsLoanRelativeRepayJnlService;
import com.pinting.util.ReturnDWZAjax;

/**
 * 
 * @author HuanXiong
 * @version 2016-4-27 下午8:26:07
 */
@Controller
public class BsLoanRelativeRepayJnlController {

    @Autowired
    private BsLoanRelativeRepayJnlService bsLoanRelativeRepayJnlService;

    /**
     * 
     * @param request
     * @param response
     * @param model
     * @param req
     * @return
     */
    @RequestMapping("/loan_relative/loan_relative_index")
    public String loanRelativeIndex(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> model, PageInfoObject req) {
        ArrayList<HashMap<String, Object>> dataGrid = bsLoanRelativeRepayJnlService.queryDataGrid(
            req.getStart(), req.getNumPerPage());
        int count = bsLoanRelativeRepayJnlService.countDataGrid();
        model.put("req", req);
        model.put("dataGrid", dataGrid);
        model.put("count", count);
        return "/loan_relative/loan_relative_index";
    }

    /**
     * 拉取还款数据并显示
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/loan_relative/draw_relay")
    public String drawRelay(HttpServletRequest request, HttpServletResponse response,
                                    Map<String, Object> model) {
        String customerId = request.getParameter("customerId");
        String borrowNo = request.getParameter("borrowNo");
        String id = request.getParameter("id");
        try {
            bsLoanRelativeRepayJnlService.drawRepayment(customerId, borrowNo, id);
        } catch (Exception e) {
            model.put("dataGrid", null);
            model.put("errMsg", "达飞还款数据拉取失败："+e.getMessage());
        }
        ArrayList<HashMap<String, Object>> dataGrid = bsLoanRelativeRepayJnlService.queryRepayJnDataGrid(customerId, borrowNo, 0, 20);
        model.put("dataGrid", dataGrid);
        return "/loan_relative/repay_detail";
    }
    
}
