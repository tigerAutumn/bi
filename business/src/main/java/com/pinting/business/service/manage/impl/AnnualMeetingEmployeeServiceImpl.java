package com.pinting.business.service.manage.impl;

import java.util.Date;
import java.util.List;

import com.pinting.business.util.Constants;
import com.pinting.core.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.Bs2016AnnualMeetingEmployeeMapper;
import com.pinting.business.model.Bs2016AnnualMeetingEmployee;
import com.pinting.business.model.vo.AnnualMeetingEmpVO;
import com.pinting.business.service.manage.AnnualMeetingEmployeeService;

@Service
public class AnnualMeetingEmployeeServiceImpl implements AnnualMeetingEmployeeService {
	
	private Logger log = LoggerFactory.getLogger(BsCheckInUserServiceImpl.class);

	@Autowired
	private Bs2016AnnualMeetingEmployeeMapper annualMeetingEmployeeMapper;
	
	@Override
	public List<AnnualMeetingEmpVO> queryAnnualMeetingEmpList(
			AnnualMeetingEmpVO record) {
		return annualMeetingEmployeeMapper.findAnnualMeetingEmpList(record);
	}

	@Override
	public int queryAnnualMeetingCount(AnnualMeetingEmpVO record) {
		return annualMeetingEmployeeMapper.findAnnualMeetingCount(record);
	}

	@Override
	public void batchInsertAnnualMeetingEmp(List<String> annualMeetingEmpList) {
		if(annualMeetingEmpList != null && annualMeetingEmpList.size() > 0) {
			String sql = "insert into bs_2016_annual_meeting_employee(employee_name,company_name, is_win, create_time, update_time) values";
			StringBuffer insert = new StringBuffer();
			for(String annualMeetingEmp : annualMeetingEmpList) {
				//插入时判断手机号是否已经存在
				String[] s = annualMeetingEmp.split(",");
				String companyName = s[0];
				String employeeName = s[1];
				Bs2016AnnualMeetingEmployee result = annualMeetingEmployeeMapper.selectRecordByEmployeeName(employeeName) ;
				if(result == null) {
					insert.append("("+ "'" +employeeName+ "'" +",");
					insert.append("'"+companyName+"',");
					insert.append("'" + Constants.IS_WIN_NO + "',");
					insert.append("'" + DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss") + "',");
					insert.append("'" + DateUtil.formatDateTime(new Date(), "yyyy-MM-dd HH:mm:ss") + "'),");
				}else{
					log.info("批量导入时年会抽奖表中已存在的姓名：" + result.getEmployeeName());
				}
			}
			//导入模板中的手机号全部都在库中
			String empInfo = insert.toString();
			if(empInfo != null && empInfo.length() != 0) {
				sql += insert.toString();
				sql =  sql.substring(0, sql.length()-1);
				annualMeetingEmployeeMapper.insertAnnualMeetingEmployee(sql);
			}
		}
		
	}
	
	
}
