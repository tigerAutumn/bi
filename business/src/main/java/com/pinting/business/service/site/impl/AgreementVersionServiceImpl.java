package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsAgreementVersionMapper;
import com.pinting.business.model.BsAgreementVersion;
import com.pinting.business.service.site.AgreementVersionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Author:      shh
 * Date:        2017/12/11
 * Description: 协议版本控制服务
 */
@Service
public class AgreementVersionServiceImpl implements AgreementVersionService {

    @Autowired
    private BsAgreementVersionMapper agreementVersionMapper;

    @Override
    public BsAgreementVersion queryAgreementVersion(String agreementType, String effectiveTime) {
        return agreementVersionMapper.selectAgreementVersion(agreementType, effectiveTime);
    }

    @Override
    public BsAgreementVersion queryAgreementVersionDate(String agreementType, String agreementVersion) {
        return agreementVersionMapper.selectAgreementVersionDate(agreementType, agreementVersion);
    }
}
