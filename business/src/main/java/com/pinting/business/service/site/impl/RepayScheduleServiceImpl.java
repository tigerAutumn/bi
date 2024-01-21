package com.pinting.business.service.site.impl;

import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.vo.CashTransferSchemesCount;
import com.pinting.business.model.vo.CashTransferSchemesVO;
import com.pinting.business.service.site.RepayScheduleService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.in.util.MethodRole;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 回款计划
 * Created by shh on 2017/3/30.
 */
@Service
public class RepayScheduleServiceImpl implements RepayScheduleService {

    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;

    @Override
    public List<CashTransferSchemesVO> getPaymentPast(Integer userId,String status) {
        if(userId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        /*查询数据*/
        List<CashTransferSchemesVO> list = lnLoanRelationMapper.PaymentPast(userId);
        try{
          if(list != null && list.size() != 0){
              for(CashTransferSchemesVO cashTransferSchemesVO:list){
                  if("PASTDEFAULT".equals(status)){
                      cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"yyyy-MM"));
                  }
                  if("MICRO".equals(status)){
                      cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"yyyy-MM"));
                  }
                  cashTransferSchemesVO.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(cashTransferSchemesVO.getPlanTotalInit(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue(),cashTransferSchemesVO.getPlanTotalRepaying()).doubleValue());
                  cashTransferSchemesVO.setReceivableAmount(MoneyUtil.subtract(cashTransferSchemesVO.getRepayScheduleTotalAmount(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue() );
              }
          }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public List<CashTransferSchemesVO> getPaymentPlantPost(Integer userId,String dateTime){
        if(userId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        CashTransferSchemesVO record = new CashTransferSchemesVO();
        record.setUserId(userId);
        record.setDateTime(dateTime);
        /*查询数据*/
        List<CashTransferSchemesVO> list = lnLoanRelationMapper.selectPaymentPlantPast(record);
        try{
            if(list != null && list.size() != 0){
                for(CashTransferSchemesVO cashTransferSchemesVO:list){
                    cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"yyyy-MM"));
                    cashTransferSchemesVO.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(cashTransferSchemesVO.getPlanTotalInit(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue(),cashTransferSchemesVO.getPlanTotalRepaying()).doubleValue());
                    cashTransferSchemesVO.setReceivableAmount(MoneyUtil.subtract(cashTransferSchemesVO.getRepayScheduleTotalAmount(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue() );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @MethodRole("APP")
    public List<CashTransferSchemesVO> queryRepayScheduleList(Integer userId) {
        if(userId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        List<CashTransferSchemesVO> list = lnLoanRelationMapper.PaymentPast(userId);
        try{
            if(list != null && list.size() != 0){
                for(CashTransferSchemesVO cashTransferSchemesVO : list){
                    cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"yyyy-MM"));
                    cashTransferSchemesVO.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(cashTransferSchemesVO.getPlanTotalInit(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue(),cashTransferSchemesVO.getPlanTotalRepaying()).doubleValue());
                    cashTransferSchemesVO.setReceivableAmount(MoneyUtil.subtract(cashTransferSchemesVO.getRepayScheduleTotalAmount(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue() );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    @Override
    @MethodRole("APP")
    public List<CashTransferSchemesVO> queryRepayScheduleDetailsList(Integer userId, String planDate) {
        if(userId == null || planDate == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        List<CashTransferSchemesVO> resultList = lnLoanRelationMapper.selectRepayScheduleDetailsList(userId, planDate);
        if(resultList != null && resultList.size() > 0) {
            for(CashTransferSchemesVO cashTransferSchemesVO : resultList){
                cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"MM-dd"));
                cashTransferSchemesVO.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(cashTransferSchemesVO.getPlanTotalInit(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue(),cashTransferSchemesVO.getPlanTotalRepaying()).doubleValue());
                cashTransferSchemesVO.setReceivableAmount(MoneyUtil.subtract(cashTransferSchemesVO.getRepayScheduleTotalAmount(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue() );
            }
        }
        return resultList;
    }

    @Override
    public CashTransferSchemesCount getPaymentPlantDetails(Integer userId, String detailsTime,Integer page,Integer pageSize, String status){
        if(userId == null || detailsTime == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        CashTransferSchemesVO cashTransferSchemesVO = new CashTransferSchemesVO();
        cashTransferSchemesVO.setUserId(userId);
        cashTransferSchemesVO.setDetailsTime(detailsTime);
        int start = (page <= 1) ? 0 : ((page - 1) * pageSize);
        cashTransferSchemesVO.setStartNumPage(start);
        cashTransferSchemesVO.setEndNumPerPage(pageSize);
        CashTransferSchemesCount cashTransferSchemesCount = new CashTransferSchemesCount();
        try{
            /*查询详情总条数*/
            Integer count = lnLoanRelationMapper.getPaymentPlantDetalisCount(cashTransferSchemesVO);

            Double totalRepayScheduleTotalAmount = 0.0;
            Double totalPlanTotalRepaied = 0.0;
            Double totalReceivableAmount = 0.0;
            List<CashTransferSchemesVO> list = new ArrayList<CashTransferSchemesVO>();
            List<CashTransferSchemesVO> lists = new ArrayList<CashTransferSchemesVO>();
            /*点击详情页查询详情*/
            if("DETAILS".equals(status)) {
              /*查询回款计划详情*/
                list = lnLoanRelationMapper.getPaymentPastDetails(cashTransferSchemesVO);
                if (page * pageSize >= count) {
                    if (list != null) {
                        for (CashTransferSchemesVO vo : list) {
                            vo.setPlanDate(com.pinting.business.util.DateUtil.getDate(vo.getBasePlanDate()));
                            vo.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(vo.getPlanTotalInit(), vo.getPlanTotalRepaied()).doubleValue(), vo.getPlanTotalRepaying()).doubleValue());
                            vo.setReceivableAmount(MoneyUtil.subtract(vo.getRepayScheduleTotalAmount(), vo.getPlanTotalRepaied()).doubleValue());
                            totalRepayScheduleTotalAmount = MoneyUtil.add(totalRepayScheduleTotalAmount,vo.getRepayScheduleTotalAmount()).doubleValue();
                            totalPlanTotalRepaied = MoneyUtil.add(totalPlanTotalRepaied, vo.getPlanTotalRepaied()).doubleValue();
                            totalReceivableAmount = MoneyUtil.add(totalReceivableAmount, vo.getReceivableAmount()).doubleValue();
                        }
                    }
                }else {
                        for (CashTransferSchemesVO vo : list) {
                            vo.setPlanDate(com.pinting.business.util.DateUtil.getDate(vo.getBasePlanDate()));
                            vo.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(vo.getPlanTotalInit(), vo.getPlanTotalRepaied()).doubleValue(), vo.getPlanTotalRepaying()).doubleValue());
                            vo.setReceivableAmount(MoneyUtil.subtract(vo.getRepayScheduleTotalAmount(), vo.getPlanTotalRepaied()).doubleValue());
                        }
                        lists = lnLoanRelationMapper.getDetailsList(cashTransferSchemesVO);
                        for (CashTransferSchemesVO vo : lists) {
                            vo.setPlanDate(com.pinting.business.util.DateUtil.getDate(vo.getBasePlanDate()));
                            vo.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(vo.getPlanTotalInit(), vo.getPlanTotalRepaied()).doubleValue(), vo.getPlanTotalRepaying()).doubleValue());
                            vo.setReceivableAmount(MoneyUtil.subtract(vo.getRepayScheduleTotalAmount(), vo.getPlanTotalRepaied()).doubleValue());
                            totalRepayScheduleTotalAmount = MoneyUtil.add(totalRepayScheduleTotalAmount, vo.getRepayScheduleTotalAmount()).doubleValue();
                            totalPlanTotalRepaied = MoneyUtil.add(totalPlanTotalRepaied, vo.getPlanTotalRepaied()).doubleValue();
                            totalReceivableAmount = MoneyUtil.add(totalReceivableAmount, vo.getReceivableAmount()).doubleValue();
                        }
                    }
                }
            /*点击分页按钮查询详情*/
        if("PAGE".equals(status)){
              //*查询回款计划详情*//*
              list = lnLoanRelationMapper.getPaymentPastDetails(cashTransferSchemesVO);
              if(list !=null){
                  for(CashTransferSchemesVO vo : list) {
                      vo.setPlanDate(com.pinting.business.util.DateUtil.getDate(vo.getBasePlanDate()));
                      vo.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(vo.getPlanTotalInit(), vo.getPlanTotalRepaied()).doubleValue(), vo.getPlanTotalRepaying()).doubleValue());
                      vo.setReceivableAmount(MoneyUtil.subtract(vo.getRepayScheduleTotalAmount(), vo.getPlanTotalRepaied()).doubleValue());
                  }
              }
              lists = lnLoanRelationMapper.getDetailsList(cashTransferSchemesVO);
              for(CashTransferSchemesVO vo : lists){
                  vo.setPlanDate(com.pinting.business.util.DateUtil.getDate(vo.getBasePlanDate()));
                  vo.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(vo.getPlanTotalInit(),vo.getPlanTotalRepaied()).doubleValue(),vo.getPlanTotalRepaying()).doubleValue());
                  vo.setReceivableAmount(MoneyUtil.subtract(vo.getRepayScheduleTotalAmount(),vo.getPlanTotalRepaied()).doubleValue() );
                  totalRepayScheduleTotalAmount = MoneyUtil.add(totalRepayScheduleTotalAmount, vo.getRepayScheduleTotalAmount()).doubleValue();
                  totalPlanTotalRepaied = MoneyUtil.add(totalPlanTotalRepaied, vo.getPlanTotalRepaied()).doubleValue();
                  totalReceivableAmount = MoneyUtil.add(totalReceivableAmount, vo.getReceivableAmount()).doubleValue();
              }
            }
            cashTransferSchemesCount.setTotalRepayScheduleTotalAmount(totalRepayScheduleTotalAmount);
            cashTransferSchemesCount.setTotalPlanTotalRepaied(totalPlanTotalRepaied);
            cashTransferSchemesCount.setTotalReceivableAmount(totalReceivableAmount);
            cashTransferSchemesCount.setCount(count);
            cashTransferSchemesCount.setList(list);
        }catch (Exception e){
            e.printStackTrace();
        }
        return cashTransferSchemesCount;
    }

    @Override
   public CashTransferSchemesCount getMicroDatecls(Integer userId,String dateilsTime){
        if(userId == null || dateilsTime == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        CashTransferSchemesCount cashTransferSchemesCount = new CashTransferSchemesCount();
        CashTransferSchemesVO cashTransferSchemesVO = new CashTransferSchemesVO();
        cashTransferSchemesVO.setUserId(userId);
        cashTransferSchemesVO.setDetailsTime(dateilsTime);
        Double totalRepayScheduleTotalAmount = 0.0;
        Double totalPlanTotalRepaied = 0.0;
       List<CashTransferSchemesVO> list = lnLoanRelationMapper.getDetailsList(cashTransferSchemesVO);
        for (CashTransferSchemesVO vo : list) {
            vo.setPlanDate(DateUtil.formatDateTime(vo.getBasePlanDate(),"MM-dd"));
            vo.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(vo.getPlanTotalInit(), vo.getPlanTotalRepaied()).doubleValue(), vo.getPlanTotalRepaying()).doubleValue());
            vo.setReceivableAmount(MoneyUtil.subtract(vo.getRepayScheduleTotalAmount(), vo.getPlanTotalRepaied()).doubleValue());
            totalRepayScheduleTotalAmount = MoneyUtil.add(totalRepayScheduleTotalAmount, vo.getRepayScheduleTotalAmount()).doubleValue();
            totalPlanTotalRepaied = MoneyUtil.add(totalPlanTotalRepaied, vo.getPlanTotalRepaied()).doubleValue();
        }
        cashTransferSchemesCount.setTotalRepayScheduleTotalAmount(totalRepayScheduleTotalAmount);
        cashTransferSchemesCount.setTotalPlanTotalRepaied(totalPlanTotalRepaied);
        cashTransferSchemesCount.setList(list);
        return  cashTransferSchemesCount;
    }
    
    @Override
	public List<CashTransferSchemesVO> getPaymentPast(Integer userId,
			String status, Integer pageIndex, Integer pageSize) {
		if(userId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        /*查询数据*/
		int start = (pageIndex <= 0) ? 0 : (pageIndex * pageSize);
        List<CashTransferSchemesVO> list = lnLoanRelationMapper.paymentPastPage(userId, start, pageSize);
        try{
          if(CollectionUtils.isNotEmpty(list)) {
              for(CashTransferSchemesVO cashTransferSchemesVO:list){
                  if("PASTDEFAULT".equals(status)){
                      cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"yyyy-MM"));
                  }
                  if("MICRO".equals(status)){
                      cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"yyyy-MM"));
                  }
                  cashTransferSchemesVO.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(cashTransferSchemesVO.getPlanTotalInit(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue(),cashTransferSchemesVO.getPlanTotalRepaying()).doubleValue());
                  cashTransferSchemesVO.setReceivableAmount(MoneyUtil.subtract(cashTransferSchemesVO.getRepayScheduleTotalAmount(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue() );
              }
          }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
	}

	@Override
	public List<CashTransferSchemesVO> getPaymentPlantPost(Integer userId,
			String dateTime, Integer pageIndex, Integer pageSize) {
		if(userId == null){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }

        CashTransferSchemesVO record = new CashTransferSchemesVO();
        record.setUserId(userId);
        record.setDateTime(dateTime);
        record.setNumPerPage(pageSize);
        record.setPageNum(pageIndex + 1);

        /*查询数据*/
        List<CashTransferSchemesVO> list = lnLoanRelationMapper.selectPaymentPlantPast(record);
        try{
            if(CollectionUtils.isNotEmpty(list)){
                for(CashTransferSchemesVO cashTransferSchemesVO:list){
                    cashTransferSchemesVO.setPlanDate(DateUtil.formatDateTime(cashTransferSchemesVO.getBasePlanDate(),"yyyy-MM"));
                    cashTransferSchemesVO.setRepayScheduleTotalAmount(MoneyUtil.add(MoneyUtil.add(cashTransferSchemesVO.getPlanTotalInit(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue(),cashTransferSchemesVO.getPlanTotalRepaying()).doubleValue());
                    cashTransferSchemesVO.setReceivableAmount(MoneyUtil.subtract(cashTransferSchemesVO.getRepayScheduleTotalAmount(),cashTransferSchemesVO.getPlanTotalRepaied()).doubleValue() );
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return list;
	}
	
	@Override
	public Integer getPaymentPastCount(Integer userId, String status) {
		return lnLoanRelationMapper.countPaymentPast(userId);
	}
	
	@Override
	public Integer getPaymentPlantPostCount(Integer userId, String dateTime) {
		return lnLoanRelationMapper.countPaymentPlantPost(userId, dateTime);
	}
	
	
}
