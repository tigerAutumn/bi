package com.pinting.manage.controller;

import com.pinting.business.dao.BsUserPoliceVerifyMapper;
import com.pinting.business.enums.EvaluateTypeEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.PoliceVerifyBusinessTypeEnum;
import com.pinting.business.enums.PoliceVerifyCheckStatusEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsChangeBindCardVO;
import com.pinting.business.model.vo.BsVerifyBindCardResVO;
import com.pinting.business.model.vo.UpdateHFUserApplyVO;
import com.pinting.business.service.manage.*;
import com.pinting.business.util.AddressUtil;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Controller
public class BsUserController {
    @Autowired
    @Qualifier("dispatchService")
    private HessianService manageService;
    @Autowired
    private AgentService agentService;
    @Autowired
    private BsTagService bsTagService;
    @Autowired
    private BsUserService userService;
    @Autowired
    private HFUserActionService hfUserActionService;
    @Autowired
   	private BsQuestionnaireQuotaService bsQuestionnaireQuotaService;
    @Autowired
    private BsUserPoliceVerifyMapper bsUserPoliceVerifyMapper;
    @Autowired
	private MUserService mUserService;
    
    private Logger log= LoggerFactory.getLogger(BsUserController.class);

    /**
     * 打开注册用户管理界面
     */
    @RequestMapping("/bsuser/index")
    public String registeredUserInit(ReqMsg_BsUser_BsUserListQuery req,
                                     HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model) {
        Integer pageNum = request.getParameter("pageNum") == null ? 0 : Integer.parseInt(request.getParameter("pageNum"));
        Integer numPerPage = request.getParameter("numPerPage") == null ? 0 : Integer.parseInt(request.getParameter("numPerPage"));
        Integer scene= request.getParameter("scene") == null ? null : Integer.parseInt(request.getParameter("scene"));

        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_100_NUMPERPAGE;
        }

        if (scene != null) {
            ResMsg_BsUser_BsUserListQuery resp = (ResMsg_BsUser_BsUserListQuery) manageService
                    .handleMsg(req);
            model.put("users", resp.getBsUserList());
            model.put("totalRows", resp.getTotalRows());
        }

