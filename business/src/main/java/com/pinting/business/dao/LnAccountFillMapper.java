package com.pinting.business.dao;

import com.pinting.business.model.LnAccountFill;
import com.pinting.business.model.LnAccountFillExample;
import java.util.List;

import com.pinting.business.model.vo.DafyAccountFillSelfForCheckVO;
import org.apache.ibatis.annotations.Param;

public interface LnAccountFillMapper {
    int countByExample(LnAccountFillExample example);

    int deleteByExample(LnAccountFillExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnAccountFill record);

    int insertSelective(LnAccountFill record);

    List<LnAccountFill> selectByExample(LnAccountFillExample example);

    LnAccountFill selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnAccountFill record, @Param("example") LnAccountFillExample example);

    int updateByExample(@Param("record") LnAccountFill record, @Param("example") LnAccountFillExample example);

    int updateByPrimaryKeySelective(LnAccountFill record);

    int updateByPrimaryKey(LnAccountFill record);

    /**
     *
     * @param time
     * @param start
     * @param numPerPage
     * @return
     */
    List<DafyAccountFillSelfForCheckVO> selectForCheck(@Param("time") String time, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);


}