package com.sk.common.base.dao;

import com.sk.common.base.entity.SysPermission;
import com.sk.common.mapper.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统权限Dao层
 *
 * @author zhangqiao
 * @since 2019-11-11 09:25:07
 */
public interface SysPermissionDao extends MyMapper<SysPermission> {

    List<SysPermission> selectUserPermissions(@Param ("userId") Integer userId);

}