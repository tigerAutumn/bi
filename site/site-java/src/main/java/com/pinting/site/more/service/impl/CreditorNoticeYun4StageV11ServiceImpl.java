package com.pinting.site.more.service.impl;

import com.pinting.business.hessian.site.message.ReqMsg_Match_GetCreditorNoticeInfo;
import com.pinting.business.hessian.site.message.ResMsg_Match_GetCreditorNoticeInfo;
import com.pinting.core.util.DateUtil;
import com.pinting.site.communicate.CommunicateBusiness;
import com.pinting.site.more.service.AgreeCompensateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;

/**
 * 代偿通知书协议(V1)——云贷等额本息,等本等息
 *
 * @author zousheng
 */
@Service("creditorNoticeYun4StageV11ServiceImpl")
public class CreditorNoticeYun4StageV11ServiceImpl implements AgreeCompensateService {

    @Autowired
    private CommunicateBusiness siteService;

    public Object execute(HttpServletRequest request, Map<String, Object> model) {
        ReqMsg_Match_GetCreditorNoticeInfo reqClaims = new ReqMsg_Match_GetCreditorNoticeInfo();
        //合作方借款编号
        String loanId = request.getParameter("loanId");
        reqClaims.setLoanId(loanId);

        //代偿协议签订时间
        model.put("createTime", DateUtil.formatDateTime(new Date(), "yyyy年MM月dd日"));

        // 合作方编码
        String partnerEnum = request.getParameter("partnerEnum");
        reqClaims.setPartnerEnum(partnerEnum);
        model.put("partnerEnum", partnerEnum);

        ResMsg_Match_GetCreditorNoticeInfo resClaimsList = (ResMsg_Match_GetCreditorNoticeInfo) siteService.handleMsg(reqClaims);

        //云贷自主放款债权受让人名字
        model.put("yunDaiSelfUserName", resClaimsList.getYunDaiSelfUserName());
        //云贷自主放款债权受让人身份证号
        model.put("yunDaiSelfIdCard", resClaimsList.getYunDaiSelfIdCard());
        //借款编号
        model.put("loanAgreementNumber", resClaimsList.getLoanAgreementNumber());
        //借款人姓名/身份证号码
        model.put("loanUserName", resClaimsList.getLoanUserName());
        model.put("loanIdCard", resClaimsList.getLoanIdCard());

        model.put("dataGrid", resClaimsList.getDataGrid());
        return model;
    }
}
