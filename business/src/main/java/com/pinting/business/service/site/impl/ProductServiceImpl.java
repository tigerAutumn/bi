package com.pinting.business.service.site.impl;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.dao.*;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.*;
import com.pinting.business.service.site.ProductService;
import com.pinting.business.service.site.ProductTagService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.in.util.MethodRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.*;

@Service("productService")
public class ProductServiceImpl implements ProductService{
	@Autowired
	private BsProductMapper productMapper;
	@Autowired
	private BsPayOrdersMapper orderMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	@Autowired
	private BsUserTypeAuthMapper bsUserTypeAuthMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsAccountMapper bsAccountMapper;
	@Autowired
	private BsPropertyInfoMapper bsPropertyInfoMapper;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsProductSerialMapper bsProductSerialMapper;
	@Autowired
	private ProductTagService productTagService;

	@Override
	public List<BsProductSummaryVO> findProductGroupByType() {
		List<BsProductSummaryVO> dataList = productMapper.selectProductGroupByType();
		return dataList.size() > 0? dataList : null;
	}

	@Override
	public List<BsProduct> findProductListByType(String productTypeReg) {

		BsProductExample productExample = new BsProductExample();
		productExample.createCriteria().andTypeEqualTo(productTypeReg);
		List<BsProduct> dataList = productMapper.selectByExample(productExample);
		return dataList.size() > 0? dataList : null;
	}

	@Override
	@MethodRole("APP")
	public BsProduct findRegularById(int id) {
		return productMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean modifyProductById(BsProduct productModify) {

		BsProductExample productExample = new BsProductExample();
		productExample.createCriteria().andIdEqualTo(productModify.getId());
		productModify.setUpdateTime(new Date());
		return productMapper.updateByExampleSelective(productModify, productExample) == 1;

	}

	@Override
	public List<BsProductSummaryVO> findProductGroupByCode() {
		List<BsProductSummaryVO> dataList = productMapper.selectProductGroupByCode();
		return dataList.size() > 0? dataList : null;
	}

	@Override
	@MethodRole("APP")
	public List<BsProduct> findProductList() {
		return productMapper.selectByExample(null);
	}

	@Override
	@MethodRole("APP")
	public List<BsProduct> selectProByUserType(String userType) {
		return productMapper.selectProByUserType(userType);
	}

	@Override
	@MethodRole("APP")
	public BsSysConfig findConfigValue(String key) {
		return bsSysConfigMapper.selectByPrimaryKey(key);
	}

    /**
     * @see com.pinting.business.service.site.ProductService#selectQianBaoProductList(com.pinting.business.hessian.site.message.ReqMsg_Product_QianBaoProductList, com.pinting.business.hessian.site.message.ResMsg_Product_QianBaoProductList)
     */
    @Override
    public void selectQianBaoProductList(ReqMsg_Product_QianBaoProductList req,
                                         ResMsg_Product_QianBaoProductList res) {
        List<Map<String,Object>> ProductLst = new ArrayList<Map<String,Object>>();
        List<BsProduct> list = productMapper.selectQianBaoProductList();
        if(list != null && list.size() > 0) {
            for(BsProduct pro : list) {
                BsProductVO vo = new BsProductVO();
                vo.setId(pro.getId());
                vo.setTerm(String.valueOf(pro.getTerm4Day()));
                vo.setName(pro.getName());
                vo.setRate(new DecimalFormat("0.0").format(pro.getBaseRate()));
                vo.setMinInvestAmount(pro.getMinInvestAmount());
                ProductLst.add(BeanUtils.transBeanMap(vo));
            }
        }
        res.setProductLst(ProductLst);
    }

	@Override
	public List<BsProduct> selectProductRateList() {
		 List<BsProduct> list = productMapper.selectProductRateList();
		 return list;
	}

    /**
     * @see com.pinting.business.service.site.ProductService#checkProductIsOff(java.lang.Integer)
     */
    @Override
    public boolean checkProductIsOff(Integer productId) {
        BsUserTypeAuthExample authExample = new BsUserTypeAuthExample();
        authExample.createCriteria().andProductIdEqualTo(productId);
        List<BsUserTypeAuth> auths = bsUserTypeAuthMapper.selectByExample(authExample);
        if(CollectionUtils.isEmpty(auths))
            return true;    // 已下线
        else
            return false;
    }

    /**
     * @see com.pinting.business.service.site.ProductService#checkProductIsSelf(com.pinting.business.hessian.site.message.ReqMsg_Product_BuyAgreement, com.pinting.business.hessian.site.message.ResMsg_Product_BuyAgreement)
     */
    @Override
    public void checkProductIsSelf(ReqMsg_Product_BuyAgreement req,
                                      ResMsg_Product_BuyAgreement res) {
        BsAccountExample accountExample = new BsAccountExample();
        accountExample.createCriteria().andUserIdEqualTo(req.getUserId());
        List<BsAccount> accounts = bsAccountMapper.selectByExample(accountExample);
        if(req.getSubAccountId() != null) {
            BsSubAccount bsSubAccount = bsSubAccountMapper.selectByPrimaryKey(req.getSubAccountId());
            if(!accounts.get(0).getId().equals(bsSubAccount.getAccountId())) {
                res.setIsSelf(false);
            }
        }
        BsSubAccountExample bsSubAccountExample = new BsSubAccountExample();
        bsSubAccountExample.createCriteria().andAccountIdEqualTo(accounts.get(0).getId()).andProductIdEqualTo(req.getProductId());
        List<BsSubAccount> bsSubAccounts = bsSubAccountMapper.selectByExample(bsSubAccountExample);
        if (!CollectionUtils.isEmpty(bsSubAccounts)) {
            res.setIsSelf(true);
        } else {
            res.setIsSelf(false);
        }
    }

	@Override
	public BsUserTypeAuth findUserTypeAuth(Integer id) {
		BsUserTypeAuthExample example = new BsUserTypeAuthExample();
		example.createCriteria().andProductIdEqualTo(id);
		List<BsUserTypeAuth> bsUserTypeAuths =  bsUserTypeAuthMapper.selectByExample(example);
		if (CollectionUtils.isEmpty(bsUserTypeAuths)) {
			return null;
		}else {
			return bsUserTypeAuths.get(0);
		}
	}

	@Override
	@MethodRole("APP")
	public List<BsProduct> selectProByRecommend(String userType, String terminal, String isSuggest) {
		List<BsProduct> result = productMapper.selectProByRecommend(userType, terminal, isSuggest);
		return result;
	}

	@Override
	@MethodRole("APP")
	public HashMap<String,Object> selectProductRateIndex(String userType, String terminal, String activityType) {
		HashMap<String,Object> result = productMapper.selectProductRateIndex(userType, terminal, activityType);
		return result;
	}

	@Override
	public List<BsProduct> findAuthPassProducts() {
		BsProductExample example = new BsProductExample();
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(Constants.PRODUCT_STATUS_OPENING);
		statusList.add(Constants.PRODUCT_STATUS_PUBLISH_NO);
		statusList.add(Constants.PRODUCT_STATUS_PUBLISH_YES);
		example.createCriteria().andStatusIn(statusList);
		return productMapper.selectByExample(example);
	}

	@Override
	public List<BsProductUserVO> selectProductWithInformList() {
		BsSysConfig sysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.PRODUCT_INFORM_MINUTE);
		List<BsProductUserVO> list = productMapper.selectProductWithInformList(Integer.parseInt(sysConfig.getConfValue()));
		return list;
	}

