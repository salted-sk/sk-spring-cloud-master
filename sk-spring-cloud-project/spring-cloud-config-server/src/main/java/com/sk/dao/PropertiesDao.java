package com.sk.dao;

import com.sk.common.mapper.MyMapper;
import com.sk.entity.Properties;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * Dao层
 *
 * @author zhangqiao
 * @since 2019-12-12 11:11:40
 */
public interface PropertiesDao extends MyMapper<Properties> {

    List<String> getGroupTypeList(@Param("type") String type);

}