package com.pinting.business.model;

import java.util.Date;

public class Tbdatapermission {
    private Long lid;

    private Long luserid;

    private String strdeptcode;

    private Integer ncurrentlevel;

    private Long loperateid;

    private Date dtupdatetime;

    public Long getLid() {
        return lid;
    }

    public void setLid(Long lid) {
        this.lid = lid;
    }

    public Long getLuserid() {
        return luserid;
    }

    public void setLuserid(Long luserid) {
        this.luserid = luserid;
    }

    public String getStrdeptcode() {
        return strdeptcode;
    }

    public void setStrdeptcode(String strdeptcode) {
        this.strdeptcode = strdeptcode;
    }

    public Integer getNcurrentlevel() {
        return ncurrentlevel;
    }

    public void setNcurrentlevel(Integer ncurrentlevel) {
        this.ncurrentlevel = ncurrentlevel;
    }

    public Long getLoperateid() {
        return loperateid;
    }

    public void setLoperateid(Long loperateid) {
        this.loperateid = loperateid;
    }

    public Date getDtupdatetime() {
        return dtupdatetime;
    }

    public void setDtupdatetime(Date dtupdatetime) {
        this.dtupdatetime = dtupdatetime;
    }
}