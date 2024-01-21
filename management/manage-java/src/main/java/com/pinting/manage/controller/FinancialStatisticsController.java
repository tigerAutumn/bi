package com.pinting.manage.controller;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.model.BsCompanyCustomer;
import com.pinting.business.model.BsCompanyDept;
import com.pinting.business.model.BsCompanyEmployee;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.vo.BsCompanyCustomerVO;
import com.pinting.business.model.vo.BsCompanyDeptVO;
import com.pinting.business.model.vo.BsCompanyEmployeeVO;
import com.pinting.business.model.vo.CompanyCustomerVO;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.business.service.manage.FinancialStatisticsService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.util.ExcelUtil;
import com.pinting.util.ExportUtil;
import com.pinting.util.ReturnDWZAjax;
import com.pinting.util.ZtreeModel;

/**
 * 财务统计Controller
 * @author Dragon & cat
 */

@Controller
@RequestMapping("/financial/statistics")
public class FinancialStatisticsController {

    @Autowired
    private FinancialStatisticsService financialStatisticsService;
    @Autowired
    private BsUserService bsUserService;

    // ==================================================== 公司部门维护 ==============================================================
    /**
     * 公司部门维护首页
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/company_dept/index")
    public String companyDeptIndex(BsCompanyDeptVO req, Map<String, Object> model,
                                   HttpServletRequest request, HttpServletResponse response) {

        // 公司部门列表

		
/*		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		
		req.setStartTime(StringUtil.isBlank(req.getStartTime())==true?yestoday:req.getStartTime());
		req.setEndTime(StringUtil.isBlank(req.getEndTime())==true?yestoday:req.getEndTime());*/
		
		
		model.put("startTime", req.getStartTime());
		model.put("endTime", req.getEndTime());
        Integer count = financialStatisticsService.queryFinancialStatisticsCount(req);
        if (count != null && count > 0) {
            List<BsCompanyDeptVO> companyDept = financialStatisticsService
                .queryFinancialStatisticsIndex(req);
            ArrayList<HashMap<String, Object>> dataGrid = BeanUtils.classToArrayList(companyDept);
            model.put("dataGrid", dataGrid);
        }
        model.put("req", req);
        model.put("pageNum", req.getPageNum());
        model.put("numPerPage", req.getNumPerPage());
        model.put("count", count);
        return "/financialStatistics/companyDept/index";
    }

    /**
     * 公司部门删除操作
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/company_dept/delete")
    public @ResponseBody
    Map<Object, Object> delete(String id, HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> model) {
    	
    	List<BsCompanyDept> bsCompanyDept = financialStatisticsService.queryChildDeptInfo(Integer.parseInt(id),-1);
    	
    	if (!CollectionUtils.isEmpty(bsCompanyDept)) {
    		return ReturnDWZAjax.fail("该部门下面存在子部门，删除失败！");
		}
        //检查部门信息是否能被删除，部门下面是否挂靠人员
        Boolean flag = financialStatisticsService.checkDeptDeleteCheck(id);
        if (flag) {
            financialStatisticsService.deleteDept(id);
            return ReturnDWZAjax.success("删除成功！");
        } else {
            return ReturnDWZAjax.fail("部门下面还有员工挂靠，删除失败！");
        }

    }

    /**
     * 公司部门添加||编辑页面
     * @param id
     * @param detailFlag
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/company_dept/detail")
    public String detail(String id, String detailFlag, Map<String, Object> model,
                         HttpServletRequest request, HttpServletResponse response) {
        if (id == null || "".equals(id)) {
            model.put("detailFlag", detailFlag);
            return "/financialStatistics/companyDept/detail_add";
        } else {
            BsCompanyDept bsCompanyDept = financialStatisticsService.queryDeptInfo(id);
            if (!bsCompanyDept.getParentId().equals(0)) {
            	BsCompanyDept parentCompanyDept = financialStatisticsService.queryDeptInfo(bsCompanyDept.getParentId().toString());
            	model.put("parentName", parentCompanyDept.getDeptName());
            }else {
            	model.put("parentName", "币港湾");
			}
            model.put("bsCompanyDept", bsCompanyDept);
            model.put("detailFlag", detailFlag);
            return "/financialStatistics/companyDept/detail";
        }

    }

    /**
     * 公司部门添加||修改操作
     * @param req
     * @param parent_id
     * @param dept_name
     * @param detailFlag
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/company_dept/update")
    public @ResponseBody
    Map<Object, Object> update(BsCompanyDept req, String parent_id, String dept_name,
                               String detailFlag, HttpServletRequest request,
                               HttpServletResponse response, Map<String, Object> model) {
    	

    	//检查部门信息是否能被删除，部门下面是否挂靠人员
        if ("update".equals(detailFlag) && req.getId() != null) {
        	List<BsCompanyDept> BsCompanyDeptList  = financialStatisticsService.selectDeptByName(dept_name);
            if (!CollectionUtils.isEmpty(BsCompanyDeptList)) {
            	if (BsCompanyDeptList.get(0).getId().equals(req.getId())) {
					
				}else {
					return ReturnDWZAjax.fail("部门名称不能重复！");
				}
    		}
            req.setDeptName(dept_name);
            //req.setParentId(Integer.parseInt(parent_id));
            req.setUpdateTime(new Date());
            Boolean flag = financialStatisticsService.updateDeptInfo(req);
            if (flag) {
                return ReturnDWZAjax.success("操作成功！");
            } else {
                return ReturnDWZAjax.fail("更新失败！");
            }
        } else if ("add".equals(detailFlag)) {
        	List<BsCompanyDept> BsCompanyDeptList  = financialStatisticsService.selectDeptByName(dept_name);
            if (!CollectionUtils.isEmpty(BsCompanyDeptList)) {
            	return ReturnDWZAjax.fail("部门名称不能重复！");
    		}
            
        	String code = financialStatisticsService.queryRootCompanyDeptMaxCode(parent_id);
	  		  
            req.setDeptCode(code);
            req.setDeptName(dept_name);
            req.setParentId(Integer.parseInt(parent_id));
            req.setCreateTime(new Date());
            req.setUpdateTime(new Date());
            financialStatisticsService.addDeptInfo(req);
            return ReturnDWZAjax.success("操作成功！");
        } else {
            return ReturnDWZAjax.success("操作参数有误！");
        }

    }

    /**
     * 公司部门查询
     * @param id
     * @param not_id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/company_dept/selectDept")
    public @ResponseBody
    Map<String, Object> selectDept(String id, String not_id, HttpServletRequest request,
                                   HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        String isUpdate = request.getParameter("isUpdate");
        String currentDeptId = request.getParameter("currentDeptId");
        String noRoot = request.getParameter("noRoot");
        // 是否显示根节点
        if (StringUtil.isBlank(noRoot) || !"yes".equals(noRoot)) {
            if (StringUtil.isBlank(id)) {
                List<ZtreeModel> ztreeModelList = new ArrayList<ZtreeModel>();
                ZtreeModel ztreeModel = new ZtreeModel();
                ztreeModel.setId(0);
                ztreeModel.setName("币港湾");
                ztreeModel.setpId(null);
                ztreeModel.setParentId(null);
                ztreeModel.setIsParent(true);
                ztreeModel.setChecked(false);
                ztreeModelList.add(ztreeModel);
                model.put("treeNodes", ztreeModelList);
            }
        } else {
            if (StringUtil.isBlank(id)) {
                id = "0";
            }
        }
        if (StringUtil.isNotBlank(id)) {
            if (StringUtil.isBlank(not_id)) {
                // 页面无not_id参数传入，100%执行此处代码
                List<BsCompanyDept> list = financialStatisticsService.queryChildDeptInfo(
                    Integer.parseInt(id), -1);
                ArrayList<HashMap<String, Object>> deptList = BeanUtils.classToArrayList(list);
                List<ZtreeModel> ztreeModelList = new ArrayList<ZtreeModel>();
                for (HashMap<String, Object> hashMap : deptList) {
                    Boolean isParent = financialStatisticsService.isParentDept((Integer) hashMap
                        .get("id"));
                    ZtreeModel ztreeModel = new ZtreeModel();
                    ztreeModel.setId((Integer) hashMap.get("id"));
                    ztreeModel.setName((String) hashMap.get("deptName"));
                    ztreeModel.setpId((Integer) hashMap.get("parentId"));
                    ztreeModel.setParentId((Integer) hashMap.get("parentId"));
                    ztreeModel.setIsParent(isParent);
                    ztreeModel.setChecked(false);
                    Boolean isHidden = false;
                    if (StringUtil.isNotBlank(isUpdate) && "yes".equals(isUpdate)) {
                        // 如果是自己，则隐藏该节点
                        if (Integer.valueOf(currentDeptId).equals((Integer) hashMap.get("id"))) {
                            isHidden = true;
                        }
                    }
                    if (!isHidden) {
                        ztreeModelList.add(ztreeModel);
                    }
                }
                model.put("treeNodes", ztreeModelList);
            } else {
                // TODO 暂不知道not_id的作用，需要完善
                List<BsCompanyDept> list = financialStatisticsService.queryChildDeptInfo(
                    Integer.parseInt(id), Integer.parseInt(not_id));
                ArrayList<HashMap<String, Object>> deptList = BeanUtils.classToArrayList(list);
                model.put("bsCompanyDept", deptList);
            }
        }
        return model;
    }
    
    
/*	private Map<String, List<Object>> deptTitles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("代码");
		titles.add("名称");
		titles.add("明细");
		titleMap.put("title", titles);
		return titleMap;
	}
    
    
	@RequestMapping("/company_dept/export")
	public void exportCompanyDept(BsCompanyDeptVO req,HttpServletRequest request, HttpServletResponse response) {
		
		//需要导出的数据
		
        //Integer count = financialStatisticsService.queryFinancialStatisticsCount(req);
        List<BsCompanyDeptVO> companyDept = financialStatisticsService
                .queryFinancialStatisticsIndex(req);
        
        
        List<HashMap<String,Object>> datas =BeanUtils.classToArrayList(companyDept);
        
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		
		list.add(deptTitles());
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("deptCode"));
				obj.add(data.get("deptName"));
				obj.add(data.get("deptDetail"));
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "公司部门", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	}*/
    
    
    
    /**
	   * 导出公司部门
	   * @param request
	   * @param response
	   */
	  @RequestMapping("/company_dept/export")
	  public void exportCompanyDept(BsCompanyDeptVO req,HttpServletRequest request, HttpServletResponse response) {
	      
	      
	      //需要导出的数据
	      List<BsCompanyDeptVO> dataGrid = financialStatisticsService
	                .queryFinancialStatisticsIndex(req);
	      
	      Integer count = dataGrid.size();
	      
	      FileInputStream fis = null;
	      HSSFWorkbook wb = null;
	      try {
	          File file = new File(GlobEnvUtil.get("company.dept.excel"));
	          String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
	          //打开excel
	          fis = new FileInputStream(file);
	          POIFSFileSystem fs = new POIFSFileSystem(fis);
	          wb = new HSSFWorkbook(fs);
	          HSSFSheet sheetPage1 = wb.getSheetAt(0); //page1
	          sheetPage1.setColumnWidth(1,9000); //设置单据编号的列宽

	          HSSFCellStyle basicStyle = ExcelUtil.getBasicStyle(wb);
	          HSSFCellStyle textCellStyle = ExcelUtil.getTextStyle(wb);
	          HSSFCellStyle smallCellStyle = ExcelUtil.getSmallStyle(wb);
	          HSSFCellStyle dateCellStyle = ExcelUtil.getDateStyle(wb);
	          
	          int base = 1;
	          for(int i=0;i<dataGrid.size();i++) {
	        	  BsCompanyDeptVO vo = dataGrid.get(i);
	              //单据头
	              HSSFRow headRow = sheetPage1.createRow(base+i);
	              Cell head_cell_0 = headRow.createCell(0); //代码
	              head_cell_0.setCellStyle(basicStyle);
	              head_cell_0.setCellValue(vo.getDeptCode());
	              Cell head_cell_1 = headRow.createCell(1); //名称
	              head_cell_1.setCellStyle(textCellStyle);
	              head_cell_1.setCellValue(vo.getDeptName()==null ?"未命名部门":vo.getDeptName());
	              Cell head_cell_2 = headRow.createCell(2); //明细
	              head_cell_2.setCellStyle(dateCellStyle);
	              head_cell_2.setCellValue(vo.getDeptDetail());
	          }
	          
	          ExportUtil.exportExcel(wb, fileName, response, request);
	      }catch(Exception e) {
	          e.printStackTrace();
	      }finally {
	          try {
	              if(wb != null) {
	                  wb.close();
	              }
	              if(fis != null) {
	                  fis.close();
	              }
	          }catch(Exception e) {
	              e.printStackTrace();
	          }
	      }
	  }
  

    // ==================================================== 公司职员维护 ============================================================
    /**
     * 公司职员维护首页
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/company_employee/index")
    public String companyEmployeeIndex(BsCompanyEmployeeVO req, Map<String, Object> model,
                                       HttpServletRequest request, HttpServletResponse response) {
        // 公司职员列表
		
/*		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		
		req.setStartTime(StringUtil.isBlank(req.getStartTime())==true?yestoday:req.getStartTime());
		req.setEndTime(StringUtil.isBlank(req.getEndTime())==true?yestoday:req.getEndTime());*/
	
    	
		model.put("startTime", req.getStartTime());
		model.put("endTime", req.getEndTime());
    	
        Integer count = financialStatisticsService.queryCompanyEmployeeCount(req);
        if (count != null && count > 0) {
            List<BsCompanyEmployeeVO> companyEmployee = financialStatisticsService
                .queryCompanyEmployeeIndex(req);
            ArrayList<HashMap<String, Object>> dataGrid = BeanUtils
                .classToArrayList(companyEmployee);
            model.put("dataGrid", dataGrid);
        }

        model.put("req", req);
        model.put("pageNum", req.getPageNum());
        model.put("numPerPage", req.getNumPerPage());
        model.put("count", count);
        return "/financialStatistics/companyEmployee/index";
    }

