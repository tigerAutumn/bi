package com.pinting.business.dao;

import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.dto.LoanDTO;
import com.pinting.business.model.dto.StagingProductsLoanDTO;
import com.pinting.business.model.vo.*;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface LnLoanMapper {
    int countByExample(LnLoanExample example);

    int deleteByExample(LnLoanExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnLoan record);

    int insertSelective(LnLoan record);

    List<LnLoan> selectByExample(LnLoanExample example);

    LnLoan selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnLoan record, @Param("example") LnLoanExample example);

    int updateByExample(@Param("record") LnLoan record, @Param("example") LnLoanExample example);

    int updateByPrimaryKeySelective(LnLoan record);

    int updateByPrimaryKey(LnLoan record);

    /**
     * 根据合作方借款编号、合作方编码 查询借款信息-借款协议数据准备
     * @param partnerLoanId 合作方借款编号
     * @param partnerCode 合作方编码
     * @return
     */
    LoanDetailInfoVO selectLoanDetailInfo(@Param("partnerLoanId")String partnerLoanId, @Param("partnerCode")String partnerCode);
    
    /**
     * 根据合作方借款编号、合作方编码 查询借款信息-借款协议数据准备
     * @param partnerLoanId 合作方借款编号
     * @param partnerCode 合作方编码
     * @return
     */
    LoanDetailInfoVO selectYunLoanDetailInfo(@Param("partnerLoanId")String partnerLoanId, @Param("partnerCode")String partnerCode);
    
    /**
     * 根据合作方借款编号、合作方编码 查询借款信息-借款协议数据准备
     * @param partnerLoanId 合作方借款编号
     * @param partnerCode 合作方编码
     * @return
     */
    LoanDetailInfoVO selectSevenLoanDetailInfo(@Param("partnerLoanId")String partnerLoanId, @Param("partnerCode")String partnerCode);

    /**
     * 根据借款id，查询本金和利息的计划金额
     * @param loanId
     * @return
     */
    Double selectPlanPIByLoanId(@Param("loanId")Integer loanId);

    /**
     * 查询借款对账明细
     * @return
     */
    List<LnLoanForCheckVO> selectLnLoanForCheck();

    /**
     * 根据借款表id查询借款年化利率,值为万分之xx
     * @param loanId
     * @return
     */
    Double selectLoanInterestRate(@Param("loanId")Integer loanId);

    /**
     * 根据借款表id查询月偿还本息数额、借款基本信息
     * @param loanId
     * @return
     */
    LoanDetailInfoVO selectLoanInfoByLoanId(@Param("loanId")Integer loanId);

    /**
     * 根据合作方借款编号 查询借款信息-借款id
     * @param partnerLoanId 合作方借款编号
     * @return
     */
    LnLoan selectLoanIdByPartnerLoanId(@Param("partnerLoanId") String partnerLoanId);

    /**
     * 查询借款
     * @param loanDTO
     * @return
     */
    List<LoanVO> selectLoanByDTO(LoanDTO loanDTO);

    /**
     * 查询借款
     * @param loanDTO
     * @return
     */
    int selectLoanByDTOCount(LoanDTO loanDTO);

    /**
     * 品听-分期产品出借查询 列表
     * @param record
     * @return
     */
    List<StagingProductsLoanVO> selectPTStagingProductsLoan(StagingProductsLoanVO record);

    /**
     * 品听-分期产品出借查询 统计
     * @param record
     * @return
     */
    int selectPTStagingProductsLoanCount (StagingProductsLoanVO record);


    /**
     * 钱报-分期产品出借查询 列表
     * @param dto
     * @return
     */
    List<StagingProductsLoanVO> selectQBStagingProductsLoan(StagingProductsLoanDTO dto);

    /**
     * 钱报-分期产品出借查询 统计
     * @param dto
     * @return
     */
    int selectQBStagingProductsLoanCount(StagingProductsLoanDTO dto);

    /**
     *杭商-分期产品出借查询 列表
     * @param record
     * @return
     */
    List<StagingProductsLoanVO> selectHSStagingProductsLoan(StagingProductsLoanVO record);

    /**
     * 杭商-分期产品出借查询 统计
     * @param record
     * @return
     */
    int selectHSStagingProductsLoanCount (StagingProductsLoanVO record);

    /**
     * 根据借款时间分组查询借款总金额
     * @param beginDate
     * @param endDate
     * @return
     */
    List<DailyAmount4LoanVO> getLoanSumAmount(@Param("beginDate")String beginDate,
    		@Param("endDate")String endDate);

    /**
     * 自主放款- 合作方借款订单号是否存在
     * @param orderNo
     * @param channel
     * @return
     */
    Integer selectByOrderNoAndPartnerCode(@Param("orderNo") String orderNo, @Param("channel") String channel);

    /**
     * 自动放款- 合作方借款记录是否存在
     * @param orderNo
     * @param partnerCode
     * @param partnerCode
     * @return
     */
    LnLoan selectByParOrdNoAndParCode(@Param("orderNo") String orderNo,@Param("partnerCode") String partnerCode);


    /**
     * 自主放款- 合作方借款编号是否存在
     * @param loanId
     * @param channel
     * @return
     */
    List<LnLoan> selectByLoadIdAndPartnerCode(@Param("loanId") String loanId,@Param("channel") String channel);


    /**
     * 根据合作方借款信息 查询借款信息-借款id
     * @param partnerLoanId 合作方借款编号
     * @return
     */
    LnLoan selectLoanByPartnerLoanId(@Param("partnerLoanId") String partnerLoanId);

    /**
     *  重复还款文件明细列表
     * @param today
     * @return
     */
    List<RevenueSettleDetailVO> selectRepeatRepayDetail(@Param("today") Date today, @Param("partner") String partner);

    /**
     * 自动放款- 还款营收文件明细列表
     * @param today
     * @return
     */
    List<RevenueSettleDetailVO> selectRepayRevenueDetail(@Param("today") Date today, @Param("partner") String partner);

    /**
     * 自动放款- 借款手续费文件明细列表
     * @param today
     * @return
     */
    List<RevenueSettleDetailVO> selectLoanFeeDetail(@Param("today") Date today, @Param("partner") String partner);

    /**
     * 自动放款- 还款营收文件明细列表，合作方7贷取营收结算金额
     * @param today
     * @return
     */
    List<RevenueSettleDetailVO> selectSevenRepayRevenueDetail(@Param("today") Date today, @Param("partner") String partner);
  
    /**
     * 生成借款+借款手续费对账单查询数据
     * @param time          时间
     * @param start         开始
     * @param numPerPage    每页显示条数
     * @return
     */
    List<DafyLoanSelfForCheckVO> selectForCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    
    /**
     * 当日申请的非失败的及当日成功的借款表总金额
     * @return
     */
    Double selectTodayNotFillAmount();
    
    /**
     * 生成借款+借款手续费对账单查询数据
     * @param time          时间
     * @param start         开始
     * @param numPerPage    每页显示条数
     * @return
     */
    List<DafyLoanSelfForCheckVO> selectForDepSevenCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 生成借款+借款手续费对账单查询数据-赞时贷
     * @param time          时间
     * @param start         开始
     * @param numPerPage    每页显示条数
     * @return
     */
    List<DafyLoanSelfForCheckVO> selectForZsdCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
    
    /**
     * 重复还款对账-赞时贷
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyLoanSelfForCheckVO> selectZsdRepeatRepayCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
    
    
    /**
     * 砍头息求和
     * @param status
     * @return
     */
    Double selectHeadFeeSum(@Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime); 
    
    /**
     * 放款日常管理 列表
     * @param record
     * @return
     */
    List<LoanDailyVO> selectLoanDailyList(LoanDailyVO record);

    /**
     * 放款日常管理 统计
     * @param record
     * @return
     */
    int selectLoanDailyCount(LoanDailyVO record);

    /**
     * 放款日常管理 赞分期资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    Double selectZanDailyTotalAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 放款日常管理 云贷资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    Double selectYunDaiDailyTotalAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 放款日常管理 赞时贷资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    Double selectZsdDailyTotalAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 放款日常管理 7贷资金计划总量
     * @param startTime
     * @param endTime
     * @return
     */
    Double selectSevenDailyTotalAmount(@Param("startTime") String startTime, @Param("endTime") String endTime);

    /**
     * 放款日常管理 放款成功(ln_loan status 'PAIED')的金额、笔数统计
     * @param partnerCode 资产合作方编码
     * @param startTime
     * @param endTime
     * @return
     */
    LoanDailyStatisticsVO selectLoanDailyPaiedStatistics(@Param("partnerCode") String partnerCode,
                                                         @Param("startTime") String startTime,
                                                         @Param("endTime") String endTime);

    /**
     * 放款日常管理 放款处理中(ln_loan status 'PAYING', 'CHECKED')的金额、笔数统计
     * @param partnerCode 资产合作方编码
     * @param startTime
     * @param endTime
     * @return
     */
    LoanDailyStatisticsVO selectLoanDailyPayingStatistics(@Param("partnerCode") String partnerCode,
                                                          @Param("startTime") String startTime,
                                                          @Param("endTime") String endTime);

    /**
     * 云贷存管-财务投资购买查询 当日匹配总金额
     * @param buyBeginTime
     * @param buyEndTime
     * @param propertySymbol
     * @return
     */
    Double selectMatchTotalAmountForDay(@Param("partnerCode")String partnerCode);

    /**
     * 赞时贷借款协议 到期偿还本息数额
     * @param loanId
     * @return
     */
    LoanDetailInfoVO selectZsdPrincipalInterest(@Param("loanId")Integer loanId);


    /**
     * (云贷、赞时贷)砍头息代收代付
     * @param partnerCode
     * @param userName
     * @param mobile
     * @param type
     * @param startTime
     * @param endTime
     * @param position
     * @param offset
     * @return
     */
    List<HeadFeeCollectPayVO> headFeeCollectPayList(@Param("partnerCode") String partnerCode,
                                                    @Param("userName") String userName,
                                                    @Param("mobile") String mobile,
                                                    @Param("type") String type,
                                                    @Param("startTime") String startTime,
                                                    @Param("endTime") String endTime,
                                                    @Param("position") Integer position,
                                                    @Param("offset") Integer offset);

    /**
     * (云贷、赞时贷)砍头息代收代付计数
     * @param partnerCode
     * @param userName
     * @param mobile
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    Integer headFeeCollectPayCount(@Param("partnerCode") String partnerCode,
                                   @Param("userName") String userName,
                                   @Param("mobile") String mobile,
                                   @Param("type") String type,
                                   @Param("startTime") String startTime,
                                   @Param("endTime") String endTime);


    /**
     * (云贷、赞时贷)砍头息代收代付总额
     * @param partnerCode
     * @param userName
     * @param mobile
     * @param type
     * @param startTime
     * @param endTime
     * @return
     */
    HeadFeeCollectPayVO headFeeCollectPayTotalAmount(@Param("partnerCode") String partnerCode,
                                                     @Param("userName") String userName,
                                                     @Param("mobile") String mobile,
                                                     @Param("type") String type,
                                                     @Param("startTime") String startTime,
                                                     @Param("endTime") String endTime);
    
    /**
     * 查询账单不存在且成功的放款
     */
    List<LnLoan> selectBillNotExist();
    
    /**
     * 查询赞分期账单计划还款时间在某时间之后的状态为未还（INIT），
     * 	根据partnerRepayId left join LnDepositionRepaySchedule表查询无线下还款记录的账单信息，根据借款id和期数排序
     * @author bianyatian
     * @param planDate
     * @return
     */
    List<LnLoanRepayScheduleVO> selectZanNotRepayReturn(@Param("planDate") Date planDate);
    
   
}

