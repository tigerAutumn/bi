package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsPayOrdersJnlMapper;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsPayReceiptMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.facade.manage.BsUserFacade;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsBankCardExample;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsPayOrdersJnl;
import com.pinting.business.model.BsPayReceipt;
import com.pinting.business.model.BsPayReceiptExample;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.BaofooService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.MobileCheckUtil;
import com.pinting.business.util.Util;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.*;
import com.pinting.gateway.out.service.BaoFooTransportService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by 剑钊 on 2016/8/3.
 */
@Service
public class BaofooServiceImpl implements BaofooService {
    private Logger logger = LoggerFactory.getLogger(BaofooServiceImpl.class);

    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private BsBankCardMapper bankCardMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private BsPayReceiptMapper bsPayReceiptMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;
    
    private Logger log = LoggerFactory.getLogger(BaofooServiceImpl.class);

/*    @Override
    public List<BsBaofooPayUser> queryBindCardUserList(BsBaofooPayUserExample example) {
        return payUserMapper.selectByExample(example);
    }*/

/*    @Override
    public List<BsBaofooPayUser> queryBindCardUserListPage(BindCardQueryVO bindCardQueryVO) {
        return payUserMapper.baoFooPayUserPage(bindCardQueryVO);
    }*/

    @Override
    public String preBindCard(BsBaoFooPreBindCardVO vo) {/*

        String transId = System.currentTimeMillis() + "";
        //新建绑卡记录
        BsBaofooPayUser bsBaofooPayUser = new BsBaofooPayUser();
        bsBaofooPayUser.setMobile(vo.getMobile());
        bsBaofooPayUser.setBankName(vo.getPayCode());
        bsBaofooPayUser.setBindStatus(BaoFooEnum.INIT.getCode());
        bsBaofooPayUser.setCardNo(vo.getAccNo());
        bsBaofooPayUser.setCreateTime(new Date());
        bsBaofooPayUser.setIdCard(vo.getIdCard());
        bsBaofooPayUser.setUserName(vo.getUserName());
        bsBaofooPayUser.setUpdateTime(new Date());
        bsBaofooPayUser.setOrderNo(transId);
        payUserMapper.insertSelective(bsBaofooPayUser);

        B2GReqMsg_BaoFooQuickPay_SendSms sendSms = new B2GReqMsg_BaoFooQuickPay_SendSms();
        sendSms.setAccNo(vo.getAccNo());
        sendSms.setTransId(transId);
        sendSms.setMobile(vo.getMobile());
        sendSms.setNextTxnSubType("01");
        BsBaofooPayUser temp = new BsBaofooPayUser();
        temp.setId(bsBaofooPayUser.getId());
        B2GResMsg_BaoFooQuickPay_SendSms res = null;
        try {
            res = baoFooTransportService.sendSms(sendSms);
        } catch (Exception e) {
            temp.setBindStatus(BaoFooEnum.COMM_FAIL.getCode());
            payUserMapper.updateByPrimaryKeySelective(temp);
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
        }


        if (res.getResCode().equals("000000")) {
            temp.setBindStatus(BaoFooEnum.PRE_BIND_SUCCESS.getCode());
        } else {
            temp.setBindStatus(BaoFooEnum.PRE_BIND_FAIL.getCode());
        }
        temp.setUpdateTime(new Date());
        temp.setReturnCode(res.getResCode());
        temp.setReturnMsg(res.getResMsg());

        payUserMapper.updateByPrimaryKeySelective(temp);

        return res.getResCode();
    */return null;
    	}

