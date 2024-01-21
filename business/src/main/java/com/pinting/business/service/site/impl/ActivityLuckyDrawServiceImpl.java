package com.pinting.business.service.site.impl;

import java.util.Date;
import java.util.List;
import java.util.Random;

import com.pinting.business.dao.BsActivityMapper;
import com.pinting.business.dao.BsRedPacketCheckMapper;
import com.pinting.business.dao.BsRedPacketInfoMapper;
import com.pinting.business.enums.PTMessageEnum;
import com.pinting.business.enums.RedisLockEnum;
import com.pinting.business.exception.PTMessageException;
import com.pinting.business.model.BsActivity;
import com.pinting.business.model.BsActivityLuckyDrawExample;
import com.pinting.business.model.BsRedPacketCheck;
import com.pinting.business.model.BsRedPacketCheckExample;
import com.pinting.business.model.BsRedPacketInfoExample;
import com.pinting.business.service.site.RedPacketService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.pinting.business.dao.BsActivityLuckyDrawMapper;
import com.pinting.business.dao.BsSubAccountMapper;
import com.pinting.business.model.BsActivityLuckyDraw;
import com.pinting.business.model.vo.BsActivityLuckyDrawVO;
import com.pinting.business.model.vo.BsSubAc4ActivityVO;
import com.pinting.business.service.site.ActivityLuckyDrawService;
import com.pinting.business.util.Constants;
import com.pinting.business.util.LuckyDrawUtil;
import com.pinting.core.redis.JedisClientDaoSupport;
import com.pinting.core.util.DateUtil;
import org.springframework.util.CollectionUtils;


@Service
public class ActivityLuckyDrawServiceImpl implements ActivityLuckyDrawService {
    private final Logger logger = LoggerFactory.getLogger(ActivityLuckyDrawServiceImpl.class);
	private static JedisClientDaoSupport jsClientDaoSupport = JedisClientDaoSupport.getInstance(Constants.REDIS_SUBSYS_BUSINESS);
    @Autowired
    private BsActivityLuckyDrawMapper bsActivityLuckyDrawMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsRedPacketInfoMapper bsRedPacketInfoMapper;
    @Autowired
    private BsRedPacketCheckMapper bsRedPacketCheckMapper;
    @Autowired
    private RedPacketService redPacketService;
    @Autowired
	private BsActivityMapper bsActivityMapper;

    @Override
    public List<BsActivityLuckyDrawVO> getLuckyAllList(Integer activityId, Integer limitNum) {

        return bsActivityLuckyDrawMapper.getLuckyAllList(activityId,limitNum);
    }
    
    @Override
    public List<BsActivityLuckyDrawVO> getLuckyLenders(Integer activityId) {
        return bsActivityLuckyDrawMapper.getLuckyLenders(activityId);
    }
    
    @Override
    public List<BsActivityLuckyDrawVO> getLuckyLendersByDate(Integer activityId, String backDrawTime) {
        return bsActivityLuckyDrawMapper.getLuckyLendersByDate(activityId, backDrawTime);
    }

    @Override
    public int countLuckyNum(Integer activityId) {
        Integer count = bsActivityLuckyDrawMapper.countLuckyNum(activityId);
        return count == null ? 0 : count;
    }

    @Override
    public List<BsActivityLuckyDrawVO> getUserLuckyList(Integer activityId,
                                                        Integer userId, String luckyFlag, Integer start, Integer page) {

        return bsActivityLuckyDrawMapper.getUserLuckyList(activityId, userId, luckyFlag, start, page);
    }

    @Override
    public int countUserLucky(Integer activityId, Integer userId,
                              String luckyFlag) {
        Integer count = bsActivityLuckyDrawMapper.countUserLucky(activityId, userId, luckyFlag);
        return count == null ? 0 : count;
    }

    @Override
    public void updateUserDraw(BsActivityLuckyDraw activityLuckyDraw) {
        activityLuckyDraw.setIsUserDraw("Y");
        activityLuckyDraw.setIsWin("Y");
        activityLuckyDraw.setUserDrawTime(new Date());
        activityLuckyDraw.setUpdateTime(new Date());
        bsActivityLuckyDrawMapper.updateByPrimaryKeySelective(activityLuckyDraw);
    }

