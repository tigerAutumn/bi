package com.pinting.manage.controller;

import com.pinting.business.hessian.manage.message.ReqMsg_StagingProductsLoan_QueryHSList;
import com.pinting.business.hessian.manage.message.ReqMsg_StagingProductsLoan_QueryPTList;
import com.pinting.business.hessian.manage.message.ResMsg_StagingProductsLoan_QueryHSList;
import com.pinting.business.hessian.manage.message.ResMsg_StagingProductsLoan_QueryPTList;
import com.pinting.business.model.dto.StagingProductsLoanDTO;
import com.pinting.business.model.vo.StagingProductsLoanVO;
import com.pinting.business.service.manage.StagingProductsLoanService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by shh on 2016/11/8 9:58.
 */
@Controller
public class StagingProductsLoanController {

    @Autowired
    @Qualifier("dispatchService")
    public HessianService stagingProductsService;
    @Autowired
    private StagingProductsLoanService stagingProductsLoanService;

    /**
     * 品听分期出借查询
     *
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/stagingProducts/ptIndex")
    public String PTStagingProductsInit(ReqMsg_StagingProductsLoan_QueryPTList req, HttpServletRequest request, Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_100_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        String orderDirection = request.getParameter("orderDirection");
        String orderField = request.getParameter("orderField");
        if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
            req.setOrderDirection(orderDirection);
            req.setOrderField(orderField);
            model.put("orderDirection", orderDirection);
            model.put("orderField", orderField);
        }
        ResMsg_StagingProductsLoan_QueryPTList res = (ResMsg_StagingProductsLoan_QueryPTList) stagingProductsService.handleMsg(req);
        model.put("req", req);
        model.put("pageNum", res.getPageNum());
        model.put("numPerPage", res.getNumPerPage());
        model.put("totalRows", res.getTotalRows());
        model.put("ptDataList", res.getValueList());
        return "/stagingProductsLoan/ptLoan_index";
    }

    /**
     * 钱报分期出借查询
     *
     * @return
     */
    @RequestMapping("/stagingProducts/qbIndex")
    public String qbStagingProductsIndex(HttpServletRequest request, Map<String, Object> model, StagingProductsLoanDTO loanDTO) {
        if (loanDTO.getPageNum() <= 0 || loanDTO.getPageNum() <= 0) {
            loanDTO.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
            loanDTO.setNumPerPage(Constants.MANAGE_100_NUMPERPAGE);
        }

        List<StagingProductsLoanVO> voList = stagingProductsLoanService.queryQBStagingProductsLoan(loanDTO);

        model.put("list", voList);
        model.put("loanDTO", loanDTO);
        model.put("totalRows", stagingProductsLoanService.queryQBStagingProductsLoanCount(loanDTO));

        return "/stagingProductsLoan/qbLoan_index";
    }

