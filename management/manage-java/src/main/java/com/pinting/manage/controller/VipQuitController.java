package com.pinting.manage.controller;

import com.pinting.business.accounting.finance.service.DepStageQuitService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_VipQuit_VipQuitList;
import com.pinting.business.hessian.manage.message.ResMsg_VipQuit_VipQuitList;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsVipQuit;
import com.pinting.business.model.MUser;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MAccountService;
import com.pinting.business.service.manage.MUserService;
import com.pinting.business.service.manage.VipQuitService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 赞分期VIP退出申请Controller
 * Created by shh on 2017/4/24.
 */
@Controller
public class VipQuitController {

    private Logger log = LoggerFactory.getLogger(VipQuitController.class);

    @Autowired
    @Qualifier("dispatchService")
    private HessianService hessianService;

    @Autowired
    private BsUserService bsUserService;

    @Autowired
    private VipQuitService vipQuitService;

    @Autowired
    private MUserService mUserService;

    @Autowired
    private MAccountService mAccountService;

    @Autowired
    private DepStageQuitService depStageQuitService;


    /**
     * 赞分期VIP退出申请-列表
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/vipQuit/vipQuitListIndex")
    public String vipQuitListInit(ReqMsg_VipQuit_VipQuitList req, HttpServletRequest request, Map<String, Object> model) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        if(req.getUserId() != null) {//显示对应VIP户的站岗户余额
            req.setUserId(req.getUserId());
        }else {
            //vip用户信息
            List<BsUser> userList = bsUserService.queryZanCompensateInfo();
            if(userList != null && userList.size() > 0) {
                req.setUserId(userList.get(0).getId());
            }
        }
        ResMsg_VipQuit_VipQuitList res = (ResMsg_VipQuit_VipQuitList) hessianService.handleMsg(req);
        model.put("req", req);
        model.put("pageNum", res.getPageNum());
        model.put("numPerPage", res.getNumPerPage());
        model.put("totalRows", res.getTotalRows());
        model.put("zanVipAuthAmount", res.getZanVipAuthAmount());
        model.put("zanUserList", res.getZanUserList());
        model.put("vipQuitList", res.getValueList());
        return "/vipQuit/vipQuit_list_index";
    }

    /**
     * 退出申请
     * @param request
     * @param response
     * @param amount
     * @param userId
     * @return
     */
    @ResponseBody
    @RequestMapping("/vipQuit/applyForExit")
    public Map<String, Object> applyForExitInit(HttpServletRequest request, HttpServletResponse response,
                                                Double amount, String userId) {
        Map<String, Object> result = new HashMap<>();

        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));

        //站岗户余额>=申请金额
        double zanAuthAmount = mAccountService.queryZanAuthAmount(Integer.parseInt(userId));
        if(zanAuthAmount >= amount) {
            BsVipQuit bsVipQuit = new BsVipQuit();
            bsVipQuit.setPropertySymbol(Constants.PROPERTY_SYMBOL_ZAN);
            bsVipQuit.setUserId(Integer.parseInt(userId));
            BsUser bsUser = bsUserService.findUserByUserId(Integer.parseInt(userId));
            if(bsUser != null) {
                bsVipQuit.setUserName(bsUser.getUserName());
            }
            bsVipQuit.setAmount(amount);
            bsVipQuit.setOpUserId(Integer.parseInt(mUserId));
            bsVipQuit.setStatus(Constants.VIP_QUIT_STATUS_INIT);//待审核
            bsVipQuit.setCreateTime(new Date());
            bsVipQuit.setUpdateTime(new Date());
            int count = vipQuitService.vipQuit(bsVipQuit);
            if(count > 0) {
                result.put("statusCode", "200");//退出操作成功，库中新增记录
                result.put("message", userId);//退出成功，下拉框显示对应的用户
            }else {
                result.put("statusCode", "300");//退出失败
            }
        }else {
            result.put("statusCode", "300");//退出失败
            log.info("申请金额大于站岗户余额，退出失败");
        }
        return result;
    }

    /**
     * 赞分期VIP退出申请-审核列表
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/vipQuit/vipQuitCheckListIndex")
    public String vipQuitCheckListInit(ReqMsg_VipQuit_VipQuitList req, HttpServletRequest request, Map<String, Object> model, String userId) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        if(StringUtil.isNotBlank(userId)) {//显示对应VIP户的站岗户余额
            req.setUserId(Integer.parseInt(userId));
        }else {
            //vip用户信息
            List<BsUser> userList = bsUserService.queryZanCompensateInfo();
            if(userList != null && userList.size() > 0) {
                req.setUserId(userList.get(0).getId());
            }
        }
        ResMsg_VipQuit_VipQuitList res = (ResMsg_VipQuit_VipQuitList) hessianService.handleMsg(req);
        model.put("req", req);
        model.put("pageNum", res.getPageNum());
        model.put("numPerPage", res.getNumPerPage());
        model.put("totalRows", res.getTotalRows());
        model.put("vipQuitList", res.getValueList());
        return "/vipQuit/vipQuit_check_index";
    }

    /**
     * 申请拒绝
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/vipQuit/quitRefu")
    @ResponseBody
    public Map<Object, Object> quitRefuInit(String id,HttpServletRequest request) {

        try {
            if(StringUtil.isNotEmpty(id)) {
                BsVipQuit result = vipQuitService.queryVipQuitById(Integer.valueOf(id));
                if(result == null) {
                    return ReturnDWZAjax.fail("没有该退出申请记录");
                }
                CookieManager cookie = new CookieManager(request);
                String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                        CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
                BsVipQuit bsVipQuit = new BsVipQuit();
                bsVipQuit.setId(Integer.parseInt(id));
                bsVipQuit.setCheckTime(new Date());
                bsVipQuit.setUpdateTime(new Date());
                bsVipQuit.setCkUserId(Integer.parseInt(mUserId));
                bsVipQuit.setStatus(Constants.VIP_QUIT_STATUS_REFU);
                int row = vipQuitService.vipQuitCheck(bsVipQuit);
                if(row >0) {
                    return ReturnDWZAjax.success("操作成功");
                }else {
                    return ReturnDWZAjax.fail("操作失败");
                }
            }else {
                return ReturnDWZAjax.fail("申请记录Id参数不能为空");
            }
        } catch (Exception e) {
            return ReturnDWZAjax.fail("操作失败");
        }

    }

    /**
     * 申请通过
     * @param id
     * @param request
     * @return
     */
    @RequestMapping("/vipQuit/quitPass")
    @ResponseBody
    public Map<Object, Object> quitPassInit(String id, String userId, HttpServletRequest request) {

        try {
            if(StringUtil.isNotEmpty(id)) {
                BsVipQuit result = vipQuitService.queryVipQuitById(Integer.valueOf(id));
                if(result == null) {
                    return ReturnDWZAjax.fail("没有该退出申请记录");
                }
                CookieManager cookie = new CookieManager(request);
                String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                        CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
                BsVipQuit bsVipQuit = new BsVipQuit();
                bsVipQuit.setId(Integer.parseInt(id));
                bsVipQuit.setCheckTime(new Date());
                bsVipQuit.setUpdateTime(new Date());
                bsVipQuit.setCkUserId(Integer.parseInt(mUserId));
                bsVipQuit.setStatus(Constants.VIP_QUIT_STATUS_PASS);//审核通过
                int row = vipQuitService.vipQuitCheck(bsVipQuit);
                if(row >0) {
                    //审核通过-调用退出服务
                    try {
                        depStageQuitService.vipQuitAccount(Integer.parseInt(id), Integer.parseInt(userId), Integer.parseInt(mUserId));
                        return ReturnDWZAjax.success("退出成功");
                    } catch (PTMessageException e) {//自定异常
                        log.info("赞分期VIP退出申请失败的原因："+e.getMessage());
                        return ReturnDWZAjax.fail(e.getMessage());
                    } catch (Exception e) {//系统异常
                        return ReturnDWZAjax.fail("操作失败");
                    }

                }else {
                    return ReturnDWZAjax.fail("退出失败");
                }
            }else {
                return ReturnDWZAjax.fail("申请记录Id参数不能为空");
            }
        } catch (Exception e) {
            return ReturnDWZAjax.fail("退出失败");

        }

    }

}
