package com.pinting.business.dao;

import com.pinting.business.model.BsHelpChannelAccount;
import com.pinting.business.model.BsHelpChannelAccountExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsHelpChannelAccountMapper {
    int countByExample(BsHelpChannelAccountExample example);

    int deleteByExample(BsHelpChannelAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsHelpChannelAccount record);

    int insertSelective(BsHelpChannelAccount record);

    List<BsHelpChannelAccount> selectByExample(BsHelpChannelAccountExample example);

    BsHelpChannelAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsHelpChannelAccount record, @Param("example") BsHelpChannelAccountExample example);

    int updateByExample(@Param("record") BsHelpChannelAccount record, @Param("example") BsHelpChannelAccountExample example);

    int updateByPrimaryKeySelective(BsHelpChannelAccount record);

    int updateByPrimaryKey(BsHelpChannelAccount record);
    
    BsHelpChannelAccount selectChannelForLock(String channel);

    /**
     * 更新help channle数据
     * @param amount
     * @param channelType
     */
    void updateHelpChannelAccount(@Param("amount") Double amount, @Param("channelType") String channelType);

    /**
     * 
     * @param amount
     * @param channelType
     */
    void updateHelpChannelAccountSuccess(@Param("amount") Double amount, @Param("channelType") String channelType);
}