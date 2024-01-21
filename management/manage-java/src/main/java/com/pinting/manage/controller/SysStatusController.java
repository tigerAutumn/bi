package com.pinting.manage.controller;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.accounting.service.impl.process.SignSeal4ZanClaimsAgreementProcess;
import com.pinting.business.dao.*;
import com.pinting.business.enums.ScheduleJobEnums;
import com.pinting.business.hessian.manage.message.ReqMsg_MSystem_status;
import com.pinting.business.hessian.manage.message.ResMsg_MSystem_status;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSealUserInfoVO;
import com.pinting.business.service.manage.MSysStatusService;
import com.pinting.business.service.site.RegularSiteService;
import com.pinting.business.util.Constants;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.schedule.hessian.message.B2SReqMsg_QuartzSchedule_PauseJob;
import com.pinting.schedule.hessian.message.B2SReqMsg_QuartzSchedule_UpdateJobCron;
import com.pinting.schedule.out.service.ScheduleTransportService;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;

/**
 * 系统管理--系统维护
 * @author bianyatian
 * @2017-5-19 下午3:19:02
 */
@Controller
public class SysStatusController {
	
	@Autowired
	private HessianService manageService;
	@Autowired
	private MSysStatusService mSysStatusService;
	@Autowired
	private ScheduleTransportService scheduleTransportService;
	@Autowired
	private BsScheduleConfigMapper bsScheduleConfigMapper;
	@Autowired
	private SignSealService signSealService;
	@Autowired
	private RegularSiteService regularSiteService;
	@Autowired
	private LnLoanRelationMapper lnLoanRelationMapper;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private LnUserMapper lnUserMapper;
	@Autowired
	private LnLoanMapper lnLoanMapper;
	
	private Logger log = LoggerFactory.getLogger(SysStatusController.class);
	
	private static ScheduledExecutorService executor;
	static {
		executor = Executors.newScheduledThreadPool(20);
	}
	
