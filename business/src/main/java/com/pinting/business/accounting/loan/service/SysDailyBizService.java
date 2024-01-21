package com.pinting.business.accounting.loan.service;

import com.pinting.business.model.BsLiquidationRecord;
import com.pinting.business.model.BsPaymentChannel;
import com.pinting.business.model.BsRevenueTransRecord;
import com.pinting.business.model.dto.TransPartnerRevenueInfo;
import java.text.ParseException;

/**
 * Created by 剑钊
 *
 * @2016/9/7 11:01.
 */
public interface SysDailyBizService {

    /**
     * 生成合作方营收计划
     */
    void checkAndGenerateRevenuePlan() throws ParseException;

    /**
     * 合作方营收日终结算转账
     * @param record 营收转账记录
     */
    void transDailyPartnerRevenue(BsRevenueTransRecord record) throws ParseException;

    /**
     * 合作方营收日终结算转账结果处理
     * @param transPartnerRevenueInfo 合作方营收转账信息
     */
    void notifyTransDailyPartnerRevenue(TransPartnerRevenueInfo transPartnerRevenueInfo);

    /**
     * 合作方对账文件生成
     */
    void generatePartnerDailyActFile();


    /**
     * 每日宝付对账
     */
    void checkBaoFooDailyAccounting(BsPaymentChannel bsPaymentChannel);
    
    /**
     * 每日宝付对账-辅助通道
     */
    void checkBaoFooDailyAccountingAssist(BsPaymentChannel bsPaymentChannel);

    /**
     * 下载对账文件
     */
    void downloadCheckAccountFile(BsPaymentChannel bsPaymentChannel);

    /**
     * 下载赞分期对账文件
     */
    void downLoadZanCheckAccountFile();

    /**
     * 垫付金自动调账
     */
    void advanceAutoAdjustAcc(BsLiquidationRecord record);
    /**
     * 下载对账文件-辅助通道
     */
	void downloadCheckAccountFileAssist(BsPaymentChannel bsPaymentChannel);
	
	/**
	 * 主商户生成对账轧差, 按业务汇总
	 * @param merchantNo
	 */
	void generateMainCheckGacha(String merchantNo);
	
	/**
	 * 辅商户生成对账轧差, 按业务汇总
	 * @param merchantNo
	 */
	void generateAssistCheckGacha(String merchantNo, Integer paymentChannelId);
	
}
