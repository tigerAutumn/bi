package com.pinting.business.dao;

import com.pinting.business.model.Tbdepartment;
import com.pinting.business.model.TbdepartmentExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbdepartmentMapper {
    int countByExample(TbdepartmentExample example);

    int deleteByExample(TbdepartmentExample example);

    int deleteByPrimaryKey(Long lid);

    int insert(Tbdepartment record);

    int insertSelective(Tbdepartment record);

    List<Tbdepartment> selectByExample(TbdepartmentExample example);

    Tbdepartment selectByPrimaryKey(Long lid);

    int updateByExampleSelective(@Param("record") Tbdepartment record, @Param("example") TbdepartmentExample example);

    int updateByExample(@Param("record") Tbdepartment record, @Param("example") TbdepartmentExample example);

    int updateByPrimaryKeySelective(Tbdepartment record);

    int updateByPrimaryKey(Tbdepartment record);
    
    List<Tbdepartment> queryLowerLevelDept(@Param("sql")String sql);
    
    List<Date> selectSyncTime();
    
    void batchInsertTbdepartment(@Param("sql")String sql);
}