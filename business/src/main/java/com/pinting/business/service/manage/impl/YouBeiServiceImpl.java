package com.pinting.business.service.manage.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.service.manage.YouBeiService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.gateway.hessian.message.youbei.B2GReqMsg_YouBei_CheckRealName;
import com.pinting.gateway.hessian.message.youbei.B2GResMsg_YouBei_CheckRealName;
import com.pinting.gateway.out.service.YouBeiTransportService;

@Service
public class YouBeiServiceImpl implements YouBeiService {
	
	@Autowired
	private YouBeiTransportService youBeiTransportService;

	@Override
	public Map<String, Object> checkRealName(String idCard, String name, String cardNo, String mobile) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("isOk", "no");
		B2GReqMsg_YouBei_CheckRealName req = new B2GReqMsg_YouBei_CheckRealName();
		req.setIdCard(idCard);
		req.setName(name);
		req.setMobile(mobile);
		req.setCardNo(cardNo);
		B2GResMsg_YouBei_CheckRealName res = youBeiTransportService.checkRealName(req);
		if(ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			Map<String, Object> temp = res.getRealNameModel();
			if("0000".equals((String)temp.get("ret_code"))) {
				if("T".equals((String)temp.get("result_auth"))) {
					map.put("isOk", "yes");
				}
				else {
					map.put("msg", "认证失效");
				}
			}
			else {
				map.put("msg", (String)temp.get("ret_code") + "-" + (String)temp.get("ret_msg"));
			}
		}
		else {
			map.put("msg", "调用有贝接口失败，通讯失败");
		}
		return map;
	}

}
