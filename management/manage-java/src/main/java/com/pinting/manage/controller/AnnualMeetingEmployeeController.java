package com.pinting.manage.controller;

import java.io.IOException;
import java.util.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pinting.business.service.site.ActivityService;
import com.pinting.core.util.DateUtil;
import com.pinting.util.ReturnDWZAjax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pinting.business.hessian.manage.message.ReqMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery;
import com.pinting.business.service.manage.AnnualMeetingEmployeeService;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.util.Constants;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;

/**
 * 2016公司年会抽奖
 * Created by shh on 2017/01/13 14:00.
 * @author shh
 */
@Controller
public class AnnualMeetingEmployeeController {
	
	@Autowired
    @Qualifier("dispatchService")
    private HessianService AnnualMeetingEmpService;
	
	@Autowired
	private AnnualMeetingEmployeeService annualMeetingEmployeeService;
    @Autowired
    private ActivityService activityService;
	
	/**
	 * 进入公司年会抽奖管理界面
	 * @return
	 */
	@RequestMapping("/annualMeetingEmployee/annualMeeting/index")
	public String checkInUserIndex(ReqMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery req, HttpServletRequest request, Map<String, Object> model) {
		Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_DEFAULT_NUMPERPAGE;
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
        ResMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery res = 
        		(ResMsg_AnnualMeetingEmp_AnnualMeetingEmpListQuery) AnnualMeetingEmpService.handleMsg(req);
        model.put("req", req);
        model.put("pageNum", res.getPageNum());
        model.put("numPerPage", res.getNumPerPage());
        model.put("totalRows", res.getTotalRows());
        model.put("annualMeetingEmpList", res.getValueList());
		return "/annualMeetingEmp/annual_meeting_index";
	}
	
	/**
     * 年终抽奖下载模板
     * @param response
     * @param request
     */
    @RequestMapping("/annualMeetingEmployee/xls")
    public void xlsLottery(HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("公司名称");
        titles.add("员工名字");
        titleMap.put("title", titles);
        list.add(titleMap);
        try {
            ExportUtil.exportExcel(response, request, DateUtil.formatDateTime(new Date(), "yyyy") + "年年会抽奖信息批量导入模板", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @ResponseBody
    @RequestMapping("/annualMeetingEmployee/revert")
    public Map<Object, Object> revert() {
        try {
            activityService.revertLuckyDraw(com.pinting.business.util.Constants.ACTIVITY_ID_2018_YEAR_END);
        } catch (Exception e) {
            e.printStackTrace();
            return ReturnDWZAjax.fail("重置失败，请重试！");
        }
        return ReturnDWZAjax.success("重置成功！");
    }

    /**
     * 员工信息导入
     * @param file
     * @param response
     * @param request
     * @return
     * @throws IOException
     */
    @RequestMapping("/annualMeetingEmployee/readExcel")
    public @ResponseBody HashMap<String, Object> readLotteryExcel(@RequestParam(value = "fileField", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        HashMap<String, Object> dataMap = new HashMap<String, Object>();
        String fileEmployees = "";
        try {
            String[][] result = ExcelUtil.getDataInputStream(file.getInputStream(), 1, 0);
            if (result != null && result.length > 0) {
                for (int i = 0; i < result.length; i++) {
                    for (int j = 0; j < result[i].length; j++) {
                        if (j == 0) {
                            String company = StringUtil.left(result[i][j], result[i][j].length());
                            String name = StringUtil.left(result[i][j+1], result[i][j+1].length());
                            fileEmployees = fileEmployees + company + "," + name + ";";
                        }
                    }
                }
                fileEmployees = fileEmployees.substring(0, fileEmployees.length() - 1);
                // fileEmployees 公司名称、员工名字数组;以";"分割得到一个新数组,遍历改数组放到map的key中;避免员工信息重复
                Map<String, Object> hashMap = new HashMap<String, Object>();
                String[] employees = fileEmployees.split(";");
                for (String employee : employees) {
                    hashMap.put(employee, employee);
                }
                StringBuffer employeeList = new StringBuffer();
              
                for (String string : hashMap.keySet()) {
                    employeeList.append(string).append(";");
                }
                employeeList.deleteCharAt(employeeList.length() - 1);

                dataMap.put("employee", employeeList.toString());
                dataMap.put("code", 1);
                
                //上传成功的信息批量插入到2016公司年会抽奖员工信息表
                String[] empInfo = employeeList.toString().split(";");
                List<String> empList = Arrays.asList(empInfo);
                
                annualMeetingEmployeeService.batchInsertAnnualMeetingEmp(empList);
            } else {
                dataMap.put("code", 0);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        return dataMap;
    }
	

}
