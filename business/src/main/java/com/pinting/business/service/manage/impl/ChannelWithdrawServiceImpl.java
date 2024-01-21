/**
 * Wenzi.com Inc.
 * Copyright (c) 2015-2025 All Rights Reserved.
 */
package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.business.accounting.finance.service.UserBalanceWithdrawService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsChannelBankcardMapper;
import com.pinting.business.dao.BsChannelCheckMapper;
import com.pinting.business.dao.BsHelpChannelAccountMapper;
import com.pinting.business.dao.BsPCAMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsSysAccountJnlMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.BsWithdrawCheckMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.ReqMsg_UserBalance_WithdrawPass;
import com.pinting.business.hessian.manage.ResMsg_UserBalance_WithdrawPass;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_AuditWithdrawCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ConfirmTransfer;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_QueryWithdrawCheck;
import com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_WithdrawIndex;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_AuditWithdrawCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ChannelWithdraw;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ConfirmTransfer;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_QueryWithdrawCheck;
import com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_WithdrawIndex;
import com.pinting.business.model.BsChannelBankcard;
import com.pinting.business.model.BsChannelCheck;
import com.pinting.business.model.BsChannelCheckExample;
import com.pinting.business.model.BsHelpChannelAccount;
import com.pinting.business.model.BsHelpChannelAccountExample;
import com.pinting.business.model.BsPCA;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.model.BsSysAccountJnl;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsWithdrawCheck;
import com.pinting.business.model.BsWithdrawCheckExample;
import com.pinting.business.model.vo.BsChannelBankcardVO;
import com.pinting.business.model.vo.BsWithdrawCheckVO;
import com.pinting.business.service.manage.ChannelWithdrawService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GReqMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPayQuery;
import com.pinting.gateway.hessian.message.reapal.B2GResMsg_ReapalAgentPay_AgentPaySubmit;
import com.pinting.gateway.hessian.message.reapal.model.AgentPayQueryDetail;
import com.pinting.gateway.out.service.ReapalTransportService;

/**
 * 管理台渠道提现
 * @author HuanXiong
 * @version $Id: FinanceServiceImpl.java, v 0.1 2016-1-7 上午10:51:17 HuanXiong Exp $
 */
@Service
public class ChannelWithdrawServiceImpl implements ChannelWithdrawService {
    
