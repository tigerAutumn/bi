package com.pinting.business.facade.manage;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.dao.BsUserTagsMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagList;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ReqMsg_Tag_BsTagModify;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagDelete;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagList;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_SelectByPrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_Tag_BsTagModify;
import com.pinting.business.model.BsTag;
import com.pinting.business.model.vo.BsTagVO;
import com.pinting.business.service.manage.BsTagService;
import com.pinting.business.util.BeanUtils;

/**
 * 
 * @author shh
 *
 */
@Component("Tag")
public class BsTagFacade {
	
	@Autowired
	private BsTagService tagSevice;
	@Autowired
	private BsUserTagsMapper bsUserTagsMapper;
	
	/**
	 * 标签列表
	 * @param req
	 * @param res
	 */
	public void bsTagList(ReqMsg_Tag_BsTagList req, ResMsg_Tag_BsTagList res) {
		int totalRows = tagSevice.findCountTag(req.getContent());
		if (totalRows > 0) {
			ArrayList<BsTagVO> bsTagList = tagSevice.findTaglList(req.getContent(), req.getPageNum(), req.getNumPerPage(), 
					req.getOrderDirection(), req.getOrderField());
			for(BsTagVO vo : bsTagList) {
				vo.setCountTag(vo.getCountTag()==null?0:vo.getCountTag());
			}
			ArrayList<HashMap<String, Object>> tagList = BeanUtils.classToArrayList(bsTagList);
			res.setValueList(tagList);
		}
		res.setTotalRows(totalRows);
		res.setPageNum(req.getPageNum());
		res.setNumPerPage(req.getNumPerPage());
	}
	
	/**
	 * 进入添加/编辑页面
	 * @param req
	 * @param res
	 */
	public void selectByPrimaryKey(ReqMsg_Tag_SelectByPrimaryKey req, ResMsg_Tag_SelectByPrimaryKey res) {
		if (req.getId() != null && req.getId() != 0) {
			BsTag bsTag = tagSevice.bsTagPrimaryKey(req.getId());
			res.setId(bsTag.getId());
			res.setContent(bsTag.getContent());
			res.setSerialId(bsTag.getSerialId());
			res.setCreator(bsTag.getCreator());
			res.setCreateTime(bsTag.getCreateTime());
		}
	}
	
	/**
	 * 添加/编辑
	 * @param req
	 * @param res
	 */
	public void bsTagModify(ReqMsg_Tag_BsTagModify req, ResMsg_Tag_BsTagModify res) {
		BsTag bsTag = new BsTag();
		bsTag.setContent(req.getContent().trim());//存库时标签名称去掉首尾空格
		BsTag result = tagSevice.selectUserTag(bsTag); // 查询标签名称是否已存在
		bsTag.setId(req.getId());
		if(req.getId() != null && req.getId() != 0) { // 编辑
			if(result != null ) {
				if(result.getId().equals(req.getId())) {
					BsTag maxBsTag = tagSevice.findMaxSerialId(bsTag);
					if(maxBsTag.getSerialId() != 0) {
						bsTag.setSerialId(maxBsTag.getSerialId()+1); // 之后serial_id最大值+1
						bsTag.setUpdateTime(new Date());
						tagSevice.updatUserTag(bsTag);
						res.setFlag(ResMsg_Tag_BsTagModify.MODIFY_SUCCESS);
					}
				} else {
					res.setFlag(ResMsg_Tag_BsTagModify.REPEAT_NAME);
				}
			} else {
				BsTag maxBsTag = tagSevice.findMaxSerialId(bsTag);
				if(maxBsTag != null && maxBsTag.getSerialId() != 0) {
					bsTag.setSerialId(maxBsTag.getSerialId()+1); // 之后serial_id最大值+1
					bsTag.setUpdateTime(new Date());
					tagSevice.updatUserTag(bsTag);
					res.setFlag(ResMsg_Tag_BsTagModify.MODIFY_SUCCESS);
				}
			}
		} else { // 添加
			if(result == null) {
				int count = tagSevice.findCountOfTag();
				if (count == 0) {
					bsTag.setCreateTime(new Date());
					bsTag.setSerialId(1); // 默认值1
					bsTag.setCreator(req.getCreator());
					tagSevice.addUserTag(bsTag);
					res.setFlag(ResMsg_Tag_BsTagModify.INSERT_SUCCESS);
				} else {
					BsTag maxBsTag = tagSevice.findMaxSerialId(bsTag);
					if(maxBsTag.getSerialId() != 0) {
						bsTag.setCreateTime(new Date());
						bsTag.setSerialId(maxBsTag.getSerialId()+1); // 之后serial_id最大值+1
						bsTag.setCreator(req.getCreator());
						tagSevice.addUserTag(bsTag);
						res.setFlag(ResMsg_Tag_BsTagModify.INSERT_SUCCESS);
					}
				}
			} else {
				res.setFlag(ResMsg_Tag_BsTagModify.REPEAT_NAME);
			}
		}
	} 
	
	/**
	 * 删除
	 * @param req
	 * @param res
	 */
	public void bsTagDelete(ReqMsg_Tag_BsTagDelete req, ResMsg_Tag_BsTagDelete res) {
		if(req.getId() != null && req.getId() != 0) {
			bsUserTagsMapper.deleteByTagId(req.getId());
			tagSevice.deleteUserTagById(req.getId());
		}
	}
	
	/**
	 * 所有标签名称
	 * @param req
	 * @param res
	 */
	public void bsTagListQuery(ReqMsg_Tag_BsTagListQuery req, ResMsg_Tag_BsTagListQuery res) {
		List<BsTag> list = tagSevice.findAllTagList();
		res.setTagList(BeanUtils.classToArrayList(list));
	}
	


}
