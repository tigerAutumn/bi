package com.pinting.business.service.site;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.model.vo.*;

import com.pinting.business.hessian.site.message.ReqMsg_Product_BuyAgreement;
import com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ReqMsg_Product_QianBaoProductList;
import com.pinting.business.hessian.site.message.ResMsg_Product_BuyAgreement;
import com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery;
import com.pinting.business.hessian.site.message.ResMsg_Product_QianBaoProductList;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.BsUserTypeAuth;


public interface ProductService {
	
	/**
	 * 将产品表以类型(定期，活期)分组查询列表，获取同一类型产品的投资人数，最高收益率，累计投资金额等
	 * @return 将分组完的产品列表返回, 没有找到，返回null
	 */
	public List<BsProductSummaryVO> findProductGroupByType();

	/**
	 * 根据产品类型查询产品列表(3, 6 12)
	 * @param productTypeReg 产品类型
	 * @return 将查询到的列表返回,没有找到，返回null
	 */
	public List<BsProduct> findProductListByType(String productTypeReg);

	
	/**
	 * 根据产品ID查询单一产品
	 * @param id 产品Id号
	 * @return 产品ID在数据库中也是唯一，所以最多只返回一条BsProduct记录。如果没找到，返回null
	 */
	public BsProduct findRegularById(int id);

	/**
	 * 根据产品的Id号进行更新
	 * @param productModify, 必须设置id属性
	 * @return 产品ID在数据库中也是唯一，最多只会更新一条数据。
	 */
	public boolean modifyProductById(BsProduct productModify);
	
	/**
	 * 对产品code进行分组查询，获取同一类型产品的投资人数，最高收益率，累计投资金额等
	 * @return 将分组完的产品列表返回, 没有找到，返回null
	 */
	public List<BsProductSummaryVO> findProductGroupByCode();

	/**
	 * 查询所有产品
	 * @return
	 */
	public List<BsProduct> findProductList();

	/**
	 * 
	 * @Title: selectProByUserType 
	 * @Description: 根据用户类型查询产品
	 * @param @param userType
	 * @param @return
	 * @return List<BsProduct>
	 * @throws
	 */
	public List<BsProduct> selectProByUserType(String userType);
	/**
	 * 根据键值查询购买金额限额
	 * @param string
	 * @return
	 */
	public BsSysConfig findConfigValue(String key);

    /**
     * 钱报的产品列表
     * @param req
     * @param res
     */
    public void selectQianBaoProductList(ReqMsg_Product_QianBaoProductList req,
                                         ResMsg_Product_QianBaoProductList res);
    
    
	/**
	 * 
	 * @Title: selectProductRateList 
	 * @Description: 查询数据库中利率列表
	 * @return List<BsProduct>
	 * @throws
	 */
	public List<BsProduct> selectProductRateList();
	
	/**
	 * 判断产品是否已经下线 { 
	 *     true：已下线
	 *     false：未下线
	 * }
	 * @param productId
	 * @return
	 */
	public boolean checkProductIsOff(Integer productId);
	
	/**
	 * 判断产品是否是当前用户
	 * @param req
	 * @param res
	 */
	public void checkProductIsSelf(ReqMsg_Product_BuyAgreement req, ResMsg_Product_BuyAgreement res);
	
	/**
	 * 根据产品ID查询用户类型产品权限
	 * @param id 产品Id号
	 * @return 产品ID在数据库中也是唯一，所以最多只返回一条BsUserTypeAuth记录。如果没找到，返回null
	 */
	public BsUserTypeAuth findUserTypeAuth(Integer id);
	
	/**
	 * 在app或者H5的首页显示推荐的产品（产品状态必须是进行中）
	 * @param userType
	 * @param terminal
	 * @param isSuggest
	 * @return
	 */
	public List<BsProduct> selectProByRecommend(String userType, String terminal, String isSuggest);
	
	/**
	 * 在app或者H5的首页显示最低和最高的年化收益率
	 * @param userType
	 * @param terminal
	 * @return
	 */
	public HashMap<String,Object> selectProductRateIndex(String userType, String terminal, String activityType);
	
	/**
	 * 查询审核通过后的产品列表
	 * 包括待发布、已发布、进行中
	 * @return
	 */
	public List<BsProduct> findAuthPassProducts();
	
	/**
	 * 根据时间读取将开发的投资产品
	 * @param minute
	 * @return
	 */
	List<BsProductUserVO> selectProductWithInformList();
	
	/**
	 * app和H5的理财产品列表
	 * @param userType
	 * @param terminal
	 * @return
	 */
	public List<BsProduct> selectAllProduct(String userType, String terminal, Integer page, String activityType);
	
	/**
	 * 根据产品ID查询产品详情信息
	 * @param productId 产品ID
	 * @return
	 */
	ProductDetailVO selectProductDetailById(Integer productId,Integer start,Integer numPerPage);
	
	/**
	 * 根据产品ID查询产品详情信息中的列表数据和列表
	 * @param productId 产品ID
	 * @return
	 */
	ProductDetailVO selectProductDetailList(Integer productId,Integer start,Integer numPerPage);
	
	/**
	 * app和H5的理财产品总数
	 * @param userType
	 * @param terminal
	 * @return
	 */
	public Integer selectAllProductCount(String userType, String terminal, String activityType);

