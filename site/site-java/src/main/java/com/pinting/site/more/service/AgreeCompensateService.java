package com.pinting.site.more.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 代偿协议
 *
 * @author zousheng
 */
public interface AgreeCompensateService {

    /**
     * 代偿协议数据拼装
     * @param request
     * @param model
     * @return
     */
    Object execute(HttpServletRequest request, Map<String, Object> model);
}
