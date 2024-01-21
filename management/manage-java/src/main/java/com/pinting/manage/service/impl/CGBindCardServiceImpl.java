package com.pinting.manage.service.impl;

import com.pinting.business.dao.UcBankCardMapper;
import com.pinting.business.model.vo.BsDailyInterestVO;
import com.pinting.business.model.vo.CGBindCardResVO;
import com.pinting.business.model.vo.CGBindCardVO;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_BatchChangeCard;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_UserAddCard;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_BatchChangeCard;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_UserAddCard;
import com.pinting.gateway.hessian.message.hfbank.model.BatchUpdateCardExtDetail;
import com.pinting.gateway.hessian.message.hfbank.model.BatchUpdateCardExtError;
import com.pinting.gateway.hessian.message.hfbank.model.BatchUpdateCardExtSuccess;
import com.pinting.gateway.out.service.HfbankTransportService;
import com.pinting.manage.service.CGBindCardService;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */
@Service
public class CGBindCardServiceImpl implements CGBindCardService {

    private Logger logger = LoggerFactory.getLogger(CGBindCardService.class);

    @Autowired
    private HfbankTransportService hfbankTransportService;

    @Autowired
    private UcBankCardMapper ucBankCardMapper;

    @Override
    public List<CGBindCardResVO> cgBindCard(Integer ucUserId) {

        List<CGBindCardVO> list = ucBankCardMapper.getIsBandList(ucUserId);
        List<CGBindCardResVO> successResult = new ArrayList<>();
        List<CGBindCardVO> successList = new ArrayList<>();

        logger.info("系统恒丰临时解绑开始");
        List<CGBindCardResVO> result = new ArrayList<>();

        int uLength = list.size();
        int uSize = 400;
        int uMaxPage = uLength%uSize==0 ? uLength/uSize : uLength/uSize + 1;
        for (int i = 1; i <= uMaxPage; i++){
            logger.info("==================系统恒丰临时解绑截取 list 子列表："+uSize*(i-1)+":"+(uLength<(uSize*i)?uLength:(uSize*i))+"====================");
            List<CGBindCardVO> reqList = list.subList(uSize*(i-1), uLength<(uSize*i)?uLength:(uSize*i));
            result.addAll(unBindCard(reqList, successResult));

            for (CGBindCardVO bindCardVO: reqList) {
                if(!CollectionUtils.isEmpty(successResult)) {
                    for (CGBindCardResVO success: successResult) {
                        if(bindCardVO.getHfUserId().equals(success.getHfUserId())) {
                            successList.add(bindCardVO);
                            break;
                        }
                    }
                }
            }
        }
        logger.info("系统恒丰临时解绑结束");
        logger.info("系统恒丰临时绑卡开始");
        if(!CollectionUtils.isEmpty(successList)) {
            List<CGBindCardResVO> bindCardResVOList = bindCard(successList);
            result.addAll(bindCardResVOList);
        }
        logger.info("系统恒丰临时绑卡结束");
        return result;
    }

