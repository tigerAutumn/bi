package com.pinting.business.dao;

import com.pinting.business.model.BsMatchRepayDetail;
import com.pinting.business.model.BsMatchRepayDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsMatchRepayDetailMapper {
    int countByExample(BsMatchRepayDetailExample example);

    int deleteByExample(BsMatchRepayDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsMatchRepayDetail record);

    int insertSelective(BsMatchRepayDetail record);

    List<BsMatchRepayDetail> selectByExample(BsMatchRepayDetailExample example);

    BsMatchRepayDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsMatchRepayDetail record, @Param("example") BsMatchRepayDetailExample example);

    int updateByExample(@Param("record") BsMatchRepayDetail record, @Param("example") BsMatchRepayDetailExample example);

    int updateByPrimaryKeySelective(BsMatchRepayDetail record);

    int updateByPrimaryKey(BsMatchRepayDetail record);
}