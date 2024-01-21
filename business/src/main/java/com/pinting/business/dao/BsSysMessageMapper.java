package com.pinting.business.dao;

import com.pinting.business.model.BsSysMessage;
import com.pinting.business.model.BsSysMessageExample;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSysMessageMapper {
    int countByExample(BsSysMessageExample example);

    int deleteByExample(BsSysMessageExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysMessage record);

    int insertSelective(BsSysMessage record);

    List<BsSysMessage> selectByExample(BsSysMessageExample example);

    BsSysMessage selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysMessage record, @Param("example") BsSysMessageExample example);

    int updateByExample(@Param("record") BsSysMessage record, @Param("example") BsSysMessageExample example);

    int updateByPrimaryKeySelective(BsSysMessage record);

    int updateByPrimaryKey(BsSysMessage record);
    
    ArrayList<BsSysMessage> selectSysMessageListPageInfo(BsSysMessage record);
    
    int selectCountSysMessage(BsSysMessage record);
}