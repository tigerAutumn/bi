package com.pinting.business.dao;

import com.pinting.business.model.BsUserCustomerManager;
import com.pinting.business.model.BsUserCustomerManagerExample;
import com.pinting.business.model.vo.OwnershipVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsUserCustomerManagerMapper {
    int countByExample(BsUserCustomerManagerExample example);

    int deleteByExample(BsUserCustomerManagerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsUserCustomerManager record);

    int insertSelective(BsUserCustomerManager record);

    List<BsUserCustomerManager> selectByExample(BsUserCustomerManagerExample example);

    BsUserCustomerManager selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsUserCustomerManager record, @Param("example") BsUserCustomerManagerExample example);

    int updateByExample(@Param("record") BsUserCustomerManager record, @Param("example") BsUserCustomerManagerExample example);

    int updateByPrimaryKeySelective(BsUserCustomerManager record);

    int updateByPrimaryKey(BsUserCustomerManager record);

    /**
     * 
     * @param req
     * @return
     */
    List<OwnershipVO> selectOwnershipGrid(@Param("record") OwnershipVO record);

    /**
     * @param req
     * @return
     */
    Integer countOwnership(@Param("record") OwnershipVO req);
}