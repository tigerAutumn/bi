package com.pinting.business.model.vo;

/**
 * 2016公司年会抽奖活动VO
 * Created by shh on 2017/01/14 10:00.
 * @author shh
 */
public class EndOf2016CompanyAnnualVO {

    /* 中奖人姓名 */
    private String employeeName;

    /* 公司 */
    private String companyName;

    /* 奖项 */
    private String content;

    /* 奖品名称 */
    private String note;

    public String getEmployeeName() {
        return employeeName;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
