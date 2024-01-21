package com.pinting.manage.controller;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeAdd;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeDetail;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppNotice_AppNoticeListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeAdd;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeDelete;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeDetail;
import com.pinting.business.hessian.manage.message.ResMsg_MAppNotice_AppNoticeListQuery;
import com.pinting.core.base.BaseController;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * app通知管理的Controller
 * @author yanwl
 * @date 2016-02-18
 */
@Controller
@RequestMapping("/app/notice")
public class AppNoticeController extends BaseController{
    @Autowired
    @Qualifier("dispatchService")
    private HessianService appService;

    /**
     * 跳转至app通知管理页面
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/index")
    public String appNoticeIndex(ReqMsg_MAppNotice_AppNoticeListQuery req,HashMap<String,Object> model) {
    	Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
    	ResMsg_MAppNotice_AppNoticeListQuery resp = (ResMsg_MAppNotice_AppNoticeListQuery)appService.handleMsg(req);
		model.put("notices", resp.getAppNoticeList());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("req", req);
        return "/app/notice/index";
    }

    @RequestMapping("/add_notice_page")
    public String addNoticePage(HttpServletRequest request, ModelMap map) {
        return "/app/notice/create_notice";
    }


    /**
     * 增加app通知
     * @param request
     * @param map
     */
    @RequestMapping("/addNotice")
    @ResponseBody
    public Map<Object, Object> saveNotice(ReqMsg_MAppNotice_AppNoticeAdd req,HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model) {
    	ResMsg_MAppNotice_AppNoticeAdd resp = (ResMsg_MAppNotice_AppNoticeAdd)appService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson())) {
    			ret = "app通知添加成功！";
    		}else if(ConstantUtil.BSRESCODE_FAIL.equals(resp.getJson())){
    			ret = "app通知名称不能重复添加！";
    			return ReturnDWZAjax.fail(ret);
    		}else {
    			ret = "app通知添加失败！";
    		}
    		return ReturnDWZAjax.success(ret);
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    /**
     * 删除app通知
     * @param request
     * @param map
     */
    @RequestMapping("/deleteNotice")
    @ResponseBody
    public Map<Object, Object> deleteNotice(ReqMsg_MAppNotice_AppNoticeDelete req,HttpServletRequest request, HttpServletResponse response,
    		Map<String, Object> model) {
    	ResMsg_MAppNotice_AppNoticeDelete resp = (ResMsg_MAppNotice_AppNoticeDelete)appService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson())) {
    			ret = "app通知删除成功！";
    		}else if(ConstantUtil.BSRESCODE_FAIL.equals(resp.getJson())){
    			ret = "app通知不存在或者app通知推送状态是已发送！";
    			return ReturnDWZAjax.fail(ret);
    		}else {
    			ret = "app通知删除失败！";
    		}
    		return ReturnDWZAjax.success(ret);
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    /**
     * 跳转至app通知查看页面
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/detail")
    public String detail(ReqMsg_MAppNotice_AppNoticeDetail req,HashMap<String,Object> model) {
    	ResMsg_MAppNotice_AppNoticeDetail resp = (ResMsg_MAppNotice_AppNoticeDetail)appService.handleMsg(req);
		model.put("msg", resp.getAppMessage());
        return "/app/notice/detail";
    }
}
