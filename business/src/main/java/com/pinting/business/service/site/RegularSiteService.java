package com.pinting.business.service.site;

import com.pinting.business.model.BsUser;
import com.pinting.business.model.LnCreditTransfer;
import com.pinting.business.model.LnFinanceRepaySchedule;
import com.pinting.business.model.LnLoan;
import com.pinting.business.model.LnLoanAmountChange;
import com.pinting.business.model.LnLoanRelation;
import com.pinting.business.model.LnUser;
import com.pinting.business.model.vo.*;

import java.util.List;

/**
 * Author:      cyb
 * Date:        2016/8/29
 * Description: 投资理财相关服务
 */
public interface RegularSiteService {

    /**
     * 查询委托计划债权明细列表
     * @param userId        理财人用户ID
     * @param subAccountId  理财人AUTH投资子账户ID
     * @param pageNum       mysql的start数据
     * @param numPerPage    每页显示条数
     * @return
     */
    List<LnLoanRelationVO> queryLnLoanRelationList(int userId, int subAccountId, int pageNum, int numPerPage);

    /**
     * 统计委托计划债权明细总数
     * @param userId        理财人用户ID
     * @param subAccountId  理财人投资子账户ID
     * @return
     */
    int countLnLoanRelation(int userId, int subAccountId);

    /**
     * 理财人还款查询
     * @param loanRelationId    借贷关系ID
     * @return
     */
    List<LnFinanceRepaySchedule> queryRepayList(int loanRelationId);

    /**
     * 根据借贷关系ID查询借款人信息
     * @param loanRelationId 借贷关系ID
     * @return
     */
    LnLoanRelationVO queryLnLoanRelation(int loanRelationId);

    /**
     * 根据借贷关系ID查询站岗户编号
     * @param loanRelationId 借贷关系ID
     * @return
     */
    LnLoanRelationVO queryAuthAccountId(int loanRelationId);

    /**
     * 根据借贷关系ID查询理财人信息
     * @param loanRelationId 借贷关系ID
     * @return
     */
    LnLoanRelationVO queryUserByRelationId(int loanRelationId);

    /**
     * 根据借款用户编号查询对应的理财人信息,以及借款人在该理财人身上的借款金额、借款期限
     * @param lnUserId 借款用户编号
     * @return
     */
    List<LnLoanRelationVO> queryUserByLnUserId(int lnUserId);

    /**
     * 根据站岗子账户ID查询出借受让金额
     * @param authAccountId 站岗子账户ID
     * @return              出借受让金额
     */
    Double queryTotalAmount(int authAccountId);
    /**
     * 根据站岗子账户ID查询剩余出借受让金额
     * @param authAccountId 站岗子账户ID
     * @return              剩余出借受让金额
     */
    public Double queryLeftAmount(int authAccountId);

    /**
     * 根据借款ID查询这一笔借款对应的理财人数据
     * @param loanId
     * @return
     */
    List<LnLoanRelationVO> queryUserByLoanId(int loanId);

    /**
     * 根据根据借款ID查询,如果存在取create_time最早的变动前金额before_amount
     * @param relationId 借贷关系编号
     * @return
     */
    LnLoanAmountChange queryAmountByRelationId(int relationId);

    /**
     * 根据借款表id查询借款年化利率,值为万分之xx
     * @param loanId
     * @return
     */
    Double queryLoanInterestRate(int loanId);

    /**
     * 根据借款表id查询月偿还本息数额、借款基本信息
     * @param loanId
     * @return
     */
    LoanDetailInfoVO queryLoanInfoByLoanId(int loanId);

    /**
     * 根据用户id,和借款表id查询借贷关系表的记录，用来判断借款协议、债权转让的协议loanRelationId是否属于当前登录的用户
     * @param userId
     * @param id
     * @return
     */
    LnLoanRelation queryRecordByUserIdAndId(Integer userId, Integer id);
    
    /**
     * 根据借款人id 查询借款人信息
     * @param lnUserId
     * @return
     */
    LnUser queryLnUserInfoById(int lnUserId);
    
    /**
     * 查询云贷自主放款债权受让人信息
     * @return
     */
    @Deprecated
    BsUser queryYunDaiSelfCreditor();

    


    /**
     * 代偿协议债转列表-根据借款编号查询债权转让列表(最后一笔还款计划)
     * @param partnerLoanId 合作方借款编号
     * @return
     */
    CompensateTransfersVO queryCompensateTransfersByLoanId(String partnerLoanId);



