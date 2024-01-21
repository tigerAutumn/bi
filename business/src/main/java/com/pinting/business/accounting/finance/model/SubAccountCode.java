package com.pinting.business.accounting.finance.model;

import com.pinting.business.accounting.loan.enums.PartnerEnum;
import com.pinting.business.util.Constants;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Author:      cyb
 * Date:        2017/8/30
 * Description: 子账户类型枚举
 */
public class SubAccountCode {

    public static Map<PartnerEnum, ProductType> productTypeMap = new HashMap<PartnerEnum, ProductType>() {
        {
            // 云贷产品类型
            ProductType yunProductType = new ProductType();
            yunProductType.setAuthCode(Constants.PRODUCT_TYPE_AUTH_YUN);
            yunProductType.setDiffCode(Constants.PRODUCT_TYPE_DIFF);
            yunProductType.setRedCode(Constants.PRODUCT_TYPE_RED);
            put(PartnerEnum.YUN_DAI_SELF, yunProductType);

            // 赞时贷产品类型
            ProductType zsdProductType = new ProductType();
            zsdProductType.setAuthCode(Constants.PRODUCT_TYPE_AUTH_ZSD);
            zsdProductType.setDiffCode(Constants.PRODUCT_TYPE_DIFF_ZSD);
            zsdProductType.setRedCode(Constants.PRODUCT_TYPE_RED_ZSD);
            put(PartnerEnum.ZSD, zsdProductType);

            // 7贷产品类型
            ProductType sevenProductType = new ProductType();
            sevenProductType.setAuthCode(Constants.PRODUCT_TYPE_AUTH_7);
            sevenProductType.setDiffCode(Constants.PRODUCT_TYPE_DIFF_7);
            sevenProductType.setRedCode(Constants.PRODUCT_TYPE_RED_7);
            put(PartnerEnum.SEVEN_DAI_SELF, sevenProductType);
            
            //自由资金产品类型
            ProductType freeProductType = new ProductType();
            freeProductType.setAuthCode(Constants.PRODUCT_TYPE_AUTH_FREE);
            freeProductType.setDiffCode(Constants.PRODUCT_TYPE_DIFF_FREE);
            freeProductType.setRedCode(Constants.PRODUCT_TYPE_RED_FREE);
            put(PartnerEnum.FREE, freeProductType);
        }
    };

    public static PartnerEnum getPartnerByAuthCode(String authCode){
        Set<Map.Entry<PartnerEnum, ProductType>> types = SubAccountCode.productTypeMap.entrySet();
        for (Map.Entry<PartnerEnum, ProductType> type : types) {
            if(type.getValue().getAuthCode().equals(authCode)){
                return type.getKey();
            }
        }
        return  null;
    }

}
