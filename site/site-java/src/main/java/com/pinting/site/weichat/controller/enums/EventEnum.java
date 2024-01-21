package com.pinting.site.weichat.controller.enums;

public enum EventEnum {
    /** 客服*/
    COSTOMER_SERVICE("COSTOMER_SERVICE", "客服"),
    ;
    private String eventKey;
    private String content;

    private EventEnum(String eventKey, String content) {
        this.eventKey = eventKey;
        this.content = content;
    }

    public static String getContent(String eventKey) {
        EventEnum[] eventEnums = EventEnum.values();
        for (EventEnum eventEnum : eventEnums) {
            if (eventEnum.getEventKey().equals(eventKey))
                return eventEnum.getContent();
        }
        return null;
    }

    public String getEventKey() {
        return eventKey;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setEventKey(String eventKey) {
        this.eventKey = eventKey;
    }

}
