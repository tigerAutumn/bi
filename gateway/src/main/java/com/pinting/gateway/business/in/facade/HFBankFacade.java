package com.pinting.gateway.business.in.facade;

import com.google.gson.Gson;
import com.pinting.core.util.*;
import com.pinting.gateway.enums.PTMessageEnum;
import com.pinting.gateway.exception.PTMessageException;
import com.pinting.gateway.hessian.message.hfbank.*;
import com.pinting.gateway.hessian.message.hfbank.model.*;
import com.pinting.gateway.hessian.message.hfbank.model.BatchExtRepayResErrorData;
import com.pinting.gateway.hessian.message.hfbank.model.BatchExtRepayResSuccessData;
import com.pinting.gateway.hessian.message.hfbank.model.ChargeOffResData;
import com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonResData;
import com.pinting.gateway.hessian.message.hfbank.model.ProdRepayResData;
import com.pinting.gateway.hessian.message.hfbank.model.PublishResData;
import com.pinting.gateway.hfbank.out.enums.HFErrorCodeEnum;
import com.pinting.gateway.hfbank.out.model.*;
import com.pinting.gateway.hfbank.out.model.BatchExtBuyReqData;
import com.pinting.gateway.hfbank.out.model.BatchExtRepayReqData;
import com.pinting.gateway.hfbank.out.model.BatchWithdrawExtErrorData;
import com.pinting.gateway.hfbank.out.model.BatchWithdrawExtSuccessData;
import com.pinting.gateway.hfbank.out.model.ChargeOffReqDetail;
import com.pinting.gateway.hfbank.out.model.EstablishOrAbandonReqRepayPlan;
import com.pinting.gateway.hfbank.out.model.PublishFinancingInfo;
import com.pinting.gateway.hfbank.out.service.HfbankOutService;
import com.pinting.gateway.util.Constants;
import net.sf.json.JSONObject;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Author:      cyb
 * Date:        2017/4/1
 * Description: 调用恒丰银行资金存管接口
 * 绑卡/充值/提现 记录恒丰和宝付的错误码  规则  'HF'+错误码+',BF'+错误码 
 */
@Component("HFBank")
public class HFBankFacade {

    @Autowired
    private HfbankOutService hfbankOutService;
    private Logger logger = LoggerFactory.getLogger(HFBankFacade.class);


    /* ============================== 浣熊写的接口开始 =============================== */
    /**
     * 1. 批量开户（四要素绑卡）
     * 2. 批量开户（实名认证），针对借款人
     * 3. 批量换卡
     * 4. 短验绑卡申请
     * 5. 短验绑卡确认
     * 6. 网关充值请求
     * 7. 充值回调通知				异步通知，不在此类内
     * 8. 快捷充值请求
     * 9. 快捷充值确认
     * 10. 借款人扣款还款（代扣）
     * 11. 借款人扣款还款（代扣）通知	异步通知，不在此类内
     * 12. 批量提现
     * 13. 提现通知					异步通知，不在此类内
     * 14. 订单状态查询
     * 15. 资金余额查询
     * 16. 账户余额明细查询
     * 17. 标的账户余额查询
     * 18. 资金变动明细查询
     * 19.绑卡
     */

