package com.pinting.site.more.service.impl;

import com.pinting.business.hessian.site.message.ReqMsg_Match_GetReceiptConfirmInfoList;
import com.pinting.business.hessian.site.message.ResMsg_Match_GetReceiptConfirmInfoList;
import com.pinting.core.util.DateUtil;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.more.service.AgreeCompensateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 代偿确认函协议(V1)——云贷等额本息,等本等息
 *
 * @author zousheng
 */
@Service("receiptConfirmYun4StageV11ServiceImpl")
public class ReceiptConfirmYun4StageV11ServiceImpl implements AgreeCompensateService {

    @Autowired
    private CommunicateBusiness siteService;

    public Object execute(HttpServletRequest request, Map<String, Object> model) {
        ReqMsg_Match_GetReceiptConfirmInfoList reqClaims = new ReqMsg_Match_GetReceiptConfirmInfoList();

        //合作方借款编号
        String loanId = request.getParameter("loanId");
        reqClaims.setLoanId(loanId);

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetReceiptConfirmInfoList resClaimsList = (ResMsg_Match_GetReceiptConfirmInfoList) siteService.handleMsg(reqClaims);
        if (resClaimsList != null && !"".equals(resClaimsList)) {
            model.put("dataGrid", resClaimsList.getDataGrid());
        }
        return model;
    }
}
