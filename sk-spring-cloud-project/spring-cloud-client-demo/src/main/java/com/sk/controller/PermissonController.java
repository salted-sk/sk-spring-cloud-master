package com.sk.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sk.common.config.po.Result;
import com.sk.sevice.FeignPermissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

/**
 * 调用服务端权限数据
 *
 * @author zhangqiao
 * @since 2019/11/21 13:31
 */
@RestController
@RequestMapping("/permission")
public class PermissonController {

    @Autowired
    private FeignPermissonService permissonService;

    @HystrixCommand(
            fallbackMethod = "hysPermission",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",
                            value = "200")
            }
    )
    @GetMapping("/getList")
    public Result getPermissionJson() {
        try {
            //随机设置超时时间，进入熔断
            Thread.sleep(new Random().nextInt(300));
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Result result = permissonService.getListJson();
        return result;
    }

    public Result hysPermission(Throwable throwable) {
        Result result = new Result();
        result.setCode(405);
        result.setMessage("服务异常或超时,进入（熔断）方法");
        return result;
    }

}
