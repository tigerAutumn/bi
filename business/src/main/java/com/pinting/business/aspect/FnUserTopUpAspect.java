package com.pinting.business.aspect;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.pinting.business.accounting.finance.service.DepUserBalanceBuyService;
import com.pinting.business.accounting.finance.service.UserBalanceBuyService;
import com.pinting.business.accounting.loan.enums.VIPId4PartnerEnum;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsFileSealRelationMapper;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealPdfPathEnum;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.enums.SealType;
import com.pinting.business.hessian.site.message.ReqMsg_RegularBuy_BalanceBuy;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 理财人充值结果切面类
 * Created by babyshark on 2016/9/12.
 */
@Aspect
@Component
@Order(5)
public class FnUserTopUpAspect {
    private Logger log = LoggerFactory.getLogger(FnUserTopUpAspect.class);

    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private LoanRelationshipService loanRelationshipService;
    @Autowired
    private PayOrdersService payOrdersService;
    @Autowired
    private UserBalanceBuyService userBalanceBuyService;
    @Autowired
    private SysConfigService sysConfigService;
    @Autowired
    private DepUserBalanceBuyService depUserBalanceBuyService;
    @Autowired
    private UserService userService;
    @Autowired
    private SignSealService signSealService;
    @Autowired
    private BsFileSealRelationMapper bsFileSealRelationMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;

    /**
     * 充值通知（恒丰快捷+网关充值）
     */
    @Pointcut("execution(public * com.pinting.business.service.common.OrderBusinessService.financeHFTopUp(..))")
    public void notifyHFPointcut(){}

    /**
     * 快捷充值通知（宝付）
     */
    @Pointcut("execution(public * com.pinting.business.service.common.OrderBusinessService.financerQuickPayTopUp(..))")
    public void notifyPointcut(){}

    /**
     * 网银充值通知（宝付）
     */
    @Pointcut("execution(public * com.pinting.business.service.common.OrderBusinessService.financerEBankTopUp(..))")
    public void notifyEBankPointcut(){}

    @After(value = "notifyHFPointcut()")
    public void notifyHFAfter(JoinPoint jp) {
        log.info(">>>恒丰充值通知切面开始<<<");
        try {
            OrderResultInfo resultInfo = (OrderResultInfo) jp.getArgs()[0];
            if(resultInfo.isSuccess())
                superUserAutoAuthHF(resultInfo.getOrderNo());

            // 支付协议签章
            BsPayOrders bsPayOrders = payOrdersService.findOrderByOrderNo(resultInfo.getOrderNo());
            if(null != bsPayOrders) {
                signSeal(bsPayOrders.getUserId(), resultInfo.getOrderNo());
            }

        }catch (Exception e){
            log.info("恒丰充值通知切面异常：", e);
        }
        log.info(">>>恒丰充值通知切面结束<<<");
    }

    @AfterReturning(value = "notifyPointcut()")
    public void notifyAfter(JoinPoint jp) {
        log.info(">>>快捷充值通知切面开始<<<");
        try {
            OrderResultInfo resultInfo = (OrderResultInfo) jp.getArgs()[0];
            if(resultInfo.isSuccess())
                superUserAutoAuth(resultInfo.getOrderNo());
        }catch (Exception e){
            log.info("快捷充值通知切面异常：", e);
        }
        log.info(">>>快捷充值通知切面结束<<<");
    }

    @AfterReturning(value = "notifyEBankPointcut()")
    public void notifyEBankAfter(JoinPoint jp) {
        log.info(">>>网银充值通知切面开始<<<");
        try {
            OrderResultInfo resultInfo = (OrderResultInfo) jp.getArgs()[0];
            if(resultInfo.isSuccess())
                superUserAutoAuth(resultInfo.getOrderNo());
        }catch (Exception e){
            log.info("网银充值通知切面异常：", e);
        }
        log.info(">>>网银充值通知切面结束<<<");
    }

    /**
     * 超级理财人充值自动转购买
     * @param orderNo
     */
    private void superUserAutoAuth(String orderNo){
        log.info(">>>[" + orderNo + "]超级理财人充值自动授权购买开始<<<");
        if(StringUtil.isEmpty(orderNo))
            return;
        // 根据订单号获得用户编号，判断用户编号是否超级理财人
        boolean isSuperUser = false;
        BsPayOrders order = payOrdersService.findOrderByOrderNo(orderNo);
        if(order == null)
            return;
        Integer userId = order.getUserId();
        List<Integer> superUserIds = loanRelationshipService.getSuperUserList();
        if(!CollectionUtils.isEmpty(superUserIds)){
            for (Integer superUserId : superUserIds) {
                if(superUserId.equals(userId)) {
                    isSuperUser = true;
                    break;
                }
            }
        }
        // 针对超级理财人，主动调起超级理财人购买流程
        if(isSuperUser){
            ReqMsg_RegularBuy_BalanceBuy req = new ReqMsg_RegularBuy_BalanceBuy();
            req.setAmount(order.getAmount());
            BsSysConfig config = sysConfigService.findConfigByKey(Constants.SUPER_PRODUCT_ID);
            Integer productId = Integer.valueOf(config.getConfValue());
            req.setProductId(productId);
            req.setUserId(userId);
            userBalanceBuyService.superAuth(req);
        }
        log.info(">>>[" + orderNo + "]超级理财人充值自动授权购买结束<<<");
    }

