package com.pinting.manage.controller;

import com.pinting.business.hessian.manage.message.*;
import com.pinting.business.model.BsAppVersion;
import com.pinting.business.service.manage.MAppVersionService;
import com.pinting.business.util.FileUtil;
import com.pinting.core.base.BaseController;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.Constants;
import com.pinting.util.GlobEnv;
import com.pinting.util.ReturnDWZAjax;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * app版本管理的Controller
 * @author yanwl
 * @date 2016-02-18
 */
@Controller
@RequestMapping("/app/version")
public class AppVersionController extends BaseController{
    @Autowired
    @Qualifier("dispatchService")
    private HessianService appService;
    @Autowired
    private MAppVersionService mAppVersionService;
    
    private Logger LOG = LoggerFactory.getLogger(UploadController.class);

    /**
     * 跳转至app版本列表页面
     * @param request
     * @param map
     * @return
     */
    @RequestMapping("/index")
    public String appIndex(ReqMsg_MAppVersion_AppVersionListQuery req,HashMap<String,Object> model) {
    	Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
    	ResMsg_MAppVersion_AppVersionListQuery resp = (ResMsg_MAppVersion_AppVersionListQuery)appService.handleMsg(req);
		model.put("versions", resp.getAppVersionList());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
        return "/app/version/index";
    }

    @RequestMapping("/add_version_page")
    public String addVersionPage(HttpServletRequest request, ModelMap map) {
        return "/app/version/create_version";
    }


