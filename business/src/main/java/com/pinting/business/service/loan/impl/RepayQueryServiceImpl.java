package com.pinting.business.service.loan.impl;

import com.pinting.business.dao.LnRepayMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.vo.LnRepayVO;
import com.pinting.business.service.loan.RepayQueryService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.loan.G2BReqMsg_Repay_QueryRepayResult;
import com.pinting.gateway.hessian.message.loan.G2BResMsg_Repay_QueryRepayResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * Created by babyshark on 2016/9/9.
 */
@Service
public class RepayQueryServiceImpl implements RepayQueryService{
    @Autowired
    private LnRepayMapper repayMapper;
    /**
     * 还款结果查询
     *
     * @param req
     * @param res
     * @throws Exception
     */
    @Override
    public void queryRepayResult(G2BReqMsg_Repay_QueryRepayResult req,
                                 G2BResMsg_Repay_QueryRepayResult res) throws Exception {
        if(StringUtil.isBlank(req.getOrderNo())){
            throw new PTMessageException(PTMessageEnum.ZAN_ADDLOANCIF_NOT_ENOUGH);
        }
        List<LnRepayVO> lnRepayVOs = repayMapper.selectByPartnerOrderNo(req.getOrderNo());
        if(CollectionUtils.isEmpty(lnRepayVOs)){
            //throw new PTMessageException(PTMessageEnum.ZAN_ORDER_NO_NOT_EXIST);
            res.setRepayResultCode("FAIL");
            res.setRepayResultMsg(PTMessageEnum.ZAN_ORDER_NO_NOT_EXIST.getMessage());
            return;
        }
        LnRepayVO lnRepayVO = lnRepayVOs.get(0);
        res.setChannel(lnRepayVO.getChannel());
        res.setLoanId(lnRepayVO.getPartnerLoanId());
        if(Constants.LN_REPAY_PAY_STATUS_REPAIED.equals(lnRepayVO.getStatus())){
            // 还款成功
            res.setRepayResultCode("SUCCESS");
            res.setRepayResultMsg("成功");
        }else if(Constants.LN_REPAY_PAY_STATUS_REPAY_FAIL.equals(lnRepayVO.getStatus())){
            // 还款失败
            res.setRepayResultCode("FAIL");
            res.setRepayResultMsg(lnRepayVO.getReturnMsg());
        }else if(Constants.LN_REPAY_PAY_STATUS_REPAYING.equals(lnRepayVO.getStatus())){
            // 还款中
            res.setRepayResultCode("PROCESS");
            res.setRepayResultMsg(lnRepayVO.getReturnMsg());
        }
    }
}
