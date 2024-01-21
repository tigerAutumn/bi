package com.pinting.business.facade.site;

import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.DepGuideVO;
import com.pinting.business.model.vo.WaiteActivateInfoVO;
import com.pinting.business.service.site.AssetsService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.HFBankDepSiteService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.util.Constants;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/12
 * Description:
 */
@Component("DepGuide")
public class DepGuideFacade {

    @Autowired
    private HFBankDepSiteService hfBankDepSiteService;
    @Autowired
    private SMSService smsService;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private BsUserMapper userMapper;
    @Autowired
    private AssetsService assetsService;

    public void findDepGuideInfo(ReqMsg_DepGuide_FindDepGuideInfo req, ResMsg_DepGuide_FindDepGuideInfo res) {
        DepGuideVO result = hfBankDepSiteService.findDepGuideInfo(req.getUserId());
        BeanUtils.copyProperties(result, res);
        if(req.getContainRisk() != null && req.getContainRisk()) {
            res.setRiskStatus(assetsService.riskStatus(req.getUserId()));
        }
    }

    public void waiteActivateInfo(ReqMsg_DepGuide_WaiteActivateInfo req, ResMsg_DepGuide_WaiteActivateInfo res) {
        WaiteActivateInfoVO result = hfBankDepSiteService.waiteActivateInfo(req.getUserId());
        BeanUtils.copyProperties(result, res);
    }

    public void activate(ReqMsg_DepGuide_Activate req, ResMsg_DepGuide_Activate res) {
        BsUser user = userMapper.selectByPrimaryKey(req.getUserId());
        if (user == null) {
            throw new PTMessageException(PTMessageEnum.USER_INFO_NOT_FOUND);
        }
        //短信校验
        boolean IsValidate = smsService.validateIdentity(user.getMobile(), req.getVerifyCode(), true);
        if (!IsValidate) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
        }
        hfBankDepSiteService.activate(req.getUserId());
    }
}
