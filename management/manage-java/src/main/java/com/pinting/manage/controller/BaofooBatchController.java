package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.accounting.finance.service.UserCardOperateService;
import com.pinting.business.exception.PTMessageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.service.manage.BaofooBatchService;
import com.pinting.util.ReturnDWZAjax;

/**
 * 
 * @author shh   2016/9/11
 *
 */
@Controller
@RequestMapping(value = "/baofooBatch")
public class BaofooBatchController {
	@Autowired
    private BaofooBatchService baofooBatchService;
	@Autowired
    private BsBankCardMapper bankCardMapper;
	@Autowired
	private UserCardOperateService userCardOperateService;
	
	 /**
     * 进入批量绑卡填写用户手机号页面
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/bind_card/baofoo_batchbind_card")
    public String batchBindCardPage(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
    	return "/baofoo/baofoo_batchbind_card";
    }
    
	/**
	 * 批量绑卡绑定操作
	 * @param request
	 * @param response
	 * @param model
	 * @param bindCardMobile 手机号信息字符串
	 * @return
	 */
    @ResponseBody
    @RequestMapping(value = "/bind_card/batch_bind_sub")
    public Map<Object, Object>  batchBindCard(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model, String bindCardMobile) {
    	
    	String resString = baofooBatchService.batchBindCard(bindCardMobile);
    	Map<Object, Object> map = new HashMap<Object, Object>();
    	String[] result = resString.split(",");
    	// toAjaxString(状态码, 绑卡成功的记录条数, map)
    	if ("success".equals(result[0])) {
    		return ReturnDWZAjax.toAjaxString("200", result[1], map);
		}else {
			return ReturnDWZAjax.fail(resString);
		}	
    }


	/**
	 * 批量绑卡绑定操作
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/hf/batch_bind")
	public Map<Object, Object>  hfBatchBind(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model, String mobile, String userName, String idCard, String cardNo) {
		try {
			userCardOperateService.batchBindCard(mobile, userName, idCard, cardNo);
			return ReturnDWZAjax.success("绑卡成功");
		} catch (PTMessageException e) {
			return ReturnDWZAjax.fail(e.getMessage());
		} catch (Exception e) {
			return ReturnDWZAjax.fail(e.getMessage());
		}
	}
}
