package com.pinting.business.dao;

import com.pinting.business.model.BsUserTags;
import com.pinting.business.model.BsUserTagsExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserTagsMapper {
    int countByExample(BsUserTagsExample example);

    int deleteByExample(BsUserTagsExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserTags record);

    int insertSelective(BsUserTags record);

    List<BsUserTags> selectByExample(BsUserTagsExample example);

    BsUserTags selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserTags record, @Param("example") BsUserTagsExample example);

    int updateByExample(@Param("record") BsUserTags record, @Param("example") BsUserTagsExample example);

    int updateByPrimaryKeySelective(BsUserTags record);

    int updateByPrimaryKey(BsUserTags record);
    
    void deleteUserTag(@Param("sql")String sql);
    
    void insertUserTag(@Param("sql")String sql);
    
    int deleteByTagId(@Param("tagId")Integer tagId);
    
    List<BsUserTags> findUserTagsByUserId(@Param("userIds")List<Integer> userIds);
}