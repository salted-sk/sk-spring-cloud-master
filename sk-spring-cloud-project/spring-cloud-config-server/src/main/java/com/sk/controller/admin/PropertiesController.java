package com.sk.controller.admin;

import com.sk.common.base.BaseController;
import com.sk.common.config.page.TableDataInfo;
import com.sk.common.config.po.Result;
import com.sk.common.exception.DaoException;
import com.sk.common.utils.EmptyUtils;
import com.sk.entity.Properties;
import com.sk.service.PropertiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 控制层
 *
 * @author zhangqiao
 * @since 2019-12-12 11:11:40
 */
@Controller
public class PropertiesController extends BaseController {

    private String prefix = "properties";

    @Autowired
    private PropertiesService propertiesService;

    /**
     * 进入
     *
     * @return
     */
    @GetMapping({"/list", "/"})
    public String list(Model model) {
        List<String> applications = propertiesService.types("application");
        List<String> profiles = propertiesService.types("profile");
        List<String> labels = propertiesService.types("label");
        model.addAttribute("applications", applications);
        model.addAttribute("profiles", profiles);
        model.addAttribute("labels", labels);
        return prefix + "/properties";
    }
    
    /**
     *获取列表
     *
     * @param properties
     * @return
     */
    @PostMapping("/getList")
    @ResponseBody
    public List<Properties> getList(Properties properties) {
        List<Properties> propertiess = propertiesService.selectList(properties);
        return propertiess;
    }

    /**
     * 获取tableList
     * 
     * @param properties
     * @return
     */
    @PostMapping("/tableList")
    @ResponseBody
    public TableDataInfo tableList(Properties properties) {
        //开启分页
        startPage();
        List<Properties> propertiess = propertiesService.selectList((criteria, example) -> {
            if (EmptyUtils.isNotEmpty(properties.getKey())) {
                criteria.andLike("key","%" + properties.getKey() + "%");
            }
            if (EmptyUtils.isNotEmpty(properties.getApplication())) {
                criteria.andEqualTo("application",properties.getApplication());
            }
            if (EmptyUtils.isNotEmpty(properties.getProfile())) {
                criteria.andEqualTo("profile",properties.getProfile());
            }
            if (EmptyUtils.isNotEmpty(properties.getLabel())) {
                criteria.andEqualTo("label",properties.getLabel());
            }
            if (EmptyUtils.isNotEmpty(properties.getStatus())) {
                criteria.andEqualTo("status",properties.getStatus());
            }
        });
        return getDataTable(propertiess);
    }

    /**
     * 进入新增页面
     *
     * @return
     */
    @GetMapping("/toAdd")
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
    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") int id, Model model) {
        Properties properties = propertiesService.selectOne(id);
        model.addAttribute("properties", properties);
        return prefix + "/edit";
    }

    /**
     * 保存
     *
     * @param properties
     * @return
     */
    @PostMapping("/save")
    @ResponseBody
    public Result save(Properties properties) {
        if (EmptyUtils.isEmpty(properties.getApplication())) {
            properties.setApplication(null);
        }
        if (EmptyUtils.isEmpty(properties.getProfile())) {
            properties.setProfile(null);
        }
        if (EmptyUtils.isEmpty(properties.getLabel())) {
            properties.setLabel(null);
        }
        int i = propertiesService.saveAndUpdate(properties);
        return result(i);
    }

    /**
     * 批量删除
     *
     * @param ids
     * @return
     */
    @PostMapping("/delete")
    @ResponseBody
    public Result delete(Integer[] ids) {
        int ret = propertiesService.undelete(ids);
        return result(ret);
    }

    /**
     * 获取数量
     *
     * @param properties
     * @return
     */
    @PostMapping("/getCount")
    @ResponseBody
    public int getCount(Properties properties) {
        return propertiesService.selectCount(properties);
    }

}