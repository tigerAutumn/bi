package com.pinting.manage.controller.mall;

import com.pinting.business.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.vo.MallAccountJnlVO;
import com.pinting.mall.model.vo.MallUserPointsVO;
import com.pinting.mall.service.MallUserPointsService;
import com.pinting.mall.service.site.MallAccountService;
import com.pinting.mall.util.BeanUtils;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * 积分用户管理
 *
 * @author shh
 * @date 2018/5/17 9:54
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallUserPointsController {

    @Autowired
    private MallUserPointsService mallUserPointsService;

    @Autowired
    private MallAccountService mallAccountService;

    /**
     * 积分用户管理查询
     * @param pagerReq
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/mallUserPoints/getList")
    public String getList(@ModelAttribute PagerReqDTO pagerReq, HttpServletRequest request, HttpServletResponse response,
                          Map<String, Object> model) {
        String searchMobile = request.getParameter("searchMobile");
        String startTotalGainPoints = request.getParameter("startTotalGainPoints");
        String endTotalGainPoints = request.getParameter("endTotalGainPoints");
        String startAvaliableBalance = request.getParameter("startAvaliableBalance");
        String endAvaliableBalance = request.getParameter("endAvaliableBalance");
        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        String queryDataFlag = request.getParameter("queryDataFlag");
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
        }

        MallUserPointsVO mallUserPointsVO = new MallUserPointsVO();
        if (StringUtils.isNotEmpty(searchMobile)) {
            mallUserPointsVO.setSearchMobile(searchMobile.trim());
        }
        if (StringUtils.isNotEmpty(startTotalGainPoints)) {
            mallUserPointsVO.setStartTotalGainPoints(startTotalGainPoints.trim());
        }
        if (StringUtils.isNotEmpty(endTotalGainPoints)) {
            mallUserPointsVO.setEndTotalGainPoints(endTotalGainPoints.trim());
        }
        if (StringUtils.isNotEmpty(startAvaliableBalance)) {
            mallUserPointsVO.setStartAvaliableBalance(startAvaliableBalance.trim());
        }
        if (StringUtils.isNotEmpty(endAvaliableBalance)) {
            mallUserPointsVO.setEndAvaliableBalance(endAvaliableBalance.trim());
        }

        int startPage = Integer.parseInt(pageNum);
        int pageSize = Integer.parseInt(numPerPage);
        startPage = (startPage <= 1) ? 0 : ((startPage - 1) * pageSize);
        mallUserPointsVO.setPageNum(startPage);
        mallUserPointsVO.setNumPerPage(pageSize);

        // 将数据返回给页面
        model.put("searchMobile", searchMobile);
        model.put("startTotalGainPoints", startTotalGainPoints);
        model.put("endTotalGainPoints", endTotalGainPoints);
        model.put("startAvaliableBalance", startAvaliableBalance);
        model.put("endAvaliableBalance", endAvaliableBalance);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);

        // 默认不查询
        if(null != queryDataFlag && queryDataFlag.equals("QUERYDATAFLAG")) {
            PagerModelRspDTO<MallUserPointsVO> resultList = mallUserPointsService.queryMallUserPointsListNew(mallUserPointsVO, pagerReq);
            model.put("mallUserPointsList", resultList.getList());
            model.put("count", resultList.getTotalRow());
            // 偏移量
            model.put("offset", resultList.getOffset());
        }

        return "/mall/malluserpoints/mallUserPoints_list";
    }

    /**
     * 积分用户管理导出excel
     * @param pagerReq
     * @param request
     * @param response
     * @param model
     */
    @RequestMapping("/mallUserPoints/exportXls")
    public void exportXls(@ModelAttribute PagerReqDTO pagerReq, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("序号");
        titles.add("手机号");
        titles.add("账户累计积分");
        titles.add("剩余积分");
        titleMap.put("title", titles);
        list.add(titleMap);

        String searchMobile = request.getParameter("searchMobile");
        String startTotalGainPoints = request.getParameter("startTotalGainPoints");
        String endTotalGainPoints = request.getParameter("endTotalGainPoints");
        String startAvaliableBalance = request.getParameter("startAvaliableBalance");
        String endAvaliableBalance = request.getParameter("endAvaliableBalance");

        MallUserPointsVO mallUserPointsVO = new MallUserPointsVO();
        if (StringUtils.isNotEmpty(searchMobile)) {
            mallUserPointsVO.setSearchMobile(searchMobile.trim());
        }
        if (StringUtils.isNotEmpty(startTotalGainPoints)) {
            mallUserPointsVO.setStartTotalGainPoints(startTotalGainPoints.trim());
        }
        if (StringUtils.isNotEmpty(endTotalGainPoints)) {
            mallUserPointsVO.setEndTotalGainPoints(endTotalGainPoints.trim());
        }
        if (StringUtils.isNotEmpty(startAvaliableBalance)) {
            mallUserPointsVO.setStartAvaliableBalance(startAvaliableBalance.trim());
        }
        if (StringUtils.isNotEmpty(endAvaliableBalance)) {
            mallUserPointsVO.setEndAvaliableBalance(endAvaliableBalance.trim());
        }

        List<MallUserPointsVO> resultList = new ArrayList<MallUserPointsVO>();

        int count = mallUserPointsService.queryMallUserPointsCount(mallUserPointsVO);
        pagerReq.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
        pagerReq.setNumPerPage(count);
        pagerReq.setCount(false); // 已统计数据，后续查询数据列表，不在统计

        PagerModelRspDTO pageList = new PagerModelRspDTO();

        if (count > 0) {
            pageList = mallUserPointsService.queryMallUserPointsListNew(mallUserPointsVO, pagerReq);
            resultList = pageList.getList();
        }

        // 将数据返回给页面
        model.put("searchMobile", searchMobile);
        model.put("startTotalGainPoints", startTotalGainPoints);
        model.put("endTotalGainPoints", endTotalGainPoints);
        model.put("startAvaliableBalance", startAvaliableBalance);
        model.put("endAvaliableBalance", endAvaliableBalance);

        List<HashMap<String, Object>> datas = BeanUtils.classToArrayList(resultList);

        // 偏移量
        int id = pageList.getOffset();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                id = id +1;
                obj.add(id);
                obj.add(data.get("mobile"));
                obj.add(data.get("totalGainPoints"));
                obj.add(data.get("avaliableBalance"));
                datasMap.put("userPoints" + (++i), obj);
                list.add(datasMap);
            }
        }

        try {
            ExportUtil.exportExcel(response, request, "积分用户管理", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 积分记录
     * @param pagerReq
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/mallUserPointsRecord/getList")
    public String mallUserPointsRecord(@ModelAttribute PagerReqDTO pagerReq, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        String queryData = request.getParameter("queryData");
        String strMobile = request.getParameter("strMobile");
        String startTransTime = request.getParameter("startTransTime");
        String endTransTime = request.getParameter("endTransTime");
        String transType = request.getParameter("transType");
        String pointsType = request.getParameter("pointsType");
        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
        }

        MallAccountJnlVO mallAccountJnlVO = new MallAccountJnlVO();
        if (StringUtils.isNotEmpty(strMobile)) {
            mallAccountJnlVO.setStrMobile(strMobile.trim());
        }
        if (StringUtils.isNotEmpty(transType)) {
            mallAccountJnlVO.setTransType(transType.trim());
        }
        if (StringUtils.isNotEmpty(pointsType)) {
            mallAccountJnlVO.setPointsType(pointsType.trim());
        }

        int startPage = Integer.parseInt(pageNum);
        int pageSize = Integer.parseInt(numPerPage);
        startPage = (startPage <= 1) ? 0 : ((startPage - 1) * pageSize);
        mallAccountJnlVO.setPageNum(startPage);
        mallAccountJnlVO.setNumPerPage(pageSize);
        // 默认查询当天的数据
        if(null == queryData) {
            startTransTime = DateUtil.format(new Date(), "yyyy-MM-dd")+" 00:00:00";
            endTransTime = DateUtil.format(new Date(), "yyyy-MM-dd")+" 23:59:59";
        }
        if (StringUtils.isNotEmpty(startTransTime)) {
            mallAccountJnlVO.setStartTransTime(startTransTime.trim());
        }
        if (StringUtils.isNotEmpty(endTransTime)) {
            mallAccountJnlVO.setEndTransTime(endTransTime.trim());
        }

        PagerModelRspDTO<MallAccountJnlVO> resultList = mallAccountService.queryMallUserPointsList(mallAccountJnlVO, pagerReq);
        model.put("userPointsRecord", resultList.getList());
        model.put("count", resultList.getTotalRow());
        // 兑换的积分总和查
        Double totalPoints = mallAccountService.queryMallUserPointsSum(mallAccountJnlVO);
        String totalPointsStr = "";
        if(totalPoints > 0) {
            totalPointsStr = totalPoints.toString();
            totalPointsStr = totalPointsStr.substring(0, totalPointsStr.indexOf("."));
        }else {
            totalPointsStr = "0";
        }
        // 查询条件手机号、时间都为空，当日兑换的积分置0
        if (!StringUtils.isNotEmpty(endTransTime) && !StringUtils.isNotEmpty(strMobile)) {
            totalPointsStr = "0";
        }

        model.put("totalPointsStr", totalPointsStr);
        // 偏移量
        model.put("offset", resultList.getOffset());


        // 将数据返回给页面
        model.put("strMobile", strMobile);
        model.put("startTransTime", startTransTime);
        model.put("endTransTime", endTransTime);
        model.put("transType", transType);
        model.put("pointsType", pointsType);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);

        return "/mall/malluserpoints/userPoints_record";
    }

}
