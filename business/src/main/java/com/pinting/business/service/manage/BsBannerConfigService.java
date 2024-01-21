package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsBannerConfig;
import com.pinting.business.model.vo.BsBannerConfigVO;

public interface BsBannerConfigService {
	
	/**
	 * banner列表条数查询
	 * @param channel
	 * @param subject
	 * @param status
	 * @return
	 */
	public int countByBanner(String channel, String subject,
			String status);
	
	/**
	 * banner-picture列表条数查询
	 * @param channel
	 * @param subject
	 * @param status
	 * @return
	 */
	public int countPictureByBanner(List<String> showPage, String channel, String subject, String status);
	
	/**
	 * banner列表查询
	 * @param channel
	 * @param subject
	 * @param status
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public List<BsBannerConfigVO> getList(String channel, String subject,
			String status, Integer pageNum, Integer numPerPage);
	
	
	/**
	 * 网站图片列表查询
	 * @param channel
	 * @param subject
	 * @param status
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public List<BsBannerConfigVO> getPictureList(List<String> showPage, String channel, String subject,
			String status, Integer pageNum, Integer numPerPage);
	
	/**
	 * 根据channel查询总的显示中的条数
	 * @param channel
	 * @return
	 */
	public int countByChannel(String channel);
	
	/**
	 * 根据channel查询总的显示中的条数
	 * @param channel
	 * @return
	 */
	public int countByChannelAndShowPage(String channel, String showPage);
	
	/**
	 * 根据传入序号修改大于等于该序号的banner顺序
	 * @param priority
	 * @param addNum
	 */
	public void updateBannersByPri(Integer priority, Integer addNum, String channel);
	
	/**
	 * 当原序号oldlPri<现传入序号newPri时，修改序号为x的值+1，x满足：oldlPri <= x <= newPri
	 * 
	 * 当原序号oldlPri>现传入序号newPri时，修改序号为x的值-1， x满足：newPri <= x <= oldlPri
	 * 
	 * @param smallPri
	 * @param bigPri
	 * @param addNum
	 * @param channel
	 */
	public void updateBannersByPris(Integer smallPri, Integer bigPri,Integer addNum, String channel);
	
	/**
	 * 根据传入序号修改大于等于该序号的banner顺序
	 * @param priority
	 * @param addNum
	 */
	public void updateBannersByShowPage(Integer priority, Integer addNum, String channel, String showPage);
	
	/**
	 * 当原序号oldlPri<现传入序号newPri时，修改序号为x的值+1，x满足：oldlPri <= x <= newPri
	 * 
	 * 当原序号oldlPri>现传入序号newPri时，修改序号为x的值-1， x满足：newPri <= x <= oldlPri
	 * 
	 * @param smallPri
	 * @param bigPri
	 * @param addNum
	 * @param channel
	 */
	public void updateBannersByShowPages(Integer smallPri, Integer bigPri,Integer addNum, String channel, String showPage);
	
	/**
	 * 修改
	 * @param bannerConfig
	 */
	public void updateBannerConfig(BsBannerConfig bannerConfig);
	
	/**
	 * 新增
	 * @param bannerConfig
	 */
	public void addBannerConfig(BsBannerConfig bannerConfig);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsBannerConfigVO selectById(Integer id);

	/**
	 * 根据展示渠道删除记录
	 * @return
     */
	public int deleteBannerByChannel();

	/**
	 * 根据还款方式查询banner信息
	 */
	public BsBannerConfig selectBannerConfigByType(String returnType, String channel);
	
	/**
	 * 根据url channel查询banner信息
	 * @param url
	 * @return
     */
	public BsBannerConfig queryBannerInfo(String url, String channel);
	
	/**
	 * 根据url 查询banner条数
	 * @param url
	 * @return
     */
	public int queryBannerCount(String url);

	/**
	 * 查询指定端口指定页面的banner
	 * @param channel	渠道（MICRO、MICRORUIAN、MICROHN、MICROKQ、MICRO178、APP、GEN、GEN178、GENKQ、GENHN、GENRUIAN）
	 * @param showPage	LOGIN FORGETPWD REGISTER ESTUARYPLAN ZANSTAGES DEPSUBJECT INDEX
     * @return
     */
	List<BsBannerConfig> queryBannerByShowPage(String channel, String showPage);
	
}
