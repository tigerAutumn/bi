package com.pinting.business.dao;

import com.pinting.business.model.BsUserReceiveAddress;
import com.pinting.business.model.BsUserReceiveAddressExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserReceiveAddressMapper {
    long countByExample(BsUserReceiveAddressExample example);

    int deleteByExample(BsUserReceiveAddressExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserReceiveAddress record);

    int insertSelective(BsUserReceiveAddress record);

    List<BsUserReceiveAddress> selectByExample(BsUserReceiveAddressExample example);

    BsUserReceiveAddress selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserReceiveAddress record, @Param("example") BsUserReceiveAddressExample example);

    int updateByExample(@Param("record") BsUserReceiveAddress record, @Param("example") BsUserReceiveAddressExample example);

    int updateByPrimaryKeySelective(BsUserReceiveAddress record);

    int updateByPrimaryKey(BsUserReceiveAddress record);
}