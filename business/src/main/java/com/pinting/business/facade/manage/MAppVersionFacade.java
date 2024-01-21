package com.pinting.business.facade.manage;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.pinting.business.hessian.manage.message.ReqMsg_MAppVersion_AppVersionAdd;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppVersion_AppVersionDelete;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppVersion_AppVersionListQuery;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppVersion_AppVersionUpdate;
import com.pinting.business.hessian.manage.message.ReqMsg_MAppVersion_PrimaryKey;
import com.pinting.business.hessian.manage.message.ResMsg_MAppVersion_AppVersionAdd;
import com.pinting.business.hessian.manage.message.ResMsg_MAppVersion_AppVersionDelete;
import com.pinting.business.hessian.manage.message.ResMsg_MAppVersion_AppVersionListQuery;
import com.pinting.business.hessian.manage.message.ResMsg_MAppVersion_AppVersionUpdate;
import com.pinting.business.hessian.manage.message.ResMsg_MAppVersion_PrimaryKey;
import com.pinting.business.model.BsAppVersion;
import com.pinting.business.service.manage.MAppVersionService;
import com.pinting.business.util.BeanUtils;
import com.pinting.business.util.Constants;
import com.pinting.core.util.ConstantUtil;
import com.pinting.core.util.StringUtil;

