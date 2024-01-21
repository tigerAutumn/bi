package com.pinting.business.dao;

import com.pinting.business.model.LnBindCard;
import com.pinting.business.model.LnBindCardExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface LnBindCardMapper {
    long countByExample(LnBindCardExample example);

    int deleteByExample(LnBindCardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(LnBindCard record);

    int insertSelective(LnBindCard record);

    List<LnBindCard> selectByExample(LnBindCardExample example);

    LnBindCard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") LnBindCard record, @Param("example") LnBindCardExample example);

    int updateByExample(@Param("record") LnBindCard record, @Param("example") LnBindCardExample example);

    int updateByPrimaryKeySelective(LnBindCard record);

    int updateByPrimaryKey(LnBindCard record);

    /**
     * 获取最新的绑卡记录
     * @param lnUserId
     * @return
     */
    LnBindCard selectLatelyCard(@Param("lnUserId") Integer lnUserId);
    
    /**
     * 根据借款人手机号查询最近绑卡卡号
     * @author bianyatian
     * @param mobile
     * @return
     */
    String getBankCardByMobile(@Param("mobile") String mobile);
}