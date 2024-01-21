package com.pinting.business.dao;

import com.pinting.business.model.LnDepositionRepayScheduleDetail;
import com.pinting.business.model.LnDepositionRepayScheduleDetailExample;
import com.pinting.business.model.vo.LnLoanServiceFeeVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnDepositionRepayScheduleDetailMapper {
    long countByExample(LnDepositionRepayScheduleDetailExample example);

    int deleteByExample(LnDepositionRepayScheduleDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnDepositionRepayScheduleDetail record);

    int insertSelective(LnDepositionRepayScheduleDetail record);

    List<LnDepositionRepayScheduleDetail> selectByExample(LnDepositionRepayScheduleDetailExample example);

    LnDepositionRepayScheduleDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnDepositionRepayScheduleDetail record, @Param("example") LnDepositionRepayScheduleDetailExample example);

    int updateByExample(@Param("record") LnDepositionRepayScheduleDetail record, @Param("example") LnDepositionRepayScheduleDetailExample example);

    int updateByPrimaryKeySelective(LnDepositionRepayScheduleDetail record);

    int updateByPrimaryKey(LnDepositionRepayScheduleDetail record);
    
    /**
     * 存管产品统计 - 借款服务费计数
     * @param userName
     * @param mobile
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    Integer countLoanServiceFee(@Param("userName") String userName, @Param("mobile") String mobile,
    		@Param("partnerCode") String partnerCode, @Param("startTime") String startTime,
            @Param("endTime") String endTime, @Param("partnerBusinessFlag") String partnerBusinessFlag);
    
    /**
     * 存管产品统计 - 借款服务费列表
     * @param userName
     * @param mobile
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    List<LnLoanServiceFeeVO> selectLoanServiceFeeList(@Param("userName") String userName, @Param("mobile") String mobile,
    		@Param("partnerCode") String partnerCode, @Param("startTime") String startTime, @Param("endTime") String endTime,
            @Param("partnerBusinessFlag") String partnerBusinessFlag,
    		@Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
    
    /**
     * 存管产品统计 - 借款服务费求和
     * @param userName
     * @param mobile
     * @param partnerCode
     * @param startTime
     * @param endTime
     * @param partnerBusinessFlag 借款产品标识
     * @return
     */
    LnLoanServiceFeeVO sumLoanServiceFee(@Param("userName") String userName, @Param("mobile") String mobile,
    		@Param("partnerCode") String partnerCode, @Param("startTime") String startTime,
            @Param("endTime") String endTime, @Param("partnerBusinessFlag") String partnerBusinessFlag);

}