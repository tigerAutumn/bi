package com.pinting.open.pojo.response.more;

import com.pinting.open.base.response.Response;

/**
 * Created by cyb on 2018/2/23.
 */
public class PlatformDataResponse extends Response {

    /* 金额或者次数 */
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
