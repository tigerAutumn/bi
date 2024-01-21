package com.pinting.manage.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.model.BsSmsPlatformsConfig;
import com.pinting.business.model.vo.BsSmsPlatformsConfigVO;
import com.pinting.business.service.manage.BsSmsPlatformsConfigService;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.controller.process.SMSCheckProcess;
import com.pinting.util.ReturnDWZAjax;

/**
 * 短信平台管理
 * @author bianyatian
 * @2017-6-20 下午2:35:17
 */
@Controller
public class SmsPlatformsConfigController {
	
	@Autowired
	private BsSmsPlatformsConfigService bsSmsPlatformsConfigService;
	
	@RequestMapping("/smsPlatforms/configIndex.htm")
	public String configIndex(HashMap<String,Object> model,HttpServletRequest request){
		List<BsSmsPlatformsConfigVO> list = bsSmsPlatformsConfigService.selectAllList();
		model.put("list", list);
		return "/smsPlatforms/configIndex";
	}
	
	@RequestMapping("/smsPlatforms/toUpadtePage.htm")
	public String toUpadtePage(HashMap<String,Object> model,HttpServletRequest request,String platformsId){
		if(StringUtil.isNotBlank(platformsId)){
			Integer id = Integer.parseInt(platformsId);
			BsSmsPlatformsConfig config = bsSmsPlatformsConfigService.selectById(id);
			model.put("platform", config);
			
			List<BsSmsPlatformsConfigVO> list = bsSmsPlatformsConfigService.selectAllList();
			model.put("size", list.size());
		}
		
		return "/smsPlatforms/upadtePage";
	}
	
	@RequestMapping("/smsPlatforms/toUpadte.htm")
	public @ResponseBody Map<Object,Object> toUpadte(HttpServletRequest request,BsSmsPlatformsConfig config){
		try {
			if(config.getPlatformsName().length()>=64){
				return ReturnDWZAjax.fail("修改失败，平台名称字段过长！");
			}
			if(config.getNote().length()>=64){
				return ReturnDWZAjax.fail("修改失败，备注字段过长！");
			}
			bsSmsPlatformsConfigService.updateSmsPlatforms(config);
			return ReturnDWZAjax.success("修改成功！");
		} catch (Exception e) {
			return ReturnDWZAjax.fail("修改失败!");
		}
		
	}
	
}
