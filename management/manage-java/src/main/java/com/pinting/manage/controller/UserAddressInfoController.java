package com.pinting.manage.controller;

import com.pinting.business.model.BsUser;
import com.pinting.business.model.BsUserAddressInfo;
import com.pinting.business.model.vo.UserAddressInfoVO;
import com.pinting.business.service.manage.UserAddressInfoService;
import com.pinting.core.util.StringUtil;
import com.pinting.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 用户地址管理Controller
 *
 * @author shh
 * @date 2018/5/28 14:45
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
@RequestMapping(value = "/userAddressInfo")
public class UserAddressInfoController {

    private Logger log = LoggerFactory.getLogger(UserAddressInfoController.class);

    @Autowired
    private UserAddressInfoService userAddressInfoService;

    @RequestMapping(value = "/getList")
    public String getList(String userName, String mobile, Integer pageNum, Integer numPerPage,  Map<String, Object> model) {
        pageNum = pageNum == null ? Constants.MANAGE_DEFAULT_PAGENUM : pageNum;
        numPerPage = numPerPage == null ? Constants.MANAGE_DEFAULT_NUMPERPAGE : numPerPage;
        if (StringUtils.isNotEmpty(userName)) {
            userName = userName.trim();
        }
        if (StringUtils.isNotEmpty(mobile)) {
            mobile = mobile.trim();
        }

        Integer count = 0;
        count = userAddressInfoService.queryUserAddressInfoCount(userName, mobile);
        if(count > 0) {
            List<UserAddressInfoVO> resultList = userAddressInfoService.queryUserAddressInfoList(userName, mobile, pageNum, numPerPage);
            model.put("resultList", resultList);
        }
        model.put("count", count);
        // 数据返回给页面
        model.put("userName", userName);
        model.put("mobile", mobile);
        model.put("pageNum", pageNum);
        model.put("numPerPage", numPerPage);
        return "/bsuseraddressinfo/userAddressInfo_index";
    }

