package com.pinting.business.dao;

import com.pinting.business.model.BsTicketGrantPlanCheck;
import com.pinting.business.model.BsTicketGrantPlanCheckExample;
import com.pinting.business.model.vo.BsInterestTicketGrantAttrVO;
import com.pinting.business.model.vo.BsTicketGrantPlanCheckVO;
import com.pinting.business.model.vo.TicketCheckVO;
import com.pinting.business.model.vo.TicketGrantPlanCheckVO;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

public interface BsTicketGrantPlanCheckMapper {
    int countByExample(BsTicketGrantPlanCheckExample example);

    int deleteByExample(BsTicketGrantPlanCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsTicketGrantPlanCheck record);

    int insertSelective(BsTicketGrantPlanCheck record);

    List<BsTicketGrantPlanCheck> selectByExample(BsTicketGrantPlanCheckExample example);

    BsTicketGrantPlanCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsTicketGrantPlanCheck record, @Param("example") BsTicketGrantPlanCheckExample example);

    int updateByExample(@Param("record") BsTicketGrantPlanCheck record, @Param("example") BsTicketGrantPlanCheckExample example);

    int updateByPrimaryKeySelective(BsTicketGrantPlanCheck record);

    int updateByPrimaryKey(BsTicketGrantPlanCheck record);

    /**
     * 查询审核信息
     *
     * @param serialNo 审核单号
     * @param amount   加入金额
     * @return
     */
    List<TicketCheckVO> selectCheckInfo(@Param("serialNo") String serialNo, @Param("amount") Double amount);

    /**
     * 查询审核通过并且发放中且当前日期是在触发时间内的审核记录
     *
     * @param birthday 日期
     * @param checkId  审核ID
     * @return
     */
    List<TicketGrantPlanCheckVO> selectPassAndProcessCheck(@Param("birthday") String birthday, @Param("checkId") Integer checkId);

    /**
     * 查询审核通过并且发放中且当前日期是在触发结束时间后的记录
     *
     * @return
     */
    List<Integer> selectPassAndProcessCheckEnd();

    /**
     * 查询加息券发放计划
     *
     * @param req
     * @return
     */
    List<BsTicketGrantPlanCheckVO> selectTicketInterestGrantManagerment(@Param("record") BsTicketGrantPlanCheckVO req);

    /**
     * 查询加息券发放计划总数量
     *
     * @param req
     * @return
     */
    int getTicketInterestGrantManagermentCount(@Param("record") BsTicketGrantPlanCheckVO req);

    /**
     * 查询自动加息券发放计划详情
     *
     * @param id
     * @return
     */
    BsInterestTicketGrantAttrVO selectAutoTicketInterestGrantDetail(@Param("id") Integer id);

    /**
     * 查询手动加息券发放计划详情
     *
     * @param id
     * @return
     */
    BsInterestTicketGrantAttrVO selectManualTicketInterestGrantDetail(@Param("id") Integer id);

    /**
     * 批量手工发放加息券
     *
     * @param id
     * @return
     */
    int insertInterestTicketInfo(@Param("id") Integer id); 
    
    /**
     * 积分商城相关
     * 查询审核通过并且发放中且订单成功时间是在触发时间内的审核记录
     * @author bianyatian
     * @param orderSuccTime 订单成功时间
     * @param triggerType 发放类型
     * @param ticketTame 加息券名称
     * @param checkId  bs_ticket_grant_plan_check 表id
     * @return
     */
    List<TicketGrantPlanCheckVO> selectMallTickets(@Param("orderSuccTime") Date orderSuccTime,
    		@Param("triggerType") String triggerType,@Param("ticketName") String ticketName,@Param("checkId") Integer checkId);
    
    
    
}