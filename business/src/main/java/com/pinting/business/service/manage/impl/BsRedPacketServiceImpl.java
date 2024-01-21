package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsAgentMapper;
import com.pinting.business.dao.BsAutoRedPacketRuleMapper;
import com.pinting.business.dao.BsRedPacketApplyMapper;
import com.pinting.business.dao.BsRedPacketCheckMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.dao.BsRedPacketPreDetailMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.dao.MUserMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ReqMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketDisable;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_AutoRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GetNewAutoRedPacket;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_GrantManagermentGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_ManualRedPocketReview;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckGrid;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckRefuse;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketCheckPass;
import com.pinting.business.hessian.manage.message.ResMsg_RedPacket_RedPacketStatistics;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.model.BsAutoRedPacketRule;
import com.pinting.business.model.BsAutoRedPacketRuleExample;
import com.pinting.business.model.BsRedPacketApply;
import com.pinting.business.model.BsRedPacketApplyExample;
import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.MUser;
import com.pinting.business.model.vo.AutoRedPocketReviewVO;
import com.pinting.business.model.vo.BsRedPacketCheckVO;
import com.pinting.business.model.vo.BsRedPacketPreDetailVO;
import com.pinting.business.model.vo.RedPacketStatisticsVO;
import com.pinting.business.service.manage.BsRedPackatInfoService;
import com.pinting.business.service.manage.BsRedPacketService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;

@Service
public class BsRedPacketServiceImpl implements BsRedPacketService {

	@Autowired
	BsRedPacketCheckMapper bsRedPacketCheckMapper;
	@Autowired
	BsAutoRedPacketRuleMapper bsAutoRedPacketRuleMapper;
	@Autowired
	BsAgentMapper bsAgentMapper;
	@Autowired
	BsRedPacketPreDetailMapper bsRedPacketPreDetailMapper;
	@Autowired
	MUserMapper mUserMapper;
	@Autowired
	BsRedPackatInfoService bsRedPackatInfoService;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	BsRedPacketInfoMapper bsRedPacketInfoMapper; 
	@Autowired
	BsRedPacketApplyMapper bsRedPacketApplyMapper;
	@Autowired
	private BsUserMapper userMapper;
	
	
	@Override
	public void grantManagermentGrid(ReqMsg_RedPacket_GrantManagermentGrid req,
			ResMsg_RedPacket_GrantManagermentGrid res) {
/*	    private String serialNo;
	    
	    private String policyType;
	    
	    private String termLimit;
	    
	    private String applicantTimeStart;
	    private String applicantTimeEnd;
	    
	    private String amountMin;
	    private String amountMax;*/
		List<BsRedPacketCheckVO>  vos = bsRedPacketCheckMapper.selectGrantManagermentGrid(req.getSerialNameSearch(), 
				req.getDistributeTypeSearch(), req.getCheckStatus(),req.getStatus(),
				req.getSerialNoSearch(),req.getPolicyTypeSearch(),req.getTermLimitSearch(),req.getApplicantTimeStart(),req.getApplicantTimeEnd(),
				req.getAmountMin(),req.getAmountMax(), req.getStart(), req.getNumPerPage());
		Integer count = bsRedPacketCheckMapper.selectGrantManagermentCount(req.getSerialNameSearch(), req.getDistributeTypeSearch(), 
				req.getCheckStatus(),req.getStatus(),req.getSerialNoSearch(),req.getPolicyTypeSearch(),req.getTermLimitSearch(),req.getApplicantTimeStart(),req.getApplicantTimeEnd(),
				req.getAmountMin(),req.getAmountMax());
		
		List<BsAgent> bsAgentList = bsAgentMapper.selectAllAgentList();
		
		//处理查询条件信息计算渠道个数
		for (BsRedPacketCheckVO bsRedPacketCheckVO : vos) {
			if (Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_MANUAL.equals(bsRedPacketCheckVO.getDistributeType())) {
				String[] queryHistory = bsRedPacketCheckVO.getManualConditions().split("；");
				if (queryHistory.length == 7) {
					String[] tempString = queryHistory[6].split("：");
					if (tempString.length == 1) {
						bsRedPacketCheckVO.setChannelNum(0);
					}else {
						String[] channelString = tempString[1].split(",");
						bsRedPacketCheckVO.setChannelNum(channelString.length);
					}

				}
			}else if (Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO.equals(bsRedPacketCheckVO.getDistributeType())) {
				if (bsRedPacketCheckVO.getAgentIds().contains("-1")) {
					bsRedPacketCheckVO.setChannelNum(bsAgentList.size()+1);
				}else {
					String[] tempString = bsRedPacketCheckVO.getAgentIds().split(",");
					bsRedPacketCheckVO.setChannelNum(tempString.length);
				}
			}
			
		}

		res.setBudgetBalance(0.0);
		res.setDataGrid(BeanUtils.classToArrayList(vos));
		res.setCount(count);
	}


