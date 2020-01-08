package com.sk.config;

import com.sk.blog.entity.BlogSysUser;
import com.sk.blog.service.BlogSysUserService;
import com.sk.common.base.entity.SysPermission;
import com.sk.common.base.entity.SysUser;
import com.sk.common.base.service.SysPermissionService;
import com.sk.common.base.service.SysUserService;
import com.sk.common.utils.EmptyUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;
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
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

    @Autowired
    private BlogSysUserService userService;

    @Autowired
    private SysPermissionService permissionService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String[] loginInfo = username.split(":", 2);
        BlogSysUser user;
        if (loginInfo.length > 1) {
            if ("mobile".equals(loginInfo[0])) {
                user = userService.getUserByMobile(loginInfo[1]);
            } else  {
                user = userService.getUserByUsername(loginInfo[1]);
                if (user == null) {
                    user = userService.getUserByEmail(loginInfo[1]);
                }
            }
        } else {
            user = userService.getUserByUsername(username);
        }
        return getUserDetails(user);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String account) throws UsernameNotFoundException {
        BlogSysUser user = userService.getUserByUsername(account);
        return getUserDetails(user);
    }

    public LoginUser getUserDetails(BlogSysUser user) {
        if (EmptyUtils.isNotEmpty(user) && !user.getDeleted()) {
            if (user.getStatus() != 1) {
                //当前用户被锁
                return LoginUser.withUsername(user.getUsername())
                        .truename(user.getScreenName())
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
            log.info("用户:" + user.getScreenName() + "登陆！");
            return LoginUser.withUsername(user.getUsername())
                    .truename(user.getScreenName())
                    .account(user.getUsername())
                    .sex(user.getSex())
                    .imageUrl(user.getHomeUrl())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        }
        return null;
    }

}
