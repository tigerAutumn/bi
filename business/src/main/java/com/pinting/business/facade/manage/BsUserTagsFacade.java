package com.pinting.business.facade.manage;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.dao.BsUserTagsMapper;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUserTags_BsUserTagsAddList;
import com.pinting.business.hessian.manage.message.ResMsg_BsUserTags_BsUserTagsAddList;
import com.pinting.business.model.BsUserTags;
import com.pinting.business.model.vo.BsUserAssetVO;
import com.pinting.business.service.manage.BsTagService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.StringUtil;

@Component("BsUserTags")
public class BsUserTagsFacade {
	
	@Autowired
	private BsTagService tagSevice;
	@Autowired
	public BsUserService bsUserService;
	
	@Autowired
	private BsUserTagsMapper bsUserTagsMapper;
	
	private Logger log = LoggerFactory.getLogger(BsUserTagsFacade.class);
	
	/**
	 * 获取页面userId、标签编号 tagIds --> 批量打标签
	 * @param req
	 * @param res
	 */
	public void bsUserTagsAddList(ReqMsg_BsUserTags_BsUserTagsAddList req, ResMsg_BsUserTags_BsUserTagsAddList res) {
		BsUserTags userTag = new BsUserTags();
		/** 打标签的userId */
		if(StringUtil.isNotEmpty(req.getUserIds()) && StringUtil.isNotEmpty(req.getTagIds()) ) {
			StringBuffer buffer = new StringBuffer("insert into bs_user_tags(user_id,tag_id,creator,create_time) VALUES");
			String userStr = StringUtil.trimStr(req.getUserIds());
			String[] userids = userStr.replace("，", ",").split(",");
			String trimTag = StringUtil.trimStr(req.getTagIds());
			String[] tagids = trimTag.replace("，", ",").split(",");
			if(userids.length > 0 && tagids.length>0) {
				for (String ustr : userids) {
					if(StringUtil.isNotEmpty(ustr.trim())) {
						for(String tstr : tagids){
							try {
								// 校验userId是否存在
								BsUserAssetVO bsUserAssetVO = new BsUserAssetVO();
								bsUserAssetVO.setId(Integer.parseInt(ustr));
								List<BsUserAssetVO> bsUserList =  bsUserService.findAllUser(bsUserAssetVO);
								if(bsUserList.size() != 0) {
									buffer.append("('"+ Integer.parseInt(ustr)+"',"+Integer.parseInt(tstr)+",'"+req.getCreator()+"','"+DateUtil.format(new Date())+"'),");
									//log.info("bsusertags 批量sql: " + buffer.toString().substring(0, buffer.toString().length()-1));

									
									bsUserTagsMapper.insertUserTag(buffer.toString().substring(0, buffer.toString().length()-1));
								} else {
									log.error("该用户userId："+ustr+"不存在");
								}
								
							} catch (Exception e) {
								log.error("此用户userId："+ustr+"不符合规范");
							}
						}
					}
				}
				
			}
		}
		
	}
	
	
}
