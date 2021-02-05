package com.sk.config;

import com.sk.common.exception.ApplicationException;
import com.sk.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.util.OAuth2Utils;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2021/2/4 9:05
 */
public class SkAuthenticationKeyGenerator implements AuthenticationKeyGenerator {
    private static final String CLIENT_ID = "client_id";
    private static final String SCOPE = "scope";
    private static final String USERNAME = "username";

    private RedisTokenStore redisTokenStore;

    public SkAuthenticationKeyGenerator(RedisTokenStore redisTokenStore) {
        this.redisTokenStore = redisTokenStore;
    }

    @Override
    public String extractKey(OAuth2Authentication authentication) {
        Map<String, String> values = new LinkedHashMap<>();
        OAuth2Request authorizationRequest = authentication.getOAuth2Request();
        if (!authentication.isClientOnly()) {
            values.put(USERNAME, authentication.getName());
        }

        values.put(CLIENT_ID, authorizationRequest.getClientId());
        if (authorizationRequest.getScope() != null) {
            values.put(SCOPE, OAuth2Utils.formatParameterList(new CopyOnWriteArraySet<>(authorizationRequest.getScope())));
        }

        Collection<OAuth2AccessToken> tokens = redisTokenStore.findTokensByClientIdAndUserName(authorizationRequest.getClientId(), authentication.getName());
        if (!CollectionUtils.isEmpty(tokens)) {
//            throw new ApplicationException("账号已在别处登录");
            //清除之前账号获取的token，并生成新的token，达到强制让原来的账号下线
            for (OAuth2AccessToken oAuth2AccessToken : tokens) {
                if (oAuth2AccessToken != null){
                    redisTokenStore.removeAccessToken(oAuth2AccessToken.getValue());
                    String refreshToken = ServletUtils.getRequest().getParameter("refresh_token");
                    if (StringUtils.isEmpty(refreshToken)) {
                        redisTokenStore.removeRefreshToken(oAuth2AccessToken.getRefreshToken());

                    }
                }
            }
        }
        return this.generateKey(values);
    }

    protected String generateKey(Map<String, String> values) {
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            byte[] bytes = digest.digest(values.toString().getBytes("UTF-8"));
            System.out.println("token=======is ===" + String.format("%032x", new BigInteger(1, bytes)));
            return String.format("%032x", new BigInteger(1, bytes));
        } catch (NoSuchAlgorithmException var4) {
            throw new IllegalStateException("MD5 algorithm not available.  Fatal (should be in the JDK).", var4);
        } catch (UnsupportedEncodingException var5) {
            throw new IllegalStateException("UTF-8 encoding not available.  Fatal (should be in the JDK).", var5);
        }
    }
}
