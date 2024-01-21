package com.pinting.business.dao;

import com.pinting.business.model.BsActivityAward;
import com.pinting.business.model.BsActivityAwardExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsActivityAwardMapper {
    int countByExample(BsActivityAwardExample example);

    int deleteByExample(BsActivityAwardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivityAward record);

    int insertSelective(BsActivityAward record);

    List<BsActivityAward> selectByExample(BsActivityAwardExample example);

    BsActivityAward selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivityAward record, @Param("example") BsActivityAwardExample example);

    int updateByExample(@Param("record") BsActivityAward record, @Param("example") BsActivityAwardExample example);

    int updateByPrimaryKeySelective(BsActivityAward record);

    int updateByPrimaryKey(BsActivityAward record);

}