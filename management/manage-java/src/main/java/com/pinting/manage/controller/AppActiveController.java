package com.pinting.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.model.BsAppActive;
import com.pinting.business.model.vo.AppActiveVO;
import com.pinting.business.service.manage.MAppActiveService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.GlobEnv;
import com.pinting.util.ReturnDWZAjax;

/**
 * app活动管理的Controller
 *
 * @Project: manage-java
 * @author yanwl
 * @Title: AppActiveController.java
 * @date 2016-3-31 下午2:32:13
 * @Copyright: 2016 bigangwan.com Inc. All rights reserved.
 */
@Controller
@RequestMapping("/app/active")
public class AppActiveController {
	@Autowired
	private MAppActiveService appActiveService;
	
	/**
     * 跳转至app活动管理列表页面
     * @param record
     * @return
     */
    @RequestMapping("/index")
    public String appActiveIndex(AppActiveVO record,HashMap<String,Object> model) {
    	Integer pageNum = record.getPageNum();
		Integer numPerPage = record.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			record.setPageNum(pageNum);
			record.setNumPerPage(numPerPage);
		}
    	int totalRows = appActiveService.findAppActiveTotalRows(record);
    	if(totalRows > 0) {
    		List<AppActiveVO> actives = appActiveService.findAppActiveListByPage(record);
    		model.put("actives", actives);
    	}
		model.put("totalRows", totalRows);
		model.put("record", record);
        return "/app/active/active_index";
    }
    
    /**
     * 跳转至app活动新增或修改页面
     * @param id
     * @return
     */
    @RequestMapping("/activeDetail")
    public String activeDetail(String activeId,HashMap<String,Object> model) {
    	try {
    		if(StringUtil.isNotEmpty(activeId)) {
        		BsAppActive active = appActiveService.findAppActiveById(Integer.valueOf(activeId));
        		model.put("active", active);
        		model.put("mUrl", GlobEnv.get("gen.web"));
        	}
		} catch (Exception e) {
			e.printStackTrace();
		}
    	return "/app/active/active_detail";
    }
    
    /**
     * app活动新增或修改
     * @param active
     * @return
     */
    @RequestMapping("/activeModify")
    @ResponseBody
    public Map<Object, Object> activeModify(BsAppActive active,HttpServletRequest request,HashMap<String,Object> model) {
    	try {
    		if(active.getId() != null) {
        		//修改app活动
        		BsAppActive record = appActiveService.findAppActiveById(active.getId());
        		active.setCreateTime(record.getCreateTime());
        		active.setUpdateTime(new Date());
        		active.setPublishTime(DateUtil.parseDateTime(request.getParameter("publishTime")));
        		active.setStartTime(DateUtil.parseDateTime(request.getParameter("startTime")));
        		active.setEndTime(DateUtil.parseDateTime(request.getParameter("endTime")));
        		int row = appActiveService.updateAppActive(active);
        		if(row >0) {
        			return ReturnDWZAjax.success("活动更新成功");
        		}else {
        			return ReturnDWZAjax.fail("活动更新失败");
        		}
        	}else {
        		//新增app活动
        		active.setCreateTime(new Date());
        		active.setUpdateTime(new Date());
        		active.setPublishTime(DateUtil.parseDateTime(request.getParameter("publishTime")));
        		active.setStartTime(DateUtil.parseDateTime(request.getParameter("startTime")));
        		active.setEndTime(DateUtil.parseDateTime(request.getParameter("endTime")));
        		int row = appActiveService.saveAppActive(active);
        		if(row >0) {
        			return ReturnDWZAjax.success("活动新增成功");
        		}else {
        			return ReturnDWZAjax.fail("活动新增失败");
        		}
        	}
		} catch (Exception e) {
			return ReturnDWZAjax.fail("活动操作失败");
		}
    }
    
    /**
     * app活动删除
     * @param id
     * @return
     */
    @RequestMapping("/activeDelete")
    @ResponseBody
    public Map<Object, Object> activeDelete(String id,HashMap<String,Object> model) {
    	try {
    		if(StringUtil.isNotEmpty(id)) {
    			int row = appActiveService.deleteAppActive(Integer.valueOf(id));
    			if(row >0) {
    				return ReturnDWZAjax.success("活动删除成功");
    			}else {
    				return ReturnDWZAjax.fail("活动删除失败");
    			}
        	}else {
        		return ReturnDWZAjax.fail("活动Id参数不能为空");
        	}
    	} catch (Exception e) {
    		return ReturnDWZAjax.fail("活动删除操作失败");
    	}
    }
}
