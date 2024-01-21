package com.pinting.mall.dao;

import com.pinting.mall.model.MallConsigneeAddressInfo;
import com.pinting.mall.model.MallConsigneeAddressInfoExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MallConsigneeAddressInfoMapper {
    int countByExample(MallConsigneeAddressInfoExample example);

    int deleteByExample(MallConsigneeAddressInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallConsigneeAddressInfo record);

    int insertSelective(MallConsigneeAddressInfo record);

    List<MallConsigneeAddressInfo> selectByExample(MallConsigneeAddressInfoExample example);

    MallConsigneeAddressInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallConsigneeAddressInfo record, @Param("example") MallConsigneeAddressInfoExample example);

    int updateByExample(@Param("record") MallConsigneeAddressInfo record, @Param("example") MallConsigneeAddressInfoExample example);

    int updateByPrimaryKeySelective(MallConsigneeAddressInfo record);

    int updateByPrimaryKey(MallConsigneeAddressInfo record);
}