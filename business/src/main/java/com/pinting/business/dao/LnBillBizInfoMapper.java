package com.pinting.business.dao;

import com.pinting.business.model.LnBillBizInfo;
import com.pinting.business.model.LnBillBizInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnBillBizInfoMapper {
    int countByExample(LnBillBizInfoExample example);

    int deleteByExample(LnBillBizInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnBillBizInfo record);

    int insertSelective(LnBillBizInfo record);

    List<LnBillBizInfo> selectByExample(LnBillBizInfoExample example);

    LnBillBizInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnBillBizInfo record, @Param("example") LnBillBizInfoExample example);

    int updateByExample(@Param("record") LnBillBizInfo record, @Param("example") LnBillBizInfoExample example);

    int updateByPrimaryKeySelective(LnBillBizInfo record);

    int updateByPrimaryKey(LnBillBizInfo record);
    
    /**
     * 根据借款id，查询最后一期账单业务处理信息（repay_time倒序）
     * @param loanId
     * @return
     */
    LnBillBizInfo selectLastByLoanId(@Param("loanId") Integer loanId);
    
    /**
     * 根据借款id，查询最后一期账单业务处理信息（repay_time倒序），排除本次id
     * @author bianyatian
     * @param loanId
     * @param id
     * @return
     */
    LnBillBizInfo selectLastByLoanIdExcept(@Param("loanId") Integer loanId,@Param("id") Integer id);

    void batchInsert(@Param("sql") String sql);
}