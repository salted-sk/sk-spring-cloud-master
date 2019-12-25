//package com.sk.config;
//
//import org.springframework.stereotype.Component;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import javax.servlet.*;
//import javax.servlet.annotation.WebFilter;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.IOException;
//
///**
// * TODO
// *
// * @author zhangqiao
// * @since 2019/12/16 13:07
// */
////@Component
////@WebFilter(filterName = "myFiler", urlPatterns = {"/js/**", "/ruoyi/**", "/css/**", "/images/**"})
//public class BaseFilter extends OncePerRequestFilter {
//
//    @Override
//    protected void doFilterInternal(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws ServletException, IOException {
//        HttpServletRequest req = (HttpServletRequest) servletRequest;
//        String requestURI = req.getRequestURI();
//        System.out.println("--------------------->拦截到静态资源：请求地址"+requestURI);
//
//        filterChain.doFilter(servletRequest, servletResponse);
//    }
//
//
//}
