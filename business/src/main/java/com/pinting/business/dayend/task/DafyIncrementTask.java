package com.pinting.business.dayend.task;

import com.pinting.business.dao.BsSpecialJnlMapper;
import com.pinting.business.dao.TbdatapermissionMapper;
import com.pinting.business.dao.TbdepartmentMapper;
import com.pinting.business.dao.TbemployeeMapper;
import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.BsSpecialJnlExample;
import com.pinting.business.service.site.SpecialJnlService;
import com.pinting.business.util.DateUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.out.service.DafyTransportService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
@Service
public class DafyIncrementTask {

	private Logger logger = LoggerFactory.getLogger(DafyIncrementTask.class);

	@Autowired
	private DafyTransportService dafyService;

	@Autowired
	private TbemployeeMapper employeeMapper;
	
	@Autowired
	private TbdepartmentMapper deptMapper;
	
	@Autowired
	private TbdatapermissionMapper dataMapper;
	
	@Autowired
	private SpecialJnlService specialJnlService;
	
	@Autowired
	private BsSpecialJnlMapper bsSpecialJnlMapper;
	
	// 定时任务执行方法
	public void execute() {
		Calendar calendar = Calendar.getInstance();
		
		logger.info("============定时任务：达飞用户信息，部门信息和权限信息增量同步开始执行============");

		//查询用户上一次同步的时间
		logger.info("============调用用户增量信息接口============");
		List<Date> userTimeList = employeeMapper.selectSyncTime();
		calendar.setTime(userTimeList.get(0));
		calendar.add(Calendar.MINUTE, -1);
		String userUpdateTime = DateUtil.format(calendar.getTime(), "yyyyMMddHHmmss");
		//达飞用户信息同步
		try {
			saveOrUpdateUserInfo(userUpdateTime, 0);
		}catch(Exception e) {
			warn("达飞用户信息同步"); //告警
			logger.error("同步用户增量信息出现异常");
			e.printStackTrace();
		}
		
		//查询部门上一次同步的时间
		logger.info("============调用部门增量信息接口============");
		List<Date> deptTimeList = deptMapper.selectSyncTime();
		calendar.setTime(deptTimeList.get(0));
		calendar.add(Calendar.MINUTE, -1);
		String deptUpdateTime = DateUtil.format(calendar.getTime(), "yyyyMMddHHmmss");
		//达飞部门信息同步
		try {
			saveOrUpdateDeptInfo(deptUpdateTime, 0);
		}catch(Exception e) {
			warn("达飞部门信息同步"); //告警
			logger.error("同步部门增量信息出现异常");
			e.printStackTrace();
		}
		
		//查询权限上一次同步的时间
		logger.info("============调用权限增量信息接口============");
		List<Date> dataTimeList = dataMapper.selectSyncTime();
		calendar.setTime(dataTimeList.get(0));
		calendar.add(Calendar.MINUTE, -1);
		String dataUpdateTime = DateUtil.format(calendar.getTime(), "yyyyMMddHHmmss");
		//达飞权限信息同步
		try {
			saveOrUpdateDataInfo(dataUpdateTime, 0);
		}catch(Exception e) {
			warn("达飞权限信息同步"); //告警
			logger.error("同步权限增量信息出现异常");
			e.printStackTrace();
		}
		
		logger.info("============定时任务：达飞用户信息，部门信息和权限信息增量同步执行结束============");
	}
	