    /**
     * 进入新增页面-列表页新增按钮
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/detailSingle")
    public String detail(HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        return "/bsuseraddressinfo/detail_Single";
    }

    /**
     * 列表页-保存操作
     * @param userName 姓名
     * @param mobile 注册手机号
     * @param consignee 收货人
     * @param consigneeMobile 收货人手机号码
     * @param consigneeAddress 收货人地址
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/saveSingle")
    public @ResponseBody
    Map<Object, Object> saveUserAddressInfoSingle(String userName, String mobile, String consignee, String consigneeMobile, String consigneeAddress,
                             HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {

        // 1、校验用户手机号与姓名与bs_user表是否一致
        List<BsUser> bsUserList = userAddressInfoService.queryUserByMobileAndName(userName, mobile);
        if(CollectionUtils.isEmpty(bsUserList)) {
            log.info("用户不存在或用户手机号与姓名不一致");
            return ReturnDWZAjax.toAjaxString("301", "用户不存在或用户手机号与姓名不一致");
        }

        BsUserAddressInfo info = new BsUserAddressInfo();
        if(StringUtil.isNotBlank(consignee)) {
            info.setConsignee(consignee.trim());
        }
        if(StringUtil.isNotBlank(consigneeMobile)) {
            info.setConsigneeMobile(consigneeMobile.trim());
        }
        if(StringUtil.isNotBlank(consigneeAddress)) {
            info.setConsigneeAddress(consigneeAddress.trim());
        }
        info.setUserId(bsUserList.get(0).getId());

        // 2、校验地址信息是否已存在（数据唯一性校验：userId、收货人、收货手机号、收货地址）
        List<BsUserAddressInfo> resultList = userAddressInfoService.uniquenessCheck(info);
        if(CollectionUtils.isNotEmpty(resultList)) {
            log.info("新增的数据，在地址信息表中已存在");
            return ReturnDWZAjax.toAjaxString("302", "地址信息已存在，请重新输入");
        }

        // 3、把userId对应的其他地址信息默认值更新为NO
        try {
            userAddressInfoService.updateIsDefaultForNo(bsUserList.get(0).getId());
            log.error("userId：" + bsUserList.get(0).getId()+"默认值更新成功");
        }catch(Exception e) {
            log.error("用户地址管理信息更新失败", e);
            return ReturnDWZAjax.fail("操作失败！");
        }

        // 4、新增的地址为默认地址
        info.setIsDefault(Constants.USER_ADDRESS_INFO_IS_DEFAULT_YES);
        info.setCreateTime(new Date());
        info.setUpdateTime(new Date());

        try {
            // 5、新增
            userAddressInfoService.addUserAddressInfoSingle(info);
            return ReturnDWZAjax.success("新增成功！");
        }catch(Exception e) {
            log.error("用户地址管理信息新增失败", e);
            return ReturnDWZAjax.fail("操作失败！");
        }

    }

    /**
     * 删除-列表页删除按钮
     * @param userId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/deleteSingle")
    public @ResponseBody Map<Object, Object> delete(Integer userId, HttpServletRequest request, Map<String, Object> model) {
        try {
            // 查询数据是否存在
            List<BsUserAddressInfo> resultList = userAddressInfoService.queryAddressInfoByUserId(userId);
            if(CollectionUtils.isEmpty(resultList)) {
                return ReturnDWZAjax.success("该数据已被删除，请刷新页面！");
            }else {
                // 删除
                userAddressInfoService.deleteByUserId(userId);
                return ReturnDWZAjax.success("删除成功！");
            }
        }catch(Exception e) {
            log.error("userId：" + userId + "的用户地址管理信息删除失败", e);
            return ReturnDWZAjax.fail("操作失败！");
        }
    }

    /**
     * 查看地址详情
     * @param userId
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/detail_review")
    public String detailReview(Integer userId, Integer detailModifiUserIdFalg,  HttpServletRequest request, HttpServletResponse response,
                               Map<String, Object> model) {
        List<UserAddressInfoVO> result = userAddressInfoService.queryDetailReview(userId);
        if(CollectionUtils.isNotEmpty(result)) {
            model.put("detailUserName", result.get(0).getUserName());
            model.put("detailMobile", result.get(0).getMobile());
            model.put("addressInfoDetailList", result);
        }
        model.put("detailUserId", userId);
        return "/bsuseraddressinfo/detail_review";
    }

    /**
     * 查看详情中删除-删除地址表的一条记录
     * @param addressInfoId
     * @param request
     * @param model
     * @return
     */
    @RequestMapping("/detailDelete")
    public @ResponseBody Map<Object, Object> detailDelete(Integer addressInfoId, Integer detailModifiUserId, String isDefault,
                                                          HttpServletRequest request, Map<String, Object> model) {
        try {
            Map<Object, Object> map = new HashMap<>();
            map.put("userId", detailModifiUserId);

            // 查询数据是否存在
            BsUserAddressInfo info = userAddressInfoService.queryAddressInfoById(addressInfoId);
            if(null == info) {
                map.put("isDelete", "ISDELETE");
                return ReturnDWZAjax.success("该数据已被删除，请刷新页面", map);
            }else {
                userAddressInfoService.deleteAddressInfoById(addressInfoId);
                // 删除的记录为默认地址
                if(StringUtil.isNotBlank(isDefault)) {
                    if(Constants.USER_ADDRESS_INFO_IS_DEFAULT_YES.equals(isDefault)) {
                        // 根据userId 更新该用户最后一条入库的记录，设置为默认地址
                        userAddressInfoService.updateLastRecordByUserId(detailModifiUserId);
                    }
                }
                return ReturnDWZAjax.success("删除成功", map);
            }

        }catch(Exception e) {
            log.error("地址表的id：" + addressInfoId + "的用户地址管理信息删除失败", e);
            return ReturnDWZAjax.fail("操作失败！");
        }
    }