    /**
     * 根据合作方借款信息 查询借款信息-借款id
     * @param partnerLoanId
     * @return
     */
    LnLoan queryLoanByPartnerLoanId(String partnerLoanId);

    /**
     * 代偿-债权转让协议 根据借款ID，借贷关系ID查询这一笔借款中最后一笔还款的债权转让信息
     * @param loanId
     * @param lnLoanRelationId
     * @return
     */
    CompensateTransfersVO selectCompensateTransferInfo(int loanId, int lnLoanRelationId);

    /**
     * 代偿-债权转让通知书 根据借款ID查询债权转让列表
     * @param loanId
     * @return
     */
    List<CompensateTransfersVO> queryCompensateTransferList(int loanId);

    /**
     * 根据子账户id查询存管产品(港湾计划)的债权明细数量(债转次数)
     * @param subAccountId
     * @return
     */
    int queryDepClaimsCountBySubAccountId(int subAccountId);

    /**
     * 分页查询存管产品(港湾计划)的债权明细
     * @param subAccountId
     * @param pageNum
     * @param numPerPage
     * @return
     */
    List<DetailsOfDebtVO> queryDepClaimsListBySubAccountId(int subAccountId, int pageNum, int numPerPage);

    /**
     * 根据借贷关系id 查询未还本金left_amount(未还本金)
     * @param lnLoanRelationId
     * @return
     */
    LnLoanRelation queryNotRepaidPrincipal(int lnLoanRelationId);

    /**
     * 存管港湾产品-借款协议 根据借款编号查询理财人数据
     * @param loanId 借款编号
     * @return
     */
    List<LnLoanRelationVO> queryCustodyFinancialManagement(int loanId);

    /**
     * 根据借款id 查询借款基本信息
     * @param loanId
     * @return
     */
    LnLoan queryLoanInfoById(int loanId);

    /**
     * 根据借贷关系id查询对应的借贷关系表记录
     * @param lnLoanRelationId
     * @return
     */
    LnLoanRelation queryLoanRelationById(int lnLoanRelationId);

    /**
     * 存管港湾产品-借款协议-债权转让记录 根据借款编号查询债权转让记录
     * @param loanId
     * @return
     */
    List<DebtTransferRecordsVO> selectCustodyLoanTransferClaims(int loanId);

    /**
     * 存管港湾产品-债权转让协议 根据借贷关系编号查询债权转让数据
     * @param lnLoanRelationId
     * @return
     */
    BsLoanRelationTransferVO queryCustodyTransferClaims(int lnLoanRelationId);

    /**
     * 根据子账户编号查询转让债权金额
     * @param subAccountId
     * @return
     */
    List<LnCreditTransfer> queryInAmountById(int subAccountId);

    /**
     * 赞时贷借款协议 到期偿还本息数额
     * @param loanId
     * @return
     */
    LoanDetailInfoVO queryZsdPrincipalInterest(int loanId);

    /**
     * 分页查询云贷存管产品的债权明细
     * @param subAccountId
     * @param pageNum
     * @param numPerPage
     * @return
     */
    List<DetailsOfDebtVO> queryDepClaimsListBySubAccountIdNew(int subAccountId, int pageNum, int numPerPage);
    
    /**
     *
     * @param partnerCode
     * @param loanRelationId
     * @return
     */
    BorrowerInfoVO queryBorrowerInfo(Integer userId, String partnerCode, Integer loanRelationId);

    /**
     * 查询7贷自主放款债权受让人信息
     * @return
     */
    @Deprecated
    BsUser query7DaiSelfCreditor();

    /**
     * 赞时贷资金迁移协议 根据loanRelationId查询借款人的资产方
     * @param loanRelationId
     * @return
     */
    CheckLnUserPartnerCodeVO queryCheckLnUserPartnerCode(int loanRelationId);

    /**
     * 代偿-债权转让通知书 根据借款ID查询债权转让列表 代偿协议重新生成合规前的版本数据查询
     * @param loanId
     * @return
     */
    List<CompensateTransfersVO> queryCompensateTransferListRenew(int loanId);

    /**
     * 分期产品代偿协议（确认函，通知书），代偿支付金额
     * @param loanId
     * @return
     */
    List<CompensateTransfersPdfVO> queryCompensateTransfer4StageList(int loanId);
}
