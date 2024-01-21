package com.pinting.business.dao;

import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsSysConfigExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSysConfigMapper {
    int countByExample(BsSysConfigExample example);

    int deleteByExample(BsSysConfigExample example);

    int deleteByPrimaryKey(String confKey);

    int insert(BsSysConfig record);

    int insertSelective(BsSysConfig record);

    List<BsSysConfig> selectByExample(BsSysConfigExample example);

    BsSysConfig selectByPrimaryKey(String confKey);

    int updateByExampleSelective(@Param("record") BsSysConfig record, @Param("example") BsSysConfigExample example);

    int updateByExample(@Param("record") BsSysConfig record, @Param("example") BsSysConfigExample example);

    int updateByPrimaryKeySelective(BsSysConfig record);

    int updateByPrimaryKey(BsSysConfig record);
    
    /**
     * 分页查询系统配置列表
     * @param bsSysConfig
     * @return
     */
    List<BsSysConfig> selectByPage(BsSysConfig bsSysConfig);

    /**
     * 查询系统配置列表
     * @param confKeys
     * @return
     */
    List<BsSysConfig> selectByConfKeys(@Param("confKeys") String... confKeys);
}