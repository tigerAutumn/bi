package com.pinting.business.enums;


public enum QuestionnaireEnum {

	FIRST_SUBJECT(1, "-2,0,-4,-10"),
	SECOND_SUBJECT(2, "0,2,6,8,10"),
	THREE_SUBJECT(3, "2,4,8,10"),
	FOUR_SUBJECT(4, "0,2,6,10"),
	FIVE_SUBJECT(5, "0,2,6,8,10"),
	SEX_SUBJECT(6, "0,4,8,10"),
	SEVEN_SUBJECT(7, "0,4,6,10"),
	EIGHT_SUBJECT(8, "4,6,8,10"),
	NINE_SUBJECT(9, "2,6,10"),
	TEN_SUBJECT(10, "-5,5,10,15,20"),
	;   
	/** 问卷调查题目索引  */ 
	private Integer subjectIndex;
	/** 问卷调查题目分数集合 */ 
	private String subjectValues;
	
	public Integer getSubjectIndex() {
		return subjectIndex;
	}
	public void setSubjectIndex(Integer subjectIndex) {
		this.subjectIndex = subjectIndex;
	}
	public String getSubjectValues() {
		return subjectValues;
	}
	public void setSubjectValues(String subjectValues) {
		this.subjectValues = subjectValues;
	}	
	
	/**
     * 私有的构造方法
     * @param subjectIndex
     * @param subjectValues
     */
    private QuestionnaireEnum(Integer subjectIndex, String subjectValues) {
        this.subjectIndex = subjectIndex;
        this.subjectValues = subjectValues;
    }
	
    public static String find(Integer subjectIndex, Integer choiceIndex) {
        for (QuestionnaireEnum questionnaire : QuestionnaireEnum.values()) {
            if (questionnaire.getSubjectIndex() == subjectIndex) {
                String[] a = questionnaire.getSubjectValues().split(",");
                if (a.length-1 < choiceIndex) {
                	return null;
                }
                return a[choiceIndex-1];
            }
        }
        return null;
    }
   
}
