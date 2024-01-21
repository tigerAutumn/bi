package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.List;

import com.pinting.business.dao.*;
import com.pinting.business.model.*;
import com.pinting.business.service.site.UcUserService;
import com.pinting.business.util.Constants;
import com.pinting.gateway.in.util.MethodRole;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UcUserServiceImpl implements UcUserService{
    @Autowired
	private UcUserMapper ucUserMapper;
    @Autowired
    private UcUserExtMapper ucUserExtMapper;
    
	@Override
	@MethodRole("APP")
	public boolean isValidMobile(String mobile) {
		UcUserExample example = new UcUserExample();
		example.createCriteria().andMobileEqualTo(mobile).andStatusEqualTo(Constants.UC_USER_OPEN);
		
		List<UcUser> ucUserList = ucUserMapper.selectByExample(example);
		//ucUser不存在,返回false 
		if(CollectionUtils.isEmpty(ucUserList)) {
			return false;
		}
		
		UcUserExtExample extExample = new UcUserExtExample();
		extExample.createCriteria().andUcUserIdEqualTo(ucUserList.get(0).getId());
		
		List<UcUserExt> ucUserExtList = ucUserExtMapper.selectByExample(extExample);
		
		List<String> userTypes = new ArrayList<>();
		for( UcUserExt userExt: ucUserExtList ) {
			userTypes.add(userExt.getUserType());
		}
		//ucUser存在,ucUserExt不存在Ext,返回false
        if( !userTypes.contains(Constants.UC_USER_TYPE_BGW) ) {
        	return false;
        }
        return true;
	}
}
