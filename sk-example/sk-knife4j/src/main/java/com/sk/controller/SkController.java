package com.sk.controller;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.swagger.web.SwaggerResource;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/11/29 18:44
 */
@RestController
@RequestMapping("/sk")
public class SkController {

    @RequestMapping("/swagger-resources")
    @ResponseBody
    public ResponseEntity<List<SwaggerResource>> swaggerResources() {
        List<SwaggerResource> resources  = new ArrayList();
        SwaggerResource resource = new SwaggerResource();
        resource.setName("1.0.0");

        resource.setUrl("/v2/api-docs?group=" + resource.getName());
        resource.setLocation(resource.getUrl());
        resource.setSwaggerVersion("2.0");
        resources.add(resource);
        return new ResponseEntity(resources, HttpStatus.OK);
    }

}
