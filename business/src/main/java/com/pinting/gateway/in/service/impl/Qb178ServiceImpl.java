package com.pinting.gateway.in.service.impl;


import com.pinting.business.dao.*;
import com.pinting.business.enums.Qb178ProductStatusEnum;
import com.pinting.business.enums.Qb178TransCodeEnum;
import com.pinting.business.model.vo.BsUserTransDetailVO;
import com.pinting.business.model.vo.ProductDetailVO;
import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.qb178.*;
import com.pinting.gateway.hessian.message.qb178.model.*;
import com.pinting.gateway.in.service.Qb178Service;
import com.pinting.gateway.in.util.Qb178InConstant;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.pinting.business.util.Util.yuan2Fen;

/**
 * Created by babyshark on 2017/7/31.
 */
@Service
public class Qb178ServiceImpl implements Qb178Service {

	@Autowired
	private		BsPayOrdersMapper  bsPayOrdersMapper;
	@Autowired 
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private BsProductMapper bsProductMapper;
	@Autowired
	private BsUserMapper bsUserMapper;
	@Autowired
	private BsUserTransDetailMapper bsUserTransDetailMapper;

    /**
     * 分页查询产品详情列表
     *
     * @param req
     * @return
     */
    @Override
    public G2BResMsg_Qb178_QueryProductList queryProductDetails(G2BReqMsg_Qb178_QueryProductList req) {
		Integer productId = StringUtil.isBlank(req.getProduct_code())  ? null : Integer.valueOf(req.getProduct_code());
		Integer status = null;
		if(!StringUtil.isEmpty(req.getProduct_status())){
			Qb178ProductStatusEnum productStatusEnum = Qb178ProductStatusEnum.find(req.getProduct_status());
			if(productStatusEnum == null){
				//状态类型系统不存在，直接返回空
				return null;
			}
			status = Integer.valueOf(productStatusEnum.getMappingCode());
		}

		Date beginDate = req.getCreate_time_begin();
		Date endDate = req.getCreate_time_end();
		Integer count = bsProductMapper.count178Products(productId, status, beginDate, endDate);
		if(count == null || count == 0){
			//如果总条数为0，直接返回空
			return null;
		}
		//超过最大页数时,查最后一页的数据
		Integer maxPage = (count%req.getPage_size()>0 ? 1:0) + count/req.getPage_size();
		Integer pageNum = maxPage<req.getPage_num() ? maxPage : req.getPage_num();
		Integer start	= (pageNum-1) * req.getPage_size();
		
		List<ProductDetailVO> detailVOs = bsProductMapper.select178ProductByPage(productId, status, beginDate, endDate, start, req.getPage_size());
		//组织返回数据
		G2BResMsg_Qb178_QueryProductList res = new G2BResMsg_Qb178_QueryProductList();
		List<ProductListDataResModel> data = new ArrayList<>();
		if(!CollectionUtils.isEmpty(detailVOs)){
			for (ProductDetailVO vo: detailVOs) {
				ProductListDataResModel tempModel = new ProductListDataResModel();
				tempModel.setProduct_code(String.valueOf(vo.getId()));
				tempModel.setProduct_name(vo.getName());
				tempModel.setIssuance_amount(yuan2Fen(vo.getMaxTotalAmount()));
				tempModel.setPurchase_starttime(com.pinting.business.util.DateUtil.getDateFormatPatter(vo.getStartTime()));
				tempModel.setPurchase_endtime(com.pinting.business.util.DateUtil.getDateFormatPatter(vo.getEndTime()));
				tempModel.setRepayment_method(Qb178InConstant.QB_REPAY_METHOD_ONETIMEPRINCIPALANDINTEREST);
				tempModel.setProduct_rate(String.valueOf(vo.getBaseRate()));
				tempModel.setPublisher(Qb178InConstant.QB_PRODUCT_PUBLISHER);
				tempModel.setDown_limit(yuan2Fen(vo.getMinInvestAmount() == null ? 0: vo.getMinInvestAmount()));
				//tempModel.setMax_peoples(0);//缺省
				tempModel.setSale_amount(yuan2Fen(vo.getMinInvestAmount()));
				tempModel.setMin_transaction(Qb178InConstant.QB_INVEST_MIN_TRANSACTION);
				tempModel.setUp_limit(yuan2Fen(vo.getMaxSingleInvestAmount() == null ?0 : vo.getMaxSingleInvestAmount()));
				tempModel.setRepayment_periods(Qb178InConstant.QB_INVEST_REPAY_PERIOD);//还款期数
				tempModel.setPeriod_unit(Qb178InConstant.QB_INVEST_PERIOD_UNIT_DAY);
				tempModel.setSingle_period(String.valueOf(vo.getTerm4Day()));
				tempModel.setYear_days(String.valueOf(vo.getTerm4Day()));
				tempModel.setDeadline(String.valueOf(vo.getTerm4Day()));
				Qb178ProductStatusEnum productStatusEnum = Qb178ProductStatusEnum.findStatus(String.valueOf(vo.getStatus()));
				tempModel.setProduct_status(productStatusEnum.getCode());
				
				data.add(tempModel);
			}

		}
		res.setTotal_num(count);
		res.setCurrent_page(pageNum);
		res.setData(data);
        return res;
    }

