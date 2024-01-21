package com.pinting.business.service.site;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsUserActivateVO;
import com.pinting.business.model.vo.BsUserAssetVO;

public interface UserService {
	/**
	 * 根据用户手机或昵称判断用户是否存在, 只要一个满足，即
	 * @param mobileOrNick 用户手机号或者昵称
	 * @return 如果找到信息，返回true，找不到则返回false
	 */
	public boolean isValidMobileOrNick(String mobileOrNick);
	/**
	 * 根据用户手机判断用户是否存在
	 * @param mobile 用户手机
	 * @return 如果找到信息，返回true，找不到则返回false
	 */
	public boolean isValidMobile(String mobile) ;
	/**
	 * 根据上线id判断上线用户是否存在
	 * @param recommendId 上线id
	 * @return 如果找到信息，返回true，找不到则返回false
	 */
	public boolean isValidCode(int recommendId) ;
	/**
	 * 根据用户昵称判断用户是否已存在
	 * @param nick 用户昵称
	 * @return 如果找到信息，返回true，找不到则返回false
	 */
	public boolean isValidNick(String nick) ;
	/**
	 * 注册用户，同时根据该用户id和主账户信息新增主账户，再根据用户id、主账户id和结算户信息来新增结账户
	 * @param user
	 * @param inviteCode 销售邀请码
	 * @return 如果全部新增成功，则返回true，否则返回
	 */
	public boolean registerUser(BsUser user,String inviteCode,Integer dafyUserId);
	/**
	 * 根据用户的手机号修改登陆密码
	 * @param password 密码
	 * @param mobile 用户手机
	 * @return 如果修改成功，则返回true，否则返回false
	 */
	public Boolean updateUserPasswordByMobile(String password,  String mobile);
	
	/**
	 * 根据用户的手机号修改支付密码
	 * @param payPassword 支付密码
	 * @param mobile 用户手机
	 * @return 如果修改成功，则返回true，否则返回false
	 */
	public Boolean updateUserPayPasswordByMobile(String payPassword, String mobile);
	/**
	 * 根据昵称查询用户信息
	 * @param nick 用户昵称
	 * @return 用户昵称在数据库中也是唯一，所以最多只返回一条BsUser记录。如果没找到，返回null
	 */
	public BsUser findUserByNick(String nick);
	/**
	 * 根据编号查询用户信息
	 * @param userId 用户编号
	 * @return 如果存在，返回BsUser对象, 否则返回null
	 */
	public BsUser findUserById(int userId);
	/**
	 * 根据手机查询用户信息
	 * @param mobile 用户昵称
	 * @return 用户手机在数据库中也是唯一，所以最多只返回一条BsUser记录。如果没找到，返回null
	 */
	public BsUser findUserByMobile(String mobile);
	/**
	 * 根据手机查询用户信息
	 * @param mobile 用户昵称
	 * @return 用户手机在数据库中也是唯一，所以最多只返回一条BsUser记录。如果没找到，返回null
	 */
	public BsUser findUserByEmail(String email);
	
	/**
	 * 根据用户编号查询当天已经购买的次数
	 * @param userId 用户编号
	 * @return 返回当前已经购买的次数
	 */
	public Integer countBuyNum(int userId);
	/**
	 * 查询用户资产信息
	 * @param userId 根据用户id，唯一获得用户资产信息
	 * @return 获得用户资产信息，返回类型为BsUserAssetVO,没有找到，返回null
	 */
	public BsUserAssetVO findUserAssetByUserId(Integer userId);
	/**
	 * 根据用户编号修改用户资料信息
	 * @param bsUser 用户资料（只修改bsUser中有的属性）
	 * @return 如果成功返回true，否则返回false
	 */
	public Boolean updateBsUser(BsUser bsUser);
	
