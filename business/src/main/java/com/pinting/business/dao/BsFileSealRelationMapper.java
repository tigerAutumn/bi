package com.pinting.business.dao;

import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsFileSealRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsFileSealRelationMapper {
    int countByExample(BsFileSealRelationExample example);

    int deleteByExample(BsFileSealRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsFileSealRelation record);

    int insertSelective(BsFileSealRelation record);

    List<BsFileSealRelation> selectByExample(BsFileSealRelationExample example);

    BsFileSealRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsFileSealRelation record, @Param("example") BsFileSealRelationExample example);

    int updateByExample(@Param("record") BsFileSealRelation record, @Param("example") BsFileSealRelationExample example);

    int updateByPrimaryKeySelective(BsFileSealRelation record);

    int updateByPrimaryKey(BsFileSealRelation record);
}