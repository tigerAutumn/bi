package com.pinting.manage.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.hessian.manage.message.ReqMsg_BsUser_BsUserTagListQuery;
import com.pinting.business.model.BsTag;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserTags;
import com.pinting.business.service.manage.BsTagService;
import com.pinting.business.service.manage.BsUserService;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.manage.enums.CookieEnums;
import com.pinting.util.Constants;
import com.pinting.util.ReturnDWZAjax;

@Controller
public class BsUserTagsController {
	
	@Autowired
	@Qualifier("dispatchService")
	public HessianService userTagsService;
	
	@Autowired
	private BsTagService bsTagService;
	@Autowired
	private BsUserService bsUserService;
	
	private Logger log = LoggerFactory.getLogger(BsUserTagsController.class);
	
	/**
     * 选择标签
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/bsuser/user_tag_list")
    public String userTagList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model, ReqMsg_BsUser_BsUserTagListQuery req) {
        String tagsForQuery = request.getParameter("tagsForQuery");
    	model.put("tagsForQuery", tagsForQuery);
        
    	// 全量添加标签参数，查询所有数据
        String isAll = request.getParameter("isAll");
        if("yes".equals(isAll)) {
            // 查询满足条件的所有用户信息
            model.put("is_user_list", "yes");
            updateReq(req, request);
            model.put("req", req);
            // 获取所有标签以及当前用户选中的标签
            List<BsTag> list = bsTagService.findAllTagList();
            model.put("tags", list);
            model.put("tagTotalRows", list.size());
            model.put("isAll", isAll);
        } else {
            String userId = request.getParameter("userId");
            String userIds = request.getParameter("userIds");
            if(StringUtil.isBlank(userId) && StringUtil.isBlank(userIds)) {
                throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
            }
            if(StringUtil.isBlank(userId) && StringUtil.isNotBlank(userIds)){
                model.put("is_user_list", "yes");
                model.put("userIds", userIds);
                // 获取所有标签以及当前用户选中的标签
                List<BsTag> list = bsTagService.findAllTagList();
                model.put("tags", list);
                model.put("tagTotalRows", list.size());
            } else {
                // 来自用户标签
                String source = request.getParameter("source");
                model.put("source", source);
                model.put("userIds", userId);
                model.put("is_user_list", "no");
                // 获取所有标签以及当前用户选中的标签
                List<Map<String,Object>> list = bsTagService.findUserSelectTag(Integer.parseInt(userId));
                model.put("tags", list);
                model.put("tagTotalRows", list.size());
            }
        }
        return "/bsTag/user_tag_list";
    }
    
    private void updateReq(ReqMsg_BsUser_BsUserTagListQuery req, HttpServletRequest request) {
        Integer pageNum = req.getPageNum();
        Integer numPerPage = req.getNumPerPage();
        if (pageNum <= 0 || numPerPage <= 0) {
            pageNum = Constants.MANAGE_DEFAULT_PAGENUM;
            numPerPage = Constants.MANAGE_100_NUMPERPAGE;
            req.setPageNum(pageNum);
            req.setNumPerPage(numPerPage);
        }
        String tagIdsForQuery = request.getParameter("tagIdsForQuery");
        List<Integer> tagIdList = new ArrayList<Integer>();
        if(!StringUtil.isBlank(tagIdsForQuery)) {
            String[] tagIds = tagIdsForQuery.split(",");
            for (String tagId : tagIds) {
                tagIdList.add(Integer.parseInt(tagId));
            }
        }
        req.setTagIdList(tagIdList);
    }
    
    
	/**
	 * 用户批量打标签
	 */
	@RequestMapping("/bsUserTags/batchEdit")
    @ResponseBody
    public Map<Object, Object> batchEdit(HttpServletRequest request, HttpServletResponse response){
        String userIds = request.getParameter("userIds");
        String tagIds = request.getParameter("tagIds");
        if(StringUtil.isBlank(userIds)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        
        List<Integer> tagIdList = new ArrayList<Integer>();
        if(!StringUtil.isBlank(tagIds)) {
            String[] tagIdStrList = tagIds.split(",");
            for (String tagId : tagIdStrList) {
                tagIdList.add(Integer.parseInt(tagId));
            }
        }
        String[] userStrList = userIds.replace("，", ",").split(",");
        //验证userId有效性
        List<BsUser> userList = bsUserService.findUserByIds(userStrList);
        if(!CollectionUtils.isEmpty(userList)) {
        	//用户表和用户标签表进行左关联,查询用户已打的标签
        	List<BsUserTags> userTagList = bsTagService.findUserTagsByUserId(userList);
        	Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>();
        	for(BsUserTags userTag : userTagList) {
        		if(map.containsKey(userTag.getUserId())) {
        			map.get(userTag.getUserId()).add(userTag.getTagId());
        		}
        		else {
        			List<Integer> tagList = new ArrayList<Integer>();
        			if(userTag.getTagId() != null) {
        				tagList.add(userTag.getTagId());
        			}
        			map.put(userTag.getUserId(), tagList);
        		}
        	}
        	
        	//修改用户标签
        	bsTagService.batchUpdateUserTag(map,tagIdList,Integer.valueOf(mUserId));
        }
        return ReturnDWZAjax.success("用户打标签成功");
    }
	
	/**
	 * 
	 * @Title: singleEdit 
	 * @Description: 单个用户修改标签
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
	@RequestMapping("/bsUserTags/singleEdit")
    @ResponseBody
    public Map<Object, Object> singleEdit(HttpServletRequest request, HttpServletResponse response) {
		String userId = request.getParameter("userIds");
        String tagIds = request.getParameter("tagIds");
        if(StringUtil.isBlank(userId)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
        List<Integer> tagIdList = new ArrayList<Integer>();
        if(!StringUtil.isBlank(tagIds)) {
            String[] tagIdStrList = tagIds.split(",");
            for (String tagId : tagIdStrList) {
                tagIdList.add(Integer.parseInt(tagId));
            }
        }
        
        //修改用户标签
        bsTagService.updateUserTag(Integer.valueOf(userId), tagIdList, Integer.valueOf(mUserId));
        return ReturnDWZAjax.success("用户打标签成功");
	}
	
	/**
	 * 查询所有标签
	 * @param request
	 * @param response
	 * @param model
	 * @return
	 */
	@RequestMapping("/bsuser/tag_list")
    public String tagList(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        // 获取所有标签以及当前用户选中的标签
        List<BsTag> list = bsTagService.findAllTagList();
        model.put("tags", list);
        model.put("tagsForQuery", request.getParameter("tagsForQuery"));
        model.put("tagTotalRows", list.size());
        return "/bsTag/tag_list";
    }
	
	/**
	 * 
	 * @Title: batchAllUserTag 
	 * @Description: 全部用户打标签
	 * @param req
	 * @param request
	 * @param response
	 * @return
	 * @throws
	 */
	@RequestMapping("/bsUserTags/batchAllUserTag")
    @ResponseBody
    public Map<Object, Object> batchAllUserTag(ReqMsg_BsUser_BsUserTagListQuery req,
    		HttpServletRequest request, HttpServletResponse response) {
		String tagIdStrs = request.getParameter("tagIds");
		List<Integer> tagIdList = new ArrayList<Integer>();
		if(!StringUtil.isBlank(tagIdStrs)) {
			String[] tagIds = tagIdStrs.split(",");
			for (String tagId : tagIds) {
				tagIdList.add(Integer.parseInt(tagId));
			}
		}
		
		String tagIdsForQuery = request.getParameter("tagIdsForQuery");
        List<Integer> tagIdListQuery = new ArrayList<Integer>();
        if(!StringUtil.isBlank(tagIdsForQuery)) {
            String[] tagIds = tagIdsForQuery.split(",");
            for (String tagId : tagIds) {
                tagIdListQuery.add(Integer.parseInt(tagId));
            }
        }
        req.setTagIdList(tagIdListQuery);
        req.setNumPerPage(null);
		List<BsUser> userList = bsUserService.selectAllUserForUserId(req);
		
		CookieManager cookie = new CookieManager(request);
        String mUserId = cookie.getValue(CookieEnums._MANAGE_PLAT_FORM.getName(),
                CookieEnums._MANAGE_PLAT_FORM_USER_ID.getName(), true);
		
		if(!CollectionUtils.isEmpty(userList)) {
			//用户表和用户标签表进行左关联,查询用户已打的标签
        	List<BsUserTags> userTagList = bsTagService.findUserTagsByUserId(userList);
        	Map<Integer,List<Integer>> map = new HashMap<Integer,List<Integer>>();
        	for(BsUserTags userTag : userTagList) {
        		if(map.containsKey(userTag.getUserId())) {
        			map.get(userTag.getUserId()).add(userTag.getTagId());
        		}
        		else {
        			List<Integer> tagList = new ArrayList<Integer>();
        			if(userTag.getTagId() != null) {
        				tagList.add(userTag.getTagId());
        			}
        			map.put(userTag.getUserId(), tagList);
        		}
        	}
			
        	//修改用户标签
        	bsTagService.batchUpdateUserTag(map,tagIdList,Integer.valueOf(mUserId));
        }
		return ReturnDWZAjax.success("全部用户打标签成功");
	}
	
	/**
	 * 根据userId查询用户个数
	 * @param req
	 * @param request
	 * @return
	 */
	@RequestMapping("/bsUserTags/userIdCount")
	@ResponseBody
	public Map<String, Object> userIdCount(HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> result = new HashMap<String, Object>();
		String userIds = request.getParameter("userIds");
		String[] userStrList = userIds.replace("，", ",").split(",");
		List<BsUser> userList = bsUserService.findUserByIds(userStrList);
		result.put("userCount", userList.size());
		return result;
	}
}
