package com.pinting.business.service.site.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pinting.business.enums.BaoFooEnum;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pinting.business.dao.Bs19payBankMapper;
import com.pinting.business.dao.BsBankCardMapper;
import com.pinting.business.dao.BsBankMapper;
import com.pinting.business.dao.BsCardBinMapper;
import com.pinting.business.dao.BsUserMapper;
import com.pinting.business.dao.DafyBindBankExtMapper;
import com.pinting.business.dao.DafyUserExtMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.facade.site.BankFacade;
import com.pinting.business.hessian.site.message.ReqMsg_Bank_Pay19BankList;
import com.pinting.business.hessian.site.message.ResMsg_Bank_Pay19BankList;
import com.pinting.business.model.Bs19payBank;
import com.pinting.business.model.Bs19payBankExample;
import com.pinting.business.model.BsBank;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsBankCardExample;
import com.pinting.business.model.BsBankExample;
import com.pinting.business.model.BsCardBin;
import com.pinting.business.model.BsCardBinExample;
import com.pinting.business.model.BsUser;
import com.pinting.business.model.DafyBindBankExt;
import com.pinting.business.model.DafyBindBankExtExample;
import com.pinting.business.model.DafyUserExt;
import com.pinting.business.model.DafyUserExtExample;
import com.pinting.business.model.vo.BankBinVO;
import com.pinting.business.model.vo.BankCardVO;
import com.pinting.business.model.vo.Bs19payBankVO;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.model.vo.Pay19BankVO;
import com.pinting.business.service.site.BankCardService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.Util;
import com.pinting.core.util.DateUtil;
import com.pinting.core.util.MoneyUtil;
import com.pinting.gateway.hessian.message.baofoo.B2GReqMsg_BaoFooQuickPay_CardBinQuery;
import com.pinting.gateway.hessian.message.baofoo.B2GResMsg_BaoFooQuickPay_CardBinQuery;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;
import com.pinting.gateway.hessian.message.loan.model.Limit;
import com.pinting.gateway.in.util.MethodRole;
import com.pinting.gateway.out.service.BaoFooTransportService;

@Service
public class BankCardServiceImpl implements BankCardService {
    private Logger log = LoggerFactory.getLogger(BankCardServiceImpl.class);
    @Autowired
    private BsBankCardMapper bsBankCardMapper;
    @Autowired
    private BsCardBinMapper bsCardBinMapper;
    @Autowired
    private BsBankMapper bsBankMapper;
    @Autowired
    private DafyUserExtMapper dafyUserExtMapper;
    @Autowired
    private DafyBindBankExtMapper dafyBankExtMapper;
    @Autowired
    private Bs19payBankMapper pay19BankMapper;
    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private BankFacade bankFacade;
    @Autowired
    private BaoFooTransportService baoFooTransportService;

    @Override
    public ArrayList<BsBankCard> findBankCardInfoByUserId(Integer userId) {

        BsBankCardExample example = new BsBankCardExample();
        example.createCriteria().andUserIdEqualTo(userId);
        example.setOrderByClause("status");
        return (ArrayList<BsBankCard>) bsBankCardMapper.selectByExample(example);
    }

    @Override
    public BsBankCard findBankCardInfoByCardNoAndUserId(String cardNo, Integer userId) {

        BsBankCardExample bankCardExample = new BsBankCardExample();
        bankCardExample.createCriteria().andCardNoEqualTo(cardNo).andUserIdEqualTo(userId);
        List<BsBankCard> bankCardList = bsBankCardMapper.selectByExample(bankCardExample);
        if (bankCardList != null && bankCardList.size() > 0) {
            return bankCardList.get(0);
        }
        return null;
    }

    @Override
    public BsCardBin findCardBinByCarBin(String carBin) {

        BsCardBinExample cardBinExample = new BsCardBinExample();
        cardBinExample.createCriteria().andCardBinEqualTo(carBin);

        List<BsCardBin> carBinList = bsCardBinMapper.selectByExample(cardBinExample);
        if (carBinList != null && carBinList.size() > 0) {
            return carBinList.get(0);
        }
        return null;
    }

    @Override
    public boolean addOrModifyBankCard(BsBankCard bankCard) {
        BsBankCardExample example = new BsBankCardExample();
        example.createCriteria().andCardNoEqualTo(bankCard.getCardNo())
                .andUserIdEqualTo(bankCard.getUserId());
        if (bsBankCardMapper.updateByExampleSelective(bankCard, example) == 0) {
            bsBankCardMapper.insertSelective(bankCard);
        }
        return true;
    }

