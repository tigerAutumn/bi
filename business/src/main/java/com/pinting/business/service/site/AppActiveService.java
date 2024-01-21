package com.pinting.business.service.site;

import com.pinting.business.hessian.site.message.ReqMsg_AppActive_QueryActivePage;
import com.pinting.business.hessian.site.message.ResMsg_AppActive_QueryActivePage;

/**
 * Author:      shh
 * Date:        2017/7/7
 * Description: H5活动中心
 */
public interface AppActiveService {

    /**
     * 活动中心-分页查询
     * @param req
     * @param res
     * @return
     */
    public void queryActivePage(ReqMsg_AppActive_QueryActivePage req, ResMsg_AppActive_QueryActivePage res);

}
