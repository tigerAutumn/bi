package com.pinting.business.accounting.finance.service.impl;

import com.pinting.business.accounting.finance.model.SysActTransResultInfo;
import com.pinting.business.accounting.finance.model.SysActTransSendInfo;
import com.pinting.business.accounting.finance.service.SysProductBuyService;
import com.pinting.business.accounting.finance.service.impl.process.SysBuyProductSendProcess;
import com.pinting.business.accounting.service.BsSysSubAccountService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.OrderResultInfo;
import com.pinting.business.model.vo.BsSubAc4BatchBuyVO;
import com.pinting.business.model.vo.BsSubAc4InterestVO;
import com.pinting.business.service.common.OrderBusinessService;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.TransferEnv;
import com.pinting.business.util.TransferEnvUtil;
import com.pinting.business.util.Util;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyPayment_SysBuyProductNotice;
import com.pinting.gateway.hessian.message.dafy.model.Products;
import com.pinting.gateway.hessian.message.pay19.enums.AcctTransTradeResult;
import com.pinting.gateway.out.service.BaoFooTransportService;
import com.pinting.gateway.out.service.DafyTransportService;
import com.pinting.gateway.out.service.Pay19TransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.util.*;

/**
 * Created by babyshark on 2016/9/9.
 */
@Service
public class SysProductBuyServiceImpl implements SysProductBuyService {
    private Logger log = LoggerFactory.getLogger(SysProductBuyServiceImpl.class);
    private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private Pay19TransportService pay19TransportService;
    @Autowired
    private DafyTransportService dafyTransportService;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private BsPayOrdersMapper bsPayOrdersMapper;
    @Autowired
    private BsPayOrdersJnlMapper bsPayOrdersJnlMapper;
    @Autowired
    private BsBatchBuyMapper bsBatchBuyMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsBatchBuyDetailMapper bsBatchBuyDetailMapper;
    @Autowired
    private BsSysSubAccountMapper sysAccountMapper;
    @Autowired
    private BsSysAccountJnlMapper sysAccountJnlMapper;
    @Autowired
    private BsTermProductCodeMapper bsTermProductCodeMapper;
    @Autowired
    private BaoFooTransportService baoFooTransportService;
    @Autowired
    private OrderBusinessService orderBusinessService;
    @Autowired
    private BsSysSubAccountService bsSysSubAccountService;
    @Autowired
    private BsSysConfigMapper bsSysConfigMapper;
    /**
     * 准备并记录某合作方昨天所有待转账的REG产品转账数据
     *
     * @param propertySymbol 资金接收方
     */
    @Override
    public List<BsSubAc4BatchBuyVO> preparePartnerDailyProduct(final String propertySymbol) {
        try {
            //统计当天 各产品的 购买总额 （已对账一致的产品）
            Date currentDay = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
            List<BsSubAc4BatchBuyVO> initProducts = bsSubAccountMapper.selectBsSubAc4BatchBuy(currentDay,propertySymbol);//初始系统购买列表
            final Map<BsSubAc4BatchBuyVO, List<BsSubAc4BatchBuyVO>> productMap = new HashMap<>();//最终系统购买列表及明细
            if(CollectionUtils.isEmpty(initProducts))
                return null;
            BsSysConfig ceilingConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.BAOFOO_TRANS_CEILING);
            BigDecimal ceilingAmount = MoneyUtil.multiply(ceilingConfig!=null?ceilingConfig.getConfValue():"10000000", "0.9");
            for (BsSubAc4BatchBuyVO initBuyVO : initProducts){
                String code = initBuyVO.getProductCode();
                Double initAmount = initBuyVO.getProductAmount();
                //查询当天该code购买的子账户明细
                List<BsSubAc4BatchBuyVO> productDetails = bsSubAccountMapper.selectBsSubAcDetail4BatchBuy(currentDay, propertySymbol, code);
                //小于等于1000w时不处理，直接添加到最终系统购买列表
                if(initAmount.compareTo(ceilingAmount.doubleValue()) <= 0) {
                    productMap.put(initBuyVO, productDetails);
                //大于1000w时，拆分
                } else {
                    //遍历明细，获得拆分后的购买列表
                    BsSubAc4BatchBuyVO tempBuyVO = null;
                    List<BsSubAc4BatchBuyVO> tempProductDetails = null;
                    for (BsSubAc4BatchBuyVO detail : productDetails){
                        if (tempBuyVO == null){
                            tempBuyVO = new BsSubAc4BatchBuyVO();
                            tempProductDetails = new ArrayList<>();
                            tempBuyVO.setProductCode(detail.getProductCode());
                            tempBuyVO.setInterestBeginDate(detail.getInterestBeginDate());
                            tempBuyVO.setLastFinishInterestDate(detail.getLastFinishInterestDate());
                            tempBuyVO.setProductAmount(detail.getProductAmount());
                            tempBuyVO.setTerm(detail.getTerm());
                            tempProductDetails.add(detail);
                            productMap.put(tempBuyVO, tempProductDetails);
                            continue;
                        }
                        Double nextAmount = MoneyUtil.add(tempBuyVO.getProductAmount(), detail.getProductAmount()).doubleValue();
                        if(nextAmount.compareTo(ceilingAmount.doubleValue()) <= 0){
                            tempBuyVO.setProductAmount(nextAmount.doubleValue());
                            tempProductDetails.add(detail);
                            productMap.put(tempBuyVO, tempProductDetails);
                        }else {
                            productMap.put(tempBuyVO, tempProductDetails);
                            tempBuyVO = new BsSubAc4BatchBuyVO();
                            tempProductDetails = new ArrayList<>();
                            tempBuyVO.setProductCode(detail.getProductCode());
                            tempBuyVO.setInterestBeginDate(detail.getInterestBeginDate());
                            tempBuyVO.setLastFinishInterestDate(detail.getLastFinishInterestDate());
                            tempBuyVO.setProductAmount(detail.getProductAmount());
                            tempBuyVO.setTerm(detail.getTerm());
                            tempProductDetails.add(detail);
                            productMap.put(tempBuyVO, tempProductDetails);
                        }
                    }
                }
            }

            if(!CollectionUtils.isEmpty(productMap)){
                log.info("=========定时任务{系统钱包转账-理财购买}该天产品分组后统计后，共计["+productMap.size()+"]笔购买需记录到[BsBatchBuy]=========");
                final List<BsSubAc4BatchBuyVO> products = new ArrayList<>();
                transactionTemplate.execute(new TransactionCallbackWithoutResult(){
                    @Override
                    protected void doInTransactionWithoutResult(TransactionStatus status) {
                        //记录到理财购买产品表，及购买明细表
                        int i = 0;
                        BigDecimal totalAmount = new BigDecimal(0);
                        for (Map.Entry<BsSubAc4BatchBuyVO, List<BsSubAc4BatchBuyVO>> entry : productMap.entrySet()) {
                            i++;
                            BsSubAc4BatchBuyVO buyVO = entry.getKey();
                            List<BsSubAc4BatchBuyVO> details = entry.getValue();
                            totalAmount = totalAmount.add(new BigDecimal(buyVO.getProductAmount()));
                            log.info("=========定时任务{系统钱包转账-理财购买}记录到[BsBatchBuy]第"+i+"笔："+buyVO+"=========");
                            //插入理财购买产品表
                            BsBatchBuy batchBuy = new BsBatchBuy();
                            batchBuy.setProductCode(buyVO.getProductCode());
                            batchBuy.setAmount(buyVO.getProductAmount());
                            batchBuy.setSendAmount(buyVO.getProductAmount());
                            batchBuy.setDafyRate(buyVO.getSysReturnRate());
                            batchBuy.setStatus(Constants.BATCHBUY_STATUS_INIT);
                            batchBuy.setExpectTime(buyVO.getLastFinishInterestDate());
                            batchBuy.setFinancingDate(DateUtil.addDays(buyVO.getInterestBeginDate(), -1));
                            batchBuy.setExpectTime(buyVO.getLastFinishInterestDate());
                            batchBuy.setPay19OrderNo(null);
                            batchBuy.setSendTime(new Date());
                            batchBuy.setCreateTime(new Date());
                            batchBuy.setUpdateTime(new Date());
                            batchBuy.setPropertySymbol(propertySymbol);
                            bsBatchBuyMapper.insertSelective(batchBuy);
                            BsBatchBuy batchBuyTemp = new BsBatchBuy();
                            batchBuyTemp.setId(batchBuy.getId());
                            batchBuyTemp.setSendBatchId(Util.generateOrderNo4Dafy(String.valueOf(batchBuy.getId())));//购买理财订单号（批次号）
                            bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuyTemp);
                            buyVO.setBatchBuyId(batchBuy.getId());
                            products.add(buyVO);
                            //明细插入
                            for (BsSubAc4BatchBuyVO detail : details){
                                BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
                                detailExample.createCriteria().andSubAccountIdEqualTo(detail.getSubAccountId());
                                List<BsBatchBuyDetail> localDetails = bsBatchBuyDetailMapper.selectByExample(detailExample);
                                //已存在的用户产品户不再插入
                                if(localDetails!=null && localDetails.size()>0){
                                    continue;
                                }
                                BsBatchBuyDetail tempDetail = new BsBatchBuyDetail();
                                tempDetail.setBatchId(batchBuy.getId());
                                tempDetail.setCreateTime(new Date());
                                tempDetail.setPrincipal(detail.getProductAmount());
                                tempDetail.setRate(detail.getSysReturnRate());
                                tempDetail.setSubAccountId(detail.getSubAccountId());
                                tempDetail.setUpdateTime(new Date());
                                tempDetail.setUserId(detail.getUserId());
                                tempDetail.setReturnStatus(Constants.RETURN_STATUS_NOT);
                                bsBatchBuyDetailMapper.insertSelective(tempDetail);
                            }
                        }
                        log.info("=========定时任务{系统钱包转账-理财购买}总计转账金额：["+totalAmount+"]=========");
                    }
                });
                return products;
            }else{
                log.info("=========定时任务{系统钱包转账-理财购买}无需转账=========");
            }
        } catch (Exception e) {
            log.error("=========定时任务{系统钱包转账-理财购买}失败=========", e);
            //告警
            specialJnlService.warn4Fail(null, "定时任务{系统钱包转账-理财购买}转账申请异常："+ StringUtil.left(e.getMessage(), 20),
                    null,"系统钱包转账-理财购买",false);
        }
        return null;
    }

    /**
     * 准备并记录单笔REG产品转账数据
     *
     * @param subAccountId 待转账的 产品户编号
     */
    @Override
    public SysActTransSendInfo prepareSingleProduct(Integer subAccountId) {
        log.info("========={人工发起单笔产品打款}开始=========");
        try {
            BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
            detailExample.createCriteria().andSubAccountIdEqualTo(subAccountId);
            List<BsBatchBuyDetail> details = bsBatchBuyDetailMapper.selectByExample(detailExample);
            if(details!=null && details.size()>0){
                throw new PTMessageException(PTMessageEnum.SYS_BUY_ACCTTRANS_IS_SENDED);
            }
            BsSubAccountExample example = new BsSubAccountExample();
            example.createCriteria().andIdEqualTo(subAccountId).andCheckStatusEqualTo(Constants.CHECK_ACCOUNT_STATUS_SUCCESS);
            List<BsSubAccount> products = bsSubAccountMapper.selectByExample(example);
            if(products!=null && products.size()>0){
                BsSubAccount productAcct = products.get(0);
                //购买总金额
                Double totalAmount = productAcct.getBalance();
                log.info("========={人工发起单笔产品打款}用户产品户编号["+subAccountId+"]打款:"+totalAmount+"元=========");

                //查询购买的产品方
                BsTermProductCodeExample example1=new BsTermProductCodeExample();
                example1.createCriteria().andCodeEqualTo(productAcct.getProductCode());
                BsTermProductCode bsTermProductCode=bsTermProductCodeMapper.selectByExample(example1).get(0);
                String orderNo="";
                //订单号
                if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI.equals(bsTermProductCode.getPropertySymbol())) {
                    orderNo= Util.generateSysOrderNo("YTS");//19付转账云贷订单号
                }else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI.equals(bsTermProductCode.getPropertySymbol())){
                    orderNo= Util.generateSysOrderNo("7TS");//19付转账7贷订单号
                }

                SysActTransSendInfo req = new SysActTransSendInfo();
                //记录到理财购买产品表
                BsBatchBuy batchBuy = new BsBatchBuy();
                batchBuy.setProductCode(productAcct.getProductCode());
                batchBuy.setAmount(totalAmount);
                batchBuy.setSendAmount(totalAmount);
                batchBuy.setDafyRate(productAcct.getProductRate());
                batchBuy.setStatus(Constants.BATCHBUY_STATUS_INIT);
                batchBuy.setExpectTime(productAcct.getLastFinishInterestDate());
                batchBuy.setFinancingDate(DateUtil.addDays(productAcct.getInterestBeginDate(), -1));
                batchBuy.setExpectTime(productAcct.getLastFinishInterestDate());
                batchBuy.setPay19OrderNo(orderNo);
                batchBuy.setSendTime(new Date());
                batchBuy.setCreateTime(new Date());
                batchBuy.setUpdateTime(new Date());
                batchBuy.setPropertySymbol(bsTermProductCode.getPropertySymbol());
                bsBatchBuyMapper.insertSelective(batchBuy);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setId(batchBuy.getId());
                batchBuyTemp.setSendBatchId(Util.generateOrderNo4Dafy(String.valueOf(batchBuy.getId())));//购买理财订单号（批次号）
                bsBatchBuyMapper.updateByPrimaryKeySelective(batchBuyTemp);

                //组织转账数据
                req.setTransNo(orderNo);
                req.setTransMoney(totalAmount);
                req.setPropertySymbol(bsTermProductCode.getPropertySymbol());
                //扩展参数 对应的用户产品户编号
                HashMap<String, Object> extendMap = new HashMap<String, Object>();
                extendMap.put("subAccountId", subAccountId);
                req.setExtendMap(extendMap);

                return req;

            }else{
                log.info("========={人工发起单笔产品打款}找不到该产品，或该产品对账标志未满足=========");
                throw new PTMessageException(PTMessageEnum.SYS_BUY_ACCTTRANS_NOT_FOUND);
            }
        } catch (Exception e) {
            log.error("========={人工发起单笔产品打款}失败=========", e);
            //告警
            specialJnlService.warn4Fail(null, "{人工发起单笔产品打款}转账申请异常："+StringUtil.left(e.getMessage(), 20),
                    null,"人工发起单笔产品打款",true);
            throw new PTMessageException(PTMessageEnum.SYS_BUY_ACCTTRANS_ERROR, StringUtil.left(e.getMessage(), 20));
        }
    }

    /**
     * 转账给合作方
     *
     * @param req
     */
    @Override
    public void trans2Partner(SysActTransSendInfo req) {
        //订单表插入
        BsPayOrders order = new BsPayOrders();
        Date now = new Date();
        order.setAccountType(Constants.ORDER_ACCOUNT_TYPE_SYS);
        order.setAmount(req.getTransMoney());
        order.setChannel(Constants.CHANNEL_BAOFOO);
        order.setChannelTransType(Constants.CHANNEL_TRS_TRANSFER);
        order.setCreateTime(now);
        order.setMoneyType(Constants.ORDER_CURRENCY_CNY);
        order.setOrderNo(req.getTransNo());
        order.setStatus(Constants.ORDER_STATUS_CREATE);
        order.setTransType(Constants.TRANS_SYS_BUY_DAFY);
        order.setUpdateTime(now);

        TransferEnv transferEnv= TransferEnvUtil.transferEnvMap.get(req.getPropertySymbol());
        //订单用userName来记录转账给哪方借贷公司
        order.setUserName(transferEnv.getTransTarget());
        if(req.getExtendMap()!=null){
            Object subAccountId = req.getExtendMap().get("subAccountId");
            if(subAccountId != null){
                order.setSubAccountId((Integer) subAccountId);
            }
        }
        bsPayOrdersMapper.insertSelective(order);
        //订单明细表插入
        BsPayOrdersJnl orderJnl = new BsPayOrdersJnl();
        orderJnl.setCreateTime(now);
        orderJnl.setOrderId(order.getId());
        orderJnl.setOrderNo(order.getOrderNo());
        orderJnl.setSubAccountCode(transferEnv.getSysSubAccountREG());
        orderJnl.setSysTime(now);
        orderJnl.setTransAmount(req.getTransMoney());
        orderJnl.setTransCode(Constants.TRANS_SYS_BUY_DAFY);
        bsPayOrdersJnlMapper.insertSelective(orderJnl);

        //发起宝付钱包转账
        B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans acctTransReq = new B2GReqMsg_BaoFooQuickPay_Pay4OnlineTrans();
//        acctTransReq.setTo_acc_name(transferEnv.getToAcctName());
//        acctTransReq.setTo_acc_no(transferEnv.getAccountTo());
//        acctTransReq.setTo_member_id(transferEnv.getMemberIdTo());
        acctTransReq.setTrans_money(String.valueOf(req.getTransMoney()));
        acctTransReq.setTrans_no(req.getTransNo());
        acctTransReq.setTransSummary("理财购买");
        acctTransReq.setPropertySymbol(req.getPropertySymbol());
        B2GResMsg_BaoFooQuickPay_Pay4OnlineTrans res = null;
        try {
            res = baoFooTransportService.pay4OnlineTrans(acctTransReq);
        } catch (Exception e) {
            //理财购买产品表状态改为 打款给19付失败
            BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
            List<String> statuss = new ArrayList<String>();
            statuss.add(Constants.BATCHBUY_STATUS_INIT);
            statuss.add(Constants.BATCHBUY_STATUS_19_FAIL);
            batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getTransNo()).andStatusIn(statuss);
            BsBatchBuy batchBuyTemp = new BsBatchBuy();
            batchBuyTemp.setPay19OrderNo(req.getTransNo());
            batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_19_FAIL);
            batchBuyTemp.setPay19ReturnCode(null);
            batchBuyTemp.setPay19ReturnMsg("通讯失败");
            batchBuyTemp.setUpdateTime(new Date());
            bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            //更新订单表，记录订单流水表
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            updateOrder.setReturnMsg("通讯失败");
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(order.getId());
            insertOrderJnl.setOrderNo(order.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(Double.valueOf(req.getTransMoney()));
            insertOrderJnl.setTransCode(Constants.TRANS_SYS_BUY_DAFY);
            insertOrderJnl.setReturnCode(ConstantUtil.BSRESCODE_FAIL);
            insertOrderJnl.setReturnMsg("通讯失败");
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-理财购买}订单号["+order.getOrderNo()+"]转账申请通讯异常",
                    order.getOrderNo(),"系统钱包转账-理财购买",false);
            return;
        }

        //转账请求成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
            if(Constants.BAOFOO_ONLINE_TRANS_STATUS_PAYING.equals(res.getState())){
                //转账中
                log.info("========={系统钱包转账-理财购买}转账申请提交成功或处理中=========");
                //更新订单表
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_PAYING);
                updateOrder.setReturnCode(res.getState());
                updateOrder.setReturnMsg(res.getResMsg());
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                //理财购买产品表状态改为 打款给19付处理中
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                List<String> statuss = new ArrayList<String>();
                statuss.add(Constants.BATCHBUY_STATUS_INIT);
                statuss.add(Constants.BATCHBUY_STATUS_19_FAIL);
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getTransNo()).andStatusIn(statuss);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setPay19OrderNo(req.getTransNo());
                batchBuyTemp.setPay19MpOrderNo(res.getTrans_orderid());
                batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_19_PROCCESS);
                batchBuyTemp.setPay19ReturnCode(res.getState());
                batchBuyTemp.setPay19ReturnMsg(res.getResMsg());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            }else if(Constants.BAOFOO_ONLINE_TRANS_STATUS_SUCCESS.equals(res.getState())){
               
            	 BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                 List<String> statuss = new ArrayList<String>();
                 statuss.add(Constants.BATCHBUY_STATUS_INIT);
                 statuss.add(Constants.BATCHBUY_STATUS_19_FAIL);
                 batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getTransNo()).andStatusIn(statuss);
                 BsBatchBuy batchBuyTemp = new BsBatchBuy();
                 batchBuyTemp.setPay19OrderNo(req.getTransNo());
                 batchBuyTemp.setPay19MpOrderNo(res.getTrans_orderid());
                 bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            	
            	//转账成功
                OrderResultInfo resultInfo = new OrderResultInfo();
                resultInfo.setResMsg(res.getResMsg());
                resultInfo.setFinishTime(new Date());//宝付同步响应结果无完成时间字段，此处直接取本地服务时间
                resultInfo.setPayOrderNo(res.getTrans_orderid());
                resultInfo.setAmount(Double.valueOf(res.getTrans_money()));
                resultInfo.setOrderNo(res.getTrans_no());
                resultInfo.setReturnCode(res.getState());
                resultInfo.setSuccess(true);
                orderBusinessService.sysRegTrans(resultInfo);

            }else {
                //转账失败 或 转账退款
                log.info("=========定时任务{系统钱包转账-理财购买}转账申请提交失败=========");
                //理财购买产品表状态改为 打款给19付失败
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                List<String> statuss = new ArrayList<String>();
                statuss.add(Constants.BATCHBUY_STATUS_INIT);
                statuss.add(Constants.BATCHBUY_STATUS_19_FAIL);
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getTransNo()).andStatusIn(statuss);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setPay19OrderNo(req.getTransNo());
                batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_19_FAIL);
                batchBuyTemp.setPay19ReturnCode(res.getState());
                batchBuyTemp.setPay19ReturnMsg(res.getResMsg());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
                //更新订单表，记录订单流水表
                BsPayOrders updateOrder = new BsPayOrders();
                updateOrder.setId(order.getId());
                updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
                updateOrder.setReturnCode(res.getState());
                updateOrder.setReturnMsg(res.getResMsg());
                updateOrder.setUpdateTime(new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
                BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(Double.valueOf(req.getTransMoney()));
                insertOrderJnl.setTransCode(Constants.TRANS_SYS_BUY_DAFY);
                insertOrderJnl.setReturnCode(res.getState());
                insertOrderJnl.setReturnMsg(res.getResMsg());
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                //告警
                specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-理财购买}订单号["+order.getOrderNo()+"]转账申请提交失败",
                        order.getOrderNo(),"系统钱包转账-理财购买",false);

            }
        }
        //转账请求失败
        else{
            log.info("========={系统钱包转账-理财购买}转账请求失败=========");
            //理财购买产品表状态改为 打款给19付失败
            BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
            List<String> statuss = new ArrayList<String>();
            statuss.add(Constants.BATCHBUY_STATUS_INIT);
            statuss.add(Constants.BATCHBUY_STATUS_19_FAIL);
            batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getTransNo()).andStatusIn(statuss);
            BsBatchBuy batchBuyTemp = new BsBatchBuy();
            batchBuyTemp.setPay19OrderNo(req.getTransNo());
            batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_19_FAIL);

            String thirdReturnCode = Util.getThirdReturnCode(res);

            batchBuyTemp.setPay19ReturnCode(thirdReturnCode);
            batchBuyTemp.setPay19ReturnMsg(res.getResMsg());
            batchBuyTemp.setUpdateTime(new Date());
            bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            //更新订单表，记录订单流水表
            BsPayOrders updateOrder = new BsPayOrders();
            updateOrder.setId(order.getId());
            updateOrder.setStatus(Constants.ORDER_STATUS_FAIL);
            updateOrder.setReturnCode(thirdReturnCode);
            updateOrder.setReturnMsg(res.getResMsg());
            updateOrder.setUpdateTime(new Date());
            bsPayOrdersMapper.updateByPrimaryKeySelective(updateOrder);
            BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
            insertOrderJnl.setCreateTime(new Date());
            insertOrderJnl.setOrderId(order.getId());
            insertOrderJnl.setOrderNo(order.getOrderNo());
            insertOrderJnl.setSubAccountCode(transferEnv.getTransTarget());
            insertOrderJnl.setSysTime(new Date());
            insertOrderJnl.setTransAmount(Double.valueOf(req.getTransMoney()));
            insertOrderJnl.setTransCode(Constants.TRANS_SYS_BUY_DAFY);
            insertOrderJnl.setReturnCode(thirdReturnCode);
            insertOrderJnl.setReturnMsg(res.getResMsg());
            bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

            //告警
            specialJnlService.warn4Fail(order.getAmount(), "{系统钱包转账-理财购买}订单号["+order.getOrderNo()+"]转账申请请求失败",
                    order.getOrderNo(),"系统钱包转账-理财购买",false);

        }

    }

    /**
     * 转账结果通知处理
     *
     * @param req
     */
    @Override
    @Transactional(noRollbackFor=PTMessageException.class)
    public void notifyTrans2PartnerResult(SysActTransResultInfo req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_TRANS2DAFY_RESULT.getKey());
            log.info("========={打款给达飞结果通知}处理开始=========");
            String orderNo = req.getOrderId();
            String mpOrderNo = req.getMpOrderId();
            AcctTransTradeResult tradeResult = req.getTradeResult();
            Double amount = req.getOrderAmount();
            Date finTime = req.getFinTime();
            String retCode = req.getRetCode();

            //订单表校验
            BsPayOrdersExample example = new BsPayOrdersExample();
            example.createCriteria().andOrderNoEqualTo(orderNo);
            List<BsPayOrders> orderList = bsPayOrdersMapper.selectByExample(example);
            BsPayOrders order = orderList.get(0);
            if(order == null){//查无此订单
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }
            if(order.getStatus() == Constants.ORDER_STATUS_SUCCESS){//查到订单，并且已成功，此时是发起重复调用，应予以拒绝
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_DUPLICATE);
            }

            TransferEnv transferEnv = null;
            if(order.getOrderNo().contains("YTS")) {//原先以TS开头的，也是云贷的交易。改为YTS便于识别
                transferEnv= TransferEnvUtil.transferEnvMap.get(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
            }else if(order.getOrderNo().contains("7TS")){
                transferEnv= TransferEnvUtil.transferEnvMap.get(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
            }

            //打款成功
            if(AcctTransTradeResult.Y.getCode().equals(tradeResult.getCode())){
                log.info("========={打款给达飞结果通知}打款成功=========");
                //系统购买产品表更新
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(orderNo).andPay19MpOrderNoEqualTo(mpOrderNo);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_19_SUCCESS);
                batchBuyTemp.setPay19ReturnCode(retCode);
                batchBuyTemp.setPay19ReturnMsg(req.getErrorMsg());
                batchBuyTemp.setUpdateTime(new Date());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
                //订单表设为成功
                order.setStatus(Constants.ORDER_STATUS_SUCCESS);
                order.setUpdateTime(finTime!=null?finTime:new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(order);
                //订单明细表记录
                BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(transferEnv.getSysSubAccountREG());
//				insertOrderJnl.setSubAccountId(3);
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(amount);
                insertOrderJnl.setTransCode(Constants.TRANS_SYS_BUY_DAFY);
                insertOrderJnl.setReturnCode(retCode);
                insertOrderJnl.setReturnMsg(req.getErrorMsg());
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);

                //系统购买产品明细表新增记录(针对人工单笔发起)
                //查询对应的系统购买产品
                BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
                batchBuyExample.createCriteria().andPay19OrderNoEqualTo(orderNo)
                        .andPay19MpOrderNoEqualTo(mpOrderNo).andStatusEqualTo(Constants.BATCHBUY_STATUS_19_SUCCESS);
                List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
                List<Products> products = new ArrayList<Products>();
                if(batchBuys!=null && batchBuys.size()>0){
                    for (BsBatchBuy bsBatchBuy : batchBuys) {
                        //组织产品明细
                        Products product = new Products();
                        product.setProductAmount(bsBatchBuy.getAmount());
                        product.setProductCode(bsBatchBuy.getProductCode());
                        product.setProductOrderNo(bsBatchBuy.getSendBatchId());
                        products.add(product);
                        //插入产品明细表（人工发起单笔用户产品转账时）
                        if(order.getSubAccountId() != null){
                            BsBatchBuyDetailExample detailExample = new BsBatchBuyDetailExample();
                            detailExample.createCriteria().andSubAccountIdEqualTo(order.getSubAccountId());
                            List<BsBatchBuyDetail> localDetails = bsBatchBuyDetailMapper.selectByExample(detailExample);
                            //已存在的用户产品户不再插入
                            if(localDetails!=null && localDetails.size()>0){
                                return;
                            }
                            BsSubAc4InterestVO bsSubAc4InterestVO = bsSubAccountMapper.selectBsSubAc4BatchBuyDetailSingle(order.getSubAccountId());
                            BsBatchBuyDetail detail = new BsBatchBuyDetail();
                            detail.setBatchId(bsBatchBuy.getId());
                            detail.setCreateTime(new Date());
                            detail.setPrincipal(bsSubAc4InterestVO.getBalance());
                            detail.setRate(bsSubAc4InterestVO.getProductRate());
                            detail.setSubAccountId(bsSubAc4InterestVO.getId());
                            detail.setUpdateTime(new Date());
                            detail.setUserId(bsSubAc4InterestVO.getUserId());
                            detail.setReturnStatus(Constants.RETURN_STATUS_NOT);
                            bsBatchBuyDetailMapper.insertSelective(detail);
                        }
                    }
                }

                //系统子账户表动帐1-2（产品购买户余额减少）
                //1-修改系统子账户表
                BsSysSubAccount sysSubAccountLock=bsSysSubAccountService.findSysSubAccount4Lock(transferEnv.getSysSubAccountREG());
                BsSysSubAccount readyUpdate = new BsSysSubAccount();
                readyUpdate.setId(sysSubAccountLock.getId());
                readyUpdate.setBalance(sysSubAccountLock.getBalance() - order.getAmount());
                readyUpdate.setAvailableBalance(sysSubAccountLock.getAvailableBalance() - order.getAmount());
                readyUpdate.setCanWithdraw(sysSubAccountLock.getCanWithdraw() - order.getAmount());
                sysAccountMapper.updateByPrimaryKeySelective(readyUpdate);
                //2-新增系统记账流水表
                BsSysAccountJnl sysAccountJnl = new BsSysAccountJnl();
                sysAccountJnl.setTransTime(new Date());
                sysAccountJnl.setTransCode(Constants.SYS_BUY);
                sysAccountJnl.setTransName("系统购买达飞产品");
                sysAccountJnl.setTransAmount(order.getAmount());
                sysAccountJnl.setSysTime(new Date());
                sysAccountJnl.setChannelTime(finTime);
                sysAccountJnl.setChannelJnlNo(req.getMpOrderId());
                sysAccountJnl.setCdFlag2(Constants.CD_FLAG_C);
                sysAccountJnl.setSubAccountCode2(sysSubAccountLock.getCode());
                sysAccountJnl.setBeforeBalance2(sysSubAccountLock.getBalance());
                sysAccountJnl.setAfterBalance2(readyUpdate.getBalance());
                sysAccountJnl.setBeforeAvialableBalance2(sysSubAccountLock.getAvailableBalance());
                sysAccountJnl.setAfterAvialableBalance2(readyUpdate.getAvailableBalance());
                sysAccountJnl.setBeforeFreezeBalance2(sysSubAccountLock.getFreezeBalance());
                sysAccountJnl.setAfterFreezeBalance2(sysSubAccountLock.getFreezeBalance());
                sysAccountJnl.setFee(0.0);
                sysAccountJnl.setStatus(Constants.SYS_ACCOUNT_STATUS_SUCCESS);
                sysAccountJnlMapper.insertSelective(sysAccountJnl);

                //触发产品购买通知
                log.info("========={打款给达飞结果通知}另起线程通知达飞进行产品购买=========");
                SysBuyProductSendProcess process = new SysBuyProductSendProcess();
                process.setSysProductBuyService(this);
                B2GReqMsg_Payment_SysBatchBuyProduct payReq = new B2GReqMsg_Payment_SysBatchBuyProduct();
                payReq.setAmount(amount);
                payReq.setPayFinshTime(finTime);
                payReq.setPayOrderNo(orderNo);
                payReq.setPay19MpOrderNo(mpOrderNo);
                payReq.setPayReqTime(order.getCreateTime());
                payReq.setProducts(products);
                if(order.getOrderNo().contains("YTS")) {
                    payReq.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI);
                }else if(order.getOrderNo().contains("7TS")){
                    payReq.setPropertySymbol(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI);
                }
                process.setReq(payReq);
                new Thread(process).start();

            }
            //打款失败
            else{
                log.info("========={打款给达飞结果通知}打款失败=========");
                //系统购买产品表更新
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(orderNo).andPay19MpOrderNoEqualTo(mpOrderNo);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setPay19OrderNo(orderNo);
                batchBuyTemp.setPay19MpOrderNo(mpOrderNo);
                batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_19_FAIL);
                batchBuyTemp.setPay19ReturnCode(retCode);
                batchBuyTemp.setPay19ReturnMsg(req.getErrorMsg());
                batchBuyTemp.setUpdateTime(new Date());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
                //订单表状态设为失败
                order.setStatus(Constants.ORDER_STATUS_FAIL);
                order.setUpdateTime(finTime!=null?finTime:new Date());
                bsPayOrdersMapper.updateByPrimaryKeySelective(order);
                //订单明细表记录
                BsPayOrdersJnl insertOrderJnl = new BsPayOrdersJnl();
                insertOrderJnl.setCreateTime(new Date());
                insertOrderJnl.setOrderId(order.getId());
                insertOrderJnl.setOrderNo(order.getOrderNo());
                insertOrderJnl.setSubAccountCode(transferEnv.getSysSubAccountREG());
                insertOrderJnl.setSysTime(new Date());
                insertOrderJnl.setTransAmount(amount);
                insertOrderJnl.setTransCode(Constants.TRANS_SYS_BUY_DAFY);
                insertOrderJnl.setReturnCode(retCode);
                insertOrderJnl.setReturnMsg(req.getErrorMsg());
                bsPayOrdersJnlMapper.insertSelective(insertOrderJnl);
                //告警
                specialJnlService.warn4Fail(amount, "{打款给达飞结果通知}订单号["+order.getOrderNo()+"]打款给达飞失败",
                        order.getOrderNo(),"打款给达飞结果通知",false);
            }
            log.info("========={打款给达飞结果通知}处理结束=========");
        } finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_TRANS2DAFY_RESULT.getKey());
        }
    }

    /**
     * 向合作方购买理财
     *
     * @param req
     */
    @Override
    public void buyProduct(B2GReqMsg_Payment_SysBatchBuyProduct req) {
        log.info("========={购买达飞理财发起}处理开始=========");
        B2GResMsg_Payment_SysBatchBuyProduct res = null;
        List<Products> products = req.getProducts();
        List<String> productOrderNos = new ArrayList<>();
        if(!CollectionUtils.isEmpty(products)){
            for (Products product:products) {
                productOrderNos.add(product.getProductOrderNo());
            }
        }
        try {
            res = dafyTransportService.sysBatchBuyProduct(req);
        } catch (Exception e) {
            log.info("========={购买达飞理财发起}提交购买失败=========");
            BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
            batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getPayOrderNo()).andSendBatchIdIn(productOrderNos);
            BsBatchBuy batchBuyTemp = new BsBatchBuy();
            batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_PAY_FAIL);
            batchBuyTemp.setDafyReturnCode(ConstantUtil.BSRESCODE_FAIL);
            batchBuyTemp.setDafyReturnMsg("通讯异常");
            batchBuyTemp.setUpdateTime(new Date());
            bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            //告警
            specialJnlService.warn4Fail(req.getAmount(), "{购买达飞理财发起}支付订单号["+req.getPayOrderNo()+"]购买达飞理财发起通讯异常",
                    req.getPayOrderNo(),"购买达飞理财发起",false);

            throw new PTMessageException(PTMessageEnum.SYSTEM_ERROR, "购买达飞理财发起通讯异常");
        }
        //提交购买成功
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())){
            if(Constants.PRODUCT_PROPERTY_SYMBOL_YUN_DAI.equals(req.getPropertySymbol())){
                //系统购买产品表更新状态为 达飞购买理财处理中
                log.info("========={购买达飞理财发起}提交购买成功，等待通知确认=========");
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getPayOrderNo()).andSendBatchIdIn(productOrderNos);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_PROCCESS);
                batchBuyTemp.setUpdateTime(new Date());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            }else if(Constants.PRODUCT_PROPERTY_SYMBOL_7_DAI.equals(req.getPropertySymbol())){
                //系统购买产品表更新状态为 7贷购买理财成功
                log.info("========={购买7贷理财发起}购买成功，状态为成功=========");
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getPayOrderNo()).andSendBatchIdIn(productOrderNos);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_SUCCESS);
                batchBuyTemp.setUpdateTime(new Date());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            }
        }
        //提交购买失败
        else{
            //系统购买产品表更新状态为 达飞购买理财失败
            log.info("========={购买达飞理财发起}提交购买失败=========");
            if("购买产品失败：重复购买".equals(res.getResMsg())){
                log.info("========={购买7贷理财发起}重复购买，已购买成功，状态改为成功=========");
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getPayOrderNo()).andSendBatchIdIn(productOrderNos);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_SUCCESS);
                batchBuyTemp.setUpdateTime(new Date());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
            }else{
                BsBatchBuyExample batchBuyTempExample = new BsBatchBuyExample();
                batchBuyTempExample.createCriteria().andPay19OrderNoEqualTo(req.getPayOrderNo()).andSendBatchIdIn(productOrderNos);
                BsBatchBuy batchBuyTemp = new BsBatchBuy();
                //购买理财产品失败
                if("910003".equals(res.getResCode())){
                    batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_FAIL);
                    //达飞确认打款失败
                }else{
                    batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_PAY_FAIL);
                }
                batchBuyTemp.setDafyReturnCode(res.getResCode());
                batchBuyTemp.setDafyReturnMsg(res.getResMsg());
                batchBuyTemp.setUpdateTime(new Date());
                bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyTempExample);
                //告警
                specialJnlService.warn4Fail(req.getAmount(), "{购买达飞理财发起}支付订单号["+req.getPayOrderNo()+"]购买达飞理财发起失败",
                        req.getPayOrderNo(),"购买达飞理财发起",false);
            }

        }
        log.info("========={购买达飞理财发起}处理结束=========");
    }

    /**
     * 购买结果通知处理
     *
     * @param req
     */
    @Override
    public void notifyBuyProductResult(G2BReqMsg_DafyPayment_SysBuyProductNotice req) {
        log.info("========={购买达飞理财结果通知}处理开始=========");
        int result = req.getResult();
        BsBatchBuyExample batchBuyExample = new BsBatchBuyExample();
        batchBuyExample.createCriteria().andSendBatchIdEqualTo(req.getProductOrderNo());
        List<BsBatchBuy> batchBuys = bsBatchBuyMapper.selectByExample(batchBuyExample);
        if(batchBuys==null || batchBuys.size()<=0){
        	log.error("========={购买达飞理财结果通知}"+req.getProductOrderNo()+"找不到对应订单=========");
        	return;
        }
        //购买成功
        if(result == G2BReqMsg_DafyPayment_SysBuyProductNotice.BUY_RESULT_SUCCESS){
            //系统购买产品表更新状态为 达飞购买理财成功
            log.info("========={购买达飞理财结果通知}购买成功=========");
            BsBatchBuy batchBuyTemp = new BsBatchBuy();
            batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_SUCCESS);
            batchBuyTemp.setUpdateTime(new Date());
            bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyExample);
        }
        //购买失败
        else{
            //系统购买产品表更新状态为 达飞购买理财失败
            log.info("========={购买达飞理财结果通知}购买失败=========");
            BsBatchBuy batchBuyTemp = new BsBatchBuy();
            batchBuyTemp.setStatus(Constants.BATCHBUY_STATUS_DAFY_FAIL);
            batchBuyTemp.setDafyReturnCode(String.valueOf(req.getResult()));
            batchBuyTemp.setDafyReturnMsg(req.getErrorMsg());
            batchBuyTemp.setUpdateTime(new Date());
            bsBatchBuyMapper.updateByExampleSelective(batchBuyTemp, batchBuyExample);
            //告警
            specialJnlService.warn4Fail(req.getProductAmount(), "{购买达飞理财结果通知}产品批次号["+req.getProductOrderNo()+"]购买达飞理财失败",
                    req.getProductOrderNo(),"购买达飞理财结果通知",false);
        }
        log.info("========={购买达飞理财结果通知}处理结束=========");
    }
}