    /**
     * 活动期间购买安心投、稳定收、长安赢任意产品大于等于5000元可获得一次抽奖机会
     * 新注册用户在购买新手标满20000元后可获得一次抽奖机会
     */
    @Override
    public void addActivityLuckyDraw(Integer userId, Double amount,
                                     String productName) {
        Date startTime = DateUtil.parseDateTime(Constants.ACTIVITY_START_TIME);
        Date endTime = DateUtil.parseDateTime(Constants.ACTIVITY_END_TIME);
        Date now = new Date();
        if (startTime.compareTo(now) > 0) {
            logger.info("==============618活动，开始==============");
        }
        if (endTime.compareTo(now) < 0) {
            logger.info("==============618活动，已结束==============");
        }
        if (startTime.compareTo(now) <= 0 && endTime.compareTo(now) >= 0) {
            //统一信息初始化
            BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
            activityLuckyDraw.setActivityId(1);
            activityLuckyDraw.setUserId(userId);
            activityLuckyDraw.setIsBackDraw("Y");//是否后台抽奖-Y
            activityLuckyDraw.setBackDrawTime(new Date());
            activityLuckyDraw.setIsUserDraw("N");//用户是否抽奖-N
            activityLuckyDraw.setIsWin("N");//是否中奖-N
            activityLuckyDraw.setIsConfirm("N");//是否兑奖-N
            activityLuckyDraw.setCreateTime(new Date());
            activityLuckyDraw.setUpdateTime(new Date());
            activityLuckyDraw.setNote(amount.toString());

            /**
             * 判断获得的奖品
             */
            if (StringUtils.isNotBlank(productName)) {
                activityLuckyDraw = getLuckyAward(activityLuckyDraw, amount, productName);


                if (activityLuckyDraw.getAwardId() != null) {
                    logger.info("==============618活动，投资产品名：" + productName + "，投资金额：" + amount
                            + ",获得奖品：" + activityLuckyDraw.getAwardId() + "==============");
                    bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
                }
            } else {
                logger.info("==============618活动，未获取到产品名称==============");
            }

        }

    }

