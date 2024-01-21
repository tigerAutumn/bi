package com.pinting.business.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsAppPushedMessage;
import com.pinting.business.model.BsAppPushedMessageExample;

public interface BsAppPushedMessageMapper {
    int countByExample(BsAppPushedMessageExample example);

    int deleteByExample(BsAppPushedMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAppPushedMessage record);

    int insertSelective(BsAppPushedMessage record);

    List<BsAppPushedMessage> selectByExample(BsAppPushedMessageExample example);

    BsAppPushedMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAppPushedMessage record, @Param("example") BsAppPushedMessageExample example);

    int updateByExample(@Param("record") BsAppPushedMessage record, @Param("example") BsAppPushedMessageExample example);

    int updateByPrimaryKeySelective(BsAppPushedMessage record);

    int updateByPrimaryKey(BsAppPushedMessage record);
    
    List<BsAppPushedMessage> selectPushedMessageByMap(Map<String, Object> map);
    
    int insertBsAppPushedMessage(@Param("sql")String sql);
}