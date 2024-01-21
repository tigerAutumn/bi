package com.pinting.business.service.manage;

import java.util.List;

import com.pinting.business.model.BsSpecialJnl;
import com.pinting.business.model.vo.BsSpecialJnlVO;

/**
 * 特殊交易流水接口服务
 * @Project: business
 * @Title: BsSpecialJnlService.java
 * @author dingpf
 * @date 2015-2-4 下午5:35:18
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
public interface BsSpecialJnlService {
	/**
	 * 新增特殊交易流水
	 * @param type 交易类型
	 * @param detail 交易详细
	 * @return 新增成功返回true，否则返回false
	 */
	public boolean addSpecialJnl(String type,String detail);
	
	/**
     * 分页查询（异常回款查询）
     * @param record
     * @return
     */
	public List<BsSpecialJnlVO> bsSpecialJnlPage(BsSpecialJnlVO record);
	public int bsSpecialJnlCount(BsSpecialJnlVO record);
	/**
	 * 总金额
	 * @param record
	 * @return
	 */
	public Double amountSum(BsSpecialJnlVO record);
	
	/**
	 * 根据主键查询未处理的信息
	 * @param id
	 * @return
	 */
	public BsSpecialJnl getByPrimaryKeyInDeal(Integer id);
	
	/**
	 * 修改特殊交易
	 * @param jnl
	 */
	public void update(BsSpecialJnl jnl);
}
