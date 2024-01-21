package com.pinting.business.dao;

import com.pinting.business.model.BsSysWithdraw;
import com.pinting.business.model.BsSysWithdrawExample;
import com.pinting.business.model.vo.BsSysWithdrawVO;

import java.util.ArrayList;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSysWithdrawMapper {
    int countByExample(BsSysWithdrawExample example);

    int deleteByExample(BsSysWithdrawExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysWithdraw record);

    int insertSelective(BsSysWithdraw record);

    List<BsSysWithdraw> selectByExample(BsSysWithdrawExample example);

    BsSysWithdraw selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysWithdraw record, @Param("example") BsSysWithdrawExample example);

    int updateByExample(@Param("record") BsSysWithdraw record, @Param("example") BsSysWithdrawExample example);

    int updateByPrimaryKeySelective(BsSysWithdraw record);

    int updateByPrimaryKey(BsSysWithdraw record);

	BsSysWithdraw selectSysWithdrawByAppleNo(String applyNo);

	ArrayList<BsSysWithdraw> selectSysWithdrawDeteail(
			BsSysWithdrawVO sysWithdrawVO);
}