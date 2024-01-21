package com.pinting.business.facade.site;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsAgreementVersion;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.site.AgreementVersionService;
import com.pinting.business.util.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Author:      shh
 * Date:        2018/1/15
 * Description: 协议版本facade
 */
@Component("AgreementVersion")
public class AgreementVersionFacade {

    @Autowired
    private AgreementVersionService agreementVersionService;

    @Autowired
    private BsSysConfigService sysConfigService;

    public void queryAgreementVersionInfo(ReqMsg_AgreementVersion_QueryAgreementVersionInfo req,
                                          ResMsg_AgreementVersion_QueryAgreementVersionInfo res) {
        BsAgreementVersion bsAgreementVersion =
                agreementVersionService.queryAgreementVersion(req.getAgreementType(), req.getAgreementEffectiveStartTime());
        if(bsAgreementVersion == null) {
            res.setAgreementVersion(Constants.AGREEMENT_VERSION_NUMBER_NO_VERSION);
        }else {
            res.setAgreementVersion(bsAgreementVersion.getAgreementVersion());
        }
    }

    // 购买的云贷、7贷理财产品提前终止违约金百分比查询
    public void queryBerachContractRate(ReqMsg_AgreementVersion_QueryBerachContractRate req,
                                        ResMsg_AgreementVersion_QueryBerachContractRate res) {
        BsSysConfig sysConfig = sysConfigService.findKey(Constants.TERMINATION_BREACH_CONTRACT_RATE);
        if(sysConfig == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        } else {
            res.setBerachContractRate(sysConfig.getConfValue());
        }
    }

    // 云贷借款服务费率
    public void queryLoanServiceRate(ReqMsg_AgreementVersion_QueryLoanServiceRate req, ResMsg_AgreementVersion_QueryLoanServiceRate res) {
        BsSysConfig sysConfig = sysConfigService.findKey(Constants.LOAN_SERVICE_RATE_YUN_DAI);
        if(sysConfig == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        } else {
            res.setLoanServiceRate(sysConfig.getConfValue());
        }
    }

    // 7贷借款服务费率
    public void querySevenLoanServiceRate(ReqMsg_AgreementVersion_QuerySevenLoanServiceRate req, ResMsg_AgreementVersion_QuerySevenLoanServiceRate res) {
        BsSysConfig sysConfig = sysConfigService.findKey(Constants.LOAN_SERVICE_RATE_SEVEN_DAI);
        if(sysConfig == null) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        } else {
            res.setSevenLoanServiceRate(sysConfig.getConfValue());
        }
    }

}
