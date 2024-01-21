package com.pinting.business.service.site;

import java.util.ArrayList;
import java.util.List;

import com.pinting.business.hessian.site.message.ReqMsg_Invest_PlatformStatistics;
import com.pinting.business.hessian.site.message.ResMsg_Invest_PlatformStatistics;
import com.pinting.business.model.BsSubAccount;
import com.pinting.business.model.vo.BsStatisticsVO;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.gateway.in.util.MethodRole;

public interface SubAccountService {
	/**
	 * 新增子账户
	 * @param subAccount
	 * @return 如果新增成功，则返回true，否则返回false
	 */
	public boolean addSubAccount(BsSubAccount subAccount);
	/**
	 * 新增子账户
	 * @param subAccount
	 * @return 如果新增成功，则返回新增子账户的id，否则返回0
	 */
	public int addSubAccountReturnId(BsSubAccount subAccount);
	/**
	 * 根据用户查询该用户结算户
	 * @param userId 用户编号唯一确定结算户
	 * @return 该用户结算户
	 */
	public BsSubAccount findJSHSubAccountByUserId(Integer userId);

	/**
	 * 根据用户查询该用户存管体系结算户
	 * @param userId 用户编号唯一确定结算户
	 * @return 该用户存管体系结算户
	 */
	public BsSubAccount findDEPJSHSubAccountByUserId(Integer userId);
	
	
	/**
	 * 根据用户查询该用户奖励金户
	 * @param userId 用户编号唯一确定结算户
	 * @return 该用户结算户
	 */
	@MethodRole("APP")
	public BsSubAccount findJLJSubAccountByUserId(Integer userId);

	/**
	 * 根据子产品id号更新
	 * @param subAccount
	 * @return 当数据当中有记录被更新之后返回true，否则返回false
	 */
	public boolean modifySubAccountById(BsSubAccount subAccount);
	
	/**
	 * 根据userId查询我的投资记录（此方法只适用于查询定期产品）
	 * @param type 产品类型
	 * @param userId 用户编号
	 * @param status 子账户状态  不为空时查询该状态及已回款的
	 * @return 如果查询成功返回列表，否则返回null
	 */
	public List<BsSubAccountVO> findMyInvestByUserIdAndType(String type,int userId,int start,int pageSize,int status);

	/**
	 * 根据userId查询我的投资记录的条数
	 * @param userId
	 * @return 返回我投资的记录的条数
	 */
	public Integer findMyInvestCountByUserId(Integer userId);

	/**
	 * 根据用户id,和子产品id号查询我的单条投资记录
	 * @param userId 用户编号
	 * @param id  资产品编号
	 * @return 返回当条投资记录，否则返回null
	 * @author HuangMengJian
	 */
	public BsSubAccountVO findMyInvestByUserIdAndId(Integer userId, Integer id);

	/**
	 * 根据Id号查询子账户表记录
	 * @param subAccountId
	 * @return
	 */
	public BsSubAccount findSubAccountById(Integer subAccountId);
	
	/**
	 * 增量修改结算户余额：
	 * 如参数bsSubAccount传入balance=100，实际修改情况是balance=原balance+100
	 * @param bsSubAccount
	 * @return 修改成功返回true，否则返回false
	 */
	public boolean modifyBalancesByIncrement(BsSubAccount bsSubAccount);
	
	/**
	 * 根据产品码查询购买人数
	 * @param code 产品代码
	 * @return 返回当前产品购买的人数
	 * @author HuangMengJian
	 */
	public int countProductNumByProductCode(String code);
	
	/**
	 * 根据产品码查询当日购买人数
	 * @param code 产品代码
	 * @return 返回当前产品购买的人数
	 * @author HuangMengJian
	 */
	public int countProductNumDayByProductCode(String code);
	
	
	/**
	 * 查询当前时间购买总金额（投资中 + 转让中 + 已结算 ）
	 * @param code
	 * @return 该产品的购买总金额
	 * @author HuangMengJian
	 */
	public double countProductAmountByProductCode(String code);
	
	
	/**
	 * 查询当日购买的总金额    sum(投资中) - sum(已转让)
	 * @param code
	 * @return 当日购买的总金额
	 * @author HuangMengJian
	 */
	public double countProductDayByProductCode(String code);
	
