package com.pinting.business.dao;

import com.pinting.business.model.BsInterestTicketGrantAttribute;
import com.pinting.business.model.BsInterestTicketGrantAttributeExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsInterestTicketGrantAttributeMapper {
    int countByExample(BsInterestTicketGrantAttributeExample example);

    int deleteByExample(BsInterestTicketGrantAttributeExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsInterestTicketGrantAttribute record);

    int insertSelective(BsInterestTicketGrantAttribute record);

    List<BsInterestTicketGrantAttribute> selectByExample(BsInterestTicketGrantAttributeExample example);

    BsInterestTicketGrantAttribute selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsInterestTicketGrantAttribute record, @Param("example") BsInterestTicketGrantAttributeExample example);

    int updateByExample(@Param("record") BsInterestTicketGrantAttribute record, @Param("example") BsInterestTicketGrantAttributeExample example);

    int updateByPrimaryKeySelective(BsInterestTicketGrantAttribute record);

    int updateByPrimaryKey(BsInterestTicketGrantAttribute record);
}