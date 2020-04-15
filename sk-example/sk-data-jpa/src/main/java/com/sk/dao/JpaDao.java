package com.sk.dao;

import com.sk.entity.JpaTable1;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * TODO
 *
 * @author zhangqiao
 * @since 2020/4/15 15:11
 */
@Mapper
public interface JpaDao extends JpaRepository<JpaTable1, Integer>{


}
