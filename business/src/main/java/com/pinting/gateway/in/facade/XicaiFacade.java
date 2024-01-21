package com.pinting.gateway.in.facade;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


import org.apache.velocity.tools.generic.NumberTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.model.BsDailyBonus;
import com.pinting.business.model.vo.InvestInfoXiCaiVO;
import com.pinting.business.model.vo.UserInfoXiCaiVO;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_GetP2P;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_InvestCount;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_UserCount;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_GetP2P;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_InvestCount;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_UserCount;
import com.pinting.gateway.hessian.message.xicai.model.InvestInfo;
import com.pinting.gateway.hessian.message.xicai.model.ProductInfo;
import com.pinting.gateway.hessian.message.xicai.model.UserInfo;
import com.pinting.gateway.in.service.CsaiService;
import com.pinting.gateway.out.service.XicaiTransportService;

@Component("Xicai")
public class XicaiFacade {
	
	@Autowired
	private XicaiTransportService xicaiTransportService;
	
	@Autowired
	private CsaiService csaiService;
	
	/**
	 * 
	 * @Title: GetP2P 
	 * @Description: 希财通过产品ID获得产品信息
	 * @param req
	 * @param res
	 * @throws
	 */
	public void getP2P(G2BReqMsg_Xicai_GetP2P req, G2BResMsg_Xicai_GetP2P res) {
		//查询产品信息
		ProductInfo productInfo = csaiService.findProductInfoByProductId(req.getPid());
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
		if (productInfo == null ) {
			res.setCode(1);
		}else {
			HashMap<String, Object> mapProductInfo=new HashMap<String, Object>();
			mapProductInfo.put("p2p_product_id", productInfo.getP2p_product_id());
			mapProductInfo.put("product_name", productInfo.getProduct_name());
			mapProductInfo.put("isexp", productInfo.getIsexp().toString());
			mapProductInfo.put("life_cycle", productInfo.getLife_cycle().toString());
			mapProductInfo.put("ev_rate", productInfo.getEv_rate().toString());
			mapProductInfo.put("amount", productInfo.getAmount().toString());
			mapProductInfo.put("invest_amount", productInfo.getInvest_amount().toString());
			mapProductInfo.put("inverst_mans", productInfo.getInverst_mans().toString());
			mapProductInfo.put("underlying_start", productInfo.getUnderlying_start());
			mapProductInfo.put("underlying_end", productInfo.getUnderlying_end());
			mapProductInfo.put("link_website", productInfo.getLink_website());
			mapProductInfo.put("product_state", productInfo.getProduct_state().toString());
			mapProductInfo.put("borrower", productInfo.getBorrower());
			mapProductInfo.put("guarant_mode", productInfo.getGuarant_mode().toString());
			mapProductInfo.put("guarantors", productInfo.getGuarantors());
			mapProductInfo.put("publish_time", productInfo.getPublish_time());
			mapProductInfo.put("repay_start_time", productInfo.getRepay_start_time());
			mapProductInfo.put("repay_end_time", productInfo.getRepay_end_time());
			mapProductInfo.put("borrow_type", productInfo.getBorrow_type().toString());
			mapProductInfo.put("pay_type", productInfo.getPay_type().toString());
			mapProductInfo.put("start_price", productInfo.getStart_price().toString());
			mapProductInfo.put("step_price", productInfo.getStep_price());
			mapProductInfo.put("charge", productInfo.getCharge());
			res.setData(mapProductInfo);
			res.setCode(0);
		}
		
		

		
	}
	
