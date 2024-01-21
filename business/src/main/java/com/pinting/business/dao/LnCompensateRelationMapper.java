package com.pinting.business.dao;

import com.pinting.business.model.LnCompensateRelation;
import com.pinting.business.model.LnCompensateRelationExample;
import com.pinting.business.model.vo.LateRepayAgreementVO;
import com.pinting.business.model.vo.LnCompensateRelationVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnCompensateRelationMapper {
    long countByExample(LnCompensateRelationExample example);

    int deleteByExample(LnCompensateRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnCompensateRelation record);

    int insertSelective(LnCompensateRelation record);

    List<LnCompensateRelation> selectByExample(LnCompensateRelationExample example);

    LnCompensateRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnCompensateRelation record, @Param("example") LnCompensateRelationExample example);

    int updateByExample(@Param("record") LnCompensateRelation record, @Param("example") LnCompensateRelationExample example);

    int updateByPrimaryKeySelective(LnCompensateRelation record);

    int updateByPrimaryKey(LnCompensateRelation record);
    
    
    /**
     * 根据借款id查询本金代偿对应的数据列表
     * @param loanId
     * @return
     */
    List<LnCompensateRelationVO> selectRelationIdListByLoanId(@Param("loanId") Integer loanId);
    
    /**
     * 根据合作方借款id查询代偿本金，及协议利率总金额
     * @param loanId
     * @return
     */
    LnCompensateRelation selectSumAmountByLoanId(@Param("loanId") Integer loanId);

    /**
     * 代偿协议-债权转让通知书 统计借款人这一笔借款-代偿的次数
     * @return
     */
    int select7CompensationCount(@Param("loanId") Integer loanId);

}