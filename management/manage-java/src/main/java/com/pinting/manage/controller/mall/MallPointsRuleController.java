package com.pinting.manage.controller.mall;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.StringUtil;
import com.pinting.mall.dao.MallPointsRuleMapper;
import com.pinting.mall.enums.MallRuleDetailEnum;
import com.pinting.mall.enums.MallRuleEnum;
import com.pinting.mall.enums.MallRuleStatusEnum;
import com.pinting.mall.model.MallPointsRule;
import com.pinting.mall.model.MallPointsRuleDetail;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.dto.MallPointsRuleDTO;
import com.pinting.mall.service.MallPointsRuleService;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 积分设置
 * @author SHENGUOPING
 * @date  2018年5月14日 上午10:03:36
 */
@Controller
public class MallPointsRuleController {

	private Logger log = LoggerFactory.getLogger(MallPointsRuleController.class);
	
	@Autowired
	private MallPointsRuleService mallPointsRuleService;
	@Autowired
	private MallPointsRuleMapper mallPointsRuleMapper;
	
	@RequestMapping("/mallPointsRule/findList")
    public String findList(Integer numPerPage, Integer pageNum, HttpServletRequest request, 
    			HttpServletResponse response, @ModelAttribute PagerReqDTO pagerReq, Map<String, Object> model) {
        pageNum = pageNum == null ? Constants.MANAGE_DEFAULT_PAGENUM : pageNum;
        numPerPage = numPerPage == null ? Constants.MANAGE_DEFAULT_NUMPERPAGE : numPerPage;
        PagerModelRspDTO pageList = mallPointsRuleService.findPointsRuleList(MallRuleStatusEnum.MALL_RULE_STATUS_DELETED.getCode(), pagerReq);
        
        model.put("pageList", pageList);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/mall/mallPointsRule/index";
    }
	
