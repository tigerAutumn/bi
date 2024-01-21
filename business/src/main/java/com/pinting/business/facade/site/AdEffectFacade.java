package com.pinting.business.facade.site;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.business.hessian.manage.message.ResMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.business.hessian.site.message.ReqMsg_AdEffect_AdEffectSaveInfo;
import com.pinting.business.hessian.site.message.ResMsg_AdEffect_AdEffectSaveInfo;
import com.pinting.business.service.site.AdEffectService;

@Component("AdEffect")
public class AdEffectFacade {

    
    @Autowired
    private AdEffectService adEffectService;
    
    /**
     * 付费广告记录新增
     * 
     * @param req
     * @param res
     */
    public void adEffectSaveInfo(ReqMsg_AdEffect_AdEffectSaveInfo req, ResMsg_AdEffect_AdEffectSaveInfo res) {
    	adEffectService.adEffectSaveInfo(req, res);
    }
    /**
     * 付费广告查询
     * 
     * @param req
     * @param res
     */
    public void selectAdEffectInfo(ReqMsg_AdEffect_SelectAdEffectInfo req, ResMsg_AdEffect_SelectAdEffectInfo res) {
    	adEffectService.selectAdEffectInfo(req, res);
    }


}