	@Override
	public G2BResMsg_Qb178_QueryOrderList queryOrderDetails(
			G2BReqMsg_Qb178_QueryOrderList req) {
		
		String  createTimeBegin = req.getCreate_time_begin() == null ? null: DateUtil.format(req.getCreate_time_begin());
		String  createTimeEnd = req.getCreate_time_end() == null ? null:DateUtil.format(req.getCreate_time_end());
		Integer count = bsPayOrdersMapper.countOrderListApp178(req.getProduct_code(), req.getUser_account(), createTimeBegin, createTimeEnd);
		if(count == null || count == 0){
			//如果总条数为0，直接返回空
			return null;
		}
		//超过最大页数时,查最后一页的数据
		Integer maxPage = (count%req.getPage_size()>0 ? 1:0) + count/req.getPage_size();
		Integer pageNum = maxPage<req.getPage_num() ? maxPage : req.getPage_num();
		Integer start	= (pageNum-1) * req.getPage_size();
		
		List<OrderListDataResModel> list= bsPayOrdersMapper.queryOrderListApp178(req.getProduct_code(), req.getUser_account(), createTimeBegin, createTimeEnd, start, req.getPage_size());
		G2BResMsg_Qb178_QueryOrderList res = new G2BResMsg_Qb178_QueryOrderList();
		res.setCurrent_page(pageNum);
		res.setTotal_num(count);
		res.setData(list);
		return res;
	}

    @Override
    public G2BResMsg_Qb178_QueryUserDetails queryUserDetails(G2BReqMsg_Qb178_QueryUserDetails req) {
		String create_time_begin = req.getCreate_time_begin() == null ? null : DateUtil.format(req.getCreate_time_begin());
		String create_time_end = req.getCreate_time_end() == null ? null : DateUtil.format(req.getCreate_time_end());
		Integer count = bsUserMapper.count178UserDetails(req.getUser_account(), create_time_begin, create_time_end);
		if(count == null || count == 0){
			//如果总条数为0，直接返回空
			return null;
		}
		
		//超过最大页数时,查最后一页的数据
		Integer maxPage = (count%req.getPage_size()>0 ? 1:0) + count/req.getPage_size();
		Integer pageNum = maxPage<req.getPage_num() ? maxPage : req.getPage_num();
		Integer start	= (pageNum-1) * req.getPage_size();
		
		List<QueryUserDetailsDataResModel> list = bsUserMapper.select178UserDetailsByPage(req.getUser_account(),
				create_time_begin, create_time_end, start, req.getPage_size());
		G2BResMsg_Qb178_QueryUserDetails res = new G2BResMsg_Qb178_QueryUserDetails();
		res.setCurrent_page(pageNum);
		res.setTotal_num(count);
		res.setData(list);
        return res;
    }

	@Override
	public void queryPositionBalance(G2BReqMsg_Qb178_QueryPositionBalance req,
			G2BResMsg_Qb178_QueryPositionBalance res) {
		PositionProductInfo productInfo = bsProductMapper.selectProductInfoById(req.getProduct_code(), Constants.AGENT_QIANBAO_ID, 
				req.getCreate_time_begin(), req.getCreate_time_end());
		Integer count = bsSubAccountMapper.CountQbUserPosition(req.getProduct_code(), Constants.AGENT_QIANBAO_ID, req.getCreate_time_begin(), req.getCreate_time_end());
		Integer maxPage = (count%req.getPage_size()>0 ? 1:0) + count/req.getPage_size();
		Integer pageNum = maxPage<req.getPage_num() ? maxPage : req.getPage_num();
		res.setCurrent_page(pageNum);
		res.setTotal_num(count);
		if(productInfo != null && count > 0){
			List<PositionProductInfo> productList = new ArrayList<PositionProductInfo>();
			
			Integer start = (pageNum - 1) * req.getPage_size();
			List<PositionProduct4UserInfo> userPageList = bsSubAccountMapper.queryQbUserPositionPageList(req.getProduct_code(), 
					Constants.AGENT_QIANBAO_ID, req.getCreate_time_begin(), req.getCreate_time_end(), start, req.getPage_size());
			if(CollectionUtils.isNotEmpty(userPageList)){
				productInfo.setData(userPageList);
			}
			productList.add(productInfo);
			res.setData(productList);
		}
		
	}
	@Override
	public void queryRepayPlan(G2BReqMsg_Qb178_QueryRepayPlan req,
			G2BResMsg_Qb178_QueryRepayPlan res) {
		Integer count = bsSubAccountMapper.countQbRepayPlan(req.getProduct_code(), req.getUser_account(),
				Constants.AGENT_QIANBAO_ID, req.getCreate_time_begin(), req.getCreate_time_end());
		Integer maxPage = (count%req.getPage_size()>0 ? 1:0) + count/req.getPage_size();
		Integer pageNum = maxPage<req.getPage_num() ? maxPage : req.getPage_num();
		res.setCurrent_page(pageNum);
		res.setTotal_num(count);
		if(count > 0){
			Integer start = (pageNum - 1) * req.getPage_size();
			List<RepayPlanInfo> list = bsSubAccountMapper.queryQbRepayPlanList(req.getProduct_code(), req.getUser_account(),
				Constants.AGENT_QIANBAO_ID, req.getCreate_time_begin(), req.getCreate_time_end(),
				start, req.getPage_size());
			if(CollectionUtils.isNotEmpty(list)){
				res.setData(list);
			}
		}
	}


