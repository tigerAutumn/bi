package com.pinting.business.dao;

import com.pinting.business.model.BsUserTransDetail;
import com.pinting.business.model.BsUserTransDetailExample;
import com.pinting.business.model.vo.BsUserTransDetailVO;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface BsUserTransDetailMapper {
    int countByExample(BsUserTransDetailExample example);

    int deleteByExample(BsUserTransDetailExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserTransDetail record);

    int insertSelective(BsUserTransDetail record);

    List<BsUserTransDetail> selectByExample(BsUserTransDetailExample example);

    /**
	 * 交易明细记录
	 * @param data
	 * @return
     */
    List<BsUserTransDetail> selectByExamplePage(Map<String, Object> data);

	/**
	 * 交易明细记录数（排除分期产品的列表明细）
	 * @param data
	 * @return
     */
	Integer countByExamplePage(Map<String, Object> data);

	/**
	 * 赞分期回款明细
	 * @param data
	 * @return
     */
	List<BsUserTransDetail> selectReturnZanDetail(Map<String, Object> data);
    
    BsUserTransDetail selectByPrimaryKey(Integer id);
    
    /**
     * 根据userId、transType、transStatus查询
     * @param transDetail
     * @return
     */
    BsUserTransDetail selectByUserTrans(BsUserTransDetail transDetail);

    int updateByExampleSelective(@Param("record") BsUserTransDetail record, @Param("example") BsUserTransDetailExample example);

    int updateByExample(@Param("record") BsUserTransDetail record, @Param("example") BsUserTransDetailExample example);

    int updateByPrimaryKeySelective(BsUserTransDetail record);

    int updateByPrimaryKey(BsUserTransDetail record);

    /**
     * 根据用户id查询某天或某月的提现成功的次数 ，传入updateTime，userId
     * @param record
     * @return
     */
    Integer countByUserIdWithdrawSuc(BsUserTransDetail record);
    
    /**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @return 返回用户交易记录列表
	 */
    List<BsUserTransDetailVO> selectUserTransDetailList(BsUserTransDetailVO obj);
	
	/**
	 * 根据条件查询进行分页查询，条件可以为空
	 * @return 用户交易总记录数
	 */
	int countUserTransDetail(BsUserTransDetailVO obj);
	
	/**
	 * 根据条件查询用户处理中订单数(无提现交易明细)
	 * @return 用户处理中订单数
	 */
	int countProcessingNoWithdraw(BsUserTransDetail record);
	
	/**
	 * 根据条件查询用户处理中订单数(仅提现交易明细)
	 * @return 用户处理中订单数
	 */
	int countProcessingWithdraw(BsUserTransDetail record);
	
	
	/**
	 * 根据条件查询用户处理中交易明细数量(WITHDRAW、DEP_WITHDRAW、USER_BONUS_WITHDRAW、WITHDRAW_FEE)
	 * @return 用户处理中订单数
	 */
	int countProcessingAllWithdraw(BsUserTransDetail record);
	
	/**
	 * 根据userId和时间，查询某用户在某时间段内的非失败的提现交易总额
	 * @param userId
	 * @param startDay
	 * @param endDay
	 * @return
	 */
	Double sumUnFallWithdraw(@Param("userId")Integer userId,  @Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 统计提现次数（排除回款提现）
	 * @param userId
	 * @param startTime
	 * @param endTime
     * @return
     */
	int countWithdrawExcludeReturn(@Param("userId")Integer userId,  @Param("startTime") String startTime, @Param("endTime") String endTime);

	/**
	 * 交易明细记录（排除分期产品的列表明细）
	 * @param data
	 * @return
	 */
	List<BsUserTransDetail> selectByExamplePageNew(Map<String, Object> data);

	/**
	 * 分页查询钱报用户交易流水
	 * @param mobile
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param numPerPage
     * @return
     */
	List<BsUserTransDetailVO> select178UserTransDetails(@Param("mobile")String mobile,
							  							@Param("startTime") Date startTime,
							  							@Param("endTime") Date endTime,
														@Param("start")Integer start,
														@Param("numPerPage")Integer numPerPage);

	/**
	 * 统计钱报用户交易流水总条数
	 * @param mobile
	 * @param startTime
	 * @param endTime
     * @return
     */
	Integer count178UserTransDetails(@Param("mobile")String mobile,
									  @Param("startTime") Date startTime,
									  @Param("endTime") Date endTime
									  );
}