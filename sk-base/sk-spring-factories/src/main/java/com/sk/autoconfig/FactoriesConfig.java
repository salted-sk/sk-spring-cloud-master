package com.sk.autoconfig;

import com.sk.filter.SleuthDemoFilter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

/**
 * 自动注入配置
 *
 * @author zhangqiao
 * @since 2020/9/24 15:58
 */
@Configuration
@EnableConfigurationProperties({FactoriesProperties.class})
public class FactoriesConfig {

    @Bean
    @ConditionalOnProperty(prefix = "sk.sleuth", value = "enabled", havingValue = "true")
    public FilterRegistrationBean registrationBean (){
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        SleuthDemoFilter filter = new SleuthDemoFilter();
        registrationBean.setFilter(filter);
        List<String> patterns = new ArrayList<>();
        registrationBean.setUrlPatterns(patterns);
        return registrationBean;
    }

}
