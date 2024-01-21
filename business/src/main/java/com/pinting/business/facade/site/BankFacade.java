package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.service.site.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.Bs19payBank;
import com.pinting.business.model.BsBank;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsBankCardExample;
import com.pinting.business.model.BsCardBin;
import com.pinting.business.model.BsPCA;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserChannel;
import com.pinting.business.model.DafyBindBankExt;
import com.pinting.business.model.DafyUserExt;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.model.vo.Pay19BankVO;
import com.pinting.business.service.manage.BsSpecialJnlService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BasicInformation;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BasicInformation;
import com.pinting.gateway.in.service.DafyUserService;
import com.pinting.gateway.in.util.DafyInConstant;
import com.pinting.gateway.out.service.DafyTransportService;

/**
 * 
 * @Project: business
 * @Title: BankFacade.java
 * @author Huang MengJian
 * @date 2015-2-11 下午6:43:08
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Bank")
public class BankFacade {

    @Autowired
    private BankCardService      bankCardService;
    @Autowired
    private ProvinceService      provinceService;
    @Autowired
    private CardBinService       cardBinService;
    @Autowired
    private UserService          userService;
    @Autowired
    private DafyUserService      dafyUserService;
    @Autowired
    private DafyTransportService dafyTransportService;
    @Autowired
    private BsSpecialJnlService  bsSpecialJnlService;
    @Autowired
    private SMSService           smsService;
    @Autowired
    private UserTransDetailService userTransDetailService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private UserChannelService userChannelService;
    @Autowired
    private SpecialJnlService  specialJnlService;
    @Autowired
    private AssetsService assetsService;
    @Autowired
    private SubAccountService subAccountService;

    public void addInfo(ReqMsg_Bank_AddInfo req, ResMsg_Bank_AddInfo res) {

        /**
         * 1.判断当前是否是绑定中，如果是处于绑定中，打开绑定银行卡页面失败，否则执行2,3,4过程 
         * 2.查询当前可用的银行。
         * 3.查询所有的省份。
         * 4.判断是否已经实名认证，如果确实已经实名认证，姓名，身份证号回显
         */

        DafyUserExt dafyUser = dafyUserService.findDafyUserByUserId(req.getUserId());
        if (dafyUser != null && dafyUser.getStatus() == Constants.DAFY_BINDING_STAUTS_BINDING) { // 正处于绑定中，打开绑定银行卡页面失败
            throw new PTMessageException(PTMessageEnum.DAFY_BANKCARD_IS_BINDING);
        }
        ArrayList<BsBank> bankList = bankCardService.findBankByStatus(Constants.BANK_STATUS_NORMAL);

        res.setBankList(BeanUtils.classToArrayList(bankList));

        List<BsPCA> pcaList = provinceService.findProvinces();

        res.setProvinceList(BeanUtils.classToArrayList(pcaList));

        List<BsPCA> cityList = provinceService.findPCAByParentId(pcaList.get(0).getId());

        res.setCityList(BeanUtils.classToArrayList(cityList));

        if (req.getId() != null) {

            BsBankCard bankCard = bankCardService.findBankCardById(req.getId());
            System.out.println(1);
            if (bankCard == null || bankCard.getUserId().intValue() != req.getUserId().intValue()) {
                return;
            }

            res.setSubBranchName(bankCard.getSubBranchName());
            res.setCardNo(bankCard.getCardNo());
            res.setOpenProvinceId(bankCard.getOpenProvinceId());
            res.setOpenCityId(bankCard.getOpenCityId());
            res.setBankId(bankCard.getBankId());

            BsUser user = userService.findUserById(req.getUserId());
            res.setUserName(user.getUserName());
            res.setIdCard(user.getIdCard());
        }
    }

    public void addSave(ReqMsg_Bank_AddSave req, ResMsg_Bank_AddSave res) {

        // 判断开户行与当前卡号是否匹配
        BsCardBin cardBin = cardBinService.findCardBinByCardNo(req.getCardNo());
        if (cardBin == null) {// 如果卡bin为空，可能也是有效卡，目前只做记录，不做错误处理
            bsSpecialJnlService.addSpecialJnl(
                "【卡bin不存在】",
                "银行卡id:" + req.getBankId() + "，银行名称：" + req.getBankName() + "，卡号："
                        + req.getCardNo());
        } else if (cardBin.getBankId() != req.getBankId()) {
            throw new PTMessageException(PTMessageEnum.BANK_CARDID_NO_EQUAL);
        }

        DafyUserExt dafyUser = dafyUserService.findDafyUserByUserId(req.getUserId());

        // 判断是否是已绑定或绑定中状态，是则报错，不进行下面的逻辑
        if (dafyUser != null && dafyUser.getDafyUserId() != null
            && dafyUser.getStatus() == DafyInConstant.DAFY_USER_STATUS_BINDED) {
            throw new PTMessageException(PTMessageEnum.DAFY_BANKCARD_IS_BINDED);
        } else if (dafyUser != null && dafyUser.getDafyUserId() != null
                   && dafyUser.getStatus() == DafyInConstant.DAFY_USER_STATUS_BINDING) {
            throw new PTMessageException(PTMessageEnum.DAFY_BANKCARD_IS_BINDING);
        }

        // 非已绑定或绑定中，进入正常流程，分成3种情况：1、新用户绑卡；2、修改绑卡；3、修改注册和绑卡

        // 记录待绑定的 银行卡信息和用户实名信息
        BsUser user = userService.findUserById(req.getUserId());
        recordCardAndUserInfo(req, user);

        // 新用户绑卡或 注册失败：新用户绑卡
        if (dafyUser == null
            || (dafyUser != null && dafyUser.getDafyUserId() == null && dafyUser.getStatus() == DafyInConstant.DAFY_USER_STATUS_BIND_FAIL)) {
            registerAndBindCard(req, user, dafyUser, Constants.BINDCARD_STATUS_NEW_ALL);
        }
        // 注册成功，实名认证成功，但绑卡失败：修改绑卡
        else if (dafyUser != null && dafyUser.getDafyUserId() != null
                 && dafyUser.getStatus() == DafyInConstant.DAFY_USER_STATUS_BIND_FAIL
                 && user.getIsBindName() == Constants.IS_BIND_NAME_YES) {
            registerAndBindCard(req, user, dafyUser, Constants.BINDCARD_STATUS_UPDATE_CARD);
        }
        // 注册成功，实名认证和绑卡都失败：修改注册和绑卡
        else if (dafyUser != null && dafyUser.getDafyUserId() != null
                 && dafyUser.getStatus() == DafyInConstant.DAFY_USER_STATUS_BIND_FAIL
                 && user.getIsBindName() == Constants.IS_BIND_NAME_NO) {
            registerAndBindCard(req, user, dafyUser, Constants.BINDCARD_STATUS_UPDATE_ALL);
        }
        // 其他情况
        else {
            registerAndBindCardFail(req);
        }
    }

    /**
     * 达飞注册+三方实名认证+三方绑卡 申请
     * 
     * @param req
     * @param user
     * @param dafyUser
     * @param bindcardStatus
     */
    private void registerAndBindCard(ReqMsg_Bank_AddSave req, BsUser user, DafyUserExt dafyUser,
                                     int bindcardStatus) {
        // 记录达飞注册信息和银行卡信息(无则新增，有则更新)
        DafyUserExt dafyUserExt = new DafyUserExt();
        dafyUserExt.setUserId(req.getUserId());
        dafyUserExt.setDafyUserId(dafyUser == null ? null : dafyUser.getDafyUserId());
        dafyUserExt.setCreateTime(new Date());
        dafyUserExt.setBankCard(req.getCardNo());
        dafyUserExt.setStatus(Constants.BANK_CARD_BINDING);
        dafyUserExt.setBindFailReson(PTMessageEnum.DAFY_BANKCARD_IS_BINDING.getMessage());
        dafyUserService.addOrModifyDafyUser(dafyUserExt);
        // 发起申请
        B2GResMsg_Payment_BasicInformation infoRes = null;
        try {
            B2GReqMsg_Payment_BasicInformation infoReq = new B2GReqMsg_Payment_BasicInformation();
            infoReq.setBankCardNo(req.getCardNo());

            DafyBindBankExt dafyBank = bankCardService.findDafyBankIdByLocalBankId(req.getBankId());
            infoReq.setBankName(String.valueOf(dafyBank.getDafyBankId()));// 转为达飞银行编码

            infoReq.setCardNo(req.getIdCard());
            infoReq.setCustomerId(dafyUser == null ? null : dafyUser.getDafyUserId());
            infoReq.setMobile(user.getMobile());
            infoReq.setName(req.getCardOwner());
            infoReq.setOpenAccountCity(String.valueOf(req.getCityCode()));// 转为
                                                                          // 达飞编码格式
            infoReq.setOpenAccountProvince(String.valueOf(req.getProvinceCode()));// 转为
                                                                                  // 达飞编码格式
            infoReq.setStatus(bindcardStatus);
            infoReq.setSubbranchName(req.getBranchName());
            infoRes = dafyTransportService.basicInformation(infoReq);
        } catch (Exception e) {
            // 修改记录为失败
            BsBankCard bankCard = new BsBankCard();
            bankCard.setUserId(req.getUserId());
            bankCard.setCardNo(req.getCardNo());
            bankCard.setStatus(Constants.BANK_CARD_BINDFAIL);
            bankCardService.modifyBankCardByUserIdAndCardNo(bankCard);

            DafyUserExt dafyUserFail = new DafyUserExt();
            dafyUserFail.setUserId(req.getUserId());
            dafyUserFail.setBankCard(req.getCardNo());
            dafyUserFail.setStatus(Constants.BANK_CARD_BINDFAIL);
            dafyUserFail.setBindFailReson(PTMessageEnum.SYSTEM_ERROR.getMessage());
            dafyUserService.addOrModifyDafyUser(dafyUserFail);

            throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR, e.getMessage());
        }
        if (infoRes != null) {
            DafyUserExt dafyUserExtRes = dafyUserService.findDafyUserByUserId(req.getUserId());
            // 申请 有异常情况
            if (!ConstantUtil.BSRESCODE_SUCCESS.equals(infoRes.getResCode())) {

                switch (bindcardStatus) {
                    case Constants.BINDCARD_STATUS_NEW_ALL:
                        // 达飞注册失败
                        bindRealNameAndBindCardFail(req, dafyUserExtRes, infoRes);
                        //手机号已经存在或者身份证号存在: 需要记录特殊交易流水，并发起管理员短信通知，进行人工干预
                        if (PTMessageEnum.DAFY_RESULT_MOBILE_OR_IDNO_EXIST.getMessage().equals(
                            infoRes.getResMsg())) {
                            String type = "【银行卡绑定】";
                            String detail = "【" + DateUtil.format(new Date()) + "】银行卡绑定：" + "用户["
                                            + user.getUserName() + "]手机号[" + user.getMobile()
                                            + "]已经存在或者身份证号[" + user.getIdCard() + "]存在";
                            bsSpecialJnlService.addSpecialJnl(type, detail);
                            smsService.sendSysManagerMobiles("客户注册重复提醒",false);
                        }
                        break;
                    case Constants.BINDCARD_STATUS_UPDATE_CARD:
                        // 已绑卡成功，需要获取达飞数据，更新本地实名信息和绑卡信息
                        if (infoRes.getThirdStatus() == Constants.BINDCARD_THIRDSTATUS_CARD_BINDED) {
                            bindRealNameAndBindCardSuccess(req, user, infoRes);
                        }
                        // 卡绑定申请失败
                        else {
                            bindRealNameAndBindCardFail(req, dafyUserExtRes, infoRes);
                        }
                        break;
                    case Constants.BINDCARD_STATUS_UPDATE_ALL:
                        // 实名已认证，卡绑定未成功，需要获取达飞数据，更新本地实名信息和绑卡信息
                        if (infoRes.getThirdStatus() == Constants.BINDCARD_THIRDSTATUS_REALNAME_BINDED) {
                            bindRealNameSuccessAndBindCardFail(req, user, infoRes);
                        }
                        // 实名+认证都已成功，需要获取达飞数据，更新本地实名信息和绑卡信息
                        else if (infoRes.getThirdStatus() == Constants.BINDCARD_THIRDSTATUS_REALNAME_AND_CARD_BINDED) {
                            bindRealNameAndBindCardSuccess(req, user, infoRes);
                        }
                        // 实名+卡绑定申请失败
                        else {
                            bindRealNameAndBindCardFail(req, dafyUserExtRes, infoRes);
                        }
                        break;
                    default:
                        bindRealNameAndBindCardFail(req, dafyUserExtRes, infoRes);
                        break;
                }
            }
            // 申请成功
            else {
                switch (bindcardStatus) {
                    case Constants.BINDCARD_STATUS_NEW_ALL:
                        // 注册成功，等待实名认证通知+卡绑定通知。 需 记录达飞客户号操作
                        DafyUserExt dafyUserExt2DB = new DafyUserExt();
                        dafyUserExt2DB.setId(dafyUserExtRes.getId());
                        dafyUserExt2DB.setDafyUserId(infoRes.getCustomerId());
                        dafyUserExt2DB.setDafyRegisterTime(new Date());
                        dafyUserService.modifyDafyUser(dafyUserExt2DB);
                        break;
                    case Constants.BINDCARD_STATUS_UPDATE_CARD:
                        // 修改绑卡申请 发起成功，等待卡绑定通知。 不需其他处理
                        break;
                    case Constants.BINDCARD_STATUS_UPDATE_ALL:
                        // 修改实名+绑卡申请 发起成功，等待实名认证通知+卡绑定通知。不需其他处理
                        break;
                }
            }
        }
    }

    /**
     * 实名认证已成功，但银行卡绑定未成功
     * 
     * @param req
     * @param user
     * @param infoRes
     */
    private void bindRealNameSuccessAndBindCardFail(ReqMsg_Bank_AddSave req, BsUser user,
                                                    B2GResMsg_Payment_BasicInformation infoRes) {
        // 达飞用户扩展表修改
        DafyUserExt dafyUserExtTemp = new DafyUserExt();
        dafyUserExtTemp.setUserId(req.getUserId());
        dafyUserExtTemp.setDafyUserId(infoRes.getCustomerId());
        // dafyUserExtTemp.setBankCard(infoRes.getBankCardNo());
        dafyUserExtTemp.setStatus(Constants.BANK_CARD_BINDFAIL);
        dafyUserExtTemp.setBindFailReson("绑卡实名已认证，但绑卡未成功，请尝试重新发起");
        dafyUserService.addOrModifyDafyUser(dafyUserExtTemp);

        // 修改本次发起的银行卡信息 为失败
        BsBankCard bankCard = new BsBankCard();
        bankCard.setUserId(req.getUserId());
        bankCard.setCardNo(req.getCardNo());
        bankCard.setStatus(Constants.BANK_CARD_BINDFAIL);
        bankCardService.modifyBankCardByUserIdAndCardNo(bankCard);

        // 修改bs_user表，记录真实姓名和身份证号
        BsUser tempUser = new BsUser();
        tempUser.setId(user.getId());
        tempUser.setUserName(req.getCardOwner());
        tempUser.setIdCard(req.getIdCard());
        tempUser.setIsBindName(Constants.IS_BIND_NAME_YES);
        tempUser.setIsBindBank(Constants.IS_BIND_BANK_NO);
        userService.updateBsUser(tempUser);

        throw new PTMessageException(PTMessageEnum.DAFY_BINDCARD_RETURN_FAIL,
            "绑卡实名已认证，但绑卡未成功，请尝试重新发起");
    }

    /**
     * 实名认证和卡绑定都成功
     * 
     * @param req
     * @param user
     * @param infoRes
     */
    private void bindRealNameAndBindCardSuccess(ReqMsg_Bank_AddSave req, BsUser user,
                                                B2GResMsg_Payment_BasicInformation infoRes) {
        // 达飞用户扩展表修改
        DafyUserExt dafyUserExtTemp = new DafyUserExt();
        dafyUserExtTemp.setUserId(req.getUserId());
        dafyUserExtTemp.setDafyUserId(infoRes.getCustomerId());
        dafyUserExtTemp.setBankCard(infoRes.getBankCardNo());
        dafyUserExtTemp.setStatus(Constants.BANK_CARD_NORMAL);
        dafyUserExtTemp.setDafyBindCardTime(new Date());
        dafyUserExtTemp.setBindFailReson(PTMessageEnum.DAFY_BANKCARD_IS_SUCCESS.getMessage());
        dafyUserService.addOrModifyDafyUser(dafyUserExtTemp);

        // 三方已成功的银行卡信息插入和修改
        BsBankCard bankCard = new BsBankCard();
        bankCard.setUserId(req.getUserId());
        bankCard.setCardNo(infoRes.getBankCardNo());
        bankCard.setCardOwner(infoRes.getName());

        DafyBindBankExt dafyBank = bankCardService.findBankIdByDafyBankId(Integer.valueOf(infoRes
            .getBankName()));
        bankCard.setBankId(dafyBank.getBankId());// 转为 本地编码格式

        bankCard.setStatus(Constants.BANK_CARD_NORMAL); //
        bankCard.setSubBranchName(infoRes.getSubbranchName());

        BsPCA city = provinceService.findPCAByItemCode(infoRes.getOpenAccountCity());
        BsPCA province = provinceService.findPCAByItemCode(infoRes.getOpenAccountProvince());
        bankCard.setOpenCityId(city.getId());// 转为 本地编码格式
        bankCard.setOpenProvinceId(province.getId());// 转为 本地编码格式

        bankCard.setBankName(infoRes.getBankName());
        bankCard.setIsFirst(Constants.IS_FIRST_BANK_YES);
        bankCard.setBindTime(new Date());
        // 有则更新，无则新增
        // bankCardService.addBankCard(bankCard);
        // 新增
        if (!req.getCardNo().equals(infoRes.getBankCardNo())) {
            // 三方已成功的银行卡信息插入
            bankCardService.addBankCard(bankCard);
            // 本次银行卡状态修改成失败
            BsBankCard oldBankCard = new BsBankCard();
            oldBankCard.setCardNo(req.getCardNo());
            oldBankCard.setUserId(req.getUserId());
            oldBankCard.setStatus(Constants.BANK_CARD_BINDFAIL);
            bankCardService.modifyBankCardByUserIdAndCardNo(oldBankCard);
        }
        // 修改
        else {
            bankCardService.modifyBankCardByUserIdAndCardNo(bankCard);
        }

        // 修改bs_user表，记录真实姓名和身份证号，认证标志设为已认证
        BsUser tempUser = new BsUser();
        tempUser.setId(user.getId());
        tempUser.setUserName(req.getCardOwner());
        tempUser.setIdCard(req.getIdCard());
        tempUser.setIsBindName(Constants.IS_BIND_NAME_YES);
        tempUser.setIsBindBank(Constants.IS_BIND_BANK_YES);
        userService.updateBsUser(tempUser);

        throw new PTMessageException(PTMessageEnum.DAFY_BINDCARD_RETURN_FAIL, "已绑定成功，请勿重复绑定");

    }

    /**
     * 实名认证和卡绑定都失败
     * 
     * @param req
     * @param dafyUserExtRes
     * @param infoRes
     */
    private void bindRealNameAndBindCardFail(ReqMsg_Bank_AddSave req, DafyUserExt dafyUserExtRes,
                                             B2GResMsg_Payment_BasicInformation infoRes) {
        String errMsg = infoRes.getResMsg();
        // 修改dafy_user_ext、bs_bank_card记录 状态失败
        DafyUserExt dafyUserExt2DB = new DafyUserExt();
        dafyUserExt2DB.setId(dafyUserExtRes.getId());
        dafyUserExt2DB.setStatus(Constants.BANK_CARD_BINDFAIL);
        dafyUserExt2DB.setBindFailReson(errMsg != null ? errMsg.substring(errMsg.indexOf("：") + 1)
            : PTMessageEnum.DAFY_REALNAME_AUTH_ERROR.getMessage());
        dafyUserService.modifyDafyUser(dafyUserExt2DB);

        BsBankCard bsBankCard = new BsBankCard();
        bsBankCard.setUserId(req.getUserId());
        bsBankCard.setCardNo(req.getCardNo());
        bsBankCard.setStatus(Constants.BANK_CARD_BINDFAIL);
        bankCardService.modifyBankCardByUserIdAndCardNo(bsBankCard);

        throw new PTMessageException(PTMessageEnum.DAFY_BINDCARD_RETURN_FAIL,
            errMsg != null ? errMsg.substring(errMsg.indexOf("：") + 1)
                : PTMessageEnum.DAFY_REALNAME_AUTH_ERROR.getMessage());

    }

    private void recordCardAndUserInfo(ReqMsg_Bank_AddSave req, BsUser user) {
        // 记录银行卡信息（有则修改，无则新增），状态为3绑定中
        BsBankCard bankCard = new BsBankCard();

        bankCard.setUserId(req.getUserId());
        bankCard.setCardNo(req.getCardNo());
        bankCard.setCardOwner(req.getCardOwner());
        bankCard.setBankId(req.getBankId());
        bankCard.setStatus(Constants.BANK_CARD_BINDING); // 状态为绑定中
        bankCard.setIsFirst(Constants.IS_FIRST_BANK_YES);
        bankCard.setBindTime(new Date());
        bankCard.setSubBranchName(req.getBranchName());
        bankCard.setOpenCityId(req.getOpenCity());
        bankCard.setOpenProvinceId(req.getOpenProvince());
        bankCard.setBankName(req.getBankName());
        // 有则更新，无则新增
        bankCardService.addOrModifyBankCard(bankCard);

        // 修改bs_user表，记录真实姓名和身份证号
        BsUser tempUser = new BsUser();
        tempUser.setId(user.getId());
        tempUser.setUserName(req.getCardOwner());
        tempUser.setIdCard(req.getIdCard());
        userService.updateBsUser(tempUser);

    }

    private void registerAndBindCardFail(ReqMsg_Bank_AddSave req) {
        // 修改bs_bank_card表状态 为失败
        BsBankCard bankCard = new BsBankCard();
        bankCard.setUserId(req.getUserId());
        bankCard.setCardNo(req.getCardNo());
        bankCard.setStatus(Constants.BANK_CARD_BINDFAIL);
        bankCardService.modifyBankCardByUserIdAndCardNo(bankCard);
        throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR);
    }

    /**
     * 查询卡bin
     * 
     * @param req
     * @param res
     */
    public void queryBankBin(ReqMsg_Bank_QueryBankBin req, ResMsg_Bank_QueryBankBin res) {
        BsCardBin bsCardBin = bankCardService.queryBankBin(req.getCardNo());
        res.setBankCardLen(bsCardBin.getBankCardLen());
        res.setBankId(bsCardBin.getBankId());
        res.setCardBin(bsCardBin.getCardBin());
        res.setCardBinLen(bsCardBin.getCardBinLen());
        res.setBankCardFuncType(bsCardBin.getBankCardFuncType());
        if(bsCardBin.getBankId() != null) {
            BsBank bank = bankCardService.findBankById(bsCardBin.getBankId());
            res.setBankName(bank.getName());
            
            Double amount = orderService.calculateTotal(req.getUserId(), bsCardBin.getBankId());
            res.setAmount(amount==null?0.0:amount);
        }
        
    }

    /**
     * 解绑银行卡
     * 
     * @param req
     * @param res
     */
    public void unbundling(ReqMsg_Bank_Unbundling req, ResMsg_Bank_Unbundling res) {
        bankCardService.unbundling(req.getCardId(), req.getUserId());
    }
    
    /**
     * 
     * @Title: selectBindBankList 
     * @Description: 查询用户绑定的银行卡
     * @param req
     * @param res
     * @throws
     */
    public void bindBankList(ReqMsg_Bank_bindBankList req, ResMsg_Bank_bindBankList res) {
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> able = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> disable = new ArrayList<Map<String,Object>>();
		
		//查询账户余额
		BsSubAccount sub = userService.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_JSH, Constants.SUBACCOUNT_STATUS_OPEN).get(0);
		res.setSubAccountId(sub.getId());
		res.setAvailableBalance(sub.getAvailableBalance());
		
		List<BsBindBankVO> list = bankCardService.selectBindBankList(req.getUserId(), Constants.PAY_TYPE_QUICK, null);
		//查询支持的快捷支付银行列表
		ReqMsg_Bank_Pay19BankList reqPay19BankMsg = new ReqMsg_Bank_Pay19BankList();
		reqPay19BankMsg.setUserId(req.getUserId());
		ResMsg_Bank_Pay19BankList resPay19BankMsg = new ResMsg_Bank_Pay19BankList();
		pay19BankList(reqPay19BankMsg,resPay19BankMsg);
		
		//匹配用户快捷银行信息进行银行信息替换

		
    	for(BsBindBankVO vo : list) {

    		//判断19付银行通道是否可用
    		Date current = new Date();
    		Date start = vo.getForbiddenStart();
    		Date end = vo.getForbiddenEnd();
    		if (start != null && end != null) {
        		int before = com.pinting.business.util.DateUtil.compareTo(current, start);
        		int after = com.pinting.business.util.DateUtil.compareTo(current, end);
        		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
        			if(!(before < 0 || after > 0)) {
        				vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
        			}
        		}
    		}
    		vo.setOneTop(MoneyUtil.divide(vo.getOneTop(), 10000.0).doubleValue());
    		vo.setDayTop(MoneyUtil.divide(vo.getDayTop(), 10000.0).doubleValue());
			for (Map<String, Object> pay19QuickBank : resPay19BankMsg.getBankList()) {
				if (vo.getBankId().equals(pay19QuickBank.get("bankId"))) {
					vo.setOneTop((Double)pay19QuickBank.get("oneTop"));
					vo.setDayTop((Double)pay19QuickBank.get("dayTop"));
					vo.setMonthTop((Double)pay19QuickBank.get("monthTop"));
					vo.setIsAvailable((Integer)pay19QuickBank.get("isAvailable"));
					vo.setDailyNotice((String)pay19QuickBank.get("dailyNotice"));
				}
			}
    		
    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
    			able.add(BeanUtils.transBeanMap(vo));
    		}
    		else {
    			disable.add(BeanUtils.transBeanMap(vo));
    		}
    	}
    	result.addAll(able);
    	result.addAll(disable);
    	Boolean s= userService.isBindBank(req.getUserId());
        res.setBindBank(s);
    	res.setBankList(result);
    	//与购买无关，查询最小充值金额
    	BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.RECHANGE_LIMIT);
    	res.setRechangeLimit(bsSysConfig.getConfValue());
    }
    
    /**
     * 
     * @Title: queryDefaultBank 
     * @Description: 判断用户是否绑定，已绑定的老用户显示最近一次使用的银行卡
     * @param req
     * @param res
     * @throws
     */
    public void queryDefaultBank(ReqMsg_Bank_QueryDefaultBank req, ResMsg_Bank_QueryDefaultBank res) {
    	//判断用户是否绑卡
    	if(userService.isBindBank(req.getUserId())) {
    		res.setBindBank(true);
    		//查询最近一次使用的银行卡
    		BsBindBankVO vo = bankCardService.selectDefaultBank(req.getUserId());
    		if (vo == null) {
    			//告警
    			specialJnlService.warn4Fail(null, "{默认卡编号不匹配}用户ID:"+req.getUserId(),null,"默认卡编号不匹配",false);
    			//查询安全卡
    			vo = bankCardService.selectSafeBankCard(req.getUserId());
    			if(vo == null) {
    				//告警
        			specialJnlService.warn4Fail(null, "{安全卡编号不匹配}用户ID:"+req.getUserId(),null,"安全卡编号不匹配",false);
    				
    				res.setBindBank(false);
        			return;
    			}
    			
			}
    		//查询用户当期啊银行通道信息
    		Bs19payBank bs19payBank = bankCardService.selectChannelBankInfo(req.getUserId(), vo.getBankId());
    		//查询用户信息
    		BsUser user = userService.findUserById(req.getUserId());
    		if(user != null) {
    			res.setMobile(user.getMobile().trim());
    		}
    		//查询用户账户余额
    		BsSubAccount sub = userService.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_JSH, Constants.SUBACCOUNT_STATUS_OPEN).get(0);
    		res.setSubAccountId(sub.getId());
    		res.setAvailableBalance(sub.getAvailableBalance()==null?0.0:sub.getAvailableBalance());
    		res.setDailyNotice(bs19payBank.getDailyNotice());
    		res.setBankId(vo.getBankId());
    		res.setCardNo(vo.getCardNo());
    		res.setOneTop(MoneyUtil.divide(bs19payBank.getOneTop(), 10000.0).doubleValue());
    		res.setDayTop(MoneyUtil.divide(bs19payBank.getDayTop(), 10000.0).doubleValue());
    		res.setBankName(vo.getBankName());
    		res.setIsFirst(vo.getIsFirst());
    		//res.setMobile(vo.getMobile());
    		res.setUserName(vo.getUserName());
    		res.setIdCard(vo.getIdCard());
    		//银行卡当日已使用的额度
    		Double amount = orderService.calculateTotal(req.getUserId(), vo.getBankId());
    		res.setAmount(amount==null?0.0:amount);
    	}
    	else {
    		res.setBindBank(false);
    	}
    	//与购买无关，查询最小充值金额
    	BsSysConfig bsSysConfig = sysConfigService.findConfigByKey(Constants.RECHANGE_LIMIT);
    	res.setRechangeLimit(bsSysConfig.getConfValue());
    }

    /**
     * 先检查当前时间是否在节假日时间区间内（start时间的开始之前一天再往前推半个小时，结束时间当天的0点）
     * 1、在节假日禁止的时间区间内
     * 	 a、查询出所有主通道is_main=1快捷银行卡列表
     *   b、检查银行卡是否在禁用时间区间内：禁用区间则is_available返回2，否则直接返回
     * 
     * 2、不在节假日的时间区间内
     * 	 a、查询出所有优先级为1  channel_priority=1快捷银行卡列表
     *   b、根据有无用户ID判断是否进行用户优先通道替换
     * 	  	有用户ID传入：查询用户优先通道配置表中是否有用户优先通道配置，有优先通道则
     * 		根据通道ID查询出通道银行相关信息替换对应的银行
     * 	 	无用户ID传入：不进行替换
     *   c、检查银行卡是否在禁用时间区间内：禁用区间则is_available返回2，否则直接返回
     * 	  
     * 	
     * @Title: pay19BankList 
     * @Description: 查询支持快捷支付的优先级最高银行
     * @param req
     * @param res
     * @throws
     */
    @RedisCache(serviceName = "bankPay19BankListCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.BANK_PAY19BANKLIST)
    public ResMsg_Bank_Pay19BankList pay19BankList(ReqMsg_Bank_Pay19BankList req, ResMsg_Bank_Pay19BankList res) {
    	/**
    	 * 1、节假日
			逻辑：
				a、查询出 is_main= 1的快捷支付银行列表

				b、判断forbidden时间区间
				          在时间区间内：通道设置为不可用 （is_available=2）
				          不在时间区间：直接返回 is_available
			
			2、 非节假日
			    a、先查出优先级为1的快捷银行列表
			    b、判断是否有userId 进行个人优先通道信息替换
			             有：
			                   查询user_channel表是否有数据  
			                        有则查询bank_channel_id,根据bank_channel_id查找bs_19pay_bank 表中对应的
			快捷银行信息，和list数据匹配替换对应的银行数据，得到一个新的List
			
			 c、判断forbidden时间区间
			          在时间区间内：通道设置为不可用 （is_available=2）
			          不在时间区间：直接返回 is_available

    	 */
    	
    	//检查当前时间是否在hiliday表控制的假日时间之内
    	boolean isHoliday = holidayService.isHolidayTimeList(new Date());
    	if (isHoliday) { //在假期时间内
    		//1-a、查询出 is_main= 1的快捷支付银行列表
	    	List<Pay19BankVO> list = bankCardService.selectMainBankList(Constants.PAY_TYPE_QUICK);
	    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> able = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> disable = new ArrayList<Map<String,Object>>();
	    	//1-b、判断forbidden时间区间
	    	// 	在时间区间内：通道设置为不可用 （is_available=2）
	    	//  不在时间区间：直接返回 is_available

	    	for(Pay19BankVO vo : list) {
	    		//判断主银行通道是否可用
	    		Date current = new Date();
	    		Date start = vo.getForbiddenStart();
	    		Date end = vo.getForbiddenEnd();
	    		if (start != null && end != null) {
	    			int before = com.pinting.business.util.DateUtil.compareTo(current, start);
		    		int after = com.pinting.business.util.DateUtil.compareTo(current, end);
		    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
		    			if(!(before < 0 || after > 0)) {
		    				vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
		    			}
		    		}
				}

	    		vo.setOneTop(MoneyUtil.divide(vo.getOneTop() == null ? 0: vo.getOneTop(), 10000.0).doubleValue());
	    		vo.setDayTop(MoneyUtil.divide(vo.getDayTop() == null ? 0: vo.getDayTop(), 10000.0).doubleValue());
	    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
	    			able.add(BeanUtils.transBeanMap(vo));
	    		}
	    		else {
	    			disable.add(BeanUtils.transBeanMap(vo));
	    		}
	    	}
	    	result.addAll(able);
	    	result.addAll(disable);
	    	res.setBankList(result);
		}else { //非节假日
			// 2-a、先查出优先级为1的快捷银行列表
	    	List<Pay19BankVO> list = bankCardService.selectFirstBankList(Constants.PAY_TYPE_QUICK);
	    	//2-b、判断是否有userId 进行个人优先通道信息替换
	    	if (req.getUserId() != null) {
	    		//查询用户是否配置了优先支付通道
	    		List<BsUserChannel> bsUserChannelList = userChannelService.queryUserChannel(req.getUserId());
	    		if (!CollectionUtils.isEmpty(bsUserChannelList)) {
	    			for (BsUserChannel UserChanne : bsUserChannelList) {
		    			Pay19BankVO pay19BankVO= bankCardService.selectFirstBank(UserChanne.getBankChannelId());
		    			int temp = 0;
		    			//将用户配置通道的信息覆盖到List
		    			if (pay19BankVO!=null) {
							for(Pay19BankVO vo : list) {
			    				if (vo.getBankId() == pay19BankVO.getBankId()) {
			    			    	list.get(temp).setId(pay19BankVO.getId());
			    			    	list.get(temp).setBankId(pay19BankVO.getBankId());
			    			    	list.get(temp).setChannel(pay19BankVO.getChannel());
			    			    	list.get(temp).setChannelPriority(pay19BankVO.getChannelPriority());
			    			    	list.get(temp).setIsMain(pay19BankVO.getIsMain());
			    			    	list.get(temp).setPay19BankCode(pay19BankVO.getPay19BankCode());
			    			    	list.get(temp).setPayType(pay19BankVO.getPayType());
			    			    	list.get(temp).setOneTop(pay19BankVO.getOneTop()==null ? 0: pay19BankVO.getOneTop());
			    			    	list.get(temp).setDayTop(pay19BankVO.getDayTop()==null ? 0: pay19BankVO.getDayTop());
			    			    	list.get(temp).setMonthTop(pay19BankVO.getMonthTop()==null ? 0: pay19BankVO.getMonthTop());
			    			    	list.get(temp).setForbiddenStart(pay19BankVO.getForbiddenStart());
			    			    	list.get(temp).setForbiddenEnd(pay19BankVO.getForbiddenEnd());
			    			    	list.get(temp).setIsAvailable(pay19BankVO.getIsAvailable());
			    			    	list.get(temp).setNotice(pay19BankVO.getNotice());
			    			    	list.get(temp).setDailyNotice(pay19BankVO.getDailyNotice());
			    			    	list.get(temp).setBankName(pay19BankVO.getBankName());
                                    list.get(temp).setLargeLogo(pay19BankVO.getLargeLogo());
                                    list.get(temp).setSmallLogo(pay19BankVO.getSmallLogo());
								}
			    				temp = temp +1;
			    			}
						}
		    		
					}

				}
			}
	    	//2-c、判断forbidden时间区间
	    	//  在时间区间内：通道设置为不可用 （is_available=2）
	    	//  不在时间区间：直接返回 is_available

	    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> able = new ArrayList<Map<String,Object>>();
	    	List<Map<String,Object>> disable = new ArrayList<Map<String,Object>>();
	    	
	    	for(Pay19BankVO vo : list) {
	    		//判断19付银行通道是否可用
	    		Date current = new Date();
	    		Date start = vo.getForbiddenStart();
	    		Date end = vo.getForbiddenEnd();
	    		if (start != null && end != null) {
		    		int before = com.pinting.business.util.DateUtil.compareTo(current, start);
		    		int after = com.pinting.business.util.DateUtil.compareTo(current, end);
		    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
		    			if(!(before < 0 || after > 0)) {
		    				vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
		    			}
		    		}
	    		}
	    		vo.setOneTop(MoneyUtil.divide(vo.getOneTop() == null ? 0: vo.getOneTop(), 10000.0).doubleValue());
	    		vo.setDayTop(MoneyUtil.divide(vo.getDayTop() == null ? 0: vo.getDayTop(), 10000.0).doubleValue());
	    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
	    			able.add(BeanUtils.transBeanMap(vo));
	    		}
	    		else {
	    			disable.add(BeanUtils.transBeanMap(vo));
	    		}
	    	}
	    	result.addAll(able);
	    	result.addAll(disable);
	    	res.setBankList(result);
		}
    	return res;
    }
    
    /**
     * 
     * @Title: userBankInfo 
     * @Description: 查询用户信息和支持快捷支付的19付银行
     * @param req
     * @param res
     * @throws
     */
    public void userBankInfo(ReqMsg_Bank_UserBankInfo req, ResMsg_Bank_UserBankInfo res) {
    	//用户信息
    	BsUser user = userService.findUserById(req.getUserId());
    	res.setEmail(user.getEmail());
    	res.setIdCard(user.getIdCard());
    	res.setIsBindBank(user.getIsBindBank());
    	res.setIsBindName(user.getIsBindName());
    	res.setMobile(user.getMobile());
    	res.setNick(user.getNick());
    	res.setStatus(user.getStatus());
    	res.setUserId(user.getId());
    	res.setUserName(user.getUserName());
    	
    	//19付支持快捷支付银行列表
    	List<Pay19BankVO> list = bankCardService.select19PayBankList(Constants.PAY_TYPE_QUICK);
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> able = new ArrayList<Map<String,Object>>();
    	List<Map<String,Object>> disable = new ArrayList<Map<String,Object>>();
    	for(Pay19BankVO vo : list) {
    		//判断19付银行通道是否可用
    		Date current = new Date();
    		Date start = vo.getForbiddenStart();
    		Date end = vo.getForbiddenEnd();
    		if (start != null && end != null) {
    			int before = com.pinting.business.util.DateUtil.compareTo(current, start);
        		int after = com.pinting.business.util.DateUtil.compareTo(current, end);
        		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
        			if(!(before < 0 || after > 0)) {
        				vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
        			}
        		}
    		}
    		
    		vo.setOneTop(MoneyUtil.divide(vo.getOneTop(), 10000.0).doubleValue());
    		vo.setDayTop(MoneyUtil.divide(vo.getDayTop(), 10000.0).doubleValue());
    		if(vo.getIsAvailable() == Constants.IS_AVAILABLE_ABLE) {
    			able.add(BeanUtils.transBeanMap(vo));
    		}
    		else {
    			disable.add(BeanUtils.transBeanMap(vo));
    		}
    	}
    	result.addAll(able);
    	result.addAll(disable);
    	res.setBankList(result);
    }
    
    /**
     * 设置回款卡
     * 
     * @param req
     * @param res
     */
    public void setIsFirstBankCard(ReqMsg_Bank_SetIsFirstBankCard req, ResMsg_Bank_SetIsFirstBankCard res) {
        bankCardService.setIsFirstBankCard(req.getUserId(), req.getCardId());
    }
    
    /**
     * 回款路径列表
     * 
     * @param req
     * @param resp
     */
    public void returnPath(ReqMsg_Bank_ReturnPath req, ResMsg_Bank_ReturnPath resp) {
        BsUser bsUser = userService.findUserById(req.getUserId());
        BsBankCard bankCard = bankCardService.findFirstBankCardByUserId(req.getUserId());
        if(bankCard != null && bankCard.getBankId() != null) {
        	BsBank bank = bankCardService.findBankById(bankCard.getBankId());
        	if(bank != null) {
        		 resp.setSmallLogo(bank.getSmallLogo());
        	     resp.setLargeLogo(bank.getLargeLogo());
        	}
        	
        	resp.setBankName(bankCard.getBankName());
            resp.setCardId(bankCard.getId());
            resp.setCardNo(bankCard.getCardNo());
        } 
        resp.setReturnPath(String.valueOf(bsUser.getReturnPath()));
        
        
        //判断是否可用银行卡
        String isShowReturnPath = "no";
        Date registerTime = bsUser.getRegisterTime();
      
        Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
        if (new Date().compareTo(returnPathOverTime) < 0) {
        	isShowReturnPath = "yes";
		}
        resp.setIsShowReturnPath(isShowReturnPath);
    }
    
    /**
     * 查询回款卡信息
     * @param req
     * @param resp
     */
    public void queryFirstCard(ReqMsg_Bank_QueryFirstCard req,ResMsg_Bank_QueryFirstCard resp){
    	BsBankCard bankCard = bankCardService.findFirstBankCardByUserId(req.getUserId());
    	if(bankCard != null && bankCard.getBankId() != null) {
    		BsBank bank = bankCardService.findBankById(bankCard.getBankId());
    		if(bank != null) {
    			resp.setLargeLogo(bank.getLargeLogo());
            	resp.setSmallLogo(bank.getSmallLogo());
    		}
    		resp.setBankId(bankCard.getBankId());
    		resp.setBankName(bankCard.getBankName());
    		resp.setCardNo(bankCard.getCardNo());
    		resp.setCardId(bankCard.getId());
    	}
    	//查询用户账户余额
		BsSubAccount sub = userService.selectSubAccount(req.getUserId(), Constants.PRODUCT_TYPE_JSH, Constants.SUBACCOUNT_STATUS_OPEN).get(0);
		resp.setCan_withdraw(sub.getCanWithdraw());
        BsSubAccount depJsh = subAccountService.findDEPJSHSubAccountByUserId(req.getUserId());
        if(null != depJsh) {
            resp.setDepCanWithdraw(depJsh.getCanWithdraw() == null ? 0d: depJsh.getCanWithdraw());
        }
		DateUtil.firstDayOfMonth();
		Integer usedWithdrawTimeDay = userTransDetailService.countByUserIdWithdrawSuc(
				req.getUserId(), DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date())));
		Integer usedWithdrawTimeMonth = userTransDetailService.countByUserIdWithdrawSuc(
				req.getUserId(),DateUtil.parseDate(DateUtil.formatYYYYMMDD(DateUtil.firstDayOfMonth())));
		
		Integer sysWithdrawTimeDay = Integer.parseInt(sysConfigService.findWithdrawDay().getConfValue());
		Integer sysWithdrawTimeMonth = Integer.parseInt(sysConfigService.findWithdrawMonth().getConfValue());
		if(sysWithdrawTimeMonth - usedWithdrawTimeMonth < sysWithdrawTimeDay){
			Integer count = sysWithdrawTimeMonth - usedWithdrawTimeMonth;
			resp.setCan_withdraw_times(count);
		}else{
			Integer count = sysWithdrawTimeDay - usedWithdrawTimeDay;
			resp.setCan_withdraw_times(count);
		}
		
		//与购买无关，查询最小充值金额
