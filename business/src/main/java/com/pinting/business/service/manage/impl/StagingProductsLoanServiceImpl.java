package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.LnCreditTransferMapper;
import com.pinting.business.dao.LnLoanMapper;
import com.pinting.business.dao.LnRepayScheduleMapper;
import com.pinting.business.model.LnCreditTransfer;
import com.pinting.business.model.LnCreditTransferExample;
import com.pinting.business.model.dto.StagingProductsLoanDTO;
import com.pinting.business.model.vo.AverageCapitalPlusInterestVO;
import com.pinting.business.model.vo.StagingProductsLoanVO;
import com.pinting.business.service.common.AlgorithmService;
import com.pinting.business.service.manage.StagingProductsLoanService;
import com.pinting.core.util.MoneyUtil;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by shh on 2016/11/7 20:00.
 */
@Service
public class StagingProductsLoanServiceImpl implements StagingProductsLoanService {

    @Autowired
    private LnLoanMapper lnLoanMapper;
    @Autowired
    private AlgorithmService algorithmService;
    @Autowired
    private LnCreditTransferMapper lnCreditTransferMapper;

    @Override
    public List<StagingProductsLoanVO> queryPTStagingProductsLoan(StagingProductsLoanVO record) {

        List<StagingProductsLoanVO> ptList = lnLoanMapper.selectPTStagingProductsLoan(record);
        //计算利息
        for (StagingProductsLoanVO vo:ptList){

            LnCreditTransferExample example=new LnCreditTransferExample();
            example.createCriteria().andOutLoanRelationIdEqualTo(vo.getLoanRelationId());
            List<LnCreditTransfer> transferList=lnCreditTransferMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(transferList) && transferList.size()>0){
                vo.setTotalAmount(MoneyUtil.add(vo.getTotalAmount(),transferList.get(0).getAmount()).doubleValue());
            }


            List<AverageCapitalPlusInterestVO> list=algorithmService.calAverageCapitalPlusInterestPlan(vo.getTotalAmount(),vo.getPeriod(),2d);
            double interest=0d;
            for (AverageCapitalPlusInterestVO vo1:list){
                interest=MoneyUtil.add(interest, vo1.getPlanInterest()).doubleValue();
            }
            vo.setServiceAmount(interest);
        }
        return ptList;
    }

    @Override
    public int queryPTStagingProductsLoanCount(StagingProductsLoanVO record) {
        return lnLoanMapper.selectPTStagingProductsLoanCount(record);
    }

    @Override
    public List<StagingProductsLoanVO> queryQBStagingProductsLoan(StagingProductsLoanDTO dto) {

        List<StagingProductsLoanVO> voList=lnLoanMapper.selectQBStagingProductsLoan(dto);

        //计算利息
        for (StagingProductsLoanVO vo:voList){

            LnCreditTransferExample example=new LnCreditTransferExample();
            example.createCriteria().andOutLoanRelationIdEqualTo(vo.getLoanRelationId());
            List<LnCreditTransfer> transferList=lnCreditTransferMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(transferList) && transferList.size()>0){
                vo.setTotalAmount(MoneyUtil.add(vo.getTotalAmount(), transferList.get(0).getAmount()).doubleValue());
            }

            List<AverageCapitalPlusInterestVO> list=algorithmService.calAverageCapitalPlusInterestPlan(vo.getTotalAmount(),vo.getPeriod(),2d);
            double interest=0d;

            for (AverageCapitalPlusInterestVO vo1:list){
                interest=MoneyUtil.add(interest, vo1.getPlanInterest()).doubleValue();
            }
            vo.setServiceAmount(interest);
        }

        return voList;
    }

    @Override
    public int queryQBStagingProductsLoanCount(StagingProductsLoanDTO dto) {
        return lnLoanMapper.selectQBStagingProductsLoanCount(dto);
    }

    @Override
    public List<StagingProductsLoanVO> queryHSStagingProductsLoan(StagingProductsLoanVO record) {
        List<StagingProductsLoanVO> hsList = lnLoanMapper.selectHSStagingProductsLoan(record);
        //计算利息
        for (StagingProductsLoanVO vo:hsList){
            LnCreditTransferExample example=new LnCreditTransferExample();
            example.createCriteria().andOutLoanRelationIdEqualTo(vo.getLoanRelationId());
            List<LnCreditTransfer> transferList=lnCreditTransferMapper.selectByExample(example);
            if(CollectionUtils.isNotEmpty(transferList) && transferList.size()>0){
                vo.setTotalAmount(MoneyUtil.add(vo.getTotalAmount(),transferList.get(0).getAmount()).doubleValue());
            }

            List<AverageCapitalPlusInterestVO> list=algorithmService.calAverageCapitalPlusInterestPlan(vo.getTotalAmount(),vo.getPeriod(),1d);
            double interest=0d;
            for (AverageCapitalPlusInterestVO vo1:list){
                interest=MoneyUtil.add(interest, vo1.getPlanInterest()).doubleValue();
            }
            vo.setServiceAmount(interest);
        }
        return hsList;
    }

    @Override
    public int queryHSStagingProductsLoanCount(StagingProductsLoanVO record) {
        return lnLoanMapper.selectHSStagingProductsLoanCount(record);
    }
}
