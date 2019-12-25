//package com.sk.config;
//
//import org.springframework.core.annotation.Order;
//import org.springframework.stereotype.Component;
//import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
//import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
//import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
//import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
//
//import javax.annotation.Resource;
//
///**
// * TODO
// *
// * @author zhangqiao
// * @since 2019/12/16 16:27
// */
//@Component
//@Order(Integer.MIN_VALUE)
//public class WebMvcConfig implements WebMvcConfigurer {
//    @Resource
//    private BaseInterceptor baseInterceptor;
//
////    @Override
////    public void addInterceptors(InterceptorRegistry registry) {
////        registry.addInterceptor(baseInterceptor);
////                //需要配置2：----------- 告知拦截器：/static/admin/** 与 /static/user/** 不需要拦截 （配置的是 路径）
//////                .excludePathPatterns("/js/**");
////    }
//
//    /**
//     * 添加静态资源文件，外部可以直接访问地址
//     * @param registry
//     */
//    @Override
//    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        //需要配置1：----------- 需要告知系统，这是要被当成静态文件的！
//        //第一个方法设置访问路径前缀，第二个方法设置资源路径
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/").resourceChain(false);
//    }
//
//    @Override
//    public void addViewControllers(ViewControllerRegistry registry) {
//        //registry.addViewController("/error/404").setViewName("/admin/page_error/error_404.html");
//    }
//}