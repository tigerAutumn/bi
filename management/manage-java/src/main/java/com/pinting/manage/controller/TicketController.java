package com.pinting.manage.controller;

import com.pinting.business.model.vo.BsInterestTicketGrantAttrVO;
import com.pinting.business.model.vo.BsTicketGrantPlanCheckVO;
import com.pinting.business.model.vo.BsTicketInterestManualVO;
import com.pinting.business.model.vo.BsTicketPreDetailVO;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.BsTicketPreDetailService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.TicketGrantplanCheckService;
import com.pinting.business.util.ArrayUtils;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 卡券发放管理
 *
 * @author zousheng
 */
@Controller
@RequestMapping("/ticket")
public class TicketController {

    private Logger log = LoggerFactory.getLogger(TicketController.class);

    @Autowired
    private AgentService agentService;
    @Autowired
    private BsTicketPreDetailService bsTicketPreDetailService;
    @Autowired
    private TicketGrantplanCheckService ticketGrantplanCheckService;
    @Autowired
    private BsUserService bsUserService;

    /**
     * 进入加息券发放计划页面
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/ticketGrantManagementIndex")
    public String ticketGrantManagementIndex(BsTicketGrantPlanCheckVO req, Map<String, Object> model) {
        // ID倒序排序
        req.setOrderByClause("a.id DESC");
        int count = ticketGrantplanCheckService.getGrantManagermentCount(req);
        model.put("count", count);
        if (count > 0) {
            model.put("dataGrid", ticketGrantplanCheckService.getGrantManagerment(req));
        }
        model.put("req", req);
        return "ticket/grantManagement/index";
    }

    /**
     * 停用加息券
     *
     * @param checkId
     * @return
     */
    @RequestMapping("/ticketGrantManagement/disable")
    public @ResponseBody
    Map<Object, Object> disableTicket(@RequestParam Integer checkId) {
        try {
            ticketGrantplanCheckService.disableTicket(checkId);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("停用成功！");
    }

    /**
     * 加息券发放管理--进入查看页面(自动发放加息券)
     *
     * @param model
     * @return
     */
    @RequestMapping("/autoInterestTicket/review")
    public String autoInterestTicketReview(BsInterestTicketGrantAttrVO req, Map<String, Object> model) {
        BsInterestTicketGrantAttrVO grantDetail = ticketGrantplanCheckService.getAutoTicketGrantDetail(req);
        grantDetail.setAgentIdsDesc(getAllAgentName(grantDetail.getAgentIds()));
        model.put("interestTicket", grantDetail);
        return "ticket/grantManagement/auto_interest_ticket_review";
    }


    /**
     * 加息券发放管理--进入查看页面(手动发放加息券)
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/manualInterestTicket/review")
    public String manualInterestTicketReview(BsInterestTicketGrantAttrVO grantAttrVO, BsTicketPreDetailVO req, Map<String, Object> model) {
        BsInterestTicketGrantAttrVO attrVO = ticketGrantplanCheckService.getManualTicketGrantDetail(grantAttrVO);
        model.put("interestTicket", attrVO);
        model.put("req", req);
        req.setSerialNoSearch(attrVO.getSerialNo());
        int count = bsTicketPreDetailService.getCountBySerialNo(req);
        model.put("count", count);
        if (count > 0) {
            model.put("dataGrid", bsTicketPreDetailService.getBsUserBySerialNo(req));
        }
        return "ticket/grantManagement/manual_interest_ticket_review";
    }

    /**
     * 进入加息券审核页
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/interestTicketCheck/index")
    public String interestTicketCheckIndex(BsTicketGrantPlanCheckVO req, Map<String, Object> model) {
        // 审核状态:待审核排列前面
        req.setOrderByClause("IF(a.check_status = 'UNCHECKED', 1,2) ASC, a.id DESC");
        model.put("count", ticketGrantplanCheckService.getGrantManagermentCount(req));
        model.put("dataGrid", ticketGrantplanCheckService.getGrantManagerment(req));
        model.put("req", req);
        return "ticket/ticketCheck/check_index";
    }


    /**
     * 进入加息券审核页面(自动发放加息券)
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/autoInterestTicketCheck/review")
    public String autoInterestTicketCheckReview(BsInterestTicketGrantAttrVO req, Map<String, Object> model) {
        BsInterestTicketGrantAttrVO grantDetail = ticketGrantplanCheckService.getAutoTicketGrantDetail(req);
        grantDetail.setAgentIdsDesc(getAllAgentName(grantDetail.getAgentIds()));
        model.put("interestTicket", grantDetail);
        return "ticket/ticketCheck/auto_interest_ticket_review";
    }

    /**
     * 组装渠道名称
     *
     * @param agentIds
     * @return
     */
    private String getAllAgentName(String agentIds) {
        String agentIdsDesc = "";
        //存在-1
        if (agentIds.indexOf("-1") >= 0) {
            agentIdsDesc = "全部渠道";
        } else {
            List agentIdList = ArrayUtils.split2Integer(agentIds);
            if (agentIdList.contains(0)) {
                agentIdsDesc = "普通渠道";
                agentIdList.remove(0);
            }
            if (CollectionUtils.isNotEmpty(agentIdList)) {
                if (StringUtils.isNotBlank(agentIdsDesc)) {
                    agentIdsDesc += ",";
                }
                agentIdsDesc += agentService.findAllAgentName(agentIdList);
            }
        }
        return agentIdsDesc;
    }

    /**
     * 进入加息券审核页面(手动发放加息券)
     *
     * @param req
     * @param model
     * @return
     */
    @RequestMapping("/manualInterestTicketCheck/review")
    public String manualInterestTicketCheckReview(BsInterestTicketGrantAttrVO grantAttrVO, BsTicketPreDetailVO req, Map<String, Object> model) {
        BsInterestTicketGrantAttrVO attrVO = ticketGrantplanCheckService.getManualTicketGrantDetail(grantAttrVO);
        model.put("interestTicket", attrVO);
        model.put("req", req);
        req.setSerialNoSearch(attrVO.getSerialNo());
        int count = bsTicketPreDetailService.getCountBySerialNo(req);
        model.put("count", count);
        if (count > 0) {
            model.put("dataGrid", bsTicketPreDetailService.getBsUserBySerialNo(req));
        }
        return "ticket/ticketCheck/manual_interest_ticket_review";
    }

    /**
     * 加息券发放审核通过操作
     *
     * @param checkId
     * @param request
     * @return
     */
    @RequestMapping("/interestTicketCheckPass")
    public @ResponseBody
    Map<Object, Object> interestTicketCheckPass(@RequestParam Integer checkId, HttpServletRequest request) {
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }
        try {
            ticketGrantplanCheckService.checkTicketGrantPlan(checkId, true, Integer.parseInt(mUserId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("通过操作成功！");
    }

    /**
     * 加息券发放审核拒绝操作
     *
     * @param checkId
     * @param request
     * @return
     */
    @RequestMapping("/interestTicketCheckRefuse")
    public @ResponseBody
    Map<Object, Object> interestTicketCheckRefuse(@RequestParam Integer checkId, HttpServletRequest request) {
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }

        try {
            ticketGrantplanCheckService.checkTicketGrantPlan(checkId, false, Integer.parseInt(mUserId));
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("拒绝操作成功！");
    }

    /**
     * 发放手动加息券
     *
     * @param checkIdStr
     */
    @RequestMapping("/manualTickectSend")
    @ResponseBody
    public Map<Object, Object> manualTickectSend(@RequestParam String checkIdStr) {
        try {
            ticketGrantplanCheckService.manualInterestTicketSend(checkIdStr);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("发放加息券成功！");
    }

    /**
     * 进入自动类型加息券新增页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/addAutoTicket/index")
    public String addAutoTicketIndex(Integer checkId, boolean isClone, Map<String, Object> model) {

        model.put("time", DateUtil.formatYYYYMMDD(new Date()));

        if (isClone && checkId != null) {
            // 克隆自动加息券
            BsInterestTicketGrantAttrVO grantDetail = new BsInterestTicketGrantAttrVO();
            grantDetail.setCheckId(checkId);
            model.put("interestTicket", ticketGrantplanCheckService.getAutoTicketGrantDetail(grantDetail));

            model.put("allAgents", agentService.findAllAgentList());
        }

        return "ticket/autoTicket/auto_interest_ticket_index";
    }

    /**
     * 所有渠道
     *
     * @param model
     * @return
     */
    @RequestMapping("/autoTicket/agent")
    public String getAgent(Map<String, Object> model) {
        model.put("agentList", agentService.findAllAgentList());
        return "ticket/autoTicket/agentList";
    }

    /**
     * 新增自动加息券
     *
     * @param req
     */
    @RequestMapping("/autoInterestTicket/save")
    @ResponseBody
    public Map<Object, Object> save(BsInterestTicketGrantAttrVO req,
                                    HttpServletRequest request) {
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }
        if (StringUtils.isBlank(req.getSerialName())) {
            return ReturnDWZAjax.fail("加息券名称不能为空");
        }
        if (req.getSerialName().length() > 12) {
            return ReturnDWZAjax.fail("加息券名称长度超过12位");
        }
        if (req.getTicketApr() == null) {
            return ReturnDWZAjax.fail("加息幅度不能为空");
        }
        if (req.getTicketApr().compareTo(0d) <= 0 || req.getTicketApr().compareTo(100d) >= 0) {
            return ReturnDWZAjax.fail("加息幅度必须大于0，且小于100%");
        }
        if (req.getGrantTotal() == null) {
            return ReturnDWZAjax.fail("加息券数量不能为空");
        }
        if (req.getInvestLimit() == null) {
            return ReturnDWZAjax.fail("加息券使用条件不能为空");
        }
        if (StringUtils.isBlank(req.getProductLimit())) {
            return ReturnDWZAjax.fail("产品限制不能为空");
        }
        if (StringUtils.isBlank(req.getTermLimit())) {
            return ReturnDWZAjax.fail("期限限制不能为空");
        }
        if (StringUtils.isBlank(req.getAgentIds())) {
            return ReturnDWZAjax.fail("请选择渠道");
        }

        Date triggerTimeStart = null;
        Date triggerTimeEnd = null;
        if (com.pinting.business.util.Constants.TICKET_TRIGGER_TYPE_HAPPY_BIRTHDAY.equals(req.getTriggerType())||
        		com.pinting.business.util.Constants.TICKET_TRIGGER_TYPE_EXCHANGE_4MALL.equals(req.getTriggerType())
			|| com.pinting.business.util.Constants.TICKET_TRIGGER_TYPE_WECHAT_MINI_PROGRAM.equals(req.getTriggerType())) {
            if (StringUtils.isBlank(req.getTriggerTimeStart()) || StringUtils.isBlank(req.getTriggerTimeEnd())) {
                return ReturnDWZAjax.fail("触发时间不能为空");
            }
            triggerTimeStart = DateUtil.parseDateTime(req.getTriggerTimeStart());
            triggerTimeEnd = DateUtil.parseDateTime(req.getTriggerTimeEnd());
            if (triggerTimeStart.after(triggerTimeEnd)) {
                return ReturnDWZAjax.fail("触发开始时间必须早于结束时间");
            }
        } else {
            return ReturnDWZAjax.fail("加息券触发类型错误");
        }
        if (com.pinting.business.util.Constants.AUTO_TICKET_VALID_TERM_TYPE_FIXED.equals(req.getValidTermType())) {
            if (StringUtils.isBlank(req.getUseTimeStart()) || StringUtils.isBlank(req.getUseTimeEnd())) {
                return ReturnDWZAjax.fail("有效时间不能为空");
            }

            Date useTimeStart = DateUtil.parseDateTime(req.getUseTimeStart());
            Date useTimeEnd = DateUtil.parseDateTime(req.getUseTimeEnd());

            if (useTimeStart.before(com.pinting.business.util.DateUtil.getDateBegin(new Date()))) {
                return ReturnDWZAjax.fail("有效期开始时间不能早于今天");
            }
            if (useTimeStart.after(useTimeEnd)) {
                return ReturnDWZAjax.fail("有效开始时间必须早于结束时间");
            }
            if (useTimeEnd.before(triggerTimeEnd)) {
                return ReturnDWZAjax.fail("有效结束时间必须晚于触发结束时间");
            }
        } else if (com.pinting.business.util.Constants.AUTO_TICKET_VALID_TERM_TYPE_AFTER_RECEIVE.equals(req.getValidTermType())) {
            if (req.getAvailableDays() == null) {
                return ReturnDWZAjax.fail("加息券有效天数不能为空");
            }
            if (req.getAvailableDays().compareTo(0) <= 0) {
                return ReturnDWZAjax.fail("加息券有效天数必须大于0");
            }
        } else {
            return ReturnDWZAjax.fail("有效期类型错误");
        }
        try {
            req.setApplicant(Integer.parseInt(mUserId));
            ticketGrantplanCheckService.addAutoTicketGrantPlan(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }

        return ReturnDWZAjax.success("添加成功！");
    }

    /**
     * 进入userId手动发放加息券页面
     *
     * @param model
     * @return
     */
    @RequestMapping("/addManualTicket/userId/index")
    public String userIdIndex(Integer checkId, boolean isClone, Map<String, Object> model) {
        model.put("totalRows", 0);

        if (isClone && checkId != null) {
            // 克隆手动加息券
            model.put("checkId", checkId);
        }

        return "ticket/manualTicket/user_id_interest_ticket_index";
    }

    /**
     * userid用户信息查询
     *
     * @return
     */
    @RequestMapping("/userId/userSearch")
    public String userIdSearch(BsTicketInterestManualVO req, Map<String, Object> model) {
        if (req.getPageNum() == null || req.getNumPerPage() == null) {
            req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
            req.setNumPerPage(Constants.MANAGE_DEFAULT_NUMPERPAGE);
        }

        String errorMsg = "";

        if (StringUtils.isBlank(req.getUserIds())) {
            errorMsg = "加息券发放用户不能为空";
        }

        if (!checkUserIds(StringUtil.trimStr(req.getUserIds()))) {
            errorMsg = "用户编号格式错误";
        }

        if (StringUtils.isBlank(errorMsg)) {
            int totalRows = 0;
            req.setReceiveNum(ArrayUtils.split2Str(req.getUserIds()).length);
            List<Integer> userIds = ArrayUtils.split2Integer(req.getUserIds());
            if (CollectionUtils.isNotEmpty(userIds)) {
                totalRows = bsUserService.findCountUserByIds(userIds);
                if (totalRows > 0) {
                    req.setUserOperateList(bsUserService.findUserByIds(userIds, req.getPageNum(), req.getNumPerPage()));
                }
            }
            req.setTotalRows(totalRows);
        }

        model.put("req", req);
        model.put("checkId", req.getCheckId());
        model.put("totalRows", req.getTotalRows());
        model.put("receiveNum", req.getReceiveNum());
        model.put("operateList", req.getUserOperateList());
        model.put("errorMsg", errorMsg);
        return "ticket/manualTicket/user_id_interest_ticket_index";
    }

    /**
     * 弹出userid加息券申请发放页面
     *
     * @param checkId
     * @return
     */
    @RequestMapping("/addManualTicket/userId/interestTicketApply")
    public String userIdInterestTicketApply(Integer userCount, Integer checkId, Map<String, Object> model) {
        model.put("totalRows", userCount);
        model.put("time", DateUtil.formatYYYYMMDD(new Date()));
        if (checkId != null) {
            // 克隆手动加息券
            BsInterestTicketGrantAttrVO grantDetail = new BsInterestTicketGrantAttrVO();
            grantDetail.setCheckId(checkId);
            model.put("interestTicket", ticketGrantplanCheckService.getManualTicketGrantDetail(grantDetail));
        }
        return "ticket/manualTicket/user_id_interest_ticket_apply";
    }

    /**
     * 手工加息券发放计划申请
     *
     * @param request
     * @return
     */
    @RequestMapping("/addManualTicket/interestTicket/apply")
    @ResponseBody
    public Map<Object, Object> applyManualInterestTicket(BsInterestTicketGrantAttrVO req, HttpServletRequest request) {
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        if (StringUtils.isBlank(mUserId)) {
            return ReturnDWZAjax.fail("未获取到登录者信息");
        }

        if (StringUtils.isBlank(req.getSerialName())) {
            return ReturnDWZAjax.fail("加息券名称不能为空");
        }
        if (req.getSerialName().length() > 12) {
            return ReturnDWZAjax.fail("加息券名称长度超过12位");
        }
        if (req.getTicketApr() == null) {
            return ReturnDWZAjax.fail("加息幅度不能为空");
        }
        if (req.getTicketApr().compareTo(0d) <= 0 || req.getTicketApr().compareTo(100d) >= 0) {
            return ReturnDWZAjax.fail("加息幅度必须大于0，且小于100%");
        }
        if (req.getGrantTotal() == null) {
            return ReturnDWZAjax.fail("加息券数量不能为空");
        }
        if (StringUtils.isBlank(req.getUseTimeStart()) || StringUtils.isBlank(req.getUseTimeEnd())) {
            return ReturnDWZAjax.fail("有效时间不能为空");
        }
        Date useTimeStart = DateUtil.parseDateTime(req.getUseTimeStart());
        Date useTimeEnd = DateUtil.parseDateTime(req.getUseTimeEnd());

        if (useTimeStart.before(com.pinting.business.util.DateUtil.getDateBegin(new Date()))) {
            return ReturnDWZAjax.fail("有效期开始时间不能早于今天");
        }
        if (useTimeStart.after(useTimeEnd)) {
            return ReturnDWZAjax.fail("有效开始时间必须早于结束时间");
        }
        if (req.getInvestLimit() == null) {
            return ReturnDWZAjax.fail("加息券使用条件不能为空");
        }
        if (StringUtils.isBlank(req.getProductLimit())) {
            return ReturnDWZAjax.fail("产品限制不能为空");
        }
        if (StringUtils.isBlank(req.getTermLimit())) {
            return ReturnDWZAjax.fail("期限限制不能为空");
        }
        if (StringUtils.isBlank(req.getUserIds())) {
            return ReturnDWZAjax.fail("加息券发放用户不能为空");
        }

        if (!checkUserIds(StringUtil.trimStr(req.getUserIds()))) {
            return ReturnDWZAjax.fail("用户编号格式错误");
        }

        try {
            req.setApplicant(Integer.valueOf(mUserId));
            ticketGrantplanCheckService.addManualTicketGrantPlan(req);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail(e.getMessage());
        }
        return ReturnDWZAjax.success("添加成功！");
    }

    /**
     * 查询触发时间范围内，过生日的用户数量
     *
     * @param triggerTimeStart
     * @param triggerTimeEnd
     * @param model
     * @return
     */
    @RequestMapping("/userBirthday/count")
    @ResponseBody
    public Map<Object, Object> findCountBirthdayByDate(@RequestParam Date triggerTimeStart, @RequestParam Date triggerTimeEnd, Map<Object, Object> model) {
        model.put("countBirthday", bsUserService.findCountBirthdayByDate(triggerTimeStart, triggerTimeEnd));
        return ReturnDWZAjax.success("操作成功！", model);
    }

    /*
     * 判断userIDs格式正确, userId拼接以，分割
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    private boolean checkUserIds(String str) {
        Pattern pattern = Pattern.compile("^[\\,\\，0-9]*$");
        Matcher check = pattern.matcher(str);
        if (!check.matches()) {
            return false;
        }
        return true;
    }

    /**
     * 检测输入的人数是否和查询人数一致
     *
     * @return
     */
    @RequestMapping("/checkUserIdCount")
    @ResponseBody
    public Map<Object, Object> checkUserIdCount(String userIdStr) {
        if (StringUtils.isBlank(userIdStr)) {
            return ReturnDWZAjax.fail("加息券发放用户不能为空");
        }


        userIdStr = StringUtil.trimStr(userIdStr);
        if (!checkUserIds(userIdStr)) {
            return ReturnDWZAjax.fail("用户编号格式错误");
        }

        int totalRows = 0;

        int receiveNum = ArrayUtils.split2Str(userIdStr).length;
        List<Integer> userIds = ArrayUtils.split2Integer(userIdStr);
        if (CollectionUtils.isNotEmpty(userIds)) {
            totalRows = bsUserService.findCountUserByIds(userIds);
            if (totalRows <= 0) {
                return ReturnDWZAjax.fail("无有效用户编号");
            }
        }
        Map<Object, Object> map = new HashMap<>();
        map.put("receiveNum", receiveNum);
        map.put("realityNum", totalRows);
        return ReturnDWZAjax.success("查询成功", map);
    }
}
