package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.hessian.manage.message.ResMsg_ProductPlanCheck_PlanDetail;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsProductExample;
import com.pinting.business.model.BsProductSerial;
import com.pinting.business.model.BsProductSerialExample;
import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.model.BsPropertyInfoExample;
import com.pinting.business.model.BsTermProductCode;
import com.pinting.business.model.BsTermProductCodeExample;
import com.pinting.business.model.vo.MProductVO;
import com.pinting.business.service.manage.MProductSerialService;
import com.pinting.business.service.manage.MProductService;
import com.pinting.business.service.manage.MPropertyInfoService;
import com.pinting.business.service.manage.MTermProductCodeService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.EditorUtil;
import com.pinting.util.ReturnDWZAjax;
/**
 * 
 * @author yanwl
 * @version $Id: ProductController.java, v 0.1 2016-4-21 下午16:20:41 yanwl Exp $
 */
@Controller
@RequestMapping("/product/manage")
public class ProductController {

    @Autowired
    private MProductService productService;
    @Autowired
    private MProductSerialService productSerialService;
    @Autowired
    private MPropertyInfoService propertyInfoService;
    @Autowired
    private MTermProductCodeService termProductCodeService;
    @Autowired
    @Qualifier("dispatchService")
    public HessianService planCheckService;
    
