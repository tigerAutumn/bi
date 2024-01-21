package com.pinting.manage.controller;

import com.pinting.business.model.vo.BsServiceFeeVO;
import com.pinting.business.service.manage.BsServiceFeeService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Author:      cyb
 * Date:        2017/1/23
 * Description:
 */
@Controller
@RequestMapping(value = "/fee")
public class BsServiceFeeController {

    @Autowired
    private BsServiceFeeService bsServiceFeeService;

    @RequestMapping(value = "/getList")
    public String getList(String feeType, String startTime, String endTime, String channel, String partnerCode, Integer numPerPage, Integer pageNum, String queryDataFlag , Map<String, Object> model) {
        pageNum = pageNum == null ? 1 : pageNum;
        numPerPage = numPerPage == null ? 20 : numPerPage;

        long count = 0l;
        // 默认不做查询
        if(null != queryDataFlag && queryDataFlag.equals("QUERYDATA")) {
            count = bsServiceFeeService.countServiceFee(feeType, startTime, endTime, channel, partnerCode);
            if(count > 0) {
                List<BsServiceFeeVO> list = bsServiceFeeService.findServiceFeePage(feeType, startTime, endTime, channel, partnerCode, numPerPage, pageNum);
                double feeOfDF = bsServiceFeeService.sumFeeOfDF(feeType, startTime, endTime, channel, partnerCode);
                model.put("list", list);
                model.put("feeOfDF", feeOfDF);
            }
        }

        model.put("feeType", feeType);
        model.put("startTime", startTime);
        model.put("endTime", endTime);
        model.put("count", count);
        model.put("channel", channel);
        model.put("partnerCode", partnerCode);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/fee/getList";
    }

    /**
     * 分页查询提现手续费-营收
     * @return
     */
    @RequestMapping(value = "/withdrawFeeList")
    public String withdrawFeeList(String queryDateFlag, String startTime, String endTime, String userName, String mobile, String channel,
                                  Integer numPerPage, Integer pageNum, Map<String, Object> model) {
        pageNum = pageNum == null ? 1 : pageNum;
        numPerPage = numPerPage == null ? 20 : numPerPage;

        if(StringUtil.isNotBlank(queryDateFlag) && "QUERYDATE".equals(queryDateFlag)) {
            long count = bsServiceFeeService.conutWithdrawFee(startTime, endTime, userName, mobile, channel);
            if(count > 0) {
                List<BsServiceFeeVO> feeList = bsServiceFeeService.queryWithdrawFeeList(startTime, endTime, userName,
                        mobile, channel, numPerPage, pageNum);
                double sumWithdrawFee = bsServiceFeeService.sumWithdrawFee(startTime, endTime, userName, mobile, channel);
                model.put("feeList", feeList);
                model.put("sumWithdrawFee", sumWithdrawFee);
            }
            model.put("startTime", startTime);
            model.put("endTime", endTime);
            model.put("count", count);
        } else { //页面载入自动执行查询系统当前日期前一日的订单
            Date start =  DateUtil.addDays(new Date(), -1);
            String date = DateUtil.formatYYYYMMDD(start);
            long count = bsServiceFeeService.conutWithdrawFee(date, date, userName, mobile, channel);
            if(count > 0) {
                List<BsServiceFeeVO> feeList = bsServiceFeeService.queryWithdrawFeeList(startTime, endTime, userName,
                        mobile, channel, numPerPage, pageNum);
                double sumWithdrawFee = bsServiceFeeService.sumWithdrawFee(date, date, userName, mobile, channel);
                model.put("feeList", feeList);
                model.put("sumWithdrawFee", sumWithdrawFee);
            }
            model.put("startTime", date);
            model.put("endTime", date);
            model.put("count", count);
        }
        model.put("userName", userName);
        model.put("mobile", mobile);
        model.put("channel", channel);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/fee/withdrawFeeList";
    }

    /**
     * 提现手续费导出excel
     */
    @RequestMapping("/exportXls")
    public void exportXls(String queryDateFlag, String startTime, String endTime, String userName, String mobile, String channel,
                          Integer numPerPage, Integer pageNum, HttpServletResponse response,
                          HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("姓名");
        titles.add("手机号");
        titles.add("订单编号");
        titles.add("手续费类型");
        titles.add("操作金额");
        titles.add("收取手续费");
        titles.add("收取端口");
        titles.add("产生时间");
        titleMap.put("title", titles);
        list.add(titleMap);

        pageNum = 1;
        List<BsServiceFeeVO> feeList = new ArrayList<BsServiceFeeVO>();
        if (StringUtil.isNotBlank(queryDateFlag) && "QUERYDATE".equals(queryDateFlag)) {
            long count = bsServiceFeeService.conutWithdrawFee(startTime, endTime, userName, mobile, channel);
            if (count > 0) {
                feeList = bsServiceFeeService.queryWithdrawFeeList(startTime, endTime, userName,
                        mobile, channel, Integer.parseInt(String.valueOf(count)), pageNum);
            }
        } else { //页面载入自动执行查询系统当前日期前一日的订单
            Date start = DateUtil.addDays(new Date(), -1);
            String date = DateUtil.formatYYYYMMDD(start);
            long count = bsServiceFeeService.conutWithdrawFee(date, date, userName, mobile, channel);
            if (count > 0) {
                feeList = bsServiceFeeService.queryWithdrawFeeList(startTime, endTime, userName,
                        mobile, channel, Integer.parseInt(String.valueOf(count)), pageNum);
                double sumWithdrawFee = bsServiceFeeService.sumWithdrawFee(date, date, userName, mobile, channel);
            }
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
                obj.add(data.get("orderNo"));
                obj.add("提现");
                obj.add(data.get("transAmount"));
                obj.add(data.get("doneFee"));
                obj.add((data.get("channel") != null && "HFBANK".equals(data.get("channel"))) ? "恒丰" : "宝付");
                obj.add(data.get("createTime"));

                datasMap.put("fee" + (++i), obj);
                list.add(datasMap);
            }
        }

        try {
            ExportUtil.exportExcel(response, request, "提现手续费营收", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
