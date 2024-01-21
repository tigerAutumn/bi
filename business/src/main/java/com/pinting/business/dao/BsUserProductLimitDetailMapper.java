package com.pinting.business.dao;

import com.pinting.business.model.BsUserProductLimitDetail;
import com.pinting.business.model.BsUserProductLimitDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserProductLimitDetailMapper {
    int countByExample(BsUserProductLimitDetailExample example);

    int deleteByExample(BsUserProductLimitDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserProductLimitDetail record);

    int insertSelective(BsUserProductLimitDetail record);

    List<BsUserProductLimitDetail> selectByExample(BsUserProductLimitDetailExample example);

    BsUserProductLimitDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserProductLimitDetail record, @Param("example") BsUserProductLimitDetailExample example);

    int updateByExample(@Param("record") BsUserProductLimitDetail record, @Param("example") BsUserProductLimitDetailExample example);

    int updateByPrimaryKeySelective(BsUserProductLimitDetail record);

    int updateByPrimaryKey(BsUserProductLimitDetail record);
}