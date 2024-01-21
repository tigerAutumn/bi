package com.pinting.manage.controller;

import com.pinting.business.model.dto.LoanUserDTO;
import com.pinting.business.model.vo.LoanUserVO;
import com.pinting.business.model.vo.RepaySchedulePlanVO;
import com.pinting.business.service.manage.LoanService;
import com.pinting.business.service.manage.LoanUsersService;
import com.pinting.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @title 借款用户管理
 * Created by 剑钊 on 2016/11/7.
 */
@Controller
public class LoanUserController {

    @Autowired
    private LoanUsersService loanUserService;
    @Autowired
    private LoanService loanService;

    @RequestMapping("/loan/user_index")
    public String loanUserIndex(LoanUserDTO loanUserDTO, Map<String, Object> model, HttpServletRequest request){

        Integer pageNum = request.getParameter("pageNum") == null ? 0 : Integer.parseInt(request.getParameter("pageNum"));
        Integer numPerPage = request.getParameter("numPerPage") == null ? 0 : Integer.parseInt(request.getParameter("numPerPage"));

        if (numPerPage <= 0 || pageNum <= 0) {
            loanUserDTO.setNumPerPage(Constants.MANAGE_100_NUMPERPAGE);
            loanUserDTO.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
        }

        try {
            List<LoanUserVO> voList = loanUserService.queryLoanUserList(loanUserDTO);
            model.put("list",voList);
            model.put("totalRows", loanUserService.queryLoanUserListCount(loanUserDTO));
        }catch (Exception e){
            model.put("list",null);
            model.put("totalRows", 0);
        }

        model.put("loanUserDTO",loanUserDTO);

        return "/loan/lnuser_index";
    }

    /**
     * 还款计划
     * @param model
     * @param request
     * @return
     */
    @RequestMapping("/loan/repaymentPlan")
    public String repaymentPlanInit(Map<String, Object> model, HttpServletRequest request) {
        String loanId = request.getParameter("loanId");
        List<RepaySchedulePlanVO> repaymentPlanList = loanService.queryRepaySchedulePlanList(Integer.parseInt(loanId));
        model.put("repaymentPlanList", repaymentPlanList);
        return "/loan/repayment_plan";
    }
}
