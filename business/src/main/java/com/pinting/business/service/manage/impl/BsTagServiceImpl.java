package com.pinting.business.service.manage.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsTagMapper;
import com.pinting.business.dao.BsUserTagsMapper;
import com.pinting.business.model.BsTag;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserTags;
import com.pinting.business.model.BsUserTagsExample;
import com.pinting.business.model.vo.BsTagVO;
import com.pinting.business.service.manage.BsTagService;
import com.pinting.business.util.BeanUtils;

@Service
public class BsTagServiceImpl implements BsTagService {
	
	@Autowired
	private BsTagMapper bsTagMapper;
	
	@Autowired
	private BsUserTagsMapper bsUserTagMapper;

	@Override
	public ArrayList<BsTagVO> findTaglList(String content, int pageNum,
			int numPerPage, String orderDirection, String orderField) {
		BsTagVO bsTagVO = new BsTagVO();
		if (null != content && !"".equals(content)) {
			bsTagVO.setContent(content);
		}
		bsTagVO.setPageNum(pageNum);
		bsTagVO.setNumPerPage(numPerPage);
		if(orderDirection != null && (!"".equals(orderDirection)) && orderField != null && (!"".equals(orderField))) {
			bsTagVO.setOrderDirection(orderDirection);
			bsTagVO.setOrderField(orderField);
		}
		return bsTagMapper.selectBsTagInfo(bsTagVO);
	}

	@Override
	public int findCountTag(String content) {
		BsTagVO bsTagVO = new BsTagVO();
		if (null != content && !"".equals(content)) {
			bsTagVO.setContent(content);
		}
		return bsTagMapper.selectCountBsTag(bsTagVO);
	}

	@Override
	public int updatUserTag(BsTag record) {
		return bsTagMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int addUserTag(BsTag record) {
		return bsTagMapper.insert(record);
	}

	@Override
	public BsTag bsTagPrimaryKey(Integer id) {
		return bsTagMapper.selectByPrimaryKey(id);
	}

	@Override
	public int deleteUserTagById(Integer id) {
		return bsTagMapper.deleteByPrimaryKey(id);
	}

	@Override
	public BsTag selectUserTag(BsTag record) {
		return bsTagMapper.selectBsTag(record);
	}

	@Override
	public int findCountOfTag() {
		return bsTagMapper.selectCountOfTag();
	}

	@Override
	public BsTag findMaxSerialId(BsTag bsTag) {
		return bsTagMapper.selectMaxSerialId(bsTag);
	}

	@Override
	public List<BsTag> findAllTagList() {
		return bsTagMapper.selectAllTagList();
	}

	@Override
	public int findTagsTotal() {
		return bsTagMapper.countByExample(null);
	}

	@Override
	public List<Map<String, Object>> findUserSelectTag(Integer userId) {
		List<BsTag> list = bsTagMapper.selectAllTagList();
		BsUserTagsExample example = new BsUserTagsExample();
		example.createCriteria().andUserIdEqualTo(userId);
		List<BsUserTags> userTagList = bsUserTagMapper.selectByExample(example);
		Map<Integer,BsUserTags> temp = new HashMap<Integer,BsUserTags>();
		for(BsUserTags userTag : userTagList) {
			temp.put(userTag.getTagId(), userTag);
		}
		List<Map<String,Object>> result = new ArrayList<Map<String,Object>>();
		for(BsTag tag : list) {
			Map<String,Object> map = BeanUtils.transBeanMap(tag);
			if(temp.containsKey(tag.getId())) {
				map.put("isSelected", "yes");
			}
			else {
				map.put("isSelected", "no");
			}
			result.add(map);
		}
		return result;
	}

	@Override
	public void updateUserTag(Integer userId, List<Integer> tagIdList, Integer creator) {
		//删除用户的所有标签
		BsUserTagsExample example = new BsUserTagsExample();
		example.createCriteria().andUserIdEqualTo(userId);
		bsUserTagMapper.deleteByExample(example);
		
		//新增用户标签
		if(!CollectionUtils.isEmpty(tagIdList)) {
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(new Date());
			String sql = "insert into bs_user_tags(user_id,tag_id,creator,create_time) values";
			StringBuffer insert = new StringBuffer();
			for(int i=0;i<tagIdList.size();i++) {
				insert.append("("+userId+",");
				insert.append(tagIdList.get(i)+",");
				insert.append(creator+",");
				insert.append("'"+time+"'),");
			}
			sql += insert.toString();
			sql = sql.substring(0, sql.length()-1);
			bsUserTagMapper.insertUserTag(sql);
		}
		
	}

	@Override
	public void batchInsertUserTag(List<Integer> userIdList, List<Integer> tagIdList, Integer creator) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String time = format.format(new Date());
		String sql = "insert into bs_user_tags(user_id,tag_id,creator,create_time) values";
		StringBuffer insert = new StringBuffer();
		for(Integer userId : userIdList) {
			for(Integer tagId : tagIdList) {
				insert.append("("+userId+",");
				insert.append(tagId+",");
				insert.append(creator+",");
				insert.append("'"+time+"'),");
			}
		}
		sql += insert.toString();
		sql = sql.substring(0, sql.length()-1);
		bsUserTagMapper.insertUserTag(sql);
	}

