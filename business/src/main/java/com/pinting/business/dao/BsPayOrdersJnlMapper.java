package com.pinting.business.dao;

import com.pinting.business.model.BsPayOrdersJnl;
import com.pinting.business.model.BsPayOrdersJnlExample;
import com.pinting.business.model.vo.BsPayOrdersVO;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsPayOrdersJnlMapper {
    int countByExample(BsPayOrdersJnlExample example);

    int deleteByExample(BsPayOrdersJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsPayOrdersJnl record);

    int insertSelective(BsPayOrdersJnl record);

    List<BsPayOrdersJnl> selectByExample(BsPayOrdersJnlExample example);

    BsPayOrdersJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsPayOrdersJnl record, @Param("example") BsPayOrdersJnlExample example);

    int updateByExample(@Param("record") BsPayOrdersJnl record, @Param("example") BsPayOrdersJnlExample example);

    int updateByPrimaryKeySelective(BsPayOrdersJnl record);

    int updateByPrimaryKey(BsPayOrdersJnl record);
    
    /** 订单明细流水查询 */
	ArrayList<BsPayOrdersVO> selectOrderDetailList(BsPayOrdersVO bsPayOrdersVO);
	
	/** 订单明细流水记录统计 */
	int selectCountOrderDetail(BsPayOrdersVO bsPayOrdersVO);
}