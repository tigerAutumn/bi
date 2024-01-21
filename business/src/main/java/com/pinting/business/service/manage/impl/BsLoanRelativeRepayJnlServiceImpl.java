package com.pinting.business.service.manage.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.pinting.business.dao.BsLoanRelativeAmountChangeMapper;
import com.pinting.business.dao.BsLoanRelativeRecordMapper;
import com.pinting.business.dao.BsMatchMapper;
import com.pinting.business.dao.BsRepayJnlMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsLoanRelativeAmountChange;
import com.pinting.business.model.BsLoanRelativeAmountChangeExample;
import com.pinting.business.model.BsLoanRelativeRecord;
import com.pinting.business.model.BsLoanRelativeRecordExample;
import com.pinting.business.model.BsRepayJnl;
import com.pinting.business.model.BsRepayJnlExample;
import com.pinting.business.model.vo.BsLoanRelativeAmountChangeVO;
import com.pinting.business.model.vo.BsMatchVO;
import com.pinting.business.service.manage.BsLoanRelativeRepayJnlService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.hessian.service.HessianService;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.B2GReqMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.B2GResMsg_Payment_QueryRepayJnl;
import com.pinting.gateway.hessian.message.dafy.model.BorrowerRepayData;

/**
 * 拉取债权关系还款流水存库
 * @author HuanXiong
 * @version 2016-4-27 下午7:05:30
 */
@Service
public class BsLoanRelativeRepayJnlServiceImpl implements BsLoanRelativeRepayJnlService {

    @Autowired
    @Qualifier("dafyGatewayService")
    private HessianService             dafyGatewayService;
    @Autowired
    private BsRepayJnlMapper           bsRepayJnlMapper;
    @Autowired
    private BsLoanRelativeRecordMapper bsLoanRelativeRecordMapper;
    @Autowired
    private BsLoanRelativeAmountChangeMapper bsLoanRelativeAmountChangeMapper;
    @Autowired
    private BsMatchMapper bsMatchMapper;

