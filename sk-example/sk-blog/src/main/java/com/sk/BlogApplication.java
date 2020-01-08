package com.sk;

import com.sk.blog.dao.BlogSysUserDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * 博客系统启动
 *
 * @author zhangqiao
 * @since 2020/1/8 16:30
 */
@MapperScan("com.sk.blog.website.dao")
@tk.mybatis.spring.annotation.MapperScan(basePackages = {"com.sk.blog.dao", "com.sk.common.base.dao"})
@SpringBootApplication
@EnableTransactionManagement
public class BlogApplication {

    public static void main(String[] args) {
        SpringApplication.run(BlogApplication.class, args);
    }

}
