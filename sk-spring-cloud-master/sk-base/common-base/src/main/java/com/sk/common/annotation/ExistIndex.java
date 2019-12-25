package com.sk.common.annotation;

import java.lang.annotation.*;

/**
 * 定义此数据库表中是否含有索引
 *
 * @author zhangqiao
 * @since 2019/8/31 20:12
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ExistIndex {

    boolean exist() default false;

}
