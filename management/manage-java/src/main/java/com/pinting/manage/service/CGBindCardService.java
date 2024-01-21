package com.pinting.manage.service;

import com.pinting.business.model.vo.CGBindCardResVO;

import java.util.List;

/**
 * Created by Administrator on 2017/9/20.
 */
public interface CGBindCardService {

    List<CGBindCardResVO> cgBindCard(Integer ucUserId);

}
