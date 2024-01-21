package com.pinting.business.dao;

import com.pinting.business.model.BsBonus;
import com.pinting.business.model.BsBonusExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsBonusMapper {
    int countByExample(BsBonusExample example);

    int deleteByExample(BsBonusExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBonus record);

    int insertSelective(BsBonus record);

    List<BsBonus> selectByExample(BsBonusExample example);

    BsBonus selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBonus record, @Param("example") BsBonusExample example);

    int updateByExample(@Param("record") BsBonus record, @Param("example") BsBonusExample example);

    int updateByPrimaryKeySelective(BsBonus record);

    int updateByPrimaryKey(BsBonus record);
}