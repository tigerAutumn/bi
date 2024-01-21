package com.pinting.business.coreflow.compensate.model.vo;

import com.pinting.business.model.LnCompensateDetail;

/**
 * 代偿还款分账辅助VO
 */
public class CompensateRepaySplitVO {
    private LnCompensateDetail agreementDetail; // 生成协议信息
    private LnCompensateDetail agreementStageDetail; // 分期产品生成协议信息

    public LnCompensateDetail getAgreementDetail() {
        return agreementDetail;
    }

    public void setAgreementDetail(LnCompensateDetail agreementDetail) {
        this.agreementDetail = agreementDetail;
    }

    public LnCompensateDetail getAgreementStageDetail() {
        return agreementStageDetail;
    }

    public void setAgreementStageDetail(LnCompensateDetail agreementStageDetail) {
        this.agreementStageDetail = agreementStageDetail;
    }
}
