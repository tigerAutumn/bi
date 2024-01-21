package com.pinting.business.facade.manage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.util.Date;
import java.util.List;

import com.pinting.business.hessian.manage.message.*;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.dao.BsWxInfoMapper;
import com.pinting.business.dao.MWxMuserInfoMapper;
import com.pinting.business.enums.WxMuserStatusEnum;
import com.pinting.business.model.BsWxInfo;
import com.pinting.business.model.BsWxInfoExample;
import com.pinting.business.model.MWxMuserInfo;
import com.pinting.business.model.MWxMuserInfoExample;
import com.pinting.business.service.site.WxUserService;
import com.pinting.business.util.WeChatUtil;
import com.pinting.core.util.StringUtil;

import net.sf.json.JSONObject;

@Component("OperationalWxUser")
/**
 * 微信用户管理
 * @Project: business
 * @Title: wxUserFacade.java
 * @author Linkin
 * @date 2015-04-02 上午10:39:39
 * @Copyright: 2015 bigangwan.com Inc. All rights reserved.
 */
public class OperationalWxUserFacade {
	private static final Logger log = LoggerFactory.getLogger(OperationalWxUserFacade.class);
	@Autowired
	private WxUserService wxUserService;
	@Autowired
	private BsWxInfoMapper bsWxInfoMapper;
	@Autowired
	private MWxMuserInfoMapper mWxMuserInfoMapper;
	

	/*public void isExist(ReqMsg_OperationalWxUser_isExist req, ResMsg_OperationalWxUser_isExist res) {
		int wxUserId = 0;
		if (req.getOpenId() != null && !"".equals(req.getOpenId())) {
			wxUserId = wxUserService.isWxUserExist(req.getOpenId());
		}

		res.setWxUserId(wxUserId);
	}

	public void regist(ReqMsg_OperationalWxUser_Regist req, ResMsg_OperationalWxUser_Regist res) {
		int wxUserId = wxUserService.isWxUserExist(req.getOpenId());
		if (wxUserId == 0) {
			BsWxUserInfo wxUserInfo = new BsWxUserInfo();
			if(StringUtil.isNotEmpty(req.getUserId())){
				wxUserInfo.setUserId(Integer.parseInt(req.getUserId()));	
			}
			wxUserInfo.setOpenId(req.getOpenId());
			wxUserInfo.setCreateTime(req.getCreateTime());
			res.setBsActivityUser(wxUserService.registerWxUser(wxUserInfo));
			
		} else {
			res.setBsActivityUser(wxUserId);
		}
	}*/
	
	//关注用户
	public void subscribe(ReqMsg_OperationalWxUser_Subscribe req, ResMsg_OperationalWxUser_Subscribe res) {
		String openId = req.getOpenId();
		if(StringUtil.isNotEmpty(openId)){
			// 关注MWxMuserInfo表初始化
			log.info("subscribe openId:{}", openId);
			MWxMuserInfoExample mWxMuserInfoExample = new MWxMuserInfoExample();
			mWxMuserInfoExample.createCriteria().andOpenIdEqualTo(openId).andStatusEqualTo(WxMuserStatusEnum.WX_MUSER_STATUS_OPEN.getCode());
			List<MWxMuserInfo> mInfoList = mWxMuserInfoMapper.selectByExample(mWxMuserInfoExample);
			if (CollectionUtils.isEmpty(mInfoList)) {	
				MWxMuserInfo mInfo = new MWxMuserInfo();
				mInfo.setOpenId(openId);
				mInfo.setCreateTime(new Date());
				mInfo.setUpdateTime(new Date());
				mInfo.setStatus("OPEN");
				mWxMuserInfoMapper.insertSelective(mInfo);
			}
						
			BsWxInfoExample example = new BsWxInfoExample();
			example.createCriteria().andOpenIdEqualTo(openId);
			List<BsWxInfo> list = bsWxInfoMapper.selectByExample(example);
			if(list != null && list.size() > 0) {
				BsWxInfo wxUser = list.get(0);
				if(!"FOLLOW".equals(wxUser.getIsFollow())) {
					wxUser.setIsFollow("FOLLOW");
					wxUser.setFollowTime(new Date());
					bsWxInfoMapper.updateByPrimaryKeySelective(wxUser);
				}

			}else {
				//开始拉取用户信息
        		String getUserMessageUrl = "https://api.weixin.qq.com/cgi-bin/user/info";
        		StringBuffer sb1 = new StringBuffer();
        		sb1.append("access_token=" + req.getAccessToken());
        		sb1.append("&openid=" + openId);
        		sb1.append("&lang=zh_CN");
        		String userMessage = "";
        		try {
        			userMessage = WeChatUtil.sendPost(getUserMessageUrl, sb1.toString(), false,
        					"UTF-8");
        		} catch (IOException e) {
        			e.printStackTrace();
        		}
        		if(StringUtil.isNotEmpty(userMessage)) {
        			try {
        				JSONObject userMessageJson = JSONObject.fromObject(userMessage);
        				log.info("subscribe userMessageJson:{}", userMessageJson.toString());
            			BsWxInfo info = new BsWxInfo();
        				info.setOpenId(openId);
        				try {
    						info.setNick(filterOffUtf8Mb4(userMessageJson.getString("nickname")));
    					} catch (UnsupportedEncodingException e) {
    						e.printStackTrace();
    					}
        				info.setSex(String.valueOf(userMessageJson.getInt("sex")));
        				info.setProvince(userMessageJson.getString("province"));
        				info.setCity(userMessageJson.getString("city"));
        				info.setCountry(userMessageJson.getString("country"));
        				info.setHeadImgUrl(userMessageJson.getString("headimgurl"));
        				info.setCreateTime(new Date());
        				info.setFollowTime(new Date());
        				info.setIsFollow("FOLLOW");
        				bsWxInfoMapper.insertSelective(info);
        				
					} catch (Exception e) {
						e.printStackTrace();
					}
        		}
			}
		}
	}
	
