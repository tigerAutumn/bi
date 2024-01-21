package com.pinting.business.dayend.task.process;

import com.pinting.business.model.vo.AgentUserVo;
import com.pinting.business.service.manage.MStatisticsService;
import com.pinting.business.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

/**
 * Created by cyb on 2018/5/10.
 */
public class AgentUserListQueryCallable implements Callable {

    private Logger logger = LoggerFactory.getLogger(AgentUserListQueryCallable.class);

    private MStatisticsService mStatisticsService;

    /* 操作类型：查询个数-count；查询金额-sum*/
    private String optionType;

    public AgentUserListQueryCallable(MStatisticsService mStatisticsService, String optionType) {
        this.mStatisticsService = mStatisticsService;
        this.optionType = optionType;
    }

    @Override
    public Object call() throws Exception {
        if("count".equals(optionType)) {
            logger.info("统计repayed_period_count列表");
            ArrayList<HashMap<String, Object>> repayedPeriodCount = new ArrayList<>();
            List<AgentUserVo> result =  mStatisticsService.queryRepayedPeriodCountList();
            if(!CollectionUtils.isEmpty(result)) {
                repayedPeriodCount = BeanUtils.classToArrayList(result);
            }
            return repayedPeriodCount;
        } else if("sum".equals(optionType)) {
            logger.info("统计real_amount_trans列表");
            List<HashMap<String, Object>> realAmountTrans = new ArrayList<>();
            List<AgentUserVo> list =  mStatisticsService.queryRealAmountTransList();
            if(!CollectionUtils.isEmpty(list)) {
                realAmountTrans = BeanUtils.classToArrayList(list);
            }
            return realAmountTrans;
        }
        return null;
    }
}
