package com.sk.controller;

import com.sk.common.config.po.Result;
import com.sk.sevice.FeignPermissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

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

    @GetMapping("/getList")
    public Result getPermissionJson() {
        Result result = permissonService.getListJson();
        return result;
    }

}
