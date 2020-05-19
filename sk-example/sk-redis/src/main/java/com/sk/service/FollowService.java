package com.sk.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

/**
 * 用户关注
 *
 * @author zhangqiao
 * @since 2020/5/18 15:30
 */
@Service
public class FollowService {

    @Autowired
    private RedisTemplate redisTemplate;

    private static final String FOLLOWING = "FOLLOWING_";
    private static final String FANS = "FANS_";
    private static final String COMMON_KEY = "COMMON_FOLLOWING_";

    /**
     * 关注或者取消关注
     *
     * @param userId
     * @param followingId
     * @return
     */
    public int addOrRelease(Integer userId, Integer followingId) {
        if (userId == null || followingId == null) {
            return -1;
        }
        int isFollow = 0; // 0 = 取消关注 1 = 关注
        String followingKey = FOLLOWING + userId;
        String fansKey = FANS + followingId;
        // 说明userId没有关注过followingId
        if(redisTemplate.opsForZSet().rank(followingKey,String.valueOf(followingId)) == null) {
            redisTemplate.opsForZSet().add(followingKey, String.valueOf(followingId), System.currentTimeMillis());
            redisTemplate.opsForZSet().add(fansKey, String.valueOf(userId), System.currentTimeMillis());
            isFollow = 1;
        } else {
            // 取消关注
            redisTemplate.opsForZSet().remove(followingKey, String.valueOf(followingId));
            redisTemplate.opsForZSet().remove(fansKey, fansKey);
        }
        return isFollow;
    }

    /**
     * 验证两个用户之间的关系
     * 0=没关系/自己 1=userId关注了otherUserId 2= otherUserId是userId的粉丝 3=互相关注
     *
     * @param userId
     * @param otherUserId
     * @return
     */
    public int checkRelations (Integer userId, Integer otherUserId) {
        if (userId == null || otherUserId == null) {
            return 0;
        }
        if (userId.equals(otherUserId)) {
            return 0;
        }
        String followingKey = FOLLOWING + userId;
        int relation = 0;
        if (redisTemplate.opsForZSet().rank(followingKey, String.valueOf(otherUserId)) != null) { // userId是否关注otherUserId
            relation += 1;
        }
        String fansKey = FANS + userId;
        if (redisTemplate.opsForZSet().rank(fansKey, String.valueOf(otherUserId)) != null) {// userId粉丝列表中是否有otherUserId
            relation += 2;
        }
        return relation;
    }

    /**
     * 获取用户所有关注的人的id
     *
     * @param userId
     * @return
     */
    public Set<Integer> findFollwings(Integer userId) {
        return findSet(FOLLOWING + userId);
    }

    /**
     * 获取用户所有的粉丝
     *
     * @param userId
     * @return
     */
    public Set<Integer> findFans(Integer userId) {
        return findSet(FANS + userId);
    }

    /**
     * 获取两个共同关注的人
     *
     * @param userId
     * @param otherUserId
     * @return
     */
    public Set<Integer> findCommonFollowing(Integer userId, Integer otherUserId) {
        if (userId == null || otherUserId == null) {
            return new HashSet<>();
        }
        String commonKey = COMMON_KEY + userId + "_" + otherUserId;
        // 取交集
        redisTemplate.opsForZSet().intersectAndStore(FOLLOWING + userId, FOLLOWING + otherUserId, commonKey);
        Set<Integer> result = redisTemplate.opsForZSet().range(commonKey, 0, -1);
        redisTemplate.delete(commonKey);
        return result;
    }

    /**
     * 根据key获取set
     *
     * @param key
     * @return
     */
    private Set<Integer> findSet(String key) {
        if (key == null) {
            return new HashSet<>();
        }
        Set<Integer> result = redisTemplate.opsForZSet().reverseRange(key, 0, -1);
        return result;
    }

}
