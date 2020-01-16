package com.sk.controller;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.util.CollectionUtils;
import com.sk.entity.ConsumeVo;
import com.sk.util.AliEasyExcelUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * easyexcel导出导入控制器
 * <p>
 * 导出【10000】条数据耗时【201】毫秒
 * 导出【50000】条数据耗时【1638】毫秒
 * 导出【100000】条数据耗时【2631】毫秒。【注意：】阿里的easyExcel导出超过6万条不会新建sheet表格
 * 导出【500000】条数据耗时【11902】毫秒
 *
 * @author zhangqiao
 * @since 2019/12/4 13:36
 */
@Controller
@Slf4j
public class EasyExcelController {

    /**
     * 导出消费记录
     *
     * @param response
     */
    @RequestMapping("exportConsume")
    public void export(HttpServletResponse response) {
        Long start = System.currentTimeMillis();
        //模拟从数据库获取需要导出的数据
        List<ConsumeVo> consumeVoList = new ArrayList<>();
        ConsumeVo consumeVo;
        int total = 100000;
        for (int i = 0; i < total; i++) {
            consumeVo = new ConsumeVo();
            consumeVo.setAccessType(new Byte("2"));
            consumeVo.setBatchNo("1000" + i);
            consumeVo.setAmount(new BigDecimal(10 + i));
            consumeVo.setProductName("商品" + i);
            consumeVo.setMerchantName("商户" + i);
            // 1:VIP消费, 2:普通消费
            consumeVo.setStatus(i/2 == 0 ? new Byte("1") : new Byte("2"));
            consumeVoList.add(consumeVo);
        }

        //导出操作
        OutputStream outputStream = null;
        try {
            List<ConsumeVo> list = consumeVoList;
            String fileName = UUID.randomUUID().toString().replace("-", "");
            response.setContentType("multipart/form-data");
            response.setCharacterEncoding("utf-8");
            response.setHeader("Content-disposition", "attachment;filename=" + fileName + ".xlsx");

            outputStream = response.getOutputStream();
            EasyExcel.write(outputStream, ConsumeVo.class).sheet("表格明细").doWrite(list);

        } catch (IOException e) {
            e.printStackTrace();
            log.error(e.getMessage());
        } finally {
            try {
                if (outputStream != null) {
                    outputStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        log.info("导出【{}】条数据耗时【{}】毫秒", total, System.currentTimeMillis() - start);
    }

    /**
     * 新版easyexcel导入demo
     * 参考  https://blog.csdn.net/u010957645/article/details/101362194
     *
     * @param excelFile
     * @return
     */
    @RequestMapping("/readExcel")
    @ResponseBody
    public Map<String, Object> toEntity(@RequestParam(value = "file", required = false) MultipartFile excelFile) {
        Map<String, Object> result = new HashMap<>(16);
        //============================不使用模板======================================================
        File file = new File("F:\\prodemo\\导入表格测试.xlsx");
        // 不用模板，读取为map。从第一个sheet、从表头（第2行）以下开始读取
        // demo1、根据文件读取
        List<Object> mapList = EasyExcel.read(file).sheet(0).headRowNumber(2).doReadSync();
        // demo2、根据文件路径读取
        List<Object> mapList2 = EasyExcel.read("F:\\prodemo\\导入表格测试.xlsx").sheet(0).headRowNumber(2).doReadSync();
        // demo3、读10000条数据
        List<Object> mapList10000 = EasyExcel.read("F:\\download\\111119a5c6407d8ff3e.xlsx").sheet(0).headRowNumber(1).doReadSync();
        // TODO 根据业务处理……

        //============================使用模板======================================================
        InputStream inputStream = null;
        try {
            // demo4、使用模板导入——接受上传文件
//            inputStream = excelFile.getInputStream();
            // demo5、使用模板导入——根据文件路径
            inputStream = new FileInputStream(new File("F:\\download\\111119a5c6407d8ff3e.xlsx"));
            // 读取表格数据
            List<Object> consumeVoList = EasyExcel.read(inputStream, ConsumeVo.class, null).headRowNumber(1).sheet(0).doReadSync();
            // TODO 根据业务处理objectList……

            result.put("mapList", mapList);
            result.put("mapList10000", mapList10000);
            result.put("consumeVoList", consumeVoList);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return result;
    }

    /**
     * 导入消费记录，AliEasyExcelUtil工具类有过期方法，待改善
     *
     * @param response
     */
    @RequestMapping("importConsume")
    public String importConsume(HttpServletResponse response) {
        //此处千万不要用InputStream，InputStream会导致无法解析出文件类型
        BufferedInputStream buffer = null;
        try {
            List<Object> list = AliEasyExcelUtil.readLessThan1000Row("F:\\prodemo\\导入表格测试.xlsx");
            //excel中第一行为栏目，必然是存在一行的
            if (CollectionUtils.isEmpty(list) || list.size() < 2) {
                return "导入数据为空！";
            }

            //进行业务处理，list为excel中解析出的数据
//            doSomething ....

        } catch (Exception e) {
            log.error("批量导入会员打标失败", e);
        } finally {
            if (buffer != null) {
                try {
                    buffer.close();
                } catch (IOException e) {
                    log.error("导入失败", e);
                }
            }
        }
        return "导入完成！";
    }


}
