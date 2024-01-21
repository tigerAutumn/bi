package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.vo.BsInterestTicketManageVO;

/**
 * 加息券查询
 * @author SHENGP
 * @date  2018年4月4日 下午1:44:12
 */
public interface BsTicketInterestService {

	/**
	 * 加息券查询 计数
	 * @param record
	 * @return
	 */
	Integer countTicketInterestInfo(BsInterestTicketManageVO record);
	
	/**
	 * 加息券查询 列表
	 * @param record
	 * @return
	 */
	List<BsInterestTicketManageVO> selectTicketInterestInfoList(BsInterestTicketManageVO record);
	
	/**
	 * 加息券查询 加息收益求和
	 * @param record
	 * @return
	 */
	Double sumTicketInterest(BsInterestTicketManageVO record);
	
	/**
     * 
     * @Title: sendTicketMessage 
     * @Description: 加息券消息推送服务
     * @param type 1、短信2、微信3、app推送
     * @param mobiles 手机号（当微信和app推送时，传入null）
     * @param userIds 用户ID（当短信推送时，传入null）
     * @param ticketName 加息券名称
     * @param ticketApr 加息券利率
     * @throws
     */
    void sendTicketMessage(Integer type, List<String> mobiles, List<Integer> userIds, String ticketName, String ticketAPr);
}
