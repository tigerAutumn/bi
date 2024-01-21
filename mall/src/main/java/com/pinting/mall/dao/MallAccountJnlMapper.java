package com.pinting.mall.dao;

import com.pinting.mall.model.MallAccountJnl;
import com.pinting.mall.model.MallAccountJnlExample;
import java.util.List;

import com.pinting.mall.model.vo.MallAccountJnlVO;
import org.apache.ibatis.annotations.Param;

public interface MallAccountJnlMapper {
    int countByExample(MallAccountJnlExample example);

    int deleteByExample(MallAccountJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MallAccountJnl record);

    int insertSelective(MallAccountJnl record);

    List<MallAccountJnl> selectByExample(MallAccountJnlExample example);

    MallAccountJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MallAccountJnl record, @Param("example") MallAccountJnlExample example);

    int updateByExample(@Param("record") MallAccountJnl record, @Param("example") MallAccountJnlExample example);

    int updateByPrimaryKeySelective(MallAccountJnl record);

    int updateByPrimaryKey(MallAccountJnl record);

    
    /**
     * 根据用户id查询用户积分记录-分页查询列表
     * @author bianyatian
     * @param userId
     * @param start
     * @param NumPerPage
     * @return
     */
    List<MallAccountJnl> selectUserPointsRecord(@Param("userId") Integer userId,
    		@Param("start")Integer start,@Param("NumPerPage")Integer NumPerPage);
    
    /**
     * 根据用户id查询用户积分记录-总条数
     * @author bianyatian
     * @param userId
     * @return
     */
    int countUserPointsRecord(@Param("userId") Integer userId);

    
    /**
     * 积分记录-列表查询
     * @param record
     * @return
     */
    List<MallAccountJnlVO> selectMallUserPointsList(MallAccountJnlVO record);

    /**
     * 积分记录-记录数查询
     * @param record
     * @return
     */
    int selectMallUserPointsCount(MallAccountJnlVO record);

    /**
     * 积分记录-兑换的积分总和查询
     * @param record
     * @return
     */
    double selectMallUserPointsSum(MallAccountJnlVO record);

}