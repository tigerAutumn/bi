package com.pinting.gateway.util;

import java.io.IOException;
import java.io.StringWriter;
import java.text.SimpleDateFormat;

import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;

public class JacksonUtil {
    private static ObjectMapper mapper = new ObjectMapper();
    
    public static String bean2Json(Object obj) throws IOException {
        StringWriter sw = new StringWriter();
        JsonGenerator gen = new JsonFactory().createJsonGenerator(sw);
        //设置日期格式  
        SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
        mapper.setDateFormat(fmt); 
        mapper.writeValue(gen, obj);
        
        gen.close();
        return sw.toString();
    }

    public static <T> T json2Bean(String jsonStr, Class<T> objClass)
            throws JsonParseException, JsonMappingException, IOException {
    	mapper.setDateFormat(new SimpleDateFormat("yyy-MM-dd HH:mm:ss"));
    	mapper.enableDefaultTyping();
        return mapper.readValue(jsonStr, objClass);
    }
    
}