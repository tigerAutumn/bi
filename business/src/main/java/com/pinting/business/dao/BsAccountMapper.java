package com.pinting.business.dao;

import com.pinting.business.model.BsAccount;
import com.pinting.business.model.BsAccountExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BsAccountMapper {
    int countByExample(BsAccountExample example);

    int deleteByExample(BsAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAccount record);

    int insertSelective(BsAccount record);

    List<BsAccount> selectByExample(BsAccountExample example);

    BsAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAccount record, @Param("example") BsAccountExample example);

    int updateByExample(@Param("record") BsAccount record, @Param("example") BsAccountExample example);

    int updateByPrimaryKeySelective(BsAccount record);

    int updateByPrimaryKey(BsAccount record);
    
    /**
     * 当日新增用户数（按渠道分组）
     * 
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> currentNewAccountNumber(@Param("startTime") String startTime, @Param("endTime") String endTime);
    
    /**
     * 按时对比：新注册用户
     * 
     * @param startTime
     * @param agentIds
     * @param nonAgentId
     * @return
     */
    List<BsAccount> selectDailyNewRegistUser(@Param("startTime") String startTime, @Param("agentIds")List<Object> agentIds,@Param("nonAgentId")String nonAgentId);

    /**
     * 累计注册用户数：渠道分组
     * @return
     */
    List<Map<String, Object>> countRegisterNumber(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 历史数据：当日累计新注册用户数
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> dailyNewRegisterNum(@Param("startTime")String startTime, @Param("endTime")String endTime);

    /**
     * 历史数据：累计注册用户数
     * @param startTime
     * @param endTime
     * @return
     */
    List<Map<String, Object>> totalRegistUser(@Param("startTime")String startTime, @Param("endTime")String endTime);
    
}