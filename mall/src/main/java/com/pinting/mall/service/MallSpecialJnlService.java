package com.pinting.mall.service;

public interface MallSpecialJnlService {
	
	/**
     * 失败告警 无短信通知
     *
     * @param points - 积分
     * @param detail - 详细信息
     * @param orderNo- 单号
     * @param type - 类型
     */
    public void warn4FailNoSMS(Double points, String detail, String orderNo, String type);

}
