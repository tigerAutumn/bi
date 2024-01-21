package com.pinting.manage.controller;

import com.pinting.business.model.vo.HFBGWRevenueOfZanVO;
import com.pinting.business.service.manage.HfBGWRevenueOfZanService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.model.HFBGWRevenueOfZanReq;
import com.pinting.util.ExportUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 查询恒丰币港湾营收（赞分期）
 * Created by cyb on 2018/4/20.
 */
@Controller
public class HFBGWRevenueOfZanController {

    @Autowired
    private HfBGWRevenueOfZanService hfBGWRevenueOfZanService;

    @RequestMapping("/hfBgwRevenueOfZan/index")
    public String hfBgwRevenueOfZanIndex(HFBGWRevenueOfZanReq req, HashMap<String, Object> model) {
        Map<String, Object> result = new HashMap<>();
        req.setNumPerPage(req.getNumPerPage() == null ? 20 : req.getNumPerPage());
        req.setPageNum(req.getPageNum() == null ? 1 : req.getPageNum());
        req.setStartTime(StringUtil.isBlank(req.getStartTime()) ? "2017-11-09" : req.getStartTime());
        req.setEndTime(StringUtil.isBlank(req.getEndTime()) ? DateUtil.formatYYYYMMDD(new Date()) : req.getEndTime());
        if("yes".equals(req.getShowData())) {
            int count = hfBGWRevenueOfZanService.countHFBGWRevenueOfZan(req.getStartTime(), req.getEndTime());
            List<HFBGWRevenueOfZanVO> list = hfBGWRevenueOfZanService.queryHFBGWRevenueOfZan(req.getStartTime(), req.getEndTime(), req.getPageNum(), req.getNumPerPage());
            result.put("list", list);
            result.put("count", count);
        }
        result.put("pageNum", req.getPageNum());
        result.put("numPerPage", req.getNumPerPage());
        result.put("startTime", req.getStartTime());
        result.put("endTime", req.getEndTime());
        result.put("showData", req.getShowData());
        model.put("result", result);
        return "/hfBgwRevenueOfZan/index";
    }

    @ResponseBody
    @RequestMapping("/hfBgwRevenueOfZan/totalAmount")
    public Map<String, Object> hfBgwRevenueOfZanTotalAmount(HFBGWRevenueOfZanReq req) {
        Map<String, Object> result = new HashMap<>();
        Double amount = hfBGWRevenueOfZanService.sumHFBGWRevenueOfZan(req.getStartTime(), req.getEndTime());
        result.put("result", MoneyUtil.format(amount));
        return result;
    }

    @RequestMapping("/hfBgwRevenueOfZan/exportXls")
    public void exportXls(HFBGWRevenueOfZanReq req, HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("投资人姓名");
        titles.add("手机号");
        titles.add("资产方");
        titles.add("出借金额");
        titles.add("币港湾营收（恒丰）");
        titles.add("结算日期");
        titleMap.put("title", titles);
        list.add(titleMap);

        List<HFBGWRevenueOfZanVO> dataList = hfBGWRevenueOfZanService.queryHFBGWRevenueOfZan(req.getStartTime(), req.getEndTime(), null, null);
        //设置导出excel内容
        if(!CollectionUtils.isEmpty(dataList)) {
            int i = 0;
            for (HFBGWRevenueOfZanVO data : dataList) {
                Map<String, List<Object>> dataMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.getUserName());
                obj.add(data.getMobile());
                obj.add(data.getPartnerCode());
                obj.add(MoneyUtil.format(data.getTotalAmount()));
                obj.add(MoneyUtil.format(data.getTransAmount()));
                obj.add(DateUtil.format(data.getTransTime()));

                dataMap.put("revenue" + (++i), obj);
                list.add(dataMap);
            }
        }

        try {
            ExportUtil.exportExcel(response, request, "恒丰币港湾营收（赞分期）", list);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
