package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.LoanCardOperateService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BankBinVO;
import com.pinting.business.service.loan.LoanUserService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.SMSService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtDetail;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtError;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtSuccess;
import com.pinting.gateway.hessian.message.hfbank.model.BatchUpdateCardExtDetail;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_BindCardConfirm;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_PreBindCard;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_LoanCif_UnBindCard;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by babyshark on 2016/9/10.
 */
@Service
public class LoanCardOperateServiceImpl implements LoanCardOperateService {
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private Logger log = LoggerFactory.getLogger(LoanCardOperateServiceImpl.class);
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private LoanUserService loanUserService;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private Bs19payBankMapper bs19payBankMapper;
    @Autowired
    private LnPayOrdersMapper payOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper payOrdersJnlMapper;
    @Autowired
    private HfbankTransportService hfbankTransportService;
    @Autowired
    private LnUserMapper	lnUserMapper;
    @Autowired
    private SMSService	smsService;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcUserExtMapper ucUserExtMapper;
    @Autowired
    private UcBankCardMapper ucBankCardMapper;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    /**
     * 预绑卡
     *
     * @param req
     */
    @Override
    public String preBindCard(G2BReqMsg_LoanCif_PreBindCard req) throws Exception {

        // 去空格
        req.setBankCard(StringUtil.isBlank(req.getBankCard()) ? req.getBankCard() : StringUtil.trimStr(req.getBankCard()));
        req.setCardHolder(StringUtil.isBlank(req.getCardHolder()) ? req.getCardHolder() : StringUtil.trimStr(req.getCardHolder()));
        req.setIdCard(StringUtil.isBlank(req.getIdCard()) ? req.getIdCard() : StringUtil.trimStr(req.getIdCard()));
        req.setMobile(StringUtil.isBlank(req.getMobile()) ? req.getMobile() : StringUtil.trimStr(req.getMobile()));

        //数据完整性校验
        if (StringUtils.isBlank(req.getBankCard()) || StringUtils.isBlank(req.getIdCard()) || StringUtils.isBlank(req.getCardHolder())
                || StringUtils.isBlank(req.getMobile()) || StringUtils.isBlank(req.getUserId())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "必传参数不完整");
        }
        //合作方订单号不为空时校验是否重复
        if(StringUtil.isNotEmpty(req.getOrderNo())){
            LnBindCardExample bindExample = new LnBindCardExample();
            bindExample.createCriteria().andPartnerOrderNoEqualTo(req.getOrderNo()).andPartnerCodeEqualTo(PartnerEnum.ZAN.getCode());
            List<LnBindCard> lnBindCards = lnBindCardMapper.selectByExample(bindExample);
            if(CollectionUtils.isNotEmpty(lnBindCards)){
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"合作方订单号重复");
            }
        }
        //判断bankCode是否正确
        if(!BaoFooEnum.bankMap.containsValue(req.getBankCode())){
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR,"银行编码没有找到");
        }
        //判断借款用户是否存在
        LnUser lnUser = loanUserService.queryLoanUserExist(req.getUserId(), req.getChannel());
        if (lnUser == null) {
            throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_USER_NOT_EXIST);
        }
        LnBindCard oldBindCard = queryLoanBindCardSuccessExist(req.getBankCard(), req.getCardHolder(), req.getIdCard(), req.getMobile());
        boolean isBind = false;
        Integer bindCardId = null;
        if (oldBindCard != null) {
            //判断此卡是否已被其他人绑定
            if(!oldBindCard.getLnUserId().equals(lnUser.getId())) {
                throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_ALREADY_BINDED);
            }else{
                //本地本用户已绑定标记
                isBind = true;
                bindCardId = oldBindCard.getId();
            }
        }

        BankBinVO bankBinVO= bankCardService.queryBankBin(String.valueOf(req.getBankCard()));
        if(bankBinVO==null || bankBinVO.getBankId()==null){
            throw new PTMessageException(PTMessageEnum.BANK_CARDBIN_NO_FIND);
        }
        //请求订单号
        String payBindOrderNo=Util.generateOrderNo4BaoFoo(8);
        //未绑卡，正常预下单
        if(!isBind){
            //插入数据库
            LnBindCard lnBindCard = new LnBindCard();
            lnBindCard.setPartnerCode(PartnerEnum.ZAN.getCode());
            lnBindCard.setUserName(req.getCardHolder());
            lnBindCard.setPartnerOrderNo(req.getOrderNo());
            lnBindCard.setMobile(req.getMobile());
            lnBindCard.setLnUserId(lnUser.getId());
            lnBindCard.setBankCard(req.getBankCard());
            lnBindCard.setCreateTime(new Date());
            lnBindCard.setIdCard(req.getIdCard());
            lnBindCard.setPayBindOrderNo(payBindOrderNo);
            lnBindCard.setStatus(BaoFooEnum.PREING.getCode());
            lnBindCard.setUpdateTime(new Date());
            lnBindCard.setBankName(BaoFooEnum.pay4BankMap.get(String.valueOf(bankBinVO.getBankId())));
            lnBindCard.setBankCode(req.getBankCode());
            lnBindCardMapper.insertSelective(lnBindCard);
            bindCardId = lnBindCard.getId();
        }

        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(req.getBankCard());
        lnPayOrders.setBankId(bankBinVO.getBankId());
        lnPayOrders.setBankName(BaoFooEnum.pay4BankMap.get(String.valueOf(bankBinVO.getBankId())));
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnUser.getId());
        lnPayOrders.setMobile(req.getMobile());
        lnPayOrders.setIdCard(req.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(payBindOrderNo);
        lnPayOrders.setPartnerCode(req.getChannel());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(req.getCardHolder());

        payOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);

        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        //发送短信
        String sendCode = null;
        String returnMsg = null;
        try {
            sendCode = smsService.sendZanBindCardPreToken(req.getMobile());
        }catch (Exception e){
            e.printStackTrace();
            sendCode = Constants.SEND_CODE_ERROR;
            if(e instanceof PTMessageException){
                returnMsg = ((PTMessageException) e).getErrMessage();
            }
        }

        LnBindCard temp = new LnBindCard();
        temp.setId(bindCardId);
        temp.setUpdateTime(new Date());

        LnPayOrders payOrderTemp = new LnPayOrders();
        payOrderTemp.setId(lnPayOrders.getId());
        payOrderTemp.setUpdateTime(new Date());

        if (!Constants.SEND_CODE_ERROR.equals(sendCode)) {
            //更新绑卡信息表状态
            if(!isBind){
                temp.setStatus(BaoFooEnum.BINDING.getCode());
            }
            temp.setBgwOrderNo(Util.generateSysOrderNo("BBC"));
            temp.setPayBindOrderNo(lnPayOrders.getOrderNo());
            lnBindCardMapper.updateByPrimaryKeySelective(temp);

            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
            payOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_SUCCESS.getCode());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnl.setTransAmount(0d);

            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            return temp.getBgwOrderNo();

        } else {
            if(!isBind) {
                temp.setStatus(BaoFooEnum.BIND_FAIL.getCode());
                lnBindCardMapper.updateByPrimaryKeySelective(temp);
            }
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_FAIL.getCode()));
            payOrderTemp.setReturnCode(sendCode);
            payOrderTemp.setReturnMsg(returnMsg);
            payOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            lnPayOrdersJnl = new LnPayOrdersJnl();
            lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_PRE_FAIL.getCode());
            lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl.setCreateTime(new Date());
            lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl.setSysTime(new Date());
            lnPayOrdersJnl.setReturnCode(sendCode);
            lnPayOrdersJnl.setReturnMsg(returnMsg);
            lnPayOrdersJnl.setTransAmount(0d);

            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

            throw new PTMessageException(PTMessageEnum.BAOFOO_PRE_BIND_CARD_FAIL, returnMsg);
        }

    }

    /**
     * 确认绑卡
     *
     * @param req
     */
    @Override
    public String bindCardConfirm(G2BReqMsg_LoanCif_BindCardConfirm req) throws Exception {

        if (StringUtils.isBlank(req.getBgwOrderNo()) || StringUtils.isBlank(req.getSmsCode())) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "订单号或验证码为空");
        }

        //查询预绑定信息是否存在
        final LnBindCardExample example = new LnBindCardExample();
        example.createCriteria().andBgwOrderNoEqualTo(req.getBgwOrderNo()).andPartnerCodeEqualTo(PartnerEnum.ZAN.getCode());
        List<LnBindCard> list = lnBindCardMapper.selectByExample(example);
        if(CollectionUtils.isEmpty(list)){
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, "绑卡订单号不存在");
        }

        final LnBindCard lnBindCard=list.get(0);
        LnPayOrdersExample payOrdersExample = new LnPayOrdersExample();
        payOrdersExample.createCriteria().andOrderNoEqualTo(lnBindCard.getPayBindOrderNo())
                .andStatusEqualTo(Integer.parseInt(LoanStatus.ORDERS_STATUS_PRE_SUCCESS.getCode()));
        final List<LnPayOrders> lnPayOrdersList = payOrdersMapper.selectByExample(payOrdersExample);
        if (CollectionUtils.isEmpty(lnPayOrdersList)) {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }

        if (!lnBindCard.getStatus().equals(BaoFooEnum.BINDING.getCode())) {
            //订单不存在
            if (lnBindCard.getStatus().equals(BaoFooEnum.PRE_BIND_FAIL.getCode())) {
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            //已绑卡成功，无需重新发起
            if(BaoFooEnum.BIND_SUCCESS.getCode().equals(lnBindCard.getStatus())){
                //throw new PTMessageException(PTMessageEnum.USER_BANK_CARD_BINDED);
                //此种情况表示已成功，直接返回绑卡编号
                return lnBindCard.getBgwBindId();
            }
            //绑卡失败
            if (BaoFooEnum.BIND_FAIL.getCode().equals(lnBindCard.getStatus())) {
                throw new PTMessageException(PTMessageEnum.BAOFOO_BIND_CARD_FAIL, lnPayOrdersList.get(0).getReturnMsg());
            }
        }
        try {
            jsClientDaoSupport.lock("BIND_CARD_"+lnBindCard.getIdCard());
            final LnBindCard oldBindCard = queryLoanBindCardSuccessExist(lnBindCard.getBankCard(), lnBindCard.getUserName(), lnBindCard.getIdCard(), lnBindCard.getMobile());
            if (oldBindCard != null) {
                if(!oldBindCard.getLnUserId().equals(lnPayOrdersList.get(0).getLnUserId())) {
                    //判断此卡是否已被其他客户绑定
                    throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_ALREADY_BINDED);
                }else{
                    //本地用户用其他绑卡单号已绑定
                    throw new PTMessageException(PTMessageEnum.USER_BANK_CARD_BINDED);
                }
            }

            //绑卡订单
            final LnPayOrders bindOrder = lnPayOrdersList.get(0);

            // 批量绑卡 需要币港湾校验验证码
            boolean IsValidate;
            String validateCode = "";
            String validateMsg = "";
            try {
                IsValidate = smsService.validateIdentity(lnBindCard.getMobile(), req.getSmsCode(), true);
            } catch (PTMessageException e) {
                IsValidate = false;
                validateCode = e.getErrCode();
                validateMsg = e.getErrMessage();
            } catch (Exception e) {
                IsValidate = false;
                validateCode = ConstantUtil.BSRESCODE_FAIL;
                validateMsg = "短信校验失败";
            }
            //验证码不通过
            if (!IsValidate) {
                //业务绑卡订单失败
                LnPayOrders payOrderTemp = new LnPayOrders();
                payOrderTemp.setId(bindOrder.getId());
                payOrderTemp.setUpdateTime(new Date());
                payOrderTemp.setReturnCode(validateCode);
                payOrderTemp.setReturnMsg(validateMsg);
                payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                payOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);
                LnPayOrdersJnl bindOrderJnlTemp = new LnPayOrdersJnl();
                bindOrderJnlTemp.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
                bindOrderJnlTemp.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
                bindOrderJnlTemp.setCreateTime(new Date());
                bindOrderJnlTemp.setOrderId(bindOrder.getId());
                bindOrderJnlTemp.setOrderNo(bindOrder.getOrderNo());
                bindOrderJnlTemp.setUserId(bindOrder.getLnUserId());
                bindOrderJnlTemp.setSysTime(new Date());
                bindOrderJnlTemp.setReturnMsg(validateMsg);
                bindOrderJnlTemp.setTransAmount(0d);
                payOrdersJnlMapper.insertSelective(bindOrderJnlTemp);
                LnBindCard temp = new LnBindCard();
                temp.setId(lnBindCard.getId());
                temp.setUpdateTime(new Date());
                temp.setStatus(BaoFooEnum.BIND_FAIL.getCode());
                lnBindCardMapper.updateByPrimaryKeySelective(temp);
                throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);

            }
            //验证码校验通过
            else {
                Integer lnUserId = lnBindCard.getLnUserId();
                UcUserExtExample ucUserExtExample = new UcUserExtExample();
                ucUserExtExample.createCriteria().andUserIdEqualTo(lnUserId).andUserTypeEqualTo(Constants.UC_USER_TYPE_ZAN);
                List<UcUserExt> extList = ucUserExtMapper.selectByExample(ucUserExtExample);
                if(CollectionUtils.isEmpty(extList)) {
                    throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND.getCode(), "用户中心信息不存在");
                }
                UcUser ucUser = ucUserMapper.selectByPrimaryKey(extList.get(0).getUcUserId());
                Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
                bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(lnBindCard.getBankCode());
                List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

                UcUserExample user2Example = new UcUserExample();
                user2Example.createCriteria().andIdCardEqualTo(bindOrder.getIdCard()).andStatusEqualTo(Constants.UC_USER_OPEN);
                List<UcUser> ucUser2s = ucUserMapper.selectByExample(user2Example);
                if(!org.springframework.util.CollectionUtils.isEmpty(ucUser2s)) {
                    ucUser = ucUser2s.get(0);
                }

                if(StringUtil.isNotEmpty(ucUser.getHfUserId())) {
                    // 1 uc_user.hf_user_id 存在记录，证明已经开户
                    if(!lnBindCard.getIdCard().equals(ucUser.getIdCard())){
                        throw new PTMessageException(PTMessageEnum.DAFY_REALNAME_AUTH_ERROR);
                    }
                    UcUserExtExample otherExtExample = new UcUserExtExample();
                    otherExtExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andUserTypeNotEqualTo(Constants.UC_USER_TYPE_ZAN);
                    List<UcUserExt> otherExtList = ucUserExtMapper.selectByExample(otherExtExample);
                    UcBankCardExample notApplyBankExample = new UcBankCardExample();
                    notApplyBankExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED).andIsBindEqualTo(Constants.UC_BIND_CARD_YES);
                    List<UcBankCard> isBindHfCards = ucBankCardMapper.selectByExample(notApplyBankExample);
                    if(CollectionUtils.isEmpty(otherExtList) && CollectionUtils.isEmpty(isBindHfCards)) {
                        // 1.1 其他端无注册用户，且无恒丰绑定卡，则需要恒丰绑卡
                        return hfBindCard(lnBindCard, bs19payBanks.get(0).getBankId(), bindOrder, ucUser);
                    }else if(CollectionUtils.isEmpty(otherExtList) && CollectionUtils.isNotEmpty(isBindHfCards)){
                        //1.2 其他端无注册用户，且有恒丰绑定卡，解绑老卡绑定新卡
                        UcBankCard ucBankCard = isBindHfCards.get(0);
                        LnBindCardExample exampleOld = new LnBindCardExample();
                        exampleOld.createCriteria().andLnUserIdEqualTo(lnUserId).andBankCardEqualTo(ucBankCard.getCardNo())
                                .andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andPartnerCodeEqualTo(PartnerEnum.ZAN.getCode());
                        List<LnBindCard> oldList = lnBindCardMapper.selectByExample(exampleOld);
                        if(CollectionUtils.isEmpty(oldList)){
                            throw new PTMessageException(PTMessageEnum.USER_NOT_BIND_CARD);
                        }
                        LnBindCard oldLnBindCard = oldList.get(0);
                        LnUser lnUser =  lnUserMapper.selectByPrimaryKey(lnUserId);
                        //解绑
                        this.unBind4Zan2(ucUser.getId(), oldLnBindCard, lnUser);
                        //绑卡
                        String resOrder = hfBindCard(lnBindCard, bs19payBanks.get(0).getBankId(), bindOrder, ucUser);
                        if(StringUtil.isEmpty(resOrder)){
                            specialJnlService.warn4FailNoSMS(null, "【用户绑卡】用户编号["
                                            + lnUserId + "]解绑再绑卡失败", bindOrder.getOrderNo(),
                                    "用户绑卡");
                        }
                        return resOrder;
                    } else {
                        // 1.3 其他端有注册用户，且有恒丰绑定卡，则只需逻辑绑卡 TOCHECK
                        UcBankCardExample bankExample = new UcBankCardExample();
                        bankExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andCardNoEqualTo(lnBindCard.getBankCard())
                                .andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
                        List<UcBankCard> ucBankCards = ucBankCardMapper.selectByExample(bankExample);
                        if(CollectionUtils.isNotEmpty(ucBankCards)){
                            //uccard已有该卡绑定，uc不处理
                        }else{
                            //uccard无该卡绑定，uc插入N绑卡信息
                            UcBankCard ucBankCard = new UcBankCard();
                            ucBankCard.setMobile(lnBindCard.getMobile());
                            ucBankCard.setBankId(bs19payBanks.get(0).getBankId());
                            ucBankCard.setBankName(lnBindCard.getBankName());
                            ucBankCard.setBindTime(new Date());
                            ucBankCard.setCardNo(lnBindCard.getBankCard());
                            ucBankCard.setCardOwner(lnBindCard.getUserName());
                            ucBankCard.setCreateTime(new Date());
                            ucBankCard.setIdCard(lnBindCard.getIdCard());
                            ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                            ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
                            ucBankCard.setUcUserId(ucUser.getId());
                            ucBankCard.setUpdateTime(new Date());
                            ucBankCardMapper.insertSelective(ucBankCard);
                        }
                        //复制信息至lncard,lnuser
                        LnBindCard cardTemp = new LnBindCard();
                        cardTemp.setId(lnBindCard.getId());
                        cardTemp.setStatus(BaoFooEnum.BIND_SUCCESS.getCode());
                        cardTemp.setUpdateTime(new Date());
                        cardTemp.setPayBindId(null);
                        cardTemp.setBgwBindId(Util.generateBindIdNo("BBC", lnUserId));
                        lnBindCardMapper.updateByPrimaryKeySelective(cardTemp);
                        LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnUserId);
                        if(StringUtil.isEmpty(lnUser.getHfUserId())){
                            LnUser userTemp = new LnUser();
                            userTemp.setId(lnUserId);
                            userTemp.setHfUserId(ucUser.getHfUserId());
                            userTemp.setIdCard(lnBindCard.getIdCard());
                            userTemp.setUserName(lnBindCard.getUserName());
                            userTemp.setUpdateTime(new Date());
                            lnUserMapper.updateByPrimaryKeySelective(userTemp);
                        }
                        return cardTemp.getBgwBindId();
                    }
                } else {
                    // 2 uc_user.hf_user_id 无记录，需进行恒丰开户
                    return hfBindCard(lnBindCard, bs19payBanks.get(0).getBankId(), bindOrder, ucUser);
                }
            }
        }finally {
            jsClientDaoSupport.unlock("BIND_CARD_"+lnBindCard.getIdCard());
        }
    }

    private String hfBindCard(LnBindCard lnBindCard, Integer bankId, LnPayOrders bindOrder, UcUser ucUser){
        Integer lnUserId = lnBindCard.getLnUserId();
        String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
        lnPayOrders.setBankId(bankId);
        lnPayOrders.setBankName(lnBindCard.getBankName());
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnUserId);
        lnPayOrders.setMobile(lnBindCard.getMobile());
        lnPayOrders.setIdCard(lnBindCard.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(payBindOrderNo);
        lnPayOrders.setPartnerCode(PartnerEnum.ZAN.getCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(lnBindCard.getUserName());
        payOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        boolean communication = true;//通讯标记
        B2GResMsg_HFBank_BatchBindCard4Elements res = null;
        if(StringUtil.isEmpty(ucUser.getHfUserId())){
            log.info("赞分期借款用户 {} 未开户，调用恒丰开户四要素绑卡接口。", lnUserId);
            B2GReqMsg_HFBank_BatchBindCard4Elements reqBankCard = new B2GReqMsg_HFBank_BatchBindCard4Elements();
            List<BatchRegistExtDetail> details = new ArrayList<>();
            BatchRegistExtDetail detail = new BatchRegistExtDetail();
            reqBankCard.setOrder_no(Util.generateOrderNo4BaoFoo(8));
            detail.setDetail_no(payBindOrderNo);
            detail.setName(lnBindCard.getUserName());
            detail.setId_type(Constants.HF_ID_TYPE_ID_CARD);
            detail.setId_code(lnBindCard.getIdCard());
            detail.setMobile(lnBindCard.getMobile());
            detail.setEmail(null);
            detail.setSex(null);
            detail.setCus_type(null);
            detail.setBirthday(null);
            detail.setOpen_branch(null);
            detail.setCard_no(lnBindCard.getBankCard());
            detail.setCard_type(Constants.HF_CARD_TYPE_DEBIT);
            detail.setPre_mobile(lnBindCard.getMobile());
            detail.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
            detail.setNotify_url(null);
            detail.setRemark("批量开户(四要素绑卡)");
            details.add(detail);
            reqBankCard.setData(details);
            reqBankCard.setTotal_num(details.size());
            reqBankCard.setPartner_trans_date(new Date());
            reqBankCard.setPartner_trans_time(new Date());
            log.info("批量绑卡请求：{}", JSONObject.fromObject(reqBankCard));

            res = new B2GResMsg_HFBank_BatchBindCard4Elements();
            try {
                res = hfbankTransportService.batchBindCard4Elements(reqBankCard);
            } catch (Exception e) {
                communication = false;
                log.error("批量开户(四要素绑卡)请求异常：{}，卡号：{}", e.getMessage(), lnPayOrders.getBankCardNo(), e);
            }
        }else {
            log.info("用户 {} 已开户，调用恒丰绑卡接口", lnUserId);
            // 单纯的绑卡（已开户）
            B2GReqMsg_HFBank_UserAddCard addCardReq = new B2GReqMsg_HFBank_UserAddCard();
            addCardReq.setOrder_no(Util.generateOrderNo4BaoFoo(8));
            addCardReq.setPartner_trans_date(new Date());
            addCardReq.setPartner_trans_time(new Date());
            addCardReq.setType(Constants.HF_BIND_CARD_TYPE_PERSON);//绑卡类型：1个人客户 2 对公客户
            addCardReq.setPlatcust(ucUser.getHfUserId());
            addCardReq.setId_type(Constants.HF_ID_TYPE_ID_CARD);    //1:身份证
            addCardReq.setId_code(lnBindCard.getIdCard());
            addCardReq.setName(lnBindCard.getUserName());
            addCardReq.setCard_no(lnBindCard.getBankCard());
            addCardReq.setCard_type(Constants.HF_CARD_TYPE_DEBIT);    //1:借记卡,2:信用卡
            addCardReq.setPre_mobile(lnBindCard.getMobile());
            addCardReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
            addCardReq.setRemark("个人绑卡");
            log.info("绑卡请求：{}", JSONObject.fromObject(addCardReq));
            try {
                B2GResMsg_HFBank_UserAddCard addCardRes = hfbankTransportService.userAddCard(addCardReq);
                log.info("绑卡响应信息：{}", JSONObject.fromObject(addCardRes));
                res = new B2GResMsg_HFBank_BatchBindCard4Elements();
                if (ConstantUtil.BSRESCODE_SUCCESS.equals(addCardRes.getResCode())) {
                    List<BatchRegistExtSuccess> success_data = new ArrayList<>();
                    BatchRegistExtSuccess success = new BatchRegistExtSuccess();
                    success.setDetail_no(addCardReq.getOrder_no());
                    success.setMobile(addCardReq.getPre_mobile());
                    success.setPlatcust(ucUser.getHfUserId());
                    success_data.add(success);
                    res.setSuccess_data(success_data);
                    res.setSuccess_num("1");
                    res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
                    res.setResMsg("已开户用户，绑卡成功");
                } else {
                    List<BatchRegistExtError> error_data = new ArrayList<>();
                    BatchRegistExtError error = new BatchRegistExtError();
                    error.setError_no(addCardRes.getResCode());
                    error.setError_info(StringUtil.isBlank(addCardRes.getRemsg()) ? addCardRes.getResMsg() : addCardRes.getRemsg());
                    error_data.add(error);
                    res.setError_data(error_data);
                    res.setResCode(error.getError_no());
                    res.setResMsg(error.getError_info());
                }
            } catch (Exception e) {
                communication = false;
                e.printStackTrace();
                log.info("恒丰正式绑卡操作失败 {}，订单号：{}", e.getMessage(), addCardReq.getOrder_no());
            }
        }


        if(!communication) {
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("通讯异常");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            payOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("通讯异常");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            //业务绑卡订单置失败
            LnPayOrders bindOrderTemp = new LnPayOrders();
            bindOrderTemp.setId(bindOrder.getId());
            bindOrderTemp.setUpdateTime(new Date());
            bindOrderTemp.setReturnMsg("通讯异常");
            bindOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            payOrdersMapper.updateByPrimaryKeySelective(bindOrderTemp);
            LnPayOrdersJnl bindOrderJnlTemp = new LnPayOrdersJnl();
            bindOrderJnlTemp.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            bindOrderJnlTemp.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            bindOrderJnlTemp.setCreateTime(new Date());
            bindOrderJnlTemp.setOrderId(bindOrder.getId());
            bindOrderJnlTemp.setOrderNo(bindOrder.getOrderNo());
            bindOrderJnlTemp.setUserId(bindOrder.getLnUserId());
            bindOrderJnlTemp.setSysTime(new Date());
            bindOrderJnlTemp.setReturnMsg("通讯异常");
            bindOrderJnlTemp.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(bindOrderJnlTemp);

            LnBindCard temp = new LnBindCard();
            temp.setId(lnBindCard.getId());
            temp.setUpdateTime(new Date());
            temp.setStatus(BaoFooEnum.BIND_FAIL.getCode());
            lnBindCardMapper.updateByPrimaryKeySelective(temp);

            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), "赞分期借款用户"+lnUserId+"调用恒丰绑卡接口通讯异常 ", null, "【赞分期恒丰绑卡通讯异常】");
            throw new PTMessageException(PTMessageEnum.FOUR_ELEMENTS_AUTH_ERROR,"，通讯异常");
        }

        //绑卡成功
        if(res != null && !org.springframework.util.CollectionUtils.isEmpty(res.getSuccess_data())
                && StringUtil.isNotEmpty(res.getSuccess_data().get(0).getPlatcust())) {
            String hfUserId = res.getSuccess_data().get(0).getPlatcust();

            UcUserExtExample ucUserExtExample = new UcUserExtExample();
            ucUserExtExample.createCriteria().andUserIdEqualTo(lnUserId).andUserTypeEqualTo(Constants.UC_USER_TYPE_ZAN);
            List<UcUserExt> extList = ucUserExtMapper.selectByExample(ucUserExtExample);
            if(CollectionUtils.isEmpty(extList)) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND.getCode(), "用户中心信息不存在");
            }
            UcUser ucUser2 = ucUserMapper.selectByPrimaryKey(extList.get(0).getUcUserId());
            //根据身份证和状态查询uc_user表，如果有，且UC用户编号不一样，则废除原UC注册帐号，加挂到此UC帐号下面
            UcUserExample user2Example = new UcUserExample();
            user2Example.createCriteria().andIdCardEqualTo(bindOrder.getIdCard()).andStatusEqualTo(Constants.UC_USER_OPEN);
            List<UcUser> ucUser2s = ucUserMapper.selectByExample(user2Example);
            if (!org.springframework.util.CollectionUtils.isEmpty(ucUser2s) && !ucUser2s.get(0).getId().equals(ucUser2.getId())) {
                UcUser closeUser = new UcUser();
                closeUser.setId(ucUser2.getId());
                closeUser.setStatus(Constants.UC_USER_CLOSE);
                closeUser.setUpdateTime(new Date());
                ucUserMapper.updateByPrimaryKeySelective(closeUser);
                UcUserExt ucUserExt = new UcUserExt();
                ucUserExt.setCreateTime(new Date());
                ucUserExt.setUcUserId(ucUser2s.get(0).getId());
                ucUserExt.setUserType(Constants.UC_USER_TYPE_ZAN);
                ucUserExt.setUserId(lnUserId);
                ucUserExtMapper.insertSelective(ucUserExt);
                UcUser openUcUser = new UcUser();
                openUcUser.setId(ucUser2s.get(0).getId());
                openUcUser.setMobile(ucUser2.getMobile());
                openUcUser.setUpdateTime(new Date());
                ucUserMapper.updateByPrimaryKeySelective(openUcUser);
            }

            //更新ln_user
            LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnUserId);
            if(StringUtil.isEmpty(lnUser.getHfUserId())){
                LnUser updateLnUser = new LnUser();
                updateLnUser.setId(lnUserId);
                updateLnUser.setUpdateTime(new Date());
                updateLnUser.setHfUserId(hfUserId);
                updateLnUser.setIdCard(lnBindCard.getIdCard());
                updateLnUser.setUserName(lnBindCard.getUserName());
                lnUserMapper.updateByPrimaryKeySelective(updateLnUser);
            }
            // 更新ln_bind_card
            LnBindCard cardTemp = new LnBindCard();
            cardTemp.setId(lnBindCard.getId());
            cardTemp.setStatus(BaoFooEnum.BIND_SUCCESS.getCode());
            cardTemp.setUpdateTime(new Date());
            cardTemp.setPayBindId(null);
            cardTemp.setBgwBindId(Util.generateBindIdNo("BBC", lnUserId));
            lnBindCardMapper.updateByPrimaryKeySelective(cardTemp);
            //更新uc_user
            UcUser ucUserUp = new UcUser();
            ucUserUp.setId(ucUser.getId());
            if(StringUtil.isEmpty(ucUser.getIdCard())){
                ucUserUp.setIdCard(lnBindCard.getIdCard());
                ucUserUp.setUserName(lnBindCard.getUserName());
            }
            ucUserUp.setHfUserId(hfUserId);
            ucUserUp.setUpdateTime(new Date());
            ucUserMapper.updateByPrimaryKeySelective(ucUserUp);
            //更新uc_bank_card
            UcBankCard ucBankCard = new UcBankCard();
            ucBankCard.setMobile(lnBindCard.getMobile());
            ucBankCard.setBindTime(new Date());
            ucBankCard.setIsBind(Constants.UC_BIND_CARD_YES);
            ucBankCard.setUpdateTime(new Date());

            UcBankCardExample bankExample = new UcBankCardExample();
            bankExample.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andCardNoEqualTo(lnBindCard.getBankCard())
                    .andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
            //uccard已有该卡绑定，则更新is_bind为Y
            if(ucBankCardMapper.updateByExampleSelective(ucBankCard, bankExample) == 0){
                //uccard无该卡绑定，uc插入N绑卡信息
                ucBankCard.setBankId(bankId);
                ucBankCard.setBankName(lnBindCard.getBankName());
                ucBankCard.setCardNo(lnBindCard.getBankCard());
                ucBankCard.setCardOwner(lnBindCard.getUserName());
                ucBankCard.setCreateTime(new Date());
                ucBankCard.setIdCard(lnBindCard.getIdCard());
                ucBankCard.setUcUserId(ucUser.getId());
                ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
                ucBankCardMapper.insertSelective(ucBankCard);
            }

            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnMsg("绑卡成功");
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            payOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg("绑卡成功");
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            //业务绑卡订单置成功
            LnPayOrders bindOrderTemp = new LnPayOrders();
            bindOrderTemp.setId(bindOrder.getId());
            bindOrderTemp.setUpdateTime(new Date());
            bindOrderTemp.setReturnMsg(PTMessageEnum.TRANS_SUCCESS.getMessage());
            bindOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
            payOrdersMapper.updateByPrimaryKeySelective(bindOrderTemp);
            LnPayOrdersJnl bindOrderJnlTemp = new LnPayOrdersJnl();
            bindOrderJnlTemp.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
            bindOrderJnlTemp.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            bindOrderJnlTemp.setCreateTime(new Date());
            bindOrderJnlTemp.setOrderId(bindOrder.getId());
            bindOrderJnlTemp.setOrderNo(bindOrder.getOrderNo());
            bindOrderJnlTemp.setUserId(bindOrder.getLnUserId());
            bindOrderJnlTemp.setSysTime(new Date());
            bindOrderJnlTemp.setReturnMsg(PTMessageEnum.TRANS_SUCCESS.getMessage());
            bindOrderJnlTemp.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(bindOrderJnlTemp);

            return cardTemp.getBgwBindId();
        } else {
            // 四要素开户失败
            LnPayOrders payOrderTemp = new LnPayOrders();
            payOrderTemp.setId(lnPayOrders.getId());
            payOrderTemp.setUpdateTime(new Date());
            payOrderTemp.setReturnCode(res.getRecode());
            payOrderTemp.setReturnMsg(res.getResMsg());
            payOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            payOrdersMapper.updateByPrimaryKeySelective(payOrderTemp);

            LnBindCard temp = new LnBindCard();
            temp.setId(lnBindCard.getId());
            temp.setUpdateTime(new Date());
            temp.setStatus(BaoFooEnum.BIND_FAIL.getCode());
            lnBindCardMapper.updateByPrimaryKeySelective(temp);

            //记录ln_pay_orders_jnl表
            LnPayOrdersJnl lnPayOrdersJnl2 = new LnPayOrdersJnl();
            lnPayOrdersJnl2.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            lnPayOrdersJnl2.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            lnPayOrdersJnl2.setCreateTime(new Date());
            lnPayOrdersJnl2.setOrderId(lnPayOrders.getId());
            lnPayOrdersJnl2.setOrderNo(lnPayOrders.getOrderNo());
            lnPayOrdersJnl2.setUserId(lnPayOrders.getLnUserId());
            lnPayOrdersJnl2.setSysTime(new Date());
            lnPayOrdersJnl2.setReturnMsg(res.getResMsg());
            lnPayOrdersJnl2.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(lnPayOrdersJnl2);

            //业务绑卡订单置失败
            LnPayOrders bindOrderTemp = new LnPayOrders();
            bindOrderTemp.setId(bindOrder.getId());
            bindOrderTemp.setUpdateTime(new Date());
            bindOrderTemp.setReturnCode(res.getRecode());
            bindOrderTemp.setReturnMsg(res.getResMsg());
            bindOrderTemp.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
            payOrdersMapper.updateByPrimaryKeySelective(bindOrderTemp);
            LnPayOrdersJnl bindOrderJnlTemp = new LnPayOrdersJnl();
            bindOrderJnlTemp.setTransCode(LoanStatus.ORDERS_JNL_STATUS_FAIL.getCode());
            bindOrderJnlTemp.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_BIND_CARD.getCode());
            bindOrderJnlTemp.setCreateTime(new Date());
            bindOrderJnlTemp.setOrderId(bindOrder.getId());
            bindOrderJnlTemp.setOrderNo(bindOrder.getOrderNo());
            bindOrderJnlTemp.setUserId(bindOrder.getLnUserId());
            bindOrderJnlTemp.setSysTime(new Date());
            bindOrderJnlTemp.setReturnMsg(res.getResMsg());
            bindOrderJnlTemp.setTransAmount(0d);
            payOrdersJnlMapper.insertSelective(bindOrderJnlTemp);

            specialJnlService.warn4FailNoSMS(lnPayOrders.getAmount(), "赞分期借款用户"+lnUserId+"调用恒丰绑卡失败："+res.getResMsg(), null, "【赞分期恒丰绑卡失败】");
            throw new PTMessageException(PTMessageEnum.FOUR_ELEMENTS_AUTH_ERROR, res.getResMsg());
        }
    }

    /**
     * 解绑卡
     *
     * @param req
     */
    @Override
    public void unBindCard(G2BReqMsg_LoanCif_UnBindCard req) throws Exception {
        log.info("赞分期解绑卡开始，请求信息：{}", JSONObject.fromObject(req));

        if (StringUtils.isBlank(req.getBgwBindId())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "绑卡编号为空");
        }
        
        //查询绑卡信息是否存在
        LnBindCardExample example = new LnBindCardExample();
        example.createCriteria().andBgwBindIdEqualTo(req.getBgwBindId()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode());
        List<LnBindCard> list = lnBindCardMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
        	final LnBindCard bindCard = list.get(0);
        	//用户ln_user信息
        	final LnUser lnUser =  lnUserMapper.selectByPrimaryKey(bindCard.getLnUserId());
        	//用户中心信息
        	UcUserExample ucUserExm = new UcUserExample();
        	ucUserExm.createCriteria().andIdCardEqualTo(bindCard.getIdCard())
        		.andUserNameEqualTo(bindCard.getUserName()).andStatusEqualTo(Constants.UC_USER_OPEN);
        	List<UcUser> ucUserList = ucUserMapper.selectByExample(ucUserExm);
        	
        	//如果信息不存在,解绑提示"数据验证失败,提示解绑失败"
        	if(CollectionUtils.isEmpty(ucUserList)) {
        		throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "UC用户中心信息异常");
        	}
        	
        	final UcUser ucUser = ucUserList.get(0);
        	UcUserExtExample userExtExm = new UcUserExtExample();
        	userExtExm.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andUserTypeNotEqualTo(Constants.UC_USER_TYPE_ZAN);
        	List<UcUserExt> otherUserExt = ucUserExtMapper.selectByExample(userExtExm);
        	// 判断是否恒丰判定卡
        	UcBankCardExample ucBankCardExm = new UcBankCardExample();
        	ucBankCardExm.createCriteria().andCardNoEqualTo(bindCard.getBankCard())
        		.andIdCardEqualTo(bindCard.getIdCard()).andIsBindEqualTo(Constants.UC_BIND_CARD_YES);
        	List<UcBankCard> ucBankCardList = ucBankCardMapper.selectByExample(ucBankCardExm);
        	
        	//卡号为恒丰绑定卡(非理财端绑卡),则发起恒丰解绑接口
        	if( CollectionUtils.isNotEmpty(ucBankCardList) && CollectionUtils.isEmpty( otherUserExt ) ) {
        		//单客户端,发起恒丰卡解绑 
        		this.unBind4Zan(ucUser.getId(), bindCard, lnUser);
        	} else {
        		//ln_bind_card信息维护 
                LnBindCard temp = new LnBindCard();
                temp.setId(bindCard.getId());
                temp.setStatus(BaoFooEnum.UNBIND_SUCCESS.getCode());
                temp.setUpdateTime(new Date());
                lnBindCardMapper.updateByPrimaryKeySelective(temp);
        	}
        } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND,	"绑卡信息不存在");
        }
    }

    /**
     * 单客户端解绑
     * @param ucUserId
     * @param lnBindCard
     * @param lnUser
     */
    public void unBind4Zan(final Integer ucUserId, final LnBindCard lnBindCard,final LnUser lnUser) {
    		
        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(lnBindCard.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        // 调用解绑接口
        String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
        lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
        lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(lnBindCard.getBankCode()));
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
        lnPayOrders.setMobile(lnUser.getMobile());
        lnPayOrders.setIdCard(lnUser.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(payBindOrderNo);
        lnPayOrders.setPartnerCode(lnUser.getPartnerCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(lnUser.getUserName());
        payOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        B2GReqMsg_HFBank_BatchChangeCard unbindReq = new B2GReqMsg_HFBank_BatchChangeCard();
        unbindReq.setPartner_trans_time(lnPayOrders.getCreateTime());
        unbindReq.setPartner_trans_date(lnPayOrders.getCreateTime());
        unbindReq.setOrder_no(Util.generateOrderNo4BaoFoo(8));
        unbindReq.setTotal_num(1);
        List<BatchUpdateCardExtDetail> data = new ArrayList<>();
        BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
        detail.setPlatcust(lnUser.getHfUserId());
        detail.setCard_no(lnBindCard.getBankCard());
        detail.setCard_no_old(lnBindCard.getBankCard());
        detail.setDetail_no(lnPayOrders.getOrderNo());
        detail.setMobile(lnUser.getMobile());
        detail.setName(lnUser.getUserName());
        detail.setRemark("用户解绑卡");
        data.add(detail);
        unbindReq.setData(data);
        B2GResMsg_HFBank_BatchChangeCard res = hfbankTransportService.batchChangeCard(unbindReq);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            if(!CollectionUtils.isEmpty(res.getSuccess_data())) {
                log.info("用户 {} 解绑卡成功：{}", lnUser.getHfUserId(), JSONObject.fromObject(res));
                lnPayOrders.setStatus(Constants.ORDER_STATUS_SUCCESS);
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setNote(detail.getDetail_no());
                lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);

                //记录ln_pay_orders_jnl表
                LnPayOrdersJnl succJnl = new LnPayOrdersJnl();
                succJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                succJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
                succJnl.setCreateTime(new Date());
                succJnl.setOrderId(lnPayOrders.getId());
                succJnl.setOrderNo(lnPayOrders.getOrderNo());
                succJnl.setUserId(lnPayOrders.getLnUserId());
                succJnl.setSysTime(new Date());
                succJnl.setTransAmount(0d);
                payOrdersJnlMapper.insertSelective(succJnl);
                
                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                    	// ln_bind_card 解绑
                        LnBindCard temp = new LnBindCard();
                        temp.setId(lnBindCard.getId());
                        temp.setStatus(BaoFooEnum.UNBIND_SUCCESS.getCode());
                        temp.setUpdateTime(new Date());
                        lnBindCardMapper.updateByPrimaryKeySelective(temp);

                        // uc_bind_card is_bind = N
                        UcBankCard ucBankCard = new UcBankCard();
                        //ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_UNBINDED);
                        ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                        ucBankCard.setUpdateTime(new Date());
//                        ucBankCard.setUnbindTime(new Date());
                        
                        UcBankCardExample bankExample = new UcBankCardExample();
                        bankExample.createCriteria().andUcUserIdEqualTo(ucUserId)
                        	.andCardNoEqualTo(lnBindCard.getBankCard()).andIdCardEqualTo(lnBindCard.getIdCard());
                        ucBankCardMapper.updateByExampleSelective(ucBankCard, bankExample);
                    }
                });
            }
            if(CollectionUtils.isEmpty(res.getSuccess_data())) {
                log.info("用户 {} 解绑卡失败：{}", lnUser.getHfUserId(), JSONObject.fromObject(res));
                String errorMsg = "";
                if(!CollectionUtils.isEmpty(res.getError_data())) {
                    lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                    lnPayOrders.setReturnCode(res.getError_data().get(0).getError_no());
                    lnPayOrders.setReturnMsg(res.getError_data().get(0).getError_info());
                    lnPayOrders.setUpdateTime(new Date());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                    errorMsg = res.getError_data().get(0).getError_info();
                } else {
                    lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                    lnPayOrders.setReturnCode(res.getResCode());
                    lnPayOrders.setReturnMsg("恒丰返回数据为空");
                    lnPayOrders.setUpdateTime(new Date());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                    errorMsg = "恒丰返回数据为空";
                }
                LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
                failjnl.setOrderId(lnPayOrders.getId());
                failjnl.setOrderNo(lnPayOrders.getOrderNo());
                failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                failjnl.setTransAmount(0d);
                failjnl.setSysTime(new Date());
                failjnl.setUserId(lnPayOrders.getLnUserId());
                failjnl.setCreateTime(new Date());
                payOrdersJnlMapper.insertSelective(failjnl);
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败：" + errorMsg);
            }
        } else {
            log.info("用户 {} 解绑卡失败：{}", lnUser.getHfUserId(), JSONObject.fromObject(res));
            lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
            lnPayOrders.setReturnCode(res.getResCode());
            lnPayOrders.setReturnMsg(res.getResMsg());
            lnPayOrders.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
            LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
            failjnl.setOrderId(lnPayOrders.getId());
            failjnl.setOrderNo(lnPayOrders.getOrderNo());
            failjnl.setTransAmount(0d);
            failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
            failjnl.setSysTime(new Date());
            failjnl.setUserId(lnPayOrders.getLnUserId());
            failjnl.setCreateTime(new Date());
            payOrdersJnlMapper.insertSelective(failjnl);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败");
        }
    }

    /**
     * 单客户端解绑（解绑再绑卡使用）
     * @param ucUserId
     * @param lnBindCard
     * @param lnUser
     */
    public void unBind4Zan2(final Integer ucUserId, final LnBindCard lnBindCard,final LnUser lnUser) {

        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andPay19BankCodeEqualTo(lnBindCard.getBankCode());
        List<Bs19payBank> bs19payBanks = bs19payBankMapper.selectByExample(bs19payBankExample);

        // 调用解绑接口
        String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(lnBindCard.getBankCard());
        lnPayOrders.setBankId(bs19payBanks.get(0).getBankId());
        lnPayOrders.setBankName(BaoFooEnum.bankCodeNameMap.get(lnBindCard.getBankCode()));
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(lnBindCard.getLnUserId());
        lnPayOrders.setMobile(lnUser.getMobile());
        lnPayOrders.setIdCard(lnUser.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(payBindOrderNo);
        lnPayOrders.setPartnerCode(lnUser.getPartnerCode());
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(lnUser.getUserName());
        payOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(lnPayOrders.getLnUserId());
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        payOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        B2GReqMsg_HFBank_BatchChangeCard unbindReq = new B2GReqMsg_HFBank_BatchChangeCard();
        unbindReq.setPartner_trans_time(lnPayOrders.getCreateTime());
        unbindReq.setPartner_trans_date(lnPayOrders.getCreateTime());
        unbindReq.setOrder_no(Util.generateOrderNo4BaoFoo(8));
        unbindReq.setTotal_num(1);
        List<BatchUpdateCardExtDetail> data = new ArrayList<>();
        BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
        detail.setPlatcust(lnUser.getHfUserId());
        detail.setCard_no(lnBindCard.getBankCard());
        detail.setCard_no_old(lnBindCard.getBankCard());
        detail.setDetail_no(lnPayOrders.getOrderNo());
        detail.setMobile(lnUser.getMobile());
        detail.setName(lnUser.getUserName());
        detail.setRemark("用户解绑卡");
        data.add(detail);
        unbindReq.setData(data);
        B2GResMsg_HFBank_BatchChangeCard res = hfbankTransportService.batchChangeCard(unbindReq);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            if(!CollectionUtils.isEmpty(res.getSuccess_data())) {
                log.info("用户 {} 解绑卡成功：{}", lnUser.getHfUserId(), JSONObject.fromObject(res));
                lnPayOrders.setStatus(Constants.ORDER_STATUS_SUCCESS);
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setNote(detail.getDetail_no());
                lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);

                //记录ln_pay_orders_jnl表
                LnPayOrdersJnl succJnl = new LnPayOrdersJnl();
                succJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
                succJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
                succJnl.setCreateTime(new Date());
                succJnl.setOrderId(lnPayOrders.getId());
                succJnl.setOrderNo(lnPayOrders.getOrderNo());
                succJnl.setUserId(lnPayOrders.getLnUserId());
                succJnl.setSysTime(new Date());
                succJnl.setTransAmount(0d);
                payOrdersJnlMapper.insertSelective(succJnl);

                transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        // uc_bind_card is_bind = N
                        UcBankCard ucBankCard = new UcBankCard();
                        //ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_UNBINDED);
                        ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                        ucBankCard.setUpdateTime(new Date());
//                        ucBankCard.setUnbindTime(new Date());

                        UcBankCardExample bankExample = new UcBankCardExample();
                        bankExample.createCriteria().andUcUserIdEqualTo(ucUserId)
                                .andCardNoEqualTo(lnBindCard.getBankCard()).andIdCardEqualTo(lnBindCard.getIdCard());
                        ucBankCardMapper.updateByExampleSelective(ucBankCard, bankExample);
                    }
                });
            }
            if(CollectionUtils.isEmpty(res.getSuccess_data())) {
                log.info("用户 {} 解绑卡失败：{}", lnUser.getHfUserId(), JSONObject.fromObject(res));
                String errorMsg = "";
                if(!CollectionUtils.isEmpty(res.getError_data())) {
                    lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                    lnPayOrders.setReturnCode(res.getError_data().get(0).getError_no());
                    lnPayOrders.setReturnMsg(res.getError_data().get(0).getError_info());
                    lnPayOrders.setUpdateTime(new Date());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                    errorMsg = res.getError_data().get(0).getError_info();
                } else {
                    lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
                    lnPayOrders.setReturnCode(res.getResCode());
                    lnPayOrders.setReturnMsg("恒丰返回数据为空");
                    lnPayOrders.setUpdateTime(new Date());
                    lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
                    errorMsg = "恒丰返回数据为空";
                }
                LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
                failjnl.setOrderId(lnPayOrders.getId());
                failjnl.setOrderNo(lnPayOrders.getOrderNo());
                failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                failjnl.setTransAmount(0d);
                failjnl.setSysTime(new Date());
                failjnl.setUserId(lnPayOrders.getLnUserId());
                failjnl.setCreateTime(new Date());
                payOrdersJnlMapper.insertSelective(failjnl);
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败：" + errorMsg);
            }
        } else {
            log.info("用户 {} 解绑卡失败：{}", lnUser.getHfUserId(), JSONObject.fromObject(res));
            lnPayOrders.setStatus(Constants.ORDER_STATUS_FAIL);
            lnPayOrders.setReturnCode(res.getResCode());
            lnPayOrders.setReturnMsg(res.getResMsg());
            lnPayOrders.setUpdateTime(new Date());
            lnPayOrdersMapper.updateByPrimaryKeySelective(lnPayOrders);
            LnPayOrdersJnl failjnl = new LnPayOrdersJnl();
            failjnl.setOrderId(lnPayOrders.getId());
            failjnl.setOrderNo(lnPayOrders.getOrderNo());
            failjnl.setTransAmount(0d);
            failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
            failjnl.setSysTime(new Date());
            failjnl.setUserId(lnPayOrders.getLnUserId());
            failjnl.setCreateTime(new Date());
            payOrdersJnlMapper.insertSelective(failjnl);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败");
        }
    }

    /**
     * 此卡是否已绑定成功
     *
     * @param bankCard 银行卡号
     * @return
     */
    private LnBindCard queryLoanBindCardSuccessExist(String bankCard, String cardHolder, String idCard, String mobile) {

        LnBindCardExample example = new LnBindCardExample();
        example.createCriteria().andBankCardEqualTo(bankCard)
                .andIdCardEqualTo(idCard)
                .andUserNameEqualTo(cardHolder)
                .andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andPartnerCodeEqualTo(PartnerEnum.ZAN.getCode());
        List<LnBindCard> list = lnBindCardMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(list) && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }
}
