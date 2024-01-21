package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.enums.UnBindCheckResultEnum;
import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.accounting.loan.enums.LoanStatus;
import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.PoliceVerifyBusinessTypeEnum;
import com.pinting.business.enums.PoliceVerifyCheckStatusEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_UnbindApplyPoliceVerify;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_UploadPicPoliceVerify;
import com.pinting.business.hessian.site.message.ResMsg_Bank_UnbindApplyPoliceVerify;
import com.pinting.business.hessian.site.message.ResMsg_Bank_UploadPicPoliceVerify;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BankBinVO;
import com.pinting.business.model.vo.UnbindCheckResVO;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.business.util.IdcardUtils;
import com.pinting.business.util.MobileCheckUtil;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.HfbankTransportService;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * Created by babyshark on 2016/9/10.
 */
@Service
public class UserCardOperateServiceImpl implements UserCardOperateService {
    private Logger logger = LoggerFactory.getLogger(UserCardOperateServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BsBankCardMapper bsBankCardMapper;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private BsPayReceiptMapper bsPayReceiptMapper;
    @Autowired
    private BankCardService bankCardService;
    @Autowired
    private BsBankCardService bsBankCardService;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private	HfbankTransportService hfbankTransportService;
    @Autowired
    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
	@Autowired
	private AccountHandleService accountHandleService;
	@Autowired
	private SubAccountService subAccountService;
    @Autowired
    private UserService userService;
    @Autowired
    private SMSService smsService;
    @Autowired
    private LnUserMapper lnUserMapper;
    @Autowired
    private BsUserTransDetailMapper bsUserTransDetailMapper;
    @Autowired
    private LnPayOrdersMapper lnPayOrdersMapper;
    @Autowired
    private LnPayOrdersJnlMapper lnPayOrdersJnlMapper;
    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcUserExtMapper ucUserExtMapper;
    @Autowired
    private UcBankCardMapper ucBankCardMapper;
    @Autowired
    private BsUserPoliceVerifyMapper bsUserPoliceVerifyMapper;
    @Autowired
    private SpecialJnlService specialJnlService;

    /**
     * 预绑卡
     *
     * @param userName     银行卡持有人姓名
     * @param idCard       身份证
     * @param cardNo       银行卡号
     * @param mobile       预留手机号
     * @param bankId       银行ID
     * @param userId       用户ID
     * @param terminalType 终端类型（1-H5端；2-PC端；3-Android端；4-iOS端）
     */
    @Override
    public String preBindCard(String userName, String idCard, String cardNo,
                              String mobile, String bankId, String userId, String terminalType) {
        // 预绑卡
        // 0、去空格
        // 零、数据校验
        // 0、非空校验
        // 1、验证是否身份证
        // 2、判断用户是否存在
        // 3、判断用户是否未成年
        // 4、未绑卡用户。判断用户年龄。首次绑卡（充值，购买），支付年龄仅限制在18-70岁之间
        // 5、未绑卡的用户需要验证是否已经绑卡
        // 6、资产端是否绑卡
        // 一、预绑卡初始化数据：
        // 2、插入订单表。状态为创建-1；订单渠道：BAOFOO；交易类型：预绑卡；渠道交易类型：快捷User
        // 3、插入订单流水表，状态为创建-1
        // 二、调用预绑卡接口成功后续操作
        // 1、更新订单表。状态为预下单成功-3；
        // 2、新增订单流水表。
        // 三、调用预绑卡接口失败后续操作
        // 1、更新订单表。状态为预下单失败-4||2；
        // 2、新增订单流水表。

        // 0、去空格
        userName = StringUtil.trim(userName);
        idCard = StringUtil.trim(idCard);
        cardNo = StringUtil.trim(cardNo);
        mobile = StringUtil.trim(mobile);
        bankId = StringUtil.trim(bankId);
        userId = StringUtil.trim(userId);
        terminalType = StringUtil.trim(terminalType);
        logger.info("预绑卡请求信息：{}", "userName = " + userName + ";idCard = " + idCard + ";cardNo = " + cardNo + ";mobile = " + mobile + ";bankId = " + bankId + ";userId = " + userId + ";terminalType = " + terminalType);

        // 零、数据校验
        // 0、非空校验
        if(StringUtil.isBlank(userName) || StringUtil.isBlank(idCard) || StringUtil.isBlank(cardNo)
                || StringUtil.isBlank(mobile) || StringUtil.isBlank(bankId) || StringUtil.isBlank(userId)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        // 1、校验手机格式
        if(!MobileCheckUtil.isMobile(mobile)) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "手机格式不正确");
        }
        // 1、验证身份证是否正确
        if(!IdcardUtils.validateCard(idCard)) {
            throw new PTMessageException(PTMessageEnum.USER_ID_CARD_ERROR);
        }
        // 2、判断用户是否存在
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(Integer.parseInt(userId));
        if(!(bsUser != null && bsUser.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }
        // 3、判断用户是否未成年
        if(!IdcardUtils.isAdultAge(idCard)) {
            throw new PTMessageException(PTMessageEnum.USER_NOT_ADULT);
        }
        // 4、未绑卡用户。判断用户年龄。首次绑卡，年龄仅限制在18-70岁之间
        if(null != bsUser.getIsBindBank() && bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_NO)){
            List<BsBankCard> unBindCards = bankCardService.findBankCardByStatusAndUserId(Constants.BANK_CARD_UNBIND, Integer.parseInt(userId));
            //非解绑用户，需要校验70岁限制
            if(CollectionUtils.isEmpty(unBindCards)) {
                if(IdcardUtils.isOldAge(idCard, Constants.OLD_MAN_AGE_LIMIT)) {
                    throw new PTMessageException(PTMessageEnum.USER_IS_OLD_MAN);
                }
            }
        }
        // 5、未绑卡的用户需要验证是否已经绑卡
        if(null != bsUser.getIsBindBank() && bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_NO)) {
            BsBankCardExample bankCardExample = new BsBankCardExample();
            bankCardExample.createCriteria().andIdCardEqualTo(idCard).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
            List<BsBankCard> cardList = bsBankCardMapper.selectByExample(bankCardExample);
            if(!CollectionUtils.isEmpty(cardList)) {
                throw new PTMessageException(PTMessageEnum.BANKCARD_IS_BIND);
            }

            // 如果用户出现过解绑操作，身份证号必须一致才能通过验证
            BsBankCardExample bcExample = new BsBankCardExample();
            bcExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId)).andStatusEqualTo(Constants.BANK_CARD_UNBIND);
            List<BsBankCard> bcList = bsBankCardMapper.selectByExample(bcExample);
            if(!CollectionUtils.isEmpty(bcList)) {
                for(BsBankCard card : bcList) {
                    if(!idCard.equalsIgnoreCase(card.getIdCard())) {
                        throw new PTMessageException(PTMessageEnum.IDCARD_IS_ERROR);
                    }
                }
            }
        } else if(null != bsUser.getIsBindBank() && bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_YES)) {
            // 当前用户已经绑卡，无需进行绑卡操作
            throw new PTMessageException(PTMessageEnum.BANKCARD_IS_BIND);
        }
        BankBinVO bankBinVO = bankCardService.queryBankBin(cardNo);
        if(bankBinVO == null) {
            throw new PTMessageException(PTMessageEnum.CARD_BIN_ERROR);
        } else if(!Integer.valueOf(bankId).equals(bankBinVO.getBankId())) {
            throw new PTMessageException(PTMessageEnum.CARD_BIN_ERROR);
        }

        // 若uc_user表存在当前身份证，则校验四要素
        UcUserExample ucUserExample = new UcUserExample();
        ucUserExample.createCriteria().andIdCardEqualTo(idCard);
        List<UcUser> ucUsers = ucUserMapper.selectByExample(ucUserExample);
        if(!CollectionUtils.isEmpty(ucUsers)) {
            UcUser ucUser = ucUsers.get(0);
            if(!ucUser.getUserName().equals(userName)) {
                throw new PTMessageException(PTMessageEnum.CARD_INFO_ERROR, "持卡人姓名错误");
            }
        }

        final Map<String, String> returnMap = new HashMap<>();
        // 一、预绑卡初始化数据：
        // 2、插入订单表。状态为创建-1；订单渠道：BAOFOO；交易类型：预绑卡；渠道交易类型：快捷

        BsPayOrders order = new BsPayOrders();
        order.setOrderNo(Util.generateOrderNo4BaoFoo(8));
        order.setUserId(Integer.parseInt(userId));
        order.setChannel(Constants.CHANNEL_HFBANK);
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(bankId)));
        order.setTerminalType(Integer.parseInt(terminalType));
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setBankId(Integer.parseInt(bankId));
        order.setBankCardNo(cardNo);
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
        order.setTransType(Constants.TRANS_USER_BIND_CARD);
        order.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
        order.setMobile(mobile);
        order.setIdCard(idCard);
        order.setAmount(0d);
        order.setUserName(userName);
        bsPayOrdersMapper.insertSelective(order);
        // 3、插入订单流水表，状态为创建-1
        BsPayOrdersJnl jnl = new BsPayOrdersJnl();
        jnl.setOrderId(order.getId());
        jnl.setOrderNo(order.getOrderNo());
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
        jnl.setTransAmount(order.getAmount());
        jnl.setSysTime(new Date());
        jnl.setUserId(Integer.parseInt(userId));
        jnl.setCreateTime(new Date());
        bsPayOrdersJnlMapper.insertSelective(jnl);

    	//发送短信
		String sendCode = null;
		String returnMsg = null;
		try {
			sendCode = smsService.sendBindCardPreToken(mobile, "1");
		}catch (Exception e){
			e.printStackTrace();
			sendCode = Constants.SEND_CODE_ERROR;
			if(e instanceof PTMessageException){
				returnMsg = ((PTMessageException) e).getErrMessage();
			}
		}
		if(Constants.SEND_CODE_ERROR.equals(sendCode)){

            // 三、调用预绑卡接口失败后续操作
            logger.info("恒丰预绑卡操作失败,订单号：{"+order.getOrderNo()+"}");
            // 1、更新订单表。状态为预下单失败-4；
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_FAIL);
            preOrder.setReturnMsg("预绑卡失败" + returnMsg);
            bsPayOrdersMapper.updateByPrimaryKeySelective(preOrder);
            // 2、新增订单流水表。
            BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
            failJnl.setOrderId(order.getId());
            failJnl.setOrderNo(order.getOrderNo());
            failJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_FAIL);
            failJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            failJnl.setTransAmount(order.getAmount());
            failJnl.setSysTime(new Date());
            failJnl.setUserId(Integer.parseInt(userId));
            failJnl.setCreateTime(new Date());
            failJnl.setReturnMsg("预绑卡失败" + returnMsg);
            bsPayOrdersJnlMapper.insertSelective(failJnl);

            // 4. 错误信息
            returnMap.put("code", PTMessageEnum.USER_ORDER_PRE_FAIL.getCode());
            returnMap.put("message", returnMsg);
        
		}else {
            // 二、调用预绑卡接口后续操作
            logger.info("恒丰预绑卡操作成功,订单号：{"+order.getOrderNo()+"}");
            // 1、更新订单表。状态为预下单成功-3
            BsPayOrders preOrder = new BsPayOrders();
            preOrder.setId(order.getId());
            preOrder.setUpdateTime(new Date());
            preOrder.setStatus(Constants.ORDER_STATUS_PRE_SUCC);
            preOrder.setReturnMsg(returnMsg);
            bsPayOrdersMapper.updateByPrimaryKeySelective(preOrder);
            // 2、新增订单流水表。
            BsPayOrdersJnl preJnl = new BsPayOrdersJnl();
            preJnl.setOrderId(order.getId());
            preJnl.setOrderNo(order.getOrderNo());
            preJnl.setTransCode(Constants.ORDER_TRANS_CODE_PRE_SUCC);
            preJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            preJnl.setTransAmount(order.getAmount());
            preJnl.setSysTime(new Date());
            preJnl.setUserId(Integer.parseInt(userId));
            preJnl.setCreateTime(new Date());
            preJnl.setReturnMsg(returnMsg);
            bsPayOrdersJnlMapper.insertSelective(preJnl);
            // 3、插入签约回执表
            // 4. 成功信息
            returnMap.put("code", ConstantUtil.BSRESCODE_SUCCESS);
            returnMap.put("message", "成功");
        
		}

        if(!ConstantUtil.BSRESCODE_SUCCESS.equals(returnMap.get("code"))) {
            // 失败
            throw new PTMessageException(returnMap.get("code"), returnMap.get("message"));
        }
        return order.getOrderNo();
    }

    /**
     * 正式绑卡
     *
     * @param bgwOrderNo 币港湾绑卡订单号
     * @param smsCode    验证码
     * @param userId     用户ID
     */
    @Override
    public void bindCard(String bgwOrderNo, String smsCode, String userId) {
        // 正式绑卡
        // 0. 去空格
        // 零、数据校验
        // 1、非空校验
        // 2、判断用户是否存在
        // 3、校验订单是否存在
        // 4、判断用户是否未成年
        // 5、未绑卡的用户需要验证是否已经绑卡
        // 6、未绑卡用户。判断用户年龄。首次绑卡（充值，购买），支付年龄仅限制在18-70岁之间
        // 一、调用正式绑卡接口成功后续操作
        // 1、更新订单表。状态为正式下单成功-6；
        // 2、新增订单流水表。
        // 3、更新支付签约回执表。已绑卡
        // 4、更新银行卡信息表
        // 5、更新用户表
        // 二、调用正式绑卡接口失败后续操作
        // 1、更新订单表。状态为正式下单失败-7；
        // 2、新增订单流水表。
        // 3、更新银行卡信息为失败

        // 0、去空格
        B2GResMsg_HFBank_BatchBindCard4Elements res = new B2GResMsg_HFBank_BatchBindCard4Elements();
        smsCode = StringUtil.trimStr(smsCode);
        userId = StringUtil.trimStr(userId);
        bgwOrderNo = StringUtil.trimStr(bgwOrderNo);
        final String bindOrderNo = StringUtil.trimStr(bgwOrderNo);

        logger.info("正式绑卡请求信息：{}", "smsCode = " + smsCode, ";userId = " + userId + ";orderNo = " + bgwOrderNo);

        final Map<String, String> returnMap = new HashMap<>();
        // 零、数据校验
        // 1、非空校验
        if (StringUtil.isBlank(bgwOrderNo) || StringUtil.isBlank(smsCode) || StringUtil.isBlank(userId)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }

        // 2. 绑卡短信验证码 必须4位，否则提示客户“请输入4位验证码”
        if (smsCode.length() != 4) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_MUST_SIX.getCode(), "请输入4位验证码");
        }
        if (!StringUtil.isNumeric(smsCode)) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_MUST_NUMBER);
        }

        // 2、判断用户是否存在
        final BsUser bsUser = bsUserMapper.selectByPrimaryKey(Integer.parseInt(userId));
        if (!(bsUser != null && bsUser.getStatus() == Constants.USER_STATUS_1)) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        // 3、校验订单是否存在
        BsPayOrdersExample bsPayOrdersExample = new BsPayOrdersExample();
        bsPayOrdersExample.createCriteria().andOrderNoEqualTo(bgwOrderNo).andUserIdEqualTo(Integer.parseInt(userId)).andStatusEqualTo(Constants.ORDER_STATUS_PRE_SUCC);
        List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(bsPayOrdersExample);
        final BsPayOrders order;
        if (!CollectionUtils.isEmpty(orderList)) {
            order = orderList.get(0);
        } else {
            throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
        }
        // 4、判断用户是否未成年
        if (!IdcardUtils.isAdultAge(order.getIdCard())) {
            throw new PTMessageException(PTMessageEnum.USER_NOT_ADULT);
        }
        try {
            jsClientDaoSupport.lock("BIND_CARD_" + order.getIdCard());
            // 5、未绑卡的用户需要验证是否已经绑卡
            if (null != bsUser.getIsBindBank() && bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_NO)) {
                BsBankCardExample bankCardExample = new BsBankCardExample();
                bankCardExample.createCriteria().andIdCardEqualTo(order.getIdCard()).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
                List<BsBankCard> cardList = bsBankCardMapper.selectByExample(bankCardExample);
                if (!CollectionUtils.isEmpty(cardList)) {
                    throw new PTMessageException(PTMessageEnum.BANKCARD_IS_BIND);
                }

                // 如果用户出现过解绑操作，身份证号必须一致才能通过验证
                BsBankCardExample bcExample = new BsBankCardExample();
                bcExample.createCriteria().andUserIdEqualTo(Integer.parseInt(userId)).andStatusEqualTo(Constants.BANK_CARD_UNBIND);
                List<BsBankCard> bcList = bsBankCardMapper.selectByExample(bcExample);
                if (!CollectionUtils.isEmpty(bcList)) {
                    for (BsBankCard card : bcList) {
                        if (!bsUser.getIdCard().equalsIgnoreCase(card.getIdCard())) {
                            throw new PTMessageException(PTMessageEnum.IDCARD_IS_ERROR);
                        }
                    }
                }
            } else if (null != bsUser.getIsBindBank() && bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_YES)) {
                // 当前用户已经绑卡，无需进行绑卡操作
                throw new PTMessageException(PTMessageEnum.BANKCARD_IS_BIND);
            }
            BankBinVO bankBinVO = bankCardService.queryBankBin(order.getBankCardNo());
            if (bankBinVO == null) {
                throw new PTMessageException(PTMessageEnum.CARD_BIN_ERROR);
            } else if (!order.getBankId().equals(bankBinVO.getBankId())) {
                throw new PTMessageException(PTMessageEnum.CARD_BIN_ERROR);
            }
            // 6、未绑卡用户。判断用户年龄。首次绑卡，年龄仅限制在18-70岁之间
            if (null != bsUser.getIsBindBank() && bsUser.getIsBindBank().equals(Constants.IS_BIND_BANK_NO)) {
                List<BsBankCard> unBindCards = bankCardService.findBankCardByStatusAndUserId(Constants.BANK_CARD_UNBIND, Integer.parseInt(userId));
                //非解绑用户，需要校验70岁限制
                if (CollectionUtils.isEmpty(unBindCards)) {
                    if (IdcardUtils.isOldAge(order.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)) {
                        throw new PTMessageException(PTMessageEnum.USER_IS_OLD_MAN);
                    }
                }
            }

            // 批量绑卡 需要币港湾校验验证码
            boolean IsValidate;
            String validateCode = "";
            String validateMsg = "";
            try {
                IsValidate = smsService.validateIdentity(order.getMobile(), smsCode, true);
            } catch (PTMessageException e) {
                IsValidate = false;
                validateCode = e.getErrCode();
                validateMsg = e.getErrMessage();
            } catch (Exception e) {
                IsValidate = false;
                validateCode = ConstantUtil.BSRESCODE_FAIL;
                validateMsg = "短信校验失败";
            }
            if (!IsValidate) {
                // 1、更新订单表。状态为正式下单失败-7；
                BsPayOrders failOrder = new BsPayOrders();
                failOrder.setId(order.getId());
                failOrder.setUpdateTime(new Date());
                failOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                failOrder.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
                failOrder.setReturnMsg("正式绑卡失败：" + validateMsg);
                bsPayOrdersMapper.updateByPrimaryKeySelective(failOrder);
                // 2、新增订单流水表。
                BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
                failJnl.setOrderId(order.getId());
                failJnl.setOrderNo(order.getOrderNo());
                failJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                failJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
                failJnl.setTransAmount(order.getAmount());
                failJnl.setSysTime(new Date());
                failJnl.setUserId(Integer.parseInt(userId));
                failJnl.setCreateTime(new Date());
                failJnl.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
                failJnl.setReturnMsg("正式绑卡失败："+validateMsg);
                bsPayOrdersJnlMapper.insertSelective(failJnl);

                // 3、更新支付签约回执表。未绑卡
                BsPayReceiptExample updateReceiptExample = new BsPayReceiptExample();
                updateReceiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_HFBANK);
                BsPayReceipt bsPayReceiptTmp = new BsPayReceipt();
                bsPayReceiptTmp.setIsBindCard(Constants.BAOFOO_BIND_NO);
                bsPayReceiptTmp.setUpdateTime(new Date());
                bsPayReceiptMapper.updateByExampleSelective(bsPayReceiptTmp, updateReceiptExample);
                throw new PTMessageException(validateCode, validateMsg);
            }

            //绑卡判断
            String interfaceFlag = "default"; // 默认批量开户四要素接口
            UcUserExample userExample = new UcUserExample();
            userExample.createCriteria().andStatusEqualTo(Constants.UC_USER_OPEN).andMobileEqualTo(bsUser.getMobile());
            final List<UcUser> ucUsers = ucUserMapper.selectByExample(userExample);
            if (CollectionUtils.isEmpty(ucUsers)) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND, ",用户中心uc_user信息不存在");
            }

            UcUserExample user2Example = new UcUserExample();
            user2Example.createCriteria().andIdCardEqualTo(order.getIdCard()).andStatusEqualTo(Constants.UC_USER_OPEN);
            List<UcUser> ucUser2s = ucUserMapper.selectByExample(user2Example);

            final UcUser ucUser;
            if(!CollectionUtils.isEmpty(ucUser2s)) {
                ucUser = ucUser2s.get(0);
            } else {
                ucUser = ucUsers.get(0);
            }
            if (StringUtil.isNotEmpty(ucUser.getHfUserId())) {
                BsBankCardExample exampleBsBank = new BsBankCardExample();
                exampleBsBank.createCriteria().andIsFirstEqualTo(Constants.BANK_CARD_USERED)
                        .andIdCardEqualTo(order.getIdCard()).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
                List<BsBankCard> bsBankCards = bsBankCardMapper.selectByExample(exampleBsBank);
                if (!CollectionUtils.isEmpty(bsBankCards)) {
                    //理财端已绑卡
                    throw new PTMessageException(PTMessageEnum.USER_BANK_CARD_BINDED);
                } else {
                    //理财端未绑卡
                    UcBankCardExample exampleUcBankY = new UcBankCardExample();
                    exampleUcBankY.createCriteria().andIdCardEqualTo(order.getIdCard())
                            .andIsBindEqualTo(Constants.UC_BIND_CARD_YES);
                    List<UcBankCard> ucBankCardYs = ucBankCardMapper.selectByExample(exampleUcBankY);
                    if (CollectionUtils.isEmpty(ucBankCardYs)) {
                        //不存在Y的记录，调用恒丰绑卡
                        interfaceFlag = "only_bind_card";
                    } else {
                        //存在Y的记录
                        final UcBankCard ucBankCard = ucBankCardYs.get(0);
                        if (ucBankCard.getCardNo().equals(order.getBankCardNo())) {
                            final String finalUserId = userId;
                            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                                @Override
                                protected void doInTransactionWithoutResult(TransactionStatus transactionStatus) {
                                    //卡一致，同步uc_bank_card信息到bs_bank_card,return
                                    BsBankCard bsBankCard = new BsBankCard();
                                    bsBankCard.setMobile(ucBankCard.getMobile());
                                    bsBankCard.setUserId(Integer.parseInt(finalUserId));
                                    bsBankCard.setCardNo(ucBankCard.getCardNo());
                                    bsBankCard.setCardOwner(ucBankCard.getCardOwner());
                                    bsBankCard.setIdCard(ucBankCard.getIdCard());
                                    bsBankCard.setBankId(ucBankCard.getBankId());
                                    bsBankCard.setStatus(Constants.DAFY_BINDING_STAUTS_NORMAL);
                                    bsBankCard.setIsFirst(Constants.IS_FIRST_BANK_YES);
                                    bsBankCard.setBindTime(new Date());
                                    bsBankCard.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(ucBankCard.getBankId())));
                                    bsBankCardMapper.insertSelective(bsBankCard);

                                    // 更新用户表
                                    BsUser bsUserTmp = new BsUser();
                                    bsUserTmp.setId(bsUser.getId());
                                    bsUserTmp.setIsBindBank(Constants.IS_BIND_BANK_YES);
                                    bsUserTmp.setIsBindName(Constants.IS_BIND_NAME_YES);
                                    bsUserTmp.setIdCard(ucBankCard.getIdCard());
                                    bsUserTmp.setUserName(ucBankCard.getCardOwner());
                                    bsUserTmp.setRecentBankCardId(bsBankCard.getId());
                                    bsUserMapper.updateByPrimaryKeySelective(bsUserTmp);

                                    //更新或插入用户恒丰银行扩展信息表

                                    BsHfbankUserExtExample hfbankUserExtExample = new BsHfbankUserExtExample();
                                    hfbankUserExtExample.createCriteria().andUserIdEqualTo(bsUser.getId());
                                    List<BsHfbankUserExt> extList = bsHfbankUserExtMapper.selectByExample(hfbankUserExtExample);
                                    if (CollectionUtils.isEmpty(extList)) {
                                        BsHfbankUserExt bsHfbankUserExt = new BsHfbankUserExt();
                                        bsHfbankUserExt.setUserId(bsUser.getId());
                                        bsHfbankUserExt.setHfUserId(ucUser.getHfUserId());
                                        bsHfbankUserExt.setHfRegistTime(new Date());
                                        bsHfbankUserExt.setHfBindCardTime(new Date());
                                        bsHfbankUserExt.setStatus(Constants.HFBANK_USER_EXT_STATUS_OPEN);
                                        bsHfbankUserExt.setNote(null);
                                        bsHfbankUserExt.setCreateTime(new Date());
                                        bsHfbankUserExt.setUpdateTime(new Date());
                                        bsHfbankUserExtMapper.insertSelective(bsHfbankUserExt);
                                    } else {
                                        BsHfbankUserExt bsHfbankUserExt = new BsHfbankUserExt();
                                        bsHfbankUserExt.setHfUserId(ucUser.getHfUserId());
                                        bsHfbankUserExt.setHfBindCardTime(new Date());
                                        bsHfbankUserExt.setStatus(Constants.HFBANK_USER_EXT_STATUS_OPEN);
                                        bsHfbankUserExt.setUpdateTime(new Date());
                                        bsHfbankUserExtMapper.updateByExampleSelective(bsHfbankUserExt, hfbankUserExtExample);
                                    }

                                    //开通DEP_JSH，新增(存管结算户)子账户表
                                    BsSubAccount bsSubAccountJsh = subAccountService.findJSHSubAccountByUserId(bsUser.getId());
                                    BsSubAccount depJsh = subAccountService.findDEPJSHSubAccountByUserId(bsUser.getId());
                                    if (null == depJsh) {
                                        String code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_DEP_JSH, bsUser.getId());
                                        BsSubAccount bsSubAccount = new BsSubAccount();
                                        bsSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_OPEN);
                                        bsSubAccount.setOpenBalance(0.0);
                                        bsSubAccount.setBalance(0.0);
                                        bsSubAccount.setAvailableBalance(0.0);
                                        bsSubAccount.setFreezeBalance(0.0);
                                        bsSubAccount.setCanWithdraw(0.0);
                                        bsSubAccount.setAccumulationInerest(0.0);
                                        bsSubAccount.setProductType(Constants.PRODUCT_TYPE_DEP_JSH);
                                        bsSubAccount.setTransStatus(0);
                                        bsSubAccount.setAccountId(bsSubAccountJsh.getAccountId());
                                        bsSubAccount.setCode(code);
                                        bsSubAccount.setOpenTime(new Date());
                                        subAccountService.addSubAccount(bsSubAccount);
                                    }

                                    //根据身份证和状态查询uc_user表，如果有，且UC用户编号不一样，则废除原UC注册帐号，加挂到此UC帐号下面
                                    UcUserExample user2Example = new UcUserExample();
                                    user2Example.createCriteria().andIdCardEqualTo(order.getIdCard()).andStatusEqualTo(Constants.UC_USER_OPEN);
                                    List<UcUser> ucUser2s = ucUserMapper.selectByExample(user2Example);
                                    if (!CollectionUtils.isEmpty(ucUser2s) && !ucUser2s.get(0).getId().equals(ucUsers.get(0).getId())) {
                                        UcUser closeUser = new UcUser();
                                        closeUser.setId(ucUsers.get(0).getId());
                                        closeUser.setStatus(Constants.UC_USER_CLOSE);
                                        closeUser.setUpdateTime(new Date());
                                        ucUserMapper.updateByPrimaryKeySelective(closeUser);
                                        UcUserExt ucUserExt = new UcUserExt();
                                        ucUserExt.setCreateTime(new Date());
                                        ucUserExt.setUcUserId(ucUser2s.get(0).getId());
                                        ucUserExt.setUserType(Constants.UC_USER_TYPE_BGW);
                                        ucUserExt.setUserId(bsUser.getId());
                                        ucUserExtMapper.insertSelective(ucUserExt);
                                        UcUser openUcUser = new UcUser();
                                        openUcUser.setId(ucUser2s.get(0).getId());
                                        openUcUser.setMobile(ucUsers.get(0).getMobile());
                                        openUcUser.setUpdateTime(new Date());
                                        ucUserMapper.updateByPrimaryKeySelective(openUcUser);
                                    }

                                    // 更新订单表为下单成功-6；
                                    BsPayOrders successOrder = new BsPayOrders();
                                    successOrder.setId(order.getId());
                                    successOrder.setUpdateTime(new Date());
                                    successOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
                                    successOrder.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                                    successOrder.setReturnMsg("借款端已绑卡，uc信息直接同步到理财端");
                                    bsPayOrdersMapper.updateByPrimaryKeySelective(successOrder);
                                    // 新增订单流水表。
                                    BsPayOrdersJnl succJnl = new BsPayOrdersJnl();
                                    succJnl.setOrderId(order.getId());
                                    succJnl.setOrderNo(order.getOrderNo());
                                    succJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                                    succJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
                                    succJnl.setTransAmount(order.getAmount());
                                    succJnl.setSysTime(new Date());
                                    succJnl.setUserId(Integer.parseInt(finalUserId));
                                    succJnl.setCreateTime(new Date());
                                    succJnl.setReturnCode(ConstantUtil.BSRESCODE_SUCCESS);
                                    succJnl.setReturnMsg("借款端已绑卡，uc信息直接同步到理财端");
                                    bsPayOrdersJnlMapper.insertSelective(succJnl);

                                    //bs_pay_receipt暂时继续维护
                                    BsPayReceiptExample receiptExample = new BsPayReceiptExample();
                                    receiptExample.createCriteria().andUserIdEqualTo(bsBankCard.getUserId()).andBankCardNoEqualTo(bsBankCard.getCardNo())
                                            .andChannelEqualTo(Constants.CHANNEL_HFBANK);
                                    List<BsPayReceipt> receiptList = bsPayReceiptMapper.selectByExample(receiptExample);
                                    if (CollectionUtils.isEmpty(receiptList)) {
                                        BsPayReceipt bsPayReceipt = new BsPayReceipt();
                                        bsPayReceipt.setBankCardNo(bsBankCard.getCardNo());
                                        bsPayReceipt.setUserId(Integer.parseInt(finalUserId));
                                        bsPayReceipt.setUserName(bsBankCard.getCardOwner());
                                        bsPayReceipt.setMobile(bsBankCard.getMobile());
                                        bsPayReceipt.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(bsBankCard.getBankId())));
                                        bsPayReceipt.setIdCard(bsBankCard.getIdCard());
                                        bsPayReceipt.setChannel(Constants.CHANNEL_HFBANK);
                                        bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_YES);
                                        bsPayReceipt.setCreateTime(new Date());
                                        bsPayReceipt.setUpdateTime(new Date());
                                        bsPayReceiptMapper.insertSelective(bsPayReceipt);
                                    } else {
                                        for (BsPayReceipt receipt : receiptList) {
                                            receipt.setMobile(bsBankCard.getMobile());
                                            receipt.setUserName(bsBankCard.getCardOwner());
                                            receipt.setIdCard(bsBankCard.getIdCard());
                                            receipt.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(bsBankCard.getBankId())));
                                            receipt.setUpdateTime(new Date());
                                            bsPayReceiptMapper.updateByPrimaryKeySelective(receipt);
                                        }
                                    }
                                    returnMap.put("code", ConstantUtil.BSRESCODE_SUCCESS);
                                    returnMap.put("message", "成功");
                                }
                            });
                            return;
                        } else {
                            //卡不一致,解绑+绑卡
                            unBind(ucUser, ucBankCard);
                            interfaceFlag = "only_bind_card";
                        }
                    }
                }
            }
            // 新增bs_bank_card绑定中
            final BsBankCard bsBankCard = new BsBankCard();
            bsBankCard.setMobile(order.getMobile());
            bsBankCard.setUserId(Integer.parseInt(userId));
            bsBankCard.setCardNo(order.getBankCardNo());
            bsBankCard.setCardOwner(order.getUserName());
            bsBankCard.setIdCard(order.getIdCard());
            bsBankCard.setBankId(order.getBankId());
            bsBankCard.setStatus(Constants.DAFY_BINDING_STAUTS_BINDING);
            bsBankCard.setIsFirst(Constants.IS_FIRST_BANK_NO);
            bsBankCard.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(order.getBankId())));
            bsBankCardMapper.insertSelective(bsBankCard);

            BsPayReceiptExample receiptExample = new BsPayReceiptExample();
            receiptExample.createCriteria().andUserIdEqualTo(bsBankCard.getUserId()).andBankCardNoEqualTo(bsBankCard.getCardNo())
                    .andChannelEqualTo(Constants.CHANNEL_HFBANK);
            List<BsPayReceipt> receiptList = bsPayReceiptMapper.selectByExample(receiptExample);
            if (CollectionUtils.isEmpty(receiptList)) {
                BsPayReceipt bsPayReceipt = new BsPayReceipt();
                bsPayReceipt.setBankCardNo(bsBankCard.getCardNo());
                bsPayReceipt.setUserId(Integer.parseInt(userId));
                bsPayReceipt.setUserName(bsBankCard.getCardOwner());
                bsPayReceipt.setMobile(bsBankCard.getMobile());
                bsPayReceipt.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(bsBankCard.getBankId())));
                bsPayReceipt.setIdCard(bsBankCard.getIdCard());
                bsPayReceipt.setChannel(Constants.CHANNEL_HFBANK);
                bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
                bsPayReceipt.setCreateTime(new Date());
                bsPayReceipt.setUpdateTime(new Date());
                bsPayReceiptMapper.insertSelective(bsPayReceipt);
            } else {
                for (BsPayReceipt receipt : receiptList) {
                    receipt.setMobile(bsBankCard.getMobile());
                    receipt.setUserName(bsBankCard.getCardOwner());
                    receipt.setIdCard(bsBankCard.getIdCard());
                    receipt.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(bsBankCard.getBankId())));
                    receipt.setUpdateTime(new Date());
                    bsPayReceiptMapper.updateByPrimaryKeySelective(receipt);
                }
            }

            boolean communication = true;
            String msg = "";
            if ("only_bind_card".equals(interfaceFlag)) {
                logger.info("用户 {} 已开户，调用恒丰绑卡接口", order.getUserId());
                // 单纯的绑卡（已开户）
                B2GReqMsg_HFBank_UserAddCard addCardReq = new B2GReqMsg_HFBank_UserAddCard();
                addCardReq.setOrder_no(bindOrderNo);
                addCardReq.setPartner_trans_date(new Date());
                addCardReq.setPartner_trans_time(new Date());
                addCardReq.setType(Constants.HF_BIND_CARD_TYPE_PERSON);//绑卡类型：1个人客户 2 对公客户
                addCardReq.setPlatcust(ucUser.getHfUserId());
                addCardReq.setId_type(Constants.HF_ID_TYPE_ID_CARD);    //1:身份证
                addCardReq.setId_code(order.getIdCard());
                addCardReq.setName(order.getUserName());
                addCardReq.setCard_no(order.getBankCardNo());
                addCardReq.setCard_type(Constants.HF_CARD_TYPE_DEBIT);    //1:借记卡,2:信用卡
                addCardReq.setPre_mobile(order.getMobile());
                addCardReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
                addCardReq.setRemark("个人绑卡");
                logger.info("绑卡请求：{}", JSONObject.fromObject(addCardReq));
                try {
                    B2GResMsg_HFBank_UserAddCard addCardRes = hfbankTransportService.userAddCard(addCardReq);
                    if (ConstantUtil.BSRESCODE_SUCCESS.equals(addCardRes.getResCode())) {
                        List<BatchRegistExtSuccess> success_data = new ArrayList<>();
                        BatchRegistExtSuccess success = new BatchRegistExtSuccess();
                        success.setDetail_no(bindOrderNo);
                        success.setMobile(order.getMobile());
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
                    logger.info("恒丰正式绑卡操作失败 {}，订单号：{}", e.getMessage(), bgwOrderNo);
                    msg = "网络通讯异常";
                }
            } else {
                logger.info("用户 {} 未开户，调用四要素批量绑卡接口", order.getUserId());
                // 绑卡（未开户）
                //请求恒丰订单号
                B2GReqMsg_HFBank_BatchBindCard4Elements req = new B2GReqMsg_HFBank_BatchBindCard4Elements();
                req.setTotal_num(1);
                List<BatchRegistExtDetail> details = new ArrayList<>();
                BatchRegistExtDetail detail = new BatchRegistExtDetail();
                detail.setDetail_no(bindOrderNo);
                detail.setName(order.getUserName());
                detail.setId_type(Constants.HF_ID_TYPE_ID_CARD);
                detail.setId_code(order.getIdCard());
                detail.setMobile(order.getMobile());
                detail.setEmail(null);
                detail.setSex(null);
                detail.setCus_type(null);
                detail.setBirthday(null);
                detail.setOpen_branch(null);
                detail.setCard_no(order.getBankCardNo());
                detail.setCard_type("1");
                detail.setPre_mobile(order.getMobile());
                detail.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
                detail.setNotify_url(null);
                detail.setRemark("批量绑卡");
                details.add(detail);
                req.setData(details);
                req.setOrder_no(Util.generateOrderNo4BaoFoo(8));
                req.setPartner_trans_date(new Date());
                req.setPartner_trans_time(new Date());
                logger.info("批量绑卡请求：{}", JSONObject.fromObject(req));
                try {
                    res = hfbankTransportService.batchBindCard4Elements(req);
                } catch (Exception e) {
                    communication = false;
                    logger.info("恒丰正式绑卡操作失败 {}，订单号：{}", e.getMessage(), bgwOrderNo);
                    msg = "网络通信异常";
                }
            }
            if (!communication) {
                // 二、调用正式绑卡接口失败后续操作
                // 1、更新订单表。状态为正式下单失败-7；
                BsPayOrders failOrder = new BsPayOrders();
                failOrder.setId(order.getId());
                failOrder.setUpdateTime(new Date());
                failOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                failOrder.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
                failOrder.setReturnMsg("正式绑卡失败：网络通信异常。");
                bsPayOrdersMapper.updateByPrimaryKeySelective(failOrder);
                // 2、新增订单流水表。
                BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
                failJnl.setOrderId(order.getId());
                failJnl.setOrderNo(order.getOrderNo());
                failJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                failJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
                failJnl.setTransAmount(order.getAmount());
                failJnl.setSysTime(new Date());
                failJnl.setUserId(Integer.parseInt(userId));
                failJnl.setCreateTime(new Date());
                failJnl.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
                failJnl.setReturnMsg("正式绑卡失败：网络通信异常。");
                //failJnl.setNote(bindOrderNo);
                bsPayOrdersJnlMapper.insertSelective(failJnl);

                // 3、更新支付签约回执表。未绑卡
                BsPayReceiptExample updateReceiptExample = new BsPayReceiptExample();
                updateReceiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_HFBANK);
                BsPayReceipt bsPayReceiptTmp = new BsPayReceipt();
                bsPayReceiptTmp.setIsBindCard(Constants.BAOFOO_BIND_NO);
                bsPayReceiptTmp.setUpdateTime(new Date());
                bsPayReceiptMapper.updateByExampleSelective(bsPayReceiptTmp, updateReceiptExample);

                // 4、更新银行卡信息为失败
                BsBankCard bankCardTmp = new BsBankCard();
                bankCardTmp.setId(bsBankCard.getId());
                bankCardTmp.setStatus(Constants.DAFY_BINDING_STAUTS_BIND_FAIL);
                bankCardTmp.setIsFirst(Constants.IS_FIRST_BANK_NO);
                bsBankCardMapper.updateByPrimaryKeySelective(bsBankCard);

                returnMap.put("code", PTMessageEnum.TRANS_ERROR.getCode());
                returnMap.put("message", msg);
                throw new PTMessageException(returnMap.get("code"), returnMap.get("message"));
            }
            if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && "1".equals(res.getSuccess_num()) && !CollectionUtils.isEmpty(res.getSuccess_data())) {
                logger.info("恒丰正式绑卡操作成功，订单号：{}，{}", bgwOrderNo, JSONObject.fromObject(res));
                final B2GResMsg_HFBank_BatchBindCard4Elements finalRes = res;
                final String finalUserId = userId;
                final BsBankCard finalBsBankCard = bsBankCard;
                final UcUser finalUcUser = ucUser;
                final String finalInterfaceFlag = interfaceFlag;
                transactionTemplate.execute(new TransactionCallback<Object>() {
                    @Override
                    public Object doInTransaction(TransactionStatus status) {

                        //根据身份证和状态查询uc_user表，如果有，且UC用户编号不一样，则废除原UC注册帐号，加挂到此UC帐号下面
                        UcUserExample user2Example = new UcUserExample();
                        user2Example.createCriteria().andIdCardEqualTo(order.getIdCard()).andStatusEqualTo(Constants.UC_USER_OPEN);
                        List<UcUser> ucUser2s = ucUserMapper.selectByExample(user2Example);
                        if (!CollectionUtils.isEmpty(ucUser2s) && !ucUser2s.get(0).getId().equals(ucUsers.get(0).getId())) {
                            UcUser closeUser = new UcUser();
                            closeUser.setId(ucUsers.get(0).getId());
                            closeUser.setStatus(Constants.UC_USER_CLOSE);
                            closeUser.setUpdateTime(new Date());
                            ucUserMapper.updateByPrimaryKeySelective(closeUser);
                            UcUserExt ucUserExt = new UcUserExt();
                            ucUserExt.setCreateTime(new Date());
                            ucUserExt.setUcUserId(ucUser2s.get(0).getId());
                            ucUserExt.setUserType(Constants.UC_USER_TYPE_BGW);
                            ucUserExt.setUserId(bsUser.getId());
                            ucUserExtMapper.insertSelective(ucUserExt);
                            UcUser openUcUser = new UcUser();
                            openUcUser.setId(ucUser2s.get(0).getId());
                            openUcUser.setMobile(ucUsers.get(0).getMobile());
                            openUcUser.setUpdateTime(new Date());
                            ucUserMapper.updateByPrimaryKeySelective(openUcUser);
                        }

                        // 1、更新订单表。状态为正式下单成功-6；
                        BsPayOrders successOrder = new BsPayOrders();
                        successOrder.setId(order.getId());
                        successOrder.setUpdateTime(new Date());
                        successOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
                        successOrder.setReturnCode(finalRes.getResCode());
                        successOrder.setReturnMsg(finalRes.getResMsg());
                        bsPayOrdersMapper.updateByPrimaryKeySelective(successOrder);
                        // 2、新增订单流水表。
                        BsPayOrdersJnl succJnl = new BsPayOrdersJnl();
                        succJnl.setOrderId(order.getId());
                        succJnl.setOrderNo(order.getOrderNo());
                        succJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                        succJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
                        succJnl.setTransAmount(order.getAmount());
                        succJnl.setSysTime(new Date());
                        succJnl.setUserId(Integer.parseInt(finalUserId));
                        succJnl.setCreateTime(new Date());
                        succJnl.setReturnCode(finalRes.getResCode());
                        succJnl.setReturnMsg(finalRes.getResMsg());
                        //succJnl.setNote(bindOrderNo);
                        bsPayOrdersJnlMapper.insertSelective(succJnl);
                        // 3、更新支付签约回执表。已绑卡
                        BsPayReceiptExample updateReceiptExample = new BsPayReceiptExample();
                        updateReceiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(finalBsBankCard.getCardNo()).andChannelEqualTo(Constants.CHANNEL_HFBANK);
                        BsPayReceipt bsPayReceiptTmp = new BsPayReceipt();
                        bsPayReceiptTmp.setIsBindCard(Constants.BAOFOO_BIND_YES);
                        bsPayReceiptTmp.setReceiptNo(null);
                        bsPayReceiptMapper.updateByExampleSelective(bsPayReceiptTmp, updateReceiptExample);
                        // 4、更新银行卡信息表
                        BsBankCard bankCardTmp = new BsBankCard();
                        bankCardTmp.setId(finalBsBankCard.getId());
                        bankCardTmp.setStatus(Constants.DAFY_BINDING_STAUTS_NORMAL);
                        bankCardTmp.setIsFirst(Constants.IS_FIRST_BANK_YES);
                        bankCardTmp.setBindTime(new Date());
                        bsBankCardMapper.updateByPrimaryKeySelective(bankCardTmp);

                        //5、uc_bank_card 置为已绑定
                        UcBankCardExample ucBankCardExample = new UcBankCardExample();
                        ucBankCardExample.createCriteria().andCardNoEqualTo(finalBsBankCard.getCardNo())
                                .andIdCardEqualTo(finalBsBankCard.getIdCard()).andIsBindEqualTo(Constants.UC_BIND_CARD_NO);
                        List<UcBankCard> ucCardList = ucBankCardMapper.selectByExample(ucBankCardExample);
                        if(CollectionUtils.isEmpty(ucCardList)) {
                            UcBankCard ucBankCard = new UcBankCard();
                            ucBankCard.setIdCard(finalBsBankCard.getIdCard());
                            ucBankCard.setUcUserId(finalUcUser.getId());
                            ucBankCard.setBankId(finalBsBankCard.getBankId());
                            ucBankCard.setBankName(finalBsBankCard.getBankName());
                            ucBankCard.setCardNo(finalBsBankCard.getCardNo());
                            ucBankCard.setCardOwner(finalBsBankCard.getCardOwner());
                            ucBankCard.setMobile(finalBsBankCard.getMobile());
                            ucBankCard.setCreateTime(new Date());
                            ucBankCard.setUpdateTime(new Date());
                            ucBankCard.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
                            ucBankCard.setIsBind(Constants.UC_BIND_CARD_YES);
                            ucBankCard.setBindTime(new Date());
                            ucBankCardMapper.insertSelective(ucBankCard);
                        } else {
                            UcBankCard card = ucCardList.get(0);
                            card.setStatus(Constants.UC_BIND_CARD_STATUS_BINDED);
                            card.setIsBind(Constants.UC_BIND_CARD_YES);
                            card.setBindTime(new Date());
                            card.setUpdateTime(new Date());
                            ucBankCardMapper.updateByPrimaryKeySelective(card);
                        }

                        // 6、更新用户表
                        BsUser bsUserTmp = new BsUser();
                        bsUserTmp.setId(bsUser.getId());
                        bsUserTmp.setIsBindBank(Constants.IS_BIND_BANK_YES);
                        bsUserTmp.setIsBindName(Constants.IS_BIND_NAME_YES);
                        bsUserTmp.setIdCard(finalBsBankCard.getIdCard());
                        bsUserTmp.setUserName(finalBsBankCard.getCardOwner());
                        bsUserTmp.setRecentBankCardId(finalBsBankCard.getId());
                        bsUserMapper.updateByPrimaryKeySelective(bsUserTmp);

                        //7、插入用户恒丰银行扩展信息表
                        BsHfbankUserExtExample hfbankUserExtExample = new BsHfbankUserExtExample();
                        hfbankUserExtExample.createCriteria().andUserIdEqualTo(bsUser.getId());
                        List<BsHfbankUserExt> extList = bsHfbankUserExtMapper.selectByExample(hfbankUserExtExample);
                        if (CollectionUtils.isEmpty(extList)) {
                            BsHfbankUserExt bsHfbankUserExt = new BsHfbankUserExt();
                            bsHfbankUserExt.setUserId(bsUser.getId());
                            bsHfbankUserExt.setHfUserId(finalRes.getSuccess_data().get(0).getPlatcust());
                            bsHfbankUserExt.setHfRegistTime(new Date());
                            bsHfbankUserExt.setHfBindCardTime(new Date());
                            bsHfbankUserExt.setStatus(Constants.HFBANK_USER_EXT_STATUS_OPEN);
                            bsHfbankUserExt.setNote(null);
                            bsHfbankUserExt.setCreateTime(new Date());
                            bsHfbankUserExt.setUpdateTime(new Date());
                            bsHfbankUserExtMapper.insertSelective(bsHfbankUserExt);
                        } else {
                            BsHfbankUserExt bsHfbankUserExt = new BsHfbankUserExt();
                            bsHfbankUserExt.setHfUserId(finalRes.getSuccess_data().get(0).getPlatcust());
                            bsHfbankUserExt.setHfBindCardTime(new Date());
                            bsHfbankUserExt.setStatus(Constants.HFBANK_USER_EXT_STATUS_OPEN);
                            bsHfbankUserExt.setUpdateTime(new Date());
                            bsHfbankUserExtMapper.updateByExampleSelective(bsHfbankUserExt, hfbankUserExtExample);
                        }

                        // 8、更新用户中心用户表
                        UcUser ucUserUpd = new UcUser();
                        ucUserUpd.setId(finalUcUser.getId());
                        //四要素开户时填入hf_user_id
                        if (!"only_bind_card".equals(finalInterfaceFlag)) {
                            ucUserUpd.setHfUserId(finalRes.getSuccess_data().get(0).getPlatcust());
                        }
                        ucUserUpd.setIdCard(finalBsBankCard.getIdCard());
                        ucUserUpd.setUserName(finalBsBankCard.getCardOwner());
                        ucUserUpd.setUpdateTime(new Date());
                        ucUserMapper.updateByPrimaryKeySelective(ucUserUpd);

                        BsSubAccount bsSubAccountJsh = subAccountService.findJSHSubAccountByUserId(bsUser.getId());
                        //9、开通DEP_JSH
                        //新增(存管结算户)子账户表
                        BsSubAccount depJsh = subAccountService.findDEPJSHSubAccountByUserId(bsUser.getId());
                        if (null == depJsh) {
                            String code = accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_DEP_JSH, bsUser.getId());
                            BsSubAccount bsSubAccount = new BsSubAccount();
                            bsSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_OPEN);
                            bsSubAccount.setOpenBalance(0.0);
                            bsSubAccount.setBalance(0.0);
                            bsSubAccount.setAvailableBalance(0.0);
                            bsSubAccount.setFreezeBalance(0.0);
                            bsSubAccount.setCanWithdraw(0.0);
                            bsSubAccount.setAccumulationInerest(0.0);
                            bsSubAccount.setProductType(Constants.PRODUCT_TYPE_DEP_JSH);
                            bsSubAccount.setTransStatus(0);
                            bsSubAccount.setAccountId(bsSubAccountJsh.getAccountId());
                            bsSubAccount.setCode(code);
                            bsSubAccount.setOpenTime(new Date());
                            subAccountService.addSubAccount(bsSubAccount);
                        }

                        return null;
                    }
                });
                returnMap.put("code", ConstantUtil.BSRESCODE_SUCCESS);
                returnMap.put("message", "成功");
            } else {
                // 二、调用正式绑卡接口失败后续操作
                logger.info("恒丰正式绑卡操作失败，订单号：{}，{}", bgwOrderNo, JSONObject.fromObject(res));
                // 1、更新订单表。状态为正式下单失败-7；
                final String finalUserId1 = userId;
                final B2GResMsg_HFBank_BatchBindCard4Elements finalRes1 = res;
                final BsBankCard finalBsBankCard1 = bsBankCard;
                transactionTemplate.execute(new TransactionCallback<Object>() {
                    @Override
                    public Object doInTransaction(TransactionStatus status) {
                        BsPayOrders failOrder = new BsPayOrders();
                        failOrder.setId(order.getId());
                        failOrder.setUpdateTime(new Date());
                        failOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                        String thirdReturnCode = "";
                        if (!CollectionUtils.isEmpty(finalRes1.getError_data())) {
                            thirdReturnCode = (String) finalRes1.getError_data().get(0).getError_no();
                        } else {
                            thirdReturnCode = finalRes1.getResCode();
                        }
                        failOrder.setReturnCode(thirdReturnCode);

                        failOrder.setReturnMsg("正式绑卡失败：" + finalRes1.getResMsg());
                        bsPayOrdersMapper.updateByPrimaryKeySelective(failOrder);
                        // 2、新增订单流水表。
                        BsPayOrdersJnl failJnl = new BsPayOrdersJnl();
                        failJnl.setOrderId(order.getId());
                        failJnl.setOrderNo(order.getOrderNo());
                        failJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                        failJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
                        failJnl.setTransAmount(order.getAmount());
                        failJnl.setSysTime(new Date());
                        failJnl.setUserId(Integer.parseInt(finalUserId1));
                        failJnl.setCreateTime(new Date());
                        failJnl.setReturnCode(Constants.ORDER_TRANS_CODE_FAIL);
                        failJnl.setReturnMsg("正式绑卡失败：" + finalRes1.getResMsg());
                        //failJnl.setNote(bindOrderNo);
                        bsPayOrdersJnlMapper.insertSelective(failJnl);

                        // 3、更新支付签约回执表。未绑卡
                        BsPayReceiptExample updateReceiptExample = new BsPayReceiptExample();
                        updateReceiptExample.createCriteria().andUserIdEqualTo(order.getUserId()).andBankCardNoEqualTo(order.getBankCardNo()).andChannelEqualTo(Constants.CHANNEL_HFBANK);
                        BsPayReceipt bsPayReceiptTmp = new BsPayReceipt();
                        bsPayReceiptTmp.setIsBindCard(Constants.BAOFOO_BIND_NO);
                        bsPayReceiptTmp.setUpdateTime(new Date());
                        bsPayReceiptMapper.updateByExampleSelective(bsPayReceiptTmp, updateReceiptExample);

                        // 4、更新银行卡信息为失败
                        BsBankCard bankCardTmp = new BsBankCard();
                        bankCardTmp.setId(finalBsBankCard1.getId());
                        bankCardTmp.setStatus(Constants.DAFY_BINDING_STAUTS_BIND_FAIL);
                        bankCardTmp.setIsFirst(Constants.IS_FIRST_BANK_NO);
                        bsBankCardMapper.updateByPrimaryKeySelective(bankCardTmp);

                        return null;
                    }
                });

                returnMap.put("code", PTMessageEnum.TRANS_ERROR.getCode());
                returnMap.put("message", res.getResMsg());
            }
            if (!ConstantUtil.BSRESCODE_SUCCESS.equals(returnMap.get("code"))) {
                throw new PTMessageException(returnMap.get("code"), returnMap.get("message"));
            }
        } finally {
            jsClientDaoSupport.unlock("BIND_CARD_" + order.getIdCard());
        }
    }

    /**
     * 借款端解绑（Y->N）
     * @param ucUser
     * @param ucBankCard
     */
    public void unBind(UcUser ucUser,UcBankCard ucBankCard){
        String payBindOrderNo = Util.generateOrderNo4BaoFoo(8);
        //记录ln_pay_orders
        LnPayOrders lnPayOrders = new LnPayOrders();
        lnPayOrders.setCreateTime(new Date());
        lnPayOrders.setAccountType(1);
        lnPayOrders.setBankCardNo(ucBankCard.getCardNo());
        lnPayOrders.setBankId(ucBankCard.getBankId());
        lnPayOrders.setBankName(ucBankCard.getBankName());
        lnPayOrders.setAmount(0d);
        lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
        lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrders.setLnUserId(null);
        lnPayOrders.setMobile(ucBankCard.getMobile());
        lnPayOrders.setIdCard(ucBankCard.getIdCard());
        lnPayOrders.setMoneyType(0);
        lnPayOrders.setOrderNo(payBindOrderNo);
        lnPayOrders.setPartnerCode(null);
        lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
        lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrders.setUpdateTime(new Date());
        lnPayOrders.setUserName(ucBankCard.getCardOwner());
        lnPayOrdersMapper.insertSelective(lnPayOrders);

        //记录ln_pay_orders_jnl表
        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_INIT.getCode());
        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
        lnPayOrdersJnl.setCreateTime(new Date());
        lnPayOrdersJnl.setOrderId(lnPayOrders.getId());
        lnPayOrdersJnl.setOrderNo(lnPayOrders.getOrderNo());
        lnPayOrdersJnl.setUserId(null);
        lnPayOrdersJnl.setSysTime(new Date());
        lnPayOrdersJnl.setTransAmount(0d);
        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

        B2GReqMsg_HFBank_BatchChangeCard unbindReq = new B2GReqMsg_HFBank_BatchChangeCard();
        unbindReq.setPartner_trans_time(lnPayOrders.getCreateTime());
        unbindReq.setPartner_trans_date(lnPayOrders.getCreateTime());
        unbindReq.setOrder_no(Util.generateOrderNo4BaoFoo(8));
        unbindReq.setTotal_num(1);
        List<BatchUpdateCardExtDetail> data = new ArrayList<>();
        BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
        detail.setPlatcust(ucUser.getHfUserId());
        detail.setCard_no(ucBankCard.getCardNo());
        detail.setCard_no_old(ucBankCard.getCardNo());
        detail.setDetail_no(lnPayOrders.getOrderNo());
        detail.setMobile(ucBankCard.getMobile());
        detail.setName(ucBankCard.getCardOwner());
        detail.setRemark("用户解绑卡");
        data.add(detail);
        unbindReq.setData(data);
        B2GResMsg_HFBank_BatchChangeCard res = hfbankTransportService.batchChangeCard(unbindReq);
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            if(!org.apache.commons.collections.CollectionUtils.isEmpty(res.getSuccess_data())) {
                logger.info("解绑卡成功：{}",  JSONObject.fromObject(res));
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
                lnPayOrdersJnlMapper.insertSelective(succJnl);

                // uc_bind_card
                UcBankCard ucBankCardUpd = new UcBankCard();
                ucBankCardUpd.setId(ucBankCard.getId());
//                ucBankCardUpd.setStatus(Constants.UC_BIND_CARD_STATUS_UNBINDED);
                ucBankCardUpd.setIsBind(Constants.UC_BIND_CARD_NO);
                ucBankCardUpd.setUpdateTime(new Date());
                // ucBankCardUpd.setUnbindTime(new Date());
                ucBankCardMapper.updateByPrimaryKeySelective(ucBankCardUpd);
            }
            if(org.apache.commons.collections.CollectionUtils.isEmpty(res.getSuccess_data())) {
                logger.info("解绑卡失败：{}", JSONObject.fromObject(res));
                String errorMsg = "";
                if(!org.apache.commons.collections.CollectionUtils.isEmpty(res.getError_data())) {
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
                lnPayOrdersJnlMapper.insertSelective(failjnl);
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败：" + errorMsg);
            }
        } else {
            logger.info("解绑卡失败：{}", JSONObject.fromObject(res));
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
            lnPayOrdersJnlMapper.insertSelective(failjnl);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败");
        }
    }

    /**
     * 解绑卡
     *
     * @param userId 用户编号
     * @param cardNo 卡号
     */
    @Override
    public void unBindCard(Integer userId, String cardNo) {
        logger.info("用户 {} 解绑卡开始，银行卡号：{}", userId, cardNo);

        // 含有处理中、审核中提现记录不得解绑卡
        BsUserTransDetailExample transDetailExample = new BsUserTransDetailExample();
        List<String> types = new ArrayList<>();
        types.add(Constants.Trans_TYPE_WITHDRAW);
        types.add(Constants.Trans_TYPE_DEP_WITHDRAW);
        List<String> transStatuses = new ArrayList<>();
        transStatuses.add(Constants.Trans_STATUS_DEAL);
        transStatuses.add(Constants.Trans_STATUS_CHECK);
        transDetailExample.createCriteria().andUserIdEqualTo(userId).andTransTypeIn(types).andTransStatusIn(transStatuses);
        int dealOrder = bsUserTransDetailMapper.countByExample(transDetailExample);
        if(dealOrder > 0) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "存在处理中、审核中提现记录，不允许解绑卡");
        }

        BsBankCardExample cardExample = new BsBankCardExample();
        cardExample.createCriteria().andUserIdEqualTo(userId).andCardNoEqualTo(cardNo)
        	.andStatusEqualTo(Constants.BANK_CARD_NORMAL);
        List<BsBankCard> bankCards = bsBankCardMapper.selectByExample(cardExample);
        if(CollectionUtils.isEmpty(bankCards)){
            throw new PTMessageException(PTMessageEnum.USER_BINDCARD_IS_ERROR);
        }
        List<String> channels = new ArrayList<>();
        channels.add(Constants.CHANNEL_HFBANK);
        BsPayReceiptExample receiptExample = new BsPayReceiptExample();
        receiptExample.createCriteria().andUserIdEqualTo(userId)
                .andBankCardNoEqualTo(cardNo).andIsBindCardEqualTo(Constants.IS_BIND_BANK_YES)
                .andChannelIn(channels);
        List<BsPayReceipt> receipts = bsPayReceiptMapper.selectByExample(receiptExample);
        if(CollectionUtils.isEmpty(receipts)) {
            throw new PTMessageException(PTMessageEnum.USER_BINDCARD_IS_ERROR);
        }
        BsBankCard card = bankCards.get(0);
