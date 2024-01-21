package com.pinting.business.dao;

import com.pinting.business.model.BsSmsPlatformsConfig;
import com.pinting.business.model.BsSmsPlatformsConfigExample;
import com.pinting.business.model.vo.BsSmsPlatformsConfigVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSmsPlatformsConfigMapper {
    long countByExample(BsSmsPlatformsConfigExample example);

    int deleteByExample(BsSmsPlatformsConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSmsPlatformsConfig record);

    int insertSelective(BsSmsPlatformsConfig record);

    List<BsSmsPlatformsConfig> selectByExample(BsSmsPlatformsConfigExample example);

    BsSmsPlatformsConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSmsPlatformsConfig record, @Param("example") BsSmsPlatformsConfigExample example);

    int updateByExample(@Param("record") BsSmsPlatformsConfig record, @Param("example") BsSmsPlatformsConfigExample example);

    int updateByPrimaryKeySelective(BsSmsPlatformsConfig record);

    int updateByPrimaryKey(BsSmsPlatformsConfig record);
    
    /**
     * 查询所有短信平台按优先级排序，并查询每个平台在当前时间之前的60分钟内的成功率，有返回状态的总数低于20不计成功率
     * @return
     */
    List<BsSmsPlatformsConfigVO> selectAllList();
}