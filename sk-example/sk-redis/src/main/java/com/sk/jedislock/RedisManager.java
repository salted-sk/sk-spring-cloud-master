//package com.sk.jedislock;
//
//import redis.clients.jedis.Jedis;
//import redis.clients.jedis.JedisPool;
//import redis.clients.jedis.JedisPoolConfig;
//
///**
// *
// * @author zhangqiao
// * @since 2019/12/24 14:16
// */
//public class RedisManager {
//
//    private static JedisPool jedisPool;
//
//    static {
//        JedisPoolConfig jedisPoolConfig=new JedisPoolConfig();
//        jedisPoolConfig.setMaxTotal(20);
//        jedisPoolConfig.setMaxIdle(10);
//        jedisPool=new JedisPool(jedisPoolConfig,"127.0.0.1",6379);
//    }
//
//    public static Jedis getJedis() throws Exception {
//        if(null!=jedisPool){
//            return jedisPool.getResource();
//        }
//        throw new Exception("Jedispool was not init");
//    }
//}
