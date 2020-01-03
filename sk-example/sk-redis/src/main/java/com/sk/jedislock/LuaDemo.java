//package com.sk.jedislock;
//
//import redis.clients.jedis.Jedis;
//
//import java.util.ArrayList;
//import java.util.List;
//
///**
// *
// * @author zhangqiao
// * @since 2019/12/24 14:16
// */
//public class LuaDemo {
//
//    public static void main(String[] args) throws Exception {
//
//        Jedis jedis=RedisManager.getJedis();
//
//        String lua="local num=redis.call('incr',KEYS[1])\n" +
//                "if tonumber(num)==1 then\n" +
//                "   redis.call('expire',KEYS[1],ARGV[1])\n" +
//                "   return 1\n" +
//                "elseif tonumber(num)>tonumber(ARGV[2]) then\n" +
//                "   return 0\n" +
//                "else\n" +
//                "   return 1\n" +
//                "end";
//        List<String> keys=new ArrayList<>();
//        keys.add("ip:limit:127.0.0.1");
//        String sha=jedis.scriptLoad(lua);
//        System.out.println(sha);
//        List<String> arggs=new ArrayList<>();
//        arggs.add("6000");
//        arggs.add("10");
//        Object obj=jedis.eval(lua,keys,arggs);
//        jedis.eval(lua,keys,arggs);
//        jedis.eval(lua,keys,arggs);
//        jedis.eval(lua,keys,arggs);
//        jedis.eval(lua,keys,arggs);
//
//
////        Object obj=jedis.evalsha(lua,keys,arggs);
//        System.out.println(obj);
//    }
//}