	/**
	 * 系统状态界面显示
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/maintainace/index")
	public String sysMaintainace(ReqMsg_MSystem_status reqMsg,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		// 分页数据
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = com.pinting.util.Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = com.pinting.util.Constants.MANAGE_DEFAULT_NUMPERPAGE;
			reqMsg.setPageNum(pageNum);
			reqMsg.setNumPerPage(numPerPage);
		}
		
		ResMsg_MSystem_status resMsg = (ResMsg_MSystem_status) manageService.handleMsg(reqMsg);
		model.put("pageNum", reqMsg.getPageNum());
		model.put("numPerPage", reqMsg.getNumPerPage());
		model.put("totalRows", resMsg.getTotalRows());
		model.put("roleGrid", resMsg.getBsValueList());
		model.put("req", reqMsg);
		return "/system/maintainace";
		
	}
	
	/**
	 * 系统状态修改，支持批量
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/sys/maintainace/updateSysStatus")
	public @ResponseBody Map<Object, Object> sysStatusMaintainaceUpdate(@RequestParam(value = "scheduleIds[]") String[] scheduleIds,
			Integer statusValue, HttpServletRequest request, HttpServletResponse response) {
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		if (scheduleIds != null && scheduleIds.length > 0) {
			BsSysStatus reqMsg = null;	
			for (String scheduleId : scheduleIds) {
				reqMsg = new BsSysStatus();
				reqMsg.setmUserId(Integer.parseInt(userId));
				reqMsg.setId(Integer.parseInt(scheduleId));
				reqMsg.setStatusValue(statusValue);
				boolean flag = mSysStatusService.updateBsStatus(reqMsg);
				if(flag){
					//定时服务处理
					BsSysStatus sysStatusTemp = mSysStatusService.findById(reqMsg.getId());
					if(com.pinting.business.util.Constants.SYS_STATUS_TRANS_TYPE_SCHEDULE.equals(sysStatusTemp.getTransType())
							&& com.pinting.business.util.Constants.SYS_STATUS_STATUS_VALUE_HANG == sysStatusTemp.getStatusValue()){
						autoStopSchedule(sysStatusTemp);
					}
					if(com.pinting.business.util.Constants.SYS_STATUS_TRANS_TYPE_SCHEDULE.equals(sysStatusTemp.getTransType())
							&& Constants.SYS_STATUS_STATUS_VALUE_NORMAL == sysStatusTemp.getStatusValue()){
						autoRunningSchedule(sysStatusTemp);
					}
					//普通交易处理，需要修改状态
					if((Constants.SYS_STATUS_TRANS_TYPE_TRANSACTION_FINANCE.equals(sysStatusTemp.getTransType())
							|| Constants.SYS_STATUS_TRANS_TYPE_TRANSACTION_LOAN.equals(sysStatusTemp.getTransType()))
							&& com.pinting.business.util.Constants.SYS_STATUS_STATUS_VALUE_HANG == sysStatusTemp.getStatusValue()){
						//不处理
					}
				} else {
					return ReturnDWZAjax.fail("操作失败！");
				}	
			}
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("没有操作的定时！");
		}
	}
	
	
	/**
	 * 跳转至系统状态修改界面
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/maintainace/toUpdate")
	public String sysMaintainaceToUpdate(HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {

		String idStr = request.getParameter("id");
		model.put("record", mSysStatusService.findById(Integer.parseInt(idStr)));
		return "/system/maintainace_update";

		
	}
	
	/**
	 * 系统状态修改
	 * @param reqMsg
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/maintainace/update")
	public @ResponseBody Map<Object, Object> sysMaintainaceUpdate(BsSysStatus reqMsg,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		reqMsg.setmUserId(Integer.parseInt(userId));

		
		boolean flag = mSysStatusService.updateBsStatus(reqMsg);
		if(flag){
			//定时服务处理
			BsSysStatus sysStatusTemp = mSysStatusService.findById(reqMsg.getId());
			if(com.pinting.business.util.Constants.SYS_STATUS_TRANS_TYPE_SCHEDULE.equals(sysStatusTemp.getTransType())
					&& com.pinting.business.util.Constants.SYS_STATUS_STATUS_VALUE_HANG == sysStatusTemp.getStatusValue()){
				autoStopSchedule(sysStatusTemp);
			}
			if(com.pinting.business.util.Constants.SYS_STATUS_TRANS_TYPE_SCHEDULE.equals(sysStatusTemp.getTransType())
					&& Constants.SYS_STATUS_STATUS_VALUE_NORMAL == sysStatusTemp.getStatusValue()){
				autoRunningSchedule(sysStatusTemp);
			}
			//普通交易处理，需要修改状态
			if((Constants.SYS_STATUS_TRANS_TYPE_TRANSACTION_FINANCE.equals(sysStatusTemp.getTransType())
					|| Constants.SYS_STATUS_TRANS_TYPE_TRANSACTION_LOAN.equals(sysStatusTemp.getTransType()))
					&& com.pinting.business.util.Constants.SYS_STATUS_STATUS_VALUE_HANG == sysStatusTemp.getStatusValue()){
				//不处理
			}
			
			return ReturnDWZAjax.success("操作成功！");
		}else{
			return ReturnDWZAjax.fail("操作失败！");
		}
		
		
	}

	/**
	 * 自动开启定时任务
	 * @param sysStatus
	 */
	private ScheduledFuture autoRunningSchedule(final BsSysStatus sysStatus) {
		String[] str = sysStatus.getTransCode().split("\\.");
		BsScheduleConfigExample example = new BsScheduleConfigExample();
		example.createCriteria().andJobGroupEqualTo(str[0]).andJobNameEqualTo(str[1]);
		List<BsScheduleConfig> list = bsScheduleConfigMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(list)){
			if(sysStatus.getTransCode() != null
					&& sysStatus.getTransCode().startsWith(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode())){
				B2SReqMsg_QuartzSchedule_UpdateJobCron req = new B2SReqMsg_QuartzSchedule_UpdateJobCron();
				req.setJobId(list.get(0).getId());
				req.setCronExpression(list.get(0).getCronExpression());
				scheduleTransportService.updateJobCron(req);
				log.info("============SCHEDULE定时任务:"+sysStatus.getTransCode()+"当即开启==========");
			}else if(sysStatus.getTransCode() != null
					&& sysStatus.getTransCode().startsWith(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode())){
				//business不处理
				log.info("============SCHEDULE定时任务:"+sysStatus.getTransCode()+"不处理==========");
			}
		}

