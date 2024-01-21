package com.pinting.business.coreflow.core.util;

import com.pinting.business.coreflow.core.service.DepFixedService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class FindServiceFactory implements ApplicationContextAware {
    protected static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    /**
     * 借款服务工厂类
     *
     * @param serviceName
     * @return
     */
    public static DepFixedService findService(String serviceName, String defaultService) {
        if (StringUtils.isNotBlank(serviceName) && applicationContext.containsBean(serviceName)) {
            Object service = applicationContext.getBean(serviceName);
            return (DepFixedService) service;
        } else {
            Object service = applicationContext.getBean(defaultService);
            return (DepFixedService) service;
        }
    }
}
