package com.pinting.manage.controller;

import com.pinting.business.model.vo.RevenueTransDetailVO;
import com.pinting.business.service.manage.RevenueTransDetailService;
import com.pinting.business.util.BeanUtils;
import com.pinting.util.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Author:      shh
 * Date:        2017/6/16
 * Description: 云贷砍头息代收代付Controller
 */
@Controller
@RequestMapping(value = "/revenueTrans")
public class RevenueTransDetailController {

    @Autowired
    private RevenueTransDetailService revenueTransDetailService;

    @RequestMapping(value = "/yunHeadFeeList")
    public String yunHeadFee(String transType, String startTime, String endTime,  String userName, String mobile,
                                 Integer numPerPage, Integer pageNum, Map<String, Object> model) {
        pageNum = pageNum == null ? 1 : pageNum;
        numPerPage = numPerPage == null ? 20 : numPerPage;
        long count = revenueTransDetailService.countYunHeadFee(startTime, endTime, userName, mobile, transType);
        if(count > 0) {
            List<RevenueTransDetailVO> yunFeeList = revenueTransDetailService.
                    queryYunHeadFeeList(startTime, endTime, userName, mobile, transType, numPerPage, pageNum);
            //云贷砍头息代收-总额
            double feeOfDS = revenueTransDetailService.sumFeeOfDS(startTime, endTime, userName, mobile, transType);
            //云贷砍头息代付-总额
            double feeOfDF = revenueTransDetailService.sumFeeOfDF(startTime, endTime, userName, mobile, transType);
            model.put("yunFeeList", yunFeeList);
            model.put("feeOfDS", feeOfDS);
            model.put("feeOfDF", feeOfDF);
        }
        model.put("startTime", startTime);
        model.put("endTime", endTime);
        model.put("userName", userName);
        model.put("mobile", mobile);
        model.put("transType", transType);
        model.put("count", count);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/fee/yunHeadFeeList";
    }

    /**
     * 导出excel
     */
    @RequestMapping("/exportXls")
    public void exportXls(String transType, String startTime, String endTime, String userName, String mobile,
                          Integer numPerPage, Integer pageNum, HttpServletResponse response,
                          HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("融资客户名称");
        titles.add("手机号");
        titles.add("类型");
        titles.add("借款金额");
        titles.add("代收");
        titles.add("代付");
        titles.add("产生时间");
        titleMap.put("title", titles);
        list.add(titleMap);
        pageNum = 1;
        List<RevenueTransDetailVO> feeList = new ArrayList<RevenueTransDetailVO>();
        long count = revenueTransDetailService.countYunHeadFee(startTime, endTime, userName, mobile, transType);
        if (count > 0) {
            feeList =  revenueTransDetailService.
                    queryYunHeadFeeList(startTime, endTime, userName, mobile, transType, Integer.parseInt(String.valueOf(count)), pageNum);
        }
        ArrayList<HashMap<String, Object>> datas = BeanUtils.classToArrayList(feeList);
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("userName"));
                obj.add(data.get("mobile"));
                obj.add((data.get("transType") != null && "HEAD_FEE_INCOME".equals(data.get("transType"))) ? "代收" : "代付");
                obj.add(data.get("applyAmount"));
                obj.add(data.get("revenueAmount"));
                obj.add(data.get("transAmount"));
                obj.add(data.get("createTime"));
                datasMap.put("fee" + (++i), obj);
                list.add(datasMap);
            }
        }
        try {
            ExportUtil.exportExcel(response, request, "云贷砍头息代收代付", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
