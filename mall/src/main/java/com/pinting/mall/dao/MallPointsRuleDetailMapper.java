package com.pinting.mall.dao;

import com.pinting.mall.model.MallPointsRuleDetail;
import com.pinting.mall.model.MallPointsRuleDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallPointsRuleDetailMapper {
    int countByExample(MallPointsRuleDetailExample example);

    int deleteByExample(MallPointsRuleDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallPointsRuleDetail record);

    int insertSelective(MallPointsRuleDetail record);

    List<MallPointsRuleDetail> selectByExample(MallPointsRuleDetailExample example);

    MallPointsRuleDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallPointsRuleDetail record, @Param("example") MallPointsRuleDetailExample example);

    int updateByExample(@Param("record") MallPointsRuleDetail record, @Param("example") MallPointsRuleDetailExample example);

    int updateByPrimaryKeySelective(MallPointsRuleDetail record);

    int updateByPrimaryKey(MallPointsRuleDetail record);
}