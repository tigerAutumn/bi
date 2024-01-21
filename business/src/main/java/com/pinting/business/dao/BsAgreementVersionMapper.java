package com.pinting.business.dao;

import com.pinting.business.model.BsAgreementVersion;
import com.pinting.business.model.BsAgreementVersionExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsAgreementVersionMapper {
    int countByExample(BsAgreementVersionExample example);

    int deleteByExample(BsAgreementVersionExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsAgreementVersion record);

    int insertSelective(BsAgreementVersion record);

    List<BsAgreementVersion> selectByExample(BsAgreementVersionExample example);

    BsAgreementVersion selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsAgreementVersion record, @Param("example") BsAgreementVersionExample example);

    int updateByExample(@Param("record") BsAgreementVersion record, @Param("example") BsAgreementVersionExample example);

    int updateByPrimaryKeySelective(BsAgreementVersion record);

    int updateByPrimaryKey(BsAgreementVersion record);

    /**
     * 根据协议类型、协议生效时间 查询协议的版本号
     * @param agreementType 协议类型
     * @param effectiveTime 协议生效时间
     * @return
     */
    BsAgreementVersion selectAgreementVersion(@Param("agreementType") String agreementType, @Param("effectiveTime") String effectiveTime);

    /**
     * 根据协议类型、 协议版本号查询协议的生效时间
     * @param agreementType
     * @param agreementVersion
     * @return
     */
    BsAgreementVersion selectAgreementVersionDate(@Param("agreementType") String agreementType, @Param("agreementVersion") String agreementVersion);

}