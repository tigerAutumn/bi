package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsTicketGrantPlanCheckMapper;
import com.pinting.business.model.BsTicketGrantPlanCheck;
import com.pinting.business.service.site.TicketInterestService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 用户生日触发相关活动业务定时
 * Created by cyb on 2018/4/4.
 */
@Service
public class HappyBirthdayTask {

    @Autowired
    private TicketInterestService ticketInterestService;

    @Autowired
    private BsTicketGrantPlanCheckMapper bsTicketGrantPlanCheckMapper;

    /**
     * 优惠券
     */
    public void ticket() {
        Date today = new Date();
        String birthday = DateUtil.formatDateTime(today, "MMdd");
        Integer numPerPage = 1000;
        ticketInterestService.happyBirthday(birthday, numPerPage);

        // 查询所有已过了触发时间，且处于发放中的发放计划
        List<Integer> checkIds = bsTicketGrantPlanCheckMapper.selectPassAndProcessCheckEnd();
        for (Integer checkId : checkIds) {
            // 所有触发时间已过期，且处于发放中的发放计划，置为发放结束
            BsTicketGrantPlanCheck planCheck = new BsTicketGrantPlanCheck();
            planCheck.setId(checkId);
            planCheck.setGrantStatus(Constants.TICKET_GRANT_STATUS_FINISH);
            planCheck.setUpdateTime(new Date());
            bsTicketGrantPlanCheckMapper.updateByPrimaryKeySelective(planCheck);
        }
    }
}
