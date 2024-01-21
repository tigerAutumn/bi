package com.pinting.business.model.vo;

import java.util.List;
import java.util.Map;

/**
 * Created by cyb on 2017/12/11.
 */
public class ChristmasEveVO extends ActivityBaseVO {

    private List<ChristmasEveResultVO> list;

    private List<ChristmasEveResultVO> drawnList;

    private Map<String, Integer> map;

    private Integer leftTimes;

    public Integer getLeftTimes() {
        return leftTimes;
    }

    public void setLeftTimes(Integer leftTimes) {
        this.leftTimes = leftTimes;
    }

    public Map<String, Integer> getMap() {
        return map;
    }

    public void setMap(Map<String, Integer> map) {
        this.map = map;
    }

    public List<ChristmasEveResultVO> getList() {
        return list;
    }

    public void setList(List<ChristmasEveResultVO> list) {
        this.list = list;
    }

    public List<ChristmasEveResultVO> getDrawnList() {
        return drawnList;
    }

    public void setDrawnList(List<ChristmasEveResultVO> drawnList) {
        this.drawnList = drawnList;
    }
}
