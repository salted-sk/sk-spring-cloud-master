package com.sk.controller;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/11/28 16:41
 */
@RestController
@RequestMapping("/demo1")
public class Demo1 {

    @ApiOperation(value = "获取角色列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "参数1", required = true, defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param2", value = "参数2", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param3", value = "参数3", required = true, defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param4", value = "参数4", defaultValue = "1", dataType = "String"),
    })
    @GetMapping("/a")
    public Object demo1(){
        Map map = new HashMap();
        map.put("A", "a");
        return map;
    }

}
