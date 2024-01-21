package com.pinting.business.service.manage;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.pinting.business.hessian.manage.message.ReqMsg_BsUser_BsUserTagListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MUserOperate_UserOperateQuery;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.MUser;
import com.pinting.business.model.vo.*;

public interface BsUserService {
	/**
	 * 查询所有用户信息(带分页)
	 * @param searchMobile 手机
	 * @param status 用户状态
	 * @param pageNum 页码
	 * @param numPerPage 用户页面大小
	 * @param sEmail 邮箱
	 * @param sIsBindBank 是否绑定银行卡
	 * @param sIsBindName 是否实名
	 * @param sName 姓名
	 * @param sNick 用户名
	 * @param sRecommend 最小推荐人数
	 * @param eRecommend 最大推荐人数
	 * @param sReward 最小奖励金额
	 * @param eReward 最大奖励金额
	 * @param sRegistTime 开始注册时间
	 * @param eRegistTime 结束注册时间
	 * @param sIdCard 身份证
	 * @param sBankCard 回款银行卡号
	 * @param sAgent 渠道来源
	 * @param sFirstBuyTime 首次购买日期
	  * @return 如果查询成功返回用户信息，否则返回null
	 */
	public List<BsUserAssetVO> findAllUser(BsUserAssetVO bsUser);
	/**
	 * 查询所有用户总数
	 * @param status 用户状态
	 * @param sMobile 手机
	 * @param sEmail 邮箱
	 * @param sIsBindBank 是否绑定银行卡
	 * @param sIsBindName 是否实名
	 * @param sName 姓名
	 * @param sNick 用户名
	 * @param sRecommend 最小推荐人数
	 * @param eRecommend 最大推荐人数
	 * @param sReward 最小奖励金额
	 * @param eReward 最大奖励金额
	 * @param sRegistTime 开始注册时间
	 * @param eRegistTime 结束注册时间
	 * @param sIdCard 身份证
	 * @param sBankCard 回款银行卡号
	 * @param sAgent 渠道来源
	 * @param sFirstBuyTime 首次购买日期
	 * @return 如果查询成功返回用户数量，否则返回0
	 */
	public Integer findAllUserCount(BsUserAssetVO bsUser);
	/**
	 * 根据id查询用户信息表
	 * @param userId 用户编号
	 * @return 如果查询成功返回用户，否则返回null
	 */
	public BsUser findUserByUserId(Integer userId);
	/**
	 * 根据userId批量修改用户状态
	 * @param idList 用户Id集合
	 * @param status 用户将要修改的状态
	 * @return 如果成功返回true，否则返回false
	 */
	public Boolean updateBsUsersStatus(List<Integer> idList , Integer status);
	
	/**
	 * 分页 查询所有意见反馈列表
	 * @param pageNum 页码
	 * @param numPerPage 每页条数
	 * @param orderField 排序
	 * @param orderDirection
	 * @return 成功返回意见反馈列表，否则返回null
	 */
	public List<BsFeedbackVO> findBsFeedbacksByPage(String pageNum, String numPerPage, 
			String orderField, String orderDirection);
	
	/**
	 * 统计意见反馈列表总条数
	 * @return 成功返回意见反馈列表总条数，否则返回0
	 */
	public int countTotalBsFeedbacks();
	
	/**
	 * 统计 该用户的下线用户数
	 * @param recommendId
	 * @return
	 */
	public int findSubUserCount(Integer recommendId);
	
	/**
	 * 查询该用户下线用户的信息(带分页)
	 * @param recommendId
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public List<BsUserAssetVO> findSubUser(Integer recommendId, Integer pageNum, Integer numPerPage);
	
	/**
	 * 计算可提现金额
	 * @return
	 */
	public Double sumCanWithdraw();
	
	/**
	 * 查询所有用户信息(带分页)
	 * @param searchMobile 手机
	 * @param status 用户状态
	 * @param pageNum 页码
	 * @param numPerPage 用户页面大小
	 * @param sEmail 邮箱
	 * @param sIsBindBank 是否绑定银行卡
	 * @param sIsBindName 是否实名
	 * @param sName 姓名
	 * @param sNick 用户名
	 * @param sRecommend 最小推荐人数
	 * @param eRecommend 最大推荐人数
	 * @param sReward 最小奖励金额
	 * @param eReward 最大奖励金额
	 * @param sRegistTime 注册时间
	 * @param agentId 渠道id
	  * @return 如果查询成功返回用户信息，否则返回null
	 */
	public List<BsUserAssetVO> findAgentUser(String searchMobile , Integer status ,Integer pageNum,Integer numPerPage
			,String sEmail,Integer sIsBindBank,
			Integer sIsBindName,String sName,String sNick,String sRecommend,String eRecommend,
			String sReward,String eReward,Date sRegistTime,Date eRegistTime,String orderDirection,
			String orderField,Integer agentId, Integer regTerminal, String distributionChannel);

