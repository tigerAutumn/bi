package com.pinting.mall.dao;

import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallPointsRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallPointsRuleMapper {
    int countByExample(MallPointsRuleExample example);

    int deleteByExample(MallPointsRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallPointsRule record);

    int insertSelective(MallPointsRule record);

    List<MallPointsRule> selectByExample(MallPointsRuleExample example);

    MallPointsRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallPointsRule record, @Param("example") MallPointsRuleExample example);

    int updateByExample(@Param("record") MallPointsRule record, @Param("example") MallPointsRuleExample example);

    int updateByPrimaryKeySelective(MallPointsRule record);

    int updateByPrimaryKey(MallPointsRule record);
    
    /**
     * 查询积分设置列表
     * @param status
     * @return
     */
    List<MallPointsRule> selectByStatus(@Param("status") String status);
    
    /**
     * 分页查询积分设置列表
     * @return
     */
    List<MallPointsRule> findPointsRuleList(@Param("status") String status,
    		@Param("pageNum") Integer pageNum, @Param("numPerPage") Integer numPerPage);
    
}