package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.hessian.site.message.ReqMsg_RepaySchedule_RepayScheduleDetails;
import com.pinting.business.hessian.site.message.ReqMsg_RepaySchedule_RepaymentPlanList;
import com.pinting.business.hessian.site.message.ResMsg_RepaySchedule_RepayScheduleDetails;
import com.pinting.business.hessian.site.message.ResMsg_RepaySchedule_RepaymentPlanList;
import com.pinting.business.model.vo.CashTransferSchemesVO;
import com.pinting.business.service.site.RepayScheduleService;
import com.pinting.business.util.BeanUtils;
import com.pinting.gateway.in.util.InterfaceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 回款计划
 * Created by shh on 2017/3/30.
 */
@Component("appRepaySchedule")
public class RepayScheduleFacade {

    @Autowired
    private RepayScheduleService RepayScheduleService;

    /**
     * 回款计划-列表
     * @param req
     * @param res
     */
    @InterfaceVersion("RepaymentPlanList/1.0.0")
    public void repaymentPlanList(ReqMsg_RepaySchedule_RepaymentPlanList req, ResMsg_RepaySchedule_RepaymentPlanList res) {
        List<CashTransferSchemesVO> valueList = RepayScheduleService.queryRepayScheduleList(req.getUserId());
        ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(valueList);
        res.setRepaymentPlanList(bean);
    }

    /**
     * 回款计划-详情
     * @param req
     * @param res
     */
    @InterfaceVersion("RepayScheduleDetails/1.0.0")
    public void repayScheduleDetails(ReqMsg_RepaySchedule_RepayScheduleDetails req, ResMsg_RepaySchedule_RepayScheduleDetails res) {
        List<CashTransferSchemesVO> valueList = RepayScheduleService.queryRepayScheduleDetailsList(req.getUserId(), req.getPlanDate());
        ArrayList<HashMap<String,Object>> bean = BeanUtils.classToArrayList(valueList);
        res.setRepayScheduleDetailsList(bean);
    }

}
