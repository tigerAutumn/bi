package com.pinting.business.dao;

import com.pinting.business.model.BsTicketPreDetail;
import com.pinting.business.model.BsTicketPreDetailExample;
import java.util.List;

import com.pinting.business.model.vo.BsTicketPreDetailVO;
import org.apache.ibatis.annotations.Param;

public interface BsTicketPreDetailMapper {
    int countByExample(BsTicketPreDetailExample example);

    int deleteByExample(BsTicketPreDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsTicketPreDetail record);

    int insertSelective(BsTicketPreDetail record);

    List<BsTicketPreDetail> selectByExample(BsTicketPreDetailExample example);

    BsTicketPreDetail selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsTicketPreDetail record, @Param("example") BsTicketPreDetailExample example);

    int updateByExample(@Param("record") BsTicketPreDetail record, @Param("example") BsTicketPreDetailExample example);

    int updateByPrimaryKeySelective(BsTicketPreDetail record);

    int updateByPrimaryKey(BsTicketPreDetail record);

    List<BsTicketPreDetailVO> selectBsUserBySerialNo(@Param("record") BsTicketPreDetailVO record);

    int selectCountBySerialNo(@Param("record") BsTicketPreDetailVO record);
}