    @Override
    public int getLuckyAward(Double amount, int productTerm) {

        //随机获得的幸运数字[1,10000]
        int luckyNum = LuckyDrawUtil.luckyNumber();

        //单笔投资金额不小于20W的
        if (amount.compareTo(200000.0) >= 0) {
            //判断购买产品的期限
            switch (productTerm) {

                case 3:
                    //3个月概率 0.5%获取1 1%获取2 2%获取3 96.5%获取4
                    if (luckyNum > 9950) {
                        return 24;
                    } else if (luckyNum > 9850) {
                        return 23;
                    } else if (luckyNum > 9650) {
                        return 22;
                    } else {
                        return 21;
                    }
                case 6:
                    //6个月概率  1%获得1	4%获得2	8%获得3	87%获得4
                    if (luckyNum > 9900) {
                        return 24;
                    } else if (luckyNum > 9500) {
                        return 23;
                    } else if (luckyNum > 8700) {
                        return 22;
                    } else {
                        return 21;
                    }
                case 12:
                    //12个月概率 2%获得1	8%获得2	15%获得3  75%获得4
                    if (luckyNum > 9800) {
                        return 24;
                    } else if (luckyNum > 9000) {
                        return 23;
                    } else if (luckyNum > 7500) {
                        return 22;
                    } else {
                        return 21;
                    }
                default:
                    break;
            }
        }
        //单笔投资额小于20W且不小于10W
        else if (amount.compareTo(100000.0) >= 0) {
            //判断购买产品的期限
            switch (productTerm) {

                case 3:
                    //3个月概率 0.5%获取2 1%获取3 2%获取4 96.5%获取5
                    if (luckyNum > 9950) {
                        return 23;
                    } else if (luckyNum > 9850) {
                        return 22;
                    } else if (luckyNum > 9650) {
                        return 21;
                    } else {
                        return 20;
                    }
                case 6:
                    //6个月概率  1%获得2	4%获得3	10%获得4	85%获得5
                    if (luckyNum > 9900) {
                        return 23;
                    } else if (luckyNum > 9500) {
                        return 22;
                    } else if (luckyNum > 8500) {
                        return 21;
                    } else {
                        return 20;
                    }
                case 12:
                    //12个月概率 2%获得2	8%获得3	15%获得4  75%获得5
                    if (luckyNum > 9800) {
                        return 23;
                    } else if (luckyNum > 9000) {
                        return 22;
                    } else if (luckyNum > 7500) {
                        return 21;
                    } else {
                        return 20;
                    }
                default:
                    break;
            }
        }
        //单笔投资金额小于10W且不小于5W
        else if (amount.compareTo(50000.0) >= 0) {
            //判断购买产品的期限
            switch (productTerm) {

                case 3:
                    //3个月概率 0.5%获取3 2%获取4 4%获取5 93.5%获取6
                    if (luckyNum > 9950) {
                        return 22;
                    } else if (luckyNum > 9750) {
                        return 21;
                    } else if (luckyNum > 9350) {
                        return 20;
                    } else {
                        return 19;
                    }
                case 6:
                    //6个月概率  1%获得3	4%获得4	10%获得5	 85%获得6
                    if (luckyNum > 9900) {
                        return 22;
                    } else if (luckyNum > 9500) {
                        return 21;
                    } else if (luckyNum > 8500) {
                        return 20;
                    } else {
                        return 19;
                    }
                case 12:
                    //12个月概率 2%获得3	8%获得4	15%获得5  75%获得6
                    if (luckyNum > 9800) {
                        return 22;
                    } else if (luckyNum > 9000) {
                        return 21;
                    } else if (luckyNum > 7500) {
                        return 20;
                    } else {
                        return 19;
                    }
                default:
                    break;
            }
        }
        //单笔投资金额小于5W且不小于2W
        else if (amount.compareTo(20000.0) >= 0) {
            //判断购买产品的期限
            switch (productTerm) {

                case 3:
                    //3个月概率 1%获取5 1%获取6 3%获取7 95%获取8
                    if (luckyNum > 9900) {
                        return 20;
                    } else if (luckyNum > 9800) {
                        return 19;
                    } else if (luckyNum > 9500) {
                        return 18;
                    } else {
                        return 17;
                    }
                case 6:
                    //6个月概率  1%获得5	4%获得6	10%获得7	 85%获得8
                    if (luckyNum > 9900) {
                        return 20;
                    } else if (luckyNum > 9500) {
                        return 19;
                    } else if (luckyNum > 8500) {
                        return 18;
                    } else {
                        return 17;
                    }
                case 12:
                    //12个月概率 2%获得5	8%获得6	15%获得7  75%获得8
                    if (luckyNum > 9800) {
                        return 20;
                    } else if (luckyNum > 9000) {
                        return 19;
                    } else if (luckyNum > 7500) {
                        return 18;
                    } else {
                        return 17;
                    }
                default:
                    break;
            }
        }
        //单笔投资金额小于2W且不小于1W
        else if (amount.compareTo(10000.0) >= 0) {

            //判断购买产品的期限
            switch (productTerm) {

                case 3:
                    //3个月概率 1%获取6 2%获取7 2%获取8 95%获取9
                    if (luckyNum > 9900) {
                        return 19;
                    } else if (luckyNum > 9700) {
                        return 18;
                    } else if (luckyNum > 9500) {
                        return 17;
                    } else {
                        return 16;
                    }
                case 6:
                    //6个月概率  1%获得6	5%获得7	10%获得8	 84%获得9
                    if (luckyNum > 9900) {
                        return 19;
                    } else if (luckyNum > 9400) {
                        return 18;
                    } else if (luckyNum > 8400) {
                        return 17;
                    } else {
                        return 16;
                    }
                case 12:
                    //12个月概率 3%获得6	10%获得7	 20%获得8  67%获得9
                    if (luckyNum > 9700) {
                        return 19;
                    } else if (luckyNum > 8700) {
                        return 18;
                    } else if (luckyNum > 6700) {
                        return 17;
                    } else {
                        return 16;
                    }
                default:
                    break;
            }
        }
        //单笔投资金额小于1W且不小于5K
        else if (amount.compareTo(5000.0) >= 0) {

            //判断购买产品的期限
            switch (productTerm) {

                case 3:
                    //3个月概率 1%获取7 2%获取8 2%获取9 95%获取10
                    if (luckyNum > 9900) {
                        return 18;
                    } else if (luckyNum > 9700) {
                        return 17;
                    } else if (luckyNum > 9500) {
                        return 16;
                    } else {
                        return 15;
                    }
                case 6:
                    //6个月概率  2%获得7	4%获得8	10%获得9	 84%获得10
                    if (luckyNum > 9800) {
                        return 18;
                    } else if (luckyNum > 9400) {
                        return 17;
                    } else if (luckyNum > 8400) {
                        return 16;
                    } else {
                        return 15;
                    }
                case 12:
                    //12个月概率 3%获得7	10%获得8	 20%获得9  67%获得10
                    if (luckyNum > 9700) {
                        return 18;
                    } else if (luckyNum > 8700) {
                        return 17;
                    } else if (luckyNum > 6700) {
                        return 16;
                    } else {
                        return 15;
                    }
                default:
                    break;
            }
        }
        //单笔投资金额小于5W且不小于2K
        else if (amount.compareTo(2000.0) >= 0) {

            //判断购买产品的期限
            switch (productTerm) {

                case 3:
                    //3个月概率 3%获取10 97%获取11
                    if (luckyNum > 9700) {
                        return 15;
                    }else {
                        return 14;
                    }
                case 6:
                    //6个月概率  20%获得10  80%获得11
                    if (luckyNum > 8000) {
                        return 15;
                    } else {
                        return 14;
                    }
                case 12:
                    //12个月概率 70%获得10 	30%获得11
                    if (luckyNum > 3000) {
                        return 15;
                    }else {
                        return 14;
                    }
                default:
                    break;
            }
        }

        return 0;
    }