	/**
	 * 更新积分设置状态
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/mallPointsRule/updateStatus")
	@ResponseBody 
    public Map<Object, Object> updateStatus(@RequestParam String ruleId, HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		Boolean isSuccess = mallPointsRuleService.updateStatus(MallRuleStatusEnum.MALL_RULE_STATUS_DELETED.getCode(), Integer.parseInt(ruleId));
		if (isSuccess) {
			return ReturnDWZAjax.success("操作成功！");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
    }
	
	/**
     * 进入添加/编辑页面
     * @param ruleId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/mallPointsRule/detail")
    public String detail(Integer ruleId, HttpServletRequest request, HttpServletResponse response,
                         Map<String, Object> model) {
        if (null != ruleId) {
        	MallPointsRule record = mallPointsRuleService.findById(ruleId);
            model.put("mallPointsRule", record);
            if (MallRuleEnum.MALL_INVEST.getCode().equals(record.getGetScene())) {
            	MallPointsRuleDetail investRuleDetail = mallPointsRuleService.findByRuleId(record.getId(), 
            			MallRuleDetailEnum.MALL_EXCHANGE_RATE.getCode());
            	model.put("ruleValueExchangeRate", investRuleDetail);
            } else if (MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(record.getGetScene())) {
            	MallPointsRuleDetail investBeginRuleDetail = mallPointsRuleService.findByRuleId(record.getId(), 
            			MallRuleDetailEnum.MALL_INVEST_AMOUNT_BEGIN.getCode());
            	MallPointsRuleDetail investEndRuleDetail = mallPointsRuleService.findByRuleId(record.getId(), 
            			MallRuleDetailEnum.MALL_INVEST_AMOUNT_END.getCode());
            	model.put("ruleValueInvestAmountBegin", investBeginRuleDetail);
            	model.put("ruleValueInvestAmountEnd", investEndRuleDetail);
            } else if (MallRuleEnum.MALL_SIGN.getCode().equals(record.getGetScene())) {
            	MallPointsRuleDetail signInitRuleDetail = mallPointsRuleService.findByRuleId(record.getId(),
						MallRuleDetailEnum.MALL_SIGN_INIT_POINT.getCode());
				MallPointsRuleDetail signIncrementRuleDetail = mallPointsRuleService.findByRuleId(record.getId(),
						MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT.getCode());
				MallPointsRuleDetail signAwardRuleDetail = mallPointsRuleService.findByRuleId(record.getId(),
						MallRuleDetailEnum.MALL_SIGN_AWARD_POINT.getCode());
				model.put("ruleValueSignInitPoint", signInitRuleDetail);
				model.put("ruleValueSignIncrementPoint", signIncrementRuleDetail);
				model.put("ruleValueSignAwardPoint", signAwardRuleDetail);

				MallPointsRuleDetail signInitRuleDetailApp = mallPointsRuleService.findByRuleId(record.getId(),
						MallRuleDetailEnum.MALL_SIGN_INIT_POINT_APP.getCode());
				MallPointsRuleDetail signIncrementRuleDetailApp = mallPointsRuleService.findByRuleId(record.getId(),
						MallRuleDetailEnum.MALL_SIGN_INCREMENT_POINT_APP.getCode());
				MallPointsRuleDetail signAwardRuleDetailApp = mallPointsRuleService.findByRuleId(record.getId(),
						MallRuleDetailEnum.MALL_SIGN_AWARD_POINT_APP.getCode());
				model.put("ruleValueSignInitPointApp", signInitRuleDetailApp);
				model.put("ruleValueSignIncrementPointApp", signIncrementRuleDetailApp);
				model.put("ruleValueSignAwardPointApp", signAwardRuleDetailApp);
            }
        } 
        return "/mall/mallPointsRule/rule_detail";
    }

    /**
     * 添加/修改实现
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/mallPointsRule/operateRule")
    @ResponseBody
    public Map<Object, Object> save(MallPointsRuleDTO req, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) {
    	String operateFlag = request.getParameter("operateFlag");
    	String triggerTimeStart = req.getTriggerTimeStart();
    	String triggerTimeEnd = req.getTriggerTimeEnd();
    	CookieManager cookie = new CookieManager(request);
		String userId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		req.setOpUserId(StringUtil.isEmpty(userId) ? 1 : Integer.parseInt(userId));
    	if (StringUtil.isEmpty(triggerTimeStart) || StringUtil.isEmpty(triggerTimeEnd)) {
    		return ReturnDWZAjax.fail("积分规则触发时间不能为空！");
    	}
    	if (triggerTimeStart.compareTo(triggerTimeEnd) >= 0) {
    		return ReturnDWZAjax.fail("积分规则触发开始时间大于或等于结束时间！");
    	}
    	if (req.getGetScene() != null && MallRuleEnum.MALL_TOTAL_INVEST.equals(req.getGetScene())) {
			if (req.getRuleValueInvestAmountBegin().compareTo(req.getRuleValueInvestAmountEnd()) > 0) {
				return ReturnDWZAjax.fail("累计年化投资满额截至金额必须大于满额起始金额！");
			}	
		}
    	Boolean isSuccess = false;
    	if ("add".equals(operateFlag)) {
    		isSuccess = mallPointsRuleService.addMallPointRule(req);
    	} else if ("update".equals(operateFlag)) {
    		// 校验是否为当前场景
    		MallPointsRule mallPointsRule = mallPointsRuleService.findById(req.getRuleId());
    		if (mallPointsRule != null && !mallPointsRule.getGetScene().equals(req.getGetScene())) {
    			return ReturnDWZAjax.fail("积分规则场景不能编辑！");
    		}
    		isSuccess = mallPointsRuleService.updateMallPointRule(req);
    	}
    	if (isSuccess) {
    		return ReturnDWZAjax.success("操作成功！");
    	} else {
    		if (req.getGetScene() != null && MallRuleEnum.MALL_TOTAL_INVEST.getCode().equals(req.getGetScene())) {
    			return ReturnDWZAjax.fail("积分规则累计年化投资金额重复，请核对！");  
    		} else {    			
    			return ReturnDWZAjax.fail("积分规则触发时间重复，请核对！");    		
    		}
    	}
    }
	
}
