package com.pinting.business.facade.site;

import com.pinting.business.aspect.cache.ConstantsForCache;
import com.pinting.business.aspect.cache.RedisCache;
import com.pinting.business.hessian.site.message.*;
import com.pinting.business.model.BsOperationReport;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.BsProductTagContentVO;
import com.pinting.business.model.vo.BsProductVO;
import com.pinting.business.model.vo.BsStatisticsVO;
import com.pinting.business.service.manage.BsActivityLuckyDrawService;
import com.pinting.business.service.site.*;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;
import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component("Home")
public class HomeFacade{
	
	
	@Autowired
	private ProductService productService;
	
	@Autowired
	private SubAccountService subAccountService;
	
	@Autowired
	private SysConfigService sysConfigService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RedPacketService redPacketService;
	
	@Autowired
    private InvestService     investService;
	
	@Autowired
	private SystemService systemService;
	
	@Autowired
	private BsOperationReportService bsOperationReportService;

    @Autowired
    private BsActivityLuckyDrawService bsActivityLuckyDrawService;

    @Autowired
    private ProductTagService productTagService;
	
	/**
	 * 根据不同显示端口查询币港湾首页所需数据
	 * @param req
	 * @param res
	 */
    @RedisCache(serviceName = "homeInfoQueryCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.HOME_INFOQUERY)
	public ResMsg_Home_InfoQuery infoQuery(ReqMsg_Home_InfoQuery req, ResMsg_Home_InfoQuery res){
        NumberTool tool = new NumberTool();
	    // 累计投资金额
	    Double totalInvestAmount = 0d;
		totalInvestAmount = productService.queryAccumulatedInvestment();
	    res.setTotalInvestAmount(tool.format("0.00", totalInvestAmount));

        if(Constants.PRODUCT_SHOW_TERMINAL_H5.equals(req.getProductShowTerminal())) {
            // H5 推荐的产品列表 3个，分别为活动标、新手标、普通标各一个
            BsProductVO productActivity = productService.queryHomeActivityProduct(req.getProductShowTerminal());
            BsProductVO productNew = productService.queryHomeNewBuyerProduct(req.getProductShowTerminal());
            BsProductVO productNormal = productService.queryHomeNormalProduct(req.getProductShowTerminal());
            ArrayList<HashMap<String, Object>> dataGrid = new ArrayList<>();
            HashMap<String, Object> productActivityMap = BeanUtils.transBeanMap(productActivity);
            HashMap<String, Object> productNewMap = BeanUtils.transBeanMap(productNew);
            HashMap<String, Object> productNormalMap = BeanUtils.transBeanMap(productNormal);

            // 普通产品添加加息标签属性
            if(null != productNormalMap && productNormalMap.size() > 0) {
                BsProductTagContentVO productTagContentVO = productTagService.queryProductTagContentById((Integer) productNormalMap.get("id"));
                if(null != productTagContentVO) {
                    productNormalMap.put("interestRatesTagContent", StringUtil.isNotBlank(productTagContentVO.getInterestRatesTagContent()) ? productTagContentVO.getInterestRatesTagContent() : "");
                }
            }
            dataGrid.add(productActivityMap);
            dataGrid.add(productNewMap);
            dataGrid.add(productNormalMap);
            res.setRecommendProductList(dataGrid);
        } else if(Constants.PRODUCT_SHOW_TERMINAL_PC.equals(req.getProductShowTerminal())) {
            // PC 产品列表
            // 新手推荐产品（一个）
            BsProductVO productVO = productService.noviceRecommendedProduct(req.getProductShowTerminal());
            // 首页四个产品，非新手推荐产品
            ReqMsg_Product_ListQuery reqPro = new ReqMsg_Product_ListQuery();
            ResMsg_Product_ListQuery resPro = new ResMsg_Product_ListQuery();
            reqPro.setPageNum(1);
            reqPro.setNumPerPage(Constants.INDEX_PRODUCT_NUM_PER_PAGE);
            reqPro.setUserType("NORMAL");
            reqPro.setProductShowTerminal(req.getProductShowTerminal());
            reqPro.setActivityType(Constants.PRODUCT_ACTIVITY_TYPE_NORMAL);
            productService.queryHomeProductPC(reqPro, resPro);
            List<Map<String, Object>> dataGrid = resPro.getProductLst();
            ArrayList<HashMap<String, Object>> pros = new ArrayList<HashMap<String, Object>>();
            for (Iterator<Map<String, Object>> iterator = dataGrid.iterator(); iterator.hasNext();) {
                HashMap<String, Object> pro = (HashMap<String, Object>) iterator.next();
                // 剔除新手推荐产品
                if(productVO.getId().equals((Integer) pro.get("id"))) {
                    iterator.remove();
                }
            }
            int index = 0;
            for (Iterator<Map<String, Object>> iterator = dataGrid.iterator(); iterator.hasNext();) {
                HashMap<String, Object> hashMap = (HashMap<String, Object>) iterator.next();
                if(index >= (Constants.INDEX_PRODUCT_NUM_PER_PAGE-1)) {
                    break;
                } else {
                    pros.add(hashMap);
                }
                index++;
            }
            res.setRecommendProductList(pros);
            if(null == productVO) {
                res.setNoviceRecommendedProduct(null);
            } else {
                res.setNoviceRecommendedProduct(BeanUtils.classToHashMap(productVO));
            }
        }
        // PC_178 产品列表
        else if(Constants.PRODUCT_SHOW_TERMINAL_PC_178.equals(req.getProductShowTerminal())
                || Constants.PRODUCT_SHOW_TERMINAL_PC_KQ.equals(req.getProductShowTerminal())
                || Constants.PRODUCT_SHOW_TERMINAL_PC_HN.equals(req.getProductShowTerminal())
                || Constants.PRODUCT_SHOW_TERMINAL_PC_RUIAN.equals(req.getProductShowTerminal())
                || Constants.PRODUCT_SHOW_TERMINAL_PC_QHD_ST.equals(req.getProductShowTerminal())) {
            // 首页所有未完成的产品
            ReqMsg_Product_ListQuery reqPro = new ReqMsg_Product_ListQuery();
            ResMsg_Product_ListQuery resPro = new ResMsg_Product_ListQuery();
            reqPro.setNumPerPage(8);
            reqPro.setStatus(-1);   // 未完成的产品
            reqPro.setUserType("NORMAL");
            reqPro.setProductShowTerminal(req.getProductShowTerminal());
            productService.listQuery(reqPro, resPro);
            List<Map<String, Object>> dataGrid = resPro.getProductLst();
            ArrayList<HashMap<String, Object>> pros = new ArrayList<HashMap<String, Object>>();
            for (Iterator iterator = dataGrid.iterator(); iterator.hasNext();) {
                HashMap<String, Object> map = (HashMap<String, Object>) iterator.next();
                pros.add(map);
            }
            res.setRecommendProductList(pros);
        }

        // 查询前六个运营报告信息
        if(!Constants.CHANNEL_H5.equals(req.getTerminal())) {
            List<BsOperationReport> report = bsOperationReportService.querySixOperationReport();
            res.setReportList(BeanUtils.classToArrayList(report));

            res.setTotalIncome(userService.countUserIncome());
            res.setTotalRegUser(userService.countRegNum());
        }
        return res;
	}
	
	public void buyOrders(ReqMsg_Home_BuyOrders req, ResMsg_Home_BuyOrders res){
		
		BsSysConfig sysConfig = sysConfigService.findViewPageNum();
		int pageNum = Integer.parseInt(sysConfig.getConfValue());  	
		
		List<BsStatisticsVO> valueList =  subAccountService.findUserBuyOrdersList(req.getProductId(),0,pageNum);
		ArrayList<HashMap<String,Object>> buyMessageList = BeanUtils.classToArrayList(valueList);
		
		res.setUserBuyOrdersList(buyMessageList);
		
	}
	
	public void noviceStandardPlan(ReqMsg_Home_NoviceStandardPlan req, ResMsg_Home_NoviceStandardPlan res){
		BsProductVO productVO = productService.noviceExclusivePlan(req.getProductShowTerminal());
		if(null == productVO) {
            res.setNoviceStandardProduct(null);
        } else {
            res.setNoviceStandardProduct(BeanUtils.classToHashMap(productVO));
        }
		Double totalAmount = null;
        if(Constants.PRODUCT_SHOW_TERMINAL_H5_178.equals(req.getProductShowTerminal()) || Constants.PRODUCT_SHOW_TERMINAL_PC_178.equals(req.getProductShowTerminal())) {
            totalAmount = redPacketService.autoRedPacketTotalAmount("NEW_USER", "178");
        } else {
            totalAmount = redPacketService.autoRedPacketTotalAmount("NEW_USER", null);
        }
        // 新手红包金额
		res.setTotalRedPacketSubtract(totalAmount == null ? 0d : totalAmount);
	}
	
	/**
	 * 服务可用测试（用户ng调用url测试服务是否可用）
	 * @param req
	 * @param res
	 */
	public void serverUsableCheck(ReqMsg_Home_ServerUsableCheck req, ResMsg_Home_ServerUsableCheck res){
		systemService.serverUsableCheck();
	}

    /**
     * 红包查询
     * @param req
     * @param res
     */
    @RedisCache(serviceName = "homeRedPacketAmountCacheImpl", redisCacheKey = ConstantsForCache.CacheKey.HOME_REDPACKETAMOUNT)
    public ResMsg_Home_RedPacketAmount redPacketAmount(ReqMsg_Home_RedPacketAmount req, ResMsg_Home_RedPacketAmount res) {
        Double totalAmount = null;
        if(Constants.PRODUCT_SHOW_TERMINAL_H5.equals(req.getProductShowTerminal()) || Constants.PRODUCT_SHOW_TERMINAL_PC.equals(req.getProductShowTerminal())) {
            totalAmount = redPacketService.autoRedPacketTotalAmount("NEW_USER", null);
        } else {
            totalAmount = redPacketService.autoRedPacketTotalAmount("NEW_USER", "178");
        }
        res.setTotalRedPacketSubtract(totalAmount == null ? 0d : totalAmount);

        return res;
    }
}
