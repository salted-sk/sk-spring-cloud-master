package com.sk.common.mapper;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * 通用mapper
 *
 * @author zhangqiao
 * @since 2019/9/6 11:49
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {

    //FIXME 特别注意，该接口不能被扫描到，否则会出错

}
