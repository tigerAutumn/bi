package com.pinting.business.coreflow.compensate.helper.impl;

import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.redis.core.model.RedisContext;
import com.pinting.business.redis.sign.model.CompensateSignRedisVO;
import com.pinting.business.redis.sign.model.SignRedisVO;
import com.pinting.business.redis.sign.service.SignRedisHelper;
import com.pinting.business.util.Constants;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Created by zousheng on 2018/6/19.
 * 代偿协议签章redis后处理
 */
@Service("CompensateAgreementSignHelperImpl")
public class CompensateAgreementSignHelperImpl implements SignRedisHelper<CompensateSignRedisVO> {

    private final Logger logger = LoggerFactory.getLogger(CompensateAgreementHelperImpl.class);
    @Autowired
    private LnCompensateAgreementAddressMapper lnCompensateAgreementAddressMapper;
    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
    @Autowired
    private LnCompensateDetailMapper lnCompensateDetailMapper;

    /**
     * 业务服务执行方法
     *
     * @param redisContext
     * @return
     */
    public void execute(RedisContext<CompensateSignRedisVO> redisContext) {
        logger.info("代偿协议签章执行后处理----start");

        if (CollectionUtils.isNotEmpty(redisContext.getRedisVOList())) {
            String partnerLoanId = null;

            for (Object obj : redisContext.getRedisVOList()) {

                CompensateSignRedisVO signRedisVO = JSONObject.parseObject(JSONObject.toJSONString(obj), CompensateSignRedisVO.class);

                BsUserSealFile sealFile = bsUserSealFileMapper.selectByPrimaryKey(signRedisVO.getSignFileId());
                if (!SealStatus.SUCC.getCode().equals(sealFile.getSealStatus())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿签章文件失败");
                }

                LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(signRedisVO.getLnLoanId());
                partnerLoanId = lnLoan.getPartnerLoanId();
                LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoan.getLnUserId());

                // 代偿签章文件路径设置到代偿协议下载地址
                LnCompensateAgreementAddressExample agreementAddressExample = new LnCompensateAgreementAddressExample();
                agreementAddressExample.createCriteria().andPartnerCodeEqualTo(lnUser.getPartnerCode())
                        .andPartnerLoanIdEqualTo(lnLoan.getPartnerLoanId())
                        .andAgreementTypeEqualTo(sealFile.getSealType());
                agreementAddressExample.setOrderByClause("id desc");
                List<LnCompensateAgreementAddress> debtTransList = lnCompensateAgreementAddressMapper.selectByExample(agreementAddressExample);
                if (CollectionUtils.isNotEmpty(debtTransList)) {
                    LnCompensateAgreementAddress agreementAddressTemp = new LnCompensateAgreementAddress();
                    agreementAddressTemp.setId(debtTransList.get(0).getId());
                    agreementAddressTemp.setDownloadUrl(sealFile.getFileAddress());
                    agreementAddressTemp.setUpdateTime(new Date());
                    lnCompensateAgreementAddressMapper.updateByPrimaryKeySelective(agreementAddressTemp);
                } else {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "代偿协议下载地址找不到");
                }
            }

            // 查询有本金代偿成功的明细列表
            LnCompensateDetailExample lnCompensateDetailExample = new LnCompensateDetailExample();
            lnCompensateDetailExample.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId)
                    .andStatusEqualTo(Constants.COMPENSATE_REPAYS_STATUS_SUCC)
                    .andPrincipalGreaterThan(0d);
            List<LnCompensateDetail> lnCompensateDetailList = lnCompensateDetailMapper.selectByExample(lnCompensateDetailExample);

            // 修改代偿协议生成成功标识
            for (LnCompensateDetail lnCompensateDetail : lnCompensateDetailList) {
                LnCompensateDetail lnCompensateDetailTemp = new LnCompensateDetail();
                lnCompensateDetailTemp.setId(lnCompensateDetail.getId());
                lnCompensateDetailTemp.setAgreementGenerateStatus(Constants.AGREEMENT_GRNERATE_STATUS_SUCC);
                lnCompensateDetailTemp.setUpdateTime(new Date());
                lnCompensateDetailMapper.updateByPrimaryKeySelective(lnCompensateDetailTemp);
            }
        }
        logger.info("代偿协议签章执行后处理----end");
    }
}