	private void saveOrUpdateUserInfo(String userUpdateTime, Integer pageIndex) {
		//调用用户同步接口
		B2GReqMsg_Customer_IncrementUserInfo userReq = new B2GReqMsg_Customer_IncrementUserInfo();
		userReq.setDtUpdateTime(userUpdateTime);
		userReq.setPageIndex(pageIndex);
		B2GResMsg_Customer_IncrementUserInfo userRes = dafyService.incrementUserInfo(userReq);
		pageIndex++;
		if (userRes.getCode() != null && userRes.getCode() == 0) {
			if(!CollectionUtils.isEmpty(userRes.getUserList())) {
				StringBuffer prefix = new StringBuffer("INSERT INTO tbemployee(lUserId,strLoginId,strName,nSex,strEmployeeNo,strTitle,strRole,strMobile,strEmail,strBirthday,strDeptCode,strDeptName,nCurrentLevel,nWorkState,nIsShutdown,strDeptCode0,strDeptCode1,strDeptCode2,strDeptCode3,strDeptCode4,strDeptCode5,strDeptCode6,strDeptCode7,strDeptCode8,strDeptCode9,dtCreateTime,strDeptProvinceCode,strDeptProvinceName,strDeptCityCode,strDeptCityName,strDeptAddress,dtUpdateTime,strWorkDay,lOldSalesId) VALUES");
				String suffix = " ON DUPLICATE KEY UPDATE strLoginId=VALUES(strLoginId),strName=VALUES(strName),nSex=VALUES(nSex),strEmployeeNo=VALUES(strEmployeeNo),strTitle=VALUES(strTitle),strRole=VALUES(strRole),strMobile=VALUES(strMobile),strEmail=VALUES(strEmail),strBirthday=VALUES(strBirthday),strDeptCode=VALUES(strDeptCode),strDeptName=VALUES(strDeptName),nCurrentLevel=VALUES(nCurrentLevel),nWorkState=VALUES(nWorkState),nIsShutdown=VALUES(nIsShutdown),"
						+ "strDeptCode0=VALUES(strDeptCode0),strDeptCode1=VALUES(strDeptCode1),strDeptCode2=VALUES(strDeptCode2),strDeptCode3=VALUES(strDeptCode3),strDeptCode4=VALUES(strDeptCode4),strDeptCode5=VALUES(strDeptCode5),strDeptCode6=VALUES(strDeptCode6),strDeptCode7=VALUES(strDeptCode7),strDeptCode8=VALUES(strDeptCode8),strDeptCode9=VALUES(strDeptCode9),"
						+ "dtCreateTime=VALUES(dtCreateTime),strDeptProvinceCode=VALUES(strDeptProvinceCode),strDeptProvinceName=VALUES(strDeptProvinceName),strDeptCityCode=VALUES(strDeptCityCode),strDeptCityName=VALUES(strDeptCityName),strDeptAddress=VALUES(strDeptAddress),dtUpdateTime=VALUES(dtUpdateTime),strWorkDay=VALUES(strWorkDay),lOldSalesId=VALUES(lOldSalesId)";
				for (HashMap<String, Object> model : userRes.getUserList()) {
					Long luserid = Long.valueOf((String)model.get("lUserId"));
					String strloginid = (String)model.get("strLoginId");
					String strname = (String)model.get("strName");
					Integer nsex = (Integer)model.get("nSex");
					String stremployeeno = (String)model.get("strEmployeeNo");
					String strtitle = (String)model.get("strTitle");
					String strrole = (String)model.get("strRole");
					String strmobile = (String)model.get("strMobile");
					String stremail = (String)model.get("strEmail");
					String strbirthday = (String)model.get("strBirthday");
					String strdeptcode = (String)model.get("strDeptCode");
					String strdeptname = (String)model.get("strDeptName");
					Integer ncurrentlevel = (Integer)model.get("nCurrentLevel");
					Integer nworkstate = (Integer)model.get("nWorkState");
					Integer nisshutdown = (Integer)model.get("nIsShutdown");
					String strdeptcode0 = (String)model.get("strDeptCode0");
					String strdeptcode1 = (String)model.get("strDeptCode1");
					String strdeptcode2 = (String)model.get("strDeptCode2");
					String strdeptcode3 = (String)model.get("strDeptCode3");
					String strdeptcode4 = (String)model.get("strDeptCode4");
					String strdeptcode5 = (String)model.get("strDeptCode5");
					String strdeptcode6 = (String)model.get("strDeptCode6");
					String strdeptcode7 = (String)model.get("strDeptCode7");
					String strdeptcode8 = (String)model.get("strDeptCode8");
					String strdeptcode9 = (String)model.get("strDeptCode9");
					String dtcreatetime = (String)model.get("dtCreateTime");
					String strdeptprovincecode = (String)model.get("strDeptProvinceCode");
					String strdeptprovincename = (String)model.get("strDeptProvinceName");
					String strdeptcitycode = (String)model.get("strDeptCityCode");
					String strdeptcityname = (String)model.get("strDeptCityName");
					String strdeptaddress = (String)model.get("strDeptAddress");
					String dtupdatetime = (String)model.get("dtUpdateTime");
					String strworkday = (String)model.get("strWorkDay");
					Long loldsalesid = Long.valueOf((String)model.get("lOldSalesId"));
					prefix.append("("+luserid+",'"+strloginid+"','"+strname+"',"+nsex+",'"+stremployeeno+"','"+strtitle+"','"+strrole+"','"+strmobile+"','"+stremail+"','"+strbirthday+"','"+strdeptcode+"','"+strdeptname+"',"+ncurrentlevel+","+nworkstate+","+nisshutdown+",'"+strdeptcode0+"','"+strdeptcode1+"','"+strdeptcode2+"','"+strdeptcode3+"','"+strdeptcode4+"','"+strdeptcode5+"','"+strdeptcode6+"','"+strdeptcode7+"','"+strdeptcode8+"','"+strdeptcode9+"','"+dtcreatetime+"','"+strdeptprovincecode+"','"+strdeptprovincename+"','"+strdeptcitycode+"','"+strdeptcityname+"','"+strdeptaddress+"','"+dtupdatetime+"','"+strworkday+"',"+loldsalesid+"),");
				}
				prefix.deleteCharAt(prefix.length()-1);
				String userSql = prefix.toString() + suffix;
				employeeMapper.batchInsertTbemployee(userSql);
				logger.info("============同步用户增量数据"+userRes.getUserList().size()+"条(第"+pageIndex+"页)============");
			}
			Integer isNext = userRes.getIsNext();
			//有下一页
			if(isNext == 1) {
				saveOrUpdateUserInfo(userUpdateTime, pageIndex);
			}
		}
		else {
			if(StringUtil.isNotBlank(userRes.getMsg())) {
				logger.info("============同步用户增量信息失败(第"+pageIndex+"页)，失败原因：" + userRes.getMsg() + "============");
			}
			else {
				logger.info("============同步用户增量信息失败(第"+pageIndex+"页)，失败原因：连接超时============");
			}
		}
	}
	
