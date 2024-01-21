package com.pinting.business.accounting.service.impl;

import com.pinting.business.accounting.service.SignSealService;
import com.pinting.business.dao.BsPropertyInfoMapper;
import com.pinting.business.dao.BsUserSealFileMapper;
import com.pinting.business.dao.BsUserSignSealMapper;
import com.pinting.business.dao.LnLoanRelationMapper;
import com.pinting.business.enums.SealBusiness;
import com.pinting.business.enums.SealPDFType;
import com.pinting.business.enums.SealStatus;
import com.pinting.business.enums.SealType;
import com.pinting.business.model.BsFileSealRelation;
import com.pinting.business.model.BsUserSealFile;
import com.pinting.business.model.BsUserSealFileExample;
import com.pinting.business.model.BsUserSignSeal;
import com.pinting.business.model.BsUserSignSealExample;
import com.pinting.business.model.vo.BsUser4LoanVO;
import com.pinting.business.model.vo.SignSeal4PdfInfoVO;
import com.pinting.business.model.vo.UserSealFileVO;
import com.pinting.business.model.vo.UserSealInfoVO;
import com.pinting.business.model.vo.UserSealResultVO;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import com.pinting.gateway.hessian.message.dafy.*;
import com.pinting.gateway.out.service.DafyCFCATransportService;

import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * 
 * @author Baby shark love blowing wind
 * @version $Id: SignSealServiceImpl.java, v 0.1 2015-9-17 上午9:44:50 BabyShark Exp $
 */
@Service("signSealService")
public class SignSealServiceImpl implements SignSealService {
	private Logger log = LoggerFactory.getLogger(SignSealServiceImpl.class);
    @Autowired
	private DafyCFCATransportService dafyCFCATransportService;
    @Autowired
    private BsUserSignSealMapper bsUserSignSealMapper;
    @Autowired
    private BsUserSealFileMapper bsUserSealFileMapper;
    @Autowired
    private BsPropertyInfoMapper bsPropertyInfoMapper;
    @Autowired
    private LnLoanRelationMapper lnLoanRelationMapper;

    @Override
    public BsUserSignSeal findUserSignSeal (BsUserSignSeal signSeal) {
        BsUserSignSealExample sealExample = new BsUserSignSealExample();
        sealExample.createCriteria().andIdCardEqualTo(signSeal.getIdCard()).andUserNameEqualTo(signSeal.getUserName());
        sealExample.setOrderByClause("id desc");
        List<BsUserSignSeal> userSignSeals = bsUserSignSealMapper.selectByExample(sealExample);

        if (CollectionUtils.isNotEmpty(userSignSeals) && userSignSeals.size() > 0) {
            return userSignSeals.get(0);
        }

        return null;
    }

    @Override
    public void addUserSeal(BsUserSignSeal signSeal) {
        signSeal.setCreateTime(new Date());
        signSeal.setUpdateTime(new Date());
        bsUserSignSealMapper.insertSelective(signSeal);
    }

