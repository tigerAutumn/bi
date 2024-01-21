package com.pinting.manage.controller;

import com.pinting.business.model.vo.BsInterestTicketManageVO;
import com.pinting.business.service.manage.BsTicketInterestService;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * 加息卷
 *
 * @author SHENGP
 * @date 2018年4月4日 上午11:24:12
 */
@Controller
@RequestMapping(value = "/interestTicket")
public class BsInterestTicketController {

    @Autowired
    private BsTicketInterestService bsTicketInterestService;

    /**
     * 加息券查询
     *
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/ticketInfo/index")
    public String ticketInfoIndex(HttpServletRequest request, Map<String, Object> model) {
        String userId = request.getParameter("userId");
        String mobile = request.getParameter("mobile");
        String distributeType = request.getParameter("distributeType");
        String serialName = request.getParameter("serialName");
        String status = request.getParameter("status");
        String distributeStatus = request.getParameter("distributeStatus");

        String useValidTimeStart = request.getParameter("useValidTimeStart");
        String useValidTimeEnd = request.getParameter("useValidTimeEnd");
        String distributeTimeStart = request.getParameter("distributeTimeStart");
        String distributeTimeEnd = request.getParameter("distributeTimeEnd");
        String lastFinishInterestDateStart = request.getParameter("lastFinishInterestDateStart");
        String lastFinishInterestDateEnd = request.getParameter("lastFinishInterestDateEnd");
        String useTimeStart = request.getParameter("useTimeStart");
        String useTimeEnd = request.getParameter("useTimeEnd");
        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        String queryFlag = request.getParameter("queryFlag");
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
        }

        BsInterestTicketManageVO record = new BsInterestTicketManageVO();
        if (StringUtils.isNotEmpty(status)) {
            record.setStatus(status);
        }
        if (StringUtils.isNotEmpty(distributeStatus)) {
            record.setDistributeStatus(distributeStatus);
        }
        if (StringUtils.isNotEmpty(distributeType)) {
            record.setDistributeType(distributeType);
        }
        if (StringUtils.isNotEmpty(serialName)) {
            record.setSerialName(serialName.trim());
        }
        if (StringUtil.isNotEmpty(mobile)) {
            record.setMobile(mobile.trim());
        }
        if (StringUtils.isNotEmpty(userId)) {
            record.setUserId(Integer.parseInt(userId.trim()));
        }

        record.setUseValidTimeStart(useValidTimeStart);
        record.setUseValidTimeEnd(useValidTimeEnd);
        record.setDistributeTimeStart(distributeTimeStart);
        record.setDistributeTimeEnd(distributeTimeEnd);
        record.setUseTimeStart(useTimeStart);
        record.setUseTimeEnd(useTimeEnd);
        record.setLastFinishInterestDateStart(lastFinishInterestDateStart);
        record.setLastFinishInterestDateEnd(lastFinishInterestDateEnd);

        if (StringUtil.isNotEmpty(queryFlag) && "QUERY".equals(queryFlag)) { // 加息券默认不查询
            // 统计
            int count = bsTicketInterestService.countTicketInterestInfo(record);
            record.setPageNum(Integer.valueOf(pageNum));
            record.setNumPerPage(Integer.valueOf(numPerPage));
            record.setTotalRows(count);

            // 列表查询
            if (count > 0) {
                List<BsInterestTicketManageVO> resultList = bsTicketInterestService.selectTicketInterestInfoList(record);
                model.put("totalRows", count);
                model.put("interestTicketList", resultList);
                Double interestTotal = bsTicketInterestService.sumTicketInterest(record);
                model.put("interestTotal", interestTotal);
            } else {
                model.put("totalRows", 0);
            }
        }

        // 将数据返回给页面
        model.put("userId", userId);
        model.put("mobile", mobile);
        model.put("distributeType", distributeType);
        model.put("serialName", serialName);
        model.put("status", status);
        model.put("distributeStatus", distributeStatus);
        model.put("useValidTimeStart", useValidTimeStart);
        model.put("useValidTimeEnd", useValidTimeEnd);
        model.put("distributeTimeStart", distributeTimeStart);
        model.put("distributeTimeEnd", distributeTimeEnd);
        model.put("useTimeStart", useTimeStart);
        model.put("useTimeEnd", useTimeEnd);
        model.put("lastFinishInterestDateStart", lastFinishInterestDateStart);
        model.put("lastFinishInterestDateEnd", lastFinishInterestDateEnd);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/ticket/ticketInfo/index";
    }

}
