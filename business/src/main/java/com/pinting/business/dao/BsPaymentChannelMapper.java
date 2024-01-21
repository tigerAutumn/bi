package com.pinting.business.dao;

import com.pinting.business.model.BsPaymentChannel;
import com.pinting.business.model.BsPaymentChannelExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsPaymentChannelMapper {
    int countByExample(BsPaymentChannelExample example);

    int deleteByExample(BsPaymentChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPaymentChannel record);

    int insertSelective(BsPaymentChannel record);

    List<BsPaymentChannel> selectByExample(BsPaymentChannelExample example);

    BsPaymentChannel selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsPaymentChannel record, @Param("example") BsPaymentChannelExample example);

    int updateByExample(@Param("record") BsPaymentChannel record, @Param("example") BsPaymentChannelExample example);

    int updateByPrimaryKeySelective(BsPaymentChannel record);

    int updateByPrimaryKey(BsPaymentChannel record);
    
    /**
     * 查询优先级最高的交易类型为代扣的商户信息
     * @return
     */
    BsPaymentChannel selectFisrtDK(); 
}