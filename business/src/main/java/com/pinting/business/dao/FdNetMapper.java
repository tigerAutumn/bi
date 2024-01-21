package com.pinting.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.FdNet;
import com.pinting.business.model.FdNetExample;
import com.pinting.business.model.vo.FdNetVO;

public interface FdNetMapper {
    int countByExample(FdNetExample example);

    int deleteByExample(FdNetExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(FdNet record);

    int insertSelective(FdNet record);
	List<FdNetVO> selectMFdNetInfoList(FdNetVO fdNet);
    List<FdNet> selectByExample(FdNetExample example);

    FdNet selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") FdNet record, @Param("example") FdNetExample example);

    int updateByExample(@Param("record") FdNet record, @Param("example") FdNetExample example);

    int updateByPrimaryKeySelective(FdNet record);

    int updateByPrimaryKey(FdNet record);
}