	/**
	 * 查询所有购买的次数   count(投资中 + 已结算)
	 * return 返回迄今为止所有购买产品的次数
	 * @author HuangMengJian
	 */
	public int countProductBuyNum(String code);
	
	/**
	 * 计算购买在区间 [balance1,balance2) 的次数
	 * @param balance1
	 * @param balance2
	 * @return 返回购买次数
	 */
	public int countSubAccountBalanceBetweenNaNAndNaN(double balance1, double balance2);
	
	/**
	 * 某个时间段内投资本金
	 * @param start 开始时间
	 * @param end  结束时间
	 * @return
	 */
	public Double sumPeriodCapital();
	
	/**
	 * 计算投资金额和投资天数
	 * @param productCode3
	 * @return
	 */
	public ArrayList<BsSubAccountVO> selectCaptialAndInvestDayByProductCode(
			String productCode3);
	
	/**
	 * 查询购买信息
	 * 
	 */
	public List<BsStatisticsVO> findUserBuyOrdersList(Integer productId,Integer start, Integer end);
	
	/**
	 * 根据产品id号查询购买的次数
	 * @param id
	 * @return
	 */
	public int countProductBuyNumByProductId(Integer id,Integer userId);
	
	/**
	 * 根据产品id号查询当前还能购买的产品金额
	 * @param id
	 * @param userId
	 * @return
	 */
	public double countProductAmountByProductId(Integer id, Integer userId);
	
	/**
	 * 计算当月营销费用
	 * @return
	 */
	public double getMarkingCosts();
	
	/**
	 * 计算当天刚产生利息的总金额
	 * 
	 */
	public double selectTodayMarketingCosts();
	/**
	 * 查询平台数据——老
	 * @param req
	 * @param res
	 */
	public void platformStatistics(ReqMsg_Invest_PlatformStatistics req,ResMsg_Invest_PlatformStatistics res);
	
	/**
	 * 我的投资列表
	 * @param userId
	 * @param start
	 * @param pageSize
	 * @return
	 */
	public List<BsSubAccountVO> selectMyInvestment(Integer userId,Integer start,Integer pageSize, Integer stauts);
	
	
	/**
	 * 查询平台数据——新
	 * @param req
	 * @param res
	 */
	public void platformStatisticsNews(ReqMsg_Invest_PlatformStatistics req,ResMsg_Invest_PlatformStatistics res);

	/**
	 * 平台数据2018年3月12日以及之后的版本
	 */
	void platformData(ReqMsg_Invest_PlatformStatistics req, ResMsg_Invest_PlatformStatistics res);
	
	/**
	 * 根基子账户ID查询对应的用户ID
	 */
	Integer selectUserIdBySubAccountId(Integer subAccountId);

	/**
	 * 根据userId查询港湾计划投资条数
	 * @param userId
	 * @return 返回我投资的记录的条数
	 */
	int countMyInvestBGWByUserId(Integer userId);
	
	/**
	 * 根据AUTH子账户ID查询对应的REG-D子账户的信息
	 * @param subAccountId 子账户ID
	 * @return
	 */
	public BsSubAccount findRegDSubAccountByAuthId(Integer subAccountId);
	
    
    /**
     * 根据subAccountId查询是否是完成所有出借，完成若是非VIP发微信通知。
     * @param subAccountId_auth
     */
    void sendWechat4LastLoan(Integer subAccountId_auth);
    /**
     * 根据状态统计我的投资数量--港湾计划
     * @param userId  用户ID
     * @param investStatus  投资状态
     */
	public Integer countMyInvestByStatus(Integer userId, String investStatus);
	
	
	/**
	 * 根据userId查询我的投资记录
	 * @param type 产品类型
	 * @param userId 用户编号
	 * @param status 子账户状态  不为空时查询该状态及已回款的
	 * @return 如果查询成功返回列表，否则返回null
	 */
	public List<BsSubAccountVO> bgwMyInvestByUserId(String type,int userId,int pageNum,int pageSize,String status);
	
