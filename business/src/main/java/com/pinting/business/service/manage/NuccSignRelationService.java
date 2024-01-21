package com.pinting.business.service.manage;

import com.pinting.business.model.vo.BsBgwNuccSignRelationVO;

import java.util.List;

public interface NuccSignRelationService {

    /**
     * 校验导入数据
     *
     * @param list
     * @param checkList
     * @return
     */
    public String checkImportDate(String merchantType, String userType, List<String> list, List<BsBgwNuccSignRelationVO> checkList, List<BsBgwNuccSignRelationVO> failList);

    /**
     * 导入数据批量入库
     *
     * @param importList
     * @return
     */
    public boolean saveImportDate(List<BsBgwNuccSignRelationVO> importList);
}

