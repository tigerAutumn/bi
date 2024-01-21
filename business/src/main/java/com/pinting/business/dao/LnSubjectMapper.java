package com.pinting.business.dao;

import com.pinting.business.model.LnSubject;
import com.pinting.business.model.LnSubjectExample;
import com.pinting.business.model.vo.LnSubjectVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LnSubjectMapper {
    int countByExample(LnSubjectExample example);

    int deleteByExample(LnSubjectExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnSubject record);

    int insertSelective(LnSubject record);

    List<LnSubject> selectByExample(LnSubjectExample example);

    LnSubject selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnSubject record, @Param("example") LnSubjectExample example);

    int updateByExample(@Param("record") LnSubject record, @Param("example") LnSubjectExample example);

    int updateByPrimaryKeySelective(LnSubject record);

    int updateByPrimaryKey(LnSubject record);
    
    
    /**
     * 根据借款编号和合作方编码查询
     * @param loanId 借款编号
     * @param partnerCode 合作方编码
     * @param subjectCode 计费规则编码
     * @return
     */
    LnSubjectVO selectByLoanId(@Param("loanId")Integer loanId,
    		@Param("partnerCode")String partnerCode,@Param("subjectCode")String subjectCode);

    /**
     * 根据借款期限查询当前有效的规则
     * @param loanTerm
     * @param partnerCode
     * @param subjectCode
     * @return
     */
    LnSubjectVO selectByLoanTerm(@Param("loanTerm")Integer loanTerm,
                               @Param("partnerCode")String partnerCode,@Param("subjectCode")String subjectCode);
}