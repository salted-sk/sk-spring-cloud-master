package com.sk.common.mapper;

import org.springframework.data.repository.NoRepositoryBean;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用mapper
 *
 * @author zhangqiao
 * @since 2019/9/6 11:49
 */
@NoRepositoryBean
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

}