    /**
     * 超级理财人充值自动转购买
     * @param orderNo
     */
    private void superUserAutoAuthHF(String orderNo){
        log.info(">>>[" + orderNo + "]（存管）超级理财人充值自动授权购买开始<<<");
        if(StringUtil.isEmpty(orderNo))
            return;
        // 根据订单号获得用户编号，判断用户编号是否超级理财人
        BsPayOrders order = payOrdersService.findOrderByOrderNo(orderNo);
        if(order == null)
            return;
        ReqMsg_RegularBuy_BalanceBuy req = new ReqMsg_RegularBuy_BalanceBuy();
        req.setAmount(order.getAmount());

        // 针对超级理财人，主动调起超级理财人购买流程
        if (isSuperUserFree(order.getUserId())) {
        	// 自由资金
            BsSysConfig config = sysConfigService.findConfigByKey(VIPId4PartnerEnum.FREE.getVipProductIdKey());
            Integer productId = Integer.valueOf(config.getConfValue());
            req.setProductId(productId);
            req.setUserId(order.getUserId());
            log.info(">>>[" + orderNo + "]（存管）超级理财人充值自动授权购买，自由资金产品 [ " + productId + " ]<<<");
            depUserBalanceBuyService.buyFixed4Super(req);
		} else if(isSupperUserZan(order.getUserId())) {
            // 赞分期
            BsSysConfig config = sysConfigService.findConfigByKey(Constants.SUPER_PRODUCT_ID);
            Integer productId = Integer.valueOf(config.getConfValue());
            req.setProductId(productId);
            req.setUserId(order.getUserId());
            log.info(">>>[" + orderNo + "]（存管）超级理财人充值自动授权购买，赞分期产品 [ " + productId + " ]<<<");
            depUserBalanceBuyService.buyStage4Super(req);
        } else if(isSupperUserYunSelf(order.getUserId())) {
            // 针对超级理财人，主动调起超级理财人购买流程
            // 云贷存管
            BsSysConfig config = sysConfigService.findConfigByKey(VIPId4PartnerEnum.YUN_DAI_SELF.getVipProductIdKey());
            Integer productId = Integer.valueOf(config.getConfValue());
            req.setProductId(productId);
            req.setUserId(order.getUserId());
            log.info(">>>[" + orderNo + "]（存管）超级理财人充值自动授权购买，云贷自主放款产品 [ " + productId + " ]<<<");
            depUserBalanceBuyService.buyFixed4Super(req);
        } else if(isSuperUserZSD(order.getUserId())) {
            // 针对超级理财人，主动调起超级理财人购买流程
            // 赞时贷存管
            BsSysConfig config = sysConfigService.findConfigByKey(VIPId4PartnerEnum.ZSD.getVipProductIdKey());
            Integer productId = Integer.valueOf(config.getConfValue());
            req.setProductId(productId);
            req.setUserId(order.getUserId());
            log.info(">>>[" + orderNo + "]（存管）超级理财人充值自动授权购买，赞时贷产品 [ " + productId + " ]<<<");
            depUserBalanceBuyService.buyFixed4Super(req);
        } else if(isSuperUser7DAI(order.getUserId())) {
            // 针对超级理财人，主动调起超级理财人购买流程
            // 7贷存管
            BsSysConfig config = sysConfigService.findConfigByKey(VIPId4PartnerEnum.SEVEN_DAI_SELF.getVipProductIdKey());
            Integer productId = Integer.valueOf(config.getConfValue());
            req.setProductId(productId);
            req.setUserId(order.getUserId());
            log.info(">>>[" + orderNo + "]（存管）超级理财人充值自动授权购买，7贷产品 [ " + productId + " ]<<<");
            depUserBalanceBuyService.buyFixed4Super(req);
        }
        log.info(">>>[" + orderNo + "]（存管）超级理财人充值自动授权购买结束<<<");
    }

    /**
     * 赞分期VIP用户
     * @param userId
     * @return
     */
    private boolean isSupperUserZan(Integer userId) {
        boolean isSuperUser = false;
        List<Integer> superUserIds = loanRelationshipService.getSuperUserList();
        if(!CollectionUtils.isEmpty(superUserIds)) {
            for (Integer superUserId : superUserIds) {
                if(superUserId.equals(userId)) {
                    isSuperUser = true;
                    break;
                }
            }
        }
        return isSuperUser;
    }

