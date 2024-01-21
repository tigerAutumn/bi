package com.pinting.business.dao;

import com.pinting.business.model.BsSales;
import com.pinting.business.model.BsSalesExample;
import com.pinting.business.model.vo.BsSalesDirectInviteVO;
import com.pinting.business.model.vo.BsSalesVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSalesMapper {
	   int countByExample(BsSalesExample example);

	    int deleteByExample(BsSalesExample example);

	    int deleteByPrimaryKey(Integer id);

	    int insert(BsSales record);

	    int insertSelective(BsSales record);

	    List<BsSales> selectByExample(BsSalesExample example);

	    BsSales selectByPrimaryKey(Integer id);

	    int updateByExampleSelective(@Param("record") BsSales record, @Param("example") BsSalesExample example);

	    int updateByExample(@Param("record") BsSales record, @Param("example") BsSalesExample example);

	    int updateByPrimaryKeySelective(BsSales record);

	    int updateByPrimaryKey(BsSales record);
	    
	    int count(BsSalesVO record);
	    
	    List<BsSalesVO> page(BsSalesVO record);
	    
	    int userCount(BsSalesVO record);
	    
	    List<BsSalesVO> userPage(BsSalesVO record);
	    
	    BsSales selectBsSales(BsSales record);
	    
	    List<BsSalesDirectInviteVO> selectSalesDirectInviteUsers(BsSales record);
    
	    int countSalesDirectInviteUsers(BsSales record);
}