    /**
     * 进入查看详情中的新增/编辑页面
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/detailModifiPage")
    public String detail(Integer addressInfoId, Integer detailModifiUserId,String detailUserName, String detailMobile,
                         HttpServletRequest request, HttpServletResponse response, Map<String, Object> model) {
        if(null != addressInfoId) {
            BsUserAddressInfo bsUserAddressInfo = userAddressInfoService.queryAddressInfoById(addressInfoId);
            model.put("bsUserAddressInfo", bsUserAddressInfo);
        }
        model.put("detailModifiUserId", detailModifiUserId);
        model.put("detailModifiUserName", detailUserName);
        model.put("detailModifiMobile", detailMobile);
        return "/bsuseraddressinfo/detail_modifi_page";
    }

    /**
     * 查看详情中新增/编辑
     * @param detailAddressInfoId
     * @param detailUserId
     * @param detailConsignee
     * @param detailConsigneeMobile
     * @param detailConsigneeAddress
     * @param request
     * @param response
     * @param model
     * @return
     */
    @RequestMapping("/detailSave")
    public @ResponseBody
    Map<Object, Object> save(Integer detailAddressInfoId, Integer detailUserId, String isDefaultVal, String detailConsignee,
                             String detailConsigneeMobile, String detailConsigneeAddress, HttpServletRequest request,
                             HttpServletResponse response, Map<String, Object> model) {

        BsUserAddressInfo info = new BsUserAddressInfo();
        if(StringUtil.isNotBlank(detailConsignee)) {
            info.setConsignee(detailConsignee.trim());
        }
        if(StringUtil.isNotBlank(detailConsigneeMobile)) {
            info.setConsigneeMobile(detailConsigneeMobile.trim());
        }
        if(StringUtil.isNotBlank(detailConsigneeAddress)) {
            info.setConsigneeAddress(detailConsigneeAddress.trim());
        }
        if(StringUtil.isNotBlank(isDefaultVal)) {
            info.setIsDefault(isDefaultVal);
        }
        info.setUserId(detailUserId);

        if (null != detailAddressInfoId) {

            // 1、查询地址信息是否已存在
            // 校验地址信息是否已存在（数据唯一性校验：userId、收货人、收货手机号、收货地址）
            List<BsUserAddressInfo> resultList = userAddressInfoService.uniquenessCheckDetail(info);


            if(CollectionUtils.isNotEmpty(resultList)) {
                log.info("新增的数据，在地址信息表中已存在");
                return ReturnDWZAjax.toAjaxString("302", "地址信息已存在，请重新输入");
            }

            // 2、把userId对应的其他地址信息默认值更新为NO
            if (StringUtil.isNotBlank(isDefaultVal)) {
                if(Constants.USER_ADDRESS_INFO_IS_DEFAULT_YES.equals(isDefaultVal)) {
                    try {
                        userAddressInfoService.updateIsDefaultForNo(detailUserId);
                        log.info("查看详情编辑userId：" + detailUserId+"默认值更新成功");
                    }catch(Exception e) {
                        log.error("查看详情用户地址管理信息更新失败", e);
                        return ReturnDWZAjax.fail("操作失败！");
                    }
                }
            }

            // 3、更新默认地址
            info.setUpdateTime(new Date());
            info.setIsDefault(isDefaultVal);
            info.setId(detailAddressInfoId);
            try {
                // 4、更新
                userAddressInfoService.updateAddressInfo(info);
                Map<Object, Object> map = new HashMap<>();
                map.put("userId", detailUserId);
                return ReturnDWZAjax.success("修改成功", map);
            }catch(Exception e) {
                log.error("地址信息更新失败", e);
                return ReturnDWZAjax.fail("操作失败！");
            }

        }else {
            // 5、查询地址信息是否已存在
            // 校验地址信息是否已存在（数据唯一性校验：userId、收货人、收货手机号、收货地址）
            List<BsUserAddressInfo> resultList = userAddressInfoService.uniquenessCheckDetail(info);
            if(CollectionUtils.isNotEmpty(resultList)) {
                log.info("新增的数据，在地址信息表中已存在");
                return ReturnDWZAjax.toAjaxString("302", "地址信息已存在，请重新输入");
            }

            // 6、把userId对应的其他地址信息默认值更新为NO
            if (StringUtil.isNotBlank(isDefaultVal)) {
                if (Constants.USER_ADDRESS_INFO_IS_DEFAULT_YES.equals(isDefaultVal)) {
                    try {
                        userAddressInfoService.updateIsDefaultForNo(detailUserId);
                        log.info("查看详情编辑userId：" + detailUserId+"默认值更新成功");
                    }catch(Exception e) {
                        log.error("查看详情用户地址管理信息更新失败", e);
                        return ReturnDWZAjax.fail("操作失败！");
                    }
                }
            }
            info.setIsDefault(isDefaultVal);
            info.setCreateTime(new Date());
            info.setUpdateTime(new Date());
            try {
                // 7、新增
                userAddressInfoService.addUserAddressInfoSingle(info);
                Map<Object, Object> map = new HashMap<>();
                map.put("userId", detailUserId);
                return ReturnDWZAjax.success("新增成功", map);
            }catch(Exception e) {
                log.error("地址信息新增失败", e);
                return ReturnDWZAjax.fail("操作失败！");
            }
        }
    }

