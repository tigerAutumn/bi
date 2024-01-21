package com.pinting.business.dao;

import com.pinting.business.model.BsCustomerReceiveMoney;
import com.pinting.business.model.BsCustomerReceiveMoneyExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCustomerReceiveMoneyMapper {
    int countByExample(BsCustomerReceiveMoneyExample example);

    int deleteByExample(BsCustomerReceiveMoneyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCustomerReceiveMoney record);

    int insertSelective(BsCustomerReceiveMoney record);

    List<BsCustomerReceiveMoney> selectByExample(BsCustomerReceiveMoneyExample example);

    BsCustomerReceiveMoney selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCustomerReceiveMoney record, @Param("example") BsCustomerReceiveMoneyExample example);

    int updateByExample(@Param("record") BsCustomerReceiveMoney record, @Param("example") BsCustomerReceiveMoneyExample example);

    int updateByPrimaryKeySelective(BsCustomerReceiveMoney record);

    int updateByPrimaryKey(BsCustomerReceiveMoney record);
}