	@Override
	public List<BsUserTags> findUserTagsByUserId(List<BsUser> list) {
		List<Integer> userIds = new ArrayList<Integer>();
		for(BsUser user : list) {
			userIds.add(user.getId());
		}
		List<BsUserTags> result = bsUserTagMapper.findUserTagsByUserId(userIds);
		return result;
	}

	@Override
	public void batchUpdateUserTag(Map<Integer,List<Integer>> userTagMap, List<Integer> tagIdList, Integer creator) {
		if(!CollectionUtils.isEmpty(tagIdList)) {
			//删除用户已经拥有的标签
			List<BsUserTags> sameTagList = new ArrayList<BsUserTags>();
			List<Integer> userIdList = new ArrayList<Integer>();
			for(Map.Entry<Integer, List<Integer>> entry : userTagMap.entrySet()) {
				userIdList.add(entry.getKey());
				List<Integer> oldTagList = entry.getValue();
				if(!CollectionUtils.isEmpty(oldTagList)) {
					for(Integer newTagId : tagIdList) {
						for(Integer oldTagId : oldTagList) {
							if(newTagId.equals(oldTagId)) {
								BsUserTags userTag = new BsUserTags();
								userTag.setUserId(entry.getKey());
								userTag.setTagId(oldTagId);
								sameTagList.add(userTag);
								break;
							}
						}
					}
				}
			}
			
			if(!CollectionUtils.isEmpty(sameTagList)) {
				int temp = 500;
				int length = sameTagList.size()%temp==0?sameTagList.size()/temp:sameTagList.size()/temp+1;
				for(int i=0;i<length;i++) {
					List<BsUserTags> deleteList = new ArrayList<BsUserTags>();
					for(int j=i*temp;j<(i+1)*temp;j++) {
						if(j < sameTagList.size()) {
							deleteList.add(sameTagList.get(j));
						}
						else {
							break;
						}
					}
					StringBuffer sql = new StringBuffer("delete from bs_user_tags where ");
					for(int m=0;m<deleteList.size();m++) {
						if(m == deleteList.size() - 1) {
							sql.append("(user_id="+deleteList.get(m).getUserId()+" and tag_id="+deleteList.get(m).getTagId()+")");
						}
						else {
							sql.append("(user_id="+deleteList.get(m).getUserId()+" and tag_id="+deleteList.get(m).getTagId()+") or ");
						}
					}
					bsUserTagMapper.deleteUserTag(sql.toString());
				}
			}
			
			//新增用户标签
			SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			String time = format.format(new Date());
			int addTemp = 500;
			int addLength = userIdList.size()%addTemp==0?userIdList.size()/addTemp:userIdList.size()/addTemp+1;
			for(int i=0;i<addLength;i++) {
				String sql = "insert into bs_user_tags(user_id,tag_id,creator,create_time) values";
				StringBuffer insert = new StringBuffer();
				for(int j=i*addTemp;j<(i+1)*addTemp;j++) {
					if(j < userIdList.size()) {
						for(Integer tagId : tagIdList) {
							insert.append("("+userIdList.get(j)+",");
		    				insert.append(tagId+",");
		    				insert.append(creator+",");
		    				insert.append("'"+time+"'),");
						}
					}
					else {
						break;
					}
				}
				sql += insert.toString();
				sql = sql.substring(0, sql.length()-1);
				bsUserTagMapper.insertUserTag(sql);
			}
		}
		else {
			//删除用户所有标签
			List<Integer> selectList = new ArrayList<Integer>();
			for(Map.Entry<Integer, List<Integer>> entry : userTagMap.entrySet()) {
				selectList.add(entry.getKey());
			}
			int temp = 500;
			int length = selectList.size()%temp==0?selectList.size()/temp:selectList.size()/temp+1;
			for(int i=0;i<length;i++) {
				List<Integer> userIdList = new ArrayList<Integer>();
				for(int j=i*temp;j<(i+1)*temp;j++) {
					if(j < selectList.size()) {
						userIdList.add(selectList.get(j));
					}
					else {
						break;
					}
				}
				BsUserTagsExample example = new BsUserTagsExample();
				example.createCriteria().andUserIdIn(userIdList);
				bsUserTagMapper.deleteByExample(example);
			}
		}
	}
}
