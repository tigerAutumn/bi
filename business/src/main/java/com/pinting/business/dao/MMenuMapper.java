package com.pinting.business.dao;

import com.pinting.business.model.MMenu;
import com.pinting.business.model.MMenuExample;
import com.pinting.business.model.vo.MMenuRoleVO;
import com.pinting.business.model.vo.MMenuVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface MMenuMapper {
    int countByExample(MMenuExample example);

    int deleteByExample(MMenuExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(MMenu record);

    int insertSelective(MMenu record);

    List<MMenu> selectByExample(MMenuExample example);

    MMenu selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") MMenu record, @Param("example") MMenuExample example);

    int updateByExample(@Param("record") MMenu record, @Param("example") MMenuExample example);

    int updateByPrimaryKeySelective(MMenu record);

    int updateByPrimaryKey(MMenu record);
    
    List<MMenuVO> selectParentMenuByRoleId(Integer roleId);
    
    List<MMenu> selectSubMenuByRoleId(Integer roleId);
    
    List<MMenuVO> selectAllParentMenu();
    
    /**
     * 查询所有子菜单
     * @return
     */
    List<MMenuRoleVO> selectAllChildMenu();
}