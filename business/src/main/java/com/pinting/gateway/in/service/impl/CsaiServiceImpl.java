package com.pinting.gateway.in.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.BsProductMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.model.BsProduct;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.InvestInfoXiCaiVO;
import com.pinting.business.model.vo.UserInfoXiCaiVO;
import com.pinting.business.util.Constants;
import com.pinting.gateway.hessian.message.xicai.model.ProductInfo;
import com.pinting.gateway.in.service.CsaiService;

@Service
public class CsaiServiceImpl implements CsaiService {
	
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsProductMapper bsProductMapper;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsSysConfigMapper bsSysConfigMapper;
	

	@Override
	public List<UserInfoXiCaiVO> findXiCaiUserList(String startdate, String enddate, Integer page, Integer pagesize) {
		return bsUserMapper.selectXiCaiUserList(startdate, enddate, (page - 1)*10, pagesize, Constants.XICAI_AGENT_ID);
	}

	@Override
	public Integer findXiCaiUserCount(String startdate, String enddate) {
		return bsUserMapper.selectXiCaiUserCount(startdate, enddate, Constants.XICAI_AGENT_ID);
	}

	@Override
	public List<InvestInfoXiCaiVO> findXiCaiInvestInfo(String startdate, String enddate, Integer page, Integer pagesize) {
		return bsUserMapper.selectXiCaiInvestInfo(startdate, enddate, (page - 1)*10, pagesize, Constants.XICAI_AGENT_ID);
	}

	@Override
	public Integer findXiCaiInvestCount(String startdate, String enddate) {
		return bsUserMapper.selectXiCaiInvestCount(startdate, enddate, Constants.XICAI_AGENT_ID);
	}

	@Override
	public ProductInfo findProductInfoByProductId(Integer productId) {
		ProductInfo productInfo = new ProductInfo();
		//查询产品信息
		BsProduct bsProduct = bsProductMapper.selectByPrimaryKey(productId);
		//已募集金额
		Double investAmount  = bsSubAccountMapper.countCsaiUserBuyAccount(Constants.XICAI_AGENT_ID,productId);
		//某一个产品投资人数
		Integer investMans  = bsSubAccountMapper.countCsaiBuyUser(Constants.XICAI_AGENT_ID,productId);
		//查询配置表希财募集金额
		BsSysConfig amountConfig = bsSysConfigMapper.selectByPrimaryKey("CSAI_INVEST_AMOUNT");
		//查询配置表希财标的开始时间
		BsSysConfig underlyingStartConfig = bsSysConfigMapper.selectByPrimaryKey("CSAI_UNDERLYING_START");
		//查询配置表希财产品信息接口中需要的发布时间
		BsSysConfig publishTimeConfig = bsSysConfigMapper.selectByPrimaryKey("CSAI_PUBLISH_TIME");
		if (bsProduct== null  || amountConfig ==null || underlyingStartConfig==null || publishTimeConfig == null) {
			return null;
		}
		/**
			private String p2p_product_id;
			private String product_name;
			private Integer isexp;
			private Integer life_cycle;
			private Double ev_rate;
			private Integer amount;
			private Integer invest_amount;
			private Integer inverst_mans;
			private String underlying_start;
			private String underlying_end;
			private String link_website;
			private Integer product_state;
			private String borrower;
			private Integer guarant_mode;
			private String guarantors;
			private String publish_time;
			private String repay_start_time;
			private String repay_end_time;
			private Integer borrow_type;
			private Integer pay_type;
			private Integer start_price;
			private Integer step_price;
			private Double charge;
		 */
		productInfo.setP2p_product_id(String.valueOf(productId)); //产品唯一ID
		productInfo.setProduct_name(bsProduct.getName()); //产品名称
		productInfo.setIsexp(0); //是否为新手标
		productInfo.setLife_cycle(bsProduct.getTerm4Day()); //产品周期
		productInfo.setEv_rate(bsProduct.getBaseRate()); //年化收益率
		productInfo.setAmount(Integer.parseInt(amountConfig.getConfValue())); //募集金额
		productInfo.setInvest_amount(investAmount==null?0:investAmount.intValue()); //已募集金额
		productInfo.setInverst_mans(investMans);  //投资人数
		productInfo.setUnderlying_start(underlyingStartConfig.getConfValue());//标的开始时间
		productInfo.setUnderlying_end(null);  //标的结束时间
		productInfo.setLink_website(null); //链接地址（标的地址）
		productInfo.setProduct_state(0);//产品募集状态 -1：流标，0：筹款中，1:已满标，2：已开始还款，3：预发布，4：还款完成，5：逾期
		productInfo.setBorrower("达飞云贷科技（北京）有限公司");  //借款人名称
		productInfo.setGuarant_mode(1); //担保方式  0 无担保 1 本息担保 2 本金担保
		productInfo.setGuarantors(null);  
		productInfo.setPublish_time(publishTimeConfig.getConfValue());  //发布时间
		productInfo.setRepay_start_time(null); //开始还款时间
		productInfo.setRepay_end_time(null); //还款结束时间
		productInfo.setBorrow_type(2); //借款担保方式1 抵押借款 2 信用借款 3 质押借款 4 第三方担保
		productInfo.setPay_type(4);  //还款方式   1 按月付息 到期还本 2 按季付息，到期还本3 每月等额本息 4 到期还本息 5 按周等额本息还款 6按周付息，到期还本 7：利随本清；8：等本等息；9：按日付息，到期还本；10：按半年付息，到期还本；11：按一年付息，到期还本；100：其他方式； 
		productInfo.setStart_price(100); //起投金额
		productInfo.setStep_price(null); //追加投入金额
		productInfo.setCharge(null); //手续费 
		return productInfo;
	}
	
	
}
