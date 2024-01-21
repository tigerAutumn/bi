package com.pinting.cfcatransfer.bigangwan.controller;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.servlet.support.RequestContext;

import com.pinting.core.util.GlobEnvUtil;




@Controller
public class SignController {
	private Logger log = LoggerFactory.getLogger(SignController.class);
	private static final String STORE_FILE_DIR="D://home//pinting//server//ftp";//文件保存的路径 
	
	@RequestMapping("/pdfFileUpload")  
	public String pdfFileUpload(HttpServletRequest request,  
	            HttpServletResponse response,RequestContext req) throws ServletException, IOException {  
	        boolean isMultipart = ServletFileUpload.isMultipartContent(request);  
	        boolean flag = false;
	        if (isMultipart) {  
	            try {  
	                MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
	                multiRequest.getFileMap();
	                Iterator<String> ite = multiRequest.getFileNames();
	                while(ite.hasNext()){  
	                    MultipartFile file = multiRequest.getFile(ite.next());
	                    if(file!=null){  
	                        File localFile = new File(GlobEnvUtil.get("cfca.seal.new.pdfSrcPath")
	                        		+file.getName()+"/"+file.getOriginalFilename());  
	                        if(!localFile.getParentFile().exists()){
	                        	localFile.getParentFile().mkdirs();
	                        }
	                        try {  
	                            file.transferTo(localFile); //将上传文件写到服务器上指定的文件 
	                            flag = true;
	                        } catch (IllegalStateException e) {  
	                            e.printStackTrace();  
	                        } catch (IOException e) {  
	                            e.printStackTrace();  
	                        }  
	                    }  
	                }
	                if(flag){
	                	response.setCharacterEncoding("UTF-8");    
		        		response.getWriter().println("0000"); 
	                }
	               
	            } catch (Exception e) {  
	                e.printStackTrace();  
	            }  
	        } 
			return null;  
	    }  

}
