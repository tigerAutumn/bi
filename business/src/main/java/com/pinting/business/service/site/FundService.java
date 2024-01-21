package com.pinting.business.service.site;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pinting.business.model.FdAppoint;
import com.pinting.business.model.FdInfo;
import com.pinting.business.model.FdNet;

public interface FundService {

	/**
	 * 增加基金预约
	 * @param appoint
	 * @return 增加成功返回true，否则返回false
	 */
	public boolean addFund(FdAppoint appoint);

	/**
	 * 计算基金列表的长度
	 * @return 基金列表的长度值
	 */
	public int countNetValueList();

	/**
	 *  带分页查询基金净值列表  每次查询最多就只有pageSize条内容
	 * @param start 开始数
	 * @param pageSize 每页页数的个数
	 * @return 带分页的基金净值表, 如果没有数据,返回null
	 */
	public List<FdInfo> findFdInfoListByPage(int start,int pageSize);

	/**
	 * 根据基金Id查询净值
	 * @param fundId
	 * @return 返回基金净值列表, 没有找到，返回null
	 */
	public List<FdNet> findFdNetByFundId(int fundId);

}
