package com.sk.config;

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
 * @see org.springframework.security.oauth2.provider.client.ClientDetailsUserDetailsService;
 */
@Service
@Slf4j
public class UserDetailsServiceImpl implements UserDetailsService, SocialUserDetailsService {

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
        System.out.println("用户登录-用户登录信息="+ user.toString());
        return getUserDetails(user);
    }

    @Override
    public SocialUserDetails loadUserByUserId(String account) throws UsernameNotFoundException {
        SysUser user = userService.getUserByUsername(account);
        return getUserDetails(user);
    }

    public LoginUser getUserDetails(SysUser user) {
        if (EmptyUtils.isNotEmpty(user) && !user.getDeleted()) {
            if (user.getStatus() != 1) {
                //当前用户被锁
                return LoginUser.withUsername(user.getAccount())
                        .userId(Long.valueOf(user.getId()))
                        .truename(user.getTrueName())
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
            log.info("用户:" + user.getTrueName() + "登陆！");
            return LoginUser.withUsername(user.getAccount())
                    .userId(Long.valueOf(user.getId()))
                    .truename(user.getTrueName())
                    .account(user.getAccount())
                    .sex(user.getSex())
                    .imageUrl(user.getImageUrl())
                    .password(user.getPassword())
                    .authorities(authorities)
                    .build();
        }
        return null;
    }

}
