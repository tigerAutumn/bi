package com.pinting.business.dao;

import com.pinting.business.model.BsHoliday;
import com.pinting.business.model.BsHolidayExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsHolidayMapper {
    int countByExample(BsHolidayExample example);

    int deleteByExample(BsHolidayExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsHoliday record);

    int insertSelective(BsHoliday record);

    List<BsHoliday> selectByExample(BsHolidayExample example);

    BsHoliday selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsHoliday record, @Param("example") BsHolidayExample example);

    int updateByExample(@Param("record") BsHoliday record, @Param("example") BsHolidayExample example);

    int updateByPrimaryKeySelective(BsHoliday record);

    int updateByPrimaryKey(BsHoliday record);
    
    List<BsHoliday> findNotInHolidayTimeList(@Param("reqTime") String reqTime);
    
    /**
     * 今天如果是周末或节假日则返回数据
     * @return
     */
    BsHoliday todayIsHolidayOrNot();
}