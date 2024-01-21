package com.pinting.business.dao;

import com.pinting.business.model.LnRepay;
import com.pinting.business.model.LnRepayExample;
import com.pinting.business.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LnRepayMapper {
    int countByExample(LnRepayExample example);

    int deleteByExample(LnRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnRepay record);

    int insertSelective(LnRepay record);

    List<LnRepay> selectByExample(LnRepayExample example);

    LnRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnRepay record, @Param("example") LnRepayExample example);

    int updateByExample(@Param("record") LnRepay record, @Param("example") LnRepayExample example);

    int updateByPrimaryKeySelective(LnRepay record);

    int updateByPrimaryKey(LnRepay record);
    
    /**
     * 根据借款id和还款计划编号查询某期已还成功的金额
     * @param loanId
     * @param repayPlanId
     * @return
     */
    Double repayedAmountByLoanIdRepayPlanId(@Param("loanId")Integer loanId, @Param("repayPlanId")Integer repayPlanId);

    
    /**
     * 根据合作方还款编号查询还款状态
     * @param partnerOrderNo
     * @return
     */
    List<LnRepayVO> selectByPartnerOrderNo(@Param("partnerOrderNo")String partnerOrderNo);

    /**
     * 查询还款对账明细
     * @return
     */
    List<LnRepayForCheckVO> selectLnRepayForCheck();

    /**
     * 根据订单号查询每一期计划利息和计划本金
     * @param orderId
     * @return
     */
    List<RepayInfoVO> selectRepayInfoGroup(@Param("orderId") String orderId);
    
    
    /**
     * 根据借款id查询已还款成功的数量
     * @param loanId
     * @return
     */
    Integer countRepayedByLoanId(@Param("loanId")Integer loanId);

    /**
     * 查询订单号对应的去重借款id列表
     * @param orderNo
     * @return
     */
    List<Integer> selectDistinctLoanIds(@Param("orderNo") String orderNo);

    /**
     * 根据合作方还款订单号和和合作方标识查询还款信息列表
     * @param orderNo
     * @param partnerCode
     * @return
     */
    List<LnRepay> selectByParOrderNoAndFlag(@Param("orderNo") String orderNo,@Param("partnerCode") String partnerCode);

    /**
     * 是否已存在还款记录
     * @param repayPlanId
     * @param lnPayOrderId
     * @return
     */
    Integer isRepeatRepay(@Param("repayPlanId") Integer repayPlanId,@Param("lnPayOrderId") Integer lnPayOrderId);

    /**
     *
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyRepaySelfForCheckVO> selectForCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 查询某时间段内的还款列表
     * @param partnerCode
     * @param begin
     * @param end
     * @param loanStatus
     * @return
     */
    List<LnRepay> selectYesRepayList(@Param("partnerCode") String partnerCode,@Param("begin") Date begin,@Param("end") Date end,@Param("loanStatus") String loanStatus);

    /**
     * 获取赞时贷还款对账单数据
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<ZsdRepayForCheckVO> selectForZsdCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 资产方线下还款对账 列表信息
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @param start
     * @param numPerPage
     * @return
     */
    List<PartnerOfflineRepaymentVO> selectPartnerOfflineRepaymentInfo(@Param("partnerCode") String partnerCode, @Param("startTime") String startTime,
                                                                      @Param("endTime") String endTime,  @Param("start") Integer start,
                                                                      @Param("numPerPage") Integer numPerPage);

    /**
     * 资产方线下还款对账 记录统计
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @return
     */
    int selectPartnerOfflineRepaymentCount(@Param("partnerCode") String partnerCode, @Param("startTime") String startTime,
                                           @Param("endTime") String endTime);
    /**
     * 查询线下还款对账数据（赞时贷）
     * @return
     */
	List<LnRepay> selectOfflineRepayCheckAccountData();

    /**
     * 对应账单是否有还款进行中
     * @param repayScheId
     */
    Boolean isBillRepaying(@Param("repayScheId") Integer repayScheId);

     /* 生成七贷还款对账单
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyRepaySelfForCheckVO> selectForDepSevenCheck(@Param("time") String time,@Param("start") int start,@Param("numPerPage") int numPerPage);
     
	 /**
     * 查询所有资产方线下还款对账数据
     * @return
     */
	List<LnRepay> selectOfflineRepayCheckAccountInfo();
	
	/**
     * 查询所有资产方线下还款对账数据
     * @return
     */
	List<LnRepayCheckAccountVO> selectOfflineRepayCheckInfo();

    /**
     * 日常业务-借款账单管理，记录数统计
     * @param record
     * @return
     */
    int selectLoanBillCount(LoanBillVO record);

    /**
     * 日常业务-借款账单管理，列表数据查询
     * @param record
     * @return
     */
    List<LoanBillVO> selectLoanBillList(LoanBillVO record);
    
}