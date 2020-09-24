package com.sk.autoconfig;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 相关参数配置
 *
 * @author zhangqiao
 * @since 2020/9/24 16:01
 */
@ConfigurationProperties("sk.sleuth")
@Setter
@Getter
public class FactoriesProperties {

    private boolean enabled;

}