    @Override
    public boolean judgeUserDrawed161Packet(Integer userId, String redPacketName) {
        BsRedPacketCheckExample bsRedPacketCheckExample = new BsRedPacketCheckExample();
        bsRedPacketCheckExample.createCriteria().andSerialNameLike("%"+redPacketName+"%");
        List<BsRedPacketCheck> bsRedPacketCheckList = bsRedPacketCheckMapper.selectByExample(bsRedPacketCheckExample);
        if(!CollectionUtils.isEmpty(bsRedPacketCheckList)) {
        	for (BsRedPacketCheck bsRedPacketCheck : bsRedPacketCheckList) {
        		BsRedPacketInfoExample bsRedPacketInfoExample = new BsRedPacketInfoExample();
                bsRedPacketInfoExample.createCriteria().andUserIdEqualTo(userId).andSerialNoEqualTo(bsRedPacketCheck.getSerialNo());
                int count = bsRedPacketInfoMapper.countByExample(bsRedPacketInfoExample);
                if(count > 0) return true;
			}
        }
        return false;
    }

    @Override
    public void openPacket(Integer userId, String redPacketName) {
    	jsClientDaoSupport.lock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
    	try {
    		if (!this.judgeUserDrawed161Packet(userId,redPacketName)) {
                // 未发放，发放161红包
                redPacketService.double11ActivityAutoRedPacketSend(userId, redPacketName);
            }else{
            	throw new PTMessageException(PTMessageEnum.RED_PACKET_NOT_ELIGIBLE.getCode(), "很抱歉，您已经拆过红包");
            }
		}finally {
            jsClientDaoSupport.unlock(RedisLockEnum.LOCK_ACTIVITY_LUCKY_DRAW.getKey());
        }
    }

