package com.pinting.business.dao;

import com.pinting.business.accounting.loan.model.LnRepayScheduleVO;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnRepaySchedule;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.model.vo.*;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LnRepayScheduleMapper {
    long countByExample(LnRepayScheduleExample example);

    int deleteByExample(LnRepayScheduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnRepaySchedule record);

    int insertSelective(LnRepaySchedule record);

    List<LnRepaySchedule> selectByExample(LnRepayScheduleExample example);

    LnRepaySchedule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnRepaySchedule record, @Param("example") LnRepayScheduleExample example);

    int updateByExample(@Param("record") LnRepaySchedule record, @Param("example") LnRepayScheduleExample example);

    int updateByPrimaryKeySelective(LnRepaySchedule record);

    int updateByPrimaryKey(LnRepaySchedule record);

    /**
     * 根据借款id 统计状态为非init的条数
     * @param loanId
     * @return
     */
    int countByLoanIdNotInit(@Param("loanId")Integer loanId);

    /**
     * 查询当前逾期的还款计划
     * @return
     */
    List<RepayScheduleVO> selectOverdueRepaySchedules();

    /**
     * 查询未代偿的计划还款编号
     */
    List<RepayScheduleVO> selectNotCompensated(@Param("excludePartnerCode") String excludePartnerCode);

    /**
     * 查询未代偿的合作方
     * @return
     */
    List<String> selectNotCompensatedPartner(@Param("excludePartnerCode") String excludePartnerCode);

    /**
     * 查询合作方未代偿明细
     * */
    List<RepayScheduleVO> selectNotCompensatedDetail(@Param("planDate") String planDate, @Param("partnerCode") String partnerCode);
    /**
     * 根据条件查询逾期未还款的条数
     * @param loanUserId 借款用户编号， 不能为空
     * @param loanId 借款编号， 可以为空
     * @param serialId 期次， 可以为空
     * @return
     */
    Integer countLateNot(@Param("loanUserId")Integer loanUserId,
    		@Param("loanId")Integer loanId, @Param("serialId")Integer serialId);

    /**
     * 根据借款编号查询管费、信息服务费、账户管理费、保费(赞分期App)
     * @param loanId 借款编号
     * @return
     */
    List<RepayScheduleVO> selectFeeByLoanId(@Param("loanId") Integer loanId);

    /**
     * 根据loanId查询还款计划
     * @param loanId 借款编号
     * @return
     */
    List<RepaySchedulePlanVO> selectRepaySchedulePlanList(@Param("loanId") Integer loanId);
    
    //-------------------------管理台业务---------------------

    /**
     * 保证金代收代付-列表查询-赞分期
     * @param queryVo
     * @return
     */
    List<MDepositDsDfResVO> selectDepositDsDfList(MDepositDsDfQueryVO queryVo);

    /**
     * 保证金代收代付-条数查询-赞分期
     * @param queryVo
     * @return
     */
    Integer countDepositDsDfList(MDepositDsDfQueryVO queryVo);

    /**
     * 保证金代收代付-代收代付总金额查询-赞分期
     * @param queryVo
     * @return
     */
    MDepositDsDfResVO sumAmount4DsDf(MDepositDsDfQueryVO queryVo);
    
    
    /**
     * 保证金代收代付-列表查询-赞时贷
     * @param queryVo
     * @return
     */
    List<MDepositDsDfResVO> selectDepositDsDfList4ZSD(MDepositDsDfQueryVO queryVo);

    /**
     * 保证金代收代付-条数查询-赞时贷
     * @param queryVo
     * @return
     */
    Integer countDepositDsDfList4ZSD(MDepositDsDfQueryVO queryVo);

    /**
     * 保证金代收代付-代收代付总金额查询-赞时贷
     * @param queryVo
     * @return
     */
    MDepositDsDfResVO sumAmount4DsDf4ZSD(MDepositDsDfQueryVO queryVo);

    /**
     * 根据借款id查询最近一次利息还款的还款时间，及借款的成功时间
     * @param loanId
     * @param planDate
     * @param schedulId  LnRepaySchedule.id
     * @return
     */
    LnRepayScheduleVO selectVoByLoanId(@Param("loanId") Integer loanId, @Param("planDate") Date planDate, @Param("schedulId") Integer schedulId);

    /**
     * 根据借款id，当期还款计划时间，查询上一期还款计划的还款时间，及借款的成功时间
     * @param loanId
     * @param planDate
     * @return
     */
    LnRepayScheduleVO selectLnRepayScheduleVOByLoanId(@Param("loanId") Integer loanId, @Param("planDate") Date planDate);

    List<RepayBillVO> selectRepayBill(Map<String, Object> params);

    /**
     * 获取计划还款最后一期合作方还款编号
     * @param loanId
     * @return
     */
	String selectLastPeriodPartnerRepayId(@Param("loanId") Integer loanId);

    /**
     * 代偿协议债转列表-根据合作方借款编号查询最后一笔还款计划的本金，及借款本金期限
     * @param loanId 借款编号
     * @return
     */
    CompensateTransfersVO selectCompensateTransfersByLoanId(@Param("loanId") Integer loanId);
    //=========================管理台-云贷自主放款无账单借款主动获取账单S==================================
    /**
     * 管理台-云贷自主放款无账单借款主动获取账单
     * 查询云贷自主放款没有账单的借款信息列表数量
     * @return
     */
	Integer countLoanNoBillData();
	
	/**
     * 管理台-云贷自主放款无账单借款主动获取账单
     * 查询云贷自主放款没有账单的借款信息列表数量，新增时间查询
     * @return
     */
	Integer countLoanNoBillData(@Param("startTime") String startTime, @Param("endTime") String endTime);
	/**
     * 管理台-云贷自主放款无账单借款主动获取账单
     * 查询云贷自主放款没有账单的借款信息列表
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<YunDaiSelfBillVO> queryLoanNoBillData(@Param("start") Integer start,@Param("numPerPage") Integer numPerPage);
	
	/**
     * 管理台-云贷自主放款无账单借款主动获取账单
     * 查询云贷自主放款没有账单的借款信息列表，新增时间查询
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<YunDaiSelfBillVO> queryLoanNoBillData(@Param("start") Integer start,@Param("numPerPage") Integer numPerPage,
			@Param("startTime") String startTime, @Param("endTime") String endTime);
	
	//=========================管理台-云贷自主放款无账单借款主动获取账单E==================================
	 List<LnCustomerSettlementVO> selectLnCustomerSettlementList(@Param("fnUserName") String fnUserName, @Param("mobile") String mobile, @Param("repayType") String repayType,
             @Param("startDate") String startDate, @Param("endDate") String endDate, @Param("partnerCode") String partnerCode, @Param("queryPartnerBusinessFlag")String queryPartnerBusinessFlag,
             @Param("numPerPage") Integer numPerPage, @Param("start") Integer start);

	int countLnCustomerSettlementList(@Param("fnUserName") String fnUserName, @Param("mobile") String mobile, @Param("repayType") String repayType,
	@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("partnerCode") String partnerCode, @Param("queryPartnerBusinessFlag")String queryPartnerBusinessFlag);
	
	Double sumLnCustomerSettlementInterest(@Param("fnUserName") String fnUserName, @Param("mobile") String mobile, @Param("repayType") String repayType,
	@Param("startDate") String startDate, @Param("endDate") String endDate, @Param("partnerCode") String partnerCode, @Param("queryPartnerBusinessFlag")String queryPartnerBusinessFlag);
	
	Double lnCustomerSettlementInterest(@Param("repayScheduleId") Integer repayScheduleId, @Param("repayType") String repayType);
	
	/**
	 * 查询账单还款，是否还部分本金情况
	 * @param loanId
	 * @param partnerRepayId
	 * @return
	 */
	RepayScheduleVO selectRepaySchedulePrincipal(@Param("loanId")Integer loanId, @Param("partnerRepayId")String partnerRepayId);

    /**
     * 代偿协议-根据代偿单号查询 云贷收款确认函债转/服务费 债权转让列表数据
     * @param orderNo 代偿单号
     * @return
     */
    List<CompensateTransfersVO> selectYunCollectionLettersByOrderNo(@Param("orderNo") String orderNo);

    /**
     * 代偿协议-根据代偿单号查询 7贷收款确认函债转/服务费 债权转让列表数据
     * @param orderNo 代偿单号
     * @return
     */
    List<CompensateTransfersVO> selectSevenCollectionLettersByOrderNo(@Param("orderNo") String orderNo);
    
    /**
     * 根据借款用户id查询该用户所有借款成功未还的本金（代偿表示已还，不统计）
     * @param lnUserId
     * @return
     */
    Double sumNotRepayByLnUserId(@Param("lnUserId")Integer lnUserId);

    /**
     * 代偿协议-根据代偿单号查询 云贷收款确认函债转/服务费 债权转让列表数据，代偿协议重新生成合规前的版本数据查询调用
     * @param orderNo 代偿单号
     * @return
     */
    List<CompensateTransfersVO> selectYunCollectionLettersByOrderNoRenew(@Param("orderNo") String orderNo);
    
    /**
     * 根据借款id查询已还本金
     * @author bianyatian
     * @param loanId
     * @return
     */
    Double sumRepaiedByLoanId(@Param("loanId")Integer loanId);
    
    /**
     * 根据借款id查询非废除状态的账单及对应明细
     * @author bianyatian
     * @param loanId
     * @return
     */
    List<RepaySchedule4DetailVO> selectAllSchedule(@Param("loanId")Integer loanId);
    
    /**
     * 根据借款id查询最后账单日
     * @param loanId
     * @return
     */
    LnRepaySchedule lastPlanByLoanId(@Param("loanId")Integer loanId);
    
    /**
     * 根据代偿信息查询对应账单状态
     * @return
     */
    LnRepaySchedule selectRepayScheduleWithCompensate(@Param("partnerRepayId") String partnerRepayId); 
    
    /**
     * 异常订单处理，信息回显
     * @param partnerRepayIds
     * @param payOrderNo
     * @return
     */
	int selectRepayScheduleWithCompensateCount(@Param("partnerRepayIds") List<String> partnerRepayIds, @Param("payOrderNo") String payOrderNo); 
	
	/**
	 * 异常订单处理，信息回显
	 * @param partnerRepayIds
	 * @param payOrderNo
	 * @return
	 */
	Double selectRepayScheduleWithCompensateSum(@Param("partnerRepayIds") List<String> partnerRepayIds, @Param("payOrderNo") String payOrderNo); 
    
    /**
     * 根据代偿信息查询对应账单状态
     * @return
     */
    LnRepaySchedule selectRepayScheduleWithCompensateAndOrderNo(@Param("partnerRepayId") String partnerRepayId, @Param("payOrderNo") String payOrderNo); 
    
    /**
     * 公众号统计  应还笔数
     * @return
     */
    int queryDailyCount(String partnerCode);

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