    /**
     * 1. 批量开户（四要素绑卡）
     * @param req
     * @param res
     */
    public void batchBindCard4Elements(B2GReqMsg_HFBank_BatchBindCard4Elements req, B2GResMsg_HFBank_BatchBindCard4Elements res) {
        BatchRegistExtReqModel reqModel = new BatchRegistExtReqModel();
        reqModel.setTotal_num(req.getTotal_num());
        List<BatchRegistExtDetail> list = req.getData();
        List<BatchRegistExtDetailReqModel> modelList = new ArrayList<BatchRegistExtDetailReqModel>();
        if (CollectionUtils.isNotEmpty(list)) {
            for(BatchRegistExtDetail obj : list) {
                BatchRegistExtDetailReqModel batchRegistExtDetailReqModel = new BatchRegistExtDetailReqModel();
                batchRegistExtDetailReqModel.setDetail_no(obj.getDetail_no());
                batchRegistExtDetailReqModel.setName(obj.getName());
                batchRegistExtDetailReqModel.setId_type(obj.getId_type());
                batchRegistExtDetailReqModel.setId_code(obj.getId_code());
                batchRegistExtDetailReqModel.setMobile(obj.getMobile());
                batchRegistExtDetailReqModel.setEmail(obj.getEmail());
                batchRegistExtDetailReqModel.setSex(obj.getSex());
                batchRegistExtDetailReqModel.setCus_type(obj.getCus_type());
                batchRegistExtDetailReqModel.setBirthday(DateUtil.formatDateTime(obj.getBirthday(), "yyyy-MM-dd"));
                batchRegistExtDetailReqModel.setOpen_branch(obj.getOpen_branch());
                batchRegistExtDetailReqModel.setCard_no(obj.getCard_no());
                batchRegistExtDetailReqModel.setCard_type(obj.getCard_type());
                batchRegistExtDetailReqModel.setPre_mobile(obj.getPre_mobile());
                batchRegistExtDetailReqModel.setPay_code(obj.getPay_code());
                batchRegistExtDetailReqModel.setNotify_url(obj.getNotify_url());
                batchRegistExtDetailReqModel.setRemark(obj.getRemark());
                modelList.add(batchRegistExtDetailReqModel);
            }
		}

        reqModel.setData(modelList);

        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        BatchRegistExtResModel resModel = hfbankOutService.batchBindCard4Elements(reqModel);
        String resMsg = resModel.getRemsg();
        String resCode= resModel.getRecode();
        res.setRecode(resModel.getRecode());
        res.setRemsg(resMsg);
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	//当成功条数>=1时返回成功,否则返回失败
        	res.setFinish_datetime(resModel.getFinish_datetime());
        	res.setOrder_info(resModel.getOrder_info());
        	res.setOrder_status(resModel.getOrder_status());
        	res.setTotal_num(resModel.getTotal_num());
        	res.setSuccess_num(resModel.getSuccess_num());
        	//批量注册用户成功
        	List<BatchRegistExtSuccessResModel> succ_data_list = resModel.getSuccess_data();
        	List<BatchRegistExtErrorResModel> err_data_list = resModel.getError_data();
        	
        	List<com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtSuccess> succ_data_modelList = new ArrayList<>();
        	List<com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtError> err_data_modelList = new ArrayList<>();
        	
        	if( CollectionUtils.isNotEmpty(succ_data_list) ) {
        		for( BatchRegistExtSuccessResModel obj : succ_data_list ) {
        			com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtSuccess successData = new com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtSuccess();
        			successData.setDetail_no(obj.getDetail_no());
        			successData.setMobile(obj.getMobile());
        			successData.setPlatcust(obj.getPlatcust());
        			succ_data_modelList.add(successData);
        		}
        	}
        	res.setSuccess_data(succ_data_modelList);
        	if( CollectionUtils.isNotEmpty(err_data_list) ) {
                String error_info = err_data_list.get(0).getError_info();
                String error_no   = err_data_list.get(0).getError_no();
                //对宝付的错误码和错误信息特殊处理,统一替换 
            	int error_no_index		=	error_info.indexOf(Constants.BAOFOO_ERROR_NO);
            	int error_info_index	=	error_info.indexOf(Constants.BAOFOO_ERROR_INFO);
            	String errNo = "";
            	//获取error_no
            	if( error_no_index > 0 ) {
            		errNo	=  error_info.substring(error_no_index+9, error_no_index+16);
            		if( !errNo.startsWith("BF") ) {
            			errNo	=  error_info.substring(error_no_index+9, error_no_index+15);
            		}
            		resCode = "HF"+error_no+","+errNo;
            	}
            	//获取error_info
            	if( error_info_index > 0 ) {
            		resMsg= PTMessageEnum.HFBANK_BATCH_REGIST_EXT_FAIL.getMessage()+","+error_info.substring(error_info_index+11, error_info.length()-1);
            	} else {
            		resMsg= PTMessageEnum.HFBANK_BATCH_REGIST_EXT_FAIL.getMessage()+","+error_info;
            	}
            	//如果宝付错误码匹配,则替换文案
            	if( HFErrorCodeEnum.hfErrorCodeMap.containsKey(errNo) ) {
            		resMsg= PTMessageEnum.HFBANK_BATCH_REGIST_EXT_FAIL.getMessage()+","+HFErrorCodeEnum.hfErrorCodeMap.get(errNo);
            	}
            	
            	for( BatchRegistExtErrorResModel obj : err_data_list ) {
        			com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtError errData = new com.pinting.gateway.hessian.message.hfbank.model.BatchRegistExtError();
        			//转换第三方错误信息
        			errData.setError_no(resCode);
        			errData.setError_info(resMsg);
        			errData.setDetail_no(obj.getDetail_no());
        			err_data_modelList.add(errData);
        		}
                res.setError_data(err_data_modelList);
            	
                throw new PTMessageException( resCode, resMsg );
            }
        } else {
        	if(StringUtil.isNotBlank(resMsg)) {
        		int error_no_index	=	resMsg.indexOf(Constants.BAOFOO_ERROR_NO);
        		int error_info_index=	resMsg.indexOf(Constants.BAOFOO_ERROR_INFO);
        		String errNo	=	"";
        		//获取error_no
            	if( error_no_index > 0 ) {
            		errNo	=  resMsg.substring(error_no_index+9, error_no_index+16);
            		if( !errNo.startsWith("BF") ) {
            			errNo	=  resMsg.substring(error_no_index+9, error_no_index+15);
            		}
            		resCode = "HF"+resCode+","+errNo;
            	}
            	//获取error_info
            	if( error_info_index > 0 ) {
            		resMsg= PTMessageEnum.HFBANK_BATCH_REGIST_EXT_FAIL.getMessage()+","+resMsg.substring(error_info_index+11, resMsg.length()-1);
            	} else {
            		resMsg= PTMessageEnum.HFBANK_BATCH_REGIST_EXT_FAIL.getMessage()+","+resMsg;
            	}
            	//如果宝付错误码匹配,则替换文案
            	if( HFErrorCodeEnum.hfErrorCodeMap.containsKey(errNo) ) {
            		resMsg= PTMessageEnum.HFBANK_BATCH_REGIST_EXT_FAIL.getMessage()+","+HFErrorCodeEnum.hfErrorCodeMap.get(errNo);
            	}
        	} else {
    			resCode	=	"HF"+resCode;
    			resMsg	=	PTMessageEnum.HFBANK_BATCH_REGIST_EXT_FAIL.getMessage()+","+resMsg;
    		}
            throw  new PTMessageException(resCode , resMsg);
        }
    }

    /**
     * 2. 批量开户（实名认证），针对借款人
     * @param req
     * @param res
     */
    public void batchBindCardRealNameAuth(B2GReqMsg_HFBank_BatchBindCardRealNameAuth req, B2GResMsg_HFBank_BatchBindCardRealNameAuth res) {
        BatchRegistExt3ReqModel reqModel = new BatchRegistExt3ReqModel();
        reqModel.setTotal_num(req.getTotal_num());
        List<BatchRegistExt3Detail> list = req.getData();
        List<BatchRegistExt3DetailReqModel> modelList = new ArrayList<BatchRegistExt3DetailReqModel>();
        if (CollectionUtils.isNotEmpty(list)) {
            for(BatchRegistExt3Detail obj : list) {
                BatchRegistExt3DetailReqModel batchRegistExt3DetailReqModel = new BatchRegistExt3DetailReqModel();
                batchRegistExt3DetailReqModel.setDetail_no(obj.getDetail_no());
                batchRegistExt3DetailReqModel.setName(obj.getName());
                batchRegistExt3DetailReqModel.setId_code(obj.getId_code());
                batchRegistExt3DetailReqModel.setMobile(obj.getMobile());
                batchRegistExt3DetailReqModel.setEmail(obj.getEmail());
                batchRegistExt3DetailReqModel.setSex(obj.getSex());
                batchRegistExt3DetailReqModel.setBirthday(DateUtil.formatDateTime(obj.getBirthday(), "yyyy-MM-dd"));
                batchRegistExt3DetailReqModel.setCus_type(obj.getCus_type());
                batchRegistExt3DetailReqModel.setOrg_name(obj.getOrg_name());
                batchRegistExt3DetailReqModel.setBusiness_license(obj.getBusiness_license());
                batchRegistExt3DetailReqModel.setBank_license(obj.getBank_license());
                batchRegistExt3DetailReqModel.setRemark(obj.getRemark());
                modelList.add(batchRegistExt3DetailReqModel);
            }
		}

        reqModel.setData(modelList);
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        BatchRegistExt3ResModel resModel = hfbankOutService.batchBindCardRealNameAuth(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	
        	//当成功条数>=1时返回成功,否则返回失败
        	String succ_num = (StringUtil.isEmpty(resModel.getSuccess_num())?"":resModel.getSuccess_num());
        	if( "".equals(succ_num) ) {
        		res.setResCode(resModel.getRecode());
        		res.setResMsg(StringUtil.isEmpty(resModel.getRemsg())?"成功笔数null,恒丰实名认证失败":resModel.getRemsg());
        	} else {
	        	if( Integer.parseInt(succ_num) >= 1 ) {
	        		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
	        	} else {
	        		res.setResCode(resModel.getRecode());
	        		res.setResMsg(StringUtil.isEmpty(resModel.getRemsg())?"成功笔数0,恒丰实名认证失败":resModel.getRemsg());
	        	}
        	}
            
            res.setPlat_no(resModel.getPlat_no());
            res.setOrder_no(resModel.getOrder_no());
            res.setFinish_datetime(resModel.getFinish_datetime());
            res.setOrder_status(resModel.getOrder_status());
            res.setOrder_info(resModel.getOrder_no());
            res.setTotal_num(resModel.getTotal_num());
            res.setSuccess_num(resModel.getSuccess_num());
            
            List<BatchRegistExt3Success> batchRegistExt3SuccesseList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(resModel.getSuccess_data()) ) {
    	        for (BatchRegistExt3SuccessResModel batchRegistExt3SuccessResModel : resModel.getSuccess_data()) {
    	        	BatchRegistExt3Success batchRegistExt3Success = new BatchRegistExt3Success();
    	        	batchRegistExt3Success.setDetail_no(batchRegistExt3SuccessResModel.getDetail_no());
    	        	batchRegistExt3Success.setMobile(batchRegistExt3SuccessResModel.getMobile());
    	        	batchRegistExt3Success.setPlatcust(batchRegistExt3SuccessResModel.getPlatcust());
    	        	batchRegistExt3SuccesseList.add(batchRegistExt3Success);
    			}
            }
            res.setSuccess_data(batchRegistExt3SuccesseList);
            
            
            List<BatchRegistExt3Error> batchRegistExt3ErrorList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(resModel.getError_data()) ) {
                for (BatchRegistExt3ErrorResModel batchRegistExt3ErrorResModel : resModel.getError_data()) {
                	BatchRegistExt3Error batchRegistExt3Error = new BatchRegistExt3Error();
                	batchRegistExt3Error.setDetail_no(batchRegistExt3ErrorResModel.getDetail_no());
                	batchRegistExt3Error.setError_no(batchRegistExt3ErrorResModel.getError_no());
                	batchRegistExt3Error.setError_info(batchRegistExt3ErrorResModel.getError_info());
                	batchRegistExt3ErrorList.add(batchRegistExt3Error);
        		}
    		}

            res.setError_data(batchRegistExt3ErrorList);
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_BATCH_REGIST_EXT3_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }

        
       
    }

    /**
     * 3. 批量换卡
     * @param req
     * @param res
     */
    public void batchChangeCard(B2GReqMsg_HFBank_BatchChangeCard req, B2GResMsg_HFBank_BatchChangeCard res) {
        BatchUpdateCardExtReqModel reqModel = new BatchUpdateCardExtReqModel();
        List<BatchUpdateCardExtDetail> list = req.getData();
        List<BatchUpdateCardExtDetailReqModel> modelList = new ArrayList<BatchUpdateCardExtDetailReqModel>();
        if (CollectionUtils.isNotEmpty(list)) {
            for(BatchUpdateCardExtDetail obj : list) {
                BatchUpdateCardExtDetailReqModel batchUpdateCardExtDetailReqModel = new BatchUpdateCardExtDetailReqModel();
                batchUpdateCardExtDetailReqModel.setDetail_no(obj.getDetail_no());
                batchUpdateCardExtDetailReqModel.setPlatcust(obj.getPlatcust());
                batchUpdateCardExtDetailReqModel.setCard_no(obj.getCard_no());
                batchUpdateCardExtDetailReqModel.setMobile(obj.getMobile());
                batchUpdateCardExtDetailReqModel.setCard_type(obj.getCard_type());
                batchUpdateCardExtDetailReqModel.setCard_no_old(obj.getCard_no_old());
                batchUpdateCardExtDetailReqModel.setCard_type_old(obj.getCard_type_old());
                batchUpdateCardExtDetailReqModel.setName(obj.getName());
                batchUpdateCardExtDetailReqModel.setPay_code(obj.getPay_code());
                batchUpdateCardExtDetailReqModel.setRemark(obj.getRemark());
                modelList.add(batchUpdateCardExtDetailReqModel);
            }
		}

        reqModel.setData(modelList);
        reqModel.setTotal_num(req.getTotal_num());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        BatchUpdateCardExtResModel resModel = hfbankOutService.batchChangeCard(reqModel);
        
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
            
        	//当成功条数>=1时返回成功,否则返回失败
        	String succ_num = (StringUtil.isEmpty(resModel.getSuccess_num())?"":resModel.getSuccess_num());
        	if( "".equals(succ_num) ) {
        		res.setResCode(resModel.getRecode());
        		res.setResMsg(StringUtil.isEmpty(resModel.getRemsg())?"成功笔数null,恒丰换卡申请失败":resModel.getRemsg());
        	} else {
	        	if( Integer.parseInt(succ_num) >= 1 ) {
	        		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
	        	} else {
	        		res.setResCode(resModel.getRecode());
	        		res.setResMsg(StringUtil.isEmpty(resModel.getRemsg())?"成功笔数0,恒丰换卡申请失败":resModel.getRemsg());
	        	}
        	}
            
            res.setPlat_no(resModel.getPlat_no());
            res.setOrder_no(resModel.getOrder_no());
            res.setFinish_datetime(resModel.getFinish_datetime());
            res.setOrder_status(resModel.getOrder_status());
            res.setOrder_info(resModel.getOrder_no());
            res.setTotal_num(resModel.getTotal_num());
            res.setSuccess_num(resModel.getSuccess_num());
            List<BatchUpdateCardExtSuccess> batchRegistExt3SuccesseList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(resModel.getSuccess_data()) ) {
                for (BatchUpdateCardExtSuccessResModel batchRegistExt3SuccessResModel : resModel.getSuccess_data()) {
                    BatchUpdateCardExtSuccess batchRegistExt3Success = new BatchUpdateCardExtSuccess();
                    batchRegistExt3Success.setDetail_no(batchRegistExt3SuccessResModel.getDetail_no());
                    batchRegistExt3Success.setPlatcust(batchRegistExt3SuccessResModel.getPlatcust());
                    batchRegistExt3SuccesseList.add(batchRegistExt3Success);
                }
            }
            res.setSuccess_data(batchRegistExt3SuccesseList);
            List<BatchUpdateCardExtError> batchRegistExt3ErrorList = new ArrayList<>();
            if (!CollectionUtils.isEmpty(resModel.getError_data()) ) {
                for (BatchUpdateCardExtErrorResModel batchRegistExt3ErrorResModel : resModel.getError_data()) {
                    BatchUpdateCardExtError batchRegistExt3Error = new BatchUpdateCardExtError();
                    batchRegistExt3Error.setDetail_no(batchRegistExt3ErrorResModel.getDetail_no());
                    batchRegistExt3Error.setError_no(batchRegistExt3ErrorResModel.getError_no());
                    batchRegistExt3Error.setError_info(batchRegistExt3ErrorResModel.getError_info());
                    batchRegistExt3ErrorList.add(batchRegistExt3Error);
                }
            }
            res.setError_data(batchRegistExt3ErrorList);
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_BATCH_UPDATE_CARD_EXT_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 4. 短验绑卡申请
     * @param req
     * @param res
     */
    public void userPreBindCard(B2GReqMsg_HFBank_UserPreBindCard req, B2GResMsg_HFBank_UserPreBindCard res) {
        GetBinkCardCode2ReqModel reqModel = new GetBinkCardCode2ReqModel();
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setId_type(req.getId_type());
        reqModel.setId_code(req.getId_code());
        reqModel.setName(req.getName());
        reqModel.setCard_no(req.getCard_no());
        reqModel.setCard_type(req.getCard_type());
        reqModel.setPre_mobile(req.getPre_mobile());
        reqModel.setPay_code(req.getPay_code());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        GetBinkCardCode2ResModel resModel = hfbankOutService.userPreBindCard(reqModel);
        res.setResCode(resModel.getRecode());
        res.setResMsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_GET_BINK_CARD_CODE2_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 5. 短验绑卡确认
     * @param req
     * @param res
     */
    public void userBindCard(B2GReqMsg_HFBank_UserBindCard req, B2GResMsg_HFBank_UserBindCard res) {
        AddUser2ReqModel reqModel = new AddUser2ReqModel();
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setId_type(req.getId_type());
        reqModel.setId_code(req.getId_code());
        reqModel.setName(req.getName());
        reqModel.setCard_no(req.getCard_no());
        reqModel.setCard_type(req.getCard_type());
        reqModel.setPre_mobile(req.getPre_mobile());
        reqModel.setPay_code(req.getPay_code());
        reqModel.setIdentifying_code(req.getIdentifying_code());
        reqModel.setOrigin_order_no(req.getOrigin_order_no());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        AddUser2ResModel resModel = hfbankOutService.userBindCard(reqModel);
        
        res.setResCode(resModel.getRecode());
        res.setResMsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
            res.setPlatcust(resModel.getPlatcust());
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_ADD_USER2_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 6. 网关充值请求
     * @param req
     * @param res
     */
    public void userGatewayRechargeRequest(B2GReqMsg_HFBank_UserGatewayRechargeRequest req, B2GResMsg_HFBank_UserGatewayRechargeRequest res) {
        GatewayRechargeRequestReqModel reqModel = new GatewayRechargeRequestReqModel();
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setType(req.getType());
        reqModel.setCharge_type(req.getCharge_type());
        reqModel.setBankcode(req.getBankcode());
        reqModel.setCard_type(req.getCard_type());
        reqModel.setCurrency_code(req.getCurrency_code());
        reqModel.setCard_no(req.getCard_no());
        reqModel.setAmt(MoneyUtil.defaultRound(req.getAmt()).toString());
        reqModel.setReturn_url(req.getReturn_url());
        reqModel.setPay_code(req.getPay_code());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setNotify_url(GlobEnvUtil.get("hfbank.ebank.topup.notify.url"));
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        GatewayRechargeRequestResModel resModel = hfbankOutService.userGatewayRechargeRequest(reqModel);
        
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
	        BeanUtils.copyProperties(resModel, res);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_GATEWAY_RECHARGE_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 8. 快捷充值请求
     * @param req
     * @param res
     */
    public void userQuickPayPreRecharge(B2GReqMsg_HFBank_UserQuickPayPreRecharge req, B2GResMsg_HFBank_UserQuickPayPreRecharge res) {
        DirectQuickRequestReqModel reqModel = new DirectQuickRequestReqModel();
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setName(req.getName());
        reqModel.setCard_no(req.getCard_no());
        reqModel.setCard_type(req.getCard_type());
        reqModel.setCurrency_code(req.getCurrency_code());
        reqModel.setId_type(req.getId_type());
        reqModel.setId_code(req.getId_code());
        reqModel.setMobile(req.getMobile());
        reqModel.setEmail(req.getEmail());
        reqModel.setAmt(MoneyUtil.defaultRound(req.getAmt()).toString());
        reqModel.setPay_code(req.getPay_code());
        reqModel.setAccount_type(req.getAccount_type());
        reqModel.setNotify_url(req.getNotify_url());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setNotify_url(GlobEnvUtil.get("hfbank.ebank.topup.notify.url"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        DirectQuickRequestResModel resModel = hfbankOutService.userQuickPayPreRecharge(reqModel);
        String resMsg = resModel.getRemsg();
        String reCode = resModel.getRecode();
        res.setRecode(resModel.getRecode());
        res.setRemsg(resMsg);
        
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	if( resModel.getData() != null ) {
        		String status = StringUtil.isEmpty(resModel.getData().getOrder_status())?"":resModel.getData().getOrder_status();
        		if( "2".equals(status) ) {
        			res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        		} else {
        			res.setResCode(resModel.getRecode());
        		}
        		DirectQuickRequestDataResModel quickRequestDataResModel = new DirectQuickRequestDataResModel();
        		quickRequestDataResModel.setOrder_no(resModel.getData().getOrder_no());
        		quickRequestDataResModel.setOrder_status(resModel.getData().getOrder_status());
        		quickRequestDataResModel.setProcess_date(resModel.getData().getProcess_date());
        		quickRequestDataResModel.setQuery_id(resModel.getData().getQuery_id());
        		quickRequestDataResModel.setHost_req_serial_no(resModel.getData().getHost_req_serial_no());//设置支付通道流水号
        		res.setData(quickRequestDataResModel);
        	}
        } else {
            if(StringUtil.isNotBlank(resMsg)) {
            	
            	//对宝付的错误码和错误信息特殊处理,统一替换 
            	int error_no_index		=	resMsg.indexOf(Constants.BAOFOO_ERROR_NO);
            	int error_info_index	=	resMsg.indexOf(Constants.BAOFOO_ERROR_INFO);
            	String errNo	=  "";
            	//获取error_no
            	if( error_no_index > 0 ) {
            		errNo	=  resMsg.substring(error_no_index+9, error_no_index+16);
            		if( !errNo.startsWith("BF") ) {
            			errNo	=  resMsg.substring(error_no_index+9, error_no_index+15);
            		}
            		reCode = "HF"+reCode+","+errNo;
            	}
            	//获取error_info
            	if( error_info_index > 0 ) {
            		resMsg= PTMessageEnum.HFBANK_DIRECT_QUICK_FAIL.getMessage()+","+resMsg.substring(error_info_index+11, resMsg.length()-1);
            	} else {
            		resMsg= PTMessageEnum.HFBANK_DIRECT_QUICK_FAIL.getMessage()+","+resMsg;
            	}
            	//如果宝付错误码匹配,则替换文案
            	if( HFErrorCodeEnum.hfErrorCodeMap.containsKey(errNo) ) {
            		resMsg= PTMessageEnum.HFBANK_DIRECT_QUICK_FAIL.getMessage()+","+HFErrorCodeEnum.hfErrorCodeMap.get(errNo);
            	}
        	} else {
        		reCode = "HF" +reCode;
        		resMsg = PTMessageEnum.HFBANK_DIRECT_QUICK_FAIL.getMessage()+","+resMsg; 
        	}
            throw new PTMessageException( reCode, resMsg );
        }
    }

    /**
     * 9. 快捷充值确认
     * @param req
     * @param res
     */
    public void userQuickPayConfirmRecharge(B2GReqMsg_HFBank_UserQuickPayConfirmRecharge req, B2GResMsg_HFBank_UserQuickPayConfirmRecharge res) {
        DirectQuickConfirmReqModel reqModel = new DirectQuickConfirmReqModel();
        reqModel.setIdentifying_code(req.getIdentifying_code());
        reqModel.setOrigin_order_no(req.getOrigin_order_no());
        reqModel.setPay_code(req.getPay_code());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        DirectQuickConfirmResModel resModel = hfbankOutService.userQuickPayConfirmRecharge(reqModel);
        String resMsg = resModel.getRemsg();
        String reCode = resModel.getRecode();
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
	        
        	if (resModel.getData() != null &&("2".equals(resModel.getData().getOrder_status())) ) {
                logger.info("成功：订单状态，{}，订单号：{}", resModel.getData().getOrder_status(), reqModel.getOrigin_order_no());
        		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);

        		DirectQuickConfirmDataResModel directQuickConfirmDataResModel = new DirectQuickConfirmDataResModel(); 
        		directQuickConfirmDataResModel.setOrder_no(resModel.getData().getOrder_no());
        		directQuickConfirmDataResModel.setOrder_status(resModel.getData().getOrder_status());
        		directQuickConfirmDataResModel.setProcess_date(resModel.getData().getProcess_date());
        		directQuickConfirmDataResModel.setQuery_id(resModel.getData().getQuery_id());
        		res.setData(directQuickConfirmDataResModel);
			} else if(resModel.getData() != null && ("0".equals(resModel.getData().getOrder_status()) || "1".equals(resModel.getData().getOrder_status()))) {
                logger.info("处理中：订单状态{}，订单号：{}", resModel.getData().getOrder_status(), reqModel.getOrigin_order_no());
                res.setResCode("920003"); // 处理中
                DirectQuickConfirmDataResModel directQuickConfirmDataResModel = new DirectQuickConfirmDataResModel();
                directQuickConfirmDataResModel.setOrder_no(resModel.getData().getOrder_no());
                directQuickConfirmDataResModel.setOrder_status(resModel.getData().getOrder_status());
                directQuickConfirmDataResModel.setProcess_date(resModel.getData().getProcess_date());
                directQuickConfirmDataResModel.setQuery_id(resModel.getData().getQuery_id());
                res.setData(directQuickConfirmDataResModel);
                throw new PTMessageException(PTMessageEnum.BUS_PROCESSING);
            } else if (resModel.getData() != null && "3".equals(resModel.getData().getOrder_status())) {
                logger.info("失败：订单状态，{}，订单号：{}", resModel.getData().getOrder_status(), reqModel.getOrigin_order_no());
                reCode = "HF"+reCode;
                resMsg  = PTMessageEnum.HFBANK_DIRECT_QUICK_CONFIRM_FAIL.getMessage()+"	"+resMsg;
              	throw new PTMessageException( reCode, resMsg );
			} else {
                logger.info("失败：订单状态，{}，订单号：{}", resModel.getData().getOrder_status(), reqModel.getOrigin_order_no());
                reCode = "HF"+reCode;
                resMsg  = PTMessageEnum.HFBANK_DIRECT_QUICK_CONFIRM_FAIL.getMessage()+"	"+resMsg;
                throw new PTMessageException( reCode, resMsg );
			}
        }else {
        	if(StringUtil.isNotBlank(resMsg)) {
            	//对宝付的错误码和错误信息特殊处理,统一替换 
            	int error_no_index		=	resMsg.indexOf(Constants.BAOFOO_ERROR_NO);
            	int error_info_index	=	resMsg.indexOf(Constants.BAOFOO_ERROR_INFO);
            	String errNo	=	"";
            	//获取error_no
            	if( error_no_index > 0 ) {
            		errNo	=  resMsg.substring(error_no_index+9, error_no_index+16);
            		if( !errNo.startsWith("BF") ) {
            			errNo	=  resMsg.substring(error_no_index+9, error_no_index+15);
            		}
            		reCode = "HF"+reCode+","+errNo;
            	}
            	//获取error_info
            	if( error_info_index > 0 ) {
            		resMsg= PTMessageEnum.HFBANK_DIRECT_QUICK_CONFIRM_FAIL.getMessage()+","+resMsg.substring(error_info_index+11, resMsg.length()-1);
            	} else {
            		resMsg= PTMessageEnum.HFBANK_DIRECT_QUICK_CONFIRM_FAIL.getMessage()+","+resMsg;
            	}
            	//如果宝付错误码匹配,则替换文案
            	if( HFErrorCodeEnum.hfErrorCodeMap.containsKey(errNo) ) {
            		resMsg= PTMessageEnum.HFBANK_DIRECT_QUICK_CONFIRM_FAIL.getMessage()+","+HFErrorCodeEnum.hfErrorCodeMap.get(errNo);
            	}
        	} else {
        		reCode = "HF"+reCode;
        		resMsg = PTMessageEnum.HFBANK_DIRECT_QUICK_CONFIRM_FAIL.getMessage()+","+resMsg;
        	}
        	throw new PTMessageException( reCode, resMsg );
        }
    }

    /**
     * 10. 借款人扣款还款（代扣）
     * @param req
     * @param res
     */
    public void borrowCutRepayDK(B2GReqMsg_HFBank_BorrowCutRepayDK req, B2GResMsg_HFBank_BorrowCutRepayDK res) {
        BorrowCutRepayReqModel reqModel = new BorrowCutRepayReqModel();
        reqModel.setAmt(MoneyUtil.defaultRound(new BigDecimal(req.getAmt())).toString());
        reqModel.setNotify_url(req.getNotify_url());
        reqModel.setRemark(req.getRemark());
        List<BorrowCutRepayPlatcust> list = req.getPlatcustList();
        List<BorrowCutRepayPlatcustReqModel> modelList = new ArrayList<BorrowCutRepayPlatcustReqModel>();
        if (CollectionUtils.isNotEmpty(list)) {
            for(BorrowCutRepayPlatcust obj : list) {
                BorrowCutRepayPlatcustReqModel borrowCutRepayPlatcustReqModel = new BorrowCutRepayPlatcustReqModel();
                borrowCutRepayPlatcustReqModel.setPlatcust(obj.getPlatcust());
                borrowCutRepayPlatcustReqModel.setAmt(MoneyUtil.defaultRound(new BigDecimal(obj.getAmt())).toString());
                modelList.add(borrowCutRepayPlatcustReqModel);
            }
		}

        reqModel.setPlatcustList(modelList);
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setNotify_url(GlobEnvUtil.get("hfbank.borrow.cutrepay.notify.url"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        BorrowCutRepayResModel resModel = hfbankOutService.borrowCutRepayDK(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
	        res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_BORROW_CUT_REPAY_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 12. 批量提现
     * @param req
     * @param res
     */
    public void userBatchWithdraw(B2GReqMsg_HFBank_UserBatchWithdraw req, B2GResMsg_HFBank_UserBatchWithdraw res) {
        BatchWithdrawExtReqModel reqModel = new BatchWithdrawExtReqModel();
        List<BatchWithdrawExtData> list = req.getData();
        List<BatchWithdrawExtDataReqModel> modelList = new ArrayList<>();
        String notifyUrl = GlobEnvUtil.get("hfbank.ebank.withdraw.notify.url");//设置回调通知地址
        if (CollectionUtils.isNotEmpty(list)) {
            for(BatchWithdrawExtData obj : list) {
                BatchWithdrawExtDataReqModel batchWithdrawExtDataReqModel = new BatchWithdrawExtDataReqModel();
                batchWithdrawExtDataReqModel.setDetail_no(obj.getDetail_no());
                batchWithdrawExtDataReqModel.setPlatcust(obj.getPlatcust());
                batchWithdrawExtDataReqModel.setAmt(MoneyUtil.defaultRound(new BigDecimal(obj.getAmt())).toString());
                batchWithdrawExtDataReqModel.setIs_advance(obj.getIs_advance());
                batchWithdrawExtDataReqModel.setPay_code(obj.getPay_code());
                batchWithdrawExtDataReqModel.setFee_amt(MoneyUtil.defaultRound(new BigDecimal(obj.getFee_amt())).toString());
                batchWithdrawExtDataReqModel.setType(obj.getType());
                batchWithdrawExtDataReqModel.setWithdraw_type(obj.getWithdraw_type());
                batchWithdrawExtDataReqModel.setBank_id(obj.getBank_id());
                batchWithdrawExtDataReqModel.setNotify_url(notifyUrl);
                batchWithdrawExtDataReqModel.setRemark(obj.getRemark());
                batchWithdrawExtDataReqModel.setClient_property(req.getClient_property());
                batchWithdrawExtDataReqModel.setCity_code(req.getCity_code());
                batchWithdrawExtDataReqModel.setBank_name(obj.getBank_name());
                batchWithdrawExtDataReqModel.setBank_code(obj.getBank_code());
                batchWithdrawExtDataReqModel.setTran_type(obj.getTran_type());
                modelList.add(batchWithdrawExtDataReqModel);
            }
		}
        
        reqModel.setData(modelList);
        reqModel.setTotal_num(req.getTotal_num());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        //批量提现受理结果处理转换
        BatchWithdrawExtResModel resModel = hfbankOutService.userBatchWithdraw(reqModel);
        String reCode = resModel.getRecode();
        String resMsg  = resModel.getRemsg();
        reCode = "HF"+reCode;
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if(Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())) {
        	
        	//当成功条数>=1时返回成功,否则返回失败
        	String succ_num = (StringUtil.isEmpty(resModel.getSuccess_num())?"":resModel.getSuccess_num());
        	if( "".equals(succ_num) ) {
        		throw  new PTMessageException(reCode, StringUtil.isEmpty(resModel.getRemsg())?"成功笔数ulln,恒丰提现失败":resModel.getRemsg());
        	} else {
	        	if( Integer.parseInt(succ_num) < 1 ) {
	        		throw  new PTMessageException(reCode, StringUtil.isEmpty(resModel.getRemsg())?"成功笔数0,恒丰提现失败":resModel.getRemsg());
	        	}
        	}
        	
        	res.setFinish_datetime(resModel.getFinish_datetime());
        	res.setOrder_info(resModel.getOrder_info());
        	res.setOrder_status(resModel.getOrder_status());
        	res.setTotal_num(resModel.getTotal_num());
        	res.setSuccess_num(resModel.getSuccess_num());
        	//批量提现受理成功
        	List<BatchWithdrawExtSuccessData> succ_data_list = resModel.getSuccess_data();//detail_no、platcust、amt
        	List<BatchWithdrawExtErrorData> err_data_list = resModel.getError_data();	//detail_no、error_no、error_info
        	List<com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtSuccessData> succ_data_modelList = new ArrayList<>();
        	List<com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtErrorData> err_data_modelList = new ArrayList<>();
        	if( CollectionUtils.isNotEmpty(succ_data_list) ) {
        		for(BatchWithdrawExtSuccessData obj : succ_data_list) {
        			com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtSuccessData successData = new com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtSuccessData();
        			successData.setAmt(obj.getAmt());
        			successData.setDetail_no(obj.getDetail_no());
        			successData.setPlatcust(obj.getPlatcust());
        			succ_data_modelList.add(successData);
        		}
        	}
        	res.setSuccess_data(succ_data_modelList);
        	if( CollectionUtils.isNotEmpty(err_data_list) ) {
        		for(BatchWithdrawExtErrorData obj :	err_data_list) {
        			com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtErrorData errData = new com.pinting.gateway.hessian.message.hfbank.model.BatchWithdrawExtErrorData();
        			errData.setDetail_no(obj.getDetail_no());
        			errData.setError_info(obj.getError_info());
        			errData.setError_no(obj.getError_no());
        			err_data_modelList.add(errData);
        		}
        	}
        	res.setError_data(err_data_modelList);
        } else if(PTMessageEnum.BUS_PROCESSING.getCode().equals(resModel.getRecode())) {
        	throw  new PTMessageException(PTMessageEnum.BUS_PROCESSING.getCode(), (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        } else {
        	//提现受理失败,则记录受理失败原因
    		resMsg	=	PTMessageEnum.HFBANK_BATCH_WITHDRAW_EXT_FAIL.getMessage()+","+resMsg;
            throw  new PTMessageException(reCode , resMsg);
        }
    }

    /**
     * 14. 订单状态查询
     * @param req
     * @param res
     */
    public void queryOrder(B2GReqMsg_HFBank_QueryOrder req, B2GResMsg_HFBank_QueryOrder  res) {
        QueryOrderReqModel reqModel = new QueryOrderReqModel();
        reqModel.setQuery_order_no(req.getQuery_order_no());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        QueryOrderResModel resModel = hfbankOutService.queryOrder(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	QueryOrderData orderData = new QueryOrderData();
        	orderData.setPlat_no(resModel.getData().getPlat_no());
        	orderData.setQuery_order_no(resModel.getData().getQuery_order_no());
        	orderData.setStatus(resModel.getData().getStatus());
        	res.setData(orderData);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_QUERY_ORDER_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 15. 资金余额查询
     * @param req
     * @param res
     */
    public void queryAccountInfo(B2GReqMsg_HFBank_QueryAccountInfo req, B2GResMsg_HFBank_QueryAccountInfo res) {
        GetAccountInfoReqModel reqModel = new GetAccountInfoReqModel();
        reqModel.setAccount(req.getAccount());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        GetAccountInfoResModel resModel = hfbankOutService.queryAccountInfo(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	GetAccountInfoDataResModel  getAccountInfoDataResModel = new GetAccountInfoDataResModel();
        	getAccountInfoDataResModel.setBalance(resModel.getData().getBalance());
        	res.setData(getAccountInfoDataResModel); 
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_QUERY_ACCOUNT_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 16. 账户余额明细查询
     * @param req
     * @param res
     */
    public void queryAccountLeftAmountInfo(B2GReqMsg_HFBank_QueryAccountLeftAmountInfo req, B2GResMsg_HFBank_QueryAccountLeftAmountInfo res) {
        GetAccountNBalaceReqModel reqModel = new GetAccountNBalaceReqModel();
        reqModel.setAccount(req.getAccount());
        reqModel.setAcct_type(req.getAcct_type());
        reqModel.setFund_type(req.getFund_type());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        GetAccountNBalaceResModel resModel = hfbankOutService.queryAccountLeftAmountInfo(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	res.setData(resModel.getData());
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_GET_ACCOUNT_NBALACE_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 17. 标的账户余额查询
     * @param req
     * @param res
     */
    public void queryProductBalance(B2GReqMsg_HFBank_QueryProductBalance req, B2GResMsg_HFBank_QueryProductBalance res) {
        GetProductNBalanceReqModel reqModel = new GetProductNBalanceReqModel();
        reqModel.setProd_id(req.getProd_id());
        reqModel.setType(req.getType());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        GetProductNBalanceResModel resModel = hfbankOutService.queryProductBalance(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	res.setData(resModel.getData());
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_QUERY_PRODUCT_BALANCE_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }
    /**
     * 18. 资金变动明细查询
     * @param req
     * @param res
     */
    public void getFundList(B2GReqMsg_HFBank_GetFundList req, B2GResMsg_HFBank_GetFundList res) {
        GetFundListReqModel reqModel = new GetFundListReqModel();
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setPagenum(req.getPagenum());
        reqModel.setPagesize(req.getPagesize());
        reqModel.setStart_date(DateUtil.formatDateTime(req.getStart_date(), "yyyy-MM-dd HH:mm:ss"));
        reqModel.setEnd_date(DateUtil.formatDateTime(req.getEnd_date(), "yyyy-MM-dd HH:mm:ss"));
        reqModel.setTrans_name(req.getTrans_name());
        
        GetFundListResModel resModel = hfbankOutService.getFundList(reqModel);
        
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	
        	//返回数据处理
        	List<GetFundListInfoData> fundList = resModel.getData().getFundList();
        	
        	List<com.pinting.gateway.hessian.message.hfbank.model.GetFundListInfoData> fundDataList_tmp = new ArrayList<com.pinting.gateway.hessian.message.hfbank.model.GetFundListInfoData>();
        	GetFundListDetailData detailData = new GetFundListDetailData();
        	
        	if( CollectionUtils.isNotEmpty(fundList) ) {
        		for( GetFundListInfoData obj : fundList ) {
        			com.pinting.gateway.hessian.message.hfbank.model.GetFundListInfoData fundData = new com.pinting.gateway.hessian.message.hfbank.model.GetFundListInfoData();
        			fundData.setAmt(obj.getAmt());
        			fundData.setAmt_type(obj.getAmt_type());
        			fundData.setPlat_no(obj.getPlat_no());
        			fundData.setPlat_no(obj.getPlat_no());
        			fundData.setPlatcust(obj.getPlatcust());
        			fundData.setTrans_date(obj.getTrans_date());
        			fundData.setTrans_time(obj.getTrans_time());
        			fundData.setTrans_name(obj.getTrans_name());
        			fundDataList_tmp.add(fundData);
        		}
        	}
        	detailData.setFundList(fundDataList_tmp);
        	res.setData(detailData);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_GET_FUNDLIST_FAIL, (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }
    
    /**
     * 19.绑卡
     * @param req
     * @param res
     * */
    public void userAddCard(B2GReqMsg_HFBank_UserAddCard req, B2GResMsg_HFBank_UserAddCard res) {
    	
        GetBinkCardInfoReqModel reqModel = new GetBinkCardInfoReqModel();
        
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setType(req.getType());
        reqModel.setId_type(req.getId_type());
        reqModel.setId_code(req.getId_code());
        reqModel.setName(req.getName());
        reqModel.setCard_no(req.getCard_no());
        reqModel.setCard_type(req.getCard_type());
        reqModel.setPre_mobile(req.getPre_mobile());
        reqModel.setOrg_no(req.getOrg_no());
        reqModel.setAcct_name(req.getAcct_name());
        reqModel.setAcct_no(req.getAcct_no());
        reqModel.setOpen_branch(req.getOpen_branch());
        reqModel.setPay_code(req.getPay_code());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        GetBinkCardInfoResModel resModel = hfbankOutService.addBankCard(reqModel);
        String resMsg = resModel.getRemsg();
        String reCode = resModel.getRecode();
        if ( Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode()) ) {
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
        	//对宝付错误码以及error_info信息特殊处理
        	if(StringUtil.isNotBlank(resMsg)) {
        		int error_no_index	=	resMsg.indexOf(Constants.BAOFOO_ERROR_NO);
        		int error_info_index	=	resMsg.indexOf(Constants.BAOFOO_ERROR_INFO);
        		String errNo = "";
        		//获取error_no
            	if( error_no_index > 0 ) {
            		errNo	=  resMsg.substring(error_no_index+9, error_no_index+16);
            		if( !errNo.startsWith("BF") ) {
            			errNo	=  resMsg.substring(error_no_index+9, error_no_index+15);
            		}
            		reCode = "HF"+reCode+","+errNo;
            	}
            	//获取error_info
            	if( error_info_index > 0 ) {
            		resMsg= PTMessageEnum.HFBANK_BIND_CARD_FAIL.getMessage()+","+resMsg.substring(error_info_index+11, resMsg.length()-1);
            	} else {
            		resMsg= PTMessageEnum.HFBANK_BIND_CARD_FAIL.getMessage()+","+resMsg;
            	}
            	//如果宝付错误码匹配,则替换文案
            	if( HFErrorCodeEnum.hfErrorCodeMap.containsKey(errNo) ) {
            		resMsg= PTMessageEnum.HFBANK_BIND_CARD_FAIL.getMessage()+","+HFErrorCodeEnum.hfErrorCodeMap.get(errNo);
            	}
        	} else {
        		reCode = "HF"+reCode;
        		resMsg = PTMessageEnum.HFBANK_BIND_CARD_FAIL.getMessage()+","+resMsg;
        	}
        	throw new PTMessageException(reCode, resMsg);
        }
    }


    public void updatePlatAcct(B2GReqMsg_HFBank_UpdatePlatAcct req, B2GResMsg_HFBank_UpdatePlatAcct res) {
        UpdatePlatAcctReqModel reqModel = new UpdatePlatAcctReqModel();

        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setPlat_no(req.getPlat_no());
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setCus_type(req.getCus_type());
        reqModel.setEmail(req.getEmail());
        reqModel.setMobile(req.getMobile());
        reqModel.setOrg_name(req.getOrg_name());
        reqModel.setBusiness_license(req.getBusiness_license());
        reqModel.setBank_license(req.getBank_license());
        reqModel.setNotify_url(req.getNotify_url());
        reqModel.setSigndata(req.getSigndata());

        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        UpdatePlatAcctResModel resModel = hfbankOutService.updatePlatAcct(reqModel);
        String resMsg = resModel.getRemsg();
        String reCode = resModel.getRecode();
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode()) ) {
            res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw new PTMessageException(PTMessageEnum.HFBANK_UPDATE_PLAT_ACCT_FAIL.getCode(), resMsg);
        }
    }

    /* ============================== 浣熊写的接口结束 =============================== */


    /* ============================== 龙猫写的接口开始 =============================== */
    /**
     * 1、标的发布
     * 2、标的成废
     * 3、批量投标
     * 4、标的出账
     * 5、标的出账信息修改
     * 6、成标出账通知       异步通知
     * 7、标的转让
     * 8、借款人批量还款
     * 9、标的还款
     * 10、平台转账个人
     * 11、平台不同账户转账
     * 12、标的代偿（委托）还款
     * 13、借款人还款代偿（委托）
     * 14、平台提现
     * 15、平台充值
     */

    /**
     * 1. 标的发布
     * @param req
     * @param res
     */
    public void publish(B2GReqMsg_HFBank_Publish req, B2GResMsg_HFBank_Publish res) {
    	PublishReqModel reqModel = new PublishReqModel();
        reqModel.setProd_id(req.getProd_id());
        reqModel.setProd_name(req.getProd_name());
        reqModel.setProd_type(req.getProd_type());
        reqModel.setTotal_limit(MoneyUtil.defaultRound(req.getTotal_limit()).toString());
        reqModel.setValue_type(req.getValue_type());
        reqModel.setCreate_type(req.getCreate_type());
        reqModel.setSell_date(req.getSell_date());
        reqModel.setValue_date(req.getValue_date());
        reqModel.setExpire_date(req.getExpire_date());
        reqModel.setCycle(req.getCycle());
        reqModel.setCycle_unit(req.getCycle_unit());
        reqModel.setIst_year(MoneyUtil.divide(req.getIst_year(),100,5).doubleValue() + "");//保留5位
        reqModel.setRepay_type(req.getRepay_type());
        List<com.pinting.gateway.hessian.message.hfbank.model.PublishFinancingInfo> list = req.getFinancing_info_list();
        List<PublishFinancingInfo> modelList = new ArrayList<PublishFinancingInfo>();
        if (CollectionUtils.isNotEmpty(list)) {
        	for(com.pinting.gateway.hessian.message.hfbank.model.PublishFinancingInfo obj : list) {
                PublishFinancingInfo publishFinancingInfo = new PublishFinancingInfo();
                publishFinancingInfo.setCust_no(obj.getCust_no());
                publishFinancingInfo.setReg_date(DateUtil.formatDateTime(obj.getReg_date(), "yyyy-MM-dd"));
                publishFinancingInfo.setReg_time(DateUtil.formatDateTime(obj.getReg_time(), "HH:mm:ss"));
                publishFinancingInfo.setFinanc_int(obj.getFinanc_int());
                publishFinancingInfo.setFee_int(MoneyUtil.defaultRound(obj.getFee_int()).toString());
                publishFinancingInfo.setFinanc_purpose(obj.getFinanc_purpose());
                publishFinancingInfo.setUse_date(DateUtil.formatDateTime(obj.getUse_date(), "yyyy-MM-dd"));
                publishFinancingInfo.setOpen_branch(obj.getOpen_branch());
                publishFinancingInfo.setWithdraw_account(obj.getWithdraw_account());
                publishFinancingInfo.setAccount_type(obj.getAccount_type());
                publishFinancingInfo.setPayee_name(obj.getPayee_name());
                publishFinancingInfo.setFinanc_amt(MoneyUtil.defaultRound(new BigDecimal(obj.getFinanc_amt())).toString());
                modelList.add(publishFinancingInfo);
            }
		}
        
        reqModel.setFinancing_info_list(modelList);
        
        List<com.pinting.gateway.hessian.message.hfbank.model.CompensationInfo> list2 = req.getcompensation_info_list();
        if( CollectionUtils.isNotEmpty(list2) ) {
        	List<CompensationInfo> modelList2 = new ArrayList<CompensationInfo>();
            for( com.pinting.gateway.hessian.message.hfbank.model.CompensationInfo obj : list2 ) {
            	CompensationInfo compInfo = new CompensationInfo();
            	compInfo.setPlatcust(obj.getPlatcust());
            	compInfo.setType(obj.getType());
            	modelList2.add(compInfo);
            }
            reqModel.setCompensation(modelList2);
        }
        reqModel.setRisk_lvl(req.getRisk_lvl());
        reqModel.setProd_info(req.getProd_info());
        Integer buyerNum = req.getBuyer_num_limit();
        buyerNum = (buyerNum!=null && buyerNum!=0 ? buyerNum : 10000);
        reqModel.setBuyer_num_limit(buyerNum);
        reqModel.setChargeOff_auto(req.getChargeOff_auto());
        reqModel.setOverLimit(req.getOverLimit());
        reqModel.setOver_total_limit(MoneyUtil.defaultRound(req.getOver_total_limit()).toString());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));


        PublishResModel resModel = hfbankOutService.publish(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
            PublishResData  data = new PublishResData();
            data.setProd_id(resModel.getData().getProd_id());
            res.setData(data);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_PUBLISH_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }


    }

    /**
     * 2、标的成废
     * @param req
     * @param res
     */
    public void establishOrAbandon(B2GReqMsg_HFBank_EstablishOrAbandon req, B2GResMsg_HFBank_EstablishOrAbandon res) {
    	EstablishOrAbandonReqModel reqModel = new EstablishOrAbandonReqModel();
        reqModel.setProd_id(req.getProd_id());
        reqModel.setFlag(req.getFlag());
        
        List<com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqFundData> dataList = req.getFunddata();
        if (CollectionUtils.isNotEmpty(dataList)) {
        	com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqFundData establishOrAbandonReqFundData = dataList.get(0);
        	Gson gson = new Gson();
        	reqModel.setFunddata(JSONObject.fromObject(gson.toJson(establishOrAbandonReqFundData)));
        }
        
        
        List<com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqRepayPlan> planList = req.getRepay_plan_list();
        List<EstablishOrAbandonReqRepayPlan> repayList = new ArrayList<EstablishOrAbandonReqRepayPlan>();
        if (CollectionUtils.isNotEmpty(planList)) {
            for(com.pinting.gateway.hessian.message.hfbank.model.EstablishOrAbandonReqRepayPlan planObj : planList) {
                EstablishOrAbandonReqRepayPlan establishOrAbandonReqRepayPlan = new EstablishOrAbandonReqRepayPlan();
                establishOrAbandonReqRepayPlan.setRepay_amt(planObj.getRepay_amt());
                establishOrAbandonReqRepayPlan.setRepay_fee(planObj.getRepay_fee());
                establishOrAbandonReqRepayPlan.setRepay_num(planObj.getRepay_num());
                establishOrAbandonReqRepayPlan.setRepay_date(DateUtil.formatDateTime(planObj.getRepay_date(), "yyyy-MM-dd"));
                repayList.add(establishOrAbandonReqRepayPlan);
            }
		}

        reqModel.setRepay_plan_list(repayList);

        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setRemark(req.getRemark());
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        EstablishOrAbandonResModel resModel = hfbankOutService.establishOrAbandon(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	EstablishOrAbandonResData establishOrAbandonResData = new EstablishOrAbandonResData();
        	if (resModel.getData() != null) {
        		establishOrAbandonResData.setOrder_no(resModel.getData().getOrder_no());
        		establishOrAbandonResData.setOrder_status(resModel.getData().getOrder_status());
        		establishOrAbandonResData.setProcess_date(resModel.getData().getProcess_date());
        		establishOrAbandonResData.setQuery_id(resModel.getData().getQuery_id());
			}
        	res.setData(establishOrAbandonResData);
        	
		}else {
            throw new PTMessageException(PTMessageEnum.HFBANK_ESTABLISH_OR_ABANDON_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
		}
    }


    /**
     * 3、批量投标
     * @param req
     * @param res
     */
    public void batchExtBuy(B2GReqMsg_HFBank_BatchExtBuy req, B2GResMsg_HFBank_BatchExtBuy res) {
    	BatchExtBuyReqModel reqModel = new BatchExtBuyReqModel();
        reqModel.setTotal_num(req.getTotal_num());
        reqModel.setProd_id(req.getProd_id());
        List<com.pinting.gateway.hessian.message.hfbank.model.BatchExtBuyReqData> list = req.getData();
        List<BatchExtBuyReqData> modelList = new ArrayList<BatchExtBuyReqData>();
       
        if (CollectionUtils.isNotEmpty(list)) {
            for(com.pinting.gateway.hessian.message.hfbank.model.BatchExtBuyReqData obj : list) {
                BatchExtBuyReqData batchExtBuyReqData = new BatchExtBuyReqData();
                batchExtBuyReqData.setDetail_no(obj.getDetail_no());
                batchExtBuyReqData.setPlatcust(obj.getPlatcust());
                batchExtBuyReqData.setTrans_amt(MoneyUtil.defaultRound(obj.getTrans_amt()).toString());
                if( obj.getExperience_amt() != null ) {
                	batchExtBuyReqData.setExperience_amt(MoneyUtil.defaultRound(obj.getExperience_amt()).toString());
                }
                if( obj.getCoupon_amt() != null ) {
                	batchExtBuyReqData.setCoupon_amt(MoneyUtil.defaultRound(obj.getCoupon_amt()).toString());
                }
                if( obj.getSelf_amt()!= null ) {
                	batchExtBuyReqData.setSelf_amt(MoneyUtil.defaultRound(obj.getSelf_amt()).toString());
                }
                if( obj.getIn_interest() != null ) {
                	batchExtBuyReqData.setIn_interest(MoneyUtil.defaultRound(obj.getIn_interest()).divide(new BigDecimal(100)).toString());
                }
                batchExtBuyReqData.setSubject_priority(obj.getSubject_priority());

                
                
                List<com.pinting.gateway.hessian.message.hfbank.model.BatchExtBuyReqCommission> commissionList = obj.getCommission();
                if (CollectionUtils.isNotEmpty(commissionList)) {
                	com.pinting.gateway.hessian.message.hfbank.model.BatchExtBuyReqCommission batchObj = commissionList.get(0);
                	Gson gson = new Gson();
                	batchExtBuyReqData.setCommission(JSONObject.fromObject(gson.toJson(batchObj)));
                }
                
                modelList.add(batchExtBuyReqData);

                batchExtBuyReqData.setRemark(obj.getRemark());
            }
		}

        reqModel.setData(modelList);

        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        BatchExtBuyResModel resModel = hfbankOutService.batchExtBuy(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	res.setFinish_datetime(resModel.getFinish_datetime());
        	res.setOrder_info(resModel.getOrder_info());
        	res.setOrder_status(resModel.getOrder_status());
        	res.setTotal_num(resModel.getTotal_num());
        	res.setSuccess_num(resModel.getSuccess_num());
        	BeanUtils.copyProperties(resModel, res);
		}else {
			throw  new PTMessageException(PTMessageEnum.HFBANK_BATCH_EXT_BUY_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
		}
        
    }
    
    /**
     * 4、标的出账
     * @param req
     * @param res
     */
    public void chargeOff(B2GReqMsg_HFBank_ChargeOff req, B2GResMsg_HFBank_ChargeOff res) {
    	ChargeOffReqModel reqModel = new ChargeOffReqModel();
        reqModel.setProd_id(req.getProd_id());
        List<com.pinting.gateway.hessian.message.hfbank.model.ChargeOffReqDetail> list = req.getCharge_off_list();
        List<ChargeOffReqDetail> detailList = new ArrayList<ChargeOffReqDetail>();
        if (CollectionUtils.isNotEmpty(list)) {
            for(com.pinting.gateway.hessian.message.hfbank.model.ChargeOffReqDetail obj : list) {
                ChargeOffReqDetail chargeOffReqDetail = new ChargeOffReqDetail();
                chargeOffReqDetail.setIs_advance(obj.getIs_advance());
                chargeOffReqDetail.setPlatcust(obj.getPlatcust());
                chargeOffReqDetail.setOut_amt(MoneyUtil.defaultRound(obj.getOut_amt()).toString());
                chargeOffReqDetail.setOpen_branch(obj.getOpen_branch());
                chargeOffReqDetail.setWithdraw_account(obj.getWithdraw_account());
                chargeOffReqDetail.setPayee_name(obj.getPayee_name());
                chargeOffReqDetail.setBank_id(obj.getBank_id());
                chargeOffReqDetail.setClient_property(obj.getClient_property());
                chargeOffReqDetail.setCity_code(obj.getCity_code());
                chargeOffReqDetail.setBank_name(obj.getBank_name());
                chargeOffReqDetail.setBank_code(obj.getBank_code());
                chargeOffReqDetail.setTran_type(obj.getTran_type());
                detailList.add(chargeOffReqDetail);
            }
		}

        reqModel.setCharge_off_list(detailList);

        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setNotify_url(GlobEnvUtil.get("hfbank.out.of.account.notify.url"));
        reqModel.setPay_code(req.getPay_code());
        reqModel.setRemark(req.getRemark());
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        ChargeOffResModel resModel = hfbankOutService.chargeOff(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
	        if( resModel.getData() != null ) {
	        	ChargeOffResData chargeOffResData = new ChargeOffResData();
	        	chargeOffResData.setOrder_no(resModel.getData().getOrder_no());
	        	chargeOffResData.setOrder_status(resModel.getData().getOrder_status());
	        	chargeOffResData.setProcess_date(resModel.getData().getProcess_date());
	        	chargeOffResData.setQuery_id(resModel.getData().getQuery_id());
	        	res.setData(chargeOffResData);
	        }
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_CHARGE_OFF_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    
    /**
     * 5、标的出账信息修改
     * @param req
     * @param res
     */
    public void modifyPayOutAcct(B2GReqMsg_HFBank_ModifyPayOutAcct req, B2GResMsg_HFBank_ModifyPayOutAcct res) {
    	ModifyPayOutAcctReqModel reqModel = new ModifyPayOutAcctReqModel();
        reqModel.setProd_id(req.getProd_id());
        reqModel.setOpen_branch(req.getOpen_branch());
        reqModel.setWithdraw_account(req.getWithdraw_account());
        reqModel.setAccount_type(req.getAccount_type());
        reqModel.setPayee_name(req.getPayee_name());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        ModifyPayOutAcctResModel resModel = hfbankOutService.modifyPayOutAcct(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
	        BeanUtils.copyProperties(resModel, res);
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_MODIFY_PAY_OUT_ACCT_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    
    /**
     * 7、标的转让
     * @param req
     * @param res
     */
    public void transferDebt(B2GReqMsg_HFBank_TransferDebt req, B2GResMsg_HFBank_TransferDebt res) {
    	TransferDebtReqModel reqModel = new TransferDebtReqModel();
        reqModel.setPlatcust(req.getPlatcust());						//转让人平台客户号
        reqModel.setTrans_share(MoneyUtil.defaultRound(new BigDecimal(req.getTrans_share())).toString());
        reqModel.setProd_id(req.getProd_id());
        reqModel.setTrans_amt(MoneyUtil.defaultRound(new BigDecimal(req.getTrans_amt())).toString());
        reqModel.setDeal_amount(MoneyUtil.defaultRound(new BigDecimal(req.getDeal_amount())).toString());
        reqModel.setCoupon_amt(MoneyUtil.defaultRound(new BigDecimal(req.getCoupon_amt())).toString());
        reqModel.setDeal_platcust(req.getDeal_platcustprivate());//受让人平台客户号
        reqModel.setSubject_priority(req.getSubject_priority());
        //转让人手续费说明
        List<com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommission> list = req.getCommission();
        if (CollectionUtils.isNotEmpty(list)) {
        	com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommission obj = list.get(0);
        	Gson gson = new Gson();
        	reqModel.setCommission(JSONObject.fromObject(gson.toJson(obj)));
        }
        //受让人手续费说明
        List<com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommissionExt> extList = req.getCommission_ext();
        if (CollectionUtils.isNotEmpty(extList)) {
        	com.pinting.gateway.hessian.message.hfbank.model.TransferDebtReqCommissionExt extObj = extList.get(0);
        	Gson gson = new Gson();
        	reqModel.setCommission_ext(JSONObject.fromObject(gson.toJson(extObj)));
        }

        reqModel.setPublish_date(DateUtil.formatDateTime(req.getPublish_date(),"yyyy-MM-dd HH:mm:ss"));
        reqModel.setTrans_date(DateUtil.formatDateTime(req.getPublish_date(),"yyyy-MM-dd HH:mm:ss"));
        reqModel.setTransfer_income(MoneyUtil.defaultRound(new BigDecimal(req.getTransfer_income())).toString());
        reqModel.setIncome_acct(req.getIncome_acct());
        reqModel.setRelated_prod_ids(req.getRelated_prod_ids());
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        TransferDebtResModel resModel = hfbankOutService.transferDebt(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
	        BeanUtils.copyProperties(resModel, res);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_TRANSFER_DEBT_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    
    /**
     * 8、借款人批量还款
     * @param req
     * @param res
     */
    public void batchExtRepay(B2GReqMsg_HFBank_BatchExtRepay req, B2GResMsg_HFBank_BatchExtRepay res) {
    	BatchExtRepayReqModel reqModel = new BatchExtRepayReqModel();

        List<com.pinting.gateway.hessian.message.hfbank.model.BatchExtRepayReqData> list = req.getData();
        List<BatchExtRepayReqData> detailList = new ArrayList<BatchExtRepayReqData>();
        if (CollectionUtils.isNotEmpty(list)) {
            for(com.pinting.gateway.hessian.message.hfbank.model.BatchExtRepayReqData obj : list) {
                BatchExtRepayReqData batchExtRepayReqData = new BatchExtRepayReqData();
                batchExtRepayReqData.setDetail_no(obj.getDetail_no());
                batchExtRepayReqData.setProd_id(obj.getProd_id());
                batchExtRepayReqData.setRepay_num(Integer.parseInt(obj.getRepay_num()));
                batchExtRepayReqData.setRepay_date(DateUtil.parseDate(DateUtil.formatDateTime(obj.getRepay_date(), "yyyy-MM-dd")));
                batchExtRepayReqData.setRepay_amt(MoneyUtil.defaultRound(new BigDecimal(obj.getRepay_amt())).toString());
                batchExtRepayReqData.setReal_repay_date(DateUtil.parseDate(DateUtil.formatDateTime(obj.getReal_repay_date(), "yyyy-MM-dd")));
                batchExtRepayReqData.setReal_repay_amt(MoneyUtil.defaultRound(new BigDecimal(obj.getReal_repay_amt())).toString());
                batchExtRepayReqData.setPlatcust(obj.getPlatcust());
                batchExtRepayReqData.setTrans_amt(MoneyUtil.defaultRound(new BigDecimal(obj.getTrans_amt())).toString());
                
                if( obj.getFee_amt() != null ) { 
                	batchExtRepayReqData.setFee_amt(MoneyUtil.defaultRound(new BigDecimal(obj.getFee_amt())).toString());
                }
                
                batchExtRepayReqData.setRemark(obj.getRemarkprivate());
                detailList.add(batchExtRepayReqData);
            }
		}
        reqModel.setData(detailList);
        reqModel.setTotal_num(req.getTotal_num());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        BatchExtRepayResModel resModel = hfbankOutService.batchExtRepay(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
            res.setPlat_no(resModel.getPlat_no());
            res.setFinish_datetime(resModel.getFinish_datetime());
            res.setOrder_info(resModel.getOrder_info());
            res.setOrder_no(resModel.getOrder_no());
            res.setOrder_status(resModel.getOrder_status());
            res.setSuccess_num(resModel.getSuccess_num());
            res.setTotal_num(resModel.getTotal_num());
            List<BatchExtRepayResSuccessData> successDatas = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(resModel.getSuccess_data())){
                for (com.pinting.gateway.hfbank.out.model.BatchExtRepayResSuccessData modelSuccData : resModel.getSuccess_data()) {
                    BatchExtRepayResSuccessData succData = new BatchExtRepayResSuccessData();
                    succData.setDetail_no(modelSuccData.getDetail_no());
                    succData.setTrans_amt(modelSuccData.getTrans_amt());
                    successDatas.add(succData);
                }
            }
            res.setSuccess_data(successDatas);
            List<BatchExtRepayResErrorData> errorDatas = new ArrayList<>();
            if(CollectionUtils.isNotEmpty(resModel.getError_data())){
                for (com.pinting.gateway.hfbank.out.model.BatchExtRepayResErrorData modelErrData : resModel.getError_data()) {
                    BatchExtRepayResErrorData errorData = new BatchExtRepayResErrorData();
                    errorData.setDetail_no(modelErrData.getDetail_no());
                    errorData.setError_info(modelErrData.getError_info());
                    errorData.setError_no(modelErrData.getError_no());
                    errorDatas.add(errorData);
                }
            }
            res.setError_data(errorDatas);
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_BATCH_EXT_REPAY_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    
    /**
     * 9、标的还款
     * @param req
     * @param res
     */
    public void prodRepay(B2GReqMsg_HFBank_ProdRepay req, B2GResMsg_HFBank_ProdRepay res) {
    	ProdRepayReqModel reqModel = new ProdRepayReqModel();
        reqModel.setProd_id(req.getProd_id());
        reqModel.setRepay_num(req.getRepay_num());
        reqModel.setIs_payoff(req.getIs_payoff());
        reqModel.setTrans_amt(MoneyUtil.defaultRound(req.getTrans_amt()).toString());
        reqModel.setRepay_flag(req.getRepay_flag());
        
        com.pinting.gateway.hessian.message.hfbank.model.ProdRepayReqFundData prodRepayReqFundData = req.getFunddata();
     	reqModel.setFunddata(JSONObject.fromObject(com.alibaba.fastjson.JSONObject.toJSONString(prodRepayReqFundData)));
        
        reqModel.setRemark(req.getRemark());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        ProdRepayResModel resModel = hfbankOutService.prodRepay(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	if (resModel.getData() != null && "2".equals(resModel.getData().getOrder_status())) {
        		res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        		ProdRepayResData prodRepayResData = new ProdRepayResData(); 
        		prodRepayResData.setOrder_no(resModel.getData().getOrder_no());
        		prodRepayResData.setOrder_status(resModel.getData().getOrder_status());
        		prodRepayResData.setProcess_date(resModel.getData().getProcess_date());
        		prodRepayResData.setQuery_id(resModel.getData().getQuery_id());
        		res.setData(prodRepayResData);
			}else if (resModel.getData() != null && "3".equals(resModel.getData().getOrder_status())) {
				 throw  new PTMessageException(PTMessageEnum.HFBANK_PROD_REPAY_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
			}else {
				 throw  new PTMessageException(PTMessageEnum.HFBANK_PROD_REPAY_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
			}
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_PROD_REPAY_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    
    /**
     * 10、平台转账个人
     * @param req
     * @param res
     */
    public void platformTransfer(B2GReqMsg_HFBank_PlatformTransfer req, B2GResMsg_HFBank_PlatformTransfer res) {
    	PlatformTransferReqModel reqModel = new PlatformTransferReqModel();
    	reqModel.setPlat_account(req.getPlat_account());
    	//针对合作方砍头息划转,如果未送platAccount
    	if( StringUtil.isEmpty(req.getPlat_account()) ) {
    		reqModel.setPlat_account("01");
    	}
        reqModel.setAmount(MoneyUtil.defaultRound(req.getAmount()).toString());
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setRemark(req.getRemark());
        reqModel.setCause_type(req.getCause_type());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        PlatformTransferResModel resModel = hfbankOutService.platformTransfer(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        }else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_PLATFORM_TRANSFER_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    

    
    /**
     * 11、平台不同账户转账
     * @param req
     * @param res
     */
    public void platformAccountConverse(B2GReqMsg_HFBank_PlatformAccountConverse req, B2GResMsg_HFBank_PlatformAccountConverse res) {
    	PlatformAccountConverseReqModel reqModel = new PlatformAccountConverseReqModel();
        reqModel.setSource_account(req.getSource_account());
        
        reqModel.setAmt(MoneyUtil.defaultRound(new BigDecimal(req.getAmt())).toString());
        reqModel.setDest_account(req.getDest_account());
        reqModel.setRemark(req.getRemark());

        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        PlatformAccountConverseResModel resModel = hfbankOutService.platformAccountConverse(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_PLATFORM_ACCOUNT_CONVERSE_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    
    
    /**
     * 
     * 12、标的代偿（委托）还款
     * @param req
     * @param res
     */
    public void compensateRepay(B2GReqMsg_HFBank_CompensateRepay req, B2GResMsg_HFBank_CompensateRepay res) {
    	CompensateRepayReqModel reqModel = new CompensateRepayReqModel();
    	reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        
        reqModel.setProd_id(req.getProd_id());
        reqModel.setRepay_num(req.getRepay_num());
        reqModel.setRepay_date(DateUtil.formatDateTime(req.getRepay_date(), "yyyyMMdd"));
        reqModel.setRepay_amt(MoneyUtil.defaultRound(new BigDecimal(req.getRepay_amt())).toString());
        reqModel.setReal_repay_date(DateUtil.formatDateTime(req.getReal_repay_date(), "yyyyMMdd"));
        reqModel.setReal_repay_amt(MoneyUtil.defaultRound(new BigDecimal(req.getReal_repay_amt())).toString());
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setCompensation_platcust(req.getCompensation_platcust());
        reqModel.setTrans_amt(MoneyUtil.defaultRound(new BigDecimal(req.getTrans_amt())).toString());
        reqModel.setFee_amt(MoneyUtil.defaultRound(new BigDecimal(req.getFee_amt())).toString());
        reqModel.setRepay_type(req.getRepay_type());
        reqModel.setRemark(req.getRemark());
        CompensateRepayResModel resModel = hfbankOutService.compensateRepay(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        //标的代偿（委托）还款成功
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())) {
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	if( resModel.getData() != null ) {
        		com.pinting.gateway.hessian.message.hfbank.model.CompensateRepayResData compensateData = new com.pinting.gateway.hessian.message.hfbank.model.CompensateRepayResData();
        		compensateData.setOrder_no(resModel.getData().getOrder_no());
        		compensateData.setOrder_status(resModel.getData().getOrder_status());
        		compensateData.setProcess_date(resModel.getData().getProcess_date());
        		compensateData.setQuery_id(resModel.getData().getQuery_id());
        		res.setData(compensateData);
        	}
		} else { 
			throw new PTMessageException(PTMessageEnum.HFBANK_COMPENSATE_REPAY_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
		}
        
    }
    
    
    
    /**
     * 
     * 13、借款人还款代偿（委托）
     * @param req
     * @param res
     */
    public void repayCompensate(B2GReqMsg_HFBank_RepayCompensate req, B2GResMsg_HFBank_RepayCompensate res) {
    	RepayCompensateReqModel reqModel = new RepayCompensateReqModel();
    	reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        reqModel.setProd_id(req.getProd_id());
        reqModel.setRepay_amt(MoneyUtil.defaultRound(req.getRepay_amt()).toString());
        reqModel.setPlatcust(req.getPlatcust());
        reqModel.setCompensation_platcust(req.getCompensation_platcust());
        reqModel.setRemark(req.getRemark());
        RepayCompensateResModel resModel = hfbankOutService.repayCompensate(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())) {
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        	if( resModel.getData() != null ) {
        		com.pinting.gateway.hessian.message.hfbank.model.RepayCompensateResData repayCompensateData = new com.pinting.gateway.hessian.message.hfbank.model.RepayCompensateResData();
        		repayCompensateData.setOrder_no(resModel.getData().getOrder_no());
        		repayCompensateData.setOrder_status(resModel.getData().getOrder_status());
        		repayCompensateData.setProcess_date(resModel.getData().getProcess_date());
        		repayCompensateData.setQuery_id(resModel.getData().getQuery_id());
        		res.setData(repayCompensateData);
        	}
		} else {
			throw new PTMessageException(PTMessageEnum.HFBANK_REPAY_COMPENSATE_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
		}
    }
    
    
    /**
     * 
     * 14、平台提现 
     * @param req
     * @param res
     */
    public void platWithDraw(B2GReqMsg_HFBank_PlatWithDraw req, B2GResMsg_HFBank_PlatWithDraw res) {
    	
    	PlatWithDrawReqModel reqModel = new PlatWithDrawReqModel();
       
    	reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
    	reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        
        reqModel.setAmount(MoneyUtil.defaultRound(new BigDecimal(req.getAmount())).toString());
        reqModel.setNotify_url(GlobEnvUtil.get("hfbank.out.of.withdraw.notify.url"));
        reqModel.setRemark(req.getRemark());
        PlatWithDrawResModel resModel = hfbankOutService.platWithdraw(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())) {
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		} else {
			throw new PTMessageException(PTMessageEnum.HFBANK_PLATFORM_WITHDRAW_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
		}
    }
    /**
     * 15、平台充值
     * */
    public void platCharge(B2GReqMsg_HFBank_PlatCharge req, B2GResMsg_HFBank_PlatCharge res) {
    	
    	PlatChargeReqModel reqModel = new PlatChargeReqModel();
       
    	reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
    	reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        
        reqModel.setAmount(MoneyUtil.defaultRound(new BigDecimal(req.getAmount())).toString());
        reqModel.setNotify_url(GlobEnvUtil.get("hfbank.out.of.charge.notify.url"));
        reqModel.setRemark(req.getRemark());
        
        PlatChargeResModel resModel = hfbankOutService.platCharge(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())) {
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		} else {
			throw new PTMessageException(PTMessageEnum.HFBANK_PLATFORM_CHARGE_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
		}
    }
    /* ============================== 龙猫写的接口结束 ============================== */

    /**
     * 对账文件资金进出数据
     * */
    public void fundDataCheck(B2GReqMsg_HFBank_FundDataCheck req, B2GResMsg_HFBank_FundDataCheck res) {

    	FundDataCheckReqModel reqModel = new FundDataCheckReqModel();

    	reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
    	reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));

        reqModel.setPaycheck_date(DateUtil.formatDateTime(req.getPaycheck_date(), "yyyyMMdd"));
        reqModel.setPrecheck_flag(req.getPrecheck_flag());
        reqModel.setBegin_time(DateUtil.formatDateTime(req.getBegin_time(), "yyyyMMddHHmmss"));
        reqModel.setEnd_time(DateUtil.formatDateTime(req.getEnd_time(), "yyyyMMddHHmmss"));

        FundDataCheckResModel resModel = hfbankOutService.fundDataCheck(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        res.setDestFileName(resModel.getDestFileName());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())) {
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
		} else {
			throw new PTMessageException(PTMessageEnum.HFBANK_FUNDDATA_CHECK_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
		}
    }

    /**
     * 恒丰银行对账文件账户余额数据
     * @param req
     * @param res
     */
    public void balanceInfo(B2GReqMsg_HFBank_BalanceInfo req, B2GResMsg_HFBank_BalanceInfo res) {
        BalanceInfoReqModel reqModel = new BalanceInfoReqModel();
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        reqModel.setPaycheck_date(DateUtil.formatDateTime(req.getPaycheck_date(), "yyyyMMdd"));
        reqModel.setPrecheck_flag(req.getPrecheck_flag());
        reqModel.setBegin_time(DateUtil.formatDateTime(req.getBegin_time(), "yyyyMMddHHmmss"));
        reqModel.setEnd_time(DateUtil.formatDateTime(req.getEnd_time(), "yyyyMMddHHmmss"));

        BalanceInfoResModel resModel = hfbankOutService.balanceInfo(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        res.setDestFileName(resModel.getDestFileName());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())) {
            res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        } else {
            throw new PTMessageException(PTMessageEnum.HFBANK_BALANCE_INFO_FAIL, res.getRecode()+ (StringUtil.isEmpty(res.getRemsg())?"":res.getRemsg()));
        }
    }
    
    /**
     * 清算状态查询
     * @param req
     * @param res
     */
    public void queryLiquidationInfo(B2GReqMsg_HFBank_QueryLiquidationInfo req, B2GResMsg_HFBank_QueryLiquidationInfo res) {
    	LiquidationInfoReqModel reqModel = new LiquidationInfoReqModel();
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        reqModel.setOrder_no(req.getOrder_no());
        reqModel.setPartner_trans_date(DateUtil.formatDateTime(req.getPartner_trans_date(), "yyyyMMdd"));
        reqModel.setPartner_trans_time(DateUtil.formatDateTime(req.getPartner_trans_time(), "HHmmss"));
        LiquidationInfoResModel resModel = hfbankOutService.queryLiquidationInfo(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
        	res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
        }else {
            throw new PTMessageException(PTMessageEnum.HFBANK_QUERY_LIQUIDATION_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }

    /**
     * 14. 充值订单状态查询
     * @param req
     * @param res
     */
    public void getFundOrderInfo(B2GReqMsg_HFBank_GetFundOrderInfo req, B2GResMsg_HFBank_GetFundOrderInfo  res) {
        QueryFundOrderReqModel reqModel = new QueryFundOrderReqModel();
        reqModel.setOriginal_serial_no(req.getOriginal_serial_no());
        reqModel.setOccur_balance(req.getOccur_balance());
        reqModel.setPlat_no(GlobEnvUtil.get("hfbank.platNo"));
        QueryFundOrderResModel resModel = hfbankOutService.queryFundOrderInfo(reqModel);
        res.setRecode(resModel.getRecode());
        res.setRemsg(resModel.getRemsg());
        if (Constants.BSRESCODE_DEP_SUCCESS.equals(resModel.getRecode())){
            res.setResCode(ConstantUtil.BSRESCODE_SUCCESS);
            QueryOrderData orderData = new QueryOrderData();
            orderData.setPlat_no(resModel.getData().getPlat_no());
            orderData.setQuery_order_no(resModel.getData().getQuery_order_no());
            orderData.setStatus(resModel.getData().getStatus());
            res.setData(orderData);
        } else {
            throw  new PTMessageException(PTMessageEnum.HFBANK_QUERY_ORDER_FAIL, resModel.getRecode()+ (StringUtil.isEmpty(resModel.getRemsg())?"":resModel.getRemsg()));
        }
    }
}
