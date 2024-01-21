package com.pinting.gateway.baofoo.in.controller;

import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.baofoo.out.enums.BaoFooTxnType;
import com.pinting.gateway.baofoo.out.model.req.PcPayGateReq;
import com.pinting.gateway.baofoo.out.service.NewCounterService;
import com.pinting.gateway.baofoo.out.util.BaoFooHttpUtil;
import com.pinting.gateway.baofoo.out.util.SecurityUtil;
import com.pinting.gateway.business.out.service.BaoFooSendBusinessService;
import com.pinting.gateway.hessian.message.baofoo.G2BReqMsg_BaoFooPay_NewCounterResultNotice;
import com.pinting.gateway.hessian.message.baofoo.G2BResMsg_BaoFooPay_NewCounterResultNotice;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by 剑钊 on 2016/7/23.
 * 网银支付异步通知
 */
@Controller
@RequestMapping("/baofoo/new_counter")
public class EbankController {

    private static Logger log = LoggerFactory
            .getLogger(EbankController.class);

    @Autowired
    private BaoFooSendBusinessService sendBusinessService;


    @ResponseBody
    @RequestMapping("/notice")
    public String notice(HttpServletRequest request, HttpServletResponse response) throws Exception {

        //打印回调参数
        log.info("接收回调参数：" + request.getParameterMap().toString());

        String transID = request.getParameter("TransID");//商户流水号
        String result = request.getParameter("Result");//支付结果
        String resultDesc = request.getParameter("ResultDesc");//支付结果描述
        String factMoney = request.getParameter("FactMoney");//实际成功金额
        String additionalInfo = request.getParameter("AdditionalInfo");//订单附加消息
        String succTime = request.getParameter("SuccTime");//支付完成时间
        String md5Sign = request.getParameter("Md5Sign");//MD5签名宝付返回的
        log.info("宝付MD5验签值：" + md5Sign);

        //本地MD5验签字符串
        String md5 = "MemberID=" + BaoFooHttpUtil.memberId + BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK + "TerminalID=" + BaoFooHttpUtil.counterterminalId + BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK
                + "TransID=" + transID + BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK + "Result=" + result + BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK
                + "ResultDesc=" + resultDesc + BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK + "FactMoney=" + factMoney + BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK
                + "AdditionalInfo=" +additionalInfo+ BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK + "SuccTime=" + succTime + BaoFooTxnType.ASYNCHRONOUS_NOTICE_MARK
                + "Md5Sign=" + BaoFooHttpUtil.counterMd5Key;
        log.info("本地MD5值：" + md5);
        String localSign = SecurityUtil.MD5(md5);
        log.info("本地MD5验签值：" + localSign);
        if (localSign.equals(md5Sign)) {
            try {
                G2BReqMsg_BaoFooPay_NewCounterResultNotice resultNotice=new G2BReqMsg_BaoFooPay_NewCounterResultNotice();
                resultNotice.setTransId(transID);
                resultNotice.setSuccTime(succTime);
                resultNotice.setFactMoney(factMoney);
                resultNotice.setResultDesc(resultDesc);
                resultNotice.setResult(result);
                
                G2BResMsg_BaoFooPay_NewCounterResultNotice res=sendBusinessService.sendNewCounterResultNotice(resultNotice);

                if(res.getResCode().equals(ConstantUtil.BSRESCODE_SUCCESS)) {
                	log.info("网银通知处理成功");
                	return "OK";
                }else {
                	log.error("网银通知处理失败");
                    return "Fail";//业务处理错误，重发
                }
            } catch (Exception e) {
            	log.error("网银通知处理异常");
                return "Fail";
            }
        } else {
            log.error("MD5验签失败");
            return "Md5CheckFail";//MD5验签失败
        }
    }
}