	private void saveOrUpdateDeptInfo(String deptUpdateTime, Integer pageIndex) {
		//调用部门同步接口
		B2GReqMsg_Customer_IncrementDeptInfo deptReq = new B2GReqMsg_Customer_IncrementDeptInfo();
		deptReq.setDtUpdateTime(deptUpdateTime);
		deptReq.setPageIndex(pageIndex);
		B2GResMsg_Customer_IncrementDeptInfo deptRes = dafyService.incrementDeptInfo(deptReq);
		pageIndex++;
		if(deptRes.getCode()!= null && deptRes.getCode() == 0) {
			if(!CollectionUtils.isEmpty(deptRes.getDeptList())) {
				StringBuffer prefix = new StringBuffer("INSERT INTO tbdepartment(lId,strDeptCode,strDeptName,strDeptProvinceCode,strDeptProvinceName,strDeptCityCode,strDeptCityName,strDeptAddress,nCurrentLevel,bIsLeaf,bIsSalesDept,strDeptCode0,strDeptName0,strDeptCode1,strDeptName1,strDeptCode2,strDeptName2,strDeptCode3,strDeptName3,strDeptCode4,strDeptName4,strDeptCode5,strDeptName5,strDeptCode6,strDeptName6,strDeptCode7,strDeptName7,strDeptCode8,strDeptName8,strDeptCode9,strDeptName9,lManagerId,strManagerName,strManagerMobile,strManagerMail,dtUpdateTime,nSalesLevel,strOpenDay,strDeptMobile) VALUES");
				String suffix = " ON DUPLICATE KEY UPDATE strDeptCode=VALUES(strDeptCode),strDeptName=VALUES(strDeptName),strDeptProvinceCode=VALUES(strDeptProvinceCode),strDeptProvinceName=VALUES(strDeptProvinceName),strDeptCityCode=VALUES(strDeptCityCode),strDeptCityName=VALUES(strDeptCityName),strDeptAddress=VALUES(strDeptAddress),nCurrentLevel=VALUES(nCurrentLevel),bIsLeaf=VALUES(bIsLeaf),bIsSalesDept=VALUES(bIsSalesDept),strDeptCode0=VALUES(strDeptCode0),strDeptName0=VALUES(strDeptName0),strDeptCode1=VALUES(strDeptCode1),strDeptName1=VALUES(strDeptName1),"
						+ "strDeptCode2=VALUES(strDeptCode2),strDeptName2=VALUES(strDeptName2),strDeptCode3=VALUES(strDeptCode3),strDeptName3=VALUES(strDeptName3),strDeptCode4=VALUES(strDeptCode4),strDeptName4=VALUES(strDeptName4),strDeptCode5=VALUES(strDeptCode5),strDeptName5=VALUES(strDeptName5),strDeptCode6=VALUES(strDeptCode6),strDeptName6=VALUES(strDeptName6),strDeptCode7=VALUES(strDeptCode7),strDeptName7=VALUES(strDeptName7),strDeptCode8=VALUES(strDeptCode8),strDeptName8=VALUES(strDeptName8),strDeptCode9=VALUES(strDeptCode9),strDeptName9=VALUES(strDeptName9),"
						+ "lManagerId=VALUES(lManagerId),strManagerName=VALUES(strManagerName),strManagerMobile=VALUES(strManagerMobile),strManagerMail=VALUES(strManagerMail),dtUpdateTime=VALUES(dtUpdateTime),nSalesLevel=VALUES(nSalesLevel),strOpenDay=VALUES(strOpenDay),strDeptMobile=VALUES(strDeptMobile)";
				for (HashMap<String, Object> model : deptRes.getDeptList()) {
					Long lid = Long.valueOf((String)model.get("lId"));
					String strdeptcode = (String)model.get("strDeptCode");
					String strdeptname = (String)model.get("strDeptName");
					String strdeptprovincecode = (String)model.get("strDeptProvinceCode");
					String strdeptprovincename = (String)model.get("strDeptProvinceName");
					String strdeptcitycode = (String)model.get("strDeptCityCode");
					String strdeptcityname = (String)model.get("strDeptCityName");
					String strdeptaddress = (String)model.get("strDeptAddress");
					Integer ncurrentlevel = (Integer)model.get("nCurrentLevel");
					Integer bisleaf = (Integer)model.get("bIsLeaf");
					Integer bissalesdept = (Integer)model.get("bIsSalesDept");
					String strdeptcode0 = (String)model.get("strDeptCode0");
					String strdeptname0 = (String)model.get("strDeptName0");
					String strdeptcode1 = (String)model.get("strDeptCode1");
					String strdeptname1 = (String)model.get("strDeptName1");
					String strdeptcode2 = (String)model.get("strDeptCode2");
					String strdeptname2 = (String)model.get("strDeptName2");
					String strdeptcode3 = (String)model.get("strDeptCode3");
					String strdeptname3 = (String)model.get("strDeptName3");
					String strdeptcode4 = (String)model.get("strDeptCode4");
					String strdeptname4 = (String)model.get("strDeptName4");
					String strdeptcode5 = (String)model.get("strDeptCode5");
					String strdeptname5 = (String)model.get("strDeptName5");
					String strdeptcode6 = (String)model.get("strDeptCode6");
					String strdeptname6 = (String)model.get("strDeptName6");
					String strdeptcode7 = (String)model.get("strDeptCode7");
					String strdeptname7 = (String)model.get("strDeptName7");
					String strdeptcode8 = (String)model.get("strDeptCode8");
					String strdeptname8 = (String)model.get("strDeptName8");
					String strdeptcode9 = (String)model.get("strDeptCode9");
					String strdeptname9 = (String)model.get("strDeptName9");
					Long lmanagerid = Long.valueOf((String)model.get("lManagerId"));
					String strmanagername = (String)model.get("strManagerName");
					String strmanagermobile = (String)model.get("strManagerMobile");
					String strmanagermail = (String)model.get("strManagerMail");
					String dtupdatetime = (String)model.get("dtUpdateTime");
					Integer nsaleslevel = (Integer)model.get("nSalesLevel");
					String stropenday = (String)model.get("strOpenDay");
					String strdeptmobile = (String)model.get("strDeptMobile");
					prefix.append("("+lid+",'"+strdeptcode+"','"+strdeptname+"','"+strdeptprovincecode+"','"+strdeptprovincename+"','"+strdeptcitycode+"','"+strdeptcityname+"','"+strdeptaddress+"',"+ncurrentlevel+","+bisleaf+","+bissalesdept+",'"+strdeptcode0+"','"+strdeptname0+"','"+strdeptcode1+"','"+strdeptname1+"','"+strdeptcode2+"','"+strdeptname2+"','"+strdeptcode3+"','"+strdeptname3+"','"+strdeptcode4+"','"+strdeptname4+"','"+strdeptcode5+"','"+strdeptname5+"','"+strdeptcode6+"','"+strdeptname6+"','"+strdeptcode7+"','"+strdeptname7+"','"+strdeptcode8+"','"+strdeptname8+"','"+strdeptcode9+"','"+strdeptname9+"',"+lmanagerid+",'"+strmanagername+"','"+strmanagermobile+"','"+strmanagermail+"','"+dtupdatetime+"',"+nsaleslevel+",'"+stropenday+"','"+strdeptmobile+"'),");
				}
				prefix.deleteCharAt(prefix.length()-1);
				String deptSql = prefix.toString() + suffix;
				deptMapper.batchInsertTbdepartment(deptSql);
				logger.info("============同步部门增量数据"+deptRes.getDeptList().size()+"条(第"+pageIndex+"页)============");
			}
			Integer isNext = deptRes.getIsNext();
			//有下一页
			if(isNext == 1) {
				saveOrUpdateDeptInfo(deptUpdateTime, pageIndex);
			}
		}
		else {
			if(StringUtil.isNotBlank(deptRes.getMsg())) {
				logger.info("============同步部门增量信息失败(第"+pageIndex+"页)，失败原因：" + deptRes.getMsg() + "============");
			}
			else {
				logger.info("============同步部门增量信息失败(第"+pageIndex+"页)，失败原因：连接超时============");
			}
		}
	}
	
