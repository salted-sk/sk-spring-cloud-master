package com.sk.config.datasource;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * 动态配置数据源切换
 *
 * @author zhangqiao
 * @since 2019/12/29 12:18
 */
@Aspect
@Component
public class MultipleDataSourceAspect {

    private static final Logger log = LoggerFactory.getLogger(MultipleDataSourceAspect.class);
    
    /**
     * 切换数据源
     *
     * @param point 切点
     * @param dataSource 数据源注解
     */
    @Before("@annotation(dataSource))")
    public void switchDataSource(JoinPoint point, DataSource dataSource) {
        if (!MultileDataSourceHolder.containDataSourceKey(dataSource.value())) {
            log.info("数据源" + dataSource.value() + "不存在！");
        } else {
            // 切换数据源
            MultileDataSourceHolder.setDataSourceKey(dataSource.value());
            log.info("数据源切换至" + dataSource.value());
        }
    }

    /**
     * 重置数据源
     *
     * @param point 切点
     * @param dataSource 数据源注解
     */
    @After("@annotation(dataSource))")
    public void restoreDataSource(JoinPoint point, DataSource dataSource) {
        // 将数据源置为默认数据源
        MultileDataSourceHolder.clearDataSourceKey();
        log.info("初始化数据源！");
    }

    @Around("execution(* com.sk.dao.*.*(..))")
    public Object setDataSources(ProceedingJoinPoint jp) throws Throwable{
        //获取当前接口注解
        Class<?>[] interfaces = jp.getTarget().getClass().getInterfaces();
        // 如果类包含@DataSource注解，动态切换数据源
        DataSource dataSource = null;
        for (Class<?> anInterface : interfaces) {
            dataSource = anInterface.getAnnotation(DataSource.class);
            if (dataSource != null) {
                break;
            }
        }
        if (dataSource != null) {
            MultileDataSourceHolder.setDataSourceKey(dataSource.value());
            log.info("数据源切换至" + dataSource.value());
        }
        return jp.proceed();
    }
}