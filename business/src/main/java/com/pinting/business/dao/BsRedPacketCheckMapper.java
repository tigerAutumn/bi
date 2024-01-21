package com.pinting.business.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.business.model.BsRedPacketCheckExample;
import com.pinting.business.model.vo.AutoRedPacketTotalAmountVO;
import com.pinting.business.model.vo.BsRedPacketCheckVO;
import com.pinting.business.model.vo.RedPacketStatisticsVO;

public interface BsRedPacketCheckMapper {
    int countByExample(BsRedPacketCheckExample example);

    int deleteByExample(BsRedPacketCheckExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsRedPacketCheck record);

    int insertSelective(BsRedPacketCheck record);

    List<BsRedPacketCheck> selectByExample(BsRedPacketCheckExample example);

    BsRedPacketCheck selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsRedPacketCheck record, @Param("example") BsRedPacketCheckExample example);

    int updateByExample(@Param("record") BsRedPacketCheck record, @Param("example") BsRedPacketCheckExample example);

    int updateByPrimaryKeySelective(BsRedPacketCheck record);

    int updateByPrimaryKey(BsRedPacketCheck record);
    
    /**
     *  查询红包发放管理列表数据
	 * @param serialName  名称
	 * @param distributeType   发放类型（手动、 自动-新用户、自动-投资满额、自动-邀请满额）
	 * @param checkStatus  审核状态
     * @param start
     * @param numPerPage
     * @return
     */
    
    /*	    private String serialNo;
    
    private String policyType;
    
    private String termLimit;
    
    private String applicantTimeStart;
    private String applicantTimeEnd;
    
    private String amountMin;
    private String amountMax;*/
    List<BsRedPacketCheckVO> selectGrantManagermentGrid(@Param("serialName") String serialName,
											    		@Param("distributeType") String distributeType,
											    		@Param("checkStatus") String checkStatus,
											    		@Param("status") String status,
											    		@Param("serialNo") String serialNo,
											    		@Param("policyType") String policyType,
											    		@Param("termLimit") String termLimit,
											    		@Param("applicantTimeStart") String applicantTimeStart,
											    		@Param("applicantTimeEnd") String applicantTimeEnd,
											    		@Param("amountMin") String amountMin,
											    		@Param("amountMax") String amountMax,
														@Param("start") Integer start,
											            @Param("numPerPage") Integer numPerPage);
	/**
	 * 查询红包发放管理列表数量
	 * @param serialName  名称
	 * @param distributeType   发放类型（手动、 自动-新用户、自动-投资满额、自动-邀请满额）
	 * @param checkStatus  审核状态
	 * @param start
	 * @param numPerPage
	 * @return
	 */
    Integer selectGrantManagermentCount(@Param("serialName") String serialName,
							    		@Param("distributeType") String distributeType,
							    		@Param("checkStatus") String checkStatus,
							    		@Param("status") String status,											    		
							    		@Param("serialNo") String serialNo,
							    		@Param("policyType") String policyType,
							    		@Param("termLimit") String termLimit,
							    		@Param("applicantTimeStart") String applicantTimeStart,
							    		@Param("applicantTimeEnd") String applicantTimeEnd,
							    		@Param("amountMin") String amountMin,
							    		@Param("amountMax") String amountMax);
    
    /**
     * 查询人工发放红包基础信息
     * @return
     */
    BsRedPacketCheckVO  selectManualRedPocketReview(@Param("id") Integer id);
    
    /**
     *  查询红包审核管理列表数据
	 * @param serialName  名称
	 * @param distributeType   发放类型（手动、 自动-新用户、自动-投资满额、自动-邀请满额）
	 * @param checkStatus  审核状态
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsRedPacketCheckVO> selectRedPacketCheckGrid(@Param("serialName") String serialName,
											    		@Param("distributeType") String distributeType,
											    		@Param("checkStatus") String checkStatus,
											    		@Param("status") String status,
											    		@Param("serialNo") String serialNo,
											    		@Param("policyType") String policyType,
											    		@Param("termLimit") String termLimit,
											    		@Param("applicantTimeStart") String applicantTimeStart,
											    		@Param("applicantTimeEnd") String applicantTimeEnd,
											    		@Param("amountMin") String amountMin,
											    		@Param("amountMax") String amountMax,
														@Param("start") Integer start,
											            @Param("numPerPage") Integer numPerPage);
	/**
	 * 查询红包审核管理列表数量
	 * @param serialName  名称
	 * @param distributeType   发放类型（手动、 自动-新用户、自动-投资满额、自动-邀请满额）
	 * @param checkStatus  审核状态
	 * @param start
	 * @param numPerPage
	 * @return
	 */
    Integer selectRedPacketCheckCount(@Param("serialName") String serialName,
							    		@Param("distributeType") String distributeType,
							    		@Param("checkStatus") String checkStatus,
							    		@Param("status") String status,
							    		@Param("serialNo") String serialNo,
							    		@Param("policyType") String policyType,
							    		@Param("termLimit") String termLimit,
							    		@Param("applicantTimeStart") String applicantTimeStart,
							    		@Param("applicantTimeEnd") String applicantTimeEnd,
							    		@Param("amountMin") String amountMin,
							    		@Param("amountMax") String amountMax);
    
    /**
     * 进行红包发放审核通过操作-将红包相关信息插入红包信息表
     * @param id
     * @return
     */
    int insertRedPocketInfo(@Param("id") Integer id);
    
    /**
     * 查询手动红包发送信息时用户列表
     * @param serialNo
     * @return
     */
    List<Integer>  queryManualRedPcketUserIdList(@Param("serialNo") String serialNo);
    
    /**
     * 查询手动红包发送信息时手机号码列表
     * @param serialNo
     * @return
     */
    List<String>  queryManualRedPcketUserMobileList(@Param("serialNo") String serialNo);
    
	/**
	 * 查询红包统计数据
	 * @param serialName 红包名称
	 * @param applyNo   申请ID
	 * @param applicant  申请人
	 * @return
	 */
    List<RedPacketStatisticsVO> selectStatisticsData(@Param("serialName") String serialName, 
                                               		 @Param("applyNo") String applyNo,
                                               		 @Param("applicant") String applicant,
                                               		 @Param("start") Integer start,
                                               		 @Param("numPerPage") Integer numPerPage);
    
	/**
	 * 查询红包统计数据数量
	 * @param serialName 红包名称
	 * @param applyNo   申请ID
	 * @param applicant  申请人
	 * @return
	 */
    Integer selectStatisticsCount(@Param("serialName") String serialName, 
                                               		 @Param("applyNo") String applyNo,
                                               		 @Param("applicant") String applicant);
    
    
    /**
     * 根据策略可模糊查询列表
     * @param policyType
     * @return
     */
    List<BsRedPacketCheck> selectByPolicyType(@Param("policyType") String policyType);

    /**
     * 
     * @param triggerType
     * @return
     */
    List<AutoRedPacketTotalAmountVO> autoRedPacketTotalGrid(@Param("triggerType") String triggerType, @Param("showTerminal") String showTerminal);
    
    /**
     * 
     * @param term
     * @return
     */
    List<BsRedPacketCheckVO> selectEffectiveActivityRedPacketCheckAndRule(@Param("term") String term);

    /**
     * 根据serialNo查询投资交易金额
     * @param serialNo
     * @return
     */
	Double queryBuyAmount(@Param("serialNo") String serialNo);
    /**
     * 根据serialNo查询投资年化交易金额
     * @param serialNo
     * @return
     */
	Double queryAmountYear(@Param("serialNo") String serialNo);
	
	/**
	 * 查询红包发放策略对应计数
	 * @param policyType
	 * @return
	 */
	Integer selectRedPacketPolicyCount(@Param("policyType") String policyType); 
	
	
}