package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.finance.enums.UnBindCheckResultEnum;
import com.pinting.business.dao.*;
import com.pinting.business.enums.PoliceVerifyBusinessTypeEnum;
import com.pinting.business.enums.PoliceVerifyCheckStatusEnum;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUser_BsUserTagListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.model.*;
import com.pinting.business.model.BsUserExample.Criteria;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.MUserOpRecordService;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.service.site.UserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class BsUserServiceImpl implements BsUserService{
	private Logger log = LoggerFactory.getLogger(BsUserServiceImpl.class);
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsFeedbackMapper bsFeedbackMapper;
	@Autowired
	private BsBankCardMapper bsBankCardMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
    private BsUserPoliceVerifyMapper bsUserPoliceVerifyMapper;
	@Autowired
	private BsBankCardService bsBankCardService;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
	private MUserOpRecordService mUserOpRecordService;
	@Autowired
	private UserService userService;
    @Autowired
    private UcUserMapper ucUserMapper;
    @Autowired
    private UcUserExtMapper ucUserExtMapper;
    
	@Override
	public BsUser findUserByUserId(Integer userId) {
		return bsUserMapper.selectByPrimaryKey(userId);
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	public Boolean updateBsUsersStatus(List<Integer> idList, Integer status) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIdIn(idList);
		BsUser bsUser = new BsUser();
		bsUser.setStatus(status);
		return bsUserMapper.updateByExampleSelective(bsUser, example)>0;
	}
	@Override
	public List<BsFeedbackVO> findBsFeedbacksByPage(String pageNum,
			String numPerPage, String orderField, String orderDirection) {
		BsFeedback bsFeedback = new BsFeedback();
		bsFeedback.setNumPerPage(Integer.valueOf(numPerPage));
		bsFeedback.setPageNum(Integer.valueOf(pageNum));
		bsFeedback.setOrderField(orderField);
		bsFeedback.setOrderDirection(orderDirection);
		List<BsFeedbackVO> feedbacks = bsFeedbackMapper.selectByPage(bsFeedback);
		
		return feedbacks.size()>0 ? feedbacks : null;
	}
	@Override
	public int countTotalBsFeedbacks() {
		BsFeedbackExample example = new BsFeedbackExample();
		return bsFeedbackMapper.countByExample(example);
	}
	@Override
	public int findSubUserCount( Integer recommendId) {

		BsUserExample example = new BsUserExample();
		Criteria criteria=example.createCriteria();
		criteria.andRecommendIdEqualTo(recommendId);
		return bsUserMapper.countByExample(example );
	}
	
	@Override
	public List<BsUserAssetVO> findSubUser(Integer recommendId, Integer pageNum, Integer numPerPage) {
		BsUserAssetVO bsUser = new BsUserAssetVO();
		bsUser.setNumPerPage(numPerPage);
		bsUser.setPageNum(pageNum);
		bsUser.setRecommendId(recommendId);
		List<BsUserAssetVO> bsUsers = bsUserMapper.selectAllUser(bsUser);
		return bsUsers.size()>0 ? bsUsers : null;
	}
	
	@Override
	public Double sumCanWithdraw() {
		Double canWithdraw = bsUserMapper.sumCanWithdraw();
		return canWithdraw == null?0:canWithdraw;
	}
	@Override
	public List<BsUserAssetVO> findAllUser(BsUserAssetVO bsUser) {
		List<BsUserAssetVO> bsUsers = bsUserMapper.selectAllUser(bsUser);
		return bsUsers.size()>0 ? bsUsers : null;
	}

	@Override
	public Integer findAllUserCount(BsUserAssetVO bsUser) {
		int count = bsUserMapper.countAllUserCount(bsUser);
		return count;
	}
	
	@Override
	public Integer findAgentUserCount(Integer status, String sMobile,
			String sEmail, Integer sIsBindBank, Integer sIsBindName,
			String sName, String sNick, String sRecommend, String eRecommend,
			String sReward, String eReward,Date sRegistTime,Date eRegistTime,
			Integer agentId, Integer regTerminal, String distributionChannel) {

		BsUserAssetVO bsUser = new BsUserAssetVO();
		
		if (sMobile != null&& (!"".equals(sMobile))) {
			bsUser.setMobile(sMobile);
		}
		if (sEmail != null&& (!"".equals(sEmail))) {
			bsUser.setsEmail(sEmail);
		}
		if (sIsBindBank != null&& (sIsBindBank!=0)) {
			bsUser.setsIsBindBank(sIsBindBank);
		}
		if (sIsBindName != null&& (sIsBindName!=0)) {
			bsUser.setsIsBindName(sIsBindName);
		}
		if (sName != null&& (!"".equals(sName))) {
			bsUser.setsName(sName);
		}
		if (sNick != null&& (!"".equals(sNick))) {
			bsUser.setsNick(sNick);
		}
		if (sRecommend != null&& (!"".equals(sRecommend))) {
			bsUser.setsRecommend(Integer.parseInt(sRecommend));
		}
		if (eRecommend != null&& (!"".equals(eRecommend))) {
			bsUser.seteRecommend(Integer.parseInt(eRecommend));
		}
		if (sReward != null&& (!"".equals(sReward))) {
			bsUser.setsReward(sReward);
		}
		if (eReward != null&& (!"".equals(eReward))) {
			bsUser.seteReward(eReward);
		}

		if (sRegistTime != null&& eRegistTime != null) {
			bsUser.setsRegisterTime(sRegistTime);
			bsUser.seteRegisterTime(DateUtil.addDays(eRegistTime, 1) );
		}
		if (distributionChannel != null&& (!"".equals(distributionChannel))) {
			bsUser.setDistributionChannel(distributionChannel);
		}
	
		bsUser.setStatus(status);
		bsUser.setAgentId(agentId);
		bsUser.setRegTerminal(regTerminal);
		int count = bsUserMapper.countAgentUserCount(bsUser);
		return count; 
	}

	@Override
	public List<BsUserAssetVO> findAgentUser(String searchMobile, Integer status,
			Integer pageNum, Integer numPerPage, String sEmail,
			Integer sIsBindBank, Integer sIsBindName, String sName,
			String sNick, String sRecommend, String eRecommend, String sReward,
			String eReward,Date sRegistTime,Date eRegistTime,String orderDirection,
			String orderField,Integer agentId, Integer regTerminal, String distributionChannel) {
		BsUserAssetVO bsUser = new BsUserAssetVO();
		bsUser.setNumPerPage(numPerPage);
		bsUser.setPageNum(pageNum);
		if (searchMobile != null&& (!"".equals(searchMobile))) {
			bsUser.setMobile(searchMobile);
		}
		if (sEmail != null&& (!"".equals(sEmail))) {
			bsUser.setsEmail(sEmail);
		}
		if (sIsBindBank != null&& (sIsBindBank!=0)) {
			bsUser.setsIsBindBank(sIsBindBank);
		}
		if (sIsBindName != null&& (sIsBindName!=0)) {
			bsUser.setsIsBindName(sIsBindName);
		}
		if (sName != null&& (!"".equals(sName))) {
			bsUser.setsName(sName);
		}
		if (sNick != null&& (!"".equals(sNick))) {
			bsUser.setsNick(sNick);
		}
		if (sRecommend != null&& (!"".equals(sRecommend))) {
			bsUser.setsRecommend(Integer.parseInt(sRecommend));
		}
		if (eRecommend != null&& (!"".equals(eRecommend))) {
			bsUser.seteRecommend(Integer.parseInt(eRecommend));
		}
		if (sReward != null&& (!"".equals(sReward))) {
			bsUser.setsReward(sReward);
		}
		if (eReward != null&& (!"".equals(eReward))) {
			bsUser.seteReward(eReward);
		}

		if (sRegistTime != null&& eRegistTime != null) {
			bsUser.setsRegisterTime(sRegistTime);
			bsUser.seteRegisterTime(DateUtil.addDays(eRegistTime, 1));
		}
		
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			bsUser.setOrderDirection(orderDirection);
			bsUser.setOrderField(orderField);
		}
		if (distributionChannel != null&& (!"".equals(distributionChannel))) {
			bsUser.setDistributionChannel(distributionChannel);
		}

		bsUser.setStatus(status);
		bsUser.setAgentId(agentId);
		bsUser.setRegTerminal(regTerminal);
		List<BsUserAssetVO> bsUsers = bsUserMapper.selectAgentUser(bsUser);
		return bsUsers.size()>0 ? bsUsers : null;
	}

	@Override
	public Map<String, Object> countResgisterUser(Map<String, Object> map) {
		return bsUserMapper.countResgisterUser(map);
	}
	
	@Override
	public int countResgisterUserByTime(Date time) {
		return bsUserMapper.countResgisterUserByTime(time);
	}
	
	@Override
	public int countBsUser(BsUser bsUser) {
		return bsUserMapper.countBsUser(bsUser);
	}
	@Override
	public List<BsUserVO> bsUserPage(BsUser bsUser) {
		return bsUserMapper.bsUserPage(bsUser);
	}
	
	@Override
	public List<BsBaseUserVO> findBaseUserInfo() {
		return bsUserMapper.selectBaseUserInfo();
	}
	
	@Override
	public List<MUserOperateVO> findUserOperatePage(MUserOperateVO recode) {
		List<MUserOperateVO> list = bsUserMapper.selectUserOperatePage(recode);
		return list.size() > 0 ? list : null;
	}
	
	@Override
	public int countUserOperate(MUserOperateVO recode) {
		return bsUserMapper.countUserOperate(recode);
	}
	
	@Override
	public int countBsUserByMap(Map<String, Object> map) {
		return bsUserMapper.countBsUserByMap(map);
	}
	
	@Override
	public List<BsUserAssetVO> findRegisterUserList(String userName, String mobile, String orderField, String orderDirection, 
			Integer pageNum, Integer numPerPage) {
		BsUserAssetVO userVO = new BsUserAssetVO();
		if (userName != null && !"".equals(userName)) {
			userVO.setUserName(userName);
		}
		if (mobile != null && !"".equals(mobile)) {
			userVO.setMobile(mobile);
		}
		if (orderDirection != null&& (!"".equals(orderDirection))&&orderField != null&& (!"".equals(orderField))) {
			userVO.setOrderDirection(orderDirection);
			userVO.setOrderField(orderField);
		}
		userVO.setNumPerPage(numPerPage);
		userVO.setPageNum(pageNum);
		return bsUserMapper.selectRegisterUser(userVO);
	}
	
	@Override
	public int findRegisterUserCount(String userName, String mobile) {
		BsUserAssetVO userVO = new BsUserAssetVO();
		if (userName != null && !"".equals(userName)) {
			userVO.setUserName(userName);
		}
		if (mobile != null && !"".equals(mobile)) {
			userVO.setMobile(mobile);
		}
		return bsUserMapper.selectRegisterUserCount(userVO);
	}
	
	@Override
	public Integer findUserComplexCount(BsUserAssetVO record) {
		return bsUserMapper.countUserComplexCount(record);
	}
	
	@Override
	public List<BsUserAssetVO> findUserComplexList(BsUserAssetVO record) {
		List<BsUserAssetVO> list = bsUserMapper.selectUserComplex(record);
		return list.size() >0 ? list : null;
	}
	
	@Override
	public Integer findUserRecordCount(BsRecordListVo record) {
		return bsUserMapper.countUserRecordCount(record);
	}
	
	@Override
	public List<BsRecordListVo> findUserRecordList(BsRecordListVo record) {
		List<BsRecordListVo> list = bsUserMapper.selectUserRecord(record);
		return list.size() >0 ? list : null;
	}
	
	@Override
	public void addUserRecordList(BsRecordListVo record) {
		bsUserMapper.addUserRecord(record);
	}
	
	@Override
	public Integer findAllUserCountTag(BsUserAssetVO bsUser) {
		int count = bsUserMapper.countAllUserTagCount(bsUser);
		return count;
	}
	
	@Override
	public List<BsUserAssetVO> findAllUserTag(BsUserAssetVO bsUser) {
		List<BsUserAssetVO> bsUsers = bsUserMapper.selectAllUserTag(bsUser);
		return bsUsers.size()>0 ? bsUsers : null;
	}
	
	@Override
	public Integer findUserIdTag(BsUserAssetVO bsUser) {
		int count = bsUserMapper.countUserIdTag(bsUser);
		return count;
	}
	
	@Override
	public List<BsUserAssetVO> findUserIdTagList(BsUserAssetVO bsUser) {
		List<BsUserAssetVO> bsUsers = bsUserMapper.selectUserIdTag(bsUser);
		return bsUsers.size()>0 ? bsUsers : null;
	}
	@Override
	public List<BsUser> findUserByIds(String[] ids) {
		BsUserExample example = new BsUserExample();
		List<Integer> idList = new ArrayList<Integer>();
		for(String id : ids) {
			id = id.trim();
			Pattern pattern = Pattern.compile("^[0-9]*$");
			Matcher matcher = pattern.matcher(id);
			if(matcher.matches()) {
				idList.add(Integer.parseInt(id));
			}
		}
		example.createCriteria().andIdIn(idList);
		List<BsUser> result = bsUserMapper.selectByExample(example);
		return result;
	}
	
	@Override
	public List<BsUser> selectAllUserForUserId(ReqMsg_BsUser_BsUserTagListQuery req) {
		BsUserAssetVO bsUser = new BsUserAssetVO();
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
		}
		if(CollectionUtils.isEmpty(bsUser.getTagIdList())) {
			bsUser.setTagIdList(null);
		}
		if(req.getNumPerPage() == null) {
		    bsUser.setNumPerPage(-1);
		}
		List<BsUser> result = bsUserMapper.selectAllUserForUserId(bsUser);
		return result;
	}
	
	@Override
	public List<BsUser> findUsersByIdsOrMobiles(Map<String, Object> map) {
		return bsUserMapper.selectUsersByIdsOrMobiles(map);
	}
	
	@Override
	public List<BsUserAssetVO> findUserComplexVoteList(BsUserAssetVO record) {
		List<BsUserAssetVO> list =  bsUserMapper.selectUserComplexVote(record);
		return list.size() > 0 ? list : null;
	}
	
	@Override
	public Integer findUserComplexVoteCount(BsUserAssetVO record) {
		int count = bsUserMapper.selectUserComplexVoteCount(record);
		return count;
	}

    @Override
    public List<BsUser> selectUserList(BsUser req) {
        List<BsUser> bsUsers = bsUserMapper.selectBaseUserGrid(req.getUserName(), req.getMobile(), req.getIdCard(), req.getStart(), req.getNumPerPage());
        return bsUsers;
    }

	@Override
	public List<InterestRepaymentVO> queryUserInterestRepayment(InterestRepaymentVO record) {
		List<InterestRepaymentVO> interestRepaymentList = bsUserMapper.selectUserInterestRepayment(record);
		return interestRepaymentList;
	}

	@Override
	public Integer queryUserInterestRepaymentCount(InterestRepaymentVO record) {
		int count = bsUserMapper.selectUserInterestRepaymentCount(record);
		return count;
	}
	
	@Override
	public Boolean isExistUser(Integer userId) {
		return bsUserMapper.selectByPrimaryKey(userId)==null?false : true;
	}

	@Override
	public BsUserAssetVO queryUserById(Integer userId) {
		BsUserAssetVO userAssetVO = new BsUserAssetVO();
		List<BsBankCardVO> bankCardList = bsBankCardMapper.selectByUserId(userId);
		if(bankCardList != null && bankCardList.size() != 0) {
			for(BsBankCardVO vo : bankCardList) {
				if(vo.getStatus() == 1) {
					userAssetVO = bsUserMapper.selectUserById(userId);
					break;
				}
			}
		}
		if(StringUtils.isBlank(userAssetVO.getCardNo())) {
			userAssetVO = bsUserMapper.selectRecentUserById(userId);
		}
		return userAssetVO;
	}


	@Override
	public List<Map<String, Object>> selectAmountTransGroup() {
		return bsUserMapper.selectAmountTransGroup();
	}

	@Override
	public List<BsUser> queryZanCompensateInfo() {
		List<BsUser> VIPUserList = new ArrayList<BsUser>();;
		List<Integer> VIPUserIdList = null;
		BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.SUPER_FINANCE_USER_ID);//VIP理财人用户编号
		if (configUser != null) {
			VIPUserIdList = new ArrayList<Integer>();
			String[] userStr = configUser.getConfValue().split(",");
			for (String string : userStr) {
				VIPUserIdList.add(Integer.parseInt(string));
			}
		}
		if(VIPUserIdList.size() > 0) {
			for(Integer userId : VIPUserIdList) {
				BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
				if (bsUser != null ) {
					VIPUserList.add(bsUser);
				}
			}
		}
		return VIPUserList;
	}

	@Override
	public List<BsUser> queryZanCompensateUserInfo() {
		List<BsUser> zanCompensateList = new ArrayList<BsUser>();;
		List<Integer> VIPUserIdList = null;
		BsSysConfig configUser = sysConfigService.findConfigByKey(Constants.ZAN_COMPENSATE_USER_ID);//赞分期代偿人用户编号
		if (configUser != null) {
			VIPUserIdList = new ArrayList<Integer>();
			String[] userStr = configUser.getConfValue().split(",");
			for (String string : userStr) {
				VIPUserIdList.add(Integer.parseInt(string));
			}
		}
		if(VIPUserIdList.size() > 0) {
			for(Integer userId : VIPUserIdList) {
				BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
				zanCompensateList.add(bsUser);
			}
		}
		return zanCompensateList;
	}
	@Override
	public ResMsg_MUserOperate_UserOperateQuery findUserOperatePageManualPacket(
			ReqMsg_MUserOperate_UserOperateQuery reqMsg) {
		
		ResMsg_MUserOperate_UserOperateQuery resMsg = new ResMsg_MUserOperate_UserOperateQuery();
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		
		MUserOperateVO recode = new MUserOperateVO();
		recode.setPageNum(pageNum);
		recode.setNumPerPage(numPerPage);
		if(reqMsg.getBankId() != null && reqMsg.getBankId() != 0) {
			recode.setBankId(reqMsg.getBankId());
		}
		if(reqMsg.getsLastBuyTime() != null) {
			recode.setsLastBuyTime(reqMsg.getsLastBuyTime());
		}
		if(reqMsg.geteLastBuyTime() != null) {
			recode.seteLastBuyTime(reqMsg.geteLastBuyTime());
		}
		if(reqMsg.getsFirstBuyTime() != null) {
			recode.setsFirstBuyTime(reqMsg.getsFirstBuyTime());
		}
		if(reqMsg.geteFirstBuyTime() != null) {
			recode.seteFirstBuyTime(reqMsg.geteFirstBuyTime());
		}
		if(reqMsg.getsBuyTimes() != null ) {
			recode.setsBuyTimes(String.valueOf(reqMsg.getsBuyTimes()));
		}
		if(reqMsg.geteBuyTimes() != null ) {
			recode.seteBuyTimes(String.valueOf(reqMsg.geteBuyTimes()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsInvestMoney())) {
			recode.setsInvestMoney(String.valueOf(reqMsg.getsInvestMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteInvestMoney())) {
			recode.seteInvestMoney(String.valueOf(reqMsg.geteInvestMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsInvestTotalMoney())) {
			recode.setsInvestTotalMoney(String.valueOf(reqMsg.getsInvestTotalMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteInvestTotalMoney())) {
			recode.seteInvestTotalMoney(String.valueOf(reqMsg.geteInvestTotalMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsTotalBonus())) {
			recode.setsTotalBonus(String.valueOf(reqMsg.getsTotalBonus()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteTotalBonus())) {
			recode.seteTotalBonus(String.valueOf(reqMsg.geteTotalBonus()));
		}
		if(reqMsg.getsRegistTime() != null) {
			recode.setsRegistTime(reqMsg.getsRegistTime());
		}
		if(reqMsg.geteRegistTime() != null) {
			recode.seteRegistTime(reqMsg.geteRegistTime());
		}
		if(StringUtil.isNotEmpty(reqMsg.getAgentIds())) {
			String[] agentIds = reqMsg.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				recode.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(reqMsg.getNonAgentId())) {
			recode.setNonAgentId(reqMsg.getNonAgentId());
		}
		int receiveNum = 0;
		if(StringUtil.isNotEmpty(reqMsg.getUserIds())) {
			String trimStr = StringUtil.trimStr(reqMsg.getUserIds());
			String[] ids = trimStr.replace("，", ",").split(",");
			if(ids.length > 0) {
				List<Object> userIds = new ArrayList<Object>();
				for (String str : ids) {
					if(StringUtil.isNotEmpty(str.trim())) {
						receiveNum++;
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
				recode.setUserIds(userIds);
			}
		}
		resMsg.setReceiveNum(receiveNum);
		
		int totalRows = bsUserMapper.countUserOperateManualPacket(recode);
		if(totalRows > 0) {
			List<MUserOperateVO> list = bsUserMapper.selectUserOperateManualPacketPage(recode);
			resMsg.setUserOperateList(BeanUtils.classToArrayList(list));
		}
		resMsg.setTotalRows(totalRows);
		return resMsg;
	}
	
	@Override
	public ResMsg_MUserOperate_UserOperateQuery countUserOperatePageManualPacket(
			ReqMsg_MUserOperate_UserOperateQuery reqMsg) {
		
		ResMsg_MUserOperate_UserOperateQuery resMsg = new ResMsg_MUserOperate_UserOperateQuery();
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		
		MUserOperateVO recode = new MUserOperateVO();
		recode.setPageNum(pageNum);
		recode.setNumPerPage(numPerPage);
		if(reqMsg.getBankId() != null && reqMsg.getBankId() != 0) {
			recode.setBankId(reqMsg.getBankId());
		}
		if(reqMsg.getsLastBuyTime() != null) {
			recode.setsLastBuyTime(reqMsg.getsLastBuyTime());
		}
		if(reqMsg.geteLastBuyTime() != null) {
			recode.seteLastBuyTime(reqMsg.geteLastBuyTime());
		}
		if(reqMsg.getsFirstBuyTime() != null) {
			recode.setsFirstBuyTime(reqMsg.getsFirstBuyTime());
		}
		if(reqMsg.geteFirstBuyTime() != null) {
			recode.seteFirstBuyTime(reqMsg.geteFirstBuyTime());
		}
		if(reqMsg.getsBuyTimes() != null ) {
			recode.setsBuyTimes(String.valueOf(reqMsg.getsBuyTimes()));
		}
		if(reqMsg.geteBuyTimes() != null ) {
			recode.seteBuyTimes(String.valueOf(reqMsg.geteBuyTimes()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsInvestMoney())) {
			recode.setsInvestMoney(String.valueOf(reqMsg.getsInvestMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteInvestMoney())) {
			recode.seteInvestMoney(String.valueOf(reqMsg.geteInvestMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsInvestTotalMoney())) {
			recode.setsInvestTotalMoney(String.valueOf(reqMsg.getsInvestTotalMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteInvestTotalMoney())) {
			recode.seteInvestTotalMoney(String.valueOf(reqMsg.geteInvestTotalMoney()));
		}
		if(StringUtil.isNotEmpty(reqMsg.getsTotalBonus())) {
			recode.setsTotalBonus(String.valueOf(reqMsg.getsTotalBonus()));
		}
		if(StringUtil.isNotEmpty(reqMsg.geteTotalBonus())) {
			recode.seteTotalBonus(String.valueOf(reqMsg.geteTotalBonus()));
		}
		if(reqMsg.getsRegistTime() != null) {
			recode.setsRegistTime(reqMsg.getsRegistTime());
		}
		if(reqMsg.geteRegistTime() != null) {
			recode.seteRegistTime(reqMsg.geteRegistTime());
		}
		if(StringUtil.isNotEmpty(reqMsg.getAgentIds())) {
			String[] agentIds = reqMsg.getAgentIds().split(",");
			if(agentIds.length > 0) {
				List<Object> ids = new ArrayList<Object>();
				for (String str : agentIds) {
					if(StringUtil.isNotEmpty(str)) {
						ids.add(Integer.valueOf(str));
					}
				}
				recode.setAgentIds(ids);
			}
		}
		if(StringUtil.isNotEmpty(reqMsg.getNonAgentId())) {
			recode.setNonAgentId(reqMsg.getNonAgentId());
		}
		int receiveNum = 0;
		if(StringUtil.isNotEmpty(reqMsg.getUserIds())) {
			String trimStr = StringUtil.trimStr(reqMsg.getUserIds());
			String[] ids = trimStr.replace("，", ",").split(",");
			if(ids.length > 0) {
				List<Object> userIds = new ArrayList<Object>();
				for (String str : ids) {
					if(StringUtil.isNotEmpty(str.trim())) {
						receiveNum++;
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
				recode.setUserIds(userIds);
			}
		}
		resMsg.setReceiveNum(receiveNum);
		
		int totalRows = bsUserMapper.countUserOperateManualPacket(recode);
		resMsg.setTotalRows(totalRows);
		return resMsg;
	}

	@Override
	public int findCountUserByIds(List<Integer> userIds) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIdIn(userIds).andStatusEqualTo(Constants.USER_STATUS_1);
		int result = bsUserMapper.countByExample(example);
		return result;
	}

	@Override
	public List<BsUser> findUserByIds(List<Integer> userIds, Integer pageNum, Integer numPerPage) {
		int start = (pageNum <= 1) ? 0 : ((pageNum - 1) * numPerPage); // mysql的分页
		List<BsUser> result = bsUserMapper.findUserByIds(userIds, start, numPerPage);
		return result;
	}

	@Override
	public int findCountBirthdayByDate(Date startTime, Date endTime) {
		return bsUserMapper.findCountBirthdayByDate(startTime, endTime);
	}
	
	@Override
	public Integer countUserChangeBindCardInfo(BsChangeBindCardVO record) {
		return bsUserMapper.countUserChangeBindCardInfo(record);
	}

	@Override
	public List<BsChangeBindCardVO> selectUserChangeBindCardInfo(BsChangeBindCardVO record) {
		return bsUserMapper.selectUserChangeBindCardInfo(record);
	}

	@Override
	public BsVerifyBindCardResVO verifyUserChangeBindCard(String muserId, Integer verifyId, String note, String checkStatus) {
		BsVerifyBindCardResVO res = new BsVerifyBindCardResVO();
		BsUserPoliceVerifyExample example = new BsUserPoliceVerifyExample();
		example.createCriteria().andIdEqualTo(verifyId).andBusinessTypeEqualTo(PoliceVerifyBusinessTypeEnum.UNBIND_BANK_CARD.getCode());
		List<BsUserPoliceVerify> list = bsUserPoliceVerifyMapper.selectByExample(example);
		if (!CollectionUtils.isEmpty(list)) {
			// 审核状态校验（已经审核通过的不能再审核）
			BsUserPoliceVerify userPoliceVerify = list.get(0);
			String dbCheckStatus = userPoliceVerify.getCheckStatus();
			if (PoliceVerifyCheckStatusEnum.PASS.getCode().equals(dbCheckStatus)) {
				res.setReturnCode("userPoliceVerify_pass");
				res.setReturnMsg("该记录已审核通过，请核对！");
			} else {
					BsUser bsUser = findUserByUserId(userPoliceVerify.getUserId());
					String checkDesc = "";
					// 审核通过
					if (PoliceVerifyCheckStatusEnum.PASS.getCode().equals(checkStatus)) {
						// 进行中的提现和充值校验
						UnBindCheckResultEnum transtypeCheck = bsBankCardService.userWithdrawTopUpCheck(userPoliceVerify.getUserId());
						if(transtypeCheck != null){
							res.setReturnCode(transtypeCheck.getCode());
							res.setReturnMsg("用户正在进行充值/提现操作，暂时无法进行审核！");
							return res;
						}
						// 调用解绑接口，接口返回失败
						UnBindBankCardRes unBindBankCardRes = bsBankCardService.unBindCard4ManagePoliceVerify(userPoliceVerify.getUserId(), 
								userPoliceVerify.getBusinessId());
						if (StringUtil.isNotEmpty(unBindBankCardRes.getErrorCode())) {
							res.setReturnCode(unBindBankCardRes.getErrorCode());
							res.setReturnMsg("调用解绑接口失败，" + unBindBankCardRes.getErrorMsg());
							log.info("调用解绑接口失败，{}", unBindBankCardRes.getErrorMsg());
						} else if (StringUtil.isEmpty(unBindBankCardRes.getErrorCode())) {
							// 调用解绑接口，接口返回成功 
							log.info("调用解绑接口成功");
							BsUserPoliceVerify userPoliceVerifyTemp = new BsUserPoliceVerify();
							userPoliceVerifyTemp.setId(userPoliceVerify.getId());
							userPoliceVerifyTemp.setCheckStatus(PoliceVerifyCheckStatusEnum.PASS.getCode());
							userPoliceVerifyTemp.setChecker(StringUtil.isEmpty(muserId)? 1 : Integer.parseInt(muserId));
							checkDesc = StringUtils.substringBefore(userPoliceVerify.getCheckDesc(), ",") + "," + note;
							userPoliceVerifyTemp.setCheckDesc(checkDesc);
							userPoliceVerifyTemp.setCheckTime(new Date());
							userPoliceVerifyTemp.setUpdateTime(new Date());
							bsUserPoliceVerifyMapper.updateByPrimaryKeySelective(userPoliceVerifyTemp);
							
							// 发送短信提醒用户
							try {
				                // 短信模板通知
				                smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.UN_BINDCARD_PASS);
				            } catch (Exception e) {
				                log.error("申请更换安全卡发送消息失败{}", e);
				            }
							
						}
						// 审核不通过	
					} else if (PoliceVerifyCheckStatusEnum.REFUSE.getCode().equals(checkStatus)) {
						log.info("申请更换安全卡审核不通过");
						BsUserPoliceVerify userPoliceVerifyTemp = new BsUserPoliceVerify();
						userPoliceVerifyTemp.setId(userPoliceVerify.getId());
						userPoliceVerifyTemp.setCheckStatus(PoliceVerifyCheckStatusEnum.REFUSE.getCode());
						userPoliceVerifyTemp.setChecker(StringUtil.isEmpty(muserId)? 1 : Integer.parseInt(muserId));
						checkDesc = StringUtils.substringBefore(userPoliceVerify.getCheckDesc(), ",") + "," + note;
						userPoliceVerifyTemp.setCheckDesc(checkDesc);
						userPoliceVerifyTemp.setCheckTime(new Date());
						userPoliceVerifyTemp.setUpdateTime(new Date());
						bsUserPoliceVerifyMapper.updateByPrimaryKeySelective(userPoliceVerifyTemp);
						
						// 发送短信提醒用户
						try {
			                // 短信模板通知
			                smsServiceClient.sendTemplateMessage(bsUser.getMobile(), TemplateKey.UN_BINDCARD_REFUSE);
			            } catch (Exception e) {
			            	log.error("申请更换安全卡发送消息失败{}", e);
			            }
						
					}
			}
		} else {
			res.setReturnCode("userPoliceVerify_empty");
			res.setReturnMsg("用户申请更换安全卡对应记录不存在，请核对！");
		}
		return res;
	}

	@Override
	public Integer findQhdAgentUserCount(String userName, String mobile, Date startRegisterTime,
			Date endRegisterTime, Integer regTerminal, String distributionChannel) {
		BsQhdUserAgentVO bsUser = new BsQhdUserAgentVO();
		if (StringUtil.isNotEmpty(mobile)) {
			bsUser.setMobile(mobile);
		}
		if (StringUtil.isNotEmpty(userName)) {
			bsUser.setUserName(userName);
		}
		bsUser.setStartRegisterTime(startRegisterTime);
		bsUser.setEndRegisterTime(endRegisterTime);
		bsUser.setRegTerminal(regTerminal);
		bsUser.setDistributionChannel(distributionChannel);
		int count = bsUserMapper.countQhdAgentUser(bsUser);
		return count; 
	}

	@Override
	public List<BsQhdUserAgentVO> findQhdAgentUserList(String userName, String mobile, Date startRegisterTime,
			Date endRegisterTime, Integer regTerminal, String distributionChannel, Integer pageNum, Integer numPerPage) {
		BsQhdUserAgentVO bsUser = new BsQhdUserAgentVO();
		if (StringUtil.isNotEmpty(mobile)) {
			bsUser.setMobile(mobile);
		}
		if (StringUtil.isNotEmpty(userName)) {
			bsUser.setUserName(userName);
		}
		bsUser.setStartRegisterTime(startRegisterTime);
		bsUser.setEndRegisterTime(endRegisterTime);
		bsUser.setRegTerminal(regTerminal);
		bsUser.setDistributionChannel(distributionChannel);
		bsUser.setPageNum(pageNum);
		bsUser.setNumPerPage(numPerPage);
		List<BsQhdUserAgentVO> bsUserList = bsUserMapper.selectQhdAgentUserList(bsUser);
		return org.apache.commons.collections.CollectionUtils.isEmpty(bsUserList) ? null : bsUserList;
	}

	@Override
	public Boolean updateUserStatus(MUser mUser, Integer userId, Integer status, String ip) {
		// 安全校验
		if (mUser == null || mUser.getStatus() != Constants.USER_STATUS_1) {
			return false;
		}
		
		// 存操作记录
		MUserOpRecord userOpRecord = new MUserOpRecord();
		userOpRecord.setOpUserId(mUser.getId());
		userOpRecord.setFunctionName("用户管理");
		userOpRecord.setFunctionUrl("/bsuser/updateUserStatus");
		userOpRecord.setOpContent(mUser.getName()+"：用户管理状态更新");
		userOpRecord.setIp(ip);
		userOpRecord.setOpTime(new Date());
		mUserOpRecordService.addMUserOpRecord(userOpRecord);
		
		BsUser bsUser = userService.findUserById(userId);
		if (bsUser == null) {
			return false;
		}
		bsUser.setStatus(status);
		bsUser.setId(bsUser.getId());
		bsUserMapper.updateByPrimaryKeySelective(bsUser);
		return true;
	}
	
}
