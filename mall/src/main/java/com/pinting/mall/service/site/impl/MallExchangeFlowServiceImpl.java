package com.pinting.mall.service.site.impl;

import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.mall.dao.*;
import com.pinting.mall.enums.*;
import com.pinting.mall.exception.PTMessageException;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ReqMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCheck;
import com.pinting.mall.hessian.site.message.ResMsg_MallExchangeFlow_ExchangeCommodity;
import com.pinting.mall.model.*;
import com.pinting.mall.service.MallSendWechatService;
import com.pinting.mall.service.site.MallAccountService;
import com.pinting.mall.service.site.MallAddrService;
import com.pinting.mall.service.site.MallExchangeFlowService;
import com.pinting.mall.util.Constants;
import com.pinting.mall.util.RegexpUtils;
import com.pinting.mall.util.Util;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.Date;
import java.util.HashMap;

/**
 * 积分商城商品兑换流程服务
 *
 * @author zousheng
 * @date 2018/5/16 10:49
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Service
public class MallExchangeFlowServiceImpl implements MallExchangeFlowService {

    private Logger logger = LoggerFactory.getLogger(MallExchangeFlowServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);

    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private MallCommodityInfoMapper commInfoMapper;
    @Autowired
    private MallAccountService mallAccountService;
    @Autowired
    private MallExchangeOrdersMapper mallExchangeOrdersMapper;
    @Autowired
    private MallAddrService mallAddrService;
    @Autowired
    private MallSendCommodityMapper mallSendCommodityMapper;
    @Autowired
    private MallAccountMapper accountMapper;
    @Autowired
    private MallAccountJnlMapper accountJnlMapper;
    @Autowired
    private MallSendWechatService sendWechatService;
    
    @Override
    public void exchangePreCheck(ReqMsg_MallExchangeFlow_ExchangeCheck req, ResMsg_MallExchangeFlow_ExchangeCheck res) {

        MallCommodityInfoWithBLOBs mallDetail = checkCommodityInfo(req.getId());

        checkAccountAvaliableBalance(mallDetail, req.getUserId());
        res.setCheckResult(true);

        // 实体商品，查询用户收货地址
        if (MallPropertyEnum.REAL.getCode().equalsIgnoreCase(mallDetail.getCommProperty())) {
            res.setAddrShow(true);
        } else {
            res.setAddrShow(false);
        }
    }

    /**
     * 校验商品信息（已下架，已售罄）
     *
     * @param id
     * @return
     */
    private MallCommodityInfoWithBLOBs checkCommodityInfo(Integer id) {
        MallCommodityInfoWithBLOBs mallDetail = commInfoMapper.selectByPrimaryKey(id);
        if (mallDetail == null) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "兑换商品不存在");
        }
        // 校验商品已下架
        if (MallInfoStatusEnum.SOLD_OUT.getCode().equals(mallDetail.getStatus())) {
            throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "兑换商品已下架");
        }
        // 校验商品剩余库存已为0
        if (mallDetail.getLeftCount() <= 0) {
            throw new PTMessageException(PTMessageEnum.ORDER_COMMODITY_SOLD_OUT);
        }
        return mallDetail;
    }

    /**
     * 校验用户积分账户（可用积分是否足够）
     *
     * @param mallDetail
     * @param userId
     * @return
     */
    private MallAccount checkAccountAvaliableBalance(MallCommodityInfoWithBLOBs mallDetail, Integer userId) {
        MallAccount mallAccount = mallAccountService.getAccountByUserId(userId);
        if (mallAccount == null) {
            throw new PTMessageException(PTMessageEnum.ORDER_USER_INTEGRAL_LESS);
        }
        // 校验用户积分是否足够
        if (mallAccount.getAvaliableBalance().compareTo(mallDetail.getPoints()) < 0) {
            throw new PTMessageException(PTMessageEnum.ORDER_USER_INTEGRAL_LESS);
        }
        return mallAccount;
    }

    @Override
    public void exchangeCommodity(final ReqMsg_MallExchangeFlow_ExchangeCommodity req, ResMsg_MallExchangeFlow_ExchangeCommodity res) {

        /**
         * 1.商品兑换业务锁 + 商品id
         * 2.校验商品信息（已下架，已售完）
         * 校验用户收货地址是否已填写
         * 校验用户积分账户（可用积分是否足够）
         * 3.事物开始
         * 4.新增兑换订单表记录（mall_exchange_orders）
         * 新增用户收货地址信息（mall_consignee_address_info）,如果已有收货地址变更，则原有收货地址设置删除
         * 新增发货记录表（mall_send_commodity）
         * 更新用户积分余额表（mall_account）- 兑换积分
         * 新增用户积分余额流水表（mall_account_jnl）
         * 更新商品信息（mall_commodity_info）
         * 5.事物结束
         * 6.释放同步锁
         **/
        try {
            //1.商品兑换业务锁 + 商品id
            jsClientDaoSupport.lock(RedisLockEnum.MALL_LOCK_EXCHANGE_COMMODITY.getKey() + req.getId());

            // 2.校验商品信息（已下架，已售完）
            final MallCommodityInfoWithBLOBs mallDetail = checkCommodityInfo(req.getId());

            // 2.校验用户收货地址是否已填写
            if (MallPropertyEnum.REAL.getCode().equalsIgnoreCase(mallDetail.getCommProperty())) {
                if (StringUtils.isBlank(req.getRecName())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人姓名不能为空！");
                }
                if (req.getRecName().length() > 8) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人姓名过长");
                }
                if (RegexpUtils.notChineseOrLetter(req.getRecName())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人姓名格式错误");
                }
                if (StringUtils.isBlank(req.getRecMobile())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人手机号不能为空！");
                }
                if (RegexpUtils.notMobile(req.getRecMobile())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人手机号格式错误");
                }
                if (StringUtils.isBlank(req.getRecAdress())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人地址不能为空！");
                }
                if (StringUtils.isBlank(req.getRecAdressDetail())) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人详细地址不能为空！");
                }

                String recAdressDetail = loadXSS(req.getRecAdressDetail());
                if (recAdressDetail.length() > 50) {
                    throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "收货人详细地址过长");
                }
                req.setRecAdressDetail(recAdressDetail);
            }

            // 2.校验用户积分账户（可用积分是否足够）
            final MallAccount mallAccount = checkAccountAvaliableBalance(mallDetail, req.getUserId());
            final String orderNo = Util.generateOrderNo(req.getUserId());

            // 3.事物开始
            transactionTemplate.execute(new TransactionCallbackWithoutResult() {
                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    // 4.新增兑换订单表记录（mall_exchange_orders）
                    MallExchangeOrders mallExchangeOrders = new MallExchangeOrders();
                    mallExchangeOrders.setOrderNo(orderNo);
                    mallExchangeOrders.setCommId(req.getId());
                    mallExchangeOrders.setUserId(req.getUserId());
                    mallExchangeOrders.setBuyNum(1);
                    mallExchangeOrders.setPayPoints(MoneyUtil.multiply(mallDetail.getPoints(), mallExchangeOrders.getBuyNum()).longValue());
                    mallExchangeOrders.setOrderStatus(MallExchangeOrderStatusEnum.SUCCESS.getCode());
                    mallExchangeOrders.setCreateTime(new Date());
                    mallExchangeOrders.setUpdateTime(new Date());
                    mallExchangeOrdersMapper.insertSelective(mallExchangeOrders);

                    Integer receiptId = null;
                    // 4.新增用户收货地址信息（mall_consignee_address_info）,如果已有收货地址变更，则原有收货地址设置删除，新增收货地址
                    if (MallPropertyEnum.REAL.getCode().equalsIgnoreCase(mallDetail.getCommProperty())) {
                        MallConsigneeAddressInfo addressInfo = mallAddrService.saveAddressInfo(req.getUserId(), req.getRecName(), req.getRecMobile(), req.getRecAdress(), req.getRecAdressDetail());
                        receiptId = addressInfo.getId();
                    }

                    // 4.新增发货记录表（mall_send_commodity）
                    MallSendCommodity mallSendCommodity = new MallSendCommodity();
                    mallSendCommodity.setOrderId(mallExchangeOrders.getId());
                    mallSendCommodity.setReceiptId(receiptId);
                    mallSendCommodity.setStatus(MallSendStatusEnum.STAY_SEND.getCode());
                    mallSendCommodity.setCreateTime(new Date());
                    mallSendCommodity.setUpdateTime(new Date());
                    mallSendCommodityMapper.insertSelective(mallSendCommodity);

                    // 4.更新用户积分余额表（mall_account）- 兑换积分
                    MallAccount pointsAcct = accountMapper.selectMallAccountByIdForLock(mallAccount.getId());
                    // 校验用户积分是否足够
                    if (pointsAcct.getAvaliableBalance().compareTo(mallDetail.getPoints()) < 0) {
                        throw new PTMessageException(PTMessageEnum.ORDER_USER_INTEGRAL_LESS);
                    }
                    MallAccount tmpPointsAcct = new MallAccount();
                    tmpPointsAcct.setId(mallAccount.getId());
                    tmpPointsAcct.setBalance(MoneyUtil.subtract(pointsAcct.getBalance(), mallExchangeOrders.getPayPoints()).longValue());
                    tmpPointsAcct.setAvaliableBalance(MoneyUtil.subtract(pointsAcct.getAvaliableBalance(), mallExchangeOrders.getPayPoints()).longValue());
                    tmpPointsAcct.setTotalUsedPoints(MoneyUtil.add(pointsAcct.getTotalUsedPoints(), mallExchangeOrders.getPayPoints()).longValue());
                    accountMapper.updateByPrimaryKeySelective(tmpPointsAcct);
                    // 新增用户积分余额流水表（mall_account_jnl）
                    MallAccountJnl accountJnl = new MallAccountJnl();
                    accountJnl.setUserId(pointsAcct.getUserId());
                    accountJnl.setAccountId(pointsAcct.getId());
                    accountJnl.setBeforeBalance(pointsAcct.getBalance());
                    accountJnl.setAfterBalance(tmpPointsAcct.getBalance());
                    accountJnl.setBeforeAvaliableBalance(pointsAcct.getAvaliableBalance());
                    accountJnl.setAfterAvaliableBalance(tmpPointsAcct.getAvaliableBalance());
                    accountJnl.setPoints(-mallExchangeOrders.getPayPoints());
                    accountJnl.setTransType(MallAcountTypeEnum.MALL_EXCHANGE.getCode());
                    accountJnl.setTransName(MallAcountTypeEnum.MALL_EXCHANGE.getMessage());
                    accountJnl.setTransTime(new Date());
                    accountJnl.setNote(MallAcountTypeEnum.MALL_EXCHANGE.getMessage());
                    accountJnl.setCreateTime(new Date());
                    accountJnl.setUpdateTime(new Date());
                    accountJnlMapper.insertSelective(accountJnl);

                    // 4. 更新商品信息（mall_commodity_info）
                    MallCommodityInfo commodityInfo = commInfoMapper.selectMallCommodityByIdForLock(mallDetail.getId());
                    // 校验商品剩余库存已为0
                    if (commodityInfo.getLeftCount() <= 0) {
                        throw new PTMessageException(PTMessageEnum.ORDER_COMMODITY_SOLD_OUT);
                    }
                    MallCommodityInfoWithBLOBs commodityInfoTemp = new MallCommodityInfoWithBLOBs();
                    commodityInfoTemp.setId(commodityInfo.getId());
                    commodityInfoTemp.setLeftCount(MoneyUtil.subtract(commodityInfo.getLeftCount(), mallExchangeOrders.getBuyNum()).intValue());
                    commodityInfoTemp.setSoldCount(MoneyUtil.add(commodityInfo.getSoldCount(), mallExchangeOrders.getBuyNum()).intValue());
                    commodityInfoTemp.setUpdateTime(new Date());
                    commInfoMapper.updateByPrimaryKeySelective(commodityInfoTemp);
                    //积分消费微信提醒
                    sendWechatService.mallPointsConsume(req.getUserId(), commodityInfo.getCommName(), "-"+commodityInfo.getPoints(), "币港湾", accountJnl.getTransTime());
                }
            });
            res.setExchangeResult(true);

            HashMap<String, Object> extendMap = new HashMap();
            extendMap.put("orderNo", orderNo);
            res.setExtendMap(extendMap);
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.MALL_LOCK_EXCHANGE_COMMODITY.getKey() + req.getId());
        }
    }

    /**
     * 数据经过XSS防注入过滤，部分符合已转换
     *
     * @param value
     * @return
     */
    private String loadXSS(String value) {
        value = value.replaceAll("&lt;", "<").replaceAll("&gt;", ">");
        value = value.replaceAll("&#40;", "\\(").replaceAll("&#41;", "\\)");
        value = value.replaceAll("&#39;", "'");
        return value;
    }
    
}
