package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import com.pinting.business.aspect.cache.ClearRedisCache;
import com.pinting.business.aspect.cache.ConstantsForCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallback;
import org.springframework.transaction.support.TransactionTemplate;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.dao.BsProductSubAccountPrefixMapper;
import com.pinting.business.dao.BsUserTypeAuthMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductExample;
import com.pinting.business.model.BsProductSubAccountPrefixExample;
import com.pinting.business.model.BsProductSubAccountPrefixKey;
import com.pinting.business.model.BsUserTypeAuth;
import com.pinting.business.model.vo.ProductReleaseInfoVO;
import com.pinting.business.service.manage.ProductReleaseService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

/**
 * 理财计划发布服务
 * @author HuanXiong
 * @version $Id: ProductReleaseServiceImpl.java, v 0.1 2016-4-21 上午11:30:42 HuanXiong Exp $
 */
@Service
public class ProductReleaseServiceImpl implements ProductReleaseService {
    
    @Autowired
    private BsProductMapper bsProductMapper;
    @Autowired
    private BsProductSubAccountPrefixMapper bsProductSubAccountPrefixMapper;
    @Autowired
    private BsUserTypeAuthMapper bsUserTypeAuthMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;

    /** 
     * @see com.pinting.business.service.manage.ProductReleaseService#queryReleaseProductGrid(com.pinting.business.model.vo.ProductReleaseInfoVO)
     */
    @Override
    public List<ProductReleaseInfoVO> queryReleaseProductGrid(ProductReleaseInfoVO req) {
        return bsProductMapper.selectReleaseProductGrid(req);
    }

    /** 
     * @see com.pinting.business.service.manage.ProductReleaseService#countReleaseProductGrid(com.pinting.business.model.vo.ProductReleaseInfoVO)
     */
    @Override
    public Integer countReleaseProductGrid(ProductReleaseInfoVO req) {
        return bsProductMapper.countReleaseProductGrid(req);
    }