/*    //公司职员导出标题
	private Map<String, List<Object>> employeeTitles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("代码");
		titles.add("名称");
		titles.add("明细");
		titleMap.put("title", titles);
		return titleMap;
	}
    
    
	@RequestMapping("/company_employee/export")
	public void exportCompanyEmployee(BsCompanyEmployeeVO req,HttpServletRequest request, HttpServletResponse response) {
		//需要导出的数据
        List<BsCompanyEmployeeVO> companyEmployee = financialStatisticsService
                .queryCompanyEmployeeIndex(req);
        List<HashMap<String,Object>> datas =BeanUtils.classToArrayList(companyEmployee);
        
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		
		list.add(employeeTitles());
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("employeeCode"));
				obj.add(data.get("employeeName"));
				obj.add("TRUE");
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		try {
			ExportUtil.exportExcel(response, request, "公司职员", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	 
	}*/
    
    
	 /**
	   * 导出公司职员
	   * @param request
	   * @param response
	   */
	  @RequestMapping("/company_employee/export")
	  public void exportCompanyEmployee(BsCompanyEmployeeVO req,HttpServletRequest request, HttpServletResponse response) {
	      
	      
	      //需要导出的数据
	      List<BsCompanyEmployeeVO> dataGrid = financialStatisticsService
	                .queryCompanyEmployeeIndex(req);
	      
	      Integer count = dataGrid.size();
	      
	      FileInputStream fis = null;
	      HSSFWorkbook wb = null;
	      try {
	          File file = new File(GlobEnvUtil.get("company.employee.excel"));
	          String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
	          //打开excel
	          fis = new FileInputStream(file);
	          POIFSFileSystem fs = new POIFSFileSystem(fis);
	          wb = new HSSFWorkbook(fs);
	          HSSFSheet sheetPage1 = wb.getSheetAt(0); //page1
	          sheetPage1.setColumnWidth(1,9000); //设置单据编号的列宽

	          HSSFCellStyle basicStyle = ExcelUtil.getBasicStyle(wb);
	          HSSFCellStyle textCellStyle = ExcelUtil.getTextStyle(wb);
	          HSSFCellStyle smallCellStyle = ExcelUtil.getSmallStyle(wb);
	          HSSFCellStyle dateCellStyle = ExcelUtil.getDateStyle(wb);
	          
	          int base = 1;
	          for(int i=0;i<dataGrid.size();i++) {
	        	  BsCompanyEmployeeVO vo = dataGrid.get(i);
	              //单据头
	              HSSFRow headRow = sheetPage1.createRow(base+i);
	              Cell head_cell_0 = headRow.createCell(0); //代码
	              head_cell_0.setCellStyle(basicStyle);
	              head_cell_0.setCellValue(vo.getEmployeeCode());
	              Cell head_cell_1 = headRow.createCell(1); //名称
	              head_cell_1.setCellStyle(textCellStyle);
	              head_cell_1.setCellValue(vo.getEmployeeName()==null ?"未命名职员":vo.getEmployeeName());
	              Cell head_cell_2 = headRow.createCell(2); //明细
	              head_cell_2.setCellStyle(dateCellStyle);
	              head_cell_2.setCellValue("TRUE");
	          }
	          
	          ExportUtil.exportExcel(wb, fileName, response, request);
	      }catch(Exception e) {
	          e.printStackTrace();
	      }finally {
	          try {
	              if(wb != null) {
	                  wb.close();
	              }
	              if(fis != null) {
	                  fis.close();
	              }
	          }catch(Exception e) {
	              e.printStackTrace();
	          }
	      }
	  }
    
    
    /**
     * 公司职员维护删除操作
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/company_employee/delete")
    public @ResponseBody
    Map<Object, Object> employeeDelete(String id, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {

        //检查部门信息是否能被删除，部门下面是否挂靠人员
        Boolean flag = financialStatisticsService.deleteEmployee(id);
        if (flag) {
            return ReturnDWZAjax.success("操作成功！");
        } else {
            return ReturnDWZAjax.fail("删除失败！");
        }

    }

    /**
     * 公司职员维护添加||编辑页面
     * @param id
     * @param detailFlag
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/company_employee/detail")
    public String employeeDetail(String id, String detailFlag, Map<String, Object> model,
                                 HttpServletRequest request, HttpServletResponse response) {
        if (id == null || "".equals(id)) {
        	//查询公司部门数量
        	BsCompanyDeptVO req = new BsCompanyDeptVO();
            Integer countInteger = financialStatisticsService.queryFinancialStatisticsCount(req);
            if (countInteger.equals(0)) {
				model.put("dept_flag", "no");
			}
            model.put("detailFlag", detailFlag);
            return "/financialStatistics/companyEmployee/detail_add";
        } else {
            BsCompanyEmployee bsCompanyEmployee = financialStatisticsService.queryEmployeeInfo(id);
            model.put("bsCompanyEmployee", bsCompanyEmployee);
            model.put("detailFlag", detailFlag);
            return "/financialStatistics/companyEmployee/detail";
        }
    }

    /**
     * 公司职员维护添加||修改操作
     * @param req
     * @param parent_id
     * @param employee_name
     * @param detailFlag
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/company_employee/update")
    public @ResponseBody
    Map<Object, Object> employeeUpdate(BsCompanyEmployee req, String parent_id,
                                       String employee_name, String detailFlag,
                                       HttpServletRequest request, HttpServletResponse response,
                                       Map<String, Object> model) {
        //检查部门信息是否能被删除，部门下面是否挂靠人员
        if ("update".equals(detailFlag) && req.getId() != null) {
            req.setEmployeeName(employee_name);
            req.setDeptId(Integer.parseInt(parent_id));
            req.setUpdateTime(new Date());
            Boolean flag = financialStatisticsService.updateEmployeeInfo(req);
            if (flag) {
                return ReturnDWZAjax.success("操作成功！");
            } else {
                return ReturnDWZAjax.fail("更新失败！");
            }
        } else if ("add".equals(detailFlag)) {
            req.setEmployeeCode("20" + String.valueOf((new Date()).getTime()));
            req.setEmployeeName(employee_name);
            req.setDeptId(Integer.parseInt(parent_id));
            req.setCreateTime(new Date());
            req.setUpdateTime(new Date());
            financialStatisticsService.addEmployeeInfo(req);
            return ReturnDWZAjax.success("操作成功！");
        } else {
            return ReturnDWZAjax.success("操作参数有误！");
        }
    }

    // TODO ==================================================== 公司客户维护 ============================================================
    /**
     * 公司客户维护首页
     * @param req
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/company_customer/index")
    public String companyCustomerIndex(CompanyCustomerVO req, Map<String, Object> model,
                                       HttpServletRequest request, HttpServletResponse response) {
        // 公司职员列表

		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DAY_OF_MONTH, -1);
		String yestoday = DateUtil.formatYYYYMMDD(calendar.getTime());
		
		req.setStartTime(StringUtil.isBlank(req.getStartTime())==true?yestoday:req.getStartTime());
		req.setEndTime(StringUtil.isBlank(req.getEndTime())==true?yestoday:req.getEndTime());
	
    	
		model.put("startTime", req.getStartTime());
		model.put("endTime", req.getEndTime());
		
		String flag = request.getParameter("flag");
    	
		if (flag != null && "business".equals(flag)) {
	        int count = financialStatisticsService.queryCompanyCustomerBusinessCount(req);
	        if (count > 0) {
	            List<BsCompanyCustomerVO> vos = financialStatisticsService.queryCompanyCustomerBusinessIndex(req);
	            ArrayList<HashMap<String, Object>> dataGrid = BeanUtils
	                .classToArrayList(vos);
	            model.put("dataGrid", dataGrid);
	        }
	        model.put("count", count);
		}else {
	        int count = financialStatisticsService.queryCompanyCustomerCount(req);
	        if (count > 0) {
	            List<BsCompanyCustomerVO> vos = financialStatisticsService.queryCompanyCustomerIndex(req);
	            ArrayList<HashMap<String, Object>> dataGrid = BeanUtils
	                .classToArrayList(vos);
	            model.put("dataGrid", dataGrid);
	        }
	        model.put("count", count);
		}


        model.put("req", req);
        model.put("pageNum", req.getPageNum());
        model.put("numPerPage", req.getNumPerPage());

        return "/financialStatistics/companyCustomer/company_customer_index";
    }
    
    /**
     * 公司客户维护添加||编辑页面
     * @param id
     * @param detailFlag
     * @param model
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("company_customer/detail")
    public String customerDetail(String id, String detailFlag, Map<String, Object> model,
                                 HttpServletRequest request, HttpServletResponse response) {
        if (id == null || "".equals(id)) {
            model.put("detailFlag", detailFlag);
            return "/financialStatistics/companyCustomer/customer_add";
        } else {
            BsCompanyCustomer bsCompanyCustomer = financialStatisticsService.queryCustomerById(id);
            if (!bsCompanyCustomer.getParentId().equals(0)) {
            	BsCompanyCustomer parentCompanyCustomer = financialStatisticsService.queryCustomerById(bsCompanyCustomer.getParentId().toString());
            	model.put("parentName", parentCompanyCustomer.getCustomerName());
            }else {
            	model.put("parentName", "币港湾客户");
			}
            
            model.put("bsCompanyCustomer", bsCompanyCustomer);
            model.put("detailFlag", detailFlag);
            return "/financialStatistics/companyCustomer/customer_update";
        }
    }
    
    @RequestMapping("company_customer/choose_user")
    public String chooseUser(String detailFlag, Map<String, Object> model, BsUser req,
                                 HttpServletRequest request, HttpServletResponse response) {
        List<BsUser> bsUsers = bsUserService.selectUserList(req);
        int count = bsUserService.countBsUserByMap(new HashMap<String, Object>());
        req.setTotalRows(count);
        model.put("req", req);
        model.put("count", count);
        model.put("dataGrid", bsUsers);
        return "/financialStatistics/companyCustomer/choose_user";
    }
    
    /**
     * 公司客户维护删除操作
     * @param id
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/company_customer/delete")
    public @ResponseBody
    Map<Object, Object> customerDelete(String id, HttpServletRequest request,
                                       HttpServletResponse response, Map<String, Object> model) {
        
    	List<BsCompanyCustomer> companyCustomers = financialStatisticsService.queryCustomerChildren(Integer.parseInt(id));
    	
    	if (!CollectionUtils.isEmpty(companyCustomers)) {
    		 return ReturnDWZAjax.fail("非叶子节点，不可删除！");
		}
    	
    	Boolean flag = financialStatisticsService.deleteCustomer(id);
        if (flag) {
            return ReturnDWZAjax.success("操作成功！");
        } else {
            return ReturnDWZAjax.fail("删除失败！");
        }
    }
    
    /**
     * 公司部门添加||修改操作
     * @param req
     * @param parent_id
     * @param dept_name
     * @param detailFlag
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/company_customer/update")
    public @ResponseBody
    Map<Object, Object> customerUpdate(BsCompanyCustomer req, String parent_id, String customer_name, Integer new_user_id,
                               String detailFlag, HttpServletRequest request,
                               HttpServletResponse response, Map<String, Object> model) {
        if (new_user_id != null && !"".equals(new_user_id)) {
			Boolean isRelation = financialStatisticsService.queryUserIsRelationInCompanyCustomer(req.getId(),new_user_id);
			if (isRelation) {
				 return ReturnDWZAjax.fail("该投资用户已存在！");
			}
        }
    	
    	if ("update".equals(detailFlag) && req.getId() != null) {
/*        	String code = financialStatisticsService.queryRootCompanyCustomerMaxCode(req.getId(),parent_id);
           
        	Random random = new Random();
        	String randString = "";
    		for (int i = 0; i < 3; i++) {
    			String rand = String.valueOf(random.nextInt(10));
    			randString = randString + rand;
    		}
    		
        	if(new_user_id != null) {
                   req.setCustomerCode(code +"_"+new_user_id + randString);
            } */
            req.setUserId(new_user_id);
            req.setCustomerName(customer_name);
            //req.setParentId(Integer.parseInt(parent_id));
            req.setUpdateTime(new Date());
            Boolean flag = financialStatisticsService.updateCompanyCustomer(req);
            if (flag) {
                return ReturnDWZAjax.success("操作成功！");
            } else {
                return ReturnDWZAjax.fail("更新失败！");
            }
        } else if ("add".equals(detailFlag)) {
        	
        	String code = financialStatisticsService.queryRootCompanyCustomerMaxCode(null,parent_id);
	  		  
        	Random random = new Random();
        	String randString = "";
    		for (int i = 0; i < 3; i++) {
    			String rand = String.valueOf(random.nextInt(10));
    			randString = randString + rand;
    		}
        	
	    	  if(new_user_id != null) {
	              req.setUserId(new_user_id);
	              req.setCustomerCode(code +"_"+ new_user_id + randString);
	          } else {
	              req.setCustomerCode(code);
	          }
        	
          
            req.setCustomerName(customer_name);
            req.setParentId(Integer.parseInt(parent_id));
            req.setCreateTime(new Date());
            req.setUpdateTime(new Date());
            financialStatisticsService.addCompanyCustomer(req);
            return ReturnDWZAjax.success("操作成功！");
        } else {
            return ReturnDWZAjax.success("操作参数有误！");
        }

    }
    
    /**
     * 公司客户查询（树）
     * @param id
     * @param not_id
     * @param request
     * @param response
     * @return
     */
    @RequestMapping("/company_customer/customerTree")
    public @ResponseBody
    Map<String, Object> customerTree(String id, HttpServletRequest request,
                                   HttpServletResponse response) {
        Map<String, Object> model = new HashMap<String, Object>();
        String isUpdate = request.getParameter("isUpdate");
        String currentDeptId = request.getParameter("currentDeptId");
        String noRoot = request.getParameter("noRoot");
        // 是否显示根节点
        if (StringUtil.isBlank(noRoot) || !"yes".equals(noRoot)) {
            if (StringUtil.isBlank(id)) {
                List<ZtreeModel> ztreeModelList = new ArrayList<ZtreeModel>();
                ZtreeModel ztreeModel = new ZtreeModel();
                ztreeModel.setId(0);
                ztreeModel.setName("币港湾");
                ztreeModel.setpId(null);
                ztreeModel.setParentId(null);
                ztreeModel.setIsParent(true);
                ztreeModel.setChecked(false);
                ztreeModelList.add(ztreeModel);
                model.put("treeNodes", ztreeModelList);
            }
        } else {
            if (StringUtil.isBlank(id)) {
                id = "0";
            }
        }
        if (StringUtil.isNotBlank(id)) {
            // 页面无not_id参数传入，100%执行此处代码
            List<BsCompanyCustomer> list = financialStatisticsService.queryCustomerChildren(Integer.parseInt(id));
            List<ZtreeModel> ztreeModelList = new ArrayList<ZtreeModel>();
            for (BsCompanyCustomer customer : list) {
            	if (customer.getCustomerCode().equals("3")) {

				}else {
	                Boolean isParent = financialStatisticsService.isParentCustomer(customer.getId());
	                ZtreeModel ztreeModel = new ZtreeModel();
	                ztreeModel.setId(customer.getId());
	                ztreeModel.setName(customer.getCustomerName());
	                ztreeModel.setpId(customer.getParentId());
	                ztreeModel.setParentId(customer.getParentId());
	                ztreeModel.setIsParent(isParent);
	                ztreeModel.setChecked(false);
	                Boolean isHidden = false;
	                if (StringUtil.isNotBlank(isUpdate) && "yes".equals(isUpdate)) {
	                    // 如果是自己，则隐藏该节点
	                    if (Integer.valueOf(currentDeptId).equals(customer.getId())) {
	                        isHidden = true;
	                    }
	                }
	                if (!isHidden) {
	                    ztreeModelList.add(ztreeModel);
	                }
				}

            }
            model.put("treeNodes", ztreeModelList);
        }
        return model;
    }
    
    
    
    
    /*	private Map<String, List<Object>> customerTitles(){
		Map<String,List<Object>> titleMap = new HashMap<String, List<Object>>();
		List<Object> titles = new ArrayList<Object>();
		titles.add("代码");
		titles.add("名称");
		titles.add("明细");
		titleMap.put("title", titles);
		return titleMap;
	}
    
    
	@RequestMapping("/company_customer/export")
	public void exportCompanyCustomer(CompanyCustomerVO req,HttpServletRequest request, HttpServletResponse response) {
		
		//需要导出的数据
		List<BsCompanyCustomerVO> companyCustomer = financialStatisticsService.queryCompanyCustomerExport(req);
        
        List<HashMap<String,Object>> datas =BeanUtils.classToArrayList(companyCustomer);
        
		List<Map<String,List<Object>>> list = new ArrayList<Map<String,List<Object>>>();
		
		list.add(customerTitles());
		//设置导出excel内容
		if(datas != null && !datas.isEmpty()) {
			int i = 0;
			for (HashMap<String,Object> data : datas) {
				Map<String,List<Object>> datasMap = new HashMap<String, List<Object>>();
				List<Object> obj = new ArrayList<Object>();
				obj.add(data.get("customerCode"));
				obj.add(data.get("customerName"));
				obj.add(data.get("customerDetail"));
				datasMap.put("user"+(++i), obj);
				list.add(datasMap);
			}
		}
		
		try {
			ExportUtil.exportExcel(response, request, "公司客户", list);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}*/
	
	 /**
	   * 导出公司客户
	   * @param request
	   * @param response
	   */
	  @RequestMapping("/company_customer/export")
	  public void exportCompanyCustomer(CompanyCustomerVO req,HttpServletRequest request, HttpServletResponse response) {
	      
	      
	      //需要导出的数据
		  String flag = request.getParameter("flag");
		  List<CompanyCustomerVO> dataGrid  =  new ArrayList<CompanyCustomerVO>();
		  if (flag != null && "business".equals(flag)) {
			  dataGrid = financialStatisticsService.queryCompanyCustomerBusinessExport(req);
		  }else {
			  dataGrid = financialStatisticsService.queryCompanyCustomerExport(req);
		  }
	      
	      Integer count = dataGrid.size();
	      
	      FileInputStream fis = null;
	      HSSFWorkbook wb = null;
	      try {
	          File file = new File(GlobEnvUtil.get("company.customer.excel"));
	          String fileName = file.getName().substring(0, file.getName().lastIndexOf("."));
	          //打开excel
	          fis = new FileInputStream(file);
	          POIFSFileSystem fs = new POIFSFileSystem(fis);
	          wb = new HSSFWorkbook(fs);
	          HSSFSheet sheetPage1 = wb.getSheetAt(0); //page1
	          sheetPage1.setColumnWidth(1,9000); //设置单据编号的列宽

	          HSSFCellStyle basicStyle = ExcelUtil.getBasicStyle(wb);
	          HSSFCellStyle textCellStyle = ExcelUtil.getTextStyle(wb);
	          HSSFCellStyle smallCellStyle = ExcelUtil.getSmallStyle(wb);
	          HSSFCellStyle dateCellStyle = ExcelUtil.getDateStyle(wb);
	          
	          int base = 1;
	          for(int i=0;i<dataGrid.size();i++) {
	        	  CompanyCustomerVO vo = dataGrid.get(i);
	              //单据头
	              HSSFRow headRow = sheetPage1.createRow(base+i);
	              Cell head_cell_0 = headRow.createCell(0); //代码
	              head_cell_0.setCellStyle(basicStyle);
	              head_cell_0.setCellValue(vo.getCustomerCode());
	              Cell head_cell_1 = headRow.createCell(1); //名称
	              head_cell_1.setCellStyle(textCellStyle);
	              head_cell_1.setCellValue(vo.getCustomerName()==null ?"未命名客户":vo.getCustomerName());
	              Cell head_cell_2 = headRow.createCell(2); //明细
	              head_cell_2.setCellStyle(dateCellStyle);
	              head_cell_2.setCellValue(vo.getCustomerDetail());
	          }
	          
	          ExportUtil.exportExcel(wb, fileName, response, request);
	      }catch(Exception e) {
	          e.printStackTrace();
	      }finally {
	          try {
	              if(wb != null) {
	                  wb.close();
	              }
	              if(fis != null) {
	                  fis.close();
	              }
	          }catch(Exception e) {
	              e.printStackTrace();
	          }
	      }
	  }
    
}
