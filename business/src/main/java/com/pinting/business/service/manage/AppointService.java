package com.pinting.business.service.manage;

import java.util.ArrayList;

import com.pinting.business.model.FdAppoint;
import com.pinting.business.model.vo.FdAppointVO;

public interface AppointService {

	/**
	 * 分页查询基金预约
	 * @param fdAppointVO
	 * @return 返回基金预约列表
	 */
	public ArrayList<FdAppointVO> findMFdAppointInfoList(FdAppoint fdAppoint);

	/**
	 * 查询一共有多少条基金预约内容
	 * @return
	 */
	public int countAppointList();

	/**
	 * 根据id号查询appoint
	 * @param id
	 * @return 放回基金预约内容
	 */
	public FdAppoint findFdAppointById(int id);

	/**
	 * 更具id号更新appoint
	 * @param appoint
	 * @return 更新成功返回true,否则返回false
	 */
	public boolean updateAppointById(FdAppoint appoint);

}