/**
 * 
 * @Project: business
 * @Title: MAppVersionFacade.java
 * @author yanwl
 * @date 2016-02-18 下午13:34:07
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
@Component("MAppVersion")
public class MAppVersionFacade{
	@Autowired
	private MAppVersionService mAppVersionService;
	
	public void appVersionListQuery(ReqMsg_MAppVersion_AppVersionListQuery reqMsg,ResMsg_MAppVersion_AppVersionListQuery resMsg) {
		Integer pageNum = reqMsg.getPageNum();
		Integer numPerPage = reqMsg.getNumPerPage();
		int totalRows =  mAppVersionService.findAllAppVersionCount();
		if(totalRows > 0) {
			List<BsAppVersion> versions = mAppVersionService.findAppVersion(pageNum, numPerPage);
			resMsg.setAppVersionList(BeanUtils.classToArrayList(versions));
		}
		resMsg.setTotalRows(totalRows > 0?totalRows:0);
		resMsg.setNumPerPage(numPerPage);
		resMsg.setPageNum(pageNum);
	}
	
	
	public void appVersionAdd(ReqMsg_MAppVersion_AppVersionAdd reqMsg,ResMsg_MAppVersion_AppVersionAdd resMsg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("version", reqMsg.getVersion());
		map.put("appType", reqMsg.getAppType());
		
		List<BsAppVersion> list = mAppVersionService.findVersionByMap(map);
		List<BsAppVersion> versionList = mAppVersionService.findAppVersionMaxValue(); // 查找ios/android APP版本号的最大值
		if(list != null && !list.isEmpty() && list.size()>0) {
			resMsg.setJson(ConstantUtil.BSRESCODE_FAIL);
		}else {
			for(int i=0;i < versionList.size();i++) {
				BsAppVersion result = versionList.get(i);
				BsAppVersion appVersion = new BsAppVersion();
				if(result.getAppType().equals("ios") && reqMsg.getAppType().equals("ios")) { // 1、添加ios版本
					int iosInt = compare(reqMsg.getVersion(), result.getVersion());
					if(iosInt > 0) { // 1)、当前ios版本号高于库中最大的版本号
						resMsg.setFlag(ResMsg_MAppVersion_AppVersionAdd.VERSION_NUMBER_NEW);
						appVersion.setAppType(reqMsg.getAppType());
						appVersion.setIsMandatory(reqMsg.getIsMandatory());
						appVersion.setCreateTime(new Date());
						appVersion.setUpdateTime(new Date());
						appVersion.setVersion(reqMsg.getVersion());
						appVersion.setUrl(Constants.IOS_URl);
						if(StringUtil.isNotEmpty(reqMsg.getContent2())) {
							appVersion.setContent(reqMsg.getContent1()+"@@@"+reqMsg.getContent2());
						}else {
							appVersion.setContent(reqMsg.getContent1());
						}
						int row = mAppVersionService.saveAppVersion(appVersion);
				        if(row > 0) {
				        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
				        }else {
				        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
				        }
						
					}else if (iosInt < 0) { // 2)、 当前ios版本号低于库中最大的版本号
						resMsg.setFlag(ResMsg_MAppVersion_AppVersionAdd.VERSION_NUMBER_FAIL);
					}else { // 3)、版本号相同
						resMsg.setFlag(ResMsg_MAppVersion_AppVersionAdd.VERSION_NUMBER_SAME);
					}
					
				}else if(result.getAppType().equals("android") && reqMsg.getAppType().equals("android")) { // 2、添加android版本
					int androidInt = compare(reqMsg.getVersion(), result.getVersion());
					if(androidInt > 0) { // 1)、当前android版本号高于库中最大的版本号
						resMsg.setFlag(ResMsg_MAppVersion_AppVersionAdd.VERSION_NUMBER_NEW);
						appVersion.setAppType(reqMsg.getAppType());
						appVersion.setIsMandatory(reqMsg.getIsMandatory());
						appVersion.setCreateTime(new Date());
						appVersion.setUpdateTime(new Date());
						appVersion.setVersion(reqMsg.getVersion());
						if(StringUtil.isNotEmpty(reqMsg.getContent2())) {
							appVersion.setContent(reqMsg.getContent1()+"@@@"+reqMsg.getContent2());
						}else {
							appVersion.setContent(reqMsg.getContent1());
						}
						
						int row = mAppVersionService.saveAppVersion(appVersion);
				        if(row > 0) {
				        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
				        }else {
				        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
				        }
						
					}else if (androidInt < 0) { // 2)、 当前android版本号低于库中最大的版本号
						resMsg.setFlag(ResMsg_MAppVersion_AppVersionAdd.VERSION_NUMBER_FAIL);
					}else { // 3)、版本号相同
						resMsg.setFlag(ResMsg_MAppVersion_AppVersionAdd.VERSION_NUMBER_SAME);
					}
				}
			}
			
		}
	}
	
	public void primaryKey(ReqMsg_MAppVersion_PrimaryKey reqMsg,ResMsg_MAppVersion_PrimaryKey resMsg) {
		resMsg.setAppVersion(mAppVersionService.findAppVersionById(reqMsg.getId()));
	}
	
	public void appVersionUpdate(ReqMsg_MAppVersion_AppVersionUpdate reqMsg,ResMsg_MAppVersion_AppVersionUpdate resMsg) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("version", reqMsg.getVersion());
		map.put("appType", reqMsg.getAppType());
		
		List<BsAppVersion> list = mAppVersionService.findVersionByMap(map);
		if(list != null && !list.isEmpty() && list.size()>0) {
			resMsg.setJson(ConstantUtil.BSRESCODE_FAIL);
		}else {
			BsAppVersion appVersion = mAppVersionService.findAppVersionById(reqMsg.getId());
			appVersion.setAppType(reqMsg.getAppType());
			appVersion.setContent(reqMsg.getContent());
			appVersion.setIsMandatory(reqMsg.getIsMandatory());
			appVersion.setVersion(reqMsg.getVersion());
			if("ios".equals(reqMsg.getAppType())) {
				appVersion.setUrl(Constants.IOS_URl);
			}else {
				appVersion.setUrl("");
			}
			
			int row = mAppVersionService.updateAppVersion(appVersion);
	        if(row > 0) {
	        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
	        }else {
	        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
	        }
		}
	}
	
	public void appVersionDelete(ReqMsg_MAppVersion_AppVersionDelete reqMsg,ResMsg_MAppVersion_AppVersionDelete resMsg) {
		BsAppVersion appVersion = mAppVersionService.findAppVersionById(reqMsg.getId());
		if(appVersion != null) {
			Map<String,Object> map = new HashMap<String,Object>();
			map.put("appVersion", appVersion.getVersion());
			map.put("appType", appVersion.getAppType());
			int ret = mAppVersionService.findIsLastVersion(map);
			if(ret == 0) {
				int row = mAppVersionService.deleteAppVersionById(reqMsg.getId());
		        if(row > 0) {
		        	resMsg.setJson(ConstantUtil.RESCODE_SUCCESS);
		        }else {
		        	resMsg.setJson(ConstantUtil.RESCODE_FAIL);
		        }
			}else {
				resMsg.setJson(ConstantUtil.BSRESCODE_FAIL);
			}
		}else {
			resMsg.setJson(ConstantUtil.BSRESCODE_FAIL);
		}
	}
	
	/**
	 * 比较版本号的大小,前者大则返回1,后者大返回-1,相等则返回0
	 * @param version1
	 * @param version2
	 * @return
	 */
	public static int compare(String version1, String version2) {
        if (version1 == null || version1.length() == 0 || version2 == null || version2.length() == 0 ){
        	throw new IllegalArgumentException("Invalid parameter!");
        }
        int index1 = 0;
        int index2 = 0;
        while(index1 < version1.length() && index2 < version2.length()) {
            int[] number1 = getValue(version1, index1);
            int[] number2 = getValue(version2, index2);
            if(number1[0] < number2[0]) {
            	return -1;
            }else if(number1[0] > number2[0]) {
            	return 1;
            }else {
                index1 = number1[1] + 1;
                index2 = number2[1] + 1;
            }
        }
        if(index1 == version1.length() && index2 == version2.length()) {
        	return 0;
        }
        if(index1 < version1.length()) {
        	return 1;
        }else {
        	return -1;
        }
    }
      
    /** 
     * @param version
     * @param index
     * @return 版本号的第一个位数，点的个数(1.2.3 [1,2])
     */  
    public static int[] getValue(String version, int index) {
        int[] value_index = new int[2];
        StringBuilder sb = new StringBuilder();
        while(index < version.length() && version.charAt(index) != '.') {
            sb.append(version.charAt(index));
            index++;
        }
        value_index[0] = Integer.parseInt(sb.toString());
        value_index[1] = index;
        return value_index;
    }
	
}
