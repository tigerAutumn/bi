package com.pinting.business.dao;

import com.pinting.business.model.BsAgentViewConfig;
import com.pinting.business.model.BsAgentViewConfigExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsAgentViewConfigMapper {
    long countByExample(BsAgentViewConfigExample example);

    int deleteByExample(BsAgentViewConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAgentViewConfig record);

    int insertSelective(BsAgentViewConfig record);

    List<BsAgentViewConfig> selectByExample(BsAgentViewConfigExample example);

    BsAgentViewConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAgentViewConfig record, @Param("example") BsAgentViewConfigExample example);

    int updateByExample(@Param("record") BsAgentViewConfig record, @Param("example") BsAgentViewConfigExample example);

    int updateByPrimaryKeySelective(BsAgentViewConfig record);

    int updateByPrimaryKey(BsAgentViewConfig record);
    /**
     * 获取该表中的agent id list
     * @return
     */
    List<Integer> getAgentIds();
    /**
     * 获取该表中钱报的agent id list
     * @return
     */
    List<Integer> getQianbaoAgentIds();
}