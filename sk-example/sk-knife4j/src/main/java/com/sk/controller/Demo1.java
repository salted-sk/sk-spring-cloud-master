package com.sk.controller;

import com.sk.domain.Demo1Domain1;
import com.sk.domain.Demo1Domain2;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/11/28 16:41
 */
@Api(tags = {"1.0.0"})
@RestController
@RequestMapping("/demo1")
public class Demo1 {

    @ApiOperation(value = "获取角色列表", tags = {"1.0.0","223",""})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "参数1", required = true, defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param2", value = "参数2", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param3", value = "参数3", required = true, defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param4", value = "参数4", defaultValue = "1", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功", response = Demo1Domain1.class),
            @ApiResponse(code = 201, message = "返回成功2", response = Demo1Domain2.class)
    })
    @GetMapping("/a")
    public Map demo1(){
        Map map = new HashMap();
        map.put("A", "a");
        return map;
    }

    @ApiOperation(value = "获取角色列表2", tags = {"1.0.0"})
    @ApiImplicitParams({
            @ApiImplicitParam(name = "param1", value = "参数1", required = true, defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param2", value = "参数2", defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param3", value = "参数3", required = true, defaultValue = "1", dataType = "String"),
            @ApiImplicitParam(name = "param4", value = "参数4", defaultValue = "1", dataType = "String"),
    })
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功", response = Demo1Domain1.class),
    })
    @GetMapping("/b")
    public Map demo2(){
        Map map = new HashMap();
        map.put("A", "a");
        return map;
    }

    @ApiOperation(value = "获取角色列表3", tags = {"2.0.0"})
    @ApiResponses({
            @ApiResponse(code = 200, message = "返回成功", response = Demo1Domain1.class),
    })
    @PostMapping("/b")
    @Deprecated
    public Map demo3(@RequestBody Demo1Domain1 demo1Domain1){
        Map map = new HashMap();
        map.put("A", "a");
        return map;
    }

}
