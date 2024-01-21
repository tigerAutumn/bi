package com.pinting.manage.controller;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.ProtocolSealVO;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.LnLoan;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.manage.SignRepeatSendService;
import com.pinting.business.service.protocol.LoanAgreementSignSealService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ReturnDWZAjax;

/**
 * 协议签章相关
 * @project manage-java
 * @title SignSealController.java
 * @author Dragon & cat
 * @date 2017-6-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Controller
public class SignSealController {

	private Logger log = LoggerFactory.getLogger(SignSealController.class);

	@Autowired
	private SignRepeatSendService signRepeatSendService;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
	@Autowired
	private LoanAgreementSignSealService loanAgreementSignSealService;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	
	/**
	 * 协议签章重发--进入重发页面
	 * 目前协议签章重发只需要重发两类协议
	 * 1、云贷四方协议（LOAN_AGREEMENT）
	 * 		四方协议不成功状态包含FAIL和UNDOWNLOAD，所以需要对两种状态都做到可重入
	 * 2、赞分期借款咨询与服务协议（BORROW_SERVICES）
	 * 		借款咨询与服务协议只有FAIL状态
	 * 
	 * @return
	 */
	@RequestMapping("/signSeal/signRepeat")
	public String signRepeat(String type,HttpServletRequest request,HttpServletResponse response,Map<String, Object> model){
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		pageNum = StringUtil.isBlank(pageNum) ? "1" : pageNum;
		numPerPage = StringUtil.isBlank(numPerPage) ? "20" : numPerPage;
		
		Integer userSealFileCount =  signRepeatSendService.countSignRepeatData(type);
		List<BsUserSealFile> userSealFiles = new ArrayList<>();
		if (userSealFileCount != null && userSealFileCount >0 ) {
			Integer start = (Integer.valueOf(pageNum) <= 1) ? 0 : ((Integer.valueOf(pageNum) - 1) * Integer.valueOf(numPerPage));
			userSealFiles =  signRepeatSendService.querySignRepeatData(type,start,Integer.valueOf(numPerPage));
		}
		model.put("totalRows", userSealFileCount);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
		model.put("type", type);
		model.put("userSealFiles", userSealFiles);
        return "signSeal/sign_repeat_index";
	}
	
	
	/**
	 * 重发
	 * @param req
	 * @param pubilsh
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/signSeal/signRepeatSend")
	public @ResponseBody Map<Object, Object> signRepeatSend(Integer id,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		log.info("签章请求重发数据id = " + id);
		if (id == null) {
			return ReturnDWZAjax.fail("请选择重发数据！");
		}
		BsUserSealFile bsUserSealFile = bsUserSealFileMapper.selectByPrimaryKey(id);
		if (bsUserSealFile == null) {
			log.error("签章请求重发>>>>查询不到对应的签章数据");
			return ReturnDWZAjax.fail("查询不到对应数据！");
		}
		

		
		if ("LOAN_AGREEMENT".equals(bsUserSealFile.getSealType()) && "YUN_DAI_SELF".equals(bsUserSealFile.getUserSrc())) {
			
			BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.LOAN_AGREEMENT_CHANGE_THREE_PART_TIME);
			if (bsSysConfig == null) {
				log.error("签章请求重发>>>>查询不到对应的配置表信息（LOAN_AGREEMENT_CHANGE_THREE_PART_TIME 借款协议四方改三方生效时间）");
				return ReturnDWZAjax.fail("查询不到对应配置数据！LOAN_AGREEMENT_CHANGE_THREE_PART_TIME");
			}
			
			Date loanAgreeChangeTime = null;
			try {
				loanAgreeChangeTime = DateUtil.parse(bsSysConfig.getConfValue(), "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			
			
			LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
			if (DateUtil.compareTo(loanAgreeChangeTime, lnLoan.getLoanTime())> 0) {
				signRepeatSendService.signLoanAgreementYun(id);
			}else {
				ProtocolSealVO protocolSeal = new ProtocolSealVO(bsUserSealFile.getAgreementNo(), lnLoan);
				loanAgreementSignSealService.protocolSeal(PartnerEnum.YUN_DAI_SELF, protocolSeal);
			}

		} else if ("LOAN_AGREEMENT".equals(bsUserSealFile.getSealType()) && "7_DAI_SELF".equals(bsUserSealFile.getUserSrc())) {
			
			BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.LOAN_AGREEMENT_CHANGE_THREE_PART_TIME);
			if (bsSysConfig == null) {
				log.error("签章请求重发>>>>查询不到对应的配置表信息（LOAN_AGREEMENT_CHANGE_THREE_PART_TIME 借款协议四方改三方生效时间）");
				return ReturnDWZAjax.fail("查询不到对应配置数据！LOAN_AGREEMENT_CHANGE_THREE_PART_TIME");
			}
			
			Date loanAgreeChangeTime = null;
			try {
				loanAgreeChangeTime = DateUtil.parse(bsSysConfig.getConfValue(), "yyyy-MM-dd HH:mm:ss");
			} catch (ParseException e) {
				e.printStackTrace();
			}
			LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
			if (DateUtil.compareTo(loanAgreeChangeTime, lnLoan.getLoanTime())> 0) {
				signRepeatSendService.signLoanAgreement7(id);
			}else {
				ProtocolSealVO protocolSeal = new ProtocolSealVO(bsUserSealFile.getAgreementNo(), lnLoan);
				loanAgreementSignSealService.protocolSeal(PartnerEnum.SEVEN_DAI_SELF, protocolSeal);
			}
		}else if ("BORROW_SERVICES".equals(bsUserSealFile.getSealType()) && "ZSD".equals(bsUserSealFile.getUserSrc())) {
			try {
				signRepeatSendService.signBorrowServicesZsd(id);
			} catch (Exception e) {
				log.error("签章请求重发异常");
				return ReturnDWZAjax.fail("重发异常！");
			}
		} else if ("BORROW_SERVICES".equals(bsUserSealFile.getSealType()) && "ZAN".equals(bsUserSealFile.getUserSrc())) {
			try {
				signRepeatSendService.signBorrowServicesZan(id);
			} catch (Exception e) {
				log.error("签章请求重发异常");
				return ReturnDWZAjax.fail("重发异常！");
			}
		}else {
			log.error("签章请求重发>>>>重发数据类型暂不支持 type = "+ bsUserSealFile.getSealType() +",userSrc = " + bsUserSealFile.getUserSrc());
			return ReturnDWZAjax.fail("重发数据类型暂不支持！");
		}
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}  
		return ReturnDWZAjax.success("重发成功！");
		
	
	}
	

}