    @Override
    public String bindCard(BsBaoFooBindCardVO bsBaoFooBindCardVO) {
    	return null;
    	/*

        BsBaofooPayUserExample example = new BsBaofooPayUserExample();
        example.createCriteria().andCardNoEqualTo(bsBaoFooBindCardVO.getAccNo())
                .andBindStatusEqualTo(BaoFooEnum.PRE_BIND_SUCCESS.getCode());
        example.setOrderByClause("update_time desc");
        List<BsBaofooPayUser> list = payUserMapper.selectByExample(example);
        //找到预绑卡成功信息
        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
            BsBaofooPayUser payUser = list.get(0);
            B2GReqMsg_BaoFooQuickPay_BindCardConfirm req = new B2GReqMsg_BaoFooQuickPay_BindCardConfirm();
            req.setMobile(payUser.getMobile());
            req.setAcc_no(payUser.getCardNo());
            req.setId_card(payUser.getIdCard());
            req.setId_holder(payUser.getUserName());
            req.setPay_code(BaoFooEnum.bankMap.get(payUser.getBankName()));
            req.setSms_code(bsBaoFooBindCardVO.getSmsCode());
            req.setTrans_id(payUser.getOrderNo());

            B2GResMsg_BaoFooQuickPay_BindCardConfirm res = null;
            BsBaofooPayUser temp = new BsBaofooPayUser();
            temp.setId(payUser.getId());
            try {
                res = baoFooTransportService.bindCardConfirm(req);
            } catch (Exception e) {
                temp.setBindStatus(BaoFooEnum.COMM_FAIL.getCode());
                payUserMapper.updateByPrimaryKeySelective(temp);
                temp.setUpdateTime(new Date());
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }

            if (res.getResCode().equals("000000")) {
                temp.setBindStatus(BaoFooEnum.BIND_SUCCESS.getCode());
                temp.setBindId(res.getBind_id());
            } else {
                temp.setBindStatus(BaoFooEnum.BIND_FAIL.getCode());
            }
            temp.setReturnCode(res.getResCode());
            temp.setReturnMsg(res.getResMsg());
            temp.setUpdateTime(new Date());
            payUserMapper.updateByPrimaryKeySelective(temp);

            return res.getResCode();
        }
        //没有预绑卡成功信息
        else {
            return PTMessageEnum.NO_DATA_FOUND.getCode();
        }
    */
    	}

    /**
     * 解绑卡
     *
     * @param bsBaoFooUnBindCardVO
     * @return
     */
    @Override
    public String unBindCard(BsBaoFooUnBindCardVO bsBaoFooUnBindCardVO) {
    	return null;
    	/*

        BsBaofooPayUser bsBaofooPayUser = payUserMapper.selectByPrimaryKey(Integer.parseInt(bsBaoFooUnBindCardVO.getBindUserId()));

        if (bsBaofooPayUser != null) {
            B2GReqMsg_BaoFooQuickPay_UnBindCard req = new B2GReqMsg_BaoFooQuickPay_UnBindCard();
            req.setBind_id(bsBaofooPayUser.getBindId());

            B2GResMsg_BaoFooQuickPay_UnBindCard res = null;

            try {
                res = baoFooTransportService.unBind(req);
            } catch (Exception e) {

                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }

            BsBaofooPayUser temp = new BsBaofooPayUser();
            temp.setId(bsBaofooPayUser.getId());

            if (res.getResCode().equals("000000")) {
                temp.setBindStatus(BaoFooEnum.UNBIND_SUCCESS.getCode());
                temp.setBindId("");
                temp.setReturnCode(res.getResCode());
                temp.setReturnMsg(res.getResMsg());
                temp.setUpdateTime(new Date());
                payUserMapper.updateByPrimaryKeySelective(temp);
            }
            return res.getResCode();
        } else {

            return PTMessageEnum.NO_DATA_FOUND.getCode();
        }

    */}

/*    @Override
    public List<BsBaofooPayOrders> queryOrderListPage(BaoFooOrdersQueryVO vo) {
        return ordersMapper.baoFooOrdersPage(vo);
    }*/


/*    @Override
    public List<BsBaofooPayOrders> queryOrderList(BsBaofooPayOrdersExample example) {

        return ordersMapper.selectByExample(example);
    }*/