	@Override
	public G2BResMsg_Qb178_QueryBalance queryBalance(G2BReqMsg_Qb178_QueryBalance req) {
		
		String create_time_begin = req.getCreate_time_begin() == null ? null : DateUtil.format(req.getCreate_time_begin());
		String create_time_end = req.getCreate_time_end() == null ? null : DateUtil.format(req.getCreate_time_end());
		List<String> userAccountAry = (CollectionUtils.isEmpty(req.getUser_account_ary_list()) || StringUtil.isBlank(req.getUser_account_ary_list().get(0))) ? null : req.getUser_account_ary_list();
		Integer	count = bsSubAccountMapper.count178QueryBalance(userAccountAry, create_time_begin, create_time_end);
		if(count == null || count == 0){
			//如果总条数为0，直接返回空
			return null;
		}
		
		Integer maxPage = (count%req.getPage_size()>0 ? 1:0) + count/req.getPage_size();
		Integer pageNum = maxPage<req.getPage_num() ? maxPage : req.getPage_num();
		Integer start = (pageNum - 1) * req.getPage_size();
		
		List<QueryBalanceInfo> data = bsSubAccountMapper.select178QueryBalanceByPage(userAccountAry,
				create_time_begin, create_time_end, start, req.getPage_size());
		G2BResMsg_Qb178_QueryBalance res = new G2BResMsg_Qb178_QueryBalance();
		res.setCurrent_page(pageNum);
		res.setTotal_num(count);
		res.setData(data);
		return res;
	}
	/**
	 * 分页查询会员资金流水
	 *
	 * @param req
	 * @return
	 */
	@Override
	public G2BResMsg_Qb178_QueryBalanceJnl queryUserBalanceJnl(G2BReqMsg_Qb178_QueryBalanceJnl req) {
		String mobile = req.getUser_account();
		Date beginDate = req.getCreate_time_begin();
		Date endDate = req.getCreate_time_end();
		Integer count = bsUserTransDetailMapper.count178UserTransDetails(mobile, beginDate, endDate);
		if(count == null || count == 0){
			//如果总条数为0，直接返回空
			return null;
		}
		
		Integer maxPage = (count%req.getPage_size()>0 ? 1:0) + count/req.getPage_size();
		Integer pageNum = maxPage<req.getPage_num() ? maxPage : req.getPage_num();
		Integer start = (pageNum - 1) * req.getPage_size();
		
		List<BsUserTransDetailVO> detailVOs = bsUserTransDetailMapper.select178UserTransDetails(mobile, beginDate, endDate, start, req.getPage_size());
		//组织返回数据
		G2BResMsg_Qb178_QueryBalanceJnl res = new G2BResMsg_Qb178_QueryBalanceJnl();
		List<QueryBalanceJnlInfo> data = new ArrayList<>();
		if(!CollectionUtils.isEmpty(detailVOs)){
			for (BsUserTransDetailVO vo: detailVOs) {
				QueryBalanceJnlInfo tempModel = new QueryBalanceJnlInfo();
				tempModel.setUser_account(vo.getMobile());
				tempModel.setTrans_amount(yuan2Fen(vo.getAmount()));
				tempModel.setPost_amount(null);
				tempModel.setTrans_code(Qb178TransCodeEnum.find(vo.getTransType()).getMappingCode());
				tempModel.setTrans_desc(Qb178TransCodeEnum.find(vo.getTransType()).getDescription());
				tempModel.setReal_trans_dt(com.pinting.business.util.DateUtil.getDateFormatPatter(vo.getUpdateTime()));
				tempModel.setLog_id(String.valueOf(vo.getId()));
				data.add(tempModel);
			}
		}
		res.setTotal_num(count);
		res.setCurrent_page(pageNum);
		res.setData(data);
		return res;
	}
}