	private void saveOrUpdateDataInfo(String dataUpdateTime, Integer pageIndex) {
		//调用权限同步接口
		B2GReqMsg_Customer_IncrementDataMission dataReq = new B2GReqMsg_Customer_IncrementDataMission();
		dataReq.setDtUpdateTime(dataUpdateTime);
		dataReq.setPageIndex(pageIndex);
		B2GResMsg_Customer_IncrementDataMission dataRes = dafyService.incrementDataMission(dataReq);
		pageIndex++;
		if(dataRes.getCode() != null && dataRes.getCode() == 0) {
			if(!CollectionUtils.isEmpty(dataRes.getDataList())) {
				StringBuffer prefix = new StringBuffer("INSERT INTO tbdatapermission(lId,lUserId,strDeptCode,nCurrentLevel,lOperateId,dtUpdateTime) VALUES");
				String suffix = " ON DUPLICATE KEY UPDATE lUserId=VALUES(lUserId),strDeptCode=VALUES(strDeptCode),nCurrentLevel=VALUES(nCurrentLevel),lOperateId=VALUES(lOperateId),dtUpdateTime=VALUES(dtUpdateTime)";
				for(HashMap<String, Object> model : dataRes.getDataList()) {
					Long lid = Long.valueOf((String)model.get("lId"));
					Long luserid = Long.valueOf((String)model.get("lUserId"));
					String strdeptcode = (String)model.get("strDeptCode");
					Integer ncurrentlevel = (Integer)model.get("nCurrentLevel");
					Long loperateid = Long.valueOf((String)model.get("lOperateId"));
					String dtupdatetime = (String)model.get("dtUpdateTime");
					prefix.append("("+lid+","+luserid+",'"+strdeptcode+"',"+ncurrentlevel+","+loperateid+",'"+dtupdatetime+"'),");
				}
				prefix.deleteCharAt(prefix.length()-1);
				String dataSql = prefix.toString() + suffix;
				dataMapper.batchInsertTbdatapermission(dataSql);
				logger.info("============同步权限增量数据"+dataRes.getDataList().size()+"条(第"+pageIndex+"页)============");
			}
			Integer isNext = dataRes.getIsNext();
			//有下一页
			if(isNext == 1) {
				saveOrUpdateDataInfo(dataUpdateTime, pageIndex);
			}
		}
		else {
			if(StringUtil.isNotBlank(dataRes.getMsg())) {
				logger.info("============同步权限增量信息失败(第"+pageIndex+"页)，失败原因：" + dataRes.getMsg() + "============");
			}
			else {
				logger.info("============同步权限增量信息失败(第"+pageIndex+"页)，失败原因：连接超时============");
			}
		}
	}
	
	private void warn(String content) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		Date start = calendar.getTime();
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		Date end = calendar.getTime();
		BsSpecialJnlExample example = new BsSpecialJnlExample();
		example.createCriteria().andDetailEqualTo("synchronizedDafyInfoError").andOpTimeGreaterThanOrEqualTo(start).andOpTimeLessThanOrEqualTo(end);
		List<BsSpecialJnl> list = bsSpecialJnlMapper.selectByExample(example);
		if(CollectionUtils.isEmpty(list)) {
			specialJnlService.warn4Fail(null, "synchronizedDafyInfoError", null, content, true);
		}
	}
}
