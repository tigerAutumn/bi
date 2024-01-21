package com.pinting.business.accounting.loan.enums;

import com.pinting.core.util.GlobEnvUtil;

/**
 * Created by 剑钊
 *
 * @2016/9/14 17:27.
 */
public enum FilePath {

    DOWN_FILE_FI_PATH(GlobEnvUtil.get("baofoo.down.accountFile.fiPath"),"收单对账文件保存地址"),
    DOWN_FILE_FO_PATH(GlobEnvUtil.get("baofoo.down.accountFile.foPath"),"出款对账文件保存地址"),
    UNCOMPRESS_FILE_PATH(GlobEnvUtil.get("baofoo.uncompress.accountFile.path"),"对账文件解压地址"),
    ZAN_DOWN_FILE_FO_PATH(GlobEnvUtil.get("zan.baofoo.account.fileFoPath"),"赞分期出款对账文件保存地址"),
    ZAN_UNCOMPRESS_FILE_PATH(GlobEnvUtil.get("zan.baofoo.uncompress.accountFile.path"),"赞分期对账文件解压地址"),

    HF_CHECK_FILE_PATH(GlobEnvUtil.get("baofoo.down.accountFile.fiPath"), "恒丰对账文件保存地址"),

    ;
    /** code */
    private String code;

    /** description */
    private String description;


    /**
     * 私有的构造方法
     * @param code
     * @param description
     */
    FilePath(String code, String description) {
        this.code = code;
        this.description = description;
    }


    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
