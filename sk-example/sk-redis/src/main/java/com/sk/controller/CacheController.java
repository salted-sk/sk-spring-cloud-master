package com.sk.controller;

import com.sk.common.base.entity.SysUser;
import com.sk.service.CacheService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 缓存demo controller
 *
 * @author zhangqiao
 * @since 2020/1/3 9:01
 */
@RestController
public class CacheController {

    @Autowired
    private CacheService cacheService;

    @GetMapping("/list")
    public List<SysUser> getUsers() {
        return cacheService.getUsers();
    }

    @GetMapping("/get/{id}")
    public SysUser getUser(@PathVariable("id") Integer id) {
        return cacheService.getUser(id);
    }

    @GetMapping("/save")
    public SysUser save(SysUser user) {
        return cacheService.save(user);
    }

    @GetMapping("/del/{id}")
    public int delete(@PathVariable("id") Integer id) {
        return cacheService.delete(id);
    }
}
