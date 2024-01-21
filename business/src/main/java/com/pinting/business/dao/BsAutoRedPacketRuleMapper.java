package com.pinting.business.dao;

import com.pinting.business.model.BsAutoRedPacketRule;
import com.pinting.business.model.BsAutoRedPacketRuleExample;
import com.pinting.business.model.vo.AutoRedPocketReviewVO;
import com.pinting.business.model.vo.RedPacketInfoGrantVO;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BsAutoRedPacketRuleMapper {
    int countByExample(BsAutoRedPacketRuleExample example);

    int deleteByExample(BsAutoRedPacketRuleExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAutoRedPacketRule record);

    int insertSelective(BsAutoRedPacketRule record);

    List<BsAutoRedPacketRule> selectByExample(BsAutoRedPacketRuleExample example);

    BsAutoRedPacketRule selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAutoRedPacketRule record, @Param("example") BsAutoRedPacketRuleExample example);

    int updateByExample(@Param("record") BsAutoRedPacketRule record, @Param("example") BsAutoRedPacketRuleExample example);

    int updateByPrimaryKeySelective(BsAutoRedPacketRule record);

    int updateByPrimaryKey(BsAutoRedPacketRule record);
    
    AutoRedPocketReviewVO autoRedPocketReview(@Param("id") Integer id);
    
    /**
     * 需要回显的自动红包
     * @return
     */
    AutoRedPocketReviewVO selectNewAutoRedPacket();
    
    /**
     * 查询审核通过并且发放中且当前日期是在触发时间内的红包规则记录
     *
     * @param  triggerType 
     * @return
     */
    List<RedPacketInfoGrantVO> selectPassRedPackerRuleInfo(@Param("triggerType") String triggerType);
    
    /**
     * 生日福利红包发放首条数据剩余可发数量
     * @param triggerType
     * @return
     */
    Integer selectRedPackerRuleLeftCount(@Param("triggerType") String triggerType,@Param("time") Date time);
    
}