package com.pinting.business.dao;

import com.pinting.business.model.LnRepeatRepayRecord;
import com.pinting.business.model.LnRepeatRepayRecordExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnRepeatRepayRecordMapper {
    int countByExample(LnRepeatRepayRecordExample example);

    int deleteByExample(LnRepeatRepayRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnRepeatRepayRecord record);

    int insertSelective(LnRepeatRepayRecord record);

    List<LnRepeatRepayRecord> selectByExample(LnRepeatRepayRecordExample example);

    LnRepeatRepayRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnRepeatRepayRecord record, @Param("example") LnRepeatRepayRecordExample example);

    int updateByExample(@Param("record") LnRepeatRepayRecord record, @Param("example") LnRepeatRepayRecordExample example);

    int updateByPrimaryKeySelective(LnRepeatRepayRecord record);

    int updateByPrimaryKey(LnRepeatRepayRecord record);
    
    int countRepeatCheckAccount();
    
    Double sumRepeatCheckAccount();
    
}