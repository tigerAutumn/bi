package com.pinting.business.dao;

import com.pinting.business.model.BsRevenueTransDetail;
import com.pinting.business.model.BsRevenueTransDetailExample;
import com.pinting.business.model.vo.LnAccountFillDetailVO;
import com.pinting.business.model.vo.RevenueTransDetailVO;

import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BsRevenueTransDetailMapper {
    long countByExample(BsRevenueTransDetailExample example);

    int deleteByExample(BsRevenueTransDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsRevenueTransDetail record);

    int insertSelective(BsRevenueTransDetail record);

    List<BsRevenueTransDetail> selectByExample(BsRevenueTransDetailExample example);

    BsRevenueTransDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsRevenueTransDetail record, @Param("example") BsRevenueTransDetailExample example);

    int updateByExample(@Param("record") BsRevenueTransDetail record, @Param("example") BsRevenueTransDetailExample example);

    int updateByPrimaryKeySelective(BsRevenueTransDetail record);

    int updateByPrimaryKey(BsRevenueTransDetail record);

    /**
     * 求某时间区间营收扣除（REVENUE_DEDUCT）的和
     * @param startTime
     * @param endTime
     * @return 返回0 或 负数
     */
    Double sumDeductAmount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 求某时间区间正常还款时营收收入（REVENUE_INCOME）的和
     * @param startTime
     * @param endTime
     * @param partnerCode 合作方
     * @return
     */
    Double sumIncomeAmount(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
                           @Param("partnerCode") String partnerCode);

    /**
     * 当日砍头息总计
     * @param today
     * @param partnerCode
     * @param transType
     */
    Double sumFeeAmountDaily(@Param("today") Date today,@Param("partnerCode") String partnerCode,
                             @Param("transType") String transType);
    
    /**
     * 云贷砍头息代收代付-列表
     * @param startTime
     * @param endTime
     * @param userName
     * @param mobile
     * @param transType
     * @param numPerPage
     * @param start
     * @return
     */
    List<RevenueTransDetailVO> selectYunHeadFeeList(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                                    @Param("userName") String userName, @Param("mobile") String mobile,
                                                    @Param("transType") String transType, @Param("numPerPage") Integer numPerPage,
                                                    @Param("start") Integer start);

    /**
     * 云贷砍头息代收代付-统计
     * @return
     */
    long countYunHeadFee(@Param("startTime") String startTime, @Param("endTime") String endTime,
                         @Param("userName") String userName, @Param("mobile") String mobile,
                         @Param("transType") String transType);

    /**
     * 云贷砍头息代收-总额
     * @return
     */
    Double sumFeeOfDS(@Param("startTime") String startTime, @Param("endTime") String endTime,
                      @Param("userName") String userName, @Param("mobile") String mobile,
                      @Param("transType") String transType);

    /**
     * 云贷砍头息代付-总额
     * @return
     */
    Double sumFeeOfDF(@Param("startTime") String startTime, @Param("endTime") String endTime,
                      @Param("userName") String userName, @Param("mobile") String mobile,
                      @Param("transType") String transType);
    /**
     * 求赞分期某时间区间逾期线下还款营收（OVERDUE_REVENUE_INCOME）的和
     * @param addDays
     * @param dateEnd
     * @return
     */
	Double sumOverdueRevenueAmount(@Param("startTime") Date startTime, @Param("endTime") Date endTime);

    /**
     * 求某时间区间营收扣除（REVENUE_DEDUCT）的和
     * @param startTime
     * @param endTime
     * @return 返回0 或 负数
     */
    Double sumDeductAmountByTimeAndType(@Param("startTime") Date startTime, @Param("endTime") Date endTime,
    		@Param("transType") String transType, @Param("partnerCode") String partnerCode);

    /**
     * 昨日营收明细
     * @param yesterday
     * @param partnerCode
     * @return
     */
    List<LnAccountFillDetailVO> getList(@Param("yesterday") Date yesterday, @Param("partnerCode") String partnerCode);
}