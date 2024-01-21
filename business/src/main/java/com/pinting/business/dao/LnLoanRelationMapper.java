package com.pinting.business.dao;

import com.pinting.business.accounting.loan.model.LoanRelation4TransferVO;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnLoanRelationExample;
import com.pinting.business.model.vo.*;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface LnLoanRelationMapper {
    int countByExample(LnLoanRelationExample example);

    int deleteByExample(LnLoanRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnLoanRelation record);

    int insertSelective(LnLoanRelation record);

    List<LnLoanRelation> selectByExample(LnLoanRelationExample example);

    LnLoanRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnLoanRelation record, @Param("example") LnLoanRelationExample example);

    int updateByExample(@Param("record") LnLoanRelation record, @Param("example") LnLoanRelationExample example);

    int updateByPrimaryKeySelective(LnLoanRelation record);

    int updateByPrimaryKey(LnLoanRelation record);

    /**
     * 查询债权明细
     * @param userId        理财人用户ID
     * @param subAccountId  理财人投资子账户ID
     * @return
     */
    List<LnLoanRelationVO> selectByBsUserId(@Param("userId") int userId, @Param("subAccountId") int subAccountId, @Param("start") int start, @Param("numPerPage") int numPerPage);

    /**
     * 根据状态统计总利息
     * @param userId        理财人用户ID
     * @param subAccountId  理财人投资子账户ID
     * @param status        还款状态
     * @return
     */
    double sumPlanTotalInterest(@Param("userId") int userId, @Param("subAccountId") int subAccountId, @Param("status") String status);


    /**
     * 根据超级用户列表查询剩余金额大于0的列表
     * 排序规则：借款时间正序，金额由大到小
     * @param userIdList
     * @return
     */
    List<LoanRelationVO> getSuperLnLoanRelationList(@Param("userIdList")List<Integer> userIdList);

    /**
     * 根据借贷关系ID查询借款人信息
     * @param loanRelationId 借贷关系ID
     * @return
     */
    LnLoanRelationVO selectByLoanRelationId(@Param("loanRelationId") int loanRelationId);

    /**
     * 根据借贷关系ID查询站岗户编号
     * @param loanRelationId 借贷关系ID
     * @return
     */
    LnLoanRelationVO selectAuthAccountId(@Param("loanRelationId") int loanRelationId);

    /**
     * 根据借款id查询出借人信息-借款协议数据准备
     * @param loanId
     * @return
     */
    List<BsUser4LoanVO> selectBsUserByLoanId(@Param("loanId") int loanId);

    /**
     * 还款成功时剩余金额减少本金
     * @param record
     */
    void repayPrincipalById(LnLoanRelation record);

    /**
     * 根据借贷关系ID查询理财人信息
     * @param loanRelationId 借贷关系ID
     * @return
     */
    LnLoanRelationVO selectUserByRelationId(@Param("loanRelationId") int loanRelationId);

    /**
     * 根据借款用户编号查询对应的理财人信息,以及借款人在该理财人身上的借款金额、借款期限
     * @param lnUserId 借款用户编号
     * @return
     */
    List<LnLoanRelationVO> selectUserByLnUserId(@Param("lnUserId") int lnUserId);

    /**
     * 根据借款ID查询这一笔借款对应的理财人数据
     * @param loanId
     * @return
     */
    List<LnLoanRelationVO> selectUserByLoanId(@Param("loanId") int loanId);

    /**
     * 根据站岗子账户ID获得出借受让金额
     * @param authAccountId 站岗子账户ID
     * @return
     */
    Double sumTotalAmountByAuthId(@Param("authAccountId") int authAccountId);
    /**
     * 根据站岗子账户ID获得剩余出借受让金额
     * @param authAccountId 站岗子账户ID
     * @return
     */
    Double sumLeftAmountByAuthId(@Param("authAccountId") int authAccountId);

    /**
     * 查询某日（create_time）借款成功或已还的金额，按投资期限统计--不包括vip
     * @param beginDate
     * @param endDate
     * @param userIdList vip
     * @return
     */
    List<DailyAmount4LoanVO> getLoanSumAmount(@Param("beginDate")String beginDate,
    		@Param("endDate")String endDate, @Param("userIdList")List<Integer> userIdList);

    /**
     * 查询VIP当前持有债权金额，按投资期限统计
     * @param userIdList
     * @return
     */
    List<DailyAmount4LoanVO> getVIPLoanedSumAmount(@Param("userIdList")List<Integer> userIdList);

    /**
     * 根据用户id,和借款表id查询借贷关系表的记录，用来判断借款协议、债权转让的协议loanRelationId是否属于当前登录的用户
     * @param paramMap
     * @return
     */
    LnLoanRelation selectRecordByUserIdAndId(Map<String, Object> paramMap);


    /**
     * 根据时间查询应付奖励金的列表
     * @param paramMap
     * @return
     */
    List<Map<String, Object>> selectNeedPayBonusGrant(Map<String, Object> paramMap);

    /**
     *
     * 判断是否完成出借，0-完成，-1-未完成
     * @param authId
     * @return
     */
    Map<String, Object> isFinishLoan(@Param("authId") Integer authId);

    /**
     * 根据loanId查询出借人列表
     * @param loanId 借款编号
     * @return
     */
    List<LnLoanRelationVO> selectLendersByLoanId(@Param("loanId") Integer loanId);

    /**
     * 根据借贷关系表查询现有债权的总借款本金--relation的leftAmount
     * @param loanId
     * @return
     */
    Double getRelationAmountByLoanId(@Param("loanId") Integer loanId);
    
    /**
     * 根据借贷关系表查询现有债权的总借款本金--relation的leftAmount - 理财人回款计划表的还款中金额
     * @param loanId
     * @return
     */
    Double getRelationAmountNotRepayingByLoanId(@Param("loanId") Integer loanId);

    
    /**
     * 根据借贷关系表查询现有债权的总借款本金--relation的leftAmount
     * @param loanId
     * @param status
     * @return
     */
    Double getRelationAmountByLoanIdAndStatus(@Param("loanId") String loanId, @Param("status") String status);
    
    /**
     * 根据bsSubAccountId查询对应的债权关系列表
     * 根据借款loanId查询对应的债权关系列表
     * @param bsSubAccountId
     * @param loanId
     * @return
     */
    List<LoanRelation4TransferVO> getRelationList(@Param("bsSubAccountId")Integer bsSubAccountId, @Param("loanId") Integer loanId);
    
    
    /**
     * 七贷还款或债权转让-获取债权列表
     * 根据bsSubAccountId查询对应的债权关系列表
     * 根据借款loanId查询对应的债权关系列表
     * @param bsSubAccountId
     * @param loanId
     * @return
     */
    List<LoanRelation4TransferVO> getSevenRelationList(@Param("bsSubAccountId")Integer bsSubAccountId, @Param("loanId") Integer loanId);
    
    /**
     * 根据bsSubAccountId查询对应的债权关系列表的剩余金额总额
     * @param bsSubAccountId
     * @return
     */
    Double sumLeftAmount(@Param("bsSubAccountId")Integer bsSubAccountId);
    
    /**
     * 根据VIP用户列表查询对应的债权关系列表，且为非代偿后接入,查询条数
     * @param vipIdList
     * @return
     */
    int countVIPRelationList(@Param("vipIdList")List<Integer> vipIdList);
    
    
    /**
     * 根据VIP用户列表查询对应的债权关系列表，且为非代偿后接入--分页查询
     * @param vipIdList
     * @return
     */
    List<LoanRelation4TransferVO> getVIPRelationList4Page(@Param("vipIdList")List<Integer> vipIdList,@Param("start")Integer start,
    		@Param("pageSize")Integer pageSize);

    /**
     * 查询回款计划中的待收回款
     * @param userId
     * @return
     */
    List<CashTransferSchemesVO> PaymentPast(@Param("userId") Integer userId);

    /**
     * 回款计划明细查询-无分页
     * @param userId
     * @param planDate
     * @return
     */
    List<CashTransferSchemesVO> selectRepayScheduleDetailsList(@Param("userId") Integer userId, @Param("planDate")String planDate);

    /**
     * 回款计划中的往期回款查询
     * @param record
     * @return
     */
    List<CashTransferSchemesVO> selectPaymentPlantPast(@Param("record") CashTransferSchemesVO record);

    /**
     * 回款计划中详情查询
     * @param record
     * @return
     */
    List<CashTransferSchemesVO> getPaymentPastDetails(@Param("record") CashTransferSchemesVO record);

    /**
     * 回款计划中详情查询条数
     * @param record
     * @return
     */
    Integer getPaymentPlantDetalisCount(@Param("record") CashTransferSchemesVO record);

    /**
     * 回款计划中详情查询条数
     * @param record
     * @return
     */
    List<CashTransferSchemesVO> getDetailsList(@Param("record") CashTransferSchemesVO record);

    /**
     * 代偿协议-债权转让通知书 根据借款ID查询这一笔借款对应的理财人数据
     * @param loanId 借款编号
     * @return
     */
    List<LnLoanRelationVO> selectCompensateUserByLoanId(@Param("loanId") Integer loanId);

    /**
     * 代偿-债权转让协议 根据借款ID查询这一笔借款中最后一笔还款的借贷关系id
     * @param loanId
     * @return
     */
    List<CompensateTransfersVO> selectCompensateLoanRelationIdList(@Param("loanId") Integer loanId);

    /**
     * 代偿-债权转让协议 根据借款ID，借贷关系ID查询这一笔借款中最后一笔还款的债权转让信息
     * @param loanId
     * @param lnLoanRelationId
     * @return
     */
    CompensateTransfersVO selectCompensateTransferInfo(@Param("loanId") Integer loanId, @Param("lnLoanRelationId") Integer lnLoanRelationId);

    /**
     * 代偿-债权转让通知书 根据借款ID查询债权转让列表
     * @param loanId
     * @return
     */
    List<CompensateTransfersVO> selectCompensateTransferList(@Param("loanId") Integer loanId);

    /**
     * 分页查询存管产品(港湾计划)的债权明细
     * @param subAccountId
     * @param start
     * @param numPerPage
     * @return
     */
    List<DetailsOfDebtVO> selectDepClaimsListBySubAccountId(@Param("subAccountId")Integer subAccountId,
                                                             @Param("start")Integer start, @Param("numPerPage")Integer numPerPage);
    /**
     * 存管港湾产品-借款协议 根据借款编号查询理财人数据
     * @param loanId 借款编号
     * @return
     */
    List<LnLoanRelationVO> selectCustodyFinancialManagement(@Param("loanId") Integer loanId);

    /**
     * 存管港湾产品-借款协议-债权转让记录 根据子账户编号查询债权转让记录
     * @param loanId
     * @return
     */
    List<DebtTransferRecordsVO> selectCustodyLoanTransferClaims(@Param("loanId") Integer loanId);
    /**
     * 云贷存管未来30天兑付查询
     * @return
     */
	List<DepCash30VO> depCash30();
	/**
	 * 云贷存管未来30天兑付查询-vip当前债权本金
	 * @return
	 */
	Double depVipDeptsAmount();
	/**
	 * 云贷存管未来30天兑付查询-vip站岗余额
	 * @return
	 */
	Double depVipStandAmount();
	/**
	 * 云贷存管未来30天兑付查询-查询某日预计退出债权利息
	 * @param finishDate
	 * @return
	 */
	Double slectQuitInterestBalanceByDate(@Param("finishDate") Date finishDate);
	

	/**
	 * 用户充值记录
	 *
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	List<UserRechanageStatisticsVO> depUserRechangeStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime,
			@Param("position") Integer position, @Param("offset") Integer offset);

	/**
	 * 用户充值记录总数
	 *
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Integer depUserRechangeStatisticsCount(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime);


	/**
	 * 用户充值总金额
	 *
	 * @param mobile
	 * @param userName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	Double depUserTotalRechangeAmountStatistics(@Param("mobile") String mobile,
			@Param("userName") String userName, @Param("startTime") String startTime, @Param("endTime") String endTime);
	

	/**
	 * 赞时贷未来30天兑付查询-vip当前债权本金
	 * @return
	 */
	Double depVipDeptsZsdAmount();
	/**
	 * 赞时贷未来30天兑付查询-vip站岗余额
	 * @return
	 */
	Double depVipStandZsdAmount();
	
    /**
     * 赞时贷未来30天兑付查询
     * @return
     */
	List<DepCash30VO> zsdDepCash30();
	/**
	 * 赞时贷未来30天兑付查询-查询某日预计退出债权利息
	 * @param finishDate
	 * @return
	 */
	Double slectQuitInterestBalanceByDateZsd(@Param("finishDate") Date finishDate);
	
	/**
     * 查询回款计划中的待收回款
     * @param userId
     * @return
     */
    List<CashTransferSchemesVO> paymentPastPage(@Param("userId") Integer userId, @Param("start")int start, @Param("numPerPage")int pageSize);

    /**
     * 回款计划中的待收回款计数
     * @param userId
     * @return
     */
    int countPaymentPast(@Param("userId") Integer userId);
	
	/**
     * 往期回款查询计数
     * @param userId
     * @return
     */
    int countPaymentPlantPost(@Param("userId") Integer userId, @Param("dateTime")String dateTime);

    /**
     * 分页查询云贷存管产品的债权明细
     * @param subAccountId
     * @param start
     * @param numPerPage
     * @return
     */
    List<DetailsOfDebtVO> selectDepClaimsListBySubAccountIdNew(@Param("subAccountId")Integer subAccountId,
                                                            @Param("start")Integer start, @Param("numPerPage")Integer numPerPage);
    
    /**
     * 分页查询云贷存管产品的债权明细,排除大借款人
     * @param subAccountId
     * @param start
     * @param numPerPage
     * @return
     */
    List<DetailsOfDebtVO> selectDepClaimsListExcludeBigBorrowers(@Param("subAccountId")Integer subAccountId,
                                                            @Param("start")Integer start, @Param("numPerPage")Integer numPerPage);

    /**
     * 根据subAccountId查询债权明细数量 status in (SUCCESS, TRANSFERRED, REPAID)
     * @param subAccountId
     * @return
     */
    int selectDepClaimsCount(@Param("subAccountId")Integer subAccountId);


    /**
     * 查询借款人信息
     * @param partnerCode
     * @param loanRelationId
     * @return
     */
    BorrowerInfoVO selectBorrowerInfo(@Param("partnerCode")String partnerCode, @Param("loanRelationId")Integer loanRelationId);

    /**
     * 赞时贷资金迁移协议 根据loanRelationId查询借款人的资产方
     * @param loanRelationId
     * @return
     */
    CheckLnUserPartnerCodeVO selectCheckLnUserPartnerCode(@Param("loanRelationId")Integer loanRelationId);
    /**
     * 七贷未来30天兑付查询
     * @return
     */
	List<DepCash30VO> loan7DepCash30();
	/**
	 * 七贷未来30天兑付查询-vip当前债权本金
	 * @return
	 */
	Double depVipDeptsLoan7Amount();
	/**
	 * 七贷未来30天兑付查询-vip站岗余额
	 * @return
	 */
	Double depVipStandLoan7Amount();
	/**
	 * 七贷未来30天兑付查询-查询某日预计退出债权利息
	 * @param finishDate
	 * @return
	 */
	Double slectQuitInterestBalanceByDateLoan7(Date finishDate);

    /**
     * 代偿-债权转让通知书 根据借款ID查询债权转让列表 代偿协议重新生成合规前的版本数据查询
     * @param loanId
     * @return
     */
    List<CompensateTransfersVO> selectCompensateTransferListRenew(@Param("loanId") Integer loanId);

    /**
     * 渠道业绩查询-渠道用户查询优化-查询债转金额
     * @return
     */
    List<AgentUserVo> selectRealAmountTransList();

    /**
     * 渠道业绩查询-渠道用户查询优化-查询理财人回款计划表记录数
     * @return
     */
    List<AgentUserVo> selectrepayedPeriodCountList();
    
    
    
    /**
     * 根据bsSubAccountId查询对应的债权关系列表--分页查询
     * 等待进行债权转让
     * @param bsSubAccountId
     * @return
     */
    List<LoanRelation4TransferVO> getRelationListWait2Transfer(@Param("bsSubAccountId")Integer bsSubAccountId,@Param("pageSize")Integer pageSize);
    
    
    /**
     * 根据bsSubAccountId查询对应的债权关系列表的条数
     * 等待进行债权转让
     * @param bsSubAccountId
     * @return
     */
    Integer countRelationListWait2Transfer(@Param("bsSubAccountId")Integer bsSubAccountId);
    

    /* 财务管理-存管未来30天兑付查询-新增自由站岗户业务数据 start */
    /**
     * 自由站岗户-未来30天兑付查询-vip当前债权本金
     * @return
     */
    Double depVipDeptsLoanFreeAmount();

    /**
     * 自由站岗户-未来30天兑付查询-vip站岗余额
     * @return
     */
    Double depVipStandLoanFreeAmount();

    /**
     * 自由站岗户-未来30天兑付查询
     * @return
     */
    List<DepCash30VO> loanFreeDepCash30();

    /* 2018财务管理-存管未来30天兑付查询-新增自由站岗户业务数据 end */
    
    
    /**
     * 云贷-等额本息-还款-获取债权列表
     * 根据借款loanId查询对应的债权关系列表
     * @param loanId
     * @return
     */
    List<LoanRelation4TransferVO> getYunFixedInstallmentRelationList( @Param("loanId") Integer loanId);
    
    /**
     * 根据借款ID获得剩余借款总本金
     * @param loanId ln_loan.id
     * @return
     */
    Double sumLeftAmountByLoanId(@Param("loanId") int loanId);

    /**
     * 平台存量数据-待还余额本金
     * @return
     */
    Double sumLoanRepayBalance(@Param("endTime") String endTime, @Param("partnerCode") String partnerCode);

    /**
     * 平台存量数据-待还余额本金
     * @return
     */
    Double sumLoanReturnRepayBalance(@Param("endTime") String endTime);
}