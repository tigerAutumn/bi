package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.FdAppoint;
import com.pinting.business.model.FdAppointExample;
import com.pinting.business.model.vo.FdAppointVO;

public interface FdAppointMapper {
    int countByExample(FdAppointExample example);

    int deleteByExample(FdAppointExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FdAppoint record);

    int insertSelective(FdAppoint record);

    List<FdAppoint> selectByExample(FdAppointExample example);

    FdAppoint selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FdAppoint record, @Param("example") FdAppointExample example);

    int updateByExample(@Param("record") FdAppoint record, @Param("example") FdAppointExample example);

    int updateByPrimaryKeySelective(FdAppoint record);

    int updateByPrimaryKey(FdAppoint record);

	ArrayList<FdAppointVO> selectMFdAppointInfoList(FdAppoint fdAppoint);
}