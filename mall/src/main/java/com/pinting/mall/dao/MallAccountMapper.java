package com.pinting.mall.dao;

import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountExample;
import java.util.List;

import com.pinting.mall.model.vo.MallUserPointsVO;
import org.apache.ibatis.annotations.Param;

public interface MallAccountMapper {
    int countByExample(MallAccountExample example);

    int deleteByExample(MallAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallAccount record);

    int insertSelective(MallAccount record);

    List<MallAccount> selectByExample(MallAccountExample example);

    MallAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallAccount record, @Param("example") MallAccountExample example);

    int updateByExample(@Param("record") MallAccount record, @Param("example") MallAccountExample example);

    int updateByPrimaryKeySelective(MallAccount record);

    int updateByPrimaryKey(MallAccount record);
    
    /**
     * 根据账户编号查询（加锁）
     * @param pAcctId
     * @return
     */
    MallAccount selectMallAccountByIdForLock(@Param("pAcctId")Integer pAcctId);

    /**
     * 积分用户管理列表查询
     * @param record
     * @return
     */
    List<MallUserPointsVO> selectMallUserPointsList(MallUserPointsVO record);

    /**
     * 积分用户管理记录数查询
     * @param record
     * @return
     */
    int selectMallUserPointsCount(MallUserPointsVO record);

}