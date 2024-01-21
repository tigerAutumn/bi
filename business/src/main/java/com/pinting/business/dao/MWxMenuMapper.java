package com.pinting.business.dao;

import com.pinting.business.model.MWxMenu;
import com.pinting.business.model.MWxMenuExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MWxMenuMapper {
    int countByExample(MWxMenuExample example);

    int deleteByExample(MWxMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MWxMenu record);

    int insertSelective(MWxMenu record);

    List<MWxMenu> selectByExample(MWxMenuExample example);

    MWxMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MWxMenu record, @Param("example") MWxMenuExample example);

    int updateByExample(@Param("record") MWxMenu record, @Param("example") MWxMenuExample example);

    int updateByPrimaryKeySelective(MWxMenu record);

    int updateByPrimaryKey(MWxMenu record);
}