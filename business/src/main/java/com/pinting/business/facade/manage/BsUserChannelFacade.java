package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_BsUserChannelDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_BsUserChannelList;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_BsUserChannelModify;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserChannel_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_BsUserChannelDelete;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_BsUserChannelList;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_BsUserChannelModify;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserChannel_SelectByPrimaryKey;
import com.pinting.business.model.BsUserChannel;
import com.pinting.business.model.vo.BsUserChannelVO;
import com.pinting.business.service.manage.BsUserChannelService;
import com.pinting.business.util.BeanUtils;

@Component("BsUserChannel")
public class BsUserChannelFacade {
	
	@Autowired
	private BsUserChannelService bsUserChannelService;
	
	/**
	 * 用户优先支付渠道列表
	 * @param reqMsg
	 * @param resMsg
	 */
	public void bsUserChannelList(ReqMsg_BsUserChannel_BsUserChannelList reqMsg, ResMsg_BsUserChannel_BsUserChannelList resMsg) {
		int totalRows = bsUserChannelService.findCountUserChannel(reqMsg.getUserName(), reqMsg.getMobile());
		if (totalRows > 0) {
			ArrayList<BsUserChannelVO> bsUserChannelList = bsUserChannelService.findUserChannelList(reqMsg.getUserName(), reqMsg.getMobile(), 
					reqMsg.getPageNum(), reqMsg.getNumPerPage(), reqMsg.getOrderDirection(), reqMsg.getOrderField());
			ArrayList<HashMap<String, Object>> channelList = BeanUtils.classToArrayList(bsUserChannelList);
			resMsg.setValueList(channelList);
		}
		resMsg.setTotalRows(totalRows);
		resMsg.setPageNum(reqMsg.getPageNum());
		resMsg.setNumPerPage(reqMsg.getNumPerPage());
	}
	
	
	/**
	 * 进入添加/编辑页面
	 * @param reqMsg
	 * @param resMsg
	 */
	public void selectByPrimaryKey(ReqMsg_BsUserChannel_SelectByPrimaryKey reqMsg, ResMsg_BsUserChannel_SelectByPrimaryKey resMsg) {
		if (reqMsg.getId() != null && reqMsg.getId() != 0) {
			BsUserChannelVO bsUserChannel = (BsUserChannelVO) bsUserChannelService.bsUserChannelPrimaryKey(reqMsg.getId());
			resMsg.setId(bsUserChannel.getId());
			resMsg.setUserId(bsUserChannel.getUserId());
			resMsg.setBankChannelId(bsUserChannel.getBankChannelId());
			resMsg.setCreateTime(bsUserChannel.getCreateTime());
			resMsg.setUserName(bsUserChannel.getUserName());
		} 
		//查找19付银行对应的银行名称-渠道类型-通道优先级
		ArrayList<BsUserChannelVO> valueList = bsUserChannelService.find19payCardNameChannelPriority(new BsUserChannelVO());
		resMsg.setPayCardList(BeanUtils.classToArrayList(valueList));
	}
	
	/**
	 * 添加/编辑
	 * @param reqMsg
	 * @param resMsg
	 */
	public void bsUserChannelModify(ReqMsg_BsUserChannel_BsUserChannelModify reqMsg, ResMsg_BsUserChannel_BsUserChannelModify resMsg) {
		BsUserChannel bsUserChananel = new BsUserChannel();
			bsUserChananel.setUserId(reqMsg.getUserId());
			bsUserChananel.setBankChannelId(reqMsg.getBankChannelId());
			//查询用户优先支付渠道是否已存在
			BsUserChannel result = bsUserChannelService.selectUserChannel(bsUserChananel);
			bsUserChananel.setId(reqMsg.getId());
			if (reqMsg.getId() != null) { // (1)编辑
				if (result != null) {
					if (result.getId().equals(reqMsg.getId())) {// 1、修改的记录与用户优先支付渠道表中的记录相同，即编辑是同一条记录，更新原有的记录
						bsUserChannelService.updateUserChannel(bsUserChananel);
						resMsg.setFlag(ResMsg_BsUserChannel_BsUserChannelModify.MODIFY_SUCCESS);// a、 更新成功
					} else {
						resMsg.setFlag(ResMsg_BsUserChannel_BsUserChannelModify.REPEAT_NAME);// b、用户优先支付渠道已存在
					}
				} else {// 2、同一个用户，在用户优先支付渠道表中有多条记录
					bsUserChannelService.updateUserChannel(bsUserChananel);
					resMsg.setFlag(ResMsg_BsUserChannel_BsUserChannelModify.MODIFY_SUCCESS);
				}
			} else { // (2)添加
				if (result == null) {
					bsUserChananel.setCreateTime(new Date());
					bsUserChannelService.addUserChannel(bsUserChananel);
					resMsg.setFlag(ResMsg_BsUserChannel_BsUserChannelModify.INSERT_SUCCESS);
				} else {
					resMsg.setFlag(ResMsg_BsUserChannel_BsUserChannelModify.REPEAT_NAME);
				}
			}
			
	}
	
	/**
	 * 删除
	 * @param reqMsg
	 * @param resMsg
	 */
	public void bsUserChannelDelete(ReqMsg_BsUserChannel_BsUserChannelDelete reqMsg, ResMsg_BsUserChannel_BsUserChannelDelete resMsg) {
		if (reqMsg.getId() != null && reqMsg.getId() != 0) {
			bsUserChannelService.deleteUserChannelById(reqMsg.getId());
			resMsg.setFlag(ResMsg_BsUserChannel_BsUserChannelDelete.DELETE_SUCCESS);
		} else {
			resMsg.setFlag(ResMsg_BsUserChannel_BsUserChannelDelete.DELETE_FAIL);
		}
	}
	
	
}
