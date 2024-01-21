package com.pinting.business.service.site;

import java.util.ArrayList;
import java.util.List;

import com.pinting.business.model.Bs19payBank;
import com.pinting.business.model.BsBank;
import com.pinting.business.model.BsBankCard;
import com.pinting.business.model.BsCardBin;
import com.pinting.business.model.DafyBindBankExt;
import com.pinting.business.model.DafyUserExt;
import com.pinting.business.model.vo.BankBinVO;
import com.pinting.business.model.vo.BankCardVO;
import com.pinting.business.model.vo.BsBankCardVO;
import com.pinting.business.model.vo.BsBindBankVO;
import com.pinting.business.model.vo.Pay19BankVO;
import com.pinting.gateway.hessian.message.loan.model.BankLimit;

public interface BankCardService {

    /**
     * 根据用户编号查询银行卡信息
     * 
     * @param userId 用户编号
     * @return 如果找到信息，返回用户银行卡列表，找不到则返回null
     */
    public List<BankCardVO> findBankCardVOByStatusAndUserId(Integer userId, Integer status);

    /**
     * 根据用户编号查询银行卡信息
     * @param userId 用户编号
     * @return 如果找到信息，返回用户银行卡列表，找不到则返回null
     */
    public ArrayList<BsBankCard> findBankCardInfoByUserId(Integer userId);

    /**
     * 根据卡号和userId查询银行卡
     * @param cardNo 银行卡id
     * @param userId 用户id
     * @return 如果找到信息返回银行卡信息，否则返回null
     */
    public BsBankCard findBankCardInfoByCardNoAndUserId(String cardNo, Integer userId);

    /**
     * 根据cardBin查询CardBin信息   暂时不用
     * @param carBin 卡bin
     * @return 如果找到信息，返回BsCardBin,否则返回null
     */
    public BsCardBin findCardBinByCarBin(String carBin);

    /**
     * 新增或修改银行卡
     * @param bankCard 银行卡信息
     * @return 插入成功返回true,否则放回false
     */
    public boolean addOrModifyBankCard(BsBankCard bankCard);

    /**
     * 新增银行卡
     * @param bankCard 银行卡信息
     * @return 插入成功返回true,否则放回false
     */
    public boolean addBankCard(BsBankCard bankCard);

    /**
     * 根据银行的状态查询银行信息
     * @param status 银行的状态
     * @return 查询到的所有的银行信息
     */
    public ArrayList<BsBank> findBankByStatus(Integer status);

    /**
     * 根据银行卡id号进行对银行卡更新
     * @param bankCardBind
     * @return 更新成功返回true，否则返回false
     */
    public boolean modifyBankCardById(BsBankCard bankCardBind);

    /**
     * 根据用户id号和卡号 进行对银行卡更新
     * @param bankCardBind
     * @return  更新成功返回true，否则返回false
     */
    public boolean modifyBankCardByUserIdAndCardNo(BsBankCard bankCardBind);

    /**
     * 根据用户id号  进行对银行卡更新
     * @param bankCardBind
     * @return  更新成功返回true，否则返回false
     */
    public boolean modifyBankCardByUserId(BsBankCard bankCardBind);

    /**
     * 根据用户id和卡号查询该用户该银行卡信息
     * @param userId
     * @param cardNo
     * @return 成功返回银行卡信息，否则返回null
     */
    public BsBankCardVO findBankCardBindInfoByUserIdAndCardNo(Integer userId, String cardNo);

    /**
     * 根据用户的id号查询当前用户是否在绑卡
     * @param userId
     * @return 成功返回达飞用户信息，失败返回null
     */
    public DafyUserExt findBindingBankCard(Integer userId);

    /**
     * 根据银行卡的状态和用户id，查询当前用户在该状态下所有的银行卡信息
     * @param status
     * @param userId
     * @return 返回当前用户在该状态下所有的银行卡信息，失败返回空
     */
    public ArrayList<BsBankCard> findBankCardByStatusAndUserId(Integer status, Integer userId);

    /**
     * 根据id号查询当前银行卡信息
     * @param id
     * @return 返回银行卡信息，失败返回null
     */
    public BsBankCard findBankCardById(Integer id);

