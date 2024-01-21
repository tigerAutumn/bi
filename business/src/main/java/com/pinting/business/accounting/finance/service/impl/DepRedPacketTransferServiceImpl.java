package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.service.DepRedPacketTransferService;
import com.pinting.business.dao.BsPayOrdersJnlMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.enums.HFBankAccountType;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersJnl;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_PlatformAccountConverse;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/5/3
 * Description: 红包抵用转账定时重发
 */
@Service
public class DepRedPacketTransferServiceImpl implements DepRedPacketTransferService {

    private Logger logger = LoggerFactory.getLogger(DepRedPacketTransferServiceImpl.class);
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private HfbankTransportService hfbankTransportService;

    @Override
    public void redPacketTransfer() {
        logger.info("红包抵用转账定时重发开始");
        // 1. 查询所有购买成功但红包抵用转账失败的记录，进行定时重发
        List<BsPayOrders> orders = bsPayOrdersMapper.selectFailRedPacketTransfer();

        if(!CollectionUtils.isEmpty(orders)) {
            for (BsPayOrders failOrder: orders) {
                BsPayOrders order = new BsPayOrders();
                String orderNo = Util.generateSysOrderNo("HTS");
                order.setOrderNo(orderNo);
                order.setAmount(failOrder.getAmount());
                order.setUserId(failOrder.getUserId());
                order.setSubAccountId(failOrder.getSubAccountId());
                order.setStatus(Constants.ORDER_STATUS_CREATE);
                order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
                order.setTerminalType(failOrder.getTerminalType());
                order.setCreateTime(new Date());
                order.setUpdateTime(new Date());
                order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
                order.setTransType(Constants.TRANS_RED_PACKET_USED);
                order.setMobile(failOrder.getMobile());
                order.setIdCard(failOrder.getIdCard());
                order.setUserName(failOrder.getUserName());
                order.setChannel(Constants.CHANNEL_HFBANK);
                order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
                bsPayOrdersMapper.insertSelective(order);

                B2GReqMsg_HFBank_PlatformAccountConverse actTrsReq = new B2GReqMsg_HFBank_PlatformAccountConverse();
                actTrsReq.setAmt(order.getAmount());
                actTrsReq.setSource_account(HFBankAccountType.DEP_JSH.getCode());
                actTrsReq.setDest_account(HFBankAccountType.DEP_RED.getCode());
                actTrsReq.setOrder_no(order.getOrderNo());
                actTrsReq.setPartner_trans_date(new Date());
                actTrsReq.setPartner_trans_time(new Date());
                actTrsReq.setRemark("红包抵用");
                B2GResMsg_HFBank_PlatformAccountConverse resMsg = hfbankTransportService.platformAccountConverse(actTrsReq);
                if(ConstantUtil.BSRESCODE_SUCCESS.equals(resMsg.getResCode())){
                    //成功
                    logger.info("红包抵用转账定时重发成功，原订单号：{}，现订单号：{}，抵用金额：{}", failOrder.getOrderNo(), order.getOrderNo(), order.getAmount());
                    BsPayOrders ordersSucc = new BsPayOrders();
                    ordersSucc.setId(order.getId());
                    ordersSucc.setStatus(Constants.ORDER_STATUS_SUCCESS);
                    ordersSucc.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(ordersSucc);
                    BsPayOrdersJnl jnlSucc = new BsPayOrdersJnl();
                    jnlSucc.setOrderId(order.getId());
                    jnlSucc.setOrderNo(order.getOrderNo());
                    jnlSucc.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                    jnlSucc.setTransAmount(order.getAmount());
                    jnlSucc.setSysTime(new Date());
                    jnlSucc.setUserId(order.getUserId());
                    jnlSucc.setSubAccountId(order.getSubAccountId());
                    jnlSucc.setCreateTime(new Date());
                    bsPayOrdersJnlMapper.insertSelective(jnlSucc);
                } else{
                    logger.info("红包抵用转账定时重发失败，原订单号：{}，现订单号：{}，抵用金额：{}", failOrder.getOrderNo(), order.getOrderNo(), order.getAmount());
                    BsPayOrders ordersFail = new BsPayOrders();
                    ordersFail.setId(order.getId());
                    ordersFail.setStatus(Constants.ORDER_STATUS_FAIL);
                    ordersFail.setReturnCode(resMsg.getRecode());
                    ordersFail.setReturnMsg(resMsg.getRemsg());
                    ordersFail.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(ordersFail);
                    BsPayOrdersJnl jnlFail = new BsPayOrdersJnl();
                    jnlFail.setOrderId(order.getId());
                    jnlFail.setOrderNo(order.getOrderNo());
                    jnlFail.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                    jnlFail.setTransAmount(order.getAmount());
                    jnlFail.setSysTime(new Date());
                    jnlFail.setUserId(order.getUserId());
                    jnlFail.setSubAccountId(order.getSubAccountId());
                    jnlFail.setCreateTime(new Date());
                    bsPayOrdersJnlMapper.insertSelective(jnlFail);
                }

            }
        } else {
            logger.info("红包抵用转账定时重发：无失败记录，无需重发");
        }
        logger.info("红包抵用转账定时重发结束");
    }
}