    @Override
    public PreTopUpResVO preTopUp(BsBaoFooPreTopUpVO bsBaoFooPreTopUpVO) {
    	return null;/*

        String transId = System.currentTimeMillis() + "";
        //新建充值订单
        BsBaofooPayUserExample example = new BsBaofooPayUserExample();
        example.createCriteria().andCardNoEqualTo(bsBaoFooPreTopUpVO.getAccNo())
                .andBindStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        List<BsBaofooPayUser> list = payUserMapper.selectByExample(example);
        PreTopUpResVO vo = new PreTopUpResVO();
        //有绑定成功信息，即有bind_id
        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
            BsBaofooPayUser payUser = list.get(0);
            BsBaofooPayOrders orders = new BsBaofooPayOrders();
            orders.setUpdateTime(new Date());
            orders.setTransStatus(BaoFooEnum.INIT.getCode());
            orders.setOrderNo(transId);
            orders.setAmount(bsBaoFooPreTopUpVO.getTxnAccount());
            orders.setBankCard(payUser.getCardNo());
            orders.setBankName(payUser.getBankName());
            orders.setTransType("QUICK_PAY");
            orders.setBindUserId(payUser.getId());
            orders.setBusinessType("TOP_UP");
            orders.setCreateTime(new Date());
            orders.setIdCard(payUser.getIdCard());
            orders.setUserName(payUser.getUserName());
            orders.setMobile(payUser.getMobile());

            ordersMapper.insertSelective(orders);
            //发送短信
            B2GReqMsg_BaoFooQuickPay_SendSms sendSms = new B2GReqMsg_BaoFooQuickPay_SendSms();
            sendSms.setBindId(payUser.getBindId());
            sendSms.setTransId(transId);
            sendSms.setTxnAmt(MoneyUtil.multiply(bsBaoFooPreTopUpVO.getTxnAccount(),100d).intValue() + "");
            sendSms.setNextTxnSubType("04");

            BsBaofooPayOrders temp = new BsBaofooPayOrders();
            temp.setId(orders.getId());
            B2GResMsg_BaoFooQuickPay_SendSms res = null;
            try {
                res = baoFooTransportService.sendSms(sendSms);
            } catch (Exception e) {
                temp.setTransStatus(BaoFooEnum.COMM_FAIL.getCode());
                temp.setUpdateTime(new Date());
                ordersMapper.updateByPrimaryKeySelective(temp);
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }


            if (res.getResCode().equals("000000")) {
                temp.setTransStatus(BaoFooEnum.PRE_ORDER_SUCCESS.getCode());
            } else {
                temp.setTransStatus(BaoFooEnum.PRE_ORDER_FAIL.getCode());
            }
            temp.setUpdateTime(new Date());
            temp.setReturnCode(res.getResCode());
            temp.setReturnMsg(res.getResMsg());

            ordersMapper.updateByPrimaryKeySelective(temp);

            vo.setResCode(res.getResCode());
            vo.setTransId(transId);

        } else {
            vo.setResCode(PTMessageEnum.NO_DATA_FOUND.getCode());
        }
        return vo;
    */}

    @Override
    public String topUp(BsBaoFooTopUpVO bsBaoFooTopUpVO) {
    	return null;
    	/*

        BsBaofooPayOrdersExample example = new BsBaofooPayOrdersExample();
        example.createCriteria().andOrderNoEqualTo(bsBaoFooTopUpVO.getTransId()).andTransStatusEqualTo(BaoFooEnum.PRE_ORDER_SUCCESS.getCode());
        List<BsBaofooPayOrders> list = ordersMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
            BsBaofooPayOrders orders = list.get(0);
            BsBaofooPayUserExample userExample = new BsBaofooPayUserExample();
            userExample.createCriteria().andIdEqualTo(orders.getBindUserId());
            BsBaofooPayUser user = payUserMapper.selectByExample(userExample).get(0);
            B2GReqMsg_BaoFooQuickPay_QuickPayConfirm req = new B2GReqMsg_BaoFooQuickPay_QuickPayConfirm();
            req.setBind_id(user.getBindId());
            req.setSms_code(bsBaoFooTopUpVO.getSmsCode());
            req.setTrans_id(bsBaoFooTopUpVO.getTransId());
            req.setTxn_amt(MoneyUtil.multiply(orders.getAmount(),100d).intValue() + "");

            BsBaofooPayOrders temp = new BsBaofooPayOrders();
            temp.setId(orders.getId());
            B2GResMsg_BaoFooQuickPay_QuickPayConfirm res = null;
            try {
                res = baoFooTransportService.quickPayConfirm(req);
            } catch (Exception e) {
                temp.setTransStatus(BaoFooEnum.COMM_FAIL.getCode());
                temp.setUpdateTime(new Date());
                ordersMapper.updateByPrimaryKeySelective(temp);
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }

            if (res.getResCode().equals("000000")) {
                temp.setTransStatus(BaoFooEnum.PAY_SUCCESS.getCode());
            } else {
                temp.setTransStatus(BaoFooEnum.PAY_FAIL.getCode());
            }
            temp.setUpdateTime(new Date());
            temp.setReturnCode(res.getResCode());
            temp.setReturnMsg(res.getResMsg());

            ordersMapper.updateByPrimaryKeySelective(temp);

            return res.getResCode();
        }
        //没有找到预下单成功订单
        else {
            return PTMessageEnum.NO_DATA_FOUND.getCode();
        }
    */}

