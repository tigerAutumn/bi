package com.pinting.business.dao;

import com.pinting.business.model.LnRepayDetail;
import com.pinting.business.model.LnRepayDetailExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnRepayDetailMapper {
    int countByExample(LnRepayDetailExample example);

    int deleteByExample(LnRepayDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnRepayDetail record);

    int insertSelective(LnRepayDetail record);

    List<LnRepayDetail> selectByExample(LnRepayDetailExample example);

    LnRepayDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnRepayDetail record, @Param("example") LnRepayDetailExample example);

    int updateByExample(@Param("record") LnRepayDetail record, @Param("example") LnRepayDetailExample example);

    int updateByPrimaryKeySelective(LnRepayDetail record);

    int updateByPrimaryKey(LnRepayDetail record);
}