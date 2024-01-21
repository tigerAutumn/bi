package com.pinting.business.dao;

import com.pinting.business.model.BsServiceFee;
import com.pinting.business.model.BsServiceFeeExample;
import java.util.List;

import com.pinting.business.model.vo.BsServiceFeeVO;
import org.apache.ibatis.annotations.Param;

public interface BsServiceFeeMapper {
    long countByExample(BsServiceFeeExample example);

    int deleteByExample(BsServiceFeeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsServiceFee record);

    int insertSelective(BsServiceFee record);

    List<BsServiceFee> selectByExample(BsServiceFeeExample example);

    BsServiceFee selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsServiceFee record, @Param("example") BsServiceFeeExample example);

    int updateByExample(@Param("record") BsServiceFee record, @Param("example") BsServiceFeeExample example);

    int updateByPrimaryKeySelective(BsServiceFee record);

    int updateByPrimaryKey(BsServiceFee record);

    List<BsServiceFeeVO> selectByParams(@Param("feeType") String feeType, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("channel") String channel, @Param("partnerCode") String partnerCode, @Param("numPerPage") Integer numPerPage, @Param("start") Integer start);

    /**
     * 统计代付手续费总额
     * @return
     */
    Double sumFeeOfDF(@Param("feeType") String feeType, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("channel") String channel, @Param("partnerCode") String partnerCode);

    long countByParams(@Param("feeType") String feeType, @Param("startTime") String startTime, @Param("endTime") String endTime, @Param("channel") String channel, @Param("partnerCode") String partnerCode);

    /**
     * 提现手续费营收-列表
     * @param startTime
     * @param endTime
     * @param userName
     * @param mobile
     * @param channel
     * @param numPerPage
     * @param start
     * @return
     */
    List<BsServiceFeeVO> selectWithdrawFeeList(@Param("startTime") String startTime, @Param("endTime") String endTime,
                                               @Param("userName") String userName, @Param("mobile") String mobile,
                                               @Param("channel") String channel, @Param("numPerPage") Integer numPerPage,
                                               @Param("start") Integer start);

    /**
     * 提现手续费营收-统计
     * @return
     */
    long conutWithdrawFee(@Param("startTime") String startTime, @Param("endTime") String endTime,
                          @Param("userName") String userName, @Param("mobile") String mobile,
                          @Param("channel") String channel);

    /**
     * 统计提现手续费营收-总额
     * @return
     */
    Double sumWithdrawFee(@Param("startTime") String startTime, @Param("endTime") String endTime,
                          @Param("userName") String userName, @Param("mobile") String mobile,
                          @Param("channel") String channel);

}