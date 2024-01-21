package com.pinting.business.service.site.impl;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.pinting.business.dao.BsWxAutoReplyMapper;
import com.pinting.business.hessian.site.message.ReqMsg_WxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.hessian.site.message.ResMsg_WxAutoReply_SelectAutoReplyMessage;
import com.pinting.business.model.BsWxAutoReply;
import com.pinting.business.model.BsWxAutoReplyExample;
import com.pinting.business.service.site.WxAutoReplyService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
@Service
public class WxAutoReplyServiceImpl implements WxAutoReplyService {
	
	@Autowired
	private BsWxAutoReplyMapper bsWxAutoReplyMapper;
	
	@Override
	public void selectAutoReplyMessage(
			ReqMsg_WxAutoReply_SelectAutoReplyMessage req,
			ResMsg_WxAutoReply_SelectAutoReplyMessage res) {
		
/*		BsWxAutoReplyExample example = new BsWxAutoReplyExample();
		example.createCriteria().andKeywordsLike("%"+req.getAutoReplyMessage()+"%").andReplyTypeEqualTo(Constants.WX_AUTO_REPLY_TYPE_KEY);
		example.setOrderByClause("update_time desc");
		List<BsWxAutoReply> bsWxAutoReplies = bsWxAutoReplyMapper.selectByExample(example);*/
		
		List<BsWxAutoReply> bsWxAutoReplies = bsWxAutoReplyMapper.selectAutoReplyMessage(req.getAutoReplyMessage(), Constants.WX_AUTO_REPLY_TYPE_KEY);
		res.setDataGrid(BeanUtils.classToArrayList(bsWxAutoReplies));
	}

	@Override
	public List<BsWxAutoReply> getSubscribeReplyList() {
		BsWxAutoReplyExample example = new BsWxAutoReplyExample();
		example.createCriteria().andReplyTypeEqualTo(Constants.WX_AUTO_REPLY_TYPE_SUBSCRIBE);
		example.setOrderByClause("update_time desc");
		return bsWxAutoReplyMapper.selectByExample(example);
	}

}
