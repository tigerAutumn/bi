package com.pinting.business.dao;

import com.pinting.business.model.MUserOpRecord;
import com.pinting.business.model.MUserOpRecordExample;
import com.pinting.business.model.vo.MUserOpRecordVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MUserOpRecordMapper {
    int countByExample(MUserOpRecordExample example);

    int deleteByExample(MUserOpRecordExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MUserOpRecord record);

    int insertSelective(MUserOpRecord record);

    List<MUserOpRecord> selectByExample(MUserOpRecordExample example);

    MUserOpRecord selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MUserOpRecord record, @Param("example") MUserOpRecordExample example);

    int updateByExample(@Param("record") MUserOpRecord record, @Param("example") MUserOpRecordExample example);

    int updateByPrimaryKeySelective(MUserOpRecord record);

    int updateByPrimaryKey(MUserOpRecord record);
    /**
     * 根据条件查询列表条数
     * @param record
     * @return
     */
    int countMListByRecordVO(MUserOpRecordVO record);
    /**
     * 根据条件查询列表
     * @param record
     * @return
     */
    List<MUserOpRecordVO> getMListByRecordVO(MUserOpRecordVO record);
    
}