    /**
     * 云贷自主放款VIP用户
     * @param userId
     * @return
     */
    private boolean isSupperUserYunSelf(Integer userId) {
        BsSysConfig supers = sysConfigService.findConfigByKey(VIPId4PartnerEnum.YUN_DAI_SELF.getVipIdKey());
        return containUserId(userId, supers);

    }

    /**
     * 赞时贷VIP用户
     * @param userId
     * @return
     */
    private boolean isSuperUserZSD(Integer userId) {
        BsSysConfig supers = sysConfigService.findConfigByKey(VIPId4PartnerEnum.ZSD.getVipIdKey());
        return containUserId(userId, supers);
    }

    /**
     * 7贷VIP用户
     * @param userId
     * @return
     */
    private boolean isSuperUser7DAI(Integer userId) {
        BsSysConfig supers = sysConfigService.findConfigByKey(VIPId4PartnerEnum.SEVEN_DAI_SELF.getVipIdKey());
        return containUserId(userId, supers);
    }
    
    /**
     * 自由资金VIP用户
     * @param userId
     * @return
     */
    private boolean isSuperUserFree(Integer userId) {
        BsSysConfig supers = sysConfigService.findConfigByKey(VIPId4PartnerEnum.FREE.getVipIdKey());
        return containUserId(userId, supers);
    }

    /**
     * 配置中是否包含此userId
     * @param userId
     * @param supers
     * @return
     */
    public boolean containUserId(Integer userId, BsSysConfig supers) {
        boolean isSuperUser = false;
        List<Integer> superUserIds = new ArrayList<>();
        if (supers != null) {
            String[] userStr = supers.getConfValue().split(",");
            for (String string : userStr) {
                superUserIds.add(Integer.parseInt(string));
            }
        }
        if(!CollectionUtils.isEmpty(superUserIds)) {
            for (Integer superUserId : superUserIds) {
                if(superUserId.equals(userId)) {
                    isSuperUser = true;
                    break;
                }
            }
        }
        return isSuperUser;
    }

    /**
     * 快捷、网银充值成功，《支付协议》和《授权委托书》协议签章
     * @param userId
     * @param orderNo
     */
    private void signSeal(Integer userId, String orderNo){

        if(null != userId && null != orderNo) {
            // 协议签章
            BsUser user = userService.findUserById(userId);
            // 签章异步处理，保存表信息：Bs_User_Seal_File, Bs_User_Sign_Seal, Bs_File_Seal_Relation
            BsUserSignSeal userSealReq = new BsUserSignSeal();
            userSealReq.setIdCard(user.getIdCard());
            userSealReq.setUserName(user.getUserName());
            userSealReq.setUserId(user.getId());
            BsUserSignSeal signSeal = signSealService.findUserSignSeal(userSealReq);

            // 用户没有签章信息,新增签章信息
            if (signSeal == null) {
                signSeal = userSealReq;
                signSealService.addUserSeal(signSeal);
            }

            String topUpAgreementHtml = GlobEnvUtil.get("cfca.seal.rechargeDelegateAuthorization.pdfSrcHtml") + "?userId=" + user.getId();

            // 新增用户签章文件记录表
            BsUserSealFile sealFile = new BsUserSealFile();
            // 协议编号为充值订单号
            sealFile.setAgreementNo(orderNo);
            sealFile.setSrcAddress(topUpAgreementHtml);
            sealFile.setSealStatus(SealStatus.UNDOWNLOAD.getCode());
            sealFile.setSealType(SealBusiness.RECHARGE_DELEGATE_AUTHORIZATION.getCode());
            sealFile.setUserId(signSeal.getUserId());
            sealFile.setUserSrc(SealPdfPathEnum.BIGANGWAN.getCode());
            sealFile.setRelativeInfo(orderNo);
            sealFile.setCreateTime(new Date());
            bsUserSealFileMapper.insertSelective(sealFile);

            // 新增签章文件与客户签章关系表
            BsFileSealRelation sealRelation = new BsFileSealRelation();
            sealRelation.setSealFileId(sealFile.getId());
            sealRelation.setCreateTime(new Date());
            sealRelation.setUpdateTime(new Date());

            // 客户签章
            sealRelation.setKeywords("签名：" + signSeal.getUserName());
            sealRelation.setContentType(SealType.PERSON.name());
            sealRelation.setUserSealId(signSeal.getId());
            bsFileSealRelationMapper.insertSelective(sealRelation);

            // 币港湾签章
            sealRelation.setKeywords("杭州币港湾科技有限公司（受托人）");
            sealRelation.setContentType(SealType.COMPANY.name());
            sealRelation.setUserSealId(SealPdfPathEnum.BIGANGWAN.getSealId()); // 填写币港湾签章ID
            bsFileSealRelationMapper.insertSelective(sealRelation);

            log.info(">>>签章入redis走定时-file_id:" + sealRelation.getSealFileId());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("file_id", sealRelation.getSealFileId());
            jsClientDaoSupport.rpush(Constants.SIGN_FILE_QUEUE_KEY, JSON.toJSONString(jsonObject));
        }
    }

}
