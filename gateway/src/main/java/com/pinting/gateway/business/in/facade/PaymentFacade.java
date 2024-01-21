package com.pinting.gateway.business.in.facade;

import java.text.DecimalFormat;

import com.pinting.gateway.dafy.out.util.DafyOutMsg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.core.hessian.msg.ReqMsg;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.gateway.dafy.out.model.BasicInformationReqModel;
import com.pinting.gateway.dafy.out.model.BasicInformationResModel;
import com.pinting.gateway.dafy.out.model.BindBankcardReqModel;
import com.pinting.gateway.dafy.out.model.BindBankcardResModel;
import com.pinting.gateway.dafy.out.model.BuyProductReqModel;
import com.pinting.gateway.dafy.out.model.BuyProductResModel;
import com.pinting.gateway.dafy.out.model.QueryRepayJnlReqModel;
import com.pinting.gateway.dafy.out.model.QueryRepayJnlResModel;
import com.pinting.gateway.dafy.out.model.SysBatchBuyProductReqModel;
import com.pinting.gateway.dafy.out.service.SendDafyService;
import com.pinting.gateway.dafy.out.util.DafyOutConstant;
import com.pinting.gateway.dafy.out.util.DafyOutMsgUtil;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_ServerUsableCheck;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BasicInformation;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BindCard;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_ServerUsableCheck;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BasicInformation;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BindCard;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_BuyProduct;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_SysBatchBuyProduct;
import com.pinting.gateway.loan7.out.service.SendLoan7Service;
import com.pinting.gateway.pay19.out.util.Pay19HttpUtil;
import com.pinting.gateway.util.Constants;

