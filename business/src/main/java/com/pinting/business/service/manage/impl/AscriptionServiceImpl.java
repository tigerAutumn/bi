package com.pinting.business.service.manage.impl;

import com.pinting.business.dao.*;
import com.pinting.business.model.*;
import com.pinting.business.model.vo.AscriptionChangeDetailVO;
import com.pinting.business.model.vo.AscriptionChangeUserInfoVO;
import com.pinting.business.service.manage.AscriptionService;
import com.pinting.business.util.Constants;
import com.pinting.core.cookie.CookieManager;
import com.pinting.core.exception.PTException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

/**
 * Created by 剑钊 on 2016/6/24.
 */

@Service
public class AscriptionServiceImpl implements AscriptionService {

    @Autowired
    private BsUserMapper bsUserMapper;
    @Autowired
    private TbemployeeMapper tbemployeeMapper;
    @Autowired
    private BsUserCustomerManagerMapper bsUserCustomerManagerMapper;
    @Autowired
    private BsFinanceDafyRelationMapper bsFinanceDafyRelationMapper;
    @Autowired
    private BsSubAccountMapper bsSubAccountMapper;
    @Autowired
    private BsDafyCustomerFixRecordMapper bsDafyCustomerFixRecordMapper;

    @Override
    public AscriptionChangeUserInfoVO queryOwnershipGrid(AscriptionChangeUserInfoVO req) {

        return bsUserMapper.selectOwnershipGrid(req);
    }

    @Override
    public AscriptionChangeDetailVO queryOwnershipDetail(AscriptionChangeDetailVO req) {

        String manageMobile = req.getManageMobile();
        Integer userId = req.getUserId();
        Date resultsTime = req.getResultsTime();

        //根据电话查询新客户经理信息
        req = tbemployeeMapper.selectByMobile(req.getManageMobile());

        //查到客户经理信息
        if (req != null) {

            //查询业绩节点后，要变更的详细信息
            //查询老信息
            AscriptionChangeDetailVO detailVO = bsUserMapper.selectOwnershipDetail(userId);

            detailVO.setAfterManageId(req.getAfterManageId());
            detailVO.setAfterDeptCode(req.getAfterDeptCode());
            detailVO.setAfterDeptId(req.getAfterDeptId());
            detailVO.setManageName(req.getManageName());
            detailVO.setDeptName(req.getDeptName());
            detailVO.setAfterManageName(req.getAfterManageName());
            req = bsUserMapper.selectResultsDetail(userId, resultsTime);
            detailVO.setResultsChangeCount(req.getResultsChangeCount());
            detailVO.setResultsChangeSum(req.getResultsChangeSum());
            detailVO.setManageMobile(manageMobile);
            detailVO.setUserId(userId);
            detailVO.setResultsTime(resultsTime);

            return detailVO;
        }
        return null;
    }