    @Override
    public BsActivityLuckyDraw saveRedPacketActivityLuckyDraw(Integer userId, Integer activityId, Integer awardId) {
        BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
        activityLuckyDraw.setActivityId(activityId);
        activityLuckyDraw.setCreateTime(new Date());
        activityLuckyDraw.setUserId(userId);
        activityLuckyDraw.setAwardId(awardId);
        activityLuckyDraw.setIsBackDraw("Y");
        activityLuckyDraw.setIsBackDraw("Y");//是否后台抽奖-Y
        activityLuckyDraw.setBackDrawTime(new Date());
        activityLuckyDraw.setIsUserDraw("N");//用户是否抽奖-N
        activityLuckyDraw.setIsWin("N");//是否中奖-N
        activityLuckyDraw.setIsConfirm("N");//是否兑奖-N
        activityLuckyDraw.setCreateTime(new Date());
        activityLuckyDraw.setUpdateTime(new Date());
        activityLuckyDraw.setIsAutoConfirm("Y");
        bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
        return activityLuckyDraw;
    }

    private BsActivityLuckyDraw getLuckyAward(BsActivityLuckyDraw activityLuckyDraw, Double amount, String productName) {
        int luckyNum = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
        if (productName.indexOf("新手") >= 0 && amount.compareTo(20000.0) >= 0) {
            //新手标60%获得1，40%获得2
            if (luckyNum <= 6000) {
                activityLuckyDraw.setAwardId(1);
            } else {
                activityLuckyDraw.setAwardId(2);
            }
            activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y

            return activityLuckyDraw;
        }
        //安心投、稳定收、长安赢
        if (productName.indexOf("安心投") >= 0 || productName.indexOf("稳定收") >= 0 || productName.indexOf("长安赢") >= 0) {
            if (amount.compareTo(5000.0) >= 0 && amount.compareTo(10000.0) < 0) {
                //5千-1万，20%获得1，79%获得2，1%获得3
                if (luckyNum <= 2000) {
                    activityLuckyDraw.setAwardId(1);
                } else if (luckyNum > 2000 && luckyNum <= 9900) {
                    activityLuckyDraw.setAwardId(2);
                } else {
                    activityLuckyDraw.setAwardId(3);
                }
                activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y

                return activityLuckyDraw;
            } else if (amount.compareTo(10000.0) >= 0 && amount.compareTo(30000.0) < 0) {
                //1万-3万，49%获得2，50%获得3,1%获得4
                if (luckyNum <= 4900) {
                    activityLuckyDraw.setAwardId(2);
                } else if (luckyNum > 4900 && luckyNum <= 9900) {
                    activityLuckyDraw.setAwardId(3);
                } else {
                    activityLuckyDraw.setAwardId(4);
                }
                activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y
                return activityLuckyDraw;
            } else if (amount.compareTo(30000.0) >= 0 && amount.compareTo(50000.0) < 0) {
                //3万-5万，10%获得4，80%获得3,10%获得2
                if (luckyNum <= 1000) {
                    activityLuckyDraw.setAwardId(2);
                } else if (luckyNum > 1000 && luckyNum <= 9000) {
                    activityLuckyDraw.setAwardId(3);
                } else {
                    activityLuckyDraw.setAwardId(4);
                }
                activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y
                return activityLuckyDraw;
            } else if (amount.compareTo(50000.0) >= 0 && amount.compareTo(100000.0) < 0) {
                //5万-10万，20%获得5，50%获得4，30%获得3
                if (luckyNum <= 3000) {
                    activityLuckyDraw.setAwardId(3);
                } else if (luckyNum > 3000 && luckyNum <= 8000) {
                    activityLuckyDraw.setAwardId(4);
                } else {
                    activityLuckyDraw.setAwardId(5);
                }
                activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y
                return activityLuckyDraw;
            } else if (amount.compareTo(100000.0) >= 0 && amount.compareTo(300000.0) < 0) {
                //10万-30万，20%获得6/7/8，50%获得5，30%获得4
                if (luckyNum <= 3000) {
                    activityLuckyDraw.setAwardId(4);
                    activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y
                } else if (luckyNum > 3000 && luckyNum <= 8000) {
                    activityLuckyDraw.setAwardId(5);
                    activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y
                } else {
                    int luckyNum2 = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
                    if (luckyNum2 <= 3334) {
                        activityLuckyDraw.setAwardId(6);
                    } else if (luckyNum2 > 3334 && luckyNum2 <= 6667) {
                        activityLuckyDraw.setAwardId(7);
                    } else {
                        activityLuckyDraw.setAwardId(8);
                    }
                    activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                }
                return activityLuckyDraw;
            } else if (amount.compareTo(300000.0) >= 0 && amount.compareTo(500000.0) < 0) {

                //第一个30万100%获得--判断是否符合
                int countByAmount = bsActivityLuckyDrawMapper.getCountByAmount(300000, 500000);
                if (countByAmount == 0) {
                    int luckyNum3 = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
                    if (luckyNum3 <= 5000) {
                        activityLuckyDraw.setAwardId(9);
                    } else {
                        activityLuckyDraw.setAwardId(10);
                    }
                    activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                    return activityLuckyDraw;
                } else {
                    //30万-50万，20%获得9/10，60%获得6/7/8，20%获得5
                    if (luckyNum <= 2000) {
                        activityLuckyDraw.setAwardId(5);
                        activityLuckyDraw.setIsAutoConfirm("Y");//奖励金--是否自动兑奖--Y
                    } else if (luckyNum > 2000 && luckyNum <= 8000) {
                        int luckyNum2 = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
                        if (luckyNum2 <= 3334) {
                            activityLuckyDraw.setAwardId(6);
                        } else if (luckyNum2 > 3334 && luckyNum2 <= 6667) {
                            activityLuckyDraw.setAwardId(7);
                        } else {
                            activityLuckyDraw.setAwardId(8);
                        }
                        activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                    } else {
                        int luckyNum3 = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
                        if (luckyNum3 <= 5000) {
                            activityLuckyDraw.setAwardId(9);
                        } else {
                            activityLuckyDraw.setAwardId(10);
                        }
                        activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                    }
                    return activityLuckyDraw;
                }
            } else if (amount.compareTo(500000.0) >= 0 && amount.compareTo(1000000.0) < 0) {
                //第一个50万100%获得--判断是否符合
                int countByAmount = bsActivityLuckyDrawMapper.getCountByAmount(500000, 1000000);
                if (countByAmount == 0) {
                    activityLuckyDraw.setAwardId(11);
                    activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                    return activityLuckyDraw;
                } else {
                    //40%获得11，60%获得9/10
                    if (luckyNum <= 6000) {
                        int luckyNum3 = LuckyDrawUtil.luckyNumber(); //随机获得的幸运数字[1,10000]
                        if (luckyNum3 <= 5000) {
                            activityLuckyDraw.setAwardId(9);
                        } else {
                            activityLuckyDraw.setAwardId(10);
                        }
                    } else {
                        activityLuckyDraw.setAwardId(11);
                    }

                    activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                    return activityLuckyDraw;
                }
            } else if (amount.compareTo(1000000.0) >= 0) {
                //第一个100万100%获得--判断是否符合
                int countByAmount = bsActivityLuckyDrawMapper.getCountByAmount(1000000, null);
                if (countByAmount == 0) {
                    activityLuckyDraw.setAwardId(12);
                    activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                    return activityLuckyDraw;
                } else {
                    //40%获得12，60%获得11
                    if (luckyNum <= 6000) {
                        activityLuckyDraw.setAwardId(11);
                    } else {
                        activityLuckyDraw.setAwardId(12);
                    }
                    activityLuckyDraw.setIsAutoConfirm("N");//实物--是否自动兑奖--N
                    return activityLuckyDraw;
                }
            }
        }
        return activityLuckyDraw;
    }


