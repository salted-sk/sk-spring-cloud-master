package com.sk.config.datasource;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 动态配置数据上下文处理
 *
 * @author zhangqiao
 * @since 2019/12/29 12:18
 */
public class MultileDataSourceHolder {

    private static final ThreadLocal<String> SOURCE_HOLDER = ThreadLocal.withInitial(() -> "master");


    /**
     * 数据源的 key集合，用于切换时判断数据源是否存在
     */
    public static List<Object> dataSourceKeys = new ArrayList<>();

    /**
     * 切换数据源
     */
    public static void setDataSourceKey(String key) {
        SOURCE_HOLDER.set(key);
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSourceKey() {
        return SOURCE_HOLDER.get();
    }

    /**
     * 重置数据源
     */
    public static void clearDataSourceKey() {
        SOURCE_HOLDER.remove();
    }

    /**
     * 判断是否包含数据源
     */
    public static boolean containDataSourceKey(String key) {
        return dataSourceKeys.contains(key);
    }
    
    /**
     * 添加数据源keys
     */
    public static boolean addDataSourceKeys(Collection<? extends Object> keys) {
        return dataSourceKeys.addAll(keys);
    }
}