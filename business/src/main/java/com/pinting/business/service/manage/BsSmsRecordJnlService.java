package com.pinting.business.service.manage;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.pinting.business.enums.SmsPlatformsCodeEnum;
import com.pinting.business.model.BsSmsRecordJnl;
import com.pinting.business.model.vo.BsSmsRecordJnlReVo;

public interface BsSmsRecordJnlService {
	
	/**
	 * 短信记录分页查询
	 * @param content 短信内容
	 * @param mobile 手机号
	 * @param beginTime 发送起始时间
	 * @param overTime 发送结束时间
	 * @param pageNum 当前页
	 * @param numPerPage 每页记录数
	 * @param platformsCode 短信平台编码
	 * @return
	 */
	public List<BsSmsRecordJnlReVo> smsRecordJnlList(String content, String mobile,
			Date beginTime, Date overTime, String type, Integer statusCode,
			int pageNum ,int numPerPage, String platformsCode);
	
	/**
	 * 条数统计
	 * @param content 短信内容
	 * @param mobile 手机号
	 * @param beginTime 发送起始时间
	 * @param overTime 发送结束时间
	 * @param platformsCode 短信平台编码
	 * @return
	 */
	public  int smsRecordJnlCount(String content, String mobile,
			Date beginTime, Date overTime, String type, 
			Integer statusCode, String platformsCode);
	
	
	/**
	 * 根据手机号和短信平台序列号修改
	 * @param mobile
	 * @param serialNo
	 * @param statusCode
	 * @param statusMsg
	 */
	public void updateJnl(BsSmsRecordJnl jnl);
	
	/**
	 * 新增
	 * @param jnl
	 */
	public void insertJnl(BsSmsRecordJnl jnl);
	
	/**
	 * 根据手机号和序列号查询
	 * @param mobile
	 * @param serNo
	 * @param platformsId
	 * @return smsEnum
	 */
	public BsSmsRecordJnl selectByMobileSerNo(String mobile, String serNo, SmsPlatformsCodeEnum smsEnum);
	
}
