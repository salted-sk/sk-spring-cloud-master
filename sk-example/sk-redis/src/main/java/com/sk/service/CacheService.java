package com.sk.service;

import com.sk.common.base.BaseService;
import com.sk.common.base.dao.SysUserDao;
import com.sk.common.base.entity.SysUser;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 缓存service
 *
 * @author zhangqiao
 * @since 2020/1/3 9:07
 */

/**
 1、@Cacheable
 * 根据方法的请求参数对其结果进行缓存
 * key：缓存的key，可以为空，如果指定要按照SPEL表达式编写，如果不指定，则按照方法的所有参数进行组合。
 * value：缓存的名称，必须指定至少一个（如 @Cacheable (value='user')或者@Cacheable(value={'user1','user2'})）
 * condition：缓存的条件，可以为空，使用SPEL编写，返回true或者false，只有为true才进行缓存。
 2、@CachePut
 * 根据方法的请求参数对其结果进行缓存，和@Cacheable不同的是，它每次都会触发真实方法的调用。参数描述见上。
 3、@CacheEvict
 * 根据条件对缓存进行清空
 * key：同上
 * value：同上
 * condition：同上
 * allEntries：是否清空所有缓存内容，缺省为false，如果指定为true，则方法调用后将立即清空所有缓存
 * beforeInvocation：是否在方法执行前就清空，缺省为false，如果指定为true，则在方法还没有执行的时候就清空缓存。缺省情况下，如果方法执行抛出异常，则不会清空缓存。
 */
@Service
public class CacheService extends BaseService<SysUser, SysUserDao> {

    @Cacheable(value = "user")
    public List<SysUser> getUsers() {
        return selectList();
    }

    @Cacheable(value = "user", key = "#id")
    public SysUser getUser(Integer id) {
        return selectOne(id);
    }

    @Cacheable(value = "sk", key = "#id")
    public SysUser getskUser(Integer id) {
        return selectOne(id);
    }

    /**
     * 缓存的为返回值
     *
     * @param user
     * @return
     */
    @CachePut(value = "user", key = "#user.id")
    public SysUser save(SysUser user) {
        saveAndUpdate(user);
        return selectOne(user.getId());
    }

    //allEntries = true删除所有缓存（同一value）
    @Override
    @CacheEvict(value = "user", key = "#id", allEntries = true)//key = "#p0"第一个参数为key名称
    public int delete(Integer id) {
        return undelete(id);
    }

}