	@Override
	public void autoRedPocketDisable(ReqMsg_RedPacket_AutoRedPocketDisable req,
			ResMsg_RedPacket_AutoRedPocketDisable res) {
		BsRedPacketCheck bsRedPacketCheck =  bsRedPacketCheckMapper.selectByPrimaryKey(req.getId());
		if (bsRedPacketCheck == null || !Constants.RED_PACKET_CHECK_STATUS_PASS.equals(bsRedPacketCheck.getCheckStatus())) {
			 throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_NOT_EXIST);
		}
		
		BsAutoRedPacketRuleExample bsAutoRedPacketRuleExample = new BsAutoRedPacketRuleExample();
		bsAutoRedPacketRuleExample.createCriteria().andSerialNoEqualTo(bsRedPacketCheck.getSerialNo());
		List<BsAutoRedPacketRule> bsAutoRedPacketRules = bsAutoRedPacketRuleMapper.selectByExample(bsAutoRedPacketRuleExample);
		
		if (CollectionUtils.isEmpty(bsAutoRedPacketRules)) {
			throw new PTMessageException(PTMessageEnum.AUTO_RED_POCKET_NOT_EXIST);
		}
		
		bsAutoRedPacketRules.get(0).setStatus(Constants.AUTO_RED_PACKET_STATUS_DISABLE);
		bsAutoRedPacketRules.get(0).setUpdateTime(new Date());
		bsAutoRedPacketRuleMapper.updateByPrimaryKeySelective(bsAutoRedPacketRules.get(0));
	}


	@Override
	public void autoRedPocketReview(ReqMsg_RedPacket_AutoRedPocketReview req,
			ResMsg_RedPacket_AutoRedPocketReview res) {
		AutoRedPocketReviewVO autoRedPocketReviewVO = bsAutoRedPacketRuleMapper.autoRedPocketReview(req.getId());
		
		//查询渠道信息
		String idsTemp = "";
		String[] ids = autoRedPocketReviewVO.getAgentIds().split(",");
		List<Integer> List = new ArrayList<Integer>();

		if (autoRedPocketReviewVO.getAgentIds().contains("-1")) {
			idsTemp = "普通用户,";
			BsAgentExample example = new BsAgentExample();
			example.createCriteria();
			List<BsAgent> agentList = bsAgentMapper.selectByExample(example);
			for (BsAgent bsAgent : agentList) {
				idsTemp = idsTemp + bsAgent.getAgentName()+",";
			}

		}else {
			for (String string : ids) {
				if ("0".equals(string)) {
					idsTemp = "普通用户,";
				}
				List.add(Integer.parseInt(string));
			}
			BsAgentExample example = new BsAgentExample();
			example.createCriteria().andIdIn(List);
			List<BsAgent> agentList = bsAgentMapper.selectByExample(example);
			for (BsAgent bsAgent : agentList) {
				idsTemp = idsTemp + bsAgent.getAgentName()+",";
			}
		}
		autoRedPocketReviewVO.setAgentIds(idsTemp);
		
		//处理通知管道
		if (autoRedPocketReviewVO.getNotifyChannel() != null && !"".equals(autoRedPocketReviewVO.getNotifyChannel())) {
			String[] notifyChannnel = autoRedPocketReviewVO.getNotifyChannel().split(",");
			List<String> notifyChannnelList = Arrays.asList(notifyChannnel);
			res.setNotifyList(notifyChannnelList);
		}

		res.setAutoPocketMap(BeanUtils.classToHashMap(autoRedPocketReviewVO));
	}

	
	

	@Override
	public void manualRedPocketReview(
			ReqMsg_RedPacket_ManualRedPocketReview req,
			ResMsg_RedPacket_ManualRedPocketReview res) {
		List<BsRedPacketPreDetailVO>  vos = bsRedPacketPreDetailMapper.manualRedPocketReview(req.getId(), req.getStart(), req.getNumPerPage());
		Integer count = bsRedPacketPreDetailMapper.manualRedPocketCount(req.getId());
		BsRedPacketCheckVO bsRedPacketCheckVO = bsRedPacketCheckMapper.selectManualRedPocketReview(req.getId());
		
		//处理通知管道
		if (bsRedPacketCheckVO.getNotifyChannel() != null && !"".equals(bsRedPacketCheckVO.getNotifyChannel())) {
			String[] notifyChannnel = bsRedPacketCheckVO.getNotifyChannel().split(",");
			List<String> notifyChannnelList = Arrays.asList(notifyChannnel);
			res.setNotifyList(notifyChannnelList);
		}

		//处理查询条件信息
		ArrayList<HashMap<String,Object>> queryHistoryList = new ArrayList<HashMap<String,Object>>();
		String[] queryHistory = bsRedPacketCheckVO.getManualConditions().split("；");
		for (String string : queryHistory) {
			String[] tempString = string.split("：");
			HashMap<String, Object> queryHistoryMap = new HashMap<String, Object>();
			queryHistoryMap.put("name", tempString[0]);
			queryHistoryMap.put("content", tempString[1]);
			queryHistoryList.add(queryHistoryMap);
		}
		

		res.setManualPocketMap(BeanUtils.classToHashMap(bsRedPacketCheckVO));
		res.setQueryHistoryList(queryHistoryList);
		res.setDataGrid(BeanUtils.classToArrayList(vos));
		res.setCount(count);
	}


	@Override
	public void addAutoPacketCheck(BsRedPacketCheck check) {
		check.setCheckStatus(Constants.RED_PACKET_CHECK_STATUS_UNCHECKED);//未审核
		check.setDistributeType(Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO);//自动红包
		check.setCreateTime(new Date());
		check.setUpdateTime(new Date());
		bsRedPacketCheckMapper.insertSelective(check);
	}
	
	
	@Override
	public void addAutoPacketRule(BsAutoRedPacketRule rule) {
		rule.setStatus(Constants.AUTO_RED_PACKET_STATUS_AVAILABLE);//可用
		rule.setCreateTime(new Date());
		rule.setUpdateTime(new Date());
		bsAutoRedPacketRuleMapper.insertSelective(rule);
		
	}


	@Override
	public void redPacketCheckGrid(ReqMsg_RedPacket_RedPacketCheckGrid req,
			ResMsg_RedPacket_RedPacketCheckGrid res) {
		List<BsRedPacketCheckVO>  vos = bsRedPacketCheckMapper.selectRedPacketCheckGrid(req.getSerialNameSearch(), req.getDistributeTypeSearch(), req.getCheckStatus(),req.getStatus(),
				req.getSerialNoSearch(),req.getPolicyTypeSearch(),req.getTermLimitSearch(),req.getApplicantTimeStart(),req.getApplicantTimeEnd(),
				req.getAmountMin(),req.getAmountMax(),req.getStart(), req.getNumPerPage());
		Integer count = bsRedPacketCheckMapper.selectRedPacketCheckCount(req.getSerialNameSearch(), req.getDistributeTypeSearch(), 
				req.getCheckStatus(),req.getStatus(),req.getSerialNoSearch(),req.getPolicyTypeSearch(),req.getTermLimitSearch(),req.getApplicantTimeStart(),req.getApplicantTimeEnd(),
				req.getAmountMin(),req.getAmountMax());
		
		
		List<BsAgent> bsAgentList = bsAgentMapper.selectAllAgentList();
		//处理查询条件信息计算渠道个数
		for (BsRedPacketCheckVO bsRedPacketCheckVO : vos) {
			if (Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_MANUAL.equals(bsRedPacketCheckVO.getDistributeType())) {
				String[] queryHistory = bsRedPacketCheckVO.getManualConditions().split("；");
				if (queryHistory.length == 7) {
					String[] tempString = queryHistory[6].split("：");
					if (tempString.length == 1) {
						bsRedPacketCheckVO.setChannelNum(0);
					}else {
						String[] channelString = tempString[1].split(",");
						bsRedPacketCheckVO.setChannelNum(channelString.length);
					}

				}
			}else if (Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO.equals(bsRedPacketCheckVO.getDistributeType())) {
				if (bsRedPacketCheckVO.getAgentIds().contains("-1")) {
					bsRedPacketCheckVO.setChannelNum(bsAgentList.size()+1);
				}else {
					String[] tempString = bsRedPacketCheckVO.getAgentIds().split(",");
					bsRedPacketCheckVO.setChannelNum(tempString.length);
				}
			}
			
		}

		res.setBudgetBalance(0.0);
		res.setDataGrid(BeanUtils.classToArrayList(vos));
		res.setCount(count);
	}


	@Override
	public void redPacketCheckPass(ReqMsg_RedPacket_RedPacketCheckPass req,
			ResMsg_RedPacket_RedPacketCheckPass res) {
		if (req.getId() == null || req.getMuserId() == null) {
			throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_PARAM_ERROR);
		}
		MUser mUser = mUserMapper.selectByPrimaryKey(req.getMuserId());
		if (mUser == null) {
			throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_MANAGER_ERROR);
		}
		//修改批次表信息
		final BsRedPacketCheck bsRedPacketCheck = bsRedPacketCheckMapper.selectByPrimaryKey(req.getId());
		bsRedPacketCheck.setUpdateTime(new Date());
		bsRedPacketCheck.setCheckStatus(Constants.RED_PACKET_CHECK_STATUS_PASS);
		bsRedPacketCheck.setChecker(mUser.getId());
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				
				bsRedPacketCheckMapper.updateByPrimaryKeySelective(bsRedPacketCheck);
				//如果是手动红包，需要进行发放操作
				//bsRedPacketCheckMapper.insertRedPocketInfo(bsRedPacketCheck.getId());
			}
		});
		

		//发送通知
