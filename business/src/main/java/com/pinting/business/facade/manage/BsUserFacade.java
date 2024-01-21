package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.vo.InterestRepaymentVO;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import com.pinting.business.accounting.service.PayOrdersService;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.SmsSignEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsPayOrders;
import com.pinting.business.model.BsRecordList;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.BsFeedbackVO;
import com.pinting.business.model.vo.BsRecordListVo;
import com.pinting.business.model.vo.BsUserAssetVO;
import com.pinting.business.model.vo.BsUserVO;
import com.pinting.business.service.manage.AgentService;
import com.pinting.business.service.manage.BsSmsRecordJnlService;
import com.pinting.business.service.manage.BsSysConfigService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.AddressUtil;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.business.util.IdcardUtils;
import com.pinting.business.util.WelinkUtil.WelinkSMSUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;

/**
 * @Project: business
 * @Title: BsUserFacade.java
 * @author Dong Wenkai update by yanwl
 * @date 2015-1-28 下午3:16:55
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("BsUser")
public class BsUserFacade{
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private BankCardService bankCardService;
	@Autowired
	private UserService userService;
	@Autowired
	private AgentService agentService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private BsSysConfigService bsSysConfigService;
	@Autowired
	private BsSmsRecordJnlService bsSmsRecordJnlService;
	@Autowired
	private PayOrdersService payOrdersService;
	
	private Logger log = LoggerFactory.getLogger(BsUserFacade.class);
	
	public void bsUserListQuery(ReqMsg_BsUser_BsUserListQuery req, ResMsg_BsUser_BsUserListQuery resp) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		BsUserAssetVO bsUser = new BsUserAssetVO();
		bsUser.setPageNum(pageNum);
		bsUser.setNumPerPage(numPerPage);
		if (StringUtil.isNotEmpty(req.getSearchMobile())) {
			bsUser.setMobile(req.getSearchMobile());
		}
		if (StringUtil.isNotEmpty(req.getsIdCard())) {
			bsUser.setIdCard(req.getsIdCard());
		}
		if (StringUtil.isNotEmpty(req.getsBankCard())) {
			bsUser.setsBankCard(req.getsBankCard());
		}
		if (req.getsAgent() != null && req.getsAgent()!=0) {
			bsUser.setAgentId(Integer.valueOf(req.getsAgent()));
		}
		if (StringUtil.isNotEmpty(req.getsName())) {
			bsUser.setsName(req.getsName());
		}
		if (StringUtil.isNotEmpty(req.getsReward())) {
			bsUser.setsReward(req.getsReward());
		}
		if (StringUtil.isNotEmpty(req.geteReward())) {
			bsUser.seteReward(req.geteReward());
		}
		if (req.getSregistTime() != null) {
			bsUser.setsRegisterTime(req.getSregistTime());
		}
		if (req.getEregistTime() != null) {
			bsUser.seteRegisterTime(req.getEregistTime());
		}
		if(req.getsFirstBuyTime() != null) {
			bsUser.setsFirstBuyTime(req.getsFirstBuyTime());
		}
		if(req.geteFirstBuyTime() != null) {
			bsUser.seteFirstBuyTime(req.geteFirstBuyTime());
		}
		if (StringUtil.isNotEmpty(req.getOrderDirection()) &&StringUtil.isNotEmpty(req.getOrderField())) {
			bsUser.setOrderDirection(req.getOrderDirection());
			bsUser.setOrderField(req.getOrderField());
		}
		if(req.getUserId() != null) {
			bsUser.setId(req.getUserId());
		}
		if(StringUtil.isNotEmpty(req.getsBalance())) {
			bsUser.setsBalance(req.getsBalance());
		}
		if(StringUtil.isNotEmpty(req.geteBalance())) {
			bsUser.seteBalance(req.geteBalance());
		}
		if(StringUtil.isNotEmpty(req.getsTotalPrincipal())) {
			bsUser.setsTotalPrincipal(req.getsTotalPrincipal());
		}
		if(StringUtil.isNotEmpty(req.geteTotalPrincipal())) {
			bsUser.seteTotalPrincipal(req.geteTotalPrincipal());
		}
		if(StringUtil.isNotEmpty(req.getsSumBalance())) {
			bsUser.setsSumBalance(req.getsSumBalance());
		}
		if(StringUtil.isNotEmpty(req.geteSumBalance())) {
			bsUser.seteSumBalance(req.geteSumBalance());
		}
		if(StringUtil.isNotEmpty(req.getGender())) {
			bsUser.setGender(req.getGender());
		}
		if(StringUtil.isNotEmpty(req.getAge())) {
			bsUser.setAge(req.getAge());
		}
		if(req.getBankStatus() != null) {
			bsUser.setBankStatus(req.getBankStatus());
		}
		if(StringUtil.isNotEmpty(req.getBankName())) {
			bsUser.setBankName(req.getBankName());
		}
		if(StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] agentIds = req.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				bsUser.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(req.getNonAgentId())) {
			bsUser.setNonAgentId(req.getNonAgentId());
		}
		if (StringUtil.isNotEmpty(req.getQuestionnaireResult())) {
			bsUser.setQuestionnaireResult(req.getQuestionnaireResult());
		}
		if(req.getRegTerminal() != null) {
			bsUser.setRegTerminal(req.getRegTerminal());
		}
		
		if (req.getStatus() != null && req.getStatus() != 0) {
			bsUser.setStatus(req.getStatus());
		}
		
		int totalRows =  bsUserService.findAllUserCount(bsUser);
		if (totalRows > 0) {
			List<BsUserAssetVO> bsUserList =  bsUserService.findAllUser(bsUser);
			resp.setBsUserList(BeanUtils.classToArrayList(bsUserList));
		}
		resp.setTotalRows(totalRows > 0?totalRows:0);
	}
	
	/**
	 * 用户综合查询
	 * @param req
	 * @param resp
	 */
	public void bsUserComplexListQuery(ReqMsg_BsUser_BsUserComplexListQuery req, ResMsg_BsUser_BsUserComplexListQuery resp) {
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		BsUserAssetVO userVO = new BsUserAssetVO();
		userVO.setPageNum(pageNum);
		userVO.setNumPerPage(numPerPage);
		if (req.getSregistTime() != null && !"".equals(req.getSregistTime())) {
			userVO.setsRegisterTime(req.getSregistTime());
		}
		if (req.getEregistTime() != null && !"".equals(req.getEregistTime())) {
			userVO.seteRegisterTime(DateUtil.addDays(req.getEregistTime(), 1));
		}
		if (req.getBankStatus() != null && !"".equals(req.getBankStatus())) {
			userVO.setBankStatus(req.getBankStatus());
		}
		if (req.getOrderDirection() != null&& (!"".equals(req.getOrderDirection())) && req.getOrderField() != null && (!"".equals(req.getOrderField()))) {
			userVO.setOrderDirection(req.getOrderDirection());
			userVO.setOrderField(req.getOrderField());
		}
		if (StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] agentIds = req.getAgentIds().split(",");
			if (agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if (StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				userVO.setAgentIds(ids);
			}
		}
		if (StringUtil.isNotEmpty(req.getNonAgentId())) { // 渠道为空
			userVO.setNonAgentId(req.getNonAgentId());
		}
		int totalRows =  bsUserService.findUserComplexCount(userVO);
		if (totalRows > 0) {
			List<HashMap<String, Object>> userList = new ArrayList<HashMap<String,Object>> ();
			List<BsUserAssetVO> bsUserList =  bsUserService.findUserComplexList(userVO);
			for(int i=0;i<bsUserList.size();i++){
				BsUserAssetVO bsUserAssetVO = bsUserList.get(i);
				HashMap<String,Object> userAsset = new HashMap<String,Object>();
				userAsset.put("id",bsUserAssetVO.getId());
				if (StringUtil.isNotEmpty(bsUserAssetVO.getUserName())) {
					StringBuffer end = new StringBuffer("");
					for (int j = 0; j < bsUserAssetVO.getUserName().length()-1; j++) {
						end.append("*");
					}
					userAsset.put("name", bsUserAssetVO.getUserName().substring(0,1) + end);
				} else {
					userAsset.put("name", bsUserAssetVO.getUserName());
				}
				userAsset.put("mobile", bsUserAssetVO.getMobile());
				//是否已实名认证 
				userAsset.put("isBindName", bsUserAssetVO.getIsBindName());
				//是否绑定银行卡 
				userAsset.put("isBindBank", bsUserAssetVO.getIsBindBank());
				userAsset.put("idCard",bsUserAssetVO.getIdCard());
				userAsset.put("status", bsUserAssetVO.getStatus());
				userAsset.put("recommendNum", bsUserAssetVO.getRecommendNum());
				userAsset.put("recommendName",bsUserAssetVO.getRecommendName());
				userAsset.put("agentName", bsUserAssetVO.getAgentName());
				userAsset.put("firstBuyTime", bsUserAssetVO.getFirstBuyTime());
				//总资产=总余额+当前投资收益=投资本金+结算户余额（推荐奖励金）+当前投资收益
				userAsset.put("sumBalance", MoneyUtil.add( MoneyUtil.add((bsUserAssetVO.getTotalBalance() ==null?0:bsUserAssetVO.getTotalBalance()), 
						(bsUserAssetVO.getTotalBonus() == null?0:bsUserAssetVO.getTotalBonus())).doubleValue() ,
						(bsUserAssetVO.getCurrentInterest() ==null?0:bsUserAssetVO.getCurrentInterest())).doubleValue());
				userAsset.put("balance", bsUserAssetVO.getTotalBalance()==null?0:bsUserAssetVO.getTotalBalance());//账户余额
				//可用余额
				userAsset.put("availableBalance", bsUserAssetVO.getAvailableBalance());
				userAsset.put("currentBanlace", (bsUserAssetVO.getTotalBalance()==null?0:bsUserAssetVO.getTotalBalance()) - (bsUserAssetVO.getBalance()==null?0:bsUserAssetVO.getBalance()));//当前投资本金=总余额-结算户余额
//				userAsset.put("totalPrincipal", bsUserAssetVO.getTotalPrincipal()==null?0:bsUserAssetVO.getTotalPrincipal());//累计投资本金
				userAsset.put("totalBonus",bsUserAssetVO.getTotalBonus()==null?0:bsUserAssetVO.getTotalBonus());//累计推荐奖励
				userAsset.put("totalInterest", bsUserAssetVO.getTotalInterest()==null?0:bsUserAssetVO.getTotalInterest());//累计投资收益
				//银行卡绑定信息
				userAsset.put("bankName", bsUserAssetVO.getBankName());
				userAsset.put("bankCardNum", bsUserAssetVO.getBankCardNum());
				userAsset.put("cardNo", bsUserAssetVO.getCardNo());
				userAsset.put("bankStatus", bsUserAssetVO.getBankStatus());
				userAsset.put("registTime", bsUserAssetVO.getRegisterTime());
				// 年龄
				userAsset.put("age", bsUserAssetVO.getIdCard()==null?"":IdcardUtils.getAgeByIdCard(bsUserAssetVO.getIdCard()));
				// 性别
				userAsset.put("gender", bsUserAssetVO.getIdCard()==null?"":IdcardUtils.getGenderByIdCard(bsUserAssetVO.getIdCard()));
				// 存量资产
				userAsset.put("stockAssets", bsUserAssetVO.getStockAssets()==null?0:bsUserAssetVO.getStockAssets());
				// 最近一次投资时间
				userAsset.put("recentBuyTime", bsUserAssetVO.getRecentBuyTime()==null?0:bsUserAssetVO.getRecentBuyTime());
				//投资次数
				userAsset.put("investmentTimes", bsUserAssetVO.getInvestmentTimes()==null?0:bsUserAssetVO.getInvestmentTimes());
				// 近3个月投资次数
				userAsset.put("threeInvestmentTimes", bsUserAssetVO.getThreeInvestmentTimes()==null?0:bsUserAssetVO.getThreeInvestmentTimes());
				// 年化投资额
				userAsset.put("annualizedInvestment", bsUserAssetVO.getAnnualizedInvestment()==null?0:bsUserAssetVO.getAnnualizedInvestment());
				// 账户余额
				userAsset.put("accountBalance", bsUserAssetVO.getAccountBalance()==null?0:bsUserAssetVO.getAccountBalance());
				userList.add(userAsset);
			}
			resp.setBsUserList(userList);
		}
		resp.setTotalRows(totalRows > 0?totalRows:0);
		resp.setNumPerPage(numPerPage);
		resp.setPageNum(pageNum);
		resp.setsName(req.getsName());
		resp.setsReward(req.getsReward());
		resp.seteReward(req.geteReward());
		resp.setEregistTime(req.getEregistTime());
		resp.setSregistTime(req.getSregistTime());
		resp.setsRecommend(req.getsRecommend());
		resp.seteRecommend(req.geteRecommend());
		resp.setsIdCard(req.getsIdCard());
		resp.setsBankCard(req.getsBankCard());
		resp.setsAgent(req.getsAgent()!=null?String.valueOf(req.getsAgent()):null);
		resp.setsFirstBuyTime(req.getsFirstBuyTime());
		
	}
	
	/**
	 * 用户综合查询回访记录查询
	 */
	public void bsUserRecordListQuery(ReqMsg_BsUser_BsUserRecordListQuery req, ResMsg_BsUser_BsUserRecordListQuery res) {
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		BsRecordListVo record = new BsRecordListVo();
		record.setPageNum(pageNum);
		record.setNumPerPage(numPerPage);
		int userId = req.getUserId();
		record.setUserId(userId);
		int totalRows =  bsUserService.findUserRecordCount(record);
		if (totalRows > 0) {
			List<HashMap<String, Object>> userList = new ArrayList<HashMap<String,Object>> ();
			List<BsRecordListVo> recordList =  bsUserService.findUserRecordList(record);
			for(int i=0;i<recordList.size();i++){
				BsRecordListVo bsRecordListVo = recordList.get(i);
				HashMap<String,Object> bsRecord = new HashMap<String,Object>();
				bsRecord.put("userId", bsRecordListVo.getUserId());
				bsRecord.put("submitTime", bsRecordListVo.getSubmitTime());
				bsRecord.put("submitter", bsRecordListVo.getSubmitter());
				bsRecord.put("content", bsRecordListVo.getContent());
				userList.add(bsRecord);
			}
			res.setRecordList(userList);
		}
		res.setTotalRows(totalRows > 0?totalRows:0);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}
	
	/**
	 * 用户综合查询回访记录保存
	 */
	public void bsUserSaveRecord(ReqMsg_BsUser_BsUserSaveRecord req, ResMsg_BsUser_BsUserSaveRecord res) {
		BsRecordListVo record = new BsRecordListVo();
		record.setUserId(req.getUserId());
		record.setSubmitTime(req.getSubmitTime());
		record.setSubmitter(req.getSubmitter());
		record.setContent(req.getContent());
		try {
			bsUserService.addUserRecordList(record);
        } catch (Exception e) {
            e.printStackTrace();
            res.setResMsg("新增回访记录失败！");
        }
	}
	
	/**
	 * 用户标签管理
	 * @param req
	 * @param resp
	 */
	public void bsUserTagListQuery(ReqMsg_BsUser_BsUserTagListQuery req, ResMsg_BsUser_BsUserTagListQuery resp) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		BsUserAssetVO bsUser = new BsUserAssetVO();
		bsUser.setPageNum(pageNum);
		bsUser.setNumPerPage(numPerPage);
		if (StringUtil.isNotEmpty(req.getBuyBankType())) {
			bsUser.setBuyBankType(req.getBuyBankType());
		}
		if (StringUtil.isNotEmpty(req.getSearchMobile())) {
			bsUser.setMobile(req.getSearchMobile());
		}
		if (StringUtil.isNotEmpty(req.getsIdCard())) {
			bsUser.setIdCard(req.getsIdCard());
		}
		if (StringUtil.isNotEmpty(req.getsBankCard())) {
			bsUser.setsBankCard(req.getsBankCard());
		}
		if (req.getsAgent() != null && req.getsAgent()!=0) {
			bsUser.setAgentId(Integer.valueOf(req.getsAgent()));
		}
		if (StringUtil.isNotEmpty(req.getsName())) {
			bsUser.setsName(req.getsName());
		}
		if (StringUtil.isNotEmpty(req.getsReward())) {
			bsUser.setsReward(req.getsReward());
		}
		if (StringUtil.isNotEmpty(req.geteReward())) {
			bsUser.seteReward(req.geteReward());
		}
		if (req.getSregistTime() != null) {
			bsUser.setsRegisterTime(req.getSregistTime());
		}
		if (req.getEregistTime() != null) {
			bsUser.seteRegisterTime(req.getEregistTime());
		}
		if(req.getsFirstBuyTime() != null) {
			bsUser.setsFirstBuyTime(req.getsFirstBuyTime());
		}
		if(req.geteFirstBuyTime() != null) {
			bsUser.seteFirstBuyTime(req.geteFirstBuyTime());
		}
		if (StringUtil.isNotEmpty(req.getOrderDirection()) &&StringUtil.isNotEmpty(req.getOrderField())) {
			bsUser.setOrderDirection(req.getOrderDirection());
			bsUser.setOrderField(req.getOrderField());
		}
		if(req.getUserId() != null) {
			bsUser.setUserId(req.getUserId());
		}
		if(StringUtil.isNotEmpty(req.getsBalance())) {
			bsUser.setsBalance(req.getsBalance());
		}
		if(StringUtil.isNotEmpty(req.geteBalance())) {
			bsUser.seteBalance(req.geteBalance());
		}
		if(StringUtil.isNotEmpty(req.getsTotalPrincipal())) {
			bsUser.setsTotalPrincipal(req.getsTotalPrincipal());
		}
		if(StringUtil.isNotEmpty(req.geteTotalPrincipal())) {
			bsUser.seteTotalPrincipal(req.geteTotalPrincipal());
		}
		if(StringUtil.isNotEmpty(req.getsSumBalance())) {
			bsUser.setsSumBalance(req.getsSumBalance());
		}
		if(StringUtil.isNotEmpty(req.geteSumBalance())) {
			bsUser.seteSumBalance(req.geteSumBalance());
		}
		if(StringUtil.isNotEmpty(req.getGender())) {
			bsUser.setGender(req.getGender());
		}
		if(StringUtil.isNotEmpty(req.getAge())) {
			bsUser.setAge(req.getAge());
		}
		if(req.getBankStatus() != null) {
			bsUser.setBankStatus(req.getBankStatus());
		}
		if(StringUtil.isNotEmpty(req.getBankName())) {
			bsUser.setBankName(req.getBankName());
		}
		if(StringUtil.isNotEmpty(req.getAgentIds())) {
			String[] agentIds = req.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				bsUser.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(req.getNonAgentId())) {
			bsUser.setNonAgentId(req.getNonAgentId());
		}
		/** 投资次数起始值*/
		if(StringUtil.isNotEmpty(req.getsInvestmentTimes())) {
			bsUser.setsInvestmentTimes(req.getsInvestmentTimes());
		}
		/** 投资次数结束值*/
		if(StringUtil.isNotEmpty(req.geteInvestmentTimes())) {
			bsUser.seteInvestmentTimes(req.geteInvestmentTimes());
		}
		/** 累计投资收益起始值 */
		if(StringUtil.isNotEmpty(req.getsTotalInterest())) {
			bsUser.setsTotalInterest(req.getsTotalInterest());
		}
		/** 累计投资收益结束值 */
		if(StringUtil.isNotEmpty(req.geteTotalInterest())) {
			bsUser.seteTotalInterest(req.geteTotalInterest());
		}
		/** 当前投资本金 */
		if(StringUtil.isNotEmpty(req.getsCurrentBalance())) {
			bsUser.setsCurrentBalance(req.getsCurrentBalance());
		}
		if(StringUtil.isNotEmpty(req.geteCurrentBalance())) {
			bsUser.seteCurrentBalance(req.geteCurrentBalance());
		}
		/** 年龄　*/
		if(StringUtil.isNotEmpty(req.getsAge())) {
			bsUser.setsAge(req.getsAge());
		}
		if(StringUtil.isNotEmpty(req.geteAge())) {
			bsUser.seteAge(req.geteAge());
		}
		/** 标签名字 */
		if(StringUtil.isNotEmpty(req.getContent())) {
			bsUser.setContent(req.getContent());
		}
		/** 推荐人数  */
		if(StringUtil.isNotEmpty(req.getsRecommendNum())) {
			bsUser.setsRecommendNum(req.getsRecommendNum());
		}
		if(StringUtil.isNotEmpty(req.geteRecommendNum())) {
			bsUser.seteRecommendNum(req.geteRecommendNum());
		}
		if(CollectionUtils.isEmpty(req.getTagIdList())){
			bsUser.setTagIdList(null);
		} else {
			List<Integer> tagIdList = req.getTagIdList();
			List<Object> tagIdObj = new ArrayList<Object>();
			for (Integer integer : tagIdList) {
				if(integer.equals(-1)){
					bsUser.setNoTagId("yes");
				} else {
					tagIdObj.add(integer);
				}
			}
			bsUser.setTagIdList(tagIdObj);
			bsUser.setTagIdListSize(tagIdObj.size());
		}
		if(CollectionUtils.isEmpty(bsUser.getTagIdList())) {
			bsUser.setTagIdList(null);
		}
		int totalRows = bsUserService.findAllUserCountTag(bsUser);
		if (totalRows > 0) {
			List<BsUserAssetVO> bsUserList =  bsUserService.findAllUserTag(bsUser);
			resp.setBsUserList(BeanUtils.classToArrayList(bsUserList));
		}
		resp.setTotalRows(totalRows > 0?totalRows:0);
		List<BsPayOrders> buyBankTypeList = payOrdersService.findBuyBankTypeList();
		resp.setBuyBankTypeList(BeanUtils.classToArrayList(buyBankTypeList));
	}
	
	/**
	 * 进入user_id添加标签页面
	 * @param req
	 * @param resp
	 */
	public void bsUserAddTagsQuery(ReqMsg_BsUser_BsUserAddTagsQuery req, ResMsg_BsUser_BsUserAddTagsQuery resp) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		BsUserAssetVO bsUser = new BsUserAssetVO();
		bsUser.setPageNum(pageNum);
		bsUser.setNumPerPage(numPerPage);
		if(StringUtil.isNotEmpty(req.getUserIds())) {
			String trimStr = StringUtil.trimStr(req.getUserIds());
			String[] ids = trimStr.replace("，", ",").split(",");
			if(ids.length > 0) {
				List<Object> userIds = new ArrayList<Object>();
				for (String str : ids) {
					if(StringUtil.isNotEmpty(str.trim())) {
						try {
							userIds.add(Integer.valueOf(str.trim()));
						} catch (Exception e) {
							log.error("此用户Id："+str+"不符合规范");
						}
					}
				}
				//如果没有符合条件的id值，默认设置为0
				if(userIds.size() == 0) {
					userIds.add(0);
				}
				bsUser.setUserIds(userIds);
			}
		}
		int totalRows = bsUserService.findUserIdTag(bsUser);
		if (totalRows > 0) {
			List<BsUserAssetVO> bsUserList =  bsUserService.findUserIdTagList(bsUser);
			resp.setBsUserList(BeanUtils.classToArrayList(bsUserList));
		}
		resp.setTotalRows(totalRows > 0?totalRows:0);
	}
	
	/**
	 * 渠道列表 查询条件
	 * @param req
	 * @param res
	 */
	public void agentQueryList(ReqMsg_BsUser_AgentQueryList req, ResMsg_BsUser_AgentQueryList res) {
		List<BsAgent> list = agentService.findAgentsList();
		res.setAgentList(BeanUtils.classToArrayList(list));
	}
	
	public void bsSubUserListQuery(ReqMsg_BsUser_BsSubUserListQuery req, ResMsg_BsUser_BsSubUserListQuery resp) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		int totalRows = bsUserService.findSubUserCount(req.getUserId());
		if (totalRows > 0) {
			List<BsUserAssetVO> bsUserList =  bsUserService.findSubUser(req.getUserId(), pageNum, numPerPage);
			resp.setBsUserList(BeanUtils.classToArrayList(bsUserList));
		}
		resp.setTotalRows(totalRows);
		resp.setNumPerPage(numPerPage);
		resp.setPageNum(pageNum);
	}
	
	private String replaceBankCardNo(String old){
		String str = old.substring(0, 4);
		str += "*****";
		str += old.substring(old.length()-4);
		return str;
	}
	public void bsUserQuery(ReqMsg_BsUser_BsUserQuery req, ResMsg_BsUser_BsUserQuery resp) {
		BsUser bsUser =bsUserService.findUserByUserId(req.getUserId());
		if(bsUser!=null){
			resp.setUserId(bsUser.getId());
			resp.setNick(bsUser.getNick());
			resp.setUserName(bsUser.getUserName());
			resp.setMobile(bsUser.getMobile());
			resp.setEmail(bsUser.getEmail());
			resp.setIdCard(bsUser.getIdCard());
			resp.setStatus(bsUser.getStatus());
			resp.setIsBindBank(bsUser.getIsBindBank());
			List<BsBankCard> bankCardList = bankCardService.findBankCardInfoByUserId(bsUser.getId());
			List<HashMap<String, Object>> bankCardMapList=null;
			if(bankCardList!= null && bankCardList.size()>0){
				bankCardMapList = new ArrayList<HashMap<String,Object>>();
				for (BsBankCard bankCardVO : bankCardList) {
					HashMap<String, Object> bcMap=new HashMap<String, Object>();
					bcMap.put("id", bankCardVO.getId());
					bcMap.put("cardNo", bankCardVO.getCardNo());
					bcMap.put("cardNoSim", replaceBankCardNo(bankCardVO.getCardNo()));
					bcMap.put("cardOwner", bankCardVO.getCardOwner());
					bcMap.put("bankId", bankCardVO.getBankId());
					bcMap.put("bankName", bankCardVO.getBankName());
					bcMap.put("status", bankCardVO.getStatus());
					bcMap.put("bindTime", bankCardVO.getBindTime());
					bankCardMapList.add(bcMap);
				}
			}
			resp.setUserBankCardList(bankCardMapList);
		}
	}
	public void bsUserSave(ReqMsg_BsUser_BsUserSave req, ResMsg_BsUser_BsUserSave resp) {
		BsUser user = userService.findUserById(req.getUserId());
		if(userService.findUserByNick(req.getNick())!=null && (!req.getNick().equals(user.getNick()))){
			throw new PTMessageException(PTMessageEnum.NICK_IS_EXIT);
		}
		if(userService.findUserByMobile(req.getMobile())!=null && (!req.getMobile().equals(user.getMobile()))){
			throw new PTMessageException(PTMessageEnum.MOBILE_IS_EXIT);
		}
		if(userService.findUserByEmail(req.getEmail())!=null && (!"".equals(req.getEmail())) && (!req.getEmail().equals(user.getEmail()))){
			throw new PTMessageException(PTMessageEnum.EMAIL_IS_EXIT);
		}
		BsUser bsUser = new BsUser();
		bsUser.setId(req.getUserId());
		bsUser.setNick(req.getNick());
		bsUser.setUserName(req.getUserName());
		bsUser.setMobile(req.getMobile());
		bsUser.setEmail("".equals(req.getEmail().trim()) ? null : req.getEmail());
		bsUser.setIdCard(req.getIdCard());
		bsUser.setStatus(req.getStatus());
		userService.updateBsUser(bsUser);
//		smsService.sendMessage("13758116343", req.getControlName()+"修改了【"+user.getNick()+ "】的信息。");

	}
	public void bsUserStatusModify(ReqMsg_BsUser_BsUserStatusModify req, ResMsg_BsUser_BsUserStatusModify resp) {
		if(req.getStatus()<=0 || req.getStatus() >3){
			throw new PTMessageException(PTMessageEnum.USER_STATUS_WRONG);
		}
		String statusModify ="恢复";
		switch (req.getStatus()) {
		case 2:
			statusModify ="注销";
			break;
		case 3:
			statusModify ="禁用";
			break;
		}
		if (StringUtil.isNotBlank(req.getIds())) {
			String[] ids = req.getIds().split(",");
			List<Integer> idList = new ArrayList<Integer>();
			for (String id: ids) {
				idList.add(Integer.valueOf(id));
			}
			bsUserService.updateBsUsersStatus(idList, req.getStatus());
//			smsService.sendMessage("13758116343", req.getControlName()+"批量"+statusModify+"了-"+ids.length+ "名-用户。");
		} else if (req.getUserId() > 0) {
			
			BsUser bsUser = new BsUser();
			bsUser.setId(req.getUserId());
			bsUser.setStatus(req.getStatus());
			userService.updateBsUser(bsUser);
//			smsService.sendMessage("13758116343", req.getControlName()+statusModify+"了【"+userService.findUserById(req.getUserId()).getNick()+ "】用户。");
		}

	}
	
	public void feedbacksQuery(ReqMsg_BsUser_FeedbacksQuery req, ResMsg_BsUser_FeedbacksQuery res) {
		
		String pageNum = req.getPageNum();
		String numPerPage = req.getNumPerPage();
		
		int totalRows = bsUserService.countTotalBsFeedbacks();
		if(totalRows > 0){
			//bsUserService.findBsFeedbacksByPage(pageNum, numPerPage);
			List<BsFeedbackVO> list = bsUserService.findBsFeedbacksByPage(pageNum, numPerPage, 
					req.getOrderField(), req.getOrderDirection());
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setFeedbacks(mapList);
		}
		res.setPageNum(pageNum);
		res.setNumPerPage(numPerPage);
		res.setTotalRows(String.valueOf(totalRows));
		
	}
	
	public void bsUserList(ReqMsg_BsUser_BsUserList req,ResMsg_BsUser_BsUserList res){
		BsUser user = new BsUser();
		user.setMobile(req.getMobile());
		user.setUserName(req.getUserName());
		user.setPageNum(Integer.parseInt(req.getPageNum()));
		user.setNumPerPage(Integer.parseInt(req.getNumPerPage()));
		int totalRows = bsUserService.countBsUser(user);
		if (totalRows > 0) {
			List<BsUserVO> list = bsUserService.bsUserPage(user);
			ArrayList<HashMap<String, Object>> mapList = BeanUtils.classToArrayList(list);
			res.setUserList(mapList);
		}
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
		res.setTotalRows(String.valueOf(totalRows));
	}
	
	public void smsUser(ReqMsg_BsUser_SmsUser req,ResMsg_BsUser_SmsUser res){
		BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.EMERGENCY_MOBILE);
		String mobile = req.getMobile() + "," + bsSysConfig.getConfValue();
		mobile = mobile.replaceAll("，", ",");
		String[] mobiles = mobile.split(",");
		HashMap<String, Object> map = new HashMap<String, Object>();
		ArrayList<String> list = new ArrayList<String>();
		if(StringUtil.isNotBlank(mobile)){
			for (String str : mobiles) {//去掉相同的电话号码
				map.put(str, str);
			}
			Iterator<String> it = map.keySet().iterator();  
		       while(it.hasNext()) {  
		           String key = (String)it.next();
		           mobile = map.get(key).toString();
		           if(StringUtil.isNotBlank(mobile.trim())){
		                list.add(mobile);
		           }
		    }  	
		}
		String msg = smsServiceClient.sendMarketingMessage(list, req.getContent().trim());
		res.setMsg(msg);
	}
	
	/**
	 * @author bianyatian
	 * 微网短信-Welink
	 */
	public void sendWelinkMarkingMessage(ReqMsg_BsUser_SendWelinkMarkingMessage req, ResMsg_BsUser_SendWelinkMarkingMessage res){
		try {
			//短信内容拼接
			if(StringUtil.isBlank(req.getContent()) || StringUtil.isBlank(req.getMobile())){res.setMsg("短信内容或手机号为空，发送失败");}
			String message = "【币港湾】"+req.getContent();
			String endStr = message.substring(message.length()-1,message.length());//获取发送信息最后一个字
			if(endStr.equals(",") || endStr.equals(".") || endStr.equals(";") || endStr.equals("!")
					|| endStr.equals("?") || endStr.equals("。") || endStr.equals("？") 
					|| endStr.equals("！") || endStr.equals("；")){
				message = message.substring(0,message.length()-1)+"，退订回T。";
			}else if(endStr.equals("，")){
				message = message+"退订回T。";
			}else{
				message = message+"，退订回T。";
			}
			//发送短信拼接
			BsSysConfig bsSysConfig = bsSysConfigService.findKey(Constants.EMERGENCY_MOBILE);
			String mobile = req.getMobile() + "," + bsSysConfig.getConfValue();
			mobile = mobile.replaceAll("，", ",");
			String[] mobiles = mobile.split(",");
			HashMap<String, Object> map = new HashMap<String, Object>();
			ArrayList<String> list = new ArrayList<String>();
			if(StringUtil.isNotBlank(mobile)){
				for (String str : mobiles) {//去掉相同的电话号码
					map.put(str, str);
				}
				Iterator<String> it = map.keySet().iterator();  
			       while(it.hasNext()) {  
			           String key = (String)it.next();
			           mobile = map.get(key).toString();
			           if(StringUtil.isNotBlank(mobile.trim())){
			                list.add(mobile);
			                BsSmsRecordJnl smsRecordJnl = new BsSmsRecordJnl();
							smsRecordJnl.setContent(message);
							smsRecordJnl.setMobile(mobile);
							smsRecordJnl.setSendTime(new Date());
							smsRecordJnl.setType(Constants.SMS_TYPE_WELINK_MARKET);
							smsRecordJnl.setPartnerCode(SmsSignEnum.BGW.getPartnerCode());
							bsSmsRecordJnlService.insertJnl(smsRecordJnl);
			           }
			    }  	
			}
			mobile = StringUtils.join(list.toArray(),",");
			
			WelinkSMSUtils.generalSend(mobile, message);
			res.setMsg("发送成功");
		} catch (Exception e) {
			e.printStackTrace();
			res.setMsg("发送失败");
		}
	}
	
	/**
	 * 注册用户查询
	 * @param req
	 * @param res
	 */
	public void bsUserRegisterListQuery(ReqMsg_BsUser_BsUserRegisterListQuery req, ResMsg_BsUser_BsUserRegisterListQuery res) {
		int totalRows = bsUserService.findRegisterUserCount(req.getUserName(), req.getMobile());
		if (totalRows > 0) {
			ArrayList<BsUserAssetVO> registerUserList = (ArrayList<BsUserAssetVO>) bsUserService.findRegisterUserList(
					req.getUserName(), req.getMobile(), 
					req.getOrderField(), req.getOrderDirection(),  
					req.getPageNum(), req.getNumPerPage());
			ArrayList<HashMap<String, Object>> userList = BeanUtils.classToArrayList(registerUserList);
			res.setValueList(userList);
		}
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}
	
	/**
	 * 用户复投查询
	 * @param req
	 * @param res
	 */
	public void bsUserComplexVoteListQuery(ReqMsg_BsUser_BsUserComplexVoteListQuery req, ResMsg_BsUser_BsUserComplexVoteListQuery res) {
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		BsUserAssetVO userVO = new BsUserAssetVO();
		userVO.setNumPerPage(numPerPage);
		userVO.setPageNum(pageNum);
		if (req.getUserId() != null && !"".equals(req.getUserId())) {
			userVO.setUserId(req.getUserId());
		}
		if (req.getsInterestBeginDate() != null && !"".equals(req.getsInterestBeginDate())) {
			userVO.setsInterestBeginDate(req.getsInterestBeginDate());
		}
		if (req.geteInterestBeginDate() != null && !"".equals(req.geteInterestBeginDate())) {
			userVO.seteInterestBeginDate(req.geteInterestBeginDate());
		}
		if (req.getsLastFinishInterestDate() != null && !"".equals(req.getsLastFinishInterestDate())) {
			userVO.setsLastFinishInterestDate(req.getsLastFinishInterestDate());
		}
		if (req.geteLastFinishInterestDate() != null && !"".equals(req.geteLastFinishInterestDate())) {
			userVO.seteLastFinishInterestDate(req.geteLastFinishInterestDate()); 
		}
		if (req.getOrderDirection() != null && (!"".equals(req.getOrderDirection())) && req.getOrderField() != null && (!"".equals(req.getOrderField()))) {
			userVO.setOrderDirection(req.getOrderDirection());
			userVO.setOrderField(req.getOrderField());
		}
		int totalRows = bsUserService.findUserComplexVoteCount(userVO);
		if (totalRows > 0) {
			List<HashMap<String, Object>> userVoteList = new ArrayList<HashMap<String,Object>> ();
			List<BsUserAssetVO> bsUserVoteList =  bsUserService.findUserComplexVoteList(userVO);
			for (int i=0;i<bsUserVoteList.size();i++) {
				BsUserAssetVO bsUserAssetVO = bsUserVoteList.get(i);
				HashMap<String,Object> userAsset = new HashMap<String,Object>();
				userAsset.put("id", bsUserAssetVO.getId());
				// 回款金额 = 投资本金 + 投资利息(年化收益总额)amount_of_payment
				userAsset.put("amountOfPayment", (bsUserAssetVO.getAmountOfPayment() == null ? 0 : bsUserAssetVO.getAmountOfPayment()));
				userAsset.put("totalPrincipal", bsUserAssetVO.getTotalPrincipal() == null ? 0 : bsUserAssetVO.getTotalPrincipal());
				// 投资次数
				userAsset.put("investmentTimes", bsUserAssetVO.getInvestmentTimes() == null ? 0 : bsUserAssetVO.getInvestmentTimes());
				userVoteList.add(userAsset);
			}
			res.setValueList(userVoteList);
		}
		res.setTotalRows(totalRows > 0 ? totalRows : 0);
		res.setNumPerPage(numPerPage);
		res.setPageNum(pageNum);
	}

	/**
	 * 用户利息回款查询
	 * Created by shh on 2016/10/11 21:57.
	 */
	public void bsUserInterestRepayment(ReqMsg_BsUser_BsUserInterestRepayment req, ResMsg_BsUser_BsUserInterestRepayment res) {
		int pageNum = req.getPageNum();
		int numPerPage = req.getNumPerPage();
		InterestRepaymentVO interestRepaymentVO = new InterestRepaymentVO();
		interestRepaymentVO.setPageNum(pageNum);
		interestRepaymentVO.setNumPerPage(numPerPage);
		if(req.getOpenTimeStart() != null && !"".equals(req.getOpenTimeStart())) {
			interestRepaymentVO.setOpenTimeStart(req.getOpenTimeStart());
		}
		if(req.getOpenTimeEnd() != null && !"".equals(req.getOpenTimeEnd())) {
			interestRepaymentVO.setOpenTimeEnd(DateUtil.addDays(req.getOpenTimeEnd(), 1));
		}
		if (req.getOrderDirection() != null && (!"".equals(req.getOrderDirection())) && req.getOrderField() != null && (!"".equals(req.getOrderField()))) {
			interestRepaymentVO.setOrderDirection(req.getOrderDirection());
			interestRepaymentVO.setOrderField(req.getOrderField());
		}
		int totalRows = bsUserService.queryUserInterestRepaymentCount(interestRepaymentVO);
		if(totalRows > 0) {
			List<InterestRepaymentVO> interestRepaymentList = bsUserService.queryUserInterestRepayment(interestRepaymentVO);
			ArrayList<HashMap<String, Object>> userList = BeanUtils.classToArrayList(interestRepaymentList);
			res.setValueList(userList);
		}
		res.setTotalRows(totalRows);
		res.setPageNum(pageNum);
		res.setNumPerPage(numPerPage);
	}
	
	/**
	 * 财务确认处理查看详情中查询用户基本信息
	 * 1、如果绑卡表中有绑卡成功status-1的记录，取这一条对应的银行卡信息
	 * 2、否则取绑卡表中离系统时间最近的一条记录对应的银行卡信息
	 * Created by shh on 2016/11/3 21:57.
	 */
	public void bsUserFinancialConfirmation(ReqMsg_BsUser_BsUserFinancialConfirmation req, ResMsg_BsUser_BsUserFinancialConfirmation res) {
		BsUserAssetVO bsUserAssetVO = bsUserService.queryUserById(req.getUserId());
		if(bsUserAssetVO == null) {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else {
			res.setUserName(bsUserAssetVO.getUserName());
			res.setIdCard(bsUserAssetVO.getIdCard());
			res.setMobile(bsUserAssetVO.getMobile());
			res.setReservedMobile(bsUserAssetVO.getReservedMobile());
			res.setCardNo(bsUserAssetVO.getCardNo());
			res.setAge(bsUserAssetVO.getAge());
			res.setGender(bsUserAssetVO.getGender());
		}
	}
	
}
 