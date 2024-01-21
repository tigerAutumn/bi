package com.pinting.business.dayend.task;

import com.pinting.business.service.site.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * （0点定时）从昨日投资号码中产生一个幸运号码，存库
 * Created by cyb on 2017/11/6.
 */
@Service
public class ActivityTask {

    @Autowired
    private ActivityService activityService;

    public void execute() {
        activityService.luckyNumber();
    }
}
