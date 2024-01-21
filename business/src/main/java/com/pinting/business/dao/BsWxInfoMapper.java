package com.pinting.business.dao;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.vo.WxAgentVO;
import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsWxInfo;
import com.pinting.business.model.BsWxInfoExample;

public interface BsWxInfoMapper {
    int countByExample(BsWxInfoExample example);

    int deleteByExample(BsWxInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsWxInfo record);

    int insertSelective(BsWxInfo record);

    List<BsWxInfo> selectByExample(BsWxInfoExample example);

    BsWxInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsWxInfo record, @Param("example") BsWxInfoExample example);

    int updateByExample(@Param("record") BsWxInfo record, @Param("example") BsWxInfoExample example);

    int updateByPrimaryKeySelective(BsWxInfo record);

    int updateByPrimaryKey(BsWxInfo record);
    
    /**
	 * 统计关注用户数
	 * @param map
	 * @return map
	 */
    Map<String,Object> countWxUserNum(Map<String,Object> map);

    /**
     * 根据渠道编号，查询该渠道关注的人数
     * @param wxAgentId
     * @return
     */
    int selectWxInfoCountByAgentId(@Param("wxAgentId") Integer wxAgentId);

    /**
     * 根据渠道编号，分页查询该渠道关注的人数列表
     * @param wxAgentId
     * @param start
     * @param numPerPage
     * @return
     */
    List<WxAgentVO> selectWxInfoListByAgentId(@Param("wxAgentId") Integer wxAgentId,
                                              @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

}