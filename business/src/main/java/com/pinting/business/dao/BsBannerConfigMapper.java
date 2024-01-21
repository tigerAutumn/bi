package com.pinting.business.dao;

import com.pinting.business.model.BsBannerConfig;
import com.pinting.business.model.BsBannerConfigExample;
import com.pinting.business.model.vo.BsBannerConfigVO;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsBannerConfigMapper {
    int countByExample(BsBannerConfigExample example);

    int deleteByExample(BsBannerConfigExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsBannerConfig record);

    int insertSelective(BsBannerConfig record);

    List<BsBannerConfig> selectByExample(BsBannerConfigExample example);

    BsBannerConfig selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsBannerConfig record, @Param("example") BsBannerConfigExample example);

    int updateByExample(@Param("record") BsBannerConfig record, @Param("example") BsBannerConfigExample example);

    int updateByPrimaryKeySelective(BsBannerConfig record);

	int updateByPrimaryKey(BsBannerConfig record);

	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	BsBannerConfigVO selectById(Integer id);

	/**
	 * 根据传入序号修改>=该序号的banner顺序
	 * 用于修改为不显示，或原顺序为不显示的情况
	 * @param priority
	 * @return
	 */
	int updateBannersByPri(@Param("priority") Integer priority, @Param("addNum") Integer addNum, 
			@Param("channel")String channel);

	/**
	 * 当原序号oldlPri<现传入序号newPri时，修改序号为x的值+1，x满足：oldlPri <= x <= newPri
	 *
	 * 当原序号oldlPri>现传入序号newPri时，修改序号为x的值-1， x满足：newPri <= x <= oldlPri
	 * @param small
	 * @param big
	 * @param addNum
	 * @param channel
	 * @return
	 */
	int updateBannersByPris(@Param("small") Integer small, @Param("big") Integer big,
			@Param("addNum") Integer addNum, @Param("channel")String channel);
	
	/**
	 * 根据传入序号修改>=该序号的banner顺序
	 * 用于修改为不显示，或原顺序为不显示的情况
	 * @param priority
	 * @param showPage
	 * @return
	 */
	int updateBannersByShowPage(@Param("priority") Integer priority, @Param("addNum") Integer addNum, 
			@Param("channel")String channel, @Param("showPage")String showPage);

	/**
	 * 当原序号oldlPri<现传入序号newPri时，修改序号为x的值+1，x满足：oldlPri <= x <= newPri
	 *
	 * 当原序号oldlPri>现传入序号newPri时，修改序号为x的值-1， x满足：newPri <= x <= oldlPri
	 * @param small
	 * @param big
	 * @param addNum
	 * @param channel
	 * @param showPage
	 * @return
	 */
	int updateBannersByShowPages(@Param("small") Integer small, @Param("big") Integer big,
			@Param("addNum") Integer addNum, @Param("channel")String channel, @Param("showPage")String showPage);
	

	/**
	 * 获取list
	 * @param channel
	 * @param subject
	 * @param status
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	List<BsBannerConfigVO> getListByBannerConfig(
			@Param("channel") String channel,
			@Param("subject") String subject,
			@Param("status") String status,
			@Param("pageNum") Integer pageNum,
			@Param("numPerPage") Integer numPerPage);

	/**
	 * 获取list条数
	 * @param channel
	 * @param subject
	 * @param status
	 * @return
	 */
	Integer countByBannerConfig(
			@Param("channel") String channel,
			@Param("subject") String subject,
			@Param("status") String status);

	/**
	 * 根据url channel查询banner信息
	 * @param url
	 * @param channel
     * @return
     */
	BsBannerConfig selectBannerInfo(@Param("url") String url, @Param("channel") String channel);
	
	/**
	 * 根据url 查询banner条数
	 * @param url
     * @return
     */
	int selectBannerCount(@Param("url") String url);
	
	/**
	 * 查询banner信息
	 * @param showPage 展示位置
	 * @param channel 渠道
	 * @param status 状态
	 * @param currId 当前操作banner
     * @return
     */
	List<BsBannerConfig> selectBannerConfigInfo(@Param("showPage") String showPage, @Param("channel") String channel, 
			@Param("status") String status, @Param("currBannerId") Integer currBannerId);
	
	/**
	 * 更新banner信息
	 * @param showPage 展示位置
	 * @param channel 渠道
	 * @param status 状态
	 * @param currId 当前操作banner
	 * @return
	 */
	int updateBannerConfigStatus(@Param("showPage") String showPage, @Param("channel") String channel, @Param("opStatus") String opStatus,
			@Param("preStatus") String preStatus, @Param("currBannerId") Integer currBannerId);
	
	
	/**
	 * 获取picture的list条数
	 * @param channel
	 * @param subject
	 * @param status
	 * @return
	 */
	Integer countPictureByBannerConfig(@Param("showPages") List<String> showPages, @Param("channel") String channel, @Param("subject") String subject,
			@Param("status") String status);
	
	/**
	 * 获取网站图片list
	 * @param channel
	 * @param subject
	 * @param status
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	List<BsBannerConfigVO>  getPictureListByBannerConfig(@Param("showPages") List<String> showPages, @Param("channel") String channel, @Param("subject") String subject,
			@Param("status") String status, @Param("pageNum") Integer pageNum, @Param("numPerPage") Integer numPerPage);
	
}