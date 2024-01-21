package com.pinting.business.model.vo;

import java.util.Date;

/**
 * Created by cyb on 2017/11/5.
 */
public class KeyValue {

    private String key;

    private Integer keyInt;

    private Date keyDate;

    private String value;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Integer getKeyInt() {
        return keyInt;
    }

    public void setKeyInt(Integer keyInt) {
        this.keyInt = keyInt;
    }

    public Date getKeyDate() {
        return keyDate;
    }

    public void setKeyDate(Date keyDate) {
        this.keyDate = keyDate;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