    /** 
     * @see com.pinting.business.service.manage.ProductReleaseService#releaseProduct(java.lang.Integer)
     */
    @ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTINDEXINFOQUERY, ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTRETURNTYPE, 
    		ConstantsForCache.CacheKey.PRODUCTFACADE_LISTQUERY, ConstantsForCache.CacheKey.HOME_INFOQUERY, ConstantsForCache.CacheKey.PRODUCT_FINDSUGGEST})
    @Override
    public synchronized void releaseProduct(final Integer productId, final Integer opId, final String startTime, final String endTime) {
        // 校验数据
        if(null == productId) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        // 校验数据
        // 检查是否符合发布条件开始
        BsProduct product = bsProductMapper.selectByPrimaryKey(productId);
        if(!Constants.PRODUCT_STATUS_PUBLISH_NO.equals(product.getStatus())) {
            throw new PTMessageException(PTMessageEnum.PRODUCT_IS_CAN_NOT_RELEASE);
        }
        if(null != product.getEndTime() && null != product.getStartTime()) {
            if(com.pinting.business.util.DateUtil.compareTo(product.getStartTime(), product.getEndTime()) >= 0) {
                throw new PTMessageException(PTMessageEnum.PRODUCT_IS_CAN_NOT_RELEASE);
            }
        }
        // 检查是否符合发布条件结束
        
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                // bs_product 更新 status 5-已发布（已经手工发布）, 发布人，期数
//                BsProduct bsProduct = bsProductMapper.selectByPrimaryKey(productId);
                BsProduct bsProduct = bsProductMapper.selectByIdLock(productId);
                bsProduct.setStatus(Constants.PRODUCT_STATUS_PUBLISH_YES);
                bsProduct.setDistributor(opId);
                bsProduct.setDistributeTime(new Date());
                if(!StringUtil.isBlank(startTime)) {
                    bsProduct.setStartTime(DateUtil.parse(startTime, "yyyy-MM-dd HH:mm:ss"));
                }
                if(!StringUtil.isBlank(endTime)) {
                    bsProduct.setEndTime(DateUtil.parse(endTime, "yyyy-MM-dd HH:mm:ss"));
                }
                
                // 系列编号为空即自定义理财计划，不需要加上期数
                if(null == bsProduct.getSerialId()) {
                } else {
                    BsProductExample bsProductExample = new BsProductExample();
                    bsProductExample.createCriteria().andSerialIdEqualTo(bsProduct.getSerialId());
                    bsProductExample.setOrderByClause("serial_order desc");
                    List<BsProduct> bsProducts = bsProductMapper.selectByExample(bsProductExample);
                    if (!CollectionUtils.isEmpty(bsProducts)) {
                        bsProduct.setSerialOrder((null == bsProducts.get(0).getSerialOrder() ? 0 : bsProducts.get(0).getSerialOrder()) + 1);
                        if(bsProduct.getSerialOrder() < 10) {
                            bsProduct.setName(bsProduct.getName() + "000" + bsProduct.getSerialOrder() + "期");
                        } else if(bsProduct.getSerialOrder() >= 10 && bsProduct.getSerialOrder() < 100) {
                            bsProduct.setName(bsProduct.getName() + "00" + bsProduct.getSerialOrder() + "期");
                        } else if(bsProduct.getSerialOrder() >= 100 && bsProduct.getSerialOrder() < 1000) {
                            bsProduct.setName(bsProduct.getName() + "0" + bsProduct.getSerialOrder() + "期");
                        } else {
                            bsProduct.setName(bsProduct.getName() + "" + bsProduct.getSerialOrder() + "期");
                        }
                    } else {
                        bsProduct.setSerialOrder(1);
                        bsProduct.setName(bsProduct.getName() + "0001期");
                    }
                }
                bsProduct.setUpdateTime(new Date());
                bsProductMapper.updateByPrimaryKeySelective(bsProduct);
                
                // bs_product_sub_account_prefix 插入
                BsProductSubAccountPrefixKey accountPrefixKey = new BsProductSubAccountPrefixKey();
                BsProductSubAccountPrefixExample accountPrefixExample = new BsProductSubAccountPrefixExample();
                accountPrefixExample.setOrderByClause("sub_account_prefix desc");
                List<BsProductSubAccountPrefixKey> accountPrefixKeys = bsProductSubAccountPrefixMapper.selectByExample(accountPrefixExample);
                // 为空的情况不作考虑
                Integer subAccountPrefix = Integer.valueOf(accountPrefixKeys.get(0).getSubAccountPrefix());
                subAccountPrefix = subAccountPrefix + 1;
                accountPrefixKey.setSubAccountPrefix(String.valueOf(subAccountPrefix));
                accountPrefixKey.setProductId(productId);
                bsProductSubAccountPrefixMapper.insertSelective(accountPrefixKey);
                
                // bs_user_type_auth 插入
                BsUserTypeAuth bsUserTypeAuth = new BsUserTypeAuth();
                bsUserTypeAuth.setProductId(productId);
                bsUserTypeAuth.setUserType(Constants.USER_TYPE_NORMAL);
                bsUserTypeAuthMapper.insertSelective(bsUserTypeAuth);
                return true;
            }
        });
    }

    /** 
     * @see com.pinting.business.service.manage.ProductReleaseService#finishProduct(java.lang.Integer)
     */
    @ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTINDEXINFOQUERY, ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTRETURNTYPE,
    		ConstantsForCache.CacheKey.PRODUCTFACADE_LISTQUERY, ConstantsForCache.CacheKey.HOME_INFOQUERY, ConstantsForCache.CacheKey.PRODUCT_FINDSUGGEST})
    @Override
    public void finishProduct(final Integer productId, final Integer opId) {
        // 校验数据
        if(null == productId) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        // 校验数据
        
        // 检查是否符合结束条件开始
        BsProduct product = bsProductMapper.selectByPrimaryKey(productId);
        if(!Constants.PRODUCT_STATUS_PUBLISH_YES.equals(product.getStatus()) && !Constants.PRODUCT_STATUS_OPENING.equals(product.getStatus())) {
            throw new PTMessageException(PTMessageEnum.PRODUCT_IS_CAN_NOT_FINISH);
        }
        // 检查是否符合结束条件结束
        
        transactionTemplate.execute(new TransactionCallback<Boolean>() {
            @Override
            public Boolean doInTransaction(TransactionStatus status) {
                // bs_product 更新 status  8.已完成
                BsProduct bsProduct = new BsProduct();
                bsProduct.setId(productId);
                bsProduct.setStatus(Constants.PRODUCT_STATUS_FINISH);
                bsProduct.setFinishTime(new Date());
                bsProduct.setTerminator(opId);
                bsProduct.setUpdateTime(new Date());
                bsProductMapper.updateByPrimaryKeySelective(bsProduct);
                
                // bs_user_type_auth 删除 前端查询理财计划列表需要关联查询
                //BsUserTypeAuthExample authExample = new BsUserTypeAuthExample();
                //authExample.createCriteria().andProductIdEqualTo(productId);
                //bsUserTypeAuthMapper.deleteByExample(authExample);
                return true;
            }
        });
    }
    
    /** 
     * @see com.pinting.business.service.manage.ProductReleaseService#recommendProduct(java.lang.Integer)
     */
    @ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTINDEXINFOQUERY, ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTRETURNTYPE, 
    		ConstantsForCache.CacheKey.PRODUCTFACADE_LISTQUERY, ConstantsForCache.CacheKey.HOME_INFOQUERY, ConstantsForCache.CacheKey.PRODUCT_FINDSUGGEST})
    @Override
    public void recommendProduct(Integer productId, Integer opId) {
        confirmOrCancelRecommend(productId, opId, Constants.PRODUCT_IS_SUGGEST_YES);
    }

    /** 
     * @see com.pinting.business.service.manage.ProductReleaseService#cancelRecommendProduct(java.lang.Integer)
     */
    @ClearRedisCache(clearKey = {ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTINDEXINFOQUERY, ConstantsForCache.CacheKey.APPPRODUCTFACADE_LISTRETURNTYPE,
    		ConstantsForCache.CacheKey.PRODUCTFACADE_LISTQUERY, ConstantsForCache.CacheKey.HOME_INFOQUERY, ConstantsForCache.CacheKey.PRODUCT_FINDSUGGEST})
    @Override
    public void cancelRecommendProduct(Integer productId, Integer opId) {
        confirmOrCancelRecommend(productId, opId, Constants.PRODUCT_IS_SUGGEST_NO);
    }
    
    /**
     * 是否推荐理财计划
     * @param productId 产品ID
     * @param opId  操作员ID
     * @param isSuggest 推荐： YES；取消推荐： NO
     */
    private void confirmOrCancelRecommend(Integer productId, Integer opId, String isSuggest) {
        // 校验数据
        if(null == productId) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        // 校验数据
        
        // 检查是否符合推荐||取消推荐 条件开始
        BsProduct product = bsProductMapper.selectByPrimaryKey(productId);
        if(Constants.PRODUCT_IS_SUGGEST_YES.equals(isSuggest)) {
            if(!Constants.PRODUCT_STATUS_PUBLISH_YES.equals(product.getStatus()) && !Constants.PRODUCT_STATUS_OPENING.equals(product.getStatus())) {
                throw new PTMessageException(PTMessageEnum.PRODUCT_IS_CAN_NOT_RECOMMEND);
            }
        } else {
            if(!Constants.PRODUCT_STATUS_PUBLISH_YES.equals(product.getStatus()) && !Constants.PRODUCT_STATUS_OPENING.equals(product.getStatus())) {
                throw new PTMessageException(PTMessageEnum.PRODUCT_IS_CAN_NOT_CANCLE_RECOMMEND);
            }
        }
        // 检查是否符合推荐||取消推荐 条件结束
        
        BsProduct bsProduct = new BsProduct();
        bsProduct.setId(productId);
        bsProduct.setIsSuggest(isSuggest);
        bsProduct.setUpdateTime(new Date());
        bsProductMapper.updateByPrimaryKeySelective(bsProduct);
    }

}
