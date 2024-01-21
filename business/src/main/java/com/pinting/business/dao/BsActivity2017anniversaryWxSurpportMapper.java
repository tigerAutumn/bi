package com.pinting.business.dao;

import com.pinting.business.model.BsActivity2017anniversaryWxSurpport;
import com.pinting.business.model.BsActivity2017anniversaryWxSurpportExample;
import java.util.List;

import com.pinting.business.model.vo.AnniversaryHelpFriendVO;
import org.apache.ibatis.annotations.Param;

public interface BsActivity2017anniversaryWxSurpportMapper {
    long countByExample(BsActivity2017anniversaryWxSurpportExample example);

    int deleteByExample(BsActivity2017anniversaryWxSurpportExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsActivity2017anniversaryWxSurpport record);

    int insertSelective(BsActivity2017anniversaryWxSurpport record);

    List<BsActivity2017anniversaryWxSurpport> selectByExample(BsActivity2017anniversaryWxSurpportExample example);

    BsActivity2017anniversaryWxSurpport selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsActivity2017anniversaryWxSurpport record, @Param("example") BsActivity2017anniversaryWxSurpportExample example);

    int updateByExample(@Param("record") BsActivity2017anniversaryWxSurpport record, @Param("example") BsActivity2017anniversaryWxSurpportExample example);

    int updateByPrimaryKeySelective(BsActivity2017anniversaryWxSurpport record);

    int updateByPrimaryKey(BsActivity2017anniversaryWxSurpport record);

    /**
     * 查询助力者列表
     * @param shareUserId   被助力者ID
     * @param activityId    活动ID
     * @param start         分页开始
     * @param numPerPage    分页每页显示条数
     * @return
     */
    List<AnniversaryHelpFriendVO> selectByPartnerId(@Param("shareUserId") Integer shareUserId, @Param("activityId") Integer activityId, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 根据openId查询助力信息
     * @param openId        微信openID
     * @param shareUserId   被助力者ID
     * @param activityId    活动ID
     * @return
     */
    AnniversaryHelpFriendVO selectByOpenId(@Param("openId") String openId, @Param("shareUserId") Integer shareUserId, @Param("activityId") Integer activityId);
}