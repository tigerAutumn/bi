package com.pinting.business.dao;

import com.pinting.business.model.BsEntrustLoanView;
import com.pinting.business.model.BsEntrustLoanViewExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface BsEntrustLoanViewMapper {
    long countByExample(BsEntrustLoanViewExample example);

    int deleteByExample(BsEntrustLoanViewExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsEntrustLoanView record);

    int insertSelective(BsEntrustLoanView record);

    List<BsEntrustLoanView> selectByExample(BsEntrustLoanViewExample example);

    BsEntrustLoanView selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsEntrustLoanView record, @Param("example") BsEntrustLoanViewExample example);

    int updateByExample(@Param("record") BsEntrustLoanView record, @Param("example") BsEntrustLoanViewExample example);

    int updateByPrimaryKeySelective(BsEntrustLoanView record);

    int updateByPrimaryKey(BsEntrustLoanView record);


    /**
     * 管理台列表查询--总数
     * @param propertySymbol
     * @param startDate
     * @param endDate
     * @return
     */
    int countList(@Param("propertySymbol")String propertySymbol,
    		@Param("startDate")String startDate,
       		@Param("endDate")String endDate);

    /**
     * 管理台列表查询
     * @param propertySymbol
     * @param startDate
     * @param endDate
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsEntrustLoanView> getList(@Param("propertySymbol")String propertySymbol,
    		@Param("startDate")String startDate,@Param("endDate")String endDate,
       		@Param("start") Integer start, @Param("numPerPage") Integer numPerPage);

    /**
     * 管理台列表查询--合计
     * @param propertySymbol
     * @param startDate
     * @param endDate

     * @return
     */
    BsEntrustLoanView getSumList(@Param("propertySymbol")String propertySymbol,
    		@Param("startDate")String startDate,@Param("endDate")String endDate);

}