	/**
	 * 进入理财计划管理页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/index")
	public String index(MProductVO productVo,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		//展示终端
		if(StringUtil.isNotEmpty(productVo.getsShowTerminal())) {
			String[] terminals = productVo.getsShowTerminal().split(",");
			if(terminals.length > 0) {
				List<Object> showTerminalList = new ArrayList<Object>();
				for (String str : terminals) {
					if(StringUtil.isNotEmpty(str.trim())) {
						showTerminalList.add(str.trim());
					}
				}
				productVo.setShowTerminalList(showTerminalList);
			}
		}
		if(productVo.getNumPerPage() <= 0) {
			productVo.setNumPerPage(100);
		}
		//查询理财计划列表
		int totalRows = productService.findMProductVOsCount(productVo);
		if(totalRows > 0) {
			List<MProductVO> products = productService.findMProductVOsByPage(productVo);
			model.put("products", products);
		}
		model.put("totalRows", totalRows);
		//查询理财计划期数
		List<BsProduct> terms = productService.findAllProductTerm();
		model.put("terms", terms);
		//查询理财计划利率
		List<BsProduct> rates = productService.findAllProductBaseRate();
		model.put("rates", rates);
		//查询产品系列列表
		BsProductSerialExample example = new BsProductSerialExample();
		example.setOrderByClause("update_time desc");
		List<BsProductSerial> serials = productSerialService.findBsProductSerials(example);
		model.put("serials", serials);
		model.put("req", productVo);
		if (productVo.getOrderField() != null && productVo.getOrderDirection() != null) {
			model.put(productVo.getOrderField(), productVo.getOrderDirection());
		}
		return "product/manage/product_index";
	}
	
	/**
	 * 进入理财计划新增或者编辑页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetail")
	public String productDetail(String pId,Map<String, Object> model) {
		try {
    		if(StringUtil.isNotEmpty(pId)) {
        		BsProduct product = productService.findBsProductById(Integer.valueOf(pId));
				BsPropertyInfo propertyInfo = propertyInfoService.selectByPrimaryId(product.getPropertyId());
        		if(product.getSerialId() == null) {
        			product.setSerialId(-1);
        		}
				model.put("propertySymbol", propertyInfo.getPropertySymbol());
        		model.put("product", product);
        	}
    		//查询产品系列列表
    		BsProductSerialExample serialexample = new BsProductSerialExample();
    		serialexample.setOrderByClause("update_time desc");
    		List<BsProductSerial> serials = productSerialService.findBsProductSerials(serialexample);
    		model.put("serials", serials);
    		//查询资产合作方信息列表
    		BsPropertyInfoExample propertyExample = new BsPropertyInfoExample();
    		propertyExample.setOrderByClause("update_time desc");
    		List<BsPropertyInfo> propertyInfos = propertyInfoService.findBsPropertyInfos(propertyExample);
    		model.put("propertyInfos", propertyInfos);
    		//查询期限产品编码列表
    		BsTermProductCodeExample example = new BsTermProductCodeExample();
    		example.setOrderByClause("term asc");
    		List<BsTermProductCode> codes = termProductCodeService.findBsTermProductCodes(example);
    		model.put("codes", codes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product/manage/product_detail";
	}
	
	/**
	 * 进入理财计划克隆页面
	 * @param pCloneId
	 * @param model
	 * @return
	 */
	@RequestMapping("/productClone")
	public String productClone(String pCloneId,Map<String, Object> model) {
		//克隆页面标志ID
		model.put("cloneId","cloneId");
		try {
    		if(StringUtil.isNotEmpty(pCloneId)) {
        		BsProduct product = productService.findBsProductById(Integer.valueOf(pCloneId));
				BsPropertyInfo propertyInfo = propertyInfoService.selectByPrimaryId(product.getPropertyId());
        		if(product.getSerialId() == null) {
        			product.setSerialId(-1);
        		}
        		model.put("product", product);
				model.put("propertySymbol", propertyInfo.getPropertySymbol());
        	}
    		//查询产品系列列表
    		BsProductSerialExample serialexample = new BsProductSerialExample();
    		serialexample.setOrderByClause("update_time desc");
    		List<BsProductSerial> serials = productSerialService.findBsProductSerials(serialexample);
    		model.put("serials", serials);
    		//查询资产合作方信息列表
    		BsPropertyInfoExample propertyExample = new BsPropertyInfoExample();
    		propertyExample.setOrderByClause("update_time desc");
    		List<BsPropertyInfo> propertyInfos = propertyInfoService.findBsPropertyInfos(propertyExample);
    		model.put("propertyInfos", propertyInfos);
    		//查询期限产品编码列表
    		BsTermProductCodeExample example = new BsTermProductCodeExample();
    		example.setOrderByClause("term asc");
    		List<BsTermProductCode> codes = termProductCodeService.findBsTermProductCodes(example);
    		model.put("codes", codes);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "product/manage/product_clone";
	}
	
	/**
	 * 进入理财计划详情页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/productDetailPage")
	public String productDetailPage(ReqMsg_ProductPlanCheck_PlanDetail req,Map<String, Object> model) {
		String resources = GlobEnvUtil.get("news.resources");
        String manageWeb = GlobEnvUtil.get("server.web");
        String web = GlobEnvUtil.get("news.resources");
        ResMsg_ProductPlanCheck_PlanDetail res = (ResMsg_ProductPlanCheck_PlanDetail) planCheckService.handleMsg(req);
        res.getProductDatas().put("note", EditorUtil.replace((String)res.getProductDatas().get("note"), resources, manageWeb, web));
        res.getProductDatas().put("propertySummary", EditorUtil.replace((String)res.getProductDatas().get("propertySummary"), resources, manageWeb, web));
        res.getProductDatas().put("returnSource", EditorUtil.replace((String)res.getProductDatas().get("returnSource"), resources, manageWeb, web));
        res.getProductDatas().put("fundSecurity", EditorUtil.replace((String)res.getProductDatas().get("fundSecurity"), resources, manageWeb, web));
        res.getProductDatas().put("orgnizeCheck", EditorUtil.replace((String)res.getProductDatas().get("orgnizeCheck"), resources, manageWeb, web));
        res.getProductDatas().put("ratingGrade", EditorUtil.replace((String)res.getProductDatas().get("ratingGrade"), resources, manageWeb, web));
        
        if (res.getProductDatas().get("coopProtocolPics") != null) {
            String[] coopProtocolImgs = ((String)res.getProductDatas().get("coopProtocolPics")).split(";");
            model.put("coopProtocolImgs", coopProtocolImgs);
        }
        if (res.getProductDatas().get("loanProtocolPics") != null) {
            String[] loanProtocolImgs = ((String)res.getProductDatas().get("loanProtocolPics")).split(";");
            model.put("loanProtocolImgs", loanProtocolImgs);
        }
        if (res.getProductDatas().get("orgnizeCheckPics") != null) {
            String[] orgnizeCheckImgs = ((String)res.getProductDatas().get("orgnizeCheckPics")).split(";");
            model.put("orgnizeCheckImgs", orgnizeCheckImgs);
        }
        if (res.getProductDatas().get("ratingGradePics") != null) {
            String[] ratingGradeImgs = ((String)res.getProductDatas().get("ratingGradePics")).split(";");
            model.put("ratingGradeImgs", ratingGradeImgs);
        }
        model.put("product", res.getProductDatas());
		return "product/manage/product_detail_page";
	}
	
	/**
	 * 删除理财计划
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/deleteProduct")
	@ResponseBody
	public Map<Object, Object> deleteProduct(String pId,HashMap<String,Object> model) {
		try {
    		if(StringUtil.isNotEmpty(pId)) {
    			int row = productService.deleteBsProduct(Integer.valueOf(pId));
    			if(row >0) {
    				return ReturnDWZAjax.success("理财计划删除成功");
    			}else {
    				return ReturnDWZAjax.fail("理财计划删除失败");
    			}
        	}else {
        		return ReturnDWZAjax.fail("理财计划Id参数不能为空");
        	}
    	} catch (Exception e) {
    		return ReturnDWZAjax.fail("理财计划删除操作失败");
    	}
	}
	
	/**
	 * 校验自定义计划名称和已有系列是否重名
	 * @param name
	 * @return boolean
	 */
	@RequestMapping("/checkSerialName")
	@ResponseBody
	public boolean checkSerialName(String name,String id, String cloneId) {
		BsProductExample example = new BsProductExample();
		example.createCriteria().andNameEqualTo(name);
		List<BsProduct> products = productService.findAllProductByExample(example);
		if(StringUtil.isNotEmpty(id)) {
			if(!CollectionUtils.isEmpty(products)) {
				//克隆页面进入
				if(StringUtil.isNotEmpty(cloneId)) {
					return true;
				}else{
					if(!products.get(0).getId().equals(Integer.valueOf(id))) {
						return true;
					}
				}
			}
		}else {
			if(!CollectionUtils.isEmpty(products)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 校验计划系列和期限是否匹配
	 * @param term
	 * @param serialId
	 * @return boolean
	 */
	@RequestMapping("/checkSerialTerm")
	@ResponseBody
	public boolean checkSerialTerm(String serialId,String termStr) {
		BsProductSerial serial = productSerialService.findBsProductSerialsById(Integer.valueOf(serialId));
		if(serial != null) {
			int term = serial.getTerm();
			int receiveTerm = Integer.valueOf(termStr);
			if(term == receiveTerm) {
				return true;
			}
			return false;
		}
		return false;
	}
	
	/**
	 * 校验开始时间是否大于当前时间
	 * @param startTime
	 * @return boolean
	 */
	@RequestMapping("/checkStartTime")
	@ResponseBody
	public boolean checkStartTime(String startTime) {
		if(new Date().before(DateUtil.parseDateTime(startTime))) {
			return true;
		}
		return false;
	}
	
	/**
	 * 保存或者提审理财计划
	 * @param status
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/modifyProduct")
	@ResponseBody
	public Map<Object, Object> modifyProduct(String status,BsProduct product,HttpServletRequest request,HashMap<String,Object> model) {
		try {
			if(StringUtil.isNotEmpty(status)) {
				if(product.getId() != null) {
					BsProduct recode = productService.findBsProductById(product.getId());
        			if(recode != null && (!Constants.PRODUCT_STATUS_CHECK_NO.equals(recode.getStatus()) && !Constants.PRODUCT_STATUS_PASS_NO.equals(recode.getStatus()))) {
        				return ReturnDWZAjax.fail("只有未审核或者未通过的理财计划才能编辑");
        			}
				}
				
				product.setStatus(Integer.valueOf(status));
				if(product.getSerialId() == -1) {
    				product.setSerialId(null);
    				product.setName(product.getName().trim());
    				product.setSerialName(Constants.PRODUCT_SERIAL_NAME_CUSTOMIZE);
    			}else {
    				BsProductSerial serial = productSerialService.findBsProductSerialsById(product.getSerialId());
    				if(serial == null) {
    					return ReturnDWZAjax.fail("没有对应的计划系列");
    				}else {
    					product.setName(serial.getSerialName());
    					product.setSerialName(Constants.PRODUCT_SERIAL_NAME_SERIAL);
    				}
    			}
    			String startTime = request.getParameter("startTime");
    			String endTime = request.getParameter("endTime");
    			if(StringUtil.isNotEmpty(startTime)) {
    				product.setStartTime(DateUtil.parseDateTime(startTime));
    			}
    			
    			if(StringUtil.isNotEmpty(endTime)) {
    				product.setEndTime(DateUtil.parseDateTime(endTime));
    			}
    			
    			if(StringUtil.isNotEmpty(product.getShowTerminal())) {
    				product.setShowTerminal(product.getShowTerminal().length() > 1 ? product.getShowTerminal().substring(0, product.getShowTerminal().length()-1) : product.getShowTerminal());
    			}
    			
    			if(product.getTerm() != null) {
    			    BsPropertyInfo bsPropertyInfo = propertyInfoService.selectByPrimaryId(product.getPropertyId());
    				BsTermProductCodeExample example = new BsTermProductCodeExample();
    				example.createCriteria().andTermEqualTo(product.getTerm()).andPropertySymbolEqualTo(bsPropertyInfo.getPropertySymbol());
					if(bsPropertyInfo.getPropertySymbol() != null &&  Constants.PROPERTY_SYMBOL_ZAN.equals(bsPropertyInfo.getPropertySymbol())) {
						product.setIsSupportRedPacket(Constants.IS_SUPPORT_RED_PACKET_FALSE);
						product.setIsSupportIncrInterest(Constants.IS_SUPPORT_INCRINTEREST_FALSE);
					}
    				List<BsTermProductCode> codes = termProductCodeService.findBsTermProductCodes(example);
    				if(!CollectionUtils.isEmpty(codes)) {
    					product.setCode(codes.get(0).getCode());
    				}else {
    					return ReturnDWZAjax.fail("投资期限没有其对应的产品编码");
    				}
    			}else {
    				return ReturnDWZAjax.fail("投资期限不能为空");
    			}
    			
    			//默认值设置
    			product.setType(Constants.PRODUCT_TYPE);
    			product.setInterestType(1);
    			product.setIsSuggest(Constants.PRODUCT_IS_SUGGEST_NO);
    			product.setCurrTotalAmount(0d);
    			product.setInvestNum(0);
    			product.setMaxInvestAmount(10000000000d);
			    product.setMaxInvestTimes(1000000);
			    
			    product.setNote(product.getNote().trim());

				//新手标不支持使用红包
				if(product.getActivityType() != null && Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(product.getActivityType())) {
					product.setIsSupportRedPacket(Constants.IS_SUPPORT_RED_PACKET_FALSE);
					product.setIsSupportIncrInterest(Constants.IS_SUPPORT_INCRINTEREST_FALSE);
				}

    			if(product.getId() == null) {
    				Date now = new Date();
    				product.setCreateTime(now);
    				product.setUpdateTime(now);
    				int row = productService.saveBsProduct(product);
        			if(row > 0) {
        				if(Constants.PRODUCT_STATUS_CHECK_NO.equals(Integer.valueOf(status))) {
        					return ReturnDWZAjax.success("理财计划保存成功");
        				}
        				return ReturnDWZAjax.success("理财计划提审成功");
        			}else {
        				if(Constants.PRODUCT_STATUS_CHECK_NO.equals(Integer.valueOf(status))) {
        					return ReturnDWZAjax.fail("理财计划保存失败");
        				}
        				return ReturnDWZAjax.fail("理财计划提审失败");
        			}
        		}else {
        			BsProduct recode = productService.findBsProductById(product.getId());
        			if(recode != null) {
        				product.setCreateTime(recode.getCreateTime());
        			}
        			product.setUpdateTime(new Date());
        			int row = productService.updateBsProduct(product);
        			if(row > 0) {
        				if(Constants.PRODUCT_STATUS_CHECK_NO.equals(Integer.valueOf(status))) {
        					return ReturnDWZAjax.success("理财计划编辑保存成功");
        				}
        				return ReturnDWZAjax.success("理财计划编辑提审成功");
        			}else {
        				if(Constants.PRODUCT_STATUS_CHECK_NO.equals(Integer.valueOf(status))) {
        					return ReturnDWZAjax.fail("理财计划编辑保存失败");
        				}
        				return ReturnDWZAjax.fail("理财计划编辑提审失败");
        			}
        		}
    		}else {
    			return ReturnDWZAjax.fail("理财计划状态不能为空");
    		}
    	} catch (Exception e) {
    		return ReturnDWZAjax.fail("理财计划操作失败");
    	}
	}
	
	/**
	 * 克隆理财计划保存
	 * @param status
	 * @param product
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/cloneProduct")
	@ResponseBody
	public Map<Object, Object> cloneProduct(String status,BsProduct product,HttpServletRequest request,HashMap<String,Object> model) {
		try {
			if(StringUtil.isNotEmpty(status)) {
				product.setStatus(Integer.valueOf(status));
				if(product.getSerialId() == -1) {
    				product.setSerialId(null);
    				product.setName(product.getName().trim());
    				product.setSerialName(Constants.PRODUCT_SERIAL_NAME_CUSTOMIZE);
    			}else {
    				BsProductSerial serial = productSerialService.findBsProductSerialsById(product.getSerialId());
    				if(serial == null) {
    					return ReturnDWZAjax.fail("没有对应的计划系列");
    				}else {
    					product.setName(serial.getSerialName());
    					product.setSerialName(Constants.PRODUCT_SERIAL_NAME_SERIAL);
    				}
    			}
    			String startTime = request.getParameter("startTime");
    			String endTime = request.getParameter("endTime");
    			if(StringUtil.isNotEmpty(startTime)) {
    				product.setStartTime(DateUtil.parseDateTime(startTime));
    			}
    			
    			if(StringUtil.isNotEmpty(endTime)) {
    				product.setEndTime(DateUtil.parseDateTime(endTime));
    			}
    			
    			if(StringUtil.isNotEmpty(product.getShowTerminal())) {
    				product.setShowTerminal(product.getShowTerminal().length() > 1 ? product.getShowTerminal().substring(0, product.getShowTerminal().length()-1) : product.getShowTerminal());
    			}
    			
    			if(product.getTerm() != null) {
    			    BsPropertyInfo bsPropertyInfo = propertyInfoService.selectByPrimaryId(product.getPropertyId());
    				BsTermProductCodeExample example = new BsTermProductCodeExample();
    				example.createCriteria().andTermEqualTo(product.getTerm()).andPropertySymbolEqualTo(bsPropertyInfo.getPropertySymbol());
					if(bsPropertyInfo.getPropertySymbol() != null &&  Constants.PROPERTY_SYMBOL_ZAN.equals(bsPropertyInfo.getPropertySymbol())) {
						product.setIsSupportRedPacket(Constants.IS_SUPPORT_RED_PACKET_FALSE);
						product.setIsSupportIncrInterest(Constants.IS_SUPPORT_INCRINTEREST_FALSE);
					}
    				List<BsTermProductCode> codes = termProductCodeService.findBsTermProductCodes(example);
    				if(!CollectionUtils.isEmpty(codes)) {
    					product.setCode(codes.get(0).getCode());
    				}else {
    					return ReturnDWZAjax.fail("投资期限没有其对应的产品编码");
    				}
    			}else {
    				return ReturnDWZAjax.fail("投资期限不能为空");
    			}
    			
    			//默认值设置
    			product.setType(Constants.PRODUCT_TYPE);
    			product.setInterestType(1);
    			product.setIsSuggest(Constants.PRODUCT_IS_SUGGEST_NO);
    			product.setCurrTotalAmount(0d);
    			product.setInvestNum(0);
    			product.setMaxInvestAmount(10000000000d);
			    product.setMaxInvestTimes(1000000);

				product.setNote(product.getNote().trim());

				//新手标不支持使用红包
				if(product.getActivityType() != null && Constants.PRODUCT_ACTIVITY_TYPE_NEW_BUYER.equals(product.getActivityType())) {
					product.setIsSupportRedPacket(Constants.IS_SUPPORT_RED_PACKET_FALSE);
					product.setIsSupportIncrInterest(Constants.IS_SUPPORT_INCRINTEREST_FALSE);
				}

				//克隆时新增一条记录
			    product.setId(null);
			    
				Date now = new Date();
				product.setCreateTime(now);
				product.setUpdateTime(now);
				//重名校验

				
				int row = productService.saveBsProduct(product);
    			if(row > 0) {
    				return ReturnDWZAjax.success("理财计划保存成功");
    			}else {
    				return ReturnDWZAjax.fail("理财计划保存失败");
    			}
    		
    		}else {
    			return ReturnDWZAjax.fail("理财计划状态不能为空");
    		}
    	} catch (Exception e) {
    		return ReturnDWZAjax.fail("理财计划操作失败");
    	}
	}
	
	/**
	 * 保存或者提审理财计划
	 * @param status
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/reviewProduct")
	@ResponseBody
	public Map<Object, Object> reviewProduct(String pId,HttpServletRequest request,HashMap<String,Object> model) {
		try {
    		if(StringUtil.isNotEmpty(pId)) {
    			BsProduct product = productService.findBsProductById(Integer.valueOf(pId));
    			if(product == null) {
    				return ReturnDWZAjax.fail("没有此理财计划");
    			}
    			if(Constants.PRODUCT_STATUS_CHECK_NO.equals(product.getStatus()) || Constants.PRODUCT_STATUS_PASS_NO.equals(product.getStatus())) {
    				product.setStatus(Constants.PRODUCT_STATUS_CHECKING);
        			int row = productService.updateBsProduct(product);
        			if(row >0) {
        				return ReturnDWZAjax.success("理财计划提审成功");
        			}else {
        				return ReturnDWZAjax.fail("理财计划提审失败");
        			}
    			}else {
        			return ReturnDWZAjax.fail("只有未审核或者未通过的理财计划才能提审");
    			}
    			
        	}else {
        		return ReturnDWZAjax.fail("理财计划Id参数不能为空");
        	}
    	} catch (Exception e) {
    		return ReturnDWZAjax.fail("理财计划提审操作失败");
    	}
	}
	
	/**
	 * 查询所有的期限
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectTerms")
	@ResponseBody
	public Map<String, Object> selectTerms() {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询期限产品编码列表
		BsTermProductCodeExample example = new BsTermProductCodeExample();
		example.setOrderByClause("term asc");
		List<BsTermProductCode> codes = termProductCodeService.findBsTermProductCodes(example);
    	map.put("terms", codes);
    	return map;
	}
	
	/**
	 * 查询系列对应的期限
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/selectSerialTerm")
	@ResponseBody
	public Map<String, Object> selectSerialTerm(String serialId) {
		Map<String, Object> map = new HashMap<String, Object>();
		//查询系列对应的期限
		BsProductSerial serial = productSerialService.findBsProductSerialsById(Integer.valueOf(serialId));
		map.put("term", serial.getTerm());
		return map;
	}
}
