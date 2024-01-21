package com.pinting.business.dao;

import com.pinting.business.model.BsDepositionQuitApply;
import com.pinting.business.model.BsDepositionQuitApplyExample;
import com.pinting.business.model.vo.BsDepositionQuitApplySubVO;

import java.util.Date;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsDepositionQuitApplyMapper {
    int countByExample(BsDepositionQuitApplyExample example);

    int deleteByExample(BsDepositionQuitApplyExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsDepositionQuitApply record);

    int insertSelective(BsDepositionQuitApply record);

    List<BsDepositionQuitApply> selectByExample(BsDepositionQuitApplyExample example);

    BsDepositionQuitApply selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsDepositionQuitApply record, @Param("example") BsDepositionQuitApplyExample example);

    int updateByExample(@Param("record") BsDepositionQuitApply record, @Param("example") BsDepositionQuitApplyExample example);

    int updateByPrimaryKeySelective(BsDepositionQuitApply record);

    int updateByPrimaryKey(BsDepositionQuitApply record);
    
    /**
     * 查询预期执行日期在当日，状态为通过的 理财退出申请列表，自由站岗户优先
     * 所有入参必传
     * @author bianyatian
     * @param planDate
     * @param status
     * @param firstProType-优先站岗户类型
     * @return
     */
    List<BsDepositionQuitApplySubVO> select4Transfer(@Param("planDate") Date planDate,@Param("status") String status,
    		@Param("firstProType") String firstProType);
}