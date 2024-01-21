package com.pinting.business.accounting.finance.service.impl.process;

import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsHfbankUserExtMapper;
import com.pinting.business.dao.BsPayReceiptMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.enums.BaoFooEnum;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.gateway.hessian.message.hfbank.B2GReqMsg_HFBank_BatchBindCard4Elements;
import com.pinting.gateway.hessian.message.hfbank.B2GResMsg_HFBank_BatchBindCard4Elements;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtDetail;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtError;
import com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtSuccess;
import com.pinting.gateway.out.service.HfbankTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/19
 * Description:
 */
public class BatchBindCard4ElementsProcess {

//    private Logger logger = LoggerFactory.getLogger(BatchBindCard4ElementsProcess.class);
//
//    private HfbankTransportService hfbankTransportService;
//    private BsBankCardService bsBankCardService;
//    private BsBankCardMapper bsBankCardMapper;
//    private BsPayReceiptMapper bsPayReceiptMapper;
//    private BsHfbankUserExtMapper bsHfbankUserExtMapper;
//    private BsUserMapper bsUserMapper;
//    private Integer pageNum;
//    private Integer numPerPage;
//
//    public BatchBindCard4ElementsProcess(BsBankCardService bsBankCardService, HfbankTransportService hfbankTransportService,
//                                         BsBankCardMapper bsBankCardMapper, BsPayReceiptMapper bsPayReceiptMapper,
//                                         BsHfbankUserExtMapper bsHfbankUserExtMapper, BsUserMapper bsUserMapper,
//                                         Integer pageNum, Integer numPerPage) {
//        this.hfbankTransportService = hfbankTransportService;
//        this.bsBankCardService = bsBankCardService;
//        this.bsBankCardMapper = bsBankCardMapper;
//        this.bsPayReceiptMapper = bsPayReceiptMapper;
//        this.bsHfbankUserExtMapper = bsHfbankUserExtMapper;
//        this.bsUserMapper = bsUserMapper;
//        this.pageNum = pageNum;
//        this.numPerPage = numPerPage;
//    }
//
//    @Override
//    public void run() {
//        int count = bsBankCardService.bankCardUserCount(null, null, null, null, null, Constants.BANK_CARD_NORMAL, null, null);
//        if(count > 0) {
//            batchBindCard();
//        }
//    }
//
//    private void batchBindCard() {
//        int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage);
//        List<BsBankCardVO> cards = bsBankCardService.findBsBankCardByPage(null, null, null, null, null, Constants.BANK_CARD_NORMAL, null, null, String.valueOf(start), String.valueOf(numPerPage));
//        if(!CollectionUtils.isEmpty(cards)) {
//            B2GReqMsg_HFBank_BatchBindCard4Elements req = new B2GReqMsg_HFBank_BatchBindCard4Elements();
//            /* 总数量 */
//            req.setTotal_num(cards.size());
//            List<BatchRegistExtDetail> details = new ArrayList<>();
//            for (BsBankCardVO card: cards) {
//                BatchRegistExtDetail detail = new BatchRegistExtDetail();
//                detail.setDetail_no(String.valueOf(card.getUserId()));
//                detail.setName(card.getCardOwner());
//                detail.setId_type("1");
//                detail.setId_code(card.getIdCard());
//                detail.setMobile(card.getObligateMobile());
//                detail.setEmail(null);
//                detail.setSex(null);
//                detail.setCus_type(null);
//                detail.setBirthday(null);
//                detail.setOpen_branch(null);
//                detail.setCard_no(card.getCardNo());
//                detail.setCard_type("1");
//                detail.setPre_mobile(card.getObligateMobile());
//                detail.setPay_code(Constants.HF_PAY_CODE_BAOFOO);
//                detail.setNotify_url(null);
//                detail.setRemark("批量绑卡");
//                details.add(detail);
//            }
//            req.setOrder_no(Util.generateOrderNo4BaoFoo(8));
//            req.setPartner_trans_date(new Date());
//            req.setPartner_trans_time(new Date());
//            logger.info("批量绑卡请求，订单号：{}", req.getOrder_no());
//            B2GResMsg_HFBank_BatchBindCard4Elements res = hfbankTransportService.batchBindCard4Elements(req);
//            logger.info("批量绑卡 {} 总条数：{}，其中成功条数：{}", res.getOrder_no(), res.getTotal_num(), res.getSuccess_num());
//            if(null != res) {
//                if(!CollectionUtils.isEmpty(res.getSuccess_data())) {
//                    bindSuccess(res.getSuccess_data());
//                }
//                if(!CollectionUtils.isEmpty(res.getError_data())) {
//                    bindError(res.getError_data());
//                }
//            }
//            pageNum++;
//            batchBindCard();
//        }
//    }
//
//    private void bindSuccess(List<BatchRegistExtSuccess> successes) {
//        for (BatchRegistExtSuccess success: successes) {
//            BsBankCardExample bankCardExample = new BsBankCardExample();
//            bankCardExample.createCriteria()
//                    .andUserIdEqualTo(Integer.parseInt(success.getDetail_no())).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
//            List<BsBankCard> cards = bsBankCardMapper.selectByExample(bankCardExample);
//            BsBankCard card = cards.get(0);
//            BsPayReceipt payReceipt = new BsPayReceipt();
//            payReceipt.setUserId(card.getUserId());
//            payReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
//            payReceipt.setUpdateTime(new Date());
//            BsPayReceiptExample payReceiptExample = new BsPayReceiptExample();
//            payReceiptExample.createCriteria().andUserIdEqualTo(card.getUserId());
//            bsPayReceiptMapper.updateByExampleSelective(payReceipt, payReceiptExample);
//
//            BsPayReceipt bsPayReceipt = new BsPayReceipt();
//            bsPayReceipt.setBankCardNo(card.getCardNo());
//            bsPayReceipt.setUserId(card.getUserId());
//            bsPayReceipt.setUserName(card.getCardOwner());
//            bsPayReceipt.setBankName(BaoFooEnum.cardBinMap.get(card.getBankId()));
//            bsPayReceipt.setIdCard(card.getIdCard());
//            bsPayReceipt.setMobile(card.getMobile());
//            bsPayReceipt.setChannel(Constants.CHANNEL_HFBANK);
//            bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_YES);
//            bsPayReceipt.setCreateTime(new Date());
//            bsPayReceipt.setUpdateTime(new Date());
//            bsPayReceiptMapper.insertSelective(bsPayReceipt);
//
//            BsHfbankUserExt bsHfbankUserExt = new BsHfbankUserExt();
//            bsHfbankUserExt.setUserId(card.getUserId());
//            bsHfbankUserExt.setHfUserId(success.getPlatcust());
//            bsHfbankUserExt.setHfRegistTime(new Date());
//            bsHfbankUserExt.setHfBindCardTime(new Date());
//            bsHfbankUserExt.setStatus(Constants.HFBANK_USER_EXT_STATUS_OPEN);
//            bsHfbankUserExt.setNote(null);
//            bsHfbankUserExt.setCreateTime(new Date());
//            bsHfbankUserExt.setUpdateTime(new Date());
//            bsHfbankUserExtMapper.insertSelective(bsHfbankUserExt);
//        }
//    }
//
//    private void bindError(List<BatchRegistExtError> errors) {
//        for (BatchRegistExtError error: errors) {
//            // 1. 银行卡信息置为失败
//            // 2. 支付签约回执表置为失败
//            // 3. 用户绑卡置为未绑卡
//
//            BsBankCardExample bankCardExample = new BsBankCardExample();
//            bankCardExample.createCriteria()
//                    .andUserIdEqualTo(Integer.parseInt(error.getDetail_no())).andStatusEqualTo(Constants.BANK_CARD_NORMAL);
//            List<BsBankCard> cards = bsBankCardMapper.selectByExample(bankCardExample);
//            BsBankCard card = cards.get(0);
//            // 1. 银行卡信息置为失败
//            BsBankCard bsBankCard = new BsBankCard();
//            bsBankCard.setStatus(Constants.BANK_CARD_BINDFAIL);
//            bsBankCard.setNote(error.getError_info());
//            bsBankCard.setUnbindTime(new Date());
//            BsBankCardExample bsBankCardExample = new BsBankCardExample();
//            bsBankCardExample.createCriteria().andCardNoEqualTo(card.getCardNo()).andUserIdEqualTo(card.getUserId())
//                    .andIdCardEqualTo(card.getIdCard()).andMobileEqualTo(card.getMobile())
//                    .andStatusEqualTo(Constants.BANK_CARD_NORMAL)
//                    .andCardOwnerEqualTo(card.getCardOwner());
//            bsBankCardMapper.updateByExample(bsBankCard, bsBankCardExample);
//
//            // 2. 支付签约回执表置为失败
//            BsPayReceipt payReceipt = new BsPayReceipt();
//            payReceipt.setUserId(card.getUserId());
//            payReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
//            payReceipt.setUpdateTime(new Date());
//            BsPayReceiptExample payReceiptExample = new BsPayReceiptExample();
//            payReceiptExample.createCriteria().andUserIdEqualTo(card.getUserId());
//            bsPayReceiptMapper.updateByExampleSelective(payReceipt, payReceiptExample);
//
//            BsPayReceipt bsPayReceipt = new BsPayReceipt();
//            bsPayReceipt.setBankCardNo(card.getCardNo());
//            bsPayReceipt.setUserId(card.getUserId());
//            bsPayReceipt.setUserName(card.getCardOwner());
//            bsPayReceipt.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(card.getBankId())));
//            bsPayReceipt.setIdCard(card.getIdCard());
//            bsPayReceipt.setMobile(card.getMobile());
//            bsPayReceipt.setChannel(Constants.CHANNEL_HFBANK);
//            bsPayReceipt.setIsBindCard(Constants.BAOFOO_BIND_NO);
//            bsPayReceipt.setCreateTime(new Date());
//            bsPayReceipt.setUpdateTime(new Date());
//            bsPayReceiptMapper.insertSelective(bsPayReceipt);
//
//            // 3. 用户绑卡置为未绑卡
//            BsUser user = new BsUser();
//            user.setId(card.getUserId());
//            user.setIsBindBank(Constants.IS_BIND_BANK_NO);
//            user.setIsBindName(Constants.IS_BIND_NAME_NO);
//            bsUserMapper.updateByPrimaryKeySelective(user);
//        }
//    }
}