 /*   @Override
    public Integer preWithdraw(BsBaofooPayOrders bsBaofooPayOrders) {

        return ordersMapper.insertSelective(bsBaofooPayOrders);
    }*/

    @Override
    public String withdraw(BsBaoFooWithdrawVO bsBaoFooWithdrawVO) {
    	return null;
    	/*
        //查询预提现订单
        BsBaofooPayOrdersExample example = new BsBaofooPayOrdersExample();
        example.createCriteria().andOrderNoEqualTo(bsBaoFooWithdrawVO.getTransId()).andTransStatusEqualTo(BaoFooEnum.PRE_ORDER_SUCCESS.getCode());
        List<BsBaofooPayOrders> baoFooPayOrdersList = ordersMapper.selectByExample(example);
        B2GResMsg_BaoFooQuickPay_Pay4Trans res = null;
        //预提现订单存在
        if (CollectionUtils.isNotEmpty(baoFooPayOrdersList) && baoFooPayOrdersList.size() > 0) {

            B2GReqMsg_BaoFooQuickPay_Pay4Trans payPay4Trans = new B2GReqMsg_BaoFooQuickPay_Pay4Trans();
            payPay4Trans.setTo_acc_no(baoFooPayOrdersList.get(0).getBankCard());
            payPay4Trans.setTrans_money(MoneyUtil.format(baoFooPayOrdersList.get(0).getAmount()));
            payPay4Trans.setTrans_no(baoFooPayOrdersList.get(0).getOrderNo());
            payPay4Trans.setTo_acc_name(baoFooPayOrdersList.get(0).getUserName());
            payPay4Trans.setTo_bank_name(baoFooPayOrdersList.get(0).getBankName());

            BsBaofooPayOrders orders = new BsBaofooPayOrders();
            orders.setId(baoFooPayOrdersList.get(0).getId());
            try {
                res = baoFooTransportService.pay4Trans(payPay4Trans);
            } catch (Exception e) {

                orders.setTransStatus(BaoFooEnum.COMM_FAIL.getCode());
                orders.setUpdateTime(new Date());
                ordersMapper.updateByPrimaryKeySelective(orders);
                throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
            }


            if (res.getResCode().equals("000000")) {

                if (res.getRes_Code().equals("200")) {
                    orders.setTransStatus(BaoFooEnum.PAY_SUCCESS.getCode());
                } else if (res.getRes_Code().equals("0000") || res.getRes_Code().equals("0300") || res.getRes_Code().equals("0999") || res.getRes_Code().equals("0401")) {
                    orders.setTransStatus(BaoFooEnum.PAYING.getCode());
                }
            } else {
                orders.setTransStatus(BaoFooEnum.PAY_FAIL.getCode());
            }

            orders.setReturnCode(res.getResCode());
            orders.setReturnMsg(res.getResMsg());
            orders.setUpdateTime(new Date());
            ordersMapper.updateByPrimaryKeySelective(orders);

            return res.getRes_Code();
        } else {
            return PTMessageEnum.NO_DATA_FOUND.getCode();
        }
    */}

/*    @Override
    public void updateOrder(BsBaofooPayOrders bsBaofooPayOrders) {
        ordersMapper.updateByPrimaryKeySelective(bsBaofooPayOrders);
    }
*/
    @Override
    public String syncOrderStatus(BsBaoFooOrderVO vo) {
    	return null;
    	/*

        BsBaofooPayOrdersExample example = new BsBaofooPayOrdersExample();
        example.createCriteria().andOrderNoEqualTo(vo.getTransId());
        List<BsBaofooPayOrders> list = ordersMapper.selectByExample(example);


        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
            if (vo.getBusinessType().equals("TOP_UP")) {
                B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery req = new B2GReqMsg_BaoFooQuickPay_QuickPayStatusQuery();
                req.setOrig_trans_id(vo.getTransId());
                B2GResMsg_BaoFooQuickPay_QuickPayStatusQuery res = null;
                BsBaofooPayOrders orders = new BsBaofooPayOrders();
                orders.setId(list.get(0).getId());
                try {
                    res = baoFooTransportService.syncQuickPayStauts(req);
                } catch (Exception e) {
                    throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                }

                if (res.getResCode().equals("000000")) {

                    orders.setTransStatus(BaoFooEnum.PAY_SUCCESS.getCode());
                } else {
                    orders.setTransStatus(BaoFooEnum.PAY_FAIL.getCode());
                }
                orders.setReturnCode(res.getResCode());
                orders.setReturnMsg(res.getResMsg());
                ordersMapper.updateByPrimaryKeySelective(orders);
                return res.getResCode();
            } else {
                //查询预提现订单
                B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery req = new B2GReqMsg_BaoFooQuickPay_Pay4StatusQuery();
                req.setTrans_no(vo.getTransId());
                B2GResMsg_BaoFooQuickPay_Pay4StatusQuery res = null;

                try {
                    res = baoFooTransportService.syncPay4Status(req);
                } catch (Exception e) {
                    throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
                }

                BsBaofooPayOrders orders = new BsBaofooPayOrders();
                orders.setId(list.get(0).getId());

                if (res.getResCode().equals("000000")) {
                    if (res.getState().equals("0")) {
                        orders.setTransStatus(BaoFooEnum.PAYING.getCode());
                    } else if (res.getState().equals("1")) {
                        orders.setTransStatus(BaoFooEnum.PAY_SUCCESS.getCode());
                    } else if (res.getState().equals("-1")) {
                        orders.setTransStatus(BaoFooEnum.PAY_FAIL.getCode());
                    }
                    orders.setReturnMsg(res.getTrans_remark());
                } else {
                    orders.setTransStatus(BaoFooEnum.PAY_FAIL.getCode());
                    orders.setReturnMsg(res.getTrans_remark());
                }
                orders.setReturnCode(res.getResCode());
                orders.setUpdateTime(new Date());
                ordersMapper.updateByPrimaryKeySelective(orders);

                return res.getResCode();
            }
        } else {
            return PTMessageEnum.NO_DATA_FOUND.getCode();
        }
    */}

