package com.sk.common.base.service;

import com.sk.common.base.BaseService;
import com.sk.common.base.dao.SysPermissionDao;
import com.sk.common.base.entity.SysPermission;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 系统权限服务层
 *
 * @author zhangqiao
 * @since 2019-11-11 10:25:05
 */
@Service
public class SysPermissionService extends BaseService<SysPermission, SysPermissionDao> {

    /**
     * 根据用户id查询当前用户所有权限
     *
     * @param userId 用户id
     * @return List<SysPermission>
     */
    public List<SysPermission> userPermissions(Integer userId) {
        return dao.selectUserPermissions(userId);
    }

}