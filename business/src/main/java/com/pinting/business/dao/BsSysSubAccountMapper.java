package com.pinting.business.dao;

import com.pinting.business.model.BsSysSubAccount;
import com.pinting.business.model.BsSysSubAccountExample;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface BsSysSubAccountMapper {
    int countByExample(BsSysSubAccountExample example);

    int deleteByExample(BsSysSubAccountExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSysSubAccount record);

    int insertSelective(BsSysSubAccount record);

    List<BsSysSubAccount> selectByExample(BsSysSubAccountExample example);

    BsSysSubAccount selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSysSubAccount record, @Param("example") BsSysSubAccountExample example);

    int updateByExample(@Param("record") BsSysSubAccount record, @Param("example") BsSysSubAccountExample example);

    int updateByPrimaryKeySelective(BsSysSubAccount record);

    int updateByPrimaryKey(BsSysSubAccount record);
    
    BsSysSubAccount selectByCode(String code);
    
    /**
	 * 根据id增量修改余额信息
	 * @param record
	 * @return
	 */
    int updateById(BsSysSubAccount record);

    /**
     * 根据编号查询并加行级锁
     * @param id
     * @return
     */
    BsSysSubAccount selectSysSubAccountForLock(@Param("id") Integer id);
    
    /**
	 * 统计系统账户余额 、用户余额  、产品户余额和回款户余额
	 * @return map
	 */
	Map<String,Object> countSysSubAccountBalance();
	
	/**
	 * 统计恒丰账户系统账户余额 、用户余额  、产品户余额和回款户余额
	 * @return
	 */
	Map<String, Object> countDepSysSubAccountBalance();

    /**
     * 财务总账查询(宝付)-统计系统账户余额 、用户余额  、产品户余额和回款户余额
     * @return
     */
    Map<String, Object> countThdSysSubAccountBalance();
	
}