/*		if (Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_MANUAL.equals(bsRedPacketCheck.getDistributeType())) {
			//手动红包发信息
			//查询用户ID List
			List<Integer> userIdList = bsRedPacketCheckMapper.queryManualRedPcketUserIdList(bsRedPacketCheck.getSerialNo());
			//查询用户手机号List
			List<String>  mobileList = bsRedPacketCheckMapper.queryManualRedPcketUserMobileList(bsRedPacketCheck.getSerialNo());
			
			if (bsRedPacketCheck.getNotifyChannel() != null && !"".equals(bsRedPacketCheck.getNotifyChannel())) {
				String[] notifyChannnel = bsRedPacketCheck.getNotifyChannel().split(",");
				for (String string : notifyChannnel) {
					if (Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_SMS.equals(string)) {
						bsRedPackatInfoService.sendRedPacketMessage(1, mobileList, null, bsRedPacketCheck.getSerialName(), String.valueOf(bsRedPacketCheck.getAmount()));
					}
					if (Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_WECHAT.equals(string)) {
						bsRedPackatInfoService.sendRedPacketMessage(2, null, userIdList, bsRedPacketCheck.getSerialName(), String.valueOf(bsRedPacketCheck.getAmount()));
					}
					if (Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_APP.equals(string)) {
						bsRedPackatInfoService.sendRedPacketMessage(3, null, userIdList, bsRedPacketCheck.getSerialName(), String.valueOf(bsRedPacketCheck.getAmount()));
					}
				}
			
			}
			
		}else if (Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_AUTO.equals(bsRedPacketCheck.getDistributeType())) {
			//自动红包无需 发送消息
			
		}else {
			
			throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_TYPE_ERROR);
		}*/

	
	}


	@Override
	public void redPacketCheckRefuse(ReqMsg_RedPacket_RedPacketCheckRefuse req,
			ResMsg_RedPacket_RedPacketCheckRefuse res) {
		if (req.getId() == null || req.getMuserId() == null) {
			throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_PARAM_ERROR);
		}
		MUser mUser = mUserMapper.selectByPrimaryKey(req.getMuserId());
		if (mUser == null) {
			throw new PTMessageException(PTMessageEnum.RED_POCKET_CHECK_MANAGER_ERROR);
		}
		//修改批次表信息
		BsRedPacketCheck bsRedPacketCheck = bsRedPacketCheckMapper.selectByPrimaryKey(req.getId());
		bsRedPacketCheck.setUpdateTime(new Date());
		bsRedPacketCheck.setCheckStatus(Constants.RED_PACKET_CHECK_STATUS_REFUSE);
		bsRedPacketCheck.setChecker(mUser.getId());
		bsRedPacketCheckMapper.updateByPrimaryKeySelective(bsRedPacketCheck);
	}


	@Override
	public void redPacketStatistics(ReqMsg_RedPacket_RedPacketStatistics req,
			ResMsg_RedPacket_RedPacketStatistics res) {
	/*		1、查询统计数据列表
			2、查询统计数据数量
			3、查询条件--预算来源
			4、查询条件--预算余额
			5、预算总金额
			6、预算余额
	*/	
		
		//1、查询统计数据列表
		List<RedPacketStatisticsVO>  statisticsDataList = bsRedPacketCheckMapper.selectStatisticsData(req.getSerialName(), req.getApplyNo(), req.getApplicant(), req.getStart(), req.getNumPerPage());
		Integer total = 0;
		Double totalAmount = 0.0;
		Integer usedRedPocket= 0;
		Double usedRedPocketAmount = 0.0;
		Integer initRedPocket= 0;
		Double initRedPocketAmount= 0.0;
		Integer overRedPocket= 0;
		Double overRedPocketAmount= 0.0;
		Double buyAmount= 0.0;
		Double buyYearAmount= 0.0;
		
		for (RedPacketStatisticsVO redPacketStatisticsVO : statisticsDataList) {
			if (redPacketStatisticsVO.getBuyAmount() == null ) {
				redPacketStatisticsVO.setBuyAmount(0.0);
			}
			if (redPacketStatisticsVO.getBuyYearAmount() == null ) {
				redPacketStatisticsVO.setBuyYearAmount(0.0);
			}
			total = total + redPacketStatisticsVO.getTotal();
			totalAmount = MoneyUtil.add(totalAmount, redPacketStatisticsVO.getTotalAmount()).doubleValue();
			usedRedPocket= usedRedPocket + redPacketStatisticsVO.getUsedRedPocket();
			usedRedPocketAmount = MoneyUtil.add(usedRedPocketAmount, redPacketStatisticsVO.getUsedRedPocketAmount()).doubleValue();
			initRedPocket= initRedPocket + redPacketStatisticsVO.getInitRedPocket();
			initRedPocketAmount= MoneyUtil.add(initRedPocketAmount, redPacketStatisticsVO.getInitRedPocketAmount()).doubleValue();
			overRedPocket= overRedPocket + redPacketStatisticsVO.getOverRedPocket();
			overRedPocketAmount= MoneyUtil.add(overRedPocketAmount, redPacketStatisticsVO.getOverRedPocketAmount()).doubleValue();
			buyAmount= MoneyUtil.add(buyAmount, redPacketStatisticsVO.getBuyAmount()).doubleValue();
			buyYearAmount= MoneyUtil.add(buyYearAmount, redPacketStatisticsVO.getBuyYearAmount()).doubleValue();
		}
		statisticsDataList.get(statisticsDataList.size()-1).setTotal(total);
		statisticsDataList.get(statisticsDataList.size()-1).setTotalAmount(totalAmount);
		statisticsDataList.get(statisticsDataList.size()-1).setUsedRedPocket(usedRedPocket);
		statisticsDataList.get(statisticsDataList.size()-1).setUsedRedPocketAmount(usedRedPocketAmount);
		statisticsDataList.get(statisticsDataList.size()-1).setInitRedPocket(initRedPocket);
		statisticsDataList.get(statisticsDataList.size()-1).setInitRedPocketAmount(initRedPocketAmount);
		statisticsDataList.get(statisticsDataList.size()-1).setOverRedPocket(overRedPocket);
		statisticsDataList.get(statisticsDataList.size()-1).setOverRedPocketAmount(overRedPocketAmount);
		statisticsDataList.get(statisticsDataList.size()-1).setBuyAmount(buyAmount);
		statisticsDataList.get(statisticsDataList.size()-1).setBuyYearAmount(buyYearAmount);
		
		//2、查询统计数据数量
		Integer  count = bsRedPacketCheckMapper.selectStatisticsCount(req.getSerialName(), req.getApplyNo(), req.getApplicant());
		//3、查询条件--预算来源
		BsRedPacketApplyExample bsRedPacketApplyExample = new BsRedPacketApplyExample();
		bsRedPacketApplyExample.createCriteria().andCheckStatusEqualTo(Constants.RED_PACKET_APPLY_CHECK_STATUS_PASS);
		List<BsRedPacketApply>  bsRedPacketApplyList = bsRedPacketApplyMapper.selectByExample(bsRedPacketApplyExample);
		//4、查询条件--申请人
		List<MUser> applicantList = mUserMapper.selectApplicantListStatistics();
		
		//5、预算总金额
		Double  budgetTotal =	bsRedPacketApplyMapper.selectTotalBudget(req.getApplyNo());
		//6、预算余额
		//Double  budgetAvailableAmount =	bsRedPacketApplyMapper.selectBudgetAvailableAmount(req.getApplyNo());
		
		res.setDataGrid(BeanUtils.classToArrayList(statisticsDataList));
		res.setCount(count);
		res.setRpNameGrid(BeanUtils.classToArrayList(bsRedPacketApplyList));
		res.setApplicantGrid(BeanUtils.classToArrayList(applicantList));
		res.setBudgetTotal(budgetTotal);
		//res.setBudgetAmount(budgetAvailableAmount);
	}


	@Override
	public void manualRedPacketSend(String ids) {
		if (StringUtil.isNotEmpty(ids)) {
			final String[] checkIds = ids.split(",");
			final Map<Integer,List<BsRedPacketCheck>> map = new HashMap<Integer,List<BsRedPacketCheck>>();
			transactionTemplate.execute(new TransactionCallbackWithoutResult(){
				@Override
				protected void doInTransactionWithoutResult(TransactionStatus status) {
					for (String checkId : checkIds) {
						if(StringUtil.isNotEmpty(checkId.trim())) {
							BsRedPacketCheck bsRedPacketCheck = bsRedPacketCheckMapper.selectByPrimaryKey(Integer.valueOf(checkId.trim()));
							if(Constants.RED_PACKET_CHECK_STATUS_PASS.equals(bsRedPacketCheck.getCheckStatus()) 
									&& Constants.RED_PACKET_CHECK_DISTRIBUTE_TYPE_MANUAL.equals(bsRedPacketCheck.getDistributeType()) 
									&& !Constants.RED_PACKET_MSG_STATUS_FINISHED.equals(bsRedPacketCheck.getMsgStatus() == null ? "" : bsRedPacketCheck.getMsgStatus())) {
								bsRedPacketCheck.setMsgStatus(Constants.RED_PACKET_MSG_STATUS_FINISHED);
								bsRedPacketCheck.setUpdateTime(new Date());
								//修改批次表信息
								bsRedPacketCheckMapper.updateByPrimaryKeySelective(bsRedPacketCheck);
								//发放手动红包
								bsRedPacketCheckMapper.insertRedPocketInfo(bsRedPacketCheck.getId());
								
								List<Integer> userIdList = bsRedPacketCheckMapper.queryManualRedPcketUserIdList(bsRedPacketCheck.getSerialNo());
								for (Integer userId : userIdList) {
									if(map.get(userId) != null) {
										List<BsRedPacketCheck> list = (ArrayList<BsRedPacketCheck>)map.get(userId);
										list.add(bsRedPacketCheck);
										map.put(userId, list);
									}else {
										List<BsRedPacketCheck> list = new ArrayList<BsRedPacketCheck>();
										list.add(bsRedPacketCheck);
										map.put(userId, list);
									}
								}
							}
						}
				    }
				}
			});
			
			//调用发送消息的方法
			if(!map.isEmpty() && map.size() > 0) {
				for (Map.Entry<Integer,List<BsRedPacketCheck>> entry : map.entrySet()) {
					sendMessages(entry.getKey(),entry.getValue());
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}
	
	//发送消息
	private void sendMessages(Integer userId,List<BsRedPacketCheck> redPacketCheckList){
		//发信息提醒用户
		//发给谁
		if(userId == null){
			return;
		}
		
		BsUser user = userMapper.selectByPrimaryKey(userId);
		if(user == null) {
			return;
		}
		
		List<String> mobiles = new ArrayList<String>();
		mobiles.add(user.getMobile());
		List<Integer> userIds = new ArrayList<Integer>();
		userIds.add(user.getId());
		
		String channels = "";
		//发什么内容
		double amount = 0;
		for (BsRedPacketCheck redPacketCheck : redPacketCheckList) {
			channels += redPacketCheck.getNotifyChannel() + ",";
			amount = MoneyUtil.add(amount, redPacketCheck.getAmount()).doubleValue();
		}
		
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_SMS)){//只要包含SMS都要发
			//短信通知
			bsRedPackatInfoService.sendRedPacketMessage(Constants.RED_PACKET_CHECK_NOTIFY_TYPE_SMS, mobiles, null, "抵扣红包", String.valueOf(amount));
		}
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_WECHAT)){//只要包含WECHAT都要发
			//微信通知
			bsRedPackatInfoService.sendRedPacketMessage(Constants.RED_PACKET_CHECK_NOTIFY_TYPE_WECHAT, null, userIds, "抵扣红包", String.valueOf(amount));
		}
		if(StringUtil.isNotEmpty(channels) && channels.contains(Constants.RED_PACKET_CHECK_NOTIFY_CHANNEL_APP)){//只要包含APP都要发
			//APP通知
			bsRedPackatInfoService.sendRedPacketMessage(Constants.RED_PACKET_CHECK_NOTIFY_TYPE_APP, null, userIds, "抵扣红包",  String.valueOf(amount));
		}
	}


	@Override
	public void getNewAutoRedPacket(ReqMsg_RedPacket_GetNewAutoRedPacket req,
			ResMsg_RedPacket_GetNewAutoRedPacket res) {
		
		AutoRedPocketReviewVO autoRedPocketReviewVO = bsAutoRedPacketRuleMapper.selectNewAutoRedPacket();
		
		if("-1".equals(autoRedPocketReviewVO.getAgentIds())){
			
		}
		
		//处理渠道信息
		if(StringUtil.isNotBlank(autoRedPocketReviewVO.getAgentIds())){
			String[] agentIds = autoRedPocketReviewVO.getAgentIds().split(",");
			List<String> agentList = Arrays.asList(agentIds);
			res.setAgentList(agentList);
		}
		//处理限用标的
		if(StringUtil.isNotBlank(autoRedPocketReviewVO.getTermLimit())){
			String[] terms = autoRedPocketReviewVO.getTermLimit().split(",");
			List<String> termList = Arrays.asList(terms);
			res.setTermList(termList);
		}
		
		//处理通知管道
		if (autoRedPocketReviewVO.getNotifyChannel() != null && !"".equals(autoRedPocketReviewVO.getNotifyChannel())) {
			String[] notifyChannnel = autoRedPocketReviewVO.getNotifyChannel().split(",");
			List<String> notifyChannnelList = Arrays.asList(notifyChannnel);
			res.setNotifyList(notifyChannnelList);
		}

		res.setAutoPocketMap(BeanUtils.classToHashMap(autoRedPocketReviewVO));
	}
}
