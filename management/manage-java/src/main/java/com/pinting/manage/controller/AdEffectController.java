package com.pinting.manage.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.pinting.business.hessian.manage.message.ReqMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.business.hessian.manage.message.ResMsg_AdEffect_SelectAdEffectInfo;
import com.pinting.core.hessian.service.HessianService;

@Controller
@RequestMapping("/adEffect")
public class AdEffectController {
    @Autowired
    @Qualifier("dispatchService")
    private HessianService dispatchService;

    /**
     * 
     * @param req
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/index")
    public String adEffectIndex(ReqMsg_AdEffect_SelectAdEffectInfo req, HttpServletRequest request,
                                Map<String, Object> model) {
        ResMsg_AdEffect_SelectAdEffectInfo res = (ResMsg_AdEffect_SelectAdEffectInfo) dispatchService
            .handleMsg(req);
        model.put("count", res.getCount());
        model.put("dataGrid", res.getDataGrid());
        model.put("req", req);
        return "adEffect/index";
    }

}