	//取消关注用户
	public void unSubscribe(ReqMsg_OperationalWxUser_UnSubscribe req, ResMsg_OperationalWxUser_UnSubscribe res) {
		String openId = req.getOpenId();
		if(StringUtil.isNotEmpty(openId)){
			BsWxInfoExample example = new BsWxInfoExample();
			example.createCriteria().andOpenIdEqualTo(openId);
			List<BsWxInfo> list = bsWxInfoMapper.selectByExample(example);
			if(list != null && list.size() > 0) {
				BsWxInfo wxUser = list.get(0);
				if(!"UNFOLLOW".equals(wxUser.getIsFollow())) {
					wxUser.setCancelTime(new Date());
					wxUser.setIsFollow("UNFOLLOW");
					bsWxInfoMapper.updateByPrimaryKeySelective(wxUser);
				}
			}
		}
	}
	
	/*public void addInfo(ReqMsg_WxUser_AddInfo req,ResMsg_WxUser_AddInfo resp){
		//查询并删除openId相同，userId不同的数据
		wxUserService.deleteByOpenId(req.getOpenId(), Integer.parseInt(req.getUserId()));
		
		//该用户已有对应的openId，则修改
		BsWxUserInfo wxUser = wxUserService.getWxUserByUserId(Integer.parseInt(req.getUserId()));
		if (wxUser == null) {
			BsWxUserInfo info = new BsWxUserInfo();
			if(StringUtil.isNotEmpty(req.getUserId())){
				info.setUserId(Integer.parseInt(req.getUserId()));	
			}
			info.setOpenId(req.getOpenId());
			info.setCreateTime(new Date());
			wxUserService.registerWxUser(info);
		} else if(!req.getOpenId().equals(wxUser.getOpenId())){
			BsWxUserInfo info = new BsWxUserInfo();
			info.setId(wxUser.getId());
			info.setUserId(wxUser.getUserId());
			info.setOpenId(req.getOpenId());
			wxUserService.modifyWxUserInfo(info);
		}
	}
	*/
	
	/**
	 * 解绑用户OPENID
	 * @param req
	 * @param res
	 *//*
	public void unbindOpenId(ReqMsg_WxUser_UnbindOpenId req, ResMsg_WxUser_UnbindOpenId res) {
	    wxUserService.unbindWxOpenId(req.getUserId());
	}*/
	
	//过滤掉非UTF-8字符方法
	private String filterOffUtf8Mb4(String text) throws UnsupportedEncodingException {  
	    byte[] bytes = text.getBytes("UTF-8");  
	    ByteBuffer buffer = ByteBuffer.allocate(bytes.length);  
	    int i = 0;  
	    while (i < bytes.length) {  
	        short b = bytes[i];  
	        if (b > 0) {  
	            buffer.put(bytes[i++]);  
	            continue;  
	        }  
	        b += 256;  
	        if ((b ^ 0xC0) >> 4 == 0) {  
	            buffer.put(bytes, i, 2);  
	            i += 2;  
	        }  
	        else if ((b ^ 0xE0) >> 4 == 0) {  
	            buffer.put(bytes, i, 3);  
	            i += 3;  
	        }  
	        else if ((b ^ 0xF0) >> 4 == 0) {  
	            i += 4;  
	        }else if (((b >> 2) ^ 0x3E) == 0) { 
                i += 5; 
            } else if (((b >> 1) ^ 0x7E) == 0) { 
                i += 6; 
            } 
	        //添加处理如b的指为-48等情况出现的死循环 
            else 
            { 
                buffer.put(bytes[i++]); 
                continue; 
            } 
	    }  
	    buffer.flip();  
	    return new String(buffer.array(), "utf-8"); 
	}  
}