    @Override
    public String ebank(BsBaoFooEBankVO req) {
    	return null;
    	/*

        String transId = System.currentTimeMillis() + "";
        B2GReqMsg_BaoFooQuickPay_EBank eBank=new B2GReqMsg_BaoFooQuickPay_EBank();
        eBank.setOrderMoney(MoneyUtil.format(MoneyUtil.multiply(req.getAmount(),100d)));
        eBank.setPayId(req.getBankCode());
        eBank.setTransId(transId);
        eBank.setTradeDate(DateUtil.formatDateTime(new Date(),"yyyyMMddHHmmss"));
        eBank.setPageUrl("http://121.43.110.214:8084/gateway/PageUrl.html");

        BsBaofooPayOrders orders = new BsBaofooPayOrders();
        orders.setUpdateTime(new Date());
        orders.setTransStatus(BaoFooEnum.INIT.getCode());
        orders.setOrderNo(transId);
        orders.setAmount(req.getAmount());
        orders.setBankName(BaoFooEnum.ebankMap.get(req.getBankCode()));
        orders.setTransType("EBank");
        orders.setBusinessType("TOP_UP");
        orders.setCreateTime(new Date());
        ordersMapper.insertSelective(orders);

        B2GResMsg_BaoFooQuickPay_EBank res=null;

        try {
           res= baoFooTransportService.eBank(eBank);
        }catch (Exception e){
            throw new PTMessageException(PTMessageEnum.CONNECTION_ERROR);
        }

        if(StringUtils.isNotBlank(res.getHtmlStr())){
            return res.getHtmlStr();
        }
        return null;
    */}
	
}