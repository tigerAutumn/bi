package com.pinting.business.dao;

import com.pinting.business.model.BsInterestTicketInfo;
import com.pinting.business.model.BsInterestTicketInfoExample;
import com.pinting.business.model.vo.BsInterestTicketManageVO;
import com.pinting.business.model.vo.InterestTicketInfoVO;
import com.pinting.business.model.vo.TicketInterestNotifyVO;
import com.pinting.business.model.vo.TicketReminderNotifyVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsInterestTicketInfoMapper {
    int countByExample(BsInterestTicketInfoExample example);

    int deleteByExample(BsInterestTicketInfoExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsInterestTicketInfo record);

    int insertSelective(BsInterestTicketInfo record);

    List<BsInterestTicketInfo> selectByExample(BsInterestTicketInfoExample example);

    BsInterestTicketInfo selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsInterestTicketInfo record, @Param("example") BsInterestTicketInfoExample example);

    int updateByExample(@Param("record") BsInterestTicketInfo record, @Param("example") BsInterestTicketInfoExample example);

    int updateByPrimaryKeySelective(BsInterestTicketInfo record);

    int updateByPrimaryKey(BsInterestTicketInfo record);

    /**
     * 购买页面优惠券列表
     *
     * @param userId    用户ID
     * @param amount    金额
     * @param productId 产品ID
     * @return
     */
    List<InterestTicketInfoVO> selectBuyTicketINITList(@Param("userId") Integer userId, @Param("amount") Double amount, @Param("productId") Integer productId);

    /**
     * 我的加息券列表
     *
     * @param userId     用户ID
     * @param status     状态
     * @param start      开始页码（0）
     * @param numPerPage 每页显示条数
     * @return
     */
    List<InterestTicketInfoVO> selectInterestTicketListNew(@Param("userId") Integer userId, @Param("status") String status, @Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**************************管理台加息券查询************************/
    /**
     * 加息券查询 计数
     *
     * @param record
     * @return
     */
    Integer countTicketInterestInfo(BsInterestTicketManageVO record);

    /**
     * 加息券查询 列表
     *
     * @param record
     * @return
     */
    List<BsInterestTicketManageVO> selectTicketInterestInfoList(BsInterestTicketManageVO record);

    /**
     * 加息收益 求和
     *
     * @param record
     * @return
     */
    Double sumTicketInterest(BsInterestTicketManageVO record);

    /**
     * 查询要发送通知的加息券信息（条件：加息券初始状态，未发送通知，10小时以内生效）
     *
     * @return
     */
    List<TicketInterestNotifyVO> selectInterestTicketNotify();
    
    /**
     * 根据用户id和站岗户id查询用户使用加息券的收益金额
     * @author bianyatian
     * @param userId
     * @param subAccountId
     * @return
     */
    Double ticketInterest(@Param("userId") Integer userId,@Param("subAccountId") Integer subAccountId);

    /**
     * 根据BsInterestTicketInfo表id查询发送通知所需信息
     * @author bianyatian
     * @return
     */
    TicketInterestNotifyVO selectInterestTicketNotifyByInfoId(@Param("infoId") Integer infoId);

    /**
     * 查询到期的加息券数量（优惠券+红包）-优惠券到期提醒定时
     * @param dueDate
     * @return
     */
    List<TicketReminderNotifyVO> selectTicketReminderList(@Param("dueDate")String dueDate);
}