	/**
	 * 保存用户 的 意见反馈
	 * @param bsFeedback 用户意见反馈
	 * @return 成功返回true，失败返回false
	 */
	public Boolean addUserFeedback(BsFeedback bsFeedback);
	/**
	 * 根据用户编号 增量 修改该用户累计奖励金、当前奖励金、可提现金额
	 * （如参数bsUser传入canWithdraw=100，实际修改情况是canWithdraw=原canWithdraw+100）
	 * @param bsUser
	 * @return 成功返回true，失败返回false
	 */
	public Boolean modifyBonusByIdAndIncrement(BsUser bsUser);
	
	/**
	 * 根据用户编号 增量 修改该用户当前投资收益
	 * @param userId
	 * @param amount
	 * @return 成功返回true，失败返回false
	 */
	public Boolean modifyCurrInterestByIdAndIncrement(Integer userId, Double amount);
	
	/**
	 * 根据用户编号，查询达飞的注册客户号
	 * @param userId 本地用户编号
	 * @return 达飞客户对象，失败返回null
	 */
	public DafyUserExt findDafyUserByUserId(Integer userId);
	/**
	 * 根据达飞客户号，查询记录
	 * @param dafyId 达飞客户号
	 * @return 达飞客户对象，失败返回null
	 */
	public DafyUserExt findDafyUserByDafyId(String dafyId);
	
	/**
	 * 计算当前总注册人数
	 * @return
	 */
	public int countRegNum();
	
	/**
	 * 计算当天的总注册人数
	 * @return
	 */
	public int countDayRegNum();
	
	/**
	 * 卡绑定超时时，查询用户信息，用作短信提示
	 * @param registerTime 已超时时间
	 * @return 成功返回list，否则返回null
	 */
	public List<BsUser> findUserForBindCardTimeout(Date registerTime);
	
	/**
	 * 根据userId查询下线
	 * @param userId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public List<BsUser> findSubUserByUserId(Integer userId,
			Integer pageIndex, Integer pageSize);
	
	/**
	 * 查询该用户底下多少个下线
	 * @param userId
	 * @return
	 */
	public double countSubAccountByUserId(Integer userId);
	
	/**
	 * 根据渠道编码查询渠道信息
	 * @param agentId
	 * @return 成功查询到数据 则返回渠道信息对象，否则返回null
	 */
	public BsAgent findAgentByCode(Integer agentId);
	
	/**
	 * 渠道浏览量增加+1
	 * 
	 * @param id
	 */
	public void increaseAgentReadCount(Integer id);
	
	/**
	 * 
	 * @Title: isBindBank 
	 * @Description: 是否绑定银行卡
	 * @param userId
	 * @return
	 * @throws
	 */
	public boolean isBindBank(Integer userId);
	
	/**
	 * 
	 * @Title: selectUserInfo 
	 * @Description: 查询用户信息
	 * @param userId
	 * @return
	 * @throws
	 */
	public List<BsSubAccount> selectSubAccount(Integer userId,String productType,Integer status);
	
	/**
	 * 
	 * @Title: countUserIncome
	 * @Description: 统计用户的总收入
	 * @return 总收入
	 * @throws
	 */
	public double countUserIncome();
	
	/**
	 * @Title: updateUserReturnPath
	 * @Description: 更改用户默认的回款路径
	 * @return 是否成功
	 * @throws
	 */
	public boolean changeUserReturnPath(Integer userId);
    
	/**
     * 根据ID查询渠道
     * @param id
     * @return
     */
    public BsAgent findAgentById(Integer id);
    
    /**
     * 
     * @Title: findMessageList 
     * @Description: 用户消息列表
     * @param userId
     * @param page
     * @return
     * @throws
     */
    public List<BsAppMessage> findMessageList(Integer userId, Integer page);
    
    /**
     * 
     * @Title: findMessageListCount 
     * @Description: 用户消息总数
     * @param userId
     * @param page
     * @return
     * @throws
     */
    public Integer findMessageListCount(Integer userId);
    
    /**
     * 
     * @Title: findMessageById 
     * @Description: 通过主键查询消息详情
     * @param id
     * @return
     * @throws
     */
    public BsAppMessage findMessageById(Integer id);
    
    
	
