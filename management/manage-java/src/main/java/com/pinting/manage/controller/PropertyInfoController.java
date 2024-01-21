package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.hessian.manage.message.ReqMsg_PropertyInfo_PropertyInfoDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_PropertyInfo_PropertyInfoList;
import com.pinting.business.hessian.manage.message.ReqMsg_PropertyInfo_PropertyInfoModify;
import com.pinting.business.hessian.manage.message.ReqMsg_PropertyInfo_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_PropertyInfo_PropertyInfoDelete;
import com.pinting.business.hessian.manage.message.ResMsg_PropertyInfo_PropertyInfoList;
import com.pinting.business.hessian.manage.message.ResMsg_PropertyInfo_PropertyInfoModify;
import com.pinting.business.model.BsPropertyInfo;
import com.pinting.business.service.manage.MPropertyInfoService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 
 * @author shh
 *
 */
@Controller
public class PropertyInfoController {
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService propertyService;
	
	@Autowired
	private MPropertyInfoService mPropertyInfoService;
	
	@RequestMapping("/propertyInfo/index")
	public String propertyInfoInit(ReqMsg_PropertyInfo_PropertyInfoList req, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_100_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		String orderDirection = request.getParameter("orderDirection");
		String orderField = request.getParameter("orderField");
		if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
			req.setOrderDirection(orderDirection);
			req.setOrderField(orderField);
			model.put("orderDirection", orderDirection);
			model.put("orderField", orderField);
		} 
		ResMsg_PropertyInfo_PropertyInfoList res = (ResMsg_PropertyInfo_PropertyInfoList) propertyService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", res.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		ArrayList<HashMap<String,Object>> valueList= new ArrayList<HashMap<String,Object>>();
		if(res.getValueList() != null && res.getValueList().size() != 0 ) { // 数据库查询结果不为空的时候 进行遍历
			for(HashMap<String, Object> s : res.getValueList()){
				String id = s.get("id").toString();
				String propertyName = s.get("propertyName").toString();
				Date updateTime = (Date) s.get("updateTime");
				// 查询该资产合作名称 是否已被产品表引用
				int count = mPropertyInfoService.findCountByProductId(Integer.parseInt(id));
				s.put("count", count);
				s.put("propertyName", propertyName);
				s.put("id", id);
				s.put("updateTime", updateTime);
				valueList.add(s);
			}
			model.put("propertyInfoList",valueList);
		}
		return "/propertyInfo/propertyInfo_index";
	}
	
	/**
	 * 进入添加 编辑页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/propertyInfo/detail")
	public String detail(ReqMsg_PropertyInfo_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
	    String count = request.getParameter("count");
        count = StringUtil.isBlank(count) ? "0" : count;
        model.put("canEdit", Integer.parseInt(count) <= 0 ? "yes" : "no");
		if (req.getId() != null && req.getId() != 0) { //编辑
			BsPropertyInfo bsPropertyInfo = mPropertyInfoService.selectByPrimaryId(req.getId());
			// 合作协议
			if(!StringUtil.isBlank(bsPropertyInfo.getCoopProtocolPics())) {
				Map<String,String> coopMap = new HashMap<String,String>();
				String[] urlCoops = bsPropertyInfo.getCoopProtocolPics().split(";");
				List<Map<String, Object>> coopList = new ArrayList<Map<String, Object>>(); // coopList 存放编辑时的图片url，保证添加跟编辑上传图片的顺序一致(url顺序)不影响其他位置的展示
				for (String urlCoop : urlCoops) {
					String[] array = urlCoop.split("/");
					String fullName = array[array.length-1];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", fullName);
					map.put("url", urlCoop);
					coopList.add(map);
//					coopMap.put(fullName, urlCoop);
				}
				model.put("coopList", coopList);
			}
			
			// 借款合同示例
			if(!StringUtil.isBlank(bsPropertyInfo.getLoanProtocolPics())) {
				Map<String,String> loanMap = new HashMap<String,String>();
				String[] urlProtocols = bsPropertyInfo.getLoanProtocolPics().split(";");
				List<Map<String, Object>> loanList = new ArrayList<Map<String, Object>>();
				for (String urlProtocol : urlProtocols) {
					String[] array = urlProtocol.split("/");
					String fullName = array[array.length-1];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", fullName);
					map.put("url", urlProtocol);
					loanList.add(map);
//					loanMap.put(fullName, urlProtocol);
				}
				model.put("loanList", loanList);
			}
			
			// 合作机构审查示例图片
			if(!StringUtil.isBlank(bsPropertyInfo.getOrgnizeCheckPics())) {
				Map<String,String> orgnizeMap = new HashMap<String,String>();
				String[] urlCheckPics = bsPropertyInfo.getOrgnizeCheckPics().split(";");
				List<Map<String, Object>> orgnizeList = new ArrayList<Map<String, Object>>();
				for (String urlCheckPic : urlCheckPics) {
					String[] array = urlCheckPic.split("/");
					String fullName = array[array.length-1];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", fullName);
					map.put("url", urlCheckPic);
					orgnizeList.add(map);
//					orgnizeMap.put(fullName, urlCheckPic);
				}
				model.put("orgnizeList", orgnizeList);
			}
			
			// 三方担保合同示例图片 
			if(!StringUtil.isBlank(bsPropertyInfo.getRatingGradePics())) {
				Map<String,String> ratingMap = new HashMap<String,String>();
				String[] urlGradePics = bsPropertyInfo.getRatingGradePics().split(";");
				List<Map<String, Object>> ratingList = new ArrayList<Map<String, Object>>();
				for (String urlGradePic : urlGradePics) {
					String[] array = urlGradePic.split("/");
					String fullName = array[array.length-1];
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("name", fullName);
					map.put("url", urlGradePic);
					ratingList.add(map);
//					ratingMap.put(fullName, urlGradePic);
				}
				model.put("ratingList", ratingList);
			}
			
			model.put("bsPropertyInfo", bsPropertyInfo);
			model.put("num", 9);
		}
		else {
			//新增
			model.put("num", 0);
		}
		
		return "/propertyInfo/propertyInfo_detail";
	}
	
	/**
	 * 新增 修改
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/propertyInfo/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_PropertyInfo_PropertyInfoModify req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		String[] protocoPicArray = request.getParameterValues("imgUrlActive_protoco_pic");
		String[] loanCheckPicArray = request.getParameterValues("imgUrlActive_loan_check_pics");
		String[] loanGradePicArray = request.getParameterValues("imgUrlActive_loan_grade_pics");
		String[] loanProtocolPicArray = request.getParameterValues("imgUrlActive_loan_protocol");
		
		if (protocoPicArray != null && protocoPicArray.length != 0) {
			req.setCoopProtocolPics(array2String(protocoPicArray));
		} else { // 第一张图片删除
			req.setCoopProtocolPics("");
		}
		if (loanCheckPicArray != null && loanCheckPicArray.length != 0) {
			req.setOrgnizeCheckPics(array2String(loanCheckPicArray));
		} else {
			req.setOrgnizeCheckPics("");
		}
		if (loanGradePicArray != null && loanGradePicArray.length != 0) {
			req.setRatingGradePics(array2String(loanGradePicArray));
		} else {
			req.setRatingGradePics("");
		}
		if (loanProtocolPicArray != null && loanProtocolPicArray.length != 0) {
			req.setLoanProtocolPics(array2String(loanProtocolPicArray));
		} else {
			req.setLoanProtocolPics("");
		}
		
		ResMsg_PropertyInfo_PropertyInfoModify res = (ResMsg_PropertyInfo_PropertyInfoModify) propertyService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("新增成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.success("修改成功！");
		} else if (res.getFlag() == 3) {
			return ReturnDWZAjax.toAjaxString("301", "资产合作方名称已存在");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	@RequestMapping("/propertyInfo/delete")
	public @ResponseBody Map<Object, Object> delete(ReqMsg_PropertyInfo_PropertyInfoDelete req, 
			HttpServletRequest request, Map<String, Object> model) {
		ResMsg_PropertyInfo_PropertyInfoDelete res = (ResMsg_PropertyInfo_PropertyInfoDelete) propertyService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			return ReturnDWZAjax.fail("删除失败，请重试！");
		}
	}
	
	private String array2String(String[] array) {
		StringBuffer buffer = new StringBuffer();
		for(int i=0;i<array.length;i++) {
			buffer.append(array[i]);
			if(i < array.length - 1) {
				buffer.append(";");
			}
		}
		return buffer.toString();
	}

}
