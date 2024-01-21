package com.pinting.gateway.baofoo.out.service.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.baofoo.out.enums.Pay4AnotherUrl;
import com.pinting.gateway.baofoo.out.model.req.*;
import com.pinting.gateway.baofoo.out.model.resp.*;
import com.pinting.gateway.baofoo.out.service.Pay4AnotherService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.PartnerBaoFooEnv;
import com.pinting.gateway.baofoo.out.util.PartnerBaoFooEnvUtil;
import com.pinting.gateway.baofoo.out.util.PaymentChannelInfo;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Created by 剑钊 on 2016/7/21.
 */
@Service
public class Pay4AnotherServiceImpl implements Pay4AnotherService {

    private Logger logger = LoggerFactory.getLogger(Pay4AnotherServiceImpl.class);

    @Override
    public Pay4AnotherResp<Pay4AnotherTransResp> trans(Pay4AnotherTransReq req) throws Exception {

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooPay4Url + Pay4AnotherUrl.AGENT_PAY.getCode(), req);
        if(StringUtil.isEmpty(resp))
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        Pay4AnotherResp<Pay4AnotherTransResp> respPay4AnotherResp = new Pay4AnotherResp<>();
        respPay4AnotherResp = (Pay4AnotherResp<Pay4AnotherTransResp>) respPay4AnotherResp.str2Obj(resp,null);