	@Override
	@MethodRole("APP")
	public List<BsProduct> selectAllProduct(String userType, String terminal, Integer page, String activityType) {
		Integer offset = 10;
		Integer start = (page - 1) * offset;
		List<BsProduct> result = productMapper.selectAllProduct(userType, terminal, start, offset, activityType);
		return result;
	}

	@Override
	public ProductDetailVO selectProductDetailById(Integer productId,Integer start,Integer numPerPage) {
		ProductDetailVO productDetail = productMapper.selectProductDetailByProductId(productId);
		
		return productDetail;
	}
	
	@Override
	@RedisCache(serviceName = "productSelectProductDetailListCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.PRODUCT_SELECTPRODUCTDETAILLIST)
	public ProductDetailVO selectProductDetailList(Integer productId,Integer start,Integer numPerPage) {
		ProductDetailVO productDetail = new ProductDetailVO();
		List<InvestRecordVO> investRecord = productMapper.selectInvestRecordByProductId(productId,start, numPerPage);
		Integer totalRows = productMapper.selectInvestRecordCountByProductId(productId);
		
		productDetail.setTotalRows(totalRows);
		productDetail.setInvestRecordVO(investRecord);

		return productDetail;
	}
	
	@Override
	@MethodRole("APP")
	public Integer selectAllProductCount(String userType, String terminal, String activityType) {
		Integer count = productMapper.selectAllProductCount(userType, terminal, activityType);
		Integer totalPage = 1;
		if(count != null) {
			totalPage = count%10==0?count/10:count/10+1;
		}
		return totalPage;
	}

    /**
     * @see com.pinting.business.service.site.ProductService#listQuery(com.pinting.business.hessian.site.message.ReqMsg_Product_ListQuery, com.pinting.business.hessian.site.message.ResMsg_Product_ListQuery)
     */
	@Override
    public ResMsg_Product_ListQuery listQuery(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res) {
        List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        /**
         * 产品列表sql优化(缓存)查询逻辑变更:投资次数,合作方信息 拆分,单独查询
         * @yangende 
         * @date 2018-05-04
         * */
        List<ProductDetailVO> bsProducts = productMapper.selectProductGrid(req.getUserType(), req.getProductShowTerminal(),
            req.getStart(), req.getNumPerPage(), req.getStatus(), req.getTerm(), req.getIsSuggest(), req.getActivityType(),req.getReturnType());
        Integer count = productMapper.countProductGrid(req.getUserType(), req.getProductShowTerminal(),
            req.getStatus(), req.getTerm(), req.getIsSuggest(), req.getActivityType(),req.getReturnType());

        if(!CollectionUtils.isEmpty(bsProducts)) {
            for(ProductDetailVO pro : bsProducts) {
                BsProductVO vo = new BsProductVO();
                vo.setId(pro.getId());
				vo.setDayTerm(String.valueOf(pro.getTerm()));
				vo.setName(pro.getName());
                vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
                vo.setMinInvestAmount(pro.getMinInvestAmount());
                vo.setCurrTime(format.format(new Date()));
                vo.setStartTime(format.format(pro.getStartTime()));
                vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
                vo.setStatus(pro.getStatus()==null?0:pro.getStatus());
                vo.setIsSuggest(pro.getIsSuggest()==null?"NO":pro.getIsSuggest());
                vo.setCurrTotalAmount(pro.getCurrTotalAmount());
                vo.setMaxTotalAmount(pro.getMaxTotalAmount());
                vo.setMaxSingleInvestAmount(pro.getMaxSingleInvestAmount());
                //写入合作方 
                BsPropertyInfo bsProInfo = bsPropertyInfoMapper.selectByPrimaryKey(pro.getPropertyId());
				vo.setPropertySymbol(bsProInfo.getPropertySymbol());
				if(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(bsProInfo.getPropertySymbol())) {
					vo.setTerm(String.valueOf(pro.getTerm()));
				} else {
					vo.setTerm(String.valueOf(pro.getTerm4Day()));
				}
                if(pro.getEndTime() != null) {
                    vo.setEndTime(format.format(pro.getEndTime()));
                }
                if(pro.getFinishTime() != null) {
                    vo.setFinishTime(format.format(pro.getFinishTime()));
                }
                //写入投资次数
                List<Integer> statusList = new ArrayList<Integer>();
                statusList.add(2);
                statusList.add(5);
                statusList.add(7);
                List<String> prodTypeList = new ArrayList<String>();
                prodTypeList.add(Constants.PRODUCT_TYPE_REG);
                prodTypeList.add(Constants.PRODUCT_TYPE_AUTH);
                prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_YUN);
                prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_ZSD);
                prodTypeList.add(Constants.PRODUCT_TYPE_AUTH_7);
                BsSubAccountExample subAcctExm = new BsSubAccountExample();
                subAcctExm.createCriteria().andProductIdEqualTo(pro.getId()).andStatusIn(statusList).andProductTypeIn(prodTypeList);
                int investCount = bsSubAccountMapper.countByExample(subAcctExm);
				vo.setInvestCount( investCount );
				
                vo.setActivityType(pro.getActivityType());
				vo.setSerialOrder(pro.getSerialOrder());

				// 写入提醒标签、加息标签内容
				BsProductTagContentVO productTagContentVO = productTagService.queryProductTagContentById(pro.getId());
				if(null != productTagContentVO) {
					vo.setRemindTagContent(StringUtil.isNotBlank(productTagContentVO.getRemindTagContent()) ? productTagContentVO.getRemindTagContent() : null);
					vo.setInterestRatesTagContent(StringUtil.isNotBlank(productTagContentVO.getInterestRatesTagContent()) ? productTagContentVO.getInterestRatesTagContent() : null);
				}
                result.add(BeanUtils.transBeanMap(vo));
            }
        }
        res.setProductLst(result);
        res.setCount(count);
        return res;
    }

	@Override
	public List<BsProduct> productPlanListIndex(String userType, String terminal) {
		 List<BsProduct> bsProducts = productMapper.productPlanListIndex(userType,terminal);
		 return bsProducts;
	}

    @Override
    public BsProductVO noviceRecommendedProduct(String terminal) {
        BsProductVO vo = null;
        boolean unfinishedProject = false;  // 无未完成计划
        BsProduct pro = productMapper.selectNewUserProduct(terminal);
        if(null != pro) {
            unfinishedProject = true;   // 有未完成计划
        }
        if(null == pro) {
            pro = productMapper.selectNoviceRecommendedProduct(terminal);
        }
        if(null == pro) {
            pro = productMapper.selectOneMonthEndTimeLastestProduct(terminal);
        }
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null != pro) {
            vo = new BsProductVO();
            vo.setId(pro.getId());
            vo.setName(pro.getName());
            vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
            vo.setMinInvestAmount(pro.getMinInvestAmount());
            vo.setCurrTime(format.format(new Date()));
            vo.setStartTime(format.format(pro.getStartTime()));
            vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            vo.setStatus(pro.getStatus());
            vo.setCurrTotalAmount(pro.getCurrTotalAmount());
            vo.setMaxTotalAmount(pro.getMaxTotalAmount());
			BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(pro.getPropertyId());
			vo.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
			if(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(bsPropertyInfo.getPropertySymbol())) {
				vo.setTerm(String.valueOf(pro.getTerm()));
			} else {
				vo.setTerm(String.valueOf(pro.getTerm4Day()));
			}
            if(pro.getEndTime() != null) {
                vo.setEndTime(format.format(pro.getEndTime()));
            }
            if(pro.getFinishTime() != null) {
                vo.setFinishTime(format.format(pro.getFinishTime()));
            }
            vo.setUnfinishedProject(unfinishedProject == true ? "YES" : "NO");
			vo.setSerialOrder(pro.getSerialOrder());
			if(pro.getSerialId() != null) {
				BsProductSerial bsProductSerial = bsProductSerialMapper.selectByPrimaryKey(pro.getSerialId());
				vo.setSerialName(bsProductSerial.getSerialName());
			}
			//产品活动类型
			vo.setActivityType(pro.getActivityType());
        }
        return vo;
    }

    @Override
	public BsProductVO noviceExclusivePlan(String terminal) {
    	BsProductVO vo = null;

    	BsProduct pro = productMapper.selectNewUserProduct(terminal);
    	if(pro == null){
    		pro = productMapper.selectOverNewUserProduct(terminal);
    	}
    	SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	if(null != pro) {
            vo = new BsProductVO();
            vo.setId(pro.getId());
            vo.setTerm(String.valueOf(pro.getTerm4Day()));
            vo.setName(pro.getName());
            vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
            vo.setMinInvestAmount(pro.getMinInvestAmount());
            vo.setCurrTime(format.format(new Date()));
            vo.setStartTime(format.format(pro.getStartTime()));
            vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            vo.setStatus(pro.getStatus());
            vo.setCurrTotalAmount(pro.getCurrTotalAmount());
            vo.setMaxTotalAmount(pro.getMaxTotalAmount());
            if(pro.getEndTime() != null) {
                vo.setEndTime(format.format(pro.getEndTime()));
            }
            if(pro.getFinishTime() != null) {
                vo.setFinishTime(format.format(pro.getFinishTime()));
            }
        }
		return vo;
	}

	/**
     * 新的首页理财计划
     * @param userType
     * @param terminal
     * @param isSuggest
     * @return
     */
    @Override
    @MethodRole("APP")
    public List<ProductDetailVO> selectNewBuyerProByRecommend(String userType, String terminal,
                                                        String isSuggest) {
        List<ProductDetailVO> result = productMapper.selectNewBuyerProByRecommend(userType, terminal, isSuggest);
        List<ProductDetailVO> list = new ArrayList<ProductDetailVO>();
        if(!CollectionUtils.isEmpty(result)) {
            int index = 1;
            for (ProductDetailVO bsProduct : result) {
                if(index > 3) {
                    break;
                } else {
                    list.add(bsProduct);
                }
                index++;
            }
        }
        return list;
    }

	@Override
	@MethodRole("APP")
	public Integer selectUserBuyProductCount(Integer userId) {
		List<String> transTypeList = new ArrayList<String>();
		transTypeList.add(Constants.TRANS_CARD_BUY_PRODUCT);
		transTypeList.add(Constants.TRANS_BALANCE_BUY_PRODUCT);
		List<Integer> statusList = new ArrayList<Integer>();
		statusList.add(Constants.ORDER_STATUS_PAYING);
		statusList.add(Constants.ORDER_STATUS_SUCCESS);

		BsPayOrdersExample payOrderExample = new BsPayOrdersExample();
		payOrderExample.createCriteria().andStatusIn(statusList).andTransTypeIn(transTypeList).andUserIdEqualTo(userId);
		Integer investCount = orderMapper.countByExample(payOrderExample);
		return investCount == null ? 0 : investCount;
	}

	@Override
	@MethodRole("APP")
	public Double selectUserMaxSingleAmount(Integer productId) {
		BsProduct p = productMapper.selectByPrimaryKey(productId);
		if(p != null) {
			return p.getMaxSingleInvestAmount();
		}
		return null;
	}

	@Override
	public BsPropertyInfo queryPropertyInfoById(Integer propertyId) {
		BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(propertyId);
		return bsPropertyInfo;
	}

	@Override
	public BsPropertyInfo queryPropertyInfoByProductId(Integer productId) {
		BsProduct bsProduct = productMapper.selectByPrimaryKey(productId);
		if (bsProduct.getPropertyId() != null ) {
			BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(bsProduct.getPropertyId());
			return bsPropertyInfo;
		}
		return null;
	}

	@Override
	@MethodRole("APP")
	public BsProductVO timeLimitActivityProduct(String terminal) {
        BsProductVO vo = null;
        BsProduct pro = productMapper.selectActivityAndNoviceProduct(terminal,Constants.PRODUCT_ACTIVITY_TYPE_ACTIVITY);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null != pro) {
            vo = new BsProductVO();
            vo.setId(pro.getId());
            vo.setName(pro.getName());
            vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
            vo.setMinInvestAmount(pro.getMinInvestAmount());
            vo.setCurrTime(format.format(new Date()));
            vo.setStartTime(format.format(pro.getStartTime()));
            vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            vo.setStatus(pro.getStatus());
            vo.setCurrTotalAmount(pro.getCurrTotalAmount());
            vo.setMaxTotalAmount(pro.getMaxTotalAmount());
            vo.setReturnType(pro.getReturnType());
			BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(pro.getPropertyId());
			vo.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
			if(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(pro.getReturnType())) {
				vo.setTerm(String.valueOf(pro.getTerm()));
			} else {
				vo.setTerm(String.valueOf(pro.getTerm4Day()));
			}
            if(pro.getEndTime() != null) {
                vo.setEndTime(format.format(pro.getEndTime()));
            }
            if(pro.getFinishTime() != null) {
                vo.setFinishTime(format.format(pro.getFinishTime()));
            }

        }
        return vo;

	}

	@Override
	@MethodRole("APP")
	public BsProductVO noviceActivityProduct(String terminal) {
        BsProductVO vo = null;
        BsProduct pro = productMapper.selectActivityAndNoviceProduct(terminal,Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(null != pro) {
            vo = new BsProductVO();
            vo.setId(pro.getId());
            vo.setName(pro.getName());
            vo.setRate(new DecimalFormat("0.00").format(pro.getBaseRate()));
            vo.setMinInvestAmount(pro.getMinInvestAmount());
            vo.setCurrTime(format.format(new Date()));
            vo.setStartTime(format.format(pro.getStartTime()));
            vo.setLeftAmount(MoneyUtil.subtract(pro.getMaxTotalAmount(), pro.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
            vo.setStatus(pro.getStatus());
            vo.setCurrTotalAmount(pro.getCurrTotalAmount());
            vo.setMaxTotalAmount(pro.getMaxTotalAmount());
            vo.setReturnType(pro.getReturnType());
			BsPropertyInfo bsPropertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(pro.getPropertyId());
			vo.setPropertySymbol(bsPropertyInfo.getPropertySymbol());
			if(Constants.PRODUCT_RETURN_TYPE_AVERAGE_CAPITAL_PLUS_INTEREST.equals(pro.getReturnType())) {
				vo.setTerm(String.valueOf(pro.getTerm()));
			} else {
				vo.setTerm(String.valueOf(pro.getTerm4Day()));
			}
            if(pro.getEndTime() != null) {
                vo.setEndTime(format.format(pro.getEndTime()));
            }
            if(pro.getFinishTime() != null) {
                vo.setFinishTime(format.format(pro.getFinishTime()));
            }

        }
        return vo;

	}

	@Override
	@MethodRole("APP")
	public List<BsProduct> selectProductList(String returnType,
			String terminal,String activityType ,Integer start,Integer offset) {
/*		Integer offset = 10;
		Integer start = (page - 1) * offset;*/
		List<BsProduct> result = productMapper.selectProductList(returnType, terminal, activityType, start, offset);
		return result;
	}

	@Override
	@MethodRole("APP")
	public Integer selectProductListCount(String returnType, String terminal,
			String activityType) {
		return  productMapper.selectProductListCount(returnType, terminal ,activityType);
	}

	@Override
	@MethodRole("APP")
	public List<BsProduct> selectProductListStatusFinish30(String returnType,
			String terminal, String activityType, Integer start, Integer offset) {
		List<BsProduct> result = productMapper.selectProductListStatusFinish30(returnType, terminal, activityType, start, offset);
		return result;
	}

	@Override
	@MethodRole("APP")
	public Integer selectProductListStatusFinish30Count(String returnType,
			String terminal, String activityType) {
		return  productMapper.selectProductListStatusFinish30Count(returnType, terminal ,activityType);
	}

	@Override
	@MethodRole("APP")
	public double queryAccumulatedInvestment() {
		return productMapper.selectAccumulatedInvestment();
	}

	@Override
	public List<BsProduct> queryProductByName(String productName) {
		BsProductExample example = new BsProductExample();
		example.createCriteria().andNameLike(productName).andStatusGreaterThanOrEqualTo(Constants.PRODUCT_STATUS_PUBLISH_NO);
		List<BsProduct> products = productMapper.selectByExample(example);
		return products;
	}

	@Override
	public List<PlayerKillingVO> queryUserInvestList(Integer productId) {
		List<PlayerKillingVO>  playerKillingVOs = bsSubAccountMapper.queryUserInvestRankingList(productId);
		return playerKillingVOs;
	}

	@Override
	public Date queryYunDaiChangeNameDate() {
		BsSysConfig sysConfig = bsSysConfigMapper.selectByPrimaryKey(Constants.YUN_DAI_CHANGE_NAME_DATE);
		Date date = DateUtil.parseDateTime(sysConfig.getConfValue());
		return date;
	}

	@Override
	public List<ProductDetailVO> suggestProductList(String terminal, String name) {
		List<ProductDetailVO> list = productMapper.selectSuggestProductList(terminal, name);
		return list;
	}

	@ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTINDEXINFOQUERY, ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTRETURNTYPE,
			ConstantsForCache.CacheKey.PRODUCTFACADE_LISTQUERY, ConstantsForCache.CacheKey.HOME_INFOQUERY, ConstantsForCache.CacheKey.PRODUCT_FINDSUGGEST})
	@Override
	public boolean openProductById(BsProduct productModify) {
		BsProductExample productExample = new BsProductExample();
		productExample.createCriteria().andIdEqualTo(productModify.getId());
		productModify.setUpdateTime(new Date());
		return productMapper.updateByExampleSelective(productModify, productExample) == 1;
	}

	@Override
	@MethodRole("APP")
	public BsProductVO queryHomeProduct(Integer userId, String terminal) {
		// 1. 限时活动标（推荐、进行中、未开始）如果没有进行中、未开始，则显示下一部分，即新手专享
		// 2. 新手专享标（推荐、进行中、未开始）如果没有进行中、未开始，则显示下一部分，即普通标的
		// 2.1 判断用户是否是新手，若不是新手，则新手专享标不显示；若是新手，则显示新手标
		// 3. 普通标的（推荐、进行中、未开始）如果没有进行中、未开始，则按发标时间顺序显示最新发布的标的
		BsProduct result;

		// 1. 限时活动标（推荐、进行中、未开始）如果没有进行中、未开始、当天已完成的计划，则显示下一部分，即新手专享
		BsProduct activityProduct = productMapper.selectHomeProduct(terminal, Constants.PRODUCT_ACTIVITY_TYPE_ACTIVITY);
		if(activityProduct == null) {
			BsProduct newProduct = productMapper.selectHomeProduct(terminal, Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER);
			if(newProduct == null) {
				BsProduct normalProduct = productMapper.selectHomeProduct(terminal, Constants.PRODUCT_ACTIVITY_TYPE_NORMAL);
				if(null != normalProduct) {
					result = normalProduct;
				} else {
					BsProduct otherProduct = productMapper.selectHomeProduct2(terminal, null);
					result = otherProduct;
				}
			} else {
				// 查看用户是否是新手
				if(null != userId) {
					BsUser user = bsUserMapper.selectByPrimaryKey(userId);
					if(user == null) {
						result = newProduct;
					} else if(user.getFirstBuyTime() == null) {
						result = newProduct;
					} else {
						BsProduct normalProduct = productMapper.selectHomeProduct(terminal, Constants.PRODUCT_ACTIVITY_TYPE_NORMAL);
						if(normalProduct == null) {
							BsProduct otherProduct = productMapper.selectHomeProduct2(terminal, Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER);
							result = otherProduct;
						} else {
							result = normalProduct;
						}
					}
				} else {
					result = newProduct;
				}
			}
		} else {
			result = activityProduct;
		}
		return convert2ProductVO(result);
	}

	/**
	 * 查询首页展示活动标
	 *
	 * @param terminal
	 * @return
	 */
	@Override
	public BsProductVO queryHomeActivityProduct(String terminal) {
		BsProduct activityProduct = productMapper.selectHomeProduct(terminal, Constants.PRODUCT_ACTIVITY_TYPE_ACTIVITY);
		return convert2ProductVO(activityProduct);
	}

	/**
	 * 查询首页展示新手标
	 *
	 * @param terminal
	 * @return
	 */
	@Override
	public BsProductVO queryHomeNewBuyerProduct(String terminal) {
		BsProduct newProduct = productMapper.selectHomeProduct(terminal, Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER);
		return convert2ProductVO(newProduct);
	}

	/**
	 * 查询首页展示普通标
	 *
	 * @param terminal
	 * @return
	 */
	@Override
	public BsProductVO queryHomeNormalProduct(String terminal) {
		BsProduct normalProduct = productMapper.selectHomeProduct(terminal, Constants.PRODUCT_ACTIVITY_TYPE_NORMAL);
		if(normalProduct == null) {
			BsProduct otherProduct = productMapper.selectHomeProduct2(terminal, null);
			return convert2ProductVO(otherProduct);
		}
		return convert2ProductVO(normalProduct);
	}

	private BsProductVO convert2ProductVO(BsProduct result){
		BsProductVO vo = null;
		if(result != null) {
			vo = new BsProductVO();
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			BsPropertyInfo propertyInfo = bsPropertyInfoMapper.selectByPrimaryKey(result.getPropertyId());
			vo.setId(result.getId());
			vo.setDayTerm(String.valueOf(result.getTerm()));
			if(Constants.PRODUCT_PROPERTY_SYMBOL_ZAN.equals(propertyInfo.getPropertySymbol())) {
				vo.setTerm(String.valueOf(result.getTerm()));
			} else {
				vo.setTerm(String.valueOf(result.getTerm4Day()));
			}
			vo.setName(result.getName());
			vo.setRate(new DecimalFormat("0.00").format(result.getBaseRate()));
			vo.setMinInvestAmount(result.getMinInvestAmount());
			vo.setCurrTime(format.format(new Date()));
			vo.setStartTime(format.format(result.getStartTime()));
			vo.setLeftAmount(MoneyUtil.subtract(result.getMaxTotalAmount(), result.getCurrTotalAmount()).setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());

			vo.setStatus(result==null?0:result.getStatus());
			vo.setIsSuggest(result==null?"NO":result.getIsSuggest());
			vo.setCurrTotalAmount(result.getCurrTotalAmount());
			vo.setMaxTotalAmount(result.getMaxTotalAmount());
			vo.setMaxSingleInvestAmount(result.getMaxSingleInvestAmount());
			vo.setPropertySymbol(propertyInfo.getPropertySymbol());
			if(result.getEndTime() != null) {
				vo.setEndTime(format.format(result.getEndTime()));
			}
			if(result.getFinishTime() != null) {
				vo.setFinishTime(format.format(result.getFinishTime()));
			}
			vo.setIsSupportRedPacket(result.getIsSupportRedPacket());
			vo.setPropertyType(result.getPropertyType());
			vo.setActivityType(result.getActivityType());
		}
		return vo;
	}

	boolean isSameProduct(List<Map<String, Object>> list, Map<String, Object> pro) {
		for (Map<String, Object> map : list) {
			Integer id = (Integer) map.get("id");
			Integer newId = (Integer) pro.get("id");
			if(newId.equals(id)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void queryHomeProductPC(ReqMsg_Product_ListQuery req, ResMsg_Product_ListQuery res) {
		List<Map<String, Object>> newProList = new ArrayList<>();
		this.listQuery(req, res);
		List<Map<String,Object>> oldProductList = res.getProductLst();

		List<Map<String,Object>> newProductList = new ArrayList<>();
		if(!CollectionUtils.isEmpty(oldProductList)) {
			ReqMsg_Product_ListQuery reqActivity = new ReqMsg_Product_ListQuery();
			reqActivity.setNumPerPage(5);
			reqActivity.setStart(req.getStart());
			reqActivity.setUserType(req.getUserType());
			reqActivity.setActivityType(null);
			reqActivity.setProductShowTerminal(req.getProductShowTerminal());
			reqActivity.setReturnType(req.getReturnType());
			ResMsg_Product_ListQuery resActivity = new ResMsg_Product_ListQuery();
			this.listQuery(reqActivity, resActivity);

			List<Map<String,Object>> activityProductList = resActivity.getProductLst();


			for (Map<String, Object> old: oldProductList) {
				if(!CollectionUtils.isEmpty(activityProductList)) {
					Integer oldStatus = (Integer) old.get("status");
					String oldIsSuggest = (String) old.get("isSuggest");
					for (Map<String, Object> activity: activityProductList) {
						Integer activityStatus = (Integer) activity.get("status");
						String activityIsSuggest = (String) activity.get("isSuggest");
						if(Constants.PRODUCT_IS_SUGGEST_YES.equals(oldIsSuggest)
								&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(oldStatus) || Constants.PRODUCT_STATUS_OPENING.equals(oldStatus))) {
							// 推荐标（状态是进行中、未开放）
							if(Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest)
									&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(activityStatus) || Constants.PRODUCT_STATUS_OPENING.equals(activityStatus))) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else {
								if(!newProductList.contains(old) && !isSameProduct(newProductList, old)) {
									newProductList.add(old);
								}
							}
						} else if(!Constants.PRODUCT_IS_SUGGEST_YES.equals(oldIsSuggest)
								&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(oldStatus) || Constants.PRODUCT_STATUS_OPENING.equals(oldStatus))) {
							// 非推荐标（状态是进行中、未开放）
							if(Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest)
									&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(activityStatus) || Constants.PRODUCT_STATUS_OPENING.equals(activityStatus))) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else if(!Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest)
									&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(activityStatus) || Constants.PRODUCT_STATUS_OPENING.equals(activityStatus))) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else {
								if(!newProductList.contains(old) && !isSameProduct(newProductList, old)) {
									newProductList.add(old);
								}
							}
						} else if(Constants.PRODUCT_IS_SUGGEST_YES.equals(oldIsSuggest) && Constants.PRODUCT_STATUS_FINISH.equals(oldStatus)) {
							// 推荐标（状态是已完成）
							if(Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest)
									&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(activityStatus) || Constants.PRODUCT_STATUS_OPENING.equals(activityStatus))) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else if(!Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest)
									&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(activityStatus) || Constants.PRODUCT_STATUS_OPENING.equals(activityStatus))) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else if(Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest) && Constants.PRODUCT_STATUS_FINISH.equals(oldStatus)) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else if(!Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest) && Constants.PRODUCT_STATUS_FINISH.equals(oldStatus)) {
								if(!newProductList.contains(old) && !isSameProduct(newProductList, old)) {
									newProductList.add(old);
								}
							}
						} else if(Constants.PRODUCT_IS_SUGGEST_NO.equals(oldIsSuggest) && Constants.PRODUCT_STATUS_FINISH.equals(oldStatus)) {
							// 非推荐标（状态是已完成）
							if(Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest)
									&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(activityStatus) || Constants.PRODUCT_STATUS_OPENING.equals(activityStatus))) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else if(!Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest)
									&& (Constants.PRODUCT_STATUS_PUBLISH_YES.equals(activityStatus) || Constants.PRODUCT_STATUS_OPENING.equals(activityStatus))) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else if(Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest) && Constants.PRODUCT_STATUS_FINISH.equals(oldStatus)) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							} else if(!Constants.PRODUCT_IS_SUGGEST_YES.equals(activityIsSuggest) && Constants.PRODUCT_STATUS_FINISH.equals(oldStatus)) {
								if(!newProductList.contains(activity) && !isSameProduct(newProductList, activity)) {
									newProductList.add(activity);
								}
							}
						}
					}
				}
			}

			if(CollectionUtils.isEmpty(activityProductList)) {
				newProList = oldProductList;
			} else {
				for (int i = 0; i < newProductList.size(); i++) {
					if(i < 5) {
						newProList.add(newProductList.get(i));
					} else if(i >= 5) {
						break;
					}
				}
			}
		}
		res.setProductLst(newProList);
	}

	@Override
	public BsProduct sumCurrTotalAmountAndInvestNum() {
		return productMapper.sumCurrTotalAmountAndInvestNum();
	}

	@Override
	public BsProduct selectById(Integer productId) {
		return productMapper.selectByPrimaryKey(productId);
	}
}
