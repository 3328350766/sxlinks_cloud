package com.sxlinks.modules.system.controller.report.utils;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SimpleApplicationEventMulticaster;
import org.springframework.context.support.AbstractApplicationContext;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Spring工具类
 *
 * @author lr
 * @since 2021-01-12
 */
public class ApplicationContextUtils implements ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public static final String GAEA_ASYN_APPLICATION_EVENT_MULTICASTER_BEAN_NAME = "gaeaAsynApplicationEventMulticaster";

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        ApplicationContextUtils.applicationContext = applicationContext;
        if (applicationContext instanceof AbstractApplicationContext) {
            AbstractApplicationContext abstractApplicationContext = (AbstractApplicationContext) applicationContext;
            //异步发布器
            SimpleApplicationEventMulticaster asynApplicationEventMulticaster = new SimpleApplicationEventMulticaster(abstractApplicationContext.getBeanFactory());
            abstractApplicationContext.getBeanFactory().registerSingleton(GAEA_ASYN_APPLICATION_EVENT_MULTICASTER_BEAN_NAME, asynApplicationEventMulticaster);
            int processors = Runtime.getRuntime().availableProcessors();
            //设置异步执行
            asynApplicationEventMulticaster
                    .setTaskExecutor(new ThreadPoolExecutor(processors,
                            processors,
                            10,
                            TimeUnit.MINUTES,
                            new ArrayBlockingQueue<>(16 * processors, true)));
        }

    }

    /**
     * 根据名称和类型获取SpringBean
     *
     * @param name
     * @param requireType
     * @param <T>
     * @return
     */
    public static <T> T getBean(String name, Class<T> requireType) {
        return applicationContext.getBean(name, requireType);
    }

    /**
     * 根据名称和类型获取SpringBean
     *
     * @param requireType
     * @param <T>
     * @return
     */
    public static <T> T getBean(Class<T> requireType) {
        return applicationContext.getBean(requireType);
    }


    /**
     * 判断bean是否存在
     * @param beanname
     * @return
     */
    public static boolean containsBean(String beanname){
        if(StringUtils.isBlank(beanname)){
            return false;
        }
        return applicationContext.containsBean(beanname);
    }

    /**
     * 发布事件
     *
     * @param applicationEvent 事件
     */
    public static void publishEvent(ApplicationEvent applicationEvent) {
        //设置事件发布异步执行
        SimpleApplicationEventMulticaster applicationEventMulticaster = applicationContext.getBean(GAEA_ASYN_APPLICATION_EVENT_MULTICASTER_BEAN_NAME, SimpleApplicationEventMulticaster.class);
        applicationEventMulticaster.multicastEvent(applicationEvent);
    }


    /**
     * 发布同步事件
     *
     * @param applicationEvent 事件
     */
    public static void publishSynEvent(ApplicationEvent applicationEvent) {
        //设置事件发布异步执行
        applicationContext.publishEvent(applicationEvent);
    }
}