    /**
     * 根据本地银行编号，查找达飞方的银行编号
     * @param localBankId
     * @return 成功则 返回 达飞银行编号，否则返回null
     */
    public DafyBindBankExt findDafyBankIdByLocalBankId(Integer localBankId);

    /**
     * 根据达飞银行编号，查找本地的银行编号
     * @param dafyBankId
     * @return 成功则 返回 本地银行编号，否则返回null
     */
    public DafyBindBankExt findBankIdByDafyBankId(Integer dafyBankId);

    /**
     * 批量修改卡绑定信息
     * @param timeout 超时时间间隔，单位为秒
     */
    public void batchModifyBindCardInfoForTimeout(int timeout);

    /**
     * 根据银行卡id号查询银行
     * @param bankId
     * @return
     */
    public BsBank findBankById(int bankId);

    /**
     * 
     * @param cardNo
     * @return
     */
    public BankBinVO queryBankBin(String cardNo);

    /**
     * 解绑银行卡
     * @param cardId
     * @return
     */
    public boolean unbundling(Integer cardId, Integer userId);
    
    /**
     * 
     * @Title: selectBindBankList 
     * @Description: 查询用户已绑定的银行卡
     * @param userId
     * @return
     * @throws
     */
    public List<BsBindBankVO> selectBindBankList(Integer userId, Integer payType, Integer bankId);
    
    /**
     * 
     * @Title: select19PayBankList 
     * @Description: 按支付类型查询19付银行列表
     * @param payType
     * @return
     * @throws
     */
    public List<Pay19BankVO> select19PayBankList(Integer payType);

    /**
     * 查询回款卡
     * @param userId
     * @return
     */
    public BsBankCard findFirstBankCardByUserId(Integer userId);

    /**
     * 设置回款路径
     * @param userId
     * @param cardId
     */
    public void setIsFirstBankCard(Integer userId, Integer cardId);
    
    /**
     * 
     * @Title: selectDefaultBank 
     * @Description: 查询最近一次使用的银行卡
     * @param userId
     * @param payType 19付银行卡的支付类型
     * @param time
     * @return
     * @throws
     */
    public BsBindBankVO selectDefaultBank(Integer userId);
    /**
     * @Title: queryBankById 
     * @Description: 根据银行ID查询银行信息
     * @param bankId  银行ID
     * @return BsBank
     * @throws
     */
	public BsBank queryBankById(Integer bankId);
	
	/**
	 * @Title: select19PayNetBankList 
	 * @Description: 查询支持的网银列表
	 * @return
	 */
	public List<Bs19payBank> select19PayNetBankList();
	
    /**
     * 
     * @Title: selectFirstBankList 
     * @Description: 按支付类型查询当前优先级最高的银行列表
     * @param payType
     * @return
     * @throws
     */
    public List<Pay19BankVO> selectFirstBankList(Integer payType);

	
	/**
     * 
     * @Title: selectFirstBank 
     * @Description: 按19pay银行id查找对应的19pay银行
     * @param BankId
     * @return
     * @throws
     */
    public Pay19BankVO selectFirstBank(Integer BankId);
    
    
    /**
     * 
     * @Title: selectMainBankList 
     * @Description: 按支付类型查询当前主通道银行列表
     * @param payType
     * @return
     * @throws
     */
    public List<Pay19BankVO> selectMainBankList(Integer payType);
    
    /**
     * 
     * @Title: selectChannelBankInfo 
     * @Description: 根据用户ID和银行ID查询适合通道的银行信息
     * @param userId  用户ID
     * @param bankId  银行ID
     * @return
     * @throws
     */
    public Bs19payBank selectChannelBankInfo(Integer userId,Integer bankId);

    /**
     * 
     * @Title: selectSafeBankCard 
     * @Description: TODO
     * @param userId
     * @return
     * @throws
     */
    public BsBindBankVO selectSafeBankCard(Integer userId);
    
    
    /**
     * 获取当前时间，银行限额列表
     * 只查快捷和代付
     * @return
     */
    public List<BankLimit> selectBankLimitList();

    /**
     * 根据支付通道和支付类型查询银行列表
     * @param channel   支付通道:PAY19-19付；REAPAL-融宝；BAOFOO-宝付
     * @param payType   支付类型1-快捷 2-代付 3-代扣 4-网银
     * @return
     */
    List<Pay19BankVO> queryBankByChannelAndPayType(String channel, int payType);
}
