package com.pinting.business.dao;

import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.business.model.LnFinanceRepayScheduleExample;
import com.pinting.business.model.vo.LnFinanceRepayScheduleVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LnFinanceRepayScheduleMapper {
    int countByExample(LnFinanceRepayScheduleExample example);

    int deleteByExample(LnFinanceRepayScheduleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnFinanceRepaySchedule record);

    int insertSelective(LnFinanceRepaySchedule record);

    List<LnFinanceRepaySchedule> selectByExample(LnFinanceRepayScheduleExample example);

    LnFinanceRepaySchedule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnFinanceRepaySchedule record, @Param("example") LnFinanceRepayScheduleExample example);

    int updateByExample(@Param("record") LnFinanceRepaySchedule record, @Param("example") LnFinanceRepayScheduleExample example);

    int updateByPrimaryKeySelective(LnFinanceRepaySchedule record);

    int updateByPrimaryKey(LnFinanceRepaySchedule record);

    /**
     * 某笔借款某期应还理财人本息
     * @param loanId
     * @param serialId
     * @return
     */
    Double sumAmountByLoanIdSerialId(@Param("loanId")Integer loanId, @Param("serialId")Integer serialId);


    /**
     * 某笔借款某期应还理财人利息
     * @param loanId
     * @param serialId
     * @return
     */
    Double sumInterestByLoanIdSerialId(@Param("loanId")Integer loanId, @Param("serialId")Integer serialId);

    /**
     * 查询当日总利息
     * @param userId
     * @return
     */
    Double sumTodayInterestByUserId(Integer userId);

    /**
     * 查询某期未还的理财人还款计划
     * @param loanId
     * @param serialId
     * @return
     */
    List<LnFinanceRepayScheduleVO> selectFnRepayScheduleBySerial(@Param("loanId")Integer loanId, @Param("serialId")Integer serialId);
    
    /**
     * 根据bsSubAccountId关联lnLoanRelation查询LnFinanceRepaySchedule
     * @param subAccountId
     * @return
     */
    List<LnFinanceRepaySchedule> selectBySubAccount(@Param("subAccountId")Integer subAccountId);
    
    /**
     * 根据bsSubAccountId关联lnLoanRelation查询LnFinanceRepaySchedule 条数
     * LnFinanceRepaySchedule状态为INIT 、REPAYING
     * lnLoanRelation为SUCCESS、REPAID 
     * @author bianyatian
     * @param subAccountId
     * @return
     */
    Integer countNotRepaied(@Param("subAccountId")Integer subAccountId);
    
}