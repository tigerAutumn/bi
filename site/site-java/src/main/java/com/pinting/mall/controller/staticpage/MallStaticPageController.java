package com.pinting.mall.controller.staticpage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * 积分商城静态页面处理Controller
 *
 * @author shh
 * @date 2018/5/10 13:59
 * @Copyright: 2018 BiGangWan.com Inc. All rights reserved.
 */
@Controller
public class MallStaticPageController {

    static String terminal_h5 = "h5";
    static String terminal_pc = "pc";
    static String terminal_pc178 = "pc178";
    static String terminal_app = "app";

    @RequestMapping("{terminal}/mallStatic/{page}")
    public String show(@PathVariable String terminal, @PathVariable String page,
                       HttpServletRequest request, HttpServletResponse response, Map<String,Object> model) {

        String client = request.getParameter("client");
        model.put("client", client);

        return "mall/static/"+page;
    }

}
