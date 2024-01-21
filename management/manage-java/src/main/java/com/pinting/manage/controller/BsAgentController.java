package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.model.MUser;
import com.pinting.business.service.manage.MUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.dao.BsAgentMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_BsUserListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_BuyMessageExcelQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_QhdBuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Agent_QhdUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_BsUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_BuyMessageExcelQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_BuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_QhdBuyMessageListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Agent_QhdUserListQuery;
import com.pinting.business.model.BsAgent;
import com.pinting.business.model.BsAgentExample;
import com.pinting.business.model.vo.SpreadChannelVO;
import com.pinting.business.service.manage.SpreadChannelService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.MoneyUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ExportUtil;
import com.pinting.util.Messages;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class BsAgentController {

	@Autowired
	@Qualifier("dispatchService")
	public HessianService hessianService;
	
	@Autowired
	public BsAgentMapper bsAgentMapper;
	
	@Autowired
	public SpreadChannelService spreadChannelService;

	@Autowired
	private MUserService mUserService;
	
	//说明：会根据当前登录用户渠道查询相应渠道用户信息
	@RequestMapping("/agent/user/index")
	public String UserInit(ReqMsg_Agent_BsUserListQuery req,
			HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));

		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		Integer mUserAgentId =  mUser.getAgentId();
		if(mUserAgentId != null) {
			if ((req.getQueryDateFlag() == null && mUserAgentId == 15) || (req.getQueryDateFlag() == "" && mUserAgentId == 15)) {
				req.setDistributionChannel(mUserAgentId.toString()); // 钱报的系统用户默认查询钱报渠道
			}else if(mUserAgentId != 15){
				req.setDistributionChannel(mUserAgentId.toString());
			}else {
				req.setDistributionChannel(req.getDistributionChannel());
			}
		}

		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if (request.getParameter("orderDirection") != null
				&& request.getParameter("orderField") != null) {
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		} 
		req.setsName(StringUtil.trim(req.getsName()));
		req.setSearchMobile(StringUtil.trim(req.getSearchMobile()));
		req.setRegTerminal(req.getRegTerminal());
		ResMsg_Agent_BsUserListQuery resp = new ResMsg_Agent_BsUserListQuery();
		if(StringUtil.isNotBlank(req.getQueryDateFlag()) && "QUERYDATA".equals(req.getQueryDateFlag())) {
			resp = (ResMsg_Agent_BsUserListQuery) hessianService
					.handleMsg(req);
		}
		model.put("users", resp.getBsUserList());
		model.put("pageNum", resp.getPageNum());
		model.put("numPerPage", resp.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("search", req.getSearchMobile());
		model.put("status", req.getStatus());
		model.put("sIsBindBank", resp.getsIsBindBank());
		model.put("sIsBindName", resp.getsIsBindName());
		model.put("sNick", resp.getsNick());
		model.put("sEmail", resp.getsEmail());
		model.put("sName", req.getsName());
		model.put("sReward", resp.getsReward());
		model.put("eReward", resp.geteReward());
		model.put("eregistTime", req.getEregistTime());
		model.put("sregistTime", req.getSregistTime());
		model.put("sRecommend", resp.getsRecommend());
		model.put("eRecommend", resp.geteRecommend());
		model.put("mAgentId",mUserAgentId);//登录用户的渠道编号
		model.put("regTerminal", req.getRegTerminal());
		model.put("distributionChannel", req.getDistributionChannel());
		if (req.getOrderField() != null) {
			model.put(req.getOrderField(), req.getOrderDirection());
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		return "/agent/user_index";
	}

	@RequestMapping("/agent/query/index")
	public String queryInit(ReqMsg_Agent_BuyMessageListQuery req,HttpServletRequest request,Map<String, Object> model){
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		
		req.setUserName(StringUtil.trim(req.getUserName()));
		req.setMobile(StringUtil.trim(req.getMobile()));
		req.setProductName(StringUtil.trim(req.getProductName()));

		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		Integer mUserAgentId =  mUser.getAgentId();

		if(mUserAgentId != null) {
			if ((req.getQueryDateFlag() == null && mUserAgentId == 15) || (req.getQueryDateFlag() == "" && mUserAgentId == 15)) {
				req.setDistributionChannel(mUserAgentId.toString());//钱报的系统用户默认查询钱报渠道
			}else if(mUserAgentId != 15){
				req.setDistributionChannel(mUserAgentId.toString());
			}else {
				req.setDistributionChannel(req.getDistributionChannel());
			}
		}

		if(pageNum <= 0 || numPerPage <= 0){
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		if(request.getParameter("orderDirection")!=null&&request.getParameter("orderField")!=null)
		{
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
			model.put("orderField", request.getParameter("orderField"));
			model.put("orderDirection", request.getParameter("orderDirection"));
		}else
		{
			req.setOrderDirection("desc");
			req.setOrderField("openTime");
			model.put("orderField", req.getOrderField());
			model.put("orderDirection", req.getOrderDirection());
		}
		req.setAccountStatus(0);//查询全部状态
		req.setTerminalType(req.getTerminalType());
		ResMsg_Agent_BuyMessageListQuery res = (ResMsg_Agent_BuyMessageListQuery) hessianService.handleMsg(req);
		model.put("sumBalanceAmount", res.getSumBalanceAmount() == null ? 0 : res.getSumBalanceAmount());
		model.put("userBuyMessage", res.getUserBuyMessageList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", res.getNumPerPage());
		model.put("totalRows", res.getTotalRows());
		model.put("req", req);
		model.put("agentId", res.getAgentId());
		if(req.getOrderField()!=null)
		{
			model.put(req.getOrderField(), req.getOrderDirection());
			
		}
		//产品累计购买人数、金额统计
		List<HashMap<String, Object>> list = res.getProductList();
		int  investNum = 0;
		double currTotalAmount = 0.0;
		if(list!=null && list.size()>0){
			for (HashMap<String, Object> hashMap : list) {
				investNum += (Integer) hashMap.get("investNum") == null ? 0 : (Integer) hashMap.get("investNum");
				currTotalAmount += (Double) hashMap.get("currTotalAmount") == null ? 0 : (Double) hashMap.get("currTotalAmount");
			}
			model.put("investNum",investNum);
			model.put("currTotalAmount", currTotalAmount);
		}
		model.put("terminalType", req.getTerminalType());
		return "/agent/query_index";
	}
	
	/**
	 * 导出用户信息excel
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/agent/userMessage/exportXls")
	public void exportXls(ReqMsg_Agent_BuyMessageExcelQuery req,HttpServletResponse response,
			HttpServletRequest request) {
		//设置导出excel标题
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		list.add(titles());
		
		//根据表单条件查询数据
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));
		req.setPageNum(1);
		req.setNumPerPage(Integer.MAX_VALUE);

		req.setUserName(StringUtil.trim(req.getUserName()));
		req.setMobile(StringUtil.trim(req.getMobile()));
		req.setProductName(StringUtil.trim(req.getProductName()));
		if (req.getQueryDateFlag() == null || req.getQueryDateFlag() == "") {
			req.setDistributionChannel("15");//钱报的系统用户默认查询钱报渠道
		}else {
			req.setDistributionChannel(req.getDistributionChannel());
		}

		if(request.getParameter("orderDirection")!=null&&request.getParameter("orderField")!=null)
		{
			req.setOrderDirection(request.getParameter("orderDirection"));
			req.setOrderField(request.getParameter("orderField"));
		}else
		{
			req.setOrderDirection("desc");
			req.setOrderField("openTime");
		}
		req.setAccountStatus(0);//查询全部状态
		req.setTerminalType(req.getTerminalType());

		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		Integer mUserAgentId =  mUser.getAgentId();

		ResMsg_Agent_BuyMessageExcelQuery res = (ResMsg_Agent_BuyMessageExcelQuery) hessianService.handleMsg(req);
		
		List<HashMap<String,Object>> datas =res.getUserBuyMessageList();
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				
				obj.add(data.get("mobile") == null ? "" : data.get("mobile"));
				obj.add(data.get("userName") == null ? "" :data.get("userName"));
				obj.add(data.get("productName") == null ? "" :data.get("productName"));
				obj.add(data.get("term") == null ? "0月" :data.get("term")+"月");
				obj.add(data.get("rate") == null ? "" :data.get("rate"));
				obj.add(data.get("balance") == null ? "" : MoneyUtil.format(data.get("balance").toString()));
				obj.add(data.get("bindBankName") == null ? "" :data.get("bindBankName"));
				obj.add(data.get("accountStatus") == null ? "" : Messages.get("ACCOUNT_STATUS_"+data.get("accountStatus")));
				obj.add(data.get("openTime") == null ? "" :data.get("openTime"));
				obj.add(data.get("investEndTime") == null ? "" :data.get("investEndTime"));
				if(15 == mUserAgentId) {
					obj.add(agentIdTrans(data.get("agentId").toString()));
					obj.add(null == data.get("terminalType") ? "" : regTerminalTrans(data.get("terminalType").toString()));
				}
				datasMap.put("userMessage"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "渠道用户投资", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Object>> titles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("手机号");
		titles.add("姓名");
		titles.add("产品名称");
		titles.add("期限");
		titles.add("利率");
		titles.add("购买金额");
		titles.add("提现银行");
		titles.add("账户状态");
		titles.add("购买日期");
		titles.add("到期提现日期");
		titles.add("分销渠道");
		titles.add("购买终端");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	
	
	/**
	 * 推广渠道管理列表页
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
    @RequestMapping("/spreadChannel/spreadIndex")
    public String spreadIndex(SpreadChannelVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	String status = request.getParameter("status");
    	List<SpreadChannelVO> spreadChannelList = spreadChannelService.querySpreadChannelList(req.getStart(), req.getNumPerPage(),StringUtil.trim(req.getAgentName()),status);
    	Integer count = spreadChannelService.getTotalCount(StringUtil.trim(req.getAgentName()),status);
		model.put("req", req);
		model.put("count", count);
		model.put("spreadChannelList", spreadChannelList);
		model.put("status", status);
        return "/spreadChannel/spread_index";
    }
	
    /**
     * 推广渠道管理新增页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/spreadChannel/spreadAdd")
    public String spreadAdd(SpreadChannelVO req, HttpServletRequest request, HttpServletResponse response,Map<String, Object> model) {
    	if(req.getId() != null && req.getId() != 0){
    		BsAgent spreadChannel = bsAgentMapper.selectByPrimaryKey(req.getId());
    		model.put("spreadChannel", spreadChannel);
    	}
    	return "/spreadChannel/spread_add";
    }
    
    /**
     * 推广渠道管理保存
     * @param request
     * @param response
     * @param model
     * @return
     */
	@RequestMapping("/spreadChannel/spreadSave")
	public @ResponseBody Map<Object, Object> spreadSave(BsAgent spread,HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
		
		if (StringUtil.isEmpty(spread.getAgentName())) {
			return ReturnDWZAjax.fail("名称不能为空！");
		} 
		int num = -1;
		if(spread.getId() != null && spread.getId() != 0){
			SpreadChannelVO update_spread = new SpreadChannelVO();
			update_spread.setId(spread.getId());
			update_spread.setAgentName(spread.getAgentName());
			update_spread.setSupportTerminal(spread.getSupportTerminal());
			update_spread.setUpdateTime(new Date());
			num = bsAgentMapper.updateByPrimaryKeySelective(update_spread);
			if (num > 0) {
				return ReturnDWZAjax.success("修改成功！");
			}else {
				return ReturnDWZAjax.fail("修改失败！");
			}
		}else{
			if(StringUtil.isEmpty(spread.getSupportTerminal())){
				return ReturnDWZAjax.fail("推广方式不能为空！");
			} 
			spread.setAgentType("FLEXIBLE");
			spread.setCreateTime(new Date());
			spread.setUpdateTime(new Date());
			spread.setPageViewTimes(0);
			spread.setDept("泓淳线上");
			num = bsAgentMapper.insertSelective(spread);
			SpreadChannelVO update_spread = new SpreadChannelVO();
			update_spread.setAgentCode("flexible" + spread.getId());
			update_spread.setId(spread.getId());
			bsAgentMapper.updateByPrimaryKeySelective(update_spread);
			if (num > 0) {
				return ReturnDWZAjax.success("新增成功！");
			}else {
				return ReturnDWZAjax.fail("新增失败！");
			}
		}
	}

	//渠道用户查询 导出excel
	@RequestMapping("/agent/user/exportXls")
	public void exportXls(ReqMsg_Agent_BsUserListQuery req, HttpServletResponse response,
						  HttpServletRequest request) {

		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));

		List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
		//设置标题
		Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));//查询当前登入管理员是那个渠道的
		Integer mUserAgentId =  mUser.getAgentId();
		if(mUserAgentId != null) {
			if ((req.getQueryDateFlag() == null && mUserAgentId == 15) || (req.getQueryDateFlag() == "" && mUserAgentId == 15)) {
				req.setDistributionChannel(mUserAgentId.toString()); // 钱报的系统用户默认查询钱报渠道
			}else if(mUserAgentId != 15){
				req.setDistributionChannel(mUserAgentId.toString());
			}else {
				req.setDistributionChannel(req.getDistributionChannel());
			}
		}

		if(null != mUser.getAgentId() && 0 != mUser.getAgentId()){
			if(mUser.getAgentId() == 15) {
				List<Object> titles = new ArrayList<Object>();
				titles.add("手机号");
				titles.add("姓名");
				titles.add("身份证");
				titles.add("总资产");
				titles.add("推荐次数");
				titles.add("可提现金额");
				titles.add("投资收益");
				titles.add("当前投资本金");
				titles.add("累计推荐奖励");
				titles.add("累计投资收益");
				titles.add("注册日期");
				titles.add("注册终端");
				titles.add("分销渠道");
				titleMap.put("title", titles);
				list.add(titleMap);
			}else {
				List<Object> titles = new ArrayList<Object>();
				titles.add("手机号");
				titles.add("姓名");
				titles.add("总资产");
				titles.add("推荐次数");
				titles.add("可提现金额");
				titles.add("投资收益");
				titles.add("当前投资本金");
				titles.add("累计推荐奖励");
				titles.add("累计投资收益");
				titles.add("注册日期");
				titleMap.put("title", titles);
				list.add(titleMap);
			}
		}

		req.setPageNum(1);
		req.setNumPerPage(req.getTotalRows());
		req.setRegTerminal(req.getRegTerminal());
		ResMsg_Agent_BsUserListQuery resp = (ResMsg_Agent_BsUserListQuery) hessianService.handleMsg(req);
		List<HashMap<String, Object>> datas = resp.getBsUserList();
		//设置导出excel内容
		if (datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String, Object> data : datas) {
				Map<String, List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();

				obj.add(data.get("mobile"));
				obj.add(data.get("name"));
				if(null != data.get("mUserAgentId") && 15 == Integer.parseInt(data.get("mUserAgentId").toString())) {
					obj.add(data.get("idCard"));
				}
				obj.add(data.get("sumBalance"));
				obj.add(data.get("recommendNum"));
				obj.add(data.get("canWithdraw"));
				obj.add(data.get("currentInterest"));
				obj.add(data.get("currentBanlace"));
				obj.add(data.get("totalBonus"));
				obj.add(data.get("totalInterest"));
				obj.add(data.get("registTime"));
				if(null != data.get("mUserAgentId") && 15 == Integer.parseInt(data.get("mUserAgentId").toString())) {
					obj.add( null == data.get("regTerminal") ? "" : regTerminalTrans(data.get("regTerminal").toString()));
					obj.add(agentIdTrans(data.get("agentId").toString()));
				}
				datasMap.put("user" + (++i), obj);
				list.add(datasMap);
			}
		}

		try {
			ExportUtil.exportExcel(response, request, "渠道用户查询", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 用户注册终端转换
	 * @param regTerminal
	 * @return
     */
	private String regTerminalTrans(String regTerminal) {
		String str = "";
		if (StringUtil.isNotBlank(regTerminal)) {
			if(Constants.BS_USER_REG_TERMINAL_1.equals(regTerminal)) {
				str = "H5端";
			}else if(Constants.BS_USER_REG_TERMINAL_2.equals(regTerminal)) {
				str = "PC端";
			}else if(Constants.BS_USER_REG_TERMINAL_3.equals(regTerminal)) {
				str = "Android端";
			}else if (Constants.BS_USER_REG_TERMINAL_4.equals(regTerminal)) {
				str = "iOS端";
			}
		}
		return str;
	}

	/**
	 * 报业分销渠道转换
	 * @param agentId
	 * @return
     */
	private String agentIdTrans(String agentId) {
		String str = "";
		if (StringUtil.isNotBlank(agentId)) {
			if(Constants.BS_USER_AGENT_ID_15.equals(agentId)) {
				str = "钱报";
			}else if(Constants.BS_USER_AGENT_ID_36.equals(agentId)) {
				str = "柯桥日报";
			}else if(Constants.BS_USER_AGENT_ID_46.equals(agentId)) {
				str = "海宁日报";
			}else if (Constants.BS_USER_AGENT_ID_47.equals(agentId)) {
				str = "瑞安日报";
			}
		}
		return str;
	}

	/******************************************管理台渠道业务查询功能（秦皇岛）**********************************************/
	
	/**
	 * 渠道用户查询（秦皇岛）
	 * 说明：会根据当前登录用户渠道查询相应渠道用户信息
	 * @param req
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/agent/user/qhdIndex")
	public String qhdUserInfo(ReqMsg_Agent_QhdUserListQuery req, HttpServletRequest request, HttpServletResponse response,
			Map<String, Object> model) {
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));
		req.setUserName(StringUtil.trim(req.getUserName()));
		req.setMobile(StringUtil.trim(req.getMobile()));
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		if (pageNum <= 0 || numPerPage <= 0) {
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		Integer mUserAgentId =  mUser.getAgentId();
		
		if (StringUtil.isEmpty(req.getQueryFlag())) {
			model.put("pageNum", req.getPageNum());
			model.put("numPerPage", req.getNumPerPage());
			model.put("totalRows", 0);
			model.put("req", req);
			model.put("mAgentId", mUserAgentId);//登录用户的渠道编号
			return "/agent/qhd_user_index";
		}
		
		if(mUserAgentId != null) {
			if (StringUtil.isEmpty(req.getQueryFlag())) {
				req.setDistributionChannel(mUserAgentId.toString());
			} else {
				req.setDistributionChannel(req.getDistributionChannel());
			}
		}
		
		ResMsg_Agent_QhdUserListQuery resp = (ResMsg_Agent_QhdUserListQuery) hessianService.handleMsg(req);
		model.put("users", resp.getBsUserList());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", req.getNumPerPage());
		model.put("totalRows", resp.getTotalRows());
		model.put("req", req);
		model.put("mAgentId", mUserAgentId);//登录用户的渠道编号
		return "/agent/qhd_user_index";
	}
	
	/**
	 * 导出渠道用户查询（秦皇岛）
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/agent/user/qhdAgentExportXls")
	public void qhdAgentUserExportXls(ReqMsg_Agent_QhdUserListQuery req,HttpServletResponse response,
			HttpServletRequest request) {
		//设置导出excel标题
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		list.add(qhdAgentUserTitles());
		
		//根据表单条件查询数据
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));
		req.setPageNum(1);
		req.setNumPerPage(Integer.MAX_VALUE);
		req.setUserName(StringUtil.trim(req.getUserName()));
		req.setMobile(StringUtil.trim(req.getMobile()));
		
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		Integer mUserAgentId =  mUser.getAgentId();
		if(mUserAgentId != null) {
			req.setDistributionChannel(mUserAgentId.toString());
		} else {
			req.setDistributionChannel(req.getDistributionChannel());
		}
		
		ResMsg_Agent_QhdUserListQuery res = (ResMsg_Agent_QhdUserListQuery) hessianService.handleMsg(req);
		List<HashMap<String,Object>> datas =res.getBsUserList();
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(StringUtil.substring(data.get("mobile").toString(),0,3)+"****"+
						StringUtil.substring(data.get("mobile").toString(),data.get("mobile").toString().length()-4,data.get("mobile").toString().length()));
				obj.add(data.get("userName") == null ? "" :StringUtil.substring(data.get("userName").toString(),0,1) + "**");				
				obj.add(data.get("totalBalance") == null ? "" : MoneyUtil.format(data.get("totalBalance").toString()));
				obj.add(data.get("canWithdraw") == null ? "" : MoneyUtil.format(data.get("canWithdraw").toString()));
				obj.add(data.get("currentInterest") == null ? "" : MoneyUtil.format(data.get("currentInterest").toString()));
				obj.add(data.get("authBalance") == null ? "" : MoneyUtil.format(data.get("authBalance").toString()));
				obj.add(data.get("totalInterest") == null ? "" : MoneyUtil.format(data.get("totalInterest").toString()));
				obj.add(data.get("registerTime") == null ? "" :data.get("registerTime"));
				datasMap.put("userMessage"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "渠道用户查询(秦皇岛)", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 渠道用户投资查询（秦皇岛）
	 * @param req
	 * @param request
	 * @param model
	 * @return
	 */
	@RequestMapping("/agent/userInvest/qhdIndex")
	public String qhdUserInvestInfo(ReqMsg_Agent_QhdBuyMessageListQuery req, HttpServletRequest request, Map<String, Object> model){
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));
		Integer pageNum = req.getPageNum();
		Integer numPerPage = req.getNumPerPage();
		req.setUserName(StringUtil.trim(req.getUserName()));
		req.setMobile(StringUtil.trim(req.getMobile()));
		if(pageNum <= 0 || numPerPage <= 0){
			pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
			numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
			req.setPageNum(pageNum);
			req.setNumPerPage(numPerPage);
		}
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		Integer mUserAgentId =  mUser.getAgentId();
		
		if (StringUtil.isEmpty(req.getQueryFlag())) {
			model.put("pageNum", req.getPageNum());
			model.put("numPerPage", req.getNumPerPage());
			model.put("totalRows", 0);
			model.put("req", req);
			model.put("agentId", mUserAgentId);
			return "/agent/qhd_userInvest_index";
		}
	
		if(mUserAgentId != null) {
			req.setDistributionChannel(mUserAgentId.toString());
		} else {
			req.setDistributionChannel(req.getDistributionChannel());
		}
		ResMsg_Agent_QhdBuyMessageListQuery res = (ResMsg_Agent_QhdBuyMessageListQuery) hessianService.handleMsg(req);
		model.put("sumBalanceAmount", res.getSumBalanceAmount() == null ? 0 : res.getSumBalanceAmount());
		model.put("userBuyMessage", res.getUserBuyMessageList());
		model.put("totalRows", res.getTotalRows());
		model.put("pageNum", req.getPageNum());
		model.put("numPerPage", req.getNumPerPage());
		model.put("req", req);
		model.put("agentId", mUserAgentId);
		return "/agent/qhd_userInvest_index";
	}
	
	/**
	 * 导出渠道用户投资查询（秦皇岛）
	 * 
	 * @param response
	 * @param request
	 */
	@RequestMapping("/agent/userInvest/qhdExportXls")
	public void qhdInvestExportXls(ReqMsg_Agent_QhdBuyMessageListQuery req,HttpServletResponse response,
			HttpServletRequest request) {
		//设置导出excel标题
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		list.add(qhdInvestTitles());
		
		//根据表单条件查询数据
		CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(), CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);//获得当前管理员的id
		req.setmUserId(Integer.parseInt(mUserId));
		req.setPageNum(1);
		req.setNumPerPage(Integer.MAX_VALUE);
		req.setUserName(StringUtil.trim(req.getUserName()));
		req.setMobile(StringUtil.trim(req.getMobile()));
		
		MUser mUser = mUserService.findMUser(Integer.parseInt(mUserId));
		Integer mUserAgentId =  mUser.getAgentId();
		if(mUserAgentId != null) {
			req.setDistributionChannel(mUserAgentId.toString());
		} else {
			req.setDistributionChannel(req.getDistributionChannel());
		}
		
		ResMsg_Agent_QhdBuyMessageListQuery res = (ResMsg_Agent_QhdBuyMessageListQuery) hessianService.handleMsg(req);
		List<HashMap<String,Object>> datas =res.getUserBuyMessageList();
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(StringUtil.substring(data.get("mobile").toString(),0,3)+"****"+
						StringUtil.substring(data.get("mobile").toString(),data.get("mobile").toString().length()-4,data.get("mobile").toString().length()));
				obj.add(data.get("userName") == null ? "" :StringUtil.substring(data.get("userName").toString(),0,1) + "**");
				obj.add(data.get("productName") == null ? "" :data.get("productName"));
				obj.add(data.get("term") == null ? "0天" :data.get("term")+"天");
				obj.add(data.get("rate") == null ? "" :data.get("rate"));
				obj.add(data.get("openBalance") == null ? "" : MoneyUtil.format(data.get("openBalance").toString()));
				obj.add(data.get("bankName") == null ? "" :data.get("bankName"));
				obj.add(data.get("accountStatus") == null ? "" : Messages.get("ACCOUNT_STATUS_"+data.get("accountStatus")));
				obj.add(data.get("openTime") == null ? "" :data.get("openTime"));
				obj.add(data.get("lastFinishInterestDate") == null ? "" :data.get("lastFinishInterestDate"));
				obj.add(data.get("agentName") == null ? "" :data.get("agentName"));
				if("1".equals(data.get("terminalType"))) {
                    obj.add(data.get("terminalType") == null ? "" : "H5端");
                } else if("2".equals(data.get("terminalType"))) {
                    obj.add(data.get("terminalType") == null ? "" : "PC端");
                } else {
                    obj.add("");
                }
				datasMap.put("userMessage"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "渠道用户投资查询(秦皇岛)", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private Map<String, List<Object>> qhdAgentUserTitles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("手机号");
		titles.add("姓名");
		titles.add("总资产");
		titles.add("可提现金额");
		titles.add("预期投资收益");
		titles.add("当前投资本金");
		titles.add("累计投资收益");
		titles.add("注册日期");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	private Map<String, List<Object>> qhdInvestTitles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("手机号");
		titles.add("姓名");
		titles.add("产品名称");
		titles.add("投资期限 ");
		titles.add("利率");
		titles.add("投资金额");
		titles.add("提现银行");
		titles.add("投资状态");
		titles.add("购买日期");
		titles.add("结算日期");
		titles.add("分销渠道");
		titles.add("投资终端");
		titleMap.put("title", titles);
		return titleMap;
	}
	
	/******************************************管理台渠道业务查询功能（秦皇岛）**********************************************/	
	
}
