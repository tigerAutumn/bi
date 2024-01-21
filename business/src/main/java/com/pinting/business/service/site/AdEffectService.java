package com.pinting.business.service.site;

import com.pinting.business.hessian.manage.message.ReqMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.business.hessian.manage.message.ResMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.business.hessian.site.message.ReqMsg_AdEffect_AdEffectSaveInfo;
import com.pinting.business.hessian.site.message.ResMsg_AdEffect_AdEffectSaveInfo;

public interface AdEffectService {

    /**
     * 广告信息影响
     * 
     * @param req
     * @param res
     */
	void adEffectSaveInfo(ReqMsg_AdEffect_AdEffectSaveInfo req,ResMsg_AdEffect_AdEffectSaveInfo res);
    /**
     * 广告列表
     * 
     * @param req
     * @param res
     */
	void selectAdEffectInfo(ReqMsg_AdEffect_SelectAdEffectInfo req,ResMsg_AdEffect_SelectAdEffectInfo res);

}
