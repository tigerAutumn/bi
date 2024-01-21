package com.pinting.business.dao;

import com.pinting.business.model.Bs2016CheckInUser;
import com.pinting.business.model.Bs2016CheckInUserExample;
import com.pinting.business.model.vo.BsCheckInUserVO;
import com.pinting.business.model.vo.EndOf2016YearActivityVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface Bs2016CheckInUserMapper {
    int countByExample(Bs2016CheckInUserExample example);

    int deleteByExample(Bs2016CheckInUserExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(Bs2016CheckInUser record);

    int insertSelective(Bs2016CheckInUser record);

    List<Bs2016CheckInUser> selectByExample(Bs2016CheckInUserExample example);

    Bs2016CheckInUser selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") Bs2016CheckInUser record, @Param("example") Bs2016CheckInUserExample example);

    int updateByExample(@Param("record") Bs2016CheckInUser record, @Param("example") Bs2016CheckInUserExample example);

    int updateByPrimaryKeySelective(Bs2016CheckInUser record);

    int updateByPrimaryKey(Bs2016CheckInUser record);
    
    void insertCheckInUser(@Param("sql")String sql);
    
    /**
     * 查询手机号码是否已存在
     * @param mobile
     * @return
     */
    Bs2016CheckInUser selectByMobile(@Param("mobile") String mobile);
    
    /**
     * 2016客户年终答谢会抽奖列表
     * @param record
     * @return
     */
    List<BsCheckInUserVO> findCheckInUserList(BsCheckInUserVO record);
    
    /**
     * 2016客户年终答谢会抽奖统计
     * @param record
     * @return
     */
    int findCheckInUserCount(BsCheckInUserVO record);
    
    /**
     * 查找可以抽奖的用户手机号相关信息
     * @return
     */
    List<EndOf2016YearActivityVO> selectLotteryMobileList();
    
    /**
     * 查找未抽奖(一、二、三等奖)的用户手机号相关信息
     * @param activityAwardId 奖品等级Id
     * @return
     */
    List<EndOf2016YearActivityVO> selectNoDrawMobileList(@Param("activityAwardId") Integer activityAwardId);
    
    /**
     * 查询年化额的最大值-特等奖：用户所有在投状态（投资中、结算中）的投资的年化额总和
     * @return
     */
    List<EndOf2016YearActivityVO> selectAnnualizedAmountMax();
    
    /**
     * 统计幸运奖，特等奖、一、二、三等奖的获奖人数，抽完之后提示抽奖人
     * @param activityAwardId 奖品等级Id
     * @return
     */
    int selectNumberOfWinners(@Param("activityAwardId") Integer activityAwardId);





    /**
     * 未抽奖用户（包含累计年化投资额）
     * @param activityId
     * @return
     */
    List<EndOf2016YearActivityVO> selectNoDrawUserAmount(@Param("activityId") Integer activityId);

    /**
     * 已中奖用户
     * @param activityId
     * @return
     */
    List<EndOf2016YearActivityVO> selectDrawedUser(@Param("activityId") Integer activityId, @Param("type") Integer type);
}