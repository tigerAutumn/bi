package com.pinting.business.dao;

import com.pinting.business.model.BsCompanyCustomer;
import com.pinting.business.model.BsCompanyCustomerExample;
import com.pinting.business.model.vo.BsCompanyCustomerVO;
import com.pinting.business.model.vo.CompanyCustomerVO;

import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsCompanyCustomerMapper {
    int countByExample(BsCompanyCustomerExample example);

    int deleteByExample(BsCompanyCustomerExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsCompanyCustomer record);

    int insertSelective(BsCompanyCustomer record);

    List<BsCompanyCustomer> selectByExample(BsCompanyCustomerExample example);

    BsCompanyCustomer selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsCompanyCustomer record, @Param("example") BsCompanyCustomerExample example);

    int updateByExample(@Param("record") BsCompanyCustomer record, @Param("example") BsCompanyCustomerExample example);

    int updateByPrimaryKeySelective(BsCompanyCustomer record);

    int updateByPrimaryKey(BsCompanyCustomer record);

    /**
     * 查询客户总数
     * @param customerCode
     * @param customerName
     * @param parentName
     * @return
     */
    int countCompanyCustomer(@Param("customerCode") String customerCode,
                             @Param("customerName") String customerName,
                             @Param("parentName") String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime);

    /**
     * 分页查询客户总数
     * @param customerCode
     * @param customerName
     * @param parentName
     * @param start
     * @param numPerPage
     * @return
     */
    List<BsCompanyCustomerVO> selectCompanyCustomer(@Param("customerCode") String customerCode,
                                                    @Param("customerName") String customerName,
                                                    @Param("parentName") String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime,
                                                    @Param("start") Integer start,
                                                    @Param("numPerPage") Integer numPerPage);
    /**
     *  分页查询需要导出的客户
     * @param customerCode
     * @param customerName
     * @param parentName
     * @param startTime
     * @param endTime
     * @param start
     * @param numPerPage
     * @return
     */
	List<CompanyCustomerVO> selectCompanyCustomerExport(@Param("customerCode") String customerCode,
            @Param("customerName") String customerName,
            @Param("parentName") String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime,
            @Param("start") Integer start,
            @Param("numPerPage") Integer numPerPage);
	/**
	 * 查询业务新增客户总数
	 * @param customerCode
	 * @param customerName
	 * @param parentName
	 * @param startTime
	 * @param endTime
	 * @return
	 */
	int countCompanyCustomerBusiness(@Param("customerCode") String customerCode,
            @Param("customerName") String customerName,
            @Param("parentName") String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime);
	/**
	 * 分页查询业务新增客户客户数据
	 * @param customerCode
	 * @param customerName
	 * @param parentName
	 * @param startTime
	 * @param endTime
	 * @param start
	 * @param numPerPage
	 * @return
	 */
	List<BsCompanyCustomerVO> selectCompanyCustomerBusiness(@Param("customerCode") String customerCode,
            @Param("customerName") String customerName,
            @Param("parentName") String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime,
            @Param("start") Integer start,
            @Param("numPerPage") Integer numPerPage);
	
    /**
     *  查询需要导出的业务增量客户
     * @param customerCode
     * @param customerName
     * @param parentName
     * @param startTime
     * @param endTime
     * @param start
     * @param numPerPage
     * @return
     */
	List<CompanyCustomerVO> selectCompanyCustomerBusinessExport(@Param("customerCode") String customerCode,
            @Param("customerName") String customerName,
            @Param("parentName") String parentName,@Param("startTime")String startTime,@Param("endTime")String endTime,
            @Param("start") Integer start,
            @Param("numPerPage") Integer numPerPage);

}