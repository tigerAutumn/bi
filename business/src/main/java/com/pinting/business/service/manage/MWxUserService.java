package com.pinting.business.service.manage;

import java.util.List;
import java.util.Map;

import com.pinting.business.model.MWxMuserInfo;
import com.pinting.business.model.vo.MUpdateWxUserInfoResVO;
import com.pinting.business.model.vo.SysOperationalDataVO;

public interface MWxUserService {
	/**
	 * 统计关注用户数
	 * @param map
	 * @return map
	 */
	public Map<String,Object> countWxUserNum(Map<String,Object> map);
	
	/**
	 * 运营数据微信用户管理计数
	 * @param req
	 * @return
	 */
	Integer countOperationalDataInfo(String nickName);
	
	/**
     * 查询运营数据微信用户管理列表
     *
     * @param SysOperationalDataVO 查询条件
     * @return
     */
    List<SysOperationalDataVO> selectOperationalDataInfoList(String nickName, Integer page,
			Integer offset);
    
    /**
     * 运营数据微信用户管理，删除用户（更新状态）
     * @return
     */
    Boolean updateStatus(String status, Integer mId, Integer opUserId);
    
    /**
     * 根据主键找对象
     * @return
     */
    MWxMuserInfo findById(Integer id);
    
    /**
     * 运营数据微信用户管理，编辑操作
     * @param req
     */
    MUpdateWxUserInfoResVO updateWxUserInfo(SysOperationalDataVO req);
    
}