    @Override
    public UserSealResultVO checkAndMakeSeal(UserSealInfoVO req) {
        UserSealResultVO result = new UserSealResultVO();
        boolean isApplyCert = false;//默认不需制章
        boolean isMakeSeal = false;//默认不需制章
        //0、判断是否已有证书（+证书有效期判断）？判断是否已有电子印章？
        BsUserSignSealExample sealExample = new BsUserSignSealExample();
        sealExample.createCriteria().andIdCardEqualTo(req.getIdCard()).andUserNameEqualTo(req.getUserName());
        sealExample.setOrderByClause("create_time desc");
        List<BsUserSignSeal> userSignSeals = bsUserSignSealMapper.selectByExample(sealExample);
        if (userSignSeals != null && userSignSeals.size() > 0) {
            BsUserSignSeal tempSeal = userSignSeals.get(0);
            if(tempSeal.getEndTime() == null){
            	isApplyCert = true;
                isMakeSeal = true;
            }
            Date endTime = DateUtil.parse(tempSeal.getEndTime(), "yyyyMMddHHmmss");
            //当离证书截止日期剩余30天时，需重新发起证书申请、重新制章
            if (DateUtil.getDiffeDay(endTime) > -30) {
                isApplyCert = true;
                isMakeSeal = true;
            }
            //证书在有效期返回内
            else {
                //尚未制章
                if (StringUtil.isEmpty(tempSeal.getSealCode())) {
                    isMakeSeal = true;
                }
                //证书有效期内，并且已制章
                else {
                    //isApplyCert、isApplyCert = false
                }
            }
        } else {
            //无证书时需发起证书申请
            isApplyCert = true;
            isMakeSeal = true;
        }

        if (isApplyCert) {
            //1、证书生成
            B2GReqMsg_CFCATransfer_UserCert userCertRequest = new B2GReqMsg_CFCATransfer_UserCert();
            userCertRequest.setIdentNo(req.getIdCard());
            userCertRequest.setUserName(req.getUserName());
            String pfxName = DateUtil.formatDateTime(new Date(), "yyyyMMddHHmmssSSS") + "-"
                             + req.getUserId() + ".pfx";
            userCertRequest.setPfxPath(GlobEnvUtil.get("cfca.seal.pfxSavePath") + req.getUserId()
                                       + "/" + pfxName);
            B2GResMsg_CFCATransfer_UserCert userCertResult = dafyCFCATransportService.userCert(userCertRequest);
            //2、证书信息存库
            if(ConstantUtil.BSRESCODE_SUCCESS.equals(userCertResult.getResCode())){
            	BsUserSignSeal signSeal = new BsUserSignSeal();
                signSeal.setCreateTime(new Date());
                signSeal.setUpdateTime(new Date());
                signSeal.setDn(userCertResult.getDn());
                signSeal.setEncryptionCert(userCertResult.getEncryptionCert());
                signSeal.setEncryptionPrivateKey(userCertResult.getEncryptionPrivateKey());
                signSeal.setEndTime(userCertResult.getEndTime());
                signSeal.setIdCard(userCertResult.getIdCard());
                signSeal.setKeyIdentifier(userCertResult.getKeyIdentifier());
                signSeal.setP10(userCertResult.getP10());
                signSeal.setPfxData(userCertResult.getPfxData());
                signSeal.setPfxPassword(userCertResult.getPfxPassword());
                signSeal.setPfxPath(userCertResult.getPfxPath());
                signSeal.setSequenceNo(userCertResult.getSequenceNo());
                signSeal.setSerialNo(userCertResult.getSerialNo());
                signSeal.setSignatureCert(userCertResult.getSignatureCert());
                signSeal.setStartTime(userCertResult.getStartTime());
                signSeal.setUserId(req.getUserId());
                signSeal.setUserName(userCertResult.getUserName());
                if (CollectionUtils.isNotEmpty(userSignSeals)) {
                	signSeal.setId(userSignSeals.get(0).getId());
                	bsUserSignSealMapper.updateByPrimaryKeySelective(signSeal);
				}else {
					 bsUserSignSealMapper.insertSelective(signSeal);
				}
               
                //3、制章
                //4、制章成功，信息入库
                String sealCode = "BGW" + signSeal.getId() + "_" + Util.generateAssignLengthNo(4);
                String sealPass = Util.generateAssignLengthNo(6);
                boolean makeSealFlag = makeSeal(userCertResult.getPfxPassword(),
                    userCertResult.getPfxPath(), sealCode, sealPass, userCertResult.getUserName(),
                    signSeal.getId());
                if (makeSealFlag) {
                    result.setSucc(true);
                    result.setCertDN(userCertResult.getDn());
                    result.setCertSN(userCertResult.getSerialNo());
                    result.setSealCode(sealCode);
                    result.setSealPassword(sealPass);
                }
            }
        } else if (isMakeSeal) {
            BsUserSignSeal retSeal = userSignSeals.get(0);
            //3、制章
            //4、制章成功，信息入库
            String sealCode = "BGW" + retSeal.getId() + "_" + Util.generateAssignLengthNo(4);
            String sealPass = Util.generateAssignLengthNo(6);
            boolean makeSealFlag = makeSeal(retSeal.getPfxPassword(), retSeal.getPfxPath(),
                sealCode, sealPass, retSeal.getUserName(), retSeal.getId());
            if (makeSealFlag) {
                result.setSucc(true);
                result.setCertDN(retSeal.getDn());
                result.setCertSN(retSeal.getSerialNo());
                result.setSealCode(sealCode);
                result.setSealPassword(sealPass);
            }

        } else {
            //已有章，直接返回印章信息
            BsUserSignSeal retSeal = userSignSeals.get(0);
            result.setSucc(true);
            result.setCertDN(retSeal.getDn());
            result.setCertSN(retSeal.getSerialNo());
            result.setSealCode(retSeal.getSealCode());
            result.setSealPassword(retSeal.getSealPassword());
        }

        return result;

    }

