package com.sk.controller;

import com.sk.common.base.BaseController;
import com.sk.common.base.entity.SysPermission;
import com.sk.common.base.service.SysPermissionService;
import com.sk.common.config.page.TableDataInfo;
import com.sk.common.config.po.Result;
import com.sk.common.utils.EmptyUtils;
import com.sk.common.utils.ServletUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 控制层
 *
 * @author zhangqiao
 * @since 2019-07-06 21:39:03
 */
@Controller
@RequestMapping("/permission")
public class PermissionController extends BaseController {

    @Value("${server.port}")
    private String port;

    private String prefix = "permission";

    @Autowired
    private SysPermissionService permissionService;

    /**
     * 进入
     *
     * @return
     */
    @PreAuthorize("hasAuthority('PERMISSION:QUERY')")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public String list(Model model) {
        model.addAttribute("port", port);
        return prefix + "/permission";
    }
    
    /**
     *获取列表
     *
     * @param syspermission
     * @return
     */
    @PreAuthorize("hasAuthority('PERMISSION:QUERY')")
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public List<SysPermission> getList(SysPermission syspermission) {
        List<SysPermission> syspermissions = permissionService.selectList(syspermission);
        return syspermissions;
    }

    @PreAuthorize("hasAuthority('PERMISSION:QUERY')")
    @GetMapping("/getList")
    @ResponseBody
    public Result getList(Principal principal) {
        List<SysPermission> syspermissions = permissionService.selectList();
        Map<String, Object> map = new HashMap<>();
        map.put("端口", port);
        map.put("数据", syspermissions);
        map.put("数据来源", "service-b");
        map.put("调用者", principal.getName());
        return success(map);
    }


    /**
     * 远程获取数据
     *
     * @param principal
     * @return
     */
//    @PreAuthorize("hasAuthority('PERMISSION:QUERY')")
    @GetMapping("/getList.json")
    @ResponseBody
    public Result getListJson(Principal principal) {
        Principal userPrincipal = ServletUtils.getRequest().getUserPrincipal();
        List<SysPermission> syspermissions = permissionService.selectList();
        Map<String, Object> map = new HashMap<>();
        map.put("数据", syspermissions);
        map.put("数据来源", "service-b");
        map.put("调用者", principal.getName());
        map.put("desc", "访问此数据需要有效的token");
        return success(map);
    }

    /**
     * 获取tableList
     * 
     * @param syspermission
     * @return
     */
    @PreAuthorize("hasAuthority('PERMISSION:QUERY')")
    @RequestMapping(value = "/tableList", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo tableList(SysPermission syspermission) {
        //开启分页
        startPage();
        List<SysPermission> sysRoles = permissionService.selectList((c, e) -> {
            if (EmptyUtils.isNotEmpty(syspermission) && EmptyUtils.isNotEmpty(syspermission.getName())) {
                c.andLike("name", "%" + syspermission.getName() + "%");
            }
            c.andEqualTo("deleted", false);
        });
        return getDataTable(sysRoles);
    }

    /**
     * 进入新增页面
     *
     * @return
     */
    @PreAuthorize("hasAuthority('PERMISSION:SAVE')")
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
    @PreAuthorize("hasAuthority('PERMISSION:SAVE')")
    @RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
    public String edit(@PathVariable("id") int id, Model model) {
        SysPermission permission = permissionService.selectOne(id);
        model.addAttribute("permission", permission);
        return prefix + "/edit";
    }

    /**
     * 保存
     *
     * @param syspermission
     * @return
     */
    @PreAuthorize("hasAuthority('PERMISSION:SAVE')")
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(SysPermission syspermission) {
        int i = permissionService.saveAndUpdate(syspermission);
        return result(i);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PreAuthorize("hasAuthority('PERMISSION:DELETE')")
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Result delete(Integer[] ids) {
        int ret = permissionService.undelete(ids);
        return result(ret);
    }

}