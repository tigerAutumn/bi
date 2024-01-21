package com.pinting.business.coreflow.compensate.util;

public interface ConstantsForCompensate {
    String TRANS_CODE_COMPENSATE_NOTICE = "lateRepay"; // 交易类型—代偿通知
    String TRANS_CODE_COMPENSATE_REPAY_SPLIT = "laterepayRepaySplit"; // 交易类型—代偿还款系统分账
    String TRANS_CODE_COMPENSATE_AGREEMENT = "laterepayAgreement"; // 交易类型—单笔借款代偿协议
    String TRANS_CODE_COMPENSATE_NOTIFY_AGREEMENT_DOWNLOAD = "laterepayNotifyAgreementDownload"; // 交易类型—代偿协议通知下载
}
