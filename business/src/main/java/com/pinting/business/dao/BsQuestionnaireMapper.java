package com.pinting.business.dao;

import com.pinting.business.model.BsQuestionnaire;
import com.pinting.business.model.BsQuestionnaireExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsQuestionnaireMapper {
    long countByExample(BsQuestionnaireExample example);

    int deleteByExample(BsQuestionnaireExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsQuestionnaire record);

    int insertSelective(BsQuestionnaire record);

    List<BsQuestionnaire> selectByExample(BsQuestionnaireExample example);

    BsQuestionnaire selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsQuestionnaire record, @Param("example") BsQuestionnaireExample example);

    int updateByExample(@Param("record") BsQuestionnaire record, @Param("example") BsQuestionnaireExample example);

    int updateByPrimaryKeySelective(BsQuestionnaire record);

    int updateByPrimaryKey(BsQuestionnaire record);
}