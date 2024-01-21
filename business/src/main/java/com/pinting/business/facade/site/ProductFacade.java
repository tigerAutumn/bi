package com.pinting.business.facade.site;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.dao.BsActivityMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsProductTagContentVO;
import com.pinting.business.model.vo.ProductDetailVO;
import com.pinting.business.service.manage.BsBannerConfigService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;

@Component("Product")
public class ProductFacade{
	
	@Autowired
	private ProductService productService;
	@Autowired
	private BsProductInformService bsProductInformService;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private BsActivityMapper bsActivityMapper;
	@Autowired
	private ActiveProductService activeProductService;
	@Autowired
	private BsBannerConfigService bsBannerConfigService;
	@Autowired
	private ProductTagService productTagService;
	
	
	public void infoQuery(ReqMsg_Product_InfoQuery req, ResMsg_Product_InfoQuery res) {
		
		BsProduct product = productService.findRegularById(req.getId());
		
		BsSysConfig  bsConfigPriceLimit = productService.findConfigValue("PRICE_LIMIT");
		BsSysConfig  bsConfigPriceCeiling = productService.findConfigValue("PRICE_CEILING");
		
		res.setBsConfigPriceLimit(bsConfigPriceLimit.getConfValue());
		res.setBsConfigPriceCeiling(bsConfigPriceCeiling.getConfValue());
		
		
		if(product == null){
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else{
			
			res.setId(product.getId());
			res.setInvestNum(product.getInvestNum());
			res.setRate(product.getBaseRate());
			res.setTrem(product.getTerm());
			res.setCurrTotalAmount(product.getCurrTotalAmount());
			res.setProductName(product.getName());
			res.setCode(product.getCode());
			res.setMinInvestAmount(product.getMinInvestAmount());
			res.setMaxTotalAmount(product.getMaxTotalAmount());
			res.setPropertyType(product.getPropertyType());
			res.setActivityType(product.getActivityType());
			res.setMaxSingleInvestAmount(product.getMaxSingleInvestAmount());
			res.setMaxInvestAmount(product.getMaxInvestAmount());
			BsPropertyInfo propertyInfo = productService.queryPropertyInfoById(product.getPropertyId());
			if (propertyInfo != null ) {
				res.setPropertySymbol(propertyInfo.getPropertySymbol());
			}
			res.setIsSupportRedPacket(product.getIsSupportRedPacket());
			res.setIsSupportInterestTicket(product.getIsSupportIncrInterest());
		}
		
	}

	@RedisCache(serviceName = "productBannerQueryCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.PRODUCTFACADE_BANNERQUERY)
	public ResMsg_Product_BannerQuery bannerQuery(ReqMsg_Product_BannerQuery req, ResMsg_Product_BannerQuery res) {
		BsBannerConfig bannerConfig = bsBannerConfigService.selectBannerConfigByType(req.getReturnType(), req.getChannel());
		if (bannerConfig != null) {
			res.setBannerUrl(bannerConfig.getUrl());
			res.setImgPath(bannerConfig.getImgPath());
			return res;
		} else {
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}
	}
	
	/**
	 * 理财计划列表（H5|PC）
	 * @param req
	 * @param res
	 */
	@RedisCache(serviceName = "productListQueryCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.PRODUCTFACADE_LISTQUERY)
	public ResMsg_Product_ListQuery listQuery(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res) {
	    return productService.listQuery(req, res);
    }
	
	public void qianBaoProductList(ReqMsg_Product_QianBaoProductList req, ResMsg_Product_QianBaoProductList res) {
	    productService.selectQianBaoProductList(req, res);
	}
	
	/**
     * 判断产品是否已经下线 { 
     *     true：已下线
     *     false：未下线
     * }
	 * @param req
	 * @param res
	 */
	public void checkProductIsOff(ReqMsg_Product_CheckProductIsOff req,
	                              ResMsg_Product_CheckProductIsOff res) {
	    res.setIsOff(productService.checkProductIsOff(req.getProductId()));
	}
	
	/**
	 * 判断产品是否是当前用户
	 * @param req
	 * @param res
	 */
	public void buyAgreement(ReqMsg_Product_BuyAgreement req, ResMsg_Product_BuyAgreement res) {
	    productService.checkProductIsSelf(req, res);
	}
	
	/**
	 * 根据产品ID查询用户类型产品权限
	 * @param req
	 * @param res
	 */
	public void findProductUserTypeAuthById(ReqMsg_Product_FindProductUserTypeAuthById req, ResMsg_Product_FindProductUserTypeAuthById res) {
		BsUserTypeAuth bsUserTypeAuth = productService.findUserTypeAuth(req.getId());
		if (bsUserTypeAuth != null ) {
			res.setId(bsUserTypeAuth.getId());
			res.setUserType(bsUserTypeAuth.getUserType());
			res.setProductId(bsUserTypeAuth.getProductId());
		}
	}
	
	/**
	 * 添加产品提醒消息
	 * @param req
	 * @param res
	 */
	public void addInform(ReqMsg_Product_AddInform req, ResMsg_Product_AddInform res){
		
		try {
			BsProduct product = productService.findRegularById(req.getProductId());
			if(product == null){
				throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
			}else{
				BsProductInform record = new BsProductInform();
				record.setUserId(req.getUserId());
				record.setProductId(req.getProductId());
				int count = bsProductInformService.countByUserIdProductId(record);
				if(count <= 0){
					bsProductInformService.addProductInform(record);
				}
				res.setTime(sysConfigService.findConfigByKey(Constants.PRODUCT_INFORM_MINUTE).getConfValue());
			}
		} catch (Exception e) {
			res.setResCode("999");
			e.printStackTrace();
		}
	}
	
	
	public void productDetailInfoQuery(ReqMsg_Product_ProductDetailInfoQuery req, ResMsg_Product_ProductDetailInfoQuery res) {
		
		ProductDetailVO product = productService.selectProductDetailById(req.getProductId(),req.getStart(),req.getNumPerPage());
		String informMinute = sysConfigService.findConfigByKey(Constants.PRODUCT_INFORM_MINUTE).getConfValue();
		String entrustLockPeriod = sysConfigService.findConfigByKey(Constants.DAY_4_WAIT_LOAN).getConfValue();
		ProductDetailVO productCache = productService.selectProductDetailList(req.getProductId(),req.getStart(),req.getNumPerPage());

		// 查询产品详情页，产品的标签属性查询
		if(null != product) {
			BsProductTagContentVO productTagContentVO = productTagService.queryProductTagContentById(product.getId());
			if(null != productTagContentVO) {
				res.setRemindTagContent(StringUtil.isNotBlank(productTagContentVO.getRemindTagContent()) ? productTagContentVO.getRemindTagContent() : null);
				res.setInterestRatesTagContent(StringUtil.isNotBlank(productTagContentVO.getInterestRatesTagContent()) ? productTagContentVO.getInterestRatesTagContent() : null);
			}
		}

		if(product == null){
			throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
		}else{
			res.setId(product.getId());
			res.setName(product.getName());
			res.setType(product.getType());
			res.setCode(product.getCode());
			res.setInterestType(product.getInterestType());
			res.setBaseRate(product.getBaseRate());
			res.setMaxRate(product.getMaxRate());
			res.setTerm(product.getTerm());
			res.setMaxTotalAmount(product.getMaxTotalAmount());
			res.setMinInvestAmount(product.getMinInvestAmount());
			res.setMaxSingleInvestAmount(product.getMaxSingleInvestAmount());
			res.setMaxInvestAmount(product.getMaxInvestAmount());
			res.setMaxInvestTimes(product.getMaxInvestTimes());
			res.setStartTime(product.getStartTime());
			res.setEndTime(product.getEndTime());
			res.setCurrTotalAmount(product.getCurrTotalAmount());
			res.setStatus(product.getStatus());
			res.setNote(product.getNote());
			res.setInvestNum(product.getInvestNum());
			res.setSysReturnRate(product.getSysReturnRate());
			res.setSerialId(product.getSerialId());
			res.setSerialOrder(product.getSerialOrder());
			res.setSerialName(product.getSerialName());
			res.setPropertyType(product.getPropertyType());
			res.setBeginInterestDays(product.getBeginInterestDays());
			res.setReturnType(product.getReturnType());
			res.setInterestDeal(product.getInterestDeal());
			res.setIsSupportTransfer(product.getIsSupportTransfer());
			res.setManageFee(product.getManageFee());
			res.setShowTerminal(product.getShowTerminal());
			res.setPropertyId(product.getPropertyId());
			res.setIsSuggest(product.getIsSuggest());
			res.setTerminator(product.getTerminator());
			res.setFinishTime(product.getFinishTime());
			res.setChecker(product.getChecker());
			res.setCheckTime(product.getCheckTime());
			res.setDistributor(product.getDistributor());
			res.setDistributeTime(product.getDistributeTime());
			res.setCreateTime(product.getCreateTime());
			res.setUpdateTime(product.getUpdateTime());
			res.setPropertyName(product.getPropertyName());
			res.setPropertySummary(product.getPropertySummary());
			res.setReturnSource(product.getReturnSource());
			res.setFundSecurity(product.getFundSecurity());
			res.setOrgnizeCheck(product.getOrgnizeCheck());
			res.setCoopProtocolPics(product.getCoopProtocolPics());
			res.setRatingGrade(product.getRatingGrade());
			res.setLoanProtocolPics(product.getLoanProtocolPics());
			res.setSurplusAmount(product.getSurplusAmount());
			res.setTotalRows(productCache.getTotalRows());
			res.setInformMinute(informMinute);
			res.setOrgnizeCheckPics(product.getOrgnizeCheckPics());
			res.setRatingGradePics(product.getRatingGradePics());
			res.setInvestRecordList(BeanUtils.classToArrayList(productCache.getInvestRecordVO()));
			res.setActivityType(product.getActivityType());
			res.setPropertySymbol(product.getPropertySymbol());
			res.setTermMonth(product.getTermMonth());
			res.setEntrustLockPeriod(entrustLockPeriod);
		}
	}
	

	public void productPlanListIndex(ReqMsg_Product_ProductPlanListIndex req, ResMsg_Product_ProductPlanListIndex res) {
	    List<BsProduct> bsProducts =  productService.productPlanListIndex(req.getUserType(), req.getTerminal());
	    res.setProductList(BeanUtils.classToArrayList(bsProducts));
	}
	
	public void active00Product(ReqMsg_Product_Active00Product req, ResMsg_Product_Active00Product res){
		BsActivity activity = bsActivityMapper.selectByPrimaryKey(4);
		Date now = new Date();
		if(activity == null || activity.getStartTime().compareTo(now) >0){
			res.setIsEndOrNoStart("noStart");
			return;
		}
		if(activity.getEndTime().compareTo(now) <0 ){
			res.setIsEndOrNoStart("isEnd");
			return;
		}
		
		List<BsProduct> bsProducts =  activeProductService.select00ActiveProdcutList(req.getShowTerminal());
		res.setProductList(BeanUtils.classToArrayList(bsProducts));
	}
	
	
	
	public void  queryYunDaiChangeNameDate(ReqMsg_Product_QueryYunDaiChangeNameDate req, ResMsg_Product_QueryYunDaiChangeNameDate res){
		Date date = productService.queryYunDaiChangeNameDate();
		res.setChangeNameDate(date);
	}
}
