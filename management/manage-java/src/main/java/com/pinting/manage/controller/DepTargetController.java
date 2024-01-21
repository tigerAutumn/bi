package com.pinting.manage.controller;

import com.pinting.business.accounting.loan.enums.DepTargetEnum;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_WithdrawIndex;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_WithdrawIndex;
import com.pinting.business.model.LnDepositionTarget;
import com.pinting.business.model.LnDepositionTargetJnl;
import com.pinting.business.service.manage.DepTargetSendAgainService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.service.DepRepayScheduleService;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;
import com.pinting.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author:      cyb
 * Date:        2017/5/3
 * Description: 标的重发管理
 */
@Controller
@RequestMapping("/target")
public class DepTargetController {

    private static String TYPE_CHARGE_OFF = "chargeOff";    // 出账重发
    private static String TYPE_WITHDRAW = "withdraw";       // 出账提现重发

    @Autowired
    private DepTargetSendAgainService depTargetSendAgainService;

    @Autowired
    private DepRepayScheduleService depRepayScheduleService;

    private Logger log = LoggerFactory.getLogger(DepTargetController.class);


    /**
     * 标的重发管理首页
     * @return
     */
    @RequestMapping("/index")
    public String index(HttpServletRequest request, HttpServletResponse response, String id, Map<String, Object> model){
        String pageNum = request.getParameter("pageNum");
        String numPerPage = request.getParameter("numPerPage");
        Integer pageNumInt = 1;
        Integer numPerPageInt = 20;
        if(StringUtil.isNotBlank(pageNum)) {
            pageNumInt = Integer.parseInt(pageNum);
        }
        if(StringUtil.isNotBlank(numPerPage)) {
            numPerPageInt = Integer.parseInt(numPerPage);
        }
        model.put("numPerPage", numPerPageInt);
        model.put("pageNum", pageNum);
        if(StringUtil.isNotBlank(id)) {
            model.put("id", id);
            try {
                Map<String, Object> result = depTargetSendAgainService.queryDepositionTargetJnl(Integer.parseInt(id), pageNumInt, numPerPageInt);
                List<LnDepositionTargetJnl> list = (List<LnDepositionTargetJnl>) result.get("list");
                Integer count = (Integer) result.get("count");
                model.put("list", list);
                model.put("count", count);
                model.put("resCode", "000000");
            } catch (Exception e) {
                model.put("resCode", "999999");
                model.put("resMsg", e.getMessage());
            }
        }
        return "dep/target/index";
    }

    /**
     * 标的重发管理首页
     * @return
     */
    @ResponseBody
    @RequestMapping("/send")
    public Map<Object, Object> send(HttpServletRequest request, String id, String type) {

        if(StringUtil.isNotBlank(id)) {
            try {
                LnDepositionTargetJnl target = depTargetSendAgainService.queryById(Integer.parseInt(id));
                if (DepTargetEnum.DEP_TARGET_OPERATE_CANCELLED.getCode().equals(target.getTransType())) {
                    depTargetSendAgainService.targetAbandon(Integer.parseInt(id));
                } else if (DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF.getCode().equals(target.getTransType())) {
                    if(TYPE_CHARGE_OFF.equals(type)) {
                        // 出账重发
                        depTargetSendAgainService.targetChargeOff(Integer.parseInt(id));
                        return ReturnDWZAjax.success("出账重发成功");
                    } else if(TYPE_WITHDRAW.equals(type)) {
                        // 出账提现重发
                        depTargetSendAgainService.targetChargeOffCallBack(Integer.parseInt(id));
                        return ReturnDWZAjax.success("提现重发成功");
                    } else {
                        return ReturnDWZAjax.fail("缺少重发类型参数");
                    }
                } else if (DepTargetEnum.DEP_TARGET_OPERATE_SET_UP.getCode().equals(target.getTransType())) {
                    depTargetSendAgainService.targetEstablish(Integer.parseInt(id));
                } else if (DepTargetEnum.DEP_TARGET_OPERATE_CHARGE_OFF_PRE.getCode().equals(target.getTransType())) {
                    // 出账重发
                    depTargetSendAgainService.targetChargeOff(Integer.parseInt(id));
                    return ReturnDWZAjax.success("出账重发成功");
                } else {
                    return ReturnDWZAjax.fail(target.getTransType() + "：该类型不需要重发");
                }
            } catch (Exception e) {
            	e.printStackTrace();
                return ReturnDWZAjax.fail("重发失败：" + (e.getMessage() == null ? "" : e.getMessage()));
            }
        } else {
            return ReturnDWZAjax.fail("请选择标的");
        }
        return ReturnDWZAjax.success("重发成功");

    }

    /**
     * 进入标的回款重发页面
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/targetRepayIndex")
    public String repayIndex(HttpServletRequest request, Map<String, Object> model) {
        return "dep/target/repay_index";
    }

    /**
     * 标的回款重发
     * @param request
     * @param targetId 标的编号
     * @param targetFilePath 文件路径
     * @return
     */
    @ResponseBody
    @RequestMapping("/targetResend")
    public Map<String, Object> targetResend(HttpServletRequest request, String targetId, String targetFilePath) {
        Map<String, Object> result = new HashMap<>();

        try {
            String str = "";
            str = depRepayScheduleService.doRepay2Investor(Integer.parseInt(targetId), targetFilePath);
            if("".equals(str)){
                log.info("标的回款重发成功");
                result.put("statusCode", "200");
            }else{
                log.info("标的回款重发成功的失败，失败原因：{}", str);
                result.put("statusCode", "300");
                result.put("message", str);
            }
        } catch (Exception e) {
            log.info("标的回款重发异常：{}", e.getMessage());
            result.put("message", e.getMessage());
        }

        return result;
    }

}
