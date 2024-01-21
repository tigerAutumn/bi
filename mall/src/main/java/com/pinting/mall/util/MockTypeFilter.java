package com.pinting.mall.util;

import com.pinting.core.util.GlobEnvUtil;
import com.pinting.core.util.StringUtil;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import java.io.IOException;

/**
 * Created by babyshark on 2016/10/29.
 */
public class MockTypeFilter implements TypeFilter {
    private static String serverMode = GlobEnvUtil.get("server.mode");
    @Override
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        if("prod".equals(serverMode) || "prod_test".equals(serverMode)){
            String className = metadataReader.getClassMetadata().getClassName();
            if(StringUtil.isNotEmpty(className) && className.contains("mockito")){
                return true;
            }
        }
        return false;
    }
}