	/**
	 * 
	 * @Title: userCount 
	 * @Description: 希财获得用户统计信息
	 * @param req
	 * @param res
	 * @throws
	 */
	public void userCount(G2BReqMsg_Xicai_UserCount req, G2BResMsg_Xicai_UserCount res) {
		int total = csaiService.findXiCaiUserCount(req.getStartdate(), req.getEnddate()); 
		try {
			ArrayList<UserInfoXiCaiVO> xiCaiUserList = (ArrayList<UserInfoXiCaiVO>) csaiService.findXiCaiUserList(
					req.getStartdate(), req.getEnddate(), req.getPage(), req.getPagesize());
			List<UserInfo> list = new ArrayList<UserInfo>();
			for (int i = 0;i < xiCaiUserList.size();i++) {
				UserInfo info = new UserInfo();
				info.setId(xiCaiUserList.get(i).getId());
				info.setUsername(xiCaiUserList.get(i).getNick());
				// 姓名 张**
				String realName = xiCaiUserList.get(i).getUserName();
				if (!StringUtil.isBlank(realName)) {
					info.setRealname((StringUtil.left(realName, 1) + "**"));
				}
				info.setDisplay(String.valueOf(xiCaiUserList.get(i).getRegTerminal()));
				// 手机号 135****0571
				String phone = xiCaiUserList.get(i).getMobile();
				info.setPhone(StringUtil.left(phone, 3) + "****" + StringUtil.right(phone, 4));
				info.setQq("");
				info.setIp("");
				info.setEmail(xiCaiUserList.get(i).getEmail() == null ? "":xiCaiUserList.get(i).getEmail());
				info.setDisplay(String.valueOf(xiCaiUserList.get(i).getRegTerminal()).equals("2")?"pc":"" );
				info.setRegtime(DateUtil.formatDateTime(xiCaiUserList.get(i).getRegisterTime(), "yyyy-MM-dd HH:mm"));
				info.setTotalmoney(xiCaiUserList.get(i).getTotalmoney().intValue());
				list.add(info);
			}
			res.setCode(0);
			res.setTotal(total);
			res.setList(list);
		} catch (Exception e) {
			res.setCode(1);
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @Title: investCount 
	 * @Description: 希财获得投资统计信息
	 * @param req
	 * @param res
	 * @throws
	 */
	public void investCount(G2BReqMsg_Xicai_InvestCount req, G2BResMsg_Xicai_InvestCount res ) {
		int total = csaiService.findXiCaiInvestCount(req.getStartdate(), req.getEnddate());
		try {
			ArrayList<InvestInfoXiCaiVO> xiCaiInvest = (ArrayList<InvestInfoXiCaiVO>) csaiService.findXiCaiInvestInfo(
					req.getStartdate(), req.getEnddate(), req.getPage(), req.getPagesize());
			List<InvestInfo> list = new ArrayList<InvestInfo>();
			DecimalFormat decimalFormat = new DecimalFormat("0.00");
			
			for (int i = 0;i < xiCaiInvest.size();i++) {
				InvestInfo investInfo = new InvestInfo();
				investInfo.setId(xiCaiInvest.get(i).getSubId());
				investInfo.setPid(xiCaiInvest.get(i).getPid());
				investInfo.setUsername(StringUtil.left(xiCaiInvest.get(i).getMobile(), 3)+"****"+StringUtil.right(xiCaiInvest.get(i).getMobile(), 4));
				investInfo.setDisplay(String.valueOf(xiCaiInvest.get(i).getRegTerminal()).equals("2")?"pc":"");
				investInfo.setDatetime(DateUtil.formatDateTime(xiCaiInvest.get(i).getOpenTime(), "yyyy-MM-dd HH:mm"));
				investInfo.setMoney(xiCaiInvest.get(i).getMoney().intValue());
				// 返回佣金  投资产品年化收益的2%
				Integer term4Day = xiCaiInvest.get(i).getTerm4Day();
				investInfo.setLife_cycle(String.valueOf(term4Day));
				investInfo.setCommision(Double.parseDouble(decimalFormat.format(xiCaiInvest.get(i).getMoney()*term4Day/365*0.02)));
				list.add(investInfo);
			}
			res.setCode(0);
			res.setTotal(total);
			res.setList(list);
		} catch (Exception e) {
			res.setCode(1);
			e.printStackTrace();
		}
	}
	
}