    /**
     * 用户地址管理-下载模板
     * @param response
     * @param request
     */
    @RequestMapping("/downLoadXls")
    public void xls(HttpServletResponse response, HttpServletRequest request) {
        List<Map<String, List<Object>>> list = new ArrayList<Map<String, List<Object>>>();
        //设置标题
        Map<String, List<Object>> titleMap = new HashMap<String, List<Object>>();
        List<Object> titles = new ArrayList<Object>();
        titles.add("姓名");
        titles.add("注册手机号");
        titles.add("收货人");
        titles.add("收货手机号");
        titles.add("地址");
        titleMap.put("title", titles);
        list.add(titleMap);
        try {
            ExportUtil.exportExcel(response, request, "用户地址管理导入模板", list);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 读取手机号码
     *
     * @return
     * @throws IOException
     */
    @RequestMapping("/readExcel")
    public
    @ResponseBody
    Map<Object, Object> readExcel(@RequestParam(value = "fileField", required = true) MultipartFile file,
                                      HttpServletResponse response, HttpServletRequest request) throws IOException {
        HashMap<Object, Object> dataMap = new HashMap<Object, Object>();
        try {
            String[][] result = ExcelUtil.getDataInputStream(file.getInputStream(), 1, 0);

            // 手机号码格式正确的地址信息
            List<UserAddressInfoVO> userAddressInfoList = new ArrayList<UserAddressInfoVO>();
            if (result != null && result.length > 0) {
                // 1、校验手机号注册手机号、收件人手机号格式是否正确
                String registerMobile = "";
                String consigneeMobile = "";
                for(int i = 0; i < result.length; i++) {
                    if(null != result[i][1]) {
                        registerMobile = result[i][1].toString();
                    }else {
                        continue;
                    }
                    if(null != result[i][3]) {
                        consigneeMobile = result[i][3].toString();
                    }else {
                        continue;
                    }

                    if(StringUtil.isNotBlank(registerMobile)) {
                        if(registerMobile.indexOf(".") != -1) {
                            registerMobile = registerMobile.substring(0, registerMobile.indexOf("."));
                        }
                        if(!MobileCheckUtil.isMobile(registerMobile)){
                            log.info("用户地址批量导入，注册手机号码格式不正确："+registerMobile);
                            continue;
                        }
                    }else {
                        continue;
                    }

                    if(StringUtil.isNotBlank(consigneeMobile)) {
                        if(consigneeMobile.indexOf(".") != -1) {
                            consigneeMobile = consigneeMobile.substring(0, consigneeMobile.indexOf("."));
                        }
                        if(!MobileCheckUtil.isMobile(consigneeMobile)){
                            log.info("用户地址批量导入，收件人手机号码格式不正确："+consigneeMobile);
                            continue;
                        }
                    }else {
                        continue;
                    }

                    UserAddressInfoVO vo = new UserAddressInfoVO();
                    vo.setUserName(null != result[i][0] ? result[i][0].toString() : null);
                    vo.setMobile(registerMobile);
                    vo.setConsignee(null != result[i][2] ? result[i][2].toString() : null);
                    vo.setConsigneeMobile(consigneeMobile);
                    vo.setConsigneeAddress(null != result[i][4] ? result[i][4].toString() : null);
                    userAddressInfoList.add(vo);
                }
            } else {
                return ReturnDWZAjax.toAjaxString("300", "导入的数据为空");
            }

            // 2、筛选出有效的地址信息
            List<UserAddressInfoVO> effectiveList = new ArrayList<UserAddressInfoVO>();

            List<BsUser> bsUserList = new ArrayList<BsUser>();

            if(CollectionUtils.isEmpty(userAddressInfoList)) {
                return ReturnDWZAjax.toAjaxString("301", "导入数据的注册手机号、收件人手机号格式不正确！");
            }else {
                for(int j = 0; j < userAddressInfoList.size(); j++) {
                    String userName = userAddressInfoList.get(j).getUserName();
                    String mobile = userAddressInfoList.get(j).getMobile();
                    String consignee = userAddressInfoList.get(j).getConsignee();
                    String consigneeMobile = userAddressInfoList.get(j).getConsigneeMobile();
                    String consigneeAddress = userAddressInfoList.get(j).getConsigneeAddress();

                    // 3、校验用户手机号与姓名与bs_user表是否一致
                    bsUserList = userAddressInfoService.queryUserByMobileAndName(userName, mobile);

                    if(CollectionUtils.isEmpty(bsUserList)) {
                        log.info("用户地址批量导入：注册手机号：" + mobile + "用户不存在或用户手机号与姓名不一致");
                        userAddressInfoList.remove(j);
                        continue;
                    }
                    // 4、校验地址信息是否已存在（数据唯一性校验：userId、收货人、收货手机号、收货地址）
                    BsUserAddressInfo info = new BsUserAddressInfo();
                    info.setUserId(bsUserList.get(0).getId());
                    info.setConsignee(consignee);
                    info.setConsigneeMobile(consigneeMobile);
                    info.setConsigneeAddress(consigneeAddress);
                    List<BsUserAddressInfo> resultList = userAddressInfoService.uniquenessCheck(info);
                    if(CollectionUtils.isNotEmpty(resultList)) {
                        log.info("用户地址批量导入：新增的数据，注册手机号：" + mobile + "在地址信息表中已存在");
                        userAddressInfoList.remove(j);
                        // 集合中删除一个元素，循环变量-1
                        j = j-1;
                        continue;
                    }else {
                        UserAddressInfoVO userAddressInfoVO = new UserAddressInfoVO();
                        userAddressInfoVO.setUserId(bsUserList.get(0).getId());
                        userAddressInfoVO.setConsignee(consignee);
                        userAddressInfoVO.setConsigneeMobile(consigneeMobile);
                        userAddressInfoVO.setConsigneeAddress(consigneeAddress);
                        effectiveList.add(userAddressInfoVO);
                    }
                }
            }

            if(CollectionUtils.isEmpty(effectiveList)) {
                if(CollectionUtils.isEmpty(bsUserList)) {
                    return ReturnDWZAjax.toAjaxString("302", "导入的数据中用户不存在或用户手机号与姓名不一致！");
                }else {
                    return ReturnDWZAjax.toAjaxString("303", "导入的数据在地址信息表中已存在！");
                }
            }else {

                try {
                    // 5、批量导入，相同注册手机号最后一个导入的地址，设置为默认地址
                    userAddressInfoService.batchInsertUserAddressInfo(effectiveList);
                    return ReturnDWZAjax.toAjaxString("200", "文件读取成功，数据已存库！");
                }catch(Exception e) {
                    log.error("用户地址管理信息批量导入失败", e);
                    return ReturnDWZAjax.fail("操作失败！");
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        response.setContentType("text/html");
        return dataMap;
    }

}
