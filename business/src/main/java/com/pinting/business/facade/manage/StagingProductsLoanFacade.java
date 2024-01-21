package com.pinting.business.facade.manage;

import com.pinting.business.hessian.manage.message.ReqMsg_StagingProductsLoan_QueryHSList;
import com.pinting.business.hessian.manage.message.ReqMsg_StagingProductsLoan_QueryPTList;
import com.pinting.business.hessian.manage.message.ResMsg_StagingProductsLoan_QueryHSList;
import com.pinting.business.hessian.manage.message.ResMsg_StagingProductsLoan_QueryPTList;
import com.pinting.business.model.vo.StagingProductsLoanVO;
import com.pinting.business.service.manage.StagingProductsLoanService;
import com.pinting.business.util.BeanUtils;
import com.pinting.core.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 分期产品出借查询
 * Created by shh on 2016/11/7 20:03.
 */
@Component("StagingProductsLoan")
public class StagingProductsLoanFacade {

    @Autowired
    private StagingProductsLoanService stagingProductsLoanService;

    /**
     * 品听-分期产品出借查询
     * @param req
     * @param res
     */
    public void queryPTList(ReqMsg_StagingProductsLoan_QueryPTList req, ResMsg_StagingProductsLoan_QueryPTList res) {
        int pageNum = req.getPageNum();
        int numPerPage = req.getNumPerPage();
        StagingProductsLoanVO productsLoanVO = new StagingProductsLoanVO();
        productsLoanVO.setPageNum(pageNum);
        productsLoanVO.setNumPerPage(numPerPage);
        if(req.getLoanTimeStart() != null && !"".equals(req.getLoanTimeStart())) {
            productsLoanVO.setLoanTimeStart(req.getLoanTimeStart());
        }
        if(req.getLoanTimeEnd() != null && !"".equals(req.getLoanTimeEnd())) {
            productsLoanVO.setLoanTimeEnd(req.getLoanTimeEnd());
        }
        int totalRows = stagingProductsLoanService.queryPTStagingProductsLoanCount(productsLoanVO);
        if(totalRows > 0) {
            List<StagingProductsLoanVO> resultList = stagingProductsLoanService.queryPTStagingProductsLoan(productsLoanVO);
            ArrayList<HashMap<String, Object>> userList = BeanUtils.classToArrayList(resultList);
            res.setValueList(userList);
        }
        res.setTotalRows(totalRows);
        res.setPageNum(pageNum);
        res.setNumPerPage(numPerPage);
    }

    /**
     * 杭商-分期产品出借查询
     * @param req
     * @param res
     */
    public void queryHSList(ReqMsg_StagingProductsLoan_QueryHSList req, ResMsg_StagingProductsLoan_QueryHSList res) {
        int pageNum = req.getPageNum();
        int numPerPage = req.getNumPerPage();
        StagingProductsLoanVO productsLoanVO = new StagingProductsLoanVO();
        productsLoanVO.setPageNum(pageNum);
        productsLoanVO.setNumPerPage(numPerPage);
        if(req.getLoanTimeStart() != null && !"".equals(req.getLoanTimeStart())) {
            productsLoanVO.setLoanTimeStart(req.getLoanTimeStart());
        }
        if(req.getLoanTimeEnd() != null && !"".equals(req.getLoanTimeEnd())) {
            productsLoanVO.setLoanTimeEnd(req.getLoanTimeEnd());
        }
        int totalRows = stagingProductsLoanService.queryHSStagingProductsLoanCount(productsLoanVO);
        if(totalRows > 0) {
            List<StagingProductsLoanVO> resultList = stagingProductsLoanService.queryHSStagingProductsLoan(productsLoanVO);
            ArrayList<HashMap<String, Object>> userList = BeanUtils.classToArrayList(resultList);
            res.setValueList(userList);
        }
        res.setTotalRows(totalRows);
        res.setPageNum(pageNum);
        res.setNumPerPage(numPerPage);
    }

}
