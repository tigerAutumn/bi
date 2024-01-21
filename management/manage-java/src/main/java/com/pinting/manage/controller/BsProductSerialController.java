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

import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_ProductSerialDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_ProductSerialList;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_ProductSerialModify;
import com.pinting.business.hessian.manage.message.ReqMsg_ProductSerial_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_ProductSerial_ProductSerialDelete;
import com.pinting.business.hessian.manage.message.ResMsg_ProductSerial_ProductSerialList;
import com.pinting.business.hessian.manage.message.ResMsg_ProductSerial_ProductSerialModify;
import com.pinting.business.model.BsProductSerial;
import com.pinting.business.model.BsTermProductCode;
import com.pinting.business.model.BsTermProductCodeExample;
import com.pinting.business.service.manage.BsProductSerialService;
import com.pinting.business.service.manage.MTermProductCodeService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.ConstantUtil;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

/**
 * 
 * @author shh
 *
 */
@Controller
public class BsProductSerialController {
	
	@Autowired
	@Qualifier("dispatchService")
	private HessianService productSerialService;
	@Autowired
	private BsProductSerialService bsProductSerialService;
	@Autowired
	private MTermProductCodeService termProductCodeService;
	
	
	/**
	 * 列表
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsProductSerial/index")
	public String productSerialInit(ReqMsg_ProductSerial_ProductSerialList req, HttpServletRequest request, Map<String, Object> model) {
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
		ResMsg_ProductSerial_ProductSerialList res = (ResMsg_ProductSerial_ProductSerialList) productSerialService.handleMsg(req);
		model.put("req", req);
		model.put("pageNum", res.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		ArrayList<HashMap<String,Object>> valueList= new ArrayList<HashMap<String,Object>>();
		if(res.getValueList() != null && res.getValueList().size() != 0) {
			for(HashMap<String, Object> s : res.getValueList()){
				String serialName = s.get("serialName").toString();
				String term = s.get("term").toString();
				String id = s.get("id").toString();
				Date updateTime = (Date) s.get("updateTime");
				int count = bsProductSerialService.selectCountOfSerialId(Integer.parseInt(id));
				s.put("count", count);
				s.put("serialName", serialName);
				s.put("term", term);
				s.put("id", id);
				s.put("updateTime", updateTime);
				valueList.add(s);
			}
			model.put("productSerialList",valueList);
		}
		return "/bsProductSerial/bsProductSerial_index";
	}
	
	/**
	 * 进入添加 编辑页面
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsProductSerial/detail")
	public String detail(ReqMsg_ProductSerial_SelectByPrimaryKey req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		if (req.getId() != null && req.getId() != 0) {
			if(req.getId() != null && req.getId() != 0) {
				BsProductSerial bsProductSerial = bsProductSerialService.selectByPrimaryId(req.getId());
				bsProductSerial.setId(bsProductSerial.getId());
				bsProductSerial.setSerialName(bsProductSerial.getSerialName());
				bsProductSerial.setTerm(bsProductSerial.getTerm());
				bsProductSerial.setCreateTime(bsProductSerial.getCreateTime());
				bsProductSerial.setUpdateTime(bsProductSerial.getUpdateTime());
				model.put("bsProductSerial", bsProductSerial);
			}
		}
		//查询期限产品编码列表
		BsTermProductCodeExample example = new BsTermProductCodeExample();
		example.setOrderByClause("term asc");
		List<BsTermProductCode> codes = termProductCodeService.findBsTermProductCodes(example);
		model.put("codes", codes);
		return "/bsProductSerial/bsProductSerial_detail";
	}
	
	/**
	 * 新增 修改
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsProductSerial/save")
	public @ResponseBody Map<Object, Object> save(ReqMsg_ProductSerial_ProductSerialModify req, 
			HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		ResMsg_ProductSerial_ProductSerialModify res = (ResMsg_ProductSerial_ProductSerialModify) productSerialService.handleMsg(req);
		if (res.getFlag() == 1) {
			return ReturnDWZAjax.success("新增成功！");
		} else if (res.getFlag() == 2) {
			return ReturnDWZAjax.success("修改成功！");
		} else if (res.getFlag() == 3) {
			return ReturnDWZAjax.toAjaxString("301", "产品系列名称已存在");
		} else {
			return ReturnDWZAjax.fail("操作失败！");
		}
	}
	
	@RequestMapping("/bsProductSerial/delete")
	public @ResponseBody Map<Object, Object> delete(ReqMsg_ProductSerial_ProductSerialDelete req, 
			HttpServletRequest request, Map<String, Object> model) {
		ResMsg_ProductSerial_ProductSerialDelete res = (ResMsg_ProductSerial_ProductSerialDelete) productSerialService.handleMsg(req);
		if (ConstantUtil.BSRESCODE_SUCCESS.equals(res.getResCode())) {
			return ReturnDWZAjax.success("删除成功！");
		} else {
			return ReturnDWZAjax.fail("删除失败，请重试！");
		}
		
	}
	

}
