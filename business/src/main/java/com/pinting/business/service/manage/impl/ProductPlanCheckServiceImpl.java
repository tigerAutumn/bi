package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.vo.MProductVO;
import com.pinting.business.model.vo.ProductDetailVO;
import com.pinting.business.service.manage.MProductService;
import com.pinting.business.service.manage.ProductPlanCheckService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class ProductPlanCheckServiceImpl implements ProductPlanCheckService {
	@Autowired
	private BsProductMapper bsProductMapper;
	@Autowired
	private MProductService mProductService;

	@Override
	public void planListQuery(ReqMsg_ProductPlanCheck_PlanListQuery reqMsg,
			ResMsg_ProductPlanCheck_PlanListQuery resMsg) {
		MProductVO mProductVO = new MProductVO();
		
		mProductVO.setsName(reqMsg.getsName());
		mProductVO.setsSerialId(reqMsg.getsSerialId());
		mProductVO.setsDistributeTime(DateUtil.parseDate(reqMsg.getsDistributeTime()));
		mProductVO.seteDistributeTime(DateUtil.parseDate(reqMsg.geteDistributeTime()));
		mProductVO.setsStartTime(DateUtil.parseDate(reqMsg.getsStartTime()));
		mProductVO.seteStartTime(DateUtil.parseDate(reqMsg.geteStartTime()));
		mProductVO.setsEndTime(DateUtil.parseDate(reqMsg.getsEndTime()));
		mProductVO.seteEndTime(DateUtil.parseDate(reqMsg.geteEndTime()));
		mProductVO.setsTerm(reqMsg.getsTerm());
		mProductVO.setsBaseRate(reqMsg.getsBaseRate());
		mProductVO.setsStatus(reqMsg.getsStatus());
		mProductVO.setsIsSuggest(reqMsg.getsIsSuggest());
		mProductVO.setSerialName(reqMsg.getSerialName());
		mProductVO.setTermStr(reqMsg.getTermStr());
		mProductVO.setCheckerName(reqMsg.getCheckerName());
		mProductVO.setDistributorName(reqMsg.getDistributorName());
		mProductVO.setStart(reqMsg.getStart());
		mProductVO.setPageNum(reqMsg.getPageNum());
		mProductVO.setNumPerPage(reqMsg.getNumPerPage());
		mProductVO.setQueryType("PLAN_CHECK");
		//展示终端
		if(StringUtil.isNotEmpty(reqMsg.getsShowTerminal())) {
			String[] terminals = reqMsg.getsShowTerminal().split(",");
			if(terminals.length > 0) {
				List<Object> showTerminalList = new ArrayList<Object>();
				for (String str : terminals) {
					if(StringUtil.isNotEmpty(StringUtil.trim(str))) {
						showTerminalList.add(StringUtil.trim(str));
					}
				}
				mProductVO.setShowTerminalList(showTerminalList);
			}
		}
		Integer rows = mProductService.findMProductVOsCount(mProductVO);
		if (rows != null && rows >0 ) {
			List<MProductVO> mProductVOs = mProductService.findMProductVOsByPage(mProductVO);
			resMsg.setValueList(BeanUtils.classToArrayList(mProductVOs));
		}
		
		resMsg.setTotalRows(rows);
		
	}

	@Override
	public void planCheck(ReqMsg_ProductPlanCheck_PlanCheck reqMsg,
			ResMsg_ProductPlanCheck_PlanCheck resMsg) {
		BsProduct bsProduct = bsProductMapper.selectByPrimaryKey(reqMsg.getProductId());
		
		
		if( bsProduct.getStatus() == 2 && ("refuse".equals(reqMsg.getCheckType()) || "pass".equals(reqMsg.getCheckType()) )){
			bsProduct.setId(reqMsg.getProductId());
			bsProduct.setChecker(reqMsg.getChecker());
			bsProduct.setStatus(reqMsg.getStatus());
			bsProduct.setCheckTime(new Date());
			bsProductMapper.updateByPrimaryKeySelective(bsProduct);
			resMsg.setBsProduct(bsProduct);
			
		}else if (bsProduct.getStatus() == 4 && "return".equals(reqMsg.getCheckType())) {
			bsProduct.setId(reqMsg.getProductId());
			bsProduct.setChecker(reqMsg.getChecker());
			bsProduct.setStatus(reqMsg.getStatus());
			bsProduct.setCheckTime(new Date());
			bsProductMapper.updateByPrimaryKeySelective(bsProduct);
			resMsg.setBsProduct(bsProduct);
			
		}else {
			resMsg.setBsProduct(bsProduct);
			resMsg.setCheckFlag("REPEAT");
		}
		

	}

	@Override
	public void planDetail(ReqMsg_ProductPlanCheck_PlanDetail reqMsg,
			ResMsg_ProductPlanCheck_PlanDetail resMsg) {
			ProductDetailVO productDetailVO =  bsProductMapper.selectProductDetailById(reqMsg.getProductId());
		resMsg.setProductDatas(BeanUtils.classToHashMap(productDetailVO));
	}

}
