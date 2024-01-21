package com.pinting.business.dao;

import com.pinting.business.model.BsSysBalanceDailyFile;
import com.pinting.business.model.BsSysBalanceDailyFileExample;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BsSysBalanceDailyFileMapper {
    int countByExample(BsSysBalanceDailyFileExample example);

    int deleteByExample(BsSysBalanceDailyFileExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysBalanceDailyFile record);

    int insertSelective(BsSysBalanceDailyFile record);

    List<BsSysBalanceDailyFile> selectByExample(BsSysBalanceDailyFileExample example);

    BsSysBalanceDailyFile selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysBalanceDailyFile record, @Param("example") BsSysBalanceDailyFileExample example);

    int updateByExample(@Param("record") BsSysBalanceDailyFile record, @Param("example") BsSysBalanceDailyFileExample example);

    int updateByPrimaryKeySelective(BsSysBalanceDailyFile record);

    int updateByPrimaryKey(BsSysBalanceDailyFile record);
    
    int countSysBalanceDailyFile(@Param("snapBeginTime") Date snapBeginTime,
			@Param("snapEndTime") Date snapEndTime);
    
    List<BsSysBalanceDailyFile> querySysBalanceDailyFileList(@Param("snapBeginTime") Date snapBeginTime, @Param("snapEndTime") Date snapEndTime,
    		@Param("start") Integer start, @Param("numPerPage") Integer numPerPage);
        
}