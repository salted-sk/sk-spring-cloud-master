package com.sk.config.properties;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/1/7 17:22
 */
@Data
public class QqProperties {

    private String clientId;

    private String clientSecret;

    private String filterProcessesUrl;

}
