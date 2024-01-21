package com.pinting.business.dao;

import com.pinting.business.model.BsSysBalanceDailySnap;
import com.pinting.business.model.BsSysBalanceDailySnapExample;
import com.pinting.business.model.vo.BAOFOODailySnapVO;
import com.pinting.business.model.vo.BGWDailySnapVO;
import com.pinting.business.model.vo.BsSysBalanceDailyFileVO;
import com.pinting.business.model.vo.HFDailySnapVO;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

public interface BsSysBalanceDailySnapMapper {
    long countByExample(BsSysBalanceDailySnapExample example);

    int deleteByExample(BsSysBalanceDailySnapExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysBalanceDailySnap record);

    int insertSelective(BsSysBalanceDailySnap record);

    List<BsSysBalanceDailySnap> selectByExample(BsSysBalanceDailySnapExample example);

    BsSysBalanceDailySnap selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysBalanceDailySnap record, @Param("example") BsSysBalanceDailySnapExample example);

    int updateByExample(@Param("record") BsSysBalanceDailySnap record, @Param("example") BsSysBalanceDailySnapExample example);

    int updateByPrimaryKeySelective(BsSysBalanceDailySnap record);

    int updateByPrimaryKey(BsSysBalanceDailySnap record);
    
    List<HashMap<String, Object>> findHfSysBalanceDailySnap(HFDailySnapVO vo);
    
    int queryHfSysBalanceDailySnapCount();
    
    List<HashMap<String, Object>> findBgwSysBalanceDailySnap(BGWDailySnapVO vo);
    
    List<HashMap<String, Object>> selectBgwSysBalanceDailySnap();
    
    int queryBgwSysBalanceDailySnapCount();
    
    List<HashMap<String, Object>> findBaofooSysBalanceDailySnap(BGWDailySnapVO vo);
    
    List<HashMap<String, Object>> selectBaofooSysBalanceDailySnap();

    int queryBaofooSysBalanceDailySnapCount();
    
    List<BsSysBalanceDailyFileVO> querySysBalanceDaily(@Param("snapDate") String time); 

}