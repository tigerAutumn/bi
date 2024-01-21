package com.pinting.business.dao;

import com.pinting.business.model.Bs19payCheckRecord;
import com.pinting.business.model.Bs19payCheckRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Bs19payCheckRecordMapper {
    int countByExample(Bs19payCheckRecordExample example);

    int deleteByExample(Bs19payCheckRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bs19payCheckRecord record);

    int insertSelective(Bs19payCheckRecord record);

    List<Bs19payCheckRecord> selectByExample(Bs19payCheckRecordExample example);

    Bs19payCheckRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bs19payCheckRecord record, @Param("example") Bs19payCheckRecordExample example);

    int updateByExample(@Param("record") Bs19payCheckRecord record, @Param("example") Bs19payCheckRecordExample example);

    int updateByPrimaryKeySelective(Bs19payCheckRecord record);

    int updateByPrimaryKey(Bs19payCheckRecord record);
}