        int agentTotal = agentService.findAgentsTotalRows();
        model.put("agentTotal", agentTotal);
        model.put("req", req);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        if (req.getOrderField() != null && req.getOrderDirection() != null) {
            model.put(req.getOrderField(), req.getOrderDirection());
        }
        return "/bsuser/bsuser_index";
    }

    
    @RequestMapping("/bsuser/change")
    public String userChangeInit(ReqMsg_BsUser_BsUserListQuery req,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        if (req.getOrderField() != null && req.getOrderDirection() != null) {
            model.put(req.getOrderField(), req.getOrderDirection());
            /*req.setOrderDirection("");
			req.setOrderField("");*/
        }
        ResMsg_BsUser_BsUserListQuery resp = (ResMsg_BsUser_BsUserListQuery) manageService
                .handleMsg(req);
        int agentTotal = agentService.findAgentsTotalRows();
        model.put("agentTotal", agentTotal);
        model.put("users", resp.getBsUserList());
        model.put("totalRows", resp.getTotalRows());
        model.put("req", req);
        return "/bsUserChannel/bsuser_change";
    }

    /**
     * 下限用户管理
     */
    @RequestMapping("/bsuser/subUser")
    public String registeredSubUserInit(ReqMsg_BsUser_BsSubUserListQuery req,
                                        HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        ResMsg_BsUser_BsSubUserListQuery resp = (ResMsg_BsUser_BsSubUserListQuery) manageService
                .handleMsg(req);
        model.put("recommendId", req.getUserId());
        model.put("users", resp.getBsUserList());
        model.put("pageNum", resp.getPageNum());
        model.put("numPerPage", resp.getNumPerPage());
        model.put("totalRows", resp.getTotalRows());

        return "/bsuser/bsuser_subIndex";
    }

    /**
     * 打开注册用户界面界面
     */
    @RequestMapping("/bsuser/detail")
    public String registeredUserDetailInit(ReqMsg_BsUser_BsUserQuery req,
                                           HttpServletRequest request, HttpServletResponse response,
                                           Map<String, Object> model, String op) {
        ResMsg_BsUser_BsUserQuery resp = null;
        if (req.getUserId() != null && req.getUserId() > 0) {
            resp = (ResMsg_BsUser_BsUserQuery) manageService.handleMsg(req);
            model.put("user", resp);
            model.put("bankcards", resp.getUserBankCardList());
            if (op != null) {
                model.put("op", "none");
            }
        }
        return "/bsuser/detail";
    }

    /**
     * 保存用户
     */
    @RequestMapping("/bsuser/save")
    @ResponseBody
    public Map<Object, Object> saveUserDetailSubmit(
            ReqMsg_BsUser_BsUserSave req, HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> model) {
        CookieManager cookie = new CookieManager(request);
        String controlName = cookie.getValue(
                CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_NAME.getName(), true);
        req.setControlName(controlName);
        ResMsg_BsUser_BsUserSave resp = (ResMsg_BsUser_BsUserSave) manageService
                .handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            return ReturnDWZAjax.success("修改成功！");
        } else {
            return ReturnDWZAjax.success(resp.getResMsg());
        }
    }

    /**
     * 注销禁用用户
     */
    @RequestMapping("/bsuser/modifystatus")
    @ResponseBody
    public Map<Object, Object> deleteUserDetailSubmit(
            ReqMsg_BsUser_BsUserStatusModify req, HttpServletRequest request,
            HttpServletResponse response, Map<String, Object> model) {
        CookieManager cookie = new CookieManager(request);
        String controlName = cookie.getValue(
                CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_NAME.getName(), true);
        req.setControlName(controlName);
        ResMsg_BsUser_BsUserStatusModify resp = (ResMsg_BsUser_BsUserStatusModify) manageService
                .handleMsg(req);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
            return ReturnDWZAjax.success("操作成功！");
        } else {
            return ReturnDWZAjax.success(resp.getResMsg());
        }
    }

    @RequestMapping("/bsuser/feedback/index")
    public String registeredUserFeedback(ReqMsg_BsUser_FeedbacksQuery reqMsg,
                                         HttpServletRequest request, HttpServletResponse response,
                                         Map<String, Object> model) {

        String pageNum = reqMsg.getPageNum();
        String numPerPage = reqMsg.getNumPerPage();
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
            reqMsg.setPageNum(pageNum);
            reqMsg.setNumPerPage(numPerPage);
        }
        String orderDirection = request.getParameter("orderDirection");
        String orderField = request.getParameter("orderField");
        if (orderDirection != null && orderField != null) {
            reqMsg.setOrderDirection(orderDirection);
            reqMsg.setOrderField(orderField);
            model.put("orderDirection", orderDirection);
            model.put("orderField", orderField);
        } else {
            reqMsg.setOrderDirection("desc");
            reqMsg.setOrderField("id"); // id倒序
            model.put("orderDirection", reqMsg.getOrderDirection());
            model.put("orderField", reqMsg.getOrderField());
        }
        ResMsg_BsUser_FeedbacksQuery resp = (ResMsg_BsUser_FeedbacksQuery) manageService
                .handleMsg(reqMsg);
        model.put("feedbackList", resp.getFeedbacks());
        model.put("pageNum", resp.getPageNum());
        model.put("numPerPage", resp.getNumPerPage());
        model.put("totalRows", resp.getTotalRows());
        model.put("orderDirection", orderDirection);
        model.put("orderField", orderField);
        if (reqMsg.getOrderField() != null) {
            model.put(reqMsg.getOrderField(), reqMsg.getOrderDirection());
            model.put("orderField", reqMsg.getOrderField());
            model.put("orderDirection", reqMsg.getOrderDirection());
        }

        return "/bsuser/feedback_index";
    }

    @RequestMapping("/bsuser/feedback/detail")
    public String registeredUserFeedbackDetail(String info,
                                               HttpServletRequest request, HttpServletResponse response,
                                               Map<String, Object> model) {
        model.put("info", info);
        return "/bsuser/feedback_detail";
    }

    /**
     * 用户综合查询
     *
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuserManage/index")
    public String userManageInit(ReqMsg_BsUser_BsUserComplexListQuery req,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        ResMsg_BsUser_BsUserComplexListQuery resp = (ResMsg_BsUser_BsUserComplexListQuery) manageService.handleMsg(req);

        int agentTotal = agentService.findAgentsTotalRows();
        model.put("agentTotal", agentTotal);
        model.put("users", resp.getBsUserList());
        model.put("totalRows", resp.getTotalRows());
        model.put("req", req);
        if (req.getOrderField() != null) {
            model.put(req.getOrderField(), req.getOrderDirection());
            model.put("orderField", req.getOrderField());
            model.put("orderDirection", req.getOrderDirection());
        }
        return "/bsuser/bsuser_manage";
    }
    
    /**
     * 用户注册查询
     *
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuserRegister/index")
    public String userRegisterInit(ReqMsg_BsUser_BsUserComplexListQuery req,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        if (req.getSregistTime() == null || "".equals(req.getSregistTime())) {
        	Date sregisterDate = DateUtil.addDays(new Date(), -30);
        	String startDay = com.pinting.business.util.DateUtil.format(sregisterDate, "yyyy-MM-dd")+" 00:00:00";
			req.setSregistTime(DateUtil.parse(startDay, "yyyy-MM-dd HH:mm:ss"));
		}
        if (req.getEregistTime() == null || "".equals(req.getEregistTime())) {
        	String endDay = com.pinting.business.util.DateUtil.format(new Date(), "yyyy-MM-dd")+" 00:00:00";
			req.setEregistTime(DateUtil.parse(endDay, "yyyy-MM-dd HH:mm:ss"));
		}
        ResMsg_BsUser_BsUserComplexListQuery resp = (ResMsg_BsUser_BsUserComplexListQuery) manageService.handleMsg(req);

        int agentTotal = agentService.findAgentsTotalRows();
        model.put("agentTotal", agentTotal);
        model.put("users", resp.getBsUserList());
        model.put("totalRows", resp.getTotalRows());
        model.put("req", req);
        if (req.getOrderField() != null) {
            model.put(req.getOrderField(), req.getOrderDirection());
            model.put("orderField", req.getOrderField());
            model.put("orderDirection", req.getOrderDirection());
        }
        return "/bsuser/bsuser_register";
    }
    
    /**
     * 用户注册查询导出excel
     *
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuserRegister/exportXls")
    public void bsuserExportXls(ReqMsg_BsUser_BsUserComplexListQuery req,
                                 HttpServletRequest request, HttpServletResponse response,
                                 Map<String, Object> model) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("姓名");
        titles.add("手机号");
        titles.add("渠道来源");
        titles.add("注册日期");
        titles.add("投资次数");
        titles.add("账户余额");
        titleMap.put("title", titles);
        list.add(titleMap);

        req.setPageNum(1);
        req.setNumPerPage(req.getTotalRows());
        ResMsg_BsUser_BsUserComplexListQuery resp = (ResMsg_BsUser_BsUserComplexListQuery) manageService
                .handleMsg(req);
        List<HashMap<String, Object>> datas = resp.getBsUserList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();

                obj.add(data.get("name"));
                obj.add(data.get("mobile"));
                obj.add(data.get("agentName"));
                obj.add(data.get("registTime"));
                obj.add(data.get("investmentTimes"));
                obj.add(data.get("accountBalance"));

                datasMap.put("user" + (++i), obj);

                list.add(datasMap);
            }
        }

        try {
            ExportUtil.exportExcel(response, request, "用户管理", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    /**
     * 回访记录查看
     */
    @RequestMapping("/bsuser/checkRecord")
    public String userManageCheckRecord(ReqMsg_BsUser_BsUserRecordListQuery req,
                                        HttpServletRequest request, HttpServletResponse response,
                                        Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        ResMsg_BsUser_BsUserRecordListQuery resp = (ResMsg_BsUser_BsUserRecordListQuery) manageService
                .handleMsg(req);
        model.put("record", resp.getRecordList());
        model.put("userId", req.getUserId());
        model.put("pageNum", resp.getPageNum());
        model.put("numPerPage", resp.getNumPerPage());
        model.put("totalRows", resp.getTotalRows());

        return "/bsuser/bsuser_check_record";
    }

    /**
	 * 回访记录新增
	 */
	@RequestMapping("/bsuser/addRecord")
	public String addDetail(ReqMsg_BsUser_BsUserAddRecord req, HttpServletRequest request, Map<String, Object> model) {
		model.put("userId", req.getUserId());
		return "/bsuser/bsuser_add_record";
	}
	
	/**
	 * 回访记录新增保存
	 */
	@RequestMapping("/bsuser/saveRecord")
	public @ResponseBody Map<Object, Object> saveRecord(ReqMsg_BsUser_BsUserSaveRecord req, HttpServletRequest request, Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
        String submitter = cookie.getValue(
                CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_NAME.getName(), true);
		req.setSubmitTime(new Date());
		req.setSubmitter(submitter);
		ResMsg_BsUser_BsUserSaveRecord res = (ResMsg_BsUser_BsUserSaveRecord) manageService.handleMsg(req);
		if (Constants.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("新增回访记录成功！");   				
		} else {
			return ReturnDWZAjax.fail(res.getResMsg());		
		}
	}
    
    /**
     * 用户标签管理
     *
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/tagManage")
    public String userTagInit(ReqMsg_BsUser_BsUserTagListQuery req,
                              HttpServletRequest request, HttpServletResponse response,
                              Map<String, Object> model) {
        Integer pageNum = req.getPageNum() == null ? 0 : req.getPageNum();
        Integer numPerPage = req.getNumPerPage() == null ? 0 : req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_100_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        String tagIdsForQuery = request.getParameter("tagIdsForQuery");
        List<Integer> tagIdList = new ArrayList<Integer>();
        if (!StringUtil.isBlank(tagIdsForQuery)) {
            String[] tagIds = tagIdsForQuery.split(",");
            for (String tagId : tagIds) {
                tagIdList.add(Integer.parseInt(tagId));
            }
        }
        req.setTagIdList(tagIdList);
        ResMsg_BsUser_BsUserTagListQuery resp = (ResMsg_BsUser_BsUserTagListQuery) manageService
                .handleMsg(req);
        int agentTotal = agentService.findAgentsTotalRows();
        model.put("users", resp.getBsUserList());
        model.put("totalRows", resp.getTotalRows());

        model.put("req", req);
        model.put("agentTotal", agentTotal);

        model.put("tagIdsForQuery", tagIdsForQuery);

        int tagIdListTotal = bsTagService.findTagsTotal();
        model.put("tagIdListTotal", tagIdListTotal);
        model.put("buyBankTypeLists", resp.getBuyBankTypeList());
        if (req.getOrderField() != null && req.getOrderDirection() != null) {
            model.put(req.getOrderField(), req.getOrderDirection());
        }
        return "/bsuser/tag_manage";
    }

    /**
     * 进入user_id添加标签页面
     *
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/userIdTags")
    public String userIdIndex(ReqMsg_BsUser_BsUserAddTagsQuery req,
                              HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        model.put("req", req);
        model.put("totalRows", 0);
        return "/bsuser/user_id_tags";
    }

    /**
     * 根据userid信息查询
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/bsuser/userIdSearch")
    public String userIdSearch(ReqMsg_BsUser_BsUserAddTagsQuery req, HttpServletRequest request,
                               HttpServletResponse response, Map<String, Object> model) {
        if (req.getPageNum() <= 0 || req.getNumPerPage() <= 0) {
            req.setPageNum(Constants.MANAGE_DEFAULT_PAGENUM);
            req.setNumPerPage(Constants.MANAGE_100_NUMPERPAGE);
        }
        ResMsg_BsUser_BsUserAddTagsQuery res = (ResMsg_BsUser_BsUserAddTagsQuery) manageService.handleMsg(req);
        // 标签总数
        int tagTotal = bsTagService.findAllTagList().size();
        model.put("tagTotal", tagTotal);
        model.put("req", req);
        model.put("totalRows", res.getTotalRows());
        model.put("tagList", res.getBsUserList());
        return "/bsuser/user_id_tags";
    }

    /**
     * 用户复投查询
     *
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/complexVote")
    public String userComplexVoteInit(ReqMsg_BsUser_BsUserComplexVoteListQuery req, HttpServletRequest request, Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_100_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        if (req.getOrderField() != null && req.getOrderDirection() != null) {
            model.put(req.getOrderField(), req.getOrderDirection());
            model.put("orderDirection", req.getOrderField());
            model.put("orderField", req.getOrderDirection());
        }
        if (req.getQueryDateFlag() != null && req.getQueryDateFlag().equals("QUERYDATE")) {
            ResMsg_BsUser_BsUserComplexVoteListQuery res = (ResMsg_BsUser_BsUserComplexVoteListQuery) manageService.handleMsg(req);
            model.put("totalRows", res.getTotalRows());
            model.put("complexVoteList", res.getValueList());
        }
        model.put("req", req);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/bsuser/bsuser_complexVote";
    }


    /**
     * 导出用户信息excel
     *
     * @param response
     * @param request
     */
    @RequestMapping("/userMessage/exportXls")
    public void exportXls(ReqMsg_BsUser_BsUserListQuery req, HttpServletResponse response,
                          HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("用户ID");
        titles.add("手机号");
        titles.add("姓名");
        titles.add("身份证");
        titles.add("性别");
        titles.add("年龄");
        titles.add("银行名称");
        titles.add("银行卡号");
        titles.add("绑卡状态");
        titles.add("总资产");
        titles.add("账户余额");
        titles.add("当前投资本金");
        titles.add("累计投资本金");
        titles.add("累计投资收益");
        titles.add("累计推荐奖励");
        titles.add("注册日期");
        titles.add("首次购买日期");
        titles.add("推荐人");
        titles.add("渠道来源");
        titles.add("账户状态");
        titleMap.put("title", titles);
        list.add(titleMap);

        req.setPageNum(1);
        req.setNumPerPage(req.getTotalRows());
        ResMsg_BsUser_BsUserListQuery resp = (ResMsg_BsUser_BsUserListQuery) manageService
                .handleMsg(req);
        List<HashMap<String, Object>> datas = resp.getBsUserList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();

                obj.add(data.get("id"));
                obj.add(data.get("mobile"));
                obj.add(data.get("userName"));
                obj.add(data.get("idCard"));
                obj.add(data.get("gender"));
                obj.add(data.get("age").toString().equals("0") ? "" : data.get("age"));
                obj.add(data.get("bankName"));
                obj.add(data.get("cardNo"));
                obj.add(getBankStatus(data.get("bankStatus")));
                obj.add(data.get("sumBalance"));
                obj.add(data.get("totalBalance"));
                obj.add(data.get("currentBalance"));
                obj.add(data.get("totalPrincipal"));
                obj.add(data.get("totalInterest"));
                obj.add(data.get("totalBonus"));
                obj.add(data.get("registerTime"));
                obj.add(data.get("firstBuyTime"));
                obj.add(data.get("recommendName"));
                obj.add(data.get("agentName"));
                obj.add(convertUserStatus(data.get("status")));
                datasMap.put("user" + (++i), obj);

                list.add(datasMap);
            }
        }

        try {
            ExportUtil.exportExcel(response, request, "用户管理", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户标签管理 导出用户userId
     *
     * @param req
     * @param response
     * @param request
     */
    @RequestMapping("/userIdOfTag/exportXls")
    public void exportXls(ReqMsg_BsUser_BsUserTagListQuery req, HttpServletResponse response,
                          HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        String tagIdsForQuery = request.getParameter("tagIdsForQuery"); // tagIdsForQuery
        List<Integer> tagIdList = new ArrayList<Integer>();
        if (!StringUtil.isBlank(tagIdsForQuery)) {
            String[] tagIds = tagIdsForQuery.split(",");
            for (String tagId : tagIds) {
                tagIdList.add(Integer.parseInt(tagId));
            }
        }
        req.setTagIdList(tagIdList);
        titles.add("用户ID");
        titleMap.put("title", titles);
        list.add(titleMap);
        req.setPageNum(1);
        req.setNumPerPage(req.getTotalRows());
        ResMsg_BsUser_BsUserTagListQuery resp = (ResMsg_BsUser_BsUserTagListQuery) manageService
                .handleMsg(req);
        List<HashMap<String, Object>> datas = resp.getBsUserList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("userId"));
                datasMap.put("user" + (++i), obj);
                list.add(datasMap);
            }
        }

        try {
            ExportUtil.exportExcel(response, request, "用户标签管理", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //获取绑卡状态
    private static String getBankStatus(Object obj) {
        String statusStr = "未绑定";
        if (obj != null) {
            int status = (Integer) obj;
            if (status == 1) {
                statusStr = "正常";
            } else if (status == 2) {
                statusStr = "禁用";
            } else if (status == 3) {
                statusStr = "绑定中";
            } else if (status == 4) {
                statusStr = "绑定失败";
            } else if (status == 5) {
                statusStr = "已解绑";
            }
        }
        return statusStr;
    }

    //获取绑卡状态
    private static String convertUserStatus(Object obj) {
        String statusStr = "";
        if (obj != null) {
            int status = (Integer) obj;
            if (status == 1) {
                statusStr = "正常";
            } else if (status == 2) {
                statusStr = "注销";
            } else if (status == 3) {
                statusStr = "禁用";
            } else if (status == 4) {
                statusStr = "冻结";
            }
        }
        return statusStr;
    }
    
    /**
     * 导出用户信息excel(用户综合查询)
     *
     * @param response
     * @param request
     */
    @RequestMapping("/user/xls")
    public void xls(ReqMsg_BsUser_BsUserListQuery req, HttpServletResponse response,
                    HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        list.add(titles());
        req.setPageNum(0);
        req.setNumPerPage(req.getTotalRows());
        ResMsg_BsUser_BsUserListQuery resp = (ResMsg_BsUser_BsUserListQuery) manageService
                .handleMsg(req);
        List<HashMap<String, Object>> datas = resp.getBsUserList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("mobile") == null ? "" : custom(data.get("mobile").toString()));
                obj.add(data.get("name"));
                obj.add(data.get("idCard") == null ? "" : custom(data.get("idCard").toString()));
                obj.add(data.get("bankName"));
                obj.add(bindCardStatus(data.get("isBindBank")));
                obj.add(data.get("sumBalance"));
                obj.add(data.get("balance"));
                obj.add(data.get("currentBanlace"));
                obj.add(data.get("totalPrincipal"));
                obj.add(data.get("totalInterest"));
                obj.add(data.get("totalBonus"));
                obj.add(data.get("registTime"));
                obj.add(data.get("firstBuyTime"));
                obj.add(getUserStatus(data.get("status")));
                obj.add(data.get("recommendName"));
                obj.add(data.get("agentName"));
                datasMap.put("user" + (++i), obj);
                list.add(datasMap);
            }
        }

        try {
            ExportUtil.exportExcel(response, request, "用户信息", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Map<String, List<Object>> titles() {
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("手机号");
        titles.add("姓名");
        titles.add("身份证");
        titles.add("银行名称");
        titles.add("绑卡状态");
        titles.add("总资产");
        titles.add("账户余额");
        titles.add("当前投资本金");
        titles.add("累计投资本金");
        titles.add("累计投资收益");
        titles.add("累计推荐奖励");
        titles.add("注册日期");
        titles.add("首次购买日期");
        titles.add("状态");
        titles.add("推荐人");
        titles.add("渠道来源");
        titleMap.put("title", titles);
        return titleMap;
    }

    /**
     * 处理手机号码或者身份证号码
     *
     * @param number手机号码或身份证
     * @return
     */
    private static String custom(String number) {
        number = number.trim();
        if (number.length() == 11) {//手机号码
            String mobile = number.substring(3, 7);
            number = number.replaceAll(mobile, "****");
        } else if (number.length() == 18) {//18位身份证
            String idCard = number.substring(6, 14);
            number = number.replaceAll(idCard, "********");
        } else if (number.length() == 15) {//15位身份证
            String idCard = number.substring(6, 12);
            number = number.replaceAll(idCard, "******");
        }
        return number;
    }

    //获取银行卡的绑定状态
    private String bindCardStatus(Object obj1) {
        String status = "未绑定";
        if (obj1 != null) {
            if ("1".equals(obj1.toString())) {
                status = "已绑定";
            }
        }
        return status;
    }

    //获取用户状态
    private String getUserStatus(Object obj) {
        String status = "";
        if (obj != null) {
            if ("2".equals(obj.toString())) {
                status = "已经注销";
            } else if ("3".equals(obj.toString())) {
                status = "已经禁用";
            } else {
                status = "正在使用";
            }
        }
        return status;
    }

    /**
     * 客户信息查询
     *
     * @param reqMsg
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/query/index")
    public String bankCardInit(ReqMsg_BsUser_BsUserList reqMsg,
                               HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> model) {
        if (StringUtil.isEmpty(reqMsg.getMobile()) && StringUtil.isEmpty(reqMsg.getUserName())) {
            return "/bsuser/query_index";
        }
        String pageNum = reqMsg.getPageNum();
        String numPerPage = reqMsg.getNumPerPage();
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
            reqMsg.setPageNum(pageNum);
            reqMsg.setNumPerPage(numPerPage);
        }
        ResMsg_BsUser_BsUserList resp = (ResMsg_BsUser_BsUserList) manageService
                .handleMsg(reqMsg);
        model.put("userList", resp.getUserList());
        model.put("pageNum", resp.getPageNum());
        model.put("numPerPage", resp.getNumPerPage());
        model.put("totalRows", resp.getTotalRows());
        model.put("mobile", reqMsg.getMobile());
        model.put("userName", reqMsg.getUserName());
        return "/bsuser/query_index";
    }


    /**
     * 进入发送短信界面
     *
     * @return
     */
    @RequestMapping("/bsuser/sms/index")
    public String smsIndex() {
        return "/bsuser/sms_index";
    }

    /**
     * 下载模板
     *
     * @param response
     * @param request
     */
    @RequestMapping("/bsuser/xls")
    public void xls(HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("手机号（只填写此列）");
        titleMap.put("title", titles);
        list.add(titleMap);
        try {
            ExportUtil.exportExcel(response, request, "短信批量模板", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取电话号码
     *
     * @param fileName 文件地址
     * @return
     * @throws IOException
     */
    @RequestMapping("/bsuser/readExcel")
    public
    @ResponseBody
    HashMap<String, Object> readExcel(@RequestParam(value = "fileField", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        String fileMobiles = "";
        try {
            String[][] result = ExcelUtil.getDataInputStream(file.getInputStream(), 1, 0);
            if (result != null && result.length > 0) {
                for (int i = 0; i < result.length; i++) {
                    for (int j = 0; j < result[i].length; j++) {
                        if (j == 0) {
                            String str = StringUtil.left(result[i][j], 11); // 截取左边11位(手机号)
                            if (MobileCheckUtil.isMobile(str)) { // 验证手机号是否符合要求
                                fileMobiles = fileMobiles + str + ",";
                            }
                        }
                    }
                }
                fileMobiles = fileMobiles.substring(0, fileMobiles.length() - 1);
                // fileMobiles手机号数组;以","分割得到一个新数组,遍历改数组放到map的key中;避免手机号重复
                Map<String, Object> hashMap = new HashMap<String, Object>();
                String[] mobiles = fileMobiles.split(",");
                for (String mobile : mobiles) {
                    hashMap.put(mobile, mobile);
                }
                StringBuffer mobileList = new StringBuffer();
                for (String string : hashMap.keySet()) {
                    mobileList.append(string).append(",");
                }
                mobileList.deleteCharAt(mobileList.length() - 1);
                dataMap.put("mobile", mobileList.toString());
                dataMap.put("code", 1);
            } else {
                dataMap.put("code", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        return dataMap;
    }

    /**
     * 给用户发送营销信息-梦网
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/bsuser/sms")
    public
    @ResponseBody
    Map<Object, Object> smsUser(
            final ReqMsg_BsUser_SmsUser reqMsg,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) throws IOException {
        new Thread() {//防止一次发送太多短信超时，所以另起一个新的线程去专门发短信
            public void run() {
                manageService.handleMsg(reqMsg);
            }
        }.start();
        response.setContentType("text/html");
        return ReturnDWZAjax.success("操作完成！");
		/*ResMsg_BsUser_SmsUser resp = (ResMsg_BsUser_SmsUser) manageService
				.handleMsg(reqMsg);
		if(resp.getMsg().equals("全部发送成功！")){
			return ReturnDWZAjax.success(resp.getMsg());
		}else{
			return ReturnDWZAjax.toAjaxString("2", resp.getMsg());
		}*/
    }

    /**
     * 发送微网营销类短信
     *
     * @param reqMsg
     * @param request
     * @param response
     * @param model
     * @return
     * @throws IOException
     */
    @RequestMapping("/bsuser/sendWelinkMessage")
    public
    @ResponseBody
    Map<Object, Object> sendWelinkMessage(
            final ReqMsg_BsUser_SendWelinkMarkingMessage reqMsg,
            HttpServletRequest request, HttpServletResponse response,
            Map<String, Object> model) throws IOException {
        new Thread() {//防止一次发送太多短信超时，所以另起一个新的线程去专门发短信
            public void run() {
                manageService.handleMsg(reqMsg);
            }
        }.start();
        response.setContentType("text/html");
        return ReturnDWZAjax.success("操作完成！");
    }


    /**
     * 注册用户查询
     *
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/registerUserIndex")
    public String registerUserInit(ReqMsg_BsUser_BsUserRegisterListQuery req, HttpServletRequest request, Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        String orderDirection = request.getParameter("orderDirection");
        String orderField = request.getParameter("orderField");
        if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
            req.setOrderDirection("");
            req.setOrderField("");
            model.put("orderDirection", req.getOrderDirection());
            model.put("orderField", req.getOrderField());
        }
        if (req.getQueryFlag() != null && req.getQueryFlag().equals("QUERY")) {
            ResMsg_BsUser_BsUserRegisterListQuery res = (ResMsg_BsUser_BsUserRegisterListQuery) manageService.handleMsg(req);
            model.put("totalRows", res.getTotalRows());
            model.put("registerUserList", res.getValueList());
        }
        model.put("req", req);
        model.put("pageNum", req.getPageNum());
        model.put("numPerPage", req.getNumPerPage());
        return "/bsuser/registerUser_index";
    }


    /**
     * 用户编号-手机号互换
     *
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/id2Mobile")
    public String id2Mobile(HttpServletRequest request, Map<String, Object> model) {
        return "/bsuser/user_id_2_mobile";
    }

    /**
     * 检测输入的人数是否和查询人数一致
     *
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/bsuser/id2Mobile/checkIdOrMobileCount")
    @ResponseBody
    public Map<String, Object> checkIdOrMobileCount(HttpServletRequest request, HttpServletResponse response) {
        String changeType = request.getParameter("changeType").trim();
        String content = request.getParameter("content").trim();
        int receiveNum = 0;
        int realityNum = 0;
        if (StringUtil.isNotEmpty(content)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            content = content.replaceAll("[\\r\\n]", ",");
            String[] str = content.replace("，", ",").split(",");
            if (str.length > 0) {
                List<Object> objs = new ArrayList<Object>();
                for (String s : str) {
                    s = s.trim();
                    if (StringUtil.isNotEmpty(s)) {
                        receiveNum++;
                        Pattern pattern = Pattern.compile("^[0-9]*$");
                        Matcher matcher = pattern.matcher(s);
                        if (matcher.matches()) {
                            objs.add(s);
                        }
                    }
                }

                if (objs.size() > 0) {
                    if ("userId".equals(changeType)) {
                        paramMap.put("mobiles", objs);
                    } else {
                        paramMap.put("userIds", objs);
                    }

                    List<BsUser> datas = userService.findUsersByIdsOrMobiles(paramMap);
                    realityNum = datas.size();
                }
            }
        }

        Map<String, Object> map = new HashMap<String, Object>();
        map.put("receiveNum", receiveNum);
        map.put("realityNum", realityNum);
        return map;
    }

    /**
     * 用户编号-手机号互换导出excel
     *
     * @param req
     * @param response
     * @param request
     */
    @RequestMapping("/bsuser/id2Mobile/exportXls")
    public void idsOrMobilesExportXls(HttpServletResponse response, HttpServletRequest request) {
        String changeType = request.getParameter("changeType").trim();
        String content = request.getParameter("content").trim();
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        String excelName = "用户编号换手机号";
        if ("userId".equals(changeType)) {
            titles.add("用户ID");
            excelName = "手机号换用户编号";
        } else {
            titles.add("手机号");
        }
        titleMap.put("title", titles);
        list.add(titleMap);

        if (StringUtil.isNotEmpty(content)) {
            Map<String, Object> paramMap = new HashMap<String, Object>();
            content = content.replaceAll("[\\r\\n]", ",");
            String[] str = content.replace("，", ",").split(",");
            if (str.length > 0) {
                List<Object> objs = new ArrayList<Object>();
                for (String s : str) {
                    s = s.trim();
                    if (StringUtil.isNotEmpty(s)) {
                        Pattern pattern = Pattern.compile("^[0-9]*$");
                        Matcher matcher = pattern.matcher(s);
                        if (matcher.matches()) {
                            objs.add(s);
                        }
                    }
                }

                if (objs.size() > 0) {
                    if ("userId".equals(changeType)) {
                        paramMap.put("mobiles", objs);
                    } else {
                        paramMap.put("userIds", objs);
                    }

                    List<BsUser> datas = userService.findUsersByIdsOrMobiles(paramMap);
                    //设置导出excel内容
                    if (!CollectionUtils.isEmpty(datas)) {
                        int i = 0;
                        for (BsUser bsUser : datas) {
                            Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                            List<Object> obj = new ArrayList<Object>();
                            if ("userId".equals(changeType)) {
                                obj.add(bsUser.getId());
                            } else {
                                obj.add(bsUser.getMobile());
                            }
                            datasMap.put("user" + (++i), obj);
                            list.add(datasMap);
                        }

                    }
                }
            }
        }

        try {
            ExportUtil.exportExcel(response, request, excelName, list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户复投查询
     *
     * @param req
     * @param response
     * @param request
     */
    @RequestMapping("/userComplexVote/exportXls")
    public void complexVoteXls1(ReqMsg_BsUser_BsUserComplexVoteListQuery req, HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("用户ID");
        titles.add("回款金额");
        titles.add("投资金额");
        titles.add("投资次数");
        titleMap.put("title", titles);
        list.add(titleMap);
        req.setPageNum(1);
        if (req.getTotalRows() == null || "".equals(req.getTotalRows())) {
            req.setNumPerPage(0);
        } else {
            req.setNumPerPage(req.getTotalRows());
        }

        ResMsg_BsUser_BsUserComplexVoteListQuery res = (ResMsg_BsUser_BsUserComplexVoteListQuery) manageService.handleMsg(req);
        List<HashMap<String, Object>> datas = res.getValueList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("id"));
                obj.add(data.get("amountOfPayment"));
                obj.add(data.get("totalPrincipal"));
                obj.add(data.get("investmentTimes"));
                datasMap.put("user" + (++i), obj);
                list.add(datasMap);
            }
        }
        try {
            ExportUtil.exportExcel(response, request, "用户复投查询", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 用户利息回款查询
     * @param req
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/interestRepayment")
    public String userInterestRepaymentInit(ReqMsg_BsUser_BsUserInterestRepayment req, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        if (req.getOrderField() != null) {
            model.put(req.getOrderField(), req.getOrderDirection());
            model.put("orderField", req.getOrderField());
            model.put("orderDirection", req.getOrderDirection());
        }
        if (req.getQueryDateFlag() != null && req.getQueryDateFlag().equals("QUERYDATE")) {
            ResMsg_BsUser_BsUserInterestRepayment resp = (ResMsg_BsUser_BsUserInterestRepayment) manageService.handleMsg(req);
            model.put("dataList", resp.getValueList());
            model.put("totalRows", resp.getTotalRows());
        }
        model.put("req", req);
        return "/bsuser/bsuser_interestRepayment";
    }

    /**
     * 用户利息回款查询-导出excel
     * @param req
     * @param response
     * @param request
     */
    @RequestMapping("/bsuser/interestRepayment/exportXls")
    public void interestRepaymentXls(ReqMsg_BsUser_BsUserInterestRepayment req, HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("购买日期");
        titles.add("姓名");
        titles.add("手机号");
        titles.add("产品名称");
        titles.add("购买金额");
        titles.add("利率");
        titles.add("期限");
        titles.add("到期提现日期");
        titles.add("状态");
        titles.add("资产合作方");
        titles.add("利息");
        titleMap.put("title", titles);
        list.add(titleMap);
        req.setPageNum(1);
        if(req.getTotalRows() == null || "".equals(req.getTotalRows())) {
            req.setNumPerPage(0);
        }else {
            req.setNumPerPage(req.getTotalRows());
        }

        ResMsg_BsUser_BsUserInterestRepayment res = (ResMsg_BsUser_BsUserInterestRepayment) manageService.handleMsg(req);
        List<HashMap<String, Object>> datas = res.getValueList();
        //设置导出excel内容
        if (datas != null && !datas.isEmpty()) {
            int i = 0;
            for (HashMap<String, Object> data : datas) {
                Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
                List<Object> obj = new ArrayList<Object>();
                obj.add(data.get("openTime"));
                obj.add(data.get("userName"));
                obj.add(data.get("mobile"));
                obj.add(data.get("productName"));
                obj.add(data.get("balance"));
                obj.add(data.get("productRate"));
                obj.add(data.get("term"));
                obj.add(data.get("lastfinishInterestDate"));
                obj.add(getAccountStatus(data.get("accountStatus")));
                obj.add(getPropertySymbol(data.get("propertySymbol")));
                obj.add(data.get("interest"));
                datasMap.put("user" + (++i), obj);
                list.add(datasMap);
            }
        }
        try {
            ExportUtil.exportExcel(response, request, "用户利息回款查询", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //获取资金接收方标志
    private String getPropertySymbol(Object obj) {
        String propertySymbol = "";
        if (obj != null) {
            if ("YUN_DAI".equals(obj.toString())) {
                propertySymbol = "云贷循环贷";
            } else if ("7_DAI".equals(obj.toString())) {
                propertySymbol = "7贷循环贷";
            } else if ("ZAN".equals(obj.toString())){
                propertySymbol = "赞分期";
            }
        }
        return propertySymbol;
    }

    //获取子账户状态
    private String getAccountStatus(Object obj) {
        String accountStatus = "";
        if (obj != null) {
            if ("1".equals(obj.toString())) {
                accountStatus = "开户";
            } else if ("2".equals(obj.toString())) {
                accountStatus = "投资中";
            } else if ("3".equals(obj.toString())){
                accountStatus = "转让中";
            } else if ("4".equals(obj.toString())) {
                accountStatus = "已转让";
            } else if ("5".equals(obj.toString())){
                accountStatus = "已结算";
            } else if ("6".equals(obj.toString())) {
                accountStatus = "已销户";
            } else if ("7".equals(obj.toString())){
                accountStatus = "结算中";
            }
        }
        return accountStatus;
    }


    // ================================ 2017-10-16 更改手机号功能 start ==============================

    /**
     * 申请更改用户手机号列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/applyChangeUserMobileIndex")
    public String applyChangeUserMobileList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        UpdateHFUserApplyVO hfUserApplyVO = new UpdateHFUserApplyVO();
        String userName = request.getParameter("userName");
        String mobile = request.getParameter("mobile");
        String cardMobile = request.getParameter("cardMobile");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String mUser = request.getParameter("mUser");

        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
        }

        int count = hfUserActionService.applyQuery(userName, mobile, cardMobile,
                startTime, endTime, mUser);
        if(count > 0) {
            List<UpdateHFUserApplyVO> resultList = hfUserActionService.applyQuery(userName, mobile, cardMobile,
                    startTime, endTime, mUser, Integer.parseInt(pageNum), Integer.parseInt(numPerPage));
            model.put("applyUserList", resultList);
        }
        // 将数据返回给页面
        model.put("totalRows", count);
        model.put("userName", userName);
        model.put("mobile", mobile);
        model.put("cardMobile", cardMobile);
        model.put("startTime", startTime);
        model.put("endTime", endTime);
        model.put("mUser", mUser);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/bsuser/applyChangeUserMobile_index";
    }

    /**
     * 进入申请更改用户手机号页面
     * @param request
     * @param response
     * @param model
     * @param userId
     * @param mobile
     * @param userName
     * @return
     */
    @RequestMapping("/bsuser/applyChangeUserMobileDetail")
    public String applyChangeUserMobileDetail(HttpServletRequest request, HttpServletResponse response,
                                              Map<String, Object> model, Integer userId,
                                              String mobile, String userName) {
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        model.put("bsUserId", userId);
        model.put("mobile", mobile);
        model.put("mUserId", mUserId);
        model.put("userName", userName);
        return "/bsuser/applyChangeUserMobile_detail";
    }

    /**
     * 申请更改用户手机号操作
     * @param request
     * @param response
     * @param userId
     * @param mUserId
     * @param newMobile
     * @return
     */
    @ResponseBody
    @RequestMapping("/bsuser/applyChangeMobile")
    public Map<String, Object> applyChangeMobile(HttpServletRequest request, HttpServletResponse response,
                                                 String userId, String mUserId, String newMobile) {
        Map<String, Object> result = new HashMap<>();
        try {
            hfUserActionService.apply(Integer.parseInt(userId), Integer.parseInt(mUserId), newMobile);
            log.info("申请更改用户手机号成功");
            result.put("statusCode", "200");
        }catch (Exception e) {
            result.put("statusCode", "300");
            log.info("申请更改用户手机号失败的原因：{}",e.getMessage());
            result.put("message", null == e.getMessage() ? "申请更改失败" : e.getMessage());
        }
        return result;
    }

    /**
     * 确认更改用户手机号列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/confirmChangeUserMobileIndex")
    public String confirmChangeUserMobileList(HttpServletRequest request, HttpServletResponse response,
                                              Map<String, Object> model) {
        UpdateHFUserApplyVO hfUserApplyVO = new UpdateHFUserApplyVO();
        String userName = request.getParameter("userName");
        String mobile = request.getParameter("mobile");
        String cardMobile = request.getParameter("cardMobile");
        String startTime = request.getParameter("startTime");
        String endTime = request.getParameter("endTime");
        String mUser = request.getParameter("mUser");

        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
        }

        int count = hfUserActionService.passQuery(userName, mobile, cardMobile,
                startTime, endTime, mUser);
        if(count > 0) {
            List<UpdateHFUserApplyVO> resultList = hfUserActionService.passQuery(userName, mobile, cardMobile,
                    startTime, endTime, mUser, Integer.parseInt(pageNum), Integer.parseInt(numPerPage));
            model.put("confirmUserList", resultList);
        }
        // 将数据返回给页面
        model.put("totalRows", count);
        model.put("userName", userName);
        model.put("mobile", mobile);
        model.put("cardMobile", cardMobile);
        model.put("startTime", startTime);
        model.put("endTime", endTime);
        model.put("mUser", mUser);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/bsuser/confirmChangeUserMobile_index";
    }

    /**
     * 进入确认更改用户手机号页面
     * @param request
     * @param response
     * @param model
     * @param applyId
     * @param mobile
     * @param newMobile
     * @param userName
     * @return
     */
    @RequestMapping("/bsuser/confirmChangeUserMobileDetail")
    public String confirmChangeUserMobileDetail(HttpServletRequest request, HttpServletResponse response,
                                                Map<String, Object> model, String applyId,
                                                String mobile, String newMobile,
                                                String userName) {
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        model.put("mUserId", mUserId);
        model.put("applyId", applyId);
        model.put("mobile", mobile);
        model.put("newMobile", newMobile);
        model.put("userName", userName);
        return "/bsuser/confirmChangeUserMobile_detail";
    }

    /**
     * 确认更改用户手机号
     * @param request
     * @param response
     * @param mUserId
     * @param applyId
     * @return
     */
    @ResponseBody
    @RequestMapping("/bsuser/confirmChangeMobile")
    public Map<String, Object> confirmChangeMobile(HttpServletRequest request, HttpServletResponse response,
                                                 String mUserId, String applyId) {
        Map<String, Object> result = new HashMap<>();
        try {
            hfUserActionService.updatePlatAcct(Integer.parseInt(applyId), Integer.parseInt(mUserId));
            log.info("确认更改用户手机号成功");
            result.put("statusCode", "200");
        }catch (Exception e) {
            result.put("statusCode", "300");
            log.info("确认更改用户手机号失败的原因：{}",e.getMessage());
            result.put("message", null == e.getMessage() ? "确认更改用户手机号失败" : e.getMessage());
        }
        return result;
    }

    // ================================ 2017-10-16 更改手机号功能 end ================================
    
    // ================================ 2018-01-19 风险测评出借额度 start ================================
    /**
     * 风险测评出借额度 列表
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/questionnaireQuotaIndex")
    public String questionnaireQuotaList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
            pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
            numPerPage = String.valueOf(Constants.MANAGE_DEFAULT_NUMPERPAGE);
        }

        int count = bsQuestionnaireQuotaService.countQuestionnaireQuota();
        if(count > 0) {
            List<BsQuestionnaireQuota> quotaList = bsQuestionnaireQuotaService.selectList();
            model.put("quotaList", quotaList);
        }
        // 将数据返回给页面
        model.put("totalRows", count);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/bsuser/questionnaireQuota_index";
    }

    /**
     * 进入风险测评出借额度页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/bsuser/questionnaireQuotaDetail")
    public String questionnaireQuotaDetail(HttpServletRequest request, HttpServletResponse response,
                                              Map<String, Object> model, String quotaId) {
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        model.put("quotaId", quotaId);
        model.put("mUserId", mUserId);
        BsQuestionnaireQuota questionnaireQuota = bsQuestionnaireQuotaService.findById(Integer.parseInt(quotaId));
        model.put("quota", questionnaireQuota);
        EvaluateTypeEnum evaluateTypeEnum = EvaluateTypeEnum.find(questionnaireQuota.getEvaluateType());
        if(evaluateTypeEnum == null) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "未找到风险测评类型");
        }
        model.put("evaluateTypeValue", evaluateTypeEnum.getName());
        return "/bsuser/questionnaireQuota_detail";
    }
    
    /**
     * 风险测评出借额度操作
     * @param request
     * @param response
     * @return
     */
    @ResponseBody
    @RequestMapping("/bsuser/operateQuestionnaireQuota")
    public Map<String, Object> questionnaireQuotaOperate(HttpServletRequest request, HttpServletResponse response,
    								 String mUserId, String periodLimit, String quotaId, String amountLimit) {
        Map<String, Object> result = new HashMap<>();
        try {
        	bsQuestionnaireQuotaService.operateQuestionnaireQuota(Integer.parseInt(quotaId), Integer.parseInt(mUserId), Integer.parseInt(periodLimit), Double.parseDouble(amountLimit));
            log.info("风险测评出借额度更新成功");
            result.put("statusCode", "200");
        }catch (Exception e) {
            result.put("statusCode", "300");
            log.info("风险测评出借额度更新失败的原因：{}", e.getMessage());
            result.put("message", null == e.getMessage() ? "风险测评出借额度更新失败" : e.getMessage());
        }
        return result;
    }
    // ================================ 2018-01-19 风险测评出借额度 end ================================
    
    // ================================ 2018-05-29 申请更换安全卡 start ================================
    
    @RequestMapping("/bsuser/changeBindCardIndex")
    public String applyChangeBindCardIndex(HttpServletRequest request, HttpServletResponse response, 
			 Map<String, Object> model) {
    	// 页面传参
    	String userName = request.getParameter("userName");
		String mobile = request.getParameter("mobile");
		String checkStatus = request.getParameter("checkStatus");
		String verifyResult = request.getParameter("verifyResult");
		String applyStartTime = request.getParameter("applyStartTime");
		String applyEndTime = request.getParameter("applyEndTime");
		String checkStartTime = request.getParameter("checkStartTime");
		String checkEndTime = request.getParameter("checkEndTime");
		String queryFlag = request.getParameter("queryFlag");
		
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		if (StringUtil.isEmpty(pageNum) || StringUtil.isEmpty(numPerPage)) {
			pageNum = String.valueOf(Constants.MANAGE_DEFAULT_PAGENUM);
			numPerPage = String.valueOf(Constants.MANAGE_100_NUMPERPAGE);
		}
		
		// 封装页面参数
		BsChangeBindCardVO record = new BsChangeBindCardVO();
		record.setBusinessType(PoliceVerifyBusinessTypeEnum.UNBIND_BANK_CARD.getCode());
		if (StringUtils.isNotEmpty(userName)) {
			record.setUserName(userName.trim());
		}
		if (StringUtils.isNotEmpty(mobile)) {
			record.setMobile(mobile.trim());
		}
		if (StringUtils.isNotEmpty(queryFlag)) {
			record.setCheckStatus(checkStatus);
		} else {
			record.setCheckStatus(PoliceVerifyCheckStatusEnum.UNCHECKED.getCode());
		}
		if (StringUtils.isNotEmpty(queryFlag)) {
			record.setVerifyResult(verifyResult);
		} else {
			record.setVerifyResult("success");
		}
		if (StringUtils.isNotEmpty(applyStartTime)) {
			record.setApplyStartTime(applyStartTime);
		}
		if (StringUtils.isNotEmpty(applyEndTime)) {
			record.setApplyEndTime(applyEndTime);
		}
		if (StringUtils.isNotEmpty(checkStartTime)) {
			record.setCheckStartTime(checkStartTime);
		}
		if (StringUtils.isNotEmpty(checkEndTime)) {
			record.setCheckEndTime(checkEndTime);
		}
		
		// 统计
		int count = userService.countUserChangeBindCardInfo(record);
		record.setPageNum(Integer.valueOf(pageNum));
		record.setNumPerPage(Integer.valueOf(numPerPage));
		record.setTotalRows(count);
	
		// 列表查询
		if(count > 0) {
			List<BsChangeBindCardVO> resultList = userService.selectUserChangeBindCardInfo(record);
			model.put("totalRows", count);
			model.put("userList", resultList);
		} else {
			model.put("totalRows", 0);
		}
		
		// 将数据返回给页面
		model.put("req", record);
		return "/bsuser/changeBindCardIndex";
    }
    
    @RequestMapping("/bsuser/changeBindCardInfoPage")
    public String changeBindCardInfoPage(HttpServletRequest request, HttpServletResponse response, 
			 String id, Map<String, Object> model) {
		model.put("id", id);
		return "/bsuser/changeBindCardInfoPage";
    }
    
    /**
   	 * 审核更换安全卡
   	 * @param reqMsg
   	 * @param request
   	 * @param response
   	 * @return
   	 */
    @RequestMapping("/bsuser/verifyBindCardChange")
   	public @ResponseBody Map<Object, Object> verifyBindCardChange(Integer verifyId, String note,
   			 String checkStatus, HttpServletRequest request, HttpServletResponse response) {
   		CookieManager cookie = new CookieManager(request);
   		String muserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
   				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
   		if (verifyId != null && verifyId > 0) {
   			BsVerifyBindCardResVO res = userService.verifyUserChangeBindCard(muserId, verifyId, note, checkStatus);
   			if (StringUtil.isEmpty(res.getReturnMsg())) {
   				return ReturnDWZAjax.success("操作成功！");   				
   			} else {
   				return ReturnDWZAjax.fail(res.getReturnMsg());		
   			}
   		} else {
   			return ReturnDWZAjax.fail("没有要审核的记录！");
   		}
   	}
    
    /**
     * 审核材料页面
     * @param request
     * @param response
     * @param verifyId
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/verifyUnBindCardInfoPage")
    public String verifyUnBindCardInfoPage(HttpServletRequest request, HttpServletResponse response, 
			 Integer verifyId, Map<String, Object> model) {
    	BsUserPoliceVerify userPoliceVerify = null;
		BsUserPoliceVerifyExample example = new BsUserPoliceVerifyExample();
		example.createCriteria().andIdEqualTo(verifyId).andBusinessTypeEqualTo(PoliceVerifyBusinessTypeEnum.UNBIND_BANK_CARD.getCode());
		List<BsUserPoliceVerify> list = bsUserPoliceVerifyMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			userPoliceVerify = list.get(0);
		}
		model.put("userPoliceVerify", userPoliceVerify);
		model.put("checkDesc", StringUtils.substringBefore(userPoliceVerify.getCheckDesc(), ","));
		return "/bsuser/verifyUnBindCardInfoPage";
    }
    
    /**
     * 身份证照 和 认证图像显示
     * @param request
     * @param response
     * @param verifyId
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/unBindCardPicInfoPage")
    public String unBindCardPicInfoPage(HttpServletRequest request, HttpServletResponse response, 
			 Integer verifyId, Map<String, Object> model) {
    	String picFlag = StringUtil.isEmpty(request.getParameter("picFlag")) ? "" : request.getParameter("picFlag");
    	BsUserPoliceVerify userPoliceVerify = null;
		BsUserPoliceVerifyExample example = new BsUserPoliceVerifyExample();
		example.createCriteria().andIdEqualTo(verifyId).andBusinessTypeEqualTo(PoliceVerifyBusinessTypeEnum.UNBIND_BANK_CARD.getCode());
		List<BsUserPoliceVerify> list = bsUserPoliceVerifyMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			userPoliceVerify = list.get(0);
		}
		String mUrl = GlobEnv.get("gen.web");
        model.put("mUrl", mUrl);
		model.put("userPoliceVerify", userPoliceVerify);
		model.put("picFlag", picFlag);
		return "/bsuser/unBindCardPicInfoPage";
    }
    // ================================ 2018-05-29 申请更换安全卡 end ================================
    
    
    // ================================ 2018-08-01 用户状态冻结  start ================================
    @RequestMapping("/bsuser/updateUserStatus/detail")
    public String userStatusDetail(Integer id, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
    	BsUser bsUser = userService.findUserByUserId(id);
    	model.put("user", bsUser);
    	return "/bsuser/userstatus_detail";
    }
    
    @RequestMapping("/bsuser/updateUserStatus")
    @ResponseBody
    public Map<Object, Object> updateUserStatus(Integer verifyId, Integer userStatus, HttpServletRequest request,
    		HttpServletResponse response, Map<String, Object> model) {
    	// 存操作记录
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		
    	Boolean isSuccess = userService.updateUserStatus(mUser, verifyId, userStatus, AddressUtil.getIp(request));
		if (isSuccess) {
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
    }
    // ================================ 2018-08-01 用户状态冻结  end ================================

}
