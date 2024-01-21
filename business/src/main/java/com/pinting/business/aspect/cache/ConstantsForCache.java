package com.pinting.business.aspect.cache;

public interface ConstantsForCache {

    int CACHE_MAX_PAGE = 3; // 缓存最大页数

    String REDIS_CACHE = "REDIS_CACHE"; // 缓存前缀标识

    int DEFAULT_EXPIRE = 5*60; // 缓存失效时间，默认5*60秒

    interface Punctuation {
        String UNDERLINE = "_"; // 下划线
    }

    /**
     * redis缓存数据类型
     */
    enum CacheType {
        OBJECT, // 单个对象
        LIST, // 列表数据
        ;
    }

    /**
     * redis_key键值定义
     * 格式：实现类_方法名称（小写）
     */
    enum CacheKey {
        // 产品相关
        PRODUCTFACADE_LISTQUERY("productfacade_listquery", "查询产品列表（理财计划列表（H5|PC））"),
        APPPRODUCTFACADE_LISTINDEXINFOQUERY("appproductfacade_listindexinfoquery", "app一级产品列表"),
        APPPRODUCTFACADE_LISTRETURNTYPE("appproductfacade_listreturntype", "app二级产品列表"),
        PRODUCT_FINDSUGGEST("product_findsuggest", "APP首页产品信息"),
        HOME_INFOQUERY("home_infoquery", "查询PC/H5首页基础数据（包含累计投资额、新手推荐产品、四个非新手产品、钱报系首页所有未完成的产品、运营报告）"),

        // banner相关
        PRODUCTFACADE_BANNERQUERY("productfacade_bannerquery", "产品列表banner页（H5|PC）"),
        BANNERCONFIGFACADE_GETLIST("bannerconfigfacade_getlist", "查询首页banner，首页banner"),
        BANNERCONFIGFACADE_APPSTART("bannerconfigfacade_appstart", "APP启动页面banner"),

        HOME_REDPACKETAMOUNT("home_redpacketamount", "查询注册红包金额额"),
        NEWSSERVICE_CURRENTNEWS("newsservice_currentnews", "查询最新的新闻|公告|动态-- 特殊业务，写在了service层"),
        BANK_PAY19BANKLIST("bank_pay19banklist", "银行卡列表"),
        PRODUCT_SELECTPRODUCTDETAILLIST("product_selectproductdetaillist", "查询产品投资列表"),

        //加薪计划
        ACTIVITYFACADE_SALARYINCREASEPLAN("activityfacade_salaryincreaseplan", "加薪计划活动"),
        ;

        private String key;

        private String desc;

        private CacheKey(String key, String desc) {
            this.key = key;
            this.desc = desc;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public static CacheKey getEnum(String key) {
            for (CacheKey cacheKey : values()) {
                if (cacheKey.getKey().equals(key)) {
                    return cacheKey;
                }
            }
            return null;
        }
    }
}
