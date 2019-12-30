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
        return selectOne(() -> {
            SysUser user = new SysUser();
            user.setAccount(username);
            return user;
        });
    }

    public SysUser getUserByMobile(String mobile) {
        return selectOne(() -> {
            SysUser user = new SysUser();
            user.setPhone(mobile);
            return user;
        });
    }
    
}