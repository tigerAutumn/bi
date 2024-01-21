package com.pinting.business.service.manage.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsSysConfigMapper;
import com.pinting.business.dao.MRoleMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.MRole;
import com.pinting.business.model.MRoleExample;
import com.pinting.business.service.manage.MRoleService;
import com.pinting.business.util.Constants;
@Service
public class MRoleServiceImpl implements MRoleService {
	@Autowired
	private MRoleMapper roleMapper;
	@Autowired
	private BsSysConfigMapper sysConfigMapper;

	@Override
	public List<MRole> findMRoleList(Integer expId) {
		//查询出管理台权限管理员角色id，并排除
		BsSysConfig sys = sysConfigMapper.selectByPrimaryKey(Constants.M_ROLE_MANAGER_ROLE_ID);
        Integer roleId = sys != null ? Integer.valueOf(sys.getConfValue()) : 0;
		MRoleExample example = new MRoleExample();
		if(expId == roleId){
			example.createCriteria().andIdNotEqualTo(roleId);
		}else{
			example.createCriteria();
		}
		
		return roleMapper.selectByExample(example);
	}

	@Override
	public boolean removeRole(int id) {
		int count =0;
		
		count = roleMapper.deleteByPrimaryKey(id);
		
		return count>0;
	}

	@Override
	public MRole findRoleById(int id) {
		return roleMapper.selectByPrimaryKey(id);
	}

	@Override
	public boolean modifyRole(MRole mrole) {
		MRoleExample example = new MRoleExample();
		example.createCriteria().andIdEqualTo(mrole.getId());
		return roleMapper.updateByExampleSelective(mrole, example)==1;
	}

	@Override
	public boolean createRole(MRole mrole) {
		return roleMapper.insertSelective(mrole)==1;
	}

	@Override
	public boolean findRoleByName(String name) {
		MRoleExample example = new MRoleExample();
		example.createCriteria().andNameEqualTo(name);
		return roleMapper.countByExample(example)==1;
	}

	@Override
	public MRole selectMRoleByDafyRoleId(Integer dafyRoleId) {
		if(dafyRoleId == null) {
			return null;
		}
		MRoleExample example = new MRoleExample();
		example.createCriteria().andDafyRoleIdEqualTo(dafyRoleId);
		List<MRole> list = roleMapper.selectByExample(example);
		if(!CollectionUtils.isEmpty(list)) {
			return list.get(0);
		}
		return null;
	}
	
}