//    	BsSysConfig bsSysConfig1 = sysConfigService.findConfigByKey(Constants.RECHANGE_LIMIT);
//    	resp.setRechageLimit(bsSysConfig1.getConfValue());
        resp.setRechageLimit((String)assetsService.queryTopUpInstruction().get("rechangeLimit"));
		
		//与购买无关，查询最小提现金额
    	BsSysConfig bsSysConfig2 = sysConfigService.findConfigByKey(Constants.WITHDRAW_LIMIT);
        BsSysConfig singleWithdrawUpperLimit = sysConfigService.findConfigByKey(Constants.SINGLE_WITHDRAW_UPPER_LIMIT);
        BsSysConfig dayWithdrawUpperLimit = sysConfigService.findConfigByKey(Constants.DAY_WITHDRAW_UPPER_LIMIT);
    	resp.setWithdrawLimit(bsSysConfig2.getConfValue());
        resp.setSingleWithdrawUpperLimit(Double.parseDouble(singleWithdrawUpperLimit.getConfValue()));
        resp.setDayWithdrawUpperLimit(Double.parseDouble(dayWithdrawUpperLimit.getConfValue()));
        // 剩余每月免费提现次数；相应手续费；用户当月可免费提现总次数
        Map<String, Object> withdrawInstrction = assetsService.queryWithdrawInstrction();
        resp.setCan_withdraw_times(assetsService.queryFreeWithdrawTimesByUserId(req.getUserId()));
        resp.setEachMonthWithdrawTimes(Integer.parseInt((String)withdrawInstrction.get("eachMonthWithdrawTimes")));
        resp.setWithdrawCounterFee(Double.parseDouble((String)withdrawInstrction.get("withdrawCounterFee")));
        resp.setWithdrawLimitAmount(Double.parseDouble((String)withdrawInstrction.get("withdrawLimitAmount")));
    }
    
    /**
     * 根据银行卡ID查询银行卡
     * 
     * @param req
     * @param res
     */
    public void queryCardById(ReqMsg_Bank_QueryCardById req, ResMsg_Bank_QueryCardById res) {
        BsBankCard bankCard = bankCardService.findBankCardById(req.getCardId());
        res.setBankId(bankCard.getBankId());
        res.setBankName(bankCard.getBankName());
        res.setBindTime(bankCard.getBindTime());
        res.setCardNo(bankCard.getCardNo());
        res.setCardOwner(bankCard.getCardOwner());
        res.setId(bankCard.getId());
        res.setIdCard(bankCard.getIdCard());
        res.setIsFirst(bankCard.getIsFirst());
        res.setMobile(bankCard.getMobile());
        res.setStatus(bankCard.getStatus());
        res.setUserId(bankCard.getUserId());
    }
    
    
    /**
     * 根据银行ID查询银行信息
     * 
     * @param req
     * @param res
     */
    public void queryBankById(ReqMsg_Bank_QueryBankById req, ResMsg_Bank_QueryBankById res) {
    	BsBank bsBank = bankCardService.queryBankById(req.getBankId());
    	if(bsBank != null) {
    		res.setId(bsBank.getId());
        	res.setName(bsBank.getName());
        	res.setStatus(bsBank.getStatus());
        	res.setNote(bsBank.getNote());
        	res.setSmallLogo(bsBank.getSmallLogo());
        	res.setLargeLogo(bsBank.getLargeLogo());
        	res.setCreateTime(bsBank.getCreateTime());
        	res.setUpdateTime(bsBank.getUpdateTime());
    	}
    }
    
    /**
     * 
     * @Title: alreadyUseMoney 
     * @Description: 计算用户当日已使用的银行额度
     * @param req
     * @param res
     * @throws
     */
    public void alreadyUseMoney(ReqMsg_Bank_AlreadyUseMoney req, ResMsg_Bank_AlreadyUseMoney res) {
    	Double amount = orderService.calculateTotal(req.getUserId(), req.getBankId());
    	res.setAmount(amount==null?0.0:amount);
    }
    
    /**
     * 
     * @Title: queryBankInfoByUser 
     * @Description: 查询用户绑定的单个银行信息
     * @param req
     * @param res
     * @throws
     */
    public void queryBankInfoByUser(ReqMsg_Bank_QueryBankInfoByUser req, ResMsg_Bank_QueryBankInfoByUser res) {
    	List<BsBindBankVO> list = bankCardService.selectBindBankList(req.getUserId(), Constants.PAY_TYPE_QUICK, req.getBankId());
    	if(list != null && list.size() > 0) {
    		BsBindBankVO vo = list.get(0);
    		res.setBankId(vo.getBankId());
    		res.setBankName(vo.getBankName());
    		res.setCardNo(vo.getCardNo());
    		res.setIdCard(vo.getIdCard());
    		res.setMobile(vo.getMobile());
    		res.setUserName(vo.getUserName());
    		res.setDailyNotice(vo.getDailyNotice());
    	}
    }
    
    
    
    /**
     * 
     * @Title: Pay19NetBankList 
     * @Description: 查询支持网银支付的宝付银行
     * @param req
     * @param res
     * @throws
     */
    public void pay19NetBankList(ReqMsg_Bank_Pay19NetBankList req, ResMsg_Bank_Pay19NetBankList res) {
    	List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
    	
    	List<Bs19payBank> list = bankCardService.select19PayNetBankList();
    	for (Bs19payBank bs19payBank : list) {
    		result.add(BeanUtils.transBeanMap(bs19payBank));
		}
    	res.setBankList(result);
    }

    /**
     * 根据支付通道和支付类型查询银行列表
     * @param req   ReqMsg_Bank_ChannelBank
     * @param res   ResMsg_Bank_ChannelBank
     */
    public void channelBank(ReqMsg_Bank_ChannelBank req, ResMsg_Bank_ChannelBank res) {
        List<Pay19BankVO> bankList = bankCardService.queryBankByChannelAndPayType(req.getPayChannel(), req.getPayType());
        res.setBankList(BeanUtils.classToArrayList(bankList));
    }
}