	/**
	 * 根据手机号（邀请码）查询达飞客户经理是否存在
	 * @param mobile
	 * @return
	 */
	public Map<String, Object> isExistClientManager(String mobile);
	
	/**
	 * 客户经理是否获得过管理台初始密码
	 * @param mobile
	 * @return true表示未获得过密码
	 */
	public boolean gainInitPassword(String mobile);
	
	/**
	 * 给客户经理发送管理台初始代码
	 * @param mobile
	 * @return
	 */
	public boolean sendInitPassword(String mobile);
	
	/**
	 * 客户经理管理台登录验证
	 * @param mobile
	 * @return true表示通过验证
	 */
	public Map<String,Object> checkClientManagerLogin(String mobile);
	
	/**
	 * 根据用户ID判断是否是新用户（未投资过）
	 * @param userId
	 * @return
	 */
	public boolean checkCanBuyEcupNewUser(Integer userId, Integer productId);
	/**
	 * 根据用户ID查询用户可用余额
	 * @param userId 用户ID
	 * @return
	 */
	public Double userBalanceQuery(String userId);

	/**
	 * 根据用户ID查询投资中的项目总数（港湾计划+委托计划，港湾计划原先的统计规则不变，委托计划统计规则（委托结束与回款结束状态）不在统计范围内）
	 * @param userId	用户ID
	 * @return			项目总数（港湾计划+委托计划，港湾计划原先的统计规则不变，委托计划统计规则（委托结束与回款结束状态）不在统计范围内）
     */
	int countInTheInvestmentByUserId(Integer userId);
	/**
	 * 更新用户APP版本信息
	 * @param userId  用户ID
	 * @param appVersion  App版本信息
	 */
	public void updataAppVersionByUserId(Integer userId, String appVersion);

	/**
	 * 根据渠道Id查找对应的展示信息
	 * @param agentId	渠道ID
	 * @return
     */
	List<BsAgentViewConfig> queryByAgentId(Integer agentId);

	Double queryAmountTrans(Integer userId);
	
	
	
	/**
	 * 根据用户id判断用户注册手机号和绑定银行卡预留手机号是否一致，一致则返回true
	 * @param userId
	 * @return
	 */
	public boolean phoneIsSame(Integer userId);
	
	/**
	 * 老用户（已有起息中的购买，赞分期产品已借出）
	 * @param userId
	 * @return
	 */
	public boolean isOldUser(Integer userId);
	/**
	 * 根据用户ID查询激活页面信息数据
	 * @param userId
	 * @return
	 */
	public BsUserActivateVO activatePageInfo(Integer userId);
	
	/**
	 * 我的资产_激活存管账户
	 * @param userId
	 * @param mobileCode
	 */
	public void activateDepAccount(Integer userId, String mobileCode);

	/**
	 * 用户会话连接信息，用户记录用户多端登录状态，控制最大会话数
	 * @param req		请求信息
	 * @param logout	是否登出操作：yes-是；no-否
     */
	void userSessionConnection(BsUserSessionConnectionInfo req, String logout);

	/**
	 * 增量更新用户的currentInterest、totalInterest
	 * @param bsUser
     */
	void modifyUserAmountIncrement(BsUser bsUser);
	
	/**
	 * 记录用户的已安装应用列表--入redis
	 * @param bsUser
     */
	void addApplications(BsUserAddApplication application);
	
	/**
	 * 记录用户的已安装应用列表--入redis
	 * @param bsUser
     */
	void userAddAddress(BsUserAddAddress info);
	
	/**
	 * 根据用户状态，确认交易是否可执行
	 * @param userId
	 * @return
	 */
	boolean userStatusConfirmTransaction(Integer userId);
	
	/**
	 * 根据用户手机判断用户是否冻结
	 * @param mobile
	 * @return
	 */
	boolean isFreezeUserByMobile(String mobile);
	
	/**
	 * 根据用户昵称判断用户是否冻结
	 * @param mobile
	 * @return
	 */
	boolean isFreezeUserByNick(String nick);
	
}
