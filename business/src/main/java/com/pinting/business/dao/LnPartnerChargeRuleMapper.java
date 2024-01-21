package com.pinting.business.dao;

import com.pinting.business.model.LnPartnerChargeRule;
import com.pinting.business.model.LnPartnerChargeRuleExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface LnPartnerChargeRuleMapper {
    int countByExample(LnPartnerChargeRuleExample example);

    int deleteByExample(LnPartnerChargeRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnPartnerChargeRule record);

    int insertSelective(LnPartnerChargeRule record);

    List<LnPartnerChargeRule> selectByExample(LnPartnerChargeRuleExample example);

    LnPartnerChargeRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnPartnerChargeRule record, @Param("example") LnPartnerChargeRuleExample example);

    int updateByExample(@Param("record") LnPartnerChargeRule record, @Param("example") LnPartnerChargeRuleExample example);

    int updateByPrimaryKeySelective(LnPartnerChargeRule record);

    int updateByPrimaryKey(LnPartnerChargeRule record);
}