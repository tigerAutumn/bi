package com.pinting.business.dao;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.vo.LoanBalanceVO;
import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.LnSubAccount;
import com.pinting.business.model.LnSubAccountExample;

public interface LnSubAccountMapper {
    int countByExample(LnSubAccountExample example);

    int deleteByExample(LnSubAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnSubAccount record);

    int insertSelective(LnSubAccount record);

    List<LnSubAccount> selectByExample(LnSubAccountExample example);

    LnSubAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnSubAccount record, @Param("example") LnSubAccountExample example);

    int updateByExample(@Param("record") LnSubAccount record, @Param("example") LnSubAccountExample example);

    int updateByPrimaryKeySelective(LnSubAccount record);

    int updateByPrimaryKey(LnSubAccount record);
    
    LnSubAccount selectByPrimaryKey4Lock(Integer id);
    
    LnSubAccount selectSingleSubActByUserAndType(@Param("userId")Integer userId, @Param("accountType")String accountType);

    /* 2018财务管理-财务总账查询 start */
    /* 1、财务总账查询（恒丰）–新增借款人余额 */
    Double selectSumLoanBalance();
    /* 2018财务管理-财务总账查询 end */

    /* 系统余额快照记录-借款人余额快照 start */
    /**
     * 查询某一天借款人余额、可用余额、冻结余额
     * @param today
     * @return
     */
    LoanBalanceVO selectSumLoanBalanceByDay(@Param("today")Date today);
    /* 系统余额快照记录-借款人余额快照 end */

}