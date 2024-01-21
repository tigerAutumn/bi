package com.pinting.mall.service.site;

import java.util.List;

import com.pinting.mall.hessian.site.message.ReqMsg_MallPoints_PointsRecord;
import com.pinting.mall.model.MallAccount;
import com.pinting.mall.model.MallAccountJnl;
import com.pinting.mall.model.common.PagerModelRspDTO;
import com.pinting.mall.model.common.PagerReqDTO;
import com.pinting.mall.model.vo.MallAccountJnlVO;

/**
 * 积分商城用户积分账户相关
 * @project mall
 * @author bianyatian
 * @2018-5-15 下午2:09:33
 */
public interface MallAccountService {

	/**
	 * 根据用户id查询用户账户积分信息
	 * @author bianyatian
	 * @param userId
	 * @return
	 */
	public MallAccount getAccountByUserId(Integer userId);
	
	/**
	 * 查询用户积分记录-分页查询
	 * @author bianyatian
	 * @param req
	 * @return
	 */
	public List<MallAccountJnl> getPointsRecordList(ReqMsg_MallPoints_PointsRecord req);
	
	/**
	 * 查询用户积分记录-总条数
	 * @author bianyatian
	 * @param req
	 * @return
	 */
	public Integer countPointsRecordList(ReqMsg_MallPoints_PointsRecord req);

	/**
	 * 积分记录-列表查询
	 * @param record
	 * @return
     */
	public List<MallAccountJnlVO> queryMallUserPointsList(MallAccountJnlVO record);

	/**
	 * 积分记录-记录数查询
	 * @param record
	 * @return
     */
	public Integer queryMallUserPointsCount(MallAccountJnlVO record);

	/**
	 * 积分记录-兑换的积分总和查询
	 * @param record
	 * @return
     */
	public Double queryMallUserPointsSum(MallAccountJnlVO record);

	/**
	 * 积分记录列表查询-PageHelper分页
	 * @param record
	 * @return
	 */
	public PagerModelRspDTO<MallAccountJnlVO> queryMallUserPointsList(MallAccountJnlVO record, PagerReqDTO pagerReq);

}
