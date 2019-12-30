package com.sk.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/10/5 20:40
 */
@FeignClient(name = "spring-cloud-server-demo")
public interface FeignAService {

    @GetMapping("/server-demo/client/logout")
    String logout();

}
