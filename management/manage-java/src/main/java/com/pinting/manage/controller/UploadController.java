package com.pinting.manage.controller;


import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

import net.sf.json.JSONObject;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.pinting.core.util.StringUtil;
import com.pinting.manage.model.Upload;
import com.pinting.util.GlobEnv;


@Controller
public class UploadController{

	private Logger LOG = LoggerFactory.getLogger(UploadController.class);
	
	/**
	 * 上传文件
	 * 
	 * @param upload
	 * @return
	 */
	@RequestMapping("/upload/pho")
	public @ResponseBody String upload(Upload upload) {
		String name = upload.getImage().getOriginalFilename();

		int index = name.lastIndexOf(".");
		String extendName = name.substring(index);
		long date = new Date().getTime();
		String path = "";
		if(extendName.equals(".jpg") || extendName.equals(".png") || extendName.equals(".jpeg") || extendName.equals(".gif")){
		  path = "/pic/" + date + extendName;
		}else{
			path = "/file/" + date + extendName;
		}
		try {
			FileUtils.copyInputStreamToFile(upload.getImage().getInputStream(), new File(GlobEnv.get("server.bank.img.upload") + path));
		} catch (IOException e) {
			LOG.error(e.getMessage(),e);
		}
		
		return path;
	}
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/img")
	public @ResponseBody String uploadImg(MultipartHttpServletRequest request) {
		/*StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();*/ 
		
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			if (file.getSize() > 1048576) {
				obj.put("err", 1);
				obj.put("msg", "上传文件过大(最大1M)!");
				return obj.toString();
			}
			
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			long date = new Date().getTime();
			String path = GlobEnv.get("server.bank.img.upload") + "/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url = GlobEnv.get("server.bank.img.dbroot") + "/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			msg.put("url", url);
			msg.put("localname", fileName);
			obj.put("msg", msg);
			obj.put("err", "");
			
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	
	/**
	 * 上传图片
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/newsImg")
	public @ResponseBody String uploadNewsImg(MultipartHttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFileMap().get("file");
			}
			
			// 实际传入文件大小扩大至1.23倍   1259952 = 1.23*102400
			if (file.getSize() > 125952) {
				obj.put("err", 1);
				obj.put("msg", "上传文件过大(最大100KB)!");
				return obj.toString();
			}
			
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			long date = new Date().getTime();
			String path = GlobEnv.get("news.img.upload") + "/pic/" + date + extendName;
			LOG.info(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			//String url = GlobEnv.get("news.web") + "/resources/upload/news/pic/" + date + extendName;
			String url =GlobEnv.get("gen.web") + "/resources/upload/news/pic/" + date + extendName;
			LOG.info(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			
		    BufferedImage bi=ImageIO.read(file.getInputStream()); 
			int width=bi.getWidth();  
		    int height=bi.getHeight();
		    msg.put("width", width);
		    msg.put("height", height);
			msg.put("url", url);
			msg.put("localname", fileName);
			obj.put("msg", msg);
			obj.put("err", "");
			
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	/**
	 * 上传广告
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/flash")
	public @ResponseBody String uploadFlash(Long date,Integer small,MultipartHttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			String path = GlobEnv.get("server.bank.img.upload") + "/pic/" + date + (small==1?"_small":"")+extendName;
			LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url = GlobEnv.get("server.bank.img.dbroot") + "/pic/" + date +(small==1?"_small":"")+ extendName;
			LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			msg.put("url", url);
			msg.put("localname", fileName);
			obj.put("msg", msg);
			obj.put("err", "");
			
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	
	
	@RequestMapping(value = "/upload/bannerImg")
	public @ResponseBody String uploadBannerImg(MultipartHttpServletRequest request) {
		/*StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();*/ 
		
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			long date = new Date().getTime();
			String path = GlobEnv.get("banner.img.upload") + "/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url =GlobEnv.get("gen.web") + "/resources/upload/banner/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			msg.put("url", url);
			msg.put("pathUrl", "/resources/upload/banner/pic/" + date + extendName);
			msg.put("localname", fileName);
			
		    BufferedImage bi=ImageIO.read(file.getInputStream()); 
			int width=bi.getWidth();  
		    int height=bi.getHeight();
		    msg.put("width", width);
		    msg.put("height", height);
		    obj.put("msg", msg);
			obj.put("err", "");
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	
	/**
	 * 图片上传公用类
	 * @param module 上传图片的模块文件夹参数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/image")
	@ResponseBody
	public String uploadImage(MultipartHttpServletRequest request) {
		String module = StringUtil.isEmpty(request.getParameter("module")) ? "" : File.separator + request.getParameter("module");
		JSONObject obj = new JSONObject();
		Map<String,Object> msg = new HashMap<String, Object>();
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			BufferedImage bi=ImageIO.read(file.getInputStream()); 
			int width=bi.getWidth();  
			int height=bi.getHeight();
			if(StringUtil.isNotEmpty(request.getParameter("width")) && StringUtil.isNotEmpty(request.getParameter("height"))) {
				int receiveWidth = Integer.valueOf(request.getParameter("width"));
				int receiveHeight = Integer.valueOf(request.getParameter("height"));
				if(width == receiveWidth && height == receiveHeight) {
					String fileName = file.getOriginalFilename();
					int index = fileName.lastIndexOf(".");
					String extendName = fileName.substring(index);
					long date = new Date().getTime();
					String path = GlobEnv.get("img.upload")+ module + "/pic/" + date + extendName;
					LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
					String url =GlobEnv.get("gen.web") + "/resources/upload"+module +"/pic/" + date + extendName;
					LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
					msg.put("url", url);
					msg.put("pathUrl", "/resources/upload"+ module +"/pic/" + date + extendName);
					msg.put("localname", fileName);
				}
			}else {
				String fileName = file.getOriginalFilename();
				int index = fileName.lastIndexOf(".");
				String extendName = fileName.substring(index);
				long date = new Date().getTime();
				String path = GlobEnv.get("img.upload")+ module + "/pic/" + date + extendName;
				LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
				String url =GlobEnv.get("gen.web") + "/resources/upload"+module +"/pic/" + date + extendName;
				LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
				msg.put("url", url);
				msg.put("pathUrl", "/resources/upload"+ module +"/pic/" + date + extendName);
				msg.put("localname", fileName);
			}
			
			msg.put("width", width);
			msg.put("height", height);
			obj.put("msg", msg);
			obj.put("err", "");
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	
	@RequestMapping(value = "/upload/wxAutoReplyImg")
	public @ResponseBody String wxAutoReplyImg(MultipartHttpServletRequest request) {
		/*StringBuffer url = request.getRequestURL();  
		String tempContextUrl = url.delete(url.length() - request.getRequestURI().length(), url.length()).append("/").toString();*/ 
		
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			long date = new Date().getTime();
			String path = GlobEnv.get("wxAutoReply.img.upload") + "/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url =GlobEnv.get("gen.web") + "/resources/upload/wxAutoReply/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			msg.put("url", url);
			msg.put("pathUrl", "/resources/upload/wxAutoReply/pic/" + date + extendName);
			msg.put("localname", fileName);
			
		    BufferedImage bi=ImageIO.read(file.getInputStream()); 
			int width=bi.getWidth();  
		    int height=bi.getHeight();
		    msg.put("width", width);
		    msg.put("height", height);
		    obj.put("msg", msg);
			obj.put("err", "");
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	
	// 资产合作产品图片上传
	@RequestMapping(value = "/upload/propertyInfoImg")
	public @ResponseBody String uploadPropertyInfo(MultipartHttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			long date = new Date().getTime();
			String path = GlobEnv.get("img.upload") + "/property/pic/" + date + "@&!!@&" + fileName; //存储路径
			LOG.info(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url = GlobEnv.get("gen.web") + "/resources/upload/property/pic/" + date + "@&!!@&" + fileName; //网页显示路径
			LOG.info(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			msg.put("url", url);
			msg.put("pathUrl", "/resources/upload/property/pic/" + date + "@&!!@&" + fileName);
			msg.put("localname", fileName);
			msg.put("extendName", extendName);
		    BufferedImage bi=ImageIO.read(file.getInputStream()); 
			int width=bi.getWidth();  
		    int height=bi.getHeight();
		    msg.put("width", width);
		    msg.put("height", height);
		    //回显框的id
		    msg.put("element_id", request.getParameter("element_id"));
		    obj.put("msg", msg);
			obj.put("err", "");
		}catch(Exception e){
			LOG.info(e.getMessage(),e);
			LOG.info(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	
	
//=======================================运营报告上传S===================================================	
	
	/**
	 * 运营报告上传---背景图片上传
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/operationReportImg")
	public @ResponseBody String operationReportImg(MultipartHttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			if (file.getSize() > 102400) {
				obj.put("err", 1);
				obj.put("msg", "上传文件过大(最大100KB)!");
				return obj.toString();
			}	
			
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			String firstName = fileName.substring(0,index);
			long date = new Date().getTime();
			String path = GlobEnv.get("operation.report.upload") + "/img/" + firstName+"_" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url = GlobEnv.get("gen.web") + "/resources/upload/report/img/" + firstName+"_" + date + extendName;
			String pathUrl = "/resources/upload/report/img/" + firstName+"_" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			
		    BufferedImage bi=ImageIO.read(file.getInputStream()); 
			int width=bi.getWidth();  
		    int height=bi.getHeight();
		    
		    if (width != 310 ||  height != 337) {
				File imgFile = new File(path);
				imgFile.delete();
				obj.put("err", 1);
				obj.put("msg", "图片大小不符合,310X337！");
				return obj.toString();
			}
		    
		    msg.put("width", width);
		    msg.put("height", height);
			msg.put("url", url);
			msg.put("pathUrl", pathUrl);
			msg.put("localname", fileName);
			obj.put("msg", msg);
			obj.put("err", "");
			
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		
		return obj.toString();
	}
	
	/**
	 * 运营报告上传---PDF文件上传
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/operationReportFile")
	public @ResponseBody String operationReportFile(MultipartHttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			if (file.getSize() > 10485760) {
				obj.put("err", 1);
				obj.put("msg", "上传文件过大(最大10M)!");
				return obj.toString();
			}
			
			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			String firstName = fileName.substring(0,index);
			long date = new Date().getTime();
			String path = GlobEnv.get("operation.report.upload") + "/file/" + firstName+"_" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url = GlobEnv.get("gen.web") + "/resources/upload/report/file/" + firstName+"_" + date + extendName;
			String pathUrl = "/resources/upload/report/file/" + firstName+"_" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			
			msg.put("url", url);
			msg.put("pathUrl", pathUrl);
			msg.put("localname", fileName);
			obj.put("msg", msg);
			obj.put("err", "");
			
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		return obj.toString();
	}
	
	
	
	//=======================================运营报告E===================================================	
	
	/**
	 * APP活动管理-图片上传
	 * @param module 上传图片的模块文件夹参数
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/appActiveImage")
	@ResponseBody
	public String appActiveImage(MultipartHttpServletRequest request) {
		String module = StringUtil.isEmpty(request.getParameter("module")) ? "" : request.getParameter("module");
		JSONObject obj = new JSONObject();
		Map<String,Object> msg = new HashMap<String, Object>();
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}
			
			BufferedImage bi=ImageIO.read(file.getInputStream()); 
			int width=bi.getWidth();  
			int height=bi.getHeight();
			if(StringUtil.isNotEmpty(request.getParameter("width")) && StringUtil.isNotEmpty(request.getParameter("height"))) {
				int receiveWidth = Integer.valueOf(request.getParameter("width"));
				int receiveHeight = Integer.valueOf(request.getParameter("height"));
				if(width == receiveWidth && height == receiveHeight) {
					String fileName = file.getOriginalFilename();
					int index = fileName.lastIndexOf(".");
					String extendName = fileName.substring(index);
					long date = new Date().getTime();
					String path = GlobEnv.get("img.upload")+ module + "/pic/" + date + extendName;
					LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
					FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
					String url =GlobEnv.get("gen.web") + "/resources/upload"+module +"/pic/" + date + extendName;
					LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
					msg.put("url", url);
					msg.put("pathUrl", "/resources/upload"+ module +"/pic/" + date + extendName);
					msg.put("localname", fileName);
				}
			}else {
				String fileName = file.getOriginalFilename();
				int index = fileName.lastIndexOf(".");
				String extendName = fileName.substring(index);
				long date = new Date().getTime();
				String path = GlobEnv.get("img.upload")+ module + "/pic/" + date + extendName;
				LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
				FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
				String url =GlobEnv.get("gen.web") + "/resources/upload"+module +"/pic/" + date + extendName;
				LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
				msg.put("url", url);
				msg.put("pathUrl", "/resources/upload"+ module +"/pic/" + date + extendName);
				msg.put("localname", fileName);
			}
			
			msg.put("width", width);
			msg.put("height", height);
			obj.put("msg", msg);
			obj.put("err", "");
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}
		return obj.toString();
	}

	/**
	 * 积分商城兑换商品——图片上传
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/upload/mallCommodityImg")
	public @ResponseBody String uploadMallCommodityImg(MultipartHttpServletRequest request) {
		JSONObject obj = new JSONObject();
		Map<String,Object> msg;
		try{
			MultipartFile file = request.getFile("filedata");
			if(null == file) {
				file = request.getFile("file");
			}

			// 实际传入文件大小扩大至1.23倍   1259952 = 1.23*102400
			// 原因：传入94.5kb图片，size值：118947  >  102400(100Kb)
			if (file.getSize() > 1259952) {
				obj.put("err", 1);
				obj.put("msg", "上传文件过大(最大100KB)!");
				return obj.toString();
			}

			String fileName = file.getOriginalFilename();
			int index = fileName.lastIndexOf(".");
			String extendName = fileName.substring(index);
			long date = new Date().getTime();
			String path = GlobEnv.get("mall.commodity.img.upload") + "/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传路径>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+path);
			FileUtils.copyInputStreamToFile(file.getInputStream(), new File(path));
			String url =GlobEnv.get("gen.web") + "/resources/upload/mallCommodity/pic/" + date + extendName;
			LOG.error(">>>>>>>>>>>>>>上传结束>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>访问路径："+url);
			msg=new HashMap<String, Object>();
			msg.put("url", url);
			msg.put("pathUrl", "/resources/upload/mallCommodity/pic/" + date + extendName);
			msg.put("localname", fileName);

			BufferedImage bi=ImageIO.read(file.getInputStream());
			int width=bi.getWidth();
			int height=bi.getHeight();
			msg.put("width", width);
			msg.put("height", height);
			obj.put("msg", msg);
			obj.put("err", "");
		}catch(Exception e){
			LOG.error(e.getMessage(),e);
			LOG.error(">>>>>>>>>>>>>>上传失败>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+e);
			obj.put("err", 1);
			obj.put("msg", "上传文件失败!");
		}

		return obj.toString();
	}
}
