package com.sk;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.sk.entity.ClassName;
import com.sk.entity.Person;
import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.*;
import java.util.Date;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DemoApplicationTests {

    /**
     * 拷贝测试ok
     */
    @Test
    public void contextLoads() {
        String origFile = "F:/海贼王.xls";
//        String copyFile = "F:/海贼王2.xls";
        String copyFile = "D:/exportTemp.xls";//拷贝到其它盘并重命名
        long startTime = System.currentTimeMillis();
        this.copy(origFile, copyFile);
        long endTime = System.currentTimeMillis();
        System.out.println("拷贝图片花费时间为：" + (endTime - startTime));
    }

    /**
     * 导入测试ok
     */
    @Test
    public void importExcel() {
        File file = new File("F:\\prodemo\\导入表格测试.xlsx");
//        File file = new File(FileUtilTest.getWebRootPath("F:\\prodemo\\testFiles\\海贼王.xls"));
        try {
            ImportParams importParams = new ImportParams();
            importParams.setTitleRows(1);
            importParams.setHeadRows(1);
            List<Person> personList = ExcelImportUtil.importExcel(file, Person.class, importParams);//创建对象失败异常？xls信息与Person类没对应上？？？
            System.out.println(personList);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入xls测试--ok
     */
    @Test
    public void importTest() {
        ImportParams params = new ImportParams();
        params.setHeadRows(2);
        long start = new Date().getTime();
        List<ClassName> list = ExcelImportUtil.importExcel(new File("E:\\workSpace\\spring-cloud-sk\\sk-spring-cloud-master\\sk-example\\sk-easypoi\\src\\main\\resources\\impTemp\\sameName.xls"), ClassName.class, params);
        System.out.println(new Date().getTime() - start);
        System.out.println(list.size());
        System.out.println(ReflectionToStringBuilder.toString(list.get(0)));
        System.out.println(ReflectionToStringBuilder.toString(list.get(0).getArrA().get(0)));

    }

    private static void copy(String origFile, String copyFile) {
        // 3.将创建的节点流的对象作为形参传递给缓冲流的构造器中
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;
        try {
            // 1.提供读入、写出的流
            File file1 = new File(origFile);
            File file2 = new File(copyFile);
            // 2.创建相应的节点流
            FileInputStream fis = new FileInputStream(file1);
            FileOutputStream fos = new FileOutputStream(file2);
            bis = new BufferedInputStream(fis);
            bos = new BufferedOutputStream(fos);
            // 4.实现文件的复制
            byte[] b = new byte[20];// 每次运20个，可按照实际文件大小调整
            int len;
            while ((len = bis.read(b)) != -1)
                bos.write(b, 0, len);
        } catch (

                Exception e) {
            e.printStackTrace();
        } finally {// 关闭相应的流
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (bis != null)
                        try {
                            bis.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
            }

        }
    }
}
