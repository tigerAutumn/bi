package com.pinting.business.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.FdInfo;
import com.pinting.business.model.FdInfoExample;

public interface FdInfoMapper {
    int countByExample(FdInfoExample example);

    int deleteByExample(FdInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FdInfo record);

    int insertSelective(FdInfo record);

    List<FdInfo> selectByExample(FdInfoExample example);

    FdInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FdInfo record, @Param("example") FdInfoExample example);

    int updateByExample(@Param("record") FdInfo record, @Param("example") FdInfoExample example);

    int updateByPrimaryKeySelective(FdInfo record);

    int updateByPrimaryKey(FdInfo record);

	List<FdInfo> selectByExamplePage(HashMap<String, Object> value);

	List<FdInfo> selectMFdInfoList(FdInfo fdInfo);

	boolean deleteFdInfoByIds(HashMap<String,Object> param);
}