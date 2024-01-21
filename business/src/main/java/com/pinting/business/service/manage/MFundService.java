package com.pinting.business.service.manage;

import java.util.Date;
import java.util.List;

import com.pinting.business.model.FdInfo;
import com.pinting.business.model.FdNet;
import com.pinting.business.model.vo.FdNetVO;

public interface MFundService {

	/**
	 * 管理台，基金分页
	 * @param fdInfo
	 * @return
	 */
	public List<FdInfo> findMFdInfoList(FdInfo fdInfo);

	/**
	 * 管理台，基金净值分页
	 * @param fdNet
	 * @return
	 */
	public List<FdNetVO> findMFdNetInfoList(FdNetVO fdNet);

	/**
	 * 根据基金表中的id号进行查询
	 * @param id 基金id号
	 * @return 返回基金表中的数据，没有找到，返回null
	 */
	public FdInfo findFdInfoById(int id);

	/**
	 * 根据基金净值表中的id号进行查询
	 * @param id 基金id号
	 * @return 返回基金表中的数据，没有找到，返回null
	 */
	public FdNet findFdNetInfoById(int id);
	/**
	 * 根据基金净值表中的fundid号和日期进行查询
	 * @param fundid
	 * @param date yyyyMMdd
	 * @return 返回基金表中的数据，没有找到，返回null
	 */
	public FdNet findFdNetInfoByfundIdAndDate(int fundid,Date date);
	
	/**
	 * 插入基金信息
	 * @param fdInfo
	 * @return 返回基金信息数据，插入成功返回true，否则返回false
	 */
	public boolean insertFdInfo(FdInfo fdInfo);
	/**
	 * 插入基金净值信息
	 * @param fdNet
	 * @return 返回基金净值信息数据，插入成功返回true，否则返回false
	 */
	public boolean insertNetInfo(FdNet fdNet);

	/**
	 * 根据id号修改单条的基金信息
	 * @param fdInfo
	 * @return 更新成功返回true，否则返回false
	 */
	public boolean modifyFdInfoById(FdInfo fdInfo);
	/**
	 * 根据id号修改单条的基金净值信息
	 * @param fdNet
	 * @return 更新成功返回true，否则返回false
	 */
	public boolean modifyNetInfoById(FdNet fdNet);

	/**
	 * 根据id号进行删除操作
	 * @param idList
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean DeleteFdInfoByIds(List<Integer> idList,int status);
	/**
	 * 根据id号进行删除操作
	 * @param id
	 * @return 删除成功返回true，否则返回false
	 */
	public boolean DeleteNetInfoById(Integer id);

	/**
	 * 计算基金列表的长度
	 * @return 基金列表的长度值
	 */
	public int countNetValueList();
	
	/**
	 * 计算基金净值列表的长度
	 * @return 基金净值的长度值
	 */
	public int countNetList();
	
}
