package com.sk.controller;

import com.sk.common.base.BaseController;
import com.sk.common.base.service.SysPermissionService;
import com.sk.common.config.page.TableDataInfo;
import com.sk.common.config.po.Result;
import com.sk.config.FdfsUpAndDowServiceImpl;
import com.sk.entity.UpFile;
import com.sk.entity.UpFiles;
import com.sk.service.UpFilesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2019/12/4 13:01
 */
@Controller
public class FileController extends BaseController {

    @Autowired
    private FdfsUpAndDowServiceImpl upAndDowService;

    @Autowired
    private UpFilesService upFilesService;

    private String prefix = "file";

    @RequestMapping({"/file"})
    public String file(){
        return "fileUpload";
    }

    @RequestMapping({"/index"})
    public String index(){
        return "index";
    }

    /**
     * 上传文件
     *
     * @param fileName
     * @return
     */
    @PostMapping("/upload")
    public String fileUpload(MultipartFile fileName, HttpServletResponse response) throws IOException {
        String fileUrl = upAndDowService.uploadFile(fileName);
        UpFile files = new UpFile();
        files.setUrl(fileUrl);
        files.setName(fileName.getOriginalFilename());
        upFilesService.saveAndUpdate(files);
        return "fileUpload";
    }

    @GetMapping("/download")
    public void download(String url, HttpServletResponse response) {
        try {
            upAndDowService.downloadFile(url, response);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 进入
     *
     * @return
     */
    @GetMapping({"/list", "/"})
    public String list() {
        return prefix + "/file";
    }

    /**
     *获取列表
     *
     * @param upFiles
     * @return
     */
    @RequestMapping(value = "/getList", method = RequestMethod.POST)
    @ResponseBody
    public List<UpFile> getList(UpFile upFiles) {
        List<UpFile> upFiless = upFilesService.selectList(upFiles);
        return upFiless;
    }

    /**
     * 获取tableList
     *
     * @param upFiles
     * @return
     */
    @RequestMapping(value = "/tableList", method = RequestMethod.POST)
    @ResponseBody
    public TableDataInfo tableList(UpFile upFiles) {
        //开启分页
        startPage();
        List<UpFile> upFiless = upFilesService.selectList((c, e) -> {
            if (upFiles != null && upFiles.getStatus() != null) {
                c.andEqualTo("status", upFiles.getStatus());
            }
            if (upFiles != null && upFiles.getName() != null) {
                c.andLike("name", "%" + upFiles.getName() + "%");
            }
        });
        return getDataTable(upFiless);
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
        UpFiles upFiles = upFilesService.selectOne(id);
        model.addAttribute("upFiles", upFiles);
        return prefix + "/edit";
    }

    /**
     * 保存
     *
     * @param upFiles
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    @ResponseBody
    public Result save(UpFile upFiles) {
        int i = upFilesService.saveAndUpdate(upFiles);
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
        for (Integer id : ids) {
//            UpFiles file = upFilesService.selectOne(id);
//            upAndDowService.deleteFile(file.getUrl());
        }
        int ret = upFilesService.undelete(ids);
        return result(ret);
    }


}
