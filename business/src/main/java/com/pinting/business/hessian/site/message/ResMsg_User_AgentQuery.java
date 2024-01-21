package com.pinting.business.hessian.site.message;


import com.pinting.core.hessian.msg.ResMsg;

public class ResMsg_User_AgentQuery extends ResMsg{

    /**
     * 
     */
    private static final long serialVersionUID = -8721518271765742932L;

    private Integer id;

    private String agentName;

    private String agentCode;

    private String agentPic;

    private String note;
    
    private Integer agentId;
    
    private String dept;
    
    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getAgentName() {
        return agentName;
    }

    public void setAgentName(String agentName) {
        this.agentName = agentName;
    }

    public String getAgentCode() {
        return agentCode;
    }

    public void setAgentCode(String agentCode) {
        this.agentCode = agentCode;
    }

    public String getAgentPic() {
        return agentPic;
    }

    public void setAgentPic(String agentPic) {
        this.agentPic = agentPic;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Integer getAgentId() {
        return agentId;
    }

    public void setAgentId(Integer agentId) {
        this.agentId = agentId;
    }
    
}
