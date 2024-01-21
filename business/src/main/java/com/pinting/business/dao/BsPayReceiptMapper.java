package com.pinting.business.dao;

import com.pinting.business.model.BsPayReceipt;
import com.pinting.business.model.BsPayReceiptExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsPayReceiptMapper {
    int countByExample(BsPayReceiptExample example);

    int deleteByExample(BsPayReceiptExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPayReceipt record);

    int insertSelective(BsPayReceipt record);

    List<BsPayReceipt> selectByExample(BsPayReceiptExample example);

    BsPayReceipt selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsPayReceipt record, @Param("example") BsPayReceiptExample example);

    int updateByExample(@Param("record") BsPayReceipt record, @Param("example") BsPayReceiptExample example);

    int updateByPrimaryKeySelective(BsPayReceipt record);

    int updateByPrimaryKey(BsPayReceipt record);
    
    int updateBindStatus(@Param("userId")Integer userId,@Param("bankCardNo")String cardNo, @Param("channel")String channel);
}