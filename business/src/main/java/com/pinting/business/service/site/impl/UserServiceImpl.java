package com.pinting.business.service.site.impl;

import com.alibaba.fastjson.JSON;
import com.pinting.business.accounting.service.AccountHandleService;
import com.pinting.business.dao.*;
import com.pinting.business.enums.BsUserStatus;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.*;
import com.pinting.business.model.dto.AddressDTO;
import com.pinting.business.model.dto.ApplictionsDTO;
import com.pinting.business.model.vo.BsProductVO;
import com.pinting.business.model.vo.BsUserActivateVO;
import com.pinting.business.model.vo.BsUserAssetVO;
import com.pinting.business.service.site.*;
import com.pinting.business.util.Constants;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Customer_QueryUserByPhone;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.DafyTransportService;
import com.pinting.gateway.out.service.SMSServiceClient;
import com.pinting.gateway.smsEnums.TemplateKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class UserServiceImpl implements UserService{

	private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
	@Autowired
	private BsUserMapper userMapper;
	@Autowired
	private AccountService accountService;
	@Autowired
	private SubAccountService subAccountService;
	@Autowired
	private BsAccountJnlMapper bsAccountJnlMapper;
	@Autowired
	private BsFeedbackMapper bsFeedbackMapper;
	@Autowired
	private AccountHandleService accountHandleService;
	@Autowired
	private DafyUserExtMapper dafyUserExtMapper;
	@Autowired
	private BsAgentMapper bsAgentMapper;
	@Autowired
	private BsSubAccountMapper subAccountMapper;
	@Autowired
	private BsSalesMapper bsSalesMapper;
	@Autowired
	private BsUserSalesMapper bsUserSalesMapper;
	@Autowired
	private BsAppMessageMapper bsAppMessageMapper;
	@Autowired
	private BsUserCustomerManagerMapper bsUserCustomerManagerMapper;
	@Autowired
	private TransactionTemplate transactionTemplate;
	@Autowired
	private DafyTransportService dafyService;
	@Autowired
	private MUserMapper muserMapper;
	@Autowired
	private SMSServiceClient smsServiceClient;
	@Autowired
    private Ecup2016ActivityService ecup2016ActivityService;
	@Autowired
    private BsPayOrdersMapper payOrderMapper;
	@Autowired
	private BsAgentViewConfigMapper bsAgentViewConfigMapper;
	@Autowired
	private BsBankCardMapper bsBankCardMapper;
	@Autowired
	private BsHfbankUserExtMapper bsHfbankUserExtMapper;
	@Autowired
	private BsBankMapper bankMapper;
    @Autowired
    private SMSService        smsService;
    @Autowired
	private UcUserMapper ucUserMapper;
    @Autowired
	private UcUserExtMapper ucUserExtMapper;
	@Autowired
	private HFBankDepSiteService hfBankDepSiteService;
	@Autowired
	private BsUserSessionConnectionInfoMapper bsUserSessionConnectionInfoMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;

	@Override
	@MethodRole("APP")
	public boolean isValidMobileOrNick(String mobileOrNick) {
		if (isValidNick(mobileOrNick) || isValidMobile(mobileOrNick)) {
			return true;
		}
		return false;
	}
	@Override
	@MethodRole("APP")
	public boolean isValidMobile(String mobile) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(1);
		return userMapper.countByExample(example)==1?true:false;
	}
	@Override
	@MethodRole("APP")
	public boolean isValidNick(String nick) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andNickEqualTo(nick).andStatusEqualTo(1);
		return userMapper.countByExample(example)==1?true:false;
	}
	@Override
	@MethodRole("APP")
	public BsUser findUserByNick(String nick) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andNickEqualTo(nick);
		List<BsUser> bsUserList = userMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0);
		}
		return null;
	}
	@Override
	@MethodRole("APP")
	public BsUser findUserByMobile(String mobile) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andMobileEqualTo(mobile);
		List<BsUser> bsUserList = userMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0);
		}
		return null;
	}
	@Override
	@MethodRole("APP")
	public BsUser findUserByEmail(String email) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andEmailEqualTo(email);
		List<BsUser> bsUserList = userMapper.selectByExample(example);
		if(bsUserList != null && bsUserList.size() > 0)
		{
			return bsUserList.get(0);
		}
		return null;
	}
	@Transactional(rollbackFor=Exception.class)
	@Override
	@MethodRole("APP")
	public boolean registerUser(final BsUser reqUser,final String inviteCode,final Integer dafyUserId) {
		
		transactionTemplate.execute(new TransactionCallbackWithoutResult(){
			@Override
			protected void doInTransactionWithoutResult(TransactionStatus status) {
				//start
				//bs_user中是否存在记录
				BsUserExample bsUserExample = new BsUserExample();
				bsUserExample.createCriteria().andMobileEqualTo(reqUser.getMobile())
						.andStatusEqualTo(Constants.BS_USER_STATUS_NORMAL);
				List<BsUser> bsUsers = userMapper.selectByExample(bsUserExample);
				if(!CollectionUtils.isEmpty(bsUsers)){
					throw new PTMessageException(PTMessageEnum.USER_EXIST);
				}

			    // 统一回款到余额
			    reqUser.setReturnPath(Constants.RETURN_PATH_BALANCE);
				userMapper.insertSelective(reqUser);

				//如果不存在则新增用户中心 uc_user记录
				UcUserExample ucUserExist = new UcUserExample();
				ucUserExist.createCriteria().andMobileEqualTo(reqUser.getMobile())
						.andStatusEqualTo(Constants.UC_USER_OPEN);
				List<UcUser> ucUsers = ucUserMapper.selectByExample(ucUserExist);
				if(CollectionUtils.isEmpty(ucUsers)){
					//新增用户中心 uc_user记录
					UcUser ucUser = new UcUser();
					ucUser.setCreateTime(new Date());
					ucUser.setUpdateTime(new Date());
					ucUser.setMobile(reqUser.getMobile());
					ucUser.setStatus(Constants.UC_USER_OPEN);
					ucUserMapper.insertSelective(ucUser);
					//新增用户中心uc_user_ext BGW记录
					UcUserExt ucUserExt = new UcUserExt();
					ucUserExt.setUcUserId(ucUser.getId());
					ucUserExt.setCreateTime(new Date());
					ucUserExt.setUserType(Constants.UC_USER_TYPE_BGW);
					ucUserExt.setUserId(reqUser.getId());
					ucUserExtMapper.insertSelective(ucUserExt);
				}else{
					UcUser ucUser = ucUsers.get(0);
					UcUserExtExample example = new UcUserExtExample();
					example.createCriteria().andUcUserIdEqualTo(ucUser.getId()).andUserTypeEqualTo(Constants.UC_USER_TYPE_BGW);
					List<UcUserExt> ucUserExts = ucUserExtMapper.selectByExample(example);
					if(!CollectionUtils.isEmpty(ucUserExts)){
						throw new PTMessageException(PTMessageEnum.USER_EXIST);
					}
					//新增用户中心
					UcUserExt ucUserExt = new UcUserExt();
					ucUserExt.setUcUserId(ucUser.getId());
					ucUserExt.setCreateTime(new Date());
					ucUserExt.setUserType(Constants.UC_USER_TYPE_BGW);
					ucUserExt.setUserId(reqUser.getId());
					ucUserExtMapper.insertSelective(ucUserExt);
				}

				//查询刚注册用户
				BsUser bsUser = new BsUser();
				bsUser.setMobile(reqUser.getMobile());
				BsUser user = userMapper.mobileSelect(bsUser);
				if(null != inviteCode && inviteCode.length() !=11){//是销售邀请码
					BsSales sales = new BsSales();
		    		sales.setInviteCode(inviteCode);
		    		sales = bsSalesMapper.selectBsSales(sales);//查询是那个销售推荐的
		    		if(null != sales){//销售人员存在
		    			BsUserSales userSales = new BsUserSales();//存入销售推荐表
		        		userSales.setSalesId(sales.getId());
		        		userSales.setGrade(1);//直接推荐
		        		userSales.setUserId(user.getId());
		        		userSales.setCreateTime(new Date());
		        		bsUserSalesMapper.insert(userSales);
		    		}else {
		    			 throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
					}
				}else if (null != inviteCode && inviteCode.length() == 11) {
					//销售经理人邀请逻辑
					/*BsCustomerManagerExample bsCustomerManagerExample = new BsCustomerManagerExample();
					bsCustomerManagerExample.createCriteria().andMobileEqualTo(inviteCode);
		    		List<BsCustomerManager> bsCustomerManagers = bsCustomerManagerMapper.selectByExample(bsCustomerManagerExample);//查询是那个推荐的销售经理人
		    		
*/		    		if(dafyUserId != null){//销售经理人存在
		    			BsUserCustomerManager bsUserCustomerManager = new BsUserCustomerManager();//存入客户经理用户关系表
		    			bsUserCustomerManager.setCustomerManagerDafyId(dafyUserId);
		    			bsUserCustomerManager.setUserId(user.getId());
		    			bsUserCustomerManager.setGrade(1);//直接推荐
		    			bsUserCustomerManager.setCreateTime(new Date());
		    			bsUserCustomerManager.setUpdateTime(new Date());
		        		bsUserCustomerManagerMapper.insertSelective(bsUserCustomerManager);
		    		}else {
		    			 throw new PTMessageException(PTMessageEnum.INVITATIONCODE_NO_EXIT);
					}
				
				}else{
					if(null != user){
						if(null != user.getRecommendId() && user.getRecommendId() > 0){//普通邀请码
							int gradeNum = 1;
							
							BsUser u = new BsUser();
							u.setRecommendId(user.getRecommendId());
							while (u.getRecommendId() != null && u.getRecommendId() != -1) {//看最初邀请人是否是销售人员
								u = userMapper.selectByPrimaryKey(u.getRecommendId());
								gradeNum ++;
								if(u == null){
									break;
								}
							}
							if(null != u){
								BsUserSales record = new BsUserSales();
								record.setUserId(u.getId());
								record.setGrade(1);
								BsUserSales bsUserSales = bsUserSalesMapper.selectSales(record);
								
								
								BsUserCustomerManagerExample bsUserCustomerManagerExample = new BsUserCustomerManagerExample();
								bsUserCustomerManagerExample.createCriteria().andUserIdEqualTo(u.getId()).andGradeEqualTo(1);
								List<BsUserCustomerManager>  bsUserCustomerManagers =bsUserCustomerManagerMapper.selectByExample(bsUserCustomerManagerExample);
								

								if(null != bsUserSales){//是销售人员间接推荐的
									BsSales sales = bsSalesMapper.selectByPrimaryKey(bsUserSales.getSalesId());
									if(sales !=  null){//判断销售人员是否离职
										if(sales.getStatus() == 1){//在职
											BsUserSales userSales = new BsUserSales();//存入销售推荐表
							        		userSales.setSalesId(sales.getId());
							        		userSales.setGrade(gradeNum);//间接推荐
							        		userSales.setUserId(user.getId());
							        		userSales.setCreateTime(new Date());
							        		bsUserSalesMapper.insert(userSales);
										}
									}
								}else if (!CollectionUtils.isEmpty(bsUserCustomerManagers)) {
					    			BsUserCustomerManager bsUserCustomerManager = new BsUserCustomerManager();//存入客户经理用户关系表
					    			bsUserCustomerManager.setCustomerManagerDafyId(bsUserCustomerManagers.get(0).getCustomerManagerDafyId());
					    			bsUserCustomerManager.setUserId(user.getId());
					    			bsUserCustomerManager.setGrade(gradeNum);//间接推荐
					    			bsUserCustomerManager.setCreateTime(new Date());
					    			bsUserCustomerManager.setUpdateTime(new Date());
					        		bsUserCustomerManagerMapper.insertSelective(bsUserCustomerManager);
								}
							}
						}
					}
				}
				
				Date dt=new Date();//如果不需要格式,可直接用dt,dt就是当前系统时间
				DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
				
				String accountcode =accountHandleService.generateAccount(Constants.ACCOUNT_PREFIX, user.getId());
				//新增主账户表
				BsAccount bsAccount = new BsAccount();
				bsAccount.setStatus(1);
				bsAccount.setAccountCode(accountcode);
				bsAccount.setUserId(user.getId());
				bsAccount.setOpenTime(dt);
				bsAccount.setLastTransTime(dt);
				accountService.addAccount(bsAccount);
				String code =accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_JSH, user.getId());
				//新增(结算户)子账户表			
				BsSubAccount bsSubAccount = new BsSubAccount();			
				bsSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_OPEN);
				bsSubAccount.setOpenBalance(0.0);
				bsSubAccount.setBalance(0.0);
				bsSubAccount.setAvailableBalance(0.0);
				bsSubAccount.setFreezeBalance(0.0);
				bsSubAccount.setCanWithdraw(0.0);
				bsSubAccount.setAccumulationInerest(0.0);
				bsSubAccount.setProductType(Constants.PRODUCT_TYPE_JSH);
				bsSubAccount.setTransStatus(0);
				bsSubAccount.setAccountId(bsAccount.getId());
				bsSubAccount.setCode(code);
				bsSubAccount.setOpenTime(dt);
				subAccountService.addSubAccount(bsSubAccount);
				
				//新增(奖励金)子账户表
				String jljCode =accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_JLJ, user.getId());
		        BsSubAccount jljSubAccount = new BsSubAccount();
		        jljSubAccount.setStatus(Constants.SUBACCOUNT_STATUS_OPEN);
		        jljSubAccount.setOpenBalance(0.0);
		        jljSubAccount.setBalance(0.0);
		        jljSubAccount.setAvailableBalance(0.0);
		        jljSubAccount.setFreezeBalance(0.0);
		        jljSubAccount.setCanWithdraw(0.0);
		        jljSubAccount.setAccumulationInerest(0.0);
		        jljSubAccount.setProductType(Constants.PRODUCT_TYPE_JLJ);
		        jljSubAccount.setTransStatus(0);
		        jljSubAccount.setAccountId(bsAccount.getId());
		        jljSubAccount.setCode(jljCode);
		        jljSubAccount.setOpenTime(dt);
		        subAccountService.addSubAccount(jljSubAccount);

				//开通DEP_JSH
				//新增(存管结算户)子账户表
				String depJshCode =accountHandleService.generateAccount(Constants.SUB_ACCOUNT_PREFIX_DEP_JSH, user.getId());
				BsSubAccount depJsh = new BsSubAccount();
				depJsh.setStatus(Constants.SUBACCOUNT_STATUS_OPEN);
				depJsh.setOpenBalance(0.0);
				depJsh.setBalance(0.0);
				depJsh.setAvailableBalance(0.0);
				depJsh.setFreezeBalance(0.0);
				depJsh.setCanWithdraw(0.0);
				depJsh.setAccumulationInerest(0.0);
				depJsh.setProductType(Constants.PRODUCT_TYPE_DEP_JSH);
				depJsh.setTransStatus(0);
				depJsh.setAccountId(bsAccount.getId());
				depJsh.setCode(depJshCode);
				depJsh.setOpenTime(new Date());
				subAccountService.addSubAccount(depJsh);
				//end
			}
		});
		return true;
	}

	@Override
	@MethodRole("APP")
	public BsUser findUserById(int userId) {
		return userMapper.selectByPrimaryKey(userId);
	}
	@Override
	public Integer countBuyNum(int userId) {
		BsAccountJnlExample bsAccountJnlExample = new BsAccountJnlExample();
		Date beginDate = DateUtil.parseDate(DateUtil.formatYYYYMMDD(new Date()));
		Date endDate = DateUtil.addDays(beginDate, 1);
		bsAccountJnlExample.createCriteria().andUserId1EqualTo(userId)
			.andTransTimeBetween(beginDate, endDate).andStatusEqualTo(Constants.ACCOUNT_JNL_STATUS_SUCCESS)
				.andTransTypeEqualTo(Constants.TRANS_TYPE_TRANSFER);
		return bsAccountJnlMapper.countByExample(bsAccountJnlExample);
	}
	
	@Override
	@MethodRole("APP")
	public BsUserAssetVO findUserAssetByUserId(Integer userId) {
		
		return userMapper.selectUserAssetByUserId(userId);
	}
	@Override
	@MethodRole("APP")
	public Boolean updateUserPasswordByMobile(String password, String mobile) {
		BsUser user = new BsUser();
		user.setPassword(password);
		user.setLoginFailTimes(0);
		BsUserExample example = new BsUserExample();
		example.createCriteria().andMobileEqualTo(mobile);
		return userMapper.updateByExampleSelective(user, example)==1;
	}
	@Override
	@MethodRole("APP")
	public Boolean updateBsUser(BsUser bsUser) {
		return userMapper.updateByPrimaryKeySelective(bsUser)>0;
	}
	
	@Override
	@MethodRole("APP")
	public Boolean addUserFeedback(BsFeedback bsFeedback) {
		
		return bsFeedbackMapper.insertSelective(bsFeedback) == 1 ? true :false;
	}
	
	@Override
	public Boolean updateUserPayPasswordByMobile(String payPassword,String mobile) {
		BsUser user = new BsUser();
		user.setPayPassword(payPassword);
		BsUserExample example = new BsUserExample();
		example.createCriteria().andMobileEqualTo(mobile);
		return userMapper.updateByExampleSelective(user, example)==1;
	}
	@Override
	public Boolean modifyBonusByIdAndIncrement(BsUser bsUser) {
		return userMapper.updateBonusById(bsUser) > 0 ? true :false;
	}
	@Override
	public DafyUserExt findDafyUserByUserId(Integer userId) {
		DafyUserExtExample example = new DafyUserExtExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<DafyUserExt> list = dafyUserExtMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	@Override
	@MethodRole("APP")
	public int countRegNum() {
		BsUserExample userExample = new BsUserExample();
		userExample.createCriteria().andStatusEqualTo(Constants.USER_STATUS_1);
		
		return userMapper.countByExample(userExample);
	}
	@Override
	public int countDayRegNum() {
		BsUserExample userExample = new BsUserExample();
		userExample.createCriteria().andStatusEqualTo(Constants.USER_STATUS_1).andRegisterTimeBetween(DateUtil.addDays(new Date(), -1), new Date());
		return userMapper.countByExample(userExample);
	}
	@Override
	public DafyUserExt findDafyUserByDafyId(String dafyId) {
		DafyUserExtExample example = new DafyUserExtExample();
		example.createCriteria().andDafyUserIdEqualTo(dafyId);
		List<DafyUserExt> list = dafyUserExtMapper.selectByExample(example);
		return list.size() > 0 ? list.get(0) : null;
	}
	@Override
	public List<BsUser> findUserForBindCardTimeout(Date createTime) {
		List<BsUser> list = userMapper.selectUserForBindCardTimeout(createTime);
		return list.size() > 0 ? list : null;
	}
	@Override
	@MethodRole("APP")
	public List<BsUser> findSubUserByUserId(Integer userId,
			Integer pageIndex, Integer pageSize) {
		Map<String, Object> data=new HashMap<String, Object>();
		data.put("userId", userId);
		data.put("start", pageIndex * pageSize);
		data.put("pageSize", pageSize);
		return userMapper.selectSubUserByUserId(data);
	}
	@Override
	@MethodRole("APP")
	public double countSubAccountByUserId(Integer userId) {
		BsUserExample example = new BsUserExample();
		
		example.createCriteria().andRecommendIdEqualTo(userId);
		return userMapper.countByExample(example);
	}
	@Override
	@MethodRole("APP")
	public boolean isValidCode(int recommendId) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIdEqualTo(recommendId).andStatusNotEqualTo(Constants.USER_STATUS_2);
		return userMapper.countByExample(example)==1?true:false;
	}
	@Override
	public Boolean modifyCurrInterestByIdAndIncrement(Integer userId,
			Double amount) {
		BsUser record = new BsUser();
		record.setId(userId);
		record.setCurrentInterest(amount);
		return userMapper.updateCurrInterestById(record) == 1 ? true :false;
	}
	
	@Override
	@MethodRole("APP")
	public BsAgent findAgentByCode(Integer agentId) {
		BsAgent bsAgent = bsAgentMapper.selectByPrimaryKey(agentId);
		return bsAgent;
	}
	@Override
	public void increaseAgentReadCount(Integer id) {
		bsAgentMapper.increaseViewTimesById(id);
	}
	@Override
	@MethodRole("APP")
	public boolean isBindBank(Integer userId) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIsBindBankEqualTo(Constants.IS_BIND_BANK_YES).andIsBindNameEqualTo(Constants.IS_BIND_NAME_YES)
		.andIdEqualTo(userId).andStatusIn(Arrays.asList(new Integer[]{Constants.USER_STATUS_1, BsUserStatus.USER_STATUS_FREEZE.getIntValue()}));
		List<BsUser> list = userMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list) && list.size() > 0) {
			return true;
		}
		return false;
	}
	@Override
	@MethodRole("APP")
	public List<BsSubAccount> selectSubAccount(Integer userId, String productType, Integer status) {
		return subAccountMapper.selectSubAccount(userId, productType, status);
	}
	@Override
	@MethodRole("APP")
	public double countUserIncome() {
		return userMapper.countUserIncome();
	}
	@Override
	public boolean changeUserReturnPath(Integer userId) {
		BsUser bsUser = new BsUser();
		bsUser.setId(userId);
		bsUser.setReturnPath(Constants.RETURN_PATH_BALANCE);
		userMapper.updateByPrimaryKeySelective(bsUser);
		return true;
	}
    /** 
     * @see com.pinting.business.service.site.UserService#findAgentById(java.lang.Integer)
     */
    @Override
    @MethodRole("APP")
    public BsAgent findAgentById(Integer id) {
        BsAgent agent = bsAgentMapper.selectByPrimaryKey(id);
        return agent;
    }
	@Override
	@MethodRole("APP")
	public List<BsAppMessage> findMessageList(Integer userId, Integer page) {
		Integer rows = 10;
		Integer start = (page - 1) * rows;
		List<BsAppMessage> list = bsAppMessageMapper.findMessageList(userId, start, rows);
		return list;
	}
	@Override
	@MethodRole("APP")
	public Integer findMessageListCount(Integer userId) {
		Integer rows = 10;
		Integer count = bsAppMessageMapper.findMessageListCount(userId);
		if(count == 0) {
			return 1;
		}
		else {
			return count%rows==0?count/rows:count/rows+1;
		}
	}
	@Override
	@MethodRole("APP")
	public BsAppMessage findMessageById(Integer id) {
		BsAppMessage message = bsAppMessageMapper.selectByPrimaryKey(id);
		return message;
	}
	@Override
	@MethodRole("APP")
	public Map<String, Object> isExistClientManager(String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("result", false);
		B2GReqMsg_Customer_QueryUserByPhone req = new B2GReqMsg_Customer_QueryUserByPhone();
		req.setMobile(mobile);
		B2GResMsg_Customer_QueryUserByPhone res = dafyService.queryUserByPhone(req);
		if(res.getCode() != null && res.getCode() == 0 && res.getStrSalesman() != null) {
			Map<String,Object> model = res.getStrSalesman();
			Integer workStatus = (Integer)model.get("nWorkState");
			Integer dafyUserId = Integer.valueOf((String)model.get("lUserId"));
			
			if(workStatus == 1) {
				map.put("result", true);
				map.put("dafyUserId", dafyUserId);
				
				return map;
			}
			else {
				return map;
			}
			
		}
		return map;
	}
	
	@Override
	public boolean gainInitPassword(String mobile) {
		//查询客户经理
		B2GReqMsg_Customer_QueryUserByPhone req = new B2GReqMsg_Customer_QueryUserByPhone();
		req.setMobile(mobile);
		B2GResMsg_Customer_QueryUserByPhone res = dafyService.queryUserByPhone(req);
		if(res.getCode() != null && res.getCode() == 0 && res.getStrSalesman() != null) {
			Map<String,Object> model = res.getStrSalesman();
			Integer workStatus = (Integer)model.get("nWorkState");
			if(workStatus == 1) { //客户经理在职
				MUserExample example = new MUserExample();
				example.createCriteria().andMobileEqualTo(mobile);
				List<MUser> muserList = muserMapper.selectByExample(example);
				
				if(!CollectionUtils.isEmpty(muserList)) {
					MUser user = muserList.get(0);
					if(user.getLoginTime() == null) {
						return true;
					}
				}
				else {
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public Map<String,Object> checkClientManagerLogin(String mobile) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("result", false);
		
		B2GReqMsg_Customer_QueryUserByPhone req = new B2GReqMsg_Customer_QueryUserByPhone();
		req.setMobile(mobile);
		B2GResMsg_Customer_QueryUserByPhone res = dafyService.queryUserByPhone(req);
		if(res.getCode() != null && res.getCode() == 0 && res.getStrSalesman() != null) {
			Map<String,Object> model = res.getStrSalesman();
			Integer workStatus = (Integer)model.get("nWorkState");
			String dafyUserId = (String)model.get("lUserId");
			String dafyUserName = (String)model.get("strName");
			String deptCode = (String)model.get("strDeptCode");
			String deptName = (String)model.get("strDeptName");
			String deptId = (String)model.get("lDeptId");
			Integer isManager = (Integer)model.get("nManager");
			
			if(workStatus == 1) { //客户经理在职
				map.put("result", true);
				map.put("dafyUserId", dafyUserId);
				map.put("dafyUserName", dafyUserName);
				map.put("dafyDeptCode", deptCode);
				map.put("dafyDeptName", deptName);
				map.put("dafyDeptId", deptId);
				map.put("isManager", isManager);
				String tempRoles = (String)model.get("strRoles");
				if(StringUtil.isNotBlank(tempRoles)) {
					map.put("dafyRoles", tempRoles);
				}
			}
		}
			
		return map;
	}
	
	@Override
	public boolean sendInitPassword(String mobile) {
		B2GReqMsg_Customer_QueryUserByPhone req = new B2GReqMsg_Customer_QueryUserByPhone();
		req.setMobile(mobile);
		B2GResMsg_Customer_QueryUserByPhone res = dafyService.queryUserByPhone(req);
		if(res.getCode() != null && res.getCode() == 0 && res.getStrSalesman() != null) {
			Map<String,Object> model = res.getStrSalesman();
			Integer workStatus = (Integer) model.get("nWorkState");
			if(workStatus == 1) {
				MUserExample example = new MUserExample();
				example.createCriteria().andMobileEqualTo(mobile);
				List<MUser> muserList = muserMapper.selectByExample(example);
				int password = (int)((Math.random()*9+1)*100000);
				Date now = new Date();
				if(CollectionUtils.isEmpty(muserList)) {
					MUser user = new MUser();
					user.setName((String)model.get("strName"));
					user.setNick((String)model.get("strName"));
					user.setPassword(MD5Util.encryptMD5(String.valueOf(password) + MD5Util.getEncryptkey()));
					user.setMobile(mobile);
					user.setStatus(Constants.USER_STATUS_1);
					user.setUserType(Constants.M_USER_TYPE_CUSTOMER_MANAGER);
					user.setCreateTime(now);
					muserMapper.insertSelective(user);
				}
				else {
					MUser user = muserList.get(0);
					user.setPassword(MD5Util.encryptMD5(String.valueOf(password) + MD5Util.getEncryptkey()));
					muserMapper.updateByPrimaryKey(user);
				}
				//发送短信
				smsServiceClient.sendTemplateMessage(mobile, TemplateKey.MANAGER_INIT_PASSWORD, String.valueOf(password));
				return true;
			}
		}
		return false;
	}

    /**
     * @param userId
     * @return
     */
    @Override
    public boolean checkCanBuyEcupNewUser(Integer userId, Integer productId) {
        // 判断是新手用户且是欧洲杯新手用户标
        List<BsProductVO> newUserPros = ecup2016ActivityService.queryEcupProductGrid(null, "NEW_USER");
        // 判断是否欧洲杯新手用户标
        if(!CollectionUtils.isEmpty(newUserPros)) {
            List<String> transTypeList = new ArrayList<String>();
            transTypeList.add(Constants.TRANS_CARD_BUY_PRODUCT);
            transTypeList.add(Constants.TRANS_BALANCE_BUY_PRODUCT);
            for (BsProductVO pro : newUserPros) {
                Integer proId = pro.getId();
                if(proId.equals(productId)) {
                    //必须之前没有买过产品
                    BsPayOrdersExample payOrderExample = new BsPayOrdersExample();
                    payOrderExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeIn(transTypeList).andUserIdEqualTo(userId);
                    Integer investCount = payOrderMapper.countByExample(payOrderExample);
                    if(investCount > 0) {
//                        throw new PTMessageException(PTMessageEnum.USER_ALREADY_BUY_PRODUCT);
                        return false;
                    }
                    break;
                }
            }
        }
        return true;
    }
	@Override
	public Double userBalanceQuery(String userId) {
		BsSubAccount subAccount = subAccountService.findJSHSubAccountByUserId(Integer.parseInt(userId));
		if (subAccount == null) {
			return 0.0;
		}
		return subAccount.getAvailableBalance();
	}

	@Override
	public int countInTheInvestmentByUserId(Integer userId) {
		return subAccountMapper.countInTheInvestmentByUserId(userId);
	}
	@Override
	public void updataAppVersionByUserId(Integer userId, String appVersion) {
		BsUser user = new BsUser();
		user.setId(userId);
		user.setLastAppVersion(appVersion);
		user.setLastAppTime(new Date());
		userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<BsAgentViewConfig> queryByAgentId(Integer agentId) {
		BsAgentViewConfigExample bsAgentViewConfigExample = new BsAgentViewConfigExample();
		if(agentId != null) {
			bsAgentViewConfigExample.createCriteria().andAgentIdEqualTo(agentId);
		}
		List<BsAgentViewConfig> views = bsAgentViewConfigMapper.selectByExample(bsAgentViewConfigExample);
		return views;
	}

	@Override
	public Double queryAmountTrans(Integer userId) {
		return userMapper.selectAmountTrans(userId);
	}
	
	
	@Override
	public boolean phoneIsSame(Integer userId) {
		String mobile = userMapper.selectByPrimaryKey(userId).getMobile();
		BsBankCardExample example = new BsBankCardExample();
		example.createCriteria().andUserIdEqualTo(userId).andIsFirstEqualTo(Constants.IS_FIRST_BANK_YES)
				.andStatusEqualTo(Constants.BANK_CARD_NORMAL);
		List<BsBankCard> list = bsBankCardMapper.selectByExample(example);
		String bankMobile = list == null?"":list.get(0).getMobile();
		return mobile.equals(bankMobile);
	}
	@Override
	public boolean isOldUser(Integer userId) {
		//根据用户id查询已经起息的REG数量+ln_loan_relation表中存在关系或已还的数量
		Integer countInvestedNum = subAccountMapper.countInvestedNum(userId);
		if(countInvestedNum <= 0){
			return false;
		}
		return true;
	}
	@MethodRole("APP")
	@Override
	public BsUserActivateVO activatePageInfo(Integer userId) {
		BsUser user = userMapper.selectByPrimaryKey(userId);
		
//		smsService.sendIdentify(user.getMobile());
    	
		BsHfbankUserExtExample bsHfbankUserExtExample = new BsHfbankUserExtExample();
		bsHfbankUserExtExample.createCriteria().andUserIdEqualTo(user.getId());
		List<BsHfbankUserExt> bsHfbankUserExts = bsHfbankUserExtMapper.selectByExample(bsHfbankUserExtExample);
		
		BsBankCardExample bankCardExample = new BsBankCardExample();
		bankCardExample.createCriteria().andUserIdEqualTo(user.getId()).andStatusEqualTo(1).andIsFirstEqualTo(1);
		List<BsBankCard> bankCards = bsBankCardMapper.selectByExample(bankCardExample);
		
		BsBank bank = bankMapper.selectByPrimaryKey(bankCards.get(0).getBankId());

		BsUserActivateVO bsUserActivateVO = new BsUserActivateVO();
		bsUserActivateVO.setMobile(user.getMobile());
		bsUserActivateVO.setUserName(user.getUserName());
		bsUserActivateVO.setIdCard(user.getIdCard());
		bsUserActivateVO.setBankCard(bankCards.get(0).getCardNo());
		bsUserActivateVO.setBankId(String.valueOf(bankCards.get(0).getBankId()));
		bsUserActivateVO.setBankName(bankCards.get(0).getBankName());
		bsUserActivateVO.setSmallLogo(bank.getSmallLogo());
		bsUserActivateVO.setLargeLogo(bank.getLargeLogo());
		bsUserActivateVO.setDepAccount(bsHfbankUserExts.get(0).getHfUserId());
		return bsUserActivateVO;
	}
	@MethodRole("APP")
	@Override
	public void activateDepAccount(Integer userId, String mobileCode) {
		BsUser user = userMapper.selectByPrimaryKey(userId);
		if (user == null) {
			throw new PTMessageException(PTMessageEnum.USER_INFO_NOT_FOUND);
		}
		//短信校验
        boolean IsValidate = smsService
            .validateIdentity(user.getMobile(), mobileCode, true);
        if (!IsValidate) {
            throw new PTMessageException(PTMessageEnum.MOBILE_CODE_WRONG_ERROR);
        }
        //查询用户恒丰账户信息
        BsHfbankUserExtExample bsHfbankUserExtExample = new BsHfbankUserExtExample();
		bsHfbankUserExtExample.createCriteria().andUserIdEqualTo(user.getId()).andStatusNotEqualTo(Constants.HFBANK_USER_EXT_STATUS_CANCEL);
		List<BsHfbankUserExt> bsHfbankUserExts = bsHfbankUserExtMapper.selectByExample(bsHfbankUserExtExample);
		if (CollectionUtils.isEmpty(bsHfbankUserExts)) {
			throw new PTMessageException(PTMessageEnum.HF_NOT_BIND);
		}
		BsHfbankUserExt bsHfbankUserExt = bsHfbankUserExts.get(0);
		
		if (Constants.HFBANK_USER_EXT_STATUS_OPEN.equals(bsHfbankUserExt.getStatus())) {
			throw new PTMessageException(PTMessageEnum.HF_BINDED_ACTIVATE_REPEAT);
		}
		hfBankDepSiteService.activate(userId);

	}

	@Override
	public void userSessionConnection(BsUserSessionConnectionInfo req, String logout) {

		if(req.getUserId() != null) {
			try {
				jsClientDaoSupport.lock(RedisLockEnum.LOCK_SESSION_CONNECTION.getKey() + req.getUserId());
				// 相同IP，相同session，且是登录状态的记录
				if(Constants.SESSION_TERMINAL_PC.equals(req.getTerminal()) || Constants.SESSION_TERMINAL_H5.equals(req.getTerminal())) {
					BsUserSessionConnectionInfoExample example = new BsUserSessionConnectionInfoExample();
					example.createCriteria().andUserIdEqualTo(req.getUserId())
							.andTerminalEqualTo(req.getTerminal()).andSessionIdEqualTo(req.getSessionId()).andIpEqualTo(req.getIp());
					List<BsUserSessionConnectionInfo> infoList = bsUserSessionConnectionInfoMapper.selectByExample(example);
					BsUserSessionConnectionInfo info = new BsUserSessionConnectionInfo();
					if(CollectionUtils.isEmpty(infoList)) {
						this.insertBsUserSessionConnection(info, req.getUserId(), req.getSessionId(), req.getDeviceToken(),
								req.getIp(), req.getTerminal(), Constants.SESSION_STATUS_LOGIN, Constants.SESSION_FORCED_LOGOUT_NO);
					} else if(infoList.get(0).getStatus().equals(Constants.SESSION_STATUS_LOGOUT)) {
						this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGIN, Constants.SESSION_FORCED_LOGOUT_NO, new Date(), new Date());
					} else if(infoList.get(0).getStatus().equals(Constants.SESSION_STATUS_LOGIN) &&
							infoList.get(0).getForcedLogout().equals(Constants.SESSION_FORCED_LOGOUT_YES)) {
						if(Constants.IS_YES.equals(logout)) {
							this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGOUT, null, new Date(), null);
						} else if(Constants.SESSION_STATUS_LOGIN.equals(logout)) {
							this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGIN, Constants.SESSION_FORCED_LOGOUT_NO, new Date(), new Date());
						} else {
							throw new PTMessageException(PTMessageEnum.USER_NEED_FORCED_LOGOUT);
						}
					} else if (infoList.get(0).getStatus().equals(Constants.SESSION_STATUS_LOGIN)) {
						if(Constants.IS_YES.equals(logout)) {
							this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGOUT, null, new Date(), null);
						}
					}
					List<BsUserSessionConnectionInfo> newInfoList = bsUserSessionConnectionInfoMapper.selectByExample(example);
					if(Constants.SESSION_STATUS_LOGIN.equals(newInfoList.get(0).getStatus())) {
						if(null == newInfoList.get(0).getExpireTime()) {
							BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.USER_LOGIN_COOKIE_MAX_AGE);
							newInfoList.get(0).setExpireTime(DateUtil.addSeconds(newInfoList.get(0).getLoginTime(), Integer.parseInt(config.getConfValue())));
						}
						if(!newInfoList.get(0).getExpireTime().after(new Date())) {
							updateBsUserSessionConnection(new BsUserSessionConnectionInfo(), newInfoList.get(0).getId(), null, Constants.SESSION_FORCED_LOGOUT_YES, new Date(), null);
							throw new PTMessageException(PTMessageEnum.USER_NEED_FORCED_LOGOUT);
						}
					}

					List<String> terminalList = new ArrayList<>();
					terminalList.add(Constants.SESSION_TERMINAL_PC);
					terminalList.add(Constants.SESSION_TERMINAL_H5);
					Integer maxSessionNumber = Integer.parseInt(bsSysConfigMapper.selectByPrimaryKey(Constants.CONFIG_KEY_SYS_MAX_SESSION_NUMBER).getConfValue());
					Integer sameIpSingleMaxSessionNumber = Integer.parseInt(bsSysConfigMapper.selectByPrimaryKey(Constants.CONFIG_KEY_SAME_IP_SINGLE_MAX_SESSION_NUMBER).getConfValue());
					Integer differentIpSingleMaxSessionNumber = Integer.parseInt(bsSysConfigMapper.selectByPrimaryKey(Constants.CONFIG_KEY_DIFFERENT_IP_SINGLE_MAX_SESSION_NUMBER).getConfValue());

					int sameIPSessionNumber = bsUserSessionConnectionInfoMapper.countSameIPSessionNumber(req.getUserId(), req.getIp());
					if(sameIPSessionNumber > sameIpSingleMaxSessionNumber) {
						// 相同IP下的会话数超过单一最大会话数量（将同IP下最早登录的记录设置为强制登出）
						BsUserSessionConnectionInfo bsUserSessionConnectionInfo = bsUserSessionConnectionInfoMapper.selectEarliestLoginRecord(req.getUserId(), req.getIp());
						updateBsUserSessionConnection(new BsUserSessionConnectionInfo(), bsUserSessionConnectionInfo.getId(), null, Constants.SESSION_FORCED_LOGOUT_YES, new Date(), null);
					}

					int totalIpNumber = bsUserSessionConnectionInfoMapper.countTotalIPSessionNumber(req.getUserId());
					int differentIPNumber = bsUserSessionConnectionInfoMapper.countSameIPSessionNumber(req.getUserId(), null);
					if(totalIpNumber > 1 && differentIPNumber > differentIpSingleMaxSessionNumber) {
						// 不同IP超过IP最大数量（将最早登录所在IP的所有记录设置为强制登出）
						BsUserSessionConnectionInfo bsUserSessionConnectionInfo = bsUserSessionConnectionInfoMapper.selectEarliestLoginRecord(req.getUserId(), null);
						BsUserSessionConnectionInfoExample infoExample = new BsUserSessionConnectionInfoExample();
						infoExample.createCriteria().andUserIdEqualTo(bsUserSessionConnectionInfo.getUserId()).andIpEqualTo(bsUserSessionConnectionInfo.getIp()).andTerminalIn(terminalList).andForcedLogoutEqualTo(Constants.SESSION_FORCED_LOGOUT_NO);
						BsUserSessionConnectionInfo sessionInfo = new BsUserSessionConnectionInfo();
						sessionInfo.setForcedLogout(Constants.SESSION_FORCED_LOGOUT_YES);
						sessionInfo.setUpdateTime(new Date());
						bsUserSessionConnectionInfoMapper.updateByExampleSelective(sessionInfo, infoExample);
					}

					int totalSessionNumber = bsUserSessionConnectionInfoMapper.countTotalSessionNumber(req.getUserId());
					if(totalSessionNumber > maxSessionNumber) {
						// 总会话数 > 系统最大并发会话数（将最早登录的记录设置为强制登出）
						BsUserSessionConnectionInfo bsUserSessionConnectionInfo = bsUserSessionConnectionInfoMapper.selectEarliestLoginRecord(req.getUserId(), null);
						updateBsUserSessionConnection(new BsUserSessionConnectionInfo(), bsUserSessionConnectionInfo.getId(), null, Constants.SESSION_FORCED_LOGOUT_YES, new Date(), null);
					}
				} else if(Constants.SESSION_TERMINAL_IOS.equals(req.getTerminal()) || Constants.SESSION_TERMINAL_ANDROID.equals(req.getTerminal())) {
					BsUserSessionConnectionInfoExample example = new BsUserSessionConnectionInfoExample();
					example.createCriteria().andUserIdEqualTo(req.getUserId())
							.andTerminalEqualTo(req.getTerminal()).andDeviceTokenEqualTo(req.getDeviceToken()).andIpEqualTo(req.getIp());
					List<BsUserSessionConnectionInfo> infoList = bsUserSessionConnectionInfoMapper.selectByExample(example);
					BsUserSessionConnectionInfo info = new BsUserSessionConnectionInfo();
					if(CollectionUtils.isEmpty(infoList)) {
						this.insertBsUserSessionConnection(info, req.getUserId(), req.getSessionId(), req.getDeviceToken(),
								req.getIp(), req.getTerminal(), Constants.SESSION_STATUS_LOGIN, Constants.SESSION_FORCED_LOGOUT_NO);
					} else if(infoList.get(0).getStatus().equals(Constants.SESSION_STATUS_LOGOUT)) {
						this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGIN, Constants.SESSION_FORCED_LOGOUT_NO, new Date(), new Date());
					} else if(infoList.get(0).getStatus().equals(Constants.SESSION_STATUS_LOGIN) &&
							infoList.get(0).getForcedLogout().equals(Constants.SESSION_FORCED_LOGOUT_YES)) {
						if(Constants.IS_YES.equals(logout)) {
							this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGOUT, null, new Date(), null);
						} else if(Constants.SESSION_STATUS_LOGIN.equals(logout)) {
							this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGIN, Constants.SESSION_FORCED_LOGOUT_NO, new Date(), new Date());
						} else {
							throw new PTMessageException(PTMessageEnum.USER_NEED_FORCED_LOGOUT);
						}
					} else if (infoList.get(0).getStatus().equals(Constants.SESSION_STATUS_LOGIN)) {
						if(Constants.IS_YES.equals(logout)) {
							this.updateBsUserSessionConnection(info, infoList.get(0).getId(), Constants.SESSION_STATUS_LOGOUT, null, new Date(), null);
						}
					}

					Integer maxDeviceNumber = Integer.parseInt(bsSysConfigMapper.selectByPrimaryKey(Constants.CONFIG_KEY_SYS_MAX_DEVICE_NUMBER).getConfValue());
					Integer sameIpSingleMaxDeviceNumber = Integer.parseInt(bsSysConfigMapper.selectByPrimaryKey(Constants.CONFIG_KEY_SAME_IP_SINGLE_MAX_DEVICE_NUMBER).getConfValue());
					Integer differentIpSingleMaxDeviceNumber = Integer.parseInt(bsSysConfigMapper.selectByPrimaryKey(Constants.CONFIG_KEY_DIFFERENT_IP_SINGLE_MAX_DEVICE_NUMBER).getConfValue());

					int totalIpNumber = bsUserSessionConnectionInfoMapper.countTotalIPDeviceNumber(req.getUserId());
					int totalDeviceNumber = bsUserSessionConnectionInfoMapper.countTotalDeviceNumber(req.getUserId());
					if(totalDeviceNumber > maxDeviceNumber) {
						// 总会话数 > 系统最大并发会话数（将最早登录的记录设置为强制登出）
						BsUserSessionConnectionInfo bsUserSessionConnectionInfo = bsUserSessionConnectionInfoMapper.selectEarliestDeviceLoginRecord(req.getUserId(), null);
						updateBsUserSessionConnection(new BsUserSessionConnectionInfo(), bsUserSessionConnectionInfo.getId(), null, Constants.SESSION_FORCED_LOGOUT_YES, new Date(), null);
					}
					int sameIPDeviceNumber = bsUserSessionConnectionInfoMapper.countSameIPDeviceNumber(req.getUserId(), req.getIp());
					if(sameIPDeviceNumber > sameIpSingleMaxDeviceNumber) {
						// 相同IP下的会话数超过单一最大会话数量（将最早登录的记录设置为强制登出）
						BsUserSessionConnectionInfo bsUserSessionConnectionInfo = bsUserSessionConnectionInfoMapper.selectEarliestDeviceLoginRecord(req.getUserId(), req.getIp());
						updateBsUserSessionConnection(new BsUserSessionConnectionInfo(), bsUserSessionConnectionInfo.getId(), null, Constants.SESSION_FORCED_LOGOUT_YES, new Date(), null);
					}

					int differentIPDeviceNumber = bsUserSessionConnectionInfoMapper.countSameIPDeviceNumber(req.getUserId(), null);
					if(totalIpNumber > 1 && differentIPDeviceNumber > differentIpSingleMaxDeviceNumber) {
						// 不同IP下的会话数超过不同IP下最大会话数量（将最早登录的记录设置为强制登出）
						BsUserSessionConnectionInfo bsUserSessionConnectionInfo = bsUserSessionConnectionInfoMapper.selectEarliestDeviceLoginRecord(req.getUserId(), null);
						updateBsUserSessionConnection(new BsUserSessionConnectionInfo(), bsUserSessionConnectionInfo.getId(), null, Constants.SESSION_FORCED_LOGOUT_YES, new Date(), null);
					}
				}
			} finally {
				jsClientDaoSupport.unlock(RedisLockEnum.LOCK_SESSION_CONNECTION.getKey() + req.getUserId());
			}
		}
	}

	@Override
	public void modifyUserAmountIncrement(BsUser bsUser) {
		userMapper.modifyUserAmountIncrement(bsUser);
	}

	private void updateBsUserSessionConnection(BsUserSessionConnectionInfo info, Integer id, String status, String forcedLogout, Date updateTime, Date loginTime) {
		info.setId(id);
		info.setStatus(status);
		info.setForcedLogout(forcedLogout);
		info.setLoginTime(loginTime);
		info.setUpdateTime(updateTime);
		if(loginTime != null) {
			BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.USER_LOGIN_COOKIE_MAX_AGE);
			info.setExpireTime(DateUtil.addSeconds(info.getLoginTime(), Integer.parseInt(config.getConfValue())));
		}
		bsUserSessionConnectionInfoMapper.updateByPrimaryKeySelective(info);
	}

	private void insertBsUserSessionConnection(BsUserSessionConnectionInfo info, Integer userId, String sessionId, String deviceToken,
											   String ip, String terminal, String status, String forcedLogout) {
		info.setUserId(userId);
		info.setSessionId(sessionId);
		info.setDeviceToken(deviceToken);
		info.setIp(ip);
		info.setTerminal(terminal);
		info.setStatus(status);
		info.setForcedLogout(forcedLogout);
		info.setLoginTime(new Date());
		info.setCreateTime(new Date());
		info.setUpdateTime(new Date());
		BsSysConfig config = bsSysConfigMapper.selectByPrimaryKey(Constants.USER_LOGIN_COOKIE_MAX_AGE);
		info.setExpireTime(DateUtil.addSeconds(info.getLoginTime(), Integer.parseInt(config.getConfValue())));
		bsUserSessionConnectionInfoMapper.insertSelective(info);
	}

	@Override
	public void addApplications(BsUserAddApplication application) {
		// 入redis
		try {
			ApplictionsDTO applictionsDTO = new ApplictionsDTO();
			applictionsDTO.setUserId(application.getUserId());
			applictionsDTO.setApplications(application.getApplications());
			jsClientDaoSupport.rpush("application_queue",
					JSON.toJSONString(applictionsDTO));
			logger.info(">>>入应用列表队列数据:" + JSON.toJSONString(applictionsDTO)
					+ "<<<");
		} catch (Exception e) {
			logger.error("入应用列表队列异常", e);
		}
	}

	@Override
	public void userAddAddress(BsUserAddAddress info) {
		// 入redis
		try {
			// 查用户地址队列中是否已存入
			AddressDTO addressDTO = new AddressDTO();
			addressDTO.setUserId(info.getUserId());
			addressDTO.setAddress(info.getAddress());
			jsClientDaoSupport.rpush("address_queue",
					JSON.toJSONString(addressDTO));
			logger.info(">>>入用户地址队列数据:" + JSON.toJSONString(addressDTO)
					+ "<<<");
		} catch (Exception e) {
			logger.error("入用户地址队列异常", e);
		}
	}

	@Override
	public boolean userStatusConfirmTransaction(Integer userId) {
		//对应用户状态冻结中则返回false
		BsUserExample example = new BsUserExample();
		example.createCriteria().andIdEqualTo(userId).andStatusEqualTo(BsUserStatus.USER_STATUS_FREEZE.getIntValue());
		List<BsUser> list = userMapper.selectByExample(example);
		if (org.apache.commons.collections.CollectionUtils.isNotEmpty(list)) {
			return false;
		}
		return true;
	}

	@Override
	public boolean isFreezeUserByMobile(String mobile) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(BsUserStatus.USER_STATUS_FREEZE.getIntValue());
		return userMapper.countByExample(example)==1?true:false;
	}

	@Override
	public boolean isFreezeUserByNick(String nick) {
		BsUserExample example = new BsUserExample();
		example.createCriteria().andNickEqualTo(nick).andStatusEqualTo(BsUserStatus.USER_STATUS_FREEZE.getIntValue());
		return userMapper.countByExample(example)==1?true:false;
	}

}
