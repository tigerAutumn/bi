package com.pinting.business.dao;

import com.pinting.business.model.BsQuestionnaireQuota;
import com.pinting.business.model.BsQuestionnaireQuotaExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsQuestionnaireQuotaMapper {
    int countByExample(BsQuestionnaireQuotaExample example);

    int deleteByExample(BsQuestionnaireQuotaExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsQuestionnaireQuota record);

    int insertSelective(BsQuestionnaireQuota record);

    List<BsQuestionnaireQuota> selectByExample(BsQuestionnaireQuotaExample example);

    BsQuestionnaireQuota selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsQuestionnaireQuota record, @Param("example") BsQuestionnaireQuotaExample example);

    int updateByExample(@Param("record") BsQuestionnaireQuota record, @Param("example") BsQuestionnaireQuotaExample example);

    int updateByPrimaryKeySelective(BsQuestionnaireQuota record);

    int updateByPrimaryKey(BsQuestionnaireQuota record);
    
    List<BsQuestionnaireQuota> findById(@Param("id") Integer id);
      
}