    /**
     * 拉取债权关系还款流水存库，修改拉取数据状态
     * @param customerId    借款人客户号
     * @param borrowNo  借款编号
     */
    @Override
    public void drawRepayment(String customerId, String borrowNo, String id) {
        // 数据校验 开始
        if (StringUtil.isBlank(customerId) || StringUtil.isBlank(borrowNo)) {
            throw new PTMessageException(PTMessageEnum.NO_DATA_FOUND);
        }
        // 数据校验 结束

        BsLoanRelativeAmountChange bsLoanRelativeAmountChange = bsLoanRelativeAmountChangeMapper.selectByPrimaryKey(Integer.parseInt(id));
        String propertySymbol = bsLoanRelativeAmountChange.getPropertySymbol();
        // 拉取达飞数据 开始
        B2GReqMsg_Payment_QueryRepayJnl req = new B2GReqMsg_Payment_QueryRepayJnl();
        req.setPropertySymbol(propertySymbol);
        req.setBorrowId(borrowNo);
        req.setBorrowerCustomerId(customerId);
        B2GResMsg_Payment_QueryRepayJnl res = (B2GResMsg_Payment_QueryRepayJnl) dafyGatewayService
            .handleMsg(req);
        List<BorrowerRepayData> repayDatas = res.getBorrowerRepays();
        if(!"000000".equals(res.getResCode())) {
            throw new PTMessageException(PTMessageEnum.TRANS_ERROR, res.getResMsg());
        }
        
        // 存在还款数据，则进行存库 BsRepayJnl 操作
        if (!CollectionUtils.isEmpty(repayDatas)) {
            for (BorrowerRepayData borrowerRepayData : repayDatas) {
                BsRepayJnlExample bsRepayJnlExample = new BsRepayJnlExample();
                bsRepayJnlExample.createCriteria()
                    .andRepayTransNoEqualTo(borrowerRepayData.getRepayTransNo()) // 还款交易编号
                    .andBorrowNoEqualTo(borrowerRepayData.getBorrowNo()) // 借款编号
                    .andCustomerIdEqualTo(borrowerRepayData.getCustomerId()) // 借款人客户号
                    .andRepayerIdCardEqualTo(borrowerRepayData.getRepayerIdCard()) // 还款人身份证
                    .andRepayPrincipalEqualTo(borrowerRepayData.getRepayPrincipal()) // 还款本金
                    .andRepayTimeEqualTo(borrowerRepayData.getRepayTime()); // 还款时间
                List<BsRepayJnl> bsRepayJnls = bsRepayJnlMapper.selectByExample(bsRepayJnlExample);
                // 当 BsRepayJnl 中不存在还款数据的时候才插入还款记录
                if (CollectionUtils.isEmpty(bsRepayJnls)) {
                    BsRepayJnl bsRepayJnl = new BsRepayJnl();
                    bsRepayJnl.setBorrowNo(borrowerRepayData.getBorrowNo());
                    bsRepayJnl.setCustomerId(borrowerRepayData.getCustomerId());
                    bsRepayJnl.setRepayerIdCard(borrowerRepayData.getRepayerIdCard());
                    bsRepayJnl.setRepayerName(borrowerRepayData.getRepayerName());
                    bsRepayJnl.setRepayPrincipal(borrowerRepayData.getRepayPrincipal());
                    bsRepayJnl.setRepayTime(borrowerRepayData.getRepayTime());
                    bsRepayJnl.setRepayTransNo(borrowerRepayData.getRepayTransNo());
                    bsRepayJnl.setCreateTime(new Date());
                    bsRepayJnl.setUpdateTime(new Date());
                    bsRepayJnlMapper.insertSelective(bsRepayJnl);
                    //修改债权关系匹配金额变动表
                    if(StringUtil.isNotBlank(id)){
                        BsLoanRelativeAmountChange record = new BsLoanRelativeAmountChange();
                        record.setId(Integer.parseInt(id));
                        record.setIsPullDetail("YES");
                        bsLoanRelativeAmountChangeMapper.updateByPrimaryKeySelective(record);
                    }
                    
                }
            }
        }
    }

    /**
     * 查询债权关系
     * @param start
     * @param numPerPage
     * @return
     */
    @Override
    public ArrayList<HashMap<String, Object>> queryDataGrid(int start, int numPerPage) {
        BsLoanRelativeRecordExample bsLoanRelativeRecordExample = new BsLoanRelativeRecordExample();
        List<BsLoanRelativeAmountChangeVO> bsLoanRelativeRecords = bsLoanRelativeRecordMapper
            .selectByExamplePage(bsLoanRelativeRecordExample, start, numPerPage);
        return BeanUtils.classToArrayList(bsLoanRelativeRecords);
    }

    /**
     * 债权关系总条数
     * @return
     */
    @Override
    public int countDataGrid() {
        BsLoanRelativeAmountChangeExample bsLoanRelativeAmountChangeExample = new BsLoanRelativeAmountChangeExample();
        return bsLoanRelativeAmountChangeMapper.countByExample(bsLoanRelativeAmountChangeExample);
    }

    /**
     * 还款列表
     * @param start
     * @param numPerPage
     * @return
     */
    @Override
    public ArrayList<HashMap<String, Object>> queryRepayJnDataGrid(String customerId, String borrowNo, int start, int numPerPage) {
        BsRepayJnlExample bsRepayJnlExample = new BsRepayJnlExample();
        bsRepayJnlExample.createCriteria().andBorrowNoEqualTo(borrowNo).andCustomerIdEqualTo(customerId);
        List<BsRepayJnl> repayJnls = bsRepayJnlMapper.selectByExample(bsRepayJnlExample);
        
        return BeanUtils.classToArrayList(repayJnls);
    }
    
	@Override
	public List<BsMatchVO> getMatchListBySubAccountId(int subAccountId) {
		return bsMatchMapper.getMatchListBySubAccountId(subAccountId);
	}

}
