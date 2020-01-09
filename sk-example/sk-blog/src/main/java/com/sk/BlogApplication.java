package com.sk;

import com.sk.blog.dao.BlogSysUserDao;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.web.firewall.HttpFirewall;
import org.springframework.security.web.firewall.StrictHttpFirewall;
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

    /**
     * 放开安全限制，不适用于安全性高的应用
     * 解决第一次访问时携带;session问题
     *
     * @return
     */
    @Bean
    public HttpFirewall allowUrlSemicolonHttpFirewall() {
        StrictHttpFirewall firewall = new StrictHttpFirewall();
        firewall.setAllowSemicolon(true);
        return firewall;
    }

}
