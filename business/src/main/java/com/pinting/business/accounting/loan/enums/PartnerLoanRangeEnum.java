package com.pinting.business.accounting.loan.enums;

public interface PartnerLoanRangeEnum {

    String ONE_LEVEL = "ONE_LEVEL";
    String TWO_LEVEL = "TWO_LEVEL";
    String THREE_LEVEL = "THREE_LEVEL";

    enum RedisQueueEnum {
        ONE_LEVEL_AUTH_YUN_QUEUE(ONE_LEVEL, PartnerEnum.YUN_DAI_SELF.getCode(), "ONE_LEVEL_AUTH_YUN_QUEUE", "ONE_LEVEL_AUTH_YUN_FLAG"), // 云贷一级区间队列
        TWO_LEVEL_AUTH_YUN_QUEUE(TWO_LEVEL, PartnerEnum.YUN_DAI_SELF.getCode(), "TWO_LEVEL_AUTH_YUN_QUEUE", "TWO_LEVEL_AUTH_YUN_FLAG"), // 云贷二级区间队列
        THREE_LEVEL_AUTH_YUN_QUEUE(THREE_LEVEL, PartnerEnum.YUN_DAI_SELF.getCode(), "THREE_LEVEL_AUTH_YUN_QUEUE", "THREE_LEVEL_AUTH_YUN_FLAG"), // 云贷三级区间队列
        ONE_LEVEL_AUTH_SEVEN_QUEUE(ONE_LEVEL, PartnerEnum.SEVEN_DAI_SELF.getCode(), "ONE_LEVEL_AUTH_SEVEN_QUEUE", "ONE_LEVEL_AUTH_SEVEN_FLAG"), // 七贷一级区间队列
        TWO_LEVEL_AUTH_SEVEN_QUEUE(TWO_LEVEL, PartnerEnum.SEVEN_DAI_SELF.getCode(), "TWO_LEVEL_AUTH_SEVEN_QUEUE", "TWO_LEVEL_AUTH_SEVEN_FLAG"), // 七贷二级区间队列
        THREE_LEVEL_AUTH_SEVEN_QUEUE(THREE_LEVEL, PartnerEnum.SEVEN_DAI_SELF.getCode(), "THREE_LEVEL_AUTH_SEVEN_QUEUE", "THREE_LEVEL_AUTH_SEVEN_FLAG"), // 自由一级区间队列
        ONE_LEVEL_AUTH_FREE_QUEUE(ONE_LEVEL, PartnerEnum.FREE.getCode(), "ONE_LEVEL_AUTH_FREE_QUEUE", "ONE_LEVEL_AUTH_FREE_FLAG"), // 自由二级区间队列
        TWO_LEVEL_AUTH_FREE_QUEUE(TWO_LEVEL, PartnerEnum.FREE.getCode(), "TWO_LEVEL_AUTH_FREE_QUEUE", "TWO_LEVEL_AUTH_FREE_FLAG"), // 自由三级区间队列
        THREE_LEVEL_AUTH_FREE_QUEUE(THREE_LEVEL, PartnerEnum.FREE.getCode(), "THREE_LEVEL_AUTH_FREE_QUEUE", "THREE_LEVEL_AUTH_FREE_FLAG"), // 一级区间队列
        ;

        private RedisQueueEnum(String code, String partnerCode, String key, String flag) {
            this.code = code;
            this.partnerCode = partnerCode;
            this.key = key;
            this.flag = flag;
        }

        private String code;
        private String partnerCode;
        private String key;
        private String flag;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getPartnerCode() {
            return partnerCode;
        }

        public void setPartnerCode(String partnerCode) {
            this.partnerCode = partnerCode;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        /**
         * @param partnerCode
         * @return
         */
        public static RedisQueueEnum getEnumByCode(String code, String partnerCode) {
            if (null == code || null == partnerCode) {
                return null;
            }
            for (RedisQueueEnum type : values()) {
                if (type.getCode().equals(code.trim()) && type.getPartnerCode().equals(partnerCode.trim()))
                    return type;
            }
            return null;
        }
    }
}
