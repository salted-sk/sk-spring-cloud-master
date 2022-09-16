package com.sk.blog.website.controller.admin;

import com.github.pagehelper.PageInfo;
import com.sk.blog.website.constant.WebConst;
import com.sk.blog.website.controller.BaseController;
import com.sk.blog.website.dto.LogActions;
import com.sk.blog.website.dto.Types;
import com.sk.blog.website.model.Bo.RestResponseBo;
import com.sk.blog.website.model.Vo.AttachVo;
import com.sk.blog.website.model.Vo.UserVo;
import com.sk.blog.website.service.IAttachService;
import com.sk.blog.website.service.ILogService;
import com.sk.blog.website.utils.Commons;
import com.sk.blog.website.utils.TaleUtils;
import com.sk.config.fdfs.FdfsUpAndDowServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 附件管理
 * <p>
 * Created by 13 on 2017/2/21.
 */
@Controller
@RequestMapping("admin/attach")
public class AttachController extends BaseController {

    private static final Logger LOGGER = LoggerFactory.getLogger(AttachController.class);

    public static final String CLASSPATH = TaleUtils.getUploadFilePath();

    @Resource
    private IAttachService attachService;

    @Resource
    private ILogService logService;

    @Autowired
    private FdfsUpAndDowServiceImpl upAndDowService;

    /**
     * 附件页面
     *
     * @param request
     * @param page
     * @param limit
     * @return
     */
    @GetMapping(value = "")
    public String index(HttpServletRequest request, @RequestParam(value = "page", defaultValue = "1") int page,
                        @RequestParam(value = "limit", defaultValue = "9") int limit) {
        PageInfo<AttachVo> attachPaginator = attachService.getAttachs(page, limit);
        request.setAttribute("attachs", attachPaginator);
        request.setAttribute(Types.ATTACH_URL.getType(), Commons.site_option(Types.ATTACH_URL.getType(), Commons.site_url()));
        request.setAttribute("max_file_size", WebConst.MAX_FILE_SIZE / 1024);
        request.setAttribute("fdfsUrl", "http://vpn.chaseself.com:28080/");
        return "admin/attach";
    }

    /**
     * 上传文件接口
     *
     * @param request
     * @return
     */
    @PostMapping(value = "upload")
    @ResponseBody
    public RestResponseBo upload(HttpServletRequest request, @RequestParam("file") MultipartFile[] multipartFiles) throws IOException {
        UserVo users = this.user(request);
        Integer uid = users.getId();
        List<String> errorFiles = new ArrayList<>();
        try {
            for (MultipartFile multipartFile : multipartFiles) {
                String fname = multipartFile.getOriginalFilename();
                if (multipartFile.getSize() <= WebConst.MAX_FILE_SIZE) {
                    String ftype = TaleUtils.isImage(multipartFile.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType();
                    String fkey = upAndDowService.uploadFile(multipartFile);
                    attachService.save(fname, fkey, ftype, uid);
                } else {
                    errorFiles.add(fname);
                }
            }
        } catch (Exception e) {
            return RestResponseBo.fail();
        }
        return RestResponseBo.ok(errorFiles);
    }

//    private void uploadNative() {
//        String fkey = TaleUtils.getFileKey(fname);
//        String ftype = TaleUtils.isImage(multipartFile.getInputStream()) ? Types.IMAGE.getType() : Types.FILE.getType();
//        String feky = upAndDowService.uploadFile(multipartFile);
////                    File file = new File(CLASSPATH + fkey);
////                    try {
////                        FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));
////                    } catch (IOException e) {
////                        e.printStackTrace();
////                    }
//        attachService.save(fname, fkey, ftype, uid);
//    }

    @RequestMapping(value = "delete")
    @ResponseBody
    public RestResponseBo delete(@RequestParam Integer id, HttpServletRequest request) {
        try {
            AttachVo attach = attachService.selectById(id);
            if (null == attach) {
                return RestResponseBo.fail("不存在该附件");
            }
            attachService.deleteById(id);
            upAndDowService.deleteFile(attach.getFkey());
//            new File(CLASSPATH + attach.getFkey()).delete();
            logService.insertLog(LogActions.DEL_ARTICLE.getAction(), attach.getFkey(), request.getRemoteAddr(), this.getUid(request));
        } catch (Exception e) {
            String msg = "附件删除失败";
            LOGGER.error(msg, e);
            return RestResponseBo.fail(msg);
        }
        return RestResponseBo.ok();
    }

}
