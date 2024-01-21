package com.pinting.business.dao;

import com.pinting.business.model.BsBank;
import com.pinting.business.model.BsBankExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsBankMapper {
    int countByExample(BsBankExample example);

    int deleteByExample(BsBankExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBank record);

    int insertSelective(BsBank record);

    List<BsBank> selectByExample(BsBankExample example);

    BsBank selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBank record, @Param("example") BsBankExample example);

    int updateByExample(@Param("record") BsBank record, @Param("example") BsBankExample example);

    int updateByPrimaryKeySelective(BsBank record);

    int updateByPrimaryKey(BsBank record);
    
    int countBsBank(BsBank record);
    
    List<BsBank> bsBankPage(BsBank record);
    
    BsBank selectBank(BsBank record);
    
    List<BsBank> groupByName();
}