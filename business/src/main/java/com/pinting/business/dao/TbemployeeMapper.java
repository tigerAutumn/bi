package com.pinting.business.dao;

import java.util.Date;

import com.pinting.business.model.vo.AscriptionChangeDetailVO;
import com.pinting.business.model.vo.CustomerQueryVO;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.pinting.business.model.Tbemployee;
import com.pinting.business.model.TbemployeeExample;

public interface TbemployeeMapper {
    int countByExample(TbemployeeExample example);

    int deleteByExample(TbemployeeExample example);

    int deleteByPrimaryKey(Long luserid);

    int insert(Tbemployee record);

    int insertSelective(Tbemployee record);

    List<Tbemployee> selectByExample(TbemployeeExample example);

    Tbemployee selectByPrimaryKey(Long luserid);

    int updateByExampleSelective(@Param("record") Tbemployee record, @Param("example") TbemployeeExample example);

    int updateByExample(@Param("record") Tbemployee record, @Param("example") TbemployeeExample example);

    int updateByPrimaryKeySelective(Tbemployee record);

    int updateByPrimaryKey(Tbemployee record);

	List<CustomerQueryVO> customerQueryIndex(@Param("dafyUserId") Long dafyUserId,@Param("dafyDeptId") Long dafyDeptId,@Param("userName") String userName,@Param("mobile") String mobile,@Param("idcard") String idcard,@Param("isBindBank") String isBindBank,@Param("position") Integer position, @Param("offset") Integer offset);
	int customerQueryCount(@Param("dafyUserId") Long dafyUserId,@Param("dafyDeptId") Long dafyDeptId,@Param("userName") String userName,@Param("mobile") String mobile,@Param("idcard") String idcard,@Param("isBindBank") String isBindBank);

    
    List<Date> selectSyncTime();
    
    void batchInsertTbemployee(@Param("sql")String sql);
    
    
	List<CustomerQueryVO> customerQueryByEmployee(@Param("userName") String userName,@Param("mobile") String mobile,@Param("idcard") String idcard,@Param("employee") List<Long> employee,@Param("dafyUserId") Long dafyUserId,@Param("isBindBank") String isBindBank,@Param("position") Integer position, @Param("offset") Integer offset);
	int customerQueryByEmployeeCount(@Param("userName") String userName,@Param("mobile") String mobile,@Param("idcard") String idcard,@Param("employee") List<Long> employee,@Param("dafyUserId") Long dafyUserId,@Param("isBindBank") String isBindBank);

    AscriptionChangeDetailVO selectByMobile(@Param("mobile")String mobile);
}