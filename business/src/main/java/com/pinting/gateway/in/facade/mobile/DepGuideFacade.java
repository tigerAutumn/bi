package com.pinting.gateway.in.facade.mobile;

import com.pinting.business.hessian.site.message.ReqMsg_DepGuide_Risk;
import com.pinting.business.hessian.site.message.ResMsg_DepGuide_Risk;
import com.pinting.business.service.site.AssetsService;
import com.pinting.gateway.in.util.InterfaceVersion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by cyb on 2018/2/23.
 */
@Component("appDepGuide")
public class DepGuideFacade {

    @Autowired
    private AssetsService assetsService;

    @InterfaceVersion("Risk/1.0.0")
    public void risk(ReqMsg_DepGuide_Risk req, ResMsg_DepGuide_Risk res) {
        res.setRiskStatus(assetsService.riskStatus(req.getUserId()));
    }

}
