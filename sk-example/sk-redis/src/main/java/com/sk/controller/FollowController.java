package com.sk.controller;

import com.sk.service.FollowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

/**
 * 用户关注控制层
 *
 * @author zhangqiao
 * @since 2020/5/18 15:30
 */
@RestController
@RequestMapping("/follow")
public class FollowController {

    @Autowired
    private FollowService followService;

    /**
     * 关注/取消关注
     * @param id
     * @param id2
     * @return
     */
    @GetMapping("/addOrRelease/{id}/{id2}")
    public int addOrRelease(@PathVariable Integer id, @PathVariable  Integer id2){
        return followService.addOrRelease(id, id2);
    }

    /**
     * 判断2个人的关系
     * @param id
     * @param id2
     * @return
     */
    @GetMapping("/checkRelations/{id}/{id2}")
    public int checkRelations(@PathVariable  Integer id, @PathVariable  Integer id2){
        return followService.checkRelations(id, id2);
    }

    /**
     * 查询2个人共同关注的人
     * @param id
     * @param id2
     * @return
     */
    @GetMapping("/findCommonFollowing/{id}/{id2}")
    public Set findCommonFollowing(@PathVariable  Integer id, @PathVariable  Integer id2){
        return followService.findCommonFollowing(id, id2);
    }

    /**
     * 查找关注我的人
     * @param id
     * @return
     */
    @GetMapping("/addOrRelease/{id}/{id2}")
    public Set addOrRelease(@PathVariable  Integer id){
        return followService.findFans(id);
    }

    /**
     * 查找我关注的人
     * @param id
     * @return
     */
    @GetMapping("/findFollwings/{id}/{id2}")
    public Set findFollwings(@PathVariable  Integer id){
        return followService.findFollwings(id);
    }

}
