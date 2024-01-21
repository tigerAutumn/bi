package com.pinting.manage.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.pinting.business.model.BsBonusGrantPlan;
import com.pinting.business.model.vo.BsBonusGrantPlanVO;
import com.pinting.business.service.manage.BonusGrantPlanService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;
import com.pinting.util.MobileCheckUtil;

/**
 * 奖励金导入
 * @author bianyatian
 * @2016-11-3 上午11:17:36
 */

@Controller
public class BonusGrantPlanAddController {
	
	private Logger log = LoggerFactory.getLogger(BonusGrantPlanAddController.class);
	@Autowired
	private BonusGrantPlanService bonusGrantPlanService;
	@Autowired
	private BsUserService bsUserService;
	
	/**
	 * 进入奖励金导入主页
	 * @param model
	 * @return
	 */
	@RequestMapping("/bonusGrant/index")
	public String acctTransRepeatSendIndex(HashMap<String,Object> model){
		
		return "/bonusGrant/index";
	}
	
	/**
	 * 模板导出
	 * @param request
	 * @param response
	 */
	@RequestMapping("/bonusGrant/exportExcel")
	public void exportExcel(HttpServletRequest request, HttpServletResponse response) {
		List<Map<String,List<Object>>> excelList = new ArrayList<Map<String,List<Object>>>();
        Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("用户编号");
        titles.add("金额");
        titles.add("发放时间（最早为当天）");
        titles.add("备注");
        
        titleMap.put("title", titles);
        excelList.add(titleMap);
        List<Object> text = new ArrayList<Object>();
        Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
        text.add(1001);
        text.add(10.1);
        text.add("2016-12-12");
        text.add("提现扣除补贴奖励金");
        
        datasMap.put("order"+0, text);
        excelList.add(datasMap);
        try {
            ExportUtil.exportExcel(response, request, "奖励金导入模板", excelList);
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
    /**
     * 读取文件并预览
     *
     * @param fileName 文件地址
     * @return
     * @throws IOException
     */
    @RequestMapping("/bonusGrant/readExcelCheck")
    public @ResponseBody 
    HashMap<String, Object> readExcelCheck(@RequestParam(value = "fileFieldCheck", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request) throws IOException {
    	HashMap<String, Object> dataMap = new HashMap<String, Object>();
        List<BsBonusGrantPlanVO> bonusGrantList = new ArrayList<BsBonusGrantPlanVO>();
        String errorMessage = "";
        try {
            String[][] result = ExcelUtil.getDataInputStream(file.getInputStream(), 1, 4);
            String userIdStrs = ",";
            if (result != null && result.length > 0) {
                for (int i = 0; i < result.length; i++) {
                	BsBonusGrantPlanVO bonusGrant = new BsBonusGrantPlanVO();
                    for (int j = 0; j < result[i].length; j++) {
                        if (j == 0) {
                        	if(StringUtil.isNotBlank(result[i][j])){
                        		bonusGrant.setUserIdStr(result[i][j].trim().replace(".00", ""));
                        		try {
                        			bonusGrant.setUserId(Integer.parseInt(bonusGrant.getUserIdStr()));
                        			bonusGrant.setUserIdStr(String.valueOf(bonusGrant.getUserId()));
                            		//校验用户id是否存在
                            		if(!bsUserService.isExistUser(bonusGrant.getUserId())){
                            			bonusGrant.setUserIdFlag("error");
                            			errorMessage = "红色部分格式不正确！";
                            		}
                            		//校验是否重复用户id
                            		if(userIdStrs.contains(","+bonusGrant.getUserIdStr()+",")){
                            			bonusGrant.setUserIdFlag("error");
                            			//errorMessage = "红色部分格式不正确！";
                            		}
                            		userIdStrs = userIdStrs+bonusGrant.getUserIdStr()+",";
								} catch (Exception e) {
									errorMessage = "红色部分格式不正确！";
									bonusGrant.setUserIdFlag("error");
									e.printStackTrace();
								}
                        		
                        	}else{
                        		bonusGrant.setUserIdStr("用户id不能为空");
                        		errorMessage = "红色部分格式不正确！";
                        		bonusGrant.setUserIdFlag("error");
                        	}
                        }
                        if (j == 1) {
                        	if(StringUtil.isNotBlank(result[i][j])){
                        		bonusGrant.setAmountStr(result[i][j].trim());
                        		try {
                            		//字符串转换金额
                            		bonusGrant.setAmount(StringUtil.isNotBlank(bonusGrant.getAmountStr())?Double.valueOf(bonusGrant.getAmountStr()): null);
                            		if(bonusGrant.getAmount() == null){
                            			bonusGrant.setAmountStr("金额不能为空");
                                		errorMessage = "红色部分格式不正确！";
                                		bonusGrant.setAmountFlag("error");
                            		}
                            	} catch (Exception e) {
    								errorMessage = "红色部分格式不正确！";
    								bonusGrant.setAmountFlag("error");
    								e.printStackTrace();
    							}
                        	}else{
                        		bonusGrant.setAmountStr("金额不能为空");
                        		errorMessage = "红色部分格式不正确！";
                        		bonusGrant.setAmountFlag("error");
                        	}
                        	
                        	
                        }
                        if (j == 2) {
                        	if(StringUtil.isNotBlank(result[i][j])){
                        		bonusGrant.setGrantDateStr(result[i][j].trim());
                            	try {
                            		bonusGrant.setGrantDate(StringUtil.isNotBlank(result[i][j])?DateUtil.parseDate(result[i][j].trim()): null);
                            		if(bonusGrant.getGrantDate() == null){
                            			bonusGrant.setGrantDateStr("时间不能为空");
                                		errorMessage = "红色部分格式不正确！";
                                		bonusGrant.setGrantDateFlag("error");
                            		}
                            		//Date tomorrowDate = DateUtil.addDays(DateUtil.parseDate(DateUtil.format(new Date())), 1);
                            		Date todayDate = DateUtil.parseDate(DateUtil.format(new Date()));
                            		if(bonusGrant.getGrantDate().compareTo(todayDate) < 0){
                            			errorMessage = "红色部分格式不正确！";
                            			bonusGrant.setGrantDateFlag("error");
                            		}
                            	} catch (Exception e) {
                            		errorMessage = "红色部分格式不正确！";
                            		bonusGrant.setGrantDateFlag("error");
    								e.printStackTrace();
    							}
                        	}else{
                        		bonusGrant.setGrantDateStr("时间不能为空");
                        		errorMessage = "红色部分格式不正确！";
                        		bonusGrant.setGrantDateFlag("error");
                        	}
                        	
                        }
                        if (j == 3) {
                        	bonusGrant.setNote(result[i][j]);
                        	if(StringUtil.isBlank(bonusGrant.getNote())){
                        		bonusGrant.setNote("备注不能为空");
                        		errorMessage = "红色部分格式不正确！";
                        		bonusGrant.setNoteFlag("error");
                        	} else if(bonusGrant.getNote().length() > 6){
                        		bonusGrant.setNote("备注不能超过6个字");
                        		errorMessage = "红色部分格式不正确！";
                        		bonusGrant.setNoteFlag("error");
                        	}
                        	
                        }
                    }
                    //校验是否存在相同的计划,存在-true,不存在-false
                    /*if(!"error".equals(bonusGrant.getGrantDateFlag()) && !"error".equals(bonusGrant.getUserIdFlag()) 
                    		&& !"error".equals(bonusGrant.getAmountFlag()) && !"error".equals(bonusGrant.getNoteFlag()) 
                    		&& bonusGrantPlanService.checkSameGrant(bonusGrant)){
                    	bonusGrant.setNoteFlag("error");
                    	bonusGrant.setGrantDateFlag("error");
                    	bonusGrant.setAmountFlag("error");
                    	bonusGrant.setUserIdFlag("error");
                    	errorMessage = "红色部分格式不正确,有重复的奖励金发放计划！";
                    }*/
                    bonusGrantList.add(bonusGrant);
                   
                }
                dataMap.put("bonusGrantList", bonusGrantList);
                dataMap.put("errorMessage", errorMessage);
                dataMap.put("showSize", result.length);
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }

    
    /**
     * 读取文件并上传
     *
     * @param fileName 文件地址
     * @return
     * @throws IOException
     */
    @RequestMapping("/bonusGrant/readExcel")
    public @ResponseBody 
    HashMap<String, Object> readExcel(@RequestParam(value = "fileField", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request) throws IOException {
    	HashMap<String, Object> dataMap = new HashMap<String, Object>();
        List<BsBonusGrantPlan> bonusGrantList = new ArrayList<BsBonusGrantPlan>();
        String errorMessage = "";
        CookieManager cookie = new CookieManager(request);
		String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
				CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		if(StringUtil.isBlank(mUserId)){
			dataMap.put("errorMessage", "未获取到用户登录信息");
			return dataMap;
		}
        try {
        	//读取文件
            String[][] result = ExcelUtil.getDataInputStream(file.getInputStream(), 1, 4);
            String userIdStrs = ",";
            if (result != null && result.length > 0) {
                for (int i = 0; i < result.length; i++) {
                	BsBonusGrantPlan bonusGrant = new BsBonusGrantPlan();
                    for (int j = 0; j < result[i].length; j++) {
                        if (j == 0) {
                        	if(StringUtil.isNotBlank(result[i][j])){
                        		String userId = result[i][j].trim().replace(".00", "");
                        		try {
                        			bonusGrant.setUserId(Integer.parseInt(userId));
                            		//校验用户id是否存在
                            		if(!bsUserService.isExistUser(bonusGrant.getUserId())){
                            			errorMessage = "校验不通过！";
                            		}
                            		//校验是否重复用户id
                            		/*if(userIdStrs.contains(","+String.valueOf(bonusGrant.getUserId())+",")){
                            			errorMessage = "校验不通过！";
                            		}*/
                            		userIdStrs = String.valueOf(bonusGrant.getUserId())+",";
								} catch (Exception e) {
									errorMessage = "校验不通过！";
									e.printStackTrace();
								}
                        		
                        	}else{
                        		errorMessage = "校验不通过！";
                        	}
                        }
                        if (j == 1) {
                        	if(StringUtil.isBlank(result[i][j])){
                        		errorMessage = "校验不通过！";
                        	}else{
                        		try {
                            		//字符串转换金额
                            		bonusGrant.setAmount(StringUtil.isNotBlank(result[i][j].trim())?Double.valueOf(result[i][j].trim()): null);
    							} catch (Exception e) {
    								errorMessage = "校验不通过！";
    								e.printStackTrace();
    							}
                        	}
                        	
                        }
                        if (j == 2) {
                        	if(StringUtil.isBlank(result[i][j])){
                        		errorMessage = "校验不通过！";
                        	}else{
	                        	try {
	                        		bonusGrant.setGrantDate(StringUtil.isNotBlank(result[i][j])?DateUtil.parseDate(result[i][j].trim()): null);
	                        		//Date tomorrowDate = DateUtil.addDays(DateUtil.parseDate(DateUtil.format(new Date())), 1);
	                        		Date todayDate = DateUtil.parseDate(DateUtil.format(new Date()));
	                        		if(bonusGrant.getGrantDate().compareTo(todayDate) < 0){
	                        			errorMessage = "校验不通过！";
	                        		}
	                        	} catch (Exception e) {
	                        		errorMessage = "校验不通过！";
									e.printStackTrace();
								}
	                        }
                        }
                        if (j == 3) {
                        	bonusGrant.setNote(result[i][j]);
                        	if(StringUtil.isBlank(bonusGrant.getNote())){
                        		errorMessage = "校验不通过！";
                        	} else if(bonusGrant.getNote().length() > 6) {
                        		errorMessage = "校验不通过！";
                        	}
                        }
                    }
                    bonusGrantList.add(bonusGrant);
                    //校验是否存在相同的计划,存在-true,不存在-false
                    /*if(StringUtil.isBlank(errorMessage) && bonusGrantPlanService.checkSameGrant(bonusGrant)){
                    	errorMessage = "校验不通过！";
                    }*/
                }
                //校验通过，存库
                if(StringUtil.isBlank(errorMessage)){
                	bonusGrantPlanService.saveBonusGrantPlan(bonusGrantList, Integer.parseInt(mUserId));
                }
                dataMap.put("errorMessage", errorMessage);
                
            } 
        } catch (IOException e) {
            e.printStackTrace();
        }
        return dataMap;
    }
	
}
