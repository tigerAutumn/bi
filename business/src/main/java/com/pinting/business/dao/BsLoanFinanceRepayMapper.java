package com.pinting.business.dao;

import com.pinting.business.model.BsLoanFinanceRepay;
import com.pinting.business.model.BsLoanFinanceRepayExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsLoanFinanceRepayMapper {
    int countByExample(BsLoanFinanceRepayExample example);

    int deleteByExample(BsLoanFinanceRepayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsLoanFinanceRepay record);

    int insertSelective(BsLoanFinanceRepay record);

    List<BsLoanFinanceRepay> selectByExample(BsLoanFinanceRepayExample example);

    BsLoanFinanceRepay selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsLoanFinanceRepay record, @Param("example") BsLoanFinanceRepayExample example);

    int updateByExample(@Param("record") BsLoanFinanceRepay record, @Param("example") BsLoanFinanceRepayExample example);

    int updateByPrimaryKeySelective(BsLoanFinanceRepay record);

    int updateByPrimaryKey(BsLoanFinanceRepay record);
    
    /**
     * 2018-1-9改为按照用户分组
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    List<BsLoanFinanceRepay> selectGroupBySubAcctId(@Param("startTime") Date startTime,
    		@Param("endTime") Date endTime,@Param("status") String status);
    
    /**
     * 根据用户id和时间、状态查询，某人某subAccoutId的回款总额信息
     * @param userId
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    BsLoanFinanceRepay selectByUserId(@Param("userId") Integer userId,
    		@Param("startTime") Date startTime,@Param("endTime") Date endTime,@Param("status") String status);
}