package com.pinting.gateway.bird.out.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.bird.out.enums.NoticeUrl;
import com.pinting.gateway.bird.out.model.*;
import com.pinting.gateway.bird.out.service.NoticeService;
import com.pinting.gateway.bird.out.util.BirdHttpUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.Constants;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Created by 剑钊 on 2016/8/18.
 */
@Service
public class NoticeServiceImpl implements NoticeService {
    private Logger log = LoggerFactory.getLogger(NoticeServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

    @Override
    public LoanRes noticeLoan(LoanReq req) throws Exception {

        LoanReq loanReq=new LoanReq();
        loanReq.setRequestTime(DateUtil.formatDateTime(new Date(), DateUtil.DATE_TIME_FORMAT));
        loanReq.setToken(getToken(false));
        loanReq.setClientKey(BirdHttpUtil.clientKey);
        loanReq.setChannel(req.getChannel());
        loanReq.setLoanId(req.getLoanId());
        loanReq.setLoanResultCode(req.getLoanResultCode());
        loanReq.setLoanResultMsg(req.getLoanResultMsg());
        loanReq.setOrderNo(req.getOrderNo());
        log.info("借款结果通知请求："+JSON.toJSONString(loanReq));
        String result= BirdHttpUtil.requestForm(BirdHttpUtil.url_v2+ NoticeUrl.LOAN_NOTICE_V2.getCode(),loanReq,null);
        log.info("借款结果通知响应："+result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        
        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        LoanRes loanRes = new LoanRes();
        if (BirdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
        	loanRes = repeatSend(BirdHttpUtil.url_v2+ NoticeUrl.LOAN_NOTICE_V2.getCode(),loanReq, new LoanRes());
		}else {
			result = result.substring(3);
			loanRes=JSON.parseObject(result,LoanRes.class);
		}

        return loanRes;
    }

    @Override
    public RepayRes noticeRepay(RepayReq req) throws Exception {

        RepayReq repayReq=new RepayReq();
        repayReq.setRequestTime(DateUtil.formatDateTime(new Date(), DateUtil.DATE_TIME_FORMAT));
        repayReq.setToken(getToken(false));
        repayReq.setClientKey(BirdHttpUtil.clientKey);
        repayReq.setChannel(req.getChannel());
        repayReq.setLoanId(req.getLoanId());
        repayReq.setRepayResultCode(req.getRepayResultCode());
        repayReq.setRepayResultMsg(req.getRepayResultMsg());
        repayReq.setOrderNo(req.getOrderNo());
        log.info("还款结果通知请求："+JSON.toJSONString(repayReq));
        String result= BirdHttpUtil.requestForm(BirdHttpUtil.url_v2+ NoticeUrl.REPAY_NOTICE_V2.getCode(),repayReq,null);
        log.info("还款结果通知响应："+result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        
        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        RepayRes repayRes = new RepayRes();
        if (BirdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
        	repayRes = repeatSend(BirdHttpUtil.url_v2+ NoticeUrl.REPAY_NOTICE_V2.getCode(),repayReq, new RepayRes());
		}else {
			result = result.substring(3);
			repayRes=JSON.parseObject(result,RepayRes.class);
		}
        
        return repayRes;
    }

	@Override
	public BankLimitRes sandBankLimit(BankLimitReq req) throws Exception {
        req.setToken(getToken(false));
        log.info("卡限额通知请求："+JSON.toJSONString(req));
        String result= BirdHttpUtil.requestFormByPost(BirdHttpUtil.url_v2+ NoticeUrl.BANK_LIMIT_NOTICE_V2.getCode(),req,null);
        log.info("卡限额通知响应："+JSON.toJSONString(result));
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        
        
        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        BankLimitRes bankLimitRes = new BankLimitRes();
        if (BirdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
        	bankLimitRes = repeatSendPost(BirdHttpUtil.url_v2+ NoticeUrl.BANK_LIMIT_NOTICE_V2.getCode(),req, new BankLimitRes());
		}else {
			result = result.substring(3);
			bankLimitRes=JSON.parseObject(result,BankLimitRes.class);
		}
        
		return bankLimitRes;
	}

    @Override
    public MarketTransRes noticeMarketTrans(MarketTransReq req) throws Exception {

        req.setRequestTime(DateUtil.formatDateTime(new Date(), DateUtil.DATE_TIME_FORMAT));
        req.setToken(getToken(false));
        req.setClientKey(BirdHttpUtil.clientKey);

        log.info("营销代付通知请求："+JSON.toJSONString(req));
        String result= BirdHttpUtil.requestForm(BirdHttpUtil.url_v2+ NoticeUrl.MARKET_NOTICE_V2.getCode(),req,null);
        log.info("营销代付通知响应："+result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        
        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        MarketTransRes repayRes = new MarketTransRes();
        if (BirdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
        	repayRes = repeatSend(BirdHttpUtil.url_v2+ NoticeUrl.MARKET_NOTICE_V2.getCode(),req, new MarketTransRes());
		}else {
			result = result.substring(3);
			repayRes=JSON.parseObject(result,MarketTransRes.class);
		}

        return repayRes;

    }

    private <T> T repeatSend(String url, BaseReqModel req, T t) throws Exception {
        req.setToken(getToken(true));
        String result = BirdHttpUtil.requestForm(url, req, null);
        log.info(url + "重发请求响应："+ result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        result = result.substring(3);
        T res = (T) JSON.parseObject(result, t.getClass());
        return res;
    }
    
    private <T> T repeatSendPost(String url, BaseReqModel req, T t) throws Exception {
        req.setToken(getToken(true));
        String result = BirdHttpUtil.requestFormByPost(url, req, null);
        log.info(url + "重发请求响应："+ result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        T res = (T) JSON.parseObject(result, t.getClass());
        return res;
    }

    /**
     * token获取
     * @param isNew true优先重新请求获取; false优先获取缓存token
     * @return 获取不到则返回null
     */
    private String getToken(boolean isNew){
        String token = null;
        try {
            if(!isNew)
                token = jsClientDaoSupport.getString(Constants.REDIS_SUBSYS_GATEWAY + "_" +
                    Constants.THIRD_SYS_CHANNEL_ZAN + "_TOKEN");
            if(isNew || StringUtil.isEmpty(token)){
                TokenReq req = new TokenReq();
                req.setOrg_code(BirdHttpUtil.org_code);
                req.setOrg_secret(BirdHttpUtil.org_secret);
                //req.setRequestTime(DateUtil.format(new Date()));
                String url = BirdHttpUtil.token_url + NoticeUrl.ZAN_TOKEN.getCode();
                log.info("赞分期TOKEN获取请求："+JSON.toJSONString(req) + "|URL:"+url);
                String result = BirdHttpUtil.requestFormByPost(url, req, null);
                log.info("赞分期TOKEN获取响应："+result);
                if (StringUtils.isBlank(result)) {
                    return null;
                }
                TokenRes tokenRes = JSON.parseObject(result, TokenRes.class);
                token = tokenRes.getAccess_token();
                if(StringUtil.isNotEmpty(token)){
                    jsClientDaoSupport.setString(token, Constants.REDIS_SUBSYS_GATEWAY + "_" +
                            Constants.THIRD_SYS_CHANNEL_ZAN + "_TOKEN");
                    return token;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

}
