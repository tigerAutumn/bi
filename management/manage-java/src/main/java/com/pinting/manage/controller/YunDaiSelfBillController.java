package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanExample;
import com.pinting.business.model.LnRepayScheduleExample;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.YunDaiSelfBillVO;
import com.pinting.business.service.manage.YunDaiSelfBillService;
import com.pinting.business.util.DateUtil;
import com.pinting.core.exception.PTException;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ReturnDWZAjax;

/**
 * 云贷自主放款账单相关
 * @project manage-java
 * @title SignSealController.java
 * @author Dragon & cat
 * @date 2017-6-1
 * @Copyright 2015 BiGangWan.com Inc. All rights reserved.
 * @Description
 */
@Controller
public class YunDaiSelfBillController {
	private Logger log = LoggerFactory.getLogger(YunDaiSelfBillController.class);
	
	@Autowired
	private YunDaiSelfBillService yunDaiSelfBillService;
	
	@Autowired
	private LnRepayScheduleMapper lnRepayScheduleMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	
	@RequestMapping("/yunDaiSelf/bill/index")
	public String index(HttpServletRequest request,HttpServletResponse response,Map<String, Object> model){
		String pageNum = request.getParameter("pageNum");
		String numPerPage = request.getParameter("numPerPage");
		pageNum = StringUtil.isBlank(pageNum) ? "1" : pageNum;
		numPerPage = StringUtil.isBlank(numPerPage) ? "20" : numPerPage;
		Date time = com.pinting.core.util.DateUtil.addDays(new Date(), -7);
		String startTime = StringUtil.isBlank(request.getParameter("startTime")) ? DateUtil.getDate(time) : request.getParameter("startTime");
		String endTime = StringUtil.isBlank(request.getParameter("endTime")) ?  DateUtil.getDate(new Date()) : request.getParameter("endTime");
		Integer count =  lnRepayScheduleMapper.countLoanNoBillData(startTime, endTime);
		List<YunDaiSelfBillVO> yunDaiSelfBillVOs = new ArrayList<>();
		if (count != null && count >0 ) {
			Integer start = (Integer.valueOf(pageNum) <= 1) ? 0 : ((Integer.valueOf(pageNum) - 1) * Integer.valueOf(numPerPage));
			yunDaiSelfBillVOs =  lnRepayScheduleMapper.queryLoanNoBillData(start, Integer.valueOf(numPerPage), startTime, endTime);
		}
		model.put("startTime", startTime);
		model.put("endTime", endTime);
		model.put("totalRows", count);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
		model.put("list", yunDaiSelfBillVOs);
        return "yunDaiSelf/bill/index";
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
	@RequestMapping("/yunDaiSelf/bill/repeatSend")
	public @ResponseBody Map<Object, Object> repeatSend(String partnerLoanId,String partnerUserId,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		log.info("签章请求重发数据partnerLoanId = " + partnerLoanId +";partnerUserId = " + partnerUserId);
		if (StringUtil.isBlank(partnerLoanId) || StringUtil.isBlank(partnerUserId)) {
			return ReturnDWZAjax.fail("请选择重发数据！");
		}
		
		try {
			yunDaiSelfBillService.syncBill(partnerLoanId,partnerUserId);
		} catch (PTMessageException e) {
			e.printStackTrace();
			return ReturnDWZAjax.fail(e.getErrCode()+":"+e.getErrMessage());
		}  
		return ReturnDWZAjax.success("同步成功！");
	}
	
	
	/**
	 * 批量重发
	 * @param req
	 * @param pubilsh
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/yunDaiSelf/bill/repeatSendBatch")
	public @ResponseBody Map<Object, Object> repeatSendBatch(String ids,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		
		
		log.info("签章请求重发数据ids = " + ids);
		if (StringUtil.isBlank(ids)) {
			return ReturnDWZAjax.fail("请选择重发数据！");
		}
		String[]  idList = ids.split(",");
		
		for (String partnerLoanId : idList) {
			LnLoanExample example = new LnLoanExample();
			example.createCriteria().andPartnerLoanIdEqualTo(partnerLoanId).andStatusEqualTo("PAIED");
			final List<LnLoan> lnLoans = lnLoanMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(lnLoans)) {
				return ReturnDWZAjax.fail("查询不到对应的借款数据！["+partnerLoanId+"]");
			}
			
			final LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoans.get(0).getLnUserId());
			
			Thread t = new Thread(new Runnable(){  
		            public void run(){  
		        		try {
		        			log.info("批量账单同步，同步开始："+lnLoans.get(0).getPartnerLoanId());
		        			yunDaiSelfBillService.syncBill(lnLoans.get(0).getPartnerLoanId(),lnUser.getPartnerUserId());
		        		} catch (PTMessageException e) {
		        			e.printStackTrace();
		        			log.info("批量账单同步，同步失败："+lnLoans.get(0).getPartnerLoanId());
		        		}  
		            }});  
		    t.start();  
			
		}
		
		return ReturnDWZAjax.success("同步成功！");
	}

}