    private List<CGBindCardResVO> unBindCard(List<CGBindCardVO> list, List<CGBindCardResVO> successResult) {
        List<CGBindCardResVO> result = new ArrayList<>();

        B2GReqMsg_HFBank_BatchChangeCard req = new B2GReqMsg_HFBank_BatchChangeCard();
        List<BatchUpdateCardExtDetail> data = new ArrayList<>();
        for (CGBindCardVO vo: list) {
            BatchUpdateCardExtDetail detail = new BatchUpdateCardExtDetail();
            detail.setPlatcust(vo.getHfUserId());
            detail.setCard_no(vo.getCardNo());
            detail.setCard_no_old(vo.getCardNo());
            detail.setDetail_no(Util.generateOrderNo4BaoFoo(8));
            detail.setMobile(vo.getMobile());
            detail.setName(vo.getUserName());
            detail.setRemark("解绑卡");
            data.add(detail);
        }
        req.setPartner_trans_time(new Date());
        req.setPartner_trans_date(new Date());
        req.setOrder_no(Util.generateOrderNo4BaoFoo(8));
        req.setTotal_num(list.size());
        req.setData(data);

        B2GResMsg_HFBank_BatchChangeCard res = new B2GResMsg_HFBank_BatchChangeCard();
        try {
            logger.info("恒丰批量解绑卡首条记录：{}, 最后一条：{}", list.get(0).getCardNo(), list.get(list.size()-1).getCardNo());
            res = hfbankTransportService.batchChangeCard(req);
        } catch (Exception e) {
            logger.info("恒丰批量换卡操作失败 {}，订单号：{}", e.getMessage(), req.getOrder_no());
            for (CGBindCardVO vo: list) {
                CGBindCardResVO error = new CGBindCardResVO();
                error.setHfUserId(vo.getHfUserId());
                error.setOrderNo("");
                error.setResCode(res.getResCode());
                error.setResMsg("解绑失败：" + res.getResMsg());
                error.setNote("解绑");
                result.add(error);
            }
            return result;
        }
        if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
            if (!CollectionUtils.isEmpty(res.getSuccess_data())) {
                for (BatchUpdateCardExtSuccess successData: res.getSuccess_data()) {
                    logger.info("系统恒丰临时解绑成功记录：{}", JSONObject.fromObject(successData));
                    CGBindCardResVO success = new CGBindCardResVO();
                    success.setHfUserId(successData.getPlatcust());
                    success.setOrderNo(successData.getDetail_no());
                    success.setResCode(res.getResCode());
                    success.setResMsg("解绑成功：" + res.getResMsg());
                    success.setNote("解绑");
                    result.add(success);
                    successResult.add(success);
                }
            }
            if(!CollectionUtils.isEmpty(res.getError_data())) {
                for (BatchUpdateCardExtError errorData: res.getError_data()) {
                    logger.info("系统恒丰临时解绑失败记录：{}", JSONObject.fromObject(errorData));
                    CGBindCardResVO error = new CGBindCardResVO();
                    for (BatchUpdateCardExtDetail detail : data) {
                        if(detail.getDetail_no().equals(errorData.getDetail_no())) {
                            error.setHfUserId(detail.getPlatcust());
                            error.setOrderNo(errorData.getDetail_no());
                            error.setResCode(errorData.getError_no());
                            error.setResMsg("解绑失败：" + errorData.getError_info());
                            error.setNote("解绑");
                            result.add(error);
                            break;
                        }
                    }
                }
            }
        } else {
            logger.info("系统恒丰临时解绑失败通讯异常：{}", JSONObject.fromObject(res));
            CGBindCardResVO error = new CGBindCardResVO();
            error.setHfUserId("");
            error.setOrderNo("");
            error.setResCode(res.getResCode());
            error.setResMsg("解绑失败：" + res.getResMsg());
            error.setNote("解绑");
            result.add(error);
        }
        return result;
    }

    private List<CGBindCardResVO> bindCard(List<CGBindCardVO> successList) {
        List<CGBindCardResVO> result = new ArrayList<>();
        for (CGBindCardVO bindCard: successList) {
            B2GReqMsg_HFBank_UserAddCard addCardReq = new B2GReqMsg_HFBank_UserAddCard();
            addCardReq.setOrder_no(Util.generateOrderNo4BaoFoo(8));
            addCardReq.setPartner_trans_date(new Date());
            addCardReq.setPartner_trans_time(new Date());
            addCardReq.setType(Constants.HF_BIND_CARD_TYPE_PERSON);//绑卡类型：1个人客户 2 对公客户
            addCardReq.setPlatcust(bindCard.getHfUserId());
            addCardReq.setId_type(Constants.HF_ID_TYPE_ID_CARD);    //1:身份证
            addCardReq.setId_code(bindCard.getIdCard());
            addCardReq.setName(bindCard.getUserName());
            addCardReq.setCard_no(bindCard.getCardNo());
            addCardReq.setCard_type(Constants.HF_CARD_TYPE_DEBIT);    //1:借记卡,2:信用卡
            addCardReq.setPre_mobile(bindCard.getMobile());
            addCardReq.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
            addCardReq.setRemark("个人绑卡");
            logger.info("系统恒丰临时绑卡请求：{}", JSONObject.fromObject(addCardReq));
            B2GResMsg_HFBank_UserAddCard addCardRes = new B2GResMsg_HFBank_UserAddCard();
            try {
                addCardRes = hfbankTransportService.userAddCard(addCardReq);
                if (ConstantUtil.BSRESCODE_SUCCESS.equals(addCardRes.getResCode())) {
                    logger.info("系统恒丰临时绑卡成功：{}", JSONObject.fromObject(addCardRes));
                    CGBindCardResVO success = new CGBindCardResVO();
                    success.setHfUserId(bindCard.getHfUserId());
                    success.setOrderNo(addCardReq.getOrder_no());
                    success.setResCode(addCardRes.getResCode());
                    success.setResMsg("绑卡成功：" + addCardRes.getResMsg());
                    success.setNote("绑卡");
                    result.add(success);
                } else {
                    logger.info("系统恒丰临时绑卡失败：{}", JSONObject.fromObject(addCardRes));
                    CGBindCardResVO error = new CGBindCardResVO();
                    error.setHfUserId(bindCard.getHfUserId());
                    error.setOrderNo(addCardReq.getOrder_no());
                    error.setResCode(addCardRes.getResCode());
                    error.setResMsg("绑卡失败：" + addCardRes.getResMsg());
                    error.setNote("绑卡");
                    result.add(error);
                }
            } catch (Exception e) {
                e.printStackTrace();
                logger.info("系统恒丰临时绑卡失败通讯异常：{}", JSONObject.fromObject(addCardRes));
                CGBindCardResVO error = new CGBindCardResVO();
                error.setHfUserId(bindCard.getHfUserId());
                error.setOrderNo(addCardReq.getOrder_no());
                error.setResCode(addCardRes.getResCode());
                error.setResMsg("绑卡失败：" + addCardRes.getResMsg());
                error.setNote("绑卡");
                result.add(error);
            }
        }
        return result;
    }

}
