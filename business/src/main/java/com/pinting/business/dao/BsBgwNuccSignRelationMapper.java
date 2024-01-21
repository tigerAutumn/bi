package com.pinting.business.dao;

import com.pinting.business.model.BsBgwNuccSignRelation;
import com.pinting.business.model.BsBgwNuccSignRelationExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsBgwNuccSignRelationMapper {

    int countByExample(BsBgwNuccSignRelationExample example);

    int deleteByExample(BsBgwNuccSignRelationExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBgwNuccSignRelation record);

    int insertSelective(BsBgwNuccSignRelation record);

    List<BsBgwNuccSignRelation> selectByExample(BsBgwNuccSignRelationExample example);

    BsBgwNuccSignRelation selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBgwNuccSignRelation record, @Param("example") BsBgwNuccSignRelationExample example);

    int updateByExample(@Param("record") BsBgwNuccSignRelation record, @Param("example") BsBgwNuccSignRelationExample example);

    int updateByPrimaryKeySelective(BsBgwNuccSignRelation record);

    int updateByPrimaryKey(BsBgwNuccSignRelation record);

    int batchInsert(List<BsBgwNuccSignRelation> list);

    int batchUpdate(List<BsBgwNuccSignRelation> list);
}