		return null;

	}

	private Integer getScheduleConfigId(String transCode) {
		String[] str = transCode.split("\\.");
		BsScheduleConfigExample example = new BsScheduleConfigExample();
		example.createCriteria().andJobGroupEqualTo(str[0]).andJobNameEqualTo(str[1]);
		
		List<BsScheduleConfig> list = bsScheduleConfigMapper.selectByExample(example);
		if(CollectionUtils.isNotEmpty(list)){
			return list.get(0).getId();
		}
		return -1;
	}

	/**
	 * 自动停止定时任务
	 * @param sysStatus
	 */
	private ScheduledFuture autoStopSchedule(final BsSysStatus sysStatus) {
		final Integer scheduleConfigId = getScheduleConfigId(sysStatus.getTransCode());
		if(sysStatus.getTransCode() != null
				&& sysStatus.getTransCode().startsWith(ScheduleJobEnums.JOB_GROUP_SCHEDULE.getCode())){
			B2SReqMsg_QuartzSchedule_PauseJob req = new B2SReqMsg_QuartzSchedule_PauseJob();
			req.setJobId(scheduleConfigId);
			log.info("============SCHEDULE定时任务:"+sysStatus.getTransCode()+"当即关闭==========");
			scheduleTransportService.pauseJob(req);
		}else if(sysStatus.getTransCode() != null
				&& sysStatus.getTransCode().startsWith(ScheduleJobEnums.JOB_GROUP_BUSINESS.getCode())){
			//business不处理
			log.info("============SCHEDULE定时任务:"+sysStatus.getTransCode()+"不处理==========");
		}

		return null;
	}

	/**
	 * 进入执行赞分期债转协议签章页面
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/sys/zanSignatureIndex")
	public String repayIndex(HttpServletRequest request, Map<String, Object> model) {
		return "/system/zanSignature_index";
	}

	/**
	 * 执行赞分期债转协议签章操作
	 * @param request
	 * @param loanRelationId
	 * @return
	 */
	@ResponseBody
	@RequestMapping("/sys/zanExecutionSignature")
	public Map<String, Object> executionSignature(HttpServletRequest request, String loanRelationId) {
		Map<String, Object> result = new HashMap<>();
		try {
			//赞分期债转协议签章
			SignSeal4ZanClaimsAgreementProcess zanClaimsAgreementProcess = new SignSeal4ZanClaimsAgreementProcess();
			List<LnLoanRelation> relationList = new ArrayList<LnLoanRelation>();

			LnLoanRelation lnLoanRelation =  lnLoanRelationMapper.selectByPrimaryKey(Integer.parseInt(loanRelationId));
			LnUser lnUser = lnUserMapper.selectByPrimaryKey(lnLoanRelation.getLnUserId());
			LnLoan lnLoan = lnLoanMapper.selectByPrimaryKey(lnLoanRelation.getLoanId());

			List<BsUser4LoanVO> voList = new ArrayList<>();
			BsUser4LoanVO vo = new BsUser4LoanVO();
			BsUser bsUser = bsUserMapper.selectByPrimaryKey(lnLoanRelation.getBsUserId());
			vo.setUserId(bsUser.getId());
			vo.setUserIdCardNo(bsUser.getIdCard());
			vo.setUserName(bsUser.getUserName());
			voList.add(vo);

			SignSealUserInfoVO signSealUserInfo4LoanAgr = new SignSealUserInfoVO();
			signSealUserInfo4LoanAgr.setIdCard(lnUser.getIdCard());
			signSealUserInfo4LoanAgr.setPdfPath(GlobEnvUtil.get("cfca.seal.pdfSrcPath"));
			signSealUserInfo4LoanAgr.setUserId(lnUser.getId());
			signSealUserInfo4LoanAgr.setUserName(lnUser.getUserName());
			signSealUserInfo4LoanAgr.setOrderNo(lnLoan.getPartnerLoanId());
			signSealUserInfo4LoanAgr.setMoney(String.valueOf(lnLoan.getApproveAmount()));
			zanClaimsAgreementProcess.setSignSealUserInfo(signSealUserInfo4LoanAgr);
			zanClaimsAgreementProcess.setLnLoanRelation(lnLoanRelation);
			zanClaimsAgreementProcess.setSignSealService(signSealService);
			zanClaimsAgreementProcess.setRegularSiteService(regularSiteService);
			new Thread(zanClaimsAgreementProcess).start();

			log.info("赞分期债转协议签章成功");
			result.put("statusCode", "200");
		} catch (Exception e) {
			log.info("赞分期债转协议签章异常：{}", e.getMessage());
			result.put("statusCode", "300");
			result.put("message", e.getMessage());
		}
		return result;
	}
}
