package com.pinting.business.service.manage;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pinting.business.model.BsTag;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserTags;
import com.pinting.business.model.vo.BsTagVO;

public interface BsTagService {
	
	/**
	 * 用户标签列表
	 * @param content
	 * @param pageNum
	 * @param numPerPage
	 * @return
	 */
	public ArrayList<BsTagVO> findTaglList(String content, int pageNum, int numPerPage, String orderDirection, String orderField);
	
	/**
	 * 用户标签统计
	 * @param content
	 * @return
	 */
	public int findCountTag(String content);
	
	/**
	 * 根据id修改信息
	 * @param record
	 * @return
	 */
	public int updatUserTag(BsTag record);
	
	/**
	 * 添加
	 * @param record
	 * @return
	 */
	public int addUserTag(BsTag record);
	
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	public BsTag bsTagPrimaryKey(Integer id);
	
	/**
	 * 根据id删除
	 * @param id
	 * @return
	 */
	public int deleteUserTagById(Integer id);
	
	/**
	 * 查询标签内容是否已存在
	 * @param record
	 * @return
	 */
	public BsTag selectUserTag(BsTag record);
	
	/**
	 * 查询 bs_tag表中是否有数据
	 * @return
	 */
	public int findCountOfTag();
	
	/**
	 * 查找 serial_id的最大值
	 * @param bsTag
	 * @return
	 */
	public BsTag findMaxSerialId(BsTag bsTag);
	
	/**
	 * 查找所有标签根据serial_id排序
	 * @return
	 */
	public List<BsTag> findAllTagList();
	
	/**
	 * 标签总数
	 * @return
	 */
	public int findTagsTotal();
	
	/**
	 * 
	 * @Title: findUserSelectTag 
	 * @Description: 查询所有的标签,并标注出用户的已有的标签
	 * @return
	 * @throws
	 */
	public List<Map<String,Object>> findUserSelectTag(Integer userId);
	
	/**
	 * 
	 * @Title: updateUserTag 
	 * @Description: 修改用户标签
	 * @param userId 用户ID
	 * @param tagList 用户选中的标签
	 * @throws
	 */
	public void updateUserTag(Integer userId, List<Integer> tagIdList, Integer creator);
	
	/**
	 * 
	 * @Title: batchInsertUserTag 
	 * @Description: 批量插入用户标签
	 * @param userId
	 * @param tagId
	 * @throws
	 */
	public void batchInsertUserTag(List<Integer> userIdList, List<Integer> tagIdList, Integer creator);
	
	/**
	 * 
	 * @Title: findUserTagsByUserId 
	 * @Description: 根据userId查询对于的标签
	 * @param list
	 * @return
	 * @throws
	 */
	public List<BsUserTags> findUserTagsByUserId(List<BsUser> list);
	
	/**
	 * 
	 * @Title: batchUpdateUserTag 
	 * @Description: 批量更新用户标签
	 * @throws
	 */
	public void batchUpdateUserTag(Map<Integer,List<Integer>> userTagMap, List<Integer> tagIdList, Integer creator);
	
}
