package com.sk.dao;

import com.sk.common.base.entity.SysUser;
import com.sk.common.mapper.MyMapper;
import com.sk.config.datasource.DataSource;

/**
 * 系统用户Dao层
 *
 * @author zhangqiao
 * @since 2019-11-11 09:25:07
 */
@DataSource("master")
public interface SysUserMasterDao extends MyMapper<SysUser> {

}