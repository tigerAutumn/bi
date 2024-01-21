package com.pinting.business.dao;

import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.BsSpecialJnlExample;
import com.pinting.business.model.vo.BsSpecialJnlVO;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface BsSpecialJnlMapper {
    int countByExample(BsSpecialJnlExample example);

    int deleteByExample(BsSpecialJnlExample example);

    int deleteByPrimaryKey(Integer id);

    int insert(BsSpecialJnl record);

    int insertSelective(BsSpecialJnl record);

    List<BsSpecialJnl> selectByExample(BsSpecialJnlExample example);

    BsSpecialJnl selectByPrimaryKey(Integer id);

    int updateByExampleSelective(@Param("record") BsSpecialJnl record, @Param("example") BsSpecialJnlExample example);

    int updateByExample(@Param("record") BsSpecialJnl record, @Param("example") BsSpecialJnlExample example);

    int updateByPrimaryKeySelective(BsSpecialJnl record);

    int updateByPrimaryKey(BsSpecialJnl record);
    /**
     * 分页查询系统配置列表
     * @param bsSysConfig
     * @return
     */
    List<BsSpecialJnl> selectByPage(BsSpecialJnl bsSpecialJnl);
    
    /**
     * 分页查询（异常回款查询）
     * @param record
     * @return
     */
    List<BsSpecialJnlVO> bsSpecialJnlPage(BsSpecialJnlVO record);
    int bsSpecialJnlCount(BsSpecialJnlVO record);
    /**
     * 查询总金额
     * @param record
     * @return
     */
    Double amountSum(BsSpecialJnlVO record);
    /**
     * 根据用户查询未成功的奖励金转余额记录
     * @param userId
     * @return
     */
    BsSpecialJnl selectBonusByUserId(Integer userId);
    
    /**
     * 根据主键查询未处理的信息 
     * (status = 1 or status = 2)
     * @param id
     * @return
     */
    BsSpecialJnl selectByPrimaryKeyInDeal(Integer id);

}