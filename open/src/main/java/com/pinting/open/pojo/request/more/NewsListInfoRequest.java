package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Author:      shh
 * Date:        2017/6/30
 * Description: 分页查询港湾资讯、平台公告 request
 */
public class NewsListInfoRequest extends Request {

    private Integer page;

    private String type;

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/more/newsList";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/more/newsList";
    }

}
