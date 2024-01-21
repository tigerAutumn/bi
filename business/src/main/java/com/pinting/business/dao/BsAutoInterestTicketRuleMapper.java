package com.pinting.business.dao;

import com.pinting.business.model.BsAutoInterestTicketRule;
import com.pinting.business.model.BsAutoInterestTicketRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsAutoInterestTicketRuleMapper {
    int countByExample(BsAutoInterestTicketRuleExample example);

    int deleteByExample(BsAutoInterestTicketRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAutoInterestTicketRule record);

    int insertSelective(BsAutoInterestTicketRule record);

    List<BsAutoInterestTicketRule> selectByExample(BsAutoInterestTicketRuleExample example);

    BsAutoInterestTicketRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAutoInterestTicketRule record, @Param("example") BsAutoInterestTicketRuleExample example);

    int updateByExample(@Param("record") BsAutoInterestTicketRule record, @Param("example") BsAutoInterestTicketRuleExample example);

    int updateByPrimaryKeySelective(BsAutoInterestTicketRule record);

    int updateByPrimaryKey(BsAutoInterestTicketRule record);
}