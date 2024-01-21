package com.pinting.business.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsRedPacketInfo;
import com.pinting.business.model.BsRedPacketInfoExample;
import com.pinting.business.model.vo.BsRedPacketInfoVO;
import com.pinting.business.model.vo.RedPacketInfoVO;
import com.pinting.business.model.vo.UserRedPublishStatisticsVO;

public interface BsRedPacketInfoMapper {
    int countByExample(BsRedPacketInfoExample example);

    int deleteByExample(BsRedPacketInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsRedPacketInfo record);

    int insertSelective(BsRedPacketInfo record);

    List<BsRedPacketInfo> selectByExample(BsRedPacketInfoExample example);

    BsRedPacketInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsRedPacketInfo record, @Param("example") BsRedPacketInfoExample example);

    int updateByExample(@Param("record") BsRedPacketInfo record, @Param("example") BsRedPacketInfoExample example);

    int updateByPrimaryKeySelective(BsRedPacketInfo record);

    int updateByPrimaryKey(BsRedPacketInfo record);

    /**
     * 管理台红包查询
     * @param useTimeStart
     * @param useTimeEnd
     * @param distributeTimeStart
     * @param distributeTimeEnd
     * @param agentId
     * @param serialName
     * @param status
     * @param mobile
     * @param serialNo
     * @param agentIds
     * @param nonAgentId
     * @param redPacketName
     * @return
     */
    List<BsRedPacketInfoVO> selectRedPacketInfoGrid(@Param("useTimeStart") String useTimeStart, 
                                                    @Param("useTimeEnd") String useTimeEnd,
                                                    @Param("distributeTimeStart") String distributeTimeStart,
                                                    @Param("distributeTimeEnd") String distributeTimeEnd, 
                                                    @Param("agentId") Integer agentId,
                                                    @Param("distributeType") String distributeType, 
                                                    @Param("triggerType") String triggerType, 
                                                    @Param("status") String status,
                                                    @Param("mobile") String mobile, 
                                                    @Param("serialNo") String serialNo,
                                                    @Param("start") Integer start,
                                                    @Param("numPerPage") Integer numPerPage,
                                                    @Param("agentIds") List<Object> agentIds,
                                                    @Param("nonAgentId") String nonAgentId,
                                                    @Param("redPacketName") String redPacketName,
                                                    @Param("redPacketNameFlag") String redPacketNameFlag,
                                                    @Param("usedTimeStart") String usedTimeStart,
                                                    @Param("usedTimeEnd") String usedTimeEnd);
    /**
     * 管理台红包查询总数
     * @param useTimeStart
     * @param useTimeEnd
     * @param distributeTimeStart
     * @param distributeTimeEnd
     * @param agentId
     * @param distributeType
     * @param triggerType
     * @param status
     * @param mobile
     * @param serialNo
     * @param agentIds
     * @param nonAgentId
     * @param redPacketName
     * @return
     */
    Integer countRedPacketInfoGrid(@Param("useTimeStart") String useTimeStart, 
                                   @Param("useTimeEnd") String useTimeEnd,
                                   @Param("distributeTimeStart") String distributeTimeStart,
                                   @Param("distributeTimeEnd") String distributeTimeEnd, 
                                   @Param("agentId") Integer agentId,
                                   @Param("distributeType") String distributeType, 
                                   @Param("triggerType") String triggerType, 
                                   @Param("status") String status,
                                   @Param("mobile") String mobile, 
                                   @Param("serialNo") String serialNo,
                                   @Param("agentIds") List<Object> agentIds,
                                   @Param("nonAgentId") String nonAgentId,
                                   @Param("redPacketName") String redPacketName,
                                   @Param("redPacketNameFlag") String redPacketNameFlag,
                                   @Param("usedTimeStart") String usedTimeStart,
                                   @Param("usedTimeEnd") String usedTimeEnd);

    /**
     * 我的红包列表
     * @param userId
     * @param status
     * @param amount
     */
    List<RedPacketInfoVO> selectRedPacketList(@Param("userId") Integer userId, 
                                              @Param("status") String status,
                                              @Param("amount") Double amount,
                                              @Param("productId") Integer productId);

    /**
     * 我的红包列表-20170316-需求
     * @param userId        用户ID
     * @param status        状态
     * @param start         开始页码（0）
     * @param numPerPage    每页显示条数
     * @return
     */
    List<RedPacketInfoVO> selectRedPacketListNew(@Param("userId") Integer userId, @Param("status") String status, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 选择红包返回相关信息
     * @param id
     * @param userId
     * @return
     */
    RedPacketInfoVO selectByIdAndUserId(@Param("id") Integer id, @Param("userId") Integer userId);
    
    /**
     * 已使用红包额
     * @return
     */
    Double selectUsedRedPaktAmount();
    
    /**
     * 未使用红包额
     * @return
     */
    Double selectUnUsedRedPaktAmount();
    
    /**
     * 已逾期红包额
     * @return
     */
    Double selectExpiryAmount();
    
    /**
     * 用户注册获得红包列表
     * @param userId
     * @return
     */
    List<RedPacketInfoVO> selectUserRegistRedPackets(Integer userId);
    
    /**
     * 已发放的过期未使用红包总金额
     * 根据申请单号，审核状态为非不通过（非REFUSE），且红包信息表中对应的未使用(!=USED)且有效期已结束的的红包总金额
     * @param applyNo
     * @param nowTime
     * @return
     */
    @Deprecated
    Double selectExpiryAmountByApplyNo(@Param("applyNo")String applyNo, @Param("nowTime")Date nowTime);
    
    /**
     * autoRule表中已停用，且已经发送给用户的总金额
     * @param applyNo
     * @return
     */
    @Deprecated
    Double selectDisableUnSendAmountByApplyNo(@Param("applyNo")String applyNo, @Param("nowTime")Date nowTime);
    
    
    BsRedPacketInfo selectRedInfoByIdForLock(@Param("id")Integer id,@Param("status")String status);

    /**
     * 统计当前用户已经领取的母亲节活动红包个数
     * @param userId    用户ID
     * @return
     */
    int countByRedPacketMotherDay(@Param("userId") Integer userId);

    /**
     * 用户红包实发记录
     * @param customerName
     * @param mobile
     * @param startTime
     * @param endTime
     * @param page
     * @param offset
     * @return
     */
    List<UserRedPublishStatisticsVO> userRedPacketPublishStatistics(@Param("customerName") String customerName,
                                                                    @Param("mobile") String mobile,
                                                                    @Param("startTime") String startTime,
                                                                    @Param("endTime") String endTime, 
                                                                    @Param("position") Integer position,
                                                                    @Param("offset") Integer offset);

    /**
     * 用户红包实发记录总数
     * @param customerName
     * @param mobile
     * @param startTime
     * @param endTime
     * @return
     */
    Integer userRedPacketPublishStatisticsCount(@Param("customerName") String customerName,
                                                @Param("mobile") String mobile,
                                                @Param("startTime") String startTime,
                                                @Param("endTime") String endTime);

    /**
     * 红包发放--红包实际使用总金额
     * @param customerName
     * @param mobile
     * @param startTime
     * @param endTime
     * @
     */
	Double userRedPacketPublishStatisticsTotalAmount(@Param("customerName") String customerName,
										            @Param("mobile") String mobile,
										            @Param("startTime") String startTime,
										            @Param("endTime") String endTime);
	
	
    
    /**
     * 根据用户id和时间查询某日使用红包的金额
     * @param userId
     * @param useDate
     * @return
     */
    Double getSumAmountByUserTime(@Param("userId") Integer userId,@Param("useDate") String useDate);
    /**
     * 根据产品ID查询用户可使用的最优红包
     * @param userId 用户ID
     * @param productId 产品ID
     * @return
     */
	RedPacketInfoVO selectOptimalRedPacket(@Param("userId") Integer userId,@Param("term") Integer term);

    /**
     * 统计某时间段某状态累计红包金额
     * @param beginTime
     * @param endTime
     * @param useStatus
     * @return
     */
    Double getUsedSumAmount(@Param("beginTime") Date beginTime,@Param("endTime") Date endTime,@Param("useStatus") String useStatus);
}