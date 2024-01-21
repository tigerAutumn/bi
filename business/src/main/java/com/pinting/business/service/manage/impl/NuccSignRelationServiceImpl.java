package com.pinting.business.service.manage.impl;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.dao.BsBgwNuccSignRelationMapper;
import com.pinting.business.dao.BsPaymentChannelMapper;
import com.pinting.business.dao.LnBindCardMapper;
import com.pinting.business.dao.LnUserMapper;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.BsBgwNuccSignRelationVO;
import com.pinting.business.service.manage.BsBankCardService;
import com.pinting.business.service.manage.NuccSignRelationService;
import com.pinting.business.util.Constants;
import com.pinting.core.util.StringUtil;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class NuccSignRelationServiceImpl implements NuccSignRelationService {

    private Logger log = LoggerFactory.getLogger(NuccSignRelationServiceImpl.class);
    @Autowired
    private BsBgwNuccSignRelationMapper bsBgwNuccSignRelationMapper;
    @Autowired
    private BsBankCardService bsBankCardService;
    @Autowired
    private LnBindCardMapper lnBindCardMapper;
    @Autowired
    private TransactionTemplate transactionTemplate;
    @Autowired
    private BsPaymentChannelMapper paymentChannelMapper;

    @Override
    public String checkImportDate(String merchantType, String userType, List<String> list, List<BsBgwNuccSignRelationVO> checkList, List<BsBgwNuccSignRelationVO> failList) {
        log.info("=====网联协议号导入校验开始=====");

        String errorMsg = "";
        // 校验导入数据
        BsBgwNuccSignRelationVO checkInfo = null;

        // 过滤重复数据
        Set<String> duplicatCheck = new HashSet<>();

        for (int i = 0; i < list.size(); i++) {
            String lineStr = list.get(i);
            // 如果是空行，跳过处理
            if (StringUtils.isBlank(lineStr)) {
                continue;
            }
            log.info("单条校验数据开始：" + lineStr);

            int LineNo = i + 1;
            String[] lines = lineStr.split(Constants.SPLIT_SYMBOL);
            // 列数错误，页面提示
            if (lines == null || lines.length != 2) {
                errorMsg = "第" + LineNo + "行，列数错误";
                // 清除已校验数据
                checkList.clear();
                failList.clear();
                log.info("导入校验失败：" + lineStr + errorMsg);
                return errorMsg;
            }

            // 第一行是标题,则跳过处理
            if (i == 0 && Constants.NUCC_SIGN_EXPORT_TITLE.equals(lineStr.trim())) {
                log.info("第一行数据为导入标题，不处理");
                continue;
            }

            String cardNo = lines[0].trim();
            String protocolNo = lines[1].trim();

            checkInfo = new BsBgwNuccSignRelationVO();
            checkInfo.setCardNo(cardNo);
            checkInfo.setProtocolNo(protocolNo);
            checkInfo.setLineNo(LineNo);
            checkInfo.setErrorMsg("校验通过");
            checkList.add(checkInfo);

            // 校验用户类型是否匹配
            if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(userType)) {
                checkInfo.setUserType(userType);
            } else if (PartnerEnum.YUN_DAI_SELF.getCode().equals(userType)) {
                checkInfo.setUserType(userType);
            } else if (Constants.UC_USER_TYPE_BGW.equals(userType)) {
                checkInfo.setUserType(userType);
            } else {
                checkInfo.setCardNoFlag("error");
                checkInfo.setErrorMsg("用户类型格式错误");
                failList.add(checkInfo);
                log.info("导入校验失败：" + lineStr + "用户类型格式错误");
                continue;
            }

            // 校验cardNo非空
            if (StringUtil.isBlank(cardNo)) {
                checkInfo.setCardNoFlag("error");
                checkInfo.setErrorMsg("银行卡号不能为空");
                failList.add(checkInfo);
                log.info("导入校验失败：" + lineStr + "银行卡号不能为空");
                continue;
            }

            // 银行卡号长度16-19位,必须是数字
            if (cardNo.length() < 16 || cardNo.length() > 19 || !isNumeric(cardNo)) {
                checkInfo.setCardNoFlag("error");
                checkInfo.setErrorMsg("银行卡号格式不正确");
                failList.add(checkInfo);
                log.info("导入校验失败：" + lineStr + "银行卡号格式不正确");
                continue;
            }

            // 重复数据校验
            if (!duplicatCheck.add(cardNo)) {
                checkInfo.setCardNoFlag("error");
                checkInfo.setErrorMsg("重复银行卡号数据");
                failList.add(checkInfo);
                log.info("导入校验失败：" + lineStr + "重复银行卡号数据");
                continue;
            }

            // 校验protocolNo非空
            if (StringUtil.isBlank(protocolNo)) {
                checkInfo.setProtocolNoFlag("error");
                checkInfo.setErrorMsg("网易协议号不能为空");
                failList.add(checkInfo);
                log.info("导入校验失败：" + lineStr + "网易协议号不能为空");
                continue;
            }

            if (Constants.BGW_BAOFOO_ASSIST.equals(merchantType)) {
                //宝付辅助商户号
                BsPaymentChannelExample paymentChannelExample = new BsPaymentChannelExample();
                paymentChannelExample.createCriteria().andTransTypeEqualTo("DK").andIsMainEqualTo(2);
                paymentChannelExample.setOrderByClause("priority ASC");
                List<BsPaymentChannel> paymentChannels = paymentChannelMapper.selectByExample(paymentChannelExample);
                if (CollectionUtils.isNotEmpty(paymentChannels)) {
                    checkInfo.setMerchanntNo(paymentChannels.get(0).getMerchantNo());
                } else {
                    checkInfo.setCardNoFlag("error");
                    checkInfo.setErrorMsg("商户号不存在");
                    failList.add(checkInfo);
                    log.info("导入校验失败：" + lineStr + "商户号不存在");
                    continue;
                }
            } else {
                checkInfo.setCardNoFlag("error");
                checkInfo.setErrorMsg("商户号类型错误");
                failList.add(checkInfo);
                log.info("导入校验失败：" + lineStr + "商户号类型错误");
                continue;
            }


            if (PartnerEnum.SEVEN_DAI_SELF.getCode().equals(userType) || PartnerEnum.YUN_DAI_SELF.getCode().equals(userType)) {
                LnBindCardExample example = new LnBindCardExample();
                example.createCriteria().andBankCardEqualTo(cardNo).andPartnerCodeEqualTo(userType).andStatusEqualTo(Constants.UC_BIND_CARD_STATUS_BINDED);
                List<LnBindCard> lnBindCards = lnBindCardMapper.selectByExample(example);

                if (CollectionUtils.isEmpty(lnBindCards) || lnBindCards.size() > 1) {
                    checkInfo.setCardNoFlag("error");
                    checkInfo.setErrorMsg("银行卡信息不存在或已解绑");
                    failList.add(checkInfo);
                    log.info("导入校验失败：" + lineStr + "银行卡信息不存在或已解绑");
                    continue;
                }

                LnBindCard bankCard = lnBindCards.get(0);
                checkInfo.setUserId(bankCard.getLnUserId());
                checkInfo.setCardOwner(bankCard.getUserName());
                checkInfo.setIdCard(bankCard.getIdCard());
                checkInfo.setMobile(bankCard.getMobile());
                checkInfo.setUserIdStr(checkInfo.getUserType() + checkInfo.getUserId());
            } else if (Constants.UC_USER_TYPE_BGW.equals(userType)) {
                BsBankCard bsBankCard = bsBankCardService.findBsBankCardByUserIdAndCardNo(cardNo, Constants.BANK_CARD_NORMAL);

                if (bsBankCard == null) {
                    checkInfo.setCardNoFlag("error");
                    checkInfo.setErrorMsg("银行卡信息不存在或已解绑");
                    failList.add(checkInfo);
                    log.info("导入校验失败：" + lineStr + "银行卡信息不存在或已解绑");
                    continue;
                }

                checkInfo.setUserId(bsBankCard.getUserId());
                checkInfo.setCardOwner(bsBankCard.getCardOwner());
                checkInfo.setIdCard(bsBankCard.getIdCard());
                checkInfo.setMobile(bsBankCard.getMobile());
                checkInfo.setUserIdStr(checkInfo.getUserType() + checkInfo.getUserId());
            }
        }
        log.info("导入校验成功数量：" + (checkList.size() - failList.size()));
        log.info("导入校验失败数量：" + failList.size());
        log.info("=====网联协议号导入校验结束=====");
        return errorMsg;
    }

    @Override
    public boolean saveImportDate(List<BsBgwNuccSignRelationVO> importList) {
        log.info("=====网联协议号导入数据入库开始=====");
        // 校验导入数据
        BsBgwNuccSignRelationVO checkInfo = null;
        // 导入校验通过数据
        final List<BsBgwNuccSignRelation> addList = new ArrayList<>();
        final List<BsBgwNuccSignRelation> updateList = new ArrayList<>();
        Date now = new Date();
        for (BsBgwNuccSignRelationVO data : importList) {
            checkInfo = new BsBgwNuccSignRelationVO();
            // 查询条件：用户编号，银行卡号，商户号，正常状态
            // 判定网联协议号完全一样，不进行处理
            // 查询记录已存在，则更新协议号
            // 查询记录未存在，则新增协议号
            BsBgwNuccSignRelationExample example = new BsBgwNuccSignRelationExample();
            example.createCriteria().andUserIdEqualTo(data.getUserId()).andCardNoEqualTo(data.getCardNo())
                    .andMerchanntNoEqualTo(data.getMerchanntNo()).andStatusEqualTo(Constants.NUCC_SIGN_NORMAL);
            List<BsBgwNuccSignRelation> nuccSignRelations = bsBgwNuccSignRelationMapper.selectByExample(example);
            if (CollectionUtils.isEmpty(nuccSignRelations)) {
                checkInfo.setUserId(data.getUserId());
                checkInfo.setUserType(data.getUserType());
                checkInfo.setMerchanntNo(data.getMerchanntNo());
                checkInfo.setProtocolNo(data.getProtocolNo());
                checkInfo.setCardNo(data.getCardNo());
                checkInfo.setCardOwner(data.getCardOwner());
                checkInfo.setIdCard(data.getIdCard());
                checkInfo.setMobile(data.getMobile());
                checkInfo.setStatus(Constants.NUCC_SIGN_NORMAL);
                checkInfo.setBindTime(now);
                checkInfo.setCreateTime(now);
                checkInfo.setUpdateTime(now);
                addList.add(checkInfo);
            } else {
                BsBgwNuccSignRelation nuccSignRelation = nuccSignRelations.get(0);
                if (!data.getProtocolNo().equals(nuccSignRelation.getProtocolNo())) {
                    nuccSignRelation.setProtocolNo(data.getProtocolNo());
                    nuccSignRelation.setBindTime(now);
                    nuccSignRelation.setUpdateTime(now);
                    updateList.add(nuccSignRelation);
                } else {
                    log.info("已存在的银行卡号与协议号，过滤数据；银行卡号：" + nuccSignRelation.getCardNo() + "协议号：" + nuccSignRelation.getProtocolNo());
                }
            }
        }

        transactionTemplate.execute(new TransactionCallbackWithoutResult() {
            @Override
            protected void doInTransactionWithoutResult(TransactionStatus status) {
                //校验通过，存库
                if (CollectionUtils.isNotEmpty(addList)) {
                    log.info("新增入库数量：" + addList.size());
                    saveAddDataBatch(addList);
                }

                if (CollectionUtils.isNotEmpty(updateList)) {
                    log.info("更新入库数量：" + updateList.size());
                    saveModifyDataBatch(updateList);
                }
            }
        });
        log.info("=====网联协议号导入数据入库结束=====");
        return true;
    }

    private void saveAddDataBatch(List<BsBgwNuccSignRelation> addList) {

        // 每次最多批量处理100数据
        int addCount = addList.size();
        int count = 0;
        while (count < addCount) {
            int delCount = (addCount - count) > Constants.BATCH_PROCESSING_COUNT ? Constants.BATCH_PROCESSING_COUNT : (addCount - count);
            List list = addList.subList(count, count + delCount);
            count += delCount;

            // 批量处理
            bsBgwNuccSignRelationMapper.batchInsert(list);
        }
    }

    private void saveModifyDataBatch(List<BsBgwNuccSignRelation> updateList) {

        // 每次最多批量处理100数据
        int updateCount = updateList.size();
        int count = 0;
        while (count < updateCount) {
            int delCount = (updateCount - count) > Constants.BATCH_PROCESSING_COUNT ? Constants.BATCH_PROCESSING_COUNT : (updateCount - count);
            List list = updateList.subList(count, count + delCount);
            count += delCount;

            // 批量处理
            bsBgwNuccSignRelationMapper.batchUpdate(list);
        }
    }

    /*
     * 判断是否为整数
     * @param str 传入的字符串
     * @return 是整数返回true,否则返回false
     */
    private boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("^[0-9]*$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }
}
