package com.pinting.business.dao;

import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductExample;
import com.pinting.business.model.vo.*;
import com.pinting.gateway.hessian.message.qb178.model.PositionProductInfo;

import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public interface BsProductMapper {
    int countByExample(BsProductExample example);

    int deleteByExample(BsProductExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsProduct record);

    int insertSelective(BsProduct record);

    List<BsProduct> selectByExample(BsProductExample example);

    BsProduct selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsProduct record, @Param("example") BsProductExample example);

    int updateByExample(@Param("record") BsProduct record, @Param("example") BsProductExample example);

    int updateByPrimaryKeySelective(BsProduct record);

    int updateByPrimaryKey(BsProduct record);
    
    ArrayList<BsProductSummaryVO> selectProductGroupByType();

	List<BsProductSummaryVO> selectProductGroupByCode();
	
	List<BsProduct> selectProByUserType(@Param("userType")String userType);
	
	BsProduct selectProByUser(@Param("id")Integer id,@Param("userId")Integer userId);

    /**
     * 钱报产品列表
     * @return
     */
    List<BsProduct> selectQianBaoProductList();

	List<BsProduct> selectProductRateList();
	
	/**
	 * 根据时间读取将开发的投资产品
	 * @param minute -分钟数，读取配置
	 * @return
	 */
	List<BsProductUserVO> selectProductWithInformList(@Param("minute")Integer minute);
	
	/**
	 * 根据多条件查询产品列表
	 * @param productVO
	 * @return 
	 * @since JDK 1.7
	 */
	 List<MProductVO> selectMProductVOsByPage(MProductVO productVO);
	
	/**
	 * 根据多条件查询产品总数
	 * @param productVO
	 * @return 
	 * @since JDK 1.7
	 */
	 int selectMProductVOsCount(MProductVO productVO);
	
	/**
	 * 根据主键查询产品
	 * @param id
	 * @return MProductVO
	 * @since JDK 1.7
	 */
	 MProductVO selectMProductVOById(Integer id);

    /**
     * 理财计划发布管理查询
     * @param req
     * @return
     */
    List<ProductReleaseInfoVO> selectReleaseProductGrid(@Param("record") ProductReleaseInfoVO req);
    
    /**
     * 理财计划发布管理查询
     * @param req
     * @return
     */
    Integer countReleaseProductGrid(@Param("record") ProductReleaseInfoVO req);
    
    /**
     * 在app或者H5的首页显示推荐的产品（产品状态必须是进行中）
     * @param userType
     * @param terminal
     * @param isSuggest
     * @return
     */
    List<BsProduct> selectProByRecommend(@Param("userType")String userType, @Param("terminal")String terminal, @Param("isSuggest")String isSuggest);
    
    /**
     * 在app或者H5的首页显示最低和最高的年化收益率
     * @param userType
     * @param terminal
     * @return
     */
    HashMap<String,Object> selectProductRateIndex(@Param("userType")String userType, @Param("terminal")String terminal, @Param("activityType")String activityType);
    
    /**
	 * 查询所有的期限
	 * @return List<BsProduct>
	 * @since JDK 1.7
	 */
	 List<BsProduct> selectAllProductTerm();
	
	/**
	 * 查询所有的利率
	 * @return List<BsProduct>
	 * @since JDK 1.7
	 */
	 List<BsProduct> selectAllProductBaseRate();
    
	 /**
	  * 根据产品ID查询理财计划详情-管理台详情预览
	  * @param id
	  * @return
	  */
     ProductDetailVO selectProductDetailById(@Param("id")Integer id);
     
     /**
      * app和H5的理财产品列表
      * @param userType
      * @param terminal
      * @return
      */
     List<BsProduct> selectAllProduct(@Param("userType")String userType, @Param("terminal")String terminal, @Param("start")Integer start, @Param("offset")Integer offset, @Param("activityType")String activityType);

	 /**
	  * 根据产品ID查询理财计划详情-H5，PC前端展示
	  * @param productId
	  * @return
	  */
     ProductDetailVO selectProductDetailByProductId(@Param("productId")Integer productId);

    /**
	 * 根据产品ID查询理财计划详情投资记录-H5，PC前端展示
	 * @param productId
	 * @param start
	 * @param numPerPage
	 * @return
     */
     List<InvestRecordVO> selectInvestRecordByProductId(@Param("productId")Integer productId,
    		 											@Param("start")Integer start,
    		 											@Param("numPerPage")Integer numPerPage);

    /**
	 * 根据产品ID查询理财计划详情投资记录总条数-H5，PC前端展示
	 * @param productId
	 * @return
     */
     Integer selectInvestRecordCountByProductId(@Param("productId")Integer productId);
     
     
     /**
      * app和H5的理财产品总数
      * @param userType
      * @param terminal
      * @return
      */
     Integer selectAllProductCount(@Param("userType")String userType, @Param("terminal")String terminal, @Param("activityType")String activityType);
     
     /**
      * 根据平台类型查询产品平均年化收益
      * @param terminal 平台类型
      * @return
      */
     Double selectAverageInvestRate(@Param("terminal")String terminal);

	/**
	 * H5|PC|H5_178|PC_178 理财计划列表
	 * @param userType
	 * @param terminal
	 * @param start
	 * @param numPerPage
	 * @param status
	 * @param term
	 * @param isSuggest
	 * @param activityType
     * @param returnType
     * @return
     */
    List<ProductDetailVO> selectProductGrid(@Param("userType") String userType, @Param("terminal") String terminal, @Param("start") Integer start,
                                      @Param("numPerPage") Integer numPerPage, @Param("status") Integer status, @Param("term") String term, 
                                      @Param("isSuggest") String isSuggest, @Param("activityType") String activityType,@Param("returnType") String returnType);

	/**
	 *  H5|PC|H5_178|PC_178 理财计划总数
	 * @param userType
	 * @param terminal
	 * @param status
	 * @param term
	 * @param isSuggest
	 * @param activityType
	 * @param returnType
     * @return
     */
    Integer countProductGrid(@Param("userType") String userType, 
                             @Param("terminal") String terminal, 
                             @Param("status") Integer status, 
                             @Param("term") String term,
                             @Param("isSuggest") String isSuggest,
                             @Param("activityType") String activityType,
                             @Param("returnType") String returnType);
    
    /**
	 * 根据主键查询产品
	 * @param id
	 * @return MProductVO
	 * @since JDK 1.7
	 */
	 MProductVO selectMProductVoById(Integer id);
	 /**
	  * 查询首页标的
	  * @param userType
	  * @param terminal
	  * @return
	  */
	List<BsProduct> productPlanListIndex(@Param("userType") String userType, 
            							 @Param("terminal") String terminal);

    /**
     * 新手推荐产品
     * 可投资的计划中，期限最短（先判断），利率最高的，开始时间最早的
     * @return
     */
    BsProduct selectNoviceRecommendedProduct(@Param("showTerminal") String showTerminal);

    /**
     * 新手推荐产品
     * 如果没有可投资计划，则显示1个月产品中结束时间最晚的
     * @return
     */
    BsProduct selectOneMonthEndTimeLastestProduct(@Param("showTerminal") String showTerminal);

    /**
     * 新手推荐产品（新手标）
     * 新手推荐产品 未完成的新手标，推荐，进行中在前，update倒序，只查询一条数据
     * @param terminal  
     * @return
     */
    BsProduct selectNewUserProduct(@Param("showTerminal") String terminal);

    /**
     * 
     * @param userType
     * @param terminal
     * @param isSuggest
     * @return
     */
    List<ProductDetailVO> selectNewBuyerProByRecommend(@Param("userType")String userType, @Param("terminal")String terminal, @Param("isSuggest")String isSuggest);
    
    /**
     * 
     * 查询已完成的新手产品 
     * @param terminal  
     * @return
     */
    BsProduct selectOverNewUserProduct(@Param("showTerminal") String terminal);

    /**
     * 查询欧洲杯活动产品，只显示当天开始的产品
     * @param showTerminal  展示端口
     * @param type  类型（NORMAL：欧洲杯产品；NEW_USER：新用户产品）
     * @param flag   标识，当天是否存在活动产品，为空，则是当天产品
     * @return
     */
    List<BsProduct> selectEcupProductGrid(@Param("showTerminal") String showTerminal, @Param("type") String type, @Param("flag") String flag);

    /**
     * 
     * @param productId
     * @return
     */
	BsProduct selectByIdLock(@Param("productId") Integer productId);
	/**
	 * 根据展示端口和产品活动类型查询产品
	 * @param terminal 展示端口
	 * @param activityType 活动类型
	 * @return
	 */
	BsProduct selectActivityProduct(@Param("terminal") String terminal,@Param("activityType") String activityType);
	
	
	/**
	 * 根据展示端口和产品活动类型查询产品
	 * @param terminal 展示端口
	 * @param activityType 活动类型
	 * @return
	 */
	BsProduct selectActivityAndNoviceProduct(@Param("terminal") String terminal,@Param("activityType") String activityType);
	
	/**
	 * 查询产品列表
	 * @param returnType
	 * @param terminal
	 * @param activityType
	 * @param start
	 * @param offset
	 * @return
	 */
	List<BsProduct> selectProductList(@Param("returnType")String returnType, @Param("terminal")String terminal,@Param("activityType")String activityType,@Param("start") Integer start,@Param("offset") Integer offset);
	/**
	 * 查询产品列表数量
	 * @param returnType
	 * @param terminal
	 * @param activityType
	 * @return
	 */
	Integer selectProductListCount(@Param("returnType")String returnType, @Param("terminal")String terminal,@Param("activityType")String activityType);
	/**
	 * 查询近30天已完成的产品列表
	 * @param returnType
	 * @param terminal
	 * @param activityType
	 * @param start
	 * @param offset
	 * @return
	 */
	List<BsProduct> selectProductListStatusFinish30(@Param("returnType")String returnType, @Param("terminal")String terminal,@Param("activityType")String activityType,@Param("start") Integer start,@Param("offset") Integer offset);
	/**
	 * 查询近30天已完成的产品列表数量
	 * @param returnType
	 * @param terminal
	 * @param activityType
	 * @return
	 */
	Integer selectProductListStatusFinish30Count(@Param("returnType")String returnType, @Param("terminal")String terminal,@Param("activityType")String activityType);
	
	/**
	 * 查询累计投资额（平台自成立以来，用户累计成交的投资金额之和）
	 * @return
	 */
	Double selectAccumulatedInvestment();

	
	/**
	 * 双旦活动显示标的------>显示正在进行中的标的（利息最大）或者活动期内已结束(结束时间最晚)的标的（普通标）
	 * @param terminal
	 * @return
	 */
	List<BsProduct> select00ActiveProduct(@Param("terminal")String terminal);
	
	/**
	 * 查询推荐的标的2个，顺序进行中，已发布未开始，已结束，活动第一天显示未开始的推荐标的
	 * @param terminal 显示端
	 * @param name 产品名称关键词
	 * @return
	 */
	List<ProductDetailVO> selectSuggestProductList(@Param("terminal") String terminal,@Param("name") String name);

	/**
	 * 钱报app分页查询产品详情列表
	 * @param productId
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<ProductDetailVO> select178ProductByPage(@Param("productId") Integer productId,
												@Param("status") Integer status,
												@Param("startTime") Date startTime,
												@Param("endTime") Date endTime,
												@Param("start")Integer start,
												@Param("numPerPage")Integer numPerPage);

	/**
	 * 钱报app根据产品id和钱报渠道查询钱报用户购买的产品相关信息
	 * @param productId
	 * @param agentId
	 * @param createTimeBegin
	 * @param createTimeEnd
	 * @return
	 */
	PositionProductInfo selectProductInfoById(@Param("productId") Integer productId,@Param("agentId") Integer agentId,
			@Param("createTimeBegin") Date createTimeBegin,@Param("createTimeEnd") Date createTimeEnd);
	/**
	 * 钱报app查询产品详情列表总条数
	 * @param productId
	 * @param status
	 * @param startTime
	 * @param endTime
     * @return
     */
	Integer count178Products(@Param("productId") Integer productId,
							 @Param("status") Integer status,
							 @Param("startTime") Date startTime,
							 @Param("endTime") Date endTime);

	BsProduct selectHomeProduct(@Param("terminal") String terminal, @Param("activityType") String activityType);

	BsProduct selectHomeProduct2(@Param("terminal") String terminal, @Param("activityType") String activityType);

	/**
	 * 统计所有产品的当前投资额和投资同次数
	 * @return
     */
	BsProduct sumCurrTotalAmountAndInvestNum();

	BsProduct selectByPrimaryKey4Lock(Integer productId);


	/**
	 * 更新产品累计投资额和投资人数
	 * @return
	 */
	int updateCurrTotalAmountAndInvestNumIncrement(@Param("currTotalAmount") Double currTotalAmount, @Param("investNum") Integer investNum, @Param("id") Integer id);

}