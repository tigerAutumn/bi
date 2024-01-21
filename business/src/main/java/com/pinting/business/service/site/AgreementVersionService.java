package com.pinting.business.service.site;

import com.pinting.business.model.BsAgreementVersion;

/**
 * Author:      shh
 * Date:        2017/12/11
 * Description: 协议版本控制服务
 */
public interface AgreementVersionService {

    /**
     * 根据协议类型、协议生效时间 查询协议的版本号
     * @param agreementType 协议类型
     * @param effectiveTime 协议生效时间
     * @return
     */
    BsAgreementVersion queryAgreementVersion(String agreementType, String effectiveTime);

    /**
     * 根据协议类型、 协议版本号查询协议的生效时间
     * @param agreementType
     * @param effectiveTime
     * @return
     */
    BsAgreementVersion queryAgreementVersionDate(String agreementType, String effectiveTime);

}