    /**
     * H5和PC的理财产品
     * @param req
     * @param res
     */
    public ResMsg_Product_ListQuery listQuery(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res);
    
    
    /**
     * 首页标的展示-理财列表非推荐前三个展示
     * @param req
     * @param res
     */
    List<BsProduct>  productPlanListIndex(String userType,String terminal);

    /**
     * 新手推荐产品（只包含未开始和进行中的产品）
     * @return
     */
    public BsProductVO noviceRecommendedProduct(String terminal);
    
    /**
     * 新的在app或者H5的首页显示推荐的产品（产品状态必须是进行中）
     * @param userType
     * @param terminal
     * @param isSuggest
     * @return
     */
    public List<ProductDetailVO> selectNewBuyerProByRecommend(String userType, String terminal, String isSuggest);
    
    /**
     * 用户购买理财产品的次数
     * @param userId
     * @return
     */
    public Integer selectUserBuyProductCount(Integer userId);

    /**
     * 查询理财产品的个人单次最高购买限额
     * @param productId
     * @return
     */
    public Double selectUserMaxSingleAmount(Integer productId);
    
    /**
     * 新手引导页 新手专享计划,只显示新手标产品
     * @param terminal
     * @return
     */
    public BsProductVO noviceExclusivePlan(String terminal);
    /**
     * 根据资金接收方ID查询资金接收方标志
     * @return
     */
    BsPropertyInfo queryPropertyInfoById(Integer propertyId);
    
    /**
     * 根据产品ID查询资金接收方标志
     * @return
     */
    BsPropertyInfo queryPropertyInfoByProductId(Integer propertyId);
    
    /**
     * 限时活动产品
     * @return
     */
    public BsProductVO timeLimitActivityProduct(String terminal);
    
    /**
     * 新手推荐产品（包含未开始、进行中和已结束的产品）
     * @return
     */
    public BsProductVO noviceActivityProduct(String terminal);
    
	/**
	 * 理财产品列表
	 * @param returnType   还款方式
	 * @param terminal  展示端口
	 * @param page  页
	 * @param activityType  活动类型
	 * @return
	 */
	public List<BsProduct> selectProductList(String returnType, String terminal, String activityType,Integer start,Integer offset);
	
	/**
	 * 理财产品列表数量
	 * @param returnType   还款方式
	 * @param terminal  展示端口
	 * @param activityType  活动类型
	 * @return
	 */
	public Integer selectProductListCount(String returnType, String terminal,String activityType);
	
	
	/**
	 * 理财产品列表
	 * 根据回款类型查询近30天已完成理财产品列表
	 * @param returnType   还款方式
	 * @param terminal  展示端口
	 * @param page  页
	 * @param activityType  活动类型
	 * @return
	 */
	public List<BsProduct> selectProductListStatusFinish30(String returnType, String terminal, String activityType,Integer start,Integer offset);
	
	/**
	 * 理财产品列表数量
	 * 根据回款类型查询近30天已完成理财产品列表
	 * @param returnType   还款方式
	 * @param terminal  展示端口
	 * @param activityType  活动类型
	 * @return
	 */
	public Integer selectProductListStatusFinish30Count(String returnType, String terminal,String activityType);
	
	/**
	 * 查询累计投资额 不包含VIP产品
	 * @return
	 */
	public double queryAccumulatedInvestment();
	
	/**
	 * 根据产品名称查询产品列表
	 * @return
	 */
	public   List<BsProduct>  queryProductByName(String productName);
	/**
	 * 查询产品投资列表
	 * @return
	 */
	public   List<PlayerKillingVO>  queryUserInvestList(Integer productId);
	
	/**
	 * 查询达飞更名为时间
	 * @return
	 */
	public	Date   queryYunDaiChangeNameDate();
	
	/**
	 * 查询推荐的标的2个，顺序进行中，已发布未开始，已结束，活动第一天显示未开始的推荐标的
	 * @param terminal
	 * @param name
	 * @return
	 */
	public List<ProductDetailVO> suggestProductList(String terminal, String name);
	/**
	 * 产品开始更新状态方法
	 * 注：此方法用于钱报178更新产品状态切面，调用前请确认
	 * @param productModify BsProduct对象
	 */
	public boolean openProductById(BsProduct productModify);

	/**
	 * 查询首页展示产品信息
	 * @return
     */
	BsProductVO queryHomeProduct(Integer userId, String terminal);

	/**
	 * 查询首页展示活动标
	 * @param terminal
	 * @return
     */
	BsProductVO queryHomeActivityProduct(String terminal);
	/**
	 * 查询首页展示新手标
	 * @param terminal
	 * @return
	 */
	BsProductVO queryHomeNewBuyerProduct(String terminal);
	/**
	 * 查询首页展示普通标
	 * @param terminal
	 * @return
	 */
	BsProductVO queryHomeNormalProduct(String terminal);

	/**
	 * 查询首页展示产品信息
     */
	void queryHomeProductPC(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res);

	/**
	 * 统计所有产品的当前投资额和投资同次数
	 * @return
	 */
	BsProduct sumCurrTotalAmountAndInvestNum();

	BsProduct selectById(Integer productId);

}
