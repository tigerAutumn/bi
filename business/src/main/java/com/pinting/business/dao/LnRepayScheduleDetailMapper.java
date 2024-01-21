package com.pinting.business.dao;

import com.pinting.business.model.LnRepayScheduleDetail;
import com.pinting.business.model.LnRepayScheduleDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnRepayScheduleDetailMapper {
    int countByExample(LnRepayScheduleDetailExample example);

    int deleteByExample(LnRepayScheduleDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnRepayScheduleDetail record);

    int insertSelective(LnRepayScheduleDetail record);

    List<LnRepayScheduleDetail> selectByExample(LnRepayScheduleDetailExample example);

    LnRepayScheduleDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnRepayScheduleDetail record, @Param("example") LnRepayScheduleDetailExample example);

    int updateByExample(@Param("record") LnRepayScheduleDetail record, @Param("example") LnRepayScheduleDetailExample example);

    int updateByPrimaryKeySelective(LnRepayScheduleDetail record);

    int updateByPrimaryKey(LnRepayScheduleDetail record);
}