package com.pinting.business.dao;

import com.pinting.business.model.BsChannelCheck;
import com.pinting.business.model.BsChannelCheckExample;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsChannelCheckMapper {
    int countByExample(BsChannelCheckExample example);

    int deleteByExample(BsChannelCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsChannelCheck record);

    int insertSelective(BsChannelCheck record);

    List<BsChannelCheck> selectByExample(BsChannelCheckExample example);

    BsChannelCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsChannelCheck record, @Param("example") BsChannelCheckExample example);

    int updateByExample(@Param("record") BsChannelCheck record, @Param("example") BsChannelCheckExample example);

    int updateByPrimaryKeySelective(BsChannelCheck record);

    int updateByPrimaryKey(BsChannelCheck record);
    
    /**
     * 查询某时间段内符合状态的数据总数
     * @param time
     * @return
     */
    int countByStatusTime(@Param("status") String status, @Param("startTime") String startTime, @Param("endTime") String endTime);
}