	/**
	 * 查询所有用户总数
	 * @param status 用户状态
	 * @param sMobile 手机
	 * @param sEmail 邮箱
	 * @param sIsBindBank 是否绑定银行卡
	 * @param sIsBindName 是否实名
	 * @param sName 姓名
	 * @param sNick 用户名
	 * @param sRecommend 最小推荐人数
	 * @param eRecommend 最大推荐人数
	 * @param sReward 最小奖励金额
	 * @param eReward 最大奖励金额
	 * @param sRegistTime 注册时间
	 * @param agentId 渠道id
	 * @return 如果查询成功返回用户数量，否则返回0
	 */

	public Integer findAgentUserCount(Integer status,String sMobile,String sEmail,Integer sIsBindBank,
			Integer sIsBindName,String sName,String sNick,String sRecommend,String eRecommend,
			String sReward,String eReward,Date sRegistTime,Date eRegistTime,Integer agentId, Integer regTerminal, String distributionChannel);

	/**
	 * 统计注册用户总数
	 * @param map 查询条件
	 * @return map
	 */
	public Map<String,Object> countResgisterUser(Map<String,Object> map);
	
	/**
	 * 公众号按时间统计注册用户总数
	 * @param map 查询条件
	 * @return map
	 */
	public int countResgisterUserByTime(Date time);
	
	/**
	 * 客户信息：分页查询
	 * @param bsUser
	 * @return
	 */
	public int countBsUser(BsUser bsUser);
	public List<BsUserVO> bsUserPage(BsUser bsUser);
	
	/**
	 * 查询用户基础信息
	 * @return List<BsBaseUserVO>
	 */
	public List<BsBaseUserVO> findBaseUserInfo();
	
	/**
	 * 分页查询用户运营信息
	 * @param recode
	 * @return
	 */
	public List<MUserOperateVO> findUserOperatePage(MUserOperateVO recode);
	
	/**
	 * 查询用户运营信息的总数
	 * @param recode
	 * @return
	 */
	public int countUserOperate(MUserOperateVO recode);
	
	/**
	 * 查询用户总数
	 * @param map
	 * @return
	 */
	public int countBsUserByMap(Map<String,Object> map);
	
	/**
	 * 注册用户查询列表
	 * @param userName
	 * @param mobile
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public List<BsUserAssetVO> findRegisterUserList(String userName, String mobile, 
			String orderField, String orderDirection, Integer pageNum, Integer numPerPage);
	
	/**
	 * 注册用户统计
	 * @param userName
	 * @param mobile
	 * @return
	 */
	public int findRegisterUserCount(String userName, String mobile);
	
	/**
	 * 用户综合查询 数量统计
	 * @param record
	 * @return
	 */
	public Integer findUserComplexCount(BsUserAssetVO record);
	
	/**
	 * 用户综合查询
	 * @param record
	 * @return
	 */
	public List<BsUserAssetVO> findUserComplexList(BsUserAssetVO record);
	
	/**
	 * 用户注册查询 数量统计
	 * @param record
	 * @return
	 */
	public Integer findUserRecordCount(BsRecordListVo record);
	
	/**
	 * 用户综合查询回访记录查询
	 * @param record
	 * @return
	 */
	public List<BsRecordListVo> findUserRecordList(BsRecordListVo record);
	
	/**
	 * 用户综合查询回访记录新增
	 * @param record
	 * @return
	 */
	public void addUserRecordList(BsRecordListVo record);
	
	/**
	 * 用户标签管理统计
	 * @param bsUser
	 * @return
	 */
	public Integer findAllUserCountTag(BsUserAssetVO bsUser);
	
	/**
	 * 用户标签管理列表
	 * @param bsUser
	 * @return
	 */
	public List<BsUserAssetVO> findAllUserTag(BsUserAssetVO bsUser);
	
	/**
	 * user_id新增标签查询列表
	 * @param bsUser
	 * @return
	 */
	public Integer findUserIdTag(BsUserAssetVO bsUser);
	
	/**
	 * user_id新增标签查询统计
	 * @param bsUser
	 * @return
	 */
	public List<BsUserAssetVO> findUserIdTagList(BsUserAssetVO bsUser);

	/**
	 * 
	 * @Title: findUserByIds 
	 * @Description: 根据userId查询用户
	 * @param ids
	 * @return
	 * @throws
	 */
	public List<BsUser> findUserByIds(String[] ids);
	
	/**
	 * 
	 * @Title: selectAllUserForUserId 
	 * @Description: 用户标签查询--按查询条件查询用户，得到userId列表
	 * @param req
	 * @return
	 * @throws
	 */
	public List<BsUser> selectAllUserForUserId(ReqMsg_BsUser_BsUserTagListQuery req);
	
	/**
	 * 
	 * @Title: findUsersByIdsOrMobiles 
	 * @Description: 根据userId或者mobile查询用户
	 * @param map
	 * @return List<BsUser>
	 */
	public List<BsUser> findUsersByIdsOrMobiles(Map<String, Object> map);
	
