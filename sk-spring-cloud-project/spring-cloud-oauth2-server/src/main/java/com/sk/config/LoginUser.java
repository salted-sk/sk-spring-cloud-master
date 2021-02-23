package com.sk.config;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.util.*;
import java.util.function.Function;

/**
 * 扩展展security的user参数
 *
 * @author zhangqiao
 * @since 2020/1/2 9:34
 */
@Data
public class LoginUser implements UserDetails, CredentialsContainer, SocialUserDetails {
    private static final long serialVersionUID = 520L;
    private static final Log logger = LogFactory.getLog(LoginUser.class);
    private Long userId;
    private String account;
    private String truename;
    private String imageUrl;
    private String sex;
    private String serverSession;
    private String password;
    private final String username;
    private final Set<GrantedAuthority> authorities;
    private final boolean accountNonExpired;
    private final boolean accountNonLocked;
    private final boolean credentialsNonExpired;
    private final boolean enabled;

    public LoginUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this(null, username, password, null, null, null, null, true, true, true, true, authorities);
    }

    public LoginUser(Long userId, String account, String truename, String imageUrl, String sex, String username, String password, boolean enabled, boolean accountNonExpired, boolean credentialsNonExpired, boolean accountNonLocked, Collection<? extends GrantedAuthority> authorities) {
        if (username != null && !"".equals(username) && password != null) {
            this.userId = userId;
            this.account = account;
            this.truename = truename;
            this.imageUrl = imageUrl;
            this.sex = sex;
            this.username = username;
            this.password = password;
            this.enabled = enabled;
            this.accountNonExpired = accountNonExpired;
            this.credentialsNonExpired = credentialsNonExpired;
            this.accountNonLocked = accountNonLocked;
            this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        } else {
            throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
        }
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public void eraseCredentials() {
        this.password = null;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet(new LoginUser.AuthorityComparator());
        Iterator var2 = authorities.iterator();

        while(var2.hasNext()) {
            GrantedAuthority grantedAuthority = (GrantedAuthority)var2.next();
            Assert.notNull(grantedAuthority, "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    @Override
    public boolean equals(Object rhs) {
        return rhs instanceof LoginUser ? this.username.equals(((LoginUser)rhs).username) : false;
    }

    @Override
    public int hashCode() {
        return this.username.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString()).append(": ");
        sb.append("UserId: ").append(this.userId).append("; ");
        sb.append("Account: ").append(this.account).append("; ");
        sb.append("Truename: ").append(this.truename).append("; ");
        sb.append("Sex: ").append(this.sex).append("; ");
        sb.append("Username: ").append(this.username).append("; ");
        sb.append("ImageUrl: ").append(this.imageUrl).append("; ");
        sb.append("Password: [PROTECTED]; ");
        sb.append("Enabled: ").append(this.enabled).append("; ");
        sb.append("AccountNonExpired: ").append(this.accountNonExpired).append("; ");
        sb.append("credentialsNonExpired: ").append(this.credentialsNonExpired).append("; ");
        sb.append("AccountNonLocked: ").append(this.accountNonLocked).append("; ");
        if (!this.authorities.isEmpty()) {
            sb.append("Granted Authorities: ");
            boolean first = true;
            Iterator var3 = this.authorities.iterator();

            while(var3.hasNext()) {
                GrantedAuthority auth = (GrantedAuthority)var3.next();
                if (!first) {
                    sb.append(",");
                }

                first = false;
                sb.append(auth);
            }
        } else {
            sb.append("Not granted any authorities");
        }

        return sb.toString();
    }

    public static LoginUser.UserBuilder withUsername(String username) {
        return builder().username(username);
    }

    public static LoginUser.UserBuilder builder() {
        return new LoginUser.UserBuilder();
    }

    /** @deprecated */
    @Deprecated
    public static LoginUser.UserBuilder withDefaultPasswordEncoder() {
        logger.warn("User.withDefaultPasswordEncoder() is considered unsafe for production and is only intended for sample applications.");
        PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
        LoginUser.UserBuilder var10000 = builder();
        encoder.getClass();
        return var10000.passwordEncoder(encoder::encode);
    }

    public static LoginUser.UserBuilder withUserDetails(UserDetails userDetails) {
        return withUsername(userDetails.getUsername()).password(userDetails.getPassword()).accountExpired(!userDetails.isAccountNonExpired()).accountLocked(!userDetails.isAccountNonLocked()).authorities(userDetails.getAuthorities()).credentialsExpired(!userDetails.isCredentialsNonExpired()).disabled(!userDetails.isEnabled());
    }

    @Override
    public String getUserId() {
        return String.valueOf(this.userId);
    }

    public static class UserBuilder {
        private Long userId;
        private String account;
        private String truename;
        private String imageUrl;
        private String sex;
        private String username;
        private String password;
        private List<GrantedAuthority> authorities;
        private boolean accountExpired;
        private boolean accountLocked;
        private boolean credentialsExpired;
        private boolean disabled;
        private Function<String, String> passwordEncoder;

        private UserBuilder() {
            this.passwordEncoder = (password) -> password;
        }

        public LoginUser.UserBuilder userId(Long userId) {
            Assert.notNull(userId, "userId cannot be null");
            this.userId = userId;
            return this;
        }

        public LoginUser.UserBuilder account(String account) {
            Assert.notNull(account, "username cannot be null");
            this.account = account;
            return this;
        }
        public LoginUser.UserBuilder truename(String truename) {
            Assert.notNull(truename, "username cannot be null");
            this.truename = truename;
            return this;
        }
        public LoginUser.UserBuilder sex(String sex) {
            Assert.notNull(sex, "username cannot be null");
            this.sex = sex;
            return this;
        }
        public LoginUser.UserBuilder imageUrl(String imageUrl) {
            Assert.notNull(sex, "imageUrl cannot be null");
            this.imageUrl = imageUrl;
            return this;
        }
        public LoginUser.UserBuilder username(String username) {
            Assert.notNull(username, "username cannot be null");
            this.username = username;
            return this;
        }

        public LoginUser.UserBuilder password(String password) {
            Assert.notNull(password, "password cannot be null");
            this.password = password;
            return this;
        }

        public LoginUser.UserBuilder passwordEncoder(Function<String, String> encoder) {
            Assert.notNull(encoder, "encoder cannot be null");
            this.passwordEncoder = encoder;
            return this;
        }

        public LoginUser.UserBuilder roles(String... roles) {
            List<GrantedAuthority> authorities = new ArrayList(roles.length);
            String[] var3 = roles;
            int var4 = roles.length;

            for(int var5 = 0; var5 < var4; ++var5) {
                String role = var3[var5];
                Assert.isTrue(!role.startsWith("ROLE_"), () -> role + " cannot start with ROLE_ (it is automatically added)");
                authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
            }

            return this.authorities(authorities);
        }

        public LoginUser.UserBuilder authorities(GrantedAuthority... authorities) {
            return this.authorities(Arrays.asList(authorities));
        }

        public LoginUser.UserBuilder authorities(Collection<? extends GrantedAuthority> authorities) {
            this.authorities = new ArrayList(authorities);
            return this;
        }

        public LoginUser.UserBuilder authorities(String... authorities) {
            return this.authorities(AuthorityUtils.createAuthorityList(authorities));
        }

        public LoginUser.UserBuilder accountExpired(boolean accountExpired) {
            this.accountExpired = accountExpired;
            return this;
        }

        public LoginUser.UserBuilder accountLocked(boolean accountLocked) {
            this.accountLocked = accountLocked;
            return this;
        }

        public LoginUser.UserBuilder credentialsExpired(boolean credentialsExpired) {
            this.credentialsExpired = credentialsExpired;
            return this;
        }

        public LoginUser.UserBuilder disabled(boolean disabled) {
            this.disabled = disabled;
            return this;
        }

        public LoginUser build() {
            String encodedPassword = this.passwordEncoder.apply(this.password);
            return new LoginUser(this.userId, this.account, this.truename, this.imageUrl, this.sex, this.username, encodedPassword, !this.disabled, !this.accountExpired, !this.credentialsExpired, !this.accountLocked, this.authorities);
        }
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>, Serializable {
        private static final long serialVersionUID = 520L;

        private AuthorityComparator() {
        }

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            if (g2.getAuthority() == null) {
                return -1;
            } else {
                return g1.getAuthority() == null ? 1 : g1.getAuthority().compareTo(g2.getAuthority());
            }
        }
    }
}
