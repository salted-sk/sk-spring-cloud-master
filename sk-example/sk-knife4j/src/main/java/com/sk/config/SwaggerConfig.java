package com.sk.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.RequestHandler;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Tag;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.WebMvcRequestHandler;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2WebMvc;

import javax.annotation.Resource;
import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/11/28 16:19
 */
@Configuration
@EnableSwagger2WebMvc
@EnableKnife4j
public class SwaggerConfig {



    @Bean
    public Docket createRestApi() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .groupName("1.0版本")
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                .apis(del(Deprecated.class))
                .paths(PathSelectors.any())
                .build();

    }

    public static Predicate<RequestHandler> del(final Class<? extends Annotation> annotation) {
        return (input) -> {
            ApiOperation apiOperation = ((WebMvcRequestHandler) input).getHandlerMethod().getMethod().getDeclaredAnnotation(ApiOperation.class);
            String[] tags = apiOperation.tags();
            return tags.length>1;
        };
    }

    @Bean
    public Docket createRestApi2() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .groupName("2.0版本")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.sk.controller"))
                .paths(PathSelectors.any())
                .build();

    }

    @Bean
    public Docket createRestApi3() {
        return  new Docket(DocumentationType.SWAGGER_2)
                .useDefaultResponseMessages(false)
                .apiInfo(apiInfo())
                .groupName("3.0版本")
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("专为***提供的api接口")
                .description("没有秒杀")
                .termsOfServiceUrl("http://localhost:8999/")
                .version("1.0")
                .build();
    }

}
