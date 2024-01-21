package com.pinting.business.service.site;

public interface SpecialJnlService {
    /**
     * 失败告警
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     * @param isTask  true-定时任务异常警告，false-一般警告
     */
    public void warn4Fail(Double amount, String detail, String orderNo, String type, boolean isTask);

    /**
     * 失败告警，短信通知财务人员
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     * @param isTask  true-定时任务异常警告，false-一般警告
     */
    public void warnFinance4Fail(Double amount, String detail, String orderNo, String type, boolean isTask);

    /**
     * 失败告警 无短信通知
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     */
    public void warn4FailNoSMS(Double amount, String detail, String orderNo, String type);

    /**
     * 失败告警，短信通知开发技术人员
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     * @param isTask  true-定时任务异常警告，false-一般警告
     */
    public void warnDevelop4Fail(Double amount, String detail, String orderNo, String type, boolean isTask);

    /**
     * 失败告警，短信通知产品/运营人员
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     * @param isTask  true-定时任务异常警告，false-一般警告
     */
    public void warnProductOperator4Fail(Double amount, String detail, String orderNo, String type, boolean isTask);

    /**
     * 失败告警，短信通知客服人员
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     * @param isTask  true-定时任务异常警告，false-一般警告
     */
    public void warnCustomerService4Fail(Double amount, String detail, String orderNo, String type, boolean isTask);

    /**
     * 失败告警，短信通知指派人员（同步）
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     * @param isTask      true-定时任务异常警告，false-一般警告
     * @param warnMobiles 告警级别常量KEY值（EMERGENCY_MOBILE，PRODUCT_OPERATOR_MOBILE，CUSTOMER_SERVICE_MOBILE，FINANCE_MOBILE）
     */
    public void warnAppoint4Fail(Double amount, String detail, String orderNo, String type, boolean isTask, String... warnMobiles);

    /**
     * 失败告警，短信通知指派人员（异步）
     *
     * @param amount
     * @param detail
     * @param orderNo
     * @param type
     * @param isTask true-定时任务异常警告，false-一般警告
     * @param warnMobiles 告警级别常量KEY值（EMERGENCY_MOBILE，PRODUCT_OPERATOR_MOBILE，CUSTOMER_SERVICE_MOBILE，FINANCE_MOBILE）
     */
    public void warnAppoint4Async(final Double amount, final String detail, final String orderNo,
                                   final String type, final boolean isTask, final String... warnMobiles);
}
