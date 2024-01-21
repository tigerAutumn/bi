package com.pinting.business.facade.manage;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsUserExcel_BsUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserExcel_BsUserListQuery;
import com.pinting.business.model.vo.BsBaseUserVO;
import com.pinting.business.service.manage.BsPayOdersService;
import com.pinting.business.service.manage.BsSubAccountService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.IdcardUtils;
import com.pinting.business.util.MobileLocationUtil;
/**
 * 导出用户信息（给客服）
 * @author yanwl
 * @date 2016-01-15
 *
 */
@Component("BsUserExcel")
public class BsUserExcelFacade {
	@Autowired
	private BsUserService bsUserService;
	@Autowired
	private BsPayOdersService bsPayOrdersService;
	@Autowired
	private BsSubAccountService bsSubAccountService;
	
	public void bsUserListQuery(ReqMsg_BsUserExcel_BsUserListQuery req,ResMsg_BsUserExcel_BsUserListQuery resp) throws Exception{
		List<BsBaseUserVO> baseUserVos = bsUserService.findBaseUserInfo();
		for (BsBaseUserVO bsBaseUserVO : baseUserVos) {
			if(bsBaseUserVO.getIdCard() != null) {
				String IDCard = bsBaseUserVO.getIdCard();
				if(IdcardUtils.validateCard(IDCard)) {
					bsBaseUserVO.setAge(IdcardUtils.getAgeByIdCard(IDCard));
					bsBaseUserVO.setSex(IdcardUtils.getGenderByIdCard(IDCard));
				}
			}
			
			if(bsBaseUserVO.getMobile() != null) {
				String mobile = bsBaseUserVO.getMobile();
				bsBaseUserVO.setArea(MobileLocationUtil.mobileUtil(mobile));
			}
			
			
			if(bsBaseUserVO.getId() != null) {
				//用户Id参数
				Map<String,Object> paramMap = new HashMap<String,Object>();
				paramMap.put("userId", bsBaseUserVO.getId());
				//查询首次投资设备
				Map<String,Object> investDevice = bsPayOrdersService.findFirstInvestDevice(paramMap);
				if(investDevice != null && !investDevice.isEmpty() ) {
					String firstInvestDevice = "";
					if(investDevice.get("terminalType") != null) {
						int device = Integer.valueOf(investDevice.get("terminalType").toString());
						if(device == 1) {
							firstInvestDevice = "手机端";
						}else if(device == 2) {
							firstInvestDevice = "PC端";
						}
						bsBaseUserVO.setFirstInvestDevice(firstInvestDevice);
					}
				}
				
				//查询用户加权投资期限
				Map<String,Object> weight = bsSubAccountService.findWeightInvestTrem(paramMap);
				if(weight != null && !weight.isEmpty()) {
					bsBaseUserVO.setWeightInvestTrem(weight.get("weightInvestTrem") == null ? 0.00 : Double.valueOf(weight.get("weightInvestTrem").toString()));
					bsBaseUserVO.setTotalTrans(weight.get("totalTrans") == null ? 0 : Integer.valueOf(weight.get("totalTrans").toString()));
				}
			}
		}
		
		resp.setUserList(BeanUtils.classToArrayList(baseUserVos));
	}
	
	
	
}
