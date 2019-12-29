package com.sk.service;

import com.sk.common.base.BaseService;
import com.sk.common.base.dao.SysUserDao;
import com.sk.common.base.entity.SysUser;
import com.sk.dao.SysUserMasterDao;
import com.sk.dao.SysUserSlaveDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 三个dao层 分别为默认的和一个master以及slave
 *
 * @author zhangqiao
 * @since 2019-11-11 10:25:07
 */
@Service
public class UserService extends BaseService<SysUser, SysUserDao> {

    @Autowired
    private SysUserMasterDao userMasterDao;

    @Autowired
    private SysUserSlaveDao userSlaveDao;

    /**
     * 主数据源用户列表
     *
     * @return
     */
    public List<SysUser> masterUsers() {
        return userMasterDao.selectAll();
    }

    /**
     * 从数据源用户列表
     *
     * @return
     */
    public List<SysUser> slaveUsers() {
        return userSlaveDao.selectAll();
    }
}