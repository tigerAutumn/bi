package com.pinting.business.dao;

import com.pinting.business.model.BsDailyStat;
import com.pinting.business.model.BsDailyStatExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDailyStatMapper {
    int countByExample(BsDailyStatExample example);

    int deleteByExample(BsDailyStatExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDailyStat record);

    int insertSelective(BsDailyStat record);

    List<BsDailyStat> selectByExample(BsDailyStatExample example);

    BsDailyStat selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDailyStat record, @Param("example") BsDailyStatExample example);

    int updateByExample(@Param("record") BsDailyStat record, @Param("example") BsDailyStatExample example);

    int updateByPrimaryKeySelective(BsDailyStat record);

    int updateByPrimaryKey(BsDailyStat record);

    /**
     * 
     * @return
     */
    BsDailyStat selectLatestRecord();

    /**
     * 
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsDailyStat> selectDailyDataGrid(@Param("start")Integer start, @Param("numPerPage")Integer numPerPage);

    /**
     * 
     * @return
     */
    Integer countDailyDataGrid();

    /**
     * 
     * @param agentIds
     * @param nonAgentId
     * @param startTime
     * @param endTime
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsDailyStat> selectDailyDataCharts(@Param("agentIds")List<Object> agentIds,@Param("nonAgentId")String nonAgentId, @Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 
     * @param startTime
     * @param size
     * @return
     */
    List<BsDailyStat> selectDailyStat(@Param("startTime")String startTime, @Param("size")Integer size);
}