//        BsPayReceipt receipt = bsBankCardService.findCardReceipt(userId, card.getCardNo(), Constants.CHANNEL_BAOFOO);
//        if(receipt == null){
//            throw new PTMessageException(PTMessageEnum.USER_BINDCARD_IS_ERROR);
//        }

        BsHfbankUserExtExample userExtExample = new BsHfbankUserExtExample();
        List<String> statuses = new ArrayList<>();
        statuses.add(Constants.HFBANK_USER_EXT_STATUS_OPEN);
        statuses.add(Constants.HFBANK_USER_EXT_STATUS_WAIT_ACTIVATE);
        userExtExample.createCriteria().andUserIdEqualTo(userId).andStatusIn(statuses);
        List<BsHfbankUserExt> exts = bsHfbankUserExtMapper.selectByExample(userExtExample);
        if(CollectionUtils.isEmpty(exts)) {
            throw new PTMessageException(PTMessageEnum.NO_HF_EXT);
        }

        // 校验币港湾、赞分期是否存在相同的身份证
//        LnBindCardExample lnBindCardExample = new LnBindCardExample();
//        lnBindCardExample.createCriteria().andIdCardEqualTo(card.getIdCard()).andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andPartnerCodeEqualTo(PartnerEnum.ZAN.getCode());
//        long bindCardCount = lnBindCardMapper.countByExample(lnBindCardExample);
//        if (bindCardCount > 0) {
//            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "该用户在赞分期绑过卡，不允许解绑卡");
//        }

        BsHfbankUserExt ext = exts.get(0);

        BsPayOrders order = new BsPayOrders();
        order.setOrderNo(Util.generateOrderNo4BaoFoo(8));
        order.setUserId(userId);
        order.setChannel(Constants.CHANNEL_HFBANK);
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setCreateTime(new Date());
        order.setUpdateTime(new Date());
        order.setBankCardNo(cardNo);
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
        order.setTransType(Constants.TRANS_USER_UN_BIND_CARD);
        order.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
        order.setBankName(card.getBankName());
        order.setBankId(card.getBankId());
        order.setMobile(card.getMobile());
        order.setIdCard(card.getIdCard());
        order.setAmount(0d);
        order.setUserName(card.getCardOwner());
        bsPayOrdersMapper.insertSelective(order);

        BsPayOrdersJnl jnl = new BsPayOrdersJnl();
        jnl.setOrderId(order.getId());
        jnl.setOrderNo(order.getOrderNo());
        jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
        jnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
        jnl.setSysTime(new Date());
        jnl.setUserId(order.getUserId());
        jnl.setCreateTime(new Date());
        jnl.setTransAmount(0d);
        bsPayOrdersJnlMapper.insertSelective(jnl);

        B2GReqMsg_HFBank_BatchChangeCard req = new B2GReqMsg_HFBank_BatchChangeCard();
        req.setPartner_trans_time(order.getCreateTime());
        req.setPartner_trans_date(order.getCreateTime());
        req.setOrder_no(Util.generateOrderNo4BaoFoo(8));
        req.setTotal_num(1);
        List<BatchUpdateCardExtDetail> data = new ArrayList<>();
        BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
        detail.setPlatcust(ext.getHfUserId());
        detail.setCard_no(cardNo);
        detail.setCard_no_old(cardNo);
        detail.setDetail_no(order.getOrderNo());
        detail.setMobile(card.getMobile());
        detail.setName(card.getCardOwner());
        detail.setRemark("用户解绑卡");
        data.add(detail);
        req.setData(data);
        B2GResMsg_HFBank_BatchChangeCard res = new B2GResMsg_HFBank_BatchChangeCard();
        try {
        	res = hfbankTransportService.batchChangeCard(req);
        } catch (Exception e) {
            logger.info("恒丰批量换卡操作失败 {}，订单号：{}", e.getMessage(), order.getOrderNo());
        }
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            if(!CollectionUtils.isEmpty(res.getSuccess_data())) {
                logger.info("用户 {} 解绑卡成功：{}", userId, JSONObject.fromObject(res));
                order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                order.setUpdateTime(new Date());
                order.setNote(detail.getDetail_no());
                bsPayOrdersMapper.updateByPrimaryKeySelective(order);

                BsPayOrdersJnl succjnl = new BsPayOrdersJnl();
                succjnl.setOrderId(order.getId());
                succjnl.setOrderNo(order.getOrderNo());
                succjnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
                succjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                succjnl.setTransAmount(0d);
                succjnl.setSysTime(new Date());
                succjnl.setUserId(order.getUserId());
                succjnl.setCreateTime(new Date());
                succjnl.setNote(detail.getDetail_no());
                bsPayOrdersJnlMapper.insertSelective(succjnl);

                BsBankCard bsBankCard = new BsBankCard();
                bsBankCard.setId(card.getId());
                bsBankCard.setStatus(Constants.BANK_CARD_UNBIND);
                bsBankCard.setUnbindTime(new Date());
                bsBankCardService.updateBsBankCard(bsBankCard);
                //修改用户表中is_bind_name与is_bind_bank状态
                BsUser bsUser = new BsUser();
                bsUser.setId(card.getUserId());
                bsUser.setIsBindBank(Constants.IS_BIND_BANK_NO);
                bsUser.setIsBindName(Constants.IS_BIND_NAME_NO);
                bsUserMapper.updateByPrimaryKeySelective(bsUser);
                
        		//uc_bank_card 置为已解绑
                UcBankCardExample unBindExample = new UcBankCardExample();
                unBindExample.createCriteria().andIdCardEqualTo(card.getIdCard())
                        .andCardNoEqualTo(card.getCardNo())
                        .andIsBindEqualTo(Constants.UC_BIND_CARD_YES);
        		UcBankCard ucBankCard = new UcBankCard();
                ucBankCard.setIsBind(Constants.UC_BIND_CARD_NO);
                ucBankCard.setUpdateTime(new Date());
                // ucBankCard.setUnbindTime(new Date());
                // ucBankCard.setNote("理财端用户恒丰解绑");
                ucBankCardMapper.updateByExampleSelective(ucBankCard,unBindExample);
                
                //修改回执表
                BsPayReceipt receipt = new BsPayReceipt();
                receipt.setIsBindCard(Constants.IS_BIND_BANK_NO);
                receipt.setReceiptNo("");
                receipt.setUpdateTime(new Date());
                BsPayReceiptExample updateReceiptExample = new BsPayReceiptExample();
                updateReceiptExample.createCriteria().andUserIdEqualTo(userId)
                        .andBankCardNoEqualTo(cardNo).andIsBindCardEqualTo(Constants.IS_BIND_BANK_YES)
                        .andChannelIn(channels);
                bsPayReceiptMapper.updateByExampleSelective(receipt, updateReceiptExample);

                // 修改恒丰开户用户扩展表状态
                BsHfbankUserExt userExt = new BsHfbankUserExt();
                userExt.setUserId(userId);
                userExt.setUpdateTime(new Date());
                userExt.setStatus(Constants.HFBANK_USER_EXT_STATUS_CANCEL);
                BsHfbankUserExtExample extExample = new BsHfbankUserExtExample();
                extExample.createCriteria().andUserIdEqualTo(userId);
                bsHfbankUserExtMapper.updateByExampleSelective(userExt, extExample);
            }
            if(CollectionUtils.isEmpty(res.getSuccess_data())) {
                logger.info("用户 {} 解绑卡失败：{}", userId, JSONObject.fromObject(res));
                String errorMsg = "";
                if(!CollectionUtils.isEmpty(res.getError_data())) {
                    order.setStatus(Constants.ORDER_STATUS_FAIL);
                    order.setReturnCode(res.getError_data().get(0).getError_no());
                    order.setReturnMsg(res.getError_data().get(0).getError_info());
                    order.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(order);
                    errorMsg = res.getError_data().get(0).getError_info();
                } else {
                    order.setStatus(Constants.ORDER_STATUS_FAIL);
                    order.setReturnCode(res.getResCode());
                    order.setReturnMsg("恒丰返回数据为空");
                    order.setUpdateTime(new Date());
                    bsPayOrdersMapper.updateByPrimaryKeySelective(order);
                    errorMsg = "恒丰返回数据为空";
                }
                BsPayOrdersJnl failjnl = new BsPayOrdersJnl();
                failjnl.setOrderId(order.getId());
                failjnl.setOrderNo(order.getOrderNo());
                failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
                failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
                failjnl.setTransAmount(0d);
                failjnl.setSysTime(new Date());
                failjnl.setUserId(order.getUserId());
                failjnl.setCreateTime(new Date());
                bsPayOrdersJnlMapper.insertSelective(failjnl);
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败：" + errorMsg);
            }
        } else {
            logger.info("用户 {} 解绑卡失败：{}", userId, JSONObject.fromObject(res));
            order.setStatus(Constants.ORDER_STATUS_FAIL);
            order.setReturnCode(res.getResCode());
            order.setReturnMsg(res.getResMsg());
            order.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(order);
            BsPayOrdersJnl failjnl = new BsPayOrdersJnl();
            failjnl.setOrderId(order.getId());
            failjnl.setOrderNo(order.getOrderNo());
            failjnl.setTransAmount(0d);
            failjnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            failjnl.setChannelTransType(Constants.CHANNEL_TRS_UN_BIND_CARD);
            failjnl.setSysTime(new Date());
            failjnl.setUserId(order.getUserId());
            failjnl.setCreateTime(new Date());
            bsPayOrdersJnlMapper.insertSelective(failjnl);
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "解绑失败");
        }
    }

    @Override
    public void batchBindCard(String mobile, String userName, String idCard, String cardNo) {

        BsUser user = userService.findUserByMobile(mobile);
        if(null != user) {
            BsHfbankUserExtExample hfbankUserExtExample = new BsHfbankUserExtExample();
            List<String> statuses = new ArrayList<>();
            statuses.add(Constants.HFBANK_USER_EXT_STATUS_OPEN);
            statuses.add(Constants.HFBANK_USER_EXT_STATUS_WAIT_ACTIVATE);
            hfbankUserExtExample.createCriteria().andUserIdEqualTo(user.getId()).andStatusIn(statuses);
            List<BsHfbankUserExt> extes = bsHfbankUserExtMapper.selectByExample(hfbankUserExtExample);
            if(!CollectionUtils.isEmpty(extes)) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START.getCode(), "已开通过恒丰存管");
            }
            BsBankCardExample example = new BsBankCardExample();
            example.createCriteria().andCardNoEqualTo(cardNo).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
            int a = bsBankCardMapper.countByExample(example);
            if(a > 0) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START.getCode(), "该卡已绑定");
            }
            example.createCriteria().andIdCardEqualTo(idCard).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
            int b = bsBankCardMapper.countByExample(example);
            if(b > 0) {
                throw new PTMessageException(PTMessageEnum.ACTIVITY_NOT_START.getCode(), "该身份证已绑定银行卡");
            }

            BsCardBin bsCardBin = bankCardService.queryBankBin(cardNo);
            BsBank bank = bankCardService.findBankById(bsCardBin.getBankId());
            B2GReqMsg_HFBank_BatchBindCard4Elements req = new B2GReqMsg_HFBank_BatchBindCard4Elements();
            /* 总数量 */
            BsPayOrders order = new BsPayOrders();
            order.setOrderNo(Util.generateOrderNo(user.getId()));
            order.setUserId(user.getId());
            order.setChannel(Constants.CHANNEL_HFBANK);
            order.setStatus(Constants.ORDER_STATUS_CREATE);
            order.setCreateTime(new Date());
            order.setUpdateTime(new Date());
            order.setBankCardNo(cardNo);
            order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_USER);
            order.setTransType(Constants.TRANS_USER_BIND_CARD);
            order.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            order.setMobile(mobile);
            order.setIdCard(idCard);
            order.setAmount(0d);
            order.setUserName(userName);
            bsPayOrdersMapper.insertSelective(order);
            // 3、插入订单流水表，状态为创建-1
            BsPayOrdersJnl jnl = new BsPayOrdersJnl();
            jnl.setOrderId(order.getId());
            jnl.setOrderNo(order.getOrderNo());
            jnl.setTransCode(Constants.ORDER_TRANS_CODE_INIT);
            jnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            jnl.setTransAmount(order.getAmount());
            jnl.setSysTime(new Date());
            jnl.setUserId(user.getId());
            jnl.setCreateTime(new Date());
            bsPayOrdersJnlMapper.insertSelective(jnl);

            BsBankCard bsBankCard = new BsBankCard();
            BsBankCardExample bsBankCardExample = new BsBankCardExample();
            bsBankCardExample.createCriteria().andCardNoEqualTo(order.getBankCardNo()).andUserIdEqualTo(user.getId())
                    .andIdCardEqualTo(order.getIdCard()).andMobileEqualTo(order.getMobile())
                    .andStatusEqualTo(Constants.BANK_CARD_BINDING)
                    .andCardOwnerEqualTo(order.getUserName());
            List<BsBankCard> bsBankCardList = bsBankCardMapper.selectByExample(bsBankCardExample);
            if(CollectionUtils.isEmpty(bsBankCardList)) {
                bsBankCard.setMobile(order.getMobile());
                bsBankCard.setUserId(user.getId());
                bsBankCard.setCardNo(order.getBankCardNo());
                bsBankCard.setCardOwner(order.getUserName());
                bsBankCard.setIdCard(order.getIdCard());
                bsBankCard.setBankId(order.getBankId());
                bsBankCard.setStatus(Constants.DAFY_BINDING_STAUTS_BINDING);
                bsBankCard.setIsFirst(Constants.IS_FIRST_BANK_YES);
                bsBankCard.setBankId(bsCardBin.getBankId());
                bsBankCard.setBankName(bank.getName());
                bsBankCardMapper.insertSelective(bsBankCard);
            } else {
                bsBankCard = bsBankCardList.get(0);
            }

            req.setTotal_num(1);
            List<BatchRegistExtDetail> details = new ArrayList<>();
            BatchRegistExtDetail detail = new BatchRegistExtDetail();
            detail.setDetail_no(order.getOrderNo());
            detail.setName(userName);
            detail.setId_type("1");
            detail.setId_code(idCard);
            detail.setMobile(mobile);
            detail.setEmail(null);
            detail.setSex(null);
            detail.setCus_type(null);
            detail.setBirthday(null);
            detail.setOpen_branch(null);
            detail.setCard_no(cardNo);
            detail.setCard_type("1");
            detail.setPre_mobile(mobile);
            detail.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
            detail.setNotify_url(null);
            detail.setRemark("批量绑卡");
            details.add(detail);
            req.setData(details);
            req.setOrder_no(Util.generateOrderNo4BaoFoo(8));
            req.setPartner_trans_date(new Date());
            req.setPartner_trans_time(new Date());
            logger.info("批量绑卡请求：{}", JSONObject.fromObject(req));
            B2GResMsg_HFBank_BatchBindCard4Elements res = hfbankTransportService.batchBindCard4Elements(req);
            logger.info("批量绑卡 {} 总条数：{}，其中成功条数：{}", res.getOrder_no(), res.getTotal_num(), res.getSuccess_num());
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                if(null != res) {
                    if(!CollectionUtils.isEmpty(res.getSuccess_data())) {
                        bindSuccess(res.getSuccess_data(), order.getOrderNo());
                    }
                    if(!CollectionUtils.isEmpty(res.getError_data())) {
                        bindError(res.getError_data(), order.getOrderNo());
                        throw new PTMessageException(PTMessageEnum.BIND_BANK_CARD_ERROR, "批量绑卡失败");
                    }
                }
            } else {
                throw new PTMessageException(PTMessageEnum.BIND_BANK_CARD_ERROR, "批量绑卡失败");
            }
        } else {
            throw new PTMessageException(PTMessageEnum.BIND_BANK_CARD_ERROR, "该手机号对应用户不存在");
        }
    }

    @Override
    public void yunUnBindCard(Integer lnUserId) {
        logger.info("云贷解绑卡开始，请求信息：{}", lnUserId);
        //查询绑卡信息是否存在
        LnBindCardExample example = new LnBindCardExample();
        example.createCriteria().andLnUserIdEqualTo(lnUserId)
                .andStatusEqualTo(BaoFooEnum.BIND_SUCCESS.getCode()).andIsFirstEqualTo(Constants.IS_FIRST_BANK_YES);
        List<LnBindCard> list = lnBindCardMapper.selectByExample(example);

        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
            // 校验币港湾、赞分期是否存在相同的身份证
            BsBankCardExample bankCardExample = new BsBankCardExample();
            bankCardExample.createCriteria().andIdCardEqualTo(list.get(0).getIdCard())
                    .andStatusEqualTo(Constants.BANK_CARD_NORMAL);
            List<BsBankCard> bankCards = bsBankCardMapper.selectByExample(bankCardExample);
            if(!org.springframework.util.CollectionUtils.isEmpty(bankCards)){
                throw new PTMessageException(PTMessageEnum.TRANS_ERROR.getCode(), "该用户在币港湾绑过卡，不允许解绑卡");
            }

            LnUser user = lnUserMapper.selectByPrimaryKey(list.get(0).getLnUserId());
            B2GReqMsg_HFBank_BatchChangeCard unBindCard = new B2GReqMsg_HFBank_BatchChangeCard();
            unBindCard.setPartner_trans_time(new Date());
            unBindCard.setPartner_trans_date(new Date());
            unBindCard.setOrder_no(Util.generateUserOrderNo4Pay19(list.get(0).getLnUserId()));
            List<BatchUpdateCardExtDetail> data = new ArrayList<>();
            unBindCard.setTotal_num(list.size());
            for (LnBindCard card: list) {
                //记录ln_pay_orders
                LnPayOrders lnPayOrders = new LnPayOrders();
                lnPayOrders.setCreateTime(new Date());
                lnPayOrders.setAccountType(1);
                lnPayOrders.setBankCardNo(card.getBankCard());
                lnPayOrders.setBankName(card.getBankName());
                lnPayOrders.setAmount(0d);
                lnPayOrders.setChannel(Constants.CHANNEL_HFBANK);
                lnPayOrders.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
                lnPayOrders.setLnUserId(user.getId());
                lnPayOrders.setMobile(card.getMobile());
                lnPayOrders.setIdCard(card.getIdCard());
                lnPayOrders.setMoneyType(0);
                lnPayOrders.setOrderNo(Util.generateOrderNo4BaoFoo(8));
                lnPayOrders.setPartnerCode(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI_SELF);
                lnPayOrders.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                lnPayOrders.setTransType(LoanStatus.TRANS_TYPE_UN_BIND_CARD.getCode());
                lnPayOrders.setUpdateTime(new Date());
                lnPayOrders.setUserName(card.getUserName());
                lnPayOrdersMapper.insertSelective(lnPayOrders);

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
                lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
                detail.setPlatcust(user.getHfUserId());
                detail.setCard_no(card.getBankCard());
                detail.setCard_no_old(card.getBankCard());
                detail.setDetail_no(lnPayOrders.getOrderNo());
                detail.setMobile(card.getMobile());
                detail.setName(card.getUserName());
                detail.setRemark("云贷用户解绑卡");
                data.add(detail);
            }
            unBindCard.setData(data);
            B2GResMsg_HFBank_BatchChangeCard res = hfbankTransportService.batchChangeCard(unBindCard);
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
                if(!org.springframework.util.CollectionUtils.isEmpty(res.getSuccess_data())) {
                    logger.info("云贷解绑卡成功信息：{}", JSONArray.fromObject(res.getSuccess_data()));
                    for (BatchUpdateCardExtSuccess success: res.getSuccess_data()) {
                        LnPayOrdersExample successExample = new LnPayOrdersExample();
                        successExample.createCriteria().andOrderNoEqualTo(success.getDetail_no());
                        List<LnPayOrders> orders = lnPayOrdersMapper.selectByExample(successExample);
                        LnPayOrders order = new LnPayOrders();
                        order.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_SUCCESS.getCode()));
                        order.setUpdateTime(new Date());
//                        order.setReturnCode(success.getDetail_no());
                        lnPayOrdersMapper.updateByExampleSelective(order, successExample);
                        LnPayOrders successOrder = lnPayOrdersMapper.selectByExample(successExample).get(0);

                        //记录ln_pay_orders_jnl表
                        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
                        lnPayOrdersJnl.setCreateTime(new Date());
                        lnPayOrdersJnl.setOrderId(successOrder.getId());
                        lnPayOrdersJnl.setOrderNo(successOrder.getOrderNo());
                        lnPayOrdersJnl.setUserId(successOrder.getLnUserId());
                        lnPayOrdersJnl.setSysTime(new Date());
                        lnPayOrdersJnl.setTransAmount(0d);
                        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);

                        LnBindCardExample cardExample = new LnBindCardExample();
                        cardExample.createCriteria().andLnUserIdEqualTo(orders.get(0).getLnUserId()).andBankCardEqualTo(orders.get(0).getBankCardNo())
                                .andIdCardEqualTo(orders.get(0).getIdCard()).andUserNameEqualTo(orders.get(0).getUserName()).andMobileEqualTo(orders.get(0).getMobile());
                        LnBindCard temp = new LnBindCard();
                        temp.setUpdateTime(new Date());
                        temp.setIsFirst(Constants.IS_FIRST_BANK_NO);
                        lnBindCardMapper.updateByExampleSelective(temp, cardExample);
                    }
                }
                if(!org.springframework.util.CollectionUtils.isEmpty(res.getError_data())) {
                    logger.info("云贷解绑卡失败信息：{}", JSONArray.fromObject(res.getError_data()));
                    for (BatchUpdateCardExtError error: res.getError_data()) {
                        LnPayOrdersExample errorExample = new LnPayOrdersExample();
                        errorExample.createCriteria().andOrderNoEqualTo(error.getDetail_no());
                        LnPayOrders order = new LnPayOrders();
                        order.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                        order.setUpdateTime(new Date());
                        order.setReturnCode(res.getResCode());
                        order.setReturnMsg(res.getResMsg());
                        lnPayOrdersMapper.updateByExampleSelective(order, errorExample);

                        LnPayOrders errorOrder = lnPayOrdersMapper.selectByExample(errorExample).get(0);
                        //记录ln_pay_orders_jnl表
                        LnPayOrdersJnl lnPayOrdersJnl = new LnPayOrdersJnl();
                        lnPayOrdersJnl.setTransCode(LoanStatus.ORDERS_JNL_STATUS_SUCCESS.getCode());
                        lnPayOrdersJnl.setChannelTransType(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode());
                        lnPayOrdersJnl.setCreateTime(new Date());
                        lnPayOrdersJnl.setOrderId(errorOrder.getId());
                        lnPayOrdersJnl.setOrderNo(errorOrder.getOrderNo());
                        lnPayOrdersJnl.setUserId(errorOrder.getLnUserId());
                        lnPayOrdersJnl.setSysTime(new Date());
                        lnPayOrdersJnl.setTransAmount(0d);
                        lnPayOrdersJnlMapper.insertSelective(lnPayOrdersJnl);
                    }
                    throw new PTMessageException(PTMessageEnum.BAOFOO_UNBIND_CARD_FAIL, res.getResMsg());
                }
            } else {
                logger.info("云贷解绑卡失败，错误信息：{}", JSONObject.fromObject(res));
                LnPayOrdersExample errorExample = new LnPayOrdersExample();
                errorExample.createCriteria().andLnUserIdEqualTo(user.getId())
                        .andChannelTransTypeEqualTo(LoanStatus.CHANNEL_TRANS_TYPE_UN_BIND_CARD.getCode())
                        .andStatusEqualTo(Integer.parseInt(LoanStatus.ORDERS_STATUS_INIT.getCode()));
                LnPayOrders order = new LnPayOrders();
                order.setStatus(Integer.parseInt(LoanStatus.ORDERS_STATUS_FAIL.getCode()));
                order.setUpdateTime(new Date());
                order.setReturnCode(res.getResCode());
                order.setReturnMsg(res.getResMsg());
                lnPayOrdersMapper.updateByExampleSelective(order, errorExample);

                throw new PTMessageException(PTMessageEnum.BAOFOO_UNBIND_CARD_FAIL, res.getResMsg());
            }

        } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
    }

    private void bindSuccess(List<BatchRegistExtSuccess> successes, String orderNo) {
        for (BatchRegistExtSuccess success: successes) {

            BsPayOrders successOrder = new BsPayOrders();
            successOrder.setUpdateTime(new Date());
            successOrder.setStatus(Constants.ORDER_STATUS_SUCCESS);
            successOrder.setNote(success.getPlatcust());
            BsPayOrdersExample ordersExample = new BsPayOrdersExample();
            ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
            bsPayOrdersMapper.updateByExampleSelective(successOrder, ordersExample);
            List<BsPayOrders> orderses =  bsPayOrdersMapper.selectByExample(ordersExample);
            BsPayOrders order = orderses.get(0);

            // 2、新增订单流水表。
            BsPayOrdersJnl succJnl = new BsPayOrdersJnl();
            succJnl.setOrderId(order.getId());
            succJnl.setOrderNo(successOrder.getOrderNo());
            succJnl.setTransCode(Constants.ORDER_TRANS_CODE_SUCCESS);
            succJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            succJnl.setTransAmount(order.getAmount());
            succJnl.setSysTime(new Date());
            succJnl.setUserId(order.getUserId());
            succJnl.setCreateTime(new Date());
            succJnl.setNote(success.getPlatcust());
            bsPayOrdersJnlMapper.insertSelective(succJnl);

            BsBankCardExample bankCardExample = new BsBankCardExample();
            bankCardExample.createCriteria().andUserIdEqualTo(order.getUserId()).andStatusEqualTo(Constants.BANK_CARD_BINDING);
            List<BsBankCard> cards = bsBankCardMapper.selectByExample(bankCardExample);
            BsBankCard card = cards.get(0);

            BsBankCard bsBankCard = new BsBankCard();
            bsBankCard.setId(card.getId());
            bsBankCard.setStatus(Constants.BANK_CARD_NORMAL);
            bsBankCard.setBindTime(new Date());
            bsBankCardMapper.updateByPrimaryKeySelective(bsBankCard);

            BsPayReceiptExample payReceiptExample = new BsPayReceiptExample();
            payReceiptExample.createCriteria().andUserIdEqualTo(card.getUserId()).andChannelEqualTo(Constants.CHANNEL_HFBANK);
            List<BsPayReceipt> receipts = bsPayReceiptMapper.selectByExample(payReceiptExample);
            if(CollectionUtils.isEmpty(receipts)) {
                BsPayReceipt bsPayReceipt = new BsPayReceipt();
                bsPayReceipt.setBankCardNo(card.getCardNo());
                bsPayReceipt.setUserId(card.getUserId());
                bsPayReceipt.setUserName(card.getCardOwner());
                bsPayReceipt.setIdCard(card.getIdCard());
                bsPayReceipt.setMobile(card.getMobile());
                bsPayReceipt.setChannel(Constants.CHANNEL_HFBANK);
                bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_YES);
                bsPayReceipt.setCreateTime(new Date());
                bsPayReceipt.setUpdateTime(new Date());
                bsPayReceiptMapper.insertSelective(bsPayReceipt);
            } else {
                BsPayReceipt payReceipt = new BsPayReceipt();
                payReceipt.setId(receipts.get(0).getId());
                payReceipt.setUserId(card.getUserId());
                payReceipt.setIsBindCard(Constants.BAOFOO_BIND_YES);
                payReceipt.setUpdateTime(new Date());
                bsPayReceiptMapper.updateByPrimaryKeySelective(payReceipt);
            }

            BsHfbankUserExt bsHfbankUserExt = new BsHfbankUserExt();
            bsHfbankUserExt.setUserId(card.getUserId());
            bsHfbankUserExt.setHfUserId(success.getPlatcust());
            bsHfbankUserExt.setHfRegistTime(new Date());
            bsHfbankUserExt.setHfBindCardTime(new Date());
            bsHfbankUserExt.setStatus(Constants.HFBANK_USER_EXT_STATUS_OPEN);
            bsHfbankUserExt.setNote(null);
            bsHfbankUserExt.setCreateTime(new Date());
            bsHfbankUserExt.setUpdateTime(new Date());
            bsHfbankUserExtMapper.insertSelective(bsHfbankUserExt);
            BsHfbankUserExtExample hfbankUserExtExample = new BsHfbankUserExtExample();
            hfbankUserExtExample.createCriteria().andUserIdEqualTo(card.getUserId());
            List<BsHfbankUserExt> extList = bsHfbankUserExtMapper.selectByExample(hfbankUserExtExample);
            if(CollectionUtils.isEmpty(extList)) {
                bsHfbankUserExtMapper.insertSelective(bsHfbankUserExt);
            } else {
                bsHfbankUserExtMapper.updateByExampleSelective(bsHfbankUserExt, hfbankUserExtExample);
            }

            // 3. 用户绑卡置为未绑卡
            BsUser user = new BsUser();
            user.setId(card.getUserId());
            user.setIsBindBank(Constants.IS_BIND_BANK_YES);
            user.setIsBindName(Constants.IS_BIND_NAME_YES);
            bsUserMapper.updateByPrimaryKeySelective(user);
        }
    }

    private void bindError(List<BatchRegistExtError> errors, String orderNo) {
        for (BatchRegistExtError error: errors) {
            // 1. 银行卡信息置为失败
            // 2. 支付签约回执表置为失败
            // 3. 用户绑卡置为未绑卡

            BsPayOrders failOrder = new BsPayOrders();
            failOrder.setUpdateTime(new Date());
            failOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            failOrder.setReturnMsg(error.getError_info());
            failOrder.setReturnCode(error.getError_no());
            BsPayOrdersExample ordersExample = new BsPayOrdersExample();
            ordersExample.createCriteria().andOrderNoEqualTo(orderNo);
            bsPayOrdersMapper.updateByExampleSelective(failOrder, ordersExample);
            List<BsPayOrders> orderses =  bsPayOrdersMapper.selectByExample(ordersExample);
            BsPayOrders order = orderses.get(0);

            // 2、新增订单流水表。
            BsPayOrdersJnl succJnl = new BsPayOrdersJnl();
            succJnl.setOrderId(order.getId());
            succJnl.setOrderNo(failOrder.getOrderNo());
            succJnl.setTransCode(Constants.ORDER_TRANS_CODE_FAIL);
            succJnl.setChannelTransType(Constants.CHANNEL_TRS_BIND_CARD);
            succJnl.setTransAmount(order.getAmount());
            succJnl.setSysTime(new Date());
            succJnl.setUserId(order.getUserId());
            succJnl.setCreateTime(new Date());
            succJnl.setReturnMsg(error.getError_info());
            succJnl.setReturnCode(error.getError_no());
            bsPayOrdersJnlMapper.insertSelective(succJnl);

            BsBankCardExample bankCardExample = new BsBankCardExample();
            bankCardExample.createCriteria().andUserIdEqualTo(order.getUserId()).andStatusEqualTo(Constants.BANK_CARD_BINDING);
            List<BsBankCard> cards = bsBankCardMapper.selectByExample(bankCardExample);
            BsBankCard card = cards.get(0);
            // 1. 银行卡信息置为失败
            BsBankCard bsBankCard = new BsBankCard();
            bsBankCard.setId(card.getId());
            bsBankCard.setStatus(Constants.BANK_CARD_BINDFAIL);
//            bsBankCard.setBindTime(new Date());
            bsBankCardMapper.updateByPrimaryKeySelective(bsBankCard);

            // 2. 支付签约回执表置为失败
            BsPayReceiptExample payReceiptExample = new BsPayReceiptExample();
            payReceiptExample.createCriteria().andUserIdEqualTo(card.getUserId()).andChannelEqualTo(Constants.CHANNEL_HFBANK);
            List<BsPayReceipt> receipts = bsPayReceiptMapper.selectByExample(payReceiptExample);
            if(CollectionUtils.isEmpty(receipts)) {
                BsPayReceipt bsPayReceipt = new BsPayReceipt();
                bsPayReceipt.setBankCardNo(card.getCardNo());
                bsPayReceipt.setUserId(card.getUserId());
                bsPayReceipt.setUserName(card.getCardOwner());
                bsPayReceipt.setIdCard(card.getIdCard());
                bsPayReceipt.setMobile(card.getMobile());
                bsPayReceipt.setChannel(Constants.CHANNEL_HFBANK);
                bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
                bsPayReceipt.setCreateTime(new Date());
                bsPayReceipt.setUpdateTime(new Date());
                bsPayReceiptMapper.insertSelective(bsPayReceipt);
            } else {
                BsPayReceipt payReceipt = new BsPayReceipt();
                payReceipt.setId(receipts.get(0).getId());
                payReceipt.setUserId(card.getUserId());
                payReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
                payReceipt.setUpdateTime(new Date());
                bsPayReceiptMapper.updateByPrimaryKeySelective(payReceipt);
            }

            // 3. 用户绑卡置为未绑卡
            BsUser user = new BsUser();
            user.setId(card.getUserId());
            user.setIsBindBank(Constants.IS_BIND_BANK_NO);
            user.setIsBindName(Constants.IS_BIND_NAME_NO);
            bsUserMapper.updateByPrimaryKeySelective(user);
        }
    }

	@Override
	@MethodRole("APP")
	public UnbindCheckResVO unBindCheck(Integer userId, Integer bankCardId) {
		/**
		 * 0、校验用户数据和绑卡数据
		 * 校验用户是否存在则checkResult字段返回NO_USER，不存在；
		 * 校验bankcard表数据是否是在绑定中，不存在绑定中的卡，则checkResult字段返回已解绑（UNBIND）；
		 * 1、校验年龄
		 * 查询用户表（bs_user）获取用户身份证，校验年龄是否大于69周岁，大于69周岁则checkResult字段返回AGE_OUT_RANGE，不进行下一步；小于等于69周岁，则进行第3步；
		 * 2、有审核验证正在进行
		 * 查询用户人脸核身验证表该用户审核状态为待审核数据，若条数大于0，则checkResult字段返回VERIFY_PROGRESS，不进行下一步；
		 * 3、校验24小时内次数
		 * 查询小于当前时间24小时内，用户人脸核身验证表中数据条数，若条数大于等于3次，则checkResult字段返回TOO_MANY_FAILURES，不进行下一步；
		 * 4、进行中的提现和充值校验
		 * 查询bs_user_trans_detail表该用户是否存在状态为1-处理中、4-审核中数据，交易类型（trans_type）为WITHDRAW、DEP_WITHDRAW、USER_BONUS_WITHDRAW、WITHDRAW_FEE的数据；存在则checkResult字段返回WITHDRAW_PROGRESS，不进行下一步；
		 * 查询订单表（bs_pay_orders），该用户，类型（TransType）为提现（BALANCE_WITHDRAW）或奖励金提现（BONUS_WITHDRAW），且状态为处理中（5）的数据，存在则checkResult字段返回WITHDRAW_PROGRESS，不进行下一步；
		 * 查询订单表（bs_pay_orders），该用户，类型（TransType）为充值（TOP_UP），且状态为处理中（5）的数据，存在则checkResult字段返回TOP_UP_PROGRESS，不进行下一步；
		 * 5、所有校验通过，checkResult字段返回PASS；返回用户姓名和身份证
		 * */	
		UnbindCheckResVO unbindCheckResVO = new UnbindCheckResVO();
		unbindCheckResVO.setIdCard("");
		unbindCheckResVO.setUserName("");
		
		BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
		//0、校验用户数据和绑卡数据
		if(bsUser == null || bsUser.getStatus() != 1){
			unbindCheckResVO.setCheckResult(UnBindCheckResultEnum.NO_USER.getCode());
			return unbindCheckResVO;
		}
		BsBankCardExample example = new BsBankCardExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsFirstEqualTo(Constants.IS_FIRST_BANK_YES)
                .andStatusEqualTo(Constants.BANK_CARD_NORMAL).andIdEqualTo(bankCardId);
        List<BsBankCard> bankCards = bsBankCardMapper.selectByExample(example);
		if(org.apache.commons.collections.CollectionUtils.isEmpty(bankCards)){
			unbindCheckResVO.setCheckResult(UnBindCheckResultEnum.NO_BANK_CARD.getCode());
			return unbindCheckResVO;
		}

		//1、校验年龄
		if(IdcardUtils.isOldAge(bsUser.getIdCard(), Constants.OLD_MAN_AGE_LIMIT)){
			unbindCheckResVO.setCheckResult(UnBindCheckResultEnum.AGE_OUT_RANGE.getCode());
			return unbindCheckResVO;
		}

		//2、有审核验证正在进行
		BsUserPoliceVerifyExample processExample = new BsUserPoliceVerifyExample();
		processExample.createCriteria().andUserIdEqualTo(userId)
		.andCheckStatusEqualTo(PoliceVerifyCheckStatusEnum.UNCHECKED.getCode());
		List<BsUserPoliceVerify> policeVerifyProcessExampleList = bsUserPoliceVerifyMapper.selectByExample(processExample);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(policeVerifyProcessExampleList)){
			unbindCheckResVO.setCheckResult(UnBindCheckResultEnum.VERIFY_PROGRESS.getCode());
			return unbindCheckResVO;
		}
		
		//3、校验24小时内次数
		Date now = new Date();
		Date hours24before = DateUtil.addSeconds(now, -60*60*24);
		BsUserPoliceVerifyExample userPoliceVerifyExample = new BsUserPoliceVerifyExample();
		userPoliceVerifyExample.createCriteria().andUserIdEqualTo(userId)
		.andCreateTimeGreaterThanOrEqualTo(hours24before)
		.andCreateTimeLessThanOrEqualTo(now);
		List<BsUserPoliceVerify> userPoliceVerifyList = bsUserPoliceVerifyMapper.selectByExample(userPoliceVerifyExample);
		if(org.apache.commons.collections.CollectionUtils.isNotEmpty(userPoliceVerifyList)
				&& userPoliceVerifyList.size()>= 3){
			unbindCheckResVO.setCheckResult(UnBindCheckResultEnum.TOO_MANY_FAILURES.getCode());
			return unbindCheckResVO;
		}

		//4、进行中的提现和充值校验
		UnBindCheckResultEnum transtypeCheck = bsBankCardService.userWithdrawTopUpCheck(userId);
		if(transtypeCheck != null){
			unbindCheckResVO.setCheckResult(transtypeCheck.getCode());
			return unbindCheckResVO;
		}

		
		
		unbindCheckResVO.setCheckResult(UnBindCheckResultEnum.CHECK_PASS.getCode());
		unbindCheckResVO.setIdCard(bsUser.getIdCard());
		unbindCheckResVO.setUserName(bsUser.getUserName());
		
		return unbindCheckResVO;
	}

    @Override
    @MethodRole("APP")
    public void unbindApplyPoliceVerify(ReqMsg_Bank_UnbindApplyPoliceVerify req, ResMsg_Bank_UnbindApplyPoliceVerify res) {
        logger.info("解绑卡申请——人脸核身验证接口开始，请求信息：用户编号：{}，解绑卡ID：{}，人脸核身验证结果：{}，分数：{}",
                req.getUserId(), req.getBankId(), req.getVerifyResult(), req.getScore());
        /**
         * 0、校验用户数据
         * 校验用户是否存在
         * 1、查询小于当前时间24小时内，用户人脸核身验证次数(bs_user_police_verify表中数据条数)
         * residueDegree（剩余可验证次数） = 3 - 24小时内已验证次数，如果residueDegree值小于0，取值0
         * 2、新增bs_user_police_verify表人脸核身记录
         * 3、人脸识别分数>= 80分，进入待审核状态，响应APP分数审核通过
         *   人脸识别分数< 80分，进入未通过状态，响应APP分数审核失败，VerifyResult 记录fail
         *   身份证验证失败，进入未通过状态，verifyResult 记录fail
         */
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(req.getUserId());
        // 0、校验用户数据和绑卡数据
        if (bsUser == null) {
            throw new PTMessageException(PTMessageEnum.USER_NO_EXIT);
        }

        // 1、查询24小时内人脸核身次数
        Date now = new Date();
        Date hours24before = DateUtil.addHours(now, -24);
        BsUserPoliceVerifyExample userPoliceVerifyExample = new BsUserPoliceVerifyExample();
        userPoliceVerifyExample.createCriteria()
                .andUserIdEqualTo(req.getUserId())
                .andCreateTimeGreaterThanOrEqualTo(hours24before)
                .andCreateTimeLessThanOrEqualTo(now);
        int count = bsUserPoliceVerifyMapper.countByExample(userPoliceVerifyExample);
        // 剩余可验证次数
        if (count >= Constants.POLICE_VERIFY_UNBIND_APPLY_COUNT) {
            res.setResidueDegree(0);
        } else {
            res.setResidueDegree(Constants.POLICE_VERIFY_UNBIND_APPLY_COUNT - count - 1);
        }

        // 2、新增bs_user_police_verify表人脸核身记录
        String serialNo = Util.generateJnlNo(req.getUserId());
        BsUserPoliceVerify userPoliceVerify = new BsUserPoliceVerify();
        userPoliceVerify.setUserId(req.getUserId());
        userPoliceVerify.setSerialNo(serialNo);
        userPoliceVerify.setBusinessType(PoliceVerifyBusinessTypeEnum.UNBIND_BANK_CARD.getCode());
        userPoliceVerify.setBusinessId(req.getBankId());
        userPoliceVerify.setVerifyResult(req.getVerifyResult());
        userPoliceVerify.setScore(req.getScore());
        userPoliceVerify.setNote(req.getNote());
        if (Constants.POLICE_VERIFY_VERIFYRESULT_SUCC.equals(req.getVerifyResult())) {
            if (req.getScore() != null && req.getScore().compareTo(80) >= 0) {
                // 人脸识别分数>= 80分，进入待审核状态，响应APP分数审核通过
                res.setScoreVerifyResult(true);
                userPoliceVerify.setCheckStatus(PoliceVerifyCheckStatusEnum.UNCHECKED.getCode());
            } else {
                // 人脸识别分数< 80分，进入未通过状态，响应APP分数审核失败
                res.setScoreVerifyResult(false);
                userPoliceVerify.setVerifyResult(Constants.POLICE_VERIFY_VERIFYRESULT_FAIL);
                userPoliceVerify.setCheckStatus(PoliceVerifyCheckStatusEnum.REFUSE.getCode());
                userPoliceVerify.setCheckDesc("人脸识别分数低于80分");
            }
        } else {
            res.setScoreVerifyResult(false);
            userPoliceVerify.setVerifyResult(Constants.POLICE_VERIFY_VERIFYRESULT_FAIL);
            userPoliceVerify.setCheckStatus(PoliceVerifyCheckStatusEnum.REFUSE.getCode());
            userPoliceVerify.setCheckDesc("百度AI人脸识别失败");
        }
        if (StringUtils.isNotBlank(req.getIdCardVerifyResult()) && !Boolean.valueOf(req.getIdCardVerifyResult())) {
            userPoliceVerify.setVerifyResult(Constants.POLICE_VERIFY_VERIFYRESULT_FAIL);
            userPoliceVerify.setCheckStatus(PoliceVerifyCheckStatusEnum.REFUSE.getCode());
            userPoliceVerify.setCheckDesc("身份证验证失败");
        }

        userPoliceVerify.setCreateTime(new Date());
        userPoliceVerify.setUpdateTime(new Date());
        bsUserPoliceVerifyMapper.insertSelective(userPoliceVerify);
        res.setSerialNo(userPoliceVerify.getSerialNo());

        logger.info("解绑卡申请——人脸核身验证接口结束，响应信息：剩余可验证次数：{}，唯一流水号：{}", res.getResidueDegree(), res.getSerialNo());
    }

    @Override
    @MethodRole("APP")
    public void uploadPicPoliceVerify(ReqMsg_Bank_UploadPicPoliceVerify req, ResMsg_Bank_UploadPicPoliceVerify res) {
        logger.info("人脸核身验证上传检测图片接口开始，请求信息：用户编号{}，唯一流水号{}", req.getUserId(), req.getSerialNo());
        /**
         * 查询人脸核身验证表记录后，更新上传检测图片
         * 图片上传,至少保证有一个图片上传成功，如果所有图片上传失败,记录告警流水
         * 可能存在必要上传的图片上传到gateway服务端，但图片文件存储失败的情况，响应false，APP端重新上传一次,记录告警流水
         */
        BsUserPoliceVerifyExample userPoliceVerifyExample = new BsUserPoliceVerifyExample();
        userPoliceVerifyExample.createCriteria().andUserIdEqualTo(req.getUserId()).andSerialNoEqualTo(req.getSerialNo());
        List<BsUserPoliceVerify> userPoliceVerifyList = bsUserPoliceVerifyMapper.selectByExample(userPoliceVerifyExample);
        if (org.apache.commons.collections.CollectionUtils.isNotEmpty(userPoliceVerifyList)) {
            BsUserPoliceVerify userPoliceVerify = userPoliceVerifyList.get(0);
            BsUserPoliceVerify record = new BsUserPoliceVerify();
            record.setId(userPoliceVerify.getId());

            boolean uploadPicRequire = true; // 标识必须上传的图片都上传成功
            boolean uploadFlag = false; // 上传图片标识

            if (StringUtils.isNotBlank(req.getIdCardFrontPic())) {
                record.setIdCardFrontPic(req.getIdCardFrontPic());
                uploadFlag = true;
            } else {
                uploadPicRequire = false;
            }
            if (StringUtils.isNotBlank(req.getIdCardBackPic())) {
                record.setIdCardBackPic(req.getIdCardBackPic());
                uploadFlag = true;
            } else {
                uploadPicRequire = false;
            }
            if (StringUtils.isNotBlank(req.getLivenessPicBlink())) {
                record.setLivenessPicBlink(req.getLivenessPicBlink());
                uploadFlag = true;
            }else {
                uploadPicRequire = false;
            }
            if (StringUtils.isNotBlank(req.getLivenessPicDropHead())) {
                record.setLivenessPicDropHead(req.getLivenessPicDropHead());
                uploadFlag = true;
            }
            if (StringUtils.isNotBlank(req.getLivenessPicLeftHead())) {
                record.setLivenessPicLeftHead(req.getLivenessPicLeftHead());
                uploadFlag = true;
            }
            if (StringUtils.isNotBlank(req.getLivenessPicRaiseHead())) {
                record.setLivenessPicRaiseHead(req.getLivenessPicRaiseHead());
                uploadFlag = true;
            }
            if (StringUtils.isNotBlank(req.getLivenessPicRightHead())) {
                record.setLivenessPicRightHead(req.getLivenessPicRightHead());
                uploadFlag = true;
            }
            if (StringUtils.isNotBlank(req.getLivenessPicSay())) {
                record.setLivenessPicSay(req.getLivenessPicSay());
                uploadFlag = true;
            } else {
                uploadPicRequire = false;
            }
            if (StringUtils.isNotBlank(req.getLivenessPicShakeHead())) {
                record.setLivenessPicShakeHead(req.getLivenessPicShakeHead());
                uploadFlag = true;
            }else {
                uploadPicRequire = false;
            }
            if (uploadFlag) {
                // 有上传成功的图片，更新bs_user_police_verify表
                bsUserPoliceVerifyMapper.updateByPrimaryKeySelective(record);
            }
            // 可能存在必要上传的图片上传到gateway服务端，但图片文件存储失败的情况，响应false，APP端重新上传一次,记录告警流水
            if (!uploadPicRequire) {
                logger.info("必要图片上传到服务端，图片文件存储失败，人脸核身流水号："+req.getSerialNo());
                specialJnlService.warn4FailNoSMS(0.0d,"必要图片上传到服务端，图片文件存储失败，人脸核身流水号："+req.getSerialNo(), req.getSerialNo(),"【人脸核身解绑卡】");
            }
            res.setUploadResult(uploadPicRequire);
        } else {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }

        logger.info("人脸核身验证上传检测图片接口结束，响应信息：上传结果{}", res.getUploadResult());
    }
}