    @Override
    @Transactional(rollbackFor = PTException.class)
    public void modifyOwnership(AscriptionChangeDetailVO req) {

        BsDafyCustomerFixRecord record = new BsDafyCustomerFixRecord();
        record.setOpTime(new Date());

        //更新用户信息
        BsUser bsUser = bsUserMapper.selectByPrimaryKey(req.getUserId());
        Integer oldAgentId = bsUser.getAgentId();

        bsUser.setAgentId(Constants.MANAGER_AGENT_ID);
        bsUser.setRecommendId(null);
        bsUserMapper.updateByPrimaryKey(bsUser);
        record.setUserId(bsUser.getId());

        BsUserCustomerManagerExample managerExample = new BsUserCustomerManagerExample();
        List<BsSubAccount> bsSubAccountList;

        if (oldAgentId != null && Constants.MANAGER_AGENT_ID == oldAgentId) {

            managerExample.createCriteria().andUserIdEqualTo(bsUser.getId());

            //查询老客户经理和用户的关系数据
            BsUserCustomerManager bsUserCustomerManager = bsUserCustomerManagerMapper.selectByExample(managerExample).get(0);
            record.setBeforeDafyCustomerManagerId(bsUserCustomerManager.getCustomerManagerDafyId());

            //删除老客户经理和用户下属的推荐人层级关系(递归)
            Map<Integer, List<BsUser>> result = queryUserByRecommendId(bsUser.getId(), 2,new HashMap<Integer, List<BsUser>>());
            List<Integer> idList = new ArrayList<>();
            idList.add(bsUser.getId());

            if (MapUtils.isNotEmpty(result) && result.size() > 0) {

                for (List<BsUser> bsUserList : result.values()) {

                    for (BsUser bsUser1 : bsUserList) {
                        idList.add(bsUser1.getId());
                    }
                }
            }

            managerExample = new BsUserCustomerManagerExample();
            managerExample.createCriteria().andUserIdIn(idList);
            bsUserCustomerManagerMapper.deleteByExample(managerExample);

            //查询用户下面所有产品户的子账户
            bsSubAccountList = bsSubAccountMapper.selectSubAccountByUserIdAndOpenTime(req.getUserId(), null);

            if (CollectionUtils.isNotEmpty(bsSubAccountList)) {

                List<Integer> subAccountIds = new ArrayList<>();
                for (BsSubAccount bsSubAccount : bsSubAccountList) {
                    subAccountIds.add(bsSubAccount.getId());
                }
                //查询业绩表中用户与老客户经理的所有业绩数据
                BsFinanceDafyRelationExample relationExample = new BsFinanceDafyRelationExample();
                relationExample.createCriteria().andSubAccountIdIn(subAccountIds);
                List<BsFinanceDafyRelation> relationList = bsFinanceDafyRelationMapper.selectByExample(relationExample);
                if (CollectionUtils.isNotEmpty(relationList)) {

                    subAccountIds = new ArrayList<>();
                    for (BsFinanceDafyRelation relation:relationList){
                        subAccountIds.add(relation.getSubAccountId());
                    }
                    record.setBeforeSubAccountId(StringUtils.join(subAccountIds, ","));
                    //删除用户与老客户经理的所有业绩数据
                    bsFinanceDafyRelationMapper.deleteByExample(relationExample);
                }

            }
        }

        //添加新客户经理和用户下属的推荐人层级关系（递归）
        Map<Integer, List<BsUser>> result = queryUserByRecommendId(bsUser.getId(), 2,new HashMap<Integer, List<BsUser>>());
        result.put(1, new ArrayList<BsUser>());
        result.get(1).add(bsUser);

        for (Integer gradeNum : result.keySet()) {

            for (BsUser bsUser1 : result.get(gradeNum)) {

                BsUserCustomerManager bsUserCustomerManager = new BsUserCustomerManager();//存入客户经理用户关系表
                bsUserCustomerManager.setCustomerManagerDafyId(req.getAfterManageId());
                bsUserCustomerManager.setUserId(bsUser1.getId());
                bsUserCustomerManager.setGrade(gradeNum);//间接推荐
                bsUserCustomerManager.setCreateTime(new Date());
                bsUserCustomerManager.setUpdateTime(new Date());
                bsUserCustomerManagerMapper.insertSelective(bsUserCustomerManager);
            }
        }


        //查询业绩截点后用户下面所有产品户的子账户
        bsSubAccountList = bsSubAccountMapper.selectSubAccountByUserIdAndOpenTime(req.getUserId(), req.getResultsTime());

        if (CollectionUtils.isNotEmpty(bsSubAccountList)) {

            List<Integer> subAccountIds = new ArrayList<>();
            for (BsSubAccount bsSubAccount : bsSubAccountList) {
                subAccountIds.add(bsSubAccount.getId());
            }

            //添加用户与新客户经理的业绩数据
            for (BsSubAccount bsSubAccount : bsSubAccountList) {
                BsFinanceDafyRelation relation = new BsFinanceDafyRelation();
                relation.setCustomerManagerDafyId(req.getAfterManageId());
                relation.setCreateTime(new Date());
                relation.setDafyDeptCode(req.getAfterDeptCode());
                relation.setDafyDeptId(req.getAfterDeptId());
                relation.setSubAccountId(bsSubAccount.getId());
                relation.setUpdateTime(new Date());
                relation.setDafyDeptName(req.getDeptName());

                bsFinanceDafyRelationMapper.insert(relation);
            }

            record.setAfterSubAccountId(StringUtils.join(subAccountIds, ","));
        }

        record.setAfterDafyCustomerManagerId(req.getAfterManageId());
        record.setSplitTime(req.getResultsTime());
        record.setOpUserId(req.getOpUserId());
        record.setUpdateTime(new Date());
        record.setCreateTime(new Date());
        //添加操作记录
        bsDafyCustomerFixRecordMapper.insertSelective(record);
    }

    /**
     * 根据推荐人id查询用户列表
     */
    private Map<Integer, List<BsUser>> queryUserByRecommendId(Integer recommendId, Integer gradeNum,Map<Integer, List<BsUser>> userMap) {

        BsUserExample example = new BsUserExample();
        example.createCriteria().andRecommendIdEqualTo(recommendId);
        List<BsUser> result = bsUserMapper.selectByExample(example);

        if (CollectionUtils.isNotEmpty(result)) {

            userMap.put(gradeNum, result);

            ++gradeNum;
            for (BsUser bsUser : result) {
                queryUserByRecommendId(bsUser.getId(),gradeNum,userMap);
            }
        }

        return userMap;
    }


}