    /**
     * 制章、信息入库
     * @param pfxPassword
     * @param pfxPath
     * @param sealCode
     * @param sealPassword
     * @param userName
     * @param sealId
     */
    private boolean makeSeal(String pfxPassword, String pfxPath, String sealCode,
                             String sealPassword, String userName, Integer sealId) {
        //3、制章
        B2GReqMsg_CFCATransfer_UserSeal userSealRequest = new B2GReqMsg_CFCATransfer_UserSeal();
        userSealRequest.setPfxPassword(pfxPassword);
        userSealRequest.setPfxPath(pfxPath);
        userSealRequest.setSealCode(sealCode);//印章编码
        userSealRequest.setSealPassword(sealPassword);//印章密码
        userSealRequest.setSealName(userName);//印章名称
        userSealRequest.setSealPerson("杭州币港湾科技有限公司");//制章人
        userSealRequest.setSealOrg("杭州币港湾科技有限公司");//制章单位（制章人所在单位）
        userSealRequest.setSealType(SealType.PERSON.getCode());//制章类型
        B2GResMsg_CFCATransfer_UserSeal makeSealResp = dafyCFCATransportService.userSeal(userSealRequest);
        //4、制章成功，信息入库
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(makeSealResp.getResCode())) {
            BsUserSignSeal signSeal = new BsUserSignSeal();
            signSeal.setSealCode(userSealRequest.getSealCode());
            signSeal.setSealPassword(userSealRequest.getSealPassword());
            signSeal.setSealName(userSealRequest.getSealName());
            signSeal.setSealPerson(userSealRequest.getSealPerson());
            signSeal.setSealOrg(userSealRequest.getSealOrg());
            signSeal.setId(sealId);
            bsUserSignSealMapper.updateByPrimaryKeySelective(signSeal);
            return true;
        }else{
        	return false;
        }
    }

    @Override
    public boolean signSeal4PdfByKeyword(SignSeal4PdfInfoVO req) {
        B2GReqMsg_CFCATransfer_SealAutoPdf sealAutoPdfRequest = new B2GReqMsg_CFCATransfer_SealAutoPdf();
        sealAutoPdfRequest.setCertDN(req.getCertDN());
        sealAutoPdfRequest.setCertSN(req.getCertSN());
        sealAutoPdfRequest.setKeyword(req.getKeyword());
        sealAutoPdfRequest.setPath(req.getPdfPath());
        sealAutoPdfRequest.setSealCode(req.getSealCode());
        sealAutoPdfRequest.setSealPassword(req.getSealPassword());
        sealAutoPdfRequest.setSealedFileName(req.getSealedFileName());
        sealAutoPdfRequest.setSealPerson("杭州币港湾科技有限公司");
        sealAutoPdfRequest.setSealLocation("杭州");
        sealAutoPdfRequest.setSealResson(req.getSealResson());
        sealAutoPdfRequest.setType(SealPDFType.KEYWORD_SEAL.getCode());
        sealAutoPdfRequest.setIsNew(req.getIsNew());
        B2GResMsg_CFCATransfer_SealAutoPdf sealResp = dafyCFCATransportService.sealAutoPdf(sealAutoPdfRequest);
        if (ConstantUtil.BSRESCODE_SUCCESS.equals(sealResp.getResCode())) {
            return true;
        }else{
        	return false;
        }
    }

	@Override
	public void addUserSealFile(UserSealFileVO req) {
		BsUserSealFile userSealFile = new BsUserSealFile();
        userSealFile.setCreateTime(new Date());
        userSealFile.setUpdateTime(new Date());
        userSealFile.setNote(req.getNote());
        userSealFile.setSealStatus(req.getSealStatus());
        userSealFile.setSealType(req.getSealType());
        userSealFile.setUserId(req.getUserId());
        userSealFile.setSrcAddress(req.getSrcAddress());
        userSealFile.setFileAddress(req.getFileAddress());
        userSealFile.setRelativeInfo(req.getRelativeInfo());
        userSealFile.setUserSrc(req.getUserSrc());
        userSealFile.setAgreementNo(req.getAgreementNo());
        bsUserSealFileMapper.insertSelective(userSealFile);
		
	}

	@Override
	public String checkPropertySymbol(Integer subAccountId) {
		String propertySymbol = bsPropertyInfoMapper.checkPropertySymbol(subAccountId);
		return propertySymbol;
	}
	
	@Override
	public void updateUserSealFile(UserSealFileVO req) {
		BsUserSealFile userSealFile = new BsUserSealFile();
		userSealFile.setId(req.getId());
        //userSealFile.setCreateTime(new Date());
        userSealFile.setUpdateTime(new Date());
        userSealFile.setNote(req.getNote());
        userSealFile.setSealStatus(req.getSealStatus());
        userSealFile.setSealType(req.getSealType());
        userSealFile.setUserId(req.getUserId());
        userSealFile.setSrcAddress(req.getSrcAddress());
        userSealFile.setFileAddress(req.getFileAddress());
        userSealFile.setRelativeInfo(req.getRelativeInfo());
        bsUserSealFileMapper.updateByPrimaryKeySelective(userSealFile);
	}

	@Override
	public BsUserSealFile querySealFileInfoByAgreementNo(String agreementNo) {
		BsUserSealFileExample userSealFileExample  = new  BsUserSealFileExample();
		userSealFileExample.createCriteria().andAgreementNoEqualTo(agreementNo).andSealStatusEqualTo(SealStatus.SUCC.getCode());
		List<BsUserSealFile> bsUserSealFileList = bsUserSealFileMapper.selectByExample(userSealFileExample);
		if (CollectionUtils.isNotEmpty(bsUserSealFileList)) {
			return bsUserSealFileList.get(0);
		}else {
			return null;
		}
	}

    @Override
    public BsUserSealFile queryInitByAgreementNo(String agreementNo) {
        BsUserSealFileExample userSealFileExample  = new  BsUserSealFileExample();
        userSealFileExample.createCriteria().andAgreementNoEqualTo(agreementNo);
        List<BsUserSealFile> bsUserSealFileList = bsUserSealFileMapper.selectByExample(userSealFileExample);
        if (CollectionUtils.isNotEmpty(bsUserSealFileList)) {
            return bsUserSealFileList.get(0);
        }else {
            return null;
        }
    }

    @Override
	public List<BsUser4LoanVO> selectUserListByLoanId(Integer loanId) {
		return lnLoanRelationMapper.selectBsUserByLoanId(loanId);
	}

	@Override
	public SignSeal4PdfInfoVO getSignSeal(BsUserSealFile bsUserSealFile,BsFileSealRelation bsFileSealRelation) {
		
		
		BsUserSignSeal bsUserSignSealTemp = bsUserSignSealMapper.selectByPrimaryKey(bsFileSealRelation.getUserSealId());
		
		UserSealInfoVO req = new UserSealInfoVO();
		req.setUserId(bsUserSignSealTemp.getUserId());
		req.setUserName(bsUserSignSealTemp.getUserName());
		req.setIdCard(bsUserSignSealTemp.getIdCard());
		UserSealResultVO userSealResult = checkAndMakeSeal(req);
		if (!userSealResult.isSucc()) {
			log.error("获取制章信息失败");
			return null;
		}
		SignSeal4PdfInfoVO companySignSeal4PdfReq = new SignSeal4PdfInfoVO();
		companySignSeal4PdfReq.setCertDN(userSealResult.getCertDN());
		companySignSeal4PdfReq.setCertSN(userSealResult.getCertSN());
		companySignSeal4PdfReq.setKeyword(bsFileSealRelation.getKeywords());
		companySignSeal4PdfReq.setPdfPath(bsUserSealFile.getFileAddress());
		companySignSeal4PdfReq.setSealCode(userSealResult.getSealCode());
		companySignSeal4PdfReq.setSealPassword(userSealResult.getSealPassword());
		
		if(SealBusiness.BUY.getCode().equals(bsUserSealFile.getSealType())){
			String propertySymbol = checkPropertySymbol(Integer.parseInt(bsUserSealFile.getRelativeInfo()));
			if(Constants.PROPERTY_SYMBOL_YUN_DAI_SELF.equals(propertySymbol) ||
					Constants.PROPERTY_SYMBOL_7_DAI_SELF.equals(propertySymbol)) {
				companySignSeal4PdfReq.setSealResson("授权委托书签章");
			}else {
				companySignSeal4PdfReq.setSealResson("出借服务协议签章");
			}
		}

		
        String tempPdfPath = bsUserSealFile.getFileAddress();
        String sealedPdfFile =  tempPdfPath.substring(0,tempPdfPath.lastIndexOf("/"))
                               + tempPdfPath.substring(tempPdfPath.lastIndexOf("/"),
                                   tempPdfPath.length() - 4) + "-sign.pdf";
        
		companySignSeal4PdfReq.setSealedFileName(sealedPdfFile);
		companySignSeal4PdfReq.setIsNew("Y");
		return companySignSeal4PdfReq;
	}
}
