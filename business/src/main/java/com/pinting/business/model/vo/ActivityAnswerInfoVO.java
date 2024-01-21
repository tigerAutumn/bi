package com.pinting.business.model.vo;

/**
 * Author:      cyb
 * Date:        2017/6/26
 * Description:
 */
public class ActivityAnswerInfoVO extends ActivityBaseVO {

    /* 答题活动页面信息-正确答题数 */
    private Integer correctAnswer;

    /* 答题活动页面信息-奖励红包金额 */
    private Double redAmount;

    public Integer getCorrectAnswer() {
        return correctAnswer;
    }

    public void setCorrectAnswer(Integer correctAnswer) {
        this.correctAnswer = correctAnswer;
    }

    public Double getRedAmount() {
        return redAmount;
    }

    public void setRedAmount(Double redAmount) {
        this.redAmount = redAmount;
    }
}
