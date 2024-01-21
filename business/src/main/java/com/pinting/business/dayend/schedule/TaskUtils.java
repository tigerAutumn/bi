package com.pinting.business.dayend.schedule;

import com.pinting.business.model.BsScheduleConfig;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 *
 * Created by babyshark on 2017/5/11.
 */
@Component
public class TaskUtils implements BeanFactoryAware {

    private static Logger log = LoggerFactory.getLogger(TaskUtils.class);

    private static BeanFactory beanFactory;

    /**
     * 通过反射调用scheduleJob中定义的方法
     *
     * @param scheduleJob
     */
    public static void invokMethod(BsScheduleConfig scheduleJob) {
        Object object = null;
        Class clazz = null;
        String beanId = scheduleJob.getBeanId();
        if (StringUtils.isNotBlank(beanId)) {
            object = beanFactory.getBean(beanId);
        } else if (StringUtils.isNotBlank(scheduleJob.getBeanClass())) {
            try {
                clazz = Class.forName(scheduleJob.getBeanClass());
                object = clazz.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (object == null) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]：未找到Bean对象，请检查beanClass");
            return;
        }
        clazz = object.getClass();
        Method method = null;
        try {
            method = clazz.getDeclaredMethod(scheduleJob.getMethodName());
        } catch (NoSuchMethodException e) {
            log.error("任务名称 = [" + scheduleJob.getJobName() + "]：未找到方法名，请检查beanMethod");
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (method != null) {
            try {
                method.invoke(object);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }
}
