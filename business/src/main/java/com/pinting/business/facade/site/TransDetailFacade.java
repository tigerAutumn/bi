package com.pinting.business.facade.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.business.hessian.site.message.ReqMsg_TransDetail_QueryZanReturnDetail;
import com.pinting.business.hessian.site.message.ResMsg_TransDetail_QueryZanReturnDetail;
import com.pinting.business.model.BsUser;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.site.message.ReqMsg_TransDetail_QueryByUserId;
import com.pinting.business.hessian.site.message.ResMsg_TransDetail_QueryByUserId;
import com.pinting.business.model.BsUserTransDetail;
import com.pinting.business.service.site.UserTransDetailService;
import com.pinting.business.util.Constants;

/**
 * 
 * @author bianyatian
 *
 */
@Component("TransDetail")
public class TransDetailFacade {
	@Autowired
	private UserTransDetailService transDetailService;

	public void queryByUserId(ReqMsg_TransDetail_QueryByUserId req,ResMsg_TransDetail_QueryByUserId resp){
		List<BsUserTransDetail> list = transDetailService.findByUserIdNew(req.getUserId(), req.getPageIndex(), req.getPageSize());
		ArrayList<HashMap<String, Object>> transDetail = null;
		if(list != null && list.size() > 0){
			transDetail = new ArrayList<HashMap<String,Object>>();
			for (BsUserTransDetail detail : list) {
				HashMap<String, Object> mapDetail=new HashMap<String, Object>();
				/**
				 * 以下循环：
				 * transTime	交易时间	必填	Date
				 * transName		交易名称		必填	Double
				 * transAmount		交易金额		可选	String
				 * afterAvialableBlance		剩余金额		可选	String
				 * cdFlag1		借贷		1代表借 2代表贷
				 */
				mapDetail.put("transType", detail.getTransType());
				mapDetail.put("transTime", detail.getUpdateTime());
				if(detail.getTransType().equals(Constants.Trans_TYPE_TOP_UP)){
					mapDetail.put("transName", "充值");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_BUY)){
					mapDetail.put("transName", "加入金额");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_RETURN)){
					mapDetail.put("transName", "回款");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_ZAN_RETURN)){
					mapDetail.put("transName", "回款");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_BONUS_2_BALANCE)){
					mapDetail.put("transName", "奖励金转余额");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_WITHDRAW)){
					mapDetail.put("transName", "提现");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_DEP_WITHDRAW)){
					mapDetail.put("transName", "提现");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_AUTH_ACCOUNT_TURN_TO_BALANCE)){
					mapDetail.put("transName", "委托退回");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_TOP_UP_FEE)){
					mapDetail.put("transName", "充值手续费");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_WITHDRAW_FEE)){
					mapDetail.put("transName", "提现手续费");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_USER_BONUS_WITHDRAW)){
					mapDetail.put("transName", "奖励金提现");
				}else if(detail.getTransType().equals(Constants.Trans_TYPE_HEAD_FEE_RETURN)) {
					mapDetail.put("transName", "手续费返还");
				}
				mapDetail.put("transAmount", detail.getAmount());
				if(detail.getTransStatus().equals(Constants.Trans_STATUS_DEAL)){
					mapDetail.put("transStatus", "处理中");
				}else if(detail.getTransStatus().equals(Constants.Trans_STATUS_SUCCESS)){
					mapDetail.put("transStatus", "成功");
				}else if(detail.getTransStatus().equals(Constants.Trans_STATUS_FAIL)){
					mapDetail.put("transStatus", "失败");
				}else if(detail.getTransStatus().equals(Constants.Trans_STATUS_CHECK)){
					mapDetail.put("transStatus", "审核中");
				}
				
				transDetail.add(mapDetail);
			}
		}
		int pageSize=req.getPageSize();
		resp.setTotalCount((int)Math.ceil((double)transDetailService.findByUserIdCountNew(req.getUserId())/pageSize));
		resp.setTransPrincipals(transDetail);
		resp.setPageIndex(req.getPageIndex());
		 //判断当前用户有多少个处理中的订单
        Integer processingNum = transDetailService.processingNumAll(req.getUserId());
        resp.setProcessingNum(processingNum);
	}

	public void queryZanReturnDetail(ReqMsg_TransDetail_QueryZanReturnDetail req, ResMsg_TransDetail_QueryZanReturnDetail res) {
		List<BsUserTransDetail> detailList = transDetailService.queryReturnZanDetail(req.getUserId(), req.getTime());
		List<HashMap<String, Object>> result = new ArrayList<>();
		for (BsUserTransDetail detail: detailList) {
			HashMap<String, Object> map = new HashMap<>();
			if(detail.getTransStatus().equals(Constants.Trans_STATUS_DEAL)){
				map.put("transStatus", "处理中");
			}else if(detail.getTransStatus().equals(Constants.Trans_STATUS_SUCCESS)){
				map.put("transStatus", "成功");
			}else if(detail.getTransStatus().equals(Constants.Trans_STATUS_FAIL)){
				map.put("transStatus", "失败");
			}else if(detail.getTransStatus().equals(Constants.Trans_STATUS_CHECK)){
				map.put("transStatus", "审核中");
			}
			map.put("transAmount", detail.getAmount());
			map.put("transTime", DateUtil.formatDateTime(detail.getCreateTime(), "yyyy-MM-dd HH:mm"));
			result.add(map);
		}
		res.setList(result);
	}

}