    @Autowired
    private BsChannelBankcardMapper bsChannelBankcardMapper;
    @Autowired
    private BsHelpChannelAccountMapper bsHelpChannelAccountMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPCAMapper bsPCAMapper;
    @Autowired
    private ReapalTransportService reapalTransportService;
    @Autowired
    private BsChannelCheckMapper bsChannelCheckMapper;
    @Autowired
    private UserBalanceWithdrawService userBalanceWithdrawService;
    @Autowired
    private BsWithdrawCheckMapper bsWithdrawCheckMapper;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    @Autowired
    private BsSysAccountJnlMapper bsSysAccountJnlMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    
    @Override
    public void withdrawIndex(ReqMsg_ChannelWithdraw_WithdrawIndex req, 
                              ResMsg_ChannelWithdraw_WithdrawIndex res) {
        // 0、支付渠道列表
        // res.setChannelList(ChannelEnum.getMapList());
        // 1、融宝渠道单日单笔限额
        if (!StringUtils.isBlank(req.getChannelType()) && Constants.CHANNEL_REAPAL.equals(req.getChannelType())) {
            BsSysConfig dayTopConfig = bsSysConfigMapper.selectByPrimaryKey("REAPAL_ONE_CARD_DF_LIMIT");    // 融宝单日单卡
            BsSysConfig oneTopConfig = bsSysConfigMapper.selectByPrimaryKey("REAPAL_ONE_DF_LIMIT");         // 融宝单日单笔
            res.setDayTop(dayTopConfig.getConfValue());
            res.setOneTop(oneTopConfig.getConfValue());
        }
        
        // 2、渠道银行列表
        List<BsChannelBankcardVO> channelBankcardVOs = bsChannelBankcardMapper.selectChannelBankCardByChannel(req.getChannelType());
        ArrayList<HashMap<String,Object>> channelBankCardList = BeanUtils.classToArrayList(channelBankcardVOs);
        res.setChannelBankCardList(channelBankCardList);
        
        // 3、最高可提现金额
        Map<String, Object> map = bsPayOrdersMapper.selectFinanceWithdrawAmount(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)), DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), 0)), req.getChannelType());
        if (map != null) {
            Double buyAmount = (Double)map.get("buyAmount") == null ? 0d : (Double)map.get("buyAmount");
            Double rechargeAmount = (Double)map.get("rechargeAmount") == null ? 0d : (Double)map.get("rechargeAmount");
            Double withdrawOverAmount = (Double)map.get("withdrawOverAmount") == null ? 0d : (Double)map.get("withdrawOverAmount");
            Double withdrawProcessAmount = (Double)map.get("withdrawProcessAmount") == null ? 0d : (Double)map.get("withdrawProcessAmount");
            Double amount = MoneyUtil.subtract(MoneyUtil.subtract(MoneyUtil.add(buyAmount, rechargeAmount).doubleValue(), withdrawOverAmount).doubleValue(), withdrawProcessAmount).doubleValue();
            
            Double buy_recharge = MoneyUtil.add(buyAmount, rechargeAmount).doubleValue();
            Double over_process = MoneyUtil.add(withdrawOverAmount, withdrawProcessAmount).doubleValue();
            
            // 查出今天成功的bs_channel_check数据
            BsChannelCheckExample confirmBsChannelCheckExample = new BsChannelCheckExample();
            String date = DateUtil.formatYYYYMMDD(new Date()) + " 00:00:00";
            Date todayStart = DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
            Date todayEnd = new Date();
            confirmBsChannelCheckExample.createCriteria().andCreateTimeBetween(todayStart, todayEnd).andStatusEqualTo(Constants.CHANNEL_CHECK_STATUS_CONFIRMED);
            List<BsChannelCheck> bsConfirmedChannelChecks = bsChannelCheckMapper.selectByExample(confirmBsChannelCheckExample);
            BsChannelCheckExample finishBsChannelCheckExample = new BsChannelCheckExample();
            finishBsChannelCheckExample.createCriteria().andCreateTimeBetween(todayStart, todayEnd).andStatusEqualTo(Constants.CHANNEL_CHECK_STATUS_FINISHED);
            List<BsChannelCheck> bsFinishedChannelChecks = bsChannelCheckMapper.selectByExample(finishBsChannelCheckExample);
            //  购买+充值-提现 = 0 时
            if(amount.compareTo(0d) == 0){
                // 判断 购买+充值 = 完成的提现，显示已完成提现OVER
                if(buy_recharge.compareTo(withdrawOverAmount) == 0){
                    // 今天存在成功的对账，则证明今天转账成功了，完成转账，完成提现（都灰色）
                    if (!CollectionUtils.isEmpty(bsConfirmedChannelChecks) || !CollectionUtils.isEmpty(bsFinishedChannelChecks)) {
                        res.setWithdrawStatus("CONFIRM_OVER");
                    } else {
                        res.setWithdrawStatus("OVER");
                    }
                }
                // 判断 购买+充值 = 完成的提现+处理的提现，显示PROCESS
                else if(over_process.compareTo(over_process) == 0) {
                    res.setWithdrawStatus("PROCESS");
                }
            } else if(amount.compareTo(0d) > 0) {
                // 购买+充值-提现 > 0 时，且购买+充值-提现 != 购买+充值 显示提现 WITHDRAW
                res.setWithdrawStatus("WITHDRAW");
            }
            res.setAmount(amount);
        } else {
            res.setWithdrawStatus("NULL");
            res.setAmount(0d);
        }
        
    }

    /**
     * 1、获取可提现金额
     * 2、修改辅助通道系统账户表bs_help_channel_acccount，可提现金额减少，冻结金额增加
     * 3、从系统配置表中获取当前支付渠道的代付限额，当提现金额大于单日单卡代付限额时，
     *    实际代付为配置中的单日单卡代付限额为准，按照单笔限额做多笔循环代付提现
     */
    /** 
     * @see com.pinting.business.service.manage.ChannelWithdrawService#channelWithdraw(com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ChannelWithdraw, com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ChannelWithdraw)
     */
    @Override
    public void channelWithdraw(ReqMsg_ChannelWithdraw_ChannelWithdraw req,
                                ResMsg_ChannelWithdraw_ChannelWithdraw res) {
        // ====================== 数据校验 开始 =============================
        if (req.getChannelBankCardId() == null || StringUtil.isBlank(req.getChannelType())) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        // ====================== 数据校验 结束 =============================
        
        // 1 获取可提现金额
        Map<String, Object> map = bsPayOrdersMapper.selectFinanceWithdrawAmount(DateUtil.formatYYYYMMDD(DateUtil.addDays(new Date(), -1)), DateUtil.formatYYYYMMDD(new Date()), req.getChannelType());
        Double buyAmount = (Double)map.get("buyAmount") == null ? 0d : (Double)map.get("buyAmount");
        Double rechargeAmount = (Double)map.get("rechargeAmount") == null ? 0d : (Double)map.get("rechargeAmount");
        Double withdrawOverAmount = (Double)map.get("withdrawOverAmount") == null ? 0d : (Double)map.get("withdrawOverAmount");
        Double withdrawProcessAmount = (Double)map.get("withdrawProcessAmount") == null ? 0d : (Double)map.get("withdrawProcessAmount");
        Double amount = MoneyUtil.subtract(MoneyUtil.subtract(MoneyUtil.add(buyAmount, rechargeAmount).doubleValue(), withdrawOverAmount).doubleValue(), withdrawProcessAmount).doubleValue();
        if (amount <= 0d) {
            throw new PTMessageException(PTMessageEnum.WITHDRAW_AMOUNT_SURPASS);
        }
        
        // 3.1 获取当前支付渠道的代付限额，单日限额+单笔限额
        BsSysConfig dayTopConfig = new BsSysConfig();
        BsSysConfig oneTopConfig = new BsSysConfig();
        if (!StringUtils.isBlank(req.getChannelType()) && Constants.CHANNEL_REAPAL.equals(req.getChannelType())) {
            dayTopConfig = bsSysConfigMapper.selectByPrimaryKey("REAPAL_ONE_CARD_DF_LIMIT");    // 融宝单日单卡
            oneTopConfig = bsSysConfigMapper.selectByPrimaryKey("REAPAL_ONE_DF_LIMIT");         // 融宝单日单笔
        }
        Double dayTop = new Double(dayTopConfig.getConfValue());
        Double oneTop = new Double(oneTopConfig.getConfValue());
        
        // 3.2 当提现金额大于单日单卡代付限额时，实际代付为配置中的单日单卡代付限额为准，按照单笔限额做多笔循环代付提现
        Integer size = 0;
        if(Double.compare(amount, dayTop) > 0) {
            Double sizeDouble = MoneyUtil.divide(MoneyUtil.subtract(dayTop, dayTop % oneTop).doubleValue(), oneTop).doubleValue();
            size = sizeDouble.intValue();
            if (dayTop % oneTop > 0) {
                size = size + 1;
            }
        }
        // 3.3 当提现金额小于等于单日单卡代付限额时，
        else {
            // 判断amount是否小于等于oneTop
            if (Double.compare(amount, oneTop) <= 0) {
                size = 1;
            } else {
                Double sizeDouble = MoneyUtil.divide(MoneyUtil.subtract(amount, amount % oneTop).doubleValue(), oneTop).doubleValue();
                //Double sizeDouble = (amount - amount % oneTop) / oneTop;
                size = sizeDouble.intValue();
                if (amount % oneTop > 0) {
                    size = size + 1;
                }
            }
        }
        // 4 提现操作，调用接口
        BsChannelBankcard bsChannelBankcard = bsChannelBankcardMapper.selectByPrimaryKey(req.getChannelBankCardId());
        BsPCA provinceBsPCA = bsPCAMapper.selectByPrimaryKey(bsChannelBankcard.getOpenProvince());
        BsPCA cityBsPCA = bsPCAMapper.selectByPrimaryKey(bsChannelBankcard.getOpenCity());
        String province = provinceBsPCA.getItemName();
        String city = cityBsPCA.getItemName();
        int flag = 0;
        String failMsg = "";
        
        BsHelpChannelAccountExample bsHelpChannelAccountExample = new BsHelpChannelAccountExample();
        List<BsHelpChannelAccount> bsHelpChannelAccounts = bsHelpChannelAccountMapper.selectByExample(bsHelpChannelAccountExample);
        BsHelpChannelAccount bsHelpChannelAccount = bsHelpChannelAccounts.get(0);
        // 多笔循环代付提现
        for (int i = 0; i < size; i++) {
            // 提现操作
            B2GReqMsg_ReapalAgentPay_AgentPaySubmit reqMsg = new B2GReqMsg_ReapalAgentPay_AgentPaySubmit();
            reqMsg.setBatchCurrnum(Util.generateSysOrderNo("CW"));
            reqMsg.setBatchCount(1);
            if(Double.compare(amount, dayTop) > 0){
                if(oneTop.compareTo(MoneyUtil.subtract(dayTop, i * oneTop).doubleValue()) > 0 
                        && (MoneyUtil.subtract(dayTop, i * oneTop).doubleValue()) > 0) {
                    reqMsg.setBatchAmount(MoneyUtil.subtract(dayTop, i * oneTop).doubleValue());
                    reqMsg.setAmount(MoneyUtil.subtract(dayTop, i * oneTop).doubleValue());
                } else {
                    reqMsg.setBatchAmount(oneTop);
                    reqMsg.setAmount(oneTop);
                }
            } else {
                // a.2.1 可提现金额小于等于单笔限额，直接设置为可提现金额
                if(amount.compareTo(oneTop) <= 0){
                    reqMsg.setBatchAmount(amount);
                    reqMsg.setAmount(amount);
                }
                // a.2.2 可提现金额大于单笔限额，判断单笔限额是否大于（可提现金额减去（第几次*单笔限额））
                else {
                    if(oneTop.compareTo(MoneyUtil.subtract(amount, i * oneTop).doubleValue()) > 0 
                            && (MoneyUtil.subtract(amount, i * oneTop).doubleValue()) > 0){
                        reqMsg.setBatchAmount(MoneyUtil.subtract(amount, i * oneTop).doubleValue());
                        reqMsg.setAmount(MoneyUtil.subtract(amount, i * oneTop).doubleValue());
                    } else {
                        reqMsg.setBatchAmount(oneTop);
                        reqMsg.setAmount(oneTop);
                    }
                }
            }
            
            reqMsg.setSerialNo("1");
            reqMsg.setAccountNo(bsChannelBankcard.getCardNo());
            reqMsg.setAccountName(bsChannelBankcard.getCardOwner());
            reqMsg.setBankName(bsChannelBankcard.getBankName());
            reqMsg.setSubBranchBankName(bsChannelBankcard.getSubBranchName());
            reqMsg.setAccountType("私");
            reqMsg.setProvince(province);
            reqMsg.setCity(city);
            reqMsg.setNote("渠道互转提现");
            B2GResMsg_ReapalAgentPay_AgentPaySubmit b2GResMsg = reapalTransportService.agentPaySubmit(reqMsg);
            // 单笔提现处理中
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(b2GResMsg.getResCode())) {
                // 插入系统记账流水表  
                BsSysAccountJnl accountJnl = new BsSysAccountJnl();
                accountJnl.setBeforeBalance1(bsHelpChannelAccount.getBalance());
                accountJnl.setBeforeAvialableBalance1(bsHelpChannelAccount.getAvailableBalance());
                accountJnl.setBeforeFreezeBalance1(bsHelpChannelAccount.getFreezeBalance());
                //accountJnl.setBeforeAvialableBalance1(MoneyUtil.subtract(bsHelpChannelAccount.getAvailableBalance(), i*reqMsg.getAmount()).doubleValue());
                //accountJnl.setBeforeFreezeBalance1(MoneyUtil.add(bsHelpChannelAccount.getFreezeBalance(), i*reqMsg.getAmount()).doubleValue());
                accountJnl.setTransTime(new Date());
                accountJnl.setTransCode(Constants.SYS_CHANNEL_WITHDRAW);
                accountJnl.setTransName("系统渠道取现，冻结金额成功");
                accountJnl.setTransAmount(reqMsg.getAmount());
                accountJnl.setSysTime(new Date());
                accountJnl.setChannelTime(new Date());
                accountJnl.setChannelJnlNo(reqMsg.getBatchCurrnum());
                accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                accountJnl.setAfterBalance1(bsHelpChannelAccount.getBalance());
                accountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(bsHelpChannelAccount.getAvailableBalance(), reqMsg.getAmount()).doubleValue());
                accountJnl.setAfterFreezeBalance1(MoneyUtil.add(bsHelpChannelAccount.getFreezeBalance(), reqMsg.getAmount()).doubleValue());
                //accountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(bsHelpChannelAccount.getAvailableBalance(), MoneyUtil.multiply(reqMsg.getAmount(), i+1).doubleValue()).doubleValue());
                //accountJnl.setAfterFreezeBalance1(MoneyUtil.add(bsHelpChannelAccount.getFreezeBalance(), MoneyUtil.multiply(reqMsg.getAmount(), i+1).doubleValue()).doubleValue());
                accountJnl.setFee(0d);
                accountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_PAYING);
                bsSysAccountJnlMapper.insertSelective(accountJnl);
                
                // 2 修改辅助渠道系统账户表bs_help_channel_acccount，可提现金额减少，冻结金额增加
                bsHelpChannelAccountMapper.updateHelpChannelAccount(reqMsg.getAmount(), req.getChannelType());
                bsHelpChannelAccount.setAvailableBalance(MoneyUtil.subtract(bsHelpChannelAccount.getAvailableBalance(), reqMsg.getAmount()).doubleValue());
                bsHelpChannelAccount.setFreezeBalance(MoneyUtil.add(bsHelpChannelAccount.getFreezeBalance(), reqMsg.getAmount()).doubleValue());
                
                // 插入处理中订单
                BsPayOrders bsPayOrders = new BsPayOrders();
                bsPayOrders.setAmount(reqMsg.getAmount());
                bsPayOrders.setOrderNo(reqMsg.getBatchCurrnum());
                bsPayOrders.setUserId(req.getUserId());
                bsPayOrders.setSubAccountId(null);
                bsPayOrders.setType(null);
                bsPayOrders.setChannel(Constants.CHANNEL_REAPAL);
                bsPayOrders.setStatus(Constants.ORDER_STATUS_PAYING);   // 处理中
                bsPayOrders.setBankName(bsChannelBankcard.getBankName());
                bsPayOrders.setMoneyType(0);
                bsPayOrders.setTerminalType(Constants.TERMINAL_TYPE_PC);
                bsPayOrders.setCreateTime(new Date());
                bsPayOrders.setUpdateTime(new Date());
                bsPayOrders.setStartJnlNo(null);
                bsPayOrders.setEndJnlNo(null);
                bsPayOrders.setBankId(bsChannelBankcard.getBankId());
                bsPayOrders.setBankCardNo(bsChannelBankcard.getCardNo());
                bsPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
                bsPayOrders.setTransType(Constants.Trans_TYPE_CHANNEL_WITHDRAW);
                bsPayOrders.setChannelTransType(Constants.CHANNEL_TRS_DF);
                bsPayOrders.setMobile(bsChannelBankcard.getMobile());
                bsPayOrders.setIdCard(bsChannelBankcard.getIdCard());
                bsPayOrders.setUserName(bsChannelBankcard.getCardOwner());
                bsPayOrders.setReturnCode(b2GResMsg.getResCode());
                bsPayOrders.setReturnMsg(b2GResMsg.getResMsg());
                bsPayOrdersMapper.insertSelective(bsPayOrders);
                
            }
            // 单笔提现失败，还原这一笔辅助渠道系统对账表的操作
            else {
                // TODO 插入系统记账流水表  
                BsSysAccountJnl accountJnl = new BsSysAccountJnl();
                accountJnl.setBeforeBalance1(bsHelpChannelAccount.getBalance());
                accountJnl.setBeforeAvialableBalance1(bsHelpChannelAccount.getAvailableBalance());
                accountJnl.setBeforeFreezeBalance1(bsHelpChannelAccount.getFreezeBalance());
                //accountJnl.setBeforeAvialableBalance1(MoneyUtil.subtract(bsHelpChannelAccount.getAvailableBalance(), i*reqMsg.getAmount()).doubleValue());
                //accountJnl.setBeforeFreezeBalance1(MoneyUtil.add(bsHelpChannelAccount.getFreezeBalance(), i*reqMsg.getAmount()).doubleValue());
                
                accountJnl.setTransTime(new Date());
                accountJnl.setTransCode(Constants.SYS_CHANNEL_WITHDRAW);
                accountJnl.setTransName("系统渠道取现：冻结金额失败");
                accountJnl.setTransAmount(amount);
                accountJnl.setSysTime(new Date());
                accountJnl.setChannelTime(new Date());
                accountJnl.setChannelJnlNo(reqMsg.getBatchCurrnum());
                accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                accountJnl.setAfterBalance1(bsHelpChannelAccount.getBalance());
                accountJnl.setAfterAvialableBalance1(bsHelpChannelAccount.getAvailableBalance());
                accountJnl.setAfterFreezeBalance1(bsHelpChannelAccount.getFreezeBalance());
                //accountJnl.setAfterAvialableBalance1(MoneyUtil.subtract(bsHelpChannelAccount.getAvailableBalance(), MoneyUtil.multiply(reqMsg.getAmount(), i+1).doubleValue()).doubleValue());
                //accountJnl.setAfterFreezeBalance1(MoneyUtil.add(bsHelpChannelAccount.getFreezeBalance(), MoneyUtil.multiply(reqMsg.getAmount(), i+1).doubleValue()).doubleValue());
                
                accountJnl.setFee(0d);
                accountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_FAIL);
                bsSysAccountJnlMapper.insertSelective(accountJnl);
                
                // 插入失败订单
                BsPayOrders bsPayOrders = new BsPayOrders();
                bsPayOrders.setAmount(reqMsg.getAmount());
                bsPayOrders.setOrderNo(reqMsg.getBatchCurrnum());
                bsPayOrders.setUserId(req.getUserId());
                bsPayOrders.setSubAccountId(null);
                bsPayOrders.setType(null);
                bsPayOrders.setChannel(Constants.CHANNEL_REAPAL);
                bsPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);   // 失败
                bsPayOrders.setBankName(bsChannelBankcard.getBankName());
                bsPayOrders.setMoneyType(0);
                bsPayOrders.setTerminalType(Constants.TERMINAL_TYPE_PC);
                bsPayOrders.setCreateTime(new Date());
                bsPayOrders.setUpdateTime(new Date());
                bsPayOrders.setStartJnlNo(null);
                bsPayOrders.setEndJnlNo(null);
                bsPayOrders.setBankId(bsChannelBankcard.getBankId());
                bsPayOrders.setBankCardNo(bsChannelBankcard.getCardNo());
                bsPayOrders.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
                bsPayOrders.setTransType(Constants.Trans_TYPE_CHANNEL_WITHDRAW);
                bsPayOrders.setChannelTransType(Constants.CHANNEL_TRS_DF);
                bsPayOrders.setMobile(bsChannelBankcard.getMobile());
                bsPayOrders.setIdCard(bsChannelBankcard.getIdCard());
                bsPayOrders.setUserName(bsChannelBankcard.getCardOwner());
                bsPayOrders.setReturnCode(b2GResMsg.getResCode());
                bsPayOrders.setReturnMsg(b2GResMsg.getResMsg());
                bsPayOrdersMapper.insertSelective(bsPayOrders);
                
                failMsg = b2GResMsg.getResMsg();
                flag++;
            }
            try {
                Thread.sleep(3000); // 当前线程睡眠三秒
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(flag != 0 && flag == size) {
            res.setMsg("您的提现操作全部失败："+failMsg);
        } else if(flag != 0 && flag < size){
            res.setMsg("您的提现操作部分失败："+failMsg);
        } else {
            res.setMsg("提现正在处理中，请耐心等待");
        }
        
    }
    
    /**
     * 1、轮询所有提现（CHANNEL_WITHDRAW）为处理中的订单BsPayOrders。
     * 2、每成功一笔，就修改bs_help_channel_account {
     *      2.1 冻结金额减去相应金额，余额和可用余额减去相应金额。(成功)
     *      2.2 冻结金额减去相应金额，可提现金额加上相应金额。        (失败)
     * }
     * 3、修改订单状态以及时间updateTime {
     *      3.1 修改订单状态为6以及时间updateTime（成功）
     *      3.2 修改订单状态为7以及时间updateTime（失败）
     *      3.3 插入系统记账流水表 bs_sys_account_jnl
     * }
     * 4、判断该订单的updateTime是否是当日，若为当日且
     *      4.1 当日所有成功的订单金额      等于
     *      4.2 （
     *           根据订单表中为该支付渠道的updatetime为昨日的所有的充值和购买的支付为成功的记录-当日已经提现CHANNEL_WITHDRAW 完成的-
     *           当日正在提现CHANNEL_WITHDRAW 处理中的
     *      ）
     *      4.3 则在 bs_channel_check中生成一条记录，状态是（TRANSFERED 钱已到账）
     */
    @Override
    public void channelWithdrawPolling() {
        
        // 1 轮询所有提现（CHANNEL_WITHDRAW）为处理中的订单。
        BsPayOrdersExample bsPayOrdersExample = new BsPayOrdersExample();
        bsPayOrdersExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_PAYING).andTransTypeEqualTo(Constants.Trans_TYPE_CHANNEL_WITHDRAW);
        List<BsPayOrders> processBsPayOrdersList = bsPayOrdersMapper.selectByExample(bsPayOrdersExample);
        
        BsHelpChannelAccountExample bsHelpChannelAccountExample = new BsHelpChannelAccountExample();
        List<BsHelpChannelAccount> bsHelpChannelAccounts = bsHelpChannelAccountMapper.selectByExample(bsHelpChannelAccountExample);
        final BsHelpChannelAccount bsHelpChannelAccount = bsHelpChannelAccounts.get(0);
        for (final BsPayOrders processBsPayOrders : processBsPayOrdersList) {
        	try {
        		transactionTemplate.execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        Date today = new Date();
                        
                        // 调用融宝接口查询成功或失败的结果
                        B2GReqMsg_ReapalAgentPay_AgentPayQuery req = new B2GReqMsg_ReapalAgentPay_AgentPayQuery();
                        req.setBatchCurrnum(processBsPayOrders.getOrderNo());
                        req.setBatchDate(processBsPayOrders.getCreateTime());
                        B2GResMsg_ReapalAgentPay_AgentPayQuery res =  reapalTransportService.agentPayQuery(req);
                        AgentPayQueryDetail detail = res.getDetails().get(0);
                        // 2 检查是否存在成功的订单，每成功一笔，就修改bs_help_channel_account
                        if (!StringUtil.isBlank(detail.getTradeFeedbackcode()) && "成功".equals(detail.getTradeFeedbackcode())) {
                            // 插入系统记账流水表
                            BsSysAccountJnl accountJnl = new BsSysAccountJnl();
                            accountJnl.setBeforeBalance1(bsHelpChannelAccount.getBalance());
                            accountJnl.setBeforeAvialableBalance1(bsHelpChannelAccount.getAvailableBalance());
                            accountJnl.setBeforeFreezeBalance1(bsHelpChannelAccount.getFreezeBalance());
                            
                            // 2.1 冻结金额减去相应金额，余额和可用余额减去相应金额。
                            Double balance = bsHelpChannelAccount.getBalance();
                            Double freezeBalance = bsHelpChannelAccount.getFreezeBalance();
                            Double amount = processBsPayOrders.getAmount();
                            bsHelpChannelAccountMapper.updateHelpChannelAccountSuccess(amount, Constants.CHANNEL_REAPAL);
                            bsHelpChannelAccount.setBalance(MoneyUtil.subtract(balance, amount).doubleValue());
                            bsHelpChannelAccount.setFreezeBalance(MoneyUtil.subtract(freezeBalance, amount).doubleValue());
                            
                            // 2.2 修改订单状态以及时间updateTime 成功
                            processBsPayOrders.setStatus(Constants.ORDER_STATUS_SUCCESS);
                            processBsPayOrders.setUpdateTime(today); 
                            bsPayOrdersMapper.updateByPrimaryKey(processBsPayOrders);
                            
                            // 2.3 插入一下系统记账流水表
                            accountJnl.setTransTime(today);
                            accountJnl.setTransCode(Constants.SYS_CHANNEL_WITHDRAW);
                            accountJnl.setTransName("系统渠道取现：提现成功");
                            accountJnl.setTransAmount(processBsPayOrders.getAmount());
                            accountJnl.setSysTime(today);
                            accountJnl.setChannelTime(processBsPayOrders.getUpdateTime());
                            accountJnl.setChannelJnlNo(processBsPayOrders.getOrderNo());
                            accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                            accountJnl.setAfterBalance1(MoneyUtil.subtract(balance, amount).doubleValue());
                            accountJnl.setAfterAvialableBalance1(bsHelpChannelAccount.getAvailableBalance());
                            accountJnl.setAfterFreezeBalance1(MoneyUtil.subtract(freezeBalance, amount).doubleValue());
                            accountJnl.setFee(0d);
                            accountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                            bsSysAccountJnlMapper.insertSelective(accountJnl);
                        } else if(!StringUtil.isBlank(detail.getTradeFeedbackcode()) && "失败".equals(detail.getTradeFeedbackcode())) {
                            // 系统记账流水表
                            BsSysAccountJnl accountJnl = new BsSysAccountJnl();
                            accountJnl.setBeforeBalance1(bsHelpChannelAccount.getBalance());
                            accountJnl.setBeforeAvialableBalance1(bsHelpChannelAccount.getAvailableBalance());
                            accountJnl.setBeforeFreezeBalance1(bsHelpChannelAccount.getFreezeBalance());
                            
                            // 3.1 冻结金额减去相应金额，可提现金额加上相应金额。
                            Double balance = bsHelpChannelAccount.getBalance();
                            Double freezeBalance = bsHelpChannelAccount.getFreezeBalance();
                            Double availableBalance = bsHelpChannelAccount.getAvailableBalance();
                            Double canWithdraw = bsHelpChannelAccount.getCanWithdraw();
                            Double amount = processBsPayOrders.getAmount();
                            bsHelpChannelAccountMapper.updateHelpChannelAccount(-amount, Constants.CHANNEL_REAPAL);
                            bsHelpChannelAccount.setFreezeBalance(MoneyUtil.subtract(freezeBalance, amount).doubleValue());
                            bsHelpChannelAccount.setAvailableBalance(MoneyUtil.add(availableBalance, amount).doubleValue());
                            bsHelpChannelAccount.setCanWithdraw(MoneyUtil.add(canWithdraw, amount).doubleValue());
                            
                            // 3.2 修改订单状态以及时间updateTime 失败
                            BsPayOrders process2Fail = new BsPayOrders();
                            process2Fail.setId(processBsPayOrders.getId());
                            process2Fail.setStatus(Constants.ORDER_STATUS_FAIL);
                            process2Fail.setUpdateTime(today);
                            if("1".equals(detail.getTradeReason())) {
                                process2Fail.setReturnMsg("人工审核拒绝");
                            } else {
                                process2Fail.setReturnMsg(detail.getTradeReason());
                            }
                            bsPayOrdersMapper.updateByPrimaryKeySelective(process2Fail);
                            
                            // 3.3 插入一下系统记账流水表
                            accountJnl.setTransTime(today);
                            accountJnl.setTransCode(Constants.SYS_CHANNEL_WITHDRAW);
                            accountJnl.setTransName("系统渠道取现：提现失败");
                            accountJnl.setTransAmount(processBsPayOrders.getAmount());
                            accountJnl.setSysTime(today);
                            accountJnl.setChannelTime(processBsPayOrders.getUpdateTime());
                            accountJnl.setChannelJnlNo(processBsPayOrders.getOrderNo());
                            accountJnl.setCdFlag1(Constants.CD_FLAG_C);
                            accountJnl.setAfterBalance1(balance);
                            accountJnl.setAfterAvialableBalance1(bsHelpChannelAccount.getAvailableBalance());
                            accountJnl.setAfterFreezeBalance1(bsHelpChannelAccount.getFreezeBalance());
                            accountJnl.setFee(0d);
                            accountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_FAIL);
                            bsSysAccountJnlMapper.insertSelective(accountJnl);
                        }
                        
                        // 4 成功的订单。判断该订单的updateTime是否是当日
                        if (processBsPayOrders.getStatus().equals(Constants.ORDER_STATUS_SUCCESS) 
                                && DateUtil.formatYYYYMMDD(processBsPayOrders.getUpdateTime()).equals(DateUtil.formatYYYYMMDD(today))) {
                            // 4.1 当日的所有的成功的CHANNEL_WITHDRAW订单金额
                            BsPayOrdersExample successPayOrdersExample = new BsPayOrdersExample();
                            successPayOrdersExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS)
                                .andTransTypeEqualTo(Constants.Trans_TYPE_CHANNEL_WITHDRAW)
                                .andUpdateTimeGreaterThanOrEqualTo(DateUtil.parse(DateUtil.formatYYYYMMDD(today)+" 00:00:00", "yyyy-MM-dd HH:mm:ss"))
                                .andUpdateTimeLessThan(DateUtil.parse(DateUtil.formatYYYYMMDD(DateUtil.addDays(today, 1))+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
                            List<BsPayOrders> successsPayOrdersList = bsPayOrdersMapper.selectByExample(successPayOrdersExample);
                            Double totalSuccessAmount = 0d;
                            for (BsPayOrders orders : successsPayOrdersList) 
                                totalSuccessAmount = MoneyUtil.add(totalSuccessAmount, orders.getAmount()).doubleValue();
                            
                            // 4.2获取可提现金额
                            Map<String, Object> map = bsPayOrdersMapper.selectFinanceWithdrawAmount(DateUtil.formatYYYYMMDD(DateUtil.addDays(today, -1)), DateUtil.formatYYYYMMDD(today), processBsPayOrders.getChannel());
                            if (map != null) {
                                Double buyAmount = (Double)map.get("buyAmount") == null ? 0d : (Double)map.get("buyAmount");
                                Double rechargeAmount = (Double)map.get("rechargeAmount") == null ? 0d : (Double)map.get("rechargeAmount");
                                Double buy_recharge = MoneyUtil.add(buyAmount, rechargeAmount).doubleValue();
                                // 4.3 判断当日且当日所有成功的提现订单金额等于buy_recharge，则在 bs_channel_check中生成一条记录，状态是（TRANSFERED 钱已到账）
                                if (totalSuccessAmount.compareTo(buy_recharge) == 0) {
                                    BsChannelCheck bsChannelCheck = new BsChannelCheck();
                                    bsChannelCheck.setChannel(processBsPayOrders.getChannel());
                                    bsChannelCheck.setCreateTime(today);
                                    bsChannelCheck.setUpdateTime(today);
                                    bsChannelCheck.setmUserId(null);
                                    bsChannelCheck.setStatus(Constants.CHANNEL_CHECK_STATUS_TRANSFERED);
                                    bsChannelCheckMapper.insertSelective(bsChannelCheck);
                                }
                            }
                        }
                        return true;
                    }
                });
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}
        }
    }
    
    /**
     * 1、更新bs_channel_check中的记录中的状态为（CONFIRMED 财务已确认）；
     * 2、处理昨日的提现审核记录，将昨日的提现审核记录的ID和该审核员ID传给提现服务
     * @see com.pinting.business.service.manage.ChannelWithdrawService#confirmTransfer(com.pinting.business.hessian.manage.message.ReqMsg_ChannelWithdraw_ConfirmTransfer, com.pinting.business.hessian.manage.message.ResMsg_ChannelWithdraw_ConfirmTransfer)
     */
    @Override
    public void confirmTransfer(ReqMsg_ChannelWithdraw_ConfirmTransfer req,
                                ResMsg_ChannelWithdraw_ConfirmTransfer res) {
        
        // 查出今天的bs_channel_check数据
        BsChannelCheckExample bsChannelCheckExample = new BsChannelCheckExample();
        String date = DateUtil.formatYYYYMMDD(new Date()) + " 00:00:00";
        Date todayStart = DateUtil.parse(date, "yyyy-MM-dd HH:mm:ss");
        Date todayEnd = new Date();
        bsChannelCheckExample.createCriteria().andCreateTimeBetween(todayStart, todayEnd).andStatusEqualTo(Constants.CHANNEL_CHECK_STATUS_TRANSFERED);
        List<BsChannelCheck> bsChannelChecks = bsChannelCheckMapper.selectByExample(bsChannelCheckExample);
        if (CollectionUtils.isEmpty(bsChannelChecks)) {
            throw new PTMessageException(PTMessageEnum.WITHDRAW_CONFIRM_TRANSFER_ERROR);
        }
        
        // 1 确认转账
        for (BsChannelCheck channelCheck : bsChannelChecks) {
            channelCheck.setStatus(Constants.CHANNEL_CHECK_STATUS_CONFIRMED);
            channelCheck.setUpdateTime(new Date());
            bsChannelCheckMapper.updateByPrimaryKeySelective(channelCheck);
        }
        
        // 2 处理今日之前的提现审核记录bs_withdraw_check，将今日之前的提现审核记录的ID和该审核员ID传给提现服务
        // Date yesterdayStart =  DateUtil.addDays(todayStart, -1);
        BsWithdrawCheckExample bsWithdrawCheckExample = new BsWithdrawCheckExample();
        bsWithdrawCheckExample.createCriteria().andCreateTimeLessThan(todayStart).andStatusEqualTo(Constants.WITHDRAW_TO_CHECK);
        List<BsWithdrawCheck> bsWithdrawChecks = bsWithdrawCheckMapper.selectByExample(bsWithdrawCheckExample);
        for (BsWithdrawCheck bsWithdrawCheck : bsWithdrawChecks) {
            ReqMsg_UserBalance_WithdrawPass reqMsg = new ReqMsg_UserBalance_WithdrawPass();
            ResMsg_UserBalance_WithdrawPass resMsg = new ResMsg_UserBalance_WithdrawPass();
            reqMsg.setCheckId(bsWithdrawCheck.getId());
            reqMsg.setManageId(req.getUserId());
            userBalanceWithdrawService.pass(reqMsg, resMsg);
        }
    }
    
    /** 
     * @see com.pinting.business.service.manage.ChannelWithdrawService#queryWithdrawCheck()
     */
    @Override
    public void queryWithdrawCheck(ReqMsg_ChannelWithdraw_QueryWithdrawCheck req,
                                   ResMsg_ChannelWithdraw_QueryWithdrawCheck res) {
        // ========================= 参数校验 开始 ==========================
        if (req.getNumPerPage() == null || req.getStart() == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        // ========================= 参数校验 结束 ==========================
        
        // 按条件查询
        List<BsWithdrawCheckVO> checkVOs = bsWithdrawCheckMapper.selectWithdrawCheckByMobileAndUserNameAndStatus(
            req.getMobile(), req.getUserName(), req.getStatus(), req.getStart(), req.getNumPerPage());
        res.setQueryWithdrawCheckList(BeanUtils.classToArrayList(checkVOs));
        // 查询总条数
        Integer count = bsWithdrawCheckMapper.countWithdrawCheckByMobileAndUserNameAndStatus(
            req.getMobile(), req.getUserName(), req.getStatus());
        res.setCount(count);
    }
    
    @Override
    public void auditWithdrawCheck(ReqMsg_ChannelWithdraw_AuditWithdrawCheck req,
                                   ResMsg_ChannelWithdraw_AuditWithdrawCheck res) {
        if (req.getId() == null || req.getmUserId() == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR);
        }
        
        // 处理昨日的提现审核记录，将昨日的提现审核记录的ID和该审核员ID传给提现服务
        ReqMsg_UserBalance_WithdrawPass reqMsg = new ReqMsg_UserBalance_WithdrawPass();
        ResMsg_UserBalance_WithdrawPass resMsg = new ResMsg_UserBalance_WithdrawPass();
        reqMsg.setCheckId(req.getId());
        reqMsg.setManageId(req.getmUserId());
        userBalanceWithdrawService.pass(reqMsg, resMsg);
    }
    
    

    public static void main(String[] args) {
        
        Date noe = DateUtil.parse("2016-1-12 01:23:32", "yyyy-MM-dd HH:mm");
        System.out.println(noe);
        // 获取提现限制时间
        BsSysConfig bsSysConfigTime = new BsSysConfig();
        bsSysConfigTime.setConfValue("13:11");
//        bsSysConfigTime.setConfValue("3:1");
        bsSysConfigTime.setConfValue("3:00");
        bsSysConfigTime.setConfValue("3:23");
        String[] b = bsSysConfigTime.getConfValue().split(":");
        String begin = DateUtil.formatYYYYMMDD(new Date()) + " "
                + bsSysConfigTime.getConfValue();
        if (b.length == 2) {
            begin = DateUtil.formatYYYYMMDD(new Date()) + " "
                    + bsSysConfigTime.getConfValue() + ":00";
        }
        Date time = DateUtil.parseDateTime(begin); // 配置的某时间
    }
}
