package com.sk.sevice;

import com.sk.config.FeignConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

/**
 * feign调用spring-cloud-server-demo
 *
 * @author zhangqiao
 * @since 2019/10/5 20:40
 */
@FeignClient(name = "spring-cloud-server-demo", configuration={FeignConfig.class})
public interface FeignPermissonService {

    @GetMapping("/server-demo/permission/getList.json")
    Map getListJson();

}
