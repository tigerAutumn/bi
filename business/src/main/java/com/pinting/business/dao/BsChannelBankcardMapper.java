package com.pinting.business.dao;

import com.pinting.business.model.BsChannelBankcard;
import com.pinting.business.model.BsChannelBankcardExample;
import com.pinting.business.model.vo.BsChannelBankcardVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsChannelBankcardMapper {
    int countByExample(BsChannelBankcardExample example);

    int deleteByExample(BsChannelBankcardExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsChannelBankcard record);

    int insertSelective(BsChannelBankcard record);

    List<BsChannelBankcard> selectByExample(BsChannelBankcardExample example);

    BsChannelBankcard selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsChannelBankcard record, @Param("example") BsChannelBankcardExample example);

    int updateByExample(@Param("record") BsChannelBankcard record, @Param("example") BsChannelBankcardExample example);

    int updateByPrimaryKeySelective(BsChannelBankcard record);

    int updateByPrimaryKey(BsChannelBankcard record);

    /**
     * 查询渠道银行卡信息
     * @param channelType
     * @return
     */
    List<BsChannelBankcardVO> selectChannelBankCardByChannel(@Param("channelType")String channelType);
    
}