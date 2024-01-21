package com.pinting.manage.controller;

import com.pinting.business.model.vo.AscriptionChangeDetailVO;
import com.pinting.business.model.vo.AscriptionChangeUserInfoVO;
import com.pinting.business.service.manage.AscriptionService;
import com.pinting.business.util.DateUtil;
import com.pinting.core.cookie.CookieManager;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Date;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/6/24.
 */
@Controller
public class AscriptionController {

    @Autowired
    private AscriptionService ascriptionService;

    /**
     * 客户归属变更--页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/ascription/index")
    public String ascriptionIndex(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        return "/ascription/ascription_index";
    }


    /**
     * 客户归属变更--用户信息
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/ascription/user_info")
    public String ascriptionUserInfo(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        AscriptionChangeUserInfoVO req = new AscriptionChangeUserInfoVO();
        String mobile = request.getParameter("mobile");
        String idCard = request.getParameter("idCard");

        req.setIdCard(idCard);
        req.setMobile(mobile);

        if (StringUtils.isNotBlank(mobile) || StringUtils.isNotBlank(idCard)) {

            AscriptionChangeUserInfoVO userInfoVO = ascriptionService.queryOwnershipGrid(req);
            model.put("userInfo", userInfoVO);

        }else {
            model.put("userInfo", null);
        }

        model.put("req", req);

        return "/ascription/ascription_index";
    }

    /**
     * 进入修改页面
     *
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/ascription/detail")
    public String detail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws ParseException {

        AscriptionChangeDetailVO req = new AscriptionChangeDetailVO();
        req.setUserId(Integer.parseInt(request.getParameter("userId")));
        req.setResultsTime(DateUtil.parse(DateUtil.format(new Date(), DateUtil.PAT_DATE) + " 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        model.put("detail", req);

        return "/ascription/detail";
    }

    /**
     * 查询详情数据
     * @param request
     * @param response
     * @param model
     * @return
     * @throws ParseException
     */
    @RequestMapping("/ascription/detail_info")
    @ResponseBody
    public Map<String, Object> queryDetail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws ParseException {

        AscriptionChangeDetailVO req = new AscriptionChangeDetailVO();
        req.setUserId(Integer.parseInt(request.getParameter("userId")));
        req.setManageMobile(request.getParameter("manageMobile"));
        req.setResultsTime(request.getParameter("resultsTime") == null ? DateUtil.parse(DateUtil.format(new Date(), DateUtil.PAT_DATE) + " 00:00:00", "yyyy-MM-dd HH:mm:ss") : DateUtil.parse(request.getParameter("resultsTime"), "yyyy-MM-dd HH:mm:ss"));

        if (StringUtils.isNotBlank(req.getManageMobile()) && req.getResultsTime() != null) {

            req = ascriptionService.queryOwnershipDetail(req);
        }

        model.put("detail", req);

        return model;
    }

    @RequestMapping("/ascription/modify")
    @ResponseBody
    public Map<Object, Object> modify(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) throws ParseException{

        try {
            AscriptionChangeDetailVO req = new AscriptionChangeDetailVO();
            req.setUserId(Integer.parseInt(request.getParameter("userId")));
            req.setManageMobile(request.getParameter("manageMobile"));
            req.setResultsTime(DateUtil.parse(request.getParameter("resultsTime"), "yyyy-MM-dd HH:mm:ss"));
            req.setAfterDeptId(Integer.parseInt(request.getParameter("afterDeptId")));
            req.setAfterDeptCode(request.getParameter("afterDeptCode"));
            req.setDeptName(request.getParameter("deptName"));
            req.setAfterManageId(Integer.parseInt(request.getParameter("afterManageId")));

            CookieManager cookie = new CookieManager(request);
            String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                    CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);

            req.setOpUserId(Integer.parseInt(mUserId));

            ascriptionService.modifyOwnership(req);

            return ReturnDWZAjax.success("变更成功！");
        }catch (Exception e){
            e.printStackTrace();
            return ReturnDWZAjax.fail("变更失败！");
        }
    }
}