	@Override
	public void addActivityLuckyDrawDouble11(Integer userId, Double amount,
			Integer subAccountId) {
		
		/*1、根据购买金额是否大于等于2000,活动时间-是否开始
		 *2、根据用户id和subAccountId查询用户使用的红包
		 *3、根据红包名称判断是否获得奖品
		 *4、根据概率取的奖品id，并存储
		 * */
		
		BsActivity active = bsActivityMapper.selectByPrimaryKey(2);
		Date now = new Date();
		if(now.compareTo(active.getStartTime()) >0 && now.compareTo(active.getEndTime()) < 0 && amount >= 2000){
			BsSubAc4ActivityVO vo = bsSubAccountMapper.select4ActivityBuy(userId, subAccountId);
			if(vo != null){
				String showTerminal = vo.getShowTerminal()+",";
				if((showTerminal.contains("APP,") || showTerminal.contains("H5,") || showTerminal.contains("PC,")) && vo.getTerm() != null && vo.getTerm() >=3 
						&& (vo.getSerialName().startsWith("双11专享") || vo.getSerialName().startsWith("双11奖励"))){
					Integer awardId = getLuckyAward(amount,vo.getTerm());
					if(awardId != null && awardId >=14 ){
						BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
						//统一信息初始化
			            activityLuckyDraw.setActivityId(2);
			            activityLuckyDraw.setAwardId(awardId);
			            activityLuckyDraw.setUserId(userId);
			            activityLuckyDraw.setIsBackDraw("Y");//是否后台抽奖-Y
			            activityLuckyDraw.setBackDrawTime(new Date());
			            activityLuckyDraw.setIsUserDraw("N");//用户是否抽奖-N
			            activityLuckyDraw.setIsWin("N");//是否中奖-N
			            activityLuckyDraw.setIsConfirm("N");//是否兑奖-N
			            activityLuckyDraw.setCreateTime(new Date());
			            activityLuckyDraw.setUpdateTime(new Date());
			            activityLuckyDraw.setNote("投资金额"+amount.toString());
			            if(awardId >= 14 && awardId <= 18){
							//奖励金--是否自动兑奖--Y
							activityLuckyDraw.setIsAutoConfirm("Y");
						}else if(awardId>18 && awardId <= 24){
							activityLuckyDraw.setIsAutoConfirm("N");
						}
						
						logger.info("==============双11活动，用户id："+userId+"，投资期限：" + vo.getTerm() 
								+ "，投资金额：" + amount + "，使用红包：" + vo.getSerialName()
		                        + "，获得奖品：" + activityLuckyDraw.getAwardId() + "==============");
		                bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
					}else{
						logger.info("==============双11活动，用户id："+userId+"，投资期限：" + vo.getTerm()+ "，投资金额：" + amount + "，使用红包：" + vo.getSerialName()+"，无奖品==============");
					}
				}else{
					logger.info("==============双11活动，用户id："+userId+"，投资期限：" + vo.getTerm()+ "，投资金额：" + amount + "，使用红包：" + vo.getSerialName()+"，无奖品==============");
				}
			}
		}
		
	}

