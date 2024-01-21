package com.pinting.open.pojo.request.more;

import com.pinting.open.base.request.Request;
import com.pinting.open.base.util.Constants;

/**
 * Created by cyb on 2018/2/23.
 */
public class PlatformDataRequest extends Request {

    private Integer type;

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Override
    public String restApiUrl() {
        return Constants.BASE_REST_URL + "/mobile/more/platformData";
    }

    @Override
    public String testApiUrl() {
        return Constants.BASE_TEST_URL + "/mobile/more/platformData";
    }

}
