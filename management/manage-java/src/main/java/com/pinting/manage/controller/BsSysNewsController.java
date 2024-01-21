package com.pinting.manage.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.core.util.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.enums.NewsReceiverTypeEnum;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_BsSysNewsDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_BsSysNewsModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_NewsList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsSysNews_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_BsSysNewsDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_BsSysNewsModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_NewsList;
import com.pinting.business.hessian.manage.message.ResMsg_BsSysNews_SelectByPrimaryKey;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class BsSysNewsController {
	@Autowired
	@Qualifier("dispatchService")
	public HessianService sysNewsService;
	
	private Logger log = LoggerFactory.getLogger(BsSysNewsController.class);
	
	
	/**
	 * 查询新闻公告
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysNews/{flag}")
	public String sysNewsInit(@PathVariable String flag,ReqMsg_BsSysNews_NewsList req, HttpServletRequest request, HashMap<String, Object> model) {
		if(StringUtils.isBlank(req.getType())){
			if("company_dynamic".equals(flag)){
				req.setType("COMPANY_DYNAMIC_WFANS_ACTIVITY");
			}else if("news".equals(flag)){
				req.setType("NEWS");
			}else if("notice".equals(flag)){
				req.setType("NOTICE");
			}
		}
		model.put("flag", flag);
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage(); 
		if(pageNum==null ||pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(request.getParameter("orderDirection") != null && request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
			model.put("orderField", request.getParameter("orderField"));
		} else {
			req.setOrderDirection("desc");
			req.setOrderField("update_time desc,create_time");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		ResMsg_BsSysNews_NewsList res = (ResMsg_BsSysNews_NewsList) sysNewsService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());

		if(null != res.getValueList()) {
			for(HashMap<String, Object> map : res.getValueList()) {
				String receiverTypeStr = (String) map.get("receiverType");
				String[] receiverTypeList = receiverTypeStr.split(",");
				StringBuffer receiverTypeBuffer = new StringBuffer();
				for (int i = 0; i < receiverTypeList.length; i++) {
					String receiverType = receiverTypeList[i];
					if (NewsReceiverTypeEnum.getEnumByCode(receiverType) != null) {
						receiverTypeList[i] = NewsReceiverTypeEnum.getEnumByCode(receiverType).getDescription();
					}
					receiverTypeBuffer.append(receiverTypeList[i] + ",");
				}
				receiverTypeBuffer.delete(receiverTypeBuffer.length() - 1, receiverTypeBuffer.length());
				map.put("receiverType", receiverTypeBuffer.toString());
			}
		}

		model.put("sysNewsList", res.getValueList());
		return "/sysNews/index";
	}
	
	
	/**
	 * 进入添加
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysNews/detail/{flag}")
	public String detail(@PathVariable String flag,ReqMsg_BsSysNews_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		model.put("flag", flag);
		if("company_dynamic".equals(flag)){
			model.put("type","");
		}else if("news".equals(flag)){
			model.put("type","NEWS");
		}else if("notice".equals(flag)){
			model.put("type","NOTICE");
		}
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_BsSysNews_SelectByPrimaryKey res = (ResMsg_BsSysNews_SelectByPrimaryKey) sysNewsService.handleMsg(req);
			model.put("bsSysNews", res);
		}
		return "/sysNews/detail";
	}
	
	/**
	 * 进入修改页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysNews/news_detail")
	public String newsDetail(ReqMsg_BsSysNews_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_BsSysNews_SelectByPrimaryKey res = (ResMsg_BsSysNews_SelectByPrimaryKey) sysNewsService.handleMsg(req);
			model.put("bsSysNews", res);
		}
		return "/sysNews/news_detail";
	}
	
	/**
	 * 进入查看页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysNews/news_review")
	public String newsReview(ReqMsg_BsSysNews_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if(req.getId() != null && req.getId() != 0) {
			ResMsg_BsSysNews_SelectByPrimaryKey res = (ResMsg_BsSysNews_SelectByPrimaryKey) sysNewsService.handleMsg(req);
			String[] receiverTypeList = res.getReceiverType().split(",");
			StringBuffer receiverTypeBuffer = new StringBuffer();
			for (int i = 0; i < receiverTypeList.length; i++) {
				String receiverType = receiverTypeList[i];
				if (NewsReceiverTypeEnum.getEnumByCode(receiverType) != null) {
					receiverTypeList[i] = NewsReceiverTypeEnum.getEnumByCode(receiverType).getDescription();
				}
				receiverTypeBuffer.append(receiverTypeList[i] + ",");
			}
			receiverTypeBuffer.delete(receiverTypeBuffer.length() - 1, receiverTypeBuffer.length());
			res.setReceiverType(receiverTypeBuffer.toString());
			model.put("bsSysNews", res);
		}
		return "/sysNews/news_review";
	}


	private String receiverTypeText(String receiverType) {
		if(Constants.NEWS_RECEIVER_TYPE_BGW.equals(receiverType)) {
			return "币港湾";
		} else if(Constants.NEWS_RECEIVER_TYPE_BGWHN.equals(receiverType)) {
			return "海宁日报-币港湾";
		} else if(Constants.NEWS_RECEIVER_TYPE_BGWRUIAN.equals(receiverType)) {
			return "瑞安日报-币港湾";
		} else if(Constants.NEWS_RECEIVER_TYPE_BGWKQ.equals(receiverType)) {
			return "柯桥日报-币港湾";
		} else if(Constants.NEWS_RECEIVER_TYPE_BGW178.equals(receiverType)) {
			return "钱报-币港湾";
		} else if(Constants.NEWS_RECEIVER_TYPE_BGWQD.equals(receiverType)) {
			return "七店-币港湾";
		}
		return "";
	}

	private void splitShowTerminal(List<HashMap<String, Object>> valueList) {
		if(null != valueList) {
			for(HashMap<String, Object> map : valueList) {
				String receiverTypeStr = (String) map.get("receiverType");
				String[] receiverTypeList = receiverTypeStr.split(",");
				StringBuffer receiverTypeBuffer = new StringBuffer();
				for (int i = 0; i < receiverTypeList.length; i++) {
					String receiverType = receiverTypeList[i];
					receiverTypeList[i] = receiverTypeText(receiverType);
					receiverTypeBuffer.append(receiverTypeList[i] + ",");
				}
				receiverTypeBuffer.delete(receiverTypeBuffer.length() - 1, receiverTypeBuffer.length());
				map.put("receiverType", receiverTypeBuffer.toString());
			}
		}
	}

	/**
	 * 添加/修改
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysNews/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_BsSysNews_BsSysNewsModify req,String[] receiver,
			String sign,String publishFlag,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String strDate = request.getParameter("publishTime");
		if ("".equals(req.getContent())) {
			return ReturnDWZAjax.fail("内容不能为空！");
		}
		//检查是否存在赋值粘贴的图片（base64）
		if(req.getContent().contains("data:image") && req.getContent().contains("base64")){
			return ReturnDWZAjax.fail("图片必须上传方式加载，请勿直接粘贴！");
		}
		
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		Boolean flag = false;
		if(StringUtils.isNotBlank(req.getReceiverType())){
			req.setKeywords(req.getSubject());
			if("PUBLISHED".equals(publishFlag)){
				req.setStatus("PUBLISHED");
			}else{
				if ("add".equals(sign)) {
					req.setStatus("NOT_PUBLISH");
				}else if ("update".equals(sign)) {
					req.setStatus("PUBLISHED");//修改只有直接发布按钮
				}else {
					req.setStatus("NOT_PUBLISH");
				}
			}
			
			
			req.setmUserId(Integer.parseInt(mUserId));
			req.setSource(req.getSource());
			req.setReadTimes(null);
			if ("add".equals(sign)) {
				req.setCreateTime(new Date());
				if (request.getParameter("publishTime") == null || "".equals(request.getParameter("publishTime"))) {
					req.setPublishTime(new Date()); // 发布时间添加时默认为当前系统时间
				} else {
					try {
						req.setPublishTime(DateUtil.parseDateTime(strDate));
					} catch (Exception e) {
						log.info("新闻公告添加时的发布时间：" + strDate);
					}
				}
			}else{
				req.setUpdateTime(new Date());
				try {
					req.setPublishTime(DateUtil.parseDateTime(strDate));
				} catch (Exception e) {
					log.info("新闻公告编辑时的发布时间：" + strDate);
				}
			}
			ResMsg_BsSysNews_BsSysNewsModify res = (ResMsg_BsSysNews_BsSysNewsModify) sysNewsService.handleMsg(req);
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
				 flag = true;
			} 
		}else{
			return ReturnDWZAjax.fail("请选择发布端口！");
		}
		if (flag) {
			if (req.getId() != null ) {
				return ReturnDWZAjax.success("修改成功！");
			}else {
				return ReturnDWZAjax.success("新增成功！");
			}
			
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	@RequestMapping("/sysNews/delete")
	public @ResponseBody Map<Object, Object> delete(ReqMsg_BsSysNews_BsSysNewsDelete req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_BsSysNews_BsSysNewsDelete res = (ResMsg_BsSysNews_BsSysNewsDelete) sysNewsService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			return ReturnDWZAjax.fail("删除失败，请重试！");
		}
	}
	
	/**
	 * 推荐
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysNews/priority")
	public @ResponseBody Map<Object, Object> changePriority(ReqMsg_BsSysNews_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_BsSysNews_SelectByPrimaryKey res = (ResMsg_BsSysNews_SelectByPrimaryKey) sysNewsService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode()) && (res.getPriority() == null ||res.getPriority() != 1)) {
			ReqMsg_BsSysNews_BsSysNewsModify  reqNews = new ReqMsg_BsSysNews_BsSysNewsModify();
			reqNews.setId(res.getId());
			reqNews.setType(res.getType());
			reqNews.setSubject(res.getSubject());
			reqNews.setKeywords(res.getKeywords());
			reqNews.setSummary(res.getSummary());
			reqNews.setSubjectImg(res.getSubjectImg());
			reqNews.setReceiverType(res.getReceiverType());
			reqNews.setPriority(1);
			reqNews.setmUserId(res.getmUserId().intValue());
			reqNews.setPublishTime(res.getPublishTime());
			reqNews.setSource(res.getSource());
			reqNews.setReadTimes(res.getReadTimes());
			reqNews.setCreateTime(res.getCreateTime());
			reqNews.setUpdateTime(res.getUpdateTime());
			reqNews.setContent(res.getContent());
			reqNews.setStatus(res.getStatus());
			ResMsg_BsSysNews_BsSysNewsModify resNews = (ResMsg_BsSysNews_BsSysNewsModify) sysNewsService.handleMsg(reqNews);
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resNews.getResCode())) {
				return ReturnDWZAjax.success("推荐成功！");
			} else {
				return ReturnDWZAjax.fail("操作失败！");
			}
		} else {
			return ReturnDWZAjax.fail("已经推荐！");
		}
	}
	
	
	/**
	 * 发布/撤下
	 * @param req
	 * @param pubilsh
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/sysNews/publish")
	public @ResponseBody Map<Object, Object> publish(ReqMsg_BsSysNews_SelectByPrimaryKey req,String  pubilsh,
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		ResMsg_BsSysNews_SelectByPrimaryKey res = (ResMsg_BsSysNews_SelectByPrimaryKey) sysNewsService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			ReqMsg_BsSysNews_BsSysNewsModify  reqNews = new ReqMsg_BsSysNews_BsSysNewsModify();
			reqNews.setId(res.getId());
			reqNews.setType(res.getType());
			reqNews.setSubject(res.getSubject());
			reqNews.setKeywords(res.getKeywords());
			reqNews.setSummary(res.getSummary());
			reqNews.setSubjectImg(res.getSubjectImg());
			reqNews.setReceiverType(res.getReceiverType());
			reqNews.setPriority(res.getPriority());
			reqNews.setStatus(pubilsh);
			reqNews.setmUserId(res.getmUserId().intValue());
			reqNews.setPublishTime(res.getPublishTime());
			/*if ("PUBLISHED".endsWith(pubilsh)) {
				reqNews.setPublishTime(new Date());
			}else {
				reqNews.setPublishTime(res.getPublishTime());
			}*/
			reqNews.setSource(res.getSource());
			reqNews.setReadTimes(res.getReadTimes());
			reqNews.setCreateTime(res.getCreateTime());
			reqNews.setUpdateTime(res.getUpdateTime());
			reqNews.setContent(res.getContent());
			ResMsg_BsSysNews_BsSysNewsModify resNews = (ResMsg_BsSysNews_BsSysNewsModify) sysNewsService.handleMsg(reqNews);
			if (ConstantUtil.BSRESCODE_SUCCESS.equals(resNews.getResCode())) {
				return ReturnDWZAjax.success("修改成功！");
			} else {
				return ReturnDWZAjax.fail("操作失败！");
			}
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
		
	
	}
	
}
