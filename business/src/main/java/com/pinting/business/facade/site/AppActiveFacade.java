package com.pinting.business.facade.site;

import com.pinting.business.hessian.site.message.ReqMsg_AppActive_QueryActivePage;
import com.pinting.business.hessian.site.message.ResMsg_AppActive_QueryActivePage;
import com.pinting.business.service.site.AppActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:      shh
 * Date:        2017/7/7
 * Description: H5活动中心
 */
@Component("AppActive")
public class AppActiveFacade {

    @Autowired
    private AppActiveService appActiveService;

    /**
     * 活动中心列表
     * @param req
     * @param res
     */
    public void queryActivePage(ReqMsg_AppActive_QueryActivePage req, ResMsg_AppActive_QueryActivePage res) {
        appActiveService.queryActivePage(req, res);
    }

}