    public boolean addBankCard(BsBankCard bankCard) {

        return bsBankCardMapper.insertSelective(bankCard) == 1;
    }

    @Override
    public ArrayList<BsBank> findBankByStatus(Integer status) {
        BsBankExample bankExample = new BsBankExample();
        bankExample.createCriteria().andStatusEqualTo(status);

        return (ArrayList<BsBank>) bsBankMapper.selectByExample(bankExample);
    }

    @Override
    public boolean modifyBankCardById(BsBankCard bankCardBind) {
        return bsBankCardMapper.updateByPrimaryKeySelective(bankCardBind) > 0;
    }

    @Override
    public BsBankCardVO findBankCardBindInfoByUserIdAndCardNo(Integer userId, String cardNo) {
        return bsBankCardMapper.selectBankCardBindInfoByUserIdAndCardNo(userId, cardNo);
    }

    @Override
    public DafyUserExt findBindingBankCard(Integer userId) {

        DafyUserExtExample example = new DafyUserExtExample();
        example.createCriteria().andUserIdEqualTo(userId);

        List<DafyUserExt> dafyUserList = dafyUserExtMapper.selectByExample(example);

        if (dafyUserList == null || dafyUserList.size() == 0) {
            return null;
        }
        return dafyUserList.get(0);

    }

    @Override
    public boolean modifyBankCardByUserIdAndCardNo(BsBankCard bankCardBind) {
        BsBankCardExample bankExample = new BsBankCardExample();
        bankExample.createCriteria().andUserIdEqualTo(bankCardBind.getUserId())
                .andCardNoEqualTo(bankCardBind.getCardNo());

        return bsBankCardMapper.updateBankCardByUserIdAndCardNo(bankCardBind) == 1;
    }

    @Override
    public boolean modifyBankCardByUserId(BsBankCard bankCardBind) {
        BsBankCardExample bankExample = new BsBankCardExample();
        bankExample.createCriteria().andUserIdEqualTo(bankCardBind.getUserId());

        return bsBankCardMapper.updateByExampleSelective(bankCardBind, bankExample) == 1;
    }

    @Override
    public ArrayList<BsBankCard> findBankCardByStatusAndUserId(Integer status, Integer userId) {

        BsBankCardExample example = new BsBankCardExample();
        example.createCriteria().andStatusEqualTo(status).andUserIdEqualTo(userId);

        return (ArrayList<BsBankCard>) bsBankCardMapper.selectByExample(example);
    }

    @Override
    public BsBankCard findBankCardById(Integer id) {
        return bsBankCardMapper.selectByPrimaryKey(id);
    }

    @Override
    public DafyBindBankExt findDafyBankIdByLocalBankId(Integer localBankId) {

        return dafyBankExtMapper.selectByPrimaryKey(localBankId);
    }

    @Override
    public DafyBindBankExt findBankIdByDafyBankId(Integer dafyBankId) {
        DafyBindBankExtExample example = new DafyBindBankExtExample();
        example.createCriteria().andDafyBankIdEqualTo(dafyBankId);
        List<DafyBindBankExt> list = dafyBankExtMapper.selectByExample(example);

        return list != null && list.size() > 0 ? list.get(0) : null;
    }

    @Override
    public void batchModifyBindCardInfoForTimeout(int timeout) {
        Date time = DateUtil.addSeconds(new Date(), -timeout);
        //修改dafy_user_ext表
        DafyUserExtExample example = new DafyUserExtExample();
        example.createCriteria().andCreateTimeLessThan(time)
                .andStatusEqualTo(Constants.BANK_CARD_BINDING);
        DafyUserExt dafyUserExt = new DafyUserExt();
        dafyUserExt.setStatus(Constants.BANK_CARD_BINDFAIL);
        dafyUserExt.setBindFailReson(PTMessageEnum.DAFY_REALNAME_AUTH_ERROR.getMessage());

        dafyUserExtMapper.updateByExampleSelective(dafyUserExt, example);
        //修改bs_bank_card表
        /*BsBankCardExample cardExample = new BsBankCardExample();
        cardExample.createCriteria()
        		.andBindTimeLessThan(time)
        		.andStatusEqualTo(Constants.BANK_CARD_BINDING);
        BsBankCard card = new BsBankCard();
        card.setStatus(Constants.BANK_CARD_BINDFAIL);
        bsBankCardMapper.updateByExampleSelective(card, cardExample);*/
    }

