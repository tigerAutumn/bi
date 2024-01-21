package com.pinting.manage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.service.site.ActivityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pinting.business.hessian.manage.message.ReqMsg_BsCheckInUser_CheckInUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_BsCheckInUser_CheckInUserListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_StagingProductsLoan_QueryPTList;
import com.pinting.business.service.manage.BsCheckInUserService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;
import com.pinting.util.MobileCheckUtil;
import com.pinting.util.ReturnDWZAjax;

/**
 * 2016客户年终答谢会签到
 * Created by shh on 2016/11/25 20:00.
 * @author shh
 */
@Controller
public class BsCheckInUserController {
	
	@Autowired
    @Qualifier("dispatchService")
    private HessianService checkInUserService;
	@Autowired
	private BsCheckInUserService bsCheckInUserService;
	@Autowired
    private ActivityService activityService;

	/**
	 * 进入签到用户抽奖管理界面
	 * @return
	 */
	@RequestMapping("/bsCheckInUser/checkInUser/index")
	public String checkInUserIndex(ReqMsg_BsCheckInUser_CheckInUserListQuery req, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = req.getPageNum() == null ? Constants.MANAGE_DEFAULT_PAGENUM : req.getPageNum();
        Integer numPerPage = req.getNumPerPage() == null ? Constants.MANAGE_DEFAULT_NUMPERPAGE : req.getNumPerPage();
        req.setPageNum(pageNum);
        req.setNumPerPage(numPerPage);
        String orderDirection = request.getParameter("orderDirection");
        String orderField = request.getParameter("orderField");
        if (orderDirection != null && orderField != null && !"".equals(orderDirection) && !"".equals(orderField)) {
            req.setOrderDirection(orderDirection);
            req.setOrderField(orderField);
            model.put("orderDirection", orderDirection);
            model.put("orderField", orderField);
        }
        ResMsg_BsCheckInUser_CheckInUserListQuery res = (ResMsg_BsCheckInUser_CheckInUserListQuery) checkInUserService.handleMsg(req);
        model.put("req", req);
        model.put("pageNum", req.getPageNum());
        model.put("numPerPage", req.getNumPerPage());
        model.put("totalRows", res.getTotalRows());
        model.put("checkInUserList", res.getValueList());
		return "/checkInUser/checkIn_user_index";
	}
	
	/**
	 * 单个手机号码添加
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@ResponseBody
    @RequestMapping(value = "/bsCheckInUser/addMobileRecord")
    public Map<Object, Object>  addMobileRecord(HttpServletRequest request, HttpServletResponse response,Map<String, Object> model, String mobile) {
    	boolean flag = bsCheckInUserService.insertCheckInUser(mobile);
    	if (flag) {
			return ReturnDWZAjax.success("添加成功！");
		}else {
			return ReturnDWZAjax.fail("添加失败，请重试！");
		}
    }
	
	/**
	 * 下载模板
	 * @param response
	 * @param request
	 */
	@RequestMapping("/bsCheckInUser/xls")
    public void xls(HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("手机号（只填写此列）");
        titleMap.put("title", titles);
        list.add(titleMap);
        try {
            ExportUtil.exportExcel(response, request, "签到用户批量导入模板", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
	
	/**
     * 读取手机号码
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/bsCheckInUser/readExcel")
    public
    @ResponseBody
    HashMap<String, Object> readExcel(@RequestParam(value = "fileField", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        String fileMobiles = "";
        String str1 = "";
        boolean importPhoneNumber = false;//导入的手机号码中有错误的号码，则退出，需要用户全部重新导入
        try {
            String[][] result = ExcelUtil.getDataInputStream(file.getInputStream(), 1, 0);
            if (result != null && result.length > 0) {
            	flag: for (int i = 0; i < result.length; i++) {
                    for (int j = 0; j < result[i].length; j++) {
                        if (j == 0) {
                            String str = StringUtil.left(result[i][j], result[i][j].length()); // 截取全部(手机号)
                            if(str.indexOf(".") != -1) {
                            	str1 = str.substring(0, str.indexOf("."));
                            }else {
                            	str1 = str;
                            }
                            if (MobileCheckUtil.isMobile(str1)) { // 验证手机号是否符合要求
                                fileMobiles = fileMobiles + str1 + ",";
                                importPhoneNumber = true;
                            }else{
                            	importPhoneNumber = false;
                            	break flag;//退出到加了flag标记位的循环
                            }
                        }
                    }
                }
            if(importPhoneNumber){
            	fileMobiles = fileMobiles.substring(0, fileMobiles.length() - 1);
                // fileMobiles手机号数组;以","分割得到一个新数组,遍历改数组放到map的key中;避免手机号重复
                Map<String, Object> hashMap = new HashMap<String, Object>();
                String[] mobiles = fileMobiles.split(",");
                for (String mobile : mobiles) {
                    hashMap.put(mobile, mobile);
                }
                StringBuffer mobileList = new StringBuffer();
                for (String string : hashMap.keySet()) {
                    mobileList.append(string).append(",");
                }
                mobileList.deleteCharAt(mobileList.length() - 1);
                
                dataMap.put("mobile", mobileList.toString());
                dataMap.put("code", 1);
                
                //上传成功的手机号批量插入2016客户年终答谢会签到表
                String[] phones = mobileList.toString().split(",");
    			List<String> phoneList = Arrays.asList(phones);
                bsCheckInUserService.batchInsertCheckInUser(phoneList);
            }else {
            	dataMap.put("code", 2);
            }
            } else {
                dataMap.put("code", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        return dataMap;
    }

    /**
     * 重置抽奖结果
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bsCheckInUser/revert")
    public Map<Object, Object> revert() {
        try {
            activityService.revertLuckyDraw(com.pinting.business.util.Constants.ACTIVITY_ID_2017_CHRISTMAS_EVE);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail("重置失败，请重试！");
        }
        return ReturnDWZAjax.success("重置成功！");
    }


}
