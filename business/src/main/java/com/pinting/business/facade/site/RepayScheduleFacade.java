package com.pinting.business.facade.site;

import com.pinting.business.hessian.site.message.ReqMsg_RepaySchedule_PaymentPlant;
import com.pinting.business.hessian.site.message.ResMsg_RepaySchedule_PaymentPlant;
import com.pinting.business.model.vo.CashTransferSchemesCount;
import com.pinting.business.model.vo.CashTransferSchemesVO;
import com.pinting.business.service.site.RepayScheduleService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 回款计划相关facade
 * Created by shh on 2017/3/30.
 */
@Component("RepaySchedule")
public class RepayScheduleFacade {
   @Autowired
   private RepayScheduleService repayScheduleService;

   public void paymentPlant(ReqMsg_RepaySchedule_PaymentPlant req, ResMsg_RepaySchedule_PaymentPlant res){
	   Integer totalCount = 0;
	   /*点击侧边菜单栏链接，查询待收回款和往期回款*/
       if("PASTDEFAULT".equals(req.getStatus())){
           /*查询待收回款*/
           List<CashTransferSchemesVO> list = repayScheduleService.getPaymentPast(req.getUserId(), req.getStatus(),
        		   req.getPageIndex(), req.getPageSize());
           totalCount = repayScheduleService.getPaymentPastCount(req.getUserId(), req.getStatus());
           res.setTotalRecord((int)Math.ceil((double)totalCount/req.getPageSize()));
           if(CollectionUtils.isNotEmpty(list)){
               ArrayList<HashMap<String, Object>> investList = BeanUtils.classToArrayList(list);
               res.setPaymentPastList(investList);
           }
       }
       /*往期回款时间段查询往期回款*/
       if("PASTYEAR".equals(req.getStatus())){
              /*查询往期回款*/
           List<CashTransferSchemesVO> arrayList = repayScheduleService.getPaymentPlantPost(req.getUserId(), req.getDateTime(),
        		   req.getPageIndex(), req.getPageSize());
           totalCount = repayScheduleService.getPaymentPlantPostCount(req.getUserId(), req.getDateTime());
           res.setTotalRecordPast((int)Math.ceil((double)totalCount/req.getPageSize()));
           if(CollectionUtils.isNotEmpty(arrayList)){
               ArrayList<HashMap<String, Object>> paymentList = BeanUtils.classToArrayList(arrayList);
               res.setPaymentPlantPastList(paymentList);
           }
       }
       /*点击详情页进入详情分页查询*/
       if("DETAILS".equals(req.getStatus()) || "PAGE" .equals(req.getStatus())){
           /*第一次查询详情*/
           CashTransferSchemesCount cashTransferSchemesCount = repayScheduleService.getPaymentPlantDetails(req.getUserId(),req.getDetailsTime(), req.getPage(),req.getPageSize(),req.getStatus());
           if (cashTransferSchemesCount != null) {
               res.setCount(cashTransferSchemesCount.getCount() != null ? cashTransferSchemesCount.getCount() : 0);
               res.setTotalRepayScheduleTotalAmount(cashTransferSchemesCount.getTotalRepayScheduleTotalAmount());
               res.setTotalPlanTotalRepaied(cashTransferSchemesCount.getTotalPlanTotalRepaied());
               res.setTotalReceivableAmount(cashTransferSchemesCount.getTotalReceivableAmount());
               ArrayList<HashMap<String, Object>> paymentList = BeanUtils.classToArrayList(cashTransferSchemesCount.getList());
               res.setPaymentPlantDetailsList(paymentList);
           }
       }

       /*H5查询回款*/
       if("MICRO".equals(req.getStatus())){
                  /*查询待收回款*/
           List<CashTransferSchemesVO> list = repayScheduleService.getPaymentPast(req.getUserId(),req.getStatus(),
                   req.getPageIndex(), req.getPageSize());
           totalCount = repayScheduleService.getPaymentPastCount(req.getUserId(), req.getStatus());
           res.setTotalRecord((int)Math.ceil((double)totalCount/req.getPageSize()));
           if(CollectionUtils.isNotEmpty(list)) {
               ArrayList<HashMap<String, Object>> investList = BeanUtils.classToArrayList(list);
               res.setPaymentPastList(investList);
           }
       }

       /*H5查询详情*/
       if("MICRODERICLS".equals(req.getStatus())){
           CashTransferSchemesCount arrayList = repayScheduleService.getMicroDatecls(req.getUserId(),req.getDetailsTime());
           if(arrayList != null){
               res.setTotalRecord(arrayList.getList().size());
               res.setTotalRepayScheduleTotalAmount(arrayList.getTotalRepayScheduleTotalAmount());
               res.setTotalPlanTotalRepaied(arrayList.getTotalPlanTotalRepaied());
               ArrayList<HashMap<String, Object>> investList = BeanUtils.classToArrayList(arrayList.getList());
               res.setPaymentPastList(investList);
           }
       }
   }
}