    @Override
    @MethodRole("APP")
    public BsBank findBankById(int bankId) {
        return bsBankMapper.selectByPrimaryKey(bankId);
    }

    /**
     * @see com.pinting.business.service.site.BankCardService#findBankCardVOByUserId(java.lang.Integer)
     */
    @Override
    @MethodRole("APP")
    public List<BankCardVO> findBankCardVOByStatusAndUserId(Integer userId, Integer status) {
        return bsBankCardMapper.selectBankCardVOByStatusAndUserId(userId, status);
    }

    /**
     * @see com.pinting.business.service.site.BankCardService#queryBankBin(java.lang.String)
     */
    @Override
    @MethodRole("APP")
    public BankBinVO queryBankBin(String cardNo) {
        BankBinVO result = new BankBinVO();
        Integer cardNoLength = cardNo.length();
        String sqlCardNo = cardNo;
        for (int i = 1; i < cardNoLength; i++) {
            sqlCardNo = sqlCardNo + "," + sqlCardNo.subSequence(0, i);
        }
        List<BsCardBin> cardBins = bsCardBinMapper.selectCardBin(sqlCardNo, cardNoLength);
        if (CollectionUtils.isNotEmpty(cardBins)) {
            result.setBankCardLen(cardBins.get(0).getBankCardLen());
            result.setBankId(cardBins.get(0).getBankId());
            result.setCardBin(cardBins.get(0).getCardBin());
            result.setCardBinLen(cardBins.get(0).getCardBinLen());
            result.setBankCardFuncType(cardBins.get(0).getBankCardFuncType());
            BsBank bank = bsBankMapper.selectByPrimaryKey(cardBins.get(0).getBankId());
            if (bank != null) {
                result.setBankName(bank.getName());
            }
        } else {
            //输入的卡号长度大于等于14位，且在库表中找不到对应卡bin的时候，需要调宝付接口，查询卡bin，查到后返回，且记录新的卡bin到数据库
            if (cardNoLength >= 14) {
                B2GReqMsg_BaoFooQuickPay_CardBinQuery reqCardBin = new B2GReqMsg_BaoFooQuickPay_CardBinQuery();
                reqCardBin.setBank_card_no(cardNo);
                reqCardBin.setTrans_serial_no(Util.generateUserTransNo4BaoFoo());
                B2GResMsg_BaoFooQuickPay_CardBinQuery resCardBin;
                try {
                    resCardBin = baoFooTransportService.cardBinQuery(reqCardBin);

                    if (resCardBin != null) {

                        String payCode = resCardBin.getPay_code(); //银行代码如CIB
                        //根据银行代码查询银行编号
                        Bs19payBankExample example = new Bs19payBankExample();
                        example.createCriteria().andChannelEqualTo(Constants.CHANNEL_BAOFOO)
                                .andPay19BankCodeEqualTo(payCode);
                        List<Bs19payBank> pay19BankList = pay19BankMapper.selectByExample(example);
                        if (pay19BankList != null && pay19BankList.size() > 0) {
                            String bankDescription = resCardBin.getBank_description(); //银行描述
                            String cardDescription = resCardBin.getCard_description();
                            String cardType = resCardBin.getCard_type(); //卡类型1-借记卡，2-贷记卡
                            String cardBin = resCardBin.getCard_bin();

                            BsCardBin cardBinRecord = new BsCardBin();
                            cardBinRecord.setBankCardLen(cardNo.length());
                            cardBinRecord.setCardBin(cardBin);
                            cardBinRecord.setCardBinLen(cardBin.length());
                            cardBinRecord.setCreateTime(new Date());
                            cardBinRecord.setUpdateTime(new Date());
                            cardBinRecord.setBankNameDesc(bankDescription);
                            cardBinRecord.setBankCardTypeDesc(cardDescription);
                            if ("1".equals(cardType)) {
                                cardBinRecord.setBankCardFuncType(Constants.CARD_BIN_FUNCTYPE_JIEJI);
                            } else if ("2".equals(cardType)) {
                                cardBinRecord.setBankCardFuncType(Constants.CARD_BIN_FUNCTYPE_DAIJI);
                            }
                            cardBinRecord.setBankId(pay19BankList.get(0).getBankId());
                            bsCardBinMapper.insertSelective(cardBinRecord);//新增
                            result.setBankCardLen(cardBinRecord.getBankCardLen());
                            result.setBankId(cardBinRecord.getBankId());
                            result.setCardBin(cardBinRecord.getCardBin());
                            result.setCardBinLen(cardBinRecord.getCardBinLen());
                            result.setBankCardFuncType(cardBinRecord.getBankCardFuncType());
                            BsBank bank = bsBankMapper.selectByPrimaryKey(cardBinRecord.getBankId());
                            if (bank != null) {
                                result.setBankName(bank.getName());
                            }
                        }

                    }

                } catch (Exception e) {
                    log.error("===========宝付获取卡bin失败===========");
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    /**
     * @see com.pinting.business.service.site.BankCardService#unbundling(java.lang.Integer)
     */
    @Override
    public boolean unbundling(Integer cardId, Integer userId) {
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
        BsBankCard bsBankCard = this.findFirstBankCardByUserId(userId);
        // 当前是回款卡，则不能解绑
        if (null != bsBankCard && bsBankCard.getId().equals(cardId)) {
            throw new PTMessageException(PTMessageEnum.BANK_CARD_IS_RETURN_CARD);
        } else {    // 解绑
            if (bsUser.getRecentBankCardId() == cardId) {
                bsUser.setRecentBankCardId(null);
                bsUserMapper.updateByPrimaryKey(bsUser);
            }
            BsBankCard bankCard = new BsBankCard();
            bankCard.setId(cardId);
            bankCard.setStatus(Constants.BANK_CARD_UNBIND);
            bsBankCardMapper.updateByPrimaryKeySelective(bankCard);
            return true;
        }
    }

    public static void main(String[] args) {
        BsBankCard bankCard = new BsBankCard();
        bankCard.setId(1);
        Integer q = 1;
        System.out.println(bankCard.getId() == q);
    }

    /**
     * @see com.pinting.business.service.site.BankCardService#findIsFirstBankCardByUserId(java.lang.Integer)
     */
    @Override
    @MethodRole("APP")
    public BsBankCard findFirstBankCardByUserId(Integer userId) {
        BsBankCardExample example = new BsBankCardExample();
        example.createCriteria().andUserIdEqualTo(userId).andIsFirstEqualTo(Constants.IS_FIRST_BANK_YES)
                .andStatusEqualTo(Constants.BANK_CARD_NORMAL);
        List<BsBankCard> bankCards = bsBankCardMapper.selectByExample(example);
        if (CollectionUtils.isEmpty(bankCards)) {
            return null;
        } else {
            return bankCards.get(0);
        }

    }

    @Override
    @MethodRole("APP")
    public List<BsBindBankVO> selectBindBankList(Integer userId, Integer payType, Integer bankId) {
        return bsBankCardMapper.selectBindBankList(userId, payType, bankId);
    }

    @Override
    public List<Pay19BankVO> select19PayBankList(Integer payType) {
        return pay19BankMapper.select19PayBankList(payType, null, null);
    }

    /**
     * @see com.pinting.business.service.site.BankCardService#setIsFirstBankCard(java.lang.Integer, java.lang.Integer)
     */
    @Override
    @MethodRole("APP")
    public void setIsFirstBankCard(Integer userId, Integer cardId) {
    	//2017年01月01日起不再允许用户设置回款路径
    	Date returnPathOverTime = DateUtil.parseDate(Constants.RETURN_PATH_OVER_TIME);
    	if (returnPathOverTime.compareTo(new Date()) < 0) {
    		throw new PTMessageException(PTMessageEnum.RETURN_PATH_DATE_ERROR);
		}
    	
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(userId);
        if (cardId != null) {
            bsUser.setReturnPath(Constants.RETURN_PATH_BANKCARD);
            bsUserMapper.updateByPrimaryKey(bsUser);
        } else {
            bsUser.setReturnPath(Constants.RETURN_PATH_BALANCE);
            bsUserMapper.updateByPrimaryKey(bsUser);
        }
    }

    @Override
    @MethodRole("APP")
    public BsBindBankVO selectDefaultBank(Integer userId) {
        List<BsBindBankVO> bindBankVOs = bsBankCardMapper.selectDefaultBank(userId, Constants.PAY_TYPE_QUICK, new Date());
        if (CollectionUtils.isEmpty(bindBankVOs)) {
            return null;
        }
        return bindBankVOs.get(0);
    }

    @Override
    public BsBank queryBankById(Integer bankId) {
        BsBank bsBank = bsBankMapper.selectByPrimaryKey(bankId);
        return bsBank;
    }

    @Override
    public List<Bs19payBank> select19PayNetBankList() {
        Bs19payBankExample bs19payBankExample = new Bs19payBankExample();
        bs19payBankExample.createCriteria().andChannelEqualTo(Constants.CHANNEL_HFBANK).andPayTypeEqualTo(Constants.PAY_TYPE_NET).andIsAvailableEqualTo(Constants.IS_AVAILABLE_ABLE);
        return pay19BankMapper.selectByExample(bs19payBankExample);
    }

    @Override
    @MethodRole("APP")
    public List<Pay19BankVO> selectFirstBankList(Integer payType) {
        return pay19BankMapper.selectFirstBankList(payType);
    }

    @Override
    @MethodRole("APP")
    public Pay19BankVO selectFirstBank(Integer bankId) {
        Pay19BankVO pay19BankVO = new Pay19BankVO();
        Bs19payBank bs19payBank = pay19BankMapper.selectByPrimaryKey(bankId);
        if (bs19payBank == null || bs19payBank.getBankId() == null) {
            return null;
        }
        BsBank bsBank = bsBankMapper.selectByPrimaryKey(bs19payBank.getBankId());
        if (bsBank == null) {
            return null;
        }

        pay19BankVO.setId(bs19payBank.getId());
        pay19BankVO.setBankId(bs19payBank.getBankId());
        pay19BankVO.setChannel(bs19payBank.getChannel());
        pay19BankVO.setChannelPriority(bs19payBank.getChannelPriority());
        pay19BankVO.setIsMain(bs19payBank.getIsMain());
        pay19BankVO.setPay19BankCode(bs19payBank.getPay19BankCode());
        pay19BankVO.setPayType(bs19payBank.getPayType());
        pay19BankVO.setOneTop(bs19payBank.getOneTop());
        pay19BankVO.setDayTop(bs19payBank.getDayTop());
        pay19BankVO.setMonthTop(bs19payBank.getMonthTop());
        pay19BankVO.setForbiddenStart(bs19payBank.getForbiddenStart());
        pay19BankVO.setForbiddenEnd(bs19payBank.getForbiddenEnd());
        pay19BankVO.setIsAvailable(bs19payBank.getIsAvailable());
        pay19BankVO.setNotice(bs19payBank.getNotice());
        pay19BankVO.setDailyNotice(bs19payBank.getDailyNotice());
        pay19BankVO.setBankName(bsBank.getName());
        return pay19BankVO;
    }

    @Override
    @MethodRole("APP")
    public List<Pay19BankVO> selectMainBankList(Integer payType) {
        return pay19BankMapper.selectMainBankList(payType);
    }

    @Override
    @MethodRole("APP")
    public Bs19payBank selectChannelBankInfo(Integer userId, Integer bankId) {
        //19付支持的银行通道信息list
        Bs19payBank bs19payBank = new Bs19payBank();
        ReqMsg_Bank_Pay19BankList req = new ReqMsg_Bank_Pay19BankList();
        ResMsg_Bank_Pay19BankList res = new ResMsg_Bank_Pay19BankList();
        req.setUserId(userId);
        bankFacade.pay19BankList(req, res);

        for (Map<String, Object> list : res.getBankList()) {
            if (bankId.equals(list.get("bankId"))) {
                Bs19payBank payBank = new Bs19payBank();
                bs19payBank.setId((Integer) list.get("id"));
                payBank = pay19BankMapper.selectByPrimaryKey(bs19payBank.getId());  //单独查询返回的数据
                bs19payBank.setBankId((Integer) list.get("bankId"));
                bs19payBank.setChannel((String) list.get("channel"));
                bs19payBank.setChannelPriority((Integer) list.get("channelPriority"));
                bs19payBank.setIsMain((Integer) list.get("isMain"));
                bs19payBank.setPay19BankCode((String) list.get("pay19BankCode"));
                bs19payBank.setPayType((Integer) list.get("payType"));
                bs19payBank.setOneTop(payBank.getOneTop() == null ? 0 : payBank.getOneTop());  //由于List列表里面的单次限额、单日限额、单月限额是以万为单位  ,这里需要单独查询返回
                bs19payBank.setDayTop(payBank.getDayTop() == null ? 0 : payBank.getDayTop());  //由于List列表里面的单次限额、单日限额、单月限额是以万为单位  ,这里需要单独查询返回
                bs19payBank.setMonthTop(payBank.getMonthTop() == null ? 0 : payBank.getMonthTop()); //由于List列表里面的单次限额、单日限额、单月限额是以万为单位  ,这里需要单独查询返回
                bs19payBank.setForbiddenStart((Date) list.get("forbiddenStart"));
                bs19payBank.setForbiddenEnd((Date) list.get("forbiddenEnd"));
                bs19payBank.setIsAvailable((Integer) list.get("isAvailable"));
                bs19payBank.setNotice((String) list.get("notice"));
                bs19payBank.setDailyNotice(payBank.getDailyNotice());    // 每日提示信息
            }
        }
        return bs19payBank;
    }

    @Override
    public BsBindBankVO selectSafeBankCard(Integer userId) {
        List<BsBindBankVO> list = bsBankCardMapper.selectSafeBankCard(userId);
        if (CollectionUtils.isNotEmpty(list)) {
            return list.get(0);
        } else {
            return null;
        }
    }

    @Override
    public List<BankLimit> selectBankLimitList() {
        List<BankLimit> returnList = new ArrayList<BankLimit>();
        BankLimit bankLimit = new BankLimit();
        //快捷
        bankLimit.setPayType("QUICK");
        Map<String, Limit> quickMap = new HashMap<String, Limit>();
        List<Bs19payBankVO> quickList = pay19BankMapper.bankLimitListByPayType(Constants.PAY_TYPE_HOLD);
        for (Bs19payBankVO bs19payBank : quickList) {
            Limit limit = new Limit();
            Double day = MoneyUtil.multiply(bs19payBank.getDayTop(), 100).doubleValue();
            limit.setDay(String.valueOf(day.intValue()));
            Double single = MoneyUtil.multiply(bs19payBank.getOneTop(), 100).doubleValue();
            limit.setSingle(String.valueOf(single.intValue()));
            limit.setBankName(BaoFooEnum.cardBinMap.get(String.valueOf(bs19payBank.getBankId())));
            quickMap.put(bs19payBank.getPay19BankCode(), limit);
        }
        bankLimit.setBankLimits(quickMap);
        returnList.add(bankLimit);
        bankLimit = new BankLimit();
        //代付
        bankLimit.setPayType("DF");
        //Map<String, Limit> dfMap = new HashMap<String, Limit>();
        /*List<Bs19payBankVO> dfList = pay19BankMapper.bankLimitListByPayType(Constants.PAY_TYPE_PAYMENT);
        for (Bs19payBankVO bs19payBank : dfList) {
            Limit limit = new Limit();
            Double day = MoneyUtil.multiply(bs19payBank.getDayTop(), 100).doubleValue();
            limit.setDay(String.valueOf(day.intValue()));
            Double single = MoneyUtil.multiply(bs19payBank.getOneTop(), 100).doubleValue();
            limit.setSingle(String.valueOf(single.intValue()));
            limit.setBankName(bs19payBank.getName());
            dfMap.put(bs19payBank.getPay19BankCode(), limit);
        }*/
        //bankLimit.setBankLimits(dfMap);
        returnList.add(bankLimit);
        return returnList;
    }

    @Override
    public List<Pay19BankVO> queryBankByChannelAndPayType(String channel, int payType) {
        Date now = new Date();
        List<Pay19BankVO> pay19BankVOList = pay19BankMapper.selectByChannelAndPayType(channel, payType);
        for (Pay19BankVO vo : pay19BankVOList) {
            if (null != vo.getForbiddenStart() && null != vo.getForbiddenEnd()) {
                if (now.compareTo(vo.getForbiddenStart()) >= 0 && now.compareTo(vo.getForbiddenEnd()) <= 0) {
                    vo.setIsAvailable(Constants.IS_AVAILABLE_DISABLE);
                }
            }
        }
        return pay19BankVOList;
    }

}
