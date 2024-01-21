package com.pinting.business.dao;

import com.pinting.business.model.UcBankCard;
import com.pinting.business.model.UcBankCardExample;
import com.pinting.business.model.vo.CGBindCardVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface UcBankCardMapper {
    long countByExample(UcBankCardExample example);

    int deleteByExample(UcBankCardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(UcBankCard record);

    int insertSelective(UcBankCard record);

    List<UcBankCard> selectByExample(UcBankCardExample example);

    UcBankCard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") UcBankCard record, @Param("example") UcBankCardExample example);

    int updateByExample(@Param("record") UcBankCard record, @Param("example") UcBankCardExample example);

    int updateByPrimaryKeySelective(UcBankCard record);

    int updateByPrimaryKey(UcBankCard record);
    
    List<CGBindCardVO> getIsBandList(@Param("ucUserId") Integer ucUserId);
}