    /**
     * 根据状态统计我的投资数量--赞分期
     * @param userId  用户ID
     * @param investStatus  投资状态
     */
	public Integer countMyInvestZanByStatus(Integer userId, String investStatus);

	/**
	 * 根据用户查询该用户站岗户-分期产品
	 * @param userId 用户编号唯一确定站岗户
	 * @return 该用户站岗户
	 */
	List<BsSubAccount> findAuthAccountByUserId(Integer userId);

	/**
	 * 根据id查询该用户子账户数据-存管产品
	 * @param authAccountId
	 * @return
     */
	public BsSubAccount queryAuthAccountById(Integer authAccountId);

	/**
	 * 根据子账户编号，查询产品购买成功后的加息收益金额
	 * @param subAccountId
	 * @return
	 */
	public Double queryInterestAmountBySubAccountId(int subAccountId);









	/* 2018平台数据添加字段 start */
	/* 1. 累计出借额（平台自成立以来出借的金额总和，仅统计本金） */
	Double sumLoanAmount();

	/* 2. 自成立以来累计借贷金额（平台成立以来出借的金额总和，仅统计本金） */
	Double sumBorrowerAmount();

	/* 3. 自成立以来累计借贷笔数（平台成立以来出借的总笔数） */
	Integer countLoanTimes();

	/* 4. 当前待还借贷金额（借款人未还款的出借金额总和，仅统计本金） */
	Double sumLeftAmount();

	/* 5. 当前待还借贷笔数（借款人未还款的出借总笔数） */
	Integer countLeftAmountTimes();

	/* 6. 累计出借人数（平台成立以来，累计的出借总人数，排除VIP理财人） */
	Integer countLoanUserTimes();

	/* 7. 当期出借人数（目前有待回款的出借人数，排除VIP理财人） */
	Integer countCurrentLoanUserTimes();

	/* 8.  前十大出借人出借余额占比（按待回款金额排序，前十大出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人） */
	Double sumTenLargestLeftAmount();

	/* 9. 最大单一出借人出借余额占比（按待回款金额排序，最大单一出借人出借余额总和/当前待还借贷金额，仅统计本金，排除VIP理财人） */
	Double sumLargestLeftAmount();

	/* 10. 累计借款人数（平台成立以来，累计的借款人数） */
	Integer countBorrowerUserTimes();

	/* 11. 当期借款人数（目前处于借款状态的借款人数） */
	Integer countCurrentBorrowerUserTimes();

	/* 12. 前十大借款人待还金额占比（按当前借款金额排序，前十大借款人待还金额总和/当前待还借贷金额，仅统计本金） */
	Double sumTenBorrowerLargestLeftAmount();

	/* 13. 最大单一借款人待还金额占比（按当前借款金额排序，最大单一借款人待还金额总和/当前待还借贷金额，仅统计本金） */
	Double sumBorrowerLargestLeftAmount();

	/* 14. 借款人逾期金额（当前对投资人已经处于逾期状态的所有借款的金额总和，仅统计本金） */
	Double sumLateAmount();

	/* 15. 借款人逾期笔数（当前对投资人处于逾期状态的所有借款的笔数之和） */
	Integer countLateAmount();

	/* 16. 借款人逾期90天以上金额（当前对投资人逾期＞90天的借款金额总和，仅统计本金） */
	Double sum90LateAmount();

	/* 17. 借款人逾期90天以上笔数（当前对投资人逾期＞90天的借款笔数之和） */
	Integer count90LateAmount();

	/* 18. 累计代偿金额（平台自成立以来，累计的代偿金额，包括本金和利息） */
	Double sumLateNotAmount();

	/* 19. 累计代偿笔数（平台自成立以来，累计代偿的笔数之和） */
	Integer countLateNotAmount();
	/* 2018平台数据添加字段 end */
}
