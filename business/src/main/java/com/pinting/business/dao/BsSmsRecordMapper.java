package com.pinting.business.dao;

import com.pinting.business.model.BsSmsRecord;
import com.pinting.business.model.BsSmsRecordExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsSmsRecordMapper {
    int countByExample(BsSmsRecordExample example);

    int deleteByExample(BsSmsRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSmsRecord record);

    int insertSelective(BsSmsRecord record);

    List<BsSmsRecord> selectByExample(BsSmsRecordExample example);

    BsSmsRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSmsRecord record, @Param("example") BsSmsRecordExample example);

    int updateByExample(@Param("record") BsSmsRecord record, @Param("example") BsSmsRecordExample example);

    int updateByPrimaryKeySelective(BsSmsRecord record);

    int updateByPrimaryKey(BsSmsRecord record);
    
    /**
     * 根据编号查询短信发送记录（加锁）
     * @param id
     * @return
     */
    BsSmsRecord selectByPk4Lock(@Param("id") Integer id);
    
    /**
     * 增量修改
	 * 发送条数+1，其他传值
     * @param record
     * @return
     */
    int updateByIncrement(BsSmsRecord record);
}