package com.pinting.business.accounting.loan.service.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.service.DepFixedActFillAccountService;
import com.pinting.business.accounting.loan.service.DepFixedActFillService;
import com.pinting.business.dao.LnAccountFillMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.LnAccountFill;
import com.pinting.business.model.LnAccountFillExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.dafy.G2BReqMsg_DafyLoan_FillFinishNotice;
import com.pinting.gateway.hessian.message.loan7.G2BReqMsg_DepLoan7_FillFinishNotice;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/7
 * Description: 云贷补账服务
 */
@Service
public class DepFixedActFillServiceImpl implements DepFixedActFillService {

    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    private final Logger logger = LoggerFactory.getLogger(DepFixedActFillServiceImpl.class);

    @Autowired
    private LnAccountFillMapper lnAccountFillMapper;
    @Autowired
    private SpecialJnlService specialJnlService;
    @Autowired
    private DepFixedActFillAccountService depFixedActFillAccountService;

    @Override
    public void depFixedActFillFinishHandle(G2BReqMsg_DafyLoan_FillFinishNotice req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_ACTFILL.getKey());
            //订单号,fillType校验
            LnAccountFillExample example = new LnAccountFillExample();
            example.createCriteria().andOrderNoEqualTo(req.getOrderNo());
            List<LnAccountFill> lnAccountFills= lnAccountFillMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(lnAccountFills)){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }else{
                if(lnAccountFills.get(0).getStatus() == Constants.FILL_FILL_STATUS_SUCCESS){
                    logger.info("==========【云贷自主补账】已有成功订单："+req.getOrderNo()+"==========");
                    return;
                }
            }
            //支付单号校验
            LnAccountFillExample example1 = new LnAccountFillExample();
            example1.createCriteria().andPayOrderNoEqualTo(req.getPayOrderNo()).andStatusEqualTo(Constants.FILL_FILL_STATUS_SUCCESS);
            List<LnAccountFill> lnAccountFills1= lnAccountFillMapper.selectByExample(example1);
            if(CollectionUtils.isNotEmpty(lnAccountFills1)){
                logger.info("==========【云贷自主补账】已有成功的同支付单号："+req.getPayOrderNo()+"==========");
                return;
            }
            LnAccountFill lnAccountFill = lnAccountFills.get(0);
            //类型校验
            if(!req.getFillType().equals(lnAccountFill.getFillType())){
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "补账类型错误");
            }
            //金额校验
            if(MoneyUtil.subtract(lnAccountFill.getAmount(),req.getAmount()).doubleValue() != 0){
                logger.info("==========【云贷自主补账】金额校验req.amount:"+MoneyUtil.divide(req.getAmount(), 100, 2).doubleValue()
                        +"lnAccountFill.amount="+lnAccountFill.getAmount()+"==========");
                specialJnlService.warn4FailNoSMS(null, "云贷补帐完成通知传入金额(" + req.getAmount() + ")和币港湾记录的需要划拨的金额("+ lnAccountFill.getAmount() +")不相等,需要您的确认,请检查", null, "云贷补帐完成通知金额不匹配");
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_AMOUNT_NOT_SAME);
            }
            //修改ln_account_fill
            LnAccountFill accountFillNew = new LnAccountFill();
            accountFillNew.setId(lnAccountFill.getId());
            accountFillNew.setFinishTime(req.getFinishTime());
            accountFillNew.setPayOrderNo(req.getPayOrderNo());
            accountFillNew.setUpdateTime(new Date());
            accountFillNew.setStatus(Constants.FILL_FILL_STATUS_SUCCESS);
            lnAccountFillMapper.updateByPrimaryKeySelective(accountFillNew);
            //调用补账完成记账服务
            depFixedActFillAccountService.depFixedActFillFinishRecord(Constants.SYS_ACCOUNT_THD_REVENUE_YUN, lnAccountFill.getAmount(), PartnerEnum.YUN_DAI_SELF);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_ACTFILL.getKey());
        }
    }

    @Override
    public void depFixedActFillFinishHandle(G2BReqMsg_DepLoan7_FillFinishNotice req) {
        try {
            jsClientDaoSupport.lock(RedisLockEnum.LOCK_DEP_FIXED_ACTFILL.getKey());
            //订单号,fillType校验
            LnAccountFillExample example = new LnAccountFillExample();
            example.createCriteria().andOrderNoEqualTo(req.getOrderNo());
            List<LnAccountFill> lnAccountFills= lnAccountFillMapper.selectByExample(example);
            if(CollectionUtils.isEmpty(lnAccountFills)){
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_NOT_EXIST);
            }else{
                if(lnAccountFills.get(0).getStatus() == Constants.FILL_FILL_STATUS_SUCCESS){
                    logger.info("==========【7贷自主补账】已有成功订单："+req.getOrderNo()+"==========");
                    return;
                }
            }
            //支付单号校验
            LnAccountFillExample example1 = new LnAccountFillExample();
            example1.createCriteria().andPayOrderNoEqualTo(req.getPayOrderNo()).andStatusEqualTo(Constants.FILL_FILL_STATUS_SUCCESS);
            List<LnAccountFill> lnAccountFills1= lnAccountFillMapper.selectByExample(example1);
            if(CollectionUtils.isNotEmpty(lnAccountFills1)){
                logger.info("==========【7贷自主补账】已有成功的同支付单号："+req.getPayOrderNo()+"==========");
                return;
            }
            LnAccountFill lnAccountFill = lnAccountFills.get(0);
            //类型校验
            if(!req.getFillType().equals(lnAccountFill.getFillType())){
                throw new PTMessageException(PTMessageEnum.DATA_VALIDATE_ERROR, "补账类型错误");
            }
            //金额校验
            if(MoneyUtil.subtract(lnAccountFill.getAmount(),req.getAmount()).doubleValue() != 0){
                logger.info("==========【7贷自主补账】金额校验req.amount:"+MoneyUtil.divide(req.getAmount(), 100, 2).doubleValue()
                        +"lnAccountFill.amount="+lnAccountFill.getAmount()+"==========");
                specialJnlService.warn4FailNoSMS(null, "7贷补帐完成通知传入金额(" + req.getAmount() + ")和币港湾记录的需要划拨的金额("+ lnAccountFill.getAmount() +")不相等,需要您的确认,请检查", null, "7贷补帐完成通知金额不匹配");
                throw new PTMessageException(PTMessageEnum.PAYMENT_ORDER_AMOUNT_NOT_SAME);
            }
            //修改ln_account_fill
            LnAccountFill accountFillNew = new LnAccountFill();
            accountFillNew.setId(lnAccountFill.getId());
            accountFillNew.setFinishTime(req.getFinishTime());
            accountFillNew.setPayOrderNo(req.getPayOrderNo());
            accountFillNew.setUpdateTime(new Date());
            accountFillNew.setStatus(Constants.FILL_FILL_STATUS_SUCCESS);
            lnAccountFillMapper.updateByPrimaryKeySelective(accountFillNew);
            //调用补账完成记账服务
            depFixedActFillAccountService.depFixedActFillFinishRecord(Constants.SYS_ACCOUNT_THD_REVENUE_7, lnAccountFill.getAmount(), PartnerEnum.SEVEN_DAI_SELF);
        }finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_DEP_FIXED_ACTFILL.getKey());
        }
    }
}
