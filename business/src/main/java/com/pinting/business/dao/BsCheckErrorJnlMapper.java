package com.pinting.business.dao;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsCheckErrorJnl;
import com.pinting.business.model.BsCheckErrorJnlExample;
import com.pinting.business.model.vo.BsCheckErrorJnlVO;

public interface BsCheckErrorJnlMapper {
    int countByExample(BsCheckErrorJnlExample example);

    int deleteByExample(BsCheckErrorJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCheckErrorJnl record);

    int insertSelective(BsCheckErrorJnl record);

    List<BsCheckErrorJnl> selectByExample(BsCheckErrorJnlExample example);

    BsCheckErrorJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCheckErrorJnl record, @Param("example") BsCheckErrorJnlExample example);

    int updateByExample(@Param("record") BsCheckErrorJnl record, @Param("example") BsCheckErrorJnlExample example);

    int updateByPrimaryKeySelective(BsCheckErrorJnl record);

    int updateByPrimaryKey(BsCheckErrorJnl record);

	ArrayList<BsCheckErrorJnlVO> selectCheckErrorJnlListPageInfo(
			BsCheckErrorJnl checkErrorJnl);
}