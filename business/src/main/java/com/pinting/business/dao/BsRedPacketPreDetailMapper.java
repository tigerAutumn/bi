package com.pinting.business.dao;

import com.pinting.business.model.BsRedPacketPreDetail;
import com.pinting.business.model.BsRedPacketPreDetailExample;
import com.pinting.business.model.vo.BsRedPacketPreDetailVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsRedPacketPreDetailMapper {
    int countByExample(BsRedPacketPreDetailExample example);

    int deleteByExample(BsRedPacketPreDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsRedPacketPreDetail record);

    int insertSelective(BsRedPacketPreDetail record);

    List<BsRedPacketPreDetail> selectByExample(BsRedPacketPreDetailExample example);

    BsRedPacketPreDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsRedPacketPreDetail record, @Param("example") BsRedPacketPreDetailExample example);

    int updateByExample(@Param("record") BsRedPacketPreDetail record, @Param("example") BsRedPacketPreDetailExample example);

    int updateByPrimaryKeySelective(BsRedPacketPreDetail record);

    int updateByPrimaryKey(BsRedPacketPreDetail record);
    
    List<BsRedPacketPreDetailVO>  manualRedPocketReview(@Param("id") Integer id,
												@Param("start") Integer start,
									            @Param("numPerPage") Integer numPerPage);
    Integer  manualRedPocketCount(@Param("id") Integer id);
    
    int saveRedPacketPreDetail(@Param("sql")String sql);
}