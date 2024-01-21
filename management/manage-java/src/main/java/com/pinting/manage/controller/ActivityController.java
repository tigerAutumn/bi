package com.pinting.manage.controller;

import com.pinting.business.model.BsActivity2017anniversaryUserBenison;
import com.pinting.business.model.BsWaterConservationSignUp;
import com.pinting.business.model.MUser;
import com.pinting.business.model.vo.ActivityWaterSignUpVO;
import com.pinting.business.service.manage.MUserService;
import com.pinting.business.service.site.ActivityService;
import com.pinting.business.service.site.AgentActivityService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.GlobEnv;
import com.pinting.util.ReturnDWZAjax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/3/2
 * Description:
 */
@Controller
@RequestMapping(value = "activity")
public class ActivityController {

    private Logger log = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private ActivityService activityService;
    @Autowired
    private AgentActivityService agentActivityService;
    @Autowired
    private MUserService mUserService;

    @RequestMapping("/water/list")
    public String waterList(HttpServletRequest request, Map<String, Object> model, ActivityWaterSignUpVO req) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        MUser muser = mUserService.findMUser(Integer.parseInt(mUserId));
        Integer agentId = 15;
        if(agentId.equals(muser.getAgentId())) {
            List<BsWaterConservationSignUp> list = agentActivityService.queryWaterSignUpList(req);
            int count = agentActivityService.countWaterSignUpList(req);
            model.put("list", list);
            model.put("count", count);
            model.put("req", req);
        }
        return "/activity/water_index";
    }

    @RequestMapping("/water/photo")
    public String waterPhoto(HttpServletRequest request, Map<String, Object> model) {
        model.put("img", GlobEnv.get("gen.web") + "/" + request.getParameter("img"));
        return "/activity/water_detail";
    }

    @ResponseBody
    @RequestMapping("/water/check")
    public Map<String, Object> waterCheck(HttpServletRequest request, BsWaterConservationSignUp req) {
        CookieManager cookieManager = new CookieManager(request);
        String mUserId = cookieManager.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        MUser muser = mUserService.findMUser(Integer.parseInt(mUserId));
        Integer agentId = 15;
        Map<String, Object> result = new HashMap<>();

        if(agentId.equals(muser.getAgentId())) {
            try {
                String idListString = req.getContent();
                String[] idList = idListString.split(",");
                for(int i = 0; i < idList.length; i++) {
                    if (!StringUtil.isBlank(idList[i])) {
                        req.setId(Integer.parseInt(idList[i]));
                        req.setContent(null);
                        agentActivityService.checkWater(req);
                    }
                }
                result.put("statusCode", "200");
                result.put("message", "成功");
            } catch (Exception e) {
                e.printStackTrace();
                result.put("statusCode", "500");
                result.put("message", "存在失败的审核，原因：" + e.getMessage());
            }
        } else {
            result.put("statusCode", "500");
            result.put("message", "无权限审核");
        }
        return result;
    }


    @RequestMapping("/benison/list")
    public String benisonList(HttpServletRequest request, Map<String, Object> model) {
        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        long count = activityService.anniversaryBenisonListCount();
        List<BsActivity2017anniversaryUserBenison> list = activityService.anniversaryBenisonList(StringUtil.isBlank(pageNum) ? 1 : Integer.parseInt(pageNum), StringUtil.isBlank(numPerPage) ? 20 : Integer.parseInt(numPerPage));
        model.put("list", list);
        model.put("count", count);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/activity/index";
    }

    @ResponseBody
    @RequestMapping("/benison/check")
    public Map<String, Object> acctTransRepeatSendIndex(BsActivity2017anniversaryUserBenison req) {
        Map<String, Object> result = new HashMap<>();
        try {
            String idListString = req.getContent();
            String[] idList = idListString.split(",");
            for(int i = 0; i < idList.length; i++) {
                if (!StringUtil.isBlank(idList[i])) {
                    BsActivity2017anniversaryUserBenison benison = new BsActivity2017anniversaryUserBenison();
                    benison.setId(Integer.parseInt(idList[i]));
                    benison.setCheckStatus(req.getCheckStatus());
                    activityService.anniversaryCheckBenison(benison);
                }
            }
            result.put("statusCode", "200");
            result.put("message", "成功");
        } catch (Exception e) {
            e.printStackTrace();
            result.put("statusCode", "500");
            result.put("message", "存在失败的审核，原因：" + e.getMessage());
        }
        return result;
    }
}