    /**
     * 杭商分期出借查询
     *
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/stagingProducts/hsIndex")
    public String HSStagingProductsInit(ReqMsg_StagingProductsLoan_QueryHSList req, HttpServletRequest request, Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_100_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        String orderDirection = request.getParameter("orderDirection");
        String orderField = request.getParameter("orderField");
        if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
            req.setOrderDirection(orderDirection);
            req.setOrderField(orderField);
            model.put("orderDirection", orderDirection);
            model.put("orderField", orderField);
        }
        ResMsg_StagingProductsLoan_QueryHSList res = (ResMsg_StagingProductsLoan_QueryHSList) stagingProductsService.handleMsg(req);
        model.put("req", req);
        model.put("pageNum", res.getPageNum());
        model.put("numPerPage", res.getNumPerPage());
        model.put("totalRows", res.getTotalRows());
        model.put("ptDataList", res.getValueList());
        return "/stagingProductsLoan/hsLoan_index";
    }


    /**
     * 品听-赞分期出借查询 导出excel
     *
     * @param req
     * @param response
     * @param request
     */
    @RequestMapping("/stagingProducts/ptExportXls")
    public void interestRepaymentXls(ReqMsg_StagingProductsLoan_QueryPTList req, HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("订单号");
        titles.add("用户id");
        titles.add("期数");
        titles.add("出借金额");
        titles.add("服务费");
        titles.add("成交日期");
        titles.add("渠道");
        titleMap.put("title", titles);
        list.add(titleMap);
        req.setPageNum(1);
        if (req.getTotalRows() == null || "".equals(req.getTotalRows())) {
            req.setNumPerPage(0);
        } else {
            req.setNumPerPage(req.getTotalRows());
        }

        ResMsg_StagingProductsLoan_QueryPTList res = (ResMsg_StagingProductsLoan_QueryPTList) stagingProductsService.handleMsg(req);
        List<HashMap<String, Object>> datas = res.getValueList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("orderNo"));
                obj.add(data.get("userId"));
                obj.add(data.get("period"));
                obj.add(data.get("totalAmount"));
                obj.add(data.get("serviceAmount"));
                obj.add(data.get("loanTime"));
                obj.add(data.get("agentName"));
                datasMap.put("user" + (++i), obj);
                list.add(datasMap);
            }
        }
        try {
            ExportUtil.exportExcel(response, request, "品听分期出借查询", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 杭商分期出借查询 导出excel
     *
     * @param req
     * @param response
     * @param request
     */
    @RequestMapping("/stagingProducts/hsExportXls")
    public void interestRepaymentHSXls(ReqMsg_StagingProductsLoan_QueryHSList req, HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("订单号");
        titles.add("用户id");
        titles.add("期数");
        titles.add("出借金额");
        titles.add("服务费");
        titles.add("成交日期");
        titles.add("渠道");
        titleMap.put("title", titles);
        list.add(titleMap);
        req.setPageNum(1);
        if (req.getTotalRows() == null || "".equals(req.getTotalRows())) {
            req.setNumPerPage(0);
        } else {
            req.setNumPerPage(req.getTotalRows());
        }

        ResMsg_StagingProductsLoan_QueryHSList res = (ResMsg_StagingProductsLoan_QueryHSList) stagingProductsService.handleMsg(req);
        List<HashMap<String, Object>> datas = res.getValueList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("orderNo"));
                obj.add(data.get("userId"));
                obj.add(data.get("period"));
                obj.add(data.get("totalAmount"));
                obj.add(data.get("serviceAmount"));
                obj.add(data.get("loanTime"));
                obj.add(data.get("agentName"));
                datasMap.put("user" + (++i), obj);
                list.add(datasMap);
            }
        }
        try {
            ExportUtil.exportExcel(response, request, "杭商分期出借查询", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 钱报分期出借查询 导出excel
     *
     * @param loanDTO
     * @param response
     * @param request
     */
    @RequestMapping("/stagingProducts/qianExportXls")
    public void interestRepaymentQBXls(StagingProductsLoanDTO loanDTO, HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<>();
        List<Object> titles = new ArrayList<>();
        titles.add("订单号");
        titles.add("用户id");
        titles.add("期数");
        titles.add("出借金额");
        titles.add("服务费");
        titles.add("成交日期");
        titles.add("渠道");
        titleMap.put("title", titles);
        list.add(titleMap);
        loanDTO.setPageNum(1);
        loanDTO.setNumPerPage(stagingProductsLoanService.queryQBStagingProductsLoanCount(loanDTO));

        List<StagingProductsLoanVO> voList = stagingProductsLoanService.queryQBStagingProductsLoan(loanDTO);

        List<HashMap<String, Object>> datas = BeanUtils.classToArrayList(voList);
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("orderNo"));
                obj.add(data.get("userId"));
                obj.add(data.get("period"));
                obj.add(data.get("totalAmount"));
                obj.add(data.get("serviceAmount"));
                obj.add(data.get("loanTime"));
                obj.add(data.get("agentName"));
                datasMap.put("user" + (++i), obj);
                list.add(datasMap);
            }
        }
        try {
            ExportUtil.exportExcel(response, request, "钱报分期出借查询", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
