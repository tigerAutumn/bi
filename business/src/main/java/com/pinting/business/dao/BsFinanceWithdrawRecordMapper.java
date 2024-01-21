package com.pinting.business.dao;

import com.pinting.business.model.BsFinanceWithdrawRecord;
import com.pinting.business.model.BsFinanceWithdrawRecordExample;
import com.pinting.business.model.vo.FinanceWithdrawRecordVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsFinanceWithdrawRecordMapper {
    int countByExample(BsFinanceWithdrawRecordExample example);

    int deleteByExample(BsFinanceWithdrawRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsFinanceWithdrawRecord record);

    int insertSelective(BsFinanceWithdrawRecord record);

    List<BsFinanceWithdrawRecord> selectByExample(BsFinanceWithdrawRecordExample example);

    BsFinanceWithdrawRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsFinanceWithdrawRecord record, @Param("example") BsFinanceWithdrawRecordExample example);

    int updateByExample(@Param("record") BsFinanceWithdrawRecord record, @Param("example") BsFinanceWithdrawRecordExample example);

    int updateByPrimaryKeySelective(BsFinanceWithdrawRecord record);

    int updateByPrimaryKey(BsFinanceWithdrawRecord record);

    /**
     * 
     * @param startTime
     * @param endTime
     * @param start
     * @param numPerPage
     * @return
     */
    List<FinanceWithdrawRecordVO> selectFinanceWithdrawRecords(@Param("startTime") String startTime, @Param("endTime") String endTime,
        @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    Integer countFinanceWithdrawRecords(@Param("startTime") String startTime, @Param("endTime") String endTime);
}