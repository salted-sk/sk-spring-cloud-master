package com.sk.lock;

/**
 * 分布式锁
 *
 * @author zhangqiao
 * @since 2020/1/3 10:34
 */
public interface DistributedLock {

    long TIMEOUT_MILLIS = 30000;

    int RETRY_TIMES = 10;

    long SLEEP_MILLIS = 500;

    boolean lock(String key);

    boolean lock(String key, int retryTimes);

    boolean lock(String key, int retryTimes, long sleepMillis);

    boolean lock(String key, long expire);

    boolean lock(String key, long expire, int retryTimes);

    boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    Boolean releaseLock(String key);

}
