package com.pinting.gateway.zsd.out.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.zsd.out.enums.NoticeUrl;
import com.pinting.gateway.zsd.out.model.*;
import com.pinting.gateway.zsd.out.service.ZsdNoticeService;
import com.pinting.gateway.zsd.out.util.ZsdHttpUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.util.Constants;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 
 * @project gateway
 * @title NoticeServiceImpl.java
 * @author Dragon & cat
 * @date 2017-9-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Service
public class ZsdNoticeServiceImpl implements ZsdNoticeService {
    private Logger log = LoggerFactory.getLogger(ZsdNoticeServiceImpl.class);
    private JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_GATEWAY);

    @Override
    public LoanRes noticeLoan(LoanReq req) throws Exception {

        LoanReq loanReq=new LoanReq();
        loanReq.setRequestTime(DateUtil.formatDateTime(new Date(), DateUtil.DATE_TIME_FORMAT));
        loanReq.setToken(getToken(false));
        loanReq.setClientKey(ZsdHttpUtil.clientKey);
        loanReq.setChannel(req.getChannel());
        loanReq.setLoanId(req.getLoanId());
        loanReq.setLoanResultCode(req.getLoanResultCode());
        loanReq.setLoanResultMsg(req.getLoanResultMsg());
        loanReq.setOrderNo(req.getOrderNo());
        log.info("借款结果通知请求："+JSON.toJSONString(loanReq));
        String result= ZsdHttpUtil.requestForm(ZsdHttpUtil.url+ NoticeUrl.LOAN_NOTICE.getCode(),loanReq,null);
        log.info("借款结果通知响应："+result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        
        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        LoanRes loanRes = new LoanRes();
        if (ZsdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
        	loanRes = repeatSend(ZsdHttpUtil.url+ NoticeUrl.LOAN_NOTICE.getCode(),loanReq, new LoanRes());
		}else {
			result = result.substring(3);
			loanRes=JSON.parseObject(result,LoanRes.class);
		}

        return loanRes;
    }
    
    @Override
	public RepayRes noticeRepay(RepayReq req) throws Exception {
		RepayReq repayReq = new RepayReq();
        repayReq.setRequestTime(DateUtil.formatDateTime(new Date(), DateUtil.DATE_TIME_FORMAT));
        repayReq.setToken(getToken(false));
        repayReq.setClientKey(ZsdHttpUtil.clientKey);
        repayReq.setChannel(req.getChannel());
        repayReq.setLoanId(req.getLoanId());
        repayReq.setRepayResultCode(req.getRepayResultCode());
        repayReq.setRepayResultMsg(req.getRepayResultMsg());
        repayReq.setOrderNo(req.getOrderNo());
        log.info("还款结果通知请求：" + JSON.toJSONString(repayReq));
        String result = ZsdHttpUtil.requestForm(ZsdHttpUtil.url + NoticeUrl.REPAY_NOTICE.getCode(), repayReq, null);
        log.info("还款结果通知响应：" + result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        
        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        RepayRes repayRes = new RepayRes();
        if (ZsdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
        	repayRes = repeatSend(ZsdHttpUtil.url + NoticeUrl.REPAY_NOTICE.getCode(), repayReq, new RepayRes());
		}else {
			result = result.substring(3);
			repayRes = JSON.parseObject(result, RepayRes.class);
		}
        return repayRes;
	}
    
    @Override
    public ZsdBankLimitRes noticeBankLimit(ZsdBankLimitReq req) throws Exception {
        req.setToken(getToken(false));
        log.info("赞时贷银行卡限额通知请求："+JSON.toJSONString(req));

        String result= ZsdHttpUtil.requestFormByPost(ZsdHttpUtil.url+ NoticeUrl.BANK_LIMIT_NOTICE.getCode(),req,null);
        log.info("卡限额通知响应："+JSON.toJSONString(result));
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }

        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        ZsdBankLimitRes zsdBankLimitRes = new ZsdBankLimitRes();
        if (ZsdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
            zsdBankLimitRes = repeatSendPost(ZsdHttpUtil.url+ NoticeUrl.BANK_LIMIT_NOTICE.getCode(),req, new ZsdBankLimitRes());
        }else {
            result = result.substring(3);
            zsdBankLimitRes = JSON.parseObject(result, ZsdBankLimitRes.class);
        }

        return zsdBankLimitRes;

    }

    private <T> T repeatSend(String url, BaseReqModel req, T t) throws Exception {
        req.setToken(getToken(true));
        String result = ZsdHttpUtil.requestForm(url, req, null);
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
        String result = ZsdHttpUtil.requestFormByPost(url, req, null);
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
                    Constants.THIRD_SYS_CHANNEL_ZSD + "_TOKEN");
            	log.info("直接从redis中获取到赞时贷token："+token);
            if(isNew || StringUtil.isEmpty(token)){
                TokenReq req = new TokenReq();
                req.setOrg_code(ZsdHttpUtil.org_code);
                req.setOrg_secret(ZsdHttpUtil.org_secret);
                //req.setRequestTime(DateUtil.format(new Date()));
                String url = ZsdHttpUtil.token_url + NoticeUrl.ZSD_TOKEN.getCode();
                log.info("赞时贷TOKEN获取请求："+JSON.toJSONString(req) + "|URL:"+url);
                String result = ZsdHttpUtil.requestFormByPost(url, req, null);
                log.info("赞时贷TOKEN获取响应："+result);
                if (StringUtils.isBlank(result)) {
                    return null;
                }
                TokenRes tokenRes = JSON.parseObject(result, TokenRes.class);
                token = tokenRes.getAccess_token();
                if(StringUtil.isNotEmpty(token)){
                    jsClientDaoSupport.setString(token, Constants.REDIS_SUBSYS_GATEWAY + "_" +
                            Constants.THIRD_SYS_CHANNEL_ZSD + "_TOKEN");
                    return token;
                }
            }
        } catch (Exception e){
            e.printStackTrace();
        }
        return token;
    }

	@Override
	public SignResultNoticeResModel signResultNotice(SignResultNoticeReqModel req) throws Exception {
		SignResultNoticeReqModel signReq = new SignResultNoticeReqModel();
		signReq.setRequestTime(DateUtil.formatDateTime(new Date(), DateUtil.DATE_TIME_FORMAT));
		signReq.setToken(getToken(false));
		signReq.setClientKey(ZsdHttpUtil.clientKey);
		signReq.setLoanId(req.getLoanId());
		signReq.setAgreementNo(req.getAgreementNo());
		signReq.setAgreementUrl(req.getAgreementUrl());
		signReq.setSignResult(req.getSignResult());
        log.info("借款协议签章结果通知请求："+JSON.toJSONString(signReq));
        String result= ZsdHttpUtil.requestForm(ZsdHttpUtil.url + NoticeUrl.SIGN_RESULT_NOTICE.getCode(), signReq, null);
        log.info("借款协议签章结果通知响应："+result);
        if (StringUtils.isBlank(result)) {
            throw new PTMessageException(PTMessageEnum.LOAN_NOTICE_FAIL);
        }
        
        //先判断返回值是否是http状态码401  如果为token超时则重发。否则进行解析
        SignResultNoticeResModel signRes = new SignResultNoticeResModel();
        if (ZsdHttpUtil.tokenExpirHttpStatusCode.equals(result)) {
        	signRes = repeatSend(ZsdHttpUtil.url + NoticeUrl.SIGN_RESULT_NOTICE.getCode(), signReq, new SignResultNoticeResModel());
		}else {
			result = result.substring(3);
			signRes = JSON.parseObject(result, SignResultNoticeResModel.class);
		}
        return signRes;
	}

}
