package com.pinting.gateway.in.facade.mobile;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.ProductTagService;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.dao.BsPayOrdersMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsPayOrdersExample;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductInform;
import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.service.site.BsProductInformService;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.InterfaceVersion;

@Component("appProduct")
public class ProductFacade{
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private BsProductInformService informService;
	
	@Autowired
	private	BsRedPacketInfoMapper bsRedPacketInfoMapper ;
	@Autowired
    private BsPayOrdersMapper payOrderMapper;
	@Autowired
	private ProductTagService productTagService;

	@InterfaceVersion("InfoQuery/1.0.0")
	public void infoQuery(ReqMsg_Product_InfoQuery req, ResMsg_Product_InfoQuery res) {
		
		BsProduct product = productService.findRegularById(req.getId());
		
//		BsSysConfig  bsConfigPriceLimit = productService.findConfigValue("PRICE_LIMIT");
//		BsSysConfig  bsConfigPriceCeiling = productService.findConfigValue("PRICE_CEILING");
		
//		res.setBsConfigPriceLimit(bsConfigPriceLimit.getConfValue());
//		res.setBsConfigPriceCeiling(bsConfigPriceCeiling.getConfValue());
		
		
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
		}
		
	}
	
	@InterfaceVersion("ListQuery/1.0.0")
	public void listQuery(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		List<BsProduct> temp = productService.selectProByUserType(req.getUserType());
		if(temp != null && temp.size() > 0) {
			for(BsProduct pro : temp) {
				BsProductVO vo = new BsProductVO();
				vo.setId(pro.getId());
				vo.setTerm(String.valueOf(pro.getTerm4Day()));
				vo.setName(pro.getName());
				vo.setRate(new DecimalFormat("0.0").format(pro.getBaseRate()));
				vo.setMinInvestAmount(pro.getMinInvestAmount());
				result.add(BeanUtils.transBeanMap(vo));
			}
		}
		res.setProductLst(result);
	}
	
	@InterfaceVersion("ListQuery/1.0.1")
	public void listQuery_1(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BsProduct> temp = productService.selectAllProduct(req.getUserType(),req.getProductShowTerminal(),req.getPage(),"NORMAL");
		Integer count = productService.selectAllProductCount(req.getUserType(),req.getProductShowTerminal(),"NORMAL");
		if(temp != null && temp.size() > 0) {
			for(BsProduct pro : temp) {
				BsProductVO vo = new BsProductVO();
				vo.setId(pro.getId());
				vo.setTerm(String.valueOf(pro.getTerm4Day()));
				vo.setName(pro.getName());
				vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
				vo.setMinInvestAmount(pro.getMinInvestAmount());
				vo.setCurrTime(format.format(new Date()));
				vo.setStartTime(format.format(pro.getStartTime()));
				vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				vo.setPropertyType(pro.getPropertyType());
				if(pro.getEndTime() != null) {
					vo.setEndTime(format.format(pro.getEndTime()));
				}
				if(pro.getFinishTime() != null) {
					vo.setFinishTime(format.format(pro.getFinishTime()));
				}
				result.add(BeanUtils.transBeanMap(vo));
			}
		}
		res.setProductLst(result);
		res.setCount(count);
	}
	
	@InterfaceVersion("ListQuery/1.0.2")
	public void listQuery_2(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		List<BsProduct> temp = productService.selectAllProduct(req.getUserType(),req.getProductShowTerminal(),req.getPage(),null);
		Integer count = productService.selectAllProductCount(req.getUserType(),req.getProductShowTerminal(),null);
		if(temp != null && temp.size() > 0) {
			for(BsProduct pro : temp) {
				
				BsPropertyInfo bsPropertyInfo = productService.queryPropertyInfoByProductId(pro.getId());
				
				BsProductVO vo = new BsProductVO();
				vo.setId(pro.getId());
				if(Constants.PROPERTY_SYMBOL_ZAN.contains(bsPropertyInfo.getPropertySymbol())){
					vo.setTerm(String.valueOf(pro.getTerm()));
				}else {
					vo.setTerm(String.valueOf(pro.getTerm4Day()));
				}
				
				vo.setName(pro.getName());
				vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
				vo.setMinInvestAmount(pro.getMinInvestAmount());
				vo.setCurrTime(format.format(new Date()));
				vo.setStartTime(format.format(pro.getStartTime()));
				vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				vo.setPropertyType(pro.getPropertyType());
				vo.setActivityType(pro.getActivityType());
				vo.setMaxSingleInvestAmount(pro.getMaxSingleInvestAmount());
				vo.setIsSupportRedPacket(pro.getIsSupportRedPacket());
				if(pro.getEndTime() != null) {
					vo.setEndTime(format.format(pro.getEndTime()));
				}
				if(pro.getFinishTime() != null) {
					vo.setFinishTime(format.format(pro.getFinishTime()));
				}
				
				if (bsPropertyInfo != null ) {
					vo.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
				}
				result.add(BeanUtils.transBeanMap(vo));
			}
		}
		res.setProductLst(result);
		res.setCount(count);
	}
	
	@InterfaceVersion("FindRate/1.0.0")
	public void findRate(ReqMsg_Product_FindRate req, ResMsg_Product_FindRate res) {
		HashMap<String,Object> map = productService.selectProductRateIndex(req.getUserType(),req.getProductShowTerminal(),"NORMAL");
		if(null != map) {
		    String maxRate = map.get("maxrate") == null ? "0.00" : new DecimalFormat("0.00").format(map.get("maxrate"));
	        String minrate = map.get("minrate") == null ? "0.00" : new DecimalFormat("0.00").format(map.get("minrate"));
	        res.setMaxRate(maxRate);
	        res.setMinRate(minrate);
		} else {
		    res.setMaxRate("0.00");
            res.setMinRate("0.00");
		}
	}
	
	@InterfaceVersion("FindSuggest/1.0.0")
	public void findSuggest(ReqMsg_Product_FindSuggest req, ResMsg_Product_FindSuggest res) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String userType = req.getUserType();
		String terminal = req.getProductShowTerminal();
		List<BsProduct> temp = productService.selectProByRecommend(userType, terminal, Constants.PRODUCT_IS_SUGGEST_YES);
		if(temp != null && temp.size() > 0) {
			for(BsProduct pro : temp) {
				BsProductVO vo = new BsProductVO();
				vo.setId(pro.getId());
				vo.setTerm(String.valueOf(pro.getTerm4Day()));
				vo.setName(pro.getName());
				vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
				vo.setMinInvestAmount(pro.getMinInvestAmount());
				vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				vo.setPropertyType(pro.getPropertyType());
				result.add(BeanUtils.transBeanMap(vo));
			}
		}
		res.setProductLst(result);
	}
	
	@InterfaceVersion("FindSuggest/1.0.1")
	public void findSuggest_1(ReqMsg_Product_FindSuggest req, ResMsg_Product_FindSuggest res) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		String userType = req.getUserType();
		String terminal = req.getProductShowTerminal();
		List<ProductDetailVO> temp = productService.selectNewBuyerProByRecommend(userType, terminal, Constants.PRODUCT_IS_SUGGEST_YES);
		if(temp != null && temp.size() > 0) {
			for(ProductDetailVO pro : temp) {
				BsProductVO vo = new BsProductVO();
				vo.setId(pro.getId());
				if (Constants.PROPERTY_SYMBOL_ZAN.equals(pro.getPropertySymbol())) {
					vo.setTerm(String.valueOf(pro.getTerm()));
				}else {
					vo.setTerm(String.valueOf(pro.getTerm4Day()));
				}
				
				vo.setName(pro.getName());
				vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
				vo.setMinInvestAmount(pro.getMinInvestAmount());
				vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				vo.setPropertyType(pro.getPropertyType());
				vo.setActivityType(pro.getActivityType());
				vo.setMaxSingleInvestAmount(pro.getMaxSingleInvestAmount());
				vo.setPropertySymbol(pro.getPropertySymbol());
				vo.setIsSupportRedPacket(pro.getIsSupportRedPacket());
				result.add(BeanUtils.transBeanMap(vo));
			}
		}
		res.setProductLst(result);
	}

	@InterfaceVersion("FindSuggest/1.0.2")
	@RedisCache(serviceName = "appHomeFindSuggestCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.PRODUCT_FINDSUGGEST)
	public ResMsg_Product_FindSuggest findSuggest2(ReqMsg_Product_FindSuggest req, ResMsg_Product_FindSuggest res) {
		// 首页所有未完成的产品
		Map<String, Object> activityProductMap = BeanUtils.transBeanMap(productService.queryHomeActivityProduct(req.getProductShowTerminal()));
		Map<String, Object> newBuyerProductMap = BeanUtils.transBeanMap(productService.queryHomeNewBuyerProduct(req.getProductShowTerminal()));
		Map<String, Object> normalProductMap = BeanUtils.transBeanMap(productService.queryHomeNormalProduct(req.getProductShowTerminal()));
		List<Map<String, Object>> result = new ArrayList<>();

		// 普通产品添加加息标签属性
		if(null != normalProductMap && normalProductMap.size() > 0) {
			BsProductTagContentVO productTagContentVO = productTagService.queryProductTagContentById((Integer) normalProductMap.get("id"));
			if(null != productTagContentVO) {
				normalProductMap.put("interestRatesTagContent", StringUtil.isNotBlank(productTagContentVO.getInterestRatesTagContent()) ? productTagContentVO.getInterestRatesTagContent() : "");
			}
		}

		result.add(activityProductMap);
		result.add(newBuyerProductMap);
		result.add(normalProductMap);
		res.setProductLst(result);
		return res;
	}

	@InterfaceVersion("IsExistRedPacket/1.0.0")
	public void isExistRedPacket(ReqMsg_Product_IsExistRedPacket req, ResMsg_Product_IsExistRedPacket res) {
		RedPacketInfoVO vo = bsRedPacketInfoMapper.selectOptimalRedPacket(req.getUserId(), req.getTerm());
		res.setIsExistRedPacket("no");
		if (vo != null) {
			res.setIsExistRedPacket("yes");
		}
	}

	@InterfaceVersion("SaveProductInform/1.0.0")
	public void saveProductInform(ReqMsg_Product_SaveProductInform req, ResMsg_Product_SaveProductInform res) {
		BsProductInform record = new BsProductInform();
		record.setUserId(req.getUserId());
		record.setProductId(req.getProductId());
		int count = informService.countByUserIdProductId(record);
		if(count == 0) {
			informService.addProductInform(record);
		}
	}
	
	@InterfaceVersion("SelectProductInform/1.0.0")
	public void selectProductInform(ReqMsg_Product_SelectProductInform req, ResMsg_Product_SelectProductInform res) {
		BsProductInform record = new BsProductInform();
		record.setUserId(req.getUserId());
		record.setProductId(req.getProductId());
		int count = informService.countByUserIdProductId(record);
		if(count > 0) {
			res.setExist(true);
		}
		else {
			res.setExist(false);
		}
	}
	
	@InterfaceVersion("NewBuyer/1.0.0")
	public void newBuyer(ReqMsg_Product_NewBuyer req, ResMsg_Product_NewBuyer res) {
		res.setInvestCount(productService.selectUserBuyProductCount(req.getUserId()));
		if(req.getProductId() != null) {
			res.setMaxSingleInvestAmount(productService.selectUserMaxSingleAmount(req.getProductId()));
		}
	}
	
	@InterfaceVersion("SingleProduct/1.0.0")
	public void selectSingleProduct(ReqMsg_Product_SingleProduct req, ResMsg_Product_SingleProduct res) {
		BsProduct product = productService.findRegularById(req.getId());
		if(product != null) {
			Integer termMonth = product.getTerm();
			Integer term = product.getTerm();
			if(term < 0){
				term = Math.abs(term);
			}else if(term == 12){
				term = 365;
			}else{
				term = term*30;
			}
			res.setId(product.getId());
			res.setName(product.getName());
			res.setRate(MoneyUtil.format(product.getBaseRate()));
			res.setMinInvestAmount(product.getMinInvestAmount());
			res.setCurrTime(DateUtil.format(new Date()));
			res.setStartTime(DateUtil.format(product.getStartTime()));
			res.setLeftAmount(MoneyUtil.subtract(product.getMaxTotalAmount(), product.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
			res.setPropertyType(product.getPropertyType());
			res.setActivityType(product.getActivityType());
			res.setMaxSingleInvestAmount(product.getMaxSingleInvestAmount());
			if(product.getEndTime() != null) {
				res.setEndTime(DateUtil.format(product.getEndTime()));
			}
			if(product.getFinishTime() != null) {
				res.setFinishTime(DateUtil.format(product.getFinishTime()));
			}
			
			if (product.getPropertyId() != null) {
				BsPropertyInfo bsPropertyInfo = productService.queryPropertyInfoById(product.getPropertyId());
				res.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
				
			}
			if (Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(product.getReturnType())) {
				res.setTerm(termMonth);
			}else {
				res.setTerm(term);
			}
			res.setStatus(product.getStatus());
			res.setMaxTotalAmount(product.getMaxTotalAmount());
			res.setCurrTotalAmount(product.getCurrTotalAmount());
			res.setIsSupportRedPacket(product.getIsSupportRedPacket());
			res.setReturnType(product.getReturnType());

			// 查询新手标的标签属性
			BsProductTagContentVO productTagContentVO = productTagService.queryProductTagContentById(product.getId());
			if(null != productTagContentVO) {
				res.setRemindTagContent(productTagContentVO.getRemindTagContent());
				res.setInterestRatesTagContent(productTagContentVO.getInterestRatesTagContent());
			}else {
				res.setRemindTagContent("");
				res.setInterestRatesTagContent("");
			}
		}
		else {
			throw new PTMessageException(PTMessageEnum.CHECK_PRODUCT_OFF_ERROR);
		}
	}
	
	
	@InterfaceVersion("ListIndexQuery/1.0.0")
	public void listIndexQuery(ReqMsg_Product_ListIndexQuery req, ResMsg_Product_ListIndexQuery res) {
				
		//限时活动
		BsProductVO timeLimitActivityProduct = productService.timeLimitActivityProduct(req.getProductShowTerminal());
		if (timeLimitActivityProduct != null ) {
			res.setTimeLimitActivityStatus("hide");
			if (timeLimitActivityProduct.getId()!=null && (timeLimitActivityProduct.getStatus() == 5 || timeLimitActivityProduct.getStatus() == 6 )) {
				res.setTimeLimitActivityStatus("show");
				res.setTimeLimitActivity(BeanUtils.transBeanMap(timeLimitActivityProduct));
			}
			if (timeLimitActivityProduct.getId()!=null && timeLimitActivityProduct.getStatus() == 7 ) {
				if (DateUtil.isSameDay(new Date(), DateUtil.parseDate(timeLimitActivityProduct.getFinishTime()))) {
					res.setTimeLimitActivityStatus("show");
					res.setTimeLimitActivity(BeanUtils.transBeanMap(timeLimitActivityProduct));
				}
			}
		}else {
			res.setTimeLimitActivityStatus("hide");
		}
		
		//新手专享
		BsProductVO noviceActivityProduct = productService.noviceActivityProduct(req.getProductShowTerminal());
		if (noviceActivityProduct != null ) {
			res.setNoviceActivityStatus("hide");
			if (noviceActivityProduct.getId()!=null && (noviceActivityProduct.getStatus() == 5 || noviceActivityProduct.getStatus() == 6 )) {
				res.setNoviceActivityStatus("show");
				res.setNoviceActivity(BeanUtils.transBeanMap(noviceActivityProduct));
			}
			if (noviceActivityProduct.getId()!=null && noviceActivityProduct.getStatus() == 7 ) {
				if (DateUtil.isSameDay(new Date(), DateUtil.parseDate(noviceActivityProduct.getFinishTime()))) {
					res.setNoviceActivityStatus("show");
					res.setNoviceActivity(BeanUtils.transBeanMap(noviceActivityProduct));
				}
			}
		}else {
			res.setNoviceActivityStatus("hide");
		}
		
		//到期还本付息
		Integer  finishReturnAllProductCount = productService.selectProductListCount(Constants.PRODUCT_RETURN_TYPE_FINISH_RETURN_ALL, Constants.PRODUCT_SHOW_TERMINAL_APP, null);
		if (finishReturnAllProductCount == null || finishReturnAllProductCount == 0 ) {
			res.setFinishReturnAllProductStatus("hide");
		}else if (finishReturnAllProductCount > 0) {
			res.setFinishReturnAllProductStatus("show");
			List<BsProduct> finishReturnAllProductList = productService.selectProductList(Constants.PRODUCT_RETURN_TYPE_FINISH_RETURN_ALL, Constants.PRODUCT_SHOW_TERMINAL_APP, null ,0,finishReturnAllProductCount);
			HashMap<String, Object> map = new HashMap<String, Object>();

			Double minRate = finishReturnAllProductList.get(0).getBaseRate();
			Double maxRate = finishReturnAllProductList.get(0).getBaseRate();
			Integer minTerm = finishReturnAllProductList.get(0).getTerm4Day();
			Integer maxTerm = finishReturnAllProductList.get(0).getTerm4Day();
			for (BsProduct bsProduct : finishReturnAllProductList) {
				if (bsProduct.getBaseRate() > maxRate) {
					maxRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getBaseRate() < minRate) {
					minRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getTerm4Day() > maxTerm) {
					maxTerm = bsProduct.getTerm4Day();
				}
				if (bsProduct.getTerm4Day() < minTerm) {
					minTerm = bsProduct.getTerm4Day();
				}
			}
			map.put("name", "到期还本付息");
			map.put("minRate", minRate);
			map.put("maxRate", maxRate);
			map.put("minTerm", minTerm);
			map.put("maxTerm", maxTerm);
			res.setFinishReturnAllProduct(map);
		}
		
		//等额本息
		Integer  averageCapitalPlusInterestProductCount = productService.selectProductListCount(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST, Constants.PRODUCT_SHOW_TERMINAL_APP, null);
		if (averageCapitalPlusInterestProductCount == null || averageCapitalPlusInterestProductCount == 0 ) {
			res.setAverageCapitalPlusInterestProductStatus("hide");
		}else if (averageCapitalPlusInterestProductCount > 0) {
			List<BsProduct> averageCapitalPlusInterestProductList = productService.selectProductList(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST, Constants.PRODUCT_SHOW_TERMINAL_APP, null ,0,averageCapitalPlusInterestProductCount);
			res.setAverageCapitalPlusInterestProductStatus("show");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			Double minRate = averageCapitalPlusInterestProductList.get(0).getBaseRate();
			Double maxRate = averageCapitalPlusInterestProductList.get(0).getBaseRate();
			Integer minTerm = averageCapitalPlusInterestProductList.get(0).getTerm();
			Integer maxTerm = averageCapitalPlusInterestProductList.get(0).getTerm();
			
			for (BsProduct bsProduct : averageCapitalPlusInterestProductList) {
				if (bsProduct.getBaseRate() > maxRate) {
					maxRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getBaseRate() < minRate) {
					minRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getTerm() > maxTerm) {
					maxTerm = bsProduct.getTerm();
				}
				if (bsProduct.getTerm() < minTerm) {
					minTerm = bsProduct.getTerm();
				}
			}
			map.put("name", "等额本息");
			map.put("minRate", minRate);
			map.put("maxRate", maxRate);
			map.put("minTerm", minTerm);
			map.put("maxTerm", maxTerm);
			res.setAverageCapitalPlusInterestProduct(map);
		}
	}
	
	/**
	 * 从原listIndexInfoQuery中分离,在用户id不为空的情况下查询用户是否购买过产品
	 * @author bianyatian
	 * @param req
	 * @param res
	 */
	@InterfaceVersion("ListIndexInfoQuery4User/1.0.1")
	public void listIndexInfoQuery4User(ReqMsg_Product_ListIndexInfoQuery4User req, ResMsg_Product_ListIndexInfoQuery4User res) {
		//之前没有买过产品
		List<String> transTypeList = new ArrayList<String>();
        transTypeList.add(Constants.TRANS_CARD_BUY_PRODUCT);
        transTypeList.add(Constants.TRANS_BALANCE_BUY_PRODUCT);
        BsPayOrdersExample payOrderExample = new BsPayOrdersExample();
        payOrderExample.createCriteria().andStatusEqualTo(Constants.ORDER_STATUS_SUCCESS).andTransTypeIn(transTypeList).andUserIdEqualTo(req.getUserId());
        Integer investCount = payOrderMapper.countByExample(payOrderExample);
		if (investCount > 0) {
			res.setIsNovice(Constants.USER_IS_NOVICE_NO);
		}else {
			res.setIsNovice(Constants.USER_IS_NOVICE_YES);
		}
	}

	@InterfaceVersion("ListIndexInfoQuery/1.0.1")
	@RedisCache(serviceName = "appProductListIndexInfoQueryImpl", redisCacheKey = ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTINDEXINFOQUERY)
	public ResMsg_Product_ListIndexInfoQuery listIndexInfoQuery(ReqMsg_Product_ListIndexInfoQuery req, ResMsg_Product_ListIndexInfoQuery res) {
		
		//限时活动
		BsProductVO timeLimitActivityProduct = productService.timeLimitActivityProduct(req.getProductShowTerminal());
		if (timeLimitActivityProduct != null ) {
			res.setTimeLimitActivityStatus("hide");
			if (timeLimitActivityProduct.getId()!=null && (timeLimitActivityProduct.getStatus() == 5 || timeLimitActivityProduct.getStatus() == 6 )) {
				res.setTimeLimitActivityStatus("show");
				res.setTimeLimitActivity(BeanUtils.transBeanMap(timeLimitActivityProduct));
			}
			if (timeLimitActivityProduct.getId()!=null && timeLimitActivityProduct.getStatus() == 7 ) {
				if (timeLimitActivityProduct.getFinishTime() != null &&
						DateUtil.isSameDay(new Date(), DateUtil.parseDate(timeLimitActivityProduct.getFinishTime()))) {
					res.setTimeLimitActivityStatus("show");
					res.setTimeLimitActivity(BeanUtils.transBeanMap(timeLimitActivityProduct));
				}
			}
		}else {
			res.setTimeLimitActivityStatus("hide");
		}
		
		//新手专享
		BsProductVO noviceActivityProduct = productService.noviceActivityProduct(req.getProductShowTerminal());
		if (noviceActivityProduct != null ) {
			res.setNoviceActivityStatus("hide");
			if (noviceActivityProduct.getId()!= null && (noviceActivityProduct.getStatus() == 5  || noviceActivityProduct.getStatus() == 6)) {
				res.setNoviceActivityStatus("show");
				res.setNoviceActivity(BeanUtils.transBeanMap(noviceActivityProduct));
			}
			if (noviceActivityProduct.getId()!=null && (noviceActivityProduct.getStatus() == 7)) {
				if (noviceActivityProduct.getFinishTime() != null &&
						DateUtil.isSameDay(new Date(), DateUtil.parseDate(noviceActivityProduct.getFinishTime()))) {
					res.setNoviceActivityStatus("show");
					res.setNoviceActivity(BeanUtils.transBeanMap(noviceActivityProduct));
				}
			}
		}else {
			res.setNoviceActivityStatus("hide");
		}
		
		//到期还本付息
		Integer  finishReturnAllProductCount = productService.selectProductListCount(Constants.PRODUCT_RETURN_TYPE_FINISH_RETURN_ALL, Constants.PRODUCT_SHOW_TERMINAL_APP, null);
		if (finishReturnAllProductCount == null || finishReturnAllProductCount == 0 ) {
			res.setFinishReturnAllProductStatus("hide");
		}else if (finishReturnAllProductCount > 0) {
			res.setFinishReturnAllProductStatus("show");
			List<BsProduct> finishReturnAllProductList = productService.selectProductList(Constants.PRODUCT_RETURN_TYPE_FINISH_RETURN_ALL, Constants.PRODUCT_SHOW_TERMINAL_APP, null ,0,finishReturnAllProductCount);
			HashMap<String, Object> map = new HashMap<String, Object>();

			Double minRate = finishReturnAllProductList.get(0).getBaseRate();
			Double maxRate = finishReturnAllProductList.get(0).getBaseRate();
			Integer minTerm = finishReturnAllProductList.get(0).getTerm4Day();
			Integer maxTerm = finishReturnAllProductList.get(0).getTerm4Day();
			for (BsProduct bsProduct : finishReturnAllProductList) {
				if (bsProduct.getBaseRate() > maxRate) {
					maxRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getBaseRate() < minRate) {
					minRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getTerm4Day() > maxTerm) {
					maxTerm = bsProduct.getTerm4Day();
				}
				if (bsProduct.getTerm4Day() < minTerm) {
					minTerm = bsProduct.getTerm4Day();
				}
			}
			map.put("name", "到期还本付息");
			map.put("minRate", minRate);
			map.put("maxRate", maxRate);
			map.put("minTerm", minTerm);
			map.put("maxTerm", maxTerm);
			res.setFinishReturnAllProduct(map);
		}
		
		//等额本息
		Integer  averageCapitalPlusInterestProductCount = productService.selectProductListCount(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST, Constants.PRODUCT_SHOW_TERMINAL_APP, null);
		if (averageCapitalPlusInterestProductCount == null || averageCapitalPlusInterestProductCount == 0 ) {
			res.setAverageCapitalPlusInterestProductStatus("hide");
		}else if (averageCapitalPlusInterestProductCount > 0) {
			List<BsProduct> averageCapitalPlusInterestProductList = productService.selectProductList(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST, Constants.PRODUCT_SHOW_TERMINAL_APP, null ,0,averageCapitalPlusInterestProductCount);
			res.setAverageCapitalPlusInterestProductStatus("show");
			
			HashMap<String, Object> map = new HashMap<String, Object>();
			Double minRate = averageCapitalPlusInterestProductList.get(0).getBaseRate();
			Double maxRate = averageCapitalPlusInterestProductList.get(0).getBaseRate();
			Integer minTerm = averageCapitalPlusInterestProductList.get(0).getTerm();
			Integer maxTerm = averageCapitalPlusInterestProductList.get(0).getTerm();
			
			for (BsProduct bsProduct : averageCapitalPlusInterestProductList) {
				if (bsProduct.getBaseRate() > maxRate) {
					maxRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getBaseRate() < minRate) {
					minRate = bsProduct.getBaseRate();
				}
				if (bsProduct.getTerm() > maxTerm) {
					maxTerm = bsProduct.getTerm();
				}
				if (bsProduct.getTerm() < minTerm) {
					minTerm = bsProduct.getTerm();
				}
			}
			map.put("name", "等额本息");
			map.put("minRate", minRate);
			map.put("maxRate", maxRate);
			map.put("minTerm", minTerm);
			map.put("maxTerm", maxTerm);
			res.setAverageCapitalPlusInterestProduct(map);
		}
		return res;
	}
	
	@InterfaceVersion("ProductListReturnType/1.0.0")
	@RedisCache(serviceName = "appProductListReturnTypeImpl", redisCacheKey = ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTRETURNTYPE)
	public ResMsg_Product_ProductListReturnType productListReturnType(ReqMsg_Product_ProductListReturnType req, ResMsg_Product_ProductListReturnType res) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Integer count = productService.selectProductListCount(req.getReturnType(), Constants.PRODUCT_SHOW_TERMINAL_APP, null);
		if(count != null && count > 0) {
			List<BsProduct> temp = productService.selectProductList(req.getReturnType(), Constants.PRODUCT_SHOW_TERMINAL_APP, null , 0, count);
			for(BsProduct pro : temp) {
				BsPropertyInfo bsPropertyInfo = productService.queryPropertyInfoByProductId(pro.getId());
				BsProductVO vo = new BsProductVO();
				vo.setId(pro.getId());
				
				if(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(pro.getReturnType())){
					vo.setTerm(String.valueOf(pro.getTerm()));
				}else {
					vo.setTerm(String.valueOf(pro.getTerm4Day()));
				}
				vo.setName(pro.getName());
				vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
				vo.setMinInvestAmount(pro.getMinInvestAmount());
				vo.setCurrTime(format.format(new Date()));
				vo.setStartTime(format.format(pro.getStartTime()));
				vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				vo.setPropertyType(pro.getPropertyType());
				vo.setActivityType(pro.getActivityType());
				vo.setMaxSingleInvestAmount(pro.getMaxSingleInvestAmount());
				vo.setIsSupportRedPacket(pro.getIsSupportRedPacket());
				if(pro.getEndTime() != null) {
					vo.setEndTime(format.format(pro.getEndTime()));
				}
				if(pro.getFinishTime() != null) {
					vo.setFinishTime(format.format(pro.getFinishTime()));
				}
				
				if (bsPropertyInfo != null ) {
					vo.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
				}
				vo.setStatus(pro.getStatus());
				vo.setReturnType(pro.getReturnType());
				vo.setCurrTotalAmount(pro.getCurrTotalAmount());
				vo.setMaxTotalAmount(pro.getMaxTotalAmount());
				vo.setIsSuggest(pro.getIsSuggest());
				// 写入提醒标签、加息标签内容
				BsProductTagContentVO productTagContentVO = productTagService.queryProductTagContentById(pro.getId());
				if(null != productTagContentVO) {
					vo.setRemindTagContent(StringUtil.isNotBlank(productTagContentVO.getRemindTagContent()) ? productTagContentVO.getRemindTagContent() : null);
					vo.setInterestRatesTagContent(StringUtil.isNotBlank(productTagContentVO.getInterestRatesTagContent()) ? productTagContentVO.getInterestRatesTagContent() : null);
				}
				result.add(BeanUtils.transBeanMap(vo));
			}
		}
		res.setProductList(result);
		return res;
	}
	
	
	
	
	@InterfaceVersion("ProductListStatusFinish30/1.0.0")
	public void productListStatusFinish30(ReqMsg_Product_ProductListStatusFinish30 req, ResMsg_Product_ProductListStatusFinish30 res) {
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		
		Integer offset = 8;
		Integer start = (req.getPage() - 1) * offset;
		
		Integer count = productService.selectProductListStatusFinish30Count(req.getReturnType(), Constants.PRODUCT_SHOW_TERMINAL_APP, null);
		Integer totalPage = 1;
		if(count != null) {
			totalPage = count%offset==0?count/offset:count/offset+1;
		}
		res.setCount(totalPage);
		
		if(count != null && count > 0) {
			List<BsProduct> temp = productService.selectProductListStatusFinish30(req.getReturnType(), Constants.PRODUCT_SHOW_TERMINAL_APP, null , start, offset);
			for(BsProduct pro : temp) {
				BsPropertyInfo bsPropertyInfo = productService.queryPropertyInfoByProductId(pro.getId());
				BsProductVO vo = new BsProductVO();
				vo.setId(pro.getId());
				
				if(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(pro.getReturnType())){
					vo.setTerm(String.valueOf(pro.getTerm()));
				}else {
					vo.setTerm(String.valueOf(pro.getTerm4Day()));
				}
				vo.setName(pro.getName());
				vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
				vo.setMinInvestAmount(pro.getMinInvestAmount());
				vo.setCurrTime(format.format(new Date()));
				vo.setStartTime(format.format(pro.getStartTime()));
				vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				vo.setPropertyType(pro.getPropertyType());
				vo.setActivityType(pro.getActivityType());
				vo.setMaxSingleInvestAmount(pro.getMaxSingleInvestAmount());
				vo.setIsSupportRedPacket(pro.getIsSupportRedPacket());
				if(pro.getEndTime() != null) {
					vo.setEndTime(format.format(pro.getEndTime()));
				}
				if(pro.getFinishTime() != null) {
					vo.setFinishTime(format.format(pro.getFinishTime()));
				}
				
				if (bsPropertyInfo != null ) {
					vo.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
				}
				vo.setStatus(pro.getStatus());
				vo.setReturnType(pro.getReturnType());
				vo.setCurrTotalAmount(pro.getCurrTotalAmount());
				vo.setMaxTotalAmount(pro.getMaxTotalAmount());
				vo.setIsSuggest(pro.getIsSuggest());
				result.add(BeanUtils.transBeanMap(vo));
			}
		}
		
		
		res.setProductList(result);
		
	}
	
	
	
	@InterfaceVersion("CheckBeforeBuy/1.0.0")
	public void checkBeforeBuy(ReqMsg_Product_CheckBeforeBuy req, ResMsg_Product_CheckBeforeBuy res) {
		
		//查询用户是否有产品购买权限
		//1、（老用户不能购买新手标）
		BsProduct bsProduct = productService.findRegularById(req.getProductId());
		if (bsProduct == null ) {
			throw new PTMessageException(PTMessageEnum.PRODUCT_INFO_NOT_FOUND_ERROR);
		}
		if (Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(bsProduct.getActivityType())) {
			Integer count = productService.selectUserBuyProductCount(req.getUserId());
			if (count > 0 ) {
				throw new PTMessageException(PTMessageEnum.PRODUCT_BUY_NEWER_ERROR);
			}
			
		}

	}
	
	
	
	@InterfaceVersion("BalanceBuyInfo/1.0.0")
	public void balanceBuyInfo(ReqMsg_Product_BalanceBuyInfo req, ResMsg_Product_BalanceBuyInfo res) {
		BsProduct product = productService.findRegularById(req.getProductId());
		if(product != null) {
			Integer termMonth = product.getTerm();
			Integer term = product.getTerm();
			if(term < 0){
				term = Math.abs(term);
			}else if(term == 12){
				term = 365;
			}else{
				term = term*30;
			}
			res.setProductId(product.getId());
			res.setName(product.getName());
			res.setRate(MoneyUtil.format(product.getBaseRate()));
			res.setMinInvestAmount(product.getMinInvestAmount());
			res.setMaxSingleInvestAmount(product.getMaxSingleInvestAmount());
			if (product.getPropertyId() != null) {
				BsPropertyInfo bsPropertyInfo = productService.queryPropertyInfoById(product.getPropertyId());
				res.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
				
			}
			if (Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(product.getReturnType())) {
				res.setTerm(String.valueOf(termMonth));
			}else {
				res.setTerm(String.valueOf(term));
			}
			res.setMaxTotalAmount(product.getMaxTotalAmount());
			res.setCurrTotalAmount(product.getCurrTotalAmount());
			res.setIsSupportRedPacket(product.getIsSupportRedPacket());
			res.setReturnType(product.getReturnType());
			
			if ("TRUE".equals(product.getIsSupportRedPacket())) {
	            RedPacketInfoVO vo = bsRedPacketInfoMapper.selectOptimalRedPacket(req.getUserId(), termMonth);
	            res.setIsExistRedPacket("no");
	            if (vo != null) {
					res.setIsExistRedPacket("yes");
					res.setRedPacketId(vo.getId());
					res.setFull(vo.getFull());
					res.setSubtract(vo.getSubtract());
					res.setSerialName(vo.getSerialName());
				}
			}
		}
		else {
			throw new PTMessageException(PTMessageEnum.CHECK_PRODUCT_OFF_ERROR);
		}
	}
	
}
