package com.sk.common.base.service;

import com.sk.common.base.BaseService;
import com.sk.common.base.dao.SysUserDao;
import com.sk.common.base.entity.SysUser;
import com.sk.common.utils.EmptyUtils;
import org.springframework.stereotype.Service;

/**
 * 系统用户服务层
 *
 * @author zhangqiao
 * @since 2019-11-11 10:25:07
 */
@Service
public class SysUserService extends BaseService<SysUser, SysUserDao> {

    /**
     * 根据用户账号获取当前用户信息
     *
     * @param username
     * @return
     */
    public SysUser getUserByUsername(String username) {
        if (EmptyUtils.isNotEmpty(username)) {
            SysUser user = new SysUser();
            user.setAccount(username);
            return selectOne(user);
        }
        return null;
    }
    
}