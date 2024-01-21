package com.dafy.core.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 * <b>DESCRIPTION:</b>json字符串工具类<br/>
 */
public class JsonProcessUtils {

    /**
     * <b>DESCRIPTION:</b>获取ObjectMapper实例<br/>
     *
     * @return 转换类型
     */
    public static synchronized ObjectMapper getMapperInstance() {
        return new ObjectMapper();
    }

    /**
     * <b>DESCRIPTION:</b>json格式转为对象<br/>
     *
     * @param data json 数据
     * @return 转换类型
     * @throws IOException 异常
     */
 /*   public static <T> FachRequestVO<T> json2Object(String data, Class<T> clazz)
            throws IOException, JsonParseException, JsonMappingException {
        ObjectMapper mapper = getMapperInstance();
        JavaType javaType = mapper.getTypeFactory().constructParametricType(FachRequestVO.class, clazz);
        return mapper.readValue(data, javaType);
    }*/

    /**
     * <b>DESCRIPTION:</b>json字符串转对象<br/>
     *
     * @param data  json 数据
     * @param clazz 转换对象
     * @param <T>   转换类型
     * @return 转换类型
     * @throws IOException 异常
     */
    public static <T> T json2SingleObject(String data, Class<T> clazz) throws IOException {
        data = data.replace("\\", "").replace("\"{", "{").replace("}\"", "}").replace("\"[{", "[{").replace("}]\"", "}]");
        ObjectMapper mapper = getMapperInstance();
        return mapper.readValue(data, clazz);
    }

    /**
     * <b>DESCRIPTION:</b>object转换为字符串<br/>
     *
     * @param o 对象
     * @return 字符串
     * @throws JsonProcessingException
     */
    public static String object2Json(Object o) throws JsonProcessingException {
        ObjectMapper mapper = getMapperInstance();
        return mapper.writeValueAsString(o);
    }
}
