package com.pinting.business.service.site;

import com.pinting.business.model.vo.CashTransferSchemesCount;
import com.pinting.business.model.vo.CashTransferSchemesVO;

import java.util.List;

/**
 * 回款计划相关service
 * Created by shh on 2017/3/30.
 */
public interface RepayScheduleService {

    /**
     * 回款计划中的待收回款查询
     * @param userId
     * @return
     */
    List<CashTransferSchemesVO> getPaymentPast(Integer userId,String status);

    /**
     * 回款计划列表-APP专用
     * @param userId
     * @return
     */
    List<CashTransferSchemesVO> queryRepayScheduleList(Integer userId);

    /**
     * 回款计划明细-APP专用
     * @param userId
     * @param planDate
     * @return
     */
    List<CashTransferSchemesVO> queryRepayScheduleDetailsList(Integer userId, String planDate);

    /**
     * 回款计划中的往期回款
     * @param userId
     * @param dateTime
     * @return
     */
    List<CashTransferSchemesVO> getPaymentPlantPost(Integer userId,String dateTime);

    /**
     * 回款计划中的详情查询
     * @param userId
     * @param detailsTime
     * @param startNum
     * @param endPageNum
     * @param status
     * @return
     */
    CashTransferSchemesCount getPaymentPlantDetails(Integer userId, String detailsTime,Integer startNum,Integer endPageNum,String status);

    /**
     * H5回款计划中的详情查询
     * @param userId
     * @param detailsTime
     * @return
     */
    CashTransferSchemesCount getMicroDatecls(Integer userId,String detailsTime);
    
    
    /**
     * 回款计划中的待收回款查询
     * @param userId
     * @return
     */
    List<CashTransferSchemesVO> getPaymentPast(Integer userId, String status, Integer pageIndex, Integer pageSize);
    
    /**
     * 回款计划中的往期回款
     * @param userId
     * @param dateTime
     * @return
     */
    List<CashTransferSchemesVO> getPaymentPlantPost(Integer userId,String dateTime, Integer pageIndex, Integer pageSize);
    
    /**
     * 回款计划中的待收回款查询计数
     * @param userId
     * @return
     */
    Integer getPaymentPastCount(Integer userId, String status);
    
    /**
     * 回款计划中的往期回款计数
     * @param userId
     * @param dateTime
     * @return
     */
    Integer getPaymentPlantPostCount(Integer userId,String dateTime);
    
}
