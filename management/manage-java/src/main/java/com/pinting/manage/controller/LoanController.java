package com.pinting.manage.controller;

import com.pinting.business.model.dto.LoanDTO;
import com.pinting.business.model.vo.LoanVO;
import com.pinting.business.service.manage.LoanService;
import com.pinting.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * @title 借款管理
 * Created by 剑钊 on 2016/11/14.
 */
@Controller
public class LoanController {

    @Autowired
    private LoanService loanService;

    @RequestMapping("/loan/index")
    public String loanIndex(LoanDTO loanDTO, HttpServletRequest request,Map<String, Object> model){

        Integer pageNum = request.getParameter("pageNum") == null ? 0 : Integer.parseInt(request.getParameter("pageNum"));
        Integer numPerPage = request.getParameter("numPerPage") == null ? 0 : Integer.parseInt(request.getParameter("numPerPage"));

        if (numPerPage <= 0 || pageNum <= 0) {
            loanDTO.setNumPerPage(Constants.MANAGE_100_NUMPERPAGE);
            loanDTO.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
        }

        try {
            List<LoanVO> voList=loanService.queryLoanList(loanDTO);
            model.put("list",voList);
            model.put("totalRows", loanService.queryLoanCount(loanDTO));
        }catch (Exception e){
            e.printStackTrace();
            model.put("list",null);
            model.put("totalRows", 0);
        }

        model.put("loanUserDTO",loanDTO);

        return "/loan/ln_index";
    }
}
