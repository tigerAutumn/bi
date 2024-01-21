package com.pinting.gateway.xicai.in.controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.encrypt.MD5Util;
import com.pinting.gateway.business.out.service.SendBusinessService;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_GetP2P;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_InvestCount;
import com.pinting.gateway.hessian.message.xicai.G2BReqMsg_Xicai_UserCount;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_GetP2P;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_InvestCount;
import com.pinting.gateway.hessian.message.xicai.G2BResMsg_Xicai_UserCount;
import com.pinting.gateway.util.ClientMacAddrUtil;
import com.pinting.gateway.util.Constants;
import com.pinting.gateway.xicai.out.config.Config;

@Controller
@RequestMapping("xicai")
public class XicaiController {

	private static Logger logger = LoggerFactory.getLogger(XicaiController.class);
	
	@Autowired
	private SendBusinessService sendBusinessService;
	
	@RequestMapping("getP2P")
	public String getP2P(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 1、获取参数
		 * 2、校验请求客户端IP地址合法性
		 * 3、校验参数合法性
		 * 4、查询p2p产品信息
		 * 5、返回数据
		 */
		 //1、接收参数
		String pid = request.getParameter("pid");
		String client_secret = request.getParameter("client_secret");
		logger.info("产品数据接口，接收参数：【pid="+pid+"client_secret="+client_secret+"】");
		 //2、检查客户端IP地址合法性
		String ipAddr = ClientMacAddrUtil.getIpAddr(request);
		logger.info("产品数据接口，请求客户端IP地址：【ipAddr="+ipAddr+"】");
    	//String ipAddr = "210.73.220.214";
    	if (!Constants.CSAI_AUTO_LOGIN_IP_ADDR1.equals(ipAddr) && !Constants.CSAI_AUTO_LOGIN_IP_ADDR2.equals(ipAddr) && !Constants.CSAI_AUTO_LOGIN_IP_ADDR3.equals(ipAddr)) {
			return null;
		}
    	 //3、校验参数
    	if (pid == null ||"".equals(pid) || client_secret == null ||"".equals(client_secret)) {
			return null ;
		}
		 //4、校验请求的合法性
    	if (!Config.client_secret.equals(client_secret)) {
			return null;
		}
		 //5、产品详情
    	
    	G2BReqMsg_Xicai_GetP2P g2bReqMsg_Xicai_GetP2P = new G2BReqMsg_Xicai_GetP2P();
    	g2bReqMsg_Xicai_GetP2P.setPid(Integer.parseInt(pid));
    	G2BResMsg_Xicai_GetP2P g2bResMsg_Xicai_GetP2P =  sendBusinessService.sendXicaiGetP2P(g2bReqMsg_Xicai_GetP2P);
    	g2bResMsg_Xicai_GetP2P.getData().put("link_website", GlobEnvUtil.get("product.return.link")+"?id="+pid+"&agent=xicai");
    	g2bResMsg_Xicai_GetP2P.setResCode(null);
    	g2bResMsg_Xicai_GetP2P.setResMsg(null);
    	//6、返回数据
		Gson gson = new Gson();  
        String jsondata = gson.toJson(g2bResMsg_Xicai_GetP2P);  
        jsondata = JSONObject.toJSON(g2bResMsg_Xicai_GetP2P).toString();
        logger.info("产品数据接口，返回数据：【"+jsondata+"】");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter pw = null;
        try {
        	pw = response.getWriter();
        	pw.write(jsondata);
        	pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
		return null;
	}
	
	
	
	@RequestMapping("p2p_user_count")
	public String p2pUserCount(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 用户统计接口：
		 * 提供接口给希财调用查询用户统计情况
		 * 1、接收参数
		 * 2、检查客户端IP地址合法性
		 * 3、校验请求的合法性
		 * 4、校验参数
		 * 5、查询希财用户导入情况
		 * 6、返回数据
		 */
		//1、接收参数
		String t = request.getParameter("t");
		String token = request.getParameter("token");
		String startdate = request.getParameter("startdate");  //格式2015-1-20 12:00
		String enddate = request.getParameter("enddate");
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		
		logger.info("用户统计接口，请求数据：【"+"t="+t+"token="+token+"startdate="+startdate +"enddate="+enddate +"page="+page +"pagesize="+pagesize +"】");
    	//2、检查客户端IP地址合法性
    	String ipAddr = ClientMacAddrUtil.getIpAddr(request);
    	logger.info("用户统计接口，请求客户端IP地址：【ipAddr="+ipAddr+"】");
    	//String ipAddr = "210.73.220.214";
    	if (!Constants.CSAI_AUTO_LOGIN_IP_ADDR1.equals(ipAddr) && !Constants.CSAI_AUTO_LOGIN_IP_ADDR2.equals(ipAddr)&& !Constants.CSAI_AUTO_LOGIN_IP_ADDR3.equals(ipAddr)) {
			return null;
		}
    	
    	if (t == null ||"".equals(t) || token == null ||"".equals(token)) {
			return null ;
		}
    	
    	//3、校验请求的合法性 token=md5(md5(t)+client_secret)
    	String tokenCheck =  MD5Util.encryptMD5(MD5Util.encryptMD5(t)+Config.client_secret);
    	if (!tokenCheck.equals(token)) {
			return null;
		}
    	
    	//4、参数检查
    	if (startdate == null || "".equals(startdate) || enddate == null || "".equals(enddate)) {
    		startdate = null;
    		enddate = null;
		}
    	
    	if (startdate == null || "".equals(startdate) ) {
    		page = "1";
		}
    	
    	if (pagesize == null || "".equals(pagesize) ) {
			pagesize = "10";
		}
    	// 5、查询希财用户导入情况
    	G2BReqMsg_Xicai_UserCount g2bReqMsg_Xicai_UserCount = new G2BReqMsg_Xicai_UserCount();
    	g2bReqMsg_Xicai_UserCount.setStartdate(startdate);
    	g2bReqMsg_Xicai_UserCount.setEnddate(enddate);
    	g2bReqMsg_Xicai_UserCount.setPage(Integer.parseInt(page));
    	g2bReqMsg_Xicai_UserCount.setPagesize(Integer.parseInt(pagesize));
    	G2BResMsg_Xicai_UserCount g2bResMsg_Xicai_UserCount =  sendBusinessService.sendXicaiUserCount(g2bReqMsg_Xicai_UserCount);
    	g2bResMsg_Xicai_UserCount.setResCode(null);
    	g2bResMsg_Xicai_UserCount.setResMsg(null);
    	Gson gson = new Gson();  
        String jsondata = gson.toJson(g2bResMsg_Xicai_UserCount);  
        logger.info("用户统计接口，返回数据：【"+jsondata+"】");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter pw = null;
        try {
        	pw = response.getWriter();
        	pw.write(jsondata);
        	pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
		return null;
	}
	
	
	@RequestMapping("p2p_invest_count")
	public String p2pInvestCount(HttpServletRequest request, HttpServletResponse response) {
		/**
		 * 投资统计接口：
		 * 提供接口给希财调用查询用户投资情况
		 * 1、接收参数
		 * 2、检查客户端IP地址合法性
		 * 3、校验请求的合法性
		 * 4、校验参数
		 * 5、查询希财用户投资情况
		 * 6、返回数据
		 */
		//1、接收参数
		String t = request.getParameter("t");
		String token = request.getParameter("token");
		String startdate = request.getParameter("startdate");  //格式2015-1-20 12:00
		String enddate = request.getParameter("enddate");
		String page = request.getParameter("page");
		String pagesize = request.getParameter("pagesize");
		
		logger.info("投资统计接口，请求数据：【"+"t="+t+"token="+token+"startdate="+startdate +"enddate="+enddate +"page="+page +"pagesize="+pagesize +"】");
    	//2、检查客户端IP地址合法性
    	String ipAddr = ClientMacAddrUtil.getIpAddr(request);
    	logger.info("投资统计接口，请求客户端IP地址：【ipAddr="+ipAddr+"】");
    	//String ipAddr = "210.73.220.214";
    	if (!Constants.CSAI_AUTO_LOGIN_IP_ADDR1.equals(ipAddr) && !Constants.CSAI_AUTO_LOGIN_IP_ADDR2.equals(ipAddr) && !Constants.CSAI_AUTO_LOGIN_IP_ADDR3.equals(ipAddr)) {
			return null;
		}
    	
    	if (t == null ||"".equals(t) || token == null ||"".equals(token)) {
			return null ;
		}
    	
    	//3、校验请求的合法性 token=md5(md5(t)+client_secret)
    	String tokenCheck =  MD5Util.encryptMD5(MD5Util.encryptMD5(t)+Config.client_secret);
    	if (!tokenCheck.equals(token)) {
			return null;
		}
    	
    	//4、参数检查
    	if (startdate == null || "".equals(startdate) || enddate == null || "".equals(enddate)) {
    		startdate = null;
    		enddate = null;
		}
    	
    	if (startdate == null || "".equals(startdate) ) {
    		page = "1";
		}
    	
    	if (pagesize == null || "".equals(pagesize) ) {
			pagesize = "10";
		}
    	//5、查询希财用户投资情况
    	G2BReqMsg_Xicai_InvestCount g2bReqMsg_Xicai_InvestCount = new G2BReqMsg_Xicai_InvestCount();
    	g2bReqMsg_Xicai_InvestCount.setStartdate(startdate);
    	g2bReqMsg_Xicai_InvestCount.setEnddate(enddate);
    	g2bReqMsg_Xicai_InvestCount.setPage(Integer.parseInt(page));
    	g2bReqMsg_Xicai_InvestCount.setPagesize(Integer.parseInt(pagesize));
    	G2BResMsg_Xicai_InvestCount g2bResMsg_Xicai_InvestCount =  sendBusinessService.sendXicaiInvestCount(g2bReqMsg_Xicai_InvestCount);
    	g2bResMsg_Xicai_InvestCount.setResCode(null);
    	g2bResMsg_Xicai_InvestCount.setResMsg(null);
    	//6、返回数据
    	Gson gson = new Gson();  
        String jsondata = gson.toJson(g2bResMsg_Xicai_InvestCount);  
        logger.info("投资统计接口，返回数据：【"+jsondata+"】");
        response.setHeader("content-type", "text/html;charset=UTF-8");
        PrintWriter pw = null;
        try {
        	pw = response.getWriter();
        	pw.write(jsondata);
        	pw.flush();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if(pw != null) {
				pw.close();
			}
		}
		return null;
	}
}