        //状态为200表示代付交易成功    状态为0000表示代付请求交易成功（交易已受理）
        if (respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("200")) {
            logger.info("提现代付成功");
            return respPay4AnotherResp;
        }else if(respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0000") ||respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0300") ||respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0999")
                || respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0401")){
            logger.info("提现错误码：" + PTMessageEnum.BUS_PROCESSING.getCode() + "；错误信息：" + PTMessageEnum.BUS_PROCESSING.getMessage());
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }
        else {
            logger.info("提现错误码：" + respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code() + "；错误信息：" + respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_msg());
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING, respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_msg());
        }
    }

    @Override
    public Pay4AnotherResp<Pay4AnotherTransResp> partnerTrans(Pay4AnotherTransReq req, String partner) throws Exception {

        PartnerBaoFooEnv transferEnv= PartnerBaoFooEnvUtil.transferEnvMap.get(partner);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooPay4Url + Pay4AnotherUrl.AGENT_PAY.getCode(), req,transferEnv);
        if(StringUtil.isEmpty(resp))
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        Pay4AnotherResp<Pay4AnotherTransResp> respPay4AnotherResp = new Pay4AnotherResp<>();
        respPay4AnotherResp = (Pay4AnotherResp<Pay4AnotherTransResp>) respPay4AnotherResp.str2Obj(resp,transferEnv);

        //状态为200表示代付交易成功    状态为0000表示代付请求交易成功（交易已受理）
        if (respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("200")) {
            logger.info("营销代付成功");
            return respPay4AnotherResp;
        }else if(respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0000") ||respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0300") ||respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0999")
                || respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0401")){
            logger.info("营销代付错误码：" + PTMessageEnum.BUS_PROCESSING.getCode() + "；错误信息：" + PTMessageEnum.BUS_PROCESSING.getMessage());
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }
        else {
            logger.info("营销代付错误码：" + respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code() + "；错误信息：" + respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_msg());
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING, respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_msg());
        }
    }

    @Override
    public Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> transStatusQuery(Pay4AnotherTransStatusQueryReq req) throws Exception {
        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooPay4Url + Pay4AnotherUrl.AGENT_PAY_STATUS_QUERY.getCode(), req);
        if(StringUtil.isEmpty(resp)) {
            logger.error("==============宝付同步代付订单状态返回值为空==============");
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> respPay4AnotherResp = new Pay4AnotherResp<>();
        respPay4AnotherResp = (Pay4AnotherResp<Pay4AnotherTransStatusQueryResp>) respPay4AnotherResp.str2Obj(resp,null);

        logger.info("==============宝付代付查证结果："+JSON.toJSONString(respPay4AnotherResp.getTrans_content().getTrans_reqDatas())+"==============");

        if(respPay4AnotherResp.getTrans_content().getTrans_reqDatas()!=null) {

            Pay4AnotherTransStatusQueryResp queryResp = JSON.parseObject(((Pay4AnotherRespData) respPay4AnotherResp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherTransStatusQueryResp.class);

            if (StringUtils.isNotBlank(queryResp.getState()) && queryResp.getState().equals("1")) {
                return respPay4AnotherResp;
            } else if (StringUtils.isNotBlank(queryResp.getState()) && queryResp.getState().equals("-1")) {
                throw new PTMessageException(PTMessageEnum.BIZ_PAY_FAIL);
            } else {
                throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
            }
        }else {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

    }

    @Override
    public Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> transStatusQuery(Pay4AnotherTransStatusQueryReq req, String partner) throws Exception {

        PartnerBaoFooEnv transferEnv= PartnerBaoFooEnvUtil.transferEnvMap.get(partner);

        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooPay4Url + Pay4AnotherUrl.AGENT_PAY_STATUS_QUERY.getCode(), req,transferEnv);
        if(StringUtil.isEmpty(resp)) {
            logger.error("===================================宝付同步代付订单状态返回值为空====================================================");
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }

        Pay4AnotherResp<Pay4AnotherTransStatusQueryResp> respPay4AnotherResp = new Pay4AnotherResp<>();
        respPay4AnotherResp = (Pay4AnotherResp<Pay4AnotherTransStatusQueryResp>) respPay4AnotherResp.str2Obj(resp,transferEnv);

        logger.info("===========================宝付返回结果："+JSON.toJSONString(respPay4AnotherResp.getTrans_content().getTrans_reqDatas())+"====================================");

        if(respPay4AnotherResp.getTrans_content().getTrans_reqDatas()!=null) {

            Pay4AnotherTransStatusQueryResp queryResp = JSON.parseObject(((Pay4AnotherRespData) respPay4AnotherResp.getTrans_content().getTrans_reqDatas().get(0)).getTrans_reqData().toString(), Pay4AnotherTransStatusQueryResp.class);

            if (StringUtils.isNotBlank(queryResp.getState()) && queryResp.getState().equals("1")) {
                return respPay4AnotherResp;
            } else if (StringUtils.isNotBlank(queryResp.getState()) && queryResp.getState().equals("-1")) {
                throw new PTMessageException(PTMessageEnum.BIZ_PAY_FAIL,queryResp.getTrans_remark());
            } else {
                throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
            }
        }else {
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        }
    }

    @Override
    public Pay4AnotherResp<Pay4AnotherRefundQueryResp> refundQuery(Pay4AnotherRefundQueryReq req) throws Exception {
        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooPay4Url + Pay4AnotherUrl.REFUND_STATUS_QUERY.getCode(), req);
        Pay4AnotherResp<Pay4AnotherRefundQueryResp> respPay4AnotherResp = new Pay4AnotherResp<>();
        return (Pay4AnotherResp<Pay4AnotherRefundQueryResp>) respPay4AnotherResp.str2Obj(resp,null);
    }

    @Override
    public Pay4AnotherResp<Pay4AnotherApplyWhiteListResp> applyWhiteList(Pay4AnotherApplyWhiteListReq req) throws Exception {
        //向宝付发送请求
        String resp = BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooPay4Url + Pay4AnotherUrl.APPLY_WHITE_LIST.getCode(), req);
        Pay4AnotherResp<Pay4AnotherApplyWhiteListResp> respPay4AnotherResp = new Pay4AnotherResp<>();
        return (Pay4AnotherResp<Pay4AnotherApplyWhiteListResp>) respPay4AnotherResp.str2Obj(resp,null);
    }

    @Override
    public Pay4AnotherResp<Pay4AnotherOnlineTransResp> onlineTrans(Pay4AnotherOnlineTransReq req) throws Exception {

        String resp=BaoFooHttpUtil.requestForm(BaoFooHttpUtil.baofooPay4Url+Pay4AnotherUrl.ONLINE_TRANS.getCode(),req);
        if(StringUtils.isBlank(resp))
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        Pay4AnotherResp<Pay4AnotherOnlineTransResp> respPay4AnotherResp=new Pay4AnotherResp<>();
        respPay4AnotherResp=(Pay4AnotherResp<Pay4AnotherOnlineTransResp>) respPay4AnotherResp.str2Obj(resp,null);
        if (respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0000")) {
            return respPay4AnotherResp;
        }else {
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code() + "|" +
                    respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_msg());
        }
    }

	@Override
	public Pay4AnotherResp<Pay4AnotherOnlineTransResp> onlineTrans4DiffChannel(
			Pay4AnotherOnlineTransReq req, PaymentChannelInfo channelInfo)
			throws Exception {
		String resp=BaoFooHttpUtil.requestForm4DiffChannel(BaoFooHttpUtil.baofooPay4Url+Pay4AnotherUrl.ONLINE_TRANS.getCode(),req,channelInfo);
        if(StringUtils.isBlank(resp))
            throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
        Pay4AnotherResp<Pay4AnotherOnlineTransResp> respPay4AnotherResp=new Pay4AnotherResp<>();
        //解密，传入商户号代付公钥
        PartnerBaoFooEnv transferEnv = new PartnerBaoFooEnv();
        transferEnv.setPay4CerPath(channelInfo.getPay4cerpath());
        respPay4AnotherResp=(Pay4AnotherResp<Pay4AnotherOnlineTransResp>) respPay4AnotherResp.str2Obj(resp,transferEnv);
        if (respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code().equals("0000")) {
            return respPay4AnotherResp;
        }else {
            throw new PTMessageException(PTMessageEnum.RPC_EXCEPTION, respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_code() + "|" +
                    respPay4AnotherResp.getTrans_content().getTrans_head().getReturn_msg());
        }
	}

}
