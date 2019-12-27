package com.sk.controller;

import com.sk.common.base.BaseController;
import com.sk.common.base.entity.SysRole;
import com.sk.common.base.service.SysRoleService;
import com.sk.common.config.page.TableDataInfo;
import com.sk.common.config.po.Result;
import com.sk.common.utils.EmptyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 角色管理控制层
 *
 * @author zhangqiao
 * @since 2019-07-06 21:39:03
 */
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController {

    private String prefix = "/role";

    @Value("${server.port}")
    private String port;

    @Autowired
    private SysRoleService roleService;

    /**
     * 进入
     *
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("port", port);
        return prefix + "/role";
    }
    
    /**
     *获取列表
     *
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public Result getList(SysRole sysRole) {
        List<SysRole> sysRoles = roleService.selectList(sysRole);
        return success(sysRoles);
    }

    /**
     * 远程获取数据
     *
     * @param principal
     * @return
     */
    @GetMapping("/getList.json")
    @ResponseBody
    public Result getListJson(Principal principal) {
        List<SysRole> sysRoles = roleService.selectList();
        Map<String, Object> map = new HashMap<>();
        map.put("数据", sysRoles);
        map.put("数据来源", "client-demo");
        map.put("调用者", principal.getName());
        map.put("desc", "访问此数据需要有效的token");
        return success(map);
    }

    /**
     * 获取tableList
     * 
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/tableList", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo tableList(SysRole sysRole) {
        //开启分页
        startPage();
        List<SysRole> sysRoles = roleService.selectList((c, e) -> {
            if (EmptyUtils.isNotEmpty(sysRole) && EmptyUtils.isNotEmpty(sysRole.getName())) {
                c.andLike("name", "%" + sysRole.getName() + "%");
            }
        });
        return getDataTable(sysRoles);
    }

    /**
     * 进入新增页面
     *
     * @return
     */
    @RequestMapping(value = "/toAdd", method = RequestMethod.GET)
    public String toAdd() {
        return prefix + "/add";
    }

    /**
     * 进入修改页
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model model) {
        SysRole sysRole = roleService.selectOne(id);
        model.addAttribute("sysRole", sysRole);
        return prefix + "/edit";
    }

    @ResponseBody
    @RequestMapping("/getById")
    public Result getById(int id) {
        SysRole sysRole = roleService.selectOne(id);
        return success(sysRole);

    }

    /**
     * 保存
     *
     * @param sysRole
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(SysRole sysRole) {
        int i = roleService.saveAndUpdate(sysRole);
        return result(i);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(Integer[] ids) {
        int ret = roleService.undelete(ids);
        return result(ret);
    }

}