package com.pinting.business.dao;

import com.pinting.business.model.Tbdatapermission;
import com.pinting.business.model.TbdatapermissionExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbdatapermissionMapper {
    int countByExample(TbdatapermissionExample example);

    int deleteByExample(TbdatapermissionExample example);

    int deleteByPrimaryKey(Long lid);

    int insert(Tbdatapermission record);

    int insertSelective(Tbdatapermission record);

    List<Tbdatapermission> selectByExample(TbdatapermissionExample example);

    Tbdatapermission selectByPrimaryKey(Long lid);

    int updateByExampleSelective(@Param("record") Tbdatapermission record, @Param("example") TbdatapermissionExample example);

    int updateByExample(@Param("record") Tbdatapermission record, @Param("example") TbdatapermissionExample example);

    int updateByPrimaryKeySelective(Tbdatapermission record);

    int updateByPrimaryKey(Tbdatapermission record);
    
    List<Date> selectSyncTime();
    
    void batchInsertTbdatapermission(@Param("sql")String sql);
}