	/**
	 * 用户复投查询 
	 * @param record
	 * @return
	 */
	public List<BsUserAssetVO> findUserComplexVoteList(BsUserAssetVO record);
	
	/**
	 * 用户复投统计
	 * @param record
	 * @return
	 */
	public Integer findUserComplexVoteCount(BsUserAssetVO record);

	/**
     * @param req
     * @return
     */
    public List<BsUser> selectUserList(BsUser req);

	/**
	 * 用户利息回款查询
	 * @param record
	 * @return
	 */
	public List<InterestRepaymentVO> queryUserInterestRepayment(InterestRepaymentVO record);

	/**
	 * 用户利息回款统计
	 * @param record
	 * @return
	 */
	public Integer queryUserInterestRepaymentCount(InterestRepaymentVO record);
	
	/**
	 * 判断是否存在用户-true，存在
	 * @param userId
	 * @return
	 */
	public Boolean isExistUser(Integer userId);
	
	/**
	 * 财务确认处理查看详情中查询用户基本信息
	 * 1、如果绑卡表中有绑卡成功status-1的记录，取这一条对应的银行卡信息
	 * 2、否则取绑卡表中离系统时间最近的一条记录对应的银行卡信息
	 * @param userId
	 * @return
	 */
	public BsUserAssetVO queryUserById(Integer userId);

	/**
	 * 计算每一个用户的出借受让金额
	 * @return
	 */
	List<Map<String, Object>> selectAmountTransGroup();

	/**
	 * 赞分期VIP用户信息-VIP理财人用户编号
	 * @return
     */
	List<BsUser> queryZanCompensateInfo();

	/**
	 * 查询赞分期代偿人信息
	 * @return
     */
	List<BsUser> queryZanCompensateUserInfo();
	/**
	 * 新增手动红包-查询用户运营信息
	 * @param req
	 * @return
	 */
	public ResMsg_MUserOperate_UserOperateQuery findUserOperatePageManualPacket(ReqMsg_MUserOperate_UserOperateQuery req);
	/**
	 * 新增手动红包-统计查询用户运营信息条数
	 * @param req
	 * @return
	 */
	public ResMsg_MUserOperate_UserOperateQuery countUserOperatePageManualPacket(ReqMsg_MUserOperate_UserOperateQuery req);

	/**
	 * @Title: findCountUserByIds
	 * @Description: 根据userIds查询用户数量
	 * @param userIds
	 * @return
	 */
	public int findCountUserByIds(List<Integer> userIds);

	/**
	 * @Title: findCountUserByIds
	 * @Description: 根据userIds查询批量用户信息
	 * @param userIds
	 * @return
	 */
	public List<BsUser> findUserByIds(List<Integer> userIds, Integer pageNum, Integer numPerPage);

	/**
	 * 查询一段时间范围内，过生日的用户数量
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	public int findCountBirthdayByDate(Date startTime, Date endTime);
	
	/*************************管理台申请更换安全卡*********************************/
	/**
	 * 管理台 申请更换安全卡计数
	 * @param record
	 * @return
	 */
	Integer countUserChangeBindCardInfo(BsChangeBindCardVO record);
	
	/**
	 * 管理台 申请更换安全卡列表
	 * @param record
	 * @return
	 */
	List<BsChangeBindCardVO> selectUserChangeBindCardInfo(BsChangeBindCardVO record);
	
	/**
	 * 管理台 审核更换安全卡
	 * @param userId
	 * @param id
	 * @return
	 */
	BsVerifyBindCardResVO verifyUserChangeBindCard(String muserId, Integer verifyId, String note, String checkStatus);
	
	/**
	 * 秦皇岛渠道用户计数
	 * @param userName
	 * @param mobile
	 * @param startRegisterTime
	 * @param endRegisterTime
	 * @param agentId
	 * @param regTerminal
	 * @param distributionChannel
	 * @return
	 */
	public Integer findQhdAgentUserCount(String userName, String mobile, Date startRegisterTime, Date endRegisterTime, 
			Integer regTerminal, String distributionChannel);
	
	/**
	 * 秦皇岛渠道用户列表
	 * @param userName
	 * @param mobile
	 * @param startRegisterTime
	 * @param endRegisterTime
	 * @param agentId
	 * @param regTerminal
	 * @param distributionChannel
	 * @return
	 */
	public List<BsQhdUserAgentVO> findQhdAgentUserList(String userName, String mobile, Date startRegisterTime, Date endRegisterTime, 
			Integer regTerminal, String distributionChannel, Integer pageNum, Integer numPerPage);
	
	/**
	 * 更新用户状态
	 * @param mUser
	 * @param userId
	 * @param status
	 * @return
	 */
	Boolean updateUserStatus(MUser mUser, Integer userId, Integer status, String ip);
	
}
