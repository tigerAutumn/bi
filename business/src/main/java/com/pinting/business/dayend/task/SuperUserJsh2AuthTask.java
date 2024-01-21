package com.pinting.business.dayend.task;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.accounting.loan.model.BaseAccount;
import com.pinting.business.accounting.loan.service.LoanAccountService;
import com.pinting.business.accounting.loan.service.LoanRelationshipService;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsSysConfig;
import com.pinting.business.model.vo.BsSubAccountVO;
import com.pinting.business.service.site.SysConfigService;
import com.pinting.business.util.Constants;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


/**
 * 超级理财人当日余额转站岗任务
 * @author bianyatian
 * @2016-8-30 下午5:10:46
 */
@Service
public class SuperUserJsh2AuthTask {
	
	private Logger log = LoggerFactory.getLogger(SuperUserJsh2AuthTask.class);
	
	@Autowired
	private LoanAccountService loanAccountService;
	@Autowired
	private BsSubAccountMapper bsSubAccountMapper;
	@Autowired
	private SysConfigService sysConfigService;
	@Autowired
	private LoanRelationshipService loanRelationshipService;
	
	public void execute() {
		log.info("==================【超级理财人当日余额转站岗任务】 开始 =================");
		try {
			//超级理财人账户
			List<Integer> superUserList = loanRelationshipService.getSuperUserList();
			//超级产品id
			BsSysConfig configPro = sysConfigService.findConfigByKey(Constants.SUPER_PRODUCT_ID);
			Integer productId = null;
			if(configPro != null){
				productId = Integer.parseInt(configPro.getConfValue());
			}
			if(CollectionUtils.isNotEmpty(superUserList) && productId != null){
				//查询超级理财人结算户金额大于0的列表
				List<BsSubAccountVO> subAccountList = bsSubAccountMapper.selectJSHBySuperUser(superUserList);
				if(CollectionUtils.isNotEmpty(subAccountList)){
					for (BsSubAccountVO bsSubAccountVO : subAccountList) {
						BaseAccount baseAccount = new BaseAccount();
						baseAccount.setPartner(PartnerEnum.ZAN);
						baseAccount.setInvestorUserId(bsSubAccountVO.getUserId());
						baseAccount.setAmount(bsSubAccountVO.getBalance());
						loanAccountService.chargeAuthActAdd(baseAccount, productId);
					}
				}
				
			}else{
				log.info("==================【超级理财人当日余额转站岗任务】 超级理财人列表:"+superUserList.size()+"=================");
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.info("==================【超级理财人当日余额转站岗任务】 结束 =================");
	}
	
}
