package com.sk.config;

import com.sk.common.base.entity.SysPermission;
import com.sk.common.base.entity.SysRolePermission;
import com.sk.common.base.entity.SysUser;
import com.sk.common.base.entity.SysUserRole;
import com.sk.common.base.service.SysPermissionService;
import com.sk.common.base.service.SysRolePermissionService;
import com.sk.common.base.service.SysUserRoleService;
import com.sk.common.base.service.SysUserService;
import com.sk.common.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 用户登陆验证
 *
 * @author zhangqiao
 * @since 2019/11/19 15:12
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private SysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] loginInfo = username.split(":", 2);
        SysUser user;
        if (loginInfo.length > 1) {
            if ("mobile".equals(loginInfo[0])) {
                user = userService.getUserByMobile(loginInfo[1]);
            } else  {
                user = userService.getUserByUsername(loginInfo[1]);
            }
        } else {
            user = userService.getUserByUsername(username);
        }
        if (EmptyUtils.isNotEmpty(user) && !user.getDeleted()) {
            if (user.getStatus() != 1) {
                //当前用户被锁
                return User.withUsername(user.getTrueName())
                        .password(user.getPassword())
                        .authorities(new ArrayList<>())
                        .accountLocked(true)
                        .build();
            }
            //获取当前用户的所有权限
            List<SysPermission> userPermissions = permissionService.userPermissions(user.getId());
            //获取当前用户权限集合
            Set<SimpleGrantedAuthority> authorities = new HashSet<>();
            userPermissions.forEach(permission -> {
                authorities.add(new SimpleGrantedAuthority(permission.getUrl()));
            });
            return User.withUsername(user.getTrueName())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        }
        return null;
    }

//    private Set<SimpleGrantedAuthority> grantedAuthority(SysUser user) {
//        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
//        //获取当前默认角色集合
//        List<SysUserRole> defaultRoles = userRoleService
//                .selectList((criteria, example) ->
//                        criteria.andEqualTo("defaultRole", true)
//                                .andEqualTo("status", 1));
//        //获取当前用户角色集合
//        List<SysUserRole> userRoles = userRoleService
//                .selectList((criteria, example) ->
//                        criteria.andEqualTo("userId", user.getId())
//                                .andEqualTo("status", 1));
//        //获取用户角色的所有权限
//        userRoles.forEach(userRole -> {
//            List<SysRolePermission> rolePermissions = rolePermissionService
//                    .selectList((criteria, example) ->
//                            criteria.andEqualTo("roleId", userRole.getId())
//                                    .andEqualTo("status", 1));
//            rolePermissions.forEach(rolePermission -> {
//                SysPermission permission = permissionService.selectOne(rolePermission.getPermissionId());
//                if (EmptyUtils.isNotEmpty(permission)) {
//                    authorities.add(new SimpleGrantedAuthority(permission.getUrl()));
//                }
//            });
//        });
//        return authorities;
//    }

}
