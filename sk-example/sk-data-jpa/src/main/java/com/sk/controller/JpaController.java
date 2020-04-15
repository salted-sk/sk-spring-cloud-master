package com.sk.controller;

import com.sk.entity.JpaTable1;
import com.sk.service.JpaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/4/15 15:10
 */
@RestController
public class JpaController {

    @Autowired
    private JpaService jpaService;

    @RequestMapping
    public List<JpaTable1> getList() {
        return jpaService.getList();
    }

}
