package com.pinting.business.service.site.impl;

import com.pinting.business.dao.BsSpecialJnlMapper;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class SpecialJnlServiceImpl implements SpecialJnlService {
    private Logger log = LoggerFactory.getLogger(SpecialJnlServiceImpl.class);
    @Autowired
    private BsSpecialJnlMapper bsSpecialJnlMapper;
    @Autowired
    private SMSService smsService;

    @Override
    public void warn4Fail(Double amount, String detail, String orderNo,
                          String type, boolean isTask) {
        try {
            log.error("告警>>>>>>" + detail);
            // 告警表插入
            BsSpecialJnl specialJnl = new BsSpecialJnl();
            specialJnl.setAmount(amount);
            specialJnl.setDetail(detail);
            specialJnl.setOrderNo(orderNo);
            specialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_CREATE);
            specialJnl.setType(type);
            specialJnl.setOpTime(new Date());
            specialJnl.setUpdateTime(new Date());
            bsSpecialJnlMapper.insertSelective(specialJnl);
            // 短信
            String message = type; //+ "失败或异常，详情请检查告警表";
            smsService.sendSysManagerMobiles(message, isTask); //发送短信接口已改
        } catch (Exception e) {
            log.warn("==================告警发生异常====================", e);
        }
    }

    @Override
    public void warnFinance4Fail(Double amount, String detail, String orderNo,
                                 String type, boolean isTask) {
        try {
            // 告警表插入
            BsSpecialJnl specialJnl = new BsSpecialJnl();
            specialJnl.setAmount(amount);
            specialJnl.setDetail(detail);
            specialJnl.setOrderNo(orderNo);
            specialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_CREATE);
            specialJnl.setType(type);
            specialJnl.setOpTime(new Date());
            specialJnl.setUpdateTime(new Date());
            bsSpecialJnlMapper.insertSelective(specialJnl);
            // 短信
            String message = type; //+ "失败或异常，详情请检查告警表";
            smsService.sendFinanceMobiles(message, isTask); //发送短信接口已改
        } catch (Exception e) {
            log.warn("==================告警发生异常====================", e);
        }
    }

    @Override
    public void warn4FailNoSMS(Double amount, String detail, String orderNo,
                               String type) {
        try {
            log.error("告警>>>>>>" + detail);
            // 告警表插入
            BsSpecialJnl specialJnl = new BsSpecialJnl();
            specialJnl.setAmount(amount);
            specialJnl.setDetail(detail);
            specialJnl.setOrderNo(orderNo);
            specialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_CREATE);
            specialJnl.setType(type);
            specialJnl.setOpTime(new Date());
            specialJnl.setUpdateTime(new Date());
            bsSpecialJnlMapper.insertSelective(specialJnl);
        } catch (Exception e) {
            log.warn("==================告警发生异常====================", e);
        }
    }

    @Override
    public void warnDevelop4Fail(Double amount, String detail, String orderNo,
                                 String type, boolean isTask) {
        warnAppoint4Fail(amount, detail, orderNo,
                type, isTask, Constants.EMERGENCY_MOBILE);
    }

    @Override
    public void warnProductOperator4Fail(Double amount, String detail, String orderNo,
                                         String type, boolean isTask) {
        warnAppoint4Fail(amount, detail, orderNo,
                type, isTask, Constants.PRODUCT_OPERATOR_MOBILE);
    }

    @Override
    public void warnCustomerService4Fail(Double amount, String detail, String orderNo,
                                         String type, boolean isTask) {
        warnAppoint4Fail(amount, detail, orderNo,
                type, isTask, Constants.CUSTOMER_SERVICE_MOBILE);
    }

    @Override
    public void warnAppoint4Fail(Double amount, String detail, String orderNo,
                                 String type, boolean isTask, String... warnMobiles) {
        try {
            log.error("告警>>>>>>{},类型：{},指派级别：{}", detail, type, warnMobiles);
            // 告警表插入
            BsSpecialJnl specialJnl = new BsSpecialJnl();
            specialJnl.setAmount(amount);
            specialJnl.setDetail(detail);
            specialJnl.setOrderNo(orderNo);
            specialJnl.setStatus(Constants.SPECIAL_JNL_STATUS_CREATE);
            specialJnl.setType(type);
            specialJnl.setOpTime(new Date());
            specialJnl.setUpdateTime(new Date());
            bsSpecialJnlMapper.insertSelective(specialJnl);
            // 短信
            String message = detail; //+ "失败或异常，详情请检查告警表";
            smsService.sendWarnMobiles(message, isTask, warnMobiles);
        } catch (Exception e) {
            log.warn("==================告警发生异常====================", e);
        }
    }

    @Override
    public void warnAppoint4Async(final Double amount, final String detail, final String orderNo,
                                   final String type, final boolean isTask, final String... warnMobiles) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                warnAppoint4Fail(amount, detail, orderNo, type, isTask, warnMobiles);
            }
        }).start();
    }
}