	@Override
	public void addLuckyLenders(Integer userId, Integer awardId, String note) {
		BsActivityLuckyDraw activityLuckyDraw = new BsActivityLuckyDraw();
		//统一信息初始化
        activityLuckyDraw.setActivityId(Constants.LUCKY_LENDERS_ACTIVITY_ID);
        activityLuckyDraw.setAwardId(awardId);
        activityLuckyDraw.setUserId(userId);
        activityLuckyDraw.setIsBackDraw("Y");//是否后台抽奖-Y
        activityLuckyDraw.setBackDrawTime(new Date());
        activityLuckyDraw.setIsUserDraw("N");//用户是否抽奖-N
        activityLuckyDraw.setUserDrawTime(new Date());
        activityLuckyDraw.setIsWin("Y");//是否中奖-N
        activityLuckyDraw.setIsConfirm("N");//是否兑奖-N
        activityLuckyDraw.setConfirmTime(new Date());
        activityLuckyDraw.setIsAutoConfirm("Y");//是否自动兑奖-Y
        activityLuckyDraw.setCreateTime(new Date());
        activityLuckyDraw.setUpdateTime(new Date());
        activityLuckyDraw.setNote(note);
        bsActivityLuckyDrawMapper.insertSelective(activityLuckyDraw);
	}
	
	@Override
	public int selectUserLuckyDraws(Integer userId, Integer activityId) {
		BsActivityLuckyDrawExample example = new BsActivityLuckyDrawExample();
		example.createCriteria().andUserIdEqualTo(userId).andActivityIdEqualTo(activityId);
		
		List<BsActivityLuckyDraw> list = bsActivityLuckyDrawMapper.selectByExample(example);
		
		return list.size();
	}

}