    /**
     * 增加app版本信息
     * @param request
     * @param map
     */
    @RequestMapping("/addVersion")
    @ResponseBody
    public Map<Object, Object> saveVersion(ReqMsg_MAppVersion_AppVersionAdd req,HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model) {
    	ResMsg_MAppVersion_AppVersionAdd resp = (ResMsg_MAppVersion_AppVersionAdd)appService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson()) && resp.getFlag().equals(1)) {
    			ret = "app版本信息添加成功！";
    			return ReturnDWZAjax.success(ret);
    		}else if(ConstantUtil.BSRESCODE_FAIL.equals(resp.getJson())){
    			ret = "app版本信息不能重复添加！";
    			return ReturnDWZAjax.fail(ret);
    		}else {
    			ret = "app版本信息添加失败，当前版本号低于库中最新的版本号！";
    			return ReturnDWZAjax.fail(ret);
    		}
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    /**
     * 编辑app版本信息
     * @param request
     * @param map
     */
    @RequestMapping("/detail")
    public String editVersionPage(ReqMsg_MAppVersion_PrimaryKey req,HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model) {
    	ResMsg_MAppVersion_PrimaryKey resp = (ResMsg_MAppVersion_PrimaryKey)appService.handleMsg(req);
    	model.put("appVersion", resp.getAppVersion());
    	return "/app/version/detail";
    }
    
    /**
     * 更新app版本信息
     * @param request
     * @param map
     */
    @RequestMapping("/updateVersion")
    @ResponseBody
    public Map<Object, Object> updateVersion(ReqMsg_MAppVersion_AppVersionUpdate req,HttpServletRequest request, HttpServletResponse response,
                                     Map<String, Object> model) {
    	ResMsg_MAppVersion_AppVersionUpdate resp = (ResMsg_MAppVersion_AppVersionUpdate)appService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson())) {
    			ret = "app版本信息更新成功！";
    		}else if(ConstantUtil.BSRESCODE_FAIL.equals(resp.getJson())){
    			ret = "app版本信息不能更新为重复的信息！";
    			return ReturnDWZAjax.fail(ret);
    		}else {
    			ret = "app版本信息更新添加失败！";
    		}
    		return ReturnDWZAjax.success(ret);
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    
    /**
     * 删除app版本信息
     * @param request
     * @param map
     */
    @RequestMapping("/deleteVersion")
    @ResponseBody
    public Map<Object, Object> deleteVersion(ReqMsg_MAppVersion_AppVersionDelete req,HttpServletRequest request, HttpServletResponse response,
    		Map<String, Object> model) {
    	ResMsg_MAppVersion_AppVersionDelete resp = (ResMsg_MAppVersion_AppVersionDelete)appService.handleMsg(req);
    	String ret = "网络连接异常，请重试！";
    	if(ConstantUtil.BSRESCODE_SUCCESS.equals(resp.getResCode())) {
    		if(ConstantUtil.RESCODE_SUCCESS.equals(resp.getJson())) {
    			ret = "app版本信息删除成功！";
    		}else if(ConstantUtil.BSRESCODE_FAIL.equals(resp.getJson())){
    			ret = "app版本不是最新版本或者app版本不存在，不能删除！";
    			return ReturnDWZAjax.fail(ret);
    		}else {
    			ret = "app版本信息删除失败！";
    		}
    		return ReturnDWZAjax.success(ret);
    	}else {
    		return ReturnDWZAjax.fail(ret);
    	}
    }
    
    /**
     * 安卓apk上传
     *
     * @param fileName 文件地址
     */
    @RequestMapping(value = "/apkFileUpload")
    public @ResponseBody HashMap<String, Object> uploadFile(@RequestParam(value = "fileField", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request, String id) throws IOException {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        
        try{
			String fileName = file.getOriginalFilename();
        	int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			BsAppVersion bsAppVersion = mAppVersionService.findAppVersionById(Integer.parseInt(id));
			String version = null;
			if(bsAppVersion != null && !"".equals(bsAppVersion)) {
				version = bsAppVersion.getVersion();
			}
			String realName ="android_"+ version + ".apk";
			Long uploadFileSize = file.getSize();
			String maxSize = GlobEnv.get("file.apk.size");
			Long maxFileSize = Long.parseLong(maxSize);
			String path = GlobEnv.get("file.apk.upload")+"/" + realName; //存储路径
			FileUtil.createDirectory(GlobEnv.get("file.apk.upload")); // 创建文件目录
			LOG.info(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			if((extendName != null && extendName.equals(".apk")) || (extendName != null && extendName.equals(".APK"))) {
				if(uploadFileSize <= maxFileSize){
					//FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
					MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;
					Iterator<String> iter = multiRequest.getFileNames();
					while(iter.hasNext()) {
						MultipartFile multiFile = multiRequest.getFile(iter.next());
						multiFile.transferTo(new File(path));
					}

					String url = "/resources/upload/apk/" + realName; //相对路径
					LOG.info(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
				    dataMap.put("code", 1);
				    BsAppVersion appVersion = new BsAppVersion();
				    appVersion.setUrl(url);
				    appVersion.setUpdateTime(new Date());
				    appVersion.setId(Integer.parseInt(id));
				    mAppVersionService.updateAppVersion(appVersion);
				}else{
					String size = "文件大小超过限制，"+"上传的最大文件为"+maxFileSize/1024/1024+"MB请重新选择";
					dataMap.put("msg", size);
					LOG.info(">>>>>>>>>>>>>>超过限制的文件大小>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>："+uploadFileSize/1024+"k");
				}
			}else {
				String s = "文件格式错误请重新选择";
				dataMap.put("msg", s);
				LOG.info(">>>>>>>>>>>>>>格式错误的文件名称>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>："+realName);
			}
			
		}catch(Exception e){
			LOG.info(e.getMessage(),e);
			LOG.info(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			dataMap.put("code", 0);
		}
        response.setContentType("text/html");
        return dataMap;
    }
    
    public static String downloadFromUrl(String url,String dir) {  
    	  
        try {  
            URL httpurl = new URL(url);  
            String fileName = getFileNameFromUrl(url);  
            System.out.println(fileName);  
            File f = new File(dir + fileName);  
            FileUtils.copyURLToFile(httpurl, f);  
        } catch (Exception e) {  
            e.printStackTrace();  
            return "Fault!";  
        }   
        return "Successful!";  
    }  
      
    public static String getFileNameFromUrl(String url){  
        String name = new Long(System.currentTimeMillis()).toString() + ".X";  
        int index = url.lastIndexOf("/");  
        if(index > 0){  
            name = url.substring(index + 1);  
            if(name.trim().length()>0){  
                return name;  
            }  
        }  
        return name;  
    }
    
}
