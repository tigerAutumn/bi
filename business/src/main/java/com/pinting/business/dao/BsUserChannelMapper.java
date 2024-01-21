package com.pinting.business.dao;

import com.pinting.business.model.BsUserChannel;
import com.pinting.business.model.BsUserChannelExample;
import com.pinting.business.model.vo.BsUserChannelVO;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserChannelMapper {
    int countByExample(BsUserChannelExample example);

    int deleteByExample(BsUserChannelExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserChannel record);

    int insertSelective(BsUserChannel record);

    List<BsUserChannel> selectByExample(BsUserChannelExample example);

    BsUserChannelVO selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserChannel record, @Param("example") BsUserChannelExample example);

    int updateByExample(@Param("record") BsUserChannel record, @Param("example") BsUserChannelExample example);

    int updateByPrimaryKeySelective(BsUserChannel record);

    int updateByPrimaryKey(BsUserChannel record);
    
    /** 用户优先支付渠道列表 */
    ArrayList<BsUserChannelVO> selectUserChannelListPageInfo(BsUserChannelVO bsUserChannelVO);
    
    /** 用户优先支付渠道统计 */
    int selectCountUserChannel(BsUserChannelVO bsUserChannelVO);
    
    /** 查询用户优先支付渠道是否已存在 */
    BsUserChannel selectUserChananel(BsUserChannel record);
    
    /** 查找19付银行对应的银行名称-渠道类型-通道优先级 */
    ArrayList<BsUserChannelVO> select19payCardNameChannelPriority(BsUserChannelVO bsUserChannelVO);
    
}