/**
 * 达飞支付相关（卡绑定，产品购买...）
 *
 * @author dingpf
 * @Project: gateway
 * @Title: PaymentFacade.java
 * @date 2015-2-12 下午7:18:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("Payment")
public class PaymentFacade {
    private static String urlJsp_micro = "http://m.bigangwan.com/micro/regular/pay/return";//三方支付成功后的回调页面
    private static String urlJsp_gen = "http://www.bigangwan.com/gen/regular/pay/return";
	
	/**
	 * 服务可用测试（用户ng调用url测试服务是否可用）
	 * @param req
	 * @param res
	 */
	public void serverUsableCheck(B2GReqMsg_Payment_ServerUsableCheck req, B2GResMsg_Payment_ServerUsableCheck res){
		//无其他调用，调用到此处说明gateway服务可用
	}

    static {
        urlJsp_micro = GlobEnvUtil.get("buy.success.url.micro");
        urlJsp_gen = GlobEnvUtil.get("buy.success.url.gen");
    }

    @Autowired
    private SendDafyService dafySendService;
    @Autowired
    private SendLoan7Service sendLoan7Service;

    /**
     * 卡绑定
     *
     * @param req
     * @param res
     */
    public void bindCard(B2GReqMsg_Payment_BindCard req, B2GResMsg_Payment_BindCard res) {
        BindBankcardReqModel reqModel = new BindBankcardReqModel();
        reqModel.setBankcardNo(req.getBankCardNo());
        reqModel.setBankName(req.getBankName());
        reqModel.setCustomerId(req.getCustomerId());
        reqModel.setOpenAccountCity(req.getOpenAccountCity());
        reqModel.setOpenAccountProvince(req.getOpenAccountProvince());
        reqModel.setSubbranchName(req.getSubBranchName());
        BindBankcardResModel dafyRes = dafySendService.bindCard(reqModel);

        if (!DafyOutConstant.RETURN_CODE_ACCEPT.equals(dafyRes.getRespCode())) {
            throw new PTMessageException(PTMessageEnum.DAFY_BINDCARD_FAIL, dafyRes.getRespMsg());
        }

    }

    /**
     * 产品购买
     *
     * @param req
     * @param res
     */
    public void buyProduct(B2GReqMsg_Payment_BuyProduct req, B2GResMsg_Payment_BuyProduct res) {
        BuyProductReqModel reqModel = new BuyProductReqModel();
        reqModel.setAmount(new DecimalFormat("0.00").format((req.getAmount())));
        reqModel.setCustomerId(req.getCustomerId());
        reqModel.setProductCode(req.getProductCode());
        reqModel.setOrderId(req.getOrderNo());
        reqModel.setUrlJsp("micro".equals(req.getChannel()) ? urlJsp_micro : urlJsp_gen);
        if (ReqMsg.CHANNEL_GEN.equals(req.getChannel())) {//PC浏览器发起
            reqModel.setTransferType(BuyProductReqModel.TRANSFER_TYPE_PC);
        } else if (ReqMsg.CHANNEL_MICRO.equals(req.getChannel())) {//WAP浏览器发起
            reqModel.setTransferType(BuyProductReqModel.TRANSFER_TYPE_WAP);
        } else {//默认WAP浏览器发起
            reqModel.setTransferType(BuyProductReqModel.TRANSFER_TYPE_WAP);
        }
        reqModel.setBankCode(req.getBankCode());
        BuyProductResModel dafyRes = dafySendService.buyProduct(reqModel);

        if (DafyOutConstant.RETURN_CODE_ACCEPT.equals(dafyRes.getRespCode())) {
            //达飞受理成功，准备跳转到三方支付页面
            res.setRetHtml(dafyRes.getRetHtml());
        } else if (DafyOutConstant.RETURN_CODE_REFUSE.equals(dafyRes.getRespCode())) {
            //达飞拒绝受理，如hash校验失败等原因，直接抛错，交由business报系统异常，网站前端控制 跳转urlError页面
            throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_REFU);
        } else if (DafyOutConstant.RETURN_CODE_FAIL.equals(dafyRes.getRespCode())) {
            //达飞受理失败，如达飞方客户不存在等原因，直接抛错，告诉business异常情况，网站前端控制 跳转urlError页面
            throw new PTMessageException(PTMessageEnum.DAFY_BUYPRODUCT_FAIL, dafyRes.getRespMsg());
        }
    }


    public void basicInformation(B2GReqMsg_Payment_BasicInformation req, B2GResMsg_Payment_BasicInformation res) {
        BasicInformationReqModel reqModel = new BasicInformationReqModel();
        reqModel.setBankcardNo(req.getBankCardNo());
        reqModel.setBankName(req.getBankName());
        reqModel.setCardNo(req.getCardNo());
        reqModel.setCustomerId(req.getCustomerId());
        reqModel.setMobile(req.getMobile());
        reqModel.setName(req.getName());
        reqModel.setOpenAccountCity(req.getOpenAccountCity());
        reqModel.setOpenAccountProvince(req.getOpenAccountProvince());
        reqModel.setSubbranchName(req.getSubbranchName());
        reqModel.setStatus(String.valueOf(req.getStatus()));

        BasicInformationResModel dafyRes = dafySendService.basicInformation(reqModel);

        res.setBankCardNo(dafyRes.getBankcardNo());
        res.setBankName(dafyRes.getBankName());
        res.setCardNo(dafyRes.getCardNo());
        res.setCustomerId(dafyRes.getCustomerId());
        res.setMobile(dafyRes.getMobile());
        res.setName(dafyRes.getName());
        res.setOpenAccountCity(dafyRes.getOpenAccountCity());
        res.setOpenAccountProvince(dafyRes.getOpenAccountProvince());
        res.setSubbranchName(dafyRes.getSubbranchName());
        res.setStatus(dafyRes.getStatus() != null ? Integer.valueOf(dafyRes.getStatus()) : 0);
        res.setThirdStatus(dafyRes.getThirdStatus() != null ? Integer.valueOf(dafyRes.getThirdStatus()) : 0);

        if (!DafyOutConstant.RETURN_CODE_ACCEPT.equals(dafyRes.getRespCode())) {
            throw new PTMessageException(PTMessageEnum.DAFY_BINDCARD_FAIL, dafyRes.getRespMsg());
        }

    }

    /**
     * 系统产品购买
     *
     * @param req
     * @param res
     */
    public void sysBatchBuyProduct(B2GReqMsg_Payment_SysBatchBuyProduct req, B2GResMsg_Payment_SysBatchBuyProduct res) {
        if("YUN_DAI".equals(req.getPropertySymbol())) {
            SysBatchBuyProductReqModel reqModel = new SysBatchBuyProductReqModel();
            reqModel.setCustomerId(DafyOutMsgUtil.sysCustomerId);
            reqModel.setAmount(req.getAmount());
            reqModel.setPayFinshTime(req.getPayFinshTime() != null ? DateUtil.format(req.getPayFinshTime()) : "");
            reqModel.setPayOrderNo(req.getPay19MpOrderNo());
            reqModel.setPayPlatform(req.getPayPlatform());
            reqModel.setPayReqTime(req.getPayReqTime() != null ? DateUtil.format(req.getPayReqTime()) : "");
            reqModel.setProducts(req.getProducts());
            dafySendService.sysBatchBuyProduct(reqModel);
        }else if("7_DAI".equals(req.getPropertySymbol())){
            com.pinting.gateway.loan7.out.model.SysBatchBuyProductReqModel reqModel = new com.pinting.gateway.loan7.out.model.SysBatchBuyProductReqModel();
            reqModel.setAmount(req.getAmount());
            reqModel.setPayFinishTime(req.getPayFinshTime() != null ? DateUtil.format(req.getPayFinshTime()) : "");
            reqModel.setPayOrderNo(req.getPay19MpOrderNo());
            reqModel.setPayPlatform(req.getPayPlatform());
            reqModel.setPayReqTime(req.getPayReqTime() != null ? DateUtil.format(req.getPayReqTime()) : "");
            reqModel.setProducts(req.getProducts());
            dafySendService.sysBatchBuy7Product(reqModel);
        }
    }


    /**
     * 还款流水拉取
     *
     * @param req
     * @param res
     */
    public void queryRepayJnl(B2GReqMsg_Payment_QueryRepayJnl req, B2GResMsg_Payment_QueryRepayJnl res) {
        // 云贷
        if("YUN_DAI".equals(req.getPropertySymbol())) {
            QueryRepayJnlReqModel reqModel = new QueryRepayJnlReqModel();
            reqModel.setBorrowNo(req.getBorrowId());
            reqModel.setCustomerId(req.getBorrowerCustomerId());
            QueryRepayJnlResModel resModel = dafySendService.queryRepayJnl(reqModel);
            if (!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())) {
                throw new PTMessageException(PTMessageEnum.DAFY_QUERY_REPAL_JNL, resModel.getRespMsg());
            } else {
                res.setBorrowerRepays(resModel.getBorrowerRepayData());
            }
        } else if("7_DAI".equals(req.getPropertySymbol())) {
            com.pinting.gateway.loan7.out.model.QueryRepayJnlReqModel reqModel = new com.pinting.gateway.loan7.out.model.QueryRepayJnlReqModel();
            reqModel.setBorrowNo(req.getBorrowId());
            reqModel.setCustomerId("bgw_dafy_customer_id");
            com.pinting.gateway.loan7.out.model.QueryRepayJnlResModel resModel = sendLoan7Service.queryRepayJnl(reqModel);
            if (!DafyOutConstant.RETURN_CODE_SUCCESS.equals(resModel.getRespCode())) {
                throw new PTMessageException(PTMessageEnum.DAFY_QUERY_REPAL_JNL, resModel.getRespMsg());
            } else {
                res.setBorrowerRepays(resModel.getBorrowerRepayData());
            }
        }
    }
    

}
